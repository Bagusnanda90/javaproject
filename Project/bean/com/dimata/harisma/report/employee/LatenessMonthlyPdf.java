package com.dimata.harisma.report.employee;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import com.dimata.util.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.locker.*;
import com.dimata.harisma.session.employee.*;

public class LatenessMonthlyPdf extends HttpServlet {
    // public static final membervariables

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
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 7, Font.NORMAL, blackColor);

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
            HttpSession sessEmpLatness = request.getSession(true);
            Vector vctMonthlyLatness = null;
            try{
	        vctMonthlyLatness = (Vector)sessEmpLatness.getValue("MONTHLY_LATENESS");
            }catch(Exception e){
                System.out.println("Exc : "+e.toString());
            }

            //Date presence = new Date();
            //String latness = "";
            Department department = new Department();
            com.dimata.harisma.entity.masterdata.Section section = new  com.dimata.harisma.entity.masterdata.Section();
            Vector listLatness = new Vector(1,1);
            int intDateOfMonth = 0;
            int startDatePeriod = 0;
            String empNum="";
            String fullName="";
            String sPeriod="";
            if(vctMonthlyLatness != null && vctMonthlyLatness.size() > 0){
                try{
	            	//latness = (String) vctMonthlyLatness.get(0);
                         if(vctMonthlyLatness.get(3)!=null){
	                long oidDepartment = Long.parseLong((String)vctMonthlyLatness.get(3));
                            if(oidDepartment !=0){
                                department = (Department)PstDepartment.fetchExc(oidDepartment);
                            }
                        }
                         if(vctMonthlyLatness.get(4)!=null){
	                long oidSection = Long.parseLong((String)vctMonthlyLatness.get(4));
                            if(oidSection !=0){
                                section = (com.dimata.harisma.entity.masterdata.Section)PstSection.fetchExc(oidSection);
                            }
                        }
                         if(vctMonthlyLatness.get(5)!=null){
                            sPeriod = (String) vctMonthlyLatness.get(5);
                         }
                         if(vctMonthlyLatness.get(0)!=null){
	                listLatness = (Vector)vctMonthlyLatness.get(0);
                         }
                         if(vctMonthlyLatness.get(6)!=null){
                        intDateOfMonth = Integer.parseInt(String.valueOf(vctMonthlyLatness.get(6)));
                         }
                          if(vctMonthlyLatness.get(7)!=null){
	                startDatePeriod = Integer.parseInt((String)vctMonthlyLatness.get(7));
                        }
                }
                catch(Exception e)
                {
                 	System.out.println("exc on get List vctMonthlyPresence : "+e.toString());
                }
            }               
            
            /* adding a Header of page, i.e. : title, align and etc */
            String departementName=null;
            if(department == null || department.getDepartment() == null && department.getDepartment().length() > 0 || department.getDepartment()==""){
               departementName="ALL";
            }else{
                 departementName=department.getDepartment();
            }
            String sectionName=null;
            if(section == null || section.getSection() == null && section.getSection().length() > 0 || section.getSection() =="-" || section.getSection() == ""){
               sectionName="ALL";
            }else{
                 sectionName=section.getSection();
            }
           /* HeaderFooter header = new HeaderFooter(new Phrase("PRESENCE REPORT PER " + presence.toUpperCase() +
                				  "\nDEPARTMENT : "+ department.getDepartment().toUpperCase(), fontHeader), false);

            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);*/
             String strReportTitle = //"ATTENDANCE RECORD : " + (Formater.formatDate(presence,"dd MMMM yyyy")).toUpperCase() +
                                    "LATNESS REPORT PER : " + sPeriod.toUpperCase() +
                                    "\nDEPARTMENT : "+ departementName.toUpperCase()+
                                    "\nSECTION : "+sectionName;
                                    // "\nSECTION : \n"+sec;                        
            HeaderFooter header = new HeaderFooter(new Phrase(strReportTitle, fontHeader), false);
            
            

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
             
            if(listLatness!=null && listLatness.size()>0)
            {
                for (int i = 0; i < listLatness.size(); i++) 
                {          
                    Vector itemPresence = (Vector)listLatness.get(i);

                    // no, payroll and name
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemPresence.get(1)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(itemPresence.get(2)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(itemPresence.get(3)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);          
                        
                    // list of schedule and duration, dimulai dari index ke 3
                    for(int isch=4; isch<itemPresence.size()-1; isch++)
                    {
                        cellEmpData = new Cell(new Chunk(String.valueOf(itemPresence.get(isch)),fontContent));
                        cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                        cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellEmpData.setBackgroundColor(whiteColor);
                        cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableEmpPresence.addCell(cellEmpData);          
                    }

                    // total
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemPresence.get(itemPresence.size()-1)),fontContent));
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
            datatable.addCell(new Phrase("Presence Duration (hours, minutes)", font1));                        
            
            // data total presence
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
            datatable.addCell(new Phrase("Latness Duration (hours, minutes)", font1));                        
            
            // data total presence
            datatable.setDefaultRowspan(2);
            datatable.setDefaultColspan(1);
            datatable.setBackgroundColor(summaryColor);                
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.addCell(new Phrase("Total Late", font1));            

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
//    public void doPost(HttpServletRequest request, HttpServletResponse response)
//    throws IOException, ServletException {
//    }
//
//    public static Table createHeaderDetail(Font font1, Date dt,int maxDateMonth, Date toDt)
//    {        
//        Table datatable = null;
//        try{
//            int max = maxDateMonth;
//            int startDatePeriod = dt.getDate();
//            //float headerwidths[] = new float[4+(max+1)];
//            float headerwidths[] = new float[3+(max+1)]; // karena payrollnya dihilangkan
//            headerwidths[0] = 2.5f;
//            //headerwidths[1] = 5;
//            headerwidths[1] = 14;
//            //int index = 3;
//            int index = 2;
//            for(int k=0;k<=max;k++)
//            {
//                headerwidths[index] = 3;
//                index++;
//            }
//            headerwidths[index] = 3;
//
//            // create table
//            //datatable = new Table(4+(max+1));
//            datatable = new Table(3+(max+1));
//            datatable.setPadding(1);
//            datatable.setSpacing(2);
//            datatable.setWidths(headerwidths);
//            datatable.setWidth(100);
//            datatable.setDefaultRowspan(2);
//            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
//            datatable.setDefaultCellBackgroundColor(summaryColor);
//            datatable.addCell(new Phrase("No", font1));
//            
//            datatable.addCell(new Phrase("Payroll", font1));
//            datatable.addCell(new Phrase("Employee", font1));
//
//            datatable.setDefaultColspan(max);
//            datatable.setDefaultRowspan(1);
//            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
//            datatable.addCell(new Phrase("Duration (hour, minutes)", font1));
//
//            datatable.setDefaultRowspan(2);
//            datatable.setDefaultColspan(2);
//            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
//            datatable.addCell(new Phrase("Total", font1));
//
//            // untuk ngisi pertama
//            datatable.setDefaultColspan(1);
//            datatable.setDefaultRowspan(1);
//            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
//            datatable.addCell(new Phrase(""+startDatePeriod, font1));
//         
//           for(int k=0;k<maxDateMonth-1;k++){
//                
//                if(startDatePeriod==maxDateMonth){
//                    startDatePeriod = 1;
//                    datatable.setDefaultColspan(1);
//                    datatable.setDefaultRowspan(1);
//                    datatable.setBackgroundColor(summaryColor);                
//                    datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
//                    datatable.addCell(new Phrase(""+(startDatePeriod), font1)); 
//                }
//                else {
//                    startDatePeriod = startDatePeriod + 1;
//                    datatable.setDefaultColspan(1);
//                    datatable.setDefaultRowspan(1);
//                    datatable.setBackgroundColor(summaryColor);                
//                    datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
//                    datatable.addCell(new Phrase(""+(startDatePeriod), font1)); 
//                    
//                }              
//            }
//             
//           
//            
//            // this is the end of the table header
//            datatable.endHeaders();
//        }catch(Exception e){}
//        return datatable;
//    }
//
//    public void doGet(HttpServletRequest request, HttpServletResponse response)
//    throws IOException, ServletException {
//            //System.out.println("= = = | Employee Monthly Lateness | = = =");
//
//        HttpSession session = request.getSession(true);
//        Vector temp = (Vector)session.getValue("MONTHLY_LATENESS");
//        PdfWriter writer = null;
//
//        // creating some content that will be used frequently
//        Paragraph newLine = new Paragraph("\n", fontTitle);
//        // step1: creating the document object
//        Document document = new Document(PageSize.A4.rotate(), 30, 30, 50, 50);
//        
//        // step2.1: creating an OutputStream
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        
//        try {
//            // step2.2: creating an instance of the writer
//            writer = PdfWriter.getInstance(document, baos);
//            // step 3.1: adding some metadata to the document
//            document.addSubject("This is a report.");
//            // step 3.4: opening the document
//
//            // create header
//            Vector list = (Vector)temp.get(3);
//            long oidDept = 0;
//            if(Long.parseLong((String)temp.get(0))==0){
//                for (int i = 0; i < list.size(); i++) {
//                    Vector tmp = (Vector)list.get(i);
//                    oidDept = Long.parseLong((String)tmp.get(0));
//                    break;
//                }
//            }else{
//                oidDept = Long.parseLong((String)temp.get(0));
//            }
//            Date startDate = (Date)temp.get(1);
//            String periodName = (String)temp.get(4);
//            int maxDateMonth = Integer.parseInt(String.valueOf(temp.get(5)));
//           // System.out.println("maxDateMonth   "+maxDateMonth);
//            Department dep = new Department();
//            try{
//                dep = PstDepartment.fetchExc(oidDept);
//            }catch(Exception e){}
//            /* adding a Header of page, i.e. : title, align and etc */
//            HeaderFooter header = new HeaderFooter(new Phrase("LATENESS REPORT PER " + periodName.toUpperCase() +
//                				  "\nDEPARTMENT : "+ dep.getDepartment().toUpperCase(), fontHeader), false);
//
//            header.setAlignment(Element.ALIGN_LEFT);
//            header.setBorder(Rectangle.BOTTOM);
//            header.setBorderColor(blackColor);
//            document.setHeader(header);
//            document.open();
//
//            Date endDate = (Date)temp.get(2);
//            Table datatable = createHeaderDetail(fontContent,startDate,maxDateMonth,endDate);
//            datatable.setDefaultRowspan(1);
//            datatable.setDefaultCellBackgroundColor(whiteColor);
//
//            //System.out.println("= = >> CREATE DATA DETAIL ...");
//            long oid = 0;
//            boolean newpage = false;
//            Cell cellEmpData = null;
//            for (int i = 0; i < list.size(); i++) {
//                Vector tmp = (Vector)list.get(i);
//                long oidDep = Long.parseLong((String)tmp.get(0));
//                if(i==0)
//                    oid = oidDep;
//                      
//                
//                
//                /*if(oidDep!=oid){
//                    document.add(datatable);
//                    oid = oidDep;
//                    document.newPage();
//                    document.add(createHeader(temp,font1,oidDep));
//                    datatable = createHeaderDetail(font2a,startDate,endDate);
//                    datatable.setDefaultCellBorderWidth(1);
//                    datatable.setDefaultRowspan(1);
//                }*/
//                datatable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
//                System.out.println("vect tmp  "+tmp);
//                if(oidDep!=-1){
//                    
//                    cellEmpData = new Cell(new Chunk(String.valueOf(tmp.get(1)),fontContent));
//                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
//                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
//                    cellEmpData.setBackgroundColor(whiteColor);
//                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
//                    datatable.addCell(cellEmpData);
//
//                    
//                    cellEmpData = new Cell(new Chunk(String.valueOf(tmp.get(2)),fontContent));
//                   /* cellEmpData = new Cell(new Chunk("",fontContent));*/
//                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
//                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
//                    cellEmpData.setBackgroundColor(whiteColor);
//                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
//                    datatable.addCell(cellEmpData);   
//                    
//                    int startDatePeriod = startDate.getDate();
//                    cellEmpData = new Cell(new Chunk(String.valueOf(tmp.get(3)),fontContent));
//                   // cellEmpData = new Cell(new Chunk("",fontContent));
//                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
//                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
//                    cellEmpData.setBackgroundColor(whiteColor);
//                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
//                    datatable.addCell(cellEmpData);
//                  // System.out.println("get 1  "+String.valueOf(tmp.get(1)));
//                }else{
//                    //datatable.setDefaultColspan(3);
//                    cellEmpData = new Cell(new Chunk(String.valueOf(tmp.get(3)),fontContent));
//                    //cellEmpData = new Cell(new Chunk("",fontContent));
//                    //cellEmpData.setColspan(3);
//                    cellEmpData.setColspan(2);
//                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
//                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
//                    cellEmpData.setBackgroundColor(whiteColor);
//                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
//                    datatable.addCell(cellEmpData);
//                    //datatable.setDefaultColspan(1);
//                   // System.out.println("get 3  "+String.valueOf(tmp.get(3)));
//                }
//
//                int idx = 4; 
//                int maxKolom = maxDateMonth + idx; // untuk array dimulai dari 4 sampai maxDateMonth
//                for(int k=idx;k<maxKolom;k++){
//                    //if()
//                   cellEmpData = new Cell(new Chunk(String.valueOf(tmp.get(idx)),fontContent));
//                    //cellEmpData = new Cell(new Chunk("",fontContent));
//                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
//                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
//                    cellEmpData.setBackgroundColor(whiteColor);
//                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
//                    datatable.addCell(cellEmpData);
//                    //datatable.addCell(new Phrase((String)tmp.get(idx), fontContent));
//                    idx++;
//                }
//                
//                //cellEmpData = new Cell(new Chunk(String.valueOf(tmp.get(idx)),fontContent));
//                
//                /*cellEmpData = new Cell(new Chunk(String.valueOf(tmp.get(idx)),fontContent));
//               // cellEmpData = new Cell(new Chunk("",fontContent));
//                cellEmpData.setColspan(2);
//                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
//                cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
//                cellEmpData.setBackgroundColor(whiteColor);
//                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
//                datatable.addCell(cellEmpData);
//                //datatable.addCell(new Phrase((String)tmp.get(idx), fontContent));
//                */
//                if(newpage == true) {
//                    newpage = false;
//                    //document.add(datatable);
//                }
//                
//                if (!writer.fitsPage(datatable)) {
//                    datatable.deleteLastRow();
//                    i--;
//                    document.add(datatable);
//                    newpage = document.newPage();
//                    datatable = createHeaderDetail(fontContent,startDate,maxDateMonth,endDate);
//                    //document.add(datatable);                    
//                }
//            }
//            
//            if(!newpage)
//                document.add(datatable);
//        }
//        catch(DocumentException de) {
//            System.err.println(de.getMessage());
//            de.printStackTrace();
//        }
//
//        // step 5: closing the document
//        document.close();
//        
//        // we have written the pdfstream to a ByteArrayOutputStream,
//        // now we are going to write this outputStream to the ServletOutputStream
//        // after we have set the contentlength (see http://www.lowagie.com/iText/faq.html#msie)
//        response.setContentType("application/pdf");
//        response.setContentLength(baos.size());
//        ServletOutputStream out = response.getOutputStream();
//        baos.writeTo(out);
//        out.flush();
//    }
}