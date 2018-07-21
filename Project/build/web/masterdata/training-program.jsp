<%-- 
    Document   : training-program
    Created on : Dec 28, 2015, 4:17:14 PM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlTraining"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmTraining"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_TRAINING, AppObjInfo.G2_TRAINING_PROGRAM, AppObjInfo.OBJ_MENU_TRAINING_PROGRAM);%>
<%@ include file = "../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!    public String drawListTrainingFile(Vector objectClass, long trainingID) {
    
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("<a style=\"text-decoration:none\" href =\"javascript:cmdDeleteTrainingFile()\"><font color=\"#30009D\">Delete</font></a>", "");
        ctrlist.addHeader("FileName", "");
        ctrlist.addHeader("Upload File", "");
            
        ctrlist.setLinkRow(1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdOpen('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;
            
            
        for (int i = 0; i < objectClass.size(); i++) {
            TrainingFile trainingFile = (TrainingFile) objectClass.get(i);
            Vector rowx = new Vector();
            rowx.add("<input type=\"checkbox\" name=\"delete" + i + "\" value=\"" + trainingFile.getOID() + "\" class=\"formElemen\" size=\"10\"><input type=\"hidden\" name=\"trainingFileId\" value=\"" + trainingFile.getOID() + "\" class=\"formElemen\" size=\"10\">");
            rowx.add(trainingFile.getFileName());
            rowx.add("<div  valign =\"top\" align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdAttach('" + trainingFile.getOID() + "','" + trainingID + "')\">Upload File</a></div>");
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(trainingFile.getFileName()));
        }
            
        return ctrlist.draw(index);
    }

    public String drawListTrainingFileOnlyView(Vector objectClass, long trainingID) {
    
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("FileName", "");
            
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdOpen('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;
            
            
        for (int i = 0; i < objectClass.size(); i++) {
            TrainingFile trainingFile = (TrainingFile) objectClass.get(i);
            Vector rowx = new Vector();
            rowx.add(trainingFile.getFileName());
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(trainingFile.getFileName()));
        }
            
        return ctrlist.draw(index);
    }
        
        
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
long oidTraining = FRMQueryString.requestLong(request, "oid_training");
CtrlTraining ctrlTraining = new CtrlTraining(request);
String pathFileTraining = PstSystemProperty.getValueByName("TRAINING_MATERIAL");
String pathFileTrainingShort = PstSystemProperty.getValueByName("TRAINING_MATERIAL_SHORT");
String[] depid = new String[1];
/* checklist department */
Vector listDepart = PstDepartment.list(0, 0, "", "");
if (listDepart != null && listDepart.size()>0){
    depid = new String[listDepart.size()];
    for(int i=0; i<listDepart.size(); i++){
        Department depart = (Department)listDepart.get(i);
        depid[i] = ""+depart.getOID();
    }
}
int iErrCode = FRMMessage.NONE;
iErrCode = ctrlTraining.action(iCommand , oidTraining, depid);
FrmTraining frmTraining = ctrlTraining.getForm();
Training training = ctrlTraining.getTraining();
String whClause = PstTrainingFile.fieldNames[PstTrainingFile.FLD_TRAINING_ID]+"="+oidTraining;
Vector vectTrainingFile = PstTrainingFile.list(0, 0, whClause, "");
// untuk delete
if (iCommand == Command.SUBMIT) {
        long  oidTrainingMaterial=0;
        String[] training_material_id = null;
            
        try {
                training_material_id = request.getParameterValues("trainingFileId");
        }
        catch (Exception e) 
        {
                System.out.println("Err : "+e.toString());
        }
            
        for (int i = 0; i < vectTrainingFile.size(); i++) 
        {
                try 
                        {
                           oidTrainingMaterial = FRMQueryString.requestLong(request, "delete"+i+""); // row yang dicheked
                        } catch (Exception e) 
                        {
                                System.out.println("err get checked value"+e.toString());
                        }
                            
                if(oidTrainingMaterial!=0){
                        PstTrainingFile.deleteExc(Long.parseLong(training_material_id[i]));
                }
        }
            
        response.sendRedirect("training-program.jsp?command="+Command.EDIT+"&oid_training="+oidTraining);
 }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Masterdata - <%=dictionaryD.getWord(I_Dictionary.TRAINING) %></title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <style type="text/css">
            .tblStyle {border-collapse: collapse; font-size: 12px;}
            .tblStyle td {padding: 7px 9px; border: 1px solid #CCC; font-size: 12px; background-color: #FFF;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}

            body {color:#373737;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px;}
            #menu_teks {color:#CCC;}
            
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
            
            .navbar li a {
                color : #F5F5F5;
                text-decoration: none;
            }
            
            .navbar li a:hover {
                color:#FFF;
            }
            
            .navbar li a:active {
                color:#FFF;
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
            
            body {background-color: #EEE;}
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
            
        </style>
        <script type="text/javascript">

            function loadList(training_name) {
                if (training_name.length == 0) { 
                    training_name = "0";
                } 
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "training-program-ajax.jsp?training_name=" + training_name +"&type_name=0", true);
                xmlhttp.send();
                
            }
            
            function prepare(){
                loadList("0");
            }
            
            function loadByType(type_name) {
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "training-program-ajax.jsp?type_name=" + type_name, true);
                xmlhttp.send();
            }
            
            function cmdListFirst(start){                
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "training-program-ajax.jsp?training_name=0&command=" + <%=Command.FIRST%> + "&start="+start, true);
                xmlhttp.send();
            }

            function cmdListPrev(start){
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "training-program-ajax.jsp?training_name=0&command=" + <%=Command.PREV%> + "&start="+start, true);
                xmlhttp.send();
            }

            function cmdListNext(start){
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "training-program-ajax.jsp?training_name=0&command=" + <%=Command.NEXT%> + "&start="+start, true);
                xmlhttp.send();
            }

            function cmdListLast(start){
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                        document.getElementById("div_respon").innerHTML = xmlhttp.responseText;
                    }
                };
                xmlhttp.open("GET", "training-program-ajax.jsp?training_name=0&command=" + <%=Command.LAST%> + "&start="+start, true);
                xmlhttp.send();
            }
            
            function cmdAdd(){
                document.frmtraining.oid_training.value="0";
                document.frmtraining.command.value="<%=Command.ADD%>";
                document.frmtraining.action="training-program.jsp";
                document.frmtraining.submit();
            }
            
            function cmdEdit(oidTraining){
                document.frmtraining.oid_training.value=oidTraining;
                document.frmtraining.command.value="<%=Command.EDIT%>";
                document.frmtraining.action="training-program.jsp";
                document.frmtraining.submit();
            }
            
            function cmdDelete(){
                var oid = document.getElementById("oid_training").value;
                document.frmtraining.oid_training.value=oid;
                document.frmtraining.command.value="<%=Command.DELETE%>";
                document.frmtraining.action="training-program.jsp";
                document.frmtraining.submit();
            }
            
            function cmdDeleteTrainingFile(){
                document.frmtraining.command.value="<%=Command.SUBMIT%>";
                document.frmtraining.action="training-program.jsp";
                document.frmtraining.submit();
            }
            
            function cmdAsk(oid){
                document.getElementById("oid_training").value=oid;
                document.getElementById("confirm").style.visibility="visible";
            }
            
            function cmdCancel(){
                document.getElementById("oid_training").value="0";
                document.getElementById("confirm").style.visibility="hidden";
            }
            
            function cmdClose(){
                document.frmtraining.oid_training.value="0";
                document.frmtraining.command.value="<%=Command.NONE%>";
                document.frmtraining.action="training-program.jsp";
                document.frmtraining.submit();
            }
            
            function cmdSave(){
                document.frmtraining.command.value="<%=Command.SAVE%>";
                document.frmtraining.action="training-program.jsp";
                document.frmtraining.submit();
            }
            
            function cmdUpload(oidTraining){
                document.frmtraining.command.value="<%=Command.EDIT%>";
                document.frmtraining.action="upload_training_material_new.jsp?command="+<%=Command.EDIT%>+"&training_id=" + oidTraining;
                document.frmtraining.submit();
            }
            
            function cmdOpen(fileName){
                window.open("<%=approot%>" + "<%=pathFileTrainingShort%>"+fileName , null);
            }

        </script>
    </head>
    <body onload="prepare()">
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
            <span id="menu_title">Masterdata <strong style="color:#333;"> / </strong><%=dictionaryD.getWord(I_Dictionary.TRAINING) %></span>
        </div>
        <div class="content-main">
            <div style="margin-bottom: 13px;">
                <% if(privAdd){ %>
                <a class="btn" style="color:#FFF" href="javascript:cmdAdd()">Tambah Data</a>
                <% } %>
            </div>
            <input type="text" style="padding:6px 7px" name="training_name" onkeyup="loadList(this.value)" placeholder="Searching..." size="70" /> 
            <select name="type_name" style="padding:4px 7px" onchange="loadByType(this.value)">
                <option value="0">-SELECT-</option>
                <%
                Vector listType = PstTrainType.list(0, 0, "", PstTrainType.fieldNames[PstTrainType.FLD_TRAIN_TYPE_NAME]);
                if (listType != null && listType.size()>0){
                    for(int i=0; i<listType.size(); i++){
                        TrainType trType = (TrainType)listType.get(i);
                        %>
                        <option value="<%=trType.getOID()%>"><%= trType.getTypeName() %></option>
                        <%
                    }
                }
                %>
            </select>
            <div>&nbsp;</div>
            <table cellspadding="0" cellspacing="0" width="100%">
                <tr>
                    <td valign="top">
                        <span id="confirm">
                            Are you sure to delete data ? <a id="btn-confirm" href="javascript:cmdDelete()">Yes</a>&nbsp;<a id="btn-confirm" href="javascript:cmdCancel()">No</a>
                        </span>
                        <div>&nbsp;</div>
                        <div id="div_respon"></div>
                    </td>
                    <td valign="top" align="right">
                        <form name="frmtraining" id="frmtraining" method="post">
                            <input type="hidden" name="command" value="<%=iCommand%>">
                            <input type="hidden" id="oid_training" name="oid_training" value="<%=oidTraining%>">
                            <%
                            if (iCommand == Command.ADD || iCommand == Command.EDIT){
                                %>
                                <div id="box-form">
                                    <div class="form-title">Form Editor</div>
                                    <div class="form-content">
                                        <div id="caption">Training Name</div>
                                        <div id="divinput">
                                            <%
                                            if (privUpdate){
                                                %>
                                                <input type="text" name="<%=frmTraining.fieldNames[FrmTraining.FRM_FIELD_NAME] %>"  value="<%= training.getName() %>" class="formElemen" size="57">
                                                * <%= frmTraining.getErrorMsg(FrmTraining.FRM_FIELD_NAME) %>
                                                <%
                                            } else {
                                                %>
                                                <%= training.getName() %>
                                                <%
                                            }
                                            %>                                             
                                        </div>
                                        <div id="caption">Training Type</div>
                                        <div id="divinput">
                                            <%
                                                Vector type_val = new Vector();
                                                Vector type_key = new Vector();

                                                String order = PstTrainType.fieldNames[PstTrainType.FLD_TRAIN_TYPE_NAME];
                                                Vector listTrainType = PstTrainType.list(0, 0, "", order);
                                                String trainTypeName = "-";
                                                for(int i=0; i<listTrainType.size(); i++) {
                                                    TrainType train = (TrainType)listTrainType.get(i);
                                                    type_key.add(train.getTypeName());
                                                    type_val.add(String.valueOf(train.getOID()));
                                                    if (training.getType()==train.getOID()){
                                                        trainTypeName = train.getTypeName();
                                                    }
                                                }
                                            %>
                                            <%
                                            if (privUpdate){
                                                %>
                                                <%= ControlCombo.draw(FrmTraining.fieldNames[FrmTraining.FRM_FIELD_TYPE],"formElemen",null, ""+training.getType(), type_val, type_key) %> * 
                                                <%
                                            } else {
                                                %>
                                                <%=trainTypeName%>
                                                <%
                                            }
                                            %>
                                            
                                        </div>
                                        <div id="caption">Description</div>
                                        <div id="divinput">
                                            <%
                                            if (privUpdate){
                                                %>
                                                <textarea name="<%=frmTraining.fieldNames[FrmTraining.FRM_FIELD_DESCRIPTION] %>" class="formElemen" cols="37" rows="3"><%= training.getDescription() %></textarea>
                                                <%
                                            } else {
                                                %>
                                                <%= training.getDescription() %>
                                                <%
                                            }
                                            %>
                                            
                                        </div>
                                        <%
                                        if (iCommand == Command.EDIT){
                                            %>
                                            <div id="caption">&nbsp;</div>
                                            <%
                                            if(privUpdate){
                                            %>
                                            <div id="divinput"><a class="btn-small" style="color:#FFF" href="javascript:cmdUpload('<%=training.getOID()%>')">Tambah data material</a></div>
                                            <%
                                            }
                                            %>
                                            
                                            <%
                                            if(vectTrainingFile!=null && vectTrainingFile.size() > 0){
                                                if(privUpdate){
                                                    %>
                                                    <div>
                                                    <%=drawListTrainingFile(vectTrainingFile,oidTraining)%>
                                                    </div>
                                                    <%
                                                } else {
                                                    %>
                                                    <%=drawListTrainingFileOnlyView(vectTrainingFile,oidTraining)%>
                                                    <%
                                                }
                                            
                                            }else{
                                            %>
                                            <div>No Training material found....</div>
                                            <%
                                             }
                                        }
                                        %>

                                    </div>
                                    <div class="form-footer">
                                        <%
                                        if (privUpdate){
                                            %>
                                            <a class="btn" style="color:#FFF" href="javascript:cmdSave()">Simpan</a>
                                            <%
                                        }
                                        %>
                                        &nbsp;
                                        <a class="btn" style="color:#FFF" href="javascript:cmdClose()">Batal</a>
                                    </div>
                                </div>
                                <%
                            }
                            %>
                        </form>
                    </td>
                </tr>
            </table>
            
            
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