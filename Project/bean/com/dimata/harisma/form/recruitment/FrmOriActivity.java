/* 
 * Form Name  	:  FrmOriActivity.java 
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

package com.dimata.harisma.form.recruitment;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.recruitment.*;

public class FrmOriActivity extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private OriActivity oriActivity;

	public static final String FRM_NAME_ORIACTIVITY		=  "FRM_NAME_ORIACTIVITY" ;

	public static final int FRM_FIELD_ORI_ACTIVITY_ID			=  0 ;
	public static final int FRM_FIELD_ORI_GROUP_ID			=  1 ;
	public static final int FRM_FIELD_ACTIVITY			=  2 ;

	public static String[] fieldNames = {
		"FRM_FIELD_ORI_ACTIVITY_ID",  "FRM_FIELD_ORI_GROUP_ID",
		"FRM_FIELD_ACTIVITY"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmOriActivity(){
	}
	public FrmOriActivity(OriActivity oriActivity){
		this.oriActivity = oriActivity;
	}

	public FrmOriActivity(HttpServletRequest request, OriActivity oriActivity){
		super(new FrmOriActivity(oriActivity), request);
		this.oriActivity = oriActivity;
	}

	public String getFormName() { return FRM_NAME_ORIACTIVITY; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public OriActivity getEntityObject(){ return oriActivity; }

	public void requestEntityObject(OriActivity oriActivity) {
		try{
			this.requestParam();
			oriActivity.setOriGroupId(getLong(FRM_FIELD_ORI_GROUP_ID));
			oriActivity.setActivity(getString(FRM_FIELD_ACTIVITY));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
