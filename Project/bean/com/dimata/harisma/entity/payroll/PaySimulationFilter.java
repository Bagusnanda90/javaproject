/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Kartika
 */
public class PaySimulationFilter extends Entity { 
    private long paySimulationId=0;
    private String filterName ="";
    private int filterType =0;
    private String filterValue="";
    
    public static String FILTER_EMP_CATEGORY = "EMP_CAT";
    public static String FILTER_PAY_COMPONENT= "PAY_COMP";
    

    /**
     * @return the paySimulationId
     */
    public long getPaySimulationId() {
        return paySimulationId;
    }

    /**
     * @param paySimulationId the paySimulationId to set
     */
    public void setPaySimulationId(long paySimulationId) {
        this.paySimulationId = paySimulationId;
    }

    /**
     * @return the filterName
     */
    public String getFilterName() {
        return filterName;
    }

    /**
     * @param filterName the filterName to set
     */
    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    /**
     * @return the filterType
     */
    public int getFilterType() {
        return filterType;
    }

    /**
     * @param filterType the filterType to set
     */
    public void setFilterType(int filterType) {
        this.filterType = filterType;
    }

    /**
     * @return the filterValue
     */
    public String getFilterValue() {
        return filterValue;
    }

    /**
     * @param filterValue the filterValue to set
     */
    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }
    
}
