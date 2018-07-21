/*
 * FrmAlStockTaken.java
 *
 * Created on September 10, 2007, 5:14 PM
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
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.session.leave.SessLeaveApplication;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.DateCalc;
import com.dimata.util.Formater;

/**
 *
 * @author yunny
 */
public class FrmLlStockTaken extends FRMHandler implements I_FRMInterface, I_FRMType {

    private LlStockTaken llStockTaken;

    public static final String FRM_LL_STOCK_TAKEN = "FRM_LL_STOCK_TAKEN";

    public static final int FRM_FIELD_LL_STOCK_TAKEN_ID = 0;
    public static final int FRM_FIELD_EMPLOYEE_ID = 1;
    public static final int FRM_FIELD_LL_STOCK_ID = 2;
    public static final int FRM_FIELD_TAKEN_DATE = 3;
    public static final int FRM_FIELD_TAKEN_QTY = 4;
    public static final int FRM_FIELD_PAID_DATE = 5;
    public static final int FRM_FIELD_TAKEN_FROM_STATUS = 6;
    public static final int FRM_FIELD_LEAVE_APPLICATION_ID = 7;
    public static final int FRM_FIELD_FINNISH_DATE = 8;

    public static String[] fieldNames = {
        "FRM_FIELD_LL_STOCK_TAKEN_ID",
        "FRM_FLD_EMPLOYEE_ID",
        "FRM_FIELD_LL_STOCK_ID",
        "FRM_FIELD_LL_TAKEN_DATE",
        "FRM_FIELD_LL_TAKEN_QTY",
        "FRM_FIELD_LL_PAID_DATE",
        "FRM_FIELD_LL_TAKEN_FROM_STATUS",
        "FRM_FLD_LEAVE_APPLICATION_ID",
        "FRM_FIELD_LL_FINNISH_DATE"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT,
        TYPE_DATE,
        TYPE_INT,
        TYPE_LONG,
        TYPE_DATE
    };

    /**
     * Creates a new instance of FrmAlStockTaken
     */
    public FrmLlStockTaken() {
    }

    public FrmLlStockTaken(LlStockTaken llStockTaken) {
        this.llStockTaken = llStockTaken;
    }

    public FrmLlStockTaken(HttpServletRequest request, LlStockTaken llStockTaken) {
        super(new FrmLlStockTaken(llStockTaken), request);
        this.llStockTaken = llStockTaken;
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
        return FRM_LL_STOCK_TAKEN;
    }

    public LlStockTaken getEntityObject() {
        return llStockTaken;
    }

    public void requestEntityObject(LlStockTaken llStockTaken) {
        try {
            this.requestParam();
            llStockTaken.setOID(getLong(FRM_FIELD_LL_STOCK_TAKEN_ID));
            llStockTaken.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            llStockTaken.setLlStockId(getLong(FRM_FIELD_LL_STOCK_ID));
            llStockTaken.setTakenDate(getDate(FRM_FIELD_TAKEN_DATE));
            llStockTaken.setPaidDate(getDate(FRM_FIELD_PAID_DATE));
            llStockTaken.setTakenFromStatus(getInt(FRM_FIELD_TAKEN_FROM_STATUS));
            llStockTaken.setLeaveApplicationId(getLong(FRM_FIELD_LEAVE_APPLICATION_ID));
            llStockTaken.setTakenFinnishDate(getDate(FRM_FIELD_FINNISH_DATE));
            //update by satrya 2013-12-12
            boolean leaveConfigCalculationCategoryOff = false;
            try {
                leaveConfigCalculationCategoryOff = Boolean.parseBoolean(PstSystemProperty.getValueByName("LEAVE_CONFIG_CALCULATE_CATEGORY_SCHEDULE_OFF"));
            } catch (Exception exc) {
                System.out.println("Exc FrmAlStockTaken" + exc);
            }
            /*            if(llStockTaken.getTakenFinnishDate()!=null && llStockTaken.getTakenDate()!=null && 
             (llStockTaken.getTakenFinnishDate().getTime()< llStockTaken.getTakenDate().getTime()) ){
             // tanggal from lebih besar dr tgl to >> maka set tanggal to = tanggal from
             Date dTemp = new Date(llStockTaken.getTakenDate().getTime());                    
             dTemp.setHours(llStockTaken.getTakenFinnishDate().getHours());
             dTemp.setMinutes(llStockTaken.getTakenFinnishDate().getMinutes());
             llStockTaken.setTakenFinnishDate(dTemp);
             }
                        
             //int llQty = (int)(llStockTaken.getTakenFinnishDate().getTime() - llStockTaken.getTakenDate().getTime())/
             //             (24*60*60*1000);            
             int llQty = SessLeaveApplication.DATEDIFF(llStockTaken.getTakenFinnishDate(), llStockTaken.getTakenDate());
             llStockTaken.setTakenQty(llQty+1);  */
            if (llStockTaken.getTakenFinnishDate() != null && llStockTaken.getTakenDate() != null
                    && (llStockTaken.getTakenFinnishDate().getTime() < llStockTaken.getTakenDate().getTime())) {
                // tanggal from lebih besar dr tgl to >> maka set tanggal to = tanggal from
                /*Date dTemp = new Date(llStockTaken.getTakenDate().getTime());
                dTemp.setHours(llStockTaken.getTakenFinnishDate().getHours());
                dTemp.setMinutes(llStockTaken.getTakenFinnishDate().getMinutes());
                llStockTaken.setTakenFinnishDate(dTemp);*/
                //update by satrya 2014-01-29
                Date tempFromDate = llStockTaken.getTakenDate();
                Date tempToDate = llStockTaken.getTakenFinnishDate();

                llStockTaken.setTakenFinnishDate(tempFromDate);
                llStockTaken.setTakenDate(tempToDate);
                
            }

            if (llStockTaken.getTakenDate().getHours() == llStockTaken.getTakenFinnishDate().getHours()
                    && llStockTaken.getTakenDate().getMinutes() == llStockTaken.getTakenFinnishDate().getMinutes()) {
                //update by satrya 2013-02-24
                long periodId = PstPeriod.getPeriodIdBySelectedDateString(Formater.formatDate(llStockTaken.getTakenDate(), "yyyy-MM-dd"));
                ScheduleSymbol scheduleSymbol = PstEmpSchedule.getEmpScheduleDateTime(periodId, llStockTaken.getEmployeeId(), llStockTaken.getTakenDate(), 0);

                // jika jam dan menit sama berarti cuti dalam hitungan hari kerja
                // update by satrya 2013-12-12 float alQty = SessLeaveApplication.DATEDIFF(llStockTaken.getTakenFinnishDate(), llStockTaken.getTakenDate());
                //update by satrya 2013-12-12  float dpQty = SessLeaveApplication.DATEDIFF(dpStockTaken.getTakenFinnishDate(), dpStockTaken.getTakenDate());
                float llQty = 0;
                //jika ingin menggunakan konfigurasi, dihitung cutinya tidak dengan dayoff
                if (leaveConfigCalculationCategoryOff) {
                    int diffDay = SessLeaveApplication.DATEDIFF(llStockTaken.getTakenFinnishDate(), llStockTaken.getTakenDate()) + 1;//kenapa di tambah 1 karena belum di hitung start datenya
                    //untuk mencari hari libur
                    Vector vctSchIDOff = PstScheduleSymbol.getScheduleId(PstScheduleCategory.CATEGORY_OFF);
                    int dayOffSchedule = 0;
                    if (llStockTaken.getTakenDate() != null) {
                        for (int ix = 0; ix < diffDay; ix++) {
                           try{
                            Date selectedDate = new Date(llStockTaken.getTakenDate().getYear(), llStockTaken.getTakenDate().getMonth(), (llStockTaken.getTakenDate().getDate() + ix));
                            Period period = PstPeriod.getPeriodBySelectedDate(selectedDate);
                            dayOffSchedule = dayOffSchedule + PstEmpSchedule.getStatusDayOff(llStockTaken.getEmployeeId(), period.getOID(), 0, vctSchIDOff, selectedDate);
                           }catch(Exception exc){
                               System.out.println("Exc Period"+exc);
                               
                           }
                        }
                    }
                    llQty = diffDay - dayOffSchedule;
                } else {
                    llQty = SessLeaveApplication.DATEDIFF(llStockTaken.getTakenFinnishDate(), llStockTaken.getTakenDate());
                }
                //update by satrya 2013-02-24
                if (scheduleSymbol != null && scheduleSymbol.getTimeIn() != null && scheduleSymbol.getTimeOut() != null) {
                    Date taken = llStockTaken.getTakenDate();
                    taken.setHours(scheduleSymbol.getTimeIn().getHours());
                    taken.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                    Date finish = llStockTaken.getTakenFinnishDate();
                    finish.setHours(scheduleSymbol.getTimeOut().getHours());
                    finish.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                    //update by satrya 2014-01-17
                    //jika taken date'nya lebih besar dari finish date bearti kemungkinan dia cross day
                    if(taken.getTime()>finish.getTime()){
                        finish = new Date(finish.getTime() + 24*60*60*1000);
                    }
                    llStockTaken.setTakenDate(taken);
                    llStockTaken.setTakenFinnishDate(finish);
                } else {
                    //jika schedulenya bernilai null maka di set defautl 8
                    Date taken = llStockTaken.getTakenDate();
                    taken.setHours(8);
                    taken.setMinutes(0);
                    taken.setSeconds(0);
                    Date finish = llStockTaken.getTakenFinnishDate();
                    finish.setHours(8);
                    finish.setMinutes(0);
                    finish.setSeconds(0);
                    llStockTaken.setTakenDate(taken);
                    llStockTaken.setTakenFinnishDate(finish);
                }
                if (leaveConfigCalculationCategoryOff) {
                    llStockTaken.setTakenQty(llQty);
                } else {
                    llStockTaken.setTakenQty(llQty + 1);
                }

            } else {
                // jika tidak : maka cuti jam-an ( 30 menit resolusi )  maximum satu hari 
                // alStockTaken.setTakenFinnishDate(alStockTaken.getTakenDate());
                // update by satrya 2013-12-12 float alQty = DateCalc.workDayDifference(llStockTaken.getTakenDate(), llStockTaken.getTakenFinnishDate(), 8f);
                float llQty = 0;
                //jika ingin menggunakan konfigurasi, dihitung cutinya tidak dengan dayoff
                if (leaveConfigCalculationCategoryOff) {
                    int diffDay = SessLeaveApplication.DATEDIFF(llStockTaken.getTakenFinnishDate(), llStockTaken.getTakenDate()) + 1;//kenapa di tambah 1 karena belum di hitung start datenya
                    float llQtyTime = DateCalc.workDayDifference(llStockTaken.getTakenDate(), llStockTaken.getTakenFinnishDate(), 8f);
                    //
                    //update by satrya 2013-12-11
                    //untuk mencari hari libur
                    Vector vctSchIDOff = PstScheduleSymbol.getScheduleId(PstScheduleCategory.CATEGORY_OFF);
                    float dayOffSchedule = 0;
                    if (llStockTaken.getTakenDate() != null) {
                        for (int ix = 0; ix < diffDay; ix++) {
                            Date selectedDate = new Date(llStockTaken.getTakenDate().getYear(), llStockTaken.getTakenDate().getMonth(), (llStockTaken.getTakenDate().getDate() + ix));
                            Period period = PstPeriod.getPeriodBySelectedDate(selectedDate);
                            dayOffSchedule = dayOffSchedule + PstEmpSchedule.getStatusDayOff(llStockTaken.getEmployeeId(), period.getOID(), 0, vctSchIDOff, selectedDate);
                        }
                    }
                    llQty = llQtyTime - dayOffSchedule;
                } else {
                    llQty = DateCalc.workDayDifference(llStockTaken.getTakenDate(), llStockTaken.getTakenFinnishDate(), 8f);
                }

                float intersec = PstEmpSchedule.breakTimeIntersection(llStockTaken.getEmployeeId(), llStockTaken.getTakenDate(), llStockTaken.getTakenFinnishDate()) / (8f * 60f * 60f * 1000f);
                llStockTaken.setTakenQty(llQty - intersec);
            }

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
