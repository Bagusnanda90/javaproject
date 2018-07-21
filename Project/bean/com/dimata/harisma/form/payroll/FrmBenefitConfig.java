/**
 * Description : Benefit Config Form
 * Date : Feb, 21 2015
 * @author Hendra Putu
 */
package com.dimata.harisma.form.payroll;

import com.dimata.harisma.entity.payroll.BenefitConfig;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmBenefitConfig extends FRMHandler implements I_FRMInterface, I_FRMType {

    private BenefitConfig entBenefitConfig;
    public static final String FRM_NAME_BENEFIT_CONFIG = "FRM_NAME_BENEFIT_CONFIG";
    public static final int FRM_FIELD_BENEFIT_CONFIG_ID = 0;
    public static final int FRM_FIELD_PERIOD_FROM = 1;
    public static final int FRM_FIELD_PERIOD_TO = 2;
    public static final int FRM_FIELD_CODE = 3;
    public static final int FRM_FIELD_TITLE = 4;
    public static final int FRM_FIELD_DESCRIPTION = 5;
    public static final int FRM_FIELD_DISTRIBUTION_PART_1 = 6;
    public static final int FRM_FIELD_DISTRIBUTION_PERCENT_1 = 7;
    public static final int FRM_FIELD_DISTRIBUTION_DESCRIPTION_1 = 8;
    public static final int FRM_FIELD_DISTRIBUTION_TOTAL_1 = 9;
    public static final int FRM_FIELD_DISTRIBUTION_PART_2 = 10;
    public static final int FRM_FIELD_DISTRIBUTION_PERCENT_2 = 11;
    public static final int FRM_FIELD_DISTRIBUTION_DESCRIPTION_2 = 12;
    public static final int FRM_FIELD_DISTRIBUTION_TOTAL_2 = 13;
    public static final int FRM_FIELD_EXCEPTION_NO_BY_CATEGORY = 14;
    public static final int FRM_FIELD_EXCEPTION_NO_BY_POSITION = 15;
    public static final int FRM_FIELD_EXCEPTION_NO_BY_PAYROLL = 16;
    public static final int FRM_FIELD_EXCEPTION_NO_BY_DIVISION = 17;
    public static final int FRM_FIELD_APPROVE_1_EMP_ID = 18;
    public static final int FRM_FIELD_APPROVE_2_EMP_ID = 19;
    public static String[] fieldNames = {
        "FRM_FIELD_BENEFIT_CONFIG_ID",
        "FRM_FIELD_PERIOD_FROM",
        "FRM_FIELD_PERIOD_TO",
        "FRM_FIELD_CODE",
        "FRM_FIELD_TITLE",
        "FRM_FIELD_DESCRIPTION",
        "FRM_FIELD_DISTRIBUTION_PART_1",
        "FRM_FIELD_DISTRIBUTION_PERCENT_1",
        "FRM_FIELD_DISTRIBUTION_DESCRIPTION_1",
        "FRM_FIELD_DISTRIBUTION_TOTAL_1",
        "FRM_FIELD_DISTRIBUTION_PART_2",
        "FRM_FIELD_DISTRIBUTION_PERCENT_2",
        "FRM_FIELD_DISTRIBUTION_DESCRIPTION_2",
        "FRM_FIELD_DISTRIBUTION_TOTAL_2",
        "FRM_FIELD_EXCEPTION_NO_BY_CATEGORY",
        "FRM_FIELD_EXCEPTION_NO_BY_POSITION",
        "FRM_FIELD_EXCEPTION_NO_BY_PAYROLL",
        "FRM_FIELD_EXCEPTION_NO_BY_DIVISION",
        "FRM_FIELD_APPROVE_1_EMP_ID",
        "FRM_FIELD_APPROVE_2_EMP_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG
    };

    private String[] empCategory;
    
    public FrmBenefitConfig() {
    }

    public FrmBenefitConfig(BenefitConfig entBenefitConfig) {
        this.entBenefitConfig = entBenefitConfig;
    }

    public FrmBenefitConfig(HttpServletRequest request, BenefitConfig entBenefitConfig) {
        super(new FrmBenefitConfig(entBenefitConfig), request);
        this.entBenefitConfig = entBenefitConfig;
    }

    public String getFormName() {
        return FRM_NAME_BENEFIT_CONFIG;
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

    public BenefitConfig getEntityObject() {
        return entBenefitConfig;
    }

    public void requestEntityObject(BenefitConfig entBenefitConfig) {
        try {
            this.requestParam();
            entBenefitConfig.setPeriodFrom(getDate(FRM_FIELD_PERIOD_FROM));
            entBenefitConfig.setPeriodTo(getDate(FRM_FIELD_PERIOD_TO));
            entBenefitConfig.setCode(getString(FRM_FIELD_CODE));
            entBenefitConfig.setTitle(getString(FRM_FIELD_TITLE));
            entBenefitConfig.setDescription(getString(FRM_FIELD_DESCRIPTION));
            entBenefitConfig.setDistributionPart1(getString(FRM_FIELD_DISTRIBUTION_PART_1));
            entBenefitConfig.setDistributionPercent1(getInt(FRM_FIELD_DISTRIBUTION_PERCENT_1));
            entBenefitConfig.setDistributionDescription1(getString(FRM_FIELD_DISTRIBUTION_DESCRIPTION_1));
            entBenefitConfig.setDistributionTotal1(getInt(FRM_FIELD_DISTRIBUTION_TOTAL_1));
            entBenefitConfig.setDistributionPart2(getString(FRM_FIELD_DISTRIBUTION_PART_2));
            entBenefitConfig.setDistributionPercent2(getInt(FRM_FIELD_DISTRIBUTION_PERCENT_2));
            entBenefitConfig.setDistributionDescription2(getString(FRM_FIELD_DISTRIBUTION_DESCRIPTION_2));
            entBenefitConfig.setDistributionTotal2(getInt(FRM_FIELD_DISTRIBUTION_TOTAL_2));
            entBenefitConfig.setExceptionNoByCategory(getEmployeeCategory());
            entBenefitConfig.setExceptionNoByPosition(getString(FRM_FIELD_EXCEPTION_NO_BY_POSITION));
            entBenefitConfig.setExceptionNoByPayroll(getString(FRM_FIELD_EXCEPTION_NO_BY_PAYROLL));
            entBenefitConfig.setExceptionNoByDivision(getString(FRM_FIELD_EXCEPTION_NO_BY_DIVISION));
            entBenefitConfig.setApprove1EmpId(getLong(FRM_FIELD_APPROVE_1_EMP_ID));
            entBenefitConfig.setApprove2EmpId(getLong(FRM_FIELD_APPROVE_2_EMP_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
    /**
     * @return the empCategory
     */
    public String[] getEmpCategory() {
        return empCategory;
    }

    /**
     * @param empCategory the empCategory to set
     */
    public void setEmpCategory(String[] empCategory) {
        this.empCategory = empCategory;
    }
    
    public String getEmployeeCategory() {
        String[] empCategoryCheck = getEmpCategory();
        String checkValues = "";
        if (empCategoryCheck != null) {
            for (int i = 0; i < empCategoryCheck.length; ++i) {
                if (i != empCategoryCheck.length - 1) {
                    checkValues = checkValues + empCategoryCheck[i] + ",";
                } else {
                    checkValues = checkValues + empCategoryCheck[i];
                }
            }
        } else {
            checkValues = "";
        }
        return checkValues;
    }
}
