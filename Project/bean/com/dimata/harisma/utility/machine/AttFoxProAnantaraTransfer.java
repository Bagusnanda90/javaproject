/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.machine;

import com.dimata.harisma.entity.attendance.MachineTransaction;
import com.dimata.harisma.entity.attendance.PstMachineTransaction;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.util.Formater;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;
import org.apache.jasper.tagplugins.jstl.core.Catch;

/**
 *
 * @author Satrya Ramayu
 */
public class AttFoxProAnantaraTransfer implements AttTransfer_I, Runnable {

    private final static int STATUS_NEW = 0;
    private final static int STATUS_PROCES = 1;
   
    private boolean running = false;
    private long sleepMs = 10;
    private String message = "";
    // database ODBC
    String dsnName = "";
    String user = "";
    String password = "";
    // Data untuk database machine
    String tableName = "";   // nama table untuk database mesin
    String machineNumber = ""; // field name - nomor mesin
    String dataIDFld = "Idabs"; // field name - nomor mesin
    String employee_num = "Idabs"; // field di database mesin untuk payroll number
    String employeeID = "";    // field name - id karyawan di mesin data 
    //in-out bisa berbeda dengan employee ID di databank harisma                             
    String checkDate = "";    // field name - waktu attendance
    String checkTime = "jam";
    String checkType = "";    // field name - jenis IN, OUT, OUT-PERSONAL, IN-PERSONAL, OUT ON DUTY, CALLBACLsesuai 
    // tergantung dari jenis mesin yang disupport pada saat masuk harisma akan di convert 
    String verifyCode = "";  // field name - apakah sukses di verify atau tidak
    String status = "";       // field name - status yang akan di set oleh harisma setelah data diambil : 
    // dari 0=belum diambil harisma menjadi 1=sudah diambil
    private int recordSize = 0;
    private int progressSize = 0;
    //PstSystemProperty.getValueByName("IMG_ROOT");
    String path = PstSystemProperty.getValueByName("PATH_LOCATION_DBF").toUpperCase();//d:\\MEMBUAT PROGRAM\\ANANTARA\\DATABASE\\FingerPrintDB\\";
    //update by satrya 2013-12-18
    Date startDate=null;
    Date endDate=null;
    int statusHr=STATUS_NEW;
    int changeAutomaticManualFinish=0;
    int flagSdhSelesaiMencariDataTgl=1;
    int flagSdhSelesaiMencariDataTglStatus=1;
    int flagSdhSelesaiCountMencariDataTgl=1;
     int flagSdhSelesaiCountStatus=1;
    
    public AttFoxProAnantaraTransfer() {
        initClass(100, path, "root", "",
                "tadat.dbf", "Idabs", "Machid", "Tgl", "inout", "Status", "status_har");


        //AttFingerSpotAbsoluteSeries
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
        this.employeeID = employeeID;
        this.checkDate = checkTime;
        this.checkType = checkType;
        this.verifyCode = verifyCode;
        this.status = status;
        this.dataIDFld = "Idabs";
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
        
       this.flagSdhSelesaiMencariDataTgl=1;
    this.flagSdhSelesaiMencariDataTglStatus=1;
    this.flagSdhSelesaiCountMencariDataTgl=1;
    this.flagSdhSelesaiCountStatus=1;
    }

    public int initRecordToGet() {
        Connection theConn = null;
        int theSize = 0;
        try {
            // connection to an ACCESS MDB
            theConn = DBFConnection.getConnection(dsnName, user, password);
            
            ResultSet rs;
            Statement stmt;
            String sql;
            System.out.println("Prosess record");
            sql = "select count(" + this.dataIDFld + ") as size from " + this.tableName + " where ";
                    // update by satrya 2013-12-18 + this.status + "=" + STATUS_NEW + " OR " + this.status + " IS NULL";
                   if(startDate!=null && endDate!=null && flagSdhSelesaiCountMencariDataTgl>0){
                      sql = sql + " between("+checkDate+", {^"+Formater.formatDate(startDate, "yyyy-MM-dd")+"}, {^"+Formater.formatDate(endDate, "yyyy-MM-dd")+"}) and ";
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
                theSize = rs.getInt("size");
            }
            System.out.println("Prosess record"+theSize);
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

        this.setRunning(true);
        //System.out.println("Process transfer data mesin dimulai");

        Connection getConn = null;


        try {
            getConn = DBFConnection.getConnection(dsnName, user, password);

        } catch (Exception exc) {
            System.err.println("Exception" + exc);
            message = "Error on connection to database DSN=" + dsnName + exc;
            if (jTextArea != null) {
                jTextArea.setText(message + "\n" + jTextArea.getText());
            }
            return;
        }
        message = "Process transfer data mesin dimulai";
        while (this.running) {
            String sql = "";

            if (jTextArea != null) {
                jTextArea.setText(message + "\n" + jTextArea.getText());
            }
            try {
                Thread.sleep(this.getSleepMs());
                // get records dari table mesin
                int limit = 20;
                ResultSet rs;
                Statement stmt;
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
                String tbName=this.tableName.toUpperCase();
                String tmpTbName=this.tableName.replace(".dbf", "");
                sql = "select " + this.employee_num + "," + this.checkDate + "," + this.checkTime + "," + this.checkType
                        + "," + this.machineNumber + "," + this.dataIDFld + " from \"" +path+ ""+tbName +"\" "    + (this.tableName!=null && this.tableName.length()>0?tmpTbName.toUpperCase():"")
                        /*+ " a left join userinfo u on a." + this.employeeID
                         + "= u." + this.employeeID*/
                        + " where ";
                        //update by satrya 2013-12-18 + this.status + "=" + STATUS_NEW + " OR " + this.status + " IS NULL ORDER BY " + this.checkDate + ", " + this.checkTime;
                        if(startDate!=null && endDate!=null && flagSdhSelesaiMencariDataTgl>0){
                      sql = sql + " between("+checkDate+", {^"+Formater.formatDate(startDate, "yyyy-MM-dd")+"}, {^"+Formater.formatDate(endDate, "yyyy-MM-dd")+"}) and ";
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
                        sql = sql + " ORDER BY " + this.checkDate + ", " + this.checkTime;
                // System.out.println(sql);
                if (jTextArea != null) {
                    jTextArea.setText(sql);
                }
                stmt = getConn.createStatement();
                message = "Run query on table machine " + this.tableName;

                rs = stmt.executeQuery(sql);
                String dataEmployee = "";
                String dataCheckTime = "";
                String dataCheckType = "";
                String dataMachineNumber = "";
                MachineTransaction machTrans = new MachineTransaction();
                long dataID = 0;
                //System.out.println(recordSize);
                int theSize = initRecordToGet();
                if (theSize > 0) {
                    recordSize = theSize;
                    progressSize = 0;
                } else {
                    recordSize = 0;
                    progressSize = 0;
                }
                if (this.jProgressBar != null) {
                    this.jProgressBar.setMinimum(0);
                    this.jProgressBar.setMaximum(recordSize);
                    this.jProgressBar.setValue(progressSize);
                }
                int updateCount = 0;
                String inPkData = ""; //   update HIstory_backup set STATUS=1 where EventSeqId in ( 1213, 12134 )
                while (theSize > 0 && rs.next() && this.running) {

                    //System.out.println(recordSize);
                    Thread.sleep(this.getSleepMs());
                    dataEmployee = rs.getString(this.employee_num);
                    dataCheckTime = rs.getString(this.checkDate) + " " + rs.getString(this.checkTime);
                    dataCheckType = rs.getString(this.checkType);
                    dataMachineNumber = rs.getString(this.machineNumber);
                    dataID = rs.getLong(this.dataIDFld);


                    try {
                        machTrans.setCardId(dataEmployee);
                        machTrans.setDateTransaction(dataCheckTime, "yyyy-MM-dd HHmm");
                        machTrans.setMode(dataCheckType);
                        machTrans.setStation("" + dataMachineNumber);
                    } catch (Exception exc) {
                        System.err.println(exc);
                        continue;
                    }

                    long idTrans = 0;

                    try {
                        MachineTransaction existingTrans = PstMachineTransaction.fetchBy(machTrans.getCardId(),
                                machTrans.getDateTransaction(), machTrans.getMode(), machTrans.getStation());
                        idTrans = existingTrans.getOID();
                        if (idTrans == 0) {
                            idTrans = PstMachineTransaction.insertExc(machTrans);
                            //}else{
                            message = "Data insert : " + machTrans.getCardId() + ";"
                                    + machTrans.getDateTransaction() + ";" + machTrans.getMode() + ";" + machTrans.getStation();
                           // System.out.println(message);
                        } else {
                            message = "Data exists : " + machTrans.getCardId() + ";"
                                    + machTrans.getDateTransaction() + ";" + machTrans.getMode() + ";" + machTrans.getStation();
                           // System.out.println(message);
                        }
                    } catch (Exception exc2) {
                        System.out.println("EXCEPTION :" + exc2);
                    }

                    if (idTrans != 0 && dataID!=0) {

                        try {

                            updateCount = updateCount + 1;
                            inPkData = inPkData +"" + "'"+dataID +"'"+ ",";


                            if (updateCount == limit || theSize <= updateCount || (this.progressSize + 1) == theSize) {

                                inPkData = inPkData + "" +"'"+dataID+"'";
                                updateODBCBatch(inPkData);
                                updateCount = 0;
                                inPkData = "";

                                message = " Update Status = 1 in mesin absence" + inPkData;
                            }
                            //System.out.println(message);
                            progressSize++;
                            if (this.progressSize == theSize) {
                                this.progressSize = 0;
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
                 //update by devin 2014-01-23
                if(changeAutomaticManualFinish==0){
                      this.running = false;
                        message = "Transfer Stop";
                   }

                rs.close();
                stmt.close();

                // simpan ke hr_machine transaction
                // set record dari table mesin , sudah diambil dan disimpan
            } catch (Exception exc) {
                //update by devin 2014-01-23 
                message = "error"+exc;
                //System.out.println(" RUN " + sql + " >>> : " + exc);
            }
        }

        this.running = false;
        message = "Transfer Stop";
        if (jTextArea != null) {
            jTextArea.setText(message + "\n" + jTextArea.getText());
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
        // System.out.println("=============================+++++++++======================");
        //System.out.println(recordSize);
        return recordSize;

    }

    public boolean updateODBC(String pkData, String userid, String checktime, String db_access) {
        Connection cn = null;
        Statement stmt = null;
        try {
            cn = DBFConnection.getConnection(dsnName, user, password);

            String qry = "UPDATE " + this.tableName + " SET " + this.status + "=" + STATUS_PROCES + " WHERE " + this.dataIDFld + "=" + pkData;
            /*" WHERE USERID=" + userid + " AND CHECKTIME= #" + checktime + "# OR "*/;
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

    public boolean updateODBCBatch(String inPkData) {
        Connection cn = null;
        Statement stmt = null;
        try {
            cn = DBFConnection.getConnection(dsnName, user, password);
            // this.checkTime =checkTime;
            //cn = DriverManager.getConnection(cs, user, password);

            String qry = "UPDATE " + this.tableName + " SET " + this.status + "=" + STATUS_PROCES + " WHERE "
                    + this.dataIDFld + " IN ( " + inPkData + ")";
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
    private javax.swing.JProgressBar jProgressBar = null;

    public void setProgressBar(javax.swing.JProgressBar jProgressBar) {
        this.jProgressBar = jProgressBar;
    }
    javax.swing.JTextArea jTextArea = null;

    public void setTextArea(javax.swing.JTextArea jTextAreaPar) {
        jTextArea = jTextAreaPar;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    public Vector<Employee> getEmployees() {
        Connection getConn = null;
        Vector<Employee> employees = new Vector();
        try {
            getConn = ODBCConnection.getConnection(dsnName, user, password);
            int limit = 3000;
            ResultSet rs;
            Statement stmt;

            String sql;
            sql = "select userid, badgenumber, name from user_info";
            stmt = getConn.createStatement();
            rs = stmt.executeQuery(sql);
            long userid = 0;
            String badgenumber = "";
            String name = "";
            while (rs.next()) {
                userid = rs.getLong("userid");
                badgenumber = rs.getString("badgenumber");
                name = rs.getString("name");
                Employee employee = new Employee();
                employee.setBarcodeNumber(badgenumber);
                employee.setOID(userid);
                employee.setFullName(name);
                employees.add(employee);
            }

            rs.close();
            stmt.close();

            // simpan ke hr_machine transaction
            // set record dari table mesin , sudah diambil dan disimpan
        } catch (Exception exc) {
            System.out.println(exc);
        }
        return employees;
    }

    public void initDBConfig(DBMachineConfig dbConfig) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
   

}
