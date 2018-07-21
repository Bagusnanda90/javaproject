/*
 * PstLeaveApplication.java
 *
 * Created on October 27, 2004, 11:51 AM
 */
package com.dimata.harisma.entity.leave;

import com.dimata.harisma.entity.attendance.AlStockTaken;
import com.dimata.harisma.entity.attendance.DpStockTaken;
import java.sql.ResultSet;
//import java.sql.SQLException;

import java.util.Vector;
import java.util.Date;

import com.dimata.util.*;
import com.dimata.util.lang.I_Language;
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;

import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.EmpSchedule;

import com.dimata.harisma.entity.attendance.LlStockTaken;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.session.attendance.SessEmpSchedule;
import com.dimata.harisma.session.leave.SessLeaveApplication;
import com.dimata.harisma.utility.service.presence.AbsenceAnalyser;
import com.dimata.harisma.utility.service.presence.LatenessAnalyser;
import com.dimata.system.entity.system.PstSystemProperty;
import com.dimata.harisma.entity.attendance.PstAlStockTaken;
import com.dimata.harisma.entity.attendance.PstLlStockTaken;
import com.dimata.harisma.entity.attendance.PstDpStockTaken;
import com.dimata.harisma.entity.attendance.PstSpecialLeave;
import com.dimata.harisma.form.leave.FrmLeaveApplication;

/**
 *
 * @author gedhy
 */
public class PstLeaveApplication extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_LEAVE_APPLICATION = "hr_leave_application";//"HR_LEAVE_APPLICATION";
    public static final int FLD_LEAVE_APPLICATION_ID = 0;
    public static final int FLD_SUBMISSION_DATE = 1;
    public static final int FLD_EMPLOYEE_ID = 2;
    public static final int FLD_LEAVE_REASON = 3;
    public static final int FLD_DEP_HEAD_APPROVAL = 4;
    public static final int FLD_HR_MAN_APPROVAL = 5;
    public static final int FLD_DOC_STATUS = 6;
    public static final int FLD_DEP_HEAD_APPROVE_DATE = 7;
    public static final int FLD_HR_MAN_APPROVE_DATE = 8;
    public static final int FLD_GM_APPROVAL = 9;
    public static final int FLD_GM_APPROVAL_DATE = 10;
    //update by satrya 2013-03-13
    public static final int FLD_TYPE_LEAVE = 11;
    //update by satrya 2013-04-11
    public static final int FLD_TYPE_FROM_LEAVE = 12;
    //update by satrya 2013-04-17
    public static final int FLD_REASON_ID = 13;
    public static final int FLD_LEAVE_APPLICATION_DIFFERENT_PERIOD = 14;
    //priska menambahkan al & ll allowance 20150805
    public static final int FLD_AL_ALLOWANCE = 15;
    public static final int FLD_LL_ALLOWANCE = 16;
    public static final int FLD_APPROVAL_1 = 17;
    public static final int FLD_APPROVAL_1_DATE = 18;
    public static final int FLD_APPROVAL_2 = 19;
    public static final int FLD_APPROVAL_2_DATE = 20;
    public static final int FLD_APPROVAL_3 = 21;
    public static final int FLD_APPROVAL_3_DATE = 22;
    public static final int FLD_APPROVAL_4 = 23;
    public static final int FLD_APPROVAL_4_DATE = 24;
    public static final int FLD_APPROVAL_5 = 25;
    public static final int FLD_APPROVAL_5_DATE = 26;
    public static final int FLD_APPROVAL_6 = 27;
    public static final int FLD_APPROVAL_6_DATE = 28;
    public static final String[] fieldNames = {
        "LEAVE_APPLICATION_ID",
        "SUBMISSION_DATE",
        "EMPLOYEE_ID",
        "LEAVE_REASON",
        "DEP_HEAD_APPROVAL",
        "HR_MAN_APPROVAL",
        "DOC_STATUS",
        "DEP_HEAD_APPROVE_DATE",
        "HR_MAN_APPROVE_DATE",
        "GM_APPROVAL",
        "GM_APPROVAL_DATE",
        "TYPE_LEAVE",
        //update by satrya 2013-04-11
        "TYPE_FORM_LEAVE",
        "REASON_ID",
        "LEAVE_APPLICATION_DIFFERENT_PERIOD",
        "AL_ALLOWANCE",
        "LL_ALLOWANCE",
        "APPROVAL_1",
        "APPROVAL_1_DATE",
        "APPROVAL_2",
        "APPROVAL_2_DATE",
        "APPROVAL_3",
        "APPROVAL_3_DATE",
        "APPROVAL_4",
        "APPROVAL_4_DATE",
        "APPROVAL_5",
        "APPROVAL_5_DATE",
        "APPROVAL_6",
        "APPROVAL_6_DATE"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE
    };
    //update by satrya 2013-04-11
    public static final int LEAVE_APPLICATION = 0;
    public static final int EXCUSE_APPLICATION = 1;
    public static final String[] strLeaveAppType = {
        "LEAVE APPLICATION",
        "EXCUSE APPLICATION",};
    public static final int LEAVE_APPLICATION_AL = 0;
    public static final int LEAVE_APPLICATION_LL = 1;
    public static final int LEAVE_APPLICATION_MATERNITY = 2;
    public static final int LEAVE_APPLICATION_DC = 3;
    public static final int LEAVE_APPLICATION_UNPAID = 4;
    public static final int LEAVE_APPLICATION_SPECIAL = 5;
    public static final String[] strApplicationType = {
        "LEAVE_APPLICATION_AL",
        "LEAVE_APPLICATION_LL",
        "LEAVE_APPLICATION_MATERNITY",
        "LEAVE_APPLICATION_DC",
        "LEAVE_APPLICATION_UNPAID",
        "LEAVE_APPLICATION_SPECIAL"
    };
    public static final int FLD_DOC_STATUS_VALID = 0;
    public static final int FLD_DOC_STATUS_INVALID = 1;
    public static final String[] fieldStatusNames = {
        "Valid",
        "Invalid"
    };
    public static final int FLD_NOT_APPROVED = 0;
    public static final int FLD_APPROVE_BY_DEPT_HEAD = 1;
    public static final int FLD_APPROVE_BY_HR_MANAGER = 2;
    public static final String[] fieldApprovalStatusNames = {
        "Not Approved",
        "Approve by Department Head",
        "Approve by HR Manager"
    };
    public static final int FLD_STATUS_APPlICATION_DRAFT = 0;
    public static final int FLD_STATUS_APPlICATION_TO_BE_APPROVE = 1;
    public static final int FLD_STATUS_APPlICATION_APPROVED = 2;
    public static final int FLD_STATUS_APPlICATION_EXECUTED = 3;
    public static final int FLD_STATUS_APPlICATION_CANCELED = 4;
    public static final String[] fieldStatusApplication = {
        "Draft",
        "To Be Approve",
        "Approved",
        "Executed",
        "Cancelled"
    };

    public PstLeaveApplication() {
    }

    public PstLeaveApplication(int i) throws DBException {
        super(new PstLeaveApplication());
    }

    public PstLeaveApplication(String sOid) throws DBException {
        super(new PstLeaveApplication(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstLeaveApplication(long lOid) throws DBException {
        super(new PstLeaveApplication(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_LEAVE_APPLICATION;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstLeaveApplication().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        LeaveApplication objLeaveApplication = fetchExc(ent.getOID());
        return objLeaveApplication.getOID();
    }

    public static LeaveApplication fetchExc(long oid) throws DBException {
        try {
            LeaveApplication objLeaveApplication = new LeaveApplication();
            PstLeaveApplication objPstLeaveApplication = new PstLeaveApplication(oid);

            objLeaveApplication.setOID(oid);
            objLeaveApplication.setSubmissionDate(objPstLeaveApplication.getDate(FLD_SUBMISSION_DATE));
            objLeaveApplication.setEmployeeId(objPstLeaveApplication.getlong(FLD_EMPLOYEE_ID));
            objLeaveApplication.setLeaveReason(objPstLeaveApplication.getString(FLD_LEAVE_REASON));
            objLeaveApplication.setDepHeadApproval(objPstLeaveApplication.getlong(FLD_DEP_HEAD_APPROVAL));
            objLeaveApplication.setHrManApproval(objPstLeaveApplication.getlong(FLD_HR_MAN_APPROVAL));
            objLeaveApplication.setDocStatus(objPstLeaveApplication.getInt(FLD_DOC_STATUS));
            objLeaveApplication.setDepHeadApproveDate(objPstLeaveApplication.getDate(FLD_DEP_HEAD_APPROVE_DATE));
            objLeaveApplication.setHrManApproveDate(objPstLeaveApplication.getDate(FLD_HR_MAN_APPROVE_DATE));
            objLeaveApplication.setGmApproval(objPstLeaveApplication.getlong(FLD_GM_APPROVAL));
            objLeaveApplication.setGmApprovalDate(objPstLeaveApplication.getDate(FLD_GM_APPROVAL_DATE));
            //update by satrya 2013-03-13
            objLeaveApplication.setTypeLeave(objPstLeaveApplication.getInt(FLD_TYPE_LEAVE));
            //update by satrya 2013-04-11
            objLeaveApplication.setTypeFormLeave(objPstLeaveApplication.getInt(FLD_TYPE_FROM_LEAVE));
            //UPDATE BY SARYA 2013-04-13
            objLeaveApplication.setReasonId(objPstLeaveApplication.getlong(FLD_REASON_ID));
            objLeaveApplication.setLeaveAppDiffPeriod(objPstLeaveApplication.getlong(FLD_LEAVE_APPLICATION_DIFFERENT_PERIOD));
            //priska 20150805
            objLeaveApplication.setAlAllowance(objPstLeaveApplication.getInt(FLD_AL_ALLOWANCE));
            objLeaveApplication.setLlAllowance(objPstLeaveApplication.getInt(FLD_LL_ALLOWANCE));
            //add by priska 20160405
            objLeaveApplication.setApproval_1(objPstLeaveApplication.getLong(FLD_APPROVAL_1));
            objLeaveApplication.setApproval_1_date(objPstLeaveApplication.getDate(FLD_APPROVAL_1_DATE));
            objLeaveApplication.setApproval_2(objPstLeaveApplication.getLong(FLD_APPROVAL_2));
            objLeaveApplication.setApproval_2_date(objPstLeaveApplication.getDate(FLD_APPROVAL_2_DATE));
            objLeaveApplication.setApproval_3(objPstLeaveApplication.getLong(FLD_APPROVAL_3));
            objLeaveApplication.setApproval_3_date(objPstLeaveApplication.getDate(FLD_APPROVAL_3_DATE));
            objLeaveApplication.setApproval_4(objPstLeaveApplication.getLong(FLD_APPROVAL_4));
            objLeaveApplication.setApproval_4_date(objPstLeaveApplication.getDate(FLD_APPROVAL_4_DATE));
            objLeaveApplication.setApproval_5(objPstLeaveApplication.getLong(FLD_APPROVAL_5));
            objLeaveApplication.setApproval_5_date(objPstLeaveApplication.getDate(FLD_APPROVAL_5_DATE));
            objLeaveApplication.setApproval_6(objPstLeaveApplication.getLong(FLD_APPROVAL_6));
            objLeaveApplication.setApproval_6_date(objPstLeaveApplication.getDate(FLD_APPROVAL_6_DATE));

            return objLeaveApplication;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveApplication(0), DBException.UNKNOWN);
        }
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((LeaveApplication) ent);
    }

    public static long updateExc(LeaveApplication objLeaveApplication) throws DBException {
        try {
            if (objLeaveApplication.getOID() != 0) {
                PstLeaveApplication objPstLeaveApplication = new PstLeaveApplication(objLeaveApplication.getOID());

                objPstLeaveApplication.setDate(FLD_SUBMISSION_DATE, objLeaveApplication.getSubmissionDate());
                objPstLeaveApplication.setLong(FLD_EMPLOYEE_ID, objLeaveApplication.getEmployeeId());
                objPstLeaveApplication.setString(FLD_LEAVE_REASON, objLeaveApplication.getLeaveReason());
                objPstLeaveApplication.setLong(FLD_DEP_HEAD_APPROVAL, objLeaveApplication.getDepHeadApproval());
                objPstLeaveApplication.setLong(FLD_HR_MAN_APPROVAL, objLeaveApplication.getHrManApproval());
                objPstLeaveApplication.setInt(FLD_DOC_STATUS, objLeaveApplication.getDocStatus());
                objPstLeaveApplication.setDate(FLD_DEP_HEAD_APPROVE_DATE, objLeaveApplication.getDepHeadApproveDate());
                objPstLeaveApplication.setDate(FLD_HR_MAN_APPROVE_DATE, objLeaveApplication.getHrManApproveDate());
                objPstLeaveApplication.setLong(FLD_GM_APPROVAL, objLeaveApplication.getGmApproval());
                objPstLeaveApplication.setDate(FLD_GM_APPROVAL_DATE, objLeaveApplication.getGmApprovalDate());
                objPstLeaveApplication.setInt(FLD_TYPE_LEAVE, objLeaveApplication.getTypeLeave());
                //update by satrya 2013-04-11
                objPstLeaveApplication.setInt(FLD_TYPE_FROM_LEAVE, objLeaveApplication.getTypeFormLeave());
                objPstLeaveApplication.setLong(FLD_REASON_ID, objLeaveApplication.getReasonId());
                objPstLeaveApplication.setLong(FLD_LEAVE_APPLICATION_DIFFERENT_PERIOD, objLeaveApplication.getLeaveAppDiffPeriod());
                //priska 20150805
                objPstLeaveApplication.setInt(FLD_AL_ALLOWANCE, objLeaveApplication.getAlAllowance());
                objPstLeaveApplication.setInt(FLD_LL_ALLOWANCE, objLeaveApplication.getLlAllowance());
                //add by priska 20160405
                objPstLeaveApplication.setLong(FLD_APPROVAL_1, objLeaveApplication.getApproval_1());
                objPstLeaveApplication.setDate(FLD_APPROVAL_1_DATE, objLeaveApplication.getApproval_1_date());
                objPstLeaveApplication.setLong(FLD_APPROVAL_2, objLeaveApplication.getApproval_2());
                objPstLeaveApplication.setDate(FLD_APPROVAL_2_DATE, objLeaveApplication.getApproval_2_date());
                objPstLeaveApplication.setLong(FLD_APPROVAL_3, objLeaveApplication.getApproval_3());
                objPstLeaveApplication.setDate(FLD_APPROVAL_3_DATE, objLeaveApplication.getApproval_3_date());
                objPstLeaveApplication.setLong(FLD_APPROVAL_4, objLeaveApplication.getApproval_4());
                objPstLeaveApplication.setDate(FLD_APPROVAL_4_DATE, objLeaveApplication.getApproval_4_date());
                objPstLeaveApplication.setLong(FLD_APPROVAL_5, objLeaveApplication.getApproval_5());
                objPstLeaveApplication.setDate(FLD_APPROVAL_5_DATE, objLeaveApplication.getApproval_5_date());
                objPstLeaveApplication.setLong(FLD_APPROVAL_6, objLeaveApplication.getApproval_6());
                objPstLeaveApplication.setDate(FLD_APPROVAL_6_DATE, objLeaveApplication.getApproval_6_date());
                objPstLeaveApplication.update();
                return objLeaveApplication.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveApplication(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long deleteExc(Entity ent) throws Exception {
        return deleteExc(ent.getOID());
    }

    public static long deleteExc(long oid) throws DBException {
        try {
            PstSpecialUnpaidLeaveTaken.deleteByLeaveAppID(oid);
            PstAlStockTaken.deleteByLeaveAppId(oid);
            PstLlStockTaken.deleteByLeaveAppId(oid);
            PstDpStockTaken.deleteByLeaveAppId(oid);
            PstLeaveApplication objPstLeaveApplication = new PstLeaveApplication(oid);
            objPstLeaveApplication.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveApplication(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((LeaveApplication) ent);
    }

    public static long insertExc(LeaveApplication objLeaveApplication) throws DBException {
        try {
            PstLeaveApplication objPstLeaveApplication = new PstLeaveApplication(0);

            objPstLeaveApplication.setDate(FLD_SUBMISSION_DATE, objLeaveApplication.getSubmissionDate());
            objPstLeaveApplication.setLong(FLD_EMPLOYEE_ID, objLeaveApplication.getEmployeeId());
            objPstLeaveApplication.setString(FLD_LEAVE_REASON, objLeaveApplication.getLeaveReason());
            objPstLeaveApplication.setLong(FLD_DEP_HEAD_APPROVAL, objLeaveApplication.getDepHeadApproval());
            objPstLeaveApplication.setLong(FLD_HR_MAN_APPROVAL, objLeaveApplication.getHrManApproval());
            objPstLeaveApplication.setInt(FLD_DOC_STATUS, objLeaveApplication.getDocStatus());
            objPstLeaveApplication.setDate(FLD_DEP_HEAD_APPROVE_DATE, objLeaveApplication.getDepHeadApproveDate());
            objPstLeaveApplication.setDate(FLD_HR_MAN_APPROVE_DATE, objLeaveApplication.getHrManApproveDate());
            objPstLeaveApplication.setLong(FLD_GM_APPROVAL, objLeaveApplication.getGmApproval());
            objPstLeaveApplication.setDate(FLD_GM_APPROVAL_DATE, objLeaveApplication.getGmApprovalDate());
            //update by satrya 2013-03-13
            objPstLeaveApplication.setInt(FLD_TYPE_LEAVE, objLeaveApplication.getTypeLeave());
            //update by satrya 2013-04-11
            objPstLeaveApplication.setInt(FLD_TYPE_FROM_LEAVE, objLeaveApplication.getTypeFormLeave());
            //update by satrya 2013-04-17
            objPstLeaveApplication.setLong(FLD_REASON_ID, objLeaveApplication.getReasonId());
            objPstLeaveApplication.setLong(FLD_LEAVE_APPLICATION_DIFFERENT_PERIOD, objLeaveApplication.getLeaveAppDiffPeriod());
            //priska 20150805
            objPstLeaveApplication.setInt(FLD_AL_ALLOWANCE, objLeaveApplication.getAlAllowance());
            objPstLeaveApplication.setInt(FLD_LL_ALLOWANCE, objLeaveApplication.getLlAllowance());
            //add by priska 20160405
            objPstLeaveApplication.setLong(FLD_APPROVAL_1, objLeaveApplication.getApproval_1());
            objPstLeaveApplication.setDate(FLD_APPROVAL_1_DATE, objLeaveApplication.getApproval_1_date());
            objPstLeaveApplication.setLong(FLD_APPROVAL_2, objLeaveApplication.getApproval_2());
            objPstLeaveApplication.setDate(FLD_APPROVAL_2_DATE, objLeaveApplication.getApproval_2_date());
            objPstLeaveApplication.setLong(FLD_APPROVAL_3, objLeaveApplication.getApproval_3());
            objPstLeaveApplication.setDate(FLD_APPROVAL_3_DATE, objLeaveApplication.getApproval_3_date());
            objPstLeaveApplication.setLong(FLD_APPROVAL_4, objLeaveApplication.getApproval_4());
            objPstLeaveApplication.setDate(FLD_APPROVAL_4_DATE, objLeaveApplication.getApproval_4_date());
            objPstLeaveApplication.setLong(FLD_APPROVAL_5, objLeaveApplication.getApproval_5());
            objPstLeaveApplication.setDate(FLD_APPROVAL_5_DATE, objLeaveApplication.getApproval_5_date());
            objPstLeaveApplication.setLong(FLD_APPROVAL_6, objLeaveApplication.getApproval_6());
            objPstLeaveApplication.setDate(FLD_APPROVAL_6_DATE, objLeaveApplication.getApproval_6_date());
            objPstLeaveApplication.insert();
            objLeaveApplication.setOID(objPstLeaveApplication.getlong(FLD_LEAVE_APPLICATION_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstLeaveApplication(0), DBException.UNKNOWN);
        }
        return objLeaveApplication.getOID();
    }

    /**
     * @param limit Start
     * @param record To Get
     * @param where Clause
     * @param order by
     * @return
     */
    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_LEAVE_APPLICATION;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                LeaveApplication objLeaveApplication = new LeaveApplication();
                resultToObject(rs, objLeaveApplication);
                lists.add(objLeaveApplication);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static Vector listOid(long employeeId, Date dateFromLeave, Date dateToLeave) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            if (dateFromLeave != null && dateToLeave != null) {
                String sql = " SELECT * FROM (SELECT AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AS TK "
                        + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF "
                        + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
                        + " , \"AL\" AS LEAVE_SYMBOL "
                        // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                        //update by satrya 2012-10-31
                        + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID] + " AS OID_DETAIL "
                        + " ,LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]
                        + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                        + " INNER JOIN  " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  ON ( AL."
                        + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID]
                        + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                        + " WHERE "
                        //update by satrya 2013-03-01
                        + "\"" + Formater.formatDate(dateToLeave, "yyyy-MM-dd HH:mm:ss") + "\" > AL."
                        + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AND AL."
                        + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateFromLeave, "yyyy-MM-dd HH:mm:ss") + "\""
                        + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                        + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                        + " UNION SELECT DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] + "  AS TK "
                        + ",DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF "
                        + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
                        + " , \"DP\" AS LEAVE_SYMBOL "
                        // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                        //update by satrya 2012-10-31
                        + ",DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID] + " AS OID_DETAIL "
                        + " ,LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]
                        + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                        + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS DP ON (LA."
                        + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                        + " = DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID] + ")"
                        + " WHERE "
                        //update by satrya 2013-03-01
                        + "\"" + Formater.formatDate(dateToLeave, "yyyy-MM-dd HH:mm:ss") + "\" > DP."
                        + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] + " AND DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateFromLeave, "yyyy-MM-dd HH:mm:ss") + "\""
                        + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                        //update by satrya 2012-11-08
                        + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                        + " UNION SELECT LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AS TK "
                        + ",LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + "  AS TF "
                        + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
                        + " , \"LL\" AS LEAVE_SYMBOL "
                        //+ ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                        //update by satrya 2012-10-31
                        + ",LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_TAKEN_ID] + "  AS OID_DETAIL "
                        + " ,LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]
                        + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                        + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS LL ON (LL."
                        + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID]
                        + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                        + " WHERE "
                        //update by satrya 2013-03-01
                        + "\"" + Formater.formatDate(dateToLeave, "yyyy-MM-dd HH:mm:ss") + "\" > LL."
                        + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AND LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateFromLeave, "yyyy-MM-dd HH:mm:ss") + "\""
                        + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                        + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                        + " UNION SELECT  SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AS TK "
                        + ",SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF"
                        + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
                        + " , SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " AS LEAVE_SYMBOL "
                        // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                        //uypdate by satrya 2012-10-31
                        + ",SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID] + " AS OID_DETAIL "
                        + " ,LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]
                        + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                        + " INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA."
                        + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                        + " = SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID] + ")"
                        + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM ON SU."
                        + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                        + " WHERE "
                        //update by satrya 2013-03-01
                        
                        //+ "\"" + Formater.formatDate(dateToLeave, "yyyy-MM-dd HH:mm:ss") + "\" > SU."
                        //+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AND SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateFromLeave, "yyyy-MM-dd HH:mm:ss") + "\""
                        
                        + "\"" + Formater.formatDate(dateToLeave, "yyyy-MM-dd 00:00:00") + "\" > SU."
                        + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AND SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateFromLeave, "yyyy-MM-dd 00:00:00") + "\""
                        
                        
                        //+ " ((SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+")" 
                        //+ " OR (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+ "))"  
                        //+ " AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+"<>"+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") "
                        //hidden by satrya 2013-02-24
                        //+ " AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+"!="+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+"!="+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
                        //+ " AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+"<>"+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"                                       
                        //                    + "\""+ sSelectDate +"\" BETWEEN DATE(SU."
                        //    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+") AND DATE(SU."
                        //    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + ") "

                        + " AND LA."
                        + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                        + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED + ") AS T GROUP BY T.LEAVE_SYMBOL";


                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();
                int totLeave = 0;
                while (rs.next()) {
                    //LeaveApplication objLeaveApplication = new LeaveApplication();
                    //resultToObject(rs, objLeaveApplication);
                    LeaveOidSym obj = new LeaveOidSym();
                    obj.setLeaveOid(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]));
                    obj.setLeaveSymbol(rs.getString("LEAVE_SYMBOL"));

                    lists.add(obj);
                }
                rs.close();
                return lists;
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    /**
     * keterangan fungsi ini untuk email
     *
     * @param leaveApp
     * @return
     */
    public static Vector searchNameLeaveWithEmail(LeaveApplication leaveApp) {
        DBResultSet dbrs = null;
        Vector result = new Vector(1, 1);
        boolean status = false;

        if (leaveApp == null || leaveApp.getOID() == 0) {
            return null;
        }

        try {

            String sql =
                    " SELECT "
                    + " APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ","
                    + " APP.TK,"
                    + " APP.TF,"
                    + " APP.LEAVE_SYMBOL,"
                    + " APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE] + ","
                    + " APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_HR_MAN_APPROVAL] + ","//APP.HR_MAN_APPROVAL,
                    + " EMPHRMAN." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + ","//EMPHRMAN.`FULL_NAME`,
                    + " APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_HR_MAN_APPROVE_DATE] + ","//APP.HR_MAN_APPROVE_DATE,
                    + " APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DEP_HEAD_APPROVAL] + ","//DEP_HEAD_APPROVAL,
                    + " EMPDEPHEAD." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + ","//EMPDEPHEAD.`FULL_NAME`,
                    + " APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DEP_HEAD_APPROVE_DATE] + "," //APP.DEP_HEAD_APPROVE_DATE
                    //update by satrya 2014-01-17
                    + " APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] //APP.EMPLOYEE_ID
                    + " FROM ( "
                    + " SELECT "
                    + " AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AS TK  ," //AL.TAKEN_DATE AS TK  ,
                    + " AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF ," //AL.TAKEN_FINNISH_DATE AS TF ,
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP  , "//LA.EMPLOYEE_ID AS EMP  , 
                    + " \"AL\" AS LEAVE_SYMBOL ," //"AL" AS LEAVE_SYMBOL  ,
                    + " AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID] + " AS OID_DETAIL  ,"//AL.AL_STOCK_ID AS OID_DETAIL  ,
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ","//LA.LEAVE_APPLICATION_ID,
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE] + "," //LA.SUBMISSION_DATE,
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_HR_MAN_APPROVAL] + "," //LA.HR_MAN_APPROVAL,
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DEP_HEAD_APPROVAL] + "," //LA.DEP_HEAD_APPROVAL,
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_TYPE_FROM_LEAVE] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_HR_MAN_APPROVE_DATE] + ","//LA.`HR_MAN_APPROVE_DATE`,
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DEP_HEAD_APPROVE_DATE] + "," //LA.`DEP_HEAD_APPROVE_DATE`
                    //update by satrya 2014-01-17
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] //APP.EMPLOYEE_ID
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA  "
                    + " INNER JOIN " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  "
                    + " ON ( AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID] + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ") WHERE  LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + "!=" + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " UNION SELECT "
                    + " DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] + "  AS TK  ,"
                    + " DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF ,"
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP  , "
                    + " \"DP\" AS LEAVE_SYMBOL ," //"AL" AS LEAVE_SYMBOL  ,
                    + " DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] + " AS OID_DETAIL  ,"
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE] + "," //LA.SUBMISSION_DATE,
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_HR_MAN_APPROVAL] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DEP_HEAD_APPROVAL] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_TYPE_FROM_LEAVE] + " ,"
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_HR_MAN_APPROVE_DATE] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DEP_HEAD_APPROVE_DATE] + ","
                    //update by satrya 2014-01-17
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] //APP.EMPLOYEE_ID
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA  "
                    + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS DP "
                    + " ON (LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " = DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID] + ")"
                    + " WHERE  LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " !=" + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " UNION SELECT "
                    + " LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AS TK  ,"
                    + " LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + "  AS TF ,"
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP  , "
                    + " \"LL\" AS LEAVE_SYMBOL ," //"AL" AS LEAVE_SYMBOL  ,
                    + " LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_ID] + "  AS OID_DETAIL   ,"
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_HR_MAN_APPROVAL] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DEP_HEAD_APPROVAL] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_TYPE_FROM_LEAVE] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_HR_MAN_APPROVE_DATE] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DEP_HEAD_APPROVE_DATE] + ","
                    //update by satrya 2014-01-17
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] //APP.EMPLOYEE_ID
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA  "
                    + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS LL  "
                    + " ON (LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID] + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                    + " WHERE  LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " UNION SELECT  SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AS TK, "
                    + " SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF  ,"
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP  , "
                    + " SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " AS LEAVE_SYMBOL ,"
                    + " SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID] + " AS OID_DETAIL   ,"
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_HR_MAN_APPROVAL] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DEP_HEAD_APPROVAL] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_TYPE_FROM_LEAVE] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_HR_MAN_APPROVE_DATE] + ","
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DEP_HEAD_APPROVE_DATE] + ","
                    //update by satrya 2014-01-17
                    + " LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] //APP.EMPLOYEE_ID
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA  "
                    + " INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU "
                    + " ON (LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " = SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID] + ")"
                    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM "
                    + " ON SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID] + " WHERE  LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED + ") AS APP"
                    + " LEFT JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMPDEPHEAD ON APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DEP_HEAD_APPROVAL] + "=EMPDEPHEAD." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " LEFT JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS EMPHRMAN ON APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_HR_MAN_APPROVAL] + "=EMPHRMAN." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " LEFT JOIN " + PstPublicLeaveDetail.TBL_PUBLIC_LEAVE_DETAIL + " AS PBL ON  PBL." + PstPublicLeaveDetail.fieldNames[PstPublicLeaveDetail.FLD_LEAVE_APPLICATION_ID] + " = APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " WHERE "
                    + " APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_TYPE_FROM_LEAVE] + " = " + leaveApp.getTypeLeave()
                    + " AND APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + "=" + leaveApp.getOID();

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                SessLeaveApplicationEmail sessLeaveApplicationEmail = new SessLeaveApplicationEmail();
                sessLeaveApplicationEmail.setDateDepHeadApprovall(rs.getDate("APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DEP_HEAD_APPROVE_DATE]));
                sessLeaveApplicationEmail.setDateHrApprovall(rs.getDate("APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_HR_MAN_APPROVE_DATE]));
                sessLeaveApplicationEmail.setFullNameDepHead(rs.getString("EMPDEPHEAD." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                sessLeaveApplicationEmail.setFullNameHr(rs.getString("EMPHRMAN." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]));
                sessLeaveApplicationEmail.setSymbole(rs.getString("LEAVE_SYMBOL"));
                sessLeaveApplicationEmail.setTakenDateStart(convertDate(rs.getDate("APP.TK"), rs.getTime("APP.TK")));
                sessLeaveApplicationEmail.setTakenFinish(convertDate(rs.getDate("APP.TF"), rs.getTime("APP.TF")));
                sessLeaveApplicationEmail.setDtOfRequest(rs.getDate("APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]));
                sessLeaveApplicationEmail.setEmployeeId(rs.getLong("APP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]));
                result.add(sessLeaveApplicationEmail);
            }
            return result;
        } catch (Exception e) {
            System.out.println("\t Exception on seach LeaveApplication : " + e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    /**
     *
     * @param employeeId
     * @param dateLeave
     * @keterangan : untuk memunculkan SYMBOL LEAVE
     * @return
     */
    //update bty satrya 2012-08-23
    public static Vector listOid(long employeeId, Date dateLeave) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            /*            String sql = "SELECT LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
             /// String sql = "SELECT AL."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
             + " , \"AL\" AS LEAVE_SYMBOL "
             + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA " 
             + " INNER JOIN  " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  "
             + "ON ( AL."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
             + " = LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
             + " WHERE  LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] 
             + " = \"" +  employeeId  +"\" AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
             + "!= " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED +" AND " 
             + "\""+ dateLeave +"\" BETWEEN DATE(AL."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +") "
             + "AND DATE(AL."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] 
             + ") AND LA." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
             + " = \"" +  employeeId  +"\" "
                    
             + " UNION SELECT LA." +PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
             + " , \"DP\" AS LEAVE_SYMBOL "
             + " FROM " +  PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
             + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + "  AS DP ON (LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " = DP."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
             + " WHERE  LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] 
             + " = \"" +  employeeId  +"\" AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
             + "!= " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED +" AND " 
             + "\""+ dateLeave+"\" BETWEEN DATE(DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]
             + ") AND DATE(DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] 
             + ") AND LA."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "= \"" +  employeeId  +"\" "

             + " UNION SELECT LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
             + " , \"LL\" AS LEAVE_SYMBOL "
             + " FROM "+ PstLeaveApplication.TBL_LEAVE_APPLICATION  + " AS LA "
             + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN 
             + " AS LL ON (LL." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
             + " = LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] +")"
             + " WHERE  LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] 
             + " = \"" +  employeeId  +"\" AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
             + "!= " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED +" AND " 
             + "\""+ dateLeave +"\" BETWEEN DATE(LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+") AND DATE(LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]
             + ") AND LA."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "= \"" + employeeId +"\""
                
             + " UNION SELECT LA." +PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
             + " , SYM."+PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] 
             + " AS LEAVE_SYMBOL "
             + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
             + " INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] 
             + "= SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID] + ")"
             + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM ON SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = SYM." +PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
             + " WHERE  LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] 
             + " = \"" +  employeeId  +"\" AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
             + "!= " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED +" AND " 
             + "\""+ dateLeave  +"\" BETWEEN DATE(SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+ ") AND DATE(SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]
             + ") AND LA."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=\"" +  employeeId  +"\"";*/
            String sql = " SELECT * FROM (SELECT AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AS TK "
                    + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF "
                    + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
                    + " , \"AL\" AS LEAVE_SYMBOL "
                    // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //update by satrya 2012-10-31
                    + ",AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID] + " AS OID_DETAIL "
                    + " ,LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN  " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  ON ( AL."
                    + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID]
                    + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                    + " WHERE "
                    //update by satrya 2013-03-01
                    + "\"" + Formater.formatDate(dateLeave, "yyyy-MM-dd 23:59:59") + "\" > AL."
                    + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AND AL."
                    + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateLeave, "yyyy-MM-dd 00:00:00") + "\""
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " UNION SELECT DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] + "  AS TK "
                    + ",DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF "
                    + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
                    + " , \"DP\" AS LEAVE_SYMBOL "
                    // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //update by satrya 2012-10-31
                    + ",DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_TAKEN_ID] + " AS OID_DETAIL "
                    + " ,LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + " AS DP ON (LA."
                    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " = DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_LEAVE_APPLICATION_ID] + ")"
                    + " WHERE "
                    //update by satrya 2013-03-01
                    + "\"" + Formater.formatDate(dateLeave, "yyyy-MM-dd 23:59:59") + "\" > DP."
                    + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] + " AND DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateLeave, "yyyy-MM-dd 00:00:00") + "\""
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                    //update by satrya 2012-11-08
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " UNION SELECT LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AS TK "
                    + ",LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + "  AS TF "
                    + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
                    + " , \"LL\" AS LEAVE_SYMBOL "
                    //+ ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //update by satrya 2012-10-31
                    + ",LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_TAKEN_ID] + "  AS OID_DETAIL "
                    + " ,LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN + " AS LL ON (LL."
                    + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID]
                    + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                    + " WHERE "
                    //update by satrya 2013-03-01
                    + "\"" + Formater.formatDate(dateLeave, "yyyy-MM-dd 23:59:59") + "\" > LL."
                    + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AND LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateLeave, "yyyy-MM-dd 00:00:00") + "\""
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " UNION SELECT  SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AS TK "
                    + ",SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " AS TF"
                    + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " AS EMP "
                    + " , SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + " AS LEAVE_SYMBOL "
                    // + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " AS LPID "
                    //uypdate by satrya 2012-10-31
                    + ",SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID] + " AS OID_DETAIL "
                    + " ,LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ",LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA."
                    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " = SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID] + ")"
                    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM ON SU."
                    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " WHERE "
                    //update by satrya 2013-03-01
                    + "\"" + Formater.formatDate(dateLeave, "yyyy-MM-dd 23:59:59") + "\" > SU."
                    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AND SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateLeave, "yyyy-MM-dd 00:00:00") + "\""
                    //+ " ((SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] +" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+")" 
                    //+ " OR (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+" BETWEEN \""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\""+" AND "+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+ "))"  
                    //+ " AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+"<>"+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") "
                    //hidden by satrya 2013-02-24
                    //+ " AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+"!="+"\""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss")+"\""+") AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+"!="+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"
                    //+ " AND (SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]+"<>"+"\""+Formater.formatDate(selectDateFrom, "yyyy-MM-dd HH:mm:ss")+"\")"                                       
                    //                    + "\""+ sSelectDate +"\" BETWEEN DATE(SU."
                    //    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+") AND DATE(SU."
                    //    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + ") "

                    + " AND LA."
                    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED + ") AS T GROUP BY T.LEAVE_SYMBOL";


            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            int totLeave = 0;
            while (rs.next()) {
                //LeaveApplication objLeaveApplication = new LeaveApplication();
                //resultToObject(rs, objLeaveApplication);
                LeaveOidSym obj = new LeaveOidSym();
                obj.setLeaveOid(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]));
                obj.setLeaveSymbol(rs.getString("LEAVE_SYMBOL"));

                lists.add(obj);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }
    //update bty satrya 2012-08-13

    /**
     *
     * @param employeeId
     * @param dateLeave
     * @return
     */
    public static Vector listDetailLeave(long employeeId, String dateLeave) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT AL." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " , \"AL\" AS LEAVE_SYMBOL "
                    //update by satrya 2012-08-12
                    + " , AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AS SD "
                    + " , AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS FD "
                    //end
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN  " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  "
                    + "ON ( AL." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                    + " WHERE  LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]
                    + " = \"" + employeeId + "\" AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
                    + "!= " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED + " AND "
                    + "\"" + dateLeave + "\" BETWEEN DATE (AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + ") "
                    + " AND DATE (AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE]
                    + ") AND LA." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]
                    + " = \"" + employeeId + "\" "
                    + " UNION SELECT LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " , \"DP\" AS LEAVE_SYMBOL "
                    //update by satrya 2012-08-12
                    + " , DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] + " AS SD "
                    + " , DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS FD "
                    //end
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + "  AS DP ON (LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " = DP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                    + " WHERE  LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]
                    + " = \"" + employeeId + "\" AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
                    + "!= " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED + " AND "
                    + "\"" + dateLeave + "\" BETWEEN DATE (DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]
                    + ") AND DATE(DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE]
                    + ") AND LA." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "= \"" + employeeId + "\" "
                    + " UNION SELECT LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " , \"LL\" AS LEAVE_SYMBOL "
                    //update by satrya 2012-08-12
                    + " , LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AS SD "
                    + " , LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS FD "
                    //end
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN
                    + " AS LL ON (LL." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                    + " WHERE  LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]
                    + " = \"" + employeeId + "\" AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
                    + "!= " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED + " AND "
                    + "\"" + dateLeave + "\" BETWEEN DATE(LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + ") AND DATE(LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]
                    + ") AND LA." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "= \"" + employeeId + "\""
                    + " UNION SELECT LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " , SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + " AS LEAVE_SYMBOL "
                    //update by satrya 2012-08-12
                    + " , SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AS SD "
                    + " , SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " AS FD "
                    //end
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + "= SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID] + ")"
                    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM ON SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " WHERE  LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]
                    + " = \"" + employeeId + "\" AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
                    + "!= " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED + " AND "
                    + "\"" + dateLeave + "\" BETWEEN DATE(SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + ") AND DATE(SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]
                    + ") AND LA." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=\"" + employeeId + "\"";

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                //LeaveApplication objLeaveApplication = new LeaveApplication();
                //resultToObject(rs, objLeaveApplication);
                LeaveOidSym obj = new LeaveOidSym();
                obj.setLeaveOid(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]));
                obj.setLeaveSymbol(rs.getString("LEAVE_SYMBOL"));
                //updaTE by satrya 2012-08-12
                // obj.setTakenDate(rs.getDate("SD"));
                java.util.Date takenDate = DBHandler.convertDate(rs.getDate("SD"), rs.getTime("SD"));
                obj.setTakenDate(takenDate);
                obj.setFinnishDate(rs.getDate("FD"));
                java.util.Date finnishDate = DBHandler.convertDate(rs.getDate("FD"), rs.getTime("FD"));
                obj.setFinnishDate(finnishDate);
                //end
                lists.add(obj);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static boolean checkDateInLeave(Date dDate, Vector<LeaveOidSym> leaveOidSyms) {
        if (dDate == null || leaveOidSyms == null || leaveOidSyms.size() < 1) {
            return false;
        } else {
            for (int i = 0; i < leaveOidSyms.size(); i++) {
                LeaveOidSym leaveOidSym = (LeaveOidSym) leaveOidSyms.get(i);
                if ((DateCalc.dayDifference(leaveOidSym.getTakenDate(), dDate) >= 0)
                        && (DateCalc.dayDifference(dDate, leaveOidSym.getTakenDate()) >= 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Vector listDetailLeave(long employeeId, Date startDate, Date endDate) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT AL." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " , \"AL\" AS LEAVE_SYMBOL "
                    //update by satrya 2012-08-12
                    + " , AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AS SD "
                    + " , AL." + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS FD "
                    //end
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN  " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  "
                    + "ON ( AL." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                    + " WHERE  "
                    /* + " LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] 
                     + " = \"" +  employeeId  +"\" AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
                     + "!= " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED +" AND " 
                     /** update by Kartika 7 Nov 2012 **/
                    /*+ "( ( AL."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +" BETWEEN (\""+Formater.formatDate(startDate,"yyyy-MM-dd") +
                     " 00:00:00\") AND (\""+Formater.formatDate(endDate,"yyyy-MM-dd") +" 23:59:59\")) OR "
                     +   "( AL."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] 
                     +" BETWEEN (\""+Formater.formatDate(startDate,"yyyy-MM-dd") +" 00:00:00\") AND (\""+Formater.formatDate(endDate,"yyyy-MM-dd") +" 23:59:59\")) ) "                
                     + " AND LA." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                     + " = \"" +  employeeId  +"\" "*/
                    //update by satrya 2013-04-06
                    + "\"" + Formater.formatDate(endDate, "yyyy-MM-dd 23:59:59") + "\" > AL."
                    + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AND AL."
                    + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(startDate, "yyyy-MM-dd 00:00:00") + "\""
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " UNION SELECT LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " , \"DP\" AS LEAVE_SYMBOL "
                    //update by satrya 2012-08-12
                    + " , DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] + " AS SD "
                    + " , DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS FD "
                    //end
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + "  AS DP ON (LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " = DP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                    + " WHERE  "
                    /*LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] 
                     + " = \"" +  employeeId  +"\" AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
                     + "!= " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED +" AND "  
                     + "( ( DP."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +/*update by kartika 24 Okt 2012*///" BETWEEN (\""+Formater.formatDate(startDate,"yyyy-MM-dd") +
                    /*     "  00:00:0\") AND (\""+Formater.formatDate(endDate,"yyyy-MM-dd") +" 23:59:59\")) OR "
                     +   "( DP."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] 
                     +" BETWEEN (\""+Formater.formatDate(startDate,"yyyy-MM-dd") +"  00:00:00\") AND (\""+Formater.formatDate(endDate,"yyyy-MM-dd") +" 23:59:59\")) ) "                
                     + " AND LA."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "= \"" +  employeeId  +"\" "*/
                    //update by satrya 2013-06-04
                    + "\"" + Formater.formatDate(endDate, "yyyy-MM-dd 23:59:59") + "\" > DP."
                    + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] + " AND DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(startDate, "yyyy-MM-dd 00:00:00") + "\""
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " UNION SELECT LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " , \"LL\" AS LEAVE_SYMBOL "
                    //update by satrya 2012-08-12
                    + " , LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AS SD "
                    + " , LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + " AS FD "
                    //end
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN
                    + " AS LL ON (LL." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                    + " WHERE "
                    /*LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] 
                     + " = \"" +  employeeId  +"\" AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
                     + "!= " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED +" AND " 
                     + "( ( LL."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +" BETWEEN (\""+Formater.formatDate(startDate,"yyyy-MM-dd") +
                     "  00:00:00\") AND (\""+Formater.formatDate(endDate,"yyyy-MM-dd") +" 23:59:59\")) OR "
                     +   "( LL."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] 
                     +" BETWEEN (\""+Formater.formatDate(startDate,"yyyy-MM-dd") +"  00:00:00\") AND DATE(\""+Formater.formatDate(endDate,"yyyy-MM-dd") +" 23:59:59\")) ) "                                    
                     + " AND LA."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "= \"" + employeeId +"\""*/
                    + "\"" + Formater.formatDate(endDate, "yyyy-MM-dd 23:59:59") + "\" > LL."
                    + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AND LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(startDate, "yyyy-MM-dd 00:00:00") + "\""
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " UNION SELECT LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " , SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]
                    + " AS LEAVE_SYMBOL "
                    //update by satrya 2012-08-12
                    + " , SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AS SD "
                    + " , SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " AS FD "
                    //end
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " = SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID] + ")"
                    + " INNER JOIN " + PstScheduleSymbol.TBL_HR_SCHEDULE_SYMBOL + " AS SYM ON SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID] + " = SYM." + PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SCHEDULE_ID]
                    + " WHERE "
                    /*LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] 
                     + " = \"" +  employeeId  +"\" AND LA."+PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
                     + "!= " +PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED +" AND " 
                     + "( ( SU."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +" BETWEEN (\""+Formater.formatDate(startDate,"yyyy-MM-dd") +
                     " 00:00:00\") AND (\""+Formater.formatDate(endDate,"yyyy-MM-dd") +" 23:59:59\")) OR "
                     +   "( SU."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] 
                     +" BETWEEN (\""+Formater.formatDate(startDate,"yyyy-MM-dd") +" 00:00:00\") AND (\""+Formater.formatDate(endDate,"yyyy-MM-dd") +" 23:59:59\")) ) "                                
                     + " AND LA."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=\"" +  employeeId  +"\""*/
                    + "\"" + Formater.formatDate(endDate, "yyyy-MM-dd 23:59:59") + "\" > SU."
                    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AND SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(startDate, "yyyy-MM-dd 00:00:00") + "\""
                    + " AND LA."
                    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED;
            ;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                //LeaveApplication objLeaveApplication = new LeaveApplication();
                //resultToObject(rs, objLeaveApplication);
                LeaveOidSym obj = new LeaveOidSym();
                obj.setLeaveOid(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]));
                obj.setLeaveSymbol(rs.getString("LEAVE_SYMBOL"));
                //updaTE by satrya 2012-08-12
                // obj.setTakenDate(rs.getDate("SD"));
                java.util.Date takenDate = DBHandler.convertDate(rs.getDate("SD"), rs.getTime("SD"));
                obj.setTakenDate(takenDate);
                obj.setFinnishDate(rs.getDate("FD"));
                java.util.Date finnishDate = DBHandler.convertDate(rs.getDate("FD"), rs.getTime("FD"));
                obj.setFinnishDate(finnishDate);
                //end
                lists.add(obj);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    //update by satrya 2012-08-01
    /**
     *
     * @param employeeId
     * @param dateLeave
     * @return status doc tidak ada maka return -1 (New)
     */
    public static int getLeaveFormStatus(long employeeId, Date dateFromLeave, Date dateToLeave) {
        //Vector lists = new Vector();
        int leaveFormStatus = -1;
        DBResultSet dbrs = null;
        try {
            if (dateFromLeave != null && dateToLeave != null) {
                String sql = "SELECT LA." + PstLeaveApplication.fieldNames[FLD_DOC_STATUS]
                        + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                        + " INNER JOIN  " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  "
                        + "ON ( AL." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                        + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                        + " WHERE "
                        /*+ " WHERE \""+ dateLeave +"\" BETWEEN DATE(AL."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +") "
                         + "AND DATE(AL."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] 
                         + ") AND LA." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                         + " = \"" + employeeId +"\" AND LA."+ PstLeaveApplication.fieldNames[FLD_DOC_STATUS]  + "!=" + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED*/
                        //update by satrya 2013-03-01
                        + "\"" + Formater.formatDate(dateToLeave, "yyyy-MM-dd HH:mm:ss") + "\" > AL."
                        + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AND AL."
                        + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateFromLeave, "yyyy-MM-dd HH:mm:ss") + "\""
                        + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                        + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                        + " UNION SELECT LA." + PstLeaveApplication.fieldNames[FLD_DOC_STATUS]
                        + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                        + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + "  AS DP ON (LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " = DP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                        /*+ " WHERE \""+ dateLeave +"\" BETWEEN DATE(DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]
                         + ") AND DATE(DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] 
                         + ") AND LA."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "= \"" + employeeId +"\" AND LA."+ PstLeaveApplication.fieldNames[FLD_DOC_STATUS]  + "!=" + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED*/
                        + " WHERE "
                        //update by satrya 2013-03-01
                        + "\"" + Formater.formatDate(dateToLeave, "yyyy-MM-dd HH:mm:ss") + "\" > DP."
                        + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] + " AND DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateFromLeave, "yyyy-MM-dd HH:mm:ss") + "\""
                        + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                        + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                        + " UNION SELECT LA." + PstLeaveApplication.fieldNames[FLD_DOC_STATUS]
                        + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                        + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN
                        + " AS LL ON (LL." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                        + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                        /*             + " WHERE \""+ dateLeave +"\" BETWEEN DATE(LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+") AND DATE(LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]
                         + ") AND LA."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "= \"" + employeeId +"\" AND LA."+ PstLeaveApplication.fieldNames[FLD_DOC_STATUS]  + "!=" + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED*/
                        + " WHERE "
                        + "\"" + Formater.formatDate(dateToLeave, "yyyy-MM-dd HH:mm:ss") + "\" > LL."
                        + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AND LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateFromLeave, "yyyy-MM-dd HH:mm:ss") + "\""
                        + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                        + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                        + " UNION SELECT LA." + PstLeaveApplication.fieldNames[FLD_DOC_STATUS]
                        + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                        + "INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                        + "= SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID] + ")"
                        /*+ " WHERE  \""+ dateLeave +"\" BETWEEN DATE(SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+ ") AND DATE(SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]
                         + ") AND LA."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=\"" + employeeId +"\" AND LA."+ PstLeaveApplication.fieldNames[FLD_DOC_STATUS]  + "!=" + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED;*/
                        + " WHERE "
                        //update by satrya 2013-03-01
                        + "\"" + Formater.formatDate(dateToLeave, "yyyy-MM-dd HH:mm:ss") + "\" > SU."
                        + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AND SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateFromLeave, "yyyy-MM-dd HH:mm:ss") + "\""
                        + " AND LA."
                        + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                        + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED;


                dbrs = DBHandler.execQueryResult(sql);
                ResultSet rs = dbrs.getResultSet();

                while (rs.next()) {
                    leaveFormStatus = rs.getInt(PstLeaveApplication.fieldNames[FLD_DOC_STATUS]);
                    if (leaveFormStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT || leaveFormStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED) {
                        return leaveFormStatus;
                    }
                }

                rs.close();
                return leaveFormStatus;
            }
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
        return leaveFormStatus;
    }

    public static int getLeaveFormStatus(long employeeId, Date dateLeave) {
        //Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT LA." + PstLeaveApplication.fieldNames[FLD_DOC_STATUS]
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN  " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN + " AS AL  "
                    + "ON ( AL." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                    + " WHERE "
                    /*+ " WHERE \""+ dateLeave +"\" BETWEEN DATE(AL."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] +") "
                     + "AND DATE(AL."+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] 
                     + ") AND LA." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] 
                     + " = \"" + employeeId +"\" AND LA."+ PstLeaveApplication.fieldNames[FLD_DOC_STATUS]  + "!=" + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED*/
                    //update by satrya 2013-03-01
                    + "\"" + Formater.formatDate(dateLeave, "yyyy-MM-dd 23:59:59") + "\" > AL."
                    + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE] + " AND AL."
                    + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateLeave, "yyyy-MM-dd 00:00:00") + "\""
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " UNION SELECT LA." + PstLeaveApplication.fieldNames[FLD_DOC_STATUS]
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN " + PstDpStockTaken.TBL_HR_DP_STOCK_TAKEN + "  AS DP ON (LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " = DP." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                    /*+ " WHERE \""+ dateLeave +"\" BETWEEN DATE(DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE]
                     + ") AND DATE(DP."+PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] 
                     + ") AND LA."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "= \"" + employeeId +"\" AND LA."+ PstLeaveApplication.fieldNames[FLD_DOC_STATUS]  + "!=" + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED*/
                    + " WHERE "
                    //update by satrya 2013-03-01
                    + "\"" + Formater.formatDate(dateLeave, "yyyy-MM-dd 23:59:59") + "\" > DP."
                    + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE] + " AND DP." + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateLeave, "yyyy-MM-dd 00:00:00") + "\""
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " UNION SELECT LA." + PstLeaveApplication.fieldNames[FLD_DOC_STATUS]
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + " INNER JOIN " + PstLlStockTaken.TBL_HR_LL_STOCK_TAKEN
                    + " AS LL ON (LL." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " = LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ")"
                    /*             + " WHERE \""+ dateLeave +"\" BETWEEN DATE(LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE]+") AND DATE(LL."+PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE]
                     + ") AND LA."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "= \"" + employeeId +"\" AND LA."+ PstLeaveApplication.fieldNames[FLD_DOC_STATUS]  + "!=" + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED*/
                    + " WHERE "
                    + "\"" + Formater.formatDate(dateLeave, "yyyy-MM-dd 23:59:59") + "\" > LL."
                    + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE] + " AND LL." + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateLeave, "yyyy-MM-dd 00:00:00") + "\""
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED
                    + " UNION SELECT LA." + PstLeaveApplication.fieldNames[FLD_DOC_STATUS]
                    + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                    + "INNER JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN + " AS SU ON (LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + "= SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID] + ")"
                    /*+ " WHERE  \""+ dateLeave +"\" BETWEEN DATE(SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+ ") AND DATE(SU."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]
                     + ") AND LA."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + "=\"" + employeeId +"\" AND LA."+ PstLeaveApplication.fieldNames[FLD_DOC_STATUS]  + "!=" + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED;*/
                    + " WHERE "
                    //update by satrya 2013-03-01
                    + "\"" + Formater.formatDate(dateLeave, "yyyy-MM-dd 23:59:59") + "\" > SU."
                    + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AND SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE] + " > \"" + Formater.formatDate(dateLeave, "yyyy-MM-dd 00:00:00") + "\""
                    + " AND LA."
                    + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = \"" + employeeId + "\" "
                    + " AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS] + " != " + PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED;


            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            int leaveFormStatus = -1;
            while (rs.next()) {
                leaveFormStatus = rs.getInt(PstLeaveApplication.fieldNames[FLD_DOC_STATUS]);
                if (leaveFormStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_DRAFT || leaveFormStatus == PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED) {
                    return leaveFormStatus;
                }
            }

            rs.close();
            return leaveFormStatus;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    //end

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ") FROM " + TBL_LEAVE_APPLICATION;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
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

    private static void resultToObject(ResultSet rs, LeaveApplication objLeaveApplication) {
        try {
            objLeaveApplication.setOID(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]));
            objLeaveApplication.setSubmissionDate(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]));
            objLeaveApplication.setEmployeeId(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]));
            objLeaveApplication.setLeaveReason(rs.getString(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_REASON]));
            objLeaveApplication.setDepHeadApproval(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DEP_HEAD_APPROVAL]));
            objLeaveApplication.setHrManApproval(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_HR_MAN_APPROVAL]));
            objLeaveApplication.setDocStatus(rs.getInt(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]));
            objLeaveApplication.setDepHeadApproveDate(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DEP_HEAD_APPROVE_DATE]));
            objLeaveApplication.setHrManApproveDate(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_HR_MAN_APPROVE_DATE]));
            objLeaveApplication.setGmApproval(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_GM_APPROVAL]));
            objLeaveApplication.setGmApprovalDate(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_GM_APPROVAL_DATE]));
            //update by satrya 2013-03-13
            objLeaveApplication.setTypeLeave(rs.getInt(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_TYPE_LEAVE]));
            //update by satrya 2013-04-17
            objLeaveApplication.setReasonId(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_REASON_ID]));
            // update by devin 2014-0319
            objLeaveApplication.setLeaveAppDiffPeriod(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_DIFFERENT_PERIOD]));
            objLeaveApplication.setApproval_1(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_APPROVAL_1]));
            objLeaveApplication.setApproval_1_date(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_APPROVAL_1_DATE]));
            objLeaveApplication.setApproval_2(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_APPROVAL_2]));
            objLeaveApplication.setApproval_2_date(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_APPROVAL_2_DATE]));
            objLeaveApplication.setApproval_3(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_APPROVAL_3]));
            objLeaveApplication.setApproval_3_date(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_APPROVAL_3_DATE]));
            objLeaveApplication.setApproval_4(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_APPROVAL_4]));
            objLeaveApplication.setApproval_4_date(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_APPROVAL_4_DATE]));
            objLeaveApplication.setApproval_5(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_APPROVAL_5]));
            objLeaveApplication.setApproval_5_date(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_APPROVAL_5_DATE]));
            objLeaveApplication.setApproval_6(rs.getLong(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_APPROVAL_6]));
            objLeaveApplication.setApproval_6_date(rs.getDate(PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_APPROVAL_6_DATE]));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param leaveType
     * @param dpTakenDate
     * @param employeeId
     * @return
     */
    public static long getLeaveApplicationIdBySchedule(int leaveType, Date leaveTakenDate, long employeeId) {
        return getLeaveMainOid(leaveType, employeeId, leaveTakenDate);
    }

    /**
     * @param oidLeaveAppMain
     * @return
     */
    public static long getLeaveMainOid(int leaveType, long employeeId, Date selectedDate) {
        long result = 0;
        DBResultSet dbrs = null;

        String strSelectedDate = "";
        if (selectedDate != null) {
            strSelectedDate = Formater.formatDate(selectedDate, "yyyy-MM-dd");
        }

        try {
            String sql = "SELECT MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " FROM " + TBL_LEAVE_APPLICATION + " AS MAIN "
                    + " INNER JOIN " + PstLeaveApplicationDetail.TBL_LEAVE_APPLICATION_DETAIL + " AS DETAIL "
                    + " ON MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " = DETAIL." + PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_LEAVE_APPLICATION_MAIN_ID]
                    + " WHERE MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]
                    + " = " + employeeId
                    + " AND DETAIL." + PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_LEAVE_TYPE]
                    + " = " + leaveType
                    + " AND DETAIL." + PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_TAKEN_DATE]
                    + " = \"" + strSelectedDate + "\""
                    + " AND MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
                    + " = " + PstLeaveApplication.FLD_DOC_STATUS_VALID;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getLong(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * @param oidLeaveAppMain
     * @return
     */
    public static LeaveApplication getDocLeaveApplication(long lEmployeeOid, Date dTakenFromDate, Date dTakenToDate) {
        LeaveApplication objLeaveApplication = new LeaveApplication();
        DBResultSet dbrs = null;

        String sTakenFromDate = "";
        String sTakenToDate = "";
        if (dTakenFromDate != null) {
            sTakenFromDate = Formater.formatDate(dTakenFromDate, "yyyy-MM-dd");
            sTakenToDate = Formater.formatDate(dTakenToDate, "yyyy-MM-dd");
        }

        try {
            String sql = "SELECT MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + ", MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_SUBMISSION_DATE]
                    + ", MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]
                    + ", MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_REASON]
                    + ", MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DEP_HEAD_APPROVAL]
                    + ", MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_HR_MAN_APPROVAL]
                    + ", MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
                    + ", MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DEP_HEAD_APPROVE_DATE]
                    + ", MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_HR_MAN_APPROVE_DATE]
                    + " FROM " + TBL_LEAVE_APPLICATION + " AS MAIN "
                    + " INNER JOIN " + PstLeaveApplicationDetail.TBL_LEAVE_APPLICATION_DETAIL + " AS DETAIL "
                    + " ON MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                    + " = DETAIL." + PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_LEAVE_APPLICATION_MAIN_ID]
                    + " WHERE MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID]
                    + " = " + lEmployeeOid
                    + " AND DETAIL." + PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_TAKEN_DATE]
                    + " BETWEEN \"" + sTakenFromDate + "\""
                    + " AND \"" + dTakenToDate + "\""
                    + " AND MAIN." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]
                    + " = " + PstLeaveApplication.FLD_DOC_STATUS_VALID;

            //System.out.println("leaveAppDocExist sql : " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                objLeaveApplication.setOID(rs.getLong(1));
                objLeaveApplication.setSubmissionDate(rs.getDate(2));
                objLeaveApplication.setEmployeeId(rs.getLong(3));
                objLeaveApplication.setLeaveReason(rs.getString(4));
                objLeaveApplication.setDepHeadApproval(rs.getLong(5));
                objLeaveApplication.setHrManApproval(rs.getLong(6));
                objLeaveApplication.setDocStatus(rs.getInt(7));
                objLeaveApplication.setDepHeadApproveDate(rs.getDate(8));
                objLeaveApplication.setHrManApproveDate(rs.getDate(9));

                break;
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
            return objLeaveApplication;
        }
    }

    //devin
    public static long cariDiferent(long oidLeave) {
        long result = 0;
        DBResultSet dbrs = null;

        try {
            String sql = "SELECT " + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_DIFFERENT_PERIOD] + " FROM " + PstLeaveApplication.TBL_LEAVE_APPLICATION
                    + " WHERE " + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + " = " + oidLeave;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getLong(1);
            }

        } catch (Exception exc) {
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * create by devin 20140409 ket: berhubungan dengan leave form beda period
     *
     * @param employeeId
     * @param oidLeave
     * @return
     */
    public static Vector cariDate(long employeeId, long oidLeave) {
        Vector result = new Vector();
        DBResultSet dbrs = null;

        try {
            String sql = "SELECT * FROM " + PstAlStockTaken.TBL_HR_AL_STOCK_TAKEN
                    + " WHERE " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] + " = " + employeeId + " AND " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_TAKEN_ID] + " = " + oidLeave;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                AlStockTaken al = new AlStockTaken();
                al.setTakenDate(rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]));
                al.setTakenFinnishDate(rs.getDate(PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_FINNISH_DATE]));
                result.add(al);
            }

        } catch (Exception exc) {
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    /**
     * used to generate(insert) leave application's documents when schedule
     * insert process occur
     *
     * @param objEmpSchedule
     * @return
     */
    public static int generateLeaveApplication(EmpSchedule objEmpSchedule) {
        int result = 0;

        // generate Leave Application Main
        LeaveApplication objLeaveApplication = new LeaveApplication();
        objLeaveApplication.setEmployeeId(objEmpSchedule.getEmployeeId());
        objLeaveApplication.setSubmissionDate(new Date());
        objLeaveApplication.setLeaveReason("Please specify leave application's reason ...");
        objLeaveApplication.setDepHeadApproval(0);
        objLeaveApplication.setHrManApproval(0);
        objLeaveApplication.setDocStatus(PstLeaveApplication.FLD_DOC_STATUS_VALID);


        // generate tanggal awal periode
        Date startPeriodDate = null;
        try {
            Period objPeriod = PstPeriod.fetchExc(objEmpSchedule.getPeriodId());
            startPeriodDate = objPeriod.getStartDate();
        } catch (Exception e) {
            System.out.println("Exc : " + e.toString());
        }


        // proses schedule dari tanggal 01 - 31
        long[] arrObjEmpSchedule = {
            objEmpSchedule.getD1(),
            objEmpSchedule.getD2(),
            objEmpSchedule.getD3(),
            objEmpSchedule.getD4(),
            objEmpSchedule.getD5(),
            objEmpSchedule.getD6(),
            objEmpSchedule.getD7(),
            objEmpSchedule.getD8(),
            objEmpSchedule.getD9(),
            objEmpSchedule.getD10(),
            objEmpSchedule.getD11(),
            objEmpSchedule.getD12(),
            objEmpSchedule.getD13(),
            objEmpSchedule.getD14(),
            objEmpSchedule.getD15(),
            objEmpSchedule.getD16(),
            objEmpSchedule.getD17(),
            objEmpSchedule.getD18(),
            objEmpSchedule.getD19(),
            objEmpSchedule.getD20(),
            objEmpSchedule.getD21(),
            objEmpSchedule.getD22(),
            objEmpSchedule.getD23(),
            objEmpSchedule.getD24(),
            objEmpSchedule.getD25(),
            objEmpSchedule.getD26(),
            objEmpSchedule.getD27(),
            objEmpSchedule.getD28(),
            objEmpSchedule.getD29(),
            objEmpSchedule.getD30(),
            objEmpSchedule.getD31()
        };


        long oidAlSchedule = Long.parseLong(PstSystemProperty.getValueByName("OID_AL_LEAVE"));
        long oidLlSchedule = Long.parseLong(PstSystemProperty.getValueByName("OID_LL_LEAVE"));
        long oidMaternitySchedule = Long.parseLong(PstSystemProperty.getValueByName("OID_MATERNITY_LEAVE"));
        long oidDcSchedule = Long.parseLong(PstSystemProperty.getValueByName("OID_SICK_LEAVE"));
        long oidUnpaidSchedule = Long.parseLong(PstSystemProperty.getValueByName("OID_UNPAID_LEAVE"));
        long oidSpecialSchedule = Long.parseLong(PstSystemProperty.getValueByName("OID_SPECIAL_LEAVE"));

        Vector listOfLeaveApplicationDetail = new Vector(1, 1);

        int maxSchedule = arrObjEmpSchedule.length;
        for (int i = 0; i < maxSchedule; i++) {
            LeaveApplicationDetail objLeaveApplicationDetail = new LeaveApplicationDetail();

            long scheduleOid = arrObjEmpSchedule[i];
            if (scheduleOid == oidAlSchedule) {
                Date takenDate = new Date(startPeriodDate.getYear(), startPeriodDate.getMonth(), i + 1);

                objLeaveApplicationDetail.setLeaveType(PstLeaveApplicationDetail.LEAVE_APPLICATION_AL);
                objLeaveApplicationDetail.setTakenDate(takenDate);
                listOfLeaveApplicationDetail.add(objLeaveApplicationDetail);
            }

            if (scheduleOid == oidLlSchedule) {
                Date takenDate = new Date(startPeriodDate.getYear(), startPeriodDate.getMonth(), i + 1);

                objLeaveApplicationDetail.setLeaveType(PstLeaveApplicationDetail.LEAVE_APPLICATION_LL);
                objLeaveApplicationDetail.setTakenDate(takenDate);
                listOfLeaveApplicationDetail.add(objLeaveApplicationDetail);
            }

            if (scheduleOid == oidMaternitySchedule) {
                Date takenDate = new Date(startPeriodDate.getYear(), startPeriodDate.getMonth(), i + 1);

                objLeaveApplicationDetail.setLeaveType(PstLeaveApplicationDetail.LEAVE_APPLICATION_MATERNITY);
                objLeaveApplicationDetail.setTakenDate(takenDate);
                listOfLeaveApplicationDetail.add(objLeaveApplicationDetail);
            }

            if (scheduleOid == oidDcSchedule) {
                Date takenDate = new Date(startPeriodDate.getYear(), startPeriodDate.getMonth(), i + 1);

                objLeaveApplicationDetail.setLeaveType(PstLeaveApplicationDetail.LEAVE_APPLICATION_DC);
                objLeaveApplicationDetail.setTakenDate(takenDate);
                listOfLeaveApplicationDetail.add(objLeaveApplicationDetail);
            }

            if (scheduleOid == oidUnpaidSchedule) {
                Date takenDate = new Date(startPeriodDate.getYear(), startPeriodDate.getMonth(), i + 1);

                objLeaveApplicationDetail.setLeaveType(PstLeaveApplicationDetail.LEAVE_APPLICATION_UNPAID);
                objLeaveApplicationDetail.setTakenDate(takenDate);
                listOfLeaveApplicationDetail.add(objLeaveApplicationDetail);
            }

            if (scheduleOid == oidSpecialSchedule) {
                Date takenDate = new Date(startPeriodDate.getYear(), startPeriodDate.getMonth(), i + 1);

                objLeaveApplicationDetail.setLeaveType(PstLeaveApplicationDetail.LEAVE_APPLICATION_SPECIAL);
                objLeaveApplicationDetail.setTakenDate(takenDate);
                listOfLeaveApplicationDetail.add(objLeaveApplicationDetail);
            }
        }

        objLeaveApplication.setListOfDetail(listOfLeaveApplicationDetail);


        try {
            // process leave application detail
            Vector vectOfLeaveAppDetail = objLeaveApplication.getListOfDetail();
            if (vectOfLeaveAppDetail != null && vectOfLeaveAppDetail.size() > 0) {
                long oidMain = PstLeaveApplication.insertExc(objLeaveApplication);

                int leaveAppCount = vectOfLeaveAppDetail.size();
                for (int ij = 0; ij < leaveAppCount; ij++) {
                    LeaveApplicationDetail objLeaveApplicationDetail = (LeaveApplicationDetail) vectOfLeaveAppDetail.get(ij);
                    objLeaveApplicationDetail.setLeaveMainOid(oidMain);
                    long oidLeaveDetail = PstLeaveApplicationDetail.insertExc(objLeaveApplicationDetail);
                }
            }
        } catch (Exception e) {
            System.out.println("Exc when generate LeaveApplication : " + e.toString());
        }

        return result;
    }

    /**
     * used to update leave application's documents when schedule update process
     * occur
     *
     * @param objEmpSchedule
     * @return
     */
    public static int updateLeaveApplication(EmpSchedule objEmpSchedule) {
        int iResult = 0;

        // generate tanggal awal periode
        Date dPeriodStartDate = null;
        Date dPeriodEndDate = null;
        try {
            Period objPeriod = PstPeriod.fetchExc(objEmpSchedule.getPeriodId());
            dPeriodStartDate = objPeriod.getStartDate();
            dPeriodEndDate = objPeriod.getEndDate();
        } catch (Exception e) {
            System.out.println("Exc : " + e.toString());
        }


        // check jumlah document Leave Application dalam periode schedule        
        LeaveApplication objLeaveApplication = PstLeaveApplication.getDocLeaveApplication(objEmpSchedule.getEmployeeId(), dPeriodStartDate, dPeriodEndDate);
        long lLeaveAppOid = objLeaveApplication.getOID();
        if (lLeaveAppOid != 0) {
            // proses schedule dari tanggal 01 - 31
            long[] arrObjEmpSchedule = {
                objEmpSchedule.getD1(),
                objEmpSchedule.getD2(),
                objEmpSchedule.getD3(),
                objEmpSchedule.getD4(),
                objEmpSchedule.getD5(),
                objEmpSchedule.getD6(),
                objEmpSchedule.getD7(),
                objEmpSchedule.getD8(),
                objEmpSchedule.getD9(),
                objEmpSchedule.getD10(),
                objEmpSchedule.getD11(),
                objEmpSchedule.getD12(),
                objEmpSchedule.getD13(),
                objEmpSchedule.getD14(),
                objEmpSchedule.getD15(),
                objEmpSchedule.getD16(),
                objEmpSchedule.getD17(),
                objEmpSchedule.getD18(),
                objEmpSchedule.getD19(),
                objEmpSchedule.getD20(),
                objEmpSchedule.getD21(),
                objEmpSchedule.getD22(),
                objEmpSchedule.getD23(),
                objEmpSchedule.getD24(),
                objEmpSchedule.getD25(),
                objEmpSchedule.getD26(),
                objEmpSchedule.getD27(),
                objEmpSchedule.getD28(),
                objEmpSchedule.getD29(),
                objEmpSchedule.getD30(),
                objEmpSchedule.getD31()
            };

            long lAlScheduleOid = Long.parseLong(PstSystemProperty.getValueByName("OID_AL_LEAVE"));
            long lLlScheduleOid = Long.parseLong(PstSystemProperty.getValueByName("OID_LL_LEAVE"));
            long lMaternityScheduleOid = Long.parseLong(PstSystemProperty.getValueByName("OID_MATERNITY_LEAVE"));
            long lDcScheduleOid = Long.parseLong(PstSystemProperty.getValueByName("OID_SICK_LEAVE"));
            long lUnpaidScheduleOid = Long.parseLong(PstSystemProperty.getValueByName("OID_UNPAID_LEAVE"));
            long lSpecialScheduleOid = Long.parseLong(PstSystemProperty.getValueByName("OID_SPECIAL_LEAVE"));

            int iMaxSchedule = arrObjEmpSchedule.length;
            for (int i = 0; i < iMaxSchedule; i++) {
                Date dTakenDate = new Date(dPeriodStartDate.getYear(), dPeriodStartDate.getMonth(), i + 1);
                LeaveApplicationDetail objLeaveApplicationDetail = PstLeaveApplicationDetail.getLeaveApplicationDetail(objEmpSchedule.getEmployeeId(), dTakenDate);
                long lLeaveAppDetailOid = objLeaveApplicationDetail.getOID();

                long lScheduleOid = arrObjEmpSchedule[i];
                if (lScheduleOid == lAlScheduleOid) {
                    if (lLeaveAppDetailOid == 0) {
                        System.out.println("...::: On " + Formater.formatDate(dTakenDate, "MMM dd, yyyy") + " - Generate Leave Application Detail (AL)");
                        long lResult = PstLeaveApplicationDetail.generateNewLeaveApplicationDetail(lLeaveAppOid, PstLeaveApplicationDetail.LEAVE_APPLICATION_AL, dTakenDate);
                    }
                } else if (lScheduleOid == lLlScheduleOid) {
                    if (objLeaveApplicationDetail.getOID() == 0) {
                        System.out.println("...::: On " + Formater.formatDate(dTakenDate, "MMM dd, yyyy") + " - Generate Leave Application Detail (LL)");
                        long lResult = PstLeaveApplicationDetail.generateNewLeaveApplicationDetail(lLeaveAppOid, PstLeaveApplicationDetail.LEAVE_APPLICATION_LL, dTakenDate);
                    }
                } else if (lScheduleOid == lMaternityScheduleOid) {
                    if (lLeaveAppDetailOid == 0) {
                        System.out.println("...::: On " + Formater.formatDate(dTakenDate, "MMM dd, yyyy") + " - Generate Leave Application Detail (Maternity)");
                        long lResult = PstLeaveApplicationDetail.generateNewLeaveApplicationDetail(lLeaveAppOid, PstLeaveApplicationDetail.LEAVE_APPLICATION_MATERNITY, dTakenDate);
                    }
                } else if (lScheduleOid == lDcScheduleOid) {
                    if (lLeaveAppDetailOid == 0) {
                        System.out.println("...::: On " + Formater.formatDate(dTakenDate, "MMM dd, yyyy") + " - Generate Leave Application Detail (Sick)");
                        long lResult = PstLeaveApplicationDetail.generateNewLeaveApplicationDetail(lLeaveAppOid, PstLeaveApplicationDetail.LEAVE_APPLICATION_DC, dTakenDate);
                    }
                } else if (lScheduleOid == lUnpaidScheduleOid) {
                    if (lLeaveAppDetailOid == 0) {
                        System.out.println("...::: On " + Formater.formatDate(dTakenDate, "MMM dd, yyyy") + " - Generate Leave Application Detail (Unpaid)");
                        long lResult = PstLeaveApplicationDetail.generateNewLeaveApplicationDetail(lLeaveAppOid, PstLeaveApplicationDetail.LEAVE_APPLICATION_UNPAID, dTakenDate);
                    }
                } else if (lScheduleOid == lSpecialScheduleOid) {
                    if (lLeaveAppDetailOid == 0) {
                        System.out.println("...::: On " + Formater.formatDate(dTakenDate, "MMM dd, yyyy") + " - Generate Leave Application Detail (Special)");
                        long lResult = PstLeaveApplicationDetail.generateNewLeaveApplicationDetail(lLeaveAppOid, PstLeaveApplicationDetail.LEAVE_APPLICATION_SPECIAL, dTakenDate);
                    }
                } else {
                    if (lLeaveAppDetailOid != 0) {
                        System.out.println("...::: On " + Formater.formatDate(dTakenDate, "MMM dd, yyyy") + " - Delete Leave Application Detail");
                        try {
                            long lResult = PstLeaveApplicationDetail.deleteExc(lLeaveAppDetailOid);
                        } catch (Exception e) {
                            System.out.println("Exc when delete leave app detail : " + e.toString());
                        }
                    }
                }
            }
        } else {
            System.out.println("...::: Generate Leave Application document and its detail");
            iResult = generateLeaveApplication(objEmpSchedule);
        }

        return iResult;
    }

    /**
     * synchonize data leave utk masing-masing tipe sehingga menjadi format
     * "from" - "to"
     *
     * @param listOfLeaveApplication
     * @return vector on obj 'LeaveApplicationDetailView'
     */
    public static Vector synchLeaveAppDetail(Vector listOfLeaveApplication) {
        Vector result = new Vector(1, 1);

        if (listOfLeaveApplication != null && listOfLeaveApplication.size() > 0) {
            int iLeaveType = -1;
            Date prevTakenDate = null;
            LeaveApplicationDetailView objLeaveApplicationDetailView = new LeaveApplicationDetailView();

            int maxLeaveApp = listOfLeaveApplication.size();
            for (int i = 0; i < maxLeaveApp; i++) {
                LeaveApplicationDetail objLeaveAppDetail = (LeaveApplicationDetail) listOfLeaveApplication.get(i);

                if (i == 0) {
                    objLeaveApplicationDetailView = new LeaveApplicationDetailView();
                    objLeaveApplicationDetailView.setILeaveType(objLeaveAppDetail.getLeaveType());
                    objLeaveApplicationDetailView.setDTakenDateFrom(objLeaveAppDetail.getTakenDate());
                    objLeaveApplicationDetailView.setDTakenDateTo(objLeaveAppDetail.getTakenDate());

                    if (i == (maxLeaveApp - 1)) {
                        result.add(objLeaveApplicationDetailView);
                    }

                    iLeaveType = objLeaveAppDetail.getLeaveType();
                    prevTakenDate = objLeaveAppDetail.getTakenDate();
                } else {
                    if (objLeaveAppDetail.getLeaveType() != iLeaveType) {
                        prevTakenDate = objLeaveAppDetail.getTakenDate();
                    }

                    long dateDuration = objLeaveAppDetail.getTakenDate().getTime() - prevTakenDate.getTime();

                    // proses object old, sehingga hanya perlu set "TakenDateTo" saja                    
                    if (dateDuration == com.dimata.util.DateCalc.DAY_MILLI_SECONDS) {
                        objLeaveApplicationDetailView.setDTakenDateTo(objLeaveAppDetail.getTakenDate());

                        // jika sudah mencapai index terakhir, maka masukkan hasil ke vector of hasil
                        if (i == (maxLeaveApp - 1)) {
                            result.add(objLeaveApplicationDetailView);
                        }

                        iLeaveType = objLeaveAppDetail.getLeaveType();
                        prevTakenDate = objLeaveAppDetail.getTakenDate();
                    }


                    // simpan dulu object old, kemudian generate object baru
                    if ((dateDuration == 0) || (dateDuration > com.dimata.util.DateCalc.DAY_MILLI_SECONDS)) {
                        result.add(objLeaveApplicationDetailView);

                        objLeaveApplicationDetailView = new LeaveApplicationDetailView();
                        objLeaveApplicationDetailView.setILeaveType(objLeaveAppDetail.getLeaveType());
                        objLeaveApplicationDetailView.setDTakenDateFrom(objLeaveAppDetail.getTakenDate());
                        objLeaveApplicationDetailView.setDTakenDateTo(objLeaveAppDetail.getTakenDate());

                        // jika sudah mencapai index terakhir, maka masukkan hasil ke vector of hasil
                        if (i == (maxLeaveApp - 1)) {
                            result.add(objLeaveApplicationDetailView);
                        }

                        iLeaveType = objLeaveAppDetail.getLeaveType();
                        prevTakenDate = objLeaveAppDetail.getTakenDate();
                    }
                }
            }
        }

        return result;
    }

    /**
     * set status old Leave Application to 'invalid'
     *
     * @param objLeaveApplication
     * @return
     */
    public static long setLeaveApplicationToInvalid(LeaveApplication objLeaveApplication) {
        long lResult = 0;

        try {
            objLeaveApplication.setDocStatus(PstLeaveApplication.FLD_DOC_STATUS_INVALID);
            lResult = PstLeaveApplication.updateExc(objLeaveApplication);
        } catch (Exception e) {
            System.out.println("Exc when setLeaveApplicationToInvalid : " + e.toString());
        }

        return lResult;
    }

    /**
     * set status old Leave Application to 'invalid'
     *
     * @param objEmpSchedule
     * @return
     */
    public static int setLeaveApplicationToInvalid(EmpSchedule objEmpSchedule) {
        int iResult = 0;

        // get period duration of specified schedule
        long lSchedulePeriodOid = objEmpSchedule.getPeriodId();
        Date dStartDatePeriod = null;
        Date dEndDatePeriod = null;
        try {
            Period objPeriod = PstPeriod.fetchExc(lSchedulePeriodOid);
            dStartDatePeriod = objPeriod.getStartDate();
            dEndDatePeriod = objPeriod.getEndDate();
        } catch (Exception e) {
            System.out.println("Exc when fetch period object : " + e.toString());
        }


        // get list Leave Application document during period duration
        if (dStartDatePeriod != null && dEndDatePeriod != null) {
            LeaveApplication objLeaveApplication = PstLeaveApplication.getDocLeaveApplication(objEmpSchedule.getEmployeeId(), dStartDatePeriod, dEndDatePeriod);
            long lResult = setLeaveApplicationToInvalid(objLeaveApplication);
            iResult++;
        }

        return iResult;
    }

    /**
     * Untuk melakukan set / insert reason to leave
     *
     * @param dtPresence
     * @param employeeId
     */
    public static int setReasonIdToLeaveOrExcuse(Date dtPresence, long employeeId, int reasonId) {

        DBResultSet dbrs = null;
        int i = 0;
        if (employeeId != 0) {
            try {

                String sql = "UPDATE " + PstLeaveApplication.TBL_LEAVE_APPLICATION + " AS LA "
                        + " LEFT JOIN " + PstSpecialUnpaidLeaveTaken.TBL_SPECIAL_UNPAID_LEAVE_TAKEN
                        + " AS SU ON LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID]
                        + " =SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]
                        + " SET " + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_REASON_ID] + " = " + reasonId
                        + " WHERE \"" + Formater.formatDate(dtPresence, "yyyy-MM-dd 23:59:59") + "\" > SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE] + " AND SU." + PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_FINNISH_DATE]
                        + " > \"" + Formater.formatDate(dtPresence, "yyyy-MM-dd 00:00:00") + "\" AND LA." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_EMPLOYEE_ID] + " = " + employeeId;
                /*"UPDATE " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE + " SET " +
                 PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_SCHEDULE_TYPE] + " = " + PstEmpSchedule.SCHEDULE_ORIGINAL +
                 " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMP_SCHEDULE_ID] + " = " + scheduleID;*/

                //System.out.println("SQL update reason ID " + sql);


                i = DBHandler.execUpdate(sql);

            } catch (Exception e) {
                System.out.println("Exception " + e.toString());
            }
        }
        return i;
    }

    /**
     * @Author Roy Andika
     * @Desc Untuk memproses schedule yang sudah di approved
     * @param leaveApplication
     * @param stsDokApprov
     */
    public static void updateScheduleLeave(LeaveApplication leaveApplication, boolean stsDokApprov) {

        try {
            PstLeaveApplication.updateExc(leaveApplication);
        } catch (Exception E) {
            System.out.println("Exception " + E.toString());
        }

        /**
         * @DESC : PENGECEKAN CONFIGURASI UPDATE SCHEDULE
         */
        //update by satrya 2012-08-03
        int iLeaveMinuteEnable = 0;
        try {
            iLeaveMinuteEnable = Integer.parseInt(PstSystemProperty.getValueByName("LEAVE_MINUTE_ENABLE"));//menambahkan system properties
        } catch (Exception e) {
            System.out.println("Exeception LEAVE_MINUTE_ENABLE:" + e);
        }
        if (SessEmpSchedule.GetConfigurasiUpdtSch() == PstEmpSchedule.UPDATE_SCHEDULE_AFTER_APPROVED && iLeaveMinuteEnable != 1) {
            ///  if (SessEmpSchedule.GetConfigurasiUpdtSch() == PstEmpSchedule.UPDATE_SCHEDULE_AFTER_APPROVED) {

            /**
             * @DEC : UNTUK MENGUPDATE STATUS SCHEDULE JIKA STATUS DOCUMENT
             * APPROVED
             */
            if (stsDokApprov == true || leaveApplication.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_APPROVED) {

                Vector listEmpSchedule = new Vector();

                AlStockTaken alStockTaken = new AlStockTaken();
                LlStockTaken llStockTaken = new LlStockTaken();
                DpStockTaken dpStockTaken = new DpStockTaken();
                SpecialUnpaidLeaveTaken specialUnpaidLeaveTaken = new SpecialUnpaidLeaveTaken();

                Vector listTknAl = SessLeaveApplication.getTakenAl(leaveApplication.getOID());
                Vector listTknLl = SessLeaveApplication.getTakenLl(leaveApplication.getOID());
                Vector listTknDp = SessLeaveApplication.getTakenDp(leaveApplication.getOID());
                Vector listTknSp = SessLeaveApplication.getTakenSpcUnpaid(leaveApplication.getOID());

                String oidAl = String.valueOf(PstSystemProperty.getValueByName("OID_AL"));
                String oidLl = String.valueOf(PstSystemProperty.getValueByName("OID_LL"));
                String oidDp = String.valueOf(PstSystemProperty.getValueByName("OID_DP"));

                for (int idxAl = 0; idxAl < listTknAl.size(); idxAl++) {

                    alStockTaken = (AlStockTaken) listTknAl.get(idxAl);
                    Date strtDate = alStockTaken.getTakenDate();
                    Date newDate = new Date();

                    for (int idxAlTkn = 0; idxAlTkn < alStockTaken.getTakenQty(); idxAlTkn++) {

                        long schId = SessEmpSchedule.getSchId(leaveApplication.getEmployeeId(), strtDate);

                        if (schId != 0) {

                            boolean same = false;

                            if (listEmpSchedule != null && listEmpSchedule.size() > 0) {

                                for (int i = 0; i < listEmpSchedule.size(); i++) {

                                    EmpSchedule objEmpScheduleProcess = new EmpSchedule();
                                    objEmpScheduleProcess = (EmpSchedule) listEmpSchedule.get(i);

                                    if (objEmpScheduleProcess.getOID() == schId) {
                                        same = true;
                                    }
                                }
                            }
                            if (same == false) {
                                EmpSchedule empScheduleProcess = new EmpSchedule();
                                empScheduleProcess.setOID(schId);
                                listEmpSchedule.add(empScheduleProcess);
                            }

                            int status = SessEmpSchedule.updateSchedule(strtDate, schId, oidAl);
                        }
                        long tmpDate = strtDate.getTime() + (24 * 60 * 60 * 1000);
                        newDate = new Date(tmpDate);
                        strtDate = newDate;
                    }
                }

                for (int idxLl = 0; idxLl < listTknLl.size(); idxLl++) {

                    llStockTaken = (LlStockTaken) listTknLl.get(idxLl);
                    Date strtDate = llStockTaken.getTakenDate();
                    Date newDate = new Date();

                    for (int idxLlTkn = 0; idxLlTkn < llStockTaken.getTakenQty(); idxLlTkn++) {

                        long schId = SessEmpSchedule.getSchId(leaveApplication.getEmployeeId(), strtDate);

                        if (schId != 0) {

                            boolean same = false;

                            if (listEmpSchedule != null && listEmpSchedule.size() > 0) {

                                for (int i = 0; i < listEmpSchedule.size(); i++) {

                                    EmpSchedule objEmpScheduleProcess = new EmpSchedule();
                                    objEmpScheduleProcess = (EmpSchedule) listEmpSchedule.get(i);

                                    if (objEmpScheduleProcess.getOID() == schId) {
                                        same = true;
                                    }
                                }
                            }
                            if (same == false) {
                                EmpSchedule empScheduleProcess = new EmpSchedule();
                                empScheduleProcess.setOID(schId);
                                listEmpSchedule.add(empScheduleProcess);
                            }

                            int status = SessEmpSchedule.updateSchedule(strtDate, schId, oidLl);
                        }

                        long tmpDate = strtDate.getTime() + (24 * 60 * 60 * 1000);
                        newDate = new Date(tmpDate);
                        strtDate = newDate;
                    }
                }

                for (int idxDp = 0; idxDp < listTknDp.size(); idxDp++) {

                    dpStockTaken = (DpStockTaken) listTknDp.get(idxDp);
                    Date strtDate = dpStockTaken.getTakenDate();
                    Date newDate = new Date();

                    for (int idxDpTkn = 0; idxDpTkn < dpStockTaken.getTakenQty(); idxDpTkn++) {

                        long schId = SessEmpSchedule.getSchId(leaveApplication.getEmployeeId(), strtDate);

                        if (schId != 0) {

                            boolean same = false;

                            if (listEmpSchedule != null && listEmpSchedule.size() > 0) {

                                for (int i = 0; i < listEmpSchedule.size(); i++) {

                                    EmpSchedule objEmpScheduleProcess = new EmpSchedule();
                                    objEmpScheduleProcess = (EmpSchedule) listEmpSchedule.get(i);

                                    if (objEmpScheduleProcess.getOID() == schId) {
                                        same = true;
                                    }
                                }
                            }
                            if (same == false) {
                                EmpSchedule empScheduleProcess = new EmpSchedule();
                                empScheduleProcess.setOID(schId);
                                listEmpSchedule.add(empScheduleProcess);
                            }

                            int status = SessEmpSchedule.updateSchedule(strtDate, schId, oidDp);

                        }
                        long tmpDate = strtDate.getTime() + (24 * 60 * 60 * 1000);
                        newDate = new Date(tmpDate);
                        strtDate = newDate;
                    }
                }

                for (int idxSp = 0; idxSp < listTknSp.size(); idxSp++) {

                    specialUnpaidLeaveTaken = (SpecialUnpaidLeaveTaken) listTknSp.get(idxSp);
                    Date strtDate = specialUnpaidLeaveTaken.getTakenDate();
                    Date newDate = new Date();
                    String oidSp = "" + specialUnpaidLeaveTaken.getScheduledId();

                    for (int idxSpTkn = 0; idxSpTkn < specialUnpaidLeaveTaken.getTakenQty(); idxSpTkn++) {

                        long schId = SessEmpSchedule.getSchId(leaveApplication.getEmployeeId(), strtDate);

                        if (schId != 0) {

                            boolean same = false;

                            if (listEmpSchedule != null && listEmpSchedule.size() > 0) {

                                for (int i = 0; i < listEmpSchedule.size(); i++) {

                                    EmpSchedule objEmpScheduleProcess = new EmpSchedule();
                                    objEmpScheduleProcess = (EmpSchedule) listEmpSchedule.get(i);

                                    if (objEmpScheduleProcess.getOID() == schId) {
                                        same = true;
                                    }
                                }
                            }
                            if (same == false) {
                                EmpSchedule empScheduleProcess = new EmpSchedule();
                                empScheduleProcess.setOID(schId);
                                listEmpSchedule.add(empScheduleProcess);
                            }

                            int status = SessEmpSchedule.updateSchedule(strtDate, schId, oidSp);
                        }
                        long tmpDate = strtDate.getTime() + (24 * 60 * 60 * 1000);
                        newDate = new Date(tmpDate);
                        strtDate = newDate;
                    }
                }

                if (listEmpSchedule != null && listEmpSchedule.size() > 0) {

                    for (int k = 0; k < listEmpSchedule.size(); k++) {

                        EmpSchedule objAktifSchedule = new EmpSchedule();
                        objAktifSchedule = (EmpSchedule) listEmpSchedule.get(k);

                        EmpSchedule objSchedule = new EmpSchedule();

                        try {
                            objSchedule = PstEmpSchedule.fetchExc(objAktifSchedule.getOID());
                        } catch (Exception e) {
                            System.out.println("Exeption " + e.toString());
                        }

                        Period period = new Period();

                        try {
                            period = PstPeriod.fetchExc(objSchedule.getPeriodId());
                        } catch (Exception e) {
                            System.out.println("Exception " + e.toString());
                        }

                        int diffPeriod = 0;

                        diffPeriod = SessLeaveApplication.DATEDIFF(period.getEndDate(), period.getStartDate());

                        diffPeriod = diffPeriod + 1;

                        Date startDate = new Date();

                        Date newDate = new Date();

                        startDate = period.getStartDate();

                        for (int x = 0; x < diffPeriod; x++) {

                            AbsenceAnalyser.checkEmployeeAbsenceAutomatic(startDate, leaveApplication.getEmployeeId());
                            LatenessAnalyser.EmployeeLateness(startDate, leaveApplication.getEmployeeId());

                            long tmpDate = startDate.getTime() + (24 * 60 * 60 * 1000);
                            newDate = new Date(tmpDate);
                            startDate = newDate;
                        }
                    }
                }
            }
        }

    }

    
    public static int getMaxApp(long employeeId, I_Leave leaveConfig, LeaveApplication leaveApplication ){
        int max = 0;
          Employee employeeObj = new Employee();
            try {
                employeeObj = PstEmployee.fetchExc(employeeId);
            }catch (Exception e){}

            Level level = new Level();
            try{
                level = PstLevel.fetchExc(employeeObj.getLevelId());
            }catch(Exception e){}

            Level maxLevelObj = new Level();
            try{
                maxLevelObj = PstLevel.fetchExc(level.getMaxLevelApproval());
            }catch(Exception e){}

            int minLevel = level.getLevelPoint();
            int maxLevel = maxLevelObj.getLevelPoint();
            int incIndexApp = 1;            
            long oidEmpDinamis = employeeId;
            for (int xx = minLevel; xx<=maxLevel; xx++ ){
            Vector listEmpApproval = leaveConfig.getApprovalEmployeeTopLinkByLevel(oidEmpDinamis, 3, xx);
                if (listEmpApproval.size()>0){
                    if (PstLeaveApplication.getApproveOidByApprovalIndex(leaveApplication, incIndexApp) != 0){
                        oidEmpDinamis = PstLeaveApplication.getApproveOidByApprovalIndex(leaveApplication, incIndexApp);
                    }
                    max++;
                    incIndexApp++;
                }
            }
        return max;
    }
    
    //created by priska 20150513 get leaveExecute
    public static long getLeaveAlExecute(long employeeId, Date scheduleDate) {
        DBResultSet dbrs = null;
        String jam = "";
        String menit = "";
        String detik = "";

        if (scheduleDate != null) {
            jam = String.valueOf(scheduleDate.getHours());
            if (jam.length() < 2) {
                jam = "0" + jam;
            }
            menit = String.valueOf(scheduleDate.getMinutes());
            if (menit.length() < 2) {
                menit = "0" + menit;
            }
            detik = String.valueOf(scheduleDate.getSeconds());
            if (detik.length() < 2) {
                detik = "0" + detik;
            }
        }
        try {
            String sql = "SELECT COUNT(hla." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ") FROM " + TBL_LEAVE_APPLICATION + " hla ";
            sql = sql + " INNER JOIN `hr_al_stock_taken` has ON has.`LEAVE_APPLICATION_ID` = hla.`LEAVE_APPLICATION_ID` ";
            sql = sql + " WHERE `hla`.`DOC_STATUS` > 1  AND hla.`EMPLOYEE_ID` = " + employeeId + " AND (\"" + Formater.formatDate(scheduleDate, "yyyy-MM-dd " + jam + ":" + menit + ":" + detik + "") + "\" BETWEEN `has`.`TAKEN_DATE` AND has.`TAKEN_FINNISH_DATE` )";


            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            long count = 0;
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

    //created by priska 20150513 get leaveExecute
    public static long getLeaveDpExecute(long employeeId, Date scheduleDate) {
        DBResultSet dbrs = null;
        String jam = "";
        String menit = "";
        String detik = "";

        if (scheduleDate != null) {
            jam = String.valueOf(scheduleDate.getHours());
            if (jam.length() < 2) {
                jam = "0" + jam;
            }
            menit = String.valueOf(scheduleDate.getMinutes());
            if (menit.length() < 2) {
                menit = "0" + menit;
            }
            detik = String.valueOf(scheduleDate.getSeconds());
            if (detik.length() < 2) {
                detik = "0" + detik;
            }
        }
        try {
            String sql = "SELECT COUNT(hla." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ") FROM " + TBL_LEAVE_APPLICATION + " hla ";
            sql = sql + " INNER JOIN `hr_dp_stock_taken` hds ON hds.`LEAVE_APPLICATION_ID` = hla.`LEAVE_APPLICATION_ID` ";
            sql = sql + " WHERE `hla`.`DOC_STATUS` > 1  AND hla.`EMPLOYEE_ID` = " + employeeId + " AND (\"" + Formater.formatDate(scheduleDate, "yyyy-MM-dd " + jam + ":" + menit + ":" + detik + "") + "\" BETWEEN `hds`.`TAKEN_DATE` AND hds.`TAKEN_FINNISH_DATE` )";


            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            long count = 0;
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

    //created by priska 20150513 get leaveExecute
    public static long getLeaveSpecial(long employeeId, Date scheduleDate) {
        DBResultSet dbrs = null;
        String jam = "";
        String menit = "";
        String detik = "";

        if (scheduleDate != null) {
            jam = String.valueOf(scheduleDate.getHours());
            if (jam.length() < 2) {
                jam = "0" + jam;
            }
            menit = String.valueOf(scheduleDate.getMinutes());
            if (menit.length() < 2) {
                menit = "0" + menit;
            }
            detik = String.valueOf(scheduleDate.getSeconds());
            if (detik.length() < 2) {
                detik = "0" + detik;
            }
        }
        try {
            String sql = "SELECT COUNT(hla." + PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_LEAVE_APPLICATION_ID] + ") FROM " + TBL_LEAVE_APPLICATION + " hla ";
            sql = sql + " INNER JOIN `hr_special_unpaid_leave_taken` hds ON hds.`LEAVE_APPLICATION_ID` = hla.`LEAVE_APPLICATION_ID` ";
            sql = sql + " WHERE `hla`.`DOC_STATUS` > 1  AND hla.`EMPLOYEE_ID` = " + employeeId + " AND (\"" + Formater.formatDate(scheduleDate, "yyyy-MM-dd " + jam + ":" + menit + ":" + detik + "") + "\" BETWEEN `hds`.`TAKEN_DATE` AND hds.`TAKEN_FINNISH_DATE` )";


            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            long count = 0;
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

    public static void main(String args[]) {
        // test synch data        
        String whereClause = PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_LEAVE_APPLICATION_MAIN_ID] + " = 504404268442628270";
        String order = PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_LEAVE_TYPE] + ","
                + PstLeaveApplicationDetail.fieldNames[PstLeaveApplicationDetail.FLD_TAKEN_DATE];
        Vector vSynchLeaveAppDetailData = PstLeaveApplicationDetail.list(0, 0, whereClause, order);
        Vector vResult = new Vector(1, 1);
        if (vSynchLeaveAppDetailData != null && vSynchLeaveAppDetailData.size() > 0) {
//            System.out.println("vSynchLeaveAppDetailData.size() : " + vSynchLeaveAppDetailData.size());
            vResult = synchLeaveAppDetail(vSynchLeaveAppDetailData);
        }

        System.out.println("");
        System.out.println("result: ");
        if (vResult != null && vResult.size() > 0) {
            int maxResult = vResult.size();
            for (int i = 0; i < maxResult; i++) {
                LeaveApplicationDetailView objLeaveApplicationDetailView = (LeaveApplicationDetailView) vResult.get(i);
                System.out.println("leave type : " + objLeaveApplicationDetailView.getILeaveType());
                System.out.println("leave from : " + objLeaveApplicationDetailView.getDTakenDateFrom());
                System.out.println("leave to   : " + objLeaveApplicationDetailView.getDTakenDateTo());
                System.out.println("");
            }
        }
    }
    
        public static boolean cekApproval(LeaveApplication leaveApplication,int index) {
        
            boolean cekAdaTrue = false;
            
            try{
                if (index == 1){
                    if (leaveApplication.getApproval_1() != 0){
                        return true;
                    }
                }
                if (index == 2){
                    if (leaveApplication.getApproval_2() != 0){
                        return true;
                    }
                }
                if (index == 3){
                    if (leaveApplication.getApproval_3() != 0){
                        return true;
                    }
                }
                if (index == 4){
                    if (leaveApplication.getApproval_4() != 0){
                        return true;
                    }
                }
                if (index == 5){
                    if (leaveApplication.getApproval_5() != 0){
                        return true;
                    }
                }
                if (index == 6){
                    if (leaveApplication.getApproval_6() != 0){
                        return true;
                    }
                }
                
            }catch(Exception e){} 
            
            return cekAdaTrue;
    }
 
        public static String getObjEmployeeByApprovalIndex(LeaveApplication leaveApplication, int index) {
        
            Employee employee = new Employee();
            String result = "";
            try{
                if (index == 1){
                    if (leaveApplication.getApproval_1() != 0){
                        try{
                            employee = PstEmployee.fetchExc(leaveApplication.getApproval_1());
                            result = "<B>" + employee.getFullName() + "</B><input type=\"hidden\" name=\"" + FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_1] + "\" value=\"" + leaveApplication.getApproval_1() + "\">";
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 2){
                    if (leaveApplication.getApproval_2() != 0){
                        try{
                            employee = PstEmployee.fetchExc(leaveApplication.getApproval_2());
                            result = "<B>" + employee.getFullName() + "</B><input type=\"hidden\" name=\"" + FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_2] + "\" value=\"" + leaveApplication.getApproval_2() + "\">";
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 3){
                    if (leaveApplication.getApproval_3() != 0){
                        try{
                            employee = PstEmployee.fetchExc(leaveApplication.getApproval_3());
                            result = "<B>" + employee.getFullName() + "</B><input type=\"hidden\" name=\"" + FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_3] + "\" value=\"" + leaveApplication.getApproval_3() + "\">";
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 4){
                    if (leaveApplication.getApproval_4() != 0){
                        try{
                            employee = PstEmployee.fetchExc(leaveApplication.getApproval_4());
                            result = "<B>" + employee.getFullName() + "</B><input type=\"hidden\" name=\"" + FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_4] + "\" value=\"" + leaveApplication.getApproval_4() + "\">";
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 5){
                    if (leaveApplication.getApproval_5() != 0){
                        try{
                            employee = PstEmployee.fetchExc(leaveApplication.getApproval_5());
                            result = "<B>" + employee.getFullName() + "</B><input type=\"hidden\" name=\"" + FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_5] + "\" value=\"" + leaveApplication.getApproval_5() + "\">";
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 6){
                    if (leaveApplication.getApproval_6() != 0){
                        try{
                            employee = PstEmployee.fetchExc(leaveApplication.getApproval_6());
                            result = "<B>" + employee.getFullName() + "</B><input type=\"hidden\" name=\"" + FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_6] + "\" value=\"" + leaveApplication.getApproval_6() + "\">";
                        } catch (Exception e){}
                        return result;
                    }
                }
                
            }catch(Exception e){} 
            
            return result;
    }
        
        public static long getApproveOidByApprovalIndex(LeaveApplication leaveApplication, int index) {
        
            long result = 0;
            try{
                if (index == 1){
                    if (leaveApplication.getApproval_1() != 0){
                        try{
                            result = leaveApplication.getApproval_1();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 2){
                    if (leaveApplication.getApproval_2() != 0){
                        try{
                            result = leaveApplication.getApproval_2();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 3){
                    if (leaveApplication.getApproval_3() != 0){
                        try{
                            result = leaveApplication.getApproval_3();    
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 4){
                    if (leaveApplication.getApproval_4() != 0){
                        try{
                            result = leaveApplication.getApproval_4();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 5){
                    if (leaveApplication.getApproval_5() != 0){
                        try{
                            result = leaveApplication.getApproval_5();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 6){
                    if (leaveApplication.getApproval_6() != 0){
                        try{
                            result = leaveApplication.getApproval_6();
                        } catch (Exception e){}
                        return result;
                    }
                }
                
            }catch(Exception e){} 
            
            return result;
    }
        
        public static Date getApproveDateByApprovalIndex(LeaveApplication leaveApplication, int index) {
        
            Date result = null;
            try{
                if (index == 1){
                    if (leaveApplication.getApproval_1() != 0){
                        try{
                            result = leaveApplication.getApproval_1_date();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 2){
                    if (leaveApplication.getApproval_2() != 0){
                        try{
                            result = leaveApplication.getApproval_2_date();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 3){
                    if (leaveApplication.getApproval_3() != 0){
                        try{
                            result = leaveApplication.getApproval_3_date();    
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 4){
                    if (leaveApplication.getApproval_4() != 0){
                        try{
                            result = leaveApplication.getApproval_4_date();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 5){
                    if (leaveApplication.getApproval_5() != 0){
                        try{
                            result = leaveApplication.getApproval_5_date();
                        } catch (Exception e){}
                        return result;
                    }
                }
                if (index == 6){
                    if (leaveApplication.getApproval_6() != 0){
                        try{
                            result = leaveApplication.getApproval_6_date();
                        } catch (Exception e){}
                        return result;
                    }
                }
                
            }catch(Exception e){} 
            
            return result;
    }
        
            public static String getApproveFrmByApprovalIndex(LeaveApplication leaveApplication, int index) {
        
            String result = "";
            try{
                if (index == 1){
                        try{
                            result = FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_1];
                        } catch (Exception e){}
                        return result;
                    
                }
                if (index == 2){
                        try{
                            result = FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_2];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 3){
                        try{
                            result = FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_3];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 4){
                        try{
                            result = FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_4];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 5){
                        try{
                            result = FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_5];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 6){
                        try{
                            result = FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_6];
                        } catch (Exception e){}
                        return result;
                }
                
            }catch(Exception e){} 
            
            return result;
    }
        
         public static String getApproveFrmDateByApprovalIndex(LeaveApplication leaveApplication, int index) {
        
            String result = "";
            try{
                if (index == 1){
                        try{
                            result = FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_1_DATE];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 2){
                        try{
                            result = FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_2_DATE];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 3){
                        try{
                            result = FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_3_DATE];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 4){
                        try{
                            result = FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_4_DATE];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 5){
                        try{
                            result = FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_5_DATE];
                        } catch (Exception e){}
                        return result;
                }
                if (index == 6){
                        try{
                            result = FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_APPROVAL_6_DATE];
                        } catch (Exception e){}
                        return result;
                }
                
            }catch(Exception e){} 
            
            return result;
    }
}


