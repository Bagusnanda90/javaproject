
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya
 * @version  	: 01
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

public class ExpRecapitulate extends Entity { 

	private Date periode;
	private long medicalTypeId;
	private double amount;
	private double discountInPercent;
	private double discountInRp;
	private double total;
	private int person;

	public Date getPeriode(){ 
		return periode; 
	} 

	public void setPeriode(Date periode){ 
		this.periode = periode; 
	} 

	public long getMedicalTypeId(){ return medicalTypeId; } 

	public void setMedicalTypeId(long medicalTypeId){ this.medicalTypeId = medicalTypeId; }

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

	public int getPerson(){ 
		return person; 
	} 

	public void setPerson(int person){ 
		this.person = person; 
	} 

}
