/* 
 * Ctrl Name  		:  CtrlLeaveStock.java 
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

public class CtrlLeaveStock extends Control implements I_Language 
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
	private LeaveStock leaveStock;
	private PstLeaveStock pstLeaveStock;
	private FrmLeaveStock frmLeaveStock;
	int language = LANGUAGE_DEFAULT;

	public CtrlLeaveStock(HttpServletRequest request){
		msgString = "";
		leaveStock = new LeaveStock();
		try{
			pstLeaveStock = new PstLeaveStock(0);
		}catch(Exception e){;}
		frmLeaveStock = new FrmLeaveStock(request, leaveStock);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmLeaveStock.addError(frmLeaveStock.FRM_FIELD_LEAVE_STOCK_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public LeaveStock getLeaveStock() { return leaveStock; } 

	public FrmLeaveStock getForm() { return frmLeaveStock; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , Vector leaveStock){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				/*if(oidLeaveStock != 0){
					try{
						leaveStock = PstLeaveStock.fetchExc(oidLeaveStock);
					}catch(Exception exc){
					}
				}*/

				//frmLeaveStock.requestEntityObject(leaveStock);

                long oid = 0;

                if(leaveStock!=null && leaveStock.size()>0){
                	for(int i=0; i<leaveStock.size(); i++){
                    	LeaveStock newLeaveStock = (LeaveStock)leaveStock.get(i);
                           if(newLeaveStock.getOID()!=0){
	                           try{
			                        LeaveStock leaveStockOld = PstLeaveStock.fetchExc(newLeaveStock.getOID());
			
			                        leaveStockOld.setAddAl(newLeaveStock.getAddAl());
			                        leaveStockOld.setAddLl(newLeaveStock.getAddLl());
			                        leaveStockOld.setAddDp(newLeaveStock.getAddDp());
			
			                        leaveStockOld.setAlAmount(leaveStockOld.getAlAmount() + newLeaveStock.getAddAl());
			                        leaveStockOld.setLlAmount(leaveStockOld.getLlAmount() + newLeaveStock.getAddLl());
			                        leaveStockOld.setDpAmount(leaveStockOld.getDpAmount() + newLeaveStock.getAddDp());
	
	
			                        oid = PstLeaveStock.updateExc(leaveStockOld);
                               }
                               catch(Exception e){
									System.out.println("Exception e oid  "+newLeaveStock.getOID()+" : "+e.toString());
                               }
                           }
                           else{
		                        newLeaveStock.setAlAmount(newLeaveStock.getAddAl());
		                        newLeaveStock.setLlAmount(newLeaveStock.getAddLl());
		                        newLeaveStock.setDpAmount(newLeaveStock.getAddDp());

                               try{
                                oid = PstLeaveStock.insertExc(newLeaveStock);
                               }
                               catch(Exception e){
                                   System.out.println("Exception e new leave :"+e.toString());
                               }
                           }


                	}
            	}

				if(oid == 0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				/*if(leaveStock.getOID()==0){
					try{
						long oid = pstLeaveStock.insertExc(this.leaveStock);
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
						long oid = pstLeaveStock.updateExc(this.leaveStock);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}*/
				break;

			case Command.EDIT :
				/*if (oidLeaveStock != 0) {
					try {
						leaveStock = PstLeaveStock.fetchExc(oidLeaveStock);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}*/
				break;

			case Command.ASK :
			/*	if (oidLeaveStock != 0) {
					try {
						leaveStock = PstLeaveStock.fetchExc(oidLeaveStock);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}    */
				break;

			case Command.DELETE :
			/*	if (oidLeaveStock != 0){
					try{
						long oid = PstLeaveStock.deleteExc(oidLeaveStock);
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
				}    */
				break;

			default :

		}
		return rsCode;
	}
}
