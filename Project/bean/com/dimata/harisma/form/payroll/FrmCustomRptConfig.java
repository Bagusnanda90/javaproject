/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.payroll;

/**
 * Description : Date : 2015-03-26
 *
 * @author Hendra Putu
 */
import com.dimata.harisma.entity.payroll.CustomRptConfig;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmCustomRptConfig extends FRMHandler implements I_FRMInterface, I_FRMType {

    private CustomRptConfig entCustomRptConfig;
    public static final String FRM_NAME_CUSTOM_RPT_CONFIG = "FRM_NAME_CUSTOM_RPT_CONFIG";
    public static final int FRM_FIELD_RPT_CONFIG_ID = 0;
    public static final int FRM_FIELD_RPT_CONFIG_SHOW_IDX = 1;
    public static final int FRM_FIELD_RPT_CONFIG_DATA_TYPE = 2;
    public static final int FRM_FIELD_RPT_CONFIG_DATA_GROUP = 3;
    public static final int FRM_FIELD_RPT_CONFIG_TABLE_NAME = 4;
    public static final int FRM_FIELD_RPT_CONFIG_FIELD_NAME = 5;
    public static final int FRM_FIELD_RPT_CONFIG_FIELD_HEADER = 6;
    public static final int FRM_FIELD_RPT_CONFIG_FIELD_COLOUR = 7;
    public static final int FRM_FIELD_RPT_CONFIG_TABLE_PRIORITY = 8;
    public static final int FRM_FIELD_RPT_MAIN_ID = 9;
    public static String[] fieldNames = {
        "FRM_FIELD_RPT_CONFIG_ID",
        "FRM_FIELD_RPT_CONFIG_SHOW_IDX",
        "FRM_FIELD_RPT_CONFIG_DATA_TYPE",
        "FRM_FIELD_RPT_CONFIG_DATA_GROUP",
        "FRM_FIELD_RPT_CONFIG_TABLE_NAME",
        "FRM_FIELD_RPT_CONFIG_FIELD_NAME",
        "FRM_FIELD_RPT_CONFIG_FIELD_HEADER",
        "FRM_FIELD_RPT_CONFIG_FIELD_COLOUR",
        "FRM_FIELD_RPT_CONFIG_TABLE_PRIORITY",
        "FRM_FIELD_RPT_MAIN_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG
    };

    public FrmCustomRptConfig() {
    }

    public FrmCustomRptConfig(CustomRptConfig entCustomRptConfig) {
        this.entCustomRptConfig = entCustomRptConfig;
    }

    public FrmCustomRptConfig(HttpServletRequest request, CustomRptConfig entCustomRptConfig) {
        super(new FrmCustomRptConfig(entCustomRptConfig), request);
        this.entCustomRptConfig = entCustomRptConfig;
    }

    public String getFormName() {
        return FRM_NAME_CUSTOM_RPT_CONFIG;
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

    public CustomRptConfig getEntityObject() {
        return entCustomRptConfig;
    }

    public void requestEntityObject(CustomRptConfig entCustomRptConfig) {
        try {
            this.requestParam();
            entCustomRptConfig.setRptConfigShowIdx(getInt(FRM_FIELD_RPT_CONFIG_SHOW_IDX));
            entCustomRptConfig.setRptConfigDataType(getInt(FRM_FIELD_RPT_CONFIG_DATA_TYPE));
            entCustomRptConfig.setRptConfigDataGroup(getInt(FRM_FIELD_RPT_CONFIG_DATA_GROUP));
            entCustomRptConfig.setRptConfigTableName(getString(FRM_FIELD_RPT_CONFIG_TABLE_NAME));
            entCustomRptConfig.setRptConfigFieldName(getString(FRM_FIELD_RPT_CONFIG_FIELD_NAME));
            entCustomRptConfig.setRptConfigFieldHeader(getString(FRM_FIELD_RPT_CONFIG_FIELD_HEADER));
            entCustomRptConfig.setRptConfigFieldColour(getString(FRM_FIELD_RPT_CONFIG_FIELD_COLOUR));
            entCustomRptConfig.setRptConfigTablePriority(getInt(FRM_FIELD_RPT_CONFIG_TABLE_PRIORITY));
            entCustomRptConfig.setRptMainId(getLong(FRM_FIELD_RPT_MAIN_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}