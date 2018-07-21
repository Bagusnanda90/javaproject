/*
 * ObjListCateg.java
 *
 * Created on May 23, 2007, 11:58 AM
 */

package com.dimata.harisma.entity.report;

import java.util.Vector;

/**
 *
 * @author  emerliana
 */
public class ObjListCateg {

    private Vector listValue = new Vector();
    private int jumlah = 0;
    private int countMale = 0;
    private int countFemale = 0;
    private String nmLevel = "";
    private String nmEducation = "";
    private int totJumlah = 0;
    private int totCountMale = 0;
    private int totCountFemale = 0;
    
    /**
     * Getter for property listValue.
     * @return Value of property listValue.
     */
    public java.util.Vector getListValue() {
        return listValue;
    }
    
    /**
     * Setter for property listValue.
     * @param listValue New value of property listValue.
     */
    public void setListValue(Vector listxx) {
        this.listValue.add(listxx);
    }   
    
    /**
     * Getter for property jumlah.
     * @return Value of property jumlah.
     */
    public int getJumlah() {
        return jumlah;
    }
    
    /**
     * Setter for property jumlah.
     * @param jumlah New value of property jumlah.
     */
    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
    
    /**
     * Getter for property countMale.
     * @return Value of property countMale.
     */
    public int getCountMale() {
        return countMale;
    }
    
    /**
     * Setter for property countMale.
     * @param countMale New value of property countMale.
     */
    public void setCountMale(int countMale) {
        this.countMale = countMale;
    }
    
    /**
     * Getter for property countFemale.
     * @return Value of property countFemale.
     */
    public int getCountFemale() {
        return countFemale;
    }
    
    /**
     * Setter for property countFemale.
     * @param countFemale New value of property countFemale.
     */
    public void setCountFemale(int countFemale) {
        this.countFemale = countFemale;
    }
    
    /**
     * Getter for property nmLevel.
     * @return Value of property nmLevel.
     */
    public java.lang.String getNmLevel() {
        return nmLevel;
    }
    
    /**
     * Setter for property nmLevel.
     * @param nmLevel New value of property nmLevel.
     */
    public void setNmLevel(java.lang.String nmLevel) {
        this.nmLevel = nmLevel;
    }
    
    /**
     * Getter for property nmEducation.
     * @return Value of property nmEducation.
     */
    public java.lang.String getNmEducation() {
        return nmEducation;
    }
    
    /**
     * Setter for property nmEducation.
     * @param nmEducation New value of property nmEducation.
     */
    public void setNmEducation(java.lang.String nmEducation) {
        this.nmEducation = nmEducation;
    }
    
    /**
     * Getter for property totJumlah.
     * @return Value of property totJumlah.
     */
    public int getTotJumlah() {
        return totJumlah;
    }
    
    /**
     * Setter for property totJumlah.
     * @param totJumlah New value of property totJumlah.
     */
    public void setTotJumlah(int totJumlah) {
        this.totJumlah = totJumlah;
    }
    
    /**
     * Getter for property totCountMale.
     * @return Value of property totCountMale.
     */
    public int getTotCountMale() {
        return totCountMale;
    }
    
    /**
     * Setter for property totCountMale.
     * @param totCountMale New value of property totCountMale.
     */
    public void setTotCountMale(int totCountMale) {
        this.totCountMale = totCountMale;
    }
    
    /**
     * Getter for property totCountFemale.
     * @return Value of property totCountFemale.
     */
    public int getTotCountFemale() {
        return totCountFemale;
    }
    
    /**
     * Setter for property totCountFemale.
     * @param totCountFemale New value of property totCountFemale.
     */
    public void setTotCountFemale(int totCountFemale) {
        this.totCountFemale = totCountFemale;
    }
    
}
