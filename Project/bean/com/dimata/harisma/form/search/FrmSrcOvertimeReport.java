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
 * @author Gede115
 */

public class FrmSrcOvertimeReport extends FRMHandler implements I_FRMInterface, I_FRMType {
private SrcOvertimeReport srcOvertimeReport;
    public static final String FRM_SRC_OVERTIME_REPORT = "FRM_SRC_OVERTIME_REPORT";

    public static final int FRM_FIELD_REQ_DATE = 0;
    public static final int FRM_FIELD_REQ_DATE_TO = 1;
    public static final int FRM_FIELD_STATUS_DOC = 2;
    public static final int FRM_FIELD_COMPANY_ID = 3;
    public static final int FRM_FIELD_DIVISION_ID = 4;
    public static final int FRM_FIELD_DEPARTMENT_ID = 5;
    public static final int FRM_FIELD_RELIGION_ID = 6;
    public static final int FRM_FIELD_PAYROLL = 7;
    public static final int FRM_FIELD_FULLNAME = 8;
//update by satrya 2013-08-13
    public static final int FRM_FIELD_SECTION_ID = 9;
    

    public static final String[] fieldNames = {
        "FRM_FIELD_REQ_DATE",
        "FRM_FIELD_REQ_TO",
        "FRM_FIELD_STATUS_DOC",
        "FRM_FIELD_COMPANY_ID",
        "FRM_FIELD_DIVISION_ID",
        "FRM_FIELD_DEPARTMENT_ID",
        "FRM_FIELD_RELIGION_ID",        
        "FRM_FIELD_PAYROLL",        
        "FRM_FIELD_FULLNAME",
        "FRM_FIELD_SECTION_ID"
    };
    public static final int[] fieldTypes = {

        TYPE_DATE,
        TYPE_DATE,
        TYPE_COLLECTION,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG
    };
    


   public FrmSrcOvertimeReport() {
    }

    public FrmSrcOvertimeReport(SrcOvertimeReport srcOvertimeReport) {
        this.srcOvertimeReport = srcOvertimeReport;
    }

    public FrmSrcOvertimeReport(HttpServletRequest request, SrcOvertimeReport srcOvertimeReport) {
        super(new FrmSrcOvertimeReport(srcOvertimeReport), request);
        this.srcOvertimeReport = srcOvertimeReport;
    }
    //@Override
    public int getFieldSize() {
        return fieldNames.length;
    }

    //@Override
    public String getFormName() {
        return FRM_SRC_OVERTIME_REPORT;
    }

    //@Override
    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public SrcOvertimeReport getEntityObject() {
        return srcOvertimeReport;
    }

    public void requestEntityObject(SrcOvertimeReport srcOvertimeReport) {
        try {
            this.requestParam();
            srcOvertimeReport.setRequestDate(getDate(FRM_FIELD_REQ_DATE));
            srcOvertimeReport.setRequestDateTo(getDate(FRM_FIELD_REQ_DATE_TO));
            srcOvertimeReport.setStatusDoc(getVectorInt(fieldNames[FRM_FIELD_STATUS_DOC]));
            srcOvertimeReport.setCompanyId(getLong(FRM_FIELD_COMPANY_ID));
            srcOvertimeReport.setDivisionId(getLong(FRM_FIELD_DIVISION_ID));
            srcOvertimeReport.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
            srcOvertimeReport.setReligionId(getLong(FRM_FIELD_RELIGION_ID));            
            srcOvertimeReport.setPayroll(getString(FRM_FIELD_PAYROLL));
            srcOvertimeReport.setFullname(getString(FRM_FIELD_FULLNAME));
            srcOvertimeReport.setSectionId(getLong(FRM_FIELD_SECTION_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

   
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
