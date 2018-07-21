/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.session.leave.dp;

import com.dimata.harisma.entity.attendance.EmpSchedule;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.leave.DpApplication;
import com.dimata.harisma.entity.leave.PstDpApplication;
import com.dimata.harisma.entity.leave.dp.DpAppDetail;
import com.dimata.harisma.entity.leave.dp.PstDpAppDetail;
import com.dimata.harisma.entity.leave.dp.PstDpAppMain;
import com.dimata.harisma.entity.leave.dp.DpAppMain;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.harisma.session.attendance.SessEmpSchedule;
import com.dimata.qdep.db.DBException;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author artha
 */
public class SessDpAppDetail {
    
    /**
     * Mencari DP yang belum diajukan dalam Dp Application
     * @param employeeId
     * @param tanggal pengajuan sekarang
     * @return vector dari vector yang belum diajukan
     */
    public static Vector listDpNotRequest(long empId, Date date,long dpAppMain){
        Vector vData = new Vector(1,1);
        
        //List Leave
        Hashtable hSysLeaveDP = new Hashtable();
        Hashtable hSysLeaveSP = new Hashtable();
        Hashtable hSysLeaveLL = new Hashtable();
        Hashtable hSysLeaveAL = new Hashtable();
        
        hSysLeaveDP = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT);
        hSysLeaveSP = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE);
        hSysLeaveLL = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_LONG_LEAVE);
        hSysLeaveAL = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE);
        
        Hashtable hLeave = new Hashtable(1,1);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT),hSysLeaveDP);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE),hSysLeaveSP);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_LONG_LEAVE),hSysLeaveLL);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE),hSysLeaveAL);
        
        Vector vListSchedule = new Vector(1,1);
        vListSchedule = SessEmpSchedule.searchEmpScheduleAfter(empId, date);
        
        Vector vDpAppliation = new Vector(1,1);
        for (int i = 0; i < vListSchedule.size(); i++) 
	{
                int startDatePeriod = 1;
                int dateStartDate = 1;
                int monthStartDate = 0;
                int yearStartDate = 1900;
		Vector temp = (Vector) vListSchedule.get(i);
		Employee employee = (Employee) temp.get(1);
		Period period = (Period) temp.get(2);
		EmpSchedule empSchedule = (EmpSchedule)temp.get(0);		
		
		long employeeId = employee.getOID();
		Date periodStartDate = period.getStartDate();
		//long periodId = period.getOID();
		String strFullName = employee.getFullName();

                //Mengeset tangga;
		monthStartDate = periodStartDate.getMonth()+1;
		yearStartDate =  periodStartDate.getYear()+1900;
		dateStartDate =  periodStartDate.getDate();
                
                Date transDate = new Date();				
                long dpApplicationOid = 0;
                long leaveApplicationOid = 0;
                int dpApplicationStatus = -1;
                String strScheduleSymbol ="";
                long idScheduleSymbol1 =0;
                long idScheduleSymbol2 =0;
                // untuk start date pertama kali
                startDatePeriod = period.getStartDate().getDate();						
                startDatePeriod = startDatePeriod -1;
                GregorianCalendar periodStart = new GregorianCalendar(period.getStartDate().getYear(), monthStartDate-1, dateStartDate);
       		int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
                //if(vDpAppliation.size()<=0){
                    vDpAppliation = listDpApplication(empId, period.getStartDate());
                //}
                for(int j = 0 ; j < maxDayOfMonth; j++) {

                        if(startDatePeriod == maxDayOfMonth){
                                startDatePeriod = 1;
                                idScheduleSymbol1 = PstEmpSchedule.getSchedule(startDatePeriod,employeeId,periodStartDate);
                                idScheduleSymbol2 = PstEmpSchedule.getSchedule2(startDatePeriod,employeeId,periodStartDate);
                                //Add by Artha
                                int typeSymbol = getLeaveSchType(hLeave, idScheduleSymbol1);
                                if(typeSymbol>0)
                                {
                                        transDate = new Date(periodStartDate.getYear(), periodStartDate.getMonth(), startDatePeriod);				
                                        System.out.println("------>>>>"+Formater.formatDate(transDate, "dd MMMM yyyy"));
                                        if(typeSymbol == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT){
                                            DpAppDetail dpAppDetail = getDpApp(vDpAppliation, transDate);
                                            if(dpAppDetail.getDpAppMainId()==dpAppMain || dpAppDetail.getDpAppMainId() == 0){
                                                vData.add(dpAppDetail);
                                            }
                                        }

                                        if(typeSymbol == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE
                                           || typeSymbol == PstScheduleCategory.CATEGORY_LONG_LEAVE
                                           || typeSymbol == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                           ){
                                                //strScheduleSymbol = "<a href=\"javascript:cmdLeaveApp('"+leaveApplicationOid+"','"+employeeId+"','')\" >"+ (leaveApplicationOid!=0 ? "<font color=\"#000000\">"+strScheduleSymbol+"</font>" : "<font color=\"#FF0000\"><b>"+strScheduleSymbol+"</b></font>") +"</a>";
                                        }


                                }

                        }
                        else {
                                startDatePeriod = startDatePeriod +1;
                                idScheduleSymbol1 = PstEmpSchedule.getSchedule(startDatePeriod,employeeId,periodStartDate);
                                idScheduleSymbol2 = PstEmpSchedule.getSchedule2(startDatePeriod,employeeId,periodStartDate);
                                //Add by Artha
                                int typeSymbol = getLeaveSchType(hLeave, idScheduleSymbol1);
                                //if(hashLeaveSchedule.get(""+idScheduleSymbol1) != null)
                                if(typeSymbol>0)
                                {
                                        transDate = new Date(periodStartDate.getYear(), periodStartDate.getMonth(), startDatePeriod);				
                                        System.out.println("------>>>>2"+Formater.formatDate(transDate, "dd MMMM yyyy"));
                                        if(typeSymbol == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT){
                                              DpAppDetail dpAppDetail = getDpApp(vDpAppliation, transDate);
                                              if(dpAppDetail.getDpAppMainId()==dpAppMain || dpAppDetail.getDpAppMainId() == 0){
                                                 vData.add(dpAppDetail);
                                              }							
                                        }

                                        if(typeSymbol == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE
                                           || typeSymbol == PstScheduleCategory.CATEGORY_LONG_LEAVE
                                           || typeSymbol == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                           ){
                                                //strScheduleSymbol = "<a href=\"javascript:cmdLeaveApp('"+leaveApplicationOid+"','"+employeeId+"','')\" >"+ (leaveApplicationOid!=0 ? "<font color=\"#000000\">"+strScheduleSymbol+"</font>" : "<font color=\"#FF0000\"><b>"+strScheduleSymbol+"</b></font>") +"</a>";
                                        }
                                }
                        }
                }
	}
        
        return vData;
    }
    
    
    private static int getLeaveSchType(Hashtable hLeave, long leaveOid){
	int type = -1;
	
	String key = String.valueOf(leaveOid);

        Hashtable hSysLeaveDP = new Hashtable();
        Hashtable hSysLeaveSP = new Hashtable();
        Hashtable hSysLeaveLL = new Hashtable();
        Hashtable hSysLeaveAL = new Hashtable();
        
	hSysLeaveDP = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT));
	hSysLeaveSP = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE));
	hSysLeaveLL = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_LONG_LEAVE));
	hSysLeaveAL = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE));
        
        if(hSysLeaveDP.containsKey(key)){
		return PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT;
	}else if(hSysLeaveSP.containsKey(key)){
		return PstScheduleCategory.CATEGORY_SPECIAL_LEAVE;
	}else if(hSysLeaveAL.containsKey(key)){
		return PstScheduleCategory.CATEGORY_LONG_LEAVE;
	}else if(hSysLeaveLL.containsKey(key)){
		return PstScheduleCategory.CATEGORY_ANNUAL_LEAVE;
	}
        
	return type;
    }
    
    //Mencari Dp yang telah dia ajukan
    public static Vector listDpApplication(long empId,Date dateStartPeriod){
        Vector lists = new Vector(1,1);
        DBResultSet dbrs = null;
        try {
             String query = "SELECT DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_DETAIL_ID]
                     +" ,DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_ID]
                     +" ,DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_ID]
                     +" ,DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_TAKEN_DATE]
                     +" ,DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_STATUS]
                     +" FROM "+PstDpAppDetail.TBL_DP_APP_DETAIL+" AS DETAIL "
                     +" INNER JOIN "+PstDpAppMain.TBL_DP_APP_MAIN+" AS MAIN "
                     +" ON DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_ID]
                     +" = MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_DP_APP_ID]
                     +" AND DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_TAKEN_DATE]
                     +" >= '"+Formater.formatDate(dateStartPeriod, "yyyy-MM-dd")+"'"
                     +" AND MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_EMPLOYEE_ID]
                     +" = "+empId;
          //  System.out.println("\tPstDpAppDetail.listDpApplication sql : "+query);
            dbrs = DBHandler.execQueryResult(query);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                DpAppDetail objDpAppDetail = new DpAppDetail();
                
                objDpAppDetail.setOID(rs.getLong(PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_DETAIL_ID]));
                objDpAppDetail.setDpAppMainId(rs.getLong(PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_ID]));
                objDpAppDetail.setDpId(rs.getLong(PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_ID]));
                objDpAppDetail.setTakenDate(rs.getDate(PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_TAKEN_DATE]));
                objDpAppDetail.setStatus(rs.getInt(PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_STATUS]));
                
                lists.add(objDpAppDetail);
            }
            rs.close();
            return lists;
            
        }catch(Exception e) {
            System.out.println(e);
        }finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);
    }
    
    public static DpAppDetail getDpApp(Vector vDpApp,Date date){
        DpAppDetail dpAppdetail = new DpAppDetail();
        for(int i=0;i<vDpApp.size();i++){
            dpAppdetail = (DpAppDetail)vDpApp.get(i);
            if(date.getTime()==dpAppdetail.getTakenDate().getTime()){
               // System.out.println(" ==> "+Formater.formatDate(date, "dd MMMM yyyy")+" ::: "+Formater.formatDate(dpAppdetail.getTakenDate(), "dd MMMM yyyy"));
                return dpAppdetail;
            }else{
                dpAppdetail = new DpAppDetail();
                //System.out.println("Tidak ada DpAppDetail yang sesuai....");
            }
        }
        dpAppdetail.setTakenDate(date);
        return dpAppdetail;
    }
    
    //Menghapus dp app detail dari suatu dp app main
    public static int clearDpAppDetail(long dpAppMainOid){
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        int result=0;
        try {
            String sql = "DELETE FROM " + PstDpAppDetail.TBL_DP_APP_DETAIL
                    +" WHERE "+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_ID]
                    +" = "+dpAppMainOid;
            
            result = DBHandler.execUpdate(sql);
            
            return result;
            
        }catch(Exception e) {
            System.out.println(e);
        }finally {
            return result;
        }
    }
    
    
    
    //Mencari dp app detail dari ap app main
    public static Vector listDpAppDetail(long dpAppMainId){
        Vector vList = new Vector(1,1);
        String whereClause = PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_ID]
                +" = "+dpAppMainId;
        vList = PstDpAppDetail.list(0, 0, whereClause, PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_TAKEN_DATE]+" ASC ");
        return vList;
    }
    
    
    //mencari dp ap detail yang belum di proses dan masih 
    public static Vector getDpAppDetail(long empId,Date takenMaxDate){
        Vector lists = new Vector(1,1);
        DBResultSet dbrs = null;
        try {
             String query = "SELECT DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_DETAIL_ID]
                     +" ,DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_ID]
                     +" ,DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_ID]
                     +" ,DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_TAKEN_DATE]
                     +" ,DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_STATUS]
                     +" ,MAIN."+ PstDpAppMain.fieldNames[PstDpAppMain.FLD_DP_APP_ID]
                     +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_EMPLOYEE_ID]
                     +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_SUBMISSION_DATE]
                     +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_BALANCE]
                     +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL_ID]
                     +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL2_ID]
                     +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL3_ID]
                     +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL_DATE]
                     +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL2_DATE]
                     +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL3_DATE]
                     +" ,MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_DOC_STATUS]
                     +" FROM "+PstDpAppDetail.TBL_DP_APP_DETAIL+" AS DETAIL "
                     +" INNER JOIN "+PstDpAppMain.TBL_DP_APP_MAIN+" AS MAIN"
                     +" ON DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_ID]
                     +" = MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_DP_APP_ID]
                     +" AND DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_TAKEN_DATE]
                     +" < '"+Formater.formatDate(takenMaxDate, "yyyy-MM-dd")+"'"
                     +" AND MAIN."+PstDpAppMain.fieldNames[PstDpAppMain.FLD_EMPLOYEE_ID]
                     +" = "+empId
                     +" AND DETAIL."+PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_STATUS]
                     +" = "+PstDpAppDetail.STATUS_NOT_PROCESSED;
        //    System.out.println("\tPstDpAppDetail.getDpAppDetail sql : "+query);
            dbrs = DBHandler.execQueryResult(query);
            ResultSet rs = dbrs.getResultSet();
            while(rs.next()) {
                DpAppDetail objDpAppDetail = new DpAppDetail();
                
                objDpAppDetail.setOID(rs.getLong(PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_DETAIL_ID]));
                objDpAppDetail.setDpAppMainId(rs.getLong(PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_APP_ID]));
                objDpAppDetail.setDpId(rs.getLong(PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_DP_ID]));
                objDpAppDetail.setTakenDate(rs.getDate(PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_TAKEN_DATE]));
                objDpAppDetail.setStatus(rs.getInt(PstDpAppDetail.fieldNames[PstDpAppDetail.FLD_STATUS]));
                
                //Dp App Main
                DpAppMain objDpAppMain = new DpAppMain();
                objDpAppMain.setOID(rs.getLong(PstDpAppMain.fieldNames[PstDpAppMain.FLD_DP_APP_ID]));
                objDpAppMain.setEmployeeId(rs.getLong(PstDpAppMain.fieldNames[PstDpAppMain.FLD_EMPLOYEE_ID]));
                objDpAppMain.setSubmissionDate(rs.getDate(PstDpAppMain.fieldNames[PstDpAppMain.FLD_SUBMISSION_DATE]));
                objDpAppMain.setBalance(rs.getInt(PstDpAppMain.fieldNames[PstDpAppMain.FLD_BALANCE]));
                objDpAppMain.setApprovalId(rs.getLong(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL_ID]));
                objDpAppMain.setApproval2Id(rs.getLong(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL2_ID]));
                objDpAppMain.setApproval3Id(rs.getLong(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL3_ID]));
                objDpAppMain.setApprovalDate(rs.getDate(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL_DATE]));
                objDpAppMain.setApproval2Date(rs.getDate(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL2_DATE]));
                objDpAppMain.setApproval3Date(rs.getDate(PstDpAppMain.fieldNames[PstDpAppMain.FLD_APPROVAL3_DATE]));
                objDpAppMain.setDocumentStatus(rs.getInt(PstDpAppMain.fieldNames[PstDpAppMain.FLD_DOC_STATUS]));
                
                Vector vTemp = new Vector(1,1);
                vTemp.add(objDpAppDetail);
                vTemp.add(objDpAppMain);
                
                lists.add(vTemp);
            }
            rs.close();
            return lists;
            
        }catch(Exception e) {
            System.out.println(e);
        }finally {
            DBResultSet.close(dbrs);
        }
        return new Vector(1,1);
    }
    
    
}
