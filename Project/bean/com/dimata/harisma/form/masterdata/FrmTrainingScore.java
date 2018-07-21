/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

/**
 *
 * @author Dimata 007
 */
import com.dimata.harisma.entity.masterdata.TrainingScore;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;

public class FrmTrainingScore extends FRMHandler implements I_FRMInterface, I_FRMType {

    private TrainingScore entTrainingScore;
    public static final String FRM_NAME_TRAINING_SCORE = "FRM_NAME_TRAINING_SCORE";
    public static final int FRM_FIELD_TRAINING_SCORE_ID = 0;
    public static final int FRM_FIELD_TRAINING_ID = 1;
    public static final int FRM_FIELD_POINT_MIN = 2;
    public static final int FRM_FIELD_POINT_MAX = 3;
    public static final int FRM_FIELD_DURATION_MIN = 4;
    public static final int FRM_FIELD_DURATION_MAX = 5;
    public static final int FRM_FIELD_FREQUENCY_MIN = 6;
    public static final int FRM_FIELD_FREQUENCY_MAX = 7;
    public static final int FRM_FIELD_SCORE = 8;
    public static final int FRM_FIELD_NOTE = 9;
    public static final int FRM_FIELD_VALID_START = 10;
    public static final int FRM_FIELD_VALID_END = 11;
    public static String[] fieldNames = {
        "FRM_FIELD_TRAINING_SCORE_ID",
        "FRM_FIELD_TRAINING_ID",
        "FRM_FIELD_POINT_MIN",
        "FRM_FIELD_POINT_MAX",
        "FRM_FIELD_DURATION_MIN",
        "FRM_FIELD_DURATION_MAX",
        "FRM_FIELD_FREQUENCY_MIN",
        "FRM_FIELD_FREQUENCY_MAX",
        "FRM_FIELD_SCORE",
        "FRM_FIELD_NOTE",
        "FRM_FIELD_VALID_START",
        "FRM_FIELD_VALID_END"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING
    };

    public FrmTrainingScore() {
    }

    public FrmTrainingScore(TrainingScore entTrainingScore) {
        this.entTrainingScore = entTrainingScore;
    }

    public FrmTrainingScore(HttpServletRequest request, TrainingScore entTrainingScore) {
        super(new FrmTrainingScore(entTrainingScore), request);
        this.entTrainingScore = entTrainingScore;
    }

    public String getFormName() {
        return FRM_NAME_TRAINING_SCORE;
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

    public TrainingScore getEntityObject() {
        return entTrainingScore;
    }

    public void requestEntityObject(TrainingScore entTrainingScore) {
        try {
            this.requestParam();
            entTrainingScore.setTrainingId(getLong(FRM_FIELD_TRAINING_ID));
            entTrainingScore.setPointMin(getFloat(FRM_FIELD_POINT_MIN));
            entTrainingScore.setPointMax(getFloat(FRM_FIELD_POINT_MAX));
            entTrainingScore.setDurationMin(getFloat(FRM_FIELD_DURATION_MIN));
            entTrainingScore.setDurationMax(getFloat(FRM_FIELD_DURATION_MAX));
            entTrainingScore.setFrequencyMin(getFloat(FRM_FIELD_FREQUENCY_MIN));
            entTrainingScore.setFrequencyMax(getFloat(FRM_FIELD_FREQUENCY_MAX));
            entTrainingScore.setScore(getFloat(FRM_FIELD_SCORE));
            entTrainingScore.setNote(getString(FRM_FIELD_NOTE));
            entTrainingScore.setValidStart(Date.valueOf(getString(FRM_FIELD_VALID_START)));
            entTrainingScore.setValidEnd(Date.valueOf(getString(FRM_FIELD_VALID_END)));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}