/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.employee;

import com.dimata.harisma.entity.employee.EmpAssetInventory;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Gunadi
 */
public class FrmEmpAssetInventory extends FRMHandler implements I_FRMInterface, I_FRMType {

    private EmpAssetInventory entEmpAssetInventory;
    public static final String FRM_NAME_EMP_ASSET_INVENTORY = "FRM_NAME_EMP_ASSET_INVENTORY";
    public static final int FRM_FIELD_EMP_ASSET_INVENTORY_ID = 0;
    public static final int FRM_FIELD_EMPLOYEE_ID = 1;
    public static final int FRM_FIELD_ASSIGNMENT_DATE = 2;
    public static final int FRM_FIELD_CHECK_BY = 3;
    public static final int FRM_FIELD_APPROVED_BY = 4;
    public static final int FRM_FIELD_RECEIVED_BY = 5;
    public static final int FRM_FIELD_APPROVED_DATE = 6;
    public static final int FRM_FIELD_RETURN_DATE = 7;
    public static final int FRM_FIELD_RECEIVED_DATE = 8;
    public static final int FRM_FIELD_DETAIL = 9;
    public static final int FRM_FIELD_STATUS = 10;
    public static final int FRM_FIELD_DOC_TYPE = 11;
    public static final int FRM_FIELD_CHECK_DATE = 12;
    public static String[] fieldNames = {
        "FRM_FIELD_EMP_ASSET_INVENTORY_ID",
        "FRM_FIELD_EMPLOYEE_ID",
        "FRM_FIELD_ASSIGNMENT_DATE",
        "FRM_FIELD_CHECK_BY",
        "FRM_FIELD_APPROVED_BY",
        "FRM_FIELD_RECEIVED_BY",
        "FRM_FIELD_APPROVED_DATE",
        "FRM_FIELD_RETURN_DATE",
        "FRM_FIELD_RECEIVED_DATE",
        "FRM_FIELD_DETAIL",
        "FRM_FIELD_STATUS",
        "FRM_FIELD_DOC_TYPE",
        "FRM_FIELD_CHECK_DATE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING
    };

    public FrmEmpAssetInventory() {
    }

    public FrmEmpAssetInventory(EmpAssetInventory entEmpAssetInventory) {
        this.entEmpAssetInventory = entEmpAssetInventory;
    }

    public FrmEmpAssetInventory(HttpServletRequest request, EmpAssetInventory entEmpAssetInventory) {
        super(new FrmEmpAssetInventory(entEmpAssetInventory), request);
        this.entEmpAssetInventory = entEmpAssetInventory;
    }

    public String getFormName() {
        return FRM_NAME_EMP_ASSET_INVENTORY;
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

    public EmpAssetInventory getEntityObject() {
        return entEmpAssetInventory;
    }

    public void requestEntityObject(EmpAssetInventory entEmpAssetInventory) {
        try {
            this.requestParam();
            entEmpAssetInventory.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            entEmpAssetInventory.setAssignmentDate(getString(FRM_FIELD_ASSIGNMENT_DATE).equals("") ? null : Date.valueOf(getString(FRM_FIELD_ASSIGNMENT_DATE)));
            entEmpAssetInventory.setCheckBy(getLong(FRM_FIELD_CHECK_BY));
            entEmpAssetInventory.setApprovedBy(getLong(FRM_FIELD_APPROVED_BY));
            entEmpAssetInventory.setReceivedBy(getLong(FRM_FIELD_RECEIVED_BY));
            entEmpAssetInventory.setApprovedDate(getString(FRM_FIELD_APPROVED_DATE).equals("") ? null : Date.valueOf(getString(FRM_FIELD_APPROVED_DATE)));
            entEmpAssetInventory.setReturnDate(getString(FRM_FIELD_RETURN_DATE).equals("") ? null : Date.valueOf(getString(FRM_FIELD_RETURN_DATE)));
            entEmpAssetInventory.setReceivedDate(getString(FRM_FIELD_RECEIVED_DATE).equals("") ? null : Date.valueOf(getString(FRM_FIELD_RECEIVED_DATE)));
            entEmpAssetInventory.setDetail(getString(FRM_FIELD_DETAIL));
            entEmpAssetInventory.setStatus(getInt(FRM_FIELD_STATUS));
            entEmpAssetInventory.setDocType(getInt(FRM_FIELD_DOC_TYPE));
            entEmpAssetInventory.setCheckDate(getString(FRM_FIELD_CHECK_DATE).equals("") ? null : Date.valueOf(getString(FRM_FIELD_CHECK_DATE)));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}