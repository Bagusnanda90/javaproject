/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Hendra McHen | 2015-02-10
 */
public class BenefitConfigDeduction extends Entity {

    private int deductionPercent = 0;
    private String deductionDescription = "";
    private long deductionReference = 0;
    private int deductionIndex = 0;
    private long benefitConfigId = 0;

    /**
     * @return the deductionPersen
     */
    public int getDeductionPercent() {
        return deductionPercent;
    }

    /**
     * @param deductionPercent the deductionPersen to set
     */
    public void setDeductionPercent(int deductionPercent) {
        this.deductionPercent = deductionPercent;
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
     * @return the benefitConfigId
     */
    public long getBenefitConfigId() {
        return benefitConfigId;
    }

    /**
     * @param benefitConfigId the benefitConfigId to set
     */
    public void setBenefitConfigId(long benefitConfigId) {
        this.benefitConfigId = benefitConfigId;
    }

    /**
     * @return the deductionIndex
     */
    public int getDeductionIndex() {
        return deductionIndex;
    }

    /**
     * @param deductionIndex the deductionIndex to set
     */
    public void setDeductionIndex(int deductionIndex) {
        this.deductionIndex = deductionIndex;
    }
    
    /**
     * @return the deductionReference
     */
    public long getDeductionReference() {
        return deductionReference;
    }

    /**
     * @param deductionReference the deductionReference to set
     */
    public void setDeductionReference(long deductionReference) {
        this.deductionReference = deductionReference;
    }

}
