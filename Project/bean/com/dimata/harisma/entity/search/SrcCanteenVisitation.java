/*
 * SrcCanteenVisitation.java
 *
 * Created on May 18, 1999, 11:54 AM
 */

package com.dimata.harisma.entity.search;

/* package java */ 
import java.util.Date;

/**
 *
 * @author  gedhy
 */
public class SrcCanteenVisitation {
    
	private String empnumber = "";

	private String fullname = "";

	private String department = "";

	private String section = "";

	private String position = "";

	private Date datefrom = new Date();

	private Date dateto = new Date();  

        /** Holds value of property periodCheck. */
        private boolean periodCheck = true;
        
	public String getEmpnumber(){ 
		return empnumber; 
	} 

	public void setEmpnumber(String empnumber){ 
		if ( empnumber == null ) {
			empnumber = ""; 
		} 
		this.empnumber = empnumber; 
	} 

	public String getFullname(){ 
		return fullname; 
	} 

	public void setFullname(String fullname){ 
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

	public Date getDatefrom(){ 
		return datefrom; 
	} 

	public void setDatefrom(Date datefrom){ 
		this.datefrom = datefrom; 
	} 

	public Date getDateto(){ 
		return dateto; 
	} 

	public void setDateto(Date dateto){ 
		this.dateto = dateto; 
	} 

        /** Getter for property periodCheck.
         * @return Value of property periodCheck.
         *
         */
        public boolean isPeriodCheck() {
            return this.periodCheck;
        }
        
        /** Setter for property periodCheck.
         * @param periodCheck New value of property periodCheck.
         *
         */
        public void setPeriodCheck(boolean periodCheck) {
            this.periodCheck = periodCheck;
        }
    
}
