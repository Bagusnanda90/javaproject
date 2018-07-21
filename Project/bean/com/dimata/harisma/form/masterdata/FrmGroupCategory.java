/* 
 * Form Name  	:  FrmGroupCategory.java 
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

public class FrmGroupCategory extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private GroupCategory groupCategory;

	public static final String FRM_NAME_GROUPCATEGORY		=  "FRM_NAME_GROUPCATEGORY" ;

	public static final int FRM_FIELD_GROUP_CATEGORY_ID			=  0 ;
	public static final int FRM_FIELD_GROUP_RANK_ID			=  1 ;
	public static final int FRM_FIELD_GROUP_NAME			=  2 ;

	public static String[] fieldNames = {
		"FRM_FIELD_GROUP_CATEGORY_ID",  "FRM_FIELD_GROUP_RANK_ID",
		"FRM_FIELD_GROUP_NAME"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED
	} ;

	public FrmGroupCategory(){
	}
	public FrmGroupCategory(GroupCategory groupCategory){
		this.groupCategory = groupCategory;
	}

	public FrmGroupCategory(HttpServletRequest request, GroupCategory groupCategory){
		super(new FrmGroupCategory(groupCategory), request);
		this.groupCategory = groupCategory;
	}

	public String getFormName() { return FRM_NAME_GROUPCATEGORY; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public GroupCategory getEntityObject(){ return groupCategory; }

	public void requestEntityObject(GroupCategory groupCategory) {
		try{
			this.requestParam();
			groupCategory.setGroupRankId(getLong(FRM_FIELD_GROUP_RANK_ID));
			groupCategory.setGroupName(getString(FRM_FIELD_GROUP_NAME));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
