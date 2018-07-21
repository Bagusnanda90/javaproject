
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
import java.util.Vector;
public class SrcPresence{ 


	private String empnumber = "";

	private String fullname = "";

	private String department = "";

	private String section = "";

	private String position = "";

	private Date datefrom = new Date();

	private Date dateto = new Date();
        
        private String departmentName="";
        private String positionName="";
        private String sectionName="";
        private String sCommarcingDate="";
        private long empId =0;
        private long presenceId;
        
        private boolean flagsts=false;

        /** Holds value of property periodCheck. */
        private boolean periodCheck = true;
        private Vector statusCheck = new Vector();
        //update by satrya 2013-06-19
        private String source="";
        
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

    /**
     * @return the statusCheck
     */
    public Vector getStatusCheck() {
        return statusCheck;
    }

    /**
     * @param statusCheck the statusCheck to set
     */
    public void setStatusCheck(Vector statusCheck) {
        this.statusCheck = statusCheck;
    }

    /**
     * @return the departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @param departmentName the departmentName to set
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * @return the positionName
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * @param positionName the positionName to set
     */
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /**
     * @return the sectionName
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * @param sectionName the sectionName to set
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * @return the sCommarcingDate
     */
    public String getsCommarcingDate() {
        return sCommarcingDate;
    }

    /**
     * @param sCommarcingDate the sCommarcingDate to set
     */
    public void setsCommarcingDate(String sCommarcingDate) {
        this.sCommarcingDate = sCommarcingDate;
    }

    /**
     * @return the flagsts
     */
    public boolean isFlagsts() {
        return flagsts;
    }

    /**
     * @param flagsts the flagsts to set
     */
    public void setFlagsts(boolean flagsts) {
        this.flagsts = flagsts;
    }

    /**
     * @return the empId
     */
    public long getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(long empId) {
        this.empId = empId;
    }

    /**
     * @return the presenceId
     */
    public long getPresenceId() {
        return presenceId;
    }

    /**
     * @param presenceId the presenceId to set
     */
    public void setPresenceId(long presenceId) {
        this.presenceId = presenceId;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }
        
}
