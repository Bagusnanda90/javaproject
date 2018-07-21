/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.outsource;

/**
 *
 * @author Kartika
 */
public class OutSourceValues {
    private double previous=0;
    private double triwulan1=0;
    private double triwulan2=0;
    private double triwulan3=0;
    private double triwulan4=0;

    /**
     * @return the previous
     */
    public double getPrevious() {
        return previous;
    }

    /**
     * @param previous the previous to set
     */
    public void setPrevious(double previous) {
        this.previous = previous;
    }

    public void addToPrevious(double previous) {
        this.previous =  this.previous + previous;
    }
    /**
     * @return the triwulan1
     */
    public double getTriwulan1() {
        return triwulan1;
    }

    /**
     * @param triwulan1 the triwulan1 to set
     */
    public void setTriwulan1(double triwulan1) {
        this.triwulan1 = triwulan1;
    }
    
    public void addToTriwulan1(double triwulan1) {
        this.triwulan1 = this.triwulan1 + triwulan1;
    }
    /**
     * @return the triwulan2
     */
    public double getTriwulan2() {
        return triwulan2;
    }

    /**
     * @param triwulan2 the triwulan2 to set
     */
    public void setTriwulan2(double triwulan2) {
        this.triwulan2 = triwulan2;
    }
    public void addToTriwulan2(double triwulan2) {
        this.triwulan2 = this.triwulan2 + triwulan2;
    }
    
    /**
     * @return the triwulan3
     */
    public double getTriwulan3() {
        return triwulan3;
    }

    /**
     * @param triwulan3 the triwulan3 to set
     */
    public void setTriwulan3(double triwulan3) {
        this.triwulan3 = triwulan3;
    }
    
    public void addToTriwulan3(double triwulan3) {
        this.triwulan3 = this.triwulan3 + triwulan3;
    }
    /**
     * @return the triwulan4
     */
    public double getTriwulan4() {
        return triwulan4;
    }

    /**
     * @param triwulan4 the triwulan4 to set
     */
    public void setTriwulan4(double triwulan4) {
        this.triwulan4 = triwulan4;
    }
    
    public void addToTriwulan4(double triwulan4) {
        this.triwulan4 = this.triwulan4 + triwulan4;
    }
    
    public void addToTriwulan(double triwulan1, double triwulan2, double triwulan3, double triwulan4 ) {
        this.triwulan1 = this.triwulan1 + triwulan1;
        this.triwulan2 = this.triwulan2 + triwulan2;
        this.triwulan3 = this.triwulan3 + triwulan3;
        this.triwulan4 = this.triwulan4 + triwulan4;
    }
    
    public double getTotalAnnual() {
        return triwulan1+triwulan2+triwulan3+triwulan4;
    }
    
    public void resetAllValues() {
        this.previous=0;
        this.triwulan1=0;
        this.triwulan2=0;
        this.triwulan3=0;
        this.triwulan4=0;
    }
}
