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
public class Intelliscanv1Transfer implements AttTransfer_I, Runnable {

    private boolean running = false;
    private long sleepMs = 100;
    private String message = "";
    String dsnName = "";
    String user = "";
    String password = "";
    // table intelliscanv1
    String tableName = "";
    String machineNumber = "";
    String EmployeeID = "";
    String eventDateTime = "";
    String checkType="";
    String status = "";
    String verify = "";
    String primaryKey = "EventSeqID";
    private int recordSize = 0;
    private int progressSize = 0;
      ///update by satrya 20121106
    int maxCardId=5; // panjang card id di machine
    int startCardId=11;


    //update by satrya 2013-12-18
    java.util.Date startDate=null;
    java.util.Date endDate=null;
    int statusHr=STATUS_NEW;
    int changeAutomaticManualFinish=0;
    
   
    public Intelliscanv1Transfer() {
        initClass(10, "Intelliscanv1Transfer", "Admin", "", "History_Backup", "UserKey", "DeviceKey", 
                "EVENTDATETIME", "DeviceType", "",
                "STATUS");
        /* initClass(10, "INTELLISCANV1", "Admin", "", "History", "UserKey", "DeviceKey", 
                "EVENTDATETIME", "DeviceType", "",
                "STATUS"); */
    }

               //update by priska 2016-02-16
    java.util.Date dateFrom = null;
    java.util.Date dateTo = null;
    public void setDateFromTo(java.util.Date dateFrom, java.util.Date dateTo) {
     this.dateFrom=dateFrom;
     this.dateTo=dateTo;
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
    public void initClass(long sleepMs, String dsnName, String user, String password, 
            String tableName, String employeeID, String machineNumber, String checkTime, 
            String checkType, String verifyCode, String status) {
        this.sleepMs = sleepMs;
        this.dsnName = dsnName;
        this.user = user;
        this.password = password;
        this.tableName = tableName;
        this.machineNumber = machineNumber;
        this.EmployeeID = employeeID;
        this.eventDateTime = checkTime;
        this.checkType = checkType;
        this.status = status;
        this.verify = verifyCode;
    }

    public int initRecordToGet() {
        Connection theConn = null;
        int theSize = 0;
        try {
            // connection to an ACCESS MDB
            theConn = ODBCConnection.getConnection(dsnName, user, password);

            ResultSet rs;
            Statement stmt;
            String sql;

            sql = "select count(" + this.primaryKey + ") as size from " + this.tableName + " where "
                    + this.status + "<>"+STATUS_PROCES+" OR "+this.status+" IS NULL" ;// and " + this.EmployeeID + "<>'000000000000000000000000' and  LEN(" + this.EmployeeID +") >= 24";
            stmt = theConn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                theSize = rs.getInt("size");
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (theConn != null) {
                    theConn.close();
                }
            } catch (Exception e) {
            }
        }
        return theSize;
    }

    public void run() {
        //di buat agar tidak mencari  coneksi database, karena ini memakai desktop
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
      ResultSet rs=null;
      Statement stmt= null;
        while (this.running) {
            try {
                Thread.sleep(this.getSleepMs()*200);
                // get records dari table mesin
                int limit = 100;


                String sql;

                sql = "select "+primaryKey+"," + this.EmployeeID + "," + this.eventDateTime +"," + this.checkType +","+this.machineNumber+
                        ( (this.verify!=null && this.verify.length() >0 )? (","+this.verify) : "" )+ " from " + this.tableName + " where "
                        + this.status + "<>"+STATUS_PROCES+" OR "+ this.status + " IS NULL " + " ORDER BY "+this.eventDateTime + " ASC "; // and " + this.EmployeeID + "<>'000000000000000000000000' and  LEN(" + this.EmployeeID +") >= 24";
                  System.out.println(sql);
                  if(jTextArea!=null){
                      jTextArea.setText(sql); 
                  }
                stmt = getConn.createStatement();
                message = "Run query on table machine " + this.tableName;
                rs = stmt.executeQuery(sql);
                String pkData = "";
                String dataevent = "";
                String dataeventDateTime = "";
                String dataemployeeId = "";
                String datadeviceKey = "";
                String dataCheckType="";
                String dataMachineNumber="";
                String dataVerify="";
                int datapositionCode;

                MachineTransaction machTrans = new MachineTransaction();

                //System.out.println(recordSize);
                int theSize = initRecordToGet();
                if (theSize > 0) {
                    recordSize = theSize;
                    progressSize = 0;
                } else {
                    recordSize = 0;
                    progressSize = 0;
                }
                if(this.jProgressBar!=null){
                     this.jProgressBar.setMinimum(0);
                     this.jProgressBar.setMaximum(recordSize);
                     this.jProgressBar.setValue(progressSize);
                }

                int updateCount =0;
                String inPkData = "" ; //   update HIstory_backup set STATUS=1 where EventSeqId in ( 1213, 12134 )
                
                while (theSize > 0 && rs.next() && this.running) {

                    ///System.out.println(recordSize);
                    Thread.sleep(this.getSleepMs());
                    //  datauserKey=rs.getString(this.userKey);
                    dataemployeeId = rs.getString(this.EmployeeID);
                      
                     //update by satrya 2012-06-11
                    if(dataemployeeId!=null && dataemployeeId.length()==24){
                        //machTrans.setCardId(dataemployeeId.substring(11, 16));
                        String dataemployeeIdX=dataemployeeId.substring(startCardId, startCardId+maxCardId);
                            if(dataemployeeIdX.compareTo("00000")==0){
                                dataemployeeId=dataemployeeId.substring(0,maxCardId);
                            }
                            else
                            {
                               dataemployeeId =  dataemployeeIdX;
                            }
                        machTrans.setCardId (dataemployeeId);
                        machTrans.setVerify(MachineTransaction.VERIFY_VALID);
                    } 
                   
                    else{
                        //machTrans.setCardId("");// invalid data
                        dataemployeeId=dataemployeeId==null?"":dataemployeeId;
                        machTrans.setCardId (dataemployeeId);
                        machTrans.setVerify(MachineTransaction.VERIFY_INVALID);
                    }
                    try{ 
                    dataeventDateTime = rs.getString(this.eventDateTime);
                    //   datadeviceKey=rs.getString(this.deviceKey);
                    //  datapositionCode=rs.getInt(this.positionCode);

                    //             machTrans.setTransactionId(dataevent);
                    //    machTrans.setCardId(datauserKey
                    pkData = rs.getString(this.primaryKey);
                    dataMachineNumber=rs.getString(this.machineNumber);
                    //dataVerify = rs.getString(this.verify);
                    machTrans.setStation(""+dataMachineNumber);
                   // machTrans.setDateTransaction(dataeventDateTime, "yyyy-MM-dd hh:mm:ss");
                    //update by satrya 2012-06-26
                     machTrans.setDateTransaction(dataeventDateTime,"yyyy-MM-dd HH:mm:ss");
                    machTrans.setMode(dataCheckType);
                    machTrans.setVerify(theSize);
                    } catch(Exception exc){ 
                        System.err.println(exc); 
                        continue;
                    }
                    
                    long idTrans = 0;
                    long stdInsertIntell = System.currentTimeMillis();
                    try {
                        MachineTransaction existingTrans = PstMachineTransaction.fetchBy(machTrans.getCardId(), 
                                 machTrans.getDateTransaction(), machTrans.getMode(),  machTrans.getStation());                         
                         idTrans = existingTrans.getOID();
                         if(idTrans==0){
                          //  System.out.println("insert Intell"+ machTrans.getCardId()+" "+machTrans.getDateTransaction() +
                            //         " "+ machTrans.getStation() + machTrans.getMode());
                             //update by satrya 2012-06-26
                            idTrans = PstMachineTransaction.insertExc(machTrans);
                             message = "Data insert Intelliscanv : "+ machTrans.getCardId()+";"+
                                 machTrans.getDateTransaction()+";"+machTrans.getMode()+";"+  machTrans.getStation();
                           
                          }
                    } catch (Exception exc2) {
                        System.out.println(exc2);
                    }
                    //System.out.print("Insert Itelliscanv ke hr_machine_trans: "+(System.currentTimeMillis()- stdInsertIntell));
                    if (idTrans != 0) {
                        //java.util.Date startUp = new java.util.Date();
                        long stdUpIntell = System.currentTimeMillis();
                        try {
                            //update by satrya 2012-07-10
                                updateCount=updateCount+1;
                                inPkData=inPkData+""+pkData +",";
                                                            
                            //System.out.println("update Intell ");
                            if(updateCount==limit  || theSize <= updateCount){
                                //updateODBC(pkData, dataemployeeId, dataeventDateTime, dsnName);
                                inPkData=inPkData+""+pkData;
                                updateODBCBatch(inPkData, dsnName);
                                updateCount=0;
                                inPkData ="";
                                //System.out.println("Time Update Itelliscanv  TO ODBC: "+(System.currentTimeMillis()- stdUpIntell));
                    
                            }/*else{
                                updateCount=updateCount+1;
                                inPkData=inPkData+""+pkData +",";
                                //System.out.println("Update Itelliscanv : "+(System.currentTimeMillis()- stdUpIntell));
                    
                            }*/
                            progressSize++;
                            if(this.jProgressBar!=null){
                                this.jProgressBar.setValue(progressSize);
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
                
                // simpan ke hr_machine transaction
                // set record dari table mesin , sudah diambil dan disimpan
            } catch (Exception exc) {
                System.out.println(exc);
            } finally {
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
    }//end karena memakai desktop by satrya 2014-06-10
        else{
            message = "Mesin Ini Sudah Melakukan Transfer Melalui Attendance Desktop";
        }
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
        //System.out.println("=============================+++++++++======================");
        //System.out.println(recordSize);
        return recordSize;

    }

    public boolean updateODBC(String pkData, String userKey, String eventDateTime, String db_access) {
        Connection cn = null;
        Statement stmt = null;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String cs = "jdbc:odbc:" + db_access;
            cn = DriverManager.getConnection(cs, user, password);
            String qry = "UPDATE "+this.tableName+" SET "+this.status+"="+STATUS_PROCES +" WHERE (" + this.primaryKey+"="+pkData+") OR ( "+this.EmployeeID+"=" + EmployeeID 
                    + " AND "+this.eventDateTime+"= #" + eventDateTime + "# )";
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
