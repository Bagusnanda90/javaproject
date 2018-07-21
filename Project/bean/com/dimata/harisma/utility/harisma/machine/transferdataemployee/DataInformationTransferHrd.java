/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine.transferdataemployee;

import com.dimata.harisma.entity.masterdata.PstInformationHrd;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;

/**
 *
 * @author Dimata 007
 */
public class DataInformationTransferHrd implements Runnable {

    private String message;
    private int recordSize = 0;
    private int progressSize = 0;
    private boolean running = false;
    private long sleepMs = 50;
    private Date dtStart;
    private Date dtFinish;

    public void run() {
        try {
            this.setRunning(true);
            this.setMessage("Process transfer information HRD");
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText("Process transfer information HRD");
            }
            String whereClause = "";

            if (getDtStart() != null && getDtFinish() != null) {
                whereClause = " " + "\"" + Formater.formatDate(getDtFinish(), "yyyy-MM-dd HH:mm:59") + "\" >= " + PstInformationHrd.fieldNames[PstInformationHrd.FLD_DATE_START] + " AND  " + PstInformationHrd.fieldNames[PstInformationHrd.FLD_DATE_END] + " >= \"" + Formater.formatDate(getDtStart(), "yyyy-MM-dd HH:mm:00") + "\"";
            }
            //boolean sdhHabisDataTransOutlet = true;
            while (this.running) {

                try {

                    Thread.sleep(this.getSleepMs() * 30);

                    int limit = 50;
                    String sql = "";
                    try {
                        if (whereClause != null && whereClause.length() > 0) {
                            recordSize = 0;
                            progressSize = 0;
                            setDataInformationTransfer(sql, whereClause, limit);

                        }
                    } catch (Exception exc) {
                        message = "Errror transfer setTransferOutlet" + exc;
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(message);
                        }
                    }

                    // simpan ke hr_machine transaction
                    // set record dari table mesin , sudah diambil dan disimpan
                } catch (Exception exc) {
                    this.setMessage("Exception transfer Employee  Outlet" + exc);
                    if (msgDownloadInformation != null) {
                        msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                    }
                }//update by devin 2014-01-15
                finally {
                    //this.running = false;
                    this.setRunning(false);
                    if(btnRunDtEmployee!=null){
                        btnRunDtEmployee.setText("Run");
                    }
                    
                }
            }



        } catch (Exception exc) {
            this.setMessage("Exception transfer Employee  Outlet" + exc);
        }
    }

    public static int getCountDataInformationDownload(String whereClause) {

        DBResultSet dbrs = null;
        try {
            String sql = " SELECT COUNT(" + PstInformationHrd.fieldNames[PstInformationHrd.FLD_INFORMATION_HRD_ID] + ") FROM " + PstInformationHrd.TBL_HRD_INFORMATION + " WHERE " + whereClause;

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

    public void setDataInformationTransfer(String sql, String whereClause, int limit) {
        DBResultSet dbrs = null;
        ResultSet rs = null;

        //Hashtable hashPeriod = new Hashtable();
        //Hashtable hashEmployeeSchedule = new Hashtable();
        Hashtable hashInformation = PstInformationHrdDesktop.hashHrdInfoAda(0, 0, "", "");

        try {
            sql = "SELECT *  FROM " + PstInformationHrd.TBL_HRD_INFORMATION + "   WHERE (1=1)";

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " AND " + whereClause;
            }
            sql = sql + " ORDER BY " + PstInformationHrd.fieldNames[PstInformationHrd.FLD_DATE_START];
            if (limit > 0) {
                //sql = sql + " LIMIT " + 0+","+limit;
            }
            message = "Run query download data  " + sql;
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText(message);
            }
            int theSize = getCountDataInformationDownload(whereClause);
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
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText(message);
            }
            dbrs = DBHandler.execQueryResult(sql);
            rs = dbrs.getResultSet();


            while (rs != null && rs.next() && theSize > 0 && isRunning()) {

                Thread.sleep(this.getSleepMs());
                TabelDataInformationTransfer tabelDataInformationTransfer = new TabelDataInformationTransfer();
                tabelDataInformationTransfer.setDateStart(DBHandler.convertDate(rs.getDate(PstInformationHrd.fieldNames[PstInformationHrd.FLD_DATE_START]), rs.getTime(PstInformationHrd.fieldNames[PstInformationHrd.FLD_DATE_START])));
                tabelDataInformationTransfer.setDateEnd(DBHandler.convertDate(rs.getDate(PstInformationHrd.fieldNames[PstInformationHrd.FLD_DATE_END]), rs.getTime(PstInformationHrd.fieldNames[PstInformationHrd.FLD_DATE_END])));
                tabelDataInformationTransfer.setInformationId(rs.getLong(PstInformationHrd.fieldNames[PstInformationHrd.FLD_INFORMATION_HRD_ID]));
                tabelDataInformationTransfer.setNamaInformation(rs.getString(PstInformationHrd.fieldNames[PstInformationHrd.FLD_NAMA_INFORMATION]));
                //insert data Period
                if (tabelDataInformationTransfer.getInformationId() != 0) {
                    if (hashInformation.size() == 0 || hashInformation.containsKey(""+tabelDataInformationTransfer.getInformationId()) == false) {
                        try {
                            PstInformationHrdDesktop.insertExc(tabelDataInformationTransfer);
                        } catch (Exception exc) {
                            message = "Error transfer data schedule symbol" + exc;
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText(message);
                            }
                        }
                        //hashInformation.put("" + tabelDataInformationTransfer.getInformationId(), tabelDataInformationTransfer.getNamaInformation());
                        message = " Insert Data Schedule " + tabelDataInformationTransfer.getNamaInformation() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(message);
                        }
                    } else {
                        PstInformationHrdDesktop.updateExc(tabelDataInformationTransfer);
                        message = " Update Data Schedule " + tabelDataInformationTransfer.getNamaInformation() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(message);
                        }
                    }
                }
                progressSize++;
//                if(theSize!=0 && this.progressSize == theSize){
//                     this.jProgressBar.setValue(100);
//                }
//                if (this.progressSize == theSize) {
//                    this.progressSize = 0;
//                }
                if (this.jProgressBar != null) {
                    this.jProgressBar.setValue(progressSize);
                }
                Thread.sleep(this.getSleepMs());

            }
        } catch (Exception e) {
            message = "Execption transfer symbol" + e;
            if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(message);
                        }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                //stmt.close();
            } catch (Exception exc) {
                this.setMessage("Exception transfer Employee  Outlet" + exc);
                if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                        }
            }
        }

    }
    private javax.swing.JProgressBar jProgressBar = null;

    public void setProgressBar(javax.swing.JProgressBar jProgressBar) {
        this.jProgressBar = jProgressBar;
    }
    private javax.swing.JButton btnRunDtEmployee = null;

    public void setButton(javax.swing.JButton btnRunDtEmployee) {
        this.btnRunDtEmployee = btnRunDtEmployee;
    }
    private javax.swing.JLabel msgDownloadInformation = null;

    public void setMessageInfo(javax.swing.JLabel msgDownloadInformations) {
        this.msgDownloadInformation = msgDownloadInformations;
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
}
