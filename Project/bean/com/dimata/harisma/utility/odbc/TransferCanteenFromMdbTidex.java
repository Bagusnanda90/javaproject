/*
 * TransferCanteenFromMdb.java
 *
 * Created on January 27, 2008, 9:50 PM
 */
package com.dimata.harisma.utility.odbc;

import java.util.*;
import java.sql.*;

import com.dimata.qdep.db.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;

import com.dimata.harisma.entity.canteen.*;
import com.dimata.harisma.entity.canteen.CanteenSchedule;
import com.dimata.harisma.entity.canteen.PstCanteenSchedule;
import com.dimata.system.entity.system.*;

/**
 *
 * @author  gedhy
 */
public class TransferCanteenFromMdbTidex extends DBConnection {

    public static final String tableName = "Transaction";
    public static final String CardID = "CardId";
    public static final String DateTrn = "DateTrn";
    public static final String Posted = "Posted";
    public static final String Station = "Station";
    public static final String Mode = "Mode";
    public static final String HarismaSt = "HarismaSt";
    public static int IGNORE_TIME = 15 * 60 * 1000;          /* --- in milli seconds --- */

    private static boolean IGNORE;
    public static boolean CHECK_SWEEP_TIME = false;
    public static final char ACTION_IN = 'A';
    public static final char ACTION_OUT_HOME = 'B';
    public static final String actionNames[] =
            {
        "In",
        "Out Home",
        "Out Work",
        "In  O/W"
    };

    public TransferCanteenFromMdbTidex() {
        try {
            CHECK_SWEEP_TIME = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_CHECK_SWEEP_TIME")) > 0;
            int ignoreTime = Integer.parseInt(PstSystemProperty.getValueByName("CANTEEN_IGNORE_SWEEP_TIME")) * 60 * 1000;
            if (ignoreTime >= 0) {
                IGNORE_TIME = ignoreTime;
            }
        } catch (Exception exc) {
            System.out.println("Exc. during instantiation of TransferCanteenFromMdbTidex");
        }
    }

    private class TransRecord {

        protected String sCardId = "";
        protected String sDateTrn = "";
        protected String sPosted = "";
        protected String sStation = "";
        protected String sMode = "";
    }
    public String dbDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
    public String dbUrl = "jdbc:odbc:harisma_sp_real_mdb";
    public String absenStation = "02";
    public String canteenStation = "01";

    public Vector retrieveData(String StationNr) {
        Connection dbConn = null;
        Statement stmt = null;
        Vector result = new Vector(1, 1);
        try {
            String sql = "SELECT " + CardID + "," + DateTrn + "," + Posted + "," + Station + "," + Mode + " FROM " + tableName +
                    " WHERE ((" + Station + "='" + StationNr + "') AND (" + HarismaSt + "<1 OR " + HarismaSt + " IS NULL) ) ORDER BY " + CardID + ", " + DateTrn;

            System.out.println("retrieveData sql : " + sql);
            dbConn = doConnect(dbDriver, dbUrl);
            stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Vector vectTemp = new Vector(1, 1);

                TransRecord tr = new TransRecord();
                tr.sCardId = rs.getString(1);
                tr.sDateTrn = rs.getString(2);
                tr.sPosted = rs.getString(3);
                tr.sStation = rs.getString(4);
                tr.sMode = rs.getString(5);
                result.add(tr);
            }
        } catch (Exception e) {
            System.out.println("err retrieve : " + e.toString());
        } finally {
            doDisconnect();
            return result;
        }
    }

    public int updateDataCanteen(String cardId, String dateTrn, String posted, String station, String mode, String setHarismaSt) {
        Connection dbConn = null;
        Statement stmt = null;
        int result = 0;
        try {
            String sql = "update " + tableName + " set " + tableName + "." + HarismaSt + "=" + setHarismaSt + " " +
                    " where (" + tableName + "." + CardID + " = '" + cardId + "'" +
                    " and " + tableName + "." + DateTrn + " = #" + dateTrn + "#" +
                    (posted==null || posted.length()<1 ? "" :
                    " and " + tableName + "." + Posted + " = " + posted + " ") +
                    " and " + tableName + "." + Station + " = '" + station + "'" +
                    (mode==null || mode.length()<1 ? "" :
                    " and " + tableName + "." + Mode + " = '" + mode + "')");

            //System.out.println("sql update : " + sql);
            //sql="update transaction set transaction.HarismaSt=1 where transaction.CardId='10109'";
            dbConn = doConnect(dbDriver, dbUrl);
            stmt = dbConn.createStatement();
            result = stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("err delete : " + e.toString());
        } finally {
            doDisconnect();
            return result;
        }
    }

    public int updateAllDataMdb(String station, String mode, String setHarismaSt) {
        Connection dbConn = null;
        Statement stmt = null;
        int result = 0;
        try {
            String sql = "update " + tableName + " set " + tableName + "." + HarismaSt + "=" + setHarismaSt + " " +
                    " where (" + tableName + "." + Station + " = '" + station + "'" +
                    " and " + tableName + "." + Mode + " = '" + mode + "')";

            //System.out.println("sql update : " + sql);
            //sql="update transaction set transaction.HarismaSt=1 where transaction.CardId='10109'";
            dbConn = doConnect(dbDriver, dbUrl);
            stmt = dbConn.createStatement();
            result = stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("err delete : " + e.toString());
        } finally {
            doDisconnect();
            return result;
        }
    }
    
    public int updateAllDataMdb(String station, String mode, String setHarismaSt, String startDate, String endDate) {
        Connection dbConn = null;
        Statement stmt = null;
        int result = 0;
        try {
            String sql = "update " + tableName + " set " + tableName + "." + HarismaSt + "=" + setHarismaSt + " " +
                    " where (" + tableName + "." + Station + " = '" + station + "'" +
                    (mode==null || mode.length()<1 ? "" :                 
                    " and " + tableName + "." + Mode + " = '" + mode + "') ") +
                    " and "+tableName+"."+DateTrn+">= #"+startDate+"# " +
                    " and "+tableName+"."+DateTrn+"<= #"+endDate+"# )";

            //System.out.println("sql update : " + sql);
            //sql="update transaction set transaction.HarismaSt=1 where transaction.CardId='10109'";
            dbConn = doConnect(dbDriver, dbUrl);
            stmt = dbConn.createStatement();
            result = stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("err delete : " + e.toString());
        } finally {
            doDisconnect();
            return result;
        }
    }
    

    public int deleteDataCanteen(String cardId, String dateTrn, String posted, String station, String mode) {
        Connection dbConn = null;
        Statement stmt = null;
        int result = 0;
        try {
            String sql = "DELETE FROM " + tableName +
                    " WHERE CARDID = '" + cardId + "'" +
                    " AND DATETRN = #" + dateTrn + "#" +
                    " AND POSTED = '" + posted + "'" +
                    " AND STATION = '" + station + "'" +
                    " AND MODE = '" + mode + "'";

            //System.out.println("sql delete : " + sql);
            dbConn = doConnect(dbDriver, dbUrl);
            stmt = dbConn.createStatement();
            result = stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("err delete : " + e.toString());
        } finally {
            doDisconnect();
            return result;
        }
    }

    public ReturnTransferDataCanteen transferDataCanteen(String StationNr) {
        int nCanteenData = 0;

        ReturnTransferDataCanteen rtDt = new ReturnTransferDataCanteen();
        Vector empNotFound = rtDt.empNotFound;

        TransferCanteenFromMdbTidex transfer = new TransferCanteenFromMdbTidex();
        Vector vectCanteenData = transfer.retrieveData(StationNr);
        if (vectCanteenData != null && vectCanteenData.size() > 0) {
            System.out.println("\n\n------ START TRANSFER DATA TIDEX MDB ---------\n\n");
            System.out.println("\nvectCanteenData.size() : " + vectCanteenData.size() + "\n");

            int maxResult = vectCanteenData.size();
            long oidVisitation = 0;
            int dataIgnored =0;
            int dataInserted=0;

            for (int i = 0; i < maxResult; i++) {
                TransRecord tr = (TransRecord) vectCanteenData.get(i);

                String tBarcode = "" + tr.sCardId;
                tBarcode = tBarcode.trim();
                // String employeeNumber = resultRead.substring(17, resultRead.length());

                java.util.Date transDate = null;

                try {

                    String date = (String) tr.sDateTrn;   // sample : 2008-01-19 19:07:00
                    int y = Integer.parseInt(date.substring(0, 4).trim());
                    int M = Integer.parseInt(date.substring(5, 7).trim());

                    int d = Integer.parseInt(date.substring(8, 10).trim());
                    //String time = (String) tr.sDateTrn;
                    int h = Integer.parseInt(date.substring(11, 13).trim());
                    int m = Integer.parseInt(date.substring(14, 16).trim());


                    transDate = new java.util.Date(y - 1900, M - 1, d, h, m);

                } catch (Exception exc) {
                    System.out.println("Exception on parsing canteen rec date " + tr.sCardId + " " + tr.sDateTrn + "\n" + exc);
                }
                // update status of canteen visitor

                CanteenVisitation canteenVisitation = new CanteenVisitation();
                long employeeOid = PstEmployee.getEmployeeByBarcode(tBarcode);
                
                
                if (employeeOid == 0) {  // employee tBarcode belum terdaftar di Employee ID
                    employeeOid = PstEmployee.getEmployeeIdByNum(tBarcode.trim()); // coba check dengan menggunanakan employee number                    
                    if (employeeOid == 0) {  // employee tBarcode belum terdaftar di Employee ID                        
                        employeeOid =PstEmployee.getEmployeeLikeBarcode(tBarcode); //coba cari barcode yang mirip
                        if (employeeOid == 0) {
                            employeeOid = PstEmployee.getEmployeeIdLikeNum(tBarcode.trim()); // coba check yang mirip employee number                    
                            if (employeeOid == 0){
                                empNotFound.add("NOT FOUND Barcode : "+tBarcode);
                            } else{
                                 empNotFound.add("ID :"+tBarcode+" FOUND with LIKE");
                            }
                        } else {                                                
                            empNotFound.add("ID :"+tBarcode+" FOUND with LIKE");
                        }
                    }
                } 
                
                if (employeeOid != 0){
                    IGNORE = false;

                    if (CHECK_SWEEP_TIME) {
                        //canteenVisitation = PstCanteenVisitation.getLatestVisitation(employeeOid);
                        java.util.Date dtMinVisit = new java.util.Date(transDate.getTime()-IGNORE_TIME);
                        java.util.Date dtMaxVisit = new java.util.Date(transDate.getTime()+IGNORE_TIME);
                        String whereCls = PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_EMPLOYEE_ID]+" = '"+ employeeOid+"'"+ " AND "+
                                          PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]+" >= \""+
                                          com.dimata.util.Formater.formatDate(dtMinVisit, "yyyy-MM-dd HH:mm:ss\"")+ " AND " +
                                          PstCanteenVisitation.fieldNames[PstCanteenVisitation.FLD_VISITATION_TIME]+" <= \""+
                                          com.dimata.util.Formater.formatDate(dtMaxVisit, "yyyy-MM-dd HH:mm:ss\"");
                        
                        int numberOfVisit = PstCanteenVisitation.getCount(whereCls);
                        if (numberOfVisit == 0) {
                            IGNORE = false;
                        } else {
                            /*
                            java.util.Date lastEmpTransTime = canteenVisitation.getVisitationTime();
                            long transactionTime = transDate.getTime();
                            long lastEmployeeVisitation = lastEmpTransTime.getTime();
                            long diff = Math.abs(transactionTime - lastEmployeeVisitation);
                            IGNORE = (diff <= IGNORE_TIME);
                             */
                            IGNORE=true;
                            //if (IGNORE) 
                            {
                                System.out.println(""+i+" Visit. ID/ employeeOid = " + tBarcode+" / "+employeeOid + " is IGNORED ...");
                                dataIgnored++;
                                empNotFound.add("Visit at : "+com.dimata.util.Formater.formatDate(dtMinVisit, "yyyy-MM-dd HH:mm:ss\"")+" for ID/ employeeOid = " + tBarcode+" / "+employeeOid + " is IGNORED ...");
                            }
                            

                        }
                    //long employeeOid = PstEmployee.getEmployeeIdByNum(employeeNumber);
                    }

                    //System.out.println("Employee OID = " + employeeOid);
                    //System.out.println("Value Of tFunction(0) = " + tFunction.charAt(0));

                    if (!IGNORE) {
                        canteenVisitation = new CanteenVisitation();
                        int canteenVisitationStatus = 0;
                        try {
                            switch (tr.sMode.charAt(0)) {
                                case 'A':
                                    canteenVisitation.setStatus(0);
                                    canteenVisitationStatus = 0;
                                    break;
                                case 'B':
                                    canteenVisitation.setStatus(1);
                                    canteenVisitationStatus = 1;
                                    break;
                                case 'C':
                                    canteenVisitation.setStatus(2);
                                    canteenVisitationStatus = 2;
                                    break;
                                case 'D':
                                    canteenVisitation.setStatus(3);
                                    canteenVisitationStatus = 3;
                                    break;
                                case 'E':
                                    canteenVisitation.setStatus(4);
                                    canteenVisitationStatus = 4;
                                    break;
                                case 'F':
                                    canteenVisitation.setStatus(5);
                                    canteenVisitationStatus = 5;
                                    break;
                                default:
                            }
                        } catch (Exception exc) {
                        }

                        // inserting download data to database
                        canteenVisitation.setEmployeeId(employeeOid);
                        //canteenVisitation.setEmployeeId(tBarcode);
                        canteenVisitation.setVisitationTime(transDate);
                        canteenVisitation.setAnalyzed(0);
                        canteenVisitation.setTransferred(0);
                        canteenVisitation.setNumOfVisitation(1);
                        try {
                            oidVisitation = PstCanteenVisitation.insertExc(canteenVisitation);
                            System.out.println(""+i+" Visit. employeeOid = " + employeeOid + " is INSERTED ...");
                            dataInserted++;
                        } catch (Exception exc) {
                            System.out.println("exc: in get canteenVisitation");
                        }
                    } else {
                        oidVisitation = employeeOid;
                    }

                    if (oidVisitation != 0) {
                        nCanteenData++;
                        transfer.updateDataCanteen(tr.sCardId, tr.sDateTrn, tr.sPosted, tr.sStation, tr.sMode, "1");
                    }

                }
            }
            System.out.println("Canteen : Total data="+maxResult+"  INSERTED=" + dataInserted + " IGNORED="+dataIgnored);
            rtDt.empNotFound.add("Canteen : Total data="+maxResult+"  INSERTED=" + dataInserted + " IGNORED="+dataIgnored);
        }
        rtDt.numberOfRecords = nCanteenData;
        return rtDt;
    }

    /**
     * Testing method
     */
    public static void main(String args[]) {
        TransferPresenceFromMdbTidex objTransferPresenceFromMdb = new TransferPresenceFromMdbTidex();
        if (false) { // test get only
            Vector vectPresenceData = objTransferPresenceFromMdb.retrieveData(objTransferPresenceFromMdb.absenStation);
            if (vectPresenceData != null && vectPresenceData.size() > 0) {
                int maxPresence = vectPresenceData.size();
                System.out.println("Employee ID | Card ID |  EmployeeId  | Date ");
                for (int i = 0; i <
                        maxPresence; i++) {
                    TransRecord tr = (TransRecord) vectPresenceData.get(i);

                    String strDate = tr.sDateTrn;
                    int intYear = Integer.parseInt(strDate.substring(0, 4));
                    int intMonth = Integer.parseInt(strDate.substring(5, 7));
                    int intDate = Integer.parseInt(strDate.substring(8, 10));
                    int intHour = Integer.parseInt(strDate.substring(11, 13));
                    int intMinutes = Integer.parseInt(strDate.substring(14, 16));
                    int intSecond = Integer.parseInt(strDate.substring(17, 19));
                    java.util.Date dtPresence = new java.util.Date(intYear - 1900, intMonth - 1, intDate, intHour, intMinutes, intSecond);

                    long employeeId = PstEmployee.getEmployeeByBarcode(tr.sCardId);
                    System.out.println(employeeId + " | " + tr.sCardId + " | " + strDate);

                /*
                System.out.println("vectTemp(1) : " + vectTemp.get(0));
                System.out.println("vectTemp(2) : " + vectTemp.get(1));
                System.out.println("dtPresence  : " + dtPresence);                
                System.out.println("vectTemp(3) : " + vectTemp.get(2));
                System.out.println("vectTemp(4) : " + vectTemp.get(3));
                System.out.println("vectTemp(5) : " + vectTemp.get(4));                
                System.out.println("intYear     : " + intYear);
                System.out.println("intMonth    : " + intMonth);
                System.out.println("intDate     : " + intDate);
                System.out.println("intHour     : " + intHour);
                System.out.println("intMinutes  : " + intMinutes);
                System.out.println("intSecond   : " + intSecond);
                 */
                }

            }
        }

      //  objTransferPresenceFromMdb.transferDataPresence("02");
    //objTransferPresenceFromMdb.updateAllDataMdb("02","A","0");
    //objTransferPresenceFromMdb.updateAllDataMdb("02","B","0");

    /*
    int result = transfer.deleteDataPresence("05-25-2004", "2335", "222222", "0");  
    System.out.println("delete finish ... : " + result);
     */

    /*  
    Vector result = transfer.transferDataPresence();
    if(result!=null && result.size()>0)
    {            
    int maxResult = result.size();
    for(int i=0; i<maxResult; i++){
    Vector tr = (Vector)result.get(i);
    System.out.print(String.valueOf(tr.get(0)));
    System.out.print(String.valueOf(tr.get(1)));
    System.out.print(String.valueOf(tr.get(2)));
    System.out.println(String.valueOf(tr.get(3)));                
    } 
    } 
     */
        
     
        // reset data by date
        TransferCanteenFromMdbTidex transfer = new TransferCanteenFromMdbTidex();
            
        String station="01";
        String mode="";
        String setHarismaSt="0";
        String startDate="05/03/2008 00:00:01";
        String endDate="05/04/2008 00:00:01";
        transfer.updateAllDataMdb(station, mode, setHarismaSt, startDate,endDate);
        
    }
}
