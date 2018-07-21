/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import java.util.Date;
import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Acer
 */
public class SubSection extends Entity {

    private long sectionId = 0;
    private String subSection = "";
    private String description = "";
    private int validstatus = 0;
    private Date validstart = null;
    private Date validend = null;

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    public String getSubSection() {
        return subSection;
    }

    public void setSubSection(String subSection) {
        this.subSection = subSection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValidStatus() {
        return validstatus;
    }

    public void setValidStatus(int validstatus) {
        this.validstatus = validstatus;
    }

    public Date getValidStart() {
        return validstart;
    }

    public void setValidStart(Date validstart) {
        this.validstart = validstart;
    }

    public Date getValidEnd() {
        return validend;
    }

    public void setValidEnd(Date validend) {
        this.validend = validend;
    }

}
