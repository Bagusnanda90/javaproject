/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.log;

import com.dimata.harisma.entity.log.LogSysHistory;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmLogSysHistory extends FRMHandler implements I_FRMInterface, I_FRMType {

    private LogSysHistory entLogSysHistory;
    public static final String FRM_NAME_LOG_SYS_HISTORY = "FRM_NAME_LOG_SYS_HISTORY";
    public static final int FRM_FIELD_LOG_ID = 0;
    public static final int FRM_FIELD_LOG_DOCUMENT_ID = 1;
    public static final int FRM_FIELD_LOG_USER_ID = 2;
    public static final int FRM_FIELD_LOG_LOGIN_NAME = 3;
    public static final int FRM_FIELD_LOG_DOCUMENT_NUMBER = 4;
    public static final int FRM_FIELD_LOG_DOCUMENT_TYPE = 5;
    public static final int FRM_FIELD_LOG_USER_ACTION = 6;
    public static final int FRM_FIELD_LOG_OPEN_URL = 7;
    public static final int FRM_FIELD_LOG_UPDATE_DATE = 8;
    public static final int FRM_FIELD_LOG_APPLICATION = 9;
    public static final int FRM_FIELD_LOG_DETAIL = 10;
    public static final int FRM_FIELD_LOG_STATUS = 11;
    public static final int FRM_FIELD_APPROVER_ID = 12;
    public static final int FRM_FIELD_APPROVE_DATE = 13;
    public static final int FRM_FIELD_APPROVER_NOTE = 14;
    public static final int FRM_FIELD_LOG_PREV = 15;
    public static final int FRM_FIELD_LOG_CURR = 16;
    public static final int FRM_FIELD_LOG_MODULE = 17;
    /* Update by Hendra Putu | 2016-05-17 */
    public static final int FRM_FIELD_COMPANY_ID = 18;
    public static final int FRM_FIELD_DIVISION_ID = 19;
    public static final int FRM_FIELD_DEPARTMENT_ID = 20;
    public static final int FRM_FIELD_SECTION_ID = 21;
    public static String[] fieldNames = {
        "FRM_FIELD_LOG_ID",
        "FRM_FIELD_LOG_DOCUMENT_ID",
        "FRM_FIELD_LOG_USER_ID",
        "FRM_FIELD_LOG_LOGIN_NAME",
        "FRM_FIELD_LOG_DOCUMENT_NUMBER",
        "FRM_FIELD_LOG_DOCUMENT_TYPE",
        "FRM_FIELD_LOG_USER_ACTION",
        "FRM_FIELD_LOG_OPEN_URL",
        "FRM_FIELD_LOG_UPDATE_DATE",
        "FRM_FIELD_LOG_APPLICATION",
        "FRM_FIELD_LOG_DETAIL",
        "FRM_FIELD_LOG_STATUS",
        "FRM_FIELD_APPROVER_ID",
        "FRM_FIELD_APPROVE_DATE",
        "FRM_FIELD_APPROVER_NOTE",
        "FRM_FIELD_LOG_PREV",
        "FRM_FIELD_LOG_CURR",
        "FRM_FIELD_LOG_MODULE",
        "FRM_FIELD_COMPANY_ID",
        "FRM_FIELD_DIVISION_ID",
        "FRM_FIELD_DEPARTMENT_ID",
        "FRM_FIELD_SECTION_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG
    };

    public FrmLogSysHistory() {
    }

    public FrmLogSysHistory(LogSysHistory entLogSysHistory) {
        this.entLogSysHistory = entLogSysHistory;
    }

    public FrmLogSysHistory(HttpServletRequest request, LogSysHistory entLogSysHistory) {
        super(new FrmLogSysHistory(entLogSysHistory), request);
        this.entLogSysHistory = entLogSysHistory;
    }

    public String getFormName() {
        return FRM_NAME_LOG_SYS_HISTORY;
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

    public LogSysHistory getEntityObject() {
        return entLogSysHistory;
    }

    public void requestEntityObject(LogSysHistory entLogSysHistory) {
        try {
            this.requestParam();
            entLogSysHistory.setLogDocumentId(getLong(FRM_FIELD_LOG_DOCUMENT_ID));
            entLogSysHistory.setLogUserId(getLong(FRM_FIELD_LOG_USER_ID));
            entLogSysHistory.setLogLoginName(getString(FRM_FIELD_LOG_LOGIN_NAME));
            entLogSysHistory.setLogDocumentNumber(getString(FRM_FIELD_LOG_DOCUMENT_NUMBER));
            entLogSysHistory.setLogDocumentType(getString(FRM_FIELD_LOG_DOCUMENT_TYPE));
            entLogSysHistory.setLogUserAction(getString(FRM_FIELD_LOG_USER_ACTION));
            entLogSysHistory.setLogOpenUrl(getString(FRM_FIELD_LOG_OPEN_URL));
            entLogSysHistory.setLogUpdateDate(getDate(FRM_FIELD_LOG_UPDATE_DATE));
            entLogSysHistory.setLogApplication(getString(FRM_FIELD_LOG_APPLICATION));
            entLogSysHistory.setLogDetail(getString(FRM_FIELD_LOG_DETAIL));
            entLogSysHistory.setLogStatus(getInt(FRM_FIELD_LOG_STATUS));
            entLogSysHistory.setApproverId(getLong(FRM_FIELD_APPROVER_ID));
            entLogSysHistory.setApproveDate(getDate(FRM_FIELD_APPROVE_DATE));
            entLogSysHistory.setApproverNote(getString(FRM_FIELD_APPROVER_NOTE));
            entLogSysHistory.setLogPrev(getString(FRM_FIELD_LOG_PREV));
            entLogSysHistory.setLogCurr(getString(FRM_FIELD_LOG_CURR));
            entLogSysHistory.setLogModule(getString(FRM_FIELD_LOG_MODULE));
            entLogSysHistory.setCompanyId(getLong(FRM_FIELD_COMPANY_ID));
            entLogSysHistory.setDivisionId(getLong(FRM_FIELD_DIVISION_ID));
            entLogSysHistory.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
            entLogSysHistory.setSectionId(getLong(FRM_FIELD_SECTION_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}