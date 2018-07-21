<%-- 
    Document   : general
    Created on : May 2, 2016, 2:54:19 PM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayGeneral"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.payroll.CurrencyType"%>
<%@page import="com.dimata.harisma.entity.payroll.PstCurrencyType"%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="com.dimata.harisma.entity.payroll.PayGeneral"%>
<%@page import="com.dimata.harisma.entity.payroll.PayGeneral"%>
<%@page import="com.dimata.harisma.form.payroll.FrmPayGeneral"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.payroll.CtrlPayGeneral"%>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_DIVISION); %>
<%@ include file = "../../main/javainit.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    CtrlPayGeneral ctrlPayGeneral = new CtrlPayGeneral(request);
    long oidPayGeneral = FRMQueryString.requestLong(request, "pay_general_oid");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request,"start");

    int iErrCode = FRMMessage.ERR_NONE;
    String msgString = "";
    ControlLine ctrLine = new ControlLine();
    System.out.println("iCommand = "+iCommand);
    iErrCode = ctrlPayGeneral.action(iCommand , oidPayGeneral);
    msgString = ctrlPayGeneral.getMessage();
    FrmPayGeneral frmPayGeneral = ctrlPayGeneral.getForm();
    PayGeneral payGeneral = ctrlPayGeneral.getPayGeneral();
    oidPayGeneral = payGeneral.getOID();
%>
<%
if(iCommand==Command.DELETE){
	%>
<jsp:forward page="general_list.jsp">
<jsp:param name="prev_command" value="<%=prevCommand%>" />
<jsp:param name="start" value="<%=start%>" />
<jsp:param name="pay_general_oid" value="<%=payGeneral.getOID()%>" />
</jsp:forward>
<%
 }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Company Detail</title>
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <style type="text/css">
            .tblStyle {border-collapse: collapse; }
            .tblStyle td {padding: 5px 7px; border: 1px solid #CCC; font-size: 12px; }
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
            #fullname {
                background-color: #474747; 
                color: #EEE; padding: 8px 11px; 
                margin-left: 5px;
                border-radius: 3px;
                
            }
            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            #style_add {
                color: #FFF;
                font-size: 12px;
                background-color: #9bdf3b;
                padding: 5px 9px;
                border-radius: 3px;
            }
            #style_edit {
                color: #FFF;
                font-size: 12px;
                background-color: #67c3cc;
                padding: 5px 9px;
                border-radius: 3px;
            }
            #style_delete {
                color: #FFF;
                font-size: 12px;
                background-color: #ea4e6f;
                padding: 5px 9px;
                border-radius: 3px;
            }
            #style_login {
                color: #FFF;
                font-size: 12px;
                background-color: #FF7E00;
                padding: 5px 9px;
                border-radius: 3px;
            }
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
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
            
            body {background-color: #EEE; font-size: 12px;}
            .header {
                
            }
            .content-main {
                padding: 5px 25px 25px 25px;
                margin: 0px 23px 59px 23px;
            }
            .content-info {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
            }
            .content-title {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
                margin-bottom: 5px;
            }
            #title-large {
                color: #575757;
                font-size: 16px;
                font-weight: bold;
            }
            #title-small {
                color:#797979;
                font-size: 11px;
            }
            .content {
                padding: 21px;
            }
            .box {
                margin: 17px 7px;
                background-color: #FFF;
                color:#575757;
            }
            #box_title {
                padding:21px; 
                font-size: 14px; 
                color: #007fba;
                border-top: 1px solid #28A7D1;
                border-bottom: 1px solid #EEE;
            }
            #box_content {
                padding:21px; 
                font-size: 12px;
                color: #575757;
            }
            #info {
                background-color: #DDD;
                color:#474747;
                margin-top: 21px;
                padding: 12px 17px;
                border-radius: 3px;
            }
            .box-info {
                padding:21px 43px; 
                background-color: #F7F7F7;
                border-bottom: 1px solid #CCC;
                -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                 -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                      box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
            }
            #title-info-name {
                padding: 11px 15px;
                font-size: 35px;
                color: #535353;
            }
            #title-info-desc {
                padding: 7px 15px;
                font-size: 21px;
                color: #676767;
            }
            
            #photo {
                padding: 7px; 
                background-color: #FFF; 
                border: 1px solid #DDD;
            }
            .btn {
                background-color: #00a1ec;
                border-radius: 3px;
                font-family: Arial;
                border-radius: 3px;
                color: #EEE;
                font-size: 12px;
                padding: 7px 11px 7px 11px;
                border: solid #00a1ec 1px;
                text-decoration: none;
            }

            .btn:hover {
                color: #FFF;
                background-color: #007fba;
                text-decoration: none;
                border: 1px solid #007fba;
            }
            
            .btn-small {
                text-decoration: none;
                padding: 3px; border: 1px solid #CCC; 
                background-color: #EEE; color: #777777; 
                font-size: 11px; cursor: pointer;
            }
            .btn-small:hover {border: 1px solid #999; background-color: #CCC; color: #FFF;}
            
            .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
            .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
            #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}
            
            .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
            .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }
            
            #caption {
                font-size: 12px;
                color: #474747;
                font-weight: bold;
                padding: 2px 0px 3px 0px;
            }
            #divinput {
                margin-bottom: 5px;
                padding-bottom: 5px;
            }
            
            #div_item_sch {
                background-color: #EEE;
                color: #575757;
                padding: 5px 7px;
            }
            
            .form-style {
                font-size: 12px;
                color: #575757;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .form-title {
                padding: 11px 21px;
                margin-bottom: 2px;
                border-bottom: 1px solid #DDD;
                background-color: #EEE;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                font-weight: bold;
            }
            .form-content {
                padding: 21px;
            }
            .form-footer {
                border-top: 1px solid #DDD;
                padding: 11px 21px;
                margin-top: 2px;
                background-color: #EEE;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
            }
            #confirm {
                padding: 18px 21px;
                background-color: #FF6666;
                color: #FFF;
                border: 1px solid #CF5353;
            }
            #btn-confirm {
                padding: 3px; border: 1px solid #CF5353; 
                background-color: #CF5353; color: #FFF; 
                font-size: 11px; cursor: pointer;
            }
            .footer-page {
                font-size: 12px;
            }
            td { font-size: 12px; }
            #title-part {
                background-color: #007fba;
                font-size: 13px;
                font-weight: bold;
                color: #FFF;
                padding: 5px 7px;
                border-radius: 3px;
                margin: 3px 0px;
            }
        </style>
        <script language=JavaScript>
            function cmdSave(){
                document.frm_pay_general.command.value="<%=Command.SAVE%>";
                document.frm_pay_general.action="general_list.jsp";
                document.frm_pay_general.submit();
            }
            
            function cmdBack(){
                document.frm_pay_general.command.value="<%=Command.FIRST%>";
                document.frm_pay_general.action="general_list.jsp";
                document.frm_pay_general.submit();
            }
            
            function cmdConfirmDelete(oid){
                var x = confirm(" Are You Sure to Delete?");
                if(x){
                    document.frm_pay_general.command.value="<%=Command.DELETE%>";
                    document.frm_pay_general.action="general.jsp";
                    document.frm_pay_general.submit();
                }
            }
            
            function hideObjectForEmployee(){    
            } 
            
            function hideObjectForLockers(){ 
            }
            
            function hideObjectForCanteen(){
            }
            
            function hideObjectForClinic(){
            }
            
            function hideObjectForMasterdata(){
            }
            
        </script>
    </head>
    <body>
        <div class="header">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../../main/header.jsp" %>
                    <!-- #EndEditable --> 
                </td>
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
            </table>
        </div>
        <div id="menu_utama">
            <span id="menu_title">Setup <strong style="color:#333;"> / </strong>Company Detail</span>
        </div>

        <div class="content-main">
            
            <form name="frm_pay_general" method="post" action="">
            <input type="hidden" name="command" value="">
            <input type="hidden" name="start" value="<%=start%>">
            <input type="hidden" name="pay_general_oid" value="<%=oidPayGeneral%>">
            <input type="hidden" name="prev_command" value="<%=prevCommand%>">
            
            <table>
                <tr>
                    <td valign="top">
                        <div id="title-part">Company Setup</div>
                        <table>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Company Name</td>
                                <td valign="top">
                                    <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_COMPANY_NAME] %>"  value="<%=payGeneral.getCompanyName()%>" size="30" maxlength="64"> 
                                    *<%=frmPayGeneral.getErrorMsg(FrmPayGeneral.FRM_FIELD_COMPANY_NAME)%>
                                </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Address</td>
                                <td valign="top">
                                    <input type="text"  name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_COMP_ADDRESS] %>"  value="<%=payGeneral.getCompAddress()%>" size="30"  maxlength="128"> 
                                    *<%=frmPayGeneral.getErrorMsg(FrmPayGeneral.FRM_FIELD_COMP_ADDRESS)%>
                                </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">City / Pos Code</td>
                                <td valign="top">
                                    <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_CITY] %>"  value="<%=payGeneral.getCity()%>" size="16" maxlength="16"> / 
                                    <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_ZIP_CODE] %>"  value="<%=payGeneral.getZipCode()%>" size="6" maxlength="6"> 
                                </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Business Type</td>
                                <td valign="top"><input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_BUSSINESS_TYPE] %>" value="<%=payGeneral.getBussinessType()%>"  size="32" maxlength="128"> </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Tax Office</td>
                                <td valign="top"><input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_OFFICE] %>"  value="<%=payGeneral.getTaxOffice()%>"size="32" maxlength="128"> </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Work Days</td>
                                <td valign="top"><input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_WORK_DAYS] %>"  value="<%=payGeneral.getWorkDays()%>" size="6" maxlength="4"> </td>
                            </tr>
                            
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">NPWP / NPPKP</td>
                                <td valign="top">
                                    <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_REG_TAX_NR] %>"   value="<%=payGeneral.getRegTaxNr()%>" size="20" maxlength="20">
                                    / <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_REG_TAX_BUS_NR] %>" size="20"  value="<%=payGeneral.getRegTaxNr()%>" maxlength="20"> 
                                </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Establishment Date</td>
                                <td valign="top"><%=ControlDate.drawDateWithStyle(frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_REG_TAX_DATE], payGeneral.getRegTaxDate()==null?new Date():payGeneral.getRegTaxDate(), 1, -35,"formElemen")%></td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Tel./Fax</td>
                                <td valign="top">
                                    <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TEL] %>" value="<%=payGeneral.getTel()%>"  size="16">
                                    / 
                                    <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_FAX] %>"  value="<%=payGeneral.getFax()%>" size="16">
                                </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Leader Name & Position</td>
                                <td valign="top">
                                    <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_LEADER_NAME] %>"  value="<%=payGeneral.getLeaderName()%>" size="20">
                                    / 
                                    <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_LEADER_POSITION] %>" value="<%=payGeneral.getLeaderPosition()%>" size="20">
                                </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Tax Report Location</td>
                                <td valign="top"><input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_REP_LOCATION] %>"  value="<%=payGeneral.getTaxRepLocation()%>" size="32" maxlength="128"> </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Local Currency</td>
                                <td valign="top">
                                    <%
                                    Vector curr_value = new Vector(1,1);
                                    Vector curr_key = new Vector(1,1);
                                    Vector listCurr= PstCurrencyType.list(0, 0, "", " NAME ");
                                     for (int i = 0; i < listCurr.size(); i++) {
                                           CurrencyType curr = (CurrencyType) listCurr.get(i);
                                           curr_key.add(curr.getName());
                                            curr_value.add(String.valueOf(curr.getCode()));
                                     }
                                  %> <%= ControlCombo.draw(FrmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_LOCAL_CUR_CODE],"formElemen",null, ""+payGeneral.getLocalCurCode(), curr_value, curr_key) %> * <%=frmPayGeneral.getErrorMsg(FrmPayGeneral.FRM_FIELD_LOCAL_CUR_CODE)%>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td valign="top">
                        <div id="title-part">Tax Setup</div>
                        <table>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Tax Year</td>
                                <td valign="top">
                                    <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_YEAR] %>" value="<%=payGeneral.getTaxYear()%>" size="6" maxlength="4">
                                </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Tax Report Date</td>
                                <td valign="top"><input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_REP_DATE] %>"  value="<%=payGeneral.getTaxRepDate()%>" size="15" maxlength="12"> </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Tax Position Cost</td>
                                <td valign="top">
                                    <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_POS_COST_PCT] %>"  value="<%=payGeneral.getTaxPosCostPct()%>" size="6" maxlength="4"> %
                                </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Tax Round 1000</td>
                                <td valign="top">
                                    <% for(int i=0;i<PstPayGeneral.resignValue.length;i++){
                                    String strRes = "";

                                    if(payGeneral.getTaxRound1000()==PstPayGeneral.resignValue[i]){
                                       strRes = "checked";
                                     }
                                   %> <input type="radio" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_ROUND1000]%>" value="<%=PstPayGeneral.resignValue[i]%>" <%=strRes%> style="border:'none'">
                                    <%=PstPayGeneral.resignKey[i]%> 
                                    <% } %>
                                </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Tax Calculation Method</td>
                                <td valign="top">
                                     <%
                                        Vector vKey = new Vector();
                                        Vector vValue = new Vector();

                                        vKey.add(PstPayGeneral.STS_GROSS+"");
                                                                                            vKey.add(PstPayGeneral.STS_NETTO+"");


                                        vValue.add(PstPayGeneral.stMetode[PstPayGeneral.STS_GROSS]);
                                                                                            vValue.add(PstPayGeneral.stMetode[PstPayGeneral.STS_NETTO]);
                                                                                        %>
                                      <%=ControlCombo.draw(FrmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_CALC_MTD],"formElemen",null, ""+payGeneral.getTaxCalcMtd(), vKey, vValue) %> * <%=frmPayGeneral.getErrorMsg(FrmPayGeneral.FRM_FIELD_TAX_CALC_MTD)%>
                                </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Non Tax Income Employee</td>
                                <td valign="top">
                                    <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_NON_TAX_INCOME]%>" value="<%=payGeneral.getNonTaxIncome()%>" size="10" maxlength="16">
                                </td>
                            </tr>
                            
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Tax Form 1721 Sign By</td>
                                <td valign="top">
                                    <%
                                        Vector signKey = new Vector();
                                        Vector signValue = new Vector();

                                        signKey.add(PstPayGeneral.PEMOTONG_PAJAK+"");
                                                                                            signKey.add(PstPayGeneral.KUASA_PEMOTONG+"");


                                        signValue.add(PstPayGeneral.signBy[PstPayGeneral.PEMOTONG_PAJAK]);
                                                                                            signValue.add(PstPayGeneral.signBy[PstPayGeneral.KUASA_PEMOTONG]);
                                                                                        %>
                                      <%=ControlCombo.draw(FrmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_FORM_SIGN_BY],"formElemen",null, ""+payGeneral.getTaxFormSignBy(), signKey, signValue) %> * <%=frmPayGeneral.getErrorMsg(FrmPayGeneral.FRM_FIELD_TAX_FORM_SIGN_BY)%>
                                </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Tax Month</td>
                                <td valign="top">
                                    <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_MONTH] %>"  value="<%=payGeneral.getTaxMonth()%>" size="6" maxlength="4"> 
                                </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Uncomplete Payment of PPh21 </td>
                                <td valign="top">
                                    <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_PAID_PCT] %>"  value="<%=payGeneral.getTaxPaidPct()%>" size="6" maxlength="4"> %
                                </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Tax Position Cost Maks Rp.</td>
                                <td valign="top">
                                    <input type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_TAX_POS_COST_MAX] %>"  value="<%=payGeneral.getTaxPosCostMax()%>" size="10" maxlength="16"> 
                                </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Wife : Rp.</td>
                                <td valign="top">
                                    <input  type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_NON_TAX_WIFE]%>"  value="<%=payGeneral.getNonTaxWife()%>" size="10" maxlength="16"> 
                                </td>
                            </tr>
                            <tr>
                                <td valign="top" style="padding-right: 9px; font-weight: bold">Guarantee : Rp.</td>
                                <td valign="top">
                                    <input  type="text" name="<%=frmPayGeneral.fieldNames[FrmPayGeneral.FRM_FIELD_NON_TAX_DEPNT]%>"  value="<%=payGeneral.getNonTaxDepnt()%>" size="10" maxlength="16">
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            </form>
            <div>&nbsp;</div><div>&nbsp;</div>
            <a href="javascript:cmdSave()" class="btn" style="color:#FFF">Save Setup General</a>
            <a href="javascript:cmdConfirmDelete('<%=oidPayGeneral%>')" class="btn" style="color:#FFF">Delete Setup General</a>
            <a href="javascript:cmdBack()" class="btn" style="color:#FFF">Back to List General</a>
            <div>
                <%
                if(iErrCode != FRMMessage.NONE){
                     out.println(msgString);
                }
                %>
            </div>
        </div>
        <div class="footer-page">
            <table>
                <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                <tr>
                    <td valign="bottom"><%@include file="../../footer.jsp" %></td>
                </tr>
                <%} else {%>
                <tr> 
                    <td colspan="2" height="20" ><%@ include file = "../../main/footer.jsp" %></td>
                </tr>
                <%}%>
            </table>
        </div>
    </body>
</html>
