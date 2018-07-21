/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.leave;
import java.util.Date;

/**
 *
 * @author Tu Roy
 */
public class listDpUpload {
    
    //table employee
    private long employee_id;
    private String employee_num;
    private Date commencing_date;
    private long department_id;
    private String full_name;
    
    //table dp stock management
    private long dp_stock_id;
    private float dp_qty;
    private Date owning_date;
    private Date expired_date;
    private int exception_flag;
    private Date expired_date_exc;
    private int dp_status;
    private String note;
    private float qty_used;
    private float qty_residue;
    
    //table dp upload
    private long dp_upload_id;
    private float dp_qty_upload;
    private Date dp_aq_date;
    private Date opname_date;
    
    private int data_status;

    //table employee
    public long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(long employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_num() {
        return employee_num;
    }

    public void setEmployee_num(String employee_num) {
        this.employee_num = employee_num;
    }

    public Date getCommencing_date() {
        return commencing_date;
    }

    public void setCommencing_date(Date commencing_date) {
        this.commencing_date = commencing_date;
    }

    public long getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(long department_id) {
        this.department_id = department_id;
    }


    //table dp stock management
    public long getDp_stock_id() {
        return dp_stock_id;
    }

    public void setDp_stock_id(long dp_stock_id) {
        this.dp_stock_id = dp_stock_id;
    }

    public float getDp_qty() {
        return dp_qty;
    }

    public void setDp_qty(float dp_qty) {
        this.dp_qty = dp_qty;
    }

    public Date getOwning_date() {
        return owning_date;
    }

    public void setOwning_date(Date owning_date) {
        this.owning_date = owning_date;
    }

    public Date getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(Date expired_date) {
        this.expired_date = expired_date;
    }

    public int getException_flag() {
        return exception_flag;
    }

    public void setException_flag(int exception_flag) {
        this.exception_flag = exception_flag;
    }

    public Date getExpired_date_exc() {
        return expired_date_exc;
    }

    public void setExpired_date_exc(Date expired_date_exc) {
        this.expired_date_exc = expired_date_exc;
    }

    public int getDp_status() {
        return dp_status;
    }

    public void setDp_status(int dp_status) {
        this.dp_status = dp_status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getQty_used() {
        return qty_used;
    }

    public void setQty_used(float qty_used) {
        this.qty_used = qty_used;
    }

    public float getQty_residue() {
        return qty_residue;
    }

    public void setQty_residue(float qty_residue) {
        this.qty_residue = qty_residue;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public long getDp_upload_id() {
        return dp_upload_id;
    }

    public void setDp_upload_id(long dp_upload_id) {
        this.dp_upload_id = dp_upload_id;
    }

    public float getDp_qty_upload() {
        return dp_qty_upload;
    }

    public void setDp_qty_upload(float dp_qty_upload) {
        this.dp_qty_upload = dp_qty_upload;
    }

    public Date getDp_aq_date() {
        return dp_aq_date;
    }

    public void setDp_aq_date(Date dp_aq_date) {
        this.dp_aq_date = dp_aq_date;
    }

    public Date getOpname_date() {
        return opname_date;
    }

    public void setOpname_date(Date opname_date) {
        this.opname_date = opname_date;
    }

    public int getData_status() {
        return data_status;
    }

    public void setData_status(int data_status) {
        this.data_status = data_status;
    }
}
