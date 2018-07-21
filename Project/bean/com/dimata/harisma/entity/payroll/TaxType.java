/*
 * TaxType.java
 *
 * Created on March 30, 2007, 4:58 PM
 */

package com.dimata.harisma.entity.payroll;

/* package qdep */
import com.dimata.qdep.entity.*;
/**
 *
 * @author  emerliana
 */
public class TaxType extends Entity {
    
   private String taxCode = "";
   private String taxType = "";
    
   /**
    * Getter for property taxCode.
    * @return Value of property taxCode.
    */
   public java.lang.String getTaxCode() {
       return taxCode;
   }
   
   /**
    * Setter for property taxCode.
    * @param taxCode New value of property taxCode.
    */
   public void setTaxCode(java.lang.String taxCode) {
       this.taxCode = taxCode;
   }
   
   /**
    * Getter for property taxType.
    * @return Value of property taxType.
    */
   public java.lang.String getTaxType() {
       return taxType;
   }
   
   /**
    * Setter for property taxType.
    * @param taxType New value of property taxType.
    */
   public void setTaxType(java.lang.String taxType) {
       this.taxType = taxType;
   }
   
}
