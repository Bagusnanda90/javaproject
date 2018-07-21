/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

/**
 *
 * @author Dimata 007
 */
import com.dimata.harisma.entity.masterdata.MappingPosition;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;

public class FrmMappingPosition extends FRMHandler implements I_FRMInterface, I_FRMType {

    private MappingPosition entMappingPosition;
    public static final String FRM_NAME_MAPPING_POSITION = "FRM_NAME_MAPPING_POSITION";
    public static final int FRM_FIELD_MAPPING_POSITION_ID = 0;
    public static final int FRM_FIELD_UP_POSITION_ID = 1;
    public static final int FRM_FIELD_DOWN_POSITION_ID = 2;
    public static final int FRM_FIELD_START_DATE = 3;
    public static final int FRM_FIELD_END_DATE = 4;
    public static final int FRM_FIELD_TYPE_OF_LINK = 5;
    public static final int FRM_FIELD_TEMPLATE_ID = 6;
    public static String[] fieldNames = {
        "FRM_FIELD_MAPPING_POSITION_ID",
        "FRM_FIELD_UP_POSITION_ID",
        "FRM_FIELD_DOWN_POSITION_ID",
        "FRM_FIELD_START_DATE",
        "FRM_FIELD_END_DATE",
        "FRM_FIELD_TYPE_OF_LINK",
        "FRM_FIELD_TEMPLATE_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG
    };

    public FrmMappingPosition() {
    }

    public FrmMappingPosition(MappingPosition entMappingPosition) {
        this.entMappingPosition = entMappingPosition;
    }

    public FrmMappingPosition(HttpServletRequest request, MappingPosition entMappingPosition) {
        super(new FrmMappingPosition(entMappingPosition), request);
        this.entMappingPosition = entMappingPosition;
    }

    public String getFormName() {
        return FRM_NAME_MAPPING_POSITION;
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

    public MappingPosition getEntityObject() {
        return entMappingPosition;
    }

    public void requestEntityObject(MappingPosition entMappingPosition) {
        try {
            this.requestParam();
            entMappingPosition.setUpPositionId(getLong(FRM_FIELD_UP_POSITION_ID));
            entMappingPosition.setDownPositionId(getLong(FRM_FIELD_DOWN_POSITION_ID));
            entMappingPosition.setStartDate(Date.valueOf(getString(FRM_FIELD_START_DATE)));
            entMappingPosition.setEndDate(Date.valueOf(getString(FRM_FIELD_END_DATE)));
            entMappingPosition.setTypeOfLink(getInt(FRM_FIELD_TYPE_OF_LINK));
            entMappingPosition.setTemplateId(getLong(FRM_FIELD_TEMPLATE_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
