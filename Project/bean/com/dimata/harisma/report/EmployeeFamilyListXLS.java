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
public class EmployeeFamilyListXLS extends HttpServlet {
   
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
        HSSFSheet sheet = wb.createSheet("Employee List");

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
        cell.setCellValue("EMPLOYEE LIST");
        cell.setCellStyle(style);
        for (int j = 1; j < 21; j++) {
            cell = row.createCell((short) j);
            cell.setCellStyle(style);
        }
        sheet.addMergedRegion(new Region(0,(short)0,0,(short)20));

        row = sheet.createRow((short) (1));
        for (int k = 0; k < 21; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue("");
            cell.setCellStyle(style4);
        }

        String[] tableHeader = {
            "Payroll", "Full Name", "Address", "Postal Code", "Phone", 
            "Handphone", "Gender", "Birth Place", "Birth Date", "Religion", 
            "Marital", "Blood Type", "Category", "Level", "Department", 
            "Section", "Position", "Commencing Date", "Locker No.", "Jamsostek No.", 
            "Jamsostek Date", "Barcode Number", "Race", "Spouse", "Covered", "Birthdate", 
            "Child 1", "Covered", "Birthdate", "Child 2", "Covered", "Birthdate", "Child 3", "Covered", "Birthdate"
        };
        row = sheet.createRow((short) (2));
        for (int k = 0; k < tableHeader.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeader[k]);
            cell.setCellStyle(style3);
        }
        
        SessEmployee sessEmployee = new SessEmployee();
        SrcEmployee srcEmployee = new SrcEmployee();
        HttpSession session = request.getSession();
        srcEmployee = (SrcEmployee) session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
        Vector listEmployee = new Vector(1,1);
        listEmployee = sessEmployee.searchEmployeeAndFamily(srcEmployee, 0, 5000);

        //String d = "";
        for (int i = 0; i < listEmployee.size(); i++) {
            Vector temp = (Vector) listEmployee.get(i);
            Employee employee = (Employee) temp.get(0);
            Department department = (Department) temp.get(1);
            Position position = (Position) temp.get(2);
            Section section = (Section) temp.get(3);
            EmpCategory empCategory = (EmpCategory) temp.get(4);
            Level level = (Level) temp.get(5);
            Religion religion = (Religion) temp.get(6);
            Marital marital = (Marital) temp.get(7);
            Locker locker = (Locker) temp.get(8);
            Race race = (Race) temp.get(10);

            row = sheet.createRow((short) (i+3));
            
            //"Payroll", "Full Name", "Address", "Postal Code", "Phone", 
            cell = row.createCell((short) 0);cell.setCellValue(employee.getEmployeeNum());cell.setCellStyle(style2);            
            cell = row.createCell((short) 1);cell.setCellValue(employee.getFullName());cell.setCellStyle(style2);
            cell = row.createCell((short) 2);cell.setCellValue(employee.getAddress());cell.setCellStyle(style2);
            cell = row.createCell((short) 3);cell.setCellValue(String.valueOf(employee.getPostalCode()));cell.setCellStyle(style2);
            cell = row.createCell((short) 4);cell.setCellValue(employee.getPhone());cell.setCellStyle(style2);
            //"Handphone", "Gender", "Birth Place", "Birth Date", "Religion", 
            cell = row.createCell((short) 5);cell.setCellValue(employee.getHandphone());cell.setCellStyle(style2);
            cell = row.createCell((short) 6);cell.setCellValue(PstEmployee.sexKey[employee.getSex()]);cell.setCellStyle(style2);
            cell = row.createCell((short) 7);cell.setCellValue(employee.getBirthPlace());cell.setCellStyle(style2);
            cell = row.createCell((short) 8);cell.setCellValue(employee.getBirthDate()==null ? "" : String.valueOf(Formater.formatDate(employee.getBirthDate(), "dd MMM yyyy")));cell.setCellStyle(style2);
            cell = row.createCell((short) 9);cell.setCellValue(religion.getReligion());cell.setCellStyle(style2);
            //"Marital", "Blood Type", "Category", "Level", "Department", 
            cell = row.createCell((short) 10);cell.setCellValue(marital.getMaritalCode());cell.setCellStyle(style2);
            cell = row.createCell((short) 11);cell.setCellValue(employee.getBloodType());cell.setCellStyle(style2);
            cell = row.createCell((short) 12);cell.setCellValue(empCategory.getEmpCategory());cell.setCellStyle(style2);
            cell = row.createCell((short) 13);cell.setCellValue(level.getLevel());cell.setCellStyle(style2);
            cell = row.createCell((short) 14);cell.setCellValue(department.getDepartment());cell.setCellStyle(style2);
            //"Section", "Position", "Commencing Date", "Locker No.", "Jamsostek No.", 
            cell = row.createCell((short) 15);cell.setCellValue(section.getSection());cell.setCellStyle(style2);
            cell = row.createCell((short) 16);cell.setCellValue(position.getPosition());cell.setCellStyle(style2);
            cell = row.createCell((short) 17);cell.setCellValue(employee.getCommencingDate()==null ? "" : String.valueOf(Formater.formatDate(employee.getCommencingDate(), "dd MMM yyyy")));cell.setCellStyle(style2);
            cell = row.createCell((short) 18);cell.setCellValue(locker.getLockerNumber());cell.setCellStyle(style2);
            cell = row.createCell((short) 19);cell.setCellValue(employee.getAstekNum());cell.setCellStyle(style2);
            //"Jamsostek Date", "Barcode Number"
            cell = row.createCell((short) 20);cell.setCellValue(employee.getAstekDate()==null ? "" : String.valueOf(Formater.formatDate(employee.getAstekDate(),"dd MMM yyyy")));cell.setCellStyle(style2);
            cell = row.createCell((short) 21);cell.setCellValue(employee.getBarcodeNumber());cell.setCellStyle(style2);
            cell = row.createCell((short) 22);cell.setCellValue(race.getRaceName());cell.setCellStyle(style2);
            
             short cIdx =22;
                FamilyMemberList famList = employee.getFamilyMemberList();
                if(famList!=null){
                    if(famList.getSpouse()!=null){
                        cell = row.createCell((short) ++cIdx);cell.setCellValue(famList.getSpouse().getFullName());cell.setCellStyle(style2);                                    
                        cell = row.createCell((short) ++cIdx);cell.setCellValue((famList.getSpouse().getGuaranteed()? "Yes" :"No"));cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue(Formater.formatDate(famList.getSpouse().getBirthDate(),"dd MMM yyyy"));cell.setCellStyle(style2);            
                    } else{
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                    }                      
                    if(famList.getChild(0)!=null){
                        cell = row.createCell((short) ++cIdx);cell.setCellValue(famList.getChild(0).getFullName());cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue((famList.getChild(0).getGuaranteed()? "Yes" :"No"));cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue(Formater.formatDate(famList.getChild(0).getBirthDate(),"dd MMM yyyy"));cell.setCellStyle(style2);            
                    }else{
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                    } 
                    if(famList.getChild(1)!=null){
                        cell = row.createCell((short) ++cIdx);cell.setCellValue(famList.getChild(1).getFullName());cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue((famList.getChild(1).getGuaranteed()? "Yes" :"No"));cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue(Formater.formatDate(famList.getChild(1).getBirthDate(),"dd MMM yyyy"));cell.setCellStyle(style2);            
                    }else{
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                    } 
                    if(famList.getChild(2)!=null){
                        cell = row.createCell((short) ++cIdx);cell.setCellValue(famList.getChild(2).getFullName());cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue((famList.getChild(2).getGuaranteed()? "Yes" :"No"));cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue(Formater.formatDate(famList.getChild(2).getBirthDate(),"dd MMM yyyy"));cell.setCellStyle(style2);            
                    }else{
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                    } 

                } else {                    
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                        cell = row.createCell((short) ++cIdx);cell.setCellValue("");cell.setCellStyle(style2);            
                }
            
            
            
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
