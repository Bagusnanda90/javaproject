
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

package com.dimata.harisma.entity.search; 
 
/* package java */ 
import java.util.Date;

public class SrcLeave{ 
	private String emp_number = "";
	private String fullname = "";
	private String department = "";
	private String section = "";
	private String position = "";

	public String getEmpNumber(){ 
		return emp_number; 
	} 

	public void setEmpNumber(String emp_number){ 
		if ( emp_number == null ) {
			emp_number = ""; 
		} 
		this.emp_number = emp_number; 
	} 

	public String getFullName(){ 
		return fullname; 
	} 

	public void setFullName(String fullname){ 
		if ( fullname == null ) {
			fullname = ""; 
		} 
		this.fullname = fullname; 
	} 

	public String getDepartment(){ 
		return department; 
	} 

	public void setDepartment(String department){ 
		if ( department == null ) {
			department = ""; 
		} 
		this.department = department; 
	} 

	public String getSection(){ 
		return section; 
	} 

	public void setSection(String section){ 
		if ( section == null ) {
			section = ""; 
		} 
		this.section = section; 
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

}
