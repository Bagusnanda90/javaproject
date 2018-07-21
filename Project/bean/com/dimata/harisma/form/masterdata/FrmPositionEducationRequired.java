/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.masterdata.PositionEducationRequired;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmPositionEducationRequired extends FRMHandler implements I_FRMInterface, I_FRMType {

    private PositionEducationRequired entPositionEducationRequired;
    public static final String FRM_NAME_POSITION_EDUCATION_REQUIRED = "FRM_NAME_POSITION_EDUCATION_REQUIRED";
    public static final int FRM_FIELD_POS_EDUCATION_REQ_ID = 0;
    public static final int FRM_FIELD_POSITION_ID = 1;
    public static final int FRM_FIELD_EDUCATION_ID = 2;
    public static final int FRM_FIELD_DURATION_MIN = 3;
    public static final int FRM_FIELD_DURATION_RECOMMENDED = 4;
    public static final int FRM_FIELD_POINT_MIN = 5;
    public static final int FRM_FIELD_POINT_RECOMMENDED = 6;
    public static final int FRM_FIELD_NOTE = 7;
    public static String[] fieldNames = {
        "FRM_FIELD_POS_EDUCATION_REQ_ID",
        "FRM_FIELD_POSITION_ID",
        "FRM_FIELD_EDUCATION_ID",
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

    public FrmPositionEducationRequired() {
    }

    public FrmPositionEducationRequired(PositionEducationRequired entPositionEducationRequired) {
        this.entPositionEducationRequired = entPositionEducationRequired;
    }

    public FrmPositionEducationRequired(HttpServletRequest request, PositionEducationRequired entPositionEducationRequired) {
        super(new FrmPositionEducationRequired(entPositionEducationRequired), request);
        this.entPositionEducationRequired = entPositionEducationRequired;
    }

    public String getFormName() {
        return FRM_NAME_POSITION_EDUCATION_REQUIRED;
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

    public PositionEducationRequired getEntityObject() {
        return entPositionEducationRequired;
    }

    public void requestEntityObject(PositionEducationRequired entPositionEducationRequired) {
        try {
            this.requestParam();
            entPositionEducationRequired.setPositionId(getLong(FRM_FIELD_POSITION_ID));
            entPositionEducationRequired.setEducationId(getLong(FRM_FIELD_EDUCATION_ID));
            entPositionEducationRequired.setDurationMin(getInt(FRM_FIELD_DURATION_MIN));
            entPositionEducationRequired.setDurationRecommended(getInt(FRM_FIELD_DURATION_RECOMMENDED));
            entPositionEducationRequired.setPointMin(getInt(FRM_FIELD_POINT_MIN));
            entPositionEducationRequired.setPointRecommended(getInt(FRM_FIELD_POINT_RECOMMENDED));
            entPositionEducationRequired.setNote(getString(FRM_FIELD_NOTE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}