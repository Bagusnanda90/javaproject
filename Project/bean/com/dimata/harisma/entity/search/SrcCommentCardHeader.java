
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

public class SrcCommentCardHeader{ 


	private String name = "";

	private Date carddatefrom;

	private Date carddateto;

	private long department;

	public String getName(){ 
		return name; 
	} 

	public void setName(String name){ 
		if ( name == null ) {
			name = ""; 
		} 
		this.name = name; 
	} 

	public Date getCarddatefrom(){ 
		return carddatefrom; 
	} 

	public void setCarddatefrom(Date carddatefrom){ 
		this.carddatefrom = carddatefrom; 
	} 

	public Date getCarddateto(){ 
		return carddateto; 
	} 

	public void setCarddateto(Date carddateto){ 
		this.carddateto = carddateto; 
	} 

	public long getDepartment(){ 
		return department; 
	} 

	public void setDepartment(long department){ 
		this.department = department; 
	} 

}
