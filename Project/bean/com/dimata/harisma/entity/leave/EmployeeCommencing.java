/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.leave;
import java.util.Date;
/**
 *
 * @author Tu Roy
 */
public class EmployeeCommencing {
    private long employeeId;
    private Date commencingDate;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public Date getCommencingDate() {
        return commencingDate;
    }

    public void setCommencingDate(Date commencingDate) {
        this.commencingDate = commencingDate;
    }
    
}
