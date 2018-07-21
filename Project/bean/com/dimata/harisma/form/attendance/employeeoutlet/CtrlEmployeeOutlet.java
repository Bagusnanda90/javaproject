/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.attendance.employeeoutlet;

import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.employeeoutlet.EmployeeOutlet;
import com.dimata.harisma.entity.attendance.employeeoutlet.PstEmployeeOutlet;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Satrya Ramayu
 */
public class CtrlEmployeeOutlet extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_FORM_OVERLAP_SCHEDULE = 4;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap"," schedule sama"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete"," Employee is Overlap schedule"}
    };
    private int start;
    private String msgString;
    private EmployeeOutlet objEmployeeOutlet;
    //private EmployeeOutlet prevObjEmployeeOutlet;
    private PstEmployeeOutlet pstEmployeeOutlet;
    private FrmEmployeeOutlet frmEmployeeOutlet;
    int language = LANGUAGE_FOREIGN;

    public CtrlEmployeeOutlet(HttpServletRequest request) {
        msgString = "";
        objEmployeeOutlet = new EmployeeOutlet();
        try {
            pstEmployeeOutlet = new PstEmployeeOutlet(0);
        } catch (Exception e) {;
        }
        frmEmployeeOutlet = new FrmEmployeeOutlet(request, objEmployeeOutlet);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmEmployeeOutlet.addError(frmEmployeeOutlet.FRM_FIELD_OUTLET_EMPLOYEE_ID, resultText[language][RSLT_EST_CODE_EXIST]);
                return resultText[language][RSLT_EST_CODE_EXIST];
            default:
                return resultText[language][RSLT_UNKNOWN_ERROR];
        }
    }

    private int getControlMsgId(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                return RSLT_EST_CODE_EXIST;
            default:
                return RSLT_UNKNOWN_ERROR;
        }
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public EmployeeOutlet getEmployeeOutlet() {
        return objEmployeeOutlet;
    }

    public FrmEmployeeOutlet getForm() {
        return frmEmployeeOutlet;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidEmployeeOutlet,Hashtable hashSchedule,Hashtable hashScheduleSymbol) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidEmployeeOutlet != 0) {
                    try {
                        objEmployeeOutlet = PstEmployeeOutlet.fetchExc(oidEmployeeOutlet);
                    } catch (Exception exc) {
                    }
                }

                //frmEmployeeOutlet.requestEntityObject(objEmployeeOutlet);

                frmEmployeeOutlet.requestEntityObjectMultiple(hashSchedule,hashScheduleSymbol);
                if(frmEmployeeOutlet!=null && frmEmployeeOutlet.getMsgFrm()!=null && frmEmployeeOutlet.getMsgFrm().length()>0){
                         msgString = frmEmployeeOutlet.getMsgFrm();
                            return RSLT_FORM_INCOMPLETE;
                }
                
                //Hashtable hashEmpMsg = new Hashtable();
                if (frmEmployeeOutlet.getEmployeeOutletList() != null && frmEmployeeOutlet.getEmployeeOutletList().size() > 0) {
                    Hashtable hashEmpErorr = new Hashtable();
                    for (int idx = 0; idx < frmEmployeeOutlet.getEmployeeOutletList().size(); idx++) {
                        objEmployeeOutlet = new EmployeeOutlet();
                        objEmployeeOutlet = (EmployeeOutlet)frmEmployeeOutlet.getEmployeeOutletList().get(idx);
                        if (frmEmployeeOutlet != null && frmEmployeeOutlet.errorSize() > 0) {
                            objEmployeeOutlet = (EmployeeOutlet)frmEmployeeOutlet.getEmployeeOutletList().get(idx);
                            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                            return RSLT_FORM_INCOMPLETE;
                        }
                        String where = "";
                        if (objEmployeeOutlet != null && objEmployeeOutlet.getDtFrom() != null && objEmployeeOutlet.getDtTo() != null) {
                            where = "\"" + Formater.formatDate(objEmployeeOutlet.getDtTo(), "yyyy-MM-dd HH:mm:ss") + "\">=" + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_FROM]
                                    + " AND "
                                    + PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_DATE_TO] + " >= \"" + Formater.formatDate(objEmployeeOutlet.getDtFrom(), "yyyy-MM-dd HH:mm:ss") + "\""
                                    + " AND "+PstEmployeeOutlet.fieldNames[PstEmployeeOutlet.FLD_EMPLOYEE_ID]+"="+objEmployeeOutlet.getEmployeeId();
                        }
                        //cek jika ada overlap saat itu
                        Vector listEmployeeOutlet = PstEmployeeOutlet.list(0, 0, where, "");
                        if (listEmployeeOutlet != null && listEmployeeOutlet.size() > 0) {
                            for(int x=0;x<listEmployeeOutlet.size();x++){
                                EmployeeOutlet employeeOutlets = (EmployeeOutlet)listEmployeeOutlet.get(x);
                                Employee employee = new Employee();
                                try{
                                    employee=PstEmployee.fetchExc(employeeOutlets.getEmployeeId());
                                }catch(Exception exc){}
                                String fullName=employee.getFullName();
                                String start = employeeOutlets.getDtFrom()!=null ? Formater.formatDate(employeeOutlets.getDtFrom(), "dd MMM yyyy"):"";
                                String end = employeeOutlets.getDtTo()!=null ? Formater.formatDate(employeeOutlets.getDtTo(), "dd MMM yyyy"):"";
                                   Date newStartDate = employeeOutlets.getDtFrom();
                                   Date newEndDate = employeeOutlets.getDtTo();
                                   Date startDate = objEmployeeOutlet.getDtFrom();
                                   Date endDate = objEmployeeOutlet.getDtTo();
                                   //hashEmpMsg.put(employee.getOID(), fullName);
                                    if (newStartDate.after(startDate) && newStartDate.before(endDate)) {
                                        msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start +" to " + end;
                                        hashEmpErorr.put(employeeOutlets.getEmployeeId(), true);
                                    }
                                    else if (newEndDate.after(startDate) && newEndDate.before(endDate)) {
                                        msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start +" to " + end;
                                        hashEmpErorr.put(employeeOutlets.getEmployeeId(), true);
                                    }
                                    else if (startDate.after(newStartDate) && startDate.before(newEndDate)) {
                                        msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start +" to " + end ;
                                        hashEmpErorr.put(employeeOutlets.getEmployeeId(), true);
                                    }
                                    else if (endDate.after(newStartDate) && endDate.before(newEndDate)) {
                                        msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start +" to " + end;
                                        hashEmpErorr.put(employeeOutlets.getEmployeeId(), true);
                                    }
                                    else if ( newStartDate.equals(startDate) && newEndDate.equals(endDate)) {
                                         msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start +" to " + end ;
                                        hashEmpErorr.put(employeeOutlets.getEmployeeId(), true);
                                    }
                                //msgString = msgString + " Employee " + fullName + " is Overlap employee outlet date " + start +" to " + end;
                                //hashEmpErorr.put(employeeOutlets.getEmployeeId(), true);
                            }
                            //return RSLT_FORM_OVERLAP_SCHEDULE;  
                        }
                        
                     if (objEmployeeOutlet.getOID() == 0 && hashEmpErorr.containsKey(objEmployeeOutlet.getEmployeeId())==false) {
                            try {
                                long oid = pstEmployeeOutlet.insertExc(objEmployeeOutlet);
                               // int oidSchedule = PstEmpSchedule.getUpdateScheduleOutlet(objEmployeeOutlet.getDtFrom(), objEmployeeOutlet.getDtTo(), objEmployeeOutlet.getScheduleType(), objEmployeeOutlet.getEmployeeId());
                                String fullName = "";
                                /*if(hashEmpMsg!=null && hashEmpMsg.size()>0 && hashEmpMsg.get(objEmployeeOutlet.getEmployeeId())!=null){
                                    fullName = (String)hashEmpMsg.get(objEmployeeOutlet.getEmployeeId());
                                }*/
                                Employee employee = new Employee();
                                try{
                                    employee=PstEmployee.fetchExc(objEmployeeOutlet.getEmployeeId());
                                    fullName = employee.getFullName();
                                    msgString = msgString + "<br>" +resultText[language][RSLT_OK] + " save employee "+fullName ;
                                }catch(Exception exc){
                                    System.out.println("Exc empLoutelet insert"+exc);
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
                    }
                    
                }

                        break;

                    
                    case Command.EDIT :
				if (oidEmployeeOutlet != 0) {
					try {
						objEmployeeOutlet = pstEmployeeOutlet.fetchExc(oidEmployeeOutlet);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidEmployeeOutlet != 0) {
					try {
						objEmployeeOutlet = PstEmployeeOutlet.fetchExc(oidEmployeeOutlet);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidEmployeeOutlet != 0){
					try{
						long oid = PstEmployeeOutlet.deleteExc(oidEmployeeOutlet);
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

			default :

		}
		return rsCode;
                }
        }
