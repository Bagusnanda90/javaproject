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

//import com.dimata.harisma.db.*;
import java.util.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.qdep.db.DBException;
import com.dimata.util.lang.I_Language;
import com.dimata.util.Command;

import javax.servlet.http.HttpServletRequest;

public class CtrlAlStockManagement extends Control implements I_Language {
    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_OWN_DATE_INVALID = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Tanggal perolehan tidak sesuai", "Data tidak lengkap"},
        {"Succes", "Can not process", "Owning date invalid", "Data incomplete"}
    };
    
    private int start;
    private String msgString;
    private AlStockManagement alStockManagement;
    private PstAlStockManagement pstAlStockManagement;
    private FrmAlStockManagement frmAlStockManagement;
    int language = LANGUAGE_DEFAULT;
    
    public CtrlAlStockManagement(HttpServletRequest request){
        msgString = "";
        alStockManagement = new AlStockManagement();
        try{
            pstAlStockManagement = new PstAlStockManagement(0);
        }catch(Exception e){;}
        frmAlStockManagement = new FrmAlStockManagement(request, alStockManagement);
    }
    
    private String getSystemMessage(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                this.frmAlStockManagement.addError(0, resultText[language][RSLT_OWN_DATE_INVALID] );
                return resultText[language][RSLT_OWN_DATE_INVALID];
            default:
                return resultText[language][RSLT_UNKNOWN_ERROR];
        }
    }
    
    private int getControlMsgId(int msgCode){
        switch (msgCode){
            case I_DBExceptionInfo.MULTIPLE_ID :
                return RSLT_OWN_DATE_INVALID;
            default:
                return RSLT_UNKNOWN_ERROR;
        }
    }
    
    public int getLanguage(){ return language; }
    
    public void setLanguage(int language){ this.language = language; }
    
    public AlStockManagement getDivision() { return alStockManagement; }
    
    public FrmAlStockManagement getForm() { return frmAlStockManagement; }
    
    public String getMessage(){ return msgString; }
    
    public int getStart() { return start; }
    
    public int action(int cmd , long oidAlStockManagement){
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch(cmd){
            case Command.ADD :
                break;
                
            case Command.SAVE :
                AlStockManagement alStockManagementOld = new AlStockManagement();
                if(oidAlStockManagement != 0){
                    try{
                        alStockManagementOld = PstAlStockManagement.fetchExc(oidAlStockManagement);
                        alStockManagement = PstAlStockManagement.fetchExc(oidAlStockManagement);
                    }catch(Exception exc){
                        System.out.println("Exc when fetxh alStockManagement : " + exc.toString());
                    }
                }
                
                frmAlStockManagement.requestEntityObject(alStockManagement);
                
                if(frmAlStockManagement.errorSize()>0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE ;
                }
                
                // pengecekan antara tanggal perolehan dengan leave period yang terselect
                /*
                long leavePerIdOwn = com.dimata.harisma.entity.masterdata.PstLeavePeriod.getPeriodIdBySelectedDate(alStockManagement.getDtOwningDate());
                if(leavePerIdOwn != alStockManagement.getLeavePeriodeId()) {
                        msgString = resultText[language][RSLT_OWN_DATE_INVALID];
                        return RSLT_OWN_DATE_INVALID;
                }
                */
                
                if(alStockManagement.getOID()==0){
                    try
                    {
                        long oid = pstAlStockManagement.insertExc(this.alStockManagement);                                                
                    }catch(DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    }catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }
                    
                }else{
                    try 
                    {
                        long oid = pstAlStockManagement.updateExc(this.alStockManagement);                        
                    }catch (DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                
                // update qty entitled
                int tahun = alStockManagement.getDtOwningDate().getYear();
                pstAlStockManagement.updateQtyEntitled(alStockManagement.getEntitled(),alStockManagement.getEmployeeId(),tahun);                
                
                
                /** 
                 * proses ke Al Stock Taken
                 * algoritma : 
                 *  - proses ini dilakukan hanya jika jumlah AL Used adalah lebih dari nol
                 *  - jika AL Quantity kurang dari Used Quantity (QTY < USED) 
                 *      => list AL Stock Taken diurutkan berdasarkan "taken_date"       
                 *      => utk masing-masing record AL Stock taken akan mewakili 1 
                 *         jumlah USED (AL Stock Management) sehingga ALStockId adalah OID dari 
                 *         "object AL Stock Management" ini 
                 *      => utk record AL Stock Taken yang tidak "kebagian", 
                 *         maka ALStockId diset menjadi nol artinya ngutang AL   
                 * edited by Edhy               
                 */                    
                if( alStockManagement.getQtyUsed() > 0 )
                {
                    if( alStockManagement.getAlQty() < alStockManagement.getQtyUsed() )
                    {
                        String whereClause = PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_AL_STOCK_ID] +
                                             " = " + alStockManagement.getOID() + 
                                             " AND " + PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID] +
                                             " = " + alStockManagement.getEmployeeId();
                        String orderByCls  = PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE];
                        Vector vectAlTaken = PstAlStockTaken.list(0, 0, whereClause, orderByCls);
                        if(vectAlTaken!=null && vectAlTaken.size()>0)
                        {
                            int maxAlTaken = vectAlTaken.size();
                            float maxAlQty = alStockManagement.getAlQty();
                            for(int i=0; i<maxAlTaken; i++)
                            {
                                AlStockTaken objAlStockTaken = (AlStockTaken) vectAlTaken.get(i);
                                if(i >= maxAlQty)
                                {
                                    objAlStockTaken.setAlStockId(0);
                                    try
                                    {
                                        long oid = PstAlStockTaken.updateExc(objAlStockTaken);
                                    }
                                    catch(Exception e)
                                    {
                                        System.out.println("Exc when update AlStockId to null : " + e.toString());
                                    }
                                }
                            }
                        }
                    }
                }        
                
                /**
                 * start proses pembayaran hutang dengan data AL
                 * algoritma : 
                 *  - proses ini dilakukan hanya jika jumlah status AL adalah aktif
                 *
                 * edited by Edhy      
                 */           
                if( alStockManagement.getAlStatus()==PstAlStockManagement.AL_STS_AKTIF )
                {
                    Vector vectOidLeavePaid2 = PstAlStockManagement.paidAlPayable(alStockManagement.getEmployeeId(), alStockManagement);																																																																																		
                }
                
                break;
                
            case Command.EDIT :
                if (oidAlStockManagement != 0) {
                    try {
                        alStockManagement = PstAlStockManagement.fetchExc(oidAlStockManagement);
                    } catch (DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            case Command.ASK :
                if (oidAlStockManagement != 0) {
                    try{
                        
                        alStockManagement = PstAlStockManagement.fetchExc(oidAlStockManagement);
                    } catch (DBException dbexc){
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc){
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            case Command.DELETE :
                if (oidAlStockManagement != 0){
                    try{
                        long oid = PstAlStockManagement.deleteExc(oidAlStockManagement);
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
