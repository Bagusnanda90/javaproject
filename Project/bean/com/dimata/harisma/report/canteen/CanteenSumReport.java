/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.canteen;

import com.dimata.harisma.entity.attendance.PstPresence;
import com.dimata.harisma.entity.canteen.CanteenSchedule;
import com.dimata.harisma.entity.canteen.PstCanteenSchedule;
import com.dimata.harisma.entity.canteen.SumReportDepartment;
import com.dimata.harisma.entity.canteen.SumReportParameter;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.session.canteen.SessCanteenVisitation;
import com.dimata.harisma.session.leave.RepLevDepartment;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook; import org.apache.poi.hssf.util.*;

/**
 *
 * @author Roy Andika
 */
public class CanteenSumReport extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    /** Destroys the servlet.
     */
    public void destroy() {
    }

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /** Returns a short description of the servlet.
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
        HSSFSheet sheet = wb.createSheet("Report Canteen Summary");

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

        Vector listVisitation = new Vector();
        Vector listVisitationAccess = new Vector();

        HttpSession sess = request.getSession(true);

        SumReportParameter sumReportParameter = new SumReportParameter();

        try {

            //listRepCanteenSummary = (Vector)sess.getValue("SUMMARY_CANTEEN_DEPARTMEN");
            sumReportParameter = (SumReportParameter) sess.getValue("SUMMARY_CANTEEN_DEPARTMENT_PARAMETER");

        } catch (Exception e) {
            System.out.println("EXCEPTION " + e.toString());
        }

        listVisitation = SessCanteenVisitation.getSummaryReportDepartment(sumReportParameter.getStartDt(), sumReportParameter.getEndDt(), sumReportParameter.getSchIdx());
        listVisitationAccess = SessCanteenVisitation.getSummaryReportDepartmentAccess(sumReportParameter.getStartDt(), sumReportParameter.getEndDt(), sumReportParameter.getSchIdx());

        if ((listVisitation != null && listVisitation.size() > 0) || listVisitationAccess != null && listVisitationAccess.size() > 0) {

            //Vector listVisitation = new Vector();

            Vector canteenSchedule = new Vector();
            String order = PstCanteenSchedule.fieldNames[PstCanteenSchedule.FLD_CODE] + " ASC";
            canteenSchedule = PstCanteenSchedule.list(0, 0, "", order);

            //listVisitation = (Vector) listRepCanteenSummary.get(0);

            HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell = row.createCell((short) 0);

            /* Ditambahkan 3 karena kolom sebelumnya sudah ada untuk  "DIVISION", "DEPARTMENT", "SCHEDULE" */
            int dateDiff = PstPresence.DATEDIFF(sumReportParameter.getEndDt(), sumReportParameter.getStartDt());
            int lengtHead = dateDiff + 4;
            int lengthDate = dateDiff + 1;

            String[] tableTitle = {
                "nikko Bali Resort & Spa",
                "MEAL COUPONS ISSUED BASED ON THE SCHEDULE",
                "Date : " + Formater.formatDate(sumReportParameter.getStartDt(), "dd MMMM yyyy") + " - " + Formater.formatDate(sumReportParameter.getEndDt(), "dd MMMM yyyy")
            };

            String[] tableSubTitle = {};

            String[] tableHeader = new String[dateDiff + 7];

            tableHeader[0] = "DIVISION";
            tableHeader[1] = "DEPARTMENT";
            tableHeader[2] = "SCHEDULE";
            tableHeader[3] = "NOMINAL";
            int countMxData = 4;

            Date dtProces = sumReportParameter.getStartDt();

            while (countMxData <= lengtHead) {

                tableHeader[countMxData] = "" + dtProces.getDate();
                Date nextProcess = (Date) dtProces.clone();
                nextProcess.setDate(nextProcess.getDate() + 1);
                dtProces = nextProcess;
                countMxData++;

            }

            tableHeader[countMxData] = "COVER";
            tableHeader[countMxData + 1] = "AMOUNT";

            /**
             *@DESC     :UNTUK COUNT ROW
             */
            int countRow = 0;

            /**
             * @DESC    : TITTLE
             */
            for (int k = 0; k < tableTitle.length; k++) {
                row = sheet.createRow((short) (countRow));
                countRow++;
                cell = row.createCell((short) 0);
                cell.setCellValue(tableTitle[k]);
                cell.setCellStyle(styleTitle);
            }

            /**
             * @DESC    : SUB TITTLE
             */
            for (int k = 0; k < tableSubTitle.length; k++) {
                row = sheet.createRow((short) (countRow));
                countRow++;
                cell = row.createCell((short) 0);
                cell.setCellValue(tableSubTitle[k]);
                cell.setCellStyle(styleSubTitle);
            }

            /**
             * @DESC    : HEADER
             */
            row = sheet.createRow((short) (countRow));
            countRow++;
            for (int k = 0; k < tableHeader.length; k++) {
                cell = row.createCell((short) k);
                cell.setCellValue(tableHeader[k]);
                cell.setCellStyle(styleHeader);
            }

            Vector listDepat = new Vector();

            String orderDpt = PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID];

            try {
                listDepat = PstDepartment.list(0, 0, "", orderDpt);
            } catch (Exception E) {
                System.out.println("[exception] " + E.toString());
            }


            if (listDepat.size() > 0 || listVisitationAccess.size() > 0) { /* Looping sebanyak department */

                String frmCurrency = "#,###"; /* Formaat untuk nominal*/

                long divId = 0;
                long depId = 0;

                int sumTotal[] = new int[lengthDate];
                double sumTotalAll = 0;

                for (int idxDpt = 0; idxDpt < listDepat.size(); idxDpt++) {

                    Department department = (Department) listDepat.get(idxDpt);

                    boolean dept = false;

                    int sumDt[] = new int[lengthDate];

                    double sumAmount = 0;

                    int max = 0;

                    for (int idxCnt = 0; idxCnt < canteenSchedule.size(); idxCnt++) { /* looping sebanyak canteen schedule */

                        if (sumReportParameter.getSchIdx()[idxCnt].compareTo("1") == 0) {

                            int collPos = 0;

                            /**
                             * @DESC    : CREATE NEW ROW
                             */
                            row = sheet.createRow((short) (countRow));
                            countRow++;
                            dept = true;

                            if (divId == 0 || divId != department.getDivisionId()) {

                                Division division = new Division();

                                try {

                                    division = (Division) PstDivision.fetchExc(department.getDivisionId());

                                    /**
                                     * @DESC    : DIVISION
                                     */
                                    cell = row.createCell((short) collPos);
                                    cell.setCellValue(String.valueOf(division.getDivision()));
                                    cell.setCellStyle(styleValue);
                                    collPos++;

                                } catch (Exception E) {
                                    System.out.println("[exception] " + E.toString());
                                }

                            } else {

                                cell = row.createCell((short) collPos);
                                cell.setCellValue(String.valueOf(""));
                                cell.setCellStyle(styleValue);
                                collPos++;

                            }

                            if (depId == 0 || depId != department.getOID()) {

                                /**
                                 * @DESC    : DEPARTMENT
                                 */
                                cell = row.createCell((short) collPos);
                                cell.setCellValue(String.valueOf(department.getDepartment()));
                                cell.setCellStyle(styleValue);
                                collPos++;

                            } else {

                                cell = row.createCell((short) collPos);
                                cell.setCellValue(String.valueOf(""));
                                cell.setCellStyle(styleValue);
                                collPos++;

                            }

                            double nominl = sumReportParameter.getNominal()[idxCnt];

                            int sumVisitation = 0;

                            CanteenSchedule cntSch = (CanteenSchedule) canteenSchedule.get(idxCnt);

                            /**
                             * @DESC    : NAMA CANTEEN SCHEDULE
                             */
                            cell = row.createCell((short) collPos);
                            cell.setCellValue(String.valueOf(cntSch.getSName()));
                            cell.setCellStyle(styleValue);
                            collPos++;

                            /**
                             * @DESC    : NOMINAL UNTUK CANTEEN SCHEDULE
                             */
                            cell = row.createCell((short) collPos);
                            cell.setCellValue(String.valueOf(nominl));
                            cell.setCellStyle(styleValue);
                            collPos++;

                            Vector visitation = (Vector) listVisitation.get(idxCnt);

                            Date ProcsDt = sumReportParameter.getStartDt();

                            countMxData = 0;

                            while (countMxData <= dateDiff) {

                                SumReportDepartment sumReportDepartment = null;
                                boolean found = false;
                                boolean DepFound = false;
                                int iDt = ProcsDt.getDate();

                                for (int idSum = 0; idSum < visitation.size(); idSum++) {

                                    sumReportDepartment = (SumReportDepartment) visitation.get(idSum);

                                    if (department.getOID() == sumReportDepartment.getDepartmentId()) {
                                        DepFound = true;
                                        if (iDt == sumReportDepartment.getCanteenVisitation().getDate()) {
                                            visitation.remove(idSum);
                                            found = true;
                                            break;
                                        }
                                    } else {

                                        if (DepFound) {
                                            break;
                                        }
                                    }

                                }

                                if (found) {

                                    cell = row.createCell((short) collPos);
                                    cell.setCellValue(String.valueOf(Formater.formatNumber(sumReportDepartment.getSummary(), frmCurrency)));
                                    cell.setCellStyle(styleValue);
                                    collPos++;

                                    sumDt[countMxData] = sumDt[countMxData] + sumReportDepartment.getSummary();
                                    sumVisitation = sumVisitation + sumReportDepartment.getSummary();
                                    sumTotal[countMxData] = sumTotal[countMxData] + sumReportDepartment.getSummary();

                                } else {

                                    cell = row.createCell((short) collPos);
                                    cell.setCellValue(String.valueOf(0));
                                    cell.setCellStyle(styleValue);
                                    collPos++;

                                    sumDt[countMxData] = sumDt[countMxData] + 0;
                                    sumTotal[countMxData] = sumTotal[countMxData] + 0;
                                }

                                countMxData++;
                                Date nextProcess = (Date) ProcsDt.clone();
                                nextProcess.setDate(nextProcess.getDate() + 1);
                                ProcsDt = nextProcess;

                            }

                            cell = row.createCell((short) collPos);
                            cell.setCellValue(String.valueOf(sumVisitation));
                            cell.setCellStyle(styleValue);
                            collPos++;

                            double sumTot = Double.parseDouble("" + sumVisitation) * nominl;
                            sumAmount = sumAmount + sumTot;
                            sumTotalAll = sumTotalAll + sumTot;

                            cell = row.createCell((short) collPos);
                            cell.setCellValue(String.valueOf(Formater.formatNumber(sumTot, frmCurrency)));
                            cell.setCellStyle(styleValue);
                            collPos++;

                        }

                        if (dept) {
                            divId = department.getDivisionId();
                            depId = department.getOID();
                        }
                        max++;
                    }


                    //Kondisi untuk yang di luar schedule
                    if (sumReportParameter.getSchIdx()[max].compareTo("1") == 0) {

                        //Vector rowx = new Vector();
                        int collPos = 0;

                        /**
                         * @DESC    : CREATE NEW ROW
                         */
                        row = sheet.createRow((short) (countRow));
                        countRow++;
                        dept = true;


                        if (divId == 0 || divId != department.getDivisionId()) {

                            Division division = new Division();

                            try {

                                division = (Division) PstDivision.fetchExc(department.getDivisionId());
                                cell = row.createCell((short) collPos);
                                cell.setCellValue(String.valueOf(division.getDivision()));
                                cell.setCellStyle(styleValue);
                                collPos++;

                            } catch (Exception E) {
                                System.out.println("[exception] " + E.toString());
                            }

                        } else {

                            cell = row.createCell((short) collPos);
                            cell.setCellValue("");
                            cell.setCellStyle(styleValue);
                            collPos++;

                        }

                        if (depId == 0 || depId != department.getOID()) {

                            cell = row.createCell((short) collPos);
                            cell.setCellValue(String.valueOf(department.getDepartment()));
                            cell.setCellStyle(styleValue);
                            collPos++;

                        } else {
                            cell = row.createCell((short) collPos);
                            cell.setCellValue("");
                            cell.setCellStyle(styleValue);
                            collPos++;
                        }

                        double nominl = sumReportParameter.getNominal()[max];

                        int sumVisitation = 0;

                        cell = row.createCell((short) collPos);
                        cell.setCellValue("SPLIT");
                        cell.setCellStyle(styleValue);
                        collPos++;

                        cell = row.createCell((short) collPos);
                        cell.setCellValue(String.valueOf(nominl));
                        cell.setCellStyle(styleValue);
                        collPos++;

                        Vector visitation = (Vector) listVisitation.get(max);

                        Date ProcsDt = sumReportParameter.getStartDt();

                        countMxData = 0;

                        while (countMxData <= dateDiff) {

                            SumReportDepartment sumReportDepartment = null;
                            boolean found = false;
                            boolean DepFound = false;
                            int iDt = ProcsDt.getDate();

                            for (int idSum = 0; idSum < visitation.size(); idSum++) {

                                sumReportDepartment = (SumReportDepartment) visitation.get(idSum);

                                if (department.getOID() == sumReportDepartment.getDepartmentId()) {
                                    DepFound = true;
                                    if (iDt == sumReportDepartment.getCanteenVisitation().getDate()) {
                                        visitation.remove(idSum);
                                        found = true;
                                        break;
                                    }
                                } else {

                                    if (DepFound) {
                                        break;
                                    }
                                }

                            }

                            if (found) {

                                cell = row.createCell((short) collPos);
                                cell.setCellValue(String.valueOf(Formater.formatNumber(sumReportDepartment.getSummary(), frmCurrency)));
                                cell.setCellStyle(styleValue);
                                collPos++;

                                sumDt[countMxData] = sumDt[countMxData] + sumReportDepartment.getSummary();
                                sumVisitation = sumVisitation + sumReportDepartment.getSummary();
                                sumTotal[countMxData] = sumTotal[countMxData] + sumReportDepartment.getSummary();

                            } else {

                                cell = row.createCell((short) collPos);
                                cell.setCellValue(String.valueOf(0));
                                cell.setCellStyle(styleValue);
                                collPos++;

                                sumDt[countMxData] = sumDt[countMxData] + 0;
                                sumTotal[countMxData] = sumTotal[countMxData] + 0;
                            }

                            countMxData++;
                            Date nextProcess = (Date) ProcsDt.clone();
                            nextProcess.setDate(nextProcess.getDate() + 1);
                            ProcsDt = nextProcess;
                        }

                        cell = row.createCell((short) collPos);
                        cell.setCellValue(String.valueOf(sumVisitation));
                        cell.setCellStyle(styleValue);
                        collPos++;

                        double sumTot = Double.parseDouble("" + sumVisitation) * nominl;
                        sumAmount = sumAmount + sumTot;
                        sumTotalAll = sumTotalAll + sumTot;

                        cell = row.createCell((short) collPos);
                        cell.setCellValue(String.valueOf(Formater.formatNumber(sumTot, frmCurrency)));
                        cell.setCellStyle(styleValue);
                        collPos++;

                    }

                    int collPos = 0;

                    /**
                     * @DESC    : CREATE NEW ROW
                     */
                    row = sheet.createRow((short) (countRow));
                    countRow++;

                    cell = row.createCell((short) collPos);
                    cell.setCellValue("");
                    cell.setCellStyle(styleValue);
                    collPos++;

                    cell = row.createCell((short) collPos);
                    cell.setCellValue("TOTAL");
                    cell.setCellStyle(styleValue);
                    collPos++;

                    cell = row.createCell((short) collPos);
                    cell.setCellValue("");
                    cell.setCellStyle(styleValue);
                    collPos++;

                    cell = row.createCell((short) collPos);
                    cell.setCellValue("");
                    cell.setCellStyle(styleValue);
                    collPos++;

                    int tot = 0;
                    for (int iDt = 0; iDt < lengthDate; iDt++) {

                        cell = row.createCell((short) collPos);
                        cell.setCellValue(String.valueOf(sumDt[iDt]));
                        cell.setCellStyle(styleValue);
                        collPos++;

                        tot = tot + sumDt[iDt];

                    }

                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(Formater.formatNumber(tot, frmCurrency)));
                    cell.setCellStyle(styleValue);
                    collPos++;

                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(Formater.formatNumber(sumAmount, frmCurrency)));
                    cell.setCellStyle(styleValue);
                    collPos++;


                    collPos = 0;

                    /**
                     * @DESC    : CREATE NEW ROW
                     */
                    row = sheet.createRow((short) (countRow));
                    countRow++;

                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(""));
                    cell.setCellStyle(styleValue);
                    collPos++;

                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(""));
                    cell.setCellStyle(styleValue);
                    collPos++;

                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(""));
                    cell.setCellStyle(styleValue);
                    collPos++;

                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(""));
                    cell.setCellStyle(styleValue);
                    collPos++;

                    for (int iDtx = 0; iDtx < lengthDate; iDtx++) {

                        cell = row.createCell((short) collPos);
                        cell.setCellValue(String.valueOf(""));
                        cell.setCellStyle(styleValue);
                        collPos++;

                    }

                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(""));
                    cell.setCellStyle(styleValue);
                    collPos++;

                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(""));
                    cell.setCellStyle(styleValue);
                    collPos++;

                    divId = department.getDivisionId();
                    depId = department.getOID();

                }

                /* Untuk yang database access */
                if (listVisitationAccess != null && listVisitationAccess.size() > 0) {

                    int idxDiv = 0;
                    int idxDep = 0;

                    int sumDt[] = new int[lengthDate];

                    double sumAmount = 0;

                    int maxAccess = 0;

                    for (int idxCnt = 0; idxCnt < canteenSchedule.size(); idxCnt++) { /* looping sebanyak canteen schedule */

                        if (sumReportParameter.getSchIdx()[idxCnt].compareTo("1") == 0) {
                            maxAccess++;
                            int collPos = 0;

                            /**
                             * @DESC    : CREATE NEW ROW
                             */
                            row = sheet.createRow((short) (countRow));
                            countRow++;

                            if (idxDiv == 0) {

                                cell = row.createCell((short) collPos);
                                cell.setCellValue(String.valueOf("Unassigned"));
                                cell.setCellStyle(styleValue);
                                collPos++;

                            } else {

                                cell = row.createCell((short) collPos);
                                cell.setCellValue(String.valueOf(""));
                                cell.setCellStyle(styleValue);
                                collPos++;
                            }

                            if (idxDep == 0) {
                                cell = row.createCell((short) collPos);
                                cell.setCellValue(String.valueOf("Unassigned"));
                                cell.setCellStyle(styleValue);
                                collPos++;
                            } else {
                                cell = row.createCell((short) collPos);
                                cell.setCellValue(String.valueOf(""));
                                cell.setCellStyle(styleValue);
                                collPos++;
                            }

                            double nominl = sumReportParameter.getNominal()[idxCnt];

                            int sumVisitation = 0;

                            CanteenSchedule cntSch = (CanteenSchedule) canteenSchedule.get(idxCnt);

                            /**
                             * @DESC    : NAMA CANTEEN SCHEDULE
                             */
                            cell = row.createCell((short) collPos);
                            cell.setCellValue(String.valueOf(cntSch.getSName()));
                            cell.setCellStyle(styleValue);
                            collPos++;

                            /**
                             * @DESC    : NOMINAL UNTUK CANTEEN SCHEDULE
                             */
                            cell = row.createCell((short) collPos);
                            cell.setCellValue(String.valueOf(nominl));
                            cell.setCellStyle(styleValue);
                            collPos++;

                            Vector visitation = (Vector) listVisitationAccess.get(idxCnt);

                            Date ProcsDt = sumReportParameter.getStartDt();

                            countMxData = 0;

                            while (countMxData <= dateDiff) {

                                SumReportDepartment sumReportDepartment = null;
                                boolean found = false;
                                int iDt = ProcsDt.getDate();

                                for (int idSum = 0; idSum < visitation.size(); idSum++) {

                                    sumReportDepartment = (SumReportDepartment) visitation.get(idSum);

                                    if (iDt == sumReportDepartment.getCanteenVisitation().getDate()) {
                                        visitation.remove(idSum);
                                        found = true;
                                        break;
                                    }
                                }

                                if (found) {
                                    cell = row.createCell((short) collPos);
                                    cell.setCellValue(String.valueOf(Formater.formatNumber(sumReportDepartment.getSummary(), frmCurrency)));
                                    cell.setCellStyle(styleValue);
                                    collPos++;
                                    sumDt[countMxData] = sumDt[countMxData] + sumReportDepartment.getSummary();
                                    sumVisitation = sumVisitation + sumReportDepartment.getSummary();
                                    sumTotal[countMxData] = sumTotal[countMxData] + sumReportDepartment.getSummary();
                                } else {
                                    cell = row.createCell((short) collPos);
                                    cell.setCellValue(String.valueOf(0));
                                    cell.setCellStyle(styleValue);
                                    collPos++;
                                    sumDt[countMxData] = sumDt[countMxData] + 0;
                                    sumTotal[countMxData] = sumTotal[countMxData] + 0;
                                }

                                countMxData++;
                                Date nextProcess = (Date) ProcsDt.clone();
                                nextProcess.setDate(nextProcess.getDate() + 1);
                                ProcsDt = nextProcess;
                            }

                            cell = row.createCell((short) collPos);
                            cell.setCellValue(String.valueOf(sumVisitation));
                            cell.setCellStyle(styleValue);
                            collPos++;

                            double sumTot = Double.parseDouble("" + sumVisitation) * nominl;
                            sumAmount = sumAmount + sumTot;
                            sumTotalAll = sumTotalAll + sumTot;

                            cell = row.createCell((short) collPos);
                            cell.setCellValue(String.valueOf(Formater.formatNumber(sumTot, frmCurrency)));
                            cell.setCellStyle(styleValue);
                            collPos++;
                            idxDiv++;
                            idxDep++;
                        }

                    }


                    //Kondisi untuk yang di luar schedule
                    if (sumReportParameter.getSchIdx()[maxAccess].compareTo("1") == 0) {

                        int collPos = 0;

                        /**
                         * @DESC    : CREATE NEW ROW
                         */
                        row = sheet.createRow((short) (countRow));
                        countRow++;

                        if (idxDiv == 0) {

                            cell = row.createCell((short) collPos);
                            cell.setCellValue(String.valueOf("Unasigned"));
                            cell.setCellStyle(styleValue);
                            collPos++;

                        } else {

                            cell = row.createCell((short) collPos);
                            cell.setCellValue(String.valueOf(""));
                            cell.setCellStyle(styleValue);
                            collPos++;
                        }

                        if (idxDep == 0) {
                            cell = row.createCell((short) collPos);
                            cell.setCellValue(String.valueOf("Unasigned"));
                            cell.setCellStyle(styleValue);
                            collPos++;
                        } else {
                            cell = row.createCell((short) collPos);
                            cell.setCellValue(String.valueOf(""));
                            cell.setCellStyle(styleValue);
                            collPos++;
                        }

                        double nominl = sumReportParameter.getNominal()[maxAccess];

                        int sumVisitation = 0;

                        cell = row.createCell((short) collPos);
                        cell.setCellValue(String.valueOf("SPLIT"));
                        cell.setCellStyle(styleValue);
                        collPos++;

                        cell = row.createCell((short) collPos);
                        cell.setCellValue(String.valueOf(nominl));
                        cell.setCellStyle(styleValue);
                        collPos++;

                        Vector visitation = (Vector) listVisitationAccess.get(maxAccess);

                        Date ProcsDt = sumReportParameter.getStartDt();

                        countMxData = 0;

                        while (countMxData <= dateDiff) {

                            SumReportDepartment sumReportDepartment = null;
                            boolean found = false;
                            int iDt = ProcsDt.getDate();

                            for (int idSum = 0; idSum < visitation.size(); idSum++) {

                                sumReportDepartment = (SumReportDepartment) visitation.get(idSum);

                                if (iDt == sumReportDepartment.getCanteenVisitation().getDate()) {
                                    visitation.remove(idSum);
                                    found = true;
                                    break;
                                }
                            }

                            if (found) {
                                cell = row.createCell((short) collPos);
                                cell.setCellValue(String.valueOf(Formater.formatNumber(sumReportDepartment.getSummary(), frmCurrency)));
                                cell.setCellStyle(styleValue);
                                collPos++;
                                sumDt[countMxData] = sumDt[countMxData] + sumReportDepartment.getSummary();
                                sumVisitation = sumVisitation + sumReportDepartment.getSummary();
                                sumTotal[countMxData] = sumTotal[countMxData] + sumReportDepartment.getSummary();
                            } else {
                                cell = row.createCell((short) collPos);
                                cell.setCellValue(String.valueOf(0));
                                cell.setCellStyle(styleValue);
                                collPos++;
                                sumDt[countMxData] = sumDt[countMxData] + 0;
                                sumTotal[countMxData] = sumTotal[countMxData] + 0;
                            }

                            countMxData++;
                            Date nextProcess = (Date) ProcsDt.clone();
                            nextProcess.setDate(nextProcess.getDate() + 1);
                            ProcsDt = nextProcess;
                        }

                        cell = row.createCell((short) collPos);
                        cell.setCellValue(String.valueOf(sumVisitation));
                        cell.setCellStyle(styleValue);
                        collPos++;
                        double sumTot = Double.parseDouble("" + sumVisitation) * nominl;
                        sumAmount = sumAmount + sumTot;
                        sumTotalAll = sumTotalAll + sumTot;

                        cell = row.createCell((short) collPos);
                        cell.setCellValue(String.valueOf(Formater.formatNumber(sumTot, frmCurrency)));
                        cell.setCellStyle(styleValue);
                        collPos++;

                    }

                    int collPos = 0;

                    /**
                     * @DESC    : CREATE NEW ROW
                     */
                    row = sheet.createRow((short) (countRow));
                    countRow++;

                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(""));
                    cell.setCellStyle(styleValue);
                    collPos++;

                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf("TOTAL"));
                    cell.setCellStyle(styleValue);
                    collPos++;

                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(""));
                    cell.setCellStyle(styleValue);
                    collPos++;

                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(""));
                    cell.setCellStyle(styleValue);
                    collPos++;

                    int tot = 0;
                    for (int iDt = 0; iDt < lengthDate; iDt++) {

                        cell = row.createCell((short) collPos);
                        cell.setCellValue(String.valueOf(sumDt[iDt]));
                        cell.setCellStyle(styleValue);
                        collPos++;
                        tot = tot + sumDt[iDt];

                    }

                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(Formater.formatNumber(tot, frmCurrency)));
                    cell.setCellStyle(styleValue);
                    collPos++;

                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(Formater.formatNumber(sumAmount, frmCurrency)));
                    cell.setCellStyle(styleValue);
                    collPos++;

                }

                int collPos = 0;

                /**
                 * @DESC    : CREATE NEW ROW
                 */
                row = sheet.createRow((short) (countRow));
                countRow++;

                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf("TOTAL ALL"));
                cell.setCellStyle(styleValue);
                collPos++;

                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(""));
                cell.setCellStyle(styleValue);
                collPos++;

                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(""));
                cell.setCellStyle(styleValue);
                collPos++;

                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(""));
                cell.setCellStyle(styleValue);
                collPos++;

                int totAll = 0;

                for (int iDtAll = 0; iDtAll < lengthDate; iDtAll++) {

                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(Formater.formatNumber(sumTotal[iDtAll], frmCurrency)));
                    cell.setCellStyle(styleValue);
                    collPos++;

                    totAll = totAll + sumTotal[iDtAll];

                }

                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(Formater.formatNumber(totAll, frmCurrency)));
                cell.setCellStyle(styleValue);
                collPos++;

                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(Formater.formatNumber(sumTotalAll, frmCurrency)));
                cell.setCellStyle(styleValue);
                collPos++;

                ServletOutputStream sos = response.getOutputStream();
                wb.write(sos);
                sos.close();

            }
        }
    }
}
