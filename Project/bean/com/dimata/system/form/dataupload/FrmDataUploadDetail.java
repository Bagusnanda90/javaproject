/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.system.form.dataupload;

import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import com.dimata.system.entity.dataupload.DataUploadDetail;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class FrmDataUploadDetail extends FRMHandler implements I_FRMInterface, I_FRMType {

    private DataUploadDetail entDataUploadDetail;
    public static final String FRM_NAME_DSJ_DATA_DETAIL = "FRM_NAME_DSJ_DATA_DETAIL";
    public static final int FRM_FIELD_DATA_DETAIL_ID = 0;
    public static final int FRM_FIELD_DATA_DETAIL_TITLE = 1;
    public static final int FRM_FIELD_DATA_DETAIL_DESC = 2;
    public static final int FRM_FIELD_DATA_MAIN_ID = 3;
    public static final int FRM_FIELD_FILENAME = 4;
    public static String[] fieldNames = {
        "FRM_FIELD_DATA_DETAIL_ID",
        "FRM_FIELD_DATA_DETAIL_TITLE",
        "FRM_FIELD_DATA_DETAIL_DESC",
        "FRM_FIELD_DATA_MAIN_ID",
        "FRM_FIELD_FILENAME"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING
    };

    public FrmDataUploadDetail() {
    }

    public FrmDataUploadDetail(DataUploadDetail entDataUploadDetail) {
        this.entDataUploadDetail = entDataUploadDetail;
    }

    public FrmDataUploadDetail(HttpServletRequest request, DataUploadDetail entDataUploadDetail) {
        super(new FrmDataUploadDetail(entDataUploadDetail), request);
        this.entDataUploadDetail = entDataUploadDetail;
    }

    public String getFormName() {
        return FRM_NAME_DSJ_DATA_DETAIL;
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

    public DataUploadDetail getEntityObject() {
        return entDataUploadDetail;
    }

    public void requestEntityObject(DataUploadDetail entDataUploadDetail) {
        try {
            this.requestParam();
           // entDataUploadDetail.setDataDetailId(getLong(FRM_FIELD_DATA_DETAIL_ID));
            entDataUploadDetail.setDataDetailTitle(getString(FRM_FIELD_DATA_DETAIL_TITLE));
            entDataUploadDetail.setDataDetailDesc(getString(FRM_FIELD_DATA_DETAIL_DESC));
            entDataUploadDetail.setDataMainId(getLong(FRM_FIELD_DATA_MAIN_ID));
            entDataUploadDetail.setFilename(getString(FRM_FIELD_FILENAME));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
