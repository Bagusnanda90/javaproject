
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

public class SrcEmpSchedule{ 
        private String empNumber="";
        private String period = "";
	private String employee = "";
	private String schedule = "";
        // Update by Hendra Putu | 20150217 | Description : company, division, empCategory
        private String company = "";
        private String division = "";
        private String department = "";
        private String section = "";
        private String position = "";
        private String empCategory = "";
        private int sortBy=0;
        private int resigned=0;

	public String getPeriod(){ 
		return period; 
	} 

	public void setPeriod(String period){ 
		if ( period == null ) {
			period = ""; 
		} 
		this.period = period; 
	} 

	public String getEmployee(){ 
		return employee; 
	} 

	public void setEmployee(String employee){ 
		if ( employee == null ) {
			employee = ""; 
		} 
		this.employee = employee; 
	} 

	public String getSchedule(){ 
		return schedule; 
	} 

	public void setSchedule(String schedule){ 
		if ( schedule == null ) {
			schedule = ""; 
		} 
		this.schedule = schedule; 
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

    /**
     * @return the empNumber
     */
    public String getEmpNumber() {
        return empNumber;
    }

    /**
     * @param empNumber the empNumber to set
     */
    public void setEmpNumber(String empNumber) {
        this.empNumber = empNumber;
    }

    /**
     * @return the sortBy
     */
    public int getSortBy() {
        return sortBy;
    }

    /**
     * @param sortBy the sortBy to set
     */
    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
}

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @return the empCategory
     */
    public String getEmpCategory() {
        return empCategory;
    }

    /**
     * @param empCategory the empCategory to set
     */
    public void setEmpCategory(String empCategory) {
        this.empCategory = empCategory;
    }

    /**
     * @return the resigned
     */
    public int getResigned() {
        return resigned;
    }

    /**
     * @param resigned the resigned to set
     */
    public void setResigned(int resigned) {
        this.resigned = resigned;
    }
}
