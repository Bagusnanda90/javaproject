/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.outsource;

import com.dimata.harisma.entity.outsource.OutsrcCostProv;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author khirayinnura
 */
public class FrmOutsrcCostProv extends FRMHandler implements I_FRMInterface, I_FRMType {
    private OutsrcCostProv entOutsrcCostProv;
    public static final String FRM_NAME_OUTSRC_COST_PROV = "FRM_NAME_OUTSRC_COST_PROV";
    public static final int FRM_FIELD_OUTSRC_COST_PROVIDER_ID = 0;
    public static final int FRM_FIELD_POSITION_ID = 1;
    public static final int FRM_FIELD_PROVIDER_ID = 2;
    public static final int FRM_FIELD_NUMBER_OF_PERSON = 3;
    public static final int FRM_FIELD_OUTSOURCE_COST_ID = 4;
    public static String[] fieldNames = {
        "FRM_FIELD_OUTSRC_COST_PROVIDER_ID",
        "FRM_FIELD_POSITION_ID",
        "FRM_FIELD_PROVIDER_ID",
        "FRM_FIELD_NUMBER_OF_PERSON",
        "FRM_FIELD_OUTSOURCE_COST_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_LONG
    };

    public FrmOutsrcCostProv() {
    }

    public FrmOutsrcCostProv(OutsrcCostProv entOutsrcCostProv) {
        this.entOutsrcCostProv = entOutsrcCostProv;
    }

    public FrmOutsrcCostProv(HttpServletRequest request, OutsrcCostProv entOutsrcCostProv) {
        super(new FrmOutsrcCostProv(entOutsrcCostProv), request);
        this.entOutsrcCostProv = entOutsrcCostProv;
    }

    public String getFormName() {
        return FRM_NAME_OUTSRC_COST_PROV;
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

    public OutsrcCostProv getEntityObject() {
        return entOutsrcCostProv;
    }

    public void requestEntityObject(OutsrcCostProv entOutsrcCostProv) {
        try {
            this.requestParam();
            entOutsrcCostProv.setPositionId(getLong(FRM_FIELD_POSITION_ID));
            entOutsrcCostProv.setProviderId(getLong(FRM_FIELD_PROVIDER_ID));
            entOutsrcCostProv.setNumberOfPerson(getInt(FRM_FIELD_NUMBER_OF_PERSON));
            entOutsrcCostProv.setOutsourceCostId(getLong(FRM_FIELD_OUTSOURCE_COST_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
