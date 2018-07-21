package com.dimata.harisma.report.attendance;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.Date;

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

public class AnnualLeaveMonthlyPdf extends HttpServlet 
{
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
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException 
    {
    }

    /**
     * @param font1
     * @param oidDept
     * @param lp
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
            headertable.addCell(new Phrase("MONTHLY LEAVE REPORT", fontHeader));
            
            if(selectedDate != null)
            {
                headertable.addCell(new Phrase("PERIOD : "+Formater.formatDate(selectedDate,"MMMM yyyy").toUpperCase(), fontHeader));
            }
            else
            {
                headertable.addCell(new Phrase("PERIOD : -", fontHeader));
            }
            
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
            System.out.println("Exc when createHeader : " + e.toString());
        }
        return headertable;
    }

    
    /**
     * @param font1
     * @param toDt
     * @return
     */    
    public static Table createHeaderDetail(Font fontDetail, Date toDt)
    {  
        Table datatable = null;
        try
        {
            // create table
            datatable = new Table(10);
            datatable.setPadding(1);
            datatable.setSpacing(1);
            int[] headerwidths={3,6,20,5,5,5,5,5,5,5};
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultRowspan(2);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.setDefaultCellBackgroundColor(summaryColor);
            
            datatable.addCell(new Phrase("NO", fontDetail));
            datatable.addCell(new Phrase("PAYROLL", fontDetail));
            datatable.addCell(new Phrase("EMPLOYEE", fontDetail));

            datatable.setDefaultColspan(7);
            datatable.setDefaultRowspan(1);
            datatable.addCell(new Phrase("ANNUAL LEAVE ", fontDetail));

            datatable.setDefaultRowspan(1);
            datatable.setDefaultColspan(1);            
            datatable.addCell(new Phrase("BALANCE "+((toDt.getYear()+1900)-1), fontDetail));
            datatable.addCell(new Phrase("ENTITLED "+(toDt.getYear()+1900), fontDetail));
            datatable.addCell(new Phrase("EARNED YTD", fontDetail));
            datatable.addCell(new Phrase("TAKEN MTD", fontDetail));
            datatable.addCell(new Phrase("TAKEN YTD "+(toDt.getYear()+1900), fontDetail));
            datatable.addCell(new Phrase("BALANCE YTD", fontDetail));
            datatable.addCell(new Phrase("CLEAR " + ((toDt.getYear()+1900)-1) + "+" + (toDt.getYear()+1900), fontDetail));

            // this is the end of the table header
            datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("exc when createHeaderDetail : " + e.toString());
        }
        return datatable;
    }

    
    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException 
    {
        HttpSession session = request.getSession(true);
        Vector temp = (Vector)session.getValue("ANNUAL_LEAVE_REPORT");

        // creating some content that will be used frequently
        Paragraph newLine = new Paragraph("\n", fontHeader);
        Anchor anchor = new Anchor("visit http://www.lowagie.com/iText/", fontHeader);
        anchor.setReference("http://www.lowagie.com/iText/");
        Paragraph link = new Paragraph(anchor);
        link.setAlignment(Element.ALIGN_CENTER);

        // step1: creating the document object
        Document document = new Document(PageSize.A4, 30, 30, 20, 20);
        
        // step2.1: creating an OutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try 
        {
            // step2.2: creating an instance of the writer
            PdfWriter.getInstance(document, baos);
            // step 3.1: adding some metadata to the document
            document.addSubject("This is a report.");
            // step 3.4: opening the document
            document.open();

            // create header
            Date selectedDate = (Date)temp.get(0);            
            long oidDept = Long.parseLong((String)temp.get(1));
            Vector list = (Vector)temp.get(2);

            document.add(createHeader(fontHeader,oidDept,selectedDate));
            Table datatable = createHeaderDetail(fontTitle,selectedDate);  
            datatable.setDefaultRowspan(1);
            datatable.setDefaultCellBackgroundColor(bgCells);

            for (int i = 0; i < list.size(); i++) 
            {
                Vector tmp = (Vector)list.get(i);
                if(i==(list.size()-1))
                {
                    datatable.setDefaultCellBackgroundColor(summaryColor);
                    datatable.setDefaultColspan(3);
                    datatable.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);
                    datatable.addCell(new Phrase((String)tmp.get(2), fontItem));
                    datatable.setDefaultColspan(1);
                    datatable.setDefaultHorizontalAlignment(Element.ALIGN_CENTER);
                    datatable.addCell(new Phrase((String)tmp.get(3), fontItem));
                    datatable.addCell(new Phrase((String)tmp.get(4), fontItem));
                    datatable.addCell(new Phrase((String)tmp.get(5), fontItem));
                    datatable.addCell(new Phrase((String)tmp.get(6), fontItem));
                    datatable.addCell(new Phrase((String)tmp.get(7), fontItem));
                    datatable.addCell(new Phrase((String)tmp.get(8), fontItem));
                    datatable.addCell(new Phrase((String)tmp.get(9), fontItem));
                }
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
                    datatable.addCell(new Phrase((String)tmp.get(8), fontItem));
                    datatable.addCell(new Phrase((String)tmp.get(9), fontItem));
                }
            }
            document.add(datatable);
        }
        catch(DocumentException de) 
        {
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