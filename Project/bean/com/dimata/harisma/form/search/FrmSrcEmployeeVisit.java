/* 
 * Form Name  	:  FrmSrcEmployeeVisit.java 
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

public class FrmSrcEmployeeVisit extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcEmployeeVisit srcEmployeeVisit;

	public static final String FRM_NAME_SRCEMPLOYEEVISIT		=  "FRM_NAME_SRCEMPLOYEEVISIT" ;

	public static final int FRM_FIELD_EMPLOYEE_NAME			=  0 ;
	public static final int FRM_FIELD_DEPARTMENT			=  1 ;
	public static final int FRM_FIELD_VISIT_DATE_FROM			=  2 ;
	public static final int FRM_FIELD_VISIT_DATE_TO			=  3 ;
    public static final int FRM_FIELD_SORT_BY			=  4 ;

	public static String[] fieldNames = {
		"FRM_FIELD_EMPLOYEE_NAME",  "FRM_FIELD_DEPARTMENT",
		"FRM_FIELD_VISIT_DATE_FROM",  "FRM_FIELD_VISIT_DATE_TO",
        "FRM_FIELD_SORT_BY"
	} ;

	public static int[] fieldTypes = {
		TYPE_STRING,  TYPE_LONG,
		TYPE_DATE,  TYPE_DATE,
        TYPE_INT
	} ;

    public static final int ORDER_VISITING_DATE 	= 0;
    public static final int ORDER_EMPLOYEE_NAME 	= 1;
    public static final int ORDER_VISITOR 		= 2;

    public static final String[] orderkey={"Visit Date", "Employee ","Visitor"};

    public static Vector getOrderKey(){
    	Vector result = new Vector(1,1);
        for(int i=0;i<orderkey.length;i++){
        	result.add(orderkey[i]);
        }
        return result;
    }

	public FrmSrcEmployeeVisit(){
	}

	public FrmSrcEmployeeVisit(SrcEmployeeVisit srcEmployeeVisit){
		this.srcEmployeeVisit = srcEmployeeVisit;
	}

	public FrmSrcEmployeeVisit(HttpServletRequest request, SrcEmployeeVisit srcEmployeeVisit){
		super(new FrmSrcEmployeeVisit(srcEmployeeVisit), request);
		this.srcEmployeeVisit = srcEmployeeVisit;
	}

	public String getFormName() { return FRM_NAME_SRCEMPLOYEEVISIT; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcEmployeeVisit getEntityObject(){ return srcEmployeeVisit; }

	public void requestEntityObject(SrcEmployeeVisit srcEmployeeVisit) {
		try{
			this.requestParam();
			srcEmployeeVisit.setEmployeeName(getString(FRM_FIELD_EMPLOYEE_NAME));
			srcEmployeeVisit.setDepartment(getLong(FRM_FIELD_DEPARTMENT));
			srcEmployeeVisit.setVisitDateFrom(getDate(FRM_FIELD_VISIT_DATE_FROM));
			srcEmployeeVisit.setVisitDateTo(getDate(FRM_FIELD_VISIT_DATE_TO));
            srcEmployeeVisit.setSortBy(getInt(FRM_FIELD_SORT_BY));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
