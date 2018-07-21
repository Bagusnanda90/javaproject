/*
 * TransferListXLS.java
 *
 * Created on December 24, 2007, 2:27 PM
 */

package com.dimata.harisma.report.payroll;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

import java.io.InputStream; 
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.model.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;

import com.dimata.qdep.form.*;
import com.dimata.util.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.session.employee.SessEmployee;


/**
 *
 * @author  Yunny
 */
public class TransferListXLS  extends HttpServlet{
    
     /** Initializes the servlet.
    */  
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

     /** Destroys the servlet.
    */  
    public void destroy() {

    }
    
    /** Creates a new instance of TransferListXLS */
    public TransferListXLS() {
    }
    
      /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        
        System.out.println("---===| Excel Report |===---");
        //response.setContentType("text/html");
        //response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("List Transfered");

        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);

        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style4 = wb.createCellStyle();
        style4.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
         /* get data from session */
            HttpSession sessEmpPresence = request.getSession(true);
            Vector vctMonthlyAbsence = null;
            try{
	        vctMonthlyAbsence = (Vector)sessEmpPresence.getValue("QUERY_REPORT");
            }catch(Exception e){
                System.out.println("Exc : "+e.toString());
        }
       
        
        String period = "";
        String summ = "";
        String departmentName = "";
        Vector listSalaryPdf = new Vector(1,1);
        String footerNote = "";
        String totalTransfered = "";
        
         if(vctMonthlyAbsence != null && vctMonthlyAbsence.size()==6){
                try{
                        //System.out.println("masuk trydfjdjhfj.............");
                        listSalaryPdf = (Vector)vctMonthlyAbsence.get(0);
	            	period = (String) vctMonthlyAbsence.get(1);
                        summ = (String) vctMonthlyAbsence.get(2);
                        departmentName = (String) vctMonthlyAbsence.get(3);
                        footerNote = (String) vctMonthlyAbsence.get(4);
                        totalTransfered = (String) vctMonthlyAbsence.get(5);
                        
                        if(departmentName.equals("")){
                            departmentName = " ALL DEPARTMENT";
                        }else{
                            departmentName =departmentName;
                        }
                        
                    
                        //System.out.println("startDatePeriod  "+startDatePeriod);
                }
                catch(Exception e)
                {
                 	System.out.println("exc on get List vctMonthlyAbsence : "+e.toString());
                }
            }   
        
       // System.out.println("period....."+period);

        // Create a row and put some cells in it. Rows are 0 based.
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("LIST TRANSFERED PERIOD "+period );
        cell.setCellStyle(style);
        for (int j = 1; j < 7; j++) {
            cell = row.createCell((short) j);
            cell.setCellStyle(style);
        }
        sheet.addMergedRegion(new Region(0,(short)0,0,(short)20));

        row = sheet.createRow((short) (1));
        for (int k = 0; k < 6; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue("");
            cell.setCellStyle(style4);
        }

        String[] tableHeader = {
            "NAME", "DEPARTMENT", "SECTION", "COMM.DATE", "BANK ACC NR.", 
            "TOTAL TRANSFERED", "DESCRIPTION"
        };
        row = sheet.createRow((short) (2));
        for (int k = 0; k < tableHeader.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeader[k]);
            cell.setCellStyle(style3);
        }
        
      
        //listEmployee = sessEmployee.getEmpSalary(secSelect,oidPeriod,departmentId);

        //String d = "";
        for (int i = 0; i < listSalaryPdf.size(); i++) {
            Vector itemAbsence = (Vector) listSalaryPdf.get(i);
            row = sheet.createRow((short) (i+3));
            
           
            cell = row.createCell((short) 0);cell.setCellValue(String.valueOf(itemAbsence.get(1)));cell.setCellStyle(style2); 
            cell = row.createCell((short) 1);cell.setCellValue(String.valueOf(itemAbsence.get(2)));cell.setCellStyle(style2);  
            cell = row.createCell((short) 2);cell.setCellValue(String.valueOf(itemAbsence.get(3)));cell.setCellStyle(style2);            
            cell = row.createCell((short) 3);cell.setCellValue(String.valueOf(itemAbsence.get(4)));cell.setCellStyle(style2);            
            cell = row.createCell((short) 4);cell.setCellValue(String.valueOf(itemAbsence.get(5)));cell.setCellStyle(style2);  
            cell = row.createCell((short) 5);cell.setCellValue(String.valueOf(itemAbsence.get(6)));cell.setCellStyle(style2); 
            cell = row.createCell((short) 6);cell.setCellValue("");cell.setCellStyle(style2);            
         }
        //sessEmpPresence.removeValue("QUERY_REPORT");
        
        // Write the output to a file
        //FileOutputStream fileOut = new FileOutputStream("workbook.xls");
        //wb.write(fileOut);
        //fileOut.close();        
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

    /** Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
}
