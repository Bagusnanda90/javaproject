/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.search;

import com.dimata.harisma.entity.search.SrcStructure;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmSrcStructure extends FRMHandler implements I_FRMInterface, I_FRMType {

    private SrcStructure srcStructure;
    public static final String FRM_NAME_SRC_STRUCTURE = "FRM_NAME_SRC_STRUCTURE";
    public static final int FRM_FIELD_RB_TIME = 0;
    public static final int FRM_FIELD_START_DATE = 1;
    public static final int FRM_FIELD_END_DATE = 2;
    public static final int FRM_FIELD_STRUCTURE_OP = 3;
    public static final int FRM_FIELD_STRUCTURE_SELECT = 4;
    public static final int FRM_FIELD_LEVEL_RANK = 5;
    public static final int FRM_FIELD_COMPANY_ID = 6;
    public static final int FRM_FIELD_DIVISION_ID = 7;
    public static final int FRM_FIELD_DEPARTMENT_ID = 8;
    public static final int FRM_FIELD_SECTION_ID = 9;
    public static final int FRM_FIELD_CHK_PHOTO = 10;
    public static final int FRM_FIELD_CHK_GAP = 11;
    
    public static String[] fieldNames = {
        "FRM_FIELD_RB_TIME",
        "FRM_FIELD_START_DATE",
        "FRM_FIELD_END_DATE",
        "FRM_FIELD_STRUCTURE_OP",
        "FRM_FIELD_STRUCTURE_SELECT",
        "FRM_FIELD_LEVEL_RANK",
        "FRM_FIELD_COMPANY_ID",
        "FRM_FIELD_DIVISION_ID",
        "FRM_FIELD_DEPARTMENT_ID",
        "FRM_FIELD_SECTION_ID",
        "FRM_FIELD_CHK_PHOTO",
        "FRM_FIELD_CHK_GAP"
    };
    public static int[] fieldTypes = {
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT
    };

    public FrmSrcStructure() {
    }

    public FrmSrcStructure(SrcStructure srcStructure) {
        this.srcStructure = srcStructure;
    }

    public FrmSrcStructure(HttpServletRequest request, SrcStructure srcStructure) {
        super(new FrmSrcStructure(srcStructure), request);
        this.srcStructure = srcStructure;
    }

    public String getFormName() {
        return FRM_NAME_SRC_STRUCTURE;
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

    public SrcStructure getEntityObject() {
        return srcStructure;
    }

    public void requestEntityObject(SrcStructure srcStructure) {
        try {
            this.requestParam();
            srcStructure.setRbTime(getInt(FRM_FIELD_RB_TIME));
            srcStructure.setStartDate(getString(FRM_FIELD_START_DATE));
            srcStructure.setEndDate(getString(FRM_FIELD_END_DATE));
            srcStructure.setStructureOp(getInt(FRM_FIELD_STRUCTURE_OP));
            srcStructure.setStructureSelect(getInt(FRM_FIELD_STRUCTURE_SELECT));
            srcStructure.setLevelRank(getInt(FRM_FIELD_LEVEL_RANK));
            srcStructure.setCompanyId(getLong(FRM_FIELD_COMPANY_ID));
            srcStructure.setDivisionId(getLong(FRM_FIELD_DIVISION_ID));
            srcStructure.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT_ID));
            srcStructure.setSectionId(getLong(FRM_FIELD_SECTION_ID));
            srcStructure.setChkPhoto(getInt(FRM_FIELD_CHK_PHOTO));
            srcStructure.setChkGap(getInt(FRM_FIELD_CHK_GAP));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
