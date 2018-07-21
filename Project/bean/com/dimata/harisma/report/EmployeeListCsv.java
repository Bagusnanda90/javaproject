/*
 * EmpScheduleListXLS.java
 *
 * Created on October 16, 2002, 12:08 PM
 */
package com.dimata.harisma.report;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

//test aja
import com.dimata.qdep.form.*;
//
import java.sql.*;//tambah
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
import java.lang.ref.Reference;

/** 
 *
 * @author  karya
 * @version 
 */
public class EmployeeListCsv extends HttpServlet {

    /** Initializes the servlet.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    /** Destroys the servlet.
     */
    public void destroy() {
    }

    
    //}

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
        /* BY KARTIKA , di hide sementara tgl. 25 September 2012
        HSSFSheet sheetFamily = wb.createSheet("Family List");
        HSSFSheet sheetCarrier = wb.createSheet("Caarier List");
        **/

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
        /*for (int j = 1; j < 34; j++) {
        cell = row.createCell((short) j);
        cell.setCellStyle(style);
        }


        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 20));

        row = sheet.createRow((short) (1));
        for (int k = 0; k < 34; k++) {
        cell = row.createCell((short) k);
        cell.setCellValue("");
        cell.setCellStyle(style4);
        } SEMENTARA INI BELUM TAU FUNGSINYA UNTUK APA, TAPI JK DIHILANGKAN, MENGURANGI SEDIKIT SIZE FILE
         */
        
/* BY KARTIKA , di hide sementara tgl. 25 September 2012        
        row = sheetFamily.createRow((short) 0);
        cell = row.createCell((short) 0);
        cell.setCellValue("FAMILY LIST");
        cell.setCellStyle(style);

        row = sheetCarrier.createRow((short) 0);
        cell = row.createCell((short) 0);
        cell.setCellValue("CARRIER LIST");
        cell.setCellStyle(style);
*/

        //untuk personal data
        String[] tableSubHeader = {
            "Payroll", "Full Name", "Address", "Postal Code", "Phone",
            "Handphone", "Gender", "Birth Place", "Birth Date", "Religion",
            "Marital", "Blood Type", "Category", "Level", "Company", "Division", "Department",
            "Section", "Position", "Commencing Date", "Locker No.", "Jamsostek No.",
            "Jamsostek Date", "Barcode Number", "Race"
        };
        int[] batas;
        batas = new int[7];
        batas[0] = tableSubHeader.length;
        row = sheet.createRow((short) (2));
        for (int k = 0; k < tableSubHeader.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableSubHeader[k]);
            cell.setCellStyle(style3);
        } // }

        SessEmployee sessEmployee = new SessEmployee();
        SrcEmployee srcEmployee = new SrcEmployee();
        HttpSession session = request.getSession();
        srcEmployee = (SrcEmployee) session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
        Vector listEmployee = new Vector(1, 1);
        listEmployee = sessEmployee.searchEmployee(srcEmployee, 0, 5000);
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
            Division division = (Division) temp.get(9);
            Company company = (Company) temp.get(11);

            row = sheet.createRow((short) (i + 3));

            //"Payroll", "Full Name", "Address", "Postal Code", "Phone", 
            cell = row.createCell((short) 0);
            cell.setCellValue(""+employee.getEmployeeNum());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 1);
            cell.setCellValue(""+employee.getFullName());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 2);
            cell.setCellValue(""+employee.getAddress());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 3);
            cell.setCellValue(String.valueOf(employee.getPostalCode()));
            cell.setCellStyle(style2);
            cell = row.createCell((short) 4);
            cell.setCellValue(""+employee.getPhone());
            cell.setCellStyle(style2);
            //"Handphone", "Gender", "Birth Place", "Birth Date", "Religion", 
            cell = row.createCell((short) 5);
            cell.setCellValue(""+employee.getHandphone());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 6);
            cell.setCellValue(PstEmployee.sexKey[employee.getSex()]);
            cell.setCellStyle(style2);
            cell = row.createCell((short) 7);
            cell.setCellValue(""+employee.getBirthPlace());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 8);
            cell.setCellValue(""+employee.getBirthDate() == null ? "" : String.valueOf(Formater.formatDate(employee.getBirthDate(), "dd MMM yyyy")));
            cell.setCellStyle(style2);
            cell = row.createCell((short) 9);
            cell.setCellValue(religion.getReligion());
            cell.setCellStyle(style2);
            //"Marital", "Blood Type", "Category", "Level", "Department", 
            cell = row.createCell((short) 10);
            cell.setCellValue(marital.getMaritalCode());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 11);
            cell.setCellValue(""+employee.getBloodType());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 12);
            cell.setCellValue(empCategory.getEmpCategory());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 13);
            cell.setCellValue(level.getLevel());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 14);
            cell.setCellValue(company.getCompany());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 15);
            cell.setCellValue(division.getDivision());
            cell.setCellStyle(style2);

            cell = row.createCell((short) 16);
            cell.setCellValue(department.getDepartment());
            cell.setCellStyle(style2);
            //"Section", "Position", "Commencing Date", "Locker No.", "Jamsostek No.", 
            cell = row.createCell((short) 17);
            cell.setCellValue(section.getSection());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 18);
            cell.setCellValue(position.getPosition());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 19);
            cell.setCellValue(""+ (employee.getCommencingDate() == null ? "" : String.valueOf(Formater.formatDate(employee.getCommencingDate(), "dd MMM yyyy"))));
            cell.setCellStyle(style2);
            cell = row.createCell((short) 20);
            cell.setCellValue(locker.getLockerNumber());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 21);
            cell.setCellValue(""+employee.getAstekNum());
            cell.setCellStyle(style2);
            //"Jamsostek Date", "Barcode Number"
            cell = row.createCell((short) 22);
            cell.setCellValue(""+ (employee.getAstekDate() == null ? "" : String.valueOf(Formater.formatDate(employee.getAstekDate(), "dd MMM yyyy"))));
            cell.setCellStyle(style2);
            cell = row.createCell((short) 23);
            cell.setCellValue(""+employee.getBarcodeNumber());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 24);
            cell.setCellValue(race.getRaceName());
            cell.setCellStyle(style2);
        }
        row = sheet.createRow((short) (listEmployee.size() + 4)); //ngakalin yang ilang saat ganjil
        cell = row.createCell((short) 0);
        cell.setCellValue("");
        cell.setCellStyle(style2);
        
/* BY KARTIKA , di hide sementara tgl. 25 September 2012        
        row = sheetFamily.createRow((short) (listEmployee.size() + 4)); //ngakalin yang ilang saat ganjil
        cell = row.createCell((short) 0);
        cell.setCellValue("");
        cell.setCellStyle(style2);
        row = sheetCarrier.createRow((short) (listEmployee.size() + 4)); //ngakalin yang ilang saat ganjil
        cell = row.createCell((short) 0);
        cell.setCellValue("");
        cell.setCellStyle(style2);


        int akhir = 0;
        //int batas = 0;
        int awal = 0;
        batas[1] = 0;
        batas[2] = 0;
        batas[3] = 0;
        batas[4] = 0;
        batas[5] = 0;
        batas[6] = 0;


        //untuk language

        int loop = PstEmpLanguage.getCount2(srcEmployee);
        if (loop > 0) {
            int b = tableSubHeader.length;
            for (int a = 0; a < loop; a++) {

                String[] tableSubHeader2 = {
                    "Language", "Oral", "Written", "Description"
                };
                awal = tableSubHeader.length + (tableSubHeader2.length * a);
                akhir = tableSubHeader2.length + tableSubHeader.length + (tableSubHeader2.length * a);
                batas[1] = akhir;

                row = sheet.createRow((short) (2));

                for (int k = awal; k < akhir; k++) {

                    cell = row.createCell((short) k);
                    cell.setCellValue(tableSubHeader2[k - (tableSubHeader.length + (tableSubHeader2.length * a))]);
                    cell.setCellStyle(style3);

                }

                for (int i = 0; i < listEmployee.size(); i++) {
                    Vector temp = (Vector) listEmployee.get(i);
                    Employee employee = (Employee) temp.get(0);

                    row = sheet.createRow((short) (i + 3));
                    Vector vctLanguage = new Vector(1, 1);
                    long oidEmployee = employee.getOID();
                    String whereClause = PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID] + " = " + oidEmployee;
                    String orderClause = "";
                    vctLanguage = PstEmpLanguage.list(0, 0, whereClause, orderClause);
                    EmpLanguage empLanguage = new EmpLanguage();

                    Education education = new Education();
                    Religion religion = new Religion();

                    if (vctLanguage != null && vctLanguage.size() > a) {
                        empLanguage = (EmpLanguage) vctLanguage.get(a);

                        String lang = String.valueOf(empLanguage.getLanguageId());
                        String langName = "";
                        langName = PstEmpLanguage.getLang(lang);



                        cell = row.createCell((short) b);
                        cell.setCellValue(PstEmpLanguage.getLang(lang));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 1));
                        cell.setCellValue(empLanguage.getOral() == 0 ? "Good" : empLanguage.getOral() == 1 ? "Fair" : "Poor");
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 2));
                        cell.setCellValue(empLanguage.getWritten() == 0 ? "Good" : empLanguage.getWritten() == 1 ? "Fair" : "Poor");
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 3));
                        cell.setCellValue(empLanguage.getDescription());
                        cell.setCellStyle(style2);
                    }

                }
                b = b + tableSubHeader2.length;
            }
        } else {
            batas[1] = batas[0];
            akhir = tableSubHeader.length;
        }

        //untuk education
        int loop2 = PstEmpEducation.getCount2(srcEmployee);
        int akhir2 = 0;
        if (loop2 > 0) {
            int b = akhir + akhir2;
            for (int a = 0; a < loop2; a++) {

                String[] tableSubHeader3 = {
                    "Education", "Education Desc", "Start Date", "End Date", "Graduation"
                };
                awal = akhir + akhir2;
                akhir2 = batas[1] + tableSubHeader3.length + (tableSubHeader3.length * a);
                akhir = 0;
                batas[2] = akhir2;
                row = sheet.createRow((short) (2));


                for (int k = awal; k < akhir2; k++) {

                    cell = row.createCell((short) k);
                    cell.setCellValue(tableSubHeader3[k - (batas[1] + (tableSubHeader3.length * a))]);
                    cell.setCellStyle(style3);

                }
                for (int i = 0; i < listEmployee.size(); i++) {
                    Vector temp = (Vector) listEmployee.get(i);
                    Employee employee = (Employee) temp.get(0);

                    row = sheet.createRow((short) (i + 3));
                    Vector vctEducation = new Vector(1, 1);
                    long oidEmployee = employee.getOID();
                    String whereClause = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID] + " = " + oidEmployee;
                    String orderClause = PstEmpEducation.fieldNames[PstEmpEducation.FLD_START_DATE];
                    vctEducation = PstEmpEducation.list(0, 0, whereClause, orderClause);
                    EmpEducation empEducation = new EmpEducation();

                    if (vctEducation != null && vctEducation.size() > a) {
                        empEducation = (EmpEducation) vctEducation.get(a);
                        String edu = String.valueOf(empEducation.getEducationId());
                        String eduName = "";
                        eduName = PstEmpEducation.getEducation(edu);

                        cell = row.createCell((short) b);
                        cell.setCellValue(PstEmpEducation.getEducation(edu));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 1));
                        cell.setCellValue(empEducation.getEducationDesc());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 2));
                        cell.setCellValue(empEducation.getStartDate());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 3));
                        cell.setCellValue(empEducation.getEndDate());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 4));
                        cell.setCellValue(empEducation.getGraduation());
                        cell.setCellStyle(style2);
                    }

                }
                b = b + tableSubHeader3.length;


            }
        } else {
            batas[2] = batas[1];
            akhir2 = akhir;
        }

        //untuk experience
        int loop3 = PstExperience.getCount2(srcEmployee);
        int akhir3 = 0;
        if (loop3 > 0) {
            int b = akhir2 + akhir3;
            for (int a = 0; a < loop3; a++) {

                String[] tableSubHeader4 = {
                    "Company Name", "Start Date", "End Date", "Position", "Move Reason"
                };
                awal = akhir2 + akhir3;
                akhir3 = batas[2] + tableSubHeader4.length + (tableSubHeader4.length * a);
                akhir2 = 0;
                batas[3] = akhir3;
                row = sheet.createRow((short) (2));

                for (int k = awal; k < akhir3; k++) {
                    cell = row.createCell((short) k);
                    cell.setCellValue(tableSubHeader4[k - (batas[2] + (tableSubHeader4.length * a))]);
                    cell.setCellStyle(style3);
                }
                for (int i = 0; i < listEmployee.size(); i++) {
                    Vector temp = (Vector) listEmployee.get(i);
                    Employee employee = (Employee) temp.get(0);

                    row = sheet.createRow((short) (i + 3));
                    Vector vctExperience = new Vector(1, 1);
                    long oidEmployee = employee.getOID();
                    String whereClause = PstExperience.fieldNames[PstExperience.FLD_EMPLOYEE_ID] + " = " + oidEmployee;
                    String orderClause = PstExperience.fieldNames[PstExperience.FLD_START_DATE];
                    vctExperience = PstExperience.list(0, 0, whereClause, orderClause);
                    Experience experience = new Experience();



                    if (vctExperience != null && vctExperience.size() > a) {
                        experience = (Experience) vctExperience.get(a);

                        cell = row.createCell((short) b);
                        cell.setCellValue(experience.getCompanyName());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 1));
                        cell.setCellValue(experience.getStartDate());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 2));
                        cell.setCellValue(experience.getEndDate());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 3));
                        cell.setCellValue(experience.getPosition());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 4));
                        cell.setCellValue(experience.getMoveReason());
                        cell.setCellStyle(style2);
                    }

                }
                b = b + tableSubHeader4.length;

            }
        } else {
            batas[3] = batas[2];
            akhir3 = akhir2;
        }

        //untuk warning
        int loop4 = PstEmpWarning.getCount2(srcEmployee);
        int akhir4 = 0;
        if (loop4 > 0) {
            int b = akhir3 + akhir4;
            for (int a = 0; a < loop4; a++) {

                String[] tableSubHeader5 = {
                    "Break Date", "Break Fact", "Warning Date", "Warning By", "Valid Until", "Level", "Point"
                };
                awal = akhir3 + akhir4;
                akhir4 = batas[3] + tableSubHeader5.length + (tableSubHeader5.length * a);
                akhir3 = 0;
                batas[4] = akhir4;
                row = sheet.createRow((short) (2));

                for (int k = awal; k < akhir4; k++) {
                    cell = row.createCell((short) k);
                    cell.setCellValue(tableSubHeader5[k - (batas[3] + (tableSubHeader5.length * a))]);
                    cell.setCellStyle(style3);
                }
                for (int i = 0; i < listEmployee.size(); i++) {
                    Vector temp = (Vector) listEmployee.get(i);
                    Employee employee = (Employee) temp.get(0);

                    row = sheet.createRow((short) (i + 3));
                    Vector vctWarning = new Vector(1, 1);
                    long oidEmployee = employee.getOID();
                    String whereClause = PstEmpWarning.fieldNames[PstEmpWarning.FLD_EMPLOYEE_ID] + "=" + oidEmployee;
                    String orderClause = PstEmpWarning.fieldNames[PstEmpWarning.FLD_BREAK_DATE];
                    vctWarning = PstEmpWarning.list(0, 0, whereClause, orderClause);
                    EmpWarning empWarning = new EmpWarning();



                    if (vctWarning != null && vctWarning.size() > a) {
                        empWarning = (EmpWarning) vctWarning.get(a);
                        String warningLevel = "";
                        String level = String.valueOf(empWarning.getWarnLevelId());
                        warningLevel = PstEmpWarning.getWarning(level);
                        String warningPoint = "";
                        String pointLvl = String.valueOf(empWarning.getWarnLevelId());
                        warningPoint = PstEmpWarning.getWarningPoint(pointLvl);

                        cell = row.createCell((short) b);
                        cell.setCellValue(empWarning.getBreakDate() == null ? "" : String.valueOf(Formater.formatDate(empWarning.getBreakDate(), "dd MMM yyyy")));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 1));
                        cell.setCellValue(empWarning.getBreakFact());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 2));
                        cell.setCellValue(empWarning.getWarningDate() == null ? "" : String.valueOf(Formater.formatDate(empWarning.getWarningDate(), "dd MMM yyyy")));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 3));
                        cell.setCellValue(empWarning.getWarningBy());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 4));
                        cell.setCellValue(empWarning.getValidityDate() == null ? "" : String.valueOf(Formater.formatDate(empWarning.getValidityDate(), "dd MMM yyyy")));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 5));
                        cell.setCellValue(PstEmpWarning.getWarning(level));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 6));
                        cell.setCellValue(PstEmpWarning.getWarningPoint(pointLvl));
                        cell.setCellStyle(style2);//Point ??
                    }

                }
                b = b + tableSubHeader5.length;

            }
        } else {
            batas[4] = batas[3];
            akhir4 = akhir3;
        }

        //untuk reprimand
        int loop5 = PstEmpReprimand.getCount2(srcEmployee);
        int akhir5 = 0;
        if (loop5 > 0) {
            int b = akhir4 + akhir5;
            for (int a = 0; a < loop5; a++) {

                String[] tableSubHeader6 = {
                    "Date", "Chapter", "Article", "Page", "Description", "Valid Until", "Level", "Point"
                };
                awal = akhir4 + akhir5;
                akhir5 = batas[4] + tableSubHeader6.length + (tableSubHeader6.length * a);
                akhir4 = 0;
                batas[5] = akhir5;
                row = sheet.createRow((short) (2));

                for (int k = awal; k < akhir5; k++) {
                    cell = row.createCell((short) k);
                    cell.setCellValue(tableSubHeader6[k - (batas[4] + (tableSubHeader6.length * a))]);
                    cell.setCellStyle(style3);
                }
                for (int i = 0; i < listEmployee.size(); i++) {
                    Vector temp = (Vector) listEmployee.get(i);
                    Employee employee = (Employee) temp.get(0);

                    row = sheet.createRow((short) (i + 3));
                    Vector vctReprimand = new Vector(1, 1);
                    long oidEmployee = employee.getOID();
                    String whereClause = PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_EMPLOYEE_ID] + "=" + oidEmployee;
                    String orderClause = PstEmpReprimand.fieldNames[PstEmpReprimand.FLD_REP_DATE];
                    vctReprimand = PstEmpReprimand.list(0, 0, whereClause, orderClause);
                    EmpReprimand empReprimand = new EmpReprimand();



                    if (vctReprimand != null && vctReprimand.size() > a) {
                        empReprimand = (EmpReprimand) vctReprimand.get(a);
                        String reprimandLevel = "";
                        String level = String.valueOf(empReprimand.getReprimandLevelId());
                        reprimandLevel = PstEmpReprimand.getReprimand(level);
                        String reprimandPoint = "";
                        String pointLvl = String.valueOf(empReprimand.getReprimandLevelId());
                        reprimandPoint = PstEmpReprimand.getReprimandPoint(pointLvl);

                        cell = row.createCell((short) b);
                        cell.setCellValue(empReprimand.getReprimandDate() == null ? "" : String.valueOf(Formater.formatDate(empReprimand.getReprimandDate(), "dd MMM yyyy")));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 1));
                        cell.setCellValue(empReprimand.getChapter());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 2));
                        cell.setCellValue(empReprimand.getArticle());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 3));
                        cell.setCellValue(empReprimand.getPage());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 4));
                        cell.setCellValue(empReprimand.getDescription());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 5));
                        cell.setCellValue(empReprimand.getValidityDate() == null ? "" : String.valueOf(Formater.formatDate(empReprimand.getValidityDate(), "dd MMM yyyy")));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 6));
                        cell.setCellValue(PstEmpReprimand.getReprimand(level));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 7));
                        cell.setCellValue(PstEmpReprimand.getReprimandPoint(pointLvl));
                        cell.setCellStyle(style2);
                    }

                }
                b = b + tableSubHeader6.length;

            }
        } else {
            batas[5] = batas[4];
            akhir5 = akhir4;
        }

        //untuk award
        int loop6 = PstEmpAward.getCount2(srcEmployee);
        int akhir6 = 0;
        if (loop6 > 0) {
            int b = akhir5 + akhir6;
            for (int a = 0; a < loop6; a++) {

                String[] tableSubHeader7 = {
                    "Date", "Dept. On Award", "Sect. On Award", "Type", "Description"
                };
                awal = akhir5 + akhir6;
                akhir6 = batas[5] + tableSubHeader7.length + (tableSubHeader7.length * a);
                akhir5 = 0;
                batas[6] = akhir6;
                row = sheet.createRow((short) (2));

                for (int k = awal; k < akhir6; k++) {
                    cell = row.createCell((short) k);
                    cell.setCellValue(tableSubHeader7[k - (batas[5] + (tableSubHeader7.length * a))]);
                    cell.setCellStyle(style3);
                }
                for (int i = 0; i < listEmployee.size(); i++) {
                    Vector temp = (Vector) listEmployee.get(i);
                    Employee employee = (Employee) temp.get(0);

                    row = sheet.createRow((short) (i + 3));
                    Vector vctAward = new Vector(1, 1);
                    long oidEmployee = employee.getOID();
                    String whereClause = PstEmpAward.fieldNames[PstEmpAward.FLD_EMPLOYEE_ID] + "=" + oidEmployee;
                    String orderClause = PstEmpAward.fieldNames[PstEmpAward.FLD_AWARD_DATE];
                    vctAward = PstEmpAward.list(0, 0, whereClause, orderClause);
                    EmpAward empAward = new EmpAward();



                    if (vctAward != null && vctAward.size() > a) {
                        empAward = (EmpAward) vctAward.get(a);
                        String awardLevel = "";
                        String level = String.valueOf(empAward.getAwardType());
                        awardLevel = PstEmpAward.getAward(level);
                        String department = "";
                        String dept = String.valueOf(empAward.getDepartmentId());
                        PstEmpAward.getDepartment(dept);
                        String section = "";
                        String sect = String.valueOf(empAward.getSectionId());
                        PstEmpAward.getSection(sect);



                        cell = row.createCell((short) b);
                        cell.setCellValue(empAward.getAwardDate() == null ? "" : String.valueOf(Formater.formatDate(empAward.getAwardDate(), "dd MMM yyyy")));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 1));
                        cell.setCellValue(PstEmpAward.getDepartment(dept));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 2));
                        cell.setCellValue(PstEmpAward.getSection(sect));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 3));
                        cell.setCellValue(PstEmpAward.getAward(level));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 4));
                        cell.setCellValue(empAward.getAwardDescription());
                        cell.setCellStyle(style2);

                    }

                }
                b = b + tableSubHeader7.length;

            }
        } else {
            batas[6] = batas[5];
        }

        //sheetFamily
        //untuk personal data
        String[] tableSubHeaderFam = {
            "Payroll", "Full Name"
        };
        int[] batasFam;
        batasFam = new int[2];
        batasFam[0] = tableSubHeaderFam.length;
        row = sheetFamily.createRow((short) (2));
        for (int k = 0; k < tableSubHeaderFam.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableSubHeaderFam[k]);
            cell.setCellStyle(style3);
        }


        srcEmployee = (SrcEmployee) session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
        listEmployee = sessEmployee.searchEmployee(srcEmployee, 0, 5000);

        //String d = "";
        for (int i = 0; i < listEmployee.size(); i++) {
            Vector temp = (Vector) listEmployee.get(i);
            Employee employee = (Employee) temp.get(0);

            row = sheetFamily.createRow((short) (i + 3));

            cell = row.createCell((short) 0);
            cell.setCellValue(""+employee.getEmployeeNum());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 1);
            cell.setCellValue(""+employee.getFullName());
            cell.setCellStyle(style2);

        }
        
        
        
        //untuk family member
        int loopFam = PstFamilyMember.getCount2(srcEmployee); //kalo count ganjil(ex 5) header2 terakhir gak muncul (religion)?
        if (loopFam > 0) {

            int b = batasFam[0];
            for (int a = 0; a < loopFam; a++) {

                String[] tableSubHeaderFam2 = {
                    "Full Name", "Sex", "Relationship", "Guaranted",
                    "Date Of Birth", "Job", "Address", "Education", "Religion"
                };
                awal = tableSubHeaderFam.length + (tableSubHeaderFam2.length * a);
                akhir = tableSubHeaderFam2.length + tableSubHeaderFam.length + (tableSubHeaderFam2.length * a);
                batasFam[1] = akhir;
                row = sheetFamily.createRow((short) (2));


                for (int k = awal; k < akhir; k++) {

                    cell = row.createCell((short) k);
                    cell.setCellValue(tableSubHeaderFam2[k - (tableSubHeaderFam.length + (tableSubHeaderFam2.length * a))]);
                    cell.setCellStyle(style3);

                }

                for (int i = 0; i < listEmployee.size(); i++) {
                    Vector temp = (Vector) listEmployee.get(i);
                    Employee employee = (Employee) temp.get(0);


                    row = sheetFamily.createRow((short) (i + 3));
                    Vector vctMember = new Vector(1, 1);
                    long oidEmployee = employee.getOID();
                    String whereClause = PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID] + " = " + oidEmployee;
                    String orderClause = "";
                    orderClause = PstFamilyMember.fieldNames[PstFamilyMember.FLD_RELATIONSHIP] + "," + PstFamilyMember.fieldNames[PstFamilyMember.FLD_BIRTH_DATE] + " ASC";
                    vctMember = PstFamilyMember.list(0, 0, whereClause, orderClause);
                    FamilyMember famX = new FamilyMember();
                    Education education = new Education();
                    Religion religion = new Religion();

                    if (vctMember != null && vctMember.size() > 0 && vctMember.size() > a) {
                        famX = (FamilyMember) vctMember.get(a);
                        //education = (Education) vctMember.get(a);
                        //religion = (Religion) vctMember.get(a);
                 //*gak jadi dipake
                   //     String relationshipName = "";
                    //    relationshipName = PstFamilyMember.getRelation(famX.getRelationship());
                     //    *
                      //   *
                        String edu = String.valueOf(famX.getEducationId());
                        String educationName = "";
                        educationName = PstFamilyMember.getEducation(edu);
                        String religi = String.valueOf(famX.getReligionId());
                        String religionName = "";
                        religionName = PstFamilyMember.getReligion(religi);

                        cell = row.createCell((short) b);
                        cell.setCellValue(famX.getFullName());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 1));
                        cell.setCellValue(famX.getSex() == 0 ? "Male" : "Female");
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 2));
                        cell.setCellValue(famX.getRelationship());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 3));
                        cell.setCellValue(famX.getGuaranteed() == true ? "Guaranted" : "");
                        cell.setCellStyle(style2);//ignore blum dimasukan
                        cell = row.createCell((short) (b + 4));
                        cell.setCellValue(famX.getBirthDate() == null ? "" : String.valueOf(Formater.formatDate(famX.getBirthDate(), "dd MMM yyyy")));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 5));
                        cell.setCellValue(famX.getJob());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 6));
                        cell.setCellValue(famX.getAddress());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 7));
                        cell.setCellValue(PstFamilyMember.getEducation(edu));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 8));
                        cell.setCellValue(PstFamilyMember.getReligion(religi));
                        cell.setCellStyle(style2);

                    }

                }
                b = b + tableSubHeaderFam2.length;
            }
        } else {
            batasFam[1] = batasFam[0];
        }

        //sheetCarrier
        //untuk personal data
        String[] tableSubHeaderCarr = {
            "Payroll", "Full Name"
        };
        int[] batasCarr;
        batasCarr = new int[2];
        batasCarr[0] = tableSubHeaderCarr.length;
        row = sheetCarrier.createRow((short) (2));
        for (int k = 0; k < tableSubHeaderCarr.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableSubHeaderCarr[k]);
            cell.setCellStyle(style3);
        } // }


        srcEmployee = (SrcEmployee) session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
        listEmployee = sessEmployee.searchEmployee(srcEmployee, 0, 5000);

        for (int i = 0; i < listEmployee.size(); i++) {
            Vector temp = (Vector) listEmployee.get(i);
            Employee employee = (Employee) temp.get(0);

            row = sheetCarrier.createRow((short) (i + 3));

            cell = row.createCell((short) 0);
            cell.setCellValue(""+employee.getEmployeeNum());
            cell.setCellStyle(style2);
            cell = row.createCell((short) 1);
            cell.setCellValue(""+employee.getFullName());
            cell.setCellStyle(style2);

        }
        //untuk career path
        int loopCarr = PstCareerPath.getCount2(srcEmployee);
        if (loopCarr > 0) {
            int b = batasCarr[0];
            for (int a = 0; a < loopCarr; a++) {

                String[] tableSubHeaderCarr2 = {
                    "Company", "Division", "Department", "Section", "Position", "Level", "Emp Catagory", "Work From", "Work To", "Salary", "Description"
                };
                awal = tableSubHeaderCarr.length + (tableSubHeaderCarr2.length * a);
                akhir = tableSubHeaderCarr2.length + tableSubHeaderCarr.length + (tableSubHeaderCarr2.length * a);
                batasCarr[1] = akhir;
                row = sheetCarrier.createRow((short) (2));

                for (int k = awal; k < akhir; k++) {
                    cell = row.createCell((short) k);
                    cell.setCellValue(tableSubHeaderCarr2[k - (tableSubHeaderCarr.length + (tableSubHeaderCarr2.length * a))]);
                    cell.setCellStyle(style3);

                }
                for (int i = 0; i < listEmployee.size(); i++) {
                    Vector temp = (Vector) listEmployee.get(i);
                    Employee employee = (Employee) temp.get(0);

                    row = sheetCarrier.createRow((short) (i + 3));
                    Vector vctCareer = new Vector(1, 1);
                    long oidEmployee = employee.getOID();
                    String whereClause = PstCareerPath.fieldNames[PstCareerPath.FLD_EMPLOYEE_ID] + " = " + oidEmployee;
                    String orderClause = PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM];
                    vctCareer = PstCareerPath.list(0, 0, whereClause, orderClause);
                    CareerPath careerPath = new CareerPath();

                    if (vctCareer != null && vctCareer.size() > a) {
                        careerPath = (CareerPath) vctCareer.get(a);

                        String company = "";
                        String comp = String.valueOf(careerPath.getCompanyId());
                        PstCareerPath.getCompany(comp);
                        String division = "";
                        String div = String.valueOf(careerPath.getDivisionId());
                        PstCareerPath.getDivision(div);
                        String department = "";
                        String dept = String.valueOf(careerPath.getDepartmentId());
                        PstCareerPath.getDepartment(dept);
                        String section = "";
                        String sect = String.valueOf(careerPath.getSectionId());
                        PstCareerPath.getSection(sect);
                        String position = "";
                        String pos = String.valueOf(careerPath.getPositionId());
                        PstCareerPath.getPosition(pos);
                        String levelCareer = "";
                        String lev = String.valueOf(careerPath.getLevelId());
                        PstCareerPath.getLevel(lev);
                        String cat = String.valueOf(careerPath.getEmpCategoryId());
                        PstCareerPath.getCategory(cat);



                        cell = row.createCell((short) b);
                        cell.setCellValue(PstCareerPath.getCompany(comp));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 1));
                        cell.setCellValue(PstCareerPath.getDivision(div));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 2));
                        cell.setCellValue(PstCareerPath.getDepartment(dept));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 3));
                        cell.setCellValue(PstCareerPath.getSection(sect));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 4));
                        cell.setCellValue(PstCareerPath.getPosition(pos));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 5));
                        cell.setCellValue(PstCareerPath.getLevel(lev));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 6));
                        cell.setCellValue(PstCareerPath.getCategory(cat));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 7));
                        cell.setCellValue(careerPath.getWorkFrom() == null ? "" : String.valueOf(Formater.formatDate(careerPath.getWorkFrom(), "dd MMM yyyy")));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 8));
                        cell.setCellValue(careerPath.getWorkTo() == null ? "" : String.valueOf(Formater.formatDate(careerPath.getWorkTo(), "dd MMM yyyy")));
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 9));
                        cell.setCellValue(careerPath.getSalary());
                        cell.setCellStyle(style2);
                        cell = row.createCell((short) (b + 10));
                        cell.setCellValue(careerPath.getDescription());
                        cell.setCellStyle(style2);

                    }

                }
                b = b + tableSubHeaderCarr2.length;

            }
        } else {
            batasCarr[1] = batasCarr[0];
        }

        //untuk header
        String[] tableHeader = {
            "Personal Data", "Language", "Education", "Experience", "Warning",
            "Reprimand", "Award"
        };

        int k = 0;
        int i = 0;
        row = sheet.createRow((short) (1));
        while (k < batas[6]) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeader[i]);
            cell.setCellStyle(style);
            k = batas[i];
            i += 1;
        }

        //untuk headerFam
        String[] tableHeaderFam = {
            "Personal Data", "Family Member"
        };

        int l = 0;
        int j = 0;
        row = sheetFamily.createRow((short) (1));
        while (l < batasFam[1]) {
            cell = row.createCell((short) l);
            cell.setCellValue(tableHeaderFam[j]);
            cell.setCellStyle(style);
            l = batasFam[j];
            j += 1;
        }

        //untuk headerCarr
        String[] tableHeaderCarr = {
            "Personal Data", "Career Path"
        };
        int d = 0;
        int e = 0;
        row = sheetCarrier.createRow((short) (1));
        while (d < batasCarr[1]) {
            cell = row.createCell((short) d);
            cell.setCellValue(tableHeaderCarr[e]);
            cell.setCellStyle(style);
            d = batasCarr[e];
            e += 1;
        }


// }
*/
        // Write the output to a file
        //FileOutputStream fileOut = new FileOutputStream("workbook.xls");
        //wb.write(fileOut);
        //fileOut.close();        
        ServletOutputStream sos = response.getOutputStream();        
        wb.write(sos);
        sos.flush();
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
