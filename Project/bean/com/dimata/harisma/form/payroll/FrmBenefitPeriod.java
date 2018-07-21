/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.payroll;

import com.dimata.harisma.entity.payroll.BenefitPeriod;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmBenefitPeriod extends FRMHandler implements I_FRMInterface, I_FRMType {

    private BenefitPeriod entBenefitPeriod;
    public static final String FRM_NAME_BENEFIT_PERIOD = "FRM_NAME_BENEFIT_PERIOD";
    public static final int FRM_FIELD_BENEFIT_PERIOD_ID = 0;
    public static final int FRM_FIELD_BENEFIT_CONFIG_ID = 1;
    public static final int FRM_FIELD_PERIOD_FROM = 2;
    public static final int FRM_FIELD_PERIOD_TO = 3;
    public static final int FRM_FIELD_PERIOD_ID = 4;
    public static final int FRM_FIELD_TOTAL_REVENUE = 5;
    public static final int FRM_FIELD_PART_1_VALUE = 6;
    public static final int FRM_FIELD_PART_1_TOTAL_DIVIDER = 7;
    public static final int FRM_FIELD_PART_2_VALUE = 8;
    public static final int FRM_FIELD_PART_2_TOTAL_DIVIDER = 9;
    public static final int FRM_FIELD_APPROVE_1_EMP_ID = 10;
    public static final int FRM_FIELD_APPROVE_1_DATE = 11;
    public static final int FRM_FIELD_APPROVE_2_EMP_ID = 12;
    public static final int FRM_FIELD_APPROVE_2_DATE = 13;
    public static final int FRM_FIELD_CREATE_EMP_ID = 14;
    public static final int FRM_FIELD_CREATE_EMP_DATE = 15;
    public static final int FRM_FIELD_DOC_STATUS = 16;
    public static String[] fieldNames = {
        "FRM_FIELD_BENEFIT_PERIOD_ID",
        "FRM_FIELD_BENEFIT_CONFIG_ID",
        "FRM_FIELD_PERIOD_FROM",
        "FRM_FIELD_PERIOD_TO",
        "FRM_FIELD_PERIOD_ID",
        "FRM_FIELD_TOTAL_REVENUE",
        "FRM_FIELD_PART_1_VALUE",
        "FRM_FIELD_PART_1_TOTAL_DIVIDER",
        "FRM_FIELD_PART_2_VALUE",
        "FRM_FIELD_PART_2_TOTAL_DIVIDER",
        "FRM_FIELD_APPROVE_1_EMP_ID",
        "FRM_FIELD_APPROVE_1_DATE",
        "FRM_FIELD_APPROVE_2_EMP_ID",
        "FRM_FIELD_APPROVE_2_DATE",
        "FRM_FIELD_CREATE_EMP_ID",
        "FRM_FIELD_CREATE_EMP_DATE",
        "FRM_FIELD_DOC_STATUS"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT
    };

    public FrmBenefitPeriod() {
    }

    public FrmBenefitPeriod(BenefitPeriod entBenefitPeriod) {
        this.entBenefitPeriod = entBenefitPeriod;
    }

    public FrmBenefitPeriod(HttpServletRequest request, BenefitPeriod entBenefitPeriod) {
        super(new FrmBenefitPeriod(entBenefitPeriod), request);
        this.entBenefitPeriod = entBenefitPeriod;
    }

    public String getFormName() {
        return FRM_NAME_BENEFIT_PERIOD;
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

    public BenefitPeriod getEntityObject() {
        return entBenefitPeriod;
    }

    public void requestEntityObject(BenefitPeriod entBenefitPeriod) {
        try {
            this.requestParam();
            entBenefitPeriod.setBenefitConfigId(getLong(FRM_FIELD_BENEFIT_CONFIG_ID));
            entBenefitPeriod.setPeriodFrom(getDate(FRM_FIELD_PERIOD_FROM));
            entBenefitPeriod.setPeriodTo(getDate(FRM_FIELD_PERIOD_TO));
            entBenefitPeriod.setPeriodId(getLong(FRM_FIELD_PERIOD_ID));
            entBenefitPeriod.setTotalRevenue(getDouble(FRM_FIELD_TOTAL_REVENUE));
            entBenefitPeriod.setPart1Value(getDouble(FRM_FIELD_PART_1_VALUE));
            entBenefitPeriod.setPart1TotalDivider(getInt(FRM_FIELD_PART_1_TOTAL_DIVIDER));
            entBenefitPeriod.setPart2Value(getDouble(FRM_FIELD_PART_2_VALUE));
            entBenefitPeriod.setPart2TotalDivider(getInt(FRM_FIELD_PART_2_TOTAL_DIVIDER));
            entBenefitPeriod.setApprove1EmpId(getLong(FRM_FIELD_APPROVE_1_EMP_ID));
            entBenefitPeriod.setApprove1Date(getDate(FRM_FIELD_APPROVE_1_DATE));
            entBenefitPeriod.setApprove2EmpId(getLong(FRM_FIELD_APPROVE_2_EMP_ID));
            entBenefitPeriod.setApprove2Date(getDate(FRM_FIELD_APPROVE_2_DATE));
            entBenefitPeriod.setCreateEmpId(getLong(FRM_FIELD_CREATE_EMP_ID));
            entBenefitPeriod.setCreateEmpDate(getDate(FRM_FIELD_CREATE_EMP_DATE));
            entBenefitPeriod.setDocStatus(getInt(FRM_FIELD_DOC_STATUS));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}