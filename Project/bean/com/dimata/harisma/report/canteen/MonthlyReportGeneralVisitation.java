/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.harisma.report.canteen;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;


import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.model.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;

import com.dimata.util.*;
import com.dimata.gui.jsp.*;
import com.dimata.qdep.form.*;

//harisma
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.search.SrcTrainingTarget;
import com.dimata.harisma.session.employee.SessTraining;
import com.dimata.harisma.session.employee.*;


/**
 *
 * @author artha
 */
public class MonthlyReportGeneralVisitation extends HttpServlet {
   
  
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        //response.setContentType("text/html");
        /*
        response.setContentType("application/x-msexcel");
        java.io.PrintWriter out = response.getWriter();
         */
        
        System.out.println("---===| Excel Report |===---");
        //response.setContentType("text/html");
        //response.setContentType("application/vnd.ms-excel");
        //response.setContentType("application/x-msexcel");
        response.setContentType("application/.ods");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Visitation Report");
        
        //Stile
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
        styleHeader.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleHeader.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleHeader.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));

        HSSFCellStyle styleValue = wb.createCellStyle();
        styleValue.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleValue.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleValue.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleValue.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleValue.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, false));
        
        HSSFCellStyle styleBold = wb.createCellStyle();
        styleBold.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleBold.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleBold.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);      
        styleBold.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));
        
        HSSFCellStyle styleSummary = wb.createCellStyle();
        styleValue.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleValue.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleValue.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleValue.setFont(createFont(wb, 10, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, false));
        
        /* Get Data From Session */
        Vector listData = new Vector();
        HttpSession sess = request.getSession(true);
        try {               
            listData = (Vector)sess.getValue("CANTEEN_MONTHLY_DETAIL_DATA");
        } 
        catch (Exception e) {
            System.out.println(e.toString());
            listData = new Vector();
        }        
        
        //Jika data tidak kosong
        if ((listData != null) && (listData.size() > 0)) {      
            Date date = (Date)listData.get(0);
            Vector data = (Vector)listData.get(1);
            
            // Create a row and put some cells in it. Rows are 0 based.
            HSSFRow row = sheet.createRow((short) 0);
            HSSFCell cell = row.createCell((short) 0);

            String[] tableTitle = {
                "Canteen Report"
            };
            
            String[] tableSubTitle = {
                "Period : " + Formater.formatDate(date, "MMMM yyyy")
            };

            String[] tableHeader = {
                "No"
                ,"Department"
                ,"This Month"
                ,"Last Month"
            };

            //Untuk mengcount Row
            int countRow = 0;

            ///////////////////////////////////// TITLE /////////////////////////////////
            for (int k = 0; k < tableTitle.length; k++) {
                row = sheet.createRow((short) (countRow));
                countRow++;
                cell = row.createCell((short) 0);
                cell.setCellValue(tableTitle[k]);
                cell.setCellStyle(styleTitle);
            }

            ///////////////////////////////////// SUB TITLE /////////////////////////////////
            for (int k = 0; k < tableSubTitle.length; k++) {
                row = sheet.createRow((short) (countRow));
                countRow++;
                cell = row.createCell((short) 0);
                cell.setCellValue(tableSubTitle[k]);
                cell.setCellStyle(styleSubTitle);
            }

            ///////////////////////////////////// HEADER /////////////////////////////////
            row = sheet.createRow((short) (countRow));
            countRow++;
            for (int k = 0; k < tableHeader.length; k++) {
                cell = row.createCell((short) k);
                cell.setCellValue(tableHeader[k]);
                cell.setCellStyle(styleHeader);
            }
            
            ///////////////////////////////////// DATA /////////////////////////////////            
            
            for(int i=0;i<data.size();i++){
                //Mengambil data
                Vector temp = (Vector) data.get(i);
               
                //Pembuatan Row
                row = sheet.createRow((short) (countRow));
                countRow++;
                
                //Pembuatan Cell
                int collPos = 0;
                
                if(i < data.size()-1) {
                
                    //No
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(temp.get(0)));//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;

                    //Department
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(temp.get(1)));//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;

                    //This month
                    cell = row.createCell((short) collPos);
                    cell.setCellType(cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue((short)Integer.parseInt(String.valueOf(temp.get(2))));//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;

                    //Last month
                    cell = row.createCell((short) collPos);
                    cell.setCellType(cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue((short)Integer.parseInt(String.valueOf(temp.get(3))));//Value Here
                    cell.setCellStyle(styleValue);
                    collPos++;
                
                }
                else
                {                    
        
                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(temp.get(0)));//Value Here
                    cell.setCellStyle(styleBold);
                    collPos++;

                    cell = row.createCell((short) collPos);
                    cell.setCellValue(String.valueOf(temp.get(1)));//Value Here
                    cell.setCellStyle(styleBold);
                    collPos++;

                    cell = row.createCell((short) collPos);
                    cell.setCellType(cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue((short)Integer.parseInt(String.valueOf(temp.get(2))));//Value Here
                    cell.setCellStyle(styleBold);
                    collPos++;

                    cell = row.createCell((short) collPos);
                    cell.setCellType(cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue((short)Integer.parseInt(String.valueOf(temp.get(3))));//Value Here
                    cell.setCellStyle(styleBold);
                    collPos++;
                    
                }
                
            }
            
            int collPos = 0;
            row = sheet.createRow((short) (countRow));
            countRow++;
            
            cell = row.createCell((short) collPos);   
            cell.setCellValue("");//Value Here         
            cell.setCellStyle(styleTitle);
           
            row = sheet.createRow((short) (countRow));
            countRow++;
                
            cell = row.createCell((short) collPos);   
            cell.setCellValue("Variance from last month");//Value Here
            cell.setCellStyle(styleBold);
           
            row = sheet.createRow((short) (countRow));
            countRow++;
                
            cell = row.createCell((short) collPos);   
            cell.setCellValue("Total Cafetaria");//Value Here
            cell.setCellStyle(styleSummary);
           
            row = sheet.createRow((short) (countRow));
            countRow++;
                
            cell = row.createCell((short) collPos);   
            cell.setCellValue("Cafetaria Cost per Cover");//Value Here
            cell.setCellStyle(styleSummary);
           
            row = sheet.createRow((short) (countRow));
            countRow++;
                
            cell = row.createCell((short) collPos);   
            cell.setCellValue("Total Meal (F & B)");//Value Here
            cell.setCellStyle(styleSummary);
         
            row = sheet.createRow((short) (countRow));
            countRow++;
                
            cell = row.createCell((short) collPos);   
            cell.setCellValue("Meal Cost per Cover");//Value Here
            cell.setCellStyle(styleSummary);
        
        }
        
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    } 

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

    private static HSSFFont createFont(HSSFWorkbook wb, int size,int color,boolean isBold){
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) size);
        font.setColor((short) color);
        if(isBold){
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        return font;
    }

}
