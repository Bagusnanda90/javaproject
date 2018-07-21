/*
 * TrainingFile.java
 *
 * Created on December 7, 2007, 2:43 PM
 */

package com.dimata.harisma.entity.masterdata;


/* package java */ 
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;


/**
 *
 * @author  Yunny
 */
public class TrainingFile extends Entity{
    private long trainingFileId;
    private long trainingId;
    private String fileName="";
    private String attachFile="";
   
    
    /** Creates a new instance of TrainingFile */
    public TrainingFile() {
    }
    
    /**
     * Getter for property trainingFileId.
     * @return Value of property trainingFileId.
     */
    public long getTrainingFileId() {
        return trainingFileId;
    }
    
    /**
     * Setter for property trainingFileId.
     * @param trainingFileId New value of property trainingFileId.
     */
    public void setTrainingFileId(long trainingFileId) {
        this.trainingFileId = trainingFileId;
    }
    
    /**
     * Getter for property trainingId.
     * @return Value of property trainingId.
     */
    public long getTrainingId() {
        return trainingId;
    }
    
    /**
     * Setter for property trainingId.
     * @param trainingId New value of property trainingId.
     */
    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
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
     * Getter for property attachFile.
     * @return Value of property attachFile.
     */
    public java.lang.String getAttachFile() {
        return attachFile;
    }
    
    /**
     * Setter for property attachFile.
     * @param attachFile New value of property attachFile.
     */
    public void setAttachFile(java.lang.String attachFile) {
        this.attachFile = attachFile;
    }
    
}
