/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata.jobdesc;

import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author khirayinnura
 */
public class PstJobDesc extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_HR_POSITION_JOB = "hr_position_job";
    public static final int FLD_POSITION_JOB_ID = 0;
    public static final int FLD_POSITION_ID = 1;
    public static final int FLD_JOB_TITLE = 2;
    public static final int FLD_JOB_CATEGORY_ID = 3;
    public static final int FLD_JOB_DESC = 4;
    public static final int FLD_JOB_INPUT = 5;
    public static final int FLD_JOB_OUTPUT = 6;
    public static final int FLD_SCHEDULE_TYPE = 7;
    public static final int FLD_REPEAT_TYPE = 8;
    public static final int FLD_START_DATETIME = 9;
    public static final int FLD_END_DATETIME = 10;
    public static final int FLD_REPEAT_EVERY = 11;
    public static final int FLD_REPEAT_UNTIL_DATE = 12;
    public static final int FLD_REPEAT_UNTIL_QTY = 13;
    public static final int FLD_REPEAT_WEEK_DAYS = 14;
    public static final int FLD_REPEAT_MONTH_DATE = 15;
    public static final int FLD_REPEAT_MONTH_DAY = 16;
    public static final int FLD_REMINDER_MINUTES_BEFORE = 17;
    public static final int FLD_NOTIFICATION = 18;
    public static final int FLD_CHECKER_POSITION_ID = 19;
    public static final int FLD_APPROVER_POSITION_ID = 20;
    
    public static String[] scheduleTypeKey = {"according to work schedule", "specific time", "allday job"};
    public static int[] scheduleTypeValue = {0,1,2};
    
    public static String[] repeatTypeKey = {"regular job time", "repeat daily", "repeat weekly", "repeat monthly", "repeat yearly", "one time job ( only once during works )"};
    public static int[] repeatTypeValue = {0,1,2,3,4,5};
    
    public static String[] repeatWeekDayKey = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thurday", "Friday", "Saturday"};
    public static int[] repeatWeekDayValue = {0, 1, 2, 3, 4, 5, 6};
    
    public static String[] notifKey = {"none", "notification system message", "email", "sys message & email"};
    public static int[] notifValue = {0, 1, 2, 3};
    
    public static String[] fieldNames = {
        "POSITION_JOB_ID",
        "POSITION_ID",
        "JOB_TITLE",
        "JOB_CATEGORY_ID",
        "JOB_DESC",
        "JOB_INPUT",
        "JOB_OUTPUT",
        "SCHEDULE_TYPE",
        "REPEAT_TYPE",
        "START_DATETIME",
        "END_DATETIME",
        "REPEAT_EVERY",
        "REPEAT_UNTIL_DATE",
        "REPEAT_UNTIL_QTY",
        "REPEAT_WEEK_DAYS",
        "REPEAT_MONTH_DATE",
        "REPEAT_MONTH_DAY",
        "REMINDER_MINUTES_BEFORE",
        "NOTIFICATION",
        "CHECKER_POSITION_ID",
        "APPROVER_POSITION_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_INT,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG
    };

    public PstJobDesc() {
    }

    public PstJobDesc(int i) throws DBException {
        super(new PstJobDesc());
    }

    public PstJobDesc(String sOid) throws DBException {
        super(new PstJobDesc(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstJobDesc(long lOid) throws DBException {
        super(new PstJobDesc(0));
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
        return TBL_HR_POSITION_JOB;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstJobDesc().getClass().getName();
    }

    public static JobDesc fetchExc(long oid) throws DBException {
        try {
            JobDesc entJobDesc = new JobDesc();
            PstJobDesc PstJobDesc = new PstJobDesc(oid);
            entJobDesc.setOID(oid);
            entJobDesc.setPositionId(PstJobDesc.getLong(FLD_POSITION_ID));
            entJobDesc.setJobTitle(PstJobDesc.getString(FLD_JOB_TITLE));
            entJobDesc.setJobCategoryId(PstJobDesc.getLong(FLD_JOB_CATEGORY_ID));
            entJobDesc.setJobDesc(PstJobDesc.getString(FLD_JOB_DESC));
            entJobDesc.setJobInput(PstJobDesc.getString(FLD_JOB_INPUT));
            entJobDesc.setJobOutput(PstJobDesc.getString(FLD_JOB_OUTPUT));
            entJobDesc.setScheduleType(PstJobDesc.getInt(FLD_SCHEDULE_TYPE));
            entJobDesc.setRepeatType(PstJobDesc.getInt(FLD_REPEAT_TYPE));
            entJobDesc.setStartDatetime(PstJobDesc.getDate(FLD_START_DATETIME));
            entJobDesc.setEndDatetime(PstJobDesc.getDate(FLD_END_DATETIME));
            entJobDesc.setRepeatEvery(PstJobDesc.getInt(FLD_REPEAT_EVERY));
            entJobDesc.setRepeatUntilDate(PstJobDesc.getDate(FLD_REPEAT_UNTIL_DATE));
            entJobDesc.setRepeatUntilQty(PstJobDesc.getInt(FLD_REPEAT_UNTIL_QTY));
            entJobDesc.setRepeatWeekDays(PstJobDesc.getInt(FLD_REPEAT_WEEK_DAYS));
            entJobDesc.setRepeatMonthDate(PstJobDesc.getInt(FLD_REPEAT_MONTH_DATE));
            entJobDesc.setRepeatMonthDay(PstJobDesc.getInt(FLD_REPEAT_MONTH_DAY));
            entJobDesc.setReminderMinutesBefore(PstJobDesc.getInt(FLD_REMINDER_MINUTES_BEFORE));
            entJobDesc.setNotification(PstJobDesc.getInt(FLD_NOTIFICATION));
            entJobDesc.setCheckerPositionId(PstJobDesc.getLong(FLD_CHECKER_POSITION_ID));
            entJobDesc.setApproverPositionId(PstJobDesc.getLong(FLD_APPROVER_POSITION_ID));
            return entJobDesc;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstJobDesc(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        JobDesc entJobDesc = fetchExc(entity.getOID());
        entity = (Entity) entJobDesc;
        return entJobDesc.getOID();
    }

    public static synchronized long updateExc(JobDesc entJobDesc) throws DBException {
        try {
            if (entJobDesc.getOID() != 0) {
                PstJobDesc PstJobDesc = new PstJobDesc(entJobDesc.getOID());
                PstJobDesc.setLong(FLD_POSITION_ID, entJobDesc.getPositionId());
                PstJobDesc.setString(FLD_JOB_TITLE, entJobDesc.getJobTitle());
                PstJobDesc.setLong(FLD_JOB_CATEGORY_ID, entJobDesc.getJobCategoryId());
                PstJobDesc.setString(FLD_JOB_DESC, entJobDesc.getJobDesc());
                PstJobDesc.setString(FLD_JOB_INPUT, entJobDesc.getJobInput());
                PstJobDesc.setString(FLD_JOB_OUTPUT, entJobDesc.getJobOutput());
                PstJobDesc.setInt(FLD_SCHEDULE_TYPE, entJobDesc.getScheduleType());
                PstJobDesc.setInt(FLD_REPEAT_TYPE, entJobDesc.getRepeatType());
                PstJobDesc.setDate(FLD_START_DATETIME, entJobDesc.getStartDatetime());
                PstJobDesc.setDate(FLD_END_DATETIME, entJobDesc.getEndDatetime());
                PstJobDesc.setInt(FLD_REPEAT_EVERY, entJobDesc.getRepeatEvery());
                PstJobDesc.setDate(FLD_REPEAT_UNTIL_DATE, entJobDesc.getRepeatUntilDate());
                PstJobDesc.setInt(FLD_REPEAT_UNTIL_QTY, entJobDesc.getRepeatUntilQty());
                PstJobDesc.setInt(FLD_REPEAT_WEEK_DAYS, entJobDesc.getRepeatWeekDays());
                PstJobDesc.setInt(FLD_REPEAT_MONTH_DATE, entJobDesc.getRepeatMonthDate());
                PstJobDesc.setInt(FLD_REPEAT_MONTH_DAY, entJobDesc.getRepeatMonthDay());
                PstJobDesc.setInt(FLD_REMINDER_MINUTES_BEFORE, entJobDesc.getReminderMinutesBefore());
                PstJobDesc.setInt(FLD_NOTIFICATION, entJobDesc.getNotification());
                PstJobDesc.setLong(FLD_CHECKER_POSITION_ID, entJobDesc.getCheckerPositionId());
                PstJobDesc.setLong(FLD_APPROVER_POSITION_ID, entJobDesc.getApproverPositionId());
                PstJobDesc.update();
                return entJobDesc.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstJobDesc(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((JobDesc) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstJobDesc PstJobDesc = new PstJobDesc(oid);
            PstJobDesc.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstJobDesc(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(JobDesc entJobDesc) throws DBException {
        try {
            PstJobDesc PstJobDesc = new PstJobDesc(0);
            PstJobDesc.setLong(FLD_POSITION_ID, entJobDesc.getPositionId());
            PstJobDesc.setString(FLD_JOB_TITLE, entJobDesc.getJobTitle());
            PstJobDesc.setLong(FLD_JOB_CATEGORY_ID, entJobDesc.getJobCategoryId());
            PstJobDesc.setString(FLD_JOB_DESC, entJobDesc.getJobDesc());
            PstJobDesc.setString(FLD_JOB_INPUT, entJobDesc.getJobInput());
            PstJobDesc.setString(FLD_JOB_OUTPUT, entJobDesc.getJobOutput());
            PstJobDesc.setInt(FLD_SCHEDULE_TYPE, entJobDesc.getScheduleType());
            PstJobDesc.setInt(FLD_REPEAT_TYPE, entJobDesc.getRepeatType());
            PstJobDesc.setDate(FLD_START_DATETIME, entJobDesc.getStartDatetime());
            PstJobDesc.setDate(FLD_END_DATETIME, entJobDesc.getEndDatetime());
            PstJobDesc.setInt(FLD_REPEAT_EVERY, entJobDesc.getRepeatEvery());
            PstJobDesc.setDate(FLD_REPEAT_UNTIL_DATE, entJobDesc.getRepeatUntilDate());
            PstJobDesc.setInt(FLD_REPEAT_UNTIL_QTY, entJobDesc.getRepeatUntilQty());
            PstJobDesc.setInt(FLD_REPEAT_WEEK_DAYS, entJobDesc.getRepeatWeekDays());
            PstJobDesc.setInt(FLD_REPEAT_MONTH_DATE, entJobDesc.getRepeatMonthDate());
            PstJobDesc.setInt(FLD_REPEAT_MONTH_DAY, entJobDesc.getRepeatMonthDay());
            PstJobDesc.setInt(FLD_REMINDER_MINUTES_BEFORE, entJobDesc.getReminderMinutesBefore());
            PstJobDesc.setInt(FLD_NOTIFICATION, entJobDesc.getNotification());
            PstJobDesc.setLong(FLD_CHECKER_POSITION_ID, entJobDesc.getCheckerPositionId());
            PstJobDesc.setLong(FLD_APPROVER_POSITION_ID, entJobDesc.getApproverPositionId());
            PstJobDesc.insert();
            entJobDesc.setOID(PstJobDesc.getlong(FLD_POSITION_JOB_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstJobDesc(0), DBException.UNKNOWN);
        }
        return entJobDesc.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((JobDesc) entity);
    }
    
    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_HR_POSITION_JOB;
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
                JobDesc jobDesc = new JobDesc();
                resultToObject(rs, jobDesc);
                lists.add(jobDesc);
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


    public static void resultToObject(ResultSet rs, JobDesc entJobDesc) {
        try {
            entJobDesc.setOID(rs.getLong(PstJobDesc.fieldNames[PstJobDesc.FLD_POSITION_JOB_ID]));
            entJobDesc.setPositionId(rs.getLong(PstJobDesc.fieldNames[PstJobDesc.FLD_POSITION_ID]));
            entJobDesc.setJobTitle(rs.getString(PstJobDesc.fieldNames[PstJobDesc.FLD_JOB_TITLE]));
            entJobDesc.setJobCategoryId(rs.getLong(PstJobDesc.fieldNames[PstJobDesc.FLD_JOB_CATEGORY_ID]));
            entJobDesc.setJobDesc(rs.getString(PstJobDesc.fieldNames[PstJobDesc.FLD_JOB_DESC]));
            entJobDesc.setJobInput(rs.getString(PstJobDesc.fieldNames[PstJobDesc.FLD_JOB_INPUT]));
            entJobDesc.setJobOutput(rs.getString(PstJobDesc.fieldNames[PstJobDesc.FLD_JOB_OUTPUT]));
            entJobDesc.setScheduleType(rs.getInt(PstJobDesc.fieldNames[PstJobDesc.FLD_SCHEDULE_TYPE]));
            entJobDesc.setRepeatType(rs.getInt(PstJobDesc.fieldNames[PstJobDesc.FLD_REPEAT_TYPE]));
            entJobDesc.setStartDatetime(rs.getDate(PstJobDesc.fieldNames[PstJobDesc.FLD_START_DATETIME]));
            entJobDesc.setEndDatetime(rs.getDate(PstJobDesc.fieldNames[PstJobDesc.FLD_END_DATETIME]));
            entJobDesc.setRepeatEvery(rs.getInt(PstJobDesc.fieldNames[PstJobDesc.FLD_REPEAT_EVERY]));
            entJobDesc.setRepeatUntilDate(rs.getDate(PstJobDesc.fieldNames[PstJobDesc.FLD_REPEAT_UNTIL_DATE]));
            entJobDesc.setRepeatUntilQty(rs.getInt(PstJobDesc.fieldNames[PstJobDesc.FLD_REPEAT_UNTIL_QTY]));
            entJobDesc.setRepeatWeekDays(rs.getInt(PstJobDesc.fieldNames[PstJobDesc.FLD_REPEAT_WEEK_DAYS]));
            entJobDesc.setRepeatMonthDate(rs.getInt(PstJobDesc.fieldNames[PstJobDesc.FLD_REPEAT_MONTH_DATE]));
            entJobDesc.setRepeatMonthDay(rs.getInt(PstJobDesc.fieldNames[PstJobDesc.FLD_REPEAT_MONTH_DAY]));
            entJobDesc.setReminderMinutesBefore(rs.getInt(PstJobDesc.fieldNames[PstJobDesc.FLD_REMINDER_MINUTES_BEFORE]));
            entJobDesc.setNotification(rs.getInt(PstJobDesc.fieldNames[PstJobDesc.FLD_NOTIFICATION]));
            entJobDesc.setCheckerPositionId(rs.getLong(PstJobDesc.fieldNames[PstJobDesc.FLD_CHECKER_POSITION_ID]));
            entJobDesc.setApproverPositionId(rs.getLong(PstJobDesc.fieldNames[PstJobDesc.FLD_APPROVER_POSITION_ID]));
        } catch (Exception e) {
        }
    }
    
    public static boolean checkOID(long jobDescId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_HR_POSITION_JOB + " WHERE "
                    + PstJobDesc.fieldNames[PstJobDesc.FLD_POSITION_JOB_ID] + " = " + jobDescId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
            return result;
        }
    }

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstJobDesc.fieldNames[PstJobDesc.FLD_POSITION_JOB_ID] + ") FROM " + TBL_HR_POSITION_JOB;
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
   
    /* This method used to find current data */
    public static int findLimitStart(long oid, int recordToGet, String whereClause) {
        String order = "";
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, order);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    JobCat jobCat = (JobCat) list.get(ls);
                    if (oid == jobCat.getOID()) {
                        found = true;
                    }
                }
            }
        }
        if ((start >= size) && (size > 0)) {
            start = start - recordToGet;
        }

        return start;
    }
}
