/*
 * TransferListXLS.java
 *
 * Satrya Ramayu
 */
package com.dimata.harisma.report.payroll;

import com.dimata.gui.excel.ControlListExcel;
import com.dimata.harisma.entity.attendance.I_Atendance;
import com.dimata.harisma.entity.attendance.LeaveApplicationSummary;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.I_Leave;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstReason;
import com.dimata.harisma.entity.masterdata.Reason;
import com.dimata.harisma.entity.payroll.ParamPayInput;
import com.dimata.harisma.entity.payroll.PayGeneral;
import com.dimata.harisma.entity.payroll.PayInput;
import com.dimata.harisma.entity.payroll.PayPeriod;
import com.dimata.harisma.entity.payroll.PaySlip;
import com.dimata.harisma.entity.payroll.PstPayGeneral;
import com.dimata.harisma.entity.payroll.PstPayInput;
import com.dimata.harisma.entity.payroll.PstPayPeriod;
import com.dimata.harisma.session.leave.SessLeaveApp;
import com.dimata.harisma.session.payroll.SessPayInput;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;

/**
 *
 * @author Yunny
 */
public class PayInputXls extends HttpServlet {

    /**
     * Initializes the servlet.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    /**
     * Destroys the servlet.
     */
    public void destroy() {
    }

    /**
     * Creates a new instance of TransferListXLS
     */
    public PayInputXls() {
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

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        System.out.println("---===| Excel Report |===---");

        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("List Pay Input");

        //Style
        HSSFCellStyle styleTitle = wb.createCellStyle();
        styleTitle.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleTitle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleTitle.setFont(createFont(wb, 12, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

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

        /* get data from request Parameter*/
        int iCommand = FRMQueryString.requestCommand(request);
        int noteCommand = FRMQueryString.requestInt(request, "noteCommand");
        String noteToAll = FRMQueryString.requestString(request, "noteToAll");
        String allLeftSeparator = FRMQueryString.requestString(request, "allLeftSeparator");
        String allLeftSeparatorNew = FRMQueryString.requestString(request, "allLeftSeparatorNew");
        int statusPayInput = FRMQueryString.requestInt(request, "status_pay_input");
        Date dtAdjusment = FRMQueryString.requestDate(request, "date_adjusment");
        if (allLeftSeparator == null || allLeftSeparator.length() == 0) {
            allLeftSeparator = "FYI:";
        }
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

        String levelCode = FRMQueryString.requestString(request, "level");
        //String levelCodeX = FRMQueryString.requestString(request,"levelCode");
        long companyId = FRMQueryString.requestLong(request, "comapanyId");
        long oidDivision = FRMQueryString.requestLong(request, "oidDivision");
        long oidDepartment=FRMQueryString.requestLong(request,"oidDepartment");
        long oidSection=FRMQueryString.requestLong(request,"oidSection");
        long departmentName = FRMQueryString.requestLong(request, "department");
        long sectionName = FRMQueryString.requestLong(request, "section");
        String searchNrFrom = FRMQueryString.requestString(request, "searchNrFrom");
        String searchNrTo = FRMQueryString.requestString(request, "searchNrTo");
        String searchName = FRMQueryString.requestString(request, "searchName");
        String searchNr = FRMQueryString.requestString(request, "searchNr");
        long periodId = FRMQueryString.requestLong(request, "periodId");
        int dataStatus = FRMQueryString.requestInt(request, "dataStatus");

        int isChekedRadioButtonSearchNr = FRMQueryString.requestInt(request, "radioButtonSerachNr");

           
        HttpSession session = request.getSession();
//         if (periodId == 0 && session.getValue("periodIdses") != null){
//            try {
//              periodId = (Long)session.getValue("periodIdses");
//            } catch (Exception e){}
//        }
//         if (companyId == 0 && session.getValue("companyIdses") != null){
//            try {
//              companyId = (Long)session.getValue("companyIdses");
//            } catch (Exception e){}
//        }

        int n1 = 0;
        int n2 = 0;
               if (n1 == 0 && session.getValue("n1") != null){
                    try {
                      n1 = (Integer)session.getValue("n1");
                      n1 = n1 - 1;
                    } catch (Exception e){}
                }
               if (n2 == 0 && session.getValue("n2") != null){
                    try {
                      n2 = (Integer)session.getValue("n2");
                    } catch (Exception e){}
                }
        Vector listPayInput = new Vector(1, 1);
        Vector listOtIdx = new Vector(1, 1);
        Hashtable listPayInputTbl = new Hashtable();
        Hashtable existPayInputId = new Hashtable();
        Vector listPos = PstPosition.list(0, 0, PstPosition.fieldNames[PstPosition.FLD_FLAG_POSITION_SHOW_IN_PAYROLL_INPUT] + "=" + PstPosition.YES_SHOW_PAY_INPUT, PstPosition.fieldNames[PstPosition.FLD_POSITION] + " ASC ");
        Vector listReason = PstReason.list(0, 0, PstReason.fieldNames[PstReason.FLD_FLAG_IN_PAY_INPUT] + "=" + PstReason.SHOW_REASON_IN_PAY_INPUT_YES, PstReason.fieldNames[PstReason.FLD_REASON] + " ASC ");
        int showOvertime = 0;
        try {
            showOvertime = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY"));
        } catch (Exception ex) {

            System.out.println("<blink>ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY NOT TO BE SET</blink>");
            showOvertime = 0;
        }
        int iPropInsentifLevel = 0;//hanya cuti full day jika fullDayLeave = 0
        try {
            iPropInsentifLevel = Integer.parseInt(PstSystemProperty.getValueByName("PAYROLL_INSENTIF_MAX_LEVEL"));
        } catch (Exception ex) {

            //System.out.println("Execption PAYROLL_INSENTIF_MAX_LEVEL: " + ex);
            System.out.println("<blink>PAYROLL_INSENTIF_MAX_LEVEL NOT TO BE SET</blink>");
        }
        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }

        String namaCompany = "";
        if (companyId != 0) {
            try {
                PayGeneral payGeneral = PstPayGeneral.fetchExc(companyId);
                namaCompany = payGeneral.getCompanyName();
            } catch (Exception exc) {
            }
        }
        PayPeriod payPeriod = new PayPeriod();
        if (periodId != 0) {
            try {
                payPeriod = PstPayPeriod.fetchExc(periodId);
                //   period= PstPeriod.fetchExc(periodId);
            } catch (Exception exc) {
                System.out.println("Period id =" + periodId + " not in system or could not fetched from database ! <br> " + exc);
            }
        }
        String getListEmployeeId = PstEmployee.getSEmployeeIdJoinSalary(0, 0, companyId, oidDivision, departmentName, sectionName, periodId, levelCode, searchNrFrom, searchNrTo, searchName, dataStatus, isChekedRadioButtonSearchNr, searchNr, "EMP." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
        ParamPayInput paramPayInput = new ParamPayInput();
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

        paramPayInput.setiPropInsentifLevel(iPropInsentifLevel); 
        if (periodId == 0 ){
            if (companyId == 0 && session.getValue("companyIdses") != null){
            try {
              paramPayInput = (ParamPayInput)session.getValue("paramPayInput");
              
                iCommand = Command.LIST;
                noteCommand = paramPayInput.getNoteCommand();
                noteToAll = paramPayInput.getNoteToAll();
                allLeftSeparator = paramPayInput.getAllLeftSeparator();
                allLeftSeparatorNew = paramPayInput.getAllLeftSeparatorNew();
                statusPayInput = paramPayInput.getStatusPayInput();
                dtAdjusment = paramPayInput.getDtAdjusment();
                if (allLeftSeparator == null || allLeftSeparator.length() == 0) {
                    allLeftSeparator = "FYI:";
                }
                if (allLeftSeparatorNew == null || allLeftSeparatorNew.length() == 0) {
                    allLeftSeparatorNew = "FYI:";
                }
                levelCode = paramPayInput.getLevelCode();
                companyId = paramPayInput.getCompanyId();
                oidDivision = paramPayInput.getOidDivision();
                //oidDepartment= paramPayInput.get;
                //oidSection= paramPayInput.getNoteCommand();
                departmentName = paramPayInput.getDepartmentName();
                sectionName = paramPayInput.getNoteCommand();
                searchNrFrom = paramPayInput.getSearchNrFrom();
                searchNrTo = paramPayInput.getSearchNrTo();
                searchName = paramPayInput.getSearchName();
                searchNr = paramPayInput.getSearchNr();
                periodId = paramPayInput.getPeriodId();
                dataStatus = paramPayInput.getDataStatus();
                isChekedRadioButtonSearchNr = paramPayInput.getIsChekedRadioButtonSearchNr();
              
              
            } catch (Exception e){}
            }
        }
        if (periodId != 0) {
            try {
                payPeriod = PstPayPeriod.fetchExc(periodId);
                //   period= PstPeriod.fetchExc(periodId);
            } catch (Exception exc) {
                System.out.println("Period id =" + periodId + " not in system or could not fetched from database ! <br> " + exc);
            }
        }
        //listPayInput = SessPayInput.listPayInput(departmentName,companyId,oidDivision,(levelCode),sectionName,searchNrFrom,searchNrTo,searchName,periodId,dataStatus,isChekedRadioButtonSearchNr,searchNr); 
        if (paramPayInput != null && paramPayInput.getsEmployeeId() != null && paramPayInput.getsEmployeeId().length() > 0 && paramPayInput.getPeriodId() != 0) {
            listPayInput = SessPayInput.listPayInput(departmentName, companyId, oidDivision, (levelCode), sectionName, searchNrFrom, searchNrTo, searchName, periodId, dataStatus, isChekedRadioButtonSearchNr, searchNr);

            String whereClause = PstPayInput.fieldNames[PstPayInput.FLD_EMPLOYEE_ID] + " IN(" + paramPayInput.getsEmployeeId() + ") AND " + PstPayInput.fieldNames[PstPayInput.FLD_PERIODE_ID] + "=" + paramPayInput.getPeriodId();
            listPayInputTbl = PstPayInput.hashListPayInput(0, 0, whereClause, "");
            existPayInputId = PstPayInput.hashChekcExistingPayInputId(0, 0, whereClause, "");
            listPos = PstPosition.list(0, 0, PstPosition.fieldNames[PstPosition.FLD_FLAG_POSITION_SHOW_IN_PAYROLL_INPUT] + "=" + PstPosition.YES_SHOW_PAY_INPUT, PstPosition.fieldNames[PstPosition.FLD_POSITION] + " ASC ");
            listReason = PstReason.list(0, 0, PstReason.fieldNames[PstReason.FLD_FLAG_IN_PAY_INPUT] + "=" + PstReason.SHOW_REASON_IN_PAY_INPUT_YES, PstReason.fieldNames[PstReason.FLD_REASON] + " ASC ");

        }
        Hashtable listTakenLeave = SessLeaveApp.checkLeaveTaken(paramPayInput.getsEmployeeId(), paramPayInput.getDtStart(), paramPayInput.getDtEnd());

        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        ControlListExcel ctrControlListExcel = new ControlListExcel();
        String printHeader = namaCompany != null && namaCompany.length() > 0 ? namaCompany : " All Company "/* update by satrya 2014-01-20 PstSystemProperty.getValueByName("PRINT_HEADER")*/;
        String[] tableTitle = {
            printHeader,
            "Export Data Pay Input",
            "Period : " + (payPeriod != null && payPeriod.getPeriod() != null ? payPeriod.getPeriod() : "-")
        };
        /**
         * @DESC : TITTLE
         */
        int countRow = 0;
        for (int k = 0; k < tableTitle.length; k++) {

            ctrControlListExcel.addHeader(tableTitle[k], countRow, countRow, countRow, 0, 4, styleTitle);
            countRow = countRow + 1;
            ctrControlListExcel.drawHeaderExcel(sheet);
            ctrControlListExcel.resetHeader();
        }
        

        ctrControlListExcel.addHeader("NO", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("Employee Nr.", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("Nama", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("Position", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("Presence On Time (Sts.Ok) Idx", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("Presence On Time (Sts.Ok) Time", countRow, 0, 0, 0, 0, styleHeader);

        ctrControlListExcel.addHeader("Late Idx", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("Late Time", countRow, 0, 0, 0, 0, styleHeader);

        ctrControlListExcel.addHeader("Early Home Idx", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("Early Home Time", countRow, 0, 0, 0, 0, styleHeader);

        ctrControlListExcel.addHeader("Late Early Idx", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("Late Early Time", countRow, 0, 0, 0, 0, styleHeader);

        ctrControlListExcel.addHeader("Only In Idx", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("Only Out Idx", countRow, 0, 0, 0, 0, styleHeader);
        if (listReason != null && listReason.size() > 0) {
            for (int idxRes = 0; idxRes < listReason.size(); idxRes++) {
                Reason reason = (Reason) listReason.get(idxRes);
                ctrControlListExcel.addHeader(reason.getReason() + " Idx", countRow, 0, 0, 0, 0, styleHeader);
                ctrControlListExcel.addHeader(reason.getReason() + " Time", countRow, 0, 0, 0, 0, styleHeader);
            }
        }

        ctrControlListExcel.addHeader("Absence Idx", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("Absence Time", countRow, 0, 0, 0, 0, styleHeader);

        ctrControlListExcel.addHeader("Presence Ok (%)", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("Days OFF Sch", countRow, 0, 0, 0, 0, styleHeader);

        ctrControlListExcel.addHeader("AL Idx", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("AL Time", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("LL Idx", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("LL Time", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("DP Idx", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("DP Time", countRow, 0, 0, 0, 0, styleHeader);

        ctrControlListExcel.addHeader("UnPaid Leave", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("Insentif", countRow, 0, 0, 0, 0, styleHeader);



        if (showOvertime == 0) {

            ctrControlListExcel.addHeader("OT. Idx Paid Salary", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("OT. Idx Paid Day Off Payment", countRow, 0, 0, 0, 0, styleHeader);

            ctrControlListExcel.addHeader("OT. Allowance Money", countRow, 0, 0, 0, 0, styleHeader);
            ctrControlListExcel.addHeader("OT. Allowance Food", countRow, 0, 0, 0, 0, styleHeader);

        }

        if (listPos != null && listPos.size() > 0) {
            for (int idxPos = 0; idxPos < listPos.size(); idxPos++) {
                Position position = (Position) listPos.get(idxPos);
                ctrControlListExcel.addHeader(position.getPosition(), countRow, 0, 0, 0, 0, styleHeader);
            }

        }

        ctrControlListExcel.addHeader("Private Note", countRow, 0, 0, 0, 0, styleHeader);
        ctrControlListExcel.addHeader("Emp.Work Days", countRow, 0, 0, 0, 0, styleHeader);

        ctrControlListExcel.addHeader("Note", countRow, 0, 0, 0, 0, styleHeader);

        countRow = countRow + 1;
        if (listPayInput != null && listPayInput.size() > 0) {
           ctrControlListExcel.drawHeaderExcel(sheet);
            ctrControlListExcel.resetHeader();
            ctrControlListExcel.resetData();
            
            int awal = 0;
            int akhir = listPayInput.size();
            
            if (n1 != 0){
            awal = n1;
            }
            if (n2 != 0){
            akhir = n2;
            }
            if (awal <= 0 ){
            awal =  0;   
            }
            if (akhir <= 0 ){
            akhir = listPayInput.size();   
            }
            if (awal >= listPayInput.size() ){
            awal =  listPayInput.size()-1;   
            }
            if (akhir >= listPayInput.size() ){
            akhir =  listPayInput.size();   
            }
            for (int i = awal; i < akhir; i++) {
                 
           
                PayInput payInput = new PayInput();
                Vector temp = (Vector) listPayInput.get(i);
                Employee employee = (Employee) temp.get(0);
                PaySlip paySlip = (PaySlip) temp.get(1);

                float alIdx = 0;
                float alTime = 0;

                float llIdx = 0;
                float llTime = 0;

                float dpIdx = 0;
                float dpTime = 0;

                int numberColom=0;
                
                
                if (listTakenLeave != null && listTakenLeave.size() > 0 && listTakenLeave.get(employee.getOID()) != null) {
                    LeaveApplicationSummary leaveApplicationSummary = (LeaveApplicationSummary) listTakenLeave.get(employee.getOID());
                    if (leaveApplicationSummary.getEmployeeId() != 0 && leaveApplicationSummary.getEmployeeId() == employee.getOID()) {
                        if (leaveApplicationSummary.getVsymbol() != null && leaveApplicationSummary.getVsymbol().size() > 0) {
                            for (int idxCT = 0; idxCT < leaveApplicationSummary.getVsymbol().size(); idxCT++) {
                                if (leaveApplicationSummary.getSymbol(idxCT).equalsIgnoreCase("AL")) {
                                    alTime = alTime + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                    alIdx = alIdx + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                    leaveApplicationSummary.getVsymbol().remove(idxCT);
                                    leaveApplicationSummary.getJumlahTaken().remove(idxCT);
                                    idxCT = idxCT - 1;
                                } else if (leaveApplicationSummary.getSymbol(idxCT).equalsIgnoreCase("DP")) {
                                    dpIdx = dpIdx + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                    dpTime = dpTime + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                    leaveApplicationSummary.getVsymbol().remove(idxCT);
                                    leaveApplicationSummary.getJumlahTaken().remove(idxCT);
                                    idxCT = idxCT - 1;
                                } else if (leaveApplicationSummary.getSymbol(idxCT).equalsIgnoreCase("LL")) {
                                    llIdx = llIdx + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                    llTime = llTime + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                    leaveApplicationSummary.getVsymbol().remove(idxCT);
                                    leaveApplicationSummary.getJumlahTaken().remove(idxCT);
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
                if (listPayInputTbl != null && listPayInputTbl.size() > 0) {
                    PstPayInput.resultToObject(payInput, listPayInputTbl, listReason, listPos);
                }
                payInput.setEmployeeNumber(employee.getEmployeeNum());
                payInput.setEmployeeName(employee.getFullName());
                payInput.setPositionName(paySlip.getPosition());
                payInput.setPositionId(employee.getPositionId());
                payInput.setPaySlipId(paySlip.getOID());

                payInput.setPresenceOntimeIdx((int) paySlip.getDayPresent());

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

                HSSFCellStyle styleValue1 = null;
                if (i % 2 == 0) {
                    styleValue1 = styleValue;
                } else {
                    styleValue1 = styleValueGenap;
                }

//                //untuk pembulatan koma
                HSSFCellStyle styleValuesFloat = null;
                if (i % 2 == 0) {
                    styleValuesFloat = styleValueFloatGanjil;
                } else {
                    styleValuesFloat = styleValueGenapFloat;

                }
                sheet.createFreezePane(4, 4);
                float hourOneWorkDay = leaveConfig==null?8f:leaveConfig.getHourOneWorkday();
                String format =  leaveConfig==null?"#,###.###":leaveConfig.getFormatLeave();
                String formatNumberFloat = "#.###";
                String formatNumberInt = "#.##";

                if(String.valueOf(countRow).equalsIgnoreCase("254")){
                    boolean t = true;
                }
                if(String.valueOf(numberColom).equalsIgnoreCase("254")){
                    boolean t = true;
                }
                ctrControlListExcel.addDataInteger((1 + i),countRow,numberColom++,  0, 0, 0, 0, styleValue1, null);//1
            
                ctrControlListExcel.addDataString(payInput.getEmployeeNumber(), countRow,numberColom++, 0, 0, 0, 0, styleValue1, null);//2
            
                ctrControlListExcel.addDataString(payInput.getEmployeeName(),countRow,numberColom++,  0, 0, 0, 0, styleValue1, null);//3
            
                ctrControlListExcel.addDataString(payInput.getPositionName(),countRow,numberColom++,  0, 0, 0, 0, styleValue1, null);//4
            
                ctrControlListExcel.addDataInteger((payInput.getPresenceOntimeIdx()+payInput.getPresenceOntimeIdxAdjust()),countRow,numberColom++,0,0,0,0,styleValue1 ,payInput.getPresenceOntimeIdx()+" + " +payInput.getPresenceOntimeIdxAdjust());//5
           
                ctrControlListExcel.addDataString((Formater.formatWorkDayHoursMinutes((float) payInput.getPresenceOntimeTime(), hourOneWorkDay, format) + " + " + Formater.formatWorkDayHoursMinutes((float) payInput.getPresenceOntimeTimeAdjust(), hourOneWorkDay, format)),countRow,numberColom++,0,0,0,0,styleValue1,null);  //6
            
                ctrControlListExcel.addDataInteger(payInput.getLateIdx()+payInput.getLateIdxAdjust(),countRow,numberColom++, 0, 0, 0, 0, styleValue1,(payInput.getLateIdx()+" + " + payInput.getLateIdxAdjust()));  //7
            
                ctrControlListExcel.addDataString(Formater.formatWorkDayHoursMinutes((float) payInput.getLateTime(), hourOneWorkDay, format) + " + " + Formater.formatWorkDayHoursMinutes((float) payInput.getLateTimeAdjust(), hourOneWorkDay, format),countRow,numberColom++,0,0,0,0,styleValue1,null);  //8
          
                ctrControlListExcel.addDataInteger(payInput.getEarlyHomeIdx()+payInput.getEarlyHomeIdxAdjust(), countRow,numberColom++, 0, 0, 0, 0, styleValue1, (payInput.getEarlyHomeIdx() + " + " + payInput.getEarlyHomeIdxAdjust()));  //9
            
                ctrControlListExcel.addDataString(Formater.formatWorkDayHoursMinutes((float) payInput.getEarlyHomeTime(), hourOneWorkDay, format) + " + " + Formater.formatWorkDayHoursMinutes((float) payInput.getEarlyHomeTimeAdjust(), hourOneWorkDay, format), countRow,numberColom++, 0, 0, 0, 0, styleValue1,null);  //10
            
                ctrControlListExcel.addDataInteger(payInput.getLateEarlyIdx()+payInput.getLateEarlyIdxAdjust(), countRow,numberColom++, 0, 0, 0, 0, styleValue1, (payInput.getLateEarlyIdx() + " + " + payInput.getLateEarlyIdxAdjust()));  //11
         
                ctrControlListExcel.addDataString(Formater.formatWorkDayHoursMinutes((float) payInput.getLateEarlyTime(), hourOneWorkDay, format) + " + " + Formater.formatWorkDayHoursMinutes((float) payInput.getLateEarlyTimeAdjust(), hourOneWorkDay, format), countRow,numberColom++, 0, 0, 0, 0, styleValue1,null );//12  

                ctrControlListExcel.addDataInteger(payInput.getOnlyInIdx()+payInput.getOnlyInIdxAdjust(), countRow,numberColom++, 0, 0, 0, 0, styleValue1, (payInput.getOnlyInIdx() + " + " + payInput.getOnlyInIdxAdjust()));  //13
         
                ctrControlListExcel.addDataInteger(payInput.getOnlyOutIdx()+payInput.getOnlyOutIdxAdjust(), countRow,numberColom++, 0, 0, 0, 0, styleValue1, (payInput.getOnlyOutIdx() + " + " + payInput.getOnlyOutIdxAdjust()));  //14
         
                if (listReason != null && listReason.size() > 0) { //jika ada yg ingin menggunakan mangkir
                    for (int idxRes = 0; idxRes < listReason.size(); idxRes++) {
                        Reason reason = (Reason) listReason.get(idxRes);
                        ctrControlListExcel.addDataInteger( (int)(payInput.getReasonIdx(reason.getNo(), payInput.getPeriodId(), payInput.getEmployeeId())+payInput.getReasonIdxAdjust(reason.getNo(), payInput.getPeriodId(), payInput.getEmployeeId())), countRow,numberColom++, 0, 0, 0, 0, styleValue1,(payInput.getReasonIdx(reason.getNo(), payInput.getPeriodId(), payInput.getEmployeeId()) + " + " + payInput.getReasonIdxAdjust(reason.getNo(), payInput.getPeriodId(), payInput.getEmployeeId())));  //13
                        ctrControlListExcel.addDataString(Formater.formatWorkDayHoursMinutes((float) payInput.getReasonTime(reason.getNo(), payInput.getPeriodId(), payInput.getEmployeeId()), hourOneWorkDay, format) + " + " + Formater.formatWorkDayHoursMinutes((float) payInput.getReasonTimeAdjust(reason.getNo(), payInput.getPeriodId(), payInput.getEmployeeId()), hourOneWorkDay, format),countRow,numberColom++, 0, 0, 0, 0, styleValue1,null);//14 
                    }
                }
                ctrControlListExcel.addDataInteger(payInput.getAbsenceIdx()+payInput.getAbsenceIdxAdjust(),countRow,numberColom++,0,0,0,0,styleValue1,(payInput.getAbsenceIdx() + " + " + payInput.getAbsenceIdxAdjust()));  //15
                ctrControlListExcel.addDataString(Formater.formatWorkDayHoursMinutes((float) payInput.getAbsenceTime(), hourOneWorkDay, format) + " + " + Formater.formatWorkDayHoursMinutes((float) payInput.getAbsenceTimeAdjust(), hourOneWorkDay, format), countRow,numberColom++, 0, 0, 0, 0, styleValue1,null);  //16
                
                
               //double prosentasePresenceOk=( (payInput.getPresenceOntimeIdx()+payInput.getPresenceOntimeIdxAdjust()) *100)/( (payInput.getPresenceOntimeIdx()+payInput.getPresenceOntimeIdxAdjust())+ (payInput.getLateIdx()+payInput.getLateIdxAdjust()) + (payInput.getEarlyHomeIdx()+payInput.getEarlyHomeIdxAdjust()) + (payInput.getLateEarlyIdx()+payInput.getLateEarlyIdxAdjust()) + (payInput.getLateEarlyIdx()+payInput.getLateEarlyIdxAdjust()));
                
                ctrControlListExcel.addDataDouble(payInput.getProsentaseOK(), countRow,numberColom++, 0, 0, 0, 0, styleValue1,null);  //17
                ctrControlListExcel.addDataDouble(payInput.getDayOffSchedule(), countRow,numberColom++, 0, 0, 0, 0, styleValue1,null);  //17


                ctrControlListExcel.addDataFloat(payInput.getAlIdx(), countRow,numberColom++, 0, 0, 0, 0, styleValuesFloat,null);  //18
                ctrControlListExcel.addDataString(Formater.formatWorkDayHoursMinutes((float) payInput.getAlTime(), hourOneWorkDay, format), countRow,numberColom++, 0, 0, 0, 0, styleValue1,null);  //19
           
                ctrControlListExcel.addDataFloat(payInput.getLlIdx(), countRow,numberColom++, 0, 0, 0, 0, styleValuesFloat,null);  //20
                ctrControlListExcel.addDataString(Formater.formatWorkDayHoursMinutes((float) payInput.getLlTime(), hourOneWorkDay, format), countRow,numberColom++, 0, 0, 0, 0, styleValue1,null);//21  
                
                ctrControlListExcel.addDataFloat(payInput.getDpIdx(), countRow,numberColom++, 0, 0, 0, 0, styleValuesFloat,null);  //22
            
                ctrControlListExcel.addDataString(Formater.formatWorkDayHoursMinutes((float) payInput.getDpTime(), hourOneWorkDay, format), countRow,numberColom++, 0, 0, 0, 0, styleValue1,null);  //23
                ctrControlListExcel.addDataString(Formater.formatWorkDayHoursMinutes((float) payInput.getUnPaidLeave(), hourOneWorkDay, format) + " + " + Formater.formatWorkDayHoursMinutes((float) payInput.getUnPaidLeaveAdjust(), hourOneWorkDay, format), countRow,numberColom++, 0, 0, 0, 0, styleValue1,null);  //26

                ctrControlListExcel.addDataDouble(payInput.getInsentif()+payInput.getInsentifAdjusment(), countRow,numberColom++, 0, 0, 0, 0, styleValue1,(payInput.getInsentif() + " + " + payInput.getInsentifAdjusment()));  //26
                //jika menggunakan overtime
                if (showOvertime == 0) {
                    ctrControlListExcel.addDataDouble(Formater.formatNumberDouble(payInput.getOtIdxPaidSalary(), formatNumberFloat) + Formater.formatNumberDouble(payInput.getOtIdxPaidSalaryAdjust(), formatNumberFloat),countRow,numberColom++, 0, 0, 0, 0, styleValue1,(Formater.formatNumberDouble(payInput.getOtIdxPaidSalary(), formatNumberFloat) + " + " + Formater.formatNumberDouble(payInput.getOtIdxPaidSalaryAdjust(), formatNumberFloat)));  //27
                    ctrControlListExcel.addDataDouble(Formater.formatNumberDouble(payInput.getOtIdxPaidDp(), formatNumberFloat),countRow,numberColom++, 0, 0, 0, 0, styleValue1,null);

                    ctrControlListExcel.addDataInteger(Formater.formatNumberInt((int)payInput.getOtAllowanceMoney(), formatNumberInt) + Formater.formatNumberInt((int)payInput.getOtAllowanceMoneyAdjust(), formatNumberInt),countRow,numberColom++, 0, 0, 0, 0, styleValue1,(Formater.formatNumberInt((int)payInput.getOtAllowanceMoney(), formatNumberInt) + " + " + Formater.formatNumberInt((int)payInput.getOtAllowanceMoneyAdjust(), formatNumberInt)));  //28
                    ctrControlListExcel.addDataInteger(Formater.formatNumberInt((int)payInput.getOtAllowanceFood(), formatNumberInt) + Formater.formatNumberInt((int)payInput.getOtAllowanceFoodAdjust(), formatNumberInt),countRow,numberColom++, 0, 0, 0, 0, styleValue1,(Formater.formatNumberInt((int)payInput.getOtAllowanceFood(), formatNumberInt) + " + " + Formater.formatNumberInt((int)payInput.getOtAllowanceFoodAdjust(), formatNumberInt)));  //29
                }
                if (listPos != null && listPos.size() > 0) {
                    for (int idxPos = 0; idxPos < listPos.size(); idxPos++) {
                        Position position = (Position) listPos.get(idxPos);
                        ctrControlListExcel.addDataDouble(Formater.formatNumberDouble(payInput.getPositionIdx(position.getOID(), payInput.getPeriodId(), payInput.getEmployeeId()), formatNumberFloat)+Formater.formatNumberDouble(payInput.getPositionAdjust(position.getOID(), payInput.getPeriodId(), payInput.getEmployeeId()), formatNumberFloat),countRow,numberColom++, 0, 0, 0, 0, styleValue1,(Formater.formatNumberDouble(payInput.getPositionIdx(position.getOID(), payInput.getPeriodId(), payInput.getEmployeeId()), formatNumberFloat) + " + " + Formater.formatNumberDouble(payInput.getPositionAdjust(position.getOID(), payInput.getPeriodId(), payInput.getEmployeeId()), formatNumberFloat)));
                    }
                }
                ctrControlListExcel.addDataString(payInput.getPrivateNote(),countRow,numberColom++, 0, 0, 0, 0, styleValue1,null);  //30

                ctrControlListExcel.addDataInteger(Formater.formatNumberInt( (int)payInput.getEmpWorkDays(), formatNumberInt),countRow,numberColom++, 0, 0, 0, 0, styleValue1,null); //31 
                ctrControlListExcel.addDataString(payInput.getNote(),countRow,numberColom++, 0, 0, 0, 0, styleValue1,null); //36
                

                ctrControlListExcel.drawDataExcel(sheet);
                countRow++;
            }
        }
        
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    }

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
}
