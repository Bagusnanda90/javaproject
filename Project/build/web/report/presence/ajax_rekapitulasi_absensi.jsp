<%-- 
    Document   : ajax_rekapitulasi_absensi
    Created on : 07-Aug-2017, 08:59:00
    Author     : Gunadi
--%>
<%@page import="com.dimata.system.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.harisma.session.leave.LeaveConfigDinamis"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayEmpLevel"%>
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
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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

<%!    public String drawList(JspWriter outJsp,RekapitulasiAbsensi rekapitulasiAbsensi,Vector listCompany,Hashtable hashDivision,Hashtable hashDepartment,Hashtable hashSection,Hashtable hashEmployee,Hashtable hashEmployeeSection,Hashtable listAttdAbsensi,Vector vReason, int viewschedule) {
        String result = "";
        if(rekapitulasiAbsensi==null){
            return result; 
        }
         String ClientName = "";
            try {
                ClientName = String.valueOf(PstSystemProperty.getValueByName("CLIENT_NAME"));//menambahkan system properties
            } catch (Exception e) {
                System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
            }
         boolean withH_EO = false ;
         if ((ClientName.equals("PEPITO")) || (ClientName.equals("MINIMART"))){
             withH_EO = true;
         }
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("table table-bordered");
        //ctrlist.setTitleStyle("listgentitle");
        //ctrlist.setCellStyle("listgensell");
        //ctrlist.setCellStyles("listgensellstyles");
        ctrlist.setRowSelectedStyles("rowselectedstyles");
        ctrlist.setHeaderStyle("listheaderJs");
        //ctrlist.setStyleSelectableTableValue("id=\"selectable\""); 
        ctrlist.setMaxFreezingTable(2);
        //mengambil nama dari kode komponent
        ctrlist.addHeader("Head Count", "5%", "2", "0");
        ctrlist.addHeader("No", "5%", "2", "0");
        ctrlist.addHeader("Nama", "5%", "2", "0");
        
        if ((ClientName.equals("PEPITO"))){
        if (withH_EO){
            ctrlist.addHeader("Absensi", "20%", "0", "" + (vReason!=null && vReason.size()>0?12+vReason.size():12));
        } else {
            ctrlist.addHeader("Absensi", "20%", "0", "" + (vReason!=null && vReason.size()>0?11+vReason.size():11));
        }
               } else {
            if (withH_EO){
                ctrlist.addHeader("Absensi", "20%", "0", "" + (vReason!=null && vReason.size()>0?10+vReason.size():10));
            } else {
                ctrlist.addHeader("Absensi", "20%", "0", "" + (vReason!=null && vReason.size()>0?9+vReason.size():9));
            }
               }
        
        ctrlist.addHeader("HK", "5%", "0", "0");
        if (withH_EO){
           ctrlist.addHeader("H", "5%", "0", "0"); 
           ctrlist.addHeader("E", "5%", "0", "0"); 
        } else {
           ctrlist.addHeader("H", "5%", "0", "0");
        }
        
        
        ctrlist.addHeader("R", "5%", "0", "0");
        ctrlist.addHeader("A", "5%", "0", "0");
        ctrlist.addHeader("D", "5%", "0", "0");
        ctrlist.addHeader("I", "5%", "0", "0");
        ctrlist.addHeader("C", "5%", "0", "0");
        ctrlist.addHeader("S", "5%", "0", "0");
        if(vReason!=null && vReason.size()>0){
            for(int d=0;d<vReason.size();d++){
                Reason reason = (Reason)vReason.get(d);
                ctrlist.addHeader(""+reason.getKodeReason(), "5%", "0", "0"); 
            }
        }
        if ((ClientName.equals("PEPITO"))){
              ctrlist.addHeader("SO 4", "5%", "0", "0");
              ctrlist.addHeader("SO 8", "5%", "0", "0");
        }
        ctrlist.addHeader("Total", "5%", "0", "0");

        /*ctrlist.addHeader("Working Schedule <br> Absensi1", "20%", "0", "" + 15);
         for(int b=0;b<15;b++){
         ctrlist.addHeader("Total"+b, "5%", "0", "0");
         }*/
        //priska 20150619
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
        int totalpercompany = 0;
        try {
            if (rekapitulasiAbsensi.getDtFrom() != null && rekapitulasiAbsensi.getDtTo() != null && listCompany!=null && listCompany.size()>0) {
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
                        if ((ClientName.equals("PEPITO"))){
                        rowx.add("");
                        rowx.add("");
                         }
                        
                        if (withH_EO){
                           rowx.add("");
                           rowx.add("");
                        } else {
                           rowx.add("");
                        }
                        rowx.add("");
                        rowx.add(""); //AL
                        rowx.add(""); //DP
                        rowx.add(""); //UL
                        rowx.add(""); //SL
                        rowx.add(""); //S
                        
                        if(vReason!=null && vReason.size()>0){
                            for(int d=0;d<vReason.size();d++){
                                //Reason reason = (Reason)vReason.get(d);
                                rowx.add(""); 
                            }
                        }
                        for (int idx = 0; idx < itDate; idx++) {
                            rowx.add("");
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
                                    rowx.add("");if ((ClientName.equals("PEPITO"))){
                                    rowx.add("");
                                    rowx.add("");
                                     }
                                    
                                    if (withH_EO){
                                       rowx.add("");
                                       rowx.add("");
                                    } else {
                                       rowx.add("");
                                    }
                                    rowx.add("");
                                    rowx.add(""); //AL
                                    rowx.add(""); //DP  
                                    rowx.add(""); //UL
                                    rowx.add(""); //SL  
                                    rowx.add(""); //S
                        
                                    if(vReason!=null && vReason.size()>0){
                                        for(int d=0;d<vReason.size();d++){
                                            //Reason reason = (Reason)vReason.get(d);
                                            rowx.add(""); 
                                        }
                                    }
                                    for (int idx = 0; idx < itDate; idx++) {
                                        rowx.add("");
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
                                                if ((ClientName.equals("PEPITO"))){
                                                rowx.add("");
                                                rowx.add("");
                                                 }
                                                
                                                if (withH_EO){
                                                   rowx.add("");
                                                   rowx.add("");
                                                } else {
                                                   rowx.add("");
                                                }
                                                rowx.add("");
                                                rowx.add("");//al
                                                rowx.add("");//DP
                                                rowx.add(""); //UL
                                                rowx.add(""); //SL
                                                rowx.add(""); //S
                                                if(vReason!=null && vReason.size()>0){
                                                    for(int d=0;d<vReason.size();d++){
                                                        //Reason reason = (Reason)vReason.get(d);
                                                        rowx.add(""); 
                                                    }
                                                }
                                                for (int idx = 0; idx < itDate; idx++) {
                                                    rowx.add("");
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
                                                                 
                                                                 rekapitulasiAbsensiGrandTotal.setHK(rekapitulasiAbsensiGrandTotal.getHK()+rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly());
                                                                 //rekapitulasiAbsensiGrandTotal.setHK(rekapitulasiAbsensiGrandTotal.getHK()+rekapitulasiPresenceDanSchedule.getTotalWorkingDays());
                                                                 if (withH_EO){
                                                                   rekapitulasiAbsensiGrandTotal.setH(rekapitulasiAbsensiGrandTotal.getH()+rekapitulasiPresenceDanSchedule.getDayOffScheduleH());
                                                                   rekapitulasiAbsensiGrandTotal.setEO(rekapitulasiAbsensiGrandTotal.getEO()+rekapitulasiPresenceDanSchedule.getDayOffScheduleEO());
                                                                } else {
                                                                   rekapitulasiAbsensiGrandTotal.setH(rekapitulasiAbsensiGrandTotal.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                }
                                                                 
                                                                 rekapitulasiAbsensiGrandTotal.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotal.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                 rekapitulasiAbsensiGrandTotal.setAnnualLeave(rekapitulasiAbsensiGrandTotal.getAnnualLeave()+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                 rekapitulasiAbsensiGrandTotal.setdPayment(rekapitulasiAbsensiGrandTotal.getdPayment()+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                 rekapitulasiAbsensiGrandTotal.setUnpaidLeave(rekapitulasiAbsensiGrandTotal.getUnpaidLeave() +rekapitulasiPresenceDanSchedule.getUnpaidLeave());
                                                                 rekapitulasiAbsensiGrandTotal.setSpecialLeave(rekapitulasiAbsensiGrandTotal.getSpecialLeave()+rekapitulasiPresenceDanSchedule.getSpecialLeave());
                                                                 rekapitulasiAbsensiGrandTotal.setSo4j(rekapitulasiAbsensiGrandTotal.getSo4j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                 rekapitulasiAbsensiGrandTotal.setSo8j(rekapitulasiAbsensiGrandTotal.getSo8j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                 rekapitulasiAbsensiGrandTotal.setS(rekapitulasiAbsensiGrandTotal.getS()+rekapitulasiPresenceDanSchedule.getTotalS());
                                                                 
                                                                 rekapitulasiAbsensiGrandTotalPerCompany.setHK(rekapitulasiAbsensiGrandTotalPerCompany.getHK()+rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly());
                                                                 //rekapitulasiAbsensiGrandTotalPerCompany.setHK(rekapitulasiAbsensiGrandTotalPerCompany.getHK()+rekapitulasiPresenceDanSchedule.getTotalWorkingDays());
                                                                 
                                                                 if (withH_EO){
                                                                    rekapitulasiAbsensiGrandTotalPerCompany.setH(rekapitulasiAbsensiGrandTotalPerCompany.getH()+rekapitulasiPresenceDanSchedule.getDayOffScheduleH());
                                                                    rekapitulasiAbsensiGrandTotalPerCompany.setEO(rekapitulasiAbsensiGrandTotalPerCompany.getEO()+rekapitulasiPresenceDanSchedule.getDayOffScheduleEO());
                                                                 } else {
                                                                    rekapitulasiAbsensiGrandTotalPerCompany.setH(rekapitulasiAbsensiGrandTotalPerCompany.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                 }
                                                                 rekapitulasiAbsensiGrandTotalPerCompany.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotalPerCompany.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                 rekapitulasiAbsensiGrandTotalPerCompany.setAnnualLeave(rekapitulasiAbsensiGrandTotalPerCompany.getAnnualLeave()+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                 rekapitulasiAbsensiGrandTotalPerCompany.setdPayment(rekapitulasiAbsensiGrandTotalPerCompany.getdPayment()+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                 
                                                                 rekapitulasiAbsensiGrandTotalPerCompany.setSpecialLeave(rekapitulasiAbsensiGrandTotalPerCompany.getSpecialLeave()+rekapitulasiPresenceDanSchedule.getSpecialLeave());
                                                                 rekapitulasiAbsensiGrandTotalPerCompany.setUnpaidLeave(rekapitulasiAbsensiGrandTotalPerCompany.getUnpaidLeave()+rekapitulasiPresenceDanSchedule.getUnpaidLeave());
                                                                 
                                                                 rekapitulasiAbsensiGrandTotalPerCompany.setSo4j(rekapitulasiAbsensiGrandTotalPerCompany.getSo4j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                 rekapitulasiAbsensiGrandTotalPerCompany.setSo8j(rekapitulasiAbsensiGrandTotalPerCompany.getSo8j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                 rekapitulasiAbsensiGrandTotalPerCompany.setS(rekapitulasiAbsensiGrandTotalPerCompany.getS()+rekapitulasiPresenceDanSchedule.getTotalS());
                                                                 
                                                                }
                                                               TotalEmployeePerDeptnSection = TotalEmployeePerDeptnSection+1;
                                                               TotalEmployeePerCompany = TotalEmployeePerCompany + 1; 
                                                                rowx = new Vector();
                                                                rowx.add("" + (noEMployeeNoSection++));
                                                                rowx.add(""+employeeMinimalis.getEmployeeNum());
                                                                rowx.add(""+employeeMinimalis.getFullName());  
                                                                rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly()); 
                                                                //rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalWorkingDays()); 
                                                                if (withH_EO){
                                                                   rowx.add(""+rekapitulasiPresenceDanSchedule.getDayOffScheduleH());
                                                                   rowx.add(""+rekapitulasiPresenceDanSchedule.getDayOffScheduleEO());
                                                                } else {
                                                                   rowx.add(""+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                }
                                                                rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                rowx.add(""+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                rowx.add(""+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                rowx.add(""+rekapitulasiPresenceDanSchedule.getUnpaidLeave());
                                                                rowx.add(""+rekapitulasiPresenceDanSchedule.getSpecialLeave());
                                                                rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalS());
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
                                                                
                                                                if ((ClientName.equals("PEPITO"))){
                                                                                rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                                rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                 }
                                                            
                                                                if (withH_EO){
                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalSemuanyaByPepito());
                                                                    totalpercompany = totalpercompany + rekapitulasiPresenceDanSchedule.getTotalSemuanyaByPepito();
                                                                } else {
                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalSemuanya());
                                                                    totalpercompany = totalpercompany + rekapitulasiPresenceDanSchedule.getTotalSemuanya() ;
                                                                }
                                                                
                                                                for (int idx = 0; idx < itDate; idx++) {
                                                                    //rowx.add("" + idx);
                                                                    Date selectedDate = new Date(rekapitulasiAbsensi.getDtFrom().getYear(), rekapitulasiAbsensi.getDtFrom().getMonth(), (rekapitulasiAbsensi.getDtFrom().getDate() + idx));
                                                                    
                                                                   long OIDschedule = PstScheduleSymbol.getScheduleId(rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate));
                                                                                         if ((OIDschedule == dayOFF) || (OIDschedule == extraDayOFF)){
                                                                        rowx.add("<b style=\"color:red\">" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate) +"</b>");
                                                                    } else {
                                                                      rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate));
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
                                                                
                                                                if (withH_EO){
                                                                   rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getH()+"</b>");
                                                                   rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getEO()+"</b>");
                                                                } else {
                                                                   rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getH()+"</b>");
                                                                }
                                                                
                                                                
                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getLateLebihLimaMenit()+"</b>");
                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getAnnualLeave()+"</b>");
                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getdPayment()+"</b>");
                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getUnpaidLeave()+"</b>");
                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getSpecialLeave()+"</b>");
                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getS()+"</b>");
                                                                
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
                                                                  if ((ClientName.equals("PEPITO"))){
                                                                                
                                                                rowx.add("<b>"+(rekapitulasiAbsensiGrandTotal.getSo4j())+"</b>");
                                                                rowx.add("<b>"+(rekapitulasiAbsensiGrandTotal.getSo8j())+"</b>");
                                                                 }
                                                                
                                                                if (withH_EO){
                                                                    rowx.add("<b>"+(rekapitulasiAbsensiGrandTotal.getTotalSemuanyaByPepito())+"</b>");
                                                                } else {
                                                                    rowx.add("<b>"+(rekapitulasiAbsensiGrandTotal.getTotalSemuanya())+"</b>");
                                                                }
                                                                
                                                                
                                                                for (int idx = 0; idx < itDate; idx++) {
                                                                    rowx.add("");
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
                                                                    if (withH_EO){
                                                                       rowx.add("");
                                                                       rowx.add("");
                                                                    } else {
                                                                       rowx.add("");
                                                                    }
                                                                    rowx.add("");
                                                                    rowx.add(""); //AL
                                                                    rowx.add(""); //DP
                                                                    rowx.add(""); //UL
                                                                    rowx.add(""); //SL
                                                                    rowx.add(""); //S
                                                                    if(vReason!=null && vReason.size()>0){
                                                                        for(int d=0;d<vReason.size();d++){
                                                                            //Reason reason = (Reason)vReason.get(d);
                                                                            rowx.add(""); 
                                                                        }
                                                                    }
                                                                    rowx.add("");  
                                                                    if ((ClientName.equals("PEPITO"))){
                                                                        rowx.add(""); 
                                                                        rowx.add("");  
                                                                         }
                                                                    for (int idx = 0; idx < itDate; idx++) {
                                                                        rowx.add("");
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
                                                                            int axx = 0;
                                                                            Vector cloneListEMp = sessEmployee.getListEmployee()!=null && sessEmployee.getListEmployee().size()>0?new Vector(sessEmployee.getListEmployee()):new Vector();
                                                                            for (int idxEmpSec = 0; idxEmpSec < cloneListEMp.size(); idxEmpSec++) {
                                                                                axx += 1;
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
                                                                                     //rekapitulasiAbsensiGrandTotal.setHK(rekapitulasiAbsensiGrandTotal.getHK()+rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly());
                                                                                     
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
                                                                                     rekapitulasiPresenceDanSchedule.setTotalWorkingDays((rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly()));
                                                                                     //rekapitulasiPresenceDanSchedule.setTotalWorkingDays((rekapitulasiPresenceDanSchedule.getTotalWorkingDays()));
                                                                                     rekapitulasiPresenceDanSchedule.setRealTotalReason((hk));   
                                                                                     rekapitulasiAbsensiGrandTotal.setHK(rekapitulasiAbsensiGrandTotal.getHK()+rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly());
                                                                                     
                                                                                     //rekapitulasiAbsensiGrandTotal.setHK(rekapitulasiAbsensiGrandTotal.getHK()+rekapitulasiPresenceDanSchedule.getTotalWorkingDays());
                                                                                     
                                                                                     if (withH_EO){
                                                                                        rekapitulasiAbsensiGrandTotal.setH(rekapitulasiAbsensiGrandTotal.getH()+rekapitulasiPresenceDanSchedule.getDayOffScheduleH());
                                                                                        rekapitulasiAbsensiGrandTotal.setEO(rekapitulasiAbsensiGrandTotal.getEO()+rekapitulasiPresenceDanSchedule.getDayOffScheduleEO());
                                                                                     } else {
                                                                                        rekapitulasiAbsensiGrandTotal.setH(rekapitulasiAbsensiGrandTotal.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                                     }
                                                                                     
                                                                                     
                                                                                     rekapitulasiAbsensiGrandTotal.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotal.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                                     rekapitulasiAbsensiGrandTotal.setAnnualLeave(rekapitulasiAbsensiGrandTotal.getAnnualLeave()+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                                     rekapitulasiAbsensiGrandTotal.setdPayment(rekapitulasiAbsensiGrandTotal.getdPayment()+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                                     
                                                                                     rekapitulasiAbsensiGrandTotal.setUnpaidLeave(rekapitulasiAbsensiGrandTotal.getUnpaidLeave()+rekapitulasiPresenceDanSchedule.getUnpaidLeave());
                                                                                     rekapitulasiAbsensiGrandTotal.setSpecialLeave(rekapitulasiAbsensiGrandTotal.getSpecialLeave()+rekapitulasiPresenceDanSchedule.getSpecialLeave());
                                                                                     
                                                                                     rekapitulasiAbsensiGrandTotal.setSo4j(rekapitulasiAbsensiGrandTotal.getSo4j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                                     rekapitulasiAbsensiGrandTotal.setSo8j(rekapitulasiAbsensiGrandTotal.getSo8j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                                     rekapitulasiAbsensiGrandTotal.setS(rekapitulasiAbsensiGrandTotal.getS()+rekapitulasiPresenceDanSchedule.getTotalS());
                                                                 
                                                                                     rekapitulasiAbsensiGrandTotalSec.setHK(rekapitulasiAbsensiGrandTotalSec.getHK()+rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly());
                                                                                     //rekapitulasiAbsensiGrandTotalSec.setHK(rekapitulasiAbsensiGrandTotalSec.getHK()+rekapitulasiPresenceDanSchedule.getTotalWorkingDays());
                                                                                     if (withH_EO){
                                                                                        rekapitulasiAbsensiGrandTotalSec.setH(rekapitulasiAbsensiGrandTotalSec.getH()+rekapitulasiPresenceDanSchedule.getDayOffScheduleH());
                                                                                        rekapitulasiAbsensiGrandTotalSec.setEO(rekapitulasiAbsensiGrandTotalSec.getEO()+rekapitulasiPresenceDanSchedule.getDayOffScheduleEO());
                                                                                     } else {
                                                                                        rekapitulasiAbsensiGrandTotalSec.setH(rekapitulasiAbsensiGrandTotalSec.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                                     }
                                                                                     rekapitulasiAbsensiGrandTotalSec.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotalSec.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                                     rekapitulasiAbsensiGrandTotalSec.setAnnualLeave(rekapitulasiAbsensiGrandTotalSec.getAnnualLeave()+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                                     rekapitulasiAbsensiGrandTotalSec.setdPayment(rekapitulasiAbsensiGrandTotalSec.getdPayment()+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                                     rekapitulasiAbsensiGrandTotalSec.setUnpaidLeave(rekapitulasiAbsensiGrandTotalSec.getUnpaidLeave()+rekapitulasiPresenceDanSchedule.getUnpaidLeave());
                                                                                     rekapitulasiAbsensiGrandTotalSec.setSpecialLeave(rekapitulasiAbsensiGrandTotalSec.getSpecialLeave()+rekapitulasiPresenceDanSchedule.getSpecialLeave());
                                                                                     
                                                                                     rekapitulasiAbsensiGrandTotalSec.setSo4j(rekapitulasiAbsensiGrandTotalSec.getSo4j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                                     rekapitulasiAbsensiGrandTotalSec.setSo8j(rekapitulasiAbsensiGrandTotalSec.getSo8j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                                     rekapitulasiAbsensiGrandTotalSec.setS(rekapitulasiAbsensiGrandTotalSec.getS()+rekapitulasiPresenceDanSchedule.getTotalS());
                                                                 
                                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setHK(rekapitulasiAbsensiGrandTotalPerCompany.getHK()+rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly());
                                                                                     //rekapitulasiAbsensiGrandTotalPerCompany.setHK(rekapitulasiAbsensiGrandTotalPerCompany.getHK()+rekapitulasiPresenceDanSchedule.getTotalWorkingDays());
                                                                                     
                                                                                      if (withH_EO){
                                                                                        rekapitulasiAbsensiGrandTotalPerCompany.setH(rekapitulasiAbsensiGrandTotalPerCompany.getH()+rekapitulasiPresenceDanSchedule.getDayOffScheduleH());
                                                                                        rekapitulasiAbsensiGrandTotalPerCompany.setEO(rekapitulasiAbsensiGrandTotalPerCompany.getEO()+rekapitulasiPresenceDanSchedule.getDayOffScheduleEO());
                                                                                     } else {
                                                                                        rekapitulasiAbsensiGrandTotalPerCompany.setH(rekapitulasiAbsensiGrandTotalPerCompany.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                                     }
                                                                                     //rekapitulasiAbsensiGrandTotalPerCompany.setH(rekapitulasiAbsensiGrandTotalPerCompany.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotalPerCompany.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setAnnualLeave(rekapitulasiAbsensiGrandTotalPerCompany.getAnnualLeave()+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setdPayment(rekapitulasiAbsensiGrandTotalPerCompany.getdPayment()+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setSpecialLeave(rekapitulasiAbsensiGrandTotalPerCompany.getSpecialLeave()+rekapitulasiPresenceDanSchedule.getSpecialLeave());
                                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setUnpaidLeave(rekapitulasiAbsensiGrandTotalPerCompany.getUnpaidLeave()+rekapitulasiPresenceDanSchedule.getUnpaidLeave());
                                                                                     
                                                                                     
                                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setSo4j(rekapitulasiAbsensiGrandTotalPerCompany.getSo4j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setSo8j(rekapitulasiAbsensiGrandTotalPerCompany.getSo8j()+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                                     rekapitulasiAbsensiGrandTotalPerCompany.setS(rekapitulasiAbsensiGrandTotalPerCompany.getS()+rekapitulasiPresenceDanSchedule.getTotalS());
                                                                 
                                                                                }
                                                                                    loopBarieEMpSec = loopBarieEMpSec + 1;
                                                                                    
                                                                                    TotalEmployeePerDeptnSection = TotalEmployeePerDeptnSection+1;
                                                                                    TotalEmployeePerCompany = TotalEmployeePerCompany + 1;
                                                                                    cloneListEMp.remove(idxEmpSec);
                                                                                    idxEmpSec = idxEmpSec -1;
                                                                                    rowx = new Vector();
																					//baru sampai sini
                                                                                    rowx.add("" + (noEMployeeNoSection++));
                                                                                    rowx.add(""+employeeMinimalis.getEmployeeNum());
                                                                                    rowx.add(""+employeeMinimalis.getFullName());
                                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalpresencelateearlyonly());   
                                                                                    //rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalWorkingDays()); 
                                                                                    if (withH_EO){
                                                                                        rowx.add(""+rekapitulasiPresenceDanSchedule.getDayOffScheduleH());
                                                                                        rowx.add(""+rekapitulasiPresenceDanSchedule.getDayOffScheduleEO());
                                                                                    } else {
                                                                                        rowx.add(""+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                                    }
                                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getAnnualLeave());
                                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getdPayment());
                                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getUnpaidLeave());
                                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getSpecialLeave());
                                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalS());
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
                                                                                    if ((ClientName.equals("PEPITO"))){
                                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname4jam());
                                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalScheduleOpname8jam());
                                                                                    }
                                                                                    
                                                                                    if (withH_EO){
                                                                                        rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalSemuanyaByPepito());
                                                                                        totalpercompany = totalpercompany + rekapitulasiPresenceDanSchedule.getTotalSemuanyaByPepito() ;
                                                                                    }else{
                                                                                        rowx.add(""+rekapitulasiPresenceDanSchedule.getTotalSemuanya());
                                                                                        totalpercompany = totalpercompany + rekapitulasiPresenceDanSchedule.getTotalSemuanya() ;
                                                                                    }
                                                                                    
                                                                                    
                                                                                    // menampilkan working schedule
                                                                                    for (int idx = 0; idx < itDate; idx++) {
                                                                                        //rowx.add("" + idx);
                                                                                        Date selectedDate = new Date(rekapitulasiAbsensi.getDtFrom().getYear(), rekapitulasiAbsensi.getDtFrom().getMonth(), (rekapitulasiAbsensi.getDtFrom().getDate() + idx));
                                                                                            
                                                                                        if (viewschedule == 2){
                                                                                            
                                                                                             long OIDschedule = PstScheduleSymbol.getScheduleId(rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate));
                                                                                          if ((OIDschedule == dayOFF) || (OIDschedule == extraDayOFF)){
                                                                                            rowx.add("<b style=\"color:red\">" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate) +"</b>");
                                                                                          } else {
                                                                                          rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate));
                                                                                        }
                                                                                        } else if (viewschedule == 1) {
                                                                                         long OIDschedule = PstScheduleSymbol.getScheduleId(rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate));
                                                                                         if ((OIDschedule == dayOFF) || (OIDschedule == extraDayOFF)){
                                                                                            rowx.add("<b style=\"color:red\">" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataStatus(selectedDate) +"</b>");
                                                                                         } else {
                                                                                          rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataStatus(selectedDate));
                                                                                        }
                                                                                        } else {
                                                                                            long OIDschedule = PstScheduleSymbol.getScheduleId(rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate));
                                                                                          if ((OIDschedule == dayOFF) || (OIDschedule == extraDayOFF)){
                                                                                            rowx.add("<b style=\"color:red\">" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate) +"</b>");
                                                                                          } else {
                                                                                                
                                                                                                
                                                                                          if ((ClientName.equals("MINIMART")) || (ClientName.equals("PEPITO"))){
                                                                                            rowx.add("" +rekapitulasiPresenceDanSchedule.getDataReasonOrScheduleStatusForMinimart(selectedDate , employeeMinimalis.getDivisionId()));
                                                                                          } else {
                                                                                            rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate));
                                                                                          }    
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
                                                                            if (withH_EO){
                                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotalSec.getH()+"</b>");
                                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotalSec.getEO()+"</b>");
                                                                            } else {
                                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotalSec.getH()+"</b>");
                                                                            }
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotalSec.getLateLebihLimaMenit()+"</b>");
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotalSec.getAnnualLeave()+"</b>");
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotalSec.getdPayment()+"</b>");
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotalSec.getUnpaidLeave()+"</b>");
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotalSec.getSpecialLeave()+"</b>");
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotalSec.getS()+"</b>");
                                                                            
                                                                            int totReason = 0;
                                                                            if(vReason!=null && vReason.size()>0){
                                                                                for(int d=0;d<vReason.size();d++){
                                                                                    Reason reason = (Reason)vReason.get(d);
                                                                                    totReason=rekapitulasiAbsensiGrandTotalSec.getGrandTotalReason(reason.getNo());
                                                                                    rowx.add(""+totReason);
                                                                                }
                                                                            }
                                                                            
                                                                            rekapitulasiAbsensiGrandTotalSec.setTotalAlasan(totReason);
                                                                            if ((ClientName.equals("PEPITO"))){
                                                                            rowx.add("<b>"+(rekapitulasiAbsensiGrandTotalSec.getSo4j())+"</b>");
                                                                            rowx.add("<b>"+(rekapitulasiAbsensiGrandTotalSec.getSo8j())+"</b>");
                                                                            }
                                                                            if (withH_EO){
                                                                                rowx.add("<b>"+(rekapitulasiAbsensiGrandTotalSec.getTotalSemuanyaByPepito())+"</b>");
                                                                            } else {
                                                                                rowx.add("<b>"+(rekapitulasiAbsensiGrandTotalSec.getTotalSemuanya())+"</b>");
                                                                            }
                                                                            for (int idx = 0; idx < itDate; idx++) {
                                                                                rowx.add("");
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
                                                                            
                                                                            if (withH_EO){
                                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getH()+"</b>");
                                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getEO()+"</b>");
                                                                            } else {
                                                                                rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getH()+"</b>");
                                                                            }
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getLateLebihLimaMenit()+"</b>");
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getAnnualLeave()+"</b>");
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getdPayment()+"</b>");
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getUnpaidLeave()+"</b>");
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getSpecialLeave()+"</b>");
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getS()+"</b>");
                                                                            
                                                                            if(vReason!=null && vReason.size()>0){
                                                                                for(int d=0;d<vReason.size();d++){
                                                                                    Reason reason = (Reason)vReason.get(d);
                                                                                    int totReason=rekapitulasiAbsensiGrandTotal.getGrandTotalReason(reason.getNo());
                                                                                    rowx.add(""+totReason);
                                                                                }
                                                                            }
                                                                            if ((ClientName.equals("PEPITO"))){
                                                                            rowx.add("<b>"+(rekapitulasiAbsensiGrandTotal.getSo4j())+"</b>");
                                                                            rowx.add("<b>"+(rekapitulasiAbsensiGrandTotal.getSo8j())+"</b>");
                                                                            }
                                                                            if (withH_EO){
                                                                                rowx.add("<b>"+(rekapitulasiAbsensiGrandTotal.getTotalSemuanyaByPepito())+"</b>");
                                                                            } else {
                                                                                rowx.add("<b>"+(rekapitulasiAbsensiGrandTotal.getTotalSemuanya())+"</b>");
                                                                            }
                                                                            
                                                                            
                                                                            for (int idx = 0; idx < itDate; idx++) {
                                                                                rowx.add("");
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
                        if (withH_EO){
                            
                        rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getH()+"</b>");
                        rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getEO()+"</b>");
                        } else {
                        rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getH()+"</b>");
                        }
                        rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getLateLebihLimaMenit()+"</b>"); 
                        rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getAnnualLeave()+"</b>");
                        rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getdPayment()+"</b>");
                        rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getUnpaidLeave()+"</b>");
                        rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getSpecialLeave()+"</b>");
                        rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getS()+"</b>");
                        if(vReason!=null && vReason.size()>0){
                            for(int d=0;d<vReason.size();d++){
                                Reason reason = (Reason)vReason.get(d);
                                int totReason=rekapitulasiAbsensiGrandTotalPerCompany.getGrandTotalReason(reason.getNo());
                                rowx.add(""+totReason); 
                            }
                        }
                        if ((ClientName.equals("PEPITO"))){
                        rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getSo4j()+"</b>");
                        rowx.add("<b>"+rekapitulasiAbsensiGrandTotalPerCompany.getSo8j()+"</b>"); 
                        }
                        rowx.add("<b>"+totalpercompany+"</b>"); 
                   
                        
                        for (int idx = 0; idx < itDate; idx++) {
                            rowx.add("");
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

<%!    public String drawListRincian(JspWriter outJsp,RekapitulasiAbsensi rekapitulasiAbsensi,Vector listCompany,Hashtable hashDivision,Hashtable hashDepartment,Hashtable hashSection,Hashtable hashEmployee,Hashtable hashEmployeeSection,Hashtable listAttdAbsensi,HashTblPayDay hashTblPayDay,long oidEmployeeDw, int viewschedule, Hashtable listPayDayfromSalLevel,int OnlyDw) {
        String result = "";
        if(rekapitulasiAbsensi==null){
            return result; 
        }
        
        long dayOFF =0;
            try{
                dayOFF = Long.parseLong(PstSystemProperty.getValueByName("OID_DAY_OFF"));
             }catch(Exception ex){
                 System.out.println("OID_DAY_OFF NOT Be SET"+ex);
                 dayOFF = 0;
             }
          //    OID_DAILYWORKER
           long Dw = 0;
            try{
                String sDw = PstSystemProperty.getValueByName("OID_DAILYWORKER"); 
                Dw = Integer.parseInt(sDw);
             }catch(Exception ex){
                 System.out.println("VALUE_DAILYWORKER NOT Be SET"+ex);
             } 
         long extraDayOFF =0;
            try{
              extraDayOFF = Long.parseLong(PstSystemProperty.getValueByName("OID_EXTRA_OFF"));
             }catch(Exception ex){
                 System.out.println("OID_DAY_OFF NOT Be SET"+ex);
                 extraDayOFF = 0;
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
        ctrlist.addHeader("HK ON PRESENCE", "5%", "0", "0");
        
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
                        
                        rowx.add("");
                        rowx.add("");
                        rowx.add("");
                        rowx.add("");
                        
                        for (int idx = 0; idx < itDate; idx++) {
                            rowx.add("");
                        }
                     
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
                                   
                                    rowx.add("");
                                    rowx.add("");
                                    rowx.add("");
                                    rowx.add("");
                                   
                                    for (int idx = 0; idx < itDate; idx++) {
                                        rowx.add("");
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
                                               
                                                rowx.add("");
                                                rowx.add("");
                                                rowx.add("");
                                                rowx.add("");
                                                
                                                for (int idx = 0; idx < itDate; idx++) {
                                                    rowx.add("");
                                                }
                                                ctrlist.drawListRowJsVer2(outJsp, 0, rowx, idxDept);
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
                                                                        rekapitulasiAbsensiGrandTotal.setHK(rekapitulasiAbsensiGrandTotal.getHK()+rekapitulasiPresenceDanSchedule.getPresenceOnTime());
                                                                        
                                                                        //rekapitulasiAbsensiGrandTotal.setH(rekapitulasiAbsensiGrandTotal.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                        //rekapitulasiAbsensiGrandTotal.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotal.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());

                                                                        rekapitulasiAbsensiGrandTotalPerCompany.setHK(rekapitulasiAbsensiGrandTotalPerCompany.getHK()+rekapitulasiPresenceDanSchedule.getPresenceOnTime());
                                                                        
                                                                        //rekapitulasiAbsensiGrandTotalPerCompany.setH(rekapitulasiAbsensiGrandTotalPerCompany.getH()+rekapitulasiPresenceDanSchedule.getDayOffSchedule());
                                                                        //rekapitulasiAbsensiGrandTotalPerCompany.setLateLebihLimaMenit(rekapitulasiAbsensiGrandTotalPerCompany.getLateLebihLimaMenit()+rekapitulasiPresenceDanSchedule.getTotalLateLebihLimaMenit());
                                                                }
                                                                rowx = new Vector();
                                                                rowx.add("" + (noEMployeeNoSection++));
                                                                rowx.add(""+employeeMinimalis.getEmployeeNum());
                                                                rowx.add(""+employeeMinimalis.getFullName());  
                                                                rowx.add(""+rekapitulasiPresenceDanSchedule.getPresenceOnTime()); 
                                                                //rowx.add("");
                                                                if (OnlyDw != 0 && OnlyDw == 1){
                                                                     double harianDw = 0;
                                                                    if (listPayDayfromSalLevel.get(employeeMinimalis.getOID()) != null){
                                                                        harianDw = (Double) (Double.valueOf(listPayDayfromSalLevel.get(employeeMinimalis.getOID()).toString()));
                                                                        
                                                                    }
                                                                rowx.add(""+Formater.formatNumberMataUang(harianDw, "Rp"));
                                                                rowx.add(""+Formater.formatNumberMataUang((rekapitulasiPresenceDanSchedule.getPresenceOnTime()*harianDw), "Rp"));
                                  
                                                                } else {
                                                                    rowx.add(""+Formater.formatNumberMataUang(hashTblPayDay.getPayDay(employeeMinimalis.getEmpCategory(), oidEmployeeDw, employeeMinimalis.getPositionId()), "Rp") ); 
                                                                    rowx.add(""+Formater.formatNumberMataUang((rekapitulasiPresenceDanSchedule.getPresenceOnTime()*hashTblPayDay.getPayDay(employeeMinimalis.getEmpCategory(), oidEmployeeDw, employeeMinimalis.getPositionId())), "Rp"));
                                                                
                                                                }
                                                                for (int idx = 0; idx < itDate; idx++) {
                                                                    //rowx.add("" + idx);
                                                                    Date selectedDate = new Date(rekapitulasiAbsensi.getDtFrom().getYear(), rekapitulasiAbsensi.getDtFrom().getMonth(), (rekapitulasiAbsensi.getDtFrom().getDate() + idx));
                                                                    
                                                                    //rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate));
                                                                    rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate));
                                                                }
                                                                if(hashTblPayDay!=null){
                                                                    rekapitulasiAbsensiGrandTotal.setPayDay(rekapitulasiAbsensiGrandTotal.getPayDay()+hashTblPayDay.getPayDay(employeeMinimalis.getEmpCategory(), oidEmployeeDw, employeeMinimalis.getPositionId()));
                                                                        rekapitulasiAbsensiGrandTotal.setTotalPayDay(rekapitulasiAbsensiGrandTotal.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getPresenceOnTime()*hashTblPayDay.getPayDay(employeeMinimalis.getEmpCategory(), oidEmployeeDw, employeeMinimalis.getPositionId())));
                                                                        rekapitulasiAbsensiGrandTotalPerCompany.setPayDay(rekapitulasiAbsensiGrandTotalPerCompany.getPayDay()+hashTblPayDay.getPayDay(employeeMinimalis.getEmpCategory(), oidEmployeeDw, employeeMinimalis.getPositionId()));
                                                                        rekapitulasiAbsensiGrandTotalPerCompany.setTotalPayDay(rekapitulasiAbsensiGrandTotalPerCompany.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getPresenceOnTime()*hashTblPayDay.getPayDay(employeeMinimalis.getEmpCategory(), oidEmployeeDw, employeeMinimalis.getPositionId())));
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
                                                                rowx.add("<b>"+Formater.formatNumberMataUang(rekapitulasiAbsensiGrandTotal.getPayDay(), "Rp")+"</b>");
                                                                rowx.add("<b>"+Formater.formatNumberMataUang((rekapitulasiAbsensiGrandTotal.getTotalPayDay()), "Rp") +"</b>");
                                                                for (int idx = 0; idx < itDate; idx++) {
                                                                    rowx.add("");
                                                                }
                                                                ctrlist.drawListRowJsVer2(outJsp, 0, rowx, 0); 
                                                        
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
                                                                    ctrlist.drawListRowJsVer2(outJsp, 0, rowx, loopBarisSection);
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
                                                                                            
                                                                                            
                                                                                            rekapitulasiAbsensiGrandTotal.setHK(rekapitulasiAbsensiGrandTotal.getHK()+rekapitulasiPresenceDanSchedule.getPresenceOnTime());
                                                                                            
                                                                                    
                                                                                            rekapitulasiAbsensiGrandTotalSec.setHK(rekapitulasiAbsensiGrandTotalSec.getHK()+rekapitulasiPresenceDanSchedule.getPresenceOnTime());
                                                                                            
                                                                                            
                                                                                            rekapitulasiAbsensiGrandTotalPerCompany.setHK(rekapitulasiAbsensiGrandTotalPerCompany.getHK()+rekapitulasiPresenceDanSchedule.getPresenceOnTime());
                                                                                            
                                                                                    
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
                                                                                    
                                                                                    //priska menambahkan untuk kategori dw
                                                                                    rowx.add(""+rekapitulasiPresenceDanSchedule.getPresenceOnTime()); 
                                                                                   // rowx.add(""+listPayDayfromSalLevel.get(employeeMinimalis.getOID()));
                                                                                   
                                                                                    if (OnlyDw != 0 && OnlyDw == 1){
                                                                                        double harianDw = 0;
                                                                                        if (listPayDayfromSalLevel.get(employeeMinimalis.getOID()) != null){
                                                                                            harianDw = (Double) (Double.valueOf(listPayDayfromSalLevel.get(employeeMinimalis.getOID()).toString()));
                                                                                        }
                                                                                        rowx.add(""+Formater.formatNumberMataUang(harianDw, "Rp"));
                                                                                        rowx.add(""+Formater.formatNumberMataUang((rekapitulasiPresenceDanSchedule.getPresenceOnTime()*harianDw), "Rp"));
                                  
                                                                                    } else {
                                                                                        rowx.add(""+Formater.formatNumberMataUang(hashTblPayDay.getPayDay(employeeMinimalis.getEmpCategory(), oidEmployeeDw, employeeMinimalis.getPositionId()), "Rp") ); 
                                                                                        rowx.add(""+Formater.formatNumberMataUang((rekapitulasiPresenceDanSchedule.getPresenceOnTime()*hashTblPayDay.getPayDay(employeeMinimalis.getEmpCategory(), oidEmployeeDw, employeeMinimalis.getPositionId())), "Rp"));
                                  
                                                                                    }
                                                                                   // rowx.add(""+Formater.formatNumberMataUang(hashTblPayDay.getPayDay(employeeMinimalis.getEmpCategory(), oidEmployeeDw, employeeMinimalis.getPositionId()), "Rp")); 
                                                                                    
                                                                                    
                                                                                    for (int idx = 0; idx < itDate; idx++) {
                                                                                        //rowx.add("" + idx);
                                                                                        Date selectedDate = new Date(rekapitulasiAbsensi.getDtFrom().getYear(), rekapitulasiAbsensi.getDtFrom().getMonth(), (rekapitulasiAbsensi.getDtFrom().getDate() + idx));
                                                                                            
                                                                                        if (viewschedule == 2){
                                                                                            
                                                                                             long OIDschedule = PstScheduleSymbol.getScheduleId(rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate));
                                                                                          if ((OIDschedule == dayOFF) || (OIDschedule == extraDayOFF)){
                                                                                            rowx.add("<b style=\"color:red\">" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate) +"</b>");
                                                                                          } else {
                                                                                          rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate));
                                                                                        }
                                                                                        } else if (viewschedule == 1) {
                                                                                         long OIDschedule = PstScheduleSymbol.getScheduleId(rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate));
                                                                                         if ((OIDschedule == dayOFF) || (OIDschedule == extraDayOFF)){
                                                                                            rowx.add("<b style=\"color:red\">" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataStatus(selectedDate) +"</b>");
                                                                                         } else {
                                                                                          rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataStatus(selectedDate));
                                                                                        }
                                                                                        } else {
                                                                                            long OIDschedule = PstScheduleSymbol.getScheduleId(rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate));
                                                                                          if ((OIDschedule == dayOFF) || (OIDschedule == extraDayOFF)){
                                                                                            rowx.add("<b style=\"color:red\">" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate) +"</b>");
                                                                                          } else {
                                                                                          rowx.add("" + rekapitulasiPresenceDanSchedule.getScheduleSymbol(selectedDate)+rekapitulasiPresenceDanSchedule.getDataReason(selectedDate));
                                                                                        }
                                                                                        }
                                                                                       
                                                                                        
                                                                                     }
                                                                                    
                                                                                  
                                                                                    if(hashTblPayDay!=null){
                                                                                        
                                                                                   String nilaiemp2 =  rekapitulasiAbsensi.getEmpCategory().replaceAll(",", "" );
                                                                                   String sdw2 = String.valueOf(Dw);
                                                                                    if ( nilaiemp2.equals(sdw2)){
                                                                                            double harianDw = 0;
                                                                                            if (listPayDayfromSalLevel.get(employeeMinimalis.getOID()) != null){
                                                                                            harianDw = (Double) (Double.valueOf(listPayDayfromSalLevel.get(employeeMinimalis.getOID()).toString()));
                                                                                            }
                                                                                            rekapitulasiAbsensiGrandTotal.setPayDay(rekapitulasiAbsensiGrandTotal.getPayDay()+harianDw);
                                                                                            rekapitulasiAbsensiGrandTotal.setTotalPayDay(rekapitulasiAbsensiGrandTotal.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getPresenceOnTime()*harianDw));
                                                                                            rekapitulasiAbsensiGrandTotalSec.setPayDay(rekapitulasiAbsensiGrandTotalSec.getPayDay()+harianDw);
                                                                                            rekapitulasiAbsensiGrandTotalSec.setTotalPayDay(rekapitulasiAbsensiGrandTotalSec.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getPresenceOnTime()*harianDw));
                                                                                            rekapitulasiAbsensiGrandTotalPerCompany.setPayDay(rekapitulasiAbsensiGrandTotalPerCompany.getPayDay()+harianDw);
                                                                                            rekapitulasiAbsensiGrandTotalPerCompany.setTotalPayDay(rekapitulasiAbsensiGrandTotalPerCompany.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getPresenceOnTime()*harianDw));
                                                                                    
                                                                                        } else {
                                                                                            rekapitulasiAbsensiGrandTotal.setPayDay(rekapitulasiAbsensiGrandTotal.getPayDay()+hashTblPayDay.getPayDay(employeeMinimalis.getEmpCategory(), oidEmployeeDw, employeeMinimalis.getPositionId()));
                                                                                            rekapitulasiAbsensiGrandTotal.setTotalPayDay(rekapitulasiAbsensiGrandTotal.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getPresenceOnTime()*hashTblPayDay.getPayDay(employeeMinimalis.getEmpCategory(), oidEmployeeDw, employeeMinimalis.getPositionId())));
                                                                                            rekapitulasiAbsensiGrandTotalSec.setPayDay(rekapitulasiAbsensiGrandTotalSec.getPayDay()+hashTblPayDay.getPayDay(employeeMinimalis.getEmpCategory(), oidEmployeeDw, employeeMinimalis.getPositionId()));
                                                                                            rekapitulasiAbsensiGrandTotalSec.setTotalPayDay(rekapitulasiAbsensiGrandTotalSec.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getPresenceOnTime()*hashTblPayDay.getPayDay(employeeMinimalis.getEmpCategory(), oidEmployeeDw, employeeMinimalis.getPositionId())));
                                                                                            rekapitulasiAbsensiGrandTotalPerCompany.setPayDay(rekapitulasiAbsensiGrandTotalPerCompany.getPayDay()+hashTblPayDay.getPayDay(employeeMinimalis.getEmpCategory(), oidEmployeeDw, employeeMinimalis.getPositionId()));
                                                                                            rekapitulasiAbsensiGrandTotalPerCompany.setTotalPayDay(rekapitulasiAbsensiGrandTotalPerCompany.getTotalPayDay()+(rekapitulasiPresenceDanSchedule.getPresenceOnTime()*hashTblPayDay.getPayDay(employeeMinimalis.getEmpCategory(), oidEmployeeDw, employeeMinimalis.getPositionId())));
                                                                                    
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
                                                                            rowx.add("<b>"+Formater.formatNumberMataUang(rekapitulasiAbsensiGrandTotalSec.getPayDay(), "Rp")+"</b>");
                                                                            rowx.add("<b>"+Formater.formatNumberMataUang((rekapitulasiAbsensiGrandTotalSec.getTotalPayDay()), "Rp") +"</b>");
                                                                            for (int idx = 0; idx < itDate; idx++) {
                                                                                rowx.add("");
                                                                            }
                                                                            
                                                                            ctrlist.drawListRowJsVer2(outJsp, 0, rowx, 0); 
                                                                            
                                                                        }//end list emp
                                                                    }//end hasEmployee
                                                                    
                                                                    
                                                                }
                                                            }
                                                            
                                                            //tota dept yg ada sectionnya
                                                                            rowx = new Vector();
                                                                            rowx.add("" + (TotalEmployeePerDeptnSection));
                                                                            rowx.add("");
                                                                            rowx.add("<b>Total Department</b>");  
                                                                            rowx.add("<b>"+rekapitulasiAbsensiGrandTotal.getHK()+"</b>"); 
                                                                            rowx.add("<b>"+Formater.formatNumberMataUang(rekapitulasiAbsensiGrandTotal.getPayDay(), "Rp") +"</b>");
                                                                            rowx.add("<b>"+Formater.formatNumberMataUang((rekapitulasiAbsensiGrandTotal.getTotalPayDay()), "Rp") +"</b>");
                                                                            for (int idx = 0; idx < itDate; idx++) {
                                                                                rowx.add("");
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
                        
                        rowx.add("<b>"+Formater.formatNumberMataUang(rekapitulasiAbsensiGrandTotalPerCompany.getPayDay(), "Rp") +"</b>");
                        rowx.add("<b>"+Formater.formatNumberMataUang((rekapitulasiAbsensiGrandTotalPerCompany.getTotalPayDay()), "Rp") +"</b>"); 
                        for (int idx = 0; idx < itDate; idx++) {
                            rowx.add("");
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

    
    //    OID_DAILYWORKER
           long Dw = 0;
            try{
                String sDw = PstSystemProperty.getValueByName("OID_DAILYWORKER"); 
                Dw = Integer.parseInt(sDw);
             }catch(Exception ex){
                 System.out.println("VALUE_DAILYWORKER NOT Be SET"+ex);
             } 
    int iCommand = FRMQueryString.requestCommand(request);
    int iErrCode = FRMMessage.NONE;
    int start = FRMQueryString.requestInt(request, "start");
   
    RekapitulasiAbsensi rekapitulasiAbsensi = new RekapitulasiAbsensi();
    rekapitulasiAbsensi.setCompanyId(FRMQueryString.requestLong(request, "company_id"));
    //rekapitulasiAbsensi.setDeptId(FRMQueryString.requestLong(request, "department"));
    rekapitulasiAbsensi.addArrDivision(FRMHandler.getParamsStringValuesStatic(request ,"division_id"));
    rekapitulasiAbsensi.addArrDepartment(FRMHandler.getParamsStringValuesStatic(request ,"department"));
    rekapitulasiAbsensi.addArrSection(FRMHandler.getParamsStringValuesStatic(request ,"section"));
    
    //rekapitulasiAbsensi.setSectionId(FRMQueryString.requestLong(request, "section"));
    //rekapitulasiAbsensi.setDivisionId(FRMQueryString.requestLong(request, "division_id"));
    rekapitulasiAbsensi.setDtFrom(java.sql.Date.valueOf(FRMQueryString.requestString(request, "check_date_start")));
    rekapitulasiAbsensi.setDtTo(java.sql.Date.valueOf(FRMQueryString.requestString(request, "check_date_finish")));
    rekapitulasiAbsensi.setEmpCategory(stsEmpCategorySel);
    rekapitulasiAbsensi.setFullName(FRMQueryString.requestString(request, "full_name"));
    rekapitulasiAbsensi.setPayrollNumber(FRMQueryString.requestString(request, "emp_number"));
    rekapitulasiAbsensi.setResignSts(FRMQueryString.requestInt(request, "statusResign"));
    rekapitulasiAbsensi.setSourceTYpe(FRMQueryString.requestInt(request, "source_type"));
    rekapitulasiAbsensi.setViewschedule(FRMQueryString.requestInt(request, "viewschedule"));
    int viewschedule = FRMQueryString.requestInt(request, "viewschedule");
    int OnlyDw = FRMQueryString.requestInt(request, "OnlyDw");
    if (OnlyDw != 0 && OnlyDw == 1){
        rekapitulasiAbsensi.setEmpCategory(Dw+",");
    }
    Vector listCompany = new Vector();
    Hashtable hashDivision = new Hashtable();
    Hashtable hashDepartment = new Hashtable();
    Hashtable hashSection = new Hashtable();
    Hashtable hashEmployee  = new Hashtable();
    Hashtable hashEmployeeSection = new Hashtable();
    Hashtable listAttdAbsensi =null;
    Hashtable listPayDayfromSalLevel = null;
    
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
    try{
    leaveConfig = (I_Leave) (Class.forName("com.dimata.harisma.session.leave.LeaveConfigDinamis").newInstance());
    }catch (Exception e){
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
        Vector vReason = null;
         //Hashtable hashSectionClone = new Hashtable(hashSection);  
         HashTblPayDay hashTblPayDay = new HashTblPayDay();
        long oidEmployeeDw = Dw;
        String where = rekapitulasiAbsensi.getWhereClauseEMployee();
    if(iCommand==Command.LIST){
            EmployeeSrcRekapitulasiAbs employeeSrcRekapitulasiAbs = PstEmployee.getEmployeeFilter(0, 0, where, PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
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
            
         //   Vector nilaial = PstAlStockTaken.getAnnualLeave(rekapitulasiAbsensi.getDtFrom(), rekapitulasiAbsensi.getDtTo());
            hashEmployeeSection = employeeSrcRekapitulasiAbs.getEmpId()!=null && employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstEmployee.hashListEmployeeSection(0, 0,  rekapitulasiAbsensi.whereClauseEmpId(employeeSrcRekapitulasiAbs.getEmpId())):new Hashtable();
            
            listAttdAbsensi =  employeeSrcRekapitulasiAbs.getEmpId()!=null &&  employeeSrcRekapitulasiAbs.getEmpId().length()>0?PstEmpSchedule.getListAttendaceRekap(attdConfig,leaveConfig,rekapitulasiAbsensi.getDtFrom(), rekapitulasiAbsensi.getDtTo(), employeeSrcRekapitulasiAbs.getEmpId(), vctSchIDOff, hashSchOff, iPropInsentifLevel, holidaysTable, hashPositionLevel, payrollCalculatorConfig,hashPeriod):new Hashtable(); 
            vReason = PstReason.listReason(0, 0, PstReason.fieldNames[PstReason.FLD_REASON_TIME] + "=" + PstReason.REASON_TIME_YES, PstReason.fieldNames[PstReason.FLD_NUMBER_OF_SHOW]+ " ASC "); 
            listPayDayfromSalLevel =  PstPayEmpLevel.getHashPayDayFromSalLev(employeeSrcRekapitulasiAbs.getEmpId(), rekapitulasiAbsensi.getDtTo());
            
           //hashSectionClone = new Hashtable(hashSection); 
             /*Hashtable listAttdAbsensiClone = new Hashtable(listAttdAbsensi);  
            Hashtable hashDepartmentClone = new Hashtable(hashDepartment); 
            Hashtable hashDivisionClone = new Hashtable(hashDivision); 
            Hashtable hashEmployeeClone = new Hashtable(hashEmployee); 
            Hashtable hashEmployeeSectionClone = new Hashtable(hashEmployeeSection); */
           
            
            /*rekapitulasiAbsensi.setHashDepartment(hashDepartmentClone);
            rekapitulasiAbsensi.setHashDivision(hashDivisionClone);
            rekapitulasiAbsensi.setHashEmployee(hashEmployeeClone);
            rekapitulasiAbsensi.setHashEmployeeSection(hashEmployeeSectionClone);
            rekapitulasiAbsensi.setHashSection(hashSectionClone);
            rekapitulasiAbsensi.setListAttdAbsensi(listAttdAbsensiClone);*/
            rekapitulasiAbsensi.setListCompany(listCompany);
            //rekapitulasiAbsensi.setvReason(vReason); 
            rekapitulasiAbsensi.setDtFrom(rekapitulasiAbsensi.getDtFrom());
            rekapitulasiAbsensi.setDtTo(rekapitulasiAbsensi.getDtTo());
            rekapitulasiAbsensi.setJudul(" REKAPITULASI  ABSENSI : "); 
            
            if(rekapitulasiAbsensi.getSourceTYpe()!=0){
                hashTblPayDay = PstPayDay.hashtblPayDay(0, 0, "", ""); 
            }
             session.putValue("rekapitulasi", rekapitulasiAbsensi);
             session.putValue("wherenya", where);
    }
%>
<%
if(rekapitulasiAbsensi.getSourceTYpe()==0){%>
    <%=drawList(out, rekapitulasiAbsensi,listCompany,hashDivision,hashDepartment,hashSection,hashEmployee,hashEmployeeSection,listAttdAbsensi,vReason,viewschedule)%>
<%

}else{%>
    <%=drawListRincian(out, rekapitulasiAbsensi,listCompany,hashDivision,hashDepartment,hashSection,hashEmployee,hashEmployeeSection,listAttdAbsensi,hashTblPayDay,oidEmployeeDw,viewschedule,listPayDayfromSalLevel,OnlyDw)%>
<%}
%>

