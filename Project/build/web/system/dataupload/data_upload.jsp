<%-- 
    Document   : data_upload
    Created on : Feb 27, 2016, 10:49:52 AM
    Author     : khirayinnura
--%>

<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.system.entity.dataupload.*"%>
<%@page import="com.dimata.system.form.dataupload.*"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.system.entity.dataupload.*"%>
<%@page import="com.dimata.system.form.dataupload.*"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_LEVEL);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%!    public String drawList(int command, Vector objectClass, long dataMainId, long oidObject, String className) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("Title", "");
        ctrlist.addHeader("Group", "");
        ctrlist.addHeader("Description", "");

        if (command != Command.EDIT) {
            ctrlist.setLinkRow(0);
        }
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;

        Vector rowx = new Vector();

        String dataGroupTitle = "";

        Vector dataGroup_value = new Vector(1, 1);
        Vector dataGroup_key = new Vector(1, 1);
        Vector listDataGroup = PstDataUploadGroup.list(0, 0, "", "DATA_GROUP_TITLE");
        dataGroup_value.add("" + 0);
        dataGroup_key.add("select");
        for (int i = 0; i < listDataGroup.size(); i++) {
            DataUploadGroup dataUploadGroup = (DataUploadGroup) listDataGroup.get(i);
            dataGroup_key.add(dataUploadGroup.getDataGroupTitle());
            dataGroup_value.add(String.valueOf(dataUploadGroup.getOID()));
        }

        for (int i = 0; i < objectClass.size(); i++) {
            DataUploadMain dataUploadMain = (DataUploadMain) objectClass.get(i);
            rowx = new Vector();
            if (dataMainId == dataUploadMain.getOID()) {
                index = i;
            }
            if (command == Command.EDIT && dataUploadMain.getOID() == dataMainId) {
                rowx.add("" + ControlCombo.draw(FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_DATA_GROUP_ID], "formElemen", null, "" + dataUploadMain.getDataGroupId(), dataGroup_value, dataGroup_key));
                rowx.add("<input type=\"text\" name=\"" + FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_DATA_MAIN_TITLE] + "\"  value=\"" + dataUploadMain.getDataMainTitle() + "\" class=\"formElemen\">");
                rowx.add("<textarea name=\"" + FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_DATA_MAIN_DESC] + "\" class=\"elemenForm\" cols=\"30\" rows=\"3\">" + dataUploadMain.getDataMainDesc() + "</textarea>");
            } else {
                try {
                    DataUploadGroup dataUpGroup = new DataUploadGroup();
                    dataUpGroup = PstDataUploadGroup.fetchExc(dataUploadMain.getDataGroupId());
                    dataGroupTitle = dataUpGroup.getDataGroupTitle();
                } catch (Exception e) {
                }

                String btn = "<div class=\"dropdown\">";
                btn += "<span><strong>" + dataUploadMain.getDataMainTitle() + "</strong></span>";
                btn += "<div class=\"dropdown-content\">";
                btn += "<div id=\"div_drop\"><a href=\"javascript:cmdAdd('" + dataUploadMain.getOID() + "')\">Manage Data</a></div>";
                btn += "<div id=\"div_drop\"><a class=\"modul\" href=\"data_upload_Detail.jsp\">Upload</a></div>";
                btn += "<div id=\"div_drop\"><a href=\"javascript:cmdAsk('" + dataUploadMain.getOID() + "')\">Delete</a></div>";
                btn += "</div>";
                btn += "</div>";

                rowx.add(btn);
                rowx.add("" + dataGroupTitle);
                rowx.add(dataUploadMain.getDataMainDesc());
            }

            lstData.add(rowx);
            lstLinkData.add(String.valueOf(dataUploadMain.getOID()));
        }

        rowx = new Vector();
        if (command == Command.ADD) {
            rowx.add("" + ControlCombo.draw(FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_DATA_GROUP_ID], "formElemen", null, "", dataGroup_value, dataGroup_key));
            rowx.add("<input type=\"hidden\" name=\"" + FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_OBJECT_ID] + "\"  value=\"" + oidObject + "\" class=\"formElemen\">"
                    + "<input type=\"hidden\" name=\"" + FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_OBJECT_CLASS] + "\"  value=\"" + className + "\" class=\"formElemen\">"
                    + "<input type=\"text\" name=\"" + FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_DATA_MAIN_TITLE] + "\"  value=\"\" class=\"formElemen\">");
            rowx.add("<textarea name=\"" + FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_DATA_MAIN_DESC] + "\" class=\"elemenForm\" cols=\"30\" rows=\"3\"></textarea>");

            lstData.add(rowx);
        }

        return ctrlist.draw(index);

        //return ctrlist.draw();
    }

%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    int cmdDetail = FRMQueryString.requestInt(request, "cmd");
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidDataMain = FRMQueryString.requestLong(request, "data_main_oid");
    long oidDataDetail = FRMQueryString.requestLong(request, "data_detail_oid");
    long oidObject = FRMQueryString.requestLong(request, "object_id"); //oid dari object misal: modul family member, maka oid family member yang diambil
    String className = FRMQueryString.requestString(request, "classname");


    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    int iErrCodeDetail = FRMMessage.NONE;
    String whereClause = "OBJECT_ID='" + oidObject + "' AND OBJECT_CLASS='" + className + "'";
    String orderClause = PstDataUploadMain.fieldNames[PstDataUploadMain.FLD_DATA_MAIN_TITLE];

    CtrlDataUploadMain ctrlDataUploadMain = new CtrlDataUploadMain(request);
    CtrlDataUploadDetail ctrlDataUploadDetail = new CtrlDataUploadDetail(request);
    ControlLine ctrLine = new ControlLine();
    Vector listDataUploadMain = new Vector(1, 1);
    Vector listDataUploadDetail = new Vector(1, 1);

    /*switch statement */
    iErrCode = ctrlDataUploadMain.action(iCommand, oidDataMain);
    iErrCodeDetail = ctrlDataUploadDetail.action(cmdDetail, oidDataDetail);
    /* end switch*/
    FrmDataUploadMain frmDataUploadMain = ctrlDataUploadMain.getForm();

    DataUploadMain dataUploadMain = ctrlDataUploadMain.getDataUploadMain();
    msgString = ctrlDataUploadMain.getMessage();


    /*switch list CareerPath*/
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidDataMain == 0)) {
        start = PstDataUploadMain.findLimitStart(dataUploadMain.getOID(), recordToGet, whereClause, orderClause);
    }

    /*count list All CareerPath*/
    int vectSize = PstDataUploadMain.getCount(whereClause);

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlDataUploadMain.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listDataUploadMain = PstDataUploadMain.list(start, recordToGet, whereClause, orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listDataUploadMain.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listDataUploadMain = PstDataUploadMain.list(start, recordToGet, whereClause, orderClause);
    }

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel="stylesheet" href="../../styles/colorbox.css" />
        <script src="../../javascripts/jquery.min.js"></script>
        <script src="../../javascripts/jquery.colorbox.js"></script>

        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable -->
                
        <script type="text/javascript">
            function cmdAdd(){
                //document.frm_data_upload.object_id.value=idClass;
                document.frm_data_upload.command.value="<%=Command.ADD%>";
                document.frm_data_upload.action="data_upload.jsp";
                document.frm_data_upload.submit();
            }
            
            function cmdSave(){
                document.frm_data_upload.command.value="<%=Command.SAVE%>";
                document.frm_data_upload.prev_command.value="<%=prevCommand%>";
                document.frm_data_upload.action="data_upload.jsp";
                document.frm_data_upload.submit();
            }
            
            function cmdSaveDetail(oidDataMain, oidDataDetail){
                document.frm_data_upload.data_main_oid.value=oidDataMain;
                document.frm_data_upload.data_detail_oid.value=oidDataDetail;
                document.frm_data_upload.command.value="<%=Command.NONE%>";
                document.frm_data_upload.cmd.value="<%=Command.SAVE%>";
                document.frm_data_upload.prev_command.value="<%=prevCommand%>";
                document.frm_data_upload.action="data_upload.jsp";
                document.frm_data_upload.submit();
            }

            function cmdEdit(oidDataMain){
                document.frm_data_upload.data_main_oid.value=oidDataMain;
                document.frm_data_upload.command.value="<%=Command.EDIT%>";
                document.frm_data_upload.action="data_upload.jsp";
                document.frm_data_upload.submit();
            }

            function cmdCancel(oidDataMain){
                document.frm_data_upload.data_main_oid.value=oidDataMain;
                document.frm_data_upload.command.value="<%=Command.NONE%>";
                document.frm_data_upload.cmd.value="<%=Command.NONE%>";
                document.frm_data_upload.prev_command.value="<%=prevCommand%>";
                document.frm_data_upload.action="data_upload.jsp";
                document.frm_data_upload.submit();
            }
            
            function cmdCancelDetail(){
                document.frm_data_upload.cmd.value="<%=Command.NONE%>";
                document.frm_data_upload.prev_command.value="<%=prevCommand%>";
                document.frm_data_upload.action="data_upload.jsp";
                document.frm_data_upload.submit();
            }
            
            function cmdAsk(oidMain){
                document.frm_data_upload.data_main_oid.value=oidMain;
                document.frm_data_upload.command.value="<%=Command.ASK%>";
                document.frm_data_upload.prev_command.value="<%=prevCommand%>";
                document.frm_data_upload.action="data_upload.jsp";
                document.frm_data_upload.submit();
            }
            
            function cmdAsk(oid){
                document.getElementById("data_main_oid").value=oid;
                document.getElementById("confirm").style.visibility="visible";
            }

            function cmdNoDel(){
                document.getElementById("data_main_oid").value="0";
                document.getElementById("confirm").style.visibility="hidden";
            }
            
            function cmdDelete(){
                var oid = document.getElementById("data_main_oid").value;
                document.frm_data_upload.data_main_oid.value=oid;
                document.frm_data_upload.command.value="<%=Command.DELETE%>";
                document.frm_data_upload.action="data_upload.jsp";
                document.frm_data_upload.submit();
            }
            
            function loadDetail(oid,detId,cmd) {
                
                if (oid.length == 0) { 
                    document.getElementById("txtHint").innerHTML = "";
                    return;
                    
                } else {
                   // alert("ok");
                    var xmlhttp = new XMLHttpRequest();
                  //  alert("ok");
                    xmlhttp.onreadystatechange = function() {
                        // alert("ok");
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                           // alert("ok");
                            document.getElementById("txtHint").innerHTML = xmlhttp.responseText;
                           // alert("ok");
                        }
                    };
                  // alert("ok");
                    xmlhttp.open("GET", "data_upload_ajax.jsp?data_main_oid=" + oid + "&data_detail_oid=" + detId + "&cmd=" + cmd, true);
                    // alert("ok");
                    xmlhttp.send();
                    // alert("ok");
                }
            }
        </script>
        <style type="text/css">
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle .td {padding: 3px 5px; border: 1px solid #CCC; }
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}

            .title_tbl_part {
                font-weight: bold;
                background-color: #EEE; 
                /*color: #08aad2;*/
            }
            .title_tbl_part_sub {
                font-weight: bold;
                background-color: #EEE; 
                color: #729a13;
            }
            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px; font-weight: bold;}
            #menu_teks {color:#CCC;}
            #box_title {padding:9px; background-color: #D5D5D5; font-weight: bold; color:#575757; margin-bottom: 7px; }
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

            .breadcrumb {
                background-color: #EEE;
                color:#0099FF;
                padding: 7px 9px;
            }
            .navbar {
                font-family: sans-serif;
                font-size: 12px;
                background-color: #0084ff;
                padding: 7px 9px;
                color : #FFF;
                border-top:1px solid #0084ff;
                border-bottom: 1px solid #0084ff;
            }
            .navbar ul {
                list-style-type: none;
                margin: 0;
                padding: 0;
            }

            .navbar li {
                padding: 7px 9px;
                display: inline;
                cursor: pointer;
            }

            .navbar li:hover {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }

            .active {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
           
            #confirm {
                font-size: 12px;
                padding: 7px 0px 8px 15px;
                background-color: #FF6666;
                color: #FFF;
                visibility: hidden;
            }
            #btn-confirm-y {
                padding: 7px 15px 8px 15px;
                background-color: #F25757; color: #FFF; 
                font-size: 12px; cursor: pointer;
            }
            #btn-confirm-n {
                padding: 7px 15px 8px 15px;
                background-color: #E34949; color: #FFF; 
                font-size: 12px; cursor: pointer;
            }
        </style>
        <style type="text/css">
            body {background-color: #EEE;}
            .header {

            }
            .content-main {
                background-color: #FFF;
                margin: 25px 23px 59px 23px;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .content-info {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
            }
            .content-title {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
                margin-bottom: 5px;
                background-color: #EEE;
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
            .btn {
                background-color: #00a1ec;
                border-radius: 3px;
                font-family: Arial;
                color: #EEE;
                font-size: 12px;
                padding: 7px 11px 7px 11px;
                text-decoration: none;
            }

            .btn:hover {
                color: #FFF;
                background-color: #007fba;
                text-decoration: none;
            }
            .btn-small {
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

            #caption {padding: 7px 0px 2px 0px; font-size: 12px; font-weight: bold; color: #575757;}
            #div_input {}

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
            .footer-page {

            }

            .bg-status {
                padding: 5px;
                background-color: #DFF0D8;
            }

            /*dropdown*/
            .dropdown {
                color:#08aad2;
                background-color: white;
                padding: 5px 7px;
                border-radius: 3px;
                border:1px solid #CCC;
                position: relative;
                display: inline-block;
            }

            .dropdown-content {
                display: none;
                position: absolute;
                background-color: #FFF;
                min-width: 160px;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
                padding: 5px 0px;
                z-index: 1;
                border-radius: 3px;
                border:1px solid #999;
            }

            .dropdown:hover .dropdown-content {
                display: block;
            }
            #div_drop {
                padding: 5px 9px;
            }
            #div_drop:hover {
                color: #237C99;
                background-color: #E5E5E5;
            }
            #div_drop a{
                color: #2A87B5;
            }
        </style>
    </head>
<body>
    <div>
        <form name="frm_data_upload" method ="post">
            <input type="hidden" name="command" value="<%=iCommand%>">  
            <input type="hidden" name="cmd" value="<%=cmdDetail%>">
            <input type="hidden" name="vectSize" value="<%=vectSize%>">
            <input type="hidden" name="start" value="<%=start%>">
            <input type="hidden" name="prev_command" value="<%=prevCommand%>">
            <input type="hidden" id="data_main_oid" name="data_main_oid" value="<%=oidDataMain%>">
            <input type="hidden" name="data_detail_oid" value="<%=oidDataDetail%>">
            <input type="hidden" name="object_id" value="<%=oidObject%>">
            <input type="hidden" name="classname" value="<%=className%>">

            <div class="content-main">
                <div class="content">
                    <div>
                        <h3>Data Upload</h3>
                    </div>

                    <div>
                        <h4>Main</h4>
                        <table width="100%" cellspacing="1" cellpadding="1" class="tblStyle td">
                            <tr>
                                <td valign="top" class="title_tbl td">Title</td>
                                <td valign="top" class="title_tbl td">Group</td>
                                <td valign="top" class="title_tbl td">Description</td>
                            </tr>
                            <%
                                for (int i = 0; i < listDataUploadMain.size(); i++) {
                                    DataUploadMain dataUpMain = (DataUploadMain) listDataUploadMain.get(i);

                                    // listDataUploadDetail = PstDataUploadDetail.list(0, 0, "DATA_MAIN_ID="+dataUploadMain.getOID(), "");

                                    String btn = "<div class=\"dropdown\">";
                                    btn += "<span><strong>" + dataUpMain.getDataMainTitle() + "</strong></span>";
                                    btn += "<div class=\"dropdown-content\">";
                                    btn += "<div id=\"div_drop\"><a href=\"javascript:cmdEdit('" + dataUpMain.getOID() + "')\">Edit</a></div>";
                                    btn += "<div id=\"div_drop\"><a href=\"javascript:loadDetail('" + dataUpMain.getOID() + "','0','2')\">Upload</a></div>";
                                    btn += "<div id=\"div_drop\" class=\"detail\"><a href=\"javascript:loadDetail('" + dataUpMain.getOID() + "','0','0')\">Detail</a></div>";
                                    btn += "<div id=\"div_drop\"><a href=\"javascript:cmdAsk('"+dataUpMain.getOID()+"')\">Delete</a></div>";
                                    //  btn +=   "<div id=\"div_drop\" ><a class=\"modul\" href=\"data_upload_Detail.jsp\">Upload</a></div>";
                                    btn += "</div>";
                                    btn += "</div>";
                            %>
                            <%if (iCommand == Command.EDIT && oidDataMain == dataUpMain.getOID()) {%>
                            <tr>
                                <%
                                    Vector dataGroup_value = new Vector(1, 1);
                                    Vector dataGroup_key = new Vector(1, 1);
                                    Vector listDataGroup = PstDataUploadGroup.list(0, 0, "", "DATA_GROUP_TITLE");
                                    dataGroup_value.add("" + 0);
                                    dataGroup_key.add("select");
                                    for (int j = 0; j < listDataGroup.size(); j++) {
                                        DataUploadGroup dataUploadGroup = (DataUploadGroup) listDataGroup.get(j);
                                        dataGroup_key.add(dataUploadGroup.getDataGroupTitle());
                                        dataGroup_value.add(String.valueOf(dataUploadGroup.getOID()));
                                    }
                                %>
                                <td class="title_tbl_part td">
                                    <input type="hidden" name="<%=FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_OBJECT_ID]%>"  value="<%=dataUpMain.getObjectId()%>" class="formElemen">
                                    <input type="hidden" name="<%=FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_OBJECT_CLASS]%>"  value="<%=dataUpMain.getObjectClass()%>" class="formElemen">
                                    <input type="text" name="<%=FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_DATA_MAIN_TITLE]%>"  value="<%=dataUpMain.getDataMainTitle()%>" class="formElemen">
                                </td>
                                <td class="title_tbl_part td"><%=ControlCombo.draw(FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_DATA_GROUP_ID], "formElemen", null, "" + dataUpMain.getDataGroupId(), dataGroup_value, dataGroup_key)%></td>
                                <td class="title_tbl_part td"><textarea name="<%=FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_DATA_MAIN_DESC]%>" class="elemenForm" cols="30" rows="3"><%=dataUpMain.getDataMainDesc()%></textarea></td>
                            </tr>
                            <%} else {%>
                            <tr>
                                <td class="title_tbl_part td"><%=btn%></td>
                                <%
                                    String dataGroupTitle = "";
                                    try {
                                        DataUploadGroup dataUpGroup = new DataUploadGroup();
                                        dataUpGroup = PstDataUploadGroup.fetchExc(dataUpMain.getDataGroupId());
                                        dataGroupTitle = dataUpGroup.getDataGroupTitle();
                                    } catch (Exception e) {
                                    }
                                %>
                                <td class="title_tbl_part td"><%=dataGroupTitle%></td>
                                <td class="title_tbl_part td"><%=dataUpMain.getDataMainDesc()%></td>
                            </tr>

                            <%}%>

                            <%
                                }
                            %>
                            <% if (iCommand == Command.ADD) {%>
                            <tr>
                                <%
                                    Vector dataGroup_value = new Vector(1, 1);
                                    Vector dataGroup_key = new Vector(1, 1);
                                    Vector listDataGroup = PstDataUploadGroup.list(0, 0, "", "DATA_GROUP_TITLE");
                                    dataGroup_value.add("" + 0);
                                    dataGroup_key.add("select");
                                    for (int j = 0; j < listDataGroup.size(); j++) {
                                        DataUploadGroup dataUploadGroup = (DataUploadGroup) listDataGroup.get(j);
                                        dataGroup_key.add(dataUploadGroup.getDataGroupTitle());
                                        dataGroup_value.add(String.valueOf(dataUploadGroup.getOID()));
                                    }
                                %>
                                <td class="title_tbl_part td">
                                    <input type="hidden" name="<%=FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_OBJECT_ID]%>"  value="<%=oidObject%>" class="formElemen">
                                    <input type="hidden" name="<%=FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_OBJECT_CLASS]%>"  value="<%=className%>" class="formElemen">
                                    <input type="text" name="<%=FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_DATA_MAIN_TITLE]%>"  value="" class="formElemen">
                                </td>
                                <td class="title_tbl_part td"><%=ControlCombo.draw(FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_DATA_GROUP_ID], "formElemen", null, "", dataGroup_value, dataGroup_key)%></td>
                                <td class="title_tbl_part td"><textarea name="<%=FrmDataUploadMain.fieldNames[FrmDataUploadMain.FRM_FIELD_DATA_MAIN_DESC]%>" class="elemenForm" cols="30" rows="3"></textarea></td>
                            </tr>
                            <%}%>
                        </table>
                    </div>
                    <!--%=drawList(iCommand, listDataUploadMain, oidDataMain, oidObject, className)%-->
                    </br>
                    <div>
                        <%if (listDataUploadMain.size() < 1) {%>
                        <p id="title-small" class="bg-status">
                            Not Any Data 
                        <p>
                            <%}%>
                            <%if (iCommand == Command.ADD || iCommand == Command.EDIT) {%>
                            <a style="color:#FFF" class="btn" href="javascript:cmdSave()">Save</a>&nbsp;<a style="color:#FFF" class="btn" href="javascript:cmdBack()">Batal</a>
                            <%} else {%>
                        <p style="margin-top: 2px"><a style="color:#FFF" class="btn" href="javascript:cmdAdd()">Tambah Data</a></p>
                        <%}%>
                        </br>
                        <span id="confirm">Are you sure to delete data ? <a id="btn-confirm-y" href="javascript:cmdDelete()">Yes</a><a id="btn-confirm-n" href="javascript:cmdNoDel()">No</a></span>
                    </div>
                </div>
            </div>

            <div class="content-main">
                <div class="content">
                        <table width="100%" cellspacing="1" cellpadding="1" class="tblStyle td">
                            <tr>
                                <td colspan="3">
                                    <div id="txtHint"></div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>
