
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

public class SrcRecrApplication{ 
	private String name = "";
	private String position = "";
	private Date appldatefrom;
	private Date appldateto;

	public String getName(){ 
		return name; 
	} 

	public void setName(String name){ 
		if ( name == null ) {
			name = ""; 
		} 
		this.name = name; 
	} 

	public String getPosition(){ 
		return position; 
	} 

	public void setPosition(String position){ 
		if ( position == null ) {
			position = ""; 
		} 
		this.position = position; 
	} 

	public Date getAppldateFrom(){ 
		return appldatefrom; 
	} 

	public void setAppldateFrom(Date appldatefrom){ 
            this.appldatefrom = appldatefrom; 
        } 

	public Date getAppldateTo(){ 
		return appldateto; 
	} 

	public void setAppldateTo(Date appldateto){ 
            this.appldateto = appldateto; 
	} 

}
