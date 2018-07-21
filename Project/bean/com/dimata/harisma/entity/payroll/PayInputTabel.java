/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.qdep.entity.Entity;
import java.util.Hashtable;

/**
 *
 * @author Satrya Ramayu
 */
public class PayInputTabel extends Entity{
    private long paySlipId;
    //private String payInputCode;
    //private Hashtable payInputValue= new Hashtable();
    private long employeeId;
    private long periodId;
    private String payInputCode;
    private double payInputValue;
    /**
     * @return the paySlipId
     */
    public long getPaySlipId() {
        return paySlipId;
    }

    /**
     * @param paySlipId the paySlipId to set
     */
    public void setPaySlipId(long paySlipId) {
        this.paySlipId = paySlipId;
    }

    

    /**
     * @return the employeeId
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the periodId
     */
    public long getPeriodId() {
        return periodId;
    }

    /**
     * @param periodId the periodId to set
     */
    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }

//    /**
//     * @return the payInputValue
//     */
//    public double getPayInputValue(String payInputCode,long periodId,long employeeId) {
//        double value=0;
//        try{
//            value = (Double)payInputValue.get(""+payInputCode+"_"+periodId+"_"+employeeId);
//        }catch(Exception exc){
//            System.out.println("Exc"+exc);
//        }
//        return value;
//    }
//
//    /**
//     * @param payInputValue the payInputValue to set
//     */
//    public void addPayInputValue(String payInputCode,long periodId,long employeeId,double value) {
//        this.payInputValue.put(""+payInputCode+"_"+periodId+"_"+employeeId, ""+value);
//    }

    /**
     * @return the payInputCode
     */
    public String getPayInputCode() {
        return payInputCode;
    }

    /**
     * @param payInputCode the payInputCode to set
     */
    public void setPayInputCode(String payInputCode) {
        this.payInputCode = payInputCode;
    }

    /**
     * @return the payInputValue
     */
    public double getPayInputValue() {
        return payInputValue;
    }

    /**
     * @param payInputValue the payInputValue to set
     */
    public void setPayInputValue(double payInputValue) {
        this.payInputValue = payInputValue;
    }
}
