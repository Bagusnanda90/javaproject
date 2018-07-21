/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata.jobdesc;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author khirayinnura
 */
public class JobCat extends Entity {
    private long jobCategoryId = 0;
    private String categoryTitle = "";
    private String description = "";

    /**
     * @return the jobCategoryId
     */
    public long getJobCategoryId() {
        return jobCategoryId;
    }

    /**
     * @param jobCategoryId the jobCategoryId to set
     */
    public void setJobCategoryId(long jobCategoryId) {
        this.jobCategoryId = jobCategoryId;
    }

    /**
     * @return the categoryTitle
     */
    public String getCategoryTitle() {
        return categoryTitle;
    }

    /**
     * @param categoryTitle the categoryTitle to set
     */
    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
