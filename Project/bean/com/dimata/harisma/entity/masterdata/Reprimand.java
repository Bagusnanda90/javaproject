/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.entity.masterdata;

import java.util.Date;
import com.dimata.qdep.entity.*;
/**
 *
 * @author Wiweka
 */
public class Reprimand extends Entity{
    private String reprimandDesc = "";
    private int reprimandPoint=0;

    
    /**
     * @return the reprimandPoint
     */
    public int getReprimandPoint() {
        return reprimandPoint;
    }

    /**
     * @param reprimandPoint the reprimandPoint to set
     */
    public void setReprimandPoint(int reprimandPoint) {
        this.reprimandPoint = reprimandPoint;
    }

    /**
     * @return the reprimandDesc
     */
    public String getReprimandDesc() {
        return reprimandDesc;
    }

    /**
     * @param reprimandDesc the reprimandDesc to set
     */
    public void setReprimandDesc(String reprimandDesc) {
        this.reprimandDesc = reprimandDesc;
    }

    
   

}
