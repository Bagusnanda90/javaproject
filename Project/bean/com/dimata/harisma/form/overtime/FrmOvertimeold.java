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
public class FrmOvertimeold extends FRMHandler implements /*I_Language,*/I_FRMInterface, I_FRMType{

    public static final String FRM_OVERTIME="FRM_OVERTIME";
    public static final int FRM_FIELD_OVERTIME_ID=0;
    public static final int FRM_FIELD_REQ_DATE=1;
    public static final int FRM_FIELD_OV_NUMBER=2;
    public static final int FRM_FIELD_OBJECTIVE=3;
    public static final int FRM_FIELD_STATUS_DOC=4;
    public static final int FRM_FIELD_CUSTOMER_TASK_ID=5;
    public static final int FRM_FIELD_LOGBOOK_ID=6;
    public static final int FRM_FIELD_REQ_ID=7;
    public static final int FRM_FIELD_APPROVAL_ID=8;
    public static final int FRM_FIELD_ACK_ID=9;
    public static final int FRM_FIELD_EMPLOYEE_ID=10;
    public static final int FRM_FIELD_COMPANY_ID=11;
    public static final int FRM_FIELD_DIVISION_ID=12;
    public static final int FRM_FIELD_DEPARTMENT_ID=13;
    public static final int FRM_FIELD_SECTION_ID=14;

    public static final String[] fieldNames={
        "FRM_FIELD_OVERTIME_ID", "FRM_FIELD_REQ_DATE",
        "FRM_FIELD_OV_NUMBER", "FRM_FIELD_OBJECTIVE","FRM_FIELD_STATUS_DOC",
        "FRM_FIELD_CUSTOMER_TASK_ID", "FRM_FIELD_LOGBOOK_ID",
        "FRM_FIELD_REQ_ID", "FRM_FIELD_APPROVAL_ID",
        "FRM_FIELD_ACK_ID", "FRM_FIELD_EMPLOYEE_ID","FRM_FIELD_COMPANY_ID",
        "FRM_FIELD_DIVISION_ID", "FRM_FIELD_DEPARTMENT_ID",
        "FRM_FIELD_SECTION_ID"
    };

    public static final int[] fieldTypes={
        TYPE_LONG ,
        TYPE_DATE,
        TYPE_LONG,
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
        TYPE_LONG
    };




    private Overtime overtime;

    public FrmOvertimeold(Overtime overtime){
        this.overtime=overtime;
    }

    public FrmOvertimeold(HttpServletRequest req, Overtime overtime){
        super(new FrmOvertimeold(overtime),req);
        this.overtime=overtime;
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

    public Overtime getEntityObject(){
        return overtime;
    }

   /* private double getManDay(Date start, Date finish){
        if(start.before(finish)){
            long temp=new Date(finish.getTime()-start.getTime()).getTime();
            double manDay=(double)temp/3600000;
            manDay=manDay/8;
            return manDay;
        }
        return 0;
    }*/

    public void requestEntityObject(Overtime overtime){
        try{
            this.requestParam();
            //overtime.setOvertimeId(this.getLong(FRM_FIELD_OVERTIME_ID));
            overtime.setRequestDate(this.getDate(FRM_FIELD_REQ_DATE));
            overtime.setOvertimeNum(this.getString(FRM_FIELD_OV_NUMBER));
            overtime.setObjective(this.getString(FRM_FIELD_OBJECTIVE));
            overtime.setStatusDoc(this.getInt(FRM_FIELD_STATUS_DOC));
            overtime.setCustomerTaskId(this.getLong(FRM_FIELD_CUSTOMER_TASK_ID));
            overtime.setLogbookId(this.getLong(FRM_FIELD_LOGBOOK_ID));
            overtime.setRequestId(this.getLong(FRM_FIELD_REQ_ID));
            overtime.setApprovalId(this.getLong(FRM_FIELD_APPROVAL_ID));
            overtime.setAckId(this.getLong(FRM_FIELD_ACK_ID));
            //overtime.setEmployeeId(this.getLong(FRM_FIELD_EMPLOYEE_ID));
            overtime.setCompanyId(this.getLong(FRM_FIELD_COMPANY_ID));
            overtime.setDivisionId(this.getLong(FRM_FIELD_DIVISION_ID));
            overtime.setDepartmentId(this.getLong(FRM_FIELD_DEPARTMENT_ID));
            overtime.setSectionId(this.getLong(FRM_FIELD_SECTION_ID));
            this.overtime=overtime;
        }catch(Exception e){
            overtime=new Overtime();
        }
    }

    /*public String getMessageEntryReqired(ControlLine ctrLine, int fieldIndex){
        if ((fieldTypes[fieldIndex] & FILTER_ENTRY) == ENTRY_REQUIRED)
            return ((getErrors().get(fieldIndex + "") == null)) ? "&nbsp;&nbsp;<img src=\"" + ctrLine.getLocationImg() + "/warning.png\" width=\"15\" height=\"15\">" : "&nbsp;&nbsp;<img src=\"" + ctrLine.getLocationImg() + "/critical.png\" width=\"15\" height=\"15\">"+"&nbsp;"+getErrorMsg(fieldIndex);
        return "";
    }*/

    public static void main(String[] arg){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.HOUR, 7);
        cal.set(Calendar.AM_PM, Calendar.AM);
        Calendar cal2=Calendar.getInstance();
        cal2.set(Calendar.HOUR, 15);
        cal2.set(Calendar.AM_PM, Calendar.AM);
        System.out.println(cal.getTime() +"==="+cal.get(Calendar.AM_PM)+"="+Calendar.AM);
        System.out.println(cal2.getTime() +"==="+cal2.get(Calendar.AM_PM)+"="+Calendar.AM);
        System.out.println(cal.before(cal2));
        long l1=cal.getTime().getTime();
        long l2=cal2.getTime().getTime();
        System.out.println((double)(l2-l1)/3600000);
    }
}
