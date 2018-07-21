/*
 * SessAbsenteeism.java
 *
 * Created on June 7, 2004, 12:01 PM
 */

package com.dimata.harisma.report.absenteeism;

/* package java */
import java.util.*;
import java.awt.Color;
import java.io.ByteArrayOutputStream;

import java.io.IOException;

/* package servlet */
import javax.servlet.*;
import javax.servlet.http.*;

/* package lowagie */
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

/* package qdep */
import com.dimata.util.*;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstReason;
import com.dimata.harisma.entity.masterdata.StrukturEmployee;
import com.dimata.harisma.session.absenteeism.AbsenteeismDaily;
import com.dimata.harisma.session.absenteeism.SessAbsenteeism;
import java.text.SimpleDateFormat;

/* package harisma */


/**
 *
 * @author  gedhy
 */
public class DailyAbsenteeismPdf extends HttpServlet {
    
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
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 8, Font.NORMAL, blackColor);
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
	        vctDailyAbsenteeism = (Vector)sessEmpPresence.getValue("ABSENTEEISM_DAILY");
            }catch(Exception e){
                System.out.println("Exc : "+e.toString());
            }

            Date presence = new Date();
           
            Department department;
            department = new Department();
            Vector listAbsence = new Vector(1,1);
            String empNumb="";
             String fullName="";
             long oidDepartment=0;
              long oidSection=0;
              //update by devin 2014-01-05
              long oidCompany=0;
              long oidDivision=0;
              Date SelectedDateFrom=null;
              Date SelectedDateTo = null;
              Vector listStruktur = new Vector(1,1);
              Vector listAbsenteeismPresence = new Vector(1,1);
            if(vctDailyAbsenteeism != null && vctDailyAbsenteeism.size()>0){
                try{
	            	//presence = (Date) vctDailyAbsenteeism.get(0);
                       /* long oidDepartment = Long.parseLong((String)vctDailyAbsenteeism.get(1));
                        // if one of all department is selected,updated By Yunny
                        if(oidDepartment!=0){
                            department = (Department)PstDepartment.fetchExc(oidDepartment);
                            departmentName = department.getDepartment();
                        }
                        else{
                            departmentName = " ALL DEPARTMENT ";
                        }*/
                         if(vctDailyAbsenteeism.get(0) !=null){
                         oidDepartment = Long.parseLong((String)vctDailyAbsenteeism.get(0));
                        } 
                         if(vctDailyAbsenteeism.get(1) !=null){
                         oidSection = Long.parseLong((String)vctDailyAbsenteeism.get(1));
                        }
                          //update by devin 2014-01-05
                         if(vctDailyAbsenteeism.get(2) !=null){
                         oidCompany= Long.parseLong((String)vctDailyAbsenteeism.get(2));
                        } 
                          if(vctDailyAbsenteeism.get(3) !=null){
                         oidDivision = Long.parseLong((String)vctDailyAbsenteeism.get(3));
                        } 
                         if(vctDailyAbsenteeism.get(4) !=null){
                          empNumb = (String)vctDailyAbsenteeism.get(4);
                        } 
                           if(vctDailyAbsenteeism.get(5) !=null){
                         fullName = (String)vctDailyAbsenteeism.get(5);
                        } 
                         if(vctDailyAbsenteeism.get(6) !=null){
                         SelectedDateFrom = (Date)vctDailyAbsenteeism.get(6);
                        } 
                           if(vctDailyAbsenteeism.get(7) !=null){
                         SelectedDateTo = (Date)vctDailyAbsenteeism.get(7);
                        } 
	                listAbsence = (Vector)vctDailyAbsenteeism.get(8);
                        if((empNumb !=null && empNumb.length() > 0) 
                                 || fullName !=null && fullName.length()>0
                                 || oidDepartment !=0
                                 || oidSection !=0){
                            listStruktur = PstDepartment.listStruktur(empNumb.trim(), fullName.trim(),oidDepartment,oidSection);
                         }
                       
                        //update by devin 2014-02-05
                        // listAbsenteeismPresence = SessAbsenteeism.listAbsenteeismDataDaily(oidDepartment,SelectedDateFrom,SelectedDateTo,oidSection,empNumb.trim(),fullName.trim());
                        listAbsenteeismPresence = SessAbsenteeism.listAbsenteeismDataDaily(oidDepartment,oidCompany,oidDivision,SelectedDateFrom,SelectedDateTo,oidSection,empNumb.trim(),fullName.trim());
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
                                    "ABSENTEEISM REPORT PER : " + (Formater.formatDate(SelectedDateFrom,"dd MMMM yyyy")).toUpperCase() + " To " + (Formater.formatDate(SelectedDateTo,"dd MMMM yyyy")).toUpperCase() +
                                    "\nCOMPANY \t\t\t\t\t\t\t\t\t: "+ company.toUpperCase()+
                                    "\nDEPARTMENT \t: "+ departement.toUpperCase()+
                                    "\nDIVISION \t\t\t\t\t\t\t\t\t\t\t: "+ division.toUpperCase()+
                                    "\nSECTION \t\t\t\t\t\t\t\t\t\t\t : "+ section.toUpperCase();
                                    // "\nSECTION : \n"+sec;
            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase(strReportTitle, fontHeader), false);

            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);
            
            /* opening the document, needed for add something into document */
            document.open();


            /* create header */
            Table tableEmpPresence = getTableHeader();

            /* generate employee attendance report's data */
            Cell cellEmpData = new Cell("");
             
            if(listAbsenteeismPresence!=null && listAbsenteeismPresence.size()>0)
            {
                for (int i = 0; i < listAbsenteeismPresence.size(); i++) 
                {                    
                    AbsenteeismDaily absenteeismDaily = (AbsenteeismDaily)listAbsenteeismPresence.get(i);                    
                    String dayString = formatterDay.format(absenteeismDaily.getSelectedDate());
                    String dateString = formatter.format(absenteeismDaily.getSelectedDate());
                   // String remark = PstReason.getStrReason(absenteeismDaily.getNoReason1st());
                    String remark = absenteeismDaily.getNote1st();
                    String CodeReason = PstReason.getStrReasonCode(absenteeismDaily.getNoReason1st());
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
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(absenteeismDaily.getEmpNum()),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(absenteeismDaily.getEmpName()),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    // schedule
                    cellEmpData = new Cell(new Chunk(String.valueOf(absenteeismDaily.getSchldSymbol()),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(absenteeismDaily.getSchldIn()),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(absenteeismDaily.getSchldOut()),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    // actual    
                    if(CodeReason!=null && CodeReason.length()>0){
                         cellEmpData = new Cell(new Chunk(String.valueOf(CodeReason),fontContent));
                    }else{
                        cellEmpData = new Cell(new Chunk(String.valueOf("-"),fontContent));
                    }
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);       
                    
                    // remark                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(remark),fontContent));
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
           int widthHeader[] = {4, 15,8, 25, 8, 8, 8, 8, 21};
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
        
            cellEmpPresence = new Cell(new Chunk("SYMBOL",fontContent));
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
