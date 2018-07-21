/* 
 * Form Name  	:  FrmSrcTrainingHistory.java 
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

public class FrmSrcTrainingHistory extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcTrainingHistory srcTrainingHistory;

	public static final String FRM_NAME_SRCTRAININGHISTORY		=  "FRM_NAME_SRCTRAININGHISTORY" ;

	public static final int FRM_FIELD_NAME			=  0 ;
	public static final int FRM_FIELD_PAYROLL			=  1 ;
	public static final int FRM_FIELD_PROGRAM			=  2 ;
	public static final int FRM_FIELD_PERIODFROM			=  3 ;
	public static final int FRM_FIELD_PERIODTO			=  4 ;
	public static final int FRM_FIELD_TRAINER			=  5 ;

	public static String[] fieldNames = {
		"FRM_FIELD_NAME",  "FRM_FIELD_PAYROLL",
		"FRM_FIELD_PROGRAM",  "FRM_FIELD_PERIODFROM",
		"FRM_FIELD_PERIODTO",  "FRM_FIELD_TRAINER"
	} ;

	public static int[] fieldTypes = {
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING,  TYPE_DATE,
		TYPE_DATE,  TYPE_STRING
	} ;

	public FrmSrcTrainingHistory(){
	}
	public FrmSrcTrainingHistory(SrcTrainingHistory srcTrainingHistory){
		this.srcTrainingHistory = srcTrainingHistory;
	}

	public FrmSrcTrainingHistory(HttpServletRequest request, SrcTrainingHistory srcTrainingHistory){
		super(new FrmSrcTrainingHistory(srcTrainingHistory), request);
		this.srcTrainingHistory = srcTrainingHistory;
	}

	public String getFormName() { return FRM_NAME_SRCTRAININGHISTORY; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcTrainingHistory getEntityObject(){ return srcTrainingHistory; }

	public void requestEntityObject(SrcTrainingHistory srcTrainingHistory) {
		try{
			this.requestParam();
                        /*
			srcTrainingHistory.setName(getString(FRM_FIELD_NAME));
			srcTrainingHistory.setPayroll(getString(FRM_FIELD_PAYROLL));
			srcTrainingHistory.setProgram(getString(FRM_FIELD_PROGRAM));
			srcTrainingHistory.setPeriodfrom(getDate(FRM_FIELD_PERIODFROM));
			srcTrainingHistory.setPeriodto(getDate(FRM_FIELD_PERIODTO));
			srcTrainingHistory.setTrainer(getString(FRM_FIELD_TRAINER));
                         */
			srcTrainingHistory.setEmployee(getString(FRM_FIELD_NAME));
			srcTrainingHistory.setPayrollNumber(getString(FRM_FIELD_PAYROLL));
			srcTrainingHistory.setProgram(getString(FRM_FIELD_PROGRAM));
			srcTrainingHistory.setStartDate(getDate(FRM_FIELD_PERIODFROM));
			srcTrainingHistory.setEndDate(getDate(FRM_FIELD_PERIODTO));
			srcTrainingHistory.setTrainer(getString(FRM_FIELD_TRAINER));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
