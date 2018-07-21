
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

public class FrmTrainVenue extends FRMHandler implements I_FRMInterface, I_FRMType {
	
	public static final String FRM_NAME_TRAIN_VENUE      =  "FRM_TRAIN_VENUE";

	public static final int FRM_FIELD_TRAIN_VENUE_ID     =  0;
	public static final int FRM_FIELD_TRAIN_VENUE_NAME   =  1;
	public static final int FRM_FIELD_TRAIN_VENUE_DESC   =  2;
	
	public static String[] fieldNames = 
        {
            "FRM_TRAIN_VENUE_ID",  
            "FRM_TRAIN_VENUE_NAME",
            "FRM_TRAIN_VENUE_DESCRIPTION"
	};

	public static int[] fieldTypes = 
        {
            TYPE_LONG,  
            TYPE_STRING + ENTRY_REQUIRED,
            TYPE_STRING
	};
        
        
        private TrainVenue trainVenue;


	public FrmTrainVenue(){
	}
        
	public FrmTrainVenue(TrainVenue trainVenue){
            this.trainVenue = trainVenue;
	}

	public FrmTrainVenue(HttpServletRequest request, TrainVenue trainVenue){
            super(new FrmTrainVenue(trainVenue), request);
            this.trainVenue = trainVenue;
	}

        
	public String getFormName() { 
            return FRM_NAME_TRAIN_VENUE; 
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

        
	public TrainVenue getEntityObject(){ 
            return trainVenue; 
        }

	public void requestEntityObject(TrainVenue trainVenue) {
            try {
                this.requestParam();
                trainVenue.setVenueName(this.getString(FRM_FIELD_TRAIN_VENUE_NAME));
                trainVenue.setVenueDesc(this.getString(FRM_FIELD_TRAIN_VENUE_DESC));                
            }
            catch(Exception e) {
                e.printStackTrace();
            }
	}
}
