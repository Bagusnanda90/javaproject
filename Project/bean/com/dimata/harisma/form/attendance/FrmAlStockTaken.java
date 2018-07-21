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
public class FrmAlStockTaken extends FRMHandler implements I_FRMInterface, I_FRMType {

    private AlStockTaken alStockTaken;

    public static final String FRM_AL_STOCK_TAKEN = "FRM_AL_STOCK_TAKEN";

    public static final int FRM_FIELD_AL_STOCK_TAKEN_ID = 0;
    public static final int FRM_FIELD_EMPLOYEE_ID = 1;
    public static final int FRM_FIELD_AL_STOCK_ID = 2;
    public static final int FRM_FIELD_TAKEN_DATE = 3;
    public static final int FRM_FIELD_TAKEN_QTY = 4;
    public static final int FRM_FIELD_PAID_DATE = 5;
    public static final int FRM_FIELD_TAKEN_FROM_STATUS = 6;
    public static final int FRM_FIELD_LEAVE_APPLICATION_ID = 7;
    public static final int FRM_FIELD_FINNISH_DATE = 8;

    public static String[] fieldNames = {
        "FRM_FIELD_AL_STOCK_TAKEN_ID",
        "FRM_FLD_EMPLOYEE_ID",
        "FRM_FIELD_AL_STOCK_ID",
        "FRM_FIELD_AL_TAKEN_DATE",
        "FRM_FIELD_AL_TAKEN_QTY",
        "FRM_FIELD_AL_PAID_DATE",
        "FRM_FIELD_AL_TAKEN_FROM_STATUS",
        "FRM_FLD_LEAVE_APPLICATION_ID",
        "FRM_FIELD_AL_FINNISH_DATE"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_DATE + ENTRY_REQUIRED,
        TYPE_FLOAT,
        TYPE_DATE,
        TYPE_INT,
        TYPE_LONG + ENTRY_REQUIRED,
        TYPE_DATE + ENTRY_REQUIRED
    };

    public static final String TIME = "_time";

    /**
     * Creates a new instance of FrmAlStockTaken
     */
    public FrmAlStockTaken() {
    }

    public FrmAlStockTaken(AlStockTaken alStockTaken) {
        this.alStockTaken = alStockTaken;
    }

    public FrmAlStockTaken(HttpServletRequest request, AlStockTaken alStockTaken) {
        super(new FrmAlStockTaken(alStockTaken), request);
        this.alStockTaken = alStockTaken;
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
        return FRM_AL_STOCK_TAKEN;
    }

    public AlStockTaken getEntityObject() {
        return alStockTaken;
    }

    public void requestEntityObject(AlStockTaken alStockTaken) {
        try {
            this.requestParam();
            alStockTaken.setOID(getLong(FRM_FIELD_AL_STOCK_TAKEN_ID));
            alStockTaken.setEmployeeId(getLong(FRM_FIELD_EMPLOYEE_ID));
            alStockTaken.setAlStockId(getLong(FRM_FIELD_AL_STOCK_ID));
            alStockTaken.setTakenDate(getDate(FRM_FIELD_TAKEN_DATE));
            alStockTaken.setPaidDate(getDate(FRM_FIELD_PAID_DATE));
            alStockTaken.setTakenFromStatus(getInt(FRM_FIELD_TAKEN_FROM_STATUS));
            alStockTaken.setLeaveApplicationId(getLong(FRM_FIELD_LEAVE_APPLICATION_ID));
            alStockTaken.setTakenFinnishDate(getDate(FRM_FIELD_FINNISH_DATE));
            //int alQty = (int)(alStockTaken.getTakenFinnishDate().getTime() - alStockTaken.getTakenDate().getTime())/
            //             (24*60*60*1000);  
            
            boolean leaveConfigCalculationCategoryOff = false;
            try{
                 leaveConfigCalculationCategoryOff = Boolean.parseBoolean(PstSystemProperty.getValueByName("LEAVE_CONFIG_CALCULATE_CATEGORY_SCHEDULE_OFF"));
            }catch(Exception exc){
                System.out.println("Exc FrmAlStockTaken"+exc);
            }
            if (alStockTaken.getTakenFinnishDate() != null && alStockTaken.getTakenDate() != null
                    && (alStockTaken.getTakenFinnishDate().getTime() < alStockTaken.getTakenDate().getTime())) {
                // tanggal from lebih besar dr tgl to >> maka set tanggal to = tanggal from
                /*Date dTemp = new Date(alStockTaken.getTakenDate().getTime());
                dTemp.setHours(alStockTaken.getTakenFinnishDate().getHours());
                dTemp.setMinutes(alStockTaken.getTakenFinnishDate().getMinutes());
                alStockTaken.setTakenFinnishDate(dTemp);*/
                //update by satrya 2014-01-29
                Date tempFromDate = alStockTaken.getTakenDate();
                Date tempToDate = alStockTaken.getTakenFinnishDate();

                alStockTaken.setTakenFinnishDate(tempFromDate);
                alStockTaken.setTakenDate(tempToDate);
                
            }

            if (alStockTaken.getTakenDate().getHours() == alStockTaken.getTakenFinnishDate().getHours()
                    && alStockTaken.getTakenDate().getMinutes() == alStockTaken.getTakenFinnishDate().getMinutes()) {
                //update by satrya 2013-02-24
                long periodId = PstPeriod.getPeriodIdBySelectedDateString(Formater.formatDate(alStockTaken.getTakenDate(), "yyyy-MM-dd"));
                ScheduleSymbol scheduleSymbol = PstEmpSchedule.getEmpScheduleDateTime(periodId, alStockTaken.getEmployeeId(), alStockTaken.getTakenDate(), 0);
 
                // jika jam dan menit sama berarti cuti dalam hitungan hari kerja
                float alQty = 0;
                //jika ingin menggunakan konfigurasi, dihitung cutinya tidak dengan dayoff
                if (leaveConfigCalculationCategoryOff) {
                    int diffDay = SessLeaveApplication.DATEDIFF(alStockTaken.getTakenFinnishDate(), alStockTaken.getTakenDate()) + 1;//kenapa di tambah 1 karena belum di hitung start datenya
                    //
                    //update by satrya 2013-12-11
                    //untuk mencari hari libur
                    Vector vctSchIDOff = PstScheduleSymbol.getScheduleId(PstScheduleCategory.CATEGORY_OFF);
                    int dayOffSchedule = 0;
                    if (alStockTaken.getTakenDate() != null) {
                        for (int ix = 0; ix < diffDay; ix++) {
                            Date selectedDate = new Date(alStockTaken.getTakenDate().getYear(), alStockTaken.getTakenDate().getMonth(), (alStockTaken.getTakenDate().getDate() + ix));
                            Period period = PstPeriod.getPeriodBySelectedDate(selectedDate);
                            dayOffSchedule = dayOffSchedule + PstEmpSchedule.getStatusDayOff(alStockTaken.getEmployeeId(), period.getOID(), 0, vctSchIDOff, selectedDate);
                        }
                    }
                    alQty = diffDay - dayOffSchedule;
                } else {
                    alQty = SessLeaveApplication.DATEDIFF(alStockTaken.getTakenFinnishDate(), alStockTaken.getTakenDate());
                }
                // update by satrya 2013-12-11 float alQty = SessLeaveApplication.DATEDIFF(alStockTaken.getTakenFinnishDate(), alStockTaken.getTakenDate());
                //update by satrya 2013-02-24
                if (scheduleSymbol != null && scheduleSymbol.getTimeIn() != null && scheduleSymbol.getTimeOut() != null) {
                    Date taken = alStockTaken.getTakenDate();
                    taken.setHours(scheduleSymbol.getTimeIn().getHours());
                    taken.setMinutes(scheduleSymbol.getTimeIn().getMinutes());
                    Date finish = alStockTaken.getTakenFinnishDate();
                    //jika schedulenya cross days
                    if (scheduleSymbol.getTimeOut().getHours() < scheduleSymbol.getTimeIn().getHours()) {
                        finish = new Date(finish.getTime() + 24 * 60 * 60 * 1000);
                    }

                    finish.setHours(scheduleSymbol.getTimeOut().getHours());
                    finish.setMinutes(scheduleSymbol.getTimeOut().getMinutes());

                    alStockTaken.setTakenDate(taken);
                    alStockTaken.setTakenFinnishDate(finish);
                } else {
                    //jika schedulenya bernilai null maka di set defautl 8
                    Date taken = alStockTaken.getTakenDate();
                    taken.setHours(8);
                    taken.setMinutes(0);
                    taken.setSeconds(0);
                    Date finish = alStockTaken.getTakenFinnishDate();
                    finish.setHours(8);
                    finish.setMinutes(0);
                    finish.setSeconds(0);
                    alStockTaken.setTakenDate(taken);
                    alStockTaken.setTakenFinnishDate(finish);
                }
                // update by satrya 2013-12-12 alStockTaken.setTakenQty(alQty+1);  
                //jika ingin menggunakan konfigurasi, dihitung cutinya tidak dengan dayoff
                if (leaveConfigCalculationCategoryOff) {
                    alStockTaken.setTakenQty(alQty);
                } else {
                    alStockTaken.setTakenQty(alQty + 1);
                }
            } else {
                // jika tidak : maka cuti jam-an ( 30 menit resolusi )  maximum satu hari 
                // alStockTaken.setTakenFinnishDate(alStockTaken.getTakenDate());
                // update by satrya 2013-12-12 float alQty = DateCalc.workDayDifference(alStockTaken.getTakenDate(), alStockTaken.getTakenFinnishDate(), 8f);
                float alQty = 0;
                //jika ingin menggunakan konfigurasi, dihitung cutinya tidak dengan dayoff
                if (leaveConfigCalculationCategoryOff) {
                    int diffDay = SessLeaveApplication.DATEDIFF(alStockTaken.getTakenFinnishDate(), alStockTaken.getTakenDate()) + 1;//kenapa di tambah 1 karena belum di hitung start datenya
                    float alQtyTime = DateCalc.workDayDifference(alStockTaken.getTakenDate(), alStockTaken.getTakenFinnishDate(), 8f);
                    //
                    //update by satrya 2013-12-11
                    //untuk mencari hari libur
                    Vector vctSchIDOff = PstScheduleSymbol.getScheduleId(PstScheduleCategory.CATEGORY_OFF);
                    float dayOffSchedule = 0;
                    if (alStockTaken.getTakenDate() != null) {
                        for (int ix = 0; ix < diffDay; ix++) {
                            Date selectedDate = new Date(alStockTaken.getTakenDate().getYear(), alStockTaken.getTakenDate().getMonth(), (alStockTaken.getTakenDate().getDate() + ix));
                            Period period = PstPeriod.getPeriodBySelectedDate(selectedDate);
                            dayOffSchedule = dayOffSchedule + PstEmpSchedule.getStatusDayOff(alStockTaken.getEmployeeId(), period.getOID(), 0, vctSchIDOff, selectedDate);
                        }
                    }
                    alQty = alQtyTime - dayOffSchedule;
                } else {
                    alQty = DateCalc.workDayDifference(alStockTaken.getTakenDate(), alStockTaken.getTakenFinnishDate(), 8f);
                }
                float intersec = PstEmpSchedule.breakTimeIntersection(alStockTaken.getEmployeeId(), alStockTaken.getTakenDate(), alStockTaken.getTakenFinnishDate()) / (8f * 60f * 60f * 1000f);
                alStockTaken.setTakenQty(alQty - intersec);
            }

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
