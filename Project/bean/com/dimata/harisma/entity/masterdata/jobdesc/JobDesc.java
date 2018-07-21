/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata.jobdesc;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author khirayinnura
 */
public class JobDesc extends Entity {
    private long positionJobId = 0;
    private long positionId = 0;
    private String positionName = "";
    private String jobTitle = "";
    private long jobCategoryId = 0;
    private String jobCategoryName = "";
    private String jobDesc = "";
    private String jobInput = "";
    private String jobOutput = "";
    private int scheduleType = 0;
    private int repeatType = 0;
    private Date startDatetime = null;
    private Date endDatetime = null;
    private int repeatEvery = 0;
    private Date repeatUntilDate = null;
    private int repeatUntilQty = 0;
    private int repeatWeekDays = 0;
    private int repeatMonthDate = 0;
    private int repeatMonthDay = 0;
    private int reminderMinutesBefore = 0;
    private int notification = 0;
    private long checkerPositionId = 0;
    private String checkerPositionName = "";
    private long approverPositionId = 0;
    private String approverPositionName = "";

    /**
     * @return the positionJobId
     */
    public long getPositionJobId() {
        return positionJobId;
    }

    /**
     * @param positionJobId the positionJobId to set
     */
    public void setPositionJobId(long positionJobId) {
        this.positionJobId = positionJobId;
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
     * @return the jobTitle
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * @param jobTitle the jobTitle to set
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * @return the jobCategoryId
     */
    public long getJobCategoryId() {
        return jobCategoryId;
    }

    /**
     * @param jobCategoryId the jobCategoryId to set
     */
    public void setJobCategoryId(long jobCategoryId) {
        this.jobCategoryId = jobCategoryId;
    }

    /**
     * @return the jobDesc
     */
    public String getJobDesc() {
        return jobDesc;
    }

    /**
     * @param jobDesc the jobDesc to set
     */
    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    /**
     * @return the jobInput
     */
    public String getJobInput() {
        return jobInput;
    }

    /**
     * @param jobInput the jobInput to set
     */
    public void setJobInput(String jobInput) {
        this.jobInput = jobInput;
    }

    /**
     * @return the jobOutput
     */
    public String getJobOutput() {
        return jobOutput;
    }

    /**
     * @param jobOutput the jobOutput to set
     */
    public void setJobOutput(String jobOutput) {
        this.jobOutput = jobOutput;
    }

    /**
     * @return the scheduleType
     */
    public int getScheduleType() {
        return scheduleType;
    }

    /**
     * @param scheduleType the scheduleType to set
     */
    public void setScheduleType(int scheduleType) {
        this.scheduleType = scheduleType;
    }

    /**
     * @return the repeatType
     */
    public int getRepeatType() {
        return repeatType;
    }

    /**
     * @param repeatType the repeatType to set
     */
    public void setRepeatType(int repeatType) {
        this.repeatType = repeatType;
    }

    /**
     * @return the startDatetime
     */
    public Date getStartDatetime() {
        return startDatetime;
    }

    /**
     * @param startDatetime the startDatetime to set
     */
    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
    }

    /**
     * @return the endDatetime
     */
    public Date getEndDatetime() {
        return endDatetime;
    }

    /**
     * @param endDatetime the endDatetime to set
     */
    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }

    /**
     * @return the repeatEvery
     */
    public int getRepeatEvery() {
        return repeatEvery;
    }

    /**
     * @param repeatEvery the repeatEvery to set
     */
    public void setRepeatEvery(int repeatEvery) {
        this.repeatEvery = repeatEvery;
    }

    /**
     * @return the repeatUntilDate
     */
    public Date getRepeatUntilDate() {
        return repeatUntilDate;
    }

    /**
     * @param repeatUntilDate the repeatUntilDate to set
     */
    public void setRepeatUntilDate(Date repeatUntilDate) {
        this.repeatUntilDate = repeatUntilDate;
    }

    /**
     * @return the repeatUntilQty
     */
    public int getRepeatUntilQty() {
        return repeatUntilQty;
    }

    /**
     * @param repeatUntilQty the repeatUntilQty to set
     */
    public void setRepeatUntilQty(int repeatUntilQty) {
        this.repeatUntilQty = repeatUntilQty;
    }

    /**
     * @return the repeatWeekDays
     */
    public int getRepeatWeekDays() {
        return repeatWeekDays;
    }

    /**
     * @param repeatWeekDays the repeatWeekDays to set
     */
    public void setRepeatWeekDays(int repeatWeekDays) {
        this.repeatWeekDays = repeatWeekDays;
    }

    /**
     * @return the repeatMonthDate
     */
    public int getRepeatMonthDate() {
        return repeatMonthDate;
    }

    /**
     * @param repeatMonthDate the repeatMonthDate to set
     */
    public void setRepeatMonthDate(int repeatMonthDate) {
        this.repeatMonthDate = repeatMonthDate;
    }

    /**
     * @return the repeatMonthDay
     */
    public int getRepeatMonthDay() {
        return repeatMonthDay;
    }

    /**
     * @param repeatMonthDay the repeatMonthDay to set
     */
    public void setRepeatMonthDay(int repeatMonthDay) {
        this.repeatMonthDay = repeatMonthDay;
    }

    /**
     * @return the reminderMinutesBefore
     */
    public int getReminderMinutesBefore() {
        return reminderMinutesBefore;
    }

    /**
     * @param reminderMinutesBefore the reminderMinutesBefore to set
     */
    public void setReminderMinutesBefore(int reminderMinutesBefore) {
        this.reminderMinutesBefore = reminderMinutesBefore;
    }

    /**
     * @return the notification
     */
    public int getNotification() {
        return notification;
    }

    /**
     * @param notification the notification to set
     */
    public void setNotification(int notification) {
        this.notification = notification;
    }

    /**
     * @return the checkerPositionId
     */
    public long getCheckerPositionId() {
        return checkerPositionId;
    }

    /**
     * @param checkerPositionId the checkerPositionId to set
     */
    public void setCheckerPositionId(long checkerPositionId) {
        this.checkerPositionId = checkerPositionId;
    }

    /**
     * @return the approverPositionId
     */
    public long getApproverPositionId() {
        return approverPositionId;
    }

    /**
     * @param approverPositionId the approverPositionId to set
     */
    public void setApproverPositionId(long approverPositionId) {
        this.approverPositionId = approverPositionId;
    }

    /**
     * @return the positionName
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * @param positionName the positionName to set
     */
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /**
     * @return the jobCategoryName
     */
    public String getJobCategoryName() {
        return jobCategoryName;
    }

    /**
     * @param jobCategoryName the jobCategoryName to set
     */
    public void setJobCategoryName(String jobCategoryName) {
        this.jobCategoryName = jobCategoryName;
    }

    /**
     * @return the checkerPositionName
     */
    public String getCheckerPositionName() {
        return checkerPositionName;
    }

    /**
     * @param checkerPositionName the checkerPositionName to set
     */
    public void setCheckerPositionName(String checkerPositionName) {
        this.checkerPositionName = checkerPositionName;
    }

    /**
     * @return the approverPositionName
     */
    public String getApproverPositionName() {
        return approverPositionName;
    }

    /**
     * @param approverPositionName the approverPositionName to set
     */
    public void setApproverPositionName(String approverPositionName) {
        this.approverPositionName = approverPositionName;
    }
}
