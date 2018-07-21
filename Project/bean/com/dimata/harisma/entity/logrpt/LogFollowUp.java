
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
/* package qdep */
import com.dimata.qdep.entity.*;

public class LogFollowUp extends Entity { 
  public static final int RPT_STATUS_IN_PROGRESS =0;  
  public static final int RPT_STATUS_FINISHED =1;
  public static final int RPT_STATUS_CANCELED =2;;
  
  public static final String rptStatus[] = {"In Progress", "Finished", "Canceled"};     
  
  private Date  startDate = new Date();
  private String flwNote="";  
  private long logReportId;
  private long flwUpByUserId;
  private String flwUpByUser;
  private int flwUpStatus;
  private Date endDateTime;
  private long chkByUserId;
  private String  chkByUser;
  private long submitByUserId;
  private String submitByUser;
  private String emailFollowUp;
  private String loginIdFollowUp;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getFlwNote() {
        return flwNote;
    }

    public void setFlwNote(String flwNote) {
        this.flwNote = flwNote;
    }

    public long getLogReportId() {
        return logReportId;
    }

    public void setLogReportId(long logReportId) {
        this.logReportId = logReportId;
    }

    public long getFlwUpByUserId() {
        return flwUpByUserId;
    }

    public void setFlwUpByUserId(long flwUpByUserId) {
        this.flwUpByUserId = flwUpByUserId;
    }

    public int getFlwUpStatus() {
        return flwUpStatus;
    }

    public void setFlwUpStatus(int flwUpStatus) {
        this.flwUpStatus = flwUpStatus;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public long getChkByUserId() {
        return chkByUserId;
    }

    public void setChkByUserId(long chkByUserId) {
        this.chkByUserId = chkByUserId;
    }

    /**
     * @return the flwUpByUser
     */
    public String getFlwUpByUser() {
        return flwUpByUser;
    }

    /**
     * @param flwUpByUser the flwUpByUser to set
     */
    public void setFlwUpByUser(String flwUpByUser) {
        this.flwUpByUser = flwUpByUser;
    }

    /**
     * @return the chkByUser
     */
    public String getChkByUser() {
        return chkByUser;
    }

    /**
     * @param chkByUser the chkByUser to set
     */
    public void setChkByUser(String chkByUser) {
        this.chkByUser = chkByUser;
    }

    /**
     * @return the submitByUserId
     */
    public long getSubmitByUserId() {
        return submitByUserId;
    }

    /**
     * @param submitByUserId the submitByUserId to set
     */
    public void setSubmitByUserId(long submitByUserId) {
        this.submitByUserId = submitByUserId;
    }

    /**
     * @return the submitByUser
     */
    public String getSubmitByUser() {
        return submitByUser;
    }

    /**
     * @param submitByUser the submitByUser to set
     */
    public void setSubmitByUser(String submitByUser) {
        this.submitByUser = submitByUser;
    }

    /**
     * @return the emailFollowUp
     */
    public String getEmailFollowUp() {
        return emailFollowUp;
    }

    /**
     * @param emailFollowUp the emailFollowUp to set
     */
    public void setEmailFollowUp(String emailFollowUp) {
        this.emailFollowUp = emailFollowUp;
    }

    /**
     * @return the loginIdFollowUp
     */
    public String getLoginIdFollowUp() {
        return loginIdFollowUp;
    }

    /**
     * @param loginIdFollowUp the loginIdFollowUp to set
     */
    public void setLoginIdFollowUp(String loginIdFollowUp) {
        this.loginIdFollowUp = loginIdFollowUp;
    }
}
