
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

public class SrcRecognition{ 
	private String fullName = "";
	private String empNumber = "";
	private Date recogDateFrom;
	private Date recogDateTo;
	private long department;
	private long position;

	public String getFullName(){ 
		return fullName; 
	} 

	public void setFullName(String fullName){ 
		if ( fullName == null ) {
			fullName = ""; 
		} 
		this.fullName = fullName; 
	} 

	public String getEmpNumber(){ 
		return empNumber; 
	} 

	public void setEmpNumber(String empNumber){ 
		if ( empNumber == null ) {
			empNumber = ""; 
		} 
		this.empNumber = empNumber; 
	} 

	public Date getRecogDateFrom(){ 
		return recogDateFrom; 
	} 

	public void setRecogDateFrom(Date recogDateFrom){ 
		this.recogDateFrom = recogDateFrom; 
	} 

	public Date getRecogDateTo(){ 
		return recogDateTo; 
	} 

	public void setRecogDateTo(Date recogDateTo){ 
		this.recogDateTo = recogDateTo; 
	} 

	public long getDepartment(){ 
		return department; 
	} 

	public void setDepartment(long department){ 
		this.department = department; 
	} 

	public long getPosition(){ 
		return position; 
	} 

	public void setPosition(long position){ 
		this.position = position; 
	} 

}
