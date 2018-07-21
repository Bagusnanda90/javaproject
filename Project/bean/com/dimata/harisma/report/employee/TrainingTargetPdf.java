
package com.dimata.harisma.report.employee;

import java.io.*;
import java.sql.*;
import java.awt.Color;
import java.io.ByteArrayOutputStream;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Font;
import com.dimata.util.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.session.employee.*;
import com.dimata.qdep.form.*;

/**
 *
 * @author guest
 */
public class TrainingTargetPdf extends HttpServlet {   
  
 
    // setting the color values
    public static Color border = new Color(0x00, 0x00, 0x00);
    public static Color bgColor = new Color(240, 240, 240);
    
    // setting some fonts in the color chosen by the user
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 14, Font.BOLDITALIC, border);
    public static Font fontSubTitle = new Font(Font.TIMES_NEW_ROMAN, 12, Font.BOLD, border);
    public static Font fontListHeader = new Font(Font.TIMES_NEW_ROMAN, 12, Font.BOLDITALIC, border);
    public static Font fontLsContent = new Font(Font.HELVETICA, 10);
    public static Font fontFooter = new Font(Font.TIMES_NEW_ROMAN, 10, Font.NORMAL, border);
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.ITALIC, border);

    /** Processes requests for both HTTP <code>GET</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

      
        Document document = new Document(PageSize.A4.rotate());
        document.setMargins(50, 50, 50, 50);

        String approot = FRMQueryString.requestString(request, "approot");      
        String pathImage = "";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            // creating an instance of the writer
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            
            HeaderFooter header = new HeaderFooter(new Phrase("Training Target Report ", fontTitle), false);           
            header.setAlignment(Element.ALIGN_LEFT); 
            document.setHeader(header); 
                                 
            HeaderFooter footer = new HeaderFooter(new Phrase("Page ", fontFooter), true);           
            footer.setAlignment(Element.ALIGN_RIGHT); 
            document.setFooter(footer); 

            document.open();

            // get data from session 
            Vector listTraining = new Vector();
            SrcTrainingTarget srcTraining = new SrcTrainingTarget();
            HttpSession sess = request.getSession(true);
            
            try {               
                listTraining = (Vector)sess.getValue("TRAINING_TARGET_REPORT");
                
            } 
            catch (Exception e) {
                System.out.println(e.toString());
                listTraining = new Vector();
            }        

            if ((listTraining != null) && (listTraining.size() > 0)) {                
                Vector vct = (Vector) listTraining.get(0);
                srcTraining = (SrcTrainingTarget)listTraining.get(1);
                String dept = "";
                
                try {
                    Department department = PstDepartment.fetchExc(srcTraining.getDepartmentId());
                    dept = department.getDepartment();
                }
                catch(Exception e) {
                    dept = " ALL DEPARTMENT ";
                }
                
                try {
                    document.add(getHeader(dept, srcTraining.getMonth(), srcTraining.getTypeOfSearch(), pathImage));
                } 
                catch (Exception exc) {}

                document.add(getContent(vct, document, writer));

            }
        } 
        catch (Exception e) {
            System.out.println(e.toString());
        }

        // closing the document
        document.close();

        // we have written the pdfstream to a ByteArrayOutputStream,
        // now we are going to write this outputStream to the ServletOutputStream       
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }

    /* 
     * this method makes table header 
     */
     
    private static Table getHeader(String deptName, Date date, int category, String pathImage) throws BadElementException, DocumentException {

        int ctnInt[] = {10, 2, 88};
        Table list = new Table(3);
        list.setBorderColor(new Color(255, 255, 255));
        list.setWidth(90);
        list.setWidths(ctnInt);
        list.setBorderWidth(0);
        list.setCellpadding(1);
        list.setCellspacing(0);

        Image gambar = null;
        try {
            gambar = Image.getInstance(pathImage);
            gambar.setAlignment(Image.MIDDLE);
            gambar.scalePercent(40);
        } 
        catch (Exception exc) {
            System.out.println("Training Target : " + exc);
        }

        Cell hcell = null;

        if (gambar != null) {

            hcell = new Cell(new Chunk("", fontTitle));
            hcell.setBorderColor(new Color(255, 255, 255));
            hcell.setNoWrap(true);
            hcell.setVerticalAlignment(0);
            hcell.setColspan(3);
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.add(gambar);
            list.addCell(hcell);
        }        
        
        hcell = new Cell(new Chunk("Department ", fontSubTitle));        
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);

        hcell = new Cell(new Chunk(" : ", fontSubTitle));
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);

        hcell = new Cell(new Chunk(deptName, fontSubTitle));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setBorder(Rectangle.BOTTOM);
        list.addCell(hcell);
        
        hcell = new Cell(new Chunk("Periode ", fontSubTitle));        
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);

        hcell = new Cell(new Chunk(" : ", fontSubTitle));
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);

        hcell = new Cell(new Chunk(Formater.formatDate(date, "MMMM yyyy"), fontSubTitle));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setBorder(Rectangle.BOTTOM);
        list.addCell(hcell);
        
        hcell = new Cell(new Chunk("Category ", fontSubTitle));        
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);

        hcell = new Cell(new Chunk(" : ", fontSubTitle));
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);

        hcell = new Cell(new Chunk(category == 0 ? "Achieving Target" : "Not Achieving Target", fontSubTitle));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setBorder(Rectangle.BOTTOM);
        list.addCell(hcell);
        
        hcell = new Cell(new Chunk(" ", fontSubTitle));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        hcell.setColspan(3);
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setBorder(Rectangle.BOTTOM);
        list.addCell(hcell);
        
        return list;   
    }

     /* 
     * this method makes detail header
     */
    
    private static Table getListHeader() throws BadElementException, DocumentException {
       
        int ctnInt[] = {5, 10, 25, 25, 25, 10};

        Table list = new Table(6);
        list.setBorderColor(new Color(255, 255, 255));
        list.setWidth(100);
        list.setWidths(ctnInt);
        list.setBorderWidth(0);
        list.setPadding(1);
        list.setSpacing(0);
        
        Cell hcell = new Cell(new Chunk("No", fontListHeader));       
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setBackgroundColor(bgColor);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);

        hcell = new Cell(new Chunk("Payroll", fontListHeader));       
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setBackgroundColor(bgColor);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);
       
        hcell = new Cell(new Chunk("Name", fontListHeader));        
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setBackgroundColor(bgColor);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);
        
        hcell = new Cell(new Chunk("Department", fontListHeader));
        hcell.setBackgroundColor(bgColor);
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);
       
        hcell = new Cell(new Chunk("Position", fontListHeader));
        hcell.setBackgroundColor(bgColor);
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);
        
        hcell = new Cell(new Chunk("Duration\n(in hours)", fontListHeader));
        hcell.setBackgroundColor(bgColor);
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);
        
        return list;      
    }

     /* 
     * this method fills detail 
     */
    
    private static Table getContent(Vector vct, Document document, PdfWriter writer) throws BadElementException, DocumentException {

        Table list = getListHeader();

        Cell hcell = null;

        if (vct != null && vct.size() > 0) {
          
            for (int i = 0; i < vct.size(); i++) {
                Vector temp = (Vector) vct.get(i);
                
                Employee employee = (Employee)temp.get(0);	
                Position position = (Position)temp.get(1);               
                Department dept = (Department)temp.get(2);
                int duration = 0;
                
                try {
                    duration = Integer.parseInt((String)temp.get(3));
                }
                catch(Exception e) {}
                
                hcell = new Cell(new Chunk(" " + (i+1), fontLsContent));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                list.addCell(hcell);
                
                hcell = new Cell(new Chunk(" " + employee.getEmployeeNum(), fontLsContent));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                list.addCell(hcell);

                hcell = new Cell(new Chunk(" " + employee.getFullName(), fontLsContent));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                list.addCell(hcell);
                
                hcell = new Cell(new Chunk(" " + dept.getDepartment(), fontLsContent));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                list.addCell(hcell);
                
                hcell = new Cell(new Chunk(" " + position.getPosition(), fontLsContent));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                list.addCell(hcell);
                                               
                hcell = new Cell(new Chunk(SessTraining.getDurationString(duration) + " ", fontLsContent));
                hcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                list.addCell(hcell);
                
                if (!writer.fitsPage(list)) {
                    list.deleteLastRow();
                    i--;                   
                    document.add(list);
                    document.newPage();
                    list = getListHeader();
                }
            }
        }

        return list;
    }
    
}
