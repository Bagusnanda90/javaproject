/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.attendance;

import java.util.Date;

/**
 *
 * @author satrya Ramayu
 */
public class SummaryEmployeeAttendance {
    private long employeeId;
    private String employeeNum;
    private String fullName;
    private String division;
    private String department;
    private String section;
    private long departmentId;
  
        private long DPStockId = 0;
    private float DPQty=0;
    private float DPTaken=0;
    private float DP2BTaken=0;
        
    private long ALStockId = 0;
    private float ALPrev=0;
    private float ALQty=0;    
    private float ALTaken=0;  
    private float AL2BTaken=0;
        
    private long LLStockId = 0; 
    private float LLPrev=0;
    private float LLQty=0;
    private float LLTaken=0;
    private float LL2BTaken=0;
    private float LLExpdQty=0;
    
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
     * @return the employeeNum
     */
    public String getEmployeeNum() {
        return employeeNum;
    }

    /**
     * @param employeeNum the employeeNum to set
     */
    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
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
     * @return the section
     */
    public String getSection() {
        return section;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
        this.section = section;
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
    public float getDPQty() {
        return DPQty;
    }

    public void setDPQty(float DPQty) {
        this.DPQty = DPQty;
    }

    public float getDPTaken() {
        return DPTaken;
    }

    public void setDPTaken(float DPTaken) {
        this.DPTaken = DPTaken;
    }
    
    public float getDPBalance(){
        return this.DPQty-this.DPTaken;
    }
    
    public float getDPBalanceWth2BTaken(){
        return this.DPQty-this.DPTaken-this.getDP2BTaken();
    }

    public float getALPrev() {
        return ALPrev;
    }

    public void setALPrev(float ALPrev) {
        this.ALPrev = ALPrev;
    }

    public float getALQty() {
        return ALQty;
    }

    public void setALQty(float ALQty) {
        this.ALQty = ALQty;
    }

    public float getALTaken() {
        return ALTaken;
    }

    public void setALTaken(float ALTaken) {
        this.ALTaken = ALTaken;
    }
    
    public float getALTotal(){
        return this.ALPrev+this.ALQty;
    }
    public float getALBalance(){
        return this.ALPrev+this.ALQty-this.ALTaken;
    }

    public float getALBalanceWth2BTaken(){
        return this.ALPrev+this.ALQty-this.ALTaken-this.getAL2BTaken();
    }

    public float getLLPrev() {
        return LLPrev;
    }

    public void setLLPrev(float LLPrev) {
        this.LLPrev = LLPrev;
    }

    public float getLLQty() {
        return LLQty;
    }

    public void setLLQty(float LLQty) {
        this.LLQty = LLQty;
    }

    public float getLLTaken() {
        return LLTaken;
    }

    public void setLLTaken(float LLTaken) {
        this.LLTaken = LLTaken;
    }

    public float getLLTotal(){
        return this.LLPrev+this.LLQty;
    }
    public float getLLBalance(){
        return this.LLPrev+this.LLQty-this.LLTaken;
    }
    
    public float getLLBalanceWth2BTaken(){
        //return this.LLPrev+this.LLQty-this.LLTaken;
        return this.LLPrev+this.LLQty-this.LLTaken-this.getLL2BTaken();
    }

    /**
     * @return the DPStockId
     */
    public long getDPStockId() {
        return DPStockId;
    }

    /**
     * @param DPStockId the DPStockId to set
     */
    public void setDPStockId(long DPStockId) {
        this.DPStockId = DPStockId;
    }

    /**
     * @return the DP2BTaken
     */
    public float getDP2BTaken() {
        return DP2BTaken;
    }

    /**
     * @param DP2BTaken the DP2BTaken to set
     */
    public void setDP2BTaken(float DP2BTaken) {
        this.DP2BTaken = DP2BTaken;
    }

    /**
     * @return the ALStockId
     */
    public long getALStockId() {
        return ALStockId;
    }

    /**
     * @param ALStockId the ALStockId to set
     */
    public void setALStockId(long ALStockId) {
        this.ALStockId = ALStockId;
    }

    /**
     * @return the AL2BTaken
     */
    public float getAL2BTaken() {
        return AL2BTaken;
    }

    /**
     * @param AL2BTaken the AL2BTaken to set
     */
    public void setAL2BTaken(float AL2BTaken) {
        this.AL2BTaken = AL2BTaken;
    }

    /**
     * @return the LLStockId
     */
    public long getLLStockId() {
        return LLStockId;
    }

    /**
     * @param LLStockId the LLStockId to set
     */
    public void setLLStockId(long LLStockId) {
        this.LLStockId = LLStockId;
    }

    /**
     * @return the LL2BTaken
     */
    public float getLL2BTaken() {
        return LL2BTaken;
    }

    /**
     * @param LL2BTaken the LL2BTaken to set
     */
    public void setLL2BTaken(float LL2BTaken) {
        this.LL2BTaken = LL2BTaken;
    }

    /**
     * @return the LLExpdQty
     */
    public float getLLExpdQty() {
        return LLExpdQty;
    }

    /**
     * @param LLExpdQty the LLExpdQty to set
     */
    public void setLLExpdQty(float LLExpdQty) {
        this.LLExpdQty = LLExpdQty;
    }

   
    
}
