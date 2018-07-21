/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.utility.machine;

/**
 *
 * @author Satrya Ramayu
 */
public class AnalyseStatusDataPresence {
    private boolean success = false;
    private String presenceId;

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the presenceId
     */
    public String getPresenceId() {
        return presenceId;
    }

    /**
     * @param presenceId the presenceId to set
     */
    public void setPresenceId(String presenceId) {
        this.presenceId = presenceId;
    }
    
}
