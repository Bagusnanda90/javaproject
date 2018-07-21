/*
 * Tax_PTKP.java
 *
 * Created on August 20, 2007, 10:15 AM
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
public class Tax_PTKP extends Entity{
    
    private long martialId;
    private int ptkp_setahun;
    private int ptkp_sebulan;
    private long regulasi_id;
    
   
    
    /**
     * Getter for property ptkp_setahun.
     * @return Value of property ptkp_setahun.
     */
    public int getPtkp_setahun() {
        return ptkp_setahun;
    }    
   
    /**
     * Setter for property ptkp_setahun.
     * @param ptkp_setahun New value of property ptkp_setahun.
     */
    public void setPtkp_setahun(int ptkp_setahun) {
        this.ptkp_setahun = ptkp_setahun;
    }    
    
    /**
     * Getter for property ptkp_sebulan.
     * @return Value of property ptkp_sebulan.
     */
    public int getPtkp_sebulan() {
        return ptkp_sebulan;
    }
    
    /**
     * Setter for property ptkp_sebulan.
     * @param ptkp_sebulan New value of property ptkp_sebulan.
     */
    public void setPtkp_sebulan(int ptkp_sebulan) {
        this.ptkp_sebulan = ptkp_sebulan;
    }
    
    /**
     * Getter for property martialId.
     * @return Value of property martialId.
     */
    public long getMartialId() {
        return martialId;
    }
    
    /**
     * Setter for property martialId.
     * @param martialId New value of property martialId.
     */
    public void setMartialId(long martialId) {
        this.martialId = martialId;
    }
    
    /**
     * Getter for property regulasi_id.
     * @return Value of property regulasi_id.
     */
    public long getRegulasi_id() {
        return regulasi_id;
    }
    
    /**
     * Setter for property regulasi_id.
     * @param regulasi_id New value of property regulasi_id.
     */
    public void setRegulasi_id(long regulasi_id) {
        this.regulasi_id = regulasi_id;
    }
    
}
