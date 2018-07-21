<%-- 
    Document   : export_excel_rekapitulasi_absensi.jsp
    Created on : Dec 11, 2012, 10:42:46 AM
    Author     : Satrya Ramayu 
--%>


<%@page import="com.dimata.harisma.entity.masterdata.payday.PstPayDay"%>
<%@page import="com.dimata.harisma.entity.masterdata.payday.HashTblPayDay"%>
<%@page import="com.dimata.harisma.session.attendance.rekapitulasiabsensi.RekapitulasiAbsensi"%>
<%@page import="com.dimata.harisma.entity.masterdata.sesssection.SessSection"%>
<%@page import="com.dimata.harisma.entity.masterdata.sessemployee.EmployeeMinimalis"%>
<%@page import="com.dimata.harisma.entity.masterdata.sessemployee.SessEmployee"%>
<%@page import="com.dimata.harisma.entity.masterdata.sessdepartment.SessDepartment"%>
<%@page import="com.dimata.harisma.entity.masterdata.sessdivision.SessDivision"%>
<%@page import="com.dimata.harisma.report.attendance.TmpListParamAttdSummary"%>
<%@page import="com.dimata.harisma.report.attendance.AttendanceSummaryXls"%>
<%@page import="com.dimata.harisma.form.payroll.FrmPayInput"%>
<%@page import="com.dimata.harisma.entity.overtime.TableHitungOvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPaySlip"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayInput"%>
<%@page import="com.dimata.harisma.session.leave.SessLeaveApp"%>
<%@page import="com.dimata.harisma.entity.overtime.HashTblOvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.payroll.PayComponent"%>
<%@page import="com.dimata.harisma.session.payroll.I_PayrollCalculator"%>
<%@page import="com.lowagie.text.Document"%>
<%@page import="com.dimata.qdep.db.DBHandler"%>
<%@page import="org.apache.poi.hssf.record.ContinueRecord"%>
<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="com.dimata.harisma.entity.overtime.Overtime"%>
<%@page import="com.dimata.harisma.entity.overtime.OvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.overtime.PstOvertimeDetail"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%>
<%@page import="com.dimata.harisma.entity.attendance.PstEmpSchedule"%>
<%@ page language="java" %>

<%@ page import ="java.util.*"%>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.text.*" %>

<%@ page import ="com.dimata.gui.jsp.*"%>
<%@ page import ="com.dimata.util.*"%>
<%@ page import ="com.dimata.qdep.form.*"%>

<%@ page import ="com.dimata.harisma.entity.masterdata.*"%>
<%@ page import ="com.dimata.harisma.entity.employee.*"%>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import ="com.dimata.harisma.session.attendance.*"%>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ include file = "../../../main/javainit.jsp" %>

<%!    private static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for (int i = 0; i < vector.size(); i++) {
            String code = (String) vector.get(i);
            if (((vector.get(vector.size() - 1)).equals(LogicParser.SIGN))
                    && ((vector.get(vector.size() - 1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size() - 1);
            }
        }
        return vector;
    }
%>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_PRESENCE_REPORT, AppObjInfo.OBJ_PRESENCE_REPORT);
    int appObjCodePresenceEdit = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_PRESENCE);
    boolean privUpdatePresence = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePresenceEdit, AppObjInfo.COMMAND_UPDATE));
%>
<%@ include file = "../../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
    //boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));

    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;

//cek tipe browser, browser detection
    //String userAgent = request.getHeader("User-Agent");
    //boolean isMSIE = (userAgent!=null && userAgent.indexOf("MSIE") !=-1); 

%>
<%!    public String drawListRincianSo4(JspWriter outJsp,RekapitulasiAbsensi rekapitulasiAbsensi,Vector listCompany,Hashtable hashDivision,Hashtable hashDepartment,Hashtable hashSection,Hashtable hashEmployee,Hashtable hashEmployeeSection,Hashtable listAttdAbsensi,HashTblPayDay hashTblPayDay,long oidEmployeeDw) {
        String result = "";
        if(rekapitulasiAbsensi==null){
            return result; 
        }
        
        double Stock_Opname_4Jam_Value =0;
        try{
          Stock_Opname_4Jam_Value = Double.parseDouble(PstSystemProperty.getValueByName("STOCK_OPNAME_4JAM_VALUE"));
         }catch(Exception ex){
             System.out.println("Stock_Opname_4Jam_Value NOT Be SET"+ex);
         }
        
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        //ctrlist.setCellStyles("listgensellstyles");
        ctrlist.setRowSelectedStyles("rowselectedstyles");
        ctrlist.setHeaderStyle("listheaderJs");
        //ctrlist.setStyleSelectableTableValue("id=\"selectable\""); 
        ctrlist.setMaxFreezingTable(2);
        //mengambil nama dari kode komponent
        ctrlist.addHeader("Head Count", "5%", "2", "0");
        ctrlist.addHeader("No", "5%", "2", "0");
        ctrlist.addHeader("Nama", "5%", "2", "0");
        String dtStart="-";
        String dtEnd="-";
        if(rekapitulasiAbsensi!=null && rekapitulasiAbsensi.getDtFrom()!=null && rekapitulasiAbsensi.getDtTo()!=null){
            dtStart = Formater.formatDate(rekapitulasiAbsensi.getDtFrom(), "dd MMM yyyy");
            dtEnd = Formater.formatDate(rekapitulasiAbsensi.getDtTo(), "dd MMM yyyy");
        }
        ctrlist.addHeader(""+dtStart+" s/d "+dtEnd, "20%", "0", "1"); 
        ctrlist.addHeader("SO", "5%", "0", "0");
        
         ctrlist.addHeader("PER DAY", "5%", "2", "0");
         ctrlist.addHeader("JUMLAH TOTAL", "5%", "2", "0");

      
        long diffStartToFinish = 0;
        int itDate = 0;
        if (rekapitulasiAbsensi.getDtFrom() != null && rekapitulasiAbsensi.getDtTo() != null) { 
            diffStartToFinish = rekapitulasiAbsensi.getDtTo().getTime() - rekapitulasiAbsensi.getDtFrom().getTime();
            if (diffStartToFinish >= 0) {
                itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)) + 1;
                ctrlist.addHeader("Working Schedule <br>" + Formater.formatDate(rekapitulasiAbsensi.getDtFrom(), "dd MMM yyyy") + " s/d " + Formater.formatDate(rekapitulasiAbsensi.getDtTo(), "dd MMM yyyy"), "20%", "0", "" + itDate);
                for (int idx = 0; idx < itDate; idx++) {
                    Date selectedDate = new Date(rekapitulasiAbsensi.getDtFrom().getYear(), rekapitulasiAbsensi.getDtFrom().getMonth(), (rekapitulasiAbsensi.getDtFrom().getDate() + idx));
                    Date selectedDatePrev = new Date(rekapitulasiAbsensi.getDtFrom().getYear(), rekapitulasiAbsensi.getDtFrom().getMonth(), (rekapitulasiAbsensi.getDtFrom().getDate() + (idx >= 0 ? idx - 1 : idx)));
                    SimpleDateFormat formatterDay = new SimpleDateFormat("EE");
                    String dayString = formatterDay.format(selectedDate);
                    // hanya untuk cobactrlist.addHeader(Formater.formatDate(selectedDate, "dd"), "5%", "0", "0");
                    if (idx != 0 && selectedDate.getYear() == selectedDatePrev.getYear()) {

                        if (dayString.equalsIgnoreCase("Sat")) {
                            ctrlist.addHeader("<font color=\"darkblue\">" + Formater.formatDate(selectedDate, "dd") + "</font>", "5%", "0", "0");
                        } else if (dayString.equalsIgnoreCase("Sun")) {
                            //ctrlist.addHeader("<font color=\"red\">" + Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd"), "5%" + "</font>");
                            ctrlist.addHeader("<font color=\"red\">" + Formater.formatDate(selectedDate, "dd") + "</font>", "5%", "0", "0");
                        } else {
                            //ctrlist.addHeader(Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd"), "5%");
                            ctrlist.addHeader(Formater.formatDate(selectedDate, "dd"), "5%", "0", "0");
                        }

                    } else {
                        if (dayString.equalsIgnoreCase("Sat")) {
                            //ctrlist.addHeader("<font color=\"darkblue\">" + Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd,yy"), "5%", "5%" + "</font>");
                            ctrlist.addHeader("<font color=\"darkblue\">" + Formater.formatDate(selectedDate, "dd") + "</font>", "5%", "0", "0");
                        } else if (dayString.equalsIgnoreCase("Sun")) {
                            //ctrlist.addHeader("<font color=\"red\">" + Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd,yy"), "5%", "5%" + "</font>");
                            ctrlist.addHeader("<font color=\"red\">" + Formater.formatDate(selectedDate, "dd") + "</font>", "5%", "0", "0");
                        } else {
                            //ctrlist.addHeader(Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd,yy"), "5%");
                            ctrlist.addHeader(Formater.formatDate(selectedDate, "dd"), "5%", "0", "0");
                        }
                    }

                }
            }
        }



        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;
        String empNumTest = "";
        int no = 0;
        try {
            if (rekapitulasiAbsensi.getDtFrom() != null && rekapitulasiAbsensi.getDtTo() != null) {
                ctrlist.drawListHeaderExcel(outJsp);//header
                // membuat variable
                RekapitulasiPresenceDanSchedule rekapitulasiPresenceDanSchedule = new RekapitulasiPresenceDanSchedule();
                //listAttdAbsensi = PstEmpSchedule.getListAttendaceRekap(attdConfig,leaveConfig,selectedDateFrom, selectedDateTo, getListEmployeeId, vctSchIDOff, hashSchOff, iPropInsentifLevel, holidaysTable, hashPositionLevel, payrollCalculatorConfig,hashPeriod,hashTblOvertimeDetail);
                 
                if (listCompany != null && listCompany.size() > 0) { 
                    for (int idxcom = 0; idxcom < listCompany.size(); idxcom++) {
                        Company company = (Company) listCompany.get(idxcom);
                        Vector rowx = new Vector();
                        rowx.add("");
                        rowx.add("<font color=\"red\"> <B># " + company.getCompany() + "</B></font>");
                        
                        rowx.add("");
                        rowx.add("");
                        rowx.add("");
                        rowx.add("");
                        
                        for (int idx = 0; idx < itDate; idx++) {
                            rowx.add("");
                        }
                     
                        ctrlist.drawListRowExcel(outJsp, 0, rowx, idxcom);
                        int TotalEmployeePerCompany=0;
                        RekapitulasiAbsensiGrandTotal rekapitulasiAbsensiGrandTotalPerCompany = new RekapitulasiAbsensiGrandTotal();
                        if (hashDivision != null && hashDivision.size() > 0) {
                            SessDivision sessDivision = (SessDivision) hashDivision.get(company.getOID());
                            if (sessDivision != null && sessDivision.getListDivision() != null) {
                                for (int idxDiv = 0; idxDiv < sessDivision.getListDivision().size(); idxDiv++) {
                                    Division division = (Division) sessDivision.getListDivision().get(idxDiv);
                                    rowx = new Vector();
                                    rowx.add("");
                                    rowx.add(" &nbsp;> " + division.getDivision());
                                   
                                    rowx.add("");
                                    rowx.add("");
                                    rowx.add("");
                                    rowx.add("");
                                   
                                    for (int idx = 0; idx < itDate; idx++) {
                                        rowx.add("");
                                    }
                                    ctrlist.drawListRowExcel(outJsp, 0, rowx, idxDiv);

                                    if (hashDepartment != null && hashDepartment.size() > 0) {
                                        SessDepartment sessDepartment = (SessDepartment) hashDepartment.get(division.getOID());
                                        if (sessDepartment != null && sessDepartment.getListDepartment() != null && sessDepartment.getListDepartment().size() > 0) {
                                            for (int idxDept = 0; idxDept < sessDepartment.getListDepartment().size(); idxDept++) {
                                                Department department = (Department) sessDepartment.getListDepartment().get(idxDept);
                                                //dept
                                                if(department.getOID()==3006L){
                                                    boolean dx=true;
                                                    if(dx){
                                                        int becek=0;
                                                    } 
                                                }
                                                rowx = new Vector();
                                                rowx.add("");
                                                rowx.add(" &nbsp;&nbsp;<b>>> " + department.getDepartment()+"</b>");
                                               
                                                rowx.add("");
                                                rowx.add("");
                                                rowx.add("");
                                                rowx.add("");
                                                
                                                for (int idx = 0; idx < itDate; idx++) {
                                                    rowx.add("");
                                                }
                                                ctrlist.drawListRowExcel(outJsp, 0, rowx, idxDept);
                                                //end dept

                                                //nanti cek ada kah employee di dept ini sectionnya
                                               int TotalEmployeePerDeptnSection=0;
                                               RekapitulasiAbsensiGrandTotal rekapitulasiAbsensiGrandTotal = new RekapitulasiAbsensiGrandTotal();
                                                if (hashEmployee != null && hashEmployee.size() > 0) { 
                                                    SessEmployee sessEmployee = (SessEmployee) hashEmployee.get(department.getOID());
                                                    if (sessEmployee != null && sessEmployee.getListEmployee() != null && sessEmployee.getListEmployee().size() > 0) {
                                                        int noEMployeeNoSection=1;
                                                        
                                                        for (int idxEmp = 0; idxEmp < sessEmployee.getListEmployee().size(); idxEmp++) {
                                                            EmployeeMinimalis employeeMinimalis = (EmployeeMinimalis) sessEmployee.getListEmployee().get(idxEmp);
                                                            //employee cek ada secktion
                                                           rekapitulasiPresenceDanSchedule = new RekapitulasiPresenceDanSchedule(); 
                                                            
                                                            if(employeeMinimalis.getSectionId()==0){
                                                                
                                                               TotalEmployeePerDeptnSection = TotalEmployeePerDeptnSection+1;
                                                               TotalEmployeePerCompany = TotalEmployeePerCompany + 1; 
                                                               
                                                               //menghitung per department
                                                                if(listAttdAbsensi!=null && listAttdAbsensi.size()>0 && listAttdAbsensi.containsKey(""+employeeMinimalis.getOID()) ){
                                                                        rekapitulasiPresenceDanSchedule = (RekapitulasiPresenceDanSchedule)listAttdAbsensi.get(""+employeeMinimalis.getOID());
                                                                        listAttdAbsensi.remove(""+employeeMinimalis.getOID());
                                                                        //rekapitulasiAbsensiGrandTotal = new RekapitulasiAbsensiGrandTotal();
                                                                        rekapitulasiAbsensiGrandTotal.setSo4j(rekapitulasiAbsensiGrandTotal.getSo4j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                        rekapitulasiAbsensiGrandTotalPerCompany.setSo4j(rekapitulasiAbsensiGrandTotalPerCompany.getSo4j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                        
                                                                        //rekapitulasiAbsensiGrandTotalPerCompany.setH(rekapitulasiAbsensiGrandTotalPerCompany.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                        //rekapitulasiAbsensiGrandTotalPerCompany.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotalPerCompany.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                }
                                                                rowx = new Vector();
                                                                rowx.add("" + (noEMployeeNoSection++));
                                                                rowx.add(""+employeeMinimalis.getEmployeeNum());
                                                                rowx.add(""+employeeMinimalis.getFullName());  
                                                                rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam()); 
                                                                rowx.add(""+Formater.formatNumberMataUang((Double) Stock_Opname_4Jam_Value, "Rp") ); 
                                                                rowx.add(""+Formater.formatNumberMataUang((rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam()* (Double) Stock_Opname_4Jam_Value ), "Rp"));
                                                                for (int idx = 0; idx < itDate; idx++) {
                                                                    //rowx.add("" + idx);
                                                                    Date selectedDate = new Date(rekapitulasiAbsensi.getDtFrom().getYear(), rekapitulasiAbsensi.getDtFrom().getMonth(), (rekapitulasiAbsensi.getDtFrom().getDate() + idx));
                                                                    
                                                                    //rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate));
                                                                    rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate));
                                                                }
                                                                if(hashTblPayDay!=null){
                                                                    rekapitulasiAbsensiGrandTotal.setPayDay(rekapitulasiAbsensiGrandTotal.getPayDay()+ (Double) Stock_Opname_4Jam_Value );
                                                                        rekapitulasiAbsensiGrandTotal.setTotalPayDay(rekapitulasiAbsensiGrandTotal.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam()* (Double) Stock_Opname_4Jam_Value ));
                                                                        rekapitulasiAbsensiGrandTotalPerCompany.setPayDay(rekapitulasiAbsensiGrandTotalPerCompany.getPayDay()+ (Double) Stock_Opname_4Jam_Value );
                                                                        rekapitulasiAbsensiGrandTotalPerCompany.setTotalPayDay(rekapitulasiAbsensiGrandTotalPerCompany.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam()* (Double) Stock_Opname_4Jam_Value ));
                                                                }
                                                                ctrlist.drawListRowExcel(outJsp, 0, rowx, idxEmp);
                                                                 //end employee list but no section
                                                            }
                                                            //untuk section gk di buat d sini

                                                        }//end loop employee
                                                        
                                                        if(hashSection==null ||  (hashSection != null && hashSection.size() > 0 && hashSection.containsKey(""+department.getOID())==false)){
                                                                rowx = new Vector();
                                                                rowx.add("" + (noEMployeeNoSection-1));
                                                                rowx.add("");
                                                                rowx.add("<b>Total Department</b>");  
                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getSo4j()+"</b>"); 
                                                                rowx.add("<b>"+Formater.formatNumberMataUang(rekapitulasiAbsensiGrandTotal.getPayDay(), "Rp")+"</b>");
                                                                rowx.add("<b>"+Formater.formatNumberMataUang((rekapitulasiAbsensiGrandTotal.getTotalPayDay()), "Rp") +"</b>");
                                                                for (int idx = 0; idx < itDate; idx++) {
                                                                    rowx.add("");
                                                                }
                                                                ctrlist.drawListRowExcel(outJsp, 0, rowx, 0); 
                                                        
                                                        }
                                                        
                                                    }
                                                }//end hasEmployee
                                                
                                                //jika ada section
                                               
                                                    if (hashSection != null && hashSection.size() > 0 && hashSection.containsKey(""+department.getOID())) {
                                                            SessSection sessSection = (SessSection) hashSection.get(""+department.getOID());
                                                            Vector cloneSection = sessSection != null && sessSection.getListSection() != null && sessSection.getListSection().size() > 0?new Vector(sessSection.getListSection()):null;
                                                            if (cloneSection!=null) {
                                                                int loopBarisSection=0;
                                                                for (int idxSec = 0; idxSec < cloneSection.size(); idxSec++) {
                                                                    Section dSection = (Section) cloneSection.get(idxDept);
                                                                    //dept
                                                                    loopBarisSection = loopBarisSection + 1;
                                                                    cloneSection.remove(idxSec);
                                                                    idxSec = idxSec -1;
                                                                    rowx = new Vector();
                                                                    rowx.add("");
                                                                    rowx.add(" &nbsp;&nbsp;<b>>>> " + dSection.getSection()+"</b>");
                                                                    rowx.add("");
                                                                    rowx.add("");
                                                                    rowx.add("");
                                                                    rowx.add("");
                                                                    for (int idx = 0; idx < itDate; idx++) {
                                                                        rowx.add("");
                                                                    }
                                                                    ctrlist.drawListRowExcel(outJsp, 0, rowx, loopBarisSection);
                                                                    //end dept

                                                                    //nanti cek ada kah employee di dept ini sectionnya
                                                                    int noEMployeeNoSection=1;
                                                                    if (hashEmployeeSection != null && hashEmployeeSection.size() > 0) {
                                                                        SessEmployee sessEmployee = (SessEmployee) hashEmployeeSection.get(dSection.getOID());
                                                                        Vector cloneListEmp =sessEmployee != null && sessEmployee.getListEmployee() != null && sessEmployee.getListEmployee().size() > 0?new Vector(sessEmployee.getListEmployee()):null;
                                                                        if (cloneListEmp!=null) {
                                                                            RekapitulasiAbsensiGrandTotal rekapitulasiAbsensiGrandTotalSec = new RekapitulasiAbsensiGrandTotal();
                                                                            int loopBarieEMpSec=0;
                                                                            for (int idxEmpSec = 0; idxEmpSec < cloneListEmp.size(); idxEmpSec++) { 
                                                                                EmployeeMinimalis employeeMinimalis = (EmployeeMinimalis) cloneListEmp.get(idxEmpSec);
                                                                                //employee cek ada secktion
                                                                                rekapitulasiPresenceDanSchedule = new RekapitulasiPresenceDanSchedule(); 
                                                                                
                                                                                if(employeeMinimalis.getSectionId()!=0){
                                                                                    if(listAttdAbsensi!=null && listAttdAbsensi.size()>0 && listAttdAbsensi.containsKey(""+employeeMinimalis.getOID()) && hashTblPayDay!=null){
                                                                                            rekapitulasiPresenceDanSchedule = (RekapitulasiPresenceDanSchedule)listAttdAbsensi.get(""+employeeMinimalis.getOID());
                                                                                            listAttdAbsensi.remove(""+employeeMinimalis.getOID());
                                                                                            
                                                                                            
                                                                                            rekapitulasiAbsensiGrandTotal.setSo4j(rekapitulasiAbsensiGrandTotal.getSo4j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                                            
                                                                                    
                                                                                            rekapitulasiAbsensiGrandTotalSec.setSo4j(rekapitulasiAbsensiGrandTotalSec.getSo4j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                                            
                                                                                            
                                                                                            rekapitulasiAbsensiGrandTotalPerCompany.setSo4j(rekapitulasiAbsensiGrandTotalPerCompany.getSo4j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                                            
                                                                                    
                                                                                    }
                                                                                    loopBarieEMpSec = loopBarieEMpSec + 1;
                                                                                    cloneListEmp.remove(idxEmpSec);
                                                                                    TotalEmployeePerDeptnSection = TotalEmployeePerDeptnSection+1;
                                                                                    TotalEmployeePerCompany = TotalEmployeePerCompany + 1;
                                                                                    idxEmpSec = idxEmpSec -1;
                                                                                    rowx = new Vector();
                                                                                    rowx.add("" + (noEMployeeNoSection++));
                                                                                    rowx.add(""+employeeMinimalis.getEmployeeNum());
                                                                                    rowx.add(""+employeeMinimalis.getFullName());  
                                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam()); 
                                                                                    rowx.add(""+Formater.formatNumberMataUang( (Double) Stock_Opname_4Jam_Value, "Rp")); 
                                                                                    rowx.add(""+Formater.formatNumberMataUang((rekapitulasiPresenceDanSchedule.getPresenceOnTime()*  (Double) Stock_Opname_4Jam_Value ), "Rp"));
                                                                                    for (int idx = 0; idx < itDate; idx++) {
                                                                                        //rowx.add("" + idx);
                                                                                        Date selectedDate = new Date(rekapitulasiAbsensi.getDtFrom().getYear(), rekapitulasiAbsensi.getDtFrom().getMonth(), (rekapitulasiAbsensi.getDtFrom().getDate() + idx));
                                                                                        if (rekapitulasiPresenceDanSchedule.getDataReason(selectedDate).equals("/B")){
                                                                                            String dataR = rekapitulasiPresenceDanSchedule.getDataReason(selectedDate);
                                                                                            String sDataR = dataR.substring(1, dataR.length());        
                                                                                            rowx.add("<b style=\"color:red\"><p align=\"center\">"  + sDataR +"</b>");
                                                                                            } else {
                                                                                            rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate));
                                                                                           
                                                                                            }
                                                                                       // rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate));
                                                                                    }
                                                                                    
                                                                                  
                                                                                    if(hashTblPayDay!=null){
                                                                                        rekapitulasiAbsensiGrandTotal.setPayDay(rekapitulasiAbsensiGrandTotal.getPayDay()+  (Double) Stock_Opname_4Jam_Value);
                                                                                            rekapitulasiAbsensiGrandTotal.setTotalPayDay(rekapitulasiAbsensiGrandTotal.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam()* (Double) Stock_Opname_4Jam_Value));
                                                                                            rekapitulasiAbsensiGrandTotalSec.setPayDay(rekapitulasiAbsensiGrandTotalSec.getPayDay()+  (Double) Stock_Opname_4Jam_Value);
                                                                                            rekapitulasiAbsensiGrandTotalSec.setTotalPayDay(rekapitulasiAbsensiGrandTotalSec.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam()* (Double) Stock_Opname_4Jam_Value));
                                                                                            rekapitulasiAbsensiGrandTotalPerCompany.setPayDay(rekapitulasiAbsensiGrandTotalPerCompany.getPayDay()+ (Double) Stock_Opname_4Jam_Value);
                                                                                            rekapitulasiAbsensiGrandTotalPerCompany.setTotalPayDay(rekapitulasiAbsensiGrandTotalPerCompany.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam()* (Double) Stock_Opname_4Jam_Value));
                                                                                    }
                                                                                    
                                                                                    ctrlist.drawListRowExcel(outJsp, 0, rowx, loopBarieEMpSec);
                                                                                     //end employee list but no section
                                                                                }
                                                                                 ///ini berati employee'nya ada section
                                                                                     //hashEmployeeAdaSection.put(employeeMinimalis.getSectionId(), employeeMinimalis);
                                                                                     //nanti buatlah hashtableEmployee tanpa section dan hasth ada section
                                                                                     //untuk pencarian hari kehadiran, absensi dll pakai fungsi summary attdance nnti itu vector,loop verctor tsb, jika employeeId'nya sama maka hapus lah
                                                                            }//end employee
                                                                            
                                                                            rowx = new Vector();
                                                                            rowx.add("" + (noEMployeeNoSection-1));
                                                                            rowx.add("");
                                                                            rowx.add("<b>Total Section</b>");  
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotalSec.getSo4j()+"</b>");
                                                                            rowx.add("<b>"+Formater.formatNumberMataUang(rekapitulasiAbsensiGrandTotalSec.getPayDay(), "Rp")+"</b>");
                                                                            rowx.add("<b>"+Formater.formatNumberMataUang((rekapitulasiAbsensiGrandTotalSec.getTotalPayDay()), "Rp") +"</b>");
                                                                            for (int idx = 0; idx < itDate; idx++) {
                                                                                rowx.add("");
                                                                            }
                                                                            
                                                                            ctrlist.drawListRowExcel(outJsp, 0, rowx, 0); 
                                                                            
                                                                        }//end list emp
                                                                    }//end hasEmployee
                                                                    
                                                                    
                                                                }
                                                            }
                                                            
                                                            //tota dept yg ada sectionnya
                                                                            rowx = new Vector();
                                                                            rowx.add("" + (TotalEmployeePerDeptnSection));
                                                                            rowx.add("");
                                                                            rowx.add("<b>Total Department</b>");  
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getSo4j()+"</b>"); 
                                                                            rowx.add("<b>"+Formater.formatNumberMataUang(rekapitulasiAbsensiGrandTotal.getPayDay(), "Rp") +"</b>");
                                                                            rowx.add("<b>"+Formater.formatNumberMataUang((rekapitulasiAbsensiGrandTotal.getTotalPayDay()), "Rp") +"</b>");
                                                                            for (int idx = 0; idx < itDate; idx++) {
                                                                                rowx.add("");
                                                                            }
                                                                            
                                                                            ctrlist.drawListRowExcel(outJsp, 0, rowx, 0); 
                                                        }
                                            }
                                        }
                                    }

                                }//end loop division
                            }
                        }//end hashDivision
                        ///ini untuk grand total masing" company
                        rowx = new Vector();
                        rowx.add("" + (TotalEmployeePerCompany));
                        rowx.add("");
                        rowx.add("<b>Grand Total ---->>>>></b>");  
                        rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getSo4j()+"</b>");  
                        
                        rowx.add("<b>"+Formater.formatNumberMataUang(rekapitulasiAbsensiGrandTotalPerCompany.getPayDay(), "Rp") +"</b>");
                        rowx.add("<b>"+Formater.formatNumberMataUang((rekapitulasiAbsensiGrandTotalPerCompany.getTotalPayDay()), "Rp") +"</b>"); 
                        for (int idx = 0; idx < itDate; idx++) {
                            rowx.add("");
                        }
                         ctrlist.drawListRowExcel(outJsp, 0, rowx, 0); 
                    }//end company
                }

                result = "";
                ctrlist.drawListEndTable(outJsp);
            } else {
                result = "<i>Belum ada data dalam sistem ...</i>";
            }

        } catch (Exception ex) {
            System.out.println("Exception export summary Attd" + " Emp:" + empNumTest + " " + ex);
        }
        return result;
    }

%>

 
<%!    public String drawListRincianSo8(JspWriter outJsp,RekapitulasiAbsensi rekapitulasiAbsensi,Vector listCompany,Hashtable hashDivision,Hashtable hashDepartment,Hashtable hashSection,Hashtable hashEmployee,Hashtable hashEmployeeSection,Hashtable listAttdAbsensi,HashTblPayDay hashTblPayDay,long oidEmployeeDw) {
        String result = "";
        if(rekapitulasiAbsensi==null){
            return result; 
        }
        
        double Stock_Opname_8Jam_Value =0;
        try{
          Stock_Opname_8Jam_Value = Double.parseDouble(PstSystemProperty.getValueByName("STOCK_OPNAME_8JAM_VALUE"));
         }catch(Exception ex){
             System.out.println("Stock_Opname_8Jam_Value NOT Be SET"+ex);
         }
        
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        //ctrlist.setCellStyles("listgensellstyles");
        ctrlist.setRowSelectedStyles("rowselectedstyles");
        ctrlist.setHeaderStyle("listheaderJs");
        //ctrlist.setStyleSelectableTableValue("id=\"selectable\""); 
        ctrlist.setMaxFreezingTable(2);
        //mengambil nama dari kode komponent
        ctrlist.addHeader("Head Count", "5%", "2", "0");
        ctrlist.addHeader("No", "5%", "2", "0");
        ctrlist.addHeader("Nama", "5%", "2", "0");
        String dtStart="-";
        String dtEnd="-";
        if(rekapitulasiAbsensi!=null && rekapitulasiAbsensi.getDtFrom()!=null && rekapitulasiAbsensi.getDtTo()!=null){
            dtStart = Formater.formatDate(rekapitulasiAbsensi.getDtFrom(), "dd MMM yyyy");
            dtEnd = Formater.formatDate(rekapitulasiAbsensi.getDtTo(), "dd MMM yyyy");
        }
        ctrlist.addHeader(""+dtStart+" s/d "+dtEnd, "20%", "0", "1"); 
        ctrlist.addHeader("SO", "5%", "0", "0");
        
         ctrlist.addHeader("PER DAY", "5%", "2", "0");
         ctrlist.addHeader("JUMLAH TOTAL", "5%", "2", "0");

      
        long diffStartToFinish = 0;
        int itDate = 0;
        if (rekapitulasiAbsensi.getDtFrom() != null && rekapitulasiAbsensi.getDtTo() != null) { 
            diffStartToFinish = rekapitulasiAbsensi.getDtTo().getTime() - rekapitulasiAbsensi.getDtFrom().getTime();
            if (diffStartToFinish >= 0) {
                itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000)) + 1;
                ctrlist.addHeader("Working Schedule <br>" + Formater.formatDate(rekapitulasiAbsensi.getDtFrom(), "dd MMM yyyy") + " s/d " + Formater.formatDate(rekapitulasiAbsensi.getDtTo(), "dd MMM yyyy"), "20%", "0", "" + itDate);
                for (int idx = 0; idx < itDate; idx++) {
                    Date selectedDate = new Date(rekapitulasiAbsensi.getDtFrom().getYear(), rekapitulasiAbsensi.getDtFrom().getMonth(), (rekapitulasiAbsensi.getDtFrom().getDate() + idx));
                    Date selectedDatePrev = new Date(rekapitulasiAbsensi.getDtFrom().getYear(), rekapitulasiAbsensi.getDtFrom().getMonth(), (rekapitulasiAbsensi.getDtFrom().getDate() + (idx >= 0 ? idx - 1 : idx)));
                    SimpleDateFormat formatterDay = new SimpleDateFormat("EE");
                    String dayString = formatterDay.format(selectedDate);
                    // hanya untuk cobactrlist.addHeader(Formater.formatDate(selectedDate, "dd"), "5%", "0", "0");
                    if (idx != 0 && selectedDate.getYear() == selectedDatePrev.getYear()) {

                        if (dayString.equalsIgnoreCase("Sat")) {
                            ctrlist.addHeader("<font color=\"darkblue\">" + Formater.formatDate(selectedDate, "dd") + "</font>", "5%", "0", "0");
                        } else if (dayString.equalsIgnoreCase("Sun")) {
                            //ctrlist.addHeader("<font color=\"red\">" + Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd"), "5%" + "</font>");
                            ctrlist.addHeader("<font color=\"red\">" + Formater.formatDate(selectedDate, "dd") + "</font>", "5%", "0", "0");
                        } else {
                            //ctrlist.addHeader(Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd"), "5%");
                            ctrlist.addHeader(Formater.formatDate(selectedDate, "dd"), "5%", "0", "0");
                        }

                    } else {
                        if (dayString.equalsIgnoreCase("Sat")) {
                            //ctrlist.addHeader("<font color=\"darkblue\">" + Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd,yy"), "5%", "5%" + "</font>");
                            ctrlist.addHeader("<font color=\"darkblue\">" + Formater.formatDate(selectedDate, "dd") + "</font>", "5%", "0", "0");
                        } else if (dayString.equalsIgnoreCase("Sun")) {
                            //ctrlist.addHeader("<font color=\"red\">" + Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd,yy"), "5%", "5%" + "</font>");
                            ctrlist.addHeader("<font color=\"red\">" + Formater.formatDate(selectedDate, "dd") + "</font>", "5%", "0", "0");
                        } else {
                            //ctrlist.addHeader(Formater.formatDate(selectedDate, "EE") + "<br>" + Formater.formatDate(selectedDate, "MMM dd,yy"), "5%");
                            ctrlist.addHeader(Formater.formatDate(selectedDate, "dd"), "5%", "0", "0");
                        }
                    }

                }
            }
        }



        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;
        String empNumTest = "";
        int no = 0;
        try {
            if (rekapitulasiAbsensi.getDtFrom() != null && rekapitulasiAbsensi.getDtTo() != null) {
                ctrlist.drawListHeaderExcel(outJsp);//header
                // membuat variable
                RekapitulasiPresenceDanSchedule rekapitulasiPresenceDanSchedule = new RekapitulasiPresenceDanSchedule();
                //listAttdAbsensi = PstEmpSchedule.getListAttendaceRekap(attdConfig,leaveConfig,selectedDateFrom, selectedDateTo, getListEmployeeId, vctSchIDOff, hashSchOff, iPropInsentifLevel, holidaysTable, hashPositionLevel, payrollCalculatorConfig,hashPeriod,hashTblOvertimeDetail);
                 
                if (listCompany != null && listCompany.size() > 0) { 
                    for (int idxcom = 0; idxcom < listCompany.size(); idxcom++) {
                        Company company = (Company) listCompany.get(idxcom);
                        Vector rowx = new Vector();
                        rowx.add("");
                        rowx.add("<font color=\"red\"> <B># " + company.getCompany() + "</B></font>");
                        
                        rowx.add("");
                        rowx.add("");
                        rowx.add("");
                        rowx.add("");
                        
                        for (int idx = 0; idx < itDate; idx++) {
                            rowx.add("");
                        }
                     
                        ctrlist.drawListRowExcel(outJsp, 0, rowx, idxcom);
                        int TotalEmployeePerCompany=0;
                        RekapitulasiAbsensiGrandTotal rekapitulasiAbsensiGrandTotalPerCompany = new RekapitulasiAbsensiGrandTotal();
                        if (hashDivision != null && hashDivision.size() > 0) {
                            SessDivision sessDivision = (SessDivision) hashDivision.get(company.getOID());
                            if (sessDivision != null && sessDivision.getListDivision() != null) {
                                for (int idxDiv = 0; idxDiv < sessDivision.getListDivision().size(); idxDiv++) {
                                    Division division = (Division) sessDivision.getListDivision().get(idxDiv);
                                    rowx = new Vector();
                                    rowx.add("");
                                    rowx.add(" &nbsp;> " + division.getDivision());
                                   
                                    rowx.add("");
                                    rowx.add("");
                                    rowx.add("");
                                    rowx.add("");
                                   
                                    for (int idx = 0; idx < itDate; idx++) {
                                        rowx.add("");
                                    }
                                    ctrlist.drawListRowExcel(outJsp, 0, rowx, idxDiv);

                                    if (hashDepartment != null && hashDepartment.size() > 0) {
                                        SessDepartment sessDepartment = (SessDepartment) hashDepartment.get(division.getOID());
                                        if (sessDepartment != null && sessDepartment.getListDepartment() != null && sessDepartment.getListDepartment().size() > 0) {
                                            for (int idxDept = 0; idxDept < sessDepartment.getListDepartment().size(); idxDept++) {
                                                Department department = (Department) sessDepartment.getListDepartment().get(idxDept);
                                                //dept
                                                if(department.getOID()==3006L){
                                                    boolean dx=true;
                                                    if(dx){
                                                        int becek=0;
                                                    } 
                                                }
                                                rowx = new Vector();
                                                rowx.add("");
                                                rowx.add(" &nbsp;&nbsp;<b>>> " + department.getDepartment()+"</b>");
                                               
                                                rowx.add("");
                                                rowx.add("");
                                                rowx.add("");
                                                rowx.add("");
                                                
                                                for (int idx = 0; idx < itDate; idx++) {
                                                    rowx.add("");
                                                }
                                                ctrlist.drawListRowExcel(outJsp, 0, rowx, idxDept);
                                                //end dept

                                                //nanti cek ada kah employee di dept ini sectionnya
                                               int TotalEmployeePerDeptnSection=0;
                                               RekapitulasiAbsensiGrandTotal rekapitulasiAbsensiGrandTotal = new RekapitulasiAbsensiGrandTotal();
                                                if (hashEmployee != null && hashEmployee.size() > 0) { 
                                                    SessEmployee sessEmployee = (SessEmployee) hashEmployee.get(department.getOID());
                                                    if (sessEmployee != null && sessEmployee.getListEmployee() != null && sessEmployee.getListEmployee().size() > 0) {
                                                        int noEMployeeNoSection=1;
                                                        
                                                        for (int idxEmp = 0; idxEmp < sessEmployee.getListEmployee().size(); idxEmp++) {
                                                            EmployeeMinimalis employeeMinimalis = (EmployeeMinimalis) sessEmployee.getListEmployee().get(idxEmp);
                                                            //employee cek ada secktion
                                                           rekapitulasiPresenceDanSchedule = new RekapitulasiPresenceDanSchedule(); 
                                                            
                                                            if(employeeMinimalis.getSectionId()==0){
                                                                
                                                               TotalEmployeePerDeptnSection = TotalEmployeePerDeptnSection+1;
                                                               TotalEmployeePerCompany = TotalEmployeePerCompany + 1; 
                                                               
                                                               //menghitung per department
                                                                if(listAttdAbsensi!=null && listAttdAbsensi.size()>0 && listAttdAbsensi.containsKey(""+employeeMinimalis.getOID()) ){
                                                                        rekapitulasiPresenceDanSchedule = (RekapitulasiPresenceDanSchedule)listAttdAbsensi.get(""+employeeMinimalis.getOID());
                                                                        listAttdAbsensi.remove(""+employeeMinimalis.getOID());
                                                                        //rekapitulasiAbsensiGrandTotal = new RekapitulasiAbsensiGrandTotal();
                                                                        rekapitulasiAbsensiGrandTotal.setSo8j(rekapitulasiAbsensiGrandTotal.getSo8j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                        rekapitulasiAbsensiGrandTotalPerCompany.setSo8j(rekapitulasiAbsensiGrandTotalPerCompany.getSo8j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                        
                                                                        //rekapitulasiAbsensiGrandTotalPerCompany.setH(rekapitulasiAbsensiGrandTotalPerCompany.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                        //rekapitulasiAbsensiGrandTotalPerCompany.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotalPerCompany.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                }
                                                                rowx = new Vector();
                                                                rowx.add("" + (noEMployeeNoSection++));
                                                                rowx.add(""+employeeMinimalis.getEmployeeNum());
                                                                rowx.add(""+employeeMinimalis.getFullName());  
                                                                rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam()); 
                                                                rowx.add(""+Formater.formatNumberMataUang((Double) Stock_Opname_8Jam_Value, "Rp") ); 
                                                                rowx.add(""+Formater.formatNumberMataUang((rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam()* (Double) Stock_Opname_8Jam_Value ), "Rp"));
                                                                for (int idx = 0; idx < itDate; idx++) {
                                                                    //rowx.add("" + idx);
                                                                    Date selectedDate = new Date(rekapitulasiAbsensi.getDtFrom().getYear(), rekapitulasiAbsensi.getDtFrom().getMonth(), (rekapitulasiAbsensi.getDtFrom().getDate() + idx));
                                                                    
                                                                    //rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate));
                                                                    rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate));
                                                                }
                                                                if(hashTblPayDay!=null){
                                                                    rekapitulasiAbsensiGrandTotal.setPayDay(rekapitulasiAbsensiGrandTotal.getPayDay()+ (Double) Stock_Opname_8Jam_Value );
                                                                        rekapitulasiAbsensiGrandTotal.setTotalPayDay(rekapitulasiAbsensiGrandTotal.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam()* (Double) Stock_Opname_8Jam_Value ));
                                                                        rekapitulasiAbsensiGrandTotalPerCompany.setPayDay(rekapitulasiAbsensiGrandTotalPerCompany.getPayDay()+ (Double) Stock_Opname_8Jam_Value );
                                                                        rekapitulasiAbsensiGrandTotalPerCompany.setTotalPayDay(rekapitulasiAbsensiGrandTotalPerCompany.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam()* (Double) Stock_Opname_8Jam_Value ));
                                                                }
                                                                ctrlist.drawListRowExcel(outJsp, 0, rowx, idxEmp);
                                                                 //end employee list but no section
                                                            }
                                                            //untuk section gk di buat d sini

                                                        }//end loop employee
                                                        
                                                        if(hashSection==null ||  (hashSection != null && hashSection.size() > 0 && hashSection.containsKey(""+department.getOID())==false)){
                                                                rowx = new Vector();
                                                                rowx.add("" + (noEMployeeNoSection-1));
                                                                rowx.add("");
                                                                rowx.add("<b>Total Department</b>");  
                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getSo8j()+"</b>"); 
                                                                rowx.add("<b>"+Formater.formatNumberMataUang(rekapitulasiAbsensiGrandTotal.getPayDay(), "Rp")+"</b>");
                                                                rowx.add("<b>"+Formater.formatNumberMataUang((rekapitulasiAbsensiGrandTotal.getTotalPayDay()), "Rp") +"</b>");
                                                                for (int idx = 0; idx < itDate; idx++) {
                                                                    rowx.add("");
                                                                }
                                                                ctrlist.drawListRowExcel(outJsp, 0, rowx, 0); 
                                                        
                                                        }
                                                        
                                                    }
                                                }//end hasEmployee
                                                
                                                //jika ada section
                                               
                                                    if (hashSection != null && hashSection.size() > 0 && hashSection.containsKey(""+department.getOID())) {
                                                            SessSection sessSection = (SessSection) hashSection.get(""+department.getOID());
                                                            Vector cloneSection = sessSection != null && sessSection.getListSection() != null && sessSection.getListSection().size() > 0?new Vector(sessSection.getListSection()):null;
                                                            if (cloneSection!=null) {
                                                                int loopBarisSection=0;
                                                                for (int idxSec = 0; idxSec < cloneSection.size(); idxSec++) {
                                                                    Section dSection = (Section) cloneSection.get(idxDept);
                                                                    //dept
                                                                    loopBarisSection = loopBarisSection + 1;
                                                                    cloneSection.remove(idxSec);
                                                                    idxSec = idxSec -1;
                                                                    rowx = new Vector();
                                                                    rowx.add("");
                                                                    rowx.add(" &nbsp;&nbsp;<b>>>> " + dSection.getSection()+"</b>");
                                                                    rowx.add("");
                                                                    rowx.add("");
                                                                    rowx.add("");
                                                                    rowx.add("");
                                                                    for (int idx = 0; idx < itDate; idx++) {
                                                                        rowx.add("");
                                                                    }
                                                                    ctrlist.drawListRowExcel(outJsp, 0, rowx, loopBarisSection);
                                                                    //end dept

                                                                    //nanti cek ada kah employee di dept ini sectionnya
                                                                    int noEMployeeNoSection=1;
                                                                    if (hashEmployeeSection != null && hashEmployeeSection.size() > 0) {
                                                                        SessEmployee sessEmployee = (SessEmployee) hashEmployeeSection.get(dSection.getOID());
                                                                        Vector cloneListEmp =sessEmployee != null && sessEmployee.getListEmployee() != null && sessEmployee.getListEmployee().size() > 0?new Vector(sessEmployee.getListEmployee()):null;
                                                                        if (cloneListEmp!=null) {
                                                                            RekapitulasiAbsensiGrandTotal rekapitulasiAbsensiGrandTotalSec = new RekapitulasiAbsensiGrandTotal();
                                                                            int loopBarieEMpSec=0;
                                                                            for (int idxEmpSec = 0; idxEmpSec < cloneListEmp.size(); idxEmpSec++) { 
                                                                                EmployeeMinimalis employeeMinimalis = (EmployeeMinimalis) cloneListEmp.get(idxEmpSec);
                                                                                //employee cek ada secktion
                                                                                rekapitulasiPresenceDanSchedule = new RekapitulasiPresenceDanSchedule(); 
                                                                                
                                                                                if(employeeMinimalis.getSectionId()!=0){
                                                                                    if(listAttdAbsensi!=null && listAttdAbsensi.size()>0 && listAttdAbsensi.containsKey(""+employeeMinimalis.getOID()) && hashTblPayDay!=null){
                                                                                            rekapitulasiPresenceDanSchedule = (RekapitulasiPresenceDanSchedule)listAttdAbsensi.get(""+employeeMinimalis.getOID());
                                                                                            listAttdAbsensi.remove(""+employeeMinimalis.getOID());
                                                                                            
                                                                                            
                                                                                            rekapitulasiAbsensiGrandTotal.setSo8j(rekapitulasiAbsensiGrandTotal.getSo8j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                                            
                                                                                    
                                                                                            rekapitulasiAbsensiGrandTotalSec.setSo8j(rekapitulasiAbsensiGrandTotalSec.getSo8j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                                            
                                                                                            
                                                                                            rekapitulasiAbsensiGrandTotalPerCompany.setSo8j(rekapitulasiAbsensiGrandTotalPerCompany.getSo8j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                                            
                                                                                    
                                                                                    }
                                                                                    loopBarieEMpSec = loopBarieEMpSec + 1;
                                                                                    cloneListEmp.remove(idxEmpSec);
                                                                                    TotalEmployeePerDeptnSection = TotalEmployeePerDeptnSection+1;
                                                                                    TotalEmployeePerCompany = TotalEmployeePerCompany + 1;
                                                                                    idxEmpSec = idxEmpSec -1;
                                                                                    rowx = new Vector();
                                                                                    rowx.add("" + (noEMployeeNoSection++));
                                                                                    rowx.add(""+employeeMinimalis.getEmployeeNum());
                                                                                    rowx.add(""+employeeMinimalis.getFullName());  
                                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam()); 
                                                                                    rowx.add(""+Formater.formatNumberMataUang( (Double) Stock_Opname_8Jam_Value, "Rp")); 
                                                                                    rowx.add(""+Formater.formatNumberMataUang((rekapitulasiPresenceDanSchedule.getPresenceOnTime()*  (Double) Stock_Opname_8Jam_Value ), "Rp"));
                                                                                    for (int idx = 0; idx < itDate; idx++) {
                                                                                        //rowx.add("" + idx);
                                                                                        Date selectedDate = new Date(rekapitulasiAbsensi.getDtFrom().getYear(), rekapitulasiAbsensi.getDtFrom().getMonth(), (rekapitulasiAbsensi.getDtFrom().getDate() + idx));
                                                                                        if (rekapitulasiPresenceDanSchedule.getDataReason(selectedDate).equals("/B")){
                                                                                            String dataR = rekapitulasiPresenceDanSchedule.getDataReason(selectedDate);
                                                                                            String sDataR = dataR.substring(1, dataR.length());        
                                                                                            rowx.add("<b style=\"color:red\"><p align=\"center\">"  + sDataR +"</b>");
                                                                                            } else {
                                                                                            rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate));
                                                                                           
                                                                                            }
                                                                                       // rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate));
                                                                                    }
                                                                                    
                                                                                  
                                                                                    if(hashTblPayDay!=null){
                                                                                        rekapitulasiAbsensiGrandTotal.setPayDay(rekapitulasiAbsensiGrandTotal.getPayDay()+  (Double) Stock_Opname_8Jam_Value);
                                                                                            rekapitulasiAbsensiGrandTotal.setTotalPayDay(rekapitulasiAbsensiGrandTotal.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam()* (Double) Stock_Opname_8Jam_Value));
                                                                                            rekapitulasiAbsensiGrandTotalSec.setPayDay(rekapitulasiAbsensiGrandTotalSec.getPayDay()+  (Double) Stock_Opname_8Jam_Value);
                                                                                            rekapitulasiAbsensiGrandTotalSec.setTotalPayDay(rekapitulasiAbsensiGrandTotalSec.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam()* (Double) Stock_Opname_8Jam_Value));
                                                                                            rekapitulasiAbsensiGrandTotalPerCompany.setPayDay(rekapitulasiAbsensiGrandTotalPerCompany.getPayDay()+ (Double) Stock_Opname_8Jam_Value);
                                                                                            rekapitulasiAbsensiGrandTotalPerCompany.setTotalPayDay(rekapitulasiAbsensiGrandTotalPerCompany.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam()* (Double) Stock_Opname_8Jam_Value));
                                                                                    }
                                                                                    
                                                                                    ctrlist.drawListRowExcel(outJsp, 0, rowx, loopBarieEMpSec);
                                                                                     //end employee list but no section
                                                                                }
                                                                                 ///ini berati employee'nya ada section
                                                                                     //hashEmployeeAdaSection.put(employeeMinimalis.getSectionId(), employeeMinimalis);
                                                                                     //nanti buatlah hashtableEmployee tanpa section dan hasth ada section
                                                                                     //untuk pencarian hari kehadiran, absensi dll pakai fungsi summary attdance nnti itu vector,loop verctor tsb, jika employeeId'nya sama maka hapus lah
                                                                            }//end employee
                                                                            
                                                                            rowx = new Vector();
                                                                            rowx.add("" + (noEMployeeNoSection-1));
                                                                            rowx.add("");
                                                                            rowx.add("<b>Total Section</b>");  
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotalSec.getSo8j()+"</b>");
                                                                            rowx.add("<b>"+Formater.formatNumberMataUang(rekapitulasiAbsensiGrandTotalSec.getPayDay(), "Rp")+"</b>");
                                                                            rowx.add("<b>"+Formater.formatNumberMataUang((rekapitulasiAbsensiGrandTotalSec.getTotalPayDay()), "Rp") +"</b>");
                                                                            for (int idx = 0; idx < itDate; idx++) {
                                                                                rowx.add("");
                                                                            }
                                                                            
                                                                            ctrlist.drawListRowExcel(outJsp, 0, rowx, 0); 
                                                                            
                                                                        }//end list emp
                                                                    }//end hasEmployee
                                                                    
                                                                    
                                                                }
                                                            }
                                                            
                                                            //tota dept yg ada sectionnya
                                                                            rowx = new Vector();
                                                                            rowx.add("" + (TotalEmployeePerDeptnSection));
                                                                            rowx.add("");
                                                                            rowx.add("<b>Total Department</b>");  
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getSo8j()+"</b>"); 
                                                                            rowx.add("<b>"+Formater.formatNumberMataUang(rekapitulasiAbsensiGrandTotal.getPayDay(), "Rp") +"</b>");
                                                                            rowx.add("<b>"+Formater.formatNumberMataUang((rekapitulasiAbsensiGrandTotal.getTotalPayDay()), "Rp") +"</b>");
                                                                            for (int idx = 0; idx < itDate; idx++) {
                                                                                rowx.add("");
                                                                            }
                                                                            
                                                                            ctrlist.drawListRowExcel(outJsp, 0, rowx, 0); 
                                                        }
                                            }
                                        }
                                    }

                                }//end loop division
                            }
                        }//end hashDivision
                        ///ini untuk grand total masing" company
                        rowx = new Vector();
                        rowx.add("" + (TotalEmployeePerCompany));
                        rowx.add("");
                        rowx.add("<b>Grand Total ---->>>>></b>");  
                        rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getSo8j()+"</b>");  
                        
                        rowx.add("<b>"+Formater.formatNumberMataUang(rekapitulasiAbsensiGrandTotalPerCompany.getPayDay(), "Rp") +"</b>");
                        rowx.add("<b>"+Formater.formatNumberMataUang((rekapitulasiAbsensiGrandTotalPerCompany.getTotalPayDay()), "Rp") +"</b>"); 
                        for (int idx = 0; idx < itDate; idx++) {
                            rowx.add("");
                        }
                         ctrlist.drawListRowExcel(outJsp, 0, rowx, 0); 
                    }//end company
                }

                result = "";
                ctrlist.drawListEndTable(outJsp);
            } else {
                result = "<i>Belum ada data dalam sistem ...</i>";
            }

        } catch (Exception ex) {
            System.out.println("Exception export summary Attd" + " Emp:" + empNumTest + " " + ex);
        }
        return result;
    }

%>



<%!    public String drawListTdd(JspWriter outJsp, int source_type) {
        String result = "";
        ControlList ctrlist = new ControlList();
        ControlList ctrlist1 = new ControlList();
        ControlList ctrlist2 = new ControlList();
        ControlList ctrlist3 = new ControlList();
        
        if (source_type == 0){
            
        ctrlist.setAreaWidth("70%");
        ctrlist.addHeader("<center>Hr Unit</center>", "10%","0","1");
        ctrlist.addHeader("<center>Acct Unit</center>", "10%", "0","1");
        ctrlist.addHeader("<center>Pimpinan Unit</center>", "10%","0","1");
        ctrlist.addHeader("<center>Hr Coorporate</center>", "10%", "0","8");
        ctrlist.addHeader("<center>Director</center>", "10%", "0","7");
        ctrlist.addHeader("<center>Pres Dir & CEO</center>", "10%","0","7");
       
        ctrlist1.setAreaWidth("70%");
        ctrlist1.addHeader("<center> Tgl </center>", "10%","0","1");
        ctrlist1.addHeader("<center> Tgl </center>", "10%", "0","1");
        ctrlist1.addHeader("<center> Tgl </center>", "10%","0","1");
        ctrlist1.addHeader("<center> Tgl </center>", "10%", "0","8");
        ctrlist1.addHeader("<center> Tgl </center>", "10%", "0","7");
        ctrlist1.addHeader("<center> Tgl </center>", "10%","0","7");
        
        ctrlist2.setAreaWidth("70%");
        ctrlist2.addHeader("<center> </center>", "10%","3","1");
        ctrlist2.addHeader("<center> </center>", "10%", "3","1");
        ctrlist2.addHeader("<center> </center>", "10%","3","1");
        ctrlist2.addHeader("<center> </center>", "10%", "3","8");
        ctrlist2.addHeader("<center> </center>", "10%", "3","7");
        ctrlist2.addHeader("<center> </center>", "10%","3","7");
       
        ctrlist3.setAreaWidth("70%");
        ctrlist3.addHeader("<center> </center>", "10%","0","1");
        ctrlist3.addHeader("<center> </center>", "10%", "0","1");
        ctrlist3.addHeader("<center> </center>", "10%","0","1");
        ctrlist3.addHeader("<center> </center>", "10%", "0","8");
        ctrlist3.addHeader("<center> </center>", "10%", "0","7");
        ctrlist3.addHeader("<center> </center>", "10%","0","7");
        
        } else {
        ctrlist.setAreaWidth("70%");
        ctrlist.addHeader("<center>Hr Unit</center>", "10%","0","1");
        ctrlist.addHeader("<center>Acct Unit</center>", "10%", "0","1");
        ctrlist.addHeader("<center>Pimpinan Unit</center>", "10%","0","1");
        ctrlist.addHeader("<center>Hr Coorporate</center>", "10%", "0","1");
        ctrlist.addHeader("<center>Director</center>", "10%", "0","3");
        ctrlist.addHeader("<center>Pres Dir & CEO</center>", "10%","0","9");
       
        ctrlist1.setAreaWidth("70%");
        ctrlist1.addHeader("<center> Tgl </center>", "10%","0","1");
        ctrlist1.addHeader("<center> Tgl </center>", "10%", "0","1");
        ctrlist1.addHeader("<center> Tgl </center>", "10%","0","1");
        ctrlist1.addHeader("<center> Tgl </center>", "10%", "0","1");
        ctrlist1.addHeader("<center> Tgl </center>", "10%", "0","3");
        ctrlist1.addHeader("<center> Tgl </center>", "10%","0","9");
        
        ctrlist2.setAreaWidth("70%");
        ctrlist2.addHeader("<center> </center>", "10%", "3","1");
        ctrlist2.addHeader("<center> </center>", "10%", "3","1");
        ctrlist2.addHeader("<center> </center>", "10%", "3","1");
        ctrlist2.addHeader("<center> </center>", "10%", "3","1");
        ctrlist2.addHeader("<center> </center>", "10%", "3","3");
        ctrlist2.addHeader("<center> </center>", "10%", "3","9");
       
        ctrlist3.setAreaWidth("70%");
        ctrlist3.addHeader("<center> </center>", "10%","0","1");
        ctrlist3.addHeader("<center> </center>", "10%", "0","1");
        ctrlist3.addHeader("<center> </center>", "10%","0","1");
        ctrlist3.addHeader("<center> </center>", "10%", "0","1");
        ctrlist3.addHeader("<center> </center>", "10%", "0","3");
        ctrlist3.addHeader("<center> </center>", "10%","0","9");    
        }
        
        int index = -1;
        String empNumTest = "";
        int no = 0;
        ctrlist.drawListHeaderExcel(outJsp);//header
        ctrlist.drawListEndTable(outJsp);
        ctrlist1.drawListHeaderExcel1(outJsp);//header
        ctrlist1.drawListEndTable(outJsp);  
        ctrlist2.drawListHeaderExcel1(outJsp);//header
        ctrlist2.drawListEndTable(outJsp); 
        ctrlist3.drawListHeaderExcel1(outJsp);//header
        ctrlist3.drawListEndTable(outJsp); 
       
        return result;
    }

%>

<%!    public String drawListSchedule(JspWriter outJsp,Vector vSchedule) {
        String result = "";
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("70%");
        ctrlist.addHeader("<center>Kode</center>", "10%","0");
        ctrlist.addHeader("<center>Jam Kerja</center>", "10%", "0");
       
       
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
     
        String empNumTest = "";
      
        try {
            if (true) {
                ctrlist.drawListHeaderExcel(outJsp);//header
                    
                      Vector rowx = new Vector();
                      if(vSchedule!=null && vSchedule.size()>0){
                          for(int idx=0;idx<vSchedule.size();idx++){
                              rowx = new Vector();
                              ScheduleSymbol scheduleSymbol = (ScheduleSymbol)vSchedule.get(idx);
                              rowx.add(""+scheduleSymbol.getSymbol());
                              String schIn="";
                              String schOut="";
                              if(scheduleSymbol!=null && scheduleSymbol.getTimeIn()!=null){
                                  schIn = Formater.formatDate(scheduleSymbol.getTimeIn(), "HH:mm");
                              }
                              if(scheduleSymbol!=null && scheduleSymbol.getTimeOut()!=null){
                                  schOut = Formater.formatDate(scheduleSymbol.getTimeOut(), "HH:mm");
                              }
                              rowx.add(""+schIn +" - "+ schOut);
                             ctrlist.drawListRowExcel(outJsp, 0, rowx, idx); 
                          }
                      }
                     
                ctrlist.drawListEndTable(outJsp);
           }

        } catch (Exception ex) {
            System.out.println("Exception export summary Attd" + " Emp:" + empNumTest + " " + ex);
        }
        return result;
    }

%>

<%!    public String drawListReason(JspWriter outJsp,Vector vSchedule,Vector vReason) {
        String result = "";
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("70%");
        ctrlist.addHeader("", "10%","0");
        ctrlist.addHeader("", "10%", "0");
       
       
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
     
        String empNumTest = "";
      
        try {
            if (true) {
                ctrlist.drawListHeaderExcel(outJsp);//header
                    
                      Vector rowx = new Vector();
                      if(vSchedule!=null && vSchedule.size()>0){
                          for(int idx=0;idx<vSchedule.size();idx++){
                               rowx = new Vector();
                              ScheduleSymbol scheduleSymbol = (ScheduleSymbol)vSchedule.get(idx);
                              if(scheduleSymbol!=null && scheduleSymbol.getSymbol()!=null && (scheduleSymbol.getSymbol().equalsIgnoreCase("H") || scheduleSymbol.getSymbol().equalsIgnoreCase("E"))){
                                   rowx.add(""+scheduleSymbol.getSymbol());
                                   rowx.add(""+scheduleSymbol.getSchedule()); 
                                   ctrlist.drawListRowExcel(outJsp, 0, rowx, idx); 
                              }
                             
                          }
                      }
                      if(vReason!=null && vReason.size()>0){
                          for(int res=0;res<vReason.size();res++){
                               rowx = new Vector();
                              Reason reason = (Reason)vReason.get(res);
                              if(reason!=null && reason.getKodeReason()!=null){
                                  rowx.add(""+reason.getKodeReason());
                                   rowx.add(""+reason.getReason()); 
                                   ctrlist.drawListRowExcel(outJsp, 0, rowx, res); 
                              }
                          }
                      }
                      
                     
                ctrlist.drawListEndTable(outJsp);
           }

        } catch (Exception ex) {
            System.out.println("Exception export summary Attd" + " Emp:" + empNumTest + " " + ex);
        }
        return result;
    }

%>

<%

    String source = FRMQueryString.requestString(request, "source");
    int source_type = FRMQueryString.requestInt(request, "source_type");
    int iCommand = FRMQueryString.requestCommand(request);
    
    RekapitulasiAbsensi rekapitulasiAbsensi = new RekapitulasiAbsensi();
    if(session.getValue("rekapitulasi")!=null){
        rekapitulasiAbsensi = (RekapitulasiAbsensi)session.getValue("rekapitulasi"); 
        //rekapitulasiAbsensi.setWhereClauseEMployee("");
    }
    String wherenya = "" ;
     if(session.getValue("wherenya")!=null){
        wherenya = (String)session.getValue("wherenya"); 
        //rekapitulasiAbsensi.setWhereClauseEMployee("");
    }
    
    Vector listCompany = new Vector();
    Hashtable hashDivision = new Hashtable();
    Hashtable hashDepartment = new Hashtable();
    Hashtable hashSection = new Hashtable();
    Hashtable hashEmployee  = new Hashtable();
    Hashtable hashEmployeeSection = new Hashtable();
    Hashtable listAttdAbsensi =null;
      Date dtFrom = null; 
      Date dtTo = null;
      String judul="";
    Vector vReason=null;
    I_Atendance attdConfig = null;
    try {
        attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
    } catch (Exception e) {
        System.out.println("Exception : " + e.getMessage());
        System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
    }
    int iPropInsentifLevel = 0;
        long lHolidays = 0;
        try {
            iPropInsentifLevel = Integer.parseInt(PstSystemProperty.getValueByName("PAYROLL_INSENTIF_MAX_LEVEL"));
            lHolidays = Long.parseLong(PstSystemProperty.getValueByName("OID_PUBLIC_HOLIDAY"));
        } catch (Exception ex) {
            System.out.println("Execption PAYROLL_INSENTIF_MAX_LEVEL: " + ex);
        }

        I_PayrollCalculator payrollCalculatorConfig = null;
        try {
            payrollCalculatorConfig = (I_PayrollCalculator) (Class.forName(PstSystemProperty.getValueByName("PAYROLL_CALC_CLASS_NAME")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception PAYROLL_CALC_CLASS_NAME " + e.getMessage());
        }
        int propReasonNo = -1;
        try {
            propReasonNo = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_REASON_DUTTY_NO"));

        } catch (Exception ex) {
            System.out.println("Execption REASON_DUTTY_NO: " + ex);
        }
        int propCheckLeaveExist = 0;
        try {
            propCheckLeaveExist = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_WHEN_LEAVE_EXIST"));

        } catch (Exception ex) {
            System.out.println("Execption ATTANDACE_WHEN_LEAVE_EXIST: " + ex);
        }

       int showOvertime = 0;
        try {
            showOvertime = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY"));
        } catch (Exception ex) {

            System.out.println("<blink>ATTANDACE_SHOW_OVERTIME_IN_REPORT_DAILY NOT TO BE SET</blink>");
            showOvertime = 0;
        }

        //update by satrya 2013-04-09
        long oidScheduleOff = 0;
        try {
            oidScheduleOff = Long.parseLong(PstSystemProperty.getValueByName("OID_DAY_OFF"));
        } catch (Exception ex) {

            System.out.println("<blink>OID_DAY_OFF NOT TO BE SET</blink>");
            oidScheduleOff = 0;
        }
        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
      
        Hashtable vctSchIDOff = new Hashtable();
        Hashtable hashSchOff = new Hashtable();
        HolidaysTable holidaysTable = new HolidaysTable();
        Hashtable hashPositionLevel = PstPosition.hashGetPositionLevel();
        String whereClausePeriod = "";
        if (rekapitulasiAbsensi.getDtTo() != null && rekapitulasiAbsensi.getDtFrom() != null) {
            whereClausePeriod = "\"" + Formater.formatDate(rekapitulasiAbsensi.getDtTo(), "yyyy-MM-dd HH:mm:ss") + "\" >="
                    + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] + " AND "
                    + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] + " >= \"" + Formater.formatDate(rekapitulasiAbsensi.getDtFrom(), "yyyy-MM-dd HH:mm:ss") + "\"";
        }

        Hashtable hashPeriod = new Hashtable();
       
         //Hashtable hashSectionClone = new Hashtable(hashSection);  
         HashTblPayDay hashTblPayDay = new HashTblPayDay();
        long oidEmployeeDw = 11002;
    Vector vSchedule = PstScheduleSymbol.listAll(); 
    Vector vReasons = PstReason.list(0, 0, PstReason.fieldNames[PstReason.FLD_REASON_TIME] + "=" + PstReason.REASON_TIME_YES, PstReason.fieldNames[PstReason.FLD_REASON_CODE]+ " ASC ");    
    if(iCommand==0 && rekapitulasiAbsensi!=null){ 
            dtFrom = rekapitulasiAbsensi.getDtFrom();
            dtTo= rekapitulasiAbsensi.getDtTo();
            judul= rekapitulasiAbsensi.getJudul();
            listCompany = rekapitulasiAbsensi.getListCompany();  
            /*hashDivision = rekapitulasiAbsensi.getHashDivision();   
            hashDepartment = rekapitulasiAbsensi.getHashDepartment(); 
            hashSection = rekapitulasiAbsensi.getHashSection();    
            hashEmployee = rekapitulasiAbsensi.getHashEmployee(); 
            hashEmployeeSection = rekapitulasiAbsensi.getHashEmployeeSection();
            listAttdAbsensi = rekapitulasiAbsensi.getListAttdAbsensi();
            vReason = rekapitulasiAbsensi.getvReason();*/
            EmployeeSrcRekapitulasiAbs employeeSrcRekapitulasiAbs = PstEmployee.getEmployeeFilter(0, 0, wherenya, PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
            String sectionId = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstEmployee.getSectionIdByEmpId(0, 0, (employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" IN("+employeeSrcRekapitulasiAbs.getEmpId()+")":""), PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]):"";
            
            hashSchOff = PstScheduleSymbol.getHashScheduleIdOFF(PstScheduleCategory.CATEGORY_OFF); 
            vctSchIDOff = PstScheduleSymbol.getHashScheduleId(PstScheduleCategory.CATEGORY_OFF);  
            hashPeriod = PstPeriod.hashlistTblPeriod(0, 0, whereClausePeriod, ""); 
            holidaysTable = PstPublicHolidays.getHolidaysTable(rekapitulasiAbsensi.getDtFrom(), rekapitulasiAbsensi.getDtTo()); 
            hashPositionLevel = PstPosition.hashGetPositionLevel();
            listCompany = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?     PstCompany.list(0, 0, (employeeSrcRekapitulasiAbs.getCompanyId()!=null && employeeSrcRekapitulasiAbs.getCompanyId().length()>0 ?PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]+" IN("+employeeSrcRekapitulasiAbs.getCompanyId()+")":""), PstCompany.fieldNames[PstCompany.FLD_COMPANY])  :new Vector(); 
            hashDivision = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0? PstDivision.hashListDivision(0, 0, (employeeSrcRekapitulasiAbs.getDivisionId()!=null && employeeSrcRekapitulasiAbs.getDivisionId().length()>0?PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" IN("+employeeSrcRekapitulasiAbs.getDivisionId()+")":"")):new Hashtable();   
            hashDepartment = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstDepartment.hashListDepartment(0, 0, (employeeSrcRekapitulasiAbs.getDepartmentId()!=null && employeeSrcRekapitulasiAbs.getDepartmentId().length()>0?PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" IN("+employeeSrcRekapitulasiAbs.getDepartmentId()+")":"")):new Hashtable(); 
            hashSection = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstSection.hashListSection(0, 0, (sectionId!=null && sectionId.length()>0?PstSection.fieldNames[PstSection.FLD_SECTION_ID]+" IN("+sectionId+")":"")):new Hashtable();     
            hashEmployee = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstEmployee.hashListEmployee(0, 0, rekapitulasiAbsensi.whereClauseEmpId(employeeSrcRekapitulasiAbs.getEmpId())):new Hashtable(); 
            hashEmployeeSection = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstEmployee.hashListEmployeeSection(0, 0,  rekapitulasiAbsensi.whereClauseEmpId(employeeSrcRekapitulasiAbs.getEmpId())):new Hashtable();
            listAttdAbsensi =  employeeSrcRekapitulasiAbs.getEmpId()!=null &&  employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstEmpSchedule.getListAttendaceRekap(attdConfig,leaveConfig,rekapitulasiAbsensi.getDtFrom(), rekapitulasiAbsensi.getDtTo(), employeeSrcRekapitulasiAbs.getEmpId(), vctSchIDOff, hashSchOff, iPropInsentifLevel, holidaysTable, hashPositionLevel, payrollCalculatorConfig,hashPeriod):new Hashtable(); 
            vReason = PstReason.listReason(0, 0, PstReason.fieldNames[PstReason.FLD_REASON_TIME] + "=" + PstReason.REASON_TIME_YES, PstReason.fieldNames[PstReason.FLD_REASON_CODE]+ " ASC "); 
            
            if(rekapitulasiAbsensi.getSourceTYpe()!=0){
                hashTblPayDay = PstPayDay.hashtblPayDay(0, 0, "", ""); 
            }            
    }
%>

<%@page contentType="application/x-msexcel" pageEncoding="UTF-8"%>
 
<html> 
    <head>
        <title>HARISMA - Rekapitulasi Absensi</title>
    </head>

    <body >
       
        <table>
           <%if (iCommand == 0) {%> 
                            <tr>
                                <td>
                                    &nbsp;
                                </td> 
                                <td>
                                    <table>
                                        <tr>
                                            
                                           <td><%=drawListSchedule(out,vSchedule)%>&nbsp;<%=drawListReason(out,vSchedule,vReasons)%></td>
                                          
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td style="font-size: large"><b><%=judul%></b></td>
                                        </tr>
                                        <tr>
                                            <td style="font-size: large"><b><%=dtFrom!=null && dtTo!=null? Formater.formatDate(dtFrom,"dd MMM yyyy")+" s/d " + Formater.formatDate(dtTo,"dd MMM yyyy"):"-"%></b></td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <%if(source_type==0){%>
                                            <td><%=drawListRincianSo4(out, rekapitulasiAbsensi,listCompany,hashDivision,hashDepartment,hashSection,hashEmployee,hashEmployeeSection,listAttdAbsensi,hashTblPayDay,oidEmployeeDw)%></td>
                                          <%}else{%>
                                            <td><%=drawListRincianSo8(out, rekapitulasiAbsensi,listCompany,hashDivision,hashDepartment,hashSection,hashEmployee,hashEmployeeSection,listAttdAbsensi,hashTblPayDay,oidEmployeeDw)%></td>
                                           <%}%>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                        </tr>
                                         <tr>
                                            <td><%=drawListTdd(out,source_type)%></td> 
                                        </tr>
                                        
                                         <tr>
                                            <td>&nbsp;</td>
                                        </tr>
                                       
                                    </table>
                                        
                                </td>  
                            </tr>

                            <%}%>
        </table>
   
    </body>

</html>