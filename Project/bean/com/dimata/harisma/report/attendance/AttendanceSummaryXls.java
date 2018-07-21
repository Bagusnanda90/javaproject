/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.attendance;

import com.dimata.gui.excel.ControlListExcel;
import com.dimata.harisma.entity.attendance.EmpScheduleReport;
import com.dimata.harisma.entity.attendance.I_Atendance;
import com.dimata.harisma.entity.attendance.LeaveApplicationSummary;
import com.dimata.harisma.entity.attendance.LeaveCheckTakenDateFinish;
import com.dimata.harisma.entity.attendance.PayInputPresence;
import com.dimata.harisma.entity.attendance.Presence;
import com.dimata.harisma.entity.attendance.PstAlStockReport;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.PstPresence;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstDefaultSchedule;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.leave.PstLeaveApplication;
import com.dimata.harisma.entity.leave.PstSpecialUnpaidLeaveTaken;
import com.dimata.harisma.entity.leave.SpecialUnpaidLeaveTaken;
import com.dimata.harisma.entity.leave.SummarySpecialUnpaidLeaveTaken;
import com.dimata.harisma.entity.masterdata.HolidaysTable;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstPublicHolidays;
import com.dimata.harisma.entity.masterdata.PstReason;
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.Reason;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.entity.overtime.HashTblOvertimeDetail;
import com.dimata.harisma.entity.overtime.Overtime;
import com.dimata.harisma.entity.overtime.OvertimeDetail;
import com.dimata.harisma.entity.overtime.PstOvertimeDetail;
import com.dimata.harisma.entity.overtime.TableHitungOvertimeDetail;
import com.dimata.harisma.entity.payroll.PayComponent;
import com.dimata.harisma.entity.payroll.PayGeneral;
import com.dimata.harisma.entity.payroll.PstPayGeneral;
import com.dimata.harisma.entity.payroll.PstPayInput;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.harisma.entity.search.SrcLeaveManagement;
import com.dimata.harisma.entity.search.SrcSpecialLeaveSummaryAttd;
import com.dimata.harisma.form.payroll.FrmPayInput;
import com.dimata.harisma.session.attendance.AttdSumInsentif;
import com.dimata.harisma.session.attendance.AttendanceSummary;
import com.dimata.harisma.session.attendance.PresenceReportDaily;
import com.dimata.harisma.session.attendance.ReasonCount;
import com.dimata.harisma.session.attendance.SessEmpSchedule;
import com.dimata.harisma.session.attendance.SessPresence;
import com.dimata.harisma.session.attendance.SummaryEmployeeAttendance;
import com.dimata.harisma.session.lateness.SessEmployeeLateness;
import com.dimata.harisma.session.leave.SessLeaveApp;
import com.dimata.harisma.session.payroll.I_PayrollCalculator;
import com.dimata.harisma.session.payroll.SessOvertime;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.DateCalc;
import com.dimata.util.Formater;
import com.dimata.util.LogicParser;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Satrya Ramayu
 */
public class AttendanceSummaryXls extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    /**
     * Destroys the servlet.
     */
    public void destroy() {
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }

    private static HSSFFont createFont(HSSFWorkbook wb, int size, int color, boolean isBold) {
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) size);
        font.setColor((short) color);
        if (isBold) {
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        return font;
    }

    private static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for (int i = 0; i < vector.size(); i++) {
            String code = (String) vector.get(i);
            if (((vector.get(vector.size() - 1)).equals(LogicParser.SIGN))
                    && ((vector.get(vector.size() - 1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size() - 1);
            }
        }
        return vector;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
         //update by satrya 2013-04-09
        long oidScheduleOff = 0;
        try {
            oidScheduleOff = Long.parseLong(PstSystemProperty.getValueByName("OID_DAY_OFF"));
        } catch (Exception ex) {

            System.out.println("<blink>OID_DAY_OFF NOT TO BE SET</blink>");
            oidScheduleOff = 0;
        }
        System.out.println("---===| Excel Report |===---");

        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Attendance Summary");

        //Style
        HSSFCellStyle styleTitle = wb.createCellStyle();
        styleTitle.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleTitle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleTitle.setFont(createFont(wb, 12, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleSubTitle = wb.createCellStyle();
        styleSubTitle.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleSubTitle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleSubTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleSubTitle.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleHeader = wb.createCellStyle();
        styleHeader.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleHeader.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //styleHeader.setWrapText(true);
        styleHeader.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));





        HSSFCellStyle styleValueGenap = wb.createCellStyle();
        styleValueGenap.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleValueGenap.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleValueGenap.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleValueGenap.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleValueGenap.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleValueGenap.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleValueGenap.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleValueGenap.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, false));
        styleValueGenap.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

        HSSFCellStyle styleValue = wb.createCellStyle();
        styleValue.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleValue.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleValue.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleValue.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleValue.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleValue.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleValue.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleValue.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, false));
        styleValue.setAlignment(HSSFCellStyle.ALIGN_RIGHT);


        HSSFCellStyle styleValueGenapFloat = wb.createCellStyle();
        HSSFDataFormat df = wb.createDataFormat();
        styleValueGenapFloat.setDataFormat(df.getFormat("#0.##"));
        styleValueGenapFloat.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleValueGenapFloat.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleValueGenapFloat.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleValueGenapFloat.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleValueGenapFloat.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleValueGenapFloat.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleValueGenapFloat.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleValueGenapFloat.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, false));
        styleValueGenapFloat.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

        HSSFCellStyle styleValueFloatGanjil = wb.createCellStyle();
        df = wb.createDataFormat();
        styleValueFloatGanjil.setDataFormat(df.getFormat("#0.##"));
        styleValueFloatGanjil.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleValueFloatGanjil.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleValueFloatGanjil.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleValueFloatGanjil.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleValueFloatGanjil.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleValueFloatGanjil.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleValueFloatGanjil.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleValueFloatGanjil.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, false));
        styleValueFloatGanjil.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

        

        Date selectedDateFrom = FRMQueryString.requestDateVer3(request, "check_date_start");
        Date selectedDateTo = FRMQueryString.requestDateVer3(request, "check_date_finish");
        long oidCompany = FRMQueryString.requestLong(request, "company_id");
        Hashtable hasSchedule = new Hashtable();
        
        int showOvertime = 0;
        try {
            showOvertime = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY"));
        } catch (Exception ex) {

            System.out.println("<blink>ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY NOT TO BE SET</blink>");
            showOvertime = 0;
        }
        int propReasonNo = -1;
        try {
            propReasonNo = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_REASON_DUTTY_NO"));

        } catch (Exception ex) {
            System.out.println("Execption REASON_DUTTY_NO: " + ex);
        }
        int propCheckLeaveExist = 0;
        try {
            propCheckLeaveExist = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_WHEN_LEAVE_EXIST"));

        } catch (Exception ex) {
            System.out.println("Execption ATTANDACE_WHEN_LEAVE_EXIST: " + ex);
        }
        


        //ScheduleSymbol scheduleSymbol = new ScheduleSymbol();


        //Vector listAttdAbsensi = PstEmpSchedule.getListAttendaceNoReason(attdConfig,leaveConfig,selectedDateFrom, selectedDateTo, getListEmployeeId, vctSchIDOff, hashSchOff, iPropInsentifLevel, holidaysTable, hashPositionLevel, payrollCalculatorConfig,hashPeriod,hashTblOvertimeDetail);    
        Vector listAttdAbsensi = new Vector();
        Vector listEmployee = new Vector();
        Vector vReason= new Vector();
        Hashtable listTakenLeave = new Hashtable();
        Hashtable listTotSUmLeaveSpecial= new Hashtable();
        Hashtable hashAdjusmentForPayInput = new Hashtable();
        Hashtable hashAdjusmentForPaySlip = new Hashtable();
         Hashtable sumOvertimeDailyPaidBySalary= new Hashtable();
         Hashtable sumOvertimeDailyPaidByDayOff= new Hashtable();
        Hashtable sumOvertimeDailyAllowanceFood = new Hashtable();
        Hashtable sumOvertimeDailyAllowanceMoney = new Hashtable();
        Hashtable hashTblSchedule= new Hashtable();
        Hashtable breakTimeDuration = PstScheduleSymbol.getBreakTimeDuration();
         Hashtable hasDfltScheduleTbl = PstDefaultSchedule.getHashTblDfltSch();
         Hashtable hasDfltSchedule = new Hashtable();
        Hashtable hashSymbol = new Hashtable();
     
        
        Hashtable hashReason = new Hashtable();
        Hashtable hashPersonalInOut = new Hashtable();

        
        TmpListParamAttdSummary tmpListParamAttdSummary = new TmpListParamAttdSummary(); 
        
        
      // Hashtable allParam = tmpListParamAttdSummary.getParameterAll();
       
       
     
       tmpListParamAttdSummary = (TmpListParamAttdSummary)request.getSession().getValue("LIST_ATTANDACE"); 
        if(tmpListParamAttdSummary!=null){
            //tmpListParamAttdSummary = (TmpListParamAttdSummary)request.getSession().getValue("LIST_ATTANDACE"); 
            
            hashReason = tmpListParamAttdSummary.getHashReason();
            hashPersonalInOut = tmpListParamAttdSummary.getHashPersonalInOut();
            listEmployee = tmpListParamAttdSummary.getListEmployee();
            listAttdAbsensi = tmpListParamAttdSummary.getListAttdAbsensi();
            
            vReason = tmpListParamAttdSummary.getvReason();
            listTakenLeave = tmpListParamAttdSummary.getListTakenLeave();
             listTotSUmLeaveSpecial = tmpListParamAttdSummary.getListTotSUmLeaveSpecial();
               hashAdjusmentForPayInput = tmpListParamAttdSummary.getHashAdjusmentForPayInput();
                 hashAdjusmentForPaySlip = tmpListParamAttdSummary.getHashAdjusmentForPaySlip();
                   sumOvertimeDailyPaidBySalary = tmpListParamAttdSummary.getSumOvertimeDailyPaidBySalary();
                    sumOvertimeDailyPaidByDayOff = tmpListParamAttdSummary.getSumOvertimeDailyPaidByDayOff();
                      sumOvertimeDailyAllowanceFood = tmpListParamAttdSummary.getSumOvertimeDailyAllowanceFood();
                       sumOvertimeDailyAllowanceMoney =  tmpListParamAttdSummary.getSumOvertimeDailyAllowanceMoney();
                         hashTblSchedule = tmpListParamAttdSummary.getHashTblSchedule();
        }
        
        
        
        
        
        

       

        if (listEmployee != null && listEmployee.size() > 0) {
            //if (listEmployeeLeaveALDPLL != null && listEmployeeLeaveALDPLL.size() > 0) {
            ServletOutputStream sos = response.getOutputStream();
            HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell = row.createCell((short) 0);

            String namaCompany = "";
            if (oidCompany != 0) {
                try {
                    PayGeneral payGeneral = PstPayGeneral.fetchExc(oidCompany);
                    namaCompany = payGeneral.getCompanyName();
                } catch (Exception exc) {
                }
            }
            String printHeader = namaCompany != null && namaCompany.length() > 0 ? namaCompany : " All Company "/* update by satrya 2014-01-20 PstSystemProperty.getValueByName("PRINT_HEADER")*/;


            String[] tableTitle = {
                printHeader,
                "SUMMARY ATTENDANCE",
                "DATE : " + (selectedDateFrom != null ? Formater.formatDate(selectedDateFrom, "dd-MM-yyyy") : "") + " To " + (selectedDateTo != null ? Formater.formatDate(selectedDateTo, "dd-MM-yyyy") : "")
            };

            //String[] tableSubTitle = {};



            Vector vSpesialLeaveSymbole = PstSpecialUnpaidLeaveTaken.listSpecialSymbole();
            //  Hashtable hSpecialLeaveSymbole = PstSpecialUnpaidLeaveTaken.getHashSpecialSymbole();



            /*=============== HEADER TABLE ====================*/
            int countRow = 0;

            ControlListExcel ctrControlListExcel = new ControlListExcel();


            /**
             * @DESC : TITTLE
             */
            for (int k = 0; k < tableTitle.length; k++) {
                //row = sheet.createRow((short) (countRow));

                //countRow++;
                //cell = row.createCell((short) 0);
                //cell.setCellValue(tableTitle[k]);
                //cell.setCellStyle(styleTitle);
                ctrControlListExcel.addHeader(tableTitle[k], countRow, countRow, countRow, 0, 4, styleTitle);
                countRow = countRow + 1;
                ctrControlListExcel.drawHeaderExcel(sheet);
                ctrControlListExcel.resetHeader();
            }
            ctrControlListExcel.addHeader("NO", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Payroll Number", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Full Name", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Division", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Departement", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Section", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Taken AL", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Time Taken AL", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Taken DP", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Time Taken DP", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Taken LL", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Time Taken LL", countRow, 0, 0, 0, 0, styleHeader);

            if (vSpesialLeaveSymbole != null && vSpesialLeaveSymbole.size() > 0) {
                for (int jHeader = 0; jHeader < vSpesialLeaveSymbole.size(); jHeader++) {
                    SpecialUnpaidLeaveTaken specialUnpaidLeaveTaken = (SpecialUnpaidLeaveTaken) vSpesialLeaveSymbole.get(jHeader);

                    ctrControlListExcel.addHeader(" Taken " + specialUnpaidLeaveTaken.getSymbole(), countRow, 0, 0, 0, 0, styleHeader);
                    ctrControlListExcel.addHeader(" Total Time " + specialUnpaidLeaveTaken.getSymbole(), countRow, 0, 0, 0, 0, styleHeader);
                }
            }

            ctrControlListExcel.addHeader("Total Taken Leave", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Total Time  Taken Leave", countRow, 0, 0, 0, 0, styleHeader);


            if (vReason != null && vReason.size() > 0) {
                //jika vSpesialLeave == null maka idx'nya dimualai dari 16
                //if (vSpesialLeaveSymbole == null  || vSpesialLeaveSymbole.size() < 1) {
                //totIdx = 12;
                //}
                for (int jHeader = 0; jHeader < vReason.size(); jHeader++) {

                    Reason reason = (Reason) vReason.get(jHeader);

                    ctrControlListExcel.addHeader(reason.getReason(), countRow, 0, 0, 0, 0, styleHeader);
                    ctrControlListExcel.addHeader("Time " + reason.getReason(), countRow, 0, 0, 0, 0, styleHeader);

                }
            }
            //update by satrya 2013-04-18

            if (showOvertime == 0) {
                ctrControlListExcel.addHeader("Total Insentif", countRow, 0, 0, 0, 0, styleHeader);
                ctrControlListExcel.addHeader("OT paid by Salary", countRow, 0, 0, 0, 0, styleHeader);
                ctrControlListExcel.addHeader("OT paid by Salary Idx", countRow, 0, 0, 0, 0, styleHeader);
                ctrControlListExcel.addHeader("Total Paid Real Duration by Salary", countRow, 0, 0, 0, 0, styleHeader);
                ctrControlListExcel.addHeader("OT paid by DP", countRow, 0, 0, 0, 0, styleHeader);
                ctrControlListExcel.addHeader("OT paid by DP Idx", countRow, 0, 0, 0, 0, styleHeader);
                ctrControlListExcel.addHeader("Total Paid Real Duration by DP", countRow, 0, 0, 0, 0, styleHeader);
                ctrControlListExcel.addHeader("OT Allowance Money", countRow, 0, 0, 0, 0, styleHeader);
                ctrControlListExcel.addHeader("OT Allowance Money Idx", countRow, 0, 0, 0, 0, styleHeader);
                ctrControlListExcel.addHeader("Total Allowance Real Duration by Money", countRow, 0, 0, 0, 0, styleHeader);
                ctrControlListExcel.addHeader("OT Allowance Food", countRow, 0, 0, 0, 0, styleHeader);
                ctrControlListExcel.addHeader("OT Allowance Food Idx", countRow, 0, 0, 0, 0, styleHeader);
                ctrControlListExcel.addHeader("Total Allowance Real Duration by Food", countRow, 0, 0, 0, 0, styleHeader);

            }

            ctrControlListExcel.addHeader("Total Late", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Total Time Late", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Total Absence", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Total Time Absence", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Total Early Home", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Total Time Early Home", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Total Late Early", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Total Time Late Early", countRow, 0, 0, 0, 0, styleHeader);

            ctrControlListExcel.addHeader("Total Only In", countRow, 0, 0, 0, 0, styleHeader);
            //ctrControlListExcel.addHeader("Total Time Only In", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Total Only Out", countRow, 0, 0, 0, 0, styleHeader);
            //ctrControlListExcel.addHeader("Total Time Only Out", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Total Status OK", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Total Time Status OK", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Total Work Days", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Total Time Work Days", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("Total Time Work Hours", countRow, 0, 0, 0, 0, styleHeader);

            countRow = countRow + 1;


            int no = 0;
            try {
                if (listEmployee != null && listEmployee.size() > 0) {
                      ctrControlListExcel.drawHeaderExcel(sheet);
                       ctrControlListExcel.resetHeader();
                       ctrControlListExcel.resetData();
                    for (int idxRecord = 0; idxRecord < listEmployee.size(); idxRecord++) {
                      

                        float sumLeaveTotal = 0;
                        no++;

                        SummaryEmployeeAttendance summaryEmployeeAttendance = (SummaryEmployeeAttendance) listEmployee.get(idxRecord);
                        int collPos = 0;

                        if(summaryEmployeeAttendance.getEmployeeNum().equalsIgnoreCase("14004")){
                         boolean dxd=true;
                        }
                        /**
                         * @DESC : CREATE NEW ROW
                         */
                        sheet.createFreezePane(6, 4);
                        //row = sheet.createRow((short) (countRow));
                        HSSFCellStyle styleValue1 = null;
                        if (idxRecord % 2 == 0) {
                            styleValue1 = styleValue;
                        } else {
                            styleValue1 = styleValueGenap;
                        }

                        //untuk pembulatan koma
                        HSSFCellStyle styleValuesFloat = null;
                        if (idxRecord % 2 == 0) {
                            styleValuesFloat = styleValueFloatGanjil;
                        } else {
                            styleValuesFloat = styleValueGenapFloat;

                        }
                        int numberColom=0;
                        ctrControlListExcel.addDataInteger(no, countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);
                        ctrControlListExcel.addDataString(String.valueOf(summaryEmployeeAttendance.getEmployeeNum()), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);
                        ctrControlListExcel.addDataString(String.valueOf(summaryEmployeeAttendance.getFullName()), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);
                        ctrControlListExcel.addDataString(String.valueOf(summaryEmployeeAttendance.getDivision()), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);
                        ctrControlListExcel.addDataString(String.valueOf(summaryEmployeeAttendance.getDepartment()), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);
                        ctrControlListExcel.addDataString(summaryEmployeeAttendance.getSection() != null && summaryEmployeeAttendance.getSection().length() > 0 ? summaryEmployeeAttendance.getSection() : "-", countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);

                        float totalTakenAl = 0.0F;
                        float totalTakenDP = 0.0F;
                        float totalTakenLL = 0.0F;
                        if (listTakenLeave != null && listTakenLeave.size() > 0 && listTakenLeave.get(summaryEmployeeAttendance.getEmployeeId()) != null) {

                            LeaveApplicationSummary leaveApplicationSummary = (LeaveApplicationSummary) listTakenLeave.get(summaryEmployeeAttendance.getEmployeeId());
                            if (leaveApplicationSummary.getEmployeeId() != 0 && leaveApplicationSummary.getEmployeeId() == summaryEmployeeAttendance.getEmployeeId()) {

                                totalTakenAl = 0;
                                totalTakenDP = 0;
                                totalTakenLL = 0;
                                Vector vSymbol = new Vector(leaveApplicationSummary.getVsymbol());
                                Vector vJumlahTaken = new Vector(leaveApplicationSummary.getJumlahTaken());
                                if (vSymbol != null &&vSymbol.size() > 0) {
                                    for (int idxCT = 0; idxCT < vSymbol.size(); idxCT++) {
                                        if (leaveApplicationSummary.getSymbol(idxCT).equalsIgnoreCase("AL")) {
                                            totalTakenAl = totalTakenAl + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                            vSymbol.remove(idxCT);
                                            vJumlahTaken.remove(idxCT);
                                            idxCT = idxCT - 1;
                                            //leaveApplicationSummary.getVsymbol().remove(idxCT);
                                             //leaveApplicationSummary.getJumlahTaken().remove(idxCT);
                                            //idxCT = idxCT - 1;
                                        } else if (leaveApplicationSummary.getSymbol(idxCT).equalsIgnoreCase("DP")) {
                                            totalTakenDP = totalTakenDP + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                            vSymbol.remove(idxCT);
                                            vJumlahTaken.remove(idxCT);
                                            idxCT = idxCT - 1;
                                            //leaveApplicationSummary.getVsymbol().remove(idxCT);
                                            //leaveApplicationSummary.getJumlahTaken().remove(idxCT);
                                            //idxCT = idxCT - 1;
                                        } else if (leaveApplicationSummary.getSymbol(idxCT).equalsIgnoreCase("LL")) {
                                            totalTakenLL = totalTakenLL + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                            vSymbol.remove(idxCT);
                                            vJumlahTaken.remove(idxCT);
                                            idxCT = idxCT - 1;
                                            //leaveApplicationSummary.getVsymbol().remove(idxCT);
                                            //leaveApplicationSummary.getJumlahTaken().remove(idxCT);
                                            //idxCT = idxCT - 1;
                                        }

                                    }
                                }
                                sumLeaveTotal = sumLeaveTotal + totalTakenAl + totalTakenDP + totalTakenLL;

                            }

                            // }

                        }

                        PayInputPresence payInputPresence = new PayInputPresence();
                        if(listAttdAbsensi!=null && listAttdAbsensi.size()>0){
                             for(int idx=0;idx<listAttdAbsensi.size();idx++){
                                   payInputPresence =(PayInputPresence)listAttdAbsensi.get(idx);
                                  if(summaryEmployeeAttendance.getEmployeeId()==payInputPresence.getEmployeeId()){
                                       
                                        listAttdAbsensi.remove(idx);
                                        idx = idx -1; 
                                        break; //jika sdh ketemu lalu di stop
                                  }
                                 
                             }
                         
                         }
                        /**
                         * * @DESC : Taken AL Frequency
                         */
                        ctrControlListExcel.addDataFloat(totalTakenAl, countRow,numberColom++, 0, 0, 0, 0, styleValuesFloat, null);
                        /**
                         * * @DESC : Taken AL TIME
                         */
                        ctrControlListExcel.addDataString(totalTakenAl == 0 ? "-" : String.valueOf(Formater.formatWorkDayHoursMinutes(totalTakenAl, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);

                        /**
                         * * @DESC : Taken DP Frequency
                         */
                        ctrControlListExcel.addDataFloat(totalTakenDP, countRow,numberColom++, 0, 0, 0, 0, styleValuesFloat, null);

                        /**
                         * * @DESC : Taken DP TIME
                         */
                        ctrControlListExcel.addDataString(totalTakenDP == 0 ? "-" : String.valueOf(Formater.formatWorkDayHoursMinutes(totalTakenDP, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);


                        /**
                         * * @DESC : Taken LL Frequency
                         */
                        ctrControlListExcel.addDataFloat(totalTakenLL, countRow,numberColom++, 0, 0, 0, 0, styleValuesFloat, null);

                        /**
                         * * @DESC : Taken LL TIME
                         */
                        ctrControlListExcel.addDataString(totalTakenLL == 0 ? "-" : String.valueOf(Formater.formatWorkDayHoursMinutes(totalTakenLL, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);

                        /**
                         * *@DESC: SPECIAL LEAVE
                         */
                        if (vSpesialLeaveSymbole != null && vSpesialLeaveSymbole.size() > 0) {
                            for (int idxSP = 0; idxSP < vSpesialLeaveSymbole.size(); idxSP++) {
                                SpecialUnpaidLeaveTaken specialUnpaidLeaveTaken = (SpecialUnpaidLeaveTaken) vSpesialLeaveSymbole.get(idxSP);



                                if (listTotSUmLeaveSpecial != null && listTotSUmLeaveSpecial.size() > 0 && listTotSUmLeaveSpecial.get(summaryEmployeeAttendance.getEmployeeId()) != null) {
                                    LeaveApplicationSummary leaveApplicationSummary = (LeaveApplicationSummary) listTotSUmLeaveSpecial.get(summaryEmployeeAttendance.getEmployeeId());
                                    if (leaveApplicationSummary.getEmployeeId() != 0 && leaveApplicationSummary.getEmployeeId() == summaryEmployeeAttendance.getEmployeeId()) {

                                        float totalCutiSU = 0;
                                        Vector vSymbol = new Vector(leaveApplicationSummary.getVsymbol());
                                        Vector vJumlahTaken = new Vector(leaveApplicationSummary.getJumlahTaken());
                                        if (vSymbol != null && vSymbol.size() > 0) {
                                            for (int idxCT = 0; idxCT < vSymbol.size(); idxCT++) {
                                                if (leaveApplicationSummary.getSymbol(idxCT).equalsIgnoreCase(specialUnpaidLeaveTaken.getSymbole())) {
                                                    totalCutiSU = totalCutiSU + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                                    
                                                    //leaveApplicationSummary.getVsymbol().remove(idxCT);
                                                    //idxCT = idxCT - 1;
                                                   vSymbol.remove(idxCT);
                                                   vJumlahTaken.remove(idxCT);
                                                   idxCT = idxCT - 1;
                                                }

                                            }

                                            sumLeaveTotal = sumLeaveTotal + totalCutiSU;

                                        }
                                        /**
                                         * * @DESC : frequensi SU
                                         */
                                        ctrControlListExcel.addDataFloat(totalCutiSU, countRow,numberColom++, 0, 0, 0, 0, styleValuesFloat, null);

                                        /**
                                         * * @DESC : Time SU
                                         */
                                        ctrControlListExcel.addDataString(totalCutiSU == 0 ? "-" : String.valueOf(Formater.formatWorkDayHoursMinutes(totalCutiSU, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);


                                    }

                                    // }

                                } else {
                                    /**
                                     * * @DESC : frequensi SU
                                     */
                                    ctrControlListExcel.addDataFloat(0, countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);

                                    /**
                                     * * @DESC : Time SU
                                     */
                                    ctrControlListExcel.addDataString("-", countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);
                                }

                            }//end lopp idxSp
                        }//end sp
                        /**
                         * * @DESC : Total Leave
                         */
                        ctrControlListExcel.addDataFloat(sumLeaveTotal, countRow,numberColom++, 0, 0, 0, 0, styleValuesFloat, null);

                        /**
                         * * @DESC : Total Time Leave
                         */
                        ctrControlListExcel.addDataString(sumLeaveTotal == 0 ? "-" : String.valueOf(Formater.formatWorkDayHoursMinutes(sumLeaveTotal, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);

                        /**
                         * *@DESC: REASON
                         */
                        if (vReason != null && vReason.size() > 0) {
                            for (int idxRes = 0; idxRes < vReason.size(); idxRes++) {
                                Reason reason = (Reason) vReason.get(idxRes);
                                EmpScheduleReport empScheduleReport = tmpListParamAttdSummary.getHashReasonCalulateBySistem(summaryEmployeeAttendance.getEmployeeId()+"_"+reason.getNo());
                                Vector vStrTotal = tmpListParamAttdSummary.getHashReasonHitung(summaryEmployeeAttendance.getEmployeeId()+"_"+reason.getNo());
                                String strTotal = "";//
                                int indReasonAdj = 0; 
                                if (vStrTotal != null && vStrTotal.size() == 2) {
                                             indReasonAdj = (Integer) vStrTotal.get(0);
                                             strTotal = (String) vStrTotal.get(1);
                                  }
                                 /**
                                    * * @DESC :
                                    * frequensi Reason
                                    */
                                   ctrControlListExcel.addDataFloat(empScheduleReport.getTotReason() + indReasonAdj, countRow,numberColom++, 0, 0, 0, 0, styleValue1, (empScheduleReport.getTotReason() + "+" + indReasonAdj));

                                   /**
                                    * * @DESC : Time
                                    * Reason
                                    */
                                   ctrControlListExcel.addDataString(strTotal == null || strTotal.equalsIgnoreCase("0") ? "-" : String.valueOf(strTotal), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);

                            }
                        }
                        /**
                         * OVERTIME
                         */
                        if (showOvertime == 0) {
//// tidak jadi di pakai OT DURATION
//double totDuration = 0;
//double totDurationIdx = 0;
//
//if (sumOvertimeDailyDurIdx != null && sumOvertimeDailyDurIdx.size() > 0 && sumOvertimeDailyDurIdx.get(summaryEmployeeAttendance.getEmployeeId()) != null) {
//
//    TableHitungOvertimeDetail overtimeDetailDur = (TableHitungOvertimeDetail) sumOvertimeDailyDurIdx.get(summaryEmployeeAttendance.getEmployeeId());
//
//    if (overtimeDetailDur.getEmployee_id() != 0 && overtimeDetailDur.getEmployee_id() == summaryEmployeeAttendance.getEmployeeId()) {
//
//        totDuration = 0;
//        if (overtimeDetailDur.getSizeDuration() != 0) {
//            for (int idxDur = 0; idxDur < overtimeDetailDur.getSizeDuration(); idxDur++) {
//                totDuration = totDuration + overtimeDetailDur.getvDuration(idxDur);
//            }
//            //totDuration = totDuration + totDurationAdj;
//        }
//        //totDuration = overtimeDetailDur.getTotDuration();
//        totDurationIdx = 0;
//        if (overtimeDetailDur.getSizeTotIdx() != 0) {
//            for (int idxtot = 0; idxtot < overtimeDetailDur.getSizeTotIdx(); idxtot++) {
//                totDurationIdx = totDurationIdx + overtimeDetailDur.getvTotIdx(idxtot);
//            }
//            //totDurationIdx = totDurationIdx + totDurationIdxAdj;
//        }
//
//    }
//
//    //}
//
//}
//
///**
// * * @DESC : Total Duration
// */
//ctrControlListExcel.addDataDouble(totDuration, countRow, 0, 0, 0, 0, styleValue1);
//
///**
// * * @DESC : Total Idx
// */
//ctrControlListExcel.addDataDouble(totDurationIdx, countRow, 0, 0, 0, 0, styleValue1);


                            float totInsentif = payInputPresence.getInsentif();
                            float insentifAdj = 0;
//                            if (sumInsentif != null && sumInsentif.size() > 0) {
//
//                                for (int instifIdx = 0; instifIdx < sumInsentif.size(); instifIdx++) {
//                                    AttdSumInsentif attSumInsntif = (AttdSumInsentif) sumInsentif.get(instifIdx);
//                                    if (attSumInsntif.getEmployeeId() != 0 && attSumInsntif.getEmployeeId() == summaryEmployeeAttendance.getEmployeeId()) {
//                                        insentifAdj = 0;
                                        if (hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0 && selectedDateTo != null && selectedDateFrom != null) {
                                            Date dtAdjusment = null;
                                            long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                            if (duration > 0) {
                                                int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                                for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                                    dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                                    if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_INSENTIF_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                                        double dtAdj = ((Double) hashAdjusmentForPayInput.get(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_INSENTIF_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd")));
                                                        //insentifAdj = insentifAdj + (float) dtAdj;
                                                        insentifAdj = (float) dtAdj;
                                                        break;//karena di pay input hanya 1 tgl saja yg di simpan
                                                    }
                                                }
                                            }

                                        }
//                                        totInsentif = attSumInsntif.getTotalInsentif();
//                                        //sumOvertimeDailyPaidBySalary.remove(attSumInsntif);
//                                        sumInsentif.remove(instifIdx);
//                                        instifIdx = instifIdx - 1;
//                                        break;
//
//                                    }
//
//                                }
//
//                            }

                            /**
                             * * @DESC : Total Insentif
                             */
                            //insentifAdj
                            ctrControlListExcel.addDataDouble(totInsentif + insentifAdj, countRow,numberColom++, 0, 0, 0, 0, styleValue1, (totInsentif + "+" + insentifAdj));


                            //OT PAID BY MONEY
                            int totPaidSalary = 0;
                            double totalRealDurationPaidSalary = 0;
                            //untuk paid by salary di ambil dari tb payslip berdasarkan date adjusment

                            //update by satrya 2014-02-08
                            double totIdxPaidSalary = 0;
                            double totalIdxPaidSalaryAdj = 0;
                            if (sumOvertimeDailyPaidBySalary != null && sumOvertimeDailyPaidBySalary.size() > 0 && sumOvertimeDailyPaidBySalary.get(summaryEmployeeAttendance.getEmployeeId()) != null) {

                                TableHitungOvertimeDetail overtimeDetailPaidMon = (TableHitungOvertimeDetail) sumOvertimeDailyPaidBySalary.get(summaryEmployeeAttendance.getEmployeeId());
                                if (overtimeDetailPaidMon.getEmployee_id() != 0 && overtimeDetailPaidMon.getEmployee_id() == summaryEmployeeAttendance.getEmployeeId()) {

                                    totPaidSalary = 0;
                                    if (overtimeDetailPaidMon.getSizePaidSalary() != 0) {
                                        for (int idxDur = 0; idxDur < overtimeDetailPaidMon.getSizePaidSalary(); idxDur++) {
                                            totPaidSalary = totPaidSalary + overtimeDetailPaidMon.getvPaidSalary(idxDur);
                                        }
                                    }

                                    totalRealDurationPaidSalary = 0;
                                    if (overtimeDetailPaidMon.getSizeDuration() != 0) {
                                        for (int idxtot = 0; idxtot < overtimeDetailPaidMon.getSizeDuration(); idxtot++) {
                                            totalRealDurationPaidSalary = totalRealDurationPaidSalary + overtimeDetailPaidMon.getvDuration(idxtot);
                                        }
                                    }

                                    totIdxPaidSalary = 0;
                                    if (overtimeDetailPaidMon.getSizeTotIdx() != 0) {
                                        for (int idxtot = 0; idxtot < overtimeDetailPaidMon.getSizeTotIdx(); idxtot++) {
                                            totIdxPaidSalary = totIdxPaidSalary + overtimeDetailPaidMon.getvTotIdx(idxtot);
                                        }
                                    }

                                }

                            }

                            totalIdxPaidSalaryAdj = 0;
                            if (hashAdjusmentForPaySlip != null && hashAdjusmentForPaySlip.size() > 0 && selectedDateTo != null && selectedDateFrom != null) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        if (dtAdjusment != null && hashAdjusmentForPaySlip.containsKey(PstPaySlip.fieldNames[PstPaySlip.FLD_OV_IDX_ADJUSTMENT] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double dtAdj = ((Double) hashAdjusmentForPaySlip.get(PstPaySlip.fieldNames[PstPaySlip.FLD_OV_IDX_ADJUSTMENT] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd")));
                                            totalIdxPaidSalaryAdj = dtAdj;
                                            break;////karena di pay input hanya 1 tgl saja yg di simpan
                                        }
                                    }
                                }

                            }

                            /**
                             * * @DESC : Total Paid Salary berapa kali dapet
                             * salary
                             */
                            ctrControlListExcel.addDataInteger(totPaidSalary, countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);

                            /**
                             * * @DESC : Total Paid Salary idx
                             */
                            ctrControlListExcel.addDataDouble(totIdxPaidSalary + totalIdxPaidSalaryAdj, countRow,numberColom++, 0, 0, 0, 0, styleValuesFloat, (totIdxPaidSalary + "+" + totalIdxPaidSalaryAdj));

                            /**
                             * * @DESC : Total Paid Salary idx
                             */
                            ctrControlListExcel.addDataDouble(totalRealDurationPaidSalary, countRow,numberColom++, 0, 0, 0, 0, styleValuesFloat, null);


                            /**
                             * OT Paid DP
                             */
                            int totPaidDP = 0;
                            double totalRealDurationPaidDp = 0;
                            double totalIdxPaidDp = 0;
                            double totalIdxPaidDayOffAdj = 0;
                            if (sumOvertimeDailyPaidByDayOff != null && sumOvertimeDailyPaidByDayOff.size() > 0 && sumOvertimeDailyPaidByDayOff.get(summaryEmployeeAttendance.getEmployeeId()) != null) {

                                TableHitungOvertimeDetail overtimeDetailPaidDP = (TableHitungOvertimeDetail) sumOvertimeDailyPaidByDayOff.get(summaryEmployeeAttendance.getEmployeeId());

                                if (overtimeDetailPaidDP.getEmployee_id() != 0 && overtimeDetailPaidDP.getEmployee_id() == summaryEmployeeAttendance.getEmployeeId()) {

                                    totPaidDP = 0;
                                    if (overtimeDetailPaidDP.getSizePaidDp() != 0) {
                                        for (int idxDur = 0; idxDur < overtimeDetailPaidDP.getSizePaidDp(); idxDur++) {
                                            //update by satrya 2014-02-10 totPaidDP = overtimeDetailPaidDP.getSizePaidDp();
                                            totPaidDP = totPaidDP + overtimeDetailPaidDP.getvPaidDp(idxDur);
                                        }
                                    }

                                    totalRealDurationPaidDp = 0;
                                    if (overtimeDetailPaidDP.getSizeDuration() != 0) {
                                        for (int idxtot = 0; idxtot < overtimeDetailPaidDP.getSizeDuration(); idxtot++) {
                                            totalRealDurationPaidDp = totalRealDurationPaidDp + overtimeDetailPaidDP.getvDuration(idxtot);
                                        }
                                    }

                                    totalIdxPaidDp = 0;
                                    if (overtimeDetailPaidDP.getSizeTotIdx() != 0) {
                                        for (int idxtot = 0; idxtot < overtimeDetailPaidDP.getSizeTotIdx(); idxtot++) {
                                            totalIdxPaidDp = totalIdxPaidDp + overtimeDetailPaidDP.getvTotIdx(idxtot);
                                        }
                                    }

                                }
                            }
                            totalIdxPaidDayOffAdj = 0;
                            if (hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0 && selectedDateTo != null && selectedDateFrom != null) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_DP] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double dtAdj = ((Double) hashAdjusmentForPayInput.get(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_DP] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd")));
                                            totalIdxPaidDayOffAdj = dtAdj;
                                            break;////karena di pay input hanya 1 tgl saja yg di simpan
                                        }
                                    }
                                }

                            }



                            /**
                             * * @DESC : Total Paid DP berapa kali dapet salary
                             */
                            ctrControlListExcel.addDataInteger(totPaidDP, countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);

                            /**
                             * * @DESC : Total Paid DP idx
                             */
                            ctrControlListExcel.addDataDouble(totalIdxPaidDp + totalIdxPaidDayOffAdj, countRow,numberColom++, 0, 0, 0, 0, styleValuesFloat, (totalIdxPaidDp + "+" + totalIdxPaidDayOffAdj));

                            /**
                             * * @DESC : Total Paid DP idx
                             */
                            ctrControlListExcel.addDataDouble(totalRealDurationPaidDp, countRow,numberColom++, 0, 0, 0, 0, styleValuesFloat, null);





                            //OT ALLOWANCE MONEY
                            int totAllwnceMoney = 0;
                            double totalRealDurationMoneyAllowance = 0;
                            double totalIdxMoneyAllowance = 0;
                            double totalIdxAllowanceMoneyAdj = 0;
                            if (sumOvertimeDailyAllowanceMoney != null && sumOvertimeDailyAllowanceMoney.size() > 0 && sumOvertimeDailyAllowanceMoney.get(summaryEmployeeAttendance.getEmployeeId()) != null) {

                                TableHitungOvertimeDetail overtimeDetailAllwnMon = (TableHitungOvertimeDetail) sumOvertimeDailyAllowanceMoney.get(summaryEmployeeAttendance.getEmployeeId());

                                if (overtimeDetailAllwnMon.getEmployee_id() != 0 && overtimeDetailAllwnMon.getEmployee_id() == summaryEmployeeAttendance.getEmployeeId()) {

                                    totAllwnceMoney = 0;
                                    if (overtimeDetailAllwnMon.getSizeAllowanceMoney() != 0) {
                                        for (int idxDur = 0; idxDur < overtimeDetailAllwnMon.getSizeAllowanceMoney(); idxDur++) {
                                            //update by satrya 2014-02-10 totAllwnceMoney = overtimeDetailAllwnMon.getSizeAllowanceMoney();
                                            totAllwnceMoney = totAllwnceMoney + overtimeDetailAllwnMon.getvAllowanceMoney(idxDur);
                                        }
                                    }

                                    totalRealDurationMoneyAllowance = 0;
                                    if (overtimeDetailAllwnMon.getSizeDuration() != 0) {
                                        for (int idxtot = 0; idxtot < overtimeDetailAllwnMon.getSizeDuration(); idxtot++) {
                                            totalRealDurationMoneyAllowance = totalRealDurationMoneyAllowance + overtimeDetailAllwnMon.getvDuration(idxtot);
                                        }
                                    }

                                    totalIdxMoneyAllowance = 0;
                                    if (overtimeDetailAllwnMon.getSizeTotIdx() != 0) {
                                        for (int idxtot = 0; idxtot < overtimeDetailAllwnMon.getSizeTotIdx(); idxtot++) {
                                            totalIdxMoneyAllowance = totalIdxMoneyAllowance + overtimeDetailAllwnMon.getvTotIdx(idxtot);
                                        }
                                    }
                                }
                            }
                            totalIdxAllowanceMoneyAdj = 0;
                            if (hashAdjusmentForPaySlip != null && hashAdjusmentForPaySlip.size() > 0 && selectedDateTo != null && selectedDateFrom != null) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {

                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        if (dtAdjusment != null && hashAdjusmentForPaySlip.containsKey(PstPaySlip.fieldNames[PstPaySlip.FLD_MEAL_ALLOWANCE_MONEY_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double dtAdj = ((Double) hashAdjusmentForPaySlip.get(PstPaySlip.fieldNames[PstPaySlip.FLD_MEAL_ALLOWANCE_MONEY_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd")));
                                            totalIdxAllowanceMoneyAdj = dtAdj;
                                            break;////karena di pay input hanya 1 tgl saja yg di simpan
                                        }
                                    }
                                }

                            }

                            /**
                             * * @DESC : Total Money Allowance berapa kali
                             * dapet salary
                             */
                            ctrControlListExcel.addDataInteger(totAllwnceMoney, countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);

                            /**
                             * * @DESC : Total Money Allowance idx
                             */
                            ctrControlListExcel.addDataDouble(totalIdxMoneyAllowance + totalIdxAllowanceMoneyAdj, countRow,numberColom++, 0, 0, 0, 0, styleValuesFloat, (totalIdxMoneyAllowance + "+" + totalIdxAllowanceMoneyAdj));

                            /**
                             * * @DESC : Total Real Money Allowance idx
                             */
                            ctrControlListExcel.addDataDouble(totalRealDurationMoneyAllowance, countRow,numberColom++, 0, 0, 0, 0, styleValuesFloat, null);



                            //OT ALLOWANCE FOOD

                            int totAllowanceFood = 0;
                            double totalRealDurationFoodAllowance = 0;
                            double totalIdxFoodAllowance = 0;
                            double totalIdxAllowanceFoodAdj = 0;

                            if (sumOvertimeDailyAllowanceFood != null && sumOvertimeDailyAllowanceFood.size() > 0 && sumOvertimeDailyAllowanceFood.get(summaryEmployeeAttendance.getEmployeeId()) != null) {

                                TableHitungOvertimeDetail overtimeDetailAllwnFood = (TableHitungOvertimeDetail) sumOvertimeDailyAllowanceFood.get(summaryEmployeeAttendance.getEmployeeId());

                                if (overtimeDetailAllwnFood.getEmployee_id() != 0 && overtimeDetailAllwnFood.getEmployee_id() == summaryEmployeeAttendance.getEmployeeId()) {

                                    totAllowanceFood = 0;
                                    if (overtimeDetailAllwnFood.getSizeAllowanceFood() != 0) {
                                        for (int idxDur = 0; idxDur < overtimeDetailAllwnFood.getSizeAllowanceFood(); idxDur++) {
                                            totAllowanceFood = totAllowanceFood + overtimeDetailAllwnFood.getvAllowanceFood(idxDur);
                                        }
                                    }

                                    totalRealDurationFoodAllowance = 0;
                                    if (overtimeDetailAllwnFood.getSizeDuration() != 0) {
                                        for (int idxtot = 0; idxtot < overtimeDetailAllwnFood.getSizeDuration(); idxtot++) {
                                            totalRealDurationFoodAllowance = totalRealDurationFoodAllowance + overtimeDetailAllwnFood.getvDuration(idxtot);
                                        }
                                    }

                                    totalIdxFoodAllowance = 0;
                                    if (overtimeDetailAllwnFood.getSizeTotIdx() != 0) {
                                        for (int idxtot = 0; idxtot < overtimeDetailAllwnFood.getSizeTotIdx(); idxtot++) {
                                            totalIdxFoodAllowance = totalIdxFoodAllowance + overtimeDetailAllwnFood.getvTotIdx(idxtot);
                                        }
                                    }
                                }

                            }


                            totalIdxAllowanceFoodAdj = 0;
                            if (hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0 && selectedDateTo != null && selectedDateFrom != null) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double dtAdj = ((Double) hashAdjusmentForPayInput.get(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd")));
                                            totalIdxAllowanceFoodAdj = dtAdj;
                                            break;////karena di pay input hanya 1 tgl saja yg di simpan
                                        }
                                    }
                                }

                            }

                            /**
                             * * @DESC : Total Food Allowance berapa kali dapet
                             * salary
                             */
                            ctrControlListExcel.addDataInteger(totAllowanceFood, countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);

                            /**
                             * * @DESC : Total Food Allowance idx
                             */
                            ctrControlListExcel.addDataDouble(totalIdxFoodAllowance + totalIdxAllowanceFoodAdj, countRow,numberColom++, 0, 0, 0, 0, styleValuesFloat, (totalIdxFoodAllowance + "+" + totalIdxAllowanceFoodAdj));

                            /**
                             * * @DESC : Total Real Food Allowance idx
                             */
                            ctrControlListExcel.addDataDouble(totalRealDurationFoodAllowance, countRow,numberColom++, 0, 0, 0, 0, styleValuesFloat, null);

                        }//end Overtime

                        /* DESC: LATE */
                        int totLate = payInputPresence.getLate();
                        //String totTimeLate = "";
                        int totalAdjLate = 0;
                        float totTimeLate = payInputPresence.getLateTime();
                        String strTotalLate = "";//
//                        if (listAttdStatusLate != null && listAttdStatusLate.size() > 0) {
//
//                            try {
//                                Date tmpDate = new Date(selectedDateFrom.getTime());
//                                Date tmpEndDate = new Date(selectedDateTo.getTime());
//                                Date dtSchDateTime = null;
//
//                                if (listAttdStatusLate != null && listAttdStatusLate.size() > 0) {
//                                    Vector vStrTotal = new Vector();//
//                                    for (int lidx = 0; lidx < listAttdStatusLate.size(); lidx++) {
//                                        strTotalLate = "";
//                                        totalAdjLate = 0;
//                                        EmpScheduleReport empScheduleReport = (EmpScheduleReport) listAttdStatusLate.get(lidx);
//
//                                        if (empScheduleReport.getEmployeeId() == summaryEmployeeAttendance.getEmployeeId()) {
//
//                                            //if (empScheduleReport.getTotStatus() != 0) {
//                                            vStrTotal = timeLateDuration(hasSchedule, hashTblSchedule, leaveConfig, oidScheduleOff, tmpEndDate, listPresencePersonalInOutLate, tmpDate, hashSymbol, dtSchDateTime, empScheduleReport, summaryEmployeeAttendance, hasDfltSchedule, breakTimeDuration);
//                                            //}
//
//                                            totLate = empScheduleReport.getTotStatus();
//                                            listAttdStatusLate.remove(lidx);
//                                            lidx = lidx - 1;
//                                            break;
//
//                                        }
//                                    }
//
//                                    if (vStrTotal != null && vStrTotal.size() == 1) {
//                                        totTimeLate = (Float) vStrTotal.get(0);
//                                        //strTotalLate = (String) vStrTotal.get(1);
//                                    }
//                                }
//                            } catch (Exception ex) {
//                                System.out.println("Exception" + ex);
//                            }
//                        }
                        double valueTimeLateAdj = 0;
                        if (summaryEmployeeAttendance != null) {
                            String payInputIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_IDX_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();
                            String payInputTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();

                            if (payInputIdx != null && payInputIdx.length() > 0 && payInputTime != null && payInputTime.length() > 0 && selectedDateTo != null && selectedDateFrom != null && hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        boolean adaAdj = false;
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double tmpValueIdx = (Double) hashAdjusmentForPayInput.get(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            totalAdjLate = (int) tmpValueIdx;
                                            adaAdj = true;
                                        }
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            valueTimeLateAdj = (Double) hashAdjusmentForPayInput.get(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            adaAdj = true;
                                        }
                                        if (adaAdj) {
                                            break;//karena di pay input hanya 1 tgl saja yg di simpan
                                        }

                                    }
                                }
                            }
                        }


                        /**
                         * * @DESC : Total Late Frequency
                         */
                        ctrControlListExcel.addDataInteger(totLate + totalAdjLate, countRow,numberColom++, 0, 0, 0, 0, styleValue1, (totLate + "+" + totalAdjLate));
                        /**
                         * * @DESC : Total Late TIME
                         */
                        try {
                            strTotalLate = Formater.formatWorkDayHoursMinutes((float) (valueTimeLateAdj + totTimeLate) * -1, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                        } catch (Exception exc) {
                        }
                        ctrControlListExcel.addDataString(strTotalLate == null || strTotalLate.length() == 0 || (strTotalLate != null && strTotalLate.equalsIgnoreCase("0")) ? "-" : String.valueOf(strTotalLate), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);


                        /* DESC : STATUS ABSENCE */
                        int totAbsence = payInputPresence.getAbsence();
                        int totalAdjAbs = 0;
                        String strTotalAbs = "";
                        float totalTimeAbs = payInputPresence.getAbsenceTime();
//                        if (listAttdAbsence != null && listAttdAbsence.size() > 0) {
//
//                            try {
//                                Date tmpDate = new Date(selectedDateFrom.getTime());
//                                Date tmpEndDate = new Date(selectedDateTo.getTime());
//                                Date dtSchDateTime = null;
//                                Vector vStrTotal = new Vector();//
//                                strTotalAbs = "";
//                                totalAdjAbs = 0;
//                                if (listAttdAbsence != null && listAttdAbsence.size() > 0) {
//                                    for (int lidx = 0; lidx < listAttdAbsence.size(); lidx++) {
//                                        EmpScheduleReport empScheduleReport = (EmpScheduleReport) listAttdAbsence.get(lidx);
//                                        
//                                        if (empScheduleReport.getEmployeeId() == summaryEmployeeAttendance.getEmployeeId()) {
////                                            String payInputIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX_ADJUSMENT] + "_" + empScheduleReport.getEmployeeId();
////                                            String payInputTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME_ADJUSMENT] + "_" + empScheduleReport.getEmployeeId();
//                                            //if (empScheduleReport.getTotStatus() != 0) {
//                                            vStrTotal = timeAbsenceDuration(hasSchedule, hashTblSchedule, leaveConfig, oidScheduleOff, tmpEndDate, listPresencePersonalInOutAbsence, tmpDate, hashSymbol, dtSchDateTime, empScheduleReport, summaryEmployeeAttendance, breakTimeDuration, -1, propReasonNo, propCheckLeaveExist, hasDfltSchedule);
//                                            //}
//                                            totAbsence = empScheduleReport.getTotStatus();
//
//                                            listAttdAbsence.remove(lidx);
//                                            lidx = lidx - 1;
//                                            break;
//                                        }
//                                    }
//
//                                    if (vStrTotal != null && vStrTotal.size() == 1) {
//                                        totalTimeAbs = (Float) vStrTotal.get(0);
//                                        //strTotalAbs = (String) vStrTotal.get(1);
//                                    }
//
//                                }
//                            } catch (Exception ex) {
//                                System.out.println("Exception" + ex);
//                            }
//                        }

                        double valueTimeAbsAdj = 0;
                        if (summaryEmployeeAttendance != null) {
                            String payInputIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();
                            String payInputTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();

                            if (payInputIdx != null && payInputIdx.length() > 0 && payInputTime != null && payInputTime.length() > 0 && selectedDateTo != null && selectedDateFrom != null && hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        boolean adaAdj = false;
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double tmpValueIdx = (Double) hashAdjusmentForPayInput.get(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            totalAdjAbs = (int) tmpValueIdx;
                                            adaAdj = true;
                                        }
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            valueTimeAbsAdj = (Double) hashAdjusmentForPayInput.get(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            adaAdj = true;
                                        }
                                        if (adaAdj) {
                                            break;//karena di pay input hanya 1 tgl saja yg di simpan
                                        }

                                    }
                                }
                            }
                        }
                        /**
                         * * @DESC : Total Abs Frequency
                         */
                        ctrControlListExcel.addDataInteger(totAbsence + totalAdjAbs, countRow,numberColom++, 0, 0, 0, 0, styleValue1, (totAbsence + "+" + totalAdjAbs));
                        /**
                         * * @DESC : Total Abs TIME
                         */
                        try {
                            strTotalAbs = Formater.formatWorkDayHoursMinutes((float) (valueTimeAbsAdj + totalTimeAbs), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                        } catch (Exception exc) {
                        }
                        ctrControlListExcel.addDataString(strTotalAbs == null || strTotalAbs.length() == 0 || (strTotalAbs != null && strTotalAbs.equalsIgnoreCase("0")) ? "-" : String.valueOf(strTotalAbs), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);


                        /* DESC : STATUS EARLY HOME */
                        int totalAdjEarlyHome = 0;
                        int totEalyHome = payInputPresence.getEarlyHome();
                        String strTotalEalyHome = "";
                        float totTimeEarlyHome = payInputPresence.getEarlyHomeTime();
//                        if (listAttdStatusEarlyHome != null && listAttdStatusEarlyHome.size() > 0) {
//
//                            try {
//                                Date tmpDate = new Date(selectedDateFrom.getTime());
//                                Date tmpEndDate = new Date(selectedDateTo.getTime());
//                                Date dtSchDateTime = null;
//
//                                Vector vStrTotal = new Vector();//
//                                strTotalEalyHome = "";
//                                totalAdjEarlyHome = 0;
//                                if (listAttdStatusEarlyHome != null && listAttdStatusEarlyHome.size() > 0) {
//                                    for (int lidx = 0; lidx < listAttdStatusEarlyHome.size(); lidx++) {
//                                        EmpScheduleReport empScheduleReport = (EmpScheduleReport) listAttdStatusEarlyHome.get(lidx);
//
//                                        if (empScheduleReport.getEmployeeId() == summaryEmployeeAttendance.getEmployeeId()) {
//
//                                            //if (empScheduleReport.getTotStatus() != 0) {
//                                            vStrTotal = timeLateDuration(hasSchedule, hashTblSchedule, leaveConfig, oidScheduleOff, tmpEndDate, listPresencePersonalInOutEarlyHome, tmpDate, hashSymbol, dtSchDateTime, empScheduleReport, summaryEmployeeAttendance, hasDfltSchedule, breakTimeDuration);
//                                            //}
//                                            totEalyHome = empScheduleReport.getTotStatus();
//                                            listAttdStatusEarlyHome.remove(lidx);
//                                            lidx = lidx - 1;
//                                            break;
//                                        }
//                                    }
//
//                                    if (vStrTotal != null && vStrTotal.size() == 1) {
//                                        totTimeEarlyHome = (Float) vStrTotal.get(0);
//                                        //xstrTotalEalyHome = (String) vStrTotal.get(1);
//                                    }
//                                }
//                            } catch (Exception ex) {
//                                System.out.println("Exception" + ex);
//                            }
//                        }


                        double valueTimeEarlyHome = 0;
                        if (summaryEmployeeAttendance != null) {
                            String payInputIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();
                            String payInputTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();


                            if (payInputIdx != null && payInputIdx.length() > 0 && payInputTime != null && payInputTime.length() > 0 && selectedDateTo != null && selectedDateFrom != null && hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        boolean adaAdj = false;
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double tmpValueIdx = (Double) hashAdjusmentForPayInput.get(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            totalAdjEarlyHome = (int) tmpValueIdx;
                                            adaAdj = true;
                                        }
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            valueTimeEarlyHome = (Double) hashAdjusmentForPayInput.get(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            adaAdj = true;
                                        }
                                        if (adaAdj) {
                                            break;//karena di pay input hanya 1 tgl saja yg di simpan
                                        }

                                    }
                                }
                            }
                        }
                        /**
                         * * @DESC : Total Early Frequency
                         */
                        ctrControlListExcel.addDataInteger(totEalyHome + totalAdjEarlyHome, countRow,numberColom++, 0, 0, 0, 0, styleValue1, (totEalyHome + "+" + totalAdjEarlyHome));
                        /**
                         * * @DESC : Total Early TIME
                         */
                        try {
                            strTotalEalyHome = Formater.formatWorkDayHoursMinutes((float) (valueTimeEarlyHome + totTimeEarlyHome) * -1, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                        } catch (Exception exc) {
                        }
                        ctrControlListExcel.addDataString(strTotalEalyHome == null || strTotalEalyHome.length() == 0 || (strTotalEalyHome != null && strTotalEalyHome.equalsIgnoreCase("0")) ? "-" : String.valueOf(strTotalEalyHome), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);


                        /* DESC : STATUS LATE  EARLY */
                        int totalAdjLateEarly = 0;
                        String strTotalLateEarly = "";
                        int totLateEarly = payInputPresence.getLateEarly();
                        float totTimeLateEarly = payInputPresence.getLateEarlyTime();
//                        if (listAttdStatusLateEarly != null && listAttdStatusLateEarly.size() > 0) {
//
//                            try {
//                                Date tmpDate = new Date(selectedDateFrom.getTime());
//                                Date tmpEndDate = new Date(selectedDateTo.getTime());
//                                Date dtSchDateTime = null;
//                                Vector vStrTotal = new Vector();//
//                                strTotalLateEarly = "";
//                                totalAdjLateEarly = 0;
//                                if (listAttdStatusLateEarly != null && listAttdStatusLateEarly.size() > 0) {
//                                    for (int lidx = 0; lidx < listAttdStatusLateEarly.size(); lidx++) {
//                                        EmpScheduleReport empScheduleReport = (EmpScheduleReport) listAttdStatusLateEarly.get(lidx);
//                                        if (empScheduleReport.getEmployeeId() == summaryEmployeeAttendance.getEmployeeId()) {
//
//                                            //if (empScheduleReport.getTotStatus() != 0) {
//                                            vStrTotal = timeLateDuration(hasSchedule, hashTblSchedule, leaveConfig, oidScheduleOff, tmpEndDate, listPresencePersonalInOutLateEarly, tmpDate, hashSymbol, dtSchDateTime, empScheduleReport, summaryEmployeeAttendance, hasDfltSchedule, breakTimeDuration);
//                                            //}
//                                            totLateEarly = empScheduleReport.getTotStatus();
//                                            listAttdStatusLateEarly.remove(lidx);
//                                            lidx = lidx - 1;
//                                            break;
//                                        }
//                                    }
//                                    if (vStrTotal != null && vStrTotal.size() == 1) {
//                                        totTimeLateEarly = (Float) vStrTotal.get(0);
//                                        //strTotalLateEarly = (String) vStrTotal.get(1);
//                                    }
//                                }
//
//                            } catch (Exception ex) {
//                                System.out.println("Exception" + ex);
//                            }
//                        }



                        double valueTimeLateEarlyAdj = 0;
                        if (summaryEmployeeAttendance != null) {
                            String payInputIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();
                            String payInputTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();

                            if (payInputIdx != null && payInputIdx.length() > 0 && payInputTime != null && payInputTime.length() > 0 && selectedDateTo != null && selectedDateFrom != null && hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        boolean adaAdj = false;
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double tmpValueIdx = (Double) hashAdjusmentForPayInput.get(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            totalAdjLateEarly = (int) tmpValueIdx;
                                            adaAdj = true;
                                        }
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            valueTimeLateEarlyAdj = (Double) hashAdjusmentForPayInput.get(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            adaAdj = true;
                                        }
                                        if (adaAdj) {
                                            break;//karena di pay input hanya 1 tgl saja yg di simpan
                                        }

                                    }
                                }
                            }
                        }
                        /**
                         * * @DESC : Total Late Early Frequency
                         */
                        ctrControlListExcel.addDataInteger(totLateEarly + totalAdjLateEarly, countRow,numberColom++, 0, 0, 0, 0, styleValue1, (totLateEarly + "+" + totalAdjLateEarly));
                        /**
                         * * @DESC : Total Late Early TIME
                         */
                        try {
                            strTotalLateEarly = Formater.formatWorkDayHoursMinutes((float) (valueTimeLateEarlyAdj + totTimeLateEarly) * -1, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                        } catch (Exception exc) {
                        }
                        ctrControlListExcel.addDataString(strTotalLateEarly == null || strTotalLateEarly.length() == 0 || (strTotalLateEarly != null && strTotalLateEarly.equalsIgnoreCase("0")) ? "-" : String.valueOf(strTotalLateEarly), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);


                        /* DESC : STATUS ONLY IN */
                        int totalAdjOnlyIn = 0;
                        String strTotalOnlyIn = "";
                        int totOnlyIn = payInputPresence.getTotalOnlyIn();
                        float totTimeOnlyIn = 0;
//                        if (listAttdStatusOnlyIn != null && listAttdStatusOnlyIn.size() > 0) {
//
//                            try {
//                                Date tmpDate = new Date(selectedDateFrom.getTime());
//                                Date tmpEndDate = new Date(selectedDateTo.getTime());
//                                Date dtSchDateTime = null;
//                                Vector vStrTotal = new Vector();//
//                                strTotalOnlyIn = "";
//                                totalAdjOnlyIn = 0;
//                                if (listAttdStatusOnlyIn != null && listAttdStatusOnlyIn.size() > 0) {
//                                    for (int lidx = 0; lidx < listAttdStatusOnlyIn.size(); lidx++) {
//                                        EmpScheduleReport empScheduleReport = (EmpScheduleReport) listAttdStatusOnlyIn.get(lidx);
//
//                                        if (empScheduleReport.getEmployeeId() == summaryEmployeeAttendance.getEmployeeId()) {
//
//                                            if (empScheduleReport.getTotStatus() != 0) {
//                                                vStrTotal = timeLateDuration(hasSchedule, hashTblSchedule, leaveConfig, oidScheduleOff, tmpEndDate, listPresencePersonalInOutOnlyIn, tmpDate, hashSymbol, dtSchDateTime, empScheduleReport, summaryEmployeeAttendance, hasDfltSchedule, breakTimeDuration);
//                                            }
//                                            totOnlyIn = empScheduleReport.getTotStatus();
//                                            listAttdStatusOnlyIn.remove(lidx);
//                                            lidx = lidx - 1;
//                                            break;
//                                        }
//                                    }
//                                    if (vStrTotal != null && vStrTotal.size() == 1) {
//                                        totTimeOnlyIn = (Float) vStrTotal.get(0);
//                                        //strTotalOnlyIn = (String) vStrTotal.get(1);
//                                    }
//                                }
//
//                            } catch (Exception ex) {
//                                System.out.println("Exception" + ex);
//                            }
//                        }

                        /**
                         * * @DESC : Total Only In Frequency
                         */
                        ctrControlListExcel.addDataInteger(totOnlyIn + totalAdjOnlyIn, countRow,numberColom++, 0, 0, 0, 0, styleValue1, (totOnlyIn + "+" + totalAdjOnlyIn));
                        /**
                         * * @DESC : Total Only In TIME
                         */
//                        try {
//                            strTotalOnlyIn = Formater.formatWorkDayHoursMinutes((float) (totTimeOnlyIn * -1), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
//                        } catch (Exception exc) {
//                        }
//                        ctrControlListExcel.addDataString(strTotalOnlyIn == null || strTotalOnlyIn.length() == 0 || (strTotalOnlyIn != null && strTotalOnlyIn.equalsIgnoreCase("0")) ? "-" : String.valueOf(strTotalOnlyIn), countRow, 0, 0, 0, 0, styleValue1, null);


                        /* DESC : STATUS ONLY OUT */
                        int totalAdjOnlyOut = 0;
                        String strTotalOnlyOut = "";
                        int totOnlyOut = payInputPresence.getTotalOnlyOut();
                        float totTimeOnlyOut = 0;
//                        if (listAttdStatusOnlyOut != null && listAttdStatusOnlyOut.size() > 0) {
//
//                            try {
//                                Date tmpDate = new Date(selectedDateFrom.getTime());
//                                Date tmpEndDate = new Date(selectedDateTo.getTime());
//                                Date dtSchDateTime = null;
//                                Vector vStrTotal = new Vector();//
//                                strTotalOnlyOut = "";
//                                totalAdjOnlyOut = 0;
//                                if (listAttdStatusOnlyOut != null && listAttdStatusOnlyOut.size() > 0) {
//
//                                    for (int lidx = 0; lidx < listAttdStatusOnlyOut.size(); lidx++) {
//                                        EmpScheduleReport empScheduleReport = (EmpScheduleReport) listAttdStatusOnlyOut.get(lidx);
//
//                                        if (empScheduleReport.getEmployeeId() == summaryEmployeeAttendance.getEmployeeId()) {
//                                            if (empScheduleReport.getTotStatus() != 0) {
//                                                vStrTotal = timeLateDuration(hasSchedule, hashTblSchedule, leaveConfig, oidScheduleOff, tmpEndDate, listPresencePersonalInOutOnlyIn, tmpDate, hashSymbol, dtSchDateTime, empScheduleReport, summaryEmployeeAttendance, hasDfltSchedule, breakTimeDuration);
//                                            }
//                                            totOnlyOut = empScheduleReport.getTotStatus();
//                                            listAttdStatusOnlyOut.remove(lidx);
//                                            lidx = lidx - 1;
//                                            break;
//                                        }
//                                    }
//
//                                    if (vStrTotal != null && vStrTotal.size() == 1) {
//                                        totTimeOnlyOut = (Float) vStrTotal.get(0);
//                                        //strTotalOnlyOut = (String) vStrTotal.get(1);
//                                    }
//                                }
//
//                            } catch (Exception ex) {
//                                System.out.println("Exception" + ex);
//                            }
//                        }
                        /**
                         * * @DESC : Total Only Out Frequency
                         */
                        ctrControlListExcel.addDataInteger(totOnlyOut + totalAdjOnlyOut, countRow,numberColom++, 0, 0, 0, 0, styleValue1, (totOnlyOut + "+" + totalAdjOnlyOut));
                        /**
                         * * @DESC : Total Only Out TIME
                         */
//                        try {
//                            strTotalOnlyOut = Formater.formatWorkDayHoursMinutes((float) (totTimeOnlyOut) * -1, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
//                        } catch (Exception exc) {
//                        }
//                        ctrControlListExcel.addDataString(strTotalOnlyOut == null || strTotalOnlyOut.length() == 0 || (strTotalOnlyOut != null && strTotalOnlyOut.equalsIgnoreCase("0")) ? "-" : String.valueOf(strTotalOnlyOut), countRow, 0, 0, 0, 0, styleValue1, null);


                        /* DESC : STATUS OK */
                        int totalAdjOk = 0;
                        String strTotalOk = "";
                        int totOk = payInputPresence.getPresenceOnTime();
                        float totTimeOk = payInputPresence.getPresenceOnTimeTime();
//                        if (listAttdOk != null && listAttdOk.size() > 0) {
//
//                            try {
//                                Date tmpDate = new Date(selectedDateFrom.getTime());
//                                Date tmpEndDate = new Date(selectedDateTo.getTime());
//                                //Date dtSchDateTime = null;
//                                Vector vStrTotal = new Vector();//
//                                strTotalOk = "";
//                                totalAdjOk = 0;
//                                if (listAttdOk != null && listAttdOk.size() > 0) {
//                                    for (int lidx = 0; lidx < listAttdOk.size(); lidx++) {
//                                        EmpScheduleReport empScheduleReport = (EmpScheduleReport) listAttdOk.get(lidx);
//
//                                        if (empScheduleReport.getEmployeeId() == summaryEmployeeAttendance.getEmployeeId()) {
//                                            if (empScheduleReport.getTotStatus() != 0) {
//                                                vStrTotal = timeWorkDuration(hasSchedule, hashTblSchedule, leaveConfig, oidScheduleOff, tmpEndDate, listPresencePersonalInOutOk, tmpDate, hashSymbol, empScheduleReport, summaryEmployeeAttendance, breakTimeDuration, propCheckLeaveExist, hasDfltSchedule, vOvertimeDetailStatusOk);
//                                            }
//                                            totOk = empScheduleReport.getTotStatus();
//                                            listAttdOk.remove(lidx);
//                                            lidx = lidx - 1;
//                                            break;
//                                        }
//                                    }
//                                    if (vStrTotal != null && vStrTotal.size() == 1) {
//                                        totTimeOk = (Float) vStrTotal.get(0);
//                                        //strTotalOk = (String) vStrTotal.get(1);
//                                    }
//                                }
//
//                            } catch (Exception ex) {
//                                System.out.println("Exception" + ex);
//                            }
//                        }

                        double valueTimeOkAdj = 0;
                        if (summaryEmployeeAttendance != null) {
                            String payInputIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_IDX_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();
                            String payInputTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();

                            if (payInputIdx != null && payInputIdx.length() > 0 && payInputTime != null && payInputTime.length() > 0 && selectedDateTo != null && selectedDateFrom != null && hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        boolean adaAdj = false;
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double tmpValueIdx = (Double) hashAdjusmentForPayInput.get(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            totalAdjOk = (int) tmpValueIdx;
                                            adaAdj = true;
                                        }
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            valueTimeOkAdj = (Double) hashAdjusmentForPayInput.get(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            adaAdj = true;
                                        }
                                        if (adaAdj) {
                                            break;//karena di pay input hanya 1 tgl saja yg di simpan
                                        }

                                    }
                                }
                            }
                        }
                        /**
                         * * @DESC : Total Ok Frequency
                         */
                        ctrControlListExcel.addDataInteger(totOk + totalAdjOk, countRow,numberColom++, 0, 0, 0, 0, styleValue1, (totOk + "+" + totalAdjOk));
                        /**
                         * * @DESC : Total Ok TIME
                         */
                        try {
                            strTotalOk = Formater.formatWorkDayHoursMinutes((float) (valueTimeOkAdj + totTimeOk), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                        } catch (Exception exc) {
                        }
                        ctrControlListExcel.addDataString(strTotalOk == null || strTotalOk.length() == 0 || (strTotalOk != null && strTotalOk.equalsIgnoreCase("0")) ? "-" : String.valueOf(strTotalOk), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);


                        /* DESC: WORKING DAYS */
                        int totWorkDays = payInputPresence.getTotalWorkingDays();
                        String totTimeWorkDays = "";
                        long fTotTimeWorkDays = payInputPresence.getTimeWorkHour();
//                        if (listAttWorkDays != null && listAttWorkDays.size() > 0) {
//
//                            try {
//                                Date tmpDate = new Date(selectedDateFrom.getTime());
//                                Date tmpEndDate = new Date(selectedDateTo.getTime());
//                                Vector vStrTotal = new Vector();//
//                                //strWorkingDays = "";
//                                //totalAdjWorkingDays = 0;
//                                if (listAttWorkDays != null && listAttWorkDays.size() > 0) {
//
//                                    for (int lidx = 0; lidx < listAttWorkDays.size(); lidx++) {
//                                        EmpScheduleReport empScheduleReport = (EmpScheduleReport) listAttWorkDays.get(lidx);
//
//                                        if (empScheduleReport.getEmployeeId() == summaryEmployeeAttendance.getEmployeeId()) {
//                                            //if(empScheduleReport.getTotStatus()!=0){
//                                            vStrTotal = timeWorkDuration(hasSchedule, hashTblSchedule, leaveConfig, oidScheduleOff, tmpEndDate, listPresencePersonalInOutWorkDays, tmpDate, hashSymbol, empScheduleReport, summaryEmployeeAttendance, breakTimeDuration, propCheckLeaveExist, hasDfltSchedule, vOvertimeDetailWorkDays);
//                                            //}
//                                            totWorkDays = empScheduleReport.getTotStatus();
//                                            // totTimeWorkDays = strTotal;
//                                            listAttWorkDays.remove(lidx);
//                                            lidx = lidx - 1;
//                                            break;
//                                        }
//                                    }
//                                    if (vStrTotal != null && vStrTotal.size() == 1) {
//                                        fTotTimeWorkDays = (Float) vStrTotal.get(0);
//                                        //strTotalOk = (String) vStrTotal.get(1);
//                                    }
//                                }
//
//                            } catch (Exception ex) {
//                                System.out.println("Exception" + ex);
//                            }
//                        }

                        /**
                         * * @DESC : Total Working days Frequency
                         */
                        ctrControlListExcel.addDataInteger(totWorkDays, countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);
                        /**
                         * * @DESC : Total Working days TIME
                         */
                        try {
                            
                            totTimeWorkDays = Formater.formatWorkDayHoursMinutes((float) fTotTimeWorkDays / (leaveConfig.getHourOneWorkday() * 60f * 60f * 1000f),leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                        } catch (Exception exc) {
                        }
                        ctrControlListExcel.addDataString(totTimeWorkDays == null || totTimeWorkDays.length() == 0 || (totTimeWorkDays != null && totTimeWorkDays.equalsIgnoreCase("0")) ? "-" : String.valueOf(totTimeWorkDays), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);


                        /* DESC: WORKING DAYS Hours */
                        //int totWorkHours = 0;
                        String totTimeWorkHours = Formater.formatWorkHoursMinutes(payInputPresence.getTimeWorkHour());

//                        if (listAttWorkHours != null && listAttWorkHours.size() > 0) {
//
//                            try {
//                                Date tmpDate = new Date(selectedDateFrom.getTime());
//                                Date tmpEndDate = new Date(selectedDateTo.getTime());
//
//                                for (int lidx = 0; lidx < listAttWorkHours.size(); lidx++) {
//                                    EmpScheduleReport empScheduleReport = (EmpScheduleReport) listAttWorkHours.get(lidx);
//
//                                    //String strTotal = "";//
//                                    if (empScheduleReport.getEmployeeId() == summaryEmployeeAttendance.getEmployeeId()) {
//
//                                        totTimeWorkHours = timeWorkDurationHours(hasSchedule, hashTblSchedule, leaveConfig, oidScheduleOff, tmpEndDate, listPresencePersonalInOutWorkDays, tmpDate, hashSymbol, empScheduleReport, summaryEmployeeAttendance, breakTimeDuration, propCheckLeaveExist, hasDfltSchedule, vOvertimeDetailWorkHours);
//
//                                        totWorkHours = empScheduleReport.getTotStatus();
//                                        // totTimeWorkHours = totWorkHours;
//                                        listAttWorkHours.remove(lidx);
//                                        lidx = lidx - 1;
//                                        break;
//                                    }
//                                }
//                            } catch (Exception ex) {
//                                System.out.println("Exception" + ex);
//                            }
//                        }


                        /**
                         * * @DESC : Total Working days hours TIME
                         */
                        ctrControlListExcel.addDataString(totTimeWorkHours == null || totTimeWorkHours.length() == 0 || (totTimeWorkHours != null && totTimeWorkHours.equalsIgnoreCase("0")) ? "-" : String.valueOf(totTimeWorkHours), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);

                       
                        countRow++;
                         ctrControlListExcel.drawDataExcel(sheet);
                         
                         //wb.write(sos);
                    }//end LOOP
                    
                }

            } catch (Exception ex) {
                System.out.println("Exception export summary Attd" + ex);
            }
            
            wb.write(sos);
            sos.close();
        }
    }

    /**
     *
     * @param hasSchedule
     * @param leaveConfig
     * @param oidScheduleOff
     * @param tmpEndDate
     * @param listPresencePersonalInOut
     * @param tmpDate
     * @param hashSymbol
     * @param dtSchDateTime
     * @param empScheduleReport
     * @param summaryEmployeeAttendance
     * @return Vector
     */
    public Vector timeLateDuration(Hashtable hasSchedule, Hashtable hashTblSchedule, I_Leave leaveConfig, long oidScheduleOff, Date tmpEndDate, Vector listPresencePersonalInOut, Date tmpDate, Hashtable hashSymbol, Date dtSchDateTime, EmpScheduleReport empScheduleReport, SummaryEmployeeAttendance summaryEmployeeAttendance, Hashtable hasDfltSchedule, Hashtable breakTimeDuration) {


        // String workDuration;
        long totalLate = 0;

        try {
            ScheduleSymbol scheduleSymbol = new ScheduleSymbol();

            int kk = 0;
            while (tmpDate.before(tmpEndDate) || tmpDate.equals(tmpEndDate)) {


                Long oidSch1St = empScheduleReport.getEmpSchedules1st(kk);
                Date actualIn = empScheduleReport.getEmpIn1st(kk);
                if (actualIn != null) {
                    actualIn.setSeconds(0);
                }
                Date actualOut = empScheduleReport.getEmpOut1st(kk);
                if (actualOut != null) {
                    actualOut.setSeconds(0);
                }
                Long empId = empScheduleReport.getEmployeeId();
                Date dtIdx = empScheduleReport.getDtIDate(kk);//mengetahui idx date


                //Vector vectFirstSchedule = PstScheduleSymbol.getScheduleData(oidSch1St, tmpDate);
                if (scheduleSymbol != null && scheduleSymbol.getOID() != oidSch1St) {
                    scheduleSymbol = (ScheduleSymbol) hasSchedule.get("" + oidSch1St);
                    if (scheduleSymbol == null) {

                        if (oidSch1St != 0) {
                            try {
                                scheduleSymbol = (ScheduleSymbol) hashTblSchedule.get(oidSch1St);//PstScheduleSymbol.fetchExc(oidSch1St);
                            } catch (Exception exc) {
                            }
                            hasSchedule.put("" + oidSch1St, scheduleSymbol);
                        }
                    }
                } else {
                    if (scheduleSymbol == null) {

                        if (oidSch1St != 0) {
                            try {
                                scheduleSymbol = (ScheduleSymbol) hashTblSchedule.get(oidSch1St);//scheduleSymbol  = PstScheduleSymbol.fetchExc(oidSch1St);
                            } catch (Exception exc) {
                            }
                            hasSchedule.put("" + oidSch1St, scheduleSymbol);
                        }
                    }
                }
                //Presence presenceBreak = new Presence(); 
                //update by satrya 2014-01-23
                long totalCutiLate = 0;
                long totalLateScheduleBreak = 0;
                long preTimeInLate = 0;
                long preTimeOutLate = 0;
                if (scheduleSymbol != null && scheduleSymbol.getBreakOut() != null && scheduleSymbol.getBreakIn() != null && dtIdx != null && listPresencePersonalInOut != null && listPresencePersonalInOut.size() > 0) {
                    Date dtpresenceDateTime = null;
                    Date dtSchEmpScheduleBIn = (Date) dtIdx.clone();
                    Date dtSchEmpScheduleBOut = (Date) dtIdx.clone();
                    Presence presenceBreak = new Presence();
                    //Hashtable hashCekCutiDtPresenceSama = new Hashtable();
                    for (int bIdx = 0; bIdx < listPresencePersonalInOut.size(); bIdx++) {

                        Date dtPresenceBreakIn = null;
                        Date dtPresenceBreakOut = null;
                        long cutiTimeStartLate = 0;
                        long cutiTimeEndLate = 0;

                        long preBreakOutLate = 0;
                        long preBreakInLate = 0;
                        presenceBreak = (Presence) listPresencePersonalInOut.get(bIdx);//yang di cari harus ada leavenya 
                        //update by satrya 2012-10-17
                        if (dtSchEmpScheduleBOut != null) {
                            dtSchEmpScheduleBOut.setHours(scheduleSymbol.getBreakOut().getHours());
                            dtSchEmpScheduleBOut.setMinutes(scheduleSymbol.getBreakOut().getMinutes());
                            dtSchEmpScheduleBOut.setSeconds(0);
                        }
                        if (dtSchEmpScheduleBIn != null) {
                            dtSchEmpScheduleBIn.setHours(scheduleSymbol.getBreakIn().getHours());
                            dtSchEmpScheduleBIn.setMinutes(scheduleSymbol.getBreakIn().getMinutes());
                            dtSchEmpScheduleBIn.setSeconds(0);
                        }

                        if (presenceBreak.getPresenceDatetime() != null) {
                            //update by satrya 2012-10-17
                            dtpresenceDateTime = (Date) presenceBreak.getPresenceDatetime().clone();
                            dtpresenceDateTime.setHours(dtpresenceDateTime.getHours());
                            dtpresenceDateTime.setMinutes(dtpresenceDateTime.getMinutes());
                            dtpresenceDateTime.setSeconds(0);
                        }
                        /*if (presenceBreak.getEmployeeId() == empId && presenceBreak.getPresenceDatetime()!=null
                         && (DateCalc.dayDifference(presenceBreak.getPresenceDatetime(), dtIdx) == 0)
                         && presenceBreak.getScheduleDatetime() == null) {
                         if (presenceBreak.getStatus() == Presence.STATUS_OUT_ON_DUTY) {
                         //bOut =bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+"<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                         // dBout = bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");
                         listPresencePersonalInOut.remove(bIdx);
                         bIdx = bIdx - 1;

                         } else if (presenceBreak.getStatus() == Presence.STATUS_CALL_BACK) {
                         //bIn =bIn+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+ "<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                         // dBin = dBin+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm"); 
                         listPresencePersonalInOut.remove(bIdx);
                         bIdx = bIdx - 1;
                         }
                         }*/
                        if (presenceBreak.getPresenceDatetime() != null /* pdate by satrya 2014-01-27 presenceBreak.getScheduleDatetime() != null*/
                                && presenceBreak.getEmployeeId() == empId
                                && (DateCalc.dayDifference(presenceBreak.getPresenceDatetime(), dtIdx) == 0)) {
                            // update by satrya 2014-01-27 && (DateCalc.dayDifference(presenceBreak.getScheduleDatetime(), dtIdx) == 0)) {
                            //kenapa di buat presenceBreak.getScheduleDatetime()!=null ini berpengaruh pada DateCalc.dayDifference(presenceBreak.getScheduleDatetime() xxxx yg menyebabkan exception
                            if (presenceBreak.getStatus() == Presence.STATUS_OUT_PERSONAL) {
                                //preBreakOutX  = dtpresenceDateTime==null?0:dtpresenceDateTime.getTime();///yang di pakai mengurangi itu adalah presence PO  
                                dtPresenceBreakOut = dtpresenceDateTime;
                                //dtScheduleBO = presenceBreak.getScheduleDatetime();
                                //ispreBreakOutsdhdiambil = false;
                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;
                            } else if (presenceBreak.getStatus() == Presence.STATUS_IN_PERSONAL) {
                                dtPresenceBreakIn = presenceBreak.getPresenceDatetime();

                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;
                                //dtScheduleBI = presenceBreak.getScheduleDatetime();
                            } else if (presenceBreak.getStatus() == Presence.STATUS_OUT_ON_DUTY) {
                                dtPresenceBreakOut = null;//update by satrya 2014-01-27 presenceBreak.getPresenceDatetime();
                                //ispreBreakOutsdhdiambil = false;
                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;
                            } else if (presenceBreak.getStatus() == Presence.STATUS_CALL_BACK) {
                                dtPresenceBreakIn = null;//update by satrya 2014-01-27 presenceBreak.getPresenceDatetime();
                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;
                                //ispreBreakOutsdhdiambil = true;

                            }

                            //cek ada cuti
                            //maka yg di pakai cek keterlambatan yaitu cutinya
                            Vector vLisOverlapCuti = SessLeaveApp.checkOverLapsLeaveTakenNoTime(empId, dtIdx, dtIdx);
                            if (vLisOverlapCuti != null && vLisOverlapCuti.size() > 0) {
                                for (int idxCuti = 0; idxCuti < vLisOverlapCuti.size(); idxCuti++) {
                                    LeaveCheckTakenDateFinish leaveCheckTaken = (LeaveCheckTakenDateFinish) vLisOverlapCuti.get(idxCuti);

                                    if (leaveCheckTaken.getTakenQty() < leaveConfig.getHourOneWorkday() / leaveConfig.getHourOneWorkday()) {
                                        //artinya cuti harus kurang dari satu hari
                                        if (leaveCheckTaken.getTakenDate() != null && dtPresenceBreakOut != null && leaveCheckTaken.getTakenDate() != null
                                                && dtPresenceBreakOut.getTime() < leaveCheckTaken.getTakenDate().getTime()
                                                && (DateCalc.dayDifference(dtPresenceBreakOut, dtIdx) == 0)) {
                                            cutiTimeStartLate = leaveCheckTaken.getTakenDate().getTime() - dtPresenceBreakOut.getTime();
                                            //hashCekCutiDtPresenceSama.put(dtPresenceBreakOut, true);
                                        } else {
                                            cutiTimeStartLate = 0;
                                        }

                                        if (dtSchEmpScheduleBIn != null && dtPresenceBreakIn != null && leaveCheckTaken.getFinishDate() != null
                                                && dtPresenceBreakIn.getTime() > leaveCheckTaken.getFinishDate().getTime()
                                                && (DateCalc.dayDifference(dtPresenceBreakIn, dtIdx) == 0)) {
                                            cutiTimeEndLate = dtPresenceBreakIn.getTime() - leaveCheckTaken.getFinishDate().getTime();
                                            //hashCekCutiDtPresenceSama.put(dtPresenceBreakIn, true);
                                        } else {
                                            cutiTimeEndLate = 0;
                                        }
                                    }
                                    totalCutiLate = totalCutiLate + (cutiTimeStartLate + cutiTimeEndLate);
                                    vLisOverlapCuti.remove(idxCuti);
                                    idxCuti = idxCuti - 1;
                                }

                                //simapan tanggalnya sementara
                            } //cek di schedule presence
                            else {
                                if (presenceBreak.getScheduleDatetime() != null && presenceBreak.getPresenceDatetime() != null
                                        && presenceBreak.getEmployeeId() == empId
                                        && (DateCalc.dayDifference(presenceBreak.getPresenceDatetime(), dtIdx) == 0)) {
                                    //update by satrya 2014-01-27 && (DateCalc.dayDifference(presenceBreak.getScheduleDatetime(), dtIdx) == 0)) {x
                                    if (presenceBreak.getStatus() == Presence.STATUS_OUT_PERSONAL) {
                                        dtPresenceBreakOut = dtpresenceDateTime;
                                        if (dtSchEmpScheduleBOut != null && presenceBreak.getScheduleDatetime() != null && dtSchEmpScheduleBIn != null && dtPresenceBreakIn != null && presenceBreak.getPresenceDatetime().getTime() > dtSchEmpScheduleBIn.getTime()) {
                                            //preBreakOut = presenceBreak.getPresenceDatetime().getTime();
                                            //melewati jam brekIn padahal statusnya presencenya personal out
                                            preBreakOutLate = dtSchEmpScheduleBOut.getTime() + dtPresenceBreakIn.getTime() + (dtPresenceBreakIn.getTime() - presenceBreak.getPresenceDatetime().getTime());
                                        } else if (presenceBreak.getPresenceDatetime() != null && presenceBreak.getScheduleDatetime() != null && (presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())) { ///jika karyawan mendahului istirahat
                                            preBreakOutLate = presenceBreak.getScheduleDatetime().getTime() - presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PO 
                                        } else {
                                            preBreakOutLate = 0;
                                        }
                                        //simapan tanggalnya semetara
                                    } else if (presenceBreak.getStatus() == Presence.STATUS_IN_PERSONAL) {

                                        if (dtSchEmpScheduleBIn != null && dtPresenceBreakOut != null && dtPresenceBreakIn != null
                                                && dtPresenceBreakOut.getTime() > dtSchEmpScheduleBIn.getTime() && dtPresenceBreakIn.getTime() > dtSchEmpScheduleBIn.getTime()) {
                                            //karena sudah pasti melewatijam istirahatnya
                                            preBreakInLate = presenceBreak.getPresenceDatetime().getTime() - dtSchEmpScheduleBIn.getTime();
                                            //preBreakIn = presenceBreak.getPresenceDatetime().getTime() + tmpBreakDuration;
                                        } else if (dtSchEmpScheduleBOut != null && dtPresenceBreakIn != null && dtPresenceBreakIn.getTime() < dtSchEmpScheduleBOut.getTime()) {
                                            preBreakInLate = dtSchEmpScheduleBOut.getTime() - dtPresenceBreakIn.getTime();
                                        } else if (presenceBreak.getPresenceDatetime() != null && presenceBreak.getScheduleDatetime() != null && presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()) { ///jika karyawan melewati jam istirahat
                                            preBreakInLate = presenceBreak.getPresenceDatetime().getTime() - dtSchEmpScheduleBIn.getTime();
                                        } else {
                                            preBreakInLate = 0;
                                        }
                                    }

                                    totalLateScheduleBreak = totalLateScheduleBreak + (preBreakOutLate + preBreakInLate);
                                }//end else if
                            }
                        }//end for break In                          
                    }
                }

                if (scheduleSymbol != null) {
                    if (scheduleSymbol.getTimeIn() != null && actualIn != null && dtIdx != null) {
                        Date schIn = (Date) dtIdx.clone();
                        schIn.setHours(scheduleSymbol.getTimeIn().getHours());
                        schIn.setMinutes(scheduleSymbol.getTimeIn().getMinutes());

                        if (schIn != null && actualIn.getTime() > schIn.getTime()) {
                            preTimeInLate = (actualIn.getTime() - schIn.getTime());
                        } else {
                            preTimeInLate = 0;
                        }
                    }

                    if (scheduleSymbol.getTimeOut() != null && actualOut != null && dtIdx != null) {
                        Date schOut = (Date) dtIdx.clone();
                        schOut.setHours(scheduleSymbol.getTimeOut().getHours());
                        schOut.setMinutes(scheduleSymbol.getTimeOut().getMinutes());

                        if (actualOut.getTime() < schOut.getTime()) {
                            preTimeOutLate = schOut.getTime() - actualOut.getTime();
                        } else {
                            preTimeOutLate = 0;
                        }
                    }
                }

                totalLate = totalLate + ((preTimeInLate + preTimeOutLate)) + totalLateScheduleBreak + totalCutiLate;
                tmpDate = new Date(tmpDate.getTime() + 24 * 60 * 60 * 1000);
                kk = kk + 1;
            }
        } catch (Exception exc) {
            System.out.println("Exception exc totalLate" + exc);
        }
        //String workDuration = Formater.formatWorkDayHoursMinutes((float) ((((totalLate)) / (leaveConfig.getHourOneWorkday() * 60f * 60f * 1000f)) * -1), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
        Vector hasil = new Vector();
        hasil.add((float) ((((totalLate)) / (leaveConfig.getHourOneWorkday() * 60f * 60f * 1000f))));
        //hasil.add(workDuration);
        return hasil;
    }

    /**
     * Keterangan mnecari absen time
     *
     * @param hasSchedule
     * @param leaveConfig
     * @param oidScheduleOff
     * @param tmpEndDate
     * @param listPresencePersonalInOut
     * @param tmpDate
     * @param hashSymbol
     * @param dtSchDateTime
     * @param empScheduleReport
     * @param summaryEmployeeAttendance
     * @param breakTimeDuration
     * @param reasonNo
     * @param propReasonNo
     * @param propCheckLeaveExist
     * @return
     */
    public Vector timeAbsenceDuration(Hashtable hasSchedule, Hashtable hashTblSchedule, I_Leave leaveConfig, long oidScheduleOff, Date tmpEndDate, Vector listPresencePersonalInOut, Date tmpDate, Hashtable hashSymbol, Date dtSchDateTime, EmpScheduleReport empScheduleReport, SummaryEmployeeAttendance summaryEmployeeAttendance, Hashtable breakTimeDuration, int reasonNo, int propReasonNo, int propCheckLeaveExist, Hashtable hasDfltSchedule) {

        // String workDuration;

        long totalAbsence = 0;
        //String payInputIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_ADJUSMENT] + payInputCode;
        //String payInputTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_ADJUSMENT] + payInputCode;

        try {
            ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
            // Hashtable hashDtCuti=new Hashtable();
            int kk = 0;
            Hashtable hashTglYgSama = new Hashtable();
            Hashtable hashSisaCutiKemarin = new Hashtable();
            while (tmpDate.before(tmpEndDate) || tmpDate.equals(tmpEndDate)) {

                //int kk = tmpDate.getDate()-1;
                long breakPresence = 0;
                long cuti = 0;
                long oidSch2st = 0;
                Long oidSch1St = empScheduleReport.getEmpSchedules1st(kk);
                Date actualIn = empScheduleReport.getEmpIn1st(kk);
                // if(actualIn!=null){
                // actualIn.setSeconds(0);
                //}
                Date actualOut = empScheduleReport.getEmpOut1st(kk);
                // if(actualOut!=null){
                // actualOut.setSeconds(0);
                // }
                Long empId = empScheduleReport.getEmployeeId();
                Date dtIdx = empScheduleReport.getDtIDate(kk);//mengetahui idx date


                // long totalAbsenceIn=0;
                //long totalAbsenceOut=0;
                if (scheduleSymbol != null && scheduleSymbol.getOID() != oidSch1St) {
                    scheduleSymbol = (ScheduleSymbol) hasSchedule.get("" + oidSch1St);
                    if (scheduleSymbol == null) {

                        if (oidSch1St != 0) {
                            try {
                                scheduleSymbol = (ScheduleSymbol) hashTblSchedule.get(oidSch1St);//PstScheduleSymbol.fetchExc(oidSch1St);
                            } catch (Exception exc) {
                            }
                            hasSchedule.put("" + oidSch1St, scheduleSymbol);
                        }
                    }
                } else {
                    if (scheduleSymbol == null) {

                        if (oidSch1St != 0) {
                            try {
                                scheduleSymbol = (ScheduleSymbol) hashTblSchedule.get(oidSch1St);//scheduleSymbol  = PstScheduleSymbol.fetchExc(oidSch1St);
                            } catch (Exception exc) {
                            }
                            hasSchedule.put("" + oidSch1St, scheduleSymbol);
                        }
                    }
                }

                //update by satrya 2014-01-23

                long preBreakOut = 0;
                long preBreakIn = 0;
                long breakDuration = 0L;
                long preLateTimeIn = 0;
                long preLateTimeOut = 0;
                long totalCuti = 0;
                boolean cekAdaCuti = false;
                long tidakAdaInOut = 0;

                Vector vLisOverlapCuti = SessLeaveApp.checkOverLapsLeaveTakenNoTime(empId, dtIdx, dtIdx);
                Vector vListOverlapCutiSame = (Vector) vLisOverlapCuti.clone();
                if (vLisOverlapCuti != null && vLisOverlapCuti.size() > 0) {
                    cekAdaCuti = true;
                    for (int idxCuti = 0; idxCuti < vLisOverlapCuti.size(); idxCuti++) {
                        LeaveCheckTakenDateFinish leaveCheckTaken = (LeaveCheckTakenDateFinish) vLisOverlapCuti.get(idxCuti);
                        Date planCutiStart = leaveCheckTaken.getTakenDate();
                        if (leaveCheckTaken.getTakenQty() <= leaveConfig.getHourOneWorkday() / leaveConfig.getHourOneWorkday()) {
                            totalCuti = totalCuti + (long) (leaveCheckTaken.getTakenQty() * leaveConfig.getHourOneWorkday() * 60 * 60 * 1000);
                        } else {

                            if (hashTglYgSama != null && (hashTglYgSama.size() == 0 || !hashTglYgSama.containsKey(planCutiStart))) {

                                totalCuti = totalCuti + (long) (leaveConfig.getHourOneWorkday() * 60 * 60 * 1000);
                                float jumlahSisa = leaveCheckTaken.getTakenQty() - (leaveConfig.getHourOneWorkday() / leaveConfig.getHourOneWorkday());
                                hashSisaCutiKemarin.put(planCutiStart, jumlahSisa);
                                hashTglYgSama.put(planCutiStart, true);
                            } else if (hashSisaCutiKemarin != null && hashTglYgSama != null && hashTglYgSama.size() > 0
                                    && hashSisaCutiKemarin.size() > 0 && hashTglYgSama.containsKey(planCutiStart)) {
                                float sisaCutiDiTglSama = (Float) hashSisaCutiKemarin.get(planCutiStart);
                                if (sisaCutiDiTglSama <= leaveConfig.getHourOneWorkday() / leaveConfig.getHourOneWorkday()) {
                                    totalCuti = totalCuti + (long) (sisaCutiDiTglSama * leaveConfig.getHourOneWorkday() * 60 * 60 * 1000);
                                } else {
                                    float sisaCutiDiTglSamaII = Math.abs(sisaCutiDiTglSama - leaveConfig.getHourOneWorkday() / leaveConfig.getHourOneWorkday());
                                    hashSisaCutiKemarin.remove(planCutiStart);
                                    hashTglYgSama.remove(planCutiStart);

                                    hashSisaCutiKemarin.put(planCutiStart, sisaCutiDiTglSamaII);
                                    hashTglYgSama.put(planCutiStart, true);
                                }
                            }

                        }
                        vLisOverlapCuti.remove(idxCuti);
                        idxCuti = idxCuti - 1;
                    }

                    //simapan tanggalnya sementara
                }
                if (scheduleSymbol != null && scheduleSymbol.getBreakOut() != null && scheduleSymbol.getBreakIn() != null && dtIdx != null && listPresencePersonalInOut != null && listPresencePersonalInOut.size() > 0) {
                    dtSchDateTime = null;
                    Date dtpresenceDateTime = null;
                    Date dtSchEmpScheduleBIn = (Date) dtIdx.clone();
                    Date dtSchEmpScheduleBOut = (Date) dtIdx.clone();
                    long preBreakOutX = 0;
                    long preBreakInX = 0;
                    Date dtBreakOut = null;
                    Date dtBreakIn = null;
                    boolean ispreBreakOutsdhdiambil = false;
                    Presence presenceBreak = new Presence();
                    for (int bIdx = 0; bIdx < listPresencePersonalInOut.size(); bIdx++) {
                        presenceBreak = (Presence) listPresencePersonalInOut.get(bIdx);//yang di cari harus ada leavenya 
                        //update by satrya 2012-10-17
                        if (dtSchEmpScheduleBOut != null) {
                            dtSchEmpScheduleBOut.setHours(scheduleSymbol.getBreakOut().getHours());
                            dtSchEmpScheduleBOut.setMinutes(scheduleSymbol.getBreakOut().getMinutes());
                            dtSchEmpScheduleBOut.setSeconds(0);
                        }
                        if (dtSchEmpScheduleBIn != null) {
                            dtSchEmpScheduleBIn.setHours(scheduleSymbol.getBreakIn().getHours());
                            dtSchEmpScheduleBIn.setMinutes(scheduleSymbol.getBreakIn().getMinutes());
                            dtSchEmpScheduleBIn.setSeconds(0);
                        }
                        if (presenceBreak.getScheduleDatetime() != null) {
                            dtSchDateTime = (Date) presenceBreak.getScheduleDatetime().clone();
                            dtSchDateTime.setHours(dtSchDateTime.getHours());
                            dtSchDateTime.setMinutes(dtSchDateTime.getMinutes());
                            dtSchDateTime.setSeconds(0);
                        }
                        if (presenceBreak.getPresenceDatetime() != null) {
                            //update by satrya 2012-10-17
                            dtpresenceDateTime = (Date) presenceBreak.getPresenceDatetime().clone();
                            dtpresenceDateTime.setHours(dtpresenceDateTime.getHours());
                            dtpresenceDateTime.setMinutes(dtpresenceDateTime.getMinutes());
                            dtpresenceDateTime.setSeconds(0);
                        }

                        //cek ada cuti
                        if (vListOverlapCutiSame != null && vListOverlapCutiSame.size() > 0) {
                        } else if (dtpresenceDateTime != null/*update by satrya 2014-01-27 presenceBreak.getScheduleDatetime() !=null*/
                                && presenceBreak.getEmployeeId() == empId
                                //update by satrya 2014-01-27 &&(DateCalc.dayDifference(presenceBreak.getScheduleDatetime(),dtIdx)==0 )){
                                && (DateCalc.dayDifference(dtpresenceDateTime, dtIdx) == 0)) {
                            //kenapa di buat presenceBreak.getScheduleDatetime()!=null ini berpengaruh pada DateCalc.dayDifference(presenceBreak.getScheduleDatetime() xxxx yg menyebabkan exception
                            if (presenceBreak.getStatus() == Presence.STATUS_OUT_PERSONAL) {
                                //update by satrya 2012-09-27
                                //if((presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())){
                                //update by satrya 2013-07-28

                                //jika sewaktu presence Out melewati schedule BI maka setlah presencenya
                                //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                preBreakOutX = dtpresenceDateTime == null ? 0 : dtpresenceDateTime.getTime();///yang di pakai mengurangi itu adalah presence PO  
                                dtBreakOut = dtpresenceDateTime;
                                if (dtSchEmpScheduleBIn != null && presenceBreak.getPresenceDatetime().getTime() > dtSchEmpScheduleBIn.getTime()) {
                                    preBreakOut = Math.abs(presenceBreak.getPresenceDatetime().getTime() - dtSchEmpScheduleBIn.getTime());
                                } else if (presenceBreak.getScheduleDatetime() != null && (presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())) { ///jika karyawan mendahului istirahat
                                    preBreakOut = Math.abs(presenceBreak.getPresenceDatetime().getTime() - presenceBreak.getScheduleDatetime().getTime());///yang di pakai mengurangi itu adalah presence PO 

                                } else if (presenceBreak.getScheduleDatetime() != null && presenceBreak.getScheduleDatetime().getHours() == 0 && presenceBreak.getScheduleDatetime().getMinutes() == 0) {
                                    preBreakOut = Math.abs(presenceBreak.getPresenceDatetime().getTime() - presenceBreak.getScheduleDatetime().getTime());//jika schedulenya 00:00
                                }

                                ispreBreakOutsdhdiambil = false;
                            } else if (presenceBreak.getStatus() == Presence.STATUS_IN_PERSONAL) {
                                //istirahat terlamabat 
                                preBreakInX = presenceBreak.getPresenceDatetime() == null ? 0 : presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                dtBreakIn = presenceBreak.getPresenceDatetime();
                                ///kasusnya misalkan dia hanya terlambat di presence In personal if(preBreakOut !=0L){   
                                if (dtSchEmpScheduleBIn != null && dtBreakOut != null && dtBreakIn != null
                                        && dtBreakOut.getTime() > dtSchEmpScheduleBIn.getTime() && dtBreakIn.getTime() > dtSchEmpScheduleBIn.getTime()) {
                                    //karena sudah pasti melewatijam istirahatnya

                                    //long  tmpBreakDuration = ((Long)breakTimeDuration.get(""+oidSch1St)).longValue();
                                    preBreakIn = presenceBreak.getPresenceDatetime().getTime() - dtSchEmpScheduleBIn.getTime();
                                } else if (presenceBreak.getScheduleDatetime() != null && presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()) { ///jika karyawan melewati jam istirahat
                                    preBreakIn = Math.abs(presenceBreak.getPresenceDatetime().getTime() - presenceBreak.getScheduleDatetime().getTime());///yang di pakai mengurangi itu adalah presence PI
                                } else if (presenceBreak.getScheduleDatetime() != null && presenceBreak.getScheduleDatetime().getHours() == 0 && presenceBreak.getScheduleDatetime().getMinutes() == 0) {
                                    preBreakIn = Math.abs(presenceBreak.getPresenceDatetime().getTime() - presenceBreak.getScheduleDatetime().getTime()); //jika schedulenya 00:00 
                                }
                                breakDuration = breakDuration + (preBreakIn - preBreakOut);


                                ispreBreakOutsdhdiambil = true;
                                preBreakOut = 0L;

                                //breakDuration = breakDuration + presenceBreak.getPresenceDatetime().getTime()-  preBOut.getPresenceDatetime().getTime(); 
                                // preBOut=null;
                                // }
                                // diffBi = diffBi+ (presenceBreak.getScheduleDatetime().getTime() - presenceBreak.getPresenceDatetime().getTime());

                            } else if (presenceBreak.getStatus() == Presence.STATUS_OUT_ON_DUTY) {
                                dtBreakOut = null;//update by satrya 2014-01-27 presenceBreak.getPresenceDatetime();
                                ispreBreakOutsdhdiambil = false;
                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;
                            } else if (presenceBreak.getStatus() == Presence.STATUS_CALL_BACK) {
                                dtBreakIn = null;//update by satrya 2014-01-27 presenceBreak.getPresenceDatetime();
                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;
                                ispreBreakOutsdhdiambil = true;

                            }

                            if (ispreBreakOutsdhdiambil) {
                                preBreakOutX = 0L;
                                preBreakInX = 0L;
                                dtBreakOut = null;
                                dtBreakIn = null;
                            }
                        }//end else if
                    }//end for break In                          

                }



                if (scheduleSymbol != null && !cekAdaCuti && dtIdx != null) {
                    if (scheduleSymbol.getTimeIn() != null && actualIn != null) {
                        Date schIn = (Date) dtIdx.clone();
                        schIn.setHours(scheduleSymbol.getTimeIn().getHours());
                        schIn.setMinutes(scheduleSymbol.getTimeIn().getMinutes());

                        if (schIn != null && actualIn.getTime() > schIn.getTime()) {
                            preLateTimeIn = Math.abs((actualIn.getTime() - schIn.getTime()));
                        } else {
                            preLateTimeIn = 0;
                        }
                    }

                    if (scheduleSymbol.getTimeOut() != null && actualOut != null && dtIdx != null) {
                        Date schOut = (Date) dtIdx.clone();
                        schOut.setHours(scheduleSymbol.getTimeOut().getHours());
                        schOut.setMinutes(scheduleSymbol.getTimeOut().getMinutes());

                        if (actualOut.getTime() < schOut.getTime()) {
                            preLateTimeOut = Math.abs(schOut.getTime() - actualOut.getTime());
                        } else {
                            preLateTimeOut = 0;
                        }
                    }
                    //jika hari libur maka tidak d hitung absence
                    if (oidSch1St != 0 && oidScheduleOff != oidSch1St && scheduleSymbol.getTimeIn() != null && scheduleSymbol.getTimeOut() != null && actualOut == null && actualIn == null) {
                        tidakAdaInOut = tidakAdaInOut + (long) (leaveConfig.getHourOneWorkday() * 60 * 60 * 1000);
                    }
                }


                totalAbsence = totalAbsence + ((preLateTimeIn + preLateTimeOut)) + breakDuration + totalCuti + tidakAdaInOut;
                //totalAbsence = totalAbsence + ((totalLateIn + totalLateOut))+ breakPresence + ((totalLateIn + totalLateOut)<0? istirahat: (Math.abs(istirahat))*-1);

                tmpDate = new Date(tmpDate.getTime() + 24 * 60 * 60 * 1000);
                kk = kk + 1;
            }
        } catch (Exception exc) {
            System.out.println("Exception exc totalAbsence "+empScheduleReport.getEmployeeId() + "Date "+""+tmpDate+ exc);
            totalAbsence = 0;
        }
        //String workDuration = Formater.formatWorkDayHoursMinutes((float) ((((totalAbsence)) / (leaveConfig.getHourOneWorkday() * 60f * 60f * 1000f))) * -1, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
        Vector hasil = new Vector();
        hasil.add((float) ((((totalAbsence)) / (leaveConfig.getHourOneWorkday() * 60f * 60f * 1000f))));
        //hasil.add(workDuration);
        return hasil;
    }

    /**
     *
     * @param hasSchedule
     * @param hashTblSchedule
     * @param leaveConfig
     * @param oidScheduleOff
     * @param tmpEndDate
     * @param listPresencePersonalInOut
     * @param tmpDate
     * @param hashSymbol
     * @param dtSchDateTime
     * @param empScheduleReport
     * @param summaryEmployeeAttendance
     * @param breakTimeDuration
     * @param reasonNo
     * @param propReasonNo
     * @param propCheckLeaveExist
     * @param hasDfltSchedule
     * @param hashPayInputAdj
     * @param payInputIdx
     * @param payInputTime
     * @return
     */
    public static Vector timeAbsenceDurationReason(Hashtable hasSchedule, Hashtable hashTblSchedule, I_Leave leaveConfig, long oidScheduleOff, Date tmpEndDate, Vector listPresencePersonalInOut, Date tmpDate, Hashtable hashSymbol, Date dtSchDateTime, EmpScheduleReport empScheduleReport, SummaryEmployeeAttendance summaryEmployeeAttendance, Hashtable breakTimeDuration, int reasonNo, int propReasonNo, int propCheckLeaveExist, Hashtable hasDfltSchedule, Hashtable hashPayInputAdj, String payInputIdx, String payInputTime) {

        // String workDuration;

        long totalAbsence = 0;
        //String payInputIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_ADJUSMENT] + payInputCode;
        //String payInputTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_ADJUSMENT] + payInputCode;
        double valueTime = 0;
        int valueIdx = 0;
        //update by satrya 2014-05-01 untuk adjusment
        if (payInputIdx != null && payInputIdx.length() > 0 && payInputTime != null && payInputTime.length() > 0 && tmpEndDate != null && tmpDate != null && hashPayInputAdj != null && hashPayInputAdj.size() > 0) {
            Date dtAdjusment = null;
            long duration = tmpEndDate.getTime() - tmpDate.getTime();
            if (duration > 0) {
                int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                for (int idxDt = 0; idxDt < itDate; idxDt++) {
                    dtAdjusment = new Date(tmpDate.getYear(), tmpDate.getMonth(), (tmpDate.getDate() + idxDt));
                    boolean adaAdj = false;
                    if (dtAdjusment != null && hashPayInputAdj.containsKey(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                        double tmpValueIdx = (Double) hashPayInputAdj.get(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                        valueIdx = (int) tmpValueIdx;
                        adaAdj = true;
                    }
                    if (dtAdjusment != null && hashPayInputAdj.containsKey(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                        valueTime = (Double) hashPayInputAdj.get(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                        adaAdj = true;
                    }
                    if (adaAdj) {
                        break;//karena di pay input hanya 1 tgl saja yg di simpan
                    }

                }
            }


        }
        try {
            ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
            // Hashtable hashDtCuti=new Hashtable();
            int kk = 0;
            Hashtable hashTglYgSama = new Hashtable();
            Hashtable hashSisaCutiKemarin = new Hashtable();
            while (tmpDate.before(tmpEndDate) || tmpDate.equals(tmpEndDate)) {

                //int kk = tmpDate.getDate()-1;
                long breakPresence = 0;
                long cuti = 0;
                long oidSch2st = 0;
                Long oidSch1St = empScheduleReport.getEmpSchedules1st(kk);
                Date actualIn = empScheduleReport.getEmpIn1st(kk);
                // if(actualIn!=null){
                // actualIn.setSeconds(0);
                //}
                Date actualOut = empScheduleReport.getEmpOut1st(kk);
                // if(actualOut!=null){
                // actualOut.setSeconds(0);
                // }
                Long empId = empScheduleReport.getEmployeeId();
                Date dtIdx = empScheduleReport.getDtIDate(kk);//mengetahui idx date


                // long totalAbsenceIn=0;
                //long totalAbsenceOut=0;
                if (scheduleSymbol != null && scheduleSymbol.getOID() != oidSch1St) {
                    scheduleSymbol = (ScheduleSymbol) hasSchedule.get("" + oidSch1St);
                    if (scheduleSymbol == null) {

                        if (oidSch1St != 0) {
                            try {
                                scheduleSymbol = (ScheduleSymbol) hashTblSchedule.get(oidSch1St);//PstScheduleSymbol.fetchExc(oidSch1St);
                            } catch (Exception exc) {
                            }
                            hasSchedule.put("" + oidSch1St, scheduleSymbol);
                        }
                    }
                } else {
                    if (scheduleSymbol == null) {

                        if (oidSch1St != 0) {
                            try {
                                scheduleSymbol = (ScheduleSymbol) hashTblSchedule.get(oidSch1St);//scheduleSymbol  = PstScheduleSymbol.fetchExc(oidSch1St);
                            } catch (Exception exc) {
                            }
                            hasSchedule.put("" + oidSch1St, scheduleSymbol);
                        }
                    }
                }

                //update by satrya 2014-01-23

                long preBreakOut = 0;
                long preBreakIn = 0;
                long breakDuration = 0L;
                long preLateTimeIn = 0;
                long preLateTimeOut = 0;
                long totalCuti = 0;
                boolean cekAdaCuti = false;
                long tidakAdaInOut = 0;

                Vector vLisOverlapCuti = SessLeaveApp.checkOverLapsLeaveTakenNoTime(empId, dtIdx, dtIdx);
                Vector vListOverlapCutiSame = (Vector) vLisOverlapCuti.clone();
                if (vLisOverlapCuti != null && vLisOverlapCuti.size() > 0) {
                    cekAdaCuti = true;
                    for (int idxCuti = 0; idxCuti < vLisOverlapCuti.size(); idxCuti++) {
                        LeaveCheckTakenDateFinish leaveCheckTaken = (LeaveCheckTakenDateFinish) vLisOverlapCuti.get(idxCuti);
                        Date planCutiStart = leaveCheckTaken.getTakenDate();
                        if (leaveCheckTaken.getTakenQty() <= leaveConfig.getHourOneWorkday() / leaveConfig.getHourOneWorkday()) {
                            totalCuti = totalCuti + (long) (leaveCheckTaken.getTakenQty() * leaveConfig.getHourOneWorkday() * 60 * 60 * 1000);
                        } else {

                            if (hashTglYgSama != null && (hashTglYgSama.size() == 0 || !hashTglYgSama.containsKey(planCutiStart))) {

                                totalCuti = totalCuti + (long) (leaveConfig.getHourOneWorkday() * 60 * 60 * 1000);
                                float jumlahSisa = leaveCheckTaken.getTakenQty() - (leaveConfig.getHourOneWorkday() / leaveConfig.getHourOneWorkday());
                                hashSisaCutiKemarin.put(planCutiStart, jumlahSisa);
                                hashTglYgSama.put(planCutiStart, true);
                            } else if (hashSisaCutiKemarin != null && hashTglYgSama != null && hashTglYgSama.size() > 0
                                    && hashSisaCutiKemarin.size() > 0 && hashTglYgSama.containsKey(planCutiStart)) {
                                float sisaCutiDiTglSama = (Float) hashSisaCutiKemarin.get(planCutiStart);
                                if (sisaCutiDiTglSama <= leaveConfig.getHourOneWorkday() / leaveConfig.getHourOneWorkday()) {
                                    totalCuti = totalCuti + (long) (sisaCutiDiTglSama * leaveConfig.getHourOneWorkday() * 60 * 60 * 1000);
                                } else {
                                    float sisaCutiDiTglSamaII = Math.abs(sisaCutiDiTglSama - leaveConfig.getHourOneWorkday() / leaveConfig.getHourOneWorkday());
                                    hashSisaCutiKemarin.remove(planCutiStart);
                                    hashTglYgSama.remove(planCutiStart);

                                    hashSisaCutiKemarin.put(planCutiStart, sisaCutiDiTglSamaII);
                                    hashTglYgSama.put(planCutiStart, true);
                                }
                            }

                        }
                        vLisOverlapCuti.remove(idxCuti);
                        idxCuti = idxCuti - 1;
                    }

                    //simapan tanggalnya sementara
                }
                if (scheduleSymbol != null && scheduleSymbol.getBreakOut() != null && scheduleSymbol.getBreakIn() != null && dtIdx != null && listPresencePersonalInOut != null && listPresencePersonalInOut.size() > 0) {
                    dtSchDateTime = null;
                    Date dtpresenceDateTime = null;
                    Date dtSchEmpScheduleBIn = (Date) dtIdx.clone();
                    Date dtSchEmpScheduleBOut = (Date) dtIdx.clone();
                    long preBreakOutX = 0;
                    long preBreakInX = 0;
                    Date dtBreakOut = null;
                    Date dtBreakIn = null;
                    boolean ispreBreakOutsdhdiambil = false;
                    Presence presenceBreak = new Presence();
                    for (int bIdx = 0; bIdx < listPresencePersonalInOut.size(); bIdx++) {
                        presenceBreak = (Presence) listPresencePersonalInOut.get(bIdx);//yang di cari harus ada leavenya 
                        //update by satrya 2012-10-17
                        if (dtSchEmpScheduleBOut != null) {
                            dtSchEmpScheduleBOut.setHours(scheduleSymbol.getBreakOut().getHours());
                            dtSchEmpScheduleBOut.setMinutes(scheduleSymbol.getBreakOut().getMinutes());
                            dtSchEmpScheduleBOut.setSeconds(0);
                        }
                        if (dtSchEmpScheduleBIn != null) {
                            dtSchEmpScheduleBIn.setHours(scheduleSymbol.getBreakIn().getHours());
                            dtSchEmpScheduleBIn.setMinutes(scheduleSymbol.getBreakIn().getMinutes());
                            dtSchEmpScheduleBIn.setSeconds(0);
                        }
                        if (presenceBreak.getScheduleDatetime() != null) {
                            dtSchDateTime = (Date) presenceBreak.getScheduleDatetime().clone();
                            dtSchDateTime.setHours(dtSchDateTime.getHours());
                            dtSchDateTime.setMinutes(dtSchDateTime.getMinutes());
                            dtSchDateTime.setSeconds(0);
                        }
                        if (presenceBreak.getPresenceDatetime() != null) {
                            //update by satrya 2012-10-17
                            dtpresenceDateTime = (Date) presenceBreak.getPresenceDatetime().clone();
                            dtpresenceDateTime.setHours(dtpresenceDateTime.getHours());
                            dtpresenceDateTime.setMinutes(dtpresenceDateTime.getMinutes());
                            dtpresenceDateTime.setSeconds(0);
                        }

                        //cek ada cuti
                        if (vListOverlapCutiSame != null && vListOverlapCutiSame.size() > 0) {
                        } else if (dtpresenceDateTime != null/*update by satrya 2014-01-27 presenceBreak.getScheduleDatetime() !=null*/
                                && presenceBreak.getEmployeeId() == empId
                                //update by satrya 2014-01-27 &&(DateCalc.dayDifference(presenceBreak.getScheduleDatetime(),dtIdx)==0 )){
                                && (DateCalc.dayDifference(dtpresenceDateTime, dtIdx) == 0)) {
                            //kenapa di buat presenceBreak.getScheduleDatetime()!=null ini berpengaruh pada DateCalc.dayDifference(presenceBreak.getScheduleDatetime() xxxx yg menyebabkan exception
                            if (presenceBreak.getStatus() == Presence.STATUS_OUT_PERSONAL) {
                                //update by satrya 2012-09-27
                                //if((presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())){
                                //update by satrya 2013-07-28

                                //jika sewaktu presence Out melewati schedule BI maka setlah presencenya
                                //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                preBreakOutX = dtpresenceDateTime == null ? 0 : dtpresenceDateTime.getTime();///yang di pakai mengurangi itu adalah presence PO  
                                dtBreakOut = dtpresenceDateTime;
                                if (dtSchEmpScheduleBIn != null && presenceBreak.getPresenceDatetime().getTime() > dtSchEmpScheduleBIn.getTime()) {
                                    preBreakOut = Math.abs(presenceBreak.getPresenceDatetime().getTime() - dtSchEmpScheduleBIn.getTime());
                                } else if (presenceBreak.getScheduleDatetime() != null && (presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())) { ///jika karyawan mendahului istirahat
                                    preBreakOut = Math.abs(presenceBreak.getPresenceDatetime().getTime() - presenceBreak.getScheduleDatetime().getTime());///yang di pakai mengurangi itu adalah presence PO 

                                } else if (presenceBreak.getScheduleDatetime() != null && presenceBreak.getScheduleDatetime().getHours() == 0 && presenceBreak.getScheduleDatetime().getMinutes() == 0) {
                                    preBreakOut = Math.abs(presenceBreak.getPresenceDatetime().getTime() - presenceBreak.getScheduleDatetime().getTime());//jika schedulenya 00:00
                                }

                                ispreBreakOutsdhdiambil = false;
                            } else if (presenceBreak.getStatus() == Presence.STATUS_IN_PERSONAL) {
                                //istirahat terlamabat 
                                preBreakInX = presenceBreak.getPresenceDatetime() == null ? 0 : presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                dtBreakIn = presenceBreak.getPresenceDatetime();
                                ///kasusnya misalkan dia hanya terlambat di presence In personal if(preBreakOut !=0L){   
                                if (dtSchEmpScheduleBIn != null && dtBreakOut != null && dtBreakIn != null
                                        && dtBreakOut.getTime() > dtSchEmpScheduleBIn.getTime() && dtBreakIn.getTime() > dtSchEmpScheduleBIn.getTime()) {
                                    //karena sudah pasti melewatijam istirahatnya

                                    //long  tmpBreakDuration = ((Long)breakTimeDuration.get(""+oidSch1St)).longValue();
                                    preBreakIn = presenceBreak.getPresenceDatetime().getTime() - dtSchEmpScheduleBIn.getTime();
                                } else if (presenceBreak.getScheduleDatetime() != null && presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()) { ///jika karyawan melewati jam istirahat
                                    preBreakIn = Math.abs(presenceBreak.getPresenceDatetime().getTime() - presenceBreak.getScheduleDatetime().getTime());///yang di pakai mengurangi itu adalah presence PI
                                } else if (presenceBreak.getScheduleDatetime() != null && presenceBreak.getScheduleDatetime().getHours() == 0 && presenceBreak.getScheduleDatetime().getMinutes() == 0) {
                                    preBreakIn = Math.abs(presenceBreak.getPresenceDatetime().getTime() - presenceBreak.getScheduleDatetime().getTime()); //jika schedulenya 00:00 
                                }
                                breakDuration = breakDuration + (preBreakIn - preBreakOut);


                                ispreBreakOutsdhdiambil = true;
                                preBreakOut = 0L;

                                //breakDuration = breakDuration + presenceBreak.getPresenceDatetime().getTime()-  preBOut.getPresenceDatetime().getTime(); 
                                // preBOut=null;
                                // }
                                // diffBi = diffBi+ (presenceBreak.getScheduleDatetime().getTime() - presenceBreak.getPresenceDatetime().getTime());

                            } else if (presenceBreak.getStatus() == Presence.STATUS_OUT_ON_DUTY) {
                                dtBreakOut = null;//update by satrya 2014-01-27 presenceBreak.getPresenceDatetime();
                                ispreBreakOutsdhdiambil = false;
                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;
                            } else if (presenceBreak.getStatus() == Presence.STATUS_CALL_BACK) {
                                dtBreakIn = null;//update by satrya 2014-01-27 presenceBreak.getPresenceDatetime();
                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;
                                ispreBreakOutsdhdiambil = true;

                            }

                            if (ispreBreakOutsdhdiambil) {
                                preBreakOutX = 0L;
                                preBreakInX = 0L;
                                dtBreakOut = null;
                                dtBreakIn = null;
                            }
                        }//end else if
                    }//end for break In                          

                }



                if (scheduleSymbol != null && !cekAdaCuti && dtIdx != null) {
                    if (scheduleSymbol.getTimeIn() != null && actualIn != null) {
                        Date schIn = (Date) dtIdx.clone();
                        schIn.setHours(scheduleSymbol.getTimeIn().getHours());
                        schIn.setMinutes(scheduleSymbol.getTimeIn().getMinutes());

                        if (schIn != null && actualIn.getTime() > schIn.getTime()) {
                            preLateTimeIn = Math.abs((actualIn.getTime() - schIn.getTime()));
                        } else {
                            preLateTimeIn = 0;
                        }
                    }

                    if (scheduleSymbol.getTimeOut() != null && actualOut != null && dtIdx != null) {
                        Date schOut = (Date) dtIdx.clone();
                        schOut.setHours(scheduleSymbol.getTimeOut().getHours());
                        schOut.setMinutes(scheduleSymbol.getTimeOut().getMinutes());

                        if (actualOut.getTime() < schOut.getTime()) {
                            preLateTimeOut = Math.abs(schOut.getTime() - actualOut.getTime());
                        } else {
                            preLateTimeOut = 0;
                        }
                    }
                    //jika hari libur maka tidak d hitung absence
                    if (oidSch1St != 0 && oidScheduleOff != oidSch1St && scheduleSymbol.getTimeIn() != null && scheduleSymbol.getTimeOut() != null && actualOut == null && actualIn == null) {
                        tidakAdaInOut = tidakAdaInOut + (long) (leaveConfig.getHourOneWorkday() * 60 * 60 * 1000);
                    }
                }


                totalAbsence = totalAbsence + ((preLateTimeIn + preLateTimeOut)) + breakDuration + totalCuti + tidakAdaInOut;
                //totalAbsence = totalAbsence + ((totalLateIn + totalLateOut))+ breakPresence + ((totalLateIn + totalLateOut)<0? istirahat: (Math.abs(istirahat))*-1);

                tmpDate = new Date(tmpDate.getTime() + 24 * 60 * 60 * 1000);
                kk = kk + 1;
            }
        } catch (Exception exc) {
            System.out.println("Exception exc totalAbsence "+empScheduleReport.getEmployeeId()+ "Date "+""+tmpDate + exc);
            totalAbsence = 0;
        }
        String workDuration = Formater.formatWorkDayHoursMinutes((float) (valueTime + (((totalAbsence)) / (leaveConfig.getHourOneWorkday() * 60f * 60f * 1000f))) * -1, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
        Vector hasil = new Vector();
        hasil.add(valueIdx);
        hasil.add(workDuration);
        return hasil;
    }

    public static Vector timeWorkOnDutty(Hashtable hasSchedule, Hashtable hashTblSchedule, I_Leave leaveConfig, long oidScheduleOff, Date tmpEndDate, Vector listPresencePersonalInOut, Date tmpDate, Hashtable hashSymbol, Date dtSchDateTime, EmpScheduleReport empScheduleReport, SummaryEmployeeAttendance summaryEmployeeAttendance, Hashtable breakTimeDuration, int reasonNo, int propReasonNo, int propCheckLeaveExist, Hashtable hasDfltSchedule, Hashtable hashPayInputAdj, String payInputIdx, String payInputTime) {

        // String workDuration;
        long totalWorkOnDutty = 0;
        //String payInputIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_ADJUSMENT] + payInputCode;
        //String payInputTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_ADJUSMENT] + payInputCode;
        double valueTime = 0;
        int valueIdx = 0;
        //update by satrya 2014-05-01 untuk adjusment
        if (payInputIdx != null && payInputIdx.length() > 0 && payInputTime != null && payInputTime.length() > 0 && tmpEndDate != null && tmpDate != null && hashPayInputAdj != null && hashPayInputAdj.size() > 0) {
            Date dtAdjusment = null;
            long duration = tmpEndDate.getTime() - tmpDate.getTime();
            if (duration > 0) {
                int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                for (int idxDt = 0; idxDt < itDate; idxDt++) {
                    dtAdjusment = new Date(tmpDate.getYear(), tmpDate.getMonth(), (tmpDate.getDate() + idxDt));
                    boolean adaAdj = false;
                    if (dtAdjusment != null && hashPayInputAdj.containsKey(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                        double tmpValueIdx = (Double) hashPayInputAdj.get(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                        valueIdx = (int) tmpValueIdx;
                        adaAdj = true;
                    }
                    if (dtAdjusment != null && hashPayInputAdj.containsKey(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                        valueTime = (Double) hashPayInputAdj.get(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                        adaAdj = true;
                    }
                    if (adaAdj) {
                        break;//karena di pay input hanya 1 tgl saja yg di simpan
                    }

                }
            }


        }
        
       
        try {
            ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
            int kk = 0;
            while (tmpDate.before(tmpEndDate) || tmpDate.equals(tmpEndDate)) {

                //int kk = tmpDate.getDate()-1;
                long totWorkDutty = 0;
                long oidSch2st = 0;//belum di pakai
                Long oidSch1St = empScheduleReport.getEmpSchedules1st(kk);
                //Date actualIn = empScheduleReport.getEmpIn1st(kk);
                //Date actualOut = empScheduleReport.getEmpOut1st(kk);
                Long empId = empScheduleReport.getEmployeeId();
                Date dtIdx = empScheduleReport.getDtIDate(kk);//mengetahui idx date

                 if(String.valueOf(empId).equalsIgnoreCase("504404530473672020")){
                    boolean x =true;
                }
                if(Formater.formatDate(tmpDate, "yyyy-MM-dd").equalsIgnoreCase("2014-02-21")){
                    boolean x =true;
                }
                if (scheduleSymbol != null && scheduleSymbol.getOID() != oidSch1St) {
                    scheduleSymbol = (ScheduleSymbol) hasSchedule.get("" + oidSch1St);
                    if (scheduleSymbol == null) {

                        if (oidSch1St != 0) {
                            try {
                                scheduleSymbol = (ScheduleSymbol) hashTblSchedule.get(oidSch1St);//PstScheduleSymbol.fetchExc(oidSch1St);
                            } catch (Exception exc) {
                            }
                            hasSchedule.put("" + oidSch1St, scheduleSymbol);
                        }
                    }
                } else {
                    if (scheduleSymbol == null) {

                        if (oidSch1St != 0) {
                            try {
                                scheduleSymbol = (ScheduleSymbol) hashTblSchedule.get(oidSch1St);//scheduleSymbol  = PstScheduleSymbol.fetchExc(oidSch1St);
                            } catch (Exception exc) {
                            }
                            hasSchedule.put("" + oidSch1St, scheduleSymbol);
                        }
                    }
                }
                Date dtSchEmpScheduleBIn = null;
                Date dtSchEmpScheduleBOut = null;
                //Presence presenceBreak = new Presence(); 
                if (scheduleSymbol != null && scheduleSymbol.getBreakIn() != null && dtIdx != null) {
                    dtSchEmpScheduleBOut = (Date) dtIdx.clone();
                    //dtSchEmpScheduleBOut = (Date) dtScheduleBo1st.clone();
                    dtSchEmpScheduleBOut.setHours(scheduleSymbol.getBreakOut().getHours());
                    dtSchEmpScheduleBOut.setMinutes(scheduleSymbol.getBreakOut().getMinutes());
                    // dtSchEmpScheduleBOut.setSeconds(0);
                }
                if (scheduleSymbol != null && scheduleSymbol.getBreakIn() != null && dtIdx != null) {
                    dtSchEmpScheduleBIn = (Date) dtIdx.clone();
                    //dtSchEmpScheduleBIn = (Date) dtScheduleBi1st.clone();
                    dtSchEmpScheduleBIn.setHours(scheduleSymbol.getBreakIn().getHours());
                    dtSchEmpScheduleBIn.setMinutes(scheduleSymbol.getBreakIn().getMinutes());
                    // dtSchEmpScheduleBIn.setSeconds(0);
                    if (dtSchEmpScheduleBIn != null && dtSchEmpScheduleBOut != null && dtSchEmpScheduleBIn.getHours() < dtSchEmpScheduleBOut.getHours()) {
                        dtSchEmpScheduleBIn = new Date(dtSchEmpScheduleBIn.getTime() + 24 * 60 * 60 * 1000);
                    }
                }
                //Presence presenceBreak = new Presence(); 
                if (empScheduleReport.getEmpSchedules1stSize() > 0
                        && listPresencePersonalInOut != null && listPresencePersonalInOut.size() > 0 && dtIdx != null) {
                    Date dtpresenceDateTime = null;
                    //Date dtScheduleBo1st = scheduleSymbol.getBreakOut();
                    //Date dtScheduleBi1st = scheduleSymbol.getBreakIn();
                    boolean ispreBreakOutsdhdiambil = false;
                    long preBreakOut = 0;
                    long preBreakIn = 0;
                    Date dtBreakOut = null;
                    Date dtBreakIn = null;

                    for (int bIdx = 0; bIdx < listPresencePersonalInOut.size(); bIdx++) {
                        Presence presenceBreak = (Presence) listPresencePersonalInOut.get(bIdx);//yang di cari harus ada leavenya 
                        //update by satrya 2012-10-17
                        if (presenceBreak.getScheduleDatetime() != null && presenceBreak.getPresenceDatetime() != null
                                && presenceBreak.getEmployeeId() == empScheduleReport.getEmployeeId()
                                && (DateCalc.dayDifference(presenceBreak.getPresenceDatetime(), dtIdx) == 0)) {
                            //update by satrya 2014-01-27 &&(DateCalc.dayDifference(presenceBreak.getScheduleDatetime(),dtIdx)==0 )){

                            if (presenceBreak.getPresenceDatetime() != null) {
                                //update by satrya 2012-10-17
                                dtpresenceDateTime = (Date) presenceBreak.getPresenceDatetime().clone();
                                //dtpresenceDateTime.setHours(dtpresenceDateTime.getHours());
                                //dtpresenceDateTime.setMinutes(dtpresenceDateTime.getMinutes());
                                // dtpresenceDateTime.setSeconds(0);       
                            }
                            if (presenceBreak.getStatus() == Presence.STATUS_OUT_ON_DUTY) {
                                if (dtpresenceDateTime != null) {
                                    preBreakOut = dtpresenceDateTime.getTime();///yang di pakai mengurangi itu adalah presence PO 
                                }

                                dtBreakOut = dtpresenceDateTime;
                                //update by satrya 2014-01-28
                                //ispreBreakOutsdhdiambil = true;
                                ispreBreakOutsdhdiambil = false;
                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;
                            } else if (presenceBreak.getStatus() == Presence.STATUS_CALL_BACK) {
                                if (dtpresenceDateTime != null) {
                                    preBreakIn = dtpresenceDateTime.getTime();///yang di pakai mengurangi itu adalah presence PI
                                }
                                dtBreakIn = dtpresenceDateTime;
                                ispreBreakOutsdhdiambil = true;
                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;
                            }
                            if (ispreBreakOutsdhdiambil) {
                                //jika menggunakan form Dutty
                                Vector listFormDutty = new Vector();
                                if (listFormDutty != null && listFormDutty.size() > 0) {
                                    //jika mengunnakan listformDutty
                                } else if (preBreakOut != 0 && preBreakIn != 0) {
                                    totWorkDutty = (preBreakIn - PstEmpSchedule.breakTimeIntersectionVer3(empId, hasDfltSchedule, hashTblSchedule, oidSch1St, oidSch2st, dtBreakOut, dtBreakIn)) - preBreakOut;//PstEmpSchedule.breakTimeIntersectionVer2(empId, dtBreakOut, dtBreakIn))- preBreakOut;
                                }
                            }


                        }//end else if
                    }
                }//end for break In

                totalWorkOnDutty = totalWorkOnDutty + totWorkDutty;
                //totalAbsence = totalAbsence + ((totalLateIn + totalLateOut))+ breakPresence + ((totalLateIn + totalLateOut)<0? istirahat: (Math.abs(istirahat))*-1);

                tmpDate = new Date(tmpDate.getTime() + 24 * 60 * 60 * 1000);
                kk = kk + 1;
            }
        } catch (Exception exc) {
            System.out.println("Exception exc totalAbsence "+empScheduleReport.getEmployeeId() + "Date "+""+tmpDate + exc);
            totalWorkOnDutty = 0;
        }
        String workDuration = Formater.formatWorkDayHoursMinutes((float) valueTime + ((totalWorkOnDutty) / (leaveConfig.getHourOneWorkday() * 60f * 60f * 1000f)), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
        Vector hasil = new Vector();
        hasil.add(valueIdx);
        hasil.add(workDuration);
        return hasil;
    }

    public Vector timeWorkDuration(Hashtable hasSchedule, Hashtable hashTblSchedule, I_Leave leaveConfig, long oidScheduleOff, Date tmpEndDate, Vector listPresencePersonalInOut, Date tmpDate, Hashtable hashSymbol, EmpScheduleReport empScheduleReport, SummaryEmployeeAttendance summaryEmployeeAttendance, Hashtable breakTimeDuration, int propCheckLeaveExist, Hashtable hasDfltSchedule, Vector vOvertimeDetail) {
        long empIdxx = 0;//gunanya hanya untuk testy
        // String workDuration;
        long totalWOrkDur = 0;
        //Hashtable hashDtCutiAndOT=new Hashtable();
        try {
            ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
            // Hashtable hashDtCuti=new Hashtable();
            int kk = 0;
            while (tmpDate.before(tmpEndDate) || tmpDate.equals(tmpEndDate)) {
                // Date dtSchEmpScheduleBIn = null;
                //   Date dtSchEmpScheduleBOut = null;
                //int kk = tmpDate.getDate()-1;
                //long breakPresence=0;
                long oidSch2st = 0;///belum di pakai
                Long oidSch1St = empScheduleReport.getEmpSchedules1st(kk);
                Date actualIn = empScheduleReport.getEmpIn1st(kk);
                Date actualOut = empScheduleReport.getEmpOut1st(kk);
                Long empId = empScheduleReport.getEmployeeId();
                Date dtIdx = empScheduleReport.getDtIDate(kk);//mengetahui idx date
                empIdxx = empId;//hanya untuk test
                //Vector vectFirstSchedule = PstScheduleSymbol.getScheduleData(oidSch1St, tmpDate);
                //Vector vectFirstSchedule = PstScheduleSymbol.getScheduleData(oidSch1St, tmpDate);
                if (scheduleSymbol != null && scheduleSymbol.getOID() != oidSch1St) {
                    scheduleSymbol = (ScheduleSymbol) hasSchedule.get("" + oidSch1St);
                    if (scheduleSymbol == null) {

                        if (oidSch1St != 0) {
                            try {
                                scheduleSymbol = (ScheduleSymbol) hashTblSchedule.get(oidSch1St);//PstScheduleSymbol.fetchExc(oidSch1St);
                            } catch (Exception exc) {
                            }
                            hasSchedule.put("" + oidSch1St, scheduleSymbol);
                        }
                    }
                } else {
                    if (scheduleSymbol == null) {

                        if (oidSch1St != 0) {
                            try {
                                scheduleSymbol = (ScheduleSymbol) hashTblSchedule.get(oidSch1St);//scheduleSymbol  = PstScheduleSymbol.fetchExc(oidSch1St);
                            } catch (Exception exc) {
                            }
                            hasSchedule.put("" + oidSch1St, scheduleSymbol);
                        }
                    }
                }

                /*   if(scheduleSymbol!=null && scheduleSymbol.getBreakOut()!=null && dtIdx!=null ){
                 dtSchEmpScheduleBOut = (Date) dtIdx.clone();
                 // dtSchEmpScheduleBOut = (Date) dtScheduleBo1st.clone();
                 dtSchEmpScheduleBOut.setHours(scheduleSymbol.getBreakOut().getHours());
                 dtSchEmpScheduleBOut.setMinutes(scheduleSymbol.getBreakOut().getMinutes());
                 // dtSchEmpScheduleBOut.setSeconds(0);
                 }*/
//update by satrya 2014-01-23
                long preBreakOut = 0;
                long preBreakIn = 0;
                long breakDuration = 0L;
                if (scheduleSymbol != null && scheduleSymbol.getBreakOut() != null && scheduleSymbol.getBreakIn() != null && dtIdx != null && listPresencePersonalInOut != null && listPresencePersonalInOut.size() > 0) {
                    Date dtSchDateTime = null;
                    Date dtpresenceDateTime = null;
                    Date dtSchEmpScheduleBIn = (Date) dtIdx.clone();
                    Date dtSchEmpScheduleBOut = (Date) dtIdx.clone();
                    long preBreakOutX = 0;
                    long preBreakInX = 0;
                    Date dtBreakOut = null;
                    Date dtBreakIn = null;
                    boolean ispreBreakOutsdhdiambil = false;
                    Presence presenceBreak = new Presence();
                    for (int bIdx = 0; bIdx < listPresencePersonalInOut.size(); bIdx++) {
                        presenceBreak = (Presence) listPresencePersonalInOut.get(bIdx);//yang di cari harus ada leavenya 
                        //update by satrya 2012-10-17
                        if (dtSchEmpScheduleBOut != null) {
                            dtSchEmpScheduleBOut.setHours(scheduleSymbol.getBreakOut().getHours());
                            dtSchEmpScheduleBOut.setMinutes(scheduleSymbol.getBreakOut().getMinutes());
                            dtSchEmpScheduleBOut.setSeconds(0);
                        }
                        if (dtSchEmpScheduleBOut != null) {
                            dtSchEmpScheduleBIn.setHours(scheduleSymbol.getBreakIn().getHours());
                            dtSchEmpScheduleBIn.setMinutes(scheduleSymbol.getBreakIn().getMinutes());
                            dtSchEmpScheduleBIn.setSeconds(0);
                        }
                        if (presenceBreak.getScheduleDatetime() != null) {
                            dtSchDateTime = (Date) presenceBreak.getScheduleDatetime().clone();
                            dtSchDateTime.setHours(dtSchDateTime.getHours());
                            dtSchDateTime.setMinutes(dtSchDateTime.getMinutes());
                            dtSchDateTime.setSeconds(0);
                        }
                        if (presenceBreak.getPresenceDatetime() != null) {
                            //update by satrya 2012-10-17
                            dtpresenceDateTime = (Date) presenceBreak.getPresenceDatetime().clone();
                            dtpresenceDateTime.setHours(dtpresenceDateTime.getHours());
                            dtpresenceDateTime.setMinutes(dtpresenceDateTime.getMinutes());
                            dtpresenceDateTime.setSeconds(0);
                        }

                        if (presenceBreak.getEmployeeId() == empId
                                && (DateCalc.dayDifference(presenceBreak.getPresenceDatetime(), dtIdx) == 0)
                                && presenceBreak.getScheduleDatetime() == null) {
                            if (presenceBreak.getStatus() == Presence.STATUS_OUT_ON_DUTY) {
                                //bOut =bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+"<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                                // dBout = bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");
                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;

                            } else if (presenceBreak.getStatus() == Presence.STATUS_CALL_BACK) {
                                //bIn =bIn+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+ "<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                                // dBin = dBin+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm"); 
                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;

                            }

                        } else if (dtpresenceDateTime != null/*update by satrya 2014-01-27 presenceBreak.getScheduleDatetime() !=null*/
                                && presenceBreak.getEmployeeId() == empId
                                //update by satrya 2014-01-27 &&(DateCalc.dayDifference(presenceBreak.getScheduleDatetime(),dtIdx)==0 )){
                                && (DateCalc.dayDifference(dtpresenceDateTime, dtIdx) == 0)) {
                            //kenapa di buat presenceBreak.getScheduleDatetime()!=null ini berpengaruh pada DateCalc.dayDifference(presenceBreak.getScheduleDatetime() xxxx yg menyebabkan exception
                            if (presenceBreak.getStatus() == Presence.STATUS_OUT_PERSONAL) {
                                //update by satrya 2012-09-27
                                //if((presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())){
                                //update by satrya 2013-07-28

                                //jika sewaktu presence Out melewati schedule BI maka setlah presencenya
                                //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                preBreakOutX = dtpresenceDateTime == null ? 0 : dtpresenceDateTime.getTime();///yang di pakai mengurangi itu adalah presence PO  
                                dtBreakOut = dtpresenceDateTime;
                                if (dtSchEmpScheduleBIn != null && presenceBreak.getPresenceDatetime().getTime() > dtSchEmpScheduleBIn.getTime()) {
                                    preBreakOut = presenceBreak.getPresenceDatetime().getTime();
                                } else if ((presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())) { ///jika karyawan mendahului istirahat
                                    preBreakOut = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PO 

                                } else if (presenceBreak.getScheduleDatetime().getHours() == 0 && presenceBreak.getScheduleDatetime().getMinutes() == 0) {
                                    preBreakOut = presenceBreak.getPresenceDatetime().getTime();//jika schedulenya 00:00
                                } else {
                                    preBreakOut = presenceBreak.getScheduleDatetime().getTime(); //yang di pakai mengurangi adalah schedule PO
                                }

                                ispreBreakOutsdhdiambil = false;
                            } else if (presenceBreak.getStatus() == Presence.STATUS_IN_PERSONAL) {
                                //istirahat terlamabat 
                                preBreakInX = presenceBreak.getPresenceDatetime() == null ? 0 : presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                dtBreakIn = presenceBreak.getPresenceDatetime();
                                if (preBreakOut != 0L) {
                                    //update by satrya 2012-09-27
                                    //if(presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()){
                                    //update by satrya 2013-07-28\
                                    //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                    if (dtSchEmpScheduleBIn != null && dtBreakOut != null && dtBreakIn != null
                                            && dtBreakOut.getTime() > dtSchEmpScheduleBIn.getTime() && dtBreakIn.getTime() > dtSchEmpScheduleBIn.getTime()) {
                                        //karena sudah pasti melewatijam istirahatnya
                                        long tmpBreakDuration = ((Long) breakTimeDuration.get("" + oidSch1St)).longValue();
                                        preBreakIn = presenceBreak.getPresenceDatetime().getTime() + tmpBreakDuration;
                                    } else if (presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()) { ///jika karyawan melewati jam istirahat
                                        preBreakIn = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                    } else if (presenceBreak.getScheduleDatetime().getHours() == 0 && presenceBreak.getScheduleDatetime().getMinutes() == 0) {
                                        preBreakIn = presenceBreak.getPresenceDatetime().getTime(); //jika schedulenya 00:00 
                                    } else {
                                        preBreakIn = presenceBreak.getScheduleDatetime().getTime(); //yang di pakai mengurangi adalah schedule PI
                                    }

                                    breakDuration = breakDuration + (preBreakIn - preBreakOut);


                                    ispreBreakOutsdhdiambil = true;
                                    preBreakOut = 0L;

                                    //breakDuration = breakDuration + presenceBreak.getPresenceDatetime().getTime()-  preBOut.getPresenceDatetime().getTime(); 
                                    // preBOut=null;
                                }
                                // diffBi = diffBi+ (presenceBreak.getScheduleDatetime().getTime() - presenceBreak.getPresenceDatetime().getTime());

                            } else if (presenceBreak.getStatus() == Presence.STATUS_OUT_ON_DUTY) {
                                dtBreakOut = null;//update by satrya 2014-01-27 presenceBreak.getPresenceDatetime();
                                ispreBreakOutsdhdiambil = false;
                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;
                            } else if (presenceBreak.getStatus() == Presence.STATUS_CALL_BACK) {
                                dtBreakIn = null;//update by satrya 2014-01-27 presenceBreak.getPresenceDatetime();
                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;
                                ispreBreakOutsdhdiambil = true;

                            }

                            if (ispreBreakOutsdhdiambil) {
                                preBreakOutX = 0L;
                                preBreakInX = 0L;
                                dtBreakOut = null;
                                dtBreakIn = null;
                            }
                        }//end else if
                    }//end for break In                          
                    //update by satrya 2012-10-18
                    //jika di loop tersebut tidak cocok maka di kurangi schedulenya
                    if (breakDuration == 0 && oidSch1St != 0 && breakTimeDuration.get("" + oidSch1St) != null) {  //&& sPresenceDateTime.equals(pSelectedDate)){
                        try {
                            breakDuration = ((Long) breakTimeDuration.get("" + oidSch1St)).longValue(); //scheduleSymbol.getBreakIn().getTime()  - scheduleSymbol.getBreakOut().getTime(); 
                        } catch (Exception ex) {
                            System.out.println("Exception scheduleSymbol" + ex.toString());
                            //System.out.println("date"+presenceReportDaily.getSelectedDate()+ presenceReportDaily.getEmpFullName());
                        }
                    }
                } //jika employee tidak ada yang keluar maka akan di potong jam istirahat default
                else {
                    if (breakDuration == 0 && oidSch1St != 0 && breakTimeDuration.get("" + oidSch1St) != null) {  //&& sPresenceDateTime.equals(pSelectedDate)){
                        try {
                            breakDuration = ((Long) breakTimeDuration.get("" + oidSch1St)).longValue(); //scheduleSymbol.getBreakIn().getTime()  - scheduleSymbol.getBreakOut().getTime(); 
                        } catch (Exception ex) {
                            System.out.println("Exception scheduleSymbol" + ex.toString());
                            //System.out.println("date"+presenceReportDaily.getSelectedDate()+ presenceReportDaily.getEmpFullName());
                        }
                    }
                }
                //update by satrya 2014-01-24
                long breakOvertime = 0;
                if (vOvertimeDetail != null && vOvertimeDetail.size() > 0) {
                    for (int idxOt = 0; idxOt < vOvertimeDetail.size(); idxOt++) {
                        OvertimeDetail ovdDetail = (OvertimeDetail) vOvertimeDetail.get(idxOt);
                        if (ovdDetail.getEmployeeId() == empId && ovdDetail.getRestTimeinHr() != 0 && dtIdx != null && ovdDetail.getDateFrom() != null
                                && DateCalc.dayDifference(ovdDetail.getDateFrom(), dtIdx) == 0) {
                            breakOvertime = breakOvertime + (long) (ovdDetail.getRestTimeinHr() * 60 * 60 * 1000);
                            vOvertimeDetail.remove(idxOt);
                            idxOt = idxOt - 1;
                        }

                    }
                }
                if (actualIn != null && actualOut != null && dtIdx != null) {
                    //update by satrya 2014-02-11
                    //jika melebihi dari 8 jam kerja maka akan di setting 8 jam kerja
                    float total = ((actualOut.getTime() - (breakDuration + breakOvertime)) - actualIn.getTime()) / (leaveConfig.getHourOneWorkday() / leaveConfig.getHourOneWorkday() * 60f * 60f * 1000f);
                    if (total > leaveConfig.getHourOneWorkday()) {
                        totalWOrkDur = totalWOrkDur + (long) (leaveConfig.getHourOneWorkday() * 60L * 60L * 1000L);
                    } else {
                        totalWOrkDur = totalWOrkDur + (actualOut.getTime() - (breakDuration + breakOvertime)) - actualIn.getTime();
                    }
                    //totalWOrkDur = totalWOrkDur + (actualOut.getTime()-(breakDuration + breakOvertime))-actualIn.getTime();
                } else {
                    //jika actual In dan Out tidak ada maka dianggap tidak dihitung
                    //jika dia harinya OFF dan ada tgs kantor
        /*if(scheduleSymbol!=null && scheduleSymbol.getOID()==oidScheduleOff){
                     totalWOrkDur = totalWOrkDur + Math.abs(breakPresence);
                     }else{*/
                    totalWOrkDur = totalWOrkDur + 0;
                    //}
                }
                tmpDate = new Date(tmpDate.getTime() + 24 * 60 * 60 * 1000);
                kk = kk + 1;
                // empIdxx=empId;
            }
        } catch (Exception exc) {
            System.out.println("Exception exc timeWorkDuration" + exc + empIdxx);
            totalWOrkDur = 0;
        }
        //System.out.println("No emp: " +empIdxx);
        //String workDuration = Formater.formatWorkDayHoursMinutes((float) totalWOrkDur / (leaveConfig.getHourOneWorkday() * 60f * 60f * 1000f), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
        //String workDuration= Formater.formatWorkDayHoursMinutes((float)totalWOrkDur/(8f*60f*60f*1000f),leaveConfig.getHourOneWorkday(),leaveConfig.getFormatLeave());
        Vector hasil = new Vector();
        hasil.add((float) totalWOrkDur / (leaveConfig.getHourOneWorkday() * 60f * 60f * 1000f));
        //hasil.add(workDuration);
        return hasil;
    }

    public String timeWorkDurationHours(Hashtable hasSchedule, Hashtable hashTblSchedule, I_Leave leaveConfig, long oidScheduleOff, Date tmpEndDate, Vector listPresencePersonalInOut, Date tmpDate, Hashtable hashSymbol, EmpScheduleReport empScheduleReport, SummaryEmployeeAttendance summaryEmployeeAttendance, Hashtable breakTimeDuration, int propCheckLeaveExist, Hashtable hasDfltSchedule, Vector vOvertimeDetail) {
        long empIdxx = 0;//gunanya hanya untuk testy
        // String workDuration;
        long totalWOrkDur = 0;
        //hanya untuk test
        long testWorkHour = 0;
        //Hashtable hashDtCutiAndOT=new Hashtable();
        try {
            ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
            // Hashtable hashDtCuti=new Hashtable();
            int kk = 0;
            while (tmpDate.before(tmpEndDate) || tmpDate.equals(tmpEndDate)) {
                //Date dtSchEmpScheduleBIn = null;
                //Date dtSchEmpScheduleBOut = null;
                //int kk = tmpDate.getDate()-1;
                long breakPresence = 0;
                long oidSch2st = 0;///belum di pakai
                Long oidSch1St = empScheduleReport.getEmpSchedules1st(kk);
                Date actualIn = empScheduleReport.getEmpIn1st(kk);
                Date actualOut = empScheduleReport.getEmpOut1st(kk);
                Long empId = empScheduleReport.getEmployeeId();
                Date dtIdx = empScheduleReport.getDtIDate(kk);//mengetahui idx date
                empIdxx = empId;//hanya untuk test
                //Vector vectFirstSchedule = PstScheduleSymbol.getScheduleData(oidSch1St, tmpDate);
                //Vector vectFirstSchedule = PstScheduleSymbol.getScheduleData(oidSch1St, tmpDate);
                if (scheduleSymbol != null && scheduleSymbol.getOID() != oidSch1St) {
                    scheduleSymbol = (ScheduleSymbol) hasSchedule.get("" + oidSch1St);
                    if (scheduleSymbol == null) {

                        if (oidSch1St != 0) {
                            try {
                                scheduleSymbol = (ScheduleSymbol) hashTblSchedule.get(oidSch1St);//PstScheduleSymbol.fetchExc(oidSch1St);
                            } catch (Exception exc) {
                            }
                            hasSchedule.put("" + oidSch1St, scheduleSymbol);
                        }
                    }
                } else {
                    if (scheduleSymbol == null) {

                        if (oidSch1St != 0) {
                            try {
                                scheduleSymbol = (ScheduleSymbol) hashTblSchedule.get(oidSch1St);//scheduleSymbol  = PstScheduleSymbol.fetchExc(oidSch1St);
                            } catch (Exception exc) {
                            }
                            hasSchedule.put("" + oidSch1St, scheduleSymbol);
                        }
                    }
                }




                long preBreakOut = 0;
                long preBreakIn = 0;
                long breakDuration = 0L;
                if (scheduleSymbol != null && scheduleSymbol.getBreakOut() != null && scheduleSymbol.getBreakIn() != null && dtIdx != null && listPresencePersonalInOut != null && listPresencePersonalInOut.size() > 0) {
                    Date dtSchDateTime = null;
                    Date dtpresenceDateTime = null;
                    Date dtSchEmpScheduleBIn = (Date) dtIdx.clone();
                    Date dtSchEmpScheduleBOut = (Date) dtIdx.clone();
                    long preBreakOutX = 0;
                    long preBreakInX = 0;
                    Date dtBreakOut = null;
                    Date dtBreakIn = null;
                    boolean ispreBreakOutsdhdiambil = false;
                    Presence presenceBreak = new Presence();
                    for (int bIdx = 0; bIdx < listPresencePersonalInOut.size(); bIdx++) {
                        presenceBreak = (Presence) listPresencePersonalInOut.get(bIdx);//yang di cari harus ada leavenya 
                        //update by satrya 2012-10-17
                        if (dtSchEmpScheduleBOut != null) {
                            dtSchEmpScheduleBOut.setHours(scheduleSymbol.getBreakOut().getHours());
                            dtSchEmpScheduleBOut.setMinutes(scheduleSymbol.getBreakOut().getMinutes());
                            dtSchEmpScheduleBOut.setSeconds(0);
                        }
                        if (dtSchEmpScheduleBOut != null) {
                            dtSchEmpScheduleBIn.setHours(scheduleSymbol.getBreakIn().getHours());
                            dtSchEmpScheduleBIn.setMinutes(scheduleSymbol.getBreakIn().getMinutes());
                            dtSchEmpScheduleBIn.setSeconds(0);
                        }
                        if (presenceBreak.getScheduleDatetime() != null) {
                            dtSchDateTime = (Date) presenceBreak.getScheduleDatetime().clone();
                            dtSchDateTime.setHours(dtSchDateTime.getHours());
                            dtSchDateTime.setMinutes(dtSchDateTime.getMinutes());
                            dtSchDateTime.setSeconds(0);
                        }
                        if (presenceBreak.getPresenceDatetime() != null) {
                            //update by satrya 2012-10-17
                            dtpresenceDateTime = (Date) presenceBreak.getPresenceDatetime().clone();
                            dtpresenceDateTime.setHours(dtpresenceDateTime.getHours());
                            dtpresenceDateTime.setMinutes(dtpresenceDateTime.getMinutes());
                            dtpresenceDateTime.setSeconds(0);
                        }
                        if (presenceBreak.getEmployeeId() == empId
                                && (DateCalc.dayDifference(presenceBreak.getPresenceDatetime(), dtIdx) == 0)
                                && presenceBreak.getScheduleDatetime() == null) {
                            if (presenceBreak.getStatus() == Presence.STATUS_OUT_ON_DUTY) {
                                //bOut =bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+"<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                                // dBout = bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");
                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;

                            } else if (presenceBreak.getStatus() == Presence.STATUS_CALL_BACK) {
                                //bIn =bIn+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+ "<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                                // dBin = dBin+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm"); 
                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;

                            }

                        } else if (dtpresenceDateTime != null/*presenceBreak.getScheduleDatetime() !=null*/
                                && presenceBreak.getEmployeeId() == empId
                                //update by satrya 2014-01-27 &&(DateCalc.dayDifference(presenceBreak.getScheduleDatetime(),dtIdx)==0 )){
                                && (DateCalc.dayDifference(dtpresenceDateTime, dtIdx) == 0)) {
                            //kenapa di buat presenceBreak.getScheduleDatetime()!=null ini berpengaruh pada DateCalc.dayDifference(presenceBreak.getScheduleDatetime() xxxx yg menyebabkan exception
                            if (presenceBreak.getStatus() == Presence.STATUS_OUT_PERSONAL) {
                                //update by satrya 2012-09-27
                                //if((presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())){
                                //update by satrya 2013-07-28

                                //jika sewaktu presence Out melewati schedule BI maka setlah presencenya
                                //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                preBreakOutX = dtpresenceDateTime == null ? 0 : dtpresenceDateTime.getTime();///yang di pakai mengurangi itu adalah presence PO  
                                dtBreakOut = dtpresenceDateTime;
                                if (dtSchEmpScheduleBIn != null && presenceBreak.getPresenceDatetime().getTime() > dtSchEmpScheduleBIn.getTime()) {
                                    preBreakOut = presenceBreak.getPresenceDatetime().getTime();
                                } else if ((presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())) { ///jika karyawan mendahului istirahat
                                    preBreakOut = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PO 

                                } else if (presenceBreak.getScheduleDatetime().getHours() == 0 && presenceBreak.getScheduleDatetime().getMinutes() == 0) {
                                    preBreakOut = presenceBreak.getPresenceDatetime().getTime();//jika schedulenya 00:00
                                } else {
                                    preBreakOut = presenceBreak.getScheduleDatetime().getTime(); //yang di pakai mengurangi adalah schedule PO
                                }

                                ispreBreakOutsdhdiambil = false;
                            } else if (presenceBreak.getStatus() == Presence.STATUS_IN_PERSONAL) {
                                //istirahat terlamabat 
                                preBreakInX = presenceBreak.getPresenceDatetime() == null ? 0 : presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                dtBreakIn = presenceBreak.getPresenceDatetime();
                                if (preBreakOut != 0L) {
                                    //update by satrya 2012-09-27
                                    //if(presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()){
                                    //update by satrya 2013-07-28\
                                    //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                    if (dtSchEmpScheduleBIn != null && dtBreakOut != null && dtBreakIn != null
                                            && dtBreakOut.getTime() > dtSchEmpScheduleBIn.getTime() && dtBreakIn.getTime() > dtSchEmpScheduleBIn.getTime()) {
                                        //karena sudah pasti melewatijam istirahatnya
                                        long tmpBreakDuration = ((Long) breakTimeDuration.get("" + oidSch1St)).longValue();
                                        preBreakIn = presenceBreak.getPresenceDatetime().getTime() + tmpBreakDuration;
                                    } else if (presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()) { ///jika karyawan melewati jam istirahat
                                        preBreakIn = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                    } else if (presenceBreak.getScheduleDatetime().getHours() == 0 && presenceBreak.getScheduleDatetime().getMinutes() == 0) {
                                        preBreakIn = presenceBreak.getPresenceDatetime().getTime(); //jika schedulenya 00:00 
                                    } else {
                                        preBreakIn = presenceBreak.getScheduleDatetime().getTime(); //yang di pakai mengurangi adalah schedule PI
                                    }

                                    breakDuration = breakDuration + (preBreakIn - preBreakOut);


                                    ispreBreakOutsdhdiambil = true;
                                    preBreakOut = 0L;

                                    //breakDuration = breakDuration + presenceBreak.getPresenceDatetime().getTime()-  preBOut.getPresenceDatetime().getTime(); 
                                    // preBOut=null;
                                }
                                // diffBi = diffBi+ (presenceBreak.getScheduleDatetime().getTime() - presenceBreak.getPresenceDatetime().getTime());

                            } else if (presenceBreak.getStatus() == Presence.STATUS_OUT_ON_DUTY) {
                                //bOut =bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+"<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                                // dBout = dBout+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm"); 
                            } else if (presenceBreak.getStatus() == Presence.STATUS_CALL_BACK) {
                                //bIn =bIn+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+ "<br>"+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+"<br><br>";                                  
                                //dBin = dBin+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");  
                                listPresencePersonalInOut.remove(bIdx);
                                bIdx = bIdx - 1;

                            }

                            if (ispreBreakOutsdhdiambil) {
                                preBreakOutX = 0L;
                                preBreakInX = 0L;
                            }
                            // }

                        }//end else if

                    }//end for break In
                    if (preBreakOutX == 0 || preBreakInX == 0) {
                        //jika hanya satu saja yg muncul atau ada misalnya hanya break IN saja atau break Out saja
                        //update by satrya 2013-06-17
                        if (preBreakOutX != 0) {
                            if (preBreakOutX < dtSchEmpScheduleBOut.getTime() || preBreakOutX > dtSchEmpScheduleBIn.getTime()) {
                                //bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br></font>";      
                            } else {
                                // bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+"<br>"+Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br>";
                            }
                        }
                        if (preBreakInX != 0) {
                            if (preBreakInX > dtSchEmpScheduleBIn.getTime()) {
                                //bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br></font>";
                            } else {
                                //bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+"<br>"+Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br>";
                            }
                        }
                    }
                    //update by satrya 2012-10-18
                    //jika di loop tersebut tidak cocok maka di kurangi schedulenya
                    if (breakDuration == 0 && oidSch1St != 0 && breakTimeDuration.get("" + oidSch1St) != null) {  //&& sPresenceDateTime.equals(pSelectedDate)){
                        try {
                            breakDuration = ((Long) breakTimeDuration.get("" + oidSch1St)).longValue(); //scheduleSymbol.getBreakIn().getTime()  - scheduleSymbol.getBreakOut().getTime(); 
                        } catch (Exception ex) {
                            System.out.println("Exception scheduleSymbol" + ex.toString());
                            //System.out.println("date"+presenceReportDaily.getSelectedDate()+ presenceReportDaily.getEmpFullName());
                        }
                    }
                } //jika employee tidak ada yang keluar maka akan di potong jam istirahat default
                else {
                    if (breakDuration == 0 && oidSch1St != 0 && breakTimeDuration.get("" + oidSch1St) != null) {  //&& sPresenceDateTime.equals(pSelectedDate)){
                        try {
                            breakDuration = ((Long) breakTimeDuration.get("" + oidSch1St)).longValue(); //scheduleSymbol.getBreakIn().getTime()  - scheduleSymbol.getBreakOut().getTime(); 
                        } catch (Exception ex) {
                            System.out.println("Exception scheduleSymbol" + ex.toString());
                            //System.out.println("date"+presenceReportDaily.getSelectedDate()+ presenceReportDaily.getEmpFullName());
                        }
                    }
                }
                //update by satrya 2014-01-24
                long breakOvertime = 0;
                if (vOvertimeDetail != null && vOvertimeDetail.size() > 0) {
                    for (int idxOt = 0; idxOt < vOvertimeDetail.size(); idxOt++) {
                        OvertimeDetail ovdDetail = (OvertimeDetail) vOvertimeDetail.get(idxOt);
                        if (ovdDetail.getEmployeeId() == empId && ovdDetail.getRestTimeinHr() != 0 && dtIdx != null && ovdDetail.getDateFrom() != null
                                && DateCalc.dayDifference(ovdDetail.getDateFrom(), dtIdx) == 0) {
                            breakOvertime = breakOvertime + (long) (ovdDetail.getRestTimeinHr() * 60 * 60 * 1000);
                        }

                    }
                }
                if (actualIn != null && actualOut != null && dtIdx != null) {
                    float total = ((actualOut.getTime() - (breakDuration + breakOvertime)) - actualIn.getTime()) / (leaveConfig.getHourOneWorkday() / leaveConfig.getHourOneWorkday() * 60f * 60f * 1000f);
                    if (total > leaveConfig.getHourOneWorkday()) {
                        totalWOrkDur = totalWOrkDur + (long) (leaveConfig.getHourOneWorkday() * 60L * 60L * 1000L);
                    } else {
                        totalWOrkDur = totalWOrkDur + (actualOut.getTime() - (breakDuration + breakOvertime)) - actualIn.getTime();
                    }
                    //totalWOrkDur = totalWOrkDur + (actualOut.getTime()-(breakDuration + breakOvertime))-actualIn.getTime();
                } else {
                    //jika actual In dan Out tidak ada maka dianggap tidak dihitung
                    //jika dia harinya OFF dan ada tgs kantor
        /*if(scheduleSymbol!=null && scheduleSymbol.getOID()==oidScheduleOff){
                     totalWOrkDur = totalWOrkDur + Math.abs(breakPresence);
                     }else{*/
                    totalWOrkDur = totalWOrkDur + 0;
                    //}
                }

                tmpDate = new Date(tmpDate.getTime() + 24 * 60 * 60 * 1000);
                kk = kk + 1;
                // empIdxx=empId;
            }
        } catch (Exception exc) {
            System.out.println("Exception exc timeWorkDuration" + exc + empIdxx);
            totalWOrkDur = 0;
        }
        //System.out.println("No emp: " +empIdxx);
        String workDuration = Formater.formatWorkHoursMinutes(totalWOrkDur);
        return workDuration;
    }
}
