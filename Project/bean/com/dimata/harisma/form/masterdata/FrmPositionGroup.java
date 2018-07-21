/* 
 * Form Name  	:  FrmPositionGroup.java 
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

public class FrmPositionGroup extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private PositionGroup positionGroup;

	public static final String FRM_NAME_POSITION_GROUP		=  "FRM_NAME_POSITION_GROUP" ;

	public static final int FRM_FIELD_POSITION_GROUP_ID			=  0 ;
	public static final int FRM_FIELD_POSITION_GROUP_NAME			=  1 ;
	public static final int FRM_FIELD_DESCRIPTION                           =  2 ;

	public static String[] fieldNames = {
		"FRM_FIELD_POSITION_GROUP_ID",  
                "FRM_FIELD_POSITION_GROUP_NAME",
		"FRM_FIELD_DESCRIPTION"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  
                TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING
	} ;

	public FrmPositionGroup(){
	}
	public FrmPositionGroup(PositionGroup positionGroup){
		this.positionGroup = positionGroup;
	}

	public FrmPositionGroup(HttpServletRequest request, PositionGroup positionGroup){
		super(new FrmPositionGroup(positionGroup), request);
		this.positionGroup = positionGroup;
	}

	public String getFormName() { return FRM_NAME_POSITION_GROUP; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public PositionGroup getEntityObject(){ return positionGroup; }

	public void requestEntityObject(PositionGroup positionGroup) {
		try{
			this.requestParam();
			positionGroup.setPositionGroupName(getString(FRM_FIELD_POSITION_GROUP_NAME));
			positionGroup.setDescription(getString(FRM_FIELD_DESCRIPTION));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
        
       
}
