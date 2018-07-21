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

// import harisma package
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.leave.LeaveApplication;
import com.dimata.harisma.entity.leave.PstLeaveApplication;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.payroll.PstPayPeriod;
import com.dimata.harisma.session.leave.RepItemLeaveAndDp;
import com.dimata.harisma.session.leave.SessLeaveApp;
import com.dimata.harisma.session.leave.SessLeaveApplication;
import com.dimata.system.entity.PstSystemProperty;

/**
 *
 * @author  yunny
 */
public class CtrlLlStockTaken  extends Control implements I_Language{
    
    public static int RSLT_OK                   = 0;
    public static int RSLT_UNKNOWN_ERROR        = 1;
    public static int RSLT_EST_CODE_EXIST       = 2;
    public static int RSLT_FORM_INCOMPLETE      = 3;
    public static int RSLT_TAKEN_DATE_WRONG     = 4;    
    public static int RSLT_TAKEN_DATE_EXPIRED   = 5;  
    public static int RSLT_STOCK_LL_CAN_NOT_MINUS   = 6;
     public static int RSLT_FRM_DATE_IN_RANGE = 7;
     public static int RSLT_FRM_INSERT_DATA = 8;
      public static int RSLT_FRM_UPDATE_DATA = 9;
    
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap","tanggal start harus lebih kecil daripada tanggal finnish","pengambilan leave melebihi waktu expired","Eligible tidak boleh minus","cuti yang di request sudah ada","Menambah data LL","Ubah Data LL"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete","Date start must be smaller than date finnish","Taken leave more than date expired","Eligible can't minus"," The are overlapping leave request, please check again","Insert Data LL","Update Data LL"}
    };
    
    private int start;
    private String msgString;
    private LlStockTaken llStockTaken;
    private PstLlStockTaken pstLlStockTaken;
    private FrmLlStockTaken frmLlStockTaken;
    int language = LANGUAGE_FOREIGN;
   
         
     public CtrlLlStockTaken(HttpServletRequest request){
        msgString = "";
        llStockTaken = new LlStockTaken();
        try{
            pstLlStockTaken = new PstLlStockTaken(0);
        }catch(Exception e){;}
        frmLlStockTaken = new FrmLlStockTaken(request, llStockTaken);
    }
     
     public CtrlLlStockTaken(){
        msgString = "";
        llStockTaken = new LlStockTaken();
        try{
            pstLlStockTaken = new PstLlStockTaken(0);
        }catch(Exception e){;}
         frmLlStockTaken = new FrmLlStockTaken();
    }
     
      private String getSystemMessage(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                this.frmLlStockTaken.addError(frmLlStockTaken.FRM_FIELD_LL_STOCK_TAKEN_ID, resultText[language][RSLT_EST_CODE_EXIST] );
                return resultText[language][RSLT_EST_CODE_EXIST];
            default:
                return resultText[language][RSLT_UNKNOWN_ERROR];
        }
    }
    
     
       private int getControlMsgId(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                return RSLT_EST_CODE_EXIST;
            default:
                return RSLT_UNKNOWN_ERROR;
        }
    }
       
    public int getLanguage(){ return language; }
    
    public void setLanguage(int language){ this.language = language; }
    
    public LlStockTaken getLlStockTaken() { return llStockTaken; }
    
    public FrmLlStockTaken getForm() { return frmLlStockTaken; }
    
    public String getMessage(){ return msgString; }
    
    public int getStart() { return start; }
    
    public int action(int cmd , long oidLeave){
    Position position = new Position();
       return action(cmd ,oidLeave,new Vector(),position);  
    }
    public int action(int cmd , long oidLeave, Vector listal){
        Position position = new Position();
       return action(cmd ,oidLeave,listal,position);  
    }
    public int action(int cmd , long oidLeave, Vector listal, Position positionOfUser) 
    {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
         float eligbleDay = 0;
         //update by satrya 2012-10-24
        Vector listLLTakenFinishDate = null;
         Date chkDateTaken = null;
         Date chkDateFinish = null;
        /* Untuk menghandle agar stock al tidak minus jika tidak diperbolehkan mengambil cuti dalam kondisi stock minus*/

        I_Leave leaveConfig = null;

        try{
            leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        }catch(Exception e) {
            System.out.println("Exception LEAVE_CONFIG: " + e.getMessage());
        }
        switch(cmd)
        {
            case Command.ADD :
                break;
                
            case Command.SAVE :
                //update by satrya 2012-11-06
                 float llTakenPrev = 0.0f;
                if(oidLeave != 0)
                {
                    try
                    {
                        llStockTaken = PstLlStockTaken.fetchExc(oidLeave);
                         llTakenPrev = llStockTaken.getTakenQty();
                    }
                    catch(Exception exc)
                    {
                    }
                }
                
                frmLlStockTaken.requestEntityObject(llStockTaken);
                
                if(frmLlStockTaken.errorSize()>0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE ;
                }
                
                if((llStockTaken.getTakenDate().getTime()/(24L*60L*60L*1000L)) > (llStockTaken.getTakenFinnishDate().getTime()/(24L*60L*60L*1000L))){
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_TAKEN_DATE_WRONG;
                }
                
                /* kondisi true -> tkn and finnis melewati expired, false - > tkn and finnis tidak melewati expired */
                boolean statusExpiredLL = false;
                statusExpiredLL = SessLeaveApplication.getStatusLL(llStockTaken.getEmployeeId(), llStockTaken.getTakenDate(), llStockTaken.getTakenFinnishDate());
                
                if(statusExpiredLL == true){    // jika tkn date dan finish date melebihi expired stock ll
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_TAKEN_DATE_EXPIRED;
                }
                //update by satrya 2012-10-08
                //agar eligible tidak minus
              if(listal !=null && listal.size() > 0){
                    RepItemLeaveAndDp item = null;
                    item = (RepItemLeaveAndDp)listal.get(0);
                    for(int dpIdx=0; dpIdx<listal.size(); dpIdx++){
                        item = (RepItemLeaveAndDp)listal.get(dpIdx);                        
                        eligbleDay = item.getLLQty() - item.getLLTaken() - item.getLL2BTaken();
                        }
                       eligbleDay = eligbleDay + llTakenPrev;
                         if(leaveConfig.getLLStockMinus(llStockTaken,leaveConfig,eligbleDay)== false){
                             //sudah ada di konfigurasi leaveConfig.getLLStockMinus(llStockTaken,leaveConfig)== false
                             // if(((eligbleDay-llStockTaken.getTakenQty()) < 0) && leaveConfig.getLLStockMinus(llStockTaken,leaveConfig)== false){
                             
                                float stockLL =(eligbleDay-llStockTaken.getTakenQty());
                              msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE) + " : ' Your advance leave exceeded  maximum limit " + stockLL +" ' ";;
                        return RSLT_STOCK_LL_CAN_NOT_MINUS;  
                   }
                }
             //update by satrya 2012-10-24
              //cek jika user memilih taken date dan finish date msh dalam 1 range 
              listLLTakenFinishDate = SessLeaveApp.checkOverLapsLeaveTaken(llStockTaken.getEmployeeId(),llStockTaken.getTakenDate(),llStockTaken.getTakenFinnishDate());
               /* listLLTakenFinishDate = SessLeaveApp.checkOverLapsLeaveTaken(llStockTaken.getEmployeeId(),llStockTaken.getTakenDate(),llStockTaken.getTakenFinnishDate());

                        if(listLLTakenFinishDate != null && listLLTakenFinishDate.size() > 0){
                         //    chekError = true;
                        // msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:";
                             
                         for (int dpCheckIdx = 0; dpCheckIdx < listLLTakenFinishDate.size(); dpCheckIdx++) {
                               LeaveCheckTakenDateFinish dpCheck = (LeaveCheckTakenDateFinish) listLLTakenFinishDate.get(dpCheckIdx);
                                if (dpCheck.getOidDetailLeave()!=0 && oidLeave ==0 && dpCheck.getOidDetailLeave() != oidLeave) {
                                    msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                    //msgString = msgString + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                    return RSLT_FRM_DATE_IN_RANGE;
                                }
                                
                            }
                           
                        }*/
               //update by satrya 2013-02-28
                /* Vector vtakenDatex = SessLeaveApp.getTakenDate(llStockTaken.getEmployeeId(), llStockTaken.getTakenDate(),llStockTaken.getTakenFinnishDate());
                //mencari nilai finishtakenDate yg sudah ada
                //Date finishTakenDatex = SessLeaveApp.getFinishTakenDate(alStockTaken.getEmployeeId(), alStockTaken.getTakenDate(),alStockTaken.getTakenFinnishDate());
                if(vtakenDatex.size()<1){
                    listLLTakenFinishDate =null;
                }else{
                    listLLTakenFinishDate  = SessLeaveApp.getFinishTakenDate(llStockTaken.getEmployeeId(), llStockTaken.getTakenDate(),llStockTaken.getTakenFinnishDate());
                }*/
                 
                        if(listLLTakenFinishDate != null && listLLTakenFinishDate.size() > 0){
                         //    chekError = true;
                        //update by satrya 2013-01-15
                         //msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:";
                             for (int dpCheckIdx = 0; dpCheckIdx < listLLTakenFinishDate.size(); dpCheckIdx++) {
                               LeaveCheckTakenDateFinish dpCheck = (LeaveCheckTakenDateFinish) listLLTakenFinishDate.get(dpCheckIdx);
                               // msgString = msgString + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                               if (dpCheck.getOidDetailLeave()!=0 && oidLeave ==0 && dpCheck.getOidDetailLeave() != oidLeave) {
                                    msgString = resultText[language][RSLT_FRM_DATE_IN_RANGE]  + " please check other Leave form on the same range:" + " <a href=\"javascript:openLeaveOverlap(\'" + dpCheck.getLeaveAppId() + "\');\">" + dpCheck.getSubmissionDate() + "</a> ; ";
                                    return RSLT_FRM_DATE_IN_RANGE;
                                }
                               
                               //update by satrya 2014-01-29
                               //karena ketika update user masih bisa overlap tgl nya ,kasus cuti AL tgl 29 Januari 2014 08:00 s/d 31 januari 2014 17:00 
                               //lalu cuti tgl 29 Januari 2014 00:00 s/d 08:00,
                               //lalu di edit menjadi tgl 29 Januari 2014 08:15 maka dia "Harusnya " jadi overlap 
                               else if(llStockTaken!=null && dpCheck!=null && dpCheck.getTakenDate()!=null && dpCheck.getFinishDate()!=null && llStockTaken.getTakenDate()!=null && llStockTaken.getTakenFinnishDate()!=null){
                                   Date newStartDate = dpCheck.getTakenDate();
                                   Date newEndDate = dpCheck.getFinishDate();
                                   Date startDate = llStockTaken.getTakenDate();
                                   Date endDate = llStockTaken.getTakenFinnishDate();
                                   if ((oidLeave!=0 ? (dpCheck.getOidDetailLeave() == oidLeave?false:true) :  dpCheck.getOidDetailLeave() != oidLeave) && newStartDate.after(llStockTaken.getTakenDate()) && newStartDate.before(llStockTaken.getTakenFinnishDate())) {
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
                        //priska 20151118
                   boolean data_validOfDateBeforeExpired = SessLeaveApplication.getBeforeDayExpired(llStockTaken.getTakenDate(),positionOfUser);
                if (data_validOfDateBeforeExpired == false) {
                    msgString = msgString + " : " + FRMMessage.getMsg(FRMMessage.MSG_DATA_OUT_OF_RANGE) + " : taken date out of LL period";
                    //untuk ngetest
                    System.out.println(""+resultText[language][RSLT_UNKNOWN_ERROR]+" ");
                    return RSLT_UNKNOWN_ERROR;
                }       
                        
                //cek payperiode apa 20150805
                long periodId = PstPayPeriod.getPayPeriodIdBySelectedDate(llStockTaken.getTakenDate()); 

                if(llStockTaken.getOID()==0)
                {
                    try
                    {
                        long oid = PstLlStockTaken.insertExc(this.llStockTaken);
                        //priska menambahkan untuk menambahkan tanda bahwa ll yang pertama semnnjak closing yang sebelumnya
//                        if ((leaveConfig.getLLallowance(llStockTaken, periodId, llStockTaken.getEmployeeId() ) == true )) {
//                            try{
//                                LeaveApplication leaveApplication = PstLeaveApplication.fetchExc(llStockTaken.getLeaveApplicationId());
//                                leaveApplication.setLlAllowance(1);
//                                PstLeaveApplication.updateExc(leaveApplication);
//                            }catch (Exception e){
//                                
//                            }
//                        }
                        if(oid!=0){
                              msgString = resultText[language][RSLT_FRM_INSERT_DATA]+" "+resultText[language][RSLT_OK];
                        }
                    }
                    catch(DBException dbexc)
                    {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    }
                    catch (Exception exc)
                    {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }
                    
                }
                else
                {
                    try 
                    {
                        long oid = PstLlStockTaken.updateExc(this.llStockTaken);
//                        //priska menambahkan untuk menambahkan tanda bahwa ll yang pertama semnnjak closing yang sebelumnya
//                        if ((leaveConfig.getLLallowance(llStockTaken, periodId, llStockTaken.getEmployeeId() ) == true )) {
//                            try{
//                                LeaveApplication leaveApplication = PstLeaveApplication.fetchExc(llStockTaken.getLeaveApplicationId());
//                                leaveApplication.setLlAllowance(1);
//                                PstLeaveApplication.updateExc(leaveApplication);
//                            }catch (Exception e){
//                                
//                            }
//                        }
                        
                        if(oid!=0){
                              msgString = resultText[language][RSLT_FRM_UPDATE_DATA]+" "+resultText[language][RSLT_OK];
                        }
                    }
                    catch (DBException dbexc)
                    {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }
                    catch (Exception exc)
                    {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                    
                }
                break;
                
            case Command.EDIT :
                if (oidLeave != 0) 
                {
                    try 
                    {
                        llStockTaken = PstLlStockTaken.fetchExc(oidLeave);
                    }
                    catch (DBException dbexc)
                    {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }
                    catch (Exception exc)
                    {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            case Command.ASK :
                if (oidLeave != 0) 
                {
                    try 
                    {
                        llStockTaken = PstLlStockTaken.fetchExc(oidLeave);
                    }
                    catch (DBException dbexc)
                    {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }
                    catch (Exception exc)
                    {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            case Command.DELETE :
                if (oidLeave != 0)
                {
                    try
                    {
                        long oid = PstLlStockTaken.deleteExc(oidLeave);
                        if(oid!=0)
                        {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED)+ " LL Taken ";
                            excCode = RSLT_OK;
                        }
                        else
                        {
                            msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                            excCode = RSLT_FORM_INCOMPLETE;
                        }
                    }
                    catch(DBException dbexc)
                    {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }
                    catch(Exception exc)                    
                    {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            default :
                
        }
        return rsCode;
    }
   
    public int action(int cmd , long oidLeave, AlStockTaken stockTaken) 
    {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch(cmd)
        {
            case Command.ADD :
                break;
                
            case Command.SAVE :
                if(oidLeave != 0)
                {
                    try
                    {
                        llStockTaken = PstLlStockTaken.fetchExc(oidLeave);
                    }
                    catch(Exception exc)
                    {
                    }
                }
                
                llStockTaken.setOID(stockTaken.getOID());
                llStockTaken.setLlStockId(stockTaken.getAlStockId());
                llStockTaken.setEmployeeId(stockTaken.getEmployeeId());
                llStockTaken.setTakenDate(stockTaken.getTakenDate());
                llStockTaken.setTakenQty(stockTaken.getTakenQty());
                llStockTaken.setPaidDate(stockTaken.getPaidDate());
                llStockTaken.setTakenFromStatus(stockTaken.getTakenFromStatus());
                
                if(llStockTaken.getOID()==0)
                {
                    try
                    {
                        long oid = PstLlStockTaken.insertExc(this.llStockTaken);
                    }
                    catch(DBException dbexc)
                    {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    }
                    catch (Exception exc)
                    {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }
                    
                }
                else
                {
                    try 
                    {
                        long oid = PstLlStockTaken.updateExc(this.llStockTaken);
                    }
                    catch (DBException dbexc)
                    {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }
                    catch (Exception exc)
                    {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                    
                }
                break;
                
            case Command.EDIT :
                if (oidLeave != 0) 
                {
                    try 
                    {
                        llStockTaken = PstLlStockTaken.fetchExc(oidLeave);
                    }
                    catch (DBException dbexc)
                    {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }
                    catch (Exception exc)
                    {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            case Command.ASK :
                if (oidLeave != 0) 
                {
                    try 
                    {
                        llStockTaken = PstLlStockTaken.fetchExc(oidLeave);
                    }
                    catch (DBException dbexc)
                    {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }
                    catch (Exception exc)
                    {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            case Command.DELETE :
                if (oidLeave != 0)
                {
                    try
                    {
                        long oid = PstLlStockTaken.deleteExc(oidLeave);
                        if(oid!=0)
                        {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            excCode = RSLT_OK;
                        }
                        else
                        {
                            msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                            excCode = RSLT_FORM_INCOMPLETE;
                        }
                    }
                    catch(DBException dbexc)
                    {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }
                    catch(Exception exc)                    
                    {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            default :
                
        }
        return rsCode;
    }
}
