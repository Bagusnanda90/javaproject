/*
 * Description : Form Deduction
 * Date : 2015-02-10
 * Author : Hendra Putu
 */
package com.dimata.harisma.form.payroll;

import com.dimata.harisma.entity.payroll.Deduction;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmDeduction extends FRMHandler implements I_FRMInterface, I_FRMType {

    private Deduction entDeduction;
    public static final String FRM_NAME_DEDUCTION = "FRM_NAME_DEDUCTION";
    public static final int FRM_FIELD_DEDUCTION_ID = 0;
    public static final int FRM_FIELD_DEDUCTION_PERSEN = 1;
    public static final int FRM_FIELD_DEDUCTION_DESCRIPTION = 2;
    public static final int FRM_FIELD_DEDUCTION_REFERENCE = 3;
    public static final int FRM_FIELD_DEDUCTION_RESULT = 4;
    public static final int FRM_FIELD_BENEFIT_MASTER_ID = 5;
    public static String[] fieldNames = {
        "FRM_FIELD_DEDUCTION_ID",
        "FRM_FIELD_DEDUCTION_PERSEN",
        "FRM_FIELD_DEDUCTION_DESCRIPTION",
        "FRM_FIELD_DEDUCTION_REFERENCE",
        "FRM_FIELD_DEDUCTION_RESULT",
        "FRM_FIELD_BENEFIT_MASTER_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_INT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_LONG
    };

    public FrmDeduction() {
    }

    public FrmDeduction(Deduction entDeduction) {
        this.entDeduction = entDeduction;
    }

    public FrmDeduction(HttpServletRequest request, Deduction entDeduction) {
        super(new FrmDeduction(entDeduction), request);
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

    public Deduction getEntityObject() {
        return entDeduction;
    }

    public void requestEntityObject(Deduction entDeduction) {
        try {
            this.requestParam();
            entDeduction.setDeductionPersen(getInt(FRM_FIELD_DEDUCTION_PERSEN));
            entDeduction.setDeductionDescription(getString(FRM_FIELD_DEDUCTION_DESCRIPTION));
            entDeduction.setDeductionReference(getInt(FRM_FIELD_DEDUCTION_REFERENCE));
            entDeduction.setDeductionResult(getDouble(FRM_FIELD_DEDUCTION_RESULT));
            entDeduction.setBenefitMasterId(getLong(FRM_FIELD_BENEFIT_MASTER_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
