/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.leave;

// package java
import com.dimata.harisma.entity.attendance.AlStockManagement;
import com.dimata.harisma.entity.attendance.LLStockManagement;
import com.dimata.harisma.session.attendance.SessLeaveManagement;
import com.dimata.harisma.session.leave.RepItemLeaveAndDp;
import com.dimata.harisma.session.leave.SessLeaveApp;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook; import org.apache.poi.hssf.util.*;

/**
 *
 * @author Tu Roy
 */
public class LeaveDpSumXls extends HttpServlet {
    

    /** Initializes the servlet.
     */
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
        HSSFSheet sheet = wb.createSheet("Month End Report - Dp, AL, LL");

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

        /**
         * @Desc    : GET VALUE SUM LEAVE DP STOCK
         */
        Vector listCurrStock = new Vector();
        listCurrStock = SessLeaveApp.listSumLeaveAndDPStock();

        /**
         * @Desc    : Untuk mengeluarkan value hasil result
         */
        if (listCurrStock != null && listCurrStock.size() > 0) {

            HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell = row.createCell((short) 0);

            String[] tableTitle = {
                "",
                "MONTH END REPORT - DP, AL, LL",
                "Month : " + Formater.formatDate(new Date(), "MMMM yyyy")
            };

            String[] tableSubTitle = {};

            String[] tableHeader = {
                //EMPLOYEE
                "NO", "DEPARTMENT", "NO. OF Employee" 
                //DP
                , "QTY DP", "TKN DP", "WILL BE TAKEN DP", "EXPIRED DP", "BALANCE DP" 
                //AL
                , "PREV PERIOD AL", "ENTITLE AL", "SUB TOTAL AL", "TAKEN AL", "WILL BE TAKEN AL", "BALANCE AL" 
                //LL
                , "PREV PERIOD LL", "ENTITLE LL", "SUB TOTAL LL", "TAKEN LL", "WILL BE TAKEN LL", "EXPIRED LL", "BALANCE LL"
            };

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

            /**
             * @DESC    : DATA
             */
            int counterData = 1;
            float totalEmp = 0;            
            float totalDPent = 0;
            float totalDPTaken = 0;
            float totalDPBlc = 0;
            
            float totalDp2BeTaken = 0;
            float totalDpExpired = 0;
            
            float totalALPrev = 0;
            float totalALent = 0;
            float totalALTotal = 0;
            float totalALTaken = 0;
            float totalAL2BeTaken = 0;
            float totalALBlc = 0;
            
            float totalLLPrev = 0;
            float totalLLent = 0;
            float totalLTotal = 0;
            float totalLLTaken = 0;
            float totalLL2BeTaken = 0;
            float totalLLExpired = 0;
            float totalLLBlc = 0;
            
            for (int i = 0; i < listCurrStock.size(); i++) {
                
                int collPos = 0;
                
                RepItemLeaveAndDp item = null;                
                item = (RepItemLeaveAndDp) listCurrStock.get(i);

                totalEmp =totalEmp + item.getEmpQty();
                
                /**
                 * @DESC    : CREATE NEW ROW
                 */
                row = sheet.createRow((short) (countRow));
                countRow++;
                
                /**
                 * @DESC    : NO
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(counterData));
                cell.setCellStyle(styleValue);
                collPos++;
                counterData++;
                
                /**
                 * @DESC    : DEPARTMENT
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(item.getDepName()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                /**
                 * @DESC    : TOTAL EMLOYEE                 
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(item.getEmpQty()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                
                /**
                 * @DESC    : DOWN PAYMENT
                 */                
                float dpBalance = 0;
                float totDpExpired = SessLeaveManagement.getTotExpDpStockByDepartment(item.getDepartmentOID());
                float totalDp2BeTake = SessLeaveApplication.getTotal2BeTakenDP(item.getDepartmentOID());
                dpBalance = item.getDPQty() - item.getDPTaken() - totalDp2BeTake - totDpExpired;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(item.getDPQty()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(item.getDPTaken()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(totalDp2BeTake));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(totDpExpired));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(dpBalance));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totalDPent =totalDPent+ item.getDPQty();
                totalDPTaken =totalDPTaken+ item.getDPTaken();                
                totalDp2BeTaken = totalDp2BeTaken + totalDp2BeTake;
                totalDpExpired = totalDpExpired + totDpExpired;
                totalDPBlc =totalDPBlc+ dpBalance; 
                
                /**
                 * @DESC    : ANNUAL LEAVE
                 */                
                AlStockManagement alStockManagement = new AlStockManagement();
                alStockManagement = SessLeaveApplication.getTotalPeriodAlAktif(item.getDepartmentOID());                
                float subTotal = alStockManagement.getPrevBalance() + alStockManagement.getAlQty();
                float balance = subTotal - alStockManagement.getQtyUsed() - alStockManagement.getALtoBeTaken();
                 
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(alStockManagement.getPrevBalance()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(alStockManagement.getAlQty()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(subTotal));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(alStockManagement.getQtyUsed()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(alStockManagement.getALtoBeTaken()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(balance));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totalALPrev =totalALPrev + alStockManagement.getPrevBalance();
                totalALent =totalALent+ alStockManagement.getAlQty();
                totalALTotal =totalALTotal + subTotal;
                totalALTaken =totalALTaken + alStockManagement.getQtyUsed();
                totalAL2BeTaken = totalAL2BeTaken + alStockManagement.getALtoBeTaken();
                totalALBlc =totalALBlc + balance;
                
                /**
                 * @LONG LEAVE
                 */
                
                LLStockManagement llStockManagement = new LLStockManagement();
                llStockManagement = SessLeaveApplication.getTotalPeriodLlAktif(item.getDepartmentOID());                
                float totalLL = llStockManagement.getPrevBalance() + llStockManagement.getLLQty();
                float llExp = SessLeaveApplication.totExpired(item.getDepartmentOID());
                float totalBalance = totalLL - llStockManagement.getQtyUsed() - llStockManagement.getToBeTaken() - llExp;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(llStockManagement.getPrevBalance()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(llStockManagement.getLLQty()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(totalLL));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(llStockManagement.getQtyUsed()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(llStockManagement.getToBeTaken()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(llExp));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(totalBalance));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totalLLPrev =totalLLPrev + llStockManagement.getPrevBalance();
                totalLLent =totalLLent + llStockManagement.getLLQty();
                totalLTotal =totalLTotal + totalLL;
                totalLLTaken =totalLLTaken + llStockManagement.getQtyUsed();
                totalLL2BeTaken = totalLL2BeTaken + llStockManagement.getToBeTaken();
                totalLLExpired = totalLLExpired + llExp;
                totalLLBlc =totalLLBlc + totalBalance;
                
            }            
            
            row = sheet.createRow((short) (countRow));
            
            int collPos = 0;
            cell = row.createCell((short) collPos);
            cell.setCellValue("");
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue("TOTAL");
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalEmp));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalDPent));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalDPTaken));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalDp2BeTaken));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalDpExpired));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalDPBlc));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalALPrev));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalALent));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalALTotal));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalALTaken));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalAL2BeTaken));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalALBlc));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalLLPrev));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalLLent));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalLTotal));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalLLTaken));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalLL2BeTaken));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalLLExpired));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalLLBlc));
            cell.setCellStyle(styleValue);
            collPos++;            
        }

        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    }
}
