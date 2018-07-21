/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.entity.Entity;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Kartika 
 * @date 2015-09-05
 */
public class PaySimulation extends Entity { 
    private String title="";
    private String objectives="";
    private Date createdDate = new Date();
    private long creadedById=0;
    private Date requestDate = new Date();
    private long requestedById=0;
    private Date dueDate = new Date();
    private int statusDoc =0;
    private double maxTotalBudget=0;
    private int maxAddEmployee=0;
    private long sourcePayPeriodId=0;

    private Vector<Long> employeeCategoryIds=null;
    private Vector<String> payrollComponents=null;
    private Vector<PaySimulationStructure> paySimulationStruct = null;
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the objectives
     */
    public String getObjectives() {
        return objectives;
    }

    /**
     * @param objectives the objectives to set
     */
    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the creadedById
     */
    public long getCreadedById() {
        return creadedById;
    }

    /**
     * @param creadedById the creadedById to set
     */
    public void setCreadedById(long creadedById) {
        this.creadedById = creadedById;
    }

    /**
     * @return the requestDate
     */
    public Date getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate the requestDate to set
     */
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * @return the requestedById
     */
    public long getRequestedById() {
        return requestedById;
    }

    /**
     * @param requestedById the requestedById to set
     */
    public void setRequestedById(long requestedById) {
        this.requestedById = requestedById;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return the statusDoc
     */
    public int getStatusDoc() {
        return statusDoc;
    }

    /**
     * @param statusDoc the statusDoc to set
     */
    public void setStatusDoc(int statusDoc) {
        this.statusDoc = statusDoc;
    }

    /**
     * @return the maxTotalBudget
     */
    public double getMaxTotalBudget() {
        return maxTotalBudget;
    }

    /**
     * @param maxTotalBudget the maxTotalBudget to set
     */
    public void setMaxTotalBudget(double maxTotalBudget) {
        this.maxTotalBudget = maxTotalBudget;
    }

    /**
     * @return the maxAddEmployee
     */
    public int getMaxAddEmployee() {
        return maxAddEmployee;
    }

    /**
     * @param maxAddEmployee the maxAddEmployee to set
     */
    public void setMaxAddEmployee(int maxAddEmployee) {
        this.maxAddEmployee = maxAddEmployee;
    }

    /**
     * @return the sourcePayPeriodId
     */
    public long getSourcePayPeriodId() {
        return sourcePayPeriodId;
    }

    /**
     * @param sourcePayPeriodId the sourcePayPeriodId to set
     */
    public void setSourcePayPeriodId(long sourcePayPeriodId) {
        this.sourcePayPeriodId = sourcePayPeriodId;
    }

    /**
     * @return the employeeCategoryIds
     */
    public Vector<Long> getEmployeeCategoryIds() {
        return employeeCategoryIds;
    }

    /**
     * @param employeeCategoryIds the employeeCategoryIds to set
     */
    public void setEmployeeCategoryIds(Vector<Long> employeeCategoryIds) {
        this.employeeCategoryIds = employeeCategoryIds;
    }

    /**
     * @return the payrollComponents
     */
    public Vector<String> getPayrollComponents() {
        return payrollComponents;
    }

    /**
     * @param payrollComponents the payrollComponents to set
     */
    public void setPayrollComponents(Vector<String> payrollComponents) {
        this.payrollComponents = payrollComponents;
    }

    /**
     * @return the paySimulationStruct
     */
    public Vector<PaySimulationStructure> getPaySimulationStruct() {
        return paySimulationStruct;
    }

    /**
     * @param paySimulationStruct the paySimulationStruct to set
     */
    public void setPaySimulationStruct(Vector<PaySimulationStructure> paySimulationStruct) {
        this.paySimulationStruct = paySimulationStruct;
    }
}
