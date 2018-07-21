<%-- 
    Document   : listArap_xls
    Created on : 21-Apr-2015, 12:08:23
    Author     : GUSWIK
--%>

<%@page import="com.dimata.harisma.form.arap.CtrlArApMain"%>
<%@page import="com.dimata.harisma.entity.arap.ArApMain"%>
<%@page import="com.dimata.harisma.entity.arap.ArApEmpDeduction"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.form.arap.FrmArApMain"%>
<%@page import="com.dimata.harisma.entity.arap.PstArApMain"%>
<%-- 
    Document   : export_excel_List_Benefit_deduction.jsp
    Created on : Dec 31, 2014, 10:42:46 AM
    Author     : Priska
--%>


<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayComponent"%>
<%@page import="com.dimata.harisma.report.payroll.ListBenefitDeduction"%>
<%@page import="com.dimata.harisma.entity.payroll.PayEmpLevel"%>
<%@page import="com.dimata.harisma.entity.payroll.PayBanks"%>
<%@page import="com.dimata.harisma.entity.payroll.PayComponenttemp"%>
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

<%!    public String drawList(Vector objectClass) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("80%");
        ctrlist.setBorder(1);
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No", "10%");
        ctrlist.addHeader("Emp Number", "20%");
        ctrlist.addHeader("Name", "20%");
        ctrlist.addHeader("Department", "10%");
        ctrlist.addHeader("Amount", "10%");
        ctrlist.addHeader("Description", "50%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;

         DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                        dfs.setCurrencySymbol("");
                        dfs.setMonetaryDecimalSeparator(',');
                        dfs.setGroupingSeparator('.');
                        df.setDecimalFormatSymbols(dfs);
        for (int i = 0; i < objectClass.size(); i++) {
            ArApEmpDeduction arApEmpDeduction = (ArApEmpDeduction) objectClass.get(i);
            Vector rowx = new Vector();
            
            rowx.add(""+(i+1));
            rowx.add("'"+arApEmpDeduction.getEmpNum());
            rowx.add(""+arApEmpDeduction.getEmpName());
            rowx.add(""+arApEmpDeduction.getDepartment());
            rowx.add(arApEmpDeduction.getAmount()!=0?"Rp. " + df.format(arApEmpDeduction.getAmount()) : "-");
            rowx.add(arApEmpDeduction.getDescription());
            
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(arApEmpDeduction.getArapMainId()));
        }
        return ctrlist.draw(index);
    }

%>

<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidArapMain = FRMQueryString.requestLong(request, "hidden_arapMain_id");
            
            long periodeId = FRMQueryString.requestLong(request, "periodId");
            long companyId = FRMQueryString.requestLong(request, FrmArApMain.fieldNames[FrmArApMain.FRM_COMPANY_ID]);
            long deductionComp = FRMQueryString.requestLong(request, FrmArApMain.fieldNames[FrmArApMain.FRM_COMPONENT_DEDUCTION_ID]);
            
            I_Atendance attdConfig = null;
    try {
        attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
    } catch (Exception e) {
        System.out.println("Exception : " + e.getMessage());
        System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
    }
            /*variable declaration*/
            int recordToGet = 50;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            PayPeriod payPeriod2 = new PayPeriod();
            try{
                payPeriod2 = PstPayPeriod.fetchExc(periodeId);
            } catch (Exception e){
                System.out.printf("period null");
            }
            
            String whereClause = "  hai.`arap_item_status` = 0 AND he.COMPANY_ID = " + companyId ;
            String whereDeduct = "  he.COMPANY_ID = " + companyId ;
            if (deductionComp > 0 ){
                whereClause = whereClause + " AND ham.`component_deduction_id` = " + deductionComp ;
            }
            if (periodeId > 0){
            whereClause = whereClause + " AND (hai.DUE_DATE BETWEEN \"" + Formater.formatDate(payPeriod2.getStartDate(), "yyyy-MM-dd HH:mm:ss") + "\" AND \"" + Formater.formatDate(payPeriod2.getEndDate(), "yyyy-MM-dd 23:59:59") + "\" )";
            //whereDeduct = whereDeduct + " AND (hai.DUE_DATE BETWEEN \"" + Formater.formatDate(payPeriod2.getStartDate(), "yyyy-MM-dd HH:mm:ss") + "\" AND \"" + Formater.formatDate(payPeriod2.getEndDate(), "yyyy-MM-dd 23:59:59") + "\" )";
            
            }
            String orderClause = "";

            CtrlArApMain ctrlArApMain = new CtrlArApMain(request);
            ControlLine ctrLine = new ControlLine();
            Vector listArApMain = new Vector(1, 1);
           
           if(session.getValue("listItem")!=null){
                listArApMain = (Vector)session.getValue("listItem"); 
                //rekapitulasiAbsensi.setWhereClauseEMployee("");
            }
%>


<%@page contentType="application/x-msexcel" pageEncoding="UTF-8" %>

<html> 
    <head>
        <title>HARISMA - Rekapitulasi Absensi</title>
    </head>

    <body >
       
        <table>
           <%if (iCommand == 0) {%> 
                            <tr>
                                
                                <td>
                                    <table>
                                      
                                        <tr>
                                            <td style="font-size: large"><b>Arap</td>
                                        </tr>
                                        <tr>
                                            <td style="font-size: large"><b></b></td>
                                        </tr>
                                      
                                        <tr>
                                            <%=drawList(listArApMain)%>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
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