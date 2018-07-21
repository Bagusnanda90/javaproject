/*
 * Currency_Rate.java
 *
 * Created on April 4, 2007, 5:14 PM
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
public class Currency_Rate extends Entity{
    
    private double rate_company;
    private double rate_bank;
    private double tax_rate;
    private Date tgl_mulai;
    private Date tgl_akhir;
    private String curr_code = "";
    private int status = 0;
    
    /**
     * Getter for property rate_company.
     * @return Value of property rate_company.
     */
    public double getRate_company() {
        return rate_company;
    }    
    
    /**
     * Setter for property rate_company.
     * @param rate_company New value of property rate_company.
     */
    public void setRate_company(double rate_company) {
        this.rate_company = rate_company;
    }
    
    /**
     * Getter for property rate_bank.
     * @return Value of property rate_bank.
     */
    public double getRate_bank() {
        return rate_bank;
    }
    
    /**
     * Setter for property rate_bank.
     * @param rate_bank New value of property rate_bank.
     */
    public void setRate_bank(double rate_bank) {
        this.rate_bank = rate_bank;
    }
    
    /**
     * Getter for property tax_rate.
     * @return Value of property tax_rate.
     */
    public double getTax_rate() {
        return tax_rate;
    }
    
    /**
     * Setter for property tax_rate.
     * @param tax_rate New value of property tax_rate.
     */
    public void setTax_rate(double tax_rate) {
        this.tax_rate = tax_rate;
    }
    
    /**
     * Getter for property tgl_mulai.
     * @return Value of property tgl_mulai.
     */
    public java.util.Date getTgl_mulai() {
        return tgl_mulai;
    }
    
    /**
     * Setter for property tgl_mulai.
     * @param tgl_mulai New value of property tgl_mulai.
     */
    public void setTgl_mulai(java.util.Date tgl_mulai) {
        this.tgl_mulai = tgl_mulai;
    }
    
    /**
     * Getter for property tgl_akhir.
     * @return Value of property tgl_akhir.
     */
    public java.util.Date getTgl_akhir() {
        return tgl_akhir;
    }
    
    /**
     * Setter for property tgl_akhir.
     * @param tgl_akhir New value of property tgl_akhir.
     */
    public void setTgl_akhir(java.util.Date tgl_akhir) {
        this.tgl_akhir = tgl_akhir;
    }
    
   /**
     * Getter for property curr_code.
     * @return Value of property curr_code.
     */
    public java.lang.String getCurr_code() {
        return curr_code;
    }
    
    /**
     * Setter for property curr_code.
     * @param curr_code New value of property curr_code.
     */
    public void setCurr_code(java.lang.String curr_code) {
        this.curr_code = curr_code;
    }
    
    /**
     * Getter for property status.
     * @return Value of property status.
     */
    public int getStatus() {
        return status;
    }    
    
    /**
     * Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(int status) {
        this.status = status;
    }    
    
}
