/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.logrpt;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author dimata005
 */
public class LogNotification extends Entity{
    private long reportId;
    private long userId;
    private String logNotification="";
    private int statusNotification;
    private Date dateNotification;
    /**
     * @return the reportId
     */
    public long getReportId() {
        return reportId;
    }

    /**
     * @param reportId the reportId to set
     */
    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the logNotification
     */
    public String getLogNotification() {
        return logNotification;
    }

    /**
     * @param logNotification the logNotification to set
     */
    public void setLogNotification(String logNotification) {
        this.logNotification = logNotification;
    }

    /**
     * @return the statusNotification
     */
    public int getStatusNotification() {
        return statusNotification;
    }

    /**
     * @param statusNotification the statusNotification to set
     */
    public void setStatusNotification(int statusNotification) {
        this.statusNotification = statusNotification;
    }

    /**
     * @return the dateNotification
     */
    public Date getDateNotification() {
        return dateNotification;
    }

    /**
     * @param dateNotification the dateNotification to set
     */
    public void setDateNotification(Date dateNotification) {
        this.dateNotification = dateNotification;
    }

}
