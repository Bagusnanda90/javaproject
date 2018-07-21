/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.employee;

/**
 *
 * @author Acer
 */
import com.dimata.harisma.entity.employee.EmpDocumentStatus;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmEmpDocumentStatus extends FRMHandler implements I_FRMInterface, I_FRMType {

    private EmpDocumentStatus entEmpDocumentStatus;
    public static final String FRM_NAME_EMP_DOCUMENT_STATUS = "FRM_NAME_EMP_DOCUMENT_STATUS";
    public static final int FRM_FIELD_EMP_DOC_ID = 0;
    public static final int FRM_FIELD_EMPLOYEE_ID = 1;
    public static final int FRM_FIELD_STATUS = 2;

    public static String[] fieldNames = {
        "FRM_FIELD_EMP_DOC_ID",
        "FRM_FIELD_EMPLOYEE_ID",
        "FRM_FIELD_STATUS"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING
    };

    public FrmEmpDocumentStatus() {
    }

    public FrmEmpDocumentStatus(EmpDocumentStatus entEmpDocumentStatus) {
        this.entEmpDocumentStatus = entEmpDocumentStatus;
    }

    public FrmEmpDocumentStatus(HttpServletRequest request, EmpDocumentStatus entEmpDocumentStatus) {
        super(new FrmEmpDocumentStatus(entEmpDocumentStatus), request);
        this.entEmpDocumentStatus = entEmpDocumentStatus;
    }

    public String getFormName() {
        return FRM_NAME_EMP_DOCUMENT_STATUS;
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

    public EmpDocumentStatus getEntityObject() {
        return entEmpDocumentStatus;
    }

    public void requestEntityObject(EmpDocumentStatus entEmpDocumentStatus) {
        try {
            this.requestParam();
            entEmpDocumentStatus.setEmpDocId(getLong(FRM_FIELD_EMP_DOC_ID));
            entEmpDocumentStatus.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            entEmpDocumentStatus.setStatus(getString(FRM_FIELD_STATUS));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
