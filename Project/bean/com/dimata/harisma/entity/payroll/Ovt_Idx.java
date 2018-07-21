/*
 * Ovt_idx.java
 *
 * Created on April 12, 2007, 9:36 AM
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
public class Ovt_Idx extends Entity {
    
   private double hour_from;
   private double hour_to;
   private double ovt_idx;
   private String ovt_type_code;
   
   /**
    * Getter for property hour_from.
    * @return Value of property hour_from.
    */
   public double getHour_from() {
       return hour_from;
   }   
    
   /**
    * Setter for property hour_from.
    * @param hour_from New value of property hour_from.
    */
   public void setHour_from(double hour_from) {
       this.hour_from = hour_from;
   }
   
   /**
    * Getter for property hour_to.
    * @return Value of property hour_to.
    */
   public double getHour_to() {
       return hour_to;
   }
   
   /**
    * Setter for property hour_to.
    * @param hour_to New value of property hour_to.
    */
   public void setHour_to(double hour_to) {
       this.hour_to = hour_to;
   }
   
   /**
    * Getter for property ovt_idx.
    * @return Value of property ovt_idx.
    */
   public double getOvt_idx() {
       return ovt_idx;
   }
   
   /**
    * Setter for property ovt_idx.
    * @param ovt_idx New value of property ovt_idx.
    */
   public void setOvt_idx(double ovt_idx) {
       this.ovt_idx = ovt_idx;
   }
   
   /**
    * Getter for property ovt_type_code.
    * @return Value of property ovt_type_code.
    */
   public java.lang.String getOvt_type_code() {
       return ovt_type_code;
   }
   
   /**
    * Setter for property ovt_type_code.
    * @param ovt_type_code New value of property ovt_type_code.
    */
   public void setOvt_type_code(java.lang.String ovt_type_code) {
       this.ovt_type_code = ovt_type_code;
   }
   
}
