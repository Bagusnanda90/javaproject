/*
 * EmpScheduleListXLS.java
 *
 * Created on October 16, 2002, 12:08 PM
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

import com.dimata.util.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.locker.*;
import com.dimata.harisma.session.locker.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;

/** 
 *
 * @author  karya
 * @version 
 */
public class LockerListXLS extends HttpServlet {
   
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
        System.out.println("\t---===| Locker Excel Report |===---");
        //response.setContentType("text/html");
        //response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Locker List");

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

        // Create a row and put some cells in it. Rows are 0 based.
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("LOCKER LIST");
        cell.setCellStyle(style);
        for (int j = 1; j < 6; j++) {
            cell = row.createCell((short) j);
            cell.setCellStyle(style);
        }
        sheet.addMergedRegion(new Region(0,(short)0,0,(short)5));

        row = sheet.createRow((short) (1));
        for (int k = 0; k < 5; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue("");
            cell.setCellStyle(style4);
        }

        String[] tableHeader = {            
            "Locker Number", 
            "Location",
            "Key Number", 
            "Spare Key", 
            "Condition",
            "Employee"
        };
        row = sheet.createRow((short) (2));
        for (int k = 0; k < tableHeader.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeader[k]);
            cell.setCellStyle(style3);
        }
        
        SessLocker sessLocker = new SessLocker();
        SrcLocker srcLocker = new SrcLocker();
        HttpSession session = request.getSession();
        srcLocker = (SrcLocker)session.getValue(SessLocker.SESS_SRC_LOCKER);
        Vector listLocker = new Vector(1,1);
        listLocker = sessLocker.searchLocker(srcLocker, 0, 5000);

        //String d = "";
        for (int i = 0; i < listLocker.size(); i++) {
            Vector temp = (Vector) listLocker.get(i);
            Locker locker = (Locker) temp.get(0);
            LockerLocation lockerlocation = (LockerLocation) temp.get(1);
            LockerCondition lockercondition = (LockerCondition) temp.get(2);

            row = sheet.createRow((short) (i+3));
            
            //"Location", "Locker Number", "Key Number", "Spare Key", "Condition"           
            cell = row.createCell((short) 0);cell.setCellValue(locker.getLockerNumber());cell.setCellStyle(style2);
            cell = row.createCell((short) 1);cell.setCellValue(lockerlocation.getLocation());cell.setCellStyle(style2);            
            cell = row.createCell((short) 2);cell.setCellValue(locker.getKeyNumber());cell.setCellStyle(style2);
            cell = row.createCell((short) 3);cell.setCellValue(locker.getSpareKey());cell.setCellStyle(style2);
            cell = row.createCell((short) 4);cell.setCellValue(lockercondition.getCondition());cell.setCellStyle(style2);
            
            String empData = "";
                
            try {
                String where = PstEmployee.fieldNames[PstEmployee.FLD_LOCKER_ID] + "=" + locker.getOID();
                Vector list = PstEmployee.list(0, 0, where, "");

                if(list != null && list.size()>0) {
                    for(int j=0; j<list.size(); j++) {
                        Employee emp = (Employee)list.get(j);
                        String deptName = "";

                        try {
                            Department dept = PstDepartment.fetchExc(emp.getDepartmentId());
                            deptName = dept.getDepartment();
                        }
                        catch(Exception e) {}

                        empData += "(" + emp.getEmployeeNum() + ") " +
                                   emp.getFullName() + " - " +
                                   deptName + "\n";

                    }
                }
            }
            catch(Exception e) {}

            cell = row.createCell((short) 5);cell.setCellValue(empData);cell.setCellStyle(style2);
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
