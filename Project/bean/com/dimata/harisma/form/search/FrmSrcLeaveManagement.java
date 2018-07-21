/*
 * FrmSrcLeaveManagement.java
 *
 * Created on September 8, 2004, 10:25 AM
 */

package com.dimata.harisma.form.search;

// import core java package
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 

// import qdep package 
import com.dimata.qdep.form.*;

// import project package
import com.dimata.harisma.entity.search.*;

/**
 *
 * @author  gedhy
 */
public class FrmSrcLeaveManagement extends FRMHandler implements I_FRMInterface, I_FRMType 
{
    
    private SrcLeaveManagement srcLeaveManagement;
    
    public static final String FRM_NAME_SRCDAYOFPAYMENT		=  "FRM_NAME_SRC_LEAVE_DP";
    
    public static final int FRM_FIELD_EMP_NUMBER		=  0 ;
    public static final int FRM_FIELD_FULL_NAME			=  1 ;
    public static final int FRM_FIELD_CATEGORY  		=  2 ;    
    public static final int FRM_FIELD_DEPARTMENT		=  3 ;
    public static final int FRM_FIELD_SECTION			=  4 ;
    public static final int FRM_FIELD_POSITION			=  5 ;
    public static final int FRM_FIELD_PERIOD			=  6 ;
    public static final int FRM_FIELD_PERIOD_CHECKED		=  7 ;
    public static final int FRM_FIELD_LEVEL                     =  8 ;
    public static final int FRM_FIELD_PERIOD_MAN                =  9 ; //add bya artha    
    public static final int FRM_FIELD_START_DATE                =  10; //add by Roy Andika
    public static final int FRM_FIELD_END_DATE                  =  11; 
    public static final int FRM_FIELD_TIME                      =  12;
    public static final int FRM_FIELD_EMPLOYEE_ID               =  13;
    //update by devin 2014-03-28
     public static final int FRM_FIELD_COMPANY               =  14;
     public static final int FRM_FIELD_DIVISION               =  15;
    public static String[] fieldNames = 
    {
        "FRM_FIELD_EMP_NUMBER",  
        "FRM_FIELD_FULL_NAME",
        "FRM_FIELD_CATEGORY",  
        "FRM_FIELD_DEPARTMENT",  
        "FRM_FIELD_SECTION",
        "FRM_FIELD_POSITION",
        "FRM_FIELD_PERIOD",
        "FRM_FIELD_PERIOD_CHECKED",
        "FRM_FIELD_LEVEL",
        "FRM_FIELD_PERIOD_MAN",
        "FRM_FIELD_START_DATE",        
        "FRM_FIELD_END_DATE",        
        "FRM_FIELD_TIME",
        "FRM_FIELD_EMPLOYEE_ID",
         //update by devin 2014-03-28
        "FRM_FIELD_COMPANY",
        "FRM_FIELD_DIVISION"
    } ;
    
    public static int[] fieldTypes = 
    {
        TYPE_STRING,  
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,  
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_INT,
        TYPE_LONG,
         //update by devin 2014-03-28
         TYPE_LONG,
        TYPE_LONG
    } ;
    
    public FrmSrcLeaveManagement()
    {
    }
    
    public FrmSrcLeaveManagement(SrcLeaveManagement srcLeaveManagement)  
    {
        this.srcLeaveManagement = srcLeaveManagement;
    }
    
    public FrmSrcLeaveManagement(HttpServletRequest request, SrcLeaveManagement srcLeaveManagement)
    {
        super(new FrmSrcLeaveManagement(srcLeaveManagement), request);
        this.srcLeaveManagement = srcLeaveManagement;
    }
    
    public String getFormName() 
    { 
        return FRM_NAME_SRCDAYOFPAYMENT; 
    }
    
    public int[] getFieldTypes() 
    { 
        return fieldTypes; 
    }
    
    public String[] getFieldNames() 
    { 
        return fieldNames; 
    }
    
    public int getFieldSize() 
    { 
        return fieldNames.length; 
    }
    
    public SrcLeaveManagement getEntityObject()
    { 
        return srcLeaveManagement; 
    }
    
    public void requestEntityObject(SrcLeaveManagement srcLeaveManagement) 
    {
        try
        {
            this.requestParam();
            srcLeaveManagement.setEmpNum(getString(FRM_FIELD_EMP_NUMBER));
            srcLeaveManagement.setEmpName(getString(FRM_FIELD_FULL_NAME));
            srcLeaveManagement.setEmpCatId(getLong(FRM_FIELD_CATEGORY));
            srcLeaveManagement.setEmpDeptId(getLong(FRM_FIELD_DEPARTMENT));  
            srcLeaveManagement.setEmpSectionId(getLong(FRM_FIELD_SECTION));
            srcLeaveManagement.setEmpPosId(getLong(FRM_FIELD_POSITION));
            srcLeaveManagement.setLeavePeriod(getDate(FRM_FIELD_PERIOD));
            srcLeaveManagement.setPeriodChecked(getBoolean(FRM_FIELD_PERIOD_CHECKED));
            srcLeaveManagement.setEmpLevelId(getLong(FRM_FIELD_LEVEL));
            srcLeaveManagement.setPeriodId(getLong(FRM_FIELD_PERIOD_MAN));
            srcLeaveManagement.setStartDate(getDate(FRM_FIELD_START_DATE));
            srcLeaveManagement.setEndDate(getDate(FRM_FIELD_END_DATE));
            srcLeaveManagement.setTime(getInt(FRM_FIELD_TIME));
            srcLeaveManagement.setEmployee_id(getLong(FRM_FIELD_EMPLOYEE_ID));
              //update by devin 2014-03-28
             srcLeaveManagement.setCompanyId(getLong(FRM_FIELD_COMPANY));
              srcLeaveManagement.setDivisionId(getLong(FRM_FIELD_DIVISION));
        }
        catch(Exception e)
        {
            System.out.println("Error on requestEntityObject : "+e.toString());
        }
    }
    
}
