/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.leave;

import java.util.Date;

/**
 *
 * @author Satrya
 */
public class LeaveOidSym {
    private long LeaveOid;
    private String LeaveSymbol;
    //update by satrya 2012-08-12
    private Date takenDate;
    private Date finnishDate;

    /**
     * @return the LeaveOid
     */
    public long getLeaveOid() {
        return LeaveOid;
    }

    /**
     * @param LeaveOid the LeaveOid to set
     */
    public void setLeaveOid(long LeaveOid) {
        this.LeaveOid = LeaveOid;
    }

    /**
     * @return the LeaveSymbol
     */
    public String getLeaveSymbol() {
        return LeaveSymbol;
    }

    /**
     * @param LeaveSymbol the LeaveSymbol to set
     */
    public void setLeaveSymbol(String LeaveSymbol) {
        this.LeaveSymbol = LeaveSymbol;
    }

    /**
     * @return the takenDate
     */
    public Date getTakenDate() {
        return takenDate;
}

    /**
     * @param takenDate the takenDate to set
     */
    public void setTakenDate(Date takenDate) {
        this.takenDate = takenDate;
    }

    /**
     * @return the finnishDate
     */
    public Date getFinnishDate() {
        return finnishDate;
    }

    /**
     * @param finnishDate the finnishDate to set
     */
    public void setFinnishDate(Date finnishDate) {
        this.finnishDate = finnishDate;
    }

   
}
