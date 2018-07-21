/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Satrya Ramayu
 */
public class GradeLevel extends  Entity{
    private String codeLevel;

    /**
     * @return the codeLevel
     */
    public String getCodeLevel() {
        if(codeLevel==null){
            return "";
        }
        return codeLevel;
    }

    /**
     * @param codeLevel the codeLevel to set
     */
    public void setCodeLevel(String codeLevel) {
        this.codeLevel = codeLevel;
    }
}
