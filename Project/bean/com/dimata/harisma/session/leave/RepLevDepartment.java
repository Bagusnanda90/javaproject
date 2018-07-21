/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.leave;

import java.util.Date;

/**
 *
 * @author Roy A.
 */
public class RepLevDepartment {

    /* ====== EMPLOYEE ===== */
    private long employeeId = 0;
    private String full_name = "";
    private String emp_num = "";
    
    /* ===== DEPARTMENT ===== */
    private long departmentId   = 0;
    private String department   = "";
    private int countEmployee   = 0;
    
    /* ===== DAY OF PAYMENT ==== */
    private float DpQtyBeforeStartPeriod      = 0;
    private float DpTknBeforeStartPeriod      = 0;
    private float DpTknExpBeforeStartPeriod   = 0;
    private float DpQtyCurrentPeriod          = 0;
    private float DpTknCurrentPeriod          = 0;
    private float DpTknExpiredCurrentPeriod   = 0;
    
    /* ==== ANNUAL LEAVE ===== */
    private float AlQtyBeforeStartPeriod      = 0;
    private float AlTknBeforeStartPeriod      = 0;
    private float AlQtyCurrentPeriod          = 0;
    private float AlTknCurrentPeriod          = 0;
    private float AlToBeTaken =0;
    /* ===== LONG LEAVE ======= */
    private float LLQtyBeforeStartPeriod      = 0;
    private float LLTknBeforeStartPeriod      = 0;
    private float LLTknExpBeforeStartPeriod   = 0;
    private float LLQtyCurrentPeriod          = 0;
    private float LLTknCurrentPeriod          = 0;
    private float LLTknExpiredCurrentPeriod   = 0;
    
    private Date DateFrom;
    private Date DateTo;
    private int RadioButton;
    private long OidPeriod;
//        /* ===== DAY OF PAYMENT ==== */
//    private int DpQtyBeforeStartPeriod      = 0;
//    private int DpTknBeforeStartPeriod      = 0;
//    private int DpTknExpBeforeStartPeriod   = 0;
//    private int DpQtyCurrentPeriod          = 0;
//    private int DpTknCurrentPeriod          = 0;
//    private int DpTknExpiredCurrentPeriod   = 0;
//    
//    /* ==== ANNUAL LEAVE ===== */
//    private int AlQtyBeforeStartPeriod      = 0;
//    private int AlTknBeforeStartPeriod      = 0;
//    private int AlQtyCurrentPeriod          = 0;
//    private int AlTknCurrentPeriod          = 0;
//    
//    /* ===== LONG LEAVE ======= */
//    private int LLQtyBeforeStartPeriod      = 0;
//    private int LLTknBeforeStartPeriod      = 0;
//    private int LLTknExpBeforeStartPeriod   = 0;
//    private int LLQtyCurrentPeriod          = 0;
//    private int LLTknCurrentPeriod          = 0;
//    private int LLTknExpiredCurrentPeriod   = 0;

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

    /**
     * @return the full_name
     */
    public String getFull_name() {
        return full_name;
    }

    /**
     * @param full_name the full_name to set
     */
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    /**
     * @return the emp_num
     */
    public String getEmp_num() {
        return emp_num;
    }

    /**
     * @param emp_num the emp_num to set
     */
    public void setEmp_num(String emp_num) {
        this.emp_num = emp_num;
    }

    /**
     * @return the departmentId
     */
    public long getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the countEmployee
     */
    public int getCountEmployee() {
        return countEmployee;
    }

    /**
     * @param countEmployee the countEmployee to set
     */
    public void setCountEmployee(int countEmployee) {
        this.countEmployee = countEmployee;
    }

    /**
     * @return the DpQtyBeforeStartPeriod
     */
    public float getDpQtyBeforeStartPeriod() {
        return DpQtyBeforeStartPeriod;
    }

    /**
     * @param DpQtyBeforeStartPeriod the DpQtyBeforeStartPeriod to set
     */
    public void setDpQtyBeforeStartPeriod(float DpQtyBeforeStartPeriod) {
        this.DpQtyBeforeStartPeriod = DpQtyBeforeStartPeriod;
    }

    /**
     * @return the DpTknBeforeStartPeriod
     */
    public float getDpTknBeforeStartPeriod() {
        return DpTknBeforeStartPeriod;
    }

    /**
     * @param DpTknBeforeStartPeriod the DpTknBeforeStartPeriod to set
     */
    public void setDpTknBeforeStartPeriod(float DpTknBeforeStartPeriod) {
        this.DpTknBeforeStartPeriod = DpTknBeforeStartPeriod;
    }

    /**
     * @return the DpTknExpBeforeStartPeriod
     */
    public float getDpTknExpBeforeStartPeriod() {
        return DpTknExpBeforeStartPeriod;
    }

    /**
     * @param DpTknExpBeforeStartPeriod the DpTknExpBeforeStartPeriod to set
     */
    public void setDpTknExpBeforeStartPeriod(float DpTknExpBeforeStartPeriod) {
        this.DpTknExpBeforeStartPeriod = DpTknExpBeforeStartPeriod;
    }

    /**
     * @return the DpQtyCurrentPeriod
     */
    public float getDpQtyCurrentPeriod() {
        return DpQtyCurrentPeriod;
    }

    /**
     * @param DpQtyCurrentPeriod the DpQtyCurrentPeriod to set
     */
    public void setDpQtyCurrentPeriod(float DpQtyCurrentPeriod) {
        this.DpQtyCurrentPeriod = DpQtyCurrentPeriod;
    }

    /**
     * @return the DpTknCurrentPeriod
     */
    public float getDpTknCurrentPeriod() {
        return DpTknCurrentPeriod;
    }

    /**
     * @param DpTknCurrentPeriod the DpTknCurrentPeriod to set
     */
    public void setDpTknCurrentPeriod(float DpTknCurrentPeriod) {
        this.DpTknCurrentPeriod = DpTknCurrentPeriod;
    }

    /**
     * @return the DpTknExpiredCurrentPeriod
     */
    public float getDpTknExpiredCurrentPeriod() {
        return DpTknExpiredCurrentPeriod;
    }

    /**
     * @param DpTknExpiredCurrentPeriod the DpTknExpiredCurrentPeriod to set
     */
    public void setDpTknExpiredCurrentPeriod(float DpTknExpiredCurrentPeriod) {
        this.DpTknExpiredCurrentPeriod = DpTknExpiredCurrentPeriod;
    }

    /**
     * @return the AlQtyBeforeStartPeriod
     */
    public float getAlQtyBeforeStartPeriod() {
        return AlQtyBeforeStartPeriod;
    }

    /**
     * @param AlQtyBeforeStartPeriod the AlQtyBeforeStartPeriod to set
     */
    public void setAlQtyBeforeStartPeriod(float AlQtyBeforeStartPeriod) {
        this.AlQtyBeforeStartPeriod = AlQtyBeforeStartPeriod;
    }

    /**
     * @return the AlTknBeforeStartPeriod
     */
    public float getAlTknBeforeStartPeriod() {
        return AlTknBeforeStartPeriod;
    }

    /**
     * @param AlTknBeforeStartPeriod the AlTknBeforeStartPeriod to set
     */
    public void setAlTknBeforeStartPeriod(float AlTknBeforeStartPeriod) {
        this.AlTknBeforeStartPeriod = AlTknBeforeStartPeriod;
    }

    /**
     * @return the AlQtyCurrentPeriod
     */
    public float getAlQtyCurrentPeriod() {
        return AlQtyCurrentPeriod;
    }

    /**
     * @param AlQtyCurrentPeriod the AlQtyCurrentPeriod to set
     */
    public void setAlQtyCurrentPeriod(float AlQtyCurrentPeriod) {
        this.AlQtyCurrentPeriod = AlQtyCurrentPeriod;
    }

    /**
     * @return the AlTknCurrentPeriod
     */
    public float getAlTknCurrentPeriod() {
        return AlTknCurrentPeriod;
    }

    /**
     * @param AlTknCurrentPeriod the AlTknCurrentPeriod to set
     */
    public void setAlTknCurrentPeriod(float AlTknCurrentPeriod) {
        this.AlTknCurrentPeriod = AlTknCurrentPeriod;
    }

    /**
     * @return the LLQtyBeforeStartPeriod
     */
    public float getLLQtyBeforeStartPeriod() {
        return LLQtyBeforeStartPeriod;
    }

    /**
     * @param LLQtyBeforeStartPeriod the LLQtyBeforeStartPeriod to set
     */
    public void setLLQtyBeforeStartPeriod(float LLQtyBeforeStartPeriod) {
        this.LLQtyBeforeStartPeriod = LLQtyBeforeStartPeriod;
    }

    /**
     * @return the LLTknBeforeStartPeriod
     */
    public float getLLTknBeforeStartPeriod() {
        return LLTknBeforeStartPeriod;
    }

    /**
     * @param LLTknBeforeStartPeriod the LLTknBeforeStartPeriod to set
     */
    public void setLLTknBeforeStartPeriod(float LLTknBeforeStartPeriod) {
        this.LLTknBeforeStartPeriod = LLTknBeforeStartPeriod;
    }

    /**
     * @return the LLTknExpBeforeStartPeriod
     */
    public float getLLTknExpBeforeStartPeriod() {
        return LLTknExpBeforeStartPeriod;
    }

    /**
     * @param LLTknExpBeforeStartPeriod the LLTknExpBeforeStartPeriod to set
     */
    public void setLLTknExpBeforeStartPeriod(float LLTknExpBeforeStartPeriod) {
        this.LLTknExpBeforeStartPeriod = LLTknExpBeforeStartPeriod;
    }

    /**
     * @return the LLQtyCurrentPeriod
     */
    public float getLLQtyCurrentPeriod() {
        return LLQtyCurrentPeriod;
    }

    /**
     * @param LLQtyCurrentPeriod the LLQtyCurrentPeriod to set
     */
    public void setLLQtyCurrentPeriod(float LLQtyCurrentPeriod) {
        this.LLQtyCurrentPeriod = LLQtyCurrentPeriod;
    }

    /**
     * @return the LLTknCurrentPeriod
     */
    public float getLLTknCurrentPeriod() {
        return LLTknCurrentPeriod;
    }

    /**
     * @param LLTknCurrentPeriod the LLTknCurrentPeriod to set
     */
    public void setLLTknCurrentPeriod(float LLTknCurrentPeriod) {
        this.LLTknCurrentPeriod = LLTknCurrentPeriod;
    }

    /**
     * @return the LLTknExpiredCurrentPeriod
     */
    public float getLLTknExpiredCurrentPeriod() {
        return LLTknExpiredCurrentPeriod;
    }

    /**
     * @param LLTknExpiredCurrentPeriod the LLTknExpiredCurrentPeriod to set
     */
    public void setLLTknExpiredCurrentPeriod(float LLTknExpiredCurrentPeriod) {
        this.LLTknExpiredCurrentPeriod = LLTknExpiredCurrentPeriod;
    }

    /**
     * @return the AlToBeTaken
     */
    public float getAlToBeTaken() {
        return AlToBeTaken;
    }

    /**
     * @param AlToBeTaken the AlToBeTaken to set
     */
    public void setAlToBeTaken(float AlToBeTaken) {
        this.AlToBeTaken = AlToBeTaken;
    }

    /**
     * @return the DateFrom
     */
    public Date getDateFrom() {
        return DateFrom;
    }

    /**
     * @param DateFrom the DateFrom to set
     */
    public void setDateFrom(Date DateFrom) {
        this.DateFrom = DateFrom;
    }

    /**
     * @return the DateTo
     */
    public Date getDateTo() {
        return DateTo;
    }

    /**
     * @param DateTo the DateTo to set
     */
    public void setDateTo(Date DateTo) {
        this.DateTo = DateTo;
    }

    /**
     * @return the RadioButton
     */
    public int getRadioButton() {
        return RadioButton;
    }

    /**
     * @param RadioButton the RadioButton to set
     */
    public void setRadioButton(int RadioButton) {
        this.RadioButton = RadioButton;
    }

    /**
     * @return the OidPeriod
     */
    public long getOidPeriod() {
        return OidPeriod;
    }

    /**
     * @param OidPeriod the OidPeriod to set
     */
    public void setOidPeriod(long OidPeriod) {
        this.OidPeriod = OidPeriod;
    }


    
}
