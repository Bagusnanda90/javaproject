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

import com.dimata.qdep.form.*;
import com.dimata.util.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.locker.*;

/** 
 *
 * @author  karya
 * @version 
 */
public class EmployeeDetailXLS extends HttpServlet {
   
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
        long oidEmployee = FRMQueryString.requestLong(request, "oid");

        System.out.println("---===| Excel Report : " + oidEmployee + " |===---");
        //response.setContentType("text/html");
        //response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Personal Data");

        HSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);

        // Create a row and put some cells in it. Rows are 0 based.
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("PERSONAL DATA");
        cell.setCellStyle(style);

        row = sheet.createRow((short) (1));

        Employee employee = new Employee();
        Religion religion = new Religion();
        Marital marital = new Marital();
        Department department = new Department();
        Section section = new Section();
        Position position = new Position();
        EmpCategory empCategory = new EmpCategory();
        Level level = new Level();
        Locker locker = new Locker();
        try {
            employee = PstEmployee.fetchExc(oidEmployee);
            religion = PstReligion.fetchExc(employee.getReligionId());
            marital = PstMarital.fetchExc(employee.getMaritalId());
            department = PstDepartment.fetchExc(employee.getDepartmentId());
            section = PstSection.fetchExc(employee.getSectionId());
            position = PstPosition.fetchExc(employee.getPositionId());
            empCategory = PstEmpCategory.fetchExc(employee.getEmpCategoryId());
            level = PstLevel.fetchExc(employee.getLevelId());
            locker = PstLocker.fetchExc(employee.getLockerId());
        }
        catch (Exception e) {
        }
        
        row = sheet.createRow((short) (2));
        cell = row.createCell((short) 0); cell.setCellValue("Payroll");
        cell = row.createCell((short) 1); cell.setCellValue(employee.getEmployeeNum());
        row = sheet.createRow((short) (3));
        cell = row.createCell((short) 0); cell.setCellValue("Full Name");
        cell = row.createCell((short) 1); cell.setCellValue(employee.getFullName());
        row = sheet.createRow((short) (4));
        cell = row.createCell((short) 0); cell.setCellValue("Address");
        cell = row.createCell((short) 1); cell.setCellValue(employee.getAddress());
        row = sheet.createRow((short) (5));
        cell = row.createCell((short) 0); cell.setCellValue("Postal Code");
        cell = row.createCell((short) 1); cell.setCellValue(String.valueOf(employee.getPostalCode()));
        row = sheet.createRow((short) (6));
        cell = row.createCell((short) 0); cell.setCellValue("Phone");
        cell = row.createCell((short) 1); cell.setCellValue(employee.getPhone());
        row = sheet.createRow((short) (7));
        cell = row.createCell((short) 0); cell.setCellValue("Handphone");
        cell = row.createCell((short) 1); cell.setCellValue(employee.getHandphone());
        row = sheet.createRow((short) (8));
        cell = row.createCell((short) 0); cell.setCellValue("Gender");
        cell = row.createCell((short) 1); cell.setCellValue(PstEmployee.sexKey[employee.getSex()]);
        row = sheet.createRow((short) (9));
        cell = row.createCell((short) 0); cell.setCellValue("Birth Place");
        cell = row.createCell((short) 1); cell.setCellValue(employee.getBirthPlace());
        row = sheet.createRow((short) (10));
        cell = row.createCell((short) 0); cell.setCellValue("Birth Date");
        cell = row.createCell((short) 1); 
        if (employee.getBirthDate() != null) {
            cell.setCellValue(Formater.formatDate(employee.getBirthDate(), "dd MMMM yyyy"));
        }
        row = sheet.createRow((short) (11));
        cell = row.createCell((short) 0); cell.setCellValue("Religion");
        cell = row.createCell((short) 1); cell.setCellValue(religion.getReligion());
        row = sheet.createRow((short) (12));
        cell = row.createCell((short) 0); cell.setCellValue("Marital Status - Children");
        cell = row.createCell((short) 1); cell.setCellValue(marital.getMaritalStatus() + " - " + marital.getNumOfChildren());
        row = sheet.createRow((short) (13));
        cell = row.createCell((short) 0); cell.setCellValue("Blood Type");
        cell = row.createCell((short) 1); cell.setCellValue(employee.getBloodType());
        
        row = sheet.createRow((short) (15));
        cell = row.createCell((short) 0); cell.setCellValue("Department");
        cell = row.createCell((short) 1); cell.setCellValue(department.getDepartment());
        row = sheet.createRow((short) (16));
        cell = row.createCell((short) 0); cell.setCellValue("Section");
        cell = row.createCell((short) 1); cell.setCellValue(section.getSection());
        row = sheet.createRow((short) (17));
        cell = row.createCell((short) 0); cell.setCellValue("Position");
        cell = row.createCell((short) 1); cell.setCellValue(position.getPosition());
        row = sheet.createRow((short) (18));
        cell = row.createCell((short) 0); cell.setCellValue("Employee Category");
        cell = row.createCell((short) 1); cell.setCellValue(empCategory.getEmpCategory());
        row = sheet.createRow((short) (19));
        cell = row.createCell((short) 0); cell.setCellValue("Level");
        cell = row.createCell((short) 1); cell.setCellValue(level.getLevel());        
        row = sheet.createRow((short) (20));
        cell = row.createCell((short) 0); cell.setCellValue("Locker Number");
        cell = row.createCell((short) 1); cell.setCellValue(locker.getLockerNumber());        
        row = sheet.createRow((short) (21));
        cell = row.createCell((short) 0); cell.setCellValue("Jamsostek Number");
        cell = row.createCell((short) 1); cell.setCellValue(employee.getAstekNum());        
        row = sheet.createRow((short) (22));
        cell = row.createCell((short) 0); cell.setCellValue("Jamsostek Date");
        cell = row.createCell((short) 1); 
        if (employee.getAstekDate() != null) {
            try {
                cell.setCellValue(Formater.formatDate(employee.getAstekDate(), "dd MMMM yyyy"));
            }
            catch (Exception e) {
                cell.setCellValue("");
            }
        }
        row = sheet.createRow((short) (23));
        cell = row.createCell((short) 0); cell.setCellValue("Commencing Date");
        cell = row.createCell((short) 1); 
        if (employee.getCommencingDate() != null) {
            cell.setCellValue(Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy"));
        }
        row = sheet.createRow((short) (24));
        cell = row.createCell((short) 0); cell.setCellValue("Resigned");
        cell = row.createCell((short) 1); cell.setCellValue(PstEmployee.resignKey[employee.getResigned()]);

        
        HSSFSheet sheetfm = wb.createSheet("Family Member");
        HSSFRow rowfm = sheetfm.createRow((short) 0);
        HSSFCell cellfm = rowfm.createCell((short) 0);
        cellfm.setCellValue("FAMILY MEMBER");
        cellfm.setCellStyle(style);

        rowfm = sheetfm.createRow((short) (1));
        
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
