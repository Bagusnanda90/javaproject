/* 
 * Form Name  	:  FrmOriGroup.java 
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

public class FrmOriGroup extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private OriGroup oriGroup;

	public static final String FRM_NAME_ORIGROUP		=  "FRM_NAME_ORIGROUP" ;

	public static final int FRM_FIELD_ORI_GROUP_ID			=  0 ;
	public static final int FRM_FIELD_GROUP_NAME			=  1 ;

	public static String[] fieldNames = {
		"FRM_FIELD_ORI_GROUP_ID",  "FRM_FIELD_GROUP_NAME"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmOriGroup(){
	}
	public FrmOriGroup(OriGroup oriGroup){
		this.oriGroup = oriGroup;
	}

	public FrmOriGroup(HttpServletRequest request, OriGroup oriGroup){
		super(new FrmOriGroup(oriGroup), request);
		this.oriGroup = oriGroup;
	}

	public String getFormName() { return FRM_NAME_ORIGROUP; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public OriGroup getEntityObject(){ return oriGroup; }

	public void requestEntityObject(OriGroup oriGroup) {
		try{
			this.requestParam();
			oriGroup.setGroupName(getString(FRM_FIELD_GROUP_NAME));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
