/*
 * AttRecordDaily.java
 *
 * Created on July 13, 2004, 2:32 PM
 */

package com.dimata.harisma.report.staffcontrol;

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
import com.dimata.harisma.entity.attendance.I_Atendance;
import com.dimata.harisma.entity.attendance.PstMachineTransaction;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.session.attendance.*;
import com.dimata.system.entity.PstSystemProperty;
import java.sql.Time;
/**
 *
 * @author  gedhy
 */
public class AttRecordDailyPdf extends HttpServlet {
    
	/* declaration constant */
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);
    public static Color titleColor = new Color(200,200,200);
    public static Color summaryColor = new Color(240,240,240);
    public static String formatDate  = "MMM dd, yyyy";
    public static String formatNumber = "#,###";

    /* setting some fonts in the color chosen by the user */
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 9, Font.BOLD, blackColor);
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 8, Font.NORMAL, blackColor);
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
            Vector vctDailyPresence = null;
            try
            {
                vctDailyPresence = (Vector)sessEmpPresence.getValue("ATTENDANCE_RECORD_DAILY");
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
            
            Vector listPresence = new Vector(1,1);
            Vector secSelect = new Vector(1,1);
            //priska 20150609
            Hashtable outletname = new Hashtable();
            long oidSection = 0;
  ///cek untuk mendqapatkan insentif
  I_Atendance attdConfig = null;
try {
    attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
} catch (Exception e) {
    System.out.println("Exception : " + e.getMessage());
    System.out.println("Please contact your system administration to setup system property: LEAVE_CONFIG ");
}
          //  Vector listStruktur = new Vector(1,1);
            if(vctDailyPresence != null && vctDailyPresence.size()>0)
                // if(vctDailyPresence != null && vctDailyPresence.size()==7)
            {
                try
                {
	            	presenceDateFrom = (Date) vctDailyPresence.get(0);
                        presenceDateTo = (Date) vctDailyPresence.get(1);
	              if(vctDailyPresence.get(2) !=null){
                        long oidDepartment = Long.parseLong((String)vctDailyPresence.get(2));
                        if(oidDepartment !=0){
	                department = (Department)PstDepartment.fetchExc(oidDepartment);
                        }
                      }  
                        if(vctDailyPresence.get(7) !=null){
                        oidSection = Long.parseLong((String)vctDailyPresence.get(7));
                        if(oidSection !=0){
                            section = (com.dimata.harisma.entity.masterdata.Section)PstSection.fetchExc(oidSection);
                        }
                      }
                       
                        //update by satrya 2012-07-24
                        String fullName = (String) vctDailyPresence.get(3);
                        
                        String empNum = (String) vctDailyPresence.get(4);
	                listPresence = (Vector)vctDailyPresence.get(5);
                        secSelect = (Vector)vctDailyPresence.get(6);
                        String InEmpNum = " 1";
                        for ( int is = 0 ; is < listPresence.size(); is++ ){
                            Vector vectTemp = (Vector)listPresence.get(is);
                            if (vectTemp != null){
                            InEmpNum = InEmpNum + "," +vectTemp.get(2);
                            }
                        }
                        String wherestation = " he." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " IN ( "+ InEmpNum +") AND ( hmt." + PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS] +" >= \""+ Formater.formatDate(presenceDateFrom, "yyyy-MM-dd") + " 00:00:00\"" + " AND hmt." + PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS] +" <= \""+ Formater.formatDate(presenceDateTo, "yyyy-MM-dd") + " 23:59:59\""  +" )" ;
                        outletname = PstMachineTransaction.GetStationByDate(0, 0, wherestation, PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS]);
                        //listStruktur = (Vector) vctDailyPresence.get(7);
                }
                catch(Exception e)
                {
                 	System.out.println("get List : "+e.toString());
                }
            }
            
            String sec = "";
            if(secSelect!=null && secSelect.size()>0){
                for(int xx=0; xx<secSelect.size(); xx++){
                    com.dimata.harisma.entity.masterdata.Section s = (com.dimata.harisma.entity.masterdata.Section)secSelect.get(xx);
                    sec = sec + s.getSection()+", ";
                }
                if(sec!=null && sec.length() >2){
                sec = sec.substring(0, sec.length()-2);
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
            
                   //update by satrya 2013-04-09
      int showOvertime = 0;
    try{
        showOvertime = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY"));
    }catch(Exception ex){

        System.out.println("<blink>ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY NOT TO BE SET</blink>" ); 
        showOvertime=0; 
    }
    
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
                                    "ATTENDANCE RECORD : " + (Formater.formatDate(presenceDateFrom,"dd MMMM yyyy")).toUpperCase() +" s/d "+ (Formater.formatDate(presenceDateTo,"dd MMMM yyyy")).toUpperCase() +
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
            Table tableEmpPresence = getTableHeader(showOvertime,attdConfig);

            /* generate employee attendance report's data */
            Cell cellEmpData = new Cell("");
            
         
            if(listPresence!=null && listPresence.size()>0)
            {
                int maxListPresence = listPresence.size();
                String instf = null;
                for(int i=0; i<maxListPresence; i++)
                {
                    Vector vectTemp = (Vector)listPresence.get(i);
                    
//                    Date presencDate = (Date) vectTemp.get(1);
//                    Time timepresence = (Time) vectTemp.get(5);
//                    outletname.get(vectTemp.get(2)+"_"+presencDate.getYear()+"-"+presencDate.getMonth()+"-"+presencDate.getDate()+"_"+timepresence);
//                    /* -------------- no , employee name & Tanggal  --------------*/
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(0)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(1)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(2)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(3)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    
                    /* -------------- schedule --------------*/
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(4)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(5)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(6)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    
                    /* -------------- actual --------------*/
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(7)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(8)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(9)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);                    
                    
                    
                    /* -------------- difference --------------*/
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(10)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData); 
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(11)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);  
                    /* ---------------- Reason & Note Status------------ */
                    
                 int insfConfig=0;
                 if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
                  if(vectTemp.get(12)== "&#10004;"){
                        instf  = "x";
                    }else{
                        instf = "-";
                    }
                     cellEmpData = new Cell(new Chunk(String.valueOf(instf),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                 }else{
                     insfConfig=1;//gunanya jika insentif tdk ingin di tampilkan maka arraynya di kurangi 1
                 }   
                     cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(13 -insfConfig)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                     cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(14-insfConfig)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(15- insfConfig)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                   if(showOvertime==0){
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(16- insfConfig)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(17- insfConfig)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(18- insfConfig)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(19- insfConfig)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(vectTemp.get(20- insfConfig)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    /* ------- end */
                   }
                    
                    if (!writer.fitsPage(tableEmpPresence)) 
                    {
                        tableEmpPresence.deleteLastRow();
                        i--;
                        document.add(tableEmpPresence);
                        document.newPage();
                        tableEmpPresence = getTableHeader(showOvertime,attdConfig);
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
    public static Table getTableHeader(int showOvertime,I_Atendance attdConfig) throws BadElementException, DocumentException 
    {      
           int recordShowTable=21;
           if(showOvertime!=0){
               recordShowTable=16;
           }
           if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_II_NO_TAKEN_INSENTIF){
               recordShowTable=15;
           }
           Table tableEmpPresence = new Table(/*showOvertime!=0 ?16:21*/ recordShowTable);
           tableEmpPresence.setCellpadding(1);
           tableEmpPresence.setCellspacing(1);
           tableEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
           //int widthHeader[] = {3,8,5,10,6,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2};
           int widthHeader[] = {3,7,4,10,5,4,4,4,4,4,4,5,3,5,4,4,3,4,4,5,4};
            int widthHeader2[] = {3,7,4,10,5,4,4,4,4,4,4,5,3,5,4,4};
            int widthHeader3[] = {3,7,4,10,5,4,4,4,4,4,4,5,5,4,4};
           if(showOvertime!=0){
                widthHeader = widthHeader2;
           }
           if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_II_NO_TAKEN_INSENTIF){
               
               widthHeader = widthHeader3;
           }
    	   tableEmpPresence.setWidths(widthHeader);
           tableEmpPresence.setWidth(100);

           Cell cellEmpPresence = new Cell(new Chunk("No.",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           //update by satrya 2012-08-18
           cellEmpPresence = new Cell(new Chunk("Date",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           //update by satrya 2012-09-06
           cellEmpPresence = new Cell(new Chunk("Payrol",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk("Employee",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk("Sch",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
       	   tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk("Actual",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(4);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk("Diference",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(2);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk("Duration",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence); 
        //update by satrya 2013-07-25
        if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
           cellEmpPresence = new Cell(new Chunk("Instf",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence); 
         } 
          if(showOvertime==0){
            cellEmpPresence = new Cell(new Chunk("OT.Form",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence); 
           
           cellEmpPresence = new Cell(new Chunk("Allwn",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence); 
           
           cellEmpPresence = new Cell(new Chunk(" Paid /DP ",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence); 
           
           cellEmpPresence = new Cell(new Chunk(" Net.OT ",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence); 
           
           cellEmpPresence = new Cell(new Chunk(" OT.Idx ",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence); 
         }
           //update by satrya 2012-07-23
           cellEmpPresence = new Cell(new Chunk("Reason",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk("Note",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
             cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk(" Status",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
             cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           //end

           cellEmpPresence = new Cell(new Chunk("Symbol",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" TI ",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk(" BO",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" BI",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk(" TO ",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk(" IN",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" OUT",fontContent));
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setColspan(1);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           
           return tableEmpPresence;
    }
    
}
