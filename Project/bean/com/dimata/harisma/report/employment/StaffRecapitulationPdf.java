/* Generated by Together */
/*
 * EmpPresencePdf.java
 * @author gedhy
 * @version 1.0
 * Created on July 13, 2002, 09:10 PM
 */

package com.dimata.harisma.report.employment;

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

public class StaffRecapitulationPdf extends HttpServlet {

    /* declaration constant */
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);
    public static Color titleColor = new Color(200,200,200);
    public static Color summaryColor = new Color(240,240,240);
    public static String formatDate  = "MMM dd, yyyy";
    public static String formatNumber = "#,###";

    /* setting some fonts in the color chosen by the user */
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 12, Font.BOLD, blackColor);
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 10, Font.NORMAL, blackColor);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 10, Font.NORMAL, blackColor);

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
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

		/* creating an OutputStream */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            /* creating an instance of the writer */
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            /* get data from session */
            Date recapYear = new Date(FRMQueryString.requestInt(request, "recapYear"), 0, 1);
            Vector listStaffRecapitulation = new Vector(1, 1);
            SessEmployee sessEmployee = new SessEmployee();
            System.out.println("\trecapYear = " + recapYear);
            listStaffRecapitulation = sessEmployee.getStaffRecapitulation(recapYear);

            Vector vDept = (Vector) listStaffRecapitulation.get(0);
              Vector vJan = (Vector) listStaffRecapitulation.get(1);
              Vector vFeb = (Vector) listStaffRecapitulation.get(2);
              Vector vMar = (Vector) listStaffRecapitulation.get(3);
              Vector vApr = (Vector) listStaffRecapitulation.get(4);
              Vector vMay = (Vector) listStaffRecapitulation.get(5);
              Vector vJun = (Vector) listStaffRecapitulation.get(6);
              Vector vJul = (Vector) listStaffRecapitulation.get(7);
              Vector vAug = (Vector) listStaffRecapitulation.get(8);
              Vector vSep = (Vector) listStaffRecapitulation.get(9);
              Vector vOct = (Vector) listStaffRecapitulation.get(10);
              Vector vNov = (Vector) listStaffRecapitulation.get(11);
              Vector vDec = (Vector) listStaffRecapitulation.get(12);
              
            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase("SUMMARY OF STAFF RECAPITULATION\nYEAR " + (recapYear.getYear() + 1900), fontHeader), false);
            header.setAlignment(Element.ALIGN_CENTER);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);

            /* opening the document, needed for add something into document */
            document.open();

            /* create header */
            Table tableEmpPresence = getTableHeader();

            /* generate employee attendance report's data */
            Cell cellEmpData = new Cell("");

            for (int i=0; i<vDept.size() - 1; i ++) {
                
                cellEmpData = new Cell(new Chunk(String.valueOf(i+1),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                cellEmpData = new Cell(new Chunk(String.valueOf(vDept.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                cellEmpData = new Cell(new Chunk(String.valueOf(vJan.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(vFeb.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(vMar.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(vApr.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(vMay.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(vJun.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(vJul.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(vAug.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(vSep.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(vOct.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(vNov.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(vDec.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
            }

            cellEmpData = new Cell(new Chunk("",fontContent));
            cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellEmpData.setBackgroundColor(whiteColor);
            cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableEmpPresence.addCell(cellEmpData);

            cellEmpData = new Cell(new Chunk(" TOTAL PERMANENT STAFF",fontContent));
            cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellEmpData.setBackgroundColor(whiteColor);
            cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableEmpPresence.addCell(cellEmpData);

            cellEmpData = new Cell(new Chunk(String.valueOf(vJan.get(vDept.size()-1)),fontContent));
            cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpData.setBackgroundColor(whiteColor);
            cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableEmpPresence.addCell(cellEmpData);
            cellEmpData = new Cell(new Chunk(String.valueOf(vFeb.get(vDept.size()-1)),fontContent));
            cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpData.setBackgroundColor(whiteColor);
            cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableEmpPresence.addCell(cellEmpData);
            cellEmpData = new Cell(new Chunk(String.valueOf(vMar.get(vDept.size()-1)),fontContent));
            cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpData.setBackgroundColor(whiteColor);
            cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableEmpPresence.addCell(cellEmpData);
            cellEmpData = new Cell(new Chunk(String.valueOf(vApr.get(vDept.size()-1)),fontContent));
            cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpData.setBackgroundColor(whiteColor);
            cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableEmpPresence.addCell(cellEmpData);
            cellEmpData = new Cell(new Chunk(String.valueOf(vMay.get(vDept.size()-1)),fontContent));
            cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpData.setBackgroundColor(whiteColor);
            cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableEmpPresence.addCell(cellEmpData);
            cellEmpData = new Cell(new Chunk(String.valueOf(vJun.get(vDept.size()-1)),fontContent));
            cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpData.setBackgroundColor(whiteColor);
            cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableEmpPresence.addCell(cellEmpData);
            cellEmpData = new Cell(new Chunk(String.valueOf(vJul.get(vDept.size()-1)),fontContent));
            cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpData.setBackgroundColor(whiteColor);
            cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableEmpPresence.addCell(cellEmpData);
            cellEmpData = new Cell(new Chunk(String.valueOf(vAug.get(vDept.size()-1)),fontContent));
            cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpData.setBackgroundColor(whiteColor);
            cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableEmpPresence.addCell(cellEmpData);
            cellEmpData = new Cell(new Chunk(String.valueOf(vSep.get(vDept.size()-1)),fontContent));
            cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpData.setBackgroundColor(whiteColor);
            cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableEmpPresence.addCell(cellEmpData);
            cellEmpData = new Cell(new Chunk(String.valueOf(vOct.get(vDept.size()-1)),fontContent));
            cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpData.setBackgroundColor(whiteColor);
            cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableEmpPresence.addCell(cellEmpData);
            cellEmpData = new Cell(new Chunk(String.valueOf(vNov.get(vDept.size()-1)),fontContent));
            cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpData.setBackgroundColor(whiteColor);
            cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableEmpPresence.addCell(cellEmpData);
            cellEmpData = new Cell(new Chunk(String.valueOf(vDec.get(vDept.size()-1)),fontContent));
            cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpData.setBackgroundColor(whiteColor);
            cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableEmpPresence.addCell(cellEmpData);

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
    public static Table getTableHeader() throws BadElementException, DocumentException {

           Table tableEmpPresence = new Table(14);
           tableEmpPresence.setCellpadding(1);
           tableEmpPresence.setCellspacing(1);
           tableEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
	       int widthHeader[] = {5, 25, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
    	   tableEmpPresence.setWidths(widthHeader);
           tableEmpPresence.setWidth(100);

           Cell cellEmpPresence = new Cell(new Chunk(" NO.",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" DEPARTMENT",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" 1",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" 2",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" 3",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" 4",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" 5",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" 6",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" 7",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" 8",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" 9",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" 10",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" 11",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" 12",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);

                cellEmpPresence = new Cell(new Chunk(" PERMANENT STAFF",fontContent));
            cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpPresence.setColspan(12);
            cellEmpPresence.setRowspan(1);
            cellEmpPresence.setBackgroundColor(summaryColor);
          	tableEmpPresence.addCell(cellEmpPresence);

            return tableEmpPresence;
    }

}
