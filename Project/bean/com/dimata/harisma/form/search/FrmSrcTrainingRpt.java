
package com.dimata.harisma.form.search;


/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 

/* qdep package */ 
import com.dimata.qdep.form.*;

/* project package */
import com.dimata.harisma.entity.search.*;

/**
 *
 * @author bayu
 */

public class FrmSrcTrainingRpt extends FRMHandler implements I_FRMInterface, I_FRMType {

        private SrcTrainingRpt srcTrainingRpt;

	public static final String FRM_NAME_TRAIN_RPT       =  "FRM_TRAINING_REPORT" ;

	public static final int FRM_FIELD_DEPARTMENT        =  0 ;
	public static final int FRM_FIELD_SECTION           =  1 ;
	public static final int FRM_FIELD_PAYROLL           =  2 ;
        public static final int FRM_FIELD_NAME              =  3 ;
        public static final int FRM_FIELD_START_DATE        =  4 ;
        public static final int FRM_FIELD_END_DATE          =  5 ;
        public static final int FRM_FIELD_SORT_BY           =  6 ;
        public static final int FRM_FIELD_TRAINER           =  7 ;
        public static final int FRM_FIELD_TRAIN_ID          =  8 ;
        public static final int FRM_FIELD_TRAIN_MONTH       =  9 ;

	public static String[] fieldNames = {
		"FRM_FIELD_DEPARTMENT",  
                "FRM_FIELD_SECTION",
		"FRM_FIELD_PAYROLL", 
                "FRM_FIELD_NAME",
                "FRM_FIELD_START_DATE",
                "FRM_FIELD_END_DATE",
                "FRM_FIELD_SORT_BY",
                "FRM_FIELD_TRAINER",
                "FRM_FIELD_TRAIN_ID",
                "FRM_FIELD_TRAIN_MONTH"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  
                TYPE_LONG,
		TYPE_STRING, 
                TYPE_STRING,
                TYPE_DATE,
                TYPE_DATE,
                TYPE_INT,
                TYPE_STRING,
                TYPE_LONG,
                TYPE_DATE
	} ;


        public static final int ORDER_EMPLOYEE_NAME		= 0;
        public static final int ORDER_PAYROLL_NUM		= 1;
        public static final int ORDER_DEPARTMENT		= 2;

        public static final String[] orderKey  = {"Name", "Payroll Number", "Department"};
        public static final int[] orderValue  = {0,1,2};

        public static Vector getOrderKey(){
            Vector order = new Vector();

            for(int i=0;i<orderKey.length;i++)          
                order.add(orderKey[i]);

            return order;
        }

        public static Vector getOrderValue(){
            Vector order = new Vector();
            
            for(int i=0;i<orderValue.length;i++)            
                order.add(""+orderValue[i]);
            
            return order;
        }


	public FrmSrcTrainingRpt(){
	}
        
	public FrmSrcTrainingRpt(SrcTrainingRpt srcTrainingRpt){
		this.srcTrainingRpt = srcTrainingRpt;
	}

	public FrmSrcTrainingRpt(HttpServletRequest request, SrcTrainingRpt srcTrainingRpt){
		super(new FrmSrcTrainingRpt(srcTrainingRpt), request);
		this.srcTrainingRpt = srcTrainingRpt;
	}

	public String getFormName() { return FRM_NAME_TRAIN_RPT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcTrainingRpt getEntityObject(){ return srcTrainingRpt; }

	public void requestEntityObject(SrcTrainingRpt srcTrainingRpt) {
		try{
			this.requestParam();
			srcTrainingRpt.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT));
			srcTrainingRpt.setSectionId(getLong(FRM_FIELD_SECTION));
			srcTrainingRpt.setPayroll(getString(FRM_FIELD_PAYROLL));
                        srcTrainingRpt.setName(getString(FRM_FIELD_NAME));
                        srcTrainingRpt.setStartDate(getDate(FRM_FIELD_START_DATE));
                        srcTrainingRpt.setEndDate(getDate(FRM_FIELD_END_DATE));
                        srcTrainingRpt.setSortBy(getInt(FRM_FIELD_SORT_BY)); 
                        srcTrainingRpt.setTrainer(getString(FRM_FIELD_TRAINER));
                        srcTrainingRpt.setTrainingId(getLong(FRM_FIELD_TRAIN_ID));
                        srcTrainingRpt.setTrainingMonth(getDate(FRM_FIELD_TRAIN_MONTH));
		}
                catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
