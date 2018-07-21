/*
 * File Pdf untuk print List Employee Resignation
 * Created By Yunny
 */


package com.dimata.harisma.report;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import com.dimata.util.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.locker.*;
import com.dimata.harisma.session.employee.*;

public class EmployeeListResignationPdf extends HttpServlet {
    // public static final membervariables
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        System.out.println("===| EmployeeList |===");
        SessEmployee sessEmployee = new SessEmployee();
        SrcEmployee srcEmployee = new SrcEmployee();
        HttpSession session = request.getSession();
        srcEmployee = (SrcEmployee) session.getValue(SessEmployee.SESS_SRC_EMPLOYEE_RESIGNATION);
        Vector listEmployee = new Vector(1,1);
        listEmployee = sessEmployee.searchEmployeeResignation(srcEmployee, 0, 5000);
   
        // setting the color values
        Color border = new Color(0x00, 0x00, 0x00);
        Color bgTable = new Color(0x40, 0x00, 0xFF);
        Color bgCells = new Color(0xFF, 0xFF, 0x00);
        // setting some fonts in the color chosen by the user
        Font font1 = new Font(Font.HELVETICA, 12, Font.NORMAL, border);
        Font font2 = new Font(Font.HELVETICA, 8, Font.NORMAL, border);
        Font font2a = new Font(Font.HELVETICA, 8, Font.BOLD, border);
        // creating some content that will be used frequently
        Paragraph newLine = new Paragraph("\n", font1);
        Anchor anchor = new Anchor("visit http://www.lowagie.com/iText/", font1);
        anchor.setReference("http://www.lowagie.com/iText/");
        Paragraph link = new Paragraph(anchor);
        link.setAlignment(Element.ALIGN_CENTER);
        
        // step1: creating the document object
        Document document = new Document(PageSize.A4.rotate(), 30, 30, 30, 30);
        
        // step2.1: creating an OutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {
            // step2.2: creating an instance of the writer
            PdfWriter.getInstance(document, baos);
            
            // step 3.1: adding some metadata to the document
            document.addSubject("This is a report.");
            
            // step 3.2: adding a Header
            HeaderFooter header = new HeaderFooter(new Phrase("List Off Employee Resignation", font1), false);
            header.setAlignment(Element.ALIGN_CENTER);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(border);
            document.setHeader(header);
            
            // step 3.3: adding a Footer
            HeaderFooter footer = new HeaderFooter(new Phrase("", font1), false);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setBorder(Rectangle.TOP);
            footer.setBorderColor(border);
            document.setFooter(footer);
            
            // step 3.4: opening the document
            document.open();
            
            Table datatable = new Table(7);
            
            datatable.setPadding(1);
            datatable.setSpacing(1);
            //datatable.setBorder(Rectangle.NO_BORDER);
            int headerwidths[] = {
                3, 5, 15, 6, 7, 5, 
                6
            };
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            
            // the first cell spans 10 columns
            //Cell cell = new Cell(new Phrase("Administration -System Users Report", FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD)));
            //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setLeading(30);
            //cell.setColspan(10);
            //cell.setBorder(Rectangle.NO_BORDER);
            //cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
            //datatable.addCell(cell);
            
            // These cells span 2 rows
            //datatable.setDefaultCellBorderWidth(2);
            //datatable.setDefaultHorizontalAlignment(1);
            //datatable.setDefaultRowspan(2);
            //datatable.addCell("User Id");
            //datatable.addCell(new Phrase("Name", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
            //datatable.addCell("Company");
            //datatable.addCell("Department");
            
            // This cell spans the remaining 6 columns in 1 row
            //datatable.setDefaultRowspan(1);
            //datatable.setDefaultColspan(6);
            //datatable.addCell("Permissions");
            
            // These cells span 1 row and 1 column
            //.setBackgroundColor(new Color(0xD0, 0xD0, 0xD0));
            
            datatable.setDefaultVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.setDefaultColspan(1);
            //Cell cell = new Cell(new Phrase("Period", font2a));
            //cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
            //datatable.addCell(cell);
            
            Employee empStatus = new Employee();
            for (int j = 0; j < listEmployee.size(); j++) {
                Vector temp = (Vector) listEmployee.get(j);
                empStatus = (Employee)temp.get(0);
            }
            datatable.addCell(new Phrase("No.", font2a));
            datatable.addCell(new Phrase("NIK", font2a));
            datatable.addCell(new Phrase("Full Name", font2a));
            if(empStatus.getResigned()==0){
                datatable.addCell(new Phrase("Commencing Date", font2a));
            }
            else{
                datatable.addCell(new Phrase("Resigned Date", font2a));
            }
            datatable.addCell(new Phrase("Department", font2a));
            datatable.addCell(new Phrase("Section", font2a));
            datatable.addCell(new Phrase("Description", font2a));
            // this is the end of the table header
            datatable.endHeaders();
            //datatable.setBackgroundColor(new Color(0xFF, 0xFF, 0xFF));
            datatable.setDefaultCellBorderWidth(1);
            datatable.setDefaultRowspan(1);

            for (int i = 0; i < listEmployee.size(); i++) {
                Vector temp = (Vector) listEmployee.get(i);
                Employee employee = (Employee)temp.get(0);
                Department department = (Department)temp.get(1);
                Position position = (Position)temp.get(2);
                com.dimata.harisma.entity.masterdata.Section section = (com.dimata.harisma.entity.masterdata.Section)temp.get(3);
                EmpCategory empCategory = (EmpCategory)temp.get(4);
                Level level = (Level)temp.get(5);
                Religion religion = (Religion)temp.get(6);
                Marital marital = (Marital)temp.get(7);
                Locker locker = (Locker)temp.get(8);
                String whereCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+" = "+employee.getOID();
                String orderCl = PstEmpEducation.fieldNames[PstEmpEducation.FLD_END_DATE]+" DESC ";
                Vector vedu = PstEmpEducation.list(0,0,whereCl,orderCl);
                
                Education education = new Education();
                EmpEducation empEducation = new EmpEducation();
                if(vedu!=null && vedu.size()>0)
                    {
                        empEducation = (EmpEducation)vedu.get(0);
                       
                        try{
                            education = PstEducation.fetchExc(+empEducation.getEducationId());
                          
                        }catch(Exception e){
                            System.out.println("Error"+e.toString());}
                    }
               
                datatable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
                datatable.addCell(new Phrase(String.valueOf(i+1), font2));
                datatable.addCell(new Phrase(employee.getEmployeeNum(), font2));
                datatable.addCell(new Phrase(employee.getFullName(), font2));
                if(employee.getResigned()==0){
                    if(employee.getCommencingDate()!=null){
                        datatable.addCell(new Phrase(String.valueOf(Formater.formatDate(employee.getCommencingDate(), "dd MMM yyyy")), font2));
                    }
                    else{
                        datatable.addCell(new Phrase("", font2));
                    }
                }
                else{
                    if(employee.getResignedDate()!=null){
                        datatable.addCell(new Phrase(String.valueOf(Formater.formatDate(employee.getResignedDate(), "dd MMM yyyy")), font2));
                    }
                    else{
                         datatable.addCell(new Phrase("", font2));

                    }
                }
                datatable.addCell(new Phrase(department.getDepartment(), font2));
                datatable.addCell(new Phrase(section.getSection(), font2));
                datatable.addCell(new Phrase(employee.getResignedDesc(), font2));
                //datatable.addCell(new Phrase(String.valueOf(employee.getAstekDate()), font2));
               /* datatable.addCell(new Phrase(marital.getMaritalStatus(), font2));
               
                datatable.addCell(new Phrase(department.getDepartment(), font2));
                
                datatable.addCell(new Phrase(position.getPosition(), font2));
                datatable.addCell(new Phrase(level.getLevel(), font2));
                //datatable.addCell(new Phrase(empCategory.getEmpCategory(), font2));
                datatable.addCell(new Phrase(String.valueOf(Formater.formatDate(employee.getCommencingDate(), "dd MMM yyyy")), font2));
                datatable.addCell(new Phrase(employee.getAddress(), font2));
                if(employee.getHandphone()==null){
                    datatable.addCell(new Phrase(employee.getHandphone(), font2));
                }
                else{
                    datatable.addCell(new Phrase(employee.getPhone(), font2));
                }
              datatable.addCell(new Phrase(employee.getCurier(), font2));*/
                    
                
                //datatable.addCell(new Phrase(employee.getBarcodeNumber(), font2));
            }
            
            document.add(datatable);
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
            de.printStackTrace();
        }
        
        // step 5: closing the document
        document.close();
        
        // we have written the pdfstream to a ByteArrayOutputStream,
        // now we are going to write this outputStream to the ServletOutputStream
        // after we have set the contentlength (see http://www.lowagie.com/iText/faq.html#msie)
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }
}