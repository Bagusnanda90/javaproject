package com.dimata.harisma.form.employee;

/* java package */ 
import java.io.*; 
import java.util.*; 
import java.sql.Date;
import javax.servlet.*;
import javax.servlet.http.*; 

/* qdep package */ 
import com.dimata.qdep.form.*;

/* project package */
import com.dimata.harisma.entity.employee.*;

/**
 *
 * @author bayu
 */

public class FrmEmpAward extends FRMHandler implements I_FRMInterface, I_FRMType {

    private EmpAward award;
    
    public static final String FRM_NAME_EMP_AWARD	=  "FRM_NAME_EMP_AWARD" ;
    
    public static final int FRM_FIELD_EMPLOYEE_ID	=  0 ;
    public static final int FRM_FIELD_DEPARTMENT_ID     =  1 ;
    public static final int FRM_FIELD_SECTION_ID	=  2 ;
    public static final int FRM_FIELD_AWARD_DATE   	=  3 ;
    public static final int FRM_FIELD_AWARD_TYPE        =  4 ;
    public static final int FRM_FIELD_AWARD_DESC        =  5 ;
    public static final int FRM_FIELD_PROVIDER_ID = 6;
    public static final int FRM_FIELD_TITLE = 7;
    public static final int FRM_FIELD_DIVISION_ID = 8;
    public static final int FRM_FIELD_COMPANY_ID = 9;
    public static final int FRM_FIELD_DOCUMENT = 10;
    public static final int FRM_FIELD_AWARD_ID = 11;
    public static final int FRM_FIELD_DOC_ID = 12;
    public static final int FRM_FIELD_ACKNOWLEDGE_STATUS = 13;
    public static final int FRM_FIELD_EXTERNAL_FROM = 14;
    public static String[] fieldNames = {
        "EMPLOYEE_ID",
        "DEPARTMENT_ID",
        "SECTION_ID",
        "AWARD_DATE",
        "AWARD_TYPE",
        "AWARD_DESC",
        "PROVIDER_ID",
        "TITLE",
        "DIVISION_ID",
        "COMPANY_ID",
        "DOCUMENT",
        "AWARD_ID",
        "DOC_ID",
        "ACKNOWLEDGE_STATUS",
        "EXTERNAL_FROM"
    };
    
    public static int[] fieldTypes = {
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_LONG, /* ENTRY_REQUIRED has been deleted */
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_STRING + ENTRY_REQUIRED,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_STRING
    };
    
        
    public FrmEmpAward(EmpAward award) {
        this.award = award;
    }
    
    public FrmEmpAward(HttpServletRequest request, EmpAward award) {
        super(new FrmEmpAward(award), request);
        this.award = award;
    }
    
      
    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getFormName() {
        return FRM_NAME_EMP_AWARD;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public EmpAward getEntityObject() {
        return award;
    }
    
    public void requestEntityObject(EmpAward award) {
        try {
            this.requestParam();
            
            award.setEmployeeId(this.getLong(FRM_FIELD_EMPLOYEE_ID));
            award.setDepartmentId(this.getLong(FRM_FIELD_DEPARTMENT_ID));
            award.setSectionId(this.getLong(FRM_FIELD_SECTION_ID));
            award.setAwardDate(Date.valueOf(this.getString(FRM_FIELD_AWARD_DATE)));
            award.setAwardType(this.getLong(FRM_FIELD_AWARD_TYPE));
            award.setAwardDescription(this.getString(FRM_FIELD_AWARD_DESC));
            award.setProviderId(this.getLong(FRM_FIELD_PROVIDER_ID));
            award.setTitle(this.getString(FRM_FIELD_TITLE));
            award.setDivisionId(this.getLong(FRM_FIELD_DIVISION_ID));
            award.setCompanyId(this.getLong(FRM_FIELD_COMPANY_ID));
            award.setDocument(this.getString(FRM_FIELD_DOCUMENT));
            award.setDocId(this.getLong(FRM_FIELD_DOC_ID));
            award.setAcknowledgeStatus(this.getInt(FRM_FIELD_ACKNOWLEDGE_STATUS));
            award.setExternalFrom(this.getString(FRM_FIELD_EXTERNAL_FROM));
        }
        catch(Exception e) {            
        }
    }
        
}
