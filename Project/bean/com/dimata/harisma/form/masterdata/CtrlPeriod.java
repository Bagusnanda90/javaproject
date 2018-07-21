/* 
 * Ctrl Name  		:  CtrlPeriod.java 
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
package com.dimata.harisma.form.masterdata;

import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
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
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.employee.PstDefaultSchedule;
import com.dimata.harisma.entity.employee.DefaultSchedule;
import com.dimata.harisma.entity.payroll.PayEmpLevel;
import com.dimata.harisma.entity.payroll.PaySlip;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.harisma.session.employee.SessEmployee;
import com.dimata.system.entity.PstSystemProperty;

public class CtrlPeriod extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
    };
    private int start;
    private String msgString;
    private Period period;
    private PstPeriod pstPeriod;
    private FrmPeriod frmPeriod;
    int language = LANGUAGE_DEFAULT;

    public CtrlPeriod(HttpServletRequest request) {
        msgString = "";
        period = new Period();
        try {
            pstPeriod = new PstPeriod(0);
        } catch (Exception e) {;
        }
        frmPeriod = new FrmPeriod(request, period);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmPeriod.addError(frmPeriod.FRM_FIELD_PERIOD_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public Period getPeriod() {
        return period;
    }

    public FrmPeriod getForm() {
        return frmPeriod;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidPeriod, int enableReplaceExistingSchedule , String oidDeleteAll ) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidPeriod != 0) {
                    try {
                        period = PstPeriod.fetchExc(oidPeriod);
                    } catch (Exception exc) {
                    }
                }
                
                frmPeriod.requestEntityObject(period);

                if (frmPeriod.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }
                
               long oidperiodsebelumnya = pstPeriod.getPeriodIdBySelectedDate(this.period.getStartDate());
                if (oidperiodsebelumnya > 0){
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_IN_USED);
                    return RSLT_FORM_INCOMPLETE;
                }
                
                if (period.getOID() == 0) {
                    try {
                        
                        long oid = pstPeriod.insertExc(this.period);
                        if (oid>0){
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
                        }
                        //System.out.println("periodId atas..."+period.getOID());
                        
                        //samapi sisni......................
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
                        long oid = pstPeriod.updateExc(this.period);
                        if (oid>0){
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
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
                if (oidPeriod != 0) {
                    try {
                        period = PstPeriod.fetchExc(oidPeriod);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidPeriod != 0) {
                    try {
                        if (PstPeriod.checkMaster(oidPeriod)) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_IN_USED);
                        } else {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_ASKDEL);
                        }

                        period = PstPeriod.fetchExc(oidPeriod);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidPeriod != 0) {
                    try {
                        long oid = PstPeriod.deleteExc(oidPeriod);
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

            case Command.POST:
                if (oidPeriod != 0) {
                    try { int totalUpdateOrInsertedSchedule=0;
                        // generate schedule fo ron existing schedule in a periode for all employee having default schedule
                        int empTotal = PstEmployee.getCountEmployeeHaveDefaultSchedule(""); // list count of employee having default schedule
                        if (empTotal > 0) {
                            Period period = PstPeriod.fetchExc(oidPeriod);// get periode and calendar , and start day                                                 
                            int start=0;
                            //update by satrya 2012-10-09
                            HolidaysTable holidaysTable = PstPublicHolidays.getHolidaysTable(period.getStartDate(), period.getEndDate());
                            long oidPublicHoliday = 0;         
                            try{
                                oidPublicHoliday = Long.parseLong(PstSystemProperty.getValueByName("OID_PUBLIC_HOLIDAY"));
                            }catch(Exception ex){
                                System.out.println("Execption OID_PUBLIC_HOLIDAY: " + ex.toString());
                            }
                            long oidDayOff = 0;         
                            try{
                                oidDayOff = Long.parseLong(PstSystemProperty.getValueByName("OID_DAY_OFF"));
                            }catch(Exception ex){
                                System.out.println("Execption OID_DAY_OFF: " + ex.toString());
                            }
                            //update by satrya 2013-04-09
                            int schedulePerWeek = 0;    
                            int recordToGet=7;
                            try{
                                schedulePerWeek = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_DEFAULT_SCHEDULE_PER_WEEK"));
                                if(schedulePerWeek!=0){
                                    recordToGet=35;
                                }
                            }catch(Exception ex){
                                System.out.println("Execption ATTANDACE_DEFAULT_SCHEDULE_PER_WEEK: " + ex.toString());
                                schedulePerWeek=0;
                            }
                            do{                                
                                Vector employeeList = PstEmployee.getEmployeeHaveDefaultSchedule(start, 50, "", ""); // loop per 50 employee
                                start = start +50;                                
                                if(employeeList!=null){                                
                                for(int idx=0; idx< employeeList.size();idx++){ 
                                    Employee employee = (Employee) employeeList.get(idx);
                                    Long emID = employee.getOID();
                                    String whereClauseDS = PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_EMPLOYEE_ID]+"="+employee.getOID();
                                    String orderDS= PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_DAY_INDEX] ;
                                     //update by satrya test saja
                                    
                                    if(employee.getOID()==11184l){
                                        boolean test=true;
                                    }
                                    Vector dftSchedules = PstDefaultSchedule.list(0, recordToGet, whereClauseDS, orderDS);                                    
                                   
                                    if(dftSchedules!=null && dftSchedules.size()>0){
                                        EmpSchedule schedule = PstEmpSchedule.fecth(oidPeriod, employee.getOID());
                                        boolean updated=false;
                                        if(schedule==null){
                                            updated=true;
                                            schedule = new EmpSchedule();
                                            schedule.setEmployeeId(employee.getOID());
                                            schedule.setPeriodId(oidPeriod);                                            
                                        }
                                        
                                        GregorianCalendar gcStart = new GregorianCalendar(period.getStartDate().getYear(), period.getStartDate().getMonth(), period.getStartDate().getDate());                                        
                                        int sDayOfWeek = gcStart.get(GregorianCalendar.DAY_OF_WEEK);
                                        int nDayOfMonthStart = gcStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
                                        int iDate=period.getStartDate().getDate();                                        
                                        int iDayWeek=period.getStartDate().getDay()+1; // Gregorion callendar Sunday = 1
                                        //update by satrya 2012-10-09
                                        Date selectedDate = (Date)period.getStartDate().clone(); // = new  Date(period.getStartDate().getTime());
                                        int countOfDay=0; // counter pengaman                                        
                                        do{ // loop through the calendar 
                                            if(schedule.getOID()!=0){ // for existing schedule 
                                                if(schedule.getD(iDate)==0){ // only set when schedule normal not exist
                                                    updated=true;
                                                    DefaultSchedule dflt = PstDefaultSchedule.getDefaultSchedule(iDayWeek , dftSchedules );
                                                    if(holidaysTable.isHoliday(employee.getReligionId(), selectedDate)){
                                                        schedule.setD(iDate, oidPublicHoliday); //jika ada holiday religion
                                                        
                                                        //System.out.println(employee.getFullName()+","+selectedDate);
                                                    }else{
                                                        //jika dflt.getSchedule1() == 0 
                                                       //untuk mencegah agar schedulenya "-" maka akan di set off (contoh unbtuk yg tdk ada schedule menjadi off)
                                                        //oidPublicHoliday
                                                        schedule.setD(iDate, dflt.getSchedule1() !=0 ? dflt.getSchedule1() : oidDayOff); 
                                                        
                                                    }
                                                }
                                                //update by satrya 2012-11-23
                                                //untuk generate ulang jika dia schedlue ada yg holiday
                                                //enableReplaceExistingSchedule == 1 berati diijinkan untuk replace schedule exiting
                                                else{
                                                    if(enableReplaceExistingSchedule ==1 && holidaysTable.isHoliday(employee.getReligionId(), selectedDate)){
                                                        schedule.setD(iDate, oidPublicHoliday); //jika ada holiday religion
                                                       
                                                    }
                                                }
                                                
                                                if(schedule.getD2nd(iDate)==0){ // only set when schedule split not exist
                                                    updated=true;
                                                    DefaultSchedule dflt = PstDefaultSchedule.getDefaultSchedule(iDayWeek , dftSchedules );
                                                      schedule.setD2nd(iDate, dflt.getSchedule2()); 
                                                    
                                                }   
                                                // Jika status Always OK | Update by Hendra | 2015-01-14
                                                if (employee.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                    schedule.setStatus(iDate, PstEmpSchedule.STATUS_PRESENCE_OK);
                                                }
                                            }else{ // set all schedule for not existing schedule
                                                    DefaultSchedule dflt = PstDefaultSchedule.getDefaultSchedule(iDayWeek , dftSchedules );
                                                    if(holidaysTable.isHoliday(employee.getReligionId(), selectedDate)){
                                                         schedule.setD(iDate, oidPublicHoliday); //jika ada holiday religion
                                                       
                                                    }else{
                                                        schedule.setD(iDate, dflt.getSchedule1() !=0 ? dflt.getSchedule1() : oidDayOff);//jika tdk ada holiday                                           
                                                        schedule.setD2nd(iDate, dflt.getSchedule2());  //jika tdk ada holiday
                                                        
                                                    }   
                                                // Jika status Always OK | Update by Hendra | 2015-01-14
                                                    if (employee.getPresenceCheckParameter() == PstEmployee.PRESENCE_CHECK_ALWAYS_OK){
                                                        schedule.setStatus(iDate, PstEmpSchedule.STATUS_PRESENCE_OK);
                                                    }
                                            } 
                                            
                                            iDayWeek = iDayWeek+1;
                                          
                                            if(iDayWeek>7 && schedulePerWeek==0){ // if day of week saturday then back to sunday =1
                                                iDayWeek =1; 
                                            }
                                            /**
                                             * hidden by satrya 2013-04-08
                                             *   if(iDayWeek>7){ // if day of week saturday then back to sunday =1
                                                iDayWeek =1; 
                                            }
                                             */
                                             
                                            countOfDay=countOfDay+1;
                                            iDate = iDate+1; 
                                            selectedDate.setDate(selectedDate.getDate()+1);//otomatis akan melewati bulannya
                                            if(iDate>nDayOfMonthStart){ // jika tanggal di schedule sudah melewati tanggal maximum di bulan itu
                                                iDate=1;
                                            }
                                            
                                        } while(iDate!=(period.getEndDate().getDate()+1) && countOfDay < 31 );
                                        if(updated){
                                              // save schedule for an employee
                                            totalUpdateOrInsertedSchedule++;
                                             try{
                                                 if(schedule.getOID()!=0){
                                                    PstEmpSchedule.updateExc(schedule);
                                                 }else{
                                                    PstEmpSchedule.insertExc(schedule);
                                                 }                                                 
                                             }catch(Exception exc){
                                                 System.out.println(exc);
                                             }
                                        }
                                    }
                                }
                               }
                            }while(start < empTotal);
                        }                                          
                        msgString = "Generate schedule : Total Employee have default schedule="+  empTotal + " ; total updated schedule="+totalUpdateOrInsertedSchedule;
                    } catch (Exception exc) {
                        msgString = exc.toString();
                        System.out.println(exc);
                    }
                }
                break;
                
                case Command.DELETEALL:
                //splits => untuk mengurutkan beberapa data menjadi baris data
                String[] splits = oidDeleteAll.split(",");
                for (String asset : splits) {
                    if (asset != "") {
                        long oidPer = Long.parseLong(asset);
                        if (oidPer != 0) {
                            try {
                                long oid = PstPeriod.deleteExc(oidPer);
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
            default:

        }
        return rsCode;
    }
    //update by satrya 2012-11-23
    public int action(int cmd, long oidPeriod){
        return action(cmd, oidPeriod, 0, "");
    
    }
}
