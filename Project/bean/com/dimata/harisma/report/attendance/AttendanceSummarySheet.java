/*
 * MonthlyPresencePdf.java
 *
 * Created on June 4, 2004, 10:36 AM
 */

package com.dimata.harisma.report.attendance;

/* package java */
import java.util.*;
import java.text.*;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

/* package lowagie */
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

/* package qdep */

/* package harisma */
import com.dimata.harisma.entity.attendance.AlStockManagement;
import com.dimata.harisma.entity.attendance.DpStockManagement;
import com.dimata.harisma.entity.attendance.LLStockManagement;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.Period;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstPeriod;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Formater;

/**
 *
 * @author  gedhy
 */
public class AttendanceSummarySheet  extends HttpServlet {
    
    /* declaration constant */
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);
    public static Color titleColor = new Color(200,200,200);
    public static Color summaryColor = new Color(240,240,240);
    public static String formatDate  = "MMM dd, yyyy";
    public static String formatNumber = "#,###";

    /* setting some fonts in the color chosen by the user */
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, blackColor);
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 8, Font.NORMAL, blackColor);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 8, Font.NORMAL, blackColor);
    public static Font fontSubTitle = new Font(Font.TIMES_NEW_ROMAN, 7, Font.NORMAL, blackColor);
    public static Font fontFootAssign = new Font(Font.TIMES_NEW_ROMAN, 8, Font.BOLD, blackColor);
    public static Font fontFootAssign_u = new Font(Font.TIMES_NEW_ROMAN, 8, Font.UNDERLINE, blackColor);
    
    private static String imgRoot = PstSystemProperty.getValueByName("IMG_ROOT");
    private static String imgCentangRoot = imgRoot+"centang.JPG"; 

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
        Document document = new Document(PageSize.A4.rotate(), 20, 20, 30, 30);

        /* creating an OutputStream */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            /* creating an instance of the writer */
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            /* get data from session */
            HttpSession sessEmpPresence = request.getSession(true);
            Vector vAttendanceSummarySheet = null;
            try{
	        vAttendanceSummarySheet = (Vector)sessEmpPresence.getValue("ATTENDANCE_SUMMARY_SHEET");
            }catch(Exception e){
                System.out.println("Exc : "+e.toString());
            }

            
            //Date presence = new Date();
            Period period = new Period();
            Department department = new Department();
            Division division = new Division();
            Vector listAttendace = new Vector(1,1);
            int intDateOfMonth = 0;
            int startDatePeriod = 0;
            if(vAttendanceSummarySheet != null && vAttendanceSummarySheet.size()==3){
                try{
	            	long periodOid = Long.parseLong((String)vAttendanceSummarySheet.get(0));
	                long departmentOid = Long.parseLong((String)vAttendanceSummarySheet.get(1));
	                department = (Department)PstDepartment.fetchExc(departmentOid);
	                period = (Period)PstPeriod.fetchExc(periodOid);
                        division = PstDivision.fetchExc(department.getDivisionId());
	                listAttendace = (Vector)vAttendanceSummarySheet.get(2);
                        
                        Calendar newCalendarTemp = Calendar.getInstance();
                        newCalendarTemp.setTime(period.getStartDate());
                        intDateOfMonth = newCalendarTemp.getActualMaximum(Calendar.DAY_OF_MONTH);
                        startDatePeriod = period.getStartDate().getDate();
                }
                catch(Exception e)
                {
                 	System.out.println("exc on get List vctMonthlyPresence : "+e.toString());
                }
            }               
            
            
             
            /* opening the document, needed for add something into document */
            document.open();

            String[] strMonth = {
                "Jan.",
                "Feb.",
                "Mar.",
                "Apr.",
                "May.",
                "Jun.",
                "Jul.",
                "Aug.",
                "Sep.",
                "Oct.",
                "Nov.",
                "Dec."
            };
            
            
            /* adding a Footer of page, i.e. : title, align and etc */
            HeaderFooter footer = new HeaderFooter(new Phrase("Page : ", fontTitle), true);

            footer.setAlignment(Element.ALIGN_RIGHT);
            footer.setBorder(Table.NO_BORDER);
            footer.setBorderColor(whiteColor);
           // document.add(footer);
            
            /* create header */
            String strPeriod = "";
            if(period.getStartDate().getYear()==period.getEndDate().getYear()){
                strPeriod = period.getStartDate().getDate()
                        +" "+strMonth[period.getStartDate().getMonth()]
                        +"-"+period.getEndDate().getDate()
                        +" "+strMonth[period.getEndDate().getMonth()]
                        +" "+(period.getEndDate().getYear()+1900);
            }else{
                strPeriod = period.getStartDate().getDate()
                        +" "+strMonth[period.getStartDate().getMonth()]
                        +" "+period.getStartDate().getYear()+1900
                        +"-"+period.getEndDate().getDate()
                        +" "+strMonth[period.getEndDate().getMonth()]
                        +" "+(period.getEndDate().getYear()+1900);
            }
            Table detailTable = createHeader(division.getDivision(), department.getDepartment(), strPeriod, intDateOfMonth, startDatePeriod);
            document.add(detailTable);
            
            Table tableEmpPresence = createHeaderDetailDinamis(intDateOfMonth, startDatePeriod, fontContent);

            /* generate employee attendance report's data */
            if(listAttendace!=null && listAttendace.size()>0) {
                try{
                    tableEmpPresence = (Table)getTableContent(tableEmpPresence, listAttendace);
                }catch(Exception ex){
                    System.out.println("Exception on createData : "+ex.toString());
                }
                if (!writer.fitsPage(tableEmpPresence)) {
                    tableEmpPresence.deleteLastRow(); 
		    document.add(tableEmpPresence);
                    document.setFooter(footer);
                    document.newPage();
                    tableEmpPresence = createHeaderDetailDinamis(intDateOfMonth, startDatePeriod, fontContent);
                }
            }
            //-------------End Generate employee attendance
            document.add(tableEmpPresence);
            
            Table tableFooterAssign = createFooterAssign(new Date());
            
            document.add(tableFooterAssign);
            
            
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
            int maxColumn = 13 + intDateOfMonth;                        
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 3;
            headerwidths[1] = 4;
            headerwidths[2] = 13;
            
            int index = 3;                        
            for(int j=0; j<intDateOfMonth; j++)
            {                    
                headerwidths[index] = 2.4f;  
                index++;                    
            }
            for(int k=0;k<10;k++){
                headerwidths[index] = 4;
                index++;
            }
            
            int iDiv = intDateOfMonth/4;
            int iSisa = intDateOfMonth % 4;

            // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(1);
            datatable.setSpacing(0); //1
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultRowspan(2);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_CENTER);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.setBackgroundColor(summaryColor);
            //datatable.setDefaultCellBorder(Cell.NO_BORDER);
            //datatable.setBorder(Table.NO_BORDER);
            
            // data no urut, payroll number and employee 
            datatable.addCell(new Phrase("NO", font1));
            datatable.addCell(new Phrase("ID #", font1));
            datatable.addCell(new Phrase("BAND MAMBER", font1));
            
             // untuk tanggal pertama dalam period yang dipilih
            datatable.addCell(new Phrase(""+(startDatePeriod), font1));
            for(int itDetail=0; itDetail<intDateOfMonth-1; itDetail++)
            {
                if(startDatePeriod==intDateOfMonth){
                    startDatePeriod = 1; 
                }
                else {
                    startDatePeriod = startDatePeriod + 1;
                }
                datatable.addCell(new Phrase(""+(startDatePeriod), font1));
            }
            //DP RECORD
            datatable.setDefaultColspan(4);
            datatable.setDefaultRowspan(1);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("DP RECORD", fontSubTitle));
             //AL RECORD
            datatable.setDefaultColspan(3);
            datatable.setDefaultRowspan(1);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("AL RECORD", fontSubTitle));
            //LL RECORD
            datatable.setDefaultColspan(3);
            datatable.setDefaultRowspan(1);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("LL RECORD", fontSubTitle));
          
            // detail data every date of month
            datatable.setDefaultColspan(1);
            datatable.setDefaultRowspan(1);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Prev.", fontSubTitle));
            datatable.addCell(new Phrase("Taken", fontSubTitle));
            datatable.addCell(new Phrase("New", fontSubTitle));
            datatable.addCell(new Phrase("Blc.", fontSubTitle));
            
            datatable.addCell(new Phrase("Prev.", fontSubTitle));
            datatable.addCell(new Phrase("Taken", fontSubTitle));
            datatable.addCell(new Phrase("Blc.", fontSubTitle));
            
            datatable.addCell(new Phrase("Prev.", fontSubTitle));
            datatable.addCell(new Phrase("Taken", fontSubTitle));
            datatable.addCell(new Phrase("Blc.", fontSubTitle));
            
            

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
     Membuat table header
     */
    private static Table createHeader(String division,String department,String strPeriod,int intDateOfMonth, int startDatePeriod){
        Table dataHeader = null;
        try{
            int maxColumn = 13 + intDateOfMonth;                        
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 3;
            headerwidths[1] = 4;
            headerwidths[2] = 13;
            
            int index = 3;                        
            for(int j=0; j<intDateOfMonth; j++)
            {                    
                headerwidths[index] = 2.4f;  
                index++;                    
            }
            for(int k=0;k<10;k++){
                headerwidths[index] = 4;
                index++;
            }
            
            int iDiv = (intDateOfMonth+4)/5;
            int iSisa = (intDateOfMonth+4) % 5;
            
            Image imgCentang = null;
            try{
                imgCentang = Image.getInstance(imgCentangRoot);
            }catch(Exception ex){}
            
            dataHeader = new Table(maxColumn);
            dataHeader.setPadding(1);
            dataHeader.setSpacing(0); //1
            dataHeader.setWidths(headerwidths);
            dataHeader.setWidth(100);
            dataHeader.setBackgroundColor(whiteColor);
            dataHeader.setDefaultCellBorder(Cell.NO_BORDER);
            dataHeader.setBorder(Table.NO_BORDER);
            
            /////////////////// Col 0
            dataHeader.setDefaultColspan(maxColumn);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            dataHeader.addCell(new Phrase("ATTENDANCE SUMMARY SHEET", fontHeader));
            dataHeader.setDefaultColspan(maxColumn);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            dataHeader.addCell(new Phrase("", fontHeader));
            
            ///////////////////Col 1
            dataHeader.setDefaultColspan(3);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("Division : "+division, fontTitle));
            
            dataHeader.setDefaultColspan(iDiv);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("A = Absent", fontTitle));
            dataHeader.setDefaultColspan(iDiv);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("V = Present", fontTitle));
            dataHeader.setDefaultColspan(iDiv);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("DP = Day Payment", fontTitle));
            dataHeader.setDefaultColspan(iDiv);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("EO = Extra Off", fontTitle));
            dataHeader.setDefaultColspan(iDiv+iSisa);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("Vi = Present In Only", fontTitle));
            
            dataHeader.setDefaultColspan(6);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("", fontTitle));
            
            /////////////////// Col 2
            dataHeader.setDefaultColspan(3);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("Department : "+department, fontTitle));
            
            dataHeader.setDefaultColspan(iDiv);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("AL = Annual Leave", fontTitle));
            dataHeader.setDefaultColspan(iDiv);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("X = Day Off", fontTitle));
            dataHeader.setDefaultColspan(iDiv);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("LL = Long Leave", fontTitle));
            dataHeader.setDefaultColspan(iDiv);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("PL = Paternity Leave", fontTitle));
            dataHeader.setDefaultColspan(iDiv+iSisa);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("Vo = Present Out Only", fontTitle));
            
            dataHeader.setDefaultColspan(6);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("", fontTitle));
            
            
            ///////////////////Col 3
            dataHeader.setDefaultColspan(3);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("", fontTitle));
            
            dataHeader.setDefaultColspan(iDiv);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("ML = Maternity Leave", fontTitle));
            dataHeader.setDefaultColspan(iDiv);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("H = Public Holiday", fontTitle));
            dataHeader.setDefaultColspan(iDiv);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("UL = Unpaid Leave", fontTitle));
            dataHeader.setDefaultColspan(iDiv);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("BL = Bereavement Leave", fontTitle));
            dataHeader.setDefaultColspan(iDiv+iSisa);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_LEFT);
            dataHeader.addCell(new Phrase("", fontTitle));
            
            dataHeader.setDefaultColspan(6);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_RIGHT);
            dataHeader.addCell(new Phrase("Periode : "+strPeriod, fontTitle));
            
            
        }catch(Exception ex){
            System.out.println("Exception on createHeader : "+ex.toString());
        }
        return dataHeader;
    }
    
    /**
     Membuat table header
     */
    private static Table createFooterAssign(Date date){
        Table dataHeader = null;
        try{
            int maxColumn = 2;                        
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 30;
            headerwidths[1] = 30;
            
           
            dataHeader = new Table(maxColumn);
            dataHeader.setPadding(1);
            dataHeader.setSpacing(0); //1
            dataHeader.setWidths(headerwidths);
            dataHeader.setWidth(100);
            dataHeader.setBackgroundColor(whiteColor);
            dataHeader.setDefaultCellBorder(Cell.NO_BORDER);
            dataHeader.setBorder(Table.NO_BORDER);
            
            /////////////////// Col 0
            dataHeader.setDefaultColspan(2);
            dataHeader.setDefaultRowspan(10);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            dataHeader.addCell(new Phrase("", fontFootAssign));
            
            dataHeader.setDefaultColspan(1);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            dataHeader.addCell(new Phrase("(                                                  )", fontFootAssign_u));
            
            dataHeader.setDefaultColspan(1);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            dataHeader.addCell(new Phrase(Formater.formatDate(date, "MMMM dd, yyyy"), fontFootAssign_u));
            
            dataHeader.setDefaultColspan(1);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            dataHeader.addCell(new Phrase("Dapartment Head", fontFootAssign));
            
            dataHeader.setDefaultColspan(1);
            dataHeader.setDefaultRowspan(1);            
            dataHeader.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            dataHeader.addCell(new Phrase("Date", fontFootAssign));
            
            
        }catch(Exception ex){
            System.out.println("Exception on createFooterAssign : "+ex.toString());
        }
        return dataHeader;
    }
    
    private static Table getTableContent(Table table, Vector data) throws BadElementException{
        Cell cell = new Cell("");
        Image imgCentang = null;
        try{
            imgCentang = Image.getInstance(imgCentangRoot);
        }catch(Exception ex){}
        if(data!=null && data.size()>0)
            {
                for (int i = 0; i < data.size(); i++) 
                {          
                    Vector itemAttendance = (Vector)data.get(i);
                    String empId = (String)itemAttendance.get(0);
                    String empNum = (String)itemAttendance.get(1);
                    String empName = (String)itemAttendance.get(2);
                    String periodId = (String)itemAttendance.get(3);
                    Vector vScheduleSymbol = (Vector)itemAttendance.get(5);
                    DpStockManagement dpStockManagement = (DpStockManagement)itemAttendance.get(6);
                    AlStockManagement alStockManagement = (AlStockManagement)itemAttendance.get(7);
                    LLStockManagement llStockManagement = (LLStockManagement)itemAttendance.get(8);
                    String totalDp = (String)itemAttendance.get(10);
                    String totalAl = (String)itemAttendance.get(11);
                    String totalLl = (String)itemAttendance.get(12);
                    String newDp = (String)itemAttendance.get(13);

                    // no, payroll and name
                    cell = new Cell(new Chunk(String.valueOf(i+1),fontContent));
                    cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cell.setBackgroundColor(whiteColor);
                   // cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    table.addCell(cell);

                    cell = new Cell(new Chunk(String.valueOf(empNum),fontContent));
                    cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cell.setBackgroundColor(whiteColor);
                   // cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    table.addCell(cell);

                    cell = new Cell(new Chunk(String.valueOf(empName),fontContent));
                    cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cell.setBackgroundColor(whiteColor);
                 //   cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    table.addCell(cell);          
                        
                    // list of schedule and duration, dimulai dari index ke 3
                    for(int isch=0; isch<vScheduleSymbol.size(); isch++)
                    {
                        if(String.valueOf(vScheduleSymbol.get(isch)).equals("&radic;")){
                            if(imgCentang!=null){
                                cell = new Cell(imgCentang);
                            }else{
                                cell = new Cell(new Chunk(String.valueOf("V"),fontContent));
                            }
                        }else{
                            cell = new Cell(new Chunk(String.valueOf(vScheduleSymbol.get(isch)),fontContent));
                        }
                        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cell.setBackgroundColor(whiteColor);
                 //       cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        table.addCell(cell);          
                    }

               //     for(int ic =0;ic<3;ic++){
                        // DP/AL/LL Prev
                    float iDpQty = (dpStockManagement!=null?dpStockManagement.getQtyResidue():0);
                    int iDpUsedQty = 0;
                    int iDpNewQty = 0;
                    try{
                        iDpUsedQty = Integer.parseInt(totalDp);
                    }catch(Exception ex){iDpUsedQty=0;}
                    try{
                        iDpNewQty = Integer.parseInt(newDp);
                    }catch(Exception ex){iDpNewQty=0;}
                    
                    float iPrev = iDpQty;
                    cell = new Cell(new Chunk(String.valueOf(iPrev),fontContent));
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cell.setBackgroundColor(whiteColor);
                    table.addCell(cell);

                    // DP/AL/LL Taken
                    int iTaken = iDpUsedQty;
                    cell = new Cell(new Chunk(String.valueOf(iTaken),fontContent));
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cell.setBackgroundColor(whiteColor);
                    table.addCell(cell);

                    int iNew = iDpNewQty;
                    cell = new Cell(new Chunk(String.valueOf(iNew),fontContent));
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cell.setBackgroundColor(whiteColor);
                    table.addCell(cell);

                    // DP/AL/LL balance
                    float iBalance = iPrev+iNew-iTaken;
                    cell = new Cell(new Chunk(String.valueOf(iBalance),fontContent));
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cell.setBackgroundColor(whiteColor);
                    table.addCell(cell);
                    
                    /////AL
                    Employee empCurr = new Employee();
                    try{
                        empCurr = PstEmployee.fetchExc(Long.parseLong(empId));
                    }catch(Exception ex){}
                    Date commDate = empCurr.getCommencingDate();
                    Date now = new Date();
                    int yearNow = now.getYear()+1900;
                    int yearComm = yearNow;
                    int monthYear = 1;
                    try{
                        yearComm = empCurr.getCommencingDate().getYear()+1900;
                        monthYear = empCurr.getCommencingDate().getMonth()+1;
                    }catch(Exception ex){
                        yearNow = yearNow-2;
                        monthYear = 1;
                    }
                    float entitled = 0;
                    float residue = 0;
                    if((yearNow - yearComm) > 1){
                    //	entitled = alStockManagement.getEntitled();
                            entitled = alStockManagement.getEntitled();
                            residue = entitled - alStockManagement.getQtyUsed();
                            //rowx.add("");
                    }
                    // jika bekerja setahun 
                    else if((yearNow - yearComm)==1){
                            if(monthYear==12){
                                    entitled = 0;
                                    residue = entitled - alStockManagement.getQtyUsed();
                            }
                            else{
                    //		entitled = alStockManagement.getEntitled() - monthYear;
                                    entitled = alStockManagement.getEntitled() - monthYear;
                                    residue = entitled - alStockManagement.getQtyUsed();
                            }
                    }else{
                            entitled = 0;
                    }
                
                    float iAlQty = residue;//(alStockManagement!=null?alStockManagement.getQtyResidue():0);
                    float iAlUsedQty = 0;
                    try{
                        iAlUsedQty = Integer.parseInt(totalAl);
                    }catch(Exception ex){iAlUsedQty=0;}
                    
                    float iPrevAl = iAlQty;
                    cell = new Cell(new Chunk(String.valueOf(iPrevAl),fontContent));
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cell.setBackgroundColor(whiteColor);
                    table.addCell(cell);

                    // DP/AL/LL Taken
                    float iTakenAl = iAlUsedQty;
                    cell = new Cell(new Chunk(String.valueOf(iTakenAl),fontContent));
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cell.setBackgroundColor(whiteColor);
                    table.addCell(cell);
                    
                    // DP/AL/LL balance
                    float iBalanceAl = iPrevAl-iTakenAl;
                    cell = new Cell(new Chunk(String.valueOf(iBalanceAl),fontContent));
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cell.setBackgroundColor(whiteColor);
                    table.addCell(cell);
                    
                    ///////////////// LL
                    
                    float iLlQty = (llStockManagement!=null?llStockManagement.getQtyResidue():0);
                    int iLlUsedQty = 0;
                    try{
                        iLlUsedQty = Integer.parseInt(totalLl);
                    }catch(Exception ex){iLlUsedQty=0;}
                    
                    float iPrevLl = iLlQty;
                    cell = new Cell(new Chunk(String.valueOf(iPrevLl),fontContent));
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cell.setBackgroundColor(whiteColor);
                    table.addCell(cell);

                    // DP/AL/LL Taken
                    int iTakenLl = iLlUsedQty;
                    cell = new Cell(new Chunk(String.valueOf(iTakenLl),fontContent));
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cell.setBackgroundColor(whiteColor);
                    table.addCell(cell);
                    
                    // DP/AL/LL balance
                    float iBalanceLl = iPrevLl-iTakenLl;
                    cell = new Cell(new Chunk(String.valueOf(iBalanceLl),fontContent));
                    cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cell.setBackgroundColor(whiteColor);
                    table.addCell(cell);
                  //  }
	        }
            }
        return table;
    }
}
