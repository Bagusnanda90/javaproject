
package com.dimata.harisma.form.attendance;

// import core java package
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

// import qdep package
import com.dimata.qdep.form.*;

// import project package
import com.dimata.harisma.entity.attendance.*;

/**
 *
 * @author bayu
 */

public class FrmSpecialLeave extends FRMHandler implements I_FRMInterface, I_FRMType {

    private SpecialLeave specialLeave;
    
    public static final String FRM_SPECIAL_LEAVE =  "FRM_SPECIAL_LEAVE" ;
    
    public static final int FRM_FIELD_SPECIAL_LEAVE_ID  =  0 ;    
    public static final int FRM_FIELD_EMPLOYEE_ID       =  1 ;
    public static final int FRM_FIELD_REQUEST_DATE      =  2 ;
    public static final int FRM_FIELD_UNPAID_REASON     =  3 ;
    public static final int FRM_FIELD_REMARK            =  4 ;
    public static final int FRM_FIELD_APPROVAL_ID       =  5 ;
    public static final int FRM_FIELD_APPROVAL2_ID      =  6 ;
    public static final int FRM_FIELD_APPROVAL3_ID      =  7 ;
    public static final int FRM_FIELD_APPROVAL_DATE     =  8 ;
    public static final int FRM_FIELD_APPROVAL2_DATE    =  9 ;
    public static final int FRM_FIELD_APPROVAL3_DATE    =  10 ;
    
    public static String[] fieldNames = {
        "FRM_FIELD_SPECIAL_LEAVE_ID",  
        "FRM_FIELD_EMPLOYEE_ID",  
        "FRM_FIELD_REQUEST_DATE",
        "FRM_FIELD_UNPAID_REASON",
        "FRM_FIELD_REMARK",
        "FRM_FIELD_APPROVAL_ID",
        "FRM_FIELD_APPROVAL2_ID",
        "FRM_FIELD_APPROVAL3_ID",
        "FRM_FIELD_APPROVAL_DATE",
        "FRM_FIELD_APPROVAL2_DATE",
        "FRM_FIELD_APPROVAL3_DATE"
    } ;
    
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,  
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_DATE
    } ;
    
    
    /** Creates a new instance of FrmSpecialLeaveStock */
    public FrmSpecialLeave() {
    }
    
    public FrmSpecialLeave(SpecialLeave specialLeave){
        this.specialLeave = specialLeave;
    }
    
     public FrmSpecialLeave(HttpServletRequest request, SpecialLeave specialLeave){
        super(new FrmSpecialLeave(specialLeave), request);
        this.specialLeave = specialLeave;
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
       return FRM_SPECIAL_LEAVE;
    }
     public SpecialLeave getEntityObject(){ return specialLeave; }
     
     public void requestEntityObject(SpecialLeave specialLeave) 
    {
        try
        {
            this.requestParam();
            specialLeave.setOID(getLong(FRM_FIELD_SPECIAL_LEAVE_ID));   
            specialLeave.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            specialLeave.setRequestDate(getDate(FRM_FIELD_REQUEST_DATE));
            specialLeave.setUnpaidLeaveReason(getString(FRM_FIELD_UNPAID_REASON));
            specialLeave.setOtherRemarks(getString(FRM_FIELD_REMARK));
            specialLeave.setApprovalId(getLong(FRM_FIELD_APPROVAL_ID));
            specialLeave.setApproval2Id(getLong(FRM_FIELD_APPROVAL2_ID));
            specialLeave.setApproval3Id(getLong(FRM_FIELD_APPROVAL3_ID));
            specialLeave.setApprovalDate(getDate(FRM_FIELD_APPROVAL_DATE));
            specialLeave.setApproval2Date(getDate(FRM_FIELD_APPROVAL2_DATE));
            specialLeave.setApproval3Date(getDate(FRM_FIELD_APPROVAL3_DATE));
        }
        catch(Exception e)
        {
            System.out.println("Error on requestEntityObject : "+e.toString());
        }
    }
    
}
