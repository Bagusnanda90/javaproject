/* 
 * Form Name  	:  FrmSrcAppraisal.java 
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

public class FrmSrcDPUpload extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcDPUpload srcDPUpload;

	public static final String FRM_DP_UPLOAD		=  "FRM_DP_UPLOAD" ;
        
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


     
	public FrmSrcDPUpload(){
	}
	public FrmSrcDPUpload(SrcDPUpload srcDPUpload){
		this.srcDPUpload = srcDPUpload;
	}

	public FrmSrcDPUpload(HttpServletRequest request, SrcDPUpload srcDPUpload){
		super(new FrmSrcDPUpload(srcDPUpload), request);
		this.srcDPUpload = srcDPUpload;
	}

	public String getFormName() { return FRM_DP_UPLOAD;} 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcDPUpload getEntityObject(){ return srcDPUpload; }

	public void requestEntityObject(SrcDPUpload srcDPUpload) {
            try{
                this.requestParam();
                srcDPUpload.setEmployeeCategory(getLong(FRM_FIELD_EMP_CAT));
                srcDPUpload.setEmployeeDepartement(getLong(FRM_FIELD_EMP_DEPT));
                srcDPUpload.setEmployeeName(getString(FRM_FIELD_EMP_NAME));
                srcDPUpload.setEmployeePayroll(getString(FRM_FIELD_EMP_PAYROLL));
                srcDPUpload.setEmployeePosition(getLong(FRM_FIELD_EMP_POS));
                srcDPUpload.setEmployeeSection(getLong(FRM_FIELD_EMP_SEC));
                srcDPUpload.setDataStatus(getInt(FRM_FIELD_DATA_STATUS));
                srcDPUpload.setOpnameDate(getDate(FRM_FIELD_OPNAME_DATE));
            }catch(Exception e){
                    System.out.println("Error on requestEntityObject : "+e.toString());
            }
	}
}
