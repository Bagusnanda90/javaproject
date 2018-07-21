
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.masterdata; 
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class EmpCategory extends Entity { 

	private String empCategory = "";
	private String description = "";
        private int typeForTax =0;
        private int entitleLeave=0;
        //update by satrya 2014-02-10
        private int entitleInsentif=0;
        private String code ="";
        private int categoryType =0;
        private int entitleDP =0;

	public String getEmpCategory(){ 
		return empCategory; 
	} 

	public void setEmpCategory(String empCategory){ 
		if ( empCategory == null ) {
			empCategory = ""; 
		} 
		this.empCategory = empCategory; 
	} 

	public String getDescription(){ 
		return description; 
	} 

	public void setDescription(String description){ 
		if ( description == null ) {
			description = ""; 
		} 
		this.description = description; 
	} 

    /**
     * @return the typeForTax
     */
    public int getTypeForTax() {
        return typeForTax;
    }

    /**
     * @param typeForTax the typeForTax to set
     */
    public void setTypeForTax(int typeForTax) {
        this.typeForTax = typeForTax;
    }

    /**
     * @return the entitleLeave
     */
    public int getEntitleLeave() {
        return entitleLeave;
    }

    /**
     * @param entitleLeave the entitleLeave to set
     */
    public void setEntitleLeave(int entitleLeave) {
        this.entitleLeave = entitleLeave;
    }

    /**
     * @return the entitleInsentif
     */
    public int getEntitleInsentif() {
        return entitleInsentif;
    }

    /**
     * @param entitleInsentif the entitleInsentif to set
     */
    public void setEntitleInsentif(int entitleInsentif) {
        this.entitleInsentif = entitleInsentif;
    }

    /**
     * @return the categoryType
     */
    public int getCategoryType() {
        return categoryType;
    }

    /**
     * @param categoryType the categoryType to set
     */
    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the entitleDP
     */
    public int getEntitleDP() {
        return entitleDP;
    }

    /**
     * @param entitleDP the entitleDP to set
     */
    public void setEntitleDP(int entitleDP) {
        this.entitleDP = entitleDP;
    }

}
