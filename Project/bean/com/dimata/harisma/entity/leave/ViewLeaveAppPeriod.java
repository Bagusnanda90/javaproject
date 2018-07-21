/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.leave;

// import core java package
import java.util.Date;

// package qdep
import com.dimata.qdep.entity.*;

/**
 *
 * @author Tu Roy
 */

public class ViewLeaveAppPeriod extends Entity{
    
    private long employee_id = 0;
    private Date al_start_date;
    private Date al_end_date;
    private Date ll_start_date;
    private Date ll_end_date;
    private Date dp_start_date;
    private Date dp_end_date;
    private Date sp_start_date;
    private Date sp_end_date;
    private Long leave_Application_id;

    public long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(long employee_id) {
        this.employee_id = employee_id;
    }

    public Date getAl_start_date() {
        return al_start_date;
    }

    public void setAl_start_date(Date al_start_date) {
        this.al_start_date = al_start_date;
    }

    public Date getAl_end_date() {
        return al_end_date;
    }

    public void setAl_end_date(Date al_end_date) {
        this.al_end_date = al_end_date;
    }

    public Date getLl_start_date() {
        return ll_start_date;
    }

    public void setLl_start_date(Date ll_start_date) {
        this.ll_start_date = ll_start_date;
    }

    public Date getLl_end_date() {
        return ll_end_date;
    }

    public void setLl_end_date(Date ll_end_date) {
        this.ll_end_date = ll_end_date;
    }

    public Date getDp_start_date() {
        return dp_start_date;
    }

    public void setDp_start_date(Date dp_start_date) {
        this.dp_start_date = dp_start_date;
    }

    public Date getDp_end_date() {
        return dp_end_date;
    }

    public void setDp_end_date(Date dp_end_date) {
        this.dp_end_date = dp_end_date;
    }

    public Date getSp_start_date() {
        return sp_start_date;
    }

    public void setSp_start_date(Date sp_start_date) {
        this.sp_start_date = sp_start_date;
    }

    public Date getSp_end_date() {
        return sp_end_date;
    }

    public void setSp_end_date(Date sp_end_date) {
        this.sp_end_date = sp_end_date;
    }

    public Long getLeave_Application_id() {
        return leave_Application_id;
    }

    public void setLeave_Application_id(Long leave_Application_id) {
        this.leave_Application_id = leave_Application_id;
    }
}
