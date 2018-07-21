/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.overtime;

import com.dimata.harisma.entity.masterdata.Level;
import java.util.Date;
import java.util.Vector;
import com.dimata.qdep.entity.Entity;
import com.dimata.util.Formater;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.harisma.session.payroll.SessOvertime;
/**
 *
 * @author Wiweka
 */
public class OvertimeDetail extends Entity{

    /**
     * @return the PAID_BY_SALARY
     */
    public static int getPAID_BY_SALARY() {
        return PAID_BY_SALARY;
    }

    /**
     * @param aPAID_BY_SALARY the PAID_BY_SALARY to set
     */
    public static void setPAID_BY_SALARY(int aPAID_BY_SALARY) {
        PAID_BY_SALARY = aPAID_BY_SALARY;
    }

    /**
     * @return the PAID_BY_DAY_OFF
     */
    public static int getPAID_BY_DAY_OFF() {
        return PAID_BY_DAY_OFF;
    }

    /**
     * @param aPAID_BY_DAY_OFF the PAID_BY_DAY_OFF to set
     */
    public static void setPAID_BY_DAY_OFF(int aPAID_BY_DAY_OFF) {
        PAID_BY_DAY_OFF = aPAID_BY_DAY_OFF;
    }
    private long overtimeDetailId;
    private long overtimeId;
    private long employeeId = 0;
    private String payroll="";
    private long empDeptId =0;
    private long positionId=0;    
    private int positionLevel=0;
    private long employeeLevel = 0;
    private int positionDisableDepartmentApproval=1;
    private int positionDisableDivisionApproval=1;
    private String name = "";
    private String jobDesk = "";
    private Date dateFrom = new Date();
    private Date dateTo = new Date();
    private int paidBy=0;
    private int status;
    //update by satrya 2012-12-20
    private int docStatusByOtMain;
    private Date  realDateFrom = null;
    private Date  realDateTo = null;
    
    private long periodId = 0;
    private String employee_num = "";    
    private String work_schedule = "";
    private double duration=0.0d;
    private String ovt_doc_nr = "";
    private long pay_slip_id = 0;
    private String ovt_code = "";
    private double tot_Idx = 0.0;
    private int allowance=0;
    private Date restStart = new Date();
    //private Date restEnd = new Date();
    private double restTimeinHr=0.0d;
    //update by satrya 2012-12-04
    private int flagStatus;
      
    private double totDuration;
    private double  totIdx;
    private int totAllowanceFood;
    private int totAllowanceMoney;
    private int totPaidBySalary;
    private int totPaidBydayOff;
    
    //update by satrya 2014-01-24
    private int manualSetRestTime;
    
    private long locationId;
    
    
    public static int PAID_BY_SALARY=0;
    public static int PAID_BY_DAY_OFF=1;

    public final static String paidByKey[] ={"Salary", "Day Off"};
    public final static int paidByVal[] ={0,1};
    
    public final static double KONSTANTA_MONEY_ALLOWANCE = 10000;
    public static Vector getPaidByKey(){
        Vector key = new Vector();        
        for(int i=0; i < paidByKey.length;i++ ){
            key.add(""+paidByKey[i]);
        }
        return key;
    }

    public static Vector getPaidByVal(){
        Vector val = new Vector();        
        for(int i=0; i < paidByVal.length;i++ ){
            val.add(""+paidByVal[i]);
        }
        return val;
    }

    /**
     * @return the overtimeDetailId
     */
    public long getOvertimeDetailId() {
        return getOID();
    }

    /**
     * @param overtimeDetailId the overtimeDetailId to set
     */
    public void setOvertimeDetailId(long overtimeDetailId) {
        this.setOID(overtimeDetailId);
    }

    /**
     * @return the overtimeId
     */
    public long getOvertimeId() {
        return overtimeId;
    }

    /**
     * @param overtimeId the overtimeId to set
     */
    public void setOvertimeId(long overtimeId) {
        this.overtimeId = overtimeId;
    }

    /**
     * @return the payroll
     */
    public String getPayroll() {
        return payroll;
    }

    /**
     * @param payroll the payroll to set
     */
    public void setPayroll(String payroll) {
        this.payroll = payroll;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the jobDesk
     */
    public String getJobDesk() {
        return jobDesk;
    }

    /**
     * @param jobDesk the jobDesk to set
     */
    public void setJobDesk(String jobDesk) {
        this.jobDesk = jobDesk;
    }

    /**
     * @return the dateFrom
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * @param dateFrom the dateFrom to set
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * @return the dateTo
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * @param dateTo the dateTo to set
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

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
     * @return the empDeptId
     */
    public long getEmpDeptId() {
        return empDeptId;
    }

    /**
     * @param empDeptId the empDeptId to set
     */
    public void setEmpDeptId(long empDeptId) {
        this.empDeptId = empDeptId;
    }

    /**
     * @return the paidBy
     */
    public int getPaidBy() {
        return paidBy;
    }

    /**
     * @param paidBy the paidBy to set
     */
    public void setPaidBy(int paidBy) {
        this.paidBy = paidBy;
    }

    /**
     * @return the positionId
     */
    public long getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the positionId to set
     */
    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }  

    /**
     * @return the positionLevel
     */
    public int getPositionLevel() {
        return positionLevel;
    }

    /**
     * @param positionLevel the positionLevel to set
     */
    public void setPositionLevel(int positionLevel) {
        this.positionLevel = positionLevel;
    }

    /**
     * @return the positionDisableDepartmentApproval
     */
    public int getPositionDisableDepartmentApproval() {
        return positionDisableDepartmentApproval;
    }

    /**
     * @param positionDisableDepartmentApproval the positionDisableDepartmentApproval to set
     */
    public void setPositionDisableDepartmentApproval(int positionDisableDepartmentApproval) {
        this.positionDisableDepartmentApproval = positionDisableDepartmentApproval;
    }

    /**
     * @return the positionDisableDivisionApproval
     */
    public int getPositionDisableDivisionApproval() {
        return positionDisableDivisionApproval;
    }

    /**
     * @param positionDisableDivisionApproval the positionDisableDivisionApproval to set
     */
    public void setPositionDisableDivisionApproval(int positionDisableDivisionApproval) {
        this.positionDisableDivisionApproval = positionDisableDivisionApproval;
    }

    /**
     * @return the realDateFrom
     */
    public Date getRealDateFrom() {
        return realDateFrom;
    }

    /**
     * @param realDateFrom the realDateFrom to set
     */
    public void setRealDateFrom(Date realDateFrom) {
        this.realDateFrom = realDateFrom;
        if(this.getNetDuration()>0.0){ // jika data in out sudah lengkap maka aka status menjadi proceed
            this.setDuration(this.getNetDuration());
            ///update by satrya 2012-12-20
          //// masih ada yg kurang kasusnya jika belum di approvce oleh GM harusnya belum bisa, tpi masih bisa processed
          //jika Main OT  masih final jadi tdk bisa statusnya proced 
            if(this.getStatus()==I_DocStatus.DOCUMENT_STATUS_FINAL && this.getDocStatusByOtMain() == I_DocStatus.DOCUMENT_STATUS_PROCEED){
             this.setStatus(I_DocStatus.DOCUMENT_STATUS_PROCEED);
            }
        }
    }

    /**
     * @return the realDateTo
     */
    public Date getRealDateTo() {
        return realDateTo;        
    }

    /**
     * @param realDateTo the realDateTo to set
     */
    public void setRealDateTo(Date realDateTo) {
        this.realDateTo = realDateTo;
        if(this.getNetDuration()>0.0){ // jika data in out sudah lengkap maka aka status menjadi proceed
            this.setDuration(this.getNetDuration());
               ///update by satrya 2012-12-20
          //// masih ada yg kurang kasusnya jika belum di approvce oleh GM harusnya belum bisa, tpi masih bisa processed
          //jika Main OT  masih final jadi tdk bisa statusnya proced 
            if(this.getStatus()==I_DocStatus.DOCUMENT_STATUS_FINAL  && this.getDocStatusByOtMain() == I_DocStatus.DOCUMENT_STATUS_PROCEED){
             this.setStatus(I_DocStatus.DOCUMENT_STATUS_PROCEED);
            }
        }
    }

    /**
     * Get overtime duration based on plan and attendance
     * The duration is maximum : the planned duration 
     * @return 
     */
    public double getNetDuration(){
     if(this.getFlagStatus()==2){//jika flagsattusnya == 2 maka ini di edit / diketik manual lewat attd daily
         return duration;
    }else{
          if( this.getRealDateFrom()!=null && this.getRealDateTo()!=null && this.getDateFrom()!=null && this.getDateTo()!=null  ){
       
        double durationX = (Formater.formatDurationTime2(
                 this.getRealDateFrom().getTime() >=this.getDateFrom().getTime() ? this.getRealDateFrom() : this.getDateFrom() 
                , this.getRealDateTo().getTime() < this.getDateTo().getTime()  ? this.getRealDateTo() : this.getDateTo() , true)- this.getRestTimeinHr());
        return durationX;
      }else{       
        return 0.0D; 
      }
     }
    }
    
    public double getRoundDuration(){
        double dur =getNetDuration();
        if(SessOvertime.getOvertimeRoundTo()>0 && (dur*60)>SessOvertime.getOvertimeRoundStart() ){ 
            
            dur = ((double)((Math.round(dur*60)/ SessOvertime.getOvertimeRoundTo())*SessOvertime.getOvertimeRoundTo()))/60d;
            return dur;
        } else{
            return dur;
        }
    }
    /**
     * @return the periodId
     */
    public long getPeriodId() {
        return periodId;
    }

    /**
     * @param periodId the periodId to set
     */
    public void setPeriodId(long periodId) {
        this.periodId = periodId;
    }

    /**
     * @return the employee_num
     */
    public String getEmployee_num() {
        return employee_num;
    }

    /**
     * @param employee_num the employee_num to set
     */
    public void setEmployee_num(String employee_num) {
        this.employee_num = employee_num;
    }

    /**
     * @return the work_schedule
     */
    public String getWork_schedule() {
        return work_schedule;
    }

    /**
     * @param work_schedule the work_schedule to set
     */
    public void setWork_schedule(String work_schedule) {
        this.work_schedule = work_schedule;
    }

    /**
     * @return the duration
     */
    public double getDuration() {
        return getNetDuration();
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(double duration) {        
        this.duration = duration;
    }

    /**
     * @return the ovt_doc_nr
     */
    public String getOvt_doc_nr() {
        return ovt_doc_nr;
    }

    /**
     * @param ovt_doc_nr the ovt_doc_nr to set
     */
    public void setOvt_doc_nr(String ovt_doc_nr) {
        this.ovt_doc_nr = ovt_doc_nr;
    }

    /**
     * @return the pay_slip_id
     */
    public long getPay_slip_id() {
        return pay_slip_id;
    }

    /**
     * @param pay_slip_id the pay_slip_id to set
     */
    public void setPay_slip_id(long pay_slip_id) {
        this.pay_slip_id = pay_slip_id;
    }

    /**
     * @return the ovt_code
     */
    public String getOvt_code() {
        return ovt_code;
    }

    /**
     * @param ovt_code the ovt_code to set
     */
    public void setOvt_code(String ovt_code) {
        this.ovt_code = ovt_code;
    }

    /**
     * @return the tot_Idx
     */
    public double getTot_Idx() {
        return tot_Idx;
    }

    /**
     * @param tot_Idx the tot_Idx to set
     */
    public void setTot_Idx(double tot_Idx) {
        this.tot_Idx = tot_Idx;
    }

    /**
     * @return the allowance
     */
    public int getAllowance() {
        return allowance;
    }

    /**
     * @param allowance the allowance to set
     */
    public void setAllowance(int allowance) {
        this.allowance = allowance;
    }

    /**
     * @return the restTimeinHr
     */
    public double getRestTimeinHr() {
        return restTimeinHr;
    }

    /**
     * @param restTimeinHr the restTimeinHr to set
     */
    public void setRestTimeinHr(double restTimeinHr) {
        this.restTimeinHr = restTimeinHr;
        //if(restStart!=null && restTimeinHr > 0.000D ){
        //    this.restEnd = new Date(restStart.getTime()+(long)(restTimeinHr * 3600000.0));            
        //}
    }

    /**
     * @return the restStart
     */
    public Date getRestStart() {
        return restStart;
    }

    /**
     * @param restStart the restStart to set
     */
    public void setRestStart(Date restStart) {
        //update by satrya 2013-11-05 
        Date tmpRestStart=null;
        if(restStart!=null){
             tmpRestStart = (Date)restStart.clone();
        }
       
        this.restStart = tmpRestStart;// update by satrya karena dia membawa nilainya sehingga dia berubah restStart;
        if(this.restStart!=null && this.restTimeinHr>0.0000000000000001){ //update by satrya 2013-11-05 if(this.restStart!=null){
            if(this.dateFrom!=null && this.dateFrom.getTime()>this.restStart.getTime()){
                if(this.dateTo!=null){
                    this.restStart.setTime(this.dateFrom.getTime()+ ((this.dateTo.getTime() - this.dateFrom.getTime())/2));   // jika tanggal dan jam rest time lebih kecil dari dateFrom maka di set 4 jam setelah itu.
                } else {
                    this.restStart.setTime(this.dateFrom.getTime()+ 4*3600000);   // jika tanggal dan jam rest time lebih kecil dari dateFrom maka di set 4 jam setelah itu.
                }
            }else {
                if(this.dateTo!=null && this.dateTo.getTime()<this.restStart.getTime()){
                 if(this.dateFrom!=null){
                    this.restStart.setTime(this.dateFrom.getTime()+ ((this.dateTo.getTime() - this.dateFrom.getTime())/2));   // jika tanggal dan jam rest time lebih kecil dari dateFrom maka di set 4 jam setelah itu.
                 }else{
                    this.restStart.setTime(this.dateTo.getTime()-(4*3600000));   // jika tanggal dan jam rest time lebih kecil dari dateFrom maka di set 4 jam sebelum itu.
                 }
                }                
            }
            
        }
        
        
    }

    /**
     * @return the restEnd
     */
   public Date getRestEnd() {
       Date restEnd = null;
       if(this.restStart!=null && this.restTimeinHr > 0.0001 ){
           restEnd = new Date(this.restStart.getTime() + (long)(this.restTimeinHr * 3600000D));
       }
       
       return restEnd;
   }

    /**
     * @return the flagStatus
     */
    public int getFlagStatus() {
        return flagStatus;
    }

    /**
     * @param flagStatus the flagStatus to set
     */
    public void setFlagStatus(int flagStatus) {
        this.flagStatus = flagStatus;
    }

    /**
     * @return the docStatusByOtMain
     */
    public int getDocStatusByOtMain() {
        return docStatusByOtMain;
    }

    /**
     * @param docStatusByOtMain the docStatusByOtMain to set
     */
    public void setDocStatusByOtMain(int docStatusByOtMain) {
        this.docStatusByOtMain = docStatusByOtMain;
    }

    /**
     * @return the totDuration
     */
    public double getTotDuration() {
        return totDuration;
        // double durTot =getTotDuration();
//        if(SessOvertime.getOvertimeRoundTo()>0 && (totDuration*60)>SessOvertime.getOvertimeRoundStart() ){ 
//            
//            totDuration = ((double)((Math.round(totDuration*60)/ SessOvertime.getOvertimeRoundTo())*SessOvertime.getOvertimeRoundTo()))/60d;
//            return totDuration;
//        } else{
//            return totDuration;
//        }
    }

    /**
     * @param totDuration the totDuration to set
     */
    public void setTotDuration(double totDuration) {
        this.totDuration = totDuration;
    }

    /**
     * @return the totIdx
     */
    public double  getTotIdx() {
        return totIdx;
    }

    /**
     * @param totIdx the totIdx to set
     */
    public void setTotIdx(double totIdx) {
        this.totIdx = totIdx;
    }

    /**
     * @return the totAllowanceFood
     */
    public int getTotAllowanceFood() {
        return totAllowanceFood;
    }

    /**
     * @param totAllowanceFood the totAllowanceFood to set
     */
    public void setTotAllowanceFood(int totAllowanceFood) {
        this.totAllowanceFood = totAllowanceFood;
    }

    /**
     * @return the totAllowanceMoney
     */
    public int getTotAllowanceMoney() {
        return totAllowanceMoney;
    }

    /**
     * @param totAllowanceMoney the totAllowanceMoney to set
     */
    public void setTotAllowanceMoney(int totAllowanceMoney) {
        this.totAllowanceMoney = totAllowanceMoney;
    }

    /**
     * @return the totPaidBySalary
     */
    public int getTotPaidBySalary() {
        return totPaidBySalary;
    }

    /**
     * @param totPaidBySalary the totPaidBySalary to set
     */
    public void setTotPaidBySalary(int totPaidBySalary) {
        this.totPaidBySalary = totPaidBySalary;
    }

    /**
     * @return the totPaidBydayOff
     */
    public int getTotPaidBydayOff() {
        return totPaidBydayOff;
    }

    /**
     * @param totPaidBydayOff the totPaidBydayOff to set
     */
    public void setTotPaidBydayOff(int totPaidBydayOff) {
        this.totPaidBydayOff = totPaidBydayOff;
    }

    /**
     * @return the manualSetRestTime
     */
    public int getManualSetRestTime() {
        return manualSetRestTime;
    }

    /**
     * @param manualSetRestTime the manualSetRestTime to set
     */
    public void setManualSetRestTime(int manualSetRestTime) {
        this.manualSetRestTime = manualSetRestTime;
    }

    /**
     * @return the locationId
     */
    public long getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    /**
     * @return the employeeLevel
     */
    public long getEmployeeMasterLevel() {
        return employeeLevel;
    }

    /**
     * @param employeeLevel the employeeLevel to set
     */
    public void setEmployeeMasterLevel(long employeeLevel) {
        this.employeeLevel = employeeLevel;
    }
    

   
    /**
     * @param restEnd the restEnd to set
     */
    /*public void setRestEnd(Date restEnd) {
        this.restEnd = restEnd;
    }*/
    
}
