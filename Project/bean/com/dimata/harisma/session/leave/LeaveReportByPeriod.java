/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.leave;

import java.util.Date;

/**
 *
 * @author roy ajus
 */
public class LeaveReportByPeriod {

    /* get Period*/
    private Date startPeriod;
    private Date endPeriod;

    public Date getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(Date startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }
    
}
