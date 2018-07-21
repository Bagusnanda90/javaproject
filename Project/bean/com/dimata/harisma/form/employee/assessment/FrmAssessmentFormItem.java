/* 
 * Form Name  	:  FrmAssessmentFormItem.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya 
 * @version  	: 01 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.form.employee.assessment;

/* java package */
import javax.servlet.http.HttpServletRequest;

/* qdep package */
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;

/* project package */

import com.dimata.harisma.entity.employee.assessment.AssessmentFormItem;
import com.dimata.harisma.entity.employee.assessment.AssessmentFormItem;

public class FrmAssessmentFormItem extends FRMHandler implements I_FRMInterface, I_FRMType {

    private AssessmentFormItem assessmentFormItem;
    public static final String FRM_ASS_FORM_ITEM = "FRM_ASS_FORM_ITEM";
    public static final int FRM_FIELD_ASS_FORM_ITEM_ID = 0;
    public static final int FRM_FIELD_ASS_FORM_SECTION_ID = 1;
    public static final int FRM_FIELD_TITLE = 2;
    public static final int FRM_FIELD_TITLE_L2 = 3;
    public static final int FRM_FIELD_ITEM_POIN_1 = 4;
    public static final int FRM_FIELD_ITEM_POIN_2 = 5;
    public static final int FRM_FIELD_ITEM_POIN_3 = 6;
    public static final int FRM_FIELD_ITEM_POIN_4 = 7;
    public static final int FRM_FIELD_ITEM_POIN_5 = 8;
    public static final int FRM_FIELD_ITEM_POIN_6 = 9;
    public static final int FRM_FIELD_TYPE = 10;
    public static final int FRM_FIELD_ORDER_NUMBER = 11;
    public static final int FRM_FIELD_NUMBER = 12;
    public static final int FRM_FIELD_PAGE = 13;
    public static final int FRM_FIELD_HEIGHT = 14;
    public static final int FRM_FIELD_KPI_LIST_ID = 15;
    public static final int FRM_FIELD_WEIGHT_POINT = 16;
    public static final int FRM_FIELD_KPI_TARGET = 17;
    public static final int FRM_FIELD_KPI_UNIT = 18;
    public static final int FRM_FIELD_KPI_NOTE = 19;
    public static String[] fieldNames = {
        "FRM_FIELD_ASS_FORM_ITEM_ID",
        "FRM_FIELD_ASS_FORM_SECTION_ID",
        "FRM_FIELD_TITLE",
        "FRM_FIELD_TITLE_L2",
        "FRM_FIELD_ITEM_POIN_1",
        "FRM_FIELD_ITEM_POIN_2",
        "FRM_FIELD_ITEM_POIN_3",
        "FRM_FIELD_ITEM_POIN_4",
        "FRM_FIELD_ITEM_POIN_5",
        "FRM_FIELD_ITEM_POIN_6",
        "FRM_FIELD_TYPE",
        "FRM_FIELD_ORDER_NUMBER",
        "FRM_FIELD_NUMBER",
        "FRM_FIELD_PAGE",
        "FRM_FIELD_HEIGHT",
        "FRM_FIELD_KPI_LIST_ID",
        "FRM_FIELD_WEIGHT_POINT",
        "FRM_FIELD_KPI_TARGET",
        "FRM_FIELD_KPI_UNIT",
        "FRM_FIELD_KPI_NOTE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT + ENTRY_REQUIRED,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_FLOAT + ENTRY_REQUIRED,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_STRING
        
    };

    public FrmAssessmentFormItem() {
    }

    public FrmAssessmentFormItem(AssessmentFormItem assessmentFormItem) {
        this.assessmentFormItem = assessmentFormItem;
    }

    public FrmAssessmentFormItem(HttpServletRequest request, AssessmentFormItem assessmentFormItem) {
        super(new FrmAssessmentFormItem(assessmentFormItem), request);
        this.assessmentFormItem = assessmentFormItem;
    }

    public String getFormName() {
        return FRM_ASS_FORM_ITEM;
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

    public AssessmentFormItem getEntityObject() {
        return assessmentFormItem;
    }

    public void requestEntityObject(AssessmentFormItem assessmentFormItem) {
        try {
            this.requestParam();
            assessmentFormItem.setAssFormSection(getLong(FRM_FIELD_ASS_FORM_SECTION_ID));
            assessmentFormItem.setTitle(getString(FRM_FIELD_TITLE));
            assessmentFormItem.setTitle_L2(getString(FRM_FIELD_TITLE_L2));
            assessmentFormItem.setItemPoin1(getString(FRM_FIELD_ITEM_POIN_1));
            assessmentFormItem.setItemPoin2(getString(FRM_FIELD_ITEM_POIN_2));
            assessmentFormItem.setItemPoin3(getString(FRM_FIELD_ITEM_POIN_3));
            assessmentFormItem.setItemPoin4(getString(FRM_FIELD_ITEM_POIN_4));
            assessmentFormItem.setItemPoin5(getString(FRM_FIELD_ITEM_POIN_5));
            assessmentFormItem.setItemPoin6(getString(FRM_FIELD_ITEM_POIN_6));
            assessmentFormItem.setType(getInt(FRM_FIELD_TYPE));
            assessmentFormItem.setOrderNumber(getInt(FRM_FIELD_ORDER_NUMBER));
            assessmentFormItem.setNumber(getInt(FRM_FIELD_NUMBER));
            assessmentFormItem.setPage(getInt(FRM_FIELD_PAGE));
            assessmentFormItem.setHeight(getInt(FRM_FIELD_HEIGHT));
            assessmentFormItem.setKpiListId(getLong(FRM_FIELD_KPI_LIST_ID));
            assessmentFormItem.setWeightPoint(getFloat(FRM_FIELD_WEIGHT_POINT));
            assessmentFormItem.setKpiTarget(getFloat(FRM_FIELD_KPI_TARGET));
            assessmentFormItem.setKpiUnit(getString(FRM_FIELD_KPI_UNIT));
            assessmentFormItem.setKpiNote(getString(FRM_FIELD_KPI_NOTE));
            
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
