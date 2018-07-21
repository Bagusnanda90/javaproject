<%-- 
    Document   : summary_attendance_sheet
    Created on : Dec 11, 2012, 10:42:46 AM
    Author     : Satrya Ramayu
--%>


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
<%!    public String drawList(JspWriter outJsp,Date selectedDateFrom,Date selectedDateTo,Vector vSpesialLeaveSymbole,Vector vReason,Vector listEmployee,Hashtable listTakenLeave,Vector listAttdAbsensi,Hashtable listTotSUmLeaveSpecial,Hashtable hashReason,Hashtable hashPersonalInOut,Hashtable hasSchedule,Hashtable hashTblSchedule,Hashtable hashAdjusmentForPayInput,Hashtable sumOvertimeDailyPaidBySalary,Hashtable hashAdjusmentForPaySlip,Hashtable sumOvertimeDailyPaidByDayOff,Hashtable sumOvertimeDailyAllowanceMoney,Hashtable sumOvertimeDailyAllowanceFood,Hashtable hasDfltSchedule,TmpListParamAttdSummary tmpListParamAttdSummary) {  
        String result = "";
        String format = "#.##"; 
        String formatInst = "#";
         Hashtable hashSymbol = new Hashtable();
         Hashtable breakTimeDuration = PstScheduleSymbol.getBreakTimeDuration();
         
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
         int propReasonNo = -1;
        try {
            propReasonNo = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_REASON_DUTTY_NO"));

        } catch (Exception ex) {
            System.out.println("Execption REASON_DUTTY_NO: " + ex);
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
        ctrlist.setMaxFreezingTable(3);
        //mengambil nama dari kode komponent
        ctrlist.addHeader("No", "5%", "2", "0");
   
            ctrlist.addHeader("Payroll Number", "5%","2", "0");
            ctrlist.addHeader("Full Name", "5%","2", "0");
            //ctrlist.addHeader("Division", "5%","2", "0");
            ctrlist.addHeader("Departement", "5%","2", "0");
            //ctrlist.addHeader("Section", "5%","2", "0");
            
            ctrlist.addHeader("Leave","20%","0",""+(6+(vSpesialLeaveSymbole==null?0:vSpesialLeaveSymbole.size()*2)));             
            ctrlist.addHeader("Taken AL", "5%","0", "0");
            ctrlist.addHeader("Time Taken AL", "5%","0", "0");
            ctrlist.addHeader("Taken DP", "5%","0", "0");
            ctrlist.addHeader("Time Taken DP", "5%","0", "0");
            ctrlist.addHeader("Taken LL", "5%","0", "0");
            ctrlist.addHeader("Time Taken LL", "5%","0", "0"); 

            if (vSpesialLeaveSymbole != null && vSpesialLeaveSymbole.size() > 0) {
                for (int jHeader = 0; jHeader < vSpesialLeaveSymbole.size(); jHeader++) {
                    SpecialUnpaidLeaveTaken specialUnpaidLeaveTaken = (SpecialUnpaidLeaveTaken) vSpesialLeaveSymbole.get(jHeader);
                    
                    ctrlist.addHeader("Taken " +specialUnpaidLeaveTaken.getSymbole(), "5%","0", "0");
                    ctrlist.addHeader("Time Taken " +specialUnpaidLeaveTaken.getSymbole(), "5%","0", "0"); 
                }
            }
             ctrlist.addHeader("Total Taken Leave", "5%","2", "0");
            ctrlist.addHeader("Total Time  Taken Leave", "5%","2", "0");
            
            if (vReason != null && vReason.size() > 0) { 
                
                ctrlist.addHeader("Reason","20%","0",""+(vReason==null?0:vReason.size()*2));              
                for (int jHeader = 0; jHeader < vReason.size(); jHeader++) {

                    Reason reason = (Reason) vReason.get(jHeader);
                    
                    ctrlist.addHeader(reason.getReason(), "5%","0", "0");
                    ctrlist.addHeader("Time " + reason.getReason(), "5%","0", "0"); 

                }
            }
            //update by satrya 2013-04-18

            if (showOvertime == 0) {
                
                ctrlist.addHeader("Total Insentif", "5%","2", "0"); 
                
                 ctrlist.addHeader("OT Paid Salary","20%","0",""+(3));        
                ctrlist.addHeader("Frequensi", "5%","0", "0"); 
                ctrlist.addHeader("Idx", "5%","0", "0"); 
                ctrlist.addHeader("Real Duration", "5%","0", "0"); 
                
                ctrlist.addHeader("OT Paid DP","20%","0",""+(3));        
                ctrlist.addHeader("Frequensi", "5%","0", "0"); 
                ctrlist.addHeader("Idx", "5%","0", "0"); 
                ctrlist.addHeader("Real Duration", "5%","0", "0"); 
                
                 ctrlist.addHeader("OT Allowance Money","20%","0",""+(3));        
                ctrlist.addHeader("Frequensi", "5%","0", "0"); 
                ctrlist.addHeader("Idx", "5%","0", "0"); 
                ctrlist.addHeader("Real Duration", "5%","0", "0"); 
                
                 ctrlist.addHeader("OT Allowance Food","20%","0",""+(3));        
                ctrlist.addHeader("Frequensi", "5%","0", "0"); 
                ctrlist.addHeader("Idx", "5%","0", "0"); 
                ctrlist.addHeader("Real Duration", "5%","0", "0"); 
          }   
 
             ctrlist.addHeader("Attendance","20%","0",""+(15));        
             
             ctrlist.addHeader("Total Late", "5%","0", "0"); 
             ctrlist.addHeader("Total Time Late", "5%","0", "0"); 
             ctrlist.addHeader("Total Absence", "5%","0", "0"); 
             ctrlist.addHeader("Total Time Absence", "5%","0", "0"); 
             ctrlist.addHeader("Total Early Home", "5%","0", "0");
             ctrlist.addHeader("Total Time Early Home", "5%","0", "0");
             ctrlist.addHeader("Total Late Early", "5%","0", "0"); 
             ctrlist.addHeader("Total Time Late Early", "5%","0", "0");
             ctrlist.addHeader("Total Only In", "5%","0", "0");
             ctrlist.addHeader("Total Only Out", "5%","0", "0");
             ctrlist.addHeader("Total Status OK", "5%","0", "0");
             ctrlist.addHeader("Total Time Status OK", "5%","0", "0");
             ctrlist.addHeader("Total Work Days", "5%","0", "0");                        
             ctrlist.addHeader("Total Time Work Days", "5%","0", "0");                        
             ctrlist.addHeader("Total Time Work Hours", "5%","0", "0");


        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;
        String empNumTest="";
        int no = 0;
            try {
                if (listEmployee != null && listEmployee.size() > 0) {
                      ctrlist.drawListHeaderWithJsVer2(outJsp);//header
                    for (int idxRecord = 0; idxRecord < listEmployee.size(); idxRecord++) {
                        float sumLeaveTotal = 0;
                        no++;
                        Vector rowx = new Vector();
                        SummaryEmployeeAttendance summaryEmployeeAttendance = (SummaryEmployeeAttendance) listEmployee.get(idxRecord);
                        empNumTest = summaryEmployeeAttendance.getEmployeeNum(); 
                        
                        int collPos = 0;

                        if(summaryEmployeeAttendance.getEmployeeNum().equalsIgnoreCase("10001")){
                         //index = 1;
                        } 
                        /**
                         * @DESC : CREATE NEW ROW
                         */
                        
                        
                        int numberColom=0;
                        rowx.add(""+no);
                        rowx.add(""+summaryEmployeeAttendance.getEmployeeNum());
                        rowx.add(""+summaryEmployeeAttendance.getFullName()); 
                        //rowx.add(""+summaryEmployeeAttendance.getDivision());
                        rowx.add(""+summaryEmployeeAttendance.getDepartment());
                        //rowx.add(summaryEmployeeAttendance.getSection() != null && summaryEmployeeAttendance.getSection().length() > 0 ? summaryEmployeeAttendance.getSection() : "-");

                        float totalTakenAl = 0.0F;
                        float totalTakenDP = 0.0F;
                        float totalTakenLL = 0.0F;
                        if (listTakenLeave != null && listTakenLeave.size() > 0 && listTakenLeave.get(summaryEmployeeAttendance.getEmployeeId()) != null) { 

                            LeaveApplicationSummary leaveApplicationSummary = (LeaveApplicationSummary) listTakenLeave.get(summaryEmployeeAttendance.getEmployeeId());
                            if (leaveApplicationSummary.getEmployeeId() != 0 && leaveApplicationSummary.getEmployeeId() == summaryEmployeeAttendance.getEmployeeId()) {

                                totalTakenAl = 0;
                                totalTakenDP = 0;
                                totalTakenLL = 0;
                                 Vector vSymbol = new Vector(leaveApplicationSummary.getVsymbol());
                                Vector vJumlahTaken = new Vector(leaveApplicationSummary.getJumlahTaken());
                                if (leaveApplicationSummary.getVsymbol() != null && leaveApplicationSummary.getVsymbol().size() > 0) {
                                    for (int idxCT = 0; idxCT < vSymbol.size(); idxCT++) {
                                        if (leaveApplicationSummary.getSymbol(idxCT).equalsIgnoreCase("AL")) {
                                            totalTakenAl = totalTakenAl + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                           // leaveApplicationSummary.getVsymbol().remove(idxCT);
                                            // leaveApplicationSummary.getJumlahTaken().remove(idxCT);
                                            //idxCT = idxCT - 1;
                                            vSymbol.remove(idxCT);
                                            vJumlahTaken.remove(idxCT);
                                            idxCT = idxCT - 1;
                                        } else if (leaveApplicationSummary.getSymbol(idxCT).equalsIgnoreCase("DP")) {
                                            totalTakenDP = totalTakenDP + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                            //leaveApplicationSummary.getVsymbol().remove(idxCT);
                                           // leaveApplicationSummary.getJumlahTaken().remove(idxCT);
                                           // idxCT = idxCT - 1;
                                            vSymbol.remove(idxCT);
                                            vJumlahTaken.remove(idxCT);
                                            idxCT = idxCT - 1;
                                        } else if (leaveApplicationSummary.getSymbol(idxCT).equalsIgnoreCase("LL")) {
                                            totalTakenLL = totalTakenLL + (float) leaveApplicationSummary.getJumlahTaken(idxCT); 
                                            vSymbol.remove(idxCT);
                                            vJumlahTaken.remove(idxCT);
                                            idxCT = idxCT - 1;
                                            //leaveApplicationSummary.getVsymbol().remove(idxCT);
                                            //leaveApplicationSummary.getJumlahTaken().remove(idxCT);
                                            //idxCT = idxCT - 1;
                                        }

                                    }
                                }
                                sumLeaveTotal = sumLeaveTotal + totalTakenAl + totalTakenDP + totalTakenLL;

                            }

                            // }

                        }

                        PayInputPresence payInputPresence = new PayInputPresence();
                        if(listAttdAbsensi!=null && listAttdAbsensi.size()>0){
                             for(int idx=0;idx<listAttdAbsensi.size();idx++){
                                   payInputPresence =(PayInputPresence)listAttdAbsensi.get(idx);
                                  if(summaryEmployeeAttendance.getEmployeeId()==payInputPresence.getEmployeeId()){
                                       
                                        listAttdAbsensi.remove(idx);
                                        idx = idx -1; 
                                        break; //jika sdh ketemu lalu di stop
                                  }
                                 
                             }
                         
                         }
                        /**
                         * * @DESC : Taken AL Frequency
                         */
                         rowx.add(""+Formater.formatNumber(totalTakenAl,format)); 
                        /**
                         * * @DESC : Taken AL TIME
                         */
                         rowx.add(totalTakenAl == 0 ? "-" : String.valueOf(Formater.formatWorkDayHoursMinutes(totalTakenAl, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()))); 

                        /**
                         * * @DESC : Taken DP Frequency
                         */
                         rowx.add(""+Formater.formatNumber(totalTakenDP,format));

                        /**
                         * * @DESC : Taken DP TIME
                         */
                        rowx.add(totalTakenDP == 0 ? "-" : String.valueOf(Formater.formatWorkDayHoursMinutes(totalTakenDP, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())));


                        /**
                         * * @DESC : Taken LL Frequency
                         */
                         rowx.add(""+Formater.formatNumber(totalTakenLL,format));

                        /**
                         * * @DESC : Taken LL TIME
                         */
                         rowx.add(totalTakenLL == 0 ? "-" : String.valueOf(Formater.formatWorkDayHoursMinutes(totalTakenLL, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())));
                        /**
                         * *@DESC: SPECIAL LEAVE
                         */
                        if (vSpesialLeaveSymbole != null && vSpesialLeaveSymbole.size() > 0) {
                            for (int idxSP = 0; idxSP < vSpesialLeaveSymbole.size(); idxSP++) {
                                SpecialUnpaidLeaveTaken specialUnpaidLeaveTaken = (SpecialUnpaidLeaveTaken) vSpesialLeaveSymbole.get(idxSP);



                                if (listTotSUmLeaveSpecial != null && listTotSUmLeaveSpecial.size() > 0 && listTotSUmLeaveSpecial.get(summaryEmployeeAttendance.getEmployeeId()) != null) {
                                    LeaveApplicationSummary leaveApplicationSummary = (LeaveApplicationSummary) listTotSUmLeaveSpecial.get(summaryEmployeeAttendance.getEmployeeId());
                                    if (leaveApplicationSummary.getEmployeeId() != 0 && leaveApplicationSummary.getEmployeeId() == summaryEmployeeAttendance.getEmployeeId()) {

                                        float totalCutiSU = 0;
                                        Vector vSymbol = new Vector(leaveApplicationSummary.getVsymbol());
                                        Vector vJumlahTaken = new Vector(leaveApplicationSummary.getJumlahTaken());
                                        if (vSymbol != null && vSymbol.size() > 0) {
                                            for (int idxCT = 0; idxCT < vSymbol.size(); idxCT++) {
                                                if (leaveApplicationSummary.getSymbol(idxCT).equalsIgnoreCase(specialUnpaidLeaveTaken.getSymbole())) {
                                                    totalCutiSU = totalCutiSU + (float) leaveApplicationSummary.getJumlahTaken(idxCT);
                                                    //leaveApplicationSummary.getVsymbol().remove(idxCT);
                                                    //idxCT = idxCT - 1;
                                                    vSymbol.remove(idxCT);
                                                   vJumlahTaken.remove(idxCT);
                                                   idxCT = idxCT - 1;
                                                }

                                            }

                                            sumLeaveTotal = sumLeaveTotal + totalCutiSU;

                                        }
                                        /**
                                         * * @DESC : frequensi SU
                                         */
                                         rowx.add(""+Formater.formatNumber(totalCutiSU,format));

                                        /**
                                         * * @DESC : Time SU
                                         */
                                         rowx.add(totalCutiSU == 0 ? "-" : String.valueOf(Formater.formatWorkDayHoursMinutes(totalCutiSU, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())));


                                    } 

                                    // }

                                } else {
                                    /**
                                     * * @DESC : frequensi SU
                                     */
                                     rowx.add(""+0);

                                    /**
                                     * * @DESC : Time SU
                                     */
                                     rowx.add("-");
                                }

                            }//end lopp idxSp
                        }//end sp
                        /**
                         * * @DESC : Total Leave
                         */
                         rowx.add(""+Formater.formatNumber(sumLeaveTotal,format));

                        /**
                         * * @DESC : Total Time Leave
                         */
                         rowx.add(sumLeaveTotal == 0 ? "-" : String.valueOf(Formater.formatWorkDayHoursMinutes(sumLeaveTotal, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())));

                        /**
                         * *@DESC: REASON
                         */
                        if (vReason != null && vReason.size() > 0) {
                            for (int idxRes = 0; idxRes < vReason.size(); idxRes++) {
                                Reason reason = (Reason) vReason.get(idxRes);

                                if (hashReason != null && hashReason.size() > 0) {
                                    if (hashPersonalInOut != null && hashPersonalInOut.size() > 0) {
                                        if (hashPersonalInOut.containsKey(reason.getNo())) {
                                            Vector listPresencePersonalInOutReason = (Vector) hashPersonalInOut.get(reason.getNo());
                                            if (hashReason.containsKey(reason.getNo())) {
                                                Hashtable vReasonByNo = (Hashtable) hashReason.get(reason.getNo());
                                                if (summaryEmployeeAttendance!=null && summaryEmployeeAttendance.getEmployeeId()!=0 && vReasonByNo != null && vReasonByNo.size() > 0 && vReasonByNo.containsKey(""+summaryEmployeeAttendance.getEmployeeId())) {
                                                    Date tmpDate = new Date(selectedDateFrom.getTime());
                                                    Date tmpEndDate = new Date(selectedDateTo.getTime());
                                                    Date dtSchDateTime = null;
                                                    //Date dtPresenceDateTime = null;
                                                    //if(){
                                                   // for (int idxResNo = 0; idxResNo < vReasonByNo.size(); idxResNo++) {

                                                        Vector vStrTotal = new Vector();//

                                                        EmpScheduleReport empScheduleReport = (EmpScheduleReport) vReasonByNo.get(""+summaryEmployeeAttendance.getEmployeeId());
                                                        
                                                        tmpListParamAttdSummary.addHashReasonCalulateBySistem(empScheduleReport, summaryEmployeeAttendance.getEmployeeId()+"_"+reason.getNo());

                                                        if (empScheduleReport.getEmployeeId() == summaryEmployeeAttendance.getEmployeeId()) {
                                                            String payInputIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_IDX_ADJUSMENT] + "_" + reason.getNo() + "_" + empScheduleReport.getEmployeeId();
                                                            String payInputTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_REASON_TIME_ADJUSMENT] + "_" + reason.getNo() + "_" + empScheduleReport.getEmployeeId();
                                                            //if (empScheduleReport.getTotReason() != 0) {
                                                            //jika dia tgs kantor
                                                            if (reason.getNo() == propReasonNo) {
                                                                vStrTotal = AttendanceSummaryXls.timeWorkOnDutty(hasSchedule, hashTblSchedule, leaveConfig, oidScheduleOff, tmpEndDate, listPresencePersonalInOutReason, tmpDate, hashSymbol, dtSchDateTime, empScheduleReport, summaryEmployeeAttendance, breakTimeDuration, reason.getNo(), propReasonNo, propCheckLeaveExist, hasDfltSchedule, hashAdjusmentForPayInput, payInputIdx, payInputTime);  
                                                            } else {
                                                                vStrTotal = AttendanceSummaryXls.timeAbsenceDurationReason(hasSchedule, hashTblSchedule, leaveConfig, oidScheduleOff, tmpEndDate, listPresencePersonalInOutReason, tmpDate, hashSymbol, dtSchDateTime, empScheduleReport, summaryEmployeeAttendance, breakTimeDuration, reason.getNo(), propReasonNo, propCheckLeaveExist, hasDfltSchedule, hashAdjusmentForPayInput, payInputIdx, payInputTime);
                                                                //ini semntara di hideen di line 1791
                                                            }
                                                            //}

                                                            String strTotal = "";//
                                                            int indReasonAdj = 0;
                                                            if (vStrTotal != null && vStrTotal.size() == 2) {
                                                                tmpListParamAttdSummary.addHashReasonHitung(vStrTotal, summaryEmployeeAttendance.getEmployeeId()+"_"+reason.getNo());
                                                                indReasonAdj = (Integer) vStrTotal.get(0);
                                                                strTotal = (String) vStrTotal.get(1);
                                                            }
                                                            
                                                            /**
                                                             * * @DESC :
                                                             * frequensi Reason
                                                             */
                                                             rowx.add(""+(empScheduleReport.getTotReason() + indReasonAdj)); 

                                                            /**
                                                             * * @DESC : Time
                                                             * Reason
                                                             */
                                                            rowx.add(strTotal == null || strTotal.equalsIgnoreCase("0") ? "-" : String.valueOf(strTotal));


                                                            vReasonByNo.remove(""+summaryEmployeeAttendance.getEmployeeId()); 
                                                            //idxResNo = idxResNo - 1;

                                                        }
                                                    //}
                                                } else {
                                                    /**
                                                     * * @DESC : frequensi
                                                     * Reason
                                                     */
                                                    rowx.add(""+0);

                                                    /**
                                                     * * @DESC : Time Reason
                                                     */
                                                    rowx.add("-");

                                                }

                                            }
                                        }
                                    }

                                }

                            }//end lopp idxSp
                        }//end jika Vspecial leave
                        /**
                         * OVERTIME
                         */
                        if (showOvertime == 0) {
                            float totInsentif = payInputPresence.getInsentif();
                            float insentifAdj = 0;

                                        if (hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0 && selectedDateTo != null && selectedDateFrom != null) {
                                            Date dtAdjusment = null;
                                            long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                            if (duration > 0) {
                                                int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                                for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                                    dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                                    if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_INSENTIF_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                                        double dtAdj = ((Double) hashAdjusmentForPayInput.get(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_INSENTIF_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd")));
                                                        //insentifAdj = insentifAdj + (float) dtAdj;
                                                        insentifAdj = (float) dtAdj;
                                                        break;//karena di pay input hanya 1 tgl saja yg di simpan
                                                    }
                                                }
                                            }

                                        }

                            /**
                             * * @DESC : Total Insentif
                             */
                            //insentifAdj
                            rowx.add(""+ ""+Formater.formatNumber((totInsentif + insentifAdj),formatInst));


                            //OT PAID BY MONEY
                            int totPaidSalary = 0;
                            double totalRealDurationPaidSalary = 0;
                            //untuk paid by salary di ambil dari tb payslip berdasarkan date adjusment

                            //update by satrya 2014-02-08
                            double totIdxPaidSalary = 0;
                            double totalIdxPaidSalaryAdj = 0;
                            if (sumOvertimeDailyPaidBySalary != null && sumOvertimeDailyPaidBySalary.size() > 0 && sumOvertimeDailyPaidBySalary.get(summaryEmployeeAttendance.getEmployeeId()) != null) {

                                TableHitungOvertimeDetail overtimeDetailPaidMon = (TableHitungOvertimeDetail) sumOvertimeDailyPaidBySalary.get(summaryEmployeeAttendance.getEmployeeId());
                                if (overtimeDetailPaidMon.getEmployee_id() != 0 && overtimeDetailPaidMon.getEmployee_id() == summaryEmployeeAttendance.getEmployeeId()) {

                                    totPaidSalary = 0;
                                    if (overtimeDetailPaidMon.getSizePaidSalary() != 0) {
                                        for (int idxDur = 0; idxDur < overtimeDetailPaidMon.getSizePaidSalary(); idxDur++) {
                                            totPaidSalary = totPaidSalary + overtimeDetailPaidMon.getvPaidSalary(idxDur);
                                        }
                                    }

                                    totalRealDurationPaidSalary = 0;
                                    if (overtimeDetailPaidMon.getSizeDuration() != 0) {
                                        for (int idxtot = 0; idxtot < overtimeDetailPaidMon.getSizeDuration(); idxtot++) {
                                            totalRealDurationPaidSalary = totalRealDurationPaidSalary + overtimeDetailPaidMon.getvDuration(idxtot);
                                        }
                                    }

                                    totIdxPaidSalary = 0;
                                    if (overtimeDetailPaidMon.getSizeTotIdx() != 0) {
                                        for (int idxtot = 0; idxtot < overtimeDetailPaidMon.getSizeTotIdx(); idxtot++) {
                                            totIdxPaidSalary = totIdxPaidSalary + overtimeDetailPaidMon.getvTotIdx(idxtot);
                                        }
                                    }

                                }

                            }

                            totalIdxPaidSalaryAdj = 0;
                            if (hashAdjusmentForPaySlip != null && hashAdjusmentForPaySlip.size() > 0 && selectedDateTo != null && selectedDateFrom != null) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        if (dtAdjusment != null && hashAdjusmentForPaySlip.containsKey(PstPaySlip.fieldNames[PstPaySlip.FLD_OV_IDX_ADJUSTMENT] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double dtAdj = ((Double) hashAdjusmentForPaySlip.get(PstPaySlip.fieldNames[PstPaySlip.FLD_OV_IDX_ADJUSTMENT] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd")));
                                            totalIdxPaidSalaryAdj = dtAdj;
                                            break;////karena di pay input hanya 1 tgl saja yg di simpan
                                        }
                                    }
                                }

                            }

                            /**
                             * * @DESC : Total Paid Salary berapa kali dapet
                             * salary
                             */
                            rowx.add(""+totPaidSalary); 

                            /**
                             * * @DESC : Total Paid Salary idx
                             */
                            rowx.add(""+Formater.formatNumber(totIdxPaidSalary + totalIdxPaidSalaryAdj, format));

                            /**
                             * * @DESC : Total Paid Salary idx
                             */
                            rowx.add(""+Formater.formatNumber(totalRealDurationPaidSalary,format));


                            /**
                             * OT Paid DP
                             */
                            int totPaidDP = 0;
                            double totalRealDurationPaidDp = 0;
                            double totalIdxPaidDp = 0;
                            double totalIdxPaidDayOffAdj = 0;
                            if (sumOvertimeDailyPaidByDayOff != null && sumOvertimeDailyPaidByDayOff.size() > 0 && sumOvertimeDailyPaidByDayOff.get(summaryEmployeeAttendance.getEmployeeId()) != null) {

                                TableHitungOvertimeDetail overtimeDetailPaidDP = (TableHitungOvertimeDetail) sumOvertimeDailyPaidByDayOff.get(summaryEmployeeAttendance.getEmployeeId());

                                if (overtimeDetailPaidDP.getEmployee_id() != 0 && overtimeDetailPaidDP.getEmployee_id() == summaryEmployeeAttendance.getEmployeeId()) {

                                    totPaidDP = 0;
                                    if (overtimeDetailPaidDP.getSizePaidDp() != 0) {
                                        for (int idxDur = 0; idxDur < overtimeDetailPaidDP.getSizePaidDp(); idxDur++) {
                                            //update by satrya 2014-02-10 totPaidDP = overtimeDetailPaidDP.getSizePaidDp();
                                            totPaidDP = totPaidDP + overtimeDetailPaidDP.getvPaidDp(idxDur);
                                        }
                                    }

                                    totalRealDurationPaidDp = 0;
                                    if (overtimeDetailPaidDP.getSizeDuration() != 0) {
                                        for (int idxtot = 0; idxtot < overtimeDetailPaidDP.getSizeDuration(); idxtot++) {
                                            totalRealDurationPaidDp = totalRealDurationPaidDp + overtimeDetailPaidDP.getvDuration(idxtot);
                                        }
                                    }

                                    totalIdxPaidDp = 0;
                                    if (overtimeDetailPaidDP.getSizeTotIdx() != 0) {
                                        for (int idxtot = 0; idxtot < overtimeDetailPaidDP.getSizeTotIdx(); idxtot++) {
                                            totalIdxPaidDp = totalIdxPaidDp + overtimeDetailPaidDP.getvTotIdx(idxtot);
                                        }
                                    }

                                }
                            }
                            totalIdxPaidDayOffAdj = 0;
                            if (hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0 && selectedDateTo != null && selectedDateFrom != null) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_DP] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double dtAdj = ((Double) hashAdjusmentForPayInput.get(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_IDX_PAID_DP] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd")));
                                            totalIdxPaidDayOffAdj = dtAdj;
                                            break;////karena di pay input hanya 1 tgl saja yg di simpan
                                        }
                                    }
                                }

                            }



                            /**
                             * * @DESC : Total Paid DP berapa kali dapet salary
                             */
                            rowx.add(""+totPaidDP);

                            /**
                             * * @DESC : Total Paid DP idx
                             */
                            rowx.add(""+Formater.formatNumber(totalIdxPaidDp + totalIdxPaidDayOffAdj,format));

                            /**
                             * * @DESC : Total Paid DP idx
                             */
                            rowx.add(""+Formater.formatNumber(totalRealDurationPaidDp,format));





                            //OT ALLOWANCE MONEY
                            int totAllwnceMoney = 0;
                            double totalRealDurationMoneyAllowance = 0;
                            double totalIdxMoneyAllowance = 0;
                            double totalIdxAllowanceMoneyAdj = 0;
                            if (sumOvertimeDailyAllowanceMoney != null && sumOvertimeDailyAllowanceMoney.size() > 0 && sumOvertimeDailyAllowanceMoney.get(summaryEmployeeAttendance.getEmployeeId()) != null) {

                                TableHitungOvertimeDetail overtimeDetailAllwnMon = (TableHitungOvertimeDetail) sumOvertimeDailyAllowanceMoney.get(summaryEmployeeAttendance.getEmployeeId());

                                if (overtimeDetailAllwnMon.getEmployee_id() != 0 && overtimeDetailAllwnMon.getEmployee_id() == summaryEmployeeAttendance.getEmployeeId()) {

                                    totAllwnceMoney = 0;
                                    if (overtimeDetailAllwnMon.getSizeAllowanceMoney() != 0) {
                                        for (int idxDur = 0; idxDur < overtimeDetailAllwnMon.getSizeAllowanceMoney(); idxDur++) {
                                            //update by satrya 2014-02-10 totAllwnceMoney = overtimeDetailAllwnMon.getSizeAllowanceMoney();
                                            totAllwnceMoney = totAllwnceMoney + overtimeDetailAllwnMon.getvAllowanceMoney(idxDur);
                                        }
                                    }

                                    totalRealDurationMoneyAllowance = 0;
                                    if (overtimeDetailAllwnMon.getSizeDuration() != 0) {
                                        for (int idxtot = 0; idxtot < overtimeDetailAllwnMon.getSizeDuration(); idxtot++) {
                                            totalRealDurationMoneyAllowance = totalRealDurationMoneyAllowance + overtimeDetailAllwnMon.getvDuration(idxtot);
                                        }
                                    }

                                    totalIdxMoneyAllowance = 0;
                                    if (overtimeDetailAllwnMon.getSizeTotIdx() != 0) {
                                        for (int idxtot = 0; idxtot < overtimeDetailAllwnMon.getSizeTotIdx(); idxtot++) {
                                            totalIdxMoneyAllowance = totalIdxMoneyAllowance + overtimeDetailAllwnMon.getvTotIdx(idxtot);
                                        }
                                    }
                                }
                            }
                            totalIdxAllowanceMoneyAdj = 0;
                            if (hashAdjusmentForPaySlip != null && hashAdjusmentForPaySlip.size() > 0 && selectedDateTo != null && selectedDateFrom != null) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {

                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        if (dtAdjusment != null && hashAdjusmentForPaySlip.containsKey(PstPaySlip.fieldNames[PstPaySlip.FLD_MEAL_ALLOWANCE_MONEY_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double dtAdj = ((Double) hashAdjusmentForPaySlip.get(PstPaySlip.fieldNames[PstPaySlip.FLD_MEAL_ALLOWANCE_MONEY_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd")));
                                            totalIdxAllowanceMoneyAdj = dtAdj;
                                            break;////karena di pay input hanya 1 tgl saja yg di simpan
                                        }
                                    }
                                }

                            }

                            /**
                             * * @DESC : Total Money Allowance berapa kali
                             * dapet salary
                             */
                            rowx.add(""+totAllwnceMoney);

                            /**
                             * * @DESC : Total Money Allowance idx
                             */
                            rowx.add(""+Formater.formatNumber(totalIdxMoneyAllowance + totalIdxAllowanceMoneyAdj,format));

                            /**
                             * * @DESC : Total Real Money Allowance idx
                             */
                            rowx.add(""+Formater.formatNumber(totalRealDurationMoneyAllowance,format));



                            //OT ALLOWANCE FOOD

                            int totAllowanceFood = 0;
                            double totalRealDurationFoodAllowance = 0;
                            double totalIdxFoodAllowance = 0;
                            double totalIdxAllowanceFoodAdj = 0;

                            if (sumOvertimeDailyAllowanceFood != null && sumOvertimeDailyAllowanceFood.size() > 0 && sumOvertimeDailyAllowanceFood.get(summaryEmployeeAttendance.getEmployeeId()) != null) {

                                TableHitungOvertimeDetail overtimeDetailAllwnFood = (TableHitungOvertimeDetail) sumOvertimeDailyAllowanceFood.get(summaryEmployeeAttendance.getEmployeeId());

                                if (overtimeDetailAllwnFood.getEmployee_id() != 0 && overtimeDetailAllwnFood.getEmployee_id() == summaryEmployeeAttendance.getEmployeeId()) {

                                    totAllowanceFood = 0;
                                    if (overtimeDetailAllwnFood.getSizeAllowanceFood() != 0) {
                                        for (int idxDur = 0; idxDur < overtimeDetailAllwnFood.getSizeAllowanceFood(); idxDur++) {
                                            totAllowanceFood = totAllowanceFood + overtimeDetailAllwnFood.getvAllowanceFood(idxDur);
                                        }
                                    }

                                    totalRealDurationFoodAllowance = 0;
                                    if (overtimeDetailAllwnFood.getSizeDuration() != 0) {
                                        for (int idxtot = 0; idxtot < overtimeDetailAllwnFood.getSizeDuration(); idxtot++) {
                                            totalRealDurationFoodAllowance = totalRealDurationFoodAllowance + overtimeDetailAllwnFood.getvDuration(idxtot);
                                        }
                                    }

                                    totalIdxFoodAllowance = 0;
                                    if (overtimeDetailAllwnFood.getSizeTotIdx() != 0) {
                                        for (int idxtot = 0; idxtot < overtimeDetailAllwnFood.getSizeTotIdx(); idxtot++) {
                                            totalIdxFoodAllowance = totalIdxFoodAllowance + overtimeDetailAllwnFood.getvTotIdx(idxtot);
                                        }
                                    }
                                }

                            }


                            totalIdxAllowanceFoodAdj = 0;
                            if (hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0 && selectedDateTo != null && selectedDateFrom != null) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double dtAdj = ((Double) hashAdjusmentForPayInput.get(FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_OT_ALLOWANCE_FOOD_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId() + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd")));
                                            totalIdxAllowanceFoodAdj = dtAdj;
                                            break;////karena di pay input hanya 1 tgl saja yg di simpan
                                        }
                                    }
                                }

                            }

                            /**
                             * * @DESC : Total Food Allowance berapa kali dapet
                             * salary
                             */
                            rowx.add(""+totAllowanceFood);

                            /**
                             * * @DESC : Total Food Allowance idx
                             */
                            rowx.add(""+Formater.formatNumber(totalIdxFoodAllowance + totalIdxAllowanceFoodAdj,format));

                            /**
                             * * @DESC : Total Real Food Allowance idx
                             */
                            rowx.add(""+Formater.formatNumber(totalRealDurationFoodAllowance,format));

                        }//end Overtime

                        /* DESC: LATE */
                        int totLate = payInputPresence.getLate();
                        //String totTimeLate = "";
                        int totalAdjLate = 0;
                        float totTimeLate = payInputPresence.getLateTime();
                        String strTotalLate = "";//

                        double valueTimeLateAdj = 0;
                        if (summaryEmployeeAttendance != null) {
                            String payInputIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_IDX_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();
                            String payInputTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_TIME_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();

                            if (payInputIdx != null && payInputIdx.length() > 0 && payInputTime != null && payInputTime.length() > 0 && selectedDateTo != null && selectedDateFrom != null && hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        boolean adaAdj = false;
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double tmpValueIdx = (Double) hashAdjusmentForPayInput.get(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            totalAdjLate = (int) tmpValueIdx;
                                            adaAdj = true;
                                        }
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            valueTimeLateAdj = (Double) hashAdjusmentForPayInput.get(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            adaAdj = true;
                                        }
                                        if (adaAdj) {
                                            break;//karena di pay input hanya 1 tgl saja yg di simpan
                                        }

                                    }
                                }
                            }
                        }


                        /**
                         * * @DESC : Total Late Frequency
                         */
                        rowx.add(""+Formater.formatNumber(totLate + totalAdjLate,format));
                        /**
                         * * @DESC : Total Late TIME
                         */
                        try {
                            strTotalLate = Formater.formatWorkDayHoursMinutes((float) (valueTimeLateAdj + totTimeLate) * -1, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                        } catch (Exception exc) {
                        }
                        rowx.add(strTotalLate == null || strTotalLate.length() == 0 || (strTotalLate != null && strTotalLate.equalsIgnoreCase("0")) ? "-" : String.valueOf(strTotalLate));


                        /* DESC : STATUS ABSENCE */
                        int totAbsence = payInputPresence.getAbsence();
                        int totalAdjAbs = 0;
                        String strTotalAbs = "";
                        float totalTimeAbs = payInputPresence.getAbsenceTime();


                        double valueTimeAbsAdj = 0;
                        if (summaryEmployeeAttendance != null) {
                            String payInputIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_IDX_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();
                            String payInputTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_ABSENCE_TIME_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();

                            if (payInputIdx != null && payInputIdx.length() > 0 && payInputTime != null && payInputTime.length() > 0 && selectedDateTo != null && selectedDateFrom != null && hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        boolean adaAdj = false;
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double tmpValueIdx = (Double) hashAdjusmentForPayInput.get(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            totalAdjAbs = (int) tmpValueIdx;
                                            adaAdj = true;
                                        }
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            valueTimeAbsAdj = (Double) hashAdjusmentForPayInput.get(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            adaAdj = true;
                                        }
                                        if (adaAdj) {
                                            break;//karena di pay input hanya 1 tgl saja yg di simpan
                                        }

                                    }
                                }
                            }
                        }
                        /**
                         * * @DESC : Total Abs Frequency
                         */
                        rowx.add(""+Formater.formatNumber(totAbsence + totalAdjAbs,format));
                        /**
                         * * @DESC : Total Abs TIME
                         */
                        try {
                            strTotalAbs = Formater.formatWorkDayHoursMinutes((float) (valueTimeAbsAdj + totalTimeAbs), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                        } catch (Exception exc) {
                        }
                        rowx.add(strTotalAbs == null || strTotalAbs.length() == 0 || (strTotalAbs != null && strTotalAbs.equalsIgnoreCase("0")) ? "-" : String.valueOf(strTotalAbs));


                        /* DESC : STATUS EARLY HOME */
                        int totalAdjEarlyHome = 0;
                        int totEalyHome = payInputPresence.getEarlyHome();
                        String strTotalEalyHome = "";
                        float totTimeEarlyHome = payInputPresence.getEarlyHomeTime();



                        double valueTimeEarlyHome = 0;
                        if (summaryEmployeeAttendance != null) {
                            String payInputIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_IDX_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();
                            String payInputTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_EARLY_HOME_TIME_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();


                            if (payInputIdx != null && payInputIdx.length() > 0 && payInputTime != null && payInputTime.length() > 0 && selectedDateTo != null && selectedDateFrom != null && hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        boolean adaAdj = false;
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double tmpValueIdx = (Double) hashAdjusmentForPayInput.get(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            totalAdjEarlyHome = (int) tmpValueIdx;
                                            adaAdj = true;
                                        }
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            valueTimeEarlyHome = (Double) hashAdjusmentForPayInput.get(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            adaAdj = true;
                                        }
                                        if (adaAdj) {
                                            break;//karena di pay input hanya 1 tgl saja yg di simpan
                                        }

                                    }
                                }
                            }
                        }
                        /**
                         * * @DESC : Total Early Frequency
                         */
                        rowx.add(""+Formater.formatNumber(totEalyHome + totalAdjEarlyHome,format));
                        /**
                         * * @DESC : Total Early TIME
                         */
                        try {
                            strTotalEalyHome = Formater.formatWorkDayHoursMinutes((float) (valueTimeEarlyHome + totTimeEarlyHome) * -1, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                        } catch (Exception exc) {
                        }
                        rowx.add(strTotalEalyHome == null || strTotalEalyHome.length() == 0 || (strTotalEalyHome != null && strTotalEalyHome.equalsIgnoreCase("0")) ? "-" : String.valueOf(strTotalEalyHome));


                        /* DESC : STATUS LATE  EARLY */
                        int totalAdjLateEarly = 0;
                        String strTotalLateEarly = "";
                        int totLateEarly = payInputPresence.getLateEarly();
                        float totTimeLateEarly = payInputPresence.getLateEarlyTime();


                        double valueTimeLateEarlyAdj = 0;
                        if (summaryEmployeeAttendance != null) {
                            String payInputIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_IDX_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();
                            String payInputTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_LATE_EARLY_TIME_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();

                            if (payInputIdx != null && payInputIdx.length() > 0 && payInputTime != null && payInputTime.length() > 0 && selectedDateTo != null && selectedDateFrom != null && hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        boolean adaAdj = false;
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double tmpValueIdx = (Double) hashAdjusmentForPayInput.get(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            totalAdjLateEarly = (int) tmpValueIdx;
                                            adaAdj = true;
                                        }
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            valueTimeLateEarlyAdj = (Double) hashAdjusmentForPayInput.get(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            adaAdj = true;
                                        }
                                        if (adaAdj) {
                                            break;//karena di pay input hanya 1 tgl saja yg di simpan
                                        }

                                    }
                                }
                            }
                        }
                        /**
                         * * @DESC : Total Late Early Frequency
                         */
                        rowx.add(""+Formater.formatNumber(totLateEarly + totalAdjLateEarly,format));
                        /**
                         * * @DESC : Total Late Early TIME
                         */
                        try {
                            strTotalLateEarly = Formater.formatWorkDayHoursMinutes((float) (valueTimeLateEarlyAdj + totTimeLateEarly) * -1, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                        } catch (Exception exc) {
                        }
                        rowx.add(strTotalLateEarly == null || strTotalLateEarly.length() == 0 || (strTotalLateEarly != null && strTotalLateEarly.equalsIgnoreCase("0")) ? "-" : String.valueOf(strTotalLateEarly));


                        /* DESC : STATUS ONLY IN */
                        int totalAdjOnlyIn = 0;
                        String strTotalOnlyIn = "";
                        int totOnlyIn = payInputPresence.getTotalOnlyIn();

                        /**
                         * * @DESC : Total Only In Frequency
                         */
                        rowx.add(""+Formater.formatNumber(totOnlyIn + totalAdjOnlyIn,format));
                        /**
                         * * @DESC : Total Only In TIME
                         */


                        /* DESC : STATUS ONLY OUT */
                        int totalAdjOnlyOut = 0;
                        String strTotalOnlyOut = "";
                        int totOnlyOut = payInputPresence.getTotalOnlyOut();
                        float totTimeOnlyOut = 0;
                        /**
                         * * @DESC : Total Only Out Frequency
                         */
                        rowx.add(""+(totOnlyOut + totalAdjOnlyOut));
                        

                        /* DESC : STATUS OK */
                        int totalAdjOk = 0;
                        String strTotalOk = "";
                        int totOk = payInputPresence.getPresenceOnTime();
                        float totTimeOk = payInputPresence.getPresenceOnTimeTime();

                        double valueTimeOkAdj = 0;
                        if (summaryEmployeeAttendance != null) {
                            String payInputIdx = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_IDX_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();
                            String payInputTime = FrmPayInput.fieldNames[FrmPayInput.FRM_FIELD_PRESENCE_ONTIME_TIME_ADJUSMENT] + "_" + summaryEmployeeAttendance.getEmployeeId();

                            if (payInputIdx != null && payInputIdx.length() > 0 && payInputTime != null && payInputTime.length() > 0 && selectedDateTo != null && selectedDateFrom != null && hashAdjusmentForPayInput != null && hashAdjusmentForPayInput.size() > 0) {
                                Date dtAdjusment = null;
                                long duration = selectedDateTo.getTime() - selectedDateFrom.getTime();
                                if (duration > 0) {
                                    int itDate = Integer.parseInt(String.valueOf(duration / 86400000));
                                    for (int idxDt = 0; idxDt < itDate; idxDt++) {
                                        dtAdjusment = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), (selectedDateFrom.getDate() + idxDt));
                                        boolean adaAdj = false;
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            double tmpValueIdx = (Double) hashAdjusmentForPayInput.get(payInputIdx + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            totalAdjOk = (int) tmpValueIdx;
                                            adaAdj = true;
                                        }
                                        if (dtAdjusment != null && hashAdjusmentForPayInput.containsKey(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"))) {
                                            valueTimeOkAdj = (Double) hashAdjusmentForPayInput.get(payInputTime + "_" + Formater.formatDate(dtAdjusment, "yyyy-MM-dd"));
                                            adaAdj = true;
                                        }
                                        if (adaAdj) {
                                            break;//karena di pay input hanya 1 tgl saja yg di simpan
                                        }

                                    }
                                }
                            }
                        }
                        /**
                         * * @DESC : Total Ok Frequency
                         */
                        rowx.add(""+Formater.formatNumber(totOk + totalAdjOk,format));
                        /**
                         * * @DESC : Total Ok TIME
                         */
                        try {
                            strTotalOk = Formater.formatWorkDayHoursMinutes((float) (valueTimeOkAdj + totTimeOk), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                        } catch (Exception exc) {
                        }
                        rowx.add(strTotalOk == null || strTotalOk.length() == 0 || (strTotalOk != null && strTotalOk.equalsIgnoreCase("0")) ? "-" : String.valueOf(strTotalOk));


                        /* DESC: WORKING DAYS */
                        int totWorkDays = payInputPresence.getTotalWorkingDays();
                        String totTimeWorkDays = "";
                        long fTotTimeWorkDays = payInputPresence.getTimeWorkHour();


                        /**
                         * * @DESC : Total Working days Frequency
                         */
                        rowx.add(""+totWorkDays);
                        /**
                         * * @DESC : Total Working days TIME
                         */
                        try {
                            
                            totTimeWorkDays = Formater.formatWorkDayHoursMinutes((float) fTotTimeWorkDays / (leaveConfig.getHourOneWorkday() * 60f * 60f * 1000f),leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                        } catch (Exception exc) {
                        }
                        rowx.add(totTimeWorkDays == null || totTimeWorkDays.length() == 0 || (totTimeWorkDays != null && totTimeWorkDays.equalsIgnoreCase("0")) ? "-" : String.valueOf(totTimeWorkDays));


                        /* DESC: WORKING DAYS Hours */
                        //int totWorkHours = 0;
                        String totTimeWorkHours = Formater.formatWorkHoursMinutes(payInputPresence.getTimeWorkHour());

                        /**
                         * * @DESC : Total Working days hours TIME
                         */
                        rowx.add(totTimeWorkHours == null || totTimeWorkHours.length() == 0 || (totTimeWorkHours != null && totTimeWorkHours.equalsIgnoreCase("0")) ? "-" : String.valueOf(totTimeWorkHours));

                       
                       ctrlist.drawListRowJsVer2(outJsp, 0, rowx, idxRecord);
                      
                    }//end LOOP
                       result ="";
                    ctrlist.drawListEndTableJsVer2(outJsp);  
                }else{
                    result = "<i>Belum ada data dalam sistem ...</i>";
                }
                
            } catch (Exception ex) {
                System.out.println("Exception export summary Attd" + " Emp:"+empNumTest + " "+ ex);
            }
        return  result;
    }

%>
<%

    Date selectedDateFrom = FRMQueryString.requestDate(request, "check_date_start");
    Date selectedDateTo = FRMQueryString.requestDate(request, "check_date_finish");
    String empNum = FRMQueryString.requestString(request, "emp_number");
    String fullName = FRMQueryString.requestString(request, "full_name");
    String source = FRMQueryString.requestString(request, "source");
    //update by satrya 2012-09-28
    //String status1st = FRMQueryString.requestString(request, "status_schedule1st");
    //String status2nd = FRMQueryString.requestString(request, "status_schedule2nd");         
    SessEmpSchedule sessEmpSchedule = new SessEmpSchedule();
    //FrmPresence frpresence = new FrmPresence();
    int iCommand = FRMQueryString.requestCommand(request);
    int iErrCode = FRMMessage.NONE;
    //update by satrya 2012-07-25
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    //int limitStart = FRMQueryString.requestInt(request, "start");
    int vectSize = 0;
    int start = FRMQueryString.requestInt(request, "start");
    //String  whereClause ="";
    //update by satrya 2013-08-14
    long oidCompany = FRMQueryString.requestLong(request, "company_id");
    long oidDivision = FRMQueryString.requestLong(request, "division_id");

    long oidDepartment = FRMQueryString.requestLong(request, "department");
    long oidSection = FRMQueryString.requestLong(request, "section");
    /*Date date = FRMQueryString.requestDate(request, "date");
     String strDate = FRMQueryString.requestString(request, "datesrc");
     ///deklarasi variable
     int recordToGet = 400;
      
     if (strDate != "") {
     Date datesrc = Formater.formatDate(strDate, "yyyy-MM-dd");
     date = datesrc;
     }
     */
    Vector vct = new Vector(1, 1);

    String wh = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + oidDepartment;
    vct = PstSection.list(0, 0, wh, PstSection.fieldNames[PstSection.FLD_SECTION]);

//out.println(oidDepartment);

    Vector secSelect = new Vector(1, 1);
    Vector secOID = new Vector(1, 1);
    if (vct != null && vct.size() > 0) {
        for (int i = 0; i < vct.size(); i++) {
            Section section = (Section) vct.get(i);
            if (iCommand != Command.NONE && iCommand != Command.ADD) {
                int ix = FRMQueryString.requestInt(request, "chx_" + section.getOID());

                if (ix == 1) {
                    secSelect.add(section);
                    secOID.add("" + section.getOID());
                    //out.println("section.getOID()"+section.getOID());
                }
            } else {
                secSelect.add(section);
            }
        }
    }

    /**
     * Configurasi Attdance Parameter
     */
     Hashtable hashSymbol = new Hashtable();
     Hashtable hasSchedule = new Hashtable();
    Hashtable breakTimeDuration = PstScheduleSymbol.getBreakTimeDuration();
    Vector vSpesialLeaveSymbole = PstSpecialUnpaidLeaveTaken.listSpecialSymbole();
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

        /* int propCheckOvertimeExist=0;
         try{
         propCheckOvertimeExist = Integer.parseInt(PstSystemProperty.getValueByName("ATTANDACE_WHEN_OVERTIME_EXIST"));
            
         }catch(Exception ex){
         System.out.println("Execption ATTANDACE_WHEN_OVERTIME_EXIST: " + ex);
         }*/

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
        String whereClause = " (1=1)";
        if (oidCompany != 0) {
            whereClause = whereClause + " AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + " = " + oidCompany;
        }
        if (oidDivision != 0) {
            whereClause = whereClause + " AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " = " + oidDivision;
        }
        if (oidDepartment != 0) {
            whereClause = whereClause + " AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + oidDepartment;
        }
        if (oidSection != 0) {
            whereClause = whereClause + " AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + oidSection;
        }
        if (selectedDateFrom != null && selectedDateTo != null) {
            whereClause = whereClause + "  AND (( HE." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.YES_RESIGN + " AND " + "HE. " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                    + " BETWEEN \"" + Formater.formatDate(selectedDateFrom, "yyyy-MM-dd  00:00:00") + "\"" + " AND " + "\"" + Formater.formatDate(selectedDateTo, "yyyy-MM-dd  23:59:59") + "\""
                    + " ) OR (HE." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN + "))";
        }
        if (empNum != null && empNum.length() > 0) {
//            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                                 + " LIKE '%"+empNum.trim()+"%'";
            Vector vectName = logicParser(empNum);
            whereClause = whereClause + " AND ";
            if (vectName != null && vectName.size() > 0) {
                //whereClause = whereClause + " AND (";

                whereClause = whereClause + " (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClause = whereClause + " HE." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClause = whereClause + str.trim();
                    }
                }
                whereClause = whereClause + ")";
            }
        }
        if (fullName != null && fullName.length() > 0) {
//            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                                 + " LIKE '%"+fullName.trim()+"%'";
            Vector vectName = logicParser(fullName);
            whereClause = whereClause + " AND ";
            if (vectName != null && vectName.size() > 0) {
                //whereClause = whereClause + " AND (";

                whereClause = whereClause + " (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClause = whereClause + " HE." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClause = whereClause + str.trim();
                    }
                }
                whereClause = whereClause + ")";
            }
        }
        String getListEmployeeId = PstEmployee.getSEmployeeId(0, 0, whereClause, PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]);
        //Vector vctSchIDOff = PstScheduleSymbol.getScheduleId(PstScheduleCategory.CATEGORY_OFF);
        Hashtable vctSchIDOff = PstScheduleSymbol.getHashScheduleId(PstScheduleCategory.CATEGORY_OFF); 
        Hashtable hashSchOff = PstScheduleSymbol.getHashScheduleIdOFF(PstScheduleCategory.CATEGORY_OFF);
        HolidaysTable holidaysTable = PstPublicHolidays.getHolidaysTable(selectedDateFrom, selectedDateTo);
        Hashtable hashPositionLevel = PstPosition.hashGetPositionLevel();
        String whereClausePeriod = "";
        if (selectedDateTo != null && selectedDateFrom != null) {
            whereClausePeriod = "\"" + Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:ss") + "\" >="
                    + PstPeriod.fieldNames[PstPeriod.FLD_START_DATE] + " AND "
                    + PstPeriod.fieldNames[PstPeriod.FLD_END_DATE] + " >= \"" + Formater.formatDate(selectedDateFrom, "yyyy-MM-dd HH:mm:ss") + "\"";
        }

        Hashtable hashPeriod = PstPeriod.hashlistTblPeriod(0, 0, whereClausePeriod, "");
        HashTblOvertimeDetail hashTblOvertimeDetail = PstOvertimeDetail.HashOvertimeOverlapVer2(0, 0, 0, getListEmployeeId, selectedDateFrom, selectedDateTo, "");
        Hashtable hashReason = new Hashtable();
    Hashtable hashPersonalInOut = new Hashtable(); 
    Vector vReason = PstReason.listReason(0, 0, PstReason.fieldNames[PstReason.FLD_REASON_TIME] + "=" + PstReason.REASON_TIME_YES, ""); 
     Vector listEmployee = new Vector();
     Vector listAttdAbsensi= new Vector();
Hashtable listTakenLeave= new Hashtable(); 
Hashtable listTotSUmLeaveSpecial= new Hashtable(); 
Hashtable hasDfltScheduleTbl= new Hashtable(); 
Hashtable hasDfltSchedule= new Hashtable(); 
Hashtable hashTblSchedule = new Hashtable(); 
 Hashtable sumOvertimeDailyAllowanceMoney= new Hashtable();
Hashtable sumOvertimeDailyAllowanceFood= new Hashtable();
Hashtable sumOvertimeDailyPaidByDayOff= new Hashtable();
Hashtable sumOvertimeDailyPaidBySalary= new Hashtable();
Hashtable hashAdjusmentForPaySlip= new Hashtable();
Hashtable hashAdjusmentForPayInput= new Hashtable();
TmpListParamAttdSummary tmpListParamAttdSummary = new TmpListParamAttdSummary(); 
    
    
    
    if(iCommand ==Command.LIST){
    
    String orderPresence = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + "," + PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + "," + PstPresence.fieldNames[PstPresence.FLD_SCHEDULE_DATETIME];
     String whereClausePresence = "(1=1)";
        if (oidCompany != 0) {
            whereClausePresence = whereClausePresence + " AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID] + " = " + oidCompany;
        }
        if (oidDivision != 0) {
            whereClausePresence = whereClausePresence + " AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + " = " + oidDivision;
        }
        if (oidDepartment != 0) {
            whereClausePresence = whereClausePresence + " AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + oidDepartment;
        }
        if (oidSection != 0) {
            whereClausePresence = whereClausePresence + " AND HE." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + " = " + oidSection;
        }
        if (selectedDateFrom != null && selectedDateTo != null) {
            //update by satrya 2012-10-15 
            if (selectedDateFrom.getTime() > selectedDateTo.getTime()) {
                Date tempFromDate = selectedDateFrom;
                Date tempToDate = selectedDateTo;
                selectedDateFrom = tempToDate;
                selectedDateTo = tempFromDate;
            }

            whereClausePresence = whereClausePresence + " AND PS." + PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] + " BETWEEN \"" + Formater.formatDate(selectedDateFrom, "yyyy-MM-dd HH:mm:00") + "\" AND \"" + Formater.formatDate(selectedDateTo, "yyyy-MM-dd HH:mm:59") + "\"";

            whereClausePresence = whereClausePresence + "  AND (( HE." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] 
                    + " = " + PstEmployee.YES_RESIGN + " AND " + "HE. " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE]
                    + " BETWEEN \"" + Formater.formatDate(selectedDateFrom, "yyyy-MM-dd  00:00:00") + "\"" + " AND " + "\"" + Formater.formatDate(selectedDateTo, "yyyy-MM-dd  23:59:59") + "\""
                    + " ) OR (HE." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                    + " = " + PstEmployee.NO_RESIGN + "))";
        }
        if (empNum != null && empNum.length() > 0) {
//            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " emp."+PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
//                                 + " LIKE '%"+empNum.trim()+"%'";
            Vector vectName = logicParser(empNum);
            whereClausePresence = whereClausePresence + " AND ";
            if (vectName != null && vectName.size() > 0) {
                //whereClause = whereClause + " AND (";

                whereClausePresence = whereClausePresence + " (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClausePresence = whereClausePresence + " HE." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClausePresence = whereClausePresence + str.trim();
                    }
                }
                whereClausePresence = whereClausePresence + ")";
            }
        }
        if (fullName != null && fullName.length() > 0) {
//            whereClauseEmpTime = whereClauseEmpTime + " AND "+ " emp."+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
//                                 + " LIKE '%"+fullName.trim()+"%'";
            Vector vectName = logicParser(fullName);
            whereClausePresence = whereClausePresence + " AND ";
            if (vectName != null && vectName.size() > 0) {
                //whereClause = whereClause + " AND (";

                whereClausePresence = whereClausePresence + " (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClausePresence = whereClausePresence + " HE." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClausePresence = whereClausePresence + str.trim();
                    }
                }
                whereClausePresence = whereClausePresence + ")";
            }
        }
        if (vReason != null && vReason.size() > 0) {
             Vector listPresencePersonalInOut = PstPresence.listPresenceForSummaryAttd(orderPresence, whereClausePresence + " AND PS." + PstPresence.fieldNames[PstPresence.FLD_STATUS] + " NOT IN(" + Presence.STATUS_IN + "," + Presence.STATUS_OUT + "," + Presence.STATUS_INVALID + ")", null);
            for (int idxReason = 0; idxReason < vReason.size(); idxReason++) {
                Reason reason = (Reason) vReason.get(idxReason);
                Hashtable vres = PstEmpSchedule.getListAttendaceReason(selectedDateFrom, selectedDateTo, whereClause, reason.getNo(), -1, hashPeriod);
                //Vector listPresencePersonalInOut = PstPresence.list(0, 0,PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] +","+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] +","+PstPresence.fieldNames[PstPresence.FLD_SCHEDULE_DATETIME], oidDepartment, fullName.trim(), selectedDateFrom, selectedDateTo, oidSection, empNum.trim(), null); 
                //update by satrya 2013-08-14
               
                hashReason.put(reason.getNo(), vres);
                hashPersonalInOut.put(reason.getNo(), listPresencePersonalInOut);
            }
        }
     //Vector listAttdAbsensi = PstEmpSchedule.getListAttendace(attdConfig, leaveConfig, selectedDateFrom, selectedDateTo, getListEmployeeId, vctSchIDOff, hashSchOff, iPropInsentifLevel, holidaysTable, hashPositionLevel, payrollCalculatorConfig, hashPeriod, hashTblOvertimeDetail);
     long strMl = System.currentTimeMillis();
     listAttdAbsensi = PstEmpSchedule.getListAttendaceNoReason(attdConfig,leaveConfig,selectedDateFrom, selectedDateTo, getListEmployeeId, vctSchIDOff, hashSchOff, iPropInsentifLevel, holidaysTable, hashPositionLevel, payrollCalculatorConfig,hashPeriod,hashTblOvertimeDetail);
     System.out.println("Durations >>>>> jsp" + (System.currentTimeMillis() - strMl));
     listEmployee = SessPresence.employeeAttendance(oidCompany, oidDivision, oidDepartment, oidSection, empNum, fullName, selectedDateFrom, selectedDateTo);
     listTakenLeave = SessLeaveApp.checkLeaveTaken(getListEmployeeId, selectedDateFrom, selectedDateTo); 

         hashAdjusmentForPayInput = PstPayInput.listPayInput(selectedDateFrom, selectedDateTo, getListEmployeeId);
     hashAdjusmentForPaySlip = PstPaySlip.listPaySlip(selectedDateFrom, selectedDateTo, getListEmployeeId);
        
        
     sumOvertimeDailyPaidBySalary = PstOvertimeDetail.summaryOT(oidCompany, oidDivision, oidDepartment, selectedDateFrom, selectedDateTo, oidSection, empNum, fullName, OvertimeDetail.PAID_BY_SALARY, -1, -1, -1, 0);
     sumOvertimeDailyPaidByDayOff = PstOvertimeDetail.summaryOT(oidCompany, oidDivision, oidDepartment, selectedDateFrom, selectedDateTo, oidSection, empNum, fullName, -1, OvertimeDetail.PAID_BY_DAY_OFF, -1, -1, 0);
     sumOvertimeDailyAllowanceFood = PstOvertimeDetail.summaryOT(oidCompany, oidDivision, oidDepartment, selectedDateFrom, selectedDateTo, oidSection, empNum, fullName, -1, -1, Overtime.ALLOWANCE_FOOD, -1, 0);
     sumOvertimeDailyAllowanceMoney = PstOvertimeDetail.summaryOT(oidCompany, oidDivision, oidDepartment, selectedDateFrom, selectedDateTo, oidSection, empNum, fullName, -1, -1, -1, Overtime.ALLOWANCE_MONEY, 0);
        // HolidaysTable holidaysTable = PstPublicHolidays.getHolidaysTable(selectedDateFrom, selectedDateTo);
     hashTblSchedule = PstScheduleSymbol.getHashTblSchedule(0, 0, "", ""); 
        SrcSpecialLeaveSummaryAttd srcSpecialLeaveSummaryAttd = new SrcSpecialLeaveSummaryAttd(); 
        srcSpecialLeaveSummaryAttd.setEmpNum(empNum);
        srcSpecialLeaveSummaryAttd.setEmpName(fullName);
        srcSpecialLeaveSummaryAttd.setEmpCompanyId(oidCompany);
        srcSpecialLeaveSummaryAttd.setEmpDivisionId(oidDivision);
        srcSpecialLeaveSummaryAttd.setEmpDeptId(oidDepartment);
        srcSpecialLeaveSummaryAttd.setEmpSection(oidSection);
        listTotSUmLeaveSpecial = PstSpecialUnpaidLeaveTaken.listTotTakenBySrcLeaveManagemnt(srcSpecialLeaveSummaryAttd, selectedDateFrom, selectedDateTo);
        hasDfltScheduleTbl = PstDefaultSchedule.getHashTblDfltSch();
        hasDfltSchedule = new Hashtable(); 
        if (selectedDateFrom != null && selectedDateTo != null) {

            if (selectedDateFrom.getTime() > selectedDateTo.getTime()) {
                Date tempFromDate = selectedDateFrom;
                Date tempToDate = selectedDateTo;
                selectedDateFrom = tempToDate;
                selectedDateTo = tempFromDate;
            }
            long diffStartToFinish = selectedDateTo.getTime() - selectedDateFrom.getTime();
            int itDate = Integer.parseInt(String.valueOf(diffStartToFinish / 86400000));

            for (int i = 0; i <= itDate; i++) {
                Date selectedDate = new Date(selectedDateFrom.getTime() + i * 1000L * 60 * 60 * 24);
                Calendar cals = Calendar.getInstance();
                cals.setTime(selectedDate);
                hasDfltSchedule.put(cals.get(Calendar.DAY_OF_WEEK), hasDfltScheduleTbl);//hasDfltSchedule.addHashDfltSch(hasDfltScheduleTbl, selectedDate); 
            }
        }
    
        
        
   
    Vector listAttdAbsensiClone = new Vector(listAttdAbsensi);
    Hashtable HashtableClone = new Hashtable(hashReason);
    Hashtable hashPersonalInOutClone = new Hashtable(hashPersonalInOut);
    Vector vReasonClone = new Vector(vReason);
    
    Hashtable listTakenLeaveClone = new Hashtable(listTakenLeave);
    
    Hashtable listTotSUmLeaveSpecialClone = new Hashtable(listTotSUmLeaveSpecial); 
    
  
    tmpListParamAttdSummary.setHashReason(HashtableClone);
    tmpListParamAttdSummary.setHashPersonalInOut(hashPersonalInOutClone);
    tmpListParamAttdSummary.setListAttdAbsensi(listAttdAbsensiClone);
    tmpListParamAttdSummary.setListEmployee(listEmployee);
    tmpListParamAttdSummary.setvReason(vReasonClone);
    
    tmpListParamAttdSummary.setListTakenLeave(listTakenLeaveClone);
    tmpListParamAttdSummary.setListTotSUmLeaveSpecial(listTotSUmLeaveSpecialClone);
    
    
    tmpListParamAttdSummary.setHashAdjusmentForPayInput(hashAdjusmentForPayInput);
    tmpListParamAttdSummary.setHashAdjusmentForPaySlip(hashAdjusmentForPaySlip);
    tmpListParamAttdSummary.setSumOvertimeDailyPaidBySalary(sumOvertimeDailyPaidBySalary);
    
    tmpListParamAttdSummary.setSumOvertimeDailyPaidByDayOff(sumOvertimeDailyPaidByDayOff);
    tmpListParamAttdSummary.setSumOvertimeDailyAllowanceFood(sumOvertimeDailyAllowanceFood);
    tmpListParamAttdSummary.setSumOvertimeDailyAllowanceMoney(sumOvertimeDailyAllowanceMoney);
    tmpListParamAttdSummary.setHashTblSchedule(hashTblSchedule);
   
    //tmpListParamAttdSummary.getParameterAll(tmpListParamAttdSummary);
    //tmpListParamAttdSummary.setAllQuery(tmpListParamAttdSummary);
      if (session.getValue("LIST_ATTANDACE") != null) {
          session.removeValue("LIST_ATTANDACE");
    }
    
    
    session.putValue("LIST_ATTANDACE", tmpListParamAttdSummary);
   // session.putValue("LIST_ATTANDACE", tmpListParamAttdSummary);
     if(1==1){
         boolean test = true;
     }
    }
   
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html> 
   
    <head> 
     
        <title>HARISMA - Summary Attendance</title>
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
                    //document.all.aSearch.focus();
                    cmdView();
                }
            }

            function cmdUpdateDiv(){
                document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frpresence.action="summary_attendance_sheet.jsp";
                document.frpresence.submit();
            }
            function cmdUpdateDep(){
                document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frpresence.action="summary_attendance_sheet.jsp";
                document.frpresence.submit();
            }
            function cmdUpdatePos(){
                document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frpresence.action="summary_attendance_sheet.jsp";
                document.frpresence.submit();
            }
            function cmdExport(){ 
                //var linkPage = "<//%=printroot%>.report.attendance.AttendanceSummaryXls";
                document.frpresence.action="<%=printroot%>.report.attendance.AttendanceSummaryXls"; 
                document.frpresence.target = "SummaryAttendances";
                document.frpresence.submit();
                document.frpresence.target = "";
                
            }
            function cmdSearch(){
                document.frpresence.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frpresence.action="summary_attendance_sheet.jsp";
                document.frpresence.submit();
                
            }
            function cmdExportExcel(){
               //var printWindow = window.open("about:blank","sumAttd","status=no,toolbar=no,menubar=no,resizable=yes,scrollbars=yes,location=no");
               //             window.focus();
                //            document.frpresence.command.value="<%=Command.VIEW%>";
                            //alert("1");
               //             document.frpresence.source.value="sum_attd";
                            //alert("2");
              //              document.frpresence.action="summary_attendance_sheet.jsp";
                            //alert("3");                            
              //              document.frpresence.target= "sumAttd";
                            //alert("4");
                            
             //               document.frpresence.submit();                                                                                        
              //              document.frpresence.target= "";
             //               cmdExport();
             //               printWindow.close();
                           
                            
                            
                //var linkPage = "<//%=printroot%>.report.attendance.AttendanceSummaryXls";
                document.frpresence.action="<%=printroot%>.report.attendance.AttendanceSummaryXls"; 
                document.frpresence.target = "SummaryAttendance";
                document.frpresence.submit();
                document.frpresence.target = "";
            }
       
            //-------------- script control line -------------------
            function MM_swapImgRestore() { //v3.0
                var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
            }

            function MM_preloadImages() { //v3.0
                var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
                }

                function MM_findObj(n, d) { //v4.0
                    var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                        d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                    if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
                    for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                    if(!x && document.getElementById) x=document.getElementById(n); return x;
                }

                function MM_swapImage() { //v3.0
                    var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                        if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
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
                                        <td height="20"> <font color="#FF6600" face="Arial"><strong>  Report &gt; Summary 
                                                &gt; Attendance  </strong></font> </td>
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
                                                                                    <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                       
                                                                                        <tr>
                                                                                            <td width="6%" nowrap="nowrap"> <div align="left">Payrol Num </div></td>
                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                <input type="text" size="40" name="emp_number"  value="<%= sessEmpSchedule != null && sessEmpSchedule.getEmpNum() != null ? sessEmpSchedule.getEmpNum() : empNum%>" title="You can Input Payroll Number more than one, ex-sample : 1111,2222" class="elemenForm" onKeyDown="javascript:fnTrapKD()"> </td>

                                                                                            <td width="5%" nowrap="nowrap"> Full Name </td>
                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                <input type="text" size="50" name="full_name"  value="<%= sessEmpSchedule != null && sessEmpSchedule.getEmpFullName() != null ? sessEmpSchedule.getEmpFullName() : fullName%>" title="You can Input Full Name more than one, ex-sample : saya,kamu" class="elemenForm" onKeyDown="javascript:fnTrapKD()"> </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td width="6%" nowrap="nowrap"> <div align="left">Company </div></td>
                                                                                            <td width="30%" nowrap="nowrap">:

                                                                                                <% /* update by satrya 2014-01-20 Vector comp_value = new Vector(1, 1);
                                                                                                     Vector comp_key = new Vector(1, 1);
                                                                                                     String whereCompany="";
                                                                                                     if (!(isHRDLogin || isEdpLogin || isGeneralManager || isDirector)){
                                                                                                     whereCompany = PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]+"='"+ emplx.getCompanyId()+"'";
                                                                                                     } else{
                                                                                                     comp_value.add("0");
                                                                                                     comp_key.add("select ...");                                            
                                                                                                     }
                                                                                                     Vector listComp = PstCompany.list(0, 0, whereCompany, " COMPANY ");
                                                                                                     for (int i = 0; i < listComp.size(); i++) {
                                                                                                     Company comp = (Company) listComp.get(i);
                                                                                                     comp_key.add(comp.getCompany());
                                                                                                     comp_value.add(String.valueOf(comp.getOID()));
                                                                                                     }
                                                                                                     //update by satrya 2013-08-13
                                                                                                     //jika user memilih select kembali
                                                                                                     if(oidCompany==0){
                                                                                                     oidDivision =0;oidDepartment=0;oidSection=0; 
                                                                                                     }*/
                                                                                                %> <%/*= update by satrya 2014-01-20 ControlCombo.draw("company_id", "formElemen", null, "" + oidCompany, comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")*/%>  
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
                                                                                                                            if (oidCompany != 0) {
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
                                                                                                                                if (oidCompany != 0) {
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
                                                                                                                    if (oidCompany != 0) {
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
                                                                                                <%= ControlCombo.draw("company_id", "formElemen", null, "" + oidCompany, comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%>
                                                                                            </td>

                                                                                            <td width="5%" nowrap="nowrap"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                <%/*  // update by satrya 2014-01-20 
                                                                                                     Vector div_value = new Vector(1, 1);
                                                                                                     Vector div_key = new Vector(1, 1);
                                                                                                     String whereDivision ="";
                                                                                                     if (!(isHRDLogin || isEdpLogin || isGeneralManager || isDirector)){
                                                                                                     whereDivision = PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+"='"+ emplx.getDivisionId()+"'";
                                                                                                     oidDivision = emplx.getDivisionId();
                                                                                                     } else{
                                                                                                     div_value.add("0");
                                                                                                     div_key.add("select ...");                                            
                                                                                                     }
                                                                                                     Vector listDiv = PstDivision.list(0, 0, whereDivision, " DIVISION ");
                                                                                                     for (int i = 0; i < listDiv.size(); i++) {
                                                                                                     Division div = (Division) listDiv.get(i);
                                                                                                     div_key.add(div.getDivision());
                                                                                                     div_value.add(String.valueOf(div.getOID()));
                                                                                                     }
                                                                                                     //update by satrya 2013-08-13
                                                                                                     //jika user memilih select kembali
                                                                                                     if(oidDivision==0){
                                                                                                     oidDepartment=0;
                                                                                                     }*/
                                                                                                %> <%/*= update by satrya 2014-01-20 ControlCombo.draw("division_id", "formElemen", null, "" + oidDivision, div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"")*/%>
                                                                                                <%

                                                                                                    //update by satrya 2013-08-13
                                                                                                    //jika user memilih select kembali
                                                                                                    if (oidCompany == 0) {
                                                                                                        oidDivision = 0;
                                                                                                    }

                                                                                                    if (oidCompany != 0) {
                                                                                                        whereComp = "(" + (whereComp != null && whereComp.length() == 0 ? "1=1" : whereComp) + ") AND " + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + "=" + oidCompany;
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
                                                                                                                    if (oidDivision != 0) {
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
                                                                                                                    chkAdaDpt = "(" + (whereComp != null && whereComp.length() == 0 ? "1=1" : whereComp) + ") AND d." + PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + "=" + oidDivision;
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

                                                                                                                                oidDivision = tmpFirstDiv;

                                                                                                                            }
                                                                                                                        }
                                                                                                                    } else {
                                                                                                                        oidDivision = tmpFirstDiv;

                                                                                                                    }

                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                %>
                                                                                                <%= ControlCombo.draw("division_id", "formElemen", null, "" + oidDivision, div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"")%> 
                                                                                            </td>
                                                                                        </tr>


                                                                                        <tr> 
                                                                                            <td width="6%" align="right" nowrap> 
                                                                                                <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>                                                                                            </td>
                                                                                            <td width="30%" nowrap="nowrap"> : 
                                                                                                <%

                                                                                                    /*  Vector dept_value = new Vector(1, 1);
                                                                                                     Vector dept_key = new Vector(1, 1);
                                                                                                     //Vector listDept = new Vector(1, 1);
                                                                                                     DepartmentIDnNameList keyList = new DepartmentIDnNameList();

                                                                                                     if (processDependOnUserDept) {
                                                                                                     if (emplx.getOID() > 0) {
                                                                                                     if (isHRDLogin || isEdpLogin || isGeneralManager) {
                                                                                                     keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                     //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                     } else {
                                                                                                     Position position = null;
                                                                                                     try {
                                                                                                     position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                     } catch (Exception exc) {
                                                                                                     }
                                                                                                     if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                                                                     String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + emplx.getDivisionId() + "";
                                                                                                     keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereDiv, true);
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
                                                                                                     keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, false);
                                                                                                     //listDept = PstDepartment.list(0, 0,whereClsDep, "");
                                                                                                     }
                                                                                                     }
                                                                                                     } else {
                                                                                                     //dept_value.add("0");
                                                                                                     //dept_key.add("select ...");
                                                                                                     keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                     //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                     }
                                                                                                     } else {
                                                                                                     //dept_value.add("0");
                                                                                                     //dept_key.add("select ...");
                                                                                                     keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                     //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                     }
                                                                                                     dept_value = keyList.getDepIDs();
                                                                                                     dept_key = keyList.getDepNames();

                                                                                                     /*for (int i = 0; i < listDept.size(); i++) {
                                                                                                     Department dept = (Department) listDept.get(i);
                                                                                                     dept_key.add(dept.getDepartment());
                                                                                                     dept_value.add(String.valueOf(dept.getOID()));
                                                                                                     } */


                                                                                                    /*   String selectValueDepartment = "" + oidDepartment;//+objSrcLeaveApp.getDepartmentId();

                                                                                                     */%>
                                                                                                <%//=ControlCombo.draw("department", "elementForm", null, selectValueDepartment, dept_value, dept_key, " onkeydown=\"javascript:fnTrapKD()\"")%>

                                                                                                <%/*=ControlCombo.draw("department", "elementForm", null, selectValueDepartment, dept_value, dept_key, " onChange=\"javascript:cmdUpdateDep()\"")*/%>
                                                                                                <%
                                                                                                    dept_value = new Vector(1, 1);
                                                                                                    dept_key = new Vector(1, 1);
                                                                                                    //dept_value.add("0");
                                                                                                    //dept_key.add("select ...");
                                                                                                    //String strWhere = PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision;
                                                                                                    Vector listDept = new Vector(); //PstDepartment.list(0, 0, strWhere, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                                            /*for (int i = 0; i < listDept.size(); i++) {
                                                                                                     Department dept = (Department) listDept.get(i);
                                                                                                     dept_key.add(dept.getDepartment());
                                                                                                     dept_value.add(String.valueOf(dept.getOID()));
                                                                                                     }*/

                                                                                                    if (processDependOnUserDept) {
                                                                                                        if (emplx.getOID() > 0) {

                                                                                                            if (isHRDLogin || isEdpLogin || isGeneralManager || isDirector) {
                                                                                                                String strWhere = PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision;
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
                                                                                                                        + "AND hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision + ") OR "
                                                                                                                        + "(hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + "=" + departmentOid + "))";

                                                                                                                if (position.getOID() != 0 && position.getDisabedAppDivisionScope() == 0) {
                                                                                                                    whereClsDep = " ( hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision + ") ";
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
                                                                                                                    whereClsDep = " (" + whereClsDep + ") AND hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision;
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
                                                                                                            listDept = PstDepartment.list(0, 0, (PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision), "DEPARTMENT");
                                                                                                        }
                                                                                                    } else {
                                                                                                        dept_value.add("0");
                                                                                                        dept_key.add("select ...");
                                                                                                        listDept = PstDepartment.list(0, 0, (PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision), "DEPARTMENT");
                                                                                                    }

                                                                                                    for (int i = 0; i < listDept.size(); i++) {
                                                                                                        Department dept = (Department) listDept.get(i);
                                                                                                        dept_key.add(dept.getDepartment());
                                                                                                        dept_value.add(String.valueOf(dept.getOID()));
                                                                                                    }


                                                                                                    //update by satrya 2013-08-13
                                                                                                    //jika user memilih select kembali
                                                                                                    if (oidDepartment == 0) {
                                                                                                        oidSection = 0;
                                                                                                    }
                                                                                                %> <%= ControlCombo.draw("department", "formElemen", null, "" + oidDepartment, dept_value, dept_key, "onChange=\"javascript:cmdUpdatePos()\"")%>
                                                                                            </td>
                                                                                            <td width="5%" align="left" nowrap valign="top"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                                                            <td width="59%" nowrap="nowrap">:
                                                                                                <%
                                                                                                    /*
                                                                                                     Vector sec_value = new Vector(1, 1);
                                                                                                     Vector sec_key = new Vector(1, 1);
                                                                                                     sec_value.add("0");
                                                                                                     sec_key.add("select ...");

                                                                                                     //String sWhereClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + sSelectedDepartment;                                                       
                                                                                                     //Vector listSec = PstSection.list(0, 0, sWhereClause, " SECTION ");
                                                                                                     String secWhere = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + oidDepartment;
                                                                                                     Vector listSec = PstSection.list(0, 0, secWhere, " SECTION ");
                                                                                                     for (int i = 0; i < listSec.size(); i++) {
                                                                                                     Section sec = (Section) listSec.get(i);
                                                                                                     sec_key.add(sec.getSection());
                                                                                                     sec_value.add(String.valueOf(sec.getOID()));
                                                                                                     }*/
                                                                                                %>
                                                                                                <%/*=ControlCombo.draw("section", null, "" + oidSection, sec_value, sec_key)*/%>     
                                                                                                <%

                                                                                                    Vector sec_value = new Vector(1, 1);
                                                                                                    Vector sec_key = new Vector(1, 1);
                                                                                                    sec_value.add("0");
                                                                                                    sec_key.add("select ...");

                                                                                                    //String sWhereClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + sSelectedDepartment;                                                       
                                                                                                    //Vector listSec = PstSection.list(0, 0, sWhereClause, " SECTION ");
                                                                                                    String secWhere = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + oidDepartment;
                                                                                                    Vector listSec = PstSection.list(0, 0, secWhere, " SECTION ");
                                                                                                    for (int i = 0; i < listSec.size(); i++) {
                                                                                                        Section sec = (Section) listSec.get(i);
                                                                                                        sec_key.add(sec.getSection());
                                                                                                        sec_value.add(String.valueOf(sec.getOID()));
                                                                                                    }
                                                                                                %>
                                                                                                <%=ControlCombo.draw("section", null, "" + oidSection, sec_value, sec_key)%>
                                                                                            </td>

                                                                                        </tr>

                                                                                        <tr> 
                                                                                            <td width="6%" align="right" nowrap> <div align=left>Date</div>                                                                                             </td>
                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                                
                                                                                                <%
                                                                                                    //String selectDateStart = "" + selectedDateFrom; 
                                                                                                    //String selectDateFinish 
%>
                                                                                                <%=ControlDate.drawDateWithStyle("check_date_start", selectedDateFrom != null ? selectedDateFrom : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%> 
                                                                                                to <%=ControlDate.drawDateWithStyle("check_date_finish", selectedDateTo != null ? selectedDateTo : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>                                                                                            </td>
                                                                                                <% //out.println("date "+date);
                                                                                                    //long periodId = PstPeriod.getPeriodIdBySelectedDate(date); 

                                                                                                %>
                                                                                           
                                                                                            <%

                                                                                                /* Vector status_value = new Vector(1, 1);
                                                                                                 Vector status_key = new Vector(1, 1);
                                                                                                                                                                          
                                                                                                 for (int i = 0; i < PstEmpSchedule.strPresenceStatus.length ; i++) { 
                                                                                                 status_value.add(""+i);
                                                                                                 status_key.add(PstEmpSchedule.strPresenceStatus[i]);
                                                                                                 }*/
                                                                                            %>
                                                                                            <%//=ControlCombo.draw("status_schedule1st","-select-", status1st, status_value, status_key)%>                                                                                            </td>

                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td height="13" width="0%">&nbsp;</td>
                                                                                            <td width="39%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a>
                                                                                           <img src="<%=approot%>/images/spacer.gif" width="6" height="1">
                                                                                          <a href="javascript:cmdSearch()">Search  for Employee</a></td>  
                                                                                        </tr>
                                                                                        <%if(iCommand==Command.LIST){%> 
                                                                                        <tr>
                                                                                            <td colspan="7"><%=drawList(out,selectedDateFrom,selectedDateTo,vSpesialLeaveSymbole,vReason,listEmployee,listTakenLeave,listAttdAbsensi,listTotSUmLeaveSpecial,hashReason,hashPersonalInOut,hasSchedule,hashTblSchedule,hashAdjusmentForPayInput,sumOvertimeDailyPaidBySalary,hashAdjusmentForPaySlip,sumOvertimeDailyPaidByDayOff,sumOvertimeDailyAllowanceMoney,sumOvertimeDailyAllowanceFood,hasDfltSchedule,tmpListParamAttdSummary)%></td>  
                                                                                        </tr>
                                                                                       
                                                                                        <tr> 
                                                                                            <td width="6%" nowrap> 
                                                                                                <div align="left"></div></td>
                                                                                            <td width="30%"> 
                                                                                                <table border="0" cellspacing="0" cellpadding="0" width="137">
                                                                                                    <tr> 
                                                                                                        <td width="16"><a href="javascript:cmdExportExcel()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/excel.png',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/excel.png" width="24" height="24" alt="Export Excel"></a></td>
                                                                                                        <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                                                                        <td width="94" class="command" nowrap><a href="javascript:cmdExportExcel()">Export Excel</a></td>
                                                                                                    </tr>
                                                                                                </table></td>
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
                int freesize=4;
                
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