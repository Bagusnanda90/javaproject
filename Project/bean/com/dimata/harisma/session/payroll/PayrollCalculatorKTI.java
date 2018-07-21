/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.session.payroll;

import com.dimata.harisma.entity.attendance.EmpSchedule;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.leave.PstLeaveApplication;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.HolidaysTable;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstPublicHolidays;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.entity.overtime.HashTblOvertimeDetail;
import com.dimata.harisma.entity.overtime.PstOvertimeDetail;
import com.dimata.harisma.entity.payroll.PayPeriod;
import com.dimata.harisma.entity.payroll.PstPayPeriod;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author ktanjana
 */
public class PayrollCalculatorKTI implements I_PayrollCalculator {

    public static long depIdHouseKeeping = 37L;
    public static long depIdAssetProtectionGroup = 124L;
    private HolidaysTable holidaysTable = null; // loaded pior to needed, that can get by other modules , so loading is only once
    private static long longOffSch = 0;
    private final String OFF_SCHEDULE = "Off";

    private static Hashtable hashTblCategoryIsPermanent=null;
    
    private void loadHolidaysTable(Date startDate, Date endDate) {
        holidaysTable = PstPublicHolidays.getHolidaysTable(startDate, endDate);
    }

    /**
     * @return the holidaysTable
     */
    private HolidaysTable getHolidaysTable() {
        return holidaysTable;
    }
    

    
    public boolean checkPayrollComponent(String payCompCode, long employeeId, long departmentId, Date selDate, boolean isHoliday, long oidLeave, long oidOvertime, int statusSchedule, int iPositionLevel, int iPropInsentifLevel, String strSchldSymbol1,long empCategoryId) {
        if (payCompCode != null) {
            
            if (payCompCode.equalsIgnoreCase("INS")) {
                // Satria lengkapi
                SimpleDateFormat formatterDay = new SimpleDateFormat("EE");
                String dayString = formatterDay.format(selDate);
                if ((hashTblCategoryIsPermanent!=null && hashTblCategoryIsPermanent.containsKey(empCategoryId)) && statusSchedule == PstEmpSchedule.STATUS_PRESENCE_OK
                        && oidLeave == 0 && !strSchldSymbol1.equalsIgnoreCase("off")
                        && iPositionLevel <= iPropInsentifLevel && isHoliday == false) {
                    if (departmentId == depIdHouseKeeping && (dayString.equalsIgnoreCase("Sat") || dayString.equalsIgnoreCase("Sun"))) {
                        //System.out.println("nilai:" + (hashTblCategoryIsPermanent!=null && hashTblCategoryIsPermanent.size()>0?hashTblCategoryIsPermanent.size():0));
                        return false;
                    } else if (departmentId == depIdAssetProtectionGroup && strSchldSymbol1.equalsIgnoreCase("off") && (oidOvertime != 0)) {
                        //System.out.println("nilai:" + (hashTblCategoryIsPermanent!=null && hashTblCategoryIsPermanent.size()>0?hashTblCategoryIsPermanent.size():0));
                        return false;
                    } else {
                        //System.out.println("nilai:" + (hashTblCategoryIsPermanent!=null && hashTblCategoryIsPermanent.size()>0?hashTblCategoryIsPermanent.size():0));
                        return true;
                    }
                    
                } else {
                    //System.out.println("nilai:" + (hashTblCategoryIsPermanent!=null && hashTblCategoryIsPermanent.size()>0?hashTblCategoryIsPermanent.size():0));
                    return false;
                }

            } else {
                System.out.println("nilai:" + (hashTblCategoryIsPermanent!=null && hashTblCategoryIsPermanent.size()>0?hashTblCategoryIsPermanent.size():0));
                return false;
            }
        } else {
            //System.out.println("nilai:" + (hashTblCategoryIsPermanent!=null && hashTblCategoryIsPermanent.size()>0?hashTblCategoryIsPermanent.size():0));
            return false;
        }
    }

    public double calculatePayrollFormula(String variableCode, long employeeId, long departmentId, long periodId,
            int dayOfMonth, int statusSchedule, Date dtPeriod, int iPositionLevel, int iPropInsentifLevel) {
        if (variableCode != null) {
            double result = 0;
            if (variableCode.equalsIgnoreCase(PstPaySlip.PAY_COMP_DATE_OK)) {
                // Pak Tut Add
                DBResultSet dbrs = null;
                int count = 0;
                try {
                    if (dtPeriod != null) {
                        String sql = "SELECT ";
                        //+PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1]+", " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1] ;;
           /* for(int idx=1;idx<31;idx++){
                         sql = sql+", " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_STATUS1+idx] 
                         +", " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_D1+idx] ;
                         }*/
                        //update by satrya 2013-02-20
                        int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(dtPeriod);
                        int x = 0;
                        for (int i = 0; i <= (dayOfMonth); i++) {
                            int idxFieldNameX = idxFieldName + i;
                            if (idxFieldNameX >= 32) {
                                idxFieldNameX = x + 1;
                                x = x + 1;
                            }
                            sql = sql + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldNameX - 1] + ", " + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idxFieldNameX - 1] + ", ";
                        }
                        sql = sql.substring(0, sql.length() - 2);

                        sql = sql + " FROM " + PstEmpSchedule.TBL_HR_EMP_SCHEDULE
                                + " WHERE " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_PERIOD_ID]
                                + " = " + periodId
                                + " AND " + PstEmpSchedule.fieldNames[PstEmpSchedule.FLD_EMPLOYEE_ID]
                                + " = " + employeeId;

                        //System.out.println("sql getStatusPresence....................."+sql);
                        dbrs = DBHandler.execQueryResult(sql);
                        ResultSet rs = dbrs.getResultSet();


                        while (rs.next()) {
                            Vector listLeave = null;
                            Vector listOvertime = null;
                            Period period = null;
                            // Period period = null;
                            try {
                                //payPeriod = PstPayPeriod.fetchExc(periodId);
                                period = PstPeriod.fetchExc(periodId);//memakai period attd
                                if (period != null) {
                                    listLeave = PstLeaveApplication.listDetailLeave(employeeId, period.getStartDate(), period.getEndDate());
                                }
                                //update by satrya 2013-05-06
                                listOvertime = PstOvertimeDetail.listOvertimeOverlapVer2(0, 0, 300, departmentId, "", period.getStartDate(), period.getEndDate(), 0, "", employeeId, "");
                                //listOvertime = PstOvertimeDetail.listOvertimeOverlap(0,0,300,departmentId, "", period.getStartDate(),  period.getEndDate(), 0, "", employeeId,"");

                            } catch (Exception exc1) {
                                System.out.println(exc1);
                            }

                            //EmpSchedule empSchedule = new EmpSchedule();
                            //int status=0;
                            x = 0;
                            int idx = 0;
                            // for(int idx=0;idx<31;idx++){
                            //update by satrya 2013-02-20
                            for (int i = 0; i <= dayOfMonth; i++) {
                                int idxFieldNameX = idxFieldName + i;
                                if (idxFieldNameX >= 32) {
                                    idxFieldNameX = x + 1;
                                    x = x + 1;
                                }

                                if ((rs.getInt(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_STATUS + idxFieldNameX - 1]) == statusSchedule)
                                        && (rs.getLong(PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldNameX - 1]) != longOffSch)) {
                                    // check cuti payPeriod
                                    String sIdx = "" + PstEmpSchedule.fieldNames[PstEmpSchedule.OFFSET_INDEX_CALENDAR + idxFieldNameX - 1].substring(1);
                                    if (sIdx != null && sIdx.length() > 0) {
                                        idx = Integer.parseInt(sIdx);
                                    }
                                    Date theDate = period.getObjDateByIndex(idx);
                                    //Date theDate = payPeriod.getObjDateByIndex(1+idx);
                                    //Date theDate = period.getObjDateByIndex(1+idx);
                                    boolean isHoliday = false;
                                    if (holidaysTable != null) {
                                        isHoliday = holidaysTable.getNationalHoliday(theDate);
                                    }
                                    if (departmentId == depIdAssetProtectionGroup) {
                                        if (!(period != null && listLeave != null && listLeave.size() > 0 && (iPositionLevel <= iPropInsentifLevel)
                                                && PstLeaveApplication.checkDateInLeave(theDate, listLeave)) && !isHoliday /*!(PstOvertimeDetail.checkOvertimeInVector(listOvertime, theDate))*/) {
                                            count++;
                                        }
                                    } else if (departmentId == depIdHouseKeeping) {
                                        if ((theDate.getDay() + 1) != Calendar.SATURDAY && (theDate.getDay() + 1) != Calendar.SUNDAY) {
                                            if (!(period != null && listLeave != null && listLeave.size() > 0 && (iPositionLevel <= iPropInsentifLevel)
                                                    && PstLeaveApplication.checkDateInLeave(theDate, listLeave)) /*!(PstOvertimeDetail.checkOvertimeInVector(listOvertime, theDate))*/) {
                                                count++;
                                            }
                                        }
                                    } else {
                                        if (!(period != null && listLeave != null && listLeave.size() > 0
                                                && PstLeaveApplication.checkDateInLeave(theDate, listLeave)) && (iPositionLevel <= iPropInsentifLevel)
                                                && !(isHoliday && PstOvertimeDetail.checkOvertimeInVector(listOvertime, theDate))) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }

                        rs.close();
                    }
                    return count;
                } catch (Exception e) {
                    return 0;
                } finally {
                    DBResultSet.close(dbrs);
                }

            }
            return result;
        } else {
            return 0.0d;
        }
    }

    public void initializedPreloadedData(Date selectedDateFrom, Date selectedDateTo) {
        loadHolidaysTable(selectedDateFrom, selectedDateTo);
        try {
            String where = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL] + "=\"" + OFF_SCHEDULE + "\"";
            Vector lst = PstScheduleSymbol.list(0, 1, where, "");
            if (lst != null && lst.size() > 0) {
                ScheduleSymbol sch = (ScheduleSymbol) lst.get(0);
                longOffSch = sch.getOID();
            }
        } catch (Exception exc) {
        }
    }
//update by satrya 2013-05-06

    public boolean checkInsentif(long employeeId, long religionId, long departementId, int statusSchedule, int iPositionLevel, int iPropInsentifLevel, long scheduleId, Hashtable hashScheduleOff,boolean isHoliday, Date selectedDate,HashTblOvertimeDetail hashTblOvertimeDetail,long empCategory) {
        // Satria lengkapi
//             boolean checkInst=false;
//      if(status == PstEmpSchedule.STATUS_PRESENCE_OK ){
//                boolean isHoliday= false;
//                if(holidaysTable!=null){            
//                            isHoliday = holidaysTable.isHoliday(religionId, selectedDate);
//                }
//               
//                 Vector  listLeave = PstLeaveApplication.listDetailLeave(employeeId, selectedDate, selectedDate);
//                
//                 Vector listOvertime = PstOvertimeDetail.listOvertimeOverlapVer2(0,0,300,departementId, "",selectedDate,  selectedDate, 0, "", employeeId,"");
//                    
//                if(departementId==depIdAssetProtectionGroup){
//                    if (listLeave.size()==0 && (iPositionLevel<=iPropInsentifLevel) &&                             
//                       PstLeaveApplication.checkDateInLeave(selectedDate, listLeave) &&  !isHoliday
//                       /*!(PstOvertimeDetail.checkOvertimeInVector(listOvertime, theDate))*/ && scheduleOff!= scheduleId) {
//                        checkInst = true;
//                    }
//                } else if(departementId==depIdHouseKeeping) {
//                    if((selectedDate.getDay()+1)!=Calendar.SATURDAY && (selectedDate.getDay()+1)!=Calendar.SUNDAY ){
//                        if (listLeave.size()==0  && (iPositionLevel<=iPropInsentifLevel) &&                            
//                           PstLeaveApplication.checkDateInLeave(selectedDate, listLeave) 
//                           /*!(PstOvertimeDetail.checkOvertimeInVector(listOvertime, theDate))*/ && scheduleOff!= scheduleId) {
//                            checkInst = true;
//                        }                                 
//                    }   
//                     
//                } else {
//                    if ((listLeave.size()==0 &&                                
//                       (!PstLeaveApplication.checkDateInLeave(selectedDate, listLeave))) 
//                       && (iPositionLevel<=iPropInsentifLevel) && scheduleOff!= scheduleId && 
//                       !( isHoliday && PstOvertimeDetail.checkOvertimeInVector(listOvertime, selectedDate))) {
//                        checkInst = true;
//                    }
//                }
//        }
//      return  checkInst;
                //update by satrya 2014-01-31
                Vector  listLeave = null/*PstLeaveApplication.listDetailLeave(employeeId, selectedDate, selectedDate)*/;
                //Vector listOvertime = dPstOvertimeDetail.listOvertimeOverlapVer2(0,0,300,departementId, "",selectedDate,  selectedDate, 0, "", employeeId,"");
                //update by satrya 2014-02-03
                boolean adaOvertime= hashTblOvertimeDetail.getCekingOvertime(employeeId, selectedDate);
                boolean scheduleIsOff=true;
                if(hashScheduleOff!=null && hashScheduleOff.size()>0){
                    scheduleIsOff = hashScheduleOff.containsKey(scheduleId);
                }
                 
                SimpleDateFormat formatterDay = new SimpleDateFormat("EE");
                String dayString = formatterDay.format(selectedDate);
                if ((hashTblCategoryIsPermanent!=null && hashTblCategoryIsPermanent.containsKey(empCategory)) && statusSchedule == PstEmpSchedule.STATUS_PRESENCE_OK
                        && (listLeave == null || listLeave.size()==0) && !scheduleIsOff
                        && iPositionLevel <= iPropInsentifLevel && isHoliday == false) {
                    if (departementId == depIdHouseKeeping && (dayString.equalsIgnoreCase("Sat") || dayString.equalsIgnoreCase("Sun"))) {
                        //System.out.println("nilai:" + (hashTblCategoryIsPermanent!=null && hashTblCategoryIsPermanent.size()>0?hashTblCategoryIsPermanent.size():0));
                        return false; 
                    } else if (departementId == depIdAssetProtectionGroup && scheduleIsOff && (adaOvertime)) {
                        //System.out.println("nilai:" + (hashTblCategoryIsPermanent!=null && hashTblCategoryIsPermanent.size()>0?hashTblCategoryIsPermanent.size():0));
                        return false;
                    } else {
                        //System.out.println("nilai:" + (hashTblCategoryIsPermanent!=null && hashTblCategoryIsPermanent.size()>0?hashTblCategoryIsPermanent.size():0));
                        return true;
                    }
                    
                } else {
                   //System.out.println("nilai:" + (hashTblCategoryIsPermanent!=null && hashTblCategoryIsPermanent.size()>0?hashTblCategoryIsPermanent.size():0));
                    return false;
                }
                

    }

    public void loadEmpCategoryInsentif() {
         System.out.println("load config");
       hashTblCategoryIsPermanent=PstEmpCategory.getHashTableEmpCatId();
    }


}
