/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author GUSWIK
 */
public class EmpDocAction extends Entity{
    
    private String actionName = "";
    private String actionTitle = "";
    private long empDocId ;

    /**
     * @return the actionName
     */
    public String getActionName() {
          if ( actionName ==null ){
            actionName = "";
        }
        return actionName;
    }

    /**
     * @param actionName the actionName to set
     */
    public void setActionName(String actionName) {
      
        this.actionName = actionName;
    }

    /**
     * @return the actionTitle
     */
    public String getActionTitle() {
        if ( actionTitle ==null ){
            actionTitle = "";
        }
        return actionTitle;
    }

    /**
     * @param actionTitle the actionTitle to set
     */
    public void setActionTitle(String actionTitle) {
        this.actionTitle = actionTitle;
    }


    /**
     * @return the empDocId
     */
    public long getEmpDocId() {
        return empDocId;
    }

    /**
     * @param empDocId the empDocId to set
     */
    public void setEmpDocId(long empDocId) {
        this.empDocId = empDocId;
    }
}
