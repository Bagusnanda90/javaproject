/* Generated by Together */
/*
 * EmpPresencePdf.java
 * @author gedhy
 * @version 1.0
 * Created on July 13, 2002, 09:10 PM
 */

package com.dimata.harisma.report.recognition;

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
import com.dimata.harisma.session.employee.*;

public class RecQuarterlyPdf extends HttpServlet {

    /* declaration constant */
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);
    public static Color titleColor = new Color(200,200,200);
    public static Color summaryColor = new Color(240,240,240);
    public static String formatDate  = "MMM dd, yyyy";
    public static String formatNumber = "#,###";

    /* setting some fonts in the color chosen by the user */
    public static Font fontHeader = new Font(Font.HELVETICA, 10, Font.BOLD, blackColor);
    public static Font fontTitle = new Font(Font.HELVETICA, 8, Font.NORMAL, blackColor);
    public static Font fontContent = new Font(Font.HELVETICA, 9, Font.NORMAL, blackColor);

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

        /* creating the document object */
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);

		/* creating an OutputStream */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            /* creating an instance of the writer */
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            /* get data from session */
            int year = FRMQueryString.requestInt(request, "year");

            if (year == 0) {
                year = new Date().getYear() + 1900;
            }

            String month[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

            Calendar dateStartYearly = new GregorianCalendar(year,0,21);
            Calendar dateEndYearly = new GregorianCalendar(year+1,0,20);

            Vector vResult = new Vector(1, 1);
            Vector vQ1 = new Vector(1, 1);
            Vector vQ2 = new Vector(1, 1);
            Vector vQ3 = new Vector(1, 1);

            Vector vEmpId = new Vector(1, 1);
            Vector vEmpNum = new Vector(1, 1);
            Vector vEmp = new Vector(1, 1);
            Vector vPoint = new Vector(1, 1);
            Vector vName = new Vector(1, 1);
            Vector vDep = new Vector(1, 1);
            Vector vPos = new Vector(1, 1);

            vResult = SessRecognition.getPointQuarterly(year);
            vEmp = (Vector) vResult.get(0);
            vPoint = (Vector) vResult.get(1);
            vName = (Vector) vResult.get(2);
            vDep = (Vector) vResult.get(3);
            vPos = (Vector) vResult.get(4);
            vEmpId = (Vector) vResult.get(5);
            vEmpNum = (Vector) vResult.get(6);

            vQ1 = (Vector) vResult.get(7);
            Vector vQ1_EmpId = new Vector(1, 1);
            Vector vQ1_Point = new Vector(1, 1);
            vQ1_EmpId = (Vector) vQ1.get(0);
            vQ1_Point = (Vector) vQ1.get(1);

            vQ2 = (Vector) vResult.get(8);
            Vector vQ2_EmpId = new Vector(1, 1);
            Vector vQ2_Point = new Vector(1, 1);
            vQ2_EmpId = (Vector) vQ2.get(0);
            vQ2_Point = (Vector) vQ2.get(1);

            vQ3 = (Vector) vResult.get(9);
            Vector vQ3_EmpId = new Vector(1, 1);
            Vector vQ3_Point = new Vector(1, 1);
            vQ3_EmpId = (Vector) vQ3.get(0);
            vQ3_Point = (Vector) vQ3.get(1);
              
            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase("Yearly Recapitulation " + year, fontHeader), false);
            header.setAlignment(Element.ALIGN_CENTER);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);

            /* opening the document, needed for add something into document */
            document.open();

            /* create header */
            Table tableEmpPresence = getTableHeader(year);

            /* generate employee attendance report's data */
            Cell cellEmpData = new Cell("");

            for (int i=0; i<vEmp.size(); i++) { 
                
                cellEmpData = new Cell(new Chunk(String.valueOf(i+1),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                cellEmpData = new Cell(new Chunk(String.valueOf(vEmpNum.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                cellEmpData = new Cell(new Chunk(String.valueOf(vEmp.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                cellEmpData = new Cell(new Chunk(String.valueOf(vDep.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    /*
                cellEmpData = new Cell(new Chunk(String.valueOf(vPos.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                     */

                String tmp = "";
                for (int a = 0; a < vQ1_EmpId.size(); a++) {
                    if (Long.parseLong(String.valueOf(vEmpId.get(i))) == Long.parseLong(String.valueOf(vQ1_EmpId.get(a)))) {
                        tmp = String.valueOf(vQ1_Point.get(a));
                    }
                }
                cellEmpData = new Cell(new Chunk(tmp,fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                tmp = "";
                for (int a = 0; a < vQ2_EmpId.size(); a++) {
                    if (Long.parseLong(String.valueOf(vEmpId.get(i))) == Long.parseLong(String.valueOf(vQ2_EmpId.get(a)))) {
                        tmp = String.valueOf(vQ2_Point.get(a));
                    }
                }
                cellEmpData = new Cell(new Chunk(tmp,fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                tmp = "";
                for (int a = 0; a < vQ3_EmpId.size(); a++) {
                    if (Long.parseLong(String.valueOf(vEmpId.get(i))) == Long.parseLong(String.valueOf(vQ3_EmpId.get(a)))) {
                        tmp = String.valueOf(vQ3_Point.get(a));
                    }
                }
                cellEmpData = new Cell(new Chunk(tmp,fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                cellEmpData = new Cell(new Chunk(String.valueOf(vPoint.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
            }
            document.add(tableEmpPresence);
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
            de.printStackTrace();
        }

        /* closing the document */
        document.close();

        /* we have written the pdfstream to a ByteArrayOutputStream, now going to write this outputStream to the ServletOutputStream
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
    public static Table getTableHeader(int year) throws BadElementException, DocumentException {
            String month[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

           Table tableEmpPresence = new Table(8);
           tableEmpPresence.setCellpadding(1);
           tableEmpPresence.setCellspacing(1);
           tableEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
	   
           //int widthHeader[] = {3, 6, 18, 12, 18, 4, 4, 4, 4, 4};
           int widthHeader[] = {3, 6, 18, 12, 5, 5, 5, 4};
    	   tableEmpPresence.setWidths(widthHeader);
           tableEmpPresence.setWidth(100);

           Cell cellEmpPresence = new Cell(new Chunk(" No",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" Payroll",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
                
           cellEmpPresence = new Cell(new Chunk(" Name",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
                
           cellEmpPresence = new Cell(new Chunk(" Department",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           /*     
           cellEmpPresence = new Cell(new Chunk(" Position",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
             */   
           cellEmpPresence = new Cell(new Chunk(" Points accumulated " + year,fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setColspan(3);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
                
           cellEmpPresence = new Cell(new Chunk(" Total",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(month[11] + "-" + month[3],fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
                
           cellEmpPresence = new Cell(new Chunk(month[3] + "-" + month[7],fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
                
           cellEmpPresence = new Cell(new Chunk(month[7] + "-" + month[11],fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
                
           return tableEmpPresence;
    }

}
