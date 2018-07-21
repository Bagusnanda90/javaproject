<%-- 
    Document   : leave_entitle_report
    Created on : Jun 30, 2015, 11:06:10 AM
    Author     : Hendra Putu
--%>

<%@page import="com.dimata.harisma.entity.payroll.LeaveEntitleReport"%>
<%@page import="com.dimata.harisma.entity.payroll.PstCustomRptMain"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.util.Command"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
    public String drawList(Vector objectClass) {

        ControlList ctrlist = new ControlList(); //membuat new class ControlList
        // membuat tampilan dengan controllist

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("No", "");
        ctrlist.addHeader("Account Code.","");
        ctrlist.addHeader("Account Name", "");
        ctrlist.addHeader("Department", "");
        ctrlist.addHeader("Section", "");
        ctrlist.addHeader("Base Amount", "");
        ctrlist.addHeader("Head Count", "");
        ctrlist.addHeader("Apply Amount", "");

        ctrlist.setLinkRow(1); // untuk menge-sett link di kolom pertama atau dikolom yg lain
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");

        ctrlist.reset();
        int no = 0;
        double total = 0;
        int headCount = 0;
        
        String[][] data = new String[objectClass.size()+1][3];//
        int temp = 0;
        for (int h = 0; h < objectClass.size()+1; h++) {
            data[h][0] = "test";
            data[h][1] = "test";
            data[h][2] = "0";
        }
        for (int i = 0; i < objectClass.size(); i++) {
            LeaveEntitleReport en = (LeaveEntitleReport)objectClass.get(i);
            data[i][0] = en.getDepartment();
            data[i][1] = en.getSection();
            data[i][2] = ""+en.getBaseAmount();
        }
        int nextDepart = 0;
        int nextSection = 1;
        int headCountDepart = 0;
        double totalDepart = 0;
        double appAmount = 0;
        double totalAppAmount = 0;
        int inc = 0;
        double totalBase = 0;
        double totalApp = 0;
        int totalHeadCount = 0;
        Vector rowz = new Vector();
        for(int j = 0; j < objectClass.size()+1; j++){
            Vector rowx = new Vector();
            Vector rowy = new Vector();
            
                if (data[j][1].equals(data[nextSection][1])){
                    total = total + Double.valueOf(data[j][2]);
                    headCount++;
                    temp = j;
                } else {
                    no = no + 1;
                    rowx.add("" + no);
                    rowx.add("-");
                    rowx.add("-");
                    rowx.add(data[temp][0]);
                    rowx.add(data[temp][1]);
                    rowx.add(""+Formater.formatNumberMataUang(total, "Rp"));
                    appAmount = total / 30;
                    totalAppAmount = totalAppAmount + appAmount;
                    rowx.add(""+headCount);
                    rowx.add(""+Formater.formatNumberMataUang(appAmount, "Rp"));
                    headCountDepart += headCount;
                    totalDepart += total;
                    total = Double.valueOf(data[j][2]);
                    headCount = 1;
                    temp = j;
                    nextSection = j;
                    lstData.add(rowx);
                }
            
            if (!(data[j][0].equals(data[nextDepart][0]))){
                rowy.add("-");
                rowy.add("-");
                rowy.add("-");
                rowy.add("-");
                rowy.add("<div style=\"color:#87c70c;\">Total Department</div>");
                rowy.add("<div style=\"color:#87c70c;\">"+Formater.formatNumberMataUang(totalDepart, "Rp")+"</div>");
                rowy.add("<div style=\"color:#87c70c;\">"+headCountDepart+"</div>");
                rowy.add("<div style=\"color:#87c70c;\">"+Formater.formatNumberMataUang(totalAppAmount, "Rp")+"</div>");
                totalHeadCount += headCountDepart;
                totalBase += totalDepart;
                totalApp += totalAppAmount;
                headCountDepart = 0;
                totalDepart = 0;
                totalAppAmount = 0;
                lstData.add(rowy);
                nextDepart = j;
                inc++;
            }
        }
        rowz.add("-");
        rowz.add("-");
        rowz.add("-");
        rowz.add("-");
        rowz.add("<div style=\"color:#87c70c;\">Total Company</div>");
        rowz.add("<div style=\"color:#87c70c;\">"+Formater.formatNumberMataUang(totalBase, "Rp")+"</div>");
        rowz.add("<div style=\"color:#87c70c;\">"+totalHeadCount+"</div>");
        rowz.add("<div style=\"color:#87c70c;\">"+Formater.formatNumberMataUang(totalApp, "Rp")+"</div>");
        lstData.add(rowz);
        return ctrlist.draw();
    }
%>

<%  
    
    int iCommand = FRMQueryString.requestCommand(request);
    long oidPeriod = FRMQueryString.requestLong(request, "period_id");
    long oidDepartment = FRMQueryString.requestLong(request, "department_id");
    Vector listReport = new Vector(1,1);
    if (iCommand == Command.VIEW){
        listReport = PstCustomRptMain.listLeaveEntitleReport(oidPeriod, oidDepartment);
    }

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Leave Entitle Report</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 
        <style type="text/css">
            
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 14px; background-color: #F5F5F5;}
            
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3; color:#0099FF; font-size: 14px; font-weight: bold;}
            
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
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
        </style>

        <script type="text/javascript">
            function getCmd(){
                document.frm_entitle.action = "leave_entitle_report.jsp";
                document.frm_entitle.submit();
            }
            
            function cmdView() {
                document.frm_entitle.command.value="<%=Command.VIEW%>";
                getCmd();
            }
            
            function cmdExportXLS(){
                document.frm_entitle.action="<%=printroot%>.report.payroll.LeaveEntitleReportXLS"; 
                document.frm_entitle.target = "ReportExcel";
                document.frm_entitle.submit();
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
                                        <td height="20"> <div id="menu_utama"> Leave Entitle Report </div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;" valign="top">
                                        
                                            <table style="padding:9px; border:1px solid #00CCFF;" <%=garisContent%> width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td valign="top">
                                                        
                                                        
                                                        <form name="frm_entitle" method="post" action="">
                                                            <input type="hidden" name="command" value="<%=iCommand%>" />
                                                            <table>
                                                                <tr>
                                                                    <th>Period</th>
                                                                    <td>
                                                                        <select name="period_id">
                                                                            <option value="0">-select-</option>
                                                                            <%
                                                                            Vector listPayPeriod = PstPayPeriod.list(0, 0, "", "");
                                                                            if (listPayPeriod != null && listPayPeriod.size()>0){
                                                                                for(int i=0; i<listPayPeriod.size(); i++){
                                                                                    PayPeriod payPeriod = (PayPeriod)listPayPeriod.get(i);
                                                                                    %>
                                                                                    <option value="<%=payPeriod.getOID()%>"><%=payPeriod.getPeriod()%></option>
                                                                                    <%
                                                                                }
                                                                            }
                                                                            %>
                                                                            
                                                                        </select>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <th><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></th>
                                                                    <td>
                                                                        <select name="department_id">
                                                                            <option value="0">-select-</option>
                                                                            <%
                                                                            Vector listDepart = PstDepartment.list(0, 0, "", "");
                                                                            if (listDepart != null && listDepart.size()>0){
                                                                                for(int j=0; j<listDepart.size(); j++){
                                                                                    Department depart = (Department)listDepart.get(j);
                                                                                    %>
                                                                                    <option value="<%=depart.getOID()%>"><%=depart.getDepartment()%></option>
                                                                                    <%
                                                                                }
                                                                            }
                                                                            %>
                                                                        </select>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2">
                                                                        <button id="btn" onclick="cmdView()">View</button>
                                                                        &nbsp;
                                                                        <button id="btn" onclick="cmdExportXLS()">Export to Excel</button>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </form>
                                                        
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                
                                                <tr>
                                                    <%
                                                    if (listReport != null && listReport.size()>0){
                                                    %>
                                                    <td>
                                                        <div id="mn_utama">List Leave Entitle Report</div>
                                                        <%=drawList(listReport)%>
                                                    </td>
                                                    
                                                    <%
                                                    } else {
                                                    %>
                                                    <td>
                                                        <div id="mn_utama">List Leave Entitle Report</div>
                                                        record not found
                                                    </td>
                                                    <%    
                                                    }
                                                    %>
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
</html>
