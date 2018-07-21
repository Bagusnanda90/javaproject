/* 
 * Form Name  	:  FrmScheduleSymbol.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  	: karya 
 * @version  	: 01 
 */
/**
 * *****************************************************************
 * Class Description : [project description ... ] Imput Parameters : [input
 * parameter ...] Output : [output ...] 
 ******************************************************************
 */
package com.dimata.harisma.form.masterdata;

/* java package */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.harisma.entity.masterdata.*;
import java.text.SimpleDateFormat;
import org.apache.jasper.compiler.JspUtil;
import org.apache.log4j.helpers.DateTimeDateFormat;
import sun.security.validator.Validator;

public class FrmScheduleSymbol extends FRMHandler implements I_FRMInterface, I_FRMType {

    private ScheduleSymbol scheduleSymbol;
    public static final String FRM_NAME_SCHEDULESYMBOL = "FRM_NAME_SCHEDULESYMBOL";
    public static final int FRM_FIELD_SCHEDULE_ID = 0;
    public static final int FRM_FIELD_SCHEDULE_CATEGORY_ID = 1;
    public static final int FRM_FIELD_SCHEDULE = 2;
    public static final int FRM_FIELD_SYMBOL = 3;
    public static final int FRM_FIELD_TIME_IN = 4;
    public static final int FRM_FIELD_TIME_OUT = 5;
    public static final int FRM_FIELD_MAX_ENTITLE = 6;
    public static final int FRM_FIELD_PERIODE = 7;
    public static final int FRM_FIELD_PERIODE_TYPE = 8;
    public static final int FRM_FIELD_MIN_SERVICE = 9;
    public static final int FRM_FIELD_BREAK_OUT = 10;
    public static final int FRM_FIELD_BREAK_IN = 11;
    // FIELD IS ADDED BY MCHEN
    public static final int FRM_FIELD_TRANSPORT_ALLOWANCE = 12;
    public static final int FRM_FIELD_NIGHT_ALLOWANCE = 13;
    public static final int FRM_FIELD_WORK_DAYS = 14;
    
    //FIELD ADDED BY JOKER
    public static final int FRM_FIELD_TIME_IN_STRING = 15;
    public static final int FRM_FIELD_TIME_OUT_STRING = 16;
    public static final int FRM_FIELD_BREAK_OUT_STRING = 17;
    public static final int FRM_FIELD_BREAK_IN_STRING = 18;
    public static String[] fieldNames = {
        "FRM_FIELD_SCHEDULE_ID", "FRM_FIELD_SCHEDULE_CATEGORY_ID",
        "FRM_FIELD_SCHEDULE", "FRM_FIELD_SYMBOL",
        "FRM_FIELD_TIME_IN", "FRM_FIELD_TIME_OUT",
        "FRM_FIELD_MAX_ENTITLE",//Tambahan untuk special leave pada hardrock
        "FRM_FIELD_PERIODE",
        "FRM_FIELD_PERIODE_TYPE",
        "FRM_FIELD_MIN_SERVICE", "FRM_FIELD_BREAK_OUT", "FRM_FIELD_BREAK_IN",
        "FRM_FIELD_TRANSPORT_ALLOWANCE", "FRM_FIELD_NIGHT_ALLOWANCE", "FRM_FIELD_WORK_DAYS",
        //Ditambahkan untuk QT 
        "FRM_FIELD_TIME_IN_STRING",
        "FRM_FIELD_TIME_OUT_STRING",
        "FRM_FIELD_BREAK_OUT_STRING",
        "FRM_FIELD_BREAK_IN_STRING"
    };
    public static int[] fieldTypes = {
        TYPE_LONG, TYPE_LONG + ENTRY_REQUIRED,
        TYPE_STRING + ENTRY_REQUIRED, TYPE_STRING + ENTRY_REQUIRED,
        TYPE_DATE, TYPE_DATE,
        TYPE_INT, TYPE_INT, TYPE_INT, TYPE_INT, TYPE_DATE, TYPE_DATE,
        TYPE_INT, TYPE_INT, TYPE_INT,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING
    };

    public FrmScheduleSymbol() {
    }

    public FrmScheduleSymbol(ScheduleSymbol scheduleSymbol) {
        this.scheduleSymbol = scheduleSymbol;
    }

    public FrmScheduleSymbol(HttpServletRequest request, ScheduleSymbol scheduleSymbol) {
        super(new FrmScheduleSymbol(scheduleSymbol), request);
        this.scheduleSymbol = scheduleSymbol;
    }

    public String getFormName() {
        return FRM_NAME_SCHEDULESYMBOL;
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

    public ScheduleSymbol getEntityObject() {
        return scheduleSymbol;
    }

    public void requestEntityObject(ScheduleSymbol scheduleSymbol) {
        try {
            this.requestParam();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            scheduleSymbol.setScheduleCategoryId(getLong(FRM_FIELD_SCHEDULE_CATEGORY_ID));
            scheduleSymbol.setSchedule(getString(FRM_FIELD_SCHEDULE));
            scheduleSymbol.setSymbol(getString(FRM_FIELD_SYMBOL));
            //update by satrya 2012-09-27
            
            String timeIn = getString(FRM_FIELD_TIME_IN_STRING);
            if (timeIn.length() > 0) {//FrmScheduleSymbol.fieldNames[FrmScheduleSymbol.FRM_FIELD_TIME_IN]
                java.util.Date date = sdf.parse(timeIn);
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(date);
                String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                String min = String.valueOf(calendar.get(Calendar.MINUTE));
                String sec = String.valueOf(calendar.get(Calendar.SECOND));
                java.util.Date dt = null;
                dt = com.dimata.util.Validator.composeTime(hour, min, sec);
                scheduleSymbol.setTimeIn(dt);
//                Date dT = new Date();
//                dT.setHours(0);
//                dT.setMinutes(0);
//                dT.setSeconds(0);
//                scheduleSymbol.setTimeIn(dT);
            } else {
                scheduleSymbol.setTimeIn(getDate(FRM_FIELD_TIME_IN));
            }
            
            String timeOut = getString(FRM_FIELD_TIME_OUT_STRING);
            if (timeOut.length() > 0) {
                java.util.Date date = sdf.parse(timeOut);
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(date);
                String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                String min = String.valueOf(calendar.get(Calendar.MINUTE));
                String sec = String.valueOf(calendar.get(Calendar.SECOND));
                java.util.Date dt = null;
                dt = com.dimata.util.Validator.composeTime(hour,min,sec);
                scheduleSymbol.setTimeOut(dt);
                
//                Date dT = new Date();
//                dT.setHours(0);
//                dT.setMinutes(0);
//                dT.setSeconds(0);
//                scheduleSymbol.setTimeOut(dT);
            } else {
                scheduleSymbol.setTimeOut(getDate(FRM_FIELD_TIME_OUT));
            }
            String breakOut = getString(FRM_FIELD_BREAK_OUT_STRING);
            if (breakOut.length() > 0) {
                Date date = sdf.parse(breakOut);
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(date);
                String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                String min = String.valueOf(calendar.get(Calendar.MINUTE));
                String sec = String.valueOf(calendar.get(Calendar.SECOND));
                Date dt = null;
                dt = com.dimata.util.Validator.composeTime(hour,min,sec);
                scheduleSymbol.setBreakOut(dt);
//                Date dT = new Date();
//                dT.setHours(0);
//                dT.setMinutes(0);
//                dT.setSeconds(0);
//                scheduleSymbol.setBreakOut(dT);
            } else {
                scheduleSymbol.setBreakOut(getDate(FRM_FIELD_BREAK_OUT));
            }
            String breakIn = getString(FRM_FIELD_BREAK_IN_STRING);
            if (breakIn.length() > 0) {
                Date date = sdf.parse(breakIn);
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(date);
                String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                String min = String.valueOf(calendar.get(Calendar.MINUTE));
                String sec = String.valueOf(calendar.get(Calendar.SECOND));
                java.util.Date dt = null;
                dt = com.dimata.util.Validator.composeTime(hour, min, sec);
                scheduleSymbol.setBreakIn(dt);
//                Date dT = new Date();
//                dT.setHours(0);
//                dT.setMinutes(0);
//                dT.setSeconds(0);
//                scheduleSymbol.setBreakIn(dT);
            } else {
                scheduleSymbol.setBreakIn(getDate(FRM_FIELD_BREAK_IN));
            }
            //Tambahan untuk special leave pada hardrock
            scheduleSymbol.setMaxEntitle(getInt(FRM_FIELD_MAX_ENTITLE));
            scheduleSymbol.setPeriode(getInt(FRM_FIELD_PERIODE));
            scheduleSymbol.setPeriodeType(getInt(FRM_FIELD_PERIODE_TYPE));
            scheduleSymbol.setMinService(getInt(FRM_FIELD_MIN_SERVICE));
            // ADD BY MCHEN
            scheduleSymbol.setTransportAllowance(getInt(FRM_FIELD_TRANSPORT_ALLOWANCE));
            scheduleSymbol.setNightAllowance(getInt(FRM_FIELD_NIGHT_ALLOWANCE));
            scheduleSymbol.setWorkDays(getInt(FRM_FIELD_WORK_DAYS));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }
}
