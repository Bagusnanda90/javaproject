
package com.dimata.harisma.form.search;


// import java
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

// import qdep
import com.dimata.qdep.form.*;

// import project
import com.dimata.harisma.entity.search.SrcEmpReprimand;

/**
 *
 * @author bayu
 */

public class FrmSrcEmpReprimand extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    public static final String FRM_NAME_EMP_REPRIMAND        =   "FRM_EMP_REPRIMAND";
    
    public static final int FRM_FIELD_EMP_ID                 =   0;
    public static final int FRM_FIELD_NAME                   =   1;
    public static final int FRM_FIELD_PAYROLL                =   2;
    public static final int FRM_FIELD_DIV_ID                 =   3;
    public static final int FRM_FIELD_DEPT_ID                =   4;
    public static final int FRM_FIELD_SEC_ID                 =   5;
    public static final int FRM_FIELD_POS_ID                 =   6;
    public static final int FRM_FIELD_REPRIMAND_START_DATE   =   7;
    public static final int FRM_FIELD_REPRIMAND_END_DATE     =   8;
    
    public static String[] fieldNames = {
        "FRM_EMPLOYEE_ID",
        "FRM_EMPLOYEE_NAME",
        "FRM_PAYROLL_NUMBER",
        "FRM_DIVISION_ID",
        "FRM_DEPARTMENT_ID",
        "FRM_SECTION_ID",
        "FRM_POSITION_ID",
        "FRM_REPRIMAND_START_DATE",
        "FRM_REPRIMAND_END_DATE"
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
        TYPE_DATE
    };
    
    private SrcEmpReprimand reprimand;
    

    public FrmSrcEmpReprimand() {
    }
    
    public FrmSrcEmpReprimand(SrcEmpReprimand reprimand) {
        this.reprimand = reprimand;
    }
    
    public FrmSrcEmpReprimand(HttpServletRequest request, SrcEmpReprimand reprimand) {
        super(new FrmSrcEmpReprimand(reprimand), request);
        this.reprimand = reprimand;
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
    
    
    public SrcEmpReprimand getEntityObject() {
        return reprimand;
    }
    
    public void requestEntityObject(SrcEmpReprimand reprimand) {
        try {
            this.requestParam();
            reprimand.setEmployeeId(this.getLong(FRM_FIELD_EMP_ID));
            reprimand.setName(this.getString(FRM_FIELD_NAME));
            reprimand.setPayroll(this.getString(FRM_FIELD_PAYROLL));
            reprimand.setDivisionId(this.getLong(FRM_FIELD_DIV_ID));
            reprimand.setDepartmentId(this.getLong(FRM_FIELD_DEPT_ID));
            reprimand.setSectionId(this.getLong(FRM_FIELD_SEC_ID));
            reprimand.setPositionId(this.getLong(FRM_FIELD_POS_ID));
            reprimand.setStartingReprimandDate(this.getDate(FRM_FIELD_REPRIMAND_START_DATE));
            reprimand.setEndingReprimandDate(this.getDate(FRM_FIELD_REPRIMAND_END_DATE));
        }
        catch(Exception e) {}
    }
    
}
