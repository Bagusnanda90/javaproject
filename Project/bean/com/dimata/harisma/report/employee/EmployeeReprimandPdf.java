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


public class EmployeeReprimandPdf extends HttpServlet {
   
    static Color border = new Color(0x00, 0x00, 0x00);
    static Color bgTable = new Color(200, 200, 200);
    static Color bgCells = new Color(255, 255, 255);    
    
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);
    public static Color titleColor = new Color(200,200,200);
    public static Color summaryColor = new Color(240,240,240);
       
    public static Font fontHeader1 = new Font(Font.HELVETICA, 14, Font.BOLD + Font.UNDERLINE, blackColor);
    public static Font fontHeader2 = new Font(Font.HELVETICA, 11, Font.BOLD, blackColor);
    public static Font fontTitleNormal = new Font(Font.HELVETICA, 8, Font.NORMAL, blackColor);
    public static Font fontTitleBold = new Font(Font.HELVETICA, 8, Font.BOLD, blackColor);
    public static Font fontTitleUnderlined = new Font(Font.HELVETICA, 8, Font.UNDERLINE, blackColor); 
    public static Font fontTitleItalic = new Font(Font.HELVETICA, 8, Font.ITALIC, blackColor);
    public static Font fontTextItem = new Font(Font.HELVETICA, 8, Font.NORMAL, blackColor);
    
    private static String imgRoot = "http://localhost:8080/HarismaVerX.1.0/images/logo.jpg";
    private static int number = 1;

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
            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("SURAT PERINGATAN", fontHeader1));
            table.addCell(new Phrase("REPRIMAND LETTER", fontHeader2)); 
            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("", fontTitleNormal));
          
            document.add(table);           
         }
         catch(Exception e){
            System.out.println("Exc when fetch createHeader : " + e.toString());
         }
    }
    
    private static void constructBody(Document document, EmpReprimand reprimand, Employee employee) 
    {
        String department = "";
        String position = "";
        
        try {       
            // create body I
            Table table = new Table(4);
            int hdrwidths[] = {3, 47, 47, 3};
            table.setWidths(hdrwidths);
            table.setWidth(100);
            table.setPadding(0);
            table.setSpacing(0);
            table.setDefaultCellBorder(Cell.NO_BORDER);
            table.setBorder(Table.BOTTOM);
            table.setBorderWidth(1);
            table.setBorderColor(border);
            
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

            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("Nama", fontTitleNormal));
            table.addCell(new Phrase("Nomor Id", fontTitleNormal));    
            table.addCell(new Phrase("", fontTitleNormal));
            
            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("Name : " + employee.getFullName(), fontTitleItalic));
            table.addCell(new Phrase("Payroll Number : " + employee.getEmployeeNum(), fontTitleItalic));
            table.addCell(new Phrase("", fontTitleNormal));
            
            addSpace(table, 4);

            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("Jabatan", fontTitleNormal));
            table.addCell(new Phrase("Departemen", fontTitleNormal));
            table.addCell(new Phrase("", fontTitleNormal));
            
            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("Position : " + position, fontTitleItalic));
            table.addCell(new Phrase("Department : " + department, fontTitleItalic));
            table.addCell(new Phrase("", fontTitleNormal));
            
            addSpace(table, 4);

            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("Surat Peringatan Ke ", fontTitleNormal));            
            table.addCell(new Phrase("Masa Kerja", fontTitleNormal));
            table.addCell(new Phrase("", fontTitleNormal));
            
            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("Reprimand Letter No : " + number, fontTitleItalic));
            table.addCell(new Phrase("Length of Service : " + getLengthOfService(employee.getCommencingDate()), fontTitleItalic));
            table.addCell(new Phrase("", fontTitleNormal));
                    
            addSpace(table, 4);
            document.add(table);
            
            
            // create body II
            Table table2 = new Table(5);
            int hdrwidths2[] = {3, 31, 32, 31, 3};
            table2.setWidths(hdrwidths2);
            table2.setWidth(100);
            table2.setPadding(0);
            table2.setSpacing(0);
            table2.setDefaultCellBorder(Cell.NO_BORDER);
            table2.setBorder(Table.BOTTOM);
            table2.setBorderWidth(1);
            table2.setBorderColor(border);
            
            table2.addCell(new Phrase("", fontTitleNormal));
            table2.setDefaultColspan(3);
            table2.addCell(new Phrase("Dengan ini Saudara diberi peringatan keras atas pelanggaran terhadap Peraturan Perusahaan sebagaimana tercantum dalam :", fontTitleNormal));
            table2.setDefaultColspan(1);
            table2.addCell(new Phrase("", fontTitleNormal));
            table2.addCell(new Phrase("", fontTitleNormal));
            table2.setDefaultColspan(3);
            table2.addCell(new Phrase("This letter of reprimand is given to you, due to your failure to adhere to the rule(s) noted in :", fontTitleItalic));
            table2.setDefaultColspan(1);
            table2.addCell(new Phrase("", fontTitleNormal));
              
            addSpace(table2, 5);
                       
            table2.addCell(new Phrase("", fontTitleNormal));
            table2.addCell(new Phrase("Bab", fontTitleNormal));
            table2.addCell(new Phrase("Pasal", fontTitleNormal));
            table2.addCell(new Phrase("Halaman", fontTitleNormal));
            table2.addCell(new Phrase("", fontTitleNormal));
            
            table2.addCell(new Phrase("", fontTitleNormal));
            table2.addCell(new Phrase("Chapter : " + reprimand.getChapter(), fontTitleItalic));
            table2.addCell(new Phrase("Article : " + reprimand.getArticle(), fontTitleItalic));
            table2.addCell(new Phrase("Page : " + reprimand.getPage(), fontTitleItalic));
            table2.addCell(new Phrase("", fontTitleNormal));
            
            addSpace(table2, 5);
             
            table2.addCell(new Phrase("", fontTitleNormal));
            table2.setDefaultColspan(4);            
            table2.addCell(new Phrase("Of our \"Collective Labor Aggreement\"", fontTitleNormal));
            
            table2.setDefaultColspan(5);
            addSpace(table2, 1);
            document.add(table2);
                    
            
            // create body III
            Table table3 = new Table(3);
            int hdrwidths3[] = {3, 94, 3};
            table3.setWidths(hdrwidths3);
            table3.setWidth(100);
            table3.setPadding(0);
            table3.setSpacing(0);
            table3.setDefaultCellBorder(Cell.NO_BORDER);
            table3.setBorder(Table.BOTTOM);
            table3.setBorderWidth(1);
            table3.setBorderColor(border);
            
            table3.addCell(new Phrase("", fontTitleNormal));
            table3.addCell(new Phrase("Uraian Pelanggaran/Description of Misconduct :", fontTitleNormal));
            table3.addCell(new Phrase("", fontTitleNormal));
            
            addSpace(table3, 3);
            
            table3.setDefaultHorizontalAlignment(Table.ALIGN_JUSTIFIED);
            table3.addCell(new Phrase("", fontTitleNormal));
            table3.addCell(new Phrase(reprimand.getDescription(), fontTitleBold));
            table3.addCell(new Phrase("", fontTitleNormal));
            
            addSpace(table3, 3);
            document.add(table3);
           
            
            // create body IV
            Table table4 = new Table(3);
            int hdrwidths4[] = {3, 94 ,3};
            table4.setWidths(hdrwidths4);
            table4.setWidth(100);
            table4.setPadding(0);
            table4.setSpacing(0);
            table4.setDefaultCellBorder(Cell.NO_BORDER);
            table4.setBorder(Table.BOTTOM);
            table4.setBorderWidth(1);
            table4.setBorderColor(border);
            
            table4.addCell(new Phrase("", fontTitleNormal));
            table4.addCell(new Phrase("Catatan/Note : ", fontTitleNormal));
            table4.addCell(new Phrase("", fontTitleNormal));            
                        
            table4.addCell(new Phrase("", fontTitleNormal));
            table4.addCell(new Phrase("Diminta kesungguhan Saudara untuk tidak mengulangi kesalahan " +
                                      "tersebut di atas, yang akan berakibat dikeluarkannya Surat Peringatan " +
                                      "yang lebih keras dan atau Pemecatan, sesuai dengan ketentuan dalam Surat " +
                                      "Kesepakatan Kerja Bersama (SKKB).", fontTitleNormal));
            table4.addCell(new Phrase("", fontTitleNormal));
            
            table4.addCell(new Phrase("", fontTitleNormal));
            table4.addCell(new Phrase("Should you repeat this misconduct, stronger disciplinary action will be taken " +
                                      "which may lead to dismissal.", fontTitleItalic));
            table4.addCell(new Phrase("", fontTitleNormal));
            
            addSpace(table4, 3);
            document.add(table4);           
        }
        catch(Exception e){
            System.out.println("Exc when fetch createBody : " + e.toString());
        }
    }
    
    private static void constructFooter(Document document, Employee employee) {
         try {            
            // create footer
            Table table = new Table(5);
            int hdrwidths[] = {3, 31, 32, 31, 3};
            table.setWidths(hdrwidths);
            table.setWidth(100);
            table.setPadding(0);
            table.setSpacing(0);
            table.setDefaultCellBorder(Cell.NO_BORDER);
            table.setBorder(Table.BOTTOM);

            table.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);

            addSpace(table, 5);
            addSpace(table, 5);
            
            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase(employee.getFullName(), fontTitleUnderlined));
            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("Date : " + Formater.formatDate(new Date(), "MMMM d, yyyy"), fontTitleNormal));
            table.addCell(new Phrase("", fontTitleNormal));
            
            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("TANDA TANGAN KARYAWAN", fontTitleNormal));
            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("", fontTitleNormal));

            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("BAND MEMBERS SIGNATURE", fontTitleItalic));
            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("", fontTitleNormal));
            
            addSpace(table, 5);
            addSpace(table, 5);
            addSpace(table, 5);
            addSpace(table, 5);
            
            table.setDefaultColspan(1);
            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("__________________", fontTitleNormal));
            table.addCell(new Phrase("_______________________________", fontTitleNormal));
            table.addCell(new Phrase("__________________", fontTitleNormal));
            table.addCell(new Phrase("", fontTitleNormal));
            
            table.addCell(new Phrase("", fontTitleNormal));
            table.addCell(new Phrase("DEPARTMENT HEAD", fontTitleNormal));
            table.addCell(new Phrase("DIRECTOR OF HUMAN RESOURCES", fontTitleNormal));
            table.addCell(new Phrase("GENERAL MANAGER", fontTitleNormal));
            table.addCell(new Phrase("", fontTitleNormal));
            
            addSpace(table, 5);
            document.add(table);
         }
         catch(Exception e){
            System.out.println("Exc when fetch createFooter : " + e.toString());
         }
    }
    
    private static void addSpace(Table table, int colspan) throws Exception {
        for(int i=0; i<colspan; i++) {
            table.addCell(new Phrase(" ", fontTitleNormal));
        }
    }
     
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException 
    {

        HttpSession session = request.getSession(true);
        Vector temp = (Vector)session.getValue("REPRIMAND_LETTER");

        Document document = new Document(PageSize.A4, 40, 40, 25, 25); 
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try 
        {            
            PdfWriter.getInstance(document, baos);
            document.open();

            EmpReprimand reprimand = (EmpReprimand)temp.get(0);  
            Employee employee = (Employee)temp.get(1);
            number = ((Integer)temp.get(2)).intValue();
                        
            constructHeader(document);
            constructBody(document, reprimand, employee);  
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
    
    private static String getLengthOfService(Date commencing) {
        int year = 0;
        int month = 0;
        Date now = new Date();
        
        int year1 = commencing.getYear();
        int month1 = commencing.getMonth();
        
        int year2 = now.getYear();
        int month2 = now.getMonth();
        
        if(year2 > year1) {
            year = year2 - year1;
            
            if(month2 > month1) {
                month = month2 - month1;
            }
            else {
                year = year - 1;
                month = (12 + month2) - month1;
            }
        }
        else if(year2 == year1) {
            month = month2 - month1;
        }
        
        return (year > 0 ? year + " tahun " : "") + (month > 0 ? month + " bulan " : "");
    }
    
}