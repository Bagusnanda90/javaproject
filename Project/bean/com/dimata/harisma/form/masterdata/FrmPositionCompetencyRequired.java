/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.masterdata.PositionCompetencyRequired;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;

public class FrmPositionCompetencyRequired extends FRMHandler implements I_FRMInterface, I_FRMType {

    private PositionCompetencyRequired entPositionCompetencyRequired;
    public static final String FRM_NAME_POSITION_COMPETENCY_REQUIRED = "FRM_NAME_POSITION_COMPETENCY_REQUIRED";
    public static final int FRM_FIELD_POS_COMP_REQ_ID = 0;
    public static final int FRM_FIELD_POSITION_ID = 1;
    public static final int FRM_FIELD_COMPETENCY_ID = 2;
    public static final int FRM_FIELD_SCORE_REQ_MIN = 3;
    public static final int FRM_FIELD_SCORE_REQ_RECOMMENDED = 4;
    public static final int FRM_FIELD_COMPETENCY_LEVEL_ID_MIN = 5;
    public static final int FRM_FIELD_COMPETENCY_LEVEL_ID_RECOMMENDED = 6;
    public static final int FRM_FIELD_COMPETENCY_LEVEL_ID = 7;
    public static final int FRM_FIELD_NOTE = 8;
    public static final int FRM_FIELD_RE_TRAIN_OR_SERTFC_REQ = 9;
    public static final int FRM_FIELD_VALID_START = 10;
    public static final int FRM_FIELD_VALID_END = 11;
    public static String[] fieldNames = {
        "FRM_FIELD_POS_COMP_REQ_ID",
        "FRM_FIELD_POSITION_ID",
        "FRM_FIELD_COMPETENCY_ID",
        "FRM_FIELD_SCORE_REQ_MIN",
        "FRM_FIELD_SCORE_REQ_RECOMMENDED",
        "FRM_FIELD_COMPETENCY_LEVEL_ID_MIN",
        "FRM_FIELD_COMPETENCY_LEVEL_ID_RECOMMENDED",
        "FRM_FIELD_COMPETENCY_LEVEL_ID",
        "FRM_FIELD_NOTE",
        "FRM_FIELD_RE_TRAIN_OR_SERTFC_REQ",
        "FRM_FIELD_VALID_START",
        "FRM_FIELD_VALID_END"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING
    };

    public FrmPositionCompetencyRequired() {
    }

    public FrmPositionCompetencyRequired(PositionCompetencyRequired entPositionCompetencyRequired) {
        this.entPositionCompetencyRequired = entPositionCompetencyRequired;
    }

    public FrmPositionCompetencyRequired(HttpServletRequest request, PositionCompetencyRequired entPositionCompetencyRequired) {
        super(new FrmPositionCompetencyRequired(entPositionCompetencyRequired), request);
        this.entPositionCompetencyRequired = entPositionCompetencyRequired;
    }

    public String getFormName() {
        return FRM_NAME_POSITION_COMPETENCY_REQUIRED;
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

    public PositionCompetencyRequired getEntityObject() {
        return entPositionCompetencyRequired;
    }

    public void requestEntityObject(PositionCompetencyRequired entPositionCompetencyRequired) {
        try {
            this.requestParam();
            entPositionCompetencyRequired.setPositionId(getLong(FRM_FIELD_POSITION_ID));
            entPositionCompetencyRequired.setCompetencyId(getLong(FRM_FIELD_COMPETENCY_ID));
            entPositionCompetencyRequired.setScoreReqMin(getFloat(FRM_FIELD_SCORE_REQ_MIN));
            entPositionCompetencyRequired.setScoreReqRecommended(getFloat(FRM_FIELD_SCORE_REQ_RECOMMENDED));
            entPositionCompetencyRequired.setCompetencyLevelIdMin(getInt(FRM_FIELD_COMPETENCY_LEVEL_ID_MIN));
            entPositionCompetencyRequired.setCompetencyLevelIdRecommended(getInt(FRM_FIELD_COMPETENCY_LEVEL_ID_RECOMMENDED));
            entPositionCompetencyRequired.setCompetencyLevelId(getLong(FRM_FIELD_COMPETENCY_LEVEL_ID));
            entPositionCompetencyRequired.setNote(getString(FRM_FIELD_NOTE));
            entPositionCompetencyRequired.setReTrainOrSertfcReq(getInt(FRM_FIELD_RE_TRAIN_OR_SERTFC_REQ));
            entPositionCompetencyRequired.setValidStart(Date.valueOf(getString(FRM_FIELD_VALID_START)));
            entPositionCompetencyRequired.setValidEnd(Date.valueOf(getString(FRM_FIELD_VALID_END)));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
