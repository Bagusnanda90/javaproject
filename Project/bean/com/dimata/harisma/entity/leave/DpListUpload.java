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
public class DpListUpload {
    
    private int employeeId;
    private int employeeNum;
    private Date commencingDate;
    private Date aqDate;
    private int dpQty;
    private String note;
    private int dpStatus;
    private Date opDate;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(int employeeNum) {
        this.employeeNum = employeeNum;
    }

    public Date getCommencingDate() {
        return commencingDate;
    }

    public void setCommencingDate(Date commencingDate) {
        this.commencingDate = commencingDate;
    }

    public Date getAqDate() {
        return aqDate;
    }

    public void setAqDate(Date aqDate) {
        this.aqDate = aqDate;
    }

    public int getDpQty() {
        return dpQty;
    }

    public void setDpQty(int dpQty) {
        this.dpQty = dpQty;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getDpStatus() {
        return dpStatus;
    }

    public void setDpStatus(int dpStatus) {
        this.dpStatus = dpStatus;
    }

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }
    
    
}
