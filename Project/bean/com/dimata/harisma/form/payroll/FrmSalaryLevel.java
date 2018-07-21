/*
 * FrmSalaryLevel.java
 *
 * Created on April 3, 2007, 2:32 AM
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
 * @author  Ana
 */
public class FrmSalaryLevel extends FRMHandler implements I_FRMInterface, I_FRMType {
    
        private SalaryLevel salaryLevel;
        public static final String FRM_SALARY_LEVEL =  "FRM_SALARY_LEVEL" ;

	public static final int FRM_FIELD_SAL_LEVEL_ID 		=  0 ;
	public static final int FRM_FIELD_LEVEL_CODE		=  1 ;
        public static final int FRM_FIELD_SORT_IDX		=  2 ;
        public static final int FRM_FIELD_LEVEL_NAME		=  3 ;
        public static final int FRM_FIELD_AMOUNT_IS		=  4 ;
	public static final int FRM_FIELD_CUR_CODE		=  5 ;
        public static final int FRM_FIELD_SALARY_LEVEL_STATUS = 6;
        public static final int FRM_FIELD_LEVEL_ASSIGN = 7;
        public static final int FRM_FIELD_SALARY_LEVEL_NOTE = 8;
       
        public static String[] fieldNames = {
	"FRM_FIELD_SAL_LEVEL_ID ",
        "FRM_FIELD_LEVEL_CODE",
	"FRM_FIELD_SORT_IDX",
        "FRM_FIELD_LEVEL_NAME",
        "FRM_FIELD_AMOUNT_IS",
        "FRM_FIELD_CUR_CODE",
        "FRM_FIELD_SALARY_LEVEL_STATUS",
        "FRM_FIELD_LEVEL_ASSIGN",
        "FRM_FIELD_SALARY_LEVEL_NOTE"
        } ;
        
        
       public static int[] fieldTypes = {
	TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_INT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG,
        TYPE_STRING
	} ;
    /** Creates a new instance of FrmSalaryLevel */
    public FrmSalaryLevel() {
    }
    
     public FrmSalaryLevel(SalaryLevel salaryLevel){
	this.salaryLevel = salaryLevel;
     }
     
     public FrmSalaryLevel(HttpServletRequest request, SalaryLevel salaryLevel){
		super(new FrmSalaryLevel(salaryLevel), request);
		this.salaryLevel = salaryLevel;
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
         return FRM_SALARY_LEVEL;
    }
    
    public SalaryLevel getEntityObject(){ return salaryLevel; }
    
    public void requestEntityObject(SalaryLevel salaryLevel) {
        try{
                this.requestParam();
                //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                salaryLevel.setLevelCode(getString(FRM_FIELD_LEVEL_CODE));
                salaryLevel.setSort_Idx(getInt(FRM_FIELD_SORT_IDX));
                salaryLevel.setLevelName(getString(FRM_FIELD_LEVEL_NAME));
                salaryLevel.setAmountIs(getInt(FRM_FIELD_AMOUNT_IS));
                salaryLevel.setCur_Code(getString(FRM_FIELD_CUR_CODE));
                salaryLevel.setSalaryLevelStatus(getInt(FRM_FIELD_SALARY_LEVEL_STATUS));
                salaryLevel.setLevelAssign(getLong(FRM_FIELD_LEVEL_ASSIGN));
                salaryLevel.setSalaryLevelNote(getString(FRM_FIELD_SALARY_LEVEL_NOTE));
        }catch(Exception e){
                System.out.println("Error on requestEntityObject : "+e.toString());
        }
    }
    
    
}
