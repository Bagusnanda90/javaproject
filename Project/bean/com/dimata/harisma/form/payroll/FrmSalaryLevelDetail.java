/*
 * FrmSalaryLevelDetail.java
 *
 * Created on March 31, 2007, 1:44 PM
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
 * @author  autami
 */
public class FrmSalaryLevelDetail extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private SalaryLevelDetail salaryLevelDetail;
    
    public static final String FRM_PAY_LEVEL_COMP =  "FRM_PAY_LEVEL_COMP" ;
    
    public static final int FRM_FIELD_PAY_LEVEL_COM_ID		=  0 ;
    public static final int FRM_FIELD_LEVEL_CODE		=  1 ;
    public static final int FRM_FIELD_COMP_CODE			=  2 ;
    public static final int FRM_FIELD_FORMULA			=  3 ;
    public static final int FRM_FIELD_SORT_IDX			=  4 ;
    public static final int FRM_FIELD_PAY_PERIOD		=  5 ;
    public static final int FRM_FIELD_TAKE_HOME_PAY		=  6 ;
    public static final int FRM_FIELD_COPY_DATA			=  7 ;
    public static final int FRM_FIELD_COMPONENT_ID		=  8 ;
    
    
    public static String[] fieldNames = {
        "FRM_FIELD_PAY_LEVEL_COM_ID",
	"FRM_FIELD_LEVEL_CODE",
        "FRM_FIELD_COMP_CODE",
	"FRM_FIELD_FORMULA",
        "FRM_FIELD_SORT_IDX",
        "FRM_FIELD_PAY_PERIOD",
        "FRM_FIELD_TAKE_HOME_PAY",
        "FRM_FIELD_COPY_DATA",
        "FRM_FIELD_COMPONENT_ID"
    };
    
    public static int[] fieldTypes = {
	TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
	TYPE_STRING,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT, 
        TYPE_LONG, 
    };
    
    /** Creates a new instance of FrmSalaryLevelDetail */
    public FrmSalaryLevelDetail() {
    }
    
    public FrmSalaryLevelDetail(SalaryLevelDetail salaryLevelDetail){
		this.salaryLevelDetail = salaryLevelDetail;
    }
    
    public FrmSalaryLevelDetail(HttpServletRequest request, SalaryLevelDetail salaryLevelDetail){
		super(new FrmSalaryLevelDetail(salaryLevelDetail), request);
		this.salaryLevelDetail = salaryLevelDetail;
        
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
        return FRM_PAY_LEVEL_COMP;
    }
    
    public SalaryLevelDetail getEntityObject(){ return salaryLevelDetail; }
    
    public void requestEntityObject(SalaryLevelDetail salaryLevelDetail) {
        try{
                this.requestParam();
                
                salaryLevelDetail.setLevelCode(getString(FRM_FIELD_LEVEL_CODE));
                salaryLevelDetail.setCompCode(getString(FRM_FIELD_COMP_CODE));
                salaryLevelDetail.setFormula(getString(FRM_FIELD_FORMULA));
                //update by satrya 2014-04-03
                String replaca =salaryLevelDetail.getFormula()==null?"":salaryLevelDetail.getFormula().replace("&gt", ">");
                if(replaca!=null && replaca.length()>0){
                    String replacasLebihKecil =replaca.replace("&lt;", "<");
                    salaryLevelDetail.setFormula(replacasLebihKecil); 
                }
                
                salaryLevelDetail.setSortIdx(getInt(FRM_FIELD_SORT_IDX));
                salaryLevelDetail.setPayPeriod(getInt(FRM_FIELD_PAY_PERIOD));
                salaryLevelDetail.setTakeHomePay(getInt(FRM_FIELD_TAKE_HOME_PAY));
                salaryLevelDetail.setCopyData(getInt(FRM_FIELD_COPY_DATA));  
                salaryLevelDetail.setComponentId(getLong(FRM_FIELD_COMPONENT_ID)); 
                
        }catch(Exception e){
                System.out.println("Error on requestEntityObject : "+e.toString());
        }
    }
    
}
