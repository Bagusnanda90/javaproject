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
public class FrmEmpDocGeneral extends FRMHandler implements I_FRMInterface, I_FRMType{
    private EmpDocGeneral empDocGeneral;

    public static final String FRM_NAME_EMP_DOC_GENERAL = "FRM_NAME_EMP_DOC_GENERAL";

    public static final int FRM_FIELD_EMP_DOC_GENERAL_ID = 0;
    public static final int FRM_FIELD_EMP_DOC_ID = 1;
    public static final int FRM_FIELD_EMPLOYEE_ID = 2;
    public static final int FRM_FIELD_ACKNOWLEDGE_STATUS = 3;
    
    
    public static String[] fieldNames = {
       
        "FRM_FIELD_EMP_DOC_GENERAL_ID",
        "FRM_FIELD_EMP_DOC_ID",
        "FRM_FIELD_EMPLOYEE_ID",
        "FRM_FIELD_ACKNOWLEDGE_STATUS"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, 
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT
    };

    public FrmEmpDocGeneral() {
    }

    public FrmEmpDocGeneral(EmpDocGeneral empDocGeneral) {
        this.empDocGeneral = empDocGeneral;
    }

    public FrmEmpDocGeneral(HttpServletRequest request, EmpDocGeneral empDocGeneral) {
        super(new FrmEmpDocGeneral(empDocGeneral), request);
        this.empDocGeneral = empDocGeneral;
    }

    public String getFormName() {
        return FRM_NAME_EMP_DOC_GENERAL;
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

    public EmpDocGeneral getEntityObject() {
        return empDocGeneral;
    }

    public void requestEntityObject(EmpDocGeneral empDocGeneral) {
        try {
            this.requestParam();
            empDocGeneral.setEmpDocId(getLong(FRM_FIELD_EMP_DOC_ID));
            empDocGeneral.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            empDocGeneral.setAcknowledgeStatus(getInt(FRM_FIELD_ACKNOWLEDGE_STATUS));
             
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
