/*
 * DailySickness.java
 *
 * Created on June 17, 2004, 6:28 PM
 */

package com.dimata.harisma.report.sickness;

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
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.session.absenteeism.*;
import com.dimata.harisma.session.sickness.SessSickness;
import com.dimata.harisma.session.sickness.SicknessDaily;
import com.lowagie.text.rtf.RtfHeaderFooter;
import com.lowagie.text.rtf.RtfPageNumber;

/**
 *
 * @author  gedhy
 */
public class DailySicknessPdf extends HttpServlet {
    
    /* declaration constant */
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);
    public static Color titleColor = new Color(200,200,200);
    public static Color summaryColor = new Color(240,240,240);
    public static String formatDate  = "MMM dd, yyyy";
    public static String formatNumber = "#,###";
    SimpleDateFormat formatter = new SimpleDateFormat("d/MM/yyyy");
    SimpleDateFormat formatterDay = new SimpleDateFormat("EE");

    /* setting some fonts in the color chosen by the user */
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, blackColor);
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 7, Font.NORMAL, blackColor);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 6, Font.NORMAL, blackColor);
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
        Document document = new Document(PageSize.A4, 30, 30, 50, 50);

        /* creating an OutputStream */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            /* creating an instance of the writer */
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            /* get data from session */
            HttpSession sessEmpPresence = request.getSession(true);
            Vector vctDailyAbsenteeism = null;
            try{
	        vctDailyAbsenteeism = (Vector)sessEmpPresence.getValue("SICKNESS_DAILY");
            }catch(Exception e){
                System.out.println("Exc : "+e.toString());
            }
           
            
            Date presenceFrom = new Date();
            Date presenceTo = new Date();
           // String departmentName ="";
            String footerNote = "";
           // Department department = null;
            //com.dimata.harisma.entity.masterdata.Section section = null;
             String empNumb="";
             String fullName="";
             long oidDepartment=0;
             long oidSection=0;
             
             long lSickDC=0;
             long lSickNDC=0;
             
             int iSickDC=0;
             int iSickNDC=0;
             Vector listSicknessPresence = new Vector(1,1);
            Vector listStruktur = new Vector(1,1);
            if(vctDailyAbsenteeism != null && vctDailyAbsenteeism.size()>0){
                //if(vctDailyAbsenteeism != null && vctDailyAbsenteeism.size()==4){
                try{
                        if(vctDailyAbsenteeism.get(0)!=null)
	            	presenceFrom = (Date) vctDailyAbsenteeism.get(0);
                        if(vctDailyAbsenteeism.get(1)!=null)
                        presenceTo = (Date) vctDailyAbsenteeism.get(1);
                        if(vctDailyAbsenteeism.get(2) !=null){
                         oidDepartment = Long.parseLong((String)vctDailyAbsenteeism.get(2));
                       // if(oidDepartment !=0){
	               // department = (Department)PstDepartment.fetchExc(oidDepartment);
                        //}
                      } 
                        if(vctDailyAbsenteeism.get(3) !=null){
                          oidSection = Long.parseLong((String)vctDailyAbsenteeism.get(3));
                        //if(oidSection !=0){
                            //section = (com.dimata.harisma.entity.masterdata.Section)PstSection.fetchExc(oidSection);
                        //}
                      }
                       // if(vctDailyAbsenteeism.get(4)!=null)
	                //listAbsence = (Vector)vctDailyAbsenteeism.get(4);
                        if(vctDailyAbsenteeism.get(5)!=null)
                        footerNote = (String)vctDailyAbsenteeism.get(5);
                        
                        if(vctDailyAbsenteeism.get(6)!=null)
                        empNumb = (String)vctDailyAbsenteeism.get(6);
                        if(vctDailyAbsenteeism.get(7)!=null)
                        fullName = (String)vctDailyAbsenteeism.get(7);
                        
                         if(vctDailyAbsenteeism.get(8)!=null)
                        lSickDC = Long.parseLong((String)vctDailyAbsenteeism.get(8)); 
                         if(vctDailyAbsenteeism.get(9)!=null)
                        lSickNDC = Long.parseLong((String)vctDailyAbsenteeism.get(9));
                           if(vctDailyAbsenteeism.get(10)!=null)
                        iSickDC = Integer.parseInt((String)vctDailyAbsenteeism.get(10));
                            if(vctDailyAbsenteeism.get(11)!=null)
                        iSickNDC = Integer.parseInt((String)vctDailyAbsenteeism.get(11));
                         listSicknessPresence = SessSickness.listSicknessDataDailyForPdf(oidDepartment,presenceFrom,presenceTo,oidSection,empNumb.trim(),fullName.trim(),0,0);      
                         if((empNumb !=null && empNumb.length() > 0) 
                                 || fullName !=null && fullName.length()>0
                                 || oidDepartment !=0
                                 || oidSection !=0){
                            listStruktur = PstDepartment.listStruktur(empNumb.trim(), fullName.trim(),oidDepartment,oidSection);
                         }
                }catch(Exception e){
                 	System.out.println("get List : "+e.toString());
                }
            }
             
            /* adding a Header of page, i.e. : title, align and etc */
          String departement = "";
          String company = "";
          String division = "";
          String section ="";
           if(listStruktur !=null && listStruktur.size()>0){
                for(int d=0; d < listStruktur.size(); d++){
                     StrukturEmployee strukturEmployee = (StrukturEmployee)listStruktur.get(d);
                    company = strukturEmployee.getCompany();
                    division = strukturEmployee.getDivision();
                    departement = strukturEmployee.getDepartment();
                    section = strukturEmployee.getSection();
                }
            }
            if(departement ==null || departement.length() <= 0 ){
               departement="-";
            }
             if(section ==null || section.length() <= 0 ){
               section="-";
            }
              if(company ==null || company.length() <= 0 ){
               company="-";
            }
            if(division ==null || division.length() <= 0 ){
               division="-";
            }
             String strReportTitle = 
                                    "SICKNESS REPORT PER : " + (Formater.formatDate(presenceFrom,"dd MMMM yyyy")).toUpperCase() +" s/d "+ (Formater.formatDate(presenceTo,"dd MMMM yyyy")).toUpperCase() +
                                    "\nCOMPANY \t\t\t\t\t\t\t\t\t: "+ company.toUpperCase()+
                                    "\nDEPARTMENT \t: "+ departement.toUpperCase()+
                                    "\nDIVISION \t\t\t\t\t\t\t\t\t\t\t: "+ division.toUpperCase()+
                                    "\nSECTION \t\t\t\t\t\t\t\t\t\t\t : "+ section.toUpperCase();
                                    // "\nSECTION : \n"+sec;
             HeaderFooter header = new HeaderFooter(new Phrase(strReportTitle, fontHeader), false);

            header.setAlignment(Element.ALIGN_LEFT);
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
             
            /* opening the document, needed for add something into document */
            document.open();


            /* create header */
            Table tableEmpPresence = getTableHeader();

            /* generate employee attendance report's data */
            Cell cellEmpData = new Cell("");
             
            if(listSicknessPresence!=null && listSicknessPresence.size()>0)
            {
                for (int i = 0; i < listSicknessPresence.size(); i++) 
                {                    
                   // Vector itemAbsence = (Vector)listSicknessPresence.get(i);                    
                    SicknessDaily sicknessDaily = (SicknessDaily)listSicknessPresence.get(i);   
                    
                        String dayString = formatterDay.format(sicknessDaily.getSelectedDate());
                        String dateString = formatter.format(sicknessDaily.getSelectedDate());
                        String strAbsence="";
                        // employee
                    cellEmpData = new Cell(new Chunk(String.valueOf(i+1),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(dayString +","+dateString),fontContent)); 
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(sicknessDaily.getEmpNum()),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(sicknessDaily.getEmpName()),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    // schedule
                      
                    cellEmpData = new Cell(new Chunk(String.valueOf(sicknessDaily.getSchldSymbol()),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(Formater.formatTimeLocale(sicknessDaily.getSchldIn(), "HH:mm")),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(Formater.formatTimeLocale(sicknessDaily.getSchldOut(), "HH:mm")),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    // actual
                    if(lSickDC!=0){
                                if(sicknessDaily.getReason() == iSickDC){
                                     strAbsence = "DC";				
                                       
                                }
                                else if(sicknessDaily.getReason() == iSickNDC){
                                    strAbsence = "W/O DC"; 				
                                         
                                }
                              
                            }
                    else if(lSickNDC!=0){
                                if(sicknessDaily.getReason() == iSickDC){
                                     strAbsence = "DC";				
                                       
                                }
                                else if(sicknessDaily.getReason() == iSickNDC){
                                    strAbsence = "W/O DC"; 				
                                         
                                }
                             
                    }
                    else {                        
                                if(sicknessDaily.getReason()==PstEmpSchedule.REASON_ABSENCE_SICKNESS){
                                        strAbsence = "DC";
                                      
                                }else{
                                        strAbsence = "CSTD";
                                        
                                }
                        }
                    cellEmpData = new Cell(new Chunk(String.valueOf(strAbsence),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);       
                    
                    // remark                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(sicknessDaily.getRemark()),fontContent));
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
                        tableEmpPresence = getTableHeader();
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
    * this method used to create header table
    */
    public static Table getTableHeader() throws BadElementException, DocumentException 
    {
           Table tableEmpPresence = new Table(9);
           tableEmpPresence.setCellpadding(1);
           tableEmpPresence.setCellspacing(1);
           tableEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
           int widthHeader[] = {3, 9,7, 35, 5, 5, 5, 7, 31};
    	   tableEmpPresence.setWidths(widthHeader);
           tableEmpPresence.setWidth(100);

           Cell cellEmpPresence = new Cell(new Chunk("NO",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk("DATE",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk("PAYROLL",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk("EMPLOYEE",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk("SCHEDULE",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(3);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

            cellEmpPresence = new Cell(new Chunk("ACTUAL",fontContent));
            cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellEmpPresence.setColspan(1);
            cellEmpPresence.setRowspan(2);
            cellEmpPresence.setBackgroundColor(summaryColor);
            tableEmpPresence.addCell(cellEmpPresence);

            cellEmpPresence = new Cell(new Chunk("REMARK",fontContent));
            cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellEmpPresence.setColspan(1);
            cellEmpPresence.setRowspan(2);
            cellEmpPresence.setBackgroundColor(summaryColor);
            tableEmpPresence.addCell(cellEmpPresence);
        
            cellEmpPresence = new Cell(new Chunk("SYM",fontContent));
            cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellEmpPresence.setColspan(1);
            cellEmpPresence.setRowspan(1);
            cellEmpPresence.setBackgroundColor(summaryColor);
            tableEmpPresence.addCell(cellEmpPresence);

            cellEmpPresence = new Cell(new Chunk("IN",fontContent));
            cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellEmpPresence.setColspan(1);
            cellEmpPresence.setRowspan(1);
            cellEmpPresence.setBackgroundColor(summaryColor);
            tableEmpPresence.addCell(cellEmpPresence);

            cellEmpPresence = new Cell(new Chunk("OUT",fontContent));
            cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellEmpPresence.setColspan(1);
            cellEmpPresence.setRowspan(1);
            cellEmpPresence.setBackgroundColor(summaryColor);
            tableEmpPresence.addCell(cellEmpPresence);

            return tableEmpPresence;
    }
    
}
