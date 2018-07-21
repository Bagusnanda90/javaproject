/*
 * DpApplicationPdf.java
 *
 * Created on January 12, 2005, 12:02 PM
 */

package com.dimata.harisma.report.leave;

// package java
import java.util.*;
import java.text.*;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

// package servlet
import javax.servlet.*;
import javax.servlet.http.*;

// package lowagie
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

// package qdep
import com.dimata.util.*;
import com.dimata.qdep.form.*;

/**
 *
 * @author  gedhy
 */
public class DpApplicationPdf extends HttpServlet {
    
    // declaration constant
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);    

    // setting some fonts in the color chosen by the user 
    public static Font fontHeader = new Font(Font.HELVETICA, 12, Font.BOLD, blackColor);    
    public static Font fontContent = new Font(Font.HELVETICA, 10, Font.NORMAL, blackColor);
    public static Font fontContentBold = new Font(Font.HELVETICA, 10, Font.BOLD, blackColor);

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


        // creating the document object
        Document document = new Document(PageSize.A4, 30, 30, 70, 50);

        // creating an OutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            // creating an instance of the writer
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            // get data from session 
            HttpSession sessDpApplication = request.getSession(true);
            DpApplicationReport objDpApplicationReport = new DpApplicationReport();
            try
            {
	        objDpApplicationReport = (DpApplicationReport) sessDpApplication.getValue("DP_APPLICATION");
            }
            catch(Exception e)
            {
                System.out.println("Exc : "+e.toString());
            }
             
            // adding a Header of page, i.e. : title, align and etc
            String reportTitle = "DAY-OFF APPLICATION IN LIEU OF WORKING\nON PUBLIC HOLIDAY/WEEKLY DAY-OFF";
            HeaderFooter header = new HeaderFooter(new Phrase(reportTitle, fontHeader), false);
            header.setAlignment(Element.ALIGN_CENTER);              
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);
             
            // opening the document
            document.open();            
            
            // add content into document
            document.add(getTableHeader(objDpApplicationReport));
            document.add(getTableContent(objDpApplicationReport));     
            document.add(getTableSignature(objDpApplicationReport));    
            document.add(getTableApproval(objDpApplicationReport));   
            document.add(getTableTAC()); 
        }
        catch(DocumentException de) 
        {
            System.err.println(de.getMessage());
            de.printStackTrace();
        }

        // closing the document 
        document.close();

        // we have written the pdfstream to a ByteArrayOutputStream, now going to write this outputStream to the ServletOutputStream
	// after we have set the contentlength        
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }    
    
    
    /**
    * this method used to create header main
    */
    public static Table getTableHeader(DpApplicationReport objDpApplicationReport) throws BadElementException, DocumentException {

            Table tableDpApplication = new Table(6);                      
            tableDpApplication.setCellpadding(1);
            tableDpApplication.setCellspacing(1);
            tableDpApplication.setBorderColor(whiteColor);
	    int widthHeader[] = {7, 3, 40, 12, 3, 35};
    	    tableDpApplication.setWidths(widthHeader);
            tableDpApplication.setWidth(100);

            // first line
            Cell cellDpApplication = new Cell(new Chunk("Date Of Submission",fontContent));            
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_RIGHT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setColspan(4);
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk(":",fontContent));
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk(objDpApplicationReport.getSubmissionDate(),fontContentBold));
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);


            // second line
            cellDpApplication = new Cell(new Chunk("To",fontContent));            
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk(":",fontContent));
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk(objDpApplicationReport.getDestName(),fontContentBold));
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk("Department",fontContent));
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_RIGHT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk(":",fontContent));
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk(objDpApplicationReport.getDestDepartment(),fontContentBold));
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            
            // third line
            cellDpApplication = new Cell(new Chunk("From",fontContent));            
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk(":",fontContent));
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk(objDpApplicationReport.getApplicantName(),fontContentBold));
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk("Department",fontContent));
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_RIGHT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk(":",fontContent));
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk(objDpApplicationReport.getApplicantDepartment(),fontContentBold));
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);
            
            // blank line
            cellDpApplication = new Cell(new Chunk("",fontContent));            
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_RIGHT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setColspan(6);
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);
            
            return tableDpApplication;
    }

    
    /**
    * this method used to create report content
    */
    public static Table getTableContent(DpApplicationReport objDpApplicationReport) throws BadElementException, DocumentException {

            Table tableDpApplication = new Table(1);                      
            tableDpApplication.setCellpadding(1);
            tableDpApplication.setCellspacing(1);
            tableDpApplication.setBorderColor(whiteColor);	        	    
            tableDpApplication.setWidth(100);

            // content line
            String strContentHeader = "I shall be (had been) required to work on my weekly off-day/Public Holiday on (day & date)";
            Cell cellDpApplication = new Cell(new Chunk(strContentHeader,fontContent));            
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);
            
            cellDpApplication = new Cell(new Chunk("   "+objDpApplicationReport.getDpOwningDate(),fontContentBold));            
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellDpApplication.setBorder(Rectangle.BOTTOM);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk("(Please specify your reason/s) :",fontContent));            
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk("   "+objDpApplicationReport.getDpNotes(),fontContent));            
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellDpApplication.setBorder(Rectangle.BOTTOM);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            for(int i=0; i<3; i++)  
            {
                cellDpApplication = new Cell(new Chunk("   -",fontContent));            
                cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellDpApplication.setBorder(Rectangle.BOTTOM);
                cellDpApplication.setBackgroundColor(whiteColor);
                tableDpApplication.addCell(cellDpApplication);
            }

            // blank line            
            cellDpApplication = new Cell(new Chunk("",fontContent));            
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);
            
            return tableDpApplication;
    }

    
    /**
    * this method used to create report signature
    */
    public static Table getTableSignature(DpApplicationReport objDpApplicationReport) throws BadElementException, DocumentException {

            Table tableDpApplication = new Table(2);                      
            tableDpApplication.setCellpadding(1);
            tableDpApplication.setCellspacing(1);
            tableDpApplication.setBorderColor(whiteColor);	        	    
            int widthHeader[] = {50, 50};
    	    tableDpApplication.setWidths(widthHeader);
            tableDpApplication.setWidth(100);

            // first line
            Cell cellDpApplication = new Cell(new Chunk("Sign,",fontContent));                        
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk("Approved by,",fontContent));                        
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);
            

            // second line
            cellDpApplication = new Cell(new Chunk("",fontContent));                        
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk("",fontContent));                        
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);


            // third line
            cellDpApplication = new Cell(new Chunk("",fontContent));                        
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk("",fontContent));                        
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);            
            
            // forth line
            cellDpApplication = new Cell(new Chunk(objDpApplicationReport.getApplicantName(),fontContentBold));                        
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk(objDpApplicationReport.getDpApprovedName(),fontContentBold));                        
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);  
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            // blank line
            cellDpApplication = new Cell(new Chunk("",fontContent));      
            cellDpApplication.setColspan(2);
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);
            
            return tableDpApplication;  
    }
    
    /**
    * this method used to create report dp taken approved
    */
    public static Table getTableApproval(DpApplicationReport objDpApplicationReport) throws BadElementException, DocumentException {

            Table tableDpApplication = new Table(2);                      
            tableDpApplication.setCellpadding(1);
            tableDpApplication.setCellspacing(1); 
            tableDpApplication.setBorderColor(whiteColor);
            int widthHeader[] = {60, 40};
    	    tableDpApplication.setWidths(widthHeader);
            tableDpApplication.setWidth(100);

            // first line
            Cell cellDpApplication = new Cell(new Chunk("My day-off in lieu of above will be taken on",fontContent));                        
            cellDpApplication.setColspan(2);
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
            cellDpApplication.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT);             
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            // second line
            cellDpApplication = new Cell(new Chunk("   "+objDpApplicationReport.getDpTakenDate(),fontContentBold));                        
            cellDpApplication.setColspan(2);
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
            cellDpApplication.setBorder(Rectangle.LEFT | Rectangle.RIGHT);                         
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);            

            // third line
            cellDpApplication = new Cell(new Chunk("",fontContent));                        
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
            cellDpApplication.setBorder(Rectangle.LEFT | Rectangle.RIGHT);                         
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk("Acknowledge by,",fontContent));                          
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
            cellDpApplication.setBorder(Rectangle.RIGHT | Rectangle.RIGHT);                         
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);


            // forth line
            cellDpApplication = new Cell(new Chunk("",fontContent));   
            cellDpApplication.setColspan(2);
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
            cellDpApplication.setBorder(Rectangle.LEFT | Rectangle.RIGHT);                         
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            // sixth line
            cellDpApplication = new Cell(new Chunk("",fontContent));                        
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
            cellDpApplication.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);                         
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk(objDpApplicationReport.getDpTakenApproved(),fontContent));                        
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
            cellDpApplication.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);                         
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            // blank line
            cellDpApplication = new Cell(new Chunk("",fontContent));      
            cellDpApplication.setColspan(2);
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                
            cellDpApplication.setBorder(Rectangle.TOP);                          
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);
            
            return tableDpApplication;  
    }
    
    
    /**
    * this method used to create report signature
    */
    public static Table getTableTAC() throws BadElementException, DocumentException {

            Table tableDpApplication = new Table(1);                      
            tableDpApplication.setCellpadding(1);
            tableDpApplication.setCellspacing(1);
            tableDpApplication.setBorderColor(whiteColor);	        	    
            tableDpApplication.setWidth(100);

            String[] strTac = 
            {                
                "    1. Authorization by Department/Division Heads",
                "    2. Submission of application at least 3 days prior to \"day off work\" with specific reason/s given",
                "    3. Onus is application to keep approved from",
                "    4. Application for \"off in lieu\" to be made and submitted on original approved from only",
                "    5. \"Off-in lieu\" to be cleared within three month otherwise it will be forfeited thereafter unless prior",
                "        approval for extension has been obtained"
            };
            
            // first line
            Cell cellDpApplication = new Cell(new Chunk("Terms and Conditions :",fontContentBold));                                                
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);                
            tableDpApplication.addCell(cellDpApplication);            
            
            int maxStrTac = strTac.length;
            for(int i=0; i<maxStrTac; i++)
            {
                cellDpApplication = new Cell(new Chunk(strTac[i],fontContent));                        
                cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
                cellDpApplication.setBorderColor(whiteColor);
                cellDpApplication.setBackgroundColor(whiteColor);                
                tableDpApplication.addCell(cellDpApplication);
            }

            // blank line
            cellDpApplication = new Cell(new Chunk("",fontContent));                        
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);                
            tableDpApplication.addCell(cellDpApplication);
            
            return tableDpApplication;
    }
    
}
