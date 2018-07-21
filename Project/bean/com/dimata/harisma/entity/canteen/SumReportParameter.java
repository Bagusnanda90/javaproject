/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.canteen;

import java.util.Date;

/**
 *
 * @author dimata
 */
public class SumReportParameter {

    
    //public String drawListCanteenVisitation(Date dateStrt, Date dateEnd, Vector dataOfReport,String schIdx[],double nominal[])
    private Date startDt;
    private Date endDt;
    private String schIdx[];
    private double nominal[];

    /**
     * @return the startDt
     */
    public Date getStartDt() {
        return startDt;
    }

    /**
     * @param startDt the startDt to set
     */
    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    /**
     * @return the endDt
     */
    public Date getEndDt() {
        return endDt;
    }

    /**
     * @param endDt the endDt to set
     */
    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    /**
     * @return the schIdx
     */
    public String[] getSchIdx() {
        return schIdx;
    }

    /**
     * @param schIdx the schIdx to set
     */
    public void setSchIdx(String[] schIdx) {
        this.schIdx = schIdx;
    }

    /**
     * @return the nominal
     */
    public double[] getNominal() {
        return nominal;
    }

    /**
     * @param nominal the nominal to set
     */
    public void setNominal(double[] nominal) {
        this.nominal = nominal;
    }



}
