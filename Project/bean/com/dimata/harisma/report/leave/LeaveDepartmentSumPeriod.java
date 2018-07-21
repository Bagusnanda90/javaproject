/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.leave;
// package java

import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.entity.payroll.PayGeneral;
import com.dimata.harisma.entity.payroll.PstPayGeneral;
import com.dimata.harisma.entity.search.SrcLeaveAppAlClosing;
import com.dimata.harisma.session.leave.RepLevDepartment;
import com.dimata.harisma.session.leave.SessLeaveApplication;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

// package lowagie
import com.lowagie.text.*;


// package qdep
import com.dimata.util.*;
import com.dimata.qdep.form.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.*;

/**
 *
 * @author roy andika
 */
public class LeaveDepartmentSumPeriod extends HttpServlet {

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        System.out.println("---===| Excel Report |===---");

        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Report Period - Dp, AL, LL");

        //Style
        HSSFCellStyle styleTitle = wb.createCellStyle();
        styleTitle.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleTitle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleTitle.setFont(createFont(wb, 12, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        //update by devin 2014-04-07
        HSSFCellStyle styleFont = wb.createCellStyle();
        styleFont.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleFont.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleFont.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleFont.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleFont.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleFont.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleFont.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleFont.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleSubTitle = wb.createCellStyle();
        styleSubTitle.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleSubTitle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleSubTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleSubTitle.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleHeader = wb.createCellStyle();
        styleHeader.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleHeader.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        styleHeader.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleHeaderBig = wb.createCellStyle();
        styleHeaderBig.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleHeaderBig.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleHeaderBig.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        styleHeaderBig.setFont(createFont(wb, 12, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleFooter = wb.createCellStyle();
        styleFooter.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleFooter.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleFooter.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleFooter.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleFooter.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleFooter.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleFooter.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleFooter.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleValueBold = wb.createCellStyle();
        styleValueBold.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleValueBold.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        styleValueBold.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleValueBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleValueBold.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleValueBold.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleValueBold.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleValueBold.setFont(createFont(wb, 12, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleValue = wb.createCellStyle();
        styleValue.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleValue.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleValue.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleValue.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleValue.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleValue.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleValue.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleValue.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, false));

        /* Get Data From Session */
        Vector listRepLevDepartment = new Vector();

        HttpSession sess = request.getSession(true);

        try {

            listRepLevDepartment = (Vector) sess.getValue("LEAVE_DEPARTMENT_PERIOD_REPORT");

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }

        if (listRepLevDepartment != null && listRepLevDepartment.size() > 0) {

            HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell = row.createCell((short) 0);

            String[] tableTitle = {
                "",
                "MONTH END DETAIL REPORT - DP, AL, LL",
                "Month : " + Formater.formatDate(new Date(), "MMMM yyyy")
            };

            String[] tableSubTitle = {};

            String[] tableHeader = {
                //EMPLOYEE
                "NO", "DEPARTMENT", "EMPLOYEE" //DP
                , "QTY DP PREVIOUS", "TKN DP PREVIOUS", "DP EXPIRED PREVIOUS", "QTY DP", "TKN DP", "TKN DP EXPIRED", " BALANCE DP" //AL
                , "QTY AL PREVIOUS", "TKN AL PREVIOUS", "QTY AL", "QTY AL PREVIOUS", "TO BE TAKEN", "BALANCE AL" //LL
                , "QTY LL PREVIOUS", "TKN LL PREVIOUS", "LL EXPIRED PREVIOUS", "QTY LL", "TKN LL", "EXPIRED LL", "BALANCE LL"
            };

            /**
             * @DESC :UNTUK COUNT ROW
             */
            int countRow = 0;
            int colomAfterName = 21;

            /**
             * @DESC : TITTLE
             */
            for (int k = 0; k < tableTitle.length; k++) {
                row = sheet.createRow((short) (countRow));
                countRow++;
                cell = row.createCell((short) 0);
                cell.setCellValue(tableTitle[k]);
                cell.setCellStyle(styleTitle);
            }

            /**
             * @DESC : SUB TITTLE
             */
            for (int k = 0; k < tableSubTitle.length; k++) {
                row = sheet.createRow((short) (countRow));
                countRow++;
                cell = row.createCell((short) 0);
                cell.setCellValue(tableSubTitle[k]);
                cell.setCellStyle(styleSubTitle);
            }

            /**
             * @DESC : HEADER
             */
            row = sheet.createRow((short) (countRow));
            countRow++;
            for (int k = 0; k < tableHeader.length; k++) {
                cell = row.createCell((short) k);
                cell.setCellValue(tableHeader[k]);
                cell.setCellStyle(styleHeader);
            }


            int counterData = 1;

            int totEmployee = 0;

            float totPQtyDp = 0;
            float totPTknDP = 0;
            float totPExpDP = 0;
            float totQtyDp = 0;
            float totTknDp = 0;
            float totExpDp = 0;
            float totBDP = 0;

            float totPQtyAL = 0;
            float totPTknAl = 0;
            float totQtyAl = 0;
            float totTknAl = 0;
            float totToBeTaken = 0;
            float totBAL = 0;

            float totPQtyLL = 0;
            float totPTknLL = 0;
            float totPExpLL = 0;
            float totQtyLL = 0;
            float totTknLL = 0;
            float totExpLL = 0;
            float totBLL = 0;
            /**
             * int totPQtyDp = 0; int totPTknDP = 0; int totPExpDP = 0; int
             * totQtyDp = 0; int totTknDp = 0; int totExpDp = 0; int totBDP = 0;
             *
             * int totPQtyAL = 0; int totPTknAl = 0; int totQtyAl = 0; int
             * totTknAl = 0; int totBAL = 0;
             *
             * int totPQtyLL = 0; int totPTknLL = 0; int totPExpLL = 0; int
             * totQtyLL = 0; int totTknLL = 0; int totExpLL = 0; int totBLL = 0;
             */
            //UPDATE BY DEVIN 2014-04-04
            Vector listCompany = new Vector();
            listCompany = PstPayGeneral.listAll();

            if (listRepLevDepartment != null && listRepLevDepartment.size() > 0) {


                if (listRepLevDepartment.size() > 0) {
                    int totEmployeeCom = 0;

                    float totPQtyDpCom = 0;
                    float totPTknDPCom = 0;
                    float totPExpDPCom = 0;
                    float totQtyDpCom = 0;
                    float totTknDpCom = 0;
                    float totExpDpCom = 0;
                    float totBDPCom = 0;

                    float totPQtyALCom = 0;
                    float totPTknAlCom = 0;
                    float totQtyAlCom = 0;
                    float totTknAlCom = 0;
                    float totToBeTakenCom = 0;
                    float totBALCom = 0;

                    float totPQtyLLCom = 0;
                    float totPTknLLCom = 0;
                    float totPExpLLCom = 0;
                    float totQtyLLCom = 0;
                    float totTknLLCom = 0;
                    float totExpLLCom = 0;
                    float totBLLCom = 0;
                    if (listCompany != null && listCompany.size() > 0) {
                        for (int x = 0; x < listCompany.size(); x++) {
                            //update by devin 2014-04-29

                            counterData = 1;
                            int com = 0;
                            int collPos = 0;
                            PayGeneral payGeneral = (PayGeneral) listCompany.get(x);

                            Vector listDivision = PstDivision.list(0, 0, PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" + payGeneral.getOID(), PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
                            if (listDivision != null && listDivision.size() > 0) {
                                for (int div = 0; div < listDivision.size(); div++) {
                                    int vivi = 0;
                                    Division division = (Division) listDivision.get(div);

                                    Vector listDep = PstDepartment.list(0, 0, PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + division.getOID(), PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);

                                    //agar jika tidak ada department,division tetap ditampilkan
                                    if (listDep.size() == 0) {
                                        int collPosition = 0;
                                        cell = row.createCell((short) collPosition);
                                        cell.setCellValue(String.valueOf(counterData));
                                        cell.setCellStyle(styleValue);
                                        collPosition++;
                                        counterData++;

                                        cell = row.createCell((short) collPosition);
                                        cell.setCellValue(String.valueOf("   -" + division.getDivision()));
                                        cell.setCellStyle(styleFont);
                                        collPosition++;

                                        for (int y = 0; y < colomAfterName; y++) {
                                            cell = row.createCell((short) collPosition);
                                            cell.setCellValue(String.valueOf(""));
                                            cell.setCellStyle(styleValue);
                                            collPosition++;
                                        }
                                        vivi = 1;
                                        collPosition = 0;


                                    }
                                    if (listDep != null && listDep.size() > 0) {
                                        for (int i = 0; i < listDep.size(); i++) {
                                            int vDept = 0;

                                            Department department = (Department) listDep.get(i);

                                            //update by devin 2014-04-14


                                            RepLevDepartment repLevDepartment = new RepLevDepartment();
                                            try {
                                                repLevDepartment = (RepLevDepartment) listRepLevDepartment.get(i);
                                            } catch (Exception e) {
                                                System.out.println("Exception " + e.toString());
                                            }
                                            Vector resultByDept = new Vector();
                                            SrcLeaveAppAlClosing objSrcLeaveAppAlClosingDept = new SrcLeaveAppAlClosing();




                                            resultByDept = SessLeaveApplication.sumLeave_DepartmentGetSection(department.getOID(), objSrcLeaveAppAlClosingDept, repLevDepartment.getDateTo(), repLevDepartment.getDateFrom(), repLevDepartment.getRadioButton(), repLevDepartment.getOidPeriod());

                                            for (int f = 0; f < resultByDept.size(); f++) {
                                                RepLevDepartment repLevDepartmentDept = new RepLevDepartment();

                                                repLevDepartmentDept = (RepLevDepartment) resultByDept.get(f);
                                                if (com == 0) {

                                                    repLevDepartmentDept.setCountEmployee(0);
                                                    repLevDepartmentDept.setDpTknBeforeStartPeriod(0);

                                                    repLevDepartmentDept.setDpTknBeforeStartPeriod(0);
                                                    repLevDepartmentDept.setDpTknExpBeforeStartPeriod(0);
                                                    repLevDepartmentDept.setDpQtyCurrentPeriod(0);
                                                    repLevDepartmentDept.setDpTknCurrentPeriod(0);
                                                    repLevDepartmentDept.setDpTknExpiredCurrentPeriod(0);
                                                    repLevDepartmentDept.setDpQtyBeforeStartPeriod(0);

                                                    /* ==== ANNUAL LEAVE ===== */
                                                    repLevDepartmentDept.setAlQtyBeforeStartPeriod(0);
                                                    repLevDepartmentDept.setAlTknBeforeStartPeriod(0);
                                                    repLevDepartmentDept.setAlQtyCurrentPeriod(0);
                                                    repLevDepartmentDept.setAlTknCurrentPeriod(0);
                                                    //update by devin 2014-04-03
                                                    repLevDepartmentDept.setAlToBeTaken(0);

                                                    /* ===== LONG LEAVE ======= */
                                                    repLevDepartmentDept.setLLQtyBeforeStartPeriod(0);
                                                    repLevDepartmentDept.setLLTknBeforeStartPeriod(0);
                                                    repLevDepartmentDept.setLLTknExpBeforeStartPeriod(0);
                                                    repLevDepartmentDept.setLLQtyCurrentPeriod(0);
                                                    repLevDepartmentDept.setLLTknCurrentPeriod(0);
                                                    repLevDepartmentDept.setLLTknExpiredCurrentPeriod(0);
                                                }

                                                int collPoss = 0;




                                                row = sheet.createRow((short) (countRow));
                                                countRow++;
                                                /**
                                                 * @DESC : CREATE NEW ROW
                                                 */
                                                /**
                                                 * @DESC : NO
                                                 */
                                                /**
                                                 * @DESC : DEPARTMENT
                                                 */
                                                if (com == 0) {
                                                    if (x != 0 && x < listCompany.size()) {
                                                        // masukanlah nilai total tapi di awal tidak


                                                        int collPossTot = 0;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue("");
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;


                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue("TOTAL");
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totEmployeeCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totPQtyDpCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totPTknDPCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totPExpDPCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totQtyDpCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totTknDpCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totExpDpCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totBDPCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totPQtyALCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totPTknAlCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totQtyAlCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totTknAlCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totToBeTakenCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totBALCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totPQtyLLCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totPTknLLCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totPExpLLCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totQtyLLCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totTknLLCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totExpLLCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;

                                                        cell = row.createCell((short) collPossTot);
                                                        cell.setCellValue(String.valueOf(totBLLCom));
                                                        cell.setCellStyle(styleValue);
                                                        collPossTot++;
                                                        totEmployeeCom = 0;
                                                        totPQtyDpCom = 0;
                                                        totPTknDPCom = 0;
                                                        totPExpDPCom = 0;
                                                        totQtyDpCom = 0;
                                                        totTknDpCom = 0;
                                                        totExpDpCom = 0;
                                                        totBDPCom = 0;

                                                        totPQtyALCom = 0;
                                                        totPTknAlCom = 0;
                                                        totQtyAlCom = 0;
                                                        totTknAlCom = 0;
                                                        totToBeTakenCom = 0;
                                                        totBALCom = 0;

                                                        totPQtyLLCom = 0;
                                                        totPTknLLCom = 0;
                                                        totPExpLLCom = 0;
                                                        totQtyLLCom = 0;
                                                        totTknLLCom = 0;
                                                        totExpLLCom = 0;
                                                        totBLLCom = 0;
                                                    }
                                                    if (x != 0 && x < listCompany.size() - 1) {
                                                        row = sheet.createRow((short) (countRow));
                                                        countRow++;
                                                    }

                                                    cell = row.createCell((short) collPoss);
                                                    cell.setCellValue(String.valueOf(""));
                                                    cell.setCellStyle(styleValue);
                                                    collPoss++;

                                                    cell = row.createCell((short) collPoss);
                                                    cell.setCellValue(String.valueOf(payGeneral.getCompanyName()));
                                                    cell.setCellStyle(styleFont);

                                                    collPoss++;

                                                    for (int y = 0; y < colomAfterName; y++) {


                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(""));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;
                                                    }
                                                    com = 1;
                                                    collPoss = 0;
                                                    f = 0;
                                                    i = -1;
                                                    row = sheet.createRow((short) (countRow));
                                                    countRow++;

                                                }
                                                if (vivi == 0) {
                                                    cell = row.createCell((short) collPoss);
                                                    cell.setCellValue(String.valueOf(counterData));
                                                    cell.setCellStyle(styleValue);
                                                    collPoss++;
                                                    counterData++;

                                                    cell = row.createCell((short) collPoss);
                                                    cell.setCellValue(String.valueOf("   -" + division.getDivision()));
                                                    cell.setCellStyle(styleFont);
                                                    collPoss++;

                                                    for (int y = 0; y < colomAfterName; y++) {
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(""));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;
                                                    }
                                                    vivi = 1;
                                                    collPoss = 0;
                                                    f = 0;
                                                    row = sheet.createRow((short) (countRow));
                                                    countRow++;

                                                }

                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(""));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf("      " + department.getDepartment()));
                                                cell.setCellStyle(vDept == 0 ? styleFont : styleValue);
                                                collPoss++;

                                                /**
                                                 * @DESC : EMPLOYEE
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getCountEmployee()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totEmployee = totEmployee + repLevDepartmentDept.getCountEmployee();
                                                totEmployeeCom = totEmployeeCom + repLevDepartmentDept.getCountEmployee();/* tot employee */

                                                /**
                                                 * @DESC : DP QTY PREVIOUS
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getDpQtyBeforeStartPeriod()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totPQtyDp = totPQtyDp + repLevDepartmentDept.getDpQtyBeforeStartPeriod();
                                                totPQtyDpCom = totPQtyDpCom + repLevDepartmentDept.getDpQtyBeforeStartPeriod();

                                                /**
                                                 * @DESC : DP TKN PREVIOUS
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getDpTknBeforeStartPeriod()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totPTknDP = totPTknDP + repLevDepartmentDept.getDpTknBeforeStartPeriod();
                                                totPTknDPCom = totPTknDPCom + repLevDepartmentDept.getDpTknBeforeStartPeriod();

                                                /**
                                                 * @DESC : DP EXP PREVIOUS
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getDpTknExpBeforeStartPeriod()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totPExpDP = totPExpDP + repLevDepartmentDept.getDpTknExpBeforeStartPeriod();
                                                totPExpDPCom = totPExpDPCom + repLevDepartmentDept.getDpTknExpBeforeStartPeriod();

                                                /**
                                                 * @DESC : DP QTY
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getDpQtyCurrentPeriod()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totQtyDp = totQtyDp + repLevDepartmentDept.getDpQtyCurrentPeriod();
                                                totQtyDpCom = totQtyDpCom + repLevDepartmentDept.getDpQtyCurrentPeriod();

                                                /**
                                                 * @DESC : DP TKN
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getDpTknCurrentPeriod()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totTknDp = totTknDp + repLevDepartmentDept.getDpTknCurrentPeriod();
                                                totTknDpCom = totTknDpCom + repLevDepartmentDept.getDpTknCurrentPeriod();

                                                /**
                                                 * @DESC : DP EXP
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getDpTknExpiredCurrentPeriod()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totExpDp = totExpDp + repLevDepartmentDept.getDpTknExpiredCurrentPeriod();
                                                totExpDpCom = totExpDpCom + repLevDepartmentDept.getDpTknExpiredCurrentPeriod();

                                                float balDp = 0;
                                                // int balDp = 0;
                                                balDp = repLevDepartmentDept.getDpQtyBeforeStartPeriod() - repLevDepartmentDept.getDpTknBeforeStartPeriod()
                                                        - repLevDepartmentDept.getDpTknExpBeforeStartPeriod() + repLevDepartmentDept.getDpQtyCurrentPeriod()
                                                        - repLevDepartmentDept.getDpTknCurrentPeriod() - repLevDepartmentDept.getDpTknExpiredCurrentPeriod();

                                                /**
                                                 * @DESC : BALANCE DP
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(balDp));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totBDP = totBDP + balDp;
                                                totBDPCom = totBDPCom + balDp;


                                                /**
                                                 * @DESC : AL QTY PREVIOUS
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getAlQtyBeforeStartPeriod()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totPQtyAL = totPQtyAL + repLevDepartmentDept.getAlQtyBeforeStartPeriod();
                                                totPQtyALCom = totPQtyALCom + repLevDepartmentDept.getAlQtyBeforeStartPeriod();

                                                /**
                                                 * @DESC : TKN AL PREVIOUS
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getAlTknBeforeStartPeriod()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totPTknAl = totPTknAl + repLevDepartmentDept.getAlTknBeforeStartPeriod();
                                                totPTknAlCom = totPTknAlCom + repLevDepartmentDept.getAlTknBeforeStartPeriod();

                                                /**
                                                 * @DESC : AL QTY
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getAlQtyCurrentPeriod()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totQtyAl = totQtyAl + repLevDepartmentDept.getAlQtyCurrentPeriod();
                                                totQtyAlCom = totQtyAlCom + repLevDepartmentDept.getAlQtyCurrentPeriod();

                                                /**
                                                 * @DESC : TKN AL
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getAlTknCurrentPeriod()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totTknAl = totTknAl + repLevDepartmentDept.getAlTknCurrentPeriod();
                                                totTknAlCom = totTknAlCom + repLevDepartmentDept.getAlTknCurrentPeriod();

                                                float balAL = 0;
                                                //  int balAL = 0;
                                                balAL = repLevDepartmentDept.getAlQtyBeforeStartPeriod() - repLevDepartmentDept.getAlTknBeforeStartPeriod()
                                                        + repLevDepartmentDept.getAlQtyCurrentPeriod() - repLevDepartmentDept.getAlTknCurrentPeriod() - repLevDepartmentDept.getAlToBeTaken();

                                                /**
                                                 * @DESC : TKN AL
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getAlToBeTaken()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;
                                                totToBeTaken = totToBeTaken + repLevDepartmentDept.getAlToBeTaken();
                                                totToBeTakenCom = totToBeTakenCom + repLevDepartmentDept.getAlToBeTaken();


                                                //totTknAl = totTknAl + repLevDepartmentDept.getAlTknCurrentPeriod();   
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(balAL));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totBAL = totBAL + balAL;
                                                totBALCom = totBALCom + balAL;

                                                /**
                                                 * @DESC : LL QTY PREVIOUS
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getLLQtyBeforeStartPeriod()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totPQtyLL = totPQtyLL + repLevDepartmentDept.getLLQtyBeforeStartPeriod();
                                                totPQtyLLCom = totPQtyLLCom + repLevDepartmentDept.getLLQtyBeforeStartPeriod();

                                                /**
                                                 * @DESC : TKN LL PREVIOUS
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getLLTknBeforeStartPeriod()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totPTknLL = totPTknLL + repLevDepartmentDept.getLLTknBeforeStartPeriod();
                                                totPTknLLCom = totPTknLLCom + repLevDepartmentDept.getLLTknBeforeStartPeriod();

                                                /**
                                                 * @DESC : TKN LL EXP PREVIOUS
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getLLTknExpBeforeStartPeriod()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totPExpLL = totPExpLL + repLevDepartmentDept.getLLTknExpBeforeStartPeriod();
                                                totPExpLLCom = totPExpLLCom + repLevDepartmentDept.getLLTknExpBeforeStartPeriod();

                                                /**
                                                 * @DESC : QTY LL
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getLLQtyCurrentPeriod()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totQtyLL = totQtyLL + repLevDepartmentDept.getLLQtyCurrentPeriod();
                                                totQtyLLCom = totQtyLLCom + repLevDepartmentDept.getLLQtyCurrentPeriod();

                                                /**
                                                 * @DESC : TKN LL
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getLLTknCurrentPeriod()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totTknLL = totTknLL + repLevDepartmentDept.getLLTknCurrentPeriod();
                                                totTknLLCom = totTknLLCom + repLevDepartmentDept.getLLTknCurrentPeriod();

                                                /**
                                                 * @DESC : TKN LL EXP
                                                 */
                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(repLevDepartmentDept.getLLTknExpiredCurrentPeriod()));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totExpLL = totExpLL + repLevDepartmentDept.getLLTknExpiredCurrentPeriod();
                                                totExpLLCom = totExpLLCom + repLevDepartmentDept.getLLTknExpiredCurrentPeriod();

                                                /**
                                                 * @DESC : BAL LL
                                                 */
                                                float bLL = 0;
                                                //int bLL = 0;
                                                bLL = repLevDepartmentDept.getLLQtyBeforeStartPeriod() - repLevDepartmentDept.getLLTknBeforeStartPeriod()
                                                        - repLevDepartmentDept.getLLTknExpBeforeStartPeriod() + repLevDepartmentDept.getLLQtyCurrentPeriod()
                                                        - repLevDepartmentDept.getLLTknCurrentPeriod() - repLevDepartmentDept.getLLTknExpiredCurrentPeriod();

                                                cell = row.createCell((short) collPoss);
                                                cell.setCellValue(String.valueOf(bLL));
                                                cell.setCellStyle(styleValue);
                                                collPoss++;

                                                totBLL = totBLL + bLL;
                                                totBLLCom = totBLLCom + bLL;




                                            }


                                            Vector listSec = PstSection.list(0, 0, PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + department.getOID(), PstSection.fieldNames[PstSection.FLD_SECTION]);
                                            //update by devin 2014-04-14
                                            if (listSec != null && listSec.size() > 0) {

                                                for (int c = 0; c < listSec.size(); c++) {
                                                    RepLevDepartment repLevDepartmentSec = new RepLevDepartment();
                                                    try {
                                                        repLevDepartmentSec = (RepLevDepartment) listRepLevDepartment.get(c);
                                                    } catch (Exception e) {
                                                        System.out.println("Exception " + e.toString());
                                                    }
                                                    Vector resultt = new Vector();
                                                    SrcLeaveAppAlClosing objSrcLeaveAppAlClosing = new SrcLeaveAppAlClosing();

                                                    Section section = (Section) listSec.get(c);



                                                    resultt = SessLeaveApplication.sumLeave_Department2(section.getOID(), objSrcLeaveAppAlClosing, repLevDepartmentSec.getDateTo(), repLevDepartmentSec.getDateFrom(), repLevDepartmentSec.getRadioButton(), repLevDepartmentSec.getOidPeriod());


                                                    for (int f = 0; f < resultt.size(); f++) {

                                                        RepLevDepartment repLevDepartmentt = new RepLevDepartment();

                                                        repLevDepartmentt = (RepLevDepartment) resultt.get(f);
                                                        if (com == 0) {

                                                            repLevDepartmentt.setCountEmployee(0);
                                                            repLevDepartmentt.setDpTknBeforeStartPeriod(0);

                                                            repLevDepartmentt.setDpTknBeforeStartPeriod(0);
                                                            repLevDepartmentt.setDpTknExpBeforeStartPeriod(0);
                                                            repLevDepartmentt.setDpQtyCurrentPeriod(0);
                                                            repLevDepartmentt.setDpTknCurrentPeriod(0);
                                                            repLevDepartmentt.setDpTknExpiredCurrentPeriod(0);
                                                            repLevDepartmentt.setDpQtyBeforeStartPeriod(0);

                                                            /* ==== ANNUAL LEAVE ===== */
                                                            repLevDepartmentt.setAlQtyBeforeStartPeriod(0);
                                                            repLevDepartmentt.setAlTknBeforeStartPeriod(0);
                                                            repLevDepartmentt.setAlQtyCurrentPeriod(0);
                                                            repLevDepartmentt.setAlTknCurrentPeriod(0);
                                                            //update by devin 2014-04-03
                                                            repLevDepartmentt.setAlToBeTaken(0);

                                                            /* ===== LONG LEAVE ======= */
                                                            repLevDepartmentt.setLLQtyBeforeStartPeriod(0);
                                                            repLevDepartmentt.setLLTknBeforeStartPeriod(0);
                                                            repLevDepartmentt.setLLTknExpBeforeStartPeriod(0);
                                                            repLevDepartmentt.setLLQtyCurrentPeriod(0);
                                                            repLevDepartmentt.setLLTknCurrentPeriod(0);
                                                            repLevDepartmentt.setLLTknExpiredCurrentPeriod(0);
                                                        }

                                                        int collPoss = 0;



                                                        /**
                                                         * @DESC : CREATE NEW
                                                         * ROW
                                                         */
                                                        row = sheet.createRow((short) (countRow));
                                                        countRow++;

                                                        /**
                                                         * @DESC : NO
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(""));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;


                                                        /**
                                                         * @DESC : DEPARTMENT
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf("         " + section.getSection()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        /**
                                                         * @DESC : EMPLOYEE
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getCountEmployee()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totEmployee = totEmployee + repLevDepartmentt.getCountEmployee(); /* tot employee */
                                                        totEmployeeCom = totEmployeeCom + repLevDepartmentt.getCountEmployee();
                                                        /**
                                                         * @DESC : DP QTY
                                                         * PREVIOUS
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getDpQtyBeforeStartPeriod()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totPQtyDp = totPQtyDp + repLevDepartmentt.getDpQtyBeforeStartPeriod();
                                                        totPQtyDpCom = totPQtyDpCom + repLevDepartmentt.getDpQtyBeforeStartPeriod();
                                                        /**
                                                         * @DESC : DP TKN
                                                         * PREVIOUS
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getDpTknBeforeStartPeriod()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totPTknDP = totPTknDP + repLevDepartmentt.getDpTknBeforeStartPeriod();
                                                        totPTknDPCom = totPTknDPCom + repLevDepartmentt.getDpTknBeforeStartPeriod();
                                                        /**
                                                         * @DESC : DP EXP
                                                         * PREVIOUS
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getDpTknExpBeforeStartPeriod()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totPExpDP = totPExpDP + repLevDepartmentt.getDpTknExpBeforeStartPeriod();
                                                        totPExpDPCom = totPExpDPCom + repLevDepartmentt.getDpTknExpBeforeStartPeriod();
                                                        /**
                                                         * @DESC : DP QTY
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getDpQtyCurrentPeriod()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totQtyDp = totQtyDp + repLevDepartmentt.getDpQtyCurrentPeriod();
                                                        totQtyDpCom = totQtyDpCom + repLevDepartmentt.getDpQtyCurrentPeriod();
                                                        /**
                                                         * @DESC : DP TKN
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getDpTknCurrentPeriod()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totTknDp = totTknDp + repLevDepartmentt.getDpTknCurrentPeriod();
                                                        totTknDpCom = totTknDpCom + repLevDepartmentt.getDpTknCurrentPeriod();

                                                        /**
                                                         * @DESC : DP EXP
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getDpTknExpiredCurrentPeriod()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totExpDp = totExpDp + repLevDepartmentt.getDpTknExpiredCurrentPeriod();
                                                        totExpDpCom = totExpDpCom + repLevDepartmentt.getDpTknExpiredCurrentPeriod();
                                                        float balDp = 0;
                                                        // int balDp = 0;
                                                        balDp = repLevDepartmentt.getDpQtyBeforeStartPeriod() - repLevDepartmentt.getDpTknBeforeStartPeriod()
                                                                - repLevDepartmentt.getDpTknExpBeforeStartPeriod() + repLevDepartmentt.getDpQtyCurrentPeriod()
                                                                - repLevDepartmentt.getDpTknCurrentPeriod() - repLevDepartmentt.getDpTknExpiredCurrentPeriod();

                                                        /**
                                                         * @DESC : BALANCE DP
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(balDp));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totBDP = totBDP + balDp;
                                                        totBDPCom = totBDPCom + balDp;

                                                        /**
                                                         * @DESC : AL QTY
                                                         * PREVIOUS
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getAlQtyBeforeStartPeriod()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totPQtyAL = totPQtyAL + repLevDepartmentt.getAlQtyBeforeStartPeriod();
                                                        totPQtyALCom = totPQtyALCom + repLevDepartmentt.getAlQtyBeforeStartPeriod();
                                                        /**
                                                         * @DESC : TKN AL
                                                         * PREVIOUS
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getAlTknBeforeStartPeriod()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totPTknAl = totPTknAl + repLevDepartmentt.getAlTknBeforeStartPeriod();
                                                        totPTknAlCom = totPTknAlCom + repLevDepartmentt.getAlTknBeforeStartPeriod();
                                                        /**
                                                         * @DESC : AL QTY
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getAlQtyCurrentPeriod()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totQtyAl = totQtyAl + repLevDepartmentt.getAlQtyCurrentPeriod();
                                                        totQtyAlCom = totQtyAlCom + repLevDepartmentt.getAlQtyCurrentPeriod();
                                                        /**
                                                         * @DESC : TKN AL
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getAlTknCurrentPeriod()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totTknAl = totTknAl + repLevDepartmentt.getAlTknCurrentPeriod();
                                                        totTknAlCom = totTknAlCom + repLevDepartmentt.getAlTknCurrentPeriod();
                                                        float balAL = 0;
                                                        //  int balAL = 0;
                                                        balAL = repLevDepartmentt.getAlQtyBeforeStartPeriod() - repLevDepartmentt.getAlTknBeforeStartPeriod()
                                                                + repLevDepartmentt.getAlQtyCurrentPeriod() - repLevDepartmentt.getAlTknCurrentPeriod() - repLevDepartmentt.getAlToBeTaken();

                                                        /**
                                                         * @DESC : TKN AL
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getAlToBeTaken()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;
                                                        totToBeTaken = totToBeTaken + repLevDepartmentt.getAlToBeTaken();
                                                        totToBeTakenCom = totToBeTakenCom + repLevDepartmentt.getAlToBeTaken();

                                                        //totTknAl = totTknAl + repLevDepartmentt.getAlTknCurrentPeriod();   
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(balAL));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totBAL = totBAL + balAL;

                                                        /**
                                                         * @DESC : LL QTY
                                                         * PREVIOUS
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getLLQtyBeforeStartPeriod()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totPQtyLL = totPQtyLL + repLevDepartmentt.getLLQtyBeforeStartPeriod();
                                                        totPQtyLLCom = totPQtyLLCom + repLevDepartmentt.getLLQtyBeforeStartPeriod();
                                                        /**
                                                         * @DESC : TKN LL
                                                         * PREVIOUS
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getLLTknBeforeStartPeriod()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totPTknLL = totPTknLL + repLevDepartmentt.getLLTknBeforeStartPeriod();
                                                        totPTknLLCom = totPTknLLCom + repLevDepartmentt.getLLTknBeforeStartPeriod();
                                                        /**
                                                         * @DESC : TKN LL EXP
                                                         * PREVIOUS
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getLLTknExpBeforeStartPeriod()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totPExpLL = totPExpLL + repLevDepartmentt.getLLTknExpBeforeStartPeriod();
                                                        totPExpLLCom = totPExpLLCom + repLevDepartmentt.getLLTknExpBeforeStartPeriod();

                                                        /**
                                                         * @DESC : QTY LL
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getLLQtyCurrentPeriod()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totQtyLL = totQtyLL + repLevDepartmentt.getLLQtyCurrentPeriod();
                                                        totQtyLLCom = totQtyLLCom + repLevDepartmentt.getLLQtyCurrentPeriod();
                                                        /**
                                                         * @DESC : TKN LL
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getLLTknCurrentPeriod()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totTknLL = totTknLL + repLevDepartmentt.getLLTknCurrentPeriod();
                                                        totTknLLCom = totTknLLCom + repLevDepartmentt.getLLTknCurrentPeriod();
                                                        /**
                                                         * @DESC : TKN LL EXP
                                                         */
                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(repLevDepartmentt.getLLTknExpiredCurrentPeriod()));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totExpLL = totExpLL + repLevDepartmentt.getLLTknExpiredCurrentPeriod();
                                                        totExpLLCom = totExpLLCom + repLevDepartmentt.getLLTknExpiredCurrentPeriod();
                                                        /**
                                                         * @DESC : BAL LL
                                                         */
                                                        float bLL = 0;
                                                        //int bLL = 0;
                                                        bLL = repLevDepartmentt.getLLQtyBeforeStartPeriod() - repLevDepartmentt.getLLTknBeforeStartPeriod()
                                                                - repLevDepartmentt.getLLTknExpBeforeStartPeriod() + repLevDepartmentt.getLLQtyCurrentPeriod()
                                                                - repLevDepartmentt.getLLTknCurrentPeriod() - repLevDepartmentt.getLLTknExpiredCurrentPeriod();

                                                        cell = row.createCell((short) collPoss);
                                                        cell.setCellValue(String.valueOf(bLL));
                                                        cell.setCellStyle(styleValue);
                                                        collPoss++;

                                                        totBLL = totBLL + bLL;
                                                        totBLLCom = totBLLCom + bLL;



                                                    }

                                                }
                                            }
                                        }//end loop department 
                                        if (x != 0 && x == listCompany.size() - 1) {
                                            //maka cetak total dan total all company
                                            row = sheet.createRow((short) (countRow));
                                            countRow++;

                                            int collPossTot = 0;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue("");
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;


                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue("TOTAL");
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totEmployeeCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totPQtyDpCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totPTknDPCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totPExpDPCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totQtyDpCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totTknDpCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totExpDpCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totBDPCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totPQtyALCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totPTknAlCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totQtyAlCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totTknAlCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totToBeTakenCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totBALCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totPQtyLLCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totPTknLLCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totPExpLLCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totQtyLLCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totTknLLCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totExpLLCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totBLLCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;



                                            row = sheet.createRow((short) (countRow));
                                            countRow++;
                                            int collPossCom = 0;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue("");
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue("TOTAL ALL COMPANY");
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totEmployee));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totPQtyDp));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totPTknDP));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totPExpDP));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totQtyDp));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totTknDp));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totExpDp));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totBDP));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totPQtyAL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totPTknAl));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totQtyAl));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totTknAl));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totToBeTaken));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totBAL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totPQtyLL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totPTknLL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totPExpLL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totQtyLL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totTknLL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totExpLL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totBLL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                        }
                                    }//end if department 

                                }//end loop division

                                //update by satrya 2014-04-29
                                if(x==0 && x == listCompany.size() - 1){
                                    row = sheet.createRow((short) (countRow));
                                            countRow++;

                                            int collPossTot = 0;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue("");
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;


                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue("TOTAL");
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totEmployeeCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totPQtyDpCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totPTknDPCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totPExpDPCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totQtyDpCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totTknDpCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totExpDpCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totBDPCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totPQtyALCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totPTknAlCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totQtyAlCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totTknAlCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totToBeTakenCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totBALCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totPQtyLLCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totPTknLLCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totPExpLLCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totQtyLLCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totTknLLCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totExpLLCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;

                                            cell = row.createCell((short) collPossTot);
                                            cell.setCellValue(String.valueOf(totBLLCom));
                                            cell.setCellStyle(styleValue);
                                            collPossTot++;



                                            row = sheet.createRow((short) (countRow));
                                            countRow++;
                                            int collPossCom = 0;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue("");
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue("TOTAL ALL COMPANY");
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totEmployee));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totPQtyDp));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totPTknDP));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totPExpDP));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totQtyDp));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totTknDp));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totExpDp));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totBDP));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totPQtyAL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totPTknAl));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totQtyAl));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totTknAl));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totToBeTaken));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totBAL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totPQtyLL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totPTknLL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totPExpLL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totQtyLL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totTknLL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totExpLL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;

                                            cell = row.createCell((short) collPossCom);
                                            cell.setCellValue(String.valueOf(totBLL));
                                            cell.setCellStyle(styleValue);
                                            collPossCom++;
                                }




                            }//end if division

                            
                        }//end company loop
                        


                    }



                }
            }
            ServletOutputStream sos = response.getOutputStream();
            wb.write(sos);
            sos.close();

        }
    }
}
