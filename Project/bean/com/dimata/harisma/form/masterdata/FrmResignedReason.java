/* 
 * Form Name  	:  FrmResignedReason.java 
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

public class FrmResignedReason extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private ResignedReason resignedReason;

	public static final String FRM_NAME_RESIGNEDREASON		=  "FRM_NAME_RESIGNEDREASON" ;

	public static final int FRM_FIELD_RESIGNED_REASON_ID			=  0 ;
	public static final int FRM_FIELD_RESIGNED_REASON			=  1 ;
        public static final int FRM_FIELD_RESIGNED_CODE			=  2 ;

	public static String[] fieldNames = {
		"FRM_FIELD_RESIGNED_REASON_ID",  "FRM_FIELD_RESIGNED_REASON",
                "FRM_FIELD_RESIGNED_CODE"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED,
                TYPE_STRING
	} ;

	public FrmResignedReason(){
	}
	public FrmResignedReason(ResignedReason resignedReason){
		this.resignedReason = resignedReason;
	}

	public FrmResignedReason(HttpServletRequest request, ResignedReason resignedReason){
		super(new FrmResignedReason(resignedReason), request);
		this.resignedReason = resignedReason;
	}

	public String getFormName() { return FRM_NAME_RESIGNEDREASON; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public ResignedReason getEntityObject(){ return resignedReason; }

	public void requestEntityObject(ResignedReason resignedReason) {
		try{
			this.requestParam();
			resignedReason.setResignedReason(getString(FRM_FIELD_RESIGNED_REASON));
                        resignedReason.setResignedCode(getString(FRM_FIELD_RESIGNED_CODE));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
