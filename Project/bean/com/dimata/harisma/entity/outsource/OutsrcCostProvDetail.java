/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

import com.dimata.harisma.form.outsource.*;
import com.dimata.qdep.entity.Entity;

/**
 *
 * @author khirayinnura
 */
public class OutsrcCostProvDetail extends Entity {

    private long outsrcCostProviderId = 0;
    private long outsrcCostId = 0;
    private double costVal = 0;
    private String note = "";

    public long getOutsrcCostProviderId() {
        return outsrcCostProviderId;
    }

    public void setOutsrcCostProviderId(long outsrcCostProviderId) {
        this.outsrcCostProviderId = outsrcCostProviderId;
    }

    public long getOutsrcCostId() {
        return outsrcCostId;
    }

    public void setOutsrcCostId(long outsrcCostId) {
        this.outsrcCostId = outsrcCostId;
    }

    public double getCostVal() {
        return costVal;
    }

    public void setCostVal(double costVal) {
        this.costVal = costVal;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
