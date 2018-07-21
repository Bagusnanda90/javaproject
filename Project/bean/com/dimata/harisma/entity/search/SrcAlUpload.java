
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

public class SrcAlUpload{ 

    private String EmployeeName = "";
    private String EmployeePayroll = "";
    private long EmployeeCategory = 0;
    private long EmployeeDepartement = 0;
    private long EmployeeSection = 0;
    private long EmployeePosition = 0;
    private int DataStatus = 0;
    private Date OpnameDate = new Date();

    private int resignStatus = 0;
    
    public int getDataStatus() {
        return DataStatus;
    }

    public void setDataStatus(int DataStatus) {
        this.DataStatus = DataStatus;
    }
    
    public long getEmployeeCategory() {
        return EmployeeCategory;
    }

    public void setEmployeeCategory(long EmployeeCategory) {
        this.EmployeeCategory = EmployeeCategory;
    }

    public long getEmployeeDepartement() {
        return EmployeeDepartement;
    }

    public void setEmployeeDepartement(long EmployeeDepartement) {
        this.EmployeeDepartement = EmployeeDepartement;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String EmployeeName) {
        this.EmployeeName = EmployeeName;
    }

    public String getEmployeePayroll() {
        return EmployeePayroll;
    }

    public void setEmployeePayroll(String EmployeePayroll) {
        this.EmployeePayroll = EmployeePayroll;
    }

    public long getEmployeePosition() {
        return EmployeePosition;
    }

    public void setEmployeePosition(long EmployeePosition) {
        this.EmployeePosition = EmployeePosition;
    }

    public long getEmployeeSection() {
        return EmployeeSection;
    }

    public void setEmployeeSection(long EmployeeSection) {
        this.EmployeeSection = EmployeeSection;
    }

    public Date getOpnameDate() {
        return OpnameDate;
    }

    public void setOpnameDate(Date OpnameDate) {
        this.OpnameDate = OpnameDate;
    }

    /**
     * @return the resignStatus
     */
    public int getResignStatus() {
        return resignStatus;
    }

    /**
     * @param resignStatus the resignStatus to set
     */
    public void setResignStatus(int resignStatus) {
        this.resignStatus = resignStatus;
    }
}
