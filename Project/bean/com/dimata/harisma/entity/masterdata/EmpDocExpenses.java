/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author GUSWIK
 */
public class EmpDocExpenses extends  Entity{
        private long empDocId  ;
   	private long docMasterExpenseId  ;
        private float budgetValue  ;
        private float realvalue ;
	private int expenseUnit ;
        private float Total ;
        private String description  ;
        private String note = "";

    /**
     * @return the empDocId
     */
    public long getEmpDocId() {
        return empDocId;
    }

    /**
     * @param empDocId the empDocId to set
     */
    public void setEmpDocId(long empDocId) {
        this.empDocId = empDocId;
    }

    /**
     * @return the docMasterExpenseId
     */
    public long getDocMasterExpenseId() {
        return docMasterExpenseId;
    }

    /**
     * @param docMasterExpenseId the docMasterExpenseId to set
     */
    public void setDocMasterExpenseId(long docMasterExpenseId) {
        this.docMasterExpenseId = docMasterExpenseId;
    }

    /**
     * @return the budgetValue
     */
    public float getBudgetValue() {
        return budgetValue;
    }

    /**
     * @param budgetValue the budgetValue to set
     */
    public void setBudgetValue(float budgetValue) {
        this.budgetValue = budgetValue;
    }

    /**
     * @return the realvalue
     */
    public float getRealvalue() {
        return realvalue;
    }

    /**
     * @param realvalue the realvalue to set
     */
    public void setRealvalue(float realvalue) {
        this.realvalue = realvalue;
    }

    /**
     * @return the expenseUnit
     */
    public int getExpenseUnit() {
        return expenseUnit;
    }

    /**
     * @param expenseUnit the expenseUnit to set
     */
    public void setExpenseUnit(int expenseUnit) {
        this.expenseUnit = expenseUnit;
    }

    /**
     * @return the Total
     */
    public float getTotal() {
        return Total;
    }

    /**
     * @param Total the Total to set
     */
    public void setTotal(float Total) {
        this.Total = Total;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }
    
}
