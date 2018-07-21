
package com.dimata.harisma.form.masterdata;

// import java
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 

// import qdep
import com.dimata.qdep.form.*;

// import harisma
import com.dimata.harisma.entity.masterdata.*;

/**
 *
 * @author bayu
 */

public class FrmTrainType extends FRMHandler implements I_FRMInterface, I_FRMType {
	
	public static final String FRM_NAME_TRAIN_TYPE      =  "FRM_TRAIN_TYPE";

        public static final int FRM_FIELD_TRAIN_TYPE_ID     =  0;
	public static final int FRM_FIELD_TRAIN_TYPE_NAME   =  1;
	public static final int FRM_FIELD_TRAIN_TYPE_DESC   =  2;
        public static final int FRM_FIELD_TRAIN_TYPE_CODE   =  3;
	
	public static String[] fieldNames = 
        {
            "FRM_TRAIN_TYPE_ID",
            "FRM_TRAIN_TYPE_NAME",
            "FRM_TRAIN_TYPE_DESCRIPTION",
            "FRM_TRAIN_TYPE_CODE"
	};

	public static int[] fieldTypes = 
        { 
            TYPE_LONG,
            TYPE_STRING + ENTRY_REQUIRED,
            TYPE_STRING,
            TYPE_STRING
	};
        
        
        private TrainType trainType;


	public FrmTrainType(){
	}
        
	public FrmTrainType(TrainType trainType){
            this.trainType = trainType;
	}

	public FrmTrainType(HttpServletRequest request, TrainType trainType){
            super(new FrmTrainType(trainType), request);
            this.trainType = trainType;
	}

        
	public String getFormName() { 
            return FRM_NAME_TRAIN_TYPE; 
        } 

	public int[] getFieldTypes() { 
            return fieldTypes; 
        }

	public String[] getFieldNames() {
            return fieldNames; 
        } 

	public int getFieldSize() { 
            return fieldNames.length; 
        } 

        
	public TrainType getEntityObject(){ 
            return trainType; 
        }

	public void requestEntityObject(TrainType trainType) {
            try {
                this.requestParam();
                trainType.setTypeName(this.getString(FRM_FIELD_TRAIN_TYPE_NAME));
                trainType.setTypeDesc(this.getString(FRM_FIELD_TRAIN_TYPE_DESC));                
                trainType.setTypeCode(this.getString(FRM_FIELD_TRAIN_TYPE_CODE));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
	}
}
