/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.utility.machine;

import java.util.Date;

/**
 *
 * @author Dimata 007
 */
public class MesinAbsensiMessage {
    private Date startDate;
    private Date endDate;
    private int statusHr;
    private int automaticContinueSearch;
    private boolean usePushStop=false;

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

    /**
     * @return the statusHr
     */
    public int getStatusHr() {
        return statusHr;
    }

    /**
     * @param statusHr the statusHr to set
     */
    public void setStatusHr(int statusHr) {
        this.statusHr = statusHr;
    }

    /**
     * @return the automaticContinueSearch
     */
    public int getAutomaticContinueSearch() {
        return automaticContinueSearch;
    }

    /**
     * @param automaticContinueSearch the automaticContinueSearch to set
     */
    public void setAutomaticContinueSearch(int automaticContinueSearch) {
        this.automaticContinueSearch = automaticContinueSearch;
    }

    /**
     * @return the usePushStop
     */
    public boolean isUsePushStop() {
        return usePushStop;
    }

    /**
     * @param usePushStop the usePushStop to set
     */
    public void setUsePushStop(boolean usePushStop) {
        this.usePushStop = usePushStop;
    }
}
