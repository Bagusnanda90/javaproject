/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.leave;

import java.util.Date;

/**
 *
 * @author satrya ramayu
 */
public class SessLeaveApplicationEmail {
    private Date takenDateStart;
    private Date takenFinish;
    private String symbole;
    private String fullNameDepHead;
    private String fullNameHr;
    private Date dateDepHeadApprovall;
    private Date dateHrApprovall;
    private Date dtOfRequest;
    //update by satrya 2014-01-17
    private long employeeId;

    /**
     * @return the takenDateStart
     */
    public Date getTakenDateStart() {
        return takenDateStart;
    }

    /**
     * @param takenDateStart the takenDateStart to set
     */
    public void setTakenDateStart(Date takenDateStart) {
        this.takenDateStart = takenDateStart;
    }

    /**
     * @return the takenFinish
     */
    public Date getTakenFinish() {
        return takenFinish;
    }

    /**
     * @param takenFinish the takenFinish to set
     */
    public void setTakenFinish(Date takenFinish) {
        this.takenFinish = takenFinish;
    }

    /**
     * @return the symbole
     */
    public String getSymbole() {
        return symbole;
    }

    /**
     * @param symbole the symbole to set
     */
    public void setSymbole(String symbole) {
        this.symbole = symbole;
    }

    /**
     * @return the fullNameDepHead
     */
    public String getFullNameDepHead() {
        return fullNameDepHead;
    }

    /**
     * @param fullNameDepHead the fullNameDepHead to set
     */
    public void setFullNameDepHead(String fullNameDepHead) {
        this.fullNameDepHead = fullNameDepHead;
    }

    /**
     * @return the fullNameHr
     */
    public String getFullNameHr() {
        return fullNameHr;
    }

    /**
     * @param fullNameHr the fullNameHr to set
     */
    public void setFullNameHr(String fullNameHr) {
        this.fullNameHr = fullNameHr;
    }

    /**
     * @return the dateDepHeadApprovall
     */
    public Date getDateDepHeadApprovall() {
        return dateDepHeadApprovall;
    }

    /**
     * @param dateDepHeadApprovall the dateDepHeadApprovall to set
     */
    public void setDateDepHeadApprovall(Date dateDepHeadApprovall) {
        this.dateDepHeadApprovall = dateDepHeadApprovall;
    }

    /**
     * @return the dateHrApprovall
     */
    public Date getDateHrApprovall() {
        return dateHrApprovall;
    }

    /**
     * @param dateHrApprovall the dateHrApprovall to set
     */
    public void setDateHrApprovall(Date dateHrApprovall) {
        this.dateHrApprovall = dateHrApprovall;
    }

    /**
     * @return the dtOfRequest
     */
    public Date getDtOfRequest() {
        return dtOfRequest;
    }

    /**
     * @param dtOfRequest the dtOfRequest to set
     */
    public void setDtOfRequest(Date dtOfRequest) {
        this.dtOfRequest = dtOfRequest;
    }

    /**
     * @return the employeeId
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
    
}
