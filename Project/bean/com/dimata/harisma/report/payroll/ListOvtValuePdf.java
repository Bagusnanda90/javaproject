/*
 * ListOvtValuePdf.java
 *
 * Created on August 16, 2007, 10:31 AM
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
public class ListOvtValuePdf extends HttpServlet{
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


        /* creating the document object */
        Document document = new Document(PageSize.LETTER.rotate(), 5, 5, 50, 50);

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
            String deptName = "";
            String sectName = "";
            Vector listOvt = new Vector(1,1);
            String footerNote = "";
            int dayOfMonth = 0;
            int startDatePeriod = 0;
            int sizeOvtType = 0;
            int idxType=0;
           
            //System.out.println("vctMonthlyAbsence  "+vctMonthlyAbsence.size());
            if(vctMonthlyAbsence != null && vctMonthlyAbsence.size()==9){
                try{
                        
                        listOvt = (Vector)vctMonthlyAbsence.get(0);
	            	period = (String) vctMonthlyAbsence.get(1);
                        deptName = (String) vctMonthlyAbsence.get(2);
                        sectName = (String) vctMonthlyAbsence.get(3);
                        footerNote = (String) vctMonthlyAbsence.get(4);
                        dayOfMonth = Integer.parseInt(String.valueOf(vctMonthlyAbsence.get(5)));
                        startDatePeriod = Integer.parseInt(String.valueOf(vctMonthlyAbsence.get(6)));
                        sizeOvtType = Integer.parseInt(String.valueOf(vctMonthlyAbsence.get(7)));
                        idxType = Integer.parseInt(String.valueOf(vctMonthlyAbsence.get(8)));
                        
                }
                catch(Exception e)
                {
                 	System.out.println("exc on get List vctMonthlyAbsence : "+e.toString());
                }
            }      
            if(sectName.equals("")){
                sectName = "";
            }
            else{
              sectName = " - SECTION : "+sectName;
            }
            
           if(deptName.equals("")){
               deptName = " ALL DEPARTMENT ";
           }
           else{
               deptName = deptName;
           }
            
            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase("LIST OF OVERTIME " +//(Formater.formatDate(presence,"MMMM")+ "-" +(Formater.formatDate(presence+1,"MMMM yyyy").toUpperCase()) +
                                                  "\n"+PstSystemProperty.getValueByName("COMPANY_NAME")+
                                                  "\nPERIOD :" + period.toUpperCase()+
                				  "\nDEPARTMENT : "+ deptName.toUpperCase()+sectName.toUpperCase(), fontHeader), false);

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
          
             
            /* opening the document, needed for add something into document */
            document.open();

            /* create header */  
            Table tableEmpPresence = new Table(1);
            if(idxType==PstPayEmpLevel.WITH_INDEX){
                tableEmpPresence = createHeaderDetailDinamis(listOvt,dayOfMonth,startDatePeriod,sizeOvtType, fontTitle);
            }else if(idxType==PstPayEmpLevel.NO_INDEX){
                tableEmpPresence = createHeaderDetailNoIndex(listOvt,dayOfMonth,startDatePeriod,sizeOvtType, fontTitle);
                fontContent = new Font(Font.TIMES_NEW_ROMAN, 8, Font.NORMAL, blackColor);
            }
            //Table tableEmpPresence = createHeaderDetailDinamis(listOvt,dayOfMonth,startDatePeriod,sizeOvtType, fontTitle);

            /* generate employee attendance report's data */
            Cell cellEmpData = new Cell("");
            
           if(listOvt!=null && listOvt.size()>0)
            {
               int intIterateTimes = 0;
                for (int i = 0; i < listOvt.size(); i++) 
                {          
                    Vector itemAbsence = (Vector)listOvt.get(i);
                    intIterateTimes = intIterateTimes + 1;
                   // System.out.println("vector itemAbsence............."+itemAbsence);
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
                    
                    if(PstOvt_Employee.OV_SALARY>0.0){
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(3)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    }
                    
                   // list of overtime jika pake index
                  if(idxType==PstPayEmpLevel.WITH_INDEX){
                   int index = 4;
                    for(int j=0; j<sizeOvtType; j++)
                    {
                        cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(index)),fontContent));
                        cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellEmpData.setBackgroundColor(whiteColor);
                        cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableEmpPresence.addCell(cellEmpData);  
                        index ++;
                    }
                    
                     int indexItem = index;
                    for(int j=0; j<sizeOvtType; j++)
                    {
                        cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(indexItem)),fontContent));
                        cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellEmpData.setBackgroundColor(whiteColor);
                        cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableEmpPresence.addCell(cellEmpData);  
                        indexItem ++;
                    }
                   
                    // total time overtime
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(indexItem)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData); 
                    
                    for(int j=0; j<sizeOvtType; j++)
                    {
                        cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(indexItem+1)),fontContent));
                        cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellEmpData.setBackgroundColor(whiteColor);
                        cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableEmpPresence.addCell(cellEmpData);  
                        indexItem ++;
                    }
                    
                   cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(indexItem+1)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData); 
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(indexItem+2)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData); 
                  }  
                    //list Overtime non index
                    
                  else{
                    //System.out.println("masuk no index ga?");
                      
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
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                  }
                        
                  if (!writer.fitsPage(tableEmpPresence)) {
                        tableEmpPresence.deleteLastRow(); 
                        i--;
                        document.add(tableEmpPresence);
                        document.newPage(); 
                         if(idxType==PstPayEmpLevel.WITH_INDEX){
                             tableEmpPresence = createHeaderDetailDinamis(listOvt,dayOfMonth,startDatePeriod,sizeOvtType, fontTitle);
                         }else{
                             tableEmpPresence = createHeaderDetailNoIndex(listOvt,dayOfMonth,startDatePeriod,sizeOvtType, fontTitle);
                         }
                    }
                    
//                    if (intIterateTimes == MAXIMUM_DATA_PER_PAGE) 
//                    {
//                        document.add(tableEmpPresence);   
//                        document.newPage();                        
//                        tableEmpPresence = createHeaderDetail(intDateOfMonth, fontContent);
//                    }  */ 
                    
                   
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
    public static Table createHeaderDetailDinamis(Vector ovtEmp,int dayOfMonth, int startDatePeriod,int sizeOvtType, Font font1)
    {        
        Table datatable = null;  
        try
        {     
           //int maxColumn = ((sizeOvtType*3)+7); 
            int maxColumn = ((sizeOvtType*3)+7); 
            //int maxColumn = listAbReason+5; 
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 2;
            headerwidths[1] = 5;
            headerwidths[2] = 3;
            headerwidths[3] = 3;
            int index = 4;                        
            for(int j=0; j<sizeOvtType; j++)
            {   
               headerwidths[index] = 4;  
               index++; 
             }
            int ovtItem = index;
             for(int o=0; o<sizeOvtType; o++)
             {   
               headerwidths[ovtItem] = 2;  
               ovtItem++; 
             }
            headerwidths[ovtItem] = 2;
          
            int ovtItemIdx = ovtItem +1;
            for(int o=0; o<sizeOvtType; o++)
             {   
               headerwidths[ovtItemIdx] = 2;  
               ovtItemIdx++; 
             }
            headerwidths[ovtItemIdx] = 3;
            headerwidths[ovtItemIdx+1] = 3;
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
            datatable.addCell(new Phrase("Name", font1));
            datatable.addCell(new Phrase("Emp.Categ", font1));
            if(PstOvt_Employee.OV_SALARY>0.0){
                datatable.addCell(new Phrase("Salary", font1));
            }
            
         
            datatable.setDefaultColspan(1);
            datatable.setDefaultRowspan(2);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Tarif 1.5", font1));


            datatable.setDefaultColspan(1);
            datatable.setDefaultRowspan(2);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Tarif 2.0", font1));
           
            datatable.setDefaultColspan(sizeOvtType);
            datatable.setDefaultRowspan(1);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Time Overtime", font1));
            
            datatable.setDefaultColspan(1);
            datatable.setDefaultRowspan(2);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Total Time", font1));
            
             datatable.setDefaultColspan(sizeOvtType);
            datatable.setDefaultRowspan(1);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Nominal Overtime", font1));
            
            datatable.setDefaultColspan(1);
            datatable.setDefaultRowspan(2);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Total Overtime", font1));
            
           
           datatable.setDefaultColspan(1);
            datatable.setDefaultRowspan(2);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Total Take Home Pay", font1));
            
            String strIdx ="";
            for(int i=0;i<sizeOvtType;i++){
                strIdx = strIdx+"I";
                datatable.setDefaultColspan(1);
                datatable.setDefaultRowspan(1);            
                datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
                datatable.addCell(new Phrase(""+strIdx, font1));
            
            }
            
           String strNom ="";
            for(int j=0;j<sizeOvtType;j++){
                strNom = strNom +"I";
                datatable.setDefaultColspan(1);
                datatable.setDefaultRowspan(1);            
                datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
                datatable.addCell(new Phrase(""+strNom, font1));
            
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
    public static Table createHeaderDetailNoIndex(Vector ovtEmp,int dayOfMonth, int startDatePeriod,int sizeOvtType, Font font1)
    {        
        Table datatable = null;  
        try
        {                        
           int maxColumn = 7; 
            //int maxColumn = listAbReason+5; 
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 2;
            headerwidths[1] = 5;
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
            datatable.setDefaultRowspan(2);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.setBackgroundColor(summaryColor);                
            
           
            datatable.addCell(new Phrase("No", font1));
            datatable.addCell(new Phrase("Name", font1));
            
            datatable.setDefaultColspan(1);
            datatable.setDefaultRowspan(2);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("EmpCatgeory", font1)); 
            
            if(PstOvt_Employee.OV_SALARY>0.0){
            datatable.setDefaultColspan(1);
            datatable.setDefaultRowspan(2);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Salary", font1));
            }
            
            datatable.setDefaultColspan(1);
            datatable.setDefaultRowspan(2);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            //datatable.addCell(new Phrase("Tarif \n(Salary:173)*2", font1));
            datatable.addCell(new Phrase("Tarif 2", font1));
            
            datatable.setDefaultColspan(1);
            datatable.setDefaultRowspan(2);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Time Overtime", font1));
           
            datatable.setDefaultColspan(1);
            datatable.setDefaultRowspan(2);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Take Home Pay", font1));
            
          
           /* datatable.setDefaultColspan(1);
            datatable.setDefaultRowspan(1);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Total", font1));*/
       
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
