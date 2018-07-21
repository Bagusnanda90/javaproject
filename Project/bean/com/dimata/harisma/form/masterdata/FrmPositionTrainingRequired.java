/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.masterdata.PositionTrainingRequired;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmPositionTrainingRequired extends FRMHandler implements I_FRMInterface, I_FRMType {

    private PositionTrainingRequired entPositionTrainingRequired;
    public static final String FRM_NAME_POSITION_TRAINING_REQUIRED = "FRM_NAME_POSITION_TRAINING_REQUIRED";
    public static final int FRM_FIELD_POS_TRAINING_REQ_ID = 0;
    public static final int FRM_FIELD_POSITION_ID = 1;
    public static final int FRM_FIELD_TRAINING_ID = 2;
    public static final int FRM_FIELD_DURATION_MIN = 3;
    public static final int FRM_FIELD_DURATION_RECOMMENDED = 4;
    public static final int FRM_FIELD_POINT_MIN = 5;
    public static final int FRM_FIELD_POINT_RECOMMENDED = 6;
    public static final int FRM_FIELD_NOTE = 7;
    public static String[] fieldNames = {
        "FRM_FIELD_POS_TRAINING_REQ_ID",
        "FRM_FIELD_POSITION_ID",
        "FRM_FIELD_TRAINING_ID",
        "FRM_FIELD_DURATION_MIN",
        "FRM_FIELD_DURATION_RECOMMENDED",
        "FRM_FIELD_POINT_MIN",
        "FRM_FIELD_POINT_RECOMMENDED",
        "FRM_FIELD_NOTE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING
    };

    public FrmPositionTrainingRequired() {
    }

    public FrmPositionTrainingRequired(PositionTrainingRequired entPositionTrainingRequired) {
        this.entPositionTrainingRequired = entPositionTrainingRequired;
    }

    public FrmPositionTrainingRequired(HttpServletRequest request, PositionTrainingRequired entPositionTrainingRequired) {
        super(new FrmPositionTrainingRequired(entPositionTrainingRequired), request);
        this.entPositionTrainingRequired = entPositionTrainingRequired;
    }

    public String getFormName() {
        return FRM_NAME_POSITION_TRAINING_REQUIRED;
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

    public PositionTrainingRequired getEntityObject() {
        return entPositionTrainingRequired;
    }

    public void requestEntityObject(PositionTrainingRequired entPositionTrainingRequired) {
        try {
            this.requestParam();
            entPositionTrainingRequired.setTrainingId(getLong(FRM_FIELD_TRAINING_ID));
            entPositionTrainingRequired.setPositionId(getLong(FRM_FIELD_POSITION_ID));
            entPositionTrainingRequired.setDurationMin(getInt(FRM_FIELD_DURATION_MIN));
            entPositionTrainingRequired.setDurationRecommended(getInt(FRM_FIELD_DURATION_RECOMMENDED));
            entPositionTrainingRequired.setPointMin(getInt(FRM_FIELD_POINT_MIN));
            entPositionTrainingRequired.setPointRecommended(getInt(FRM_FIELD_POINT_RECOMMENDED));
            entPositionTrainingRequired.setNote(getString(FRM_FIELD_NOTE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
