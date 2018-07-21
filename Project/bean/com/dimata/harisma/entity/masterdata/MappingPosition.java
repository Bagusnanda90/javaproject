/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

/**
 *
 * @author Dimata 007
 */
import com.dimata.qdep.entity.Entity;
import java.util.Date;

public class MappingPosition extends Entity {

    private long upPositionId = 0;
    private long downPositionId = 0;
    private Date startDate = null;
    private Date endDate = null;
    private int typeOfLink = 0;
    private long templateId = 0;

    public long getUpPositionId() {
        return upPositionId;
    }

    public void setUpPositionId(long upPositionId) {
        this.upPositionId = upPositionId;
    }

    public long getDownPositionId() {
        return downPositionId;
    }

    public void setDownPositionId(long downPositionId) {
        this.downPositionId = downPositionId;
    }

    public long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(long templateId) {
        this.templateId = templateId;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the typeOfLink
     */
    public int getTypeOfLink() {
        return typeOfLink;
    }

    /**
     * @param typeOfLink the typeOfLink to set
     */
    public void setTypeOfLink(int typeOfLink) {
        this.typeOfLink = typeOfLink;
    }
}
