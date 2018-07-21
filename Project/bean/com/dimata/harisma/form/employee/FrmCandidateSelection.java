/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.employee;

/**
 *
 * @author Dimata 007
 */
import com.dimata.harisma.entity.employee.CandidateSelection;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmCandidateSelection extends FRMHandler implements I_FRMInterface, I_FRMType {

    private CandidateSelection entCandidateSelection;
    public static final String FRM_NAME_CANDIDATE_SELECTION = "FRM_NAME_CANDIDATE_SELECTION";
    public static final int FRM_FIELD_CANDIDATE_SELECTION_ID = 0;
    public static final int FRM_FIELD_CANDIDATE_MAIN_ID = 1;
    public static final int FRM_FIELD_CANDIDATE_EDUCATION_ID = 2;
    public static final int FRM_FIELD_CANDIDATE_TRAINING_ID = 3;
    public static final int FRM_FIELD_CANDIDATE_COMPETENCY_ID = 4;
    public static final int FRM_FIELD_CANDIDATE_KPI_ID = 5;
    public static String[] fieldNames = {
        "FRM_FIELD_CANDIDATE_SELECTION_ID",
        "FRM_FIELD_CANDIDATE_MAIN_ID",
        "FRM_FIELD_CANDIDATE_EDUCATION_ID",
        "FRM_FIELD_CANDIDATE_TRAINING_ID",
        "FRM_FIELD_CANDIDATE_COMPETENCY_ID",
        "FRM_FIELD_CANDIDATE_KPI_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG
    };

    public FrmCandidateSelection() {
    }

    public FrmCandidateSelection(CandidateSelection entCandidateSelection) {
        this.entCandidateSelection = entCandidateSelection;
    }

    public FrmCandidateSelection(HttpServletRequest request, CandidateSelection entCandidateSelection) {
        super(new FrmCandidateSelection(entCandidateSelection), request);
        this.entCandidateSelection = entCandidateSelection;
    }

    public String getFormName() {
        return FRM_NAME_CANDIDATE_SELECTION;
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

    public CandidateSelection getEntityObject() {
        return entCandidateSelection;
    }

    public void requestEntityObject(CandidateSelection entCandidateSelection) {
        try {
            this.requestParam();
            entCandidateSelection.setCandidateMainId(getLong(FRM_FIELD_CANDIDATE_MAIN_ID));
            entCandidateSelection.setCandidateEducationId(getLong(FRM_FIELD_CANDIDATE_EDUCATION_ID));
            entCandidateSelection.setCandidateTrainingId(getLong(FRM_FIELD_CANDIDATE_TRAINING_ID));
            entCandidateSelection.setCandidateCompetencyId(getLong(FRM_FIELD_CANDIDATE_COMPETENCY_ID));
            entCandidateSelection.setCandidateKpiId(getLong(FRM_FIELD_CANDIDATE_KPI_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}