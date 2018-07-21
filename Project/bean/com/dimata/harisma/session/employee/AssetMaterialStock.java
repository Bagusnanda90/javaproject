/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.employee;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Gunadi
 */
public class AssetMaterialStock extends Entity {

    private long materialId = 0;
    private String materialName = "";
    private long materialCategoryId = 0;
    private String materialCategory = "";
    private long materialLocationId = 0;
    private String materialLocation = "";
    private int materialQty = 0;

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public long getMaterialCategoryId() {
        return materialCategoryId;
    }

    public void setMaterialCategoryId(long materialCategoryId) {
        this.materialCategoryId = materialCategoryId;
    }

    public String getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(String materialCategory) {
        this.materialCategory = materialCategory;
    }

    public long getMaterialLocationId() {
        return materialLocationId;
    }

    public void setMaterialLocationId(long materialLocationId) {
        this.materialLocationId = materialLocationId;
    }

    public String getMaterialLocation() {
        return materialLocation;
    }

    public void setMaterialLocation(String materialLocation) {
        this.materialLocation = materialLocation;
    }

    public int getMaterialQty() {
        return materialQty;
    }

    public void setMaterialQty(int materialQty) {
        this.materialQty = materialQty;
    }
}