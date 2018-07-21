/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
 * @author Tu Roy
 */
public class FrmSrcSpecialUpload extends FRMHandler implements I_FRMInterface, I_FRMType {
        private SrcSpecialUpload objSrcSpecialUpload;

	public static final String FRM_SPECIAL_UPLOAD		=  "FRM_SPECIAL_UPLOAD" ;
        
	public static final int FRM_FIELD_EMP_NAME		=  0 ;
	public static final int FRM_FIELD_EMP_PAYROLL		=  1 ;
	public static final int FRM_FIELD_EMP_CAT		=  2 ;
	public static final int FRM_FIELD_EMP_DEPT		=  3 ;
	public static final int FRM_FIELD_EMP_SEC		=  4 ;
	public static final int FRM_FIELD_EMP_POS		=  5 ;
	public static final int FRM_FIELD_DATA_STATUS		=  6 ;
	public static final int FRM_FIELD_OPNAME_DATE		=  7 ;

	public static String[] fieldNames = {
		"FRM_FIELD_EMP_NAME",  
                "FRM_FIELD_EMP_PAYROLL",
		"FRM_FIELD_EMP_CAT",  
                "FRM_FIELD_EMP_DEPT",
                "FRM_FIELD_EMP_SEC",
                "FRM_FIELD_EMP_POS",
                "FRM_FIELD_DATA_STATUS",
                "FRM_FIELD_OPNAME_DATE"
	};

	public static int[] fieldTypes = {
		TYPE_STRING,  
                TYPE_STRING,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_INT,
		TYPE_DATE
	};

	public FrmSrcSpecialUpload(){
	}
	public FrmSrcSpecialUpload(SrcSpecialUpload objSrcSpecialUpload){
		this.objSrcSpecialUpload = objSrcSpecialUpload;
	}

	public FrmSrcSpecialUpload(HttpServletRequest request, SrcSpecialUpload objSrcSpecialUpload){
		super(new FrmSrcSpecialUpload(objSrcSpecialUpload), request);
		this.objSrcSpecialUpload = objSrcSpecialUpload;
	}

	public String getFormName() { return FRM_SPECIAL_UPLOAD;} 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcSpecialUpload getEntityObject(){ return objSrcSpecialUpload; }

	public void requestEntityObject(SrcSpecialUpload srcSpecialUpload) {
            try{
                this.requestParam();
                srcSpecialUpload.setEmployeeCategory(getLong(FRM_FIELD_EMP_CAT));
                srcSpecialUpload.setEmployeeDepartement(getLong(FRM_FIELD_EMP_DEPT));
                srcSpecialUpload.setEmployeeName(getString(FRM_FIELD_EMP_NAME));
                srcSpecialUpload.setEmployeePayroll(getString(FRM_FIELD_EMP_PAYROLL));
                srcSpecialUpload.setEmployeePosition(getLong(FRM_FIELD_EMP_POS));
                srcSpecialUpload.setEmployeeSection(getLong(FRM_FIELD_EMP_SEC));
                srcSpecialUpload.setDataStatus(getInt(FRM_FIELD_DATA_STATUS));
                srcSpecialUpload.setOpnameDate(getDate(FRM_FIELD_OPNAME_DATE));
            }catch(Exception e){
                    System.out.println("Error on requestEntityObject : "+e.toString());
            }
	}
}
