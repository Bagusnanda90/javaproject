/*
 * AttRecordPerEmployeePdf.java
 *
 * Created on July 13, 2004, 1:18 PM
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
import com.dimata.harisma.entity.attendance.Presence;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.PstMachineTransaction;
import com.dimata.harisma.entity.attendance.PstPresence;
import com.dimata.harisma.entity.leave.LeaveOidSym;
import com.dimata.harisma.entity.leave.PstLeaveApplication;
import com.dimata.harisma.entity.overtime.Overtime;
import com.dimata.harisma.entity.overtime.OvertimeDetail;
import com.dimata.harisma.entity.overtime.PstOvertimeDetail;
import com.dimata.harisma.entity.payroll.PayComponent;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.session.attendance.*;
import com.dimata.harisma.session.payroll.I_PayrollCalculator;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.system.entity.PstSystemProperty;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import java.util.logging.Logger;
import javax.swing.border.Border;

/**
 *
 * @author  gedhy
 */
public class AttRecordDailyPerEmployeeKadivPdf extends HttpServlet {
    
    /** Attribute declaration
    */
    public static String textCurrency[] = {"IDR","USD"};

    /* declaration constant */
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);
    public static Color titleColor = new Color(200,200,200);
    public static Color summaryColor = new Color(240,240,240);
    public static String formatDate  = "MMM dd, yyyy";
    public static String formatNumber = "#,###";

    /* setting some fonts in the color chosen by the user */
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 11, Font.BOLD, blackColor);
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL, blackColor);
    public static Font fontContent = new Font(Font.COURIER, 7, Font.NORMAL, blackColor);
    
    private static long Al_oid = 0;
    private static long DP_oid = 0;
    private static long LL_oid = 0;

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
        try {
            processRequest(request, response);
        } catch (DocumentException ex) {
            Logger.getLogger(AttRecordDailyPerEmployeePdf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DocumentException ex) {
            Logger.getLogger(AttRecordDailyPerEmployeePdf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);            
        }
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
    throws ServletException, IOException, DocumentException {

        /* setting some constant */
        String currText[] = {"(IRD)","(US$)"};

        /* creating the document object */
        Document document = new Document(PageSize.A4, 5, 5, 40, 40);

	/* creating an OutputStream */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try 
        {
            /* creating an instance of the writer */
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            /* get data from session */
            HttpSession sessEmpPresence = request.getSession(true);

    Date selectedDateFrom = FRMQueryString.requestDateVer3(request, "check_date_start");
    Date selectedDateTo = FRMQueryString.requestDateVer3(request, "check_date_finish");
    String empNum = FRMQueryString.requestString(request, "emp_number");
    String fullName = FRMQueryString.requestString(request, "full_name");
    
    //update by satrya 2013-04-08
    int reason_sts = FRMQueryString.requestInt(request, "reason_status");
    //update by satrya 2012-09-28
    //String status1st = FRMQueryString.requestString(request, "status_schedule1st");
    //String status2nd = FRMQueryString.requestString(request, "status_schedule2nd");         
    long oidDepartment = FRMQueryString.requestLong(request, "department");        
    long oidSection = FRMQueryString.requestLong(request, "section");    
    
        //update by satrya 2013-1202
    long oidCompany = FRMQueryString.requestLong(request, "hidden_companyId");
     long oidDivision = FRMQueryString.requestLong(request, "hidden_divisionId");
     
    Date date = FRMQueryString.requestDate(request, "date");
     int vectSize = 0;
    //String  whereClause ="";
    ///deklarasi variable
     int recordToGet = 40000;

     String sStatusResign = FRMQueryString.requestString(request, "statusResign"); 
    int statusResign=0;
    if(sStatusResign!=null && sStatusResign.length()>0){
        statusResign = Integer.parseInt(sStatusResign); 
    }
    
    
    //update by satrya 2013-03-29
   String[] stsSchedule = null;
    String stsScheduleSel = "";
            stsSchedule = new String[PstEmpSchedule.strPresenceStatus.length]; 
            //Vector stsScheduleSel= new Vector(); 
            int maxStsSchedule = 0;
            
            for(int j = 0 ; j < PstEmpSchedule.strPresenceStatusIdx.length ; j++){                
                String name = "STS_SCH_"+PstEmpSchedule.strPresenceStatusIdx[j];
                String val = FRMQueryString.requestString(request,name);
                stsSchedule[j] = val;
                if(val!=null && val.length()>0){  
                   stsScheduleSel = stsScheduleSel + ""+PstEmpSchedule.strPresenceStatusIdx[j]+","; 
                }
                maxStsSchedule++;
            }
    
    String[] stsEmpCategory = null; 
    int sizeCategory = PstEmpCategory.listAll()!=null ? PstEmpCategory.listAll().size():0;   
    stsEmpCategory = new String[sizeCategory]; 
    //Vector stsEmpCategorySel= new Vector(); 
    String stsEmpCategorySel = "";
    int maxEmpCat = 0; 
    for(int j = 0 ; j < sizeCategory ; j++){                
        String name = "EMP_CAT_"+j;
        String val = FRMQueryString.requestString(request,name);
        stsEmpCategory[j] = val;
        if(val!=null && val.length()>0){ 
           //stsEmpCategorySel.add(""+val); 
            stsEmpCategorySel = stsEmpCategorySel + val+",";
        }
        maxEmpCat++;
    }
try {
    Al_oid = Long.parseLong(PstSystemProperty.getValueByName("OID_AL"));
    DP_oid = Long.parseLong(PstSystemProperty.getValueByName("OID_DP"));
    LL_oid = Long.parseLong(PstSystemProperty.getValueByName("OID_LL"));
} catch (Exception exc) {
    System.out.println("Exception:"+ exc);
}

 ///cek untuk mendqapatkan insentif
  I_Atendance attdConfig = null;
try {
    attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
} catch (Exception e) {
    System.out.println("Exception : " + e.getMessage());
    System.out.println("Please contact your system administration to setup system property: LEAVE_CONFIG ");
}

                       //update by satrya 2013-03-29
String getEmployeePresence="";
   String[] stsPresence = null;
            stsPresence = new String[Presence.STATUS_ATT_IDX.length]; 
            Vector stsPresenceSel= new Vector(); 
            int max1 = 0;
            
            for(int j = 0 ; j < Presence.STATUS_ATT_IDX.length ; j++){                
                String name = "ATTD_"+Presence.STATUS_ATT_IDX[j];
                String val = FRMQueryString.requestString(request,name);
                stsPresence[j] = val;
                if(val!=null && val.equals("1")){ 
                   stsPresenceSel.add(""+Presence.STATUS_ATT_IDX[j]); 
                }
                max1++;
            }
           
            /* opening the document, needed for add something into document */
            int newpage=0;
            document.open();
            //document.newPage();
            //document.add(new Paragraph("First page of the document."));
           
             Vector vCompany = PstCompany.list(0, 0, oidCompany==0?"":PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]+"="+oidCompany, PstCompany.fieldNames[PstCompany.FLD_COMPANY]+" ASC ");
            for(int c=0; c < vCompany.size(); c++){
               Company company = (Company) vCompany.get(c);
               Vector vdivision = PstDivision.list(0,1000, oidDivision!=0?PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+"="+oidDivision:PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+company.getOID(),PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" ASC ");            
                for(int div=0; div < vdivision.size(); div++){
                     Division division = (Division) vdivision.get(div);                     
                     Vector vdepartment = PstDepartment.list(0, 1000, 
                         PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+"="+division.getOID()
                            + ( oidDepartment ==0 ? "": " AND "+PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+"="+oidDepartment ) 
                          , PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+" ASC ");
                    for(int dep=0; dep < vdepartment.size(); dep++){
                       Department department = (Department) vdepartment.get(dep);
                       //oidDepartment= department.getOID();
                       //hanya untuk test
                       if(department.getDepartment().equalsIgnoreCase("CONTROLLER")){
                        boolean test=true;
                       }
                       Vector vsection = PstSection.listByEmployee(0, 1000, department.getOID(),oidSection,empNum,fullName,PstSection.fieldNames[PstSection.FLD_SECTION]+" ASC ");
                        boolean loopOnlyDep=false;
                        if(vsection==null || vsection.size()<1){
                          loopOnlyDep = true;  
                        }
                        for(int sec=0; sec < vsection.size() || loopOnlyDep==true; sec++){
                            Vector listAttendanceRecordDailly = new Vector(1, 1);
                            com.dimata.harisma.entity.masterdata.Section sectionSel = null;
                            if(!loopOnlyDep){                                                            
                                sectionSel = (com.dimata.harisma.entity.masterdata.Section) vsection.get(sec);
                                
                                //SessEmpSchedule sessEmpSchedule = new SessEmpSchedule();                                 
                                 
                               // if(oidSection ==0){
                                    //oidSection = sectionSel.getOID();
                                //}        
                            }else{
                                oidSection =0;
                            }
                            loopOnlyDep = false;

	// list record yang sesuai 	
	//vectListAL = SessLeaveManagement.listSummaryAlStockInt(srcLeaveManagement, start, recordToGet); 
          String order = "DATE("+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " )ASC, "
                        + "TIME("+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " )ASC, " 
                        + PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID]
                        + " , "+ PstPresence.fieldNames[PstPresence.FLD_STATUS] + " ASC ";
          
          //update by satrya 2013-12-06
          boolean cekAdaAttdance = SessEmpSchedule.getCheckAdaDataPresence(department.getOID(), selectedDateFrom, selectedDateTo,  sectionSel==null ? 0: sectionSel.getOID(), empNum, fullName, stsScheduleSel, 0, recordToGet,stsPresenceSel,reason_sts,stsEmpCategorySel,statusResign,company.getOID(),division.getOID());
          if(cekAdaAttdance){
              listAttendanceRecordDailly = SessEmpSchedule.listEmpPresenceDaily(department.getOID(), selectedDateFrom, selectedDateTo,  sectionSel==null ? 0: sectionSel.getOID(), empNum, fullName, stsScheduleSel, 0, recordToGet,stsPresenceSel,reason_sts,stsEmpCategorySel,statusResign,company.getOID(),division.getOID());
          }else{
              //kasusnya : ada salah karyawan yg tidak ada sectionnya tpi ada juga yg ada didalam 1 department
              //jadi sectionnya di set 0
              listAttendanceRecordDailly = SessEmpSchedule.listEmpPresenceDaily(department.getOID(), selectedDateFrom, selectedDateTo, 0, empNum, fullName, stsScheduleSel, 0, recordToGet,stsPresenceSel,reason_sts,stsEmpCategorySel,statusResign,company.getOID(),division.getOID());
          }
          //update by satrya 2013-12-06
	  //listAttendanceRecordDailly = SessEmpSchedule.listEmpPresenceDaily(department.getOID(), selectedDateFrom, selectedDateTo,  sectionSel==null ? 0: sectionSel.getOID(), empNum, fullName, stsScheduleSel, 0, recordToGet,stsPresenceSel,reason_sts,stsEmpCategorySel,statusResign,company.getOID(),division.getOID());
                                                                      
         
            /* adding a Header of page, i.e. : title, align and etc */
           if(listAttendanceRecordDailly==null || listAttendanceRecordDailly.size()<1){
               continue;
           }
            //PresenceReportDaily presenceReportDailyFirst = (PresenceReportDaily) listAttendanceRecordDailly.get(0);
             //Vector listPresencePersonalInOut = null;
          //update by satrya 2013-04-02
          Vector listPresencePersonalInOut =  PstPresence.list(vectSize , recordToGet, order, department.getOID(), fullName.trim(), selectedDateFrom, selectedDateTo, sectionSel==null ? 0: sectionSel.getOID(), empNum.trim(),stsPresenceSel,stsEmpCategorySel,statusResign,company.getOID(),division.getOID());
         
          //update by satrya 2012-09-13
          Vector listOvertimeDaily = PstOvertimeDetail.listOvertime(0, recordToGet,department.getOID(), fullName.trim(), selectedDateFrom, selectedDateTo, sectionSel==null ? 0: sectionSel.getOID(), empNum.trim(), "",stsPresenceSel,company.getOID(),division.getOID());
          //update by satrya 2012-09-18
          //Vector listHoliday =  PstPublicHolidays.getHoliday(selectedDateFrom, selectedDateTo);
          HolidaysTable holidaysTable = PstPublicHolidays.getHolidaysTable(selectedDateFrom, selectedDateTo);                                                                                             
           /* String strDepartment = department.getDepartment();
            String strEmployee = "";
            String strPeriod = "";// from to date
            Vector vEmpPresece =  new Vector();
            * /*/
            drawList(document,writer,company.getCompany(),division.getDivision(),department.getDepartment(), 
                    sectionSel==null? "-":sectionSel.getSection(),
                    (listAttendanceRecordDailly !=null && listAttendanceRecordDailly.size() > 0 ? listAttendanceRecordDailly : new Vector()),
                    (listPresencePersonalInOut !=null && listPresencePersonalInOut.size() > 0 ? listPresencePersonalInOut : new Vector()),(listOvertimeDaily !=null && listOvertimeDaily.size() > 0 ? listOvertimeDaily : new Vector()),holidaysTable,  selectedDateFrom,selectedDateTo,fullName,empNum,newpage,attdConfig);
              newpage=1;
                        } // for section
                    } // for department
                } // for division
            } // for company
        } 
        catch(DocumentException de) {
            System.err.println(de.getMessage());
            de.printStackTrace();
            throw new DocumentException(de.getMessage());
        }
        
        /* closing the document */
        document.close();

        /* 
         * we have written the pdfstream to a ByteArrayOutputStream, now going to write this outputStream to the ServletOutputStream
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
    public static Table getTableHeader() throws BadElementException, DocumentException {

           Table tableEmpPresence = new Table(15);
           tableEmpPresence.setCellpadding(1);
           tableEmpPresence.setCellspacing(1);
           //tableEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
           int widthHeader[] = {4, 8, 4, 2,4, 4, 5, 5, 2, 3, 5,2,3,2,2};
    	   tableEmpPresence.setWidths(widthHeader);
           tableEmpPresence.setWidth(100);
           
          
           Cell cellEmpPresence = new Cell(new Chunk("Date",fontTitle));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM );
           cellEmpPresence.setBorderColor(blackColor);
           cellEmpPresence.setRowspan(2);
           //cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk("Date Description",fontTitle));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM );
           cellEmpPresence.setBorderColor(blackColor);
           cellEmpPresence.setRowspan(2);
           //cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           

           cellEmpPresence = new Cell(new Chunk("Sch",fontTitle));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM );
           cellEmpPresence.setBorderColor(blackColor);
           cellEmpPresence.setRowspan(2);
           //cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk("Act",fontTitle));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM );
           cellEmpPresence.setBorderColor(blackColor);
           cellEmpPresence.setRowspan(2);
           //cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk("Time IN",fontTitle));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
          cellEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM );
           cellEmpPresence.setBorderColor(blackColor);
           cellEmpPresence.setRowspan(2);
           //cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk("Time OUT",fontTitle));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
          cellEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM );
           cellEmpPresence.setBorderColor(blackColor);
           cellEmpPresence.setRowspan(2);
           //cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk("Break OUT",fontTitle));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
          cellEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM );
           cellEmpPresence.setBorderColor(blackColor);
           cellEmpPresence.setRowspan(2);
           //cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk("Break IN",fontTitle));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
         cellEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM );
           cellEmpPresence.setBorderColor(blackColor);
           cellEmpPresence.setRowspan(2);
           //cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk("Ov. Frm",fontTitle));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cellEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM );
           cellEmpPresence.setBorderColor(blackColor);
           cellEmpPresence.setRowspan(2);
           //cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk("Allo wance",fontTitle));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM );
           cellEmpPresence.setBorderColor(blackColor);
           cellEmpPresence.setRowspan(2);
           //cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk("Paid  /DP",fontTitle));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM );
           cellEmpPresence.setBorderColor(blackColor);
           cellEmpPresence.setRowspan(2);
           //cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk("Insentif",fontTitle));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
          cellEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM );
           cellEmpPresence.setBorderColor(blackColor);
           cellEmpPresence.setRowspan(2);
           //cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk("NET OT",fontTitle));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM );
           cellEmpPresence.setBorderColor(blackColor);
           cellEmpPresence.setRowspan(2);
           //cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
           cellEmpPresence = new Cell(new Chunk("OT .Idx",fontTitle));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM );
           cellEmpPresence.setBorderColor(blackColor);
           cellEmpPresence.setRowspan(2);
           //cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
           
            cellEmpPresence = new Cell(new Chunk("Sts",fontTitle));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM );
           cellEmpPresence.setBorderColor(blackColor);
           cellEmpPresence.setRowspan(2);
           //cellEmpPresence.setBackgroundColor(summaryColor);
           tableEmpPresence.addCell(cellEmpPresence);
       
           return tableEmpPresence;
    }    
    
    public void drawList(Document document, PdfWriter writer, String companyName, String divName, String depName, String secName, Vector listAttendanceRecordDaillyByDate, Vector listPresencePersonalInOut,Vector listOvertimeDaily,HolidaysTable holidaysTable,  Date selectDateFrom, Date selectDateTo,String fullName,String empNum,int newpage,I_Atendance attdConfig) {
        Vector result = new Vector(1, 1);
       
        
        Hashtable attPerEmployees = new Hashtable();
        Vector vAllPerEmployee = new Vector();
        
        //untuk special Leave <satrya 2012-08-01>
                    Vector listScheduleSymbol = new Vector(1, 1);
                    try{
                        
                         listScheduleSymbol.add(new Long(PstSystemProperty.getValueByName("OID_SPECIAL")));
                     }catch(Exception E){
                         
                         System.out.println("EXCEPTION SYS PROP OID_SPECIAL : "+E.toString());
                     }

                     try{
                         listScheduleSymbol.add(new Long(PstSystemProperty.getValueByName("OID_UNPAID")));
                       }catch(Exception E){
                       System.out.println("EXCEPTION SYS PROP OID_UNPAID : "+E.toString());
                     }
                  Hashtable scheduleSymbolIdMap = PstScheduleSymbol.getScheduleSymbolIdMap(listScheduleSymbol);
                   Hashtable breakTimeDuration = PstScheduleSymbol.getBreakTimeDuration();
                
                       //update by satrya 2012-10-08
                /**
                    untuk pengaturan apakah mendapatkan insentif atau tidak
                **/
                        int iPropInsentifLevel = 0;//hanya cuti full day jika fullDayLeave = 0
                        try{
                            iPropInsentifLevel = Integer.parseInt(PstSystemProperty.getValueByName("PAYROLL_INSENTIF_MAX_LEVEL"));
                        }catch(Exception ex){
                            System.out.println("Execption PAYROLL_INSENTIF_MAX_LEVEL: " + ex);
                        }
                      ///cek untuk mendqapatkan insentif
                    I_PayrollCalculator payrollCalculatorConfig = null;
                    try{
                        payrollCalculatorConfig = (I_PayrollCalculator)(Class.forName(PstSystemProperty.getValueByName("PAYROLL_CALC_CLASS_NAME")).newInstance());
                    }catch(Exception e) {
                        System.out.println("Exception PAYROLL_CALC_CLASS_NAME " + e.getMessage());
                    }
                     //update by satrya 2014-03-10
                    if(payrollCalculatorConfig!=null){
                        payrollCalculatorConfig.loadEmpCategoryInsentif();
                    }
                    
                    //update by satrya 2013-04-09
      int showOvertime = 0;
    try{
        showOvertime = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY"));
    }catch(Exception ex){

        System.out.println("<blink>ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY NOT TO BE SET</blink>" ); 
        showOvertime=0; 
    }
                           /**
                    untuk cek apakah department house kipping perlu dtp insentif
                **/
//                        long iPropDeptInstf = -1;
//                        try{
//                            iPropDeptInstf = Long.parseLong(PstSystemProperty.getValueByName("PAYROLL_INSENTIF_DEPT_ID_EXCEPT")); 
//                        }catch(Exception ex){
//                           
//                            //System.out.println("Execption PAYROLL_INSENTIF_MAX_LEVEL: " + ex);
//                            System.out.println("Execption PAYROLL_INSENTIF_DEPT_ID_EXCEPT NOT TO BE SET" );
//                        }
                
                  //update by satrya 2012-08-05
                /**
                    untuk melakukan settingan jika statusnya full day atau jam-jam'an
                **/
                        int iLeaveMinuteEnable = 0;//hanya cuti full day jika fullDayLeave = 0
                        try{
                            iLeaveMinuteEnable = Integer.parseInt(PstSystemProperty.getValueByName("LEAVE_MINUTE_ENABLE"));
                        }catch(Exception ex){
                            System.out.println("Execption LEAVE_MINUTE_ENABLE: " + ex);
                        }
          // priska 20150610
                        Hashtable outletname = new Hashtable();
                        String InEmpNum = " 1";
                        
                        //str1.contains(cs1)
                        
                        for ( int is = 0 ; is < listAttendanceRecordDaillyByDate.size(); is++ ){
                            PresenceReportDaily presenceReportDailyEmpNum = (PresenceReportDaily)listAttendanceRecordDaillyByDate.get(is);
                            if (presenceReportDailyEmpNum != null && (!InEmpNum.contains(presenceReportDailyEmpNum.getEmpNum().toString())) ){
                            InEmpNum = InEmpNum + "," +presenceReportDailyEmpNum.getEmpNum();
                            }
                        }
                        Date newDateTo = (Date) selectDateTo.clone();
                        newDateTo.setDate(newDateTo.getDate() + 1);
                        String wherestation = " he." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " IN ( "+ InEmpNum +") AND ( hmt." + PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS] +" >= \""+ Formater.formatDate(selectDateFrom, "yyyy-MM-dd") + " 00:00:00\"" + " AND hmt." + PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS] +" <= \""+ Formater.formatDate(newDateTo, "yyyy-MM-dd") + " 23:59:59\""  +" )" ;
                        outletname = PstMachineTransaction.GetStationByDate(0, 0, wherestation, PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS]);
                             
        if (listAttendanceRecordDaillyByDate != null && listAttendanceRecordDaillyByDate.size() > 0) {
            
            // generate hastable per employee
            for (int i = 0; i < listAttendanceRecordDaillyByDate.size(); i++) { 
                listAttendanceRecordDaillyByDate.get(i);
                PresenceReportDaily presenceReportDaily = (PresenceReportDaily) listAttendanceRecordDaillyByDate.get(i);
                //update by satrya 2012-07-26
                String strPayrolNumber = ""+presenceReportDaily.getEmpId()+""+presenceReportDaily.getEmpNum();
                if(attPerEmployees.containsKey(strPayrolNumber)){
                    Vector vPerEmployee = (Vector) attPerEmployees.get(strPayrolNumber) ;
                    vPerEmployee.add(presenceReportDaily);                    
                } else {
                    Vector vPerEmployee =  new Vector();
                    vAllPerEmployee.add(vPerEmployee);
                    vPerEmployee.add(presenceReportDaily);
                    attPerEmployees.put(strPayrolNumber, vPerEmployee);
                }

            }            
            
            
            
            ///hidden by satrya 2013-04-4
           /* Vector statusVal = new Vector();
            Vector statusTxt = new Vector();
            for (int s = 0; s < PstEmpSchedule.strSymStatus.length; s++) {
                statusVal.add("" + s);
                statusTxt.add("" + PstEmpSchedule.strSymStatus[s]);
            }*/
            ////getTable

            
            
            //int index = -1;
           // Vector list = new Vector(1, 1);

            // vector of data will used in pdf report            
            if(listAttendanceRecordDaillyByDate !=null && listAttendanceRecordDaillyByDate.size() > 0){
                String employeeNumber="";
                String dateTest="";
                try {
          if(vAllPerEmployee!=null && vAllPerEmployee.size()>0){  
            for(int iPe=0;iPe<vAllPerEmployee.size();iPe++){
            Vector listAttendanceRecordDailly = (Vector)  vAllPerEmployee.get(iPe);
            // Buat header per employee per page
                   //strPayrolNumber
                   //strEmpFullName            
            // add table
            // set table header
            /* create header */
            /* adding a Header of page, i.e. : title, align and etc */
           /*if(iPe > 0){
               document.newPage();
           }*/ 
            //update by satrya 2012-11-18
            //masalahnya di newpage
            if(newpage > 0){
                 document.newPage();
               
            }
           if(listAttendanceRecordDailly==null && listAttendanceRecordDailly.size()<1){
               continue;
           }
            
            PresenceReportDaily presenceReportDailyFirst = (PresenceReportDaily) listAttendanceRecordDailly.get(0);
             newpage = newpage + 1;
            /*HeaderFooter header = new HeaderFooter(new Phrase(strReportTitle, fontHeader), false);
            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);*/
            /*document.add(new Paragraph("ATTENDANCE RECORD PER EMPLOYEE"));
            document.add(new Paragraph("EMPLOYEE_ID___:" + presenceReportDailyFirst.getEmpNum() ));
            document.add(new Paragraph("EMPLOYEE_NAME_:" + presenceReportDailyFirst.getEmpFullName() ));
            document.add(new Paragraph("COMPANY_______:" + companyName));
            document.add(new Paragraph("DIVISION______:" + divName));
            document.add(new Paragraph("DEPARTMENT____:" + depName));*/
            
           Table tableHeader = new Table(7);
           tableHeader.setCellpadding(0);
           tableHeader.setCellspacing(0);
           //tableEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
           int widthHeader[] = {4, 1, 10,1,4, 1, 10};
    	   tableHeader.setWidths(widthHeader);
           tableHeader.setWidth(100);
          
           Cell  objTableNew;
           objTableNew = new Cell (new Chunk("ATTENDANCE REPORT PER EMPLOYEE",fontHeader));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setHorizontalAlignment(Cell.ALIGN_LEFT);
           objTableNew.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           objTableNew.setBorderColor(whiteColor);
           objTableNew.setColspan(7);
           tableHeader.addCell(objTableNew);
           
          
           ///payrol number
           objTableNew = new Cell (new Chunk("Payrol Number",fontTitle));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(":"));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(presenceReportDailyFirst.getEmpNum(),fontTitle));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           objTableNew = new Cell (new Chunk(""));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           objTableNew = new Cell (new Chunk("Name",fontTitle));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(":"));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(presenceReportDailyFirst.getEmpFullName(),fontTitle));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           
           
           objTableNew = new Cell (new Chunk("Company",fontTitle));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(":"));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(companyName,fontTitle));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           objTableNew = new Cell (new Chunk(""));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           objTableNew = new Cell (new Chunk("Division",fontTitle));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(":"));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(divName,fontTitle));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           
           objTableNew = new Cell (new Chunk("Departement",fontTitle));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(":"));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(depName,fontTitle));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           objTableNew = new Cell (new Chunk(""));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           objTableNew = new Cell (new Chunk("Section",fontTitle));
           objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(":"));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
            objTableNew = new Cell (new Chunk(secName,fontTitle));
            objTableNew.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           objTableNew.setBorderColor(whiteColor);
           tableHeader.addCell(objTableNew);
           
            document.add(tableHeader);
            
            
            
            Table tableEmpPresence = getTableHeader();

            /* generate employee attendance report's data */
            Cell cellEmpData = new Cell("");  
            double   totalNetOv =0.0;
            double totalOvIdx= 0.0;
            String sTotalNetOv ="0";
            String sTotalOvIdx="0";
           // int iTotalInsentif=0;
            int totalInsentif=0;
            int totalInsentif2nd=0;
            Hashtable cekTglSamaDlmEmployeeSama = new Hashtable();
            for (int i = 0; i < listAttendanceRecordDailly.size(); i++) { 
                PresenceReportDaily presenceReportDaily = (PresenceReportDaily) listAttendanceRecordDailly.get(i);
                 employeeNumber = presenceReportDaily.getEmpNum();
                //cekkking tanggal yg sama dalam employee yg sama
                if(cekTglSamaDlmEmployeeSama!=null && cekTglSamaDlmEmployeeSama.get(presenceReportDaily.getSelectedDate())!=null){
                    long empTestIdSama = (Long)cekTglSamaDlmEmployeeSama.get(presenceReportDaily.getSelectedDate());
                    if(empTestIdSama==presenceReportDaily.getEmpId()){
                        System.out.println("Tgl yg Sama"+presenceReportDaily.getSelectedDate()+" Emp Id"+presenceReportDaily.getEmpId());
                        continue;
                    }
                }
                //update by satrya 2012-07-26
                String strCodeReason1st = PstReason.getStrReasonCode(presenceReportDaily.getReasonNo1nd());
                String strCodeReason2st = PstReason.getStrReasonCode(presenceReportDaily.getReasonNo2nd());
               // long dtSch = new Date().getTime();
                long lDatePresence = presenceReportDaily.getSelectedDate().getTime();
                //String strEmpFullName = presenceReportDaily.getEmpFullName();
                //String strPayrolNumber = presenceReportDaily.getEmpNum();
                long religion_id = presenceReportDaily.getReligion_id();
                //update by satrya 2012-08-01
                long lEmpId = presenceReportDaily.getEmpId();
                Date dateLeave = new Date(lDatePresence);
                // String sDateLeave = Formater.formatTimeLocale(dateLeave, "yyyy-MM-dd");
                 /*int leaveStatus =-1;
                try{
                    leaveStatus = PstLeaveApplication.getLeaveFormStatus(lEmpId, sDateLeave);
                  }catch(Exception ex){
                      System.out.println("Exception Leave Status "+ex);
                  }*/
                 
              
               
                //String strEmpNum = presenceReportDaily.getEmpNum();
               // SimpleDateFormat formatter = new SimpleDateFormat("d/MM/yyyy");
                SimpleDateFormat formatterDay = new SimpleDateFormat("EEEE");
                String dayString = formatterDay.format(presenceReportDaily.getSelectedDate());

                int iPositionLevel = PstPosition.iGetPositionLevel(presenceReportDaily.getEmpId());
                int intScheduleCategory1st = presenceReportDaily.getSchldCategory1st();
                String strSchldSymbol1 = presenceReportDaily.getScheduleSymbol1();
                Date dtSchldIn1st = (Date) presenceReportDaily.getScheduleIn1st();
                Date dtSchldOut1st = (Date) presenceReportDaily.getScheduleOut1st();
                Date dtActualIn1st = (Date) presenceReportDaily.getActualIn1st();
                Date dtActualOut1st = (Date) presenceReportDaily.getActualOut1st();
                
                
                 /**
                    update by satrya
                    untuk menampilkan AL,LL, DP,etc
               **/
                 long oidLeave  = 0;
                  String sSymbolLeave = ""; 
               Vector listLeaveAplication =  PstLeaveApplication.listOid(lEmpId, dtSchldIn1st,dtSchldOut1st);
               if(listLeaveAplication !=null && listLeaveAplication.size()>0){
                   //LeaveOidSym obj = new LeaveOidSym();
                   try{
                   for (int j = 0; j < listLeaveAplication.size(); j++) {
                    LeaveOidSym leaveOidSym = (LeaveOidSym) listLeaveAplication.get(j);
                    oidLeave = leaveOidSym.getLeaveOid();
                    String sSymbolLeaveX= (String.valueOf(leaveOidSym.getLeaveSymbol()));
                    sSymbolLeave = sSymbolLeave  + sSymbolLeaveX + ",";
                }
                    if(sSymbolLeave!=null && sSymbolLeave.length()>0){
                         sSymbolLeave= sSymbolLeave.substring(0,sSymbolLeave.length()-1); 
                        }
                 
                   }catch(Exception ex){System.out.println("Exception list Leave Application"+ex);}
                
               }
                //update by satrya 2012-09-26
                // Date dtSchldBO1st = (Date) presenceReportDaily.getScheduleBO1st();
                // String sDtSchldBO1st = Formater.formatDate(dtSchldBO1st, "HH:mm");
                 
               // Date dtSchldBO2nd = (Date) presenceReportDaily.getScheduleBO2nd();
                // String sDtSchldBO2nd = Formater.formatDate(dtSchldBO2nd, "HH:mm");
                 
                // Date dtSchldBI1st = (Date) presenceReportDaily.getScheduleBI1st();
                // String sDtSchldBI1st = Formater.formatDate(dtSchldBI1st, "HH:mm");
                   
                  //Date dtSchldBI2nd = (Date) presenceReportDaily.getScheduleBI2nd();
                 // String sDtSchldBI2nd = Formater.formatDate(dtSchldBI2nd, "HH:mm");
                   
                  //String strScheduleDesc1st = (String) presenceReportDaily.getScheduleDesc1st();
                  //String strScheduleDesc2nd = (String) presenceReportDaily.getScheduleDesc2nd();
                //int presenceStatus = (int) presenceReportDaily.getPresenceStatus();
                                       //update by satrya 2012-07-23
                // Hashtable reasonMap = PstReason.getReason(0, 500, "", PstReason.fieldNames[PstReason.FLD_REASON]); 

              // String strNote = presenceReportDaily.getNote1nd().equals("") ? "-" : presenceReportDaily.getNote1nd();
               String strStatus = PstEmpSchedule.strSymStatus[presenceReportDaily.getStatus1()] !=null ? PstEmpSchedule.strSymStatus[presenceReportDaily.getStatus1()] : "-";
               //update by satrya 2012-09-27
                String  pSelectedDate = Formater.formatDate( presenceReportDaily.getSelectedDate(), "yyyy-MM-dd");
                dateTest=pSelectedDate;
                if(dateTest.equalsIgnoreCase("2014-07-29")){ 
                    boolean ds=true;
                }
               // String sPresenceDateTime = Formater.formatDate( presenceReportDaily.getPresenceDateTime(), "yyyy-MM-dd");
                //untuk yg 2nd
              //  int intScheduleCategory2nd = presenceReportDaily.getSchldCategory2nd();
                String strSchldSymbol2 = presenceReportDaily.getScheduleSymbol2().toUpperCase();
                Date dtSchldIn2nd = (Date) presenceReportDaily.getScheduleIn2nd();
                Date dtSchldOut2nd = (Date) presenceReportDaily.getScheduleOut2nd();
                Date dtActualIn2nd = (Date) presenceReportDaily.getActualIn2nd();
                Date dtActualOut2nd = (Date) presenceReportDaily.getActualOut2nd();
                ///update by satrya 2012-07-23
             //  String strNote2nd = presenceReportDaily.getNote2nd().equals("") ? "-" : presenceReportDaily.getNote2nd();
                String strStatus2nd =PstEmpSchedule.strSymStatus[presenceReportDaily.getStatus2()] !=null ? PstEmpSchedule.strSymStatus[presenceReportDaily.getStatus2()] :"-";
                //Vector rowx = new Vector();
                //Vector rowxPdf = new Vector();

                // ---> SPLIT SHIFT / EOD SCHEDULE					
                //if(intScheduleCategory==PstScheduleCategory.CATEGORY_SPLIT_SHIFT)
                //update by satrya 2012-08-19
               /* String inputName = "" + presenceReportDaily.getEmpScheduleId() + "_d_" + presenceReportDaily.getSelectedDate().getDate();
                String inputName2nd = "" + presenceReportDaily.getEmpScheduleId() + "_d2nd_" + presenceReportDaily.getSelectedDate().getDate();
                Vector reason_value = new Vector(1, 1);
                Vector reason_key = new Vector(1, 1);
                Vector listReason = new Vector(1, 1);
                //String selectedReason = String.valueOf(presenceReportDaily.getSelectedDate());
                listReason = PstReason.list(0, 0, "", "REASON");
                for (int r = 0; r < listReason.size(); r++) {
                    Reason reason = (Reason) listReason.get(r);
                    reason_key.add(reason.getReason());
                    reason_value.add(String.valueOf(reason.getNo()));
                }*/
                //end
                 String payCompCode = PayComponent.COMPONENT_INS; 
                String bOut ="";
                String bIn = "";
                //update by satrya 2012-09-13
                //menginisialisasikan variable untuk overtime
                String insentif ="";
                 long ovId=0;
                String oVForm ="";
                String allwance ="";
                String paid ="";
                //double   NetOv =0.0;
                String   NetOv ="";
                //double oVerIdx= 0.0;
                String oVerIdx= "";
                Presence preBOut = null;
                long preBreakOut = 0;
                long preBreakIn = 0;
                long breakDuration =0L;
                //update by satrya 2012-10-09
                //menginisialisasikan variable untuk Holiday
                String daysHolidayName = "";
                if(holidaysTable.isHoliday(religion_id !=0 ? religion_id : 0, presenceReportDaily.getSelectedDate())){
                   //daysHolidayName = holidaysTable.getDescHoliday();
                        daysHolidayName = holidaysTable.getDescHoliday(religion_id,presenceReportDaily.getSelectedDate());

                }
                if(listPresencePersonalInOut!=null && listPresencePersonalInOut.size() > 0 ){
                     for(int bIdx = 0;bIdx < listPresencePersonalInOut.size();bIdx++){
                         Presence presenceBreak = (Presence) listPresencePersonalInOut.get(bIdx);//yang di cari harus ada leavenya
                         if(presenceBreak.getEmployeeId()==presenceReportDaily.getEmpId() 
                                  && (DateCalc.dayDifference(presenceBreak.getPresenceDatetime(),presenceReportDaily.getSelectedDate())==0 )
                                  && presenceBreak.getScheduleDatetime()== null ){ 
                              if(presenceBreak.getStatus()== Presence.STATUS_OUT_ON_DUTY){
                                  String pBreak = Formater.formatDate(presenceBreak.getPresenceDatetime(), "yyyy-MM-dd HH:mm:ss");
                                  Object stationB = outletname.get(presenceReportDaily.getEmpNum()+"_"+pBreak);
                                  bOut =bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm:ss  ")+"/"+stationB+" ";                                  
                                  listPresencePersonalInOut.remove(bIdx);
                                  bIdx = bIdx -1;
                              }
                              else if(presenceBreak.getStatus()== Presence.STATUS_CALL_BACK){
                                  String pBreak = Formater.formatDate(presenceBreak.getPresenceDatetime(), "yyyy-MM-dd HH:mm:ss");
                                  Object stationB = outletname.get(presenceReportDaily.getEmpNum()+"_"+pBreak);
                                  bIn =bIn+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm:ss  ")+"/"+stationB+" ";                                  
                                   listPresencePersonalInOut.remove(bIdx);
                                  bIdx = bIdx -1;
                              }  
                          }
                         else if( presenceBreak.getScheduleDatetime()!=null && presenceBreak.getEmployeeId()==presenceReportDaily.getEmpId() 
                                  && 
                                  (DateCalc.dayDifference(presenceBreak.getScheduleDatetime(),presenceReportDaily.getSelectedDate())==0 )){
                             double oneMonth = (double) (2505600000D); 
                             if(presenceBreak.getStatus()== Presence.STATUS_OUT_PERSONAL){
                                  //update by satrya 2012-09-27
                                  //priska 20150526 menambahkan fungsi untuk mengecek kesalahan periode pada saat menampilkan break out
                                  long diffschduleprensencedate = Math.abs(presenceBreak.getScheduleDatetime().getTime() - presenceBreak.getPresenceDatetime().getTime());
                                  
                                  if (diffschduleprensencedate < oneMonth){
                                    if(presenceBreak.getScheduleDatetime()==null || (presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())){ ///jika karyawan mendahului istirahat
                                        preBreakOut = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PO 
                                    }else{
                                        preBreakOut = presenceBreak.getScheduleDatetime().getTime(); //yang di pakai mengurangi adalah schedule PO
                                    }
                                    String pBreak = Formater.formatDate(presenceBreak.getPresenceDatetime(), "yyyy-MM-dd HH:mm:ss");
                                    Object sOut = outletname.get(presenceReportDaily.getEmpNum()+"_"+pBreak);
                    
                                    bOut =bOut+ "P:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm:ss  ")+"/"+sOut+" ";
                                  
                                  }
                                    
                                
                              } else if(presenceBreak.getStatus()== Presence.STATUS_OUT_ON_DUTY){
                                  String pBreak = Formater.formatDate(presenceBreak.getPresenceDatetime(), "yyyy-MM-dd HH:mm:ss");
                                  Object stationB = outletname.get(presenceReportDaily.getEmpNum()+"_"+pBreak); 
                                  bOut =bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm:ss  ")+"/"+stationB+" ";                                  
                              }else if(presenceBreak.getStatus()== Presence.STATUS_IN_PERSONAL){
                                  long diffschduleprensencedate = Math.abs(presenceBreak.getScheduleDatetime().getTime() - presenceBreak.getPresenceDatetime().getTime());
                                  
                                  if (diffschduleprensencedate < oneMonth){
                                      String pBreak = Formater.formatDate(presenceBreak.getPresenceDatetime(), "yyyy-MM-dd HH:mm:ss");
                                      Object stationB = outletname.get(presenceReportDaily.getEmpNum()+"_"+pBreak); 
                                      bIn =bIn+ "P:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm:ss  ")+"/"+stationB+" ";    
                                  }
                                                                
                                  //if(preBOut!=null && preBOut.getStatus()==Presence.STATUS_OUT_PERSONAL){
                                   if(preBreakOut !=0L){   
                                  //update by satrya 2012-09-27
                                  if(presenceBreak.getScheduleDatetime()==null ||  presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()){ ///jika karyawan melewati jam istirahat
                                      preBreakIn = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                  }else{
                                      preBreakIn = presenceBreak.getScheduleDatetime().getTime(); //yang di pakai mengurangi adalah schedule PI
                                  }
                                   breakDuration = breakDuration + preBreakIn -  preBreakOut;
                                   preBreakOut =0L;
                                    //breakDuration = breakDuration + presenceBreak.getPresenceDatetime().getTime()-  preBOut.getPresenceDatetime().getTime(); 
                                   // preBOut=null;
                                  }                                   
                              } else if(presenceBreak.getStatus()== Presence.STATUS_CALL_BACK){
                                  String pBreak = Formater.formatDate(presenceBreak.getPresenceDatetime(), "yyyy-MM-dd HH:mm:ss");
                                  Object stationB = outletname.get(presenceReportDaily.getEmpNum()+"_"+pBreak); 
                                  bIn =bIn+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm:ss  ")+"/"+stationB+" ";                                  
                              }                              
                              listPresencePersonalInOut.remove(bIdx);                              
                              bIdx=bIdx-1;                              
                         }//end if
                           /* else{
                           if(presenceReportDaily.getScheduleId1()!=0 ){//&& sPresenceDateTime.equals(pSelectedDate)){
                           try{                          
                            breakDuration = ((Long)breakTimeDuration.get(""+presenceReportDaily.getScheduleId1())).longValue(); //scheduleSymbol.getBreakIn().getTime()  - scheduleSymbol.getBreakOut().getTime(); 
                            //listPresencePersonalInOut.remove(bIdx);                              
                            //bIdx=bIdx-1;
                            //break; 
                           }catch(Exception ex){
                               System.out.println("Exception scheduleSymbol"+ex);
                           }
                         } 

                       }*/
                     }//end for
                    }//listPresencePersonalInOut
                   //jika employee tidak ada yang keluar maka akan di potong jam istirahat default
                     //update by satrya 2012-09-19
                    else{
                        if(presenceReportDaily.getScheduleId1()!=0 ){//&& sPresenceDateTime.equals(pSelectedDate)){
                        try{                          
                         breakDuration = ((Long)breakTimeDuration.get(""+presenceReportDaily.getScheduleId1())).longValue(); //scheduleSymbol.getBreakIn().getTime()  - scheduleSymbol.getBreakOut().getTime(); 
                        }catch(Exception ex){
                            System.out.println("Exception scheduleSymbol"+ex);
                        }
                      } 
                        
                    }
                //update by satrya 2012-09-13
                     //list Overtime
                if(showOvertime==0){
                      pSelectedDate = Formater.formatDate( presenceReportDaily.getSelectedDate(), "yyyy-MM-dd");
                     if(listOvertimeDaily!=null && listOvertimeDaily.size()> 0){  
                     for(int oVx = 0;oVx < listOvertimeDaily.size();oVx++){
                         OvertimeDetail overtimeDetail = (OvertimeDetail) listOvertimeDaily.get(oVx);
                         
                         String pdateOv = Formater.formatDate(overtimeDetail.getDateFrom(), "yyyy-MM-dd");
                         if(overtimeDetail.getOID() !=0 && overtimeDetail.getEmployeeId()==presenceReportDaily.getEmpId()
                               && pdateOv.equals(pSelectedDate)){
                               ovId= overtimeDetail.getOID();
                            oVForm = I_DocStatus.fieldDocumentStatusShort[overtimeDetail.getStatus()];
                            if(overtimeDetail.getStatus()==I_DocStatus.DOCUMENT_STATUS_PROCEED){
                            allwance = Overtime.allowanceType[overtimeDetail.getAllowance()]; 
                            }
                            //allwance = Overtime.allowanceType[overtimeDetail.getAllowance()]; 
                            paid = OvertimeDetail.paidByKey[overtimeDetail.getPaidBy()];
                            if(overtimeDetail.getNetDuration() !=0.0){
                                  NetOv = Formater.formatNumber(overtimeDetail.getRoundDuration(), "###.##");  
                                  //NetOv = Formater.formatNumber(overtimeDetail.getNetDuration(), "###.##");  
                                   totalNetOv = totalNetOv+overtimeDetail.getRoundDuration();
                                   // totalNetOv = totalNetOv+overtimeDetail.getNetDuration();
                                   sTotalNetOv = Formater.formatNumber(totalNetOv,  "###.##");
                                   
                            }
                            if(overtimeDetail.getTot_Idx() != 0.0){
                                  oVerIdx = Formater.formatNumber(overtimeDetail.getTot_Idx(), "###.##");
                                  totalOvIdx = totalOvIdx+overtimeDetail.getTot_Idx();
                                   sTotalOvIdx = Formater.formatNumber(totalOvIdx,  "###.##");
                            }
                              listOvertimeDaily.remove(oVx);                              
                              oVx=oVx-1;
                              break;
                         }
                     }
                    }
                }//end cek show overtime
                   
                    //untuk insentif 1st
                boolean isInsentif=false;
                if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
                    isInsentif = payrollCalculatorConfig.checkPayrollComponent(payCompCode, presenceReportDaily.getEmpId(), presenceReportDaily.getDepartement_id(), presenceReportDaily.getSelectedDate(),holidaysTable.isHoliday(religion_id !=0 ? religion_id : 0, presenceReportDaily.getSelectedDate()), oidLeave, ovId, presenceReportDaily.getStatus1(), iPositionLevel, iPropInsentifLevel, strSchldSymbol1,presenceReportDaily.getEmpCategoryId());
                }
                
                if (dtSchldIn2nd != null && dtSchldOut2nd != null) {
                    // ---> FIRST SCHEDULE															
                    // calculate working duration
                   // String strDurationFirst = getWorkingDuration(dtActualIn1st, dtActualOut1st, breakDuration);

                    // process generate string time interval for selected schedule
                    String strDtSchldIn = Formater.formatTimeLocale(dtSchldIn1st);
                    String strDtSchldOut = Formater.formatTimeLocale(dtSchldOut1st);
                    boolean schedule1stWithoutInterval = false;
                    if (!(intScheduleCategory1st == PstScheduleCategory.CATEGORY_REGULAR
                            || intScheduleCategory1st == PstScheduleCategory.CATEGORY_SPLIT_SHIFT
                            || intScheduleCategory1st == PstScheduleCategory.CATEGORY_NIGHT_WORKER
                            || intScheduleCategory1st == PstScheduleCategory.CATEGORY_ACCROSS_DAY
                            || intScheduleCategory1st == PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY)) {
                        if (strDtSchldIn.compareTo(strDtSchldOut) == 0) {
                            strDtSchldIn = "-";
                            strDtSchldOut = "-";
                            schedule1stWithoutInterval = true;
                        }
                    }

                    /* -------------- selected Date  --------------*/
                    cellEmpData = new Cell(new Chunk(Formater.formatTimeLocale(presenceReportDaily.getSelectedDate(), "dd-MMM-yy"),fontTitle));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);

                    /* -------------- Date Desc  --------------*/
                     if(dayString.equalsIgnoreCase("Saturday")){
                        if(daysHolidayName.length() > 0){
                             daysHolidayName = " Saturday "+daysHolidayName;
                        }else{
                            daysHolidayName=" Saturday ";
                        }
                    }else if(dayString.equalsIgnoreCase("Sunday")){
                        if(daysHolidayName.length() > 0){
                             daysHolidayName = " Sunday "+daysHolidayName;
                        }else{
                             daysHolidayName = " Sunday ";
                        }
                    }
                     //update by satrya 2014-08-15
                     if(daysHolidayName==null){
                         daysHolidayName="";
                     }
                    cellEmpData = new Cell(new Chunk((daysHolidayName),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    /* -------------- Date Schedule --------------*/
                    if(strSchldSymbol2 ==null && strSchldSymbol2.length() < 1){
                        cellEmpData = new Cell(new Chunk(String.valueOf(""),fontContent));
                    }else{
                        cellEmpData = new Cell(new Chunk((strSchldSymbol1),fontContent));
                    }
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    /* -------------- Act / Late,not ok --------------*/
                    if(strCodeReason2st ==null && strCodeReason2st.length() < 1){
                        cellEmpData = new Cell(new Chunk(String.valueOf(""),fontContent));
                    }else{
                        cellEmpData = new Cell(new Chunk((strCodeReason2st),fontContent));
                    }
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    /* -------------- Time In --------------*/
                    if(dtActualIn2nd !=null){
                         cellEmpData = new Cell(new Chunk (Formater.formatTimeLocale(dtActualIn2nd, "HH:mm:ss"),fontContent));
                    }else{
                          cellEmpData = new Cell(new Chunk(String.valueOf(""),fontContent));
                    }
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    /* -------------- Time Out --------------*/
                    if(dtActualOut2nd !=null){
                         cellEmpData = new Cell(new Chunk (Formater.formatTimeLocale(dtActualOut2nd, "HH:mm:ss"),fontContent));
                    }else{
                          cellEmpData = new Cell(new Chunk(String.valueOf(""),fontContent));
                    }
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    /* -------------- Break Out --------------*/
                    if(bOut.length() < 1){
                        bOut="-";
                    }
                    cellEmpData = new Cell(new Chunk ((bOut),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                     /* -------------- Break IN --------------*/
                    if(bIn.length() < 1){
                        bIn="-";
                    }
                    cellEmpData = new Cell(new Chunk ((bIn),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    
                     /* --------------  OvForm --------------*/
                    cellEmpData = new Cell(new Chunk ((oVForm),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);

                    /* -------------- Food/Money --------------*/
                    cellEmpData = new Cell(new Chunk ((allwance),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);

                    /* -------------- Paid --------------*/
                    cellEmpData = new Cell(new Chunk ((paid),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);

                   /* -------------- Insentif --------------*/
                /*    if(presenceReportDaily.getStatus2() == PstEmpSchedule.STATUS_PRESENCE_OK 
                            && oidLeave==0 && !strSchldSymbol1.equalsIgnoreCase("off")){
                        insentif = "x";
                        totalInsentif2nd = totalInsentif2nd + 1;
                    }*/
                      if(isInsentif){
                          insentif = "x";
                        totalInsentif2nd = totalInsentif2nd + 1;
                    }else{
                        insentif = "";
                    }
                    cellEmpData = new Cell(new Chunk ((insentif),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    
                    /* -------------- net Ot --------------*/
                    cellEmpData = new Cell(new Chunk ((NetOv),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                                       
                    /* -------------- OvIdx --------------*/
                    cellEmpData = new Cell(new Chunk ((oVerIdx),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    /* ------------- status --------------- */
                     if(strStatus2nd ==null && strStatus2nd.length() < 1){
                        cellEmpData = new Cell(new Chunk(String.valueOf(""),fontContent));
                    }else{
                        cellEmpData = new Cell(new Chunk((strStatus2nd),fontContent));
                    }
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    // ---> SECOND SCHEDULE	
                } // ---> REGULAR SCHEDULE			
                else {
                    
                                        
                    /* -------------- selected Date  --------------*/
                    cellEmpData = new Cell(new Chunk(Formater.formatTimeLocale(presenceReportDaily.getSelectedDate(), "dd-MMM-yy"),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);

                    /* -------------- Date Desc  --------------*/
                    if(dayString.equalsIgnoreCase("Saturday")){
                        if(daysHolidayName!=null && daysHolidayName.length() > 0){
                             daysHolidayName = " Saturday "+"("+daysHolidayName+")";
                        }else{
                            daysHolidayName=" Saturday ";
                        }
                    }else if( daysHolidayName!=null &&  dayString.equalsIgnoreCase("Sunday")){
                        if(daysHolidayName.length() > 0){
                             daysHolidayName = " Sunday "+"("+daysHolidayName+")";
                        }else{
                             daysHolidayName = " Sunday ";
                        }
                    }
                    cellEmpData = new Cell(new Chunk((daysHolidayName),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    /* -------------- Date Schedule --------------*/
                    String sSymbol ="";
                        if(strSchldSymbol1 !=null && strSchldSymbol1.length() >0){
                            
                              if(Al_oid != presenceReportDaily.getScheduleId1() && LL_oid != presenceReportDaily.getScheduleId1() && DP_oid != presenceReportDaily.getScheduleId1()){
                                  if(iLeaveMinuteEnable !=1){
                                        sSymbol =strSchldSymbol1;
                                  }else{  
                                      if(sSymbolLeave !=null && sSymbolLeave.length() > 0){
                                          sSymbol =strSchldSymbol1 +" / "+sSymbolLeave;
                                      }
                                      else{
                                          sSymbol = strSchldSymbol1;
                                                  
                                      }                            
                                    }                            
                            } else{ 
                                  if(iLeaveMinuteEnable !=1){
                                  sSymbol = strSchldSymbol1 ;
                                   
                                    }else
                                    {
                                     sSymbol = strSchldSymbol1 + " / " + sSymbolLeave;
                                    }
                             }
                        }else{
                          sSymbol="";
                        }
     
                    cellEmpData = new Cell(new Chunk((sSymbol),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    /* -------------- Act / Late,not ok --------------*/
                    if(strCodeReason1st ==null && strCodeReason1st.length() < 1){
                        cellEmpData = new Cell(new Chunk(String.valueOf(""),fontContent));
                    }else{
                        cellEmpData = new Cell(new Chunk((strCodeReason1st),fontContent));
                    }
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    /* -------------- Time In --------------*/
                    if(dtActualIn1st !=null){
                        String fDtActualIn1st = Formater.formatDate(dtActualIn1st, "yyyy-MM-dd HH:mm:ss");
                        Object stationIn = outletname.get(presenceReportDaily.getEmpNum()+"_"+fDtActualIn1st);
                        cellEmpData = new Cell(new Chunk (Formater.formatTimeLocale(dtActualIn1st, "HH:mm:ss") + "/" + (stationIn != null ? stationIn : "-" ),fontContent));
                    }else{
                        cellEmpData = new Cell(new Chunk(String.valueOf(""),fontContent));
                    }
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    /* -------------- Time Out --------------*/
                    if(dtActualOut1st !=null){
                    String fDtActualOut1st = Formater.formatDate(dtActualOut1st, "yyyy-MM-dd HH:mm:ss");
                    Object stationOut = outletname.get(presenceReportDaily.getEmpNum()+"_"+fDtActualOut1st);
                         cellEmpData = new Cell(new Chunk (Formater.formatTimeLocale(dtActualOut1st, "HH:mm:ss") + "/"+ (stationOut != null ? stationOut : "-" ) ,fontContent));
                    }else{
                          cellEmpData = new Cell(new Chunk(String.valueOf(""),fontContent));
                    }
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    /* -------------- Break Out --------------*/
                    cellEmpData = new Cell(new Chunk ((bOut),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                     /* -------------- Break IN --------------*/
                     
                    cellEmpData = new Cell(new Chunk ((bIn),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    
                     /* --------------  OvForm --------------*/
                    
                    cellEmpData = new Cell(new Chunk ((oVForm),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);

                    /* -------------- Food/Money --------------*/
                    
                    cellEmpData = new Cell(new Chunk ((allwance),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);

                    /* -------------- Paid --------------*/
                    
                    cellEmpData = new Cell(new Chunk ((paid),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    /* -------------- Insentif --------------*/

                   if(isInsentif){
                           insentif = "x";
                        totalInsentif = totalInsentif + 1;
                    }else{
                        insentif = "";
                    }
                    cellEmpData = new Cell(new Chunk ((insentif),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    
                    /* -------------- net Ot --------------*/
                    cellEmpData = new Cell(new Chunk ((NetOv),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                    
                                        
                    
                    /* -------------- OvIdx --------------*/
                    cellEmpData = new Cell(new Chunk ((oVerIdx),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
    
                    /** ------------- status -------------- */
                     if(strStatus ==null && strStatus.length() < 1){
                        cellEmpData = new Cell(new Chunk(String.valueOf(""),fontContent));
                    }else{
                        cellEmpData = new Cell(new Chunk((strStatus),fontContent));
                    }
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                    cellEmpData.setBorderColor(blackColor);
                    tableEmpPresence.addCell(cellEmpData);
                }//end else
                
                cekTglSamaDlmEmployeeSama.put(presenceReportDaily.getSelectedDate(),presenceReportDaily.getEmpId());
                  if (!writer.fitsPage(tableEmpPresence)) 
                    {
                        tableEmpPresence.deleteLastRow();
                         tableEmpPresence.deleteLastRow();
                        i=i>1? i-2 : 0;
                        document.add(tableEmpPresence);
                        document.newPage();
                        tableEmpPresence = getTableHeader();
                    }            
              }//listAttendanceRecordDailly
               // PAGE BREAK 
            ///add total
           //tableEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
          
           //Cell  objTableNewTotal;
           cellEmpData = new Cell (new Chunk("Total",fontTitle));
           cellEmpData.setBorder(Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
           //cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpData.setBorderColor(whiteColor);
           cellEmpData.setColspan(10);
           tableEmpPresence.addCell(cellEmpData);
           
           cellEmpData = new Cell (new Chunk(":",fontContent));
           cellEmpData.setBorder(Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           cellEmpData.setBorderColor(whiteColor);
           tableEmpPresence.addCell(cellEmpData);
           ///untuk Total insentif
           cellEmpData = new Cell (new Chunk(String.valueOf(totalInsentif),fontContent));
           cellEmpData.setBorder(Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           cellEmpData.setBorderColor(whiteColor);
           cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
           tableEmpPresence.addCell(cellEmpData);
           ///untuk total net Overtime
           cellEmpData = new Cell (new Chunk(sTotalNetOv,fontContent));
           cellEmpData.setBorder(Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           cellEmpData.setBorderColor(whiteColor);
           cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
           tableEmpPresence.addCell(cellEmpData);
           //untuk total overtime index
           cellEmpData = new Cell (new Chunk(sTotalOvIdx,fontContent));
           cellEmpData.setBorder(Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
           cellEmpData.setBorderColor(whiteColor);
           cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
           tableEmpPresence.addCell(cellEmpData);
              
              document.add(tableEmpPresence);
            }
       }
           }catch(Exception ex){
               
               System.out.println("Exception presenceReportDaily : " +ex.toString() + " Emp.Number:"+employeeNumber+" Date:"+dateTest);
               
           }
            }//end list
                //document.drawList(document, index);
        } else {
             String strReportTitle = " no data...";
        }
        
    }
  
}
