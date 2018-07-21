/* 
 * Form Name  	:  FrmMarital.java 
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

public class FrmMarital extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private Marital marital;
        public static final String FRM_NAME_MARITAL = "FRM_NAME_MARITAL";
        public static final int FRM_FIELD_MARITAL_ID = 0;
        public static final int FRM_FIELD_MARITAL_STATUS = 1;
        public static final int FRM_FIELD_MARITAL_CODE = 2;
        public static final int FRM_FIELD_NUM_OF_CHILDREN = 3;
        public static final int FRM_FIELD_MARITAL_STATUS_TAX = 4;

	public static String[] fieldNames = {
		"FRM_FIELD_MARITAL_ID",  "FRM_FIELD_MARITAL_STATUS",
		"FRM_FIELD_MARITAL_CODE",  "FRM_FIELD_NUM_OF_CHILDREN", "FRM_FIELD_MARITAL_STATUS_TAX"
	};

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING + ENTRY_REQUIRED,
		TYPE_STRING + ENTRY_REQUIRED,  TYPE_INT, TYPE_INT
	};

	public FrmMarital(){
	}
	public FrmMarital(Marital marital){
		this.marital = marital;
	}

	public FrmMarital(HttpServletRequest request, Marital marital){
		super(new FrmMarital(marital), request);
		this.marital = marital;
	}

	public String getFormName() { return FRM_NAME_MARITAL; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public Marital getEntityObject(){ return marital; }

	public void requestEntityObject(Marital marital) {
            try{
                    this.requestParam();
                    marital.setMaritalStatus(getString(FRM_FIELD_MARITAL_STATUS));
                    marital.setMaritalCode(getString(FRM_FIELD_MARITAL_CODE));
                    marital.setNumOfChildren(getInt(FRM_FIELD_NUM_OF_CHILDREN));
                    marital.setMaritalStatusTax(getInt(FRM_FIELD_MARITAL_STATUS_TAX));
                    
            }catch(Exception e){
                    System.out.println("Error on requestEntityObject : "+e.toString());
            }
	}
}
