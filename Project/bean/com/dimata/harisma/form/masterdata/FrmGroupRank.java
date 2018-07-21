/* 
 * Form Name  	:  FrmGroupRank.java 
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

public class FrmGroupRank extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private GroupRank groupRank;

	public static final String FRM_NAME_GROUPRANK		=  "FRM_NAME_GROUPRANK" ;

	public static final int FRM_FIELD_GROUP_RANK_ID			=  0 ;
	public static final int FRM_FIELD_GROUP_NAME			=  1 ;
	public static final int FRM_FIELD_DESCRIPTION			=  2 ;

	public static String[] fieldNames = {
		"FRM_FIELD_GROUP_RANK_ID",  "FRM_FIELD_GROUP_NAME",
		"FRM_FIELD_DESCRIPTION"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING
	} ;

	public FrmGroupRank(){
	}
	public FrmGroupRank(GroupRank groupRank){
		this.groupRank = groupRank;
	}

	public FrmGroupRank(HttpServletRequest request, GroupRank groupRank){
		super(new FrmGroupRank(groupRank), request);
		this.groupRank = groupRank;
	}

	public String getFormName() { return FRM_NAME_GROUPRANK; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public GroupRank getEntityObject(){ return groupRank; }

	public void requestEntityObject(GroupRank groupRank) {
		try{
			this.requestParam();
			groupRank.setGroupName(getString(FRM_FIELD_GROUP_NAME));
			groupRank.setDescription(getString(FRM_FIELD_DESCRIPTION));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
