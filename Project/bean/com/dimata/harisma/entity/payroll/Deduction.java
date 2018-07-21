/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Hendra McHen | 2010-02-10
 */
public class Deduction extends Entity {

    private long deductionId = 0;
    private int deductionPersen = 0;
    private String deductionDescription = "";
    private int deductionReference = 0;
    private double deductionResult = 0;
    private long benefitMasterId = 0;

    /**
     * @return the deductionId
     */
    public long getDeductionId() {
        return deductionId;
    }

    /**
     * @param deductionId the deductionId to set
     */
    public void setDeductionId(long deductionId) {
        this.deductionId = deductionId;
    }

    /**
     * @return the deductionPersen
     */
    public int getDeductionPersen() {
        return deductionPersen;
    }

    /**
     * @param deductionPersen the deductionPersen to set
     */
    public void setDeductionPersen(int deductionPersen) {
        this.deductionPersen = deductionPersen;
    }

    /**
     * @return the deductionDescription
     */
    public String getDeductionDescription() {
        return deductionDescription;
    }

    /**
     * @param deductionDescription the deductionDescription to set
     */
    public void setDeductionDescription(String deductionDescription) {
        this.deductionDescription = deductionDescription;
    }

    /**
     * @return the deductionReference
     */
    public int getDeductionReference() {
        return deductionReference;
    }

    /**
     * @param deductionReference the deductionReference to set
     */
    public void setDeductionReference(int deductionReference) {
        this.deductionReference = deductionReference;
    }

    /**
     * @return the deductionResult
     */
    public double getDeductionResult() {
        return deductionResult;
    }

    /**
     * @param deductionResult the deductionResult to set
     */
    public void setDeductionResult(double deductionResult) {
        this.deductionResult = deductionResult;
    }

    /**
     * @return the benefitMasterId
     */
    public long getBenefitMasterId() {
        return benefitMasterId;
    }

    /**
     * @param benefitMasterId the benefitMasterId to set
     */
    public void setBenefitMasterId(long benefitMasterId) {
        this.benefitMasterId = benefitMasterId;
    }
}
