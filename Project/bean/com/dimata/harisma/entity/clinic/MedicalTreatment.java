
package com.dimata.harisma.entity.clinic;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author bayu
 */

public class MedicalTreatment extends Entity {
 
    private long caseGroupId = 0;
    private String caseName = "";
    private int maxOccurance = 0;
    private int occurancePeriod = 0;
    private double maxBudget = 0.0;
    private int budgetPeriod = 0;
    private int budgetTarget = 0;

    
    public int getBudgetPeriod() {
        return budgetPeriod;
    }

    public void setBudgetPeriod(int budgetPeriod) {
        this.budgetPeriod = budgetPeriod;
    }

    public int getBudgetTarget() {
        return budgetTarget;
    }

    public void setBudgetTarget(int budgetTarget) {
        this.budgetTarget = budgetTarget;
    }

    public long getCaseGroupId() {
        return caseGroupId;
    }

    public void setCaseGroupId(long caseGroupId) {
        this.caseGroupId = caseGroupId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public double getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(double maxBudget) {
        this.maxBudget = maxBudget;
    }

    public int getMaxOccurance() {
        return maxOccurance;
    }

    public void setMaxOccurance(int maxOccurance) {
        this.maxOccurance = maxOccurance;
    }

    public int getOccurancePeriod() {
        return occurancePeriod;
    }

    public void setOccurancePeriod(int occurancePeriod) {
        this.occurancePeriod = occurancePeriod;
    }
    
}
