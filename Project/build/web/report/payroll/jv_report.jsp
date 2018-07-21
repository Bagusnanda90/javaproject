<%-- 
    Document   : jv_report
    Created on : Jun 24, 2015, 12:40:28 PM
    Author     : Hendra Putu
--%>

<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@page import="com.dimata.aiso.entity.masterdata.PstPerkiraan"%>
<%@page import="com.dimata.aiso.entity.masterdata.Perkiraan"%>
<%@page import="com.dimata.harisma.entity.payroll.PstCustomRptMain"%>
<%@page import="com.dimata.harisma.entity.payroll.CustomRptMain"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.payroll.CtrlCustomRptMain"%>
<%@page import="com.dimata.harisma.form.payroll.FrmCustomRptMain"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
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

    public String getCoAValue(long idPerkiraan, long periodId){
        String str = "";

        return str;
    }
    
    public String drawList(Vector objectClass, long periodId) {

        ControlList ctrlist = new ControlList(); //membuat new class ControlList
        // membuat tampilan dengan controllist

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("No", "");
        ctrlist.addHeader("Account No.","");
        ctrlist.addHeader("Description", "");
        ctrlist.addHeader("Debit", "");
        ctrlist.addHeader("Credit", "");

        ctrlist.setLinkRow(1); // untuk menge-sett link di kolom pertama atau dikolom yg lain
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");

        ctrlist.reset(); //berfungsi untuk menginisialisasi list menjadi kosong

        int no = 0;
        int tanda = 0;
        Vector rowy = new Vector();
        long debitTotal = 0;
        long creditTotal = 0;

        for (int i = 0; i < objectClass.size(); i++) {
            Perkiraan perkiraan = (Perkiraan)objectClass.get(i);
            // rowx will be created secara berkesinambungan base on i
            Vector rowx = new Vector();
            tanda = perkiraan.getTandaDebetKredit();
            long total = PstComponentCoaMap.getCoA(perkiraan.getOID(), periodId);
            no = no + 1;
            rowx.add("" + no);
            rowx.add(""+perkiraan.getNoPerkiraan());
            rowx.add(""+perkiraan.getAccountNameEnglish());
            if (tanda == 0){
                debitTotal = debitTotal + total;
                rowx.add(""+Formater.formatNumberMataUang(total, "Rp")); // Debit
                rowx.add("-"); // Credit
            } else {
                creditTotal = creditTotal + total;
                rowx.add("-"); // Debit
                rowx.add(""+Formater.formatNumberMataUang(total, "Rp")); // Credit
            }
            
            lstData.add(rowx);
        }
        rowy.add("-");
        rowy.add("-");
        rowy.add("GRAND TOTAL");
        rowy.add(""+Formater.formatNumberMataUang(debitTotal, "Rp"));
        rowy.add(""+Formater.formatNumberMataUang(creditTotal, "Rp"));
        lstData.add(rowy);
        return ctrlist.draw(); // mengembalikan data-data control list
        
    }
%>
<!DOCTYPE html>
<%  
    
    int iCommand = FRMQueryString.requestCommand(request);
    long oidPeriod = FRMQueryString.requestLong(request, "period_id");
    Vector listPerkiraan = new Vector(1,1);
    if (iCommand == Command.VIEW){
        listPerkiraan = PstPerkiraan.list(0, 0, "", "");
    }
    
    
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JV Report</title>
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
                document.frm_perkiraan.action = "jv_report.jsp";
                document.frm_perkiraan.submit();
            }
            function cmdView() {
                document.frm_perkiraan.command.value="<%=Command.VIEW%>";               
                document.frm_perkiraan.oid_custom.value = "0";
                getCmd();
            }
            function cmdExportXLS(){
                document.frm_perkiraan.action="<%=printroot%>.report.payroll.JVReportXLS"; 
                document.frm_perkiraan.target = "ReportExcel";
                document.frm_perkiraan.submit();
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
                                        <td height="20"> <div id="menu_utama"> JV Report </div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;" valign="top">
                                        
                                            <table style="padding:9px; border:1px solid #00CCFF;" <%=garisContent%> width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td valign="top">
                                                        
                                                        
                                                        <form name="frm_perkiraan" method="post" action="">
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
                                                                    <td colspan="2">
                                                                        <button id="btn" onclick="cmdView()">View</button>
                                                                        &nbsp;
                                                                        <button id="btn" onclick="cmdExportXLS()">Export To Excel</button>
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
                                                    if (listPerkiraan != null && listPerkiraan.size()>0){
                                                    %>
                                                    <td>
                                                        <div id="mn_utama">List JV Report</div>
                                                        <%=drawList(listPerkiraan, oidPeriod)%>
                                                    </td>
                                                    
                                                    <%
                                                    } else {
                                                    %>
                                                    <td>
                                                        <div id="mn_utama">List JV Report</div>
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
    <!-- #BeginEditable "script" --> <script language="JavaScript">
                var oBody = document.body;
                var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
                
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>