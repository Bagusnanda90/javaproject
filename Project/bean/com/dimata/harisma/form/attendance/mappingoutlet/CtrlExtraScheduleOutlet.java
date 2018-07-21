/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.attendance.mappingoutlet;

import com.dimata.harisma.entity.attendance.mappingoutlet.ExtraScheduleOutlet;
import com.dimata.harisma.entity.attendance.mappingoutlet.ExtraScheduleOutletDetail;
import com.dimata.harisma.entity.attendance.mappingoutlet.PstExtraScheduleOutlet;
import com.dimata.harisma.entity.attendance.mappingoutlet.PstExtraScheduleOutletDetail;
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
public class CtrlExtraScheduleOutlet extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_FORM_OVERLAP_SCHEDULE = 4;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap", " schedule sama"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete", " Employee is Overlap schedule"}
    };
    private int start;
    private String msgString;
    private ExtraScheduleOutlet objExtraScheduleOutlet;
    //private ExtraScheduleOutlet prevObjEmployeeOutlet; 
    private PstExtraScheduleOutlet pstExtraScheduleOutlet;
    private FrmExtraScheduleOutlet frmExtraScheduleOutlet;
    private ExtraScheduleOutletDetail objExtraScheduleOutletDetail;
    private PstExtraScheduleOutletDetail pstExtraScheduleOutletDetail;
    private FrmExtraScheduleOutletDetail frmExtraScheduleOutletDetail;
    private Hashtable hashCekOverlap;
    int language = LANGUAGE_FOREIGN;

    public CtrlExtraScheduleOutlet(HttpServletRequest request) {
        msgString = "";
        objExtraScheduleOutlet = new ExtraScheduleOutlet();
        objExtraScheduleOutletDetail = new ExtraScheduleOutletDetail();
        try {
            pstExtraScheduleOutlet = new PstExtraScheduleOutlet(0);
            pstExtraScheduleOutletDetail = new PstExtraScheduleOutletDetail(0);
        } catch (Exception e) {;
        }
        frmExtraScheduleOutlet = new FrmExtraScheduleOutlet(request, objExtraScheduleOutlet);
        frmExtraScheduleOutletDetail = new FrmExtraScheduleOutletDetail(request, objExtraScheduleOutletDetail);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmExtraScheduleOutlet.addError(frmExtraScheduleOutlet.FRM_EXTRA_SCHEDULE_MAPPING_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public ExtraScheduleOutlet getMappingOutlet() {
        return objExtraScheduleOutlet;
    }

    public FrmExtraScheduleOutlet getForm() {
        return frmExtraScheduleOutlet;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    /**
     *
     * @param cmd
     * @param dtSearch
     * @param sEmployeeId
     * @param flagFromDetail : berguna untuk flag ketika klik Edit, sama dengan
     * 1= berati dari edit
     * @param oidDetailExtraSch : maksudnya jika di klik edit detail
     * @return
     */
    public int action(int cmd, Date dtSearch, String sEmployeeId, int flagFromDetail, long oidDetailExtraSch) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_UNKNOWN_ERROR;
        switch (cmd) {
            case Command.EDIT:
                if (flagFromDetail == 1) {
                    if (oidDetailExtraSch != 0) {
                        try {
                            objExtraScheduleOutlet = PstExtraScheduleOutlet.fetchExc(oidDetailExtraSch);
                        } catch (Exception exc) {
                        }

                    }
                } else {
                    if (dtSearch != null && sEmployeeId != null && sEmployeeId.length() > 0) {
                        try {
                            //mencari oidMain'nya dengan param dtSearch dan sEmployee yg dipilih
                            String whereClause = PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_REQUEST_DATE_EXTRA_SCHEDULE] + "=\"" +Formater.formatDate(dtSearch, "yyyy-MM-dd HH:mm")+ "\" AND " + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EMPLOYEE_ID] + " IN(" + sEmployeeId + ")";
                            objExtraScheduleOutlet = PstExtraScheduleOutlet.getExtraSchedule(0, 1, whereClause, "");
                        } catch (Exception exc) {
                            msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        }
                    }
                }
                break;

            case Command.SAVE:
                if (flagFromDetail == 1) {
                    if (oidDetailExtraSch != 0) {
                        try {
                            objExtraScheduleOutlet = PstExtraScheduleOutlet.fetchExc(oidDetailExtraSch);
                        } catch (Exception exc) {
                        }

                    }
                } else {
                    if (dtSearch != null && sEmployeeId != null && sEmployeeId.length() > 0) {
                        try {
                            //mencari oidMain'nya dengan param dtSearch dan sEmployee yg dipilih
                            String whereClause = PstExtraScheduleOutlet.fieldNames[PstExtraScheduleOutlet.FLD_REQUEST_DATE_EXTRA_SCHEDULE] + "=\"" + Formater.formatDate(dtSearch, "yyyy-MM-dd HH:mm") + "\" AND " + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EMPLOYEE_ID] + " IN(" + sEmployeeId + ")";
                            objExtraScheduleOutlet = PstExtraScheduleOutlet.getExtraSchedule(0, 1, whereClause, "");
                        } catch (Exception exc) {
                            msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        }
                    }
                }
                frmExtraScheduleOutlet.requestEntityObject(objExtraScheduleOutlet);
                if(frmExtraScheduleOutletDetail!=null){
                frmExtraScheduleOutletDetail.requestEntityObjectMultiple();
                    if (frmExtraScheduleOutletDetail != null && frmExtraScheduleOutletDetail.getMsgFrm() != null && frmExtraScheduleOutletDetail.getMsgFrm().length() > 0) {
                        msgString = frmExtraScheduleOutletDetail.getMsgFrm();
                        return RSLT_FORM_INCOMPLETE;
                    }
                }
                if (frmExtraScheduleOutlet.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (objExtraScheduleOutlet.getOID() == 0) {
                    try {
                        long oid = pstExtraScheduleOutlet.insertExc(this.objExtraScheduleOutlet);

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
                        long oid = pstExtraScheduleOutlet.updateExc(this.objExtraScheduleOutlet);

                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                //masukkan detailnya
                if (frmExtraScheduleOutletDetail != null) {
                    

                    //Hashtable hashEmpMsg = new Hashtable();
                    if (frmExtraScheduleOutletDetail.getMappingExtraSchedule() != null && frmExtraScheduleOutletDetail.getMappingExtraSchedule().size() > 0) {
                        Hashtable hashEmpErorr = new Hashtable();
                        for (int idx = 0; idx < frmExtraScheduleOutletDetail.getMappingExtraSchedule().size(); idx++) {
                            ExtraScheduleOutletDetail objExtraScheduleOutletDetail = (ExtraScheduleOutletDetail)frmExtraScheduleOutletDetail.getMappingExtraSchedule().get(idx);
                            objExtraScheduleOutletDetail.setExtraScheduleMappingId(objExtraScheduleOutlet.getOID());
                            String where = "";
                            if (objExtraScheduleOutletDetail != null && objExtraScheduleOutletDetail.getStartDatePlan() != null && objExtraScheduleOutletDetail.getEndDatePlan() != null) {
                                where = "\"" + Formater.formatDate(objExtraScheduleOutletDetail.getEndDatePlan(), "yyyy-MM-dd HH:mm:ss") + "\">=" + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_START_DATE_PLAN]
                                        + " AND "
                                        + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_END_DATE_PLAN] + " >= \"" + Formater.formatDate(objExtraScheduleOutletDetail.getStartDatePlan(), "yyyy-MM-dd HH:mm:ss") + "\""
                                        + " AND " + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EMPLOYEE_ID] + "=" + objExtraScheduleOutletDetail.getEmployeeId();
                                if (objExtraScheduleOutletDetail.getOID() != 0) {
                                    where = where + " AND " + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EXTRA_SCHEDULE_MAPPING_DETAIL_ID] + "!=" + objExtraScheduleOutletDetail.getOID();
                                }
                            }
                            //cek jika ada overlap saat itu
                            Vector listExtraScheduleDetail = PstExtraScheduleOutletDetail.list(0, 0, where, "");
                            if (listExtraScheduleDetail != null && listExtraScheduleDetail.size() > 0) {
                                for (int x = 0; x < listExtraScheduleDetail.size(); x++) {
                                     ExtraScheduleOutletDetail objExtraScheduleOutletDetails = (ExtraScheduleOutletDetail)listExtraScheduleDetail.get(x);
                                   
                                    Employee employee = new Employee();
                                    try {
                                        employee = PstEmployee.fetchExc(objExtraScheduleOutletDetails.getEmployeeId());
                                    } catch (Exception exc) {
                                    }
                                    String fullName = employee.getFullName();
                                    String start = objExtraScheduleOutletDetails.getStartDatePlan() != null ? Formater.formatDate(objExtraScheduleOutletDetails.getStartDatePlan(), "dd MMM yyyy") : "";
                                    String end = objExtraScheduleOutletDetails.getEndDatePlan() != null ? Formater.formatDate(objExtraScheduleOutletDetails.getEndDatePlan(), "dd MMM yyyy") : "";
                                    Date newStartDate = objExtraScheduleOutletDetails.getStartDatePlan();
                                    Date newEndDate = objExtraScheduleOutletDetails.getEndDatePlan();
                                    Date startDate = objExtraScheduleOutletDetail.getStartDatePlan();
                                    Date endDate = objExtraScheduleOutletDetail.getEndDatePlan();
                                    //hashEmpMsg.put(employee.getOID(), fullName);
                                    if (newStartDate.after(startDate) && newStartDate.before(endDate)) {
                                        msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
                                        hashEmpErorr.put(objExtraScheduleOutletDetails.getEmployeeId(), true);
                                    } else if (newEndDate.after(startDate) && newEndDate.before(endDate)) {
                                        msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
                                        hashEmpErorr.put(objExtraScheduleOutletDetails.getEmployeeId(), true);
                                    } else if (startDate.after(newStartDate) && startDate.before(newEndDate)) {
                                        msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
                                        hashEmpErorr.put(objExtraScheduleOutletDetails.getEmployeeId(), true);
                                    } else if (endDate.after(newStartDate) && endDate.before(newEndDate)) {
                                        msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
                                        hashEmpErorr.put(objExtraScheduleOutletDetails.getEmployeeId(), true);
                                    } else if (newStartDate.equals(startDate) && newEndDate.equals(endDate)) {
                                        msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
                                        hashEmpErorr.put(objExtraScheduleOutletDetails.getEmployeeId(), true);
                                    }
                                    //msgString = msgString + " Employee " + fullName + " is Overlap employee outlet date " + start +" to " + end;
                                    //hashEmpErorr.put(employeeOutlets.getEmployeeId(), true);
                                }
                                //return RSLT_FORM_OVERLAP_SCHEDULE;  
                            }

                            if (objExtraScheduleOutletDetail.getOID() == 0 && hashEmpErorr.containsKey(objExtraScheduleOutletDetail.getEmployeeId()) == false) {
                                try {
                                   
                                    long oid = pstExtraScheduleOutletDetail.insertExc(objExtraScheduleOutletDetail);
                                    String fullName = "";
                                    /*if(hashEmpMsg!=null && hashEmpMsg.size()>0 && hashEmpMsg.get(objMappingOutlet.getEmployeeId())!=null){
                                     fullName = (String)hashEmpMsg.get(objMappingOutlet.getEmployeeId());
                                     }*/
                                    Employee employee = new Employee();
                                    try {
                                        employee = PstEmployee.fetchExc(objExtraScheduleOutletDetail.getEmployeeId());
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

                            } else if (objExtraScheduleOutletDetail.getOID() != 0 && hashEmpErorr.containsKey(objExtraScheduleOutletDetail.getEmployeeId()) == false) {
                                //maka dia update
                                try {
                                   
                                    long oid = pstExtraScheduleOutletDetail.update(objExtraScheduleOutletDetail);
                                    String fullName = "";
                                    /*if(hashEmpMsg!=null && hashEmpMsg.size()>0 && hashEmpMsg.get(objMappingOutlet.getEmployeeId())!=null){
                                     fullName = (String)hashEmpMsg.get(objMappingOutlet.getEmployeeId());
                                     }*/
                                    Employee employee = new Employee();
                                    try {
                                        employee = PstEmployee.fetchExc(objExtraScheduleOutletDetail.getEmployeeId());
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
                if (oidDetailExtraSch != 0) {
                    try {
                        long result = PstExtraScheduleOutletDetail.deleteExc(oidDetailExtraSch);

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
