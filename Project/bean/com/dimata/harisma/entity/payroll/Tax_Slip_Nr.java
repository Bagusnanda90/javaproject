/*
 * Tax_Slip_Nr.java
 *
 * Created on April 5, 2007, 1:51 PM
 */

package com.dimata.harisma.entity.payroll;

/* package qdep */
import com.dimata.qdep.entity.*;

/**
 *
 * @author  emerliana
 */
public class Tax_Slip_Nr extends Entity {
    
   private long period_id = 0;
   private String tax_code = "";
   private String prefix = "";
   private String sufix = "";
   private int digit = 0;
    
   /**
    * Getter for property period_id.
    * @return Value of property period_id.
    */
   public long getPeriod_id() {
       return period_id;
   }
   
   /**
    * Setter for property period_id.
    * @param period_id New value of property period_id.
    */
   public void setPeriod_id(long period_id) {
       this.period_id = period_id;
   }
   
   /**
    * Getter for property tax_code.
    * @return Value of property tax_code.
    */
   public java.lang.String getTax_code() {
       return tax_code;
   }
   
   /**
    * Setter for property tax_code.
    * @param tax_code New value of property tax_code.
    */
   public void setTax_code(java.lang.String tax_code) {
       this.tax_code = tax_code;
   }
   
   /**
    * Getter for property prefix.
    * @return Value of property prefix.
    */
   public java.lang.String getPrefix() {
       return prefix;
   }
   
   /**
    * Setter for property prefix.
    * @param prefix New value of property prefix.
    */
   public void setPrefix(java.lang.String prefix) {
       this.prefix = prefix;
   }
   
   /**
    * Getter for property sufix.
    * @return Value of property sufix.
    */
   public java.lang.String getSufix() {
       return sufix;
   }
   
   /**
    * Setter for property sufix.
    * @param sufix New value of property sufix.
    */
   public void setSufix(java.lang.String sufix) {
       this.sufix = sufix;
   }
   
   /**
    * Getter for property digit.
    * @return Value of property digit.
    */
   public int getDigit() {
       return digit;
   }
   
   /**
    * Setter for property digit.
    * @param digit New value of property digit.
    */
   public void setDigit(int digit) {
       this.digit = digit;
   }
   
}
