/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Hendra McHen
 */
public class WarningReprimandPasal extends Entity{

    private String pasalTitle = "";
    private long babId = 0;

    /**
     * @return the pasalTitle
     */
    public String getPasalTitle() {
        return pasalTitle;
    }

    /**
     * @param pasalTitle the pasalTitle to set
     */
    public void setPasalTitle(String pasalTitle) {
        this.pasalTitle = pasalTitle;
    }

    /**
     * @return the babId
     */
    public long getBabId() {
        return babId;
    }

    /**
     * @param babId the babId to set
     */
    public void setBabId(long babId) {
        this.babId = babId;
    }


}
