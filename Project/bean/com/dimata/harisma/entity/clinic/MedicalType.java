
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author	 :
 * @version	 :
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.clinic; 
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class MedicalType extends Entity { 

	private String typeCode = "";
	private String typeName = "";
    private long medExpenseTypeId;

    /**
     * Holds value of property yearlyAmount.
     */
    private double yearlyAmount;
    
	public String getTypeCode(){ 
		return typeCode; 
	} 

	public void setTypeCode(String typeCode){ 
		if ( typeCode == null ) {
			typeCode = ""; 
		} 
		this.typeCode = typeCode; 
	} 

	public String getTypeName(){ 
		return typeName; 
	} 

	public void setTypeName(String typeName){ 
		if ( typeName == null ) {
			typeName = ""; 
		} 
		this.typeName = typeName; 
	} 

    public long getMedExpenseTypeId(){ return medExpenseTypeId; }

    public void setMedExpenseTypeId(long medExpenseTypeId){ this.medExpenseTypeId = medExpenseTypeId; }
    
    /**
     * Getter for property yearlyAmount.
     * @return Value of property yearlyAmount.
     */
    public double getYearlyAmount() {
        return this.yearlyAmount;
    }
    
    /**
     * Setter for property yearlyAmount.
     * @param yearlyAmount New value of property yearlyAmount.
     */
    public void setYearlyAmount(double yearlyAmount) {
        this.yearlyAmount = yearlyAmount;
    }
    
}
