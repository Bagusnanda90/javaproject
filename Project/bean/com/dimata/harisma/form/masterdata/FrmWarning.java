/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author	 : Ari_20110930
 * @version	 : -
 */

/*******************************************************************
 * Class Description 	: FrmWarning
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.form.masterdata;

/**
 *
 * @author Wiweka
 */
/* java package */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;

public class FrmWarning extends FRMHandler implements I_FRMInterface, I_FRMType{
    private Warning warning;

	public static final String FRM_NAME_WARNING		=  "FRM_NAME_WARNING" ;

	public static final int FRM_FIELD_WARN_ID			=  0 ;
	public static final int FRM_FIELD_WARN_DESC                 =  1 ;
        public static final int FRM_FIELD_WARN_POINT               = 2;

	public static String[] fieldNames = {
		"FRM_FIELD_WARN_ID",
                "FRM_FIELD_WARN_DESC",
                "FRM_FIELD_WARN_POINT"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED,
                TYPE_INT + ENTRY_REQUIRED
	} ;

	public FrmWarning(){
	}
	public FrmWarning(Warning warning){
		this.warning = warning;
	}

	public FrmWarning(HttpServletRequest request, Warning warning){
		super(new FrmWarning(warning), request);
		this.warning = warning;
	}

	public String getFormName() { return FRM_NAME_WARNING; }

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; }

	public int getFieldSize() { return fieldNames.length; }

	public Warning getEntityObject(){ return warning; }

	public void requestEntityObject(Warning warning) {
		try{
			this.requestParam();
			warning.setWarnDesc(getString(FRM_FIELD_WARN_DESC));
                        warning.setWarnPoint(getInt(FRM_FIELD_WARN_POINT));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}

}
