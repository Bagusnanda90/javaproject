/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.attendance;

/**
 *
 * @author Dimata 007
 */
public class ReasonCount {
    private long empId;
    private int Countreason;
    //private String reasonName;
    private int ReasonNo;

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
     * @return the Countreason
     */
    public int getCountreason() {
        return Countreason;
    }

    /**
     * @param Countreason the Countreason to set
     */
    public void setCountreason(int Countreason) {
        this.Countreason = Countreason;
    }

   

    /**
     * @return the ReasonNo
     */
    public int getReasonNo() {
        return ReasonNo;
    }

    /**
     * @param ReasonNo the ReasonNo to set
     */
    public void setReasonNo(int ReasonNo) {
        this.ReasonNo = ReasonNo;
    }
}
