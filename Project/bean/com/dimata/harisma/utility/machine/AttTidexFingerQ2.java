/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.utility.machine;

import com.dimata.harisma.entity.employee.Employee;
import java.sql.*;
import com.dimata.harisma.entity.attendance.MachineTransaction;
import com.dimata.harisma.entity.attendance.PstMachineTransaction;
import com.dimata.util.Formater;
import java.util.Vector;

/**
 *
 * @author ktanjana
 */
public class AttTidexFingerQ2 implements AttTransfer_I, Runnable {
    private final static int STATUS_NEW=0;
    private final static int STATUS_PROCES=1;
    private boolean running=false;
    private long sleepMs=50;
    private String message="";
    // database ODBC
    String dsnName="";
    String user="";
    String password="";
    
    // Data untuk database machine
    String tableName="";   // nama table untuk database mesin
    String machineNumber=""; // field name - nomor mesin
    String employeeID="";    // field name - id karyawan di mesin data 
                            //in-out bisa berbeda dengan employee ID di databank harisma                             
    String checkTime ="";    // field name - waktu attendance
    String checkDate ="";    // field name - tanggal attendance 
    String checkType ="";    // field name - jenis IN, OUT, OUT-PERSONAL, IN-PERSONAL, OUT ON DUTY, CALLBACLsesuai 
                             // tergantung dari jenis mesin yang disupport pada saat masuk harisma akan di convert 
    String verifyCode = "";  // field name - apakah sukses di verify atau tidak
    String status ="";       // field name - status yang akan di set oleh harisma setelah data diambil : 
                             // dari 0=belum diambil harisma menjadi 1=sudah diambil
    String dataIDFld = "DATA_ID"; // field name - nomor mesin
    
    private int recordSize=0;
    private int progressSize=0;

    
    //update by satrya 2013-12-18
    java.util.Date startDate=null;
    java.util.Date endDate=null;
    int statusHr=STATUS_NEW;
    int changeAutomaticManualFinish=0;
    
     public AttTidexFingerQ2() {
            initClass(100, "AttTidexFingerQ2", "root", "123456", "absensi",
            "trans_pengenal", "trans_mesin", "trans_jam", "trans_status", "trans_flag", "STATUS");         
    }

          public void initClass(long sleepMs, String dsnName, String user, String password,
             String tableName, String employeeID, String machineNumber, String checkTime,
             String checkType, String verifyCode,  String status){
                      this.sleepMs = sleepMs;
        this.dsnName = dsnName;
        this.user = user;
        this.password = password;
        this.tableName =tableName;
        this.machineNumber=machineNumber;
        this.employeeID=employeeID;
        this.checkTime =checkTime;
        this.checkDate ="trans_tgl";
        this.checkType =checkType;
        this.verifyCode = verifyCode;
        this.status =status;        

          }
    /**
     * <pre>create by satrya 2013-12-18</pre> Keterangan: untuk parameter search
     * pencarian DB
     *
     * @param startDate
     * @param endDate
     * @param statusHr
     * @param changeAutomaticManualFinish
     */
    public void searchDBMachine(java.util.Date startDate, java.util.Date endDate, int statusHr, int changeAutomaticManualFinish) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusHr = statusHr;
        this.changeAutomaticManualFinish = changeAutomaticManualFinish;
    }
    
                             //update by priska 2016-02-16
    java.util.Date dateFrom = null;
    java.util.Date dateTo = null;
    public void setDateFromTo(java.util.Date dateFrom, java.util.Date dateTo) {
     this.dateFrom=dateFrom;
     this.dateTo=dateTo;
    }
    
  public int initRecordToGet(){
    Connection theConn=null;
    int theSize=0;
    try {
      // connection to an ACCESS MDB
      theConn = ODBCConnection.getConnection(dsnName, user, password);

      ResultSet rs;
      Statement stmt;
      String sql;

      sql =  "select count("+this.employeeID+") as size from " + this.tableName+" where "+
              //update by satrya 2012-08-16
              //this.status+"="+STATUS_NEW+" OR "+ this.status+" IS NULL ";
              this.status+"="+STATUS_NEW+" OR "+ this.status+" IS NULL ORDER BY " + this.checkDate +"," +this.checkTime;
      stmt = theConn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
         theSize=rs.getInt("size");
      }
      rs.close();
      stmt.close();
    }
    catch (Exception e) {
        e.printStackTrace();
    }
    finally {
      try {
        if (theConn != null) theConn.close();
      }
      catch (Exception e) {
      }
    }
 return theSize;
    }
  

    public void run() {

        this.setRunning(true);
        System.out.println("Process transfer data mesin dimulai");
       
        Connection getConn=null;
       
        try{
           getConn = ODBCConnection.getConnection(dsnName, user, password);
         
        } catch(Exception exc){
           message="Error on connection to database DSN= "+dsnName;
           return;
        }

        while(this.running)
        {
            try {
                Thread.sleep(this.getSleepMs()*30);
                // get records dari table mesin
                int limit=50;
                  ResultSet rs;
                  Statement stmt;
                
                  String sql;
                  
                /*String sql = "SELECT " + Att2000.Fld_InOut_USERID + ","
                        + Att2000.Fld_InOut_CHECKTIME + ","
                        + Att2000.Fld_InOut_CHECKTYPE + ","
                        + Att2000.Fld_InOut_VERIFYCODE + ","
                        + Att2000.Fld_InOut_SENSORID + ","
                        + Att2000.Fld_InOut_WorkCode + ","
                        + Att2000.Fld_InOut_sn
                        + " FROM " + Att2000.Tbl_CheckInOut + " WHERE "
                        + Att2000.Fld_InOut_STATUS + " IS NULL OR " + Att2000.Fld_InOut_STATUS + " = '" + Att2000.Status_Not_Transfered + "'";
                  */

                  sql =  "select "+this.employeeID+","+ this.checkDate+","+ this.checkTime+","+this.checkType+
                         ","+this.dataIDFld+","+this.machineNumber+" from " + this.tableName+" where "+
                          //update by satrya 2012-08-16
                          // this.status+"='"+STATUS_NEW+"' OR "+ this.status+" IS NULL ";
                          this.status+"='"+STATUS_NEW+"' OR "+ this.status+" IS NULL ORDER BY " + this.checkDate +"," +this.checkTime;
                  //System.out.println(sql);
                  if(jTextArea!=null){
                      jTextArea.setText(sql);
                  }
                  stmt = getConn.createStatement();
                  message="Run query on table machine "+this.tableName;
                  rs = stmt.executeQuery(sql);
                  String dataEmployee="";
                  String dataCheckDate="";
                  String dataCheckTime="";
                  String dataCheckType="";
                  String dataMachineNumber="";
                  MachineTransaction machTrans = new MachineTransaction();
                  long dataID =0;                  
                  int theSize=initRecordToGet();
                  //update by satrya 2012-06-18
                  //System.out.println(Formater.formatDate(new java.util.Date(), "hh:mm:ss")+ " theSize="+theSize);
                  //update by satrya 2012-09-28
                  System.out.println(Formater.formatDate(new java.util.Date(), "HH:mm:ss")+ " theSize="+theSize);
                   if(theSize>0){
                    recordSize =  theSize;
                    progressSize=0;
                }
                  while (theSize>0 && rs.next() && this.running) {
                   
                      //System.out.println(recordSize);
                        Thread.sleep(this.getSleepMs());
                     dataEmployee=rs.getString(this.employeeID);
                     dataCheckDate=rs.getString(this.checkDate);
                     dataCheckTime=rs.getString(this.checkTime);                     
                     dataCheckType=rs.getString(this.checkType);
                     dataMachineNumber=rs.getString(this.machineNumber);
                     dataID = rs.getLong(this.dataIDFld);
                     if(dataEmployee!=null && dataEmployee.length()<4){
                         String fill ="0000";
                         dataEmployee= fill.substring(0, 4-dataEmployee.length()) + dataEmployee;
                     }
                     machTrans.setCardId(dataEmployee);
                     //update by satrya 2012-06-18
                     // machTrans.setDateTransaction(dataCheckDate+" "+dataCheckTime,"yyyy-MM-dd hh:mm:ss"
                     machTrans.setDateTransaction(dataCheckDate+" "+dataCheckTime,"yyyy-MM-dd HH:mm:ss");
                     machTrans.setMode(dataCheckType);
                     machTrans.setStation(""+dataMachineNumber);                     
                     int countTrans =0;
                     try{                       
                       countTrans = PstMachineTransaction.getCount(machTrans);
                     } catch(Exception exc){
                           System.out.println("EXCEPTION : " + exc);                         
                     }
                     long idTrans=0;
                     if(countTrans<1){
                     try{            
                         //MachineTransaction existingTrans = PstMachineTransaction.fetchBy(machTrans.getCardId(), 
                         //        machTrans.getDateTransaction(), machTrans.getMode(),  machTrans.getStation());                         
                          //  idTrans = existingTrans.getOID();
                         //if(idTrans==0){
                            idTrans=PstMachineTransaction.insertExc(machTrans);
                         //}else{
                             message = "Data insert : "+ machTrans.getCardId()+";"+
                                 machTrans.getDateTransaction()+";"+machTrans.getMode()+";"+  machTrans.getStation();
                             //System.out.println(message);
                         //} 
                      }
                     catch(Exception exc2){
                           System.out.println("EXCEPTION :" + exc2);
                     }
                     }
                     if(idTrans!=0 || countTrans > 0){
                         if(countTrans>0){
                             message = "Data exists : "+ machTrans.getCardId()+";"+
                                 machTrans.getDateTransaction()+";"+machTrans.getMode()+";"+  machTrans.getStation();                             
                             System.out.println(message);
                         }
                         try{
                            //System.out.println("update");
                              updateODBC_Q(""+dataID, dataEmployee, dataCheckDate, dataCheckTime, dsnName);
                              progressSize++;
                         Thread.sleep(this.getSleepMs());}

                         catch  (Exception exc2) {
                System.out.println(" Update Failed"+ exc2);
            }
                     }

                   
                  }
                 
                  rs.close();
                  stmt.close();

                // simpan ke hr_machine transaction
                // set record dari table mesin , sudah diambil dan disimpan
            } catch (Exception exc) {
                System.out.println("EXCEPTION :" + exc);
            }
        }

        this.running = false;
        

    }

    /**
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * @return the progressSize
     */
    public int getProgressSize() {
        return progressSize;
    }

    /**
     * @return the recordSize
     */
    public int getRecordSize() {
       // System.out.println("=============================+++++++++======================");
        //System.out.println(recordSize);
        return recordSize;

    }

    public  boolean updateODBC(String pkData, String userid, String dataCheckTime, String db_access) {
        Connection cn = null;
        Statement stmt = null;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);
                    this.checkTime =checkTime;
        this.checkDate ="trans_tgl";
        String dataCheckDate="";
        
            String qry = "UPDATE "+this.tableName+" SET STATUS='" + STATUS_PROCES + "' WHERE "+this.employeeID+"=" + userid + 
                    " AND "+this.checkDate+"= #" + dataCheckDate + "#" +
                    " AND "+this.checkTime+"= #" + dataCheckTime + "#";
            stmt = cn.createStatement();
            stmt.executeUpdate(qry);
            return true;
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } finally {
            try {
                stmt.close();
                cn.close();
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }
        }

        return false;
    }


    public  boolean updateODBC_Q(String pkData, String userid, String dataCheckDate, String dataCheckTime, String db_access) {
        Connection cn = null;
        Statement stmt = null;
        String qry ="";
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs);
                    this.checkTime =checkTime;
        this.checkDate ="trans_tgl";               
            qry = "UPDATE "+this.tableName+" SET STATUS=" + STATUS_PROCES + " WHERE "+this.dataIDFld+"="+pkData;
                    /*" AND "+this.checkDate+"= \"" + dataCheckDate + "\"" +
                    " AND "+this.checkTime+"= \"" + dataCheckTime + "\"";*/
            stmt = cn.createStatement();
            stmt.executeUpdate(qry);
            return true;
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString()+ "\n QUERY= "+qry);
        } finally {
            try {
                stmt.close();
                cn.close();
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }
        }

        return false;
    }

    /**
     * @return the sleepMs
     */
    public long getSleepMs() {
        return sleepMs;
    }

    /**
     * @param sleepMs the sleepMs to set
     */
    public void setSleepMs(long sleepMs) {
        this.sleepMs = sleepMs;
    }

    private javax.swing.JProgressBar jProgressBar=null;
    public void setProgressBar(javax.swing.JProgressBar jProgressBar){
        this.jProgressBar = jProgressBar;
    }
    
    javax.swing.JTextArea jTextArea=null;
    public  void setTextArea(javax.swing.JTextArea jTextAreaPar){
         jTextArea = jTextAreaPar;
     }

    public String getMessage() {
        return message;
    }

    public Vector<Employee> getEmployees() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
     public void initDBConfig(DBMachineConfig dbConfig) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
     
   

}
