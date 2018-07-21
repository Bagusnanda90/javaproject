/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.overtimeReport;

import com.dimata.gui.excel.ControlListExcel;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.entity.overtime.Overtime;
import com.dimata.harisma.entity.overtime.OvertimeDetail;
import com.dimata.harisma.entity.overtime.OvertimeReportSummary;
import com.dimata.harisma.entity.overtime.PstOvertime;
import com.dimata.harisma.entity.payroll.PayGeneral;
import com.dimata.harisma.entity.payroll.PstPayGeneral;
import com.dimata.harisma.entity.payroll.PstPayInput;
import com.dimata.harisma.entity.payroll.PstPaySlip;
import com.dimata.harisma.entity.search.SrcOvertimeSummary;
import com.dimata.harisma.form.payroll.FrmPayInput;
import com.dimata.harisma.form.search.FrmSrcOvertimeSummary;
import com.dimata.harisma.session.payroll.SessOvertimeSummary;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Formater;
import com.dimata.util.LogicParser;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

/**
 *
 * @author Satrya Ramayu
 */
public class OvertimeSummaryReportXLS extends HttpServlet {

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
        SrcOvertimeSummary srcOvertimeSummary = new SrcOvertimeSummary();
        FrmSrcOvertimeSummary frmSrcOvertimeSummary = new FrmSrcOvertimeSummary();
        frmSrcOvertimeSummary.requestEntityObject(srcOvertimeSummary, request);
        //String sFullName = request.getParameter(frmSrcOvertimeSummary.fieldNames[frmSrcOvertimeSummary.FRM_FIELD_EMPLOYEE_NAME]);
        System.out.println("\r=============================");

        Vector listOvertimeSummary = new Vector(1, 1);

        String konstantaGaji = "";
        try {
            konstantaGaji = PstSystemProperty.getValueByName("PAYROLL_COMPONEN_CODE_GAJI");

        } catch (Exception exc) {
            System.out.println("Exception PAYROLL_COMPONEN_CODE_GAJI not be set");

        }
        String Order = "";
        //jika dia memilih group by cost center
        if (srcOvertimeSummary != null && srcOvertimeSummary.getGroupBy() == FrmSrcOvertimeSummary.GROUP_BY_COST_CENTER) {
            Order = PstOvertime.fieldNames[PstOvertime.FLD_COST_DEP_ID];
        }
        listOvertimeSummary = SessOvertimeSummary.searchOvertimeSumaryReport(srcOvertimeSummary, 0, 0, Order);
        //listEmployee = SessSpecialEmployee.searchSpecialEmployee(vparam);
        java.util.Hashtable hashListGaji = SessOvertimeSummary.listGajiPerEmployee(0, 0, konstantaGaji, PstPaySlip.fieldNames[PstPaySlip.FLD_EMPLOYEE_ID] + " ASC ");
        String whereClause = " (1=1)";
        if (srcOvertimeSummary.getCompanyId() != 0) {
            whereClause = whereClause + " AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + " = " + srcOvertimeSummary.getCompanyId();
        }
        if (srcOvertimeSummary.getDivisionId() != 0) {
            whereClause = whereClause + " AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " = " + srcOvertimeSummary.getDivisionId();
        }
        if (srcOvertimeSummary.getDepartementId() != 0) {
            whereClause = whereClause + " AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + srcOvertimeSummary.getDepartementId();
        }
        if (srcOvertimeSummary.getSectionId() != 0) {
            whereClause = whereClause + " AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + srcOvertimeSummary.getSectionId();
        }
        if (srcOvertimeSummary.getStartOvertime() != null && srcOvertimeSummary.getEndOvertime() != null) {
            whereClause = whereClause + "  AND (( HE." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.YES_RESIGN + " AND " + "HE. " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                    + " BETWEEN \"" + Formater.formatDate(srcOvertimeSummary.getStartOvertime(), "yyyy-MM-dd  00:00:00") + "\"" + " AND " + "\"" + Formater.formatDate(srcOvertimeSummary.getEndOvertime(), "yyyy-MM-dd  23:59:59") + "\""
                    + " ) OR (HE." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN + "))";
        }
        if (srcOvertimeSummary.getEmpNumber() != null && srcOvertimeSummary.getEmpNumber().length() > 0) {
//            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                                 + " LIKE '%"+empNum.trim()+"%'";
            Vector vectName = logicParser(srcOvertimeSummary.getEmpNumber());
            whereClause = whereClause + " AND ";
            if (vectName != null && vectName.size() > 0) {
                //whereClause = whereClause + " AND (";

                whereClause = whereClause + " (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClause = whereClause + " HE." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClause = whereClause + str.trim();
                    }
                }
                whereClause = whereClause + ")";
            }
        }
        if (srcOvertimeSummary.getFullName() != null && srcOvertimeSummary.getFullName().length() > 0) {
//            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                                 + " LIKE '%"+fullName.trim()+"%'";
            Vector vectName = logicParser(srcOvertimeSummary.getFullName());
            whereClause = whereClause + " AND ";
            if (vectName != null && vectName.size() > 0) {
                //whereClause = whereClause + " AND (";

                whereClause = whereClause + " (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClause = whereClause + " HE." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClause = whereClause + str.trim();
                    }
                }
                whereClause = whereClause + ")";
            }
        }
        String getListEmployeeId = PstEmployee.getSEmployeeId(0, 0, whereClause, PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
        Hashtable hashAdjusmentForPayInput = PstPayInput.listPayInput(srcOvertimeSummary.getStartOvertime(), srcOvertimeSummary.getEndOvertime(), getListEmployeeId);
        Hashtable hashAdjusmentForPaySlip = PstPaySlip.listPaySlip(srcOvertimeSummary.getStartOvertime(), srcOvertimeSummary.getEndOvertime(), getListEmployeeId);

        Date selectedDateFrom = srcOvertimeSummary.getStartOvertime();
        Date selectedDateTo = srcOvertimeSummary.getEndOvertime();
        System.out.println("---===| Excel Report Summary Overtime |===---");
        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Summary Overtime");

        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle styleFillBagroundRedFloat = wb.createCellStyle();
        HSSFDataFormat dfColorRed = wb.createDataFormat();
        styleFillBagroundRedFloat.setDataFormat(dfColorRed.getFormat("#0.##0"));
        styleFillBagroundRedFloat.setFillBackgroundColor(new HSSFColor.RED().getIndex());//HSSFCellStyle.WHITE);
        styleFillBagroundRedFloat.setFillForegroundColor(new HSSFColor.RED().getIndex());//HSSFCellStyle.WHITE);
        styleFillBagroundRedFloat.setFont(createFont(wb, 11, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));
        styleFillBagroundRedFloat.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleFillBagroundRedFloat.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleFillBagroundRedFloat.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleFillBagroundRedFloat.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle styleFillBagroundRedRp = wb.createCellStyle();
        HSSFDataFormat dfColorRedRp = wb.createDataFormat();
        styleFillBagroundRedRp.setDataFormat(dfColorRedRp.getFormat("_(Rp* #,##0.00_);_(Rp* (#,##0.00);_(Rp* \"-\"??_);_(@_)"));//
        styleFillBagroundRedRp.setFillBackgroundColor(new HSSFColor.RED().getIndex());//HSSFCellStyle.WHITE);
        styleFillBagroundRedRp.setFillForegroundColor(new HSSFColor.RED().getIndex());//HSSFCellStyle.WHITE);
        styleFillBagroundRedRp.setFont(createFont(wb, 11, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));
        styleFillBagroundRedRp.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleFillBagroundRedRp.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleFillBagroundRedRp.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleFillBagroundRedRp.setBorderRight(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle formatterFloat = wb.createCellStyle();
        HSSFDataFormat df = wb.createDataFormat();
        formatterFloat.setDataFormat(df.getFormat("#0.##0"));//_(Rp* #,##0.00_);_(Rp* (#,##0.00);_(Rp* "-"??_);_(@_)
        formatterFloat.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        formatterFloat.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        formatterFloat.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        formatterFloat.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        formatterFloat.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        formatterFloat.setBorderRight(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle formatRupiah = wb.createCellStyle();
        HSSFDataFormat dfs = wb.createDataFormat();
        formatRupiah.setDataFormat(dfs.getFormat("_(Rp* #,##0.00_);_(Rp* (#,##0.00);_(Rp* \"-\"??_);_(@_)"));//
        formatRupiah.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        formatRupiah.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        formatRupiah.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        formatRupiah.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        formatRupiah.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        formatRupiah.setBorderRight(HSSFCellStyle.BORDER_THIN);


        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);


        HSSFCellStyle styleTitle = wb.createCellStyle();
        styleTitle.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleTitle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleTitle.setFont(createFont(wb, 12, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));
        // Create a row and put some cells in it. Rows are 0 based.

        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);

        String printHeader = "";//PstSystemProperty.getValueByName("PRINT_HEADER");

        if (printHeader.equals(PstSystemProperty.SYS_NOT_INITIALIZED)) {
            printHeader = "";
        }
        PayGeneral payGeneral = new PayGeneral();
        if (srcOvertimeSummary != null && srcOvertimeSummary.getCompanyId() != 0) {
            try {
                payGeneral = PstPayGeneral.fetchExc(srcOvertimeSummary.getCompanyId());
            } catch (Exception exc) {
            }
        }
        Division division = new Division();
        if (srcOvertimeSummary != null && srcOvertimeSummary.getDivisionId() != 0) {
            try {
                division = PstDivision.fetchExc(srcOvertimeSummary.getDivisionId());
            } catch (Exception exc) {
            }
        }
        Department department = new Department();
        if (srcOvertimeSummary != null && srcOvertimeSummary.getDepartementId() != 0) {
            try {
                department = PstDepartment.fetchExc(srcOvertimeSummary.getDepartementId());
            } catch (Exception exc) {
            }
        }
        Section section = new Section();
        if (srcOvertimeSummary != null && srcOvertimeSummary.getSectionId() != 0) {
            try {
                section = PstSection.fetchExc(srcOvertimeSummary.getSectionId());
            } catch (Exception exc) {
            }
        }
        String companyName = payGeneral != null && payGeneral.getCompanyName().length() > 0 ? payGeneral.getCompanyName() : "-";
        String divisionName = division != null && division.getDivision().length() > 0 ? division.getDivision() : "-";
        String departmenName = department != null && department.getDepartment().length() > 0 ? department.getDepartment() : "-";
        String sectionName = section != null && section.getSection().length() > 0 ? section.getSection() : "-";
        String[] tableTitle = {
            printHeader,
            "REPORT OVERTIME SUMMARY",
            "DATE : " + (srcOvertimeSummary.getStartOvertime() != null ? Formater.formatDate(srcOvertimeSummary.getStartOvertime(), "dd-MM-yyyy") : "") + " To " + (srcOvertimeSummary.getEndOvertime() != null ? Formater.formatDate(srcOvertimeSummary.getEndOvertime(), "dd-MM-yyyy") : ""),
            "COMPANY : " + companyName,
            "DIVISION : " + divisionName,
            "DEPARTEMENT : " + departmenName,
            "SECTION : " + sectionName
        };
        ControlListExcel ctrControlListExcel = new ControlListExcel();
        int countRow = 0;
        /**
         *
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
        countRow = countRow + 1;
        ctrControlListExcel.addHeader("No", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Payrol Number", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Nama", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Company", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Division", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Departement", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Section", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Religion", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Cost Center", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Real Duration", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Rounded Duration", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Total OT Idx Paid By DP", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Total OT Idx Paid By DP Adj", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Total OT Paid By Salary", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Total OT Paid By Salary Adj", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Money Allowance", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Money Allowance Adj", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Food", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Food Adj", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Doc.Status", countRow, 0, 0, 0, 0, style3);
        ctrControlListExcel.addHeader("Paid Salary + Money Allowance", countRow, 0, 0, 0, 0, style3);

        countRow = countRow + 1;
        if (listOvertimeSummary != null && listOvertimeSummary.size() > 0) {

            int no = 1;
            long tmpEmpId = 0;

            double totalRealDuration = 0;
            double totalRoundedDuration = 0;
            //double totalIdxOt = 0;
            double totalPaidByDPAngka = 0;
            double totalPaidBySalary = 0;
            double totalMoneyAllwance = 0;
            int totalFoodAngka = 0;

            double totalPaidByDPAngkaAdj = 0;
            double totalPaidBySalaryAdj = 0;
            double totalMoneyAllwanceAdj = 0;
            int totalFoodAngkaAdj = 0;
            double totalPaidSalaryRupiah = 0;
            boolean noFirtsLoop = false;
            int tmpLoop = 1;
            Hashtable hashEmployeeOtPaidDpAdjSdhDiambil = new Hashtable();
            Hashtable hashEmployeeOtPaidSalaryAdjSdhDiambil = new Hashtable();
            Hashtable hashEmployeeOtAllowanceFoodAdjSdhDiambil = new Hashtable();
            Hashtable hashEmployeeOtAllowanceMOneyAdjSdhDiambil = new Hashtable();
            int rowspanEnd = 0;
            int rowspanStart = 0;
            int tmpRowStart=9;//row dimulai dari
            for (int i = 0; i < listOvertimeSummary.size(); i++) {
                tmpLoop++;
                rowspanEnd++;
                //jumlahRow++;
                ctrControlListExcel.drawHeaderExcel(sheet);
                ctrControlListExcel.resetHeader();
                ctrControlListExcel.resetData();
                OvertimeReportSummary overtimeReportSummary = (OvertimeReportSummary) listOvertimeSummary.get(i);
                try {
                    row = sheet.createRow((short) (countRow));
                    countRow++;

                    sheet.createFreezePane(7, 9);
                    boolean employeeBerbeda = false;
                    int moneyAllowance = 0;
                    int food = 0;
                    double valueGaji = 0;
                    if (overtimeReportSummary != null && overtimeReportSummary.getEmployee_id() != 0 && hashListGaji.get(overtimeReportSummary.getEmployee_id()) != null) {
                        valueGaji = (Double) hashListGaji.get(overtimeReportSummary.getEmployee_id());
                    }


                    cell = row.createCell((short) 0);
                    cell.setCellValue("");
                    cell.setCellStyle(style2);

                    double totalIdxPaidDayOffAdj = 0;
                    if (hashEmployeeOtPaidDpAdjSdhDiambil == null || hashEmployeeOtPaidDpAdjSdhDiambil.size() == 0 || (hashEmployeeOtPaidDpAdjSdhDiambil.size() > 0 && hashEmployeeOtPaidDpAdjSdhDiambil.containsKey("" + overtimeReportSummary.getEmployee_id()) == false)) {
                        if (hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0 && selectedDateTo != null && selectedDateFrom != null) {
                            Date dtAdjusment = null;
                            long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                            if (duration > 0) {
                                int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                    dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                    if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_DP] + "_" + overtimeReportSummary.getEmployee_id() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                        double dtAdj = ((Double) hashAdjusmentForPayInput.get(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_DP] + "_" + overtimeReportSummary.getEmployee_id() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd")));
                                        totalIdxPaidDayOffAdj = dtAdj;
                                        hashEmployeeOtPaidDpAdjSdhDiambil.put("" + overtimeReportSummary.getEmployee_id(), true);
                                        break;////karena di pay input hanya 1 tgl saja yg di simpan

                                    }
                                }
                            }

                        }
                    }
                    double totalIdxPaidSalaryAdj = 0;
                    if (hashEmployeeOtPaidSalaryAdjSdhDiambil == null || hashEmployeeOtPaidSalaryAdjSdhDiambil.size() == 0 || (hashEmployeeOtPaidSalaryAdjSdhDiambil.size() > 0 && hashEmployeeOtPaidSalaryAdjSdhDiambil.containsKey("" + overtimeReportSummary.getEmployee_id()) == false)) {
                        if (hashAdjusmentForPaySlip != null && hashAdjusmentForPaySlip.size() > 0 && selectedDateTo != null && selectedDateFrom != null) {
                            Date dtAdjusment = null;
                            long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                            if (duration > 0) {
                                int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                    dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                    if (dtAdjusment != null && hashAdjusmentForPaySlip.containsKey(PstPaySlip.fieldNames[PstPaySlip.FLD_OV_IDX_ADJUSTMENT] + "_" + overtimeReportSummary.getEmployee_id() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                        double dtAdj = ((Double) hashAdjusmentForPaySlip.get(PstPaySlip.fieldNames[PstPaySlip.FLD_OV_IDX_ADJUSTMENT] + "_" + overtimeReportSummary.getEmployee_id() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd")));
                                        totalIdxPaidSalaryAdj = dtAdj;
                                        hashEmployeeOtPaidSalaryAdjSdhDiambil.put("" + overtimeReportSummary.getEmployee_id(), true);
                                        break;////karena di pay input hanya 1 tgl saja yg di simpan
                                    }
                                }
                            }

                        }
                    }

                    double totalIdxAllowanceMoneyAdj = 0;
                    if (hashEmployeeOtAllowanceMOneyAdjSdhDiambil == null || hashEmployeeOtAllowanceMOneyAdjSdhDiambil.size() == 0 || (hashEmployeeOtAllowanceMOneyAdjSdhDiambil.size() > 0 && hashEmployeeOtAllowanceMOneyAdjSdhDiambil.containsKey("" + overtimeReportSummary.getEmployee_id()) == false)) {
                        if (hashAdjusmentForPaySlip != null && hashAdjusmentForPaySlip.size() > 0 && selectedDateTo != null && selectedDateFrom != null) {
                            Date dtAdjusment = null;
                            long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                            if (duration > 0) {
                                int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                for (int idxDt = 0; idxDt < itDate; idxDt++) {

                                    dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                    if (dtAdjusment != null && hashAdjusmentForPaySlip.containsKey(PstPaySlip.fieldNames[PstPaySlip.FLD_MEAL_ALLOWANCE_MONEY_ADJUSMENT] + "_" + overtimeReportSummary.getEmployee_id() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                        double dtAdj = ((Double) hashAdjusmentForPaySlip.get(PstPaySlip.fieldNames[PstPaySlip.FLD_MEAL_ALLOWANCE_MONEY_ADJUSMENT] + "_" + overtimeReportSummary.getEmployee_id() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd")));
                                        totalIdxAllowanceMoneyAdj = dtAdj;
                                        hashEmployeeOtAllowanceMOneyAdjSdhDiambil.put("" + overtimeReportSummary.getEmployee_id(), true);
                                        break;////karena di pay input hanya 1 tgl saja yg di simpan
                                    }
                                }
                            }

                        }
                    }

                    double totalIdxAllowanceFoodAdj = 0;
                    if (hashEmployeeOtAllowanceFoodAdjSdhDiambil == null || hashEmployeeOtAllowanceFoodAdjSdhDiambil.size() == 0 || (hashEmployeeOtAllowanceFoodAdjSdhDiambil.size() > 0 && hashEmployeeOtAllowanceFoodAdjSdhDiambil.containsKey("" + overtimeReportSummary.getEmployee_id()) == false)) {
                        if (hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0 && selectedDateTo != null && selectedDateFrom != null) {
                            Date dtAdjusment = null;
                            long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                            if (duration > 0) {
                                int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                    dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                    if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_ADJUSMENT] + "_" + overtimeReportSummary.getEmployee_id() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                        double dtAdj = ((Double) hashAdjusmentForPayInput.get(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_ADJUSMENT] + "_" + overtimeReportSummary.getEmployee_id() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd")));
                                        totalIdxAllowanceFoodAdj = dtAdj;
                                        hashEmployeeOtAllowanceFoodAdjSdhDiambil.put("" + overtimeReportSummary.getEmployee_id(), true);
                                        break;////karena di pay input hanya 1 tgl saja yg di simpan
                                    }
                                }
                            }

                        }
                    }
                    if (overtimeReportSummary != null && overtimeReportSummary.getEmployee_id() != tmpEmpId) { // sessTmpSpecialEmployee.getEmployeeNum().equalsIgnoreCase("25043")){
                        tmpEmpId = overtimeReportSummary.getEmployee_id();
                        employeeBerbeda = true;

                        if (noFirtsLoop) {
                            //countRow++;
                            //row = sheet.createRow((short) (countRow));
                            cell = row.createCell((short) 0);
                            cell.setCellValue("");
                            cell.setCellStyle(styleFillBagroundRedFloat);
                            cell = row.createCell((short) 1);
                            cell.setCellValue("");
                            cell.setCellStyle(styleFillBagroundRedFloat);
                            cell = row.createCell((short) 2);
                            cell.setCellValue("Total");
                            cell.setCellStyle(styleFillBagroundRedFloat);
                            for (int idx = 3; idx < 9; idx++) {
                                cell = row.createCell((short) idx);
                                cell.setCellValue("");
                                cell.setCellStyle(styleFillBagroundRedFloat);
                            }
                            cell = row.createCell((short) 9);
                            cell.setCellValue(totalRealDuration);
                            cell.setCellStyle(styleFillBagroundRedFloat);

                            cell = row.createCell((short) 10);
                            cell.setCellValue(totalRoundedDuration);
                            cell.setCellStyle(styleFillBagroundRedFloat);


                            cell = row.createCell((short) 11);
                            cell.setCellValue(totalPaidByDPAngka + totalPaidByDPAngkaAdj);//tot by DP
                            cell.setCellFormula(totalPaidByDPAngka + "+" + totalPaidByDPAngkaAdj);
                            cell.setCellStyle(styleFillBagroundRedFloat);


                            cell = row.createCell((short) 12);
                            cell.setCellValue("");//tot by DP adj
                            cell.setCellStyle(styleFillBagroundRedFloat);
                            rowspanStart= tmpRowStart;
                            //rowspanEnd = rowspanEnd + 1;//kenapa tambah sat
//                            if (rowspanStart != 0 || rowspanEnd != 0 || 12 != 0 || 12 != 0) {
//                                sheet.addMergedRegion(new Region(
//                                        rowspanStart,//row form
//                                        (short) 12, //cols from
//                                        rowspanEnd, //row end
//                                        (short) 12 //cols end
//                                        ));
//
//
//                            }
//


                            if (srcOvertimeSummary.isCekUser()) {
                                cell = row.createCell((short) 13);
                                cell.setCellValue((totalPaidBySalary + totalPaidBySalaryAdj) * PstOvertime.KOSNTANTA_OVERTIME * valueGaji);
                                cell.setCellFormula((("(" + totalPaidBySalary + "+" + totalPaidBySalaryAdj) + ")" + "*" + ("" + PstOvertime.KOSNTANTA_OVERTIME) + "*" + ("" + valueGaji)));
                                cell.setCellStyle(styleFillBagroundRedRp);

                                cell = row.createCell((short) 14);
                                cell.setCellValue("");
                                cell.setCellStyle(styleFillBagroundRedFloat);
                                //cell.setCellFormula(( (""+totIdxSalary))); 
                            } else {
                                cell = row.createCell((short) 13);
                                cell.setCellValue(totalPaidBySalary + totalPaidBySalaryAdj);
                                cell.setCellFormula((("" + totalPaidBySalary + totalPaidBySalaryAdj)));
                                cell.setCellStyle(styleFillBagroundRedFloat);

                                cell = row.createCell((short) 14);
                                cell.setCellValue("");
                                cell.setCellStyle(styleFillBagroundRedFloat);
                            }

                            if (srcOvertimeSummary.isCekUser()) {
                                cell = row.createCell((short) 15);
                                cell.setCellValue((totalMoneyAllwance + totalIdxAllowanceMoneyAdj) * OvertimeDetail.KONSTANTA_MONEY_ALLOWANCE);
                                cell.setCellFormula((("(" + totalMoneyAllwance + "+" + totalIdxAllowanceMoneyAdj) + ")" + "*" + ("" + OvertimeDetail.KONSTANTA_MONEY_ALLOWANCE)));
                                cell.setCellStyle(styleFillBagroundRedRp);

                                cell = row.createCell((short) 16);
                                cell.setCellValue("");
                                cell.setCellStyle(styleFillBagroundRedFloat);
                            } else {
                                cell = row.createCell((short) 15);
                                cell.setCellValue(moneyAllowance + totalIdxAllowanceMoneyAdj);
                                cell.setCellFormula((("" + moneyAllowance + "+" + totalIdxAllowanceMoneyAdj)));
                                cell.setCellStyle(styleFillBagroundRedFloat);

                                cell = row.createCell((short) 16);
                                cell.setCellValue("");
                                cell.setCellStyle(styleFillBagroundRedFloat);
                            }

                            cell = row.createCell((short) 17);
                            cell.setCellValue(totalFoodAngka + totalFoodAngkaAdj);
                            cell.setCellFormula(totalFoodAngka + "+" + totalFoodAngkaAdj);
                            cell.setCellStyle(styleFillBagroundRedFloat);

                            cell = row.createCell((short) 18);
                            cell.setCellValue("");// tot food adj
                            cell.setCellStyle(styleFillBagroundRedFloat);

                            //update by satrya 2014-02-11
                            cell = row.createCell((short) 19);
                            cell.setCellValue("");
                            cell.setCellStyle(styleFillBagroundRedFloat);

                            if (srcOvertimeSummary.isCekUser()) {
                                totalPaidSalaryRupiah = ((totalPaidBySalary + totalPaidBySalaryAdj) * PstOvertime.KOSNTANTA_OVERTIME * valueGaji) + ((totalMoneyAllwance + totalMoneyAllwanceAdj) * OvertimeDetail.KONSTANTA_MONEY_ALLOWANCE);
                                cell = row.createCell((short) 20);
                                cell.setCellValue(totalPaidSalaryRupiah);
                                cell.setCellStyle(styleFillBagroundRedRp);
                            } else {
                                totalPaidSalaryRupiah = (totalPaidBySalary + totalPaidBySalaryAdj) + (totalMoneyAllwance + totalMoneyAllwanceAdj);
                                cell = row.createCell((short) 20);
                                cell.setCellValue(totalPaidSalaryRupiah);
                                cell.setCellStyle(styleFillBagroundRedRp);
                            }


                            //row = sheet.getRow(countRow);
                            row = sheet.createRow((short) (countRow));
                            countRow++;

                            rowspanStart = rowspanEnd+tmpRowStart;
                            rowspanEnd = 0;

                        }

                        totalRealDuration = 0;
                        totalRoundedDuration = 0;
                        totalPaidByDPAngka = 0;
                        totalPaidByDPAngkaAdj = 0;

                        totalPaidBySalary = 0;
                        totalPaidBySalaryAdj = 0;

                        totalMoneyAllwance = 0;
                        totalMoneyAllwanceAdj = 0;

                        totalFoodAngka = 0;
                        totalFoodAngkaAdj = 0;

                        totalPaidSalaryRupiah = 0;
                        noFirtsLoop = true;
                        cell = row.createCell((short) 0);
                        cell.setCellValue(no);
                        cell.setCellStyle(style2);
                        no++;
                    }


                    if (employeeBerbeda) {

                        cell = row.createCell((short) 1);
                        cell.setCellValue(overtimeReportSummary.getEmpNumber() != null && overtimeReportSummary.getEmpNumber().length() > 0 ? overtimeReportSummary.getEmpNumber() : "-");
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) 2);
                        cell.setCellValue(overtimeReportSummary.getFullName() != null && overtimeReportSummary.getFullName().length() > 0 ? overtimeReportSummary.getFullName() : "-");
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) 3);
                        cell.setCellValue(overtimeReportSummary.getCompany() != null && overtimeReportSummary.getCompany().length() > 0 ? overtimeReportSummary.getCompany() : "-");
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) 4);
                        cell.setCellValue(overtimeReportSummary.getDivision() != null && overtimeReportSummary.getDivision().length() > 0 ? overtimeReportSummary.getDivision() : "-");
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) 5);
                        cell.setCellValue(overtimeReportSummary.getDepartmentEmployee() != null && overtimeReportSummary.getDepartmentEmployee().length() > 0 ? overtimeReportSummary.getDepartmentEmployee() : "-");
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) 6);
                        cell.setCellValue(overtimeReportSummary.getSectionName() != null && overtimeReportSummary.getSectionName().length() > 0 ? overtimeReportSummary.getSectionName() : "-");
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) 7);
                        cell.setCellValue(overtimeReportSummary.getReligion() != null && overtimeReportSummary.getReligion().length() > 0 ? overtimeReportSummary.getReligion() : "-");
                        cell.setCellStyle(style2);
                    } else {
                        //row = sheet.createRow((short) (countRow));

                        for (int idx = 1; idx < 21; idx++) {
                            cell = row.createCell((short) idx);
                            cell.setCellValue("");
                            cell.setCellStyle(style2);
                        }

                        for (int idx = 1; idx < 8; idx++) {
                            cell = row.createCell((short) idx);
                            cell.setCellValue("");
                            cell.setCellStyle(style2);
                        }

                    }



                    cell = row.createCell((short) 8);
                    cell.setCellValue(overtimeReportSummary.getCostDept() != null && overtimeReportSummary.getCostDept().length() > 0 ? overtimeReportSummary.getCostDept() : "-");
                    cell.setCellStyle(style2);

                    cell = row.createCell((short) 9);
                    cell.setCellValue(overtimeReportSummary.getOvtDuration());
                    cell.setCellStyle(style2);

                    cell = row.createCell((short) 10);
                    cell.setCellValue(overtimeReportSummary.getOvtRoundDuration());
                    cell.setCellStyle(style2);


                    //Paid by DP
                    double totIdxDp = 0;
                    if (overtimeReportSummary.getPaidBy() == OvertimeDetail.PAID_BY_DAY_OFF) {
                        totIdxDp = overtimeReportSummary.getTotIdx();
                    }

                    double totIdxSalary = 0;
                    if (overtimeReportSummary.getPaidBy() == OvertimeDetail.PAID_BY_SALARY) {
                        totIdxSalary = overtimeReportSummary.getTotIdx();
                    }

                    cell = row.createCell((short) 11);
                    cell.setCellValue(totIdxDp);
                    cell.setCellStyle(style2);

                    //ini ada adj totIdx Dp
                    cell = row.createCell((short) 12);
                    if (totalIdxPaidDayOffAdj == 0) {
                        cell.setCellValue("");
                    } else {
                        cell.setCellValue(totalIdxPaidDayOffAdj);
                    }
                    cell.setCellStyle(style2);


                    if (srcOvertimeSummary.isCekUser()) {
                        cell = row.createCell((short) 13);
                        cell.setCellValue(totIdxSalary * PstOvertime.KOSNTANTA_OVERTIME * valueGaji);
                        cell.setCellStyle(formatRupiah);
                        cell.setCellFormula((("" + totIdxSalary) + "*" + ("" + PstOvertime.KOSNTANTA_OVERTIME) + "*" + ("" + valueGaji)));

                        cell = row.createCell((short) 14);
                        if (totalIdxPaidSalaryAdj == 0) {
                            cell.setCellValue("");
                        } else {
                            cell.setCellValue(totalIdxPaidSalaryAdj);
                        }
                        cell.setCellStyle(style2);
                        //cell.setCellFormula(( (""+totIdxSalary))); 
                    } else {
                        cell = row.createCell((short) 13);
                        cell.setCellValue(totIdxSalary);
                        cell.setCellStyle(style2);
                        cell.setCellFormula((("" + totIdxSalary)));

                        cell = row.createCell((short) 14);
                        if (totalIdxPaidSalaryAdj == 0) {
                            cell.setCellValue("");
                        } else {
                            cell.setCellValue(totalIdxPaidSalaryAdj);
                        }

                        cell.setCellStyle(style2);
                    }



                    if (overtimeReportSummary.getAllowance() == Overtime.ALLOWANCE_MONEY) {

                        moneyAllowance = 1;
                    } else {
                        food = 1;
                    }

                    if (srcOvertimeSummary.isCekUser()) {
                        cell = row.createCell((short) 15);
                        cell.setCellValue(moneyAllowance * OvertimeDetail.KONSTANTA_MONEY_ALLOWANCE);
                        cell.setCellStyle(formatRupiah);
                        cell.setCellFormula((("" + moneyAllowance) + "*" + ("" + OvertimeDetail.KONSTANTA_MONEY_ALLOWANCE)));

                        cell = row.createCell((short) 16);
                        if (totalIdxAllowanceMoneyAdj == 0) {
                            cell.setCellValue("");
                        } else {
                            cell.setCellValue(totalIdxAllowanceMoneyAdj);
                        }

                        cell.setCellStyle(formatterFloat);
                    } else {
                        cell = row.createCell((short) 15);
                        cell.setCellValue(moneyAllowance);
                        cell.setCellFormula((("" + moneyAllowance)));
                        cell.setCellStyle(formatterFloat);

                        cell = row.createCell((short) 16);
                        if (totalIdxAllowanceMoneyAdj == 0) {
                            cell.setCellValue("");
                        } else {
                            cell.setCellValue(totalIdxAllowanceMoneyAdj);
                        }

                        cell.setCellStyle(formatterFloat);
                    }

                    //cell.setCellFormula(( (""+totIdxSalary))); 

                    cell = row.createCell((short) 17);
                    cell.setCellValue(food);
                    cell.setCellStyle(formatterFloat);

                    cell = row.createCell((short) 18);
                    if (totalIdxAllowanceFoodAdj == 0) {
                        cell.setCellValue("");
                    } else {
                        cell.setCellValue(totalIdxAllowanceFoodAdj);
                    }

                    cell.setCellStyle(formatterFloat);

                    //update by satrya 2014-02-11
                    //status Doc
                    cell = row.createCell((short) 19);
                    cell.setCellValue(overtimeReportSummary.getStatusDoc());
                    cell.setCellStyle(formatterFloat);


                    cell = row.createCell((short) 20);
                    cell.setCellValue("");
                    //cell.setCellFormula("="+totPaidBySalary * PstOvertime.KOSNTANTA_OVERTIME * valueGaji); 
                    cell.setCellStyle(formatterFloat);
                    // }
                    totalRealDuration = totalRealDuration + overtimeReportSummary.getOvtDuration();
                    totalRoundedDuration = totalRoundedDuration + overtimeReportSummary.getOvtRoundDuration();
                    //totalIdxOt = totalIdxOt + overtimeReportSummary.getTotIdx();
                    totalPaidByDPAngka = totalPaidByDPAngka + totIdxDp;
                    totalPaidByDPAngkaAdj = totalPaidByDPAngkaAdj + totalIdxPaidDayOffAdj;


                    totalPaidBySalary = totalPaidBySalary + totIdxSalary;//(totIdxSalary * PstOvertime.KOSNTANTA_OVERTIME * valueGaji);
                    totalPaidBySalaryAdj = totalPaidBySalaryAdj + totalIdxPaidSalaryAdj;


                    totalMoneyAllwance = totalMoneyAllwance + moneyAllowance;//(moneyAllowance * OvertimeDetail.KONSTANTA_MONEY_ALLOWANCE);
                    totalMoneyAllwanceAdj = totalMoneyAllwanceAdj + totalIdxAllowanceMoneyAdj;

                    totalFoodAngka = totalFoodAngka + food;
                    totalFoodAngkaAdj = totalFoodAngkaAdj + (int) totalIdxAllowanceFoodAdj;
                    //totalPaidSalaryRupiah = totalPaidSalaryRupiah + (totIdxSalary * PstOvertime.KOSNTANTA_OVERTIME * valueGaji);
                    if (tmpLoop > listOvertimeSummary.size()) {
                        //countRow++;
                        //row = sheet.createRow((short) (countRow));
                        row = sheet.createRow((short) (countRow));

                        for (int idx = 0; idx < 21; idx++) {
                            cell = row.createCell((short) idx);
                            cell.setCellValue("");
                            cell.setCellStyle(styleFillBagroundRedFloat);
                        }
                        cell = row.createCell((short) 0);
                        cell.setCellValue("");
                        cell.setCellStyle(styleFillBagroundRedFloat);
                        cell = row.createCell((short) 1);
                        cell.setCellValue("");
                        cell.setCellStyle(styleFillBagroundRedFloat);
                        cell = row.createCell((short) 2);
                        cell.setCellValue("Total");
                        cell.setCellStyle(styleFillBagroundRedFloat);

                        for (int idx = 3; idx < 9; idx++) {
                            cell = row.createCell((short) idx);
                            cell.setCellValue("");
                            cell.setCellStyle(styleFillBagroundRedFloat);
                        }

                        cell = row.createCell((short) 9);
                        cell.setCellValue(totalRealDuration);
                        cell.setCellStyle(styleFillBagroundRedFloat);

                        cell = row.createCell((short) 10);
                        cell.setCellValue(totalRoundedDuration);
                        cell.setCellStyle(styleFillBagroundRedFloat);


                        cell = row.createCell((short) 11);
                        cell.setCellValue(totalPaidByDPAngka + totalPaidByDPAngkaAdj);//tot by DP
                        cell.setCellFormula(totalPaidByDPAngka + "+" + totalPaidByDPAngkaAdj);
                        cell.setCellStyle(styleFillBagroundRedFloat);

                        cell = row.createCell((short) 12);
                        cell.setCellValue("");//tot by DP adj
                        cell.setCellStyle(styleFillBagroundRedFloat);

                        if (srcOvertimeSummary.isCekUser()) {
                            cell = row.createCell((short) 13);
                            cell.setCellValue((totalPaidBySalary + totalPaidBySalaryAdj) * PstOvertime.KOSNTANTA_OVERTIME * valueGaji);
                            cell.setCellFormula((("(" + totalPaidBySalary + "+" + totalPaidBySalaryAdj) + ")" + "*" + ("" + PstOvertime.KOSNTANTA_OVERTIME) + "*" + ("" + valueGaji)));
                            cell.setCellStyle(styleFillBagroundRedRp);

                            cell = row.createCell((short) 14);
                            cell.setCellValue("");
                            cell.setCellStyle(styleFillBagroundRedFloat);
                            //cell.setCellFormula(( (""+totIdxSalary))); 
                        } else {
                            cell = row.createCell((short) 13);
                            cell.setCellValue(totalPaidBySalary + totalPaidBySalaryAdj);
                            cell.setCellFormula((("" + totalPaidBySalary + totalPaidBySalaryAdj)));
                            cell.setCellStyle(styleFillBagroundRedFloat);

                            cell = row.createCell((short) 14);
                            cell.setCellValue("");
                            cell.setCellStyle(styleFillBagroundRedFloat);
                        }


                        cell = row.createCell((short) 14);
                        cell.setCellValue("");//tot by DP Salary Adj
                        cell.setCellStyle(styleFillBagroundRedFloat);
                        //harusnya di marge

                        if (srcOvertimeSummary.isCekUser()) {
                            cell = row.createCell((short) 15);
                            cell.setCellValue(((totalMoneyAllwance + totalMoneyAllwanceAdj) * OvertimeDetail.KONSTANTA_MONEY_ALLOWANCE));
                            cell.setCellFormula(("(" + totalMoneyAllwance + "+" + totalMoneyAllwanceAdj + ")" + "*" + OvertimeDetail.KONSTANTA_MONEY_ALLOWANCE));
                            cell.setCellStyle(styleFillBagroundRedRp);

                        } else {
                            cell = row.createCell((short) 15);
                            cell.setCellValue(totalMoneyAllwance + totalMoneyAllwanceAdj);
                            cell.setCellFormula(totalMoneyAllwance + "+" + totalMoneyAllwanceAdj);
                            cell.setCellStyle(styleFillBagroundRedFloat);
                        }

                        cell = row.createCell((short) 16);
                        cell.setCellValue("");// tot moneyAllowanceAdj
                        cell.setCellStyle(styleFillBagroundRedFloat);

                        cell = row.createCell((short) 17);
                        cell.setCellValue(totalFoodAngka + totalFoodAngkaAdj);
                        cell.setCellFormula(totalFoodAngka + "+" + totalFoodAngkaAdj);
                        cell.setCellStyle(styleFillBagroundRedFloat);

                        cell = row.createCell((short) 18);
                        cell.setCellValue("");// tot food adj
                        cell.setCellStyle(styleFillBagroundRedFloat);

                        //update by satrya 2014-02-11
                        cell = row.createCell((short) 19);
                        cell.setCellValue("");
                        cell.setCellStyle(styleFillBagroundRedFloat);

                        if (srcOvertimeSummary.isCekUser()) {
                            totalPaidSalaryRupiah = ((totalPaidBySalary + totalPaidBySalaryAdj) * PstOvertime.KOSNTANTA_OVERTIME * valueGaji) + ((totalMoneyAllwance + totalMoneyAllwanceAdj) * OvertimeDetail.KONSTANTA_MONEY_ALLOWANCE);
                            cell = row.createCell((short) 20);
                            cell.setCellValue(totalPaidSalaryRupiah);
                            cell.setCellStyle(styleFillBagroundRedRp);
                        } else {
                            totalPaidSalaryRupiah = (totalPaidBySalary + totalPaidBySalaryAdj) + (totalMoneyAllwance + totalMoneyAllwanceAdj);
                            cell = row.createCell((short) 20);
                            cell.setCellValue(totalPaidSalaryRupiah);
                            cell.setCellStyle(styleFillBagroundRedFloat);
                        }


                        rowspanStart = rowspanEnd+rowspanStart;
                        rowspanEnd = 0;

                    }
                    //rowspanEnd = rowspanEnd + 1;



                    //System.out.println(" Employee "+sessTmpSpecialEmployee.getFullName()+" Employee Num: "+ sessTmpSpecialEmployee.getEmployeeNum()+" DEPARTEMENT "+sessTmpSpecialEmployee.getDepartement());
                } catch (Exception exc) {
                    System.out.println("Exception export excelovertime employee " + exc + " Employee Num " + overtimeReportSummary.getEmpNumber());
                }
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
