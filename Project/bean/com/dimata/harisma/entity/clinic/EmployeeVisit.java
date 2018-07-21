
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
import java.util.*;
import java.io.*;
/* package qdep */
import com.dimata.qdep.entity.*;

public class EmployeeVisit extends Entity implements Serializable{

	private Date visitDate;
	private long employeeId;
	private String diagnose = "";
	private long visitedBy;
	private String description = "";

	public Date getVisitDate(){ 
		return visitDate; 
	} 

	public void setVisitDate(Date visitDate){ 
		this.visitDate = visitDate; 
	} 

	public long getEmployeeId(){ 
		return employeeId; 
	} 

	public void setEmployeeId(long employeeId){ 
		this.employeeId = employeeId; 
	} 

	public String getDiagnose(){ 
		return diagnose; 
	} 

	public void setDiagnose(String diagnose){ 
		if ( diagnose == null ) {
			diagnose = ""; 
		} 
		this.diagnose = diagnose; 
	} 

	public long getVisitedBy(){ 
		return visitedBy; 
	} 

	public void setVisitedBy(long visitedBy){ 
		this.visitedBy = visitedBy; 
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

}
