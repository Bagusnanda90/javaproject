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
 * @author guest
 */

public class FrmSrcEndTraining extends FRMHandler implements I_FRMInterface, I_FRMType {
    
    private SrcEndTraining srcEndTraining;
    
    public static final String FRM_NAME_SRC_END_TRAINING  	=  "FRM_NAME_SRC_END_TRAINING";
    
    public static final int FRM_FIELD_END_PERIOD                =  0 ;
    public static final int FRM_FIELD_DEPARTMENT		=  1 ;
    public static final int FRM_FIELD_SECTION                   =  2 ;    
    public static final int FRM_FIELD_SORT_FIELD		=  3 ;
   
    
    public static String[] fieldNames = 
    {
        "FRM_FIELD_END_PERIOD",  
        "FRM_FIELD_DEPARTMENT",
        "FRM_FIELD_SECTION",  
        "FRM_FIELD_SORT_FIELD"
    } ;
    
    public static int[] fieldTypes = 
    {
        TYPE_INT,  
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING
    } ;
    
    public FrmSrcEndTraining()
    {
    }
    
    public FrmSrcEndTraining(SrcEndTraining srcEndTraining)  
    {
        this.srcEndTraining = srcEndTraining;
    }
    
    public FrmSrcEndTraining(HttpServletRequest request, SrcEndTraining srcEndTraining)
    {
        super(new FrmSrcEndTraining(srcEndTraining), request);
        this.srcEndTraining = srcEndTraining;
    }
    
    public String getFormName() 
    { 
        return FRM_NAME_SRC_END_TRAINING; 
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
    
    public SrcEndTraining getEntityObject()
    { 
        return srcEndTraining; 
    }
    
    public void requestEntityObject(SrcEndTraining srcEndTraining) 
    {
        try
        {
            this.requestParam();
            srcEndTraining.setEndPeriod(getInt(FRM_FIELD_END_PERIOD));
            srcEndTraining.setDepartmentId(getLong(FRM_FIELD_DEPARTMENT));
            srcEndTraining.setSectionId(getLong(FRM_FIELD_SECTION));
            srcEndTraining.setSortField(getString(FRM_FIELD_SORT_FIELD));
        }
        catch(Exception e)
        {
            System.out.println("Error on requestEntityObject : "+e.toString());
        }
    }
    
}
