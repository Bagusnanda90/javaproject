/*
 * Ctrl Name  		:  CtrlPresence.java
 * Created on 	:  [date] [time] AM/PM
 *
 * @author  		: karya
 * @version  		: 01
 */

/*******************************************************************
 * Class Description 	: [project description ... ]
 * Imput Parameters 	: [input parameter ...]
 * Output 		: [output ...]
 *******************************************************************/

package com.dimata.harisma.form.attendance;

/* java package */
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* dimata package */
import com.dimata.util.*;
import com.dimata.util.lang.*;
/* qdep package */
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.db.*;
import com.dimata.gui.jsp.*;
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.log.I_LogHistory;
import com.dimata.harisma.entity.log.LogSysHistory;
import com.dimata.harisma.entity.log.PstLogSysHistory;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.utility.service.presence.PresenceAnalyser;

// import barcode/presence logger -- add by edhy
import com.dimata.harisma.utility.service.tma.*;
import com.dimata.system.entity.PstSystemProperty;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.jasper.tagplugins.jstl.core.Catch;

public class CtrlPresence extends Control implements I_Language {
    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    //update by satrya 2012-10-10
    public static int RSLT_RECORD_NOT_FOUND = 4;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap","Data tidak ada"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete","Record Not Found"}
    };
    
    private int start;
    private String msgString;
    private Presence presence;
    private PstPresence pstPresence;
    private FrmPresence frmPresence;
    int language = LANGUAGE_DEFAULT;
    //update by devin 2014-03-24
    private Hashtable msgSuccess = new Hashtable();
    
    public CtrlPresence(HttpServletRequest request){            
        msgString = "";
        presence = new Presence();
        try{
            pstPresence = new PstPresence(0);
        }catch(Exception e){;}
        frmPresence = new FrmPresence(request, presence);
    }
    
    private String getSystemMessage(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                this.frmPresence.addError(frmPresence.FRM_FIELD_PRESENCE_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
    
    public Presence getPresence() { return presence; }
    
    public FrmPresence getForm() { return frmPresence; }
    
    public String getMessage(){ return msgString; }
    //update by devin 2014-04-24
    
    public Hashtable msgSuccess(){ return msgSuccess;}
    
    public int getStart() { return start; }
   
   /**
    * create by devin 2014-04-09
    * Ket: untuk action presence create many employee
    * @param cmd
    * @param oidPresence
    * @param request
    * @param rangeTime
    * @param time
    * @param inputDateFrom
    * @return 
    */
   public int actionn(int cmd , long oidPresence, HttpServletRequest request, int rangeTime, Date time, Date inputDateFrom)
    {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        
        switch(cmd)
        {
            case Command.ADD :
                break;
                
            case Command.SAVE :
                // added by edhy
                // indicate previous presence before process update execute
                Presence previousPresence = new Presence();
               
                if(oidPresence != 0)
                {
                    try 
                    {
                        presence = PstPresence.fetchExc(oidPresence);
                        previousPresence = PstPresence.fetchExc(oidPresence);
                    }
                    catch(Exception exc) 
                    {
                        System.out.println("Exc when fetchPresence : "+exc.toString());
                    }
                }
                 
    
            
                frmPresence.manualInputPresenceMultiple();
                Date presenceDate = ControlDate.getDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME], request);
                Date presenceTime = ControlDate.getTime(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME], request);
                int y = presenceDate.getYear();
                int M = presenceDate.getMonth();
                int d = presenceDate.getDate();
                int h = presenceTime.getHours();
                int m = presenceTime.getMinutes();
                Date presenceDateTime = new Date(y, M, d, h, m);
                 Date datePresence = new Date(); 
                presence.setPresenceDatetime(presenceDateTime);                                
                if(frmPresence.getCekBox()!=null && frmPresence.getCekBox().size()>0 ){
                    for(int x=0;x<frmPresence.getCekBox().size();x++){
                        //if(frmPresence.errorSize()>0) 
                //{
                  //  msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    //return RSLT_FORM_INCOMPLETE ;
                //}
                Presence kehadiran =(Presence)frmPresence.getCekBox().get(x);
                long dx = kehadiran.getEmployeeId(); 
                String strNote = "";
                if(presence.getOID()==0)
                {
                   
                    int jam=0;
                    int menit=0;
                    int detik=0;
                     for(int idxdt=0; idxdt<rangeTime;idxdt++){
                          if(inputDateFrom!=null && idxdt==0  ){
                         jam=time.getHours();
                         menit=time.getMinutes();
                         detik=time.getSeconds();
                        inputDateFrom.setHours(jam);
                        inputDateFrom.setMinutes(menit);
                        inputDateFrom.setSeconds(detik);
                        kehadiran.setPresenceDatetime(inputDateFrom);
                        
                     }else{
                              
                             
                              datePresence = (Date)new Date(inputDateFrom.getTime() + idxdt * 1000L * 60 * 60 * 24).clone();
                              datePresence.setHours(jam);
                              datePresence.setMinutes(menit);
                              datePresence.setSeconds(detik);
                             kehadiran.setPresenceDatetime(datePresence);
                          }
                         
                         try
                    {
                        long oid = pstPresence.insertExc(kehadiran);
                        if (oid != 0){
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        }
                        
                        // add by edhy
                        // update in schedule
                        long periodId=0;
                        if(idxdt==0){
                             periodId = PstPeriod.getPeriodIdBySelectedDate(inputDateFrom);
                        }else{
                             periodId = PstPeriod.getPeriodIdBySelectedDate(datePresence);
                        }
                        
                        int updatedFieldIndex = -1;
                        long updatePeriodId = periodId;
                        Vector vectFieldIndex=new Vector();
                         if(idxdt==0){
                             vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdated(periodId, kehadiran.getEmployeeId(), kehadiran.getStatus(), inputDateFrom);                         
                             
                         }else{
                              vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdated(periodId, kehadiran.getEmployeeId(), kehadiran.getStatus(), datePresence);                        
                         }
                        
                        
                        if(vectFieldIndex!=null && vectFieldIndex.size()==2) 
                        {
                            updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                            updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));
                            //System.out.println("... Update field : " + PstEmpSchedule.fieldNames[updatedFieldIndex] + " on period " + updatePeriodId);
                        }                        
                                                
                        int updateStatus = 0;
                        try 
                        {
                            updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, kehadiran.getEmployeeId(), updatedFieldIndex, idxdt==0?inputDateFrom:datePresence);
                            if(updateStatus>0) 
                            {
                                presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                            }
                        }
                        catch(Exception e) 
                        {
                            System.out.println("Update Presence exc : "+e.toString());
                        }
                        
                        // process on absence and lateness
                        // absence dilakukan sebelum lateness karena lateness itu ada jika tidak absence
                        //com.dimata.harisma.utility.service.presence.AbsenceAnalyser.processEmployeeAbsence(presenceDateTime, presence.getEmployeeId());
                        //com.dimata.harisma.utility.service.presence.LatenessAnalyser.processEmployeeLateness(presenceDateTime, presence.getEmployeeId());
                        //update by satrya 2012-10-15 
                        PresenceAnalyser.analyzePresencePerEmployeeByEmployeeId( idxdt==0?inputDateFrom:datePresence,kehadiran.getEmployeeId());
                        
                        // logging ==> not use logging
                        /*
                        if(oid>0) 
                        {
                            strNote = "Insert data presence dengan oid = " + oid;
                            
                            BarcodeLog barcodeLog = new BarcodeLog();
                            barcodeLog.setCmdType("INSERT");
                            barcodeLog.setDate(new Date());
                            barcodeLog.setNotes(strNote);
                            PstBarcodeLog.insertExc(barcodeLog);  
                        }
                        */      
                     
                        
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
                    
                }
                   if(kehadiran.getStatus()==0){
                            msgSuccess.put(kehadiran.getEmployeeId(),"Success For IN");
                        }else{
                         msgSuccess.put(kehadiran.getEmployeeId(),"Success For OUT");
                    }
                    }
                }
                
                
                
                
                
                else
                {
                    try   
                    {
                        long oid = pstPresence.updateExc(this.presence);
                        
                        // add by edhy
                        // update "in" schedule with updated data
                        long periodId = PstPeriod.getPeriodIdBySelectedDate(presenceDateTime);
                        int updatedFieldIndex = -1;
                        long updatePeriodId = periodId;
                        Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdated(periodId, presence.getEmployeeId(), presence.getStatus(), presenceDateTime);                        
                        if(vectFieldIndex!=null && vectFieldIndex.size()==2) 
                        {
                            updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                            updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));
                           // System.out.println("... Update curr field : " + PstEmpSchedule.fieldNames[updatedFieldIndex] + " on period " + updatePeriodId);                            
                        }
                        
                        // update "in" schedule with previous data
                        long prevPeriodId = PstPeriod.getPeriodIdBySelectedDate(previousPresence.getPresenceDatetime());
                        int prevUpdatedFieldIndex = -1;
                        long prevUpdatePeriodId = prevPeriodId;
                        Vector vectPrevFieldIndex = PstEmpSchedule.getFieldIndexWillUpdated(prevPeriodId, previousPresence.getEmployeeId(), previousPresence.getStatus(), previousPresence.getPresenceDatetime());
                        if(vectPrevFieldIndex!=null && vectPrevFieldIndex.size()==2) 
                        {                            
                            prevUpdatePeriodId = Long.parseLong(String.valueOf(vectPrevFieldIndex.get(0)));
                            prevUpdatedFieldIndex = Integer.parseInt(String.valueOf(vectPrevFieldIndex.get(1)));
                           // System.out.println("... Update prev field : " + PstEmpSchedule.fieldNames[prevUpdatedFieldIndex] + " on period " + prevUpdatePeriodId);                            
                        }                        
                        
                        int updateStatus = 0;
                        try 
                        {
                            updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, presence.getEmployeeId(), updatedFieldIndex, presenceDateTime);
                            
                            if(updatedFieldIndex != prevUpdatedFieldIndex) 
                            {
                                updateStatus = PstEmpSchedule.updateScheduleDataByPresence(prevUpdatePeriodId, previousPresence.getEmployeeId(), prevUpdatedFieldIndex, null);
                            }
                            
                            if(updateStatus>0) 
                            {
                                presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                            }
                        }
                        catch(Exception e) 
                        {
                            System.out.println("Update Presence exc : "+e.toString());
                        }
                        
                        
                        // process on absence and lateness
                        // absence dilakukan sebelum lateness karena lateness itu ada jika tidak absence
                       
                       // com.dimata.harisma.utility.service.presence.AbsenceAnalyser.processEmployeeAbsence(presenceDateTime, presence.getEmployeeId());
                        //com.dimata.harisma.utility.service.presence.LatenessAnalyser.processEmployeeLateness(presenceDateTime, presence.getEmployeeId());
                         PresenceAnalyser.analyzePresencePerEmployeeByEmployeeId( presence.getPresenceDatetime(),presence.getEmployeeId());
                       
                        
                        //logging  ==> not use logging  
                        /*
                        if(oid>0) 
                         {
                            strNote = "Update data presence dengan oid = " + oid;
                            
                            BarcodeLog barcodeLog = new BarcodeLog();
                            barcodeLog.setCmdType("UPDATE");
                            barcodeLog.setDate(new Date());
                            barcodeLog.setNotes(strNote);
                            PstBarcodeLog.insertExc(barcodeLog);
                        }
                        */
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
                if (oidPresence != 0) 
                {
                    try 
                    {
                        presence = PstPresence.fetchExc(oidPresence);
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
                if (oidPresence != 0) 
                {
                    try   
                    {
                        presence = PstPresence.fetchExc(oidPresence);
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
                if (oidPresence != 0)
                {
                    try
                    {
                        // added by edhy
                        // indicate previous presence before process update execute
                        Presence prevPresence = new Presence();
                        try 
                        {
                            prevPresence = PstPresence.fetchExc(oidPresence);                                                        
                        }
                        catch(Exception exc) 
                        {
                            System.out.println("Exc when fetchPresence : "+exc.toString());
                        }
                        
                        long oid = PstPresence.deleteExc(oidPresence);                        
                        if(oid!=0)
                        {                            
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            excCode = RSLT_OK;                            
                            
                            // update in schedule with previous data  
                            Date presenceDateDel = prevPresence.getPresenceDatetime();                            
                            int yd = presenceDateDel.getYear();
                            int Md = presenceDateDel.getMonth();
                            int dd = presenceDateDel.getDate();                                                        
                            int Hd = presenceDateDel.getHours();
                            int md = presenceDateDel.getMinutes();
                            int sd = presenceDateDel.getSeconds();
                            Date presenceDateTimeDel = new Date(yd, Md, dd, Hd, md, sd);
                            
                            long prevPeriodId = PstPeriod.getPeriodIdBySelectedDate(presenceDateTimeDel);
                            
                            int prevUpdatedFieldIndex = -1;
                            long prevUpdatePeriodId = prevPeriodId;
                            Vector vectPrevFieldIndex = PstEmpSchedule.getFieldIndexWillUpdated(prevPeriodId, prevPresence.getEmployeeId(), prevPresence.getStatus(), presenceDateTimeDel);                                                        
                            if(vectPrevFieldIndex!=null && vectPrevFieldIndex.size()==2) 
                            {                                
                                prevUpdatePeriodId = Long.parseLong(String.valueOf(vectPrevFieldIndex.get(0)));
                                prevUpdatedFieldIndex = Integer.parseInt(String.valueOf(vectPrevFieldIndex.get(1)));                             
                                System.out.println("... Update prev field : " + PstEmpSchedule.fieldNames[prevUpdatedFieldIndex] + " on period " + prevUpdatePeriodId);
                            }                            
                            
                            
                            int updateStatus = 0;
                            try 
                            {
                                updateStatus = PstEmpSchedule.updateScheduleDataByPresence(prevUpdatePeriodId, prevPresence.getEmployeeId(), prevUpdatedFieldIndex, null);
                            }
                            catch(Exception e) 
                            {
                                System.out.println("Update Presence exc : "+e.toString());
                            }
                            
                            // process on absence and lateness
                            // absence dilakukan sebelum lateness karena lateness itu ada jika tidak absence  
                           // com.dimata.harisma.utility.service.presence.AbsenceAnalyser.processEmployeeAbsence(presenceDateTimeDel, prevPresence.getEmployeeId());
                            //com.dimata.harisma.utility.service.presence.LatenessAnalyser.processEmployeeLateness(presenceDateTimeDel, prevPresence.getEmployeeId());
                              PresenceAnalyser.analyzePresencePerEmployeeByEmployeeId( presence.getPresenceDatetime(),presence.getEmployeeId());
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

    public int action(int cmd , long oidPresence, HttpServletRequest request, String loginName, long userId, String oidDeleteAll ) {
       return action(cmd , oidPresence, request, 0, loginName, userId, oidDeleteAll);
    }
    
    public int action(int cmd , long oidPresence, HttpServletRequest request, int manual, String loginName, long userId, String oidDeleteAll)
    {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        int sysLog = Integer.parseInt(String.valueOf(PstSystemProperty.getPropertyLongbyName("SET_USER_ACTIVITY_LOG")));
        String logDetail = "";
        String logPrev = "";
        String logCurr = "";
        Date nowDate = new Date();
        switch(cmd)
        {
            case Command.ADD :
                break;
                
            case Command.SAVE :
                // added by edhy
                // indicate previous presence before process update execute
                Presence previousPresence = new Presence();
                if(oidPresence != 0)
                {
                    try 
                    {
                        presence = PstPresence.fetchExc(oidPresence);
                        previousPresence = PstPresence.fetchExc(oidPresence);
                    }
                    catch(Exception exc) 
                    {
                        System.out.println("Exc when fetchPresence : "+exc.toString());
                    }
                }
                
                frmPresence.requestEntityObject(presence);
                
//                Date presenceDate = ControlDate.getDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME], request);
//                Date presenceTime = ControlDate.getTime(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME], request);
//                int y = presenceDate.getYear();
//                int M = presenceDate.getMonth();
//                int d = presenceDate.getDate();
//                int h = presenceTime.getHours();
//                int m = presenceTime.getMinutes();
//                Date presenceDateTime = new Date(y, M, d, h, m);
//                 Date datePresence = new Date();
                
                
                String getdatetime = FRMQueryString.requestString(request, "FRM_FIELD_PRESENCE_DATETIME"); 
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date presenceDateTime = new Date();
                try {
                    presenceDateTime = sdf.parse(getdatetime);
                }catch(Exception ex) {}
                
                
                presence.setPresenceDatetime(presenceDateTime);                                
                
                if(frmPresence.errorSize()>0) 
                {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE ;
                }
                //presence.setManualPresence(manual);
                String strNote = "";
                if(presence.getOID()==0)
                {
                    try
                    {
                        long oid = pstPresence.insertExc(this.presence);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        
                        // add by edhy
                        // update in schedule
                        long periodId = PstPeriod.getPeriodIdBySelectedDate(presenceDateTime);
                        int updatedFieldIndex = -1;
                        long updatePeriodId = periodId;
                        Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdated(periodId, presence.getEmployeeId(), presence.getStatus(), presenceDateTime);                        
                        
                        if(vectFieldIndex!=null && vectFieldIndex.size()==2) 
                        {
                            updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                            updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));
                            //System.out.println("... Update field : " + PstEmpSchedule.fieldNames[updatedFieldIndex] + " on period " + updatePeriodId);
                        }                        
                                                
                        int updateStatus = 0;
                        try 
                        {
                            updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, presence.getEmployeeId(), updatedFieldIndex, presenceDateTime);
                            if(updateStatus>0) 
                            {
                                presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                            }
                        }
                        catch(Exception e) 
                        {
                            System.out.println("Update Presence exc : "+e.toString());
                        }
                        
                        // process on absence and lateness
                        // absence dilakukan sebelum lateness karena lateness itu ada jika tidak absence
                        //com.dimata.harisma.utility.service.presence.AbsenceAnalyser.processEmployeeAbsence(presenceDateTime, presence.getEmployeeId());
                        //com.dimata.harisma.utility.service.presence.LatenessAnalyser.processEmployeeLateness(presenceDateTime, presence.getEmployeeId());
                        //update by satrya 2012-10-15 
                        PresenceAnalyser.analyzePresencePerEmployeeByEmployeeId( presence.getPresenceDatetime(),presence.getEmployeeId());
                        
                        // logging ==> not use logging
                        /*
                        if(oid>0) 
                        {
                            strNote = "Insert data presence dengan oid = " + oid;
                            
                            BarcodeLog barcodeLog = new BarcodeLog();
                            barcodeLog.setCmdType("INSERT");
                            barcodeLog.setDate(new Date());
                            barcodeLog.setNotes(strNote);
                            PstBarcodeLog.insertExc(barcodeLog);  
                        }
                        */
                        presence = PstPresence.fetchExc(oid);
                        
                        logDetail  = PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_STATUS]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_ANALYZED]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_TRANSFERRED]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_SCHEDULE_DATETIME]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_SCHEDULE_TYPE]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_OID_SCHEDULE_LEAVE]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_PERIOD_ID]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_MANUAL_PRESENCE]+";";
                        
                        
                        if (presence.getEmployeeId() != 0){
                            Employee employee = PstEmployee.fetchExc(presence.getEmployeeId());
                            logCurr += "["+ presence.getEmployeeId()+"] "+employee.getEmployeeNum()+";";
                        }
                        else {
                            logCurr += presence.getEmployeeId()+";";
                        }
                            logCurr += "'"+ presence.getPresenceDatetime()+"';";
                        
                            String status = "";
                            if(presence.getStatus() == 0){
                                status = "In";
                            }
                            else if(presence.getStatus() == 1){
                                status = "Out";
                            }
                            else if(presence.getStatus() == 2){
                                status = "Out Personal";
                            }
                            else if(presence.getStatus() == 3){
                                status = "In Personal";
                            }
                            else if(presence.getStatus() == 4){
                                status = "Out On Duty";
                            }
                            else if(presence.getStatus() == 5){
                                status = "Call Back";
                            }
                            else if(presence.getStatus() == 6){
                                status = "Invalid";
                            }
                            else {
                                status = "TBD In Out";
                                }
                            logCurr += "["+presence.getStatus()+"] "+status+";";
                        
                        logCurr += "'"+ presence.getAnalyzed()+"';";
                        logCurr += "'"+ presence.getTransferred()+"';";
                        logCurr += "'"+ presence.getScheduleDatetime()+"';";
                        logCurr += "'"+ presence.getScheduleType()+"';";
                        logCurr += "'"+ presence.getScheduleLeaveId()+"';";
                        logCurr += "'"+ presence.getPeriodId()+"';";
                        logCurr += "'"+ presence.getManualPresence()+"';";
                        
                        logPrev  = "-;";
                        logPrev += "-;";
                        logPrev += "-;";
                        logPrev += "-;";
                        logPrev += "-;";
                        logPrev += "-;";
                        logPrev += "-;";
                        logPrev += "-;";
                        logPrev += "-;";
                        logPrev += "-;";
                        
                        String empName = PstEmployee.getEmployeeName(presence.getEmployeeId());
                        
                        if (sysLog == 1){
                            String className = presence.getClass().getName();
                            
                            LogSysHistory logSysHistory = new LogSysHistory();
                            
                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + presence.getEmployeeId();
                            
                            logSysHistory.setLogDocumentId(0);
                            logSysHistory.setLogUserId(userId);
                            logSysHistory.setApproverId(userId);
                            logSysHistory.setLogLoginName(loginName);
                            logSysHistory.setLogDocumentNumber("");
                            logSysHistory.setLogDocumentType(className); //entity
                            logSysHistory.setLogUserAction("ADD"); // command
                            logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                            logSysHistory.setLogUpdateDate(nowDate);
                            logSysHistory.setApproveDate(nowDate);
                            logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                            logSysHistory.setLogDetail(logDetail);
                            logSysHistory.setLogPrev(logPrev);
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogStatus(0);
                            logSysHistory.setLogModule("Manual Presence");
                            logSysHistory.setLogEditedUserId(presence.getEmployeeId());
                            
                            PstLogSysHistory.insertExc(logSysHistory);
                            
                            
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
                        long oid = pstPresence.updateExc(this.presence);
                        msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        // add by edhy
                        // update "in" schedule with updated data
                        long periodId = PstPeriod.getPeriodIdBySelectedDate(presenceDateTime);
                        int updatedFieldIndex = -1;
                        long updatePeriodId = periodId;
                        Vector vectFieldIndex = PstEmpSchedule.getFieldIndexWillUpdated(periodId, presence.getEmployeeId(), presence.getStatus(), presenceDateTime);                        
                        if(vectFieldIndex!=null && vectFieldIndex.size()==2) 
                        {
                            updatePeriodId = Long.parseLong(String.valueOf(vectFieldIndex.get(0)));
                            updatedFieldIndex = Integer.parseInt(String.valueOf(vectFieldIndex.get(1)));
                           // System.out.println("... Update curr field : " + PstEmpSchedule.fieldNames[updatedFieldIndex] + " on period " + updatePeriodId);                            
                        }
                        
                        // update "in" schedule with previous data
                        long prevPeriodId = PstPeriod.getPeriodIdBySelectedDate(previousPresence.getPresenceDatetime());
                        int prevUpdatedFieldIndex = -1;
                        long prevUpdatePeriodId = prevPeriodId;
                        Vector vectPrevFieldIndex = PstEmpSchedule.getFieldIndexWillUpdated(prevPeriodId, previousPresence.getEmployeeId(), previousPresence.getStatus(), previousPresence.getPresenceDatetime());
                        if(vectPrevFieldIndex!=null && vectPrevFieldIndex.size()==2) 
                        {                            
                            prevUpdatePeriodId = Long.parseLong(String.valueOf(vectPrevFieldIndex.get(0)));
                            prevUpdatedFieldIndex = Integer.parseInt(String.valueOf(vectPrevFieldIndex.get(1)));
                           // System.out.println("... Update prev field : " + PstEmpSchedule.fieldNames[prevUpdatedFieldIndex] + " on period " + prevUpdatePeriodId);                            
                        }                        
                        
                        int updateStatus = 0;
                        try 
                        {
                            updateStatus = PstEmpSchedule.updateScheduleDataByPresence(updatePeriodId, presence.getEmployeeId(), updatedFieldIndex, presenceDateTime);
                            
                            if(updatedFieldIndex != prevUpdatedFieldIndex) 
                            {
                                updateStatus = PstEmpSchedule.updateScheduleDataByPresence(prevUpdatePeriodId, previousPresence.getEmployeeId(), prevUpdatedFieldIndex, null);
                            }
                            
                            if(updateStatus>0) 
                            {
                                presence.setTransferred(PstPresence.PRESENCE_TRANSFERRED);
                            }
                        }
                        catch(Exception e) 
                        {
                            System.out.println("Update Presence exc : "+e.toString());
                        }
                        
                        
                        // process on absence and lateness
                        // absence dilakukan sebelum lateness karena lateness itu ada jika tidak absence
                       
                       // com.dimata.harisma.utility.service.presence.AbsenceAnalyser.processEmployeeAbsence(presenceDateTime, presence.getEmployeeId());
                        //com.dimata.harisma.utility.service.presence.LatenessAnalyser.processEmployeeLateness(presenceDateTime, presence.getEmployeeId());
                         PresenceAnalyser.analyzePresencePerEmployeeByEmployeeId( presence.getPresenceDatetime(),presence.getEmployeeId());
                       
                        
                        //logging  ==> not use logging  
                        /*
                        if(oid>0) 
                         {
                            strNote = "Update data presence dengan oid = " + oid;
                            
                            BarcodeLog barcodeLog = new BarcodeLog();
                            barcodeLog.setCmdType("UPDATE");
                            barcodeLog.setDate(new Date());
                            barcodeLog.setNotes(strNote);
                            PstBarcodeLog.insertExc(barcodeLog);
                        }
                        */
                    
                    if (sysLog == 1) {
                        presence = PstPresence.fetchExc(oid);
                        
                        if (presence != null && previousPresence != null) {
                            if (presence.getPresenceDatetime()!= previousPresence.getPresenceDatetime()) {
                                logDetail += PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME]+";";
                                logPrev += "'"+previousPresence.getPresenceDatetime()+"';";
                                logCurr += "'"+presence.getPresenceDatetime()+"';";
                                }
                            if (presence.getStatus() != previousPresence.getStatus()) {
                                logDetail += PstPresence.fieldNames[PstPresence.FLD_STATUS]+";";
                                String prevStatus = "";
                                if (presence.getStatus() == 0) {
                                    prevStatus = "In";
                                } else if (previousPresence.getStatus() == 1) {
                                    prevStatus = "Out";
                                } else if (previousPresence.getStatus() == 2) {
                                    prevStatus = "Out Personal";
                                } else if (previousPresence.getStatus() == 3) {
                                    prevStatus = "In Personal";
                                } else if (previousPresence.getStatus() == 4) {
                                    prevStatus = "Out On Duty";
                                } else if (previousPresence.getStatus() == 5) {
                                    prevStatus = "Call Back";
                                } else if (previousPresence.getStatus() == 6) {
                                    prevStatus = "Invalid";
                                } else {
                                    prevStatus = "TBD In Out";
                                }
                                logPrev += "[" + previousPresence.getStatus() + "] " + prevStatus + ";";
                                String status = "";
                                if (presence.getStatus() == 0) {
                                    status = "In";
                                } else if (presence.getStatus() == 1) {
                                    status = "Out";
                                } else if (presence.getStatus() == 2) {
                                    status = "Out Personal";
                                } else if (presence.getStatus() == 3) {
                                    status = "In Personal";
                                } else if (presence.getStatus() == 4) {
                                    status = "Out On Duty";
                                } else if (presence.getStatus() == 5) {
                                    status = "Call Back";
                                } else if (presence.getStatus() == 6) {
                                    status = "Invalid";
                                } else {
                                    status = "TBD In Out";
                                }
                                logCurr += "[" + presence.getStatus() + "] " + status + ";";
                            }
                            
                                 String className = presence.getClass().getName();
                                
                                LogSysHistory logSysHistory = new LogSysHistory();
                                
                                String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + presence.getEmployeeId();
                                
                                logSysHistory.setLogDocumentId(0);
                                logSysHistory.setLogUserId(userId);
                                logSysHistory.setApproverId(userId);
                                logSysHistory.setLogLoginName(loginName);
                                logSysHistory.setLogDocumentNumber("");
                                logSysHistory.setLogDocumentType(className); //entity
                                logSysHistory.setLogUserAction("EDIT"); // command
                                logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                                logSysHistory.setLogUpdateDate(nowDate);
                                logSysHistory.setApproveDate(nowDate);
                                logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                                logSysHistory.setLogDetail(logDetail);
                                logSysHistory.setLogPrev(logPrev);
                                logSysHistory.setLogCurr(logCurr);
                                logSysHistory.setLogStatus(0);
                                logSysHistory.setLogModule("Manual Presence");
                                logSysHistory.setLogEditedUserId(presence.getEmployeeId());
                                
                                PstLogSysHistory.insertExc(logSysHistory);
                            
                        }
                        
                        
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
                if (oidPresence != 0) 
                {
                    try 
                    {
                        presence = PstPresence.fetchExc(oidPresence);
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
                if (oidPresence != 0) 
                {
                    try   
                    {
                        presence = PstPresence.fetchExc(oidPresence);
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
                if (oidPresence != 0)
                {
                    try
                    {
                        presence = PstPresence.fetchExc(oidPresence);
                        
                        logDetail  = PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_STATUS]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_ANALYZED]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_TRANSFERRED]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_SCHEDULE_DATETIME]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_SCHEDULE_TYPE]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_OID_SCHEDULE_LEAVE]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_PERIOD_ID]+";";
                        logDetail += PstPresence.fieldNames[PstPresence.FLD_MANUAL_PRESENCE]+";";
                        
                        
                        if (presence.getEmployeeId() != 0){
                            Employee employee = PstEmployee.fetchExc(presence.getEmployeeId());
                            logPrev += "["+ presence.getEmployeeId()+"] "+employee.getEmployeeNum()+";";
                        }
                        else {
                            logPrev += presence.getEmployeeId()+";";
                        }
                            logPrev += "'"+ presence.getPresenceDatetime()+"';";
                        
                            String status = "";
                            if(presence.getStatus() == 0){
                                status = "In";
                            }
                            else if(presence.getStatus() == 1){
                                status = "Out";
                            }
                            else if(presence.getStatus() == 2){
                                status = "Out Personal";
                            }
                            else if(presence.getStatus() == 3){
                                status = "In Personal";
                            }
                            else if(presence.getStatus() == 4){
                                status = "Out On Duty";
                            }
                            else if(presence.getStatus() == 5){
                                status = "Call Back";
                            }
                            else if(presence.getStatus() == 6){
                                status = "Invalid";
                            }
                            else {
                                status = "TBD In Out";
                                }
                            logPrev += "["+presence.getStatus()+"] "+status+";";
                        
                        logPrev += "'"+ presence.getAnalyzed()+"';";
                        logPrev += "'"+ presence.getTransferred()+"';";
                        logPrev += "'"+ presence.getScheduleDatetime()+"';";
                        logPrev += "'"+ presence.getScheduleType()+"';";
                        logPrev += "'"+ presence.getScheduleLeaveId()+"';";
                        logPrev += "'"+ presence.getPeriodId()+"';";
                        logPrev += "'"+ presence.getManualPresence()+"';";
                        
                        logCurr  = "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        logCurr += "-;";
                        
                        String empName = PstEmployee.getEmployeeName(presence.getEmployeeId());
                        
                        if (sysLog == 1){
                            String className = presence.getClass().getName();
                            
                            LogSysHistory logSysHistory = new LogSysHistory();
                            
                            String reqUrl = request.getRequestURI().toString() + "?employee_oid=" + presence.getEmployeeId();
                            
                            logSysHistory.setLogDocumentId(0);
                            logSysHistory.setLogUserId(userId);
                            logSysHistory.setApproverId(userId);
                            logSysHistory.setLogLoginName(loginName);
                            logSysHistory.setLogDocumentNumber("");
                            logSysHistory.setLogDocumentType(className); //entity
                            logSysHistory.setLogUserAction("DELETE"); // command
                            logSysHistory.setLogOpenUrl(reqUrl); // locate jsp
                            logSysHistory.setLogUpdateDate(nowDate);
                            logSysHistory.setApproveDate(nowDate);
                            logSysHistory.setLogApplication(I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]); // interface
                            logSysHistory.setLogDetail(logDetail);
                            logSysHistory.setLogPrev(logPrev);
                            logSysHistory.setLogCurr(logCurr);
                            logSysHistory.setLogStatus(0);
                            logSysHistory.setLogModule("Manual Presence");
                            logSysHistory.setLogEditedUserId(presence.getEmployeeId());
                            
                            PstLogSysHistory.insertExc(logSysHistory);
                            
                            
                        }
                        // added by edhy
                        // indicate previous presence before process update execute
                        Presence prevPresence = new Presence();
                        try 
                        {
                            prevPresence = PstPresence.fetchExc(oidPresence);                                                        
                        }
                        catch(Exception exc) 
                        {
                            System.out.println("Exc when fetchPresence : "+exc.toString());
                        }
                        
                        long oid = PstPresence.deleteExc(oidPresence);                        
                        if(oid!=0)
                        {                            
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            excCode = RSLT_OK;                            
                            
                            // update in schedule with previous data  
                            Date presenceDateDel = prevPresence.getPresenceDatetime();                            
                            int yd = presenceDateDel.getYear();
                            int Md = presenceDateDel.getMonth();
                            int dd = presenceDateDel.getDate();                                                        
                            int Hd = presenceDateDel.getHours();
                            int md = presenceDateDel.getMinutes();
                            int sd = presenceDateDel.getSeconds();
                            Date presenceDateTimeDel = new Date(yd, Md, dd, Hd, md, sd);
                            
                            long prevPeriodId = PstPeriod.getPeriodIdBySelectedDate(presenceDateTimeDel);
                            
                            int prevUpdatedFieldIndex = -1;
                            long prevUpdatePeriodId = prevPeriodId;
                            Vector vectPrevFieldIndex = PstEmpSchedule.getFieldIndexWillUpdated(prevPeriodId, prevPresence.getEmployeeId(), prevPresence.getStatus(), presenceDateTimeDel);                                                        
                            if(vectPrevFieldIndex!=null && vectPrevFieldIndex.size()==2) 
                            {                                
                                prevUpdatePeriodId = Long.parseLong(String.valueOf(vectPrevFieldIndex.get(0)));
                                prevUpdatedFieldIndex = Integer.parseInt(String.valueOf(vectPrevFieldIndex.get(1)));                             
                                System.out.println("... Update prev field : " + PstEmpSchedule.fieldNames[prevUpdatedFieldIndex] + " on period " + prevUpdatePeriodId);
                            }                            
                            
                            
                            int updateStatus = 0;
                            try 
                            {
                                updateStatus = PstEmpSchedule.updateScheduleDataByPresence(prevUpdatePeriodId, prevPresence.getEmployeeId(), prevUpdatedFieldIndex, null);
                            }
                            catch(Exception e) 
                            {
                                System.out.println("Update Presence exc : "+e.toString());
                            }
                            
                            // process on absence and lateness
                            // absence dilakukan sebelum lateness karena lateness itu ada jika tidak absence  
                           // com.dimata.harisma.utility.service.presence.AbsenceAnalyser.processEmployeeAbsence(presenceDateTimeDel, prevPresence.getEmployeeId());
                            //com.dimata.harisma.utility.service.presence.LatenessAnalyser.processEmployeeLateness(presenceDateTimeDel, prevPresence.getEmployeeId());
                              PresenceAnalyser.analyzePresencePerEmployeeByEmployeeId( presence.getPresenceDatetime(),presence.getEmployeeId());
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
                
                 case Command.DELETEALL:
                //splits => untuk mengurutkan beberapa data menjadi baris data
                String[] splits = oidDeleteAll.split(",");
                for (String asset : splits) {
                    if (asset != "") {
                        long oidPresencenew = Long.parseLong(asset);
                        if (oidPresencenew != 0) {
                            try {
                                long oid = PstPresence.deleteExc(oidPresencenew);
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
                    }
                }
                break;
                
            default :
        }
        return rsCode;
    }
}
