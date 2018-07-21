/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class SrcObject {
    private long divisiId = 0;
    private long providerId = 0;
    private String payPeriodId = "";
    private Vector payPeriodIdV = new Vector();

    /**
     * @return the divisiId
     */
    public long getDivisiId() {
        return divisiId;
    }

    /**
     * @param divisiId the divisiId to set
     */
    public void setDivisiId(long divisiId) {
        this.divisiId = divisiId;
    }

    /**
     * @return the providerId
     */
    public long getProviderId() {
        return providerId;
    }

    /**
     * @param providerId the providerId to set
     */
    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }

    /**
     * @return the payPeriodId
     */
    public String getPayPeriodId() {
        return payPeriodId;
    }

    /**
     * @param payPeriodId the payPeriodId to set
     */
    public void setPayPeriodId(String payPeriodId) {
        this.payPeriodId = payPeriodId;
    }

    /**
     * @return the payPeriodIdV
     */
    public Vector getPayPeriodIdV() {
        return payPeriodIdV;
    }

    /**
     * @param payPeriodIdV the payPeriodIdV to set
     */
    public void setPayPeriodIdV(Vector payPeriodIdV) {
        this.payPeriodIdV = payPeriodIdV;
    }
    
}
