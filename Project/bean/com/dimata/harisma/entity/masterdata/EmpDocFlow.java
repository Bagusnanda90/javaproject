/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.masterdata;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author GUSWIK
 */
public class EmpDocFlow extends Entity{
     private long emp_doc_flow_id ; 
     private String flowTitle ; 
     private int flowIndex ;
     private long signedBy ; 
     private Date signedDate ;
     private String note ;
     private long empDocId ;

    /**
     * @return the emp_doc_flow_id
     */
    public long getEmp_doc_flow_id() {
        return emp_doc_flow_id;
    }

    /**
     * @param emp_doc_flow_id the emp_doc_flow_id to set
     */
    public void setEmp_doc_flow_id(long emp_doc_flow_id) {
        this.emp_doc_flow_id = emp_doc_flow_id;
    }



    /**
     * @return the flowIndex
     */
    public int getFlowIndex() {
        return flowIndex;
    }

    /**
     * @param flowIndex the flowIndex to set
     */
    public void setFlowIndex(int flowIndex) {
        this.flowIndex = flowIndex;
    }

    /**
     * @return the signedBy
     */
    public long getSignedBy() {
        return signedBy;
    }

    /**
     * @param signedBy the signedBy to set
     */
    public void setSignedBy(long signedBy) {
        this.signedBy = signedBy;
    }

    /**
     * @return the signedDate
     */
    public Date getSignedDate() {
        return signedDate;
    }

    /**
     * @param signedDate the signedDate to set
     */
    public void setSignedDate(Date signedDate) {
        this.signedDate = signedDate;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the flowTitle
     */
    public String getFlowTitle() {
        return flowTitle;
    }

    /**
     * @param flowTitle the flowTitle to set
     */
    public void setFlowTitle(String flowTitle) {
        this.flowTitle = flowTitle;
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
