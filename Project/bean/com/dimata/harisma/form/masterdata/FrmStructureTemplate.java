/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

/**
 *
 * @author Dimata 007
 */
import com.dimata.harisma.entity.masterdata.StructureTemplate;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;

public class FrmStructureTemplate extends FRMHandler implements I_FRMInterface, I_FRMType {

    private StructureTemplate entStructureTemplate;
    public static final String FRM_NAME_STRUCTURE_TEMPLATE = "FRM_NAME_STRUCTURE_TEMPLATE";
    public static final int FRM_FIELD_TEMPLATE_ID = 0;
    public static final int FRM_FIELD_TEMPLATE_NAME = 1;
    public static final int FRM_FIELD_TEMPLATE_DESC = 2;
    public static final int FRM_FIELD_START_DATE = 3;
    public static final int FRM_FIELD_END_DATE = 4;
    public static String[] fieldNames = {
        "FRM_FIELD_TEMPLATE_ID",
        "FRM_FIELD_TEMPLATE_NAME",
        "FRM_FIELD_TEMPLATE_DESC",
        "FRM_FIELD_START_DATE",
        "FRM_FIELD_END_DATE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING
    };

    public FrmStructureTemplate() {
    }

    public FrmStructureTemplate(StructureTemplate entStructureTemplate) {
        this.entStructureTemplate = entStructureTemplate;
    }

    public FrmStructureTemplate(HttpServletRequest request, StructureTemplate entStructureTemplate) {
        super(new FrmStructureTemplate(entStructureTemplate), request);
        this.entStructureTemplate = entStructureTemplate;
    }

    public String getFormName() {
        return FRM_NAME_STRUCTURE_TEMPLATE;
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

    public StructureTemplate getEntityObject() {
        return entStructureTemplate;
    }

    public void requestEntityObject(StructureTemplate entStructureTemplate) {
        try {
            this.requestParam();
            entStructureTemplate.setTemplateName(getString(FRM_FIELD_TEMPLATE_NAME));
            entStructureTemplate.setTemplateDesc(getString(FRM_FIELD_TEMPLATE_DESC));
            entStructureTemplate.setStartDate(Date.valueOf(getString(FRM_FIELD_START_DATE)));
            entStructureTemplate.setEndDate(Date.valueOf(getString(FRM_FIELD_END_DATE)));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}