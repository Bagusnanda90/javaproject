package com.dimata.harisma.form.attendance;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
// import dimata package
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
// import harisma package
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.session.attendance.SessLeaveManagement;
import com.dimata.harisma.session.leave.LeaveConfigKTI;
import com.dimata.harisma.session.leave.RepItemLeaveAndDp;
import com.dimata.harisma.session.leave.SessLeaveApp;
import com.dimata.harisma.session.leave.SessLeaveApplication;
import com.dimata.system.entity.PstSystemProperty;

/**
 *
 * @author Roy Andika
 */
public class CtrlDpStockTaken extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_FRM_TAKEN_BEFORE_EXPIRED_DATE = 4;
    public static int RSLT_FRM_ELIGBLE_MINUS = 5;
    public static int RSLT_FRM_DATE_IN_RANGE = 6;
     public static int RSLT_FRM_INSERT_DATA = 8;
      public static int RSLT_FRM_UPDATE_DATA = 9;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap", "Taken harus sebelum expired date", "EligbleDay tidak boleh minus", "cuti yang di request sudah ada","Menambah data Dp","Ubah Data Dp"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete", "Taken date must be before expired date", "EligbleDay dosn't minus", "The are overlapping leave request, please check again","Insert Data Dp","Update Data Dp"}
    };
    private int start;
    private String msgString;
    private DpStockTaken dpStockTaken;
    private PstDpStockTaken pstDpStockTaken;
    private FrmDpStockTaken frmDpStockTaken;
    int language = LANGUAGE_FOREIGN;
    private float dpAvailableDays = 1.0f;

    public CtrlDpStockTaken(HttpServletRequest request) {
        msgString = "";
        dpStockTaken = new DpStockTaken();
        try {
            pstDpStockTaken = new PstDpStockTaken(0);
        } catch (Exception e) {;
        }
        frmDpStockTaken = new FrmDpStockTaken(request, dpStockTaken);
    }

    public CtrlDpStockTaken() {
        msgString = "";
        dpStockTaken = new DpStockTaken();
        try {
            pstDpStockTaken = new PstDpStockTaken(0);
        } catch (Exception e) {;
        }
        frmDpStockTaken = new FrmDpStockTaken();
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmDpStockTaken.addError(frmDpStockTaken.FRM_FIELD_DP_STOCK_TAKEN_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public DpStockTaken getDpStockTaken() {
        return dpStockTaken;
    }

    public FrmDpStockTaken getForm() {
        return frmDpStockTaken;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidLeave) {
        return action(cmd, oidLeave, new Vector());
    }

    public int action(int cmd, long oidLeave, Vector listal) {
        msgString = "";
        DpStockManagement dpStockManagement = new DpStockManagement();
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        float eligbleDay = 0;

        /* Untuk menghandle agar stock al tidak minus jika tidak diperbolehkan mengambil cuti dalam kondisi stock minus*/
        Vector listDPTakenFinishDate = null;
        Date chkDateTaken = null;
        Date chkDateFinish = null;
        I_Leave leaveConfig = null;

        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception LEAVE_CONFIG: " + e.getMessage());
        }
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                float dpTakenPrev = 0.0f;
                if (oidLeave != 0) {
                    try {
                        dpStockTaken = PstDpStockTaken.fetchExc(oidLeave);
                        dpTakenPrev = dpStockTaken.getTakenQty();
                    } catch (Exception exc) {
                        System.out.println("Exception fetchExc(oidLeave)" + exc);
                    }
                }

                frmDpStockTaken.requestEntityObject(dpStockTaken);

                if (frmDpStockTaken.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                try {
                    if (dpStockTaken.getDpStockId() != 0) {
                        dpStockManagement = PstDpStockManagement.fetchExc(dpStockTaken.getDpStockId());
                    }
                } catch (Exception e) {
                    System.out.println("Exception " + e);
                }

                /* Pengeceken agar date taken tidak melewati expired date */
                //update by satrya 2014-02-08
                        Date dtTaken = dpStockTaken.getTakenDate()==null?null:Formater.reFormatDate(dpStockTaken.getTakenDate(), "yyyy-MM-dd 00:00:00");
                        Date dtExpired = dpStockManagement.getDtExpiredDate()==null?null:Formater.reFormatDate(dpStockManagement.getDtExpiredDate(), "yyyy-MM-dd 00:00:00");
                        Date dtExpiredExc = dpStockManagement.getDtExpiredDateExc()==null?null:Formater.reFormatDate(dpStockManagement.getDtExpiredDateExc(), "yyyy-MM-dd 00:00:00");
                if (leaveConfig.getDPExpired() && dpStockManagement.getDtExpiredDate() != null && dpStockManagement.getiExceptionFlag() == PstDpStockManagement.EXC_STS_NO) {
                    //update by satrya 2013-10-18
                    //if (dpStockManagement.getDtExpiredDate()!=null && dpStockManagement.getiExceptionFlag() == PstDpStockManagement.EXC_STS_NO) {
                     
                        //update by satrya 2014-02-08
                        //if ((dpStockManagement.getDtExpiredDate().getTime() / (24L * 60L * 60L * 1000L)) < (dpStockTaken.getTakenDate().getTime()) / (24L * 60L * 60L * 1000L)) {
                    if (dtTaken!=null && dtExpired!=null && (dtExpired.getTime() / (24L * 60L * 60L * 1000L)) < (dtTaken.getTime()) / (24L * 60L * 60L * 1000L)) {
                        msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                        return RSLT_FRM_TAKEN_BEFORE_EXPIRED_DATE;
                    }
                } else if ( dpStockManagement.getDtExpiredDateExc() != null && dpStockManagement.getiExceptionFlag() == PstDpStockManagement.EXC_STS_YES) {

                    if (dtExpiredExc!=null && dtTaken!=null && (dtExpiredExc.getTime() / (24L * 60L * 60L * 1000L)) < (dtTaken.getTime()) / (24L * 60L * 60L * 1000L)) {
                        msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                        return RSLT_FRM_TAKEN_BEFORE_EXPIRED_DATE;
                    }
                }
                //update by satrya 2012-10-08
                //agar eligible tidak minus

                if (listal != null && listal.size() > 0) {
                    RepItemLeaveAndDp item = null;
                    item = (RepItemLeaveAndDp) listal.get(0);
                    //float expiredQTY = SessLeaveManagement.getDpExpired(item.getEmployeeId(), null);
                    // int expiredQTY = SessLeaveManagement.getDpExpired(item.getEmployeeId(),null);
                    //float takenWithForm = SessLeaveManagement.getDpTakenOwningDate(item.getEmployeeId(), dpStockManagement.getDtOwningDate());

                    //float stockDP = SessLeaveManagement.getEligibleDayOffLeave(dpStockTaken);// - dpStockTaken.getTakenQty();
                    boolean isMinus = false;
                    if (dpStockTaken.getOID() != 0) {

                        DpStockTaken objDpStockTakenInSystem = new DpStockTaken();

                        try {
                            objDpStockTakenInSystem = PstDpStockTaken.fetchExc(dpStockTaken.getOID());
                        } catch (Exception E) {
                            System.out.println("[exception] " + E.toString());
                        }
                        float residueSystem = (SessLeaveApplication.getDpEligbleDays(dpStockTaken.getEmployeeId(), objDpStockTakenInSystem));
                        //if ((dpStockTaken.getTakenQty() > (residueSystem + dpTakenPrev))) { 
                        if(leaveConfig.getDPStockMinus(dpStockTaken,residueSystem,dpTakenPrev)){
                            isMinus = true;
                        }
                    }

                    /*for (int dpIdx = 0; dpIdx < listal.size(); dpIdx++) {
                     item = (RepItemLeaveAndDp) listal.get(dpIdx);
                     eligbleDay = item.getDPQty() - item.getDPTaken() - item.getDP2BTaken();
                     //update by satrya 2013-07-26*/
                       //eligbleDay = dpStockManagement.getQtyResidue();
                    //}
                    //eligbleDay = eligbleDay - expiredQTY - takenWithForm + dpTakenPrev;
                    //eligbleDay = eligbleDay - expiredQTY + dpTakenPrev;
                   /* if (((eligbleDay - dpStockTaken.getTakenQty()) < 0) && leaveConfig.getDPStockMinus() == false) {
                     msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                     return RSLT_FRM_ELIGBLE_MINUS;
                     }*/
                    //update by satrya 2013-08-30
                    //if(isMinus && leaveConfig.getDPStockMinus() == false){
                    if (isMinus || leaveConfig.getDPStockMinus(listal, dpStockTaken.getEmployeeId(), dpStockTaken, dpTakenPrev) == false) {
                        msgString = (leaveConfig.isMessageUseAdvanceMinusLimit() ? (FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE) + " Eligible Day exceed maximum limit" + (dpStockManagement.getiDpQty() - dpStockTaken.getTakenQty())) : FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE));
                        return RSLT_FRM_ELIGBLE_MINUS;
                    }
                }

                //update by satrya 2012-10-24
                //cek jika user memilih taken date dan finish date msh dalam 1 range 
                //listDPTakenFinishDate = SessLeaveApp.checkDetailLeaveTakenDate(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate());
                /*if (listDPTakenFinishDate != null && listDPTakenFinishDate.size() > 0) {
                 LeaveCheckTakenDateFinish dpCheck = null; 
                 for (int dpCheckIdx = 0; dpCheckIdx < listDPTakenFinishDate.size(); dpCheckIdx++) {
                 dpCheck = (LeaveCheckTakenDateFinish) listDPTakenFinishDate.get(dpCheckIdx);
                 if (dpCheck.getOidDetailLeave() != oidLeave) {
                 if (dpCheck.getLeaveAppId() != 0) {
                 // if(dpCheck.getLeaveSymbol().equalsIgnoreCase("DP")){
                 chkDateTaken = dpCheck.getTakenDate();
                 chkDateTaken.setSeconds(0);
                 chkDateFinish = dpCheck.getFinishDate();
                 chkDateFinish.setSeconds(0);
                 if ((dpStockTaken.getTakenDate().getTime() == dpStockTaken.getTakenFinnishDate().getTime()) || (chkDateTaken.getTime() == chkDateFinish.getTime()) 
                 //|| (chkDateTaken.getTime() <= dpStockTaken.getTakenDate().getTime() && dpStockTaken.getTakenDate().getTime() < chkDateFinish.getTime())
                 || ( ( (chkDateTaken.getTime() < dpStockTaken.getTakenDate().getTime() && dpStockTaken.getTakenDate().getTime() < chkDateFinish.getTime()) 
                 && (dpStockTaken.getTakenDate().getTime() < chkDateFinish.getTime() && chkDateFinish.getTime()< dpStockTaken.getTakenFinnishDate().getTime()))
                                
                 || ( ((dpStockTaken.getTakenDate().getTime() < chkDateTaken.getTime())
                 && chkDateTaken.getTime() < dpStockTaken.getTakenFinnishDate().getTime())
                 && (chkDateTaken.getTime()< dpStockTaken.getTakenFinnishDate().getTime() && dpStockTaken.getTakenFinnishDate().getTime()< chkDateFinish.getTime())
                 ))
                                        
                 || (chkDateTaken.getTime() == dpStockTaken.getTakenDate().getTime() && dpStockTaken.getTakenDate().getTime() == chkDateFinish.getTime())) {
                 //msgString = FRMMessage.getMsg(CtrlDpStockTaken.RSLT_FRM_DATE_IN_RANGE);
                 //break;
                 return RSLT_FRM_DATE_IN_RANGE;

                 }
                 //}
                 }//end
                 }
                 }
                 }*/
                        //update by satrya 2012-10-24
                //cek jika user memilih taken date dan finish date msh dalam 1 range 

                /* listDPTakenFinishDate = SessLeaveApp.checkOverLapsLeaveTaken(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(),dpStockTaken.getTakenFinnishDate());

                 if(listDPTakenFinishDate != null && listDPTakenFinishDate.size() > 0){
                 if(oidLeave==0){
                 for (int dpCheckIdx = 0; dpCheckIdx < listDPTakenFinishDate.size(); dpCheckIdx++) {
                 LeaveCheckTakenDateFinish dpCheck = (LeaveCheckTakenDateFinish) listDPTakenFinishDate.get(dpCheckIdx);
                 if (dpCheck.getOidDetailLeave()!=0 && oidLeave !=0 && dpCheck.getOidDetailLeave() != oidLeave) {
                 //chekError = true;
                 //msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:";
                 msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                 }
                 }
                 }
                         
                 //return RSLT_FRM_DATE_IN_RANGE;
                 }*/
              //update by satrya 2013-12-16
              if(leaveConfig != null && leaveConfig.getConfigurationLeaveApprovall() == I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG){
                 
              }else{
                //update by satrya 2013-02-28
                Vector vtakenDatex = SessLeaveApp.getTakenDate(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                //mencari nilai finishtakenDate yg sudah ada
                //Date finishTakenDatex = SessLeaveApp.getFinishTakenDate(alStockTaken.getEmployeeId(), alStockTaken.getTakenDate(),alStockTaken.getTakenFinnishDate());
                if (vtakenDatex.size() < 1) {
                    listDPTakenFinishDate = null;
                } else {
                    listDPTakenFinishDate = SessLeaveApp.checkOverLapsLeaveTaken(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                }

                if (listDPTakenFinishDate != null && listDPTakenFinishDate.size() > 0) {
                    //    chekError = true;
                    //update by satrya 2013-01-15
                    //msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:";
                    for (int dpCheckIdx = 0; dpCheckIdx < listDPTakenFinishDate.size(); dpCheckIdx++) {
                        LeaveCheckTakenDateFinish dpCheck = (LeaveCheckTakenDateFinish) listDPTakenFinishDate.get(dpCheckIdx);
                        // msgString = msgString + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                        // && oidLeave == 0 dihilangkan karena padahal sudah jls overlap tpi karena hal ini jdinya tdk overlap if (dpCheck.getOidDetailLeave() != 0 && oidLeave == 0 && dpCheck.getOidDetailLeave() != oidLeave) {
                        if (dpCheck.getOidDetailLeave() != 0  && dpCheck.getOidDetailLeave() != oidLeave) {
                            msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE] + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                            return RSLT_FRM_DATE_IN_RANGE;
                        }
                        
                        //update by satrya 2014-01-29
                       //karena ketika update user masih bisa overlap tgl nya ,kasus cuti AL tgl 29 Januari 2014 08:00 s/d 31 januari 2014 17:00 
                       //lalu cuti tgl 29 Januari 2014 00:00 s/d 08:00,
                       //lalu di edit menjadi tgl 29 Januari 2014 08:15 maka dia "Harusnya " jadi overlap 
                               else if(dpStockTaken!=null && dpCheck!=null && dpCheck.getTakenDate()!=null && dpCheck.getFinishDate()!=null && dpStockTaken.getTakenDate()!=null && dpStockTaken.getTakenFinnishDate()!=null){
                                   Date newStartDate = dpCheck.getTakenDate();
                                   Date newEndDate = dpCheck.getFinishDate();
                                   Date startDate = dpStockTaken.getTakenDate();
                                   Date endDate = dpStockTaken.getTakenFinnishDate();
                                   if ((oidLeave!=0 ? (dpCheck.getOidDetailLeave() == oidLeave?false:true) :  dpCheck.getOidDetailLeave() != oidLeave) && newStartDate.after(dpStockTaken.getTakenDate()) && newStartDate.before(dpStockTaken.getTakenFinnishDate())) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    else if ((oidLeave!=0 ? (dpCheck.getOidDetailLeave() == oidLeave?false:true) :  dpCheck.getOidDetailLeave() != oidLeave) && newEndDate.after(startDate) && newEndDate.before(endDate)) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    else if ((oidLeave!=0 ? (dpCheck.getOidDetailLeave() == oidLeave?false:true) :  dpCheck.getOidDetailLeave() != oidLeave) && startDate.after(newStartDate) && startDate.before(newEndDate)) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    else if ((oidLeave!=0 ? (dpCheck.getOidDetailLeave() == oidLeave?false:true) :  dpCheck.getOidDetailLeave() != oidLeave) && endDate.after(newStartDate) && endDate.before(newEndDate)) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    else if ( (oidLeave!=0 ? (dpCheck.getOidDetailLeave() == oidLeave?false:true) :  dpCheck.getOidDetailLeave() != oidLeave) && newStartDate.equals(startDate) && newEndDate.equals(endDate)) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    /*else if (newEndDate.equals(endDate)) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }*/
                                     else {
                                        //maka dia tidak overlap
                                     }
                               
                               }
                    }

                }
        }

                if (dpStockTaken.getOID() == 0) {
                    try {
                        this.dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                        //update by satrya 2013-12-13 long oid = PstDpStockTaken.insertExc(this.dpStockTaken);
                        if (leaveConfig != null && leaveConfig.getConfigurationLeaveApprovall() == I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG) {
                           
                                if (dpStockTaken.getSizeTakenDate() != 0 && dpStockTaken.getSizeTakenDateFinish() != 0 && dpStockTaken.getSizeTakenDate()==dpStockTaken.getSizeTakenDateFinish()) {
                                   String adaError="";
                                   Hashtable tmpCekDate = new Hashtable();
                                    for (int ix = 0; ix < dpStockTaken.getSizeTakenDate(); ix++) {
                                        dpStockTaken.setTakenDate(dpStockTaken.getvTakenDate(ix));
                                        dpStockTaken.setTakenFinnishDate(dpStockTaken.getvTakenFinishDate(ix));
                                        listDPTakenFinishDate = SessLeaveApp.checkOverLapsLeaveTaken(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                        if(listDPTakenFinishDate!=null && listDPTakenFinishDate.size()>0){
                                        for (int dpCheckIdx = 0; dpCheckIdx < listDPTakenFinishDate.size(); dpCheckIdx++) {
                                               LeaveCheckTakenDateFinish dpCheck = (LeaveCheckTakenDateFinish) listDPTakenFinishDate.get(dpCheckIdx);
                                            if (dpCheck.getOidDetailLeave() != 0  && dpCheck.getOidDetailLeave() != oidLeave) {
                                               //msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE] + " please check other Leave form on the same range:"+dpCheck.getLeaveSymbol() + " Taken Date" + Formater.formatDate(dpStockTaken.getTakenDate(), "dd-MM-yyyy hh:mm:dd") + " And Finish Date" + Formater.formatDate(dpStockTaken.getTakenFinnishDate(),  Formater.formatDate(dpStockTaken.getTakenFinnishDate(), "dd-MM-yyyy hh:mm:dd"))  + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                               //return RSLT_FRM_DATE_IN_RANGE;
                                               if(tmpCekDate.containsKey(dpCheck.getTakenDate())==false || tmpCekDate.containsKey(dpCheck.getFinishDate())==false){
                                                   adaError = adaError + "<b> <br>"+dpCheck.getLeaveSymbol() + " Taken Date :" + Formater.formatDate(dpCheck.getTakenDate(), "dd-MM-yyyy HH:mm") + " And Finish Date :" + Formater.formatDate(dpCheck.getFinishDate(), "dd-MM-yyyy HH:mm")  + " Date of Request : </b> <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                               }
                                               tmpCekDate.put(dpCheck.getTakenDate(), true);
                                               tmpCekDate.put(dpCheck.getFinishDate(), true);
                                               
                                           }
                                            /*else{
                                                 float dpQty = DateCalc.workDayDifference(dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate(), 8f);
                                                float intersec = PstEmpSchedule.breakTimeIntersection(dpStockTaken.getEmployeeId(),dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate()) / (leaveConfig.getHourOneWorkday() * 60f * 60f * 1000f);
                                                dpStockTaken.setTakenQty(dpQty - intersec);
                                                long oid = PstDpStockTaken.insertExc(this.dpStockTaken);
                                            }*/
                                           
                                        }
                                       }else{
                                             float dpQty = DateCalc.workDayDifference(dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate(), 8f);
                                                float intersec = PstEmpSchedule.breakTimeIntersection(dpStockTaken.getEmployeeId(),dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate()) / (leaveConfig.getHourOneWorkday() * 60f * 60f * 1000f);
                                                dpStockTaken.setTakenQty(dpQty - intersec);
                                                long oid = PstDpStockTaken.insertExc(this.dpStockTaken);
                                        }
                                    }
                                        
                                     if(adaError.length()>0){
                                                msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE] + " please check other Leave form on the same range: " + adaError;
                                                rsCode = RSLT_FRM_DATE_IN_RANGE;
                                     }
                                }
                            
                        } else {
                            long oid = PstDpStockTaken.insertExc(this.dpStockTaken);
                            if(oid!=0){
                              msgString = resultText[language][RSLT_FRM_INSERT_DATA]+" "+resultText[language][RSLT_OK];
                            }
                        }

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
                        //update by satrya 2012-08-07
                        this.dpStockTaken.setPaidDate(dpStockManagement.getDtOwningDate());
                        //if (((eligbleDay - dpStockTaken.getTakenQty()) < 0) && leaveConfig.getDPStockMinus() == false) {
                        ///pengecekan jika user melebihi dari stok sewaktu update
                        //update by satrya 2013-08-30
                        //if(dpStockManagement.getiDpQty()<dpStockTaken.getTakenQty() && leaveConfig.getDPStockMinus() == false){
                        if (leaveConfig.getDPStockMinus(dpStockManagement, dpStockTaken) && leaveConfig.getDPStockMinus(listal, dpStockTaken.getEmployeeId(), dpStockTaken, dpTakenPrev) == false) {
                            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                            return RSLT_FRM_ELIGBLE_MINUS;
                        }
                        //update by satrya 2013-12-13 long oid = PstDpStockTaken.updateExc(this.dpStockTaken);
                        if (leaveConfig != null && leaveConfig.getConfigurationLeaveApprovall() == I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG) {
                           if (dpStockTaken.getSizeTakenDate() != 0 && dpStockTaken.getSizeTakenDateFinish() != 0 && dpStockTaken.getSizeTakenDate()==dpStockTaken.getSizeTakenDateFinish()) {
                                    String adaError="";
                                    Hashtable tmpCekDate = new Hashtable();
                                    for (int ix = 0; ix < dpStockTaken.getSizeTakenDate(); ix++) {
                                       dpStockTaken.setTakenDate(dpStockTaken.getvTakenDate(ix));
                                        dpStockTaken.setTakenFinnishDate(dpStockTaken.getvTakenFinishDate(ix));
                                        
                                            float dpQty = DateCalc.workDayDifference(dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate(), 8f);
                                            float intersec = PstEmpSchedule.breakTimeIntersection(dpStockTaken.getEmployeeId(),dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate()) / (leaveConfig.getHourOneWorkday() * 60f * 60f * 1000f);
                                            dpStockTaken.setTakenQty(dpQty - intersec);
                                            Vector listDPTaken = SessLeaveApp.checkOverLapsLeaveTaken(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate());
                                            if(listDPTaken!=null && listDPTaken.size()>0){
                                                for (int dpCheckIdx = 0; dpCheckIdx < listDPTaken.size(); dpCheckIdx++) {
                                                        LeaveCheckTakenDateFinish dpCheck = (LeaveCheckTakenDateFinish) listDPTaken.get(dpCheckIdx);
                                                   if (dpCheck.getOidDetailLeave() != 0  && dpCheck.getOidDetailLeave() != oidLeave) {
                                                        //msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE] + " please check other Leave form on the same range:"+dpCheck.getLeaveSymbol() + " Taken Date" + Formater.formatDate(dpStockTaken.getTakenDate(), "dd-MM-yyyy hh:mm:dd") + " And Finish Date" + Formater.formatDate(dpStockTaken.getTakenFinnishDate(),  Formater.formatDate(dpStockTaken.getTakenFinnishDate(), "dd-MM-yyyy hh:mm:dd"))  + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                                        //return RSLT_FRM_DATE_IN_RANGE;
                                                        if(tmpCekDate.containsKey(dpCheck.getTakenDate())==false || tmpCekDate.containsKey(dpCheck.getFinishDate())==false){
                                                            adaError = adaError + "<b> <br>"+dpCheck.getLeaveSymbol() + " Taken Date :" + Formater.formatDate(dpCheck.getTakenDate(), "dd-MM-yyyy HH:mm") + " And Finish Date :" + Formater.formatDate(dpCheck.getFinishDate(), "dd-MM-yyyy HH:mm")  + " Date of Request : </b> <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                                        }
                                                        tmpCekDate.put(dpCheck.getTakenDate(), true);
                                                        tmpCekDate.put(dpCheck.getFinishDate(), true);

                                                    }
                                                    /*else{
                                                            if(listDPTaken!=null && listDPTaken.size()>0){
                                                                long oid = PstDpStockTaken.updateExc(this.dpStockTaken);
                                                            }else{
                                                               long oid = PstDpStockTaken.insertExc(this.dpStockTaken);
                                                            }
                                                     }*/
                                               }
                                          }else{
                                                if(listDPTaken!=null && listDPTaken.size()>0){
                                                    long oid = PstDpStockTaken.updateExc(this.dpStockTaken);
                                                }else{
                                                   long oid = PstDpStockTaken.insertExc(this.dpStockTaken);
                                                }
                                          }
                                    }
                                    if(adaError.length()>0){
                                                msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE] + " please check other Leave form on the same range: "+ adaError;
                                                rsCode = RSLT_FRM_DATE_IN_RANGE;
                                     }
                                }
                        } else {
                            long oid = PstDpStockTaken.updateExc(this.dpStockTaken);
                            if(oid!=0){
                              msgString = resultText[language][RSLT_FRM_UPDATE_DATA]+" "+resultText[language][RSLT_OK];
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
                if (oidLeave != 0) {
                    try {
                        dpStockTaken = PstDpStockTaken.fetchExc(oidLeave);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidLeave != 0) {
                    try {
                        dpStockTaken = PstDpStockTaken.fetchExc(oidLeave);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidLeave != 0) {
                    try {
                        long oid = PstDpStockTaken.deleteExc(oidLeave);
                        if (oid != 0) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED) + " DP Leave Taken ";
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

    public synchronized int actionMultiple(int cmd, long oidDpId, long empId, ScheduleSymbol scheduleSymbol, float dpTotReq, long dpStokTakenId, long calculateBreak) {
        msgString = "";
        DpStockManagement dpStockManagement = new DpStockManagement();
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        //long dpStokTakenId = PstDpStockTaken.getDpStokTakenId(oidDpId);
        boolean chekError = false;
        boolean chekBreak = true;

        /* Untuk menghandle agar stock al tidak minus jika tidak diperbolehkan mengambil cuti dalam kondisi stock minus*/
        Vector listDPTakenFinishDate = null;
        Date chkDateTaken = null;
        Date chkDateFinish = null;
        I_Leave leaveConfig = null;

        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception LEAVE_CONFIG: " + e.getMessage());
        }
        switch (cmd) {
            case Command.SAVE:
                float dpTakenPrev = 0.0f;

                if (dpStokTakenId != 0) {
                    try {
                        dpStockTaken = PstDpStockTaken.fetchExc(dpStokTakenId);
                        //dpTakenPrev = dpStockTaken.getTakenQty();
                    } catch (Exception exc) {
                        System.out.println("Exception fetchExc(oidLeave)" + exc);
                    }
                }

                frmDpStockTaken.requestEntityObjectMultiple();
                if (frmDpStockTaken.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    //return RSLT_FORM_INCOMPLETE ;
                    chekError = true;
                }
                if (frmDpStockTaken.getDpTakens() != null && frmDpStockTaken.getDpTakens().size() > 0) {
                    float eligbleDay = 0;
                    long takenDateNewX = 0;//(scheduleSymbol.getTimeIn().getTime());;
                    long finishDateNewX = 0;
                    //float dpWillBeTakenPerDp = 0.0f;
                    float totalRequest = 0.0f;

                    for (int idx = 0; idx < frmDpStockTaken.getDpTakens().size(); idx++) {
                        boolean cekExpired = false;

                        DpStockTaken dpStockTakens = (DpStockTaken) frmDpStockTaken.getDpTakens().get(idx);
                        long dpStokTakenIdX = PstDpStockTaken.getDpStokTakenId(dpStockTakens.getDpStockId());
                        try {
                            dpStockManagement = PstDpStockManagement.fetchExc(dpStockTakens.getDpStockId());
                        } catch (Exception e) {
                            System.out.println("Exception " + e);
                        }
                        /*if(oidStokTakenId!=0){
                         long xDpStokTakenOid =dpStockTakens.getOID();
                         oidStokTakenId= PstDpStockTaken.fetchExc(xDpStokTakenOid); 
                         }*/

                        float dpRoundEligible = dpStockManagement.getEligible();
                        // float x2BeTaken=//0.0f;
                        if (dpRoundEligible <= 0.000000f) {
                            continue;
                        }
                        float dpFullFilled = 0.0f;

//                        
//                      if (cekExpired == true) {
//                            dpWillBeTakenPerDp = dpWillBeTakenPerDp;
//                        } else if (dpRoundEligible >= (sisa - dpFullFilled)) {
//                            dpWillBeTakenPerDp = sisa - dpFullFilled;
//                        } else {
//                            dpWillBeTakenPerDp = dpRoundEligible;
//                        }
//                        dpFullFilled = dpFullFilled + dpWillBeTakenPerDp;
//                        if (dpWillBeTakenPerDp != 0) {
//                            dpStockTakens.setTakenQty(dpWillBeTakenPerDp);
//                        }
                        if (dpTotReq >= totalRequest && dpAvailableDays >= dpTotReq || dpStockTakens.getTakenDate().getTime() > dpStockTakens.getTakenFinnishDate().getTime()) {///cek jika user memilih lagi padahal sdh lwt 1 hari

                            //jika user sudah memilih 1 hari (batas Max DP) maka tidak di set lagi takenDate dan finishDate'nya
                            //update by satrya 2013-01-08
                            if (dpStockTakens.getTakenQty() != 1) {
                                long timeSelected = Formater.getWorkDayMiliSeconds(dpStockTakens.getTakenQty(), leaveConfig.getHourOneWorkday());

                                if (takenDateNewX == 0) {
                                    takenDateNewX = (dpStockTakens.getTakenDate().getTime());
                                    dpStockTakens.setTakenDate(new Date(takenDateNewX));
                                } else {
                                    dpStockTakens.setTakenDate(new Date(finishDateNewX));
                                }
                                finishDateNewX = (timeSelected + dpStockTakens.getTakenDate().getTime());
                                dpStockTakens.setTakenFinnishDate(new Date(finishDateNewX));
                                long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(dpStockTakens.getEmployeeId(), dpStockTakens.getTakenDate(), dpStockTakens.getTakenFinnishDate());
                                if (intersecX != 0) {
                                    finishDateNewX = finishDateNewX + calculateBreak;
                                    dpStockTakens.setTakenFinnishDate(new Date(finishDateNewX));
                                }
                            }
                        } else {
                            msgString = "Your Selected DP can't over to 1 day";
                            chekError = true;
                        }
                        //update by satrya 2014-02-08
                        Date dtTaken = dpStockTakens.getTakenDate()==null?null:Formater.reFormatDate(dpStockTakens.getTakenDate(), "yyyy-MM-dd 00:00:00");
                        Date dtExpired = dpStockManagement.getDtExpiredDate()==null?null:Formater.reFormatDate(dpStockManagement.getDtExpiredDate(), "yyyy-MM-dd 00:00:00");
                        Date dtExpiredExc = dpStockManagement.getDtExpiredDateExc()==null?null:Formater.reFormatDate(dpStockManagement.getDtExpiredDateExc(), "yyyy-MM-dd 00:00:00");
                        /* Pengeceken agar date taken tidak melewati expired date */
                        if (leaveConfig.getDPExpired() && dpStockManagement.getiExceptionFlag() == PstDpStockManagement.EXC_STS_NO) {
                        
                           
                            //update by satrya 2013-10-18
                            //if (dpStockManagement.getiExceptionFlag() == PstDpStockManagement.EXC_STS_NO) {
                            //update by satrya 2014-02-08
                            //if ((dpStockManagement.getDtExpiredDate().getTime() / (24L * 60L * 60L * 1000L)) < (dpStockTakens.getTakenDate().getTime()) / (24L * 60L * 60L * 1000L)) {
                            if (dtExpired!=null && dtTaken!=null && (dtExpired.getTime() / (24L * 60L * 60L * 1000L)) < (dtTaken.getTime()) / (24L * 60L * 60L * 1000L)) {
                                //msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                                // return RSLT_FRM_TAKEN_BEFORE_EXPIRED_DATE;        
                                chekError = true;
                                cekExpired = true;
                                msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE) + resultText[language][RSLT_FRM_TAKEN_BEFORE_EXPIRED_DATE];
                            }
                        } else if (dpStockManagement.getiExceptionFlag() == PstDpStockManagement.EXC_STS_YES) {

                            if (dtExpiredExc!=null && dtTaken!=null &&(dtExpiredExc.getTime() / (24L * 60L * 60L * 1000L)) < (dtTaken.getTime()) / (24L * 60L * 60L * 1000L)) {
                                //msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                                //return RSLT_FRM_TAKEN_BEFORE_EXPIRED_DATE;        
                                chekError = true;
                                cekExpired = true;
                                msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE) + resultText[language][RSLT_FRM_TAKEN_BEFORE_EXPIRED_DATE];
                            }
                        }

                        //update by satrya 2012-10-08
                        //agar eligible tidak minus
                        Vector dpList = SessLeaveApp.detailLeaveDPStockByEmployee(empId);
                        if (dpList != null && dpList.size() > 0) {
                            RepItemLeaveAndDp itemX = (RepItemLeaveAndDp) dpList.get(0);
                            //DpStockTaken dpStockTakens = (DpStockTaken) frmDpStockTaken.getDpTakens().get(idx);

                            float expiredQTY = SessLeaveManagement.getDpExpired(dpStockTakens.getEmployeeId(), null);
                            // int expiredQTY = SessLeaveManagement.getDpExpired(item.getEmployeeId(),null);
                            for (int dpIdx = 0; dpIdx < dpList.size(); dpIdx++) {
                                itemX = (RepItemLeaveAndDp) dpList.get(dpIdx);
                                eligbleDay = (itemX.getDPQty() - itemX.getDPTaken() - itemX.getDP2BTaken());
                            }

                            eligbleDay = eligbleDay - expiredQTY + dpTakenPrev;
                            //update by satrya 2013-08-30
                            //if (((eligbleDay - dpStockTakens.getTakenQty()) < 0) && leaveConfig.getDPStockMinus() == false) {
                            if (((eligbleDay - dpStockTakens.getTakenQty()) < 0) && leaveConfig.getDPStockMinus(dpList, dpStockTaken.getEmployeeId(), dpStockTaken, dpTakenPrev) == false) {
                                //msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                                ///return RSLT_FRM_ELIGBLE_MINUS;  
                                chekError = true;
                            }
                        }

                        //update by satrya 2012-10-24
                        //cek jika user memilih taken date dan finish date msh dalam 1 range 
                        listDPTakenFinishDate = SessLeaveApp.checkOverLapsLeaveTaken(dpStockTakens.getEmployeeId(), dpStockTakens.getTakenDate(), dpStockTakens.getTakenFinnishDate());

                        if (listDPTakenFinishDate != null && listDPTakenFinishDate.size() > 0) {
                            //if(dpStokTakenId==0){
                            for (int dpCheckIdx = 0; dpCheckIdx < listDPTakenFinishDate.size(); dpCheckIdx++) {
                                LeaveCheckTakenDateFinish dpCheck = (LeaveCheckTakenDateFinish) listDPTakenFinishDate.get(dpCheckIdx);
                                // if (dpCheck.getOidDetailLeave()!=0 && dpStokTakenId !=0 && dpCheck.getOidDetailLeave() != dpStokTakenId) {
                                chekError = true;
                                //msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:";
                                msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE] + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                //}
                            }
                         //}

                            //return RSLT_FRM_DATE_IN_RANGE;
                        }
//                        if (listDPTakenFinishDate != null && listDPTakenFinishDate.size() > 0) {
//                            LeaveCheckTakenDateFinish dpCheck = null;
//                            for (int dpCheckIdx = 0; dpCheckIdx < listDPTakenFinishDate.size(); dpCheckIdx++) {
//                                dpCheck = (LeaveCheckTakenDateFinish) listDPTakenFinishDate.get(dpCheckIdx);
//                                if (dpCheck.getOidDetailLeave() != dpStokTakenId) {
//                                    if (dpCheck.getLeaveAppId() != 0) {
//                                        // if(dpCheck.getLeaveSymbol().equalsIgnoreCase("DP")){
//                                        chkDateTaken = dpCheck.getTakenDate();
//                                        chkDateTaken.setSeconds(0);
//                                        chkDateFinish = dpCheck.getFinishDate();
//                                        chkDateFinish.setSeconds(0);
////                                        if ((dpStockTakens.getTakenDate().getTime() == dpStockTakens.getTakenFinnishDate().getTime()) || 
////                                                (chkDateTaken.getTime() == chkDateFinish.getTime()) || 
////                                                (chkDateTaken.getTime() <= dpStockTakens.getTakenDate().getTime()
////                                                && dpStockTakens.getTakenDate().getTime() < chkDateFinish.getTime())
////                                                || (chkDateTaken.getTime() == dpStockTakens.getTakenDate().getTime() 
////                                                    && dpStockTakens.getTakenDate().getTime() == chkDateFinish.getTime())) {
//                                        //update by satrya 2013-01-08
//                                        if((chkDateTaken.getTime() == dpStockTakens.getTakenDate().getTime() || chkDateFinish.getTime() == dpStockTakens.getTakenFinnishDate().getTime()) ||
//                                                (((chkDateFinish.getTime()<dpStockTakens.getTakenDate().getTime()))|| ((dpStockTakens.getTakenFinnishDate().getTime()> chkDateTaken.getTime())))){
//                                        
//                                        chekError = true;
//                                            return RSLT_FRM_DATE_IN_RANGE; 
//                                            
//
//                                        }
//                                        //}
//                                    }//end
//                                }
//                            }
//                        }
                        if (dpStokTakenIdX == dpStokTakenId && dpStokTakenIdX != 0 && dpStokTakenId != 0) {
                            dpStockTakens.setOID(dpStokTakenId);//untuk jka user menkan tombol edit
                        }

                        if (dpStockTakens.getOID() == 0) {
                            try {
                                dpStockTakens.setPaidDate(dpStockManagement.getDtOwningDate());
                                if (chekError == false) {
                                    long oidStokTakenId = PstDpStockTaken.insertExc(dpStockTakens);
                                    //prevDpStockTakens = dpStockTakens;

                                }
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
                                //update by satrya 2012-08-07
                                if (((dpStockTakens.getTmpWillBeTaken() + dpStockTakens.getTmpEligible()) - dpTotReq) < 0) {
                                    float tmpEligible = dpStockTakens.getTmpWillBeTaken() - dpTotReq;
                                    String sWillBeTaken = Formater.formatWorkDayHoursMinutes(dpStockTakens.getTmpWillBeTaken(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                                    String sTotalHours = Formater.formatWorkDayHoursMinutes(dpTotReq, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                                    String stmpEligible = Formater.formatWorkDayHoursMinutes(Math.abs(tmpEligible), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                                    msgString = "Eligble Day Is Not Enough."
                                            + " <ul><li> Request Dp:" + sTotalHours + "</li>"
                                            + " <li> Eligible DP (" + (dpStockManagement.getDtOwningDate() == null ? "-" : Formater.formatDate(dpStockManagement.getDtOwningDate(), "dd MMM yyyy")) + "):" + sWillBeTaken + "</li>"
                                            + " <li> Need More DP (" + stmpEligible + ")" + "</li></ul>";
                                    /**
                                     * msgString = "Eligble Day dosn't minus.
                                     * WillBeTaken (" + sWillBeTaken + ") - " +
                                     * "Hours (" + sTotalHours + ") = Eligible
                                     * (" + stmpEligible + ")"; msgString =
                                     * "Eligble Day Is Not Enough. \n\n Request
                                     * Dp:"+sTotalHours+ " \n\n Eligible
                                     * DP("+dpStockManagement.getDtOwningDate()==null?"-":Formater.formatDate(dpStockManagement.getDtOwningDate(),
                                     * "dd MM yyyy") +"):"+sWillBeTaken + "
                                     * WillBeTaken (" + sWillBeTaken + ") - " +
                                     * "Hours (" + sTotalHours + ") = Eligible
                                     * (" + stmpEligible + ")";
                                     */
                                    chekError = true;
                                }

                                if (chekError == false) {
                                    dpStockTakens.setPaidDate(dpStockManagement.getDtOwningDate());
                                    long oid = PstDpStockTaken.updateExc(dpStockTakens);
                                }

                            } catch (DBException dbexc) {
                                excCode = dbexc.getErrorCode();
                                msgString = getSystemMessage(excCode);
                            } catch (Exception exc) {
                                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                            }

                        }
                        totalRequest = totalRequest + dpStockTakens.getTakenQty();
                        /*
                         break;*/
                    } //loop frm 

                }//jika frm vector ==null 

        }
        return rsCode;
    }

    public int action(int cmd, long oidLeave, AlStockTaken stockTaken) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidLeave != 0) {
                    try {
                        dpStockTaken = PstDpStockTaken.fetchExc(oidLeave);
                    } catch (Exception exc) {
                    }
                }

                dpStockTaken.setOID(stockTaken.getOID());
                dpStockTaken.setDpStockId(stockTaken.getAlStockId());
                dpStockTaken.setEmployeeId(stockTaken.getEmployeeId());
                dpStockTaken.setTakenDate(stockTaken.getTakenDate());
                dpStockTaken.setTakenQty(stockTaken.getTakenQty());
                dpStockTaken.setPaidDate(stockTaken.getPaidDate());
                dpStockTaken.setTakenFinnishDate(stockTaken.getTakenFinnishDate());

                if (dpStockTaken.getOID() == 0) {
                    try {
                        long oid = PstDpStockTaken.insertExc(this.dpStockTaken);
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
                        long oid = PstDpStockTaken.updateExc(this.dpStockTaken);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidLeave != 0) {
                    try {
                        dpStockTaken = PstDpStockTaken.fetchExc(oidLeave);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidLeave != 0) {
                    try {
                        dpStockTaken = PstDpStockTaken.fetchExc(oidLeave);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidLeave != 0) {
                    try {
                        long oid = PstDpStockTaken.deleteExc(oidLeave);
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

            default:

        }
        return rsCode;
    }
}
