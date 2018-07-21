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
import com.dimata.harisma.entity.locker.*;
import com.dimata.harisma.session.locker.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;

public class LockerListPdf extends HttpServlet {
    // public static final membervariables
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        System.out.println("\t---===| LockerList |===---");
        SessLocker sessLocker = new SessLocker();
        SrcLocker srcLocker = new SrcLocker();
        HttpSession session = request.getSession();
        srcLocker = (SrcLocker)session.getValue(SessLocker.SESS_SRC_LOCKER);
        Vector listLocker = new Vector(1,1);
        listLocker = sessLocker.searchLocker(srcLocker, 0, 5000);

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
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        
        // step2.1: creating an OutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {
            // step2.2: creating an instance of the writer
            PdfWriter.getInstance(document, baos);
            
            // step 3.1: adding some metadata to the document
            document.addSubject("This is a report.");
            
            // step 3.2: adding a Header
            HeaderFooter header = new HeaderFooter(new Phrase("Locker List", font1), false);
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
                4, 16, 20, 10, 10, 10, 30
            };
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            
            datatable.setDefaultVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.setDefaultColspan(1);
            //Cell cell = new Cell(new Phrase("Period", font2a));
            //cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
            //datatable.addCell(cell);
            
            datatable.addCell(new Phrase("No", font2a));
            datatable.addCell(new Phrase("Locker Number", font2a));
            datatable.addCell(new Phrase("Location", font2a));
            datatable.addCell(new Phrase("Key Number", font2a));
            datatable.addCell(new Phrase("Spare Key", font2a));
            datatable.addCell(new Phrase("Condition", font2a));
            datatable.addCell(new Phrase("Employee", font2a));
            
            // this is the end of the table header
            datatable.endHeaders();
            
            //datatable.setBackgroundColor(new Color(0xFF, 0xFF, 0xFF));
            datatable.setDefaultCellBorderWidth(1);
            datatable.setDefaultRowspan(1);

            for (int i = 0; i < listLocker.size(); i++) {
                Vector temp = (Vector) listLocker.get(i);
                Locker locker = (Locker) temp.get(0);
                LockerLocation lockerlocation = (LockerLocation) temp.get(1);
                LockerCondition lockercondition = (LockerCondition) temp.get(2);
                
                datatable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
                datatable.addCell(new Phrase("" + (i+1), font2));
                datatable.addCell(new Phrase(locker.getLockerNumber(), font2));
                datatable.addCell(new Phrase(lockerlocation.getLocation(), font2));                
                datatable.addCell(new Phrase(locker.getKeyNumber(), font2));
                datatable.addCell(new Phrase((locker.getSpareKey()==null) ? "" : locker.getSpareKey(), font2));
                datatable.addCell(new Phrase(lockercondition.getCondition(), font2));
                           
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
                
                datatable.addCell(new Phrase(empData, font2));
                
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