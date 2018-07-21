/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.leave;

/**
 *
 * @author Tu Roy
 */
import javax.servlet.http.HttpServletRequest;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.lang.I_Language;
import com.dimata.util.Command;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.leave.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.session.attendance.*;
import com.dimata.harisma.session.leave.SessLeaveApp;
import com.dimata.harisma.session.leave.SessLeaveApplication;
import com.dimata.system.entity.PstSystemProperty;
import java.util.Date;
import java.util.Vector;

public class CtrlSpecialUnpaidLeaveTaken extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_TAKEN_DATE_WRONG = 4;
    public static int RSLT_FRM_DATE_IN_RANGE = 5;
    public static int RSLT_FRM_INSERT_DATA = 6;
      public static int RSLT_FRM_UPDATE_DATA = 7;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap", 
            "tanggal start harus lebih kecil daripada tanggal finnish","cuti yang di request sudah ada","Menambah data Special Leave","Ubah Data Special Leave"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete", "Date start must be smaller than date finnish" ," The are overlapping leave request, please check again ","Insert Data Special Leave","Update Data Special Leave"}
    };
    private int start;
    private String msgString;
    private SpecialUnpaidLeaveTaken objSpecialUnpaidLeaveTaken;
    private PstSpecialUnpaidLeaveTaken objPstSpecialUnpaidLeaveTaken;
    private FrmSpecialUnpaidLeaveTaken objFrmSpecialUnpaidLeaveTaken;
    int language = LANGUAGE_DEFAULT;

    public CtrlSpecialUnpaidLeaveTaken(HttpServletRequest request) {
        msgString = "";
        objSpecialUnpaidLeaveTaken = new SpecialUnpaidLeaveTaken();
        try {
            objPstSpecialUnpaidLeaveTaken = new PstSpecialUnpaidLeaveTaken(0);
        } catch (Exception e) {
            System.out.println("EXCEPTION :::" + e.toString());
        }
        objFrmSpecialUnpaidLeaveTaken = new FrmSpecialUnpaidLeaveTaken(request, objSpecialUnpaidLeaveTaken);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.objFrmSpecialUnpaidLeaveTaken.addError(0, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public SpecialUnpaidLeaveTaken getSpecialUnpaidLeaveTaken() {
        return objSpecialUnpaidLeaveTaken;
    }

    public FrmSpecialUnpaidLeaveTaken getForm() {
        return objFrmSpecialUnpaidLeaveTaken;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

     public int action(int cmd, long Oid) {
         Position position = new Position();
        return action(cmd, Oid, position);
    }
    
    public int action(int cmd, long Oid, Position position) {
        msgString = "";
        ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        //update by satrya 2012-10-24
    Vector listSUTakenFinishDate = null;
    Date chkDateTaken = null;
    Date chkDateFinish = null;
    
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                String addMessage="";
                if (Oid != 0) {
                    try {
                        objSpecialUnpaidLeaveTaken = PstSpecialUnpaidLeaveTaken.fetchExc(Oid);
                    } catch (Exception exc) {
                        System.out.println("Exc when fetch AlUpload entity : " + exc.toString());
                    }
                }

                objFrmSpecialUnpaidLeaveTaken.requestEntityObject(objSpecialUnpaidLeaveTaken);

                if (objFrmSpecialUnpaidLeaveTaken.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                boolean stsminservice = true;
                boolean mxentitle = true;
                boolean periode = true;

                Employee employee = new Employee();

                try {
                    employee = PstEmployee.fetchExc(objSpecialUnpaidLeaveTaken.getEmployeeId());
                } catch (Exception e) {
                    System.out.println("Exception " + e.toString());
                }

                try {
                    scheduleSymbol = PstScheduleSymbol.fetchExc(objSpecialUnpaidLeaveTaken.getScheduledId());
                } catch (Exception e) {
                    System.out.println("Exception " + e.toString());
                }
                long schExcuseId=0;
               
                if(PstSystemProperty.getValueByName("OID_EXCUSE_SCHEDULE_CATEGORY")!=null && PstSystemProperty.getValueByName("OID_EXCUSE_SCHEDULE_CATEGORY").length()>0){
                    schExcuseId=Long.parseLong((PstSystemProperty.getValueByName("OID_EXCUSE_SCHEDULE_CATEGORY")));
                };
                long scheduleId = 0;
                if(PstSystemProperty.getValueByName("OID_UNPAID")!=null && PstSystemProperty.getValueByName("OID_UNPAID").length()>0){
                    scheduleId=Long.parseLong((PstSystemProperty.getValueByName("OID_UNPAID")));
                };

                /* Untuk Pengecekan konfigurasi di schedule symbol */
                if (scheduleId != scheduleSymbol.getScheduleCategoryId() && schExcuseId != scheduleSymbol.getScheduleCategoryId()) {
                    //update by satrya 2013-04-11
                    //if (scheduleId != scheduleSymbol.getScheduleCategoryId() ) {

                    //int maxEntitle = scheduleSymbol.getMaxEntitle();
                    //int dateSetahun = 365; // 365 hari setahun
                    //int dateSebulan = 30;  //30 hari sebulan                    
                   
                    float tknEntitle = 0;
                    
                    /* Untuk mendapatkan jumlah pengambilan dari taken dan finnish taken date */
                    tknEntitle = objSpecialUnpaidLeaveTaken.getTakenQty();// SessLeaveApplication.DATE_DIFERENT(objSpecialUnpaidLeaveTaken.getTakenDate(),objSpecialUnpaidLeaveTaken.getTakenFinnishDate());
                    
                    //int tknDate = (int) objSpecialUnpaidLeaveTaken.getTakenDate().getTime() / (24 * 60 * 60 * 1000);
                    //int fnsDate = (int) objSpecialUnpaidLeaveTaken.getTakenFinnishDate().getTime() / (24 * 60 * 60 * 1000);
                    //int cmencDateEmployee = (int)employee.getCommencingDate().getTime()/(24*60*60*1000);
                    
                    //tknEntitle = fnsDate - tknDate + 1;
                    
                    /* Untuk mendapatkan minimum service */
                    int diferentdt = SessSpecialUnpaidLeaveTaken.DateAdd(objSpecialUnpaidLeaveTaken.getTakenDate(), employee.getCommencingDate(), scheduleSymbol.getMinService());                   
                    
                    int mxOnePeriod = scheduleSymbol.getPeriode();

                    if (diferentdt < 0) {
                        stsminservice = false;
                        addMessage =" Not fullfill : Entitle after "+ scheduleSymbol.getMinService() + " months of services ";
                    }
                    if (tknEntitle > (float)scheduleSymbol.getMaxEntitle()){
                        mxentitle = false;
                        addMessage =" Max entitle per taking="+ scheduleSymbol.getMaxEntitle() + " will be taken="+tknEntitle ;
                    }
                    if(scheduleSymbol.getPeriodeType() == PstScheduleSymbol.PERIODE_TYPE_TIME_AT_ALL) {
                        float entAll = SessSpecialUnpaidLeaveTaken.getSumQtySpcUnpLeaveAllTime(objSpecialUnpaidLeaveTaken.getEmployeeId(), objSpecialUnpaidLeaveTaken.getScheduledId(), 0);
                        if(objSpecialUnpaidLeaveTaken.getOID()==0){
                            entAll = entAll + tknEntitle;                                                        
                        }
                        if (entAll > (float)(scheduleSymbol.getMaxEntitle() * scheduleSymbol.getPeriode() )){
                            mxentitle = false;
                            addMessage =" Max entitle="+ (scheduleSymbol.getMaxEntitle() * scheduleSymbol.getPeriode()) + " all taken="+entAll ;
                        }
                        
                        int sumTknAll = SessSpecialUnpaidLeaveTaken.getSpcUnpLeaveAllTime(objSpecialUnpaidLeaveTaken.getEmployeeId(), objSpecialUnpaidLeaveTaken.getScheduledId(), objSpecialUnpaidLeaveTaken.getLeaveApplicationId());
                        if (sumTknAll > mxOnePeriod) {
                            periode = false;
                            addMessage =addMessage+" Max  frequence taken at all ="+ mxOnePeriod + " all taken ="+sumTknAll ;                            
                        }

                    }else if(scheduleSymbol.getPeriodeType() == PstScheduleSymbol.PERIODE_TYPE_MONTH) {

                        int sumTkn = SessSpecialUnpaidLeaveTaken.getSpcUnpLeaveMonth(objSpecialUnpaidLeaveTaken.getTakenDate(), objSpecialUnpaidLeaveTaken.getEmployeeId(), objSpecialUnpaidLeaveTaken.getScheduledId());
                        if (sumTkn > mxOnePeriod) {
                            periode = false;
                            addMessage =addMessage+" Max frequence taken in a month="+ mxOnePeriod + " taken ="+sumTkn ;                            
                        }

                    }else if(scheduleSymbol.getPeriodeType() == PstScheduleSymbol.PERIODE_TYPE_YEAR) {

                        int sumTknYear = SessSpecialUnpaidLeaveTaken.getSpcUnpLeaveYear(objSpecialUnpaidLeaveTaken.getTakenDate(), objSpecialUnpaidLeaveTaken.getEmployeeId(), objSpecialUnpaidLeaveTaken.getScheduledId());
                        if (sumTknYear >= mxOnePeriod) {
                            periode = false;
                            addMessage =addMessage+" Max frequence taken in a year="+ mxOnePeriod + " taken ="+sumTknYear ;                            
                        }
                    }

                    if (periode == false || stsminservice == false || mxentitle == false) {

                        msgString = "Employee can't take this special leave due to "+(periode?"": " period of taken ")+(stsminservice?"":" minimum length of service ")+(mxentitle?"":" maximum entitle days ")+", please check length of days or other leave application exist"; //FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                        msgString= msgString + " "+ addMessage; 
                        return RSLT_FORM_INCOMPLETE;
                    }
                }
                //end pengecekan
                //update by satrya 2012-10-24
              //cek jika user memilih taken date dan finish date msh dalam 1 range 
                listSUTakenFinishDate = SessLeaveApp.checkOverLapsLeaveTaken(objSpecialUnpaidLeaveTaken.getEmployeeId(),objSpecialUnpaidLeaveTaken.getTakenDate(),objSpecialUnpaidLeaveTaken.getTakenFinnishDate());
             /* listSUTakenFinishDate = SessLeaveApp.checkOverLapsLeaveTaken(objSpecialUnpaidLeaveTaken.getEmployeeId(),objSpecialUnpaidLeaveTaken.getTakenDate(),objSpecialUnpaidLeaveTaken.getTakenFinnishDate());

                        if(listSUTakenFinishDate != null && listSUTakenFinishDate.size() > 0){
                         //    chekError = true;
                         //msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:";
                             for (int dpCheckIdx = 0; dpCheckIdx < listSUTakenFinishDate.size(); dpCheckIdx++) {
                               LeaveCheckTakenDateFinish dpCheck = (LeaveCheckTakenDateFinish) listSUTakenFinishDate.get(dpCheckIdx);
                                if (dpCheck.getOidDetailLeave()!=0  && dpCheck.getOidDetailLeave() != Oid) {
                                    msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                    //msgString = msgString + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                    return RSLT_FRM_DATE_IN_RANGE;
                                } 
                               
                            }
                           
                        }*/
                 //update by satrya 2013-02-28
                 /*Vector vtakenDatex = SessLeaveApp.getTakenDate(objSpecialUnpaidLeaveTaken.getEmployeeId(), objSpecialUnpaidLeaveTaken.getTakenDate(),objSpecialUnpaidLeaveTaken.getTakenFinnishDate());
                //mencari nilai finishtakenDate yg sudah ada
                //Date finishTakenDatex = SessLeaveApp.getFinishTakenDate(alStockTaken.getEmployeeId(), alStockTaken.getTakenDate(),alStockTaken.getTakenFinnishDate());
                if(vtakenDatex.size()<1){
                    listSUTakenFinishDate =null;
                }else{
                    listSUTakenFinishDate  = SessLeaveApp.getFinishTakenDate(objSpecialUnpaidLeaveTaken.getEmployeeId(), objSpecialUnpaidLeaveTaken.getTakenDate(),objSpecialUnpaidLeaveTaken.getTakenFinnishDate());
                }*/
                 
                        if(listSUTakenFinishDate != null && listSUTakenFinishDate.size() > 0){
                         //    chekError = true;
                        //update by satrya 2013-01-15
                         //msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:";
                             for (int dpCheckIdx = 0; dpCheckIdx < listSUTakenFinishDate.size(); dpCheckIdx++) {
                               LeaveCheckTakenDateFinish dpCheck = (LeaveCheckTakenDateFinish) listSUTakenFinishDate.get(dpCheckIdx);
                               // msgString = msgString + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                               if (dpCheck.getOidDetailLeave()!=0 && Oid ==0 && dpCheck.getOidDetailLeave() != Oid) {
                                    msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                    return RSLT_FRM_DATE_IN_RANGE;
                                }
                               
                               //update by satrya 2014-01-29
                               //karena ketika update user masih bisa overlap tgl nya ,kasus cuti AL tgl 29 Januari 2014 08:00 s/d 31 januari 2014 17:00 
                               //lalu cuti tgl 29 Januari 2014 00:00 s/d 08:00,
                               //lalu di edit menjadi tgl 29 Januari 2014 08:15 maka dia "Harusnya " jadi overlap 
                               else if(objSpecialUnpaidLeaveTaken!=null && dpCheck!=null && dpCheck.getTakenDate()!=null && dpCheck.getFinishDate()!=null && objSpecialUnpaidLeaveTaken.getTakenDate()!=null && objSpecialUnpaidLeaveTaken.getTakenFinnishDate()!=null){
                                   Date newStartDate = dpCheck.getTakenDate();
                                   Date newEndDate = dpCheck.getFinishDate();
                                   Date startDate = objSpecialUnpaidLeaveTaken.getTakenDate();
                                   Date endDate = objSpecialUnpaidLeaveTaken.getTakenFinnishDate();
                                   //update by satrya 2014-02-04
                                   //penambahan Oid (Oid!=0 ? (dpCheck.getOidDetailLeave() == Oid?false:true) :  dpCheck.getOidDetailLeave() != oidLeave) && 
                                   if ((Oid!=0 ? (dpCheck.getOidDetailLeave() == Oid?false:true) :  dpCheck.getOidDetailLeave() != Oid) && newStartDate.after(objSpecialUnpaidLeaveTaken.getTakenDate()) && newStartDate.before(objSpecialUnpaidLeaveTaken.getTakenFinnishDate())) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    else if ((Oid!=0 ? (dpCheck.getOidDetailLeave() == Oid?false:true) :  dpCheck.getOidDetailLeave() != Oid) && newEndDate.after(startDate) && newEndDate.before(endDate)) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    else if ((Oid!=0 ? (dpCheck.getOidDetailLeave() == Oid?false:true) :  dpCheck.getOidDetailLeave() != Oid) && startDate.after(newStartDate) && startDate.before(newEndDate)) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    else if ((Oid!=0 ? (dpCheck.getOidDetailLeave() == Oid?false:true) :  dpCheck.getOidDetailLeave() != Oid) && endDate.after(newStartDate) && endDate.before(newEndDate)) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    else if ( (Oid!=0 ? (dpCheck.getOidDetailLeave() == Oid?false:true) :  dpCheck.getOidDetailLeave() != Oid) && newStartDate.equals(startDate) && newEndDate.equals(endDate)) {
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
                               
                               
                               
                               //end update by satrya 2014-01-29
                             }
                            
                        }


                                //priska 20151118
                   boolean data_validOfDateBeforeExpired = SessLeaveApplication.getBeforeDayExpired(objSpecialUnpaidLeaveTaken.getTakenDate(),position);
                if (data_validOfDateBeforeExpired == false) {
                    msgString = msgString + " : " + FRMMessage.getMsg(FRMMessage.MSG_DATA_OUT_OF_RANGE) + " : taken date out of LL period";
                    //untuk ngetest
                    System.out.println(""+resultText[language][RSLT_UNKNOWN_ERROR]+" ");
                    return RSLT_UNKNOWN_ERROR;
                }      
              
                if (objSpecialUnpaidLeaveTaken.getOID() == 0) {
                    try {
                        long oid = PstSpecialUnpaidLeaveTaken.insertExc(this.objSpecialUnpaidLeaveTaken);
                        if(oid!=0){
                              msgString = resultText[language][RSLT_FRM_INSERT_DATA]+" "+resultText[language][RSLT_OK];
                        }
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exc) {
                        System.out.println("Exc excuse leave insert "+ exc);
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }
                } else {
                    try {
                        long oid = PstSpecialUnpaidLeaveTaken.updateExc(this.objSpecialUnpaidLeaveTaken);
                        if(oid!=0){
                              msgString = resultText[language][RSLT_FRM_UPDATE_DATA]+" "+resultText[language][RSLT_OK];
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
                if (Oid != 0) {
                    try {
                        objSpecialUnpaidLeaveTaken = PstSpecialUnpaidLeaveTaken.fetchExc(Oid);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (Oid != 0) {
                    try {
                        objSpecialUnpaidLeaveTaken = PstSpecialUnpaidLeaveTaken.fetchExc(Oid);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (Oid != 0) {
                    try {
                        long oid = PstSpecialUnpaidLeaveTaken.deleteExc(Oid);
                        if (oid != 0) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED) + " Special Leave Taken ";
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
