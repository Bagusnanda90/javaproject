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
public class DocExpenses extends Entity { 

   
	private int doc_expense_id  ;
	private String type_name = "";
        private String expense_name  = "";
        private float plan_expense_value  ;
        private String note = "";

    /**
     * @return the doc_expense_id
     */
    public int getDoc_expense_id() {
        return doc_expense_id;
    }

    /**
     * @param doc_expense_id the doc_expense_id to set
     */
    public void setDoc_expense_id(int doc_expense_id) {
        this.doc_expense_id = doc_expense_id;
    }

    /**
     * @return the type_name
     */
    public String getType_name() {
        return type_name;
    }

    /**
     * @param type_name the type_name to set
     */
    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    /**
     * @return the expense_name
     */
    public String getExpense_name() {
        return expense_name;
    }

    /**
     * @param expense_name the expense_name to set
     */
    public void setExpense_name(String expense_name) {
        this.expense_name = expense_name;
    }

    /**
     * @return the plan_expense_value
     */
    public float getPlan_expense_value() {
        return plan_expense_value;
    }

    /**
     * @param plan_expense_value the plan_expense_value to set
     */
    public void setPlan_expense_value(float plan_expense_value) {
        this.plan_expense_value = plan_expense_value;
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
