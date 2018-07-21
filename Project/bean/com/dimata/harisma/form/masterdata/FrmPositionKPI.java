/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.masterdata.PositionKPI;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class FrmPositionKPI extends FRMHandler implements I_FRMInterface, I_FRMType {

    private PositionKPI entPositionKpi;
    public static final String FRM_NAME_POSITION_KPI = "FRM_NAME_POSITION_KPI";
    public static final int FRM_FIELD_POS_KPI_ID = 0;
    public static final int FRM_FIELD_KPI_MIN_ACHIEVMENT = 1;
    public static final int FRM_FIELD_KPI_RECOMMEND_ACHIEV = 2;
    public static final int FRM_FIELD_DURATION_MONTH = 3;
    public static final int FRM_FIELD_POSITION_ID = 4;
    public static final int FRM_FIELD_KPI_LIST_ID = 5;
    public static final int FRM_FIELD_SCORE_MIN = 6;
    public static final int FRM_FIELD_SCORE_RECOMMENDED = 7;
    public static String[] fieldNames = {
        "FRM_FIELD_POS_KPI_ID",
        "FRM_FIELD_KPI_MIN_ACHIEVMENT",
        "FRM_FIELD_KPI_RECOMMEND_ACHIEV",
        "FRM_FIELD_DURATION_MONTH",
        "FRM_FIELD_POSITION_ID",
        "FRM_FIELD_KPI_LIST_ID",
        "FRM_FIELD_SCORE_MIN",
        "FRM_FIELD_SCORE_RECOMMENDED"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT
    };

    public FrmPositionKPI() {
    }

    public FrmPositionKPI(PositionKPI entPositionKpi) {
        this.entPositionKpi = entPositionKpi;
    }

    public FrmPositionKPI(HttpServletRequest request, PositionKPI entPositionKpi) {
        super(new FrmPositionKPI(entPositionKpi), request);
        this.entPositionKpi = entPositionKpi;
    }

    public String getFormName() {
        return FRM_NAME_POSITION_KPI;
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

    public PositionKPI getEntityObject() {
        return entPositionKpi;
    }

    public void requestEntityObject(PositionKPI entPositionKpi) {
        try {
            this.requestParam();
            entPositionKpi.setKpiMinAchievment(getInt(FRM_FIELD_KPI_MIN_ACHIEVMENT));
            entPositionKpi.setKpiRecommendAchiev(getInt(FRM_FIELD_KPI_RECOMMEND_ACHIEV));
            entPositionKpi.setDurationMonth(getInt(FRM_FIELD_DURATION_MONTH));
            entPositionKpi.setPositionId(getLong(FRM_FIELD_POSITION_ID));
            entPositionKpi.setKpiListId(getLong(FRM_FIELD_KPI_LIST_ID));
            entPositionKpi.setScoreMin(getFloat(FRM_FIELD_SCORE_MIN));
            entPositionKpi.setScoreRecommended(getFloat(FRM_FIELD_SCORE_RECOMMENDED));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
