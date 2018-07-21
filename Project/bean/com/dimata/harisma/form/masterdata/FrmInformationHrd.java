/* 
 * Form Name  	:  FrmInformationHrd.java
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
import com.dimata.harisma.form.masterdata.*;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import com.dimata.harisma.entity.masterdata.InformationHrd;

import javax.servlet.http.HttpServletRequest;

public class FrmInformationHrd extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private InformationHrd informationHrd;

	public static final String FRM_NAME_INFORMATION		=  "FRM_NAME_INFORMATION" ;

	public static final int FRM_FIELD_INFORMATION_HRD_ID			=  0 ;
        public static final int FRM_FIELD_NAMA_INFORMATION			=  1 ;
	public static final int FRM_FIELD_DATE_START			=  2 ;
	public static final int FRM_FIELD_DATE_END			=  3 ;

	public static String[] fieldNames = {
		"FRM_FIELD_INFORMATION_HRD_ID",  "FRM_FIELD_NAMA_INFORMATION",
		"FRM_FIELD_DATE_START","FRM_FIELD_DATE_END"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_STRING,
		TYPE_DATE+ENTRY_REQUIRED,TYPE_DATE+ENTRY_REQUIRED,
	} ;

	public FrmInformationHrd(){
	}
	public FrmInformationHrd(InformationHrd informationHrd){
		this.informationHrd = informationHrd;
	}

	public FrmInformationHrd(HttpServletRequest request, InformationHrd informationHrd){
		super(new FrmInformationHrd(informationHrd), request);
		this.informationHrd = informationHrd;
	}

	public String getFormName() { return FRM_NAME_INFORMATION; }

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public InformationHrd getEntityObject(){ return informationHrd; }

	public void requestEntityObject(InformationHrd informationHrd) {
		try{
			this.requestParam();
			informationHrd.setNamaInformation(getString(FRM_FIELD_NAMA_INFORMATION));
			informationHrd.setDtStartInfo(getDate(FRM_FIELD_DATE_START));
                        informationHrd.setDtEndInfo(getDate(FRM_FIELD_DATE_END));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
