/* 
 * Ctrl Name  		:  CtrlPosition.java 
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

import java.util.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.qdep.db.DBException;
import com.dimata.util.lang.I_Language;
import com.dimata.util.Command;

import javax.servlet.http.HttpServletRequest;

public class CtrlLLStockManagement extends Control implements I_Language
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
	private LLStockManagement llStockManagement;
	private PstLLStockManagement pstLLStockManagement;
	private FrmLLStockManagement frmLLStockManagement;
	int language = LANGUAGE_DEFAULT;

	public CtrlLLStockManagement(HttpServletRequest request){
		msgString = "";
		llStockManagement = new LLStockManagement();
		try{
			pstLLStockManagement = new PstLLStockManagement(0);
		}catch(Exception e){;}
		frmLLStockManagement = new FrmLLStockManagement(request, llStockManagement);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmLLStockManagement.addError(0, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public LLStockManagement getLLStockManagement() { return llStockManagement; }

	public FrmLLStockManagement getForm() { return frmLLStockManagement; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidLLStockManagement){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD : 
				break;

			case Command.SAVE :
				if(oidLLStockManagement != 0){
					try{
						llStockManagement = PstLLStockManagement.fetchExc(oidLLStockManagement);
					}catch(Exception exc){
					}
				}

				frmLLStockManagement.requestEntityObject(llStockManagement);

				if(frmLLStockManagement.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(llStockManagement.getOID()==0){
					try{
						long oid = pstLLStockManagement.insertExc(this.llStockManagement);
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
						long oid = pstLLStockManagement.updateExc(this.llStockManagement);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
                                
                                /** 
                                 * proses ke Ll Stock Taken
                                 * algoritma : 
                                 *  - proses ini dilakukan hanya jika jumlah LL Used adalah lebih dari nol
                                 *  - jika LL Quantity kurang dari Used Quantity (QTY < USED) 
                                 *      => list LL Stock Taken diurutkan berdasarkan "taken_date"       
                                 *      => utk masing-masing record LL Stock taken akan mewakili 1 
                                 *         jumlah USED (LL Stock Management) sehingga LLStockId adalah OID dari 
                                 *         "object LL Stock Management" ini 
                                 *      => utk record LL Stock Taken yang tidak "kebagian", 
                                 *         maka LLStockId diset menjadi nol artinya ngutang LL   
                                 * edited by Edhy               
                                 */                    
                                if( llStockManagement.getQtyUsed() > 0 )
                                {
                                    if( llStockManagement.getLLQty() < llStockManagement.getQtyUsed() )
                                    {
                                        String whereClause = PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LL_STOCK_ID] +
                                                             " = " + llStockManagement.getOID() + 
                                                             " AND " + PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_EMPLOYEE_ID] +
                                                             " = " + llStockManagement.getEmployeeId();
                                        String orderByCls  = PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_TAKEN_DATE];
                                        Vector vectLlTaken = PstLlStockTaken.list(0, 0, whereClause, orderByCls);
                                        if(vectLlTaken!=null && vectLlTaken.size()>0)
                                        {
                                            int maxLlTaken = vectLlTaken.size();
                                            float maxLlQty = llStockManagement.getLLQty();
                                            for(int i=0; i<maxLlTaken; i++)
                                            {
                                                LlStockTaken objLlStockTaken = (LlStockTaken) vectLlTaken.get(i);
                                                if(i >= maxLlQty)
                                                {
                                                    objLlStockTaken.setLlStockId(0);
                                                    try
                                                    {
                                                        long oid = PstLlStockTaken.updateExc(objLlStockTaken);
                                                    }
                                                    catch(Exception e)
                                                    {
                                                        System.out.println("Exc when update LlStockId to null : " + e.toString());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }        

                                /**
                                 * start proses pembayaran hutang dengan data LL
                                 * algoritma : 
                                 *  - proses ini dilakukan hanya jika jumlah status AL adalah aktif
                                 *
                                 * edited by Edhy      
                                 */           
                                if( llStockManagement.getLLStatus()==PstLLStockManagement.LL_STS_AKTIF )
                                {
                                    Vector vectOidLeavePaid2 = PstLLStockManagement.paidLlPayable(llStockManagement.getEmployeeId(), llStockManagement);																																																																																		
                                }
                                
				break;

			case Command.EDIT :
				if (oidLLStockManagement != 0) {
					try {
						llStockManagement = PstLLStockManagement.fetchExc(oidLLStockManagement);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidLLStockManagement != 0) {
                	try{
						llStockManagement = PstLLStockManagement.fetchExc(oidLLStockManagement);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidLLStockManagement != 0){
					try{
						long oid = PstLLStockManagement.deleteExc(oidLLStockManagement);
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
