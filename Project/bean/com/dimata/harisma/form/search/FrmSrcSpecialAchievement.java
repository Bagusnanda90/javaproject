/* 
 * Form Name  	:  FrmSrcSpecialAchievement.java 
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

public class FrmSrcSpecialAchievement extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcSpecialAchievement srcSpecialAchievement;

	public static final String FRM_NAME_SRCSPECIALACHIEVEMENT		=  "FRM_NAME_SRCSPECIALACHIEVEMENT" ;

	public static final int FRM_FIELD_NAME			=  0 ;
	public static final int FRM_FIELD_PAYROLL			=  1 ;
	public static final int FRM_FIELD_AWARD			=  2 ;
	public static final int FRM_FIELD_START			=  3 ;
	public static final int FRM_FIELD_END			=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_NAME",  "FRM_FIELD_PAYROLL",
		"FRM_FIELD_AWARD",  "FRM_FIELD_START",
		"FRM_FIELD_END"
	} ;

	public static int[] fieldTypes = {
		TYPE_STRING,  TYPE_STRING,
		TYPE_STRING,  TYPE_DATE,
		TYPE_DATE
	} ;

	public FrmSrcSpecialAchievement(){
	}
	public FrmSrcSpecialAchievement(SrcSpecialAchievement srcSpecialAchievement){
		this.srcSpecialAchievement = srcSpecialAchievement;
	}

	public FrmSrcSpecialAchievement(HttpServletRequest request, SrcSpecialAchievement srcSpecialAchievement){
		super(new FrmSrcSpecialAchievement(srcSpecialAchievement), request);
		this.srcSpecialAchievement = srcSpecialAchievement;
	}

	public String getFormName() { return FRM_NAME_SRCSPECIALACHIEVEMENT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcSpecialAchievement getEntityObject(){ return srcSpecialAchievement; }

	public void requestEntityObject(SrcSpecialAchievement srcSpecialAchievement) {
		try{
			this.requestParam();
			srcSpecialAchievement.setName(getString(FRM_FIELD_NAME));
			srcSpecialAchievement.setPayrollNumber(getString(FRM_FIELD_PAYROLL));
			srcSpecialAchievement.setAward(getString(FRM_FIELD_AWARD));
			srcSpecialAchievement.setStartDate(getDate(FRM_FIELD_START));
			srcSpecialAchievement.setEndDate(getDate(FRM_FIELD_END));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
