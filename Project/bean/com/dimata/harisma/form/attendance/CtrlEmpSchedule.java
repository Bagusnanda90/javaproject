/* 
 * Ctrl Name  		:  CtrlEmpSchedule.java 
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
/* project package */
//import com.dimata.harisma.db.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstReason;
import com.dimata.harisma.entity.masterdata.Reason;
import com.dimata.harisma.session.attendance.SessEmpSchedule;
import org.apache.jasper.tagplugins.jstl.core.Catch;

public class CtrlEmpSchedule extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static String[][] resultText = {{"Berhasil", "Tidak dapat diproses", "No Perkiraan sudah ada", "Data tidak lengkap"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete"}};
    private int start;
    private String msgString;
    private EmpSchedule empSchedule;
    private PstEmpSchedule pstEmpSchedule;
    private FrmEmpSchedule frmEmpSchedule;
    int language = LANGUAGE_DEFAULT;

    public CtrlEmpSchedule(HttpServletRequest request) {
        msgString = "";
        empSchedule = new EmpSchedule();
        try {
            pstEmpSchedule = new PstEmpSchedule(0);
        } catch (Exception e) {
            ;
        }
        frmEmpSchedule = new FrmEmpSchedule(request, empSchedule);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmEmpSchedule.addError(frmEmpSchedule.FRM_FIELD_EMP_SCHEDULE_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public EmpSchedule getEmpSchedule() {
        return empSchedule;
    }

    public FrmEmpSchedule getForm() {
        return frmEmpSchedule;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidEmpSchedule, Position position, String oidDeleteAll){
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                
                if (oidEmpSchedule != 0) {
                    try {                        
                        empSchedule = PstEmpSchedule.fetchExc(oidEmpSchedule);
                    } catch (Exception exc) {
                    }
                }
                /*
                 * Description : mengubah status employee yang memiliki nilai PresenceCheckParameter == Always OK
                 * Date : 2015-01-12
                 * Author : Hendra McHen
                 */
                
                //frmEmpSchedule.requestEntityObject(empSchedule);
                if (oidEmpSchedule != 0){
                    frmEmpSchedule.requestEntityObjectSpecial(empSchedule, position);
                } else {
                    frmEmpSchedule.requestEntityObject(empSchedule);
                }
                
                
                
                long empID = empSchedule.getEmployeeId();
                if (empID != 0) {
                    Employee empCheck = new Employee();
                    try {
                        empCheck = PstEmployee.fetchExc(empID);//
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                    if (empCheck.getPresenceCheckParameter() == 1) // status Always OK
                    {
                        for(int k = 1; k < 32; k++){
                            empSchedule.setStatus(k, PstEmpSchedule.STATUS_PRESENCE_OK);
                        }
                    }
                }
               

                

                if (frmEmpSchedule.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }
                
                Vector listReason = new Vector();
                
                String whereReason = PstReason.fieldNames[PstReason.FLD_SCHEDULE_ID] + " != " + 0;

                listReason = PstReason.list(0, 0, whereReason, null);
                
                int status_sch = PstEmpSchedule.STATUS_PRESENCE_OK;

                if (listReason != null && listReason.size() > 0) {

                    for (int i = 0; i < listReason.size(); i++) {

                        Reason reason = new Reason();

                        reason = (Reason) listReason.get(i);

                        if (this.empSchedule.getD1() == reason.getScheduleId()){

                            this.empSchedule.setReason1(reason.getNo());
                            this.empSchedule.setStatus1(status_sch);
                            
                        }

                        if (this.empSchedule.getD2() == reason.getScheduleId()) {

                            this.empSchedule.setReason2(reason.getNo());
                            this.empSchedule.setStatus2(status_sch);

                        }

                        if (this.empSchedule.getD3() == reason.getScheduleId()) {

                            this.empSchedule.setReason3(reason.getNo());
                            this.empSchedule.setStatus3(status_sch);

                        }

                        if (this.empSchedule.getD4() == reason.getScheduleId()) {

                            this.empSchedule.setReason4(reason.getNo());
                            this.empSchedule.setStatus4(status_sch);

                        }

                        if (this.empSchedule.getD5() == reason.getScheduleId()) {

                            this.empSchedule.setReason5(reason.getNo());
                            this.empSchedule.setStatus5(status_sch);

                        }

                        if (this.empSchedule.getD6() == reason.getScheduleId()) {

                            this.empSchedule.setReason6(reason.getNo());
                            this.empSchedule.setStatus6(status_sch);

                        }

                        if (this.empSchedule.getD7() == reason.getScheduleId()) {

                            this.empSchedule.setReason7(reason.getNo());
                            this.empSchedule.setStatus7(status_sch);

                        }

                        if (this.empSchedule.getD8() == reason.getScheduleId()) {

                            this.empSchedule.setReason8(reason.getNo());
                            this.empSchedule.setStatus8(status_sch);

                        }

                        if (this.empSchedule.getD9() == reason.getScheduleId()) {

                            this.empSchedule.setReason9(reason.getNo());
                            this.empSchedule.setStatus9(status_sch);

                        }

                        if (this.empSchedule.getD10() == reason.getScheduleId()) {

                            this.empSchedule.setReason10(reason.getNo());
                            this.empSchedule.setStatus10(status_sch);

                        }

                        if (this.empSchedule.getD11() == reason.getScheduleId()) {

                            this.empSchedule.setReason11(reason.getNo());
                            this.empSchedule.setStatus11(status_sch);

                        }

                        if (this.empSchedule.getD12() == reason.getScheduleId()) {

                            this.empSchedule.setReason12(reason.getNo());
                            this.empSchedule.setStatus12(status_sch);

                        }

                        if (this.empSchedule.getD13() == reason.getScheduleId()) {

                            this.empSchedule.setReason13(reason.getNo());
                            this.empSchedule.setStatus13(status_sch);

                        }

                        if (this.empSchedule.getD14() == reason.getScheduleId()) {

                            this.empSchedule.setReason14(reason.getNo());
                            this.empSchedule.setStatus14(status_sch);

                        }

                        if (this.empSchedule.getD15() == reason.getScheduleId()) {

                            this.empSchedule.setReason15(reason.getNo());
                            this.empSchedule.setStatus15(status_sch);

                        }

                        if (this.empSchedule.getD16() == reason.getScheduleId()) {

                            this.empSchedule.setReason16(reason.getNo());
                            this.empSchedule.setStatus16(status_sch);

                        }

                        if (this.empSchedule.getD17() == reason.getScheduleId()) {

                            this.empSchedule.setReason17(reason.getNo());
                            this.empSchedule.setStatus17(status_sch);

                        }

                        if (this.empSchedule.getD18() == reason.getScheduleId()) {

                            this.empSchedule.setReason18(reason.getNo());
                            this.empSchedule.setStatus18(status_sch);

                        }

                        if (this.empSchedule.getD19() == reason.getScheduleId()) {

                            this.empSchedule.setReason19(reason.getNo());
                            this.empSchedule.setStatus19(status_sch);

                        }

                        if (this.empSchedule.getD20() == reason.getScheduleId()) {

                            this.empSchedule.setReason20(reason.getNo());
                            this.empSchedule.setStatus20(status_sch);

                        }

                        if (this.empSchedule.getD21() == reason.getScheduleId()) {

                            this.empSchedule.setReason21(reason.getNo());
                            this.empSchedule.setStatus21(status_sch);

                        }

                        if (this.empSchedule.getD22() == reason.getScheduleId()) {

                            this.empSchedule.setReason22(reason.getNo());
                            this.empSchedule.setStatus22(status_sch);

                        }

                        if (this.empSchedule.getD23() == reason.getScheduleId()) {

                            this.empSchedule.setReason23(reason.getNo());
                            this.empSchedule.setStatus23(status_sch);

                        }

                        if (this.empSchedule.getD24() == reason.getScheduleId()) {

                            this.empSchedule.setReason24(reason.getNo());
                            this.empSchedule.setStatus24(status_sch);

                        }

                        if (this.empSchedule.getD25() == reason.getScheduleId()) {

                            this.empSchedule.setReason25(reason.getNo());
                            this.empSchedule.setStatus25(status_sch);

                        }

                        if (this.empSchedule.getD26() == reason.getScheduleId()) {

                            this.empSchedule.setReason26(reason.getNo());
                            this.empSchedule.setStatus26(status_sch);

                        }

                        if (this.empSchedule.getD27() == reason.getScheduleId()) {

                            this.empSchedule.setReason27(reason.getNo());
                            this.empSchedule.setStatus27(status_sch);

                        }

                        if (this.empSchedule.getD28() == reason.getScheduleId()) {

                            this.empSchedule.setReason28(reason.getNo());
                            this.empSchedule.setStatus28(status_sch);

                        }

                        if (this.empSchedule.getD29() == reason.getScheduleId()) {

                            this.empSchedule.setReason29(reason.getNo());
                            this.empSchedule.setStatus29(status_sch);

                        }

                        if (this.empSchedule.getD30() == reason.getScheduleId()) {

                            this.empSchedule.setReason30(reason.getNo());
                            this.empSchedule.setStatus30(status_sch);

                        }

                        if (this.empSchedule.getD31() == reason.getScheduleId()) {

                            this.empSchedule.setReason31(reason.getNo());
                            this.empSchedule.setStatus31(status_sch);

                        }


                        if (this.empSchedule.getD2nd1() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd1(reason.getNo());
                            this.empSchedule.setStatus2nd1(status_sch);

                        }

                        if (this.empSchedule.getD2nd2() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd2(reason.getNo());
                            this.empSchedule.setStatus2nd2(status_sch);

                        }

                        if (this.empSchedule.getD2nd3() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd3(reason.getNo());
                            this.empSchedule.setStatus2nd3(status_sch);

                        }

                        if (this.empSchedule.getD2nd4() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd4(reason.getNo());
                            this.empSchedule.setStatus2nd4(status_sch);

                        }

                        if (this.empSchedule.getD2nd5() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd5(reason.getNo());
                            this.empSchedule.setStatus2nd5(status_sch);

                        }

                        if (this.empSchedule.getD2nd6() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd6(reason.getNo());
                            this.empSchedule.setStatus2nd6(status_sch);

                        }

                        if (this.empSchedule.getD2nd7() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd7(reason.getNo());
                            this.empSchedule.setStatus2nd7(status_sch);

                        }

                        if (this.empSchedule.getD2nd8() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd8(reason.getNo());
                            this.empSchedule.setStatus2nd8(status_sch);

                        }

                        if (this.empSchedule.getD2nd9() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd9(reason.getNo());
                            this.empSchedule.setStatus2nd9(status_sch);

                        }

                        if (this.empSchedule.getD2nd10() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd10(reason.getNo());
                            this.empSchedule.setStatus2nd10(status_sch);

                        }

                        if (this.empSchedule.getD2nd11() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd11(reason.getNo());
                            this.empSchedule.setStatus2nd11(status_sch);

                        }

                        if (this.empSchedule.getD2nd12() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd12(reason.getNo());
                            this.empSchedule.setStatus2nd12(status_sch);

                        }

                        if (this.empSchedule.getD2nd13() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd13(reason.getNo());
                            this.empSchedule.setStatus2nd13(status_sch);

                        }

                        if (this.empSchedule.getD2nd14() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd14(reason.getNo());
                            this.empSchedule.setStatus2nd14(status_sch);

                        }

                        if (this.empSchedule.getD2nd15() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd15(reason.getNo());
                            this.empSchedule.setStatus2nd15(status_sch);

                        }

                        if (this.empSchedule.getD2nd16() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd16(reason.getNo());
                            this.empSchedule.setStatus2nd16(status_sch);

                        }

                        if (this.empSchedule.getD2nd17() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd17(reason.getNo());
                            this.empSchedule.setStatus2nd17(status_sch);

                        }

                        if (this.empSchedule.getD2nd18() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd18(reason.getNo());
                            this.empSchedule.setStatus2nd18(status_sch);

                        }

                        if (this.empSchedule.getD2nd19() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd19(reason.getNo());
                            this.empSchedule.setStatus2nd19(status_sch);

                        }

                        if (this.empSchedule.getD2nd20() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd20(reason.getNo());
                            this.empSchedule.setStatus2nd20(status_sch);

                        }

                        if (this.empSchedule.getD2nd21() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd21(reason.getNo());
                            this.empSchedule.setStatus2nd21(status_sch);

                        }

                        if (this.empSchedule.getD2nd22() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd22(reason.getNo());
                            this.empSchedule.setStatus2nd22(status_sch);

                        }

                        if (this.empSchedule.getD2nd23() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd23(reason.getNo());
                            this.empSchedule.setStatus2nd23(status_sch);

                        }

                        if (this.empSchedule.getD2nd24() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd24(reason.getNo());
                            this.empSchedule.setStatus2nd24(status_sch);

                        }

                        if (this.empSchedule.getD2nd25() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd25(reason.getNo());
                            this.empSchedule.setStatus2nd25(status_sch);

                        }

                        if (this.empSchedule.getD2nd26() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd26(reason.getNo());
                            this.empSchedule.setStatus2nd26(status_sch);

                        }

                        if (this.empSchedule.getD2nd27() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd27(reason.getNo());
                            this.empSchedule.setStatus2nd27(status_sch);

                        }

                        if (this.empSchedule.getD2nd28() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd28(reason.getNo());
                            this.empSchedule.setStatus2nd28(status_sch);

                        }

                        if (this.empSchedule.getD2nd29() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd29(reason.getNo());
                            this.empSchedule.setStatus2nd29(status_sch);

                        }

                        if (this.empSchedule.getD2nd30() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd30(reason.getNo());
                            this.empSchedule.setStatus2nd30(status_sch);

                        }

                        if (this.empSchedule.getD2nd31() == reason.getScheduleId()) {

                            this.empSchedule.setReason2nd31(reason.getNo());
                            this.empSchedule.setStatus2nd31(status_sch);

                        }
                    }
                }


                if (empSchedule.getOID() == 0) {
                    try {

                        boolean ScheduleExist = false;
                        ScheduleExist = SessEmpSchedule.ScheduleExist(this.empSchedule.getEmployeeId(),this.empSchedule.getPeriodId());
                        
                        if(ScheduleExist == true){
                            msgString = FRMMessage.getMsg(FRMMessage.MSG_SCHEDULE_EXIST);
                            return RSLT_FORM_INCOMPLETE;
                        }
                        
                        long oid = pstEmpSchedule.insertExc(this.empSchedule);
                        
                        if(oid != 0){
                            msgString = FRMMessage.getMsg(FRMMessage.MSG_SAVED);
                            /*insert to table history */
                            EmpSchedule empScheduleHystory = new EmpSchedule();
                            empScheduleHystory.setOID(oid);
                            empScheduleHystory.setEmployeeId(this.empSchedule.getEmployeeId());
                            empScheduleHystory.setPeriodId(this.empSchedule.getPeriodId());
                        
                            PstPresence.importPresenceTriggerByImportEmpScheduleExcel(empScheduleHystory, empSchedule);
                        
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

                        long oid = pstEmpSchedule.updateExc(this.empSchedule);
                        msgString = FRMMessage.getMsg(FRMMessage.MSG_SAVED);
                        
                        String whereHystory = PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_EMP_SCHEDULE_ORG_ID]+
                                " = "+oid+" AND "+PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_EMPLOYEE_ID]+" = "+
                                this.empSchedule.getEmployeeId()+" AND "+PstEmpScheduleHistory.fieldNames[PstEmpScheduleHistory.FLD_PERIOD_ID]+" = "+
                                this.empSchedule.getPeriodId();
                        
                        Vector listEmpSchHistory = new Vector();
                        listEmpSchHistory = PstEmpScheduleHistory.list(0, 0, whereHystory, null);
                        
                        if(listEmpSchHistory == null || listEmpSchHistory.size() <=0){
                            
                            if(oid != 0){
                        
                                /*insert to table history */
                                EmpSchedule empScheduleHystory = new EmpSchedule();
                                empScheduleHystory.setOID(oid);
                                empScheduleHystory.setEmployeeId(this.empSchedule.getEmployeeId());
                                empScheduleHystory.setPeriodId(this.empSchedule.getPeriodId());
                        
                                PstPresence.importPresenceTriggerByImportEmpScheduleExcel(empScheduleHystory, empSchedule);
                        
                            }
                            
                        }
                        
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }

                // --- proses simpan schedule original ke table temporary yg nantinya akan diproses oleh service utk pencocokan ---
                // PstPresence.importPresenceTriggerByImportEmpScheduleExcel();                                

                // --- proses import presence ---
                // proses ini dilakukan jika terlambat insert schedule ke HARISMA
                // atau update schedule untuk masing-masing employee
                // add by edhy
                // update in schedule                                   
                // PstPresence.importPresenceTriggerByEmpSchedule(orgEmpSchedule, empSchedule);                                

                break;

            case Command.EDIT:
                if (oidEmpSchedule != 0) {
                    try {
                        empSchedule = PstEmpSchedule.fetchExc(oidEmpSchedule);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidEmpSchedule != 0) {
                    try {
                        empSchedule = PstEmpSchedule.fetchExc(oidEmpSchedule);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidEmpSchedule != 0) {
                    try {
                        long oid = PstEmpSchedule.deleteExc(oidEmpSchedule);
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
                case Command.DELETEALL:
                //splits => untuk mengurutkan beberapa data menjadi baris data
                String[] splits = oidDeleteAll.split(",");
                for (String asset : splits) {
                    if (asset != "") {
                        long oidEdu = Long.parseLong(asset);
                        if (oidEdu != 0) {
                            try {
                                long oid = PstEmpSchedule.deleteExc(oidEdu);
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
}
