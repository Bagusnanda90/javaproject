/*
 * Description : Form Benefit Master
 * Date : 2015-02-10
 * Author : Hendra McHen
 */
package com.dimata.harisma.form.payroll;

import com.dimata.harisma.entity.payroll.BenefitMaster;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmBenefitMaster extends FRMHandler implements I_FRMInterface, I_FRMType {

    private BenefitMaster entBenefitMaster;
    public static final String FRM_NAME_BENEFIT_MASTER = "FRM_NAME_BENEFIT_MASTER";
    public static final int FRM_FIELD_BENEFIT_MASTER_ID = 0;
    public static final int FRM_FIELD_PERIOD_FROM = 1;
    public static final int FRM_FIELD_PERIOD_TO = 2;
    public static final int FRM_FIELD_CODE = 3;
    public static final int FRM_FIELD_TITLE = 4;
    public static final int FRM_FIELD_DESCRIPTION = 5;
    public static final int FRM_FIELD_COMPANY_STRUCTURE = 6;
    public static final int FRM_FIELD_PRORATE_EMPLOYEE_PRESENCE = 7;
    public static final int FRM_FIELD_EMPLOYEE_LEVEL_POINT = 8;
    public static final int FRM_FIELD_DISTRIBUTION_PART_1 = 9;
    public static final int FRM_FIELD_DISTRIBUTION_CODE_1 = 10;
    public static final int FRM_FIELD_DISTRIBUTION_DESCRIPTION_1 = 11;
    public static final int FRM_FIELD_DISTRIBUTION_TOTAL_1 = 12;
    public static final int FRM_FIELD_DISTRIBUTION_PART_2 = 13;
    public static final int FRM_FIELD_DISTRIBUTION_CODE_2 = 14;
    public static final int FRM_FIELD_DISTRIBUTION_DESCRIPTION_2 = 15;
    public static final int FRM_FIELD_DISTRIBUTION_TOTAL_2 = 16;
    public static final int FRM_FIELD_DISTRIBUTION_PART_3 = 17;
    public static final int FRM_FIELD_DISTRIBUTION_CODE_3 = 18;
    public static final int FRM_FIELD_DISTRIBUTION_DESCRIPTION_3 = 19;
    public static final int FRM_FIELD_DISTRIBUTION_TOTAL_3 = 20;
    public static final int FRM_FIELD_EXCEPTION_NO_BY_CATEGORY = 21;
    public static final int FRM_FIELD_EXCEPTION_NO_BY_POSITION = 22;
    public static final int FRM_FIELD_EXCEPTION_NO_BY_PAYROLL = 23;
    public static final int FRM_FIELD_EXCEPTION_NO_BY_SPECIAL_LEAVE = 24;
    public static final int FRM_FIELD_EMPLOYEE_BY_ENTITLE = 25;
    public static String[] fieldNames = {
        "FRM_FIELD_BENEFIT_MASTER_ID",
        "FRM_FIELD_PERIOD_FROM",
        "FRM_FIELD_PERIOD_TO",
        "FRM_FIELD_CODE",
        "FRM_FIELD_TITLE",
        "FRM_FIELD_DESCRIPTION",
        "FRM_FIELD_COMPANY_STRUCTURE",
        "FRM_FIELD_PRORATE_EMPLOYEE_PRESENCE",
        "FRM_FIELD_EMPLOYEE_LEVEL_POINT",
        "FRM_FIELD_DISTRIBUTION_PART_1",
        "FRM_FIELD_DISTRIBUTION_CODE_1",
        "FRM_FIELD_DISTRIBUTION_DESCRIPTION_1",
        "FRM_FIELD_DISTRIBUTION_TOTAL_1",
        "FRM_FIELD_DISTRIBUTION_PART_2",
        "FRM_FIELD_DISTRIBUTION_CODE_2",
        "FRM_FIELD_DISTRIBUTION_DESCRIPTION_2",
        "FRM_FIELD_DISTRIBUTION_TOTAL_2",
        "FRM_FIELD_DISTRIBUTION_PART_3",
        "FRM_FIELD_DISTRIBUTION_CODE_3",
        "FRM_FIELD_DISTRIBUTION_DESCRIPTION_3",
        "FRM_FIELD_DISTRIBUTION_TOTAL_3",
        "FRM_FIELD_EXCEPTION_NO_BY_CATEGORY",
        "FRM_FIELD_EXCEPTION_NO_BY_POSITION",
        "FRM_FIELD_EXCEPTION_NO_BY_PAYROLL",
        "FRM_FIELD_EXCEPTION_NO_BY_SPECIAL_LEAVE",
        "FRM_FIELD_EMPLOYEE_BY_ENTITLE"
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
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING
    };

    public FrmBenefitMaster() {
    }

    public FrmBenefitMaster(BenefitMaster entBenefitMaster) {
        this.entBenefitMaster = entBenefitMaster;
    }

    public FrmBenefitMaster(HttpServletRequest request, BenefitMaster entBenefitMaster) {
        super(new FrmBenefitMaster(entBenefitMaster), request);
        this.entBenefitMaster = entBenefitMaster;
    }

    public String getFormName() {
        return FRM_NAME_BENEFIT_MASTER;
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

    public BenefitMaster getEntityObject() {
        return entBenefitMaster;
    }

    public void requestEntityObject(BenefitMaster entBenefitMaster) {
        try {
            this.requestParam();
            entBenefitMaster.setPeriodFrom(getDate(FRM_FIELD_PERIOD_FROM));
            entBenefitMaster.setPeriodTo(getDate(FRM_FIELD_PERIOD_TO));
            entBenefitMaster.setCode(getString(FRM_FIELD_CODE));
            entBenefitMaster.setTitle(getString(FRM_FIELD_TITLE));
            entBenefitMaster.setDescription(getString(FRM_FIELD_DESCRIPTION));
            entBenefitMaster.setCompanyStructure(getString(FRM_FIELD_COMPANY_STRUCTURE));
            entBenefitMaster.setProrateEmployeePresence(getInt(FRM_FIELD_PRORATE_EMPLOYEE_PRESENCE));
            entBenefitMaster.setEmployeeLevelPoint(getInt(FRM_FIELD_EMPLOYEE_LEVEL_POINT));
            entBenefitMaster.setDistributionPart1(getString(FRM_FIELD_DISTRIBUTION_PART_1));
            entBenefitMaster.setDistributionCode1(getString(FRM_FIELD_DISTRIBUTION_CODE_1));
            entBenefitMaster.setDistributionDescription1(getString(FRM_FIELD_DISTRIBUTION_DESCRIPTION_1));
            entBenefitMaster.setDistributionTotal1(getInt(FRM_FIELD_DISTRIBUTION_TOTAL_1));
            entBenefitMaster.setDistributionPart2(getString(FRM_FIELD_DISTRIBUTION_PART_2));
            entBenefitMaster.setDistributionCode2(getString(FRM_FIELD_DISTRIBUTION_CODE_2));
            entBenefitMaster.setDistributionDescription2(getString(FRM_FIELD_DISTRIBUTION_DESCRIPTION_2));
            entBenefitMaster.setDistributionTotal2(getInt(FRM_FIELD_DISTRIBUTION_TOTAL_2));
            entBenefitMaster.setDistributionPart3(getString(FRM_FIELD_DISTRIBUTION_PART_3));
            entBenefitMaster.setDistributionCode3(getString(FRM_FIELD_DISTRIBUTION_CODE_3));
            entBenefitMaster.setDistributionDescription3(getString(FRM_FIELD_DISTRIBUTION_DESCRIPTION_3));
            entBenefitMaster.setDistributionTotal3(getInt(FRM_FIELD_DISTRIBUTION_TOTAL_3));
            entBenefitMaster.setExceptionNoByCategory(getString(FRM_FIELD_EXCEPTION_NO_BY_CATEGORY));
            entBenefitMaster.setExceptionNoByPosition(getString(FRM_FIELD_EXCEPTION_NO_BY_POSITION));
            entBenefitMaster.setExceptionNoByPayroll(getString(FRM_FIELD_EXCEPTION_NO_BY_PAYROLL));
            entBenefitMaster.setExceptionNoBySpecialLeave(getString(FRM_FIELD_EXCEPTION_NO_BY_SPECIAL_LEAVE));
            entBenefitMaster.setEmployeeByEntitle(getString(FRM_FIELD_EMPLOYEE_BY_ENTITLE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}