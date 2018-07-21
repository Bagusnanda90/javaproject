
/*
 * SpecialQueryResultOld20130730.java
 *
 * Created on June 16, 2003, 1:32 PM
 */
 
package com.dimata.harisma.session.employee;           

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
import com.dimata.gui.jsp.*;
import com.dimata.qdep.form.*;

import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.locker.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.form.employee.*;
import com.dimata.harisma.entity.admin.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.session.employee.*;

/** 
 *
 * @author  karya
 * @version 
 */
public class SpecialQueryResultOld20130730 extends HttpServlet {
   
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
        //response.setContentType("text/html");
        /*
        response.setContentType("application/x-msexcel");
        java.io.PrintWriter out = response.getWriter();
         */
        /* output your page here */
        
        String sex      = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_SEX]);
        String resigned = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RESIGNED]);
       // System.out.println("resigned = " + resigned + " --- " + Integer.valueOf(resigned));
        String sort     = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_ORDER]);

        String workyearfrom    = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_WORKYEARFROM]);
        //System.out.println("workyearfrom = " + workyearfrom);
        String workmonthfrom   = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_WORKMONTHFROM]);
        String workyearto      = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_WORKYEARTO]);
        String workmonthto     = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_WORKMONTHTO]);

        String ageyearfrom    = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_AGEYEARFROM]);
        String agemonthfrom   = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_AGEMONTHFROM]);
        String ageyearto      = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_AGEYEARTO]);
        String agemonthto     = request.getParameter(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_AGEMONTHTO]);

        String radiocommdate = request.getParameter("radiocommdate");
        java.util.Date commdatefrom    = FRMQueryString.requestDate(request, FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_COMMDATEFROM]);
        java.util.Date commdateto      = FRMQueryString.requestDate(request, FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_COMMDATETO]);

        Vector vparam = new Vector(1,1);
        String[] arrCompany = null;
        String[] arrDepartmentId = null;
        String[] arrPositionId = null;
        String[] arrSectionId = null;
        String[] arrLevelId = null;
        String[] arrEducationId = null;
        String[] arrReligionId = null;
        String[] arrMaritalId = null;
        String[] arrBlood = null;
        String[] arrLanguageId = null;
        String[] arrRaceId = null;

       // System.out.println("\r=== specialquery_list.jsp ===");
        try {
            
            arrCompany = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_COMPANY_ID]);
            arrDepartmentId = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_DEPARTMENT]);
            arrPositionId   = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_POSITION]);
            arrSectionId    = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_SECTION]);
            arrLevelId      = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_LEVEL]);
            arrEducationId  = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_EDUCATION]);
            arrReligionId   = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RELIGION]);
            arrMaritalId    = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_MARITAL]);
            arrBlood        = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_BLOOD]);
            arrLanguageId   = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_LANGUAGE]);
            arrRaceId       = request.getParameterValues(FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RACE]);

            //System.out.println(" arrDepartmentId.length = " + arrDepartmentId.length);
            for (int i=0; i<arrDepartmentId.length; i++) {
                System.out.println(" arrDepartmentId #" + i + " = " + arrDepartmentId[i]);
            }
        }
        catch (Exception e) {
            System.out.println("Exception SpecialQuery export Excel"+e);
        }
        System.out.println("\r=============================");
        vparam.add(arrCompany);         //0
        vparam.add(arrDepartmentId);    //1
        vparam.add(arrPositionId);      //2
        vparam.add(arrSectionId);       //3
        vparam.add(arrLevelId);         //4
        
        if(arrEducationId==null){
            vparam.add(""+0);     //5
        }else{
            vparam.add(arrEducationId);     //5
        }
        vparam.add(arrReligionId);      //6
        vparam.add(arrMaritalId);       //7
       //create by satrya 2012-12-23
        if(arrBlood==null){
             vparam.add(""+0);           //8
        }else{
           vparam.add(arrBlood);           //8
        }
        vparam.add(arrLanguageId);      //9
        vparam.add(Integer.valueOf(sex));        //10
        vparam.add(Integer.valueOf(resigned));   //11
        vparam.add(radiocommdate);      //12
        vparam.add(commdatefrom);       //13
        vparam.add(commdateto);         //14

        vparam.add(workyearfrom);   //15
        vparam.add(workmonthfrom);  //16
        vparam.add(workyearto);     //17
        vparam.add(workmonthto);    //18
        vparam.add(ageyearfrom);    //19
        vparam.add(agemonthfrom);   //20
        vparam.add(ageyearto);      //21
        vparam.add(agemonthto);     //22

        vparam.add(sort);           //23
        vparam.add(arrRaceId);      //24

        Vector listEmployee = new Vector(1,1);
       //listEmployee = SessSpecialEmployee.searchSpecialEmployee(vparam);
        
        /*
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Special Query Result</title>");  
        out.println("</head>");
        out.println("<body>");
        out.println("<table>");
        out.println("<tr>" + 
                    "<td nowrap><b>PAYROLL</b></td>" + 
                    "<td nowrap><b>FULL NAME</b></td>" + 
                    "<td nowrap><b>DEPARTMENT</b></td>" + 
                    "<td nowrap><b>SECTION</b></td>" + 
                    "<td nowrap><b>POSITION</b></td>" + 
                    "<td nowrap><b>LEVEL</b></td>" + 
                    "<td nowrap><b>ADDRESS</b></td>" + 
                    "<td nowrap><b>PHONE</b></td>" + 
                    "<td nowrap><b>HAND PHONE</b></td>" + 
                    "<td nowrap><b>POSTAL CODE</b></td>" + 
                    "<td nowrap><b>SEX</b></td>" + 
                    "<td nowrap><b>BIRTH PLACE</b></td>" + 
                    "<td nowrap><b>BIRTH DATE</b></td>" + 
                    "<td nowrap><b>RELIGION</b></td>" + 
                    "<td nowrap><b>BLOOD</b></td>" + 
                    "<td nowrap><b>ASTEK NUM</b></td>" + 
                    "<td nowrap><b>ASTEK DATE</b></td>" + 
                    "<td nowrap><b>MARITAL STATUS</b></td>" + 
                    "<td nowrap><b>COMMENCING DATE</b></td>" + 
                    "</tr>");
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
            Education education = (Education) temp.get(8);
            Language language = (Language) temp.get(9);
            out.println("<tr>" +  
                        "<td nowrap>&nbsp;" + employee.getEmployeeNum() + "</td>" + 
                        "<td nowrap>" + employee.getFullName() + "</td>" + 
                        "<td nowrap>" + department.getDepartment() + "</td>" + 
                        "<td nowrap>" + section.getSection() + "</td>" + 
                        "<td nowrap>" + position.getPosition() + "</td>" + 
                        "<td nowrap>" + level.getLevel() + "</td>" + 
                        "<td nowrap>" + employee.getAddress() + "</td>" + 
                        "<td nowrap>" + employee.getPhone() + "</td>" + 
                        "<td nowrap>" + employee.getHandphone() + "</td>" + 
                        "<td nowrap>" + employee.getPostalCode() + "</td>" + 
                        "<td nowrap>" + PstEmployee.sexKey[employee.getSex()] + "</td>" + 
                        "<td nowrap>" + employee.getBirthPlace() + "</td>" + 
                        "<td nowrap>" + Formater.formatDate(employee.getBirthDate(), "dd MMM yyyy") + "</td>" + 
                        "<td nowrap>" + religion.getReligion() + "</td>" + 
                        "<td nowrap>" + employee.getBloodType() + "</td>" + 
                        "<td nowrap>" + employee.getAstekNum() + "</td>" + 
                        "<td nowrap>" + employee.getAstekDate() + "</td>" + 
                        "<td nowrap>" + marital.getMaritalCode() + "</td>" + 
                        "<td nowrap>" + Formater.formatDate(employee.getCommencingDate(), "dd MMM yyyy") + "</td>" + 
                        "</tr>");
        }
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
        
        out.close();
         */

        System.out.println("---===| Excel Report |===---");
        //response.setContentType("text/html");
        //response.setContentType("application/vnd.ms-excel");
        //response.setContentType("application/x-msexcel");
        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Special Query Result");

        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // Create a row and put some cells in it. Rows are 0 based.
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);

        String[] tableHeader = {
                    "PAYROLL","FULL NAME","DEPARTMENT","SECTION","POSITION",
                    "LEVEL","ADDRESS","PHONE","HAND PHONE","POSTAL CODE",
                    "GENDER","BIRTH PLACE","BIRTH DATE","RELIGION","BLOOD",
                    "ASTEK NUM","ASTEK DATE","MARITAL STATUS","COMMENCING DATE"
                    ,"BARCODE NUMBER", "RACE"
        };
        row = sheet.createRow((short) (0));
        for (int k = 0; k < tableHeader.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeader[k]);
            cell.setCellStyle(style3);
        }
         
        for (int i = 0; i < listEmployee.size(); i++) {
             SessTmpSpecialEmployee sessTmpSpecialEmployee = (SessTmpSpecialEmployee) listEmployee.get(i);
//            Vector temp = (Vector) listEmployee.get(i);
//            Employee employee = (Employee) temp.get(0);
//            Department department = (Department) temp.get(1);
//            Position position = (Position) temp.get(2);
//            Section section = (Section) temp.get(3);
//            EmpCategory empCategory = (EmpCategory) temp.get(4);
//            Level level = (Level) temp.get(5);
//            Religion religion = (Religion) temp.get(6);
//            Marital marital = (Marital) temp.get(7);
//            Education education = (Education) temp.get(8);
//            Language language = (Language) temp.get(9);
//            Race race = (Race) temp.get(10);

            row = sheet.createRow((short) (i+1));
            
            //"Payroll", "Full Name", "Department", "Section", "Position", 
            cell = row.createCell((short) 0);cell.setCellValue(sessTmpSpecialEmployee.getEmployeeNum());cell.setCellStyle(style2);            
            cell = row.createCell((short) 1);cell.setCellValue(sessTmpSpecialEmployee.getFullName());cell.setCellStyle(style2);
            cell = row.createCell((short) 2);cell.setCellValue(sessTmpSpecialEmployee.getDepartement());cell.setCellStyle(style2);
            cell = row.createCell((short) 3);cell.setCellValue(sessTmpSpecialEmployee.getSection());cell.setCellStyle(style2);
            cell = row.createCell((short) 4);cell.setCellValue(sessTmpSpecialEmployee.getPosition());cell.setCellStyle(style2);
            //"Level", "Address", "Phone", "Handphone", "Postal Code", 
            cell = row.createCell((short) 5);cell.setCellValue(sessTmpSpecialEmployee.getLevel());cell.setCellStyle(style2);
            cell = row.createCell((short) 6);cell.setCellValue(sessTmpSpecialEmployee.getAddressEmployee());cell.setCellStyle(style2);
            cell = row.createCell((short) 7);cell.setCellValue(sessTmpSpecialEmployee.getPhoneEmployee());cell.setCellStyle(style2);
            cell = row.createCell((short) 8);cell.setCellValue(/*sessTmpSpecialEmployee.getHandphone()*/"");cell.setCellStyle(style2);
            cell = row.createCell((short) 9);cell.setCellValue(String.valueOf(sessTmpSpecialEmployee.getPostalCodeEmployee()));cell.setCellStyle(style2);
            //"Gender", "Birth Place", "Birth Date", "Religion", "Blood Type", 
            cell = row.createCell((short) 10);cell.setCellValue(PstEmployee.sexKey[sessTmpSpecialEmployee.getSexEmployee()]);cell.setCellStyle(style2);
            cell = row.createCell((short) 11);cell.setCellValue(sessTmpSpecialEmployee.getBirthPlaceEmployee());cell.setCellStyle(style2);
            cell = row.createCell((short) 12);cell.setCellValue(sessTmpSpecialEmployee.getBirthDateEmployee()==null ? "" : String.valueOf(Formater.formatDate(sessTmpSpecialEmployee.getBirthDateEmployee(), "dd MMM yyyy")));cell.setCellStyle(style2);
            cell = row.createCell((short) 13);cell.setCellValue(sessTmpSpecialEmployee.getReligion());cell.setCellStyle(style2);
            cell = row.createCell((short) 14);cell.setCellValue(sessTmpSpecialEmployee.getBloodTypeEmployee());cell.setCellStyle(style2);
            //"Jamsostek No.", "Jamsostek Date", "Marital", "Commencing Date"
            cell = row.createCell((short) 15);cell.setCellValue(sessTmpSpecialEmployee.getAstekNumEMployee());cell.setCellStyle(style2);
            cell = row.createCell((short) 16);cell.setCellValue(sessTmpSpecialEmployee.getAsktekDate()==null ? "" : String.valueOf(Formater.formatDate(sessTmpSpecialEmployee.getAsktekDate(), "dd MMM yyyy")));cell.setCellStyle(style2);
            cell = row.createCell((short) 17);cell.setCellValue(sessTmpSpecialEmployee.getMaritalStatus());cell.setCellStyle(style2);
            cell = row.createCell((short) 18);cell.setCellValue(sessTmpSpecialEmployee.getCommercingDateEmployee()==null ? "" : String.valueOf(Formater.formatDate(sessTmpSpecialEmployee.getCommercingDateEmployee(), "dd MMM yyyy")));cell.setCellStyle(style2);
            //"Barcode Number"
            cell = row.createCell((short) 19);cell.setCellValue(sessTmpSpecialEmployee.getBarcodeNumber());cell.setCellStyle(style2);
            cell = row.createCell((short) 20);cell.setCellValue(sessTmpSpecialEmployee.getRaceName());cell.setCellStyle(style2);
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

    /** Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }

}
