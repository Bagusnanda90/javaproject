/*
 * CheckSweepTime.java
 * @author  rusdianta
 * Created on February 10, 2005, 2:06 PM
 */

package com.dimata.harisma.utility.service.tma;

import java.util.*;
import java.sql.*;

import com.dimata.qdep.db.*;
import com.dimata.system.entity.system.*;

import com.dimata.harisma.entity.canteen.*;

public class CheckSweepTime {
    
    public static final String TBL_HR_CANTEEN_VISITS_TEMP = "hr_canteen_visits_temp";//"HR_CANTEEN_VISITS_TEMP";
    
    private boolean IGNORE = false;
    private static int IGNORE_TIME = 15 * 60 * 1000;
    
    /** Creates a new instance of CheckSweepTime */
    public CheckSweepTime() {
    }
    
    public static Vector getVisitsData() {
        Vector results = new Vector();
        try {
            String sql = "SELECT * FROM " + TBL_HR_CANTEEN_VISITS_TEMP;
            DBResultSet dbrs = DBHandler.execQueryResult(sql);
            ResultSet rsl = dbrs.getResultSet();
            
            while (rsl.next()) {
                CanteenVisitation data = new CanteenVisitation();
                data.setVisitationId(rsl.getLong("VISITATION_ID"));
                data.setEmployeeId(rsl.getLong("EMPLOYEE_ID"));
                java.sql.Date date = rsl.getDate("VISITATION_TIME");
                Time time = rsl.getTime("VISITATION_TIME");
                java.util.Date visitTime = new java.util.Date(date.getYear(), date.getMonth(), date.getDate(), time.getHours(), time.getMinutes(), time.getSeconds());
                data.setVisitationTime(visitTime);
                data.setStatus(rsl.getInt("STATUS"));
                data.setAnalyzed(rsl.getInt("ANALYZED"));
                data.setTransferred(rsl.getInt("TRANSFERRED"));
                results.add(data);
            }
            rsl.close();
        } catch (Exception error) {
            System.out.println("There are error occur in CheckSweepTime : " + error.toString());
        }
        return results;
    }
    
    public static void main(String args[]) {
        Vector orgVisitsData = getVisitsData();
        int numOfData = orgVisitsData.size();
        System.out.println("Number Of Data : " + numOfData);
        
        int dataCount = 0;        
        boolean CHECK_SWEEP_TIME = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_CHECK_SWEEP_TIME")) > 0;        
        int ignoreTime = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_IGNORE_SWEEP_TIME")) * 60 * 1000;
        if (ignoreTime > 0)
            IGNORE_TIME = ignoreTime;         
        
        for (int item = 0; item < numOfData; item++) {            
            CanteenVisitation canVisitsData = (CanteenVisitation) orgVisitsData.get(item);            
            long oidVisitation = 0;
            java.util.Date downloadDate = new java.util.Date();
            dataCount++;
            
            System.out.println("\tCheckSweepTime transaction #" + dataCount);
            
            String tNumber = "01";
            String tFunction = "A";
            java.util.Date visitTime = canVisitsData.getVisitationTime();
            //System.out.println((canVisitsData.getVisitationTime()).getYear());            
            int tYear = visitTime.getYear();
            int tMonth = visitTime.getMonth();
            int tDate = visitTime.getDate();
            int tHour = visitTime.getHours();
            int tMin = visitTime.getMinutes();            
            java.util.Date tGregCal = new java.util.Date(tYear, tMonth, tDate, tHour, tMin);
            downloadDate = tGregCal;            
            CanteenVisitation canteenVisitation;            
            long employeeOid = canVisitsData.getEmployeeId();            
            boolean IGNORE = false;
            
            if (CHECK_SWEEP_TIME) {
                canteenVisitation = PstCanteenVisitation.getLatestVisitation(employeeOid);                    
                
                if (canteenVisitation == null) 
                    IGNORE = false;
                else {
                    java.util.Date lastEmpTransTime = canteenVisitation.getVisitationTime(); 
                    long transactionTime = tGregCal.getTime();
                    long lastEmployeeVisitation = lastEmpTransTime.getTime();
                    long diff = transactionTime - lastEmployeeVisitation;
                    IGNORE = (diff <= IGNORE_TIME);
                    if (IGNORE) {
                        System.out.println("Visitation data with employeeOid = " + employeeOid + " is IGNORED ...");
                    }
                }
            }
            
            if (!IGNORE) {
                canteenVisitation = new CanteenVisitation();
                int canteenVisitationStatus = 0;
                
                switch (tFunction.charAt(0)) {
                    case 'A' :
                        canteenVisitation.setStatus(0);
                        canteenVisitationStatus = 0;
                    break;
                    case 'B' :
                        canteenVisitation.setStatus(1);
                        canteenVisitationStatus = 1;
                    break;
                    case 'C' :
                        canteenVisitation.setStatus(2);
                        canteenVisitationStatus = 2;
                    break;
                    case 'D' :
                        canteenVisitation.setStatus(3);
                        canteenVisitationStatus = 3;
                    break;
                    case 'E' :
                        canteenVisitation.setStatus(4);
                        canteenVisitationStatus = 4;
                    break;
                    case 'F' :
                        canteenVisitation.setStatus(5);
                        canteenVisitationStatus = 5;
                    break;
                    default :    
                }
                                
                // inserting download data to database
                canteenVisitation.setEmployeeId(employeeOid);
                //canteenVisitation.setEmployeeId(tBarcode);
                canteenVisitation.setVisitationTime(tGregCal);
                canteenVisitation.setAnalyzed(0);
                canteenVisitation.setTransferred(0);
                
                try {
                    oidVisitation = PstCanteenVisitation.insertExc(canteenVisitation);                    
                    System.out.println("INSERT TRANSACTION WITH employeeOid = " + employeeOid + " is SUCCESS ...");                    
                } catch (Exception error) {
                    System.out.println(error.toString());
                }
            } else
                oidVisitation = employeeOid;
        }   
    }
}
