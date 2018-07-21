/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.form.payroll;

import com.dimata.gui.excel.ControlListExcel;
import com.dimata.harisma.entity.attendance.I_Atendance;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.Reason;
import com.dimata.harisma.entity.payroll.PayInput;
import com.dimata.harisma.entity.payroll.PstPayInput;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import static com.dimata.qdep.form.I_FRMType.ENTRY_REQUIRED;
import static com.dimata.qdep.form.I_FRMType.TYPE_DATE;
import static com.dimata.qdep.form.I_FRMType.TYPE_FLOAT;
import static com.dimata.qdep.form.I_FRMType.TYPE_INT;
import static com.dimata.qdep.form.I_FRMType.TYPE_LONG;
import static com.dimata.qdep.form.I_FRMType.TYPE_STRING;
import com.dimata.util.Formater;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

/**
 *
 * @author Satrya Ramayu
 */
public class FrmPayInput extends FRMHandler implements I_FRMInterface, I_FRMType {

    private PayInput payInput;
    private Vector vListManyCheckBox = new Vector();
    private Vector sSelectedChkBox = new Vector();
    private Vector vListVctErrorMessage = new Vector();
    public static final String FRM_PAY_INPUT = "FRM_PAY_INPUT";
    public static final int FRM_FIELD_EMPLOYEE_ID = 0;
    public static final int FRM_FIELD_PRESENCE_ONTIME_IDX = 1;
    public static final int FRM_FIELD_PRESENCE_ONTIME_IDX_ADJUSMENT = 2;
    public static final int FRM_FIELD_PRESENCE_ONTIME_TIME = 3;
    public static final int FRM_FIELD_PRESENCE_ONTIME_TIME_ADJUSMENT = 4;
    public static final int FRM_FIELD_LATE_IDX = 5;
    public static final int FRM_FIELD_LATE_IDX_ADJUSMENT = 6;
    public static final int FRM_FIELD_LATE_TIME = 7;
    public static final int FRM_FIELD_LATE_TIME_ADJUSMENT = 8;
    public static final int FRM_FIELD_EARLY_HOME_IDX = 9;
    public static final int FRM_FIELD_EARLY_HOME_IDX_ADJUSMENT = 10;
    public static final int FRM_FIELD_EARLY_HOME_TIME = 11;
    public static final int FRM_FIELD_EARLY_HOME_TIME_ADJUSMENT = 12;
    public static final int FRM_FIELD_LATE_EARLY_IDX = 13;
    public static final int FRM_FIELD_LATE_EARLY_IDX_ADJUSMENT = 14;
    public static final int FRM_FIELD_LATE_EARLY_TIME = 15;
    public static final int FRM_FIELD_LATE_EARLY_TIME_ADJUSMENT = 16;
    public static final int FRM_FIELD_REASON_IDX = 17;
    public static final int FRM_FIELD_REASON_IDX_ADJUSMENT = 18;
    public static final int FRM_FIELD_REASON_TIME = 19;
    public static final int FRM_FIELD_REASON_TIME_ADJUSMENT = 20;
    public static final int FRM_FIELD_ABSENCE_IDX = 21;
    public static final int FRM_FIELD_ABSENCE_IDX_ADJUSMENT = 22;
    public static final int FRM_FIELD_ABSENCE_TIME = 23;
    public static final int FRM_FIELD_ABSENCE_TIME_ADJUSMENT = 24;
    public static final int FRM_FIELD_PRESENCE_OK = 25;
    public static final int FRM_FIELD_DAY_OFF_SCHEDULE = 26;
//        public static final int FRM_FIELD_PAID_LEAVE_AL                     =  27 ;
//        public static final int FRM_FIELD_PAID_LEAVE_AL_ADJUSMENT           =  28 ;
//        
//        public static final int FRM_FIELD_PAID_LEAVE_LL                     =  29 ;
//        public static final int FRM_FIELD_PAID_LEAVE_LL_ADJUSMENT           =  30 ;
//        
//        public static final int FRM_FIELD_PAID_LEAVE_DP                     =  31 ;
//        public static final int FRM_FIELD_PAID_LEAVE_DP_ADJUSMENT           =  32 ;
//        
//        public static final int FRM_FIELD_PAID_LEAVE_SU                     =  33 ;
//        public static final int FRM_FIELD_PAID_LEAVE__SU_ADJUSMENT          =  34 ;
    public static final int FRM_FIELD_UNPAID_lEAVE = 27;
    public static final int FRM_FIELD_UNPAID_LEAVE_ADJUSMENT = 28;
    public static final int FRM_FIELD_OT_IDX_PAID_SALARY = 29;
    public static final int FRM_FIELD_OT_IDX_PAID_SALARY_ADJUSMENT = 30;
    public static final int FRM_FIELD_OT_IDX_PAID_DP = 31;
    public static final int FRM_FIELD_OT_ALLOWANCE_MONEY = 32;
    public static final int FRM_FIELD_OT_ALLOWANCE_MONEY_ADJUSMENT = 33;
    public static final int FRM_FIELD_OT_ALLOWANCE_FOOD = 34;
    public static final int FRM_FIELD_OT_ALLOWANCE_FOOD_ADJUSMENT = 35;
    public static final int FRM_FIELD_PRIVATE_NOTE = 36;
    public static final int FRM_FIELD_EMP_WORK_DAYS = 37;
    public static final int FRM_FIELD_NOTE = 38;
    public static final int FRM_FIELD_PRESENCE_ONTIME_IDX_CHK_BOX = 39;
    public static final int FRM_FIELD_PRESENCE_ONTIME_TIME_CHK_BOX = 40;
    public static final int FRM_FIELD_LATE_IDX_CHK_BOX = 41;
    public static final int FRM_FIELD_LATE_TIME_CHK_BOX = 42;
    public static final int FRM_FIELD_EARLY_HOME_IDX_CHK_BOX = 43;
    public static final int FRM_FIELD_EARLY_HOME_TIME_CHK_BOX = 44;
    public static final int FRM_FIELD_LATE_EARLY_IDX_CHK_BOX = 45;
    public static final int FRM_FIELD_LATE_EARLY_TIME_CHK_BOX = 46;
    public static final int FRM_FIELD_REASON_IDX_CHK_BOX = 47;
    public static final int FRM_FIELD_REASON_TIME_CHK_BOX = 48;
    public static final int FRM_FIELD_ABSENCE_IDX_CHK_BOX = 49;
    public static final int FRM_FIELD_ABSENCE_TIME_CHK_BOX = 50;
    public static final int FRM_FIELD_PAID_LEAVE_AL_CHK_BOX = 51;
    public static final int FRM_FIELD_PAID_LEAVE_LL_CHK_BOX = 52;
    public static final int FRM_FIELD_PAID_LEAVE_DP_CHK_BOX = 53;
    public static final int FRM_FIELD_PAID_LEAVE_SU_CHK_BOX = 54;
    public static final int FRM_FIELD_UNPAID_LEAVE_CHK_BOX = 55;
    public static final int FRM_FIELD_OT_IDX_SALARY_CHK_BOX = 56;
    public static final int FRM_FIELD_OT_ALLOWANCE_MONEY_CHK_BOX = 57;
    public static final int FRM_FIELD_OT_ALLOWANCE_FOOD_CHK_BOX = 58;
    public static final int FRM_FIELD_PROSESS = 59;
    public static final int FRM_FIELD_STATUS_PAY_INPUT = 60;
    public static final int FRM_FIELD_PAY_INPUT_ID = 61;
    public static final int FRM_FIELD_PAY_INPUT_PAID_LEAVE_AL_IDX = 62;
    public static final int FRM_FIELD_PAY_INPUT_PAID_LEAVE_AL_TIME = 63;
    public static final int FRM_FIELD_PAY_INPUT_PAID_LEAVE_LL_IDX = 64;
    public static final int FRM_FIELD_PAY_INPUT_PAID_LEAVE_LL_TIME = 65;
    public static final int FRM_FIELD_PAY_INPUT_PAID_LEAVE_DP_IDX = 66;
    public static final int FRM_FIELD_PAY_INPUT_PAID_LEAVE_DP_TIME = 67;
    public static final int FRM_FIELD_PAY_INPUT_PAID_LEAVE_SU_IDX = 68;
    public static final int FRM_FIELD_PAY_INPUT_PAID_LEAVE_SU_TIME = 69;
    public static final int FRM_FIELD_PAY_INPUT_PAID_LEAVE_TOTAL = 70;
    public static final int FRM_FIELD_PAID_LEAVE_AL_TIME_CHK_BOX = 71;
    public static final int FRM_FIELD_PAID_LEAVE_LL_TIME_CHK_BOX = 72;
    public static final int FRM_FIELD_PAID_LEAVE_DP_TIME_CHK_BOX = 73;
    public static final int FRM_FIELD_PAID_LEAVE_SU_TIME_CHK_BOX = 74;
    public static final int FRM_FIELD_INSENTIF = 75;
    public static final int FRM_FIELD_INSENTIF_ADJUSMENT = 76;
    public static final int FRM_FIELD_INSENTIF_CHK_BOX = 77;
    public static final int FRM_FIELD_POSITION_CHK_BOX = 78;
    public static final int FRM_FIELD_POSITION_IDX = 79;
    public static final int FRM_FIELD_POSITION_ADJUSMENT = 80;
    public static final int FRM_FIELD_NIGHT_ALLOWANCE = 81;
    public static final int FRM_FIELD_TRANSPORT_ALLOWANCE = 82;
    public static final int FRM_FIELD_NIGHT_ALLOWANCE_CHK_BOX = 83;
    public static final int FRM_FIELD_TRANSPORT_ALLOWANCE_CHK_BOX = 84;
    public static final int FRM_FIELD_NIGHT_ALLOWANCE_ADJUSMENT = 85;
    public static final int FRM_FIELD_TRANSPORT_ALLOWANCE_ADJUSMENT = 86;
    
    
    
    public static final int FRM_FIELD_ONLY_IN = 87;
    public static final int FRM_FIELD_ONLY_IN_ADJUSMENT = 88;
    public static final int FRM_FIELD_ONLY_IN_CHK_BOX = 89;
    public static final int FRM_FIELD_ONLY_IN_TIME = 90;
    public static final int FRM_FIELD_ONLY_IN_TIME_ADJUSMENT = 91;
    public static final int FRM_FIELD_ONLY_IN_TIME_CHK_BOX = 92;
    
    public static final int FRM_FIELD_ONLY_OUT = 93;
    public static final int FRM_FIELD_ONLY_OUT_ADJUSMENT = 94;
    public static final int FRM_FIELD_ONLY_OUT_CHK_BOX = 95;
    public static final int FRM_FIELD_ONLY_OUT_TIME = 96;
    public static final int FRM_FIELD_ONLY_OUT_TIME_ADJUSMENT = 97;
    public static final int FRM_FIELD_ONLY_OUT_TIME_CHK_BOX = 98;
    
    //public static final int FRM_FIELD_PRESENCE_ONTIME_TIME_ADJUSMENT_CHK_BOX      =  81 ;
    public static String formatNumberFloat = "#.###";
    public static String formatNumberInt = "#.##";
    public static String[] fieldNames = {
        //kenapa di kapai titik karena _ dan - sudah di pakai
        "FRM.FIELD.EMPLOYEE.ID",
        "FRM.FIELD.PRESENCE.ONTIME.IDX",
        "FRM.FIELD.PRESENCE.ONTIME.IDX.ADJUSMENT",
        "FRM.FIELD.PRESENCE.ONTIME.TIME",
        "FRM.FIELD.PRESENCE.ONTIME.TIME.ADJUSMENT",
        "FRM.FIELD.LATE.IDX",
        "FRM.FIELD.LATE.IDX.ADJUSMENT",
        "FRM.FIELD.LATE.TIME",
        "FRM.FIELD.LATE.TIME.ADJUSMENT",
        "FRM.FIELD.EARLY.HOME.IDX",
        "FRM.FIELD.EARLY.HOME.IDX.ADJUSMENT",
        "FRM.FIELD.EARLY.HOME.TIME",
        "FRM.FIELD.EARLY.HOME.TIME.ADJUSMENT",
        "FRM.FIELD.LATE.EARLY.IDX",
        "FRM.FIELD.LATE.EARLY.IDX.ADJUSMENT",
        "FRM.FIELD.LATE.EARLY.TIME",
        "FRM.FIELD.LATE.EARLY.TIME.ADJUSMENT",
        "FRM.FIELD.REASON.IDX",
        "FRM.FIELD.REASON.IDX.ADJUSMENT",
        "FRM.FIELD.REASON.TIME",
        "FRM.FIELD.REASON.TIME.ADJUSMENT",
        "FRM.FIELD.ABSENCE.IDX",
        "FRM.FIELD.ABSENCE.IDX.ADJUSMENT",
        "FRM.FIELD.ABSENCE.TIME",
        "FRM.FIELD.ABSENCE.TIME.ADJUSMENT",
        "FRM.FIELD.PRESENCE.OK",
        "FRM.FIELD.DAY.OFF.SCHEDULE",
        "FRM.FIELD.UNPAID.lEAVE",
        "FRM.FIELD.UNPAID.LEAVE.ADJUSMENT",
        "FRM.FIELD.OT.IDX.PAID.SALARY",
        "FRM.FIELD.OT.IDX.PAID.SALARY.ADJUSMENT",
        "FRM.FIELD.OT.IDX.PAID.DP",
        "FRM.FIELD.OT.ALLOWANCE.MONEY",
        "FRM.FIELD.OT.ALLOWANCE.MONEY.ADJUSMENT",
        "FRM.FIELD.OT.ALLOWANCE.FOOD",
        "FRM.FIELD.OT.ALLOWANCE.FOOD.ADJUSMENT",
        "FRM.FIELD.PRIVATE.NOTE",
        "FRM.FIELD.EMP.WORK.DAYS",
        "FRM.FIELD.NOTE",
        "FRM.FIELD.PRESENCE.ONTIME.IDX.CHK.BOX",
        "FRM.FIELD.PRESENCE.ONTIME.TIME.CHK.BOX",
        "FRM.FIELD.LATE.IDX.CHK.BOX",
        "FRM.FIELD.LATE.TIME.CHK.BOX",
        "FRM.FIELD.EARLY.HOME.IDX.CHK.BOX",
        "FRM.FIELD.EARLY.HOME.TIME.CHK.BOX",
        "FRM.FIELD.LATE.EARLY.IDX.CHK.BOX",
        "FRM.FIELD.LATE.EARLY.TIME.CHK.BOX",
        "FRM.FIELD.REASON.IDX.CHK.BOX",
        "FRM.FIELD.REASON.TIME.CHK.BOX",
        "FRM.FIELD.ABSENCE.IDX.CHK.BOX",
        "FRM.FIELD.ABSENCE.TIME.CHK.BOX",
        "FRM.FIELD.PAID.LEAVE.AL.CHK.BOX",
        "FRM.FIELD.PAID.LEAVE.LL.CHK.BOX",
        "FRM.FIELD.PAID.LEAVE.DP.CHK.BOX",
        "FRM.FIELD.PAID.LEAVE.SU.CHK.BOX",
        "FRM.FIELD.UNPAID.LEAVE.CHK.BOX",
        "FRM.FIELD.OT.IDX.SALARY.CHK.BOX",
        "FRM.FIELD.OT.ALLOWANCE.MONEY.CHK.BOX",
        "FRM.FIELD.OT.ALLOWANCE.FOOD.CHK.BOX",
        "FRM.FIELD.PROSESS",
        "FRM.FIELD.STATUS.PAY.INPUT",
        "FRM.FIELD.PAY.INPUT.ID",
        "FRM.FIELD.PAY.INPUT.PAID.LEAVE.AL.IDX",
        "FRM.FIELD.PAY.INPUT.PAID.LEAVE.AL.TIME",
        "FRM.FIELD.PAY.INPUT.PAID.LEAVE.LL.IDX",
        "FRM.FIELD.PAY.INPUT.PAID.LEAVE.LL.TIME",
        "FRM.FIELD.PAY.INPUT.PAID.LEAVE.DP.IDX",
        "FRM.FIELD.PAY.INPUT.PAID.LEAVE.DP.TIME",
        "FRM.FIELD.PAY.INPUT.PAID.LEAVE.SU.IDX",
        "FRM.FIELD.PAY.INPUT.PAID.LEAVE.SU.TIME",
        "FRM.FIELD.PAY.INPUT.PAID.LEAVE.TOTAL",
        "FRM.FIELD.PAID.LEAVE.AL.TIME.CHK.BOX",
        "FRM.FIELD.PAID.LEAVE.LL.TIME.CHK.BOX",
        "FRM.FIELD.PAID.LEAVE.DP.TIME.CHK.BOX",
        "FRM.FIELD.PAID.LEAVE.SU.TIME.CHK.BOX",
        "FRM.FIELD.INSENTIF",
        "FRM.FIELD.INSENTIF.ADJUSMENT",
        "FRM.FIELD.INSENTIF.CHK.BOX",
        "FRM.FIELD.POSITION.CHK.BOX",
        "FRM.FIELD.POSITION.IDX",
        "FRM.FIELD.POSITION.ADJUSMENT",
        "FRM.FIELD.NIGHT.ALLOWANCE",
        "FRM.FIELD.TRANSPORT.ALLOWANCE",
        "FRM.FIELD.NIGHT.ALLOWANCE.CHK.BOX",
        "FRM.FIELD.TRANSPORT.ALLOWANCE.CHK.BOX",
        "FRM.FIELD.NIGHT.ALLOWANCE.ADJUSMENT",
        "FRM.FIELD.TRANSPORT.ALLOWANCE.ADJUSMENT",
        
        "FRM_FIELD_ONLY_IN",
        "FRM_FIELD_ONLY_IN_ADJUSMENT",
        "FRM_FIELD_ONLY_IN_CHK_BOX",
        "FRM_FIELD_ONLY_IN_TIME",
        "FRM_FIELD_ONLY_IN_TIME_ADJUSMENT",
        "FRM_FIELD_ONLY_IN_TIME_CHK_BOX",
    
        "FRM_FIELD_ONLY_OUT",
        "FRM_FIELD_ONLY_OUT_ADJUSMENT",
        "FRM_FIELD_ONLY_OUT_CHK_BOX",
        "FRM_FIELD_ONLY_OUT_TIME",
        "FRM_FIELD_ONLY_OUT_TIME_ADJUSMENT",
        "FRM_FIELD_ONLY_OUT_TIME_CHK_BOX"
        
    //"FRM_FIELD_PRESENCE_ONTIME_TIME_ADJUSMENT_CHK_BOX"
    };
    public static int[] fieldTypes = {
        TYPE_LONG,
        TYPE_FLOAT,//"FRM_FIELD_PRESENCE_ONTIME_IDX",
        TYPE_FLOAT,//"FRM_FIELD_PRESENCE_ONTIME_IDX_ADJUSMENT",
        TYPE_FLOAT,//"FRM_FIELD_PRESENCE_ONTIME_TIME",
        TYPE_FLOAT,//"FRM_FIELD_PRESENCE_ONTIME_TIME_ADJUSMENT",

        TYPE_FLOAT,//"FRM_FIELD_LATE_IDX",
        TYPE_FLOAT,//"FRM_FIELD_LATE_IDX_ADJUSMENT",
        TYPE_FLOAT,//"FRM_FIELD_LATE_TIME",
        TYPE_FLOAT,//"FRM_FIELD_LATE_TIME_ADJUSMENT",

        TYPE_FLOAT,//"FRM_FIELD_EARLY_HOME_IDX",
        TYPE_FLOAT,//"FRM_FIELD_EARLY_HOME_IDX_ADJUSMENT",
        TYPE_FLOAT,//"FRM_FIELD_EARLY_HOME_TIME",
        TYPE_FLOAT,//"FRM_FIELD_EARLY_HOME_TIME_ADJUSMENT",

        TYPE_FLOAT,//"FRM_FIELD_LATE_EARLY_IDX",
        TYPE_FLOAT,//"FRM_FIELD_LATE_EARLY_IDX_ADJUSMENT",
        TYPE_FLOAT,//"FRM_FIELD_LATE_TIME_TIME",
        TYPE_FLOAT,//"FRM_FIELD_LATE_EARLY_TIME_ADJUSMENT",

        TYPE_FLOAT,//"FRM_FIELD_MANGKIR_IDX",
        TYPE_FLOAT,//"FRM_FIELD_MANGKIR_IDX_ADJUSMENT",
        TYPE_FLOAT,//"FRM_FIELD_MANGKIR_TIME",
        TYPE_FLOAT,//"FRM_FIELD_MANGKIR_TIME_ADJUSMENT",

        TYPE_FLOAT,//"FRM_FIELD_ABSENCE_IDX",
        TYPE_FLOAT,//"FRM_FIELD_ABSENCE_IDX_ADJUSMENT",
        TYPE_FLOAT,//"FRM_FIELD_ABSENCE_TIME",
        TYPE_FLOAT,//"FRM_FIELD_ABSENCE_TIME_ADJUSMENT",

        TYPE_FLOAT,//"FRM_FIELD_PRESENCE_OK",
        TYPE_FLOAT,//"FRM_FIELD_DAY_OFF_SCHEDULE",

        //            TYPE_FLOAT,//"FRM_FIELD_PAID_LEAVE_AL",
        //            TYPE_FLOAT,//"FRM_FIELD_PAID_LEAVE_AL_ADJUSMENT",
        //        
        //            TYPE_FLOAT,//"FRM_FIELD_PAID_LEAVE_LL",
        //            TYPE_FLOAT,//"FRM_FIELD_PAID_LEAVE_LL_ADJUSMENT",
        //        
        //            TYPE_FLOAT,//"FRM_FIELD_PAID_LEAVE_DP",
        //            TYPE_FLOAT,//"FRM_FIELD_PAID_LEAVE_DP_ADJUSMENT",
        //        
        //            TYPE_FLOAT,//"FRM_FIELD_PAID_LEAVE_SU",
        //            TYPE_FLOAT,//"FRM_FIELD_PAID_LEAVE__SU_ADJUSMENT",

        TYPE_FLOAT,//"FRM_FIELD_UNPAID_lEAVE",
        TYPE_FLOAT,//"FRM_FIELD_UNPAID_LEAVE_ADJUSMENT",

        TYPE_FLOAT,//"FRM_FIELD_OT_IDX_PAID_SALARY",
        TYPE_FLOAT,//"FRM_FIELD_OT_IDX_PAID_SALARY_ADJUSMENT",
        TYPE_FLOAT,//"FRM_FIELD_OT_IDX_PAID_DP",

        TYPE_FLOAT,//"FRM_FIELD_OT_ALLOWANCE_MONEY",
        TYPE_FLOAT,//"FRM_FIELD_OT_ALLOWANCE_MONEY_ADJUSMENT",
        TYPE_FLOAT,//"FRM_FIELD_OT_ALLOWANCE_FOOD",
        TYPE_FLOAT,//"FRM_FIELD_OT_ALLOWANCE_FOOD_ADJUSMENT",

        TYPE_FLOAT,//"FRM_FIELD_PRIVATE_NOTE",
        TYPE_FLOAT,//"FRM_FIELD_EMP_WORK_DAYS",

        TYPE_FLOAT,//"FRM_FIELD_NOTE",

        TYPE_INT,//"FRM_FIELD_PRESENCE_ONTIME_IDX_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_PRESENCE_ONTIME_TIME_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_LATE_IDX_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_LATE_TIME_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_EARLY_HOME_IDX_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_EARLY_HOME_TIME_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_LATE_EARLY_IDX_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_LATE_EARLY_TIME_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_MANGKIR_IDX_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_MANGKIR_TIME_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_ABSENCE_IDX_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_ABSENCE_TIME_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_PAID_LEAVE_AL_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_PAID_LEAVE_LL_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_PAID_LEAVE_DP_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_PAID_LEAVE_SU_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_UNPAID_LEAVE_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_OT_IDX_SALARY_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_OT_ALLOWANCE_MONEY_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_OT_ALLOWANCE_FOOD_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_PROSESS",
        TYPE_INT,//"FRM_FIELD_STATUS_PAY_INPUT"
        TYPE_LONG,
        TYPE_FLOAT,//"FRM_FIELD_PAY_INPUT_PAID_LEAVE_AL_IDX",
        TYPE_FLOAT,//"FRM_FIELD_PAY_INPUT_PAID_LEAVE_AL_TIME",
        TYPE_FLOAT,//"FRM_FIELD_PAY_INPUT_PAID_LEAVE_LL_IDX",
        TYPE_FLOAT,//"FRM_FIELD_PAY_INPUT_PAID_LEAVE_LL_TIME",
        TYPE_FLOAT,//"FRM_FIELD_PAY_INPUT_PAID_LEAVE_DP_IDX",
        TYPE_FLOAT,//"FRM_FIELD_PAY_INPUT_PAID_LEAVE_DP_TIME",
        TYPE_FLOAT,//"FRM_FIELD_PAY_INPUT_PAID_LEAVE_SU_IDX",
        TYPE_FLOAT,//"FRM_FIELD_PAY_INPUT_PAID_LEAVE_SU_TIME",
        TYPE_FLOAT,//"FRM_FIELD_PAY_INPUT_PAID_LEAVE_TOTAL"

        TYPE_INT,//"FRM_FIELD_PAID_LEAVE_AL_TIME_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_PAID_LEAVE_LL_TIME_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_PAID_LEAVE_DP_TIME_CHK_BOX",
        TYPE_INT,//"FRM_FIELD_PAID_LEAVE_SU_TIME_CHK_BOX"
        TYPE_FLOAT,//"FRM_FIELD_INSENTIF",
        TYPE_FLOAT,//"FRM_FIELD_INSENTIF_ADJUSMENT",
        TYPE_FLOAT,//"FRM_FIELD_INSENTIF_CHK_BOX" 
        TYPE_INT,//FRM_FIELD_POSITION_CHK_BOX
        TYPE_FLOAT,//     "FRM_FIELD_POSITION",
        TYPE_FLOAT,//"FRM_FIELD_POSITION_ADJUSMENT"
        
        TYPE_INT,//"FRM.FIELD.NIGHT.ALLOWANCE",
        TYPE_INT,//"FRM.FIELD.TRANSPORT.ALLOWANCE",
        TYPE_INT,//"FRM.FIELD.NIGHT.ALLOWANCE.CHK.BOX",
        TYPE_INT,//"FRM.FIELD.TRANSPORT.ALLOWANCE.CHK.BOX",
        TYPE_FLOAT,//"FRM.FIELD.NIGHT.ALLOWANCE.ADJUSMENT",
        TYPE_FLOAT,//"FRM.FIELD.TRANSPORT.ALLOWANCE.ADJUSMENT"
        
        TYPE_FLOAT,//"FRM_FIELD_ONLY_OUT",
        TYPE_FLOAT,//"FRM_FIELD_ONLY_OUT_ADJUSMENT",
        TYPE_INT, //"FRM_FIELD_ONLY_OUT_CHK_BOX",
        TYPE_FLOAT,//"FRM_FIELD_ONLY_OUT_TIME",
        TYPE_FLOAT,//"FRM_FIELD_ONLY_OUT_TIME_ADJUSMENT",
        TYPE_INT,//"FRM_FIELD_ONLY_OUT_TIME_CHK_BOX"
        
        TYPE_FLOAT,//"FRM_FIELD_ONLY_OUT",
        TYPE_FLOAT,//"FRM_FIELD_ONLY_OUT_ADJUSMENT",
        TYPE_INT, //"FRM_FIELD_ONLY_OUT_CHK_BOX",
        TYPE_FLOAT,//"FRM_FIELD_ONLY_OUT_TIME",
        TYPE_FLOAT,//"FRM_FIELD_ONLY_OUT_TIME_ADJUSMENT",
        TYPE_INT//"FRM_FIELD_ONLY_OUT_TIME_CHK_BOX"
        
    //TYPE_INT//"FRM_FIELD_PRESENCE_ONTIME_TIME_ADJUSMENT_CHK_BOX"
    };

    /**
     * Creates a new instance of FrmPayGeneral
     */
    public FrmPayInput() {
    }

    public FrmPayInput(PayInput payInput) {
        this.payInput = payInput;
    }

    public FrmPayInput(HttpServletRequest request, PayInput payInput) {
        super(new FrmPayInput(payInput), request);
        this.payInput = payInput;
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

        return FRM_PAY_INPUT;
    }

    public PayInput getEntityObject() {
        return payInput;
    }

    /**
     *
     * @param paramRequest : dari name checkboxnya
     */
    public void requestEntityObjOvertime(Vector listSelected) {

        this.requestParam();
        String userSelected[];
        userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_SALARY_CHK_BOX]);
        if (userSelected != null && userSelected.length > 0) {
            for (int i = 0; i < userSelected.length; i++) {
                try {
                    long empId = Long.parseLong((userSelected[i].split("_")[0]));
                    long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                    long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                    String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_SALARY_ADJUSMENT];
                    if (empId != 0) {
                        //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                        //listSelected.add(payCode+"_"+empId);
                        // sSelectedChkBox.add(payCode+"-"+paySlipId+"-"+periodId+"-"+empId);
                        Date dtAdjusment = this.getParamDateVer2("date_adjusment");
                        PayInput payInputs = new PayInput();
                        payInputs.setEmployeeId(empId);
                        payInputs.setPeriodId(periodId);
                        payInputs.setPaySlipId(paySlipId);
                        payInputs.setOtIdxPaidSalaryAdjust(this.getParamDouble(payCode + "_" + empId));
                        payInputs.setOtIdxPaidSalaryAdjustDt(dtAdjusment);
                       
                        listSelected.add(payInputs);
                    }
                } catch (Exception exc) {
                    System.out.println("Exc" + exc);
                }
            }
        }
        userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_MONEY_CHK_BOX]);
        if (userSelected != null && userSelected.length > 0) {
            for (int i = 0; i < userSelected.length; i++) {
                try {
                    long empId = Long.parseLong((userSelected[i].split("_")[0]));
                    long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                    long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                    String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_MONEY_ADJUSMENT];
                    if (empId != 0) {
                        //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                        //vListManyCheckBox.add(payCode+"_"+empId);
                        // sSelectedChkBox.add(payCode+"-"+paySlipId+"-"+periodId+"-"+empId);
                        Date dtAdjusment = this.getParamDateVer2("date_adjusment");
                        PayInput payInputs = new PayInput();
                        payInputs.setEmployeeId(empId);
                        payInputs.setPeriodId(periodId);
                        payInputs.setPaySlipId(paySlipId);
                        payInputs.setOtAllowanceMoneyAdjust(this.getParamDouble(payCode + "_" + empId));
                        payInputs.setOtAllowanceMoneyAdjustDt(dtAdjusment);
                        listSelected.add(payInputs);
                    }
                } catch (Exception exc) {
                    System.out.println("Exc" + exc);
                }
            }
        }
       
        
         userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_NIGHT_ALLOWANCE_CHK_BOX]);
        if (userSelected != null && userSelected.length > 0) {
            for (int i = 0; i < userSelected.length; i++) {
                try {
                    long empId = Long.parseLong((userSelected[i].split("_")[0]));
                    long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                    long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                    
                    //String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_MONEY_ADJUSMENT];
                    String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_NIGHT_ALLOWANCE_ADJUSMENT];
                    if (empId != 0) {
                        //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                        //vListManyCheckBox.add(payCode+"_"+empId);
                        // sSelectedChkBox.add(payCode+"-"+paySlipId+"-"+periodId+"-"+empId);
                        Date dtAdjusment = this.getParamDateVer2("date_adjusment");
                        PayInput payInputs = new PayInput();
                        payInputs.setEmployeeId(empId);
                        payInputs.setPeriodId(periodId);
                        payInputs.setPaySlipId(paySlipId);
                        payInputs.setNightAllowanceAdjusment(this.getParamDouble(payCode + "_" + empId));
                        payInputs.setNightAllowanceAdjustDt(dtAdjusment);
                        listSelected.add(payInputs);
                    }
                } catch (Exception exc) {
                    System.out.println("Exc" + exc);
                }
            }
        }
        
        userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_TRANSPORT_ALLOWANCE_CHK_BOX]);
        if (userSelected != null && userSelected.length > 0) {
            for (int i = 0; i < userSelected.length; i++) {
                try {
                    long empId = Long.parseLong((userSelected[i].split("_")[0]));
                    long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                    long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                    
                    //String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_MONEY_ADJUSMENT];
                    String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_TRANSPORT_ALLOWANCE_ADJUSMENT];
                    if (empId != 0) {
                        //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                        //vListManyCheckBox.add(payCode+"_"+empId);
                        // sSelectedChkBox.add(payCode+"-"+paySlipId+"-"+periodId+"-"+empId);
                        Date dtAdjusment = this.getParamDateVer2("date_adjusment");
                        PayInput payInputs = new PayInput();
                        payInputs.setEmployeeId(empId);
                        payInputs.setPeriodId(periodId);
                        payInputs.setPaySlipId(paySlipId);
                        payInputs.setTransportAllowanceAdjusment(this.getParamDouble(payCode + "_" + empId));
                        payInputs.setTransportAllowanceAdjustDt(dtAdjusment);
                        listSelected.add(payInputs);
                    }
                } catch (Exception exc) {
                    System.out.println("Exc" + exc);
                }
            }
        }

    }

    public void requestEntityObjectMultipleProsess(String paramRequest, Vector listReason, Vector listPosition, String chkBox, Hashtable hashEmployee) {
        try {
            this.requestParam();
            String userSelectValue[] = this.getParamsStringValues(paramRequest);

            if (userSelectValue != null && userSelectValue.length > 0) {
                for (int i = 0; i < userSelectValue.length; i++) {
                    try {
                        //cari employee id,componen code,dan period
                        //lalu satu persatu di update
                        //jika ada formnya benrnama reason maka akan ada penambahan nilai
                        //long takenDpOid = Long.parseLong((userSelect[i].split("_")[0]));
                        double value = Double.parseDouble((userSelectValue[i].split("_")[0]));
                        String userSelect[] = chkBox.split("-");
                        String payInputCode = ((userSelect[0]));
                        long paySlipId = Long.parseLong((userSelect[1]));
                        long periodId = Long.parseLong((userSelect[2]));
                        long empId = Long.parseLong((userSelect[3]));


                        Date dtAdjusment = this.getParamDateVer2("date_adjusment");
                        //int statusPayInput = this.getParamInt("status_pay_input");//ini di update di paid status table pay slip
                        if (empId != 0 && payInputCode != null) {
                            //jika name payInputCode == reason(reason idx, reason time) atau position(adjusment)
                            if (payInputCode.equalsIgnoreCase(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_ADJUSMENT])
                                    || payInputCode.equalsIgnoreCase(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_ADJUSMENT])
                                    || payInputCode.equalsIgnoreCase(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_ADJUSMENT])) {
                                //payInputCode = ((userSelect[i].split("_")[4]));
//                                          String no =  ((userSelect[i].split("_")[5]));
//                                          String periodeId =  ((userSelect[i].split("_")[6]));
//                                          String sEmpId =  ((userSelect[i].split("_")[7]));

                                payInputCode = ((userSelect[4]));

                                long periodeId = Long.parseLong((userSelect[6]));
                                long sEmpId = Long.parseLong((userSelect[7]));
                                if (payInputCode.equalsIgnoreCase(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_ADJUSMENT])
                                        || payInputCode.equalsIgnoreCase(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_ADJUSMENT])) {
                                    //maka reason
                                    int no = Integer.parseInt((userSelect[5]));
                                    if (listReason != null && listReason.size() > 0) {
                                        for (int idxRes = 0; idxRes < listReason.size(); idxRes++) {
                                            Reason reason = (Reason) listReason.get(idxRes);
                                            if (no == reason.getNo()) {
                                                value = value = Double.parseDouble((userSelectValue[i].split("_")[0]));
                                                PstPayInput pstPayInput = new PstPayInput();
                                                payInputCode = payInputCode + "_" + no + "_" + periodeId + "_" + sEmpId;
                                                pstPayInput.updatePayInput(paySlipId, payInputCode, periodId, empId, value, dtAdjusment, vListVctErrorMessage, hashEmployee);
                                            }
                                        }
                                    }


                                } else {
                                    //maka position
                                    long positionId = Long.parseLong((userSelect[5]));
                                    if (listPosition != null && listPosition.size() > 0) {
                                        for (int idxPost = 0; idxPost < listPosition.size(); idxPost++) {
                                            Position position = (Position) listPosition.get(idxPost);
                                            if (positionId == position.getOID()) {
                                                value = Double.parseDouble((userSelectValue[i].split("_")[0]));
                                                PstPayInput pstPayInput = new PstPayInput();
                                                payInputCode = payInputCode + "_" + positionId + "_" + periodeId + "_" + empId;
                                                pstPayInput.updatePayInput(paySlipId, payInputCode, periodId, empId, value, dtAdjusment, vListVctErrorMessage, hashEmployee);
                                            }
                                        }
                                    }

                                }
                            } else {
                                //umum atau static
                                PstPayInput pstPayInput = new PstPayInput();
                                pstPayInput.updatePayInput(paySlipId, payInputCode, periodId, empId, value, dtAdjusment, vListVctErrorMessage, hashEmployee);

                            }
                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }
            //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
            //payGeneral.setCompanyName(getString(FRM_FIELD_COMPANY_NAME));


        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

    /**
     * menentukan berapa form name yg di cekbox create by satrya 2014-04-28
     *
     * @param listReason
     * @param listPosition
     */
    public Vector requestHowManyCheckbox(Vector listReason, Vector listPosition) {
        try {
            this.requestParam();

            String userSelected[];
            //int indexString =0;
            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_IDX_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_IDX_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            //sSelected.add()
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);


                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }

            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);

                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }

            //priska
//              userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_CHK_BOX]);
//            if (userSelected != null && userSelected.length > 0) {
//                for (int i = 0; i < userSelected.length; i++) {
//                    try {
//                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
//                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
//                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
//                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_ADJUSMENT];
//                        if (empId != 0) {
//                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
//                            vListManyCheckBox.add(payCode + "_" + empId);
//                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);
//
//                        }
//                    } catch (Exception exc) {
//                        System.out.println("Exc" + exc);
//                    }
//                }
//            }
//              userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_CHK_BOX]);
//            if (userSelected != null && userSelected.length > 0) {
//                for (int i = 0; i < userSelected.length; i++) {
//                    try {
//                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
//                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
//                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
//                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_ADJUSMENT];
//                        if (empId != 0) {
//                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
//                            vListManyCheckBox.add(payCode + "_" + empId);
//                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);
//
//                        }
//                    } catch (Exception exc) {
//                        System.out.println("Exc" + exc);
//                    }
//                }
//            }
            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_IDX_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_IDX_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);
                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }


            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);
                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }

            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);

                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }


            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);
                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }


            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);
                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }


            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);
                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }

            if (listReason != null && listReason.size() > 0) {
                for (int idxRes = 0; idxRes < listReason.size(); idxRes++) {
                    Reason reason = (Reason) listReason.get(idxRes);
                    userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_CHK_BOX] + "_" + reason.getNo());
                    if (userSelected != null && userSelected.length > 0) {
                        for (int i = 0; i < userSelected.length; i++) {
                            try {
                                long empId = Long.parseLong((userSelected[i].split("_")[0]));
                                long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                                long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                                int reasonNo = Integer.parseInt((userSelected[i].split("_")[3]));
                                String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_ADJUSMENT];
                                if (empId != 0 && reasonNo == reason.getNo()) {
                                    //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId+"_"+payCode+"_"+reason.getNo()+"_"+periodId+"_"+empId);
                                    vListManyCheckBox.add(payCode + "_" + reason.getNo() + "_" + empId);
                                    sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId + "-" + payCode + "-" + reason.getNo() + "-" + periodId + "-" + empId);
                                }
                            } catch (Exception exc) {
                                System.out.println("Exc" + exc);
                            }
                        }
                    }

                    userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_CHK_BOX]);
                    if (userSelected != null && userSelected.length > 0) {
                        for (int i = 0; i < userSelected.length; i++) {
                            try {
                                long empId = Long.parseLong((userSelected[i].split("_")[0]));
                                long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                                long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                                String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_ADJUSMENT];
                                if (empId != 0) {
                                    //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId+"_"+payCode+"_"+reason.getNo()+"_"+periodId+"_"+empId);
                                    vListManyCheckBox.add(payCode + "_" + reason.getNo() + "_" + empId);
                                    sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId + "-" + payCode + "-" + reason.getNo() + "-" + periodId + "-" + empId);
                                }
                            } catch (Exception exc) {
                                System.out.println("Exc" + exc);
                            }
                        }
                    }

                }
            }


            //end reason
            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);
                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }

            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);
                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }
            //priska 20150320
            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);
                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }


            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_TIME_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_TIME_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);
                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }

            
            
            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);
                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }


            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_TIME_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_TIME_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);
                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }
            
            
            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_UNPAID_LEAVE_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_UNPAID_LEAVE_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);
                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }

            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_INSENTIF_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_INSENTIF_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);
                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }

            
          
            
            /*userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_SALARY_CHK_BOX]);
             if (userSelected != null && userSelected.length > 0) {
             for (int i = 0; i < userSelected.length; i++) {
             try {
             long empId = Long.parseLong((userSelected[i].split("_")[0]));
             long periodId =Long.parseLong((userSelected[i].split("_")[1]));
             long paySlipId =Long.parseLong((userSelected[i].split("_")[2]));
             String payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_SALARY_ADJUSMENT];
             if(empId!=0){
             //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
             vListManyCheckBox.add(payCode+"_"+empId);
             sSelectedChkBox.add(payCode+"-"+paySlipId+"-"+periodId+"-"+empId);
             }
             }catch(Exception exc){
             System.out.println("Exc"+exc);
             }
             }
             }*/

            /*userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_MONEY_CHK_BOX]);
             if (userSelected != null && userSelected.length > 0) {
             for (int i = 0; i < userSelected.length; i++) {
             try {
             long empId = Long.parseLong((userSelected[i].split("_")[0]));
             long periodId =Long.parseLong((userSelected[i].split("_")[1]));
             long paySlipId =Long.parseLong((userSelected[i].split("_")[2]));
             String payCode =FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_MONEY_ADJUSMENT];
             if(empId!=0){
             //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
             vListManyCheckBox.add(payCode+"_"+empId);
             sSelectedChkBox.add(payCode+"-"+paySlipId+"-"+periodId+"-"+empId);
             }
             }catch(Exception exc){
             System.out.println("Exc"+exc);
             }
             }
             }*/

            userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_CHK_BOX]);
            if (userSelected != null && userSelected.length > 0) {
                for (int i = 0; i < userSelected.length; i++) {
                    try {
                        long empId = Long.parseLong((userSelected[i].split("_")[0]));
                        long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                        long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                        String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_ADJUSMENT];
                        if (empId != 0) {
                            //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId);
                            vListManyCheckBox.add(payCode + "_" + empId);
                            sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId);
                        }
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }

            //position
            if (listPosition != null && listPosition.size() > 0) {
                for (int idxRes = 0; idxRes < listPosition.size(); idxRes++) {
                    Position position = (Position) listPosition.get(idxRes);
                    userSelected = this.getParamsStringValues(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_CHK_BOX] + "_" + position.getOID());
                    if (userSelected != null && userSelected.length > 0) {
                        for (int i = 0; i < userSelected.length; i++) {
                            try {
                                long empId = Long.parseLong((userSelected[i].split("_")[0]));
                                long periodId = Long.parseLong((userSelected[i].split("_")[1]));
                                long paySlipId = Long.parseLong((userSelected[i].split("_")[2]));
                                long positionId = Long.parseLong((userSelected[i].split("_")[3]));
                                String payCode = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_ADJUSMENT];
                                if (empId != 0 && positionId == position.getOID()) {
                                    //vListManyCheckBox.add(payCode+"_"+paySlipId+"_"+periodId+"_"+empId+"_"+payCode+"_"+position.getOID()+"_"+periodId+"_"+empId);
                                    vListManyCheckBox.add(payCode + "_" + position.getOID() + "_" + empId);
                                    sSelectedChkBox.add(payCode + "-" + paySlipId + "-" + periodId + "-" + empId + "-" + payCode + "-" + position.getOID() + "-" + periodId + "-" + empId);
                                }
                            } catch (Exception exc) {
                                System.out.println("Exc" + exc);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
        Vector vListAllTotal = new Vector();
        vListAllTotal.add(vListManyCheckBox);
        vListAllTotal.add(sSelectedChkBox);
        return vListAllTotal;
    }

    /**
     * untuk merubah statusnya
     */
    public void requestEntityObjectMultipleStatus() {
        try {
            this.requestParam();
            String userSelect[] = this.getParamsStringValues("prosess");

            if (userSelect != null && userSelect.length > 0) {
                for (int i = 0; i < userSelect.length; i++) {
                    try {
                        //long takenDpOid = Long.parseLong((userSelect[i].split("_")[0]));
                    } catch (Exception exc) {
                        System.out.println("Exc" + exc);
                    }
                }
            }
            //employee.setPositionId(getLong(FRM_FIELD_POSITION_ID));
            //payGeneral.setCompanyName(getString(FRM_FIELD_COMPANY_NAME));


        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

    public static void getListInput(I_Atendance attdConfig, I_Leave leaveConfig, Vector rowx, PayInput objPayInput, int statusPayInput, Date dtAjusment, long periodId, int i, Vector listPos, int showOT, Vector listReason) {
        //Vector rowx = new Vector(1,1);
        try {
            //jika statusnya msh draff
            //Formater.formatWorkDayHoursMinutes(sumSpecial,leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave())
            if (statusPayInput == I_DocStatus.DOCUMENT_STATUS_DRAFT) {
                /*0*/ rowx.add(String.valueOf(1 + i));//1
            /*1*/
                rowx.add(objPayInput.getEmployeeNumber());//2
            /*2*/
                rowx.add(objPayInput.getEmployeeName());//3
            /*3*/
                rowx.add(objPayInput.getPositionName());//4
            /*4*/
                rowx.add("<div align=\"right\">" + objPayInput.getPresenceOntimeIdx() + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_IDX] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getPresenceOntimeIdx() + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_IDX_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getPresenceOntimeIdxAdjust() + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_IDX_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");//5
            /*5*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getPresenceOntimeTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getPresenceOntimeTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getPresenceOntimeTimeAdjust(), formatNumberFloat) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //6
            /*6*/
                rowx.add("<div align=\"right\">" + objPayInput.getLateIdx() + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_IDX] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getLateIdx() + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_IDX_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getLateIdxAdjust() + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_IDX_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //7
            /*7*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getLateTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getLateTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getLateTimeAdjust(), formatNumberFloat) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //8
            /*8*/
                rowx.add("<div align=\"right\">" + objPayInput.getEarlyHomeIdx() + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getEarlyHomeIdx() + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getEarlyHomeIdxAdjust() + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //9
            
                
                /*9*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getEarlyHomeTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getEarlyHomeTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getEarlyHomeTimeAdjust(), formatNumberFloat) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //10
           
               /*10*/
                rowx.add("<div align=\"right\">" + objPayInput.getLateEarlyIdx() + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getLateEarlyIdx() + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getLateEarlyIdxAdjust() + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //11
            /*11*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getLateEarlyTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getLateEarlyTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getLateEarlyTimeAdjust(), formatNumberFloat) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");//12  
            
                 /*8*/
                rowx.add("<div align=\"right\">" + objPayInput.getOnlyInIdx() + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getOnlyInIdx() + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getOnlyInIdxAdjust() + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //9
            /*9*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getOnlyInTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_TIME] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getOnlyInTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_TIME_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getOnlyInTimeAdjust(), formatNumberFloat) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_TIME_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //10
            
                
                /*8*/
                rowx.add("<div align=\"right\">" + objPayInput.getOnlyOutIdx() + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getOnlyOutIdx() + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getOnlyOutIdxAdjust() + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //9
            /*9*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getOnlyOutTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_TIME] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getOnlyOutTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_TIME_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getOnlyOutTimeAdjust(), formatNumberFloat) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_TIME_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //10
            
                if (listReason != null && listReason.size() > 0) { //jika ada yg ingin menggunakan mangkir
                    for (int idxRes = 0; idxRes < listReason.size(); idxRes++) {
                        Reason reason = (Reason) listReason.get(idxRes);
                        /*12*/ rowx.add("<div align=\"right\">" + objPayInput.getReasonIdx(reason.getNo(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX] + "_" + reason.getNo() + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getReasonIdx(reason.getNo(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_ADJUSMENT] + "_" + reason.getNo() + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getReasonIdxAdjust(reason.getNo(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_CHK_BOX] + "_" + reason.getNo() + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "_" + reason.getNo() + "\"/>" + "</div>");  //13
                    /*13*/
                        rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getReasonTime(reason.getNo(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME] + "_" + reason.getNo() + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getReasonTime(reason.getNo(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_ADJUSMENT] + "_" + reason.getNo() + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getReasonTimeAdjust(reason.getNo(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()), formatNumberFloat) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_CHK_BOX] + "_" + reason.getNo() + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "_" + reason.getNo() + "\"/>" + "</div>");//14
                    }

                }
                /*14*/ rowx.add("<div align=\"right\">" + objPayInput.getAbsenceIdx() + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getAbsenceIdx() + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getAbsenceIdxAdjust() + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //15
            /*15*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getAbsenceTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getAbsenceTime(), formatNumberFloat) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getAbsenceTimeAdjust(), formatNumberFloat) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //16
            /*16*/
                rowx.add("<div align=\"right\">" + objPayInput.getProsentaseOK() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_OK] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getProsentaseOK() + "\" /> " + "</div>");  //17
            /*17*/
                rowx.add("<div align=\"right\">" + objPayInput.getDayOffSchedule() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_DAY_OFF_SCHEDULE] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getDayOffSchedule() + "\" /> " + "</div>");  //17


                /*18*/ rowx.add("<div align=\"right\">" + objPayInput.getAlIdx() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PAY_INPUT_PAID_LEAVE_AL_IDX] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getAlIdx() + "\" /> " + "</div>");  //18
            /*19*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getAlTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PAY_INPUT_PAID_LEAVE_AL_TIME] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getAlTime(), formatNumberFloat) + "\" /> " + "</div>");  //19
            /*20*/
                rowx.add("<div align=\"right\">" + objPayInput.getLlIdx() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PAY_INPUT_PAID_LEAVE_LL_IDX] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getLlIdx() + "\" /> " + "</div>");  //20
            /*21*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getLlTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PAY_INPUT_PAID_LEAVE_LL_TIME] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getLlTime(), formatNumberFloat) + "\" /> " + "</div>");//21  
            /*22*/
                rowx.add("<div align=\"right\">" + objPayInput.getDpIdx() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PAY_INPUT_PAID_LEAVE_DP_IDX] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getDpIdx() + "\" /> " + "</div>");  //22
            /*23*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getDpTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PAY_INPUT_PAID_LEAVE_DP_TIME] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getDpTime(), formatNumberFloat) + "\" /> " + "</div>");  //23
//            /*24*/rowx.add("<div align=\"right\">"+payInputPaidLeaveDetail.getSuIdx()+" <input type=\"hidden\" name=\""+FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PAY_INPUT_PAID_LEAVE_SU_IDX]+"_"+objPayInput.getEmployeeId()+"\" size=\"14\" value=\""+payInputPaidLeaveDetail.getSuIdx()+"\" /> "+"</div>"); //24 
//            /*25*/rowx.add("<div align=\"right\">"+payInputPaidLeaveDetail.getSuTime()+" <input type=\"hidden\" name=\""+FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PAY_INPUT_PAID_LEAVE_SU_TIME]+"_"+objPayInput.getEmployeeId()+"\" size=\"14\" value=\""+payInputPaidLeaveDetail.getSuTime()+"\" /> "+"</div>");  //25

                /*26*/ rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getUnPaidLeave(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_UNPAID_lEAVE] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getUnPaidLeave(), formatNumberFloat) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_UNPAID_LEAVE_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getUnPaidLeaveAdjust(), formatNumberFloat) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_UNPAID_LEAVE_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //26

                /*26*/ rowx.add("<div align=\"right\">" + objPayInput.getInsentif() + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_INSENTIF] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getInsentif() + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_INSENTIF_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getInsentifAdjusment() + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_INSENTIF_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //26
                //jika menggunakan overtime
                if (showOT == 0) {
                    /*27*/ rowx.add("<div align=\"right\">" + Formater.formatNumber(objPayInput.getOtIdxPaidSalary(), formatNumberFloat) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_SALARY] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getOtIdxPaidSalary(), formatNumberFloat) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_SALARY_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getOtIdxPaidSalaryAdjust(), formatNumberFloat) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_SALARY_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //27
            /*28*/
                    rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getOtIdxPaidDp(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_DP] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getOtIdxPaidDp(), formatNumberFloat) + "\" /> " + "</div>");

                    /*29*/ //rowx.add("<div align=\"right\">"+Formater.formatWorkDayHoursMinutes((float)objPayInput.getOtAllowanceMoney(), leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave())+" + "+" <input type=\"hidden\" name=\""+FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_MONEY]+"_"+objPayInput.getEmployeeId()+"\" value=\""+Formater.formatWorkDayHoursMinutes((float)objPayInput.getOtAllowanceMoney(), leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave())+"\" /> "+" <input type=\"text\" name=\""+FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_MONEY_ADJUSMENT]+"_"+objPayInput.getEmployeeId()+"\" size=\"14\" value=\""+Formater.formatNumber(objPayInput.getOtAllowanceMoneyAdjust(), formatNumberFloat)+"\" /> "+"<input name=\""+FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_MONEY_CHK_BOX]+"\" type=\"checkbox\" value=\""+objPayInput.getEmployeeId()+"_"+objPayInput.getPeriodId()+"_"+objPayInput.getPaySlipId()+"\"/>"+"</div>");  //28
                    rowx.add("<div align=\"right\">" + Formater.formatNumber(objPayInput.getOtAllowanceMoney(), formatNumberInt) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_MONEY] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getOtAllowanceMoney(), formatNumberInt) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_MONEY_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getOtAllowanceMoneyAdjust(), formatNumberInt) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_MONEY_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //28
            /*30*/ //rowx.add("<div align=\"right\">"+Formater.formatWorkDayHoursMinutes((float)objPayInput.getOtAllowanceFood(), leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave())+" + "+" <input type=\"hidden\" name=\""+FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD]+"_"+objPayInput.getEmployeeId()+"\" value=\""+Formater.formatWorkDayHoursMinutes((float)objPayInput.getOtAllowanceFood(), leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave())+"\" /> "+" <input type=\"text\" name=\""+FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_ADJUSMENT]+"_"+objPayInput.getEmployeeId()+"\" size=\"14\" value=\""+Formater.formatNumber(objPayInput.getOtAllowanceFoodAdjust(), formatNumberFloat)+"\" /> "+"<input name=\""+FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_CHK_BOX]+"\" type=\"checkbox\" value=\""+objPayInput.getEmployeeId()+"_"+objPayInput.getPeriodId()+"_"+objPayInput.getPaySlipId()+"\"/>"+"</div>");  //29
                    rowx.add("<div align=\"right\">" + Formater.formatNumber(objPayInput.getOtAllowanceFood(), formatNumberInt) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getOtAllowanceFood(), formatNumberInt) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getOtAllowanceFoodAdjust(), formatNumberInt) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");  //29
                    
                   
                    
                }
                 rowx.add("<div align=\"right\">" + Formater.formatNumber(objPayInput.getNightAllowance(), formatNumberInt) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_NIGHT_ALLOWANCE] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getNightAllowance(), formatNumberInt) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_NIGHT_ALLOWANCE_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getNightAllowanceAdjusment(), formatNumberInt) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_NIGHT_ALLOWANCE_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");
                 rowx.add("<div align=\"right\">" + Formater.formatNumber(objPayInput.getTransportAllowance(), formatNumberInt) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_TRANSPORT_ALLOWANCE] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getTransportAllowance(), formatNumberInt) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_TRANSPORT_ALLOWANCE_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getTransportAllowanceAdjusment(), formatNumberInt) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_TRANSPORT_ALLOWANCE_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");
                if (listPos != null && listPos.size() > 0) {
                    for (int idxPos = 0; idxPos < listPos.size(); idxPos++) {
                        Position position = (Position) listPos.get(idxPos);
                        rowx.add("<div align=\"right\">" + Formater.formatNumber(objPayInput.getPositionIdx(position.getOID(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()), formatNumberFloat) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_IDX] + "_" + position.getOID() + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getPositionIdx(position.getOID(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()), formatNumberFloat) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_ADJUSMENT] + "_" + position.getOID() + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getPositionAdjust(position.getOID(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()), formatNumberFloat) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_CHK_BOX] + "_" + position.getOID() + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "_" + position.getOID() + "\"/>" + "</div>");  //29
                    }
                }
                /*31*/ rowx.add("<div align=\"right\">" + "<input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRIVATE_NOTE] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getPrivateNote() + "\" /> " + "</div>");  //30
            /*32*/
                rowx.add("<div align=\"right\">" + Formater.formatNumber(objPayInput.getEmpWorkDays(), formatNumberInt) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EMP_WORK_DAYS] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getEmpWorkDays(), formatNumberInt) + "\" /> " + "</div>"); //31 
            /*33*///rowx.add("<a href=\"javascript:cmdNote('"+objPayInput.getEmployeeId()+"','"+periodId+"','"+objPayInput.getOID()+"','"+(dtAjusment==null?0:dtAjusment.getTime())+")\"><img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\"></a>");//32
                rowx.add("<a href=\"javascript:cmdNote('" + objPayInput.getEmployeeId() + "','" + periodId + "','" + objPayInput.getPaySlipId() + "','" + (dtAjusment == null ? new Date().getTime() : dtAjusment.getTime()) + "')\"><img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\"></a>");
                /*34*/ rowx.add("<input type=\"checkbox\" name=\"prosess" + i + "\" value=\"" + 1 + "\" class=\"formElemen\" size=\"10\"  >");//33
            /*35*///rowx.add("<input type=\"checkbox\" name=\"status\" value=\""+objPayInput.getEmployeeId()+"_"+statusPayInput+"\" class=\"formElemen\" size=\"10\">");//34
            /*35*/
                rowx.add("<input type=\"checkbox\" name=\"status" + i + "\" value=\"" + 1 + "\" class=\"formElemen\" size=\"10\">");//34
            /*36*/
                rowx.add(objPayInput.getNote()); //36

            } else {
                //jika statusnya sudah finall
            /*0*/ rowx.add(String.valueOf(1 + i));//1
            /*1*/
                rowx.add(objPayInput.getEmployeeNumber());//2
            /*2*/
                rowx.add(objPayInput.getEmployeeName());//3
            /*3*/
                rowx.add(objPayInput.getPositionName());//4
            /*4*/
                rowx.add("<div align=\"right\">" + objPayInput.getPresenceOntimeIdx() + " + " + objPayInput.getPresenceOntimeIdxAdjust() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_IDX] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getPresenceOntimeIdx() + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_IDX_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getPresenceOntimeIdxAdjust() + "\" /> " + "</div>");//5
            /*5*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getPresenceOntimeTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + Formater.formatWorkDayHoursMinutes((float) objPayInput.getPresenceOntimeTimeAdjust(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getPresenceOntimeTime(), formatNumberFloat) + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getPresenceOntimeTimeAdjust(), formatNumberFloat) + "\" /> " + "</div>");  //6
            /*6*/
                rowx.add("<div align=\"right\">" + objPayInput.getLateIdx() + " + " + objPayInput.getLateIdxAdjust() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_IDX] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getLateIdx() + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_IDX_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getLateIdxAdjust() + "\" /> " + "</div>");  //7
            /*7*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getLateTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + Formater.formatWorkDayHoursMinutes((float) objPayInput.getLateTimeAdjust(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getLateTime(), formatNumberFloat) + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getLateTimeAdjust(), formatNumberFloat) + "\" /> " + "</div>");  //8
            /*8*/
                rowx.add("<div align=\"right\">" + objPayInput.getEarlyHomeIdx() + " + " + objPayInput.getEarlyHomeIdxAdjust() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getEarlyHomeIdx() + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getEarlyHomeIdxAdjust() + "\" /> " + "</div>");  //9
            
                /*9*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getEarlyHomeTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + Formater.formatWorkDayHoursMinutes((float) objPayInput.getEarlyHomeTimeAdjust(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getEarlyHomeTime(), formatNumberFloat) + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getEarlyHomeTimeAdjust(), formatNumberFloat) + "\" /> " + "</div>");  //10
                /*10*/
                rowx.add("<div align=\"right\">" + objPayInput.getLateEarlyIdx() + " + " + objPayInput.getLateEarlyIdxAdjust() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getLateEarlyIdx() + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getLateEarlyIdxAdjust() + "\" /> " + "</div>");  //11
            /*11*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getLateEarlyTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + Formater.formatWorkDayHoursMinutes((float) objPayInput.getLateEarlyTimeAdjust(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getLateEarlyTime(), formatNumberFloat) + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getLateEarlyTimeAdjust(), formatNumberFloat) + "\" /> " + "</div>");//12  

                /*8*/
                rowx.add("<div align=\"right\">" + objPayInput.getOnlyInIdx() + " + " + objPayInput.getOnlyInIdxAdjust() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getOnlyInIdx() + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getOnlyInIdxAdjust() + "\" /> " + "</div>");  //9
            /*9*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getOnlyInTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + Formater.formatWorkDayHoursMinutes((float) objPayInput.getOnlyInTimeAdjust(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_TIME] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getOnlyInTime(), formatNumberFloat) + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_TIME_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getOnlyInTimeAdjust(), formatNumberFloat) + "\" /> " + "</div>");  //10
            
                /*8*/
                rowx.add("<div align=\"right\">" + objPayInput.getOnlyOutIdx() + " + " + objPayInput.getOnlyOutIdxAdjust() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getOnlyOutIdx() + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getOnlyOutIdxAdjust() + "\" /> " + "</div>");  //9
 /*9*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getOnlyOutTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + Formater.formatWorkDayHoursMinutes((float) objPayInput.getOnlyOutTimeAdjust(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_TIME] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getOnlyOutTime(), formatNumberFloat) + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_TIME_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getOnlyOutTimeAdjust(), formatNumberFloat) + "\" /> " + "</div>");  //10
            
                
                if (listReason != null && listReason.size() > 0) { //jika ada yg ingin menggunakan mangkir
                    for (int idxRes = 0; idxRes < listReason.size(); idxRes++) {
                        Reason reason = (Reason) listReason.get(idxRes);
                        /*12*/ rowx.add("<div align=\"right\">" + objPayInput.getReasonIdx(reason.getNo(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()) + " + " + objPayInput.getReasonIdxAdjust(reason.getNo(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX] + "_" + reason.getNo() + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getReasonIdx(reason.getNo(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()) + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_ADJUSMENT] + "_" + reason.getOID() + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getReasonIdxAdjust(reason.getNo(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()) + "\" /> " + "</div>");  //13
                    /*13*/
                        rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getReasonTime(reason.getNo(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + Formater.formatWorkDayHoursMinutes((float) objPayInput.getReasonTimeAdjust(reason.getNo(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME] + "_" + reason.getNo() + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getReasonTime(reason.getNo(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()), formatNumberFloat) + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_ADJUSMENT] + "_" + reason.getOID() + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getReasonTimeAdjust(reason.getNo(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()), formatNumberFloat) + "\" /> " + "</div>");//14 
                    }
                }
                /*14*/ rowx.add("<div align=\"right\">" + objPayInput.getAbsenceIdx() + " + " + objPayInput.getAbsenceIdxAdjust() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getAbsenceIdx() + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getAbsenceIdxAdjust() + "\" /> " + "</div>");  //15
            /*15*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getAbsenceTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + Formater.formatWorkDayHoursMinutes((float) objPayInput.getAbsenceTimeAdjust(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getAbsenceTime(), formatNumberFloat) + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getAbsenceTimeAdjust(), formatNumberFloat) + "\" /> " + "</div>");  //16
            /*16*/
                rowx.add("<div align=\"right\">" + objPayInput.getProsentaseOK() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_OK] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getProsentaseOK() + "\" /> " + "</div>");  //17
            /*17*/
                rowx.add("<div align=\"right\">" + objPayInput.getDayOffSchedule() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_DAY_OFF_SCHEDULE] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getDayOffSchedule() + "\" /> " + "</div>");  //17


                /*18*/ rowx.add("<div align=\"right\">" + objPayInput.getAlIdx() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PAY_INPUT_PAID_LEAVE_AL_IDX] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getAlIdx() + "\" /> " + "</div>");  //18
            /*19*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getAlTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PAY_INPUT_PAID_LEAVE_AL_TIME] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getAlTime(), formatNumberFloat) + "\" /> " + "</div>");  //19
            /*20*/
                rowx.add("<div align=\"right\">" + objPayInput.getLlIdx() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PAY_INPUT_PAID_LEAVE_LL_IDX] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getLlIdx() + "\" /> " + "</div>");  //20
            /*21*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getLlTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PAY_INPUT_PAID_LEAVE_LL_TIME] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getLlTime(), formatNumberFloat) + "\" /> " + "</div>");//21  
            /*22*/
                rowx.add("<div align=\"right\">" + objPayInput.getDpIdx() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PAY_INPUT_PAID_LEAVE_DP_IDX] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getDpIdx() + "\" /> " + "</div>");  //22
            /*23*/
                rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getDpTime(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PAY_INPUT_PAID_LEAVE_DP_TIME] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getDpTime(), formatNumberFloat) + "\" /> " + "</div>");  //23
//            /*24*/rowx.add("<div align=\"right\">"+payInputPaidLeaveDetail.getSuIdx()+" <input type=\"hidden\" name=\""+FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PAY_INPUT_PAID_LEAVE_SU_IDX]+"_"+objPayInput.getEmployeeId()+"\" size=\"14\" value=\""+payInputPaidLeaveDetail.getSuIdx()+"\" /> "+"</div>"); //24 
//            /*25*/rowx.add("<div align=\"right\">"+payInputPaidLeaveDetail.getSuTime()+" <input type=\"hidden\" name=\""+FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PAY_INPUT_PAID_LEAVE_SU_TIME]+"_"+objPayInput.getEmployeeId()+"\" size=\"14\" value=\""+payInputPaidLeaveDetail.getSuTime()+"\" /> "+"</div>");  //25

                /*26*/ rowx.add("<div align=\"right\">" + Formater.formatWorkDayHoursMinutes((float) objPayInput.getUnPaidLeave(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " + " + Formater.formatWorkDayHoursMinutes((float) objPayInput.getUnPaidLeaveAdjust(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_UNPAID_lEAVE] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getUnPaidLeave(), formatNumberFloat) + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_UNPAID_LEAVE_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getUnPaidLeaveAdjust(), formatNumberFloat) + "\" /> " + "</div>");  //26

                /*26*/ rowx.add("<div align=\"right\">" + objPayInput.getInsentif() + " + " + objPayInput.getInsentifAdjusment() + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_INSENTIF] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getInsentif() + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_INSENTIF_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + objPayInput.getInsentifAdjusment() + "\" /> " + "</div>");  //26
                //jika menggunakan overtime
                if (showOT == 0) {
                    /*27*/ rowx.add("<div align=\"right\">" + Formater.formatNumber(objPayInput.getOtIdxPaidSalary(), formatNumberFloat) + " + " + Formater.formatNumber(objPayInput.getOtIdxPaidSalaryAdjust(), formatNumberFloat) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_SALARY] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getOtIdxPaidSalary(), formatNumberFloat) + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_SALARY_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getOtIdxPaidSalaryAdjust(), formatNumberFloat) + "\" /> " + "</div>");  //27
            /*28*/
                    rowx.add("<div align=\"right\">" + Formater.formatNumber(objPayInput.getOtIdxPaidDp(), formatNumberFloat) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_DP] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getOtIdxPaidDp(), formatNumberFloat) + "\" /> " + "</div>");

                    /*29*/ rowx.add("<div align=\"right\">" + Formater.formatNumber(objPayInput.getOtAllowanceMoney(), formatNumberInt) + " + " + Formater.formatNumber(objPayInput.getOtAllowanceMoneyAdjust(), formatNumberInt) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_MONEY] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getOtAllowanceMoney(), formatNumberInt) + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_MONEY_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getOtAllowanceMoneyAdjust(), formatNumberFloat) + "\" /> " + "</div>");  //28
            /*30*/
                    rowx.add("<div align=\"right\">" + Formater.formatNumber(objPayInput.getOtAllowanceFood(), formatNumberInt) + " + " + Formater.formatNumber(objPayInput.getOtAllowanceFoodAdjust(), formatNumberInt) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getOtAllowanceFood(), formatNumberInt) + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getOtAllowanceFoodAdjust(), formatNumberInt) + "\" /> " + "</div>");  //29
                }
                rowx.add("<div align=\"right\">" + Formater.formatNumber(objPayInput.getNightAllowance(), formatNumberInt) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_NIGHT_ALLOWANCE] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getNightAllowance(), formatNumberInt) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_NIGHT_ALLOWANCE_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getNightAllowanceAdjusment(), formatNumberInt) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_NIGHT_ALLOWANCE_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");
                 rowx.add("<div align=\"right\">" + Formater.formatNumber(objPayInput.getTransportAllowance(), formatNumberInt) + " + " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_TRANSPORT_ALLOWANCE] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getTransportAllowance(), formatNumberInt) + "\" /> " + " <input type=\"text\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_TRANSPORT_ALLOWANCE_ADJUSMENT] + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getTransportAllowanceAdjusment(), formatNumberInt) + "\" /> " + "<input name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_TRANSPORT_ALLOWANCE_CHK_BOX] + "\" type=\"checkbox\" value=\"" + objPayInput.getEmployeeId() + "_" + objPayInput.getPeriodId() + "_" + objPayInput.getPaySlipId() + "\"/>" + "</div>");
                if (listPos != null && listPos.size() > 0) {
                    for (int idxPos = 0; idxPos < listPos.size(); idxPos++) {
                        Position position = (Position) listPos.get(idxPos);
                        rowx.add("<div align=\"right\">" + Formater.formatNumber(objPayInput.getPositionIdx(position.getOID(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()), formatNumberFloat) + " + " + Formater.formatNumber(objPayInput.getPositionAdjust(position.getOID(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()), formatNumberFloat) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_IDX] + "_" + position.getOID() + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getPositionIdx(position.getOID(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()), formatNumberFloat) + "\" /> " + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_ADJUSMENT] + "_" + position.getOID() + "_" + objPayInput.getEmployeeId() + "\" size=\"14\" value=\"" + Formater.formatNumber(objPayInput.getPositionAdjust(position.getOID(), objPayInput.getPeriodId(), objPayInput.getEmployeeId()), formatNumberFloat) + "\" /> " + "</div>");
                    }
                }
                /*31*/ rowx.add("<div align=\"right\">" + objPayInput.getPrivateNote() + "<input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRIVATE_NOTE] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + objPayInput.getPrivateNote() + "\" /> " + "</div>");  //30
            /*32*/
                rowx.add("<div align=\"right\">" + Formater.formatNumber(objPayInput.getEmpWorkDays(), formatNumberInt) + " <input type=\"hidden\" name=\"" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EMP_WORK_DAYS] + "_" + objPayInput.getEmployeeId() + "\" value=\"" + Formater.formatNumber(objPayInput.getEmpWorkDays(), formatNumberInt) + "\" /> " + "</div>"); //31 
            /*33*/
                rowx.add("<a href=\"javascript:cmdNote('" + objPayInput.getEmployeeId() + "','" + periodId + "','" + objPayInput.getOID() + "','" + (dtAjusment == null ? new Date().getTime() : dtAjusment.getTime()) + ")\"><img border=\"0\" src=\"../../images/BtnNew.jpg\" width=\"14\" height=\"14\"></a>");//32

                /*34*/ rowx.add("<input type=\"checkbox\" name=\"prosess" + i + "\" value=\"" + 1 + "\" class=\"formElemen\" size=\"10\"  >");//33
            /*35*/
                rowx.add("<input type=\"checkbox\" name=\"status" + i + "\" value=\"" + 1 + "\" class=\"formElemen\" size=\"10\">");//34
            /*36*/
                rowx.add(objPayInput.getNote()); //36

            }
        } catch (Exception exc) {
            System.out.println("exc getListPayInput" + exc);
        }

    }

    /**
     * @return the vListManyCheckBox
     */
    public Vector getvListManyCheckBox() {
        return vListManyCheckBox;
    }

    /**
     * @param vListManyCheckBox the vListManyCheckBox to set
     */
    public void setvListManyCheckBox(Vector vListManyCheckBox) {
        this.vListManyCheckBox = vListManyCheckBox;
    }

    /**
     * @return the sSelectedChkBox
     */
    public Vector getsSelectedChkBox() {
        return sSelectedChkBox;
    }

    /**
     * @param sSelectedChkBox the sSelectedChkBox to set
     */
    public void setsSelectedChkBox(Vector sSelectedChkBox) {
        this.sSelectedChkBox = sSelectedChkBox;
    }

    /**
     * @return the vListVctErrorMessage
     */
    public Vector getvListVctErrorMessage() {
        return vListVctErrorMessage;
    }

    /**
     * @param vListVctErrorMessage the vListVctErrorMessage to set
     */
    public void addvListVctErrorMessage(String message) {
        this.vListVctErrorMessage.add(message);
    }
}
