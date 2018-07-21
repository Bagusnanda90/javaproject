<%-- 
    Document   : division type
    Created on : Agt, 2015, 9:46:50 AM
    Author     : Dimata 007
--%>

<%@page import="com.dimata.harisma.form.masterdata.FrmDepartmentType"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlDepartmentType"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.masterdata.CtrlDivisionType"%>
<%@page import="com.dimata.harisma.form.masterdata.FrmDivisionType"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.system.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
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
        ctrlist.addHeader("Group Type", "");
        ctrlist.addHeader("Type Name", "");
        ctrlist.addHeader("Description", "");
        ctrlist.addHeader("Level", "");
        ctrlist.addHeader("","");
        ctrlist.setLinkRow(1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        if (objectClass != null && objectClass.size()>0){
            for(int i=0; i<objectClass.size(); i++){
                DepartmentType depType = (DepartmentType)objectClass.get(i);
                Vector rowx = new Vector();
                String group = "";
                switch(depType.getGroupType()){
                    case 0: group = PstDepartmentType.groupType[0]; break;
                    case 1: group = PstDepartmentType.groupType[1]; break;
                }
                rowx.add(group);
                rowx.add(depType.getTypeName());
                rowx.add(depType.getDescription());
                rowx.add(""+depType.getLevel());
                rowx.add("<button class=\"btn\" onclick=\"cmdAsk('"+depType.getOID()+"')\">&times;</button>");
                lstData.add(rowx);
                lstLinkData.add(String.valueOf(depType.getOID()));
            }
        }
        return ctrlist.draw();
    }
%>
<!DOCTYPE html>
<%  
    int iCommand = FRMQueryString.requestCommand(request);
    int commandOther = FRMQueryString.requestInt(request, "command_other");
    long oidDepartmentType = FRMQueryString.requestLong(request, "oid_department_type");
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");

    /*variable declaration*/
    int recordToGet = 50;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";

    CtrlDepartmentType ctrlDepartmentType = new CtrlDepartmentType(request);
    ControlLine ctrLine = new ControlLine();
    Vector listDepartmentType= new Vector(1, 1);

    /*switch statement */
    iErrCode = ctrlDepartmentType.action(iCommand, oidDepartmentType);
    /* end switch*/
    FrmDepartmentType frmDepartmentType = ctrlDepartmentType.getForm();

    /*count list All Position*/
    int vectSize = PstDepartmentType.getCount(whereClause);

    DepartmentType departmentType = ctrlDepartmentType.getDepartmentType();
    
    /*switch list Division*/
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        start = PstDivisionType.findLimitStart(departmentType.getOID(), recordToGet, whereClause, orderClause);
        oidDepartmentType = departmentType.getOID();
        departmentType = new DepartmentType();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlDepartmentType.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listDepartmentType = PstDepartmentType.list(start, recordToGet, whereClause, orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listDepartmentType.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listDepartmentType = PstDepartmentType.list(start, recordToGet, whereClause, orderClause);
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Department Type Master</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 
        <style type="text/css">

            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 12px; font-weight: bold; background-color: #F5F5F5;}
            
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
            .btn {
                padding: 3px; border: 1px solid #CCC; 
                background-color: #EEE; color: #777777; 
                font-size: 11px; cursor: pointer;
            }
            .btn:hover {border: 1px solid #999; background-color: #CCC; color: #FFF;}
            #tdForm {
                padding: 5px;
            }
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
            #desc_field_type{padding:7px 12px; background-color: #F3F3F3; border:1px solid #FFF; margin:3px 0px;}
            #text_desc {background-color: #FFF;color:#575757; padding:3px; font-size: 9px;}
            #data_list{padding:3px 5px; color:#FFF; background-color: #79bbff; margin:2px 1px 2px 0px; border-radius: 3px;}
            #data_list_close {padding:3px 5px; color:#FFF; background-color: #79bbff; margin:2px 1px 2px 0px; border-radius: 3px; cursor: pointer;}
            #data_list_close:hover {padding:3px 5px; color:#FFF; background-color: #0099FF; margin:2px 1px 2px 0px; border-radius: 3px;}
        </style>
        <script type="text/javascript">
            function getCmd(){
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.action = "department_type.jsp";
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.submit();
            }
            function cmdSave(){
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
            
            function cmdAdd(){
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.oid_department_type.value = "0";
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.command.value="<%=Command.ADD%>";
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.prev_command.value="<%=prevCommand%>";
                getCmd();
            }
            
            function cmdEdit(oid) {
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.oid_department_type.value = oid;
                getCmd();
            }
            
            function cmdListFirst(){
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.command.value="<%=Command.FIRST%>";
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.prev_command.value="<%=Command.FIRST%>";
                getCmd();
            }

            function cmdListPrev(){
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.command.value="<%=Command.PREV%>";
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.prev_command.value="<%=Command.PREV%>";
                getCmd();
            }

            function cmdListNext(){
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.command.value="<%=Command.NEXT%>";
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.prev_command.value="<%=Command.NEXT%>";
                getCmd();
            }

            function cmdListLast(){
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.command.value="<%=Command.LAST%>";
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.prev_command.value="<%=Command.LAST%>";
                getCmd();
            }
            
            function cmdBack() {
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.command.value="<%=Command.BACK%>";               
                getCmd();
            }
            
            function cmdAsk(oid){
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.command.value="<%=Command.ASK%>";
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.oid_department_type.value = oid;
                getCmd();
            }
            
            function cmdDelete(oid){
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.command.value = "<%=Command.DELETE%>";
                document.<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>.oid_department_type.value = oid;
                getCmd();
            }
        </script>
        
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
            <tr> 
                <td width="88%" valign="top" align="left"> 
                    <table width="100%" border="0" cellspacing="3" cellpadding="2" id="tbl0">
                        <tr> 
                            <td width="100%" colspan="3" valign="top" style="padding: 12px"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr> 
                                        <td height="20"> <div id="menu_utama"> <!-- #BeginEditable "contenttitle" -->Department Type Master<!-- #EndEditable --> </div> </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;" valign="top">
                                        
                                            <table style="padding:9px; border:1px solid <%=garisContent%>;"  width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td valign="top">
                                                        
                                                        
                                                        <form name="<%=FrmDepartmentType.FRM_NAME_DEPARTMENT_TYPE%>" method="POST" action="">
                                                            <input type="hidden" name="command" value="<%=iCommand%>" />
                                                            <input type="hidden" name="command_other" value="<%=commandOther%>" />
                                                            <input type="hidden" name="oid_department_type" value="<%=oidDepartmentType%>" />
                                                            <input type="hidden" name="start" value="<%=start%>" />
                                                            <input type="hidden" name="prev_command" value="<%=prevCommand%>" />
                                                            <%
                                                            if (iCommand == Command.ASK){
                                                            %>
                                                            <div id="confirm">
                                                                <strong>Are you sure to delete item ?</strong> &nbsp;
                                                                <button id="btn1" onclick="javascript:cmdDelete('<%=oidDepartmentType%>')">Yes</button>
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
                                                                    <td colspan="2"><div id="mn_utama">Form of Department Type</div></td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="middle">Group Type</td>
                                                                    <td valign="middle">
                                                                        <select name="<%=frmDepartmentType.fieldNames[FrmDepartmentType.FRM_FIELD_GROUP_TYPE]%>">
                                                                            <%
                                                                            for(int i=0; i<PstDepartmentType.groupType.length; i++){
                                                                                if (departmentType.getGroupType() == i){
                                                                                    %>
                                                                                    <option selected="selected" value="<%=i%>"><%=PstDepartmentType.groupType[i]%></option>
                                                                                    <%
                                                                                } else {
                                                                                    %>
                                                                                    <option value="<%=i%>"><%=PstDepartmentType.groupType[i]%></option>
                                                                                    <%
                                                                                }
                                                                            }
                                                                            %>
                                                                        </select>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="middle">Type Name</td>
                                                                    <td valign="middle"><input type="text" name="<%=frmDepartmentType.fieldNames[FrmDepartmentType.FRM_FIELD_TYPE_NAME]%>" size="45" value="<%=departmentType.getTypeName()%>" /></td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="middle">Description</td>
                                                                    <td valign="middle">
                                                                        <textarea name="<%=frmDepartmentType.fieldNames[FrmDepartmentType.FRM_FIELD_DESCRIPTION]%>"><%=departmentType.getDescription()%></textarea>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="middle">Level</td>
                                                                    <td valign="middle"><input type="text" name="<%=frmDepartmentType.fieldNames[FrmDepartmentType.FRM_FIELD_LEVEL]%>" size="45" value="<%=departmentType.getLevel()%>" /></td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="2">
                                                                        <button id="btn" onclick="cmdSave()">Submit</button>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                            <% } %>    
                                                            <table>
                                                                <tr>
                                                                    <td valign="top">
                                                                        <div id="mn_utama">List of Department Type</div>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <%
                                                                    if (listDepartmentType != null && listDepartmentType.size()>0){
                                                                        %>
                                                                        <td valign="top">
                                                                            <%=drawList(listDepartmentType)%>
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
                                                                                        if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidDepartmentType == 0)) {
                                                                                            cmd = PstWarningReprimandBab.findLimitCommand(start, recordToGet, vectSize);
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