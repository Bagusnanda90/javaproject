/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.masterdata.HolidaysTable;
import com.dimata.harisma.session.payroll.I_PayrollCalculator;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class ParamPayInput {
    private int noteCommand;
    private String noteToAll;
    private String allLeftSeparator;
    private String allLeftSeparatorNew;
    private int statusPayInput;
    private Date dtAdjusment;
    private String levelCode;
    private long companyId;
    private long oidDivision;
    private long departmentName;
    private long sectionName;
    private String searchNrFrom;
    private String searchNrTo;
    private String searchName;
    private String searchNr;
    private long periodId;
    private int dataStatus;             
    private int isChekedRadioButtonSearchNr;
    
    private Date dtStart;
    private Date dtEnd;
    
    private String sEmployeeId;
    
    private Hashtable vctSchIDOff=new Hashtable();
    private Hashtable hashSchOff = new Hashtable();
    private int iPropInsentifLevel;
    private HolidaysTable holidaysTable= new HolidaysTable();
    private Hashtable hashPositionLevel= new Hashtable();
    private I_PayrollCalculator payrollCalculatorConfig;
    private Hashtable hashPeriod = new Hashtable();
    private Hashtable hashTblOvertimeDetail = new Hashtable();
    
    private I_Leave leaveConfig = null;
    
    

    /**
     * @return the noteCommand
     */
    public int getNoteCommand() {
        return noteCommand;
    }

    /**
     * @param noteCommand the noteCommand to set
     */
    public void setNoteCommand(int noteCommand) {
        this.noteCommand = noteCommand;
    }

    /**
     * @return the noteToAll
     */
    public String getNoteToAll() {
        return noteToAll;
    }

    /**
     * @param noteToAll the noteToAll to set
     */
    public void setNoteToAll(String noteToAll) {
        this.noteToAll = noteToAll;
    }

    /**
     * @return the allLeftSeparator
     */
    public String getAllLeftSeparator() {
        return allLeftSeparator;
    }

    /**
     * @param allLeftSeparator the allLeftSeparator to set
     */
    public void setAllLeftSeparator(String allLeftSeparator) {
        this.allLeftSeparator = allLeftSeparator;
    }

    /**
     * @return the allLeftSeparatorNew
     */
    public String getAllLeftSeparatorNew() {
        return allLeftSeparatorNew;
    }

    /**
     * @param allLeftSeparatorNew the allLeftSeparatorNew to set
     */
    public void setAllLeftSeparatorNew(String allLeftSeparatorNew) {
        this.allLeftSeparatorNew = allLeftSeparatorNew;
    }

    /**
     * @return the statusPayInput
     */
    public int getStatusPayInput() {
        return statusPayInput;
    }

    /**
     * @param statusPayInput the statusPayInput to set
     */
    public void setStatusPayInput(int statusPayInput) {
        this.statusPayInput = statusPayInput;
    }

    /**
     * @return the dtAdjusment
     */
    public Date getDtAdjusment() {
        return dtAdjusment;
    }

    /**
     * @param dtAdjusment the dtAdjusment to set
     */
    public void setDtAdjusment(Date dtAdjusment) {
        this.dtAdjusment = dtAdjusment;
    }

    /**
     * @return the levelCode
     */
    public String getLevelCode() {
        return levelCode;
    }

    /**
     * @param levelCode the levelCode to set
     */
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    /**
     * @return the companyId
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the oidDivision
     */
    public long getOidDivision() {
        return oidDivision;
    }

    /**
     * @param oidDivision the oidDivision to set
     */
    public void setOidDivision(long oidDivision) {
        this.oidDivision = oidDivision;
    }

    /**
     * @return the departmentName
     */
    public long getDepartmentName() {
        return departmentName;
    }

    /**
     * @param departmentName the departmentName to set
     */
    public void setDepartmentName(long departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * @return the sectionName
     */
    public long getSectionName() {
        return sectionName;
    }

    /**
     * @param sectionName the sectionName to set
     */
    public void setSectionName(long sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * @return the searchNrFrom
     */
    public String getSearchNrFrom() {
        return searchNrFrom;
    }

    /**
     * @param searchNrFrom the searchNrFrom to set
     */
    public void setSearchNrFrom(String searchNrFrom) {
        this.searchNrFrom = searchNrFrom;
    }

    /**
     * @return the searchNrTo
     */
    public String getSearchNrTo() {
        return searchNrTo;
    }

    /**
     * @param searchNrTo the searchNrTo to set
     */
    public void setSearchNrTo(String searchNrTo) {
        this.searchNrTo = searchNrTo;
    }

    /**
     * @return the searchName
     */
    public String getSearchName() {
        return searchName;
    }

    /**
     * @param searchName the searchName to set
     */
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    /**
     * @return the searchNr
     */
    public String getSearchNr() {
        return searchNr;
    }

    /**
     * @param searchNr the searchNr to set
     */
    public void setSearchNr(String searchNr) {
        this.searchNr = searchNr;
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
     * @return the dataStatus
     */
    public int getDataStatus() {
        return dataStatus;
    }

    /**
     * @param dataStatus the dataStatus to set
     */
    public void setDataStatus(int dataStatus) {
        this.dataStatus = dataStatus;
    }

    /**
     * @return the isChekedRadioButtonSearchNr
     */
    public int getIsChekedRadioButtonSearchNr() {
        return isChekedRadioButtonSearchNr;
    }

    /**
     * @param isChekedRadioButtonSearchNr the isChekedRadioButtonSearchNr to set
     */
    public void setIsChekedRadioButtonSearchNr(int isChekedRadioButtonSearchNr) {
        this.isChekedRadioButtonSearchNr = isChekedRadioButtonSearchNr;
    }

    /**
     * @return the dtStart
     */
    public Date getDtStart() {
        return dtStart;
    }

    /**
     * @param dtStart the dtStart to set
     */
    public void setDtStart(Date dtStart) {
        this.dtStart = dtStart;
    }

    /**
     * @return the dtEnd
     */
    public Date getDtEnd() {
        return dtEnd;
    }

    /**
     * @param dtEnd the dtEnd to set
     */
    public void setDtEnd(Date dtEnd) {
        this.dtEnd = dtEnd;
    }

    /**
     * @return the sEmployeeId
     */
    public String getsEmployeeId() {
        return sEmployeeId;
    }

    /**
     * @param sEmployeeId the sEmployeeId to set
     */
    public void setsEmployeeId(String sEmployeeId) {
        this.sEmployeeId = sEmployeeId;
    }

   
    /**
     * @return the hashSchOff
     */
    public Hashtable getHashSchOff() {
        return hashSchOff;
    }

    /**
     * @param hashSchOff the hashSchOff to set
     */
    public void setHashSchOff(Hashtable hashSchOff) {
        this.hashSchOff = hashSchOff;
    }

    /**
     * @return the iPropInsentifLevel
     */
    public int getiPropInsentifLevel() {
        return iPropInsentifLevel;
    }

    /**
     * @param iPropInsentifLevel the iPropInsentifLevel to set
     */
    public void setiPropInsentifLevel(int iPropInsentifLevel) {
        this.iPropInsentifLevel = iPropInsentifLevel;
    }

    

    /**
     * @return the hashPositionLevel
     */
    public Hashtable getHashPositionLevel() {
        return hashPositionLevel;
    }

    /**
     * @param hashPositionLevel the hashPositionLevel to set
     */
    public void setHashPositionLevel(Hashtable hashPositionLevel) {
        this.hashPositionLevel = hashPositionLevel;
    }

    /**
     * @return the payrollCalculatorConfig
     */
    public I_PayrollCalculator getPayrollCalculatorConfig() {
        return payrollCalculatorConfig;
    }

    /**
     * @param payrollCalculatorConfig the payrollCalculatorConfig to set
     */
    public void setPayrollCalculatorConfig(I_PayrollCalculator payrollCalculatorConfig) {
        this.payrollCalculatorConfig = payrollCalculatorConfig;
    }

    /**
     * @return the hashPeriod
     */
    public Hashtable getHashPeriod() {
        return hashPeriod;
    }

    /**
     * @param hashPeriod the hashPeriod to set
     */
    public void setHashPeriod(Hashtable hashPeriod) {
        this.hashPeriod = hashPeriod;
    }

    /**
     * @return the hashTblOvertimeDetail
     */
    public Hashtable getHashTblOvertimeDetail() {
        return hashTblOvertimeDetail;
    }

    /**
     * @param hashTblOvertimeDetail the hashTblOvertimeDetail to set
     */
    public void setHashTblOvertimeDetail(Hashtable hashTblOvertimeDetail) {
        this.hashTblOvertimeDetail = hashTblOvertimeDetail;
    }

    /**
     * @return the holidaysTable
     */
    public HolidaysTable getHolidaysTable() {
        return holidaysTable;
    }

    /**
     * @param holidaysTable the holidaysTable to set
     */
    public void setHolidaysTable(HolidaysTable holidaysTable) {
        this.holidaysTable = holidaysTable;
    }

    /**
     * @return the leaveConfig
     */
    public I_Leave getLeaveConfig() {
        return leaveConfig;
    }

    /**
     * @param leaveConfig the leaveConfig to set
     */
    public void setLeaveConfig(I_Leave leaveConfig) {
        this.leaveConfig = leaveConfig;
    }

    /**
     * @return the vctSchIDOff
     */
    public Hashtable getVctSchIDOff() {
        return vctSchIDOff;
    }

    /**
     * @param vctSchIDOff the vctSchIDOff to set
     */
    public void setVctSchIDOff(Hashtable vctSchIDOff) {
        this.vctSchIDOff = vctSchIDOff;
    }
}
