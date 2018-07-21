/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.payroll;

import com.dimata.harisma.entity.arap.ArApItem;
import com.dimata.harisma.entity.arap.PstArApItem;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.entity.payroll.PayEmpLevel;
import com.dimata.harisma.entity.payroll.PayPeriod;
import com.dimata.harisma.entity.payroll.PaySlip;
import com.dimata.harisma.entity.payroll.PstPayPeriod;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.harisma.session.employee.SessEmployee;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Dimata 007
 */
public class CtrlPayPeriod extends Control implements I_Language {

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
    private PayPeriod payPeriod;
    private PstPayPeriod pstPayPeriod;
    private FrmPayPeriod frmPayPeriod;
    int language = LANGUAGE_DEFAULT;

    public CtrlPayPeriod(HttpServletRequest request) {
        msgString = "";
        payPeriod = new PayPeriod();
        try {
            pstPayPeriod = new PstPayPeriod(0);
        } catch (Exception e) {;
        }
        frmPayPeriod = new FrmPayPeriod(request, payPeriod);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmPayPeriod.addError(frmPayPeriod.FRM_FIELD_PERIOD_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public PayPeriod getPeriod() {
        return payPeriod;
    }

    public FrmPayPeriod getForm() {
        return frmPayPeriod;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidPeriod, int enableReplaceExistingSchedule) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidPeriod != 0) {
                    try {
                        payPeriod = PstPayPeriod.fetchExc(oidPeriod);
                    } catch (Exception exc) {
                        System.out.println("Exception"+exc);
                    }
                }

                frmPayPeriod.requestEntityObject(payPeriod);

                if (frmPayPeriod.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (payPeriod.getOID() == 0) {
                    try {
                        long oid = pstPayPeriod.insertExc(this.payPeriod);
                        //System.out.println("periodId atas..."+period.getOID());
                        // insert ke payslip

                        Vector vectEmp = SessEmployee.getEmpPaySlip();
                        if (vectEmp != null && vectEmp.size() > 0) {
                            try {
                                for (int i = 0; i < vectEmp.size(); i++) {
                                    Vector temp = (Vector) vectEmp.get(i);
                                    Employee emp = (Employee) temp.get(0);
                                    Division division = (Division) temp.get(1);
                                    Department department = (Department) temp.get(2);
                                    Position position = (Position) temp.get(3);
                                    Section section = (Section) temp.get(4);
                                    PayEmpLevel payEmpLevel = (PayEmpLevel) temp.get(5);

                                    PaySlip paySlip = new PaySlip();
                                    paySlip.setPeriodId(payPeriod.getOID());
                                    paySlip.setEmployeeId(emp.getOID());
                                    paySlip.setStatus(0);
                                    paySlip.setPaidStatus(0);
                                    paySlip.setPaySlipDate(payPeriod.getPaySlipDate());
                                    paySlip.setDayPresent(0);
                                    paySlip.setDayPaidLv(0);
                                    paySlip.setDayAbsent(0);
                                    paySlip.setDayUnpaidLv(0);
                                    paySlip.setDivision(division.getDivision());
                                    paySlip.setDepartment(department.getDepartment());
                                    paySlip.setPosition(position.getPosition());
                                    paySlip.setSection(section.getSection());
                                    paySlip.setNote("");
                                    paySlip.setCommencDate(emp.getCommencingDate());
                                    paySlip.setPaySlipType(0);
                                    paySlip.setCompCode("");
                                    paySlip.setDayLate(0);
                                    PstPaySlip.insertExc(paySlip);
                                    //paySlip.set
                                    //System.out.println("emp id..."+emp.getOID());


                                }
                            } catch (Exception exc) {
                                System.out.println("Err CtlPeriod.COMMAND.SAVE");
                            }

                        }


                        //samapi sisni......................
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }

                } else {
                    try {
                        long oid = pstPayPeriod.updateExc(this.payPeriod);
                        
                        if(payPeriod.getPayProsess() == 1){
                            String startDate = Formater.formatDate(payPeriod.getStartDate(),"yyyy-MM-dd");
                            String endDate = Formater.formatDate(payPeriod.getEndDate(),"yyyy-MM-dd");
                            
                            Vector listArpItem = PstArApItem.list(0, 0, "due_date >= '"+startDate+"' AND due_date <= '"+endDate+"'", "");
                            for(int i = 0; i < listArpItem.size(); i++){
                                ArApItem arpItem = (ArApItem)listArpItem.get(i);
                                arpItem.setArApItemStatus(1);
                                long oidArpItem = PstArApItem.updateExc(arpItem);
                            }
                        }
                        
                        Hashtable hashCekExistPayslip = PstPaySlip.getOidPayslipByPeriod(payPeriod.getOID());
                        Vector vectEmp = SessEmployee.getEmpPaySlip();
                        if (vectEmp != null && vectEmp.size() > 0) {
                            try {
                                for (int i = 0; i < vectEmp.size(); i++) {
                                    Vector temp = (Vector) vectEmp.get(i);
                                    Employee emp = (Employee) temp.get(0);
                                    Division division = (Division) temp.get(1);
                                    Department department = (Department) temp.get(2);
                                    Position position = (Position) temp.get(3);
                                    Section section = (Section) temp.get(4);
                                    PayEmpLevel payEmpLevel = (PayEmpLevel) temp.get(5);
                                    
                                if(hashCekExistPayslip!=null && hashCekExistPayslip.size()==0 && hashCekExistPayslip.get(emp)==null){    
                                
                                    PaySlip paySlip = new PaySlip();
                                    paySlip.setPeriodId(payPeriod.getOID());
                                    paySlip.setEmployeeId(emp.getOID());
                                    paySlip.setStatus(0);
                                    paySlip.setPaidStatus(0);
                                    paySlip.setPaySlipDate(payPeriod.getPaySlipDate());
                                    paySlip.setDayPresent(0);
                                    paySlip.setDayPaidLv(0);
                                    paySlip.setDayAbsent(0);
                                    paySlip.setDayUnpaidLv(0);
                                    paySlip.setDivision(division.getDivision());
                                    paySlip.setDepartment(department.getDepartment());
                                    paySlip.setPosition(position.getPosition());
                                    paySlip.setSection(section.getSection());
                                    paySlip.setNote("");
                                    paySlip.setCommencDate(emp.getCommencingDate());
                                    paySlip.setPaySlipType(0);
                                    paySlip.setCompCode("");
                                    paySlip.setDayLate(0);
                                    PstPaySlip.insertExc(paySlip);
                                    //paySlip.set
                                    //System.out.println("emp id..."+emp.getOID());
                                }

                                }
                            } catch (Exception exc) {
                                System.out.println("Err CtlPeriod.COMMAND.SAVE");
                            }

                        }
                        
                        
                        
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidPeriod != 0) {
                    try {
                        payPeriod = PstPayPeriod.fetchExc(oidPeriod);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidPeriod != 0) {
                    try {
                        /*if (PstPayPeriod.checkOID(oidPeriod)) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                        } else {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        }*/

                        payPeriod = PstPayPeriod.fetchExc(oidPeriod);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidPeriod != 0) {
                    try {
                        long oid = PstPayPeriod.deleteExc(oidPeriod);
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
                break;
        }
        return rsCode;
    }
    //update by satrya 2012-11-23
    public int action(int cmd, long oidPeriod){
        return action(cmd, oidPeriod, 0);
    
    }
    
}
