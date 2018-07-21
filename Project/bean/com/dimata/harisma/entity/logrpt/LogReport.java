
/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.entity.logrpt; 
 
/* package java */ 
import java.util.Date;
import java.util.Vector;
/* package qdep */ 
import com.dimata.qdep.entity.*;

public class LogReport extends Entity { 
    
  public static final int RPT_STATUS_NEW =0;
  public static final int RPT_STATUS_FOLLOWED_UP =1;
  public static final int RPT_STATUS_FINISHED =2;
  public static final int RPT_STATUS_CANCELED =3;;
  
  public static final String rptStatus[] = {"New ", "Followed Up", "Finished", "Canceled" };

  private String logNumber="";
  private Date  recordDate = new Date();
  private long recordByUserId;
  private Date reportDate;
  private long reportByUserId;
  private long rptTypeId;  
  private long rptCategoryId;
  private String logDesc="";
  private long pasalUmumId;
  private long pasalKhususId;
  private long picUserId;
  private int status;
  private long logLocationId;
  private Date dueDateTime;
  private Date realFinishDateTime;
  private Vector reportPasal;
  private Date time;
  private long logCustomerId;
  private int priority;

   private Vector reportCc;
   private Vector reportNotification;
   
   //menentukan type report, jika report informasi maka status report logbook langsung finished
  private int reportInformation;
  
    public String getLogNumber() {
        return logNumber;
    }

    public void setLogNumber(String logNumber) {
        this.logNumber = logNumber;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public long getRecordByUserId() {
        return recordByUserId;
    }

    public void setRecordByUserId(long recordByUserId) {
        this.recordByUserId = recordByUserId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public long getReportByUserId() {
        return reportByUserId;
    }

    public void setReportByUserId(long reportByUserId) {
        this.reportByUserId = reportByUserId;
    }

    public long getRptTypeId() {
        return rptTypeId;
    }

    public void setRptTypeId(long rptTypeId) {
        this.rptTypeId = rptTypeId;
    }

    public long getRptCategoryId() {
        return rptCategoryId;
    }

    public void setRptCategoryId(long rptCategoryId) {
        this.rptCategoryId = rptCategoryId;
    }

    public String getLogDesc() {
        return logDesc;
    }

    public void setLogDesc(String logDesc) {
        this.logDesc = logDesc;
    }

    public long getPasalUmumId() {
        return pasalUmumId;
    }

    public void setPasalUmumId(long pasalUmumId) {
        this.pasalUmumId = pasalUmumId;
    }

    public long getPasalKhususId() {
        return pasalKhususId;
    }

    public void setPasalKhususId(long pasalKhususId) {
        this.pasalKhususId = pasalKhususId;
    }

    public long getPicUserId() {
        return picUserId;
    }

    public void setPicUserId(long picUserId) {
        this.picUserId = picUserId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getLogLocationId() {
        return logLocationId;
    }

    public void setLogLocationId(long logLocationId) {
        this.logLocationId = logLocationId;
    }

    public Date getDueDateTime() {
        return dueDateTime;
    }

    public void setDueDateTime(Date dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public Date getRealFinishDateTime() {
        return realFinishDateTime;
    }

    public void setRealFinishDateTime(Date realFinishDateTime) {
        this.realFinishDateTime = realFinishDateTime;
    }

    /**
     * @return the reportPasal
     */
    public Vector getReportPasal() {
        if(reportPasal==null) {
            reportPasal= new Vector();
        }
        return reportPasal;
    }

    /**
     * @param reportPasal the reportPasal to set
     */
    public void setReportPasal(Vector reportPasal) {
        this.reportPasal = reportPasal;
    }

    /**
     * @return the logCustomerId
     */
    public long getLogCustomerId() {
        return logCustomerId;
    }

    /**
     * @param logCustomerId the logCustomerId to set
     */
    public void setLogCustomerId(long logCustomerId) {
        this.logCustomerId = logCustomerId;
    }

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the reportCc
     */
    public Vector getReportCc() {
        if(reportCc==null) {
            reportCc= new Vector();
        }
        return reportCc;
    }

    /**
     * @param reportCc the reportCc to set
     */
    public void setReportCc(Vector reportCc) {
        this.reportCc = reportCc;
    }

    /**
     * @return the reportNotification
     */
    public Vector getReportNotification() {
        if(reportNotification==null) {
            reportNotification= new Vector();
        }
        return reportNotification;
    }

    /**
     * @param reportNotification the reportNotification to set
     */
    public void setReportNotification(Vector reportNotification) {
        this.reportNotification = reportNotification;
    }

    /**
     * @return the reportInformation
     */
    public int getReportInformation() {
        return reportInformation;
    }

    /**
     * @param reportInformation the reportInformation to set
     */
    public void setReportInformation(int reportInformation) {
        this.reportInformation = reportInformation;
    }
}
