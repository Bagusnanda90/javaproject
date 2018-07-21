package com.dimata.harisma.report.employee;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.Date;
import java.util.*;

import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.entity.search.SrcEndTraining;
import com.dimata.util.Formater;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;  


public class EmployeeWarningPdf extends HttpServlet {
   
    static Color border = new Color(0x00, 0x00, 0x00);
    static Color bgTable = new Color(200, 200, 200);
    static Color bgCells = new Color(255, 255, 255);    
    
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);
    public static Color titleColor = new Color(200,200,200);
    public static Color summaryColor = new Color(240,240,240);
       
    public static Font fontHeader1 = new Font(Font.HELVETICA, 14, Font.BOLD + Font.UNDERLINE, blackColor);
    public static Font fontHeader2 = new Font(Font.HELVETICA, 11, Font.BOLD, blackColor);
    public static Font fontTitleNormal = new Font(Font.HELVETICA, 9, Font.NORMAL, blackColor);
    public static Font fontTitleUnderlined = new Font(Font.HELVETICA, 9, Font.UNDERLINE, blackColor); 
    public static Font fontTitleItalic = new Font(Font.HELVETICA, 9, Font.ITALIC, blackColor);
    public static Font fontTextItem = new Font(Font.HELVETICA, 9, Font.NORMAL, blackColor);
    
    public static String imgRoot = "http://localhost:8080/HarismaVerX.1.0/images/logo.jpg";

    private static void constructHeader(Document document) {
         try {            
            // create header
            Table table = new Table(1);
            int hdrwidths[] = {100};
            table.setWidths(hdrwidths);
            table.setWidth(100);
            table.setPadding(0);
            table.setSpacing(0);
            table.setDefaultCellBorder(Cell.NO_BORDER);
            table.setBorder(Table.NO_BORDER);
            
            try {
                Image image = Image.getInstance(imgRoot);
                
                image.setAlignment(Image.MIDDLE);   

                Cell hcell = new Cell(new Chunk("", fontHeader2));
                hcell.setRowspan(2);
                hcell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                hcell.setVerticalAlignment(Cell.ALIGN_TOP);
                hcell.setBorderColor(new Color(255,255,255));
                hcell.setBackgroundColor(new Color(255,255,255));
                hcell.add(image);
                table.addCell(hcell);
            } 
            catch (Exception e) {}
            
            table.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            table.addCell(new Phrase("LETTER OF WARNING", fontHeader1));
            table.addCell(new Phrase("SURAT TEGURAN", fontHeader2)); 
            addSpace(table, 1);
            addSpace(table, 1);
            
            document.add(table);
         }
         catch(Exception e){
            System.out.println("Exc when fetch createHeader : " + e.toString());
         }
    }
    
    private static void constructBody(Document document, EmpWarning warning, Employee employee) 
    {
        String department = "";
        String position = "";
        
        try {            
            // create body I
            Table table = new Table(3);
            int hdrwidths[] = {20, 5, 75};
            table.setWidths(hdrwidths);
            table.setWidth(100);
            table.setPadding(0);
            table.setSpacing(0);
            table.setDefaultCellBorder(Cell.NO_BORDER);
            table.setBorder(Table.NO_BORDER);
            
            if(employee.getDepartmentId() != 0) {
                Department dep = new Department(); 
                
                try {
                    dep = PstDepartment.fetchExc(employee.getDepartmentId());
                    department = dep.getDepartment();
                }
                catch(Exception e) {
                    System.out.println("Exc when fetch Department : " + e.toString());
                }
            }
                       
            if(employee.getPositionId() != 0) {
                Position pos = new Position();         
                
                try {
                    pos = PstPosition.fetchExc(employee.getPositionId());
                    position = pos.getPosition();
                }
                catch(Exception e) {
                    System.out.println("Exc when fetch Position : " + e.toString());
                }
            }
            
            table.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            
            table.addCell(new Phrase("Name/Nama", fontTitleNormal));
            table.addCell(new Phrase(":", fontTitleNormal));
            table.addCell(new Phrase(employee.getFullName(), fontTitleNormal));
            addSpace(table, 3);
            
            table.addCell(new Phrase("Payroll/No. Id ", fontTitleNormal));
            table.addCell(new Phrase(":", fontTitleNormal));
            table.addCell(new Phrase(employee.getEmployeeNum(), fontTitleNormal));
            addSpace(table, 3);
            
            table.addCell(new Phrase("Position/Jabatan", fontTitleNormal));            
            table.addCell(new Phrase(":", fontTitleNormal));
            table.addCell(new Phrase(position, fontTitleNormal));
            addSpace(table, 3);
            
            table.addCell(new Phrase("Department/Bagian", fontTitleNormal));
            table.addCell(new Phrase(":", fontTitleNormal));
            table.addCell(new Phrase(department, fontTitleNormal));
            addSpace(table, 3);
            
            document.add(table);
            
            
            // create body II
            Table table2 = new Table(2);
            int hdrwidths2[] = {3, 97};
            table2.setWidths(hdrwidths2);
            table2.setWidth(100);
            table2.setPadding(0);
            table2.setSpacing(0);
            table2.setDefaultCellBorder(Cell.NO_BORDER);
            table2.setBorder(Table.NO_BORDER);
            
            table2.setDefaultColspan(2);
            table2.addCell(new Phrase("You are proved to violate the following Disciplinary Rules :", fontTitleUnderlined));
            table2.addCell(new Phrase("Saudara telah terbukti melanggar Peraturan Disiplin Karyawan sebagai berikut : ", fontTitleItalic));
            addSpace(table2, 2);
            
            table2.setDefaultColspan(1);
            table2.addCell(new Phrase("1.", fontTitleNormal));
            table2.addCell(new Phrase("The fact and dates :", fontTitleUnderlined));
            addSpace(table2, 1);
            table2.addCell(new Phrase("Bukti Pelanggaran :", fontTitleItalic));
            addSpace(table2, 1);
            table2.addCell(new Phrase(Formater.formatDate(warning.getBreakDate(), "MMMM d, yyyy") + " - " + warning.getBreakFact(), fontTextItem));
            addSpace(table2, 2);
            
            table2.addCell(new Phrase("2.", fontTitleNormal));
            table2.addCell(new Phrase("Verbal warning was given on :", fontTitleUnderlined));
            addSpace(table2, 1);
            table2.addCell(new Phrase("Peringatan lisan diberikan tanggal :", fontTitleItalic));
            addSpace(table2, 1);
            table2.addCell(new Phrase(Formater.formatDate(warning.getWarningDate(), "MMMM d, yyyy"), fontTextItem));
            addSpace(table2, 2);
            
            table2.addCell(new Phrase("3.", fontTitleNormal));
            table2.addCell(new Phrase("Verbal warning was given by :", fontTitleUnderlined));
            addSpace(table2, 1);
            table2.addCell(new Phrase("Peringatan lisan diberikan oleh :", fontTitleItalic));
            addSpace(table2, 1);
            table2.addCell(new Phrase(warning.getWarningBy(), fontTextItem));
            addSpace(table2, 2);
            
            document.add(table2);
        }
        catch(Exception e){
            System.out.println("Exc when fetch createBody : " + e.toString());
        }
    }
    
    private static void constructFooter(Document document, Employee employee) {
         try {            
            // create footer
            Table table = new Table(2);
            int hdrwidths[] = {50, 50};
            table.setWidths(hdrwidths);
            table.setWidth(100);
            table.setPadding(0);
            table.setSpacing(0);
            table.setDefaultCellBorder(Cell.NO_BORDER);
            table.setBorder(Table.NO_BORDER);

            table.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);

            table.addCell(new Phrase("Kuta, " + Formater.formatDate(new Date(), "MMMM dd, yyyy"), fontTitleNormal));
          
            addSpace(table, 3);
            addSpace(table, 2);
     
            table.setDefaultColspan(1);
            table.addCell(new Phrase("___________________________", fontTitleNormal));
            table.addCell(new Phrase(employee.getFullName(), fontTitleUnderlined));
            table.addCell(new Phrase("Division/Department Head", fontHeader2));
            table.addCell(new Phrase("Employee", fontHeader2));
            
            document.add(table);
         }
         catch(Exception e){
            System.out.println("Exc when fetch createFooter : " + e.toString());
         }
    }
    
    private static void addSpace(Table table, int colspan) throws Exception {
        for(int i=0; i<colspan; i++) {
            table.addCell(new Phrase(" "));
        }
    }
     
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException 
    {

        HttpSession session = request.getSession(true);
        Vector temp = (Vector)session.getValue("LETTER_OF_WARNING");

        Document document = new Document(PageSize.A4, 40, 30, 30, 20); 
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try 
        {            
            PdfWriter.getInstance(document, baos);
            document.open();

            EmpWarning warning = (EmpWarning)temp.get(0);  
            Employee employee = (Employee)temp.get(1);
            
            constructHeader(document);
            constructBody(document, warning, employee);  
            constructFooter(document, employee);
        }
        catch(DocumentException de) 
        {
            System.err.println(de.getMessage());
            de.printStackTrace();
        }
        
        document.close();
        
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();  
        baos.writeTo(out);
        out.flush();
        out.close();
    }
    
}