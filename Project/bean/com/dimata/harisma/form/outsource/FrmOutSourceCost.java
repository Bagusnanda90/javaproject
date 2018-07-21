/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.outsource;

import com.dimata.harisma.entity.outsource.OutSourceCost;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class FrmOutSourceCost extends FRMHandler implements I_FRMInterface, I_FRMType {

    private OutSourceCost entOutSourceEvaluation;
    public static final String FRM_NAME_OUTSOURCE_COST = "FRM_NAME_OUTSOURCE_COST";
    public static final int FRM_FIELD_OUTSOURCE_COST_ID = 0;
    public static final int FRM_FIELD_TITLE = 1;
    public static final int FRM_FIELD_COMPANY_ID = 2;
    public static final int FRM_FIELD_CREATED_DATE = 3;
    public static final int FRM_FIELD_CREATED_BY_ID = 4;
    public static final int FRM_FIELD_APPROVED_DATE = 5;
    public static final int FRM_FIELD_APPROVED_BY_ID = 6;
    public static final int FRM_FIELD_NOTE = 7;
    public static final int FRM_FIELD_STATUS_DOC = 8;
    public static final int FRM_FIELD_DIVISION_ID = 9;
    public static final int FRM_FIELD_DEPARTMENT_ID = 10;
    public static final int FRM_FIELD_SECTION_ID = 11;
    public static final int FRM_FIELD_DATE_OF_PAY = 12;
    public static final int FRM_FIELD_PERIOD_ID = 13;
    public static String[] fieldNames = {
        "FRM_FIELD_OUTSOURCE_COST_ID",
        "FRM_FIELD_TITLE",
        "FRM_FIELD_COMPANY_ID",
        "FRM_FIELD_CREATED_DATE",
        "FRM_FIELD_CREATED_BY_ID",
        "FRM_FIELD_APPROVED_DATE",
        "FRM_FIELD_APPROVED_BY_ID",
        "FRM_FIELD_NOTE",
        "FRM_FIELD_STATUS_DOC",
        "FRM_FIELD_DIVISION_ID",
        "FRM_FIELD_DEPARTMENT_ID",
        "FRM_FIELD_SECTION_ID",
        "FRM_FIELD_DATE_OF_PAY",
        "FRM_FIELD_PERIOD_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG
    };

    public FrmOutSourceCost() {
    }

    public FrmOutSourceCost(OutSourceCost entOutSourceEvaluation) {
        this.entOutSourceEvaluation = entOutSourceEvaluation;
    }

    public FrmOutSourceCost(HttpServletRequest request, OutSourceCost entOutSourceEvaluation) {
        super(new FrmOutSourceCost(entOutSourceEvaluation), request);
        this.entOutSourceEvaluation = entOutSourceEvaluation;
    }

    public String getFormName() {
        return FRM_NAME_OUTSOURCE_COST;
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

    public OutSourceCost getEntityObject() {
        return entOutSourceEvaluation;
    }

    public void requestEntityObject(OutSourceCost entOutSourceCost) {
        try {
            this.requestParam();
            entOutSourceCost.setTitle(getString(FRM_FIELD_TITLE));
            entOutSourceCost.setCompanyId(getLong(FRM_FIELD_COMPANY_ID));
            entOutSourceCost.setCreatedDate(getDate(FRM_FIELD_CREATED_DATE));
            entOutSourceCost.setCreatedById(getLong(FRM_FIELD_CREATED_BY_ID));
            entOutSourceCost.setApprovedDate(getDate(FRM_FIELD_APPROVED_DATE));
            entOutSourceCost.setApprovedById(getLong(FRM_FIELD_APPROVED_BY_ID));
            entOutSourceCost.setNote(getString(FRM_FIELD_NOTE));
            entOutSourceCost.setStatusDoc(getInt(FRM_FIELD_STATUS_DOC));
            entOutSourceCost.setDivisionId(getLong(FRM_FIELD_DIVISION_ID));
            entOutSourceCost.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
            entOutSourceCost.setSectionId(getLong(FRM_FIELD_SECTION_ID));
            entOutSourceCost.setDateOfPay(getDate(FRM_FIELD_DATE_OF_PAY));
            entOutSourceCost.setPeriodId(getLong(FRM_FIELD_PERIOD_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
