/*
 * PayTaxVariabel.java
 *
 * Created on August 10, 2007, 1:11 PM
 */

package com.dimata.harisma.entity.payroll;

/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

/**
 *
 * @author  emerliana
 */
public class PayTaxVariabel extends Entity {
    
    
    private String nameVariabel;
    private int valueVariabel;
    private String levelCode;
    private int jenis_Variabel;
    private double persen_variabel;
    
    /**
     * Getter for property nameVariabel.
     * @return Value of property nameVariabel.
     */
    public java.lang.String getNameVariabel() {
        return nameVariabel;
    }
    
    /**
     * Setter for property nameVariabel.
     * @param nameVariabel New value of property nameVariabel.
     */
    public void setNameVariabel(java.lang.String nameVariabel) {
        this.nameVariabel = nameVariabel;
    }
    
    /**
     * Getter for property valueVariabel.
     * @return Value of property valueVariabel.
     */
    public int getValueVariabel() {
        return valueVariabel;
    }
    
    /**
     * Setter for property valueVariabel.
     * @param valueVariabel New value of property valueVariabel.
     */
    public void setValueVariabel(int valueVariabel) {
        this.valueVariabel = valueVariabel;
    }
    
    /**
     * Getter for property levelCode.
     * @return Value of property levelCode.
     */
    public java.lang.String getLevelCode() {
        return levelCode;
    }
    
    /**
     * Setter for property levelCode.
     * @param levelCode New value of property levelCode.
     */
    public void setLevelCode(java.lang.String levelCode) {
        this.levelCode = levelCode;
    }
    
    /**
     * Getter for property jenis_Variabel.
     * @return Value of property jenis_Variabel.
     */
    public int getJenis_Variabel() {
        return jenis_Variabel;
    }
    
    /**
     * Setter for property jenis_Variabel.
     * @param jenis_Variabel New value of property jenis_Variabel.
     */
    public void setJenis_Variabel(int jenis_Variabel) {
        this.jenis_Variabel = jenis_Variabel;
    }
    
    /**
     * Getter for property persen_variabel.
     * @return Value of property persen_variabel.
     */
    public double getPersen_variabel() {
        return persen_variabel;
    }
    
    /**
     * Setter for property persen_variabel.
     * @param persen_variabel New value of property persen_variabel.
     */
    public void setPersen_variabel(double persen_variabel) {
        this.persen_variabel = persen_variabel;
    }
    
}
