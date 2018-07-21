/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.employee;

import com.dimata.harisma.entity.employee.PositionCandidateLocation;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmPositionCandidateLocation extends FRMHandler implements I_FRMInterface, I_FRMType {

    private PositionCandidateLocation entPositionCandidateLocation;
    public static final String FRM_NAME_POSITION_CANDIDATE_LOCATION = "FRM_NAME_POSITION_CANDIDATE_LOCATION";
    public static final int FRM_FIELD_POS_CANDIDATE_LOCATION_ID = 0;
    public static final int FRM_FIELD_POS_CANDIDATE_ID = 1;
    public static final int FRM_FIELD_COMPANY_ID = 2;
    public static final int FRM_FIELD_DIVISION_ID = 3;
    public static final int FRM_FIELD_DEPARTMENT_ID = 4;
    public static final int FRM_FIELD_SECTION_ID = 5;
    public static final int FRM_FIELD_NUMBER_NEEDED = 6;
    public static final int FRM_FIELD_DUE_DATE = 7;
    public static String[] fieldNames = {
        "FRM_FIELD_POS_CANDIDATE_LOCATION_ID",
        "FRM_FIELD_POS_CANDIDATE_ID",
        "FRM_FIELD_COMPANY_ID",
        "FRM_FIELD_DIVISION_ID",
        "FRM_FIELD_DEPARTMENT_ID",
        "FRM_FIELD_SECTION_ID",
        "FRM_FIELD_NUMBER_NEEDED",
        "FRM_FIELD_DUE_DATE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_DATE
    };

    public FrmPositionCandidateLocation() {
    }

    public FrmPositionCandidateLocation(PositionCandidateLocation entPositionCandidateLocation) {
        this.entPositionCandidateLocation = entPositionCandidateLocation;
    }

    public FrmPositionCandidateLocation(HttpServletRequest request, PositionCandidateLocation entPositionCandidateLocation) {
        super(new FrmPositionCandidateLocation(entPositionCandidateLocation), request);
        this.entPositionCandidateLocation = entPositionCandidateLocation;
    }

    public String getFormName() {
        return FRM_NAME_POSITION_CANDIDATE_LOCATION;
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

    public PositionCandidateLocation getEntityObject() {
        return entPositionCandidateLocation;
    }

    public void requestEntityObject(PositionCandidateLocation entPositionCandidateLocation) {
        try {
            this.requestParam();
            entPositionCandidateLocation.setPosCandidateId(getLong(FRM_FIELD_POS_CANDIDATE_ID));
            entPositionCandidateLocation.setCompanyId(getLong(FRM_FIELD_COMPANY_ID));
            entPositionCandidateLocation.setDivisionId(getLong(FRM_FIELD_DIVISION_ID));
            entPositionCandidateLocation.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
            entPositionCandidateLocation.setSectionId(getLong(FRM_FIELD_SECTION_ID));
            entPositionCandidateLocation.setNumberNeeded(getInt(FRM_FIELD_NUMBER_NEEDED));
            entPositionCandidateLocation.setDueDate(getDate(FRM_FIELD_DUE_DATE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}