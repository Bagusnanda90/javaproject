/*
 * FrmQuery.java
 *
 * Created on August 3, 2007, 2:52 PM
 */

package com.dimata.harisma.form.payroll;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.payroll.*;


/**
 *
 * @author  yunny
 */
public class FrmQuery extends FRMHandler implements I_FRMInterface, I_FRMType  {
     private Query query;

	public static final String FRM_HR_METADATA=  "FRM_HR_METADATA" ;

	public static final int FRM_FIELD_QUERY_ID			=  0 ;
	public static final int FRM_FIELD_REPORT_TITLE=  1 ;
	public static final int FRM_FIELD_REPORT_SUBTITLE=  2 ;
	public static final int FRM_FIELD_WHERE_PARAM=  3 ;
	public static final int FRM_FIELD_ORDER_BY_PARAM=  4 ;
	public static final int FRM_FIELD_GROUP_BY_PARAM=  5 ;
	public static final int FRM_FIELD_QUERY=  6 ;
        public static final int FRM_FIELD_SUB_QUERY=  7 ;
	public static final int FRM_FIELD_DATE=  8 ;
	public static final int FRM_FIELD_DESCRIPTION=  9 ;
	
        
         public static String[] fieldNames = {
	"FRM_FIELD_QUERY_ID",
        "FRM_FIELD_REPORT_TITLE",
	"FRM_FIELD_REPORT_SUBTITLE",
        "FRM_FIELD_WHERE_PARAM",
	"FRM_FIELD_ORDER_BY_PARAM",
        "FRM_FIELD_GROUP_BY_PARAM",
	"FRM_FIELD_QUERY",
        "FRM_FIELD_SUB_QUERY",
        "FRM_FIELD_DATE",
	"FRM_FIELD_DESCRIPTION",
        };
         
         public static int[] fieldTypes = {
	TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED,
	TYPE_STRING,
        TYPE_STRING,
	TYPE_STRING,
        TYPE_STRING,
	TYPE_STRING + ENTRY_REQUIRED,
        TYPE_STRING,
        TYPE_DATE,
	TYPE_STRING,
        };
        
    /** Creates a new instance of FrmQuery */
    public FrmQuery() {
    }
    public FrmQuery(Query query){
		this.query= query;
   }
   public FrmQuery(HttpServletRequest request, Query query){
		super(new FrmQuery(query), request);
		this.query= query;
   }
   
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int getFieldSize() {
        return fieldNames.length;
    }
    
    public int[] getFieldTypes() {
         return fieldTypes;
    }
    
    public String getFormName() {
        return FRM_HR_METADATA;
    }
    
    public Query getEntityObject(){ return query; }
    
    public void requestEntityObject(Query query) {
		try{
			this.requestParam();
                        //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                        query.setReportTitle(getString(FRM_FIELD_REPORT_TITLE));
                        query.setReportSubtitle(getString(FRM_FIELD_REPORT_SUBTITLE));
                        query.setWhereParam(getString(FRM_FIELD_WHERE_PARAM));
                        query.setOrderByParam(getString(FRM_FIELD_ORDER_BY_PARAM));
                        query.setGroupByParam(getString(FRM_FIELD_GROUP_BY_PARAM));
                        query.setQuery(getString(FRM_FIELD_QUERY));
                        query.setSubQuery(getString(FRM_FIELD_SUB_QUERY));
                        query.setDate(getDate(FRM_FIELD_DATE));
                        query.setDescription(getString(FRM_FIELD_DESCRIPTION));
                        
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
    
}
