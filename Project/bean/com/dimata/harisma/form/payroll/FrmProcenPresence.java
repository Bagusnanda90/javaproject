/*
 * FrmTaxType.java
 *
 * Created on March 30, 2007, 6:01 PM
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
 * @author  emerliana
 */
public class FrmProcenPresence extends FRMHandler implements I_FRMInterface, I_FRMType {
    
        private ProcenPresence procenPresence;
        public static final String FRM_PROCEN_PRESENCE =  "FRM_PROCEN_PRESENCE" ;

	public static final int FRM_FIELD_PROCEN_PRESENCE_ID			=  0 ;
	public static final int FRM_FIELD_PROCEN_PRESENCE			=  1 ;
	public static final int FRM_FIELD_ABSENCE_DAY			=  2 ;
	
        
        public static String[] fieldNames = {
	"FRM_FIELD_PROCEN_PRESENCE_ID",
        "FRM_FIELD_PROCEN_PRESENCE",
	"FRM_FIELD_ABSENCE_DAY"
        } ;
        
        
       public static int[] fieldTypes = {
	TYPE_LONG,
        TYPE_FLOAT + ENTRY_REQUIRED,
        TYPE_INT + ENTRY_REQUIRED
	} ;
    /** Creates a new instance of FrmTaxType */
    public FrmProcenPresence() {
    }
    
     public FrmProcenPresence(ProcenPresence procenPresence){
		this.procenPresence = procenPresence;
     }
    
     public FrmProcenPresence(HttpServletRequest request, ProcenPresence procenPresence){
		super(new FrmProcenPresence(procenPresence), request);
		this.procenPresence = procenPresence;
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
        return FRM_PROCEN_PRESENCE;
    }
    
    public ProcenPresence getEntityObject(){ return procenPresence; }
    
     public void requestEntityObject(ProcenPresence procenPresence) {
        try{
                this.requestParam();
                //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
                procenPresence.setProcenPresence(getDouble(FRM_FIELD_PROCEN_PRESENCE));
                procenPresence.setAbsenceDay(getInt(FRM_FIELD_ABSENCE_DAY));
        }catch(Exception e){
                System.out.println("Error on requestEntityObject : "+e.toString());
        }
    }
    
}
