/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.machine;

import com.dimata.harisma.entity.attendance.MachineTransaction;
import com.dimata.harisma.entity.attendance.PstMachineTransaction;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.util.Formater;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class AttFingerSpotAbsoluteSeries implements AttTransfer_I, Runnable {
    private final static int STATUS_NEW=0;
    private final static int STATUS_PROCES=1;
    private boolean running=false;
    private long sleepMs=50;
    private String message="";
     // database ODBC
    String dsnName="localhost:3306";/// 205.101.0.214:3306 alamat tmpt mysql
    String user="root";//profinger
    String password="dsj4kasir2";//""
    String dbName="ftm";//melia
   
    // Data untuk database machine
    String tableName="att_log";   // nama table untuk database mesin
    String machineNumber=""; // field name - nomor mesin
    String employeeID="";    // field name - id karyawan di mesin data 
                            //in-out bisa berbeda dengan employee ID di databank harisma                             
    String checkTime ="";    // field name - waktu attendance
    String checkDate ="";    // field name - tanggal attendance 
    String checkType ="io_mode";    // field name - jenis IN, OUT, OUT-PERSONAL, IN-PERSONAL, OUT ON DUTY, CALLBACLsesuai 
                             // tergantung dari jenis mesin yang disupport pada saat masuk harisma akan di convert 
    String verifyCode = "";  // field name - apakah sukses di verify atau tidak
    String status ="";       // field name - status yang akan di set oleh harisma setelah data diambil : 
                             // dari 0=belum diambil harisma menjadi 1=sudah diambil
    String dataIDFld = "att_id"; // ID dari att_log(mesin absennya)
   
    private int recordSize=0;
    private int progressSize=0;

    
    //update by satrya 2013-12-18
    java.util.Date startDate=null;
    java.util.Date endDate=null;
    int statusHr=STATUS_NEW;
    int changeAutomaticManualFinish=0;
    //update by devin 2014-01-13
    int flagSdhSelesaiMencariDataTgl=1;
    int flagSdhSelesaiMencariDataTglStatus=1;
    int flagSdhSelesaiCountMencariDataTgl=1;
     int flagSdhSelesaiCountStatus=1;
   
     public AttFingerSpotAbsoluteSeries() {
            initClass(100, "AttFingerSpotAbsoluteSeries", user,password, tableName,
            "pin", "sn", "scan_date", "io_mode", "verify_mode", "STATUS"); 
            //  initClass(100, "AttFingerSpotAbsoluteSeries", "profinger", "", "att_log",
            //"pin", "sn", "scan_date", "io_mode", "verify_mode", "STATUS"); 
    }

          public void initClass(long sleepMs, String ClassName, String user, String password,
             String tableName, String employeeID, String machineNumber, String checkTime,
             String checkType, String verifyCode,  String status){
                      this.sleepMs = sleepMs;
        //this.dsnName = dsnName;
        //this.dbName=dbName;
        this.user = user;
        this.password = password;
        this.tableName =tableName;
        this.machineNumber=machineNumber;
        this.employeeID=employeeID;
        this.checkTime =checkTime;
        this.checkDate ="scan_date";
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
        //update by devin 2014-01-13
        this.flagSdhSelesaiMencariDataTgl=1;
        this.flagSdhSelesaiMencariDataTglStatus=1;
        this.flagSdhSelesaiCountMencariDataTgl=1;
        this.flagSdhSelesaiCountStatus=1;
    }
        public void initDBConfig(DBMachineConfig dbConfig) {
        /*if(dbConfig!=null){
            this.dsnName= "//"+dbConfig.getDsn()+":"+dbConfig.getPort()+"/";
            this.user = dbConfig.getUser();
            this.password = dbConfig.getPwd();
        }*/
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
      theConn = MYSQLConnection.getConnection(dsnName,dbName, user, password);

      ResultSet rs;
      Statement stmt;
      String sql;

      sql =  "select count("+this.dataIDFld+") as size from " + this.tableName+" where ";//+
              //"select count("+this.employeeID+") as size from " + this.tableName+" where "+
              //update by satrya 2012-08-16
              //this.status+"="+STATUS_NEW+" OR "+ this.status+" IS NULL ";
             // this.status+"="+STATUS_NEW+" OR "+ this.status+" IS NULL ORDER BY " + this.checkDate +"," +this.checkTime;
              //update by devin 2014-01-06
              if(startDate!=null && endDate!=null && flagSdhSelesaiCountMencariDataTgl>0){
                  
                      sql = sql + checkDate +" between  \"" +Formater.formatDate(startDate, "yyyy-MM-dd 00:00:00")+ "\" AND \""+Formater.formatDate(endDate, "yyyy-MM-dd 23:59:59")+"\" and ";
                      if(changeAutomaticManualFinish>0){
                        flagSdhSelesaiCountMencariDataTgl=0;
                      }
                   }
                   if(flagSdhSelesaiCountStatus>0){
                       sql = sql + this.status + (statusHr==STATUS_ALL ? " in("+STATUS_NEW+","+STATUS_PROCES+")":"="+statusHr);
                   }else{
                       sql = sql + this.status +  "=" + STATUS_NEW + " OR " + this.status + " IS NULL";
                   }
                   if(changeAutomaticManualFinish>0){
                     flagSdhSelesaiCountStatus=0;
                   }
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
        //System.out.println("Exception initRecordToGet"+e);
    }
    finally {
      try {
        if (theConn != null) theConn.close();
      }
      catch (Exception e) {
           //System.out.println("Exception initRecordToGet"+e);
      }
    }
 return theSize;
    }
  

    public void run() {

        this.setRunning(true);
        System.out.println("Process transfer data mesin dimulai 1");
       
        Connection getConn=null;
       
        try{
             //System.out.println("Process Coneksi ke mesin");
           getConn = MYSQLConnection.getConnection(dsnName,dbName, user, password);
         
        } catch(Exception exc){
           message="Error on connection to database "+dbName + " No Database Exis OR"+ dsnName;
            System.out.println("Process Coneksi ke mesin"+exc);
           return;
        }
         //System.out.println("Status running"+running);
        //update by devin 2014-01-15
          ResultSet rs=null;
      Statement stmt= null;
        while(this.running)
        {
            try {
                Thread.sleep(this.getSleepMs()*30);
                // get records dari table mesin
                int limit=50;
                
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

                  sql =  "select "+this.employeeID+","+ this.checkDate+","+this.checkType+
                         ","+this.dataIDFld+","+this.machineNumber+" from " + this.tableName+" where ";
                          //update by satrya 2012-08-16
                          // this.status+"='"+STATUS_NEW+"' OR "+ this.status+" IS NULL ";
                          //update by devin 2014-01-13 +this.status+"='"+STATUS_NEW+"' OR "+ this.status+" IS NULL ORDER BY " + this.checkDate +"," +this.checkTime;
                           if(startDate!=null && endDate!=null && flagSdhSelesaiMencariDataTgl>0){
                      sql = sql + checkDate+ " BETWEEN \""+Formater.formatDate(startDate, "yyyy-MM-dd 00:00:00")+"\" AND \""+Formater.formatDate(endDate, "yyyy-MM-dd 23:59:59")+"\""
                              +" AND ";
                      if(changeAutomaticManualFinish>0){
                      flagSdhSelesaiMencariDataTgl=0;
                      }
                   }
                   if(flagSdhSelesaiMencariDataTglStatus>0){
                       sql = sql + this.status + (statusHr==STATUS_ALL ? " in("+STATUS_NEW+","+STATUS_PROCES+")":"="+statusHr);
                   }else{
                       sql = sql + this.status +  "=" + STATUS_NEW + " OR " + this.status + " IS NULL";
                   }
                   if(changeAutomaticManualFinish>0){
                     flagSdhSelesaiMencariDataTglStatus=0;
                   }
                        sql = sql + " ORDER BY " + this.checkDate;
                
                  //System.out.println(sql);
                  if(jTextArea!=null){
                      jTextArea.setText(sql);
                  }
                  //System.out.println("Status running"+sql);
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
                  //System.out.println(Formater.formatDate(new java.util.Date(), "HH:mm:ss")+ " theSize="+theSize);
                   if(theSize>0){
                    recordSize =  theSize;
                    progressSize=0;
                }else {
                    recordSize = 0;
                    progressSize = 0;
                }
                   int updateCount =0;
                String inPkData = "" ; //   update HIstory_backup set STATUS=1 where EventSeqId in ( 1213, 12134 )
                  while (theSize>0 && rs.next() && this.running) {
                   
                      //System.out.println(recordSize);
                        Thread.sleep(this.getSleepMs());
                     dataEmployee=rs.getString(this.employeeID);
                     dataCheckDate=rs.getString(this.checkDate);
                     dataCheckTime=rs.getString(this.checkTime);                     
                     dataCheckType=rs.getString(this.checkType);
                     dataMachineNumber=rs.getString(this.machineNumber);
                     dataID = rs.getLong(this.dataIDFld);
                    /* if(dataEmployee!=null && dataEmployee.length()<4){
                         String fill ="0000";
                         dataEmployee= fill.substring(0, 4-dataEmployee.length()) + dataEmployee;
                     }*/
                      //System.out.println("Status running dataEmployee"+dataEmployee);
                     // System.out.println("Status running dataEmployee"+dataCheckDate);
                  try{   
                     machTrans.setCardId(dataEmployee);
                     //update by satrya 2012-06-18
                     // machTrans.setDateTransaction(dataCheckDate+" "+dataCheckTime,"yyyy-MM-dd hh:mm:ss"
                     machTrans.setDateTransaction(dataCheckDate+" "+dataCheckTime,"yyyy-MM-dd HH:mm:ss");
                     machTrans.setMode(dataCheckType);
                     machTrans.setStation(""+dataMachineNumber);                     
                  }catch(Exception exc){
                      System.err.println(exc); 
                        continue;
                  }   
                     /*int countTrans =0;
                     try{                       
                       countTrans = PstMachineTransaction.getCount(machTrans);
                     } catch(Exception exc){
                           System.out.println("EXCEPTION : " + exc);                         
                     }*/
                     long idTrans=0;
                     
                     ///update by satrya 2013-05-6
                      //long stdInsertIntell = System.currentTimeMillis();
                     //if(countTrans<1){
                     try{            
                          //if(machTrans.getCardId()!=null){
                         MachineTransaction existingTrans = PstMachineTransaction.fetchBy(machTrans.getCardId(), 
                               machTrans.getDateTransaction(), machTrans.getMode(),  machTrans.getStation());                         
                          idTrans = existingTrans.getOID();
                         if(idTrans==0){
                            idTrans=PstMachineTransaction.insertExc(machTrans);
                         //}else{
                             message = "Data insert : "+ machTrans.getCardId()+";"+
                                 machTrans.getDateTransaction()+";"+machTrans.getMode()+";"+  machTrans.getStation();
                             System.out.println(message);
                         }else{
                              message = "Data exists : "+ machTrans.getCardId()+";"+
                                 machTrans.getDateTransaction()+";"+machTrans.getMode()+";"+  machTrans.getStation();                             
                             System.out.println(message);
                         }
                          // }
                        //update by devin 2014-01-13
                        
                      }catch(Exception exc2){
                           System.out.println("EXCEPTION :" + exc2);
                     }
                       // System.out.print("Insert Itelliscanv ke hr_machine_trans: "+(System.currentTimeMillis()- stdInsertIntell));
                    // }
                    /* if(idTrans!=0 || countTrans > 0){
                         
                         try{
                            //System.out.println("update");
                              updateODBC_Q(""+dataID, dataEmployee, dataCheckDate, dataCheckTime, dbName);
                              //System.out.println("Time Update Itelliscanv  TO ODBC: "+(System.currentTimeMillis()- stdUpIntell));
                              progressSize++;
                         Thread.sleep(this.getSleepMs());}

                         catch  (Exception exc2) {
                System.out.println(" Update Failed"+ exc2);
                        }
                     }*/
                     ///xxxx
                      if (idTrans != 0) {
                        //java.util.Date startUp = new java.util.Date();
                        //long stdUpIntell = System.currentTimeMillis();
                        try {
                            //update by satrya 2012-07-10
                                updateCount=updateCount+1;
                                inPkData=inPkData+""+dataID +",";
                                                            
                            //System.out.println("update Intell ");
                            if(updateCount==limit  || theSize <= updateCount || (this.progressSize+1)==theSize){
                                //updateODBC(pkData, dataemployeeId, dataeventDateTime, dsnName);
                                inPkData=inPkData+""+dataID;
                                updateODBCBatch(inPkData, dsnName);
                                updateCount=0;
                                inPkData ="";
                                //System.out.println("Time Update Itelliscanv  TO ODBC: "+(System.currentTimeMillis()- stdUpIntell));
                                message = " Update Status = 1 in mesin absence"+inPkData;
                            }/*else{
                                updateCount=updateCount+1;
                                inPkData=inPkData+""+pkData +",";
                                //System.out.println("Update Itelliscanv : "+(System.currentTimeMillis()- stdUpIntell));
                                
                            }*/
                            System.out.println(message);
                            progressSize++;
                            if(this.progressSize==theSize){
                                this.progressSize=0;
                            }
                            Thread.sleep(this.getSleepMs());
                        } catch (Exception exc2) {
                            System.out.println("Update Failed" + exc2);
                        }
                        //java.util.Date endUp = new java.util.Date();
                        //System.out.print(endUp.getTime() -  startUp.getTime());
                       // System.out.println("Time Update Status Itelliscanv : "+(System.currentTimeMillis()- stdUpIntell));
                    }

                   
                  }
                 

                   //update by devin 2014-01-13
               if(changeAutomaticManualFinish==0){
                      this.running = false;
                        message = "Transfer Stop";
                   }
                // simpan ke hr_machine transaction
                // set record dari table mesin , sudah diambil dan disimpan
            } catch (Exception exc) {
                System.out.println("EXCEPTION :" + exc);
            }//update by devin 2014-01-15
            finally {
                try{
                rs.close();
                stmt.close();
            }
                catch (Exception exc){
                    System.out.println(exc);
        }
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
            cn = MYSQLConnection.getConnection(dsnName,dbName, user, password);
                    this.checkTime =checkTime;
       // this.checkDate ="trans_tgl";
        String dataCheckDate="";
        
            String qry = "UPDATE "+this.tableName+" SET STATUS='" + STATUS_PROCES + "' WHERE "+this.dataIDFld+"=" + userid + 
                    //String qry = "UPDATE "+this.tableName+" SET STATUS='" + STATUS_PROCES + "' WHERE "+this.employeeID+"=" + userid + 
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


    public boolean updateODBCBatch(String inPkData, String db_access) {
        Connection cn = null;
        Statement stmt = null;
        try {
            cn = MYSQLConnection.getConnection(dsnName,dbName, user, password);
                   // this.checkTime =checkTime;
           //cn = DriverManager.getConnection(cs, user, password);
            String qry = "UPDATE "+this.tableName+" SET "+this.status+"="+STATUS_PROCES +" WHERE "+ 
                     this.dataIDFld + " IN ( "+ inPkData + ")";
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
/*==========================================================================XXXX=========*/
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
    
    

   
}
