/* 
 * Form Name  	:  FrmSrcEmpSchedule.java 
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

public class FrmSrcEmpSchedule extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcEmpSchedule srcEmpSchedule;

    public static final String FRM_NAME_SRCEMPSCHEDULE = "FRM_NAME_SRCEMPSCHEDULE";
    public static final int FRM_FIELD_PERIOD = 0;
    public static final int FRM_FIELD_EMPLOYEE = 1;
    public static final int FRM_FIELD_SCHEDULE = 2;
    public static final int FRM_FIELD_COMPANY = 3; // Update by Hendra Putu | 20150217
    public static final int FRM_FIELD_DIVISION = 4; // Update by Hendra Putu | 20150217
    public static final int FRM_FIELD_DEPARTMENT = 5;
    public static final int FRM_FIELD_SECTION = 6;
    public static final int FRM_FIELD_POSITION = 7;
    public static final int FRM_FIELD_EMPNUMBER = 8;
        //update by satrya 2013-12-06
    public static final int FRM_FIELD_SORT_BY = 9;
    public static final int FRM_FIELD_EMP_CATEGORY = 10; // Update by Hendra Putu | 20150217
    public static final int FRM_FIELD_RESIGNED = 11; // Update by Hendra Putu | 20150217
	public static String[] fieldNames = {
		"FRM_FIELD_PERIOD",  
                "FRM_FIELD_EMPLOYEE",
		"FRM_FIELD_SCHEDULE",
                "FRM_FIELD_COMPANY",
                "FRM_FIELD_DIVISION",
                "FRM_FIELD_DEPARTMENT",
                "FRM_FIELD_SECTION", 
                "FRM_FIELD_POSITION", 
                "FRM_FIELD_EMPNUMBER",
                //update by satrya 2013-12-06
                "FRM_FIELD_SORT_BY",
                "FRM_FIELD_EMP_CATEGORY",
                "FRM_FIELD_RESIGNED"
	} ;

	public static int[] fieldTypes = {
		TYPE_STRING,  
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_STRING,
		TYPE_STRING,  
                TYPE_STRING, 
                TYPE_STRING,
                //update by satrya 2013-12-06
                TYPE_INT,
                TYPE_STRING,
                TYPE_INT
	} ;
         
        //update by satrya 2013-12-06
       public static final int SORT_BY_NAME =1;
       public static final int SORT_BY_EMP_NUMBER=2;
        //update by satrya 2013-12-06
       public static String[] sortByKey ={
           " Select "," Name "," Employee Number"
       };
       public static final int[] sortByValue = {0, 1,2};
  //update by satrya 2013-12-06
    public static Vector getOrderValue() {
        Vector order = new Vector();
        for (int i = 0; i < sortByValue.length; i++) {
            order.add(String.valueOf(sortByValue[i]));
        }
        return order;
    }
  //update by satrya 2013-12-06
    public static Vector getOrderKey() {
        Vector order = new Vector();
        for (int i = 0; i < sortByKey.length; i++) {
            order.add(sortByKey[i]);
        }
        return order;
    }

	public FrmSrcEmpSchedule(){
	}
	public FrmSrcEmpSchedule(SrcEmpSchedule srcEmpSchedule){
		this.srcEmpSchedule = srcEmpSchedule;
	}

	public FrmSrcEmpSchedule(HttpServletRequest request, SrcEmpSchedule srcEmpSchedule){
		super(new FrmSrcEmpSchedule(srcEmpSchedule), request);
		this.srcEmpSchedule = srcEmpSchedule;
	}

	public String getFormName() { return FRM_NAME_SRCEMPSCHEDULE; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcEmpSchedule getEntityObject(){ return srcEmpSchedule; }

	public void requestEntityObject(SrcEmpSchedule srcEmpSchedule) {
		try{
			this.requestParam();
			srcEmpSchedule.setPeriod(getString(FRM_FIELD_PERIOD));
			srcEmpSchedule.setEmployee(getString(FRM_FIELD_EMPLOYEE));
			srcEmpSchedule.setSchedule(getString(FRM_FIELD_SCHEDULE));
                        srcEmpSchedule.setCompany(getString(FRM_FIELD_COMPANY)); // add 20150217
                        srcEmpSchedule.setDivision(getString(FRM_FIELD_DIVISION)); // add 20150217
                        srcEmpSchedule.setDepartment(getString(FRM_FIELD_DEPARTMENT));
                        srcEmpSchedule.setSection(getString(FRM_FIELD_SECTION));
                        srcEmpSchedule.setPosition(getString(FRM_FIELD_POSITION));
                        srcEmpSchedule.setEmpNumber(getString(FRM_FIELD_EMPNUMBER));
                        //update by satrya 2013-12-06
                        srcEmpSchedule.setSortBy(getInt(FRM_FIELD_SORT_BY));
                        srcEmpSchedule.setEmpCategory(getString(FRM_FIELD_EMP_CATEGORY));
                        srcEmpSchedule.setResigned(getInt(FRM_FIELD_RESIGNED));
                        
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
