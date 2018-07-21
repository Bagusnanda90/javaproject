/*
 * FrmSrcDPExpiration.java
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
public class FrmSrcDPExpiration extends FRMHandler implements I_FRMInterface, I_FRMType 
{
    
    private com.dimata.harisma.entity.search.SrcDPExpiration  srcDPExpiration; 
    
    public static final String FRM_NAME_SRCDAYOFPAYMENT		=  "FRM_NAME_SRC_DP_EXPIRATION";
    
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
    public static final int FRM_FIELD_DP_EXP_PERIOD             = 10 ;
    public static final int FRM_FIELD_START_DATE                = 11 ;
    
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
        "FRM_FIELD_DP_EXP_PERIOD",
        "FRM_FIELD_START_DATE"
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
        TYPE_INT,
        TYPE_DATE
    } ;
    
    public FrmSrcDPExpiration()
    {
    }
    
    public FrmSrcDPExpiration(com.dimata.harisma.entity.search.SrcDPExpiration srcDPExpiration)
    {
        this.srcDPExpiration = srcDPExpiration;
    }
    
    public FrmSrcDPExpiration(HttpServletRequest request, com.dimata.harisma.entity.search.SrcDPExpiration srcDPExpiration) 
    {
        super(new FrmSrcDPExpiration(srcDPExpiration), request);
        this.srcDPExpiration = srcDPExpiration;
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
    
    public com.dimata.harisma.entity.search.SrcDPExpiration getEntityObject()
    { 
        return srcDPExpiration; 
    }
    
    public void requestEntityObject(com.dimata.harisma.entity.search.SrcDPExpiration srcDPExpiration) 
    {
        try
        {
            this.requestParam();
            srcDPExpiration.setEmpNum(getString(FRM_FIELD_EMP_NUMBER));
            srcDPExpiration.setEmpName(getString(FRM_FIELD_FULL_NAME));
            srcDPExpiration.setEmpCatId(getLong(FRM_FIELD_CATEGORY));
            srcDPExpiration.setEmpDeptId(getLong(FRM_FIELD_DEPARTMENT));  
            srcDPExpiration.setEmpSectionId(getLong(FRM_FIELD_SECTION));
            srcDPExpiration.setEmpPosId(getLong(FRM_FIELD_POSITION));
            srcDPExpiration.setLeavePeriod(getDate(FRM_FIELD_PERIOD));
            srcDPExpiration.setPeriodChecked(getBoolean(FRM_FIELD_PERIOD_CHECKED));
            srcDPExpiration.setEmpLevelId(getLong(FRM_FIELD_LEVEL));
            srcDPExpiration.setPeriodId(getLong(FRM_FIELD_PERIOD_MAN));
            srcDPExpiration.setExpirationPeriod(getInt(FRM_FIELD_DP_EXP_PERIOD));
            srcDPExpiration.setStartDate(getDate(FRM_FIELD_START_DATE));
        }
        catch(Exception e)
        {
            System.out.println("Error on requestEntityObject : "+e.toString());
        }
    }
    
}
