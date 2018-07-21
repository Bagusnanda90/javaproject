/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/* package java */
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

/**
 *
 * @author Wiweka
 */
public class Warning extends Entity {

    private String warnDesc = "";
    private int warnPoint = 0;
    //private String warnPoint= "";

   
    /**
     * @return the warnDesc
     */
    public String getWarnDesc() {
        return warnDesc;
    }

    /**
     * @param warnDesc the warnDesc to set
     */
    public void setWarnDesc(String warnDesc) {
        this.warnDesc = warnDesc;
    }

    /**
     * @return the warnPoint
     */
    public int getWarnPoint() {
        return warnPoint;
    }

    /**
     * @param warnPoint the warnPoint to set
     */
    public void setWarnPoint(int warnPoint) {
        this.warnPoint = warnPoint;
    }

    

}
