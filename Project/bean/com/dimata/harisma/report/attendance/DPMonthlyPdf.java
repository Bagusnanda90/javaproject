package com.dimata.harisma.report.attendance;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.Date;
import java.util.*;

import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.util.Formater;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;  

public class DPMonthlyPdf extends HttpServlet {
    // public static final membervariables

    static Color border = new Color(0x00, 0x00, 0x00);
    static Color bgTable = new Color(200, 200, 200);
    static Color bgCells = new Color(255, 255, 255);    
    
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);
    public static Color titleColor = new Color(200,200,200);
    public static Color summaryColor = new Color(240,240,240);
    
    public static Font fontHeader = new Font(Font.HELVETICA, 9, Font.BOLD, blackColor);
    public static Font fontTitle = new Font(Font.HELVETICA, 7, Font.NORMAL, blackColor);        
    public static Font fontItem = new Font(Font.HELVETICA, 7, Font.NORMAL, blackColor);    

    /**
     * @param fontHeader
     * @param oidDept
     * @param selectedDate
     * @return
     */    
    public static Table createHeader(Font fontHeader, long oidDept, Date selectedDate)   
    {
        Table headertable = null;
        try
        {
            headertable = new Table(1);
            int hdrwidths[] = {100};
            headertable.setWidths(hdrwidths);
            headertable.setWidth(100);
            headertable.setPadding(0);
            headertable.setSpacing(0);
            headertable.setDefaultCellBorder(Cell.NO_BORDER);
            headertable.setBorder(Table.NO_BORDER);

            headertable.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            headertable.addCell(new Phrase("MONTHLY DP REPORT", fontHeader));
            headertable.addCell(new Phrase("PERIOD : " + Formater.formatDate(selectedDate,"MMMM yyyy").toUpperCase(), fontHeader));
            
            String sDepartmentName = ""; 
            if(oidDept != 0)
            {
                Department dep = new Department();            
                try
                {
                    dep = PstDepartment.fetchExc(oidDept);
                    sDepartmentName = dep.getDepartment();
                }
                catch(Exception e)
                {
                    System.out.println("Exc when fetch Department : " + e.toString());
                }
            }
            else
            {
                sDepartmentName = "ALL DEPARTMENT";
            }
            
            headertable.addCell(new Phrase("DEPARTMENT : "+sDepartmentName.toUpperCase(), fontHeader));
        }
        catch(Exception e)
        {
            System.out.println("Exc when fetch createHeader : " + e.toString());
        }

        return headertable;
    }

    
    /**
     * @param fontHeader
     * @param toDt
     * @return
     */    
    public static Table createHeaderDetail(Font fontHeader, String[] strTitle)
    {        
        Table datatable = null;
        try
        {
            // create table
            datatable = new Table(8);
            datatable.setPadding(1);
            datatable.setSpacing(1);
            int[] headerwidths={2,5,17,5,5,5,5,5};
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultRowspan(2);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.setDefaultCellBackgroundColor(summaryColor);
            
            datatable.addCell(new Phrase(strTitle[0], fontHeader));
            datatable.addCell(new Phrase(strTitle[1], fontHeader));
            datatable.addCell(new Phrase(strTitle[2], fontHeader));
            datatable.addCell(new Phrase(strTitle[3], fontHeader));
            datatable.setDefaultColspan(4);
            datatable.setDefaultRowspan(1);
            datatable.addCell(new Phrase(strTitle[4], fontHeader));
            datatable.setDefaultRowspan(1);
            datatable.setDefaultColspan(1);
            datatable.addCell(new Phrase(strTitle[5], fontHeader));
            datatable.addCell(new Phrase(strTitle[6], fontHeader));
            datatable.addCell(new Phrase(strTitle[7], fontHeader));
            datatable.addCell(new Phrase(strTitle[8], fontHeader));

            // this is the end of the table header
            datatable.endHeaders();
        }
        catch(Exception e)       
        {
            System.out.println("Exc createHeaderDetail : " + e.toString());
        }
        return datatable;
    }

     
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException 
    {

        HttpSession session = request.getSession(true);
        Vector temp = (Vector)session.getValue("DP_REPORT");

        Paragraph newLine = new Paragraph("\n", fontHeader);
        Anchor anchor = new Anchor("visit http://www.lowagie.com/iText/", fontHeader);
        anchor.setReference("http://www.lowagie.com/iText/");
        Paragraph link = new Paragraph(anchor);
        link.setAlignment(Element.ALIGN_CENTER);

        Document document = new Document(PageSize.A4, 30, 30, 20, 20);        

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try 
        {
            
            PdfWriter.getInstance(document, baos);
            document.open();

            String[] strTitle = (String[])temp.get(0);
            Date selectedDate = (Date)temp.get(1);
            long oidDept = Long.parseLong((String)temp.get(2));  
            Vector list = (Vector)temp.get(3);            

            document.add(createHeader(fontHeader, oidDept, selectedDate));  
            Table datatable = createHeaderDetail(fontTitle, strTitle);
            datatable.setDefaultRowspan(1);
            datatable.setDefaultCellBackgroundColor(bgCells);
            
            for (int i = 0; i < list.size(); i++) 
            {
                Vector tmp = (Vector)list.get(i);
                
                // menggambar total
                if(i==(list.size()-1))
                {
                    datatable.setDefaultCellBackgroundColor(summaryColor);
                    datatable.setDefaultColspan(3);
                    datatable.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);
                    datatable.addCell(new Phrase((String)tmp.get(2), fontTitle));
                    datatable.setDefaultColspan(1);
                    datatable.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);
                    datatable.addCell(new Phrase((String)tmp.get(3), fontTitle));
                    datatable.addCell(new Phrase((String)tmp.get(4), fontTitle));
                    datatable.addCell(new Phrase((String)tmp.get(5), fontTitle));
                    datatable.addCell(new Phrase((String)tmp.get(6), fontTitle));
                    datatable.addCell(new Phrase((String)tmp.get(7), fontTitle));
                }
                
                // menggambar list data
                else
                {
                    datatable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
                    datatable.addCell(new Phrase((String)tmp.get(0), fontItem));
                    datatable.addCell(new Phrase((String)tmp.get(1), fontItem));
                    datatable.addCell(new Phrase((String)tmp.get(2), fontItem));
                    datatable.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);
                    datatable.addCell(new Phrase((String)tmp.get(3), fontItem));
                    datatable.addCell(new Phrase((String)tmp.get(4), fontItem));
                    datatable.addCell(new Phrase((String)tmp.get(5), fontItem));
                    datatable.addCell(new Phrase((String)tmp.get(6), fontItem));
                    datatable.addCell(new Phrase((String)tmp.get(7), fontItem));  
                }
            }
            document.add(datatable);
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
    }
}