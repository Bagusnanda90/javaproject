<%-- 
    Document   : org_struct_template
    Created on : Apr 23, 2016, 10:52:44 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.form.masterdata.FrmStructureTemplate"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlStructureTemplate"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MENU_ORGANISASI, AppObjInfo.G2_MENU_STRUKTUR_ORGANISASI, AppObjInfo.OBJ_MENU_STRUKTUR_ORGANISASI); %>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        ctrlist.addHeader("Structure Template Name", "");
        ctrlist.addHeader("Description", "");
        ctrlist.addHeader("Start Date");
        ctrlist.addHeader("End Date");
        ctrlist.addHeader("Mapping", "");
        ctrlist.addHeader("","");
        ctrlist.setLinkRow(1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int no = 0;
        if (objectClass != null && objectClass.size()>0){
            for(int i=0; i<objectClass.size(); i++){
                StructureTemplate template = (StructureTemplate)objectClass.get(i);
                Vector rowx = new Vector();
                no++;
                rowx.add(""+no);
                rowx.add(template.getTemplateName());
                rowx.add(template.getTemplateDesc());
                rowx.add(""+template.getStartDate());
                rowx.add(""+template.getEndDate());
                rowx.add("<a href=\"javascript:cmdMapping('"+template.getOID()+"')\">Mapping</a>");
                rowx.add("<button class=\"btn\" onclick=\"cmdAsk('"+template.getOID()+"')\">&times;</button>");
                lstData.add(rowx);
                lstLinkData.add(String.valueOf(template.getOID()));
            }
        }
        return ctrlist.draw();
    }
   
%>
<%  
    int iCommand = FRMQueryString.requestCommand(request);
    int commandOther = FRMQueryString.requestInt(request, "command_other");
    long oidTemplate= FRMQueryString.requestLong(request, "oid_template");
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");

    /*variable declaration*/
    int recordToGet = 50;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";

    CtrlStructureTemplate ctrlTemplate = new CtrlStructureTemplate(request);
    ControlLine ctrLine = new ControlLine();
    Vector listTemplate = new Vector(1, 1);

    /*switch statement */
    iErrCode = ctrlTemplate.action(iCommand, oidTemplate);
    /* end switch*/
    FrmStructureTemplate frmTemplate = ctrlTemplate.getForm();

    /*count list All Position*/
    int vectSize = PstStructureTemplate.getCount(whereClause);

    StructureTemplate template = ctrlTemplate.getStructureTemplate();

    /*switch list Division*/
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        start = PstStructureTemplate.findLimitStart(template.getOID(), recordToGet, whereClause, orderClause);
        oidTemplate = template.getOID();
        template = new StructureTemplate();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlTemplate.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listTemplate = PstStructureTemplate.list(start, recordToGet, whereClause, orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listTemplate.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listTemplate = PstStructureTemplate.list(start, recordToGet, whereClause, orderClause);
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Organization Template</title>
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <style type="text/css">
            .tblStyle {border-collapse: collapse; font-size: 12px; background-color: #FFF; }
            .tblStyle td {padding: 6px 9px; border: 1px solid #CCC; font-size: 12px; }
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .tr1 {background-color: #FFF;}
            .tr2 {background-color: #EEE;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
            #font-info {
                padding: 2px 5px;
                font-size: 12px; 
                font-weight: bold;
            }
            #font-val {
                color : #007fba;
                padding: 2px 5px;
                font-size: 12px; 
                font-weight: bold;
            }
            #tbl_form td {
                font-size: 12px;
            }
            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3; border-bottom: 1px solid #DDD;}
            #menu_sub {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #FFF; margin-bottom: 5px; } 
            #note {
                font-size: 13px;
                padding: 11px;
                background-color: #d3f0f9;
                color: #59aac1;
            }
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            
            .active {
                background-color: #0b71d0;
                border-bottom: 1px solid #033a6d;
            }
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
            
            body {
                background-color: #EEE;
            }
            .header {
                
            }
            .content-main {
                padding: 21px 32px;
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
                margin: 3px 5px;
                background-color: #FFF;
                border-radius: 3px;
                border: 1px solid #DDD;
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
                font-family: sans-serif;
                text-decoration: none;
                padding: 5px 7px; 
                background-color: #676767; color: #F5F5F5; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
            }
            .btn-small:hover { background-color: #474747; color: #FFF;}
            
            .btn-small-1 {
                font-family: sans-serif;
                text-decoration: none;
                padding: 5px 7px; 
                background-color: #EEE; color: #575757; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
            }
            .btn-small-1:hover { background-color: #DDD; color: #474747;}
            
            .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
            .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
            #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}
            
            .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
            .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }
            
            .caption {
                font-size: 13px;
                color: #575757;
                font-weight: bold;
                padding: 9px 13px 9px 13px;
                border-bottom: 1px solid #DDD;
            }
            #divinput {
                margin: 5px;
                padding-bottom: 5px;
                background-color: #DDD;
            }
                       
            #div_item_sch {
                background-color: #EEE;
                color: #575757;
                padding: 5px 7px;
            }
            
            #record_count{
                font-size: 12px;
                font-weight: bold;
                padding-bottom: 9px;
            }
            #box-form {
                background-color: #EEE; 
                border-radius: 5px;
            }
            .form-style {
                font-size: 12px;
                color: #575757;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .form-title {
                padding: 11px 21px;
                text-align: left;
                border-bottom: 1px solid #DDD;
                background-color: #FFF;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                font-weight: bold;
            }
            .form-content {
                text-align: left;
                padding: 21px;
                background-color: #DDD;
            }
            .form-footer {
                border-top: 1px solid #DDD;
                padding: 11px 21px;
                background-color: #FFF;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
            }
            #confirm {
                padding: 13px 17px;
                background-color: #FF6666;
                color: #FFF;
                border-radius: 3px;
                font-size: 12px;
                font-weight: bold;
                visibility: hidden;
            }
            #btn-confirm {
                padding: 4px 9px; border-radius: 3px;
                background-color: #CF5353; color: #FFF; 
                font-size: 11px; cursor: pointer;
            }
            .footer-page {
                font-size: 12px; 
            }
            
            #floatleft {
                font-size: 12px;
                color: #474747;
                padding: 7px 11px;
                background-color: #FFF;
                border-radius: 3px;
                margin: 5px 0px;
                cursor: pointer;
            }
            #floatleft:hover {
                color: #FFF;
                background-color: #474747;
            }
            .box-item {
                color: #474747;
                padding: 7px 13px;
                font-size: 11px;
                background-color: #FFF;
                margin: 1px 0px;
                cursor: pointer;
            }
            .box-item:hover {
                color: #373737;
                background-color: #EEE;
            }
            .box-item-active {
                color: #F5F5F5;
                padding: 9px 13px;
                font-size: 11px;
                background-color: #007fba;
                margin: 1px 0px;
                cursor: pointer;
            }
        </style>
        <script type="text/javascript">
           function getCmd(){
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.action = "org_struct_template.jsp";
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.submit();
            }
            function cmdSave(){
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
            
            function cmdAdd(){
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.oid_template.value = "0";
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.command.value="<%=Command.ADD%>";
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.prev_command.value="<%=prevCommand%>";
                getCmd();
            }
            
            function cmdEdit(oid) {
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.oid_template.value = oid;
                getCmd();
            }
            
            function cmdListFirst(){
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.command.value="<%=Command.FIRST%>";
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.prev_command.value="<%=Command.FIRST%>";
                getCmd();
            }

            function cmdListPrev(){
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.command.value="<%=Command.PREV%>";
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.prev_command.value="<%=Command.PREV%>";
                getCmd();
            }

            function cmdListNext(){
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.command.value="<%=Command.NEXT%>";
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.prev_command.value="<%=Command.NEXT%>";
                getCmd();
            }

            function cmdListLast(){
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.command.value="<%=Command.LAST%>";
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.prev_command.value="<%=Command.LAST%>";
                getCmd();
            }
            function cmdBack() {
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.command.value="<%=Command.BACK%>";               
                getCmd();
            }
            function cmdAsk(oid){
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.command.value="<%=Command.ASK%>";
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.oid_template.value = oid;
                getCmd();
            }
            function cmdDelete(oid){
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.command.value = "<%=Command.DELETE%>";
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.oid_template.value = oid;
                getCmd();
            }
            function cmdMapping(oid){
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.action = "org_struct_template.jsp";
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.oid_template.value = oid;
                document.<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>.submit();
            }
        </script>
        <link rel="stylesheet" href="<%=approot%>/javascripts/datepicker/themes/jquery.ui.all.css">
        <script src="<%=approot%>/javascripts/jquery.js"></script>
        <script src="<%=approot%>/javascripts/datepicker/jquery.ui.core.js"></script>
        <script src="<%=approot%>/javascripts/datepicker/jquery.ui.widget.js"></script>
        <script src="<%=approot%>/javascripts/datepicker/jquery.ui.datepicker.js"></script>
        <script>
        $(function() {
            $( "#datepicker1" ).datepicker({ dateFormat: "yy-mm-dd" });
            $( "#datepicker2" ).datepicker({ dateFormat: "yy-mm-dd" });
        });
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
            <span id="menu_title">Organization Structure Template</span>
        </div>
        <div class="content-main">
            <form name="<%=FrmStructureTemplate.FRM_NAME_STRUCTURE_TEMPLATE%>" method="post" action="">
                <input type="hidden" name="command" value="<%=iCommand%>" />
                <input type="hidden" name="command_other" value="<%=commandOther%>" />
                <input type="hidden" name="oid_template" value="<%=oidTemplate%>" />
                <input type="hidden" name="start" value="<%=start%>" />
                <input type="hidden" name="prev_command" value="<%=prevCommand%>" />
                <%
                if (iCommand == Command.ASK){
                %>
                <div id="confirm">
                    <strong>Are you sure to delete item ?</strong> &nbsp;
                    <button id="btn1" onclick="javascript:cmdDelete('<%=oidTemplate%>')">Yes</button>
                    &nbsp;<button id="btn1" onclick="javascript:cmdBack()">No</button>
                </div>
                <%
                }
                %>
                <%
                if(iCommand == Command.ADD || iCommand == Command.EDIT){
                %>
                <table>
                    <tr>
                        <td colspan="2">
                            <div id="mn_utama">Form of Structure Template</div>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle">Structure Template Name</td>
                        <td valign="middle"><input type="text" name="<%=frmTemplate.fieldNames[FrmStructureTemplate.FRM_FIELD_TEMPLATE_NAME]%>" size="50" value="<%=template.getTemplateName()%>" /></td>
                    </tr>
                    <tr>
                        <td valign="middle">Description</td>
                        <td valign="middle">
                            <textarea name="<%=frmTemplate.fieldNames[FrmStructureTemplate.FRM_FIELD_TEMPLATE_DESC]%>"><%=template.getTemplateDesc()%></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle">Start Date</td>
                        <td valign="middle"><input type="text" id="datepicker1" name="<%=frmTemplate.fieldNames[FrmStructureTemplate.FRM_FIELD_START_DATE]%>" size="50" value="<%=template.getStartDate()%>" /></td>
                    </tr>
                    <tr>
                        <td valign="middle">End Date</td>
                        <td valign="middle"><input type="text" id="datepicker2" name="<%=frmTemplate.fieldNames[FrmStructureTemplate.FRM_FIELD_END_DATE]%>" size="50" value="<%=template.getEndDate()%>" /></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <button id="btn" onclick="cmdSave()">Save</button>&nbsp;
                            <button id="btn" onclick="cmdBack()">Back</button>
                        </td>
                    </tr>
                </table>
                <% } %>
                <table>
                    <tr>
                        <td valign="top">
                            <div id="mn_utama">List of Structure Template</div>
                        </td>
                    </tr>
                    <tr>
                        <%
                        if (listTemplate != null && listTemplate.size()>0){
                            %>
                            <td valign="top">
                                <%=drawList(listTemplate)%>
                            </td>
                            <%
                        } else {
                            %>
                            <td valign="top">
                                No record found
                            </td>
                            <%
                        }
                        %>
                    </tr>
                    <tr>
                        <td>
                            <button id="btn" onclick="cmdAdd()">Add Data</button>&nbsp;
                            <button id="btn" onclick="cmdBack()">Back</button>
                        </td>
                    </tr>
                    <tr>
                        <td valign="top">
                            <span class="command"> 
                                <%
                                    int cmd = 0;
                                    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                                            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                                        cmd = iCommand;
                                    } else {
                                        if (iCommand == Command.NONE || prevCommand == Command.NONE) {
                                            cmd = Command.FIRST;
                                        } else {
                                            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidTemplate == 0)) {
                                                cmd = PstStructureTemplate.findLimitCommand(start, recordToGet, vectSize);
                                            } else {
                                                cmd = prevCommand;
                                            }
                                        }
                                    }
                                %>
                                <%
                                    ctrLine.setLocationImg(approot + "/images");
                                    ctrLine.initDefault();
                                %>
                                <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%> 
                            </span>
                        </td>
                    </tr>
                </table>
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