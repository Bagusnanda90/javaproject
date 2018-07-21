/*
 * PresenceReportDaily.java
 *
 * Created on July 13, 2004, 3:06 PM
 */

package com.dimata.harisma.session.attendance;

import java.util.Date;
import com.dimata.harisma.entity.attendance.*;
/**
 *
 * @author  gedhy
 */
public class PresenceReportDaily {
    
    
    private int schldCategory1st;    
    private Date selectedDate;
    private String empNum;    
    private String empFullName;
    private Date scheduleIn1st;
    private Date scheduleIn2nd;
    private Date scheduleOut1st;
    private Date scheduleOut2nd;
    private String scheduleSymbol1;
    private String scheduleSymbol2;
    private Date actualIn1st;
    private Date actualIn2nd;
    private Date actualOut1st;
    private Date actualOut2nd;  
    private long scheduleId1;
    private long scheduleId2;
    private int status1;
    private int status2;
    private long empScheduleId =0;
    private String note1nd;
   private String note2nd;
   private int reasonNo1nd;
   private int reasonNo2nd;
   private long empId = 0;
   //update by satrya 2012-08-22
   private int presenceStatus;
  private Date presenceDateTime;
  private Date presenceScheduleTime;
  //update by satrya 2012-09-26
  private Date scheduleBO1st;
  private Date scheduleBO2nd;
  private Date scheduleBI1st;
  private Date scheduleBI2nd;
  private String scheduleDesc1st;
    private String scheduleDesc2nd;
   //update  by satya 2012-10-09
    private long religion_id;
    //update by satrya 2012-10-31
    private long departement_id;
   //update by satrya 2013-05-28
    private Date schTimeIn;
    private Date schTimeOut;
    private Date schBreakIn;
    private Date schBreakOut;
    /** Holds value of property schldCategory2nd. */
    private int schldCategory2nd;
   
    //update by satrya 2014-02-10
    private long empCategoryId;
    //update by satrya 2012-12-23
//    private String departmentName;
//    private String divisionName;
//    private String sectionName;
    /** Getter for property schldCategory.
     * @return Value of property schldCategory.
     *
     */
    public int getSchldCategory1st() {
        return this.schldCategory1st;
    }
    
    /** Setter for property schldCategory.
     * @param schldCategory New value of property schldCategory.
     *
     */
    public void setSchldCategory1st(int schldCategory1st) {
        this.schldCategory1st = schldCategory1st;
    }
    
    /** Getter for property empNum.
     * @return Value of property empNum.
     *
     */
    public String getEmpNum() {
        return this.empNum;
    }
    
    /** Setter for property empNum.
     * @param empNum New value of property empNum.
     *
     */
    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }
    
    /** Getter for property empFullName.
     * @return Value of property empFullName.
     *
     */
    public String getEmpFullName() {
        if(empFullName == null || empFullName.length()<1){
            empFullName = "";
        }
        return this.empFullName;
    }
    
    /** Setter for property empFullName.
     * @param empFullName New value of property empFullName.
     *
     */
    public void setEmpFullName(String empFullName) {
        this.empFullName = empFullName;
    }
    
    /** Getter for property in1st.
     * @return Value of property in1st.
     *
     */
    public Date getScheduleIn1st() {
        return this.scheduleIn1st;
    }
    
    /** Setter for property in1st.
     * @param in1st New value of property in1st.
     *
     */
    public void setScheduleIn1st(Date scheduleIn1st) {
        this.scheduleIn1st = scheduleIn1st;
    }
    
    /** Getter for property in2nd.
     * @return Value of property in2nd.
     *
     */
    public Date getScheduleIn2nd() {
        return this.scheduleIn2nd;
    }
    
    /** Setter for property in2nd.
     * @param in2nd New value of property in2nd.
     *
     */
    public void setScheduleIn2nd(Date scheduleIn2nd) {
        this.scheduleIn2nd = scheduleIn2nd;
    }
    
    /** Getter for property out1st.
     * @return Value of property out1st.
     *
     */
    public Date getScheduleOut1st() {
        return this.scheduleOut1st;
    }
    
    /** Setter for property out1st.
     * @param out1st New value of property out1st.
     *
     */
    public void setScheduleOut1st(Date scheduleOut1st) {
        this.scheduleOut1st = scheduleOut1st;
    }
    
    /** Getter for property out2nd.
     * @return Value of property out2nd.
     *
     */
    public Date getScheduleOut2nd() {
        return this.scheduleOut2nd;
    }
    
    /** Setter for property out2nd.
     * @param out2nd New value of property out2nd.
     *
     */
    public void setScheduleOut2nd(Date scheduleOut2nd) {
        this.scheduleOut2nd = scheduleOut2nd;
    }
    
    /** Getter for property symbol1.
     * @return Value of property symbol1.
     *
     */
    public String getScheduleSymbol1() {
        return this.scheduleSymbol1;
    }
    
    /** Setter for property symbol1.
     * @param symbol1 New value of property symbol1.
     *
     */
    public void setScheduleSymbol1(String scheduleSymbol1) {
        this.scheduleSymbol1 = scheduleSymbol1;
    }
    
    /** Getter for property symbol2.
     * @return Value of property symbol2.
     *
     */
    public String getScheduleSymbol2() {
        return this.scheduleSymbol2;
    }
    
    /** Setter for property symbol2.
     * @param symbol2 New value of property symbol2.
     *
     */
    public void setScheduleSymbol2(String scheduleSymbol2) {
        this.scheduleSymbol2 = scheduleSymbol2;
    }
    
    /** Getter for property actualIn1st.
     * @return Value of property actualIn1st.
     *
     */
    public Date getActualIn1st() {
        return this.actualIn1st;
    }
    
    /** Setter for property actualIn1st.
     * @param actualIn1st New value of property actualIn1st.
     *
     */
    public void setActualIn1st(Date actualIn1st) {
        this.actualIn1st = actualIn1st;
    }
    
    /** Getter for property actualIn2nd.
     * @return Value of property actualIn2nd.
     *
     */
    public Date getActualIn2nd() {
        return this.actualIn2nd;
    }
    
    /** Setter for property actualIn2nd.
     * @param actualIn2nd New value of property actualIn2nd.
     *
     */
    public void setActualIn2nd(Date actualIn2nd) {
        this.actualIn2nd = actualIn2nd;
    }
    
    /** Getter for property actualOut1st.
     * @return Value of property actualOut1st.
     *
     */
    public Date getActualOut1st() {
        return this.actualOut1st;
    }
    
    /** Setter for property actualOut1st.
     * @param actualOut1st New value of property actualOut1st.
     *
     */
    public void setActualOut1st(Date actualOut1st) {
        this.actualOut1st = actualOut1st;
    }
    
    /** Getter for property actualOut2nd.
     * @return Value of property actualOut2nd.
     *
     */
    public Date getActualOut2nd() {
        return this.actualOut2nd;
    }
    
    /** Setter for property actualOut2nd.
     * @param actualOut2nd New value of property actualOut2nd.
     *
     */
    public void setActualOut2nd(Date actualOut2nd) {
        this.actualOut2nd = actualOut2nd;
    }
    
    /** Getter for property schldCategory2nd.
     * @return Value of property schldCategory2nd.
     *
     */
    public int getSchldCategory2nd() {
        return this.schldCategory2nd;
    }
    
    /** Setter for property schldCategory2nd.
     * @param schldCategory2nd New value of property schldCategory2nd.
     *
     */
    public void setSchldCategory2nd(int schldCategory2nd) {
        this.schldCategory2nd = schldCategory2nd;
    }

    /**
     * @return the scheduleId1
     */
    public long getScheduleId1() {
        return scheduleId1;
    }

    /**
     * @param scheduleId1 the scheduleId1 to set
     */
    public void setScheduleId1(long scheduleId1) {
        this.scheduleId1 = scheduleId1;
    }

    /**
     * @return the scheduleId2
     */
    public long getScheduleId2() {
        return scheduleId2;
    }

    /**
     * @param scheduleId2 the scheduleId2 to set
     */
    public void setScheduleId2(long scheduleId2) {
        this.scheduleId2 = scheduleId2;
    }

    /**
     * @return the status1
     */
    public int getStatus1() {
        return status1;
    }

    /**
     * @param status1 the status1 to set
     */
    public void setStatus1(int status1) {
        this.status1 = status1;
    }

    /**
     * @return the status2
     */
    public int getStatus2() {
        return status2;
    }

    /**
     * @param status2 the status2 to set
     */
    public void setStatus2(int status2) {
        this.status2 = status2;
    }

    /**
     * @return the empScheduleId
     */
    public long getEmpScheduleId() {
        return empScheduleId;
    }

    /**
     * @param empScheduleId the empScheduleId to set
     */
    public void setEmpScheduleId(long empScheduleId) {
        this.empScheduleId = empScheduleId;
    }
    

    
    /**
     * @return the selectedDate
     */
    public Date getSelectedDate() {
        return selectedDate;
}

    /**
     * @param selectedDate the selectedDate to set
     */
    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    /**
     * @return the note1nd
     */
    public String getNote1nd() {
        return note1nd;
    }

    /**
     * @param note1nd the note1nd to set
     */
    public void setNote1nd(String note1nd) {
        this.note1nd = note1nd;
    }

    /**
     * @return the note2nd
     */
    public String getNote2nd() {
        return note2nd;
    }

    /**
     * @param note2nd the note2nd to set
     */
    public void setNote2nd(String note2nd) {
        this.note2nd = note2nd;
    }

    /**
     * @return the reasonNo1nd
     */
    public int getReasonNo1nd() {
        return reasonNo1nd;
    }

    /**
     * @param reasonNo1nd the reasonNo1nd to set
     */
    public void setReasonNo1nd(int reasonNo1nd) {
        this.reasonNo1nd = reasonNo1nd;
    }

    /**
     * @return the reasonNo2nd
     */
    public int getReasonNo2nd() {
        return reasonNo2nd;
    }

    /**
     * @param reasonNo2nd the reasonNo2nd to set
     */
    public void setReasonNo2nd(int reasonNo2nd) {
        this.reasonNo2nd = reasonNo2nd;
    }

    /**
     * @return the empId
     */
    public long getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(long empId) {
        this.empId = empId;
    }

    /**
     * @return the actualBreakOut
     */
    public int getPresenceStatus() {
        return presenceStatus;
    }
   
    /**
     * @param presenceStatus the presenceStatus to set
     */
    public void setPresenceStatus(int presenceStatus) {
        this.presenceStatus = presenceStatus;
    }
    
    /**
     * @return the presenceDateTime
     */
    public Date getPresenceDateTime() {
        return presenceDateTime;
    }
    
    /**
     * @param presenceDateTime the presenceDateTime to set
     */
    public void setPresenceDateTime(Date presenceDateTime) {
        this.presenceDateTime = presenceDateTime;
}

    /**
     * @return the presenceScheduleTime
     */
    public Date getPresenceScheduleTime() {
        return presenceScheduleTime;
    }

    /**
     * @param presenceScheduleTime the presenceScheduleTime to set
     */
    public void setPresenceScheduleTime(Date presenceScheduleTime) {
        this.presenceScheduleTime = presenceScheduleTime;
    }

    /**
     * @return the scheduleBO1st
     */
    public Date getScheduleBO1st() {
        return scheduleBO1st;
    }

    /**
     * @param scheduleBO1st the scheduleBO1st to set
     */
    public void setScheduleBO1st(Date scheduleBO1st) {
        this.scheduleBO1st = scheduleBO1st;
    }

    /**
     * @return the scheduleBO2nd
     */
    public Date getScheduleBO2nd() {
        return scheduleBO2nd;
    }

    /**
     * @param scheduleBO2nd the scheduleBO2nd to set
     */
    public void setScheduleBO2nd(Date scheduleBO2nd) {
        this.scheduleBO2nd = scheduleBO2nd;
    }

    /**
     * @return the scheduleBI1st
     */
    public Date getScheduleBI1st() {
        return scheduleBI1st;
    }

    /**
     * @param scheduleBI1st the scheduleBI1st to set
     */
    public void setScheduleBI1st(Date scheduleBI1st) {
        this.scheduleBI1st = scheduleBI1st;
    }

    /**
     * @return the scheduleBI2nd
     */
    public Date getScheduleBI2nd() {
        return scheduleBI2nd;
    }

    /**
     * @param scheduleBI2nd the scheduleBI2nd to set
     */
    public void setScheduleBI2nd(Date scheduleBI2nd) {
        this.scheduleBI2nd = scheduleBI2nd;
    }

    /**
     * @return the scheduleDesc1st
     */
    public String getScheduleDesc1st() {
        return scheduleDesc1st;
    }

    /**
     * @param scheduleDesc1st the scheduleDesc1st to set
     */
    public void setScheduleDesc1st(String scheduleDesc1st) {
        this.scheduleDesc1st = scheduleDesc1st;
    }

    /**
     * @return the scheduleDesc2nd
     */
    public String getScheduleDesc2nd() {
        return scheduleDesc2nd;
    }

    /**
     * @param scheduleDesc2nd the scheduleDesc2nd to set
     */
    public void setScheduleDesc2nd(String scheduleDesc2nd) {
        this.scheduleDesc2nd = scheduleDesc2nd;
    }

    /**
     * @return the religion_id
     */
    public long getReligion_id() {
        return religion_id;
    }
    
    /**
     * @param religion_id the religion_id to set
     */
    public void setReligion_id(long religion_id) {
        this.religion_id = religion_id;
    }

    /**
     * @return the departement_id
     */
    public long getDepartement_id() {
        return departement_id;
    }

    /**
     * @param departement_id the departement_id to set
     */
    public void setDepartement_id(long departement_id) {
        this.departement_id = departement_id;
    }

    /**
     * @return the schTimeIn
     */
    public Date getSchTimeIn() {
        return schTimeIn;
    }

    /**
     * @param schTimeIn the schTimeIn to set
     */
    public void setSchTimeIn(Date schTimeIn) {
        this.schTimeIn = schTimeIn;
    }

    /**
     * @return the schTimeOut
     */
    public Date getSchTimeOut() {
        return schTimeOut;
    }

    /**
     * @param schTimeOut the schTimeOut to set
     */
    public void setSchTimeOut(Date schTimeOut) {
        this.schTimeOut = schTimeOut;
    }

    /**
     * @return the schBreakIn
     */
    public Date getSchBreakIn() {
        return schBreakIn;
    }

    /**
     * @param schBreakIn the schBreakIn to set
     */
    public void setSchBreakIn(Date schBreakIn) {
        this.schBreakIn = schBreakIn;
    }

    /**
     * @return the schBreakOut
     */
    public Date getSchBreakOut() {
        return schBreakOut;
    }

    /**
     * @param schBreakOut the schBreakOut to set
     */
    public void setSchBreakOut(Date schBreakOut) {
        this.schBreakOut = schBreakOut;
    }

    /**
     * @return the empCategoryId
     */
    public long getEmpCategoryId() {
        return empCategoryId;
    }

    /**
     * @param empCategoryId the empCategoryId to set
     */
    public void setEmpCategoryId(long empCategoryId) {
        this.empCategoryId = empCategoryId;
    }

  
}
