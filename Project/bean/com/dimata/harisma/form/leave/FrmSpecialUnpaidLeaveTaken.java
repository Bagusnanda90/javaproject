/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.leave;

/**
 *
 * @author Tu Roy
 */
import javax.servlet.http.*;

/* qdep package */
import com.dimata.qdep.form.*;

/* project package */
import com.dimata.harisma.entity.leave.*;
import com.dimata.harisma.session.leave.SessLeaveApplication;
import com.dimata.util.DateCalc;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Formater;
import java.util.Date;
import java.util.Vector;

public class FrmSpecialUnpaidLeaveTaken extends FRMHandler implements I_FRMInterface, I_FRMType {

    private SpecialUnpaidLeaveTaken objSpecialUnpaidLeaveTaken;

    public static final String FRM_SPECIAL_UNPAID_LEAVE_TAKEN = "FRM_SPECIAL_UNPAID_LEAVE_TAKEN";

    public static final int FRM_FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID = 0;
    public static final int FRM_FLD_LEAVE_APLICATION_ID = 1;
    public static final int FRM_FLD_SCHEDULED_ID = 2;
    public static final int FRM_FLD_EMPLOYEE_ID = 3;
    public static final int FRM_FLD_TAKEN_DATE = 4;
    public static final int FRM_FLD_TAKEN_QTY = 5;
    public static final int FRM_FLD_TAKEN_STATUS = 6;
    public static final int FRM_FLD_TAKEN_FROM_STATUS = 7;
    public static final int FRM_FLD_TAKEN_FINNISH_DATE = 8;

    public static String[] fieldNames = {
        "FRM_FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID",
        "FRM_FLD_LEAVE_APPLICATION_ID",
        "FRM_FLD_SCHEDULED_ID",
        "FRM_FLD_EMPLOYEE_ID",
        "FRM_FLD_TAKEN_DATE_US",
        "FRM_FLD_TAKEN_QTY_US",
        "FRM_FLD_TAKEN_STATUS_US",
        "FRM_FLD_TAKEN_FROM_STATUS_US",
        "FRM_FLD_TAKEN_FINNISH_DATE_US"
    };

    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_DATE
    };

    public FrmSpecialUnpaidLeaveTaken() {
    }

    public FrmSpecialUnpaidLeaveTaken(SpecialUnpaidLeaveTaken objSpecialUnpaidLeaveTaken) {
        this.objSpecialUnpaidLeaveTaken = objSpecialUnpaidLeaveTaken;
    }

    public FrmSpecialUnpaidLeaveTaken(HttpServletRequest request, SpecialUnpaidLeaveTaken objSpecialUnpaidLeaveTaken) {
        super(new FrmSpecialUnpaidLeaveTaken(objSpecialUnpaidLeaveTaken), request);
        this.objSpecialUnpaidLeaveTaken = objSpecialUnpaidLeaveTaken;
    }

    public String getFormName() {
        return FRM_SPECIAL_UNPAID_LEAVE_TAKEN;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public SpecialUnpaidLeaveTaken getEntityObject() {
        return objSpecialUnpaidLeaveTaken;
    }

    public void requestEntityObject(SpecialUnpaidLeaveTaken objSpecialUnpaidLeaveTakenNew) {
        try {

            this.requestParam();
            objSpecialUnpaidLeaveTakenNew.setOID(getLong(FRM_FLD_SPECIAL_UNPAID_LEAVE_TAKEN_ID));
            objSpecialUnpaidLeaveTakenNew.setLeaveApplicationId(getLong(FRM_FLD_LEAVE_APLICATION_ID));
            objSpecialUnpaidLeaveTakenNew.setScheduledId(getLong(FRM_FLD_SCHEDULED_ID));
            objSpecialUnpaidLeaveTakenNew.setTakenDate(getDate(FRM_FLD_TAKEN_DATE));
            objSpecialUnpaidLeaveTakenNew.setTakenFromStatus(getInt(FRM_FLD_TAKEN_FROM_STATUS));
            //objSpecialUnpaidLeaveTaken.setTakenQty(getInt(FRM_FLD_TAKEN_QTY));
            objSpecialUnpaidLeaveTakenNew.setTakenStatus(getInt(FRM_FLD_TAKEN_STATUS));
            objSpecialUnpaidLeaveTakenNew.setEmployeeId(getLong(FRM_FLD_EMPLOYEE_ID));
            objSpecialUnpaidLeaveTakenNew.setTakenFinnishDate(getDate(FRM_FLD_TAKEN_FINNISH_DATE));

            //update by satrya 2013-12-12
            boolean leaveConfigCalculationCategoryOff = false;
            try {
                leaveConfigCalculationCategoryOff = Boolean.parseBoolean(PstSystemProperty.getValueByName("LEAVE_CONFIG_CALCULATE_CATEGORY_SCHEDULE_OFF"));
            } catch (Exception exc) {
                System.out.println("Exc FrmAlStockTaken" + exc);
            }
            if (objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate() != null && objSpecialUnpaidLeaveTakenNew.getTakenDate() != null
                    && (objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate().getTime() < objSpecialUnpaidLeaveTakenNew.getTakenDate().getTime())) {
                // tanggal from lebih besar dr tgl to >> maka set tanggal to = tanggal from
                //update by satrya 2014-01-29
                Date tempFromDate = objSpecialUnpaidLeaveTakenNew.getTakenDate();
                Date tempToDate = objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate();

                objSpecialUnpaidLeaveTakenNew.setTakenFinnishDate(tempFromDate);
                objSpecialUnpaidLeaveTakenNew.setTakenDate(tempToDate);
                /* update by satrya 2014-01-29 Date dTemp = new Date(objSpecialUnpaidLeaveTakenNew.getTakenDate().getTime());
                dTemp.setHours(objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate().getHours());
                dTemp.setMinutes(objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate().getMinutes());
                objSpecialUnpaidLeaveTakenNew.setTakenFinnishDate(dTemp);*/
            }

            if (objSpecialUnpaidLeaveTakenNew.getTakenDate().getHours() == objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate().getHours()
                    && objSpecialUnpaidLeaveTakenNew.getTakenDate().getMinutes() == objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate().getMinutes()) { // jika jam dan menit sama berarti cuti dalam hitungan hari kerja

                // update by satrya 2013-12-12 float alQty = SessLeaveApplication.DATEDIFF(objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate(), objSpecialUnpaidLeaveTakenNew.getTakenDate());
                float SpecialQty = 0;
                //jika ingin menggunakan konfigurasi, dihitung cutinya tidak dengan dayoff
                if (leaveConfigCalculationCategoryOff) {
                    int diffDay = SessLeaveApplication.DATEDIFF(objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate(), objSpecialUnpaidLeaveTakenNew.getTakenDate()) + 1;//kenapa di tambah 1 karena belum di hitung start datenya
                    //untuk mencari hari libur
                    Vector vctSchIDOff = PstScheduleSymbol.getScheduleId(PstScheduleCategory.CATEGORY_OFF);
                    int dayOffSchedule = 0;
                    if (objSpecialUnpaidLeaveTakenNew.getTakenDate() != null) {
                        for (int ix = 0; ix < diffDay; ix++) {
                            Date selectedDate = new Date(objSpecialUnpaidLeaveTakenNew.getTakenDate().getYear(), objSpecialUnpaidLeaveTakenNew.getTakenDate().getMonth(), (objSpecialUnpaidLeaveTakenNew.getTakenDate().getDate() + ix));
                            Period period = PstPeriod.getPeriodBySelectedDate(selectedDate);
                            dayOffSchedule = dayOffSchedule + PstEmpSchedule.getStatusDayOff(objSpecialUnpaidLeaveTakenNew.getEmployeeId(), period.getOID(), 0, vctSchIDOff, selectedDate);
                        }
                    }
                    SpecialQty = diffDay - dayOffSchedule;
                } else {
                    SpecialQty = SessLeaveApplication.DATEDIFF(objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate(), objSpecialUnpaidLeaveTakenNew.getTakenDate());
                }
                //update by satrya 2013-02-24
                long periodId = PstPeriod.getPeriodIdBySelectedDateString(Formater.formatDate(objSpecialUnpaidLeaveTakenNew.getTakenDate(), "yyyy-MM-dd"));
                ScheduleSymbol scheduleSymbol = PstEmpSchedule.getEmpScheduleDateTime(periodId, objSpecialUnpaidLeaveTakenNew.getEmployeeId(), objSpecialUnpaidLeaveTakenNew.getTakenDate(), 0);

                if (scheduleSymbol != null && scheduleSymbol.getTimeIn() != null && scheduleSymbol.getTimeOut() != null) {
                    Date taken = objSpecialUnpaidLeaveTakenNew.getTakenDate();
                    taken.setHours(scheduleSymbol.getTimeIn().getHours());
                    taken.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                    Date finish = objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate();
                    finish.setHours(scheduleSymbol.getTimeOut().getHours());
                    finish.setMinutes(scheduleSymbol.getTimeOut().getMinutes());
                    //update by satrya 2014-01-17
                    //jika taken date'nya lebih besar dari finish date bearti kemungkinan dia cross day
                    if(taken.getTime()>finish.getTime()){
                        finish = new Date(finish.getTime() + 24*60*60*1000);
                    }
                    objSpecialUnpaidLeaveTakenNew.setTakenDate(taken);
                    objSpecialUnpaidLeaveTakenNew.setTakenFinnishDate(finish);
                } else {
                    //jika schedulenya bernilai null maka di set defautl 8
                    Date taken = objSpecialUnpaidLeaveTakenNew.getTakenDate();
                    taken.setHours(8);
                    taken.setMinutes(0);
                    taken.setSeconds(0);
                    Date finish = objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate();
                    finish.setHours(8);
                    finish.setMinutes(0);
                    finish.setSeconds(0);
                    objSpecialUnpaidLeaveTakenNew.setTakenDate(taken);
                    objSpecialUnpaidLeaveTakenNew.setTakenFinnishDate(finish);
                }
                //update by satrya 2013-12-12
                if (leaveConfigCalculationCategoryOff) {
                    objSpecialUnpaidLeaveTakenNew.setTakenQty(SpecialQty);
                } else {
                    //update by satrya 2013-12-12
                    objSpecialUnpaidLeaveTakenNew.setTakenQty(SpecialQty + 1);
                }
            } else {
                // jika tidak : maka cuti jam-an ( 30 menit resolusi )  maximum satu hari 
                // alStockTaken.setTakenFinnishDate(alStockTaken.getTakenDate());

                //update by satrya 2013-12-12 float alQty = DateCalc.workDayDifference(objSpecialUnpaidLeaveTakenNew.getTakenDate(), objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate(), 8f);
                float speacialQty = 0;
                //jika ingin menggunakan konfigurasi, dihitung cutinya tidak dengan dayoff
                if (leaveConfigCalculationCategoryOff) {
                    int diffDay = SessLeaveApplication.DATEDIFF(objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate(), objSpecialUnpaidLeaveTakenNew.getTakenDate()) + 1;//kenapa di tambah 1 karena belum di hitung start datenya
                    float specialQtyTime = DateCalc.workDayDifference(objSpecialUnpaidLeaveTakenNew.getTakenDate(), objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate(), 8f);
                    //
                    //update by satrya 2013-12-11
                    //untuk mencari hari libur
                    Vector vctSchIDOff = PstScheduleSymbol.getScheduleId(PstScheduleCategory.CATEGORY_OFF);
                    float dayOffSchedule = 0;
                    if (objSpecialUnpaidLeaveTakenNew.getTakenDate() != null) {
                        for (int ix = 0; ix < diffDay; ix++) {
                            Date selectedDate = new Date(objSpecialUnpaidLeaveTakenNew.getTakenDate().getYear(), objSpecialUnpaidLeaveTakenNew.getTakenDate().getMonth(), (objSpecialUnpaidLeaveTakenNew.getTakenDate().getDate() + ix));
                            Period period = PstPeriod.getPeriodBySelectedDate(selectedDate);
                            dayOffSchedule = dayOffSchedule + PstEmpSchedule.getStatusDayOff(objSpecialUnpaidLeaveTakenNew.getEmployeeId(), period.getOID(), 0, vctSchIDOff, selectedDate);
                        }
                    }
                    speacialQty = specialQtyTime - dayOffSchedule;
                } else {
                    speacialQty = DateCalc.workDayDifference(objSpecialUnpaidLeaveTakenNew.getTakenDate(), objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate(), 8f);
                }
                float intersec = PstEmpSchedule.breakTimeIntersection(objSpecialUnpaidLeaveTakenNew.getEmployeeId(), objSpecialUnpaidLeaveTakenNew.getTakenDate(), objSpecialUnpaidLeaveTakenNew.getTakenFinnishDate()) / (8f * 60f * 60f * 1000f);
                objSpecialUnpaidLeaveTakenNew.setTakenQty(speacialQty - intersec);
            }
            this.objSpecialUnpaidLeaveTaken = objSpecialUnpaidLeaveTakenNew;

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
