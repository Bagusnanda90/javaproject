 <%@page import="com.dimata.harisma.session.leave.SessLeaveApp"%>
<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="com.dimata.harisma.entity.overtime.Overtime"%>
<%@page import="com.dimata.harisma.entity.overtime.TableHitungOvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.overtime.HashTblOvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.overtime.OvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.overtime.PstOvertimeDetail"%>
<%@page import="com.dimata.harisma.printout.PayPrintText"%>
<%@ page language="java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>

<%@ page import = "com.dimata.util.*" %>

<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<%@ page import = "java.util.Date" %>


<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.session.payroll.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.SessEmpSchedule" %>


<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_PROCESS_INPUT);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%!//public String drawList(JspWriter outJsp,boolean privViewDetail, int iCommand, Vector objectClass,Hashtable sumOvertimeDailyDurIdx,long periodId,int dayOfMonth,String salaryLevel,double minOvtDuration,I_PayrollCalculator payrollCalculator ,int iPropInsentifLevel,int iPropNotCalculateALDPLL,Vector listAttdAbsensi,Hashtable  sumOvertimeMealAllowanceMoney,HttpServletRequest request/*,Hashtable hashCheked*/,Vector listPos,Vector listReason){  
    public String drawList(JspWriter outJsp, boolean privViewDetail, int iCommand, Vector listPayInput, HttpServletRequest request, Vector listPos, Vector listReason, ParamPayInput paramPayInput, Hashtable listPayInputTbl, Hashtable existPayInputId, Hashtable hashPrevPrivateNote, int[] nightAllow) {
        String result = "";
        if (paramPayInput == null) {
            return result;
        }
        long unpaidLeaveOids[] = PstScheduleSymbol.getOidArrayUnpaidLeave();
        /*  PstScheduleSymbol.getOidArrayUnpaidLeave();
         * ============================================
            SELECT SYM.SCHEDULE_ID
            FROM hr_schedule_symbol  AS SYM 
            INNER JOIN hr_schedule_category  AS CAT ON SYM.SCHEDULE_CATEGORY_ID = CAT.SCHEDULE_CATEGORY_ID
            WHERE CAT.CATEGORY_TYPE = 'Unpaid Leave'
         *  return : SCHEDULE_ID
         *  result : 504404559441538049, 504404559441572519, 504404559441601239, ...
         */
        int statusPayInput = 0;//FRMQueryString.requestInt(request, "status_pay_input");
        Date dtAdjusment = FRMQueryString.requestDate(request, "date_adjusment");
        String flagSaveAdjusment = FRMQueryString.requestString(request, "flagSaveAdjusment");
        int showOvertime = 0;
        try {
            showOvertime = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY"));
            /* PstSystemProperty.getValueByName("ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY");
             * SELECT VALUEPROP FROM hr_system_property WHERE `NAME`='ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY';
             * result VALUEPROP = 0
             */ 
        } catch (Exception ex) {

            System.out.println("<blink>ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY NOT TO BE SET</blink>");
            showOvertime = 0;
        }
        I_Atendance attdConfig = null;
        try {
            // akan melakukan pembuatan class
            attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
            /* PstSystemProperty.getValueByName("ATTENDANCE_CONFIG");
             * SELECT VALUEPROP FROM hr_system_property WHERE `NAME`='ATTENDANCE_CONFIG';
             * result VALUEPROP = com.dimata.harisma.entity.attendance.AttendanceConfigRamayanaMM
             */
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
        }
        I_Leave leaveConfig = null;

        try {
            // proses ini akan melakukan pembuatan class
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
            /* PstSystemProperty.getValueByName("LEAVE_CONFIG");
             * SELECT VALUEPROP FROM hr_system_property WHERE `NAME`='LEAVE_CONFIG';
             * result VALUEPROP = com.dimata.harisma.session.leave.LeaveConfigDinamis
             */
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        //Vector token = new Vector(1,1);
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setCellStyles("listgensellstyles");
        ctrlist.setRowSelectedStyles("rowselectedstyles");
        ctrlist.setHeaderStyle("listheaderJs");
        //mengambil nama dari kode komponent
        ctrlist.addHeader("No", "5%", "2", "0");
        ctrlist.addHeader("Employee <br>Nr.", "5%", "2", "0");
        ctrlist.addHeader("Nama", "12%", "2", "0");
        ctrlist.addHeader("Position", "20%", "2", "0");
        //String selectedPO ="<a href=\"Javascript:SetAllCheckBoxesPO('frm_input_pay', true)\">Select</a> | <a href=\"Javascript:SetAllCheckBoxesPO('frm_input_pay', false)\">Clear</a>";
        String selectedPoIdx = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_IDX_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_IDX_CHK_BOX] + "')\">Clear</a>";
        String selectedPoTime = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME_CHK_BOX] + "')\">Clear</a>";
        ctrlist.addHeader("Presence On Time (Sts.Ok)", "20%", "0", "2");
        ctrlist.addHeader("Idx <br>" + selectedPoIdx, "2%", "0", "0");
        ctrlist.addHeader("Time <br>" + selectedPoTime, "4%", "0", "0");

        String selectedLtIdx = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_IDX_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_IDX_CHK_BOX] + "')\">Clear</a>";
        String selectedLtTime = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME_CHK_BOX] + "')\">Clear</a>";
        ctrlist.addHeader("Late", "20%", "0", "2");
        ctrlist.addHeader("Idx <br>" + selectedLtIdx, "2%", "0", "0");
        ctrlist.addHeader("Time <br>" + selectedLtTime, "4%", "0", "0");

        String selectedEhIdx = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX_CHK_BOX] + "')\">Clear</a>";
        String selectedEhTime = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME_CHK_BOX] + "')\">Clear</a>";
        ctrlist.addHeader("Early Home", "20%", "0", "2");
        ctrlist.addHeader("Idx <br>" + selectedEhIdx, "2%", "0", "0");
        ctrlist.addHeader("Time <br>" + selectedEhTime, "4%", "0", "0");

        String selectedLeIdx = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX_CHK_BOX] + "')\">Clear</a>";
        String selectedLeTime = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME_CHK_BOX] + "')\">Clear</a>";
        ctrlist.addHeader(" Late Early", "20%", "0", "2");
        ctrlist.addHeader("Idx <br>" + selectedLeIdx, "2%", "0", "0");
        ctrlist.addHeader("Time <br>" + selectedLeTime, "4%", "0", "0");


        String selectedOIIdx = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_CHK_BOX] + "')\">Clear</a>";
        String selectedOITime = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_TIME_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_IN_TIME_CHK_BOX] + "')\">Clear</a>";
        ctrlist.addHeader(" Only IN", "20%", "0", "2");
        ctrlist.addHeader("Idx <br>" + selectedOIIdx, "2%", "0", "0");
        ctrlist.addHeader("Time <br>" + selectedOITime, "4%", "0", "0");
        
        String selectedOOIdx = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_CHK_BOX] + "')\">Clear</a>";
        String selectedOOTime = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_TIME_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ONLY_OUT_TIME_CHK_BOX] + "')\">Clear</a>";
        ctrlist.addHeader(" Only OUT", "20%", "0", "2");
        ctrlist.addHeader("Idx <br>" + selectedOOIdx, "2%", "0", "0");
        ctrlist.addHeader("Time <br>" + selectedOOTime, "4%", "0", "0");
                
        
        if (listReason != null && listReason.size() > 0) {

            ctrlist.addHeader("Reason", "20%", "0", "" + (listReason.size() * 2));
            for (int idxRes = 0; idxRes < listReason.size(); idxRes++) {
                Reason reason = (Reason) listReason.get(idxRes);
                String selectedMKIdx = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + (FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_CHK_BOX] + "_" + reason.getNo()) + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + (FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_CHK_BOX] + "_" + reason.getNo()) + "')\">Clear</a>";
                String selectedMKTime = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + (FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_CHK_BOX] + "_" + reason.getNo()) + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + (FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_CHK_BOX] + "_" + reason.getNo()) + "')\">Clear</a>";
                ctrlist.addHeader(reason.getReason() + "_Idx <br>" + selectedMKIdx, "2%", "0", "0");
                ctrlist.addHeader(reason.getReason() + "_Time <br>" + selectedMKTime, "4%", "0", "0");
            }
        }


        String selectedAbsIdx = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX_CHK_BOX] + "')\">Clear</a>";
        String selectedAbsTime = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME_CHK_BOX] + "')\">Clear</a>";
        ctrlist.addHeader(" Absence", "20%", "0", "2");
        ctrlist.addHeader("Idx <br>" + selectedAbsIdx, "2%", "0", "0");
        ctrlist.addHeader("Time <br>" + selectedAbsTime, "4%", "0", "0");

        ctrlist.addHeader("Presence Ok (%)", "2%", "2", "0");
        ctrlist.addHeader("Days OFF Sch", "10%", "2", "0");

        ctrlist.addHeader(" Paid Leave", "20%", "0", "6");
        ctrlist.addHeader("AL Idx", "2%", "0", "0");
        ctrlist.addHeader("AL Time", "2%", "0", "0");
        ctrlist.addHeader("LL Idx", "4%", "0", "0");
        ctrlist.addHeader("LL Time", "4%", "0", "0");
        ctrlist.addHeader("DP Idx", "4%", "0", "0");
        ctrlist.addHeader("DP Time", "4%", "0", "0");
        //ctrlist.addHeader("SU Idx","4%", "0", "0");
        //ctrlist.addHeader("SU Time","4%", "0", "0");

        String selectedUnPaidLeave = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_UNPAID_LEAVE_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_UNPAID_LEAVE_CHK_BOX] + "')\">Clear</a>";
        ctrlist.addHeader(" UnPaid Leave <br>" + selectedUnPaidLeave, "5%", "2", "0");
        String selectedInsentif = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_INSENTIF_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_INSENTIF_CHK_BOX] + "')\">Clear</a>";
        ctrlist.addHeader("Insentif <br>" + selectedInsentif, "5%", "2", "0");
        
        if (showOvertime == 0) {
            String selectedOtIdx = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_SALARY_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_SALARY_CHK_BOX] + "')\">Clear</a>";
            ctrlist.addHeader(" OT. Idx Paid ", "20%", "0", "2");
            ctrlist.addHeader("Salary <br>" + selectedOtIdx, "2%", "0", "0");
            ctrlist.addHeader("Day Off Payment", "4%", "0", "0");

            String selectedAllowance = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_MONEY_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_MONEY_CHK_BOX] + "')\">Clear</a>";
            String selectedFood = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_CHK_BOX] + "')\">Clear</a>";
            ctrlist.addHeader(" Allowance", "20%", "0", "2");
            ctrlist.addHeader("Money <br>" + selectedAllowance, "2%", "0", "0");
            ctrlist.addHeader("Food <br>" + selectedFood, "4%", "0", "0");
        }

        /*
         * description : menambah baris kode action check box
         * 2014-11-25
         * mchen 
         */
        
        String selectedNight = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_NIGHT_ALLOWANCE_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_NIGHT_ALLOWANCE_CHK_BOX] + "')\">Clear</a>";
        String selectedTransport = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_TRANSPORT_ALLOWANCE_CHK_BOX] + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_TRANSPORT_ALLOWANCE_CHK_BOX] + "')\">Clear</a>";
        ctrlist.addHeader("Allowances", "20%", "0", "2");
        ctrlist.addHeader("Night Allowance <br>" + selectedNight, "2%", "0", "0");
        ctrlist.addHeader("Transport Allowance<br>" + selectedTransport, "4%", "0", "0");


        if (listPos != null && listPos.size() > 0) {
            ctrlist.addHeader("Position", "20%", "0", "" + listPos.size());
            for (int idxPos = 0; idxPos < listPos.size(); idxPos++) {
                Position position = (Position) listPos.get(idxPos);
                String selectedPostAdj = "<a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', true,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_CHK_BOX] + "_" + position.getOID() + "')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxes('frm_input_pay', false,'" + FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_POSITION_CHK_BOX] + "_" + position.getOID() + "')\">Clear</a>";
                ctrlist.addHeader(position.getPosition() + " <br>" + selectedPostAdj, "2%", "0", "0");
            }

        }



        ctrlist.addHeader("Private Note", "10%", "2", "0");

        ctrlist.addHeader("Emp.Work Days", "10%", "2", "0");

        ctrlist.addHeader("Note Admin", "10%", "2", "0");

        ctrlist.addHeader("Prosess <br> <a href=\"Javascript:SetAllCheckBoxesX('frm_input_pay', true,'prosess')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxesX('frm_input_pay', false,'prosess')\">Clear</a>", "10%", "2", "0");

        ctrlist.addHeader("Status <br> <a href=\"Javascript:SetAllCheckBoxesX('frm_input_pay', true,'status')\">Select</a> | <a href=\"Javascript:SetAllCheckBoxesX('frm_input_pay', false,'status')\">Clear</a>", "10%", "2", "0");

        ctrlist.addHeader("Note", "20%", "2", "0");

        if (false/*privViewDetail*/) {
            ctrlist.addHeader("Income", "10%", "2", "0");
            ctrlist.addHeader("Deduction", "5%", "2", "0");
        }

        String checked = "";
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        Vector rowx = new Vector(1, 1);
        //int index = -1;
        //String frmCurrency = "#,###";
// test 123
        if (listPayInput != null && listPayInput.size() > 0) {
            //ctrlist.drawListHeaderWithJs(outJsp);
            ctrlist.drawListHeaderWithJsVer2(outJsp);//header

            //long paidLeaveOids[] =PstScheduleSymbol.getOidArrayScheduleDpAlLlSpecial();
            //long specialLeaveOids[] =PstScheduleSymbol.getOidSpecial();
            //long unpaidLeaveOids[] =PstScheduleSymbol.getOidArrayUnpaidLeave ();
            Hashtable sumOvertimeDailyMoneyAllowance = new Hashtable();
            Hashtable sumOvertimeDailyPaidBySalary = new Hashtable();
            Hashtable sumOvertimeDailyMoneyFood = new Hashtable();
            Hashtable sumOvertimeDailyPaidByDp = new Hashtable();
            HashTblOvertimeDetail hashTblOvertimeDetail = new HashTblOvertimeDetail();
            Hashtable listTakenLeave = SessLeaveApp.checkLeaveTaken(paramPayInput.getsEmployeeId(), paramPayInput.getDtStart(), paramPayInput.getDtEnd());
            Vector listAttdAbsensi = new Vector();
           
            if (iCommand == Command.EDIT) { // test dikecogi
                sumOvertimeDailyMoneyAllowance = PstOvertimeDetail.summaryOTOffPayInput(paramPayInput.getDtStart(), paramPayInput.getDtEnd(), paramPayInput.getsEmployeeId(), -1, -1, -1, Overtime.ALLOWANCE_MONEY, 0);
                sumOvertimeDailyMoneyFood = PstOvertimeDetail.summaryOTOffPayInput(paramPayInput.getDtStart(), paramPayInput.getDtEnd(), paramPayInput.getsEmployeeId(), -1, -1, -1, Overtime.ALLOWANCE_FOOD, 0);
                sumOvertimeDailyPaidBySalary = PstOvertimeDetail.summaryOTOffPayInput(paramPayInput.getDtStart(), paramPayInput.getDtEnd(), paramPayInput.getsEmployeeId(), OvertimeDetail.PAID_BY_SALARY, -1, -1, -1, 0);
                sumOvertimeDailyPaidByDp = PstOvertimeDetail.summaryOTOffPayInput(paramPayInput.getDtStart(), paramPayInput.getDtEnd(), paramPayInput.getsEmployeeId(), OvertimeDetail.PAID_BY_DAY_OFF, -1, -1, -1, 0);
                hashTblOvertimeDetail = PstOvertimeDetail.HashOvertimeOverlapVer2(0, 0, 0, paramPayInput.getsEmployeeId(), paramPayInput.getDtStart(), paramPayInput.getDtEnd(), "");
                long strMl = System.currentTimeMillis();
                listAttdAbsensi = PstEmpSchedule.getListAttendace(attdConfig, paramPayInput.getLeaveConfig(), paramPayInput.getDtStart(), paramPayInput.getDtEnd(), paramPayInput.getsEmployeeId(), paramPayInput.getVctSchIDOff(), paramPayInput.getHashSchOff(), paramPayInput.getiPropInsentifLevel(), paramPayInput.getHolidaysTable(), paramPayInput.getHashPositionLevel(), paramPayInput.getPayrollCalculatorConfig(), paramPayInput.getHashPeriod(), hashTblOvertimeDetail);
                System.out.println("Durations >>>>> jsp" + (System.currentTimeMillis() - strMl));
            }
            
            String sEmployeeId = "";
            for (int i = 0; i < listPayInput.size(); i++) { // test for listPayInput
                rowx = new Vector(1, 1);
                PayInput payInput = new PayInput();
                Vector temp = (Vector) listPayInput.get(i);
                Employee employee = (Employee) temp.get(0);
                PaySlip paySlip = (PaySlip) temp.get(1);

                int idxPresenceOntime = 0;
                int idxPresenceOntimeAdjusment = 0;
                float timePresenceOntime = 0;
                float timePresenceOntimeAdjusment = 0;

                int idxLate = 0;
                int idxLateAdjusment = 0;
                float timeLate = 0;
                float timeLateAdjusment = 0;

                int idxEarlyHome = 0;
                int idxEarlyHomeAdjusment = 0;
                float timeEarlyHome = 0;
                float timeEarlyHomeAdjusment = 0;

                int idxLateEarly = 0;
                int idxLateEarlyAdjusment = 0;
                float timeLateEarly = 0;
                float timeLateEarlyAdjusment = 0;

                //by priska 20150320
                int idxOnlyIn = 0;
                int idxOnlyInAdjusment = 0;
                float timeOnlyIn = 0;
                float timeOnlyInAdjusment = 0;

                int idxOnlyOut = 0;
                int idxOnlyOutAdjusment = 0;
                float timeOnlyOut = 0;
                float timeOnlyOutAdjusment = 0;
                
                //ini akan dinamis
                int idxReason = 0;
                int idxReasonAdjusment = 0;
                float timeReason = 0;
                float timeReasonAdjusment = 0;

                int idxAbs = 0;
                int idxAbsAdjusment = 0;
                float timeAbs = 0;
                float timeAbsAdjusment = 0;
                

                float prosentasePresenceOk = 0;
                int dayOffSchedule = 0;

                float alIdx = 0;
                float alTime = 0;

                float llIdx = 0;
                float llTime = 0;

                float dpIdx = 0;
                float dpTime = 0;

                //int suIdx=0;
                //float suTime=0;

                float unpaidLeaveByForm = 0;
                float unpaidLeaveByAdjusment = 0;

                float insentifByForm = 0;
                float insentifByAdjusment = 0;

                double otIdxByForm = 0;
                double otIdxByAdjusment = 0;
                double otDayOffPaymentByForm = 0;

                double otMoneyAllowanceForm = 0;
                double otMoneyAllowanceAdjusment = 0;
                double otFoodAllowanceForm = 0;
                double otFootAllowanceAdjusment = 0;

                float positionByEmpOutlet = 0;
                float positionByAdjusment = 0;
                
                // mchen
                int idxNightAllowance = 0;
// test kode di bawah dikecogi lo...!
                if (listTakenLeave != null && listTakenLeave.size() > 0 && listTakenLeave.get(employee.getOID()) != null) {
                    LeaveApplicationSummary leaveApplicationSummary = (LeaveApplicationSummary) listTakenLeave.get(employee.getOID());
                    if (leaveApplicationSummary.getEmployeeId() != 0 && leaveApplicationSummary.getEmployeeId() == employee.getOID()) {
                        if (leaveApplicationSummary.getVsymbol() != null && leaveApplicationSummary.getVsymbol().size() > 0) {
                            for (int idxCT = 0; idxCT < leaveApplicationSummary.getVsymbol().size(); idxCT++) {
                                if (leaveApplicationSummary.getSymbol(idxCT).equalsIgnoreCase("AL")) {
                                    alTime = alTime + (float) leaveApplicationSummary.getJumlahTaken(idxCT);

                                    //alIdx = alIdx + 1; 
                                    alIdx = alIdx + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                    leaveApplicationSummary.getVsymbol().remove(idxCT);
                                    idxCT = idxCT - 1;
                                } else if (leaveApplicationSummary.getSymbol(idxCT).equalsIgnoreCase("DP")) {
                                    //dpIdx = dpIdx + 1;
                                    dpIdx = dpIdx + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                    dpTime = dpTime + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                    leaveApplicationSummary.getVsymbol().remove(idxCT);
                                    idxCT = idxCT - 1;
                                } else if (leaveApplicationSummary.getSymbol(idxCT).equalsIgnoreCase("LL")) {
                                    //llIdx = llIdx  + 1;
                                    llIdx = llIdx + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                    llTime = llTime + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                    leaveApplicationSummary.getVsymbol().remove(idxCT);
                                    idxCT = idxCT - 1;
                                }

                            }
                        }
                    }
                }
                payInput.setAlIdx(alIdx);
                payInput.setAlTime(alTime);
                payInput.setLlIdx(llIdx);
                payInput.setLlTime(llTime);
                payInput.setDpIdx(dpIdx);
                payInput.setDpTime(dpTime);
                payInput.setPeriodId(paramPayInput.getPeriodId());
                payInput.setEmployeeId(employee.getOID());
                //payInputPaidLeaveDetail.setSuIdx(suIdx);
                if (listPayInputTbl != null && listPayInputTbl.size() > 0) { // test dijemak
                    //PayInputTabel payInputTabel = (PayInputTabel)listPayInputTbl.get(""+payInputTabel.getPayInputCode()+"_"+payInputTabel.getPeriodId()+"_"+payInputTabel.getEmployeeId());
                    PstPayInput.resultToObject(payInput, listPayInputTbl, listReason, listPos);
                }
                int prosess = FRMQueryString.requestInt(request, "prosess" + i + "");
                int prosessStatus = FRMQueryString.requestInt(request, "status" + i + "");
                // test proses set data yg akan ditampilkan
                payInput.setEmployeeNumber(employee.getEmployeeNum());
                payInput.setEmployeeName(employee.getFullName());
                payInput.setPositionName(paySlip.getPosition());
                payInput.setPositionId(employee.getPositionId());
                payInput.setPaySlipId(paySlip.getOID());

                payInput.setPresenceOntimeIdx((int) paySlip.getDayPresent());// Presence (OK)

                payInput.setAbsenceIdx((int) paySlip.getDayAbsent());
                payInput.setUnPaidLeave(paySlip.getDayUnpaidLv());
                payInput.setLateIdx((int) paySlip.getDayLate());
                payInput.setProsentaseOK(paySlip.getProcentasePresence());
                payInput.setDayOffSchedule(paySlip.getDayOffSch());
                payInput.setInsentif(paySlip.getInsentif());
                payInput.setOtIdxPaidSalary(paySlip.getOvertimeIdxByForm());
                payInput.setOtIdxPaidSalaryAdjust(paySlip.getOvIdxAdj());
                payInput.setOtAllowanceMoney(paySlip.getMealAllowanceMoneyByForm());
                payInput.setOtAllowanceMoneyAdjust(paySlip.getMealAllowanceMoneyAdj());
                payInput.setNote(paySlip.getNote());
                payInput.setPrivateNote(paySlip.getPrivateNote());
                
                // mchen
                payInput.setNightAllowance(nightAllow[i]);//
                
                if (iCommand == Command.EDIT && prosess == 1) { // ini dikecogi lo...!!
                    ///searchParamnya
                    //query
                    //lalu action'nya
                    //untuk melihat data" overtime
                    if (listAttdAbsensi != null && listAttdAbsensi.size() > 0) {
                        for (int idx = 0; idx < listAttdAbsensi.size(); idx++) {
                            PayInputPresence payInputPresence = (PayInputPresence) listAttdAbsensi.get(idx);
                            if (employee.getOID() == payInputPresence.getEmployeeId()) {
                                idxPresenceOntime = payInputPresence.getPresenceOnTime();
                                //idxPresenceOntimeAdjusment 
                                timePresenceOntime = payInputPresence.getPresenceOnTimeTime();
                                //timePresenceOntimeAdjusment=0; 
                                idxLate = payInputPresence.getLate();
                                //idxLateAdjusment=0;
                                timeLate = payInputPresence.getLateTime();
                                //timeLateAdjusment=0;
                                idxEarlyHome = payInputPresence.getEarlyHome();
                                //idxEarlyHomeAdjusment=0;
                                timeEarlyHome = payInputPresence.getEarlyHomeTime();
                                //timeEarlyHomeAdjusment=0;
                                
                                //priska 20150321
                                 idxOnlyIn = payInputPresence.getTotalOnlyIn();
                                 timeOnlyIn= payInputPresence.getTimeOnlyIn();
                                
                                     //priska 20150321
                                 idxOnlyOut = payInputPresence.getTotalOnlyOut();
                                 timeOnlyOut= payInputPresence.getTimeOnlyOut();
                                
                                idxLateEarly = payInputPresence.getLateEarly();
                                //idxLateEarlyAdjusment=0;
                                timeLateEarly = payInputPresence.getLateEarlyTime();
                                //timeLateEarlyAdjusment=0;
                                payInput.addReasonIdx(payInputPresence.getReason());
                                payInput.addReasonTime(payInputPresence.getReasonTime());
                                idxAbs = payInputPresence.getAbsence();
                                //idxAbsAdjusment=payInputPresence.get;
                                timeAbs = payInputPresence.getAbsenceTime();//,timeAbsAdjusment=0;
                                dayOffSchedule = payInputPresence.getDayOffSchedule();
                                insentifByForm = payInputPresence.getInsentif();

                                payInput.setEmpWorkDays(payInputPresence.getTotalWorkingDays());
                                //untuk reason nnti ketika ngeset
                                listAttdAbsensi.remove(idx);
                                idx = idx - 1;
                                
                                idxNightAllowance = 3; // payInputPresence.getNightAllowance();
                                break; //jika sdh ketemu lalu di stop
                            }

                        }

                    }


                    if (sumOvertimeDailyPaidBySalary != null && sumOvertimeDailyPaidBySalary.size() > 0 && employee.getOID() != 0 && sumOvertimeDailyPaidBySalary.get(employee.getOID()) != null) {
                        TableHitungOvertimeDetail overtimeDetailDur = (TableHitungOvertimeDetail) sumOvertimeDailyPaidBySalary.get(employee.getOID());

                        if (overtimeDetailDur.getEmployee_id() != 0 && overtimeDetailDur.getEmployee_id() == employee.getOID()) {

                            otIdxByForm = 0;
                            if (overtimeDetailDur.getSizeTotIdx() != 0) {
                                for (int idxtot = 0; idxtot < overtimeDetailDur.getSizeTotIdx(); idxtot++) {
                                    otIdxByForm = otIdxByForm + overtimeDetailDur.getvTotIdx(idxtot);
                                }
                            }
                        }
                    }
                    if (sumOvertimeDailyPaidByDp != null && sumOvertimeDailyPaidByDp.size() > 0 && employee.getOID() != 0 && sumOvertimeDailyPaidByDp.get(employee.getOID()) != null) {
                        TableHitungOvertimeDetail overtimeDetailDur = (TableHitungOvertimeDetail) sumOvertimeDailyPaidByDp.get(employee.getOID());

                        if (overtimeDetailDur.getEmployee_id() != 0 && overtimeDetailDur.getEmployee_id() == employee.getOID()) {

                            otDayOffPaymentByForm = 0;
                            if (overtimeDetailDur.getSizePaidDp() != 0) {
                                for (int idxtot = 0; idxtot < overtimeDetailDur.getSizePaidDp(); idxtot++) {
                                    otDayOffPaymentByForm = otDayOffPaymentByForm + overtimeDetailDur.getvPaidDp(idxtot);
                                }
                            }
                        }
                    }
                    //update by satrya 2014-02-06
                    if (sumOvertimeDailyMoneyAllowance != null && sumOvertimeDailyMoneyAllowance.size() > 0 && employee.getOID() != 0 && sumOvertimeDailyMoneyAllowance.get(employee.getOID()) != null) {
                        TableHitungOvertimeDetail overtimeDetailMoneyAllowance = (TableHitungOvertimeDetail) sumOvertimeDailyMoneyAllowance.get(employee.getOID());

                        if (overtimeDetailMoneyAllowance.getEmployee_id() != 0 && overtimeDetailMoneyAllowance.getEmployee_id() == employee.getOID()) {

                            otMoneyAllowanceForm = 0;
                            if (overtimeDetailMoneyAllowance.getSizeAllowanceMoney() != 0) {
                                for (int idxtot = 0; idxtot < overtimeDetailMoneyAllowance.getSizeAllowanceMoney(); idxtot++) {
                                    otMoneyAllowanceForm = otMoneyAllowanceForm + overtimeDetailMoneyAllowance.getvAllowanceMoney(idxtot);
                                }
                            }
                        }
                    }

                    if (sumOvertimeDailyMoneyFood != null && sumOvertimeDailyMoneyFood.size() > 0 && employee.getOID() != 0 && sumOvertimeDailyMoneyFood.get(employee.getOID()) != null) {
                        TableHitungOvertimeDetail overtimeDetailAllowancefood = (TableHitungOvertimeDetail) sumOvertimeDailyMoneyFood.get(employee.getOID());

                        if (overtimeDetailAllowancefood.getEmployee_id() != 0 && overtimeDetailAllowancefood.getEmployee_id() == employee.getOID()) {

                            otFoodAllowanceForm = 0;
                            if (overtimeDetailAllowancefood.getSizeAllowanceFood() != 0) {
                                for (int idxtot = 0; idxtot < overtimeDetailAllowancefood.getSizeAllowanceFood(); idxtot++) {
                                    otFoodAllowanceForm = otFoodAllowanceForm + overtimeDetailAllowancefood.getvAllowanceFood(idxtot);
                                }
                            }
                        }
                    }


                    //action di sini di pakai jika di clik prosess


                    payInput.setPresenceOntimeIdx(idxPresenceOntime);
                    payInput.setPresenceOntimeTime(timePresenceOntime);
                    payInput.setLateIdx(idxLate);
                    payInput.setLateTime(timeLate);
                    payInput.setEarlyHomeIdx(idxEarlyHome);
                    payInput.setEarlyHomeTime(timeEarlyHome);
                    payInput.setLateEarlyIdx(idxLateEarly);
                    payInput.setLateEarlyTime(timeLateEarly);
                    //untuk reason sudah di atas
                    payInput.setAbsenceIdx(idxAbs);
                    payInput.setAbsenceTime(timeAbs);
                    
                    //priska 20150320
                    payInput.setOnlyInIdx(idxOnlyIn);
                    payInput.setOnlyInTime(timeOnlyIn);
                    payInput.setOnlyOutIdx(idxOnlyOut);
                    payInput.setOnlyOutTime(timeOnlyOut);
                    
                    // mchen night
                    payInput.setNightAllowance(idxNightAllowance);


                    payInput.setDayOffSchedule(dayOffSchedule);

                    unpaidLeaveByForm = PstSpecialUnpaidLeaveTaken.getSpUnQty(employee.getOID(), paramPayInput.getDtStart(), paramPayInput.getDtEnd(), unpaidLeaveOids);
                    payInput.setUnPaidLeave(unpaidLeaveByForm);
                    payInput.setInsentif(insentifByForm);
                    payInput.setOtIdxPaidSalary(otIdxByForm);
                    payInput.setOtIdxPaidDp(otDayOffPaymentByForm);
                    payInput.setOtAllowanceMoney(otMoneyAllowanceForm);
                    payInput.setOtAllowanceFood(otFoodAllowanceForm);



                    payInput.setNote(paySlip.getNote());
                    payInput.setPrivateNote(paySlip.getPrivateNote());
                    
                    payInput.setNightAllowance(4);

                    try {
                        prosentasePresenceOk = ((idxPresenceOntime + payInput.getPresenceOntimeIdxAdjust()) * 100) / ((idxPresenceOntime + payInput.getPresenceOntimeIdxAdjust()) + (idxLate + payInput.getLateIdxAdjust()) + (idxEarlyHome + payInput.getLateEarlyIdxAdjust()) + (idxLateEarly + idxAbs + payInput.getLateIdxAdjust()));
                    } catch (Exception exc) {
                        System.out.println("Exc prosentase" + exc);
                    }
                    payInput.setProsentaseOK(prosentasePresenceOk);
                    //maka di update langsung
                    //PstPaySlip.NO_APPROVE

                    PstPaySlip.updateWorkingDay(payInput, PstPaySlip.NO_APPROVE);

                    try {
                        PstPayInput.insertExcOrUpdateExc(payInput, listReason, listPos, existPayInputId);
                    } catch (Exception exc) {
                        System.out.print("Error insert pay input" + exc);
                    }


                } else {// test_1 idxPresenceOntime (OK)
                    idxPresenceOntime = payInput.getPresenceOntimeIdx();
                    idxLate = payInput.getLateIdx();
                    idxEarlyHome = payInput.getEarlyHomeIdx();
                    //priska
                    idxOnlyIn = payInput.getOnlyInIdx();
                    
                    idxOnlyOut = payInput.getOnlyOutIdx();
                    
                    idxLateEarly = payInput.getLateEarlyIdx();
                    idxAbs = payInput.getAbsenceIdx();
                    /// mchen
                    idxNightAllowance = payInput.getNightAllowance();
                }
                // if dibawah dikecogi
                if ((iCommand == Command.SAVE && flagSaveAdjusment != null && flagSaveAdjusment.equalsIgnoreCase("saveAdj"))) {
                    try {
                        prosentasePresenceOk = ((idxPresenceOntime + payInput.getPresenceOntimeIdxAdjust()) * 100) / ((idxPresenceOntime + payInput.getPresenceOntimeIdxAdjust()) + (idxLate + payInput.getLateIdxAdjust()) + (idxEarlyHome + payInput.getLateEarlyIdxAdjust()) + (idxLateEarly + payInput.getLateEarlyIdxAdjust()) + (idxAbs + payInput.getAbsenceIdxAdjust()));
                    } catch (Exception exc) {
                        System.out.println("Exc prosentase" + exc);
                    }
                    payInput.setProsentaseOK(prosentasePresenceOk);
                    //maka di update langsung
                    //PstPaySlip.NO_APPROVE

                    PstPaySlip.updateWorkingDay(payInput, PstPaySlip.NO_APPROVE);
                }
                /*try{
                 prosentasePresenceOk=( (idxPresenceOntime+payInput.getPresenceOntimeIdxAdjust())*100)/( (idxPresenceOntime+payInput.getPresenceOntimeIdxAdjust())+ (idxLate+payInput.getLateIdxAdjust())+ (idxEarlyHome+payInput.getLateEarlyIdxAdjust())+ (idxLateEarly+idxAbs+payInput.getLateIdxAdjust()));  
                 }catch(Exception exc){
                 System.out.println("Exc prosentase"+exc);
                 }*/
                //payInput.setProsentaseOK(prosentasePresenceOk);

                //payInput.setEmpWorkDays(idxPresenceOntime);
                if (iCommand == Command.SAVE) { // test dikecogi

                    //CtrlPayInput ctrlPayInput =  new CtrlPayInput(request);
                    //int actionError  = ctrlPayInput.action(iCommand,payInput,listReason,listPos); 
                    int docStatusPaySlip = FRMQueryString.requestInt(request, "status_pay_input");
                    //statusPayInput= docStatusPaySlip;
                    String privateNote = FRMQueryString.requestString(request, FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRIVATE_NOTE] + "_" + payInput.getEmployeeId());
                    payInput.setPrivateNote(privateNote);
                    if ((payInput.getPaySlipId() != 0 && privateNote != null && privateNote.length() > 0 && hashPrevPrivateNote != null && hashPrevPrivateNote.size() > 0 && hashPrevPrivateNote.containsKey("" + payInput.getPaySlipId()) && (!((String) hashPrevPrivateNote.get("" + payInput.getPaySlipId())).equalsIgnoreCase(privateNote)))) {
                        PstPaySlip.updateWorkingDay(payInput, docStatusPaySlip);
                    }
                    if (prosessStatus == 1) {
                        sEmployeeId = sEmployeeId + payInput.getEmployeeId() + ",";
                        statusPayInput = docStatusPaySlip;
                    }

                }


                try { // test FrmPayInput
                    // row baris
                    FrmPayInput.getListInput(attdConfig, leaveConfig, rowx, payInput, statusPayInput, dtAdjusment, paramPayInput.getPeriodId(), i, listPos, showOvertime, listReason);
                    /*for(int x=0;x<36;x++){
                     rowx.add(""+x); 
                     }*/
                    ctrlist.drawListRowJsVer2(outJsp, 0, rowx, i);// row mendapatkan nilai
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            } // end for (int i = 0; i < listPayInput.size(); i++)
            if (iCommand == Command.SAVE) {

                //CtrlPayInput ctrlPayInput =  new CtrlPayInput(request);
                //int actionError  = ctrlPayInput.action(iCommand,payInput,listReason,listPos); 
                int docStatusPaySlip = FRMQueryString.requestInt(request, "status_pay_input");
                if (sEmployeeId != null && sEmployeeId.length() > 0) {
                    sEmployeeId = sEmployeeId.substring(0, sEmployeeId.length() - 1);
                    PstPaySlip.updateWorkingDay(sEmployeeId, paramPayInput.getPeriodId(), docStatusPaySlip);
                }
            }
            result = "";
            //ctrlist.drawListEndTableJs(outJsp); //ctrlist.drawList(outJsp,0 ); //ctrlist.drawList(); 
            //update by satrya 2014-02-25
            ctrlist.drawListEndTableJsVer2(outJsp);
        } else {
            result = "<i>Belum ada data dalam sistem ...</i>";
        }
        return result;
    }
%>

<%
    boolean isSecretaryLogin = (positionType >= PstPosition.LEVEL_SECRETARY) ? true : false;
    long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>


<%
    int iCommand = FRMQueryString.requestCommand(request);
    int noteCommand = FRMQueryString.requestInt(request, "noteCommand");
    String noteToAll = FRMQueryString.requestString(request, "noteToAll");
    String allLeftSeparator = FRMQueryString.requestString(request, "allLeftSeparator");
    String allLeftSeparatorNew = FRMQueryString.requestString(request, "allLeftSeparatorNew");
    int statusPayInput = FRMQueryString.requestInt(request, "status_pay_input");
    Date dtAdjusment = FRMQueryString.requestDate(request, "date_adjusment");
    String levelCode = FRMQueryString.requestString(request, "level");
    //String levelCodeX = FRMQueryString.requestString(request,"levelCode");
    long companyId = FRMQueryString.requestLong(request, "companyId");
    long oidDivision = FRMQueryString.requestLong(request, "oidDivision");
    long oidDepartment = 0;
    long oidSection = 0;
    
    oidDepartment = FRMQueryString.requestLong(request,"oidDepartment");
    oidSection = FRMQueryString.requestLong(request,"oidSection");
    long departmentName = FRMQueryString.requestLong(request, "department");
    long sectionName = FRMQueryString.requestLong(request, "section");
    String searchNrFrom = FRMQueryString.requestString(request, "searchNrFrom");
    String searchNrTo = FRMQueryString.requestString(request, "searchNrTo");
    String searchName = FRMQueryString.requestString(request, "searchName");
    String searchNr = FRMQueryString.requestString(request, "searchNr");
    long periodId = FRMQueryString.requestLong(request, "periodId");
    int dataStatus = FRMQueryString.requestInt(request, "dataStatus");
    
    
    int n1 = FRMQueryString.requestInt(request, "n1");
    int n2 = FRMQueryString.requestInt(request, "n2");
    session.putValue("n1", n1);session.putValue("n2", n2);
    int isChekedRadioButtonSearchNr = FRMQueryString.requestInt(request, "radioButtonSerachNr");
    // variable added by mchen
    String startDatePeriod = "";
    String endDatePeriod = "";
    Date selectedDateFrom = null;
    Date selectedDateTo = null;
    int[] nightAllowanceEmp = new int[1000];
    
  
    if (allLeftSeparator == null || allLeftSeparator.length() == 0) {
        allLeftSeparator = "FYI:";
    }
    boolean privViewDetailX = privViewDetail;
    if (allLeftSeparatorNew == null || allLeftSeparatorNew.length() == 0) {
        allLeftSeparatorNew = "FYI:";
    }

    //update by satrya 2013-07-08
    I_Atendance attdConfig = null;
    try {
        attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
    } catch (Exception e) {
        System.out.println("Exception : " + e.getMessage());
        System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
    }

    //srcPayInput srcPayInputObj = new srcPayInput();
    
    session.putValue("periodIdses", periodId);
    session.putValue("companyIdses", companyId);
    // Hashtable hashCheked = new Hashtable();

    /*
     * Description : listPayInput digunakan untuk menampung data-data 
     * yang akan ditampilkan di drawList();
     */
    Vector listPayInput = new Vector(1, 1);
    //Vector listOtIdx= new Vector(1,1);
    Hashtable listPayInputTbl = new Hashtable();
    Hashtable existPayInputId = new Hashtable();

    /*mengambil nama period saat ini
     Updated By Yunny*/
    PayPeriod pr = new PayPeriod();
    //Period pr = new Period();
    String periodName = "";
    Date startDate = new Date();
    Double minOvtDuration = 0.0;
    String strMinOvtDur = String.valueOf(PstSystemProperty.getValueByName("MIN_OVERTM_DURATION"));

    //update by satrya 2013-02-20
    String parollCalculatorClassName = "";
    I_PayrollCalculator payrollCalculator = null;
    /*
     * CodeName : try {}catch{} 001 [PayPeriod]
     * Declaration :
     * - int periodId;
     * - PayPeriod pr;
     * Description :
     * mendapatkan data-data PayPeriod,
     * kemudian ditampung ke periodName, startDate
     */
    try {
        //update by satrya 2014-01-28
        if (periodId != 0) {
            pr = PstPayPeriod.fetchExc(periodId);
            periodName = pr.getPeriod();
            startDate = pr.getStartDate();
            if (strMinOvtDur != null && strMinOvtDur.length() > 0) {
                minOvtDuration = Double.parseDouble(strMinOvtDur);
            }
        }
        //	pr = PstPeriod.fetchExc(periodId);

    } catch (Exception e) {
        System.out.println("Exception" + e);
    }

    /*
     * Declaration :
     * # String parollCalculatorClassName;
     * # I_PayrollCalculator payrollCalculator;
     * Description :
     * # membuat object payrollCalculator
     */
    try {
        parollCalculatorClassName = PstSystemProperty.getValueByName("PAYROLL_CALC_CLASS_NAME");
        // result parollCalculatorClassName : com.dimata.harisma.session.payroll.PayrollCalculatorKTI
        if (parollCalculatorClassName == null || parollCalculatorClassName.length() < 1) {
            parollCalculatorClassName = "com.dimata.harisma.session.payroll.PayrollCalculator";
        }
        payrollCalculator = (I_PayrollCalculator) (Class.forName(parollCalculatorClassName).newInstance());
        if (pr != null) {
            payrollCalculator.initializedPreloadedData(pr.getStartDate(), pr.getEndDate());
        }
    } catch (Exception exc) {
        System.out.println(exc);
    }
    /*
     * Description :
     * # check if payrollCalculator obj is null
     */
    if (payrollCalculator == null) {
        try {
            parollCalculatorClassName = "com.dimata.harisma.session.payroll.PayrollCalculator";
            payrollCalculator = (I_PayrollCalculator) (Class.forName(parollCalculatorClassName).newInstance());
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }
    /*
     * Declaration :
     * # I_Leave leaveConfig;
     * Description :
     * # create object
     */
    I_Leave leaveConfig = null;

    try {
        leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
    } catch (Exception e) {
        System.out.println("Exception : " + e.getMessage());
    }
    /*
     * Declaration : int iPropInsentifLevel;
     * Description : hanya cuti full day jika fullDayLeave = 0
     */
    int iPropInsentifLevel = 0;
    try {
        iPropInsentifLevel = Integer.parseInt(PstSystemProperty.getValueByName("PAYROLL_INSENTIF_MAX_LEVEL"));
    } catch (Exception ex) {

        //System.out.println("Execption PAYROLL_INSENTIF_MAX_LEVEL: " + ex);
        System.out.println("<blink>PAYROLL_INSENTIF_MAX_LEVEL NOT TO BE SET</blink>");
    }
    /*
     * Declaration : int iPropNotCalculateALDPLL = 0;
     * Description : 0 artinya tidak, sedngkan 1 artinya di hitung cuti AL,DP,LL
     */
    int iPropNotCalculateALDPLL = 0;
    try {
        iPropNotCalculateALDPLL = Integer.parseInt(PstSystemProperty.getValueByName("PAYROLL_CALCULATE_UNPAID"));
    } catch (Exception ex) {
        iPropNotCalculateALDPLL = 0;
        //System.out.println("Execption PAYROLL_INSENTIF_MAX_LEVEL: " + ex);
        System.out.println("<blink>PAYROLL_PAYROLL_CALCULATE_UNPAID NOT TO BE SET</blink>");
    }

    /*
     * Declaration : long lHolidays = 0;
     * Description : get value long
     */
    long lHolidays = 0;
    try {
        lHolidays = Long.parseLong(PstSystemProperty.getValueByName("OID_PUBLIC_HOLIDAY"));
    } catch (Exception ex) {
        System.out.println("Execption PAYROLL_INSENTIF_MAX_LEVEL: " + ex);
    }
    /*
     * Declaration : I_PayrollCalculator payrollCalculatorConfig = null;
     * Description : create object payrollCalculatorConfig;
     */
    I_PayrollCalculator payrollCalculatorConfig = null;
    try {
        payrollCalculatorConfig = (I_PayrollCalculator) (Class.forName(PstSystemProperty.getValueByName("PAYROLL_CALC_CLASS_NAME")).newInstance());
    } catch (Exception e) {
        System.out.println("Exception PAYROLL_CALC_CLASS_NAME " + e.getMessage());
    }

    //Vector listAttdAbsensi= new Vector();
    //Hashtable sumOvertimeDailyPaidBySalary = new Hashtable();
    // HashTblOvertimeDetail hashTblOvertimeDetail = new HashTblOvertimeDetail();
    //Hashtable sumOvertimeDailyMoneyAllowance = new Hashtable();
    //Hashtable sumOvertimeDailyMoneyFood = new Hashtable();
    //Hashtable sumOvertimeDailyPaidByDp = new Hashtable();
    /*
     * Entity : ParamPayInput
     */
    ParamPayInput paramPayInput = new ParamPayInput();
    Vector listPos = new Vector();
    Vector listReason = new Vector();
    paramPayInput.setLeaveConfig(leaveConfig);
    paramPayInput.setPayrollCalculatorConfig(payrollCalculatorConfig);
    /*
     * Entity : PayPeriod;
     */
    PayPeriod payPeriod = new PayPeriod();
    String errorMsg = "";
     String getListEmployeeId = "";
     if (iCommand != Command.NONE){
    getListEmployeeId = PstEmployee.getSEmployeeIdJoinSalary(0, 0, companyId, oidDivision, departmentName, sectionName, periodId, levelCode, searchNrFrom, searchNrTo, searchName, dataStatus, isChekedRadioButtonSearchNr, searchNr, "EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
       }
    if (periodId != 0) {
        try {
            payPeriod = PstPayPeriod.fetchExc(periodId);
            //   period= PstPeriod.fetchExc(periodId);
        } catch (Exception exc) {
            System.out.println("Period id =" + periodId + " not in system or could not fetched from database ! <br> " + exc);
        }
    }

    if (noteCommand == Command.SAVE) {

        PstPaySlip.updateNote(periodId, noteToAll, allLeftSeparator, allLeftSeparatorNew, getListEmployeeId);
    }
    Hashtable hashPrevPrivateNote = new Hashtable();
    // test mchen
    if (iCommand == Command.LIST) {

        paramPayInput.setAllLeftSeparator(allLeftSeparator);
        paramPayInput.setAllLeftSeparatorNew(allLeftSeparatorNew);
        paramPayInput.setCompanyId(companyId);
        paramPayInput.setDataStatus(dataStatus);
        paramPayInput.setDepartmentName(departmentName);
        paramPayInput.setDtAdjusment(dtAdjusment);
        paramPayInput.setIsChekedRadioButtonSearchNr(isChekedRadioButtonSearchNr);
        paramPayInput.setLevelCode(levelCode);
        paramPayInput.setNoteCommand(noteCommand);
        paramPayInput.setNoteToAll(noteToAll);
        paramPayInput.setOidDivision(oidDivision);
        paramPayInput.setPeriodId(periodId);
        paramPayInput.setSearchName(searchName);
        paramPayInput.setSearchNr(searchNr);
        paramPayInput.setSearchNrFrom(searchNrFrom);
        paramPayInput.setSearchNrTo(searchNrTo);
        paramPayInput.setSectionName(sectionName);
        paramPayInput.setStatusPayInput(statusPayInput);
        paramPayInput.setDtStart(payPeriod.getStartDate());//2014-09-19        
        paramPayInput.setDtEnd(payPeriod.getEndDate());//2014-10-04
        paramPayInput.setsEmployeeId(getListEmployeeId);
        
        //mchen        
        selectedDateFrom = payPeriod.getStartDate();
        selectedDateTo = payPeriod.getEndDate();

        paramPayInput.setiPropInsentifLevel(iPropInsentifLevel);
        
        session.putValue("paramPayInput", paramPayInput);
        //listPayInput = SessPayInput.listPayInput(departmentName,companyId,oidDivision,(levelCode),sectionName,searchNrFrom,searchNrTo,searchName,periodId,dataStatus,isChekedRadioButtonSearchNr,searchNr); 
        if (paramPayInput != null && paramPayInput.getsEmployeeId() != null && paramPayInput.getsEmployeeId().length() > 0 && paramPayInput.getPeriodId() != 0) {
            listPayInput = SessPayInput.listPayInput(departmentName, companyId, oidDivision, (levelCode), sectionName, searchNrFrom, searchNrTo, searchName, periodId, dataStatus, isChekedRadioButtonSearchNr, searchNr);

            String whereClause = PstPayInput.fieldNames[PstPayInput.FLD_EMPLOYEE_ID] + " IN(" + paramPayInput.getsEmployeeId() + ") AND " + PstPayInput.fieldNames[PstPayInput.FLD_PERIODE_ID] + "=" + paramPayInput.getPeriodId(); 
            
            /*
             * Declaration : Hashtable listPayInputTbl = new Hashtable();
             * Description : listPayInputTbl menampung nilai untuk ditampilkan di form input
             */
            listPayInputTbl = PstPayInput.hashListPayInput(0, 0, whereClause, "");
            /*
             * Declaration : Hashtable existPayInputId = new Hashtable();
             * Description : existPayInputId only store Pay Input Id
             */
            existPayInputId = PstPayInput.hashChekcExistingPayInputId(0, 0, whereClause, "");
            
            listPos = PstPosition.list(0, 0, PstPosition.fieldNames[PstPosition.FLD_FLAG_POSITION_SHOW_IN_PAYROLL_INPUT] + "=" + PstPosition.YES_SHOW_PAY_INPUT, PstPosition.fieldNames[PstPosition.FLD_POSITION] + " ASC ");
            listReason = PstReason.list(0, 0, PstReason.fieldNames[PstReason.FLD_FLAG_IN_PAY_INPUT] + "=" + PstReason.SHOW_REASON_IN_PAY_INPUT_YES, PstReason.fieldNames[PstReason.FLD_REASON] + " ASC ");

        }

    } //ini terjadi ketika prosess All
    else if (iCommand == Command.EDIT || iCommand == Command.SAVE) {

        //untuk generate payslip Id
        if (periodId != 0) {
            SessPaySlip.generatePaySlip(periodId, (levelCode), companyId, oidDivision, departmentName, sectionName, searchNrFrom, searchNrTo, searchName, dataStatus);


            //Vector listPeriodDate = PstPeriod.getListStartEndDatePeriod(payPeriod.getStartDate(), payPeriod.getEndDate());                               



            listPos = PstPosition.list(0, 0, PstPosition.fieldNames[PstPosition.FLD_FLAG_POSITION_SHOW_IN_PAYROLL_INPUT] + "=" + PstPosition.YES_SHOW_PAY_INPUT, PstPosition.fieldNames[PstPosition.FLD_POSITION] + " ASC ");
            listReason = PstReason.list(0, 0, PstReason.fieldNames[PstReason.FLD_FLAG_IN_PAY_INPUT] + "=" + PstReason.SHOW_REASON_IN_PAY_INPUT_YES, PstReason.fieldNames[PstReason.FLD_REASON] + " ASC ");

            Hashtable vctSchIDOff = PstScheduleSymbol.getHashScheduleId(PstScheduleCategory.CATEGORY_OFF);
            Hashtable hashSchOff = PstScheduleSymbol.getHashScheduleIdOFF(PstScheduleCategory.CATEGORY_OFF);
            HolidaysTable holidaysTable = PstPublicHolidays.getHolidaysTable(payPeriod.getStartDate(), payPeriod.getEndDate());
            Hashtable hashPositionLevel = PstPosition.hashGetPositionLevel();
            Hashtable hashPeriod = PstPeriod.hashlistTblPeriod(0, 0, "", "");
            //selectedDateFrom, selectedDateTo, getListEmployeeId, 0, PstEmpSchedule.STATUS_PRESENCE_ABSENCE

            //action di sini di pakai jika ada adjusment
            //CtrlPayInput ctrlPayInput = new CtrlPayInput(request);
            //int actionError  = ctrlPayInput.action(iCommand);  
            paramPayInput.setAllLeftSeparator(allLeftSeparator);
            paramPayInput.setAllLeftSeparatorNew(allLeftSeparatorNew);
            paramPayInput.setCompanyId(companyId);
            paramPayInput.setDataStatus(dataStatus);
            paramPayInput.setDepartmentName(departmentName);
            paramPayInput.setDtAdjusment(dtAdjusment);
            paramPayInput.setIsChekedRadioButtonSearchNr(isChekedRadioButtonSearchNr);
            paramPayInput.setLevelCode(levelCode);
            paramPayInput.setNoteCommand(noteCommand);
            paramPayInput.setNoteToAll(noteToAll);
            paramPayInput.setOidDivision(oidDivision);
            paramPayInput.setPeriodId(periodId);
            paramPayInput.setSearchName(searchName);
            paramPayInput.setSearchNr(searchNr);
            paramPayInput.setSearchNrFrom(searchNrFrom);
            paramPayInput.setSearchNrTo(searchNrTo);
            paramPayInput.setSectionName(sectionName);
            paramPayInput.setStatusPayInput(statusPayInput);
            paramPayInput.setDtStart(payPeriod.getStartDate());
            paramPayInput.setDtEnd(payPeriod.getEndDate());
            paramPayInput.setsEmployeeId(getListEmployeeId);
            paramPayInput.setVctSchIDOff(vctSchIDOff);
            paramPayInput.setHashSchOff(hashSchOff);
            paramPayInput.setHolidaysTable(holidaysTable);
            paramPayInput.setiPropInsentifLevel(iPropInsentifLevel);
            paramPayInput.setHashPeriod(hashPeriod);
            paramPayInput.setHashPositionLevel(hashPositionLevel);

            
            session.putValue("paramPayInput", paramPayInput);
            
            if (paramPayInput != null && paramPayInput.getsEmployeeId() != null && paramPayInput.getsEmployeeId().length() > 0 && paramPayInput.getPeriodId() != 0) {
                if (iCommand == Command.SAVE) {
                    CtrlPayInput ctrlPayInput = new CtrlPayInput(request);
                    int actionError = ctrlPayInput.action(iCommand, listReason, listPos);
                    errorMsg = ctrlPayInput.getMessages();
                }
                listPayInput = SessPayInput.listPayInput(departmentName, companyId, oidDivision, (levelCode), sectionName, searchNrFrom, searchNrTo, searchName, periodId, dataStatus, isChekedRadioButtonSearchNr, searchNr);

                String whereClause = PstPayInput.fieldNames[PstPayInput.FLD_EMPLOYEE_ID] + " IN(" + paramPayInput.getsEmployeeId() + ") AND " + PstPayInput.fieldNames[PstPayInput.FLD_PERIODE_ID] + "=" + paramPayInput.getPeriodId();
                listPayInputTbl = PstPayInput.hashListPayInput(0, 0, whereClause, "");
                existPayInputId = PstPayInput.hashChekcExistingPayInputId(0, 0, whereClause, "");
                hashPrevPrivateNote = PstPaySlip.prevPrivateNotePaySlip(departmentName, companyId, oidDivision, (levelCode), sectionName, searchNrFrom, searchNrTo, searchName, periodId, dataStatus, isChekedRadioButtonSearchNr, searchNr);

            }
        }

    }


    if (iCommand == Command.LIST) {
        
        /*
        * Description : mengambil schedule_id dari hr_schedule_symbol
        * Date : 2014-12-01
        * Autor : Hendra McHen
        */
       Vector listScheduleSymbol = new Vector(1, 1);
       String whereSch = " "+ PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_NIGHT_ALLOWANCE]+"!=0 ";
       int countSch = PstScheduleSymbol.getCount(whereSch);
       String[] arrSymbol = new String[countSch];
       int[] arrNightValue = new int[countSch];
       listScheduleSymbol = PstScheduleSymbol.list(0, 500, whereSch, PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]);

       for(int i = 0; i < listScheduleSymbol.size(); i++){
           ScheduleSymbol sch = (ScheduleSymbol) listScheduleSymbol.get(i);
           arrSymbol[i] = sch.getSymbol();
           arrNightValue[i] = sch.getNightAllowance();
       }

       
        
        Vector listAttendanceRecordDailly = new Vector(1,1);
                                                    //listEmpPresenceDaily(long departmentId, Date fromDate, Date toDate, long sectionId, String empNumId, String fullName, String status1st, int limitStart, int recordToGet, Vector stStatus, int reasonSts, String stsEmpCategorySel, int statusResign, long oidCompany, long oidDivision)
        
        for(int g = 0; g < countSch; g++){
            for (int h = 0; h < listPayInput.size(); h++) { /// diulang sebanyak employee pay input

                Vector li = (Vector) listPayInput.get(h);
                Employee em = (Employee) li.get(0);
                listAttendanceRecordDailly = SessEmpSchedule.listEmpPresenceDaily(oidDepartment, selectedDateFrom, selectedDateTo, oidSection, em.getEmployeeNum(), em.getFullName(), "", 0, 17, null, 0, "", 0, 0, oidDivision); 
                for(int i=0; i < listAttendanceRecordDailly.size(); i++){
                    PresenceReportDaily presenceReportDaily = (PresenceReportDaily) listAttendanceRecordDailly.get(i);
                    // membadingkan object menggunakan equals
                    if(arrSymbol[g].equals(presenceReportDaily.getScheduleSymbol1())){
                        if(presenceReportDaily.getStatus1()==7){
                            nightAllowanceEmp[h] = nightAllowanceEmp[h] + arrNightValue[g];
                        }
                    }
                }

            }
        }
    }
%>

<html>
<head>
<title>HARISMA - Salary Process - Input</title>
 <%@ include file = "../../main/konfigurasi_jquery.jsp" %>    
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <script type="text/javascript" src="../../javascripts/jquery.min.js"></script>
    <script type="text/javascript" src="../../javascripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="../../javascripts/gridviewScroll.min.js"></script>
    <link href="../../stylesheets/GridviewScroll.css" rel="stylesheet" />
	
  
<SCRIPT language=JavaScript>
            function noBack() { window.history.forward(); }
            function cmdUpdateDiv(){
                document.frm_input_pay.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_input_pay.action="pay-input.jsp";
                document.frm_input_pay.submit();
            }
            function cmdUpdateDep(){
                document.frm_input_pay.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_input_pay.action="pay-input.jsp";
                document.frm_input_pay.submit();
            }
            function cmdUpdatePos(){
                document.frm_input_pay.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_input_pay.action="pay-input.jsp";
                document.frm_input_pay.submit();
            }
function fnTrapKD(){
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
}

function cmdSearch(){
	document.frm_input_pay.command.value="<%=Command.LIST%>";
	document.frm_input_pay.action="pay-input.jsp";
	document.frm_input_pay.submit();
}

function cmdSave(){
	document.frm_input_pay.command.value="<%=Command.SAVE%>";
	document.frm_input_pay.action="pay-input.jsp";
        document.frm_input_pay.flagSaveAdjusment.value="saveAdj";
	document.frm_input_pay.submit();
}

function cmdExportExcel(periodId){
    //alert(periodId);
    //alert(companyId);
    //document.frm_input_pay.periodId.value=periodId;
    //document.frm_input_pay.companyId.value=companyId;
     var linkPage = "<%=printroot%>report.PayInputXls"; 
    document.frm_input_pay.action="<%=printroot%>.report.payroll.PayInputXls"; 
    document.frm_input_pay.target = "ReportExcel";
    document.frm_input_pay.submit();
    document.frm_input_pay.target = "";
    
}

function cmdEdit(){
	document.frm_input_pay.command.value="<%=Command.EDIT%>";
	document.frm_input_pay.action="pay-input.jsp";
	document.frm_input_pay.submit();
}

function setNote(){
	document.frm_input_pay.noteCommand.value="<%=Command.SAVE%>";    
	document.frm_input_pay.command.value="<%=Command.LIST%>";
	document.frm_input_pay.action="pay-input.jsp";
	document.frm_input_pay.submit();
}

function cmdNote(empId,periodId,paySlipId,dtAdjusment){
   
    window.open("note_edit.jsp?command="+<%=Command.EDIT%>+"&FRM_PAY_SLIP_EMPLOYEE_ID="+empId+"&FRM_PAY_SLIP_NOTE_PERIODE="+periodId+"&FRM_PAY_SLIP_ID=" + paySlipId+"&FRM_PAY_SLIP_NOTE_DATE=" + dtAdjusment, null,"height=300,width=700,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
    window.focus();
}

function cmdLevel(employeeId,salaryLevel,paySlipId,paySlipPeriod){
        var detailYN=document.frm_input_pay.detail_slip.value;
        //alert(document.frm_input_pay.detail_slip);
        //alert(detailYN);
        var periodId =document.frm_input_pay.periodId.value; 
        // document.frm_input_pay.target="PayrollDetail";
	// document.frm_input_pay.action="pay-input-detail.jsp?employeeId=" + employeeId+ "&salaryLevel=" + salaryLevel+"&paySlipId=" + paySlipId +"&paySlipPeriod=" + paySlipPeriod+"&detail_slip"+detailYN;
	// document.frm_input_pay.command.value="<%=Command.LIST%>";
	// document.frm_input_pay.submit();                
        var strUrl = "pay-input-detail.jsp?command="+<%=Command.LIST%>+"&employeeId=" + employeeId+ "&salaryLevel=" + 
               salaryLevel+"&paySlipId=" + paySlipId +"&paySlipPeriod=" + paySlipPeriod+"&detail_slip="+detailYN+"&periodId="+periodId;
          // alert(strUrl);
        var detailWindow = window.open(strUrl);
        //detailWindow.document.write("Detail Payroll");
        detailWindow.focus();  
}

function openLevel(){
    var strUrl ="sel_salary-level.jsp?frmname=frm_input_pay";
    var levelWindow = window.open(strUrl);
    levelWindow.focus();
}

function cmdNoteAdmin(hiddenOID){	
	window.open("note_edit_admin.jsp?command="+<%=Command.EDIT%>+"&hidden_payslip="+hiddenOID, null, "height=300,width=500,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
}

function SetAllCheckBoxesX(FormName, CheckValue,name){
	    if(!document.forms[FormName]){
		return;
            }
            var obj = null;
            
               <% if(listPayInput!=null){ %>
                for(var i = 0 ; i < <%=listPayInput.size()%> ; i++){ 
                    var strName = name+""+i;
                    //alert(strName);
                    obj = document.getElementsByName(strName);                           
                    //alert("lenght: "+obj.length);
                    //alert("value: "+obj.value);
                    //alert("cheked: "+obj.checked);
                    //alert("cheked array: "+obj[0].checked);
                    //alert("value array: "+obj[0].value);
                    if(obj!=null){
                       obj[0].checked = CheckValue;
                    }
                 }            
             <%}%>              
        }
        
function SetAllCheckBoxes(FormName, CheckValue,FieldName)
{
	if(!document.forms[FormName])
		return;
	var objCheckBoxes = document.forms[FormName].elements[FieldName];
	if(!objCheckBoxes)
		return;
	var countCheckBoxes = objCheckBoxes.length;
	if(!countCheckBoxes)
		objCheckBoxes.checked = CheckValue;
	else
		// set the check value for all check boxes
                    //alert(CheckValue);
			for(var i = 0; i < countCheckBoxes; i++)
			objCheckBoxes[i].checked = CheckValue;
}



    


function disablefield(id) {
                            //btn   = document.forms['frm_input_pay'].elements[btn];
                            //cells = document.getElementsByName('t'+btn.name);
                            //mode = btn.checked ? showMode : 'none';
                            //for(j = 0; j < cells.length; j++) cells[j].style.display = mode; 
                            //alert(document.getElementById('active').checked.valueOf());
                            if (id== 'active' ) {
                                document.getElementById('search_by_range').style.display='block';
                                document.getElementById('search_selected').style.display='none';
                                document.frm_input_pay.searchNrFrom.value="";
                                document.frm_input_pay.searchNrTo.value="";
                                
                                //alert("satu");
                                // document.getElementById('positionHideen').disabled = '';
                                // document.getElementById('scheduleHideen').disabled='';
                                //document.getElementById('inputanActive').style.display='block';
                            } else {
                                document.getElementById('search_selected').style.display='block';
                                document.getElementById('search_by_range').style.display='none';
                                document.frm_input_pay.searchNr.value="";  
                                //alert("kedua");
                                // document.getElementById('positionHideen').disabled = 'disabled';
                                // document.getElementById('scheduleHideen').disabled='disabled';
                                //document.getElementById('inputanActive').style.display="none";
                            }
                        }
 

function clearLevelCode(){
    document.frm_input_pay.level.value="";
}


    function hideObjectForEmployee(){
        
    } 
	 
    function hideObjectForLockers(){ 
    }
	
    function hideObjectForCanteen(){
    }
	
    function hideObjectForClinic(){
    }

    function hideObjectForMasterdata(){
    }
	
	function showObjectForMenu(){
        
    }
</SCRIPT>

</head>

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">  
    <style>
.fakeContainer { /* The parent container */
    margin: 1px;
    padding: 1px;
    border: none;
    width: 100%; /* Required to set */
    height: 450px; /* Required to set */
    overflow: auto; /* Required to set */
}
.skinCon {
    float: inside;
    margin: 0px;
    border: none;
    width: 1310px;
}

</style>
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
   <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
     <%@include file="../../styletemplate/template_header.jsp" %>
 <%}else{%> 
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      
      <%@ include file = "../../main/header.jsp" %>
    </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> 
      <%@ include file = "../../main/mnmain.jsp" %>
    </td>
  </tr>
  <tr> 
    <td  bgcolor="#9BC1FF" height="10" valign="middle"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
          <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
          <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
        </tr>
      </table>
    </td>
  </tr>
  <%}%>
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> 
                        Payroll Prosess &gt; Salary Input </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; "> 


                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" cellspacing="1" cellpadding="1" class="tabbg" >
                                <tr> 
                                  <td valign="top"> 
                                    <form name="frm_input_pay" method="post" action="">
					<input type="hidden" name="command" value=""/>
                                            <input type="hidden" name="flagSaveAdjusment" value=""/>
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
											 
                                          <td height="13" width="0%">&nbsp;</td>
                                          <td height="13" width="39%" nowrap><b class="listtitle"><font size="3" color="#000000">Period &nbsp; &nbsp;
                                            
											<%
                                          Vector perValue = new Vector(1,1);
										  Vector perKey = new Vector(1,1);
										 // salkey.add(" ALL DEPARTMET");
										  //deptValue.add("0");
						                  Vector listPeriod = PstPayPeriod.list(0, 0, "", "START_DATE DESC"); 
                                                                  //Vector listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");
                                          for(int r=0;r<listPeriod.size();r++){ 
										  	PayPeriod payPeriods = (PayPeriod)listPeriod.get(r);
                                                                                        //Period period = (Period)listPeriod.get(r);
											perValue.add(""+payPeriods.getOID()); 
											perKey.add(payPeriods.getPeriod());
										  }
										  %> <%=ControlCombo.draw("periodId",null,""+periodId,perValue,perKey,"")%>
										  </font></b></td>
                                          <td height="13" width="36%">Cetak dari : <input type="text" name="n1" value="<%=n1%>"/> - <input type="text" name="n2" value="<%=n2%>"/></td>
                                          <td height="13" width="25%">&nbsp;</td>
                                          <td height="13" width="0%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td height="31" width="0%">&nbsp;</td>
                                          <td height="31" width="39%" nowrap>Salary Level 
                                              :  
                                              <input name="level" type="text" readonly="true" value="<%=levelCode%>"> <a href="javascript:openLevel();">Select</a>
                                              | <a href="javascript:clearLevelCode()">Clear</a>
                                             <%/*
                                              Vector listSalaryLevel = PstSalaryLevel.list(0, 0, "", " LEVEL_CODE, LEVEL_NAME");										  
                                              Vector salValue = new Vector(1,1);
                                              Vector salKey = new Vector(1,1);
                                                    salValue.add("0");
                                                    salKey.add("-All Level-");										  

                                              for(int d=0;d<listSalaryLevel.size();d++)
                                              {
                                                    SalaryLevel salLevel = (SalaryLevel)listSalaryLevel.get(d);
                                                    salValue.add(""+salLevel.getLevelCode());
                                                    salKey.add(""+salLevel.getLevelCode()+" "+ salLevel.getLevelName());										  
                                              }
                                              out.println(ControlCombo.draw("level",null,""+levelCode,salValue,salKey));
                                                 */    %>
                                          </td>
                                          <td>
                                              Company : 
                                              <%
                                                    Vector comp_value = new Vector(1, 1);
                                                    Vector comp_key = new Vector(1, 1);
                                                    String whereCompany="";
                                                    if (!(isHRDLogin || isEdpLogin || isGeneralManager)){
                                                        whereCompany = PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]+"='"+ emplx.getCompanyId()+"'";
                                                    } else{
                                                        comp_value.add("0");
                                                        comp_key.add("select ...");                                            
                                                    }
                                                    Vector listComp = PstCompany.list(0, 0, whereCompany, " COMPANY ");
                                                    for (int i = 0; i < listComp.size(); i++) {
                                                            Company comp = (Company) listComp.get(i);
                                                            comp_key.add(comp.getCompany());
                                                            comp_value.add(String.valueOf(comp.getOID()));
                                                    }
                                               //update by satrya 2013-08-13
                                               //jika user memilih select kembali
                                               if(companyId==0){
                                                   //srcOvertime = new SrcOvertime();
                                                   oidDivision = 0;
                                                   departmentName=0;
                                                   sectionName=0;
                                               }
                                    %> <%= ControlCombo.draw("companyId", "formElemen", null, "" + companyId, comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%> 
                                          </td>
                                          <td>
                                              Division : 
                                              <%
					Vector div_value = new Vector(1, 1);
					Vector div_key = new Vector(1, 1);
                                        String whereDivision ="";
                                        if (!(isHRDLogin || isEdpLogin || isGeneralManager)){
                                            whereDivision = PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+"='"+ emplx.getDivisionId()+"'";
                                            oidDivision = emplx.getDivisionId();
                                        } else{
                                            div_value.add("0");
                                            div_key.add("select ...");                                            
                                        }
					Vector listDiv = PstDivision.list(0, 0, whereDivision, " DIVISION ");
					for (int i = 0; i < listDiv.size(); i++) {
						Division div = (Division) listDiv.get(i);
						div_key.add(div.getDivision());
						div_value.add(String.valueOf(div.getOID()));
					}
                                   //update by satrya 2013-08-13
                                   //jika user memilih select kembali
                                   if(oidDivision==0){
                                       departmentName =0;
                                       sectionName=0;  
                                   }
			%> <%= ControlCombo.draw("oidDivision", "formElemen", null, "" + oidDivision, div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"")%>  
                                          </td>
                                          
                                          
                                          <td height="31" width="0%">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td height="31" width="0%">&nbsp;</td>
                                            <td height="31" width="39%" nowrap>
                                                Department 
                                            : 
                                            <%
					Vector dept_value = new Vector(1, 1);
					Vector dept_key = new Vector(1, 1);
					//dept_value.add("0");
					//dept_key.add("select ...");
					//String strWhere = PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision;
					Vector listDept = new Vector(); //PstDepartment.list(0, 0, strWhere, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
					/*for (int i = 0; i < listDept.size(); i++) {
						Department dept = (Department) listDept.get(i);
						dept_key.add(dept.getDepartment());
						dept_value.add(String.valueOf(dept.getOID()));
					}*/
            
            if (processDependOnUserDept) {
                if (emplx.getOID() > 0) {
                    
                    if (isHRDLogin || isEdpLogin || isGeneralManager){
                        String strWhere = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision;
                        dept_value.add("0");
                        dept_key.add("select ...");                        
                        listDept = PstDepartment.list(0, 0, strWhere, "DEPARTMENT");
                        
                    } else {                        
                        Position position = new Position();
                        try{
                            position = PstPosition.fetchExc(emplx.getPositionId());
                        }catch(Exception exc){
                            
                        }
                                
                        String whereClsDep="(((hr_department.DEPARTMENT_ID = " + departmentOid+") "
                                + "AND hr_department."+ PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision+") OR "
                                 + "(hr_department."+ PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + "=" + departmentOid+"))";
                        
                        if(position.getOID()!=0 && position.getDisabedAppDivisionScope()==0){
                            whereClsDep=" ( hr_department."+ PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision+") ";
                        }
                        
                        Vector SectionList = new Vector();
                        try {
                            String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                            Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);
                            
                            String joinDeptSection = PstSystemProperty.getValueByName("JOIN_DEPARTMENT_SECTION");
                            Vector depSecGroup = com.dimata.util.StringParser.parseGroup(joinDeptSection);

                            int grpIdx = -1;
                            int maxGrp = depGroup == null ? 0 : depGroup.size();
                            
                            int grpSecIdx = -1;
                            int maxGrpSec = depSecGroup == null ? 0 : depSecGroup.size();
                                                        
                            int countIdx = 0;
                            int MAX_LOOP = 10;
                            int curr_loop = 0;
                            
                            int countIdxSec = 0;
                            int MAX_LOOPSec = 10;
                            int curr_loopSec = 0;
                            
                            do { // find group department belonging to curretn user base in departmentOid
                                curr_loop++;
                                String[] grp = (String[]) depGroup.get(countIdx);
                                for (int g = 0; g < grp.length; g++) {
                                    String comp = grp[g];
                                    if(comp.trim().compareToIgnoreCase(""+departmentOid)==0){
                                      grpIdx = countIdx;   // A ha .. found here                                       
                                    }
                                }
                                countIdx++;
                            } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop<MAX_LOOP)); // if found then exit                            
                            
                            Vector idxSecGroup = new Vector();
                            
                            for(int x = 0; x < maxGrpSec ; x++){
                                
                                String[] grp = (String[]) depSecGroup.get(x);
                                for(int j = 0 ; j < 1 ; j++){
                                    
                                    String comp = grp[j];
                                    if(comp.trim().compareToIgnoreCase(""+departmentOid)==0){
                                        Counter counter = new Counter();
                                        counter.setCounter(x);
                                        idxSecGroup.add(counter);
                                    }                                    
                                }
                            }
                            
                            for(int s = 0 ; s < idxSecGroup.size() ; s++ ){
                                
                                Counter counter = (Counter)idxSecGroup.get(s);
                                
                                String[] grp = (String[]) depSecGroup.get(counter.getCounter());
                                
                                Section sec = new Section();
                                sec.setDepartmentId(Long.parseLong(grp[0]));
                                sec.setOID(Long.parseLong(grp[2]));
                                SectionList.add(sec);
                                
                            }
                            
                            // compose where clause
                            if(grpIdx>=0){
                                String[] grp = (String[]) depGroup.get(grpIdx);
                                for (int g = 0; g < grp.length; g++) {
                                    String comp = grp[g];
                                    whereClsDep=whereClsDep+ " OR (j.DEPARTMENT_ID = " + comp+")"; 
                                }                                     
                            }             
                            whereClsDep = " (" + whereClsDep + ") AND hr_department."+ PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision;
                        } catch (Exception exc) {
                            System.out.println(" Parsing Join Dept" + exc);
                        }

                        //dept_value.add("0");
                        //dept_key.add("select ...");
                        listDept = PstDepartment.list(0, 0,whereClsDep, "");
                        
                        for(int idx = 0 ; idx < SectionList.size() ; idx++){    
                            
                            Section sect = (Section)SectionList.get(idx);
                            
                            long sectionOid = 0;
                            
                            for(int z = 0 ; z < listDept.size() ; z++){
                            
                                Department dep = new Department();
                                
                                dep = (Department)listDept.get(z);
                                
                                if(sect.getDepartmentId() == dep.getOID()){
                                    
                                    sectionOid = sect.getOID();
                                    oidDepartment = dep.getOID();//mchen
                                    oidSection = sect.getOID();
                                }                                
                            }
                            
                            if(sectionOid != 0){
                                
                                Section lstSection = new Section();
                                Department lstDepartment = new Department();
                                
                                try{
                                    lstSection = PstSection.fetchExc(sectionOid);
                                }catch(Exception e){
                                    System.out.println("Exception "+e.toString());
                                }
                                
                                try{
                                    lstDepartment = PstDepartment.fetchExc(lstSection.getDepartmentId());
                                }catch(Exception e){
                                    System.out.println("Exception "+e.toString());
                                }
                                
                                listDept.add(lstDepartment);
                                
                            }
                        }                        
                    }
                } else {
                    dept_value.add("0");
                    dept_key.add("select ...");
                    listDept = PstDepartment.list(0, 0, (PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision ) , "DEPARTMENT");
                }
            } else {
                dept_value.add("0");
                dept_key.add("select ...");
                listDept = PstDepartment.list(0, 0, (PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision ) , "DEPARTMENT");
            }

            for (int i = 0; i < listDept.size(); i++) {
                Department dept = (Department) listDept.get(i);
                dept_key.add(dept.getDepartment());
                dept_value.add(String.valueOf(dept.getOID()));
            }


            //update by satrya 2013-08-13
            //jika user memilih select kembali
            if(departmentName==0){
                sectionName=0; 
            }
			%> <%= ControlCombo.draw("department", "formElemen", null, "" + departmentName, dept_value, dept_key, "onChange=\"javascript:cmdUpdatePos()\"")%>
                                            </td>
                                            <td height="31" width="25%">Section 
                                           <%
               
					Vector sec_value = new Vector(1, 1);
					Vector sec_key = new Vector(1, 1);
					sec_value.add("0");
					sec_key.add("select ...");
					//Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
					String strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + departmentName;
					Vector listSec = PstSection.list(0, 0, strWhereSec, " SECTION ");
					for (int i = 0; i < listSec.size(); i++) {
						Section sec = (Section) listSec.get(i);
						sec_key.add(sec.getSection());
						sec_value.add(String.valueOf(sec.getOID()));
					}
			%> <%= ControlCombo.draw("section", "formElemen", null, "" + sectionName, sec_value, sec_key, " onkeydown=\"javascript:fnTrapKD()\"")%> 
                                          </td>
                                        </tr>
                                        <tr> 
                                            <%
                                                String isCheked1=isChekedRadioButtonSearchNr==0?"checked=\"checked\"":"";
                                                String isCheked2=isChekedRadioButtonSearchNr==1?"checked=\"checked\"":"";
                                                
                                            %>
                                          <td height="31" width="0%">&nbsp;</td>
                                          <td height="31" width="39%" nowrap>Employee.Nr : 
                                              <input name="radioButtonSerachNr" type="radio" value="0" <%=isCheked1%> id='active'  onClick='disablefield(this.id);' /> Search By Range
                                              <input name="radioButtonSerachNr" type="radio" value="1" <%=isCheked2%> id='active1'  onClick='disablefield(this.id);'/> Search Selected
                                              <table width="100%"  >
                                                  <tr>
                                                      <td  width="9%">&nbsp;</td>
                                                      <td width="50%" id="search_by_range"  <%=isChekedRadioButtonSearchNr==0 ? "" : "style=\"display:none\""%>><input type="text" name="searchNrFrom" size="12" value="<%=searchNrFrom%>"></input>
                                                      to 
                                                    <input type="text" name="searchNrTo" size="12" value="<%=searchNrTo%>"></input></td>
                                                      
                                                    <td width="50%" id="search_selected" <%=isChekedRadioButtonSearchNr==1 ? "" : "style=\"display:none\""%>><input type="text" name="searchNr" size="40" value="<%=searchNr%>">
                                                    </input></td>
                                                    <td  width="9%">&nbsp;</td>
                                                  </tr>
                                              </table> 
                                              
                                           
                                          </td>
                                          <td height="31" width="36%">Name 
                                            <input type="text" name="searchName" size="20" value="<%=searchName%>">
                                          </td>
                                          <td height="31" width="25%">Status : 
                                            <%
															if(dataStatus==I_DocStatus.DOCUMENT_STATUS_DRAFT){%>
													
                                                                                                                        <input type="radio" name="dataStatus" value="<%=I_DocStatus.DOCUMENT_STATUS_DRAFT%>"checked > 
																Draft
                                                                                                                                <input type="radio" name="dataStatus" value="<%=I_DocStatus.DOCUMENT_STATUS_FINAL%>">
																Final
																<input type="radio" name="dataStatus" value="-1"  >
																All 
																<% 
                                                                                                                        }else if(dataStatus==I_DocStatus.DOCUMENT_STATUS_FINAL){%>
													
																<input type="radio" name="dataStatus" value="<%=I_DocStatus.DOCUMENT_STATUS_DRAFT%>"  >
																Draft 
																<input type="radio" name="dataStatus" value="<%=I_DocStatus.DOCUMENT_STATUS_FINAL%>" checked>
																Final
																<input type="radio" name="dataStatus" value="-1" >
																All 
															<% }else{%>
																<input type="radio" name="dataStatus" value="<%=I_DocStatus.DOCUMENT_STATUS_DRAFT%>"  >
																Draft 
																<input type="radio" name="dataStatus" value="<%=I_DocStatus.DOCUMENT_STATUS_FINAL%>"  > 
																Final
																<input type="radio" name="dataStatus" value="-1"  checked>
																All 
																<% } %>
											
										  </td>
                                          <td height="31" width="0%">&nbsp;</td>
                                        </tr>
                                        
                                           
                                        <tr> 
                                          <td height="13" width="0%">&nbsp;</td>
                                            <td width="39%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a>
                                           <img src="<%=approot%>/images/spacer.gif" width="6" height="1">
                                          <a href="javascript:cmdSearch()">Search  for Employee</a></td>                                          
                                          <td height="13" width="25%">
                                              &nbsp;Note for all : Current Separator <input type="text" value="<%=allLeftSeparator%>" name="allLeftSeparator" size="5" >
                                               > New Separator <input type="text" value="<%=allLeftSeparatorNew%>" name="allLeftSeparatorNew" size="5" >
                                              <input size ="80" multiple="" type="text" value="<%=noteToAll %>" name="noteToAll"> <a href="javascript:setNote()">Set</a>
                                              <input type="hidden" name="noteCommand" value="0">
                                          </td>
                                          <td height="13" width="36%" nowrap>&nbsp;</td>
                                          <td height="13" width="0%">&nbsp;</td>
                                        </tr>
                                         <tr> 
                                          <td height="13" width="0%">&nbsp;</td>
                                          <td height="13" colspan="4">&nbsp;</td>
                                        </tr>
                                        <%
                                            Vector sts_value= new Vector();
                                            Vector sts_key = new Vector();
                                            
                                            sts_value.add(""+I_DocStatus.DOCUMENT_STATUS_DRAFT);
                                            sts_value.add(""+I_DocStatus.DOCUMENT_STATUS_FINAL);
                                            
                                            
                                            sts_key.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT]);
                                             sts_key.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);
                                           
                                        %>
					<tr>
                                            <td colspan="4">
                                                <fieldset>
                                                    <legend>Input Adjustment</legend>
                                                <table width="100%">
                                                    <tr>
                                                         <td height="13" width="5%">&nbsp;</td>
                                                        <td height="13" width="5%">&nbsp;</td>     
                                                        <td height="13" width="11%" nowrap="nowrap">Date Adjustment: <%=ControlDate.drawDateWithStyle("date_adjusment",dtAdjusment !=null ? dtAdjusment : new Date(), 0, installInterval, "formElemen", "")%></td> 
                                                        <td height="13" width="79%">Status:<%=ControlCombo.draw("status_pay_input", "formElemen", null,""+statusPayInput , sts_value, sts_key, "")%></td>                 
                                                    </tr>
                                                </table>
                                                </fieldset>   
                                            </td>
                                            
                                         
                                                    
                                        </tr>                                                           
										
                                        
                                        <tr> 
                                          <td height="13" width="0%">&nbsp;</td>
                                          <td height="13" colspan="4">&nbsp;</td>
                                        </tr>
										<tr> 
                                          <td height="13" width="0%">&nbsp;</td>
                                          <td height="13" colspan="4"></td>
                                        </tr>
                                        <tr> 
										 <td height="13" width="0%">&nbsp;</td>
                                         
										  <%
										  //System.out.println("listPreData  "+listPreData.size());
										  if((listPayInput!=null)&&(listPayInput.size()>0)){
										  	Calendar newCalendar = Calendar.getInstance();
											newCalendar.setTime(startDate);
											int dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
										  %>
											  <td colspan="6" height="8"><%=drawList(out, privViewDetailX,iCommand,listPayInput,request /*,hashCheked*/,listPos,listReason,paramPayInput,listPayInputTbl,existPayInputId,hashPrevPrivateNote, nightAllowanceEmp)%></td>
										  <% }
										  else{%>
									  	<tr> 
											<td>&nbsp;  </td>
                                          <td height="8" width="39%" class="comment"><span class="comment"><br>
                                            &nbsp;No Employee available</span> 
                                          </td>
                                        </tr>
										  <% }%>
                                          <td valign="top" colspan="4"> 
                                            
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="0%">&nbsp;</td>
                                          <td colspan="4">&nbsp; </td>
                                        </tr>
                                        <tr> 
                                          <td class="listtitle" width="0%">&nbsp;</td>
                                          <td class="listtitle" colspan="4">&nbsp;</td>
                                        </tr>
                                       
                                      </table>
                                      <%if(errorMsg!=null && errorMsg.length()>0){%>
                                      <table width="100%">
                                          <tr>
                                              <td bgcolor="#FFFF00"><img src="<%=approot%>/images/info3.png"/>
                                                  <%=errorMsg%>
                                              </td>
                                          </tr>
                                      </table>
                                      <%}%>
									  <table width="100%" border="0">
									  <%
									  if((listPayInput!=null)&&(listPayInput.size()>0)){
									  %>
									 <tr>
											<td>
											 <img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Process Summary">
											<img src="<%=approot%>/images/spacer.gif" width="6" height="1">
											 <a href="javascript:cmdEdit()" class="command">Process Working Day Summary</a> &nbsp;&nbsp; &nbsp;&nbsp;
											
											 <img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data">
											<img src="<%=approot%>/images/spacer.gif" width="6" height="1">
											 <a href="javascript:cmdSave()" class="command">Save Adjusment OR Final Status</a> &nbsp;&nbsp; &nbsp;&nbsp;
                                                                                         
                                                                                         <img name="Image261" border="0" src="<%=approot%>/images/excel.png" width="24" height="24" alt="Save Data">
											<img src="<%=approot%>/images/spacer.gif" width="6" height="1">
                                                                                            <a href="javascript:cmdExportExcel('<%=periodId%>')" class="command">Export Excel</a> &nbsp;&nbsp; &nbsp;&nbsp;
											</td>
                                        </tr>
									 <tr>
											<td>As open pay slip show details : <input name="detail_slip" type="radio" value="1"> Yes &nbsp;&nbsp;&nbsp; <input name="detail_slip" type="radio" value="0" checked> No</td>
                                        </tr>                                                                                   
                                                                                   
										  <%
										  }//else {
										  %>
										  
										  <%
										 // }
										  %>
									  </table>

                                    </form>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td>&nbsp; </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <!-- jangan menggunakan tanda sebelah kiri tsb jika menggunakan menu freezing karena error di IE<tr> 
                            <td valign="bottom">
                            
                                <%//@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>-->
            <%}else{%>
            <!--<tr> 
                <td colspan="2" height="20" > 
      <%//@ include file = "../../main/footer.jsp" %>
               </td>
            </tr>-->
            <%}%>
</table>

        <script type="text/javascript">
	    $(document).ready(function () {
	        gridviewScroll();
	    });
            <%
                int freesize=4;
                
            %>
	    function gridviewScroll() {
	        gridView1 = $('#GridView1').gridviewScroll({
                width: 1310,
                height: 500,
                railcolor: "##33AAFF",
                barcolor: "#CDCDCD",
                barhovercolor: "#606060",
                bgcolor: "##33AAFF",
                freezesize: <%=freesize%>,
                arrowsize: 30,
                varrowtopimg: "<%=approot%>/images/arrowvt.png",
                varrowbottomimg: "<%=approot%>/images/arrowvb.png",
                harrowleftimg: "<%=approot%>/images/arrowhl.png",
                harrowrightimg: "<%=approot%>/images/arrowhr.png",
                headerrowcount: 2,
                railsize: 16,
                barsize: 15
            });
	    }
	</script>
</body>


</html>
