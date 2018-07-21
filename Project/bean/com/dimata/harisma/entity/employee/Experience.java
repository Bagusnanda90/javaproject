
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

package com.dimata.harisma.entity.employee; 
 
/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

public class Experience extends Entity { 

	private long employeeId;
	private String companyName = "";
	private Date startDate = new Date();
	private Date endDate = new Date();
	private String position = "";
	private String moveReason = "";
        private long providerID=0;
        private String salaryReceived="";
	public long getEmployeeId(){ 
		return employeeId; 
	} 

	public void setEmployeeId(long employeeId){ 
		this.employeeId = employeeId; 
	} 

	public String getCompanyName(){ 
		return companyName; 
	} 

	public void setCompanyName(String companyName){ 
		if ( companyName == null ) {
			companyName = ""; 
		} 
		this.companyName = companyName; 
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

	public String getMoveReason(){ 
		return moveReason; 
	} 

	public void setMoveReason(String moveReason){ 
		if ( moveReason == null ) {
			moveReason = ""; 
		} 
		this.moveReason = moveReason; 
	} 

    /**
     * @return the providerID
     */
    public long getProviderID() {
        return providerID;
    }

    /**
     * @param providerID the providerID to set
     */
    public void setProviderID(long providerID) {
        this.providerID = providerID;
    }

    /**
     * @return the salaryReceived
     */
    public String getSalaryReceived() {
        return salaryReceived;
}

    /**
     * @param salaryReceived the salaryReceived to set
     */
    public void setSalaryReceived(String salaryReceived) {
        this.salaryReceived = salaryReceived;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
