
package com.dimata.harisma.report.leave;

// package java
import java.util.*;
import java.text.*;
import java.awt.Color;
import java.io.ByteArrayOutputStream;  
import java.io.IOException;

// package servlet
import javax.servlet.*;
import javax.servlet.http.*;

// package lowagie
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

// package qdep
import com.dimata.util.*;
import com.dimata.qdep.form.*;

// package harisma
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.system.entity.PstSystemProperty;


public class LeaveRequestPdf extends HttpServlet {   
   
    private static Font fontHeader = new Font(Font.HELVETICA, 12, Font.BOLD, Color.BLACK);    
    private static Font fontHeaderItalic = new Font(Font.HELVETICA, 12, Font.BOLDITALIC, Color.BLACK);
    private static Font fontContent = new Font(Font.HELVETICA, 8, Font.NORMAL, Color.BLACK);
    private static Font fontContentItalic = new Font(Font.HELVETICA, 8, Font.ITALIC, Color.BLACK);  
    private static Font fontContentBold = new Font(Font.HELVETICA, 8, Font.BOLD, Color.BLACK);
    private static Font fontContentBoldItalic = new Font(Font.HELVETICA, 8, Font.BOLDITALIC, Color.BLACK);
    private static Font fontContentUnderlined = new Font(Font.HELVETICA, 8, Font.UNDERLINE, Color.BLACK);
    private static Font fontContentUnderlinedItalic = new Font(Font.HELVETICA, 8, Font.UNDERLINE + Font.ITALIC, Color.BLACK);
    private static Font fontContentUnderlinedBold = new Font(Font.HELVETICA, 8, Font.UNDERLINE + Font.BOLD, Color.BLACK);
  
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        Document document = new Document(PageSize.A4, 30, 30, 40, 40);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {            
            PdfWriter writer = PdfWriter.getInstance(document, baos);
                                 
            Vector objLeaveApplicationReport = new Vector();            
            SpecialLeave leave = new SpecialLeave();
            Vector leaveDetails = new Vector();           
            int maxApproval = 0;
            
            try {
                HttpSession sessLeaveApplication = request.getSession(true);   
	        objLeaveApplicationReport = (Vector) sessLeaveApplication.getValue("LEAVE_APPLICATION");
                
                if(objLeaveApplicationReport != null) {
                    leave = (SpecialLeave)objLeaveApplicationReport.get(0);
                    leaveDetails = (Vector)objLeaveApplicationReport.get(1);   
                    maxApproval = Integer.parseInt(String.valueOf(objLeaveApplicationReport.get(2)));
                }
            }
            catch(Exception e) {
                System.out.println("Exc fetching session object: "+e.toString());
            }
          
            document.open(); 
            
            // create header table
            Table headerTable = createTableHeader();
            document.add(headerTable);    
            
            // create main table
            int[] colWidths = new int[100];
            
            for(int i=0; i<colWidths.length; i++) {
                colWidths[i] = 1;   // creating grids
            }
            
            Table mainTable = new Table(colWidths.length); 
            mainTable.setWidth(100);
            mainTable.setWidths(colWidths);                        
            mainTable.setPadding(0);
            mainTable.setSpacing(0);
            mainTable.setBorderColor(Color.BLACK);
            mainTable.setBorderWidth(1);
            mainTable.setDefaultCellBorder(Cell.NO_BORDER);
            mainTable.setDefaultCellBorderColor(Color.BLACK);
            mainTable.setDefaultCellBorderWidth(1);
            mainTable.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
            mainTable.setDefaultVerticalAlignment(Table.ALIGN_TOP);
            
            createEmployeeInfo(mainTable, leave);
            createLeaveDetail(mainTable, leaveDetails);
            createLeaveNote(mainTable, leave);
            createLeaveApproval(mainTable, leave, maxApproval);
         
            document.add(mainTable);
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
            de.printStackTrace();
        }

        document.close();

        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }    
    
    
    public static Table createTableHeader() 
    throws BadElementException, DocumentException {
        
        // creating table      
        Table table = new Table(1);
        table.setWidth(100);      
        table.setWidths(new int[] {100});
        table.setPadding(0);
        table.setSpacing(0);        
        table.setBorder(Table.NO_BORDER);
        table.setDefaultCellBorder(Cell.NO_BORDER);
        table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        
        // creating header logo        
        try {
            String imgRoot = PstSystemProperty.getValueByName("IMG_ROOT") + "logo.jpg";
            Image image = Image.getInstance(imgRoot);
            image.setAlignment(Image.MIDDLE);   

            Cell cell = new Cell(new Chunk("", fontHeader));   
            cell.add(image);
            
            table.addCell(cell);
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
        // creating header title       
        table.addCell(new Cell(new Phrase("LEAVE REQUEST", fontHeader)));
        
        return table;
    }
    
    
    public static void createEmployeeInfo(Table table, SpecialLeave leave) 
    throws BadElementException, DocumentException {
        
        Employee emp = new Employee();
        Division div = new Division();
        Department dept = new Department();
        Position pos = new Position();
        
        try {
            emp = PstEmployee.fetchExc(leave.getEmployeeId());
        }
        catch(Exception e) {
            emp = new Employee();
        }
        
        try {
            long oidDiv = emp.getDivisionId();
            div = PstDivision.fetchExc(oidDiv);
        }
        catch(Exception e) {
            div = new Division();
        }
        
        try {
            long oidDept = emp.getDepartmentId();
            dept = PstDepartment.fetchExc(oidDept);
        }
        catch(Exception e) {
            dept = new Department();
        }
        
        try {
            long oidPos = emp.getPositionId();
            pos = PstPosition.fetchExc(oidPos);
        }
        catch(Exception e) {
            pos = new Position();
        }
        
        // adding space
        Cell cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(100);
        table.addCell(cell);
                         
        // first line
        cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(4);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("Name", fontContent));
        cell.setColspan(12);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(":", fontContent));
        cell.setColspan(5);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(emp.getFullName(), fontContent));
        cell.setColspan(39);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(40);
        table.addCell(cell);
        
        
        // second line        
        cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(4);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("Position", fontContent));
        cell.setColspan(12);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(":", fontContent));
        cell.setColspan(5);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(pos.getPosition(), fontContent));
        cell.setColspan(39);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("ID No.", fontContent));
        cell.setColspan(11);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(":", fontContent));
        cell.setColspan(5);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(emp.getEmployeeNum(), fontContent));
        cell.setColspan(20);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(4);
        table.addCell(cell);
        
        
        // third line        
        cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(4);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("Division", fontContent));
        cell.setColspan(12);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(":", fontContent));
        cell.setColspan(5);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(div.getDivision(), fontContent));
        cell.setColspan(39);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("Department", fontContent));
        cell.setColspan(11);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(":", fontContent));
        cell.setColspan(5);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(dept.getDepartment(), fontContent));
        cell.setColspan(20);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(4);
        table.addCell(cell);
        
        
        // fourth line        
        cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(4);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("Comm. Date", fontContent));
        cell.setColspan(12);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(":", fontContent));
        cell.setColspan(5);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(Formater.formatDate(emp.getCommencingDate(), "MMM dd, yyyy"), fontContent));
        cell.setColspan(39);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("Date of Req.", fontContent));
        cell.setColspan(11);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(":", fontContent));
        cell.setColspan(5);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(Formater.formatDate(leave.getRequestDate(), "MMM dd, yyyy"), fontContent));
        cell.setColspan(20);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(4);
        table.addCell(cell);
        
        
        // adding space
        cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(100);
        table.addCell(cell);
        
    }
    
    public static void createLeaveDetail(Table table, Vector leaveDetails) 
    throws BadElementException, DocumentException {
        
        table.setDefaultCellBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
        table.setPadding(2);
        
        // header       
        Cell cell = new Cell(new Phrase("Type of Leave", fontContent));
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);        
        cell.setColspan(36);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("No.of Days Eligible", fontContent));
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setColspan(12);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("No.of Days Requested", fontContent));
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setColspan(12);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("Date", fontContent));
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setColspan(40);
        table.addCell(cell);
        
        
         table.setDefaultCellBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.LEFT);
        
        // detail
        if(leaveDetails != null) {
            
            for(int i=0; i<leaveDetails.size(); i++) {
                Vector list = (Vector)leaveDetails.get(i);
               
                cell = new Cell(new Phrase("        " + (String)list.get(1),fontContent));
                cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cell.setColspan(36);
                table.addCell(cell);

                cell = new Cell(new Phrase((String)list.get(2) + "  ",fontContent));
                cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cell.setColspan(12);
                table.addCell(cell);

                cell = new Cell(new Phrase((String)list.get(3) + "  ",fontContent));
                cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cell.setColspan(12);
                table.addCell(cell);

                cell = new Cell(new Phrase("    " + replaceHTML((String)list.get(4)),fontContent));
                cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cell.setColspan(40);
                table.addCell(cell);

            }
            
        }
        
    }
    
    public static void createLeaveNote(Table table, SpecialLeave leave) 
    throws BadElementException, DocumentException {
              
        table.setDefaultCellBorder(Cell.NO_BORDER);
        
        // adding space
        Cell cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(100);
        table.addCell(cell);
        
        // first line  
        cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(4);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("Unpaid Leave",fontContentBold));
        cell.setColspan(96);
        table.addCell(cell);

        // second line
        cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(4);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("Reason for Unpaid Leave", fontContent));
        cell.setColspan(25);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(":", fontContent));
        cell.setColspan(5);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(leave.getUnpaidLeaveReason(),fontContentBold));
        cell.setColspan(62);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(4);
        table.addCell(cell);
        
        // third line        
         cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(4);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("Other Remarks", fontContent));
        cell.setColspan(25);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(":", fontContent));
        cell.setColspan(5);
        table.addCell(cell);
        
        cell = new Cell(new Phrase(leave.getOtherRemarks(),fontContentBold));
        cell.setColspan(62);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(4);
        table.addCell(cell);
        
        // adding space
        cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(100);
        table.addCell(cell);
       
    }
    
    public static void createLeaveApproval(Table table, SpecialLeave leave, int approval) 
    throws BadElementException, DocumentException {
       
        table.setDefaultCellBorder(Rectangle.TOP | Rectangle.BOTTOM);
        table.setPadding(2);
        
        // header       
        Cell cell = new Cell(new Phrase("", fontContent));             
        cell.setColspan(4);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("Approval", fontContentBold));
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);        
        cell.setColspan(36);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("Signature", fontContentBold));
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setColspan(30);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("Date", fontContentBold));
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setColspan(26);
        table.addCell(cell);
        
        cell = new Cell(new Phrase("", fontContent));             
        cell.setColspan(4);
        table.addCell(cell);
        
        
        table.setDefaultCellBorder(Table.NO_BORDER);
        
        String[] approver = {
            "Band Member",
            "Division/Department Head",
            "Producer - Talent",
            "Executive Producer"
        };
       
        for(int i=0; i<=approval; i++) {
                        
            Employee emp = new Employee();
            Date date = new Date();
            long oid = 0;
            
            switch(i) {
                case 0 :
                    oid = leave.getEmployeeId();
                    date = leave.getRequestDate();
                    break;
                    
                case 1 :
                    oid = leave.getApprovalId();
                    date = leave.getApprovalDate();
                    break;
                    
                case 2 :
                    oid = leave.getApproval2Id();
                    date = leave.getApproval2Date();
                    break;
                    
                case 3 :
                    oid = leave.getApproval3Id();
                    date = leave.getApproval3Date();
                    break;
            }
                        
            try {
                emp = PstEmployee.fetchExc(oid);
            }
            catch(Exception e) {}
            
            
            cell = new Cell(new Phrase("",fontContent));
            cell.setColspan(4);
            table.addCell(cell);
        
            cell = new Cell(new Phrase(approver[i],fontContent));
            cell.setColspan(25);
            table.addCell(cell);
            
            cell = new Cell(new Phrase(":",fontContent));
            cell.setColspan(5);
            table.addCell(cell);
            
            cell = new Cell(new Phrase(emp.getFullName(),fontContent));
            cell.setColspan(13);
            table.addCell(cell);
            
            cell = new Cell(new Phrase("",fontContent));
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setColspan(23);
            table.addCell(cell);
            
            cell = new Cell(new Phrase(Formater.formatDate(date, "MMM dd, yyyy"),fontContent));
            cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setColspan(26);
            table.addCell(cell);
            
            cell = new Cell(new Phrase("",fontContent));
            cell.setColspan(4);
            table.addCell(cell);
            
        }
        
        // adding space
        cell = new Cell(new Phrase("", fontContent));
        cell.setColspan(100);
        table.addCell(cell);
        
    }     
    
    private static String replaceHTML(String str) {
        String temp = "";
        
        temp = str.replaceAll("&nbsp;", " ");
        
        temp = temp.replaceAll("&ndash;", "-");
        
        temp = temp.replaceAll("<br />", "\n");
        
        temp = temp.replaceAll("\\<.*?>","");
        
        return temp;
    }
    
}