/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Satrya Ramayu
 */
public class FrmPaySlipGroup extends FRMHandler implements I_FRMInterface, I_FRMType{
     private PaySlipGroup paySlipGroup;

    /**
      * Keterangan : menandakan apa sih nama form ini
      */
    public static final String FRM_NAME_PAYSLIP_GROUP = "FRM_NAME_PAYSLIP_GROUP";

    public static final int FRM_FIELD_PAYSLIP_GROUP_ID = 0;
    public static final int FRM_FIELD_PAYSLIP_GROUP_NAME = 1;
    public static final int FRM_FIELD_PAYSLIP_GROUP_DESCRIPTION = 2;

    public static String[] fieldNames = {
        "FRM_FIELD_PAYSLIP_GROUP_ID", "FRM_PAYSLIP_GROUP_NAME",
        "FRM_PAYSLIP_GROUP_DESCRIPTION"
    };

    public static int[] fieldTypes = {
        TYPE_LONG, TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING
    };

    /**
     * merupakan Constracktor, fungsinya agar bisa new <nama kelas>
     */
    public FrmPaySlipGroup() {
    
    }

    public FrmPaySlipGroup(PaySlipGroup paySlipGroup) {
        this.paySlipGroup = paySlipGroup;
    }

    public FrmPaySlipGroup(HttpServletRequest request, PaySlipGroup paySlipGroup) {
        super(new FrmPaySlipGroup(paySlipGroup), request);
        this.paySlipGroup = paySlipGroup;
    }

    public String getFormName() {
        return FRM_NAME_PAYSLIP_GROUP;
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

    public PaySlipGroup getEntityObject() {
        return paySlipGroup;
    }

    public void requestEntityObject(PaySlipGroup paySlipGroup) {
        try {
            this.requestParam();
            paySlipGroup.setGroupName(getString(FRM_FIELD_PAYSLIP_GROUP_NAME));
            paySlipGroup.setGroupDesc(getString(FRM_FIELD_PAYSLIP_GROUP_DESCRIPTION));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
