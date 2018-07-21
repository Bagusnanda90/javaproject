/*
 * FrmSection.java
 *
 * Created on January 5, 2005, 1:42 PM
 */

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

/**
 *
 * @author  gedhy
 */
public class FrmSrcSection  extends FRMHandler implements I_FRMInterface, I_FRMType {
    
	private SrcSection srcSection;

	public static final String FRM_NAME_SRC_SECTION		=  "FRM_NAME_SRC_SECTION" ;

	public static final int FRM_FIELD_DEPARTMENT			=  0 ;
	public static final int FRM_FIELD_NAME			=  1 ;

	public static String[] fieldNames = {
		"FRM_FIELD_DEPARTMENT",  
		"FRM_FIELD_NAME"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,
		TYPE_STRING
	} ;

	public FrmSrcSection(){
	}
	public FrmSrcSection(SrcSection srcSection){
		this.srcSection = srcSection;
	}

	public FrmSrcSection(HttpServletRequest request, SrcSection srcSection){
		super(new FrmSrcSection(srcSection), request);
		this.srcSection = srcSection;
	}

	public String getFormName() { return FRM_NAME_SRC_SECTION; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcSection getEntityObject(){ return srcSection; }

	public void requestEntityObject(SrcSection srcSection) {
		try{
			this.requestParam();
			srcSection.setSecDepartment(getLong(FRM_FIELD_DEPARTMENT));
			srcSection.setSecName(getString(FRM_FIELD_NAME));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
