/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.entity.Entity;

public class BenefitPeriodDeduction extends Entity {

    private long deductionId = 0;
    private double deductionResult = 0;
    private long benefitPeriodId = 0;

    public long getDeductionId() {
        return deductionId;
    }

    public void setDeductionId(long deductionId) {
        this.deductionId = deductionId;
    }

    public double getDeductionResult() {
        return deductionResult;
    }

    public void setDeductionResult(double deductionResult) {
        this.deductionResult = deductionResult;
    }

    public long getBenefitPeriodId() {
        return benefitPeriodId;
    }

    public void setBenefitPeriodId(long benefitPeriodId) {
        this.benefitPeriodId = benefitPeriodId;
    }
}