/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine.transferdataemployee;

import com.dimata.harisma.entity.masterdata.location.PstLocation;
import com.dimata.harisma.utility.harisma.machine.db.DBHandler;
import com.dimata.harisma.utility.harisma.machine.db.DBResultSet;
import com.dimata.harisma.utility.harisma.masterdatadesktop.entity.EmployeeDummyTransfer;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author Dimata 007
 */
public class EmployeeOutletTransferUploadData implements Runnable {

    private String message;
    private int recordSize = 0;
    private int progressSize = 0;
    private boolean running = false;
    private long sleepMs = 50;
    private Date dtStart;
    private Date dtFinish;
    private String codeLocationMesin;

    public void run() {
        try {
            this.setRunning(true);
            this.setMessage("Process transfer data Employee  Dummy");
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText("Process transfer data Employee  Dummy");
            }
            String whereClause = "";

            if (getDtStart() != null && getDtFinish() != null && getCodeLocationMesin() != null && getCodeLocationMesin().length() > 0) {
                whereClause = " WHERE " + PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_DATE_CREATE_TRANSFER] + " between \"" + Formater.formatDate(getDtStart(), "yyyy-MM-dd 00:00") + "\" AND \"" + Formater.formatDate(getDtFinish(), "yyyy-MM-dd 23:59") + "\"";
                        //+ " AND " + PstLocation.fieldNames[PstLocation.FLD_CODE] + " = \"" + getCodeLocationMesin() + "\"";
            }

            boolean sdhHabisDataTransMasterSch = true;
            while (this.running && (sdhHabisDataTransMasterSch)) {

                try {

                    Thread.sleep(this.getSleepMs() * 30);

                    int limit = 50;
                    try {
                        recordSize = 0;
                        progressSize = 0;
                        if (whereClause != null && whereClause.length() > 0) {
                            setTransferEmpDummyTrans(whereClause);
                        } else {
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Date is null");
                            }
                        }

                    } catch (Exception exc) {
                        message = "Errror transfer setTransfer  employee dummy" + exc;
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText("Errror transfer setTransfer employee dummy" + exc);
                        }
                    }




                    // simpan ke hr_machine transaction
                    // set record dari table mesin , sudah diambil dan disimpan
                } catch (Exception exc) {
                    this.setMessage("Exception transfer employee dummy" + exc);
                    if (msgDownloadInformation != null) {
                        msgDownloadInformation.setText("Exception transfer employee dummy" + exc);
                    }
                }//update by devin 2014-01-15
                finally {
                    setRunning(false);
                    this.running = false;
                    if (btnRunDtEmployee != null) {
                        btnRunDtEmployee.setText("Run");
                    }

                }
            }



        } catch (Exception exc) {
            this.setMessage("Exception transfer employee dummy" + exc);
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText("Exception transfer employee dummy" + exc);
            }
        }
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the recordSize
     */
    public int getRecordSize() {
        return recordSize;
    }

    /**
     * @param recordSize the recordSize to set
     */
    public void setRecordSize(int recordSize) {
        this.recordSize = recordSize;
    }

    /**
     * @return the progressSize
     */
    public int getProgressSize() {
        return progressSize;
    }

    /**
     * @param progressSize the progressSize to set
     */
    public void setProgressSize(int progressSize) {
        this.progressSize = progressSize;
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

    /**
     * @return the dtStart
     */
    public Date getDtStart() {
        return dtStart;
    }

    /**
     * @param dtStart the dtStart to set
     */
    public void setDtStart(Date dtStart) {
        this.dtStart = dtStart;
    }

    /**
     * @return the dtFinish
     */
    public Date getDtFinish() {
        return dtFinish;
    }

    /**
     * @param dtFinish the dtFinish to set
     */
    public void setDtFinish(Date dtFinish) {
        this.dtFinish = dtFinish;
    }

    /**
     * @return the codeLocationMesin
     */
    public String getCodeLocationMesin() {
        return codeLocationMesin;
    }

    /**
     * @param codeLocationMesin the codeLocationMesin to set
     */
    public void setCodeLocationMesin(String codeLocationMesin) {
        this.codeLocationMesin = codeLocationMesin;
    }
    private javax.swing.JProgressBar jProgressBar = null;

    /**
     * @param jProgressBar the jProgressBar to set
     */
    public void setjProgressBar(javax.swing.JProgressBar jProgressBar) {
        this.jProgressBar = jProgressBar;
    }
    private javax.swing.JLabel msgDownloadInformation = null;

    public void setMessageLabel(javax.swing.JLabel messagess) {
        this.msgDownloadInformation = messagess;
    }
    javax.swing.JButton btnRunDtEmployee = null;

    public void setButton(javax.swing.JButton btnRunDtEmployee) {
        this.btnRunDtEmployee = btnRunDtEmployee;
    }
//    public void getProgressBar(javax.swing.JProgressBar jProgressBar) {
//        this.jProgressBar = jProgressBar;
//    }

    public String setTransferEmpDummyTrans(String whereClause) throws SQLException {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        String empEmployeeDummyId = "";
        Hashtable hashEmployeeDummy = PstEmployeeDummyTransferToServer.hashEmpEmpDummyAda(0, 0, "", "");
        try {
            String sql = "SELECT * FROM "+PstEmployeeDummyTransferToServer.TBL_TRANSFER_EMPLOYEE_OUTLET
                    + " "+whereClause;


            message = "Run query download employee dummy " + sql;
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText("Run query download data employee dummy " + sql);
            }
            int theSize = getCountEmployeeDummyUpload(whereClause);
            if (theSize > 0) {
                recordSize = theSize;
                progressSize = 0;
            } else {
                recordSize = 0;
                progressSize = 0;
            }
            if (this.jProgressBar != null) {
                this.jProgressBar.setMaximum(theSize);
            }

            message = " Total  download data employee dummy" + theSize;
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText(" Total  download data employee dummy" + theSize);
            }
            dbrs = DBHandler.execQueryResult(sql);
            rs = dbrs.getResultSet();


            while (rs != null && rs.next() && theSize > 0 && isRunning()) {

                Thread.sleep(this.getSleepMs());
                EmployeeDummyTransfer employeeDummyTransfer = new EmployeeDummyTransfer();
                PstEmployeeDummyTransferToServer.resultToObject(rs, employeeDummyTransfer); 
                
                //insert data employee
                if (employeeDummyTransfer.getOID() != 0) {
                    empEmployeeDummyId = empEmployeeDummyId + employeeDummyTransfer.getOID() + ",";
                    if (hashEmployeeDummy.size() == 0 || hashEmployeeDummy.containsKey("" + employeeDummyTransfer.getOID()) == false) {
                        try {
                            PstEmployeeDummyTransferToServer.insertExc(employeeDummyTransfer);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer employee dummy" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer employee dummy " + exc);
                            }
                        }

                        message = " Insert Data Employee employee dummy " + employeeDummyTransfer.getEmployeeFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data employee dummy " + employeeDummyTransfer.getEmployeeFullName() + " In local");
                        }
                    } else {
                        PstEmployeeDummyTransferToServer.updateExc(employeeDummyTransfer);
                        message = " Update Data employee dummy" + employeeDummyTransfer.getEmployeeFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data employee dummy " + employeeDummyTransfer.getEmployeeFullName() + " In local");
                        }
                    }
                }


                progressSize++;

                if (this.jProgressBar != null) {
                    this.jProgressBar.setValue(progressSize);
                }
                Thread.sleep(this.getSleepMs());

            }
        } catch (Exception exc) {
            message = "Exc Transfer Data Outlet" + exc;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                //stmt.close();
            } catch (Exception exc) {
                this.setMessage("Exception transfer Employee  Outlet" + exc);
            }
            return empEmployeeDummyId;
        }
    }
    
    public static int getCountEmployeeDummyUpload(String whereClause) {
        if (whereClause == null || whereClause.length() == 0) {
            return 0;
        }
        DBResultSet dbrs = null;
        try {

            String sql = "SELECT COUNT(" + PstEmployeeDummyTransferToServer.fieldNames[PstEmployeeDummyTransferToServer.FLD_TRANSFER_EMPLOYEE_ID] + ")";
            sql = sql + " FROM " + PstEmployeeDummyTransferToServer.TBL_TRANSFER_EMPLOYEE_OUTLET + " "+whereClause;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
}
