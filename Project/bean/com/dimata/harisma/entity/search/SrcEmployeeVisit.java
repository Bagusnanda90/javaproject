
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

public class SrcEmployeeVisit{ 


	private String employeeName = "";

	private long department;

	private Date visitDateFrom;

	private Date visitDateTo;

    private int sortBy;

	public String getEmployeeName(){ return employeeName; } 

	public void setEmployeeName(String employeeName){ this.employeeName = employeeName; }

	public long getDepartment(){ 
		return department; 
	} 

	public void setDepartment(long department){ 
		this.department = department; 
	} 

	public Date getVisitDateFrom(){ return visitDateFrom; } 

	public void setVisitDateFrom(Date visitDateFrom){ this.visitDateFrom = visitDateFrom; }

	public Date getVisitDateTo(){ return visitDateTo; } 

	public void setVisitDateTo(Date visitDateTo){ this.visitDateTo = visitDateTo; }

    public int getSortBy(){ return sortBy; }

    public void setSortBy(int sortBy){ this.sortBy = sortBy; }
}
