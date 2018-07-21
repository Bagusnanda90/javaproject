/*
 * FrmDpStockTaken.java
 *
 * Created on Jan, 08 2010, 5:14 PM
 */
package com.dimata.harisma.form.attendance;

// import core java package
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

// import qdep package
import com.dimata.qdep.form.*;

// import project package
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.session.leave.SessLeaveApplication;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.DateCalc;
import com.dimata.util.Formater;
import java.text.SimpleDateFormat;

/**
 *
 * @author Roy Andika
 */
public class FrmDpStockTaken extends FRMHandler implements I_FRMInterface, I_FRMType {

    private DpStockTaken dpStockTaken = null;
    private Vector dpTakens = null;
    public static final String FRM_DP_STOCK_TAKEN = "FRM_DP_STOCK_TAKEN";
    public static final int FRM_FIELD_DP_STOCK_TAKEN_ID = 0;
    public static final int FRM_FIELD_EMPLOYEE_ID = 1;
    public static final int FRM_FIELD_DP_STOCK_ID = 2;
    public static final int FRM_FIELD_TAKEN_DATE = 3;
    public static final int FRM_FIELD_TAKEN_QTY = 4;
    public static final int FRM_FIELD_PAID_DATE = 5;
    public static final int FRM_FIELD_LEAVE_APPLICATION_ID = 6;
    public static final int FRM_FIELD_FINNISH_DATE = 7;
    //update by satrya 2013-12-17
    public static final int FRM_FIELD_FLAG_FULL_SCHEDULE = 8;
    public static String[] fieldNames = {
        "FRM_FIELD_DP_STOCK_TAKEN_ID",
        "FRM_FLD_EMPLOYEE_ID",
        "FRM_FLD_DP_STOCK_ID",
        "FRM_FIELD_DP_TAKEN_DATE",
        "FRM_FIELD_DP_TAKEN_QTY",
        "FRM_FIELD_DP_PAID_DATE",
        "FRM_FLD_LEAVE_APPLICATION_ID",
        "FRM_FIELD_DP_FINNISH_DATE",
        //update by satrya 2013-12-17
        "FRM_FIELD_FLAG_FULL_SCHEDULE"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_LONG
    };

    /**
     * Creates a new instance of FrmAlStockTaken
     */
    public FrmDpStockTaken() {
        dpTakens = new Vector();
    }

    public FrmDpStockTaken(DpStockTaken dpStockTaken) {
        this.dpStockTaken = dpStockTaken;
        dpTakens = new Vector();
    }

    public FrmDpStockTaken(HttpServletRequest request, DpStockTaken dpStockTaken) {
        super(new FrmDpStockTaken(dpStockTaken), request);
        this.dpStockTaken = dpStockTaken;
        dpTakens = new Vector();
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getFormName() {
        return FRM_DP_STOCK_TAKEN;
    }

    public DpStockTaken getEntityObject() {
        return dpStockTaken;
    }

    public void requestEntityObject(DpStockTaken dpStockTaken) {
        try {
            this.requestParam();
            dpStockTaken.setOID(getLong(FRM_FIELD_DP_STOCK_TAKEN_ID));
            dpStockTaken.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            dpStockTaken.setDpStockId(getLong(FRM_FIELD_DP_STOCK_ID));
            dpStockTaken.setTakenDate(getDate(FRM_FIELD_TAKEN_DATE));
            dpStockTaken.setPaidDate(getDate(FRM_FIELD_PAID_DATE));
            dpStockTaken.setLeaveApplicationId(getLong(FRM_FIELD_LEAVE_APPLICATION_ID));
            //update by satrya 2013-12-17
            dpStockTaken.setFlagFullSchedule(getInt(FRM_FIELD_FLAG_FULL_SCHEDULE));
            //update by satrya 2013-12-13
            boolean leaveConfigCalculationCategoryOff = false;
            I_Leave leaveConfig = null;
            try {
                leaveConfigCalculationCategoryOff = Boolean.parseBoolean(PstSystemProperty.getValueByName("LEAVE_CONFIG_CALCULATE_CATEGORY_SCHEDULE_OFF"));
                leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
            } catch (Exception exc) {
                System.out.println("Exc FrmAlStockTaken" + exc);
            }

            //update by satrya 2013-12-13
            /**
             * dpStockTaken.setTakenFinnishDate(getDate(FRM_FIELD_TAKEN_DATE));
             * ///update by satrya 2012-08-07
             * //dpStockTaken.setTakenFinnishDate(getDate(FRM_FIELD_TAKEN_DATE));
             * // Kartika : finish date is the same as taken date only the time
             * will be different java.util.Date finishTime =
             * getDate(FRM_FIELD_FINNISH_DATE); java.util.Date finishDate =
             * dpStockTaken.getTakenFinnishDate();
             * finishDate.setHours(finishTime.getHours());
             * finishDate.setMinutes(finishTime.getMinutes());
             * finishDate.setSeconds(finishTime.getSeconds());
             * dpStockTaken.setTakenFinnishDate(finishDate);
             */
            if (leaveConfig != null && leaveConfig.getConfigurationLeaveApprovall() == I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG) {
                dpStockTaken.setTakenFinnishDate(getDate(FRM_FIELD_FINNISH_DATE));
            } else {
                dpStockTaken.setTakenFinnishDate(getDate(FRM_FIELD_TAKEN_DATE));
                ///update by satrya 2012-08-07
                //dpStockTaken.setTakenFinnishDate(getDate(FRM_FIELD_TAKEN_DATE));
                // Kartika : finish date is the same  as taken date only the time will be different            
                java.util.Date finishTime = getDate(FRM_FIELD_FINNISH_DATE);
                java.util.Date finishDate = dpStockTaken.getTakenFinnishDate();
                finishDate.setHours(finishTime.getHours());
                finishDate.setMinutes(finishTime.getMinutes());
                finishDate.setSeconds(finishTime.getSeconds());
                dpStockTaken.setTakenFinnishDate(finishDate);
            }

            if (dpStockTaken.getTakenDate().getHours() == dpStockTaken.getTakenFinnishDate().getHours()
                    && dpStockTaken.getTakenDate().getMinutes() == dpStockTaken.getTakenFinnishDate().getMinutes()) { // jika jam dan menit sama berarti cuti dalam hitungan hari kerja

                //update by satrya 2013-12-12  float dpQty = SessLeaveApplication.DATEDIFF(dpStockTaken.getTakenFinnishDate(), dpStockTaken.getTakenDate());
                float dpQty = 0;
                //jika ingin menggunakan konfigurasi, dihitung cutinya tidak dengan dayoff
                if (leaveConfigCalculationCategoryOff) {
                    int diffDay = SessLeaveApplication.DATEDIFF(dpStockTaken.getTakenFinnishDate(), dpStockTaken.getTakenDate()) + 1;//kenapa di tambah 1 karena belum di hitung start datenya
                    //untuk mencari hari libur
                    Vector vctSchIDOff = PstScheduleSymbol.getScheduleId(PstScheduleCategory.CATEGORY_OFF);
                    int dayOffSchedule = 0;
                   
                    if (dpStockTaken.getTakenDate() != null) {
                        for (int ix = 0; ix < diffDay; ix++) {
                            Date selectedDate = new Date(dpStockTaken.getTakenDate().getYear(), dpStockTaken.getTakenDate().getMonth(), (dpStockTaken.getTakenDate().getDate() + ix));
                            Period period = PstPeriod.getPeriodBySelectedDate(selectedDate);
                            dayOffSchedule = dayOffSchedule + PstEmpSchedule.getStatusDayOff(dpStockTaken.getEmployeeId(), period.getOID(), 0, vctSchIDOff, selectedDate);
                            int dayOffSchedules = PstEmpSchedule.getStatusDayOff(dpStockTaken.getEmployeeId(), period.getOID(), 0, vctSchIDOff, selectedDate);
                            if (dayOffSchedules == 0) {
                                Date dtTakendate = null;
                                Date dtTakenFinishDate=null;
                                ScheduleSymbol scheduleSymbol = PstEmpSchedule.getEmpScheduleDateTime(period.getOID(), dpStockTaken.getEmployeeId(), selectedDate, 0);
                               if(dpStockTaken.getFlagFullSchedule()==1){
                                dtTakendate =(new Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate(), scheduleSymbol.getTimeIn().getHours(), scheduleSymbol.getTimeIn().getMinutes(), 0));
                                dtTakenFinishDate = (new Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate(), scheduleSymbol.getTimeOut().getHours(), scheduleSymbol.getTimeOut().getMinutes(), 0));
                               }else{
                                   if(ix==0){
                                       dtTakendate =(new Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate(), dpStockTaken.getTakenDate().getHours(), dpStockTaken.getTakenDate().getMinutes(), 0));
                                   }else{
                                       dtTakendate =(new Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate(), scheduleSymbol.getTimeIn().getHours(), scheduleSymbol.getTimeIn().getMinutes(), 0));
                                   }
                                   if(ix==diffDay-1){
                                        dtTakenFinishDate = (new Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate(), dpStockTaken.getTakenFinnishDate().getHours(), dpStockTaken.getTakenFinnishDate().getMinutes(), 0));
                                   }else{
                                       dtTakenFinishDate = (new Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate(), scheduleSymbol.getTimeOut().getHours(), scheduleSymbol.getTimeOut().getMinutes(), 0));
                                   }
                               }
                                dpStockTaken.addvTakenDate(dtTakendate);
                                dpStockTaken.addvTakenFinishDate(dtTakenFinishDate);
                            }

                          
                        }
                    }
                    dpQty = diffDay - dayOffSchedule;
                } else {
                    dpQty = SessLeaveApplication.DATEDIFF(dpStockTaken.getTakenFinnishDate(), dpStockTaken.getTakenDate());
                }
                //update by satrya 2013-02-24
                long periodId = PstPeriod.getPeriodIdBySelectedDateString(Formater.formatDate(dpStockTaken.getTakenDate(), "yyyy-MM-dd"));
                ScheduleSymbol scheduleSymbol = PstEmpSchedule.getEmpScheduleDateTime(periodId, dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), 0);

                if (scheduleSymbol != null && scheduleSymbol.getTimeIn() != null && scheduleSymbol.getTimeOut() != null) {
                    Date taken = dpStockTaken.getTakenDate();
                    taken.setHours(scheduleSymbol.getTimeIn().getHours());
                    taken.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                    Date finish = dpStockTaken.getTakenFinnishDate();
                    finish.setHours(scheduleSymbol.getTimeOut().getHours());
                    finish.setMinutes(scheduleSymbol.getTimeOut().getMinutes());

                    dpStockTaken.setTakenDate(taken);
                    dpStockTaken.setTakenFinnishDate(finish);
                    //update by satrya 2014-04-18
                    //karena jika dia off maka tidak bisa save
                    dpStockTaken.addvTakenDate(taken);
                    dpStockTaken.addvTakenFinishDate(finish);
                } else {
                    //jika schedulenya bernilai null maka di set defautl 8
                    Date taken = dpStockTaken.getTakenDate();
                    taken.setHours(8);
                    taken.setMinutes(0);
                    taken.setSeconds(0);
                    Date finish = dpStockTaken.getTakenFinnishDate();
                    finish.setHours(8);
                    finish.setMinutes(0);
                    finish.setSeconds(0);
                    dpStockTaken.setTakenDate(taken);
                    dpStockTaken.setTakenFinnishDate(finish);
                    //update by satrya 2014-04-18
                    //karena jika dia off maka tidak bisa save
                    dpStockTaken.addvTakenDate(taken);
                    dpStockTaken.addvTakenFinishDate(finish);
                }
                //update by satrya 2013-12-12
                if (leaveConfigCalculationCategoryOff) {
                    dpStockTaken.setTakenQty(dpQty);
                } else {
                    dpStockTaken.setTakenQty(dpQty + 1);
                }

            } else {
                // jika tidak : maka cuti jam-an ( 30 menit resolusi )  maximum satu hari 
                // alStockTaken.setTakenFinnishDate(alStockTaken.getTakenDate());
                // update by satrya 2013-12-12 float dpQty = DateCalc.workDayDifference(dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate(), 8f);

                float dpQty = 0;
                //jika ingin menggunakan konfigurasi, dihitung cutinya tidak dengan dayoff
                if (leaveConfigCalculationCategoryOff && leaveConfig != null && leaveConfig.getConfigurationLeaveApprovall() == I_Leave.LEAVE_CONFIG_REQUEST_DP_TAKEN_YES_LONG) {
                    int diffDay = SessLeaveApplication.DATEDIFF(dpStockTaken.getTakenFinnishDate(), dpStockTaken.getTakenDate()) + 1;//kenapa di tambah 1 karena belum di hitung start datenya
                    float alQtyTime = DateCalc.workDayDifference(dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate(), 8f);
                    //
                    //update by satrya 2013-12-11
                    //untuk mencari hari libur
                    Vector vctSchIDOff = PstScheduleSymbol.getScheduleId(PstScheduleCategory.CATEGORY_OFF);
                    float dayOffSchedule = 0;
                   
                    if (dpStockTaken.getTakenDate() != null) {
                        for (int ix = 0; ix < diffDay; ix++) {
                            Date selectedDate = new Date(dpStockTaken.getTakenDate().getYear(), dpStockTaken.getTakenDate().getMonth(), (dpStockTaken.getTakenDate().getDate() + ix));
                            Period period = PstPeriod.getPeriodBySelectedDate(selectedDate);
                            dayOffSchedule = dayOffSchedule + PstEmpSchedule.getStatusDayOff(dpStockTaken.getEmployeeId(), period.getOID(), 0, vctSchIDOff, selectedDate);

                            int dayOffSchedules = PstEmpSchedule.getStatusDayOff(dpStockTaken.getEmployeeId(), period.getOID(), 0, vctSchIDOff, selectedDate);
                            if (dayOffSchedules == 0) {
                                ScheduleSymbol scheduleSymbol = PstEmpSchedule.getEmpScheduleDateTime(period.getOID(), dpStockTaken.getEmployeeId(), selectedDate, 0);
                                Date dtTakenDate=null;
                                Date dtTakenFinishDate=null;
                                
                               if(dpStockTaken.getFlagFullSchedule()==1){
                                dtTakenDate =(new Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate(), scheduleSymbol.getTimeIn().getHours(), scheduleSymbol.getTimeIn().getMinutes(), 0));
                                dtTakenFinishDate = (new Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate(), scheduleSymbol.getTimeOut().getHours(), scheduleSymbol.getTimeOut().getMinutes(), 0));
                               }else{
                                   if(ix==0){
                                       dtTakenDate =(new Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate(), dpStockTaken.getTakenDate().getHours(), dpStockTaken.getTakenDate().getMinutes(), 0));
                                   }else{
                                       dtTakenDate =(new Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate(), scheduleSymbol.getTimeIn().getHours(), scheduleSymbol.getTimeIn().getMinutes(), 0));
                                   }
                                   if(ix==diffDay-1){
                                        dtTakenFinishDate = (new Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate(), dpStockTaken.getTakenFinnishDate().getHours(), dpStockTaken.getTakenFinnishDate().getMinutes(), 0));
                                   }else{
                                       dtTakenFinishDate = (new Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate(), scheduleSymbol.getTimeOut().getHours(), scheduleSymbol.getTimeOut().getMinutes(), 0));
                                   }
                               }
                                /*if (ix != 0) {
                                    dtTakenDate =(new Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate(), scheduleSymbol.getTimeIn().getHours(), scheduleSymbol.getTimeIn().getMinutes(), 0));
                                }else{
                                    dtTakenDate = dpStockTaken.getTakenDate();
                                }
                                if (ix != diffDay - 1) {
                                    dtTakenFinishDate = (new Date(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDate(), scheduleSymbol.getTimeOut().getHours(), scheduleSymbol.getTimeOut().getMinutes(), 0));
                                }else{
                                    dtTakenFinishDate = dpStockTaken.getTakenFinnishDate();
                                }*/
                               
                                dpStockTaken.addvTakenDate(dtTakenDate);
                                dpStockTaken.addvTakenFinishDate(dtTakenFinishDate);
                            }
                          
                        }
                    }
                    dpQty = alQtyTime - dayOffSchedule;
                } else {
                    dpQty = DateCalc.workDayDifference(dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate(), 8f);
                }

                float intersec = PstEmpSchedule.breakTimeIntersection(dpStockTaken.getEmployeeId(), dpStockTaken.getTakenDate(), dpStockTaken.getTakenFinnishDate()) / (8f * 60f * 60f * 1000f);
                dpStockTaken.setTakenQty(dpQty - intersec);
            }

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

    public Vector<DpStockTaken> requestEntityObjectMultiple() {
        try {
            this.requestParam();

            String userSelect[] = this.getParamsStringValues("userSelect");//request.getParameterValues("userSelect"); 
            if (userSelect != null && userSelect.length > 0) {
                for (int i = 0; i < userSelect.length; i++) {
                    try {
                        long takenDpOid = Long.parseLong((userSelect[i].split("_")[0]));
                        String sPaidDate = String.valueOf((userSelect[i].split("_")[2]));
                        float takenQty = Float.parseFloat((userSelect[i].split("_")[4]));
                        float tmpWillBeTaken = Float.parseFloat((userSelect[i].split("_")[6]));
                        float tmpEligible = Float.parseFloat((userSelect[i].split("_")[8]));
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date dtPaidDate = dateFormat.parse(sPaidDate);

                        DpStockTaken dpStockTkn = new DpStockTaken();
                        dpStockTkn.setOID(0);

                        // dpStockTkn.setEmployeeId(Long.getLong("FRM_FLD_EMPLOYEE_ID"));
                        dpStockTkn.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
                        dpStockTkn.setDpStockId(takenDpOid);
                        dpStockTkn.setTakenDate(getDate(FRM_FIELD_TAKEN_DATE));
                        dpStockTkn.setPaidDate(dtPaidDate);
                        dpStockTkn.setLeaveApplicationId(getLong(FRM_FIELD_LEAVE_APPLICATION_ID));
                        dpStockTkn.setTakenFinnishDate(getDate(FRM_FIELD_TAKEN_DATE));
                        ///update by satrya 2012-08-07
                        //dpStockTaken.setTakenFinnishDate(getDate(FRM_FIELD_TAKEN_DATE));
                        // Kartika : finish date is the same  as taken date only the time will be different            
                        java.util.Date finishTime = getDate(FRM_FIELD_FINNISH_DATE);
                        java.util.Date finishDate = dpStockTkn.getTakenFinnishDate();
                        finishDate.setHours(finishTime.getHours());
                        finishDate.setMinutes(finishTime.getMinutes());
                        finishDate.setSeconds(finishTime.getSeconds());
                        dpStockTkn.setTakenFinnishDate(finishDate);
                        dpStockTkn.setTakenQty(takenQty);
                        dpStockTkn.setTmpWillBeTaken(tmpWillBeTaken);
                        dpStockTkn.setTmpEligible(tmpEligible);

                        getDpTakens().add(dpStockTkn);
                    } catch (Exception ex) {
                        System.out.println("Exception" + ex);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
        return getDpTakens();
    }

    /**
     * @return the dpTakens
     */
    public Vector getDpTakens() {
        return dpTakens;
    }
}
