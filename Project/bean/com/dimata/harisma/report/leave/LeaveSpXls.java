/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.report.leave;

// package java

import com.dimata.harisma.entity.leave.ListSp;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.session.attendance.SessLeaveManagement;
import com.dimata.harisma.session.leave.RepItemLeaveAndDp;
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
public class LeaveSpXls extends HttpServlet{
    
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
        
        
        /* Get Data From Session */
        Vector listDetailLeave = new Vector();        
        HttpSession sess = request.getSession(true);
        
        try {               
            
            listDetailLeave = (Vector)sess.getValue("DETAIL_LEAVE_SP_REPORT");

        }catch(Exception e){
            System.out.println("EXCEPTION "+e.toString());
        } 
        
        Vector listSp = (Vector)listDetailLeave.get(0);
        
        /**
         * @Desc    : Untuk mengeluarkan value hasil result
         */
        if (listSp != null && listSp.size() > 0) {

            HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell = row.createCell((short) 0);

            String[] tableTitle = {
                "HARD ROCK HOTEL BALI",
                "MONTH END DETAIL REPORT - SPECIAL, UNPAID LEAVE",
                "Month : " + Formater.formatDate(new Date(), "MMMM yyyy")
            };

            String[] tableSubTitle = {};

            String[] tableHeader = {
                //EMPLOYEE
                "NO", "PAYROLL", "EMPLOYEE","DEPARTMENT" 
                //SP
                , "SP TAKEN", "WILL BE TAKEN"
                //UNPAID
                , "UNPAID TAKEN", "WILL TO BE TAKEN"
                
                
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
            
            int no = 1;
            float totalSpTkn = 0;
            float totalSpToBeTkn = 0;
            float totalUnpTkn = 0;
            float totalUnpToBeTkn = 0;
            
            for (int i = 0; i < listSp.size(); i++) {
                
                int collPos = 0;
                
                ListSp listSP = new ListSp();
                
                listSP = (ListSp)listSp.get(i);                
                
                Department department = new Department();
                
                try{
                    department = PstDepartment.fetchExc(listSP.getDepartmentId());
                }catch(Exception e){
                    System.out.println("EXCEPTION "+e.toString());
                }
                
                /**
                 * @DESC    : CREATE NEW ROW
                 */
                row = sheet.createRow((short) (countRow));
                countRow++;
                
                /**
                 * @DESC    : NO
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(no));
                cell.setCellStyle(styleValue);
                collPos++;
                no++;
                
                /**
                 * @DESC    : PAYROLL
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listSP.getEmployeeNum()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                /**
                 * @DESC    : EMLOYEE NAME                 
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listSP.getFullName()));
                cell.setCellStyle(styleValue);
                collPos++;        
                
                 /**
                 * @DESC    : DEPARTMENT NAME                 
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(department.getDepartment()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listSP.getTakenSp()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listSP.getToBeTakenSp()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listSP.getTakenUnp()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(listSP.getTobeTakenUnp()));
                cell.setCellStyle(styleValue);
                collPos++;                
                
                
                totalSpTkn = totalSpTkn + listSP.getTakenSp();
                totalSpToBeTkn = totalSpToBeTkn + listSP.getToBeTakenSp();
                totalUnpTkn = totalUnpTkn + listSP.getTakenUnp();
                totalUnpToBeTkn = totalUnpToBeTkn + listSP.getTobeTakenUnp();
                
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
            cell.setCellValue("");
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue("");
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalSpTkn));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalSpToBeTkn));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalUnpTkn));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalUnpToBeTkn));
            cell.setCellStyle(styleValue);
            collPos++;
                        
        }

        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    }

}
