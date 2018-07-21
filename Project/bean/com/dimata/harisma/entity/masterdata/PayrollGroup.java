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
public class PayrollGroup extends Entity {
    private String payrollGroupName = "";
    private String description = "";

    /**
     * @return the PayrollGroupName
     */
    public String getPayrollGroupName() {
        return payrollGroupName;
    }

    /**
     * @param PayrollGroupName the PayrollGroupName to set
     */
    public void setPayrollGroupName(String payrollGroupName) {
        this.payrollGroupName = payrollGroupName;
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
    
}
