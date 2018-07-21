/*
 * DpApplicationPdf.java
 *
 * Created on January 12, 2005, 12:02 PM
 */

package com.dimata.harisma.report.leave;

// package java
import com.dimata.harisma.entity.attendance.SpecialLeave;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

// package lowagie
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

// package qdep
import com.dimata.util.*;
import com.dimata.qdep.form.*;
import com.dimata.system.entity.PstSystemProperty;
import java.util.Date;
import java.util.Vector;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author  gedhy
 */
public class SpecialLeaveReportPDF extends HttpServlet {
    
    public static Color border = new Color(0x00, 0x00, 0x00);
    public static Color bgColor = new Color(240,240,240);
    public static Color blackColor = new Color(0,0,0);
    public static Color putih = new Color(250,250,250);

    // setting some fonts in the color chosen by the user
    public static Font fontHeaderBig2 = new Font(Font.HELVETICA, 14, Font.BOLD, border);
    public static Font fontHeaderBig = new Font(Font.HELVETICA, 12, Font.BOLD, border);
    public static Font fontHeaderBigS = new Font(Font.HELVETICA, 11, Font.BOLD, border);
    public static Font fontHeaderSmall = new Font(Font.HELVETICA, 8, Font.NORMAL, border);
    public static Font fontHeaderBoldSmall = new Font(Font.HELVETICA, 8, Font.BOLD, border);
    public static Font fontHeader = new Font(Font.HELVETICA, 11, Font.BOLD, border);
    public static Font fontContent = new Font(Font.HELVETICA, 10, Font.BOLD, border);
    public static Font tableContent = new Font(Font.HELVETICA, 10, Font.NORMAL, border);
    public static Font tableContentBox = new Font(Font.HELVETICA, 8, Font.NORMAL, border);
    public static Font fontSpellCharge = new Font(Font.HELVETICA, 10, Font.BOLDITALIC, border);
    public static Font fontItalicSmall = new Font(Font.HELVETICA, 8, Font.BOLDITALIC, border);
    public static Font fontItalic = new Font(Font.HELVETICA, 10, Font.BOLDITALIC, border);
    public static Font fontItalicBig = new Font(Font.HELVETICA, 12, Font.BOLDITALIC, border);
    public static Font fontItalicBottom = new Font(Font.HELVETICA, 10, Font.ITALIC, border);
    public static Font fontItalicBottomSmall = new Font(Font.HELVETICA, 8, Font.ITALIC, border);
    public static Font fontUnderline = new  Font(Font.HELVETICA, 10, Font.UNDERLINE, border);
    
    public static String imagesRoot = PstSystemProperty.getValueByName("IMG_ROOT");
    public static String imgLogo = imagesRoot+"logo.jpg"; 
    public static String imgBox = imagesRoot+"icon/box.jpg"; 
    public static String imgCheckBox = imagesRoot+"icon/check_box.jpg"; 

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


        // creating the document object
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);

        // creating an OutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpSession sess = request.getSession(true);
        // get data from session
        Vector vListSpecialLeave = new Vector(1,1);
        
        long lPeriod = 0;
        long leaveOid = 0;
        Date period = new Date();
        ScheduleSymbol sc = new ScheduleSymbol();
        try
        {
            vListSpecialLeave = (Vector)sess.getValue("SESS_SPECIAL_LEAVE");
            lPeriod = FRMQueryString.requestLong(request, "currPeriod");
            leaveOid = FRMQueryString.requestLong(request, "scheduleSymbolId");
            period = new Date(lPeriod);
            sc = PstScheduleSymbol.fetchExc(leaveOid);
        }
        catch(Exception e)
        {
            System.out.println("Exc : "+e.toString());
        }
        
        //First Config
        String[] aTitle = {
                "SPECIAL LEAVE REPORT (OTHER LEAVE REPORT)",
                sc.getSchedule(),
                "Period : "+Formater.formatDate(period, "MMMM yyyy")
        };

        //Setting Footer
        String strFooterText = "Hard Rock Hotel - Bali | ";
        boolean isUsePageFooter = false;
        String strFooterDateFormat = "MMMM dd, yyyy";

        boolean isUseLogo = true;
        
        try {

            // creating an instance of the writer
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            
            // step 3.1: adding some metadata to the document
            document.addSubject("This is a subject.");
            document.addSubject("This is a subject two."); 
            
            // FOOTER
            HeaderFooter footer = new HeaderFooter(new Phrase(strFooterText+Formater.formatDate(new Date(), strFooterDateFormat), fontHeaderSmall),isUsePageFooter);
            footer.setAlignment(Element.ALIGN_RIGHT);
            footer.setBorder(Rectangle.TOP);
            footer.setBorderColor(blackColor);
           // document.setFooter(footer);
            
            // step 3.4: opening the document
            document.open();
            
            //INFORMATION
            //Header
            Table tableHeader = createHeader(aTitle);
            document.add(tableHeader);
            
            //Emp Date
            Table tableEmpDetail = createData(vListSpecialLeave);
            document.add(tableEmpDetail);
            
           //Menampilkan Report
            
            
           //End -- Menampilkan Report
            
        }
        catch(DocumentException de) 
        {
            System.err.println(de.getMessage());
            de.printStackTrace();
        }

        // closing the document 
        document.close();

        // we have written the pdfstream to a ByteArrayOutputStream, now going to write this outputStream to the ServletOutputStream
	// after we have set the contentlength        
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }
    
    /** 
     * Header Information
     */    
    public Table createHeader(String[] aTitle)
    {        
        Table datatable = null;  
        Cell cellData = new Cell("");
        try
        {                        
            int maxColumn = 1;                        
            float headerwidths[] = new float[maxColumn];
          //  for(int i=0;i<maxColumn;i++){
                headerwidths[0] = 100;
          //  }
            
            // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(1);
            datatable.setSpacing(1); //1
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_CENTER);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.setBorder(Table.NO_BORDER);
            datatable.setDefaultCellBorder(Cell.NO_BORDER);
                        
            //Title
            for(int i=0;i<aTitle.length;i++){
                cellData = new Cell(new Phrase(aTitle[i],fontHeaderBig));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_CENTER);
                cellData.setLeading(12);
                cellData.setColspan(maxColumn);
                datatable.addCell(cellData);
            }
            // this is the end of the table header
          //  datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on DpAppPdf.createHeader : "+e.toString());
        }
        return datatable;
    }
    
    /** 
     * Employee Information
     */    
    public Table createData(Vector vListSpecialLeave)
    {        
        ///////////////////////////////////////
        Table datatable = null;  
        Cell cellData = new Cell("");
        try
        {                        
            int maxColumn = 7;                        
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 5;
            headerwidths[1] = 8;
            headerwidths[2] = 25;
            headerwidths[3] = 15;
            headerwidths[4] = 15;
            headerwidths[5] = 15;
            headerwidths[6] = 17;
            
            
            // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(1);
            datatable.setSpacing(1); //1
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_CENTER);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
           // datatable.setBorder(Table.NO_BORDER);
           // datatable.setDefaultCellBorder(Cell.);
            
            //////////////////// CREATE TITLE TABLE ////////////////////
            String[] strTitle = {
                "No",
                "Payroll",
                "Name",
                "Department",
                "Section",
                "Leave Period",
                "Remark"
            };
            
            for(int i=0;i<strTitle.length;i++){
                cellData = new Cell(new Chunk(strTitle[i],fontContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                cellData.setBackgroundColor(bgColor);
                datatable.addCell(cellData);
            }
            
            //////////////// CREATE DATA //////////////////////////////
            int iterateNo = 0;
            for(int j=0;j<vListSpecialLeave.size();j++){
                Vector vSpList = new Vector(1,1);
                vSpList = (Vector)vListSpecialLeave.get(j);

                Vector vSpecialLeave = new Vector(1,1);
                Employee emp = new Employee();
                Department dep = new Department();
                Section sec = new Section();
                Vector vDetail = new Vector(1,1);

                
                iterateNo += 1;
                emp = (Employee)vSpList.get(0);
                dep = (Department)vSpList.get(1);
                sec = (Section)vSpList.get(2);
                vDetail = (Vector)vSpList.get(3);
                vSpecialLeave = (Vector)vSpList.get(4);
                
                //No
                cellData = new Cell(new Chunk(""+iterateNo,tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                datatable.addCell(cellData);
                
                //Payroll
                cellData = new Cell(new Chunk(""+emp.getEmployeeNum(),tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                datatable.addCell(cellData);
                
                //Name
                cellData = new Cell(new Chunk(""+emp.getFullName(),tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                datatable.addCell(cellData);
                
                //Department
                cellData = new Cell(new Chunk(""+dep.getDepartment(),tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                datatable.addCell(cellData);
                
                //Section
                cellData = new Cell(new Chunk(""+sec.getSection(),tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                datatable.addCell(cellData);
                
                //Leave Period
                cellData = new Cell(new Chunk(createTakenDate(vDetail),tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                datatable.addCell(cellData);
                
                //Remark
                cellData = new Cell(new Chunk(createRemark(vSpecialLeave),tableContent));
                cellData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellData.setVerticalAlignment(Cell.ALIGN_TOP);
                datatable.addCell(cellData);
                
            }
            
            // this is the end of the table header
          //  datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on DpAppPdf.createEmpId : "+e.toString());
        }
        return datatable;
    }
    
    
    
    private String createTakenDate(Vector vDetail){
        String strTakenDate = "";
        String strDateFormat = "dd-MMM-yyyy";
        Date datePrev = null;
        Date dateNext = null;
        for(int j=0;j<vDetail.size();j++){
            Date dateTaken = (Date)vDetail.get(j);
            if(j==0){
                datePrev = dateTaken;
                dateNext = dateTaken;
                if(j+1==vDetail.size()){
                    strTakenDate += Formater.formatDate(datePrev, strDateFormat);
                }
            }else{
                if(getTommorow(dateNext).getTime()==dateTaken.getTime()){
                    dateNext = dateTaken;
                    if(j+1==vDetail.size()){
                        if(strTakenDate.length()>0){strTakenDate += ", \n";}
                        strTakenDate += Formater.formatDate(datePrev, strDateFormat)
                                +" s/d "+Formater.formatDate(dateNext, strDateFormat);
                    }
                }else{
                    if(datePrev.getTime()==dateNext.getTime()){
                        if(strTakenDate.length()>0){strTakenDate += ", \n";}
                         strTakenDate += Formater.formatDate(datePrev, strDateFormat);
                    }else{
                        if(strTakenDate.length()>0){strTakenDate += ", \n";}
                        strTakenDate += Formater.formatDate(datePrev, strDateFormat)
                                +" s/d "+Formater.formatDate(dateNext, strDateFormat);
                    }
                    datePrev = dateTaken;
                    dateNext = dateTaken;
                    if(j+1==vDetail.size()){
                        if(strTakenDate.length()>0){strTakenDate += ", \n";}
                         strTakenDate += Formater.formatDate(datePrev, strDateFormat);
                    }
                }
            }

        }
        return strTakenDate;
    }
    
    private Date getTommorow(Date date){
       Date tdate = (Date)date.clone();
       tdate.setDate(date.getDate()+1);
       return tdate;
    }
    
    private String createRemark(Vector vSpecialLeave){
    String strRemark = "";
    for(int i=0;i<vSpecialLeave.size();i++){
        SpecialLeave sp = new SpecialLeave();
        sp = (SpecialLeave)vSpecialLeave.get(i);
        if(strRemark.length()>0){
            strRemark += "\n";
        }
        strRemark += sp.getOtherRemarks();
    }
    return strRemark;
}
}
