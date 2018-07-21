/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.masterdata.CompetencyDetail;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmCompetencyDetail extends FRMHandler implements I_FRMInterface, I_FRMType {

    private CompetencyDetail entCompetencyDetail;
    public static final String FRM_NAME_COMPETENCY_DETAIL = "FRM_NAME_COMPETENCY_DETAIL";
    public static final int FRM_FIELD_DETAIL_ID = 0;
    public static final int FRM_FIELD_COMPETENCY_ID = 1;
    public static final int FRM_FIELD_DESCRIPTION = 2;
    public static String[] fieldNames = {
        "FRM_FIELD_DETAIL_ID",
        "FRM_FIELD_COMPETENCY_ID",
        "FRM_FIELD_DESCRIPTION"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING
    };

    public FrmCompetencyDetail() {
    }

    public FrmCompetencyDetail(CompetencyDetail entCompetencyDetail) {
        this.entCompetencyDetail = entCompetencyDetail;
    }

    public FrmCompetencyDetail(HttpServletRequest request, CompetencyDetail entCompetencyDetail) {
        super(new FrmCompetencyDetail(entCompetencyDetail), request);
        this.entCompetencyDetail = entCompetencyDetail;
    }

    public String getFormName() {
        return FRM_NAME_COMPETENCY_DETAIL;
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

    public CompetencyDetail getEntityObject() {
        return entCompetencyDetail;
    }

    public void requestEntityObject(CompetencyDetail entCompetencyDetail) {
        try {
            this.requestParam();
            entCompetencyDetail.setCompetencyId(getLong(FRM_FIELD_COMPETENCY_ID));
            entCompetencyDetail.setDescription(getString(FRM_FIELD_DESCRIPTION));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
