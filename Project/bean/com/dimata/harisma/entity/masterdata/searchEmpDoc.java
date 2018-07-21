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
public class searchEmpDoc extends  Entity{
    private long doc_master_id ;
    private String docTitle ;
    private String docNumber;
    private int docStatus = 0;
    /**
     * @return the doc_master_id
     */
    public long getDoc_master_id() {
        return doc_master_id;
    }

    /**
     * @param doc_master_id the doc_master_id to set
     */
    public void setDoc_master_id(long doc_master_id) {
        this.doc_master_id = doc_master_id;
    }

    /**
     * @return the docTitle
     */
    public String getDocTitle() {
        return docTitle;
    }

    /**
     * @param docTitle the docTitle to set
     */
    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    /**
     * @return the docNumber
     */
    public String getDocNumber() {
        return docNumber;
    }

    /**
     * @param docNumber the docNumber to set
     */
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    /**
     * @return the docStatus
     */
    public int getDocStatus() {
        return docStatus;
    }

    /**
     * @param docStatus the docStatus to set
     */
    public void setDocStatus(int docStatus) {
        this.docStatus = docStatus;
    }
}
