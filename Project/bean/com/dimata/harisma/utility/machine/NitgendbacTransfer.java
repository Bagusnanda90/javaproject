/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.utility.machine;
import com.dimata.harisma.entity.employee.Employee;
import java.sql.*;
import com.dimata.harisma.entity.attendance.MachineTransaction;
import com.dimata.harisma.entity.attendance.PstMachineTransaction;
import java.util.Vector;

/**
 *
 * @author mukti
 */
public class NitgendbacTransfer  implements AttTransfer_I, Runnable
{
    private boolean running=false;
    private long sleepMs=100;
    private String message="";

    // database ODBC
    String dsnName="";
    String user="";
    String password="";

    //table NGAC_LOG
    String tableName="";
    String machineNumber="";
    String logTime="";
    String checkType="";
    String employeeID="";
    String status="";
     private int recordSize=0;
    private int progressSize=0;
    String primaryKey = "logindex";
      ///update by satrya 20121106
    int maxCardId=5; // panjang card id di machine


    
    //update by satrya 2013-12-18
    java.util.Date startDate=null;
    java.util.Date endDate=null;
    int statusHr=STATUS_NEW;
    int changeAutomaticManualFinish=0;
      
    public NitgendbacTransfer ()
    {
        initClass(10,"NitgendbacTransfer","Admin","nac3000","NGAC_LogBackup" ,
                "userid","nodeid","logtime","authtype","", "STATUS"); 
        /*initClass(10,"NITGENDBAC","Admin","nac3000","NGAC_LOG" ,
                "userid","nodeid","logtime","authtype","", "STATUS"); untuk data asli*/
    }
    public void initClass(long sleepMs, String dsnName, String user, String password,
            String tableName, String employeeID, String machineNumber, String checkTime, 
            String checkType, String verifyCode,
            String status) {
        this.sleepMs = sleepMs;
        this.dsnName = dsnName;
        this.machineNumber=machineNumber;
        this.user = user;
        this.password = password;
        this.tableName =tableName;
        this.employeeID=employeeID;
        this.logTime=checkTime;
        this.checkType=checkType;
        this.status=status;
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
              this.status+"<>"+STATUS_PROCES+" OR "+ this.status+" IS NULL "; ;
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

               //update by priska 2016-02-16
    java.util.Date dateFrom = null;
    java.util.Date dateTo = null;
    public void setDateFromTo(java.util.Date dateFrom, java.util.Date dateTo) {
     this.dateFrom=dateFrom;
     this.dateTo=dateTo;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getProgressSize() {
        return progressSize;
    }

    public int getRecordSize() {
        //System.out.println("=============================+++++++++======================");
        //System.out.println(recordSize);
        return recordSize;
    }

    public boolean updateODBC(String pkData, String userid, String checktime, String db_access) {
         Connection cn = null;
        Statement stmt = null;
        long stdUpODBC = System.currentTimeMillis();
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs, user, password);
            //String qry = "UPDATE NGAC_LOG SET STATUS="+STATUS_PROCES+" WHERE (" + this.primaryKey+"="+pkData+") OR (userid=" +employeeID + " AND logtime= #" + checktime + "# )";
            String qry = "UPDATE "+this.tableName+" SET "+this.status+"="+STATUS_PROCES +" WHERE (" + this.primaryKey+"="+pkData+") OR ( "+this.employeeID+"=" + employeeID 
                    + " AND "+this.logTime+"= #" + checktime + "# )";
            
            stmt = cn.createStatement();
            stmt.executeUpdate(qry);
            return true;
        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        } 
        finally {
            try {
                stmt.close();
                cn.close();
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.toString());
            }
        }
         //System.out.println("Update Time ODBC : "+(System.currentTimeMillis()- stdUpODBC));

        return false;
    }

    public long getSleepMs() {
        return sleepMs;
    }

    public void setSleepMs(long sleepMs) {
        this.sleepMs = sleepMs;
    }

    public void run() {
        ////end karena dia memakai desktop agar tidak diambil by satrya 2014-06-10
        if(false){
        this.setRunning(true);
        System.out.println("Transfer Process  :" + dsnName +" started : "+ this.running + "  try to connect ..." );
        message= "Transfer Process  :" + dsnName +" started : "+ this.running + "  try to connect ..." ;
        Connection getConn=null;

        try{
           getConn = ODBCConnection.getConnection(dsnName, user, password);

        } catch(Exception exc){
           message="Error on connection to database DSN="+dsnName+ " : "+exc;
           return;
        }
        System.out.println("Connection to  "+ dsnName +" OK ");
        message = "Connection to  "+ dsnName +" OK ";
        while(this.running)
        {
            
            try {
                Thread.sleep(this.getSleepMs()*300);
                // get records dari table mesin
                int limit=100;
                  ResultSet rs;
                  Statement stmt;

                  String sql;

                  sql =  "select "+primaryKey+","+this.employeeID+","+ this.logTime+","+this.machineNumber+","+this.checkType+" from " + this.tableName+" where "+
                          this.status+"<>"+STATUS_PROCES+" OR "+ this.status+" IS NULL " + " ORDER BY "+this.logTime + " ASC ";
                  System.out.println(sql);
                  if(jTextArea!=null){
                      jTextArea.setText(sql);
                  }
                  stmt = getConn.createStatement();
                  message="Run query on table machine "+this.tableName;
                  rs = stmt.executeQuery(sql);
                  String pkData = "";
                  String dataEmployee="";
                  String dataCheckTime="";
                  String dataCheckType="";
                  String dataMachineNumber="";
                  MachineTransaction machTrans = new MachineTransaction();

                  //System.out.println(recordSize);
                  int theSize=initRecordToGet();
                   if(theSize>0){
                    recordSize =  theSize;
                    progressSize=0;
                }
                if(this.jProgressBar!=null){
                     this.jProgressBar.setMinimum(0);
                     this.jProgressBar.setMaximum(recordSize);
                     this.jProgressBar.setValue(progressSize);
                }
                  //System.out.println(" theSize = " + theSize);
                  //update by satrya 2012-07-09
                     int updateCount =0;
                String inPkData = "" ; //   update HIstory_backup set STATUS=1 where EventSeqId in ( 1213, 12134 )
                  while (theSize>0 && rs.next() && this.running) {                     
                        Thread.sleep(this.getSleepMs());
                     dataEmployee=rs.getString(this.employeeID);
                      //update by satrya 2012-06-11
                     if(dataEmployee!=null && dataEmployee.length()>=maxCardId){
                        dataEmployee=dataEmployee.substring(0, maxCardId);
                        machTrans.setVerify(MachineTransaction.VERIFY_VALID);
                    } else{
                        machTrans.setVerify(MachineTransaction.VERIFY_INVALID);
                        dataEmployee=dataEmployee==null?"":dataEmployee;
                        //continue;
                    }
                     //update by satrya 201207-09
                      try{ 
                     pkData = rs.getString(this.primaryKey);
                     dataCheckTime=rs.getString(this.logTime);
                     dataMachineNumber=rs.getString(this.machineNumber);
                     machTrans.setCardId(dataEmployee);
                     //machTrans.setDateTransaction(dataCheckTime,"yyyy-MM-dd hh:mm:ss");
                     //update by satrya 2012-06-26
                     machTrans.setDateTransaction(dataCheckTime,"yyyy-MM-dd HH:mm:ss");
                     machTrans.setStation(""+dataMachineNumber);
                     machTrans.setMode(dataCheckType); 
                     //update by satrya 2012-07-09
                    } catch(Exception exc){ 
                        System.err.println(exc); 
                        continue;
                    }
                     long idTrans=0;
                       long stdInsertTrans = System.currentTimeMillis();
                     try{
                         MachineTransaction existingTrans = PstMachineTransaction.fetchBy(machTrans.getCardId(), 
                                 machTrans.getDateTransaction(), machTrans.getMode(),  machTrans.getStation());                         
                            idTrans = existingTrans.getOID();
                            
            
                         if(idTrans==0){
                           
                            //System.out.println("insert Nitgen : "+ machTrans.getCardId()+" "+machTrans.getDateTransaction() +
                                    // " "+ machTrans.getStation() + machTrans.getMode());
                             //update by satrya 2012-06-26
                             
                            idTrans=PstMachineTransaction.insertExc(machTrans);
                            message = "Data insert Nitgen : "+ machTrans.getCardId()+";"+
                                 machTrans.getDateTransaction()+";"+machTrans.getMode()+";"+  machTrans.getStation();
                         }
                      }
                     catch(Exception exc2){
                           System.out.println("EXCEPTION :"+exc2);
                     }
                       //System.out.println("Insert Nitgen Ke hr_machine_trans: "+(System.currentTimeMillis()- stdInsertTrans)); 
                    
                     if(idTrans!=0){
                             long stdUpdateODBC = System.currentTimeMillis();
                         try{
                             // System.out.println("  >> update odbc Nitgen ");
                              //updateODBC(pkData, dataEmployee, dataCheckTime, dsnName);
                              //update by satrya 2012-07-9
                              updateCount=updateCount+1;
                                inPkData=inPkData+""+pkData +",";
                           if(updateCount==limit  || theSize <= updateCount){
                                //updateODBC(pkData, dataemployeeId, dataeventDateTime, dsnName);
                                inPkData=inPkData+""+pkData;
                                updateODBCBatch(inPkData, dsnName);
                                updateCount=0;
                                inPkData ="";
                                //System.out.println("Update Nitgen  time: "+(System.currentTimeMillis()- stdUpdateODBC));
                    
                            }/*else{
                                updateCount=updateCount+1;
                                inPkData=inPkData+""+pkData +",";
                                //System.out.println("Update Nitgen : "+(System.currentTimeMillis()- stdUpdateODBC));
                    
                            }*/
                              progressSize++;
                             if(this.jProgressBar!=null){
                                this.jProgressBar.setValue(progressSize);
                            }

                         Thread.sleep(this.getSleepMs());
                         }

                         catch  (Exception exc2) {
                              System.out.println(" EXCEPTION : Update Failed"+ exc2);
                        }
                          //System.out.print("Update Nitgen ODBC : "+(System.currentTimeMillis()- stdUpdateODBC)); 
                     }


                  }

                  rs.close();
                  stmt.close();

                // simpan ke hr_machine transaction
                // set record dari table mesin , sudah diambil dan disimpan
            } catch (Exception exc) {
                System.out.println("EXCEPTION : "+ exc);
            }
        }

        this.running = false;
    }//end karena dia memakai desktop agar tidak diambil by satrya 2014-06-10
        else{
            message = "Mesin Ini Sudah Melakukan Transfer Melalui Attendance Desktop";
        }
    }
//update by satrya 2012-07-09
  public boolean updateODBCBatch(String inPkData, String db_access) {
        Connection cn = null;
        Statement stmt = null;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs, user, password);
            String qry = "UPDATE "+this.tableName+" SET "+this.status+"="+STATUS_PROCES +" WHERE "+ 
                     this.primaryKey + " IN ( "+ inPkData + ")";
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
