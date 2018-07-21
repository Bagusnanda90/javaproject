/*
 * FrmEmpPicture.java
 *
 * Created on November 30, 2007, 11:29 AM
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.harisma.form.employee;

/**
 *
 * @author  yunny
 */
/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* qdep package */ 
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.employee.*;


public class FrmEmpPicture extends FRMHandler implements I_FRMInterface, I_FRMType {
   
    private EmpPicture empPicture;
    
    public static final String FRM_EMP_PICTURE		=  "FRM_EMP_PICTURE" ;
    
    public static final int FRM_FIELD_PIC_EMP_ID			=  0 ;
    public static final int FRM_FIELD_EMPLOYEE_ID			=  1 ;
    public static final int FRM_FIELD_PIC			=  2 ;
    
    
    public static String[] fieldNames = {
		"FRM_FIELD_PIC_EMP_ID",  "FRM_FIELD_EMPLOYEE_ID",
		"FRM_FIELD_PIC"
	} ;
        
    public static int[] fieldTypes = {
		TYPE_LONG,  TYPE_LONG + ENTRY_REQUIRED,
		TYPE_LONG,  
		TYPE_STRING
    } ;
    
    /** Creates a new instance of FrmEmpPicture */
    public FrmEmpPicture() {
        
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
        return FRM_EMP_PICTURE;
    }
    
    public FrmEmpPicture(EmpPicture empPicture){
		this.empPicture = empPicture;
	}

	public FrmEmpPicture(HttpServletRequest request, EmpPicture empPicture){
		super(new FrmEmpPicture(empPicture), request);
		this.empPicture = empPicture;
	}

    
    public EmpPicture getEntityObject(){ return empPicture; }
    
    public void requestEntityObject(EmpPicture empPicture) {
		try{
			this.requestParam();
			empPicture.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
			empPicture.setPic(getString(FRM_FIELD_PIC));
			
		}catch(Exception e){
			System.out.println("Error on requestEntityObject : "+e.toString());
		}
	}
    
}
