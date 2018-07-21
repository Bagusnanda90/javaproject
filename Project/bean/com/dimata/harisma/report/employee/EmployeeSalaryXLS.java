/*
 * EmpScheduleListXLS.java
 *
 * Created on October 16, 2002, 12:08 PM
 */
 
package com.dimata.harisma.report.employee;           

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

import com.dimata.util.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.locker.*;
import com.dimata.harisma.session.employee.*;

/** 
 *
 * @author  karya
 * @version 
 */
public class EmployeeSalaryXLS extends HttpServlet {
   
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        System.out.println("---===| Excel Report |===---");
        //response.setContentType("text/html");
        //response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Salary List");

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
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style4 = wb.createCellStyle();
        style4.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //Current Salary,New Salary
        String[] tableHeader = {
            "No", "Name", "Payroll No.", "Position", "Level", "MS", 
            "Comm.Date", "LOS1", "LOS2", "", 
            "Current Salary", "", " ", "New Salary", " ",
            " ", "Increment", " "," ", " ", "Percentage"," "
        };
        
        String[] tableHeaderDetail = {
            "Basic", "Transport", "Total", "Basic", "Transport", "Total", "Basic", "Transport","Additional", "Total",
            "Basic", "Transport", "Total"
        };
        
        
        // Create a row and put some cells in it. Rows are 0 based.
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        /*cell.setCellValue("EMPLOYEE SALARY---WAYAN");
        cell.setCellStyle(style);
        for (int j = 1; j < tableHeader.length; j++) {
            cell = row.createCell((short) j);
            cell.setCellStyle(style);
        }
        sheet.addMergedRegion(new Region(0,(short)0,0,(short)15));

        row = sheet.createRow((short) (1));
        for (int k = 0; k < 16; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue("");
            cell.setCellStyle(style4);
        }*/
        
        
        
        // Header Pertama
        //row = sheet.createRow((short) (2));
        for (int k = 0; k < tableHeader.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeader[k]);
            cell.setCellStyle(style3);
        }
        
        int jd = 0;
        row = sheet.createRow((short) (1));
        for (int j = 0; j < tableHeader.length; j++) {
            cell = row.createCell((short) j);
            if((j>8) && (j<tableHeader.length)){
                cell.setCellValue(tableHeaderDetail[jd]);
                jd++;
            }else
                cell.setCellValue("");
            
            cell.setCellStyle(style3);
        }

        SessEmployee sessEmployee = new SessEmployee();
        HttpSession session = request.getSession();
        Vector listEmployee  = (Vector) session.getValue("LIST_SALARY");

        //String d = "";
        for (int i = 0; i < listEmployee.size(); i++) {
            Vector temp = (Vector)listEmployee.get(i);
            if(i==0){
                System.out.println("-------> temp : "+temp);
	    }
            
            Employee employee = (Employee)temp.get(0);
            Position position = (Position)temp.get(1);
            Department department = (Department)temp.get(2);
            Level level = (Level)temp.get(3);
            Marital marital = (Marital)temp.get(4);
            EmpSalary empSalary = (EmpSalary)temp.get(5);
            
            //create row
            row = sheet.createRow((short) (i+2));
            
            // "Name", "Payroll No.", "Position", "Level", "MS",
            cell = row.createCell((short) 0);
            cell.setCellValue(""+(i+1));
            cell.setCellStyle(style2); 
            
            cell = row.createCell((short) 1);
            cell.setCellValue(employee.getFullName());
            cell.setCellStyle(style2);  
            
            cell = row.createCell((short) 2);
            cell.setCellValue(employee.getEmployeeNum());
            cell.setCellStyle(style2);
            
            cell = row.createCell((short) 3);
            cell.setCellValue(position.getPosition());
            cell.setCellStyle(style2);
            
            cell = row.createCell((short) 4);
            cell.setCellValue(level.getLevel());
            cell.setCellStyle(style2);
            
            cell = row.createCell((short) 5);
            cell.setCellValue(marital.getMaritalCode());
            cell.setCellStyle(style2);
            
            //"Comm.Date", "LOS1", "LOS2", "Input Date", "Basic", 
            cell = row.createCell((short) 6);
            cell.setCellValue(Formater.formatDate(employee.getCommencingDate(),"dd-MMM-yy"));
            cell.setCellStyle(style2);
            
            cell = row.createCell((short) 7);
            cell.setCellValue(empSalary.getLos1());
            cell.setCellStyle(style2);
            
            cell = row.createCell((short) 8);
            cell.setCellValue(empSalary.getLos2());
            cell.setCellStyle(style2);
             
            /*cell = row.createCell((short) 9);
            cell.setCellValue(Formater.formatDate(empSalary.getCurrDate(),"dd-MMM-yy"));
            cell.setCellStyle(style2);*/
            
            String strCurrBasic = "-"; 
            if(empSalary.getCurrBasic()-0.0f>0.0f)
                strCurrBasic = Formater.formatNumber(empSalary.getCurrBasic(),"####");
            cell = row.createCell((short) 9);
            cell.setCellValue(strCurrBasic);
            cell.setCellStyle(style2);
            
            strCurrBasic = "-";
            if(empSalary.getCurrTransport()-0.0f>0.0f)
                strCurrBasic = Formater.formatNumber(empSalary.getCurrTransport(),"####");                
            cell = row.createCell((short) 10);
            cell.setCellValue(strCurrBasic);
            cell.setCellStyle(style2);
            
            strCurrBasic = "-";
            if(empSalary.getCurrTotal()-0.0f>0.0f)
                    strCurrBasic = Formater.formatNumber(empSalary.getCurrTotal(),"####");
            cell = row.createCell((short) 11);
            cell.setCellValue(strCurrBasic);
            cell.setCellStyle(style2);
            
            strCurrBasic = "-";
            if(empSalary.getNewBasic()-0.0f>0.0f)
                    strCurrBasic = Formater.formatNumber(empSalary.getNewBasic(),"####");
            cell = row.createCell((short) 12);
            cell.setCellValue(strCurrBasic);
            cell.setCellStyle(style2);
            
            strCurrBasic = "-";
            if(empSalary.getNewTransport()-0.0f>0.0f)
                    strCurrBasic = Formater.formatNumber(empSalary.getNewTransport(),"####");
            cell = row.createCell((short) 13);
            cell.setCellValue(strCurrBasic);
            cell.setCellStyle(style2);
            
            strCurrBasic = "-";
            if(empSalary.getNewTotal()-0.0f>0.0f)
                    strCurrBasic = Formater.formatNumber(empSalary.getNewTotal(),"####");
            cell = row.createCell((short) 14);
            cell.setCellValue(strCurrBasic);
            cell.setCellStyle(style2);
            
            /*strCurrBasic = "-";
            if(empSalary.getAdditional()-0.0f>0.0f)
                    strCurrBasic = Formater.formatNumber(empSalary.getAdditional(),"#,###");
            cell = row.createCell((short) 15);
            cell.setCellValue(strCurrBasic);
            cell.setCellStyle(style2);*/
            
            /*Tambahan untuk blok increment*/
            strCurrBasic = "-";
            if((empSalary.getNewBasic()- empSalary.getCurrBasic())-0.0f > 0.0f)
                strCurrBasic = Formater.formatNumber((empSalary.getNewBasic()- empSalary.getCurrBasic()),"####"); 
            cell = row.createCell((short) 15);
            cell.setCellValue(strCurrBasic);
            cell.setCellStyle(style2);
            
            strCurrBasic = "-";
            if((empSalary.getNewTransport()-empSalary.getCurrTransport())-0.0f > 0.0f)
                strCurrBasic = Formater.formatNumber((empSalary.getNewTransport()-empSalary.getCurrTransport()),"####"); 
            cell = row.createCell((short) 16);
            cell.setCellValue(strCurrBasic);
            cell.setCellStyle(style2);
            
            strCurrBasic = "-";
            if((empSalary.getNewTotal()- empSalary.getCurrTotal())-0.0f > 0.0f)
                strCurrBasic = Formater.formatNumber(0,"####"); 
            cell = row.createCell((short) 17);
            cell.setCellValue(strCurrBasic);
            cell.setCellStyle(style2);
            
            strCurrBasic = "-";
            if((empSalary.getNewTotal()- empSalary.getCurrTotal())-0.0f > 0.0f)
                strCurrBasic = Formater.formatNumber((empSalary.getNewTotal()- empSalary.getCurrTotal()),"####"); 
            cell = row.createCell((short) 18);
            cell.setCellValue(strCurrBasic);
            cell.setCellStyle(style2);
            
            /*Tambah untuk blok percentage*/
            strCurrBasic = "-";              
            if((empSalary.getNewBasic()- empSalary.getCurrBasic())/empSalary.getCurrBasic()*100 -0.0f > 0.0f){
                double dbl = ((empSalary.getNewBasic() - empSalary.getCurrBasic())/empSalary.getCurrBasic())*100;
                System.out.println("Hasil percentage basic sebelum di format............:"+dbl);
                strCurrBasic = Formater.formatNumber(dbl,"#.###"); 
                System.out.println("Hasil setelah diformat.............:"+strCurrBasic);
            }            
            cell = row.createCell((short) 19);
            cell.setCellValue(strCurrBasic);
            cell.setCellStyle(style2);
            
            strCurrBasic = "-";
            if((empSalary.getNewTransport()-empSalary.getCurrTransport())/empSalary.getCurrTransport()*100 -0.0f > 0.0f)
                strCurrBasic = Formater.formatNumber((empSalary.getNewTransport()-empSalary.getCurrTransport())/empSalary.getCurrTransport()*100,"#.###"); 
            cell = row.createCell((short) 20);
            cell.setCellValue(strCurrBasic);
            cell.setCellStyle(style2);
            
            strCurrBasic = "-";
            if((empSalary.getNewTotal()- empSalary.getCurrTotal())/empSalary.getCurrTotal()*100 -0.0f > 0.0f)
                strCurrBasic = Formater.formatNumber((empSalary.getNewTotal()- empSalary.getCurrTotal())/empSalary.getCurrTotal()*100,"#.###"); 
            cell = row.createCell((short) 21);
            cell.setCellValue(strCurrBasic);
            cell.setCellStyle(style2);
            
            
        }
        
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
