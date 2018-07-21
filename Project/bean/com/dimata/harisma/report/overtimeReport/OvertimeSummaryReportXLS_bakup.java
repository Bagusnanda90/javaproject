/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.overtimeReport;

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

/**
 *
 * @author Satrya Ramayu
 */
public class OvertimeSummaryReportXLS_bakup extends HttpServlet {

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
            if(srcOvertimeSummary!=null && srcOvertimeSummary.getCompanyId()!=0){
               try{
                payGeneral = PstPayGeneral.fetchExc(srcOvertimeSummary.getCompanyId());
               }catch(Exception exc){
               
               }
            }
            Division division = new Division();
            if(srcOvertimeSummary!=null && srcOvertimeSummary.getDivisionId()!=0){
              try{
                division = PstDivision.fetchExc(srcOvertimeSummary.getDivisionId());
              }catch(Exception exc){
              
              }
            }
            Department department = new Department();
            if(srcOvertimeSummary!=null && srcOvertimeSummary.getDepartementId()!=0){
               try{
                department =PstDepartment.fetchExc(srcOvertimeSummary.getDepartementId());
               }catch(Exception exc){
               
               }
            }
            Section section = new Section();
            if(srcOvertimeSummary!=null && srcOvertimeSummary.getSectionId()!=0){
               try{
                section = PstSection.fetchExc(srcOvertimeSummary.getSectionId());
               }catch(Exception exc){
               
               }
            }
            String companyName = payGeneral!=null && payGeneral.getCompanyName().length()>0?payGeneral.getCompanyName():"-";
            String divisionName = division!=null && division.getDivision().length()>0?division.getDivision():"-";
            String departmenName = department!=null && department.getDepartment().length()>0?department.getDepartment():"-";
            String sectionName = section!=null && section.getSection().length()>0?section.getSection():"-";
            String[] tableTitle = {
                printHeader,
                "REPORT OVERTIME SUMMARY",
                "DATE : " + (srcOvertimeSummary.getStartOvertime() != null ? Formater.formatDate(srcOvertimeSummary.getStartOvertime(), "dd-MM-yyyy") : "") + " To " + (srcOvertimeSummary.getEndOvertime() != null ? Formater.formatDate(srcOvertimeSummary.getEndOvertime(), "dd-MM-yyyy") : ""),
                "COMPANY : " + companyName,
                "DIVISION : " + divisionName,
                "DEPARTEMENT : " + departmenName,
                "SECTION : " + sectionName
            };

        int countRow = 0;
        /**
         * 
         */
        for (int k = 0; k < tableTitle.length; k++) {
                row = sheet.createRow((short) (countRow));
               
                countRow++;
                cell = row.createCell((short) 0);
                cell.setCellValue(tableTitle[k]);
                cell.setCellStyle(styleTitle);
         }
        
        
          
           // String[] tableSubTitle = {};
        String[] tableHeader = {
             "No", "Payrol Number", "Nama", "Company", "Division", 
            "Departement", "Section", "Religion", "Cost Center", 
            "Real Duration", "Rounded Duration", 
            "Total OT Idx Paid By DP", "Total OT Paid By Salary", "Money Allowance", "Food","Doc.Status", "Total Paid Salary"
        };
        row = sheet.createRow((short) (6));
        for (int k = 0; k < tableHeader.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeader[k]);
            cell.setCellStyle(style3);
        }
         countRow = 7;
        // int addRow = 0;
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
            double totalPaidSalaryRupiah = 0;
            boolean noFirtsLoop = false;
            int tmpLoop = 1;
            for (int i = 0; i < listOvertimeSummary.size(); i++) {
                tmpLoop++;
                OvertimeReportSummary overtimeReportSummary = (OvertimeReportSummary) listOvertimeSummary.get(i);
                try {
                    row = sheet.createRow((short) (countRow));
                    countRow++;

                    sheet.createFreezePane(7, 7);
                    boolean employeeBerbeda = false;
                    int moneyAllowance = 0;
                    int food = 0;
                    double valueGaji = 0;
                     if (overtimeReportSummary != null && overtimeReportSummary.getEmployee_id() != 0 && hashListGaji.get(overtimeReportSummary.getEmployee_id())!=null ) {
                        valueGaji = (Double) hashListGaji.get(overtimeReportSummary.getEmployee_id());
                    }

                    //double totIdx=0;
                    //hanya menampilkan paid by salary saja
                    // if (overtimeReportSummary.getPaidBy() == OvertimeDetail.PAID_BY_SALARY) {
                    // totIdx = overtimeReportSummary.getTotIdx();

                    cell = row.createCell((short) 0);
                    cell.setCellValue("");
                    cell.setCellStyle(style2);
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

//                            cell = row.createCell((short) 11);
//                            cell.setCellValue(totalIdxOt);
//                            cell.setCellStyle(formatterFloat);

                            cell = row.createCell((short) 11);
                            cell.setCellValue(totalPaidByDPAngka);//tot by DP
                            cell.setCellStyle(styleFillBagroundRedFloat);

                            cell = row.createCell((short) 12);//tot by salary
                            cell.setCellValue(totalPaidBySalary);
                            cell.setCellStyle(styleFillBagroundRedRp);

                            cell = row.createCell((short) 13);
                            cell.setCellValue(totalMoneyAllwance);
                            cell.setCellStyle(styleFillBagroundRedRp);

                            cell = row.createCell((short) 14);
                            cell.setCellValue(totalFoodAngka);
                            cell.setCellStyle(styleFillBagroundRedFloat);
                            
                            //update by satrya 2014-02-11
                            cell = row.createCell((short) 15);
                            cell.setCellValue("");
                            cell.setCellStyle(styleFillBagroundRedFloat);

                            totalPaidSalaryRupiah = totalPaidBySalary + totalMoneyAllwance;
                            cell = row.createCell((short) 16);
                            cell.setCellValue(totalPaidSalaryRupiah);
                            cell.setCellStyle(styleFillBagroundRedRp);
                            
                           
                            //row = sheet.getRow(countRow);
                            row = sheet.createRow((short) (countRow));
                             countRow++;
                            
//                          for(int idx=0;idx<17;idx++){
//                            cell = row.createCell((short) idx);
//                            cell.setCellValue("dd");
//                            cell.setCellStyle(style2);
//                          }
                          
                        }
                           
                        totalRealDuration = 0;
                        totalRoundedDuration = 0;
                        totalPaidByDPAngka = 0;
                        totalPaidBySalary = 0;
                        totalMoneyAllwance = 0;
                        totalFoodAngka = 0;
                        totalPaidSalaryRupiah = 0;
                        noFirtsLoop = true;
                        cell = row.createCell((short) 0);
                        cell.setCellValue(no);
                        cell.setCellStyle(style2);
                        no++;
                    }

                    
                   if(employeeBerbeda){
                      
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
                  }
                    else{
                        //row = sheet.createRow((short) (countRow));
                            
                          for(int idx=1;idx<17;idx++){
                            cell = row.createCell((short) idx);
                            cell.setCellValue("");
                            cell.setCellStyle(style2);
                          }
                          
                        for(int idx=1;idx<8;idx++){
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

//                    cell = row.createCell((short) 11);
//                    cell.setCellValue(overtimeReportSummary.getTotIdx());
//                    cell.setCellStyle(style2);

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

                    //UPDATE BY DEVIN 2014-02-12
                    if(srcOvertimeSummary.isCekUser()==false){
                    
                    cell = row.createCell((short) 12);
                    cell.setCellValue(totIdxSalary);
                    cell.setCellStyle(style2);
                    cell.setCellFormula(( (""+totIdxSalary))); 
                     
                    }
                     if(srcOvertimeSummary.isCekUser()==true){
                         cell = row.createCell((short) 12);
                    cell.setCellValue(totIdxSalary * PstOvertime.KOSNTANTA_OVERTIME * valueGaji);
                    cell.setCellStyle(formatRupiah);
                    cell.setCellFormula(( (""+totIdxSalary) +"*"+ (""+PstOvertime.KOSNTANTA_OVERTIME) +"*"+ (""+valueGaji))); 
                    
                     }

                    
                    
                    if (overtimeReportSummary.getAllowance() == Overtime.ALLOWANCE_MONEY) {

                        moneyAllowance = 1;
                    } else {
                        food = 1;
                    }
                    cell = row.createCell((short) 13);
                    cell.setCellValue(moneyAllowance * OvertimeDetail.KONSTANTA_MONEY_ALLOWANCE);
                    cell.setCellStyle(formatRupiah);
                    cell.setCellFormula(( (""+moneyAllowance) +"*"+ (""+OvertimeDetail.KONSTANTA_MONEY_ALLOWANCE)) ); 
                    

                    cell = row.createCell((short) 14);
                    cell.setCellValue(food);
                    cell.setCellStyle(style2);
                    
                    //update by satrya 2014-02-11
                    //status Doc
                    cell = row.createCell((short) 15);
                    cell.setCellValue(overtimeReportSummary.getStatusDoc());
                    cell.setCellStyle(style2);

                    
                    cell = row.createCell((short) 16);
                    cell.setCellValue("");
                    //cell.setCellFormula("="+totPaidBySalary * PstOvertime.KOSNTANTA_OVERTIME * valueGaji); 
                    cell.setCellStyle(formatterFloat);
                    // }
                     totalRealDuration = totalRealDuration + overtimeReportSummary.getOvtDuration();
                    totalRoundedDuration = totalRoundedDuration + overtimeReportSummary.getOvtRoundDuration();
                    //totalIdxOt = totalIdxOt + overtimeReportSummary.getTotIdx();
                    totalPaidByDPAngka =  totalPaidByDPAngka + totIdxDp;
                    totalPaidBySalary =  totalPaidBySalary + (totIdxSalary * PstOvertime.KOSNTANTA_OVERTIME * valueGaji);
                    totalMoneyAllwance =  totalMoneyAllwance + (moneyAllowance * OvertimeDetail.KONSTANTA_MONEY_ALLOWANCE);
                    totalFoodAngka = totalFoodAngka + food;
                    //totalPaidSalaryRupiah = totalPaidSalaryRupiah + (totIdxSalary * PstOvertime.KOSNTANTA_OVERTIME * valueGaji);
                    if (tmpLoop > listOvertimeSummary.size()) {
                        //countRow++;
                        //row = sheet.createRow((short) (countRow));
                        row = sheet.createRow((short) (countRow));
                            
                          for(int idx=0;idx<17;idx++){
                            cell = row.createCell((short) idx);
                            cell.setCellValue("");
                            cell.setCellStyle(style2);
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

//                        cell = row.createCell((short) 11);
//                        cell.setCellValue(totalIdxOt);
//                        cell.setCellStyle(formatterFloat);

                        cell = row.createCell((short) 11);
                        cell.setCellValue(totalPaidByDPAngka);//tot by DP
                        cell.setCellStyle(styleFillBagroundRedFloat);

                        cell = row.createCell((short) 12);//tot by salary
                        cell.setCellValue(totalPaidBySalary);
                        //cell.setCellStyle(formatterFloat);
                         cell.setCellStyle(styleFillBagroundRedRp);

                        cell = row.createCell((short) 13);
                        cell.setCellValue(totalMoneyAllwance);
                        //cell.setCellStyle(formatterFloat);
                         cell.setCellStyle(styleFillBagroundRedRp);

                        cell = row.createCell((short) 14);
                        cell.setCellValue(totalFoodAngka);
                        cell.setCellStyle(styleFillBagroundRedFloat);
                        
                        //update by satrya 2014-02-11
                        cell = row.createCell((short) 15);
                        cell.setCellValue("");
                        cell.setCellStyle(styleFillBagroundRedFloat);

                        totalPaidSalaryRupiah = totalPaidBySalary + totalMoneyAllwance;
                        cell = row.createCell((short) 16);
                        cell.setCellValue(totalPaidSalaryRupiah);
                        //cell.setCellStyle(formatterFloat);
                         cell.setCellStyle(styleFillBagroundRedRp);
                    }



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
