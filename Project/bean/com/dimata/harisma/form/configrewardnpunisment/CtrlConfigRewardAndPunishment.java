/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.form.configrewardnpunisment;

import com.dimata.harisma.entity.configrewardnpunisment.ConfigRewardAndPunishment;
import com.dimata.harisma.entity.configrewardnpunisment.PstConfigRewardAndPunishment;
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
public class CtrlConfigRewardAndPunishment   extends Control implements I_Language  {
    
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
    private  ConfigRewardAndPunishment configRewardAndPunishment;
    private PstConfigRewardAndPunishment pstConfigRewardAndPunishment;
    private FrmConfigRewardAndPunisment frmConfigRewardAndPunisment;
    int language = LANGUAGE_DEFAULT;

    public CtrlConfigRewardAndPunishment (HttpServletRequest request){
                msgString = "";
               
                configRewardAndPunishment = new ConfigRewardAndPunishment();
                try{
                     pstConfigRewardAndPunishment = new PstConfigRewardAndPunishment(0) ;
                     frmConfigRewardAndPunisment = new FrmConfigRewardAndPunisment(request, configRewardAndPunishment);
                }catch(Exception e){
                    System.out.println("Exception"+e);
                   
                }
		
	}
    private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
                        this.frmConfigRewardAndPunisment.addError(FrmConfigRewardAndPunisment.FRM_FLD_CONFIG_ID,                                        
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
    public ConfigRewardAndPunishment getConfigRewardAndPunishment() { return configRewardAndPunishment; }
    public FrmConfigRewardAndPunisment getForm() { return frmConfigRewardAndPunisment; }
    public String getMessage(){ return msgString; }
    public int getStart() { return start; }
    public int action(int cmd , long oidConfig){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
                switch(cmd){
			case Command.ADD :
				break;
                        case Command.SAVE :
				if(oidConfig != 0){
					try{
						configRewardAndPunishment = PstConfigRewardAndPunishment.fetchExc(oidConfig);
					}catch(Exception exc){}
				}
				frmConfigRewardAndPunisment.requestEntityObject(configRewardAndPunishment);
				if(frmConfigRewardAndPunisment.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
				if(configRewardAndPunishment.getOID()==0){
					try{
						long oid = PstConfigRewardAndPunishment.insertExc(this.configRewardAndPunishment);
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
						long oid = PstConfigRewardAndPunishment.updateExc(this.configRewardAndPunishment);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;
			case Command.EDIT :

				if (oidConfig != 0) {

					try {

						configRewardAndPunishment = PstConfigRewardAndPunishment.fetchExc(oidConfig);

					} catch (DBException dbexc){

						excCode = dbexc.getErrorCode();

						msgString = getSystemMessage(excCode);

					} catch (Exception exc){

						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

					}

				}

				break;



			case Command.ASK :

				if (oidConfig != 0) {

					try {

						configRewardAndPunishment = PstConfigRewardAndPunishment.fetchExc(oidConfig);

					} catch (DBException dbexc){

						excCode = dbexc.getErrorCode();

						msgString = getSystemMessage(excCode);

					} catch (Exception exc){

						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

					}

				}

				break;
			case Command.DELETE :
				if (oidConfig!= 0){

					try{
						long oid = PstConfigRewardAndPunishment.deleteExc(oidConfig);
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
