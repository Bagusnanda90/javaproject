/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.masterdata;
import com.dimata.harisma.entity.attendance.AlStockManagement;
import com.dimata.harisma.entity.attendance.AlStockTaken;
import com.dimata.harisma.entity.attendance.PstAlStockManagement;
import com.dimata.harisma.entity.attendance.PstAlStockTaken;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.leave.LeaveApplication;
import com.dimata.harisma.entity.leave.PstLeaveApplication;
import com.dimata.harisma.entity.masterdata.TblCekErrorPublicLeave;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstPublicHolidays;
import com.dimata.harisma.entity.masterdata.PstPublicLeave;
import com.dimata.harisma.entity.masterdata.PstPublicLeaveDetail;
import com.dimata.harisma.entity.masterdata.PublicLeave;
import com.dimata.harisma.entity.masterdata.PublicLeaveDetail;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.session.leave.SessLeaveApp;
import com.dimata.harisma.session.leave.SessLeaveApplication;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Command;
import com.dimata.util.DateCalc;
import com.dimata.util.lang.I_Language;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
/**
 *
 * @author Dimata 007
 */
public class CtrlPublicLeaveDetail extends Control implements I_Language{
     public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
     public static int RSLT_FORM_ERROR = 3;

    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap","ERROR"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete","ERROR"}
    };

    private int start;
    private String msgString;
     //update by devin 2104-02-17
    private TblCekErrorPublicLeave tblCekErrorPublicLeave = new TblCekErrorPublicLeave();
    private PublicLeaveDetail objPublicLeaveDetail;
    private PstPublicLeaveDetail objPstPublicLeaveDetail;
    private FrmPublicLeaveDetail objFrmPublicLeaveDetail;
    int language = LANGUAGE_FOREIGN;

    public CtrlPublicLeaveDetail(HttpServletRequest request) {
        msgString = "";
        objPublicLeaveDetail = new PublicLeaveDetail();
        try {
            objPstPublicLeaveDetail = new PstPublicLeaveDetail(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objFrmPublicLeaveDetail = new FrmPublicLeaveDetail(request, objPublicLeaveDetail);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.objFrmPublicLeaveDetail.addError(FrmPublicLeaveDetail.FRM_FIELD_PUBLIC_LEAVE_DETAIL_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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
     //update by devin 2104-02-17
 public TblCekErrorPublicLeave tblCekErrorPublicLeave() {
        return tblCekErrorPublicLeave;
    }
    public void setLanguage(int language) {
        this.language = language;
    }

    public PublicLeaveDetail getPublicLeaveDetail() {
        return objPublicLeaveDetail;
    }

    public FrmPublicLeaveDetail getForm() {
        return objFrmPublicLeaveDetail;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int iCmd, long lOidPublicLeaveId,Vector dayLeave,long sysPublicholidays,long oidPublicHoliday) {
        int iRsCode = RSLT_OK;
        switch (iCmd) {
            case Command.ADD:
                break;
            case Command.SAVE:
                iRsCode = actionSave(lOidPublicLeaveId,dayLeave,sysPublicholidays,oidPublicHoliday);
                break;
            /*case Command.EDIT:
                iRsCode = actionEditOrAsk(lOidPublicLeaveId);
                break;
            case Command.ASK:
                iRsCode = actionEditOrAsk(lOidPublicLeaveId);
                break;*/
            /*case Command.DELETE:
                iRsCode = actionDelete(lOidPublicLeaveId);
                break;*/
            default:
                break;
        }

        return iRsCode;
    }

    private int actionSave(long lOidPublicLeaveDetail,Vector dayLeave,long sysPublicholiday,long publicHoliday) {
        msgString = "";
            I_Leave leaveConfig = null;
        
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception LEAVE_CONFIG: " + e.getMessage());
        }
        
        int reasonDC=0;
        try {
            reasonDC = Integer.parseInt(PstSystemProperty.getValueByName("SICK_REASON_WITH_DC"));
        } catch (Exception e) {
            System.out.println("Exception Reason DC: " + e.getMessage());
        }
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        //update by devin 2014-02-10
           int c=0;
        if (lOidPublicLeaveDetail != 0) {
            try {
                objPublicLeaveDetail = objPstPublicLeaveDetail.fetchExc(lOidPublicLeaveDetail);
            } catch (DBException dbe) {
                dbe.printStackTrace();
            }
        }
       
        //objFrmPublicLeaveDetail.requestEntityObject(objPublicLeaveDetail);
    
         objFrmPublicLeaveDetail.requestEntityObjectMultiple();
        if (objFrmPublicLeaveDetail!=null && objFrmPublicLeaveDetail.errorSize() > 0) {
            msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
            return RSLT_FORM_INCOMPLETE;
        }
        if(objFrmPublicLeaveDetail.getPublicLeaves()!=null && objFrmPublicLeaveDetail.getPublicLeaves().size()>0){
         for(int idx=0; idx < objFrmPublicLeaveDetail.getPublicLeaves().size(); idx++){ 
             PublicLeaveDetail publicLeaveDetails = (PublicLeaveDetail) objFrmPublicLeaveDetail.getPublicLeaves().get(idx);
              String whereClause = PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_PUBLIC_HOLIDAY_ID]+"="+publicHoliday 
                      +" AND "+ PstPublicLeaveDetail.fieldNames[PstPublicLeaveDetail.FLD_EMPLOYEE_ID]+"="+publicLeaveDetails.getEmployeeId() ;
        Vector checkPublicdetailSdhAda = PstPublicLeaveDetail.list(0, 0, whereClause, "");
        
                if(dayLeave!=null && dayLeave.size()>0){
                    for(int idl=0; idl < dayLeave.size(); idl++){
                        PublicLeave publicLeave = (PublicLeave)dayLeave.get(idl);
                        //di cek jika dia typenya permanen atau DW
                        boolean chkDC = PstEmpSchedule.getchkDC(publicLeaveDetails.getEmployeeId() ,  publicLeave.getDateLeaveFrom(), reasonDC);
                        if(publicLeaveDetails.getEmpCategoryId()!=0 && chkDC && checkPublicdetailSdhAda.size()<1 && publicLeaveDetails.getEmpCategoryId()==publicLeave.getEmpCat()){
                            //jika type'nya OFF
                            if(publicLeave.getTypeLeave()==sysPublicholiday){
                         
                             //jika jam yg di setting off'nya full 1 hari maka di set schedulenya OFF 
                             if(publicLeave.getDateLeaveFrom().getHours()== publicLeave.getDateLeaveTo().getHours() 
                                         && publicLeave.getDateLeaveFrom().getMinutes()== publicLeave.getDateLeaveTo().getMinutes()){
                                 
                                 //ScheduleSymbol scheduleSymbols = PstEmpSchedule.getDailySchedule(publicLeave.getDateLeaveFrom(), publicLeaveDetails.getEmployeeId());
                                 Period periodId = PstPeriod.getPeriodBySelectedDate(publicLeave.getDateLeaveFrom());
                                 //prosess update schedule menjadi holiday/ OFF
                               int updSch= PstEmpSchedule.updateSchedule(periodId.getOID(), publicLeaveDetails.getEmployeeId(), publicLeave.getDateLeaveFrom(),publicLeave.getTypeLeave());
                               
                               //System.out.print("Update Schedule"+updSch);
                               
                                PublicLeaveDetail publicLeaveDetail = new PublicLeaveDetail();
                                publicLeaveDetail.setEmployeeId(publicLeaveDetails.getEmployeeId());
                                publicLeaveDetail.setTypeLeaveId(publicLeave.getTypeLeave());
                                publicLeaveDetail.setPublicLeaveId(publicLeave.getOID());
                                publicLeaveDetail.setPublicHolidayId(publicLeave.getPublicHolidayId());
                                publicLeaveDetail.setDateFrom(publicLeave.getDateLeaveFrom());
                                  publicLeaveDetail.setDateTo(publicLeave.getDateLeaveTo());
                                    try{
                                       if(lOidPublicLeaveDetail==0){
                                          if(updSch!=0){
                                           long ins = PstPublicLeaveDetail.insertExc(publicLeaveDetail); 
                                        
                                           c=c+1;
                                           //update by devin 2014-02-17
                                             long oid=publicLeaveDetails.getEmployeeId();
                                        tblCekErrorPublicLeave.addCekErrorSdhAdaDetail(oid, "<p style=\"color:#0000FF\">Success</p>");
                            
                                          }
                                       }else{
                                            if(updSch!=0){
                                           long upd = PstPublicLeaveDetail.updateExc(publicLeaveDetail); 
                                            //update by devin 2014-02-10
                                           long oid=publicLeaveDetails.getEmployeeId();
                                            tblCekErrorPublicLeave.addCekErrorSdhAdaDetail(oid, "<p style=\"color:#0000FF\">Success</p>");
                                            c=c+1;
                                          }
                                           
                                       }
                                    
                                    }catch(Exception exc){
                                        System.out.println("Exception insert public leave detail"+exc);
                                    }
                                }else{
                                   PublicLeaveDetail publicLeaveDetail = new PublicLeaveDetail();
                                publicLeaveDetail.setEmployeeId(publicLeaveDetails.getEmployeeId());
                                publicLeaveDetail.setTypeLeaveId(publicLeave.getTypeLeave());
                                publicLeaveDetail.setPublicLeaveId(publicLeave.getOID());
                                publicLeaveDetail.setPublicHolidayId(publicLeave.getPublicHolidayId());
                                 publicLeaveDetail.setDateFrom(publicLeave.getDateLeaveFrom());
                                  publicLeaveDetail.setDateTo(publicLeave.getDateLeaveTo());
                                    try{
                                     if(lOidPublicLeaveDetail==0){
                                          
                                           long ins = PstPublicLeaveDetail.insertExc(publicLeaveDetail); 
                                           //update by devin 2014-02-17
                                           long oid=publicLeaveDetails.getEmployeeId();                         
                                        tblCekErrorPublicLeave.addCekErrorSdhAdaDetail(oid, "<p style=\"color:#0000FF\">Success</p>");
                                       }else{
                                           long upd = PstPublicLeaveDetail.updateExc(publicLeaveDetail); 
                                           //update by devin 2014-02-17
                                           long oid=publicLeaveDetails.getEmployeeId();                           
                                        tblCekErrorPublicLeave.addCekErrorSdhAdaDetail(oid, "<p style=\"color:#0000FF\">Success</p>");
                                       }
                                    }catch(Exception exc){
                                        System.out.println("Exception insert public leave detail"+exc);
                                    }
                              }
                            }
                            //jika typenya Leave
                            else if(publicLeave.getTypeLeave()==PstPublicLeaveDetail.TYPE_LEAVE  && checkPublicdetailSdhAda.size()<1){
                                ScheduleSymbol scheduleSymbol = PstEmpSchedule.getDailySchedule(publicLeave.getDateLeaveFrom(), publicLeaveDetails.getEmployeeId());
                                AlStockManagement alStockManagement = PstAlStockManagement.getAlStockManagement(publicLeaveDetails.getEmployeeId());
                                AlStockTaken alStockTaken = new AlStockTaken();
                                Date startdt = (Date) publicLeave.getDateLeaveFrom().clone();
                                    startdt.setHours(0);
                                    startdt.setMinutes(0);
                                    startdt.setSeconds(0);
                                Date enddt = (Date) publicLeave.getDateLeaveTo().clone();
                                    enddt.setHours(23);
                                    enddt.setMinutes(59);
                                    enddt.setSeconds(59);
                                    //mencari apakah sudah ada leave pada hari itu
                                Vector listTakenFinishDateLeave = SessLeaveApp.checkOverLapsLeaveTaken(publicLeaveDetails.getEmployeeId(), startdt,enddt);
                                if (scheduleSymbol!=null && scheduleSymbol.getTimeOut() != null && scheduleSymbol.getTimeIn() != null) {
                                        if(scheduleSymbol.getSymbol()!=null && scheduleSymbol.getSymbol().length()>0 && listTakenFinishDateLeave.size()<1 
                                                && (!(scheduleSymbol.getSymbol().equalsIgnoreCase("OFF"))
                                                //update by satrya 2013-05-06
                                                //jika schedulenya off itu di ganti atau tidak ada
                                                || !(scheduleSymbol.getTimeIn().getHours()==0 && scheduleSymbol.getTimeIn().getMinutes()==0  && scheduleSymbol.getTimeOut().getHours()==0 && scheduleSymbol.getTimeOut().getMinutes()==0))){
                                            
                                                Date takenDateTmp = new Date(publicLeave.getDateLeaveFrom().getTime());
                                                takenDateTmp.setHours(scheduleSymbol.getTimeIn().getHours());
                                                takenDateTmp.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                                                takenDateTmp.setSeconds(scheduleSymbol.getTimeIn().getSeconds());
                                                
                                                LeaveApplication leaveApplication = new LeaveApplication();
                                                leaveApplication.setSubmissionDate(takenDateTmp);
                                                leaveApplication.setEmployeeId(publicLeaveDetails.getEmployeeId());
                                                leaveApplication.setLeaveReason(publicLeaveDetails.getNote());
                                                leaveApplication.setDocStatus(I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED);
                                                leaveApplication.setTypeLeave(PstPublicLeaveDetail.TYPE_LEAVE ); 
                                                 long oidLeaveApp = 0;
                                                 try{
                                                        oidLeaveApp = PstLeaveApplication.insertExc(leaveApplication);
                                                 }catch(Exception exc){
                                                     System.out.println("Exc");
                                                 }
                                                 long durationTime = DateCalc.timeDifference(publicLeave.getDateLeaveFrom(), publicLeave.getDateLeaveTo());
                                                 long alTakenOid=0;
                                            if(publicLeave.getFlagSch()==PstPublicLeave.FLAG_SCH_AWAL){
                                                long durationBreakTime = DateCalc.timeDifference(scheduleSymbol.getBreakOut(), scheduleSymbol.getBreakIn());
                                                long hasilAkhir = (durationTime ) + takenDateTmp.getTime();
                                                Date finishDateTmp = new Date(hasilAkhir);
                                                alStockTaken.setAlStockId(alStockManagement.getOID());
                                                alStockTaken.setEmployeeId(publicLeaveDetails.getEmployeeId());
                                                alStockTaken.setTakenDate(takenDateTmp);
                                                alStockTaken.setTakenFinnishDate(finishDateTmp);
                                                float tmpTakenQty =0;
                                                long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(alStockManagement.getEmployeeId(), alStockTaken.getTakenDate(), alStockTaken.getTakenFinnishDate());
                                                if (intersecX != 0) {
                                                    alStockTaken.setTakenFinnishDate(new Date(alStockTaken.getTakenFinnishDate().getTime() + durationBreakTime));
                                                    long intersecXX = PstEmpSchedule.breakTimeIntersectionVer2(alStockManagement.getEmployeeId(), alStockTaken.getTakenDate(), alStockTaken.getTakenFinnishDate());
                                                    long lTakenFinishDatex = alStockTaken.getTakenFinnishDate().getTime() - intersecXX;
                                                    Date takenFinish = new Date(lTakenFinishDatex);
                                                    tmpTakenQty = DateCalc.workDayDifference(alStockTaken.getTakenDate(), takenFinish, leaveConfig.getHourOneWorkday());
                                                }else{
                                               
                                                    tmpTakenQty = DateCalc.workDayDifference(alStockTaken.getTakenDate(), alStockTaken.getTakenFinnishDate(), leaveConfig.getHourOneWorkday());
                                                }
                                                
                                                //float tmpTakenQty = DateCalc.workDayDifference(alStockTaken.getTakenDate(), alStockTaken.getTakenFinnishDate(), leaveConfig.getHourOneWorkday());
                                                alStockTaken.setTakenQty(tmpTakenQty);
                                                alStockTaken.setLeaveApplicationId(oidLeaveApp);
                                                
                                                
                                            }else{
                                                Date finishDateTmp = new Date(publicLeave.getDateLeaveTo().getTime());
                                                finishDateTmp.setHours(scheduleSymbol.getTimeOut().getHours());
                                                finishDateTmp.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                                                finishDateTmp.setSeconds(scheduleSymbol.getTimeOut().getSeconds());
                                                long durationBreakTime = DateCalc.timeDifference(scheduleSymbol.getBreakOut(), scheduleSymbol.getBreakIn());
                                                long hasilAkhir = finishDateTmp.getTime() - (durationTime);
                                                Date takenDateTmpx = new Date(hasilAkhir);
                                                
                                                ///belum ada pengecekan jika residuenya 0
                                                alStockTaken.setAlStockId(alStockManagement.getOID());
                                                 alStockTaken.setEmployeeId(publicLeaveDetails.getEmployeeId());
                                                alStockTaken.setTakenDate(takenDateTmpx);
                                                alStockTaken.setTakenFinnishDate(finishDateTmp);
                                                float tmpTakenQty =0;
                                                long intersecX = PstEmpSchedule.breakTimeIntersectionVer2(alStockManagement.getEmployeeId(), alStockTaken.getTakenDate(), alStockTaken.getTakenFinnishDate());
                                                if (intersecX != 0) {
                                                    alStockTaken.setTakenDate(new Date(alStockTaken.getTakenDate().getTime() - durationBreakTime));
                                                    long intersecXX = PstEmpSchedule.breakTimeIntersectionVer2(alStockManagement.getEmployeeId(), alStockTaken.getTakenDate(), alStockTaken.getTakenFinnishDate());
                                                    long lTakenFinishDatex = alStockTaken.getTakenFinnishDate().getTime() - intersecXX;
                                                    Date takenFinish = new Date(lTakenFinishDatex);
                                                    tmpTakenQty = DateCalc.workDayDifference(alStockTaken.getTakenDate(), takenFinish, leaveConfig.getHourOneWorkday());
                                                }else{
                                                //float tmpTakenQty = DateCalc.workDayDifference(alStockTaken.getTakenDate(), alStockTaken.getTakenFinnishDate(), leaveConfig.getHourOneWorkday());
                                                    tmpTakenQty = DateCalc.workDayDifference(alStockTaken.getTakenDate(), alStockTaken.getTakenFinnishDate(), leaveConfig.getHourOneWorkday());
                                                }
                                                //float hasilSisaTakenX = tmpTakenQty - alStockManagement.getAlQty();
                                                alStockTaken.setTakenQty(tmpTakenQty);
                                                alStockTaken.setLeaveApplicationId(oidLeaveApp);
                                            
                                            
                                            }
                                                try{
                                                 alTakenOid = PstAlStockTaken.insertExc(alStockTaken);
                                                }catch(Exception exc){
                                                    System.out.println("Exc Insert"+exc);
                                                }
                                        
                                                if(alTakenOid!=0){
                                                PublicLeaveDetail publicLeaveDetailXX = new PublicLeaveDetail();
                                                publicLeaveDetailXX.setEmployeeId(publicLeaveDetails.getEmployeeId());
                                                publicLeaveDetailXX.setTypeLeaveId(publicLeave.getTypeLeave());
                                                publicLeaveDetailXX.setPublicLeaveId(publicLeave.getOID());
                                                publicLeaveDetailXX.setPublicHolidayId(publicLeave.getPublicHolidayId());
                                                publicLeaveDetailXX.setAppLeaveId(oidLeaveApp);
                                                publicLeaveDetailXX.setDateFrom(publicLeave.getDateLeaveFrom());
                                                publicLeaveDetailXX.setDateTo(publicLeave.getDateLeaveTo());
                                                try{
                                                long ins = PstPublicLeaveDetail.insertExc(publicLeaveDetailXX); 
                                                 //update by devin 2014-02-10
                                                 c=c+1;
                                                 //update by devin 2014-02-17
                                                 long oid=publicLeaveDetails.getEmployeeId();
                                                 tblCekErrorPublicLeave.addCekErrorSdhAdaDetail(oid, "<p style=\"color:#0000FF\">Success</p>");
                                                }catch(Exception exc){
                                                    System.out.println("Exception insert public leave detail"+exc);
                                                }
                                                }
                                         }else{
                                            //update by devin 2014-02-17
                                             long oid=publicLeaveDetails.getEmployeeId();                           
                                             tblCekErrorPublicLeave.addCekErrorGagalTersimpan(oid, "<p title=\"Gagal Tersimpan karena schedulenya off\" style=\"color:#FF0000\">Failed</p>");
                                         }
                                     }
                            }
                        }else{
                           //update by devin 2014-02-17
                            long oid=publicLeaveDetails.getEmployeeId();
                            tblCekErrorPublicLeave.addCekErrorCategoryEmp(oid, "<p title=\"Gagal Tersimpan karena category tidak cocok dengan form cuti\" style=\"color:#FF0000\">Failed</p>");
                        }
                        
                    }
                
                
                }
                
         }
        
    }
//        if (objPublicLeaveDetail.getOID() == 0) {
//            try {
//                long insc = objPstPublicLeaveDetail.insertExc(objPublicLeaveDetail);
//                if(insc!=0){
//                 msgString = FRMMessage.getMsg(FRMMessage.MSG_SAVED);
//                }
//            } catch (DBException dbe) {
//                excCode = dbe.getErrorCode();
//                msgString = getSystemMessage(excCode);
//                return getControlMsgId(excCode);
//            } catch (Exception exc) {
//                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//            }
//        } else {
//            try {
//               long upd = objPstPublicLeaveDetail.updateExc(objPublicLeaveDetail);
//                if(upd!=0){
//                 msgString = FRMMessage.getMsg(FRMMessage.MSG_UPDATED);
//                }
//            } catch (DBException dbe) {
//                excCode = dbe.getErrorCode();
//                msgString = getSystemMessage(excCode);
//                return getControlMsgId(excCode);
//            } catch (Exception exc) {
//                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
//                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
//            }
//        }

        return c;
    }

    public int actionEditOrAsk(long lOidPublicLeaveDetail) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        if (lOidPublicLeaveDetail != 0) {
            try {
                objPublicLeaveDetail = objPstPublicLeaveDetail.fetchExc(lOidPublicLeaveDetail);
            } catch (DBException dbe) {
                excCode = dbe.getErrorCode();
                msgString = getSystemMessage(excCode);
                return getControlMsgId(excCode);
            } catch (Exception exc) {
                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
            }
        }
        return excCode;
    }

    public int actionDelete(long lOidPublicLeaveDetaiL) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        if (lOidPublicLeaveDetaiL != 0) {
            try {
                long oid = objPstPublicLeaveDetail.deleteExc(lOidPublicLeaveDetaiL);
                if (oid != 0) {
                    msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                    excCode = RSLT_OK;
                } else {
                    msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                    excCode = RSLT_FORM_INCOMPLETE;
                }
            } catch (DBException dbe) {
                excCode = dbe.getErrorCode();
                msgString = getSystemMessage(excCode);
                return getControlMsgId(excCode);
            } catch (Exception exc) {
                msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
            }
        }
        return excCode;
    }
}
