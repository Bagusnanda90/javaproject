/*
 * WeeklyEarlyXLS.java
 *
 * Created on December 24, 2007, 2:27 PM
 */

package com.dimata.harisma.report;

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



/**
 *
 * @author  Yunny
 */
public class MonthlyEarlyXLS  extends HttpServlet{
    
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
    public MonthlyEarlyXLS() {
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
        HSSFSheet sheet = wb.createSheet("Early Home Montly");

        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);

        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style2.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style2.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //style3.setWrapText(true);

        HSSFCellStyle style4 = wb.createCellStyle();
        style4.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        HSSFCellStyle style5 = wb.createCellStyle();
        style5.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style5.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style5.setWrapText(true);
      
        
        HSSFCellStyle hssfcellstyle18 = wb.createCellStyle();
        hssfcellstyle18.setFillBackgroundColor(new HSSFColor.GREY_80_PERCENT().getIndex());//HSSFCellStyle.GREY_80_PERCENT);
        hssfcellstyle18.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        hssfcellstyle18.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        hssfcellstyle18.setBorderBottom(HSSFCellStyle.SOLID_FOREGROUND);
        hssfcellstyle18.setBorderLeft(HSSFCellStyle.SOLID_FOREGROUND);
        hssfcellstyle18.setBorderRight(HSSFCellStyle.SOLID_FOREGROUND);
        hssfcellstyle18.setBorderTop(HSSFCellStyle.SOLID_FOREGROUND);
        hssfcellstyle18.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        
        HSSFCellStyle hssfcellstyle17 = wb.createCellStyle();
        hssfcellstyle17.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        hssfcellstyle17.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        hssfcellstyle17.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        hssfcellstyle17.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        hssfcellstyle17.setBorderLeft(HSSFCellStyle.SOLID_FOREGROUND);
        hssfcellstyle17.setBorderRight(HSSFCellStyle.SOLID_FOREGROUND);
        hssfcellstyle17.setBorderTop(HSSFCellStyle.SOLID_FOREGROUND);
        hssfcellstyle17.setWrapText(true);
       
         /* get data from session */
            HttpSession sessEmpPresence = request.getSession(true);
            Vector vctMonthlyAbsence = null;
            try{
	        vctMonthlyAbsence = (Vector)sessEmpPresence.getValue("MONTHLY_EARLY");
            }catch(Exception e){
                System.out.println("Exc : "+e.toString());
        }
       
        
        
        String departmentId = "";
        String sectionId = "";
        Vector listWeeklyEarly = new Vector(1,1);
        Date startDateMonth = new Date();
        Date endDateMonth = new Date();
        String departmentName = "";
        String sectionName = "";
        
         if(vctMonthlyAbsence != null && vctMonthlyAbsence.size()==5){
                try{
                        //System.out.println("masuk trydfjdjhfj.............");
                        listWeeklyEarly = (Vector)vctMonthlyAbsence.get(3);
	                departmentId = (String)vctMonthlyAbsence.get(0);
                        startDateMonth = (Date) vctMonthlyAbsence.get(1);
                        endDateMonth = (Date) vctMonthlyAbsence.get(2);
                        sectionId = (String)vctMonthlyAbsence.get(4);
                        //System.out.println("endDateMonth.."+endDateMonth);
                        if(departmentId.length()>0){
                            try{
                                Department department = new Department();
                                department = PstDepartment.fetchExc(Long.parseLong(departmentId));
                                departmentName = department.getDepartment();
                            }catch(Exception e){
                                System.out.println("err get department"+e.toString());
                                
                            }
                        }
                        
                        /* if(sectionId.length()>0){
                            try{
                                Section section = new Section();
                                section = PstSection.fetchExc(Long.parseLong(sectionId));
                                sectionName = section.getSection();
                            }catch(Exception e){
                                System.out.println("err get department"+e.toString());
                                
                            }
                        }*/
                        
                        if(departmentId.equals("")){
                            departmentName = " ALL DEPARTMENT";
                        }else{
                            
                            departmentName =departmentName;
                        }
                        
                    // System.out.println("departmentName..."+departmentName); 
                        //System.out.println("startDatePeriod  "+startDatePeriod);
                }
                catch(Exception e)
                {
                 	System.out.println("exc on get List vctMonthlyAbsence : "+e.toString());
                }
            }   
        
       // System.out.println("period....."+period);
       
        // Create a row and put some cells in it. Rows are 0 based.
        //String week = getStrWeek(Integer.parseInt(idx));
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("LIST EARLY HOME MONTLY ");
        
        row = sheet.createRow((short) 1);
        cell = row.createCell((short) 0);
        cell.setCellValue("PER : "+Formater.formatDate(startDateMonth,"MMMM yyyy").toUpperCase());
        
        row = sheet.createRow((short) 2);
        cell = row.createCell((short) 0);
        cell.setCellValue("DEPARTMENT : "+departmentName.toUpperCase());
        cell.setCellStyle(style);
     
        int numColum = 65;
        for (int j = 1; j <= numColum ; j++) {
             
           cell = row.createCell((short) j);
           cell.setCellStyle(style);
           
       }
      
       sheet.addMergedRegion(new Region(0,(short)0,0,(short)65));
       sheet.addMergedRegion(new Region(1,(short)0,0,(short)65));
       sheet.addMergedRegion(new Region(2,(short)0,0,(short)65));
      
        row = sheet.createRow((short) (3));
        for (int k = 0; k < 65; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue("");
            cell.setCellStyle(style4);
        }
        
        /*String[] tableHeader = {
            "PAYROLL", 
            "EMPLOYEE", 
            "1",
            "2",
            "3","4","5","6","7","8","9","10","11","12","13",
            "14","15","16","17","18","19","20","21","22","23",
            "24","25","26","27","28","29","30","31",
            "TOTAL EARLY HOME"
            
        };*/
         row = sheet.createRow((short) (4));
         cell = row.createCell((short) 0);
         cell.setCellValue("Payroll");
         cell.setCellStyle(hssfcellstyle18);
         cell = row.createCell((short) 1);
         cell.setCellValue("Employee Name");
         cell.setCellStyle(hssfcellstyle18);
         cell = row.createCell((short) 2);
         cell.setCellValue("Duration (hour, minutes)");
         cell.setCellStyle(hssfcellstyle18);
         int r = 3;
         int w = startDateMonth.getDate();
         for (;w < endDateMonth.getDate();) {
              cell = row.createCell((short)r);
              cell.setCellValue("");
              cell.setCellStyle(hssfcellstyle18);
              r = r + 1;
              cell = row.createCell((short)r);
              cell.setCellValue("");
              cell.setCellStyle(hssfcellstyle18);
              r = r + 1;
              w++;
         }
         cell = row.createCell((short)r);
         cell.setCellValue("");
         cell.setCellStyle(hssfcellstyle18);
         cell = row.createCell((short)(r + 1));
         cell.setCellValue("Total");
         cell.setCellStyle(hssfcellstyle18);
         cell = row.createCell((short)(r + 2));
         cell.setCellValue("Deduction");
         cell.setCellStyle(hssfcellstyle18);
         sheet.addMergedRegion(new Region(4 , (short)2 , 4 , (short)r));
         row = sheet.createRow((short) 5);
         cell = row.createCell((short) 0);
         cell.setCellValue("");
         cell.setCellStyle(hssfcellstyle18);
         cell = row.createCell((short) 1);
         cell.setCellValue("");
         cell.setCellStyle(hssfcellstyle18);
         r = 2;
         w = startDateMonth.getDate();
         for (;w <= endDateMonth.getDate();) {
              cell = row.createCell((short)r);
              cell.setCellValue(new StringBuffer().append("").append(w).toString());
              cell.setCellStyle(hssfcellstyle18);
              r = r + 1;
              cell = row.createCell((short)r);
              cell.setCellValue("");
              cell.setCellStyle(hssfcellstyle18);
              r = r + 1;
              w++;
         }
         cell = row.createCell((short)r);
         cell.setCellValue("");
         cell.setCellStyle(hssfcellstyle18);
         cell = row.createCell((short)(r + 1));
         cell.setCellValue("");
         cell.setCellStyle(hssfcellstyle18);
         sheet.addMergedRegion(new Region(4 , (short)0 , 5 , (short)0));
         sheet.addMergedRegion(new Region(4 , (short)1 , 5 , (short)1));
         sheet.addMergedRegion(new Region(4 , (short)r , 5 , (short)r));
         sheet.addMergedRegion(new Region(4 , (short)(r + 1) , 5 , (short)(r + 1)));
         r = 2;
         w = startDateMonth.getDate();
         for (;w <= endDateMonth.getDate();) {
              sheet.addMergedRegion(new Region(5 , (short)r , 5 , (short)(r + 1)));
              r = r + 2;
              w++;
         }
         
       /* row = sheet.createRow((short) (4));
        for (int k = 0; k < tableHeader.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeader[k]);
            if(k<2){
                cell.setCellStyle(style5);
            }
            if(k>=2){
                cell.setCellStyle(style3);
                sheet.addMergedRegion(new Region(4,(short)0,0,(short)34));
                row = sheet.createRow((short) (5));
            }
        }*/
        
      
        //listEmployee = sessEmployee.getEmpSalary(secSelect,oidPeriod,departmentId);
        //String d = "";
        for (int i = 0; i < listWeeklyEarly.size(); i++) {
            Vector itemAbsence = (Vector) listWeeklyEarly.get(i);
           System.out.println("itemAbsence..."+itemAbsence.size());
           row = sheet.createRow((short) (i+6));
           cell = row.createCell((short) 0);cell.setCellValue(String.valueOf(itemAbsence.get(2)));cell.setCellStyle(hssfcellstyle17); 
           cell = row.createCell((short) 1);cell.setCellValue(String.valueOf(itemAbsence.get(3)));cell.setCellStyle(hssfcellstyle17);
           int c = 4;
           int b = 2;
           int tot = 0;
           int idx = startDateMonth.getDate();
           for (;idx <= endDateMonth.getDate();) {
                   cell = row.createCell((short)b);
                   cell.setCellValue(String.valueOf(itemAbsence.get(c)));
                   cell.setCellStyle(hssfcellstyle17);
                   b++;
                   String string35 = "";
                   //System.out.println("nilai..."+String.valueOf(itemAbsence.get(c)));
                   if (String.valueOf(itemAbsence.get(c)).length() > 0) {
                        string35 = MonthlyEarlyXLS.getMinutesDeduction(String.valueOf(itemAbsence.get(c)));
                        tot = tot + Integer.parseInt(string35);
                   }
                   cell = row.createCell((short)b);
                   cell.setCellValue(string35);
                   cell.setCellStyle(hssfcellstyle17);
                   c++;
                   b++;
                   idx++;
              }
           
          cell = row.createCell((short)b);
          cell.setCellValue(String.valueOf(itemAbsence.get(c)));
          cell.setCellStyle(hssfcellstyle17);
          cell = row.createCell((short)(b + 1));
          cell.setCellValue(new StringBuffer().append("").append(tot).toString());
          cell.setCellStyle(hssfcellstyle17);
          b++;
           /*cell = row.createCell((short) 2);cell.setCellValue(String.valueOf(itemAbsence.get(4)));cell.setCellStyle(style2);
           cell = row.createCell((short) 3);cell.setCellValue(String.valueOf(itemAbsence.get(5)));cell.setCellStyle(style2);
           cell = row.createCell((short) 4);cell.setCellValue(String.valueOf(itemAbsence.get(6)));cell.setCellStyle(style2);
           cell = row.createCell((short) 5);cell.setCellValue(String.valueOf(itemAbsence.get(7)));cell.setCellStyle(style2);
           cell = row.createCell((short) 6);cell.setCellValue(String.valueOf(itemAbsence.get(8)));cell.setCellStyle(style2);
           cell = row.createCell((short) 7);cell.setCellValue(String.valueOf(itemAbsence.get(9)));cell.setCellStyle(style2);
           cell = row.createCell((short) 8);cell.setCellValue(String.valueOf(itemAbsence.get(10)));cell.setCellStyle(style2);
           cell = row.createCell((short) 9);cell.setCellValue(String.valueOf(itemAbsence.get(11)));cell.setCellStyle(style2);
           cell = row.createCell((short) 10);cell.setCellValue(String.valueOf(itemAbsence.get(12)));cell.setCellStyle(style2);
           cell = row.createCell((short) 11);cell.setCellValue(String.valueOf(itemAbsence.get(13)));cell.setCellStyle(style2);
           cell = row.createCell((short) 12);cell.setCellValue(String.valueOf(itemAbsence.get(14)));cell.setCellStyle(style2);
           cell = row.createCell((short) 13);cell.setCellValue(String.valueOf(itemAbsence.get(15)));cell.setCellStyle(style2);
           cell = row.createCell((short) 14);cell.setCellValue(String.valueOf(itemAbsence.get(16)));cell.setCellStyle(style2);
           cell = row.createCell((short) 15);cell.setCellValue(String.valueOf(itemAbsence.get(17)));cell.setCellStyle(style2);
           cell = row.createCell((short) 16);cell.setCellValue(String.valueOf(itemAbsence.get(18)));cell.setCellStyle(style2);
           cell = row.createCell((short) 17);cell.setCellValue(String.valueOf(itemAbsence.get(19)));cell.setCellStyle(style2);
           cell = row.createCell((short) 18);cell.setCellValue(String.valueOf(itemAbsence.get(20)));cell.setCellStyle(style2);
           cell = row.createCell((short) 19);cell.setCellValue(String.valueOf(itemAbsence.get(21)));cell.setCellStyle(style2);
           cell = row.createCell((short) 20);cell.setCellValue(String.valueOf(itemAbsence.get(22)));cell.setCellStyle(style2);
           cell = row.createCell((short) 21);cell.setCellValue(String.valueOf(itemAbsence.get(23)));cell.setCellStyle(style2);
           cell = row.createCell((short) 22);cell.setCellValue(String.valueOf(itemAbsence.get(24)));cell.setCellStyle(style2);
           cell = row.createCell((short) 23);cell.setCellValue(String.valueOf(itemAbsence.get(25)));cell.setCellStyle(style2);
           cell = row.createCell((short) 24);cell.setCellValue(String.valueOf(itemAbsence.get(26)));cell.setCellStyle(style2);
           cell = row.createCell((short) 25);cell.setCellValue(String.valueOf(itemAbsence.get(27)));cell.setCellStyle(style2);
           cell = row.createCell((short) 26);cell.setCellValue(String.valueOf(itemAbsence.get(28)));cell.setCellStyle(style2);
           cell = row.createCell((short) 27);cell.setCellValue(String.valueOf(itemAbsence.get(29)));cell.setCellStyle(style2);
           cell = row.createCell((short) 28);cell.setCellValue(String.valueOf(itemAbsence.get(30)));cell.setCellStyle(style2);
           //cell = row.createCell((short) 28);cell.setCellValue(String.valueOf(itemAbsence.get(31)));cell.setCellStyle(style2);
           if(endDateMonth.getDate()==28){
               cell = row.createCell((short) 29);cell.setCellValue(String.valueOf(itemAbsence.get(31)));cell.setCellStyle(style2);
               cell = row.createCell((short) 30);cell.setCellValue("");cell.setCellStyle(style2);
               cell = row.createCell((short) 31);cell.setCellValue("");cell.setCellStyle(style2);
               cell = row.createCell((short) 32);cell.setCellValue("");cell.setCellStyle(style2);
               cell = row.createCell((short) 33);cell.setCellValue(String.valueOf(itemAbsence.get(32)));cell.setCellStyle(style2);

          }
            else if(endDateMonth.getDate()==29){
             cell = row.createCell((short) 29);cell.setCellValue(String.valueOf(itemAbsence.get(31)));cell.setCellStyle(style2);
             cell = row.createCell((short) 30);cell.setCellValue(String.valueOf(itemAbsence.get(32)));cell.setCellStyle(style2);
             cell = row.createCell((short) 31);cell.setCellValue("");cell.setCellStyle(style2);
             cell = row.createCell((short) 32);cell.setCellValue("");cell.setCellStyle(style2);
             cell = row.createCell((short) 33);cell.setCellValue(String.valueOf(itemAbsence.get(33)));cell.setCellStyle(style2);

            }
            else if (endDateMonth.getDate()==30){
                 cell = row.createCell((short) 29);cell.setCellValue(String.valueOf(itemAbsence.get(31)));cell.setCellStyle(style2);
                 cell = row.createCell((short) 30);cell.setCellValue(String.valueOf(itemAbsence.get(32)));cell.setCellStyle(style2);
                 cell = row.createCell((short) 31);cell.setCellValue(String.valueOf(itemAbsence.get(33)));cell.setCellStyle(style2);
                 cell = row.createCell((short) 32);cell.setCellValue("");cell.setCellStyle(style2);
                 cell = row.createCell((short) 33);cell.setCellValue(String.valueOf(itemAbsence.get(34)));cell.setCellStyle(style2);

            }
            else {
                 cell = row.createCell((short) 29);cell.setCellValue(String.valueOf(itemAbsence.get(31)));cell.setCellStyle(style2);
                 cell = row.createCell((short) 30);cell.setCellValue(String.valueOf(itemAbsence.get(32)));cell.setCellStyle(style2);
                 cell = row.createCell((short) 31);cell.setCellValue(String.valueOf(itemAbsence.get(33)));cell.setCellStyle(style2);
                 cell = row.createCell((short) 32);cell.setCellValue(String.valueOf(itemAbsence.get(34)));cell.setCellStyle(style2);
                 cell = row.createCell((short) 33);cell.setCellValue(String.valueOf(itemAbsence.get(35)));cell.setCellStyle(style2);
           }*/
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
    
    public static String getMinutesDeduction(String string)
    {
         int i = MonthlyEarlyXLS.getMinutesNumber(string);
         if (i <= 5) 
              return ("0");
         
         if (i <= 15) 
              return ("15");
         
         if (i <= 30) 
              return ("30");
         
         if (i <= 45) 
              return ("45");
         
         if (i > 45) 
              return ("60");
         
         return ("0");
    }
    
    public static int getMinutesNumber(String string)
    {
        // System.out.println("nilai string..."+string);
         System.out.println(new StringBuffer().append("\nin total minutes : ").append(string).toString());
         int i = 0;
         if (string != null && string.length() > 0) {
              StringTokenizer stringtokenizer = new StringTokenizer(string , ",");
              Vector vector = new Vector(1 , 1);
              int j;
              int k;
              for (;stringtokenizer.hasMoreTokens();) 
                   vector.add(stringtokenizer.nextToken());
             System.out.println("nilai vector..."+vector);
              if (vector.size() == 2) {
                   j = MonthlyEarlyXLS.getMinutesHours((String)vector.get(0));
                   k = MonthlyEarlyXLS.getMinutes((String)vector.get(1));
                   i = j + k;
              }
              else {
                   j = MonthlyEarlyXLS.getMinutes((String)vector.get(0));
                   i = j;
              }
         }
         System.out.println("\n");
         return (i);
    }

    
    public static int getMinutesHours(String string)
    {
         System.out.println(new StringBuffer().append("in getMinutesHours : ").append(string).toString());
         int i = 0;
         string = string.trim();
         if (string != null && string.length() > 0) {
              int j = string.indexOf("-");
              if (j > -1) 
                   string = string.substring(j + 1 , string.length());
              
              j = string.indexOf("h");
              if (j > -1) 
                   string = string.substring(0 , j);
              
              System.out.println(new StringBuffer().append("last str : ").append(string).toString());
              i = Integer.parseInt(string) * 60;
         }
         return (i);
    }
    
    public static int getMinutes(String string)
    {
         System.out.println(new StringBuffer().append("in getMinutes : ").append(string).toString());
         int i = 0;
         string = string.trim();
         if (string != null && string.length() > 0) {
              int j = string.indexOf("-");
              if (j > -1) 
                   string = string.substring(j + 1 , string.length());
              
              j = string.indexOf("m");
              if (j > -1) 
                   string = string.substring(0 , j);
              
              System.out.println(new StringBuffer().append("last str : ").append(string).toString());
              i = Integer.parseInt(string);
         }
         return (i);
    }
}
