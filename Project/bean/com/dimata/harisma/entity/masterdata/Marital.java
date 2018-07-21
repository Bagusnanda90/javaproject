
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

public class Marital extends Entity { 

	private String maritalStatus = "";
	private String maritalCode = "";
     
	private int numOfChildren;
        //update by satrya 2013-08-01
        private String taxMarital;
        private int taxNumberChirldren;
        // upadate by hendra 2015-01-29
        private int maritalStatusTax;

	public String getMaritalStatus(){ 
		return maritalStatus; 
	} 

	public void setMaritalStatus(String maritalStatus){ 
		if ( maritalStatus == null ) {
			maritalStatus = ""; 
		} 
		this.maritalStatus = maritalStatus; 
	} 

	public String getMaritalCode(){ 
		return maritalCode; 
	} 

	public void setMaritalCode(String maritalCode){ 
		if ( maritalCode == null ) {
			maritalCode = ""; 
		} 
		this.maritalCode = maritalCode; 
	} 

	public int getNumOfChildren(){ 
		return numOfChildren; 
	} 

	public void setNumOfChildren(int numOfChildren){ 
		this.numOfChildren = numOfChildren; 
	} 

    /**
     * @return the taxMarital
     */
    public String getTaxMarital() {
        return taxMarital;
    }

    /**
     * @param taxMarital the taxMarital to set
     */
    public void setTaxMarital(String taxMarital) {
        this.taxMarital = taxMarital;
    }

    /**
     * @return the taxNumberChirldren
     */
    public int getTaxNumberChirldren() {
        return taxNumberChirldren;
    }

    /**
     * @param taxNumberChirldren the taxNumberChirldren to set
     */
    public void setTaxNumberChirldren(int taxNumberChirldren) {
        this.taxNumberChirldren = taxNumberChirldren;
    }

    /**
     * @return the maritalStatusTax
     */
    public int getMaritalStatusTax() {
        return maritalStatusTax;
    }

    /**
     * @param maritalStatusTax the maritalStatusTax to set
     */
    public void setMaritalStatusTax(int maritalStatusTax) {
        this.maritalStatusTax = maritalStatusTax;
    }

}
