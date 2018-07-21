/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.overtime;
/* java package */

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;

import com.dimata.harisma.entity.overtime.*;

/**
 *
 * @author Wiweka
 */
public class FrmOvertime extends FRMHandler implements /*I_Language,*/ I_FRMInterface, I_FRMType {

    public static final String FRM_OVERTIME = "FRM_OVERTIME";
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
    public static final int FRM_FIELD_COST_DEP_ID = 14;
    public static final int FRM_FIELD_ALLOWANCE = 15;    
    public static final int FRM_FIELD_REST_TIME_HR = 16;    
    public static final int FRM_FIELD_REST_TIME_START = 17;    
    public static final int FRM_FIELD_ALLOWANCE_DO = 18;        
    public static final int FRM_FIELD_REST_TIME_START_DO = 19;    
    //update by satrya 2013-04-30
    public static final int FRM_FIELD_TIME_REQUEST_OT = 20;    
    public static final int FRM_FIELD_TIME_APPROVAL_OT = 21;    
    public static final int FRM_FIELD_TIME_ACK_OT = 22;  
    public static final int FRM_FIELD_APPROVAL_1_ID =23;
    public static final int FRM_FIELD_TIME_APPROVAL_1 =24;   
    public static final int FRM_FIELD_APPROVAL_2_ID =25;
    public static final int FRM_FIELD_TIME_APPROVAL_2 =26;   
    public static final int FRM_FIELD_APPROVAL_3_ID =27;
    public static final int FRM_FIELD_TIME_APPROVAL_3 =28;   
    public static final int FRM_FIELD_APPROVAL_4_ID =29;
    public static final int FRM_FIELD_TIME_APPROVAL_4 =30;   
    public static final int FRM_FIELD_APPROVAL_5_ID =31;
    public static final int FRM_FIELD_TIME_APPROVAL_5 =32;   
    public static final int FRM_FIELD_APPROVAL_6_ID =33;
    public static final int FRM_FIELD_TIME_APPROVAL_6 =34;   
    
    public static final String[] fieldNames = {
        "FRM_FIELD_OVERTIME_ID",
        "FRM_FIELD_REQ_DATE",
        "FRM_FIELD_OV_NUMBER", "FRM_FIELD_OBJECTIVE", "FRM_FIELD_STATUS_DOC",
        "FRM_FIELD_CUSTOMER_TASK_ID", "FRM_FIELD_LOGBOOK_ID",
        "FRM_FIELD_REQ_ID", "FRM_FIELD_APPROVAL_ID",
        "FRM_FIELD_ACK_ID", "FRM_FIELD_COMPANY_ID",
        "FRM_FIELD_DIVISION_ID", "FRM_FIELD_DEPARTMENT_ID",
        "FRM_FIELD_SECTION_ID",
        "FRM_FIELD_COST_DEP_ID",
        "FRM_FIELD_ALLOWANCE",
        "FRM_FIELD_REST_TIME_HR",
        "FRM_FIELD_REST_TIME_START",
        "FRM_FIELD_ALLOWANCE_DO",
        "FRM_FIELD_REST_TIME_START_DO",
        //UPDATE BY SATRYA 2013-04-30
        "FRM_FIELD_TIME_REQUEST_OT",    
        "FRM_FIELD_TIME_APPROVAL_OT",    
        "FRM_FIELD_TIME_ACK_OT",
        "FRM_FIELD_APPROVAL_1_ID",
        "FRM_FIELD_TIME_APPROVAL_1",
        "FRM_FIELD_APPROVAL_2_ID",
        "FRM_FIELD_TIME_APPROVAL_2",
        "FRM_FIELD_APPROVAL_3_ID",
        "FRM_FIELD_TIME_APPROVAL_3",
        "FRM_FIELD_APPROVAL_4_ID",
        "FRM_FIELD_TIME_APPROVAL_4",
        "FRM_FIELD_APPROVAL_5_ID",
        "FRM_FIELD_TIME_APPROVAL_5",
        "FRM_FIELD_APPROVAL_6_ID",
        "FRM_FIELD_TIME_APPROVAL_6"
    };
    public static final int[] fieldTypes = {
        TYPE_LONG,//0
        TYPE_DATE+ENTRY_REQUIRED,
        TYPE_STRING,
        TYPE_STRING+ENTRY_REQUIRED,
        TYPE_INT,
        TYPE_LONG, //5
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG+ENTRY_REQUIRED, //10
        TYPE_LONG+ENTRY_REQUIRED,
        TYPE_LONG+ENTRY_REQUIRED,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT, //15
        TYPE_FLOAT,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_DATE,
        TYPE_DATE,
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
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE
    };
    private Overtime overtime;

    public FrmOvertime(Overtime overtime) {
        this.overtime = overtime;
    }

    public FrmOvertime(HttpServletRequest req, Overtime overtime) {
        super(new FrmOvertime(overtime), req);
        this.overtime = overtime;
    }

    //@Override
    public int getFieldSize() {
        return fieldNames.length;
    }

    //@Override
    public String getFormName() {
        return FRM_OVERTIME;
    }

    //@Override
    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public Overtime getEntityObject() {
        return overtime;
    }

    public void requestEntityObject(Overtime overtime) {
        try {
            this.requestParam();
            overtime.setRequestDate(getDate(FRM_FIELD_REQ_DATE));
            overtime.setOvertimeNum(getString(FRM_FIELD_OV_NUMBER));
            overtime.setObjective(getString(FRM_FIELD_OBJECTIVE));
            overtime.setStatusDoc(getInt(FRM_FIELD_STATUS_DOC));
            overtime.setCustomerTaskId(getLong(FRM_FIELD_CUSTOMER_TASK_ID));
            overtime.setLogbookId(getLong(FRM_FIELD_LOGBOOK_ID));
            overtime.setRequestId(getLong(FRM_FIELD_REQ_ID));
            overtime.setApprovalId(getLong(FRM_FIELD_APPROVAL_ID));
            overtime.setAckId(getLong(FRM_FIELD_ACK_ID));
            overtime.setCompanyId(getLong(FRM_FIELD_COMPANY_ID));
            overtime.setDivisionId(getLong(FRM_FIELD_DIVISION_ID));
            overtime.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
            overtime.setSectionId(getLong(FRM_FIELD_SECTION_ID));
            overtime.setCostDepartmentId(getLong(FRM_FIELD_COST_DEP_ID));
            overtime.setAllowence(getInt(FRM_FIELD_ALLOWANCE));
            overtime.setRestTimeStart(getDate(FRM_FIELD_REST_TIME_START));
            overtime.setRestTimeHR(getFloat(FRM_FIELD_REST_TIME_HR));
            overtime.setDoRestTimeStart(getInt(FRM_FIELD_REST_TIME_START_DO));
            overtime.setDoUpdateAllowence(getInt(FRM_FIELD_ALLOWANCE_DO));
            //UPDATE BY SATRYA 2013-04-30
           if(getLong(FRM_FIELD_REQ_ID)!=0){
            overtime.setTimeReqOt(getDate(FRM_FIELD_TIME_REQUEST_OT));
           }else{
               overtime.setTimeReqOt(null);
           }
           if(getLong(FRM_FIELD_APPROVAL_ID)!=0){
            overtime.setTimeApproveOt(getDate(FRM_FIELD_TIME_APPROVAL_OT));
           }else{
               overtime.setTimeApproveOt(null);
           }
           if(getLong(FRM_FIELD_ACK_ID)!=0){
            overtime.setTimeAckOt(getDate(FRM_FIELD_TIME_ACK_OT));
           }else{
               overtime.setTimeAckOt(null);
           }
           
           
            overtime.setApproval1Id(getLong(FRM_FIELD_APPROVAL_1_ID));
            overtime.setTimeApproval1(getDate(FRM_FIELD_TIME_APPROVAL_1));
            overtime.setApproval2Id(getLong(FRM_FIELD_APPROVAL_2_ID));
            overtime.setTimeApproval2(getDate(FRM_FIELD_TIME_APPROVAL_2));
            overtime.setApproval3Id(getLong(FRM_FIELD_APPROVAL_3_ID));
            overtime.setTimeApproval3(getDate(FRM_FIELD_TIME_APPROVAL_3));
            overtime.setApproval4Id(getLong(FRM_FIELD_APPROVAL_4_ID));
            overtime.setTimeApproval4(getDate(FRM_FIELD_TIME_APPROVAL_4));
            overtime.setApproval1Id(getLong(FRM_FIELD_APPROVAL_5_ID));
            overtime.setTimeApproval1(getDate(FRM_FIELD_TIME_APPROVAL_5));
            overtime.setApproval1Id(getLong(FRM_FIELD_APPROVAL_6_ID));
            overtime.setTimeApproval1(getDate(FRM_FIELD_TIME_APPROVAL_6));
        
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
