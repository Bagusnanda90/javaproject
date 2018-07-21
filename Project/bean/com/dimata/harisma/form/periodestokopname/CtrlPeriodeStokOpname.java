/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.periodestokopname;

import com.dimata.harisma.entity.periodestokopname.PeriodeStokOpname;
import com.dimata.harisma.entity.periodestokopname.PstPeriodeStokOpname;
import com.dimata.harisma.entity.periodestokopname.PeriodeStokOpname;
import com.dimata.harisma.entity.periodestokopname.PstPeriodeStokOpname;
import com.dimata.harisma.form.periodestokopname.FrmPeriodeStokOpname;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Devin
 */
public class CtrlPeriodeStokOpname extends Control implements I_Language  {
    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;

    public static String[][] resultText ={
        {"Berhasil","Tidak Bisa Di Prosess","kode Sudah Ada","Data Belum Lengkap"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
    };

    private int start;
    private String msgString;
    private  PeriodeStokOpname periodeStokOpname;
    private PstPeriodeStokOpname pstPeriodeStokOpname;
    private FrmPeriodeStokOpname frmPeriodeStokOpname;
    int language = LANGUAGE_DEFAULT;

    public CtrlPeriodeStokOpname (HttpServletRequest request){
                msgString = "";
                periodeStokOpname = new PeriodeStokOpname();
                try{
                     pstPeriodeStokOpname = new PstPeriodeStokOpname(0) ;
                }catch(Exception e){
                    System.out.println("Exception"+e);
                   
                }
		frmPeriodeStokOpname = new FrmPeriodeStokOpname(request, periodeStokOpname);
	}
    private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
                        this.frmPeriodeStokOpname.addError(FrmPeriodeStokOpname.FRM_PERIOD_OPNAME_ID,                                        
                             resultText[language][RSLT_EST_CODE_EXIST] );
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
    public PeriodeStokOpname getPeriodStokOpname() { return periodeStokOpname; }
    public FrmPeriodeStokOpname getForm() { return frmPeriodeStokOpname; }
    public String getMessage(){ return msgString; }
    public int getStart() { return start; }
    public int action(int cmd , long oidPeriodeStokOpname){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
                switch(cmd){
			case Command.ADD :
				break;
                        case Command.SAVE :
				if(oidPeriodeStokOpname != 0){ 
					try{
						periodeStokOpname = PstPeriodeStokOpname.fetchExc(oidPeriodeStokOpname);
					}catch(Exception exc){}
				}
				frmPeriodeStokOpname.requestEntityObject(periodeStokOpname);
				if(frmPeriodeStokOpname.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
				if(periodeStokOpname.getOID()==0){
                                   
					try{
						long oid = PstPeriodeStokOpname.insertExc(this.periodeStokOpname);
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
						long oid = PstPeriodeStokOpname.updateExc(this.periodeStokOpname);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;
			case Command.EDIT :

				if (oidPeriodeStokOpname != 0) {

					try {

						periodeStokOpname = PstPeriodeStokOpname.fetchExc(oidPeriodeStokOpname);

					} catch (DBException dbexc){

						excCode = dbexc.getErrorCode();

						msgString = getSystemMessage(excCode);

					} catch (Exception exc){

						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

					}

				}

				break;



			case Command.ASK :

				if (oidPeriodeStokOpname != 0) {

					try {

						periodeStokOpname = PstPeriodeStokOpname.fetchExc(oidPeriodeStokOpname);

					} catch (DBException dbexc){

						excCode = dbexc.getErrorCode();

						msgString = getSystemMessage(excCode);

					} catch (Exception exc){

						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

					}

				}

				break;
			case Command.DELETE :
				if (oidPeriodeStokOpname!= 0){

					try{
						long oid = PstPeriodeStokOpname.deleteExc(oidPeriodeStokOpname);
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
		}
		return rsCode;
	}
    
    
}
