/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.employee;

import com.dimata.harisma.entity.employee.PositionCandidateEmp;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmPositionCandidateEmp extends FRMHandler implements I_FRMInterface, I_FRMType {

    private PositionCandidateEmp entPositionCandidateEmp;
    public static final String FRM_NAME_POSITION_CANDIDATE_EMP = "FRM_NAME_POSITION_CANDIDATE_EMP";
    public static final int FRM_FIELD_POS_CANDIDATE_EMP_ID = 0;
    public static final int FRM_FIELD_POS_CANDIDATE_ID = 1;
    public static final int FRM_FIELD_EMPLOYEE_ID = 2;
    public static final int FRM_FIELD_RANK = 3;
    public static final int FRM_FIELD_SCORE = 4;
    public static final int FRM_FIELD_SCORE_NEED = 5;
    public static final int FRM_FIELD_COMPANY_ID = 6;
    public static final int FRM_FIELD_DIVISION_ID = 7;
    public static final int FRM_FIELD_DEPARTMENT_ID = 8;
    public static final int FRM_FIELD_SECTION_ID = 9;
    public static final int FRM_FIELD_POSITION_ID = 10;
    public static final int FRM_FIELD_CANDIDATE_STATUS = 11;
    public static String[] fieldNames = {
        "FRM_FIELD_POS_CANDIDATE_EMP_ID",
        "FRM_FIELD_POS_CANDIDATE_ID",
        "FRM_FIELD_EMPLOYEE_ID",
        "FRM_FIELD_RANK",
        "FRM_FIELD_SCORE",
        "FRM_FIELD_SCORE_NEED",
        "FRM_FIELD_COMPANY_ID",
        "FRM_FIELD_DIVISION_ID",
        "FRM_FIELD_DEPARTMENT_ID",
        "FRM_FIELD_SECTION_ID",
        "FRM_FIELD_POSITION_ID",
        "FRM_FIELD_CANDIDATE_STATUS"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT
    };

    public FrmPositionCandidateEmp() {
    }

    public FrmPositionCandidateEmp(PositionCandidateEmp entPositionCandidateEmp) {
        this.entPositionCandidateEmp = entPositionCandidateEmp;
    }

    public FrmPositionCandidateEmp(HttpServletRequest request, PositionCandidateEmp entPositionCandidateEmp) {
        super(new FrmPositionCandidateEmp(entPositionCandidateEmp), request);
        this.entPositionCandidateEmp = entPositionCandidateEmp;
    }

    public String getFormName() {
        return FRM_NAME_POSITION_CANDIDATE_EMP;
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

    public PositionCandidateEmp getEntityObject() {
        return entPositionCandidateEmp;
    }

    public void requestEntityObject(PositionCandidateEmp entPositionCandidateEmp) {
        try {
            this.requestParam();
            entPositionCandidateEmp.setPosCandidateId(getLong(FRM_FIELD_POS_CANDIDATE_ID));
            entPositionCandidateEmp.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            entPositionCandidateEmp.setRank(getInt(FRM_FIELD_RANK));
            entPositionCandidateEmp.setScore(getFloat(FRM_FIELD_SCORE));
            entPositionCandidateEmp.setScoreNeed(getFloat(FRM_FIELD_SCORE_NEED));
            entPositionCandidateEmp.setCompanyId(getLong(FRM_FIELD_COMPANY_ID));
            entPositionCandidateEmp.setDivisionId(getLong(FRM_FIELD_DIVISION_ID));
            entPositionCandidateEmp.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
            entPositionCandidateEmp.setSectionId(getLong(FRM_FIELD_SECTION_ID));
            entPositionCandidateEmp.setPositionId(getLong(FRM_FIELD_POSITION_ID));
            entPositionCandidateEmp.setCandidateStatus(getInt(FRM_FIELD_CANDIDATE_STATUS));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
