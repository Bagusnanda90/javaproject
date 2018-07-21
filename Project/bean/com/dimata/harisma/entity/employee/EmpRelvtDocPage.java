/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.employee;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author khirayinnura
 */
public class EmpRelvtDocPage extends Entity {
    private String pageTitle = "";
    private String pageDesc = "";
    private long docRelevantId = 0;
    private String fileName = "";

    /**
     * @return the pageTitle
     */
    public String getPageTitle() {
        return pageTitle;
    }

    /**
     * @param pageTitle the pageTitle to set
     */
    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    /**
     * @return the pageDesc
     */
    public String getPageDesc() {
        return pageDesc;
    }

    /**
     * @param pageDesc the pageDesc to set
     */
    public void setPageDesc(String pageDesc) {
        this.pageDesc = pageDesc;
    }

    /**
     * @return the docRelevantId
     */
    public long getDocRelevantId() {
        return docRelevantId;
    }

    /**
     * @param docRelevantId the docRelevantId to set
     */
    public void setDocRelevantId(long docRelevantId) {
        this.docRelevantId = docRelevantId;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
