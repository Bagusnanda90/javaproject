/* 
 * Form Name  	:  FrmEmpEducation.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.form.employee;

/* java package */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.employee.*;

public class FrmEmpEducation extends FRMHandler implements I_FRMInterface, I_FRMType {

    private EmpEducation empEducation;
    public static final String FRM_NAME_EMPEDUCATION = "FRM_NAME_EMPEDUCATION";
    public static final int FRM_FIELD_EMP_EDUCATION_ID = 0;
    public static final int FRM_FIELD_EDUCATION_ID = 1;
    public static final int FRM_FIELD_EMPLOYEE_ID = 2;
    public static final int FRM_FIELD_START_DATE = 3;
    public static final int FRM_FIELD_END_DATE = 4;
    public static final int FRM_FIELD_GRADUATION = 5;
    public static final int FRM_FIELD_EDUCATION_DESC = 6;
    public static final int FRM_FIELD_POINT = 7;
    public static final int FRM_FIELD_INSTITUTION_ID = 8;
    public static String[] fieldNames = {
        "FRM_FIELD_EMP_EDUCATION_ID", "FRM_FIELD_EDUCATION_ID",
        "FRM_FIELD_EMPLOYEE_ID", "FRM_FIELD_START_DATE",
        "FRM_FIELD_END_DATE", "FRM_FIELD_GRADUATION",
        "FRM_FIELD_EDUCATION_DESC","FRM_FIELD_POINT", "FRM_FIELD_INSTITUTION_ID"};
    public static int[] fieldTypes = {
        TYPE_LONG, TYPE_LONG + ENTRY_REQUIRED,
        TYPE_LONG + ENTRY_REQUIRED, TYPE_INT + ENTRY_REQUIRED,
        TYPE_INT + ENTRY_REQUIRED, TYPE_STRING, TYPE_STRING, TYPE_FLOAT, TYPE_LONG
    };

    public FrmEmpEducation() {
    }

    public FrmEmpEducation(EmpEducation empEducation) {
        this.empEducation = empEducation;
    }

    public FrmEmpEducation(HttpServletRequest request, EmpEducation empEducation) {
        super(new FrmEmpEducation(empEducation), request);
        this.empEducation = empEducation;
    }

    public String getFormName() {
        return FRM_NAME_EMPEDUCATION;
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

    public EmpEducation getEntityObject() {
        return empEducation;
    }

    public void requestEntityObject(EmpEducation empEducation) {
        try {
            this.requestParam();
            empEducation.setEducationId(getLong(FRM_FIELD_EDUCATION_ID));
            empEducation.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            empEducation.setStartDate(getInt(FRM_FIELD_START_DATE));
            empEducation.setEndDate(getInt(FRM_FIELD_END_DATE));
            empEducation.setGraduation(getString(FRM_FIELD_GRADUATION));
            empEducation.setEducationDesc(getString(FRM_FIELD_EDUCATION_DESC));
            empEducation.setPoint(getFloat(FRM_FIELD_POINT));
            empEducation.setInstitutionId(getLong(FRM_FIELD_INSTITUTION_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
