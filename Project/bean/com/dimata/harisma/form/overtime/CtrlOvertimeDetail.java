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
import com.dimata.harisma.entity.attendance.DpStockManagement;
import com.dimata.harisma.entity.attendance.EmpScheduleReport;
import com.dimata.harisma.entity.attendance.PstDpStockManagement;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;

// import qdep
import com.dimata.qdep.db.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.system.*;

// import project
import com.dimata.harisma.entity.overtime.*;
import com.dimata.harisma.form.overtime.*;
import com.dimata.harisma.session.payroll.SessOvertime;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.system.entity.PstSystemProperty;

/**
 *
 * @author Wiweka
 */
public class CtrlOvertimeDetail extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_FORM_END_TIME_PRIOR_TO_START_TIME = 4;
    public static int RSLT_PRESENCE_IN_OUT_NULL = 5;
    public static int RSLT_OVERTIME_OVERLAP_THIS_SCHEDULE=6;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Data overtime sudah ada", "Data tidak lengkap", "Tgl./Jam akhir lebih awal dari tgl./jam mulai", "Presence IN Atau OUT Kosong","Overtime Bentrok dengan Schedule"},
        {"Success", "Can not process", "Overtime Detail exists", "Data incomplete", "End date/time prior to start date/time", "Presence IN or OUT is NULL","Overtime is Overlap with this Schedule"}
    };
    private int start;
    private String msgString;
    private int language;
    private OvertimeDetail overtimeDetail;
    //update by satrya 2012-12-20
    private Overtime overtime;
    private PstOvertimeDetail pstOvertimeDetail;
    private FrmOvertimeDetail frmOvertimeDetail;
    private HttpServletRequest req;

    public CtrlOvertimeDetail(HttpServletRequest request) {
        msgString = "";
        language = LANGUAGE_FOREIGN;
        overtimeDetail = new OvertimeDetail();

        try {
            pstOvertimeDetail = new PstOvertimeDetail(0);
        } catch (Exception e) {
        }
        req = request;
        frmOvertimeDetail = new FrmOvertimeDetail(request, overtimeDetail);
    }

    public int getStart() {
        return start;
    }

    public String getMessage() {
        return msgString;
    }

    public OvertimeDetail getOvertimeDetail() {
        return overtimeDetail;
    }

    public FrmOvertimeDetail getForm() {
        return frmOvertimeDetail;
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
                frmOvertimeDetail.addError(frmOvertimeDetail.FRM_FIELD_OVERTIME_DETAIL_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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
    
    public int action(int cmd, long oidOvertimeDetail, long oidOvertime, HttpServletRequest request) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;

        switch (cmd) {
            case Command.ADD:
                break;

            case Command.EDIT:
                if (oidOvertimeDetail != 0) {
                    try {
                        overtimeDetail = PstOvertimeDetail.fetchExc(oidOvertimeDetail);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.SAVE:
                if (oidOvertimeDetail != 0) {
                    try {
                        overtimeDetail = PstOvertimeDetail.fetchExc(oidOvertimeDetail);
                    } catch (Exception exc) {
                        System.out.println("Exception fetchExc(oidOvertimeDetail)" + exc);
                    }
                }
                overtimeDetail.setOID(oidOvertimeDetail);
                frmOvertimeDetail.requestEntityObject(overtimeDetail);
                if (overtimeDetail.getOvertimeId() == 0 && oidOvertime != 0) {
                    overtimeDetail.setOvertimeId(oidOvertime);
                }
                if (overtimeDetail.getDateFrom() == null) {
                    Date dateStart = ControlDate.getDateTime(frmOvertimeDetail.fieldNames[frmOvertimeDetail.FRM_FIELD_DATE_FROM], request);
                    if(dateStart!=null){
                    overtimeDetail.setDateFrom(dateStart);
                    }
                }
                if (overtimeDetail.getDateTo() == null) {
                  
                    Date dateEnd = ControlDate.getDateTime(frmOvertimeDetail.fieldNames[frmOvertimeDetail.FRM_FIELD_DATE_TO], request);
                    
                      if(dateEnd!=null){
                    overtimeDetail.setDateTo(dateEnd);
                      }
                }

                /*untuk date hour start
                 Date date_start_Time = ControlDate.getDate(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM], request);
                 Date hour_start_Time = ControlDate.getTime(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM], request);


                 int y = date_start_Time.getYear();
                 int M = date_start_Time.getMonth();
                 int d = date_start_Time.getDate();
                 int h = hour_start_Time.getHours();
                 int m = hour_start_Time.getMinutes();
                 Date startDateTime = new Date(y, M, d, h, m);



                 //untuk date hour end
                 Date date_End_Time = ControlDate.getDate(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO], request);
                 Date hour_end_Time = ControlDate.getTime(FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO], request);


                 int y1 = date_End_Time.getYear();
                 int M1 = date_End_Time.getMonth();
                 int d1 = date_End_Time.getDate();
                 int h1 = hour_end_Time.getHours();
                 int m1 = hour_end_Time.getMinutes();
                 Date startEndTime = new Date(y1, M1, d1, h1, m1);*/

                if (frmOvertimeDetail.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }
                if (overtimeDetail != null && overtimeDetail.getDateFrom() != null && overtimeDetail.getDateTo() != null) {
                    if (overtimeDetail.getStatus() != I_DocStatus.DOCUMENT_STATUS_CANCELLED) {
                        Vector ovLst = PstOvertimeDetail.listOvertimeOverlapVer3(overtimeDetail.getOID(), 0, 100, 0, "", overtimeDetail.getDateFrom(), overtimeDetail.getDateTo(),
                                0, "", overtimeDetail.getEmployeeId(), "");
                        if (ovLst != null && ovLst.size() > 0) {
                            msgString = resultText[language][RSLT_EST_CODE_EXIST] + " " + overtimeDetail.getName() + " please check other Overtime form on the same range:";
                            for (int idx = 0; idx < ovLst.size(); idx++) {
                                OvertimeDetail ovDetail = (OvertimeDetail) ovLst.get(idx);
                                msgString = msgString + " <a href=\"javascript:openOvertimeOverlap(\'" + ovDetail.getOvertimeId() + "\');\">" + ovDetail.getOvt_doc_nr() + "</a> ; ";
                            }
                            return RSLT_EST_CODE_EXIST;
                        }
                    }
                } else {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }
                if ((this.overtimeDetail == null || this.overtimeDetail.getDateFrom() == null || this.overtimeDetail.getDateTo() == null
                        || this.overtimeDetail.getDateFrom().getTime() >= this.overtimeDetail.getDateTo().getTime()) && overtimeDetail.getStatus() != I_DocStatus.DOCUMENT_STATUS_CANCELLED) {
                    msgString = resultText[1][RSLT_FORM_END_TIME_PRIOR_TO_START_TIME];
                    return RSLT_FORM_END_TIME_PRIOR_TO_START_TIME;
                }
                
                //update by satrya 2013-0904
               // Hashtable hashCekOverlap = new Hashtable();
                Vector vListOverlapSchedule = PstEmpSchedule.getListSchedule(overtimeDetail.getDateFrom(), overtimeDetail.getDateTo(), overtimeDetail.getEmployeeId());
                   EmpScheduleReport empScheduleReport = new EmpScheduleReport();
                  if(vListOverlapSchedule!=null && vListOverlapSchedule.size()>0){
                      empScheduleReport =(EmpScheduleReport)vListOverlapSchedule.get(0);
                  }
                  long oidDayOff = 0;         
                    try{
                        oidDayOff = Long.parseLong(PstSystemProperty.getValueByName("OID_DAY_OFF"));
                    }catch(Exception ex){
                        System.out.println("Execption OID_DAY_OFF: " + ex.toString());
                    }
                   Hashtable hasOverlapOT = PstOvertime.isScheduleOverlapOvertime(overtimeDetail.getEmployeeId(), vListOverlapSchedule, oidDayOff, overtimeDetail.getDateFrom(),  overtimeDetail.getDateTo(), empScheduleReport); 
                   if(hasOverlapOT!=null && hasOverlapOT.get("true")!=null){
                       String sOidSch = (String)hasOverlapOT.get("true");
                      String sOidSchx = sOidSch!=null?sOidSch.split("_")[0]:"0";  
                      
                       long oidSch= Long.parseLong(sOidSchx);
                       ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                     if(oidSch!=0){
                        try{
                         scheduleSymbol = PstScheduleSymbol.fetchExc(oidSch); 
                        }catch(Exception exc){
                        
                        }
                     }
                       
                    String sOidEmp = (String)hasOverlapOT.get("true");
                    String sOidEmpx = sOidEmp!=null?sOidEmp.split("_")[1]:"0";   
                    long oidEmp= Long.parseLong(sOidEmpx);
                    if(oidEmp!=0 && oidSch!=0){ 
                      //hashCekOverlap.put(oidEmp, scheduleSymbol);
                       try{
                        scheduleSymbol = PstScheduleSymbol.fetchExc(oidSch); 
                       }catch(Exception exc){
                       
                       }
                         int iErrCode = CtrlOvertimeDetail.RSLT_OVERTIME_OVERLAP_THIS_SCHEDULE ;
                         msgString =   CtrlOvertimeDetail.resultText[CtrlOvertimeDetail.LANGUAGE_FOREIGN][iErrCode] +" "+ (scheduleSymbol.getSymbol()!=null ? scheduleSymbol.getSymbol() :"") + " = (" + (scheduleSymbol.getTimeIn()!=null? Formater.formatDate(scheduleSymbol.getTimeIn(), " HH:mm "):"")+" : "+(scheduleSymbol.getTimeOut()!=null ? Formater.formatDate(scheduleSymbol.getTimeOut(), " HH:mm "):"")+")";
                        return RSLT_FORM_INCOMPLETE;
                    }
                 }
                
                if (overtimeDetail.getOID() == 0) {    // insert
                    try {
                        long result = pstOvertimeDetail.insertExc(this.overtimeDetail);
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
                        long result = pstOvertimeDetail.updateExc(this.overtimeDetail);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;


            case Command.ASK:
                if (oidOvertimeDetail != 0) {
                    try {
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        overtimeDetail = PstOvertimeDetail.fetchExc(oidOvertimeDetail);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
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
        }

        return rsCode;
    }

    public int actionManual(int cmd, long oidOvertimeDetail, float minOvertimeHour, HttpServletRequest request,String userIsLogin) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        boolean reloadOvertimeIndexMap = true;
  
        I_Leave leaveConfig = null;

        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception LEAVE_CONFIG: " + e.getMessage());
        }
        switch (cmd) {

            case Command.SAVE:
                if (oidOvertimeDetail != 0) {
                    try {
                        overtimeDetail = PstOvertimeDetail.fetchExc(oidOvertimeDetail);
                    } catch (Exception exc) {
                        System.out.println("Exception fetchExc(oidOvertimeDetail)" + exc);
                    }
                }
                overtimeDetail.setOID(oidOvertimeDetail);
                frmOvertimeDetail.requestEntityObjectManualy(overtimeDetail);
                //update by satrya 2012-12-20
                if(overtimeDetail.getOvertimeId()!=0){
                  try{
                   overtime = PstOvertime.fetchExc(overtimeDetail.getOvertimeId()); 
                  }catch(Exception ex){
                      System.out.println("Exception fetchExc(overtimeDetail.getOvertimeId()"+ ex);
                  }
                }
                if (overtimeDetail.getDateFrom() == null) {
                    Date dateStart = ControlDate.getDateTime(frmOvertimeDetail.fieldNames[frmOvertimeDetail.FRM_FIELD_DATE_FROM], request);
                    overtimeDetail.setDateFrom(dateStart);
                }
                if (overtimeDetail.getDateTo() == null) {
                    Date dateEnd = ControlDate.getDateTime(frmOvertimeDetail.fieldNames[frmOvertimeDetail.FRM_FIELD_DATE_TO], request);
                    overtimeDetail.setDateTo(dateEnd);
                }


                /* if(frmOvertimeDetail.errorSize() > 0) {
                 msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                 return RSLT_FORM_INCOMPLETE ;
                 }*/
                if (overtimeDetail != null && overtimeDetail.getDateFrom() != null && overtimeDetail.getDateTo() != null) {
                    if (overtimeDetail.getStatus() != I_DocStatus.DOCUMENT_STATUS_CANCELLED) {
                        Vector ovLst = PstOvertimeDetail.listOvertimeOverlap(overtimeDetail.getOID(), 0, 100, 0, "", overtimeDetail.getDateFrom(), overtimeDetail.getDateTo(),
                                0, "", overtimeDetail.getEmployeeId(), "");
                        if (ovLst != null && ovLst.size() > 0) {
                            msgString = resultText[language][RSLT_EST_CODE_EXIST] + " " + overtimeDetail.getName() + " please check other Overtime form on the same range:";
                            for (int idx = 0; idx < ovLst.size(); idx++) {
                                OvertimeDetail ovDetail = (OvertimeDetail) ovLst.get(idx);
                                msgString = msgString + " <a href=\"javascript:openOvertimeOverlap(\'" + ovDetail.getOvertimeId() + "\');\">" + ovDetail.getOvt_doc_nr() + "</a> ; ";
                            }
                            return RSLT_EST_CODE_EXIST;
                        }
                    }
                } else {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }
                if ((this.overtimeDetail == null || this.overtimeDetail.getDateFrom() == null || this.overtimeDetail.getDateTo() == null
                        || this.overtimeDetail.getDateFrom().getTime() >= this.overtimeDetail.getDateTo().getTime()) && overtimeDetail.getStatus() != I_DocStatus.DOCUMENT_STATUS_CANCELLED) {
                    msgString = resultText[1][RSLT_FORM_END_TIME_PRIOR_TO_START_TIME];
                    return RSLT_FORM_END_TIME_PRIOR_TO_START_TIME;
                }

                if (overtimeDetail.getOID() == 0) {    // insert
                    try {
                        long result = pstOvertimeDetail.insertExc(this.overtimeDetail);
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
                        long result = pstOvertimeDetail.updateExc(this.overtimeDetail);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                ///di analisa
                if (overtimeDetail.getRealDateFrom() != null && overtimeDetail.getRealDateTo() != null) {
                    PstOvertimeDetail.setRealTime(overtimeDetail, 1000 * 60 * 30, 1000 * 60 * 30, reloadOvertimeIndexMap, minOvertimeHour,overtime.getStatusDoc()); // normal early and late  = 30 menit
                    try{
                        String Errmsg=""; 
                       ///update by satrya 2013-02-01
                        //dan dia langsung menganalisa apakah yg di pilih itu
                       if(overtimeDetail.getPaidBy()==OvertimeDetail.PAID_BY_SALARY){  
                        SessOvertime.calcOvTmIndex(overtimeDetail, reloadOvertimeIndexMap, minOvertimeHour); 
                        DpStockManagement dpStock = new DpStockManagement(); 
                        dpStock.setEmployeeId(overtimeDetail.getEmployeeId());
                        // kenapa di pakai empTime.getOID() supaya  spesific masing" overtime detail dp_stoc yg di generate,di karenakan jika memakai empTime.GetPeriodId , jika karyawan tersebut OT di period yg sama maka nantinya bisa terhapus
                        dpStock.setLeavePeriodeId(overtimeDetail.getOID());
                        dpStock.setDtOwningDate(overtimeDetail.getRealDateFrom());
                        long oid =PstDpStockManagement.deleteByPeriodId(dpStock);
                        //update by satrya 2012-12-20
                        if(oid==-1){
                            Errmsg = Errmsg +"<br>"+  "can't update paid by to salary, because the DP have been used "+overtimeDetail.getEmployee_num();
                            overtimeDetail.setPaidBy(OvertimeDetail.PAID_BY_DAY_OFF);
                        }
                     }else{
                         //empTime.setTot_Idx(0);// dibayar dengan day off
                         SessOvertime.calcOvTmDayOff(overtimeDetail, reloadOvertimeIndexMap, minOvertimeHour); 
                         //insert DP
                         if((overtimeDetail.getStatus()==I_DocStatus.DOCUMENT_STATUS_FINAL || overtimeDetail.getStatus()==I_DocStatus.DOCUMENT_STATUS_PROCEED) 
                                 && overtimeDetail.getRealDateFrom()!=null && overtimeDetail.getRealDateTo()!=null ){                                                                             
                             DpStockManagement dpStock = new DpStockManagement(); 
                             dpStock.setDtOwningDate(overtimeDetail.getRealDateFrom());
                             dpStock.setDtExpiredDate(new Date(overtimeDetail.getRealDateFrom().getTime()+ ((30L*24L*60L*60L*1000L)*(long)leaveConfig.getDpValidity(leaveConfig.getStrLevels()[0]) )));
                             dpStock.setDtStartDate(overtimeDetail.getRealDateFrom());
                             dpStock.setEmployeeId(overtimeDetail.getEmployeeId());
                                // kenapa di pakai empTime.getOID() supaya  spesific masing" overtime detail dp_stoc yg di generate,di karenakan jika memakai empTime.GetPeriodId , jika karyawan tersebut OT di period yg sama maka nantinya bisa terhapus
                             dpStock.setLeavePeriodeId(overtimeDetail.getOID());                                                                      
                             dpStock.setQtyResidue((float)(overtimeDetail.getTot_Idx()/8f )); //empTime.getNetDuration()/8f));
                             dpStock.setiDpQty((float)(overtimeDetail.getTot_Idx()/8f)); // empTime.getNetDuration()/8f));
                             dpStock.setQtyUsed(0f); 
                             dpStock.setStNote("Dp generated from overtime by "+ userIsLogin.toLowerCase());
                             dpStock.setToBeTaken(0f);
                             if(overtimeDetail.getTot_Idx()>0){
                                 //update by satrya 2012-12-20
                                   if(overtimeDetail.getStatus()== I_DocStatus.DOCUMENT_STATUS_PROCEED){
                                        PstDpStockManagement.insertOrUpdateByPeriodId(dpStock);
                                   }
                             }else{
                               long oid = PstDpStockManagement.deleteByPeriodId(dpStock); 
                                //update by satrya 2012-12-20
                                /*if(oid==-1){
                                    msgStr = msgStr +"<br>"+ " can't update paid by to salary, because the DP have been used "+empTime.getEmployee_num();
                                     empTime.setPaidBy(OvertimeDetail.PAID_BY_DAY_OFF);
                                }*/
                             }
                         }
                     }
                        PstOvertimeDetail.updateExc(overtimeDetail);
                        reloadOvertimeIndexMap = false;          
                    }catch(Exception ex){
                        System.out.println("Exception"+ex);
                    }
                } else {
                    //return RSLT_PRESENCE_IN_OUT_NULL;
                    if ((overtimeDetail.getRealDateFrom() == null && overtimeDetail.getRealDateTo() == null)) {
                        if (overtimeDetail.getStatus() == I_DocStatus.DOCUMENT_STATUS_PROCEED) {
                            overtimeDetail.setStatus(I_DocStatus.DOCUMENT_STATUS_FINAL);
                        }
                        
                        overtimeDetail.setDuration(0);
                        try {
                            PstOvertimeDetail.updateExc(overtimeDetail);
                       //update by satrya 2013-02-01
                       //jika user merubah realDateForm'nya null maka di delete juga nilai dari DP dan salary
                        if(overtimeDetail.getPaidBy()==OvertimeDetail.PAID_BY_DAY_OFF){
                             DpStockManagement dpStock = new DpStockManagement(); 
                                dpStock.setEmployeeId(overtimeDetail.getEmployeeId());
                                dpStock.setLeavePeriodeId(overtimeDetail.getOID());                                                                       
                                dpStock.setDtOwningDate(overtimeDetail.getRealDateFrom());
                                long oid =PstDpStockManagement.deleteByPeriodId(dpStock);
                        }
                        
                        } catch (Exception exc) {
                            System.out.println("Exception" + exc);
                        }
                    }
                }
                break;

        }

        return rsCode;
    }
}
