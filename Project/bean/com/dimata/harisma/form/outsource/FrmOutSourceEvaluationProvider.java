/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.outsource;

import com.dimata.harisma.entity.outsource.OutSourceEvaluationProvider;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class FrmOutSourceEvaluationProvider extends FRMHandler implements I_FRMInterface, I_FRMType {

  private OutSourceEvaluationProvider entOutSourceEvalProvider;
  public static final String FRM_NAME_OUT_SOURCE_EVAL_PROVIDER = "FRM_NAME_OUT_SOURCE_EVAL_PROVIDER";
  public static final int FRM_FIELD_OUT_SOURCE_EVAL_PROVIDER_ID = 0;
  public static final int FRM_FIELD_OUT_SOURCE_EVAL_ID = 1;
  public static final int FRM_FIELD_POSITION_ID = 2;
  public static final int FRM_FIELD_PROVIDER_ID = 3;
  public static final int FRM_FIELD_NUMBER_OF_PERSON = 4;
  public static final int FRM_FIELD_EVAL_POINT = 5;
  public static final int FRM_FIELD_JUSTIFICATION = 6;
  public static final int FRM_FIELD_SUGGESTION = 7;


public static String[] fieldNames = {
    "FRM_FIELD_OUT_SOURCE_EVAL_PROVIDER_ID",
    "FRM_FIELD_OUT_SOURCE_EVAL_ID",
    "FRM_FIELD_POSITION_ID",
    "FRM_FIELD_PROVIDER_ID",
    "FRM_FIELD_NUMBER_OF_PERSON",
    "FRM_FIELD_EVAL_POINT",
    "FRM_FIELD_JUSTIFICATION",
    "FRM_FIELD_SUGGESTION"
};

public static int[] fieldTypes = {
    TYPE_LONG,
    TYPE_LONG,
    TYPE_LONG,
    TYPE_LONG,
    TYPE_INT,
    TYPE_INT,
    TYPE_STRING,
    TYPE_STRING
};
    
    public FrmOutSourceEvaluationProvider() {
    }

    public FrmOutSourceEvaluationProvider(OutSourceEvaluationProvider entOutSourceEvalProvider) {
        this.entOutSourceEvalProvider = entOutSourceEvalProvider;
    }

    public FrmOutSourceEvaluationProvider(HttpServletRequest request, OutSourceEvaluationProvider entOutSourceEvalProvider) {
        super(new FrmOutSourceEvaluationProvider(entOutSourceEvalProvider), request);
        this.entOutSourceEvalProvider = entOutSourceEvalProvider;
    }

    public String getFormName() {
        return FRM_NAME_OUT_SOURCE_EVAL_PROVIDER;
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

    public OutSourceEvaluationProvider getEntityObject() {
        return entOutSourceEvalProvider;
    }

    public void requestEntityObject(OutSourceEvaluationProvider entOutSourceEvaluationProvider) {
        try {
            this.requestParam();
   // entOutSourceEvalProvider.setOutsourceEvalProviderId(getLong(FRM_FIELD_OUT_SOURCE_EVAL_PROVIDER_ID));
    entOutSourceEvaluationProvider.setOutsourceEvalId(getLong(FRM_FIELD_OUT_SOURCE_EVAL_ID));
    entOutSourceEvaluationProvider.setPositionId(getLong(FRM_FIELD_POSITION_ID));
    entOutSourceEvaluationProvider.setProviderId(getLong(FRM_FIELD_PROVIDER_ID));
    entOutSourceEvaluationProvider.setNumberOfPerson(getInt(FRM_FIELD_NUMBER_OF_PERSON));
    entOutSourceEvaluationProvider.setEvalPoint(getInt(FRM_FIELD_EVAL_POINT));
    entOutSourceEvaluationProvider.setJustification(getString(FRM_FIELD_JUSTIFICATION));
    entOutSourceEvaluationProvider.setSuggestion(getString(FRM_FIELD_SUGGESTION));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
