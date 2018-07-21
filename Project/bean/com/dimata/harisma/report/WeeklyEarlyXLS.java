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
public class WeeklyEarlyXLS  extends HttpServlet{
    
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
    public WeeklyEarlyXLS() {
    }
    
     public static String getStrWeek(int idx){
        String str = "";
        switch(idx){
            case 1:
                str = "WEEK I";
                break;
            case 2:
                str = "WEEK II";
                break;
            case 3:
                str = "WEEK III";
                break;
            case 4:
                str = "WEEK IV";
                break;
            case 5:
                str = "WEEK V";
                break;
            case 6:
                str = "WEEK VI";
                break;
            case 7:
                str = "WEEK VII";
                break;
        }
        return str;
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
        HSSFSheet sheet = wb.createSheet("Early Home Weekly");

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
        style2.setBorderLeft(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderRight(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderTop(HSSFCellStyle.SOLID_FOREGROUND);

        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style3.setBorderLeft(HSSFCellStyle.SOLID_FOREGROUND);
        style3.setBorderRight(HSSFCellStyle.SOLID_FOREGROUND);
        style3.setBorderTop(HSSFCellStyle.SOLID_FOREGROUND);
        //style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style4 = wb.createCellStyle();
        style4.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
         /* get data from session */
            HttpSession sessEmpPresence = request.getSession(true);
            Vector vctMonthlyAbsence = null;
            try{
	        vctMonthlyAbsence = (Vector)sessEmpPresence.getValue("WEEKLY_EARLY_HOME");
            }catch(Exception e){
                System.out.println("Exc : "+e.toString());
        }
       
        
        String idx = "";
        String departmentId = "";
        Vector listWeeklyEarly = new Vector(1,1);
        Date date = new Date();
        Date startDate = new Date();
        Date endDate = new Date();
        String departmentName = "";
        
         if(vctMonthlyAbsence != null && vctMonthlyAbsence.size()==7){
                try{
                        //System.out.println("masuk trydfjdjhfj.............");
                        listWeeklyEarly = (Vector)vctMonthlyAbsence.get(5);
	            	idx = (String) vctMonthlyAbsence.get(0);
                        departmentId = (String)vctMonthlyAbsence.get(1);
                        date = (Date) vctMonthlyAbsence.get(2);
                        startDate = (Date) vctMonthlyAbsence.get(3);
                        endDate = (Date) vctMonthlyAbsence.get(4);
                        
                        if(departmentId.length()>0){
                            try{
                                Department department = new Department();
                                department = PstDepartment.fetchExc(Long.parseLong(departmentId));
                                departmentName = department.getDepartment();
                            }catch(Exception e){
                                System.out.println("err get department"+e.toString());
                                
                            }
                        }
                        
                        if(departmentId.equals("")){
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
        String week = getStrWeek(Integer.parseInt(idx));
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("LIST EARLY HOME WEEKLY "+week+" OF "+Formater.formatDate(date,"MMMM yyyy").toUpperCase());
      
        
       row = sheet.createRow((short) (1));
       cell = row.createCell((short) 0); cell.setCellValue("DEPATMENT :"+departmentName.toUpperCase());
       cell.setCellStyle(style);
       
        int numColum = ((endDate.getDate() - startDate.getDate())+3);
        Vector dateColum = new Vector();
        for (int j = 1; j < 11; j++) {
           cell = row.createCell((short) j);
           cell.setCellStyle(style);
          
        }
        for(int d=0;d<=((endDate.getDate() - startDate.getDate()));d++){
            int dt = (startDate.getDate()+d);
            dateColum.add(""+dt);
        }
        
       
        int selisih = 7-dateColum.size();
        
        if(dateColum.size() < 7 ){
            for(int t=0;t<selisih;t++){
                dateColum.add("");
            }
        }
        
        sheet.addMergedRegion(new Region(0,(short)0,0,(short)20));

        row = sheet.createRow((short) (2));
        for (int k = 0; k < 10; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue("");
            cell.setCellStyle(style4);
        }
        
        
        String[] tableHeader = {
            "PAYROLL", 
            "EMPLOYEE", 
            ""+dateColum.get(0)+"",
            ""+dateColum.get(1)+"",
            ""+dateColum.get(2)+"",
            ""+dateColum.get(3)+"",
            ""+dateColum.get(4)+"",
            ""+dateColum.get(5)+"",
            ""+dateColum.get(6)+"",
            "TOTAL EARLY HOME"
            
        };
        row = sheet.createRow((short) (3));
        for (int k = 0; k < tableHeader.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeader[k]);
            cell.setCellStyle(style3);
        }
        
      
        //listEmployee = sessEmployee.getEmpSalary(secSelect,oidPeriod,departmentId);

        //String d = "";
        for (int i = 0; i < listWeeklyEarly.size(); i++) {
            Vector itemAbsence = (Vector) listWeeklyEarly.get(i);
            int maxDate = (endDate.getDate() - startDate.getDate());
            int sht=2;
            row = sheet.createRow((short) (i+4));
            cell = row.createCell((short) 0);cell.setCellValue(String.valueOf(itemAbsence.get(2)));cell.setCellStyle(style2); 
            cell = row.createCell((short) 1);cell.setCellValue(String.valueOf(itemAbsence.get(3)));cell.setCellStyle(style2);
            for(int cnt=0;cnt<=maxDate;cnt++){
                //sht = 2;
                cell = row.createCell((short) sht);cell.setCellValue(String.valueOf(itemAbsence.get(4+cnt)));cell.setCellStyle(style2);      
                sht = sht +1;
                
            }
            if(maxDate < 7){
                int cntSelisih = (7-maxDate);
                for(int add=0;add < cntSelisih;add++){
                    cell = row.createCell((short) sht);cell.setCellValue("");cell.setCellStyle(style2); 
                    sht = sht +1;
                }
            }
           
            int index=numColum+2 ;
            cell = row.createCell((short) 9);cell.setCellValue(String.valueOf(itemAbsence.get(index)));cell.setCellStyle(style2); 
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
