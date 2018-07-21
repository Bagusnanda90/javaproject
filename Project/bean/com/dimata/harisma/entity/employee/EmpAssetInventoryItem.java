/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

/**
 *
 * @author Gunadi
 */
import com.dimata.qdep.entity.Entity;

public class EmpAssetInventoryItem extends Entity {

    private long empAssetInventoryId = 0;
    private long materialId = 0;
    private int qty = 0;
    private String detail = "";
    private String purpose = "";
    private long locationId = 0;

    public long getEmpAssetInventoryId() {
        return empAssetInventoryId;
    }

    public void setEmpAssetInventoryId(long empAssetInventoryId) {
        this.empAssetInventoryId = empAssetInventoryId;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * @return the locationId
     */
    public long getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }
}