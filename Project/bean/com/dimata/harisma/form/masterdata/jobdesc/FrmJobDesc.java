/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata.jobdesc;

import com.dimata.harisma.entity.masterdata.jobdesc.JobDesc;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class FrmJobDesc extends FRMHandler implements I_FRMInterface, I_FRMType {

    private JobDesc entJobDesc;
    public static final String FRM_NAME_HR_POSITION_JOB = "FRM_NAME_HR_POSITION_JOB";
    public static final int FRM_FIELD_POSITION_JOB_ID = 0;
    public static final int FRM_FIELD_POSITION_ID = 1;
    public static final int FRM_FIELD_JOB_TITLE = 2;
    public static final int FRM_FIELD_JOB_CATEGORY_ID = 3;
    public static final int FRM_FIELD_JOB_DESC = 4;
    public static final int FRM_FIELD_JOB_INPUT = 5;
    public static final int FRM_FIELD_JOB_OUTPUT = 6;
    public static final int FRM_FIELD_SCHEDULE_TYPE = 7;
    public static final int FRM_FIELD_REPEAT_TYPE = 8;
    public static final int FRM_FIELD_START_DATETIME = 9;
    public static final int FRM_FIELD_END_DATETIME = 10;
    public static final int FRM_FIELD_REPEAT_EVERY = 11;
    public static final int FRM_FIELD_REPEAT_UNTIL_DATE = 12;
    public static final int FRM_FIELD_REPEAT_UNTIL_QTY = 13;
    public static final int FRM_FIELD_REPEAT_WEEK_DAYS = 14;
    public static final int FRM_FIELD_REPEAT_MONTH_DATE = 15;
    public static final int FRM_FIELD_REPEAT_MONTH_DAY = 16;
    public static final int FRM_FIELD_REMINDER_MINUTES_BEFORE = 17;
    public static final int FRM_FIELD_NOTIFICATION = 18;
    public static final int FRM_FIELD_CHECKER_POSITION_ID = 19;
    public static final int FRM_FIELD_APPROVER_POSITION_ID = 20;
    public static String[] fieldNames = {
        "FRM_FIELD_POSITION_JOB_ID",
        "FRM_FIELD_POSITION_ID",
        "FRM_FIELD_JOB_TITLE",
        "FRM_FIELD_JOB_CATEGORY_ID",
        "FRM_FIELD_JOB_DESC",
        "FRM_FIELD_JOB_INPUT",
        "FRM_FIELD_JOB_OUTPUT",
        "FRM_FIELD_SCHEDULE_TYPE",
        "FRM_FIELD_REPEAT_TYPE",
        "FRM_FIELD_START_DATETIME",
        "FRM_FIELD_END_DATETIME",
        "FRM_FIELD_REPEAT_EVERY",
        "FRM_FIELD_REPEAT_UNTIL_DATE",
        "FRM_FIELD_REPEAT_UNTIL_QTY",
        "FRM_FIELD_REPEAT_WEEK_DAYS",
        "FRM_FIELD_REPEAT_MONTH_DATE",
        "FRM_FIELD_REPEAT_MONTH_DAY",
        "FRM_FIELD_REMINDER_MINUTES_BEFORE",
        "FRM_FIELD_NOTIFICATION",
        "FRM_FIELD_CHECKER_POSITION_ID",
        "FRM_FIELD_APPROVER_POSITION_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
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

    public FrmJobDesc() {
    }

    public FrmJobDesc(JobDesc entJobDesc) {
        this.entJobDesc = entJobDesc;
    }

    public FrmJobDesc(HttpServletRequest request, JobDesc entJobDesc) {
        super(new FrmJobDesc(entJobDesc), request);
        this.entJobDesc = entJobDesc;
    }

    public String getFormName() {
        return FRM_NAME_HR_POSITION_JOB;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public JobDesc getEntityObject() {
        return entJobDesc;
    }

    public void requestEntityObject(JobDesc entJobDesc) {
        try {
            this.requestParam();
            entJobDesc.setPositionJobId(getLong(FRM_FIELD_POSITION_JOB_ID));
            entJobDesc.setPositionId(getLong(FRM_FIELD_POSITION_ID));
            entJobDesc.setJobTitle(getString(FRM_FIELD_JOB_TITLE));
            entJobDesc.setJobCategoryId(getLong(FRM_FIELD_JOB_CATEGORY_ID));
            entJobDesc.setJobDesc(getString(FRM_FIELD_JOB_DESC));
            entJobDesc.setJobInput(getString(FRM_FIELD_JOB_INPUT));
            entJobDesc.setJobOutput(getString(FRM_FIELD_JOB_OUTPUT));
            entJobDesc.setScheduleType(getInt(FRM_FIELD_SCHEDULE_TYPE));
            entJobDesc.setRepeatType(getInt(FRM_FIELD_REPEAT_TYPE));
            entJobDesc.setStartDatetime(getDate(FRM_FIELD_START_DATETIME));
            entJobDesc.setEndDatetime(getDate(FRM_FIELD_END_DATETIME));
            entJobDesc.setRepeatEvery(getInt(FRM_FIELD_REPEAT_EVERY));
            entJobDesc.setRepeatUntilDate(getDate(FRM_FIELD_REPEAT_UNTIL_DATE));
            entJobDesc.setRepeatUntilQty(getInt(FRM_FIELD_REPEAT_UNTIL_QTY));
            entJobDesc.setRepeatWeekDays(getInt(FRM_FIELD_REPEAT_WEEK_DAYS));
            entJobDesc.setRepeatMonthDate(getInt(FRM_FIELD_REPEAT_MONTH_DATE));
            entJobDesc.setRepeatMonthDay(getInt(FRM_FIELD_REPEAT_MONTH_DAY));
            entJobDesc.setReminderMinutesBefore(getInt(FRM_FIELD_REMINDER_MINUTES_BEFORE));
            entJobDesc.setNotification(getInt(FRM_FIELD_NOTIFICATION));
            entJobDesc.setCheckerPositionId(getLong(FRM_FIELD_CHECKER_POSITION_ID));
            entJobDesc.setApproverPositionId(getLong(FRM_FIELD_APPROVER_POSITION_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
