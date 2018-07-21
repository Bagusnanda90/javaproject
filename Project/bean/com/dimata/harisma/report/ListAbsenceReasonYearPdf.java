/*
 * ListAbsenceReasonYearPdf.java
 *
 * Created on May 31, 2007, 5:11 PM
 */

package com.dimata.harisma.report;


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
import com.dimata.system.entity.system.*;

/* package harisma */
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.admin.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.session.attendance.*;

/**
 *
 * @author  yunny
 */
public class ListAbsenceReasonYearPdf extends HttpServlet {
    
    /* declaration of maximum data per page*/
    private int MAXIMUM_DATA_PER_PAGE = 20;
    
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
            Vector vctMonthlyAbsence = null;
            try{
	        vctMonthlyAbsence = (Vector)sessEmpPresence.getValue("ABSENCE_REASON");
            }catch(Exception e){
                System.out.println("Exc : "+e.toString());
            }

            //Date presence = new Date(); untuk start periode dari 1-31
            String period = "";
            String departmentName = "";
            Vector listAbsence = new Vector(1,1);
            Vector listReasonMaster = new Vector (1,1);
            Vector absenceReasonList = new Vector(1,1);
            int listAbReason = 0;
            String strFooter ="";
            String sectName ="";
            //System.out.println("vctMonthlyAbsence  "+vctMonthlyAbsence.size());
            if(vctMonthlyAbsence != null && vctMonthlyAbsence.size()==8){
                try{
	            	period = (String) vctMonthlyAbsence.get(0);
                        departmentName = (String)vctMonthlyAbsence.get(1);
                       
                        if(departmentName.equals("")){
                            departmentName = " ALL DEPARTMENT";
                        }
                        else{
                            departmentName = (String)vctMonthlyAbsence.get(1);
                        }
	                
	                listAbsence = (Vector)vctMonthlyAbsence.get(2);
                        listAbReason = Integer.parseInt(String.valueOf(vctMonthlyAbsence.get(3)));
                        listReasonMaster = (Vector)vctMonthlyAbsence.get(4);
                        absenceReasonList = (Vector)vctMonthlyAbsence.get(5);
                        strFooter = (String)vctMonthlyAbsence.get(6);
                        sectName = (String)vctMonthlyAbsence.get(7);
                        
                        if(sectName.equals("")){
                            sectName = "";
                        }else{
                            sectName = " - SECTION : "+sectName;
                        }
                }
                catch(Exception e)
                {
                 	System.out.println("exc on get List vctMonthlyAbsence : "+e.toString());
                }
            }               
            
            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase("ABSENCE REASON REPORT PER " + period.toUpperCase()+//(Formater.formatDate(presence,"MMMM")+ "-" +(Formater.formatDate(presence+1,"MMMM yyyy").toUpperCase()) +
                                                    "\n"+com.dimata.system.entity.system.PstSystemProperty.getValueByName("COMPANY_NAME")+
                				  "\nDEPARTMENT : "+ departmentName.toUpperCase()+""+sectName.toUpperCase(), fontHeader), false);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);
            
               //footer
            HeaderFooter footer = new HeaderFooter(new Phrase("DESCRIPTION :"+absenceReasonList+
                                                                "\n\n" +strFooter.toUpperCase()+
                                                                "\n                                                             "+
                                                                "                                                               "+
                                                                "                                                               "+
                                                                "                                                               "+
                                                                "                                                               "+
                                                                "                                          ", fontContent), true);
            // HeaderFooter headerleft = new HeaderFooter(new Phrase("LEVEL : " + levName.toUpperCase(), fontHeader), false);
            footer.setAlignment(Element.ALIGN_LEFT);
            footer.setBorder(Rectangle.TOP);
            footer.setBorderColor(blackColor);
            document.setFooter(footer);
             
            /* opening the document, needed for add something into document */
            document.open();

            /* create header */            
            Table tableEmpPresence = createHeaderDetailDinamis(listAbReason,listReasonMaster, fontContent);

            /* generate employee attendance report's data */
            Cell cellEmpData = new Cell("");
             
            if(listAbsence!=null && listAbsence.size()>0)
            {
                int intIterateTimes = 0;
                for (int i = 0; i < listAbsence.size(); i++) 
                {          
                    Vector itemAbsence = (Vector)listAbsence.get(i);
                    intIterateTimes = intIterateTimes + 1;

                    
                      
                    // no
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(0)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(1)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(2)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);     
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(3)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);    
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(4)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);    
                    
                        
                    // list of schedule and duration, dimulai dari index ke 3
                    int index = 5;
                    for(int j=0; j<listReasonMaster.size(); j++)
                    {
                        cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(index)),fontContent));
                        cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellEmpData.setBackgroundColor(whiteColor);
                        cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableEmpPresence.addCell(cellEmpData);  
                        index ++;
                    }
                    
                    //total
                    int indexItem = listReasonMaster.size()+5;
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(indexItem)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData); 
        
                    if (!writer.fitsPage(tableEmpPresence)) {
                        tableEmpPresence.deleteLastRow(); 
                        i--;
                        document.add(tableEmpPresence);
                        document.newPage();                        
                        tableEmpPresence = createHeaderDetailDinamis(listAbReason,listReasonMaster, fontContent);
                    }
                    
//                    if (intIterateTimes == MAXIMUM_DATA_PER_PAGE) 
//                    {
//                        document.add(tableEmpPresence);   
//                        document.newPage();                        
//                        tableEmpPresence = createHeaderDetail(intDateOfMonth, fontContent);
//                    }                    
                    
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
            datatable.addCell(new Phrase("Employee", font1));
            datatable.addCell(new Phrase("Payroll", font1));
            
            datatable.setDefaultColspan(intDateOfMonth);
            datatable.setDefaultRowspan(1);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Absence Period", font1));                        
            
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
     * create header of report
     * @param intDateOfMonth
     * @param font1
     * @param startDatePeriod // start date yang diset di sistem property
     * @return
     * @created by Yunny
     */    
    public static Table createHeaderDetailDinamis(int listAbReason, Vector listReasonMaster, Font font1)
    {        
        Table datatable = null;  
        try
        {                        
           int maxColumn = listAbReason  + 6; 
            //int maxColumn = listAbReason+5; 
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 2;
            headerwidths[1] = 5;
            headerwidths[2] = 3;
            headerwidths[3] = 4;
            headerwidths[4] = 5;
            int index = 5;      
            //header for reason
            for(int j=0; j<listAbReason; j++)
            {   
                headerwidths[index] = 3;  
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
               // data no urut, payroll number and employee
            datatable.addCell(new Phrase("No", font1));
            datatable.addCell(new Phrase("Employee", font1));
            datatable.addCell(new Phrase("Comm. Date", font1));
            datatable.addCell(new Phrase("Marital St./Religion", font1));
            datatable.addCell(new Phrase("Emp.Category", font1));
           
            for(int i=0;i<listReasonMaster.size();i++){
                    datatable.addCell(new Phrase(""+String.valueOf(listReasonMaster.get(i)), font1));
            }
           
            // data total night shift
            datatable.setDefaultRowspan(2);
            datatable.setDefaultColspan(1);
            datatable.setBackgroundColor(summaryColor);                
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.addCell(new Phrase("Total", font1));          

             // this is the end of the table header
            datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on createHeaderDetailDinamis : "+e.toString());
        }
        return datatable;
    }
    
}
