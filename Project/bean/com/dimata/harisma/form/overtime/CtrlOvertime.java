/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.overtime;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

// import dimata
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.gui.jsp.ControlDate;

// import qdep
import com.dimata.qdep.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.system.*;

// import project
import com.dimata.harisma.entity.overtime.*;
import com.dimata.harisma.form.overtime.*;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.net.MailSender;
import com.dimata.harisma.entity.leave.I_Leave;

/**
 *
 * @author Wiweka
 */
public class CtrlOvertime extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Kode sudah ada", "Data tidak lengkap"},
        {"Success", "Can not process", "Code exist", "Data incomplete"}
    };
    private int start;
    private String msgString;
    private int language;
    private Overtime overtime;
    private PstOvertime pstOvertime;
    private FrmOvertime frmOvertime;
    
    //update by satrya 2014-06-15
    private OvertimeDetail overtimeDetail;
    private PstOvertimeDetail pstOvertimeDetail;
    private FrmOvertimeDetail frmOvertimeDetail;
    
    private HttpServletRequest req;   
    

    
    //update by satrya 2014-05-26
    private Hashtable hashCekOverlap;
    
    public CtrlOvertime(HttpServletRequest request) {
        msgString = "";
        language = LANGUAGE_DEFAULT;
        overtime = new Overtime();
        //update by satrya 2014-06-15
        overtimeDetail = new OvertimeDetail();

        try {
            pstOvertime = new PstOvertime(0);
            //update by satrya 2014-06-15
            pstOvertimeDetail = new PstOvertimeDetail(0);
        } catch (Exception e) {
        }

        frmOvertime = new FrmOvertime(request, overtime);
        //update by satrya 2014-06-15
        frmOvertimeDetail = new FrmOvertimeDetail(request,overtimeDetail); 
    }

    public int getStart() {
        return start;
    }

    public String getMessage() {
        return msgString;
    }

    public Overtime getOvertime() {
        return overtime;
    }

    public FrmOvertime getForm() {
        return frmOvertime;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                frmOvertime.addError(frmOvertime.FRM_FIELD_OVERTIME_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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
  
    public int action(int cmd, long oid, long oidEmployee, HttpServletRequest request) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;

        switch (cmd) {
            case Command.ADD:
                break;

            case Command.EDIT:
                if (oid != 0) {
                    try {
                        overtime = PstOvertime.fetchExc(oid);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.SAVE:
                if (oid != 0) {
                    try {
                        overtime = PstOvertime.fetchExc(oid);
                    } catch (Exception exc) {
                    }
                }

                frmOvertime.requestEntityObject(overtime);
                
                Date dateReq = ControlDate.getDateTime(frmOvertime.fieldNames[frmOvertime.FRM_FIELD_REQ_DATE], request);
                overtime.setRequestDate(dateReq);


                if (frmOvertime.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                /*
                 * val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_DRAFT));
                key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT]);
                
                val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED));
                key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED]);
                
                val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_FINAL));
                key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);
                
                val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_PROCEED));
                key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_PROCEED]);                                                            
                
                val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_CLOSED));
                key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CLOSED]);                                                            
                
                val_status.add(String.valueOf(I_DocStatus.DOCUMENT_STATUS_CANCELLED));
                key_status.add(I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CANCELLED]);
                 */
                if (overtime.getRequestId() != 0) {
                    //update by satrya 2013-05-05
                    //dikarenakan ada yg tidak beres
                    //overtime.setTimeReqOt(new Date());
                    overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED);
                    if (overtime.getApprovalId() != 0) {
                        //update by satrya 2013-05-05
                        //overtime.setTimeApproveOt(new Date());
                        overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_FINAL);
                        if (overtime.getAckId() != 0) {
                            //update by satrya 2013-05-05
                            //overtime.setTimeAckOt(new Date());
                            overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_PROCEED);
                        }
                    } else {
                        overtime.setAckId(0);
                        //update by satrya 2013-05-05
                        overtime.setTimeApproveOt(null);
                        overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED);
                    }
                } else {
                    // jika belum di sign oleh requester maka semua yg proses approval setelah itu akan di set 0 ( di reset )
                    overtime.setApprovalId(0);
                    overtime.setAckId(0);
                    //update by satrya 2013-05-05
                    overtime.setTimeApproveOt(null);
                    overtime.setTimeAckOt(null);
                    if (overtime.getStatusDoc() != I_DocStatus.DOCUMENT_STATUS_CANCELLED) {
                        overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_DRAFT);
                    }
                }
                if (overtime.getOID() == 0) {    // insert
                    try {
                        if (this.overtime.getCostDepartmentId() == 0) { // jika tidak dispecify cost center , maka = department asal karyawan
                            this.overtime.setCostDepartmentId(this.overtime.getDepartmentId());
                        }                        
                        long result = pstOvertime.insertExc(this.overtime);                        
                        updateOvTimeDetailStatus(this.overtime);
                        sendEmail(overtime, 0);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode(); 
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }

                } else {                      // update
                    try {
                        long result = pstOvertime.updateExc(this.overtime);
                        updateOvTimeDetailStatus(this.overtime);      
                        String whereOvDetailStatus = PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS] + " NOT IN (" +
                        I_DocStatus.DOCUMENT_STATUS_PAID +") ";
                        if(overtime.getDoUpdateAllowence()==1){
                         PstOvertimeDetail.updateAllowanceByOvertimeID(overtime.getOID(), overtime.getAllowence(),whereOvDetailStatus);
                        }
                        if(overtime.getDoRestTimeStart()==1){
                            PstOvertimeDetail.updateRestTimeByOvertimeID(overtime.getOID(), overtime.getRestTimeStart(), overtime.getRestTimeHR(), whereOvDetailStatus);
                        }

                        if(overtime.getStatusDoc() == I_DocStatus.DOCUMENT_STATUS_CANCELLED){
                            PstOvertimeDetail.updateStatusByOvertimeID(overtime.getOID(), I_DocStatus.DOCUMENT_STATUS_CANCELLED, whereOvDetailStatus);
                        }
                                               
                        sendEmail(overtime, 1);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                 
                break;


            case Command.ASK:
                if (oid != 0) {
                    try {
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        overtime = PstOvertime.fetchExc(oid);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oid != 0) {
                    try {
                        long result = PstOvertime.deleteExc(oid);

                        if (result != 0) {
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
            default:
                if (oid != 0) {
                    try {
                        overtime = PstOvertime.fetchExc(oid);
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
    
    
    /**
     *create by satrya 2014-06-15
     * @param cmd
     * @param dtSearch
     * @param sEmployeeId
     * @param flagFromDetail : berguna untuk flag ketika klik Edit, sama dengan
     * 1= berati dari edit
     * @param oidDetailExtraSch : maksudnya jika di klik edit detail
     * @return
     */
    public int action(int cmd, Date dtSearch, String sEmployeeId, int flagFromDetail, long oidOvertimeDetail) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_UNKNOWN_ERROR;
        switch (cmd) {
            case Command.EDIT:
                if (flagFromDetail == 1) {
                    if (oidOvertimeDetail != 0) {
                        try {
                            overtime = PstOvertime.fetchExc(oidOvertimeDetail);
                        } catch (Exception exc) {
                        }

                    }
                } else {
                    if (dtSearch != null && sEmployeeId != null && sEmployeeId.length() > 0) {
                        try {
                            //mencari oidMain'nya dengan param dtSearch dan sEmployee yg dipilih
                            String whereClause = PstOvertime.fieldNames[PstOvertime.FLD_REQ_DATE] + "=\"" +Formater.formatDate(dtSearch, "yyyy-MM-dd HH:mm")+ "\" AND " + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + " IN(" + sEmployeeId + ")";
                            overtime = PstOvertime.getOvertime(0, 1, whereClause, "");
                        } catch (Exception exc) {
                            msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        }
                    }
                }
                break;

            case Command.SAVE:
                if (flagFromDetail == 1) {
                    if (oidOvertimeDetail != 0) {
                        try {
                            overtime = PstOvertime.fetchExc(oidOvertimeDetail);
                        } catch (Exception exc) {
                        }

                    }
                } else {
                    if (dtSearch != null && sEmployeeId != null && sEmployeeId.length() > 0) {
                        try {
                            //mencari oidMain'nya dengan param dtSearch dan sEmployee yg dipilih
                            String whereClause = PstOvertime.fieldNames[PstOvertime.FLD_REQ_DATE] + "=\"" +Formater.formatDate(dtSearch, "yyyy-MM-dd HH:mm")+ "\" AND " + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + " IN(" + sEmployeeId + ")";
                            overtime = PstOvertime.getOvertime(0, 1, whereClause, "");
                        } catch (Exception exc) {
                            msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        }
                    }
                }
                frmOvertime.requestEntityObject(overtime);
                if(frmOvertimeDetail!=null){
                 frmOvertimeDetail.requestEntityObjectMultiple();
                    if (frmOvertimeDetail != null && frmOvertimeDetail.getMsgFrm() != null && frmOvertimeDetail.getMsgFrm().length() > 0) {
                        msgString = frmOvertimeDetail.getMsgFrm();
                        return RSLT_FORM_INCOMPLETE;
                    }
                }
                if (frmOvertime.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (overtime.getOID() == 0) {
                    try {
                        long oid = pstOvertime.insertExc(this.overtime);

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
                        long oid = pstOvertime.updateExc(this.overtime);

                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                //masukkan detailnya
                if (frmOvertimeDetail != null) {
                    //Hashtable hashEmpMsg = new Hashtable();
                    if (frmOvertimeDetail.getvOvertime() != null && frmOvertimeDetail.getvOvertime().size() > 0) {
                        Hashtable hashEmpErorr = new Hashtable();
                        for (int idx = 0; idx < frmOvertimeDetail.getvOvertime().size(); idx++) {
                            OvertimeDetail objOvertimeDetail = (OvertimeDetail)frmOvertimeDetail.getvOvertime().get(idx);
                            objOvertimeDetail.setOvertimeId(overtime.getOID());
                            String where = "";
                            if (objOvertimeDetail != null && objOvertimeDetail.getDateFrom() != null && objOvertimeDetail.getDateTo() != null) {
                                where = "\"" + Formater.formatDate(objOvertimeDetail.getDateTo(), "yyyy-MM-dd HH:mm:ss") + "\">=" + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_FROM]
                                        + " AND "
                                        + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_DATE_TO] + " >= \"" + Formater.formatDate(objOvertimeDetail.getDateFrom(), "yyyy-MM-dd HH:mm:ss") + "\""
                                        + " AND " + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_EMPLOYEE_ID] + "=" + objOvertimeDetail.getEmployeeId();
                                if (objOvertimeDetail.getOID() != 0) {
                                    where = where + " AND " + PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_OVERTIME_DETAIL_ID] + "!=" + objOvertimeDetail.getOID();
                                }
                            }
                            //cek jika ada overlap saat itu
                            Vector listOvertimeDetail = PstOvertimeDetail.list(0, 0, where, "");
                            if (listOvertimeDetail != null && listOvertimeDetail.size() > 0) {
                                for (int x = 0; x < listOvertimeDetail.size(); x++) {
                                     OvertimeDetail objOvertimeDetails = (OvertimeDetail)listOvertimeDetail.get(x);
                                   
                                    Employee employee = new Employee();
                                    try {
                                        employee = PstEmployee.fetchExc(objOvertimeDetails.getEmployeeId());
                                    } catch (Exception exc) {
                                    }
                                    String fullName = employee.getFullName();
                                    String start = objOvertimeDetails.getDateFrom() != null ? Formater.formatDate(objOvertimeDetails.getDateFrom(), "dd MMM yyyy") : "";
                                    String end = objOvertimeDetails.getDateTo() != null ? Formater.formatDate(objOvertimeDetails.getDateTo(), "dd MMM yyyy") : "";
                                    Date newStartDate = objOvertimeDetails.getDateFrom();
                                    Date newEndDate = objOvertimeDetails.getDateTo();
                                    Date startDate = objOvertimeDetail.getDateFrom();
                                    Date endDate = objOvertimeDetail.getDateTo();
                                    //hashEmpMsg.put(employee.getOID(), fullName);
                                    if (newStartDate.after(startDate) && newStartDate.before(endDate)) {
                                        msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
                                        hashEmpErorr.put(objOvertimeDetails.getEmployeeId(), true);
                                    } else if (newEndDate.after(startDate) && newEndDate.before(endDate)) {
                                        msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
                                        hashEmpErorr.put(objOvertimeDetails.getEmployeeId(), true);
                                    } else if (startDate.after(newStartDate) && startDate.before(newEndDate)) {
                                        msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
                                        hashEmpErorr.put(objOvertimeDetails.getEmployeeId(), true);
                                    } else if (endDate.after(newStartDate) && endDate.before(newEndDate)) {
                                        msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
                                        hashEmpErorr.put(objOvertimeDetails.getEmployeeId(), true);
                                    } else if (newStartDate.equals(startDate) && newEndDate.equals(endDate)) {
                                        msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
                                        hashEmpErorr.put(objOvertimeDetails.getEmployeeId(), true);
                                    }
                                    //msgString = msgString + " Employee " + fullName + " is Overlap employee outlet date " + start +" to " + end;
                                    //hashEmpErorr.put(employeeOutlets.getEmployeeId(), true);
                                }
                                //return RSLT_FORM_OVERLAP_SCHEDULE;  
                            }

                            if (objOvertimeDetail.getOID() == 0 && hashEmpErorr.containsKey(objOvertimeDetail.getEmployeeId()) == false) {
                                try {
                                   
                                    long oid = PstOvertimeDetail.insertExc(objOvertimeDetail);
                                    String fullName = "";
                                    /*if(hashEmpMsg!=null && hashEmpMsg.size()>0 && hashEmpMsg.get(objMappingOutlet.getEmployeeId())!=null){
                                     fullName = (String)hashEmpMsg.get(objMappingOutlet.getEmployeeId());
                                     }*/
                                    Employee employee = new Employee();
                                    try {
                                        employee = PstEmployee.fetchExc(objOvertimeDetail.getEmployeeId());
                                        fullName = employee.getFullName();
                                        msgString = msgString + "<br>" + resultText[language][RSLT_OK] + " save employee " + fullName;
                                        rsCode = RSLT_OK;
                                    } catch (Exception exc) {
                                        System.out.println("Exc empLoutelet insert" + exc);
                                    }

                                } catch (DBException dbexc) {
                                    excCode = dbexc.getErrorCode();
                                    msgString = getSystemMessage(excCode);
                                    return getControlMsgId(excCode);
                                } catch (Exception exc) {
                                    msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                    return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                                }

                            } else if (objOvertimeDetail.getOID() != 0 && hashEmpErorr.containsKey(objOvertimeDetail.getEmployeeId()) == false) {
                                //maka dia update
                                try {
                                   
                                    long oid = pstOvertimeDetail.updateExc(objOvertimeDetail);
                                    String fullName = "";
                                    /*if(hashEmpMsg!=null && hashEmpMsg.size()>0 && hashEmpMsg.get(objMappingOutlet.getEmployeeId())!=null){
                                     fullName = (String)hashEmpMsg.get(objMappingOutlet.getEmployeeId());
                                     }*/
                                    Employee employee = new Employee();
                                    try {
                                        employee = PstEmployee.fetchExc(objOvertimeDetail.getEmployeeId());
                                        fullName = employee.getFullName();
                                        msgString = msgString + "<br>" + resultText[language][RSLT_OK] + " save employee " + fullName;
                                        rsCode = RSLT_OK;
                                    } catch (Exception exc) {
                                        System.out.println("Exc empLoutelet insert" + exc);
                                    }

                                }catch (Exception exc) {
                                    msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                                    return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                                }
                            }
                        }//end loop employee selected

                    }
                }
                break;

            case Command.DELETE:
                if (oidOvertimeDetail != 0) {
                    try {
                        long result = PstOvertimeDetail.deleteExc(oidOvertimeDetail);

                        if (result != 0) {
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

            default:

        }
        return rsCode;
    }


    public static void updateOvTimeDetailStatus(Overtime overtime) {
        String where = PstOvertimeDetail.fieldNames[PstOvertimeDetail.FLD_STATUS];
        switch (overtime.getStatusDoc()) {
            case I_DocStatus.DOCUMENT_STATUS_DRAFT:
                where = "";/*where + " IN (" + I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED + ", "
                        + I_DocStatus.DOCUMENT_STATUS_FINAL + ", "
                        + I_DocStatus.DOCUMENT_STATUS_PROCEED + ") ";*/

                PstOvertimeDetail.updateStatusByOvertimeID(overtime.getOID(), I_DocStatus.DOCUMENT_STATUS_DRAFT, where);
                break;
            case I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED:
                where = "";/*where + " IN (" + I_DocStatus.DOCUMENT_STATUS_DRAFT + ", "
                        + I_DocStatus.DOCUMENT_STATUS_FINAL + ", "
                        + I_DocStatus.DOCUMENT_STATUS_PROCEED + ") "*/;

                PstOvertimeDetail.updateStatusByOvertimeID(overtime.getOID(), I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED, where);
                break;
            case I_DocStatus.DOCUMENT_STATUS_FINAL:
                where = "";/* where + " IN (" + I_DocStatus.DOCUMENT_STATUS_DRAFT + ", "
                        + I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED + ", "
                        + I_DocStatus.DOCUMENT_STATUS_PROCEED + ") ";*/

                PstOvertimeDetail.updateStatusByOvertimeID(overtime.getOID(), I_DocStatus.DOCUMENT_STATUS_FINAL, where);
                break;
         ///update by satrya 2013-01-18
         //jika user memilih ke  request By,Approve By, Final By Approve secara bersamaan
         // maka di set overtime  detailnya statusnya FInal tpi mainnya sudah di set proceed
           case I_DocStatus.DOCUMENT_STATUS_PROCEED:
                where = "";/* where + " IN (" + I_DocStatus.DOCUMENT_STATUS_DRAFT + ", "
                        + I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED + ", "
                        + I_DocStatus.DOCUMENT_STATUS_PROCEED + ") ";*/

                PstOvertimeDetail.updateStatusByOvertimeID(overtime.getOID(), I_DocStatus.DOCUMENT_STATUS_FINAL, where);
                break;
            default:
                break;
        }
    }

    public String approveMultipleBy(long employeeId,  Vector<Long> vIdLeaveToBeAproval, Vector<Long> vIdLeaveToFinalAproval){        
        if( ( vIdLeaveToBeAproval==null || vIdLeaveToBeAproval.size()<1) && (vIdLeaveToFinalAproval==null || vIdLeaveToFinalAproval.size()<1) ){
          return "No overtime to be approved";
        }
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;

        for(int i=0; i < vIdLeaveToBeAproval.size();i++){
          long oid = ((Long) vIdLeaveToBeAproval.get(i)).longValue();
                try {
                    overtime = PstOvertime.fetchExc(oid);
                } catch (Exception exc) {
                }

                if (overtime.getRequestId() != 0) {
                    overtime.setApprovalId(employeeId); 
                    //update by satrya 2013-04-30
                    overtime.setTimeApproveOt(new Date());
                    overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_FINAL);                                    
                    try {
                        long result = pstOvertime.updateExc(this.overtime);
                        updateOvTimeDetailStatus(this.overtime);                        
                        sendEmail(overtime, 1);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }          
                }  
        }
        
        for(int i=0; i < vIdLeaveToFinalAproval.size();i++){
          long oid = ((Long) vIdLeaveToFinalAproval.get(i)).longValue();
                try {
                    overtime = PstOvertime.fetchExc(oid);
                } catch (Exception exc) {
                }

                if (overtime.getApprovalId() != 0) {
                    overtime.setAckId(employeeId);
                    //update by satrya 2013-04-30
                    overtime.setTimeAckOt(new Date());
                    overtime.setStatusDoc(I_DocStatus.DOCUMENT_STATUS_PROCEED);                                                    
                    try {
                        long result = pstOvertime.updateExc(this.overtime);
                        updateOvTimeDetailStatus(this.overtime);                        
                        sendEmail(overtime, 1);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }          
                }
        }
        
        
        return msgString;
    }
    
    
    public static void sendEmail(Overtime overtime, int status) {        
        String emailOn = PstSystemProperty.getValueByName("EMAIL_NOTIFICATION_ON"); //0= off 1=on        
        if (!(emailOn.contains("1"))){
          return;
        }
        
        String emailNotiftoFinalApprover = ""+PstSystemProperty.getValueByName("EMAIL_NOTIFICATION_GM"); //0= off 1=on                        
        
        I_Leave leaveConfig = null;         
        try{
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
            
        }catch (Exception e){
            System.out.println("Exception : " + e.getMessage());
            return;
        }
        
        Vector listRec = new Vector();
        
        if(overtime.getDepartmentId()!=0){
            if(overtime.getRequestId()==0){
                //listRec= PstEmployee.listEmployeeSupervisi(overtime.getDepartmentId(), 0L, PstPosition.LEVEL_SUPERVISOR, PstPosition.LEVEL_MANAGER);
            } else{
               if(overtime.getApprovalId()==0){
                listRec= leaveConfig.overtimeApprover(overtime.getOID()) ;//PstEmployee.listEmployeeSupervisi(overtime.getDepartmentId(), 0L, PstPosition.LEVEL_ASST_MANAGER, PstPosition.LEVEL_DIRECTOR);                
               } else{
                   if(overtime.getAckId()==0){
                       if(emailNotiftoFinalApprover.contains("1")){
                        listRec= leaveConfig.overtimeFinalApprover(overtime.getOID()) ;//PstEmployee.listEmployeeSupervisi(overtime.getDepartmentId(), 0L, PstPosition.LEVEL_ASST_MANAGER, PstPosition.LEVEL_DIRECTOR);                
                       }
                   } else{ // overtime is final approved
                       try{
                       Employee empReq = PstEmployee.fetchExc(overtime.getRequestId());
                       listRec.add(empReq);
                       } catch(Exception exc){                           
                           System.out.println(exc);
                       }                       
                   }
               }
            }
        }
        if(listRec==null || listRec.size()<1){
            return;
        }
        
        String harismaURL = PstSystemProperty.getValueByName("HARISMA_URL");
        if(harismaURL!=null){
            if(!harismaURL.endsWith("/login.jsp")){
                harismaURL=harismaURL+"/login.jsp";
            }
        }
        String from = PstSystemProperty.getValueByName("EMAIL_NOTIFICATION_FROM"); //"support@dimata.com"
        String host = PstSystemProperty.getValueByName("EMAIL_NOTIFICATION_HOST"); //"beagle2.webappcabaret.net";
        int port = 25;
        try{
         port=Integer.parseInt(PstSystemProperty.getValueByName("EMAIL_NOTIFICATION_PORT")); //25;
        } catch(Exception exc){            
        }
        String username = PstSystemProperty.getValueByName("EMAIL_NOTIFICATION_USERNAME"); //"support@dimata.com";
        String password = PstSystemProperty.getValueByName("EMAIL_NOTIFICATION_PASSWORD"); //"dodbest";
        //update by satrya 2013-11-04
          String attacment = "";
         boolean SSL = false;
        try{
            String sSSL = PstSystemProperty.getValueByName("EMAIL_SSL_SETTING"); 
            SSL = Boolean.parseBoolean(sSSL);
        }catch(Exception exc){
            SSL = false;
        }
        Vector<String> recipientsCC= new Vector();
        Vector<String> recipientsBCC= new Vector();
        String subject = overtime.getAckId()==0 ? "New Overtimeform " : "Final Approval ";
        String txtMessage = ( overtime.getAckId()==0 ? "New/Update": " Final Approval"  ) + " of overtimeform number:" + overtime.getOvertimeNum() + " Date:" +
                Formater.formatDate(overtime.getRequestDate(), "dd MMMM yyyy") + " please access : "+harismaURL+"?page_name=overtime.jsp&page_command="+Command.EDIT+"&data_oid="+overtime.getOID()+
                "\n sent by Dimata Harisma System";

        
        Vector<String> recx = new Vector();
        try {
            for (int i = 0; i < listRec.size() && i < 3; i++) {
                Employee req = (Employee) listRec.get(i);
                if(req.getEmailAddress()!=null && req.getEmailAddress().length()>0){
                    recx.add(req.getEmailAddress());
                }
            }

        } catch (Exception exc) {
            System.out.println(exc);
        }

        if (listRec != null && listRec.size() > 0) {
            try {// send email as a thread ..
                MailSender.postMailThread(recx, recipientsCC,
                        recipientsBCC, subject, txtMessage, from,
                        host, port, username, password, SSL,attacment,false);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }

    }

    /**
     * @return the hashCekOverlap
     */
    public Hashtable getHashCekOverlap() {
        return hashCekOverlap;
    }

    /**
     * @param hashCekOverlap the hashCekOverlap to set
     */
    public void setHashCekOverlap(Hashtable hashCekOverlap) {
        this.hashCekOverlap = hashCekOverlap;
    }
}
