/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.attendance;

/**
 *
 * @author Tu Roy
 */

// import package core java
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

// import package dimata
import com.dimata.qdep.db.*;
import com.dimata.util.LogicParser;
import com.dimata.util.Formater;

// import package project
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.leave.*;
import com.dimata.harisma.entity.search.*;


public class SessSpecialUnpaidLeaveTaken {
    
    public static int getSpcUnpLeaveMonth(Date tknDate,long empId,long schId){
        
        if(schId == 0 || schId == 0 || tknDate == null){
            return 0;
        }
        
        String strTknDate = Formater.formatDate(tknDate,"yyyy-MM-dd");
        int ttl = 0;
                
        String whrSpcLev = " SP."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID]+" = "+empId+
                    " AND SP."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]+" = "+schId+
                    " AND date_format("+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+",'%Y %m') = "+
                    " date_format('"+strTknDate+"','%Y %m')" +
                 //update by satrya 2013-06-07
                " AND "+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+"!="+PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED;
        
        ttl = PstSpecialUnpaidLeaveTaken.getCountRecordSpcUnpLeaveTkn(whrSpcLev);
            
        return ttl;   
    }
    
    public static int getSpcUnpLeaveAllTime(long empId,long schId){
        
        if(empId == 0 || schId == 0){
            return 0;
        }
        
        int ttl = 0;
        
        String whrSpcLev = " SP."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID]+" = "+empId+
                    " AND SP."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]+" = "+schId +
                 //update by satrya 2013-06-07
                " AND "+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+"!="+PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED;
                    
        ttl = PstSpecialUnpaidLeaveTaken.getCountRecordSpcUnpLeaveTkn(whrSpcLev);
            
        return ttl;
    }
    
    public static int getSpcUnpLeaveAllTime(long empId,long schId, long excepOidLv){
        
        if(empId == 0 || schId == 0){
            return 0;
        }
        
        int ttl = 0;
        
        String whrSpcLev = " SP."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID]+" = "+empId+
                    " AND SP."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]+" = "+schId +
                " AND SP."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID] + "<>"+excepOidLv +
             //update by satrya 2013-06-07
                " AND "+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+"!="+PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED;
        ttl = PstSpecialUnpaidLeaveTaken.getCountRecordSpcUnpLeaveTkn(whrSpcLev);
            
        return ttl;
    }
    
    
    public static float getSumQtySpcUnpLeaveAllTime(long empId,long schId, long excepOidLv){
        
        if(empId == 0 || schId == 0){
            return 0;
        }
        
        float ttl = 0f;
        
        String whrSpcLev = " SP."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID]+" = "+empId+
                    " AND SP."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]+" = "+schId +
                " AND SP."+ PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID] + "<>"+excepOidLv +
                " AND "+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+"!="+PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED;
                    
        ttl = PstSpecialUnpaidLeaveTaken.getSumQtySpcUnpLeaveTkn(whrSpcLev);
            
        return ttl;
    }
    
    
    public static int getSpcUnpLeaveYear(Date tknDate,long empId,long schId){
        
        String strDtFrmt = Formater.formatDate(tknDate,"yyyy-MM-dd");
        
        if(schId == 0 || schId == 0 || tknDate == null){
            return 0;
        }
        
        int ttl = 0;
        
        String strTknDate = Formater.formatDate(tknDate,"yyyy-MM-dd");
        
        String whrSpcLev = " SP."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID]+" = "+empId+
                    " AND SP."+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]+" = "+schId+
                    " AND date_format("+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+",'%m') = "+
                    " date_format('"+strTknDate+"','%m')" +
                 //update by satrya 2013-06-07
                " AND "+ PstLeaveApplication.fieldNames[PstLeaveApplication.FLD_DOC_STATUS]+"!="+PstLeaveApplication.FLD_STATUS_APPlICATION_CANCELED;
        ttl = PstSpecialUnpaidLeaveTaken.getCountRecordSpcUnpLeaveTkn(whrSpcLev);
            
        return ttl;
    }
    
    public static int getDiferentDate(Date dtFrm, Date dtFns){
        
        if(dtFrm == null || dtFns == null){
            return 0;
        }
        DBResultSet dbrs = null;
        String strdtFrm = Formater.formatDate(dtFrm,"yyyy-MM-dd");
        String strdtFns = Formater.formatDate(dtFns,"yyyy-MM-dd");
        
        try{
            String sql = "SELECT DATEDIFF('"+strdtFrm+"','"+strdtFns+"')";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            int diferent = 0;
            
            while(rs.next()) {
                diferent = rs.getInt(1);
            }
            
            rs.close();
            return diferent;
            
        }catch(Exception e) {
            System.out.println(e.toString());
        }finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }
    
    
    public static int DateAdd(Date date1,Date date2,int intrvMonth){
        
        if(date1 == null || date2 == null){
            return 0;
        }
        DBResultSet dbrs = null;
        
        String strDate1 = Formater.formatDate(date1,"yyyy-MM-dd");
        String strDate2 = Formater.formatDate(date2,"yyyy-MM-dd");
        
        try{
            
            String sql = "SELECT DATEDIFF('"+strDate1+"', DATE_ADD('"+strDate2+"', INTERVAL "+intrvMonth+" MONTH))";
            
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            int diferent = 0;
            
            while(rs.next()) {
                diferent = rs.getInt(1);
            }
            
            rs.close();
            return diferent;
            
        }catch(Exception e) {
            System.out.println(e.toString());
        }finally {
            DBResultSet.close(dbrs);
        }
        return 0;
    }    
}
