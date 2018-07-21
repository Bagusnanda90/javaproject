/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

/**
 *
 * @author Acer
 */
import com.dimata.harisma.entity.masterdata.SubSection;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmSubSection extends FRMHandler implements I_FRMInterface, I_FRMType {

    private SubSection entSubSection;
    public static final String FRM_NAME_SUB_SECTION = "FRM_NAME_SUBSECTION";
    public static final int FRM_FIELD_SUB_SECTION_ID = 0;
    public static final int FRM_FIELD_SECTION_ID = 1;
    public static final int FRM_FIELD_SUB_SECTION = 2;
    public static final int FRM_FIELD_DESCRIPTION = 3;
    public static final int FRM_FIELD_VALID_STATUS = 4;
    public static final int FRM_FIELD_VALID_START = 5;
    public static final int FRM_FIELD_VALID_END = 6;

    public static String[] fieldNames = {
        "FRM_FIELD_SUB_SECTION_ID",
        "FRM_FIELD_SECTION_ID",
        "FRM_FIELD_SUB_SECTION",
        "FRM_FIELD_DESCRIPTION",
        "FRM_FIELD_VALID_STATUS",
        "FRM_FIELD_VALID_START",
        "FRM_FIELD_VALID_END"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_DATE,
        TYPE_DATE
    };

    public FrmSubSection() {
    }

    public FrmSubSection(SubSection entSubSection) {
        this.entSubSection = entSubSection;
    }

    public FrmSubSection(HttpServletRequest request, SubSection entSubSection) {
        super(new FrmSubSection(entSubSection), request);
        this.entSubSection = entSubSection;
    }

    public String getFormName() {
        return FRM_NAME_SUB_SECTION;
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

    public SubSection getEntityObject() {
        return entSubSection;
    }

    public void requestEntityObject(SubSection entSubSection) {
        try {
            this.requestParam();
            entSubSection.setSectionId(getLong(FRM_FIELD_SECTION_ID));
            entSubSection.setSubSection(getString(FRM_FIELD_SUB_SECTION));
            entSubSection.setDescription(getString(FRM_FIELD_DESCRIPTION));
            entSubSection.setValidStatus(getInt(FRM_FIELD_VALID_STATUS));
            entSubSection.setValidStart(getDate(FRM_FIELD_VALID_START));
            entSubSection.setValidEnd(getDate(FRM_FIELD_VALID_END));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
