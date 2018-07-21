/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.form.search;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.overtime.*;

/**
 *
 * @author Wiweka
 */
public class FrmSrcOvertime extends FRMHandler implements I_FRMInterface, I_FRMType {
private SrcOvertime srcOvertime;
    public static final String FRM_SRC_OVERTIME = "FRM_SRC_OVERTIME";
    public static final int FRM_FIELD_OVERTIME_ID = 0;
    public static final int FRM_FIELD_REQ_DATE = 1;
    public static final int FRM_FIELD_OV_NUMBER = 2;
    public static final int FRM_FIELD_OBJECTIVE = 3;
    public static final int FRM_FIELD_STATUS_DOC = 4;
    public static final int FRM_FIELD_CUSTOMER_TASK_ID = 5;
    public static final int FRM_FIELD_LOGBOOK_ID = 6;
    public static final int FRM_FIELD_REQ_ID = 7;
    public static final int FRM_FIELD_APPROVAL_ID = 8;
    public static final int FRM_FIELD_ACK_ID = 9;
    public static final int FRM_FIELD_COMPANY_ID = 10;
    public static final int FRM_FIELD_DIVISION_ID = 11;
    public static final int FRM_FIELD_DEPARTMENT_ID = 12;
    public static final int FRM_FIELD_SECTION_ID = 13;
    public static final int FRM_FIELD_ORDER = 14;
    public static final int FRM_FIELD_REQ_DATE_TO = 15;

    public static final String[] fieldNames = {
        "FRM_FIELD_OVERTIME_ID",
        "FRM_FIELD_REQ_DATE",
        "FRM_FIELD_OV_NUMBER", "FRM_FIELD_OBJECTIVE", "FRM_FIELD_STATUS_DOC",
        "FRM_FIELD_CUSTOMER_TASK_ID", "FRM_FIELD_LOGBOOK_ID",
        "FRM_FIELD_REQ_ID", "FRM_FIELD_APPROVAL_ID",
        "FRM_FIELD_ACK_ID", "FRM_FIELD_COMPANY_ID",
        "FRM_FIELD_DIVISION_ID", "FRM_FIELD_DEPARTMENT_ID",
        "FRM_FIELD_SECTION_ID", "FRM_FIELD_ORDER", "FRM_FIELD_REQ_DATE_TO"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT, 
        TYPE_DATE
    };
    public static final int ORDER_REQ_DATE = 0;
    public static final int ORDER_NUMBER = 1;
    public static final int ORDER_DEPARTMENT = 2;
    public static final int ORDER_COMPANY = 3;
   

    public static final int[] orderValue = {0, 1, 2,3};

    public static final String[] orderKey = {"Request Date", "Doc. Number", "Department","Company"};

    public static Vector getOrderValue() {
        Vector order = new Vector();
        for (int i = 0; i < orderValue.length; i++) {
            order.add(String.valueOf(orderValue[i]));
        }
        return order;
    }

    public static Vector getOrderKey() {
        Vector order = new Vector();
        for (int i = 0; i < orderKey.length; i++) {
            order.add(orderKey[i]);
        }
        return order;
    }

   public FrmSrcOvertime() {
    }

    public FrmSrcOvertime(SrcOvertime srcOvertime) {
        this.srcOvertime = srcOvertime;
    }

    public FrmSrcOvertime(HttpServletRequest request, SrcOvertime srcOvertime) {
        super(new FrmSrcOvertime(srcOvertime), request);
        this.srcOvertime = srcOvertime;
    }
    //@Override
    public int getFieldSize() {
        return fieldNames.length;
    }

    //@Override
    public String getFormName() {
        return FRM_SRC_OVERTIME;
    }

    //@Override
    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public SrcOvertime getEntityObject() {
        return srcOvertime;
    }

    public void requestEntityObject(SrcOvertime srcOvertime) {
        try {
            this.requestParam();
            srcOvertime.setRequestDate(getDate(FRM_FIELD_REQ_DATE));
            srcOvertime.setRequestDateTo(getDate(FRM_FIELD_REQ_DATE_TO));
            srcOvertime.setOvertimeNum(getString(FRM_FIELD_OV_NUMBER));
            srcOvertime.setObjective(getString(FRM_FIELD_OBJECTIVE));
            srcOvertime.setStatusDoc(getInt(FRM_FIELD_STATUS_DOC));
            srcOvertime.setCustomerTaskId(getLong(FRM_FIELD_CUSTOMER_TASK_ID));
            srcOvertime.setLogbookId(getLong(FRM_FIELD_LOGBOOK_ID));
            srcOvertime.setRequestId(getLong(FRM_FIELD_REQ_ID));
            srcOvertime.setApprovalId(getLong(FRM_FIELD_APPROVAL_ID));
            srcOvertime.setAckId(getLong(FRM_FIELD_ACK_ID));
            srcOvertime.setCompanyId(getLong(FRM_FIELD_COMPANY_ID));
            srcOvertime.setDivisionId(getLong(FRM_FIELD_DIVISION_ID));
            srcOvertime.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
            srcOvertime.setSectionId(getLong(FRM_FIELD_SECTION_ID));
            srcOvertime.setOrderBy(getInt(FRM_FIELD_ORDER));

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

    /*public String getMessageEntryReqired(ControlLine ctrLine, int fieldIndex){
    if ((fieldTypes[fieldIndex] & FILTER_ENTRY) == ENTRY_REQUIRED)
    return ((getErrors().get(fieldIndex + "") == null)) ? "&nbsp;&nbsp;<img src=\"" + ctrLine.getLocationImg() + "/warning.png\" width=\"15\" height=\"15\">" : "&nbsp;&nbsp;<img src=\"" + ctrLine.getLocationImg() + "/critical.png\" width=\"15\" height=\"15\">"+"&nbsp;"+getErrorMsg(fieldIndex);
    return "";
    }*/
    public static void main(String[] arg) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 7);
        cal.set(Calendar.AM_PM, Calendar.AM);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.HOUR, 15);
        cal2.set(Calendar.AM_PM, Calendar.AM);
        System.out.println(cal.getTime() + "===" + cal.get(Calendar.AM_PM) + "=" + Calendar.AM);
        System.out.println(cal2.getTime() + "===" + cal2.get(Calendar.AM_PM) + "=" + Calendar.AM);
        System.out.println(cal.before(cal2));
        long l1 = cal.getTime().getTime();
        long l2 = cal2.getTime().getTime(); 
        System.out.println((double) (l2 - l1) / 3600000);
    }
}

