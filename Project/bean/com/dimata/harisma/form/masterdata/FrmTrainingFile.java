/*
 * FrmTrainingFile.java
 *
 * Created on December 7, 2007, 5:03 PM
 */

package com.dimata.harisma.form.masterdata;

/**
 *
 * @author  Yunny
 */


/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;


public class FrmTrainingFile extends FRMHandler implements I_FRMInterface, I_FRMType {
     private TrainingFile trainingFile;
     
     public static final String FRM_TRAINING_FILE		=  "FRM_TRAINING_FILE" ;
     
     public static final int FRM_FIELD_TRAINING_FILE_ID			=  0 ;
     public static final int FRM_FIELD_TRAINING_ID			=  1 ;
     public static final int FRM_FIELD_FILE_NAME			=  2 ;
     public static final int FRM_FIELD_ATTACH_FILE			=  3 ;
     
     
     public static String[] fieldNames = {
		"FRM_FIELD_TRAINING_FILE_ID",  "FRM_FIELD_TRAINING_ID",
		"FRM_FIELD_FILE_NAME","FRM_FIELD_ATTACH_FILE"
	} ;
        
      public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG,  
		TYPE_STRING,
                TYPE_STRING
     } ;
    
    
    
    /** Creates a new instance of FrmTrainingFile */
    public FrmTrainingFile() {
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int getFieldSize() {
         return fieldNames.length;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String getFormName() {
        return FRM_TRAINING_FILE;
    }
    
     public FrmTrainingFile(TrainingFile trainingFile){
		this.trainingFile = trainingFile;
     }
     
      public FrmTrainingFile(HttpServletRequest request, TrainingFile trainingFile){
		super(new FrmTrainingFile(trainingFile), request);
		this.trainingFile = trainingFile;
	}
      
      public TrainingFile getEntityObject(){ return trainingFile; }
      
       public void requestEntityObject(TrainingFile trainingFile) {
		try{
			this.requestParam();
			trainingFile.setTrainingId(getLong(FRM_FIELD_TRAINING_ID));
			trainingFile.setFileName(getString(FRM_FIELD_FILE_NAME));
			trainingFile.setAttachFile(getString(FRM_FIELD_ATTACH_FILE));
                       
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
       
       
    
    
}
