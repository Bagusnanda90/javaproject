/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.dimata.harisma.form.adjusmentWorkingDay;



import com.dimata.harisma.entity.adjusmentworkingday.AdjusmentWorkingDay;
import com.dimata.harisma.entity.adjusmentworkingday.PstAdjusmentWorkingDay;
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
public class CtrlAdjusmentWorkingDay  extends Control implements I_Language  {
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
    private  AdjusmentWorkingDay adjusmentWorkingDay;
    private PstAdjusmentWorkingDay pstAdjusmentWorkingDay;
    private FrmAdjusmentWorkingDay frmAdjusmentWorkingDay;
    int language = LANGUAGE_DEFAULT;

    public CtrlAdjusmentWorkingDay (HttpServletRequest request){
                msgString = "";
                adjusmentWorkingDay = new AdjusmentWorkingDay();
                try{
                     pstAdjusmentWorkingDay = new PstAdjusmentWorkingDay(0) ;
                }catch(Exception e){
                    System.out.println("Exception"+e);
                   
                }
		frmAdjusmentWorkingDay = new FrmAdjusmentWorkingDay(request, adjusmentWorkingDay);
	}
    private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
                        this.frmAdjusmentWorkingDay.addError(FrmAdjusmentWorkingDay.FRM_FLD_ADJUSMENT_WORKING_DAY_ID,                                        
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
    public AdjusmentWorkingDay getAdjusmentWorkingDay() { return adjusmentWorkingDay; }
    public FrmAdjusmentWorkingDay getForm() { return frmAdjusmentWorkingDay; }
    public String getMessage(){ return msgString; }
    public int getStart() { return start; }
    public int action(int cmd , long oidAdjusmentWorkingDay){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
                switch(cmd){
			case Command.ADD :
				break;
                        case Command.SAVE :
				if(oidAdjusmentWorkingDay != 0){
					try{
						adjusmentWorkingDay = PstAdjusmentWorkingDay.fetchExc(oidAdjusmentWorkingDay);
					}catch(Exception exc){}
				}
				frmAdjusmentWorkingDay.requestEntityObject(adjusmentWorkingDay);
				if(frmAdjusmentWorkingDay.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}
				if(adjusmentWorkingDay.getOID()==0){
                                    if(adjusmentWorkingDay.getEmployeeId()==0){
                                        return 100;
                                    }
                                    if(adjusmentWorkingDay.getLocationId()==0){
                                        return 101;
                                    }
					try{
						long oid = PstAdjusmentWorkingDay.insertExc(this.adjusmentWorkingDay);
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
						long oid = PstAdjusmentWorkingDay.updateExc(this.adjusmentWorkingDay);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;
			case Command.EDIT :

				if (oidAdjusmentWorkingDay != 0) {

					try {

						adjusmentWorkingDay = PstAdjusmentWorkingDay.fetchExc(oidAdjusmentWorkingDay);

					} catch (DBException dbexc){

						excCode = dbexc.getErrorCode();

						msgString = getSystemMessage(excCode);

					} catch (Exception exc){

						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

					}

				}

				break;



			case Command.ASK :

				if (oidAdjusmentWorkingDay != 0) {

					try {

						adjusmentWorkingDay = PstAdjusmentWorkingDay.fetchExc(oidAdjusmentWorkingDay);

					} catch (DBException dbexc){

						excCode = dbexc.getErrorCode();

						msgString = getSystemMessage(excCode);

					} catch (Exception exc){

						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);

					}

				}

				break;
			case Command.DELETE :
				if (oidAdjusmentWorkingDay!= 0){

					try{
						long oid = PstAdjusmentWorkingDay.deleteExc(oidAdjusmentWorkingDay);
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
