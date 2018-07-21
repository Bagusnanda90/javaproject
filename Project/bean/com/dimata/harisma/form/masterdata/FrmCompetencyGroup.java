/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.masterdata.CompetencyGroup;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Dimata 007
 */
public class FrmCompetencyGroup extends FRMHandler implements I_FRMInterface, I_FRMType {

    private CompetencyGroup entCompetencyGroup;
    public static final String FRM_NAME_COMPETENCY_GROUP = "FRM_NAME_COMPETENCY_GROUP";
    public static final int FRM_FIELD_COMPETENCY_GROUP_ID = 0;
    public static final int FRM_FIELD_GROUP_NAME = 1;
    public static final int FRM_FIELD_NOTE = 2;
    public static String[] fieldNames = {
        "FRM_FIELD_COMPETENCY_GROUP_ID",
        "FRM_FIELD_GROUP_NAME",
        "FRM_FIELD_NOTE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING
    };

    public FrmCompetencyGroup() {
    }

    public FrmCompetencyGroup(CompetencyGroup entCompetencyGroup) {
        this.entCompetencyGroup = entCompetencyGroup;
    }

    public FrmCompetencyGroup(HttpServletRequest request, CompetencyGroup entCompetencyGroup) {
        super(new FrmCompetencyGroup(entCompetencyGroup), request);
        this.entCompetencyGroup = entCompetencyGroup;
    }

    public String getFormName() {
        return FRM_NAME_COMPETENCY_GROUP;
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

    public CompetencyGroup getEntityObject() {
        return entCompetencyGroup;
    }

    public void requestEntityObject(CompetencyGroup entCompetencyGroup) {
        try {
            this.requestParam();
            entCompetencyGroup.setGroupName(getString(FRM_FIELD_GROUP_NAME));
            entCompetencyGroup.setNote(getString(FRM_FIELD_NOTE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
