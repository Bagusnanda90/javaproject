/*
 * mealAllowancePdf.java
 *
 * Created on August 24, 2007, 1:20 PM
 */

package com.dimata.harisma.report.payroll;

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
import com.dimata.harisma.entity.payroll.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.session.attendance.*;

/**
 *
 * @author  yunny
 */
public class mealAllowancePdf extends HttpServlet{
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
        Document document = new Document(PageSize.LETTER.rotate(), 15, 15, 50, 50);

        /* creating an OutputStream */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            /* creating an instance of the writer */
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            /* get data from session */
            HttpSession sessEmpPresence = request.getSession(true);
            Vector vctMonthlyAbsence = null;
            try{
	        vctMonthlyAbsence = (Vector)sessEmpPresence.getValue("QUERY_REPORT");
            }catch(Exception e){
                System.out.println("Exc : "+e.toString());
            }

            //Date presence = new Date(); untuk start periode dari 1-31
            String deptName = "";
            String sectName = "";
            Vector listAllowance = new Vector(1,1);
            String footerNote = "";
            Date selectedDateFrom = new Date();
            int month = 0;
            int year = 0;
            int startDate = 0;
            int dateOfMonth = 0;
            Date selectedDateTo = new Date();
            Date dateTo = new Date();
            String sumMealWeekly="";
            String totalMealAll ="";
            String totalAbsence ="";
            String totalPayment = "";
            Vector secSelect = new Vector(1,1);
            String absSatpam = "";
            // System.out.println("vctMonthlyAbsence.size()......"+vctMonthlyAbsence.size());
            //System.out.println("vctMonthlyAbsence  "+vctMonthlyAbsence);
            if(vctMonthlyAbsence != null && vctMonthlyAbsence.size()==13){
                try{
                       
                        listAllowance = (Vector)vctMonthlyAbsence.get(0);
                      	deptName = (String) vctMonthlyAbsence.get(1);
                        sectName = (String) vctMonthlyAbsence.get(2);
                        footerNote = (String) vctMonthlyAbsence.get(3);
                        selectedDateFrom = (Date) vctMonthlyAbsence.get(4);
                            month = selectedDateFrom.getMonth();
                            year = selectedDateFrom.getYear();
                            startDate = selectedDateFrom.getDate();
                            Calendar newCalendar = Calendar.getInstance();
                            newCalendar.setTime(selectedDateFrom);
                            dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
                        selectedDateTo = (Date) vctMonthlyAbsence.get(5);
                        sumMealWeekly = (String) vctMonthlyAbsence.get(7);
                        totalMealAll = (String) vctMonthlyAbsence.get(8);
                        totalAbsence = (String) vctMonthlyAbsence.get(9);
                        totalPayment = (String) vctMonthlyAbsence.get(10);
                        secSelect = (Vector)vctMonthlyAbsence.get(11);
                        absSatpam = (String) vctMonthlyAbsence.get(12);
                        if(sectName.equals("")){
                            sectName = "";
                        }else{
                            sectName = " - "+sectName;
                        }
                       // startDate = startDate +1;
                        for(int d=0;d<6;d++){
                           if(startDate==dateOfMonth){
                                startDate = 0;
                                month = month +1;
                                year = selectedDateTo.getYear();
                            }
                           startDate ++;
                           
                          
                        }
                        dateTo = new java.util.Date(year, month, startDate, 0, 0);
                       
                         
                }
                catch(Exception e)
                {
                 	System.out.println("exc on get List vctMonthlyAbsence : "+e.toString());
                }
            }               
            
            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase("WEEKLY MEAL ALLOWANCE " +//(Formater.formatDate(presence,"MMMM")+ "-" +(Formater.formatDate(presence+1,"MMMM yyyy").toUpperCase()) +
                                                  "\n"+com.dimata.system.entity.system.PstSystemProperty.getValueByName("COMPANY_NAME")+
                                                  "\nDEPARTMENT :" + deptName.toUpperCase()+""+sectName.toUpperCase()+
                				  "\nPERIOD : "+ Formater.formatDate(selectedDateFrom,"dd-MMM-yyyy")+" - "+Formater.formatDate(dateTo,"dd-MMM-yyyy"), fontHeader), false);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);
            
            
             //footer
             
           HeaderFooter footer = new HeaderFooter(new Phrase(""+footerNote.toUpperCase(), fontContent), false);
            // HeaderFooter headerleft = new HeaderFooter(new Phrase("LEVEL : " + levName.toUpperCase(), fontHeader), false);
            footer.setAlignment(Element.ALIGN_LEFT);
            footer.setBorder(Rectangle.TOP);
            footer.setBorderColor(blackColor);
            document.setFooter(footer);
           //------------------------------------
            /* Vector reason = PstReason.list(0,0, "", "");
           //System.out.println("size reason "+reason.size());
            if(reason!=null && reason.size()>0){
                for(int r=0;r<reason.size();r++){
                    Reason rs = (Reason)reason.get(r);
                    HeaderFooter footer = new HeaderFooter(new Phrase("" + rs.getReason().toUpperCase()+
                    "="+ rs.getDescription().toUpperCase(),fontHeader), false);
                    
                    footer.setAlignment(Element.ALIGN_LEFT);
                    footer.setBorder(Rectangle.TOP);
                    footer.setBorderColor(blackColor);
                    document.setFooter(footer);
           
                }
            }*/
             
            
             
            /* opening the document, needed for add something into document */
            document.open();

            /* create header */            
            Table tableEmpPresence = createHeaderDetailDinamis(listAllowance,selectedDateFrom,dateOfMonth, fontContent);

            /* generate employee attendance report's data */
            Cell cellEmpData = new Cell("");
             
            if(listAllowance!=null && listAllowance.size()>0)
            {
                
                int intIterateTimes = 0;
                for (int i = 0; i < listAllowance.size(); i++) 
                {          
                    Vector itemAbsence = (Vector)listAllowance.get(i);
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
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(5)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData); 
                
                    // list of salary component (benefit)
                     int index = 6;
                    for(int j=0; j<6; j++)
                    {
                        cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(index)),fontContent));
                        cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellEmpData.setBackgroundColor(whiteColor);
                        cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableEmpPresence.addCell(cellEmpData);  
                        index ++;
                    }
                    
                    
                   cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(index)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);    
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(index+1)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);    
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(index+2)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);    
                    
                  
                   
                    if (!writer.fitsPage(tableEmpPresence)) {
                        tableEmpPresence.deleteLastRow(); 
                        i--;
                        document.add(tableEmpPresence);
                        document.newPage();                        
                        tableEmpPresence = createHeaderDetailDinamis(listAllowance,selectedDateFrom,dateOfMonth, fontContent);
                    }
                    
//                    if (intIterateTimes == MAXIMUM_DATA_PER_PAGE) 
//                    {
//                        document.add(tableEmpPresence);   
//                        document.newPage();                        
//                        tableEmpPresence = createHeaderDetail(intDateOfMonth, fontContent);
//                    }   
                    
                   
	        }
                 
            }
           
           if(Integer.parseInt(sumMealWeekly)==1){
                
                if (secSelect.size()>1) {
                    document.add(tableEmpPresence);   
                    //document.newPage(); 
                    tableEmpPresence = createSummSalary(sumMealWeekly,totalMealAll,totalAbsence,totalPayment,absSatpam,fontTitle);
                   
                }else{
                    document.add(tableEmpPresence);   
                    document.newPage(); 
                    tableEmpPresence = createSummSalary(sumMealWeekly,totalMealAll,totalAbsence,totalPayment,absSatpam,fontTitle);
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
     * @return
     * @created by Yunny
     */    
    public static Table createHeaderDetailDinamis(Vector listAllowance,Date selectedDateFrom, int maxDayOfMonth, Font font1)
    {        
        Table datatable = null;  
        try
        {        
           int startDate = selectedDateFrom.getDate();
           int maxColumn = 7+8; 
            //int maxColumn = listAbReason+5; 
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 2;
            headerwidths[1] = 6;
            headerwidths[2] = 3;
            headerwidths[3] = 3;
            headerwidths[4] = 3;
            headerwidths[5] = 3;
            int index = 6;                        
            for(int j=0; j<6; j++)
            {   
               headerwidths[index] = 2;  
                index++; 
             }
           
            headerwidths[index] = 3;
             headerwidths[index+1] = 3;
             headerwidths[index+2] = 3;
             

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
            datatable.addCell(new Phrase("Emp.Category", font1));
            datatable.addCell(new Phrase("Religion", font1));
            datatable.addCell(new Phrase("Comp.Date", font1));
           
           datatable.setDefaultRowspan(1);
            datatable.setDefaultColspan(7);
            datatable.setBackgroundColor(summaryColor);                
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.addCell(new Phrase("Date", font1)); 
            
            datatable.setDefaultRowspan(2);
            datatable.setDefaultColspan(1);
            datatable.setBackgroundColor(summaryColor);                
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.addCell(new Phrase("Presence", font1)); 
            
            datatable.setDefaultRowspan(2);
            datatable.setDefaultColspan(1);
            datatable.setBackgroundColor(summaryColor);                
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.addCell(new Phrase("Meal Allowance", font1)); 
            
            datatable.setDefaultRowspan(2);
            datatable.setDefaultColspan(1);
            datatable.setBackgroundColor(summaryColor);                
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.addCell(new Phrase("Description", font1));
           
                   
            datatable.setDefaultRowspan(1);
            datatable.setDefaultColspan(1);
            datatable.setBackgroundColor(summaryColor);                
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.addCell(new Phrase(""+startDate, font1)); 
       
           for(int d=0; d<6; d++)
            {
                if(startDate==maxDayOfMonth){
                    startDate = 0;
                }
                startDate = startDate +1;
                datatable.setDefaultRowspan(1);
                datatable.setDefaultColspan(1);
                datatable.setBackgroundColor(summaryColor);                
                datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
                datatable.addCell(new Phrase(""+startDate, font1)); 
                               
            }

            // this is the end of the table header
            datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on createHeaderDetailDinamis : "+e.toString());
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
   public static Table  createSummSalary(String sumMealWeekly,String totalMealAll,String totalAbsence,String totalPayment,String absSatpam,Font font1)
    {        
        Table datatable = null;  
        try
        {                        
           int maxColumn = 7; 
           String frmCurrency = "#,###";
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 5;
            headerwidths[1] = 2;
            headerwidths[2] = 5;
            headerwidths[3] = 2;
            headerwidths[4] = 5;
            headerwidths[5] = 2;
            headerwidths[6] = 5;
           
           // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(0);
            datatable.setSpacing(1);
            datatable.setBorderWidth(0);
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultRowspan(2);
            datatable.setBorder(Rectangle.TOP);
            datatable.setBackgroundColor(whiteColor); 
            
             // row 1 cols 1
            Cell placeCell = new Cell(new Chunk("Denpasar,"+Formater.formatDate(new Date(),"dd-MMM-yyyy"), font1));
            placeCell.setColspan(3);
            placeCell.setBorderColor(new Color(255, 255, 255));
            placeCell.setBorder(Rectangle.TOP);
            placeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            placeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(placeCell);
            
            // row 1 cols 1
            placeCell = new Cell(new Chunk("Cashier", font1));
            placeCell.setColspan(2);
            placeCell.setBorderColor(new Color(255, 255, 255));
            placeCell.setBorder(Rectangle.TOP);
            placeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            placeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(placeCell);
            
             // row 1 cols 1
            placeCell = new Cell(new Chunk("General Manager", font1));
            placeCell.setColspan(2);
            placeCell.setBorderColor(new Color(255, 255, 255));
            placeCell.setBorder(Rectangle.TOP);
            placeCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            placeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(placeCell);
            
             
             // row 1 cols 1
            Cell spaceName = new Cell(new Chunk("", font1));
            spaceName.setColspan(7);
            spaceName.setRowspan(2);
            spaceName.setBorderColor(new Color(255, 255, 255));
            spaceName.setBorder(Rectangle.TOP);
            spaceName.setHorizontalAlignment(Element.ALIGN_LEFT);
            spaceName.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(spaceName);
            
            Cell nameCell = new Cell(new Chunk(""+PstSystemProperty.getValueByName("PENGHITUNG"), font1));
            nameCell.setColspan(3);
            nameCell.setBorderColor(new Color(255, 255, 255));
            nameCell.setBorder(Rectangle.TOP);
            nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(nameCell);
            
            // row 1 cols 1
            nameCell = new Cell(new Chunk(""+PstSystemProperty.getValueByName("KASIR"), font1));
            nameCell.setColspan(2);
            nameCell.setBorderColor(new Color(255, 255, 255));
            nameCell.setBorder(Rectangle.TOP);
            nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(nameCell);
            
             // row 1 cols 1
            nameCell = new Cell(new Chunk(""+PstSystemProperty.getValueByName("GM"), font1));
            nameCell.setColspan(2);
            nameCell.setBorderColor(new Color(255, 255, 255));
            nameCell.setBorder(Rectangle.TOP);
            nameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(nameCell);
            
              // row 1 cols 1
            Cell spaceCell= new Cell(new Chunk("",font1));
            spaceCell.setColspan(7);
            spaceCell.setBorderColor(new Color(255, 255, 255));
            spaceCell.setBorder(Rectangle.TOP);
            spaceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            spaceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(spaceCell);
            
            
             // row 1 cols 1
            Cell billCell = new Cell(new Chunk("Total Meall Allowance", font1));
            billCell.setColspan(2);
            billCell.setBorderColor(new Color(255, 255, 255));
            billCell.setBorder(Rectangle.TOP);
            billCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(billCell);
            
         
            // row 1 cols 3
            billCell = new Cell(new Chunk(""+Formater.formatNumber(Double.parseDouble(totalMealAll),frmCurrency), font1));
            billCell.setBorderColor(new Color(255, 255, 255));
            billCell.setBorder(Rectangle.TOP);
            billCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(billCell);
            
             // row 1 cols 4
            billCell = new Cell(new Chunk("", font1));
            billCell.setColspan(4);
            billCell.setBorderColor(new Color(255, 255, 255));
            billCell.setBorder(Rectangle.TOP);
            billCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(billCell);
            
           
            // row 2 cols 1
            Cell absenceCell = new Cell(new Chunk("Absence Deduction", font1));
            absenceCell.setColspan(2);
            absenceCell.setBorderColor(new Color(255, 255, 255));
            absenceCell.setBorder(Rectangle.TOP);
            absenceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            absenceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(absenceCell);
            
           
             // row 2 cols 3
            absenceCell = new Cell(new Chunk(""+Formater.formatNumber(Double.parseDouble(totalAbsence),frmCurrency), font1));
            absenceCell.setBorderColor(new Color(255, 255, 255));
            absenceCell.setBorder(Rectangle.TOP);
            absenceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            absenceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(absenceCell);
            
            absenceCell = new Cell(new Chunk("", font1));
            absenceCell.setColspan(4);
            absenceCell.setBorderColor(new Color(255, 255, 255));
            absenceCell.setBorder(Rectangle.TOP);
            absenceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            absenceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(absenceCell);
        
            // row 3 cols 1
            Cell totalCell = new Cell(new Chunk("Total Payment", font1));
            totalCell.setColspan(2);
            totalCell.setBorderColor(new Color(255, 255, 255));
            totalCell.setBorder(Rectangle.TOP);
            totalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(totalCell);
            
           
            
             // row 3 cols 3
            totalCell = new Cell(new Chunk(""+Formater.formatNumber(Double.parseDouble(totalPayment),frmCurrency), font1));
            totalCell.setBorderColor(new Color(255, 255, 255));
            totalCell.setBorder(Rectangle.TOP);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(totalCell);
            
            totalCell = new Cell(new Chunk("", font1));
            totalCell.setColspan(4);
            totalCell.setBorderColor(new Color(255, 255, 255));
            totalCell.setBorder(Rectangle.TOP);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(totalCell);
            
            
            // row 3 cols 1
            Cell totalSatp = new Cell(new Chunk("Late/Absence Satpam", font1));
            totalSatp.setColspan(2);
            totalSatp.setBorderColor(new Color(255, 255, 255));
            totalSatp.setBorder(Rectangle.TOP);
            totalSatp.setHorizontalAlignment(Element.ALIGN_LEFT);
            totalSatp.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(totalSatp);
            
           
            
             // row 3 cols 3
            totalSatp = new Cell(new Chunk(""+Formater.formatNumber(Double.parseDouble(absSatpam),frmCurrency), font1));
            totalSatp.setBorderColor(new Color(255, 255, 255));
            totalSatp.setBorder(Rectangle.TOP);
            totalSatp.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalSatp.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(totalSatp);
            
            totalSatp = new Cell(new Chunk("", font1));
            totalSatp.setColspan(4);
            totalSatp.setBorderColor(new Color(255, 255, 255));
            totalSatp.setBorder(Rectangle.TOP);
            totalSatp.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalSatp.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(totalSatp);

            
          
            
            
        
           // this is the end of the table header
            datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on createHeaderDetailDinamis : "+e.toString());
        }
        return datatable;
    }
    
    /** Creates a new instance of mealAllowancePdf */
    public mealAllowancePdf() {
    }
    
}
