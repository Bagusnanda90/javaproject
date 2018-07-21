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
public class DocMasterExpense extends Entity { 

   
	private long doc_master_expense_id ;
        private long doc_master_id ;
	private float budget_min ;
        private float budget_max ;
        private int unit_type ;
        private String unit_name ;
        private String description = "";
        private long doc_expense_id ;

    /**
     * @return the doc_master_expense_id
     */
    public long getDoc_master_expense_id() {
        return doc_master_expense_id;
    }

    /**
     * @param doc_master_expense_id the doc_master_expense_id to set
     */
    public void setDoc_master_expense_id(long doc_master_expense_id) {
        this.doc_master_expense_id = doc_master_expense_id;
    }

    /**
     * @return the doc_master_id
     */
    public long getDoc_master_id() {
        return doc_master_id;
    }

    /**
     * @param doc_master_id the doc_master_id to set
     */
    public void setDoc_master_id(long doc_master_id) {
        this.doc_master_id = doc_master_id;
    }

    /**
     * @return the budget_min
     */
    public float getBudget_min() {
        return budget_min;
    }

    /**
     * @param budget_min the budget_min to set
     */
    public void setBudget_min(float budget_min) {
        this.budget_min = budget_min;
    }

    /**
     * @return the budget_max
     */
    public float getBudget_max() {
        return budget_max;
    }

    /**
     * @param budget_max the budget_max to set
     */
    public void setBudget_max(float budget_max) {
        this.budget_max = budget_max;
    }

    /**
     * @return the unit_type
     */
    public int getUnit_type() {
        return unit_type;
    }

    /**
     * @param unit_type the unit_type to set
     */
    public void setUnit_type(int unit_type) {
        this.unit_type = unit_type;
    }

    /**
     * @return the unit_name
     */
    public String getUnit_name() {
        return unit_name;
    }

    /**
     * @param unit_name the unit_name to set
     */
    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
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
     * @return the doc_expense_id
     */
    public long getDoc_expense_id() {
        return doc_expense_id;
    }

    /**
     * @param doc_expense_id the doc_expense_id to set
     */
    public void setDoc_expense_id(long doc_expense_id) {
        this.doc_expense_id = doc_expense_id;
    }
    
}
