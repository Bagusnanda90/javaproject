/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.employee;

import com.dimata.harisma.entity.employee.EmpAssetInventoryItem;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Gunadi
 */
public class FrmEmpAssetInventoryItem extends FRMHandler implements I_FRMInterface, I_FRMType {

    private EmpAssetInventoryItem entEmpAssetInventoryItem;
    public static final String FRM_NAME_EMP_ASSET_INVENTORY_ITEM = "FRM_NAME_EMP_ASSET_INVENTORY_ITEM";
    public static final int FRM_FIELD_EMP_ASSET_INVENTORY_ITEM_ID = 0;
    public static final int FRM_FIELD_EMP_ASSET_INVENTORY_ID = 1;
    public static final int FRM_FIELD_MATERIAL_ID = 2;
    public static final int FRM_FIELD_QTY = 3;
    public static final int FRM_FIELD_DETAIL = 4;
    public static final int FRM_FIELD_PURPOSE = 5;
    public static final int FRM_FIELD_LOCATION_ID = 6;
    public static String[] fieldNames = {
        "FRM_FIELD_EMP_ASSET_INVENTORY_ITEM_ID",
        "FRM_FIELD_EMP_ASSET_INVENTORY_ID",
        "FRM_FIELD_MATERIAL_ID",
        "FRM_FIELD_QTY",
        "FRM_FIELD_DETAIL",
        "FRM_FIELD_PURPOSE",
        "FRM_FIELD_LOCATION_ID"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG
    };

    public FrmEmpAssetInventoryItem() {
    }

    public FrmEmpAssetInventoryItem(EmpAssetInventoryItem entEmpAssetInventoryItem) {
        this.entEmpAssetInventoryItem = entEmpAssetInventoryItem;
    }

    public FrmEmpAssetInventoryItem(HttpServletRequest request, EmpAssetInventoryItem entEmpAssetInventoryItem) {
        super(new FrmEmpAssetInventoryItem(entEmpAssetInventoryItem), request);
        this.entEmpAssetInventoryItem = entEmpAssetInventoryItem;
    }

    public String getFormName() {
        return FRM_NAME_EMP_ASSET_INVENTORY_ITEM;
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

    public EmpAssetInventoryItem getEntityObject() {
        return entEmpAssetInventoryItem;
    }

    public void requestEntityObject(EmpAssetInventoryItem entEmpAssetInventoryItem) {
        try {
            this.requestParam();
            entEmpAssetInventoryItem.setEmpAssetInventoryId(getLong(FRM_FIELD_EMP_ASSET_INVENTORY_ID));
            entEmpAssetInventoryItem.setMaterialId(getLong(FRM_FIELD_MATERIAL_ID));
            entEmpAssetInventoryItem.setQty(getInt(FRM_FIELD_QTY));
            entEmpAssetInventoryItem.setDetail(getString(FRM_FIELD_DETAIL));
            entEmpAssetInventoryItem.setPurpose(getString(FRM_FIELD_PURPOSE));
            entEmpAssetInventoryItem.setLocationId(getLong(FRM_FIELD_LOCATION_ID));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}