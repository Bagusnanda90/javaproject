/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.masterdata.CompetencyType;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Dimata 007
 */
public class FrmCompetencyType extends FRMHandler implements I_FRMInterface, I_FRMType {

    private CompetencyType entCompetencyType;
    public static final String FRM_NAME_COMPETENCY_TYPE = "FRM_NAME_COMPETENCY_TYPE";
    public static final int FRM_FIELD_COMPETENCY_TYPE_ID = 0;
    public static final int FRM_FIELD_TYPE_NAME = 1;
    public static final int FRM_FIELD_NOTE = 2;
    public static String[] fieldNames = {
        "FRM_FIELD_COMPETENCY_TYPE_ID",
        "FRM_FIELD_TYPE_NAME",
        "FRM_FIELD_NOTE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING
    };

    public FrmCompetencyType() {
    }

    public FrmCompetencyType(CompetencyType entCompetencyType) {
        this.entCompetencyType = entCompetencyType;
    }

    public FrmCompetencyType(HttpServletRequest request, CompetencyType entCompetencyType) {
        super(new FrmCompetencyType(entCompetencyType), request);
        this.entCompetencyType = entCompetencyType;
    }

    public String getFormName() {
        return FRM_NAME_COMPETENCY_TYPE;
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

    public CompetencyType getEntityObject() {
        return entCompetencyType;
    }

    public void requestEntityObject(CompetencyType entCompetencyType) {
        try {
            this.requestParam();
            entCompetencyType.setTypeName(getString(FRM_FIELD_TYPE_NAME));
            entCompetencyType.setNote(getString(FRM_FIELD_NOTE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}