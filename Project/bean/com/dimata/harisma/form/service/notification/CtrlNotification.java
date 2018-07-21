/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.service.notification;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.dimata.harisma.entity.service.notification.Notification;
import com.dimata.harisma.entity.service.notification.NotificationRecipient;
import com.dimata.harisma.entity.service.notification.PstNotification;
import com.dimata.harisma.entity.service.notification.PstNotificationRecipient;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import javax.servlet.http.HttpServletRequest;
import org.apache.jasper.tagplugins.jstl.core.Catch;

/**
 *
 * @author GUSWIK
 */
public class CtrlNotification extends Control implements I_Language 
{
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
	private Notification notification;
	private PstNotification pstNotification;
	private FrmNotification frmNotification;
	int language = LANGUAGE_DEFAULT;

	public CtrlNotification(HttpServletRequest request){
		msgString = "";
		notification = new Notification();
		try{
			pstNotification = new PstNotification(0);
		}catch(Exception e){;}
		frmNotification = new FrmNotification(request, notification);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmNotification.addError(frmNotification.FRM_FIELD_NOTIFICATION_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public Notification getNotification() { return notification; } 

	public FrmNotification getForm() { return frmNotification; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidNotification, String oidDelete){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
                long oid = 0;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidNotification != 0){
					try{
						notification = PstNotification.fetchExc(oidNotification);
					}catch(Exception exc){
					}
				}

				frmNotification.requestEntityObjectSpecial(notification);

				if(frmNotification.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(notification.getOID()==0){
					try{
						oid = pstNotification.insertExc(this.notification);
                                                msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
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
						oid = pstNotification.updateExc(this.notification);
                                                msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
                                
                                try{
                                   PstNotificationRecipient.deleteRecipient(4, oidNotification);
                                   if (!notification.getSmsNum().equals("")){
                                        String []parts = notification.getSmsNum().split("-");
                                        if (parts.length > 0){
                                            for (int s = 0; s < parts.length; s++){
                                                String num = parts[s];
                                                NotificationRecipient notificationRecipient = new NotificationRecipient();
                                                notificationRecipient.setNotificationId(oid);
                                                notificationRecipient.setRecipientsSms(num);
                                                notificationRecipient.setRecipientAs(4);
                                                notificationRecipient.setFromAdd(1);
                                                PstNotificationRecipient.insertExc(notificationRecipient);
                                            }
                                        }
                                    } 
                                }catch (Exception e){}
                                try {
                                    PstNotificationRecipient.deleteRecipient(0, oidNotification);
                                    if (notification.getArrRecipient(0).length > 0){
                                        for (int x = 0 ; x < notification.getArrRecipient(0).length; x++ ){
                                            NotificationRecipient notificationRecipient = new NotificationRecipient();
                                            notificationRecipient.setNotificationId(oid);
                                            notificationRecipient.setRecipientsEmail(notification.getArrRecipient(0)[x]);
                                            notificationRecipient.setRecipientAs(0);
                                            PstNotificationRecipient.insertExc(notificationRecipient);
                                        }
                                    }
                                }catch(Exception e){}
                                
                                try{
                                   if (!notification.getRecipientAdd().equals("")){
                                        String []parts = notification.getRecipientAdd().split("-");
                                        if (parts.length > 0){
                                            for (int s = 0; s < parts.length; s++){
                                                String emails = parts[s];
                                                NotificationRecipient notificationRecipient = new NotificationRecipient();
                                                notificationRecipient.setNotificationId(oid);
                                                notificationRecipient.setRecipientsEmail(emails);
                                                notificationRecipient.setRecipientAs(0);
                                                notificationRecipient.setFromAdd(1);
                                                PstNotificationRecipient.insertExc(notificationRecipient);
                                            }
                                        }
                                    } 
                                }catch (Exception e){}
                                
                                
                                try {
                                    PstNotificationRecipient.deleteRecipient(1, oidNotification);
                                    if (notification.getArrCC(0).length > 0){
                                        for (int x = 0 ; x < notification.getArrCC(0).length; x++ ){
                                            NotificationRecipient notificationRecipient = new NotificationRecipient();
                                            notificationRecipient.setNotificationId(oid);
                                            notificationRecipient.setRecipientsEmail(notification.getArrRecipient(0)[x]);
                                            notificationRecipient.setRecipientAs(1);
                                            PstNotificationRecipient.insertExc(notificationRecipient);
                                        }
                                    }
                                }catch(Exception e){}
                                
                                try{
                                   if (!notification.getCcAdd().equals("")){
                                        String []parts = notification.getCcAdd().split("-");
                                        if (parts.length > 0){
                                            for (int s = 0; s < parts.length; s++){
                                                String emails = parts[s];
                                                NotificationRecipient notificationRecipient = new NotificationRecipient();
                                                notificationRecipient.setNotificationId(oid);
                                                notificationRecipient.setRecipientsEmail(emails);
                                                notificationRecipient.setRecipientAs(1);
                                                notificationRecipient.setFromAdd(1);
                                                PstNotificationRecipient.insertExc(notificationRecipient);
                                            }
                                        }
                                    } 
                                   
                                   
                                }catch (Exception e){}
                                     try {
                                    PstNotificationRecipient.deleteRecipient(2, oidNotification);
                                    if (notification.getArrBCC(0).length > 0){
                                        for (int x = 0 ; x < notification.getArrBCC(0).length; x++ ){
                                            NotificationRecipient notificationRecipient = new NotificationRecipient();
                                            notificationRecipient.setNotificationId(oid);
                                            notificationRecipient.setRecipientsEmail(notification.getArrBCC(0)[x]);
                                            notificationRecipient.setRecipientAs(2);
                                            PstNotificationRecipient.insertExc(notificationRecipient);
                                        }
                                    }
                                }catch(Exception e){}
                                
                                try{
                                   if (!notification.getBccAdd().equals("")){
                                        String []parts = notification.getBccAdd().split("-");
                                        if (parts.length > 0){
                                            for (int s = 0; s < parts.length; s++){
                                                String emails = parts[s];
                                                NotificationRecipient notificationRecipient = new NotificationRecipient();
                                                notificationRecipient.setNotificationId(oid);
                                                notificationRecipient.setRecipientsEmail(emails);
                                                notificationRecipient.setRecipientAs(2);
                                                notificationRecipient.setFromAdd(1);
                                                PstNotificationRecipient.insertExc(notificationRecipient);
                                            }
                                        }
                                    } 
                                   
                                   
                                }catch (Exception e){}
				break;

			case Command.EDIT :
				if (oidNotification != 0) {
					try {
						notification = PstNotification.fetchExc(oidNotification);
                                                msgString = FRMMessage.getMessage(FRMMessage.MSG_SAVED);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidNotification != 0) {
					try {
						notification = PstNotification.fetchExc(oidNotification);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;


			case Command.DELETE :
				if (oidNotification != 0){
					try{
						oid = PstNotification.deleteExc(oidNotification);
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
                            case Command.UPDATE :
				if(oidNotification != 0){
					try{
						notification = PstNotification.fetchExc(oidNotification);
					}catch(Exception exc){
					}
				}
                                if(notification.getStatus() == 0){
                                    notification.setStatus(1);
                                } else {
                                    notification.setStatus(0);
                                } 
				
					try {
						oid = pstNotification.updateExc(this.notification);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				
				break;
                              case Command.DELETEALL:
                                //splits => untuk mengurutkan beberapa data menjadi baris data
                                String[] splits = oidDelete.split(",");
                                for (String asset : splits) {
                                    if (asset != "") {
                                        oid = Long.parseLong(asset);
                                        if (oid != 0) {
                                            try {
                                                long result = PstNotification.deleteExc(oid);
                                                if (result != 0) {
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
