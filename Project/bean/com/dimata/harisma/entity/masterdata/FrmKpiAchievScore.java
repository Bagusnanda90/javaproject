/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Hendra Putu
 */
import com.dimata.harisma.entity.masterdata.KpiAchievScore;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;

public class FrmKpiAchievScore extends FRMHandler implements I_FRMInterface, I_FRMType {

    private KpiAchievScore entKpiAchievScore;
    public static final String FRM_NAME_KPI_ACHIEV_SCORE = "FRM_NAME_KPI_ACHIEV_SCORE";
    public static final int FRM_FIELD_KPI_ACHIEV_SCORE_ID = 0;
    public static final int FRM_FIELD_KPI_LIST_ID = 1;
    public static final int FRM_FIELD_ACHIEV_PCTG_MIN = 2;
    public static final int FRM_FIELD_ACHIEV_PCTG_MAX = 3;
    public static final int FRM_FIELD_ACHIEV_DURATION_MIN = 4;
    public static final int FRM_FIELD_ACHIEV_DURATION_MAX = 5;
    public static final int FRM_FIELD_SCORE = 6;
    public static final int FRM_FIELD_VALID_START = 7;
    public static final int FRM_FIELD_VALID_END = 8;
    public static String[] fieldNames = {
        "FRM_FIELD_KPI_ACHIEV_SCORE_ID",
        "FRM_FIELD_KPI_LIST_ID",
        "FRM_FIELD_ACHIEV_PCTG_MIN",
        "FRM_FIELD_ACHIEV_PCTG_MAX",
        "FRM_FIELD_ACHIEV_DURATION_MIN",
        "FRM_FIELD_ACHIEV_DURATION_MAX",
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

    public FrmKpiAchievScore() {
    }

    public FrmKpiAchievScore(KpiAchievScore entKpiAchievScore) {
        this.entKpiAchievScore = entKpiAchievScore;
    }

    public FrmKpiAchievScore(HttpServletRequest request, KpiAchievScore entKpiAchievScore) {
        super(new FrmKpiAchievScore(entKpiAchievScore), request);
        this.entKpiAchievScore = entKpiAchievScore;
    }

    public String getFormName() {
        return FRM_NAME_KPI_ACHIEV_SCORE;
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

    public KpiAchievScore getEntityObject() {
        return entKpiAchievScore;
    }

    public void requestEntityObject(KpiAchievScore entKpiAchievScore) {
        try {
            this.requestParam();
            entKpiAchievScore.setKpiListId(getLong(FRM_FIELD_KPI_LIST_ID));
            entKpiAchievScore.setAchievPctgMin(getFloat(FRM_FIELD_ACHIEV_PCTG_MIN));
            entKpiAchievScore.setAchievPctgMax(getFloat(FRM_FIELD_ACHIEV_PCTG_MAX));
            entKpiAchievScore.setAchievDurationMin(getFloat(FRM_FIELD_ACHIEV_DURATION_MIN));
            entKpiAchievScore.setAchievDurationMax(getFloat(FRM_FIELD_ACHIEV_DURATION_MAX));
            entKpiAchievScore.setScore(getFloat(FRM_FIELD_SCORE));
            entKpiAchievScore.setValidStart(Date.valueOf(getString(FRM_FIELD_VALID_START)));
            entKpiAchievScore.setValidEnd(Date.valueOf(getString(FRM_FIELD_VALID_END)));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}