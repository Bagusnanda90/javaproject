/*
 * WatcherMachine.java
 * @author  artha
 * Created on December 12, 2008, 7:40 PM
 */

package com.dimata.harisma.utility.machine.dcanteen;

import com.dimata.harisma.entity.attendance.MachineTransaction;
import com.dimata.harisma.entity.attendance.PstMachineTransaction;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.utility.machine.I_Machine;
import com.dimata.harisma.utility.machine.MachineBroker;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.harisma.session.attendance.SessMachineTransaction;
import com.dimata.harisma.utility.machine.Att2000;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.*;
import java.sql.*;



public class WatcherMachineCanteen implements Runnable {

    int i = 0;
    String machineNumber = "01";
    String[] machineNumbers;
    public static final int STATUS_PROCES = 1;
    
    public WatcherMachineCanteen() {
    }
    
    public void run() {        
        
        System.out.println(".:: [Watcher Machine] started ....................");  
        machineNumber = String.valueOf(PstSystemProperty.getValueByName("CANTEEN_TMA_NO"));
        StringTokenizer strTokenizer = new StringTokenizer(machineNumber,",");
        machineNumbers = new String[strTokenizer.countTokens()];
        int count = 0;
        while(strTokenizer.hasMoreTokens()){
            machineNumbers[count] = strTokenizer.nextToken();
            System.out.println("ABSEN MACHINE :::::::::: "+machineNumbers[count]);
            count ++;
        }
        while (ServiceManagerMachineCanteen.running) {            
            try {
                // proses download data from machine          
                process();   // sleeping time for next process
                long TIMEKEEPING_INTERVAL = Long.parseLong(PstSystemProperty.getValueByName("TIMEKEEPING_INTERVAL"));                
                int sleepTime = (int) (TIMEKEEPING_INTERVAL * 60 * 1000);
                System.out.println(".:: [Watcher Machine] finished, service will sleep for "+TIMEKEEPING_INTERVAL+" minutes\r");                
                Thread.sleep(sleepTime);                
            } catch (Exception e) {
                System.out.println("\t[Watcher Machine] interrupted with message : " + e);
            }
        }        
    }

    public void process() {     
        
        String propertyMchneCanteen = "";
        
        try{
            propertyMchneCanteen = PstSystemProperty.getValueByName("MACHINE_CANTEEN");
        }catch(Exception E){
            System.out.println("PROPERTY MACHINE_CANTEEN TIDAK DITEMUKAN");
            propertyMchneCanteen = "";
        }
        
        /* PENGECEKAN UNTUK KEBERADAAN MESIN CANTEEN (OK -> MESIN CANTEEN ADA)  */
        if(propertyMchneCanteen.equals("ok")){
         
            TransactionAccessCanteen();
            
            SessMachineTransaction.analistPresentAll();
            
        }
        
        System.out.println("\r     -> [Watcher Machine] process #" + i + " @ " + new Date().toLocaleString());
        i++;

        // Watcher machine
        System.out.println("-----------------------------------------------------------");
        for(int ic=0;ic<machineNumbers.length;ic++){
            String msg = "";
            try {
                Vector vData = new Vector(1, 1);
                I_Machine i_Machine = MachineBroker.getMachineByNumber(machineNumbers[ic]);
                if (i_Machine.processCheckMachine()) {
                    vData = i_Machine.processDownloadTransaction();
                    int countVal = 0;
                   /* for (int id = 0; id < vData.size(); id++) {
                        Vector vTemp = (Vector) vData.get(id);
                        String strVal = (String) vTemp.get(1);
                        if (strVal.equals("true")) {
                            countVal++;
                        }
                    }*/
                    msg ="[RESULT]  "+ vData.size() + " transaction(s) downloaded from Machine-" + i_Machine.getMachineNumber();
                    if (vData.size() > 0) {
                        msg += "\n-->  Valid data download     : " + String.valueOf(vData.size());
                       // msg += "\n-->  Not Valid data download : " + String.valueOf(vData.size() - countVal);
                    }
                } else {
                    msg = "[ERROR]  Unable to download from Machine-" + i_Machine.getMachineNumber() + "";
                }
            } catch (Exception ex) {
                msg = "[ERROR]  Unable to download from Machine-" + machineNumbers[ic] + ":: Cek koneksi ke mesin!";
                ex.printStackTrace();
            }
            System.out.println(msg);
        }
              
    }
    
    /**
     * @Author  Roy Andika
     * @param   str_time
     * @Desc    Untuk Mendapatkan tanggal machine transaction, dimana intputannya String dan diubah menjadi format Date
     * @return  Date
     * @throws  java.lang.Exception
     */
    private static Date getMachineDate(String str_time) throws Exception{
        
        Date date = new Date();
        if(str_time.length()>0){
            
           int tYear = Integer.parseInt(str_time.substring(0,4))-1900;
           int tMonth = Integer.parseInt(str_time.substring(5,7))-1;
           int tDate = Integer.parseInt(str_time.substring(8,10));
           int tHour = Integer.parseInt(str_time.substring(11,13));
           int tMin = Integer.parseInt(str_time.substring(14,16));
           return new Date(tYear, tMonth, tDate, tHour, tMin);
           
        }
        return date;
    }
    
    /**
     * @Author  Roy Andika
     * @Desc    Untuk mendapatkan no machine fingger spot
     * @param   SensorId
     * @return
     */
    public static String getNoMachine(String SensorId) {

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stm = null; 
        
        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);

            String qry = "SELECT "+Att2000.Fld_MachineAlias+" FROM "+Att2000.Tbl_Machines+
                    " WHERE " + Att2000.Fld_MachineNumber+" = "+SensorId;

            stm = cn.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {

                String barcodeNomor = rs.getString(1);

                return barcodeNomor;

            }
            
            rs.close();

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }finally{
            try{
                stm.close();
                cn.close();
            }catch(Exception e){
                System.out.println("EXCEPTION " + e.toString());
            }
        }

        return null;

    }
    
    /**
     * @Author  Roy Andika
     * @Desc    Untuk mendapatkan barcode number dari database access
     * @param   userid
     * @return
     */
    public static String getBarcodeNo(String userid) {

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stm = null;
        
        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);

            String qry = "SELECT Badgenumber FROM USERINFO WHERE USERID = " + userid;

            stm = cn.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {

                String barcodeNomor = rs.getString(1);

                return barcodeNomor;

            }
            
            rs.close();

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally{
            try{
                stm.close();
                cn.close();
            }catch(Exception e){
                System.out.println("EXCEPTION " + e.toString());
            }
        }

        return null;

    }
    
    /**
     * @Author  Roy Andika
     * @Desc    Untuk mengupdate database Access, dengan memberikan flag 1 pada kolom status dimana record sudah diambil
     * @param   userid
     * @param   checktime
     * @return
     */
    public static boolean updateODBC(String userid, String checktime) {
        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            System.out.println("Exception " + e.toString());
        }
        
        Connection cn = null;
        Statement stmt = null;
        
        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);

            String qry = "UPDATE CHECKINOUT SET STATUS='" + STATUS_PROCES + "' WHERE USERID=" + userid + " AND CHECKTIME= #" + checktime + "#";

            stmt = cn.createStatement();
            stmt.executeUpdate(qry);

            return true;

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }finally{
            try{
                stmt.close();
                cn.close();
            }catch(Exception e){
                System.out.println("EXCEPTION " + e.toString());
            }
        }

        return false;
    }
    
    /**
     * @Author  Roy Andika
     * @Desc    Untuk mengambil data dari mesin fingger spot, kemudian memasukan ke database mesin transaction
     *          dan selanjutnya melakukan proses analisis     
     */  
    
    public static Vector TransactionAccessCanteen(){

        String db_access = "";

        try {
            db_access = PstSystemProperty.getValueByName("DATA_BASE_FN");
        } catch (Exception e) {
            db_access = "";
            System.out.println("Exception " + e.toString());
        }

        Connection cn = null;
        Statement stm = null;
        
        /* mengecek keberadaan database */
        if (!db_access.equals("")) {

            try {

                /* Configurasi database access */
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                String cs = "jdbc:odbc:" + db_access;
                cn = DriverManager.getConnection(cs);
                
                String sql = "SELECT "+Att2000.Fld_InOut_USERID+","+
                            Att2000.Fld_InOut_CHECKTIME+","+
                            Att2000.Fld_InOut_CHECKTYPE+","+
                            Att2000.Fld_InOut_VERIFYCODE+","+
                            Att2000.Fld_InOut_SENSORID+","+ 
                            Att2000.Fld_InOut_WorkCode+","+
                            Att2000.Fld_InOut_sn+
                            " FROM "+Att2000.Tbl_CheckInOut+" WHERE "+
                            Att2000.Fld_InOut_STATUS+" IS NULL OR "+Att2000.Fld_InOut_STATUS+" = '"+Att2000.Status_Not_Transfered+"'";

                stm = cn.createStatement();
                ResultSet rs = stm.executeQuery(sql);

                System.out.println(" --------------------------------------------------------------------");
                System.out.println(" --------------- DOWNLOAD DATA FINGGERSPOT CANTEEN ------------------");
                System.out.println(" --------------------------------------------------------------------");

                int no = 1;                

                while (rs.next()) {                    

                    String userId = rs.getString(Att2000.Fld_InOut_USERID);
                    
                    String str_time = rs.getString(Att2000.Fld_InOut_CHECKTIME);
                    
                    String CHECKTYPE = rs.getString(Att2000.Fld_InOut_CHECKTYPE);
                    
                    String idMesin = rs.getString(Att2000.Fld_InOut_SENSORID);
                   
                    Date time = getMachineDate(str_time);
                    
                    String barcodeNomor = getBarcodeNo(userId);

                    /* untuk megecek keberadaan employee */

                    String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER] + "='" + barcodeNomor + "' AND " +
                            PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = " + PstEmployee.NO_RESIGN;

                    Vector EmpMatch = PstEmployee.list(0, 0, whereClause, null);

                    /* jika employee ada */

                    if (EmpMatch != null && EmpMatch.size() > 0){

                        Employee employee = new Employee();

                        employee = (Employee) EmpMatch.get(0);

                        int idxSwap = str_time.indexOf(" ");

                        String date = str_time.substring(0, idxSwap);

                        /* untuk mengecek keberadaan periode */
                        long periodId = PstPeriod.getPeriodIdBySelectedDateString(date);

                        if (periodId != 0) {

                            String mode = Att2000.mode_in;  /* default */
                            
                            if(CHECKTYPE.equals(Att2000.CheckType_CheckIn)){
                                mode = Att2000.mode_in;
                            }else if(CHECKTYPE.equals(Att2000.CheckType_CheckOut)){
                                mode = Att2000.mode_out;
                            }else{
                                mode = Att2000.mode_in;
                            }
                            
                            String station = "0";
                            station = getNoMachine(idMesin);
                            
                            if(station.equals(null)){
                                station = "0";
                            }
                            
                            MachineTransaction machineTransaction = new MachineTransaction();
                            machineTransaction.setStation(station);
                            machineTransaction.setCardId(employee.getBarcodeNumber());
                            machineTransaction.setMode(mode);
                            machineTransaction.setDateTransaction(time);
                            machineTransaction.setPosted(PstMachineTransaction.POSTED_STATUS_OPEN);
                            
                            long machine_id = 0;
                            
                            try {
                                machine_id = PstMachineTransaction.insertExc(machineTransaction);
                            } catch (Exception e) {
                                System.out.println("Exception " + e.toString());
                            }

                            if(machine_id != 0){                                
                                
                                updateODBC(userId, str_time);
                                System.out.println(" < SUKSES >" + no + " USER ID >>>> " + barcodeNomor + " TIME >>>> " + time+" \n");
                                no++;
                                
                            }else{
                                
                                System.out.println(" < FAILD >" + no + " USER ID >>>> " + barcodeNomor + " TIME >>>> " + time+" \n");
                                
                            }
                        }
                    }
                }
                
                rs.close();
                
                System.out.println(" --------------------------------------------------------------------");
                System.out.println(" --------------------------- END DOWNLOAD ---------------------------");
                System.out.println(" --------------------------------------------------------------------");               
                
                
            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            } finally{
                try{
                    stm.close();
                    cn.close();
                }catch(Exception e){
                    System.out.println("Exception " + e.toString());
                }
            }

        }
        return null;
    }
    
    
}
