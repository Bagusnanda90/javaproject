/*
 * LeaveApplicationPdf.java
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

// package project
import com.dimata.harisma.entity.leave.LeaveApplicationDetail;
import com.dimata.harisma.entity.leave.PstLeaveApplicationDetail;
import com.dimata.harisma.entity.leave.LeaveApplicationDetailView;

/**
 *
 * @author  gedhy
 */
public class LeaveApplicationPdf extends HttpServlet {
    
    // declaration constant
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);    

    // setting some fonts in the color chosen by the user 
    public static Font fontHeader = new Font(Font.HELVETICA, 14, Font.BOLD, blackColor);    
    public static Font fontContentSmall = new Font(Font.HELVETICA, 8, Font.NORMAL, blackColor);
    public static Font fontContent = new Font(Font.HELVETICA, 10, Font.NORMAL, blackColor);
    public static Font fontContentItalic = new Font(Font.HELVETICA, 10, Font.ITALIC, blackColor);
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
            HttpSession sessLeaveApplication = request.getSession(true);
            LeaveApplicationReport objLeaveApplicationReport = new LeaveApplicationReport();
            try
            {
	        objLeaveApplicationReport = (LeaveApplicationReport) sessLeaveApplication.getValue("LEAVE_APPLICATION");
            }
            catch(Exception e)
            {
                System.out.println("Exc : "+e.toString());
            }
             
            // adding a Header of page, i.e. : title, align and etc
            String reportTitle = "LEAVE APPLICATION FORM";
            HeaderFooter header = new HeaderFooter(new Phrase(reportTitle, fontHeader), false);
            header.setAlignment(Element.ALIGN_CENTER);              
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);
             
            // opening the document
            document.open();             
            
            // add content into document
            document.add(getTableHeader(objLeaveApplicationReport));
            document.add(getTableContent(objLeaveApplicationReport));     
            document.add(getTableNote(objLeaveApplicationReport));                  
            document.add(getTableSignature(objLeaveApplicationReport));                
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
    public static Table getTableHeader(LeaveApplicationReport objLeaveApplicationReport) throws BadElementException, DocumentException {

            Table tableDpApplication = new Table(6);                      
            tableDpApplication.setCellpadding(1);
            tableDpApplication.setCellspacing(1);
            tableDpApplication.setBorderColor(whiteColor);
	    int widthHeader[] = {12, 3, 35, 12, 3, 35};
    	    tableDpApplication.setWidths(widthHeader);
            tableDpApplication.setWidth(100);

            // first line
            Cell cellDpApplication = new Cell(new Chunk("Date Of Application",fontContent));            
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

            cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getDateOfApplication(),fontContentBold));
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);


            // second line
            cellDpApplication = new Cell(new Chunk("Name",fontContent));            
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

            cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getApplicatName(),fontContentBold));
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            cellDpApplication = new Cell(new Chunk("Position",fontContent));
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

            cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getApplicantPosition(),fontContentBold));
            cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
            cellDpApplication.setBorderColor(whiteColor);
            cellDpApplication.setBackgroundColor(whiteColor);
            tableDpApplication.addCell(cellDpApplication);

            
            // third line
            cellDpApplication = new Cell(new Chunk("Employee No",fontContent));            
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

            cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getApplicantPayroll(),fontContentBold));
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

            cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getApplicantDepartment(),fontContentBold));
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
    public static Table getTableContent(LeaveApplicationReport objLeaveApplicationReport) throws BadElementException, DocumentException 
    {
            Table tableDpApplication = new Table(8);                      
            tableDpApplication.setCellpadding(1);
            tableDpApplication.setCellspacing(2);
            tableDpApplication.setBorderColor(whiteColor);
	    int widthHeader[] = {5, 39, 15, 11, 15, 3, 5, 7};
    	    tableDpApplication.setWidths(widthHeader);            
            tableDpApplication.setWidth(100);
        
            Vector listOfDetail = objLeaveApplicationReport.getVListOfDetailView();
            if(listOfDetail!=null && listOfDetail.size()>0)
            {
                
                // first line
                String strContentHeader = "Leave applied for : Please tick the box appropriate :";
                Cell cellDpApplication = new Cell(new Chunk(strContentHeader,fontContent));            
                cellDpApplication.setColspan(8);
                cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellDpApplication.setBorderColor(whiteColor);
                cellDpApplication.setBackgroundColor(whiteColor);
                tableDpApplication.addCell(cellDpApplication);
                
                int listOfDetailCount = listOfDetail.size();
                int intLeaveTaken = 0;
                for(int i=0; i<listOfDetailCount; i++)
                {
                    LeaveApplicationDetailView objLeaveApplicationDetail = (LeaveApplicationDetailView) listOfDetail.get(i);

                    // second line            
                    cellDpApplication = new Cell(new Chunk("",fontContent));            
                    cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellDpApplication.setBorderColor(whiteColor);
                    cellDpApplication.setBackgroundColor(whiteColor);
                    tableDpApplication.addCell(cellDpApplication);

                    //cellDpApplication = new Cell(new Chunk("Annual Leave from (date)",fontContent));            
                    String strLeaveTitle = PstLeaveApplicationDetail.strApplicationType[objLeaveApplicationDetail.getILeaveType()];
                    cellDpApplication = new Cell(new Chunk(strLeaveTitle+" from (date)",fontContent));            
                    cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellDpApplication.setBorderColor(whiteColor);
                    cellDpApplication.setBackgroundColor(whiteColor);
                    tableDpApplication.addCell(cellDpApplication);
                    
                    String strTakenDateFrom = objLeaveApplicationDetail.getDTakenDateFrom()!=null ? Formater.formatDate(objLeaveApplicationDetail.getDTakenDateFrom(),"MMM dd, yyyy") : "-";
                    cellDpApplication = new Cell(new Chunk(strTakenDateFrom,fontContent));            
                    cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellDpApplication.setBorderColor(whiteColor);
                    cellDpApplication.setBackgroundColor(whiteColor);
                    tableDpApplication.addCell(cellDpApplication);

                    cellDpApplication = new Cell(new Chunk("to",fontContent));            
                    cellDpApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellDpApplication.setBorderColor(whiteColor);
                    cellDpApplication.setBackgroundColor(whiteColor);
                    tableDpApplication.addCell(cellDpApplication);

                    String strTakenDateTo = objLeaveApplicationDetail.getDTakenDateTo()!=null ? Formater.formatDate(objLeaveApplicationDetail.getDTakenDateTo(),"MMM dd, yyyy") : "-";                    
                    cellDpApplication = new Cell(new Chunk(strTakenDateTo,fontContent));            
                    cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellDpApplication.setBorderColor(whiteColor);
                    cellDpApplication.setBackgroundColor(whiteColor);
                    tableDpApplication.addCell(cellDpApplication);

                    cellDpApplication = new Cell(new Chunk("=",fontContent));            
                    cellDpApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellDpApplication.setBorderColor(whiteColor);
                    cellDpApplication.setBackgroundColor(whiteColor);
                    tableDpApplication.addCell(cellDpApplication);

                    
                    int leaveTakenAmount = 0;
                    if(objLeaveApplicationDetail.getDTakenDateFrom()!=null &&objLeaveApplicationDetail.getDTakenDateTo()!=null)
                    {                    
                        leaveTakenAmount = (int) (DateCalc.dayDifference(objLeaveApplicationDetail.getDTakenDateFrom(), objLeaveApplicationDetail.getDTakenDateTo())+1);
                    }		                    
                    intLeaveTaken = intLeaveTaken + leaveTakenAmount;
                    
                    cellDpApplication = new Cell(new Chunk(String.valueOf(leaveTakenAmount),fontContent));            
                    cellDpApplication.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellDpApplication.setBorderColor(whiteColor);
                    cellDpApplication.setBackgroundColor(whiteColor);
                    tableDpApplication.addCell(cellDpApplication);            

                    cellDpApplication = new Cell(new Chunk("day(s)",fontContentItalic));            
                    cellDpApplication.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellDpApplication.setBorderColor(whiteColor);
                    cellDpApplication.setBackgroundColor(whiteColor);
                    tableDpApplication.addCell(cellDpApplication);
                }
                
                // eigth line            
                cellDpApplication = new Cell(new Chunk("",fontContent));    
                cellDpApplication.setColspan(4);
                cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
                cellDpApplication.setBorder(Rectangle.TOP); 
                cellDpApplication.setBackgroundColor(whiteColor);
                tableDpApplication.addCell(cellDpApplication);

                cellDpApplication = new Cell(new Chunk("Total Leave",fontContentItalic));                
                cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
                cellDpApplication.setBorder(Rectangle.TOP); 
                cellDpApplication.setBackgroundColor(whiteColor);
                tableDpApplication.addCell(cellDpApplication);

                cellDpApplication = new Cell(new Chunk("=",fontContent));            
                cellDpApplication.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
                cellDpApplication.setBorder(Rectangle.TOP); 
                cellDpApplication.setBackgroundColor(whiteColor);
                tableDpApplication.addCell(cellDpApplication);

                cellDpApplication = new Cell(new Chunk(String.valueOf(intLeaveTaken),fontContent));            
                cellDpApplication.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
                cellDpApplication.setBorder(Rectangle.TOP); 
                cellDpApplication.setBackgroundColor(whiteColor);
                tableDpApplication.addCell(cellDpApplication);            

                cellDpApplication = new Cell(new Chunk("day(s)",fontContentItalic));            
                cellDpApplication.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);            
                cellDpApplication.setBorder(Rectangle.TOP); 
                cellDpApplication.setBackgroundColor(whiteColor);
                tableDpApplication.addCell(cellDpApplication);


                // blank line            
                cellDpApplication = new Cell(new Chunk("",fontContent));            
                cellDpApplication.setColspan(8);
                cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellDpApplication.setBorderColor(whiteColor);
                cellDpApplication.setBackgroundColor(whiteColor);
                tableDpApplication.addCell(cellDpApplication);                
            }
        
            return tableDpApplication;
    }
    
    
    /**
    * this method used to create report dp taken approved
    */    
    public static Table getTableNote(LeaveApplicationReport objLeaveApplicationReport) throws BadElementException, DocumentException 
    {
        Table tableDpApplication = new Table(6);                      
        tableDpApplication.setBorderWidth(0);
        tableDpApplication.setCellpadding(1);
        tableDpApplication.setCellspacing(0);         
        tableDpApplication.setBorderColor(whiteColor);
        int widthHeader[] = {47, 17, 2, 12, 12, 10};
        tableDpApplication.setWidths(widthHeader);  
        tableDpApplication.setWidth(100);
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);                                    
        tableDpApplication.setDefaultCellBackgroundColor(whiteColor);        
        
        
        // first line  
        Cell cellDpApplication = new Cell(new Chunk("Reason",fontContent));                                
        cellDpApplication.setBorder(Rectangle.TOP);             
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk("",fontContent));                                
        cellDpApplication.setColspan(2);
        cellDpApplication.setBorder(Rectangle.LEFT | Rectangle.TOP);                          
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);
        
        cellDpApplication = new Cell(new Chunk("Leave Statistic",fontContent)); 
        cellDpApplication.setColspan(2);
        cellDpApplication.setBorder(Rectangle.TOP);                          
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk("",fontContent));                                        
        cellDpApplication.setBorder(Rectangle.TOP);                          
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);
        
        
        // second line
        cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getLeaveReason(),fontContentSmall));                                        
        cellDpApplication.setBorder(Rectangle.RIGHT); 
        cellDpApplication.setRowspan(6);
        tableDpApplication.setDefaultHorizontalAlignment(Table.LEFT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_TOP);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk("",fontContentSmall));                                
        cellDpApplication.setColspan(2);
        cellDpApplication.setBorder(Rectangle.LEFT | Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);
        
        cellDpApplication = new Cell(new Chunk("Annual Leave",fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk("Long Leave",fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk("",fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        // third line
        cellDpApplication = new Cell(new Chunk("Total Entitle",fontContentSmall));                                        
        cellDpApplication.setBorder(Rectangle.LEFT | Rectangle.TOP);         
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk(":",fontContentSmall));                                        
        cellDpApplication.setBorder(Rectangle.TOP);         
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getAlEntitle(),fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP);         
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getLlEntitle(),fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP);         
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk("day(s)",fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP);         
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);        

        
        // forth line
        cellDpApplication = new Cell(new Chunk("Has been taken",fontContentSmall));                                        
        cellDpApplication.setBorder(Rectangle.LEFT | Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);
        
        cellDpApplication = new Cell(new Chunk(":",fontContentSmall));                                        
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getAlHasBeenTaken(),fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getLlHasBeenTaken(),fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk("day(s)",fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);


        
        // fifth line
        cellDpApplication = new Cell(new Chunk("Sub total",fontContentSmall));                                        
        cellDpApplication.setBorder(Rectangle.LEFT | Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);
        
        cellDpApplication = new Cell(new Chunk(":",fontContentSmall));                                        
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getAlSubTotal(),fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getLlSubTotal(),fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk("day(s)",fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);
        
        
        // sixth line
        cellDpApplication = new Cell(new Chunk("To be taken",fontContentSmall));                                        
        cellDpApplication.setBorder(Rectangle.LEFT | Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);
        
        cellDpApplication = new Cell(new Chunk(":",fontContentSmall));                                        
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getAlToBeTaken(),fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getLlToBeTaken(),fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk("day(s)",fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);


        // seventh line
        cellDpApplication = new Cell(new Chunk("Balance",fontContentSmall));                                        
        cellDpApplication.setBorder(Rectangle.LEFT | Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);
        
        cellDpApplication = new Cell(new Chunk(":",fontContentSmall));                                        
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getAlBalance(),fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getLlBalance(),fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk("day(s)",fontContentSmall));         
        cellDpApplication.setBorder(Rectangle.TOP); 
        tableDpApplication.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
        tableDpApplication.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
        tableDpApplication.addCell(cellDpApplication);
        
        // blank line
        cellDpApplication = new Cell(new Chunk("",fontContentSmall)); 
        cellDpApplication.setColspan(6);
        cellDpApplication.setBorder(Rectangle.TOP);                          
        cellDpApplication.setBackgroundColor(whiteColor);
        tableDpApplication.addCell(cellDpApplication);        

        return tableDpApplication;  
    }

    
    /**
    * this method used to create report signature
    */    
    public static Table getTableSignature(LeaveApplicationReport objLeaveApplicationReport) throws BadElementException, DocumentException 
    {
        Table tableDpApplication = new Table(3);                      
        tableDpApplication.setCellpadding(1);
        tableDpApplication.setCellspacing(1);
        tableDpApplication.setBorderColor(whiteColor);	        	    
        int widthHeader[] = {33, 34, 33};
        tableDpApplication.setWidths(widthHeader);
        tableDpApplication.setWidth(100);

        // first line
        Cell cellDpApplication = new Cell(new Chunk("Requested by,",fontContent));                        
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

        cellDpApplication = new Cell(new Chunk("",fontContent));                        
        cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
        cellDpApplication.setBorderColor(whiteColor);
        cellDpApplication.setBackgroundColor(whiteColor);
        tableDpApplication.addCell(cellDpApplication);            

        
        // forth line
        cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getApplicatName(),fontContentBold));                        
        cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
        cellDpApplication.setBorderColor(whiteColor);
        cellDpApplication.setBackgroundColor(whiteColor);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getDepHeadApproval(),fontContentBold));                        
        cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);  
        cellDpApplication.setBorderColor(whiteColor);
        cellDpApplication.setBackgroundColor(whiteColor);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk(objLeaveApplicationReport.getHrManApproval(),fontContentBold));                        
        cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);  
        cellDpApplication.setBorderColor(whiteColor);
        cellDpApplication.setBackgroundColor(whiteColor);
        tableDpApplication.addCell(cellDpApplication);

        // fifth line
        cellDpApplication = new Cell(new Chunk("Employee",fontContent));                        
        cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
        cellDpApplication.setBorderColor(whiteColor);
        cellDpApplication.setBackgroundColor(whiteColor);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk("Dept/Division Head",fontContent));                        
        cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);  
        cellDpApplication.setBorderColor(whiteColor);
        cellDpApplication.setBackgroundColor(whiteColor);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk("HR Manager",fontContent));                        
        cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);  
        cellDpApplication.setBorderColor(whiteColor);
        cellDpApplication.setBackgroundColor(whiteColor);
        tableDpApplication.addCell(cellDpApplication);


        // fifth line
        cellDpApplication = new Cell(new Chunk("Date : " + objLeaveApplicationReport.getDateRequestApproval(),fontContent));                        
        cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                            
        cellDpApplication.setBorderColor(whiteColor);
        cellDpApplication.setBackgroundColor(whiteColor);
        tableDpApplication.addCell(cellDpApplication);

        cellDpApplication = new Cell(new Chunk("Date : " + objLeaveApplicationReport.getDateDeptHeadApproval(),fontContent));                        
        cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);  
        cellDpApplication.setBorderColor(whiteColor);
        cellDpApplication.setBackgroundColor(whiteColor);
        tableDpApplication.addCell(cellDpApplication);  

        cellDpApplication = new Cell(new Chunk("Date : " + objLeaveApplicationReport.getDateHrManApproval(),fontContent));                        
        cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);  
        cellDpApplication.setBorderColor(whiteColor);
        cellDpApplication.setBackgroundColor(whiteColor);
        tableDpApplication.addCell(cellDpApplication);

        
        // blank line
        cellDpApplication = new Cell(new Chunk("",fontContent));      
        cellDpApplication.setColspan(3);
        cellDpApplication.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cellDpApplication.setVerticalAlignment(Cell.ALIGN_MIDDLE);                
        cellDpApplication.setBorderColor(whiteColor);
        cellDpApplication.setBackgroundColor(whiteColor);
        tableDpApplication.addCell(cellDpApplication);

        return tableDpApplication;  
    }        
}
