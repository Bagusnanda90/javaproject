/* 
 * Form Name  	:  FrmPrevLeave.java 
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

package com.dimata.harisma.form.attendance;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.attendance.*;

public class FrmPrevLeave extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private PrevLeave prevLeave;

	public static final String FRM_NAME_PREVLEAVE		=  "FRM_NAME_PREVLEAVE" ;

	public static final int FRM_FIELD_PREV_LEAVE_ID			=  0 ;
	public static final int FRM_FIELD_DP_LM			=  1 ;
	public static final int FRM_FIELD_DP_ADD			=  2 ;
	public static final int FRM_FIELD_DP_TAKEN			=  3 ;
	public static final int FRM_FIELD_DP_BAL			=  4 ;
	public static final int FRM_FIELD_AL_LM			=  5 ;
	public static final int FRM_FIELD_AL_ADD			=  6 ;
	public static final int FRM_FIELD_AL_TAKEN			=  7 ;
	public static final int FRM_FIELD_AL_BAL			=  8 ;
	public static final int FRM_FIELD_LL_LM			=  9 ;
	public static final int FRM_FIELD_LL_ADD			=  10 ;
	public static final int FRM_FIELD_LL_TAKEN			=  11 ;
	public static final int FRM_FIELD_LL_BAL			=  12 ;

	public static String[] fieldNames = {
		"FRM_FIELD_PREV_LEAVE_ID",  "FRM_FIELD_DP_LM",
		"FRM_FIELD_DP_ADD",  "FRM_FIELD_DP_TAKEN",
		"FRM_FIELD_DP_BAL",  "FRM_FIELD_AL_LM",
		"FRM_FIELD_AL_ADD",  "FRM_FIELD_AL_TAKEN",
		"FRM_FIELD_AL_BAL",  "FRM_FIELD_LL_LM",
		"FRM_FIELD_LL_ADD",  "FRM_FIELD_LL_TAKEN",
		"FRM_FIELD_LL_BAL"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_INT,
		TYPE_INT,  TYPE_INT,
		TYPE_INT,  TYPE_INT,
		TYPE_INT,  TYPE_INT,
		TYPE_INT,  TYPE_INT,
		TYPE_INT,  TYPE_INT,
		TYPE_INT
	} ;

	public FrmPrevLeave(){
	}
	public FrmPrevLeave(PrevLeave prevLeave){
		this.prevLeave = prevLeave;
	}

	public FrmPrevLeave(HttpServletRequest request, PrevLeave prevLeave){
		super(new FrmPrevLeave(prevLeave), request);
		this.prevLeave = prevLeave;
	}

	public String getFormName() { return FRM_NAME_PREVLEAVE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public PrevLeave getEntityObject(){ return prevLeave; }

	public void requestEntityObject(PrevLeave prevLeave) {
		try{
			this.requestParam();
			prevLeave.setDpLm(getInt(FRM_FIELD_DP_LM));
			prevLeave.setDpAdd(getInt(FRM_FIELD_DP_ADD));
			prevLeave.setDpTaken(getInt(FRM_FIELD_DP_TAKEN));
			prevLeave.setDpBal(getInt(FRM_FIELD_DP_BAL));
			prevLeave.setAlLm(getInt(FRM_FIELD_AL_LM));
			prevLeave.setAlAdd(getInt(FRM_FIELD_AL_ADD));
			prevLeave.setAlTaken(getInt(FRM_FIELD_AL_TAKEN));
			prevLeave.setAlBal(getInt(FRM_FIELD_AL_BAL));
			prevLeave.setLlLm(getInt(FRM_FIELD_LL_LM));
			prevLeave.setLlAdd(getInt(FRM_FIELD_LL_ADD));
			prevLeave.setLlTaken(getInt(FRM_FIELD_LL_TAKEN));
			prevLeave.setLlBal(getInt(FRM_FIELD_LL_BAL));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
