<%-- 
    Document   : src_list_benefit_deduction
    Created on : Dec 31, 2014, 10:42:46 AM
    Author     : PRISKA
--%>


<%@page import="com.dimata.harisma.entity.payroll.PayComponenttemp"%>
<%@page import="com.dimata.harisma.entity.payroll.PayEmpLevel"%>
<%@page import="com.dimata.harisma.entity.payroll.PayBanks"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayComponent"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@page import="com.dimata.harisma.report.payroll.ListBenefitDeduction"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
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
<%@ include file = "../../main/javainit.jsp" %>

<%!    
    private static Vector logicParser(String text) {
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
<%@ include file = "../../main/checkuser.jsp" %>
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

<%!    public String drawList(JspWriter outJsp,ListBenefitDeduction listBenefitDeduction,Vector listCompany,Hashtable hashDivision,Hashtable hashDepartment,Hashtable hashSection,Hashtable hashEmployee,Hashtable hashEmployeeSection,Hashtable listAttdAbsensi,Vector vReason, Hashtable listdatanew,Vector vpcomponent) {
        String result = "";
        if(listBenefitDeduction==null){
            return result; 
        }
        Date dtfrom = PstPayPeriod.getfromdate(listBenefitDeduction.getPeriodeId());
        Date dtend = PstPayPeriod.getenddate(listBenefitDeduction.getPeriodeId());
        
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
        ctrlist.addHeader("Absensi", "20%", "0", "" + (vReason!=null && vReason.size()>0?4+vReason.size():4));
        ctrlist.addHeader("HK", "5%", "0", "0");
        //ctrlist.addHeader("A", "5%", "0", "0");
        //ctrlist.addHeader("D", "5%", "0", "0");
        ctrlist.addHeader("H", "5%", "0", "0");
        ctrlist.addHeader("R", "5%", "0", "0");
        if(vReason!=null && vReason.size()>0){
            for(int d=0;d<vReason.size();d++){
                Reason reason = (Reason)vReason.get(d);
                ctrlist.addHeader(""+reason.getKodeReason(), "5%", "0", "0"); 
            }
        }
        /*ctrlist.addHeader("S", "5%", "0", "0");
        ctrlist.addHeader("B", "5%", "0", "0");
        ctrlist.addHeader("Oth/I", "5%", "0", "0");*/
        ctrlist.addHeader("Total", "5%", "0", "0");

        /*ctrlist.addHeader("Working Schedule <br> Absensi1", "20%", "0", "" + 15);
         for(int b=0;b<15;b++){
         ctrlist.addHeader("Total"+b, "5%", "0", "0");
         }*/
        
        long dayOFF =0;
            try{
                dayOFF = Long.parseLong(PstSystemProperty.getValueByName("OID_DAY_OFF"));
             }catch(Exception ex){
                 System.out.println("OID_DAY_OFF NOT Be SET"+ex);
                 dayOFF = 0;
             }
        
         long extraDayOFF =0;
            try{
              extraDayOFF = Long.parseLong(PstSystemProperty.getValueByName("OID_EXTRA_OFF"));
             }catch(Exception ex){
                 System.out.println("OID_DAY_OFF NOT Be SET"+ex);
                 extraDayOFF = 0;
             }
         
            long intAl =0;
            try{
                String sintAl = PstSystemProperty.getValueByName("VALUE_ANNUAL_LEAVE"); 
                intAl = Integer.parseInt(sintAl);
             }catch(Exception ex){
                 System.out.println("VALUE_ANNUAL_LEAVE NOT Be SET"+ex);
                 intAl = 0;
             }
                
                long intDp =0;
            try{
                String sintDp = PstSystemProperty.getValueByName("VALUE_DAY_OF_PAYMENT"); 
                intDp = Integer.parseInt(sintDp);
             }catch(Exception ex){
                 System.out.println("VALUE_DAY_OF_PAYMENT NOT Be SET"+ex);
                 intDp = 0;
             }
            
            long intB =0;
            try{
                String sintB = PstSystemProperty.getValueByName("VALUE_B_REASON_SYMBOL"); 
                intB = Integer.parseInt(sintB);
             }catch(Exception ex){
                 System.out.println("VALUE_DAY_OF_PAYMENT NOT Be SET"+ex);
                 intB = 0;
             } 
         ctrlist.addHeader("VALUE <br>" + Formater.formatDate(dtfrom, "dd MMM yyyy") + " s/d " + Formater.formatDate(dtend, "dd MMM yyyy"), "20%", "0", "" + vpcomponent.size());
               
        if(vpcomponent!=null && vpcomponent.size()>0){
            for(int d=0;d<vpcomponent.size();d++){
                PayComponent payComponent = (PayComponent)vpcomponent.get(d);
                ctrlist.addHeader(""+payComponent.getCompName(), "5%", "0", "0"); 
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
        int totalpercompany = 0;
        try {
            if (dtfrom != null && dtend != null && listCompany!=null && listCompany.size()>0) {
                ctrlist.drawListHeaderWithJsVer2(outJsp);//header
                // membuat variable
                RekapitulasiPresenceDanSchedule rekapitulasiPresenceDanSchedule = new RekapitulasiPresenceDanSchedule();
                //listAttdAbsensi = PstEmpSchedule.getListAttendaceRekap(attdConfig,leaveConfig,selectedDateFrom, selectedDateTo, getListEmployeeId, vctSchIDOff, hashSchOff, iPropInsentifLevel, holidaysTable, hashPositionLevel, payrollCalculatorConfig,hashPeriod,hashTblOvertimeDetail);
                 
                if (listCompany != null && listCompany.size() > 0) { 
                    for (int idxcom = 0; idxcom < listCompany.size(); idxcom++) {
                        Company company = (Company) listCompany.get(idxcom);
                        Vector rowx = new Vector();
                        rowx.add("");
                        rowx.add("<font color=\"red\"> <B># " + company.getCompany() + "</B></font>");
                       // rowx.add("");
                       // rowx.add("");
                        rowx.add("");
                        rowx.add("");
                        rowx.add("");
                        rowx.add("");
                        rowx.add("");
                        if(vReason!=null && vReason.size()>0){
                            for(int d=0;d<vReason.size();d++){
                                //Reason reason = (Reason)vReason.get(d);
                                rowx.add(""); 
                            }
                        }
                        if(vpcomponent!=null && vpcomponent.size()>0){
                            for(int d=0;d<vpcomponent.size();d++){
                                rowx.add("");
                            }
                        }
                        /*for(int b=0;b<15;b++){
                         rowx.add("Total"+b);
                         }*/
                        ctrlist.drawListRowJsVer2(outJsp, 0, rowx, idxcom);
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
                                   // rowx.add("");
                                   // rowx.add("");
                                    rowx.add("");
                                    rowx.add("");
                                    rowx.add("");
                                    rowx.add("");
                                    rowx.add("");
                                    if(vReason!=null && vReason.size()>0){
                                        for(int d=0;d<vReason.size();d++){
                                            //Reason reason = (Reason)vReason.get(d);
                                            rowx.add(""); 
                                        }
                                    }
                                    if(vpcomponent!=null && vpcomponent.size()>0){
                                        for(int d=0;d<vpcomponent.size();d++){
                                            rowx.add("");
                                        }
                                    }
                                    ctrlist.drawListRowJsVer2(outJsp, 0, rowx, idxDiv);

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
                                                //rowx.add("");
                                               // rowx.add("");
                                                rowx.add("");
                                                rowx.add("");
                                                rowx.add("");
                                                rowx.add("");
                                                rowx.add("");
                                                if(vReason!=null && vReason.size()>0){
                                                    for(int d=0;d<vReason.size();d++){
                                                        //Reason reason = (Reason)vReason.get(d);
                                                        rowx.add(""); 
                                                    }
                                                }
                                                if(vpcomponent!=null && vpcomponent.size()>0){
                                                    for(int d=0;d<vpcomponent.size();d++){
                                                        rowx.add("");
                                                    }
                                                }
                                                ctrlist.drawListRowJsVer2(outJsp, 0, rowx, idxDept);
                                                //end dept

                                                //nanti cek ada kah employee di dept ini sectionnya
                                               int TotalEmployeePerDeptnSection=0;
                                               RekapitulasiAbsensiGrandTotal rekapitulasiAbsensiGrandTotal = new RekapitulasiAbsensiGrandTotal();
                                                if (hashEmployee != null && hashEmployee.size() > 0) { 
                                                    SessEmployee sessEmployee = (SessEmployee) hashEmployee.get(department.getOID());
                                                    Vector sessEmployeeClone =sessEmployee != null && sessEmployee.getListEmployee() != null && sessEmployee.getListEmployee().size() > 0? new Vector(sessEmployee.getListEmployee()):null;
                                                    if (sessEmployeeClone!=null) {
                                                        int noEMployeeNoSection=1;
                                                        
                                                        for (int idxEmp = 0; idxEmp < sessEmployeeClone.size(); idxEmp++) {
                                                           
                                                            EmployeeMinimalis employeeMinimalis = (EmployeeMinimalis) sessEmployeeClone.get(idxEmp);
                                                            //employee cek ada secktion
                                                           rekapitulasiPresenceDanSchedule = new RekapitulasiPresenceDanSchedule(); 
                                                            
                                                            if(employeeMinimalis.getSectionId()==0){
                                                                 int hk = 0 ;
                                                            Vector reasonnew = new Vector();
                                                                if(listAttdAbsensi!=null && listAttdAbsensi.size()>0 && listAttdAbsensi.containsKey(""+employeeMinimalis.getOID())){
                                                                 rekapitulasiPresenceDanSchedule = (RekapitulasiPresenceDanSchedule)listAttdAbsensi.get(""+employeeMinimalis.getOID());
                                                                 listAttdAbsensi.remove(""+employeeMinimalis.getOID());
                                                                 //rekapitulasiAbsensiGrandTotal = new RekapitulasiAbsensiGrandTotal();
                                                                 
                                                                 if(vReason!=null && vReason.size()>0){
                                                                    for(int d=0;d<vReason.size();d++){
                                                                        Reason reason = (Reason)vReason.get(d);
                                                                        int totReason=rekapitulasiPresenceDanSchedule.getTotalReason(reason.getNo());

                                                                        if (reason.getNo() == intAl || reason.getNo() == intDp || reason.getNo() == intB ){                                                                                         
                                                                         hk = hk + totReason; 
                                                                        }
                                                                                            //rowx.add(""+totReason);
                                                                         reasonnew.add(""+totReason);

                                                                        if(rekapitulasiAbsensiGrandTotal!=null && rekapitulasiAbsensiGrandTotal.getReason().containsKey(""+reason.getNo())){
                                                                        int totRes= rekapitulasiAbsensiGrandTotal.getTotalReason(reason.getNo())+totReason;
                                                                            rekapitulasiAbsensiGrandTotal.addReason(reason.getNo(), totRes);
                                                                        }else{
                                                                            rekapitulasiAbsensiGrandTotal.addReason(reason.getNo(), totReason);
                                                                        }
                                                                        
                                                                        //if(rekapitulasiAbsensiGrandTotalPerCompany!=null && rekapitulasiAbsensiGrandTotalPerCompany.getReason().containsKey(""+reason.getNo())){
                                                                         //   int totRes= rekapitulasiAbsensiGrandTotalPerCompany.getTotalReason(reason.getNo())+totReason;
                                                                        //    rekapitulasiAbsensiGrandTotalPerCompany.addReason(reason.getNo(), totRes);
                                                                        //}else{
                                                                        //    rekapitulasiAbsensiGrandTotalPerCompany.addReason(reason.getNo(), totReason);
                                                                        //}
                                                                        
                                                                    }
                                                                }
                                                                 rekapitulasiPresenceDanSchedule.setTotalWorkingDays((rekapitulasiPresenceDanSchedule.getTotalWorkingDays()));
                                                                                     rekapitulasiPresenceDanSchedule.setRealTotalReason((hk)); 
                                                                 
                                                                 //rekapitulasiAbsensiGrandTotal.setHK(rekapitulasiAbsensiGrandTotal.getHK()+rekapitulasiPresenceDanSchedule.getPresenceOnTime());
                                                                 rekapitulasiAbsensiGrandTotal.setHK(rekapitulasiAbsensiGrandTotal.getHK()+rekapitulasiPresenceDanSchedule.getTotalWorkingDays());
                                                                 rekapitulasiAbsensiGrandTotal.setH(rekapitulasiAbsensiGrandTotal.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                 rekapitulasiAbsensiGrandTotal.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotal.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                 rekapitulasiAbsensiGrandTotal.setAnnualLeave(rekapitulasiAbsensiGrandTotal.getAnnualLeave()+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                 rekapitulasiAbsensiGrandTotal.setdPayment(rekapitulasiAbsensiGrandTotal.getdPayment()+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                 
                                                                 //rekapitulasiAbsensiGrandTotalPerCompany.setHK(rekapitulasiAbsensiGrandTotalPerCompany.getHK()+rekapitulasiPresenceDanSchedule.getPresenceOnTime());
                                                                 rekapitulasiAbsensiGrandTotalPerCompany.setHK(rekapitulasiAbsensiGrandTotalPerCompany.getHK()+rekapitulasiPresenceDanSchedule.getTotalWorkingDays());
                                                                 rekapitulasiAbsensiGrandTotalPerCompany.setH(rekapitulasiAbsensiGrandTotalPerCompany.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                 rekapitulasiAbsensiGrandTotalPerCompany.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotalPerCompany.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                 rekapitulasiAbsensiGrandTotalPerCompany.setAnnualLeave(rekapitulasiAbsensiGrandTotalPerCompany.getAnnualLeave()+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                 rekapitulasiAbsensiGrandTotalPerCompany.setdPayment(rekapitulasiAbsensiGrandTotalPerCompany.getdPayment()+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                 
                                                                }
                                                               TotalEmployeePerDeptnSection = TotalEmployeePerDeptnSection+1;
                                                               TotalEmployeePerCompany = TotalEmployeePerCompany + 1; 
                                                                rowx = new Vector();
                                                                rowx.add("" + (noEMployeeNoSection++));
                                                                rowx.add(""+employeeMinimalis.getEmployeeNum());
                                                                rowx.add(""+employeeMinimalis.getFullName());  
                                                                //rowx.add(""+rekapitulasiPresenceDanSchedule.getPresenceOnTime()); 
                                                                rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalWorkingDays()); 
                                                                //rowx.add(""+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                //rowx.add(""+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                rowx.add(""+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                /*rowx.add("S");
                                                                rowx.add("B");
                                                                rowx.add("oth");*/
                                                               if(reasonnew!=null && reasonnew.size()>0){
                                                                                        for(int d=0;d<reasonnew.size();d++){
                                                                                            String reasonn = (String) reasonnew.get(d);
                                                                                         rowx.add(""+reasonn) ;

                                                                                        }
                                                                }
                                                                // ini dia total semua ^-^
                                                                rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalSemuanya());
                                                                totalpercompany = totalpercompany + rekapitulasiPresenceDanSchedule.getTotalSemuanya() ;
                                                                
                                                                Vector temp = (Vector) listdatanew.get(employeeMinimalis.getOID());
                                                                     if (temp != null && temp.size()>0 ){
                                                                                        Vector pcomp = (Vector) temp.get(7);
                                                                                         if(pcomp!=null && pcomp.size() > 0){
                                                                                            for(int d=0;d<pcomp.size();d++){
                                                                                                PayComponenttemp payComponenttemp = (PayComponenttemp) pcomp.get(d);

                                                                                               //Reason reason = (Reason)vReason.get(d);
                                                                                                rowx.add("Rp. " + Formater.formatNumber(payComponenttemp.getCompValue(), "#,###.##"));
                                                                                                }
                                                                                            }
                                                                                     } else {
                                                                                        if(vpcomponent!=null && vpcomponent.size()>0){
                                                                                            for(int d=0;d<vpcomponent.size();d++){
                                                                                                rowx.add("");
                                                                                            }
                                                                                        }
                                                                                     }
                                                                ctrlist.drawListRowJsVer2(outJsp, 0, rowx, idxEmp);
                                                                 //end employee list but no section
                                                            }
                                                            //untuk section gk di buat d sini

                                                        }//end loop employee
                                                        
                                                        if(hashSection==null ||  (hashSection != null && hashSection.size() > 0 && hashSection.containsKey(""+department.getOID())==false)){
                                                                rowx = new Vector();
                                                                rowx.add("" + (noEMployeeNoSection-1));
                                                                rowx.add("");
                                                                rowx.add("<b>Total Department</b>");  
                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getHK()+"</b>"); 
                                                                //rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getAnnualLeave()+"</b>");
                                                                //rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getdPayment()+"</b>");
                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getH()+"</b>");
                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getLateLebihLimaMenit()+"</b>");
                                                                /*rowx.add("<b>"+TotalSperDept+"</b>");
                                                                rowx.add("<b>"+TotalBperDept+"</b>");
                                                                rowx.add("<b>"+TotalIPerDept+"</b>");*/
                                                                if(vReason!=null && vReason.size()>0){
                                                                    for(int d=0;d<vReason.size();d++){
                                                                        Reason reason = (Reason)vReason.get(d);
                                                                        int totReason=rekapitulasiAbsensiGrandTotal.getGrandTotalReason(reason.getNo());
                                                                        rowx.add(""+totReason);
                                                                    }
                                                                }
                                                                rowx.add("<b>"+(rekapitulasiAbsensiGrandTotal.getTotalSemuanya())+"</b>");
                                                                if(vpcomponent!=null && vpcomponent.size()>0){
                                                                    for(int d=0;d<vpcomponent.size();d++){
                                                                        rowx.add("");
                                                                    }
                                                                }
                                                                ctrlist.drawListRowJsVer2(outJsp, 0, rowx, 0); 
                                                        
                                                        }
                                                        
                                                    }
                                                }//end hasEmployee
                                                
                                                //jika ada section
                                               
                                                    if (hashSection != null && hashSection.size() > 0 && hashSection.containsKey(""+department.getOID())) {
                                                            SessSection sessSection = (SessSection) hashSection.get(""+department.getOID());
                                                            Vector vSection = sessSection != null && sessSection.getListSection() != null && sessSection.getListSection().size() > 0?new Vector(sessSection.getListSection()):null;
                                                            if (vSection!=null) {
                                                                int loopBarisSection=0;
                                                                for (int idxSec = 0; idxSec < vSection.size(); idxSec++) {
                                                                    Section dSection = (Section) vSection.get(idxDept);
                                                                    //dept
                                                                    loopBarisSection = loopBarisSection + 1;
                                                                    vSection.remove(idxSec);
                                                                    idxSec = idxSec -1;
                                                                    rowx = new Vector();
                                                                    rowx.add("");
                                                                    rowx.add(" &nbsp;&nbsp;<b>>>> " + dSection.getSection()+"</b>");
                                                                   // rowx.add("");
                                                                   // rowx.add("");
                                                                    rowx.add("");
                                                                    rowx.add("");
                                                                    rowx.add("");
                                                                    rowx.add("");
                                                                    if(vReason!=null && vReason.size()>0){
                                                                        for(int d=0;d<vReason.size();d++){
                                                                            //Reason reason = (Reason)vReason.get(d);
                                                                            rowx.add(""); 
                                                                        }
                                                                    }
                                                                    rowx.add("");
                                                                    if(vpcomponent!=null && vpcomponent.size()>0){
                                                                        for(int d=0;d<vpcomponent.size();d++){
                                                                            rowx.add("");
                                                                        }
                                                                    }
                                                                    ctrlist.drawListRowJsVer2(outJsp, 0, rowx, loopBarisSection);
                                                                    //end dept

                                                                    //nanti cek ada kah employee di dept ini sectionnya
                                                                    int noEMployeeNoSection=1;
                                                                    if (hashEmployeeSection != null && hashEmployeeSection.size() > 0) {
                                                                        SessEmployee sessEmployee = (SessEmployee) hashEmployeeSection.get(dSection.getOID());
                                                                        if (sessEmployee != null && sessEmployee.getListEmployee() != null && sessEmployee.getListEmployee().size() > 0) {
                                                                            RekapitulasiAbsensiGrandTotal rekapitulasiAbsensiGrandTotalSec = new RekapitulasiAbsensiGrandTotal();
                                                                            int loopBarieEMpSec=0;
                                                                            Vector cloneListEMp = sessEmployee.getListEmployee()!=null && sessEmployee.getListEmployee().size()>0?new Vector(sessEmployee.getListEmployee()):new Vector();
                                                                            for (int idxEmpSec = 0; idxEmpSec < cloneListEMp.size(); idxEmpSec++) {
                                                                                EmployeeMinimalis employeeMinimalis = (EmployeeMinimalis) cloneListEMp.get(idxEmpSec);
                                                                                //employee cek ada secktion
                                                                                rekapitulasiPresenceDanSchedule = new RekapitulasiPresenceDanSchedule(); 
                                                                                
                                                                                if(employeeMinimalis.getSectionId()!=0){
                                                                                    Vector reasonnew = new Vector();
                                                                                    int hk = 0;
                                                                                    if(listAttdAbsensi!=null && listAttdAbsensi.size()>0 && listAttdAbsensi.containsKey(""+employeeMinimalis.getOID())){
                                                                                     rekapitulasiPresenceDanSchedule = (RekapitulasiPresenceDanSchedule)listAttdAbsensi.get(""+employeeMinimalis.getOID());
                                                                                     listAttdAbsensi.remove(""+employeeMinimalis.getOID());
                                                                                     //rekapitulasiAbsensiGrandTotal = new RekapitulasiAbsensiGrandTotal();
                                                                                     //rekapitulasiAbsensiGrandTotal.setHK(rekapitulasiAbsensiGrandTotal.getHK()+rekapitulasiPresenceDanSchedule.getPresenceOnTime());
                                                                                     
                                                                                      if(vReason!=null && vReason.size()>0){
                                                                                        for(int d=0;d<vReason.size();d++){
                                                                                            Reason reason = (Reason)vReason.get(d);
                                                                                            int totReason=rekapitulasiPresenceDanSchedule.getTotalReason(reason.getNo());
                                                                                            if (reason.getNo() == intAl || reason.getNo() == intDp || reason.getNo() == intB ){
                                                                                            hk = hk + totReason; 
                                                                                               }
                                                                                            //rowx.add(""+totReason);
                                                                                            reasonnew.add(""+totReason);
                                                                                            if(rekapitulasiAbsensiGrandTotalSec!=null && rekapitulasiAbsensiGrandTotalSec.getReason().containsKey(""+reason.getNo())){
                                                                                                int totRes= rekapitulasiAbsensiGrandTotalSec.getTotalReason(reason.getNo())+totReason;
                                                                                                rekapitulasiAbsensiGrandTotalSec.addReason(reason.getNo(), totRes);
                                                                                            }else{
                                                                                                rekapitulasiAbsensiGrandTotalSec.addReason(reason.getNo(), totReason);
                                                                                            }
                                                                                            if(rekapitulasiAbsensiGrandTotal!=null && rekapitulasiAbsensiGrandTotal.getReason().containsKey(""+reason.getNo())){
                                                                                                int totRes= rekapitulasiAbsensiGrandTotal.getTotalReason(reason.getNo())+totReason;
                                                                                                rekapitulasiAbsensiGrandTotal.addReason(reason.getNo(), totRes);
                                                                                            }else{
                                                                                                rekapitulasiAbsensiGrandTotal.addReason(reason.getNo(), totReason);
                                                                                            }
                                                                                            
                                                                                          //  if(rekapitulasiAbsensiGrandTotalPerCompany!=null && rekapitulasiAbsensiGrandTotalPerCompany.getReason().containsKey(""+reason.getNo())){
                                                                                          //      int totRes= rekapitulasiAbsensiGrandTotalPerCompany.getTotalReason(reason.getNo())+totReason;
                                                                                          //      rekapitulasiAbsensiGrandTotalPerCompany.addReason(reason.getNo(), totRes);
                                                                                           // }else{
                                                                                           //     rekapitulasiAbsensiGrandTotalPerCompany.addReason(reason.getNo(), totReason);
                                                                                           // }
                                                                                            

                                                                                        }
                                                                                    }
                                                                                     rekapitulasiPresenceDanSchedule.setTotalWorkingDays((rekapitulasiPresenceDanSchedule.getTotalWorkingDays()));
                                                                                     rekapitulasiPresenceDanSchedule.setRealTotalReason((hk));   
                                                                                     
                                                                                     rekapitulasiAbsensiGrandTotal.setHK(rekapitulasiAbsensiGrandTotal.getHK()+rekapitulasiPresenceDanSchedule.getTotalWorkingDays());
                                                                                     rekapitulasiAbsensiGrandTotal.setH(rekapitulasiAbsensiGrandTotal.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                                     rekapitulasiAbsensiGrandTotal.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotal.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                                     rekapitulasiAbsensiGrandTotal.setAnnualLeave(rekapitulasiAbsensiGrandTotal.getAnnualLeave()+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                                     rekapitulasiAbsensiGrandTotal.setdPayment(rekapitulasiAbsensiGrandTotal.getdPayment()+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                 
                                                                                     //rekapitulasiAbsensiGrandTotalSec.setHK(rekapitulasiAbsensiGrandTotalSec.getHK()+rekapitulasiPresenceDanSchedule.getPresenceOnTime());
                                                                                     rekapitulasiAbsensiGrandTotalSec.setHK(rekapitulasiAbsensiGrandTotalSec.getHK()+rekapitulasiPresenceDanSchedule.getTotalWorkingDays());
                                                                                     
                                                                                     rekapitulasiAbsensiGrandTotalSec.setH(rekapitulasiAbsensiGrandTotalSec.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                                     rekapitulasiAbsensiGrandTotalSec.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotalSec.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                                     rekapitulasiAbsensiGrandTotalSec.setAnnualLeave(rekapitulasiAbsensiGrandTotalSec.getAnnualLeave()+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                                     rekapitulasiAbsensiGrandTotalSec.setdPayment(rekapitulasiAbsensiGrandTotalSec.getdPayment()+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                 
                                                                                     
                                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setHK(rekapitulasiAbsensiGrandTotalPerCompany.getHK()+rekapitulasiPresenceDanSchedule.getTotalWorkingDays());
                                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setH(rekapitulasiAbsensiGrandTotalPerCompany.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotalPerCompany.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setAnnualLeave(rekapitulasiAbsensiGrandTotalPerCompany.getAnnualLeave()+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setdPayment(rekapitulasiAbsensiGrandTotalPerCompany.getdPayment()+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                 
                                                                                }
                                                                                    loopBarieEMpSec = loopBarieEMpSec + 1;
                                                                                    
                                                                                    TotalEmployeePerDeptnSection = TotalEmployeePerDeptnSection+1;
                                                                                    TotalEmployeePerCompany = TotalEmployeePerCompany + 1;
                                                                                    cloneListEMp.remove(idxEmpSec);
                                                                                    idxEmpSec = idxEmpSec -1;
                                                                                    rowx = new Vector();
                                                                                    rowx.add("" + (noEMployeeNoSection++));
                                                                                    rowx.add(""+employeeMinimalis.getEmployeeNum());
                                                                                    rowx.add(""+employeeMinimalis.getFullName());  
                                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalWorkingDays()); 
                                                                                   // rowx.add(""+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                                   // rowx.add(""+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                                    /*rowx.add("S");
                                                                                    rowx.add("B");
                                                                                    rowx.add("oth");*/
                                                                                   
                                                                                    if(reasonnew!=null && reasonnew.size()>0){
                                                                                        for(int d=0;d<reasonnew.size();d++){
                                                                                            String reasonn = (String) reasonnew.get(d);
                                                                                         rowx.add(""+reasonn) ;

                                                                                        }
                                                                                    }
                                                                                    /*getTotalSemuanya(); is updated by Hendra McHen*/
                                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalSemuanya());
                                                                                    totalpercompany = totalpercompany + rekapitulasiPresenceDanSchedule.getTotalSemuanya() ;
                                                                                    // menampilkan working schedule
                                                                                    Vector temp = (Vector) listdatanew.get(employeeMinimalis.getOID());
                                                                                    if (temp != null && temp.size()>0 ){
                                                                                        Vector pcomp = (Vector) temp.get(7);
                                                                                         if(pcomp!=null && pcomp.size() > 0){
                                                                                            for(int d=0;d<pcomp.size();d++){
                                                                                                PayComponenttemp payComponenttemp = (PayComponenttemp) pcomp.get(d);

                                                                                               //Reason reason = (Reason)vReason.get(d);
                                                                                                rowx.add("Rp. " + Formater.formatNumber(payComponenttemp.getCompValue(), "#,###.##"));
                                                                                                }
                                                                                            }
                                                                                     } else {
                                                                                        if(vpcomponent!=null && vpcomponent.size()>0){
                                                                                            for(int d=0;d<vpcomponent.size();d++){
                                                                                                rowx.add("");
                                                                                            }
                                                                                        }
                                                                                     }
                                                                                    
                                                                                    ctrlist.drawListRowJsVer2(outJsp, 0, rowx, loopBarieEMpSec);
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
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotalSec.getHK()+"</b>"); 
                                                                            //rowx.add("<b>"+rekapitulasiAbsensiGrandTotalSec.getAnnualLeave()+"</b>");
                                                                            //rowx.add("<b>"+rekapitulasiAbsensiGrandTotalSec.getdPayment()+"</b>");
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotalSec.getH()+"</b>");
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotalSec.getLateLebihLimaMenit()+"</b>");
                                                                            int totReason = 0;
                                                                            if(vReason!=null && vReason.size()>0){
                                                                                for(int d=0;d<vReason.size();d++){
                                                                                    Reason reason = (Reason)vReason.get(d);
                                                                                    totReason=rekapitulasiAbsensiGrandTotalSec.getGrandTotalReason(reason.getNo());
                                                                                    rowx.add(""+totReason);
                                                                                }
                                                                            }
                                                                            
                                                                            rekapitulasiAbsensiGrandTotalSec.setTotalAlasan(totReason);
                                                                            rowx.add("<b>"+(rekapitulasiAbsensiGrandTotalSec.getTotalSemuanya())+"</b>");
                                                                            if(vpcomponent!=null && vpcomponent.size()>0){
                                                                                for(int d=0;d<vpcomponent.size();d++){
                                                                                    rowx.add("");
                                                                                }
                                                                            }
                                                                                ctrlist.drawListRowJsVer2(outJsp, 0, rowx, 0); 
                                                                            
                                                                        }//end list emp
                                                                    }//end hasEmployee
                                                                    
                                                                    
                                                                }
                                                            }
                                                            
                                                            //total dept yg ada sectionnya
                                                                            rowx = new Vector();
                                                                            //rowx.add("" + (TotalEmployeePerDeptnSection-1));
                                                                            rowx.add("" + (TotalEmployeePerDeptnSection));
                                                                            rowx.add("");
                                                                            rowx.add("<b>Total Department</b>");  
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getHK()+"</b>"); 
                                                                            //rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getAnnualLeave()+"</b>");
                                                                            //rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getdPayment()+"</b>");
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getH()+"</b>");
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getLateLebihLimaMenit()+"</b>");

                                                                            if(vReason!=null && vReason.size()>0){
                                                                                for(int d=0;d<vReason.size();d++){
                                                                                    Reason reason = (Reason)vReason.get(d);
                                                                                    int totReason=rekapitulasiAbsensiGrandTotal.getGrandTotalReason(reason.getNo());
                                                                                    rowx.add(""+totReason);
                                                                                }
                                                                            }
                                                                            rowx.add("<b>"+(rekapitulasiAbsensiGrandTotal.getTotalSemuanya())+"</b>");
                                                                            if(vpcomponent!=null && vpcomponent.size()>0){
                                                                                    for(int d=0;d<vpcomponent.size();d++){
                                                                                        rowx.add("");
                                                                                    }
                                                                                }
                                                                            
                                                                            ctrlist.drawListRowJsVer2(outJsp, 0, rowx, 0); 
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
                        rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getHK()+"</b>");  
                        //rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getAnnualLeave()+"</b>");
                        //rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getdPayment()+"</b>");
                        rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getH()+"</b>");
                        rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getLateLebihLimaMenit()+"</b>"); 

                        if(vReason!=null && vReason.size()>0){
                            for(int d=0;d<vReason.size();d++){
                                Reason reason = (Reason)vReason.get(d);
                                int totReason=rekapitulasiAbsensiGrandTotalPerCompany.getGrandTotalReason(reason.getNo());
                                rowx.add(""+totReason); 
                            }
                        }
                        rowx.add("<b>"+totalpercompany+"</b>"); 
                   
                        
                        if(vpcomponent!=null && vpcomponent.size()>0){
                             for(int d=0;d<vpcomponent.size();d++){
                                  rowx.add("");
                            }
                        }
                         ctrlist.drawListRowJsVer2(outJsp, 0, rowx, 0); 
                    }//end company
                }

                result = "";
                ctrlist.drawListEndTableJsVer2(outJsp);
            } else {
                result = "<i>Belum ada data dalam sistem ...</i>";
            }

        } catch (Exception ex) {
            System.out.println("Exception export summary Attd" + " Emp:" + empNumTest + " " + ex);
        }
        return result;
    }

%>

<%!    public String drawListnew(JspWriter outJsp,Vector listdatanew, Vector vpcomponent, long sourcetype, Hashtable listAttdAbsensi) {
        String result = "";
        
        if (sourcetype == 0){
             sourcetype = 1;
         } else if (sourcetype == 1){
             sourcetype = 2;
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
        ctrlist.addHeader("Head Count", "5%", "0", "0");
        ctrlist.addHeader("No", "5%", "0", "0");
        ctrlist.addHeader("Nama", "5%", "0", "0");
        ctrlist.addHeader("BANK", "5%", "0", "0");
        ctrlist.addHeader("BANK ACC", "5%", "0", "0");
        ctrlist.addHeader("HARI KERJA", "5%", "0", "0");
        ctrlist.addHeader("Gaji Harian", "5%", "0", "0");
       // ctrlist.addHeader("Absensi", "20%", "0", "" + (vReason!=null && vReason.size()>0?4+vReason.size():4));
       // ctrlist.addHeader("HK", "5%", "0", "0");
        if(vpcomponent!=null && vpcomponent.size()>0){
            for(int d=0;d<vpcomponent.size();d++){
                PayComponent payComponent = (PayComponent)vpcomponent.get(d);
                ctrlist.addHeader(""+payComponent.getCompName(), "5%", "0", "0"); 
            }
        }
        
        ctrlist.addHeader("SUBTOTAL GAJI P + TUNJANGAN", "5%", "0", "0");
        //ctrlist.addHeader("Gaji Harian", "5%", "0", "0");
        
        String empNumTest = "";
        int no = 0 ;
        String TempCompany = "";
        String TempDivision = "";
        String TempDepartment = "";
        String TempSection = "";
        
        String TempCompany2 = "";
        String TempDivision2 = "";
        String TempDepartment2 = "";
        String TempSection2 = "";
        
        //set total company default
        Vector totalCompany = new Vector(); 
        if(vpcomponent!=null && vpcomponent.size()>0){
            for(int d=0;d<vpcomponent.size();d++){
                totalCompany.add(0);
            }
        }
        
        //set total division default
        Vector totalDivision = new Vector(); 
        if(vpcomponent!=null && vpcomponent.size()>0){
            for(int d=0;d<vpcomponent.size();d++){
                totalDivision.add(0);
            }
        }
        
        //set total department default
        Vector totalDepartment = new Vector(); 
        if(vpcomponent!=null && vpcomponent.size()>0){
            for(int d=0;d<vpcomponent.size();d++){
                totalDepartment.add(0);
            }
        }
        
        //set total section default
        Vector totalSection = new Vector(); 
        if(vpcomponent!=null && vpcomponent.size()>0){
            for(int d=0;d<vpcomponent.size();d++){
                totalSection.add(0);
            }
        }
        
        try {
            if (listdatanew.size() > 0){
                ctrlist.drawListHeaderExcel(outJsp);//header
                    for (int i=0; i<listdatanew.size(); i++) 
                {
                      Vector temp = (Vector) listdatanew.get(i);
                      
                      Employee employee = (Employee) temp.get(0);
                      String Company = (String) temp.get(1);
                      String Division = (String) temp.get(2);
                      String Department = (String) temp.get(3);
                      String Section ="";
                      if (temp.get(5) != null){
                          Section = (String) temp.get(4);
                      } else {
                          Section = "";
                      }
                      RekapitulasiPresenceDanSchedule rekapitulasiPresenceDanSchedule = new RekapitulasiPresenceDanSchedule();
                       if(listAttdAbsensi!=null && listAttdAbsensi.size()>0 && listAttdAbsensi.containsKey(""+employee.getOID())){
                                                                 rekapitulasiPresenceDanSchedule = (RekapitulasiPresenceDanSchedule)listAttdAbsensi.get(""+employee.getOID());
                                                                 listAttdAbsensi.remove(""+employee.getOID());
                       }
                      
                      PayBanks payBanks = (PayBanks) temp.get(5);
                      PayEmpLevel payEmpLevel = (PayEmpLevel) temp.get(6);
                      Vector pcomp = (Vector) temp.get(7);
                      
                      if (i==0){
                          TempCompany2 = (String) temp.get(1);
                          TempDivision2 = (String) temp.get(2);
                          TempDepartment2 = (String) temp.get(3);
                          if (temp.get(4) != null){
                          TempSection2 = (String) temp.get(4);    
                          } else {
                              TempSection2 = "";
                          }
                          
                      }
                      
                      if ((!TempCompany.equals(Company)) && (Company != null)){

                         //cetak dulu jumlah yang sebelumnya jumlah total per company
                       
                         
                          
                        no = 0 ;
                        TempCompany = Company;
                        Vector rowxcomp = new Vector();
                        rowxcomp.add("");
                        rowxcomp.add("<B>COMPANY</B>");
                        rowxcomp.add("<B>"+Company+"</B>");
                        rowxcomp.add("");
                        rowxcomp.add("");
                        rowxcomp.add("");
                        rowxcomp.add("");
                    
                        if(pcomp!=null && pcomp.size() > 0){
                                        for(int d=0;d<pcomp.size();d++){
                                            rowxcomp.add("");
                                        }
                                    }
                    
                        ctrlist.drawListRowJsVer2(outJsp, 0, rowxcomp, i);    
                        } else {
                          
                      }
                      
                            if ((!TempDivision.equals(Division)) && (Division != null)){
                                no = 0;
                                TempDivision = Division;
                                Vector rowx = new Vector();
                                rowx.add("");
                                rowx.add("<B>DIVISION</B>");
                                rowx.add("<B> ->"+Division+"</B>");
                                rowx.add("");
                                rowx.add("");
                                rowx.add("");
                                rowx.add("");
                    
                                if(pcomp!=null && pcomp.size() > 0){
                                        for(int d=0;d<pcomp.size();d++){
                                            rowx.add("");
                                        }
                                    }
                                rowx.add("");
                                ctrlist.drawListRowJsVer2(outJsp, 0, rowx, i);    
                            } 
                                
                                if ((!TempDepartment.equals(Department)) && (Department != null)){
                                    no = 0;
                                    TempDepartment = Department;
                                    Vector rowx = new Vector();
                                    rowx.add("");
                                    rowx.add("<B>DEPARTMENT</B>");
                                    rowx.add("<B> -->"+Department+"</B>");
                                    rowx.add("");
                                    rowx.add("");
                                    rowx.add("");
                                    rowx.add("");
                    
                                if(pcomp!=null && pcomp.size() > 0){
                                        for(int d=0;d<pcomp.size();d++){
                                            rowx.add("");
                                        }
                                    }
                                    rowx.add("");
                                ctrlist.drawListRowJsVer2(outJsp, 0, rowx, i);    
                                }
                      
                                    if ((!TempSection.equals(Section))  && (Section != null)){
                                        no = 0;
                                        TempSection = Section;
                                        Vector rowx = new Vector();
                                        rowx.add("");
                                        rowx.add("<B>SECTION</B>");
                                        rowx.add("<B> -->"+Section+"</B>");
                                        rowx.add("");
                                        rowx.add("");
                                        rowx.add("");
                                        rowx.add("");
                    
                                    if(pcomp!=null && pcomp.size() > 0){
                                        for(int d=0;d<pcomp.size();d++){
                                            rowx.add("");
                                        }
                                    }
                                        rowx.add("");
                                    ctrlist.drawListRowJsVer2(outJsp, 0, rowx, i);    
                                    } else {

                                    }
                      
                      Vector rowx = new Vector();
                      rowx.add(""+ (no+1) );
                      rowx.add(""+employee.getEmployeeNum());
                      rowx.add(""+employee.getFullName());
                      rowx.add(payBanks.getBankName()!=null?""+ payBanks.getBankName(): "-"); 
                      rowx.add(""+payEmpLevel.getBankAccNr());
                      rowx.add(""+rekapitulasiPresenceDanSchedule.getPresenceOnTime());
                      rowx.add("");
                    
                      double totalgajitunjanganservice = 0; 
                      if(pcomp!=null && pcomp.size() > 0){
                                        for(int d=0;d<pcomp.size();d++){
                                            PayComponenttemp payComponenttemp = (PayComponenttemp) pcomp.get(d);
                                            
                                            //total section
                                            double totalsect = Double.parseDouble(totalSection.get(d).toString()); 
                                            totalSection.set(d, totalsect+payComponenttemp.getCompValue());
                                            //total percompany
                                            double totalcomp = Double.parseDouble(totalCompany.get(d).toString()); 
                                            totalCompany.set(d, totalcomp+payComponenttemp.getCompValue());
                                            //total division
                                            double totaldiv = Double.parseDouble(totalDivision.get(d).toString()); 
                                            totalDivision.set(d, totaldiv+payComponenttemp.getCompValue());
                                            //total dpartment
                                            double totaldepart = Double.parseDouble(totalDepartment.get(d).toString()); 
                                            totalDepartment.set(d, totaldepart+payComponenttemp.getCompValue());
                                            
                                            //Reason reason = (Reason)vReason.get(d);
                                            rowx.add("Rp. " + Formater.formatNumber(payComponenttemp.getCompValue(), "#,###.##"));
                                            totalgajitunjanganservice = totalgajitunjanganservice + payComponenttemp.getCompValue();
                                        }
                                    }
                     rowx.add("Rp. " + Formater.formatNumber(totalgajitunjanganservice, "#,###.##"));
                                          
                     ctrlist.drawListRowJsVer2(outJsp, 0, rowx, i);
                     
                     //mengecek section selanjutnya
                     Vector temp2 =  new Vector();
                     String section2 = "";
                     String company2 = "";
                     String division2 = "";
                     String department2 = "";
                     if (i != (listdatanew.size()-1)){
                     if (listdatanew.get(i+1) != null){
                       temp2 = (Vector) listdatanew.get(i+1);
                       
                       company2 = (String) temp2.get(1);
                       division2 = (String) temp2.get(2);
                       department2 = (String) temp2.get(3);
                       if (temp2.get(4) != null){
                       section2 = (String) temp2.get(4);    
                       } else {
                           section2 = "";
                       }
                       
                     } else {;
                       section2 = "";
                       company2 = "";
                       division2 = "";
                       department2 = "";
                     }
                     }
                     
                     // cetak grand total section
                     if (((!TempSection2.equals(section2)) && (section2 != null) && (section2.equals("")) && i != 0) || (i ==(listdatanew.size() - 1) && (!TempSection2.equals("")) )){
                                        no = 0;
                                        TempSection2 = section2;
                                        Vector rowxtotalsec = new Vector();
                                        rowxtotalsec.add("");
                                        rowxtotalsec.add("<B>TOTAL</B>");
                                        rowxtotalsec.add("<B> --> SECTION </B>");
                                        rowxtotalsec.add("");
                                        rowxtotalsec.add("");
                                        rowxtotalsec.add("");
                                        rowxtotalsec.add("");
                    
                                    if(pcomp!=null && pcomp.size() > 0){
                                        for(int d=0;d<pcomp.size();d++){
                                            double tsect = Double.valueOf(totalSection.get(d).toString());
                                            rowxtotalsec.add("Rp. " + Formater.formatNumber(tsect, "#,###.##"));
                                          
                                        }
                                    }
                                        rowxtotalsec.add("");
                                     //set total section default
       
                                    if(vpcomponent!=null && vpcomponent.size()>0){
                                        for(int d=0;d<vpcomponent.size();d++){
                                            totalSection.set(d,0);
                                        }
                                    }
                                        
                                    ctrlist.drawListRowJsVer2(outJsp, 0, rowxtotalsec, i);    
                                    }
                     
                     
                                     

                         // cetak grand total department
                     if (((!TempDepartment2.equals(department2)) && (department2 != null) && i != 0) || (i ==(listdatanew.size() - 1))){
                                        no = 0;
                                        TempDepartment2 = department2;
                                        Vector rowxtotaldepart = new Vector();
                                        rowxtotaldepart.add("");
                                        rowxtotaldepart.add("<B>TOTAL</B>");
                                        rowxtotaldepart.add("<B> --> DEPARTMENT </B>");
                                        rowxtotaldepart.add("");
                                        rowxtotaldepart.add("");
                                        rowxtotaldepart.add("");
                                        rowxtotaldepart.add("");
                    
                                    if(pcomp!=null && pcomp.size() > 0){
                                        for(int d=0;d<pcomp.size();d++){
                                            double tdepart = Double.valueOf(totalDepartment.get(d).toString());
                                            rowxtotaldepart.add("Rp. " + Formater.formatNumber(tdepart, "#,###.##"));
                                          
                                        }
                                    }
                    
                                        rowxtotaldepart.add("");
                                     //set total section default
       
                                    if(vpcomponent!=null && vpcomponent.size()>0){
                                        for(int d=0;d<vpcomponent.size();d++){
                                            totalDepartment.set(d,0);
                                        }
                                    }
                                        
                                    ctrlist.drawListRowJsVer2(outJsp, 0, rowxtotaldepart, i);    
                                    }


                         // cetak grand total division
                     if (((!TempDivision2.equals(division2)) && (division2 != null) && i != 0) || (i ==(listdatanew.size() - 1))){
                                        no = 0;
                                        TempDivision2 = division2;
                                        Vector rowxtotaldiv = new Vector();
                                        rowxtotaldiv.add("");
                                        rowxtotaldiv.add("<B>TOTAL</B>");
                                        rowxtotaldiv.add("<B> --> DIVISION </B>");
                                        rowxtotaldiv.add("");
                                        rowxtotaldiv.add("");
                                        rowxtotaldiv.add("");
                                        rowxtotaldiv.add("");
                    
                                    if(pcomp!=null && pcomp.size() > 0){
                                        for(int d=0;d<pcomp.size();d++){
                                            double tdivi = Double.valueOf(totalDivision.get(d).toString());
                                            rowxtotaldiv.add("Rp. " + Formater.formatNumber(tdivi, "#,###.##"));
                                          
                                        }
                                    }
                    
                                        rowxtotaldiv.add("");
                                     //set total section default
       
                                    if(vpcomponent!=null && vpcomponent.size()>0){
                                        for(int d=0;d<vpcomponent.size();d++){
                                            totalDivision.set(d,0);
                                        }
                                    }
                                        
                                    ctrlist.drawListRowJsVer2(outJsp, 0, rowxtotaldiv, i);    
                                    }
                     
                     
                                                             
                                    // cetak grand total company
                     if (((!TempCompany2.equals(company2)) && (company2 != null) && i != 0) || (i ==(listdatanew.size() - 1))){
                                        no = 0;
                                        TempCompany2 = company2;
                                        Vector rowxtotalcomp = new Vector();
                                        rowxtotalcomp.add("");
                                        rowxtotalcomp.add("<B>TOTAL</B>");
                                        rowxtotalcomp.add("<B> --> COMPANY </B>");
                                        rowxtotalcomp.add("");
                                        rowxtotalcomp.add("");
                                        rowxtotalcomp.add("");
                                        rowxtotalcomp.add("");
                    
                                    if(pcomp!=null && pcomp.size() > 0){
                                        for(int d=0;d<pcomp.size();d++){
                                            double tcompa = Double.valueOf(totalCompany.get(d).toString());
                                            rowxtotalcomp.add("Rp. " + Formater.formatNumber(tcompa, "#,###.##"));
                                          
                                        }
                                    }
                    
                                        rowxtotalcomp.add("");
                                     //set total section default
       
                                    if(vpcomponent!=null && vpcomponent.size()>0){
                                        for(int d=0;d<vpcomponent.size();d++){
                                            totalCompany.set(d,0);
                                        }
                                    }
                                        
                                    ctrlist.drawListRowJsVer2(outJsp, 0, rowxtotalcomp, i);    
                                    }
                     
                     
                     no=no+1;
                }
                  
                ctrlist.drawListEndTable(outJsp);
           
              } else {
                 System.out.println("Belum ada data");
              }
        } catch (Exception ex) {
            System.out.println("Exception export summary Attd" + " Emp:" + empNumTest + " " + ex);
        }
        return result;
    }
%>

<%

   
 
    String source = FRMQueryString.requestString(request, "source");
    String[] stsEmpCategory = null;
    int sizeCategory = PstEmpCategory.listAll() != null ? PstEmpCategory.listAll().size() : 0;
    stsEmpCategory = new String[sizeCategory];
    String stsEmpCategorySel = "";
    int maxEmpCat = 0;
    for (int j = 0; j < sizeCategory; j++) {
        String name = "EMP_CAT_" + j;
        String val = FRMQueryString.requestString(request, name);
        stsEmpCategory[j] = val;
        if (val != null && val.length() > 0) {
            //stsEmpCategorySel.add(""+val); 
            stsEmpCategorySel = stsEmpCategorySel + val + ",";
        }
        maxEmpCat++;
    }

    int iCommand = FRMQueryString.requestCommand(request);
    int iErrCode = FRMMessage.NONE;
    int start = FRMQueryString.requestInt(request, "start");
   
    
    ListBenefitDeduction listBenefitDeduction = new ListBenefitDeduction();
    listBenefitDeduction.setCompanyId(FRMQueryString.requestLong(request, "company_id"));
    listBenefitDeduction.setDeptId(FRMQueryString.requestLong(request, "department"));
    listBenefitDeduction.setDivisionId(FRMQueryString.requestLong(request, "division_id"));
    listBenefitDeduction.setPeriodeId(FRMQueryString.requestLong(request, "period_id"));
    listBenefitDeduction.setEmpCategory(stsEmpCategorySel);
    listBenefitDeduction.setFullName(FRMQueryString.requestString(request, "full_name"));
    listBenefitDeduction.setPayrollNumber(FRMQueryString.requestString(request, "emp_number"));
    listBenefitDeduction.setResignSts(FRMQueryString.requestInt(request, "statusResign"));
    listBenefitDeduction.setSectionId(FRMQueryString.requestLong(request, "section"));
    listBenefitDeduction.setSourceTYpe(FRMQueryString.requestInt(request, "source_type"));
    
    
    Vector listCompany = new Vector();
    Hashtable hashDivision = new Hashtable();
    Hashtable hashDepartment = new Hashtable();
    Hashtable hashSection = new Hashtable();
    Hashtable hashEmployee  = new Hashtable();
    Hashtable hashEmployeeSection = new Hashtable();
    Hashtable listAttdAbsensi =null;
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
        Date dtfrom = PstPayPeriod.getfromdate(listBenefitDeduction.getPeriodeId());
        
        Date dtend = PstPayPeriod.getenddate(listBenefitDeduction.getPeriodeId());
        
        if (dtend != null && dtfrom != null) {
            whereClausePeriod = "\"" + Formater.formatDate(dtend, "yyyy-MM-dd HH:mm:ss") + "\" >="
                    + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] + " AND "
                    + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] + " >= \"" + Formater.formatDate(dtfrom, "yyyy-MM-dd HH:mm:ss") + "\"";
        }

        Hashtable hashPeriod = new Hashtable();
        Vector vPayComponent = null;
        Hashtable Listdatanewh = null;
        Vector vReason = null;
    if(iCommand==Command.LIST){
          
         String order = " e." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] ;
         int nilai=1;
         
         if (listBenefitDeduction.getSourceTYpe() == 0){
             nilai = 1;
         } else if (listBenefitDeduction.getSourceTYpe() == 1){
             nilai = 2;
         }
             
         String wherepaycomponent = PstPayComponent.fieldNames[PstPayComponent.FLD_SHOW_IN_REPORTS] + " = " + 1 +" AND " +PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE] + " = " + nilai;
         vPayComponent = PstPayComponent.list(0, 0, wherepaycomponent, PstPayComponent.fieldNames[PstPayComponent.FLD_COMPONENT_ID]  +" ASC "); 
         Listdatanewh = PstEmployee.listBenefitDeductionhas(0, 0, null, order, listBenefitDeduction.getPeriodeId(), listBenefitDeduction.getSourceTYpe(), vPayComponent,listBenefitDeduction );
                
        EmployeeSrcRekapitulasiAbs employeeSrcRekapitulasiAbs = PstEmployee.getEmployeeFilter(0, 0, listBenefitDeduction.getWhereClauseEMployee(), PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
        hashSchOff = PstScheduleSymbol.getHashScheduleIdOFF(PstScheduleCategory.CATEGORY_OFF);
        vctSchIDOff = PstScheduleSymbol.getHashScheduleId(PstScheduleCategory.CATEGORY_OFF); 
        String sectionId = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstEmployee.getSectionIdByEmpId(0, 0, (employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+" IN("+employeeSrcRekapitulasiAbs.getEmpId()+")":""), PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]):"";
        holidaysTable = PstPublicHolidays.getHolidaysTable(dtfrom, dtend); 
        hashPeriod = PstPeriod.hashlistTblPeriod(0, 0, whereClausePeriod, "");
        hashPositionLevel = PstPosition.hashGetPositionLevel();
        listCompany = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?     PstCompany.list(0, 0, (employeeSrcRekapitulasiAbs.getCompanyId()!=null && employeeSrcRekapitulasiAbs.getCompanyId().length()>0 ?PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]+" IN("+employeeSrcRekapitulasiAbs.getCompanyId()+")":""), PstCompany.fieldNames[PstCompany.FLD_COMPANY])  :new Vector(); 
        hashDivision = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0? PstDivision.hashListDivision(0, 0, (employeeSrcRekapitulasiAbs.getDivisionId()!=null && employeeSrcRekapitulasiAbs.getDivisionId().length()>0?PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+" IN("+employeeSrcRekapitulasiAbs.getDivisionId()+")":"")):new Hashtable();   
        hashDepartment = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstDepartment.hashListDepartment(0, 0, (employeeSrcRekapitulasiAbs.getDepartmentId()!=null && employeeSrcRekapitulasiAbs.getDepartmentId().length()>0?PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+" IN("+employeeSrcRekapitulasiAbs.getDepartmentId()+")":"")):new Hashtable(); 
        hashSection = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstSection.hashListSection(0, 0, (sectionId!=null && sectionId.length()>0?PstSection.fieldNames[PstSection.FLD_SECTION_ID]+" IN("+sectionId+")":"")):new Hashtable();     
        hashEmployee = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstEmployee.hashListEmployee(0, 0, listBenefitDeduction.whereClauseEmpId(employeeSrcRekapitulasiAbs.getEmpId())):new Hashtable(); 
            
        
        listAttdAbsensi =  employeeSrcRekapitulasiAbs.getEmpId()!=null &&  employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstEmpSchedule.getListAttendaceRekap(attdConfig,leaveConfig,dtfrom, dtend, employeeSrcRekapitulasiAbs.getEmpId(), vctSchIDOff, hashSchOff, iPropInsentifLevel, holidaysTable, hashPositionLevel, payrollCalculatorConfig,hashPeriod):new Hashtable();  
        vReason = PstReason.listReason(0, 0, PstReason.fieldNames[PstReason.FLD_REASON_TIME] + "=" + PstReason.REASON_TIME_YES, PstReason.fieldNames[PstReason.FLD_REASON_CODE]+ " ASC "); 
            
        
        session.putValue("benefitdeduction", listBenefitDeduction);
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html> 

    <head> 

        <title>HARISMA - Rekapitulasi Absensi</title>
        <%@ include file = "../../main/konfigurasi_jquery.jsp" %>    
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <script type="text/javascript" src="../../javascripts/jquery.min.js"></script>
        <script type="text/javascript" src="../../javascripts/jquery-ui.min.js"></script>
        <script type="text/javascript" src="../../javascripts/gridviewScroll.min.js"></script>
        <link href="../../stylesheets/GridviewScroll.css" rel="stylesheet" />
        <script language="JavaScript">

            function fnTrapKD(){ 
                if (event.keyCode == 13) {
                    cmdView();
                }
            }

            function cmdUpdateDiv(){
                document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frpresence.action="src_list_benefit_deduction_2.jsp";
                document.frpresence.submit();
            }
            function cmdUpdateDep(){
                document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frpresence.action="src_list_benefit_deduction_2.jsp";
                document.frpresence.submit();
            }
            function cmdUpdatePos(){
                document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frpresence.action="src_list_benefit_deduction_2.jsp";
                document.frpresence.submit();
            }
            function cmdExport(){ 
                document.frpresence.action="<%=printroot%>.report.attendance.AttendanceSummaryXls"; 
                document.frpresence.target = "SummaryAttendances";
                document.frpresence.submit();
                document.frpresence.target = "";
                
            }
            
             function cmdSearch(nilai){
                document.frpresence.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frpresence.source_type.value=nilai;
                document.frpresence.action="src_list_benefit_deduction_2.jsp";
                document.frpresence.submit();
                
            }
            
            function cmdExportExcel(source_type){
                 
                var linkPage = "<%=approot%>/report/payroll/export_excel/export_excel_list_benefit_deduction.jsp?source_type="+source_type;    
                var newWin = window.open(linkPage,"attdReportDaily","height=700,width=990,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes"); 			
                 newWin.focus();
 
                //document.frpresence.action="rekapitulasi_absensi.jsp"; 
                //document.frpresence.target = "SummaryAttendance";
                //document.frpresence.submit();
                //document.frpresence.target = "";
            }
       
        </script>

        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 


    </head>

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
        <%//if(isMSIE){%>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>

            <%//}else{%>

            <%//}%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">    
                    <%@ include file = "../../main/header.jsp" %>
                </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
                    <%@ include file = "../../main/mnmain.jsp" %>
                </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="10" valign="middle"> 
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                            <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
                            <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
                            <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <%}%>
            <tr> 
                <td width="88%" valign="top" align="left"> 
                    <table width="100%" border="0" cellspacing="3" cellpadding="2">
                        <tr> 
                            <td width="100%"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr> 
                                        <td height="20"> <font color="#FF6600" face="Arial"><strong>  Report &gt; Rekapitulasi Absensi  </strong></font> </td>
                                    </tr>
                                    <tr> 
                                        <td> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0"  >
                                                            <tr> 
                                                                <td valign="top"> 
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                                        <tr> 
                                                                            <td valign="top"> 
                                                                                <form name="frpresence" method="post" action="">
                                                                                    <input type="hidden" name="hidden_empschedule_id" value="<%=iCommand%>">

                                                                                    <input type="hidden" name="command" value="">
                                                                                    <input type="hidden" name="source" value="">
                                                                                    <input type="hidden" name="source_type" value="">
                                                                                    
                                                                                    <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">

                                                                                        <tr>
                                                                                            <td width="6%" nowrap="nowrap"> <div align="left">Payrol Num </div></td>
                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                <input type="text" size="40" placeholder="Type Employee Number.." name="emp_number"  value="<%= listBenefitDeduction.getPayrollNumber()%>" title="You can Input Payroll Number more than one, ex-sample : 1111,2222" class="elemenForm" onKeyDown="javascript:fnTrapKD()"> </td>

                                                                                            <td width="5%" nowrap="nowrap"> Full Name </td>
                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                <input type="text" size="50" placeholder="Type Full Name.." name="full_name"  value="<%= listBenefitDeduction.getFullName()%>" title="You can Input Full Name more than one, ex-sample : saya,kamu" class="elemenForm" onKeyDown="javascript:fnTrapKD()"> </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td width="6%" nowrap="nowrap"> <div align="left">Company </div></td>
                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                <%
                                                                                                    Vector comp_value = new Vector(1, 1);
                                                                                                    Vector comp_key = new Vector(1, 1);
                                                                                                    String whereComp = "";
                                                                                                    /*if(srcOvertime!=null && srcOvertime.getCompanyId()!=0){
                                                                                                     whereComp = PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] +"="+srcOvertime.getCompanyId();
                                                                                                     }*/
                                                                                                    Vector div_value = new Vector(1, 1);
                                                                                                    Vector div_key = new Vector(1, 1);

                                                                                                    Vector dept_value = new Vector(1, 1);
                                                                                                    Vector dept_key = new Vector(1, 1);
                                                                                                    if (processDependOnUserDept) {
                                                                                                        if (emplx.getOID() > 0) {
                                                                                                            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                                                                                //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                                comp_value.add("0");
                                                                                                                comp_key.add("select ...");

                                                                                                                div_value.add("0");
                                                                                                                div_key.add("select ...");

                                                                                                                dept_value.add("0");
                                                                                                                dept_key.add("select ...");
                                                                                                            } else {
                                                                                                                Position position = null;
                                                                                                                try {
                                                                                                                    position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                } catch (Exception exc) {
                                                                                                                }
                                                                                                                if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                                                                                    String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + emplx.getDivisionId() + "";
                                                                                                                    //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereDiv, true);
                                                                                                                    // comp_value.add("0");
                                                                                                                    // comp_key.add("select ...");

                                                                                                                    //div_value.add("0");
                                                                                                                    //div_key.add("select ...");

                                                                                                                    dept_value.add("0");
                                                                                                                    dept_key.add("select ...");

                                                                                                                    whereComp = whereComp != null && whereComp.length() > 0 ? whereComp + " AND (" + whereDiv + ")" : whereDiv;

                                                                                                                } else {

                                                                                                                    String whereClsDep = "(" + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + departmentOid
                                                                                                                            + ") OR (" + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + " = " + departmentOid + ") ";
                                                                                                                    try {
                                                                                                                        String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                                                                                                                        Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                                                                                                                        int grpIdx = -1;
                                                                                                                        int maxGrp = depGroup == null ? 0 : depGroup.size();
                                                                                                                        int countIdx = 0;
                                                                                                                        int MAX_LOOP = 10;
                                                                                                                        int curr_loop = 0;
                                                                                                                        do { // find group department belonging to curretn user base in departmentOid
                                                                                                                            curr_loop++;
                                                                                                                            String[] grp = (String[]) depGroup.get(countIdx);
                                                                                                                            for (int g = 0; g < grp.length; g++) {
                                                                                                                                String comp = grp[g];
                                                                                                                                if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                                                                                                                    grpIdx = countIdx;   // A ha .. found here 
                                                                                                                                }
                                                                                                                            }
                                                                                                                            countIdx++;
                                                                                                                        } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit

                                                                                                                        // compose where clause
                                                                                                                        if (grpIdx >= 0) {
                                                                                                                            String[] grp = (String[]) depGroup.get(grpIdx);
                                                                                                                            for (int g = 0; g < grp.length; g++) {
                                                                                                                                String comp = grp[g];
                                                                                                                                whereClsDep = whereClsDep + " OR (DEPARTMENT_ID = " + comp + ")";
                                                                                                                            }
                                                                                                                        }
                                                                                                                    } catch (Exception exc) {
                                                                                                                        System.out.println(" Parsing Join Dept" + exc);

                                                                                                                    }
                                                                                                                    //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, false);

                                                                                                                    whereComp = whereComp != null && whereComp.length() > 0 ? whereComp + " AND (" + whereClsDep + ")" : whereClsDep;

                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    } else {
                                                                                                        comp_value.add("0");
                                                                                                        comp_key.add("select ...");

                                                                                                        div_value.add("0");
                                                                                                        div_key.add("select ...");

                                                                                                        dept_value.add("0");
                                                                                                        dept_key.add("select ...");
                                                                                                    }
                                                                                                    Vector listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
                                                                                                    String prevCompany = "";
                                                                                                    String prevDivision = "";



                                                                                                    long prevCompanyTmp = 0;
                                                                                                    for (int i = 0; i < listCostDept.size(); i++) {
                                                                                                        Department dept = (Department) listCostDept.get(i);
                                                                                                        if (prevCompany.equals(dept.getCompany())) {
                                                                                                            if (prevDivision.equals(dept.getDivision())) {
                                                                                                                //if(srcOvertime!=null && srcOvertime.getCompanyId()!=0){
                                                                                                                dept_key.add(dept.getDepartment());
                                                                                                                dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                //}
                                                                                                            } else {
                                                                                                                div_key.add(dept.getDivision());
                                                                                                                div_value.add("" + dept.getDivisionId());
                                                                                                                if (dept_key != null && dept_key.size() == 0) {
                                                                                                                    dept_key.add(dept.getDepartment());
                                                                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                }
                                                                                                                prevDivision = dept.getDivision();
                                                                                                            }
                                                                                                        } else {
                                                                                                            String chkAdaDiv = "";
                                                                                                            if (div_key != null && div_key.size() > 0) {
                                                                                                                chkAdaDiv = (String) div_key.get(0);
                                                                                                            }
                                                                                                            if ((div_key != null && div_key.size() == 0) || (chkAdaDiv.equalsIgnoreCase("select ..."))) {
                                                                                                                if (prevCompanyTmp != dept.getCompanyId()) {
                                                                                                                    comp_key.add(dept.getCompany());
                                                                                                                    comp_value.add("" + dept.getCompanyId());
                                                                                                                    prevCompanyTmp = dept.getCompanyId();
                                                                                                                }
                                                                                                                //untuk karyawan admin yg hanya bisa akses departement tertentu (ketika di awal)
                                                                                                                ////update
                                                                                                                if (processDependOnUserDept) {
                                                                                                                    if (emplx.getOID() > 0) {
                                                                                                                        if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                                                                                            if (listBenefitDeduction.getCompanyId() != 0) { 
                                                                                                                                div_key.add(dept.getDivision());
                                                                                                                                div_value.add("" + dept.getDivisionId());

                                                                                                                                dept_key.add(dept.getDepartment());
                                                                                                                                dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                prevCompany = dept.getCompany();
                                                                                                                                prevDivision = dept.getDivision();
                                                                                                                            }
                                                                                                                        } else {
                                                                                                                            Position position = null;
                                                                                                                            try {
                                                                                                                                position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                            } catch (Exception exc) {
                                                                                                                            }
                                                                                                                            if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                                                                                                if (listBenefitDeduction.getCompanyId() != 0) {
                                                                                                                                    div_key.add(dept.getDivision());
                                                                                                                                    div_value.add("" + dept.getDivisionId());

                                                                                                                                    dept_key.add(dept.getDepartment());
                                                                                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                    prevCompany = dept.getCompany();
                                                                                                                                    prevDivision = dept.getDivision();
                                                                                                                                } //update by satrya 2013-09-19
                                                                                                                                else if ((div_key != null && div_key.size() == 0) || (chkAdaDiv.equalsIgnoreCase("select ..."))) {
                                                                                                                                    div_key.add(dept.getDivision());
                                                                                                                                    div_value.add("" + dept.getDivisionId());

                                                                                                                                    //update by satrya 2013-09-19
                                                                                                                                    dept_key.add(dept.getDepartment());
                                                                                                                                    dept_value.add(String.valueOf(dept.getOID()));

                                                                                                                                    prevCompany = dept.getCompany();
                                                                                                                                    prevDivision = dept.getDivision();
                                                                                                                                }

                                                                                                                            } else {

                                                                                                                                div_key.add(dept.getDivision());
                                                                                                                                div_value.add("" + dept.getDivisionId());

                                                                                                                                dept_key.add(dept.getDepartment());
                                                                                                                                dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                                prevCompany = dept.getCompany();
                                                                                                                                prevDivision = dept.getDivision();
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                } else {
                                                                                                                    if (listBenefitDeduction.getCompanyId() != 0) {
                                                                                                                        div_key.add(dept.getDivision());
                                                                                                                        div_value.add("" + dept.getDivisionId());

                                                                                                                        dept_key.add(dept.getDepartment());
                                                                                                                        dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                        prevCompany = dept.getCompany();
                                                                                                                        prevDivision = dept.getDivision();
                                                                                                                    }
                                                                                                                }

                                                                                                            } else {
                                                                                                                if (prevCompanyTmp != dept.getCompanyId()) {
                                                                                                                    comp_key.add(dept.getCompany());
                                                                                                                    comp_value.add("" + dept.getCompanyId());
                                                                                                                    prevCompanyTmp = dept.getCompanyId();
                                                                                                                }

                                                                                                            }

                                                                                                        }
                                                                                                    }
                                                                                                %>
                                                                                                <%= ControlCombo.draw("company_id", "formElemen", null, "" + listBenefitDeduction.getCompanyId(), comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%>
                                                                                            </td>

                                                                                            <td width="5%" nowrap="nowrap"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                <%

                                                                                                    //update by satrya 2013-08-13
                                                                                                    //jika user memilih select kembali
                                                                                                    if (listBenefitDeduction.getCompanyId() == 0) {
                                                                                                        listBenefitDeduction.setDivisionId(0);
                                                                                                    }

                                                                                                    if (listBenefitDeduction.getCompanyId() != 0) {
                                                                                                        whereComp = "(" + (whereComp != null && whereComp.length() == 0 ? "1=1" : whereComp) + ") AND " + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + "=" + listBenefitDeduction.getCompanyId(); 
                                                                                                        listCostDept = PstDepartment.listWithCompanyDiv(0, 0, whereComp);
                                                                                                        prevCompany = "";
                                                                                                        prevDivision = "";

                                                                                                        div_value = new Vector(1, 1);
                                                                                                        div_key = new Vector(1, 1);

                                                                                                        dept_value = new Vector(1, 1);
                                                                                                        dept_key = new Vector(1, 1);

                                                                                                        prevCompanyTmp = 0;
                                                                                                        long tmpFirstDiv = 0;

                                                                                                        if (processDependOnUserDept) {
                                                                                                            if (emplx.getOID() > 0) {
                                                                                                                if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                                                                                    //keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                                    comp_value.add("0");
                                                                                                                    comp_key.add("select ...");

                                                                                                                    div_value.add("0");
                                                                                                                    div_key.add("select ...");

                                                                                                                    dept_value.add("0");
                                                                                                                    dept_key.add("select ...");
                                                                                                                } else {
                                                                                                                    Position position = null;
                                                                                                                    try {
                                                                                                                        position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                    } catch (Exception exc) {
                                                                                                                    }
                                                                                                                    if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                                                                                        //div_value.add("0");
                                                                                                                        //div_key.add("select ...");

                                                                                                                        dept_value.add("0");
                                                                                                                        dept_key.add("select ...");

                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        } else {
                                                                                                            comp_value.add("0");
                                                                                                            comp_key.add("select ...");

                                                                                                            div_value.add("0");
                                                                                                            div_key.add("select ...");

                                                                                                            dept_value.add("0");
                                                                                                            dept_key.add("select ...");
                                                                                                        }
                                                                                                        long prevDivTmp = 0;
                                                                                                        for (int i = 0; i < listCostDept.size(); i++) {
                                                                                                            Department dept = (Department) listCostDept.get(i);
                                                                                                            if (prevCompany.equals(dept.getCompany())) {
                                                                                                                if (prevDivision.equals(dept.getDivision())) {
                                                                                                                    //update
                                                                                                                    if (listBenefitDeduction.getDivisionId() != 0) {
                                                                                                                        dept_key.add(dept.getDepartment());
                                                                                                                        dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                    }
                                                                                                                    //lama
                        /*
                                                                                                                     dept_key.add(dept.getDepartment());
                                                                                                                     dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                     */

                                                                                                                } else {
                                                                                                                    div_key.add(dept.getDivision());
                                                                                                                    div_value.add("" + dept.getDivisionId());
                                                                                                                    if (dept_key != null && dept_key.size() == 0) {
                                                                                                                        dept_key.add(dept.getDepartment());
                                                                                                                        dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                    }
                                                                                                                    prevDivision = dept.getDivision();
                                                                                                                }
                                                                                                            } else {
                                                                                                                String chkAdaDiv = "";
                                                                                                                if (div_key != null && div_key.size() > 0) {
                                                                                                                    chkAdaDiv = (String) div_key.get(0);
                                                                                                                }
                                                                                                                if ((div_key != null && div_key.size() == 0) || (chkAdaDiv.equalsIgnoreCase("select ..."))) {
                                                                                                                    //comp_key.add(dept.getCompany());
                                                                                                                    //comp_value.add(""+dept.getCompanyId());



                                                                                                                    if (prevDivTmp != dept.getDivisionId()) {
                                                                                                                        div_key.add(dept.getDivision());
                                                                                                                        div_value.add("" + dept.getDivisionId());
                                                                                                                        prevDivTmp = dept.getDivisionId();
                                                                                                                    }

                                                                                                                    tmpFirstDiv = dept.getDivisionId();

                                                                                                                    // dept_key.add(dept.getDepartment());
                                                                                                                    //   dept_value.add(String.valueOf(dept.getOID()));           

                                                                                                                    prevCompany = dept.getCompany();
                                                                                                                    prevDivision = dept.getDivision();
                                                                                                                }
                                                                                                                /*else{
                                                                                                                 if(prevCompanyTmp!=dept.getCompanyId()){
                                                                                                                 comp_key.add(dept.getCompany());
                                                                                                                 comp_value.add(""+dept.getCompanyId());
                                                                                                                 prevCompanyTmp=dept.getCompanyId();
                                                                                                                 }
              
                                                                                                                 }*/
                                                                                                                String chkAdaDpt = "";
                                                                                                                if (whereComp != null && whereComp.length() > 0) {
                                                                                                                    chkAdaDpt = "(" + (whereComp != null && whereComp.length() == 0 ? "1=1" : whereComp) + ") AND d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + "=" +  listBenefitDeduction.getDivisionId(); 
                                                                                                                }
                                                                                                                Vector listCheckAdaDept = PstDepartment.listWithCompanyDiv(0, 0, chkAdaDpt);
                                                                                                                if ((listCheckAdaDept == null || listCheckAdaDept.size() == 0)) {

                                                                                                                    if (processDependOnUserDept) {
                                                                                                                        if (emplx.getOID() > 0) {
                                                                                                                            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                                                                                            } else {
                                                                                                                                Position position = null;
                                                                                                                                try {
                                                                                                                                    position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                                } catch (Exception exc) {
                                                                                                                                }

                                                                                                                                listBenefitDeduction.setDivisionId(tmpFirstDiv);  

                                                                                                                            }
                                                                                                                        }
                                                                                                                    } else {
                                                                                                                        listBenefitDeduction.setDivisionId(tmpFirstDiv); 

                                                                                                                    }

                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                %>
                                                                                                <%= ControlCombo.draw("division_id", "formElemen", null, "" + listBenefitDeduction.getDivisionId(), div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"")%> 
                                                                                            </td>
                                                                                        </tr>


                                                                                        <tr> 
                                                                                            <td width="6%" align="right" nowrap> 
                                                                                                <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>                                                                                            </td>
                                                                                            <td width="30%" nowrap="nowrap"> : 

                                                                                                <%
                                                                                                    dept_value = new Vector(1, 1);
                                                                                                    dept_key = new Vector(1, 1);
                                                                                                    Vector listDept = new Vector(); //PstDepartment.list(0, 0, strWhere, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);


                                                                                                    if (processDependOnUserDept) {
                                                                                                        if (emplx.getOID() > 0) {

                                                                                                            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                                                                                String strWhere = PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + listBenefitDeduction.getDivisionId();
                                                                                                                dept_value.add("0");
                                                                                                                dept_key.add("select ...");
                                                                                                                listDept = PstDepartment.list(0, 0, strWhere, "DEPARTMENT");

                                                                                                            } else {
                                                                                                                Position position = new Position();
                                                                                                                try {
                                                                                                                    position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                } catch (Exception exc) {
                                                                                                                }

                                                                                                                String whereClsDep = "(((hr_department.DEPARTMENT_ID = " + departmentOid + ") "
                                                                                                                        + "AND hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + listBenefitDeduction.getDivisionId() + ") OR "
                                                                                                                        + "(hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + "=" + departmentOid + "))";

                                                                                                                if (position.getOID() != 0 && position.getDisabedAppDivisionScope() == 0) {
                                                                                                                    whereClsDep = " ( hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + listBenefitDeduction.getDivisionId() + ") ";
                                                                                                                }

                                                                                                                Vector SectionList = new Vector();
                                                                                                                try {
                                                                                                                    String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                                                                                                                    Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                                                                                                                    String joinDeptSection = PstSystemProperty.getValueByName("JOIN_DEPARTMENT_SECTION");
                                                                                                                    Vector depSecGroup = com.dimata.util.StringParser.parseGroup(joinDeptSection);

                                                                                                                    int grpIdx = -1;
                                                                                                                    int maxGrp = depGroup == null ? 0 : depGroup.size();

                                                                                                                    int grpSecIdx = -1;
                                                                                                                    int maxGrpSec = depSecGroup == null ? 0 : depSecGroup.size();

                                                                                                                    int countIdx = 0;
                                                                                                                    int MAX_LOOP = 10;
                                                                                                                    int curr_loop = 0;

                                                                                                                    int countIdxSec = 0;
                                                                                                                    int MAX_LOOPSec = 10;
                                                                                                                    int curr_loopSec = 0;

                                                                                                                    do { // find group department belonging to curretn user base in departmentOid
                                                                                                                        curr_loop++;
                                                                                                                        String[] grp = (String[]) depGroup.get(countIdx);
                                                                                                                        for (int g = 0; g < grp.length; g++) {
                                                                                                                            String comp = grp[g];
                                                                                                                            if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                                                                                                                grpIdx = countIdx;   // A ha .. found here                                       
                                                                                                                            }
                                                                                                                        }
                                                                                                                        countIdx++;
                                                                                                                    } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit                            

                                                                                                                    Vector idxSecGroup = new Vector();

                                                                                                                    for (int x = 0; x < maxGrpSec; x++) {

                                                                                                                        String[] grp = (String[]) depSecGroup.get(x);
                                                                                                                        for (int j = 0; j < 1; j++) {

                                                                                                                            String comp = grp[j];
                                                                                                                            if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                                                                                                                Counter counter = new Counter();
                                                                                                                                counter.setCounter(x);
                                                                                                                                idxSecGroup.add(counter);
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }

                                                                                                                    for (int s = 0; s < idxSecGroup.size(); s++) {

                                                                                                                        Counter counter = (Counter) idxSecGroup.get(s);

                                                                                                                        String[] grp = (String[]) depSecGroup.get(counter.getCounter());

                                                                                                                        Section sec = new Section();
                                                                                                                        sec.setDepartmentId(Long.parseLong(grp[0]));
                                                                                                                        sec.setOID(Long.parseLong(grp[2]));
                                                                                                                        SectionList.add(sec);

                                                                                                                    }

                                                                                                                    // compose where clause
                                                                                                                    if (grpIdx >= 0) {
                                                                                                                        String[] grp = (String[]) depGroup.get(grpIdx);
                                                                                                                        for (int g = 0; g < grp.length; g++) {
                                                                                                                            String comp = grp[g];
                                                                                                                            whereClsDep = whereClsDep + " OR (j.DEPARTMENT_ID = " + comp + ")";
                                                                                                                        }
                                                                                                                    }
                                                                                                                    whereClsDep = " (" + whereClsDep + ") AND hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + listBenefitDeduction.getDivisionId();
                                                                                                                } catch (Exception exc) {
                                                                                                                    System.out.println(" Parsing Join Dept" + exc);
                                                                                                                }

                                                                                                                //dept_value.add("0");
                                                                                                                //dept_key.add("select ...");
                                                                                                                listDept = PstDepartment.list(0, 0, whereClsDep, "");

                                                                                                                for (int idx = 0; idx < SectionList.size(); idx++) {

                                                                                                                    Section sect = (Section) SectionList.get(idx);

                                                                                                                    long sectionOid = 0;

                                                                                                                    for (int z = 0; z < listDept.size(); z++) {

                                                                                                                        Department dep = new Department();

                                                                                                                        dep = (Department) listDept.get(z);

                                                                                                                        if (sect.getDepartmentId() == dep.getOID()) {

                                                                                                                            sectionOid = sect.getOID();

                                                                                                                        }
                                                                                                                    }

                                                                                                                    if (sectionOid != 0) {

                                                                                                                        Section lstSection = new Section();
                                                                                                                        Department lstDepartment = new Department();

                                                                                                                        try {
                                                                                                                            lstSection = PstSection.fetchExc(sectionOid);
                                                                                                                        } catch (Exception e) {
                                                                                                                            System.out.println("Exception " + e.toString());
                                                                                                                        }

                                                                                                                        try {
                                                                                                                            lstDepartment = PstDepartment.fetchExc(lstSection.getDepartmentId());
                                                                                                                        } catch (Exception e) {
                                                                                                                            System.out.println("Exception " + e.toString());
                                                                                                                        }

                                                                                                                        listDept.add(lstDepartment);

                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        } else {
                                                                                                            dept_value.add("0");
                                                                                                            dept_key.add("select ...");
                                                                                                            listDept = PstDepartment.list(0, 0, (PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + listBenefitDeduction.getDivisionId()), "DEPARTMENT");
                                                                                                        }
                                                                                                    } else {
                                                                                                        dept_value.add("0");
                                                                                                        dept_key.add("select ...");
                                                                                                        listDept = PstDepartment.list(0, 0, (PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + listBenefitDeduction.getDivisionId()), "DEPARTMENT");
                                                                                                    }

                                                                                                    for (int i = 0; i < listDept.size(); i++) {
                                                                                                        Department dept = (Department) listDept.get(i);
                                                                                                        dept_key.add(dept.getDepartment());
                                                                                                        dept_value.add(String.valueOf(dept.getOID()));
                                                                                                    }


                                                                                                    //update by satrya 2013-08-13
                                                                                                    //jika user memilih select kembali
                                                                                                    if (listBenefitDeduction.getDeptId() == 0) {
                                                                                                        listBenefitDeduction.setSectionId(0);
                                                                                                    }
                                                                                                %> <%= ControlCombo.draw("department", "formElemen", null, "" + listBenefitDeduction.getDeptId(), dept_value, dept_key, "onChange=\"javascript:cmdUpdatePos()\"")%>
                                                                                            </td>
                                                                                            <td width="5%" align="left" nowrap valign="top"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                <%

                                                                                                    Vector sec_value = new Vector(1, 1);
                                                                                                    Vector sec_key = new Vector(1, 1);
                                                                                                    sec_value.add("0");
                                                                                                    sec_key.add("select ...");

                                                                                                    //String sWhereClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + sSelectedDepartment;                                                       
                                                                                                    //Vector listSec = PstSection.list(0, 0, sWhereClause, " SECTION ");
                                                                                                    String secWhere = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + listBenefitDeduction.getDeptId();
                                                                                                    Vector listSec = PstSection.list(0, 0, secWhere, " SECTION ");
                                                                                                    for (int i = 0; i < listSec.size(); i++) {
                                                                                                        Section sec = (Section) listSec.get(i);
                                                                                                        sec_key.add(sec.getSection());
                                                                                                        sec_value.add(String.valueOf(sec.getOID()));
                                                                                                    }
                                                                                                %>
                                                                                                <%=ControlCombo.draw("section", null, "" + listBenefitDeduction.getSectionId(), sec_value, sec_key)%> 
                                                                                            </td>

                                                                                        </tr>

                                                                                        <tr> 
                                                                                            <td width="6%" align="right" nowrap> <div align=left>Date</div>                                                                                             </td>
                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                    <%
                                                                                                Vector periodValue = new Vector(1, 1);
                                                                                                Vector periodKey = new Vector(1, 1);
                                                                                                // salkey.add(" ALL DEPARTMET");
                                                                                                //deptValue.add("0");
                                                                                                Vector listPeriod = PstPayPeriod.list(0, 0, "", "START_DATE DESC");
                                                                                                //   Vector listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");
                                                                                                for (int r = 0; r < listPeriod.size(); r++) {
                                                                                                    PayPeriod payPeriod = (PayPeriod) listPeriod.get(r);
                                                                                                    //  Period period = (Period) listPeriod.get(r);
                                                                                                    periodValue.add("" + payPeriod.getOID());
                                                                                                    periodKey.add(payPeriod.getPeriod());
                                                                                                }
                                                                                                %> <%=ControlCombo.draw("period_id", null, "" + listBenefitDeduction.getPeriodeId(), periodValue, periodKey, "")%>
                                                                                           
                                                                                            </td>
                                                                                                <td width="5%" align="right" nowrap><div align="left">Resigned Status </div></td>
                                                                                            <td>:
                                                                                                <input type="radio" name="statusResign" value="0" checked>
                                                                                                No
                                                                                                <input type="radio" name="statusResign" value="1">
                                                                                                Yes
                                                                                                <input type="radio" name="statusResign" value="2">
                                                                                                All </td> 
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td width="5%" align="right" nowrap><div align="left">Emp. Category </div></td>
                                                                                            <td width="59%" nowrap="nowrap">
                                                                                                <table>
                                                                                                    <tr>
                                                                                                        <td width="83%" colspan="4">
                                                                                                            <table>
                                                                                                                <%

                                                                                                                    int numCol = 4;
                                                                                                                    boolean createTr = false;
                                                                                                                    int numOfColCreated = 0;
                                                                                                                    Hashtable hashGetListEmpSel = new Hashtable();
                                                                                                                    if (stsEmpCategorySel != null && stsEmpCategorySel.length() > 0) {
                                                                                                                        stsEmpCategorySel = stsEmpCategorySel.substring(0, stsEmpCategorySel.length() - 1);
                                                                                                                        String whereClause = PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " IN (" + stsEmpCategorySel + ")";
                                                                                                                        hashGetListEmpSel = PstEmpCategory.getHashListEmpSchedule(0, 0, whereClause, "");
                                                                                                                    }
                                                                                                                    Vector vObjek = PstEmpCategory.listAll();
                                                                                                                    //String checked="unchecked"; 
                                                                                                                    long oidEmpCat = 0;
                                                                                                                    for (int tc = 0; (vObjek != null) && (tc < vObjek.size()); tc++) {
                                                                                                                        EmpCategory empCategory = (EmpCategory) vObjek.get(tc);
                                                                                                                        oidEmpCat = 0;
                                                                                                                        if (tc % numCol == 0) {
                                                                                                                            out.println("<tr><td nowrap>");
                                                                                                                            createTr = true;
                                                                                                                            numOfColCreated = 1;
                                                                                                                        } else {
                                                                                                                            out.println("<td nowrap>");
                                                                                                                            numOfColCreated = 1 + numOfColCreated;
                                                                                                                        }
                                                                                                                        if (empCategory != null) {

                                                                                                                            if (hashGetListEmpSel != null && hashGetListEmpSel.size() > 0 && hashGetListEmpSel.get(empCategory.getOID()) != null) {
                                                                                                                                EmpCategory empCategoryHas = (EmpCategory) hashGetListEmpSel.get(empCategory.getOID());
                                                                                                                                oidEmpCat = empCategoryHas.getOID();

                                                                                                                            }

                                                                                                                            if (oidEmpCat != 0) {
                                                                                                                %>
                                                                                                                <input type="checkbox" name="<%="EMP_CAT_" + tc%>"  checked="checked" value="<%=empCategory.getOID()%>"  />
                                                                                                                <%=empCategory.getEmpCategory()%> &nbsp;&nbsp;
                                                                                                                <%} else {%>
                                                                                                                <input type="checkbox" name="<%="EMP_CAT_" + tc%>"  value="<%=empCategory.getOID()%>"  />
                                                                                                                <%=empCategory.getEmpCategory()%> &nbsp;&nbsp;
                                                                                                                <%}%>
                                                                                                                <%
                                                                                                                            if (numOfColCreated == numCol || (tc + 2) > vObjek.size()) {
                                                                                                                                out.println("</td></tr>");
                                                                                                                            } else {
                                                                                                                                out.println("</td>");
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }

                                                                                                                %>
                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table></td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td height="13" width="0%">&nbsp;</td>
                                                                                            <td width="39%">
                                                                                                <table>
                                                                                                    <tr>
                                                                                                       <td width="39%"><a href="javascript:cmdSearch(0)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a>
                                                                                                <img src="<%=approot%>/images/spacer.gif" width="6" height="1">
                                                                                                <a href="javascript:cmdSearch(0)">Search Benefit</a></td>  
                                                                                                        <td width="39%"><a href="javascript:cmdSearch(1)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a>
                                                                                                <img src="<%=approot%>/images/spacer.gif" width="6" height="1">
                                                                                                <a href="javascript:cmdSearch(1)">Search Deduction</a></td>  
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                            
                                                                                        </tr>
                                                                                        <%if (iCommand == Command.LIST) {%> 
                                                                                        <tr>
                                                                                            <td colspan="7">
                                                                                                <%
                                                                                                if(listBenefitDeduction.getSourceTYpe()==0){%>
                                                                                                <%=drawList(out, listBenefitDeduction,listCompany,hashDivision,hashDepartment,hashSection,hashEmployee,hashEmployeeSection,listAttdAbsensi,vReason,Listdatanewh, vPayComponent)%>
                                                                                              
                                                                                                 <%
                                                                                                
                                                                                                }else{%>
                                                                                                 <%=drawList(out, listBenefitDeduction,listCompany,hashDivision,hashDepartment,hashSection,hashEmployee,hashEmployeeSection,listAttdAbsensi,vReason,Listdatanewh,vPayComponent)%>
                                                                                                 <%}
                                                                                                %>
                                                                                                </td>  
                                                                                        </tr>
                                                                                     
                                                                                        <tr> 
                                                                                            <td width="6%" nowrap> 
                                                                                                <div align="left"></div></td>
                                                                                            <%if(listBenefitDeduction.getSourceTYpe()==0){%>
                                                                                                <td width="30%"> 
                                                                                                <table border="0" cellspacing="0" cellpadding="0" width="137">
                                                                                                    <tr> 
                                                                                                        <td width="16"><a href="javascript:cmdExportExcel(<%=listBenefitDeduction.getSourceTYpe()%>)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/excel.png',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/excel.png" width="24" height="24" alt="Export Excel"></a></td>
                                                                                                        <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                                                                        <td width="94" class="command" nowrap><a href="javascript:cmdExportExcel(<%=listBenefitDeduction.getSourceTYpe()%>)">Export Excel Rekapitulasi</a></td>
                                                                                                    </tr>
                                                                                                </table></td>
                                                                                            <%}else{%>
                                                                                                <td width="30%"> 
                                                                                                <table border="0" cellspacing="0" cellpadding="0" width="137">
                                                                                                    <tr> 
                                                                                                        <td width="16"><a href="javascript:cmdExportExcel(<%=listBenefitDeduction.getSourceTYpe()%>)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/excel.png',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/excel.png" width="24" height="24" alt="Export Excel"></a></td>
                                                                                                        <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                                                                        <td width="94" class="command" nowrap><a href="javascript:cmdExportExcel(<%=listBenefitDeduction.getSourceTYpe()%>)">Export Excel Rincian</a></td>
                                                                                                    </tr>
                                                                                                </table></td>
                                                                                            <%}%>
                                                                                        </tr>
                                                                                        <%}%>
                                                                                    </table>

                                                                                </form>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr> 
                                                    <td>&nbsp; </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom">

                    <%@include file="../../footer.jsp" %>
                </td>

            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>>
                    <%@ include file = "../../main/footer.jsp" %>
                </td>
            </tr>
            <%}%>
        </table>
        <script type="text/javascript">
            $(document).ready(function () {
                gridviewScroll();
            });
            <%
                int freesize = 3;

            %>
                function gridviewScroll() {
                    gridView1 = $('#GridView1').gridviewScroll({
                        width: 1310,
                        height: 500,
                        railcolor: "##33AAFF",
                        barcolor: "#CDCDCD",
                        barhovercolor: "#606060",
                        bgcolor: "##33AAFF",
                        freezesize: <%=freesize%>,
                        arrowsize: 30,
                        varrowtopimg: "<%=approot%>/images/arrowvt.png",
                        varrowbottomimg: "<%=approot%>/images/arrowvb.png",
                        harrowleftimg: "<%=approot%>/images/arrowhl.png",
                        harrowrightimg: "<%=approot%>/images/arrowhr.png",
                        headerrowcount: 2,
                        railsize: 16,
                        barsize: 15
                    });
                }
        </script>

    </body>

</html>