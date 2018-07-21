/* 
 * Form Name  	:  FrmSrcOriChecklist.java 
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

public class FrmSrcOriChecklist extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcOriChecklist srcOriChecklist;

	public static final String FRM_NAME_SRCORICHECKLIST		=  "FRM_NAME_SRCORICHECKLIST" ;

	public static final int FRM_FIELD_NAME			=  0 ;
	public static final int FRM_FIELD_DEPARTMENT			=  1 ;
	//public static final int FRM_FIELD_SECTION			=  2 ;
	public static final int FRM_FIELD_POSITION			=  2 ;
	public static final int FRM_FIELD_COMMDATESTART			=  3 ;
	public static final int FRM_FIELD_COMMDATEEND			=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_NAME",  "FRM_FIELD_DEPARTMENT",
		//"FRM_FIELD_SECTION",  
                "FRM_FIELD_POSITION",
		"FRM_FIELD_COMMDATESTART",  "FRM_FIELD_COMMDATEEND"
	} ;

	public static int[] fieldTypes = {
		TYPE_STRING,  TYPE_LONG,
		//TYPE_STRING,  
                TYPE_LONG,
		TYPE_DATE,  TYPE_DATE
	} ;

	public FrmSrcOriChecklist(){
	}
	public FrmSrcOriChecklist(SrcOriChecklist srcOriChecklist){
		this.srcOriChecklist = srcOriChecklist;
	}

	public FrmSrcOriChecklist(HttpServletRequest request, SrcOriChecklist srcOriChecklist){
		super(new FrmSrcOriChecklist(srcOriChecklist), request);
		this.srcOriChecklist = srcOriChecklist;
	}

	public String getFormName() { return FRM_NAME_SRCORICHECKLIST; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcOriChecklist getEntityObject(){ return srcOriChecklist; }

	public void requestEntityObject(SrcOriChecklist srcOriChecklist) {
		try{
			this.requestParam();
			srcOriChecklist.setName(getString(FRM_FIELD_NAME));
			srcOriChecklist.setDepartment(getLong(FRM_FIELD_DEPARTMENT));
			//srcOriChecklist.setSection(getString(FRM_FIELD_SECTION));
			srcOriChecklist.setPosition(getLong(FRM_FIELD_POSITION));
			srcOriChecklist.setCommdatestart(getDate(FRM_FIELD_COMMDATESTART));
			srcOriChecklist.setCommdateend(getDate(FRM_FIELD_COMMDATEEND));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
