
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

package com.dimata.harisma.entity.search; 
 
/* package java */ 
import java.util.Date;

public class SrcSpecialAchievement{ 


	private String name = "";

	private String award = "";

	private String payrollNumber = "";

	private Date startDate;

	private Date endDate;

	public String getName(){ 
		return name; 
	} 

	public void setName(String name){ 
		if ( name == null ) {
			name = ""; 
		} 
		this.name = name; 
	} 

	public String getAward(){ 
		return award; 
	} 

	public void setAward(String award){ 
		if ( award == null ) {
			award = ""; 
		} 
		this.award = award; 
	} 

	public String getPayrollNumber(){ 
		return payrollNumber; 
	} 

	public void setPayrollNumber(String payrollNumber){ 
		if ( payrollNumber == null ) {
			payrollNumber = ""; 
		} 
		this.payrollNumber = payrollNumber; 
	} 

	public Date getStartDate(){ 
		return startDate; 
	} 

	public void setStartDate(Date startDate){ 
		this.startDate = startDate; 
	} 

	public Date getEndDate(){ 
		return endDate; 
	} 

	public void setEndDate(Date endDate){ 
		this.endDate = endDate; 
	} 

}
