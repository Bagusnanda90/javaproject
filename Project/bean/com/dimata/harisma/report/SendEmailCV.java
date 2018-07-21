/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report;

import com.dimata.harisma.entity.employee.CareerPath;
import com.dimata.harisma.entity.employee.EmpAward;
import com.dimata.harisma.entity.employee.EmpEducation;
import com.dimata.harisma.entity.employee.EmpLanguage;
import com.dimata.harisma.entity.employee.EmpReprimand;
import com.dimata.harisma.entity.employee.EmpWarning;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.Experience;
import com.dimata.harisma.entity.employee.FamilyMember;
import com.dimata.harisma.entity.employee.PstCareerPath;
import com.dimata.harisma.entity.employee.PstEmpAward;
import com.dimata.harisma.entity.employee.PstEmpEducation;
import com.dimata.harisma.entity.employee.PstEmpLanguage;
import com.dimata.harisma.entity.employee.PstEmpReprimand;
import com.dimata.harisma.entity.employee.PstEmpWarning;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.PstExperience;
import com.dimata.harisma.entity.employee.PstFamilyMember;
import com.dimata.harisma.entity.employee.PstTrainingHistory;
import com.dimata.harisma.entity.employee.TrainingHistory;
import com.dimata.harisma.entity.masterdata.AwardType;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.Education;
import com.dimata.harisma.entity.masterdata.EmpCategory;
import com.dimata.harisma.entity.masterdata.FamRelation;
import com.dimata.harisma.entity.masterdata.Language;
import com.dimata.harisma.entity.masterdata.Level;
import com.dimata.harisma.entity.masterdata.LockerLocation;
import com.dimata.harisma.entity.masterdata.Marital;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstAwardType;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstEducation;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstFamRelation;
import com.dimata.harisma.entity.masterdata.PstLanguage;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.PstLockerLocation;
import com.dimata.harisma.entity.masterdata.PstMarital;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstReligion;
import com.dimata.harisma.entity.masterdata.PstResignedReason;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.PstTraining;
import com.dimata.harisma.entity.masterdata.Religion;
import com.dimata.harisma.entity.masterdata.ResignedReason;
import com.dimata.harisma.entity.masterdata.Training;
import com.dimata.harisma.session.employee.SessEmployeePicture;
import com.dimata.harisma.session.employee.SessTraining;
import com.dimata.harisma.util.email;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Formater;
import com.dimata.util.NumberSpeller;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Vector;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dimata 007
 */
public class SendEmailCV extends HttpServlet {

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
        System.out.println("===| EmployeeDetailPdf |===");
        String[] oidEmployees = FRMQueryString.requestStringValues(request, "emp_id");
        String[] toEmails = FRMQueryString.requestStringValues(request, "to_email");
        String cc = FRMQueryString.requestString(request, "cc");
        String subject = FRMQueryString.requestString(request, "subject");
        String description = FRMQueryString.requestString(request, "description");
        
        if (oidEmployees != null && oidEmployees.length > 0){
            for(int index=0; index<oidEmployees.length; index++){
                long employeeId = Long.valueOf(oidEmployees[index]);
                String toEmail = toEmails[index];
                //createCV(employeeId, toEmail, cc, subject, description, response);
                ////////////////////////////////////////////////////////////////
                
                
                Employee employee = new Employee();
                Religion religion = new Religion();
                Marital marital = new Marital();
                Department department = new Department();
                com.dimata.harisma.entity.masterdata.Section section = new com.dimata.harisma.entity.masterdata.Section();
                Position position = new Position();
                EmpCategory empCategory = new EmpCategory();
                Level level = new Level();

                CareerPath career = new CareerPath();
                FamilyMember family = new FamilyMember();
                EmpLanguage empL = new EmpLanguage();
                EmpEducation empE = new EmpEducation();
                Experience exp = new Experience();
                TrainingHistory tr = new TrainingHistory();

                String empEmail = "";

                try {
                    employee = PstEmployee.fetchExc(employeeId);
                    empEmail = employee.getEmailAddress();
                    if (employee.getReligionId() != 0){
                        religion = PstReligion.fetchExc(employee.getReligionId());
                    }
                    if (employee.getMaritalId() != 0){
                        marital = PstMarital.fetchExc(employee.getMaritalId());
                    }
                    if (employee.getDepartmentId() != 0){
                        department = PstDepartment.fetchExc(employee.getDepartmentId());
                    }
                    if (employee.getSectionId() != 0){
                        section = PstSection.fetchExc(employee.getSectionId());
                    }
                    if (employee.getPositionId() != 0){
                        position = PstPosition.fetchExc(employee.getPositionId());
                    }
                    if (employee.getEmpCategoryId() != 0){
                        empCategory = PstEmpCategory.fetchExc(employee.getEmpCategoryId());
                    }
                    if (employee.getLevelId() != 0){
                        level = PstLevel.fetchExc(employee.getLevelId());
                    }

                } catch (Exception e) {
                }


                //load career
                Vector vCareer = PstCareerPath.list(0, 0, "employee_id=" + employee.getOID() + "", "WORK_TO desc");
                if (vCareer != null && vCareer.size() > 0) {
                    career = (CareerPath) vCareer.get(0);
                }

                //load family member
                Vector vFamily = PstFamilyMember.list(0, 0, "employee_id=" + employee.getOID() + "", "");
                if (vFamily != null && vFamily.size() > 0) {
                    family = (FamilyMember) vFamily.get(0);
                }

                //load EmpLanguage
                Vector vLan = PstEmpLanguage.list(0, 0, "employee_id=" + employee.getOID() + "", "");
                if (vLan != null && vLan.size() > 0) {
                    empL = (EmpLanguage) vLan.get(0);
                }

                //load Education
                Vector vEdu = PstEmpEducation.list(0, 0, "employee_id=" + employee.getOID() + "", "END_DATE desc");
                if (vEdu != null && vEdu.size() > 0) {
                    empE = (EmpEducation) vEdu.get(0);
                }

                //load Experience
                Vector vExp = PstExperience.list(0, 0, "employee_id=" + employee.getOID() + "", "END_DATE desc");
                if (vExp != null && vExp.size() > 0) {
                    exp = (Experience) vExp.get(0);
                }

                Vector vTraning = PstTrainingHistory.list(0, 0, "employee_id=" + employee.getOID() + "", "END_DATE desc");
                if (vTraning != null && vTraning.size() > 0) {
                    tr = (TrainingHistory) vTraning.get(0);
                }

                // load warning 
                Vector vWarning = PstEmpWarning.list(0, 0, "employee_id=" + employee.getOID() + "", "WARN_DATE");

                // load reprimand
                Vector vReprimand = PstEmpReprimand.list(0, 0, "employee_id=" + employee.getOID() + "", "REPRIMAND_DATE");

                // load reward
                Vector vAward = PstEmpAward.list(0, 0, "emp_id=" + employee.getOID() + "", "'DATE'");

                //end load - file    

                //request gambar
                String pictPath = "";
                SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
                try {
                    pictPath = sessEmployeePicture.fetchImagePeserta(employeeId);
                } catch (Exception e) {
                    System.out.println("err." + e.toString());
                }

                NumberSpeller numSpeller = new NumberSpeller();

                Color border = new Color(0x00, 0x00, 0x00);

                // setting some fonts in the color chosen by the user
                Font fontHeaderBig = new Font(Font.HELVETICA, 10, Font.BOLD, border);
                Font fontHeaderSmall = new Font(Font.HELVETICA, 6, Font.NORMAL, border);
                Font fontHeader = new Font(Font.HELVETICA, 8, Font.BOLD, border);
                Font fontContent = new Font(Font.HELVETICA, 8, Font.BOLD, border);
                Font tableContent = new Font(Font.HELVETICA, 8, Font.NORMAL, border);
                Font fontSpellCharge = new Font(Font.HELVETICA, 8, Font.BOLDITALIC, border);
                Font fontItalic = new Font(Font.HELVETICA, 8, Font.BOLDITALIC, border);
                Font fontItalicBottom = new Font(Font.HELVETICA, 8, Font.ITALIC, border);
                Font fontUnderline = new Font(Font.HELVETICA, 8, Font.UNDERLINE, border);

                Color bgColor = new Color(240, 240, 240);

                Color blackColor = new Color(0, 0, 0);

                Color putih = new Color(250, 250, 250);

                Document document = new Document(PageSize.A4, 30, 30, 50, 50);
                //Document document = new Document(PageSize.A4.rotate(), 10, 10, 30, 30);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Vector<DataSource> attachment = new Vector(); 
                Vector<String> attachmentName = new Vector(); 
                Vector<String> emailLogs = new Vector();
                emailLogs.add("List send via email by Dimata Hairisma System on "+ Formater.formatDate(new java.util.Date(),"yyyy-MM-dd hh:mm:ss"));


                try {
                    // step2.2: creating an instance of the writer
                    PdfWriter.getInstance(document, baos);

                    // step 3.1: adding some metadata to the document
                    document.addSubject("This is a subject.");
                    document.addSubject("This is a subject two.");

                    String str = PstSystemProperty.getValueByName("PRINT_HEADER");

                    //Header 
                    HeaderFooter header = new HeaderFooter(new Phrase(str, fontHeaderSmall), false);
                    header.setAlignment(Element.ALIGN_LEFT);
                    header.setBorder(Rectangle.BOTTOM);
                    header.setBorderColor(blackColor);
                    document.setHeader(header);

                    HeaderFooter footer = new HeaderFooter(new Phrase("printed : " + Formater.formatDate(new Date(), "dd/MM/yyyy"), fontHeaderSmall), false);
                    footer.setAlignment(Element.ALIGN_RIGHT);
                    footer.setBorder(Rectangle.TOP);
                    footer.setBorderColor(blackColor);
                    document.setFooter(footer);

                    //images
                     /* image logo */
                    Image logo = null;

                    String strPath = PstSystemProperty.getValueByName("IMGCACHE");

                    System.out.println("root dan gambar =" + strPath + "" + employee.getEmployeeNum() + ".jpg");
                    System.out.println("pictPath =" + pictPath);

                    try {
                        if (true) {
                            //1.menentukan path gambar dan gambarnya
                            logo = Image.getInstance(strPath + "" + employee.getEmployeeNum() + ".JPG");
                            logo.scalePercent(95);
                            //logo.setWidthPercentage(40);
                            //posisi atau letak gambar yang diinginkan 
                            logo.setAbsolutePosition(100, 100);
                        //  logo.setAlignment(Image.ALIGN_MIDDLE | Image.ALIGN_TOP);

                        }
                    } catch (Exception exc) {
                        System.out.println(" ERROR @ InvoicePdf - upload image : \n" + exc.toString());
                    }

                    // step 3.4: opening the document
                    document.open();

                    //Personal Data

                    int personalHeaderTop[] = {30, 2, 20, 5, 40, 5, 10, 5, 20};
                    Table personTable = new Table(9);
                    personTable.setWidth(100);
                    personTable.setWidths(personalHeaderTop);
                    personTable.setBorderColor(new Color(255, 255, 255));
                    personTable.setBorderWidth(1);
                    personTable.setAlignment(1);
                    personTable.setCellpadding(0);
                    personTable.setCellspacing(1);

                    //0
                    Cell titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("EMPLOYEE PERSONAL DATA ", fontHeaderBig));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setColspan(7);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk(" ", tableContent));
                    titleCellTop.setColspan(9);
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    //1Employee Number
                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setRowspan(7);
                    try {
                        titleCellTop.add(logo);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_TOP);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);


                    titleCellTop = new Cell(new Chunk("", tableContent));
                    //titleCellTop.add(logo);
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("Employee Number", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk(":", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk(employee.getEmployeeNum(), tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    //add space
                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);
                    //end space 

                    //2 Nama
                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("Name", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk(":", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk(employee.getFullName(), tableContent));
                    titleCellTop.setColspan(5);
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    //3 Address
                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("Address", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk(":", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("" + employee.getAddress(), tableContent));
                    titleCellTop.setColspan(5);
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    //4 Postal Code
                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("Postal Code", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk(":", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("" + employee.getPostalCode(), tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    //add space
                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);
                    //end space          

                    //5 Gender
                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("Gender", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk(":", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("" + PstEmployee.sexKey[employee.getSex()], tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    //add space
                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);
                    //end space          

                    //6 Place/DOB
                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("Place/DOB", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk(":", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("" + employee.getBirthPlace() + "/" + "" + Formater.formatDate(employee.getBirthDate(), "dd MMMM yyyy"), tableContent));
                    titleCellTop.setColspan(5);
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    //7 Phone
                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("Phone", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk(":", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("" + employee.getPhone(), tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    //add space
                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);

                    titleCellTop = new Cell(new Chunk("", tableContent));
                    titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop.setBorderColor(new Color(255, 255, 255));
                    personTable.addCell(titleCellTop);
                    //end space          

                    document.add(personTable);

                    //lanjutanya                         
                    int personalHeader[] = {30, 2, 20, 5, 40, 5, 10, 5, 20};
                    Table personTable2 = new Table(9);
                    personTable2.setWidth(100);
                    personTable2.setWidths(personalHeader);
                    personTable2.setBorderColor(new Color(255, 255, 255));
                    personTable2.setBorderWidth(0);
                    personTable2.setAlignment(1);
                    personTable2.setCellpadding(0);
                    personTable2.setCellspacing(1);

                    //8 Blood 
                    Cell titleCellTop2 = new Cell(new Chunk("Join Date :", tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);

                    titleCellTop2 = new Cell(new Chunk("", tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);

                    titleCellTop2 = new Cell(new Chunk("Mobile", tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);

                    titleCellTop2 = new Cell(new Chunk(":", tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);

                    titleCellTop2 = new Cell(new Chunk("" + employee.getHandphone(), tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);

                    //add space
                    titleCellTop2 = new Cell(new Chunk("", tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);

                    titleCellTop2 = new Cell(new Chunk("Religion", tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);

                    titleCellTop2 = new Cell(new Chunk(":", tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);

                    titleCellTop2 = new Cell(new Chunk("" + religion.getReligion(), tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);
                    //end space          

                    //9 Marital Status
                    titleCellTop2 = new Cell(new Chunk(Formater.formatDate(employee.getCommencingDate(), "yyyy-MM-dd"), tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);

                    titleCellTop2 = new Cell(new Chunk("", tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);

                    titleCellTop2 = new Cell(new Chunk("Marital Status", tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);

                    titleCellTop2 = new Cell(new Chunk(":", tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);

                    titleCellTop2 = new Cell(new Chunk("" + marital.getMaritalStatus() + " - " + marital.getNumOfChildren(), tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);

                    //add space
                    titleCellTop2 = new Cell(new Chunk("", tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);

                    titleCellTop2 = new Cell(new Chunk("Blood", tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);

                    titleCellTop2 = new Cell(new Chunk(":", tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);

                    titleCellTop2 = new Cell(new Chunk("" + employee.getBloodType(), tableContent));
                    titleCellTop2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellTop2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellTop2.setBorderColor(new Color(255, 255, 255));
                    personTable2.addCell(titleCellTop2);
                    //end space          

                    document.add(personTable2);

                    //LAST CARRIER
                    int lastCarrierHeaderTop[] = {24, 5, 45, 5, 25, 5, 40};
                    Table lastCarrierTable = new Table(7);
                    lastCarrierTable.setWidth(100);
                    lastCarrierTable.setWidths(lastCarrierHeaderTop);
                    lastCarrierTable.setBorderColor(blackColor);
                    lastCarrierTable.setBorderWidth(0);
                    lastCarrierTable.setAlignment(1);
                    lastCarrierTable.setCellpadding(0);
                    lastCarrierTable.setCellspacing(1);


                    Cell titleCellCarrierTop = new Cell(new Chunk(" ", tableContent));
                    titleCellCarrierTop.setColspan(7);
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    //titleCellCarrierTop.setBorder(Rectangle.BOTTOM);
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk("Last Carrier ", fontContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setColspan(7);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    //titleCellCarrierTop.setBorder(Rectangle.BOTTOM);
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk(" ", tableContent));
                    titleCellCarrierTop.setColspan(7);
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    //titleCellCarrierTop.setBorder(Rectangle.BOTTOM);
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    Division div = new Division();
                    try {
                        div = PstDivision.fetchExc(employee.getDivisionId());
                    } catch (Exception exx) {
                        System.out.println(exx.toString());
                    }

                    //1Division
                    titleCellCarrierTop = new Cell(new Chunk("Division", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    //titleCellCarrierTop.setBorder(Rectangle.BOTTOM);
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    //titleCellCarrierTop.setBorder(Rectangle.BOTTOM);
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk(div.getDivision(), tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    //titleCellCarrierTop.setBorder(Rectangle.BOTTOM);
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    // add space         
                    titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk("Employee Category", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk("" + empCategory.getEmpCategory(), tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    // end space

                    //2Department
                    titleCellCarrierTop = new Cell(new Chunk("Department", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk("" + department.getDepartment(), tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    // add space         
                    titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk("Level", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk("" + level.getLevel(), tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    // end space

                    //3 Section
                    titleCellCarrierTop = new Cell(new Chunk("Section", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk("" + section.getSection(), tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    // add space         
                    titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk("Position", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk("" + position.getPosition(), tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    // end space

                    //4 Locker
                    titleCellCarrierTop = new Cell(new Chunk("Locker", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    LockerLocation lock = new LockerLocation();
                    try {
                        lock = PstLockerLocation.fetchExc(employee.getLockerId());
                    } catch (Exception ev) {
                        System.out.println(ev.toString());
                    }

                    titleCellCarrierTop = new Cell(new Chunk("" + lock.getLocation(), tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    // add space         
                    titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk("Resigned", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    String resigned = "";
                    if (employee.getResigned() == 0) {
                        resigned = "No";
                    } else {
                        resigned = "Yes";
                    }

                    titleCellCarrierTop = new Cell(new Chunk(resigned, tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    // end space

                    //5 Jamsostek Number
                    titleCellCarrierTop = new Cell(new Chunk("Jamsostek Number", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk("" + employee.getAstekNum(), tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    // add space         
                    titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);



                    if (employee.getResigned() == 1) {

                        titleCellCarrierTop = new Cell(new Chunk("Resign Reason", tableContent));
                        titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                        lastCarrierTable.addCell(titleCellCarrierTop);

                        titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
                        titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                        titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                        lastCarrierTable.addCell(titleCellCarrierTop);

                        ResignedReason rrs = new ResignedReason();
                        try {
                            rrs = PstResignedReason.fetchExc(employee.getResignedReasonId());
                        } catch (Exception exc) {
                            System.out.println(exc.toString());
                        }

                        titleCellCarrierTop = new Cell(new Chunk(rrs.getResignedReason(), tableContent));
                        titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                        lastCarrierTable.addCell(titleCellCarrierTop);

                    } else {

                        titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                        titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                        lastCarrierTable.addCell(titleCellCarrierTop);

                        titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                        titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                        titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                        lastCarrierTable.addCell(titleCellCarrierTop);

                        titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                        titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                        lastCarrierTable.addCell(titleCellCarrierTop);
                    }

                    // end space

                    //6 Jamsostek Date
                    titleCellCarrierTop = new Cell(new Chunk("Jamsostek Date", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    if (employee.getAstekDate() != null) {

                        titleCellCarrierTop = new Cell(new Chunk(Formater.formatDate(employee.getAstekDate(), "dd MMMM yyyy"), tableContent));
                        titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                        lastCarrierTable.addCell(titleCellCarrierTop);

                    } else {
                        titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                        titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                        lastCarrierTable.addCell(titleCellCarrierTop);
                    }

                    // add space         
                    titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                    titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                    titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                    lastCarrierTable.addCell(titleCellCarrierTop);

                    if (employee.getResigned() == 1) {

                        titleCellCarrierTop = new Cell(new Chunk("Resign Description", tableContent));
                        titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                        lastCarrierTable.addCell(titleCellCarrierTop);

                        titleCellCarrierTop = new Cell(new Chunk(":", tableContent));
                        titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                        titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                        lastCarrierTable.addCell(titleCellCarrierTop);

                        titleCellCarrierTop = new Cell(new Chunk(employee.getResignedDesc(), tableContent));
                        titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                        lastCarrierTable.addCell(titleCellCarrierTop);

                    } else {

                        titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                        titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                        lastCarrierTable.addCell(titleCellCarrierTop);

                        titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                        titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_CENTER);
                        titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                        lastCarrierTable.addCell(titleCellCarrierTop);

                        titleCellCarrierTop = new Cell(new Chunk("", tableContent));
                        titleCellCarrierTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCellCarrierTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCellCarrierTop.setBorderColor(new Color(255, 255, 255));
                        lastCarrierTable.addCell(titleCellCarrierTop);
                    }

                    // end space

                    document.add(lastCarrierTable);

                    //CARRIER PATH

                    //Header Carrier Path
                    if (vCareer != null && vCareer.size() > 0) {

                        int careerHeader[] = {10, 10, 10, 10, 10, 10};
                        Table careerTableH = new Table(6);
                        careerTableH.setWidth(100);
                        careerTableH.setWidths(careerHeader);
                        careerTableH.setBorderColor(new Color(255, 255, 255));
                        careerTableH.setBorderWidth(1);
                        careerTableH.setAlignment(1);
                        careerTableH.setCellpadding(0);
                        careerTableH.setCellspacing(1);


                        //0(Experience Header)
                        Cell titleCareerCell = new Cell(new Chunk("", fontHeader));
                        titleCareerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCareerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCareerCell.setColspan(6);
                        titleCareerCell.setBorderColor(new Color(255, 255, 255));
                        careerTableH.addCell(titleCareerCell);

                        titleCareerCell = new Cell(new Chunk("Carrier Path : ", fontHeader));
                        titleCareerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCareerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCareerCell.setColspan(6);
                        titleCareerCell.setBorderColor(new Color(255, 255, 255));
                        careerTableH.addCell(titleCareerCell);

                        document.add(careerTableH);
                    }

                    if (vCareer != null && vCareer.size() > 0) {

                        //membuat table Career Profile
                        int careerHeaderTop[] = {10, 10, 10, 10, 10, 10};
                        Table careerTable = new Table(6);
                        careerTable.setWidth(100);
                        careerTable.setWidths(careerHeaderTop);
                        careerTable.setBorderColor(new Color(255, 255, 255));
                        careerTable.setBorderWidth(1);
                        careerTable.setAlignment(1);
                        careerTable.setCellpadding(0);
                        careerTable.setCellspacing(1);

                        //1
                        Cell titleCareerCellTop = new Cell(new Chunk("Department", fontHeader));
                        titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                        titleCareerCellTop.setBackgroundColor(bgColor);
                        careerTable.addCell(titleCareerCellTop);

                        titleCareerCellTop = new Cell(new Chunk("Section", fontHeader));
                        titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                        titleCareerCellTop.setBackgroundColor(bgColor);
                        careerTable.addCell(titleCareerCellTop);

                        titleCareerCellTop = new Cell(new Chunk("Position", fontHeader));
                        titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                        titleCareerCellTop.setBackgroundColor(bgColor);
                        careerTable.addCell(titleCareerCellTop);

                        titleCareerCellTop = new Cell(new Chunk("Work From", fontHeader));
                        titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                        titleCareerCellTop.setBackgroundColor(bgColor);
                        careerTable.addCell(titleCareerCellTop);

                        titleCareerCellTop = new Cell(new Chunk("Work To", fontHeader));
                        titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                        titleCareerCellTop.setBackgroundColor(bgColor);
                        careerTable.addCell(titleCareerCellTop);

                        titleCareerCellTop = new Cell(new Chunk("Description", fontHeader));
                        titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                        titleCareerCellTop.setBackgroundColor(bgColor);
                        careerTable.addCell(titleCareerCellTop);

                        //value-valuenya
                        for (int i = 0; i < vCareer.size(); i++) {
                            CareerPath cr = (CareerPath) vCareer.get(i);

                            //1
                            titleCareerCellTop = new Cell(new Chunk(cr.getDepartment(), tableContent));
                            titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                            careerTable.addCell(titleCareerCellTop);

                            titleCareerCellTop = new Cell(new Chunk(cr.getSection(), tableContent));
                            titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                            careerTable.addCell(titleCareerCellTop);

                            titleCareerCellTop = new Cell(new Chunk(cr.getPosition(), tableContent));
                            titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                            careerTable.addCell(titleCareerCellTop);

                            titleCareerCellTop = new Cell(new Chunk((cr.getWorkFrom() == null) ? "" : "" + Formater.formatDate(cr.getWorkFrom(), "dd MMMM yyyy"), tableContent));
                            titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                            careerTable.addCell(titleCareerCellTop);

                            titleCareerCellTop = new Cell(new Chunk((cr.getWorkTo() == null) ? "" : "" + Formater.formatDate(cr.getWorkTo(), "dd MMMM yyyy"), tableContent));
                            titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                            careerTable.addCell(titleCareerCellTop);

                            titleCareerCellTop = new Cell(new Chunk(cr.getDescription(), tableContent));
                            titleCareerCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCareerCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCareerCellTop.setBorderColor(new Color(255, 255, 255));
                            careerTable.addCell(titleCareerCellTop);

                        }

                        document.add(careerTable);
                    }

                    //Family Member

                    //Header Family Member
                    if (vFamily != null && vFamily.size() > 0) {

                        int familyHeader[] = {10, 10, 10, 10, 10, 10};
                        Table familyTableH = new Table(6);
                        familyTableH.setWidth(100);
                        familyTableH.setWidths(familyHeader);
                        familyTableH.setBorderColor(new Color(255, 255, 255));
                        familyTableH.setBorderWidth(1);
                        familyTableH.setAlignment(1);
                        familyTableH.setCellpadding(0);
                        familyTableH.setCellspacing(1);


                        //0(Family Header)
                        Cell titleFamilyCell = new Cell(new Chunk("", fontHeader));
                        titleFamilyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleFamilyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleFamilyCell.setColspan(6);
                        titleFamilyCell.setBorderColor(new Color(255, 255, 255));
                        familyTableH.addCell(titleFamilyCell);

                        titleFamilyCell = new Cell(new Chunk("Family Member : ", fontHeader));
                        titleFamilyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleFamilyCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleFamilyCell.setColspan(6);
                        titleFamilyCell.setBorderColor(new Color(255, 255, 255));
                        familyTableH.addCell(titleFamilyCell);

                        document.add(familyTableH);
                    }

                    if (vFamily != null && vFamily.size() > 0) {

                        //membuat table Family Member
                        int familyHeaderTop[] = {10, 10, 10, 10, 10, 10};
                        Table familyTable = new Table(6);
                        familyTable.setWidth(100);
                        familyTable.setWidths(familyHeaderTop);
                        familyTable.setBorderColor(new Color(255, 255, 255));
                        familyTable.setBorderWidth(1);
                        familyTable.setAlignment(1);
                        familyTable.setCellpadding(0);
                        familyTable.setCellspacing(1);

                        //1
                        Cell titleFamilyCellTop = new Cell(new Chunk("Nama", fontHeader));
                        titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                        titleFamilyCellTop.setBackgroundColor(bgColor);
                        familyTable.addCell(titleFamilyCellTop);

                        titleFamilyCellTop = new Cell(new Chunk("Relationship", fontHeader));
                        titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                        titleFamilyCellTop.setBackgroundColor(bgColor);
                        familyTable.addCell(titleFamilyCellTop);

                        titleFamilyCellTop = new Cell(new Chunk("Guaranteed", fontHeader));
                        titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                        titleFamilyCellTop.setBackgroundColor(bgColor);
                        familyTable.addCell(titleFamilyCellTop);

                        titleFamilyCellTop = new Cell(new Chunk("DOB", fontHeader));
                        titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                        titleFamilyCellTop.setBackgroundColor(bgColor);
                        familyTable.addCell(titleFamilyCellTop);

                        titleFamilyCellTop = new Cell(new Chunk("Job", fontHeader));
                        titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                        titleFamilyCellTop.setBackgroundColor(bgColor);
                        familyTable.addCell(titleFamilyCellTop);

                        titleFamilyCellTop = new Cell(new Chunk("Address", fontHeader));
                        titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                        titleFamilyCellTop.setBackgroundColor(bgColor);
                        familyTable.addCell(titleFamilyCellTop);

                        //value-valuenya
                        for (int i = 0; i < vFamily.size(); i++) {
                            FamilyMember faml = (FamilyMember) vFamily.get(i);

                            //1
                            titleFamilyCellTop = new Cell(new Chunk(faml.getFullName(), tableContent));
                            titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                            familyTable.addCell(titleFamilyCellTop);

                            titleFamilyCellTop = new Cell(new Chunk(getRelationName(faml.getRelationship()), tableContent));
                            titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                            familyTable.addCell(titleFamilyCellTop);

                            String grd = "";
                            if (faml.getGuaranteed() == true) {
                                grd = "Yes";
                            } else {
                                grd = "No";
                            }

                            titleFamilyCellTop = new Cell(new Chunk(grd, tableContent));
                            titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                            familyTable.addCell(titleFamilyCellTop);

                            titleFamilyCellTop = new Cell(new Chunk((faml.getBirthDate() == null) ? "" : "" + Formater.formatDate(faml.getBirthDate(), "MMM dd yyyy"), tableContent));
                            titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                            familyTable.addCell(titleFamilyCellTop);

                            titleFamilyCellTop = new Cell(new Chunk(faml.getJob(), tableContent));
                            titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                            familyTable.addCell(titleFamilyCellTop);

                            titleFamilyCellTop = new Cell(new Chunk(faml.getAddress(), tableContent));
                            titleFamilyCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleFamilyCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleFamilyCellTop.setBorderColor(new Color(255, 255, 255));
                            familyTable.addCell(titleFamilyCellTop);

                        }

                        document.add(familyTable);
                    }


                    //Language

                    //Header Language
                    if (vLan != null && vLan.size() > 0) {

                        int languageHeader[] = {10, 10, 10, 10, 10, 10};
                        Table languageTableH = new Table(6);
                        languageTableH.setWidth(100);
                        languageTableH.setWidths(languageHeader);
                        languageTableH.setBorderColor(new Color(255, 255, 255));
                        languageTableH.setBorderWidth(1);
                        languageTableH.setAlignment(1);
                        languageTableH.setCellpadding(0);
                        languageTableH.setCellspacing(1);


                        //0(Language)
                        Cell titleLanguageCell = new Cell(new Chunk("", fontHeader));
                        titleLanguageCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleLanguageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleLanguageCell.setColspan(6);
                        titleLanguageCell.setBorderColor(new Color(255, 255, 255));
                        languageTableH.addCell(titleLanguageCell);

                        titleLanguageCell = new Cell(new Chunk("Language : ", fontHeader));
                        titleLanguageCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleLanguageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleLanguageCell.setColspan(6);
                        titleLanguageCell.setBorderColor(new Color(255, 255, 255));
                        languageTableH.addCell(titleLanguageCell);

                        document.add(languageTableH);
                    }

                    if (vLan != null && vLan.size() > 0) {

                        //membuat table Language
                        int languageHeaderTop[] = {10, 10, 10, 10, 10, 10};
                        Table languageTable = new Table(6);
                        languageTable.setWidth(100);
                        languageTable.setWidths(languageHeaderTop);
                        languageTable.setBorderColor(new Color(255, 255, 255));
                        languageTable.setBorderWidth(1);
                        languageTable.setAlignment(1);
                        languageTable.setCellpadding(0);
                        languageTable.setCellspacing(1);

                        //1
                        Cell titleLanguageCellTop = new Cell(new Chunk("Language", fontHeader));
                        titleLanguageCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleLanguageCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleLanguageCellTop.setBorderColor(new Color(255, 255, 255));
                        titleLanguageCellTop.setBackgroundColor(bgColor);
                        languageTable.addCell(titleLanguageCellTop);

                        titleLanguageCellTop = new Cell(new Chunk("Oral", fontHeader));
                        titleLanguageCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleLanguageCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleLanguageCellTop.setBorderColor(new Color(255, 255, 255));
                        titleLanguageCellTop.setBackgroundColor(bgColor);
                        languageTable.addCell(titleLanguageCellTop);

                        titleLanguageCellTop = new Cell(new Chunk("Writen", fontHeader));
                        titleLanguageCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleLanguageCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleLanguageCellTop.setBorderColor(new Color(255, 255, 255));
                        titleLanguageCellTop.setBackgroundColor(bgColor);
                        languageTable.addCell(titleLanguageCellTop);

                        titleLanguageCellTop = new Cell(new Chunk("Description", fontHeader));
                        titleLanguageCellTop.setColspan(3);
                        titleLanguageCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleLanguageCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleLanguageCellTop.setBorderColor(new Color(255, 255, 255));
                        titleLanguageCellTop.setBackgroundColor(bgColor);
                        languageTable.addCell(titleLanguageCellTop);

                        //value-valuenya
                        for (int i = 0; i < vLan.size(); i++) {
                            EmpLanguage lan = (EmpLanguage) vLan.get(i);

                            //1
                            Language lang = new Language();
                            try {
                                lang = PstLanguage.fetchExc(lan.getLanguageId());
                            } catch (Exception ex) {
                                System.out.println(ex.toString());
                            }

                            titleLanguageCellTop = new Cell(new Chunk(lang.getLanguage(), tableContent));
                            titleLanguageCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleLanguageCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleLanguageCellTop.setBorderColor(new Color(255, 255, 255));
                            languageTable.addCell(titleLanguageCellTop);

                            String oral = "";
                            if (lan.getOral() == 0) {
                                oral = "Good";
                            } else if (lan.getOral() == 1) {
                                oral = "Fair";
                            } else if (lan.getOral() == 2) {
                                oral = "Poor";
                            }

                            titleLanguageCellTop = new Cell(new Chunk(oral, tableContent));
                            titleLanguageCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleLanguageCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleLanguageCellTop.setBorderColor(new Color(255, 255, 255));
                            languageTable.addCell(titleLanguageCellTop);

                            String writen = "";
                            if (lan.getWritten() == 0) {
                                writen = "Good";
                            } else if (lan.getWritten() == 1) {
                                writen = "Fair";
                            } else if (lan.getWritten() == 2) {
                                writen = "Poor";
                            }

                            titleLanguageCellTop = new Cell(new Chunk(writen, tableContent));
                            titleLanguageCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleLanguageCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleLanguageCellTop.setBorderColor(new Color(255, 255, 255));
                            languageTable.addCell(titleLanguageCellTop);

                            titleLanguageCellTop = new Cell(new Chunk(lan.getDescription(), tableContent));
                            titleLanguageCellTop.setColspan(3);
                            titleLanguageCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleLanguageCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleLanguageCellTop.setBorderColor(new Color(255, 255, 255));
                            languageTable.addCell(titleLanguageCellTop);

                        }

                        document.add(languageTable);
                    }

                    //EDUCATION 

                    //Header Education
                    if (vEdu != null && vEdu.size() > 0) {

                        int educationHeader[] = {10, 10, 10, 10, 10, 10};
                        Table educationTableH = new Table(6);
                        educationTableH.setWidth(100);
                        educationTableH.setWidths(educationHeader);
                        educationTableH.setBorderColor(new Color(255, 255, 255));
                        educationTableH.setBorderWidth(1);
                        educationTableH.setAlignment(1);
                        educationTableH.setCellpadding(0);
                        educationTableH.setCellspacing(1);


                        //0(Family Header)
                        Cell titleEducationCell = new Cell(new Chunk("", fontHeader));
                        titleEducationCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleEducationCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleEducationCell.setColspan(6);
                        titleEducationCell.setBorderColor(new Color(255, 255, 255));
                        educationTableH.addCell(titleEducationCell);

                        titleEducationCell = new Cell(new Chunk("Education : ", fontHeader));
                        titleEducationCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleEducationCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleEducationCell.setColspan(6);
                        titleEducationCell.setBorderColor(new Color(255, 255, 255));
                        educationTableH.addCell(titleEducationCell);

                        document.add(educationTableH);
                    }

                    if (vEdu != null && vEdu.size() > 0) {

                        //membuat table Education
                        int familyEducationTop[] = {10, 10, 10, 10, 10, 10};
                        Table educationTable = new Table(6);
                        educationTable.setWidth(100);
                        educationTable.setWidths(familyEducationTop);
                        educationTable.setBorderColor(new Color(255, 255, 255));
                        educationTable.setBorderWidth(1);
                        educationTable.setAlignment(1);
                        educationTable.setCellpadding(0);
                        educationTable.setCellspacing(1);

                        //1
                        Cell titleEducationCellTop = new Cell(new Chunk("Level", fontHeader));
                        titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                        titleEducationCellTop.setBackgroundColor(bgColor);
                        educationTable.addCell(titleEducationCellTop);

                        titleEducationCellTop = new Cell(new Chunk("Description", fontHeader));
                        titleEducationCellTop.setColspan(2);
                        titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                        titleEducationCellTop.setBackgroundColor(bgColor);
                        educationTable.addCell(titleEducationCellTop);

                        titleEducationCellTop = new Cell(new Chunk("Start Date", fontHeader));
                        titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                        titleEducationCellTop.setBackgroundColor(bgColor);
                        educationTable.addCell(titleEducationCellTop);

                        titleEducationCellTop = new Cell(new Chunk("End Date", fontHeader));
                        titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                        titleEducationCellTop.setBackgroundColor(bgColor);
                        educationTable.addCell(titleEducationCellTop);

                        titleEducationCellTop = new Cell(new Chunk("Graduation", fontHeader));
                        titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                        titleEducationCellTop.setBackgroundColor(bgColor);
                        educationTable.addCell(titleEducationCellTop);

                        //value-valuenya
                        for (int i = 0; i < vEdu.size(); i++) {
                            EmpEducation edu = (EmpEducation) vEdu.get(i);

                            Education education = new Education();
                            if (edu.getEducationId() != 0) {
                                try {
                                    education = PstEducation.fetchExc(edu.getEducationId());
                                } catch (Exception exc) {
                                    education = new Education();
                                }
                            }

                            //1
                            titleEducationCellTop = new Cell(new Chunk(String.valueOf(education.getEducation()), tableContent));
                            titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                            educationTable.addCell(titleEducationCellTop);

                            titleEducationCellTop = new Cell(new Chunk(edu.getEducationDesc(), tableContent));
                            titleEducationCellTop.setColspan(2);
                            titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                            educationTable.addCell(titleEducationCellTop);

                            titleEducationCellTop = new Cell(new Chunk(String.valueOf(edu.getStartDate()), tableContent));
                            titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                            educationTable.addCell(titleEducationCellTop);

                            titleEducationCellTop = new Cell(new Chunk(String.valueOf(edu.getEndDate()), tableContent));
                            titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                            educationTable.addCell(titleEducationCellTop);

                            titleEducationCellTop = new Cell(new Chunk(edu.getGraduation(), tableContent));
                            titleEducationCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleEducationCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleEducationCellTop.setBorderColor(new Color(255, 255, 255));
                            educationTable.addCell(titleEducationCellTop);

                        }

                        document.add(educationTable);
                    }

                    //EXPERIENCE 

                    //Header Experience
                    if (vExp != null && vExp.size() > 0) {

                        int experienceHeader[] = {10, 10, 10, 10, 10, 10, 10};
                        Table experienceTableH = new Table(7);
                        experienceTableH.setWidth(100);
                        experienceTableH.setWidths(experienceHeader);
                        experienceTableH.setBorderColor(new Color(255, 255, 255));
                        experienceTableH.setBorderWidth(1);
                        experienceTableH.setAlignment(1);
                        experienceTableH.setCellpadding(0);
                        experienceTableH.setCellspacing(1);


                        //0(Experience Header)
                        Cell titleExperienceCell = new Cell(new Chunk("", fontHeader));
                        titleExperienceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleExperienceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleExperienceCell.setColspan(7);
                        titleExperienceCell.setBorderColor(new Color(255, 255, 255));
                        experienceTableH.addCell(titleExperienceCell);

                        titleExperienceCell = new Cell(new Chunk("Experience : ", fontHeader));
                        titleExperienceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleExperienceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleExperienceCell.setColspan(7);
                        titleExperienceCell.setBorderColor(new Color(255, 255, 255));
                        experienceTableH.addCell(titleExperienceCell);

                        document.add(experienceTableH);
                    }

                    if (vExp != null && vExp.size() > 0) {

                        //membuat table Experience
                        int familyExperienceTop[] = {10, 10, 10, 10, 10, 10, 10};
                        Table exprienceTable = new Table(7);
                        exprienceTable.setWidth(100);
                        exprienceTable.setWidths(familyExperienceTop);
                        exprienceTable.setBorderColor(new Color(255, 255, 255));
                        exprienceTable.setBorderWidth(1);
                        exprienceTable.setAlignment(1);
                        exprienceTable.setCellpadding(0);
                        exprienceTable.setCellspacing(1);

                        //1
                        Cell titleExperieceCellTop = new Cell(new Chunk("Company Name", fontHeader));
                        titleExperieceCellTop.setColspan(2);
                        titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                        titleExperieceCellTop.setBackgroundColor(bgColor);
                        exprienceTable.addCell(titleExperieceCellTop);

                        titleExperieceCellTop = new Cell(new Chunk("Start Date", fontHeader));
                        titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                        titleExperieceCellTop.setBackgroundColor(bgColor);
                        exprienceTable.addCell(titleExperieceCellTop);

                        titleExperieceCellTop = new Cell(new Chunk("End Date", fontHeader));
                        titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                        titleExperieceCellTop.setBackgroundColor(bgColor);
                        exprienceTable.addCell(titleExperieceCellTop);

                        titleExperieceCellTop = new Cell(new Chunk("Position", fontHeader));
                        titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                        titleExperieceCellTop.setBackgroundColor(bgColor);
                        exprienceTable.addCell(titleExperieceCellTop);

                        titleExperieceCellTop = new Cell(new Chunk("Move Reason", fontHeader));
                        titleExperieceCellTop.setColspan(2);
                        titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                        titleExperieceCellTop.setBackgroundColor(bgColor);
                        exprienceTable.addCell(titleExperieceCellTop);

                        //value-valuenya
                        for (int i = 0; i < vExp.size(); i++) {
                            Experience ex = (Experience) vExp.get(i);

                            //1
                            titleExperieceCellTop = new Cell(new Chunk(ex.getCompanyName(), tableContent));
                            titleExperieceCellTop.setColspan(2);
                            titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                            exprienceTable.addCell(titleExperieceCellTop);

                            titleExperieceCellTop = new Cell(new Chunk(String.valueOf(ex.getStartDate()), tableContent));
                            titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                            exprienceTable.addCell(titleExperieceCellTop);

                            titleExperieceCellTop = new Cell(new Chunk(String.valueOf(ex.getEndDate()), tableContent));
                            titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                            exprienceTable.addCell(titleExperieceCellTop);

                            titleExperieceCellTop = new Cell(new Chunk(ex.getPosition(), tableContent));
                            titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                            exprienceTable.addCell(titleExperieceCellTop);

                            titleExperieceCellTop = new Cell(new Chunk(ex.getMoveReason(), tableContent));
                            titleExperieceCellTop.setColspan(2);
                            titleExperieceCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleExperieceCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleExperieceCellTop.setBorderColor(new Color(255, 255, 255));
                            exprienceTable.addCell(titleExperieceCellTop);

                        }

                        document.add(exprienceTable);
                    }


                    //TRANING HISTORY 

                    //Header Traning
                    if (vTraning != null && vTraning.size() > 0) {

                        int traningHeader[] = {10, 10, 10, 10, 10, 10};
                        Table traningTableH = new Table(6);
                        traningTableH.setWidth(100);
                        traningTableH.setWidths(traningHeader);
                        traningTableH.setBorderColor(new Color(255, 255, 255));
                        traningTableH.setBorderWidth(1);
                        traningTableH.setAlignment(1);
                        traningTableH.setCellpadding(0);
                        traningTableH.setCellspacing(1);


                        //0(Traning Header)
                        Cell titleTraningCell = new Cell(new Chunk("", fontHeader));
                        titleTraningCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleTraningCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleTraningCell.setColspan(6);
                        titleTraningCell.setBorderColor(new Color(255, 255, 255));
                        traningTableH.addCell(titleTraningCell);

                        titleTraningCell = new Cell(new Chunk("Traning History : ", fontHeader));
                        titleTraningCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleTraningCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleTraningCell.setColspan(6);
                        titleTraningCell.setBorderColor(new Color(255, 255, 255));
                        traningTableH.addCell(titleTraningCell);

                        document.add(traningTableH);
                    }

                    if (vTraning != null && vTraning.size() > 0) {

                        //membuat table Traning
                        int familyTraningTop[] = {10, 10, 10, 10, 10, 10};
                        Table traningTable = new Table(6);
                        traningTable.setWidth(100);
                        traningTable.setWidths(familyTraningTop);
                        traningTable.setBorderColor(new Color(255, 255, 255));
                        traningTable.setBorderWidth(1);
                        traningTable.setAlignment(1);
                        traningTable.setCellpadding(0);
                        traningTable.setCellspacing(1);

                        //1
                        Cell titleTraningCellTop = new Cell(new Chunk("Program", fontHeader));
                        titleTraningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleTraningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleTraningCellTop.setBorderColor(new Color(255, 255, 255));
                        titleTraningCellTop.setBackgroundColor(bgColor);
                        traningTable.addCell(titleTraningCellTop);

                        titleTraningCellTop = new Cell(new Chunk("Period", fontHeader));
                        titleTraningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleTraningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleTraningCellTop.setBorderColor(new Color(255, 255, 255));
                        titleTraningCellTop.setBackgroundColor(bgColor);
                        traningTable.addCell(titleTraningCellTop);

                        titleTraningCellTop = new Cell(new Chunk("Duration", fontHeader));
                        titleTraningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleTraningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleTraningCellTop.setBorderColor(new Color(255, 255, 255));
                        titleTraningCellTop.setBackgroundColor(bgColor);
                        traningTable.addCell(titleTraningCellTop);

                        titleTraningCellTop = new Cell(new Chunk("Trainer", fontHeader));
                        titleTraningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleTraningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleTraningCellTop.setBorderColor(new Color(255, 255, 255));
                        titleTraningCellTop.setBackgroundColor(bgColor);
                        traningTable.addCell(titleTraningCellTop);

                        titleTraningCellTop = new Cell(new Chunk("Remark", fontHeader));
                        titleTraningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleTraningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleTraningCellTop.setBorderColor(new Color(255, 255, 255));
                        titleTraningCellTop.setBackgroundColor(bgColor);
                        traningTable.addCell(titleTraningCellTop);

                        titleTraningCellTop = new Cell(new Chunk("Description", fontHeader));
                        titleTraningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleTraningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleTraningCellTop.setBorderColor(new Color(255, 255, 255));
                        titleTraningCellTop.setBackgroundColor(bgColor);
                        traningTable.addCell(titleTraningCellTop);

                        //value-valuenya
                        for (int i = 0; i < vTraning.size(); i++) {
                            TrainingHistory training = (TrainingHistory) vTraning.get(i);

                            Training trn1 = new Training();
                            try {
                                trn1 = PstTraining.fetchExc(training.getTrainingId());
                            } catch (Exception e) {
                                trn1 = new Training();
                            }


                            titleTraningCellTop = new Cell(new Chunk(trn1.getName(), tableContent));
                            titleTraningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleTraningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleTraningCellTop.setBorderColor(new Color(255, 255, 255));
                            traningTable.addCell(titleTraningCellTop);

                            titleTraningCellTop = new Cell(new Chunk(Formater.formatDate(training.getStartDate(), "dd-MM-yy") + " // " + Formater.formatDate(training.getEndDate(), "dd-MM-yy"), tableContent));
                            titleTraningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleTraningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleTraningCellTop.setBorderColor(new Color(255, 255, 255));
                            traningTable.addCell(titleTraningCellTop);

                            titleTraningCellTop = new Cell(new Chunk(SessTraining.getDurationString(training.getDuration()), tableContent));
                            titleTraningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleTraningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleTraningCellTop.setBorderColor(new Color(255, 255, 255));
                            traningTable.addCell(titleTraningCellTop);

                            titleTraningCellTop = new Cell(new Chunk(training.getTrainer(), tableContent));
                            titleTraningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleTraningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleTraningCellTop.setBorderColor(new Color(255, 255, 255));
                            traningTable.addCell(titleTraningCellTop);

                            titleTraningCellTop = new Cell(new Chunk(training.getRemark(), tableContent));
                            titleTraningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleTraningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleTraningCellTop.setBorderColor(new Color(255, 255, 255));
                            traningTable.addCell(titleTraningCellTop);

                            titleTraningCellTop = new Cell(new Chunk(trn1.getDescription(), tableContent));
                            titleTraningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleTraningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleTraningCellTop.setBorderColor(new Color(255, 255, 255));
                            traningTable.addCell(titleTraningCellTop);

                        }

                        document.add(traningTable);
                    }


                    if (vWarning != null && vWarning.size() > 0) {
                        //membuat table title Warning
                        int warningTop[] = {1};
                        Table dataTable = new Table(1);
                        dataTable.setWidth(100);
                        dataTable.setWidths(warningTop);
                        dataTable.setBorderColor(new Color(255, 255, 255));
                        dataTable.setBorderWidth(1);
                        dataTable.setAlignment(1);
                        dataTable.setCellpadding(0);
                        dataTable.setCellspacing(1);

                        /*
                        Cell titleTableTop = new Cell(new Chunk("", fontHeader));
                        titleTableTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleTableTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleTableTop.setBorderColor(new Color(255, 255, 255));
                        titleTableTop.setBackgroundColor(bgColor);
                        dataTable.addCell(titleTableTop);
                         * */

                        /* Cell titleTableTop = new Cell(new Chunk("Warning History", fontHeader)); // diganti menjadi Pembinaan */ 
                        Cell titleTableTop = new Cell(new Chunk("Pembinaan", fontHeader));
                        titleTableTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleTableTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleTableTop.setBorderColor(new Color(255, 255, 255));
                        titleTableTop.setBackgroundColor(bgColor);
                        dataTable.addCell(titleTableTop);

                        document.add(dataTable);                
                      }

                    if (vWarning != null && vWarning.size() > 0) {
                        //membuat table Traning
                        int warningTop[] = {10, 10, 10, 10, 10, 10};
                        Table dataTable = new Table(6);
                        dataTable.setWidth(100);
                        dataTable.setWidths(warningTop);
                        dataTable.setBorderColor(new Color(255, 255, 255));
                        dataTable.setBorderWidth(1);
                        dataTable.setAlignment(1);
                        dataTable.setCellpadding(0);
                        dataTable.setCellspacing(1);

                        //1
                        Cell titleWarningCellTop = new Cell(new Chunk("Break Date", fontHeader));
                        titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                        titleWarningCellTop.setBackgroundColor(bgColor);
                        dataTable.addCell(titleWarningCellTop);
                        /* Update by Hendra Putu | 2015-11-04 | utk sementara tidak ditampilkan
                        titleWarningCellTop = new Cell(new Chunk("Break Fact", fontHeader));
                        titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                        titleWarningCellTop.setBackgroundColor(bgColor);
                        dataTable.addCell(titleWarningCellTop);
                        */
                        titleWarningCellTop = new Cell(new Chunk("Warning Date", fontHeader));
                        titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                        titleWarningCellTop.setBackgroundColor(bgColor);
                        dataTable.addCell(titleWarningCellTop);

                        titleWarningCellTop = new Cell(new Chunk("Warning By", fontHeader));
                        titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                        titleWarningCellTop.setBackgroundColor(bgColor);
                        dataTable.addCell(titleWarningCellTop);

                        titleWarningCellTop = new Cell(new Chunk("Valid Until", fontHeader));
                        titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                        titleWarningCellTop.setBackgroundColor(bgColor);
                        dataTable.addCell(titleWarningCellTop);
                        /* Update by Hendra Putu | 2015-11-04 | utk sementara tidak ditampilkan
                        titleWarningCellTop = new Cell(new Chunk("Warning Level", fontHeader));
                        titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                        titleWarningCellTop.setBackgroundColor(bgColor);
                        dataTable.addCell(titleWarningCellTop);
                        */
                        //value-valuenya
                        for (int i = 0; i < vWarning.size(); i++) {
                            EmpWarning wrn = (EmpWarning) vWarning.get(i);

                            titleWarningCellTop = new Cell(new Chunk(Formater.formatDate(wrn.getBreakDate(), "MMM d, yyyy"), tableContent));
                            titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                            dataTable.addCell(titleWarningCellTop);
                            /* Update by Hendra Putu | 2015-11-04 | utk sementara tidak ditampilkan
                            titleWarningCellTop = new Cell(new Chunk("" + wrn.getBreakFact(), tableContent));
                            titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                            dataTable.addCell(titleWarningCellTop);
                            */
                            titleWarningCellTop = new Cell(new Chunk(Formater.formatDate(wrn.getWarningDate(), "MMM d, yyyy"), tableContent));
                            titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                            dataTable.addCell(titleWarningCellTop);

                            titleWarningCellTop = new Cell(new Chunk(wrn.getWarningBy(), tableContent));
                            titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                            dataTable.addCell(titleWarningCellTop);

                            titleWarningCellTop = new Cell(new Chunk(Formater.formatDate(wrn.getValidityDate(), "MMM d, yyyy"), tableContent));
                            titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                            dataTable.addCell(titleWarningCellTop);

                            //titleWarningCellTop = new Cell(new Chunk(PstEmpWarning.levelNames[wrn.getWarnLevel()], tableContent));
                            //titleWarningCellTop = new Cell(new Chunk(PstEmpWarning.levelNames[wrn.getWarnLevelId()], tableContent));
                            /* Update by Hendra Putu | 2015-11-04 | utk sementara tidak ditampilkan
                            titleWarningCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleWarningCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleWarningCellTop.setBorderColor(new Color(255, 255, 255));
                            dataTable.addCell(titleWarningCellTop);*/

                        }


                        document.add(dataTable);
                    }

                        // Print Reprimand
                    if (vReprimand != null && vReprimand.size() > 0) {
                        //membuat table title Warning
                        int warningTop[] = {10};
                        Table dataTable = new Table(1);
                        dataTable.setWidth(100);
                        dataTable.setWidths(warningTop);
                        dataTable.setBorderColor(new Color(255, 255, 255));
                        dataTable.setBorderWidth(1);
                        dataTable.setAlignment(1);
                        dataTable.setCellpadding(0);
                        dataTable.setCellspacing(1);

                        //1
                        /*
                        Cell titleTableTop = new Cell(new Chunk("", fontHeader));
                        titleTableTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleTableTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleTableTop.setBorderColor(new Color(255, 255, 255));
                        titleTableTop.setBackgroundColor(bgColor);
                        dataTable.addCell(titleTableTop);
                         * */
                        /* Update by Hendra Putu | Reprimand History diganti menjadi Peringatan
                        Cell titleTableTop = new Cell(new Chunk("Reprimand History", fontHeader));
                        */
                        Cell titleTableTop = new Cell(new Chunk("Peringatan", fontHeader));
                        titleTableTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleTableTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleTableTop.setBorderColor(new Color(255, 255, 255));
                        titleTableTop.setBackgroundColor(bgColor);
                        dataTable.addCell(titleTableTop);

                        document.add(dataTable);                
                      }


                        if (vReprimand != null && vReprimand.size() > 0) {
                            //membuat table 
                            int dataTop[] = {10, 10, 10, 10, 10, 10, 10};
                            Table dataTable = new Table(7);
                            dataTable.setWidth(100);
                            dataTable.setWidths(dataTop);
                            dataTable.setBorderColor(new Color(255, 255, 255));
                            dataTable.setBorderWidth(1);
                            dataTable.setAlignment(1);
                            dataTable.setCellpadding(0);
                            dataTable.setCellspacing(1);

                            //1
                            titleCellTop = new Cell(new Chunk("Date", fontHeader));
                            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCellTop.setBorderColor(new Color(255, 255, 255));
                            titleCellTop.setBackgroundColor(bgColor);
                            dataTable.addCell(titleCellTop);

                            titleCellTop = new Cell(new Chunk("Chapter", fontHeader));
                            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCellTop.setBorderColor(new Color(255, 255, 255));
                            titleCellTop.setBackgroundColor(bgColor);
                            dataTable.addCell(titleCellTop);

                            titleCellTop = new Cell(new Chunk("Article", fontHeader));
                            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCellTop.setBorderColor(new Color(255, 255, 255));
                            titleCellTop.setBackgroundColor(bgColor);
                            dataTable.addCell(titleCellTop);

                            titleCellTop = new Cell(new Chunk("Page", fontHeader));
                            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCellTop.setBorderColor(new Color(255, 255, 255));
                            titleCellTop.setBackgroundColor(bgColor);
                            dataTable.addCell(titleCellTop);
                            /* Update by Hendra Putu | 2015-11-04 | Description is not show
                            titleCellTop = new Cell(new Chunk("Description", fontHeader));
                            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCellTop.setBorderColor(new Color(255, 255, 255));
                            titleCellTop.setBackgroundColor(bgColor);
                            dataTable.addCell(titleCellTop);
                            */
                            titleCellTop = new Cell(new Chunk("Valid Until", fontHeader));
                            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCellTop.setBorderColor(new Color(255, 255, 255));
                            titleCellTop.setBackgroundColor(bgColor);
                            dataTable.addCell(titleCellTop);

                            titleCellTop = new Cell(new Chunk("Level", fontHeader));
                            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCellTop.setBorderColor(new Color(255, 255, 255));
                            titleCellTop.setBackgroundColor(bgColor);
                            dataTable.addCell(titleCellTop);

                            //value-valuenya
                            for (int i = 0; i < vReprimand.size(); i++) {
                                EmpReprimand rpm = (EmpReprimand) vReprimand.get(i);

                                titleCellTop = new Cell(new Chunk(Formater.formatDate(rpm.getReprimandDate(), "d-MMM-yyyy"), tableContent));
                                titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                                titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                titleCellTop.setBorderColor(new Color(255, 255, 255));
                                dataTable.addCell(titleCellTop);

                                titleCellTop = new Cell(new Chunk("" + rpm.getChapter(), tableContent));
                                titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                                titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                titleCellTop.setBorderColor(new Color(255, 255, 255));
                                dataTable.addCell(titleCellTop);

                                titleCellTop = new Cell(new Chunk("" + rpm.getArticle(), tableContent));
                                titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                                titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                titleCellTop.setBorderColor(new Color(255, 255, 255));
                                dataTable.addCell(titleCellTop);

                                titleCellTop = new Cell(new Chunk("" + rpm.getPage(), tableContent));
                                titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                                titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                titleCellTop.setBorderColor(new Color(255, 255, 255));
                                dataTable.addCell(titleCellTop);
                                /* Update by Hendra Putu | 2015-11-04 | Description is not show
                                titleCellTop = new Cell(new Chunk((rpm.getDescription().length() > 100) ? rpm.getDescription().substring(0, 100) + " ..." : rpm.getDescription(), tableContent));
                                titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                                titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                titleCellTop.setBorderColor(new Color(255, 255, 255));
                                dataTable.addCell(titleCellTop);*/

                                titleCellTop = new Cell(new Chunk("" + Formater.formatDate(rpm.getValidityDate(), "d-MMM-yyyy"), tableContent));
                                titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                                titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                titleCellTop.setBorderColor(new Color(255, 255, 255));
                                dataTable.addCell(titleCellTop);

                                //titleCellTop = new Cell(new Chunk("" + PstEmpReprimand.levelNames[rpm.getReprimandLevel()], tableContent));
                                titleCellTop = new Cell(new Chunk("" + rpm.getReprimandLevelId(), tableContent));
                                titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                                titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                titleCellTop.setBorderColor(new Color(255, 255, 255));
                                dataTable.addCell(titleCellTop);
                            }
                            document.add(dataTable);
                        }

                        // Print Award
                    if (vAward != null && vAward.size() > 0) {
                        //membuat table title Award
                        int warningTop[] = {10};
                        Table dataTable = new Table(1);
                        dataTable.setWidth(100);
                        dataTable.setWidths(warningTop);
                        dataTable.setBorderColor(new Color(255, 255, 255));
                        dataTable.setBorderWidth(1);
                        dataTable.setAlignment(1);
                        dataTable.setCellpadding(0);
                        dataTable.setCellspacing(1);

                        /*
                        Cell titleTableTop = new Cell(new Chunk("", fontHeader));
                        titleTableTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleTableTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleTableTop.setBorderColor(new Color(255, 255, 255));
                        titleTableTop.setBackgroundColor(bgColor);
                        dataTable.addCell(titleTableTop);*/

                        Cell titleTableTop = new Cell(new Chunk("Award History", fontHeader));
                        titleTableTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                        titleTableTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        titleTableTop.setBorderColor(new Color(255, 255, 255));
                        titleTableTop.setBackgroundColor(bgColor);
                        dataTable.addCell(titleTableTop);

                        document.add(dataTable);                
                      }

                    if (vAward != null && vAward.size() > 0) {
                            //membuat table 
                            int dataTop[] = {10, 10, 10, 10, 10};
                            Table dataTable = new Table(5);
                            dataTable.setWidth(100);
                            dataTable.setWidths(dataTop);
                            dataTable.setBorderColor(new Color(255, 255, 255));
                            dataTable.setBorderWidth(1);
                            dataTable.setAlignment(1);
                            dataTable.setCellpadding(0);
                            dataTable.setCellspacing(1);

                            //1
                            titleCellTop = new Cell(new Chunk("Date", fontHeader));
                            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCellTop.setBorderColor(new Color(255, 255, 255));
                            titleCellTop.setBackgroundColor(bgColor);
                            dataTable.addCell(titleCellTop);

                            titleCellTop = new Cell(new Chunk("Department", fontHeader));
                            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCellTop.setBorderColor(new Color(255, 255, 255));
                            titleCellTop.setBackgroundColor(bgColor);
                            dataTable.addCell(titleCellTop);

                            titleCellTop = new Cell(new Chunk("Section", fontHeader));
                            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCellTop.setBorderColor(new Color(255, 255, 255));
                            titleCellTop.setBackgroundColor(bgColor);
                            dataTable.addCell(titleCellTop);

                            titleCellTop = new Cell(new Chunk("Award Type", fontHeader));
                            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCellTop.setBorderColor(new Color(255, 255, 255));
                            titleCellTop.setBackgroundColor(bgColor);
                            dataTable.addCell(titleCellTop);

                            titleCellTop = new Cell(new Chunk("Description", fontHeader));
                            titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                            titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            titleCellTop.setBorderColor(new Color(255, 255, 255));
                            titleCellTop.setBackgroundColor(bgColor);
                            dataTable.addCell(titleCellTop);

                            //value-valuenya
                            for (int i = 0; i < vAward.size(); i++) {
                                EmpAward awd = (EmpAward) vAward.get(i);
                                Department dept = new Department();
                                com.dimata.harisma.entity.masterdata.Section sect = new com.dimata.harisma.entity.masterdata.Section();
                                AwardType typ = new AwardType();

                                try {
                                    dept = PstDepartment.fetchExc(awd.getDepartmentId());
                                } catch (Exception e) {
                                    dept = new Department();
                                }

                                try {
                                    sect = PstSection.fetchExc(awd.getSectionId());
                                } catch (Exception e) {
                                    sect = new com.dimata.harisma.entity.masterdata.Section();
                                }

                                try {
                                    typ = PstAwardType.fetchExc(awd.getAwardType());
                                } catch (Exception e) {
                                    typ = new AwardType();
                                }

                                titleCellTop = new Cell(new Chunk(Formater.formatDate(awd.getAwardDate(), "d-MMM-yyyy"), tableContent));
                                titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                                titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                titleCellTop.setBorderColor(new Color(255, 255, 255));
                                dataTable.addCell(titleCellTop);

                                titleCellTop = new Cell(new Chunk("" + dept.getDepartment(), tableContent));
                                titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                                titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                titleCellTop.setBorderColor(new Color(255, 255, 255));
                                dataTable.addCell(titleCellTop);

                                titleCellTop = new Cell(new Chunk("" + sect.getSection(), tableContent));
                                titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                                titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                titleCellTop.setBorderColor(new Color(255, 255, 255));
                                dataTable.addCell(titleCellTop);

                                titleCellTop = new Cell(new Chunk("" + typ.getAwardType(), tableContent));
                                titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                                titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                titleCellTop.setBorderColor(new Color(255, 255, 255));
                                dataTable.addCell(titleCellTop);

                                titleCellTop = new Cell(new Chunk((awd.getAwardDescription().length() > 100) ? awd.getAwardDescription().substring(0, 100) + " ..." : awd.getAwardDescription(), tableContent));
                                titleCellTop.setHorizontalAlignment(Element.ALIGN_LEFT);
                                titleCellTop.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                titleCellTop.setBorderColor(new Color(255, 255, 255));
                                dataTable.addCell(titleCellTop);
                            }
                            document.add(dataTable);
                        }


                } catch (Exception e) {
                    System.out.println("PRINT EMPLOYEE DATA ==>" + e);
                }

                document.close();

                byte[] bytes = baos.toByteArray();
                DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
                attachment.add(dataSource); // add as attachement
                attachmentName.add("CV"+".pdf");
                Vector<String> listTo = new Vector();
                listTo.add(empEmail);
                listTo.add(toEmail);
                Vector<String> listCc = new Vector();
                listCc.add(cc);
                //listRec.add("" +emailAddress);
                email.sendEmail(listTo, listCc, null, subject, description, attachment, attachmentName);
                
                
                ////////////////////////////////////////////////////////////////
            }
        }
    }
    
    protected void createCV(long employeeId, String toEmail, String cc, String subject, String description, HttpServletResponse response) throws ServletException, java.io.IOException {
        

        
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
    
    /* Update by Hendra Putu | 2015-11-04 */
    public String getRelationName(String oids){
        String str = "-";
        long oid = Long.valueOf(oids);
        try {
            FamRelation famRelation = PstFamRelation.fetchExc(oid);
            str = famRelation.getFamRelation();
        } catch(Exception e){
            System.out.println(e.toString());
        }
        return str;
    }
}