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

/**
 *
 * @author bayu
 */

public class FrmSrcSysLogger extends FRMHandler implements I_FRMInterface, I_FRMType 
{
    
    public static final String FRM_NAME_SRC_SYS_LOG     =   "FORM_SRC_SYS_LOG" ;

    public static final int FRM_FIELD_LOG_DATE_START    =   0;
    public static final int FRM_FIELD_LOG_DATE_END      =   1;
    public static final int FRM_FIELD_LOG_SYSMODE       =   2;
    public static final int FRM_FIELD_LOG_CATEGORY      =   3;

    public static String[] fieldNames = {
            "FRM_FIELD_LOG_DATE_START",  
            "FRM_FIELD_LOG_DATE_END",
            "FRM_FIELD_LOG_SYSMODE",
            "FRM_FIELD_LOG_CATEGORY",
    } ;

    public static int[] fieldTypes = {
            TYPE_DATE,  
            TYPE_DATE,
            TYPE_STRING, 
            TYPE_INT,
    } ;
    
    private SrcSysLogger logger;
    
    
    public FrmSrcSysLogger() {
    }
    
    public FrmSrcSysLogger(SrcSysLogger logger){
        this.logger = logger;
    }

    public FrmSrcSysLogger(HttpServletRequest request, SrcSysLogger logger){
        super(new FrmSrcSysLogger(logger), request);
        this.logger = logger;
    }

    
    
    public String getFormName() { return FRM_NAME_SRC_SYS_LOG; } 

    public int[] getFieldTypes() { return fieldTypes; }

    public String[] getFieldNames() { return fieldNames; } 

    public int getFieldSize() { return fieldNames.length; } 

    public SrcSysLogger getEntityObject(){ return logger; }

    public void requestEntityObject(SrcSysLogger logger) {
        try{
            this.requestParam();
            logger.setLogDateStart(this.getDate(FRM_FIELD_LOG_DATE_START));
            logger.setLogDateEnd(this.getDate(FRM_FIELD_LOG_DATE_END));
            logger.setLogSysMode(this.getString(FRM_FIELD_LOG_SYSMODE));
            logger.setLogCategory(this.getInt(FRM_FIELD_LOG_CATEGORY));
        }
        catch(Exception e){
            System.out.println("Error on requestEntityObject : "+e.toString());
        }
    }
    
}
