/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.leave;

import com.dimata.harisma.form.attendance.CtrlAlStockManagement;
import com.dimata.harisma.form.attendance.CtrlAlStockTaken;
import com.dimata.harisma.form.attendance.CtrlSpecialLeaveStock;
import com.dimata.util.Command;
import java.util.Vector;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import com.dimata.system.entity.system.*;
import com.dimata.harisma.session.attendance.*;

import com.dimata.qdep.db.*;
import com.dimata.util.LogicParser;
import com.dimata.util.Formater;

import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.leave.*;
import com.dimata.harisma.entity.search.*;

/**
 *
 * @author roy andika
 */

public class SessLeaveReport {
    
    /**
     * @Author Roy Andika
     * @param employee_id
     * @param period_id
     * @return
     */
    
    public static Vector getListLeaveSummary(long employee_id,long period_id){
        
        LeaveReportSummary leaveReportSummary = new LeaveReportSummary();
        
        if(employee_id == 0 || period_id == 0){
            return null;
        }
        
        Period period = new Period();
        Date endPeriod = new Date();        
        Date startPeriod = new Date();
        
        try{
            period = PstPeriod.fetchExc(period_id);
            endPeriod = period.getEndDate();
            startPeriod = period.getStartDate();
        }catch(Exception e){            
            System.out.println("Exception "+e.toString());
            return null;
        }
        
        String strStartPeriod = Formater.formatDate(startPeriod,"yyyy-MM-dd");
        String strEndPeriod = Formater.formatDate(endPeriod,"yyyy-MM-dd");
        //update by satrya 2012-10-16
        String whereAl = PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_EMPLOYEE_ID]+" = "+employee_id+
                " AND '"+strEndPeriod+"' BETWEEN "+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE]+
                " AND DATE_ADD("+PstAlStockManagement.fieldNames[PstAlStockManagement.FLD_ENTITLE_DATE]+",INTERVAL 1 YEAR )";
        
        Vector resultDataAl = new Vector();
        
        try{
            resultDataAl = PstAlStockManagement.list(0, 0, whereAl, null);
        }catch(Exception e){
            System.out.println("Exception Get Data Al Stock : "+e.toString());
        }
        
        AlStockManagement alStockManagement = new AlStockManagement();
        
        if(resultDataAl != null && resultDataAl.size() > 0){
            alStockManagement = (AlStockManagement)resultDataAl.get(0);
        }
        
        leaveReportSummary.setLlPrevBalance(alStockManagement.getPrevBalance());
        leaveReportSummary.setAlBalance(alStockManagement.getAlQty());
        
        /**
         * @True  : entitle berada dalam range start period dan end period
         * @False : entitle tidak berada dalam range start period dan end period
         */
        
        boolean infoAnnualLeave = false;
        
        infoAnnualLeave = infoAnnualLeaveAktif(alStockManagement.getEntitleDate(),endPeriod,startPeriod);
        
        int totalTknCurrentAnnualLeave = 0;
        int totalTknAllAnnualLeave = 0;
            
        if(infoAnnualLeave){ //entitle berada dalam range start period dan end period
            
            String whereTkn = PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]+" = "+employee_id+" AND "+
                    PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+" BETWEEN '"
                    +Formater.formatDate(alStockManagement.getEntitleDate(),"yyyy-MM-dd")+"' AND '"+strEndPeriod+"'";
            Vector listTknAl = new Vector();
            
            try{
                listTknAl = PstAlStockTaken.list(0, 0, whereTkn, null);
            }catch(Exception e){
                System.out.println("EXCEPTION "+e.toString());
            }
            
            if(listTknAl != null && listTknAl.size() > 0){
                
                for(int i = 0 ; i<listTknAl.size() ; i++){
                    
                    AlStockTaken alStockTaken = new AlStockTaken();
                    alStockTaken = (AlStockTaken)listTknAl.get(i);
                    
                    LeaveApplication leaveApplication = new LeaveApplication();
                    
                    try{
                        leaveApplication = PstLeaveApplication.fetchExc(alStockTaken.getLeaveApplicationId());
                    }catch(Exception e){
                        System.out.println("EXCEPTION "+e.toString());                        
                    }
                    
                    if(leaveApplication.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        
                        for(int j = 0 ; j < alStockTaken.getTakenQty() ; j++){
                        
                            totalTknCurrentAnnualLeave = totalTknCurrentAnnualLeave + 1;
                            totalTknAllAnnualLeave = totalTknAllAnnualLeave + 1;
                            
                        }
                    }
                }                
            }
        }else{ /*entitle berada dalam range start period dan end period */            
            
            String whereTkn = PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]+" = "+employee_id+" AND "+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+" BETWEEN '"
                    +strStartPeriod+"' AND '"+strEndPeriod+"'";
            Vector listTknAl = new Vector();
            
            try{
                listTknAl = PstAlStockTaken.list(0, 0, whereTkn, null);
            }catch(Exception e){
                System.out.println("EXCEPTION "+e.toString());
            }
            
            if(listTknAl != null && listTknAl.size() > 0){
                
                for(int i = 0 ; i<listTknAl.size() ; i++){
                    
                    AlStockTaken alStockTaken = new AlStockTaken();
                    alStockTaken = (AlStockTaken)listTknAl.get(i);
                    
                    LeaveApplication leaveApplication = new LeaveApplication();
                    
                    try{
                        leaveApplication = PstLeaveApplication.fetchExc(alStockTaken.getLeaveApplicationId());
                    }catch(Exception e){
                        System.out.println("EXCEPTION "+e.toString());                        
                    }
                    
                    if(leaveApplication.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        
                        for(int j = 0 ; j < alStockTaken.getTakenQty() ; j++){
                        
                            totalTknCurrentAnnualLeave = totalTknCurrentAnnualLeave + 1;
                            
                        }
                    }
                }                
            }
            
            String whereTknAll =PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]+" = "+employee_id+" AND "+ PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+" BETWEEN '"
                    +Formater.formatDate(alStockManagement.getEntitleDate(),"yyyy-MM-dd")+"' AND '"+strEndPeriod+"'";
            
            Vector alStockTknAll = new Vector();
            try{
                alStockTknAll = PstAlStockTaken.list(0, 0, whereTknAll, null);
            }catch(Exception e){
                System.out.println("Exception "+e.toString());
            }
            
            if(alStockTknAll != null && alStockTknAll.size() > 0){
                
                for(int p = 0 ; p < alStockTknAll.size() ; p++){
                    
                    AlStockTaken alStockTaken = new AlStockTaken();
                    alStockTaken = (AlStockTaken)listTknAl.get(p);
                    
                    LeaveApplication leaveApplication = new LeaveApplication();
                    
                    try{
                        leaveApplication = PstLeaveApplication.fetchExc(alStockTaken.getLeaveApplicationId());
                    }catch(Exception e){
                        System.out.println("EXCEPTION "+e.toString());                        
                    }
                    
                    if(leaveApplication.getDocStatus() == PstLeaveApplication.FLD_STATUS_APPlICATION_EXECUTED){
                        
                        for(int j = 0 ; j < alStockTaken.getTakenQty() ; j++){
                        
                            totalTknAllAnnualLeave = totalTknAllAnnualLeave + 1;
                            
                        }
                    }
                }
            }
        }
        
        String whereDp = PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_EMPLOYEE_ID]+" = "+employee_id+
                " AND "+PstDpStockManagement.fieldNames[PstDpStockManagement.FLD_OWNING_DATE]+" < '"+strEndPeriod+"'";                
        
        Vector valueDp = new Vector();
        
        valueDp = PstDpStockManagement.list(0, 0, whereDp, null);
        
        for(int i = 0 ; i < valueDp.size() ; i++){
            
            DpStockManagement dpStockManagement = new DpStockManagement();
            
            dpStockManagement = (DpStockManagement)valueDp.get(i);
                    
            String whereDpExpired = PstDpStockExpired.fieldNames[PstDpStockExpired.FLD_DP_STOCK_ID]+" = "+dpStockManagement.getOID();
            
            Vector resultDpExpired = new Vector();
            
            float totalDpExpired = 0;
            
            resultDpExpired = PstDpStockExpired.list(0, 0, whereDpExpired, null);            
            
            if(resultDpExpired != null && resultDpExpired.size() > 0 ){
                
                for(int j = 0 ; j < resultDpExpired.size() ; j++){
                    
                    DpStockExpired dpStockExpired = (DpStockExpired)resultDpExpired.get(j);
                    
                    totalDpExpired = totalDpExpired + dpStockExpired.getExpiredQty();
                    
                }
                
            }
            
            DpStockExpired dpStockExpired = new DpStockExpired();  
            
            /* insert data dp management to object*/
            
            
            
        }
        
        return null;        
        
    }
    
    /**
     * @Author Roy Andika
     * @param entitleDate
     * @param startPeriod
     * @param endPeriod
     * @return
     */
   public static boolean infoAnnualLeaveAktif(Date entitleDate,Date startPeriod,Date endPeriod){
       
       DBResultSet dbrs = null;
       
       try{
           
           String sqtEntitleDate = Formater.formatDate(entitleDate,"yyyy-MM-dd");
           String strStringPeriod = Formater.formatDate(startPeriod,"yyyy-MM-dd");
           String strEndPeriod = Formater.formatDate(endPeriod,"yyyy-MM-dd");
           
           String sql = "SELECT '"+sqtEntitleDate+"' BETWEEN '"+strStringPeriod+"' AND '"+strEndPeriod+"'";
           
           dbrs = DBHandler.execQueryResult(sql);
           ResultSet rs = dbrs.getResultSet();
           
           while(rs.next()){
                int i = rs.getInt(1);
                if(i == 1)
                    return true;
                else
                    return false;
           }
           
       }catch(Exception e){
           System.out.println("EXCEPTION "+e.toString());
       }
       
       return false;
   }

}
