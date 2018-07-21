/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.system.form.dataupload;

import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import com.dimata.system.entity.dataupload.DataUploadGroup;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class FrmDataUploadGroup extends FRMHandler implements I_FRMInterface, I_FRMType {

    private DataUploadGroup entDataUploadGroup;
    public static final String FRM_NAME_DSJ_DATA_GROUP = "FRM_NAME_DSJ_DATA_GROUP";
    public static final int FRM_FIELD_DATA_GROUP_ID = 0;
    public static final int FRM_FIELD_DATA_GROUP_TITLE = 1;
    public static final int FRM_FIELD_DATA_GROUP_DESC = 2;
    public static final int FRM_FIELD_DATA_GROUP_TIPE = 3;
    public static final int FRM_FIELD_SYSTEM_NAME = 4;
    public static final int FRM_FIELD_MODUL = 5;
    public static String[] fieldNames = {
        "FRM_FIELD_DATA_GROUP_ID",
        "FRM_FIELD_DATA_GROUP_TITLE",
        "FRM_FIELD_DATA_GROUP_DESC",
        "FRM_FIELD_DATA_GROUP_TIPE",
        "FRM_FIELD_SYSTEM_NAME",
        "FRM_FIELD_MODUL"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT
    };

    public FrmDataUploadGroup() {
    }

    public FrmDataUploadGroup(DataUploadGroup entDataUploadGroup) {
        this.entDataUploadGroup = entDataUploadGroup;
    }

    public FrmDataUploadGroup(HttpServletRequest request, DataUploadGroup entDataUploadGroup) {
        super(new FrmDataUploadGroup(entDataUploadGroup), request);
        this.entDataUploadGroup = entDataUploadGroup;
    }

    public String getFormName() {
        return FRM_NAME_DSJ_DATA_GROUP;
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

    public DataUploadGroup getEntityObject() {
        return entDataUploadGroup;
    }

    public void requestEntityObject(DataUploadGroup entDataUploadGroup) {
        try {
            this.requestParam();
            entDataUploadGroup.setDataGroupId(getLong(FRM_FIELD_DATA_GROUP_ID));
            entDataUploadGroup.setDataGroupTitle(getString(FRM_FIELD_DATA_GROUP_TITLE));
            entDataUploadGroup.setDataGroupDesc(getString(FRM_FIELD_DATA_GROUP_DESC));
            entDataUploadGroup.setDataGroupTipe(getInt(FRM_FIELD_DATA_GROUP_TIPE));
            entDataUploadGroup.setSystemName(getInt(FRM_FIELD_SYSTEM_NAME));
            entDataUploadGroup.setModul(getInt(FRM_FIELD_MODUL));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
