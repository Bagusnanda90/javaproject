<%-- 
    Document   : src_list_benefit_deduction_department
    Created on : 26-Feb-2016, 04:00:08
    Author     : GUSWIK
--%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@ page language="java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<%@ page import = "java.util.Date" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ page import = "com.dimata.printman.*" %>
<%@ page import = "com.dimata.harisma.printout.*" %>
<%@ page import = "com.dimata.harisma.printout.PayPrintText" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_PROCESS_PRINT);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    CtrlPaySlipComp ctrlPaySlipComp = new CtrlPaySlipComp(request);
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int reportType = FRMQueryString.requestInt(request, "reportType");

    long oidCompany = FRMQueryString.requestLong(request, "oidCompany");
    long oidDivision = FRMQueryString.requestLong(request, "division");
    long oidDepartment = FRMQueryString.requestLong(request, "department");
    long oidSection = FRMQueryString.requestLong(request, "section");
    long periodeId = FRMQueryString.requestLong(request, "periodId");

    int inclResign = FRMQueryString.requestInt(request, "INCLUDE_RESIGN");
    boolean bIncResign = (inclResign == 1 ? true : false);
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 
        <title>Report</title>
        <style type="text/css">
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px; font-weight: bold;}
            #menu_teks {color:#CCC;}
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 14px; background-color: #F5F5F5;}
            #btn {
                background: #C7C7C7;
                border: 1px solid #BBBBBB;
                border-radius: 3px;
                font-family: Arial;
                color: #474747;
                font-size: 11px;
                padding: 3px 7px;
                cursor: pointer;
            }

            #btn:hover {
                color: #FFF;
                background: #B3B3B3;
                border: 1px solid #979797;
            }
            #btn1 {
                background: #f27979;
                border: 1px solid #d74e4e;
                border-radius: 3px;
                font-family: Arial;
                color: #ffffff;
                font-size: 12px;
                padding: 3px 9px 3px 9px;
            }

            #btn1:hover {
                background: #d22a2a;
                border: 1px solid #c31b1b;
            }
            #tdForm {
                padding: 5px;
            }
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .td_grand_total {font-weight: bold; background-color: #0096bb; color: #F5F5F5;}
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
            #td1 {border: 1px solid #CCC; background-color: #DDD;}
            #td2 {border: 1px solid #CCC;}
            #tdTotal {background-color: #fad9d9;}
            #query {
                padding: 7px 9px; color: #f7f7f7; background-color: #797979; 
                border:1px solid #575757; border-radius: 5px; 
                margin-bottom: 5px; font-size: 12px;
                font-family: Courier New,Courier,Lucida Sans Typewriter,Lucida Typewriter,monospace;
            }
            #searchForm {
                width: 100%;
                padding: 21px; color: #797979; background-color: #F7F7F7;
                border:1px solid #DDD;
                font-size: 12px;
            }

            #resultForm {
                width: 100%;
                padding: 21px; color: #474747; background-color: #ffffff;
                border:1px solid #DDD;
                font-size: 12px;
            }

            .LockOff {
                display: none;
                visibility: hidden;
            }

            .LockOn {
                display: block;
                visibility: visible;
                position: absolute;
                z-index: 999;
                top: 0px;
                left: 0px;
                width: 105%;
                height: 105%;
                background-color: #ccc;
                text-align: center;
                padding-top: 20%;
                filter: alpha(opacity=75);
                opacity: 0.75;
                font-size: 250%;
            }
        </style>
        <SCRIPT language=JavaScript>        


            function cmdLoad(){
                document.frm_printing.target="";    
                document.frm_printing.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frm_printing.action="src_list_benefit_deduction_department.jsp";        
                document.frm_printing.submit();
            }

            function cmdRekonsiliasi(){
                document.frm_printing.command.value="<%=Command.PRINT%>";
                document.frm_printing.action="src_list_benefit_deduction_department.jsp";
                document.frm_printing.submit();
            }
 
            function cmdAllSectionExcel(){
                document.frm_printing.command.value="<%=Command.PRINT%>";
                document.frm_printing.target="blank";
                document.frm_printing.action="export_excel/all_section_excel.jsp";
                document.frm_printing.submit();
            }
 
            function cmdRekonsiliasiExcel(){
                document.frm_printing.command.value="<%=Command.PRINT%>";
                document.frm_printing.target="blank";
                document.frm_printing.action="export_excel/rekonsiliasi_excel.jsp";
                document.frm_printing.submit();
            }

            function cmdRingkasanGajiExcel(){
                document.frm_printing.command.value="<%=Command.PRINT%>";
                document.frm_printing.target="blank";
                document.frm_printing.action="export_excel/ringkasan_gaji_excel.jsp";
                document.frm_printing.submit();
            }
            
            function cmdPrintAllCsvRekonsiliasi(){
                document.frm_printing.target="";
                document.frm_printing.command.value="<%=Command.PRINT%>";
                document.frm_printing.aksiCommand.value="0";
                document.frm_printing.target="summarypayroll";
                document.frm_printing.action="<%=approot%>/servlet/com.dimata.harisma.printout.PayrollSummaryXlsRekonsiliasiGaji";
                document.frm_printing.submit();
            }
        
        </SCRIPT>
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <div id="theLockPane" class="LockOff"></div> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../../main/header.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
                    <%@ include file = "../../main/mnmain.jsp" %>
                    <!-- #EndEditable --> </td>
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
                    <table width="100%" border="0" cellspacing="3" cellpadding="2" id="tbl0">
                        <tr> 
                            <td width="100%" colspan="3" valign="top" style="padding: 12px"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td height="20"> <div id="menu_utama"><span id="menu_teks">Report</span> <!-- #EndEditable --> </div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;" valign="top">

                                            <table style="padding:9px; border:1px solid #00CCFF;" <%=garisContent%> width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">

                                                <tr>
                                                    <td valign="top">
                                                        <form name="frm_printing" method="POST" action="">
                                                            <input type="hidden" name="command" value="<%=iCommand%>">
                                                            <input type="hidden" name="reportType" value="<%=reportType%>">
                                                            <input type="hidden" name="paySlipPeriod" value="1">


                                                            <table>

                                                                <tr>
                                                                    <td colspan="2">
                                                                        <div id="searchForm">
                                                                            <h2>Search Form</h2>
                                                                            <table >
                                                                                <tr>
                                                                                    <td width="10%" style="background: #EEE">Period</td>
                                                                                    <td width="20%" style="background: #EEE"><%
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
                                                                                        %> <%=ControlCombo.draw("periodId", null, "" + periodeId, periodValue, periodKey, "")%>
                                                                                    </td>
                                                                                    <td width="3%">&nbsp;</td>
                                                                                    <td width="10%" style="background: #EEE">Company</td>
                                                                                    <td width="20%" style="background: #EEE"><%
                                                                                        Vector comp_value = new Vector(1, 1);
                                                                                        Vector comp_key = new Vector(1, 1);
                                                                                        comp_value.add("0");
                                                                                        comp_key.add("select ...");
                                                                                        Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                                                                                        for (int i = 0; i < listComp.size(); i++) {
                                                                                            Company comp = (Company) listComp.get(i);
                                                                                            comp_key.add(comp.getCompany());
                                                                                            comp_value.add(String.valueOf(comp.getOID()));
                                                                                        }
                                                                                        %> 
                                                                                        <%= ControlCombo.draw("oidCompany", "formElemen", null, "" + oidCompany, comp_value, comp_key, "onChange=\"javascript:cmdLoad()\"")%>
                                                                                    </td>
                                                                                    <td width="3%">&nbsp;</td>
                                                                                    <td width="10%" style="background: #EEE">Division</td>
                                                                                    <td width="20%" style="background: #EEE"><%
                                                                                        String whereDiv = "";
                                                                                        if (oidCompany != 0) {
                                                                                            whereDiv = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + " = " + oidCompany;
                                                                                        }
                                                                                        Vector listDivision = PstDivision.list(0, 0, whereDiv, "DIVISION");
                                                                                        Vector divValue = new Vector(1, 1);
                                                                                        Vector divKey = new Vector(1, 1);
                                                                                        divValue.add("0");
                                                                                        divKey.add("select ...");
                                                                                        for (int d = 0; d < listDivision.size(); d++) {
                                                                                            Division division = (Division) listDivision.get(d);
                                                                                            divValue.add("" + division.getOID());
                                                                                            divKey.add(division.getDivision());
                                                                                        }
                                                                                        out.println(ControlCombo.draw("division", null, "" + oidDivision, divValue, divKey, "onChange=\"javascript:cmdLoad()\""));%>
                                                                                    </td>
                                                                                    <td width="3%">&nbsp;</td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td width="10%" style="background: #EEE">Department</td>
                                                                                    <td width="20%" style="background: #EEE"><%
                                                                                        Vector dept_value = new Vector(1, 1);
                                                                                        Vector dept_key = new Vector(1, 1);
                                                                                        dept_value.add("0");
                                                                                        dept_key.add("select ...");
                                                                                        String whereDept = "" + PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision;
                                                                                        Vector listDept = PstDepartment.list(0, 0, whereDept, " DEPARTMENT ");

                                                                                        for (int i = 0; i < listDept.size(); i++) {
                                                                                            Department dept = (Department) listDept.get(i);
                                                                                            dept_key.add(dept.getDepartment());
                                                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                                                        }
                                                                                        out.println(ControlCombo.draw("department", null, "" + oidDepartment, dept_value, dept_key, "onChange=\"javascript:cmdLoad()\""));%>
                                                                                    </td>
                                                                                    <td width="3%">&nbsp;</td>
                                                                                    <td width="10%" style="background: #EEE">Section</td>
                                                                                    <td width="20%" style="background: #EEE"><%
                                                                                        Vector sec_value = new Vector(1, 1);
                                                                                        Vector sec_key = new Vector(1, 1);
                                                                                        sec_value.add("0");
                                                                                        sec_key.add("select ...");
                                                                                        String whereSec = "" + PstSection.TBL_HR_SECTION + "." + PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + oidDepartment;
                                                                                        Vector listSec = PstSection.list(0, 0, whereSec, " SECTION ");
                                                                                        for (int i = 0; i < listSec.size(); i++) {
                                                                                            Section sec = (Section) listSec.get(i);
                                                                                            sec_key.add(sec.getSection());
                                                                                            sec_value.add(String.valueOf(sec.getOID()));
                                                                                        }
                                                                                        out.println(ControlCombo.draw("section", null, "" + oidSection, sec_value, sec_key));%>
                                                                                    </td>
                                                                                    <td width="3%">&nbsp;</td>
                                                                                    <td width="10%" style="background: #EEE">Resign Include</td>
                                                                                    <td width="20%" style="background: #EEE"><input type="checkbox" name="INCLUDE_RESIGN" value="1" />
                                                                                    </td>
                                                                                    <td width="3%">&nbsp;</td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td width="3%">&nbsp;</td>
                                                                                </tr> 
                                                                                   
                                                                            </table>
                                                                            <table >
                                                                                
                                                                                <tr>
                                                                                    <td width="3%">&nbsp;</td>
                                                                                </tr> 
                                                                                <tr>
                                                                                    <td width="10%"></td>
                                                                                    <td><button id="btn" onclick="cmdRingkasanGajiExcel(0)">Ringkasan Gaji Excel</button> <button id="btn" onclick="cmdAllSectionExcel(0)">All Section Excel</button><button id="btn" onclick="cmdRekonsiliasiExcel(0)">Rekonsiliasi Excel</button></td>
                                                                                </tr>    
                                                                            </table>        
                                                                                    
                                                                        </div>
                                                                    </td>
                                                                </tr>

                                                            </table>
                                                        </form>
                                                    </td>
                                                </tr>


                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                            </table>

                                        </td>
                                    </tr>
                                </table><!---End Tble--->
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom">
                    <!-- untuk footer -->
                    <%@include file="../../footer.jsp" %>
                </td>

            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
                    <%@ include file = "../../main/footer.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
        <div id="theLockPane" class="LockOff"></div> 
    </body>
</html>
