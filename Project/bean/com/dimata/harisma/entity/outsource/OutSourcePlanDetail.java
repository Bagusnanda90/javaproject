/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.outsource;

/**
 *
 * @author Kartika
 */
import com.dimata.qdep.entity.Entity;

public class OutSourcePlanDetail extends Entity {

    private long outsourcePlanId = 0;
    private long positionId = 0;
    private double costPerPerson = 0;
    private String generalInfo = "";
    private String typeOfContract = "";
    private double contractPeriod = 0;
    private String objectives = "";
    private String costNBenefitAnlysis = "";
    private double costTotal = 0;
    private String riskAnaliys = "";
    private String description = "";

    public long getOutsourcePlanId() {
        return outsourcePlanId;
    }

    public void setOutsourcePlanId(long outsourcePlanId) {
        this.outsourcePlanId = outsourcePlanId;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    public double getCostPerPerson() {
        return costPerPerson;
    }

    public void setCostPerPerson(double costPerPerson) {
        this.costPerPerson = costPerPerson;
    }

    public String getGeneralInfo() {
        return generalInfo;
    }

    public void setGeneralInfo(String generalInfo) {
        this.generalInfo = generalInfo;
    }

    public String getTypeOfContract() {
        return typeOfContract;
    }

    public void setTypeOfContract(String typeOfContract) {
        this.typeOfContract = typeOfContract;
    }

    public double getContractPeriod() {
        return contractPeriod;
    }

    public void setContractPeriod(double contractPeriod) {
        this.contractPeriod = contractPeriod;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public String getCostNBenefitAnlysis() {
        return costNBenefitAnlysis;
    }

    public void setCostNBenefitAnlysis(String costNBenefitAnlysis) {
        this.costNBenefitAnlysis = costNBenefitAnlysis;
    }

    public double getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(double costTotal) {
        this.costTotal = costTotal;
    }

    public String getRiskAnaliys() {
        return riskAnaliys;
    }

    public void setRiskAnaliys(String riskAnaliys) {
        this.riskAnaliys = riskAnaliys;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}