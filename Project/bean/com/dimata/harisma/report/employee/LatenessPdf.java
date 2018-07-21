package com.dimata.harisma.report.employee;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.Date;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.util.Formater;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

public class LatenessPdf extends HttpServlet {
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
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {


        /* creating the document object */
        Document document = new Document(PageSize.A4, 30, 30, 50, 50);

        /* creating an OutputStream */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            /* creating an instance of the writer */
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            /* get data from session */
            HttpSession sessLatness = request.getSession(true);
            Vector vctDailyLatness = null;
            try
            {
                vctDailyLatness = (Vector)sessLatness.getValue("LATENESS");
            }
            catch(Exception e)
            {
                System.out.println("Exc : "+e.toString());
            }

            Date presenceDateFrom = new Date();
            Date presenceDateTo = new Date();
            Department department = null;
             com.dimata.harisma.entity.masterdata.Section section = null; 
          /*  if ( department !=null || department.getDepartment().equals("") ) {
			department.setDepartment("-"); 
		} 
            */
            
            Vector listLatness = new Vector(1,1);
            //Vector secSelect = new Vector(1,1);
            long oidSection = 0;
          //  Vector listStruktur = new Vector(1,1);
            if(vctDailyLatness != null && vctDailyLatness.size()>0)
                // if(vctDailyPresence != null && vctDailyPresence.size()==7)
            {
                try
                {
	            	presenceDateFrom = (Date) vctDailyLatness.get(2);
                        presenceDateTo = (Date) vctDailyLatness.get(3);
	              if(vctDailyLatness.get(4) !=null){
                        long oidDepartment = Long.parseLong((String)vctDailyLatness.get(4));
                        if(oidDepartment !=0){
	                department = (Department)PstDepartment.fetchExc(oidDepartment);
                        }
                      }  
                        if(vctDailyLatness.get(7) !=null){
                        oidSection = Long.parseLong((String)vctDailyLatness.get(7));
                        if(oidSection !=0){
                            section = (com.dimata.harisma.entity.masterdata.Section)PstSection.fetchExc(oidSection);
                        }
                      }
                       
                        //update by satrya 2012-07-24
                        String fullName = (String) vctDailyLatness.get(5);
                        
                        String empNum = (String) vctDailyLatness.get(6);
	                listLatness = (Vector)vctDailyLatness.get(1);
                       // secSelect = (Vector)vctDailyPresence.get(6);
                        //listStruktur = (Vector) vctDailyPresence.get(7);
                }
                catch(Exception e)
                {
                 	System.out.println("get List : "+e.toString());
                }
            }
            
            
       /*   String departement = "-";
          String company = "-";
          String division = "-";*/
          // Department objDepartment = null;
          /*  if(listStruktur !=null && listStruktur.size()>0){
                for(int d=0; d < listStruktur.size(); d++){
                     StrukturEmployee strukturEmployee = (StrukturEmployee)listStruktur.get(d);
                    company = strukturEmployee.getCompany();
                    division = strukturEmployee.getDivision();
                    departement = strukturEmployee.getDepartment();
                }
            }*/
            
            /* adding a Header of page, i.e. : title, align and etc */
             String departementName=null;
            if(department==null||department.getDepartment() == null && department.getDepartment().length() > 0 || department.getDepartment() =="-"){
               departementName="ALL";
            }else{
                 departementName=department.getDepartment();
            }
            String sectionName=null;
            if(section == null || section.getSection() == null && section.getSection().length() > 0 || section.getSection() =="-"){
               sectionName="ALL";
            }else{
                 sectionName=section.getSection();
            }
            String strReportTitle = //"ATTENDANCE RECORD : " + (Formater.formatDate(presence,"dd MMMM yyyy")).toUpperCase() +
                                    "LATNESS RECORD : " + (Formater.formatDate(presenceDateFrom,"dd MMMM yyyy")).toUpperCase() +" s/d "+ (Formater.formatDate(presenceDateTo,"dd MMMM yyyy")).toUpperCase() +
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
            Table tableEmpLate = getTableHeader();

            /* generate employee attendance report's data */
            Cell cellEmpData = new Cell("");
            
            if(listLatness!=null && listLatness.size()>0)
            {
                int maxListLate = listLatness.size();
                String instf = null;
                for(int i=0; i<maxListLate; i++)
                {
                    Vector vectTemp = (Vector)listLatness.get(i);
                    Date selectedDate = (Date)vectTemp.get(1); 
                    /* -------------- no , employee name & Tanggal  --------------*/
                   cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(0)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpLate.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(Formater.formatDate(selectedDate, "EE,dd/MM/yyyy"),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpLate.addCell(cellEmpData);
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(2)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpLate.addCell(cellEmpData);
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(3)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpLate.addCell(cellEmpData);

                    
                    /* -------------- schedule --------------*/
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(4)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpLate.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(5)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpLate.addCell(cellEmpData);
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(6)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpLate.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(7)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpLate.addCell(cellEmpData);

                    /* ------- end */
                    
                    if (!writer.fitsPage(tableEmpLate)) 
                    {
                        tableEmpLate.deleteLastRow();
                        i--;
                        document.add(tableEmpLate);
                        document.newPage();
                        tableEmpLate = getTableHeader();
                    }                                                            
                }
            }                       
            document.add(tableEmpLate);
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
    public static Table getTableHeader() throws BadElementException, DocumentException 
    {
           Table tableEmpPresence = new Table(8);
           tableEmpPresence.setCellpadding(1);
           tableEmpPresence.setCellspacing(1);
           tableEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
           //int widthHeader[] = {3,8,5,10,6,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2};
           int widthHeader[] = {3,7,4,10,5,10,10,10};
    	   tableEmpPresence.setWidths(widthHeader);
           tableEmpPresence.setWidth(100);

           Cell cellEmpPresence = new Cell(new Chunk("No.",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           //update by satrya 2012-08-18
           cellEmpPresence = new Cell(new Chunk("Date",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
          
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           //update by satrya 2012-09-06
           cellEmpPresence = new Cell(new Chunk("Payrol",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk("Employee",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk("Symbol",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setBackgroundColor(summaryColor);
       	   tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk("Schedule",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk("Actual",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk("Late",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence); 
           
           return tableEmpPresence;
    }


//    public static Table createHeaderDetail(Table datatable,Font font1){
//        try{
//            datatable.setPadding(1);
//            datatable.setSpacing(1);
//            int headerwidths[] = {
//                5,5, 10, 30, 10, 10
//                    // 5,5, 10, 30, 10, 10, 10 ,10
//            };
//            datatable.setWidths(headerwidths);
//            datatable.setWidth(100);
//            datatable.setDefaultRowspan(2);
//            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
//            datatable.setDefaultCellBackgroundColor(summaryColor);
//            datatable.addCell(new Phrase("NO", font1));
//            //update by satrya 2012-11-10
//            datatable.addCell(new Phrase("DATE", font1));
//            datatable.addCell(new Phrase("PAYROLL", font1));
//            datatable.addCell(new Phrase("EMPLOYEE", font1));
//            datatable.setDefaultColspan(1);
//            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
//            datatable.addCell(new Phrase("SYMBOL", font1));
//            datatable.addCell(new Phrase("SCHEDULE", font1));
//            datatable.addCell(new Phrase("ACTUAL", font1));
//            datatable.addCell(new Phrase("LATE", font1));
//            // this is the end of the table header
//            datatable.endHeaders();
//        }catch(Exception e){}
//        return datatable;
//    }
//
//    public void doGet(HttpServletRequest request, HttpServletResponse response)
//    throws IOException, ServletException {
//        System.out.println("===| Employee Daily Lateness |===");
//
//        HttpSession session = request.getSession(true);
//        Vector temp = (Vector)session.getValue("LATENESS");
//        // step1: creating the document object
//        Document document = new Document(PageSize.A4, 30, 30, 50, 50);
//        
//        // step2.1: creating an OutputStream
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        
//        try {
//            // step2.2: creating an instance of the writer
//            PdfWriter.getInstance(document, baos);
//            // step 3.1: adding some metadata to the document
//            document.addSubject("This is a report.");
//            // step 3.4: opening the document
//
//
//            // create header
//            Vector list = (Vector)temp.get(2);
//            long oidDept = 0;
//            if(Long.parseLong((String)temp.get(1))==0){
//                for (int i = 0; i < list.size(); i++) {
//                    Vector tmp = (Vector)list.get(i);
//                    oidDept = Long.parseLong((String)tmp.get(0));
//                    break;
//                }
//            }else{
//                oidDept = Long.parseLong((String)temp.get(1));
//            }
//
//            Department dep = new Department();
//            try{
//                dep = PstDepartment.fetchExc(oidDept);
//            }catch(Exception e){}
//
//            /* adding a Header of page, i.e. : title, align and etc */
//            HeaderFooter header = new HeaderFooter(new Phrase("LATENESS REPORT PER " + (Formater.formatDate((Date)temp.get(0),"MMMM dd, yyyy")).toUpperCase() +
//                                  "\nDEPARTMENT : "+ dep.getDepartment().toUpperCase(), fontHeader), false);
//
//            header.setAlignment(Element.ALIGN_LEFT);
//            header.setBorder(Rectangle.BOTTOM);
//            header.setBorderColor(blackColor);
//            document.setHeader(header);
//            document.open();
//
//            //document.add(createHeader(temp,font1,oidDept));
//            Table datatable = createHeaderDetail(new Table(7),fontContent);
//            //datatable.setDefaultCellBorderWidth(1);
//            datatable.setDefaultRowspan(1);
//            datatable.setDefaultCellBackgroundColor(whiteColor);
//
//            long oid = 0;
//            for (int i = 0; i < list.size(); i++) {
//                Vector tmp = (Vector)list.get(i);
//                long oidDep = Long.parseLong((String)tmp.get(0));
//                if(i==0)
//                    oid = oidDep;
//
//               /* if(oidDep!=oid){
//                    document.add(datatable);
//                    oid = oidDep;
//                    document.newPage();
//                    document.add(createHeader(temp,font1,oidDep));
//                    datatable = createHeaderDetail(new Table(8),font2a);
//                    datatable.setDefaultCellBorderWidth(1);
//                    datatable.setDefaultRowspan(1);
//                }*/
//
//                datatable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
//                if(oidDep!=-1){ // shift 1
//                    datatable.addCell(new Phrase((String)tmp.get(1), fontContent));//no
//                    datatable.addCell(new Phrase((String)tmp.get(2), fontContent));
//                    datatable.addCell(new Phrase((String)tmp.get(3), fontContent));//emp number
//                    datatable.addCell(new Phrase((String)tmp.get(4), fontContent));//nama
//                }else{ // shift 2
//                    datatable.setDefaultColspan(3);
//                    datatable.addCell(new Phrase((String)tmp.get(4), fontContent));//nama
//                    datatable.setDefaultColspan(1);
//                }
//                datatable.addCell(new Phrase((String)tmp.get(5), fontContent));//schedule
//                datatable.addCell(new Phrase((String)tmp.get(6), fontContent));
//                //datatable.addCell(new Phrase((String)tmp.get(7), fontContent));
//                //datatable.addCell(new Phrase((String)tmp.get(7), fontContent));
//            }
//            document.add(datatable);
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
    