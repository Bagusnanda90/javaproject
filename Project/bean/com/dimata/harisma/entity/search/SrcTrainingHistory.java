
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

public class SrcTrainingHistory{ 


	private String employee = "";

	private String payrollNumber = "";

	private String program = "";

	private Date startDate;

	private Date endDate;

	private String trainer = "";

	public String getEmployee(){ 
		return employee; 
	} 

	public void setEmployee(String employee){ 
		if ( employee == null ) {
			employee = ""; 
		} 
		this.employee = employee; 
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

	public String getProgram(){ 
		return program; 
	} 

	public void setProgram(String program){ 
		if ( program == null ) {
			program = ""; 
		} 
		this.program = program; 
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

	public String getTrainer(){ 
		return trainer; 
	} 

	public void setTrainer(String trainer){ 
		if ( trainer == null ) {
			trainer = ""; 
		} 
		this.trainer = trainer; 
	} 

}
