/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.Reason;
import com.dimata.harisma.form.payroll.CtrlPayInput;
import com.dimata.harisma.form.payroll.FrmPayInput;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.db.I_DBInterface;
import com.dimata.qdep.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Satrya Ramayu
 */
public class PstPayInput extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 
      public static final  String TBL_PAY_INPUT = "pay_input";//"PAY_GENERAL";

	public static final  int FLD_PAY_INPUT_ID = 0;
	public static final  int FLD_PAY_SLIP_ID = 1;
	public static final  int FLD_PAY_INPUT_CODE= 2;
	public static final  int FLD_PAY_INPUT_VALUE= 3;
        
        public static final  int FLD_PERIODE_ID= 4;
	public static final  int FLD_EMPLOYEE_ID= 5;
        public static final int FLD_DATE_ADJUMSENT =6;
        
	
   
    
        public static final  String[] fieldNames = {
		"PAY_INPUT_ID",
		"PAY_SLIP_ID",
		"PAY_INPUT_CODE",
		"PAY_INPUT_VALUE",
                "PERIODE_ID",
                "EMPLOYEE_ID",
                "DATE_ADJUMSENT"
	 };
         
         public static final  int[] fieldTypes = {
		TYPE_LONG + TYPE_PK + TYPE_ID,
		TYPE_LONG,
                TYPE_STRING,
                TYPE_FLOAT,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_DATE
	 };

         
    /** Creates a new instance of PstPayInput */
    public PstPayInput() {
        
    }
    
    public PstPayInput(int i) throws DBException { 
		super(new PstPayInput()); 
	}
    
    public PstPayInput(String sOid) throws DBException { 
		super(new PstPayInput(0)); 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	}
    
    public PstPayInput(long lOid) throws DBException { 
		super(new PstPayInput(0)); 
		String sOid = "0"; 
		try { 
			sOid = String.valueOf(lOid); 
		}catch(Exception e) { 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
		if(!locate(sOid)) 
			throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		else 
			return; 
	} 
    
    public int getFieldSize(){ 
		return fieldNames.length; 
	}
    
    public String getTableName(){ 
		return TBL_PAY_INPUT;
	}
    
    public String[] getFieldNames(){ 
		return fieldNames; 
	}
    
    public int[] getFieldTypes(){ 
		return fieldTypes; 
	}
    
    public String getPersistentName(){ 
		return new PstPayInput().getClass().getName(); 
	}
   
    
    public long deleteExc(Entity ent) throws Exception {
        if(ent==null){ 
		throw new DBException(this,DBException.RECORD_NOT_FOUND); 
		} 
	return deleteExc(ent.getOID());
    }
    
    public long fetchExc(Entity ent) throws Exception{ 
		//PayInput payInput = fetchExc(ent.getOID()); 
		//ent = (Entity)payInput; 
		return 0; 
	}
    
    public long insertExc(Entity ent) throws Exception {
        return 0; 
    }
    
    public long updateExc(Entity ent) throws Exception {
        return 0;
    } 
    
  
   public static long insertExcOrUpdateExc(PayInput payInput,Vector listReason,Vector listPos,Hashtable existPayInputId) throws DBException{ 
		try{ 
                        PstPayInput pstPayGeneral=null;
                        long oidPaySlip = payInput.getPaySlipId();
                        long periodId=payInput.getPeriodId();
                        long oidEmployee = payInput.getEmployeeId();
                        String payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_IDX_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
			//pstPayGeneral.setString(FLD_COMPANY_NAME, payInput.getCompanyName());
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_IDX_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getPresenceOntimeIdxAdjust()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        //pstPayGeneral.insert();
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                         
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getPresenceOntimeTime()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getPresenceOntimeTimeAdjust()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_IDX_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_IDX_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getLateIdxAdjust()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                       if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getLateTime()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getLateTimeAdjust());
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getEarlyHomeIdx());
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                       if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getEarlyHomeIdxAdjust()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getEarlyHomeTime()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getEarlyHomeTimeAdjust());
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        //priska 20150320
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getOnlyInIdx());
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                       if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getOnlyInIdxAdjust()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_TIME];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_TIME]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getOnlyInTime()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_TIME_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_TIME_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getOnlyInTimeAdjust());
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getOnlyOutIdx());
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                       if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getOnlyOutIdxAdjust()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_TIME];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_TIME]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getOnlyOutTime()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_TIME_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_TIME_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getOnlyOutTimeAdjust());
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        //end priska
                        
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getLateEarlyIdx()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getLateEarlyIdxAdjust());
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getLateEarlyTime()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getLateEarlyTimeAdjust());
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        //reason
                        if(listReason!=null && listReason.size()>0){
                            for(int idxRes=0;idxRes<listReason.size();idxRes++){
                                Reason reason = (Reason)listReason.get(idxRes);
                                payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX]+"_"+reason.getNo()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                                if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                                    pstPayGeneral = new PstPayInput(0);
                                }else{
                                    if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                        long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                         pstPayGeneral = new PstPayInput(oidPayslip);
                                    }
                                }
                                pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId());
                                pstPayGeneral.setString(FLD_PAY_INPUT_CODE,FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX]+"_"+reason.getNo()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId());
                                pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getReasonIdx(reason.getNo(),payInput.getPeriodId(),payInput.getEmployeeId())); 
                                pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                                pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                                if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                                    pstPayGeneral.insert();
                                }else{
                                     if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                         pstPayGeneral.update();
                                     }
                                }
                                payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_ADJUSMENT]+"_"+reason.getNo()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                                if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                                    pstPayGeneral = new PstPayInput(0);
                                }else{
                                    if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                        long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                         pstPayGeneral = new PstPayInput(oidPayslip);
                                    }
                                }
                                pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                                pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_ADJUSMENT]+"_"+reason.getNo()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId());
                                pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getReasonIdxAdjust(reason.getNo(),payInput.getPeriodId(),payInput.getEmployeeId()));
                                pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                                pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                               if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                                    pstPayGeneral.insert();
                                 }else{
                                     if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                         pstPayGeneral.update();
                                     }
                                 }
                                

                                payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME]+"_"+reason.getNo()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                                if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                                    pstPayGeneral = new PstPayInput(0);
                                }else{
                                    if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                        long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                         pstPayGeneral = new PstPayInput(oidPayslip);
                                    }
                                }
                                pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                                pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME]+"_"+reason.getNo()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId());
                                pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getReasonTime(reason.getNo(),payInput.getPeriodId(),payInput.getEmployeeId())); 
                                 pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                                pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                                if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                                    pstPayGeneral.insert();
                                 }else{
                                     if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                         pstPayGeneral.update();
                                     }
                                 }
                                payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_ADJUSMENT]+"_"+reason.getNo()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                                if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                                    pstPayGeneral = new PstPayInput(0);
                                }else{
                                    if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                        long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                         pstPayGeneral = new PstPayInput(oidPayslip);
                                    }
                                }
                                pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                                pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_ADJUSMENT]+"_"+reason.getNo()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId());
                                pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getReasonTimeAdjust(reason.getNo(),payInput.getPeriodId(),payInput.getEmployeeId()));
                                 pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                                pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                               if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                            } 
                        }
                         
                        //end reason
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getAbsenceIdx()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                       if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getAbsenceIdxAdjust()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                       if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getAbsenceTime()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                       if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getAbsenceTimeAdjust()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_UNPAID_LEAVE_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_UNPAID_LEAVE_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getUnPaidLeaveAdjust()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        //karena unpaid leave nya sdh ada di payslip
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_INSENTIF_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_INSENTIF_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getInsentifAdjusment());
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                         //karena insentif nya sdh ada di payslip
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_DP];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_DP]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getOtIdxPaidDp()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                       if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                         //karena ot idx paid salary dan adjusment sdh ada  di payslip
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getOtAllowanceFood());
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                       if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_ADJUSMENT];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_ADJUSMENT]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getOtAllowanceFoodAdjust()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        
                        payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EMP_WORK_DAYS];
                        if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                            pstPayGeneral = new PstPayInput(0);
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                 pstPayGeneral = new PstPayInput(oidPayslip);
                            }
                        }
                        pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                        pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EMP_WORK_DAYS]);
                        pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getEmpWorkDays()); 
                        pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                        pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                       if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                           pstPayGeneral.insert();
                        }else{
                            if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                pstPayGeneral.update();
                            }
                        }
                        //untuk position tinggal di buatkan sama dengan reason
                        if(listPos!=null && listPos.size()>0){
                            for(int idxPos=0;idxPos<listPos.size();idxPos++){
                                   Position position = (Position)listPos.get(idxPos);
                                    payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_IDX]+"_"+position.getOID()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                                    if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                                        pstPayGeneral = new PstPayInput(0);
                                    }else{
                                        if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                            long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                             pstPayGeneral = new PstPayInput(oidPayslip);
                                        }
                                    }
                                    pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                                    pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_IDX]+"_"+position.getOID()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId());
                                    pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getPositionIdx(position.getOID(),payInput.getPeriodId(),payInput.getEmployeeId()));
                                    pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                                    pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                                   if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                                        pstPayGeneral.insert();
                                   }else{
                                        if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                            pstPayGeneral.update();
                                        }
                                   }
                                    payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_ADJUSMENT]+"_"+position.getOID()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                                    if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                                        pstPayGeneral = new PstPayInput(0);
                                    }else{
                                        if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                            long oidPayslip = (Long)existPayInputId.get(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee);
                                             pstPayGeneral = new PstPayInput(oidPayslip);
                                        }
                                    }
                                    pstPayGeneral.setLong(FLD_PAY_SLIP_ID, payInput.getPaySlipId()); 
                                    pstPayGeneral.setString(FLD_PAY_INPUT_CODE, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_ADJUSMENT]+"_"+position.getOID()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId());
                                    pstPayGeneral.setDouble(FLD_PAY_INPUT_VALUE, payInput.getPositionAdjust(position.getOID(),payInput.getPeriodId(),payInput.getEmployeeId()));
                                     pstPayGeneral.setLong(FLD_PERIODE_ID, payInput.getPeriodId()); 
                                    pstPayGeneral.setLong(FLD_EMPLOYEE_ID, payInput.getEmployeeId()); 
                                   if((existPayInputId==null) || (existPayInputId.size()==0) || existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)==false){ 
                                        pstPayGeneral.insert();
                                   }else{
                                        if(existPayInputId!=null && existPayInputId.size()>0 && existPayInputId.containsKey(""+oidPaySlip+"_"+payCode+"_"+periodId+"_"+oidEmployee)){
                                            pstPayGeneral.update();
                                   }
                        }
                            }
                        }
			//payInput.setOID(pstPayGeneral.getlong(FLD_GEN_ID));
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayInput(0),DBException.UNKNOWN); 
		}
		return payInput.getOID();
	}
  
    public static long deleteExc(long oid) throws DBException{ 
		try{ 
			PstPayInput pstPayGeneral = new PstPayInput(oid);
			pstPayGeneral.delete();
		}catch(DBException dbe){ 
			throw dbe; 
		}catch(Exception e){ 
			throw new DBException(new PstPayInput(0),DBException.UNKNOWN); 
		}
		return oid;
	}

  /**
   * Create BY satrya 2014-04-27
   * keterangan: pengecekan ID apakah sudah ada Pay Input ID
   * @param limitStart
   * @param recordToGet
   * @param whereClause
   * @param order
   * @return 
   */
   public static Hashtable<String,Long> hashChekcExistingPayInputId(int limitStart,int recordToGet, String whereClause, String order){
		Hashtable lists = new Hashtable(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_PAY_INPUT; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
			dbrs = DBHandler.execQueryResult(sql);
                        //System.out.println("SQL LIST"+sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				//PayInputTabel payInputTabel = new PayInputTabel();
				//resultToObject(rs, payInputTabel);
                             long payInputId=(rs.getLong(PstPayInput.fieldNames[PstPayInput.FLD_PAY_INPUT_ID]));
                             long paySlipId =(rs.getLong(PstPayInput.fieldNames[PstPayInput.FLD_PAY_SLIP_ID]));
                             long employeeId =(rs.getLong(PstPayInput.fieldNames[PstPayInput.FLD_EMPLOYEE_ID]));
                             long periodId =(rs.getLong(PstPayInput.fieldNames[PstPayInput.FLD_PERIODE_ID]));
                             String nameCode = rs.getString(PstPayInput.fieldNames[PstPayInput.FLD_PAY_INPUT_CODE]);
                             //double value = rs.getDouble(PstPayInput.fieldNames[PstPayInput.FLD_PAY_INPUT_VALUE]);
                             lists.put(paySlipId+"_"+nameCode+"_"+periodId+"_"+employeeId,payInputId);
			}
			rs.close();
			

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
                        return lists;
		}
	}
   
    /**
    * create by satrya 2014-04-25
    * Keterangan: untuk melakukan menyimpan tbl di memory berguna di pay input
    * @param limitStart
    * @param recordToGet
    * @param whereClause
    * @param order
    * @return 
    */
   public static Hashtable hashListPayInput(int limitStart,int recordToGet, String whereClause, String order){
		Hashtable lists = new Hashtable(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_PAY_INPUT; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
			dbrs = DBHandler.execQueryResult(sql);
                        //System.out.println("SQL LIST"+sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				//PayInputTabel payInputTabel = new PayInputTabel();
				//resultToObject(rs, payInputTabel);
                             //long payInputId=(rs.getLong(PstPayInput.fieldNames[PstPayInput.FLD_PAY_INPUT_ID]));
                             //long paySlipId =(rs.getLong(PstPayInput.fieldNames[PstPayInput.FLD_PAY_SLIP_ID]));
                             long employeeId =(rs.getLong(PstPayInput.fieldNames[PstPayInput.FLD_EMPLOYEE_ID]));
                             long periodId =(rs.getLong(PstPayInput.fieldNames[PstPayInput.FLD_PERIODE_ID]));
                             String nameCode = rs.getString(PstPayInput.fieldNames[PstPayInput.FLD_PAY_INPUT_CODE]);
                             double value = rs.getDouble(PstPayInput.fieldNames[PstPayInput.FLD_PAY_INPUT_VALUE]);
                             lists.put(nameCode+"_"+periodId+"_"+employeeId,value);
			}
			rs.close();
			

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
                        return lists;
		}
	}
  /**
   * Create by satrya 2014-04-25
   * untuk pay input
   * @param payInput
   * @param listPayInputTbl 
   */ 
   public static void resultToObject(PayInput payInput,Hashtable listPayInputTbl,Vector listReason,Vector listPost){
		try{
			String containsPresenceOntimeIdxAdjust = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_IDX_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsPresenceOntimeIdxAdjust)){
                            double value = (Double)listPayInputTbl.get(containsPresenceOntimeIdxAdjust);
                            payInput.setPresenceOntimeIdxAdjust((int)value);
                            
                        }
                        String containsPresenceOntimeTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsPresenceOntimeTime)){
                            double value = (Double)listPayInputTbl.get(containsPresenceOntimeTime);
                            payInput.setPresenceOntimeTime(value);
                            
                        }
                        String containsPresenceOntimeTimeAdjust = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsPresenceOntimeTimeAdjust)){
                            double value = (Double)listPayInputTbl.get(containsPresenceOntimeTimeAdjust);
                            payInput.setPresenceOntimeTimeAdjust(value);
                            
                        }
                        
                        String containsLateIdxAdjust = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_IDX_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsLateIdxAdjust)){
                            double value = (Double)listPayInputTbl.get(containsLateIdxAdjust);
                            payInput.setLateIdxAdjust((int)value);
                            
                        }
                        
                        String containssetLateTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containssetLateTime)){
                            double value = (Double)listPayInputTbl.get(containssetLateTime);
                            payInput.setLateTime(value);
                            
                        }
                        
                        String containsLateTimeAdjust = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsLateTimeAdjust)){
                            double value = (Double)listPayInputTbl.get(containsLateTimeAdjust);
                            payInput.setLateTimeAdjust(value);
                            
                        }
                        
                        String containsEarlyHomeIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsEarlyHomeIdx)){
                            double value = (Double)listPayInputTbl.get(containsEarlyHomeIdx);
                            payInput.setEarlyHomeIdx((int)value);
                            
                        }
                        
                        String containsEarlyHomeIdxAdjust = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsEarlyHomeIdxAdjust)){
                            double value = (Double)listPayInputTbl.get(containsEarlyHomeIdxAdjust);
                            payInput.setEarlyHomeIdxAdjust((int)value);
                            
                        }
                        
                        String containsEarlyHomeTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsEarlyHomeTime)){
                            double value = (Double)listPayInputTbl.get(containsEarlyHomeTime);
                            payInput.setEarlyHomeTime(value);
                            
                        }
                        
                        String containsEarlyHomeTimeAdjust = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsEarlyHomeTimeAdjust)){
                            double value = (Double)listPayInputTbl.get(containsEarlyHomeTimeAdjust);
                            payInput.setEarlyHomeTimeAdjust(value);
                            
                        }
                        
                        
                        //by priska 20150321
                        String containsOnlyInIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsOnlyInIdx)){
                            double value = (Double)listPayInputTbl.get(containsOnlyInIdx);
                            payInput.setOnlyInIdx((int)value);
                            
                        }
                        
                        String containsOnlyInIdxAdjust = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsOnlyInIdxAdjust)){
                            double value = (Double)listPayInputTbl.get(containsOnlyInIdxAdjust);
                            payInput.setOnlyInIdxAdjust((int)value);
                            
                        }
                        
                        String containsOnlyInTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_TIME]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsOnlyInTime)){
                            double value = (Double)listPayInputTbl.get(containsOnlyInTime);
                            payInput.setOnlyInTime(value);
                            
                        }
                        
                        String containsOnlyInTimeAdjust = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_TIME_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsOnlyInTimeAdjust)){
                            double value = (Double)listPayInputTbl.get(containsOnlyInTimeAdjust);
                            payInput.setOnlyInTimeAdjust(value);
                            
                        }
                        
                        
                        String containsOnlyOutIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsOnlyOutIdx)){
                            double value = (Double)listPayInputTbl.get(containsOnlyOutIdx);
                            payInput.setOnlyOutIdx((int)value);
                            
                        }
                        
                        String containsOnlyOutIdxAdjust = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsOnlyOutIdxAdjust)){
                            double value = (Double)listPayInputTbl.get(containsOnlyOutIdxAdjust);
                            payInput.setOnlyOutIdxAdjust((int)value);
                            
                        }
                        
                        String containsOnlyOutTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_TIME]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsOnlyOutTime)){
                            double value = (Double)listPayInputTbl.get(containsOnlyOutTime);
                            payInput.setOnlyOutTime(value);
                            
                        }
                        
                        String containsOnlyOutTimeAdjust = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_TIME_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsOnlyOutTimeAdjust)){
                            double value = (Double)listPayInputTbl.get(containsOnlyOutTimeAdjust);
                            payInput.setOnlyOutTimeAdjust(value);
                            
                        }
                        
                        
                        //end priska
                        
                        String containsLateEarlyIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsLateEarlyIdx)){
                            double value = (Double)listPayInputTbl.get(containsLateEarlyIdx);
                            payInput.setLateEarlyIdx((int)value);
                            
                        }
                        
                        String containsLateEarlyIdxAdjust = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsLateEarlyIdxAdjust)){
                            double value = (Double)listPayInputTbl.get(containsLateEarlyIdxAdjust);
                            payInput.setLateEarlyIdxAdjust((int)value);
                            
                        }
                        
                        String containsLateEarlyTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsLateEarlyTime)){
                            double value = (Double)listPayInputTbl.get(containsLateEarlyTime);
                            payInput.setLateEarlyTime(value);
                            
                        }
                        
                        String containsLateEarlyTimeAdjust = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsLateEarlyTimeAdjust)){
                            double value = (Double)listPayInputTbl.get(containsLateEarlyTimeAdjust);
                            payInput.setLateEarlyTimeAdjust(value);
                        }
                        if(listReason!=null && listReason.size()>0){
                            for(int idxRes=0;idxRes<listReason.size();idxRes++){
                                    Reason reason = (Reason)listReason.get(idxRes);
                                    //String containsReasonIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX]+"_"+reason.getNo()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                                    String containsReasonIdx =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX]+"_"+reason.getNo()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();//karena di dbtblPayInput harus menambahkan periode dan empID
                                    if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsReasonIdx)){
                                        double value = (Double)listPayInputTbl.get(containsReasonIdx);
                                        payInput.addReasonHashIdx(reason.getNo(), value,payInput.getPeriodId(),payInput.getEmployeeId());
                                    }
                                    String containsReasonIdxAdj =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_ADJUSMENT]+"_"+reason.getNo()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();//karena di dbtblPayInput harus menambahkan periode dan empID
                                    if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsReasonIdxAdj)){
                                        double value = (Double)listPayInputTbl.get(containsReasonIdxAdj);
                                        payInput.addReasonHashIdxAdjust(reason.getNo(), value,payInput.getPeriodId(),payInput.getEmployeeId());
                                    }
                                    
                                    String containsReasonTime =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME]+"_"+reason.getNo()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();//karena di dbtblPayInput harus menambahkan periode dan empID
                                    if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsReasonTime)){
                                        double value = (Double)listPayInputTbl.get(containsReasonTime);
                                        payInput.addReasonHashTime(reason.getNo(), value,payInput.getPeriodId(),payInput.getEmployeeId());
                                    }
                                    
                                    String containsReasonTimeAdj =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_ADJUSMENT]+"_"+reason.getNo()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();//karena di dbtblPayInput harus menambahkan periode dan empID
                                    if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsReasonTimeAdj)){
                                        double value = (Double)listPayInputTbl.get(containsReasonTimeAdj);
                                        payInput.addReasonHashTimeAdjust(reason.getNo(), value,payInput.getPeriodId(),payInput.getEmployeeId());
                                    }

                            }
                        }
                        String containsAbsanceIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsAbsanceIdx)){
                            double value = (Double)listPayInputTbl.get(containsAbsanceIdx);
                            payInput.setAbsenceIdx((int)value);
                            
                        }
                        
                        String containsAbsenceAdj = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsAbsenceAdj)){
                            double value = (Double)listPayInputTbl.get(containsAbsenceAdj);
                            payInput.setAbsenceIdxAdjust((int)value);
                            
                        }
                        
                        String containsAbsenceTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsAbsenceTime)){
                            double value = (Double)listPayInputTbl.get(containsAbsenceTime);
                            payInput.setAbsenceTime(value);
                            
                        }
                        
                        String containsAbsenceTimeAdj = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsAbsenceTimeAdj)){
                            double value = (Double)listPayInputTbl.get(containsAbsenceTimeAdj);
                            payInput.setAbsenceTimeAdjust(value);
                            
                        }
                        
                        String containsUnpaidLeaveAdj = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_UNPAID_LEAVE_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsUnpaidLeaveAdj)){
                            double value = (Double)listPayInputTbl.get(containsUnpaidLeaveAdj);
                            payInput.setUnPaidLeaveAdjust(value);
                            
                        }
                        
                        String containsInsentifAdj = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_INSENTIF_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsInsentifAdj)){
                            double value = (Double)listPayInputTbl.get(containsInsentifAdj);
                            payInput.setInsentifAdjusment(value);
                            
                        }
                        
                        String containsOtDp = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_DP]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsOtDp)){
                            double value = (Double)listPayInputTbl.get(containsOtDp);
                            payInput.setOtIdxPaidDp((int)value);
                            
                        }
                        
                        String containsOtAllowanceFood = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsOtAllowanceFood)){
                            double value = (Double)listPayInputTbl.get(containsOtAllowanceFood);
                            payInput.setOtAllowanceFood(value);
                            
                        }
                        
                        String containsOtAllowanceFoodAdjust = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_ADJUSMENT]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsOtAllowanceFoodAdjust)){
                            double value = (Double)listPayInputTbl.get(containsOtAllowanceFoodAdjust);
                            payInput.setOtAllowanceFoodAdjust(value);
                            
                        }
                        
                       if(listPost!=null && listPost.size()>0){
                            for(int idxPos=0;idxPos<listPost.size();idxPos++){
                                    Position position = (Position)listPost.get(idxPos);
                                    String containsPositionIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_IDX]+"_"+position.getOID()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();//karena di tbHastbalePayInput ada payInput.getPeriodId()+"_"+payInput.getEmployeeId() double
                                    if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsPositionIdx)){
                                        double value = (Double)listPayInputTbl.get(containsPositionIdx);
                                        payInput.addPositionHashIdx(position.getOID(), value,payInput.getPeriodId(),payInput.getEmployeeId());
                                    }
                                    String containsPositionIdxAdj = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_ADJUSMENT]+"_"+position.getOID()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId()+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();//karena di tbHastbalePayInput ada payInput.getPeriodId()+"_"+payInput.getEmployeeId() double
                                    if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsPositionIdxAdj)){
                                        double value = (Double)listPayInputTbl.get(containsPositionIdxAdj);
                                        payInput.addPositionHashAdjust(position.getOID(), value,payInput.getPeriodId(),payInput.getEmployeeId());
                                    }
                            }
                        }
                       
                       String containsEmpWorkDays = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EMP_WORK_DAYS]+"_"+payInput.getPeriodId()+"_"+payInput.getEmployeeId();
                        if(listPayInputTbl!=null && listPayInputTbl.containsKey(containsEmpWorkDays)){
                            double value = (Double)listPayInputTbl.get(containsEmpWorkDays);
                            payInput.setEmpWorkDays(value);
                            
                        }
                        //position
                        
                       // payInput.setCompanyName(rs.getString(PstPayInput.fieldNames[PstPayInput.FLD_COMPANY_NAME]));
                        
		}catch(Exception e){ 
                    System.out.println("exc "+e);
                }
	}
    public static Vector list(int limitStart,int recordToGet, String whereClause, String order){
		Vector lists = new Vector(); 
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM " + TBL_PAY_INPUT; 
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;
			if(order != null && order.length() > 0)
				sql = sql + " ORDER BY " + order;
			if(limitStart == 0 && recordToGet == 0)
				sql = sql + "";
			else
				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;
			dbrs = DBHandler.execQueryResult(sql);
                        //System.out.println("SQL LIST"+sql);
			ResultSet rs = dbrs.getResultSet();
			while(rs.next()) {
				PayInputTabel payInputTabel = new PayInputTabel();
				resultToObject(rs, payInputTabel);
				lists.add(payInputTabel);
			}
			rs.close();
			

		}catch(Exception e) {
			System.out.println(e);
		}finally {
			DBResultSet.close(dbrs);
                        return lists;
		}
	}
   
   public static void resultToObjectPayInput(PayInput payInput,PayInput objPayInput,Vector listReason,Vector listPos){
		try{
			payInput.setPresenceOntimeIdxAdjust(objPayInput.getPresenceOntimeIdxAdjust());
                        payInput.setPresenceOntimeTime(objPayInput.getPresenceOntimeTime());
                        payInput.setPresenceOntimeTimeAdjust(objPayInput.getPresenceOntimeTimeAdjust());
                        payInput.setLateIdxAdjust(objPayInput.getLateIdxAdjust());
                        payInput.setLateTime(objPayInput.getLateTime());
                        payInput.setLateTimeAdjust(objPayInput.getLateTimeAdjust());
                        payInput.setEarlyHomeIdx(objPayInput.getEarlyHomeIdx());
                        payInput.setEarlyHomeIdxAdjust(objPayInput.getEarlyHomeIdxAdjust());
                        payInput.setEarlyHomeTime(objPayInput.getEarlyHomeTime());
                        payInput.setEarlyHomeTimeAdjust(objPayInput.getEarlyHomeTimeAdjust());
                        payInput.setLateEarlyIdx(objPayInput.getLateEarlyIdx());
                        payInput.setLateEarlyIdxAdjust(objPayInput.getLateEarlyIdxAdjust());
                        payInput.setLateEarlyTime(objPayInput.getLateEarlyTime());
                        payInput.setLateEarlyTimeAdjust(objPayInput.getLateEarlyTimeAdjust());
                        if(listReason!=null && listReason.size()>0){
                            for(int idxRes=0;idxRes<listReason.size();idxRes++){
                               Reason reason = (Reason)listReason.get(idxRes);
                               payInput.addReasonHashIdx(reason.getNo(),objPayInput.getReasonIdx(reason.getNo(),payInput.getPeriodId(),payInput.getEmployeeId()),payInput.getPeriodId(),payInput.getEmployeeId()); 
                               payInput.addReasonHashIdxAdjust(reason.getNo(),objPayInput.getReasonIdxAdjust(reason.getNo(),payInput.getPeriodId(),payInput.getEmployeeId()),payInput.getPeriodId(),payInput.getEmployeeId());
                               payInput.addReasonHashTime(reason.getNo(),objPayInput.getReasonTime(reason.getNo(),payInput.getPeriodId(),payInput.getEmployeeId()),payInput.getPeriodId(),payInput.getEmployeeId()); 
                               payInput.addReasonHashTimeAdjust(reason.getNo(),objPayInput.getReasonTimeAdjust(reason.getNo(),payInput.getPeriodId(),payInput.getEmployeeId()),payInput.getPeriodId(),payInput.getEmployeeId());
                            } 
                        }
                        payInput.setAbsenceIdx(objPayInput.getAbsenceIdx());
                        payInput.setAbsenceIdxAdjust(objPayInput.getAbsenceIdxAdjust());
                        payInput.setAbsenceTime(objPayInput.getAbsenceTime());
                        payInput.setAbsenceTimeAdjust(objPayInput.getAbsenceTimeAdjust());
                        payInput.setUnPaidLeaveAdjust(objPayInput.getUnPaidLeaveAdjust());
                        payInput.setInsentifAdjusment(objPayInput.getInsentifAdjusment());
                        payInput.setOtAllowanceFood(objPayInput.getOtAllowanceFood());
                        payInput.setOtAllowanceFoodAdjust(objPayInput.getOtAllowanceFoodAdjust());
                        if(listPos!=null && listPos.size()>0){
                            for(int idxPos=0;idxPos<listPos.size();idxPos++){
                                   Position position = (Position)listPos.get(idxPos);
                                   payInput.addPositionHashIdx(position.getOID(),objPayInput.getPositionIdx(position.getOID(),payInput.getPeriodId(),payInput.getEmployeeId()),payInput.getPeriodId(),payInput.getEmployeeId());
                                   payInput.addPositionHashAdjust(position.getOID(),objPayInput.getPositionAdjust(position.getOID(),payInput.getPeriodId(),payInput.getEmployeeId()),payInput.getPeriodId(),payInput.getEmployeeId());
                            }
                        }
                        
		}catch(Exception e){ System.out.println("Exc resultToObjectPayInput"+e); } 
	}
   
   public static void resultToObject(ResultSet rs, PayInputTabel payInputTabel){
		try{
                        payInputTabel.setOID(rs.getLong(PstPayInput.fieldNames[PstPayInput.FLD_PAY_INPUT_ID]));
                        payInputTabel.setPaySlipId(rs.getLong(PstPayInput.fieldNames[PstPayInput.FLD_PAY_SLIP_ID]));
                        payInputTabel.setEmployeeId(rs.getLong(PstPayInput.fieldNames[PstPayInput.FLD_EMPLOYEE_ID]));
                        payInputTabel.setPeriodId(rs.getLong(PstPayInput.fieldNames[PstPayInput.FLD_PERIODE_ID]));
                        String nameCode = rs.getString(PstPayInput.fieldNames[PstPayInput.FLD_PAY_INPUT_CODE]);
                        double value = rs.getDouble(PstPayInput.fieldNames[PstPayInput.FLD_PAY_INPUT_VALUE]);
                        //payInputTabel.addPayInputValue(nameCode, payInputTabel.getPeriodId(), payInputTabel.getEmployeeId(), value);
                        payInputTabel.setPayInputValue(value);
                        payInputTabel.setPayInputCode(nameCode);
                       
                }catch(Exception exc){
                    
                }
   }
   
 
   public static boolean checkOID(long payInputId){
		DBResultSet dbrs = null;
		boolean result = false;
		try{
			String sql = "SELECT * FROM " + TBL_PAY_INPUT + " WHERE " + 
						PstPayInput.fieldNames[PstPayInput.FLD_PAY_INPUT_ID] + " = '" + payInputId+"'";

			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) { result = true; }
			rs.close();
		}catch(Exception e){
			System.out.println("err : "+e.toString());
		}finally{
			DBResultSet.close(dbrs);
			return result;
		}
	}
   
   /**
    * unpdate pay input
    * create by satrya 2014-04-28
    * @param paySlipId
    * @param payCode
    * @param periodId
    * @param employeeId
    * @param value 
    */
   public  void updatePayInput(long paySlipId,String payCode,long periodId,long employeeId,double value,Date dtAdjusment,Vector vListError,Hashtable hashEmployee){
       int scc=0;
       if(paySlipId!=0 && periodId!=0 && employeeId!=0 && payCode!=null && payCode.length()!=0 && dtAdjusment!=null){
		try{
			String sql = "UPDATE "+PstPayInput.TBL_PAY_INPUT + " SET "+PstPayInput.fieldNames[PstPayInput.FLD_PAY_INPUT_VALUE]+"="+value +","+ PstPayInput.fieldNames[PstPayInput.FLD_DATE_ADJUMSENT]+"=\""+Formater.formatDate(dtAdjusment, "yyyy-MM-dd HH:mm")+"\""
                            + " WHERE "+PstPayInput.fieldNames[PstPayInput.FLD_PAY_SLIP_ID]+"="+paySlipId
                                +" AND "+PstPayInput.fieldNames[PstPayInput.FLD_PAY_INPUT_CODE] +"=\""+payCode+"\" AND "+PstPayInput.fieldNames[PstPayInput.FLD_PERIODE_ID]+"="+periodId + " AND "+PstPayInput.fieldNames[PstPayInput.FLD_EMPLOYEE_ID] + "="+employeeId;

			scc = DBHandler.execUpdate(sql);
                        
		}catch(Exception e){
			System.out.println("err : "+e.toString());
		}finally{
                    if(scc==0){
                            //jika tidak bverhasil save
                        String replacePayCode="";
                        if(payCode!=null && payCode.length()>0){
                            String tmpPayCode[] = payCode.split("_"); 
                            if(tmpPayCode!=null && tmpPayCode.length>0){
                                      replacePayCode = tmpPayCode[0];
                            }
                          
                        }
                            Employee emp = new Employee();
                            if(hashEmployee!=null && hashEmployee.size()>0 && employeeId!=0){
                                emp = (Employee)hashEmployee.get(employeeId);
                            }
                            vListError.add(replacePayCode+" "+emp.getEmployeeNum());
                    }
		}
       }
   }
   
   public static int getCount(String whereClause){
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT COUNT("+ PstPayInput.fieldNames[PstPayInput.FLD_PAY_INPUT_ID] + ") FROM " + TBL_PAY_INPUT;
			if(whereClause != null && whereClause.length() > 0)
				sql = sql + " WHERE " + whereClause;

			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			int count = 0;
			while(rs.next()) { count = rs.getInt(1); }

			rs.close();
			return count;
		}catch(Exception e) {
			return 0;
		}finally {
			DBResultSet.close(dbrs);
		}
	}
   
    public static int getPayInputValue(long paySlipId, String payInputCode, long periodeId, long employeeId){
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT "+ PstPayInput.fieldNames[PstPayInput.FLD_PAY_INPUT_VALUE] + " FROM " + TBL_PAY_INPUT;
			
				sql = sql + " WHERE " + PstPayInput.fieldNames[PstPayInput.FLD_PAY_SLIP_ID] +" = "+paySlipId+"" ;
                                sql = sql + " AND " + PstPayInput.fieldNames[PstPayInput.FLD_PAY_INPUT_CODE] +" = \""+payInputCode+" \" " ;
                                sql = sql + " AND " + PstPayInput.fieldNames[PstPayInput.FLD_PERIODE_ID] +" = "+periodeId+"" ;
                                sql = sql + " AND " + PstPayInput.fieldNames[PstPayInput.FLD_EMPLOYEE_ID] +" = "+employeeId+" " ;
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();

			int count = 0;
			while(rs.next()) { count = rs.getInt(1); }

			rs.close();
			return count;
		}catch(Exception e) {
			return 0;
		}finally {
			DBResultSet.close(dbrs);
		}
	}
    
   public static Hashtable listPayInput(Date selectedDateFrom, Date selectedDateTo,String getListEmployeeId){
                Hashtable hashListPayInput = new Hashtable();
                if(selectedDateFrom==null || selectedDateTo==null || getListEmployeeId==null || getListEmployeeId.length()==0){ 
                    return hashListPayInput;
                }
		DBResultSet dbrs = null;
		try {
			String sql = "SELECT * FROM "+TBL_PAY_INPUT + " WHERE ("+fieldNames[FLD_DATE_ADJUMSENT]+" BETWEEN \""+Formater.formatDate(selectedDateFrom, "yyyy-MM-dd") +"\" AND \""+Formater.formatDate(selectedDateTo, "yyyy-MM-dd") +"\") AND "+fieldNames[FLD_EMPLOYEE_ID] + " IN("+getListEmployeeId+")";
			
			dbrs = DBHandler.execQueryResult(sql);
			ResultSet rs = dbrs.getResultSet();
 
			while(rs.next()) {
                            //HashTablePayInput hashTablePayInput = new HashTablePayInput();
                            String tmpResult = rs.getString(fieldNames[FLD_PAY_INPUT_CODE]);
                            if(tmpResult!=null){
                                String hasilNya[] = tmpResult.split("_");
                                String payInputCode = hasilNya[0];
                                Date dtAdjusment = rs.getDate(fieldNames[FLD_DATE_ADJUMSENT]);
                                double value = rs.getDouble(fieldNames[FLD_PAY_INPUT_VALUE]);
                                if(hasilNya.length>2){
                                    
                                    String posOrReasonNo = hasilNya[1];
                                    //String periodeId = hasilNya[2];//periode
                                    String empId = hasilNya[3];//employeeId
                                    payInputCode = payInputCode+"_"+posOrReasonNo+"_"+empId;
                                     //hashTablePayInput.addHashValue(dtAdjusment, value);
                                    if(dtAdjusment!=null){ 
                                     hashListPayInput.put(payInputCode+"_"+Formater.formatDate(dtAdjusment, "yyyy-MM-dd"),value);  
                                    }
                                    
                                }else{
                                    
                                     //hashTablePayInput.addHashValue(dtAdjusment, value);
                                    if(dtAdjusment!=null){
                                        long empId = rs.getLong(fieldNames[FLD_EMPLOYEE_ID]);
                                        hashListPayInput.put(payInputCode+"_"+empId+"_"+Formater.formatDate(dtAdjusment, "yyyy-MM-dd"),value); 
                                    }
                                     
                                }
                            }
                           
                           
                        }

			rs.close();
			
		}catch(Exception e) {
			
		}finally {
			DBResultSet.close(dbrs);
                        return hashListPayInput;
		}
	}
   
   /* This method used to find current data */
	public static int findLimitStart( long oid, int recordToGet, String whereClause,String orderClause){
		String order = "";
		int size = getCount(whereClause);
		int start = 0;
		boolean found =false;
		for(int i=0; (i < size) && !found ; i=i+recordToGet){
			 Vector list =  list(i,recordToGet, whereClause, orderClause); 
			 start = i;
			 if(list.size()>0){
			  for(int ls=0;ls<list.size();ls++){ 
			  	   PayInput payInput = (PayInput)list.get(ls);
				   if(oid == payInput.getOID())
					  found=true;
			  }
		  }
		}
		if((start >= size) && (size > 0))
		    start = start - recordToGet;

		return start;
	}
        
        
    public static int findLimitCommand(int start, int recordToGet, int vectSize)
    {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + mdl;
    	if(start == 0)
            cmd =  Command.FIRST;
        else{
            if(start == (vectSize-recordToGet))
                cmd = Command.LAST;
            else{
            	start = start + recordToGet;
             	if(start <= (vectSize - recordToGet)){
                 	cmd = Command.NEXT;
                    System.out.println("next.......................");
             	}else{
                    start = start - recordToGet;
		             if(start > 0){
                         cmd = Command.PREV;
                         System.out.println("prev.......................");
		             }
                }
            }
        }

        return cmd;
    }
    
   

    
    
}
