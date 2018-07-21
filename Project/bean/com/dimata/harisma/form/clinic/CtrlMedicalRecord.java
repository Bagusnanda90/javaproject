/* 
 * Ctrl Name  		:  CtrlMedicalRecord.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.harisma.form.clinic;

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

import com.dimata.harisma.entity.clinic.*;

import com.dimata.harisma.entity.clinic.*;
import com.dimata.harisma.session.clinic.*;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;

public class CtrlMedicalRecord extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete"}};
    private int start;
    private String msgString;
    private MedicalRecord medicalRecord;
    private PstMedicalRecord pstMedicalRecord;
    private FrmMedicalRecord frmMedicalRecord;
    int language = LANGUAGE_DEFAULT;

    public CtrlMedicalRecord(HttpServletRequest request) {

        msgString = "";

        medicalRecord = new MedicalRecord();

        try {

            pstMedicalRecord = new PstMedicalRecord(0);

        } catch (Exception e) {
            ;
        }

        frmMedicalRecord = new FrmMedicalRecord(request, medicalRecord);

    }

    private String getSystemMessage(int msgCode) {

        switch (msgCode) {

            case I_DBExceptionInfo.MULTIPLE_ID:

                this.frmMedicalRecord.addError(frmMedicalRecord.FRM_FIELD_MEDICAL_RECORD_ID, resultText[language][RSLT_EST_CODE_EXIST]);

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

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public FrmMedicalRecord getForm() {
        return frmMedicalRecord;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidMedicalRecord) {

        msgString = "";

        int excCode = I_DBExceptionInfo.NO_EXCEPTION;

        int rsCode = RSLT_OK;

        switch (cmd) {

            case Command.ADD:

                break;



            case Command.SAVE:

                if (oidMedicalRecord != 0) {

                    try {

                        medicalRecord = PstMedicalRecord.fetchExc(oidMedicalRecord);

                    } catch (Exception exc) {

                    }

                }



                double before = medicalRecord.getTotal();

                frmMedicalRecord.requestEntityObject(medicalRecord);

                String whereStr = " ( " + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] + "" +
                        " <= '" + Formater.formatDate(medicalRecord.getRecordDate(), "yyyy-MM-dd hh:mm:ss") + "') " +
                        " AND ( " + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] + "" +
                        " >= '" + Formater.formatDate(medicalRecord.getRecordDate(), "yyyy-MM-dd hh:mm:ss") + "') ";

                String orderStr = " "+PstPeriod.fieldNames[PstPeriod.FLD_END_DATE]+ " DESC";

                Vector listHRPeriod = PstPeriod.list(0, 1, whereStr, orderStr);
                Period perHr = null;
                Date hrMedStart = null;
                Date hrMedEnd = null;
                
                if(listHRPeriod!=null && listHRPeriod.size()>0){
                    perHr = (Period) listHRPeriod.get(0);
                    hrMedStart = perHr.getStartDate();
                    hrMedEnd = perHr.getEndDate();
                } else {
                    frmMedicalRecord.addError(frmMedicalRecord.FRM_FIELD_TOTAL,  " Period of HR is not defined yet");
                    msgString = " Please define HR period on the medical record date";
                    return RSLT_FORM_INCOMPLETE ;                    
                }

                CheckMedBudgetRslt chkMed = SessMedicalRecord.checkMedicalBudget(medicalRecord.getEmployeeId(), medicalRecord.getFamilyMemberId(),
                        medicalRecord, medicalRecord.getMedicalCaseId(), medicalRecord.getCaseQuantity(), medicalRecord.getAmount(), 
                        medicalRecord.getRecordDate(), hrMedStart, hrMedEnd, hrMedStart.getDate());
                
                if( chkMed.getErrCode()!=CheckMedBudgetRslt.RESULT_OK){                    
                    frmMedicalRecord.addError(frmMedicalRecord.FRM_FIELD_TOTAL,  chkMed.getMessage());
                    msgString = "Data not approved: "+chkMed.getMessage() + " Please correct the data";
                    return RSLT_FORM_INCOMPLETE ;                                        
                }



                /**  disable by Kartika 
                //eka --------
                MedicalType mt = new MedicalType();
                try{
                mt  = PstMedicalType.fetchExc(medicalRecord.getMedicalTypeId());
                }
                catch(Exception e){
                }
                System.out.println("\n========\nyearly amount : "+mt.getYearlyAmount());
                System.out.println("medicalRecord.getMedicalTypeId() : "+medicalRecord.getMedicalTypeId());
                double max = 0;
                double amount = PstMedicalRecord.getTotalMedicalUsed(medicalRecord.getEmployeeId(), medicalRecord.getMedicalTypeId());
                System.out.println("amount used : "+amount);
                if(medicalRecord.getOID()==0){
                max = mt.getYearlyAmount() - amount;
                amount = amount + medicalRecord.getTotal();                                    
                }
                else{
                max = mt.getYearlyAmount() - (amount - before);
                amount = amount + medicalRecord.getTotal() - before;                                    
                }
                System.out.println("max : "+max);
                if(mt.getYearlyAmount()<amount){
                frmMedicalRecord.addError(frmMedicalRecord.FRM_FIELD_AMOUNT,  "Maximum amount is "+Formater.formatNumber(max, "#,###.##"));
                msgString = "Amount over maximum medical budget";
                return RSLT_FORM_INCOMPLETE ;
                }
                //------------
                 **/
                if (frmMedicalRecord.errorSize() > 0) {

                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);

                    return RSLT_FORM_INCOMPLETE;

                }



                if (medicalRecord.getOID() == 0) {

                    try {

                        long oid = pstMedicalRecord.insertExc(this.medicalRecord);

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

                        long oid = pstMedicalRecord.updateExc(this.medicalRecord);

                    } catch (DBException dbexc) {

                        excCode = dbexc.getErrorCode();

                        msgString = getSystemMessage(excCode);

                    } catch (Exception exc) {

                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

                    }



                }

                break;



            case Command.EDIT:

                if (oidMedicalRecord != 0) {

                    try {

                        medicalRecord = PstMedicalRecord.fetchExc(oidMedicalRecord);

                    } catch (DBException dbexc) {

                        excCode = dbexc.getErrorCode();

                        msgString = getSystemMessage(excCode);

                    } catch (Exception exc) {

                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

                    }

                }

                break;



            case Command.ASK:

                if (oidMedicalRecord != 0) {

                    try {

                        medicalRecord = PstMedicalRecord.fetchExc(oidMedicalRecord);

                    } catch (DBException dbexc) {

                        excCode = dbexc.getErrorCode();

                        msgString = getSystemMessage(excCode);

                    } catch (Exception exc) {

                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

                    }

                }

                break;



            case Command.DELETE:

                if (oidMedicalRecord != 0) {

                    try {

                        long oid = PstMedicalRecord.deleteExc(oidMedicalRecord);

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

