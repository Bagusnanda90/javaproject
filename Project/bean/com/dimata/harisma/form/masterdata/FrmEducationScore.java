/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

/**
 *
 * @author Dimata 007
 */
import com.dimata.harisma.entity.masterdata.EducationScore;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;

public class FrmEducationScore extends FRMHandler implements I_FRMInterface, I_FRMType {

    private EducationScore entEducationScore;
    public static final String FRM_NAME_EDUCATION_SCORE = "FRM_NAME_EDUCATION_SCORE";
    public static final int FRM_FIELD_EDUCATION_SCORE_ID = 0;
    public static final int FRM_FIELD_EDUCATION_ID = 1;
    public static final int FRM_FIELD_POINT_MIN = 2;
    public static final int FRM_FIELD_POINT_MAX = 3;
    public static final int FRM_FIELD_DURATION_MIN = 4;
    public static final int FRM_FIELD_DURATION_MAX = 5;
    public static final int FRM_FIELD_SCORE = 6;
    public static final int FRM_FIELD_VALID_START = 7;
    public static final int FRM_FIELD_VALID_END = 8;
    public static String[] fieldNames = {
        "FRM_FIELD_EDUCATION_SCORE_ID",
        "FRM_FIELD_EDUCATION_ID",
        "FRM_FIELD_POINT_MIN",
        "FRM_FIELD_POINT_MAX",
        "FRM_FIELD_DURATION_MIN",
        "FRM_FIELD_DURATION_MAX",
        "FRM_FIELD_SCORE",
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
        TYPE_STRING,
        TYPE_STRING
    };

    public FrmEducationScore() {
    }

    public FrmEducationScore(EducationScore entEducationScore) {
        this.entEducationScore = entEducationScore;
    }

    public FrmEducationScore(HttpServletRequest request, EducationScore entEducationScore) {
        super(new FrmEducationScore(entEducationScore), request);
        this.entEducationScore = entEducationScore;
    }

    public String getFormName() {
        return FRM_NAME_EDUCATION_SCORE;
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

    public EducationScore getEntityObject() {
        return entEducationScore;
    }

    public void requestEntityObject(EducationScore entEducationScore) {
        try {
            this.requestParam();
            entEducationScore.setEducationId(getLong(FRM_FIELD_EDUCATION_ID));
            entEducationScore.setPointMin(getFloat(FRM_FIELD_POINT_MIN));
            entEducationScore.setPointMax(getFloat(FRM_FIELD_POINT_MAX));
            entEducationScore.setDurationMin(getFloat(FRM_FIELD_DURATION_MIN));
            entEducationScore.setDurationMax(getFloat(FRM_FIELD_DURATION_MAX));
            entEducationScore.setScore(getFloat(FRM_FIELD_SCORE));
            entEducationScore.setValidStart(Date.valueOf(getString(FRM_FIELD_VALID_START)));
            entEducationScore.setValidEnd(Date.valueOf(getString(FRM_FIELD_VALID_END)));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}