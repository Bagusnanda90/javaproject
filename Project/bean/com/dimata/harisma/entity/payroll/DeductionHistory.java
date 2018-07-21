/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Dimata 007
 */
public class DeductionHistory extends Entity {

    private int percen = 0;
    private String description = "";
    private double percenResult = 0;
    private double deductionResult = 0;
    private long benefitPeriodHistoryId = 0;

    public int getPercen() {
        return percen;
    }

    public void setPercen(int percen) {
        this.percen = percen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPercenResult() {
        return percenResult;
    }

    public void setPercenResult(double percenResult) {
        this.percenResult = percenResult;
    }

    public double getDeductionResult() {
        return deductionResult;
    }

    public void setDeductionResult(double deductionResult) {
        this.deductionResult = deductionResult;
    }

    public long getBenefitPeriodHistoryId() {
        return benefitPeriodHistoryId;
    }

    public void setBenefitPeriodHistoryId(long benefitPeriodHistoryId) {
        this.benefitPeriodHistoryId = benefitPeriodHistoryId;
    }

}
