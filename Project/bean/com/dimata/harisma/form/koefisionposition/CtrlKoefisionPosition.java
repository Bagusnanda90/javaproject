/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.dimata.harisma.form.koefisionposition;


import com.dimata.harisma.entity.koefisionposition.KoefisionPosition;
import com.dimata.harisma.entity.koefisionposition.PstKoefisionPosition;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Devin
 */
public class CtrlKoefisionPosition  extends Control implements I_Language  {
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
    private  KoefisionPosition koefisionPosition;
    private PstKoefisionPosition pstKoefisionPosition;
    private FrmKoefisionPosition frmKoefisionPosition;
    int language = LANGUAGE_DEFAULT;

    public CtrlKoefisionPosition (HttpServletRequest request){
                msgString = "";
                koefisionPosition = new KoefisionPosition();
                try{
                     pstKoefisionPosition = new PstKoefisionPosition(0) ;
                }catch(Exception e){
                    System.out.println("Exception"+e);
                   
                }
		frmKoefisionPosition = new FrmKoefisionPosition(request, koefisionPosition);
	}
    private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
                        this.frmKoefisionPosition.addError(FrmKoefisionPosition.FRM_FLD_KOEFISION_POSITION_ID,                                        
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
    public KoefisionPosition getCoefisionPosition() { return koefisionPosition; }
    public FrmKoefisionPosition getForm() { return frmKoefisionPosition; }
    public String getMessage(){ return msgString; }
    public int getStart() { return start; }
    public int action(int cmd , long oidKoefisionPosition){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
                switch(cmd){
			case Command.ADD :
				break;
                        case Command.SAVE :
				if(oidKoefisionPosition != 0){
					try{
						koefisionPosition = PstKoefisionPosition.fetchExc(oidKoefisionPosition);
					}catch(Exception exc){}
				}
				frmKoefisionPosition.requestEntityObject(koefisionPosition);
				if(frmKoefisionPosition.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
                              
				if(koefisionPosition.getOID()==0){
					try{
						long oid = PstKoefisionPosition.insertExc(this.koefisionPosition);
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
						long oid = PstKoefisionPosition.updateExc(this.koefisionPosition);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;
			case Command.EDIT :

				if (oidKoefisionPosition != 0) {

					try {

						koefisionPosition = PstKoefisionPosition.fetchExc(oidKoefisionPosition);

					} catch (DBException dbexc){

						excCode = dbexc.getErrorCode();

						msgString = getSystemMessage(excCode);

					} catch (Exception exc){

						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

					}

				}

				break;



			case Command.ASK :

				if (oidKoefisionPosition != 0) {

					try {

						koefisionPosition = PstKoefisionPosition.fetchExc(oidKoefisionPosition);

					} catch (DBException dbexc){

						excCode = dbexc.getErrorCode();

						msgString = getSystemMessage(excCode);

					} catch (Exception exc){

						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

					}

				}

				break;
			case Command.DELETE :
				if (oidKoefisionPosition!= 0){

					try{
						long oid = PstKoefisionPosition.deleteExc(oidKoefisionPosition);
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
