/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine.transferdataemployee;

import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.PstMachineTransaction;
import com.dimata.harisma.entity.attendance.employeeoutlet.PstEmployeeOutlet;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.location.PstLocation;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author Dimata 007
 */
public class TransferDataPresenceAutoU implements Runnable {

    private String message;
    private int recordSize = 0;
    private int progressSize = 0;
    private boolean running = false;
    private long sleepMs = 50;
    private Date dtStart;
    private Date dtFinish;
    private String codeLocationMesin;
    private String inputParam;

    public void run() {
        try {
            this.setRunning(true);
            this.setMessage("Process transfer data presence");
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText(message);
            }
            String whereClause = "";

            if (getDtStart() != null && getDtFinish() != null && getCodeLocationMesin() != null && getCodeLocationMesin().length() > 0) {
                whereClause = " WHERE " + "\"" + Formater.formatDate(getDtFinish(), "yyyy-MM-dd HH:mm:59") + "\" >= eo." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND  eo." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " >= \"" + Formater.formatDate(getDtStart(), "yyyy-MM-dd HH:mm:00") + "\""
                        + " AND " + PstLocation.fieldNames[PstLocation.FLD_CODE] + " = \"" + getCodeLocationMesin() + "\"";
            }
            boolean sdhHabisDataTransOutlet = true;
            while (this.running && (sdhHabisDataTransOutlet)) {

                try {

                    Thread.sleep(this.getSleepMs() * 30);

                    int limit = 50;
                    String sql = "";
                    if (true) {
                        try {
                            setTransferPresence(sql, whereClause, 0, 0, sdhHabisDataTransOutlet);
                        } catch (Exception exc) {
                            message = "Errror transfer setTransferOutlet" + exc;
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText(message);
                            }
                        }
                    }



                    // simpan ke hr_machine transaction
                    // set record dari table mesin , sudah diambil dan disimpan
                } catch (Exception exc) {
                    this.setMessage("Exception transfer Employee  Outlet" + exc);
                    if (msgDownloadInformation != null) {
                        msgDownloadInformation.setText(message);
                    }
                }//update by devin 2014-01-15
                finally {
                    setRunning(false);
                    this.running = false;
                    if (btnRun != null) {
                        btnRun.setText("Run");
                    }
                }
            }



        } catch (Exception exc) {
            this.setMessage("Exception transfer Employee  Outlet" + exc);
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText(message);
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

    public static int getCountTransferPresence(String whereClause, Date Start, Date End, int start, int limit) {
//        if (Start == null || End == null) {
//            return 0;
//        }
        com.dimata.harisma.utility.harisma.machine.db.DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_MACHINE_TRANS_ID] + ")";
            sql = sql + " FROM " + PstMachineTransferDesktop.TBL_HR_MACHINE_TRANS + " AS tm "+" WHERE tm." + PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_POSTED] + "=" + PstMachineTransferDesktop.POSTED_STATUS_OPEN;

            sql = sql + " ORDER BY tm." + PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_DATE_TRANS];


            dbrs = com.dimata.harisma.utility.harisma.machine.db.DBHandler.execQueryResult(sql);
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
            com.dimata.harisma.utility.harisma.machine.db.DBResultSet.close(dbrs);
        }
    }
    private javax.swing.JProgressBar jProgressBar = null;

    public void setProgressBar(javax.swing.JProgressBar jProgressBar) {
        this.jProgressBar = jProgressBar;
    }
    private javax.swing.JLabel msgDownloadInformation = null;

    public void setMessageLabel(javax.swing.JLabel messagess) {
        this.msgDownloadInformation = messagess;
    }
    javax.swing.JButton btnRun = null;

    public void setButton(javax.swing.JButton btnRunDtEmployee) {
        this.btnRun = btnRunDtEmployee;
    }

    /**
     * transfer mengenai data employee
     *
     * @param sql
     * @param whereClause
     * @param dbrs
     * @param rs
     * @param hashEmployeeId
     * @param hashWorkHistory
     * @param hashAppUser
     * @param hashPosition
     * @param hashLocation
     * @param sdhHabisData
     */
    public String setTransferPresence(String sql, String whereClause, int start, int limit, boolean sdhHabisData) throws SQLException {
        com.dimata.harisma.utility.harisma.machine.db.DBResultSet dbrs = null;
        ResultSet rs = null;
        String empId = "";
        String machine_id = "";
        Hashtable hashPresence = new Hashtable();
        try {
            sql = "SELECT * FROM " + PstMachineTransferDesktop.TBL_HR_MACHINE_TRANS + " WHERE " + PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_POSTED] + "=" + PstMachineTransferDesktop.POSTED_STATUS_OPEN;

            message = "Run query download data employee " + sql;

            int theSize = getCountTransferPresence(whereClause, getDtStart(), getDtFinish(), start, limit);
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
            message = " Total  download data employee " + theSize;
            dbrs = com.dimata.harisma.utility.harisma.machine.db.DBHandler.execQueryResult(sql);
            rs = dbrs.getResultSet();


            while (rs != null && rs.next() && theSize > 0 && isRunning()) {

                Thread.sleep(this.getSleepMs());
                TabelMachineTransaction tabelMachineTransaction = new TabelMachineTransaction();
                tabelMachineTransaction.setCardId(rs.getString(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_CARD_ID]));
                tabelMachineTransaction.setDateTransaction(com.dimata.harisma.utility.harisma.machine.db.DBHandler.convertDate(rs.getDate(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_DATE_TRANS]), rs.getTime(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_DATE_TRANS])));
                tabelMachineTransaction.setMode(rs.getString(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_MODE]));
                tabelMachineTransaction.setNote(rs.getString(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_NOTE]));
                tabelMachineTransaction.setPosted(rs.getInt(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_POSTED]));
                tabelMachineTransaction.setStation(rs.getString(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_STATION]));
                tabelMachineTransaction.setVerify(rs.getInt(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_VERIFY]));
                tabelMachineTransaction.setMachineTransId(rs.getLong(PstMachineTransferDesktop.fieldNames[PstMachineTransferDesktop.FLD_MACHINE_TRANS_ID]));




                //insert data employee
                if (tabelMachineTransaction.getMachineTransId() != 0) {
                    machine_id = machine_id + tabelMachineTransaction.getMachineTransId() + ",";
                    if (hashPresence.size() == 0 || hashPresence.containsKey("" + tabelMachineTransaction.getMachineTransId()) == false) {
                        try {
                            PstMachineTransaction.insertTransfer(tabelMachineTransaction);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                        }

                        hashPresence.put("" + tabelMachineTransaction.getMachineTransId(), tabelMachineTransaction.getCardId());

                        message = " Insert Data Card ID " + tabelMachineTransaction.getCardId() + " In local";
                    } else {
                        PstMachineTransaction.updateTransfer(tabelMachineTransaction);
                        message = " Update Data Card ID " + tabelMachineTransaction.getCardId() + " In local";
                        //String fullName = hashEmployeeId == null || hashEmployeeId.get(employeeTransfer.getEmployeeId()) == null ? "" : (String) hashEmployeeId.get(employeeTransfer.getEmployeeId());
                        //message = fullName.length() == 0 ? " Data Employee already exis" : " Data Employee already exis " + fullName + " In local";
                    }
                }

                progressSize++;
//                if (this.progressSize == theSize) {
//                    this.progressSize = 0;
//                }
                if (this.jProgressBar != null) {
                    this.jProgressBar.setValue(progressSize);
                }
                Thread.sleep(this.getSleepMs());

            }
            if (machine_id != null && machine_id.length() > 0) {
                //update
                machine_id = machine_id.substring(0, machine_id.length() - 1);
                PstMachineTransferDesktop.updateStatusPosted(machine_id);
            }

            sdhHabisData = false;
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
            return empId;
        }
    }

    /**
     * @return the inputParam
     */
    public String getInputParam() {
        return inputParam;
    }

    /**
     * @param inputParam the inputParam to set
     */
    public void setInputParam(String inputParam) {
        this.inputParam = inputParam;
    }
}
