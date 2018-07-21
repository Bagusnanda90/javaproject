/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.employee;

import com.dimata.harisma.entity.employee.PositionCandidate;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmPositionCandidate extends FRMHandler implements I_FRMInterface, I_FRMType {

    private PositionCandidate entPositionCandidate;
    public static final String FRM_NAME_POSITION_CANDIDATE = "FRM_NAME_POSITION_CANDIDATE";
    public static final int FRM_FIELD_POS_CANDIDATE_ID = 0;
    public static final int FRM_FIELD_CANDIDATE_TYPE = 1;
    public static final int FRM_FIELD_POSITION_ID = 2;
    public static final int FRM_FIELD_TITLE = 3;
    public static final int FRM_FIELD_OBJECTIVES = 4;
    public static final int FRM_FIELD_NUM_CANDIDATES = 5;
    public static final int FRM_FIELD_DUE_DATE = 6;
    public static final int FRM_FIELD_REQUEST_BY = 7;
    public static final int FRM_FIELD_DOC_STATUS = 8;
    public static final int FRM_FIELD_COMPANY = 9;
    public static final int FRM_FIELD_DIVISION = 10;
    public static final int FRM_FIELD_DEPARTMENT = 11;
    public static final int FRM_FIELD_SECTION = 12;
    public static final int FRM_FIELD_SEARCH_DATE = 13;
    public static String[] fieldNames = {
        "FRM_FIELD_POS_CANDIDATE_ID",
        "FRM_FIELD_CANDIDATE_TYPE",
        "FRM_FIELD_POSITION_ID",
        "FRM_FIELD_TITLE",
        "FRM_FIELD_OBJECTIVES",
        "FRM_FIELD_NUM_CANDIDATES",
        "FRM_FIELD_DUE_DATE",
        "FRM_FIELD_REQUEST_BY",
        "FRM_FIELD_DOC_STATUS",
        "FRM_FIELD_COMPANY",
        "FRM_FIELD_DIVISION",
        "FRM_FIELD_DEPARTMENT",
        "FRM_FIELD_SECTION",
        "FRM_FIELD_SEARCH_DATE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_INT,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_DATE
    };

    private String[] SelectedValues;
    
    public FrmPositionCandidate() {
    }

    public FrmPositionCandidate(PositionCandidate entPositionCandidate) {
        this.entPositionCandidate = entPositionCandidate;
    }

    public FrmPositionCandidate(HttpServletRequest request, PositionCandidate entPositionCandidate) {
        super(new FrmPositionCandidate(entPositionCandidate), request);
        this.entPositionCandidate = entPositionCandidate;
    }

    public String getFormName() {
        return FRM_NAME_POSITION_CANDIDATE;
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

    public PositionCandidate getEntityObject() {
        return entPositionCandidate;
    }

    public void requestEntityObject(PositionCandidate entPositionCandidate) {
        try {
            this.requestParam();
            entPositionCandidate.setCandidateType(getInt(FRM_FIELD_CANDIDATE_TYPE));
            entPositionCandidate.setPositionId(getLong(FRM_FIELD_POSITION_ID));
            entPositionCandidate.setTitle(getString(FRM_FIELD_TITLE));
            entPositionCandidate.setObjectives(getString(FRM_FIELD_OBJECTIVES));
            entPositionCandidate.setNumCandidates(getInt(FRM_FIELD_NUM_CANDIDATES));
            entPositionCandidate.setDueDate(getDate(FRM_FIELD_DUE_DATE));
            entPositionCandidate.setRequestBy(getLong(FRM_FIELD_REQUEST_BY));
            entPositionCandidate.setDocStatus(getInt(FRM_FIELD_DOC_STATUS));
            entPositionCandidate.setCompany(getString(FRM_FIELD_COMPANY));
            entPositionCandidate.setDivision(getString(FRM_FIELD_DIVISION));
            entPositionCandidate.setDepartment(getString(FRM_FIELD_DEPARTMENT));
            entPositionCandidate.setSection(getString(FRM_FIELD_SECTION));
            entPositionCandidate.setSearchDate(getDate(FRM_FIELD_SEARCH_DATE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
    
    public String[] getSelectedValues() {
        return SelectedValues;
    }

    /**
     * @param SelectedValues the SelectedValues to set
     */
    public void setSelectedValues(String[] SelectedValues) {
        this.SelectedValues = SelectedValues;
    }
    // 2014-11-27 update by Hendra McHen
    public String getSelectedCheckBox(){
        String[] checks = getSelectedValues();
        String checkValues = "";
        for (int i = 0; i < checks.length; ++i){
            if (i != checks.length-1) {
                checkValues = checkValues + checks[i] + ",";
            }
            else {
                checkValues = checkValues + checks[i];
            }
        }
        return checkValues;
    }
}
