/*
 * ListSalaryPdf.java
 *
 * Created on August 10, 2007, 11:39 AM
 */

package com.dimata.harisma.report.payroll;

/* package java */
import java.util.*;
import java.text.*;
import java.awt.Color;
import java.awt.Dimension;
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
public class ListSalaryPdf extends HttpServlet{
    
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
        int LENGTH_OF_DATA = 24;

        /* creating the document object */
        Document document = new Document(PageSize.LETTER.rotate(), 15, 15, 30, 20);

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
            String levelName = "";
            Vector listSalaryPdf = new Vector(1,1);
            String footerNote = "";
            Vector vectBenefit = new Vector(1,1);
            Vector vectDeduction = new Vector(1,1);
            String department = "";
            String section = "";
            String summRadio = "";
            String sumSalary = "";
            String sumOvertime = "";
            String sumEksAllowance ="";
            String sumMealAllowance ="";
            String total ="";
            String sumKasbon ="";
            String sumLeaveDeduction ="";
            String sumOther ="";
            String sumLateDeduc="";
            String sumLateSatpamDeduc="";
            String sumSukaDukaDeduc="";
            String finalTotal ="";
            String totalTransfered ="";
            String nonTransfered ="";
            Period payPeriod = new Period();
 
            //System.out.println("vctMonthlyAbsence  "+vctMonthlyAbsence.size());
            if(vctMonthlyAbsence != null && vctMonthlyAbsence.size()==LENGTH_OF_DATA){
                try{
                        listSalaryPdf = (Vector)vctMonthlyAbsence.get(0);
	            	period = (String) vctMonthlyAbsence.get(1);
                        levelName = (String) vctMonthlyAbsence.get(2);
                        footerNote = (String) vctMonthlyAbsence.get(3);
                        vectBenefit = (Vector)vctMonthlyAbsence.get(4);
                        vectDeduction = (Vector)vctMonthlyAbsence.get(5);
                        department = (String) vctMonthlyAbsence.get(6);
                        section = (String) vctMonthlyAbsence.get(7);
                        summRadio =(String)vctMonthlyAbsence.get(8);
                        sumSalary =(String)vctMonthlyAbsence.get(9);
                        sumOvertime = (String)vctMonthlyAbsence.get(10);
                        sumEksAllowance = (String)vctMonthlyAbsence.get(11);
                        sumMealAllowance = (String)vctMonthlyAbsence.get(19);

                        sumLateDeduc=(String)vctMonthlyAbsence.get(20);;
                        sumLateSatpamDeduc=(String)vctMonthlyAbsence.get(21);
                        sumSukaDukaDeduc=(String)vctMonthlyAbsence.get(22);
                         payPeriod = (Period)vctMonthlyAbsence.get(23);
                        
                        
                        
                        total = (String)vctMonthlyAbsence.get(12);
                        sumKasbon = (String)vctMonthlyAbsence.get(13);
                        sumLeaveDeduction = (String)vctMonthlyAbsence.get(14);
                        sumOther = (String)vctMonthlyAbsence.get(15);
                        finalTotal = (String)vctMonthlyAbsence.get(16);
                        totalTransfered = (String)vctMonthlyAbsence.get(17);
                        nonTransfered = (String)vctMonthlyAbsence.get(18);

                      
                        if(section.equals("")){
                            section = section;
                        }else{
                            section = " - SEC : "+section;
                        }
                        
                        //section
                        
                         //System.out.println("startDatePeriod  "+startDatePeriod);
                }
                catch(Exception e)
                {
                 	System.out.println("exc on get List vctMonthlyAbsence : "+e.toString());
                }
            }               
            
            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase("LIST OF EMPLOYEE SALARY " +//(Formater.formatDate(presence,"MMMM")+ "-" +(Formater.formatDate(presence+1,"MMMM yyyy").toUpperCase()) +
                                                  "\n"+com.dimata.system.entity.system.PstSystemProperty.getValueByName("COMPANY_NAME")+
                                                  "\nPERIOD :" + period.toUpperCase()+
                				  "\nSALARY LEVEL : "+ levelName.toUpperCase(), fontHeader), false);

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
            Table tableEmpPresence = createHeaderDetailDinamis(listSalaryPdf,vectBenefit,vectDeduction, fontContent);

            /* generate employee attendance report's data */
            Cell cellEmpData = new Cell("");
            
            int totalDataRow=0;
             
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
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);   
                
                    // list of salary component (benefit)
                    int index = 6;
                    for(int j=0; j<vectBenefit.size(); j++)
                    {
                        cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(index)),fontContent));
                        cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
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
            
                    int indexItem = index + 1;
                    for(int j=0; j<vectDeduction.size(); j++)
                    {
                        cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(indexItem)),fontContent));
                        cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                        cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellEmpData.setBackgroundColor(whiteColor);
                        cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableEmpPresence.addCell(cellEmpData);  
                        indexItem ++;
                    }
                    
                    // total deduction
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(indexItem)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);  
                    
                     // total take home pay
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(indexItem+1)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData); 
                    
                    totalDataRow =indexItem;
                    
                    if (!writer.fitsPage(tableEmpPresence)) {
                        tableEmpPresence.deleteLastRow(); 
                        i--;
                        document.add(tableEmpPresence);
                        document.newPage();                        
                        tableEmpPresence = createHeaderDetailDinamis(listSalaryPdf,vectBenefit,vectDeduction, fontContent);
                    }
                    
                    /*if (intIterateTimes == listSalaryPdf.size()) 
                   {
                        document.add(tableEmpPresence);   
                        document.newPage();                        
                        tableEmpPresence = createHeaderDetailDinamis(listSalaryPdf,vectBenefit,vectDeduction, fontContent);
                    }  */ 
                    
                   
	        }
                 
            }
                                                
            boolean testSignOnPage=false;
            boolean testSummOnPage=false;
            Dimension dimTbl = tableEmpPresence.getDimension();
            
            totalDataRow = dimTbl.getSize().width;
            // test apakah halaman pdf masih cukup bila di tambah 5 kolom untuk table tanda tangan 
            for(int dum=0;dum<5;dum++){
                cellEmpData = new Cell(new Chunk("-",fontContent));
                cellEmpData.setColspan(totalDataRow);
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableEmpPresence.addCell(cellEmpData);    
            }
            if (writer.fitsPage(tableEmpPresence)) { // cek apakah cukup dlm satu halaman                
                testSignOnPage=true;
            }            
            // test apakah halaman pdf masih cukup bila di tambah 13 kolom untuk table summary
            for(int dum=0;dum<13;dum++){
                cellEmpData = new Cell(new Chunk("-",fontContent));
                cellEmpData.setColspan(totalDataRow);
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                tableEmpPresence.addCell(cellEmpData);    
            }
            if (writer.fitsPage(tableEmpPresence)) { // cek apakah cukup dlm satu halaman                
                testSummOnPage=true;
            }            
            for(int dum=0;dum<18;dum++){            // hapus row untuk test tadi
                tableEmpPresence.deleteLastRow(); 
            }
           
            document.add(tableEmpPresence);
                        
            if(!testSignOnPage){ // jika tidak cukup pada satu halaman dengan list salary , maka buat halaman baru
                document.newPage();                        
                tableEmpPresence = createHeaderDetailDinamis(listSalaryPdf,vectBenefit,vectDeduction, fontContent);
                document.add(tableEmpPresence);
            }
            
            tableEmpPresence = createSignTable(fontTitle,fontHeader, payPeriod.getEndDate());
            document.add(tableEmpPresence);
            
            
            int pInt=0;
            try{
               pInt = Integer.parseInt(summRadio);
            }catch(Exception exc){
                System.out.println(""+exc);
            }
            if(pInt==1){
                
                    if(testSignOnPage && !testSummOnPage) {
                        document.newPage();   // add only new page if sign page add no page and not enough page for sum page
                    }
                 
                    tableEmpPresence = createSummSalary(sumSalary,sumOvertime,sumEksAllowance,sumMealAllowance,total,
                            sumKasbon,sumLeaveDeduction,sumOther,sumLateDeduc, sumLateSatpamDeduc, sumSukaDukaDeduc, finalTotal,totalTransfered,nonTransfered, fontTitle,fontHeader);
                
                if (!writer.fitsPage(tableEmpPresence)) {
                    document.add(tableEmpPresence);   
                    document.newPage(); 
                    //tableEmpPresence = createSummSalary(sumSalary,sumOvertime,sumEksAllowance,total,sumKasbon,sumLeaveDeduction,sumOther,finalTotal,totalTransfered,nonTransfered, fontTitle,fontHeader);
                    //tableEmpPresence = createSummSalary(sumSalary,sumOvertime,sumEksAllowance,sumMealAllowance,total,
                      //      sumKasbon,sumLeaveDeduction,sumOther,sumLateDeduc, sumLateSatpamDeduc, sumSukaDukaDeduc, finalTotal,totalTransfered,nonTransfered, fontTitle,fontHeader);
                   
                }else{
                    //document.add(tableEmpPresence);   
                    //document.newPage(); 
                    //tableEmpPresence = createSummSalary(sumSalary,sumOvertime,sumEksAllowance,total,sumKasbon,sumLeaveDeduction,sumOther,finalTotal,totalTransfered,nonTransfered, fontTitle,fontHeader);
                    //tableEmpPresence = createSummSalary(sumSalary,sumOvertime,sumEksAllowance,sumMealAllowance,total,
                    //        sumKasbon,sumLeaveDeduction,sumOther,sumLateDeduc, sumLateSatpamDeduc, sumSukaDukaDeduc, finalTotal,totalTransfered,nonTransfered, fontTitle,fontHeader);
                    
                 }
                document.add(tableEmpPresence);

            }
            
            
            
           
            
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
    public static Table createHeaderDetailDinamis(Vector listSalaryPdf,Vector vectBenefit, Vector vectDeduction, Font font1)
    {        
        Table datatable = null;  
        try
        {                        
           int maxColumn = (vectBenefit.size()+vectDeduction.size()+9); 
            //int maxColumn = listAbReason+5; 
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 2;
            headerwidths[1] = 5;
            headerwidths[2] = 3;
            headerwidths[3] = 3;
            headerwidths[4] = 2;
            headerwidths[5] = 3;
            int index = 6;                        
            for(int j=0; j<vectBenefit.size(); j++)
            {   
               headerwidths[index] = 3;  
                index++; 
             }
           
             headerwidths[index] = 3;
             int dateItem = index +1;
             for(int j=0; j<vectDeduction.size(); j++)
              {   
                  headerwidths[dateItem] = 3;  
                  dateItem++; 
              }
             headerwidths[dateItem] = 3;
             headerwidths[dateItem+1] = 3;

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
            datatable.addCell(new Phrase("NO", font1));
            datatable.addCell(new Phrase("EMPLOYEE", font1));
            datatable.addCell(new Phrase("EMP.CAT", font1));
            datatable.addCell(new Phrase("RELIGION", font1));
            datatable.addCell(new Phrase("COMP.DATE", font1));
            datatable.addCell(new Phrase("SECTION", font1));
           
            for(int i=0;i<vectBenefit.size();i++){
                    datatable.addCell(new Phrase(""+String.valueOf(vectBenefit.get(i)).toUpperCase(), font1));
            }
            datatable.addCell(new Phrase("SALARY", font1));          
         
            for(int i=0;i<vectDeduction.size();i++){
                  datatable.addCell(new Phrase(""+String.valueOf(vectDeduction.get(i)).toUpperCase(), font1));
            }
            
            datatable.addCell(new Phrase("NON SALARY", font1));   
            datatable.addCell(new Phrase("TAKE HOME PAY", font1));          
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
    public static Table createSignTable(Font font1,Font font2, Date signDate)

    {        
        Table datatable = null;  
        try
        {                        
           int maxColumn = 7; 
            //String frmCurrency = "#,###";
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
            
            
             
             // row 1 cols 1-3
            if(signDate==null)
                signDate= new Date();
            Cell placeCell = new Cell(new Chunk("Denpasar,"+Formater.formatDate(signDate,"dd-MMM-yyyy"), font1));
            placeCell.setColspan(3);
            placeCell.setBorderColor(new Color(255, 255, 255));
            placeCell.setBorder(Rectangle.TOP);
            placeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            placeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(placeCell);
            
            // row 1 cols 4-5
            placeCell = new Cell(new Chunk("Cashier", font1));
            placeCell.setColspan(2);
            placeCell.setBorderColor(new Color(255, 255, 255));
            placeCell.setBorder(Rectangle.TOP);
            placeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            placeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(placeCell);
            
             // row 1 cols 6-7
            placeCell = new Cell(new Chunk("General Manager", font1));
            placeCell.setColspan(2);
            placeCell.setBorderColor(new Color(255, 255, 255));
            placeCell.setBorder(Rectangle.TOP);
            placeCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            placeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(placeCell);
            
             
             // row 2-3 cols 1-7
            Cell spaceName = new Cell(new Chunk("", font1));
            spaceName.setColspan(7);
            spaceName.setRowspan(2);
            spaceName.setBorderColor(new Color(255, 255, 255));
            spaceName.setBorder(Rectangle.TOP);
            spaceName.setHorizontalAlignment(Element.ALIGN_LEFT);
            spaceName.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(spaceName);
            
            //row 4 cols 2-3
            Cell nameCell = new Cell(new Chunk(""+PstSystemProperty.getValueByName("ACC_NAME"), font1));
            nameCell.setColspan(3);
            nameCell.setBorderColor(new Color(255, 255, 255));
            nameCell.setBorder(Rectangle.TOP);
            nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(nameCell);
            
            // row 4 cols 4-5
            nameCell = new Cell(new Chunk(""+PstSystemProperty.getValueByName("KASIR"), font1));
            nameCell.setColspan(2);
            nameCell.setBorderColor(new Color(255, 255, 255));
            nameCell.setBorder(Rectangle.TOP);
            nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(nameCell);
            
             // row 4 cols 6-7
            nameCell = new Cell(new Chunk(""+PstSystemProperty.getValueByName("GM"), font1));
            nameCell.setColspan(2);
            nameCell.setBorderColor(new Color(255, 255, 255));
            nameCell.setBorder(Rectangle.TOP);
            nameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(nameCell);
                        
        
           // this is the end of the table header
            datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on sign table : "+e.toString());
        }
        return datatable;
    }
    
    
    public static Table createSummSalary(String sumSalary,String sumOvertime,String sumEksAllowance, String sumMealAllowance, String total,
            String sumKasbon, String sumLeaveDeduction,String sumOther,String sumLateDeduc, String sumLateSatpamDeduc, String sumSukaDukaDeduc,
            String finalTotal,String totalTransfered,String nonTransfered, Font font1,Font font2)

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
     /*       
             // row 1 cols 1-3
            Cell placeCell = new Cell(new Chunk("Denpasar,"+Formater.formatDate(new Date(),"dd-MMM-yyyy"), font1));
            placeCell.setColspan(3);
            placeCell.setBorderColor(new Color(255, 255, 255));
            placeCell.setBorder(Rectangle.TOP);
            placeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            placeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(placeCell);
            
            // row 1 cols 4-5
            placeCell = new Cell(new Chunk("Cashier", font1));
            placeCell.setColspan(2);
            placeCell.setBorderColor(new Color(255, 255, 255));
            placeCell.setBorder(Rectangle.TOP);
            placeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            placeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(placeCell);
            
             // row 1 cols 6-7
            placeCell = new Cell(new Chunk("General Manager", font1));
            placeCell.setColspan(2);
            placeCell.setBorderColor(new Color(255, 255, 255));
            placeCell.setBorder(Rectangle.TOP);
            placeCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            placeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(placeCell);
            
             
             // row 2-3 cols 1-7
            Cell spaceName = new Cell(new Chunk("", font1));
            spaceName.setColspan(7);
            spaceName.setRowspan(2);
            spaceName.setBorderColor(new Color(255, 255, 255));
            spaceName.setBorder(Rectangle.TOP);
            spaceName.setHorizontalAlignment(Element.ALIGN_LEFT);
            spaceName.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(spaceName);
            
            //row 4 cols 2-3
            Cell nameCell = new Cell(new Chunk(""+PstSystemProperty.getValueByName("ACC_NAME"), font1));
            nameCell.setColspan(3);
            nameCell.setBorderColor(new Color(255, 255, 255));
            nameCell.setBorder(Rectangle.TOP);
            nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(nameCell);
            
            // row 4 cols 4-5
            nameCell = new Cell(new Chunk(""+PstSystemProperty.getValueByName("KASIR"), font1));
            nameCell.setColspan(2);
            nameCell.setBorderColor(new Color(255, 255, 255));
            nameCell.setBorder(Rectangle.TOP);
            nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(nameCell);
            
             // row 4 cols 6-7
            nameCell = new Cell(new Chunk(""+PstSystemProperty.getValueByName("GM"), font1));
            nameCell.setColspan(2);
            nameCell.setBorderColor(new Color(255, 255, 255));
            nameCell.setBorder(Rectangle.TOP);
            nameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(nameCell);
            
       */     
            
             // row 5 cols 1
            Cell billCell = new Cell(new Chunk("", font1));
            billCell.setBorderColor(new Color(255, 255, 255));
            billCell.setBorder(Rectangle.TOP);
            billCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(billCell);
            
            // row 5 cols 2
           billCell = new Cell(new Chunk("TOTAL SALARY :", font1));
            billCell.setBorderColor(new Color(255, 255, 255));
            billCell.setBorder(Rectangle.TOP);
            billCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(billCell);
            
            // row 1 cols 3
            double pDbl=0.0;
            try{
               pDbl = Double.parseDouble(sumSalary);
            }catch(Exception exc){
                System.out.println(""+exc);
            }            
            
            // row 5 cols 3
            billCell = new Cell(new Chunk(""+Formater.formatNumber(pDbl,frmCurrency), font1));
            billCell.setBorderColor(new Color(255, 255, 255));
            billCell.setBorder(Rectangle.TOP);
            billCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(billCell);
            
             // row 5 cols 4
            billCell = new Cell(new Chunk("", font1));
            billCell.setBorderColor(new Color(255, 255, 255));
            billCell.setBorder(Rectangle.TOP);
            billCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(billCell);
            
             // row 5 cols 5
            billCell = new Cell(new Chunk("TOTAL TRANSFERED", font1));
            billCell.setBorderColor(new Color(255, 255, 255));
            billCell.setBorder(Rectangle.TOP);
            billCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(billCell);
            
            pDbl=0.0;
            try{
               pDbl = Double.parseDouble(totalTransfered);
            }catch(Exception exc){
                System.out.println(""+exc);
            }                        
            // row 5 cols 6
            billCell = new Cell(new Chunk(""+Formater.formatNumber(pDbl,frmCurrency), font1));
            billCell.setBorderColor(new Color(255, 255, 255));
            billCell.setBorder(Rectangle.TOP);
            billCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(billCell);
            
             // row 5 cols 7
            billCell = new Cell(new Chunk("", font1));
            billCell.setBorderColor(new Color(255, 255, 255));
            billCell.setBorder(Rectangle.TOP);
            billCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(billCell);
            
            // row 6 cols 1
            Cell ovtCell = new Cell(new Chunk("", font1));
            ovtCell.setBorderColor(new Color(255, 255, 255));
            ovtCell.setBorder(Rectangle.TOP);
            ovtCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            ovtCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(ovtCell);
            
            // row 6 cols 2
            ovtCell = new Cell(new Chunk("TOTAL OVERTIME", font1));
            ovtCell.setBorderColor(new Color(255, 255, 255));
            ovtCell.setBorder(Rectangle.TOP);
            ovtCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            ovtCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(ovtCell);
            
             
            pDbl=0.0;
            try{
               pDbl = Double.parseDouble(sumOvertime);
            }catch(Exception exc){
                System.out.println(""+exc);
            }            
            // row 6 cols 3
            billCell = new Cell(new Chunk(""+Formater.formatNumber(pDbl,frmCurrency), font1));
            billCell.setBorderColor(new Color(255, 255, 255));
            billCell.setBorder(Rectangle.TOP);
            billCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(billCell);
            
            // row 6 cols 4
            ovtCell = new Cell(new Chunk("", font1));
            ovtCell.setBorderColor(new Color(255, 255, 255));
            ovtCell.setBorder(Rectangle.TOP);
            ovtCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            ovtCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(ovtCell);
            
            // row 6 cols 5
            ovtCell = new Cell(new Chunk("TOTAL NON TRANSFERED", font1));
            ovtCell.setBorderColor(new Color(255, 255, 255));
            ovtCell.setBorder(Rectangle.TOP);
            ovtCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            ovtCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(ovtCell);
            
             // row 6 cols 6
            pDbl=0.0;
            try{
               pDbl = Double.parseDouble(nonTransfered);
            }catch(Exception exc){
                System.out.println(""+exc);
            }            
            billCell = new Cell(new Chunk(""+Formater.formatNumber(pDbl,frmCurrency), font1));
            billCell.setBorderColor(new Color(255, 255, 255));
            billCell.setBorder(Rectangle.TOP);
            billCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            billCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(billCell);
            
             // row 6 cols 7
            ovtCell = new Cell(new Chunk("", font1));
            ovtCell.setBorderColor(new Color(255, 255, 255));
            ovtCell.setBorder(Rectangle.TOP);
            ovtCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            ovtCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(ovtCell);
            
            
             // row 7 cols 1
            Cell eksAll = new Cell(new Chunk("", font1));
            eksAll.setBorderColor(new Color(255, 255, 255));
            eksAll.setBorder(Rectangle.TOP);
            eksAll.setHorizontalAlignment(Element.ALIGN_LEFT);
            eksAll.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(eksAll);
            
            // row 7 cols 2
            eksAll = new Cell(new Chunk("EKSPOR ALLOWANCE", font1));
            eksAll.setBorderColor(new Color(255, 255, 255));
            eksAll.setBorder(Rectangle.TOP);
            eksAll.setHorizontalAlignment(Element.ALIGN_LEFT);
            eksAll.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(eksAll);
            
             // row 7 cols 3
            pDbl=0.0;
            try{
               pDbl = Double.parseDouble(sumEksAllowance);
            }catch(Exception exc){
                System.out.println(""+exc);
            }            
            
            eksAll = new Cell(new Chunk(""+Formater.formatNumber(pDbl,frmCurrency), font1));
            eksAll.setBorderColor(new Color(255, 255, 255));
            eksAll.setBorder(Rectangle.TOP);
            eksAll.setHorizontalAlignment(Element.ALIGN_RIGHT);
            eksAll.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(eksAll);
            
            // row 7 cols 4
            eksAll = new Cell(new Chunk("", font1));
            eksAll.setBorderColor(new Color(255, 255, 255));
            eksAll.setBorder(Rectangle.TOP);
            eksAll.setHorizontalAlignment(Element.ALIGN_LEFT);
            eksAll.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(eksAll);
            
            // row 7 cols 5
            eksAll = new Cell(new Chunk("", font1));
            eksAll.setBorderColor(new Color(255, 255, 255));
            eksAll.setBorder(Rectangle.TOP);
            eksAll.setHorizontalAlignment(Element.ALIGN_LEFT);
            eksAll.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(eksAll);
            
             // row 7 cols 6
            eksAll = new Cell(new Chunk("", font1));
            eksAll.setBorderColor(new Color(255, 255, 255));
            eksAll.setBorder(Rectangle.TOP);
            eksAll.setHorizontalAlignment(Element.ALIGN_RIGHT);
            eksAll.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(eksAll);
            
             // row 7 cols 7
            eksAll = new Cell(new Chunk("", font1));
            eksAll.setBorderColor(new Color(255, 255, 255));
            eksAll.setBorder(Rectangle.TOP);
            eksAll.setHorizontalAlignment(Element.ALIGN_LEFT);
            eksAll.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(eksAll);
            

             // row 4 cols 1
            Cell mealAll = new Cell(new Chunk("", font1));
            mealAll.setBorderColor(new Color(255, 255, 255));
            mealAll.setBorder(Rectangle.TOP);
            mealAll.setHorizontalAlignment(Element.ALIGN_LEFT);
            mealAll.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(mealAll);
            
            // row 4 cols 2
            mealAll = new Cell(new Chunk("MEAL ALLOWANCE", font1));
            mealAll.setBorderColor(new Color(255, 255, 255));
            mealAll.setBorder(Rectangle.TOP);
            mealAll.setHorizontalAlignment(Element.ALIGN_LEFT);
            mealAll.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(mealAll);
            
             // row 4 cols 3
            mealAll = new Cell(new Chunk(""+Formater.formatNumber(Double.parseDouble(sumMealAllowance),frmCurrency), font1));
            mealAll.setBorderColor(new Color(255, 255, 255));
            mealAll.setBorder(Rectangle.TOP);
            mealAll.setHorizontalAlignment(Element.ALIGN_RIGHT);
            mealAll.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(mealAll);
            
            // row 4 cols 4
            mealAll = new Cell(new Chunk("", font1));
            mealAll.setBorderColor(new Color(255, 255, 255));
            mealAll.setBorder(Rectangle.TOP);
            mealAll.setHorizontalAlignment(Element.ALIGN_LEFT);
            mealAll.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(mealAll);
            
            // row 4 cols 5
            mealAll = new Cell(new Chunk("", font1));
            mealAll.setBorderColor(new Color(255, 255, 255));
            mealAll.setBorder(Rectangle.TOP);
            mealAll.setHorizontalAlignment(Element.ALIGN_LEFT);
            mealAll.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(mealAll);
            
             // row 4 cols 6
            mealAll = new Cell(new Chunk("", font1));
            mealAll.setBorderColor(new Color(255, 255, 255));
            mealAll.setBorder(Rectangle.TOP);
            mealAll.setHorizontalAlignment(Element.ALIGN_RIGHT);
            mealAll.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(mealAll);
            
             // row 4 cols 7
            mealAll = new Cell(new Chunk("", font1));
            mealAll.setBorderColor(new Color(255, 255, 255));
            mealAll.setBorder(Rectangle.TOP);
            mealAll.setHorizontalAlignment(Element.ALIGN_LEFT);
            mealAll.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(mealAll);
            
            
             // row 5 cols 1
            Cell totalCell = new Cell(new Chunk("", font2));
            totalCell.setBorderColor(new Color(255, 255, 255));
            totalCell.setBorder(Rectangle.TOP);
            totalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(totalCell);
            
            // row 5 cols 2
            totalCell = new Cell(new Chunk("TOTAL", font2));
            totalCell.setBorderColor(new Color(255, 255, 255));
            totalCell.setBorder(Rectangle.TOP);
            totalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(totalCell);
            
             // row 5 cols 3
            
            totalCell = new Cell(new Chunk(""+Formater.formatNumber(Double.parseDouble(total),frmCurrency), font2));
            totalCell.setBorderColor(new Color(255, 255, 255));
            totalCell.setBorder(Rectangle.TOP);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(totalCell);
            
            // row 5 cols 4
            totalCell = new Cell(new Chunk("", font2));
            totalCell.setBorderColor(new Color(255, 255, 255));
            totalCell.setBorder(Rectangle.TOP);
            totalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(totalCell);
            
            // row 5 cols 5
            totalCell = new Cell(new Chunk("", font2));
            totalCell.setBorderColor(new Color(255, 255, 255));
            totalCell.setBorder(Rectangle.TOP);
            totalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(totalCell);
            
             // row 5 cols 6
            totalCell = new Cell(new Chunk("", font2));
            totalCell.setBorderColor(new Color(255, 255, 255));
            totalCell.setBorder(Rectangle.TOP);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(totalCell);
            
             // row 5 cols 7
            totalCell = new Cell(new Chunk("", font2));
            totalCell.setBorderColor(new Color(255, 255, 255));
            totalCell.setBorder(Rectangle.TOP);
            totalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(totalCell);
            
            // row 6 cols 1.2.3.4,5,6,7
            Cell space = new Cell(new Chunk("", font2));
            space.setColspan(7);
            space.setBorderColor(new Color(255, 255, 255));
            space.setBorder(Rectangle.TOP);
            space.setHorizontalAlignment(Element.ALIGN_LEFT);
            space.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(space);


            String[] dataTitle = {"KASBON","LEAVE DEDUCTION","OTHER DEDUCTION","TOTAL LATE DEDUC.","TOTAL LATE SATPAM DEDUC.",
                     "TOTAL SUKA DUKA DEDUC.","TOTAL"};
            String[] dataValue = {sumKasbon, sumLeaveDeduction,sumOther, sumLateDeduc,  sumLateSatpamDeduc,  sumSukaDukaDeduc,
            finalTotal};
            
            // row 6 cols 1
            for(int dt=0;dt<dataTitle.length;dt++){
            Cell kasbonCell = new Cell(new Chunk("", font1));
            kasbonCell.setBorderColor(new Color(255, 255, 255));
            kasbonCell.setBorder(Rectangle.TOP);
            kasbonCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            kasbonCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(kasbonCell);
            
            // row 6 cols 2
            kasbonCell = new Cell(new Chunk(dataTitle[dt], font1));
            kasbonCell.setBorderColor(new Color(255, 255, 255));
            kasbonCell.setBorder(Rectangle.TOP);
            kasbonCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            kasbonCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(kasbonCell);
            
             // row 5 cols 3
            pDbl=0.0;
            try{
               pDbl = Double.parseDouble(dataValue[dt]);
            }catch(Exception exc){
                System.out.println(""+exc);
            }                        
            kasbonCell = new Cell(new Chunk(""+Formater.formatNumber(pDbl,frmCurrency), font1));
            kasbonCell.setBorderColor(new Color(255, 255, 255));
            kasbonCell.setBorder(Rectangle.TOP);
            kasbonCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            kasbonCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(kasbonCell);
            
            // row 5 cols 4,5,6,7
            kasbonCell = new Cell(new Chunk("", font2));
            kasbonCell.setColspan(4);
            kasbonCell.setBorderColor(new Color(255, 255, 255));
            kasbonCell.setBorder(Rectangle.TOP);
            kasbonCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            kasbonCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(kasbonCell);
            }
            
                                    
/*            
            // row 6 cols 1
            Cell kasbonCell = new Cell(new Chunk("", font1));
            kasbonCell.setBorderColor(new Color(255, 255, 255));
            kasbonCell.setBorder(Rectangle.TOP);
            kasbonCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            kasbonCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(kasbonCell);
            
            // row 6 cols 2
            kasbonCell = new Cell(new Chunk("KASBON", font1));
            kasbonCell.setBorderColor(new Color(255, 255, 255));
            kasbonCell.setBorder(Rectangle.TOP);
            kasbonCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            kasbonCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(kasbonCell);
            
             // row 5 cols 3
            pDbl=0.0;
            try{
               pDbl = Double.parseDouble(sumKasbon);
            }catch(Exception exc){
                System.out.println(""+exc);
            }                        
            kasbonCell = new Cell(new Chunk(""+Formater.formatNumber(pDbl,frmCurrency), font1));
            kasbonCell.setBorderColor(new Color(255, 255, 255));
            kasbonCell.setBorder(Rectangle.TOP);
            kasbonCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            kasbonCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(kasbonCell);
            
            // row 5 cols 4,5,6,7
            kasbonCell = new Cell(new Chunk("", font2));
            kasbonCell.setColspan(4);
            kasbonCell.setBorderColor(new Color(255, 255, 255));
            kasbonCell.setBorder(Rectangle.TOP);
            kasbonCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            kasbonCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(kasbonCell);
            
            // row 5 cols 1
            Cell leaveCell = new Cell(new Chunk("", font1));
            leaveCell.setBorderColor(new Color(255, 255, 255));
            leaveCell.setBorder(Rectangle.TOP);
            leaveCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            leaveCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(leaveCell);
            
            // row 5 cols 2
            leaveCell = new Cell(new Chunk("LEAVE DEDUCTION", font1));
            leaveCell.setBorderColor(new Color(255, 255, 255));
            leaveCell.setBorder(Rectangle.TOP);
            leaveCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            leaveCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(leaveCell);
            
             // row 5 cols 3
            pDbl=0.0;
            try{
               pDbl = Double.parseDouble(sumLeaveDeduction);
            }catch(Exception exc){
                System.out.println(""+exc);
            }                        
            
            leaveCell = new Cell(new Chunk(""+Formater.formatNumber(pDbl,frmCurrency), font1));
            leaveCell.setBorderColor(new Color(255, 255, 255));
            leaveCell.setBorder(Rectangle.TOP);
            leaveCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            leaveCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(leaveCell);
            
            // row 5 cols 4,5,6,7
            leaveCell = new Cell(new Chunk("", font2));
            leaveCell.setColspan(4);
            leaveCell.setBorderColor(new Color(255, 255, 255));
            leaveCell.setBorder(Rectangle.TOP);
            leaveCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            leaveCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(leaveCell);
            
            
            
            // row 5 cols 1
            Cell otherCell = new Cell(new Chunk("", font1));
            otherCell.setBorderColor(new Color(255, 255, 255));
            otherCell.setBorder(Rectangle.TOP);
            otherCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            otherCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(otherCell);
            
            // row 5 cols 2
            otherCell = new Cell(new Chunk("OTHERS DEDUCTION", font1));
            otherCell.setBorderColor(new Color(255, 255, 255));
            otherCell.setBorder(Rectangle.TOP);
            otherCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            otherCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(otherCell);
            
             // row 5 cols 3
            pDbl=0.0;
            try{
               pDbl = Double.parseDouble(sumOther);
            }catch(Exception exc){
                System.out.println(""+exc);
            }             
            otherCell = new Cell(new Chunk(""+Formater.formatNumber(pDbl,frmCurrency), font1));
            otherCell.setBorderColor(new Color(255, 255, 255));
            otherCell.setBorder(Rectangle.TOP);
            otherCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            otherCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(otherCell);
            
            // row 5 cols 4,5,6,7
            otherCell = new Cell(new Chunk("", font2));
            otherCell.setColspan(4);
            otherCell.setBorderColor(new Color(255, 255, 255));
            otherCell.setBorder(Rectangle.TOP);
            otherCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            otherCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(otherCell);

             // row 5 cols 1
            Cell deducCell = new Cell(new Chunk("", font1));
            deducCell.setBorderColor(new Color(255, 255, 255));
            deducCell.setBorder(Rectangle.TOP);
            deducCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            deducCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(deducCell);
            
            // row 5 cols 2
            deducCell = new Cell(new Chunk("TOTAL LATE DEDUC.", font1));
            deducCell.setBorderColor(new Color(255, 255, 255));
            deducCell.setBorder(Rectangle.TOP);
            deducCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            deducCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(deducCell);
            
             // row 5 cols 3
            pDbl=0.0;
            try{
               pDbl = Double.parseDouble(sumLateDeduc);
            }catch(Exception exc){
                System.out.println(""+exc);
            }                        
            //sumLateDeduc, String sumLateSatpamDeduc, String sumSukaDukaDeduc,
            
            deducCell = new Cell(new Chunk(""+Formater.formatNumber(pDbl,frmCurrency), font1));
            deducCell.setBorderColor(new Color(255, 255, 255));
            deducCell.setBorder(Rectangle.TOP);
            deducCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            deducCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(deducCell);
            
            // row 5 cols 4,5,6,7
            deducCell = new Cell(new Chunk("", font2));
            deducCell.setColspan(4);
            deducCell.setBorderColor(new Color(255, 255, 255));
            deducCell.setBorder(Rectangle.TOP);
            deducCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            deducCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(deducCell);
                         
             // row 5 cols 1
            Cell space2 = new Cell(new Chunk("", font1));
            space2.setColspan(7);
            space2.setBorderColor(new Color(255, 255, 255));
            space2.setBorder(Rectangle.TOP);
            space2.setHorizontalAlignment(Element.ALIGN_LEFT);
            space2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(space2);
            
             // row 5 cols 1
            Cell finalCell = new Cell(new Chunk("", font1));
            finalCell.setBorderColor(new Color(255, 255, 255));
            finalCell.setBorder(Rectangle.TOP);
            finalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            finalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(finalCell);
            
            // row 5 cols 2
            finalCell = new Cell(new Chunk("TOTAL", font2));
            finalCell.setBorderColor(new Color(255, 255, 255));
            finalCell.setBorder(Rectangle.TOP);
            finalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            finalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(finalCell);
            
             // row 5 cols 3
            pDbl=0.0;
            try{
               pDbl = Double.parseDouble(finalTotal);
            }catch(Exception exc){
                System.out.println(""+exc);
            }             
            
            finalCell = new Cell(new Chunk(""+Formater.formatNumber(pDbl,frmCurrency), font2));
            finalCell.setBorderColor(new Color(255, 255, 255));
            finalCell.setBorder(Rectangle.TOP);
            finalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            finalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(finalCell);
            
            // row 5 cols 4,5,6,7
            finalCell = new Cell(new Chunk("", font2));
            finalCell.setColspan(4);
            finalCell.setBorderColor(new Color(255, 255, 255));
            finalCell.setBorder(Rectangle.TOP);
            finalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            finalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            datatable.addCell(finalCell);            
 */
        
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
