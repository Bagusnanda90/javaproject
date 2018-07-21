
package com.dimata.harisma.report.employee;


import java.sql.*;
import java.awt.Color;
import java.io.ByteArrayOutputStream;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Font;
import com.dimata.util.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.qdep.form.*;

/**
 *
 * @author bayu
 */

public class TrainingSearchPdf extends HttpServlet {
    
    // setting the color values
    public static Color border = new Color(0x00, 0x00, 0x00);
    public static Color bgColor = new Color(240, 240, 240);
    
    // setting some fonts in the color chosen by the user
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 14, Font.BOLD, border);
    public static Font fontFooter = new Font(Font.TIMES_NEW_ROMAN, 10, Font.NORMAL, border);
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.ITALIC, border);
    public static Font fontListHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, border);
    public static Font fontLsContent = new Font(Font.TIMES_NEW_ROMAN, 10);

    /** Processes requests for both HTTP <code>GET</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

      
        Rectangle rectangle = new Rectangle(20, 20, 20, 20);
        rectangle.rotate();        
        Document document = new Document(PageSize.A4, 30, 30, 50, 50);

        String approot = FRMQueryString.requestString(request, "approot");      
        String pathImage = approot + "/images/traningheader.jpg";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            // creating an instance of the writer
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            // adding some metadata to the document
            document.addSubject("This is a subject.");
            document.addSubject("This is a subject two.");
            
            /*HeaderFooter footer = new HeaderFooter(new Phrase("Left"), false);
            footer.setAlignment(Element.ALIGN_LEFT); 
            document.setFooter(footer);*/
            
            HeaderFooter footer2 = new HeaderFooter(new Phrase("Page ", fontFooter), true);           
            footer2.setAlignment(Element.ALIGN_RIGHT); 
            document.setFooter(footer2); 

            document.open();

            // get data from session 
            Vector listTraining = new Vector();
            HttpSession sess = request.getSession(true);
            
            try {
                listTraining = (Vector) sess.getValue("SESS_PRINT_SPECIAL_TRAINING");
            } 
            catch (Exception e) {
                System.out.println("Exc : " + e.toString());
                listTraining = new Vector();
            }        

            if ((listTraining != null) && (listTraining.size() > 0)) {
                SrcTraining srcTraining = (SrcTraining) listTraining.get(0);
                Department department = (Department) listTraining.get(1);
                Training training = (Training) listTraining.get(2);
                Vector vct = (Vector) listTraining.get(3);

                try {
                    document.add(getHeader(department, training, srcTraining, pathImage));
                } 
                catch (Exception exc) {}

                document.add(getContent(vct, document, writer, srcTraining));

            }
        } 
        catch (Exception e) {
            System.out.println("Exception e : " + e.toString());
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
     
    private static Table getHeader(Department department, Training training, SrcTraining srcTraining, String pathImage) throws BadElementException, DocumentException {

        int ctnInt[] = {25, 2, 73};
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
            System.out.println("Training Special : get Gambar " + exc);
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
        
        hcell = new Cell(new Chunk("DEPARTMENT ", fontHeader));        
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);

        hcell = new Cell(new Chunk(" : ", fontHeader));
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);

        hcell = new Cell(new Chunk((department.getDepartment()).toUpperCase(), fontLsContent));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setBorder(Rectangle.BOTTOM);
        list.addCell(hcell);

        hcell = new Cell(new Chunk("TRAINING PROGRAM ", fontHeader));
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);

        hcell = new Cell(new Chunk(" : ", fontHeader));
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);

        hcell = new Cell(new Chunk((training.getName()).toUpperCase(), fontLsContent));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setBorder(Rectangle.BOTTOM);
        list.addCell(hcell);

        hcell = new Cell(new Chunk("LIST CATEGORY ", fontHeader));
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);

        hcell = new Cell(new Chunk(" : ", fontHeader));
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);

        String st = "";
        if (srcTraining.getTypeOfSearch() == 0) {
            st = "TRAINNED EMPLOYEES";
        } 
        else {
            st = "NOT YET TRAINNED EMPLOYEES";
        }

        hcell = new Cell(new Chunk(st, fontLsContent));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setBorder(Rectangle.BOTTOM);
        list.addCell(hcell);

        hcell = new Cell(new Chunk("", fontHeader));
        hcell.setBorderColor(new Color(255, 255, 255));
        hcell.setColspan(3);
        list.addCell(hcell);

        return list;   
    }

     /* 
     * this method makes detail header
     */
    
    private static Table getListHeader(SrcTraining srcTraining) throws BadElementException, DocumentException {
       
        int ctnInt[] = {4, 8, 30, 25, 25, 8};

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
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setBackgroundColor(bgColor);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);

        hcell = new Cell(new Chunk("Position", fontListHeader));
        hcell.setBackgroundColor(bgColor);
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);

        hcell = new Cell(new Chunk("Level", fontListHeader));
        hcell.setBackgroundColor(bgColor);
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        list.addCell(hcell);

        return list;      
    }

     /* 
     * this method fills detail 
     */
    
    private static Table getContent(Vector vct, Document document, PdfWriter writer, SrcTraining srcTraining) throws BadElementException, DocumentException {

        Table list = getListHeader(srcTraining);

        Cell hcell = null;

        if (vct != null && vct.size() > 0) {
            for (int i = 0; i < vct.size(); i++) {
                Vector temp = (Vector) vct.get(i);
                Employee employee = (Employee) temp.get(0);                    
                Position position = (Position) temp.get(1);
                Level level = (Level) temp.get(2);
                Department dept = (Department) temp.get(3);

                hcell = new Cell(new Chunk("" + (i + 1), fontLsContent));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                list.addCell(hcell);

                hcell = new Cell(new Chunk(employee.getEmployeeNum(), fontLsContent));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                list.addCell(hcell);

                hcell = new Cell(new Chunk(employee.getFullName(), fontLsContent));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                list.addCell(hcell);

                hcell = new Cell(new Chunk(dept.getDepartment(), fontLsContent));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                list.addCell(hcell);

                hcell = new Cell(new Chunk(position.getPosition(), fontLsContent));
                hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                list.addCell(hcell);

                hcell = new Cell(new Chunk(level.getLevel(), fontLsContent));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                list.addCell(hcell);

                if (!writer.fitsPage(list)) {
                    list.deleteLastRow();
                    i--;                   
                    document.add(list); 
                    document.newPage();
                    list = getListHeader(srcTraining);
                }
            }
        }

        return list;
    }
    
}
