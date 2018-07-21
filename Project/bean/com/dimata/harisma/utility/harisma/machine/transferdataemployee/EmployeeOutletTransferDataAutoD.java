/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.harisma.machine.transferdataemployee;

import com.dimata.harisma.entity.admin.PstAppUser;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.employeeoutlet.EmployeeOutlet;
import com.dimata.harisma.entity.attendance.employeeoutlet.PstEmployeeOutlet;
import com.dimata.harisma.entity.attendance.mappingoutlet.PstExtraScheduleOutletDetail;
import com.dimata.harisma.entity.employee.PstCareerPath;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.location.PstLocation;
import com.dimata.harisma.entity.masterdata.mappingkadiv.PstMappingKadivDetail;
import com.dimata.harisma.entity.masterdata.mappingkadiv.PstMappingKadivMain;
import com.dimata.harisma.entity.overtime.PstOvertimeDetail;
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
public class EmployeeOutletTransferDataAutoD implements Runnable {

    private String message;
    private int recordSize = 0;
    private int progressSize = 0;
    private boolean running = false;
    private long sleepMs = 50;
    private Date dtStart;
    private Date dtFinish;
    private String codeLocationMesin;
    private boolean cbxOutlet;
    private boolean cbxSchedule;
    private boolean cbxScheduleSymbol;
    private boolean cbxKadivMapping;
    private String inputParam;

    public void run() {
        try {
            this.setRunning(true);
            this.setMessage("Process transfer data Employee  Outlet");
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText("Process transfer data Employee  Outlet");
            }
            String whereClause = "";

            if (getDtStart() != null && getDtFinish() != null && getCodeLocationMesin() != null && getCodeLocationMesin().length() > 0) {
                whereClause = " WHERE " + "\"" + Formater.formatDate(getDtFinish(), "yyyy-MM-dd 23:59:59") + "\" >= eo." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND  eo." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " >= \"" + Formater.formatDate(getDtStart(), "yyyy-MM-dd 00:00:00") + "\""
                        + " AND " + PstLocation.fieldNames[PstLocation.FLD_CODE] + " = \"" + getCodeLocationMesin() + "\"";
            }
//            else{
//                if (msgDownloadInformation != null) {
//                                msgDownloadInformation.setText("Date Is Not Selected");
//                }
//            }
            boolean sdhHabisDataTransOutlet = true;
            boolean sdhHabisDataTransSchedule = true;
            boolean sdhHabisDataTransMasterSch = true;
            while (this.running && (sdhHabisDataTransOutlet || sdhHabisDataTransSchedule || sdhHabisDataTransMasterSch)) {

                try {

                    Thread.sleep(this.getSleepMs() * 30);

                    int limit = 50;
                    String sql = "";
                    String sEmployeeId = "";
                    String sScheduleId = "";
                    if (isCbxKadivMapping()) {
                        try {
                            //if (whereClause != null && whereClause.length() > 0) {
                            recordSize = 0;
                            progressSize = 0; 
                            sEmployeeId = setTransferKadivPerOutlet(sql, "", sdhHabisDataTransOutlet);
                            
                            try {
                                    String periodId = "";
                                    sql = "";
                                    if (getDtStart() != null && getDtFinish() != null) {
                                        periodId = PstPeriod.oidPeriodByStartAndDate(getDtStart(), getDtFinish())+",";
                                    }
                                    if (isCbxKadivMapping()) {
                                        String xPeriod = "" + PstPeriod.getPeriodIdBySelectedDateString(Formater.formatDate(new Date(), "yyyy-MM-dd"));
                                        if (xPeriod != null && xPeriod.length() > 0 && !xPeriod.equalsIgnoreCase("0")) {
                                            periodId = periodId + xPeriod;
                                        }
                                    }
                                    if (sEmployeeId != null && sEmployeeId.length() > 0) {
                                        sEmployeeId = sEmployeeId.substring(0, sEmployeeId.length() - 1);
                                    }

                                    if (periodId != null && periodId.length() > 0 && sEmployeeId!=null && sEmployeeId.length()>0) {
                                        recordSize = 0;
                                        progressSize = 0;
                                        sScheduleId = setScheduleTransfer(sql, periodId, sEmployeeId, sdhHabisDataTransSchedule);
                                    }
                                } catch (Exception exc) {
                                    message = "Error transfer setScheduleTransfer" + exc;
                                }
                            //}
                        } catch (Exception exc) {
                            message = "Errror transfer setTransferOutlet" + exc;
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Errror transfer setTransferOutlet" + exc);
                            }
                        }
                    }
                    if (isCbxOutlet()) {
                        try {
                            if (whereClause != null && whereClause.length() > 0) {
                                recordSize = 0;
                                progressSize = 0;
                                String sEmployeeD=setTransferOutlet(sql, whereClause, sdhHabisDataTransOutlet);
                                String sEmployeeDx=setTransferOvertime(sql, whereClause, sdhHabisDataTransOutlet);
                                String sEmployeeDy=setTransferExtraSch(sql, whereClause, sdhHabisDataTransOutlet);
                                //sEmployeeId = sEmployeeId + (sEmployeeD!=null && sEmployeeD.length()>0? (sEmployeeD):sEmployeeD);
                                // sEmployeeId = sEmployeeId + (sEmployeeDx!=null && sEmployeeDx.length()>0? (","+sEmployeeDx):sEmployeeDx);
                                //  sEmployeeId = sEmployeeId + (sEmployeeDy!=null && sEmployeeDy.length()>0? (","+sEmployeeDy):sEmployeeDy);
                                sEmployeeId = sEmployeeId!=null && sEmployeeId.length()>0? (sEmployeeId+(sEmployeeD!=null && sEmployeeD.length()>0?(","+sEmployeeD):"")):sEmployeeD;
                                sEmployeeId = sEmployeeId!=null && sEmployeeId.length()>0? (sEmployeeId+(sEmployeeDx!=null && sEmployeeDx.length()>0?(","+sEmployeeDx):"")):sEmployeeDx;
                                sEmployeeId = sEmployeeId!=null && sEmployeeId.length()>0? (sEmployeeId+(sEmployeeDy!=null && sEmployeeDy.length()>0?(","+sEmployeeDy):"")):sEmployeeDy;
                                setTransferMappingOutlet();
                                try {
                                    String periodId = "";
                                    sql = "";
                                    if (getDtStart() != null && getDtFinish() != null) {
                                        periodId = PstPeriod.oidPeriodByStartAndDate(getDtStart(), getDtFinish());
                                    }
                                    if (sEmployeeId != null && sEmployeeId.length() > 0) {
                                        sEmployeeId = sEmployeeId.substring(0, sEmployeeId.length() - 1);
                                    }

                                    if (periodId != null && periodId.length() > 0 && sEmployeeId!=null && sEmployeeId.length()>0) {
                                        recordSize = 0;
                                        progressSize = 0;
                                        sScheduleId = setScheduleTransfer(sql, periodId, sEmployeeId, sdhHabisDataTransSchedule);
                                    }
                                } catch (Exception exc) {
                                    message = "Error transfer setScheduleTransfer" + exc;
                                }
                            }
                        } catch (Exception exc) {
                            message = "Errror transfer setTransferOutlet" + exc;
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Errror transfer setTransferOutlet" + exc);
                            }
                        }
                    }

                    if (isCbxScheduleSymbol()) {
                        try {
                            sql = "";
                            if (sScheduleId != null && sScheduleId.length() > 0) {
                                sScheduleId = sScheduleId.substring(0, sScheduleId.length() - 1);
                            }
                            recordSize = 0;
                            progressSize = 0;
                            setScheduleSymbolTransfer(sql, getInputParam(), sScheduleId);
                        } catch (Exception exc) {
                            message = "Error transfer setScheduleSymbolTransfer" + exc;
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
                    setRunning(false);
                    this.running = false;
                    if (btnRunDtEmployee != null) {
                        btnRunDtEmployee.setText("Run");
                    }

                }
            }



        } catch (Exception exc) {
            this.setMessage("Exception transfer Employee  Outlet" + exc);
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
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

    public static int getCountEmployeeOutletDownload(String whereClause, Date Start, Date End, String noMesin) {
        if (Start == null || End == null || noMesin == null || noMesin.length() == 0) {
            return 0;
        }
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ")";
            sql = sql + " FROM " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " AS eo "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON eo." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS pos ON pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " = emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " LEFT JOIN " + PstAppUser.TBL_APP_USER + " AS appuser ON appuser." + PstAppUser.fieldNames[PstAppUser.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " LEFT JOIN " + PstCareerPath.TBL_HR_WORK_HISTORY_NOW + " AS workemp ON workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstLocation.TBL_P2_LOCATION + " AS loc ON loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + "=eo." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_LOCATION_ID] + " AND loc." + PstLocation.fieldNames[PstLocation.FLD_CODE] + "=\"" + noMesin + "\"";

            if (Start != null && End != null) {
                sql = sql 
                        //+ " LEFT JOIN " + PstOvertimeDetail.TBL_OVERTIME_DETAIL + " AS otdetail ON otdetail." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND " + "\"" + Formater.formatDate(End, "yyyy-MM-dd HH:mm:59") + "\" >= otdetail." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " AND  otdetail." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + " >= \"" + Formater.formatDate(Start, "yyyy-MM-dd HH:mm:00") + "\"";
                        //+ " LEFT JOIN " + PstExtraScheduleOutletDetail.TBL_EXTRA_SCHEDULE_OUTLET_DETAIL + " AS extrasch ON extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND " + "\"" + Formater.formatDate(End, "yyyy-MM-dd HH:mm:59") + "\" >= extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_START_DATE_PLAN] + " AND  extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_END_DATE_PLAN] + " >= \"" + Formater.formatDate(Start, "yyyy-MM-dd HH:mm:00") + "\""
                        + " LEFT JOIN " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " AS empoutlet ON empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND " + "\"" + Formater.formatDate(End, "yyyy-MM-dd 23:59:59") + "\" >= empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND  empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " >= \"" + Formater.formatDate(Start, "yyyy-MM-dd 00:00:00") + "\"";
                //sql = sql + whereClause;
            }
            sql = sql + " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            //sql = sql + " GROUP BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            //      sql = sql + " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];



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
    
    public static int getCountEmployeeOvertimeDownload(String whereClause, Date Start, Date End, String noMesin) {
        if (Start == null || End == null || noMesin == null || noMesin.length() == 0) {
            return 0;
        }
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ")";
            sql = sql + " FROM " + PstOvertimeDetail.TBL_OVERTIME_DETAIL + " AS otdetail "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON otdetail." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS pos ON pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " = emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " LEFT JOIN " + PstAppUser.TBL_APP_USER + " AS appuser ON appuser." + PstAppUser.fieldNames[PstAppUser.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " LEFT JOIN " + PstCareerPath.TBL_HR_WORK_HISTORY_NOW + " AS workemp ON workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstLocation.TBL_P2_LOCATION + " AS loc ON loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + "=otdetail." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_LOCATION_ID] + " AND loc." + PstLocation.fieldNames[PstLocation.FLD_CODE] + "=\"" + noMesin + "\"";

            if (Start != null && End != null) {
                sql = sql 
                        + " WHERE " + "\"" + Formater.formatDate(End, "yyyy-MM-dd 23:59:59") + "\" >= otdetail." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " AND  otdetail." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + " >= \"" + Formater.formatDate(Start, "yyyy-MM-dd 00:00:00") + "\"";
                        //+ " LEFT JOIN " + PstExtraScheduleOutletDetail.TBL_EXTRA_SCHEDULE_OUTLET_DETAIL + " AS extrasch ON extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND " + "\"" + Formater.formatDate(End, "yyyy-MM-dd HH:mm:59") + "\" >= extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_START_DATE_PLAN] + " AND  extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_END_DATE_PLAN] + " >= \"" + Formater.formatDate(Start, "yyyy-MM-dd HH:mm:00") + "\""
                        //+ " LEFT JOIN " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " AS empoutlet ON empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND " + "\"" + Formater.formatDate(End, "yyyy-MM-dd HH:mm:59") + "\" >= empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND  empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " >= \"" + Formater.formatDate(Start, "yyyy-MM-dd HH:mm:00") + "\"";

            }
            sql = sql + " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            //sql = sql + " GROUP BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            //      sql = sql + " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];



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
    
    public static int getCountEmployeeExtraSchDownload(String whereClause, Date Start, Date End, String noMesin) {
        if (Start == null || End == null || noMesin == null || noMesin.length() == 0) {
            return 0;
        }
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ")";
            sql = sql + " FROM " + PstExtraScheduleOutletDetail.TBL_EXTRA_SCHEDULE_OUTLET_DETAIL + " AS extrasch "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON extrasch." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS pos ON pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " = emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " LEFT JOIN " + PstAppUser.TBL_APP_USER + " AS appuser ON appuser." + PstAppUser.fieldNames[PstAppUser.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " LEFT JOIN " + PstCareerPath.TBL_HR_WORK_HISTORY_NOW + " AS workemp ON workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstLocation.TBL_P2_LOCATION + " AS loc ON loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + "=extrasch." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_LOCATION_ID] + " AND loc." + PstLocation.fieldNames[PstLocation.FLD_CODE] + "=\"" + noMesin + "\"";

            if (Start != null && End != null) {
                sql = sql 
                        //+ " LEFT JOIN " + PstOvertimeDetail.TBL_OVERTIME_DETAIL + " AS otdetail ON otdetail." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND " + "\"" + Formater.formatDate(End, "yyyy-MM-dd HH:mm:59") + "\" >= otdetail." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " AND  otdetail." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + " >= \"" + Formater.formatDate(Start, "yyyy-MM-dd HH:mm:00") + "\""
                        +  " WHERE " + "\"" + Formater.formatDate(End, "yyyy-MM-dd 23:59:59") + "\" >= extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_START_DATE_PLAN] + " AND  extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_END_DATE_PLAN] + " >= \"" + Formater.formatDate(Start, "yyyy-MM-dd 00:00:00") + "\"";
                        //+ " LEFT JOIN " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " AS empoutlet ON empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND " + "\"" + Formater.formatDate(End, "yyyy-MM-dd HH:mm:59") + "\" >= empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND  empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " >= \"" + Formater.formatDate(Start, "yyyy-MM-dd HH:mm:00") + "\"";

            }
            sql = sql + " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            //sql = sql + " GROUP BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            //      sql = sql + " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];



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

    /**
     * count employee outlet
     *
     * @param whereClause
     * @param Start
     * @param End
     * @param noMesin
     * @return
     */
    public static int getCountEmpOutletMappingDownload(Date Start, Date End, String noMesin) {
        if (Start == null || End == null || noMesin == null || noMesin.length() == 0) {
            return 0;
        }

        DBResultSet dbrs = null;
        try {
            String sql = "SELECT  COUNT(empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_OUTLET_EMPLOYEE_ID] + ") FROM " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " AS empoutlet "
                    + " INNER JOIN " + PstLocation.TBL_P2_LOCATION + " AS loc ON loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + "=empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_LOCATION_ID] + " WHERE (1=1)";

            if (Start != null && End != null && noMesin != null && noMesin.length() > 0) {
                sql = sql + " AND " + "\"" + Formater.formatDate(End, "yyyy-MM-dd 23:59:59") + "\" >= empoutlet." + PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND  empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " >= \"" + Formater.formatDate(Start, "yyyy-MM-dd 00:00:00") + "\""
                        + " AND loc." + PstLocation.fieldNames[PstLocation.FLD_CODE] + "=\"" + noMesin + "\"";
            }


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

    public static int getCountEmployeeKadivDownload(String noMesin) {
        if (noMesin == null || noMesin.length() == 0) {
            return 0;
        }
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ")";
            sql = sql + " FROM " + PstMappingKadivMain.TBL_HR_MAPPING_KADIV_MAIN + " AS mkm "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON mkm." + PstMappingKadivMain.fieldNames[PstMappingKadivMain.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstMappingKadivDetail.TBL_HR_MAPPING_KADIV_DETAIL + " AS mkd ON mkm." + PstMappingKadivMain.fieldNames[PstMappingKadivMain.FLD_MAPPING_KADIV_MAIN_ID] + "=mkd." + PstMappingKadivDetail.fieldNames[PstMappingKadivDetail.FLD_MAPPING_KADIV_MAIN_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS pos ON pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " = emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " LEFT JOIN " + PstAppUser.TBL_APP_USER + " AS appuser ON appuser." + PstAppUser.fieldNames[PstAppUser.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " LEFT JOIN " + PstCareerPath.TBL_HR_WORK_HISTORY_NOW + " AS workemp ON workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstLocation.TBL_P2_LOCATION + " AS loc ON loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + "=mkd." + PstMappingKadivDetail.fieldNames[PstMappingKadivDetail.FLD_LOCATION_ID] + " AND loc." + PstLocation.fieldNames[PstLocation.FLD_CODE] + "=\"" + noMesin + "\"";
            sql = sql + " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            //sql = sql + " GROUP BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            //      sql = sql + " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];



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
    public String setTransferKadivPerOutlet(String sql, String whereClause, boolean sdhHabisData) throws SQLException {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        String empId = "";
        Hashtable hashEmployeeId = PstEmployeeDesktopTransfer.hashEmpIdAda(0, 0, "", "");
        Hashtable hashWorkHistory = PstCareerPathDestopTransfer.hashWorkHistoryAda(0, 0, "", "");
        Hashtable hashAppUser = PstAppUserDestopTransfer.hashUsersdhAda(0, 0, "", "");
        Hashtable hashPosition = PstPositionDestopTransfer.hashPositionSdhAda(0, 0, "", "");
        Hashtable hashLocation = PstLocationDestopTransfer.hashLocationSdhAda(0, 0, "", "");
        try {
            sql = "SELECT emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE_EMERGENCY] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_HANDPHONE]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_SEX] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_PERMANENT] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_EMG]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]
                    + ",pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + ",pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ",appuser." + PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]
                    + ",appuser." + PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD]
                    + ",appuser." + PstAppUser.fieldNames[PstAppUser.FLD_USER_ID]
                    + ",workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]
                    + ",workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]
                    + ",workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]
                    + ",loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]
                    + ",loc." + PstLocation.fieldNames[PstLocation.FLD_NAME]
                    + ",loc." + PstLocation.fieldNames[PstLocation.FLD_CODE];

            sql = sql + " FROM " + PstMappingKadivMain.TBL_HR_MAPPING_KADIV_MAIN + " AS mkm "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON mkm." + PstMappingKadivMain.fieldNames[PstMappingKadivMain.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstMappingKadivDetail.TBL_HR_MAPPING_KADIV_DETAIL + " AS mkd ON mkm." + PstMappingKadivMain.fieldNames[PstMappingKadivMain.FLD_MAPPING_KADIV_MAIN_ID] + "=mkd." + PstMappingKadivDetail.fieldNames[PstMappingKadivDetail.FLD_MAPPING_KADIV_MAIN_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS pos ON pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " = emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " LEFT JOIN " + PstAppUser.TBL_APP_USER + " AS appuser ON appuser." + PstAppUser.fieldNames[PstAppUser.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " LEFT JOIN " + PstCareerPath.TBL_HR_WORK_HISTORY_NOW + " AS workemp ON workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstLocation.TBL_P2_LOCATION + " AS loc ON loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + "=mkd." + PstMappingKadivDetail.fieldNames[PstMappingKadivDetail.FLD_LOCATION_ID] + " AND loc." + PstLocation.fieldNames[PstLocation.FLD_CODE] + "=\"" + getCodeLocationMesin() + "\"";

            sql = sql + " GROUP BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            sql = sql + " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            //System.out.println(sql);
                  /*if(jTextArea!=null){
             jTextArea.setText(sql);
             }*/
            //System.out.println("Status running"+sql);
            message = "Run query download data employee kadiv " + sql;
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText("Run query download data employee kadiv" + sql);
            }
            int theSize = getCountEmployeeKadivDownload(getCodeLocationMesin());
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

            message = " Total  download data employee kadiv" + theSize;
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText(" Total  download data employee kadiv" + theSize);
            }
            dbrs = DBHandler.execQueryResult(sql);
            rs = dbrs.getResultSet();


            while (rs != null && rs.next() && theSize > 0 && isRunning()) {

                Thread.sleep(this.getSleepMs());
                TabelEmployeeOutletTransferData tabelEmployeeOutletTransferData = new TabelEmployeeOutletTransferData();
                tabelEmployeeOutletTransferData.setEmployeeId(rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                tabelEmployeeOutletTransferData.setFullName(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                tabelEmployeeOutletTransferData.setEmployeeNumber(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                tabelEmployeeOutletTransferData.setPhone(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                tabelEmployeeOutletTransferData.setPhoneEmergency(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE_EMERGENCY]));
                tabelEmployeeOutletTransferData.setAddress(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                tabelEmployeeOutletTransferData.setPermanentAddress(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_PERMANENT]));
                tabelEmployeeOutletTransferData.setAddressEmg(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_EMG]));
                tabelEmployeeOutletTransferData.setEmpPin(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));
                tabelEmployeeOutletTransferData.setEmpCategoryId(rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                tabelEmployeeOutletTransferData.setHandPhone(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_HANDPHONE]));
                tabelEmployeeOutletTransferData.setSex(rs.getInt("emp." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
                tabelEmployeeOutletTransferData.setCommencingDate(rs.getDate("emp." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                tabelEmployeeOutletTransferData.setResigned(rs.getInt("emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]));
                tabelEmployeeOutletTransferData.setResignedDate(rs.getDate("emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]));
                tabelEmployeeOutletTransferData.setBarcodeNumber(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));


                tabelEmployeeOutletTransferData.setPositionId(rs.getLong("pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]));
                tabelEmployeeOutletTransferData.setPositionName(rs.getString("pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION]));

                tabelEmployeeOutletTransferData.setLoginId(rs.getString("appuser." + PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]));
                tabelEmployeeOutletTransferData.setPassword(rs.getString("appuser." + PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD]));
                tabelEmployeeOutletTransferData.setUserId(rs.getLong("appuser." + PstAppUser.fieldNames[PstAppUser.FLD_USER_ID]));

                tabelEmployeeOutletTransferData.setWorkHistoryId(rs.getLong("workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]));
                tabelEmployeeOutletTransferData.setWorkHistoryDateStart(rs.getDate("workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]));
                tabelEmployeeOutletTransferData.setWorkHistoryDateEnd(rs.getDate("workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]));

                tabelEmployeeOutletTransferData.setLocationId(rs.getLong("loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]));
                tabelEmployeeOutletTransferData.setLocationName(rs.getString("loc." + PstLocation.fieldNames[PstLocation.FLD_NAME]));
                tabelEmployeeOutletTransferData.setLocationCode(rs.getString("loc." + PstLocation.fieldNames[PstLocation.FLD_CODE]));




                //insert data employee
                if (tabelEmployeeOutletTransferData.getEmployeeId() != 0) {
                    empId = empId + tabelEmployeeOutletTransferData.getEmployeeId() + ",";
                    if (hashEmployeeId.size() == 0 || hashEmployeeId.containsKey("" + tabelEmployeeOutletTransferData.getEmployeeId()) == false) {
                        try {
                            PstEmployeeDesktopTransfer.insertExc(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  kadiv" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  kadiv" + exc);
                            }
                        }

                        //hashEmployeeId.put("" + tabelEmployeeOutletTransferData.getEmployeeId(), tabelEmployeeOutletTransferData.getFullName());

                        message = " Insert Data Employee " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Employee " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    } else {
                        PstEmployeeDesktopTransfer.updateExc(tabelEmployeeOutletTransferData);
                        message = " Update Data Employee " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Employee " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                        //String fullName = hashEmployeeId == null || hashEmployeeId.get(employeeTransfer.getEmployeeId()) == null ? "" : (String) hashEmployeeId.get(employeeTransfer.getEmployeeId());
                        //message = fullName.length() == 0 ? " Data Employee already exis" : " Data Employee already exis " + fullName + " In local";
                    }
                }

                //insert data hr_work_histori
                if (tabelEmployeeOutletTransferData.getWorkHistoryId() != 0) {
                    if ((hashWorkHistory.size() == 0 || hashWorkHistory.containsKey("" + tabelEmployeeOutletTransferData.getWorkHistoryId()) == false)) {
                        try {
                            PstCareerPathDestopTransfer.insertExc(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  kadiv" + exc);
                        }
                        //hashEmployeeId.put("" + tabelEmployeeOutletTransferData.getEmployeeId(), tabelEmployeeOutletTransferData.getFullName());

                        message = " Insert Data Work History " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Work History " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    } else {
                        PstCareerPathDestopTransfer.updateExc(tabelEmployeeOutletTransferData);
                        message = " Update Data Work History " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Work History " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                        //String fullName = hashEmployeeId == null || hashEmployeeId.get(employeeTransfer.getEmployeeId()) == null ? "" : (String) hashEmployeeId.get(employeeTransfer.getEmployeeId());
                        //message = fullName.length() == 0 ? " Data Employee already exis" : " Data Employee already exis " + fullName + " In local";
                    }
                }

                //insert data user 
                if (tabelEmployeeOutletTransferData.getUserId() != 0) {
                    if (hashAppUser.size() == 0 || hashAppUser.containsKey("" + tabelEmployeeOutletTransferData.getUserId()) == false) {
                        try {
                            PstAppUserDestopTransfer.insert(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  kadiv" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                            }
                        }

                        //hashAppUser.put("" + tabelEmployeeOutletTransferData.getUserId(), "" + tabelEmployeeOutletTransferData.getLoginId());
                        message = " Insert  Data User Login " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert  Data User Login " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    } else {
                        PstAppUserDestopTransfer.update(tabelEmployeeOutletTransferData);
                        message = " Update Data User Login " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data User Login " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    }
                }

                //insert data position
                if (tabelEmployeeOutletTransferData.getPositionId() != 0) {
                    if (hashPosition.size() == 0 || hashPosition.containsKey("" + tabelEmployeeOutletTransferData.getPositionId()) == false) {
                        try {
                            PstPositionDestopTransfer.insertExc(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  kadiv" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  kadiv" + exc);
                            }
                        }
                        //hashEmployeeId.put("" + tabelEmployeeOutletTransferData.getPositionId(), tabelEmployeeOutletTransferData.getFullName());

                        message = " Insert Data Position " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Position " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    } else {
                        PstPositionDestopTransfer.updateExc(tabelEmployeeOutletTransferData);
                        message = " Update Data Position " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Position " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                        //String fullName = hashEmployeeId == null || hashEmployeeId.get(employeeTransfer.getEmployeeId()) == null ? "" : (String) hashEmployeeId.get(employeeTransfer.getEmployeeId());
                        //message = fullName.length() == 0 ? " Data Employee already exis" : " Data Employee already exis " + fullName + " In local";
                    }
                }



                //insert data hashLocation 
                if (tabelEmployeeOutletTransferData.getLocationId() != 0) {
                    if (hashLocation.size() == 0 || hashLocation.containsKey("" + tabelEmployeeOutletTransferData.getLocationId()) == false) {
                        try {
                            PstLocationDestopTransfer.insertExc(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                            }
                        }

                        //hashLocation.put("" + tabelEmployeeOutletTransferData.getLocationId(), tabelEmployeeOutletTransferData.getLocationName());
                        message = " Insert Data Position " + tabelEmployeeOutletTransferData.getPositionName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Position " + tabelEmployeeOutletTransferData.getPositionName() + " In local");
                        }
                    } else {

                        PstLocationDestopTransfer.updateExc(tabelEmployeeOutletTransferData);
                        message = " Update Data Position " + tabelEmployeeOutletTransferData.getPositionName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Position " + tabelEmployeeOutletTransferData.getPositionName() + " In local");
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

    public String setTransferMappingOutlet() throws SQLException {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        String empMappingOutletId = "";
        String whereClause = "";
        Hashtable hashEmployeeOutlet = null;
        String StrEmployeeOutletWithDate = "";
        String wherenew = "";
        if (getDtStart() != null && getDtFinish() != null && getCodeLocationMesin() != null && getCodeLocationMesin().length() > 0) {
                wherenew = "" + "\"" + Formater.formatDate(getDtFinish(), "yyyy-MM-dd 23:59:59") + "\" >= empoutlet." + PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND  empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " >= \"" + Formater.formatDate(getDtStart(), "yyyy-MM-dd 00:00:00") + "\""
                        + " AND loc." + PstLocation.fieldNames[PstLocation.FLD_CODE] + "=\"" + getCodeLocationMesin() + "\"";
            StrEmployeeOutletWithDate = PstEmployeeOutletDesktop.StrEmpMappingIdAdaWithDate(0, 0, wherenew, "");
        }
        try {
            String sql = "SELECT empoutlet.*,emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " FROM " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " AS empoutlet "
                    + " INNER JOIN " + PstLocation.TBL_P2_LOCATION + " AS loc ON loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + "=empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_LOCATION_ID]
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID]
                    + " WHERE (1=1)";

            if (getDtStart() != null && getDtFinish() != null && getCodeLocationMesin() != null && getCodeLocationMesin().length() > 0) {
                sql = sql + " AND " + "\"" + Formater.formatDate(getDtFinish(), "yyyy-MM-dd 23:59:59") + "\" >= empoutlet." + PstEmployeeOutletDesktop.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND  empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " >= \"" + Formater.formatDate(getDtStart(), "yyyy-MM-dd 00:00:00") + "\""
                        + " AND loc." + PstLocation.fieldNames[PstLocation.FLD_CODE] + "=\"" + getCodeLocationMesin() + "\"";
            }

            message = "Run query download data employee " + sql;
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText("Run query download data emp outlet " + sql);
            }
            int theSize = getCountEmpOutletMappingDownload(getDtStart(), getDtFinish(), getCodeLocationMesin());
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

            message = " Total  download data employee outlet" + theSize;
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText(" Total  download data employee outlet" + theSize);
            }
            dbrs = DBHandler.execQueryResult(sql);
            rs = dbrs.getResultSet();
            
            
            if (rs !=null && getDtStart() != null && getDtFinish() != null && getCodeLocationMesin() != null && getCodeLocationMesin().length() > 0){
                try{
                            Date dt = EmployeeOutletTransferDataAutoD.deleteByEmpOutlet(StrEmployeeOutletWithDate);
                }catch(Exception e) {
                            System.out.println("delete data mapping outlet hari ini gagal");
                }
            hashEmployeeOutlet = PstEmployeeOutletDesktop.hashEmpMappingIdAda(0, 0, "", "");
            }

            while (rs != null && rs.next() && theSize > 0 && isRunning()) {

                Thread.sleep(this.getSleepMs());
                EmployeeOutlet employeeOutlet = new EmployeeOutlet();
                PstEmployeeOutletDesktop.resultToObject(rs, employeeOutlet);
                employeeOutlet.setEmployeeName(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));

                
                
                //insert data employee
                if (employeeOutlet.getOID() != 0) {
                    empMappingOutletId = empMappingOutletId + employeeOutlet.getOID() + ",";
                    if (hashEmployeeOutlet.size() == 0 || hashEmployeeOutlet.containsKey("" + employeeOutlet.getOID()) == false) {
                        
                        
                        
                        try {
                            PstEmployeeOutletDesktop.insertExc(employeeOutlet);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                            }
                        }
                        
                        
                        
                        message = " Insert Data Employee outlet mapping" + employeeOutlet.getEmployeeName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Employee mapping" + employeeOutlet.getEmployeeName() + " In local");
                        }
                    } else {
                        PstEmployeeOutletDesktop.updateExc(employeeOutlet);
                        
                       
                        
                        message = " Update Data Employee mapping" + employeeOutlet.getEmployeeName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Employee mapping" + employeeOutlet.getEmployeeName() + " In local");
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
            return empMappingOutletId;
        }
    }

    public String setTransferOutlet(String sql, String whereClause, boolean sdhHabisData) throws SQLException {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        String empId = "";
        Hashtable hashEmployeeId = PstEmployeeDesktopTransfer.hashEmpIdAda(0, 0, "", "");
        Hashtable hashWorkHistory = PstCareerPathDestopTransfer.hashWorkHistoryAda(0, 0, "", "");
        Hashtable hashAppUser = PstAppUserDestopTransfer.hashUsersdhAda(0, 0, "", "");
        Hashtable hashPosition = PstPositionDestopTransfer.hashPositionSdhAda(0, 0, "", "");
        Hashtable hashLocation = PstLocationDestopTransfer.hashLocationSdhAda(0, 0, "", "");
        try {
            sql = "SELECT emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE_EMERGENCY] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_HANDPHONE]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_SEX] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_PERMANENT] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_EMG]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]
                    + ",pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + ",pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ",appuser." + PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]
                    + ",appuser." + PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD]
                    + ",appuser." + PstAppUser.fieldNames[PstAppUser.FLD_USER_ID]
                    + ",workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]
                    + ",workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]
                    + ",workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]
                    + ",loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]
                    + ",loc." + PstLocation.fieldNames[PstLocation.FLD_NAME]
                    + ",loc." + PstLocation.fieldNames[PstLocation.FLD_CODE];

            sql = sql + " FROM " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " AS eo "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON eo." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS pos ON pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " = emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " LEFT JOIN " + PstAppUser.TBL_APP_USER + " AS appuser ON appuser." + PstAppUser.fieldNames[PstAppUser.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " LEFT JOIN " + PstCareerPath.TBL_HR_WORK_HISTORY_NOW + " AS workemp ON workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstLocation.TBL_P2_LOCATION + " AS loc ON loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + "=eo." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_LOCATION_ID] + " AND loc." + PstLocation.fieldNames[PstLocation.FLD_CODE] + "=\"" + getCodeLocationMesin() + "\"";

            /*if (getDtStart() != null && getDtFinish() != null) {
                sql = sql 
                        + " LEFT JOIN " + PstOvertimeDetail.TBL_OVERTIME_DETAIL + " AS otdetail ON otdetail." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND " + "\"" + Formater.formatDate(getDtFinish(), "yyyy-MM-dd HH:mm:59") + "\" >= otdetail." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " AND  otdetail." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + " >= \"" + Formater.formatDate(getDtStart(), "yyyy-MM-dd HH:mm:00") + "\""
                        + " LEFT JOIN " + PstExtraScheduleOutletDetail.TBL_EXTRA_SCHEDULE_OUTLET_DETAIL + " AS extrasch ON extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND " + "\"" + Formater.formatDate(getDtFinish(), "yyyy-MM-dd HH:mm:59") + "\" >= extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_START_DATE_PLAN] + " AND  extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_END_DATE_PLAN] + " >= \"" + Formater.formatDate(getDtStart(), "yyyy-MM-dd HH:mm:00") + "\""
                        //+ " LEFT JOIN " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " AS empoutlet ON empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND " + "\"" + Formater.formatDate(getDtFinish(), "yyyy-MM-dd HH:mm:59") + "\" >= empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND  empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " >= \"" + Formater.formatDate(getDtStart(), "yyyy-MM-dd HH:mm:00") + "\"";

            }*/

            if (getDtStart() != null && getDtFinish() != null) {
                sql = sql + whereClause;
            }

            sql = sql + " GROUP BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            sql = sql + " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            //System.out.println(sql);
                  /*if(jTextArea!=null){
             jTextArea.setText(sql);
             }*/
            //System.out.println("Status running"+sql);
            message = "Run query download data employee " + sql;
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText("Run query download data employee " + sql);
            }
            int theSize = getCountEmployeeOutletDownload(whereClause, getDtStart(), getDtFinish(), getCodeLocationMesin());
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
                msgDownloadInformation.setText(" Total  download data employee " + theSize);
            }
            dbrs = DBHandler.execQueryResult(sql);
            rs = dbrs.getResultSet();


            while (rs != null && rs.next() && theSize > 0 && isRunning()) {

                Thread.sleep(this.getSleepMs());
                TabelEmployeeOutletTransferData tabelEmployeeOutletTransferData = new TabelEmployeeOutletTransferData();
                tabelEmployeeOutletTransferData.setEmployeeId(rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                tabelEmployeeOutletTransferData.setFullName(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                tabelEmployeeOutletTransferData.setEmployeeNumber(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                tabelEmployeeOutletTransferData.setPhone(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                tabelEmployeeOutletTransferData.setPhoneEmergency(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE_EMERGENCY]));
                tabelEmployeeOutletTransferData.setAddress(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                tabelEmployeeOutletTransferData.setPermanentAddress(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_PERMANENT]));
                tabelEmployeeOutletTransferData.setAddressEmg(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_EMG]));
                tabelEmployeeOutletTransferData.setEmpPin(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));
                tabelEmployeeOutletTransferData.setEmpCategoryId(rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                tabelEmployeeOutletTransferData.setHandPhone(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_HANDPHONE]));
                tabelEmployeeOutletTransferData.setSex(rs.getInt("emp." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
                tabelEmployeeOutletTransferData.setCommencingDate(rs.getDate("emp." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                tabelEmployeeOutletTransferData.setResigned(rs.getInt("emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]));
                tabelEmployeeOutletTransferData.setResignedDate(rs.getDate("emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]));
                tabelEmployeeOutletTransferData.setBarcodeNumber(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));


                tabelEmployeeOutletTransferData.setPositionId(rs.getLong("pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]));
                tabelEmployeeOutletTransferData.setPositionName(rs.getString("pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION]));

                tabelEmployeeOutletTransferData.setLoginId(rs.getString("appuser." + PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]));
                tabelEmployeeOutletTransferData.setPassword(rs.getString("appuser." + PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD]));
                tabelEmployeeOutletTransferData.setUserId(rs.getLong("appuser." + PstAppUser.fieldNames[PstAppUser.FLD_USER_ID]));

                tabelEmployeeOutletTransferData.setWorkHistoryId(rs.getLong("workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]));
                tabelEmployeeOutletTransferData.setWorkHistoryDateStart(rs.getDate("workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]));
                tabelEmployeeOutletTransferData.setWorkHistoryDateEnd(rs.getDate("workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]));

                tabelEmployeeOutletTransferData.setLocationId(rs.getLong("loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]));
                tabelEmployeeOutletTransferData.setLocationName(rs.getString("loc." + PstLocation.fieldNames[PstLocation.FLD_NAME]));
                tabelEmployeeOutletTransferData.setLocationCode(rs.getString("loc." + PstLocation.fieldNames[PstLocation.FLD_CODE]));




                //insert data employee
                if (tabelEmployeeOutletTransferData.getEmployeeId() != 0) {
                    empId = empId + tabelEmployeeOutletTransferData.getEmployeeId() + ",";
                    if (hashEmployeeId.size() == 0 || hashEmployeeId.containsKey("" + tabelEmployeeOutletTransferData.getEmployeeId()) == false) {
                        try {
                            PstEmployeeDesktopTransfer.insertExc(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                            }
                        }

                        //hashEmployeeId.put("" + tabelEmployeeOutletTransferData.getEmployeeId(), tabelEmployeeOutletTransferData.getFullName());

                        message = " Insert Data Employee " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Employee " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    } else {
                        PstEmployeeDesktopTransfer.updateExc(tabelEmployeeOutletTransferData);
                        message = " Update Data Employee " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Employee " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                        //String fullName = hashEmployeeId == null || hashEmployeeId.get(employeeTransfer.getEmployeeId()) == null ? "" : (String) hashEmployeeId.get(employeeTransfer.getEmployeeId());
                        //message = fullName.length() == 0 ? " Data Employee already exis" : " Data Employee already exis " + fullName + " In local";
                    }
                }

                //insert data hr_work_histori
                if (tabelEmployeeOutletTransferData.getWorkHistoryId() != 0) {
                    if ((hashWorkHistory.size() == 0 || hashWorkHistory.containsKey("" + tabelEmployeeOutletTransferData.getWorkHistoryId()) == false)) {
                        try {
                            PstCareerPathDestopTransfer.insertExc(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                        }
                        //hashEmployeeId.put("" + tabelEmployeeOutletTransferData.getEmployeeId(), tabelEmployeeOutletTransferData.getFullName());

                        message = " Insert Data Work History " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Work History " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    } else {
                        PstCareerPathDestopTransfer.updateExc(tabelEmployeeOutletTransferData);
                        message = " Update Data Work History " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Work History " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                        //String fullName = hashEmployeeId == null || hashEmployeeId.get(employeeTransfer.getEmployeeId()) == null ? "" : (String) hashEmployeeId.get(employeeTransfer.getEmployeeId());
                        //message = fullName.length() == 0 ? " Data Employee already exis" : " Data Employee already exis " + fullName + " In local";
                    }
                }

                //insert data user 
                if (tabelEmployeeOutletTransferData.getUserId() != 0) {
                    if (hashAppUser.size() == 0 || hashAppUser.containsKey("" + tabelEmployeeOutletTransferData.getUserId()) == false) {
                        try {
                            PstAppUserDestopTransfer.insert(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                            }
                        }

                        //hashAppUser.put("" + tabelEmployeeOutletTransferData.getUserId(), "" + tabelEmployeeOutletTransferData.getLoginId());
                        message = " Insert  Data User Login " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert  Data User Login " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    } else {
                        PstAppUserDestopTransfer.update(tabelEmployeeOutletTransferData);
                        message = " Update Data User Login " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data User Login " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    }
                }

                //insert data position
                if (tabelEmployeeOutletTransferData.getPositionId() != 0) {
                    if (hashPosition.size() == 0 || hashPosition.containsKey("" + tabelEmployeeOutletTransferData.getPositionId()) == false) {
                        try {
                            PstPositionDestopTransfer.insertExc(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                            }
                        }
                        //hashEmployeeId.put("" + tabelEmployeeOutletTransferData.getPositionId(), tabelEmployeeOutletTransferData.getFullName());

                        message = " Insert Data Position " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Position " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    } else {
                        PstPositionDestopTransfer.updateExc(tabelEmployeeOutletTransferData);
                        message = " Update Data Position " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Position " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                        //String fullName = hashEmployeeId == null || hashEmployeeId.get(employeeTransfer.getEmployeeId()) == null ? "" : (String) hashEmployeeId.get(employeeTransfer.getEmployeeId());
                        //message = fullName.length() == 0 ? " Data Employee already exis" : " Data Employee already exis " + fullName + " In local";
                    }
                }



                //insert data hashLocation 
                if (tabelEmployeeOutletTransferData.getLocationId() != 0) {
                    if (hashLocation.size() == 0 || hashLocation.containsKey("" + tabelEmployeeOutletTransferData.getLocationId()) == false) {
                        try {
                            PstLocationDestopTransfer.insertExc(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                            }
                        }

                        //hashLocation.put("" + tabelEmployeeOutletTransferData.getLocationId(), tabelEmployeeOutletTransferData.getLocationName());
                        message = " Insert Data Position " + tabelEmployeeOutletTransferData.getPositionName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Position " + tabelEmployeeOutletTransferData.getPositionName() + " In local");
                        }
                    } else {

                        PstLocationDestopTransfer.updateExc(tabelEmployeeOutletTransferData);
                        message = " Update Data Position " + tabelEmployeeOutletTransferData.getPositionName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Position " + tabelEmployeeOutletTransferData.getPositionName() + " In local");
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

    public String setTransferOvertime(String sql, String whereClause, boolean sdhHabisData) throws SQLException {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        String empId = "";
        Hashtable hashEmployeeId = PstEmployeeDesktopTransfer.hashEmpIdAda(0, 0, "", "");
        Hashtable hashWorkHistory = PstCareerPathDestopTransfer.hashWorkHistoryAda(0, 0, "", "");
        Hashtable hashAppUser = PstAppUserDestopTransfer.hashUsersdhAda(0, 0, "", "");
        Hashtable hashPosition = PstPositionDestopTransfer.hashPositionSdhAda(0, 0, "", "");
        Hashtable hashLocation = PstLocationDestopTransfer.hashLocationSdhAda(0, 0, "", "");
        try {
            sql = "SELECT emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE_EMERGENCY] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_HANDPHONE]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_SEX] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_PERMANENT] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_EMG]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]
                    + ",pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + ",pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ",appuser." + PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]
                    + ",appuser." + PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD]
                    + ",appuser." + PstAppUser.fieldNames[PstAppUser.FLD_USER_ID]
                    + ",workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]
                    + ",workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]
                    + ",workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]
                    + ",loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]
                    + ",loc." + PstLocation.fieldNames[PstLocation.FLD_NAME]
                    + ",loc." + PstLocation.fieldNames[PstLocation.FLD_CODE];

            sql = sql + " FROM " + PstOvertimeDetail.TBL_OVERTIME_DETAIL + " AS ot "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON ot." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS pos ON pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " = emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " LEFT JOIN " + PstAppUser.TBL_APP_USER + " AS appuser ON appuser." + PstAppUser.fieldNames[PstAppUser.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " LEFT JOIN " + PstCareerPath.TBL_HR_WORK_HISTORY_NOW + " AS workemp ON workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstLocation.TBL_P2_LOCATION + " AS loc ON loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + "=ot." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_LOCATION_ID] + " AND loc." + PstLocation.fieldNames[PstLocation.FLD_CODE] + "=\"" + getCodeLocationMesin() + "\"";

            if (getDtStart() != null && getDtFinish() != null) {
                sql = sql 
                        +  " WHERE " + "\"" + Formater.formatDate(getDtFinish(), "yyyy-MM-dd 23:59:59") + "\" >= ot." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM] + " AND  ot." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + " >= \"" + Formater.formatDate(getDtStart(), "yyyy-MM-dd 00:00:00") + "\"";
                       // + " LEFT JOIN " + PstExtraScheduleOutletDetail.TBL_EXTRA_SCHEDULE_OUTLET_DETAIL + " AS extrasch ON extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND " + "\"" + Formater.formatDate(getDtFinish(), "yyyy-MM-dd HH:mm:59") + "\" >= extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_START_DATE_PLAN] + " AND  extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_END_DATE_PLAN] + " >= \"" + Formater.formatDate(getDtStart(), "yyyy-MM-dd HH:mm:00") + "\""
                       // + " LEFT JOIN " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " AS empoutlet ON empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND " + "\"" + Formater.formatDate(getDtFinish(), "yyyy-MM-dd HH:mm:59") + "\" >= empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND  empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " >= \"" + Formater.formatDate(getDtStart(), "yyyy-MM-dd HH:mm:00") + "\"";

            }

           /* if (getDtStart() != null && getDtFinish() != null) {
                sql = sql + whereClause;
            }*/

            sql = sql + " GROUP BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            sql = sql + " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            //System.out.println(sql);
                  /*if(jTextArea!=null){
             jTextArea.setText(sql);
             }*/
            //System.out.println("Status running"+sql);
            message = "Run query download data employee " + sql;
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText("Run query download data employee " + sql);
            }
            int theSize = getCountEmployeeOvertimeDownload(whereClause, getDtStart(), getDtFinish(), getCodeLocationMesin());
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
                msgDownloadInformation.setText(" Total  download data employee " + theSize);
            }
            dbrs = DBHandler.execQueryResult(sql);
            rs = dbrs.getResultSet();


            while (rs != null && rs.next() && theSize > 0 && isRunning()) {

                Thread.sleep(this.getSleepMs());
                TabelEmployeeOutletTransferData tabelEmployeeOutletTransferData = new TabelEmployeeOutletTransferData();
                tabelEmployeeOutletTransferData.setEmployeeId(rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                tabelEmployeeOutletTransferData.setFullName(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                tabelEmployeeOutletTransferData.setEmployeeNumber(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                tabelEmployeeOutletTransferData.setPhone(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                tabelEmployeeOutletTransferData.setPhoneEmergency(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE_EMERGENCY]));
                tabelEmployeeOutletTransferData.setAddress(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                tabelEmployeeOutletTransferData.setPermanentAddress(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_PERMANENT]));
                tabelEmployeeOutletTransferData.setAddressEmg(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_EMG]));
                tabelEmployeeOutletTransferData.setEmpPin(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));
                tabelEmployeeOutletTransferData.setEmpCategoryId(rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                tabelEmployeeOutletTransferData.setHandPhone(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_HANDPHONE]));
                tabelEmployeeOutletTransferData.setSex(rs.getInt("emp." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
                tabelEmployeeOutletTransferData.setCommencingDate(rs.getDate("emp." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                tabelEmployeeOutletTransferData.setResigned(rs.getInt("emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]));
                tabelEmployeeOutletTransferData.setResignedDate(rs.getDate("emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]));
                tabelEmployeeOutletTransferData.setBarcodeNumber(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));


                tabelEmployeeOutletTransferData.setPositionId(rs.getLong("pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]));
                tabelEmployeeOutletTransferData.setPositionName(rs.getString("pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION]));

                tabelEmployeeOutletTransferData.setLoginId(rs.getString("appuser." + PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]));
                tabelEmployeeOutletTransferData.setPassword(rs.getString("appuser." + PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD]));
                tabelEmployeeOutletTransferData.setUserId(rs.getLong("appuser." + PstAppUser.fieldNames[PstAppUser.FLD_USER_ID]));

                tabelEmployeeOutletTransferData.setWorkHistoryId(rs.getLong("workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]));
                tabelEmployeeOutletTransferData.setWorkHistoryDateStart(rs.getDate("workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]));
                tabelEmployeeOutletTransferData.setWorkHistoryDateEnd(rs.getDate("workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]));

                tabelEmployeeOutletTransferData.setLocationId(rs.getLong("loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]));
                tabelEmployeeOutletTransferData.setLocationName(rs.getString("loc." + PstLocation.fieldNames[PstLocation.FLD_NAME]));
                tabelEmployeeOutletTransferData.setLocationCode(rs.getString("loc." + PstLocation.fieldNames[PstLocation.FLD_CODE]));




                //insert data employee
                if (tabelEmployeeOutletTransferData.getEmployeeId() != 0) {
                    empId = empId + tabelEmployeeOutletTransferData.getEmployeeId() + ",";
                    if (hashEmployeeId.size() == 0 || hashEmployeeId.containsKey("" + tabelEmployeeOutletTransferData.getEmployeeId()) == false) {
                        try {
                            PstEmployeeDesktopTransfer.insertExc(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                            }
                        }

                        //hashEmployeeId.put("" + tabelEmployeeOutletTransferData.getEmployeeId(), tabelEmployeeOutletTransferData.getFullName());

                        message = " Insert Data Employee " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Employee " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    } else {
                        PstEmployeeDesktopTransfer.updateExc(tabelEmployeeOutletTransferData);
                        message = " Update Data Employee " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Employee " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                        //String fullName = hashEmployeeId == null || hashEmployeeId.get(employeeTransfer.getEmployeeId()) == null ? "" : (String) hashEmployeeId.get(employeeTransfer.getEmployeeId());
                        //message = fullName.length() == 0 ? " Data Employee already exis" : " Data Employee already exis " + fullName + " In local";
                    }
                }

                //insert data hr_work_histori
                if (tabelEmployeeOutletTransferData.getWorkHistoryId() != 0) {
                    if ((hashWorkHistory.size() == 0 || hashWorkHistory.containsKey("" + tabelEmployeeOutletTransferData.getWorkHistoryId()) == false)) {
                        try {
                            PstCareerPathDestopTransfer.insertExc(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                        }
                        //hashEmployeeId.put("" + tabelEmployeeOutletTransferData.getEmployeeId(), tabelEmployeeOutletTransferData.getFullName());

                        message = " Insert Data Work History " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Work History " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    } else {
                        PstCareerPathDestopTransfer.updateExc(tabelEmployeeOutletTransferData);
                        message = " Update Data Work History " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Work History " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                        //String fullName = hashEmployeeId == null || hashEmployeeId.get(employeeTransfer.getEmployeeId()) == null ? "" : (String) hashEmployeeId.get(employeeTransfer.getEmployeeId());
                        //message = fullName.length() == 0 ? " Data Employee already exis" : " Data Employee already exis " + fullName + " In local";
                    }
                }

                //insert data user 
                if (tabelEmployeeOutletTransferData.getUserId() != 0) {
                    if (hashAppUser.size() == 0 || hashAppUser.containsKey("" + tabelEmployeeOutletTransferData.getUserId()) == false) {
                        try {
                            PstAppUserDestopTransfer.insert(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                            }
                        }

                        //hashAppUser.put("" + tabelEmployeeOutletTransferData.getUserId(), "" + tabelEmployeeOutletTransferData.getLoginId());
                        message = " Insert  Data User Login " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert  Data User Login " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    } else {
                        PstAppUserDestopTransfer.update(tabelEmployeeOutletTransferData);
                        message = " Update Data User Login " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data User Login " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    }
                }

                //insert data position
                if (tabelEmployeeOutletTransferData.getPositionId() != 0) {
                    if (hashPosition.size() == 0 || hashPosition.containsKey("" + tabelEmployeeOutletTransferData.getPositionId()) == false) {
                        try {
                            PstPositionDestopTransfer.insertExc(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                            }
                        }
                        //hashEmployeeId.put("" + tabelEmployeeOutletTransferData.getPositionId(), tabelEmployeeOutletTransferData.getFullName());

                        message = " Insert Data Position " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Position " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    } else {
                        PstPositionDestopTransfer.updateExc(tabelEmployeeOutletTransferData);
                        message = " Update Data Position " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Position " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                        //String fullName = hashEmployeeId == null || hashEmployeeId.get(employeeTransfer.getEmployeeId()) == null ? "" : (String) hashEmployeeId.get(employeeTransfer.getEmployeeId());
                        //message = fullName.length() == 0 ? " Data Employee already exis" : " Data Employee already exis " + fullName + " In local";
                    }
                }



                //insert data hashLocation 
                if (tabelEmployeeOutletTransferData.getLocationId() != 0) {
                    if (hashLocation.size() == 0 || hashLocation.containsKey("" + tabelEmployeeOutletTransferData.getLocationId()) == false) {
                        try {
                            PstLocationDestopTransfer.insertExc(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                            }
                        }

                        //hashLocation.put("" + tabelEmployeeOutletTransferData.getLocationId(), tabelEmployeeOutletTransferData.getLocationName());
                        message = " Insert Data Position " + tabelEmployeeOutletTransferData.getPositionName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Position " + tabelEmployeeOutletTransferData.getPositionName() + " In local");
                        }
                    } else {

                        PstLocationDestopTransfer.updateExc(tabelEmployeeOutletTransferData);
                        message = " Update Data Position " + tabelEmployeeOutletTransferData.getPositionName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Position " + tabelEmployeeOutletTransferData.getPositionName() + " In local");
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
    
    public String setTransferExtraSch(String sql, String whereClause, boolean sdhHabisData) throws SQLException {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        String empId = "";
        Hashtable hashEmployeeId = PstEmployeeDesktopTransfer.hashEmpIdAda(0, 0, "", "");
        Hashtable hashWorkHistory = PstCareerPathDestopTransfer.hashWorkHistoryAda(0, 0, "", "");
        Hashtable hashAppUser = PstAppUserDestopTransfer.hashUsersdhAda(0, 0, "", "");
        Hashtable hashPosition = PstPositionDestopTransfer.hashPositionSdhAda(0, 0, "", "");
        Hashtable hashLocation = PstLocationDestopTransfer.hashLocationSdhAda(0, 0, "", "");
        try {
            sql = "SELECT emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE_EMERGENCY] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_HANDPHONE]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_SEX] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_PERMANENT] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_EMG]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]
                    + ",pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]
                    + ",pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION]
                    + ",appuser." + PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]
                    + ",appuser." + PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD]
                    + ",appuser." + PstAppUser.fieldNames[PstAppUser.FLD_USER_ID]
                    + ",workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]
                    + ",workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]
                    + ",workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]
                    + ",loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]
                    + ",loc." + PstLocation.fieldNames[PstLocation.FLD_NAME]
                    + ",loc." + PstLocation.fieldNames[PstLocation.FLD_CODE];

            sql = sql + " FROM " + PstExtraScheduleOutletDetail.TBL_EXTRA_SCHEDULE_OUTLET_DETAIL + " AS extrasch "
                    + " INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ON extrasch." + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstPosition.TBL_HR_POSITION + " AS pos ON pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID] + " = emp." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                    + " LEFT JOIN " + PstAppUser.TBL_APP_USER + " AS appuser ON appuser." + PstAppUser.fieldNames[PstAppUser.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " LEFT JOIN " + PstCareerPath.TBL_HR_WORK_HISTORY_NOW + " AS workemp ON workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " INNER JOIN " + PstLocation.TBL_P2_LOCATION + " AS loc ON loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + "=extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_LOCATION_ID] + " AND loc." + PstLocation.fieldNames[PstLocation.FLD_CODE] + "=\"" + getCodeLocationMesin() + "\"";

            if (getDtStart() != null && getDtFinish() != null) {
                sql = sql 
                        +  " WHERE  " + "\"" + Formater.formatDate(getDtFinish(), "yyyy-MM-dd 23:59:59") + "\" >= extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_START_DATE_PLAN] + " AND  extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_END_DATE_PLAN] + " >= \"" + Formater.formatDate(getDtStart(), "yyyy-MM-dd 00:00:00") + "\"";
                        //+ " LEFT JOIN " + PstExtraScheduleOutletDetail.TBL_EXTRA_SCHEDULE_OUTLET_DETAIL + " AS extrasch ON extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND " + "\"" + Formater.formatDate(getDtFinish(), "yyyy-MM-dd HH:mm:59") + "\" >= extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_START_DATE_PLAN] + " AND  extrasch." + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_END_DATE_PLAN] + " >= \"" + Formater.formatDate(getDtStart(), "yyyy-MM-dd HH:mm:00") + "\""
                        //+ " LEFT JOIN " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " AS empoutlet ON empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + "=emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " AND " + "\"" + Formater.formatDate(getDtFinish(), "yyyy-MM-dd HH:mm:59") + "\" >= empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM] + " AND  empoutlet." + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " >= \"" + Formater.formatDate(getDtStart(), "yyyy-MM-dd HH:mm:00") + "\"";

            }

           /* if (getDtStart() != null && getDtFinish() != null) {
                sql = sql + whereClause;
            }*/

            sql = sql + " GROUP BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            sql = sql + " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];
            //System.out.println(sql);
                  /*if(jTextArea!=null){
             jTextArea.setText(sql);
             }*/
            //System.out.println("Status running"+sql);
            message = "Run query download data employee " + sql;
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText("Run query download data employee " + sql);
            }
            int theSize = getCountEmployeeExtraSchDownload(whereClause, getDtStart(), getDtFinish(), getCodeLocationMesin());
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
                msgDownloadInformation.setText(" Total  download data employee " + theSize);
            }
            dbrs = DBHandler.execQueryResult(sql);
            rs = dbrs.getResultSet();


            while (rs != null && rs.next() && theSize > 0 && isRunning()) {

                Thread.sleep(this.getSleepMs());
                TabelEmployeeOutletTransferData tabelEmployeeOutletTransferData = new TabelEmployeeOutletTransferData();
                tabelEmployeeOutletTransferData.setEmployeeId(rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                tabelEmployeeOutletTransferData.setFullName(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                tabelEmployeeOutletTransferData.setEmployeeNumber(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                tabelEmployeeOutletTransferData.setPhone(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE]));
                tabelEmployeeOutletTransferData.setPhoneEmergency(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_PHONE_EMERGENCY]));
                tabelEmployeeOutletTransferData.setAddress(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]));
                tabelEmployeeOutletTransferData.setPermanentAddress(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_PERMANENT]));
                tabelEmployeeOutletTransferData.setAddressEmg(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_EMG]));
                tabelEmployeeOutletTransferData.setEmpPin(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN]));
                tabelEmployeeOutletTransferData.setEmpCategoryId(rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]));
                tabelEmployeeOutletTransferData.setHandPhone(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_HANDPHONE]));
                tabelEmployeeOutletTransferData.setSex(rs.getInt("emp." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]));
                tabelEmployeeOutletTransferData.setCommencingDate(rs.getDate("emp." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]));
                tabelEmployeeOutletTransferData.setResigned(rs.getInt("emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]));
                tabelEmployeeOutletTransferData.setResignedDate(rs.getDate("emp." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]));
                tabelEmployeeOutletTransferData.setBarcodeNumber(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_BARCODE_NUMBER]));


                tabelEmployeeOutletTransferData.setPositionId(rs.getLong("pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION_ID]));
                tabelEmployeeOutletTransferData.setPositionName(rs.getString("pos." + PstPosition.fieldNames[PstPosition.FLD_POSITION]));

                tabelEmployeeOutletTransferData.setLoginId(rs.getString("appuser." + PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]));
                tabelEmployeeOutletTransferData.setPassword(rs.getString("appuser." + PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD]));
                tabelEmployeeOutletTransferData.setUserId(rs.getLong("appuser." + PstAppUser.fieldNames[PstAppUser.FLD_USER_ID]));

                tabelEmployeeOutletTransferData.setWorkHistoryId(rs.getLong("workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_HISTORY_NOW_ID]));
                tabelEmployeeOutletTransferData.setWorkHistoryDateStart(rs.getDate("workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM]));
                tabelEmployeeOutletTransferData.setWorkHistoryDateEnd(rs.getDate("workemp." + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO]));

                tabelEmployeeOutletTransferData.setLocationId(rs.getLong("loc." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]));
                tabelEmployeeOutletTransferData.setLocationName(rs.getString("loc." + PstLocation.fieldNames[PstLocation.FLD_NAME]));
                tabelEmployeeOutletTransferData.setLocationCode(rs.getString("loc." + PstLocation.fieldNames[PstLocation.FLD_CODE]));




                //insert data employee
                if (tabelEmployeeOutletTransferData.getEmployeeId() != 0) {
                    empId = empId + tabelEmployeeOutletTransferData.getEmployeeId() + ",";
                    if (hashEmployeeId.size() == 0 || hashEmployeeId.containsKey("" + tabelEmployeeOutletTransferData.getEmployeeId()) == false) {
                        try {
                            PstEmployeeDesktopTransfer.insertExc(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                            }
                        }

                        //hashEmployeeId.put("" + tabelEmployeeOutletTransferData.getEmployeeId(), tabelEmployeeOutletTransferData.getFullName());

                        message = " Insert Data Employee " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Employee " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    } else {
                        PstEmployeeDesktopTransfer.updateExc(tabelEmployeeOutletTransferData);
                        message = " Update Data Employee " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Employee " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                        //String fullName = hashEmployeeId == null || hashEmployeeId.get(employeeTransfer.getEmployeeId()) == null ? "" : (String) hashEmployeeId.get(employeeTransfer.getEmployeeId());
                        //message = fullName.length() == 0 ? " Data Employee already exis" : " Data Employee already exis " + fullName + " In local";
                    }
                }

                //insert data hr_work_histori
                if (tabelEmployeeOutletTransferData.getWorkHistoryId() != 0) {
                    if ((hashWorkHistory.size() == 0 || hashWorkHistory.containsKey("" + tabelEmployeeOutletTransferData.getWorkHistoryId()) == false)) {
                        try {
                            PstCareerPathDestopTransfer.insertExc(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                        }
                        //hashEmployeeId.put("" + tabelEmployeeOutletTransferData.getEmployeeId(), tabelEmployeeOutletTransferData.getFullName());

                        message = " Insert Data Work History " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Work History " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    } else {
                        PstCareerPathDestopTransfer.updateExc(tabelEmployeeOutletTransferData);
                        message = " Update Data Work History " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Work History " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                        //String fullName = hashEmployeeId == null || hashEmployeeId.get(employeeTransfer.getEmployeeId()) == null ? "" : (String) hashEmployeeId.get(employeeTransfer.getEmployeeId());
                        //message = fullName.length() == 0 ? " Data Employee already exis" : " Data Employee already exis " + fullName + " In local";
                    }
                }

                //insert data user 
                if (tabelEmployeeOutletTransferData.getUserId() != 0) {
                    if (hashAppUser.size() == 0 || hashAppUser.containsKey("" + tabelEmployeeOutletTransferData.getUserId()) == false) {
                        try {
                            PstAppUserDestopTransfer.insert(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                            }
                        }

                        //hashAppUser.put("" + tabelEmployeeOutletTransferData.getUserId(), "" + tabelEmployeeOutletTransferData.getLoginId());
                        message = " Insert  Data User Login " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert  Data User Login " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    } else {
                        PstAppUserDestopTransfer.update(tabelEmployeeOutletTransferData);
                        message = " Update Data User Login " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data User Login " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    }
                }

                //insert data position
                if (tabelEmployeeOutletTransferData.getPositionId() != 0) {
                    if (hashPosition.size() == 0 || hashPosition.containsKey("" + tabelEmployeeOutletTransferData.getPositionId()) == false) {
                        try {
                            PstPositionDestopTransfer.insertExc(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                            }
                        }
                        //hashEmployeeId.put("" + tabelEmployeeOutletTransferData.getPositionId(), tabelEmployeeOutletTransferData.getFullName());

                        message = " Insert Data Position " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Position " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                    } else {
                        PstPositionDestopTransfer.updateExc(tabelEmployeeOutletTransferData);
                        message = " Update Data Position " + tabelEmployeeOutletTransferData.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Position " + tabelEmployeeOutletTransferData.getFullName() + " In local");
                        }
                        //String fullName = hashEmployeeId == null || hashEmployeeId.get(employeeTransfer.getEmployeeId()) == null ? "" : (String) hashEmployeeId.get(employeeTransfer.getEmployeeId());
                        //message = fullName.length() == 0 ? " Data Employee already exis" : " Data Employee already exis " + fullName + " In local";
                    }
                }



                //insert data hashLocation 
                if (tabelEmployeeOutletTransferData.getLocationId() != 0) {
                    if (hashLocation.size() == 0 || hashLocation.containsKey("" + tabelEmployeeOutletTransferData.getLocationId()) == false) {
                        try {
                            PstLocationDestopTransfer.insertExc(tabelEmployeeOutletTransferData);
                        } catch (Exception exc) {
                            this.setMessage("Exception transfer Employee  Outlet" + exc);
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText("Exception transfer Employee  Outlet" + exc);
                            }
                        }

                        //hashLocation.put("" + tabelEmployeeOutletTransferData.getLocationId(), tabelEmployeeOutletTransferData.getLocationName());
                        message = " Insert Data Position " + tabelEmployeeOutletTransferData.getPositionName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Insert Data Position " + tabelEmployeeOutletTransferData.getPositionName() + " In local");
                        }
                    } else {

                        PstLocationDestopTransfer.updateExc(tabelEmployeeOutletTransferData);
                        message = " Update Data Position " + tabelEmployeeOutletTransferData.getPositionName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(" Update Data Position " + tabelEmployeeOutletTransferData.getPositionName() + " In local");
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
     * Transfer Schedule
     *
     * @param sql
     * @param periodId
     * @param dbrs
     * @param rs
     * @param hashPeriod
     * @param hashEmployeeSchedule
     */
    public String setScheduleTransfer(String sql, String periodId, String sEmployeeId, boolean dataHabis) throws SQLException {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        String scheduleId = "";
        Hashtable hashPeriod = PstPeriodDestopTransfer.hashPeriodSdhAda(0, 0, "", "");
        Hashtable hashEmployeeSchedule = PstEmpScheduleDestopTransfer.hashEmpScheduleSdhAda(0, 0, "", "");
        try {
            sql = "SELECT emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                    + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + ",emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID];
            if (periodId != null && periodId.length() > 0) {
                //update by satrya 2014-10-31
                if(periodId.length()==19){
                    periodId = periodId.substring(0, periodId.length()-1);
                }
                
                sql = sql + " ,empsch.*,period.*";
            }



            sql = sql + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ";
            String tmpEmployee = "";
            if (sEmployeeId != null && sEmployeeId.length() > 0) {
                tmpEmployee = " AND empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + " IN(" + sEmployeeId + ")";
            }
            if (periodId != null && periodId.length() > 0) {
                sql = sql + " INNER JOIN " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS empsch ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + " AND empsch." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] + " IN(" + periodId + ")" + tmpEmployee
                        + " INNER JOIN " + PstPeriod.TBL_HR_PERIOD + " AS period ON empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + "=period." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] + " AND period." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] + " IN(" + periodId + ")";
            }


            //sql = sql + " GROUP BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            sql = sql + " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];

            message = "Run query download data employee " + sql;
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText(message);
            }
            int theSize = getCountEmployeeScheduleDownload(periodId);
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
                TabelEmployeeScheduleTransfer tabelEmployeeScheduleTransfer = new TabelEmployeeScheduleTransfer();
                tabelEmployeeScheduleTransfer.setEmployeeId(rs.getLong("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]));
                tabelEmployeeScheduleTransfer.setFullName(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                tabelEmployeeScheduleTransfer.setEmployeeNUmber(rs.getString("emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]));
                tabelEmployeeScheduleTransfer.setPeriodId(rs.getLong("period." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID]));
                tabelEmployeeScheduleTransfer.setNamaPeriod(rs.getString("period." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD]));
                tabelEmployeeScheduleTransfer.setDtStartPeriod(rs.getDate("period." + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]));
                tabelEmployeeScheduleTransfer.setDtEndPeriod(rs.getDate("period." + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE]));
                tabelEmployeeScheduleTransfer.setEmpScheduleId(rs.getLong("empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID]));
                tabelEmployeeScheduleTransfer.setScheduleType(rs.getInt("empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_SCHEDULE_TYPE]));
                for (int idx = 1; idx < 32; idx++) {
                    tabelEmployeeScheduleTransfer.setD(idx, rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idx - 1]));
                    scheduleId = scheduleId + rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idx - 1]) + ",";
                }
                for (int idx = 1; idx < 32; idx++) {
                    tabelEmployeeScheduleTransfer.setStatus(idx, rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idx - 1]));
                }





                //insert data Period
                if (tabelEmployeeScheduleTransfer.getPeriodId() != 0) {
                    if (hashPeriod.size() == 0 || hashPeriod.containsKey("" + tabelEmployeeScheduleTransfer.getPeriodId()) == false) {
                        try {
                            PstPeriodDestopTransfer.insertExc(tabelEmployeeScheduleTransfer);
                        } catch (Exception exc) {
                            message = "Error insert data period" + exc;
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText(message);
                            }
                        }

                        // hashPeriod.put("" + tabelEmployeeScheduleTransfer.getPeriodId(), tabelEmployeeScheduleTransfer.getNamaPeriod());

                        message = " Insert Data Period " + tabelEmployeeScheduleTransfer.getNamaPeriod() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(message);
                        }
                    } else {
                        PstPeriodDestopTransfer.updateExc(tabelEmployeeScheduleTransfer);
                        message = " Update Data Period " + tabelEmployeeScheduleTransfer.getNamaPeriod() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(message);
                        }

                    }
                }

                //insert data employee schedule
                if (tabelEmployeeScheduleTransfer.getEmpScheduleId() != 0) {

                    if (hashEmployeeSchedule.size() == 0 || hashEmployeeSchedule.containsKey("" + tabelEmployeeScheduleTransfer.getEmpScheduleId()) == false) {

                        try {
                            PstEmpScheduleDestopTransfer.insertExc(tabelEmployeeScheduleTransfer);
                        } catch (Exception exc) {
                            message = "Error insert data period" + exc;
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText(message);
                            }
                        }
                        //hashEmployeeSchedule.put("" + tabelEmployeeScheduleTransfer.getEmployeeId(), tabelEmployeeScheduleTransfer.getFullName());

                        message = " Insert Data emp schedule " + tabelEmployeeScheduleTransfer.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(message);
                        }
                    } else {
                        PstEmpScheduleDestopTransfer.updateExc(tabelEmployeeScheduleTransfer);
                        message = " Update Data emp schedule " + tabelEmployeeScheduleTransfer.getFullName() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(message);
                        }
                        //String fullName = hashEmployeeId == null || hashEmployeeId.get(employeeTransfer.getEmployeeId()) == null ? "" : (String) hashEmployeeId.get(employeeTransfer.getEmployeeId());
                        //message = fullName.length() == 0 ? " Data Employee already exis" : " Data Employee already exis " + fullName + " In local";
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
            message = "Exception Schedule Transfer data" + e;
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
            return scheduleId;
        }
    }

    public void setScheduleSymbolTransfer(String sql, String inputScheduleParam, String sScheduleId) throws SQLException {
        DBResultSet dbrs = null;
        ResultSet rs = null;

        //Hashtable hashPeriod = new Hashtable();
        //Hashtable hashEmployeeSchedule = new Hashtable();
        Hashtable hashSchedule = PstScheduleDestopTransfer.hashSchSdhAda(0, 0, "", "");

        try {
            sql = "SELECT *  FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS sch WHERE (1=1)";

            if (inputScheduleParam != null && inputScheduleParam.length() > 0) {
                String sInputSchedule[] = inputScheduleParam.split(",");
                String inputSch = "";
                if (sInputSchedule != null && sInputSchedule.length > 0) {
                    for (int idx = 0; idx < sInputSchedule.length; idx++) {
                        inputSch = inputSch + "\"" + sInputSchedule[idx] + "\",";
                    }
                    if (inputSch != null && inputSch.length() > 0) {
                        inputSch = inputSch.substring(0, inputSch.length() - 1);
                    }
                }
                sql = sql + " AND " + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " IN(" + inputSch + ")";
            }
            if (sScheduleId != null && sScheduleId.length() > 0) {
                sql = sql + " AND " + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] + " IN(" + sScheduleId + ")";
            }
            sql = sql + " ORDER BY sch." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL];

            message = "Run query download data employee " + sql;
            if (msgDownloadInformation != null) {
                msgDownloadInformation.setText(message);
            }
            int theSize = getCountScheduleDownload(inputScheduleParam);
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
                TabelEmployeeScheduleTransfer tabelEmployeeScheduleTransfer = new TabelEmployeeScheduleTransfer();
                tabelEmployeeScheduleTransfer.setScheduleId(rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]));
                tabelEmployeeScheduleTransfer.setScheduleCategory(rs.getLong(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_CATEGORY_ID]));
                tabelEmployeeScheduleTransfer.setScheduleSymbol(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]));
                tabelEmployeeScheduleTransfer.setNameSchedule(rs.getString(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE]));
                tabelEmployeeScheduleTransfer.setTimeIn(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_IN]));
                tabelEmployeeScheduleTransfer.setTimeOut(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_TIME_OUT]));
                tabelEmployeeScheduleTransfer.setBreakIn(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_IN]));
                tabelEmployeeScheduleTransfer.setBreakOut(rs.getTime(PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_BREAK_OUT]));

                //insert data Period
                if (tabelEmployeeScheduleTransfer.getScheduleId() != 0) {
                    if (hashSchedule.size() == 0 || hashSchedule.containsKey("" + tabelEmployeeScheduleTransfer.getScheduleId()) == false) {
                        try {
                            PstScheduleDestopTransfer.insertExc(tabelEmployeeScheduleTransfer);
                        } catch (Exception exc) {
                            message = "Error transfer data schedule symbol" + exc;
                            if (msgDownloadInformation != null) {
                                msgDownloadInformation.setText(message);
                            }
                        }
                        //hashSchedule.put("" + tabelEmployeeScheduleTransfer.getScheduleId(), tabelEmployeeScheduleTransfer.getScheduleSymbol());
                        message = " Insert Data Schedule " + tabelEmployeeScheduleTransfer.getScheduleSymbol() + " In local";
                        if (msgDownloadInformation != null) {
                            msgDownloadInformation.setText(message);
                        }
                    } else {
                        PstScheduleDestopTransfer.updateExc(tabelEmployeeScheduleTransfer);
                        message = " Update Data Schedule " + tabelEmployeeScheduleTransfer.getScheduleSymbol() + " In local";
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

    /**
     * @return the cbxOutlet
     */
    public boolean isCbxOutlet() {
        return cbxOutlet;
    }

    /**
     * @param cbxOutlet the cbxOutlet to set
     */
    public void setCbxOutlet(boolean cbxOutlet) {
        this.cbxOutlet = cbxOutlet;
    }

    /**
     * @return the cbxSchedule
     */
    public boolean isCbxSchedule() {
        return cbxSchedule;
    }

    /**
     * @param cbxSchedule the cbxSchedule to set
     */
    public void setCbxSchedule(boolean cbxSchedule) {
        this.cbxSchedule = cbxSchedule;
    }

    /**
     * @return the cbxScheduleSymbol
     */
    public boolean isCbxScheduleSymbol() {
        return cbxScheduleSymbol;
    }

    /**
     * @param cbxScheduleSymbol the cbxScheduleSymbol to set
     */
    public void setCbxScheduleSymbol(boolean cbxScheduleSymbol) {
        this.cbxScheduleSymbol = cbxScheduleSymbol;
    }

    public static int getCountEmployeeScheduleDownload(String periodId) {
        if (periodId == null || periodId.length() == 0) {
            return 0;
        }
        DBResultSet dbrs = null;
        try {

            String sql = "SELECT COUNT(emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + ")";
            sql = sql + " FROM " + PstEmployee.TBL_HR_EMPLOYEE + " AS emp ";

            if (periodId != null && periodId.length() > 0) {
                sql = sql + " INNER JOIN " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " AS empsch ON emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID] + " AND empsch." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] + " IN(" + periodId + ")"
                        + " INNER JOIN " + PstPeriod.TBL_HR_PERIOD + " AS period ON empsch." + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID] + "=period." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] + " AND period." + PstPeriod.fieldNames[PstPeriod.FLD_PERIOD_ID] + " IN(" + periodId + ")";
            }

            sql = sql + " GROUP BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];
            sql = sql + " ORDER BY emp." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM];


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

    //priska 20150631 untuk delete dulu schedule yang diubah ubah
    public static Date deleteByEmpOutlet(String empoutlet) {
        Date dt = new Date();
        
        try {
            String sql = " DELETE FROM " + PstEmployeeOutlet.TBL_HR_EMP_OUTLET + " "
                    + " WHERE " + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_OUTLET_EMPLOYEE_ID]
                    + " IN ("+ empoutlet.substring(0, (empoutlet.length() -1)) +")" ; 
                    //+ PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_OUTLET_EMPLOYEE_ID] + " != " + empOutletId + " "
                    //+ " AND "+ PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID] + " = " + empId + " "
                   // + "  (\"" + Formater.formatDate(dt, "yyyy-MM-dd 00:00:00") + "\" BETWEEN " + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM]
                   // + " AND " + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO]+")";
            int status = com.dimata.harisma.utility.harisma.machine.db.DBHandler.execUpdate(sql);
            
        } catch (Exception exc) {
            System.out.println("error delete empschedule by employee " + exc.toString());
        }
        return dt;
    }
    
    
    /**
     * mencari jumlah berapa yg ada employee schedulenya
     *
     * @param whereClause
     * @param Start
     * @param End
     * @return
     */
    public static int getCountScheduleDownload(String whereClause) {

        DBResultSet dbrs = null;
        try {

            String sql = "SELECT COUNT(" + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] + ")"
                    + " FROM " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS sch ";

            if (whereClause != null && whereClause.length() > 0) {
                String sInputSchedule[] = whereClause.split(",");
                String inputSch = "";
                if (sInputSchedule != null && sInputSchedule.length > 0) {
                    for (int idx = 0; idx < sInputSchedule.length; idx++) {
                        inputSch = inputSch + "\"" + sInputSchedule[idx] + "\",";
                    }
                    if (inputSch != null && inputSch.length() > 0) {
                        inputSch = inputSch.substring(0, inputSch.length() - 1);
                    }
                }
                //sql = sql + " AND " + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " IN(" + inputSch + ")";
                sql = sql + " WHERE " + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " IN(" + inputSch + ")";
            }
            sql = sql + " ORDER BY " + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL];


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

    /**
     * @return the inputParam
     */
    public String getInputParam() {
        if (inputParam == null) {
            return "";
        }
        return inputParam;
    }

    /**
     * @param inputParam the inputParam to set
     */
    public void setInputParam(String inputParam) {
        this.inputParam = inputParam;
    }

    /**
     * @return the cbxKadivMapping
     */
    public boolean isCbxKadivMapping() {
        return cbxKadivMapping;
    }

    /**
     * @param cbxKadivMapping the cbxKadivMapping to set
     */
    public void setCbxKadivMapping(boolean cbxKadivMapping) {
        this.cbxKadivMapping = cbxKadivMapping;
    }
}
