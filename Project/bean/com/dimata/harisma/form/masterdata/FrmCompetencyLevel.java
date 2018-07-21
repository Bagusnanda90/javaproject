/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.masterdata.CompetencyLevel;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;

public class FrmCompetencyLevel extends FRMHandler implements I_FRMInterface, I_FRMType {

    private CompetencyLevel entCompetencyLevel;
    public static final String FRM_NAME_COMPETENCY_LEVEL = "FRM_NAME_COMPETENCY_LEVEL";
    public static final int FRM_FIELD_COMPETENCY_LEVEL_ID = 0;
    public static final int FRM_FIELD_COMPENTENCY_ID = 1;
    public static final int FRM_FIELD_SCORE_VALUE = 2;
    public static final int FRM_FIELD_DESCRIPTION = 3;
    public static final int FRM_FIELD_LEVEL_MIN = 4;
    public static final int FRM_FIELD_LEVEL_MAX = 5;
    public static final int FRM_FIELD_LEVEL_UNIT = 6;
    public static final int FRM_FIELD_VALID_START = 7;
    public static final int FRM_FIELD_VALID_END = 8;
    public static String[] fieldNames = {
        "FRM_FIELD_COMPETENCY_LEVEL_ID",
        "FRM_FIELD_COMPENTENCY_ID",
        "FRM_FIELD_SCORE_VALUE",
        "FRM_FIELD_DESCRIPTION",
        "FRM_FIELD_LEVEL_MIN",
        "FRM_FIELD_LEVEL_MAX",
        "FRM_FIELD_LEVEL_UNIT",
        "FRM_FIELD_VALID_START",
        "FRM_FIELD_VALID_END"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING
    };

    public FrmCompetencyLevel() {
    }

    public FrmCompetencyLevel(CompetencyLevel entCompetencyLevel) {
        this.entCompetencyLevel = entCompetencyLevel;
    }

    public FrmCompetencyLevel(HttpServletRequest request, CompetencyLevel entCompetencyLevel) {
        super(new FrmCompetencyLevel(entCompetencyLevel), request);
        this.entCompetencyLevel = entCompetencyLevel;
    }

    public String getFormName() {
        return FRM_NAME_COMPETENCY_LEVEL;
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

    public CompetencyLevel getEntityObject() {
        return entCompetencyLevel;
    }

    public void requestEntityObject(CompetencyLevel entCompetencyLevel) {
        try {
            this.requestParam();
            entCompetencyLevel.setCompetencyId(getLong(FRM_FIELD_COMPENTENCY_ID));
            entCompetencyLevel.setScoreValue(getFloat(FRM_FIELD_SCORE_VALUE));
            entCompetencyLevel.setDescription(getString(FRM_FIELD_DESCRIPTION));
            entCompetencyLevel.setLevelMin(getInt(FRM_FIELD_LEVEL_MIN));
            entCompetencyLevel.setLevelMax(getInt(FRM_FIELD_LEVEL_MAX));
            entCompetencyLevel.setLevelUnit(getString(FRM_FIELD_LEVEL_UNIT));
            entCompetencyLevel.setValidStart(Date.valueOf(getString(FRM_FIELD_VALID_START)));
            entCompetencyLevel.setValidEnd(Date.valueOf(getString(FRM_FIELD_VALID_END)));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
