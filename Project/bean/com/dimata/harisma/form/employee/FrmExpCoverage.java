/* 
 * Form Name  	:  FrmExpCoverage.java 
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

package com.dimata.harisma.form.employee;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.employee.*;

public class FrmExpCoverage extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private ExpCoverage expCoverage;

	public static final String FRM_NAME_EXPCOVERAGE		=  "FRM_NAME_EXPCOVERAGE" ;

	public static final int FRM_FIELD_EXPLANATION_COVERAGE_ID			=  0 ;
	public static final int FRM_FIELD_GROUP_RANK_ID			=  1 ;
	public static final int FRM_FIELD_DESCRIPTIONS			=  2 ;
	public static final int FRM_FIELD_DEF_COVERAGE			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_EXPLANATION_COVERAGE_ID",  "FRM_FIELD_GROUP_RANK_ID",
		"FRM_FIELD_DESCRIPTIONS",  "FRM_FIELD_DEF_COVERAGE"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmExpCoverage(){
	}
	public FrmExpCoverage(ExpCoverage expCoverage){
		this.expCoverage = expCoverage;
	}

	public FrmExpCoverage(HttpServletRequest request, ExpCoverage expCoverage){
		super(new FrmExpCoverage(expCoverage), request);
		this.expCoverage = expCoverage;
	}

	public String getFormName() { return FRM_NAME_EXPCOVERAGE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public ExpCoverage getEntityObject(){ return expCoverage; }

	public void requestEntityObject(ExpCoverage expCoverage) {
		try{
			this.requestParam();
			expCoverage.setGroupRankId(getLong(FRM_FIELD_GROUP_RANK_ID));
			expCoverage.setDescriptions(getString(FRM_FIELD_DESCRIPTIONS));
			expCoverage.setDefCoverage(getString(FRM_FIELD_DEF_COVERAGE));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
