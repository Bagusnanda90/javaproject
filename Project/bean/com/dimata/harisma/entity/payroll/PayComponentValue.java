/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

/**
 *
 * @author Kartika
 * untuk menampung nilai payroll slip dengan data ke;lengkapanspt di payroll component dan level component
 */
public class PayComponentValue extends PayComponent{
    private double checkValue =0; 
    private int takeHomePay =0;
    private String formula  = null;

    /**
     * @return the checkValue
     */
    public double getCheckValue() {
        return checkValue;
    }

    /**
     * @param checkValue the checkValue to set
     */
    public void setCheckValue(double checkValue) {
        this.checkValue = checkValue;
    }

    /**
     * @return the takeHomePay
     */
    public int getTakeHomePay() {
        return takeHomePay;
    }

    /**
     * @param takeHomePay the takeHomePay to set
     */
    public void setTakeHomePay(int takeHomePay) {
        this.takeHomePay = takeHomePay;
    }

    /**
     * @return the formula
     */
    public String getFormula() {
        return formula;
    }

    /**
     * @param formula the formula to set
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }
}
