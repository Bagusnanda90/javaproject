/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.service.notification;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author GUSWIK
 */
public class NotificationEmailRecord extends Entity {
    
    private long notificationEmailId = 0;
    private String email = "";
    private Date dateSend = new Date();    
    private String subject = "";

    /**
     * @return the notificationEmailId
     */
    public long getNotificationEmailId() {
        return notificationEmailId;
    }

    /**
     * @param notificationEmailId the notificationEmailId to set
     */
    public void setNotificationEmailId(long notificationEmailId) {
        this.notificationEmailId = notificationEmailId;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the dateSend
     */
    public Date getDateSend() {
        return dateSend;
    }

    /**
     * @param dateSend the dateSend to set
     */
    public void setDateSend(Date dateSend) {
        this.dateSend = dateSend;
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

    
}
