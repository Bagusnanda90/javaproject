/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.attendance.mappingoutlet;

import com.dimata.harisma.entity.attendance.EmpScheduleReport;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.mappingoutlet.ExtraScheduleOutletDetail;
import com.dimata.harisma.entity.attendance.mappingoutlet.ExtraScheduleOutletDetail;
import com.dimata.harisma.entity.attendance.mappingoutlet.PstExtraScheduleOutletDetail;
import com.dimata.harisma.entity.attendance.mappingoutlet.PstExtraScheduleOutletDetail;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.system.entity.PstSystemProperty;
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
public class CtrlExtraScheduleOutletDetail extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_FORM_END_TIME_PRIOR_TO_START_TIME = 4;
    public static int RSLT_PRESENCE_IN_OUT_NULL = 5;
    public static int RSLT_OVERTIME_OVERLAP_THIS_SCHEDULE = 6;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Data Extra Schedule sudah ada", "Data tidak lengkap", "Tgl./Jam akhir lebih awal dari tgl./jam mulai", "Presence IN Atau OUT Kosong", "Extra Schedule Bentrok dengan Schedule"},
        {"Success", "Can not process", "Extra Schedule Detail exists", "Data incomplete", "End date/time prior to start date/time", "Presence IN or OUT is NULL", "Extra Schedule is Extra Schedule with this Schedule"}
    };
    private int start;
    private String msgString;
    private ExtraScheduleOutletDetail objExtraScheduleOutletDetail;
    //private ExtraScheduleOutletDetail prevObjEmployeeOutlet; 
    private PstExtraScheduleOutletDetail pstExtraScheduleOutletDetail;
    private FrmExtraScheduleOutletDetail frmExtraScheduleOutletDetail;
    int language = LANGUAGE_FOREIGN;

    public CtrlExtraScheduleOutletDetail(HttpServletRequest request) {
        msgString = "";
        objExtraScheduleOutletDetail = new ExtraScheduleOutletDetail();
        try {
            pstExtraScheduleOutletDetail = new PstExtraScheduleOutletDetail(0);
        } catch (Exception e) {;
        }
        frmExtraScheduleOutletDetail = new FrmExtraScheduleOutletDetail(request, objExtraScheduleOutletDetail);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmExtraScheduleOutletDetail.addError(frmExtraScheduleOutletDetail.FRM_EXTRA_SCHEDULE_MAPPING_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public ExtraScheduleOutletDetail getMappingOutlet() {
        return objExtraScheduleOutletDetail;
    }

    public FrmExtraScheduleOutletDetail getForm() {
        return frmExtraScheduleOutletDetail;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

//    public int action(int cmd, long oidExtraSchedule) {
//        msgString = "";
//        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
//        int rsCode = RSLT_UNKNOWN_ERROR;
//        switch (cmd) {
//            case Command.EDIT:
//                break;
//
//            case Command.SAVE:
//                frmExtraScheduleOutletDetail.requestEntityObjectMultiple();
//
//                if (frmExtraScheduleOutletDetail != null && frmExtraScheduleOutletDetail.getMsgFrm() != null && frmExtraScheduleOutletDetail.getMsgFrm().length() > 0) {
//                    msgString = frmExtraScheduleOutletDetail.getMsgFrm();
//                    return RSLT_FORM_INCOMPLETE;
//                }
//
//
//                if (frmExtraScheduleOutletDetail.getMappingExtraSchedule() != null && frmExtraScheduleOutletDetail.getMappingExtraSchedule().size() > 0) {
//                    Hashtable hashEmpErorr = new Hashtable();
//                    for (int idx = 0; idx < frmExtraScheduleOutletDetail.getMappingExtraSchedule().size(); idx++) {
//                        ExtraScheduleOutletDetail extraScheduleOutletDetail = (ExtraScheduleOutletDetail) frmExtraScheduleOutletDetail.getMappingExtraSchedule().get(idx);
//                        if (extraScheduleOutletDetail.getExtraScheduleMappingId() == 0 && oidExtraSchedule != 0) {
//                            extraScheduleOutletDetail.setExtraScheduleMappingId(oidExtraSchedule);
//                        }
//                        String where = "";
//                        if (extraScheduleOutletDetail != null && extraScheduleOutletDetail.getStartDatePlan() != null && extraScheduleOutletDetail.getEndDatePlan() != null) {
//                            where = "\"" + Formater.formatDate(extraScheduleOutletDetail.getEndDatePlan(), "yyyy-MM-dd HH:mm:ss") + "\">=" + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_START_DATE_PLAN]
//                                    + " AND "
//                                    + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_END_DATE_PLAN] + " >= \"" + Formater.formatDate(extraScheduleOutletDetail.getStartDatePlan(), "yyyy-MM-dd HH:mm:ss") + "\""
//                                    + " AND " + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EMPLOYEE_ID] + "=" + extraScheduleOutletDetail.getEmployeeId();
//                            if (extraScheduleOutletDetail.getOID() != 0) {
//                                where = where + " AND " + PstExtraScheduleOutletDetail.fieldNames[PstExtraScheduleOutletDetail.FLD_EXTRA_SCHEDULE_MAPPING_DETAIL_ID] + "!=" + extraScheduleOutletDetail.getOID();
//                            }
//                        }
//                        //cek jika ada overlap saat itu
//                        Vector listEmployeeExtraScheduleDetail = PstExtraScheduleOutletDetail.list(0, 0, where, "");
//                        if (listEmployeeExtraScheduleDetail != null && listEmployeeExtraScheduleDetail.size() > 0) {
//                            for (int x = 0; x < listEmployeeExtraScheduleDetail.size(); x++) {
//                                ExtraScheduleOutletDetail extraScheduleOutletDetails = (ExtraScheduleOutletDetail) listEmployeeExtraScheduleDetail.get(x);
//                                Employee employee = new Employee();
//                                try {
//                                    employee = PstEmployee.fetchExc(extraScheduleOutletDetails.getEmployeeId());
//                                } catch (Exception exc) {
//                                }
//                                String fullName = employee.getFullName();
//                                String start = extraScheduleOutletDetails.getStartDatePlan() != null ? Formater.formatDate(extraScheduleOutletDetails.getStartDatePlan(), "dd MMM yyyy") : "";
//                                String end = extraScheduleOutletDetails.getEndDatePlan() != null ? Formater.formatDate(extraScheduleOutletDetails.getEndDatePlan(), "dd MMM yyyy") : "";
//                                Date newStartDate = extraScheduleOutletDetails.getStartDatePlan();
//                                Date newEndDate = extraScheduleOutletDetails.getEndDatePlan();
//                                Date startDate = extraScheduleOutletDetail.getStartDatePlan();
//                                Date endDate = extraScheduleOutletDetail.getEndDatePlan();
//                                //hashEmpMsg.put(employee.getOID(), fullName);
//                                if (newStartDate.after(startDate) && newStartDate.before(endDate)) {
//                                    msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
//                                    hashEmpErorr.put(extraScheduleOutletDetails.getEmployeeId(), true);
//                                } else if (newEndDate.after(startDate) && newEndDate.before(endDate)) {
//                                    msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
//                                    hashEmpErorr.put(extraScheduleOutletDetails.getEmployeeId(), true);
//                                } else if (startDate.after(newStartDate) && startDate.before(newEndDate)) {
//                                    msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
//                                    hashEmpErorr.put(extraScheduleOutletDetails.getEmployeeId(), true);
//                                } else if (endDate.after(newStartDate) && endDate.before(newEndDate)) {
//                                    msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
//                                    hashEmpErorr.put(extraScheduleOutletDetails.getEmployeeId(), true);
//                                } else if (newStartDate.equals(startDate) && newEndDate.equals(endDate)) {
//                                    msgString = msgString + "<br>  Employee " + fullName + " is Overlap employee outlet date " + start + " to " + end;
//                                    hashEmpErorr.put(extraScheduleOutletDetails.getEmployeeId(), true);
//                                }
//                                //msgString = msgString + " Employee " + fullName + " is Overlap employee outlet date " + start +" to " + end;
//                                //hashEmpErorr.put(employeeOutlets.getEmployeeId(), true);
//                            }
//                            //return RSLT_FORM_OVERLAP_SCHEDULE;  
//                        }
//
//                        if (extraScheduleOutletDetail.getOID() == 0 && hashEmpErorr.containsKey(extraScheduleOutletDetail.getEmployeeId()) == false) {
//                            try {
//
//                                long oid = pstExtraScheduleOutletDetail.insertExc(extraScheduleOutletDetail);
//                                long oidSchedule = 0;
//
//                                String fullName = "";
//
//                                Employee employee = new Employee();
//                                try {
//                                    employee = PstEmployee.fetchExc(extraScheduleOutletDetail.getEmployeeId());
//                                    fullName = employee.getFullName();
//                                    msgString = msgString + "<br>" + resultText[language][RSLT_OK] + " save employee " + fullName;
//                                    rsCode = RSLT_OK;
//                                } catch (Exception exc) {
//                                    System.out.println("Exc empLoutelet insert" + exc);
//                                }
//
//                            } catch (DBException dbexc) {
//                                excCode = dbexc.getErrorCode();
//                                msgString = getSystemMessage(excCode);
//                                return getControlMsgId(excCode);
//                            } catch (Exception exc) {
//                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                            }
//
//                        } else if (extraScheduleOutletDetail.getOID() != 0 && hashEmpErorr.containsKey(extraScheduleOutletDetail.getEmployeeId()) == false) {
//                            //maka dia update
//                            try {
//                                //update yg lama di update
//                                long oid = pstExtraScheduleOutletDetail.update(extraScheduleOutletDetail);
//
//                            } catch (Exception exc) {
//                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//                            }
//                        }
//                    }//end loop employee selected
//
//                }
//
//                break;
//
//            case Command.DELETE:
//                break;
//
//            default:
//
//        }
//        return rsCode;
//    }
}
