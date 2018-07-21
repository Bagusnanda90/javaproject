<%-- 
    Document   : benefit_input_history
    Created on : May 20, 2015, 10:46:23 AM
    Author     : Hendra Putu
--%>

<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.harisma.entity.payroll.SrcBenefitEmp"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.qdep.form.Control"%>
<%@page import="com.dimata.harisma.entity.payroll.PstBenefitPeriodEmp"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.payroll.PstBenefitConfig"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.BenefitConfig"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.BenefitPeriod"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.harisma.session.payroll.SessBenefitLevel"%>
<%@page import="javax.swing.text.Style"%>
<%@ page language="java" %>

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_COMPONENT);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    public String drawList(Vector objectClass) {

        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("No", "");
        ctrlist.addHeader("Benefit Config", "");
        ctrlist.addHeader("Period From - Period To","");
        ctrlist.addHeader("Payroll Period", "");
        ctrlist.addHeader("Total Revenue", "");

        ctrlist.setLinkRow(1); 

        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdView('");
        ctrlist.setLinkSufix("')");

        Vector lstData = ctrlist.getData();

        ctrlist.reset(); //berfungsi untuk menginisialisasi list menjadi kosong

        int no = 0;
        for (int i = 0; i < objectClass.size(); i++) { 
            BenefitPeriodHistory bpHistory = (BenefitPeriodHistory)objectClass.get(i);
            Vector rowx = new Vector();
            no = no + 1;
            rowx.add("" + no);
            rowx.add(bpHistory.getBenefitConfiguration());
            rowx.add(bpHistory.getPeriodFrom()+" - "+bpHistory.getPeriodTo());
            rowx.add(bpHistory.getPayrollPeriod());
            rowx.add(""+Formater.formatNumberMataUang(bpHistory.getTotalRevenue(),""));
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(bpHistory.getOID()));
        }

        return ctrlist.draw(); // mengembalikan data-data control list

    }
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidHistory = FRMQueryString.requestLong(request, "oid_history");
    

    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";//
    Vector listBPHistory = new Vector();
    ControlLine ctrLine = new ControlLine();

    /*count list All Position*/
    int vectSize = PstBenefitPeriodHistory.getCount(whereClause); //PstBenefitPeriodEmp.getCount(periodId, empNum, empName);     
    Control control = new Control();
    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = control.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */

    listBPHistory = PstBenefitPeriodHistory.list(start, recordToGet, whereClause, ""); //PstBenefitPeriodEmp.list(start, recordToGet, periodId, empNum, empName);


    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listBPHistory.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listBPHistory = listBPHistory = PstBenefitPeriodHistory.list(start, recordToGet, whereClause, "");
    }
%>
<html>
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Benefit Period History</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        
        <style type="text/css">
            #menu_utama {color: #0066CC; font-weight: bold; padding: 5px 14px; border-left: 1px solid #0066CC; font-size: 14px; background-color: #F7F7F7;}
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 14px; background-color: #F5F5F5;}            
            #btn {
              background: #3498db;
              border: 1px solid #0066CC;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            
            #btn:hover {
              background: #3cb0fd;
              border: 1px solid #3498db;
            }
            #titleTd {background-color: #3cb0fd; color: #FFF; padding: 3px 5px; border-left: 1px solid #0066CC;}
            #subtitle {padding: 2px 7px; font-weight: bold; background-color: #FFF; border-left: 1px solid #3498db;}
            #td1{ padding: 3px;}
            #td2{ padding: 3px 7px 3px 5px;}
            #tdrs {padding: 3px; border-top: 1px solid #333;text-align: right;}
            #tdrs1 { padding: 3px; border-top: 1px solid #373737;text-align: right;background-color: #ebffd2; color: #3d6a02; font-weight: bold;}
            #tdrs2 { padding: 3px; text-align: right;background-color: #dff6ff; color: #197a9e; font-weight: bold;}
            #tbl {border-collapse: collapse;}
            #td3 {padding: 3px; border: 1px solid #999;}
            #td3Header{padding: 3px; border: 1px solid #999; background-color: #DDD; color:#333; font-weight: bold;}
            #td4 {padding:3px 5px 3px 7px;background-color:#F5F5F5;}
            #td5 {padding:3px 5px 3px 7px;background-color:#F7F7F7;}
            #td4L {padding:3px 5px 3px 7px;background-color:#F5F5F5;border-left: 1px solid #0066CC;}
            #td5L {padding:3px 5px 3px 7px;background-color:#F7F7F7;border-left: 1px solid #0066CC;}
            #tdsave {padding: 3px;background-color: #FFF;}
        </style>
        <script type="text/javascript">
            function getCmd(){
                document.frm_history.action = "benefit_input_history.jsp";
                document.frm_history.submit();
            }

            function cmdView(oid){
                document.frm_history.oid_history.value=oid;
                document.frm_history.command.value="<%=Command.VIEW%>";
                getCmd();
            }
            function cmdListFirst(){
                document.frm_history.command.value="<%=Command.FIRST%>";
                document.frm_history.prev_command.value="<%=Command.FIRST%>";
                getCmd();
            }

            function cmdListPrev(){
                document.frm_history.command.value="<%=Command.PREV%>";
                document.frm_history.prev_command.value="<%=Command.PREV%>";
                getCmd();
            }

            function cmdListNext(){
                document.frm_history.command.value="<%=Command.NEXT%>";
                document.frm_history.prev_command.value="<%=Command.NEXT%>";
                getCmd();
            }

            function cmdListLast(){
                document.frm_history.command.value="<%=Command.LAST%>";
                document.frm_history.prev_command.value="<%=Command.LAST%>";
                getCmd();
            }

            function fnTrapKD(){
            //alert(event.keyCode);
                switch(event.keyCode) {
                    case <%=LIST_PREV%>:
                            cmdListPrev();
                        break;
                    case <%=LIST_NEXT%>:
                            cmdListNext();
                        break;
                    case <%=LIST_FIRST%>:
                            cmdListFirst();
                        break;
                    case <%=LIST_LAST%>:
                            cmdListLast();
                        break;
                    default:
                        break;
                }

            }
        </script>
        <!-- #EndEditable --> 
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
                            <td width="100%" colspan="3" valign="top"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr> 
                                        <td height="20"> <div id="menu_utama"> <!-- #BeginEditable "contenttitle" -->Benefit Period History<!-- #EndEditable --> </div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;">
                                        
                                            <table style="padding:9px; border:1px solid #00CCFF;" <%=garisContent%> width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td>
                                                        
                                                        <form name="frm_history" method="POST" action="">
                                                        <input type="hidden" name="start" value="<%=start%>">
                                                        <input type="hidden" name="command" value="<%=iCommand%>">
                                                        <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                        <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                        <input type="hidden" name="oid_history" value="<%=oidHistory%>">
                                                        </form>
                                                        <%
                                                        if (iCommand == Command.VIEW){
                                                            String whereDBPH = " BENEFIT_PERIOD_HISTORY_ID="+oidHistory;
                                                            Vector dataBPHistory = PstBenefitPeriodHistory.list(0, 0, whereDBPH, "");
                                                            String[] bpHistoryDetail = new String[19];
                                                            double distributionResult1 = 0;
                                                            double distributionResult2 = 0;
                                                            if (dataBPHistory!= null && dataBPHistory.size()>0){
                                                                for(int b=0; b<dataBPHistory.size(); b++){
                                                                    BenefitPeriodHistory bpH = (BenefitPeriodHistory)dataBPHistory.get(b);
                                                                    bpHistoryDetail[0] = ""+bpH.getBenefitPeriodId();
                                                                    bpHistoryDetail[1] = bpH.getBenefitConfiguration();
                                                                    bpHistoryDetail[2] = bpH.getPeriodFrom();
                                                                    bpHistoryDetail[3] = bpH.getPeriodTo();
                                                                    bpHistoryDetail[4] = bpH.getPayrollPeriod();
                                                                    bpHistoryDetail[5] = ""+Formater.formatNumberMataUang(bpH.getTotalRevenue(),"Rp");
                                                                    bpHistoryDetail[6] = ""+Formater.formatNumberMataUang(bpH.getDistributionValue(),"Rp");
                                                                    bpHistoryDetail[7] = ""+bpH.getDistributionDesc1();
                                                                    bpHistoryDetail[8] = ""+bpH.getDistributionDesc2();
                                                                    bpHistoryDetail[9] = ""+bpH.getDistributionPercent1();
                                                                    bpHistoryDetail[10] = ""+bpH.getDistributionPercent2();
                                                                    bpHistoryDetail[11] = ""+bpH.getPart1TotalDivider();
                                                                    bpHistoryDetail[12] = ""+bpH.getPart2TotalDivider();
                                                                    bpHistoryDetail[13] = ""+Formater.formatNumberMataUang(bpH.getPart1Value(),"Rp");
                                                                    bpHistoryDetail[14] = ""+Formater.formatNumberMataUang(bpH.getPart2Value(),"Rp");
                                                                    distributionResult1 = bpH.getPart1Value();
                                                                    distributionResult2 = bpH.getPart2Value();
                                                                    bpHistoryDetail[15] = bpH.getDocStatus();
                                                                    bpHistoryDetail[16] = bpH.getApprove1();
                                                                    bpHistoryDetail[17] = bpH.getApprove2();
                                                                    bpHistoryDetail[18] = bpH.getCreatedBy();
                                                                            
                                                                }
                                                            }
                                                        %>
                                                        <div>
                                                            <!-- Start View Detail -->
                                                            <table style="color:#373737;" border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td valign="top">
                                                                    <strong>Benefit Configuration</strong>
                                                                </td>
                                                                <td colspan="3">
                                                                    <%=bpHistoryDetail[1]%>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <strong>Period From</strong>
                                                                        
                                                                </td>
                                                                <td>
                                                                    <%=bpHistoryDetail[2]+" To "+bpHistoryDetail[3]%>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <strong>Payroll Period</strong>
                                                                </td>
                                                                
                                                                <td colspan="3">
                                                                    <%=bpHistoryDetail[4]%>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <strong>Total Revenue</strong>
                                                                </td>
                                                                <td colspan="3">
                                                                    <%=bpHistoryDetail[5]%>
                                                                </td>
                                                            </tr>
                                                            
                                                            <tr>
                                                                <td colspan="4"><div id="subtitle">Deduction</div></td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="4">
                                                                    <table>
                                                                        <%
                                                                        String whereListDeduction = " BENEFIT_PERIOD_HISTORY_ID="+oidHistory+" ";
                                                                        Vector listDeduction = PstDeductionHistory.list(0, 0, whereListDeduction, "");
                                                                        if (listDeduction != null && listDeduction.size() > 0){
                                                                            for(int d=0; d<listDeduction.size(); d++){
                                                                                DeductionHistory dedHistory = (DeductionHistory)listDeduction.get(d);
                                                                                
                                                                                
                                                                        %>
                                                                            <tr>
                                                                                <td id="td1"><%=d+1%>)</td>
                                                                                <td id="td1"><%=dedHistory.getPercen()%>% &times; <%=dedHistory.getDescription()%>&nbsp;</td>
                                                                                <td id="td1"><%=Formater.formatNumberMataUang(dedHistory.getPercenResult(), "Rp")%></td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td colspan="3"><%=Formater.formatNumberMataUang(dedHistory.getDeductionResult(),"Rp")%></td>
                                                                            </tr>
                                                                            <%
                                                                            }
                                                                        }
                                                                        %>
                                                                        
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                            <!-- to be continue --->
                                                            <tr>
                                                                <td colspan="4"><div id="subtitle">Service Charge Distribution</div></td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="4">
                                                                    <table>
                                                                        <tr>
                                                                            <td id="td2">1)</td>
                                                                            <td colspan="3" id="td2"><%=bpHistoryDetail[7]%></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td id="td2">&nbsp;</td>
                                                                            <td id="td2"><%=bpHistoryDetail[9]%>% &times; <%=bpHistoryDetail[6]%></td>
                                                                            <td id="td2">
                                                                                <%=bpHistoryDetail[11]%>
                                                                            </td>
                                                                            <td id="tdrs2">
                                                                                <%=bpHistoryDetail[13]%>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td id="td2">2)</td>
                                                                            <td colspan="3" id="td2"><%=bpHistoryDetail[8]%></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td>&nbsp;</td>
                                                                            <td id="td2"><%=bpHistoryDetail[10]%>% &times; <%=bpHistoryDetail[6]%></td>
                                                                            <td id="td2">
                                                                                <%=bpHistoryDetail[12]%>
                                                                            </td>
                                                                            <td id="tdrs2">
                                                                                <%=bpHistoryDetail[14]%>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                            
                                                            <tr>
                                                                <td colspan="4">
                                                                    <table id="tbl">
                                                                        <tr>
                                                                            <td id="td3Header">Level</td>
                                                                            <td id="td3Header">Level Point</td>
                                                                            <td id="td3Header">Flat Service Charge</td>
                                                                            <td id="td3Header">Point</td>
                                                                            <td id="td3Header">Service By Point</td>
                                                                            <td id="td3Header">Total Service Charge</td>
                                                                            <td id="td3Header">Head Count</td>
                                                                        </tr>
                                                                        <%
                                                                            double serviceByPoint = 0;
                                                                            double totalServiceCharge = 0;
                                                                            BenefitPeriod bPeriod = new BenefitPeriod();
                                                                            try {
                                                                                bPeriod = PstBenefitPeriod.fetchExc(Long.valueOf(bpHistoryDetail[0]));
                                                                            } catch (Exception ex){
                                                                                System.out.println(ex.toString());
                                                                            }
                                                                            Vector listPoint = PstBenefitPeriod.getTotalPoint(bPeriod.getBenefitConfigId(), bPeriod.getPeriodId());
                                                                            for(int i=0; i<listPoint.size();i++){
                                                                                SessBenefitLevel pointLevel = (SessBenefitLevel)listPoint.get(i);
                                                                                serviceByPoint = pointLevel.getLevelPoint() * distributionResult2;
                                                                                totalServiceCharge = distributionResult1 + serviceByPoint;
                                                                        %>
                                                                        <tr>
                                                                            <td id="td3"><%=pointLevel.getLevel()%></td>
                                                                            <td id="td3"><%=pointLevel.getLevelPoint()%></td>
                                                                            <td id="td3"><%=Formater.formatNumberMataUang(distributionResult1,"Rp")%></td>
                                                                            <td id="td3"><%=Formater.formatNumberMataUang(distributionResult2,"Rp")%></td>
                                                                            <td id="td3"><%=Formater.formatNumberMataUang(serviceByPoint,"Rp")%></td>
                                                                            <td id="td3"><%=Formater.formatNumberMataUang(totalServiceCharge,"Rp")%></td>
                                                                            <td id="td3"><%=pointLevel.getCountPoint()%></td>
                                                                        </tr>
                                                                        <%
                                                                            }
                                                                        %>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td valign="top" colspan="4" style="padding:9px 0px;">
                                                                    <table style="border-collapse: collapse;border: 1px solid #DDD;">
                                                                        <tr>
                                                                            <td id="td4"><strong>Status Document</strong></td>
                                                                            <td id="td4"> 
                                                                                <%=bpHistoryDetail[15]%>
                                                                            </td>
                                                                            <td id="td4"><strong>Created by</strong></td>
                                                                            <td id="td4">
                                                                                <%=bpHistoryDetail[18]%>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td id="td4"><strong>Approve 1</strong></td>
                                                                            <td id="td4">
                                                                                <%=bpHistoryDetail[16]%>                                                                                
                                                                            </td>
                                                                            <td id="td4"><strong>Approve 2</strong></td>
                                                                            <td id="td4">
                                                                                <%=bpHistoryDetail[17]%>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                  
                                                            
                                                            
                                                        </table>
                                                            <!-- End View Detail -->
                                                        </div>
                                                        <% } %>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <div id="mn_utama"> List Benefit Input History </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <%if (listBPHistory != null && listBPHistory.size() > 0) {%>
                                                    <td>
                                                        <%=drawList(listBPHistory)%>
                                                    </td>

                                                    <div> 
                                                    <%
                                                        int cmd = 0;
                                                        if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                                                                || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                                                            cmd = iCommand;
                                                        } else {
                                                            if (iCommand == Command.NONE || prevCommand == Command.NONE) {
                                                                cmd = Command.FIRST;
                                                            } else {
                                                                if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                                                                    cmd = PstBenefitPeriodEmp.findLimitCommand(start, recordToGet, vectSize);
                                                                } else {
                                                                    cmd = prevCommand;
                                                                }
                                                            }
                                                        }

                                                    %>
                                                    <% ctrLine.setLocationImg(approot + "/images");
                                                        ctrLine.initDefault();
                                                    %>
                                                    <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%> 
                                                </div>
                                                    
                                                    <%} else {%>
                                                    <td>
                                                        record not found
                                                    </td>
                                                    <%}%>
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
    <!-- #BeginEditable "script" --> 
    <script language="JavaScript">
                var oBody = document.body;
                var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
                
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
