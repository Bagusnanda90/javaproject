/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.logrpt;

import com.dimata.qdep.entity.Entity;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author user
 */
public class LogSrcReportList extends Entity {
    private String Number = "";
    private String Description = "";
    private Vector Type = new Vector();
    private Vector CategoryId = new Vector();
    private Vector Status = new Vector();
    private String PasalUmum = "";
    private String PasalKhusus = "";
    private Date RecordDateFrom;
    private Date RecordDateTo;
    private Date ReportDateFrom;
    private Date ReportDateTo;
    private Vector LocationId= new Vector();
    private String PIC;
    private String reportedBy;
    private String recordedBy;
    private String followUpBy;
    private Date DueDateFrom;
    private Date DueDateTo;
    private Date FinishDateFrom;
    private Date FinishDateTo;
    private int allRecordDate=0;
    private int allReportDate=0;
    private int allDueDate=0;
    private int allFinishDate=0;
    private String orderBy="";
    private int read =0;
    private int typeReport=0;
    private int source=0;
    private long costumerId;

    private int statusFollowUp=0;
    
    
    public void LogSrcReportList(){
        
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String Number) {
        this.Number = Number;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Vector getType() {
        return Type;
    }

    public void setType(Vector Type) {
        this.Type = Type;
    }

    public Vector getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Vector CategoryId) {
        this.CategoryId = CategoryId;
    }

    public Vector getStatus() {
        return Status;
    }

    public void setStatus(Vector Status) {
        this.Status = Status;
    }

    public String getPasalUmum() {
        return PasalUmum;
    }

    public void setPasalUmum(String PasalUmum) {
        this.PasalUmum = PasalUmum;
    }

    public String getPasalKhusus() {
        return PasalKhusus;
    }

    public void setPasalKhusus(String PasalKhusus) {
        this.PasalKhusus = PasalKhusus;
    }

    public Date getRecordDateFrom() {
        return RecordDateFrom;
    }

    public void setRecordDateFrom(Date RecordDateFrom) {
        this.RecordDateFrom = RecordDateFrom;
    }

    public Date getRecordDateTo() {
        return RecordDateTo;
    }

    public void setRecordDateTo(Date RecordDateTo) {
        this.RecordDateTo = RecordDateTo;
    }

    public Date getReportDateFrom() {
        return ReportDateFrom;
    }

    public void setReportDateFrom(Date ReportDateFrom) {
        this.ReportDateFrom = ReportDateFrom;
    }

    public Date getReportDateTo() {
        return ReportDateTo;
    }

    public void setReportDateTo(Date ReportDateTo) {
        this.ReportDateTo = ReportDateTo;
    }

    public Vector getLocationId() {
        return LocationId;
    }

    public void setLocationId(Vector LocationId) {
        this.LocationId = LocationId;
    }

    public String getPIC() {
        return PIC;
    }

    public void setPIC(String PIC) {
        this.PIC = PIC;
    }

    public Date getDueDateFrom() {
        return DueDateFrom;
    }

    public void setDueDateFrom(Date DueDateFrom) {
        this.DueDateFrom = DueDateFrom;
    }

    public Date getDueDateTo() {
        return DueDateTo;
    }

    public void setDueDateTo(Date DueDateTo) {
        this.DueDateTo = DueDateTo;
    }

    public Date getFinishDateFrom() {
        return FinishDateFrom;
    }

    public void setFinishDateFrom(Date FinishDateFrom) {
        this.FinishDateFrom = FinishDateFrom;
    }

    public Date getFinishDateTo() {
        return FinishDateTo;
    }

    public void setFinishDateTo(Date FinishDateTo) {
        this.FinishDateTo = FinishDateTo;
    }

    /**
     * @return the allRecordDate
     */
    public int getAllRecordDate() {
        return allRecordDate;
    }

    /**
     * @param allRecordDate the allRecordDate to set
     */
    public void setAllRecordDate(int allRecordDate) {
        this.allRecordDate = allRecordDate;
    }

    /**
     * @return the allReportDate
     */
    public int getAllReportDate() {
        return allReportDate;
    }

    /**
     * @param allReportDate the allReportDate to set
     */
    public void setAllReportDate(int allReportDate) {
        this.allReportDate = allReportDate;
    }

    /**
     * @return the allDueDate
     */
    public int getAllDueDate() {
        return allDueDate;
    }

    /**
     * @param allDueDate the allDueDate to set
     */
    public void setAllDueDate(int allDueDate) {
        this.allDueDate = allDueDate;
    }

    /**
     * @return the allFinishDate
     */
    public int getAllFinishDate() {
        return allFinishDate;
    }

    /**
     * @param allFinishDate the allFinishDate to set
     */
    public void setAllFinishDate(int allFinishDate) {
        this.allFinishDate = allFinishDate;
    }

    /**
     * @return the orderBy
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * @param orderBy the orderBy to set
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * @return the reportedBy
     */
    public String getReportedBy() {
        return reportedBy;
    }

    /**
     * @param reportedBy the reportedBy to set
     */
    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    /**
     * @return the recordedBy
     */
    public String getRecordedBy() {
        return recordedBy;
    }

    /**
     * @param recordedBy the recordedBy to set
     */
    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    /**
     * @return the followUpBy
     */
    public String getFollowUpBy() {
        return followUpBy;
    }

    /**
     * @param followUpBy the followUpBy to set
     */
    public void setFollowUpBy(String followUpBy) {
        this.followUpBy = followUpBy;
    }

    /**
     * @return the read
     */
    public int getRead() {
        return read;
    }

    /**
     * @param read the read to set
     */
    public void setRead(int read) {
        this.read = read;
    }

    /**
     * @return the typeReport
     */
    public int getTypeReport() {
        return typeReport;
    }

    /**
     * @param typeReport the typeReport to set
     */
    public void setTypeReport(int typeReport) {
        this.typeReport = typeReport;
    }

    /**
     * @return the source
     */
    public int getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(int source) {
        this.source = source;
    }

    /**
     * @return the costumerId
     */
    public long getCostumerId() {
        return costumerId;
    }

    /**
     * @param costumerId the costumerId to set
     */
    public void setCostumerId(long costumerId) {
        this.costumerId = costumerId;
    }

    /**
     * @return the statusFollowUp
     */
    public int getStatusFollowUp() {
        return statusFollowUp;
    }

    /**
     * @param statusFollowUp the statusFollowUp to set
     */
    public void setStatusFollowUp(int statusFollowUp) {
        this.statusFollowUp = statusFollowUp;
    }

    

}
