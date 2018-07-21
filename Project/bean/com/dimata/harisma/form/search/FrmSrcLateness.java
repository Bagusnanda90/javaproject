/**
 * Created by IntelliJ IDEA.
 * User: gadnyana
 * Date: Apr 7, 2004
 * Time: 3:41:30 PM
 * To change this template use Options | File Templates.
 */
package com.dimata.harisma.form.search;

import com.dimata.harisma.entity.search.SrcAppraisal;
import com.dimata.harisma.entity.search.SrcLateness;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;

import javax.servlet.http.HttpServletRequest;
import java.util.Vector;

public class FrmSrcLateness extends FRMHandler implements I_FRMInterface, I_FRMType {

	private SrcLateness srcLateness;

	public static final String FRM_NAME_LATENESS		=  "FRM_NAME_LATENESS" ;

	public static final int FRM_FIELD_DATELATENESS			=  0 ;
	public static final int FRM_FIELD_DEPARTMENTID			=  1 ;

	public static String[] fieldNames = {
		"FRM_FIELD_DATELATENESS",  "FRM_FIELD_DEPARTMENTID"
	} ;

	public static int[] fieldTypes = {
		TYPE_DATE,  TYPE_LONG
	} ;

	public FrmSrcLateness(){
	}
	public FrmSrcLateness(SrcLateness srcLateness){
		this.srcLateness = srcLateness;
	}

	public FrmSrcLateness(HttpServletRequest request, SrcLateness srcLateness){
		super(new FrmSrcLateness(srcLateness), request);
		this.srcLateness = srcLateness;
	}

	public String getFormName() { return FRM_NAME_LATENESS; }

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; }

	public int getFieldSize() { return fieldNames.length; }

	public SrcLateness getEntityObject(){ return srcLateness; }

	public void requestEntityObject(SrcLateness srcLateness) {
		try{
			this.requestParam();
			srcLateness.setDepartmentId(getLong(FRM_FIELD_DEPARTMENTID));
			srcLateness.setDtLateness(getDate(FRM_FIELD_DATELATENESS));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
