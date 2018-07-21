<%-- 
    Document   : structure_mapping
    Created on : Aug 24, 2015, 10:14:43 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.form.masterdata.CtrlMappingPosition"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmMappingPosition"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.system.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_PRESENCE_REPORT, AppObjInfo.OBJ_PRESENCE_REPORT);
    int appObjCodePresenceEdit = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_PRESENCE);
    boolean privUpdatePresence = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePresenceEdit, AppObjInfo.COMMAND_UPDATE));
%>
<%@ include file = "../main/checkuser.jsp" %>
<%
    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>
<%!
    public String drawList(Vector objectClass) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("Up Position", "");
        ctrlist.addHeader("Down Position", "");
        ctrlist.addHeader("Start Date", "");
        ctrlist.addHeader("End Date", "");
        ctrlist.addHeader("Type of Link", "");
        ctrlist.addHeader("","");
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        if (objectClass != null && objectClass.size()>0){
            for(int i=0; i<objectClass.size(); i++){
                MappingPosition mapping = (MappingPosition)objectClass.get(i);
                Vector rowx = new Vector();

                rowx.add(getPositionName(mapping.getUpPositionId()));
                rowx.add(getPositionName(mapping.getDownPositionId()));
                rowx.add(""+mapping.getStartDate());
                rowx.add(""+mapping.getEndDate());
                rowx.add(PstMappingPosition.typeOfLink[mapping.getTypeOfLink()]);
                rowx.add("<button class=\"btn\" onclick=\"cmdAsk('"+mapping.getOID()+"')\">&times;</button>");
                lstData.add(rowx);
                lstLinkData.add(String.valueOf(mapping.getOID()));
            }
        }
        return ctrlist.draw();
    }
    
    public String getDrawDownPosition(long oidPosition, long oidTemplate){
        String str = "";
        String whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_UP_POSITION_ID]+"="+oidPosition;
        whereClause += " AND "+PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+"="+oidTemplate;
        Vector listDown = PstMappingPosition.list(0, 0, whereClause, "");
        if (listDown != null && listDown.size()>0){
            str = "<table class=\"tblStyle1\"><tr>";
            for(int i=0; i<listDown.size(); i++){
                MappingPosition pos = (MappingPosition)listDown.get(i);
                str += "<td>"+getPositionName(pos.getDownPositionId())+"<br />"+getDrawDownPosition(pos.getDownPositionId(),oidTemplate)+"</td>";
            }
            str += "</tr></table>";
        }
        
        return str;
    }

    public String getPositionName(long posId){
        String position = "";
        Position pos = new Position();
        try {
            pos = PstPosition.fetchExc(posId);
        } catch(Exception ex){
            System.out.println("getPositionName ==> "+ex.toString());
        }
        position = pos.getPosition();
        return position;
    }
   
%>
<!DOCTYPE html>
<%  
    int iCommand = FRMQueryString.requestCommand(request);
    long oidMapping = FRMQueryString.requestLong(request, "oid_mapping");
    long oidTemplate = FRMQueryString.requestLong(request, "oid_template");
    long selectClone = FRMQueryString.requestLong(request, "select_clone");
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidUpPosition = FRMQueryString.requestLong(request, "up_position");
    long oidDownPosition = FRMQueryString.requestLong(request, "down_position");

    /*variable declaration*/
    int recordToGet = 15;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+"="+oidTemplate;
    String orderClause = "";

    CtrlMappingPosition ctrlMapping = new CtrlMappingPosition(request);
    ControlLine ctrLine = new ControlLine();
    Vector listMapping = new Vector(1, 1);

    /*switch statement */
    iErrCode = ctrlMapping.action(iCommand, oidMapping);
    /* end switch*/
    FrmMappingPosition frmMapping = ctrlMapping.getForm();

    /*count list All Position*/
    int vectSize = PstMappingPosition.getCount(whereClause);

    MappingPosition mapping = ctrlMapping.getMappingPosition();

    /*switch list Division*/
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        start = PstMappingPosition.findLimitStart(mapping.getOID(), recordToGet, whereClause, orderClause);
        oidMapping = mapping.getOID();
        mapping = new MappingPosition();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlMapping.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listMapping = PstMappingPosition.list(start, recordToGet, whereClause, orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listMapping.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST//
        }
        listMapping = PstMappingPosition.list(start, recordToGet, whereClause, orderClause);
    }
    
    if (iCommand == Command.YES){
        MappingPosition mapPos = new MappingPosition();
        whereClause = PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+"="+selectClone;
        Vector listMapClone = PstMappingPosition.list(0, 0, whereClause, "");
        if (listMapClone != null && listMapClone.size()>0){
            for(int i=0; i<listMapClone.size(); i++){
                MappingPosition mappingPos = (MappingPosition)listMapClone.get(i);
                mapPos.setOID(0);
                mapPos.setUpPositionId(mappingPos.getUpPositionId());
                mapPos.setDownPositionId(mappingPos.getDownPositionId());
                mapPos.setStartDate(mappingPos.getStartDate());
                mapPos.setEndDate(mappingPos.getEndDate());
                mapPos.setTypeOfLink(mappingPos.getTypeOfLink());
                mapPos.setTemplateId(oidTemplate);
                try {
                    PstMappingPosition.insertExc(mapPos);
                } catch (Exception e){
                    System.out.println("Insert mapping clone=>"+e.toString());
                }
            }
        }
        iCommand = Command.VIEW;
        listMapping = PstMappingPosition.list(0, recordToGet, whereClause, orderClause);
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Structure Mapping</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 
        <style type="text/css">

            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 12px; font-weight: bold; background-color: #F5F5F5;}
            
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3; color:#0099FF; font-size: 14px; font-weight: bold;}
            .btn {
              background: #C7C7C7;
              border: 1px solid #BBBBBB;
              border-radius: 3px;
              font-family: Arial;
              color: #474747;
              font-size: 11px;
              padding: 3px 7px;
              cursor: pointer;
            }

            .btn:hover {
              color: #FFF;
              background: #B3B3B3;
              border: 1px solid #979797;
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
            .detail {
                background-color: #b01818;
                color:#FFF;
                padding: 2px;
                font-size: 9px;
                cursor: pointer;
            }
            .detail1 {
                background-color: #ffe400;
                color:#d76f09;
                padding: 2px;
                font-size: 9px;
                cursor: pointer;
            }
            .detail_pos {
                cursor: pointer;
            }
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            
            .tblStyle1 {border-collapse: collapse; background-color: #FFF;}
            .tblStyle1 td {color:#575757; text-align: center; font-size: 11px; padding: 3px 5px; border: 1px solid #999;}
            
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
            .title_part {background-color: #FFF; border-left: 1px solid #3cb0fd; padding:5px 15px;  color: #575757; margin: 3px 0px;}
            #position {
                background-color: #DDD;
                color:#FFF;
                font-size: 11px;
                padding:3px;
            }
            .div_title_clone {
                color: #474747;
                font-weight: bold;
                padding-left: 19px;
                background-color: #DDD;
                border:1px solid #CCC;
                border-bottom: none;
                border-right: none;
                border-top-left-radius: 5px;
            }
            .div_close {
                padding: 9px;
                background-color: #DDD;
                border:1px solid #CCC;
                border-left: none;
                border-bottom: none;
                border-top-right-radius: 5px;
            }
            .div_clone {
                padding: 19px;
                background-color: #F5F5F5;
                border:1px solid #CCC;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
            }
        </style>
        <script type="text/javascript">
            function getCmd(){
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.action = "structure_mapping.jsp";
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.submit();
            }
            function cmdSave(){
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
            
            function cmdAdd(){
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.oid_mapping.value = "0";
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.command.value="<%=Command.ADD%>";
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.prev_command.value="<%=prevCommand%>";
                getCmd();
            }
            
            function cmdClone(){
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.command.value="<%=Command.DETAIL%>";
                getCmd();
            }
            
            function cmdSubmitClone(val){
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.select_clone=val;
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.command.value="<%=Command.YES%>";
                getCmd();
            }
            
            function cmdEdit(oid) {
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.oid_mapping.value = oid;
                getCmd();
            }
            
            function cmdListFirst(){
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.command.value="<%=Command.FIRST%>";
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.prev_command.value="<%=Command.FIRST%>";
                getCmd();
            }

            function cmdListPrev(){
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.command.value="<%=Command.PREV%>";
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.prev_command.value="<%=Command.PREV%>";
                getCmd();
            }

            function cmdListNext(){
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.command.value="<%=Command.NEXT%>";
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.prev_command.value="<%=Command.NEXT%>";
                getCmd();
            }

            function cmdListLast(){
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.command.value="<%=Command.LAST%>";
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.prev_command.value="<%=Command.LAST%>";
                getCmd();
            }
            function cmdBack() {
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.command.value="<%=Command.BACK%>";               
                getCmd();
            }
            function cmdAsk(oid){
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.command.value="<%=Command.ASK%>";
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.oid_mapping.value = oid;
                getCmd();
            }
            function cmdDelete(oid){
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.command.value = "<%=Command.DELETE%>";
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.oid_mapping.value = oid;
                getCmd();
            }
            function cmdGoBack() {
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.command.value="<%=Command.BACK%>";               
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.action="structure_template.jsp";
                document.<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>.submit();
            }
            function cmdUpPosition(){
                newWindow=window.open("structure_upposition_form.jsp","StructureUpposition", "height=600,width=500,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
                newWindow.focus();
            }
            function cmdDownPosition(){
                newWindow=window.open("structure_downposition_form.jsp","StructureDownposition", "height=600,width=500,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
                newWindow.focus();
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
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../styletemplate/template_header.jsp" %>
            <%} else {%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" height="54"> 
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
                    <table border="0" cellspacing="0" cellpadding="0">
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
                <td valign="top" align="left" width="100%"> 
                    <table border="0" cellspacing="3" cellpadding="2" id="tbl0" width="100%">
                        <tr> 
                            <td  colspan="3" valign="top" style="padding: 12px"> 
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr> 
                                        <td height="20"> 
                                            <div id="menu_utama"> 
                                                <button class="btn" onclick="cmdGoBack()">Back to search</button> &nbsp; Mapping Position
                                            </div> 
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;" valign="top" width="100%">
                                        
                                            <table width="100%" style="padding:9px; border:1px solid <%=garisContent%>;"  border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td valign="top">
                                                        <!--- fitur filter --->
                                                        <form name="<%=FrmMappingPosition.FRM_NAME_MAPPING_POSITION%>" method="post" action="">
                                                            <input type="hidden" name="command" value="<%=iCommand%>" />
                                                            <input type="hidden" name="oid_mapping" value="<%=oidMapping%>" />
                                                            <input type="hidden" name="oid_template" value="<%=oidTemplate%>" />
                                                            <input type="hidden" name="<%=frmMapping.fieldNames[FrmMappingPosition.FRM_FIELD_TEMPLATE_ID]%>" value="<%=oidTemplate%>" />
                                                            <input type="hidden" name="start" value="<%=start%>" />
                                                            <input type="hidden" name="prev_command" value="<%=prevCommand%>" />
                                                            <%
                                                            if (iCommand == Command.ASK){
                                                            %>
                                                            <div id="confirm">
                                                                <strong>Are you sure to delete item ?</strong> &nbsp;
                                                                <button id="btn1" onclick="javascript:cmdDelete('<%=oidMapping%>')">Yes</button>
                                                                &nbsp;<button id="btn1" onclick="javascript:cmdBack()">No</button>
                                                            </div>
                                                            <%
                                                            }

                                                            if (iCommand == Command.DETAIL){
                                                            %>
                                                            <table cellspacing="0" cellpadding="0">
                                                                <tr>
                                                                    <td class="div_title_clone">Clone Data Mapping</td>
                                                                    <td align="right" class="div_close">
                                                                        <button class="btn" onclick="cmdBack()">&times;</button>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2" class="div_clone">
                                                                        <select name="select_clone">
                                                                            <option value="0">-SELECT-</option>
                                                                            <%
                                                                            Vector listTemplate = PstStructureTemplate.list(0, 0, "", "");
                                                                            if (listTemplate != null && listTemplate.size()>0){
                                                                                for(int i=0; i<listTemplate.size(); i++){
                                                                                    StructureTemplate template = (StructureTemplate)listTemplate.get(i);
                                                                                    %>
                                                                                    <option value="<%=template.getOID()%>"><%=template.getTemplateName()%></option>
                                                                                    <%
                                                                                }
                                                                            }
                                                                            %>
                                                                        </select>
                                                                        &nbsp;<button class="btn" onclick="cmdSubmitClone()">Clone</button>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            <% } %>
                                                            
                                                            <table>
                                                                <tr>
                                                                    <td>
                                                                        <button id="btn" onclick="cmdAdd()">Add Data</button>&nbsp;
                                                                        <button id="btn" onclick="cmdClone()">Clone Data</button>&nbsp;
                                                                        <button id="btn" onclick="cmdBack()">Back</button>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            <%
                                                            if(iCommand == Command.ADD || iCommand == Command.EDIT){
                                                            %>
                                                            <table>
                                                                <tr>
                                                                    <td colspan="2">
                                                                        <div id="mn_utama">Form of Mapping Position</div>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="middle">Up Position</td>
                                                                    <td valign="middle">
                                                                        <input type="hidden" name="up_position" value="<%=mapping.getUpPositionId() %>" />
                                                                        <input type="hidden" name="<%=frmMapping.fieldNames[FrmMappingPosition.FRM_FIELD_UP_POSITION_ID]%>" value="<%=mapping.getUpPositionId()%>" />
                                                                        <a href="javascript:cmdUpPosition()">Browse</a>
                                                                        <%
                                                                        if (oidUpPosition != 0){
                                                                            %>
                                                                            <span id="upposname" name="upposname" class="btn"><%=getPositionName(oidUpPosition)%></span>
                                                                            <%
                                                                        } else {
                                                                            //if (mapping.getUpPositionId() != 0){
                                                                                %>
                                                                                <span  id="upposname" name="upposname" class="btn"><%=getPositionName(mapping.getUpPositionId())%></span>
                                                                                <%
                                                                            //}
                                                                        }
                                                                        %>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="middle">Down Position</td>
                                                                    <td valign="middle">
                                                                        <input type="hidden" name="down_position" value="<%=mapping.getDownPositionId()%>" />
                                                                        <input type="hidden" name="<%=frmMapping.fieldNames[FrmMappingPosition.FRM_FIELD_DOWN_POSITION_ID]%>" value="<%=mapping.getDownPositionId()%>" />
                                                                        <a href="javascript:cmdDownPosition()">Browse</a>
                                                                        <%
                                                                        if (oidDownPosition != 0){
                                                                            %>
                                                                            <span id="downposname" name="downposname" class="btn"><%=getPositionName(oidDownPosition)%></span>
                                                                            <%
                                                                        } else {
                                                                            //if (mapping.getDownPositionId()!=0){
                                                                                %>
                                                                                <span  id="downposname" name="downposname"  class="btn"><%=getPositionName(mapping.getDownPositionId())%></span>
                                                                                <%
                                                                           // }
                                                                        }
                                                                        %>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="middle">Start Date</td>
                                                                    <td valign="middle"><input type="text" id="datepicker1" name="<%=frmMapping.fieldNames[FrmMappingPosition.FRM_FIELD_START_DATE]%>" size="50" value="<%=mapping.getStartDate()%>" /></td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="middle">End Date</td>
                                                                    <td valign="middle"><input type="text" id="datepicker2" name="<%=frmMapping.fieldNames[FrmMappingPosition.FRM_FIELD_END_DATE]%>" size="50" value="<%=mapping.getEndDate()%>" /></td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="middle">Type of Link</td>
                                                                    <td valign="middle">
                                                                        <select name="<%=frmMapping.fieldNames[FrmMappingPosition.FRM_FIELD_TYPE_OF_LINK]%>">
                                                                            <%
                                                                            String[] arrType = {"-select-","Supervisory","Coordination","Leave Approval","Schedule Approval"};
                                                                            for (int t=0; t<arrType.length; t++){
                                                                                if(t == mapping.getTypeOfLink()){
                                                                                    %>
                                                                                    <option value="<%=t%>" selected="selected"><%=arrType[t]%></option>
                                                                                    <%
                                                                                } else {
                                                                                    %>
                                                                                    <option value="<%=t%>"><%=arrType[t]%></option>
                                                                                    <%
                                                                                }
                                                                            }
                                                                            %>
                                                                        </select>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2">
                                                                        <button id="btn" onclick="javascript:cmdSave()">Save</button>&nbsp;
                                                                        <button id="btn" onclick="javascript:cmdBack()">Back</button>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            <%
                                                            }
                                                            %>
                                                            <table>
                                                                <tr>
                                                                    <td valign="top">
                                                                        <div id="mn_utama">List of Position Mapping</div>
                                                                    </td>
                                                                </tr>
                                                                <%
                                                                
                                                                if ((listMapping != null && listMapping.size()>0)|| iCommand == Command.VIEW){
                                                                %>
                                                                <tr>
                                                                    <td valign="top">
                                                                        <%=drawList(listMapping)%>
                                                                    </td>
                                                                </tr>
                                                                <%
                                                                } else {
                                                                %>
                                                                <tr>
                                                                    <td valign="top">
                                                                        No record found
                                                                    </td>
                                                                </tr>
                                                                <%
                                                                }
                                                                
                                                                %>
                                                                
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
                                                                                        if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidMapping== 0)) {
                                                                                            cmd = PstMappingPosition.findLimitCommand(start, recordToGet, vectSize);
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
                                                                <%
                                                                if ((listMapping != null && listMapping.size()>0)|| iCommand == Command.VIEW){
                                                                %>
                                                                <div id="mn_utama">View of Structure</div>
                                                                <table>
                                                                    <tr>
                                                                        <td valign="top">
                                                                            <%
                                                                            String whereMap = PstMappingPosition.fieldNames[PstMappingPosition.FLD_TEMPLATE_ID]+"="+oidTemplate;
                                                                            Vector listMap = PstMappingPosition.list(0, 0, whereMap, "");
                                                                            int checkUp = 0;
                                                                            long topMain = 0;
                                                                            if (listMap != null && listMap.size()>0){
                                                                                long[] arrUp = new long[listMap.size()];
                                                                                long[] arrDown = new long[listMap.size()];
                                                                                for(int i=0; i<listMap.size(); i++){
                                                                                    MappingPosition map = (MappingPosition)listMap.get(i);
                                                                                    arrUp[i] = map.getUpPositionId();
                                                                                    arrDown[i] = map.getDownPositionId();
                                                                                }
                                                                                for(int j=0; j<arrUp.length; j++){
                                                                                    for(int k=0; k<arrDown.length; k++){
                                                                                        if (arrUp[j] == arrDown[k]){
                                                                                            checkUp++;
                                                                                        }
                                                                                    }
                                                                                    if (checkUp == 0){
                                                                                        topMain = arrUp[j];
                                                                                    }
                                                                                    checkUp = 0;
                                                                                }

                                                                                if (topMain > 0){
                                                                                    %>
                                                                                    <table class="tblStyle1">
                                                                                        <tr>
                                                                                            <td>
                                                                                                <%=getPositionName(topMain)%>
                                                                                                <%=getDrawDownPosition(topMain,oidTemplate)%>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                                                                    <%
                                                                                }
                                                                            }
                                                                            %>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                                <% } %>
                                                        </form>

                                                        
                                                    </td>
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
                    <%@include file="../footer.jsp" %>
                </td>
                            
            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
                    <%@ include file = "../main/footer.jsp" %>
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