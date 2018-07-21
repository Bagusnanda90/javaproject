/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.logrpt;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author dimata005
 */
public class LogSubscribe extends Entity{
    private long userId;
    private long reportId;

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
    
}
