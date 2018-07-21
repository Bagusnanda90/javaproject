/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.employee;

/**
 *
 * @author Dimata 007
 */
import com.dimata.harisma.entity.employee.CandidateMain;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;

public class FrmCandidateMain extends FRMHandler implements I_FRMInterface, I_FRMType {

    private CandidateMain entCandidateMain;
    public static final String FRM_NAME_CANDIDATE_MAIN = "FRM_NAME_CANDIDATE_MAIN";
    public static final int FRM_FIELD_CANDIDATE_MAIN_ID = 0;
    public static final int FRM_FIELD_TITLE = 1;
    public static final int FRM_FIELD_OBJECTIVE = 2;
    public static final int FRM_FIELD_DUE_DATE = 3;
    public static final int FRM_FIELD_REQUESTED_BY = 4;
    public static final int FRM_FIELD_DATE_OF_REQUEST = 5;
    public static final int FRM_FIELD_SCORE_TOLERANCE = 6;
    public static final int FRM_FIELD_CREATED_BY = 7;
    public static final int FRM_FIELD_CREATED_DATE = 8;
    public static final int FRM_FIELD_STATUS_DOC = 9;
    public static final int FRM_FIELD_CANDIDATE_TYPE = 10;
    public static final int FRM_FIELD_APPROVE_BY_ID_1 = 11;
    public static final int FRM_FIELD_APPROVE_BY_ID_2 = 12;
    public static final int FRM_FIELD_APPROVE_BY_ID_3 = 13;
    public static final int FRM_FIELD_APPROVE_BY_ID_4 = 14;
    public static final int FRM_FIELD_APPROVE_DATE_1 = 15;
    public static final int FRM_FIELD_APPROVE_DATE_2 = 16;
    public static final int FRM_FIELD_APPROVE_DATE_3 = 17;
    public static final int FRM_FIELD_APPROVE_DATE_4 = 18;
    public static String[] fieldNames = {
        "FRM_FIELD_CANDIDATE_MAIN_ID",
        "FRM_FIELD_TITLE",
        "FRM_FIELD_OBJECTIVE",
        "FRM_FIELD_DUE_DATE",
        "FRM_FIELD_REQUESTED_BY",
        "FRM_FIELD_DATE_OF_REQUEST",
        "FRM_FIELD_SCORE_TOLERANCE",
        "FRM_FIELD_CREATED_BY",
        "FRM_FIELD_CREATED_DATE",
        "FRM_FIELD_STATUS_DOC",
        "FRM_FIELD_CANDIDATE_TYPE",
        "FRM_FIELD_APPROVE_BY_ID_1",
        "FRM_FIELD_APPROVE_BY_ID_2",
        "FRM_FIELD_APPROVE_BY_ID_3",
        "FRM_FIELD_APPROVE_BY_ID_4",
        "FRM_FIELD_APPROVE_DATE_1",
        "FRM_FIELD_APPROVE_DATE_2",
        "FRM_FIELD_APPROVE_DATE_3",
        "FRM_FIELD_APPROVE_DATE_4"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_FLOAT,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING
    };

    public FrmCandidateMain() {
    }

    public FrmCandidateMain(CandidateMain entCandidateMain) {
        this.entCandidateMain = entCandidateMain;
    }

    public FrmCandidateMain(HttpServletRequest request, CandidateMain entCandidateMain) {
        super(new FrmCandidateMain(entCandidateMain), request);
        this.entCandidateMain = entCandidateMain;
    }

    public String getFormName() {
        return FRM_NAME_CANDIDATE_MAIN;
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

    public CandidateMain getEntityObject() {
        return entCandidateMain;
    }

    public void requestEntityObject(CandidateMain entCandidateMain) {
        try {
            this.requestParam();
            entCandidateMain.setTitle(getString(FRM_FIELD_TITLE));
            entCandidateMain.setObjective(getString(FRM_FIELD_OBJECTIVE));
            entCandidateMain.setDueDate(Date.valueOf(getString(FRM_FIELD_DUE_DATE)));
            entCandidateMain.setRequestedBy(getLong(FRM_FIELD_REQUESTED_BY));
            entCandidateMain.setDateOfRequest(Date.valueOf(getString(FRM_FIELD_DATE_OF_REQUEST)));
            entCandidateMain.setScoreTolerance(getFloat(FRM_FIELD_SCORE_TOLERANCE));
            entCandidateMain.setCreatedBy(getLong(FRM_FIELD_CREATED_BY));
            entCandidateMain.setCreatedDate(Date.valueOf(getString(FRM_FIELD_CREATED_DATE)));
            entCandidateMain.setStatusDoc(getInt(FRM_FIELD_STATUS_DOC));
            entCandidateMain.setCandidateType(getInt(FRM_FIELD_CANDIDATE_TYPE));
            entCandidateMain.setApproveById1(getLong(FRM_FIELD_APPROVE_BY_ID_1));
            entCandidateMain.setApproveById2(getLong(FRM_FIELD_APPROVE_BY_ID_2));
            entCandidateMain.setApproveById3(getLong(FRM_FIELD_APPROVE_BY_ID_3));
            entCandidateMain.setApproveById4(getLong(FRM_FIELD_APPROVE_BY_ID_4));
            entCandidateMain.setApproveDate1(Date.valueOf(getString(FRM_FIELD_APPROVE_DATE_1)));
            entCandidateMain.setApproveDate2(Date.valueOf(getString(FRM_FIELD_APPROVE_DATE_2)));
            entCandidateMain.setApproveDate3(Date.valueOf(getString(FRM_FIELD_APPROVE_DATE_3)));
            entCandidateMain.setApproveDate4(Date.valueOf(getString(FRM_FIELD_APPROVE_DATE_4)));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}