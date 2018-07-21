
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

public class MedicalRecord extends Entity { 

	private long familyMemberId;
	private long diseaseTypeId;
	private long medicalTypeId;
	private long employeeId;
        private long medicalCaseId;
	private Date recordDate;
        private double caseQuantity=1.0;
	private double amount;
    private double discountInPercent;
	private double discountInRp;
	private double total;

	public long getFamilyMemberId(){ 
		return familyMemberId; 
	} 

	public void setFamilyMemberId(long familyMemberId){ 
		this.familyMemberId = familyMemberId; 
	} 

	public long getDiseaseTypeId(){ 
		return diseaseTypeId; 
	} 

	public void setDiseaseTypeId(long diseaseTypeId){ 
		this.diseaseTypeId = diseaseTypeId; 
	} 

	public long getMedicalTypeId(){ 
		return medicalTypeId; 
	} 

	public void setMedicalTypeId(long medicalTypeId){ 
		this.medicalTypeId = medicalTypeId; 
	} 

	public long getEmployeeId(){ 
		return employeeId; 
	} 

	public void setEmployeeId(long employeeId){ 
		this.employeeId = employeeId; 
	} 

	public Date getRecordDate(){ 
		return recordDate; 
	} 

	public void setRecordDate(Date recordDate){ 
		this.recordDate = recordDate; 
	} 

	public double getAmount(){ 
		return amount; 
	} 

	public void setAmount(double amount){ 
		this.amount = amount; 
	}

 		public double getDiscountInPercent(){ 
		return discountInPercent; 
	} 

	public void setDiscountInPercent(double discountInPercent){ 
		this.discountInPercent = discountInPercent; 
	} 

	public double getDiscountInRp(){ 
		return discountInRp; 
	} 

	public void setDiscountInRp(double discountInRp){ 
		this.discountInRp = discountInRp; 
	} 

	public double getTotal(){ 
		return total; 
	} 

	public void setTotal(double total){ 
		this.total = total; 
	}

    public long getMedicalCaseId() {
        return medicalCaseId;
    }

    public void setMedicalCaseId(long medicalCaseId) {
        this.medicalCaseId = medicalCaseId;
    }

    public double getCaseQuantity() {
        return caseQuantity;
    }

    public void setCaseQuantity(double caseQuantity) {
        this.caseQuantity = caseQuantity;
    }


}
