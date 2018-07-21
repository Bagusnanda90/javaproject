/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.payroll;

import com.dimata.harisma.entity.payroll.BenefitPeriodDeduction;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmBenefitPeriodDeduction extends FRMHandler implements I_FRMInterface, I_FRMType {

    private BenefitPeriodDeduction entBenefitPeriodDeduction;
    public static final String FRM_NAME_BENEFIT_PERIOD_DEDUCTION = "FRM_NAME_BENEFIT_PERIOD_DEDUCTION";
    public static final int FRM_FIELD_BENEFIT_PERIOD_DEDUCTION_ID = 0;
    public static final int FRM_FIELD_DEDUCTION_ID = 1;
    public static final int FRM_FIELD_DEDUCTION_RESULT = 2;
    public static final int FRM_FIELD_BENEFIT_PERIOD_ID = 3;
    public static String[] fieldNames = {
        "FRM_FIELD_BENEFIT_PERIOD_DEDUCTION_ID",
        "FRM_FIELD_DEDUCTION_ID",
        "FRM_FIELD_DEDUCTION_RESULT",
        "FRM_FIELD_BENEFIT_PERIOD_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_LONG
    };

    public FrmBenefitPeriodDeduction() {
    }

    public FrmBenefitPeriodDeduction(BenefitPeriodDeduction entBenefitPeriodDeduction) {
        this.entBenefitPeriodDeduction = entBenefitPeriodDeduction;
    }

    public FrmBenefitPeriodDeduction(HttpServletRequest request, BenefitPeriodDeduction entBenefitPeriodDeduction) {
        super(new FrmBenefitPeriodDeduction(entBenefitPeriodDeduction), request);
        this.entBenefitPeriodDeduction = entBenefitPeriodDeduction;
    }

    public String getFormName() {
        return FRM_NAME_BENEFIT_PERIOD_DEDUCTION;
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

    public BenefitPeriodDeduction getEntityObject() {
        return entBenefitPeriodDeduction;
    }

    public void requestEntityObject(BenefitPeriodDeduction entBenefitPeriodDeduction) {
        try {
            this.requestParam();
            entBenefitPeriodDeduction.setDeductionId(getLong(FRM_FIELD_DEDUCTION_ID));
            entBenefitPeriodDeduction.setDeductionResult(getFloat(FRM_FIELD_DEDUCTION_RESULT));
            entBenefitPeriodDeduction.setBenefitPeriodId(getLong(FRM_FIELD_BENEFIT_PERIOD_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}