/*
 * CtrlAlStockTaken.java
 *
 * Created on September 10, 2007, 5:13 PM
 */
package com.dimata.harisma.form.attendance;
// import core java package
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

// import dimata package
import com.dimata.util.*;
import com.dimata.util.lang.*;
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.harisma.session.leave.*;
// import harisma package
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.harisma.entity.leave.PstLeaveApplication;
import com.dimata.harisma.entity.leave.LeaveApplication;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.payroll.PstPayPeriod;

/**
 *
 * @author yunny
 */
public class CtrlAlStockTaken extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static int RSLT_TAKEN_DATE_WRONG = 4;
    public static int RSLT_TAKEN_DATE_OUT_PERIOD = 5;
    public static int RSLT_STOCK_AL_CAN_NOT_MINUS = 6;
    public static int RSLT_FRM_DATE_IN_RANGE = 7;
     public static int RSLT_FRM_INSERT_DATA = 8;
      public static int RSLT_FRM_UPDATE_DATA = 9;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap", "tanggal start harus lebih kecil daripada tanggal finnish", "tanggal pengambilan harus sebelum expired", " stock al tidak bisa minus", "cuti yang di request sudah ada","Menambah data AL","Ubah Data AL"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete", "Date start must be smaller than date finnish", "Date taken must be before expired date", " Stock AL can't minus", "The are overlapping leave request, please check again","Insert Data AL","Update Data AL"}
    };
    private int start;
    private String msgString;
    private AlStockTaken alStockTaken;
    private PstAlStockTaken pstAlStockTaken;
    private FrmAlStockTaken frmAlStockTaken;
    int language = LANGUAGE_FOREIGN;
    float eligbleDay = 0;

    public CtrlAlStockTaken(HttpServletRequest request) {
        msgString = "";
        alStockTaken = new AlStockTaken();
        try {
            pstAlStockTaken = new PstAlStockTaken(0);
        } catch (Exception e) {;
        }
        frmAlStockTaken = new FrmAlStockTaken(request, alStockTaken);
    }

    public CtrlAlStockTaken() {
        msgString = "";
        alStockTaken = new AlStockTaken();
        try {
            pstAlStockTaken = new PstAlStockTaken(0);
        } catch (Exception e) {;
        }
        frmAlStockTaken = new FrmAlStockTaken();
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmAlStockTaken.addError(frmAlStockTaken.FRM_FIELD_AL_STOCK_TAKEN_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public AlStockTaken getAlStockTaken() {
        return alStockTaken;
    }

    public FrmAlStockTaken getForm() {
        return frmAlStockTaken;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidLeave) {
         Position position = new Position();
        return action(cmd, oidLeave, new Vector(),position);
    }
    //priska 20151118
    public int action(int cmd, long oidLeave, Vector listal) {
        Position position = new Position();
        return action(cmd, oidLeave, listal,position);
    }

    public int action(int cmd, long oidLeave, Vector listal, Position positionOfUser ) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;

        /* Untuk menghandle agar stock al tidak minus jika tidak diperbolehkan mengambil cuti dalam kondisi stock minus*/
        //update by satrya 2012-10-24
        Vector listALTakenFinishDate = null;
        Date chkDateTaken = null;
        Date chkDateFinish = null;
        I_Leave leaveConfig = null;
        AlStockManagement alStockManagement = new AlStockManagement();
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception LEAVE_CONFIG: " + e.getMessage());
        }
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                msgString = "";
                //update by satrya 2012-11-06
                float alTakenPrev = 0.0f;
                if (oidLeave != 0) {
                    try {
                        alStockTaken = PstAlStockTaken.fetchExc(oidLeave);
                        alTakenPrev = alStockTaken.getTakenQty();
                    } catch (Exception exc) {
                        System.out.println("EXCEPTION :::: " + exc.toString());
                    }
                }
               
                frmAlStockTaken.requestEntityObject(alStockTaken);
                if (frmAlStockTaken.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }
                 try {
                 if(alStockTaken.getAlStockId()!=0){
                    alStockManagement = PstAlStockManagement.fetchExc(alStockTaken.getAlStockId());
                 }
                } catch (Exception e) {
                    System.out.println("Exception alStockManagement" + e);
                }
                 LeaveApplication leaveApp  = new LeaveApplication();
                try {
                    //LeaveApplication leaveApp = PstLeaveApplication.fetchExc(this.alStockTaken.getLeaveApplicationId());
                    //update by devin 20140409
                    leaveApp = PstLeaveApplication.fetchExc(this.alStockTaken.getLeaveApplicationId());
                    if (leaveApp.getEmployeeId() != this.alStockTaken.getEmployeeId()) {
                        Employee empLeaveApp = PstEmployee.fetchExc(leaveApp.getEmployeeId());
                        Employee empAlStokTaken = PstEmployee.fetchExc(leaveApp.getEmployeeId());
                        msgString = msgString + " : Missing link Employee of Leave Application " + empLeaveApp.getEmployeeNum()
                                + empLeaveApp.getFullName() + " and AL Stock " + empAlStokTaken.getEmployeeNum()
                                + empAlStokTaken.getFullName() + ". Please reset the taken dates";
                        return RSLT_FORM_INCOMPLETE;
                    }
                } catch (Exception exc) {
                    msgString = msgString + " : " + FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }
                /* Untuk mengecek apakah date taken berada didalam range period  */
                boolean data_valid = SessLeaveApplication.getLastDayAlPeriod(alStockTaken.getAlStockId(), alStockTaken.getTakenDate());
                if (data_valid == false) {//direvisi karena kemungkinan jika mungkin residue'nya masih ada  if (alStockManagement.getQtyResidue()<=0.0f && data_valid == false) {//update by satrya 2012-12-07    if (data_valid == false) {
                    // //di hide dulu
                    //msgString = msgString + " : " + FRMMessage.getMsg(FRMMessage.MSG_DATA_OUT_OF_RANGE) + " : taken date out of AL period";
                    //untuk ngetest
                    //System.out.println(""+resultText[language][RSLT_TAKEN_DATE_OUT_PERIOD]+" "+ alStockManagement.getQtyResidue() +  data_valid + alStockTaken.getAlStockId() + alStockTaken.getTakenDate());
                    //return RSLT_TAKEN_DATE_OUT_PERIOD;
                }
                //add by priska 20151117
                 /* Untuk mengecek apakah date taken sebelum before range  */
                boolean data_validOfDateBeforeExpired = SessLeaveApplication.getBeforeDayExpired(alStockTaken.getTakenDate(),positionOfUser);
                if (data_validOfDateBeforeExpired == false) {
                     //di hide dulu
                    //msgString = msgString + " : " + FRMMessage.getMsg(FRMMessage.MSG_DATA_OUT_OF_RANGE) + " : taken date out of AL period";
                    //System.out.println(""+resultText[language][RSLT_UNKNOWN_ERROR]+" "+ alStockManagement.getQtyResidue() +  data_validOfDateBeforeExpired + alStockTaken.getAlStockId() + alStockTaken.getTakenDate());
                    //return RSLT_UNKNOWN_ERROR;
                }
                boolean data_validOfDateAfterExpired = SessLeaveApplication.getAfterExpiredDate(alStockTaken);
                if (data_validOfDateAfterExpired == false) {
                    // //di hide dulu
                    //msgString = msgString + " : " + FRMMessage.getMsg(FRMMessage.MSG_DATA_OUT_OF_RANGE) + " : taken date out of AL period";
                    //System.out.println(""+resultText[language][RSLT_UNKNOWN_ERROR]+" "+ alStockManagement.getQtyResidue() +  data_validOfDateBeforeExpired + alStockTaken.getAlStockId() + alStockTaken.getTakenDate());
                    //return RSLT_UNKNOWN_ERROR;
                }
                /* Untuk mengecek apakah date taken finnish berada didalam range period */
                boolean data_Finish_valid = SessLeaveApplication.getLastDayAlPeriod(alStockTaken.getAlStockId(), alStockTaken.getTakenFinnishDate());
                if (data_Finish_valid == false) {////direvisi karena kemungkinan jika mungkin residue'nya masih ada  if (alStockManagement.getQtyResidue()<=0.0f && data_valid == false) { //update by satrya 2012-12-07 if (data_Finish_valid == false) {
                     //di hide dulu
                    //msgString = msgString + " : " + FRMMessage.getMsg(FRMMessage.MSG_DATA_OUT_OF_RANGE) + " : taken date out of AL period";
                    //System.out.println(""+resultText[language][RSLT_TAKEN_DATE_OUT_PERIOD]+" "+ alStockManagement.getQtyResidue() +  data_valid + alStockTaken.getAlStockId() + alStockTaken.getTakenFinnishDate());
                    //return RSLT_TAKEN_DATE_OUT_PERIOD;
                }

                boolean stsInput = SessLeaveApp.inputTakeAl(alStockTaken.getTakenDate(), alStockTaken.getEmployeeId(), alStockTaken.getTakenQty());

                if (stsInput == true) {//////direvisi karena kemungkinan jika mungkin residue'nya masih ada  if (alStockManagement.getQtyResidue()<=0.0f && stsInput == true) {//update by satrya 2012-12-07 (stsInput == true) {
                    msgString = msgString + " : " + FRMMessage.getMsg(FRMMessage.MSG_DATA_OUT_OF_RANGE) + " : taken date out of AL period";
                    
                    //untuk ngetest
                    System.out.println(""+resultText[language][RSLT_TAKEN_DATE_OUT_PERIOD]+" "+ alStockManagement.getQtyResidue() +  stsInput + alStockTaken.getTakenDate() +  alStockTaken.getEmployeeId() +  alStockTaken.getTakenQty());
                    return RSLT_TAKEN_DATE_OUT_PERIOD;
                }
                //update by satrya 2014-01-02
                //karena kasusnya di th yg baru dia mengambil sudah beda periode alias expired kasus di kti
                boolean cekExpiredAlTaken =SessLeaveApplication.cekExpiredTakenDate(alStockTaken.getEmployeeId(),alStockTaken.getTakenDate(),leaveConfig);
                if(cekExpiredAlTaken==false){
                   // sementara
                  //   msgString = msgString + " : " + FRMMessage.getMsg(FRMMessage.MSG_DATA_OUT_OF_RANGE) + " : taken date out of AL period";
                    //System.out.println(""+resultText[language][RSLT_TAKEN_DATE_OUT_PERIOD]+" "+ alStockManagement.getQtyResidue() +  stsInput + alStockTaken.getTakenDate() +  alStockTaken.getEmployeeId() +  alStockTaken.getTakenQty());
                  //  return RSLT_TAKEN_DATE_OUT_PERIOD;
                }
                //update by satrya 2014-01-02
                //karena kasusnya di th yg baru dia mengambil sudah beda periode alias expired kasus di kti
                boolean cekExpiredAlTakenFinish =SessLeaveApplication.cekExpiredTakenFinishDate(alStockTaken.getEmployeeId(),alStockTaken.getTakenFinnishDate(),leaveConfig);
                if(cekExpiredAlTakenFinish==false){
                   //di hide dulu
                    //  msgString = msgString + " : " + FRMMessage.getMsg(FRMMessage.MSG_DATA_OUT_OF_RANGE) + " : taken finish date out of AL period";
                    //System.out.println(""+resultText[language][RSLT_TAKEN_DATE_OUT_PERIOD]+" "+ alStockManagement.getQtyResidue() +  stsInput + alStockTaken.getTakenDate() +  alStockTaken.getEmployeeId() +  alStockTaken.getTakenQty());
                   // return RSLT_TAKEN_DATE_OUT_PERIOD;
                }
//                if (((((alStockTaken.getTakenFinnishDate().getTime()) - (alStockTaken.getTakenDate().getTime())) / (24L * 60L * 60L * 1000L)) ) < (3) )  {
//                    msgString = msgString + " : " + FRMMessage.getMsg(FRMMessage.ERR_OUT_OF_RANGE);
//                    return RSLT_FORM_INCOMPLETE;
//                }
                if ((alStockTaken.getTakenDate().getTime() / (24L * 60L * 60L * 1000L)) > (alStockTaken.getTakenFinnishDate().getTime() / (24L * 60L * 60L * 1000L))) {
                    msgString = msgString + " : " + FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_TAKEN_DATE_WRONG;
                }
                float maxMinusAl=0;
                     try {
                        maxMinusAl = Float.parseFloat(PstSystemProperty.getValueByName("MAX_LEAVE_AL_MINUS"));
                       
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);

                    }
                    
                
               
                     
                if (leaveConfig.getALStockMinus(alStockTaken) == false ) { // dilakukan untuk pengecekan sisa stock jika stock tidak boleh minus
                    ///belum selesai
                    //sudah ada di konfigurasi leaveConfig.getALStockMinus(alStockTaken)
                   //float stockAl = SessLeaveApplication.getEligibleAnnualLeave(leaveConfig, alStockTaken) - alStockTaken.getTakenQty();

                   // if (stockAl < 0) {
        
                     float stockAl = SessLeaveApplication.getEligibleAnnualLeave(null, alStockTaken) ;
                        msgString = msgString + " : " + (leaveConfig.isMessageUseAdvanceMinusLimit() ? (FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE) + " : ' your AL request make Eligible " + stockAl +" that exceed maximum minus for AL "+maxMinusAl * -1+" ") :FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE));
                        return RSLT_STOCK_AL_CAN_NOT_MINUS;
                    //}

                }
                /*
                 @DESC : UNTUK MENGECEK TAKEN AGAR ELIGIBLE TIDAK KURANG DARI 0, UNTUK HADROCK TIDAK BERLAKU
                
                 if(alStockTaken.getOID() != 0){
                    
                 int diff = SessLeaveApplication.DATEDIFF(alStockTaken.getTakenFinnishDate(),alStockTaken.getTakenDate());
                    
                 int tkn = diff + 1;
                    
                 if(tkn > 0){
                        
                 int eligible = SessLeaveApplication.getAlEligbleDay(alStockTaken.getEmployeeId(),alStockTaken.getOID(), tkn);
                        
                 if(eligible < 0){
                 msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                 return RSLT_TAKEN_DATE_WRONG;
                 }                        
                 }                    
                    
                 }else{
                    
                 int diff = SessLeaveApplication.DATEDIFF(alStockTaken.getTakenFinnishDate(),alStockTaken.getTakenDate());
                    
                 int tkn = diff + 1;
                    
                 if(tkn > 0){
                        
                 int eligible = SessLeaveApplication.getAlEligbleDayHaveStock(alStockTaken.getEmployeeId(), tkn);
                        
                 if(eligible < 0){
                 msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                 return RSLT_TAKEN_DATE_WRONG;
                 }                        
                 }
                 }
                 */

                //Vector dateStatus = SessLeaveApp.getCompareDate(alStockTaken.getTakenDate(),alStockTaken.getTakenFinnishDate(),alStockTaken.getLeaveApplicationId());

                /*
                 if(dateStatus != null){
                 msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                 return RSLT_TAKEN_DATE_WRONG;
                 }
                 */
                //update by satrya 2012-10-08
                //agar eligible tidak minus
                if (listal != null && listal.size() > 0) {
                    RepItemLeaveAndDp item = null;
                    item = (RepItemLeaveAndDp) listal.get(0);
                    for (int dpIdx = 0; dpIdx < listal.size(); dpIdx++) {
                        item = (RepItemLeaveAndDp) listal.get(dpIdx);
                        eligbleDay = item.getALTotal() - item.getALTaken() - item.getAL2BTaken();
                    }
                    //update by satrya 2012-11-06
                    eligbleDay = eligbleDay + alTakenPrev;
                    if (((eligbleDay - alStockTaken.getTakenQty()) < 0) && (eligbleDay <= (0 - maxMinusAl))){
                        float stockAl = SessLeaveApplication.getEligibleAnnualLeave(null, alStockTaken) ;
                        msgString = msgString + " : " + (leaveConfig.isMessageUseAdvanceMinusLimit() ? (FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE) + " : ' your AL request make Eligible " + stockAl +" that exceed maximum minus for AL "+maxMinusAl * -1+" ") :FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE));
                        return RSLT_STOCK_AL_CAN_NOT_MINUS;
                    }
                    if (((eligbleDay - alStockTaken.getTakenQty()) < 0) && leaveConfig.getALStockMinus(alStockTaken) == false) {
                        //update by satrya 2013-08-18
                        //if (((eligbleDay - alStockTaken.getTakenQty()) < 0) && leaveConfig.getALStockMinus() == false) {
                        msgString = (leaveConfig.isMessageUseAdvanceMinusLimit() ? (FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE) + " : ' Your advance leave exceeded  maximum limit " + " ' ") :FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE));
                        return RSLT_STOCK_AL_CAN_NOT_MINUS;
                    }
                }
                //update by satrya 2013-02-27
                //mencari nilai takenDate yg sudah ada
                listALTakenFinishDate = SessLeaveApp.checkOverLapsLeaveTaken(alStockTaken.getEmployeeId(), alStockTaken.getTakenDate(),alStockTaken.getTakenFinnishDate());
                
                //update by satrya 2012-10-24
                //cek jika user memilih taken date dan finish date msh dalam 1 range 
                      // listALTakenFinishDate = SessLeaveApp.checkOverLapsLeaveTaken(alStockTaken.getEmployeeId(), takenDatex,finishTakenDatex);
                        
                        if(listALTakenFinishDate != null && listALTakenFinishDate.size() > 0){
                         //    chekError = true; 
                        //update by satrya 2013-01-15
                         //msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:";
                             for (int dpCheckIdx = 0; dpCheckIdx < listALTakenFinishDate.size(); dpCheckIdx++) {
                               LeaveCheckTakenDateFinish dpCheck = (LeaveCheckTakenDateFinish) listALTakenFinishDate.get(dpCheckIdx);
                               // msgString = msgString + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                               if (dpCheck.getOidDetailLeave()!=0 && oidLeave ==0 && dpCheck.getOidDetailLeave() != oidLeave) {
                                    msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                    return RSLT_FRM_DATE_IN_RANGE;
                                }
                               
                               //update by satrya 2014-01-29
                               //karena ketika update user masih bisa overlap tgl nya ,kasus cuti AL tgl 29 Januari 2014 08:00 s/d 31 januari 2014 17:00 
                               //lalu cuti tgl 29 Januari 2014 00:00 s/d 08:00,
                               //lalu di edit menjadi tgl 29 Januari 2014 08:15 maka dia "Harusnya " jadi overlap 
                               else if(alStockTaken!=null && dpCheck!=null && dpCheck.getTakenDate()!=null && dpCheck.getFinishDate()!=null && alStockTaken.getTakenDate()!=null && alStockTaken.getTakenFinnishDate()!=null){
                                   Date newStartDate = dpCheck.getTakenDate();
                                   Date newEndDate = dpCheck.getFinishDate();
                                   Date startDate = alStockTaken.getTakenDate();
                                   Date endDate = alStockTaken.getTakenFinnishDate();
                                   if ((oidLeave!=0 ? (dpCheck.getOidDetailLeave() == oidLeave?false:true) :  dpCheck.getOidDetailLeave() != oidLeave) &&newStartDate.after(alStockTaken.getTakenDate()) && newStartDate.before(alStockTaken.getTakenFinnishDate())) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    else if ((oidLeave!=0 ? (dpCheck.getOidDetailLeave() == oidLeave?false:true) :  dpCheck.getOidDetailLeave() != oidLeave) &&newEndDate.after(startDate) && newEndDate.before(endDate)) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    else if ((oidLeave!=0 ? (dpCheck.getOidDetailLeave() == oidLeave?false:true) :  dpCheck.getOidDetailLeave() != oidLeave) &&startDate.after(newStartDate) && startDate.before(newEndDate)) {
                                         msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                        return RSLT_FRM_DATE_IN_RANGE;
                                    }
                                    else if ( (oidLeave!=0 ? (dpCheck.getOidDetailLeave() == oidLeave?false:true) :  dpCheck.getOidDetailLeave() != oidLeave) && endDate.after(newStartDate) && endDate.before(newEndDate)) {
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
                        
                
                
                 //cek payperiode apa
                //long periodId = PstPayPeriod.getPayPeriodIdBySelectedDate(alStockTaken.getTakenDate()); 

                if (alStockTaken.getOID() == 0) {
                    try {
                        long oid = PstAlStockTaken.insertExc(this.alStockTaken);
                       
                        if(oid!=0){
                              msgString = resultText[language][RSLT_FRM_INSERT_DATA]+" "+resultText[language][RSLT_OK];
                        }
                       
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = msgString + " : " + getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }

                } else {
                    try {
                        long oid = PstAlStockTaken.updateExc(this.alStockTaken);
                        //priska menambahkan cek al allowance 20150805 update nilai untuk al allowance
                        
                        //cek AL untuk allowance sudah ada atau belum 
                        //priska menambahkan untuk menambahkan tanda bahwa AL yang pertama semnnjak closing yang sebelumnya           
//                        if ((leaveConfig.getALallowance(alStockTaken, periodId, alStockTaken.getEmployeeId() ) == true )) {
//                            try{
//                                LeaveApplication leaveApplication = PstLeaveApplication.fetchExc(alStockTaken.getLeaveApplicationId());
//                                leaveApplication.setAlAllowance(1);
//                                PstLeaveApplication.updateExc(leaveApplication);
//                            }catch (Exception e){
//                                
//                            }
//                        } 
                        if(oid!=0){
                              msgString = resultText[language][RSLT_FRM_UPDATE_DATA]+" "+resultText[language][RSLT_OK];
                        }
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = msgString + " : " + getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                msgString = "";
                if (oidLeave != 0) {
                    try {
                        alStockTaken = PstAlStockTaken.fetchExc(oidLeave);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = msgString + " : " + getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = msgString + " : " + getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidLeave != 0) {
                    try {
                        alStockTaken = PstAlStockTaken.fetchExc(oidLeave);
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
                        long oid = PstAlStockTaken.deleteExc(oidLeave);
                        if (oid != 0) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED) + " AL Taken ";
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
                        alStockTaken = PstAlStockTaken.fetchExc(oidLeave);
                    } catch (Exception exc) {
                    }
                }

                alStockTaken.setOID(stockTaken.getOID());
                alStockTaken.setAlStockId(stockTaken.getAlStockId());
                alStockTaken.setEmployeeId(stockTaken.getEmployeeId());
                alStockTaken.setTakenDate(stockTaken.getTakenDate());
                alStockTaken.setTakenQty(stockTaken.getTakenQty());
                alStockTaken.setPaidDate(stockTaken.getPaidDate());
                alStockTaken.setTakenFromStatus(stockTaken.getTakenFromStatus());

                if (alStockTaken.getOID() == 0) {
                    try {
                        long oid = PstAlStockTaken.insertExc(this.alStockTaken);
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
                        long oid = PstAlStockTaken.updateExc(this.alStockTaken);
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
                        alStockTaken = PstAlStockTaken.fetchExc(oidLeave);
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
                        alStockTaken = PstAlStockTaken.fetchExc(oidLeave);
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
                        long oid = PstAlStockTaken.deleteExc(oidLeave);
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
