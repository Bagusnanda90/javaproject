/* 
 * Form Name  	:  FrmSrcEmpSalary.java 
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
import com.dimata.harisma.entity.employee.*;

public class FrmSrcEmpSalary extends FRMHandler implements I_FRMInterface, I_FRMType 
{
	private SrcEmpSalary srcEmpSalary;

	public static final String FRM_NAME_SRCEMPSALARY		=  "FRM_NAME_SRCEMPSALARY" ;

	public static final int FRM_FIELD_NAME			=  0 ;
	public static final int FRM_FIELD_EMPNUMBER			=  1 ;
	public static final int FRM_FIELD_POSITION			=  2 ;
	public static final int FRM_FIELD_LEVEL			=  3 ;
	public static final int FRM_FIELD_CURRTOTALFROM			=  4 ;
	public static final int FRM_FIELD_CURRTOTALTO			=  5 ;
	public static final int FRM_FIELD_NEWTOTALFROM			=  6 ;
	public static final int FRM_FIELD_NEWTOTALTO			=  7 ;
    public static final int FRM_FIELD_COMMDATEFROM			=  8 ;
	public static final int FRM_FIELD_COMMDATETO			=  9 ;
	public static final int FRM_FIELD_ORDER                 =  10;
	public static final int FRM_FIELD_DEPARTMENT			=  11 ;

	public static String[] fieldNames = {
		"FRM_FIELD_NAME",  "FRM_FIELD_EMPNUMBER",
		"FRM_FIELD_POSITION",  "FRM_FIELD_LEVEL",
		"FRM_FIELD_CURRTOTALFROM",  "FRM_FIELD_CURRTOTALTO",
		"FRM_FIELD_NEWTOTALFROM",  "FRM_FIELD_NEWTOTALTO",
        "FRM_FIELD_COMMDATEFROM", "FRM_FIELD_COMMDATETO",
        "FRM_FIELD_ORDER", "FRM_FIELD_DEPARTMENT"
	} ;

	public static int[] fieldTypes = {
		TYPE_STRING,  TYPE_STRING,
		TYPE_LONG,  TYPE_LONG,
		TYPE_FLOAT,  TYPE_FLOAT,
		TYPE_FLOAT,  TYPE_FLOAT,
        TYPE_DATE, TYPE_DATE,
        TYPE_INT, TYPE_LONG
	} ;


    public static final int ORDER_EMPLOYEE_NAME 	= 0;
    public static final int ORDER_EMPLOYEE_NUMBER 	= 1;
    public static final int ORDER_POSITION 		= 2;
    public static final int ORDER_LEVEL 		= 3;
    public static final int ORDER_COMM_DATE 		= 4;

    public static final int[] orderValue = {0,1,2,3,4};

    public static final String[] orderKey = {"Name", "Payroll Number","Position","Level","Commencing Date"};

    public static Vector getOrderValue(){
    	Vector result = new Vector(1,1);
        for(int i = 0;i<orderValue.length;i++){
        	result.add(String.valueOf(orderValue[i]));
        }
        return result;
    }

    public static Vector getOrderKey(){
    	Vector result = new Vector(1,1);
        for(int i = 0;i<orderKey.length;i++){
        	result.add(orderKey[i]);
        }
        return result;
    }


	public FrmSrcEmpSalary(){
	}
	public FrmSrcEmpSalary(SrcEmpSalary srcEmpSalary){
		this.srcEmpSalary = srcEmpSalary;
	}

	public FrmSrcEmpSalary(HttpServletRequest request, SrcEmpSalary srcEmpSalary){
		super(new FrmSrcEmpSalary(srcEmpSalary), request);
		this.srcEmpSalary = srcEmpSalary;
	}

	public String getFormName() { return FRM_NAME_SRCEMPSALARY; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SrcEmpSalary getEntityObject(){ return srcEmpSalary; }

	public void requestEntityObject(SrcEmpSalary srcEmpSalary) {
		try{
			this.requestParam();
			srcEmpSalary.setName(getString(FRM_FIELD_NAME));
			srcEmpSalary.setEmpnumber(getString(FRM_FIELD_EMPNUMBER));
			srcEmpSalary.setPosition(getLong(FRM_FIELD_POSITION));
			srcEmpSalary.setDepartment(getLong(FRM_FIELD_DEPARTMENT));
			srcEmpSalary.setLevel(getLong(FRM_FIELD_LEVEL));
			srcEmpSalary.setCurrtotalfrom(getDouble(FRM_FIELD_CURRTOTALFROM));
			srcEmpSalary.setCurrtotalto(getDouble(FRM_FIELD_CURRTOTALTO));
			srcEmpSalary.setNewtotalfrom(getDouble(FRM_FIELD_NEWTOTALFROM));
			srcEmpSalary.setNewtotalto(getDouble(FRM_FIELD_NEWTOTALTO));
            srcEmpSalary.setCommDateFrom(getDate(FRM_FIELD_COMMDATEFROM));
			srcEmpSalary.setCommDateTo(getDate(FRM_FIELD_COMMDATETO));
            srcEmpSalary.setOrderBy(getInt(FRM_FIELD_ORDER));
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
}
