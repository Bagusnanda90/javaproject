/*
 * Description : Form BenefitConfigDeduction
 * Date : 2015-02-10
 * Author : Hendra Putu
 */
package com.dimata.harisma.form.payroll;

import com.dimata.harisma.entity.payroll.BenefitConfigDeduction;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmBenefitConfigDeduction extends FRMHandler implements I_FRMInterface, I_FRMType {

    private BenefitConfigDeduction entDeduction;
    public static final String FRM_NAME_DEDUCTION = "FRM_NAME_DEDUCTION";
    public static final int FRM_FIELD_DEDUCTION_ID = 0;
    public static final int FRM_FIELD_DEDUCTION_PERCENT = 1;
    public static final int FRM_FIELD_DEDUCTION_DESCRIPTION = 2;
    public static final int FRM_FIELD_DEDUCTION_REFERENCE = 3;
    public static final int FRM_FIELD_DEDUCTION_INDEX = 4;
    public static final int FRM_FIELD_BENEFIT_CONFIG_ID = 5;
    public static String[] fieldNames = {
        "FRM_FIELD_DEDUCTION_ID",
        "FRM_FIELD_DEDUCTION_PERCENT",
        "FRM_FIELD_DEDUCTION_DESCRIPTION",
        "FRM_FIELD_DEDUCTION_REFERENCE",
        "FRM_FIELD_DEDUCTION_INDEX",
        "FRM_FIELD_BENEFIT_CONFIG_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_INT,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_INT,
        TYPE_LONG
    };

    public FrmBenefitConfigDeduction() {
    }

    public FrmBenefitConfigDeduction(BenefitConfigDeduction entDeduction) {
        this.entDeduction = entDeduction;
    }

    public FrmBenefitConfigDeduction(HttpServletRequest request, BenefitConfigDeduction entDeduction) {
        super(new FrmBenefitConfigDeduction(entDeduction), request);
        this.entDeduction = entDeduction;
    }

    public String getFormName() {
        return FRM_NAME_DEDUCTION;
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

    public BenefitConfigDeduction getEntityObject() {
        return entDeduction;
    }

    public void requestEntityObject(BenefitConfigDeduction entDeduction) {
        try {
            this.requestParam();
            entDeduction.setDeductionPercent(getInt(FRM_FIELD_DEDUCTION_PERCENT));
            entDeduction.setDeductionDescription(getString(FRM_FIELD_DEDUCTION_DESCRIPTION));
            entDeduction.setDeductionReference(getLong(FRM_FIELD_DEDUCTION_REFERENCE));
            entDeduction.setDeductionIndex(getInt(FRM_FIELD_DEDUCTION_INDEX));
            entDeduction.setBenefitConfigId(getLong(FRM_FIELD_BENEFIT_CONFIG_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
