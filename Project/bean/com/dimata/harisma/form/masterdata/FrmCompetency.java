/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

/**
 *
 * @author Dimata 007
 */
import com.dimata.harisma.entity.masterdata.Competency;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmCompetency extends FRMHandler implements I_FRMInterface, I_FRMType {

    private Competency entCompetency;
    public static final String FRM_NAME_COMPETENCY = "FRM_NAME_COMPETENCY";
    public static final int FRM_FIELD_COMPETENCY_ID = 0;
    public static final int FRM_FIELD_COMPETENCY_GROUP_ID = 1;
    public static final int FRM_FIELD_COMPETENCY_TYPE_ID = 2;
    public static final int FRM_FIELD_COMPETENCY_NAME = 3;
    public static final int FRM_FIELD_DESCRIPTION = 4;
    public static String[] fieldNames = {
        "FRM_FIELD_COMPETENCY_ID",
        "FRM_FIELD_COMPETENCY_GROUP_ID ",
        "FRM_FIELD_COMPETENCY_TYPE_ID",
        "FRM_FIELD_COMPETENCY_NAME",
        "FRM_FIELD_DESCRIPTION"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING
    };

    public FrmCompetency() {
    }

    public FrmCompetency(Competency entCompetency) {
        this.entCompetency = entCompetency;
    }

    public FrmCompetency(HttpServletRequest request, Competency entCompetency) {
        super(new FrmCompetency(entCompetency), request);
        this.entCompetency = entCompetency;
    }

    public String getFormName() {
        return FRM_NAME_COMPETENCY;
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

    public Competency getEntityObject() {
        return entCompetency;
    }

    public void requestEntityObject(Competency entCompetency) {
        try {
            this.requestParam();
            entCompetency.setCompetencyGroupId(getLong(FRM_FIELD_COMPETENCY_GROUP_ID));
            entCompetency.setCompetencyTypeId(getLong(FRM_FIELD_COMPETENCY_TYPE_ID));
            entCompetency.setCompetencyName(getString(FRM_FIELD_COMPETENCY_NAME));
            entCompetency.setDescription(getString(FRM_FIELD_DESCRIPTION));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
