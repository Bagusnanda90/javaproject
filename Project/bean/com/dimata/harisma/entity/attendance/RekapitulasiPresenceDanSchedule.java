/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.attendance;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.util.Formater;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class RekapitulasiPresenceDanSchedule {

    private long employeeId = 0;
    private int dayOffSchedule = 0;
    private int dayOffScheduleH = 0;
    private int dayOffScheduleEO = 0;
    //private int dtOffOvertime = 0;
    private int insentif = 0;
    private int presenceOnTime = 0;
    private float presenceOnTimeTime = 0;
    private int late = 0;//ini late 
    private float lateTime = 0;
    private int totalLateLebihLimaMenit=0;
    private int totalpresencelateearlyonly=0;
    private int totalSemuanya=0;
    
    private int earlyHome = 0;//ini late 
    private float earlyHomeTime = 0;
    private int lateEarly = 0;//ini late 
    private float lateEarlyTime = 0;
    private Hashtable reason = new Hashtable();//ini late 
    private Hashtable reasonTime = new Hashtable();//ini late 
    
    private int totalReason=0;
    private int absence = 0;
    private float absenceTime = 0;
    private int totalWorkingDays = 0;
    private long timeWorkHour = 0;
    private int totalOnlyIn = 0;
    private float timeOnlyIn = 0;
    private int totalOnlyOut = 0;
    private float timeOnlyOut = 0;
    //update by priska 23-12-2014
    private int annualLeave = 0;
    private int dPayment = 0;
    private int unpaidLeave = 0;
    private int specialLeave = 0;
    private int realTotalReason = 0;
    private int totalScheduleOpname4jam = 0;
    private int totalScheduleOpname8jam = 0;
    private int totalS = 0;
    private int nightShift = 0;
    private int splitShift = 0;
    private Hashtable hashDataSchedule=new Hashtable();
    private Hashtable hashDataReason=new Hashtable();
    private Hashtable hashDataStatus=new Hashtable();
      
    
    private int dayPH = 0;
    //private Hashtable dtIDate = new Vector(1,1);//mengetahui dia ada d tanggal berapa tpi dlm int
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
     * @return the presenceOnTime
     */
    public int getPresenceOnTime() {
        return presenceOnTime;
    }

    /**
     * @param presenceOnTime the presenceOnTime to set
     */
    public void setPresenceOnTime(int presenceOnTime) {
        this.presenceOnTime = presenceOnTime;
    }

    /**
     * @return the dayOffSchedule
     */
    public int getDayOffSchedule() {
        return dayOffSchedule;
    }

    /**
     * @param dayOffSchedule the dayOffSchedule to set
     */
    public void setDayOffSchedule(int dayOffSchedule) {
        this.dayOffSchedule = dayOffSchedule;
    }

    /**
     * @return the insentif
     */
    public int getInsentif() {
        return insentif;
    }

    /**
     * @param insentif the insentif to set
     */
    public void setInsentif(int insentif) {
        this.insentif = insentif;
    }

    /**
     * @return the absence
     */
    public int getAbsence() {
        return absence;
    }

    /**
     * @param absence the absence to set
     */
    public void setAbsence(int absence) {
        this.absence = absence;
    }

    /**
     * @return the late
     */
    public int getLate() {
        return late;
    }

    /**
     * @param late the late to set
     */
    public void setLate(int late) {
        this.late = late;
    }

    /**
     * @return the presenceOnTimeTime
     */
    public float getPresenceOnTimeTime() {
        return presenceOnTimeTime;
    }

    /**
     * @param presenceOnTimeTime the presenceOnTimeTime to set
     */
    public void setPresenceOnTimeTime(float presenceOnTimeTime) {
        this.presenceOnTimeTime = presenceOnTimeTime;
    }

    /**
     * @return the lateTime
     */
    public float getLateTime() {
        return lateTime;
    }

    /**
     * @param lateTime the lateTime to set
     */
    public void setLateTime(float lateTime) {
        this.lateTime = lateTime;
    }

    /**
     * @return the earlyHome
     */
    public int getEarlyHome() {
        return earlyHome;
    }

    /**
     * @param earlyHome the earlyHome to set
     */
    public void setEarlyHome(int earlyHome) {
        this.earlyHome = earlyHome;
    }

    /**
     * @return the earlyHomeTime
     */
    public float getEarlyHomeTime() {
        return earlyHomeTime;
    }

    /**
     * @param earlyHomeTime the earlyHomeTime to set
     */
    public void setEarlyHomeTime(float earlyHomeTime) {
        this.earlyHomeTime = earlyHomeTime;
    }

    /**
     * @return the lateEarly
     */
    public int getLateEarly() {
        return lateEarly;
    }

    /**
     * @param lateEarly the lateEarly to set
     */
    public void setLateEarly(int lateEarly) {
        this.lateEarly = lateEarly;
    }

    /**
     * @return the lateEarlyTime
     */
    public float getLateEarlyTime() {
        return lateEarlyTime;
    }

    /**
     * @param lateEarlyTime the lateEarlyTime to set
     */
    public void setLateEarlyTime(float lateEarlyTime) {
        this.lateEarlyTime = lateEarlyTime;
    }

    /**
     * @return the absenceTime
     */
    public float getAbsenceTime() {
        return absenceTime;
    }

    /**
     * @param absenceTime the absenceTime to set
     */
    public void setAbsenceTime(float absenceTime) {
        this.absenceTime = absenceTime;
    }

    public Hashtable getReason() {
        return reason;
    }
    /**
     * @return the reason
     */
       public int getTotalReasonWithoutInc(int reasonNo) {
        int totReason=0;
//        if(reason!=null && reason.size()>0 && reason.containsKey(""+reasonNo)){
//            totReason = (Integer)reason.get(""+reasonNo);
//            totalReason = totalReason + totReason;
//            reason.remove(""+reasonNo);
//        }
         if(reason!=null && reason.size()>0 && reason.containsKey(""+reasonNo)){
            totReason = (Integer)reason.get(""+reasonNo);
            //totalReason = totalReason + totReason;
            //reason.remove(""+reasonNo);
        }
        return totReason;
    }
    public int getTotalReason(int reasonNo) {
        int totReason=0;
//        if(reason!=null && reason.size()>0 && reason.containsKey(""+reasonNo)){
//            totReason = (Integer)reason.get(""+reasonNo);
//            totalReason = totalReason + totReason;
//            reason.remove(""+reasonNo);
//        }
         if(reason!=null && reason.size()>0 && reason.containsKey(""+reasonNo)){
            totReason = (Integer)reason.get(""+reasonNo);
            totalReason = totalReason + totReason;
            reason.remove(""+reasonNo);
        }
        return totReason;
    }

    /**
     * @param reason the reason to set
     */
    public void addReason(int reasonNo,int nilai) {
        if(reason==null){
            reason = new Hashtable();
        }
        this.reason.put(""+reasonNo, nilai); 
    }

    /**
     * @return the reasonTime
     */
    public Hashtable getReasonTime() {
        return reasonTime;
    }

    /**
     * @param reasonTime the reasonTime to set
     */
    public void setReasonTime(Hashtable reasonTime) {
        this.reasonTime = reasonTime;
    }

    /**
     * @return the totalWorkingDays
     */
    public int getTotalWorkingDays() {
        return totalWorkingDays;
    }

    /**
     * @param totalWorkingDays the totalWorkingDays to set
     */
    public void setTotalWorkingDays(int totalWorkingDays) {
        this.totalWorkingDays = totalWorkingDays;
    }

   

    /**
     * @return the totalOnlyIn
     */
    public int getTotalOnlyIn() {
        return totalOnlyIn;
    }

    /**
     * @param totalOnlyIn the totalOnlyIn to set
     */
    public void setTotalOnlyIn(int totalOnlyIn) {
        this.totalOnlyIn = totalOnlyIn;
    }

    /**
     * @return the timeOnlyIn
     */
    public float getTimeOnlyIn() {
        return timeOnlyIn;
    }

    /**
     * @param timeOnlyIn the timeOnlyIn to set
     */
    public void setTimeOnlyIn(float timeOnlyIn) {
        this.timeOnlyIn = timeOnlyIn;
    }

    /**
     * @return the totalOnlyOut
     */
    public int getTotalOnlyOut() {
        return totalOnlyOut;
    }

    /**
     * @param totalOnlyOut the totalOnlyOut to set
     */
    public void setTotalOnlyOut(int totalOnlyOut) {
        this.totalOnlyOut = totalOnlyOut;
    }

    /**
     * @return the timeOnlyOut
     */
    public float getTimeOnlyOut() {
        return timeOnlyOut;
    }

    /**
     * @param timeOnlyOut the timeOnlyOut to set
     */
    public void setTimeOnlyOut(float timeOnlyOut) {
        this.timeOnlyOut = timeOnlyOut;
    }

    /**
     * @return the timeWorkHour
     */
    public long getTimeWorkHour() {
        return timeWorkHour;
    }

    /**
     * @param timeWorkHour the timeWorkHour to set
     */
    public void setTimeWorkHour(long timeWorkHour) {
        this.timeWorkHour = timeWorkHour;
    }

    /**
     * @return the hashDataSchedule
     */
    public String getScheduleSymbol(Date tgl) {
        //return hashDataSchedule;
        String symbol="-";
        if(tgl!=null && hashDataSchedule!=null && hashDataSchedule.containsKey(Formater.formatDate(tgl, "yyyy-MM-dd"))){
                symbol =(String) hashDataSchedule.get(Formater.formatDate(tgl, "yyyy-MM-dd"));
        }
        return symbol;
    }

    /**
     * @param hashDataSchedule the hashDataSchedule to set
     */
    public void addHashDataSchedule(Date tgl,String symbol) {
       if(tgl!=null){
            this.hashDataSchedule.put(Formater.formatDate(tgl, "yyyy-MM-dd"), symbol);
       }
    }
    
    public void addHashDataReason(Date tgl,String reason) {
       if(tgl!=null){
            this.hashDataReason.put(Formater.formatDate(tgl, "yyyy-MM-dd"), reason);
       }
    }
    
    public String getDataReason(Date tgl) {
        //return hashDataSchedule;
        if(tgl.getTime()==1414771200000L){
            boolean dc=true;
        }
        String symbol="";
        if(tgl!=null && hashDataReason!=null && hashDataReason.containsKey(Formater.formatDate(tgl, "yyyy-MM-dd"))){
                symbol =((String) hashDataReason.get(Formater.formatDate(tgl, "yyyy-MM-dd")));
                if(symbol!=null && symbol.length()>0){
                    symbol = "/"+symbol;
                }
        }
        return symbol;
    }

    public String getDataReasonOrScheduleStatusForMinimart(Date tgl, long oidDiv) {
        //return hashDataSchedule Reason;
 
         long oidDivOperation =0;
                try{
                    oidDivOperation = Long.parseLong(com.dimata.system.entity.system.PstSystemProperty.getValueByName("OPERATION_DIV_ID")); 
                }catch(Exception ex){
                     System.out.println("oidDivOperation NOT Be SET"+ex);
                }   
                
        if(tgl.getTime()==1414771200000L){
            boolean dc=true;
        }
        String symbol="";
        if(tgl!=null && hashDataReason!=null && hashDataReason.containsKey(Formater.formatDate(tgl, "yyyy-MM-dd"))){
                symbol =((String) hashDataReason.get(Formater.formatDate(tgl, "yyyy-MM-dd")));
                if(symbol!=null && symbol.length()>0){
                    symbol = ""+symbol;
                }
        }
        
        
        //return hashDataSchedule;
        String symbolSchedule="-";
        if(tgl!=null && hashDataSchedule!=null && hashDataSchedule.containsKey(Formater.formatDate(tgl, "yyyy-MM-dd"))){
                symbolSchedule =(String) hashDataSchedule.get(Formater.formatDate(tgl, "yyyy-MM-dd"));
        }
        
        //return hashDataSchedule;
        if(tgl.getTime()==1414771200000L){
            boolean dc=true;
        }
        String status="";
        if(tgl!=null && hashDataStatus!=null && hashDataStatus.containsKey(Formater.formatDate(tgl, "yyyy-MM-dd"))){
                status =((String) hashDataStatus.get(Formater.formatDate(tgl, "yyyy-MM-dd")));
                if(status!=null && status.length()>0){
                    status = ""+status;
                }
        }
        
        if (status.equals("Absence") && (!symbolSchedule.equals("H"))){
            return "B";
        } else if ((status.equals("Late")) || (status.equals("Early Out/Home")) || (symbolSchedule.equals("IMT")) || ((symbolSchedule.equals("IPC") && (symbol.equals("IPC")) && (status.equals("Late Early"))) ) ){
            if (oidDiv == 2006l ){
                return symbolSchedule;
            } else {
                if (!symbolSchedule.equals("H-4")){
                    return "R";
                } else {
                    return symbolSchedule;
                }
                
            }
        }else{
            return symbolSchedule;
        }
        
    }
    
    
     public void addHashDataStatus(Date tgl,String status) {
       if(tgl!=null){
            this.hashDataStatus.put(Formater.formatDate(tgl, "yyyy-MM-dd"), status);
       }
    }
    
    public String getDataStatus(Date tgl) {
        //return hashDataSchedule;
        if(tgl.getTime()==1414771200000L){
            boolean dc=true;
        }
        String status="";
        if(tgl!=null && hashDataStatus!=null && hashDataStatus.containsKey(Formater.formatDate(tgl, "yyyy-MM-dd"))){
                status =((String) hashDataStatus.get(Formater.formatDate(tgl, "yyyy-MM-dd")));
                if(status!=null && status.length()>0){
                    status = "/"+status;
                }
        }
        return status;
    }
    
    
    /**
     * @return the totalLateLebihLimaMenit
     */
    public int getTotalLateLebihLimaMenit() {
        return totalLateLebihLimaMenit;
    }

    /**
     * @param totalLateLebihLimaMenit the totalLateLebihLimaMenit to set
     */
    public void setTotalLateLebihLimaMenit(int totalLateLebihLimaMenit) {
        this.totalLateLebihLimaMenit = totalLateLebihLimaMenit;
    }
    
    public boolean telatlebihLimaMenit(Date dtSelect,ScheduleSymbol scheduleSymbol,Date ActualIn){
        boolean telat=false;
        
        if(dtSelect==null || ActualIn==null || scheduleSymbol==null){
            return telat;
        }
        int minTelat= 5;
        Date dtSchedule = new Date(dtSelect.getYear(), dtSelect.getMonth(), dtSelect.getDate(), scheduleSymbol.getTimeIn().getHours(), scheduleSymbol.getTimeIn().getMinutes());
        if (dtSchedule.getHours() == 0 && dtSchedule.getMinutes() == 0) {
            dtSchedule = new Date(dtSelect.getYear(), dtSelect.getMonth(), dtSelect.getDate() + 1, 0, 0);
        }

        if (dtSchedule != null && ActualIn != null) {
            Date cloneFromdtschedule = (Date) dtSchedule.clone();
            cloneFromdtschedule.setSeconds(0);
            cloneFromdtschedule.setMinutes(cloneFromdtschedule.getMinutes()+minTelat);
           if (ActualIn.getTime() > (cloneFromdtschedule.getTime()) ){
               telat=true;
           }
//            dtSchedule.setSeconds(0);
//            ActualIn.setSeconds(0); 
//            long iDuration = dtSchedule.getTime() / 60000 - ActualIn.getTime() / 60000;
//           // long iDurationHour = (iDuration - (iDuration % 60)) / 60;
//            long iDurationMin = iDuration % 60;
//            if( (iDurationMin)<minTelat){
//                telat=true;
//            }
        }
        return telat;
        
    }

    /**
     * @return the totalSemuanya
     */
    public int getTotalSemuanya() {
        // totalSemuanya di-update by McHen
        if (totalReason == 0){
            totalSemuanya = totalpresencelateearlyonly+dayOffSchedule+0;
            
        } else {
            totalSemuanya = totalpresencelateearlyonly+dayOffSchedule+totalReason;
        
        }
        return totalSemuanya;
    }
     public int getTotalSemuanyaByPepito() {
        // totalSemuanya di-update by McHen
        if (totalReason == 0){
            totalSemuanya = totalpresencelateearlyonly+dayOffScheduleH+dayOffScheduleEO+annualLeave+dPayment+specialLeave+unpaidLeave+ totalReason +0+totalS ;
            //totalSemuanya = totalpresencelateearlyonly+dayOffScheduleH+dayOffScheduleEO+annualLeave+dPayment+specialLeave+unpaidLeave+ totalReason +0+totalS + dayPH;
            
        } else {
            totalSemuanya = totalpresencelateearlyonly+dayOffScheduleH+dayOffScheduleEO+annualLeave+dPayment+specialLeave+unpaidLeave+totalReason+totalS;
        
        }
        return totalSemuanya;
    }
     
     public int getTotalSemuanyaByRamaResto() {
        // totalSemuanya di-update by McHen
        if (totalReason == 0){
            //totalSemuanya = totalpresencelateearlyonly+dayOffScheduleH+dayOffScheduleEO+annualLeave+dPayment+specialLeave+unpaidLeave+ totalReason +0+totalS ;
            totalSemuanya = totalpresencelateearlyonly+dayOffScheduleH+dayOffScheduleEO+annualLeave+dPayment+specialLeave+unpaidLeave+ totalReason +0+totalS + dayPH;
            
        } else {
            totalSemuanya = totalpresencelateearlyonly+dayOffScheduleH+dayOffScheduleEO+annualLeave+dPayment+specialLeave+unpaidLeave+totalReason+totalS;
        
        }
        return totalSemuanya;
    }

    /**
     * @return the annualLeave
     */
    public int getAnnualLeave() {
        return annualLeave;
    }

    /**
     * @param annualLeave the annualLeave to set
     */
    public void setAnnualLeave(int annualLeave) {
        this.annualLeave = annualLeave;
    }

    /**
     * @return the dPayment
     */
    public int getdPayment() {
        return dPayment;
    }

    /**
     * @param dPayment the dPayment to set
     */
    public void setdPayment(int dPayment) {
        this.dPayment = dPayment;
    }

    /**
     * @return the realTotalReason
     */
    public int getRealTotalReason() {
        return realTotalReason;
    }

    /**
     * @param realTotalReason the realTotalReason to set
     */
    public void setRealTotalReason(int realTotalReason) {
        this.realTotalReason = realTotalReason;
    }

    /**
     * @return the totalpresencelateearlyonly
     */
    public int getTotalpresencelateearlyonly() {
        return totalpresencelateearlyonly;
    }

    /**
     * @param totalpresencelateearlyonly the totalpresencelateearlyonly to set
     */
    public void setTotalpresencelateearlyonly(int totalpresencelateearlyonly) {
        this.totalpresencelateearlyonly = totalpresencelateearlyonly;
    }

    /**
     * @return the totalScheduleOpname4jam
     */
    public int getTotalScheduleOpname4jam() {
        return totalScheduleOpname4jam;
    }

    /**
     * @param totalScheduleOpname4jam the totalScheduleOpname4jam to set
     */
    public void setTotalScheduleOpname4jam(int totalScheduleOpname4jam) {
        this.totalScheduleOpname4jam = totalScheduleOpname4jam;
    }

    /**
     * @return the totalScheduleOpname8jam
     */
    public int getTotalScheduleOpname8jam() {
        return totalScheduleOpname8jam;
    }

    /**
     * @param totalScheduleOpname8jam the totalScheduleOpname8jam to set
     */
    public void setTotalScheduleOpname8jam(int totalScheduleOpname8jam) {
        this.totalScheduleOpname8jam = totalScheduleOpname8jam;
    }

    /**
     * @return the nightShift
     */
    public int getNightShift() {
        return nightShift;
    }

    /**
     * @param nightShift the nightShift to set
     */
    public void setNightShift(int nightShift) {
        this.nightShift = nightShift;
    }

    /**
     * @return the dayOffScheduleH
     */
    public int getDayOffScheduleH() {
        return dayOffScheduleH;
    }

    /**
     * @param dayOffScheduleH the dayOffScheduleH to set
     */
    public void setDayOffScheduleH(int dayOffScheduleH) {
        this.dayOffScheduleH = dayOffScheduleH;
    }

    /**
     * @return the dayOffScheduleEO
     */
    public int getDayOffScheduleEO() {
        return dayOffScheduleEO;
    }

    /**
     * @param dayOffScheduleEO the dayOffScheduleEO to set
     */
    public void setDayOffScheduleEO(int dayOffScheduleEO) {
        this.dayOffScheduleEO = dayOffScheduleEO;
    }

    /**
     * @return the unpaidLeave
     */
    public int getUnpaidLeave() {
        return unpaidLeave;
    }

    /**
     * @param unpaidLeave the unpaidLeave to set
     */
    public void setUnpaidLeave(int unpaidLeave) {
        this.unpaidLeave = unpaidLeave;
    }

    /**
     * @return the specialLeave
     */
    public int getSpecialLeave() {
        return specialLeave;
    }

    /**
     * @param specialLeave the specialLeave to set
     */
    public void setSpecialLeave(int specialLeave) {
        this.specialLeave = specialLeave;
    }

    /**
     * @return the totalS
     */
    public int getTotalS() {
        return totalS;
    }

    /**
     * @param totalS the totalS to set
     */
    public void setTotalS(int totalS) {
        this.totalS = totalS;
    }

    /**
     * @return the splitShift
     */
    public int getSplitShift() {
        return splitShift;
    }

    /**
     * @param splitShift the splitShift to set
     */
    public void setSplitShift(int splitShift) {
        this.splitShift = splitShift;
    }

    /**
     * @return the dayPH
     */
    public int getDayPH() {
        return dayPH;
    }

    /**
     * @param dayPH the dayPH to set
     */
    public void setDayPH(int dayPH) {
        this.dayPH = dayPH;
    }


    
}
