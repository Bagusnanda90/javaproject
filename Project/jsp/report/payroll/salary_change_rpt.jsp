<%-- 
    Document   : salary_change_rpt
    Created on : Oct 22, 2015, 9:49:18 AM
    Author     : Hendra Putu
--%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.SalaryChangeEntity"%>
<%@page import="com.dimata.harisma.entity.payroll.SalaryChangeModel"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_PRESENCE_REPORT, AppObjInfo.OBJ_PRESENCE_REPORT);
    int appObjCodePresenceEdit = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_PRESENCE);
    boolean privUpdatePresence = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePresenceEdit, AppObjInfo.COMMAND_UPDATE));
%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>
<%!
    public String getPreviousStartDate(String startDate){
        String str = "";
        int year = Integer.valueOf(startDate.substring(0, 4));
        int month = Integer.valueOf(startDate.substring(5, 7));
        String monthStr = "";
        String day = startDate.substring(8, 10);
        if (month - 1 == 0){
            month = 12;
            year = year - 1;
            monthStr = String.valueOf(month);
        } else {
            month = month - 1;
            if (String.valueOf(month).length() == 1){
                monthStr = "0"+month;
            } else {
                monthStr = String.valueOf(month);
            }
        }
        str = year + "-" + monthStr + "-" + day;
        return str;
    }
    
    public String getPreviousPeriod(long periodId){
        String str = "-";
        PayPeriod period = new PayPeriod();
        try {
            period = PstPayPeriod.fetchExc(periodId);
            str = getPreviousStartDate(String.valueOf(period.getStartDate()));
            String where = PstPayPeriod.fieldNames[PstPayPeriod.FLD_START_DATE] + "='" + str +"'";
            Vector listPayPeriod = PstPayPeriod.list(0, 0, where, "");
            if (listPayPeriod != null && listPayPeriod.size()>0){
                PayPeriod period1 = (PayPeriod)listPayPeriod.get(0);
                str = String.valueOf(period1.getOID());
            } else {
                str = "0";
            }
        } catch(Exception e){
            System.out.println("PayPeriod=>"+e.toString());
        }
        return str;
    }
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    long periodId = FRMQueryString.requestLong(request, "period_id");
    
    Vector listSalaryChange = new Vector();
    if (iCommand == Command.LIST){
        listSalaryChange = SalaryChangeModel.listSalaryChange(periodId);
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Salary Change</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 
        <style type="text/css">
            #menu_utama {color: #0066CC; padding: 9px 14px; border-left: 1px solid #0066CC; font-size: 14px; background-color: #F3F3F3;}
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 12px; background-color: #F5F5F5;}
            #btn {
              background: #3498db;
              border: 1px solid #0066CC;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn:hover {
              background: #3cb0fd;
              border: 1px solid #3498db;
            }
            #tdForm {
                padding: 5px;
            }
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
        </style>
        <script type="text/javascript">
            function cmdSearch(){ 
                document.frm.command.value="<%=Command.LIST%>";
                document.frm.action="salary_change_rpt.jsp";
                document.frm.submit();
            }
            function cmdExportExcel(){	 
                document.frm.action="<%=printroot%>.report.payroll.SalaryChangeXLS"; 
                document.frm.target = "ReportExcel";
                document.frm.submit();
            }
        </script>
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
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
                                        <td height="20"> <div id="menu_utama">Salary Change Report</div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;" valign="top">
                                        
                                            <table style="padding:9px; border:1px solid #00CCFF;" <%=garisContent%> width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td valign="top">
                                                        <form name="frm" method="post" action="">
                                                            <input type="hidden" name="command" value="<%=iCommand%>" />
                                                            <input type="hidden" name="" value="" />
                                                            <div id="mn_utama">Search Salary Change</div>
                                                            <table>
                                                                <tr>
                                                                    <td>Period</td>
                                                                    <td>
                                                                        <select name="period_id">
                                                                            <option value="0">-SELECT-</option>
                                                                            <%
                                                                            Vector listPeriod = PstPayPeriod.list(0, 0, "", "");
                                                                            if (listPeriod != null && listPeriod.size()>0){
                                                                                for(int i=0; i<listPeriod.size(); i++){
                                                                                    PayPeriod period = (PayPeriod)listPeriod.get(i);
                                                                                    if (periodId == period.getOID()){
                                                                                        %>
                                                                                        <option selected="selected" value="<%=period.getOID()%>"><%= period.getPeriod() %></option>
                                                                                        <%
                                                                                    } else {
                                                                                        %>
                                                                                        <option value="<%=period.getOID()%>"><%= period.getPeriod() %></option>
                                                                                        <%
                                                                                    }
                                                                                }
                                                                            }
                                                                            %>
                                                                        </select>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>&nbsp;</td>
                                                                    <td><button id="btn" onclick="javascript:cmdSearch()">Search</button>&nbsp;<button id="btn" onclick="javascript:cmdExportExcel()">Export To Excel</button></td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2">&nbsp;</td>
                                                                </tr>
                                                            </table>
                                                            <div id="mn_utama">List Salary Change</div>
                                                            
                                                            <%
                                                            if (listSalaryChange != null && listSalaryChange.size()>0){
                                                             
                                                            %>
                                                            <table cellpadding="0" cellspacing="0" class="tblStyle">
                                                                <tr>
                                                                    <td class="title_tbl">No</td>
                                                                    <td class="title_tbl">Payroll Number</td>
                                                                    <td class="title_tbl">Full Name</td>
                                                                    <td class="title_tbl">Department Old</td>
                                                                    <td class="title_tbl">Section Old</td>
                                                                    <td class="title_tbl">Position Old</td>
                                                                    <td class="title_tbl">Level Old</td>
                                                                    <td class="title_tbl">Department New</td>
                                                                    <td class="title_tbl">Section New</td>
                                                                    <td class="title_tbl">Position New</td>
                                                                    <td class="title_tbl">Level New</td>
                                                                    <td class="title_tbl">Basic Old</td>
                                                                    <td class="title_tbl">Merit Old</td>
                                                                    <td class="title_tbl">Grade Old</td>
                                                                    <td class="title_tbl">Total Old</td>
                                                                    <td class="title_tbl">Basic New</td>
                                                                    <td class="title_tbl">Merit New</td>
                                                                    <td class="title_tbl">Grade New</td>
                                                                    <td class="title_tbl">Total New</td>
                                                                </tr>
                                                                <%
                                                                for(int i=0; i<listSalaryChange.size(); i++){
                                                                    SalaryChangeEntity salaryChange = (SalaryChangeEntity)listSalaryChange.get(i);
                                                                %>
                                                                <tr>
                                                                    <td><%=(i+1)%></td>
                                                                    <td><%=salaryChange.getPayrollNumber()%></td>
                                                                    <td><%=salaryChange.getFullName()%></td>
                                                                    <td><%=salaryChange.getDepartmentOld()%></td>
                                                                    <td><%=salaryChange.getSectionOld()%></td>
                                                                    <td><%=salaryChange.getPositionOld()%></td>
                                                                    <td><%=salaryChange.getLevelOld()%></td>
                                                                    <td><%=salaryChange.getDepartmentNew()%></td>
                                                                    <td><%=salaryChange.getSectionNew()%></td>
                                                                    <td><%=salaryChange.getPositionNew()%></td>
                                                                    <td><%=salaryChange.getLevelNew()%></td>
                                                                    <td><%=salaryChange.getBasicOld()%></td>
                                                                    <td><%=salaryChange.getMeritOld()%></td>
                                                                    <td><%=salaryChange.getGradeOld()%></td>
                                                                    <td><%=salaryChange.getTotalOld()%></td>
                                                                    <td><%=salaryChange.getBasicNew()%></td>
                                                                    <td><%=salaryChange.getMeritNew()%></td>
                                                                    <td><%=salaryChange.getGradeNew()%></td>
                                                                    <td><%=salaryChange.getTotalNew()%></td>
                                                                </tr>
                                                                <% } %>
                                                            </table>
                                                            <%
                                                            }
                                                            %>
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
    </body>

                
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>