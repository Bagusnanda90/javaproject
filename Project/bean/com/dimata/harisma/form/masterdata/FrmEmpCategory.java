/* 
 * Form Name  	:  FrmEmpCategory.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya 
 * @version  	: 01 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.masterdata;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;

public class FrmEmpCategory extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private EmpCategory empCategory;

	public static final String FRM_NAME_EMPCATEGORY		=  "FRM_NAME_EMPCATEGORY" ;

	public static final int FRM_FIELD_EMP_CATEGORY_ID		=  0 ;
	public static final int FRM_FIELD_EMP_CATEGORY			=  1 ;
	public static final int FRM_FIELD_DESCRIPTION			=  2 ;
	public static final int FRM_FIELD_TYPE_FOR_TAX			=  3 ;
//update by satrya 2013-04-11
        public static final int FRM_FIELD_ENTITLE_FOR_LEAVE		=  4 ;
        //update by satrya 2014-02-10
        public static final int FRM_FIELD_ENTITLE_FOR_INSENTIF		=  5 ;
        
        public static final int FRM_FIELD_CODE                                = 6 ;
        public static final int FRM_FIELD_CATEGORY_TYPE                       = 7 ;
        public static final int FRM_FIELD_ENTITLE_DP                       = 8 ;
        
	public static String[] fieldNames = {
		"FRM_FIELD_EMP_CATEGORY_ID",  "FRM_FIELD_EMP_CATEGORY",
		"FRM_FIELD_DESCRIPTION", "FRM_FIELD_TYPE_FOR_TAX","FRM_FIELD_ENTITLE_FOR_LEAVE","FRM_FIELD_ENTITLE_FOR_INSENTIF",
                "FRM_FIELD_CODE","FRM_FIELD_CATEGORY_TYPE","FRM_FIELD_ENTITLE_DP"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING, TYPE_INT,TYPE_INT,TYPE_INT,
                TYPE_STRING, TYPE_INT, TYPE_INT
	} ;

	public FrmEmpCategory(){
	}
	public FrmEmpCategory(EmpCategory empCategory){
		this.empCategory = empCategory;
	}

	public FrmEmpCategory(HttpServletRequest request, EmpCategory empCategory){
		super(new FrmEmpCategory(empCategory), request);
		this.empCategory = empCategory;
	}

	public String getFormName() { return FRM_NAME_EMPCATEGORY; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public EmpCategory getEntityObject(){ return empCategory; }

	public void requestEntityObject(EmpCategory empCategory) {
		try{
			this.requestParam();
			empCategory.setEmpCategory(getString(FRM_FIELD_EMP_CATEGORY));
			empCategory.setDescription(getString(FRM_FIELD_DESCRIPTION));
                        empCategory.setTypeForTax(getInt(FRM_FIELD_TYPE_FOR_TAX));
                        //UPDATE BY SATRYA 2013-04-11
                        empCategory.setEntitleLeave(getInt(FRM_FIELD_ENTITLE_FOR_LEAVE));
                        //update by satrya 2014-02-10
                        empCategory.setEntitleInsentif(getInt(FRM_FIELD_ENTITLE_FOR_INSENTIF));
                        empCategory.setCode(getString(FRM_FIELD_CODE));
                        empCategory.setCategoryType(getInt(FRM_FIELD_CATEGORY_TYPE));
                        empCategory.setEntitleDP(getInt(FRM_FIELD_ENTITLE_DP));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
