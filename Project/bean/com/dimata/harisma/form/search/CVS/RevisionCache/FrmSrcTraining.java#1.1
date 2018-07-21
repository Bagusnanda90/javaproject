/* 
 * Form Name  	:  FrmSrcTraining.java 
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

public class FrmSrcTraining extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcTraining srcTraining;

	public static final String FRM_NAME_APPRAISAL		=  "FRM_NAME_APPRAISAL" ;

	public static final int FRM_FIELD_DEPARTMENT			=  0 ;
	public static final int FRM_FIELD_TRAINING			=  1 ;
	public static final int FRM_FIELD_TYPE_OF_SEARCH		=  2 ;
        public static final int FRM_FIELD_SORT_BY			=  3 ;
        public static final int FRM_FIELD_TRAIN_TYPE                    =  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_DEPARTMENT",  "FRM_FIELD_TRAINING",
		"FRM_FIELD_TYPE_OF_SEARCH", "FRM_FIELD_SORT_BY",
                "FRM_FIELD_TRAIN_TYPE"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG,
		TYPE_INT, TYPE_INT,
                TYPE_INT
	} ;


     public static final int ORDER_EMPLOYEE_NAME		= 0;
     public static final int ORDER_PAYROLL_NUM			= 1;
     public static final int ORDER_DEPARTMENT			= 2;

     public static final String[] orderKey  = {"Name", "Payroll Number", "Department"};
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


	public FrmSrcTraining(){
	}
	public FrmSrcTraining(SrcTraining srcTraining){
		this.srcTraining = srcTraining;
	}

	public FrmSrcTraining(HttpServletRequest request, SrcTraining srcTraining){
		super(new FrmSrcTraining(srcTraining), request);
		this.srcTraining = srcTraining;
	}

	public String getFormName() { return FRM_NAME_APPRAISAL; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcTraining getEntityObject(){ return srcTraining; }

	public void requestEntityObject(SrcTraining srcTraining) {
		try{
			this.requestParam();
			srcTraining.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT));
			srcTraining.setTrainingId(getLong(FRM_FIELD_TRAINING));
			srcTraining.setTypeOfSearch(getInt(FRM_FIELD_TYPE_OF_SEARCH));
                        srcTraining.setSortBy(getInt(FRM_FIELD_SORT_BY));
                        srcTraining.setTrainingType(getInt(FRM_FIELD_TRAIN_TYPE));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
