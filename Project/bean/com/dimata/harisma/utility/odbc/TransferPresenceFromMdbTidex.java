/*
 * TransferPresenceFromMdb.java
 *
 * Created on December 18, 2004, 7:57 AM
 */
package com.dimata.harisma.utility.odbc;

import java.util.*;
import java.sql.*;

import com.dimata.qdep.db.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;

import com.dimata.harisma.session.attendance.*;

/**
 *
 * @author  gedhy
 */
public class TransferPresenceFromMdbTidex extends DBConnection {

    public static final String tableName    = "Transaction";
    public static final String CardID       = "CardId";
    public static final String DateTrn      = "DateTrn";
    public static final String Posted       = "Posted";
    public static final String Station      = "Station";
    public static final String Mode         = "Mode";
    public static final String HarismaSt    = "HarismaSt";    
    
    public static final char ACTION_IN          = 'A';
    public static final char ACTION_OUT_HOME    = 'B';
    public static final String actionNames[]    = {
        "In",
        "Out Home",
        "Out Work",
        "In  O/W"
    };

    private class TransRecord {
        protected String sCardId = "";
        protected String sDateTrn = "";
        protected String sPosted = "";
        protected String sStation = "";
        protected String sMode = "";
    }

    public String dbDriver          = "sun.jdbc.odbc.JdbcOdbcDriver";
    public String dbUrl             = "jdbc:odbc:harisma_sp_real_mdb"; 
    public String absenStation      = "02";
    public String canteenStation    = "01";    
    
    public Vector retrieveData(String StationNr) {
        Connection dbConn = null;
        Statement stmt = null;
        Vector result = new Vector(1, 1);
        try {
            String sql = "SELECT " + CardID + "," + DateTrn + "," + Posted + "," + Station + "," + Mode + " FROM " + tableName +
                    " WHERE (("+Station+"='"+StationNr +"') AND ("+HarismaSt+"<1 OR "+HarismaSt+" IS NULL) ) ORDER BY " + CardID + ", " + DateTrn;

            System.out.println("retrieveData sql : " + sql);
            dbConn = doConnect(dbDriver,dbUrl);
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
    
    public  Vector retrieveEmployeeOnMDB() {
        Connection dbConn = null;
        Statement stmt = null;
        Vector result = new Vector(1, 1);
        try {
            String sql = "SELECT CardID , Nama, Pin FROM Karyawan"; 
            dbConn = doConnect(dbDriver,dbUrl);
            stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Vector vectTemp = new Vector(1, 1);
                Employee emp = new Employee();                
                emp.setEmployeeNum(rs.getString(1));
                emp.setFullName(rs.getString(2));
                emp.setEmpPin(rs.getString(3));
                result.add(emp);
            }
        } catch (Exception e) {
            System.out.println("err retrieve : " + e.toString());
        } finally {
            doDisconnect();
            return result;
        }
    }
    
    public void checkEmployeeList(){
        Vector empListOnMdb = this.retrieveEmployeeOnMDB();
        if(empListOnMdb!=null){
           for(int i=0;i<empListOnMdb.size();i++)   {
               Employee emp = (Employee) empListOnMdb.get(i);
               System.out.println("Emp ID="+emp.getEmployeeNum()+" ");
               if(!PstEmployee.checkOID((new Long(emp.getEmployeeNum()).longValue()))){
                   System.out.print("Emp ID="+emp.getEmployeeNum()+" ada di mesin, tapi tak ada di MySQL");
               };
           }
        }
        System.out.println(" END ");
        
        
    }

    public int updateDataPresence(String cardId, String dateTrn, String posted, String station, String mode, String setHarismaSt) {
        Connection dbConn = null;
        Statement stmt = null;
        int result = 0;
        try {
            String sql = "update " + tableName + " set " + tableName + "."+HarismaSt+"="+setHarismaSt+" " +
                    " where (" + tableName + "."+CardID+" = '"+ cardId + "'" +
                    " and " + tableName + "."+DateTrn+" = #"+ dateTrn + "#" +
                    " and " + tableName + "."+Posted+" = "+ posted + " " +
                    " and "+ tableName + "."+Station+" = '" + station + "'" +
                    " and "+ tableName + "."+Mode+" = '" + mode + "')";

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
            String sql = "update " + tableName + " set " + tableName + "."+HarismaSt+"="+setHarismaSt+" " +
                    " where (" + tableName + "."+Station+" = '" + station + "'" +
                    " and "+ tableName + "."+Mode+" = '" + mode + "')";

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
    
    
    
    public int deleteDataPresence(String cardId, String dateTrn, String posted, String station, String mode) {
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
    
    
    public Vector transferDataPresence(String StationNr){
        TransferPresenceFromMdbTidex transfer = new TransferPresenceFromMdbTidex();
        Vector vectPresenceData = transfer.retrieveData(StationNr);
        if (vectPresenceData != null && vectPresenceData.size() > 0) {
            System.out.println("\n\n------ START TRANSFER DATA TIDEX MDB ---------\n\n");
            System.out.println("\nvectPresenceData.size() : " + vectPresenceData.size() + "\n");

            int maxResult = vectPresenceData.size();
            for (int i = 0; i < maxResult; i++) {
                TransRecord tr = (TransRecord) vectPresenceData.get(i);

                // proses pembuatan object presence untuk diproses 
                Presence presence = new Presence();

                //System.out.println("processing data i = " + i + " => "+tr.sCardId+" "+tr.sDateTrn+" "+tr.sMode+""+tr.sStation);               


                long employeeId = PstEmployee.getEmployeeByBarcode(tr.sCardId.trim());
                presence.setEmployeeId(employeeId);


                //edited by yunny 
                try{
                String date = (String) tr.sDateTrn;   // sample : 2008-01-19 19:07:00
                int y = Integer.parseInt(date.substring(0, 4).trim());
                int M = Integer.parseInt(date.substring(5, 7).trim());

                int d = Integer.parseInt(date.substring(8, 10).trim());
                //String time = (String) tr.sDateTrn;
                int h = Integer.parseInt(date.substring(11, 13).trim());
                int m = Integer.parseInt(date.substring(14, 16).trim());

                //presence.setPresenceDatetime(tempPresDate);
                java.util.Date presenceDateTime = new java.util.Date(y - 1900, M - 1, d, h, m);
                presence.setPresenceDatetime(presenceDateTime);
                } catch(Exception exc){
                    System.out.println("Exception on parsing date "+tr.sCardId+" "+tr.sDateTrn+"\n"+exc);
                }
                
                //status
                tr.sMode=tr.sMode.trim();
                int intPresenceStatus = -1;
                
                
                if (tr.sMode.equals("A")) {
                    intPresenceStatus = Presence.STATUS_IN;
                } else {
                    if (tr.sMode.equals("B")) {
                        intPresenceStatus = Presence.STATUS_OUT;
                    } else {
                        if (tr.sMode.equals("C")) {
                            intPresenceStatus = Presence.STATUS_OUT_ON_DUTY;
                        } else {
                            if (tr.sMode.equals("D")) {
                                intPresenceStatus = Presence.STATUS_IN_PERSONAL;
                            } else {
                                if (tr.sMode.equals("E")) {
                                    intPresenceStatus = Presence.STATUS_IN_PERSONAL;
                                } else {
                                if (tr.sMode.equals("F")) {
                                    intPresenceStatus = Presence.STATUS_CALL_BACK;
                                } else {
                                }                        
                            }                        
                        }                        
                    }
                }
                }
                presence.setStatus(intPresenceStatus);

                //System.out.println(" Record " + tr.sCardId );

                {
                    presence.setAnalyzed(0);
                    presence.setTransferred(PstPresence.PRESENCE_NOT_TRANSFERRED);


                    // insert data ke table presence harisma
                    boolean objPresenceExist = false;
                    long presenceId = 0;
                    try {
                        if (employeeId != 0) {
                            objPresenceExist = PstPresence.presenceExist(presence);                            
                            if (!objPresenceExist) {
                                presenceId = PstPresence.insertExc(presence);
                                System.out.println("sukses input presence : " + presence.getEmployeeId()+" "+presence.getStatus()+" "+presence.getPresenceDatetime());
                            } else {
                                System.out.println("objPresenceExist : " + presence.getEmployeeId()+" "+presence.getStatus()+" "+presence.getPresenceDatetime());
                            }
                        // delete dari database  
                        //transfer.deleteDataPresence(com.dimata.util.Formater.formatDate(tempPresDate,"MM-dd-yyyy"), String.valueOf(tr.get(1)), String.valueOf(tr.get(2)), String.valueOf(tr.get(3)));
                        transfer.updateDataPresence(tr.sCardId, tr.sDateTrn, tr.sPosted, tr.sStation, tr.sMode,"1");    
                        }
                    } catch (Exception e) {
                        System.out.println("Err : " + e.toString());
                    }


                    // untuk check ke empSchedule
                    // update presence (IN or OUT) on employee schedule  
                    // by KAR just skip this process do later when finished 
                    if (false && !objPresenceExist) {

                        System.out.println("\nin update emp schedule ");

                        if (presenceId != 0) {
                            long periodId = PstPeriod.getPeriodIdBySelectedDate(presence.getPresenceDatetime());

                            System.out.println("\nperiodId : " + periodId);

                            int updatedFieldIndex = -1;
                            long updatePeriodId = periodId;
                            Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdated(periodId, presence.getEmployeeId(), presence.getStatus(), presence.getPresenceDatetime());

                            System.out.println("\nvectFieldIndex : " + vectFieldIndex);

                            if (vectFieldIndex != null && vectFieldIndex.size() == 2) {
                                updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                                updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));
                            }

                            int updateStatus = 0;
                            try {
                                updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, presence.getEmployeeId(), updatedFieldIndex, presence.getPresenceDatetime());

                                System.out.println("\nupdate emp sch status : " + updateStatus);

                                if (updateStatus > 0) {
                                    presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                                    PstPresence.updateExc(presence);

                                    System.out.println("\nsuccess update presence II");
                                }

                            } catch (Exception e) {
                                System.out.println("Update Presence exc : " + e.toString());
                            }
                        }
                    }
                }
            } // end for insert data presence from MDB to MySql
        }   
        
        SessPresence.fixAttendanceRecords(0,0,120,120);         
        return vectPresenceData;
    }
    
    public void transferDataPresenceOld() {
        TransferPresenceFromDb transfer = new TransferPresenceFromDb();
        Vector vectPresenceData = transfer.retrieveData();
        if (vectPresenceData != null && vectPresenceData.size() > 0) {
            int maxResult = vectPresenceData.size();
            for (int i = 0; i < maxResult; i++) {
                Vector tr = (Vector) vectPresenceData.get(i);

                // proses pembuatan object presence untuk diproses 
                Presence presence = new Presence();
                long employeeId = PstEmployee.getEmployeeByBarcode(String.valueOf(tr.get(2)));
                presence.setEmployeeId(employeeId);

            /*
            java.lang.Character charKu = new java.lang.Character();
            char chrPresenceStatusTemp = ((java.lang.Character) tr.get(3)).get;
            int intPresenceStatus = 0;
            switch(chrPresenceStatusTemp)
            {
            case ACTION_IN :
            intPresenceStatus = Presence.STATUS_IN;
            break;                        
            case ACTION_OUT_HOME : 
            intPresenceStatus = Presence.STATUS_OUT;
            break;                        
            default :      
            break;
            }
             */

            /*
            presence.setStatus(intPresenceStatus);
            java.util.Date tempPresDate = (java.util.Date)tr.get(0);
            String tempPresTime = String.valueOf(tr.get(1));
            int y = tempPresDate.getYear();
            int M = tempPresDate.getMonth();
            int d = tempPresDate.getDate();
            int h = Integer.parseInt(tempPresTime.substring(0,2));
            int m = Integer.parseInt(tempPresTime.substring(2,4));
            java.util.Date presenceDateTime = new java.util.Date(y, M, d, h, m);
            presence.setPresenceDatetime(presenceDateTime);
            presence.setAnalyzed(0);
            presence.setTransferred(PstPresence.PRESENCE_NOT_TRANSFERRED);
            // insert data ke table presence harisma
            long presenceId = 0;
            try
            {
            if(employeeId != 0)
            {
            presenceId = PstPresence.insertExc(presence);
            // delete dari database  
            transfer.deleteDataPresence(com.dimata.util.Formater.formatDate(tempPresDate,"MM-dd-yyyy"), String.valueOf(tr.get(1)), String.valueOf(tr.get(2)), String.valueOf(tr.get(3)));
            }
            }
            catch(Exception e)
            {
            System.out.println("Err : " + e.toString());   
            }
            // untuk check ke empSchedule
            // update presence (IN or OUT) on employee schedule  
            if(presenceId != 0)
            {
            long periodId = PstPeriod.getPeriodIdBySelectedDate(presence.getPresenceDatetime());                                
            int updatedFieldIndex = -1;  
            long updatePeriodId = periodId;
            Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdated(periodId, presence.getEmployeeId(), presence.getStatus(), presence.getPresenceDatetime());                                
            if(vectFieldIndex!=null && vectFieldIndex.size()==2)
            {
            updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
            updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));
            }
            int updateStatus = 0;  
            try
            {
            updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, presence.getEmployeeId(), updatedFieldIndex, presence.getPresenceDatetime());                    
            if(updateStatus>0)
            {
            presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
            PstPresence.updateExc(presence);                        
            }long employeeId = PstEmployee.getEmployeeByBarcode(String.valueOf(tr.get(2)));
            }
            catch(Exception e)
            {
            System.out.println("Update Presence exc : "+e.toString());
            }
            }     
             */

            }
        }
    }

    /**
     * Testing method
     */
    public static void main(String args[]) {
        TransferPresenceFromMdbTidex objTransferPresenceFromMdb = new TransferPresenceFromMdbTidex();

        
        
        if( false ) { // test get only
        Vector vectPresenceData = objTransferPresenceFromMdb.retrieveData(objTransferPresenceFromMdb.absenStation);
        if (vectPresenceData != null && vectPresenceData.size() > 0) {
            int maxPresence = vectPresenceData.size();
                System.out.println("Employee ID | Card ID |  EmployeeId  | Date " );
            for (int i = 0; i < maxPresence; i++) {
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
                System.out.println( employeeId +" | "+tr.sCardId+" | "+ strDate );

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
        
    //objTransferPresenceFromMdb.transferDataPresence("02");
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
        
        
        objTransferPresenceFromMdb.checkEmployeeList();
    }
    
    
    
}
