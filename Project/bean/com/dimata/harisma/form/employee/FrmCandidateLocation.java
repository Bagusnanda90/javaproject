/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.employee;

/**
 *
 * @author Dimata 007
 */
import com.dimata.harisma.entity.employee.CandidateLocation;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmCandidateLocation extends FRMHandler implements I_FRMInterface, I_FRMType {

    private CandidateLocation entCandidateLocation;
    public static final String FRM_NAME_CANDIDATE_LOCATION = "FRM_NAME_CANDIDATE_LOCATION";
    public static final int FRM_FIELD_CANDIDATE_LOC_ID = 0;
    public static final int FRM_FIELD_CANDIDATE_MAIN_ID = 1;
    public static final int FRM_FIELD_GEN_ID = 2;
    public static final int FRM_FIELD_DIVISION_ID = 3;
    public static final int FRM_FIELD_DEPARTMENT_ID = 4;
    public static final int FRM_FIELD_SECTION_ID = 5;
    public static String[] fieldNames = {
        "FRM_FIELD_CANDIDATE_LOC_ID",
        "FRM_FIELD_CANDIDATE_MAIN_ID",
        "FRM_FIELD_GEN_ID",
        "FRM_FIELD_DIVISION_ID",
        "FRM_FIELD_DEPARTMENT_ID",
        "FRM_FIELD_SECTION_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG
    };

    public FrmCandidateLocation() {
    }

    public FrmCandidateLocation(CandidateLocation entCandidateLocation) {
        this.entCandidateLocation = entCandidateLocation;
    }

    public FrmCandidateLocation(HttpServletRequest request, CandidateLocation entCandidateLocation) {
        super(new FrmCandidateLocation(entCandidateLocation), request);
        this.entCandidateLocation = entCandidateLocation;
    }

    public String getFormName() {
        return FRM_NAME_CANDIDATE_LOCATION;
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

    public CandidateLocation getEntityObject() {
        return entCandidateLocation;
    }

    public void requestEntityObject(CandidateLocation entCandidateLocation) {
        try {
            this.requestParam();
            entCandidateLocation.setCandidateMainId(getLong(FRM_FIELD_CANDIDATE_MAIN_ID));
            entCandidateLocation.setGenId(getLong(FRM_FIELD_GEN_ID));
            entCandidateLocation.setDivisionId(getLong(FRM_FIELD_DIVISION_ID));
            entCandidateLocation.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
            entCandidateLocation.setSectionId(getLong(FRM_FIELD_SECTION_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}