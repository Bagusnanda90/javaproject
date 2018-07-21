/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

/**
 *
 * @author dimata005
 */

import com.dimata.qdep.entity.Entity;

public class OutSourceCostMaster extends Entity {

    private int showIndex = 0;
    private String costCode = "";
    private String costName = "";
    private int type = 0;
    private String note = "";
    private long parentOutSourceCostId = 0;

    public int getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(int showIndex) {
        this.showIndex = showIndex;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getParentOutSourceCostId() {
        return parentOutSourceCostId;
    }

    public void setParentOutSourceCostId(long parentOutSourceCostId) {
        this.parentOutSourceCostId = parentOutSourceCostId;
    }
}