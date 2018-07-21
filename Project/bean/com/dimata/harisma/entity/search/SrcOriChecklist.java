
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

public class SrcOriChecklist{ 
	private String name = "";
	private long department;
	private long position;
	private Date commdatestart;
	private Date commdateend;

	public String getName(){ 
		return name; 
	} 

	public void setName(String name){ 
		if ( name == null ) {
			name = ""; 
		} 
		this.name = name; 
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

	public Date getCommdatestart(){ 
		return commdatestart; 
	} 

	public void setCommdatestart(Date commdatestart){ 
		this.commdatestart = commdatestart; 
	} 

	public Date getCommdateend(){ 
		return commdateend; 
	} 

	public void setCommdateend(Date commdateend){ 
		this.commdateend = commdateend; 
	} 

}
