/**
 * User: gadnyana
 * Date: Apr 8, 2004
 * Time: 2:19:32 PM
 * Version: 1.0
 */
package com.dimata.harisma.form.attendance;

import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.lang.I_Language;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;
//import com.dimata.harisma.session.attendance.DPMontly;
import com.dimata.harisma.session.attendance.AnnualLeaveMontly;
import com.dimata.harisma.session.attendance.SessDayOfPayment;
import com.dimata.harisma.session.attendance.SessLongLeave;
//import com.dimata.harisma.utility.service.leavedp.ServiceLLStock;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.session.leave.SessDPUpload;
import com.dimata.system.entity.system.PstSystemProperty;

import javax.servlet.http.HttpServletRequest;
import java.util.Vector;
import java.util.Date;
import java.util.Calendar;
//import java.util.GregorianCalendar;

public class CtrlDpStockManagement extends Control implements I_Language {

    public static int RSLT_OK               = 0;
    public static int RSLT_UNKNOWN_ERROR    = 1;
    public static int RSLT_EST_CODE_EXIST   = 2;
    public static int RSLT_FORM_INCOMPLETE  = 3;
    public static int RSLT_FORM_EXIST       = 4;


    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap","Data Aq date sudah ada"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete","Data aq date exist"}
    };

    private int start;
    private String msgString;
    private DpStockManagement dpStockManagement;
    private PstDpStockManagement pstDpStockManagement;
    private FrmDpStockManagement frmDpStockManagement;
    int language = LANGUAGE_DEFAULT;

    public CtrlDpStockManagement(HttpServletRequest request) {
        msgString = "";
        dpStockManagement = new DpStockManagement();
        try {
            pstDpStockManagement = new PstDpStockManagement(0);
        } catch (Exception e) {
            ;
        }
        frmDpStockManagement = new FrmDpStockManagement(request, dpStockManagement);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmDpStockManagement.addError(0, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public DpStockManagement getDpStockManagement() {
        return dpStockManagement;
    }

    public FrmDpStockManagement getForm() {
        return frmDpStockManagement;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidDpStockManagement) {
        
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        
        I_Leave leaveConfig = null;

        try {
            leaveConfig = (I_Leave) (Class.forName(com.dimata.system.entity.PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception LEAVE_CONFIG: " + e.getMessage());
        }
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:                
                if (oidDpStockManagement != 0) 
                {
                    try 
                    {                        
                        dpStockManagement = PstDpStockManagement.fetchExc(oidDpStockManagement);
                    }
                    catch (Exception exc) 
                    {
                        System.out.println("EXCEPTION "+exc.toString());
                    }
                }

                // validasi data from request form, kalau sisa Dp 0 maka status menjadi TAKEN
                frmDpStockManagement.requestEntityObject(dpStockManagement);  
                
                if((dpStockManagement.getiDpQty() - dpStockManagement.getQtyUsed()) == 0)
                {
                    dpStockManagement.setiDpStatus(PstDpStockManagement.DP_STS_TAKEN);
                }

                if (frmDpStockManagement.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }
                
                long dpStockManagementExist = 0;
                long dpUploadExist = 0;
                
                dpStockManagementExist = SessDPUpload.getOIDOpnameSama(dpStockManagement.getEmployeeId(), dpStockManagement.getDtOwningDate());
                dpUploadExist = SessDPUpload.getOIDOpnameSama_2(dpStockManagement.getEmployeeId(), dpStockManagement.getDtOwningDate());
                
                /*
                if(dpStockManagementExist != 0 || dpUploadExist != 0 ){
                    
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_IN_EXIST);
                    return RSLT_FORM_EXIST;
                    
                }
                */
                
                if(this.dpStockManagement.getiExceptionFlag() == PstDpStockManagement.EXC_STS_NO){   // jika kondisi no exception expired
                    this.dpStockManagement.setDtExpiredDateExc(null);
                }
                
                if (dpStockManagement.getOID() == 0) {
                    try {
                        long oid = pstDpStockManagement.insertExc(this.dpStockManagement);
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
                        long oid = pstDpStockManagement.updateExc(this.dpStockManagement);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);  
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                
                
                /** 
                 * proses ke Dp Stock Taken
                 * algoritma : 
                 *  - proses ini dilakukan hanya jika jumlah DP Used adalah lebih dari nol
                 *  - jika DP Quantity kurang dari Used Quantity (QTY < USED) 
                 *      => list Dp Stock Taken diurutkan berdasarkan "taken_date"       
                 *      => utk masing-masing record Dp Stock taken akan mewakili 1 
                 *         jumlah USED (Dp Stock Management) sehingga DpStockId adalah OID dari 
                 *         "object DP Stock Management" ini 
                 *      => utk record Dp Stock Taken yang tidak "kebagian", 
                 *         maka DpStockId diset menjadi nol artinya ngutang DP   
                 * edited by Edhy               
                 */                    
                
                if( dpStockManagement.getQtyUsed() > 0 )
                {                    
                    System.out.println("----------> dpStockManagement.getQtyUsed() > 0");
                    if( dpStockManagement.getiDpQty() < dpStockManagement.getQtyUsed() )
                    {
                        System.out.println("----------> dpStockManagement.getiDpQty() < dpStockManagement.getQtyUsed()");
                        String whereClause = PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_DP_STOCK_ID] +
                                             " = " + dpStockManagement.getOID()+ 
                                             " AND " + PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_EMPLOYEE_ID]+
                                             " = " + dpStockManagement.getEmployeeId();
                        String orderByCls  = PstDpStockTaken.fieldNames[PstDpStockTaken.FLD_TAKEN_DATE];
                        Vector vectDpTaken = PstDpStockTaken.list(0, 0, whereClause, orderByCls);
                        
                        if(vectDpTaken!=null && vectDpTaken.size()>0)
                        {
                            int maxDpTaken = vectDpTaken.size();
                            float maxDpQty = dpStockManagement.getiDpQty();
                            for(int i=0; i<maxDpTaken; i++)
                            {
                                DpStockTaken objDpStockTaken = (DpStockTaken) vectDpTaken.get(i);
                                if(i >= maxDpQty)
                                {
                                    objDpStockTaken.setDpStockId(0);
                                    try
                                    {
                                        long oid = PstDpStockTaken.updateExc(objDpStockTaken);
                                    }
                                    catch(Exception e)
                                    {
                                        System.out.println("Exc when update DpStockId to null : " + e.toString());
                                    }
                                }
                            }
                        } 
                    }
                }        
                
                /**
                 * start proses pembayaran hutang dengan data DP
                 * algoritma : 
                 *  - proses ini dilakukan hanya jika jumlah status DP adalah aktif
                 *
                 * edited by Edhy      
                 */           
                if( dpStockManagement.getiDpStatus()==PstDpStockManagement.DP_STS_AKTIF && leaveConfig.getDPPaidPayable())
                {
                    //di hidden sementara karena menunggu keputusan bu ayu
                    //20130325
                    Vector vectOidLeavePaid2 = PstDpStockManagement.paidDpPayable(dpStockManagement.getEmployeeId(), dpStockManagement);																																																																																		
                }
                
                break;

            case Command.EDIT:
                if (oidDpStockManagement != 0) {
                    try {
                        dpStockManagement = PstDpStockManagement.fetchExc(oidDpStockManagement);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidDpStockManagement != 0) {
                    try {
                        dpStockManagement = PstDpStockManagement.fetchExc(oidDpStockManagement);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidDpStockManagement != 0) 
                {                    
                    try 
                    {                 
                        long oid = PstDpStockManagement.deleteExc(oidDpStockManagement);
                        if (oid != 0) 
                        {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            excCode = RSLT_OK;
                        }
                        else 
                        {
                            msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                            excCode = RSLT_FORM_INCOMPLETE;
                        }
                    }
                    catch (DBException dbexc) 
                    {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    }
                    catch (Exception exc) 
                    {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                
                break;

            default :

        }
        return rsCode;
    }


    /**
     * @param oid
     * @param newOid
     * @param lLeavePeriodId
     * @param lEmployeeId
     * @created by Gadnyana
     */
    /*
    public void checkScheduleEmp(long oid, long newOid, long lLeavePeriodId, long lEmployeeId) 
    {
        // fetch symbol dan category untuk schedule sebelum di update
        ScheduleSymbol scdSymbol = new ScheduleSymbol();
        try 
        {
            scdSymbol = PstScheduleSymbol.fetchExc(oid);
        } 
        catch (Exception e) 
        {
            System.out.println("Exc when PstScheduleSymbol.fetchExc(oid) on checkScheduleEmp : " + e.toString());
        }

        ScheduleCategory scdCategory = new ScheduleCategory();
        try 
        {
            scdCategory = PstScheduleCategory.fetchExc(scdSymbol.getScheduleCategoryId());
        } 
        catch (Exception e) 
        {
            System.out.println("Exc when PstScheduleCategory.fetchExc(scdSymbol.getScheduleCategoryId()) on checkScheduleEmp : " + e.toString());
        }


        // fetch symbol dan category untuk schedule setelah di update
        ScheduleSymbol scdSymbolAfter = new ScheduleSymbol();
        try 
        {
            scdSymbolAfter = PstScheduleSymbol.fetchExc(newOid);
        } 
        catch (Exception e) 
        {
            System.out.println("Exc when PstScheduleSymbol.fetchExc(newOid) on checkScheduleEmp : " + e.toString());
        }

        ScheduleCategory scdCategoryAfter = new ScheduleCategory();
        try 
        {
            scdCategoryAfter = PstScheduleCategory.fetchExc(scdSymbolAfter.getScheduleCategoryId());
        } 
        catch (Exception e) 
        {
            System.out.println("Exc when PstScheduleCategory.fetchExc(scdSymbolAfter.getScheduleCategoryId()) on checkScheduleEmp : " + e.toString());
        }

        DpStockManagement objDpStockMgn = new DpStockManagement();   

        // jika schedule sebelumnya ABSENCE dan menjadi schedule MASUK setelah diupdate
        if (scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_ABSENCE) 
        {
            System.out.println("........scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_ABSENCE");
            if ((scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_NIGHT_WORKER) || 
                (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_REGULAR) || 
                (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_ACCROSS_DAY) ||                 
                (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_SPLIT_SHIFT)) 
            {
                try 
                {
                    // get dp stock management for update qty dp
                    objDpStockMgn = PstDpStockManagement.getDpStockManagement(lLeavePeriodId, lEmployeeId);
                    objDpStockMgn.setiDpQty(objDpStockMgn.getiDpQty() + PstDpStockManagement.DP_QTY_COUNT);
                    PstDpStockManagement.updateExc(objDpStockMgn);
                } 
                catch (Exception e) 
                {
                    System.out.println("Exc on jika schedule sebelumnya ABSENCE dan menjadi schedule MASUK setelah diupdate : " + e.toString());
                }
            }
        }

        // jika schedule sebelumnya MASUK dan menjadi schedule ABSENCE/OFF setelah diupdate
        else if ((scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_NIGHT_WORKER) || 
                 (scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_REGULAR) ||
                 (scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_ACCROSS_DAY) ||                 
                 (scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_SPLIT_SHIFT)) 
        {
            System.out.println("........scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_NIGHT_WORKER, CATEGORY_REGULAR, CATEGORY_ACCROSS_DAY, CATEGORY_SPLIT_SHIFT");            
            if (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_ABSENCE) 
            {
                try 
                {
                    objDpStockMgn = PstDpStockManagement.getDpStockManagement(lLeavePeriodId, lEmployeeId);
                    objDpStockMgn.setiDpQty(objDpStockMgn.getiDpQty() - PstDpStockManagement.DP_QTY_COUNT);
                    PstDpStockManagement.updateExc(objDpStockMgn);
                } 
                catch (Exception e) 
                {
                    System.out.println("Exc on jika schedule sebelumnya MASUK dan menjadi schedule ABSENCE setelah diupdate : " + e.toString());
                }                
            } 
            
            else if (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_OFF) 
            {
                try 
                {
                    objDpStockMgn = PstDpStockManagement.getDpStockManagement(lLeavePeriodId, lEmployeeId);
                    objDpStockMgn.setiDpQty(objDpStockMgn.getiDpQty() - PstDpStockManagement.DP_QTY_COUNT_FROM_OFF);
                    PstDpStockManagement.updateExc(objDpStockMgn);
                } 
                catch (Exception e) 
                {
                    System.out.println("Exc on jika schedule sebelumnya MASUK dan menjadi schedule OFF setelah diupdate : " + e.toString());
                }
            }
        }

        
        // jika schedule sebelumnya OFF dan menjadi schedule MASUK setelah diupdate
        else if (scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_OFF)   
        {
            System.out.println("........scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_OFF");            
            if ((scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_NIGHT_WORKER) || 
                (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_REGULAR) || 
                (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_SPLIT_SHIFT)) 
            {
                try 
                {
                    objDpStockMgn = PstDpStockManagement.getDpStockManagement(lLeavePeriodId, lEmployeeId);
                    int dp = objDpStockMgn.getiDpQty() - PstDpStockManagement.DP_QTY_COUNT_FROM_OFF;
                    objDpStockMgn.setiDpQty(dp + PstDpStockManagement.DP_QTY_COUNT);
                    PstDpStockManagement.updateExc(objDpStockMgn);
                } 
                catch (Exception e) 
                {
                    System.out.println("Exc on jika schedule sebelumnya OFF dan menjadi schedule MASUK setelah diupdate : " + e.toString());
                }
            } 
            
            
            // absen
            else 
            { 
                try 
                {
                    objDpStockMgn = PstDpStockManagement.getDpStockManagement(lLeavePeriodId, lEmployeeId);
                    int dp = objDpStockMgn.getiDpQty() - PstDpStockManagement.DP_QTY_COUNT_FROM_OFF;
                    objDpStockMgn.setiDpQty(dp);
                    PstDpStockManagement.updateExc(objDpStockMgn);
                } 
                catch (Exception e) 
                {
                    System.out.println("Exc on jika schedule sebelumnya OFF dan menjadi schedule ABSENCE setelah diupdate : " + e.toString());
                }
            }
        }
    }
    */

    /**
     * @param dpOwningDate
     * @param oid
     * @param newOid
     * @param lEmployeeId
     * @created by Gedhy
     */
    public void checkScheduleEmp(long currScheduleoid, long updatedScheduleOid, Date dpOwningDate, long lLeavePeriodId, long lEmployeeId) 
    {
//        System.out.println("----------- IN checkScheduleEmp");
        
        // fetch symbol dan category untuk schedule sebelum di update
        ScheduleSymbol scdSymbol = new ScheduleSymbol();
        try 
        {
            scdSymbol = PstScheduleSymbol.fetchExc(currScheduleoid);
        } 
        catch (Exception e) 
        {
            System.out.println("Exc when PstScheduleSymbol.fetchExc(oid) on checkScheduleEmp : " + e.toString());
        }

        ScheduleCategory scdCategory = new ScheduleCategory();
        try 
        {
            scdCategory = PstScheduleCategory.fetchExc(scdSymbol.getScheduleCategoryId());
        } 
        catch (Exception e) 
        {
            System.out.println("Exc when PstScheduleCategory.fetchExc(scdSymbol.getScheduleCategoryId()) on checkScheduleEmp : " + e.toString());
        }


        
        // fetch symbol dan category untuk schedule setelah di update
        ScheduleSymbol scdSymbolAfter = new ScheduleSymbol();
        try 
        {
            scdSymbolAfter = PstScheduleSymbol.fetchExc(updatedScheduleOid);
        } 
        catch (Exception e) 
        {
            System.out.println("Exc when PstScheduleSymbol.fetchExc(newOid) on checkScheduleEmp : " + e.toString());
        }

        ScheduleCategory scdCategoryAfter = new ScheduleCategory();
        try 
        {
            scdCategoryAfter = PstScheduleCategory.fetchExc(scdSymbolAfter.getScheduleCategoryId());
        } 
        catch (Exception e)     
        {
            System.out.println("Exc when PstScheduleCategory.fetchExc(scdSymbolAfter.getScheduleCategoryId()) on checkScheduleEmp : " + e.toString());
        }

        

        // jika schedule sebelumnya MASUK (dapat DP) dan menjadi schedule ABSENCE/OFF setelah diupdate        
        if ((scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_NIGHT_WORKER) || 
            (scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_REGULAR) ||
            (scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_ACCROSS_DAY) ||                 
            (scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_SPLIT_SHIFT)) 
        {
            System.out.println("........scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_NIGHT_WORKER, CATEGORY_REGULAR, CATEGORY_ACCROSS_DAY, CATEGORY_SPLIT_SHIFT");            
            // maka delete current DP for specified employee                    
            if (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_ABSENCE)                
            {            
                try 
                {                    
                    DpStockManagement objDpStockMgn = PstDpStockManagement.getDpStockManagement(dpOwningDate, lEmployeeId);                    
                    PstDpStockManagement pstDpStockManagement = new PstDpStockManagement();
                    pstDpStockManagement.deleteExc(objDpStockMgn);
                    System.out.println("Delete DP ...");
//                    System.out.println("..............::::::::::::::: start MASUK, end ABSENCE : pstDpStockManagement.deleteExc(objDpStockMgn)");
                } 
                catch (Exception e) 
                {
                    System.out.println("Exc on jika schedule sebelumnya MASUK dan menjadi schedule ABSENCE setelah diupdate : " + e.toString());
                }
            }
            
            // maka update current DP for specified employee to (current_DP - 1)
            if (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_OFF)                
            {            
                try   
                {                    
                    DpStockManagement objDpStockMgn = PstDpStockManagement.getDpStockManagement(dpOwningDate, lEmployeeId);                    
                    objDpStockMgn.setiDpQty(PstDpStockManagement.DP_QTY_COUNT_FROM_OFF);
                    objDpStockMgn.setQtyResidue(objDpStockMgn.getiDpQty() - objDpStockMgn.getQtyUsed());
                    PstDpStockManagement pstDpStockManagement = new PstDpStockManagement();
                    pstDpStockManagement.updateExc(objDpStockMgn);
                    System.out.println("Update DP ...");
//                    System.out.println("..............::::::::::::::: start MASUK, end OFF : pstDpStockManagement.updateExc(objDpStockMgn)");
                } 
                catch (Exception e) 
                {   
                    System.out.println("Exc on jika schedule sebelumnya MASUK dan menjadi schedule OFF setelah diupdate : " + e.toString());
                }
            }            
        }

        
        
        // jika schedule sebelumnya ABSENCE dan berubah menjadi schedule MASUK (masuk pada saat PH) setelah diupdate        
        // maka akan ditambahkan/insert 2 buah DP
        if (scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_ABSENCE)          
        {            
            System.out.println("........scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_ABSENCE");                        
            if ((scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_NIGHT_WORKER) || 
                (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_REGULAR) || 
                (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_ACCROSS_DAY) ||                 
                (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_SPLIT_SHIFT)) 
            {
                try 
                {                    
                    DpStockManagement objDpStockMgn = getDpStockManagement(dpOwningDate, lLeavePeriodId, lEmployeeId);        
                    PstDpStockManagement pstDpStockManagement = new PstDpStockManagement();
                    pstDpStockManagement.insertExc(objDpStockMgn);
                    System.out.println("Insert 2 DP ...");
//                    System.out.println("..............::::::::::::::: start ABSENCE, end MASUK : pstDpStockManagement.insertExc(objDpStockMgn)");
                } 
                catch (Exception e) 
                {  
                    System.out.println("Exc on jika schedule sebelumnya ABSENCE dan menjadi schedule MASUK setelah diupdate : " + e.toString());
                }
            }                       

            // maka insert new DP for specified employee to (1 DP)
            if (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_OFF)                
            {            
                try 
                {                    
                    DpStockManagement objDpStockMgn = getDpStockManagementOff(dpOwningDate, lLeavePeriodId, lEmployeeId);   
                    PstDpStockManagement pstDpStockManagement = new PstDpStockManagement();
                    pstDpStockManagement.insertExc(objDpStockMgn);
                    System.out.println("Insert 1 DP ...");
//                    System.out.println("..............::::::::::::::: start ABSENCE, end OFF : pstDpStockManagement.insertExc(objDpStockMgn)");
                } 
                catch (Exception e) 
                {
                    System.out.println("Exc on jika schedule sebelumnya ABSENCE dan menjadi schedule MASUK setelah diupdate : " + e.toString());
                }
            }            
        }

        
        
        // jika schedule sebelumnya OFF dan berubah menjadi schedule MASUK (masuk pada saat PH) setelah diupdate        
        if (scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_OFF)            
        {
            System.out.println("........scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_OFF");                                    
            // maka update current DP for specified employee to (current_DP + 1)
            if ((scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_NIGHT_WORKER) || 
                (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_REGULAR) || 
                (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_ACCROSS_DAY) ||                 
                (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_SPLIT_SHIFT)) 
            {
                try 
                {                    
                    DpStockManagement objDpStockMgn = PstDpStockManagement.getDpStockManagement(dpOwningDate, lEmployeeId);                    
                    objDpStockMgn.setiDpQty(PstDpStockManagement.DP_QTY_COUNT);
                    objDpStockMgn.setQtyResidue(objDpStockMgn.getiDpQty() - objDpStockMgn.getQtyUsed());
                    PstDpStockManagement pstDpStockManagement = new PstDpStockManagement();
                    pstDpStockManagement.updateExc(objDpStockMgn);                    
//                    System.out.println("..............::::::::::::::: start OFF, end MASUK : pstDpStockManagement.updateExc(objDpStockMgn)");
                } 
                catch (Exception e) 
                {
                    System.out.println("Exc on jika schedule sebelumnya OFF dan menjadi schedule MASUK setelah diupdate : " + e.toString());
                }
            }
            
            // maka hapus perolehan Dp untuk tanggal ini
            if (scdCategoryAfter.getCategoryType() == PstScheduleCategory.CATEGORY_ABSENCE)                
            {            
                try 
                {                    
                    DpStockManagement objDpStockMgn = PstDpStockManagement.getDpStockManagement(dpOwningDate, lEmployeeId);                    
                    PstDpStockManagement pstDpStockManagement = new PstDpStockManagement();
                    pstDpStockManagement.deleteExc(objDpStockMgn);
//                    System.out.println("..............::::::::::::::: start OFF, end ABSENCE : pstDpStockManagement.deleteExc(objDpStockMgn)");
                } 
                catch (Exception e) 
                {
                    System.out.println("Exc on jika schedule sebelumnya OFF dan menjadi schedule ABSENCE setelah diupdate : " + e.toString());
                }    
            }
        }
    }
   
    
    /**
     * digunakan untuk ngecek apakah kategori schedule adalah masuk (regular, night shift atau split shift)
     * @param oid
     * @return "true" jika schedule masuk, "false" jika schedule libur
     * @created by Gadnyana
     */
    public boolean checkScheduleEmp(long oid) 
    {
        if(oid != 0)
        {
            // fetch symbol dan category untuk schedule sebelum di update
            ScheduleSymbol scdSymbol = new ScheduleSymbol();   
            try 
            {
                scdSymbol = PstScheduleSymbol.fetchExc(oid);
            }
            catch (Exception e) 
            {
                System.out.println("Exc when PstScheduleSymbol.fetchExc(oid) on checkScheduleEmp : " + e.toString());
            }

            ScheduleCategory scdCategory = new ScheduleCategory();
            try 
            {
                scdCategory = PstScheduleCategory.fetchExc(scdSymbol.getScheduleCategoryId());
            }
            catch (Exception e) 
            {
                System.out.println("Exc when PstScheduleCategory.fetchExc(scdSymbol.getScheduleCategoryId()) on checkScheduleEmp : " + e.toString());
            }

            // ngecek apakah kategori schedule adalah masuk (regular, night shift atau split shift, accross day)
            if (scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_REGULAR ||
                scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_NIGHT_WORKER ||
                scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_ACCROSS_DAY ||            
                scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_SPLIT_SHIFT) 
            {                
                return true;
            }
        }    

        return false;
    }


    /**
     * digunakan untuk ngecek apakah kategori schedule adalah Off (weekly off / extra off)
     * @param oid
     * @return "true" jika schedule of, "false" jika schedule lainnya
     * @created by Gadnyana
     */
    public boolean checkScheduleEmpOff(long oid) 
    {
        if(oid != 0)
        {
            // fetch symbol dan category untuk schedule sebelum di update
            ScheduleSymbol scdSymbol = new ScheduleSymbol();   
            try 
            {
                scdSymbol = PstScheduleSymbol.fetchExc(oid);
            }
            catch (Exception e) 
            {
                System.out.println("Exc when PstScheduleSymbol.fetchExc(oid) on checkScheduleEmp : " + e.toString());
            }

            ScheduleCategory scdCategory = new ScheduleCategory();
            try 
            {
                scdCategory = PstScheduleCategory.fetchExc(scdSymbol.getScheduleCategoryId());
            }
            catch (Exception e) 
            {
                System.out.println("Exc when PstScheduleCategory.fetchExc(scdSymbol.getScheduleCategoryId()) on checkScheduleEmp : " + e.toString());
            }

            // ngecek apakah kategori schedule adalah off
            if (scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_OFF) 
            {                
                return true;
            }
        }    

        return false;
    }
    
    
    /**
     * digunakan untuk ngecek apakah kategori schedule adalah Off (weekly off / extra off)
     * @param oid
     * @return "true" jika schedule of, "false" jika schedule lainnya
     * @created by Gadnyana
     */
    public boolean checkScheduleEmpSO(long oid) 
    {
        if(oid != 0)
        {
            // fetch symbol dan category untuk schedule sebelum di update
            ScheduleSymbol scdSymbol = new ScheduleSymbol();   
            try 
            {
                scdSymbol = PstScheduleSymbol.fetchExc(oid);
            }
            catch (Exception e) 
            {
                System.out.println("Exc when PstScheduleSymbol.fetchExc(oid) on checkScheduleEmp : " + e.toString());
            }

            ScheduleCategory scdCategory = new ScheduleCategory();
            try 
            {
                scdCategory = PstScheduleCategory.fetchExc(scdSymbol.getScheduleCategoryId());
            }
            catch (Exception e) 
            {
                System.out.println("Exc when PstScheduleCategory.fetchExc(scdSymbol.getScheduleCategoryId()) on checkScheduleEmp : " + e.toString());
            }

            // ngecek apakah kategori schedule adalah off
            if (scdCategory.getCategoryType() == PstScheduleCategory.CATEGORY_SUPPOSED_TO_BE_OFF) 
            {                
                return true;
            }
        }    

        return false;
    }

    
    /**
     * generate data DP setiap ada public holiday
     * @param empSchedule
     * @param empAfterSchedule
     * @param lEmployeeId     
     */
    public void generateDpStock(EmpSchedule empSchedule, EmpSchedule empAfterSchedule, long oidPeriod, long lEmployeeId) 
    {
        // get period and public holidays
        Vector vectHolidays = new Vector(1, 1);   
        try 
        {
            Period period = PstPeriod.fetchExc(oidPeriod);
            String where = PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE] +
                    " BETWEEN '" + Formater.formatDate(period.getStartDate(), "yyyy-MM-dd") +
                    "' AND '" + Formater.formatDate(period.getEndDate(), "yyyy-MM-dd") + "'";

            vectHolidays = PstPublicHolidays.list(0, 0, where, "");
        } 
        catch (Exception e) 
        {
            System.out.println("Exc when get Public Holiday : " + e.toString());   
        }

        LeavePeriod leavePeriod;
        
        // update empSchedule
        if (empSchedule.getOID() != 0) 
        {

            if (vectHolidays != null && vectHolidays.size() > 0) 
            {
                for (int k = 0; k < vectHolidays.size(); k++) 
                {
                    PublicHolidays pbHolidays = (PublicHolidays) vectHolidays.get(k);
                    Date phDate = pbHolidays.getDtHolidayDate(); 
                    
                    switch (pbHolidays.getDtHolidayDate().getDate()) 
                    {
                        // algoritma
                        // 1. ada dua schedule yg akan dibandingkan yaitu "empSchedule" ==> schedule current, "empAfterSchedule" ==> schedule update
                        // 2. check apakah schedule update adalah kategori "masuk" atau "libur", kalau schedule "masuk(masuk pada saat PH)", maka
                        //    2.1 bandingkan schedule current dengan update, jika tidak sama (ada perbedaan/update) maka :
                        //        2.1.1 jika tanggal PH adalah hari ini atau hari mendatang, maka :
                        //              2.1.1.1 update perolehan DP
                        case 1:
                                if (!String.valueOf(empSchedule.getD1()).equals(String.valueOf(empAfterSchedule.getD1()))) 
                                {    
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD1(), empAfterSchedule.getD1(), phDate, 0, lEmployeeId);  
                                    }
                                }
                                break;

                        case 2:
                                if (!String.valueOf(empSchedule.getD2()).equals(String.valueOf(empAfterSchedule.getD2()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD2(), empAfterSchedule.getD2(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 3:
                                if (!String.valueOf(empSchedule.getD3()).equals(String.valueOf(empAfterSchedule.getD3()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD3(), empAfterSchedule.getD3(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 4:
                                if (!String.valueOf(empSchedule.getD4()).equals(String.valueOf(empAfterSchedule.getD4()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD4(), empAfterSchedule.getD4(), phDate, 0, lEmployeeId);
                                    }
                                } 
                                break;

                        case 5:
                                if (!String.valueOf(empSchedule.getD5()).equals(String.valueOf(empAfterSchedule.getD5()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD5(), empAfterSchedule.getD5(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 6:
                                if (!String.valueOf(empSchedule.getD6()).equals(String.valueOf(empAfterSchedule.getD6()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD6(), empAfterSchedule.getD6(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 7:
                                if (!String.valueOf(empSchedule.getD7()).equals(String.valueOf(empAfterSchedule.getD7()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD7(), empAfterSchedule.getD7(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 8:
                                if (!String.valueOf(empSchedule.getD8()).equals(String.valueOf(empAfterSchedule.getD8()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD8(), empAfterSchedule.getD8(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 9:
                                if (!String.valueOf(empSchedule.getD9()).equals(String.valueOf(empAfterSchedule.getD9()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD9(), empAfterSchedule.getD9(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 10:
                                if (!String.valueOf(empSchedule.getD10()).equals(String.valueOf(empAfterSchedule.getD10()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD10(), empAfterSchedule.getD10(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 11:
                                if (!String.valueOf(empSchedule.getD11()).equals(String.valueOf(empAfterSchedule.getD11()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD11(), empAfterSchedule.getD11(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 12:
                                if (!String.valueOf(empSchedule.getD12()).equals(String.valueOf(empAfterSchedule.getD12()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD12(), empAfterSchedule.getD12(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 13:
                                if (!String.valueOf(empSchedule.getD11()).equals(String.valueOf(empAfterSchedule.getD13()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD13(), empAfterSchedule.getD13(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 14:
                                if (!String.valueOf(empSchedule.getD14()).equals(String.valueOf(empAfterSchedule.getD14()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD14(), empAfterSchedule.getD14(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 15:
                                if (!String.valueOf(empSchedule.getD15()).equals(String.valueOf(empAfterSchedule.getD15()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD15(), empAfterSchedule.getD15(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 16:
                                if (!String.valueOf(empSchedule.getD16()).equals(String.valueOf(empAfterSchedule.getD16()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD16(), empAfterSchedule.getD16(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 17:
                                if (!String.valueOf(empSchedule.getD17()).equals(String.valueOf(empAfterSchedule.getD17()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD17(), empAfterSchedule.getD17(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 18:
                                if (!String.valueOf(empSchedule.getD18()).equals(String.valueOf(empAfterSchedule.getD18()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD18(), empAfterSchedule.getD18(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 19:
                                if (!String.valueOf(empSchedule.getD19()).equals(String.valueOf(empAfterSchedule.getD19()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD19(), empAfterSchedule.getD19(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 20:
                                if (!String.valueOf(empSchedule.getD20()).equals(String.valueOf(empAfterSchedule.getD20()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD20(), empAfterSchedule.getD20(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 21:
                                if (!String.valueOf(empSchedule.getD21()).equals(String.valueOf(empAfterSchedule.getD21()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD21(), empAfterSchedule.getD21(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 22:
                                if (!String.valueOf(empSchedule.getD22()).equals(String.valueOf(empAfterSchedule.getD22()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD22(), empAfterSchedule.getD22(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 23:
                                if (!String.valueOf(empSchedule.getD23()).equals(String.valueOf(empAfterSchedule.getD23()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD23(), empAfterSchedule.getD23(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 24:
                                if (!String.valueOf(empSchedule.getD24()).equals(String.valueOf(empAfterSchedule.getD24()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD24(), empAfterSchedule.getD24(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 25:
                                if (!String.valueOf(empSchedule.getD25()).equals(String.valueOf(empAfterSchedule.getD25()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD25(), empAfterSchedule.getD25(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 26:
                                if (!String.valueOf(empSchedule.getD26()).equals(String.valueOf(empAfterSchedule.getD26()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD26(), empAfterSchedule.getD26(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 27:
                                if (!String.valueOf(empSchedule.getD27()).equals(String.valueOf(empAfterSchedule.getD27()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD27(), empAfterSchedule.getD27(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 28:
                                if (!String.valueOf(empSchedule.getD28()).equals(String.valueOf(empAfterSchedule.getD28()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {                                        
                                        checkScheduleEmp(empSchedule.getD28(), empAfterSchedule.getD28(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 29:
                                if (!String.valueOf(empSchedule.getD29()).equals(String.valueOf(empAfterSchedule.getD29()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD29(), empAfterSchedule.getD29(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 30:
                                if (!String.valueOf(empSchedule.getD30()).equals(String.valueOf(empAfterSchedule.getD30()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD30(), empAfterSchedule.getD30(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;

                        case 31:
                                if (!String.valueOf(empSchedule.getD31()).equals(String.valueOf(empAfterSchedule.getD31()))) 
                                {
                                    if (pbHolidays.getDtHolidayDate().after(new Date()) || pbHolidays.getDtHolidayDate().equals(new Date())) 
                                    {
                                        checkScheduleEmp(empSchedule.getD31(), empAfterSchedule.getD31(), phDate, 0, lEmployeeId);
                                    }
                                }
                                break;
                    }
                }
            }
        }

        
        // insert empSchedule
        else 
        {

            if (vectHolidays != null && vectHolidays.size() > 0) 
            {
                for (int k = 0; k < vectHolidays.size(); k++) 
                {
                    PublicHolidays pbHolidays = (PublicHolidays) vectHolidays.get(k);
                    Date phDate = pbHolidays.getDtHolidayDate();                    

                    switch (pbHolidays.getDtHolidayDate().getDate()) 
                    {
                        
                        // algoritma
                        // 1. check apakah schedule update adalah kategori "masuk" atau "libur", kalau schedule "masuk(masuk pada saat PH)", maka
                        //    1.1 insert perolehan 2 DP                        
                        // 2. check apakah schedule update adalah kategori "of", kalau schedule "off(off bertepatan dengan PH)", maka
                        //    2.1 insert perolehan 1 DP                                                
                        case 1:
                            if (checkScheduleEmp(empAfterSchedule.getD1())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);                                
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD1())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }    
                            break;

                        case 2:
                            if (checkScheduleEmp(empAfterSchedule.getD2())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD2())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }   
                            break;

                        case 3:
                            if (checkScheduleEmp(empAfterSchedule.getD3())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD3())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 4:
                            if (checkScheduleEmp(empAfterSchedule.getD4())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD4())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 5:
                            if (checkScheduleEmp(empAfterSchedule.getD5())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD5())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 6:
                            if (checkScheduleEmp(empAfterSchedule.getD6())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD6())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 7:
                            if (checkScheduleEmp(empAfterSchedule.getD7())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD7())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 8:
                            if (checkScheduleEmp(empAfterSchedule.getD8())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD8())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 9:
                            if (checkScheduleEmp(empAfterSchedule.getD9())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD9())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 10:
                            if (checkScheduleEmp(empAfterSchedule.getD10())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD10())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 11:
                            if (checkScheduleEmp(empAfterSchedule.getD11())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD11())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 12:
                            if (checkScheduleEmp(empAfterSchedule.getD12())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD12())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 13:
                            if (checkScheduleEmp(empAfterSchedule.getD13())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD13())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 14:
                            if (checkScheduleEmp(empAfterSchedule.getD14())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD14())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 15:
                            if (checkScheduleEmp(empAfterSchedule.getD15())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD15())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 16:
                            if (checkScheduleEmp(empAfterSchedule.getD16())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD16())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 17:
                            if (checkScheduleEmp(empAfterSchedule.getD17())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD17())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 18:
                            if (checkScheduleEmp(empAfterSchedule.getD18())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD18())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 19:
                            if (checkScheduleEmp(empAfterSchedule.getD19())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD19())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 20:
                            if (checkScheduleEmp(empAfterSchedule.getD20())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD20())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 21:
                            if (checkScheduleEmp(empAfterSchedule.getD21())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD21())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 22:
                            if (checkScheduleEmp(empAfterSchedule.getD22())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD22())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 23:
                            if (checkScheduleEmp(empAfterSchedule.getD23())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD23())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 24:
                            if (checkScheduleEmp(empAfterSchedule.getD24())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD24())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 25:
                            if (checkScheduleEmp(empAfterSchedule.getD25())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD25())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 26:
                            if (checkScheduleEmp(empAfterSchedule.getD26())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD26())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 27:
                            if (checkScheduleEmp(empAfterSchedule.getD27())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD27())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 28:
                            if (checkScheduleEmp(empAfterSchedule.getD28())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD28())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 29:
                            if (checkScheduleEmp(empAfterSchedule.getD29())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD29())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 30:
                            if (checkScheduleEmp(empAfterSchedule.getD30())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD30())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 31:
                            if (checkScheduleEmp(empAfterSchedule.getD31())) 
                            {
                                generateDp(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD31())) 
                            {
                                generateDpOff(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;   
                    }
                }
            }
        }
    }

    
    /** 
     * generate data DP setiap ada public holiday
     * @param oidPeriod
     * @param empAfterSchedule
     * @param lEmployeeId
     */
    /*
    public void generateDp(EmpSchedule empAfterSchedule, long oidPeriod, long lEmployeeId) 
    {
        // get period and public holidays
        Vector vectHolidays = new Vector(1, 1);   
        try 
        {
            Period period = PstPeriod.fetchExc(oidPeriod);
            String where = PstPublicHolidays.fieldNames[PstPublicHolidays.FLD_HOLIDAY_DATE] +
                    " BETWEEN '" + Formater.formatDate(period.getStartDate(), "yyyy-MM-dd") +
                    "' AND '" + Formater.formatDate(period.getEndDate(), "yyyy-MM-dd") + "'";
            vectHolidays = PstPublicHolidays.list(0, 0, where, "");
        } 
        catch (Exception e) 
        {
            System.out.println("Exc when get Public Holiday : " + e.toString());
        }
        
        // insert/update DP's owning based on empSchedule
        if(empAfterSchedule.getOID() != 0) 
        {

            if (vectHolidays != null && vectHolidays.size() > 0) 
            {
                for (int k = 0; k < vectHolidays.size(); k++) 
                {
                    PublicHolidays pbHolidays = (PublicHolidays) vectHolidays.get(k);
                    Date phDate = pbHolidays.getDtHolidayDate();                   

                    switch (pbHolidays.getDtHolidayDate().getDate()) 
                    {
                        
                        // algoritma
                        // 1. check apakah schedule update adalah kategori "masuk" atau "libur", kalau schedule "masuk(masuk pada saat PH)", maka
                        //    1.1 insert perolehan 2 DP                        
                        // 2. check apakah schedule update adalah kategori "of", kalau schedule "off(off bertepatan dengan PH)", maka
                        //    2.1 insert perolehan 1 DP                                                
                        case 1:
                            if (checkScheduleEmp(empAfterSchedule.getD1())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD1())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }    
                            break;

                        case 2:
                            if (checkScheduleEmp(empAfterSchedule.getD2())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD2())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }   
                            break;

                        case 3:
                            if (checkScheduleEmp(empAfterSchedule.getD3())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD3())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 4:
                            if (checkScheduleEmp(empAfterSchedule.getD4())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD4())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 5:
                            if (checkScheduleEmp(empAfterSchedule.getD5())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD5())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 6:
                            if (checkScheduleEmp(empAfterSchedule.getD6())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD6())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 7:
                            if (checkScheduleEmp(empAfterSchedule.getD7())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD7())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 8:
                            if (checkScheduleEmp(empAfterSchedule.getD8())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD8())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 9:
                            if (checkScheduleEmp(empAfterSchedule.getD9())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD9())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 10:
                            if (checkScheduleEmp(empAfterSchedule.getD10())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD10())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 11:
                            if (checkScheduleEmp(empAfterSchedule.getD11())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD11())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 12:
                            if (checkScheduleEmp(empAfterSchedule.getD12())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD12())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 13:
                            if (checkScheduleEmp(empAfterSchedule.getD13())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD13())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 14:
                            if (checkScheduleEmp(empAfterSchedule.getD14())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD14())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 15:
                            if (checkScheduleEmp(empAfterSchedule.getD15())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD15())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 16:
                            if (checkScheduleEmp(empAfterSchedule.getD16())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD16())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 17:
                            if (checkScheduleEmp(empAfterSchedule.getD17())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD17())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 18:
                            if (checkScheduleEmp(empAfterSchedule.getD18())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD18())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 19:
                            if (checkScheduleEmp(empAfterSchedule.getD19())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD19())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 20:
                            if (checkScheduleEmp(empAfterSchedule.getD20())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD20())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 21:
                            if (checkScheduleEmp(empAfterSchedule.getD21())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD21())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 22:
                            if (checkScheduleEmp(empAfterSchedule.getD22())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD22())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 23:
                            if (checkScheduleEmp(empAfterSchedule.getD23())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD23())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 24:
                            if (checkScheduleEmp(empAfterSchedule.getD24())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD24())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 25:
                            if (checkScheduleEmp(empAfterSchedule.getD25())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD25())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 26:
                            if (checkScheduleEmp(empAfterSchedule.getD26())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD26())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 27:
                            if (checkScheduleEmp(empAfterSchedule.getD27())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD27())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 28:
                            if (checkScheduleEmp(empAfterSchedule.getD28())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD28())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 29:
                            if (checkScheduleEmp(empAfterSchedule.getD29())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD29())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 30:
                            if (checkScheduleEmp(empAfterSchedule.getD30())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD30())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;

                        case 31:
                            if (checkScheduleEmp(empAfterSchedule.getD31())) 
                            {
                                generateDpManual(pbHolidays, 0, lEmployeeId);
                            }
                            
                            if (checkScheduleEmpOff(empAfterSchedule.getD31())) 
                            {
                                generateDpOffManual(pbHolidays, 0, lEmployeeId);                                
                            }
                            break;     
                    }
                }
            }
        }
    }    
    */
    

    /** 
     * gadnyana
     * cek jumlah al/ll yang di gunakan dalam schedule
     * dan di sesuaikan dengan jumlah al/ll yang aktif
     * jika kurang di tambah dengan qty -1
     * @param empAfterSchedule
     * @param oidPeriod  
     *
     * @edited by Edhy
     * algoritma : 
     *  1. ambil data "list schedule tipe AL dan LL", "data AL dan LL per employee" masing-masing dari master SCHEDULE dan AL_STOCK_MANAGEMENT dan LL_STOCK_MANAGEMENT    
     *  2. check apakah di masterdata sudah ada schedule AL dan/atau LL
     *  3. jika ada schedule AL dan/atau LL, iterasi sebanyak jumlah schedule tersebut untuk proses berikutnya
     *  4. hitung total schedule AL/LL yang diambil dalam schedule ini
     *  5. check apakah dalam "schedule ini" ada pemakaian AL dan/atau LL ditandai dengan "qty>1"
     *  6. check tipe/kategori schedule yang sedang diproses (AL atau LL)
     *     6.1 jika kategori adalah AL, maka : 
     *         6.1.1 pengecekan apakah ada employee dengan schedule ini memiliki stock AL (all)
     *               6.1.1.1 jika ada, maka check perbandingan antara AL aktif dengan AL terpakai (sesuai schedule ini)
     *                       jika AL aktif < AL terpakai : lakukan proses insert AL lebih sebanyak "(AL terpakai - AL aktif)"
     *
     *               6.1.1.2 jika tdk, lakukan proses insert AL lebih sebanyak "AL terpakai"
     *
     *     6.2 jika kategori adalah LL, maka : 
     *         6.2.1 pengecekan apakah ada employee dengan schedule ini memiliki stock LL (all)
     *               6.2.1.1 jika ada, pengecekan perbandingan antara LL aktif dengan LL terpakai (sesuai schedule ini) 
     *                       jika LL aktif < LL terpakai : lakukan proses insert AL lebih sebanyak "(AL terpakai - AL aktif)"
     *
     *               6.2.1.2 jika tdk, lakukan proses insert LL lebih sebanyak "LL terpakai"
     *
     */
    public static void cekAL_LL(EmpSchedule empAfterSchedule, long oidPeriod)    
    {        
        // 1. mengambil beberapa data yang diperlukan : 
        //    a. schedule kategori AL dan LL 
        //    b. daftar sisa AL per employee yang memiliki schedule ini
        //    c. daftar sisa LL per employee yang memiliki schedule ini        
        Vector list = PstScheduleSymbol.getScheduleDPALLL(); 
        Vector listAl = AnnualLeaveMontly.prosessGetAl(oidPeriod, empAfterSchedule.getEmployeeId()); 
        Vector listll = SessLongLeave.prosessGetLongLeave(oidPeriod, empAfterSchedule.getEmployeeId()); 

        // deklarasi jumlah AL dan LL yang sudah terpakai
        int qtyUsedAL = 0;
        int qtyUsedLL = 0;
        
        // 2. pengecekan apakah di masterdata sudah ada schedule AL dan/atau LL
        if (list != null && list.size() > 0) 
        {
            
            // 3. jika ada schedule AL dan/atau LL, 
            //    iterasi sebanyak jumlah schedule tersebut
            int maxListSchedule = list.size();
            for (int k=0; k<maxListSchedule; k++) 
            {
                Vector vect = (Vector) list.get(k);                
                ScheduleSymbol scheduleSymbol = (ScheduleSymbol) vect.get(0);
                ScheduleCategory scheduleCategory = (ScheduleCategory) vect.get(1);                
                long oid = scheduleSymbol.getOID();                
                int qty = 0;
                
                // 4. melakukan perhitungan total schedule AL/LL yang diambil dalam schedule ini
                if (empAfterSchedule.getD1() == oid)
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD2() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD3() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD4() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD5() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD6() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD7() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD8() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD9() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD10() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD11() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD12() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD13() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD14() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD15() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD16() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD17() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD18() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD19() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD20() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD21() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD22() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD23() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD24() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD25() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD26() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD27() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD28() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD29() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD30() == oid) 
                {
                    qty = qty + 1;
                }
                
                if (empAfterSchedule.getD31() == oid) 
                {
                    qty = qty + 1;
                }

                
                // 5. melakukan pengecekan apakah dalam "schedule ini" ada pemakaian AL dan/atau LL ditandai dengan "qty>1"
                if(qty > 0)
                {
//                    System.out.println("qty AL dan/atau LL > 0");
                    
                    // 6. pengecekan tipe/kategori schedule yang sedang diproses (AL atau LL)
                    // 6.1 jika kategori adalah AL
                    if (scheduleCategory.getCategoryType() == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE) 
                    {
//                        System.out.println("scheduleCategory.getCategoryType() == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE");

                        // set jumlah AL yang terpakai dengan hasil perhitungan "qty" di atas
                        qtyUsedAL = qty;
                            
                        // 6.1.1 pengecekan apakah ada employee dengan schedule ini memiliki stock AL (all)
                        // stock AL ada
                        if (listAl != null && listAl.size() > 0) 
                        {
//                            System.out.println("listAl != null && listAl.size() > 0");                            
                            
                            Vector vt = (Vector) listAl.get(0);
                            int qtyALAktif = Integer.parseInt((String) vt.get(1));                            

                            // 6.1.1.1 pengecekan perbandingan antara AL aktif dengan AL terpakai (sesuai schedule ini)
                            // jika jumlah pengambilan AL melebihi stock AL
                            if (qtyALAktif < qtyUsedAL) 
                            {
//                                System.out.println("qtyALAktif < qtyUsedAL");

                                // mengambil owning date maksimum dari data AL stock
                                Date maxOwnDtAL = PstAlStockManagement.getMaxALManagement(empAfterSchedule.getEmployeeId());

                                // insert new leave period jika leave period yang based on owning date
                                // dari AL yang bakalan didpt bulan yang kana datang (leave period qty minus)
                                int intLvWillInsert = qtyUsedAL - qtyALAktif;                                                                     
                                Date startOwnDtAL = new Date(maxOwnDtAL.getYear(), (maxOwnDtAL.getMonth()+1), 1);
                                Date endOwnDtAL = new Date(maxOwnDtAL.getYear(), (maxOwnDtAL.getMonth() + intLvWillInsert), maxOwnDtAL.getDate());
                                PstLeavePeriod.generateLeavePeriodObj(startOwnDtAL, endOwnDtAL);                                                                                                                           
                                
                                // iterasi insert new AL ke stock AL
                                for (int jj=0; jj<intLvWillInsert; jj++)   
                                {                                                
                                    Date selectedOwnDtAL = new Date(startOwnDtAL.getYear(), (startOwnDtAL.getMonth() + jj), startOwnDtAL.getDate());                                                
                                    LeavePeriod leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(selectedOwnDtAL);

                                    AlStockManagement alStockManagement = new AlStockManagement();
                                    alStockManagement.setAlQty(0);
                                    alStockManagement.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);
                                    alStockManagement.setDtOwningDate(leavePeriod.getStartDate());
                                    alStockManagement.setEmployeeId(empAfterSchedule.getEmployeeId());
                                    alStockManagement.setQtyUsed(1);
                                    alStockManagement.setQtyResidue(alStockManagement.getAlQty() - alStockManagement.getQtyUsed());
                                    alStockManagement.setLeavePeriodeId(leavePeriod.getOID());
                                    try 
                                    {
                                        PstAlStockManagement.insertExc(alStockManagement);
                                    }
                                    catch (Exception e) 
                                    {
                                        System.out.println("Exc when PstAlStockManagement.insertExc(alStockManagement) AL Stock Not Null : " + e.toString());
                                    }                                                
                                }
                            }                                                        
                        } 

                        
                        // 6.1.2. pengecekan apakah ada AL (semua) yang dimiliki employee dengan schedule ini
                        // stock AL tidak ada
                        else 
                        {                      
//                            System.out.println("listAl == null || listAl.size() == 0");                            
                            
                            Period schedulePeriod = new Period();
                            Date maxOwnDtAL = new Date();
                            try
                            {
                                schedulePeriod = PstPeriod.fetchExc(empAfterSchedule.getPeriodId());
                                maxOwnDtAL = schedulePeriod.getEndDate();
                            }
                            catch(Exception e)
                            {
                                System.out.println("Exc when PstPeriod.fetchExc(empAfterSchedule.getPeriodId()) : " + e.toString());
                            }                                                       
                            
                            // insert new leave period jika leave period yang based on owning date
                            // dari AL yang bakalan didpt bulan yang kana datang (leave period qty minus)
                            int intLvWillInsert = qtyUsedAL;                                                                     
                            Date startOwnDtAL = new Date(maxOwnDtAL.getYear(), (maxOwnDtAL.getMonth()+1), 1);
                            Date endOwnDtAL = new Date(maxOwnDtAL.getYear(), (maxOwnDtAL.getMonth() + intLvWillInsert), maxOwnDtAL.getDate());
                            PstLeavePeriod.generateLeavePeriodObj(startOwnDtAL, endOwnDtAL);                                                                                                                                                       

                            for (int jj=0; jj<qtyUsedAL; jj++) 
                            {
                                Date selectedOwnDtAL = new Date(startOwnDtAL.getYear(), (startOwnDtAL.getMonth() + jj), startOwnDtAL.getDate());                                                
                                LeavePeriod leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(selectedOwnDtAL);
                                
                                AlStockManagement alStockManagement = new AlStockManagement();
                                alStockManagement.setAlQty(0);
                                alStockManagement.setAlStatus(PstAlStockManagement.AL_STS_AKTIF);
                                alStockManagement.setDtOwningDate(leavePeriod.getStartDate());
                                alStockManagement.setEmployeeId(empAfterSchedule.getEmployeeId());
                                alStockManagement.setQtyUsed(1);
                                alStockManagement.setQtyResidue(alStockManagement.getAlQty() - alStockManagement.getQtyUsed());
                                alStockManagement.setLeavePeriodeId(leavePeriod.getOID());
                                try 
                                {
                                    PstAlStockManagement.insertExc(alStockManagement);
                                }
                                catch (Exception e) 
                                {
                                    System.out.println("Exc when PstAlStockManagement.insertExc(alStockManagement) AL Stock Null : " + e.toString());
                                }                                                
                            }
                        }
                    } 




                    // 6. pengecekan tipe/kategori schedule yang sedang diproses (AL atau LL)
                    //    6.2 jika kategori adalah LL
                    if (scheduleCategory.getCategoryType() == PstScheduleCategory.CATEGORY_LONG_LEAVE) 
                    {
//                        System.out.println("scheduleCategory.getCategoryType() == PstScheduleCategory.CATEGORY_LONG_LEAVE");

                        // set jumlah LL yang terpakai dengan hasil perhitungan "qty" di atas
                        qtyUsedLL = qty;
                        
                        // 6.2.1 pengecekan apakah ada LL (semua) yang dimiliki employee dengan schedule ini
                        // stock LL ada
                        if (listll != null && listll.size() > 0) 
                        {
//                            System.out.println("listll != null && listll.size() > 0");                                                       

                            Vector vt = (Vector) listll.get(0);
                            int qtyLLAktif = Integer.parseInt((String) vt.get(1));                            
                     
                            // 6.2.1.1 pengecekan perbandingan antara LL aktif dengan LL terpakai (sesuai schedule ini)
                            // jika jumlah pengambilan LL melebihi stock LL
                            if (qtyLLAktif < qtyUsedLL) 
                            {
//                                System.out.println("qtyLLAktif < qtyUsedLL");                     
                                
                                // mencari LL stock terakhir (yang sudah taken)
                                LLStockManagement llStockTemp = PstLLStockManagement.getLlStockPerEmpLast();

                                // mencari owning date yang sesuai                
                                Date ownDateTmp = llStockTemp.getDtOwningDate();
                                Date ownDateNextFiveYear = null;
                                if(ownDateTmp != null)
                                {
                                    ownDateNextFiveYear = new Date(ownDateTmp.getYear()+5, ownDateTmp.getMonth(), ownDateTmp.getDate());
                                }
                                else
                                {
                                    // mencari data employee untuk mendapatkan commencing date-nya
                                    Employee emp = new Employee();
                                    try
                                    {
                                        emp = PstEmployee.fetchExc(empAfterSchedule.getEmployeeId());
                                        Date dtComm = emp.getCommencingDate();
                                        ownDateNextFiveYear = new Date(dtComm.getYear()+5, dtComm.getMonth(), dtComm.getDate());                    
                                    }
                                    catch(Exception e)
                                    {
                                        System.out.println("Exc when fetchExc(employeeId) : " + e.toString());
                                    }                                                            
                                }                                                                                               
                                
                                // mencari leave period yang sesuai
                                LeavePeriod leavePeriod = new LeavePeriod();                
                                if(ownDateTmp != null)
                                {
                                    leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(ownDateTmp);                
                                }
                                else
                                {
                                    leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(new Date());                                    
                                }
                                
                                // instantiate object "LLStockManagement"                                
                                int llCount = PstLLStockManagement.getCountLLBelogsToEmp(empAfterSchedule.getEmployeeId());                                
                                LLStockManagement llStockManagement = new LLStockManagement();
                                
                                llStockManagement.setLLQty(0);
                                llStockManagement.setLLStatus(PstLLStockManagement.LL_STS_AKTIF);
                                llStockManagement.setDtOwningDate(ownDateNextFiveYear);
                                llStockManagement.setEmployeeId(empAfterSchedule.getEmployeeId());
                                llStockManagement.setQtyUsed((qtyUsedLL - qtyLLAktif));
                                llStockManagement.setQtyResidue(llStockManagement.getLLQty() - llStockManagement.getQtyUsed());
                                llStockManagement.setEntitled(llCount+1);
                                llStockManagement.setLeavePeriodeId(leavePeriod.getOID());
                                
                                try 
                                {
                                    PstLLStockManagement.insertExc(llStockManagement);
                                }
                                catch (Exception e) 
                                {
                                    System.out.println("Exc when PstAlStockManagement.insertExc(alStockManagement) LL Stock Not Null : " + e.toString());
                                }                                                                                
                            }
                        }
                        
                        
                        // 6.2.2 pengecekan apakah ada AL (semua) yang dimiliki employee dengan schedule ini
                        // stock LL tidak ada                        
                        else
                        {
                            // mencari data employee untuk mendapatkan commencing date-nya                            
                            Date ownDateNextFiveYear = null;                            
                            Employee emp = new Employee();
                            try
                            {
                                emp = PstEmployee.fetchExc(empAfterSchedule.getEmployeeId());
                                Date dtComm = emp.getCommencingDate();
                                ownDateNextFiveYear = new Date(dtComm.getYear()+5, dtComm.getMonth(), dtComm.getDate());                    
                            }
                            catch(Exception e)
                            {
                                System.out.println("Exc when fetchExc(employeeId) : " + e.toString());
                            }                                                                                                                                                                                    

                            // mencari leave period yang sesuai                             
                            LeavePeriod leavePeriod = PstLeavePeriod.cekAlreadyExistLeavePeriod(new Date());                                                                

                            // instantiate object "LLStockManagement"                                
                            int llCount = PstLLStockManagement.getCountLLBelogsToEmp(empAfterSchedule.getEmployeeId());                                
                            LLStockManagement llStockManagement = new LLStockManagement();

                            llStockManagement.setLLQty(0);
                            llStockManagement.setLLStatus(PstLLStockManagement.LL_STS_AKTIF);
                            llStockManagement.setDtOwningDate(ownDateNextFiveYear);
                            llStockManagement.setEmployeeId(empAfterSchedule.getEmployeeId());
                            llStockManagement.setQtyUsed(qtyUsedLL);
                            llStockManagement.setQtyResidue(llStockManagement.getLLQty() - llStockManagement.getQtyUsed());
                            llStockManagement.setEntitled(llCount+1);
                            llStockManagement.setLeavePeriodeId(leavePeriod.getOID());

                            try 
                            {
                                PstLLStockManagement.insertExc(llStockManagement);
                            }
                            catch (Exception e) 
                            {
                                System.out.println("Exc when PstAlStockManagement.insertExc(alStockManagement) LL Stock Not Null : " + e.toString());
                            }                                                                                                            
                        }
                    }                     
                }                
            }   
        }
    }

    
    /**
     * Untuk men-generate Day of Payment, jika dalam Date schedule ada holiday
     * @param pbHolidays
     * @param lLeavePeriodId  
     * @param lEmployeeId
     */    
    public void generateDp(PublicHolidays pbHolidays, long lLeavePeriodId, long lEmployeeId) 
    {
        if (pbHolidays.getDtHolidayDate() != null) 
        {
            boolean bHolidaySts = false;
            DpStockManagement objDpStockMgn = new DpStockManagement();  
            //String stHinduOid = PstSystemProperty.getValueByName(PstPublicHolidays.HINDU_STR);
            Employee objEmployee = new Employee();
            try 
            {
                objEmployee = PstEmployee.fetchExc(lEmployeeId);
            }
            catch (Exception e) 
            {
                System.out.println("Exc when PstEmployee.fetchExc(lEmployeeId) on generateDp : " + e.toString());
            }

            if (pbHolidays.getiHolidaySts() == PstPublicHolidays.STS_NATIONAL)   
            {
                bHolidaySts = true;
            }
            else 
            {
                
                //if ((objEmployee.getReligionId() + "").trim().equals(stHinduOid.trim())) 
                //{
                    if (pbHolidays.getiHolidaySts() == objEmployee.getReligionId()) //PstPublicHolidays.STS_HINDHU) 
                    {
                        bHolidaySts = true;
                    }
                //}
                else 
                {
                    if (pbHolidays.getiHolidaySts() == PstPublicHolidays.STS_NONE)  // INI BELUM FIX PERLU 
                    {
                        bHolidaySts = false;//true;
                    }
                }
            }

            if (bHolidaySts) 
            {
                try 
                {                    
                    objDpStockMgn = PstDpStockManagement.getDpStockManagement(lLeavePeriodId, lEmployeeId, pbHolidays.getDtHolidayDate());                                        

                    // update DpStockManagement
                    if ( (objDpStockMgn.getOID() != 0) && (objDpStockMgn.getiDpStatus()==PstDpStockManagement.DP_STS_NOT_AKTIF) ) 
                    {                        
//                        objDpStockMgn.setiDpQty(objDpStockMgn.getiDpQty() + PstDpStockManagement.DP_QTY_COUNT);                        
//                        objDpStockMgn.setQtyResidue(objDpStockMgn.getiDpQty() - objDpStockMgn.getQtyUsed());
                        PstDpStockManagement.updateExc(objDpStockMgn);
                    }
                    
                    // insert DpStockManagement
                    else 
                    {                        
                        objDpStockMgn = getDpStockManagement(pbHolidays.getDtHolidayDate(), lLeavePeriodId, lEmployeeId);
                        PstDpStockManagement.insertExc(objDpStockMgn);
                    }

                }
                catch (DBException dbe) 
                {
                    dbe.printStackTrace();
                }
            }
        }
    }    

    /**
     * Untuk men-generate Day of Payment, jika dalam Date schedule ada holiday
     * @param pbHolidays
     * @param lLeavePeriodId  
     * @param lEmployeeId
     */
    /*
    public void generateDpManual(PublicHolidays pbHolidays, long lLeavePeriodId, long lEmployeeId) 
    {
        if (pbHolidays.getDtHolidayDate() != null) 
        {
            boolean bHolidaySts = false;
            DpStockManagement objDpStockMgn = new DpStockManagement();  
            String stHinduOid = PstSystemProperty.getValueByName(PstPublicHolidays.HINDU_STR);
            Employee objEmployee = new Employee();
            try 
            {
                objEmployee = PstEmployee.fetchExc(lEmployeeId);
            }
            catch (Exception e) 
            {
                System.out.println("Exc when PstEmployee.fetchExc(lEmployeeId) on generateDp : " + e.toString());
            }

            if (pbHolidays.getiHolidaySts() == PstPublicHolidays.STS_BOTH)   
            {
                bHolidaySts = true;
            }
            else 
            {
                if ((objEmployee.getReligionId() + "").trim().equals(stHinduOid.trim())) 
                {
                    if (pbHolidays.getiHolidaySts() == PstPublicHolidays.STS_HINDU) 
                    {
                        bHolidaySts = true;
                    }
                }
                else 
                {
                    if (pbHolidays.getiHolidaySts() == PstPublicHolidays.STS_NON_HINDU) 
                    {
                        bHolidaySts = true;
                    }
                }
            }

            if (bHolidaySts) 
            {
                try 
                {                    
                    objDpStockMgn = PstDpStockManagement.getDpStockManagement(lLeavePeriodId, lEmployeeId, pbHolidays.getDtHolidayDate());                    
                    
                    // insert DpStockManagement
                    if (objDpStockMgn.getOID() == 0) 
                    {
                        objDpStockMgn = getDpStockManagement(pbHolidays.getDtHolidayDate(), lLeavePeriodId, lEmployeeId);
                        PstDpStockManagement.insertExc(objDpStockMgn);
                    }
                }
                catch (DBException dbe) 
                {
                    dbe.printStackTrace();
                }
            }
        }
    }
    */
    
    /**
     * Untuk men-generate Day of Payment, jika dalam Date schedule ada holiday (utk schedule OFF bertepatan dengan PH)
     * @param pbHolidays
     * @param lLeavePeriodId  
     * @param lEmployeeId
     * @created by Edhy
     */
    public void generateDpOff(PublicHolidays pbHolidays, long lLeavePeriodId, long lEmployeeId) 
    {
        if (pbHolidays.getDtHolidayDate() != null) 
        {
            boolean bHolidaySts = false;
            DpStockManagement objDpStockMgn = new DpStockManagement();  
            //String stHinduOid = PstSystemProperty.getValueByName(PstPublicHolidays.HINDU_STR);
            Employee objEmployee = new Employee();
            try 
            {
                objEmployee = PstEmployee.fetchExc(lEmployeeId);
            }
            catch (Exception e) 
            {
                System.out.println("Exc when PstEmployee.fetchExc(lEmployeeId) on generateDp : " + e.toString());
            }

            if (pbHolidays.getiHolidaySts() == PstPublicHolidays.STS_NATIONAL)   
            {
                bHolidaySts = true;
            }
            else 
            {
                //if ((objEmployee.getReligionId() + "").trim().equals(stHinduOid.trim())) 
                //{
                    if (pbHolidays.getiHolidaySts() == objEmployee.getReligionId() ) //PstPublicHolidays.STS_HINDHU) 
                    {
                        bHolidaySts = true;
                    }
                //}
                else 
                {
                    if (pbHolidays.getiHolidaySts() == PstPublicHolidays.STS_NONE) 
                    {
                        bHolidaySts = false;//true;
                    }
                }
            }

            if (bHolidaySts) 
            {
                try 
                {                    
                    objDpStockMgn = PstDpStockManagement.getDpStockManagement(lLeavePeriodId, lEmployeeId, pbHolidays.getDtHolidayDate());
                    
                    // update DpStockManagement
                    if (objDpStockMgn.getOID() != 0) 
                    {
                        objDpStockMgn.setiDpQty(objDpStockMgn.getiDpQty() + PstDpStockManagement.DP_QTY_COUNT_FROM_OFF);
                        PstDpStockManagement.updateExc(objDpStockMgn);
                    }
                    
                    // insert DpStockManagement
                    else 
                    {                        
                        objDpStockMgn = getDpStockManagementOff(pbHolidays.getDtHolidayDate(), lLeavePeriodId, lEmployeeId);
                        PstDpStockManagement.insertExc(objDpStockMgn);
                    }
                }
                catch (DBException dbe) 
                {
                    dbe.printStackTrace();
                }
            }
        }
    }
    

    /**
     * Untuk men-generate Day of Payment, jika dalam Date schedule ada holiday (utk schedule OFF bertepatan dengan PH)
     * @param pbHolidays
     * @param lLeavePeriodId  
     * @param lEmployeeId
     * @created by Edhy
     */
    public void generateDpOffManual(PublicHolidays pbHolidays, long lLeavePeriodId, long lEmployeeId) 
    {
        if (pbHolidays.getDtHolidayDate() != null) 
        {
            boolean bHolidaySts = false;
            DpStockManagement objDpStockMgn = new DpStockManagement();  
            //String stHinduOid = PstSystemProperty.getValueByName(PstPublicHolidays.HINDU_STR);
            Employee objEmployee = new Employee();
            try 
            {
                objEmployee = PstEmployee.fetchExc(lEmployeeId);
            }
            catch (Exception e) 
            {
                System.out.println("Exc when PstEmployee.fetchExc(lEmployeeId) on generateDp : " + e.toString());
            }

            if (pbHolidays.getiHolidaySts() == PstPublicHolidays.STS_NONE)   
            {
                bHolidaySts = true;
            }
            else 
            {
              //  if ((objEmployee.getReligionId() + "").trim().equals(stHinduOid.trim())) 
                //{
                    if (pbHolidays.getiHolidaySts() == objEmployee.getReligionId()) //PstPublicHolidays.STS_HINDHU) 
                    {
                        bHolidaySts = true;
                    }
                //}
                else 
                {
                    if (pbHolidays.getiHolidaySts() == PstPublicHolidays.STS_NONE) 
                    {
                        bHolidaySts = false;//true;
                    }
                }
            }

            if (bHolidaySts) 
            {
                try 
                {                    
                    objDpStockMgn = PstDpStockManagement.getDpStockManagement(lLeavePeriodId, lEmployeeId, pbHolidays.getDtHolidayDate());
                    
                    // update DpStockManagement
                    if (objDpStockMgn.getOID() == 0) 
                    {
                        objDpStockMgn = getDpStockManagementOff(pbHolidays.getDtHolidayDate(), lLeavePeriodId, lEmployeeId);
                        PstDpStockManagement.insertExc(objDpStockMgn);
                    }
                }
                catch (DBException dbe) 
                {
                    dbe.printStackTrace();
                }
            }
        }
    }
    

    /**
     * Untuk mengenerate default dari DpStockManagement
     * @param dtScheduleDate
     * @return DpStockManagement
     */
    private DpStockManagement getDpStockManagement(Date dtScheduleDate, long lLeavePeriodId, long lEmployeeId) {
        DpStockManagement objDpStockMgn = new DpStockManagement();        

        Date tmpexpiredDate = new Date(dtScheduleDate.getYear(), dtScheduleDate.getMonth()+PstDpStockManagement.DP_EXPIRED_COUNT, 1); 
        Calendar objCal = Calendar.getInstance();
        objCal.setTime(tmpexpiredDate);
        int maxDay = objCal.getActualMaximum(Calendar.DAY_OF_MONTH);                                                                                               
        Date expiredDate = new Date();
        if(dtScheduleDate.getDate() <= 28)
        {
            expiredDate = new Date(tmpexpiredDate.getYear(), tmpexpiredDate.getMonth(), dtScheduleDate.getDate());
        }
        else
        {
            expiredDate = new Date(tmpexpiredDate.getYear(), tmpexpiredDate.getMonth(), maxDay);
        }

        objDpStockMgn.setLeavePeriodeId(lLeavePeriodId);
        objDpStockMgn.setEmployeeId(lEmployeeId);
        objDpStockMgn.setiDpQty(PstDpStockManagement.DP_QTY_COUNT);
        objDpStockMgn.setQtyUsed(0);
        objDpStockMgn.setQtyResidue(objDpStockMgn.getiDpQty() - objDpStockMgn.getQtyUsed());
        objDpStockMgn.setDtOwningDate(dtScheduleDate);
        objDpStockMgn.setDtExpiredDate(expiredDate);
        objDpStockMgn.setiExceptionFlag(PstDpStockManagement.EXC_STS_NO);
        objDpStockMgn.setDtExpiredDateExc(expiredDate);
        objDpStockMgn.setiDpStatus(PstDpStockManagement.DP_STS_NOT_AKTIF);
        objDpStockMgn.setStNote("DP PH on " + Formater.formatDate(dtScheduleDate,"dd-MM-yyyy"));

        return objDpStockMgn;
    }

    
    /**
     * Memberikan DP untuk employee yang kerja pada hari liburnya
     * Khusus untuk HARD ROCK
     */
    public static int cekDpAvailable(int iScheduleCategory, boolean isPresent, long empOid, Date dCek){
        Employee emp = new Employee();        
        Level level = new Level();
        try {
            emp = PstEmployee.fetchExc(empOid); //employeeId
            level = PstLevel.fetchExc(emp.getLevelId());
        //leaveConfig.
        } catch (DBException ex) {
            ex.printStackTrace();
        }
        
        I_Leave leaveConfig = null;           
        try {
            leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
        }
        catch(Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        
        boolean boolPublicHoliday = false;
        boolean boolBirthDay = false;
        boolPublicHoliday = leaveConfig.isPublicHoliday(emp, dCek);
        boolBirthDay = leaveConfig.isBirthDay(emp, dCek);
        int iDpEntitle = 0;
        if (iScheduleCategory == PstScheduleCategory.CATEGORY_OFF || iScheduleCategory == PstScheduleCategory.CATEGORY_SUPPOSED_TO_BE_OFF) {
            if (isPresent) {
                iDpEntitle += leaveConfig.getDPStandardEntitle(level.getLevel());
                if (boolPublicHoliday) {
                    iDpEntitle += 1;
                }
                if (boolBirthDay) {
                    iDpEntitle += 1;
                }
            } else {
                if (boolPublicHoliday) {
                    iDpEntitle += 1;
                }
                if (boolBirthDay) {
                    iDpEntitle += 1;
                }
            }
        } else {
            if (isPresent) {
                if (boolPublicHoliday) {
                    iDpEntitle += 2;
                }
                if (boolBirthDay) {
                    iDpEntitle += 1;
                }
            } else {
                //Belum didefinisikan
                if (boolPublicHoliday) {
                    iDpEntitle += 2;
                }
                if (boolBirthDay) {
                    iDpEntitle += 1;
                }
            }
        }
        
       return iDpEntitle;
    }
    
    /**
     * Untuk mengenerate default dari DpStockManagement
     * @param dtScheduleDate
     * @return DpStockManagement
     * @created by Edhy
     */
    private DpStockManagement getDpStockManagementOff(Date dtScheduleDate, long lLeavePeriodId, long lEmployeeId) 
    {
        DpStockManagement objDpStockMgn = new DpStockManagement();

        Date tmpexpiredDate = new Date(dtScheduleDate.getYear(), dtScheduleDate.getMonth()+PstDpStockManagement.DP_EXPIRED_COUNT, 1); 
        Calendar objCal = Calendar.getInstance();
        objCal.setTime(tmpexpiredDate);
        int maxDay = objCal.getActualMaximum(Calendar.DAY_OF_MONTH);                                                                                               
        Date expiredDate = new Date();
        if(dtScheduleDate.getDate() <= 28)
        {
            expiredDate = new Date(tmpexpiredDate.getYear(), tmpexpiredDate.getMonth(), dtScheduleDate.getDate());
        }
        else
        {
            expiredDate = new Date(tmpexpiredDate.getYear(), tmpexpiredDate.getMonth(), maxDay);
        }

        objDpStockMgn.setLeavePeriodeId(lLeavePeriodId);
        objDpStockMgn.setEmployeeId(lEmployeeId);
        objDpStockMgn.setiDpQty(PstDpStockManagement.DP_QTY_COUNT_FROM_OFF);
        objDpStockMgn.setQtyUsed(0);
        objDpStockMgn.setQtyResidue(objDpStockMgn.getiDpQty() - objDpStockMgn.getQtyUsed());
        objDpStockMgn.setDtOwningDate(dtScheduleDate);
        objDpStockMgn.setDtExpiredDate(expiredDate);
        objDpStockMgn.setiExceptionFlag(PstDpStockManagement.EXC_STS_NO);
        objDpStockMgn.setDtExpiredDateExc(expiredDate);
        objDpStockMgn.setiDpStatus(PstDpStockManagement.DP_STS_NOT_AKTIF);
        objDpStockMgn.setStNote("DP OFF on " + Formater.formatDate(dtScheduleDate,"dd-MM-yyyy"));

        return objDpStockMgn;  
    }
    
    /**
     * Untuk mengenerate default dari DpStockManagement
     * @param dtScheduleDate
     * @return DpStockManagement
     * @created by Artha
     */
    private DpStockManagement getDpStockManagementSO(Date dtScheduleDate, long lLeavePeriodId, long lEmployeeId) 
    {
        DpStockManagement objDpStockMgn = new DpStockManagement();

        //Date tmpexpiredDate = new Date(dtScheduleDate.getYear(), dtScheduleDate.getMonth()+PstDpStockManagement.DP_EXPIRED_COUNT, 1); 
        I_Leave leaveConfig = null;           
        try {
            leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());            
        }
        catch(Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }        
        
        String strLevel = leaveConfig.getLevel(lEmployeeId);
        Date tmpexpiredDate = new Date(dtScheduleDate.getYear(), dtScheduleDate.getMonth()+leaveConfig.getDpValidity(strLevel), 1); 
        Calendar objCal = Calendar.getInstance();
        objCal.setTime(tmpexpiredDate);
        int maxDay = objCal.getActualMaximum(Calendar.DAY_OF_MONTH);                                                                                               
        Date expiredDate = new Date();
        if(dtScheduleDate.getDate() <= 28)
        {
            expiredDate = new Date(tmpexpiredDate.getYear(), tmpexpiredDate.getMonth(), dtScheduleDate.getDate());
        }
        else
        {
            expiredDate = new Date(tmpexpiredDate.getYear(), tmpexpiredDate.getMonth(), maxDay);
        }

        objDpStockMgn.setLeavePeriodeId(lLeavePeriodId);
        objDpStockMgn.setEmployeeId(lEmployeeId);
        //objDpStockMgn.setiDpQty(PstDpStockManagement.DP_QTY_COUNT_FROM_OFF);
        objDpStockMgn.setiDpQty(cekDpAvailable(PstScheduleCategory.CATEGORY_SUPPOSED_TO_BE_OFF, true, lEmployeeId, dtScheduleDate));
        objDpStockMgn.setQtyUsed(0);
        objDpStockMgn.setQtyResidue(objDpStockMgn.getiDpQty() - objDpStockMgn.getQtyUsed());
        objDpStockMgn.setDtOwningDate(dtScheduleDate);
        objDpStockMgn.setDtExpiredDate(expiredDate);
        objDpStockMgn.setiExceptionFlag(PstDpStockManagement.EXC_STS_NO);
        objDpStockMgn.setDtExpiredDateExc(expiredDate);
        objDpStockMgn.setiDpStatus(PstDpStockManagement.DP_STS_NOT_AKTIF);
        objDpStockMgn.setStNote("DP Supposed to be Off on " + Formater.formatDate(dtScheduleDate,"dd-MM-yyyy"));

        return objDpStockMgn;  
    }
    
    /**
     * Untuk mengenerate default dari DpStockManagement
     * @param dtScheduleDate
     * @return DpStockManagement  
     * @created by Edhy
     */
    private DpStockManagement getDpStockManagementEod(Date dtScheduleDate, long lLeavePeriodId, long lEmployeeId) 
    {
        DpStockManagement objDpStockMgn = new DpStockManagement();

        Date tmpexpiredDate = new Date(dtScheduleDate.getYear(), dtScheduleDate.getMonth()+PstDpStockManagement.DP_EXPIRED_COUNT, 1); 
        Calendar objCal = Calendar.getInstance();
        objCal.setTime(tmpexpiredDate);
        int maxDay = objCal.getActualMaximum(Calendar.DAY_OF_MONTH);                                                                                               
        Date expiredDate = new Date();
        if(dtScheduleDate.getDate() <= 28)
        {
            expiredDate = new Date(tmpexpiredDate.getYear(), tmpexpiredDate.getMonth(), dtScheduleDate.getDate());
        }
        else
        {
            expiredDate = new Date(tmpexpiredDate.getYear(), tmpexpiredDate.getMonth(), maxDay);
        }

        objDpStockMgn.setLeavePeriodeId(lLeavePeriodId);
        objDpStockMgn.setEmployeeId(lEmployeeId);
        objDpStockMgn.setiDpQty(PstDpStockManagement.DP_QTY_COUNT_ON_EOD);
        objDpStockMgn.setQtyUsed(0);
        objDpStockMgn.setQtyResidue(objDpStockMgn.getiDpQty() - objDpStockMgn.getQtyUsed());
        objDpStockMgn.setDtOwningDate(dtScheduleDate);
        objDpStockMgn.setDtExpiredDate(expiredDate);
        objDpStockMgn.setiExceptionFlag(PstDpStockManagement.EXC_STS_NO);
        objDpStockMgn.setDtExpiredDateExc(expiredDate);
        objDpStockMgn.setiDpStatus(PstDpStockManagement.DP_STS_NOT_AKTIF);
        objDpStockMgn.setStNote("DP EOD on " + Formater.formatDate(dtScheduleDate,"dd-MM-yyyy"));

        return objDpStockMgn;  
    }

    /**
     * generate data DP untuk schedule EOD, tidak ada sangkut pautnya dengan Public Holiday
     * @param empSchedule
     * @param empAfterSchedule
     * @param lEmployeeId
     */
    public void generateDpEod(EmpSchedule empSchedule, EmpSchedule empAfterSchedule, long oidPeriod, long lEmployeeId) 
    {        
        Vector vectScheduleOrg = toVector(empSchedule);        
        Vector vectScheduleAfter = toVector(empAfterSchedule);
        
        if( (vectScheduleOrg!=null && vectScheduleOrg.size()>0) && 
            (vectScheduleAfter!=null && vectScheduleAfter.size()>0) &&
            (vectScheduleOrg.size() == vectScheduleAfter.size())
          )
        {
            Period objPeriod = new Period();
            Date startDatePeriod = null;
            try
            {
                objPeriod = PstPeriod.fetchExc(oidPeriod);
                startDatePeriod = objPeriod.getStartDate();
            }
            catch(Exception e)
            {
                System.out.println("Exc when fetch period : " + e.toString());
            }            
            
            int maxIterate = vectScheduleOrg.size();
            Date dpOwningDate = null;
            for(int i=0; i<maxIterate; i++)
            {
                long scheduleOrgId = Long.parseLong(String.valueOf(vectScheduleOrg.get(i)));
                long scheduleAfterId = Long.parseLong(String.valueOf(vectScheduleAfter.get(i)));
                
                if(i<31)
                {
                    dpOwningDate = new Date(startDatePeriod.getYear(), startDatePeriod.getMonth(), i+1);
                }
                else
                {
                    dpOwningDate = new Date(startDatePeriod.getYear(), startDatePeriod.getMonth(), i-30);
                }                
                
                checkScheduleEod(scheduleOrgId, scheduleAfterId, dpOwningDate, oidPeriod, lEmployeeId);
            }
        }        
    }
    
    
    /**
     * @param dpOwningDate
     * @param oid
     * @param newOid
     * @param lEmployeeId
     * @created by Gedhy
     */
    public void checkScheduleEod(long orgScheduleoid, long updatedScheduleOid, Date dpOwningDate, long lLeavePeriodId, long lEmployeeId) 
    {
        int intCategoryOrgSchedule = PstScheduleSymbol.getCategoryType(orgScheduleoid);
        int intCategoryUpdatedSchedule = PstScheduleSymbol.getCategoryType(updatedScheduleOid);        

        // jika schedule sebelumnya EOD (dapat DP) kemudian diubah
        if (intCategoryOrgSchedule == PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY)
        {
            // maka delete current DP for specified employee                    
            if (intCategoryUpdatedSchedule != PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY)            
            {
                try  
                {                    
                    DpStockManagement objDpStockMgn = PstDpStockManagement.getDpStockManagement(dpOwningDate, lEmployeeId);                    
                    PstDpStockManagement pstDpStockManagement = new PstDpStockManagement();
                    pstDpStockManagement.deleteExc(objDpStockMgn);
                } 
                catch (Exception e) 
                {
                    System.out.println("Exc on jika schedule sebelumnya MASUK dan menjadi schedule ABSENCE setelah diupdate : " + e.toString());
                }            
            }
        }
        
        // jika schedule sebelumnya bukan EOD dan berubah menjadi schedule EOD                 
        if (intCategoryOrgSchedule != PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY)            
        {            
            // maka akan ditambahkan/insert 1 buah DP
            if (intCategoryUpdatedSchedule == PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY)            
            {
                try 
                {                    
                    DpStockManagement objDpStockMgn = getDpStockManagementEod(dpOwningDate, lLeavePeriodId, lEmployeeId);        
                    PstDpStockManagement pstDpStockManagement = new PstDpStockManagement();
                    
                    // check apakah sudah ada perolehan DP pada tanggal tersebut                    
                    String strOwningDate = Formater.formatDate(dpOwningDate, "yyyy-MM-dd");
                    String whereClause = pstDpStockManagement.fieldNames[pstDpStockManagement.FLD_EMPLOYEE_ID] +
                                         " = " + lEmployeeId + 
                                         " AND " + pstDpStockManagement.fieldNames[pstDpStockManagement.FLD_OWNING_DATE] +
                                         " = \"" + strOwningDate + "\"";
                    Vector vectDpStockExist = pstDpStockManagement.list(0,0,whereClause,"");                    
                    if(vectDpStockExist!=null && vectDpStockExist.size()>0)
                    {                        
                        DpStockManagement objDpStockMgnExist = (DpStockManagement) vectDpStockExist.get(0);

                        objDpStockMgn.setiDpQty(objDpStockMgnExist.getiDpQty());
                        objDpStockMgn.setQtyUsed(0);
                        objDpStockMgn.setQtyResidue(objDpStockMgn.getiDpQty() - objDpStockMgn.getQtyUsed());                        
                        pstDpStockManagement.updateExc(objDpStockMgn);                                                
                    }
                    else
                    {                                            
                        pstDpStockManagement.insertExc(objDpStockMgn);                        
                    }
                    
                } 
                catch (Exception e) 
                {  
                    System.out.println("Exc on jika schedule sebelumnya ABSENCE dan menjadi schedule MASUK setelah diupdate : " + e.toString());
                }                
            }            
        }
    }

    
    /** 
     * generate vector of oid schedule
     * consists of two index : 
     *  1st => schedule regular
     *  2nd => schedule second/split/eod
     * @param objEmpSchedule
     * @return
     */    
    public Vector toVector(EmpSchedule objEmpSchedule)
    {
        Vector result = new Vector(1,1);
        result.add(String.valueOf(objEmpSchedule.getD1()));
        result.add(String.valueOf(objEmpSchedule.getD2()));
        result.add(String.valueOf(objEmpSchedule.getD3()));
        result.add(String.valueOf(objEmpSchedule.getD4()));
        result.add(String.valueOf(objEmpSchedule.getD5()));
        result.add(String.valueOf(objEmpSchedule.getD6()));
        result.add(String.valueOf(objEmpSchedule.getD7()));
        result.add(String.valueOf(objEmpSchedule.getD8()));
        result.add(String.valueOf(objEmpSchedule.getD9()));
        result.add(String.valueOf(objEmpSchedule.getD10()));
        result.add(String.valueOf(objEmpSchedule.getD11()));
        result.add(String.valueOf(objEmpSchedule.getD12()));
        result.add(String.valueOf(objEmpSchedule.getD13()));
        result.add(String.valueOf(objEmpSchedule.getD14()));
        result.add(String.valueOf(objEmpSchedule.getD15()));
        result.add(String.valueOf(objEmpSchedule.getD16()));
        result.add(String.valueOf(objEmpSchedule.getD17()));
        result.add(String.valueOf(objEmpSchedule.getD18()));
        result.add(String.valueOf(objEmpSchedule.getD19()));
        result.add(String.valueOf(objEmpSchedule.getD20()));
        result.add(String.valueOf(objEmpSchedule.getD21()));
        result.add(String.valueOf(objEmpSchedule.getD22()));
        result.add(String.valueOf(objEmpSchedule.getD23()));
        result.add(String.valueOf(objEmpSchedule.getD24()));
        result.add(String.valueOf(objEmpSchedule.getD25()));
        result.add(String.valueOf(objEmpSchedule.getD26()));
        result.add(String.valueOf(objEmpSchedule.getD27()));
        result.add(String.valueOf(objEmpSchedule.getD28()));
        result.add(String.valueOf(objEmpSchedule.getD29()));
        result.add(String.valueOf(objEmpSchedule.getD30()));
        result.add(String.valueOf(objEmpSchedule.getD31()));
        result.add(String.valueOf(objEmpSchedule.getD2nd1()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd2()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd3()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd4()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd5()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd6()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd7()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd8()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd9()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd10()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd11()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd12()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd13()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd14()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd15()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd16()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd17()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd18()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd19()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd20()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd21()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd22()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd23()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd24()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd25()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd26()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd27()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd28()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd29()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd30()));        
        result.add(String.valueOf(objEmpSchedule.getD2nd31()));                 
        return result;
    }    
}
