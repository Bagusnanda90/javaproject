
package com.dimata.harisma.form.search;


// import java
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

// import qdep
import com.dimata.qdep.form.*;

// import project
import com.dimata.harisma.entity.search.SrcEmpWarning;

/**
 *
 * @author bayu
 */

public class FrmSrcEmpWarning extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    public static final String FRM_NAME_EMP_WARNING     =   "FRM_EMP_WARNING";
    
    public static final int FRM_FIELD_EMP_ID            =   0;
    public static final int FRM_FIELD_NAME              =   1;
    public static final int FRM_FIELD_PAYROLL           =   2;
    public static final int FRM_FIELD_DIV_ID            =   3;
    public static final int FRM_FIELD_DEPT_ID           =   4;
    public static final int FRM_FIELD_SEC_ID            =   5;
    public static final int FRM_FIELD_POS_ID            =   6;
    public static final int FRM_FIELD_BREAK_START_DATE  =   7;
    public static final int FRM_FIELD_BREAK_END_DATE    =   8;
    public static final int FRM_FIELD_WARN_START_DATE   =   9;
    public static final int FRM_FIELD_WARN_END_DATE     =   10;
    
    public static String[] fieldNames = {
        "FRM_EMPLOYEE_ID",
        "FRM_EMPLOYEE_NAME",
        "FRM_PAYROLL_NUMBER",
        "FRM_DIVISION_ID",
        "FRM_DEPARTMENT_ID",
        "FRM_SECTION_ID",
        "FRM_POSITION_ID",
        "FRM_BREAK_START_DATE",
        "FRM_BREAK_END_DATE",
        "FRM_WARN_START_DATE",
        "FRM_WARN_END_DATE"
    };
    
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE
    };
    
    private SrcEmpWarning warning;
    

    public FrmSrcEmpWarning() {
    }
    
    public FrmSrcEmpWarning(SrcEmpWarning warning) {
        this.warning = warning;
    }
    
    public FrmSrcEmpWarning(HttpServletRequest request, SrcEmpWarning warning) {
        super(new FrmSrcEmpWarning(warning), request);
        this.warning = warning;
    }
    

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getFormName() {
        return getClass().getName();
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    
    public SrcEmpWarning getEntityObject() {
        return warning;
    }
    
    public void requestEntityObject(SrcEmpWarning warning) {
        try {
            this.requestParam();
            warning.setEmployeeId(this.getLong(FRM_FIELD_EMP_ID));
            warning.setName(this.getString(FRM_FIELD_NAME));
            warning.setPayroll(this.getString(FRM_FIELD_PAYROLL));
            warning.setDivisionId(this.getLong(FRM_FIELD_DIV_ID));
            warning.setDepartmentId(this.getLong(FRM_FIELD_DEPT_ID));
            warning.setSectionId(this.getLong(FRM_FIELD_SEC_ID));
            warning.setPositionId(this.getLong(FRM_FIELD_POS_ID));
            warning.setStartingFactDate(this.getDate(FRM_FIELD_BREAK_START_DATE));
            warning.setEndingFactDate(this.getDate(FRM_FIELD_BREAK_END_DATE));
            warning.setStartingWarnDate(this.getDate(FRM_FIELD_WARN_START_DATE));
            warning.setEndingWarnDate(this.getDate(FRM_FIELD_WARN_END_DATE));
        }
        catch(Exception e) {}
    }
    
}
