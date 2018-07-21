/* 
 * Form Name  	:  FrmEvaluation.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya 
 * @version  	: 01 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.form.masterdata;

/* java package */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;

public class FrmEvaluation extends FRMHandler implements I_FRMInterface, I_FRMType {

    private Evaluation evaluation;
    public static final String FRM_NAME_EVALUATION = "FRM_NAME_EVALUATION";
    public static final int FRM_FIELD_EVALUATION_ID = 0;
    public static final int FRM_FIELD_CODE = 1;
    public static final int FRM_FIELD_NAME = 2;
    public static final int FRM_FIELD_DESRIPTION = 3;
    public static final int FRM_FIELD_MAX_PERCENTAGE = 4;
    public static final int FRM_FIELD_EVAL_TYPE = 5;
    public static final int FRM_FIELD_MAX_POINT = 6;
    public static String[] fieldNames = {
        "FRM_FIELD_EVALUATION_ID", "FRM_FIELD_CODE",
        "FRM_FIELD_NAME", "FRM_FIELD_DESRIPTION", "FRM_MAX_PERCENTAGE", "FRM_FIELD_EVAL_TYPE", "FRM_FIELD_MAX_POINT"
    };
    public static int[] fieldTypes = {
        TYPE_LONG, TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING + ENTRY_REQUIRED, TYPE_STRING, TYPE_FLOAT, TYPE_LONG, TYPE_INT+ ENTRY_REQUIRED
    };

    public FrmEvaluation() {
    }

    public FrmEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public FrmEvaluation(HttpServletRequest request, Evaluation evaluation) {
        super(new FrmEvaluation(evaluation), request);
        this.evaluation = evaluation;
    }

    public String getFormName() {
        return FRM_NAME_EVALUATION;
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

    public Evaluation getEntityObject() {
        return evaluation;
    }

    public void requestEntityObject(Evaluation evaluation) {
        try {
            this.requestParam();

            evaluation.setCode(getString(FRM_FIELD_CODE));
            evaluation.setName(getString(FRM_FIELD_NAME));
            evaluation.setDesription(getString(FRM_FIELD_DESRIPTION));
            evaluation.setMaxPercentage(getDouble(FRM_FIELD_MAX_PERCENTAGE));
            evaluation.setEvalType(getLong(FRM_FIELD_EVAL_TYPE));
            evaluation.setMaxPoint(getInt(FRM_FIELD_MAX_POINT));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
