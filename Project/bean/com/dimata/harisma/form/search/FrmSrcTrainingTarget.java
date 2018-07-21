/* 
 * Form Name  	:  FrmSrcTrainingTarget.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	:  [authorName] 
 * @version  	:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

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

public class FrmSrcTrainingTarget extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcTrainingTarget srcTrainingTarget;

	public static final String FRM_NAME_TRAIN_TARGET		=  "FRM_TRAINING_TARGET" ;

	public static final int FRM_FIELD_DEPARTMENT			=  0 ;
	public static final int FRM_FIELD_MONTH 			=  1 ;
	public static final int FRM_FIELD_TYPE_OF_SEARCH		=  2 ;
        public static final int FRM_FIELD_SORT_BY			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_DEPARTMENT",  
                "FRM_FIELD_MONTH",
		"FRM_FIELD_TYPE_OF_SEARCH", 
                "FRM_FIELD_SORT_BY"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  
                TYPE_DATE,
		TYPE_INT, 
                TYPE_INT
	} ;


        public static final int ORDER_EMPLOYEE_NAME     = 0;
        public static final int ORDER_PAYROLL_NUM	= 1;

        public static final String[] orderKey  = {"Name", "Payroll Number"};
        public static final int[] orderValue  = {0,1,2};

         public static Vector getOrderKey(){
            Vector order = new Vector();
            for(int i=0;i<orderKey.length;i++)
            {
                order.add(orderKey[i]);
            }
            return order;
         }

         public static Vector getOrderValue(){
            Vector order = new Vector();
            for(int i=0;i<orderValue.length;i++)
            {
                order.add(""+orderValue[i]);
            }
            return order;
         }


	public FrmSrcTrainingTarget(){
	}
        
	public FrmSrcTrainingTarget(SrcTrainingTarget srcTrainingTarget){
		this.srcTrainingTarget = srcTrainingTarget;
	}

	public FrmSrcTrainingTarget(HttpServletRequest request, SrcTrainingTarget srcTrainingTarget){
		super(new FrmSrcTrainingTarget(srcTrainingTarget), request);
		this.srcTrainingTarget = srcTrainingTarget;
	}

	public String getFormName() { return FRM_NAME_TRAIN_TARGET; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcTrainingTarget getEntityObject(){ return srcTrainingTarget; }

	public void requestEntityObject(SrcTrainingTarget srcTrainingTarget) {
		try{
			this.requestParam();
			srcTrainingTarget.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT));
			srcTrainingTarget.setMonth(getDate(FRM_FIELD_MONTH));
			srcTrainingTarget.setTypeOfSearch(getInt(FRM_FIELD_TYPE_OF_SEARCH));
                        srcTrainingTarget.setSortBy(getInt(FRM_FIELD_SORT_BY));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
