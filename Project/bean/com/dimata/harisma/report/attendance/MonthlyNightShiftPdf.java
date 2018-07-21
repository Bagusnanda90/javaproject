/*
 * MonthlyNightShiftPdf.java
 *
 * Created on June 4, 2004, 10:36 AM
 */

package com.dimata.harisma.report.attendance;

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
public class MonthlyNightShiftPdf  extends HttpServlet {
    
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
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 8, Font.NORMAL, blackColor);

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
        Document document = new Document(PageSize.A4.rotate(), 30, 30, 50, 50);

        /* creating an OutputStream */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            /* creating an instance of the writer */
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            /* get data from session */
            HttpSession sessEmpPresence = request.getSession(true);
            Vector vctMonthlyPresence = null;
            try{
	        vctMonthlyPresence = (Vector)sessEmpPresence.getValue("NIGHT_SHIFT_MONTHLY");
            }catch(Exception e){
                System.out.println("Exc : "+e.toString());
            }

            //Date presence = new Date();
            String presence = "";
            Department department = new Department();
            Vector listPresence = new Vector(1,1);
            int intDateOfMonth = 0;
            int startDatePeriod = 0;
            if(vctMonthlyPresence != null && vctMonthlyPresence.size()==5){
                try{
	            	presence = (String) vctMonthlyPresence.get(0);
	                long oidDepartment = Long.parseLong((String)vctMonthlyPresence.get(1));
	                department = (Department)PstDepartment.fetchExc(oidDepartment);
	                listPresence = (Vector)vctMonthlyPresence.get(2);
                        intDateOfMonth = Integer.parseInt(String.valueOf(vctMonthlyPresence.get(3)));
                        startDatePeriod = Integer.parseInt(String.valueOf(vctMonthlyPresence.get(4)));

                }
                catch(Exception e)
                {
                 	System.out.println("exc on get List vctMonthlyPresence : "+e.toString());
                }
            }               
            
            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase("NIGHT SHIFT REPORT PER " + presence.toUpperCase() +
                				  "\nDEPARTMENT : "+ department.getDepartment().toUpperCase(), fontHeader), false);

            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);
             
            /* opening the document, needed for add something into document */
            document.open();

            /* create header */            
            Table tableEmpPresence = createHeaderDetailDinamis(intDateOfMonth, startDatePeriod, fontContent);

            /* generate employee attendance report's data */
            Cell cellEmpData = new Cell("");
             
            if(listPresence!=null && listPresence.size()>0)
            {
                for (int i = 0; i < listPresence.size(); i++) 
                {          
                    Vector itemNightShift = (Vector)listPresence.get(i);

                    // no, payroll and name
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemNightShift.get(0)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(itemNightShift.get(1)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(itemNightShift.get(2)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);          
                        
                    // list of schedule and duration, dimulai dari index ke 3
                    for(int isch=3; isch<itemNightShift.size()-1; isch++)
                    {
                        cellEmpData = new Cell(new Chunk(String.valueOf(itemNightShift.get(isch)),fontContent));
                        cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                        cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellEmpData.setBackgroundColor(whiteColor);
                        cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableEmpPresence.addCell(cellEmpData);          
                    }

                    // total
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemNightShift.get(itemNightShift.size()-1)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);                                
  
                    if (!writer.fitsPage(tableEmpPresence)) {
                        tableEmpPresence.deleteLastRow();
                        i--;
                        document.add(tableEmpPresence);
                        document.newPage();                        
                        tableEmpPresence = createHeaderDetailDinamis(intDateOfMonth,startDatePeriod, fontContent);
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
     * create header of report
     * @param intDateOfMonth
     * @param font1
     * @return
     * @created by Edhy
     */    
    public static Table createHeaderDetail(int intDateOfMonth, Font font1)
    {        
        Table datatable = null;  
        try
        {                        
            int maxColumn = 4 + intDateOfMonth;                        
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 2;
            headerwidths[1] = 5;
            headerwidths[2] = 14;
            
            int index = 3;                        
            for(int j=0; j<intDateOfMonth; j++)
            {                    
                headerwidths[index] = 2.5f;  
                index++;                    
            }
            headerwidths[index] = 3;
            

            // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(1);
            datatable.setSpacing(1);
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultRowspan(2);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.setBackgroundColor(summaryColor);                
            
            // data no urut, payroll number and employee
            datatable.addCell(new Phrase("No", font1));
            datatable.addCell(new Phrase("Payroll", font1));
            datatable.addCell(new Phrase("Employee", font1));
            
            
            datatable.setDefaultColspan(intDateOfMonth);
            datatable.setDefaultRowspan(1);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Duration (hours, minutes)", font1));                        
            
            // data total night shift
            datatable.setDefaultRowspan(2);
            datatable.setDefaultColspan(1);
            datatable.setBackgroundColor(summaryColor);                
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.addCell(new Phrase("Total", font1));            

            // detail data every date of month
            for(int itDetail=0; itDetail<intDateOfMonth; itDetail++)
            {
                datatable.setDefaultColspan(1);
                datatable.setDefaultRowspan(1);
                datatable.setBackgroundColor(summaryColor);                
                datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
                datatable.addCell(new Phrase(""+(itDetail+1), font1));                
            }

            // this is the end of the table header
            datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on createHeaderDetail : "+e.toString());
        }
        return datatable;
    }
    
    /** 
     * create header of report ( untuk periode dinamis)
     * @param intDateOfMonth
     * @param font1
     * @param startDatePeriod
     * @return
     * @created by Yunny
     */    
    public static Table createHeaderDetailDinamis(int intDateOfMonth, int startDatePeriod, Font font1)
    {        
        Table datatable = null;  
        try
        {                        
            int maxColumn = 4 + intDateOfMonth;                        
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 2;
            headerwidths[1] = 5;
            headerwidths[2] = 14;
            
            int index = 3;                        
            for(int j=0; j<intDateOfMonth; j++)
            {                    
                headerwidths[index] = 2.5f;  
                index++;                    
            }
            headerwidths[index] = 3;
            

            // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(1);
            datatable.setSpacing(1);
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultRowspan(2);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.setBackgroundColor(summaryColor);                
            
            // data no urut, payroll number and employee
            datatable.addCell(new Phrase("No", font1));
            datatable.addCell(new Phrase("Payroll", font1));
            datatable.addCell(new Phrase("Employee", font1));
            
            
            datatable.setDefaultColspan(intDateOfMonth);
            datatable.setDefaultRowspan(1);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Sickness Period", font1));                        
            
            // data total night shift
            datatable.setDefaultRowspan(2);
            datatable.setDefaultColspan(1);
            datatable.setBackgroundColor(summaryColor);                
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.addCell(new Phrase("Total", font1));            

            // detail data every date of month
            
             // untuk tanggal pertama dalam period yang dipilih
            datatable.setDefaultColspan(1);
            datatable.setDefaultRowspan(1);
            datatable.setBackgroundColor(summaryColor);                
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase(""+(startDatePeriod), font1)); 
            
            for(int itDetail=0; itDetail<intDateOfMonth-1; itDetail++)
            {
                if(startDatePeriod==intDateOfMonth){
                    startDatePeriod = 1;
                    datatable.setDefaultColspan(1);
                    datatable.setDefaultRowspan(1);
                    datatable.setBackgroundColor(summaryColor);                
                    datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
                    datatable.addCell(new Phrase(""+(startDatePeriod), font1)); 
                }
                else {
                    startDatePeriod = startDatePeriod + 1;
                    datatable.setDefaultColspan(1);
                    datatable.setDefaultRowspan(1);
                    datatable.setBackgroundColor(summaryColor);                
                    datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
                    datatable.addCell(new Phrase(""+(startDatePeriod), font1)); 
                    
                }               
            }

            // this is the end of the table header
            datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on createHeaderDetail : "+e.toString());
        }
        return datatable;
    }
    
}
