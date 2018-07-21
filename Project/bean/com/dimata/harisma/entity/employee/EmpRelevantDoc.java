/*
 * EmpRelevantDoc.java
 *
 * Created on December 3, 2007, 3:10 PM
 */

package com.dimata.harisma.entity.employee;


/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;

/**
 *
 * @author  yunny
 */
public class EmpRelevantDoc  extends Entity{
    private long docRelevantId;
    private long employeeId;
    private String docTitle;
    private String docDescription="";
    private String fileName="";
    private String docAttachFile="";
    private long empRelvtDocGrpId;
    
    /** Creates a new instance of EmpRelevantDoc */
    public EmpRelevantDoc() {
    }
    
    /**
     * Getter for property docRelevantId.
     * @return Value of property docRelevantId.
     */
    public long getDocRelevantId() {
        return docRelevantId;
    }
    
    /**
     * Setter for property docRelevantId.
     * @param docRelevantId New value of property docRelevantId.
     */
    public void setDocRelevantId(long docRelevantId) {
        this.docRelevantId = docRelevantId;
    }
    
    /**
     * Getter for property employeeId.
     * @return Value of property employeeId.
     */
    public long getEmployeeId() {
        return employeeId;
    }
    
    /**
     * Setter for property employeeId.
     * @param employeeId New value of property employeeId.
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
    
    /**
     * Getter for property docTitle.
     * @return Value of property docTitle.
     */
    public java.lang.String getDocTitle() {
        return docTitle;
    }
    
    /**
     * Setter for property docTitle.
     * @param docTitle New value of property docTitle.
     */
    public void setDocTitle(java.lang.String docTitle) {
        this.docTitle = docTitle;
    }
    
    /**
     * Getter for property docDescription.
     * @return Value of property docDescription.
     */
    public java.lang.String getDocDescription() {
        return docDescription;
    }
    
    /**
     * Setter for property docDescription.
     * @param docDescription New value of property docDescription.
     */
    public void setDocDescription(java.lang.String docDescription) {
        this.docDescription = docDescription;
    }
    
    /**
     * Getter for property docAttachFile.
     * @return Value of property docAttachFile.
     */
    public java.lang.String getDocAttachFile() {
        return docAttachFile;
    }
    
    /**
     * Setter for property docAttachFile.
     * @param docAttachFile New value of property docAttachFile.
     */
    public void setDocAttachFile(java.lang.String docAttachFile) {
        this.docAttachFile = docAttachFile;
    }
    
    /**
     * Getter for property fileName.
     * @return Value of property fileName.
     */
    public java.lang.String getFileName() {
        return fileName;
    }    
      
    /**
     * Setter for property fileName.
     * @param fileName New value of property fileName.
     */
    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the empRelvtDocGrpId
     */
    public long getEmpRelvtDocGrpId() {
        return empRelvtDocGrpId;
    }

    /**
     * @param empRelvtDocGrpId the empRelvtDocGrpId to set
     */
    public void setEmpRelvtDocGrpId(long empRelvtDocGrpId) {
        this.empRelvtDocGrpId = empRelvtDocGrpId;
    }
    
}
