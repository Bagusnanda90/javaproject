/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.configrewardnpunisment;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Devin
 */
public class ConfigRewardAndPunishment extends Entity{
    public static final int DC=0;
    public static final int NetOffSales=1;
    private double maxDeduction;
    private double presentaseToSales;
    private double presentaseToBod;
    private int dayNewEmployeeFree;

    /**
     * @return the maxDeduction
     */
    public double getMaxDeduction() {
        return maxDeduction;
    }

    /**
     * @param maxDeduction the maxDeduction to set
     */
    public void setMaxDeduction(double maxDeduction) {
        this.maxDeduction = maxDeduction;
    }

    /**
     * @return the presentaseToSales
     */
    public double getPresentaseToSales() {
        return presentaseToSales;
    }

    /**
     * @param presentaseToSales the presentaseToSales to set
     */
    public void setPresentaseToSales(double presentaseToSales) {
        this.presentaseToSales = presentaseToSales;
    }

    /**
     * @return the presentaseToBod
     */
    public double getPresentaseToBod() {
        return presentaseToBod;
    }

    /**
     * @param presentaseToBod the presentaseToBod to set
     */
    public void setPresentaseToBod(double presentaseToBod) {
        this.presentaseToBod = presentaseToBod;
    }

    /**
     * @return the dayNewEmployeeFree
     */
    public int getDayNewEmployeeFree() {
        return dayNewEmployeeFree;
    }

    /**
     * @param dayNewEmployeeFree the dayNewEmployeeFree to set
     */
    public void setDayNewEmployeeFree(int dayNewEmployeeFree) {
        this.dayNewEmployeeFree = dayNewEmployeeFree;
    }
    
    
}
