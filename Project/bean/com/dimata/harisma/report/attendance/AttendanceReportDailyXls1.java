/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.attendance;

import com.dimata.harisma.entity.attendance.AttendanceReportDaily;
import com.dimata.harisma.entity.attendance.I_Atendance;
import com.dimata.harisma.entity.attendance.LeaveCheckTakenDateFinish;
import com.dimata.harisma.entity.attendance.Presence;
import com.dimata.harisma.entity.attendance.PstEmpSchedule;
import com.dimata.harisma.entity.attendance.PstMachineTransaction;
import com.dimata.harisma.entity.attendance.PstPresence;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.leave.LeaveOidSym;
import com.dimata.harisma.entity.leave.PstLeaveApplication;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Division;
import com.dimata.harisma.entity.masterdata.HolidaysTable;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstPublicHolidays;
import com.dimata.harisma.entity.masterdata.PstReason;
import com.dimata.harisma.entity.masterdata.PstScheduleCategory;
import com.dimata.harisma.entity.masterdata.PstScheduleSymbol;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.masterdata.ScheduleSymbol;
import com.dimata.harisma.entity.masterdata.Section;
import com.dimata.harisma.entity.overtime.Overtime;
import com.dimata.harisma.entity.overtime.OvertimeDetail;
import com.dimata.harisma.entity.overtime.PstOvertimeDetail;
import com.dimata.harisma.entity.payroll.PayComponent;
import com.dimata.harisma.entity.payroll.PayGeneral;
import com.dimata.harisma.entity.payroll.PstPayGeneral;
import com.dimata.harisma.entity.search.SrcReportDailyXls;
import com.dimata.harisma.session.attendance.PresenceReportDaily;
import com.dimata.harisma.session.attendance.SessEmpSchedule;
import com.dimata.harisma.session.leave.SessLeaveApp;
import com.dimata.harisma.session.payroll.I_PayrollCalculator;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.DateCalc;
import com.dimata.util.Formater;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;

import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.model.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
/**
 *
 * @author Satrya Ramayu
 */
public class AttendanceReportDailyXls1 extends HttpServlet{
    
    
     
    /** Initializes the servlet.
    */  
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    /** Destroys the servlet.
    */  
    public void destroy() {

    }

    
    private static HSSFFont createFont(HSSFWorkbook wb, int size, int color, boolean isBold) {
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) size);
        font.setColor((short) color);
        if (isBold) {
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        return font;
    }
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
     private static long Al_oid = 0;
    private static long DP_oid = 0;
    private static long LL_oid = 0;
    @SuppressWarnings("empty-statement")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
       
       // System.out.println("\r=== specialquery_list.jsp ===");
        try {
            
        }
        catch (Exception e) {
            System.out.println("Exception SpecialQuery export Excel"+e);
        }
       
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
    
     //int recordToGet = 40000;

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
 int showOvertime = 0;
try {
    showOvertime = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY"));
} catch (Exception ex) {

    System.out.println("<blink>ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY NOT TO BE SET</blink>");
    showOvertime = 0;
}

 I_PayrollCalculator payrollCalculatorConfig = null; 
 try{
      payrollCalculatorConfig = (I_PayrollCalculator)(Class.forName(PstSystemProperty.getValueByName("PAYROLL_CALC_CLASS_NAME")).newInstance());
 }catch(Exception exc){
 
 };
//update by satrya 2014-03-10
      if(payrollCalculatorConfig!=null){
          payrollCalculatorConfig.loadEmpCategoryInsentif();
      }
 
 int iPropInsentifLevel = 0;//hanya cuti full day jika fullDayLeave = 0
    try{
        iPropInsentifLevel = Integer.parseInt(PstSystemProperty.getValueByName("PAYROLL_INSENTIF_MAX_LEVEL"));
    }catch(Exception ex){

        //System.out.println("Execption PAYROLL_INSENTIF_MAX_LEVEL: " + ex);
        System.out.println("<blink>PAYROLL_INSENTIF_MAX_LEVEL NOT TO BE SET</blink>" );
    }
//update by satrya 2014-02-01
HolidaysTable holidaysTable = PstPublicHolidays.getHolidaysTable(selectedDateFrom, selectedDateTo);
 Hashtable hashPositionLevel = PstPosition.hashGetPositionLevel();  
SrcReportDailyXls srcReportDailyXls = new SrcReportDailyXls();
srcReportDailyXls.setEmpNum(empNum);
srcReportDailyXls.setFullName(fullName);
srcReportDailyXls.setOidDepartment(oidDepartment);
srcReportDailyXls.setOidSection(oidSection);
srcReportDailyXls.setReason_sts(reason_sts);
srcReportDailyXls.setSelectedDateFrom(selectedDateFrom);
srcReportDailyXls.setSelectedDateTo(selectedDateTo);
srcReportDailyXls.setStatusResign(statusResign);
srcReportDailyXls.setStatusSchedule(stsScheduleSel);
srcReportDailyXls.setStsEmpCategorySel(stsEmpCategorySel);
srcReportDailyXls.addStatusPresence(stsPresenceSel);
srcReportDailyXls.setOidCompany(oidCompany);
srcReportDailyXls.setOidDivision(oidDivision);
Vector listOvertimeDaily = null;
Vector vOvertimeDetail = new Vector();
if(showOvertime==0){
        
          listOvertimeDaily = PstOvertimeDetail.listOvertime(0, 0, oidDepartment, fullName.trim(), 
                  selectedDateFrom, selectedDateTo, oidSection, empNum.trim(), "",stsPresenceSel,oidCompany,oidDivision); 
          
           vOvertimeDetail = PstOvertimeDetail.listOvertimeDetail(0, 0, oidDepartment, fullName.trim(), 
                  selectedDateFrom, selectedDateTo, oidSection, empNum.trim(), "",stsPresenceSel,oidCompany,oidDivision);
        
}


        Vector vListReportDaily = SessEmpSchedule.listEmpPresenceDailyXls(srcReportDailyXls); 
        Hashtable hashSchSymbol = PstScheduleSymbol.getHashTlScheduleAll();
        System.out.println("---===| Excel Report |===---");
        response.setContentType("application/x-msexcel");

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Attedance Report Daily");

        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        
         HSSFCellStyle style3RedFont = wb.createCellStyle();
        style3RedFont.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style3RedFont.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        style3RedFont.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style3RedFont.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style3RedFont.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style3RedFont.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //style3RedFont.setFont();
        HSSFFont fontRed = wb.createFont();
        fontRed.setColor(new HSSFColor.RED().getIndex()); 
        style3RedFont.setFont(fontRed);
        //style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFCellStyle style3 = wb.createCellStyle();
        style3.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());//HSSFCellStyle.GREY_25_PERCENT);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        
        HSSFCellStyle styleTitle = wb.createCellStyle();
        styleTitle.setFillBackgroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleTitle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());//HSSFCellStyle.WHITE);
        styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleTitle.setFont(createFont(wb, 12, HSSFFont.COLOR_NORMAL /*HSSFFont.BLACK*/, true));
        // Create a row and put some cells in it. Rows are 0 based.
        // Create a row and put some cells in it. Rows are 0 based.
        //HSSFRow row = sheet.createRow((short) 0);
        //HSSFCell cell = row.createCell((short) 0);
        
        //update by satrya 2014-02-01
        //pemberian warna font
        CellStyle styleFont = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(HSSFColor.RED.index);
        styleFont.setFont(font);
        
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        
           String printHeader = PstSystemProperty.getValueByName("PRINT_HEADER");

            if (printHeader.equals(PstSystemProperty.SYS_NOT_INITIALIZED)) {
                printHeader = "";
            }
            PayGeneral payGeneral = new PayGeneral();
            if(srcReportDailyXls!=null && srcReportDailyXls.getOidCompany()!=0){
               try{
                payGeneral = PstPayGeneral.fetchExc(srcReportDailyXls.getOidCompany());
               }catch(Exception exc){
               
               }
            }
            Division division = new Division();
            if(srcReportDailyXls!=null && srcReportDailyXls.getOidDivision()!=0){
              try{
                division = PstDivision.fetchExc( srcReportDailyXls.getOidDivision());
              }catch(Exception exc){
              
              }
            }
            Department department = new Department();
            if(srcReportDailyXls!=null && srcReportDailyXls.getOidDepartment()!=0){
               try{
                department =PstDepartment.fetchExc(srcReportDailyXls.getOidDepartment());
               }catch(Exception exc){
               
               }
            }
            Section section = new Section();
            if(srcReportDailyXls!=null && srcReportDailyXls.getOidSection()!=0){
               try{
                section = PstSection.fetchExc(srcReportDailyXls.getOidSection());
               }catch(Exception exc){
               
               }
            }
            String companyName = payGeneral!=null && payGeneral.getCompanyName().length()>0?payGeneral.getCompanyName():"-";
            String divisionName = division!=null && division.getDivision().length()>0?division.getDivision():"-";
            String departmenName = department!=null && department.getDepartment().length()>0?department.getDepartment():"-";
            String sectionName = section!=null && section.getSection().length()>0?section.getSection():"-";
            String[] tableTitle = {
                printHeader,
                "REPORT ATENDANCE DAILY",
                "DATE : " + (srcReportDailyXls.getSelectedDateFrom() != null ? Formater.formatDate(srcReportDailyXls.getSelectedDateFrom(), "dd-MM-yyyy") : "") + " To " + (srcReportDailyXls.getSelectedDateTo() != null ? Formater.formatDate(srcReportDailyXls.getSelectedDateTo(), "dd-MM-yyyy") : ""),
                "COMPANY : " + companyName,
                "DIVISION : " + divisionName,
                "DEPARTEMENT : " + departmenName,
                "SECTION : " + sectionName
            };

        int countRow = 0;
        /**
         * 
         */
        for (int k = 0; k < tableTitle.length; k++) {
                row = sheet.createRow((short) (countRow));
               
                countRow++;
                cell = row.createCell((short) 0);
                cell.setCellValue(tableTitle[k]);
                cell.setCellStyle(styleTitle);
         }
        
        
        
        row = sheet.createRow((short) 6);
        cell = row.createCell((short) 0);
        int totTanpaOvertime=6;
        //row = sheet.createRow((short) (0));
        if(showOvertime==0){
           totTanpaOvertime=11;
            String[] tableHeaderWithOT = {

            "No","Payrol Number","Nama","Company","Division","Departement","Section","Date"
            ,"Schedule","Time In","Time Out","Break Out","Break In","Diff In"
            ,"Diff Out","Duration","insentif","Ot.Form","Allowance","Paid/Dp","Net.Ot","Reason","Note","Status"

            };
             for (int k = 0; k < tableHeaderWithOT.length; k++) {
                cell = row.createCell((short) k);
                cell.setCellValue(tableHeaderWithOT[k]);
                cell.setCellStyle(style3);
            }
        }else{
           String[] tableHeaderNotOT = {

            "No","Payrol Number","Nama","Company","Division","Departement","Section","Date"
            ,"Schedule","Time In","Time Out","Break Out","Break In","Diff In"
            ,"Diff Out","Duration","Reason","Note","Status"

            };
            for (int k = 0; k < tableHeaderNotOT.length; k++) {
                cell = row.createCell((short) k);
                cell.setCellValue(tableHeaderNotOT[k]);
                cell.setCellStyle(style3);
            }
        }

        
        
        
     countRow = 6 ;
      // int addRow =0;
      if(vListReportDaily!=null && vListReportDaily.size()>0){
         int no=1;
         //long tmpEmpId=0;
         String order = "DATE("+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " )ASC, "
                        + "TIME("+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " )ASC, " 
                        + PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID]
                        + " , "+ PstPresence.fieldNames[PstPresence.FLD_STATUS] + " ASC ";  
         Vector listPresencePersonalInOut =  PstPresence.list(0 , 0, order, oidDepartment, fullName.trim(), 
              selectedDateFrom, selectedDateTo, oidSection, empNum.trim(),stsPresenceSel,stsEmpCategorySel,statusResign); 
         Hashtable breakTimeDuration = PstScheduleSymbol.getBreakTimeDuration();
         Hashtable hashReason = PstReason.getReason(0, 0, "", "");
         //Hashtable tmpDataEmployeeExist= new Hashtable();
          // priska 20150610
                        Hashtable outletname = new Hashtable();
                        String InEmpNum = " 1";
                        
                        //str1.contains(cs1)
                        
                        for ( int is = 0 ; is < vListReportDaily.size(); is++ ){
                             AttendanceReportDaily attendanceReportDaily = (AttendanceReportDaily) vListReportDaily.get(is);
                            if (attendanceReportDaily != null && (!InEmpNum.contains(attendanceReportDaily.getPayrollNumb().toString())) ){
                            InEmpNum = InEmpNum + "," +attendanceReportDaily.getPayrollNumb();
                            }
                        }
                        Date newDateTo = (Date) selectedDateTo.clone();
                        newDateTo.setDate(newDateTo.getDate() + 1);
                        String wherestation = " he." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " IN ( "+ InEmpNum +") AND ( hmt." + PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS] +" >= \""+ Formater.formatDate(selectedDateFrom, "yyyy-MM-dd") + " 00:00:00\"" + " AND hmt." + PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS] +" <= \""+ Formater.formatDate(newDateTo, "yyyy-MM-dd") + " 23:59:59\""  +" )" ;
                        outletname = PstMachineTransaction.GetStationByDate(0, 0, wherestation, PstMachineTransaction.fieldNames[PstMachineTransaction.FLD_DATE_TRANS]);
                           
        for (int i = 0; i < vListReportDaily.size(); i++) {
         AttendanceReportDaily attendanceReportDaily = (AttendanceReportDaily) vListReportDaily.get(i);
          try{
            countRow++;
            //countRow = countRow + addRow;
            row = sheet.createRow((short) (countRow));
            sheet.createFreezePane(7, 7);
            //cell = row.createCell((short) 0);cell.setCellValue("");cell.setCellStyle(style2);    
            //if(attendanceReportDaily!=null && attendanceReportDaily.getEmployeeId()!=tmpEmpId && tmpDataEmployeeExist.get(attendanceReportDaily.getEmployeeId())==null){ 
            //    tmpEmpId = attendanceReportDaily.getEmployeeId();
                //tmpDataEmployeeExist.put(attendanceReportDaily.getEmployeeId(), true);
                cell = row.createCell((short) 0);cell.setCellValue(no);cell.setCellStyle(style2);    
                no++;
                cell = row.createCell((short) 1);cell.setCellValue(attendanceReportDaily.getPayrollNumb()!=null && attendanceReportDaily.getPayrollNumb().length()>0?attendanceReportDaily.getPayrollNumb():"-");cell.setCellStyle(style2);            
                cell = row.createCell((short) 2);cell.setCellValue(attendanceReportDaily.getFullName()!=null &&attendanceReportDaily.getFullName().length()>0?attendanceReportDaily.getFullName():"-");cell.setCellStyle(style2);            
                cell = row.createCell((short) 3);cell.setCellValue(attendanceReportDaily.getCompanyName()!=null && attendanceReportDaily.getCompanyName().length()>0?attendanceReportDaily.getCompanyName():"-");cell.setCellStyle(style2);            
                cell = row.createCell((short) 4);cell.setCellValue(attendanceReportDaily.getDivisionName()!=null&& attendanceReportDaily.getDivisionName().length()>0?attendanceReportDaily.getDivisionName():"-");cell.setCellStyle(style2);   
                cell = row.createCell((short) 5);cell.setCellValue(attendanceReportDaily.getDepartementName()!=null&& attendanceReportDaily.getDepartementName().length()>0?attendanceReportDaily.getDepartementName():"-");cell.setCellStyle(style2);   
                cell = row.createCell((short) 6);cell.setCellValue(attendanceReportDaily.getSectionName()!=null&& attendanceReportDaily.getSectionName().length()>0?attendanceReportDaily.getSectionName():"-");cell.setCellStyle(style2);   
                
//            }else{
//                for(int idx=0;idx<7;idx++){
//                    cell = row.createCell((short) idx);cell.setCellValue("");cell.setCellStyle(style2);   
//                }
//            }
            ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
            if(hashSchSymbol!=null && hashSchSymbol.get(attendanceReportDaily.getSchedule1st())!=null){
                scheduleSymbol = (ScheduleSymbol)hashSchSymbol.get(attendanceReportDaily.getSchedule1st());
            }
            String actualIn="";
            String actualOut="";
            if(attendanceReportDaily.getTimeIn()!=null){
                //String fDtActualIn1st = Formater.formatDate(attendanceReportDaily.getTimeIn(), "yyyy-MM-dd HH:mm:ss");
                String fDtActualIn1st = Formater.formatDate(attendanceReportDaily.getTimeIn(), "HH:mm:ss");
                Object stationIn = outletname.get(attendanceReportDaily.getPayrollNumb()+"_"+fDtActualIn1st);
                actualIn = fDtActualIn1st +"/"+(stationIn != null ? stationIn : "-" );
            }else{
                actualIn = "-";
            }
            if(attendanceReportDaily.getTimeOut()!=null){
               // String fDtActualOut1st = Formater.formatDate(attendanceReportDaily.getTimeOut(), "yyyy-MM-dd HH:mm:ss");
                String fDtActualOut1st = Formater.formatDate(attendanceReportDaily.getTimeOut(), "HH:mm:ss");
              Object stationOut = outletname.get(attendanceReportDaily.getPayrollNumb()+"_"+fDtActualOut1st);
                actualOut = fDtActualOut1st +"/"+(stationOut != null ? stationOut : "-" );
            }else{
                actualOut = "-";
            }
             //update by satrya 2014-02-01
              long oidLeave  = 0;
              String sSymbolLeave = ""; 
              String strSchldSymbol1 = attendanceReportDaily.getScheduleSymbol1st(); 
               Vector listLeaveAplication =  PstLeaveApplication.listOid(attendanceReportDaily.getEmployeeId(), attendanceReportDaily.getSchTimeIn1st(),attendanceReportDaily.getSchTimeOut1st());
               //update by satrya 2013-12-12
               Vector vctSchIDOff = PstScheduleSymbol.getScheduleId(PstScheduleCategory.CATEGORY_OFF);
               if(listLeaveAplication !=null && listLeaveAplication.size()>0){
                   //LeaveOidSym obj = new LeaveOidSym();
                   try{
                   for (int j = 0; j < listLeaveAplication.size(); j++) {
                    LeaveOidSym leaveOidSym = (LeaveOidSym) listLeaveAplication.get(j);
                    oidLeave = leaveOidSym.getLeaveOid();
                    String sSymbolLeaveX= (String.valueOf(leaveOidSym.getLeaveSymbol()));
                    //update by satrya 2013-12-12
                    //mencari jika ada schedule off
                    if(vctSchIDOff!=null && vctSchIDOff.size()>0){
                        for(int xOff=0;xOff<vctSchIDOff.size();xOff++){
                            long oidOff = (Long)vctSchIDOff.get(xOff);
                            //jika schedulenya off maka dihilangkan symbol cutinya
                            if((attendanceReportDaily.getSchedule1st() == oidOff)){
                                sSymbolLeaveX="";
                            }
                        }
                    }
                    sSymbolLeave = sSymbolLeave  + sSymbolLeaveX + ",";
                }
                    if(sSymbolLeave!=null && sSymbolLeave.length()>0){
                         sSymbolLeave= sSymbolLeave.substring(0,sSymbolLeave.length()-1); 
                        }
                 
                   }catch(Exception ex){System.out.println("Exception list Leave Application"+ex);}
                
               }
            String selectedDate = attendanceReportDaily.getSelectedDt()!=null?Formater.formatDate(attendanceReportDaily.getSelectedDt(), "EE, dd/MM/yyyy"):"-";
            cell = row.createCell((short) 7);cell.setCellValue(selectedDate);cell.setCellStyle(style2);            
            cell = row.createCell((short) 8);cell.setCellValue(scheduleSymbol.getSymbol()!=null && scheduleSymbol.getSymbol().length()>0?scheduleSymbol.getSymbol()+(sSymbolLeave!=null && sSymbolLeave.length()>0?"/"+sSymbolLeave:""):"-");cell.setCellStyle(style2);            
            cell = row.createCell((short) 9);cell.setCellValue(actualIn);cell.setCellStyle(style2);            
            cell = row.createCell((short) 10);cell.setCellValue(actualOut);cell.setCellStyle(style2);
           
             String reason1st="";
            if(hashReason!=null && hashReason.get(""+attendanceReportDaily.getReason1st())!=null){
                reason1st = (String)hashReason.get(""+attendanceReportDaily.getReason1st());
            }
            
           
            
            
            //menginisialisasikan variable untuk overtime
                String insentif ="-";
                int oVForm =-1;
                int allwance =-1;
                int paid =-1 ;
                long ovId=0;
                //double   NetOv =0.0;
                String   NetOv ="-";
                //double oVerIdx= 0.0;
                String oVerIdx= "-";
                Presence preBOut = null;
                long preBreakOut = 0;
                long preBreakIn = 0;
                long breakDuration =0L;
                long breakOvertime=0;
                String payCompCode = PayComponent.COMPONENT_INS; 
                String bOut =""; 
                String bIn = "";
                String dBout = "";
                String dBin = ""; 
            /*if(listPresencePersonalInOut!=null && listPresencePersonalInOut.size() > 0 ){
                         Date dtSchDateTime = null; 
                         Date dtpresenceDateTime = null;
                         Date dtSchEmpScheduleBIn = null;
                         Date dtSchEmpScheduleBOut = null;
                         long preBreakOutX=0;
                         long preBreakInX=0;
                         Date dtBreakOut=null; 
                          Date dtBreakIn=null;
                          boolean ispreBreakOutsdhdiambil = false; 
                     for(int bIdx = 0;bIdx < listPresencePersonalInOut.size();bIdx++){
                         Presence presenceBreak = (Presence) listPresencePersonalInOut.get(bIdx);//yang di cari harus ada leavenya 
                         //update by satrya 2012-10-17
                         if(attendanceReportDaily.getSchBreakOut1st()!=null){
                             dtSchEmpScheduleBOut = (Date) attendanceReportDaily.getSchBreakOut1st().clone();
                             dtSchEmpScheduleBOut.setHours(dtSchEmpScheduleBOut.getHours());
                             dtSchEmpScheduleBOut.setMinutes(dtSchEmpScheduleBOut.getMinutes());
                            dtSchEmpScheduleBOut.setSeconds(0);
                         }
                         if(attendanceReportDaily.getSchBreakIn1st()!=null){
                             dtSchEmpScheduleBIn = (Date) attendanceReportDaily.getSchBreakIn1st().clone();
                             dtSchEmpScheduleBIn.setHours(dtSchEmpScheduleBIn.getHours());
                             dtSchEmpScheduleBIn.setMinutes(dtSchEmpScheduleBIn.getMinutes());
                            dtSchEmpScheduleBIn.setSeconds(0);
                         }
                         if(presenceBreak.getScheduleDatetime()!=null){
                            dtSchDateTime = (Date)presenceBreak.getScheduleDatetime().clone();
                            dtSchDateTime.setHours(dtSchDateTime.getHours());
                            dtSchDateTime.setMinutes(dtSchDateTime.getMinutes());
                            dtSchDateTime.setSeconds(0);                            
                         }
                         if(presenceBreak.getPresenceDatetime() !=null){ 
                                //update by satrya 2012-10-17
                            dtpresenceDateTime = (Date)presenceBreak.getPresenceDatetime().clone();
                            dtpresenceDateTime.setHours(dtpresenceDateTime.getHours());
                            dtpresenceDateTime.setMinutes(dtpresenceDateTime.getMinutes());
                            dtpresenceDateTime.setSeconds(0);       
                         }
                         if(presenceBreak.getEmployeeId()==attendanceReportDaily.getEmployeeId()
                                  && (DateCalc.dayDifference(presenceBreak.getPresenceDatetime(),attendanceReportDaily.getSelectedDt())==0 )
                                  && presenceBreak.getScheduleDatetime()== null ){ 
                              if(presenceBreak.getStatus()== Presence.STATUS_OUT_ON_DUTY){
                                  bOut =bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"dd/MM/yyyy")+","+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");                                  
                                  dBout = bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");
                                  listPresencePersonalInOut.remove(bIdx);
                                  bIdx = bIdx -1;
                                  
                              }
                              else if(presenceBreak.getStatus()== Presence.STATUS_CALL_BACK){
                                  bIn =bIn+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"dd/MM/yyyy")+ ","+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");                                  
                                   dBin = dBin+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm"); 
                                  listPresencePersonalInOut.remove(bIdx);
                                  bIdx = bIdx -1;
                                  
                              }
                             
                          }
                         else if(presenceBreak.getScheduleDatetime() !=null
                                 && presenceBreak.getEmployeeId()==attendanceReportDaily.getEmployeeId()
                                 &&(DateCalc.dayDifference(presenceBreak.getScheduleDatetime(),attendanceReportDaily.getSelectedDt())==0 )){
                             //kenapa di buat presenceBreak.getScheduleDatetime()!=null ini berpengaruh pada DateCalc.dayDifference(presenceBreak.getScheduleDatetime() xxxx yg menyebabkan exception
                             if(presenceBreak.getStatus()== Presence.STATUS_OUT_PERSONAL){
                                  //update by satrya 2012-09-27
                                 //if((presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())){
                                 //update by satrya 2013-07-28
                                      
                                  //jika sewaktu presence Out melewati schedule BI maka setlah presencenya
                                  //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                   preBreakOutX  = dtpresenceDateTime==null?0:dtpresenceDateTime.getTime();///yang di pakai mengurangi itu adalah presence PO  
                                  dtBreakOut = dtpresenceDateTime; 
                                  if(dtSchEmpScheduleBIn!=null && presenceBreak.getPresenceDatetime().getTime() > dtSchEmpScheduleBIn.getTime()){
                                      preBreakOut = presenceBreak.getPresenceDatetime().getTime();
                                  }
                                  else if((presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())){ ///jika karyawan mendahului istirahat
                                      preBreakOut = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PO 
                                    
                                  }else if(presenceBreak.getScheduleDatetime().getHours()==0 && presenceBreak.getScheduleDatetime().getMinutes()==0){
                                       preBreakOut = presenceBreak.getPresenceDatetime().getTime();//jika schedulenya 00:00
                                  }
                                  else{
                                       preBreakOut = presenceBreak.getScheduleDatetime().getTime(); //yang di pakai mengurangi adalah schedule PO
                                  }
                                 
                                  ispreBreakOutsdhdiambil = false; 
                              }else if(presenceBreak.getStatus()== Presence.STATUS_IN_PERSONAL){
                                  //istirahat terlamabat 
                                   preBreakInX = presenceBreak.getPresenceDatetime()==null?0:presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                   dtBreakIn = presenceBreak.getPresenceDatetime();
                                 if(preBreakOut !=0L){   
                                  //update by satrya 2012-09-27
                                    //if(presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()){
                                     //update by satrya 2013-07-28\
                                    //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                  if(dtSchEmpScheduleBIn!=null && dtBreakOut!=null && dtBreakIn!=null &&
                                          dtBreakOut.getTime() > dtSchEmpScheduleBIn.getTime() && dtBreakIn.getTime() > dtSchEmpScheduleBIn.getTime()){
                                      //karena sudah pasti melewatijam istirahatnya
                                     long  tmpBreakDuration = ((Long)breakTimeDuration.get(""+attendanceReportDaily.getSchedule1st())).longValue();
                                      preBreakIn = presenceBreak.getPresenceDatetime().getTime() + tmpBreakDuration;
                                  }   
                                  else if(presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()){ ///jika karyawan melewati jam istirahat
                                      preBreakIn = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                  }else if(presenceBreak.getScheduleDatetime().getHours()==0 && presenceBreak.getScheduleDatetime().getMinutes()==0){
                                       preBreakIn = presenceBreak.getPresenceDatetime().getTime(); //jika schedulenya 00:00 
                                  }
                                  else{
                                      preBreakIn = presenceBreak.getScheduleDatetime().getTime(); //yang di pakai mengurangi adalah schedule PI
                                  }
                                  
                                   breakDuration = breakDuration + (preBreakIn -  preBreakOut);
                                
                                 
                                 ispreBreakOutsdhdiambil = true;
                                   preBreakOut =0L;
                                   
                                    //breakDuration = breakDuration + presenceBreak.getPresenceDatetime().getTime()-  preBOut.getPresenceDatetime().getTime(); 
                                   // preBOut=null;
                                  }
                                 // diffBi = diffBi+ (presenceBreak.getScheduleDatetime().getTime() - presenceBreak.getPresenceDatetime().getTime());
                                 
                              }else if(presenceBreak.getStatus()== Presence.STATUS_OUT_ON_DUTY){
                                  bOut =bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"dd/MM/yyyy")+","+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");                                  
                                   dBout = dBout+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm"); 
                              } else if(presenceBreak.getStatus()== Presence.STATUS_CALL_BACK){
                                  bIn =bIn+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"dd/MM/yyyy")+ ","+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");                                  
                                  dBin = dBin+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");  
                                  listPresencePersonalInOut.remove(bIdx);                              
                                   bIdx=bIdx-1;
                                 
                              }
                             
                             if(ispreBreakOutsdhdiambil){
                                   //cek da cuti
                                Vector vLisOverlapCuti  = SessLeaveApp.checkOverLapsLeaveTaken(attendanceReportDaily.getEmployeeId(), dtBreakOut,dtBreakIn);
                                if(vLisOverlapCuti!=null && vLisOverlapCuti.size()>0){
                                      for(int idxcuti=0; idxcuti< vLisOverlapCuti.size();idxcuti++){
                                          LeaveCheckTakenDateFinish leaveCheckTaken = (LeaveCheckTakenDateFinish)vLisOverlapCuti.get(idxcuti);
                                          if(leaveCheckTaken.getTakenDate()!=null && dtBreakOut!=null
                                                  && preBreakOutX < leaveCheckTaken.getTakenDate().getTime() 
                                                  && preBreakOutX < dtSchEmpScheduleBOut.getTime()){
                                                //bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"dd/MM/yyyy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br></font>";      
                                              bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"dd/MM/yyyy")+","+ Formater.formatDate(dtBreakOut,"HH:mm");      
                                          }else{
                                              bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"dd/MM/yyyy")+","+Formater.formatDate(dtBreakOut,"HH:mm");
                                          }

                                          if(dtSchEmpScheduleBIn!=null && dtBreakIn!=null 
                                                  && preBreakInX > leaveCheckTaken.getFinishDate().getTime() 
                                                  && preBreakInX > dtSchEmpScheduleBIn.getTime()){

                                               bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"dd/MM/yyyy")+","+ Formater.formatDate(dtBreakIn,"HH:mm");
                                               // bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"dd/MM/yyyy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br></font>";
                                               
                                          }else{
                                              bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"dd/MM/yyyy")+","+Formater.formatDate(dtBreakIn,"HH:mm");
                                          }
                                        
                                          vLisOverlapCuti.remove(idxcuti);                              
                                            idxcuti=idxcuti-1;
                                              break;
                                      }
                                }//end cek cuti
                                else{
                                    if(preBreakOutX < dtSchEmpScheduleBOut.getTime() || preBreakOutX > dtSchEmpScheduleBIn.getTime()){
                                          bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"dd/MM/yyyy")+","+ Formater.formatDate(dtBreakOut,"HH:mm");      
                                          // bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"dd/MM/yyyy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br></font>";      
                                    }else{
                                        bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"dd/MM/yyyy")+","+Formater.formatDate(dtBreakOut,"HH:mm");
                                    }
                                    if(preBreakInX > dtSchEmpScheduleBIn.getTime()){
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"dd/MM/yyyy")+","+ Formater.formatDate(dtBreakIn,"HH:mm");
                                         // bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"dd/MM/yyyy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br></font>";
                                    }else{
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"dd/MM/yyyy")+","+Formater.formatDate(dtBreakIn,"HH:mm");
                                    }
                                }
                                 //update by satrya 2013-06-17
                                   preBreakOutX=0L;
                                   preBreakInX=0L;
                             }
                          // }
                              
                         }//end else if
                           
                     }//end for break In
                          if(preBreakOutX==0 || preBreakInX==0){ 
                                 //jika hanya satu saja yg muncul atau ada misalnya hanya break IN saja atau break Out saja
                                 //update by satrya 2013-06-17
                               if(preBreakOutX!=0){
                                 if(preBreakOutX < dtSchEmpScheduleBOut.getTime() || preBreakOutX > dtSchEmpScheduleBIn.getTime()){
                                          bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"dd/MM/yyyy")+","+ Formater.formatDate(dtBreakOut,"HH:mm");      
                                          // bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"dd/MM/yyyy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakOut,"HH:mm")+"<br><br></font>";      
                                    }else{
                                        bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"dd/MM/yyyy")+","+Formater.formatDate(dtBreakOut,"HH:mm");
                                    }
                              }
                              if(preBreakInX!=0){
                                    if(preBreakInX > dtSchEmpScheduleBIn.getTime()){
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"dd/MM/yyyy")+","+ Formater.formatDate(dtBreakIn,"HH:mm");
                                         //bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"dd/MM/yyyy")+"<br> <font color=\"#FF0000\">"+ Formater.formatDate(dtBreakIn,"HH:mm")+"<br><br></font>";
                                    }else{
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"dd/MM/yyyy")+","+Formater.formatDate(dtBreakIn,"HH:mm");
                                    }
                             }
                             }
                         //update by satrya 2012-10-18
                             //jika di loop tersebut tidak cocok maka di kurangi schedulenya
                                if(breakDuration ==0 && attendanceReportDaily.getSchedule1st()!=0 && breakTimeDuration.get(""+attendanceReportDaily.getSchedule1st()) !=null){  //&& sPresenceDateTime.equals(pSelectedDate)){
                                        try{                          
                                         breakDuration = ((Long)breakTimeDuration.get(""+attendanceReportDaily.getSchedule1st())).longValue(); //scheduleSymbol.getBreakIn().getTime()  - scheduleSymbol.getBreakOut().getTime(); 
                                        }catch(Exception ex){
                                            System.out.println("Exception scheduleSymbol"+ex.toString());
                                            //System.out.println("date"+presenceReportDaily.getSelectedDate()+ presenceReportDaily.getEmpFullName());
                                        }
                                      }
                    }
                   //jika employee tidak ada yang keluar maka akan di potong jam istirahat default
                  else{
                    if(breakDuration ==0 && attendanceReportDaily.getSchedule1st()!=0 && breakTimeDuration.get(""+attendanceReportDaily.getSchedule1st()) !=null){  //&& sPresenceDateTime.equals(pSelectedDate)){
                        try{                          
                         breakDuration = ((Long)breakTimeDuration.get(""+attendanceReportDaily.getSchedule1st())).longValue(); //scheduleSymbol.getBreakIn().getTime()  - scheduleSymbol.getBreakOut().getTime(); 
                        }catch(Exception ex){
                            System.out.println("Exception scheduleSymbol"+ex.toString());
                            //System.out.println("date"+presenceReportDaily.getSelectedDate()+ presenceReportDaily.getEmpFullName());
                        }
                      } 
                  }update by satrya 2014-02-01*/
                 Hashtable hashCekTanggalSamaBreak = new Hashtable();
                 if(listPresencePersonalInOut!=null && listPresencePersonalInOut.size() > 0 ){
                         Date dtSchDateTime = null; 
                         Date dtpresenceDateTime = null;
                         Date dtSchEmpScheduleBIn = null;
                         Date dtSchEmpScheduleBOut = null;
                         long preBreakOutX=0;
                         long preBreakInX=0;
                         Date dtBreakOut=null; 
                         Date dtBreakIn=null;
                         boolean ispreBreakOutsdhdiambil = false; 
                          
                          //priska menambahkan untuk menampilkan station 20150619
                          
                          
                     for(int bIdx = 0;bIdx < listPresencePersonalInOut.size();bIdx++){
                        
                         Presence presenceBreak = (Presence) listPresencePersonalInOut.get(bIdx);//yang di cari harus ada leavenya 
                          long lEmpId = attendanceReportDaily.getEmployeeId();
                         //update by satrya 2012-10-17
                         if(attendanceReportDaily.getSchBreakOut1st()!=null){
                             dtSchEmpScheduleBOut = (Date) attendanceReportDaily.getSchBreakOut1st().clone();
                             dtSchEmpScheduleBOut.setHours(dtSchEmpScheduleBOut.getHours());
                             dtSchEmpScheduleBOut.setMinutes(dtSchEmpScheduleBOut.getMinutes());
                             dtSchEmpScheduleBOut.setSeconds(0);
                         }
                         if(attendanceReportDaily.getSchBreakIn1st()!=null){
                             dtSchEmpScheduleBIn = (Date) attendanceReportDaily.getSchBreakIn1st().clone();
                             dtSchEmpScheduleBIn.setHours(dtSchEmpScheduleBIn.getHours());
                             dtSchEmpScheduleBIn.setMinutes(dtSchEmpScheduleBIn.getMinutes());
                             dtSchEmpScheduleBIn.setSeconds(0);
                         }
                         if(presenceBreak.getScheduleDatetime()!=null){
                            dtSchDateTime = (Date)presenceBreak.getScheduleDatetime().clone();
                            dtSchDateTime.setHours(dtSchDateTime.getHours());
                            dtSchDateTime.setMinutes(dtSchDateTime.getMinutes());
                            dtSchDateTime.setSeconds(0);                            
                         }
                         if(presenceBreak.getPresenceDatetime() !=null){ 
                                //update by satrya 2012-10-17
                            dtpresenceDateTime = (Date)presenceBreak.getPresenceDatetime().clone();
                            dtpresenceDateTime.setHours(dtpresenceDateTime.getHours());
                            dtpresenceDateTime.setMinutes(dtpresenceDateTime.getMinutes());
                            dtpresenceDateTime.setSeconds(0);       
                         }
                         if(presenceBreak.getEmployeeId()==attendanceReportDaily.getEmployeeId() && presenceBreak.getPresenceDatetime()!=null 
                                  && (DateCalc.dayDifference(presenceBreak.getPresenceDatetime(),attendanceReportDaily.getSelectedDt())==0 )
                                  && presenceBreak.getScheduleDatetime()== null ){ 
                              if(presenceBreak.getStatus()== Presence.STATUS_OUT_ON_DUTY){
                                  hashCekTanggalSamaBreak.put(presenceBreak.getPresenceDatetime(), true);
                                  bOut =bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+","+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+" ";                                  
                                  dBout = bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");
                                  listPresencePersonalInOut.remove(bIdx);
                                  bIdx = bIdx -1;
                                  
                              }
                              else if(presenceBreak.getStatus()== Presence.STATUS_CALL_BACK){
                                   hashCekTanggalSamaBreak.put(presenceBreak.getPresenceDatetime(), true);
                                  bIn =bIn+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+ ","+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+" ";                                  
                                   dBin = dBin+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm"); 
                                  listPresencePersonalInOut.remove(bIdx);
                                  bIdx = bIdx -1;
                                  
                              }
                             
                          }
                         else if( presenceBreak.getPresenceDatetime()!=null /* update by satrya 2014-01-27 presenceBreak.getScheduleDatetime() !=null*/ 
                                 && presenceBreak.getEmployeeId()==attendanceReportDaily.getEmployeeId()
                                 &&(DateCalc.dayDifference(presenceBreak.getPresenceDatetime(),attendanceReportDaily.getSelectedDt())==0 )){
                                 // karena bisa tgl yg laen yg di pakai &&(DateCalc.dayDifference(presenceBreak.getScheduleDatetime(),presenceReportDaily.getSelectedDate())==0 )){
                             //kenapa di buat presenceBreak.getScheduleDatetime()!=null ini berpengaruh pada DateCalc.dayDifference(presenceBreak.getScheduleDatetime() xxxx yg menyebabkan exception
                             if(presenceBreak.getStatus()== Presence.STATUS_OUT_PERSONAL){
                                  //update by satrya 2012-09-27
                                 //if((presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())){
                                 //update by satrya 2013-07-28
                                      
                                  //jika sewaktu presence Out melewati schedule BI maka setlah presencenya
                                  //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                   preBreakOutX  = dtpresenceDateTime==null?0:dtpresenceDateTime.getTime();///yang di pakai mengurangi itu adalah presence PO  
                                  dtBreakOut = dtpresenceDateTime; 
                                  if(dtSchEmpScheduleBIn!=null && presenceBreak.getPresenceDatetime().getTime() > dtSchEmpScheduleBIn.getTime()){
                                      preBreakOut = presenceBreak.getPresenceDatetime().getTime();
                                      listPresencePersonalInOut.remove(bIdx);                              
                                      bIdx=bIdx-1;
                                  }
                                  else if((presenceBreak.getPresenceDatetime().getTime() < presenceBreak.getScheduleDatetime().getTime())){ ///jika karyawan mendahului istirahat
                                      preBreakOut = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PO 
                                      listPresencePersonalInOut.remove(bIdx);                              
                                      bIdx=bIdx-1;
                                    
                                  }else if(presenceBreak.getScheduleDatetime().getHours()==0 && presenceBreak.getScheduleDatetime().getMinutes()==0){
                                       preBreakOut = presenceBreak.getPresenceDatetime().getTime();//jika schedulenya 00:00
                                       listPresencePersonalInOut.remove(bIdx);                              
                                       bIdx=bIdx-1;
                                  }
                                  else{
                                       preBreakOut = presenceBreak.getScheduleDatetime().getTime(); //yang di pakai mengurangi adalah schedule PO
                                       listPresencePersonalInOut.remove(bIdx);                              
                                       bIdx=bIdx-1;
                                  }
                                 
                                  ispreBreakOutsdhdiambil = false; 
                              }else if(presenceBreak.getStatus()== Presence.STATUS_IN_PERSONAL){
                                  //istirahat terlamabat 
                                   preBreakInX = presenceBreak.getPresenceDatetime()==null?0:presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                   dtBreakIn = presenceBreak.getPresenceDatetime();
                                 if(preBreakOut !=0L){   
                                  //update by satrya 2012-09-27
                                    //if(presenceBreak.getScheduleDatetime()==null || presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()){
                                     //update by satrya 2013-07-28\
                                    //misal sch BO & BI = 13 s/d 14 dan ada presence BO 15.00 maka yg di set 15.00 untk penguranganya
                                  if(dtSchEmpScheduleBIn!=null && dtBreakOut!=null && dtBreakIn!=null &&
                                          dtBreakOut.getTime() > dtSchEmpScheduleBIn.getTime() && dtBreakIn.getTime() > dtSchEmpScheduleBIn.getTime()){
                                      //karena sudah pasti melewatijam istirahatnya
                                     long  tmpBreakDuration = ((Long)breakTimeDuration.get(""+attendanceReportDaily.getSchedule1st())).longValue();
                                      preBreakIn = presenceBreak.getPresenceDatetime().getTime() + tmpBreakDuration;
                                      listPresencePersonalInOut.remove(bIdx);                              
                                      bIdx=bIdx-1;
                                  }   
                                  else if(presenceBreak.getPresenceDatetime().getTime() > presenceBreak.getScheduleDatetime().getTime()){ ///jika karyawan melewati jam istirahat
                                      preBreakIn = presenceBreak.getPresenceDatetime().getTime();///yang di pakai mengurangi itu adalah presence PI
                                      listPresencePersonalInOut.remove(bIdx);                              
                                      bIdx=bIdx-1;
                                  }else if(presenceBreak.getScheduleDatetime().getHours()==0 && presenceBreak.getScheduleDatetime().getMinutes()==0){
                                       preBreakIn = presenceBreak.getPresenceDatetime().getTime(); //jika schedulenya 00:00 
                                       listPresencePersonalInOut.remove(bIdx);                              
                                       bIdx=bIdx-1;
                                  }
                                  else{
                                      preBreakIn = presenceBreak.getScheduleDatetime().getTime(); //yang di pakai mengurangi adalah schedule PI
                                      listPresencePersonalInOut.remove(bIdx);                              
                                      bIdx=bIdx-1;
                                  }
                                  
                                   breakDuration = breakDuration + (preBreakIn -  preBreakOut);
                                
                                 
                                 ispreBreakOutsdhdiambil = true;
                                   preBreakOut =0L;
                                   
                                    //breakDuration = breakDuration + presenceBreak.getPresenceDatetime().getTime()-  preBOut.getPresenceDatetime().getTime(); 
                                   // preBOut=null;
                                  }
                                 // diffBi = diffBi+ (presenceBreak.getScheduleDatetime().getTime() - presenceBreak.getPresenceDatetime().getTime());
                                 
                              }else if(presenceBreak.getStatus()== Presence.STATUS_OUT_ON_DUTY){
                                   dtBreakOut = presenceBreak.getPresenceDatetime();
                                    hashCekTanggalSamaBreak.put(presenceBreak.getPresenceDatetime(), true);
                                  bOut =bOut+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+","+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+" ";                                  
                                   dBout = dBout+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm"); 
                                   ispreBreakOutsdhdiambil=false;
                                   listPresencePersonalInOut.remove(bIdx);                              
                                   bIdx=bIdx-1;
                              } else if(presenceBreak.getStatus()== Presence.STATUS_CALL_BACK){
                                  dtBreakIn = presenceBreak.getPresenceDatetime();
                                   hashCekTanggalSamaBreak.put(presenceBreak.getPresenceDatetime(), true);
                                  bIn =bIn+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"d/M/yy")+ " "+Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm")+" ";                                  
                                  dBin = dBin+ "D:" +Formater.formatDate(presenceBreak.getPresenceDatetime(),"HH:mm");  
                                  listPresencePersonalInOut.remove(bIdx);                              
                                   bIdx=bIdx-1;
                                   ispreBreakOutsdhdiambil=true;
                                 
                              }
                             
                             if(ispreBreakOutsdhdiambil){
                                   //cek da cuti
                                  //penambahan lokasi by priska 20150609
                                     String DateBreakOut = Formater.formatDate(dtBreakOut, "yyyy-MM-dd HH:mm:ss");
                                     Object stationOut = outletname.get(attendanceReportDaily.getPayrollNumb()+"_"+DateBreakOut);
                                            if (stationOut == null){ stationOut = "-";  }
                                     String DateBreakIn = Formater.formatDate(dtBreakIn, "yyyy-MM-dd HH:mm:ss");
                                     Object stationIn = outletname.get(attendanceReportDaily.getPayrollNumb()+"_"+DateBreakIn);
                                            if (stationIn == null){ stationIn = "-";  }       
                                            
                                Vector vLisOverlapCuti  = SessLeaveApp.checkOverLapsLeaveTaken(lEmpId, dtBreakOut,dtBreakIn);
                                if(vLisOverlapCuti!=null && vLisOverlapCuti.size()>0){
                                      for(int idxcuti=0; idxcuti< vLisOverlapCuti.size();idxcuti++){
                                          LeaveCheckTakenDateFinish leaveCheckTaken = (LeaveCheckTakenDateFinish)vLisOverlapCuti.get(idxcuti);
                                          if(leaveCheckTaken.getTakenDate()!=null && dtBreakOut!=null
                                                  && preBreakOutX < leaveCheckTaken.getTakenDate().getTime() 
                                                  && preBreakOutX < dtSchEmpScheduleBOut.getTime()){
                                               hashCekTanggalSamaBreak.put(dtBreakOut, true);
                                                bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+",lt"+ Formater.formatDate(dtBreakOut,"HH:mm")+","+stationOut+" ";      
                                               
                                          }else{
                                               hashCekTanggalSamaBreak.put(dtBreakOut, true);
                                              bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+","+Formater.formatDate(dtBreakOut,"HH:mm")+","+stationOut+" ";
                                          }

                                          if(dtSchEmpScheduleBIn!=null && dtBreakIn!=null 
                                                  && preBreakInX > leaveCheckTaken.getFinishDate().getTime() 
                                                  && preBreakInX > dtSchEmpScheduleBIn.getTime()){
                                               hashCekTanggalSamaBreak.put(dtBreakIn, true);
                                               bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+",lt"+ Formater.formatDate(dtBreakIn,"HH:mm")+","+stationIn+" ";
                                               
                                          }else{
                                               hashCekTanggalSamaBreak.put(dtBreakIn, true);
                                              bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+","+Formater.formatDate(dtBreakIn,"HH:mm")+","+stationIn+" ";
                                          }
                                        
                                          vLisOverlapCuti.remove(idxcuti);                              
                                            idxcuti=idxcuti-1;
                                              break;
                                      }
                                }//end cek cuti
                                else{
                                    //update by satrya 2014-01-25
                                  if(preBreakOutX!=0 && (hashCekTanggalSamaBreak.size()==0 || !hashCekTanggalSamaBreak.containsKey(dtBreakOut))){
                                    if(preBreakOutX < dtSchEmpScheduleBOut.getTime() || preBreakOutX > dtSchEmpScheduleBIn.getTime()){
                                         hashCekTanggalSamaBreak.put(dtBreakOut, true);
                                          bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+",lt"+ Formater.formatDate(dtBreakOut,"HH:mm")+","+stationOut+" ";      
                                    }else{
                                        hashCekTanggalSamaBreak.put(dtBreakOut, true);
                                        bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+","+Formater.formatDate(dtBreakOut,"HH:mm")+","+stationOut+" ";
                                    }
                                  }
                                  //update by satrya 2014-01-25
                                   if(preBreakInX!=0 && (hashCekTanggalSamaBreak.size()==0 || !hashCekTanggalSamaBreak.containsKey(dtBreakIn))){
                                    if(preBreakInX > dtSchEmpScheduleBIn.getTime()){
                                          hashCekTanggalSamaBreak.put(dtBreakIn, true);
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+",lt"+Formater.formatDate(dtBreakIn,"HH:mm")+","+stationIn+" ";
                                    }//update by satrya 2014-01-27
                                    else if(dtSchEmpScheduleBOut!=null && preBreakInX<dtSchEmpScheduleBOut.getTime()){
                                        hashCekTanggalSamaBreak.put(dtBreakIn, true);
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+",lt"+Formater.formatDate(dtBreakIn,"HH:mm")+","+stationIn+" ";
                                    }
                                    else{
                                          hashCekTanggalSamaBreak.put(dtBreakIn, true);
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+","+Formater.formatDate(dtBreakIn,"HH:mm")+","+stationIn+" ";
                                    }
                                   }
                                }
                                 //update by satrya 2013-06-17
                                   preBreakOutX=0L;
                                   preBreakInX=0L;
                                   dtBreakOut=null;
                                   dtBreakIn=null; 
                             }
                          // }
                              
                         }//end else if
                           
                     }//end for break In
                          if((preBreakOutX==0 || preBreakInX==0) ){  
                                 //jika hanya satu saja yg muncul atau ada misalnya hanya break IN saja atau break Out saja
                                 //update by satrya 2013-06-17
                               if( dtBreakOut!=null && (hashCekTanggalSamaBreak.size()==0 || !hashCekTanggalSamaBreak.containsKey(dtBreakOut)) && preBreakOutX!=0 && dtSchEmpScheduleBOut!=null && DateCalc.dayDifference(dtSchEmpScheduleBOut,attendanceReportDaily.getSelectedDt())==0 ){
                                 if(preBreakOutX < dtSchEmpScheduleBOut.getTime() || preBreakOutX > dtSchEmpScheduleBIn.getTime()){
                                     //penambahan lokasi by priska 20150609
                                     String DateBreak = Formater.formatDate(dtBreakOut, "yyyy-MM-dd HH:mm:ss");
                                     Object station = outletname.get(attendanceReportDaily.getPayrollNumb()+"_"+DateBreak);
                                            if (station == null){ station = "-";  } 
                                     bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+",lt"+Formater.formatDate(dtBreakOut,"HH:mm")+", "+station+" ";      
                                            
                                    }else{
                                      //penambahan lokasi by priska 20150609
                                    String DateBreak = Formater.formatDate(dtBreakOut, "yyyy-MM-dd HH:mm:ss");
                                    Object station = outletname.get(attendanceReportDaily.getPayrollNumb()+"_"+DateBreak);
                                            if (station == null){ station = "-";  } 
                                    bOut =bOut+ "P:" +Formater.formatDate(dtBreakOut,"d/M/yy")+","+Formater.formatDate(dtBreakOut,"HH:mm")+", "+station+" ";
                                    }
                              }
                              if( dtBreakIn!=null && (hashCekTanggalSamaBreak.size()==0 || !hashCekTanggalSamaBreak.containsKey(dtBreakIn)) && preBreakInX!=0 && dtSchEmpScheduleBIn!=null && DateCalc.dayDifference(dtSchEmpScheduleBIn,attendanceReportDaily.getSelectedDt())==0){
                                    if(preBreakInX > dtSchEmpScheduleBIn.getTime()){
                                        //penambahan lokasi by priska 20150609
                                    String DateBreak = Formater.formatDate(dtBreakIn, "yyyy-MM-dd HH:mm:ss");
                                    Object station = outletname.get(attendanceReportDaily.getPayrollNumb()+"_"+DateBreak);
                                            if (station == null){ station = "-";  } 
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+",lt"+Formater.formatDate(dtBreakIn,"HH:mm")+", "+station+" ";
                                    }else{
                                         //penambahan lokasi by priska 20150609
                                    String DateBreak = Formater.formatDate(dtBreakIn, "yyyy-MM-dd HH:mm:ss");
                                    Object station = outletname.get(attendanceReportDaily.getPayrollNumb()+"_"+DateBreak);
                                            if (station == null){ station = "-";  } 
                                         bIn =bIn+ "P:" +Formater.formatDate(dtBreakIn,"d/M/yy")+",lt"+Formater.formatDate(dtBreakIn,"HH:mm")+", "+station+" ";
                                    }
                             }
                             }
                         //update by satrya 2012-10-18
                             //jika di loop tersebut tidak cocok maka di kurangi schedulenya
                                if(breakDuration ==0 && attendanceReportDaily.getSchedule1st()!=0 && breakTimeDuration.get(""+attendanceReportDaily.getSchedule1st()) !=null){  //&& sPresenceDateTime.equals(pSelectedDate)){
                                        try{                          
                                         breakDuration = ((Long)breakTimeDuration.get(""+attendanceReportDaily.getSchedule1st())).longValue(); //scheduleSymbol.getBreakIn().getTime()  - scheduleSymbol.getBreakOut().getTime(); 
                                        }catch(Exception ex){
                                            System.out.println("Exception scheduleSymbol"+ex.toString());
                                            //System.out.println("date"+presenceReportDaily.getSelectedDate()+ presenceReportDaily.getEmpFullName());
                                        }
                                      }
                    }
                   //jika employee tidak ada yang keluar maka akan di potong jam istirahat default
                  else{
                    if(breakDuration ==0 && attendanceReportDaily.getSchedule1st()!=0 && breakTimeDuration.get(""+attendanceReportDaily.getSchedule1st()) !=null){  //&& sPresenceDateTime.equals(pSelectedDate)){
                        try{                          
                         breakDuration = ((Long)breakTimeDuration.get(""+attendanceReportDaily.getSchedule1st())).longValue(); //scheduleSymbol.getBreakIn().getTime()  - scheduleSymbol.getBreakOut().getTime(); 
                        }catch(Exception ex){
                            System.out.println("Exception scheduleSymbol"+ex.toString());
                            //System.out.println("date"+presenceReportDaily.getSelectedDate()+ presenceReportDaily.getEmpFullName());
                        }
                      } 
                  }
                                 //list Overtime
                 // sementara belum di pakai
                 //TmpOvertimeReportDaily  tmtmpOvertimeReportDaily = new TmpOvertimeReportDaily(); 
               /* update by satrya 2014-02-01 if(showOvertime==0){
                      String pSelectedDate = Formater.formatDate( attendanceReportDaily.getSelectedDt(), "yyyy-MM-dd");
                     if(listOvertimeDaily!=null && listOvertimeDaily.size()> 0){  
                     for(int oVx = 0;oVx < listOvertimeDaily.size();oVx++){
                         OvertimeDetail overtimeDetail = (OvertimeDetail) listOvertimeDaily.get(oVx);
                         
                         String pdateOv = Formater.formatDate(overtimeDetail.getDateFrom(), "yyyy-MM-dd");
                         if(overtimeDetail.getOID() !=0 && overtimeDetail.getEmployeeId()==attendanceReportDaily.getEmployeeId() 
                               && pdateOv.equals(pSelectedDate)){
                             ovId= overtimeDetail.getOID();
                            oVForm = overtimeDetail.getStatus();
                            //I_DocStatus.fieldDocumentStatusShort[overtimeDetail.getStatus()];
                            //update by satrya 2012-10-31
                            if(overtimeDetail.getStatus()==I_DocStatus.DOCUMENT_STATUS_PROCEED){
                                allwance = overtimeDetail.getAllowance(); 
                            //Overtime.allowanceType[overtimeDetail.getAllowance()]; 
                            }
                            paid = overtimeDetail.getPaidBy();
                            //OvertimeDetail.paidByKey[overtimeDetail.getPaidBy()];
                            if(overtimeDetail.getNetDuration() !=0.0){
                                NetOv = Formater.formatNumber(overtimeDetail.getRoundDuration(), "###.##");  
                                //NetOv = Formater.formatNumber(overtimeDetail.getDuration(), "###.##");  
                            }
                            if(overtimeDetail.getTot_Idx() != 0.0){
                                  oVerIdx = Formater.formatNumber(overtimeDetail.getTot_Idx(), "###.##");  ;
                            }
                              listOvertimeDaily.remove(oVx);                              
                              oVx=oVx-1;
                              break;
                              x
                         }
                     }
                    }
               }*///end cek show overtime
                 
               if(showOvertime==0){
                     String pSelectedDate = Formater.formatDate( attendanceReportDaily.getSelectedDt(), "yyyy-MM-dd");
                     if(listOvertimeDaily!=null && listOvertimeDaily.size()> 0){  
                     for(int oVx = 0;oVx < listOvertimeDaily.size();oVx++){
                         OvertimeDetail overtimeDetail = (OvertimeDetail) listOvertimeDaily.get(oVx);
                         
                         String pdateOv = Formater.formatDate(overtimeDetail.getDateFrom(), "yyyy-MM-dd");
                         if(overtimeDetail.getOID() !=0 && overtimeDetail.getEmployeeId()==attendanceReportDaily.getEmployeeId() 
                               && pdateOv.equals(pSelectedDate)){
                             ovId= overtimeDetail.getOID();
                            oVForm = overtimeDetail.getStatus();
                            //I_DocStatus.fieldDocumentStatusShort[overtimeDetail.getStatus()];
                            //update by satrya 2012-10-31
                            if(overtimeDetail.getStatus()==I_DocStatus.DOCUMENT_STATUS_PROCEED){
                                allwance = overtimeDetail.getAllowance(); 
                            //Overtime.allowanceType[overtimeDetail.getAllowance()]; 
                            }
                            paid = overtimeDetail.getPaidBy();
                            //OvertimeDetail.paidByKey[overtimeDetail.getPaidBy()];
                            if(overtimeDetail.getNetDuration() !=0.0){
                                NetOv = Formater.formatNumber(overtimeDetail.getRoundDuration(), "###.##");  
                                //NetOv = Formater.formatNumber(overtimeDetail.getDuration(), "###.##");  
                            }
                            if(overtimeDetail.getTot_Idx() != 0.0){
                                  oVerIdx = Formater.formatNumber(overtimeDetail.getTot_Idx(), "###.##");  ;
                            }
                              listOvertimeDaily.remove(oVx);                              
                              oVx=oVx-1;
                              break;
                         }
                     }
                    }
                      
                     //update by satrya 2014-01-27
                   
                      if(vOvertimeDetail!=null && vOvertimeDetail.size()>0){
                          for(int idxOt=0; idxOt<vOvertimeDetail.size();idxOt++){
                              OvertimeDetail  ovdDetail = (OvertimeDetail)vOvertimeDetail.get(idxOt);
                              if( ovdDetail.getEmployeeId()==attendanceReportDaily.getEmployeeId() && ovdDetail.getRestTimeinHr()!=0 
                                  && DateCalc.dayDifference(ovdDetail.getDateFrom(),attendanceReportDaily.getSelectedDt())==0){
                                  breakOvertime = breakOvertime + (long) (ovdDetail.getRestTimeinHr()*60*60*1000); 
                              }

                          }
                      }
               }
            
                //break out dan in
            cell = row.createCell((short) 11);cell.setCellValue(bOut);cell.setCellStyle(style2);//breakOut            
            cell = row.createCell((short) 12);cell.setCellValue(bIn);cell.setCellStyle(style2); //breakIn           
            
            
             String strDiffIn1st = "-";
             String strDiffOut1st = "-";
                try { 
                    strDiffIn1st = SessEmpSchedule.getDiffIn(attendanceReportDaily.getSchTimeIn1st(), attendanceReportDaily.getTimeIn());
                    strDiffOut1st = SessEmpSchedule.getDiffOut(attendanceReportDaily.getSchTimeOut1st(), attendanceReportDaily.getTimeOut());
                }catch(Exception ex){
                    System.out.println("exCeption Interval "+ex);
                }
            String strDurationFirst = SessEmpSchedule.getWorkingDuration(attendanceReportDaily.getTimeIn(), attendanceReportDaily.getTimeOut(), (breakDuration + breakOvertime));
            
            cell = row.createCell((short) 13);cell.setCellValue(strDiffIn1st.length()>0?strDiffIn1st:"-");cell.setCellStyle(style2);//diffIn            
            cell = row.createCell((short) 14);cell.setCellValue(strDiffOut1st.length()>0?strDiffOut1st:"-");cell.setCellStyle(style2);//diffOut            
            cell = row.createCell((short) 15);cell.setCellValue(strDurationFirst);cell.setCellStyle(style2);//duration
            
            if(showOvertime==0){
                
                boolean isInsentif=false;
                  if(attdConfig!=null && attdConfig.getConfigurasiInsentif()==I_Atendance.CONFIGURASI_I_TAKEN_INSENTIF){
                      int positionLevel=0;
                       if(hashPositionLevel!=null && hashPositionLevel.get(attendanceReportDaily.getEmployeeId())!=null){
                            positionLevel = (Integer)hashPositionLevel.get(attendanceReportDaily.getEmployeeId());
                       }
                      isInsentif = payrollCalculatorConfig.checkPayrollComponent(payCompCode, attendanceReportDaily.getEmployeeId(), attendanceReportDaily.getDepartmentId(), attendanceReportDaily.getSelectedDt(),holidaysTable.isHoliday(attendanceReportDaily.getReligionId() !=0 ? attendanceReportDaily.getReligionId() : 0, attendanceReportDaily.getSelectedDt()), oidLeave, ovId, attendanceReportDaily.getiStatus1st(), positionLevel, iPropInsentifLevel, strSchldSymbol1,attendanceReportDaily.getEmpCategoriId());
                  }
                if(isInsentif){
                    insentif = "✔";
                }else{
                    insentif = "";
                }
                cell = row.createCell((short) 16);cell.setCellValue(""+insentif);cell.setCellStyle(style2);//insentif            
                cell = row.createCell((short) 17);cell.setCellValue(oVForm==-1?"-":I_DocStatus.fieldDocumentStatus[oVForm]);cell.setCellStyle(style2);//ot form            
                cell = row.createCell((short) 18);cell.setCellValue(allwance==-1?"-":Overtime.allowanceType[allwance]);cell.setCellStyle(style2);//allowance            
                cell = row.createCell((short) 19);cell.setCellValue(paid==-1?"-":OvertimeDetail.paidByKey[paid]);cell.setCellStyle(style2);//paid/DP            
                cell = row.createCell((short) 20);cell.setCellValue(""+NetOv);cell.setCellStyle(style2);//netOt            
                cell = row.createCell((short) 21);cell.setCellValue(""+reason1st);cell.setCellStyle(style2);//reason            
                cell = row.createCell((short) 22);cell.setCellValue(""+attendanceReportDaily.getNote1st());cell.setCellStyle(style2);//note            
                cell = row.createCell((short) 23);cell.setCellValue(""+attendanceReportDaily.getStatus1st());cell.setCellStyle(style2);//staus            
            }else{
                cell = row.createCell((short) 16);cell.setCellValue(""+reason1st);cell.setCellStyle(style2);//,Reason
                cell = row.createCell((short) 17);cell.setCellValue(""+attendanceReportDaily.getNote1st());cell.setCellStyle(style2);//note            
                cell = row.createCell((short) 18);cell.setCellValue(""+attendanceReportDaily.getStatus1st());cell.setCellStyle(style2);    //status        
                
            }
             //System.out.println(" Employee "+sessTmpSpecialEmployee.getFullName()+" Employee Num: "+ sessTmpSpecialEmployee.getEmployeeNum()+" DEPARTEMENT "+sessTmpSpecialEmployee.getDepartement());
            }catch(Exception exc){
                System.out.println("Exception export excel data bank employee "+ exc +" Employee Num "+ attendanceReportDaily.getPayrollNumb());
            }
        }
      }
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    } 

    /** Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processRequest(request, response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /** Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }

}

