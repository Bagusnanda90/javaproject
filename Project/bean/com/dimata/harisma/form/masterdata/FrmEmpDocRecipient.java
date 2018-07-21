/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.employee.Employee;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.util.Formater;

/**
 *
 * @author Gunadi
 */
public class FrmEmpDocRecipient extends FRMHandler implements I_FRMInterface, I_FRMType{
    private EmpDocRecipient empDocRecipient;

    public static final String FRM_NAME_EMP_DOC_RECIPIENT = "FRM_NAME_EMP_DOC_RECIPIENT";

    public static final int FRM_FIELD_EMP_DOC_RECIPIENT_ID = 0;
    public static final int FRM_FIELD_EMP_DOC_ID = 1;
    public static final int FRM_FIELD_COMPANY_ID = 2;
    public static final int FRM_FIELD_DIVISION_ID = 3;
    public static final int FRM_FIELD_DEPARTMENT_ID = 4;
    public static final int FRM_FIELD_SECTION_ID = 5;
    public static final int FRM_FIELD_POSITION_ID = 6;
    public static final int FRM_FIELD_LEVEL_ID = 7;
    public static final int FRM_FIELD_ALIAS = 8;
    
    public static String[] fieldNames = {
       
        "FRM_FIELD_EMP_DOC_RECIPIENT_ID",
        "FRM_FIELD_EMP_DOC_ID",
        "FRM_FIELD_COMPANY_ID",
        "FRM_FIELD_DIVISION_ID",
        "FRM_FIELD_DEPARTMENT_ID",
        "FRM_FIELD_SECTION_ID",
        "FRM_FIELD_POSITION_ID",
        "FRM_FIELD_LEVEL_ID",
        "FRM_FIELD_ALIAS"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING
    };

    public FrmEmpDocRecipient() {
    }

    public FrmEmpDocRecipient(EmpDocRecipient empDocRecipient) {
        this.empDocRecipient = empDocRecipient;
    }

    public FrmEmpDocRecipient(HttpServletRequest request, EmpDocRecipient empDocRecipient) {
        super(new FrmEmpDocRecipient(empDocRecipient), request);
        this.empDocRecipient = empDocRecipient;
    }

    public String getFormName() {
        return FRM_NAME_EMP_DOC_RECIPIENT;
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

    public EmpDocRecipient getEntityObject() {
        return empDocRecipient;
    }

    public void requestEntityObject(EmpDocRecipient empDocRecipient) {
        try {
            this.requestParam();
            empDocRecipient.setEmpDocId(getLong(FRM_FIELD_EMP_DOC_ID));
            empDocRecipient.setCompanyId(getLong(FRM_FIELD_COMPANY_ID));
            empDocRecipient.setDivisionId(getLong(FRM_FIELD_DIVISION_ID));
            empDocRecipient.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
            empDocRecipient.setSectionId(getLong(FRM_FIELD_SECTION_ID));
            empDocRecipient.setPositionId(getLong(FRM_FIELD_POSITION_ID));
            empDocRecipient.setLevelId(getLong(FRM_FIELD_LEVEL_ID));
            empDocRecipient.setAlias(getString(FRM_FIELD_ALIAS));
             
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
