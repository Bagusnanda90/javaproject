/*
 * CtrlTax_PTKP.java
 *
 * Created on August 20, 2007, 2:19 PM
 */

package com.dimata.harisma.form.payroll;
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
import com.dimata.harisma.entity.payroll.*;
/**
 *
 * @author  emerliana
 */
public class CtrlTax_PTKP  extends Control implements I_Language{
    
    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Konfigurasi tipe ini sudah ada", "Data tidak lengkap"},
        {"Succes", "Can not process", "This configuration type exist", "Data incomplete"}
    };
    
    private int start;
    private String msgString;
    private Tax_PTKP tax_PTKP;
    private PstTax_PTKP pstTax_PTKP;
    private FrmTax_PTKP frmTax_PTKP;
    int language = LANGUAGE_DEFAULT;
    
     public CtrlTax_PTKP(HttpServletRequest request){
        msgString = "";
        tax_PTKP = new Tax_PTKP();
        try{
            pstTax_PTKP = new PstTax_PTKP(0);
        }catch(Exception e){;}
        frmTax_PTKP = new FrmTax_PTKP(request, tax_PTKP);
    }
    
    private String getSystemMessage(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                this.frmTax_PTKP.addError(frmTax_PTKP.FRM_FIELD_PAY_TAX_PTKP_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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
    
    public Tax_PTKP getTax_PTKP() { return tax_PTKP; }
    
    public FrmTax_PTKP getForm() { return frmTax_PTKP; }
    
    public String getMessage(){ return msgString; }
    
    public int getStart() { return start; }
    
    public int action(int cmd , long oidTax_PTKP, long idRegulasi, HttpServletRequest request){
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch(cmd){
            case Command.ADD :
                break;
                
            case Command.SAVE :
                if(oidTax_PTKP != 0){
                    try{
                        tax_PTKP = PstTax_PTKP.fetchExc(oidTax_PTKP);
                    }catch(Exception exc){
                    }
                }
                
                frmTax_PTKP.requestEntityObject(tax_PTKP);
                
                System.out.println("idRegulasi::::::::::::::::::::::::::::::::::::::"+idRegulasi);
                tax_PTKP.setRegulasi_id(idRegulasi);
                
                if(frmTax_PTKP.errorSize()>0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE ;
                }
               
                if(tax_PTKP.getOID()==0){
                    try{
                        long oid = pstTax_PTKP.insertExc(this.tax_PTKP);
                    }catch(DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    }catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }
                    
                }else{
                    try {
                        long oid = pstTax_PTKP.updateExc(this.tax_PTKP);
                    }catch (DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                    
                }
                break;
                
            case Command.EDIT :
                if (oidTax_PTKP != 0) {
                    try {
                        tax_PTKP = PstTax_PTKP.fetchExc(oidTax_PTKP);
                    } catch (DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            case Command.ASK :
                if (oidTax_PTKP != 0) {
                    try {
                        tax_PTKP = PstTax_PTKP.fetchExc(oidTax_PTKP);
                    } catch (DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            case Command.DELETE :
                if (oidTax_PTKP != 0){
                    try{
                        long oid = PstTax_PTKP.deleteExc(oidTax_PTKP);
                        if(oid!=0){
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            excCode = RSLT_OK;
                        }else{
                            msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                            excCode = RSLT_FORM_INCOMPLETE;
                        }
                    }catch(DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }catch(Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            default :
                if (oidTax_PTKP != 0) {
                    try {
                        tax_PTKP = PstTax_PTKP.fetchExc(oidTax_PTKP);
                    } catch (DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
        }
        return rsCode;
    }
    
    
}
