/*
 * ListTunjEksporPdf.java
 *
 * Created on August 29, 2007, 4:20 PM
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
public class ListTunjEksporPdf extends HttpServlet{
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
            String period = "";
            String sectionName = "";
            String departmentName = "";
            Vector listSalaryPdf = new Vector(1,1);
            String footerNote = "";
            String summRadio = "";
            String summEkspor = "";
            
            //System.out.println("vctMonthlyAbsence  "+vctMonthlyAbsence.size());
            if(vctMonthlyAbsence != null && vctMonthlyAbsence.size()==7){
                try{
                        //System.out.println("masuk try.............");
                        listSalaryPdf = (Vector)vctMonthlyAbsence.get(0);
	            	period = (String) vctMonthlyAbsence.get(1);
                        sectionName = (String) vctMonthlyAbsence.get(2);
                        departmentName = (String) vctMonthlyAbsence.get(3);
                        footerNote = (String) vctMonthlyAbsence.get(4);
                        summRadio = (String) vctMonthlyAbsence.get(5);
                        summEkspor = (String) vctMonthlyAbsence.get(6);
                        if(departmentName.equals("")){
                            departmentName = " ALL DEPARTMENT";
                        }else{
                            departmentName = departmentName;
                        }
                        
                         if(sectionName.equals("")){
                            sectionName = " ";
                         }else{
                            sectionName = " - "+sectionName;
                         }
                      
                      
                        //System.out.println("startDatePeriod  "+startDatePeriod);
                }
                catch(Exception e)
                {
                 	System.out.println("exc on get List vctMonthlyAbsence : "+e.toString());
                }
            }               
            
            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase("EKSPOR ALLOWANCE REPORT" +//(Formater.formatDate(presence,"MMMM")+ "-" +(Formater.formatDate(presence+1,"MMMM yyyy").toUpperCase()) +
                                                  "\n"+com.dimata.system.entity.system.PstSystemProperty.getValueByName("COMPANY_NAME")+
                                                  "\nPERIOD :" + period.toUpperCase()+
                				  "\nDEPARTMENT : "+departmentName.toUpperCase()+""+sectionName.toUpperCase(), fontHeader), false);

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
            Table tableEmpPresence = createHeaderDetailDinamis(listSalaryPdf, fontContent);

            /* generate employee attendance report's data */
            Cell cellEmpData = new Cell("");
             
            if(listSalaryPdf!=null && listSalaryPdf.size()>0)
            {
                
                int intIterateTimes = 0;
                for (int i = 0; i < listSalaryPdf.size(); i++) 
                {          
                    Vector itemAbsence = (Vector)listSalaryPdf.get(i);
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
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);  
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(4)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData); 
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(5)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData); 
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(6)),fontContent));
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
                        tableEmpPresence = createHeaderDetailDinamis(listSalaryPdf, fontContent);
                    }
                    
//                    if (intIterateTimes == MAXIMUM_DATA_PER_PAGE) 
//                    {
//                        document.add(tableEmpPresence);   
//                        document.newPage();                        
//                        tableEmpPresence = createHeaderDetail(intDateOfMonth, fontContent);
//                    }   
                    
                   
	        }
                 
            }

            
            if(Integer.parseInt(summRadio)==1){
                
                if (!writer.fitsPage(tableEmpPresence)) {
                    document.add(tableEmpPresence);   
                    document.newPage(); 
                    tableEmpPresence = createSummSalary(summEkspor, fontTitle,fontHeader);
                   
                }else{
                    document.add(tableEmpPresence);   
                    //document.newPage(); 
                    tableEmpPresence = createSummSalary(summEkspor, fontTitle,fontHeader);
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
     * @param startDatePeriod // start date yang diset di sistem property
     * @return
     * @created by Yunny
     */    
    public static Table createHeaderDetailDinamis(Vector listSalaryPdf, Font font1)
    {        
        Table datatable = null;  
        try
        {                        
           int maxColumn = 7; 
            //int maxColumn = listAbReason+5; 
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 2;
            headerwidths[1] = 8;
            headerwidths[2] = 3;
            headerwidths[3] = 3;
            headerwidths[4] = 3;
            headerwidths[5] = 3;
            headerwidths[6] = 3;
         
            // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(1);
            datatable.setSpacing(1);
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultRowspan(1);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.setBackgroundColor(summaryColor);                
         
            // data no urut, payroll number and employee
            datatable.addCell(new Phrase("No", font1));
            datatable.addCell(new Phrase("Employee", font1));
            datatable.addCell(new Phrase("Emp.Category", font1));
            datatable.addCell(new Phrase("Tunj.Ekspor", font1));
            datatable.addCell(new Phrase("Persentage Presence (%)", font1));
            datatable.addCell(new Phrase("Total", font1));
            datatable.addCell(new Phrase("Description", font1));
        
            // this is the end of the table header
            datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on createHeaderDetailDinamis : "+e.toString());
        }
        return datatable;
    }
    
    
    public static Table createSummSalary(String summEkspor, Font font1,Font font2)
    {        
        Table datatable = null;  
        try
        {                        
           int maxColumn = 7; 
           String frmCurrency = "#,###";
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 2;
            headerwidths[1] = 5;
            headerwidths[2] = 3;
            headerwidths[3] = 5;
            headerwidths[4] = 5;
            headerwidths[5] = 3;
            headerwidths[6] = 3;
      
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
            
            Cell nameCell = new Cell(new Chunk(""+PstSystemProperty.getValueByName("ACC_NAME"), font1));
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
            Cell cellSpace = new Cell(new Chunk("", font1));
            cellSpace.setColspan(7);
            cellSpace.setRowspan(2);
            cellSpace.setBorderColor(new Color(255, 255, 255));
            cellSpace.setBorder(Rectangle.TOP);
            cellSpace.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellSpace.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(cellSpace);
            
             // row 1 cols 1
            Cell billCell = new Cell(new Chunk("", font1));
            billCell.setBorderColor(new Color(255, 255, 255));
            billCell.setBorder(Rectangle.TOP);
            billCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(billCell);
            
            // row 1 cols 2
            billCell = new Cell(new Chunk("TOTAL EKSPOR ALLOWANCE :", font1));
            billCell.setBorderColor(new Color(255, 255, 255));
            billCell.setBorder(Rectangle.TOP);
            billCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(billCell);
            
            // row 1 cols 3
            billCell = new Cell(new Chunk(""+Formater.formatNumber(Double.parseDouble(summEkspor),frmCurrency), font1));
            billCell.setBorderColor(new Color(255, 255, 255));
            billCell.setBorder(Rectangle.TOP);
            billCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(billCell);
            
             // row 1 cols 4
            billCell = new Cell(new Chunk("", font1));
            billCell.setColspan(5);
            billCell.setBorderColor(new Color(255, 255, 255));
            billCell.setBorder(Rectangle.TOP);
            billCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(billCell);
            
         
           // this is the end of the table header
            datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on createHeaderDetailDinamis : "+e.toString());
        }
        return datatable;
    }
    
    /** Creates a new instance of ListTunjEksporPdf */
    public ListTunjEksporPdf() {
    }
    
}
