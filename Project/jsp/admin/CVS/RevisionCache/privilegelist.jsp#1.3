<%-- 
    Document   : privilegelist
    Created on : May 2, 2016, 9:23:00 AM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.form.admin.FrmAppPriv"%>
<%@page import="com.dimata.harisma.form.admin.CtrlAppPriv"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_USER_MANAGEMENT, AppObjInfo.OBJ_USER_PRIVILEGE); %>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%

    /* VARIABLE DECLARATION */
    int recordToGet = 25;
    String whereClause = "";
    String order = " " + PstAppPriv.fieldNames[PstAppPriv.FLD_PRIV_NAME];

    Vector listAppPriv = new Vector(1, 1);
    ControlLine ctrLine = new ControlLine();

    /* GET REQUEST FROM HIDDEN TEXT */
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    long appPrivOID = FRMQueryString.requestLong(request, "appriv_oid");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int prevSaveCommand = FRMQueryString.requestInt(request, "prev_save_command");
    String privilege = request.getParameter("privilegeNameSearch");

    if (privilege != null && privilege.length() > 0) {
        whereClause = "" + PstAppPriv.fieldNames[PstAppPriv.FLD_PRIV_NAME] + " LIKE '%" + privilege + "%'";
    }

    CtrlAppPriv ctrlAppPriv = new CtrlAppPriv(request);
    FrmAppPriv frmAppPriv = ctrlAppPriv.getForm();

    int vectSize = PstAppPriv.getCount(whereClause);

    int excCode = ctrlAppPriv.action(iCommand, appPrivOID, start, vectSize, recordToGet);
    vectSize = PstAppPriv.getCount(whereClause);
    String msgString = ctrlAppPriv.getMessage();
    AppPriv appPriv = ctrlAppPriv.getAppPriv();

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlAppPriv.getStart();
    }

    if ((iCommand == Command.SAVE) && (frmAppPriv.errorSize() < 1)) {
        start = PstAppPriv.findLimitStart(appPriv.getOID(), recordToGet, "", order);
    }

    order = PstAppPriv.fieldNames[PstAppPriv.FLD_PRIV_NAME];
    listAppPriv = PstAppPriv.list(start, recordToGet, whereClause, order);

    /* TO HANDLE CONDITION AFTER DELETE LAST, IF START LIMIT IS BIGGER THAN VECT SIZE, GET LIST FIRST */
    if (((listAppPriv == null) || (listAppPriv.size() < 1))) {
        start = 0;
        listAppPriv = PstAppPriv.list(start, recordToGet, whereClause, order);

    }

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HAIRISMA - System Privilege List</title>
        <script language="JavaScript">
            function addNew(){
                document.frmAppPriv.appriv_oid.value="0";
                document.frmAppPriv.prev_command.value="<%=prevCommand%>";
                document.frmAppPriv.command.value="<%=Command.ADD%>";
                document.frmAppPriv.action="privilegelist.jsp";
                document.frmAppPriv.submit();
            }
            
            function cmdEdit(oid){
                document.frmAppPriv.appriv_oid.value=oid;
                document.frmAppPriv.prev_command.value="<%=prevCommand%>";
                document.frmAppPriv.command.value="<%=Command.EDIT%>";
                document.frmAppPriv.action="privilegelist.jsp";
                document.frmAppPriv.submit();
            }
            
            
            function cmdSave(){
                document.frmAppPriv.command.value="<%=Command.SAVE%>";
                document.frmAppPriv.prev_command.value="<%=prevCommand%>";
                document.frmAppPriv.action="privilegelist.jsp";
                document.frmAppPriv.submit();
            }
            
            function cmdEditObj(oid){
                document.frmAppPriv.appriv_oid.value=oid;
                document.frmAppPriv.prev_command.value="<%=prevCommand%>";
                document.frmAppPriv.command.value="<%=Command.LIST%>";
                document.frmAppPriv.action="privilegeedit.jsp";
                document.frmAppPriv.submit();
            }
            
            function cmdAsk(oid){
                document.frmAppPriv.appriv_oid.value=oid;
                document.frmAppPriv.prev_command.value="<%=prevCommand%>";
                document.frmAppPriv.command.value="<%=Command.ASK%>";
                document.frmAppPriv.action="privilegelist.jsp";
                document.frmAppPriv.submit();
            }
            function cmdDelete(oid){
                document.frmAppPriv.appriv_oid.value=oid;
                document.frmAppPriv.prev_command.value="<%=prevCommand%>";
                document.frmAppPriv.command.value="<%=Command.DELETE%>";
                document.frmAppPriv.action="privilegelist.jsp";
                document.frmAppPriv.submit();
            }
            
            function cmdCancel(){
                document.frmAppPriv.prev_command.value="<%=prevCommand%>";
                document.frmAppPriv.command.value="<%=Command.EDIT%>";
                document.frmAppPriv.action="privilegelist.jsp";
                document.frmAppPriv.submit();
            }
            
            
            function cmdListFirst(){
                document.frmAppPriv.command.value="<%=Command.FIRST%>";
                document.frmAppPriv.prev_command.value="<%=Command.FIRST%>";
                document.frmAppPriv.action="privilegelist.jsp";
                document.frmAppPriv.submit();
            }
            function cmdListPrev(){
                document.frmAppPriv.command.value="<%=Command.PREV%>";
                document.frmAppPriv.prev_command.value="<%=Command.PREV%>";
                document.frmAppPriv.action="privilegelist.jsp";
                document.frmAppPriv.submit();
            }
            
            function cmdListNext(){
                document.frmAppPriv.command.value="<%=Command.NEXT%>";
                document.frmAppPriv.prev_command.value="<%=Command.NEXT%>";
                document.frmAppPriv.action="privilegelist.jsp";
                document.frmAppPriv.submit();
            }
            function cmdListLast(){
                document.frmAppPriv.command.value="<%=Command.LAST%>";
                document.frmAppPriv.prev_command.value="<%=Command.LAST%>";
                document.frmAppPriv.action="privilegelist.jsp";
                document.frmAppPriv.submit();
            }
            
            function cmdBack(){
                document.frmAppPriv.command.value="<%=Command.BACK%>";
                document.frmAppPriv.action="privilegelist.jsp";
                document.frmAppPriv.submit();
            }
            function cmdSearch(){
                document.frmAppPriv.command.value="<%=Command.SUBMIT%>";
                document.frmAppPriv.action="privilegelist.jsp";
                document.frmAppPriv.submit();
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
            <span id="menu_title" style="font-weight: bold;">User Management <strong style="color:#333;"> / </strong>Privilege List</span>
        </div>

        <div class="content-main">
            <form name="search" method="get" action="">
                <h2>Search For Privilege Name</h2>
                <div id="caption">Privilege Name</div>
                <div id="divinput">
                    <input onFocus="this.select()" name="privilegeNameSearch"  type="text" id="privilege" size="35" maxlength="35">
                    <input class="btn-small" type="submit" value="search" >
                </div>
            </form>
            <form name="frmAppPriv" method="post" action="">
                <input type="hidden" name="command" value="">
                <input type="hidden" name="appriv_oid" value="<%=appPrivOID%>">
                <input type="hidden" name="vectSize" value="<%=vectSize%>">
                <input type="hidden" name="start" value="<%=start%>">
                <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                <input type="hidden" name="prev_save_command" value="<%=prevSaveCommand%>">
                <%if(privAdd  && (iCommand!=Command.ADD)&&(iCommand!=Command.ASK)&&(iCommand!=Command.EDIT)&&(frmAppPriv.errorSize()<1)){%>       
                <div>&nbsp;</div>
                <a href="javascript:addNew()" class="btn" style="color:#FFF">Add New Privilege</a>
                <div>&nbsp;</div>
                <%}%>      
                <%if(((iCommand==Command.SAVE)&&(frmAppPriv.errorSize()>0))||(iCommand==Command.ADD)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                <h2><%= appPrivOID!=0 ? "Edit" : "Add"%>&nbsp;Privilege</h2>
                <div id="caption">Privilege Name</div>
                <div id="divinput">
                    <input type="text" name="<%=frmAppPriv.fieldNames[frmAppPriv.FRM_PRIV_NAME] %>" value="<%=appPriv.getPrivName()%>" class="formElemen" size="30">
                    * &nbsp;<%= frmAppPriv.getErrorMsg(frmAppPriv.FRM_PRIV_NAME) %>
                </div>
                
                <div id="caption">Description</div>
                <div id="divinput">
                    <textarea name="<%=frmAppPriv.fieldNames[frmAppPriv.FRM_DESCRIPTION] %>" cols="45" rows="3" class="formElemen"><%=appPriv.getDescr()%></textarea>
                </div>
                
                <div id="caption">Creation/Update Date</div>
                <div id="divinput"><%=ControlDate.drawDate(frmAppPriv.fieldNames[FrmAppPriv.FRM_REG_DATE], appPriv.getRegDate(), 0, -30)%></div>
                <div>
                    <%
                        ctrLine.setLocationImg(approot+"/images");
                        ctrLine.initDefault();
                        ctrLine.setTableWidth("80%");
                        String scomDel = "javascript:cmdAsk('"+appPrivOID+"')";
                        String sconDelCom = "javascript:cmdDelete('"+appPrivOID+"')";
                        String scancel = "javascript:cmdCancel('"+appPrivOID+"')";
                        ctrLine.setBackCaption("Back to Privilege List");
                        ctrLine.setCommandStyle("buttonlink");
                        ctrLine.setSaveCaption("Save Privilege");
                        ctrLine.setDeleteCaption("Delete Privilege");
                        ctrLine.setConfirmDelCaption("Yes Delete Privilege");
                        ctrLine.setAddCaption("");

                        if (privDelete){
                                ctrLine.setConfirmDelCommand(sconDelCom);
                                ctrLine.setDeleteCommand(scomDel);
                                ctrLine.setEditCommand(scancel);
                        }else{ 
                                ctrLine.setConfirmDelCaption("");
                                ctrLine.setDeleteCaption("");
                                ctrLine.setEditCaption("");
                        }

                        if(privAdd == false  && privUpdate == false){
                                ctrLine.setSaveCaption("");
                        }

                        if (privAdd == false){
                                ctrLine.setAddCaption("");
                        }
                        %>
                    <%= ctrLine.drawImage(iCommand, excCode, msgString)%>
                </div>
                <div>
                    <% if((privAdd && privUpdate) && (iCommand != Command.ASK || iCommand == Command.DELETE) && (appPrivOID != 0)){%>
                        <a href="javascript:cmdEditObj('<%=appPrivOID%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10011','','<%=approot%>/images/BtnEditOn.jpg',1)"><img name="Image10011" border="0" src="<%=approot%>/images/BtnEdit.jpg" width="24" height="24" alt="Edit Module Access"></a>
                        <a href="javascript:cmdEditObj('<%=appPrivOID%>')" class="command">Edit Module Access</a>
                    <%}%>
                </div>
                <% } %>
                <div>&nbsp;</div>                      
                <% if ((listAppPriv!=null)&&(listAppPriv.size()>0)){ %>
                <div>
                    <table class="tblStyle">
                        <tr>
                            <td class="title_tbl">Privilege Name</td>
                            <td class="title_tbl">Description</td>
                            <td class="title_tbl">Creation Date</td>
                        </tr>
                    
                    <%
                    String regdatestr = "";
                    String trCSS = "tr1";
                    for (int i = 0; i < listAppPriv.size(); i++) {
                        AppPriv appPrivObj = (AppPriv) listAppPriv.get(i);
                        if (i % 2 == 0){
                            trCSS = "tr1";
                        } else {
                            trCSS = "tr2";
                        }
                        try {
                            Date regdate = appPriv.getRegDate();
                            regdatestr = Formater.formatDate(regdate, "dd MMMM yyyy");
                        } catch (Exception e) {
                            regdatestr = "";
                        }
                        %>
                        <tr class="<%= trCSS %>">
                            <td><a href="javascript:cmdEdit('<%=appPrivObj.getOID()%>')"><%=appPrivObj.getPrivName()%></a></td>
                            <td><%= appPrivObj.getDescr() %></td>
                            <td><%= regdatestr %></td>
                        </tr>
                        <%
                    }
                    %>
                    </table>
                </div>
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
                                if ((prevSaveCommand == Command.ADD) && (iCommand == Command.SAVE) && (frmAppPriv.errorSize() < 1)) {
                                    cmd = Command.LAST;
                                } else {
                                    if ((iCommand == Command.SAVE) && (frmAppPriv.errorSize() < 1)) {
                                        cmd = PstAppPriv.findLimitCommand(start, recordToGet, vectSize);
                                    } else {
                                        cmd = prevCommand;
                                    }
                                }
                                        
                            }
                        }
                                
                        ctrLine.setLocationImg(approot + "/images");
                        ctrLine.initDefault();
                    %>
                    <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%> 
                </div>
                <% } %>
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
