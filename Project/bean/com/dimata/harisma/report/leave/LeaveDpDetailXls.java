/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.report.leave;

// package java

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
import com.dimata.system.entity.PstSystemProperty;
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
public class LeaveDpDetailXls extends HttpServlet {
    
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
            
            listDetailLeave = (Vector)sess.getValue("DETAIL_LEAVE_DP_REPORT");

        }catch(Exception e){
            System.out.println("EXCEPTION "+e.toString());
        } 
        
        Vector listDetailLev = (Vector)listDetailLeave.get(2);
        

        /**
         * @Desc    : GET VALUE SUM LEAVE DP STOCK
         */
        //Vector listCurrStock = new Vector();
        //listCurrStock = SessLeaveApp.listSumLeaveAndDPStock();
        
        /**
         * @Desc    : Untuk mengeluarkan value hasil result
         */
        //update by satrya 2012-12-22
        String companyName=" Select CompanyName ";
          try{
            companyName = String.valueOf(PstSystemProperty.getValueByName("PRINT_HEADER"));
        }catch(Exception ex){
            companyName=" Select CompanyName "; 
        }    
        if (listDetailLev != null && listDetailLev.size() > 0) {

            HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell = row.createCell((short) 0);
           
            String[] tableTitle = {
                companyName,
                "MONTH END DETAIL REPORT - DP, AL, LL",
                "Month : " + Formater.formatDate(new Date(), "MMMM yyyy")
            };

            String[] tableSubTitle = {};

            String[] tableHeader = {
                //EMPLOYEE
                "NO", "PAYROLL", "EMPLOYEE","DEPARTMENT" 
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
            float totalDP2BTaken = 0;
            float totalExpDP = 0;
            float totalDPBlc = 0;
            
            float totalALPrev = 0;
            float totalALent = 0;
            float totalALTotal = 0;
            float totalALTaken = 0;
            float totalAL2BTaken = 0;
            float totalALBlc = 0;
            
            float totalLLPrev = 0;
            float totalLLent = 0;
            float totalLTotal = 0;
            float totalLLTaken = 0;
            float totalLL2BTaken = 0;
            float totalLLBlc = 0;
            float totalExpLL = 0;
            float LLBalanceWth2BTakenWthExpiredQty = 0;
            
            for (int i = 0; i < listDetailLev.size(); i++) {
                
                int collPos = 0;
                
                RepItemLeaveAndDp item = null;                
                item = (RepItemLeaveAndDp) listDetailLev.get(i);
                

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
                 * @DESC    : PAYROLL
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(item.getPayrollNum()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                /**
                 * @DESC    : EMLOYEE NAME                 
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(item.getEmployeeName()));
                cell.setCellStyle(styleValue);
                collPos++;                
                
                /**
                 * @DESC    : DEPARTMENT NAME                 
                 */
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(item.getDepName()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                /**
                 * @DESC    : DOWN PAYMENT
                 */                                
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(item.getDPQty()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(item.getDPTaken()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(item.getDP2BTaken()));
                cell.setCellStyle(styleValue);
                collPos++;
                
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(SessLeaveManagement.totalExpiredDp(item.getEmployeeId())));
                cell.setCellStyle(styleValue);
                collPos++;
                
                float residueDp = 0;
                residueDp = item.getDPBalanceWth2BTaken() - SessLeaveManagement.totalExpiredDp(item.getEmployeeId());
                cell = row.createCell((short) collPos);
                cell.setCellValue(String.valueOf(residueDp));
                cell.setCellStyle(styleValue);
                collPos++;
                
                totalDPent =totalDPent+ item.getDPQty();
                totalDPTaken =totalDPTaken+ item.getDPTaken();
                totalDP2BTaken =totalDP2BTaken+ item.getDP2BTaken();
                totalExpDP = totalExpDP = SessLeaveManagement.totalExpiredDp(item.getEmployeeId());
                totalDPBlc =totalDPBlc+ residueDp;
                
                /**
                 * @DESC    : ANNUAL LEAVE
                 */     
                boolean stsAlAktf = true;
                
                stsAlAktf = SessLeaveApplication.getLastDayAlPeriod(item.getALStockId(),new Date());
                // update by satrya 2014-01-04 stsAlAktf = SessLeaveApplication.getLastDayAlPeriod(item.getALStockId(),new Date());
                boolean statusExpired =  SessLeaveApplication.getStatusLeaveAlExpired(item.getALStockId()); ;// update by satrya 2014-01-04 SessLeaveApplication.getStatusLeaveAlExpired(item.getALStockId()); 
                
                if(stsAlAktf == true){
                    
                    float exp = 0;
                    
                    if(statusExpired == true){
                        
                        cell = row.createCell((short) collPos);
                        cell.setCellValue(String.valueOf(0));
                        cell.setCellStyle(styleValue);
                        collPos++;                        
                        
                        exp = item.getALPrev();
                        
                    }else{                        
                        
                        cell = row.createCell((short) collPos);
                        cell.setCellValue(String.valueOf(item.getALPrev()));
                        cell.setCellStyle(styleValue);
                        collPos++;                        
                        
                        totalALPrev =totalALPrev + item.getALPrev();
                    }
                    
                    float Total = item.getALTotal() - exp;
                    float balance = Total - item.getALTaken() - item.getAL2BTaken();
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(item.getALEntitle()));
                    //update by satrya 2013-10-24
                    //cell.setCellValue(String.valueOf(item.getALQty()));
                    cell.setCellStyle(styleValue);
                    collPos++;                        
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(Total));
                    cell.setCellStyle(styleValue);
                    collPos++;                        
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(item.getALTaken()));
                    cell.setCellStyle(styleValue);
                    collPos++;                        
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(item.getAL2BTaken()));
                    cell.setCellStyle(styleValue);
                    collPos++;                        
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(balance));
                    cell.setCellStyle(styleValue);
                    collPos++;                                            
                    
                    totalALent =totalALent+ item.getALEntitle()/* update by satrya 2013-10-28 item.getALQty()*/;                    
                    totalALTotal =totalALTotal + Total;
                    totalALTaken =totalALTaken + item.getALTaken();
                    totalAL2BTaken =totalAL2BTaken + item.getAL2BTaken();
                    totalALBlc = totalALBlc + balance;                   
                
                }else{
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(0));
                    cell.setCellStyle(styleValue);
                    collPos++;  
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(0));
                    cell.setCellStyle(styleValue);
                    collPos++;                        
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(0));
                    cell.setCellStyle(styleValue);
                    collPos++;                        
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(0));
                    cell.setCellStyle(styleValue);
                    collPos++;                        
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(0));
                    cell.setCellStyle(styleValue);
                    collPos++;                        
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(0));
                    cell.setCellStyle(styleValue);
                    collPos++;                                            
                    
                }
                
                /**
                 * @LONG LEAVE
                 */
                boolean stsLLExp = false;
                stsLLExp = SessLeaveApplication.getStatusLLExpired(item.getEmployeeId()); 
                
                if(stsLLExp == false){
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(item.getLLPrev()));
                    cell.setCellStyle(styleValue);
                    collPos++;                                            
                    
                    cell = row.createCell((short) collPos);
                    //cell.setCellValue(String.valueOf(item.getLLQty()));
                    //update by satrya 2013-11-22
                    cell.setCellValue(String.valueOf(item.getLLQty()));
                    cell.setCellStyle(styleValue);
                    collPos++;                                      
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(item.getLLTotal()));
                    cell.setCellStyle(styleValue);
                    collPos++;                                      
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(item.getLLTaken()));
                    cell.setCellStyle(styleValue);
                    collPos++;                                      
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(item.getLL2BTaken()));
                    cell.setCellStyle(styleValue);
                    collPos++;                                      
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(item.getLLExpdQty()));
                    cell.setCellStyle(styleValue);
                    collPos++;                                      
                    
                    //update by satrya 2013-11-22
                     LLBalanceWth2BTakenWthExpiredQty = item.getLLBalanceWth2BTaken() - item.getLLExpdQty();
                     
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(LLBalanceWth2BTakenWthExpiredQty));
                    cell.setCellStyle(styleValue);
                    collPos++;                                      
                                        
                    totalLLPrev = totalLLPrev + item.getLLPrev();
                    totalLLent = totalLLent + item.getLLQty();
                    totalLTotal = totalLTotal + item.getLLTotal();
                    totalLLTaken = totalLLTaken + item.getLLTaken(); 
                    totalLL2BTaken = totalLL2BTaken + item.getLL2BTaken();
                    totalExpLL = totalExpLL + item.getLLExpdQty();
                   //LLBalanceWth2BTakenWthExpiredQty = item.getLLBalanceWth2BTaken() - item.getLLExpdQty();
                    totalLLBlc =totalLLBlc + LLBalanceWth2BTakenWthExpiredQty;                          
                    
                }else{
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(0));
                    cell.setCellStyle(styleValue);
                    collPos++;                                            
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(0));
                    cell.setCellStyle(styleValue);
                    collPos++;                                      
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(0));
                    cell.setCellStyle(styleValue);
                    collPos++;                                      
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(0));
                    cell.setCellStyle(styleValue);
                    collPos++;                                      
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(0));
                    cell.setCellStyle(styleValue);
                    collPos++;                                      
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(0));
                    cell.setCellStyle(styleValue);
                    collPos++;                                      
                    
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(0));
                    cell.setCellStyle(styleValue);
                    collPos++;
                    
                }
                
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
            cell.setCellValue(String.valueOf(totalDPent));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalDPTaken));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalDP2BTaken));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalExpDP));
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
            cell.setCellValue(String.valueOf(totalAL2BTaken));
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
            cell.setCellValue(String.valueOf(totalLL2BTaken));
            cell.setCellStyle(styleValue);
            collPos++;
            
            cell = row.createCell((short) collPos);
            cell.setCellValue(String.valueOf(totalExpLL));
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
