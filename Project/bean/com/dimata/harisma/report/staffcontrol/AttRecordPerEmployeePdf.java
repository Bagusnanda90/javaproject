/*
 * AttRecordPerEmployeePdf.java
 *
 * Created on July 13, 2004, 1:18 PM
 */

package com.dimata.harisma.report.staffcontrol;

/* package java */
import java.util.*;
import java.text.*;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/* package servlet */
import javax.servlet.*;
import javax.servlet.http.*;

/* package lowagie */
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

/* package qdep */
import com.dimata.util.*;
import com.dimata.qdep.form.*;

/* package harisma */
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.admin.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.session.attendance.*;

/**
 *
 * @author  gedhy
 */
public class AttRecordPerEmployeePdf extends HttpServlet {
    
    /** Attribute declaration
    */
    public static String textCurrency[] = {"IDR","USD"};

    /* declaration constant */
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);
    public static Color titleColor = new Color(200,200,200);
    public static Color summaryColor = new Color(240,240,240);
    public static String formatDate  = "MMM dd, yyyy";
    public static String formatNumber = "#,###";

    /* setting some fonts in the color chosen by the user */
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, blackColor);
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL, blackColor);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL, blackColor);

    /** Initializes the servlet
    */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /** Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** Destroys the servlet
    */
    public void destroy() {
    }

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        /* setting some constant */
        String currText[] = {"(IRD)","(US$)"};

        /* creating the document object */
        Document document = new Document(PageSize.A4, 30, 30, 50, 50);

	/* creating an OutputStream */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try 
        {
            /* creating an instance of the writer */
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            /* get data from session */
            HttpSession sessEmpPresence = request.getSession(true);
            Vector vectEmployeePresence = null;
            try
            {
	            vectEmployeePresence = (Vector)sessEmpPresence.getValue("ATTENDANCE_RECORD_MONTHLY");
            }
            catch(Exception e)
            {
                System.out.println("Exc : "+e.toString());
            }

            String strDepartment = (String)vectEmployeePresence.get(0);
            String strEmployee = (String)vectEmployeePresence.get(1);
            String strPeriod = (String)vectEmployeePresence.get(2);
            Vector vEmpPresece = (Vector)vectEmployeePresence.get(3);

            /* adding a Header of page, i.e. : title, align and etc */
            String strReportTitle = "ATTENDANCE RECORD PER " + strPeriod.toUpperCase() +
                		    "\nDEPARTMENT : "+ strDepartment.toUpperCase() +
                                    "\nEMPLOYEE : "+strEmployee.toUpperCase();
            HeaderFooter header = new HeaderFooter(new Phrase(strReportTitle, fontHeader), false);
            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);

            /* opening the document, needed for add something into document */
            document.open();


            /* create header */
            Table tableEmpPresence = getTableHeader();

            /* generate employee attendance report's data */
            Cell cellEmpData = new Cell("");  
            if(vEmpPresece!=null && vEmpPresece.size()>0)
            {
                int maxEmpPresence= vEmpPresece.size();
                for(int i=0; i<maxEmpPresence; i++)
                {
                    Vector vectTemp = (Vector)vEmpPresece.get(i);
                    
                    /* -------------- day & date period  --------------*/
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(0)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(1)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    
                    /* -------------- schedule --------------*/
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(2)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(3)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(4)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    
                    /* -------------- actual --------------*/
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(5)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(6)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(7)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    
                    /* -------------- difference --------------*/
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(8)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(9)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);                    
                    
                    if (!writer.fitsPage(tableEmpPresence)) 
                    {
                        tableEmpPresence.deleteLastRow();
                        i--;
                        document.add(tableEmpPresence);
                        document.newPage();
                        tableEmpPresence = getTableHeader();
                    }                                        
                }
            }            
            document.add(tableEmpPresence);
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
            de.printStackTrace();
        }

        /* closing the document */
        document.close();

        /* 
         * we have written the pdfstream to a ByteArrayOutputStream, now going to write this outputStream to the ServletOutputStream
	 * after we have set the contentlength
         */
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }


    /**
     * this method used to create header table
     */
    public static Table getTableHeader() throws BadElementException, DocumentException {

           Table tableEmpPresence = new Table(10);
           tableEmpPresence.setCellpadding(1);
           tableEmpPresence.setCellspacing(1);
           tableEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
           int widthHeader[] = {15, 10, 8, 8, 10, 8, 10, 10, 8, 8};
    	   tableEmpPresence.setWidths(widthHeader);
           tableEmpPresence.setWidth(100);

           Cell cellEmpPresence = new Cell(new Chunk(" DAY IN",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" DATE IN",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" SCHEDULE",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(3);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" ACTUAL",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(3);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" DIFFERENCE",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(2);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);


           cellEmpPresence = new Cell(new Chunk(" SYMBOL",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" TIME IN",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" TIME OUT",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);



           cellEmpPresence = new Cell(new Chunk(" TIME IN",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" DURATION",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" TIME OUT",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);



           cellEmpPresence = new Cell(new Chunk(" IN",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" OUT",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           return tableEmpPresence;
    }    
}
