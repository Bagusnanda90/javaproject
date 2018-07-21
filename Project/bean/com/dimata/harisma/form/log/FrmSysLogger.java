package com.dimata.harisma.form.log;

/* java package */ 
import java.io.*; 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 

/* qdep package */ 
import com.dimata.qdep.form.*;

/* project package */
import com.dimata.harisma.entity.log.*;


public class FrmSysLogger extends FRMHandler implements I_FRMInterface, I_FRMType 
{	
	public static final String FRM_NAME_SYS_LOGGER      =   "FORM_SYSTEM_LOGGER" ;

	public static final int FRM_FIELD_LOG_ID            =   0;
	public static final int FRM_FIELD_LOG_DATE          =   1;
	public static final int FRM_FIELD_LOG_SYSMODE       =   2;
	public static final int FRM_FIELD_LOG_CATEGORY      =   3;
        public static final int FRM_FIELD_LOG_NOTE          =   4;

	public static String[] fieldNames = {
		"FRM_FIELD_LOG_ID",  
                "FRM_FIELD_LOG_DATE",
		"FRM_FIELD_LOG_SYSMODE",
                "FRM_FIELD_LOG_CATEGORY",
                "FRM_FIELD_LOG_NOTE"
	} ;

	public static int[] fieldTypes = {
		TYPE_LONG,  
                TYPE_DATE,
		TYPE_STRING, 
                TYPE_INT,
                TYPE_STRING
	} ;
        
        private SysLogger logger;

        
	public FrmSysLogger(){
	}
	public FrmSysLogger(SysLogger logger){
            this.logger = logger;
	}

	public FrmSysLogger(HttpServletRequest request, SysLogger logger){
            super(new FrmSysLogger(logger), request);
            this.logger = logger;
	}

	public String getFormName() { return FRM_NAME_SYS_LOGGER; } 

	public int[] getFieldTypes() { return fieldTypes; }

	public String[] getFieldNames() { return fieldNames; } 

	public int getFieldSize() { return fieldNames.length; } 

	public SysLogger getEntityObject(){ return logger; }

	public void requestEntityObject(SysLogger logger) {
            try{
                this.requestParam();
                logger.setLogDate(this.getDate(FRM_FIELD_LOG_DATE));
                logger.setLogNote(this.getString(FRM_FIELD_LOG_NOTE));
                logger.setLogSysMode(this.getString(FRM_FIELD_LOG_SYSMODE));
                logger.setLogCategory(this.getInt(FRM_FIELD_LOG_CATEGORY));
            }
            catch(Exception e){
                System.out.println("Error on requestEntityObject : "+e.toString());
            }
	}
}
