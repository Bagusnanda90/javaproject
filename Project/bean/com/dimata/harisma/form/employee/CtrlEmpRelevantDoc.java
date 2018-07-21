/*
 * CtlEmpRelevantDoc.java
 *
 * Created on December 3, 2007, 5:56 PM
 */

package com.dimata.harisma.form.employee;

/**
 *
 * @author  yunny
 */

/* java package */ 
import com.dimata.harisma.entity.admin.AppUser;
import com.dimata.harisma.entity.admin.PstAppUser;
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 
/* dimata package */
import com.dimata.util.*;
import com.dimata.util.lang.*;
/* qdep package */
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.harisma.entity.log.I_LogHistory;
import com.dimata.harisma.entity.log.LogSysHistory;
import com.dimata.harisma.entity.log.PstLogSysHistory;
import com.dimata.harisma.entity.masterdata.EmpRelevantDocGroup;
import com.dimata.harisma.entity.masterdata.PstEmpRelevantDocGroup;
import static com.dimata.harisma.form.employee.CtrlEmpLanguage.RSLT_FORM_INCOMPLETE;
import static com.dimata.harisma.form.employee.CtrlEmpLanguage.RSLT_OK;


/**
 *
 * @author  yunny
 */
public class CtrlEmpRelevantDoc extends Control implements I_Language{
    
    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    
     public static String[][] resultText = {
            {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap"},
            {"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
    };
    
    private int start;
    private String msgString;
    private EmpRelevantDoc empRelevantDoc;
    private PstEmpRelevantDoc pstEmpRelevantDoc;
    private FrmEmpRelevantDoc frmEmpRelevantDoc;
    int language = LANGUAGE_DEFAULT;
    
    /** Creates a new instance of CtlEmpRelevantDoc */
     public CtrlEmpRelevantDoc(HttpServletRequest request){
		msgString = "";
		empRelevantDoc = new EmpRelevantDoc();
		try{
			pstEmpRelevantDoc = new PstEmpRelevantDoc(0);
		}catch(Exception e){;}
		frmEmpRelevantDoc = new FrmEmpRelevantDoc(request, empRelevantDoc);
                
	}
     
      private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmEmpRelevantDoc.addError(frmEmpRelevantDoc.FRM_FIELD_DOC_RELEVANT_ID, resultText[language][RSLT_EST_CODE_EXIST] );
				return resultText[language][RSLT_EST_CODE_EXIST];
			default:
				return resultText[language][RSLT_UNKNOWN_ERROR]; 
		}
   }
      
    
       private int getControlMsgId(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				return RSLT_EST_CODE_EXIST;
			default:
				return RSLT_UNKNOWN_ERROR;
		}
	}
       
    public int getLanguage(){ return language; }

    public void setLanguage(int language){ this.language = language; }

    public EmpRelevantDoc getEmpRelevantDoc() { return empRelevantDoc; } 

    public FrmEmpRelevantDoc getForm() { return frmEmpRelevantDoc; }

    public String getMessage(){ return msgString; }

    public int getStart() { return start; }
    
    
    public int action(int cmd , long oidEmpRelevantDoc, long oidEmployee, HttpServletRequest request, String loginName, long userId, String oidDeleteAll){
		msgString = "";
                
                //ystem.out.println("cmd...."+cmd);
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
                int sysLog = Integer.parseInt(String.valueOf(PstSystemProperty.getPropertyLongbyName("SET_USER_ACTIVITY_LOG")));
                //long sysLog = 1;
                String logField = "";
                String logPrev = "";
                String logCurr = "";
                Date nowDate = new Date();
                AppUser appUser = new AppUser();
                Employee emp = new Employee();
                try {
                appUser = PstAppUser.fetch(userId);
                emp = PstEmployee.fetchExc(appUser.getEmployeeId());
                } catch(Exception e){
                System.out.println("Get AppUser: userId");
                }
                /* End Prepare data (configurasi log system) */
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
                                EmpRelevantDoc prevEmpDoc = null;
				if(oidEmpRelevantDoc != 0){
                                    try {
                                        empRelevantDoc = PstEmpRelevantDoc.fetchExc(oidEmpRelevantDoc);
                                        if (sysLog == 1) {

                                            prevEmpDoc = PstEmpRelevantDoc.fetchExc(oidEmpRelevantDoc);
                                        }
                                    } catch (Exception exc) {
                                    }
				}

                            empRelevantDoc.setOID(oidEmpRelevantDoc);

                            frmEmpRelevantDoc.requestEntityObject(empRelevantDoc);

                            empRelevantDoc.setEmployeeId(oidEmployee);
                            
                            if(this.empRelevantDoc.getDocTitle()=="" || this.empRelevantDoc==null){
                                                msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                                                return RSLT_FORM_INCOMPLETE ;
                                            }
                      //System.out.println("frmEmpRelevantDoc.errorSize()"+frmEmpRelevantDoc.errorSize());

				/*if(frmEmpRelevantDoc.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}*/
 
                            if (empRelevantDoc.getOID() == 0) {
                        try {
                        long result = pstEmpRelevantDoc.insertExc(this.empRelevantDoc);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        if (sysLog == 1) {
                            
                            String className = empRelevantDoc.getClass().getName();
                            LogSysHistory logSysHistory = new LogSysHistory();
                        
                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + empRelevantDoc.getEmployeeId();
                            /* Lakukan set data ke entity logSysHistory */
                            logSysHistory.setLogDocumentId(0);
                            logSysHistory.setLogUserId(userId);
                            logSysHistory.setApproverId(userId);
                            logSysHistory.setApproveDate(nowDate);
                            logSysHistory.setLogLoginName(loginName);
                            logSysHistory.setLogDocumentNumber("");
                            logSysHistory.setLogDocumentType(className); //entity
                            logSysHistory.setLogUserAction("ADD"); // command
                            logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                            logSysHistory.setLogUpdateDate(nowDate);
                            logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                            /* Inisialisasi logField dengan menggambil field EmpEducation */
                            /* Tips: ambil data field dari persistent */
                            logField = PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_RELEVANT_ID]+";";
                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_EMPLOYEE_ID]+";";
                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_TITLE]+";";
                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_DESCRIPTION]+";";
                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_FILE_NAME]+";";
                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_ATTACH_FILE]+";";
                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_EMP_RELVT_DOC_GRP_ID]+";";
                           
                            
                            
                            /* data logField yg telah terisi kemudian digunakan untuk setLogDetail */
                            logSysHistory.setLogDetail(logField); // apa yang dirubah
                            logSysHistory.setLogStatus(0); /* 0 == draft */
                            /* inisialisasi value, yaitu logCurr */
                            logCurr = ""+empRelevantDoc.getOID()+";";
                            logCurr += ""+empRelevantDoc.getEmployeeId()+";";
                            logCurr += ""+empRelevantDoc.getDocTitle()+";";
                            logCurr += ""+empRelevantDoc.getDocDescription()+";";
                            logCurr += ""+empRelevantDoc.getFileName()+";";
                            logCurr += ""+empRelevantDoc.getDocAttachFile()+";";
                            logCurr += ""+empRelevantDoc.getEmpRelvtDocGrpId()+";";
                            
  
                            /* data logCurr yg telah diinisalisasi kemudian dipakai untuk set ke logPrev, dan logCurr */
                            logSysHistory.setLogPrev(logCurr);
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogModule("Employee Relevant Doc"); /* nama sub module*/
                            /* data struktur perusahaan didapat dari pengguna yang login melalui AppUser */
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            /* mencatat item yang diedit */
                            logSysHistory.setLogEditedUserId(empRelevantDoc.getEmployeeId());
                            /* setelah di set maka lakukan proses insert ke table logSysHistory */
                            PstLogSysHistory.insertExc(logSysHistory);
                        }
                       
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                                
                                
                                else {
                                try {
                                    if (this.empRelevantDoc.getDocTitle() == "" || this.empRelevantDoc == null) {
                                        msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                                        return RSLT_FORM_INCOMPLETE;
                                    }
                                    long oid = pstEmpRelevantDoc.updateExc(this.empRelevantDoc);
                                    msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                                    if (sysLog == 1) {
                            empRelevantDoc = PstEmpRelevantDoc.fetchExc(oid);
                            
                            logField = PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_RELEVANT_ID]+";";
                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_EMPLOYEE_ID]+";";
                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_TITLE]+";";
                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_DESCRIPTION]+";";
                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_FILE_NAME]+";";
                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_ATTACH_FILE]+";";
                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_EMP_RELVT_DOC_GRP_ID]+";";
                            
                            logCurr = ""+empRelevantDoc.getOID()+";";
                            logCurr += ""+empRelevantDoc.getEmployeeId()+";";
                            logCurr += ""+empRelevantDoc.getDocTitle()+";";
                            logCurr += ""+empRelevantDoc.getDocDescription()+";";
                            logCurr += ""+empRelevantDoc.getFileName()+";";
                            logCurr += ""+empRelevantDoc.getDocAttachFile()+";";
                            logCurr += ""+empRelevantDoc.getEmpRelvtDocGrpId()+";";
                            
                            logPrev = ""+prevEmpDoc.getOID()+";";
                            logPrev += ""+prevEmpDoc.getEmployeeId()+";";
                            logPrev += ""+prevEmpDoc.getDocTitle()+";";
                            logPrev += ""+prevEmpDoc.getDocDescription()+";";
                            logPrev += ""+prevEmpDoc.getFileName()+";";
                            logPrev += ""+prevEmpDoc.getDocAttachFile()+";";
                            logPrev += ""+prevEmpDoc.getEmpRelvtDocGrpId()+";";
                            
                            String className = empRelevantDoc.getClass().getName();
                            LogSysHistory logSysHistory = new LogSysHistory();
                            
                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + empRelevantDoc.getOID();
                            /* Lakukan set data ke entity logSysHistory */
                            logSysHistory.setLogDocumentId(0);
                            logSysHistory.setLogUserId(userId);
                            logSysHistory.setApproverId(userId);
                            logSysHistory.setApproveDate(nowDate);
                            logSysHistory.setLogLoginName(loginName);
                            logSysHistory.setLogDocumentNumber("");
                            logSysHistory.setLogDocumentType(className); //entity
                            logSysHistory.setLogUserAction("EDIT"); // command
                            logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                            logSysHistory.setLogUpdateDate(nowDate);
                            logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                            logSysHistory.setLogDetail(logField); // apa yang dirubah
                            logSysHistory.setLogStatus(0); /* 0 == draft */
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogPrev(logPrev);
                            logSysHistory.setLogModule("Employee Relevant Doc"); /* nama sub module*/
                            logSysHistory.setCompanyId(emp.getCompanyId());
                            logSysHistory.setDivisionId(emp.getDivisionId());
                            logSysHistory.setDepartmentId(emp.getDepartmentId());
                            logSysHistory.setSectionId(emp.getSectionId());
                            logSysHistory.setLogEditedUserId(empRelevantDoc.getEmployeeId());
                            PstLogSysHistory.insertExc(logSysHistory);
                            
                          }
                                                
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidEmpRelevantDoc != 0) {
					try {
						empRelevantDoc = PstEmpRelevantDoc.fetchExc(oidEmpRelevantDoc);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidEmpRelevantDoc != 0) {
					try {
						empRelevantDoc = PstEmpRelevantDoc.fetchExc(oidEmpRelevantDoc);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidEmpRelevantDoc != 0){
					try{
                                            empRelevantDoc = PstEmpRelevantDoc.fetchExc(oidEmpRelevantDoc);
                                            
                                            logField = PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_RELEVANT_ID]+";";
                                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_EMPLOYEE_ID]+";";
                                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_TITLE]+";";
                                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_DESCRIPTION]+";";
                                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_FILE_NAME]+";";
                                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_DOC_ATTACH_FILE]+";";
                                            logField += PstEmpRelevantDoc.fieldNames[PstEmpRelevantDoc.FLD_EMP_RELVT_DOC_GRP_ID]+";";

                                            logPrev = ""+empRelevantDoc.getOID()+";";
                                            logPrev += ""+empRelevantDoc.getEmployeeId()+";";
                                            logPrev += ""+empRelevantDoc.getDocTitle()+";";
                                            logPrev += ""+empRelevantDoc.getDocDescription()+";";
                                            logPrev += ""+empRelevantDoc.getFileName()+";";
                                            logPrev += ""+empRelevantDoc.getDocAttachFile()+";";
                                            logPrev += ""+empRelevantDoc.getEmpRelvtDocGrpId()+";";
                                            
                                            logCurr  = "-;";
                                            logCurr += "-;";
                                            logCurr += "-;";
                                            logCurr += "-;";
                                            logCurr += "-;";
                                            logCurr += "-;";
                                            logCurr += "-;";
                                                    
                                            
						long oid = PstEmpRelevantDoc.deleteExc(oidEmpRelevantDoc);
                                                msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                                                if(sysLog == 1){
                                                
                                                
                                                String className = empRelevantDoc.getClass().getName();

                                                LogSysHistory logSysHistory = new LogSysHistory();

                                                String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + empRelevantDoc.getEmployeeId();
                                                
                                                logSysHistory.setLogDocumentId(0);
                                                logSysHistory.setLogUserId(userId);
                                                logSysHistory.setApproverId(userId);
                                                logSysHistory.setApproveDate(nowDate);
                                                logSysHistory.setLogLoginName(loginName);
                                                logSysHistory.setLogDocumentNumber("");
                                                logSysHistory.setLogDocumentType(className); //entity
                                                logSysHistory.setLogUserAction("DELETE"); // command
                                                logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                                                logSysHistory.setLogUpdateDate(nowDate);
                                                logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                                                logSysHistory.setLogDetail(logField); // apa yang dirubah
                                                logSysHistory.setLogStatus(0);
                                                logSysHistory.setLogPrev(logPrev);
                                                logSysHistory.setLogCurr(logCurr);
                                                logSysHistory.setLogModule("Employee Relevant Doc");
                                                logSysHistory.setCompanyId(emp.getCompanyId());
                                                logSysHistory.setDivisionId(emp.getDivisionId());
                                                logSysHistory.setDepartmentId(emp.getDepartmentId());
                                                logSysHistory.setSectionId(emp.getSectionId());
                                                logSysHistory.setLogEditedUserId(empRelevantDoc.getEmployeeId());

                                                PstLogSysHistory.insertExc(logSysHistory);
                                            }
						if(oid!=0){
							msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
							excCode = RSLT_OK;
						}else{
							msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
							excCode = RSLT_FORM_INCOMPLETE;
						}
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch(Exception exc){	
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;
                                case Command.DELETEALL:
                                //splits => untuk mengurutkan beberapa data menjadi baris data
                                String[] splits = oidDeleteAll.split(",");
                                for (String asset : splits) {
                                    if (asset != "") {
                                        long oidDoc = Long.parseLong(asset);
                                        if (oidDoc != 0) {
                                            try {
                                                long oid = PstEmpRelevantDoc.deleteExc(oidDoc);
                                                if (oid != 0) {
                                                    msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                                                    excCode = RSLT_OK;
                                                } else {
                                                    msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                                                    excCode = RSLT_FORM_INCOMPLETE;
                                                }
                                            } catch (DBException dbexc) {
                                                excCode = dbexc.getErrorCode();
                                                msgString = getSystemMessage(excCode);
                                            } catch (Exception exc) {
                                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                            }
                                        }
                                    }
                                }
                                break;

			default :

		}
		return rsCode;
	}
    
   
   
    
}
