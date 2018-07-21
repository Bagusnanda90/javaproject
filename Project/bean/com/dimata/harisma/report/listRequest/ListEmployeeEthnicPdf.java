/*
 * ListEmployeeEthnicPdf.java
 *
 * Created on May 23, 2007, 11:34 AM
 */

package com.dimata.harisma.report.listRequest;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;

import com.lowagie.text.*; 
import com.dimata.util.*;
import com.lowagie.text.pdf.PdfWriter;
import com.dimata.harisma.entity.employee.*; 
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.report.ObjListCateg;
import com.dimata.harisma.form.employee.*;
import com.dimata.harisma.session.employee.SessEmployee;
import com.dimata.system.entity.system.*;
import com.dimata.harisma.entity.masterdata.Section;

/**
 *
 * @author  bayu
 */

public class ListEmployeeEthnicPdf extends HttpServlet  {
    
    public static Color bgColor = new Color(200,200,200);  
    public static Color border = new Color(0x00, 0x00, 0x00);

    public static Color blackColor = new Color(0, 0, 0);
    public static Color whiteColor = new Color(255, 255, 255);
    public static Color titleColor = new Color(200, 200, 200);
    public static Color summaryColor = new Color(255, 255, 255);
    public static String formatDate = "MMM dd, yyyy";
    public static String formatNumber = "#,###";

    // setting some fonts in the color chosen by the user
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, border); 
    public static Font fontLamp = new Font(Font.TIMES_NEW_ROMAN, 12);
    public static Font fontLsHeader = new Font(Font.TIMES_NEW_ROMAN, 14, Font.BOLD, border);
    public static Font fontLsHeaderU = new Font(Font.TIMES_NEW_ROMAN, 14, Font.BOLD | Font.UNDERLINE, border);
    public static Font fontLsJudulU = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD | Font.UNDERLINE, border);
    public static Font fontLsContent = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, border);
    public static Font fontLsIsiContent = new Font(Font.TIMES_NEW_ROMAN, 12);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 9);
    public static Font fontNotAvb = new Font(Font.TIMES_NEW_ROMAN, 12, Font.BOLDITALIC, border);
    public static Font fontHeaderLine = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL | Font.UNDERLINE, border);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        
        Color bgColor = new Color(200, 200, 200);
        Rectangle rectangle = new Rectangle(30, 20, 30, 20);
        Document document = new Document(PageSize.A4.rotate(), 30, 20, 30, 20);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String strLevel = "";
        String strFooter = "";
        Date dateFrom = new Date();
        Date dateTo = new Date();
        
        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);            
            
            HttpSession session = request.getSession(true);   
            Vector tmp = (Vector)session.getValue("EMPLOYEE_RACE");
            
            long deptId = Long.parseLong((String)tmp.get(0));
            long sectId = Long.parseLong((String)tmp.get(1));
            Vector data = (Vector)tmp.get(2);
                                    
            document.open();
            document.add(getTableTitle(deptId, sectId));
            document.add(getTableBlank());
            document.add(getHeaderCategory(data));            
            document.add(getEmpContent(data));
        }
        catch (Exception e) {
            System.out.println("Exception e : " + e.toString());
        }
        document.close();

        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }
    
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processRequest(request, response);
    }
    
    public static Table getTableTitle(long oidDept, long oidSect) throws BadElementException, DocumentException {

        int headerInt[] = {10, 90};   
        Table tableHeader = new Table(2);
        
        tableHeader.setCellpadding(1);
        tableHeader.setCellspacing(1);

        tableHeader.setBorderColor(whiteColor);
        tableHeader.setWidth(100);
        
        String nmDept = "";
        String nmSect = "";
        
        if(oidDept != 0)
        {
            Department dept = new Department();
            try
            {
                dept = PstDepartment.fetchExc(oidDept);
            }
            catch(Exception e){}
            
            nmDept = dept.getDepartment();
        }
        else{
            nmDept = "All Departments";
        }
        
        if(oidSect != 0)
        {
            Section sect = new Section();
            try
            {
                sect = PstSection.fetchExc(oidSect);
            }
            catch(Exception e){}
            
            nmSect = sect.getSection();
        }
        else{
            nmSect = "All Sections";
        }
        
        // rows 1 cols 1-2 /
        Cell cellJudul = new Cell(new Chunk("LIST OF EMPLOYEE RACE/ETHNIC ", fontLsHeaderU));
        cellJudul.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellJudul.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellJudul.setBorderColor(whiteColor);
        cellJudul.setBackgroundColor(whiteColor);
        cellJudul.setColspan(2);
        tableHeader.addCell(cellJudul);
        
        Cell cellSpace = new Cell(new Chunk("", fontLsHeaderU));
        cellSpace.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cellSpace.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellSpace.setBorderColor(whiteColor);
        cellSpace.setBackgroundColor(whiteColor);
        cellSpace.setColspan(2);
        tableHeader.addCell(cellSpace);
        
        Cell cellInfo = new Cell(new Chunk("Department : " + nmDept, fontLsHeader));
        cellInfo.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellInfo.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellInfo.setBorderColor(whiteColor);
        cellInfo.setBackgroundColor(whiteColor);
        cellInfo.setColspan(2);
        tableHeader.addCell(cellInfo);
        
        Cell cellSection = new Cell(new Chunk("Section : " + nmSect, fontLsHeader));
        cellSection.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellSection.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellSection.setBorderColor(whiteColor);
        cellSection.setBackgroundColor(whiteColor);
        cellSection.setColspan(2);
        tableHeader.addCell(cellSection);
        
        return tableHeader;    
    }
    
    public static Table getTableBlank() throws BadElementException, DocumentException {
        Table tableHeader = new Table(1);
        tableHeader.setCellpadding(1);
        tableHeader.setCellspacing(1);
        tableHeader.setBorderColor(whiteColor);
        int widthHead[] = {100};
        tableHeader.setWidths(widthHead);
        tableHeader.setWidth(100);

        Cell cellOpname = new Cell(new Chunk("", fontHeader));
        cellOpname.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellOpname.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellOpname.setBackgroundColor(whiteColor);
        cellOpname.setBorderColor(whiteColor);
        tableHeader.addCell(cellOpname);
        return tableHeader;
    }
    
    
    public static Table getHeaderCategory(Vector data) {
   
        try{            
            Vector categories = (Vector)data.get(0);
            
            int[] headerInt = new int[3+(categories.size()*2)];
            headerInt[0] = 3;
            headerInt[1] = 22;
            
            int k = 0;
            
            while(k<categories.size()*2) {
                headerInt[k+2] = 65 / (categories.size()*2);
                k++;
            }            
            
            headerInt[k+2] = 10;
           
            
            Table tbl = new Table(headerInt.length);
        
            tbl.setWidth(100);
            tbl.setWidths(headerInt);
            tbl.setBorderWidth(0);
            tbl.setPadding(1);
            tbl.setSpacing(1);
            tbl.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            tbl.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
                       

            Cell hcell = null ;
            hcell = new Cell(new Chunk("No", fontHeader));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setBackgroundColor(whiteColor); 
            hcell.setRowspan(2);
            tbl.addCell(hcell);
            
            hcell = new Cell(new Chunk("Race/Ethnic", fontHeader));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setBackgroundColor(whiteColor); 
            hcell.setRowspan(2);
            tbl.addCell(hcell); 
            
            
            for(int i=0; i<categories.size(); i++) {
                hcell = new Cell(new Chunk(""+categories.get(i), fontHeader));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                hcell.setBackgroundColor(whiteColor); 
                hcell.setColspan(2);
                tbl.addCell(hcell); 
            }
            
                       
            hcell = new Cell(new Chunk("Total", fontHeader));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setBackgroundColor(whiteColor); 
            hcell.setRowspan(2);
            tbl.addCell(hcell); 
            
            
            for(int i=0; i<categories.size(); i++) {
                hcell = new Cell(new Chunk("M", fontHeader));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                hcell.setBackgroundColor(whiteColor);
                tbl.addCell(hcell); 
                
                hcell = new Cell(new Chunk("F", fontHeader));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                hcell.setBackgroundColor(whiteColor);
                tbl.addCell(hcell); 
            }
            
            return tbl;
          
        }
        catch(Exception e){
            System.out.println("Error di header "+e.toString());
        }
        
        return null;
    }
   
    public Table getEmpContent(Vector data) {
        
        try{            
            Vector categories = (Vector)data.get(0);
            Vector empCounts = (Vector)data.get(1);
            
            int[] headerInt = new int[3+(categories.size()*2)];
            headerInt[0] = 3;
            headerInt[1] = 22;
            
            int k = 0;
            
            while(k<categories.size()*2) {
                headerInt[k+2] = 65 / (categories.size()*2);
                k++;
            }            
            
            headerInt[k+2] = 10;
           
            
            Table tbl = new Table(headerInt.length);
        
            tbl.setWidth(100);
            tbl.setWidths(headerInt);
            tbl.setBorderWidth(0);
            tbl.setPadding(1);
            tbl.setSpacing(1);
            tbl.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            tbl.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            
            
            for(int i=0; i<empCounts.size(); i++) {
                Vector dataList = (Vector)empCounts.get(i);
                
                for(int j=0; j<dataList.size(); j++) {
                    
                    if(i != empCounts.size()-1) {
                        Cell hcell = null ;
                        hcell = new Cell(new Chunk(""+dataList.get(j), fontContent));
                        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        hcell.setBackgroundColor(whiteColor); 
                        tbl.addCell(hcell);                   
                    }
                    else {
                        String content = ""+dataList.get(j);
                        content = content.replace("<b>", "");
                        content = content.replace("</b>", "");
                        
                        Cell hcell = null ;
                        hcell = new Cell(new Chunk(content, fontHeader));
                        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        hcell.setBackgroundColor(whiteColor); 
                        tbl.addCell(hcell);  
                    }
                }
            }
            
            return tbl;
          
        }
        catch(Exception e){
            System.out.println("Error di detail "+e.toString());
        }
        
        return null;
        
    }
    
}
