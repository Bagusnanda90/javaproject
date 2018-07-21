/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.system.form.dataupload;

import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import com.dimata.system.entity.dataupload.DataUploadMain;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class FrmDataUploadMain extends FRMHandler implements I_FRMInterface, I_FRMType {

    private DataUploadMain entDataUploadMain;
    public static final String FRM_NAME_DSJ_DATA_MAIN = "FRM_NAME_DSJ_DATA_MAIN";
    public static final int FRM_FIELD_DATA_MAIN_ID = 0;
    public static final int FRM_FIELD_OBJECT_ID = 1;
    public static final int FRM_FIELD_OBJECT_CLASS = 2;
    public static final int FRM_FIELD_DATA_MAIN_TITLE = 3;
    public static final int FRM_FIELD_DATA_MAIN_DESC = 4;
    public static final int FRM_FIELD_DATA_GROUP_ID = 5;
    public static String[] fieldNames = {
        "FRM_FIELD_DATA_MAIN_ID",
        "FRM_FIELD_OBJECT_ID",
        "FRM_FIELD_OBJECT_CLASS",
        "FRM_FIELD_DATA_MAIN_TITLE",
        "FRM_FIELD_DATA_MAIN_DESC",
        "FRM_FIELD_DATA_GROUP_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG
    };

    public FrmDataUploadMain() {
    }

    public FrmDataUploadMain(DataUploadMain entDataUploadMain) {
        this.entDataUploadMain = entDataUploadMain;
    }

    public FrmDataUploadMain(HttpServletRequest request, DataUploadMain entDataUploadMain) {
        super(new FrmDataUploadMain(entDataUploadMain), request);
        this.entDataUploadMain = entDataUploadMain;
    }

    public String getFormName() {
        return FRM_NAME_DSJ_DATA_MAIN;
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

    public DataUploadMain getEntityObject() {
        return entDataUploadMain;
    }

    public void requestEntityObject(DataUploadMain entDataUploadMain) {
        try {
            this.requestParam();
           // entDataUploadMain.setDataMainId(getLong(FRM_FIELD_DATA_MAIN_ID));
            entDataUploadMain.setObjectId(getLong(FRM_FIELD_OBJECT_ID));
            entDataUploadMain.setObjectClass(getString(FRM_FIELD_OBJECT_CLASS));
            entDataUploadMain.setDataMainTitle(getString(FRM_FIELD_DATA_MAIN_TITLE));
            entDataUploadMain.setDataMainDesc(getString(FRM_FIELD_DATA_MAIN_DESC));
            entDataUploadMain.setDataGroupId(getLong(FRM_FIELD_DATA_GROUP_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
