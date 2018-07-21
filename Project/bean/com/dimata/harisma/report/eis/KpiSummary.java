/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.eis;

/**
 *
 * @author Kartika
 */
public class KpiSummary {
    private long companyId = 0;
    private long kpiID =0;
    private String kpiTitle ="";
    private double achive=0.0;
    private double target=0.0;
    private String valType="";   

    /**
     * @return the companyId
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the kpiID
     */
    public long getKpiID() {
        return kpiID;
    }

    /**
     * @param kpiID the kpiID to set
     */
    public void setKpiID(long kpiID) {
        this.kpiID = kpiID;
    }

    /**
     * @return the kpiTitle
     */
    public String getKpiTitle() {
        return kpiTitle;
    }

    /**
     * @param kpiTitle the kpiTitle to set
     */
    public void setKpiTitle(String kpiTitle) {
        this.kpiTitle = kpiTitle;
    }

    /**
     * @return the achive
     */
    public double getAchive() {
        return achive;
    }

    /**
     * @param achive the achive to set
     */
    public void setAchive(double achive) {
        this.achive = achive;
    }

    /**
     * @return the target
     */
    public double getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(double target) {
        this.target = target;
    }

    /**
     * @return the valType
     */
    public String getValType() {
        return valType;
    }

    /**
     * @param valType the valType to set
     */
    public void setValType(String valType) {
        this.valType = valType;
    }
}
