<%-- 
    Document   : userlist
    Created on : Apr 29, 2016, 3:05:05 PM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.admin.FrmAppUser"%>
<%@page import="com.dimata.harisma.form.admin.CtrlAppUser"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_USER_MANAGEMENT, AppObjInfo.OBJ_USER_LIST); %>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    /* VARIABLE DECLARATION */
    int recordToGet = 25;
    String order = " " + PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID];

    Vector listAppUser = new Vector(1, 1);
    ControlLine ctrLine = new ControlLine();

    /* GET REQUEST FROM HIDDEN TEXT */
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    long appUserOID = FRMQueryString.requestLong(request, "user_oid");
    int listCommand = FRMQueryString.requestInt(request, "list_command");
    String login = request.getParameter("login");
    String fullname = request.getParameter("fullname");
    String Sstatus = request.getParameter("status_login");
    int iStsLogin = Sstatus == null ? -1 : Integer.parseInt(Sstatus);

    //Update By Agus 10-02-2014
    String whereClause = "";
    if (login != null && login.length() > 0) {
        whereClause = "" + PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID] + " LIKE '%" + login + "%'";
    }
    if (fullname != null && fullname.length() > 0) {
        if (login == null || login.length() == 0) {
            whereClause = "" + PstAppUser.fieldNames[PstAppUser.FLD_FULL_NAME] + " LIKE '%" + fullname + "%'";
        } else {
            whereClause = whereClause + " AND " + PstAppUser.fieldNames[PstAppUser.FLD_FULL_NAME] + " LIKE '%" + fullname + "%'";
        }
    }
    if (Sstatus != null) {
        if (iStsLogin != -1 && (login == null || login.length() == 0)) {
            whereClause = "" + PstAppUser.fieldNames[PstAppUser.FLD_USER_STATUS] + " = '" + iStsLogin + "'";
        } else if (iStsLogin != -1 && (fullname == null || fullname.length() == 0)) {
            whereClause = "" + PstAppUser.fieldNames[PstAppUser.FLD_USER_STATUS] + " = '" + iStsLogin + "'";
        } else if (iStsLogin != -1) {
            whereClause = whereClause + " AND " + PstAppUser.fieldNames[PstAppUser.FLD_USER_STATUS] + " = '" + iStsLogin + "'";
        }
    }

    if (listCommand == Command.NONE) {
        listCommand = Command.LIST;
    }
    AppUser appUser = new AppUser();
    CtrlAppUser ctrlAppUser = new CtrlAppUser(request);
    FrmAppUser frmAppUser = ctrlAppUser.getForm();

    int vectSize = PstAppUser.getCount(whereClause);
    start = ctrlAppUser.actionList(listCommand, start, vectSize, recordToGet);

    listAppUser = PstAppUser.listPartObj(start, recordToGet, whereClause, order);

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HAIRISMA - User List</title>
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
            
        </style>
        <script language="JavaScript">
            <% if (privAdd) {%>
                function addNew(){
                    document.frmAppUser.user_oid.value="0";
                    document.frmAppUser.list_command.value="<%=listCommand%>";
                    document.frmAppUser.command.value="<%=Command.ADD%>";
                    document.frmAppUser.action="useredit.jsp";
                    document.frmAppUser.submit();
                }
            <%}%>
                
                function cmdEdit(oid){
                    document.frmAppUser.user_oid.value=oid;
                    document.frmAppUser.list_command.value="<%=listCommand%>";
                    document.frmAppUser.command.value="<%=Command.EDIT%>";
                    document.frmAppUser.action="useredit.jsp";
                    document.frmAppUser.submit();
                }
                
                function cmdListFirst(){
                    document.frmAppUser.command.value="<%=Command.FIRST%>";
                    document.frmAppUser.list_command.value="<%=Command.FIRST%>";
                    document.frmAppUser.action="userlist.jsp";
                    document.frmAppUser.submit();
                }
                function cmdListPrev(){
                    document.frmAppUser.command.value="<%=Command.PREV%>";
                    document.frmAppUser.list_command.value="<%=Command.PREV%>";
                    document.frmAppUser.action="userlist.jsp";
                    document.frmAppUser.submit();
                }
                
                function cmdListNext(){
                    document.frmAppUser.command.value="<%=Command.NEXT%>";
                    document.frmAppUser.list_command.value="<%=Command.NEXT%>";
                    document.frmAppUser.action="userlist.jsp";
                    document.frmAppUser.submit();
                }
                function cmdListLast(){
                    document.frmAppUser.command.value="<%=Command.LAST%>";
                    document.frmAppUser.list_command.value="<%=Command.LAST%>";
                    document.frmAppUser.action="userlist.jsp";
                    document.frmAppUser.submit();
                }
                function cmdSearch(){
                    document.frmAppUser.command.value="<%=Command.SUBMIT%>";
                    document.frmAppUser.action="userlist.jsp";
                    document.frmAppUser.submit();
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
            <span id="menu_title" style="font-weight: bold;">System <strong style="color:#333;"> / </strong>User List</span>
        </div>

        <div class="content-main">
            <form name="frmAppUser" method="post" action="">
                <input type="hidden" name="command" value="">
                <input type="hidden" name="user_oid" value="<%=appUserOID%>">
                <input type="hidden" name="vectSize" value="<%=vectSize%>">
                <input type="hidden" name="start" value="<%=start%>">
                <input type="hidden" name="list_command" value="<%=listCommand%>">
                <table class="tblForm">
                    <tr>
                        <td colspan="2"><strong>Search For User List</strong></td>
                    </tr>
                    <tr>
                        <td><strong>Login ID</strong></td>
                        <td><input onFocus="this.select()" name="login"  type="text" id="login" value="<%=login==null ||login.length()==0?"":login%>" /></td>
                    </tr>
                    <tr>
                        <td><strong>Full Name</strong></td>
                        <td><input type="text" name="fullname" id="fullname" size="50" value="<%=fullname==null ||fullname.length()==0?"":fullname%>" /></td>
                    </tr>
                    <tr>
                        <td><strong>Status</strong></td>
                        <td>
                            <%
                                ControlCombo cmbox = new ControlCombo();
                                Vector sts = AppUser.getStatusTxtsVer2();
                                Vector stsVals = AppUser.getStatusValsVer2();
                            %>
                            <%=cmbox.draw("status_login" ,"formElemen", null, Integer.toString(iStsLogin), stsVals, sts)%> 
                            &nbsp;<%= frmAppUser.getErrorMsg(frmAppUser.FRM_USER_STATUS) %>
                            <input type="submit" class="btn-small" value="Search" >
                        </td>
                    </tr>
                </table>
                <div>&nbsp;</div>
                <div>
                    <% if ((listAppUser!=null)&&(listAppUser.size()>0)){ %>
                    <table class="tblStyle" width="100%">
                        <tr>
                            <td class="title_tbl">Login ID</td>
                            <td class="title_tbl">Full Name</td>
                            <td class="title_tbl">Status</td>
                        </tr>
                        <%
                        String trCSS = "tr1";
                        for (int i=0; i<listAppUser.size(); i++){
                            AppUser appUserObj = (AppUser) listAppUser.get(i);
                            if (i % 2 == 0){
                                trCSS = "tr1";
                            } else {
                                trCSS = "tr2";
                            }
                            %>
                            <tr class="<%= trCSS %>">
                                <td>
                                    <a href="javascript:cmdEdit('<%= appUserObj.getOID() %>')">
                                    <%=String.valueOf(appUserObj.getLoginId())%>
                                    </a>
                                </td>
                                <td><%=String.valueOf(appUserObj.getFullName())%></td>
                                <td><%=String.valueOf(appUserObj.getStatusTxt(appUserObj.getUserStatus()))%></td>
                            </tr>
                            <%
                        }
                        %>
                        
                    </table>
                    <%}%>
                </div>
                <div>
                    <%
                    ctrLine.setLocationImg(approot+"/images");
                    ctrLine.initDefault();
                    out.println(ctrLine.drawImageListLimit(listCommand,vectSize,start,recordToGet));
                    %> 
                </div>
                <div>&nbsp;</div>
                <div>
                    <% if (privAdd){%>
                    <a href="javascript:addNew()" class="btn" style="color:#FFF;">Add New User</a>
                    <%}%>
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
