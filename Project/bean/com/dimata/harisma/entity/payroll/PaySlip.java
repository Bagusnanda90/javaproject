/*
 * PaySlip.java
 *
 * Created on April 24, 2007, 4:29 PM
 */

package com.dimata.harisma.entity.payroll;

/* package java */ 
import java.util.Date;
import java.util.Vector;

/* package qdep */
import com.dimata.qdep.entity.*;

/**
 *
 * @author  yunny
 */
public class PaySlip extends Entity{
    public static final int MODEL_UP_DOWN = 0;
    public static final int MODEL_LEFT_RIGHT = 1;

    
    private long periodId;
    private long employeeId;
    private int status=0;
    private int paidStatus=0;
    private Date paySlipDate;
    private double dayPresent;
    private double dayPaidLv;
    private double dayAbsent;
    private double dayUnpaidLv;
    private String division="";
    private String department="";
    private String position="";
    private String section="";
    private String note="";
    private Date commencDate;
    private long paymentType;
    private long bankId;
    private int paySlipType=0;
    //private String bankAddress="";
    /**
     * company code / company name
     */
    private String compCode ="";
    private double dayLate;
    private double procentasePresence;
    private Vector<PaySlipComp> paySlipComps = null;
    private String bankName;
    
    //update by satrya 2013-02-20
    private double dayOffSch;
    private double totDayOffOt;
    private double insentif;
    private double daysOkWithLeave;
    
        //UPDATE BY SATRYA 2013-05-06
    private double ovIdxAdj;
    private String privateNote="";
    //update by satrya 2014-02-06
    private double overtimeIdxByForm;
    private double mealAllowanceMoneyByForm;
    private double mealAllowanceMoneyAdj;
    private String noteAdmin;
    /** Creates a new instance of PaySlip */
    //update by satrya 2014-05-01
    private Date ovIdxPaidBySalaryAdjDt;
    private Date ovAllowanceMoneyAdjDt;
    public PaySlip() {
    }
    
    /**
     * Getter for property periodId.
     * @return Value of property periodId.
     */
    public long getPeriodId() {
        return periodId;
    }
    
    /**
     * Setter for property periodId.
     * @param periodId New value of property periodId.
     */
    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }
    
    /**
     * Getter for property employeeId.
     * @return Value of property employeeId.
     */
    public long getEmployeeId() {
        return employeeId;
    }
    
    /**
     * Setter for property employeeId.
     * @param employeeId New value of property employeeId.
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
    
    /**
     * Getter for property status.
     * @return Value of property status.
     */
    public int getStatus() {
        return status;
    }
    
    /**
     * Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(int status) {
        this.status = status;
    }
    
    /**
     * Getter for property paidStatus.
     * @return Value of property paidStatus.
     */
    public int getPaidStatus() {
        return paidStatus;
    }
    
    /**
     * Setter for property paidStatus.
     * @param paidStatus New value of property paidStatus.
     */
    public void setPaidStatus(int paidStatus) {
        this.paidStatus = paidStatus;
    }
    
    /**
     * Getter for property paySlipDate.
     * @return Value of property paySlipDate.
     */
    public Date getPaySlipDate() {
        return paySlipDate;
    }
    
    /**
     * Setter for property paySlipDate.
     * @param paySlipDate New value of property paySlipDate.
     */
    public void setPaySlipDate(Date paySlipDate) {
        this.paySlipDate = paySlipDate;
    }
    
    /**
     * Getter for property dayPresent.
     * @return Value of property dayPresent.
     */
    public double getDayPresent() {
        return dayPresent;
    }
    
    /**
     * Setter for property dayPresent.
     * @param dayPresent New value of property dayPresent.
     */
    public void setDayPresent(double dayPresent) {
        this.dayPresent = dayPresent;
    }
    
    /**
     * Getter for property dayPaidLv.
     * @return Value of property dayPaidLv.
     */
    public double getDayPaidLv() {
        return dayPaidLv;
    }
    
    /**
     * Setter for property dayPaidLv.
     * @param dayPaidLv New value of property dayPaidLv.
     */
    public void setDayPaidLv(double dayPaidLv) {
        this.dayPaidLv = dayPaidLv;
    }
    
    /**
     * Getter for property dayAbsent.
     * @return Value of property dayAbsent.
     */
    public double getDayAbsent() {
        return dayAbsent;
    }
    
    /**
     * Setter for property dayAbsent.
     * @param dayAbsent New value of property dayAbsent.
     */
    public void setDayAbsent(double dayAbsent) {
        this.dayAbsent = dayAbsent;
    }
    
    /**
     * Getter for property dayUnpaidLv.
     * @return Value of property dayUnpaidLv.
     */
    public double getDayUnpaidLv() {
        return dayUnpaidLv;
    }
    
    /**
     * Setter for property dayUnpaidLv.
     * @param dayUnpaidLv New value of property dayUnpaidLv.
     */
    public void setDayUnpaidLv(double dayUnpaidLv) {
        this.dayUnpaidLv = dayUnpaidLv;
    }
    
    /**
     * Getter for property division.
     * @return Value of property division.
     */
    public String getDivision() {
        return division;
    }
    
    /**
     * Setter for property division.
     * @param division New value of property division.
     */
    public void setDivision(String division) {
        if(division== null){
            division = "";
        }
        this.division = division;
    }
    
    /**
     * Getter for property department.
     * @return Value of property department.
     */
    public String getDepartment() {
        return department;
    }
    
    /**
     * Setter for property department.
     * @param department New value of property department.
     */
    public void setDepartment(String department) {
        if(department==null){
            department = "";
        }
        this.department = department;
    }
    
    /**
     * Getter for property position.
     * @return Value of property position.
     */
    public String getPosition() {
        return position;
    }
    
    /**
     * Setter for property position.
     * @param position New value of property position.
     */
    public void setPosition(String position) {
        if(position==null){
            position="";
        }
        this.position = position;
    }
    
    /**
     * Getter for property section.
     * @return Value of property section.
     */
    public String getSection() {
        return section;
    }
    
    /**
     * Setter for property section.
     * @param section New value of property section.
     */
    public void setSection(String section) {
        if(section==null){
            section="";
        }
        this.section = section;
    }
    
    /**
     * Getter for property note.
     * @return Value of property note.
     */
    public String getNote() {
        return note;
    }
    
    /**
     * Setter for property note.
     * @param note New value of property note.
     */
    public void setNote(String note) {
        if(note==null){
            note="";
         }
        this.note = note;
    }
    
    /**
     * Getter for property commencDate.
     * @return Value of property commencDate.
     */
    public Date getCommencDate() {
        return commencDate;
    }
    
    /**
     * Setter for property commencDate.
     * @param commencDate New value of property commencDate.
     */
    public void setCommencDate(Date commencDate) {
        this.commencDate = commencDate;
    }
    
    /**
     * Getter for property paymentType.
     * @return Value of property paymentType.
     */
    public long getPaymentType() {
        return paymentType;
    }
    
    /**
     * Setter for property paymentType.
     * @param paymentType New value of property paymentType.
     */
    public void setPaymentType(long paymentType) {
        this.paymentType = paymentType;
    }
    
    /**
     * Getter for property bankId.
     * @return Value of property bankId.
     */
    public long getBankId() {
        return bankId;
    }
    
    /**
     * Setter for property bankId.
     * @param bankId New value of property bankId.
     */
    public void setBankId(long bankId) {
        this.bankId = bankId;
    }
    
    /**
     * Getter for property paySlipType.
     * @return Value of property paySlipType.
     */
    public int getPaySlipType() {
        return paySlipType;
    }
    
    /**
     * Setter for property paySlipType.
     * @param paySlipType New value of property paySlipType.
     */
    public void setPaySlipType(int paySlipType) {
        this.paySlipType = paySlipType;
    }
    
    /**
     * Getter for property compCode.
     * @return Value of property compCode.
     */
    public String getCompCode() {
        return compCode;
    }
    
    /**
     * Setter for property compCode.
     * @param compCode New value of property compCode.
     */
    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }
    
    /**
     * Getter for property dayLate.
     * @return Value of property dayLate.
     */
    public double getDayLate() {
        return dayLate;
    }
    
    /**
     * Setter for property dayLate.
     * @param dayLate New value of property dayLate.
     */
    public void setDayLate(double dayLate) {
        this.dayLate = dayLate;
    }
    
    /**
     * Getter for property procentasePresence.
     * @return Value of property procentasePresence.
     */
    public double getProcentasePresence() {
        return procentasePresence;
    }
    
    /**
     * Setter for property procentasePresence.
     * @param procentasePresence New value of property procentasePresence.
     */
    public void setProcentasePresence(double procentasePresence) {
        this.procentasePresence = procentasePresence;
    }

    /**
     * @return the paySlipComp
     */
    public Vector<PaySlipComp> getPaySlipComps() {
        return paySlipComps;
    }
    
    public void addPaySlipComp(PaySlipComp aSlipComp) {        
        if(aSlipComp!=null){
           if(paySlipComps==null)  {
               paySlipComps = new Vector();             
           } 
           paySlipComps.add(aSlipComp);
        }
    }        
    
    public PaySlipComp getPaySlipComp(int idx) {        
        if(idx>=0 && paySlipComps!=null && paySlipComps.size()>idx ){                         
               return paySlipComps.get(idx);           
        }
        return null;
    }        
    
    public int getPaySlipCompSize() {        
        if(paySlipComps!=null && paySlipComps.size()>0 ){                         
               return paySlipComps.size();           
        }
        return 0;
    }

    /**
     * @return the bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName the bankName to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * @return the dayOffSch
     */
    public double getDayOffSch() {
        return dayOffSch;
    }

    /**
     * @param dayOffSch the dayOffSch to set
     */
    public void setDayOffSch(double dayOffSch) {
        this.dayOffSch = dayOffSch;
    }

    /**
     * @return the totDayOffOt
     */
    public double getTotDayOffOt() {
        return totDayOffOt;
    }

    /**
     * @param totDayOffOt the totDayOffOt to set
     */
    public void setTotDayOffOt(double totDayOffOt) {
        this.totDayOffOt = totDayOffOt;
    }

    /**
     * @return the insentif
     */
    public double getInsentif() {
        return insentif;
    }

    /**
     * @param insentif the insentif to set
     */
    public void setInsentif(double insentif) {
        this.insentif = insentif;
    }

    /**
     * @return the daysOkWithLeave
     */
    public double getDaysOkWithLeave() {
        return daysOkWithLeave;
    }

    /**
     * @param daysOkWithLeave the daysOkWithLeave to set
     */
    public void setDaysOkWithLeave(double daysOkWithLeave) {
        this.daysOkWithLeave = daysOkWithLeave;
    }

    /**
     * @return the ovIdxAdj
     */
    public double getOvIdxAdj() {
        return ovIdxAdj;
    }

    /**
     * @param ovIdxAdj the ovIdxAdj to set
     */
    public void setOvIdxAdj(double ovIdxAdj) {
        this.ovIdxAdj = ovIdxAdj;
    }

    /**
     * @return the privateNote
     */
    public String getPrivateNote() {
        return privateNote;
    }

    /**
     * @param privateNote the privateNote to set
     */
    public void setPrivateNote(String privateNote) {
        this.privateNote = privateNote;
    }

    /**
     * @return the overtimeIdxByForm
     */
    public double getOvertimeIdxByForm() {
        return overtimeIdxByForm;
    }

    /**
     * @param overtimeIdxByForm the overtimeIdxByForm to set
     */
    public void setOvertimeIdxByForm(double overtimeIdxByForm) {
        this.overtimeIdxByForm = overtimeIdxByForm;
    }

    /**
     * @return the mealAllowanceMoneyByForm
     */
    public double getMealAllowanceMoneyByForm() {
        return mealAllowanceMoneyByForm;
    }

    /**
     * @param mealAllowanceMoneyByForm the mealAllowanceMoneyByForm to set
     */
    public void setMealAllowanceMoneyByForm(double mealAllowanceMoneyByForm) {
        this.mealAllowanceMoneyByForm = mealAllowanceMoneyByForm;
    }

    /**
     * @return the mealAllowanceMoneyAdj
     */
    public double getMealAllowanceMoneyAdj() {
        return mealAllowanceMoneyAdj;
    }

    /**
     * @param mealAllowanceMoneyAdj the mealAllowanceMoneyAdj to set
     */
    public void setMealAllowanceMoneyAdj(double mealAllowanceMoneyAdj) {
        this.mealAllowanceMoneyAdj = mealAllowanceMoneyAdj;
    }

    /**
     * @return the noteAdmin
     */
    public String getNoteAdmin() {
        return noteAdmin;
    }

    /**
     * @param noteAdmin the noteAdmin to set
     */
    public void setNoteAdmin(String noteAdmin) {
        this.noteAdmin = noteAdmin;
    }

    /**
     * @return the ovIdxPaidBySalaryAdjDt
     */
    public Date getOvIdxPaidBySalaryAdjDt() {
        return ovIdxPaidBySalaryAdjDt;
    }

    /**
     * @param ovIdxPaidBySalaryAdjDt the ovIdxPaidBySalaryAdjDt to set
     */
    public void setOvIdxPaidBySalaryAdjDt(Date ovIdxPaidBySalaryAdjDt) {
        this.ovIdxPaidBySalaryAdjDt = ovIdxPaidBySalaryAdjDt;
    }

    /**
     * @return the ovAllowanceMoneyAdjDt
     */
    public Date getOvAllowanceMoneyAdjDt() {
        return ovAllowanceMoneyAdjDt;
    }

    /**
     * @param ovAllowanceMoneyAdjDt the ovAllowanceMoneyAdjDt to set
     */
    public void setOvAllowanceMoneyAdjDt(Date ovAllowanceMoneyAdjDt) {
        this.ovAllowanceMoneyAdjDt = ovAllowanceMoneyAdjDt;
    }

   

}
