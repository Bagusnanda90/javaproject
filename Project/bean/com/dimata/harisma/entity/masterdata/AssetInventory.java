/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;
import com.dimata.qdep.entity.Entity;
/**
 *
 * @author Gunadi
 */
public class AssetInventory extends Entity {

    private long aktivaId = 0;
    private String code = "";
    private String name = "";
    private int qty = 0;

    public long getAktivaId() {
        return aktivaId;
    }

    public void setAktivaId(long aktivaId) {
        this.aktivaId = aktivaId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}