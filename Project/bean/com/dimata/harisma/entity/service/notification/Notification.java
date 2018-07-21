/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.service.notification;

import com.dimata.qdep.entity.Entity;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author GUSWIK
 */
public class Notification extends Entity {
    
    private long notificationId = 0;
    private int modulName = 0;
    private String type = "";
    private String timeDistance = "";
    private String subject = "";
    private String text = "";
    private Date dateTime = new Date();
    private int status = 0;
    private int targetEmployee = 0;
    
    private Vector arrRecipient = new Vector();
    private Vector arrCC = new Vector();
    private Vector arrBCC = new Vector();
    private String recipientAdd = "";
    private String smsNum = "";
    private String ccAdd = "";
    private String bccAdd = "";
    private String time = "";
    

          /**
     * @return the arrDivision
     */
    public String[] getArrRecipient(int idx) {
         if(idx>=arrRecipient.size()){
            return null;
        }
        return (String[])arrRecipient.get(idx); 
    }

    /**
     * @param arrDivision the arrDivision to set
     */
    public void addArrRecipient(String[]  arrRecipient) {
        this.arrRecipient.add(arrRecipient);
    }
    
    
        /**
     * @return the arrDivision
     */
    public String[] getArrCC(int idx) {
         if(idx>=arrCC.size()){
            return null;
        }
        return (String[])arrCC.get(idx); 
    }

    /**
     * @param arrDivision the arrDivision to set
     */
    public void addArrCC(String[]  arrCC) {
        this.arrCC.add(arrCC);
    }
        /**
     * @return the arrDivision
     */
    public String[] getArrBCC(int idx) {
         if(idx>=arrBCC.size()){
            return null;
        }
        return (String[])arrBCC.get(idx); 
    }

    /**
     * @param arrDivision the arrDivision to set
     */
    public void addArrBCC(String[]  arrBCC) {
        this.arrCC.add(arrBCC);
    }
    /**
     * @return the notificationId
     */
    public long getNotificationId() {
        return notificationId;
    }

    /**
     * @param notificationId the notificationId to set
     */
    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * @return the modulName
     */
    public int getModulName() {
        return modulName;
    }

    /**
     * @param modulName the modulName to set
     */
    public void setModulName(int modulName) {
        this.modulName = modulName;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the timeDistance
     */
    public String getTimeDistance() {
        return timeDistance;
    }

    /**
     * @param timeDistance the timeDistance to set
     */
    public void setTimeDistance(String timeDistance) {
        this.timeDistance = timeDistance;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the dateTime
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * @param dateTime the dateTime to set
     */
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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
     * @return the targetEmployee
     */
    public int getTargetEmployee() {
        return targetEmployee;
    }

    /**
     * @param targetEmployee the targetEmployee to set
     */
    public void setTargetEmployee(int targetEmployee) {
        this.targetEmployee = targetEmployee;
    }

    /**
     * @return the recipientAdd
     */
    public String getRecipientAdd() {
        return recipientAdd;
    }

    /**
     * @param recipientAdd the recipientAdd to set
     */
    public void setRecipientAdd(String recipientAdd) {
        this.recipientAdd = recipientAdd;
    }

    /**
     * @return the ccAdd
     */
    public String getCcAdd() {
        return ccAdd;
    }

    /**
     * @param ccAdd the ccAdd to set
     */
    public void setCcAdd(String ccAdd) {
        this.ccAdd = ccAdd;
    }

    /**
     * @return the bccAdd
     */
    public String getBccAdd() {
        return bccAdd;
    }

    /**
     * @param bccAdd the bccAdd to set
     */
    public void setBccAdd(String bccAdd) {
        this.bccAdd = bccAdd;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }
    
    /**
     * @return the smsNum
     */
    public String getSmsNum() {
        return smsNum;
}

    /**
     * @param smsNum the smsNum to set
     */
    public void setSmsNum(String smsNum) {
        this.smsNum = smsNum;
    }
    
}
