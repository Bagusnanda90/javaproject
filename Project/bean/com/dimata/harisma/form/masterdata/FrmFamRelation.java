/* Created on 	:  30 September 2011 [time] AM/PM
 *
 * @author	 : Ari_20110930
 * @version	 : -
 */

/*******************************************************************
 * Class Description 	: FrmFamRelation
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

public class FrmFamRelation extends FRMHandler implements I_FRMInterface, I_FRMType{
    private FamRelation famRelation;

	public static final String FRM_NAME_FAM_RELATION		=  "FRM_NAME_FAM_RELATION" ;

	public static final int FRM_FIELD_FAMILY_RELATION_ID			=  0 ;
	public static final int FRM_FIELD_FAMILY_RELATION                  =  1 ;
        public static final int FRM_FIELD_FAMILY_RELATION_TYPE               = 2;

	public static String[] fieldNames = {
		"FRM_FIELD_FAMILY_RELATION_ID",
                "FRM_FIELD_FAMILY_RELATION",
                "FRM_FIELD_FAMILY_RELATION_TYPE"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED,
                TYPE_INT
	} ;

	public FrmFamRelation(){
	}
	public FrmFamRelation(FamRelation famRelation){
		this.famRelation = famRelation;
	}

	public FrmFamRelation(HttpServletRequest request, FamRelation famRelation){
		super(new FrmFamRelation(famRelation), request);
		this.famRelation = famRelation;
	}

	public String getFormName() { return FRM_NAME_FAM_RELATION; }

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; }

	public int getFieldSize() { return fieldNames.length; }

	public FamRelation getEntityObject(){ return famRelation; }

	public void requestEntityObject(FamRelation famRelation) {
		try{
			this.requestParam();
			famRelation.setFamRelation(getString(FRM_FIELD_FAMILY_RELATION));
                        famRelation.setFamRelationType(getInt(FRM_FIELD_FAMILY_RELATION_TYPE));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}

}
