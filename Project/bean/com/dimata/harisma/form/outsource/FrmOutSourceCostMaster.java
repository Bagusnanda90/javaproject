/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.outsource;

import com.dimata.harisma.entity.outsource.OutSourceCostMaster;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author dimata005
 */
public class FrmOutSourceCostMaster extends FRMHandler implements I_FRMInterface, I_FRMType {
    private OutSourceCostMaster entOutSourceCostMaster;
    public static final String FRM_NAME_OUTSRC_COST = "FRM_NAME_OUTSRC_COST_MASTER";
    public static final int FRM_FIELD_OUTSRC_COST_ID = 0;
    public static final int FRM_FIELD_SHOW_INDEX = 1;
    public static final int FRM_FIELD_COST_CODE = 2;
    public static final int FRM_FIELD_COST_NAME = 3;
    public static final int FRM_FIELD_TYPE = 4;
    public static final int FRM_FIELD_NOTE = 5;
    public static final int FRM_FIELD_PARENT_OUTSRC_COST_ID = 6;
    public static String[] fieldNames = {
        "FRM_FIELD_OUTSRC_COST_ID",
        "FRM_FIELD_SHOW_INDEX",
        "FRM_FIELD_COST_CODE",
        "FRM_FIELD_COST_NAME",
        "FRM_FIELD_TYPE",
        "FRM_FIELD_NOTE",
        "FRM_FIELD_PARENT_OUTSRC_COST_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_LONG
    };

    public FrmOutSourceCostMaster() {
    }

    public FrmOutSourceCostMaster(OutSourceCostMaster entOutSourceCostMaster) {
        this.entOutSourceCostMaster = entOutSourceCostMaster;
    }

    public FrmOutSourceCostMaster(HttpServletRequest request, OutSourceCostMaster entOutSourceCostMaster) {
        super(new FrmOutSourceCostMaster(entOutSourceCostMaster), request);
        this.entOutSourceCostMaster = entOutSourceCostMaster;
    }

    public String getFormName() {
        return FRM_NAME_OUTSRC_COST;
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

    public OutSourceCostMaster getEntityObject() {
        return entOutSourceCostMaster;
    }

    public void requestEntityObject(OutSourceCostMaster entOutSourceCostMaster) {
        try {
            this.requestParam();
            entOutSourceCostMaster.setShowIndex(getInt(FRM_FIELD_SHOW_INDEX));
            entOutSourceCostMaster.setCostCode(getString(FRM_FIELD_COST_CODE));
            entOutSourceCostMaster.setCostName(getString(FRM_FIELD_COST_NAME));
            entOutSourceCostMaster.setType(getInt(FRM_FIELD_TYPE));
            entOutSourceCostMaster.setNote(getString(FRM_FIELD_NOTE));
            entOutSourceCostMaster.setParentOutSourceCostId(getLong(FRM_FIELD_PARENT_OUTSRC_COST_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
