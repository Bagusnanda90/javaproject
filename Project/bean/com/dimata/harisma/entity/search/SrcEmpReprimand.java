
package com.dimata.harisma.entity.search;

import java.util.Date;

/**
 *
 * @author bayu
 */

public class SrcEmpReprimand {
    
    private long employeeId = 0;
    private String name = "";
    private String payroll = "";
    private long divId = 0;
    private long deptId = 0;
    private long secId = 0;
    private long posId = 0;
    private Date reprimandDateStart = new Date();
    private Date reprimandDateEnd = new Date();
    
    
    public SrcEmpReprimand() {
    }
    
    
    
    public long getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPayroll() {
        return payroll;
    }
    
    public void setPayroll(String payroll) {
        this.payroll = payroll;
    }
    
    public long getDepartmentId() {
        return deptId;
    }
    
    public void setDepartmentId(long deptId) {
        this.deptId = deptId;
    }
    
    public long getSectionId() {
        return secId;
    }
    
    public void setSectionId(long secId) {
        this.secId = secId;
    }
    
    public long getPositionId() {
        return posId;
    }
    
    public void setPositionId(long posId) {
        this.posId = posId;
    }    
    
    public Date getStartingReprimandDate() {
        return reprimandDateStart;
    }
    
    public void setStartingReprimandDate(Date reprimandDateStart) {
        this.reprimandDateStart = reprimandDateStart;
    }    
    
    public Date getEndingReprimandDate() {
        return reprimandDateEnd;
    }
    
    public void setEndingReprimandDate(Date reprimandDateEnd) {
        this.reprimandDateEnd = reprimandDateEnd;
    }

    /**
     * @return the divId
     */
    public long getDivisionId() {
        return divId;
    }

    /**
     * @param divId the divId to set
     */
    public void setDivisionId(long divId) {
        this.divId = divId;
    }
    
}
