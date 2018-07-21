/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.leave;

/**
 *
 * @author D
 */
public class RepItemLeaveAndDp {
    private long departmentOID =0;
    private String depName="";
    private float empQty=0;
    
    //update by satrya 2013-10-25
    private long companyOID =0;
    private String companyName;
    
    private long divisionId=0;
    
    private long sectionOID=0;
    private String secName="";
    
    private long employeeId=0;
    private String payrollNum="";
    private String employeeName="";
    
    private String divisionName;
    private String sectionName;
    
    private long DPStockId = 0;
    private float DPQty=0;
    private float DPTaken=0;
    private float DP2BTaken=0;
        
    private long ALStockId = 0;
    private float ALPrev=0;
    private float ALQty=0;    
    private float ALTaken=0;  
    private float AL2BTaken=0;
    //update by satrya 2013-10-10
    private float ALEntitle=0;
        
    private long LLStockId = 0; 
    private float LLPrev=0;
    private float LLQty=0;
    private float LLTaken=0;
    private float LL2BTaken=0;
    private float LLExpdQty=0;
    //update by satrya 2013-10-10
    private float LLEntitle=0;
    private float LLEntitle2=0;
    
    //update by satrya 2013-04-11
    private int statusKaryawan;

    public long getDepartmentOID() {
        return departmentOID;
    }

    public void setDepartmentOID(long departmentOID) {
        this.departmentOID = departmentOID;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
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
        return this.DPQty-this.DPTaken-this.DP2BTaken;
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
        //update by satrya 2013-10-18
        //return this.ALPrev+this.ALQty;
        return this.ALPrev+this.ALEntitle;
    }
    public float getALBalance(){
        //update by satrya 2013-10-18
        //return this.ALPrev+this.ALQty-this.ALTaken;
         return (this.ALPrev+this.ALEntitle)-this.ALTaken - this.AL2BTaken;
    }

    public float getALBalanceWth2BTaken(){
        //update by satrya 2013-10-18
        //return this.ALPrev+this.ALQty-this.ALTaken-this.AL2BTaken;
        return (this.ALPrev+this.ALEntitle)-this.ALTaken-this.AL2BTaken;
    }

    public float getLLPrev() {
        return LLPrev;
    }

    public void setLLPrev(float LLPrev) {
        this.LLPrev = LLPrev;
    }

    public float getLLQty() {
        //update by satrya 2013-11-22 return LLQty;
        return this.LLEntitle + this.LLEntitle2;
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
        // update by satrya 2013-11-22 return this.LLPrev+this.LLQty;
        return this.LLPrev+this.LLEntitle + this.LLEntitle2;
    }
    public float getLLBalance(){
        //update by satrya 2013-11-22 return this.LLPrev+this.LLQty-this.LLTaken;
        return  this.LLPrev+this.LLEntitle + this.LLEntitle2 -this.LLTaken;
    }
    
    public float getLLBalanceWth2BTaken(){
        //return this.LLPrev+this.LLQty-this.LLTaken;
        
       //update by satrya 2013-11-22  return this.LLPrev+this.LLQty-this.LLTaken-this.LL2BTaken;
        return this.LLPrev+this.LLEntitle + this.LLEntitle2-this.LLTaken-this.LL2BTaken;
        
    }    

    public float getEmpQty() {
        return empQty;
    }

    public void setEmpQty(float empQty) {
        this.empQty = empQty;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getPayrollNum() {
        return payrollNum;
    }

    public void setPayrollNum(String payrollNum) {
        this.payrollNum = payrollNum;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public long getSectionOID() {
        return sectionOID;
    }

    public void setSectionOID(long sectionOID) {
        this.sectionOID = sectionOID;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public float getDP2BTaken() {
        return DP2BTaken;
    }

    public void setDP2BTaken(float DP2BTaken) {
        this.DP2BTaken = DP2BTaken;
    }

    public float getAL2BTaken() {
        return AL2BTaken;
    }

    public void setAL2BTaken(float AL2BTaken) {
        this.AL2BTaken = AL2BTaken;
    }

    public float getLL2BTaken() {
        return LL2BTaken;
    }

    public void setLL2BTaken(float LL2BTaken) {
        this.LL2BTaken = LL2BTaken;
    }

    public float getLLExpdQty() {
        return LLExpdQty;
    }

    public void setLLExpdQty(float LLExpdQty) {
        this.LLExpdQty = LLExpdQty;
    }

    public long getDPStockId() {
        return DPStockId;
    }

    public void setDPStockId(long DPStockId) {
        this.DPStockId = DPStockId;
    }

    public long getALStockId() {
        return ALStockId;
    }

    public void setALStockId(long ALStockId) {
        this.ALStockId = ALStockId;
    }

    public long getLLStockId() {
        return LLStockId;
    }

    public void setLLStockId(long LLStockId) {
        this.LLStockId = LLStockId;
    }

    /**
     * @return the divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * @param divisionName the divisionName to set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * @return the sectionName
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * @param sectionName the sectionName to set
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * @return the statusKaryawan
     */
    public int getStatusKaryawan() {
        return statusKaryawan;
    }

    /**
     * @param statusKaryawan the statusKaryawan to set
     */
    public void setStatusKaryawan(int statusKaryawan) {
        this.statusKaryawan = statusKaryawan;
    }

    /**
     * @return the ALEntitle
     */
    public float getALEntitle() {
        return ALEntitle;
    }

    /**
     * @param ALEntitle the ALEntitle to set
     */
    public void setALEntitle(float ALEntitle) {
        this.ALEntitle = ALEntitle;
    }

    /**
     * @return the LLEntitle
     */
    public float getLLEntitle() {
        return LLEntitle;
    }

    /**
     * @param LLEntitle the LLEntitle to set
     */
    public void setLLEntitle(float LLEntitle) {
        this.LLEntitle = LLEntitle;
    }

    /**
     * @return the LLEntitle2
     */
    public float getLLEntitle2() {
        return LLEntitle2;
    }

    /**
     * @param LLEntitle2 the LLEntitle2 to set
     */
    public void setLLEntitle2(float LLEntitle2) {
        this.LLEntitle2 = LLEntitle2;
    }

    /**
     * @return the companyOID
     */
    public long getCompanyOID() {
        return companyOID;
    }

    /**
     * @param companyOID the companyOID to set
     */
    public void setCompanyOID(long companyOID) {
        this.companyOID = companyOID;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the divisionId
     */
    public long getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId the divisionId to set
     */
    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }
   
}
