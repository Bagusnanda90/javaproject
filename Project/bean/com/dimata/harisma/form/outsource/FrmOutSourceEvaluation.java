/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.outsource;

import com.dimata.harisma.entity.outsource.OutSourceEvaluation;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class FrmOutSourceEvaluation extends FRMHandler implements I_FRMInterface, I_FRMType {

  private OutSourceEvaluation entOutSourceEvaluation;
  
  public static final String FRM_NAME_OUT_SOURCE_EVALUATION = "FRM_NAME_OUT_SOURCE_EVALUATION";
  
  public static final int FRM_FIELD_OUTSOURCE_EVAL_ID = 0;
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
  public static final int FRM_FIELD_DATE_OF_EVAL = 12;
  public static final int FRM_FIELD_PERIOD_ID = 13;
  
    public static String[] fieldNames = {
           "FRM_FIELD_OUTSOURCE_EVAL_ID",
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
    "FRM_FIELD_DATE_OF_EVAL",
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

    public FrmOutSourceEvaluation() {
    }

    public FrmOutSourceEvaluation(OutSourceEvaluation entOutSourceEvaluation) {
        this.entOutSourceEvaluation = entOutSourceEvaluation;
    }

    public FrmOutSourceEvaluation(HttpServletRequest request, OutSourceEvaluation entOutSourceEvaluation) {
        super(new FrmOutSourceEvaluation(entOutSourceEvaluation), request);
        this.entOutSourceEvaluation = entOutSourceEvaluation;
    }

    public String getFormName() {
        return FRM_NAME_OUT_SOURCE_EVALUATION;
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

    public OutSourceEvaluation getEntityObject() {
        return entOutSourceEvaluation;
    }

    public void requestEntityObject(OutSourceEvaluation entOutSourceEvaluation) {
        try {
            this.requestParam();
    entOutSourceEvaluation.setOutsourceEvalId(getLong(FRM_FIELD_OUTSOURCE_EVAL_ID));
    entOutSourceEvaluation.setTitle(getString(FRM_FIELD_TITLE));
    entOutSourceEvaluation.setCompanyId(getLong(FRM_FIELD_COMPANY_ID));
    entOutSourceEvaluation.setCreatedDate(getDate(FRM_FIELD_CREATED_DATE));
    entOutSourceEvaluation.setCreatedById(getLong(FRM_FIELD_CREATED_BY_ID));
    entOutSourceEvaluation.setApprovedDate(getDate(FRM_FIELD_APPROVED_DATE));
    entOutSourceEvaluation.setApprovedById(getLong(FRM_FIELD_APPROVED_BY_ID));
    entOutSourceEvaluation.setNote(getString(FRM_FIELD_NOTE));
    entOutSourceEvaluation.setStatusDoc(getInt(FRM_FIELD_STATUS_DOC));
    entOutSourceEvaluation.setDivisionId(getLong(FRM_FIELD_DIVISION_ID));
    entOutSourceEvaluation.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
    entOutSourceEvaluation.setSectionId(getLong(FRM_FIELD_SECTION_ID));
    entOutSourceEvaluation.setDateOfEval(getDate(FRM_FIELD_DATE_OF_EVAL));
    entOutSourceEvaluation.setPeriodId(getLong(FRM_FIELD_PERIOD_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
