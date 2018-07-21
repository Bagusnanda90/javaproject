/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.service.notification;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author GUSWIK
 */
public class NotificationRecipient extends Entity {
    
    
    private long notificationRecipientId = 0;
    private long notificationId = 0;
    private String recipientsEmail = "";
    private int recipientAs = 0;
    private int fromAdd = 0;
    private String recipientsSms = "";

   
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
     * @return the notificationRecipientId
     */
    public long getNotificationRecipientId() {
        return notificationRecipientId;
    }

    /**
     * @param notificationRecipientId the notificationRecipientId to set
     */
    public void setNotificationRecipientId(long notificationRecipientId) {
        this.notificationRecipientId = notificationRecipientId;
    }

    /**
     * @return the recipientsEmail
     */
    public String getRecipientsEmail() {
        return recipientsEmail;
    }

    /**
     * @param recipientsEmail the recipientsEmail to set
     */
    public void setRecipientsEmail(String recipientsEmail) {
        this.recipientsEmail = recipientsEmail;
    }

    /**
     * @return the recipientAs
     */
    public int getRecipientAs() {
        return recipientAs;
    }

    /**
     * @param recipientAs the recipientAs to set
     */
    public void setRecipientAs(int recipientAs) {
        this.recipientAs = recipientAs;
    }

    /**
     * @return the fromAdd
     */
    public int getFromAdd() {
        return fromAdd;
    }

    /**
     * @param fromAdd the fromAdd to set
     */
    public void setFromAdd(int fromAdd) {
        this.fromAdd = fromAdd;
    }

    /**
     * @return the recipientsSms
     */
    public String getRecipientsSms() {
        return recipientsSms;
    }
   
    /**
     * @param recipientsSms the recipientsSms to set
     */
    public void setRecipientsSms(String recipientsSms) {
        this.recipientsSms = recipientsSms;
    }
    
   
    
}
