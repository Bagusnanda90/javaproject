/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;

/**
 *
 * @author Dimata 007
 */
import com.dimata.harisma.entity.masterdata.DivisionType;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmDivisionType extends FRMHandler implements I_FRMInterface, I_FRMType {

    private DivisionType entDivisionType;
    public static final String FRM_NAME_DIVISION_TYPE = "FRM_NAME_DIVISION_TYPE";
    public static final int FRM_FIELD_DIVISION_TYPE_ID = 0;
    public static final int FRM_FIELD_GROUP_TYPE = 1;
    public static final int FRM_FIELD_TYPE_NAME = 2;
    public static final int FRM_FIELD_DESCRIPTION = 3;
    public static final int FRM_FIELD_LEVEL = 4;
    public static String[] fieldNames = {
        "FRM_FIELD_DIVISION_TYPE_ID",
        "FRM_FIELD_GROUP_TYPE",
        "FRM_FIELD_TYPE_NAME",
        "FRM_FIELD_DESCRIPTION",
        "FRM_FIELD_LEVEL"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT
    };

    public FrmDivisionType() {
    }

    public FrmDivisionType(DivisionType entDivisionType) {
        this.entDivisionType = entDivisionType;
    }

    public FrmDivisionType(HttpServletRequest request, DivisionType entDivisionType) {
        super(new FrmDivisionType(entDivisionType), request);
        this.entDivisionType = entDivisionType;
    }

    public String getFormName() {
        return FRM_NAME_DIVISION_TYPE;
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

    public DivisionType getEntityObject() {
        return entDivisionType;
    }

    public void requestEntityObject(DivisionType entDivisionType) {
        try {
            this.requestParam();
            entDivisionType.setGroupType(getInt(FRM_FIELD_GROUP_TYPE));
            entDivisionType.setTypeName(getString(FRM_FIELD_TYPE_NAME));
            entDivisionType.setDescription(getString(FRM_FIELD_DESCRIPTION));
            entDivisionType.setLevel(getInt(FRM_FIELD_LEVEL));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
