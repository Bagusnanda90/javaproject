<%-- 
    Document   : groupedit
    Created on : May 4, 2016, 3:08:34 PM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="com.dimata.harisma.form.admin.FrmAppGroup"%>
<%@page import="com.dimata.harisma.form.admin.CtrlAppGroup"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@ include file = "../main/javainit.jsp" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_USER_MANAGEMENT, AppObjInfo.OBJ_USER_GROUP); %>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

/* VARIABLE DECLARATION */ 

ControlLine ctrLine = new ControlLine();

/* GET REQUEST FROM HIDDEN TEXT */
int iCommand = FRMQueryString.requestCommand(request);

long appGroupOID = FRMQueryString.requestLong(request,"group_oid");
int start = FRMQueryString.requestInt(request, "start"); 

CtrlAppGroup ctrlAppGroup = new CtrlAppGroup(request);

FrmAppGroup frmAppGroup = ctrlAppGroup.getForm();
 
int iErrCode = ctrlAppGroup.action(iCommand,appGroupOID);
String msgString = ctrlAppGroup.getMessage();
AppGroup appGroup = ctrlAppGroup.getAppGroup();
Vector listPrivilege = new Vector();
if (appGroupOID != 0){
    listPrivilege = SessAppGroup.getGroupPriv(appGroupOID);
}
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>System Group Edit</title>
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <style type="text/css">
            .tblStyle {border-collapse: collapse; }
            .tblStyle td {padding: 5px 7px; border: 1px solid #CCC; font-size: 12px; }
            .tr1 {background-color: #FFF;}
            .tr2 {background-color: #EEE;}
            .tblForm { }
            .tblForm td {padding: 5px 7px; font-weight: bold; font-size: 12px; }
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}

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
                margin: 17px 0px;
                padding: 5px 7px;
                border-radius: 3px;
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
                padding: 3px;
                background-color: #00a1ec; color: #EEE; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
                border:1px solid #00a1ec;
            }
            .btn-small:hover {background-color: #007fba; color: #FFF; border:1px solid #007fba;}
            
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
            #item {
                font-weight: bold;
                background-color: #EEE;
                border-radius: 3px;
                padding: 5px;
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
            .tips {
                background-color: #FFF;
                border-radius: 3px;
                padding: 7px 9px;
            }
        </style>
        <script language="JavaScript">

            function cmdCancel(){
                    //document.frmAppGroup.group_oid.value=oid;
                    document.frmAppGroup.command.value="<%=Command.EDIT%>";
                    document.frmAppGroup.action="groupedit.jsp";
                    document.frmAppGroup.submit();
            }

            <% if(privAdd || privUpdate) {%>
            function cmdSave(){
                    document.frmAppGroup.command.value="<%=Command.SAVE%>";
                    document.frmAppGroup.action="groupedit.jsp";
                    document.frmAppGroup.submit();
            }

            <%}%>

            <% if(privDelete) {%>
            function cmdAsk(oid){
                    document.frmAppGroup.group_oid.value=oid;
                    document.frmAppGroup.command.value="<%=Command.ASK%>";
                    document.frmAppGroup.action="groupedit.jsp";
                    document.frmAppGroup.submit();
            }
            function cmdDelete(oid){
                    document.frmAppGroup.group_oid.value=oid;
                    document.frmAppGroup.command.value="<%=Command.DELETE%>";
                    document.frmAppGroup.action="groupedit.jsp";
                    document.frmAppGroup.submit();
            }
            <%}%>
            function cmdBack(oid){
                    document.frmAppGroup.group_oid.value=oid;
                    document.frmAppGroup.command.value="<%=Command.LIST%>";
                    document.frmAppGroup.action="grouplist.jsp";
                    document.frmAppGroup.submit();
            }

        </script>
    </head>
    <body>
        <div class="header">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
                    <%@ include file = "../main/header.jsp" %>
                    <!-- #EndEditable --> 
                </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
                    <%@ include file = "../main/mnmain.jsp" %>
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
            <span id="menu_title" style="font-weight: bold;">User Management <strong style="color:#333;"> / </strong>Group Edit</span>
        </div>
        <div class="content-main">
            <form name="frmAppGroup" method="post" action="">
            <input type="hidden" name="command" value="<%= iCommand %>">
            <input type="hidden" name="group_oid" value="<%=appGroupOID%>">
            <input type="hidden" name="start" value="<%=start%>">
            <div id="caption">
                Group Name
            </div>
            <div id="divinput">
                <input type="text" name="<%=frmAppGroup.fieldNames[frmAppGroup.FRM_GROUP_NAME] %>" value="<%=appGroup.getGroupName()%>" class="formElemen" size="70">
                * &nbsp;<%= frmAppGroup.getErrorMsg(frmAppGroup.FRM_GROUP_NAME) %>
            </div>
            <div id="caption">
                Description
            </div>
            <div id="divinput">
                <textarea name="<%=frmAppGroup.fieldNames[frmAppGroup.FRM_DESCRIPTION] %>" cols="50" rows="3" class="formElemen"><%=appGroup.getDescription()%></textarea>
            </div>
            <div id="caption">
                Created / Update Date
            </div>
            <div id="divinput">
                <%=ControlDate.drawDate(frmAppGroup.fieldNames[FrmAppGroup.FRM_REG_DATE], appGroup.getRegDate(),"formElemen",  0, -30)%> 
            </div>
            <div id="caption">
                Privilege Assigned
            </div>
            <div>
                <a class="btn" style="color:#FFF;" href="javascript:cmdAddPriv()">Tambah Privilege</a>
            </div>
            <div class="box">
                <%
                if (iCommand == Command.EDIT){
                %>
                <table cellpadding="2" cellspacing="2">
                <%
                int cols = 1;
                if (listPrivilege != null && listPrivilege.size()>0){
                    for (int i=0; i<listPrivilege.size(); i++){
                        AppPriv appPriv = (AppPriv) listPrivilege.get(i);
                        if (cols == 1){
                            %>
                            <tr>
                            <%
                        }
                        %>
                        <td><strong><%=appPriv.getPrivName()%></strong>&nbsp;<a href="javascript:cmdHapus()">&times;</a></td>
                        <%
                        if (cols == 3){
                            %>
                        </tr>
                            <%
                        }
                        %>
                        <%
                        if (cols < 3){
                            cols++;
                        } else {
                            cols = 1;
                        }
                    }
                }
                %>
                </table>
                <%
                } else {
                %>
                
                <div class="tips">
                    <table>
                        <tr>
                            <td style="padding-right: 9px"><img width="16" src="<%=approot%>/images/tips.png" /></td>
                            <td><strong>Tips:</strong> Simpan data, selanjutnya Edit untuk melakukan penambahan privilege.</td>
                        </tr>
                    </table>
                </div>
                <%
                }
                %>
            </div>
            
            <div>
                <a href="javascript:cmdSave()" class="btn" style="color:#FFF">Save Group</a>
                <a href="javascript:cmdBack()" class="btn" style="color:#FFF">Back to list</a>
            </div>
            </form>
        </div>
        <div class="footer-page">
            <table>
                <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                <tr>
                    <td valign="bottom"><%@include file="../footer.jsp" %></td>
                </tr>
                <%} else {%>
                <tr> 
                    <td colspan="2" height="20" ><%@ include file = "../main/footer.jsp" %></td>
                </tr>
                <%}%>
            </table>
        </div>
    </body>
</html>
