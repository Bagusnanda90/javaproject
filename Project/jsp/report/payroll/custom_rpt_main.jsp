<%-- 
    Document   : custom_rpt_main
    Created on : Mar 25, 2015, 4:30:32 PM
    Author     : Hendra Putu
--%>

<%@page import="com.dimata.harisma.entity.payroll.PstCustomRptMain"%>
<%@page import="com.dimata.harisma.entity.payroll.CustomRptMain"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.payroll.CtrlCustomRptMain"%>
<%@page import="com.dimata.harisma.form.payroll.FrmCustomRptMain"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_PRESENCE_REPORT, AppObjInfo.OBJ_PRESENCE_REPORT);
    int appObjCodePresenceEdit = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_PRESENCE);
    boolean privUpdatePresence = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodePresenceEdit, AppObjInfo.COMMAND_UPDATE));
%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>
<%!

    public String drawList(Vector objectClass) {

        ControlList ctrlist = new ControlList(); //membuat new class ControlList
        // membuat tampilan dengan controllist

        ctrlist.setAreaWidth("");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
        ctrlist.addHeader("No", "");
        ctrlist.addHeader("Report Title","");
        ctrlist.addHeader("Description", "");
        ctrlist.addHeader("Date Create", "");
        ctrlist.addHeader("Created by", "");
        ctrlist.addHeader("List of Privilege","");

        Vector lstData = ctrlist.getData();

        ctrlist.reset(); //berfungsi untuk menginisialisasi list menjadi kosong

        int no = 0;
        // objectClass mempunyai tipe data Vector
        // objectClass.size(); mendapatkan banyak record
        for (int i = 0; i < objectClass.size(); i++) {
            CustomRptMain custom = (CustomRptMain)objectClass.get(i);
            // rowx will be created secara berkesinambungan base on i
            Vector rowx = new Vector();
            Level level = new Level();
            Position position = new Position();
            Employee employee = new Employee();
            try {
                level = PstLevel.fetchExc(custom.getRptMainPrivLevel());
                position = PstPosition.fetchExc(custom.getRptMainPrivPos());
                employee = PstEmployee.fetchExc(custom.getRptMainCreatedBy());
            } catch(Exception ex){
                System.out.println(ex.toString());
            }
            no = no + 1;
            rowx.add("" + no);
            String btn = "<div class=\"dropdown\">";
            btn += "<span><strong>"+custom.getRptMainTitle()+"</strong></span>";
            btn += "<div class=\"dropdown-content\">";
            btn +=   "<div id=\"div_drop\"><a href=\"javascript:cmdManage('"+custom.getOID()+"')\">Manage Data</a></div>";
            btn +=   "<div id=\"div_drop\"><a href=\"javascript:cmdGenerate('"+custom.getOID()+"')\">Generate</a></div>";
            btn +=   "<div id=\"div_drop\"><a href=\"javascript:cmdAsk('"+custom.getOID()+"')\">Delete</a></div>";
            btn += "</div>";
            btn += "</div>";
            rowx.add(btn);
            rowx.add(custom.getRptMainDesc());//
            rowx.add("" + custom.getRptMainDateCreate());//
            rowx.add("" + employee.getFullName());//
            rowx.add("" + level.getLevel() + " : " + position.getPosition());
            lstData.add(rowx);
        }

        return ctrlist.draw(); // mengembalikan data-data control list
        
    }
%>
<!DOCTYPE html>
<%  
    
    /* Update by Hendra Putu | 20150326 */
    int iCommand = FRMQueryString.requestCommand(request);
    long oidCustom = FRMQueryString.requestLong(request, "oid_custom");
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");

    
    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = " RPT_MAIN_TITLE ASC";//

    CtrlCustomRptMain ctrCustomRptMain = new CtrlCustomRptMain(request);
    ControlLine ctrLine = new ControlLine();
    Vector listCustomRptMain = new Vector(1, 1);
    FrmCustomRptMain frmCustomRptMain = new FrmCustomRptMain();
    frmCustomRptMain = ctrCustomRptMain.getForm();
    CustomRptMain customRptMain = new CustomRptMain();
    
    /* code process save */
    iErrCode = ctrCustomRptMain.action(iCommand, oidCustom);
    /* end switch*/
    

    /*count list All Position*/
    int vectSize = PstCustomRptMain.getCount(whereClause); //PstWarningReprimandAyat.getCount(whereClause);
    customRptMain = ctrCustomRptMain.getCustomRptMain();
    msgString = ctrCustomRptMain.getMessage();
    
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        start = PstCustomRptMain.findLimitStart(customRptMain.getOID(), recordToGet, whereClause, orderClause);
        oidCustom = customRptMain.getOID();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrCustomRptMain.actionList(iCommand, start, vectSize, recordToGet);
    }
   
    
    /* get record to display */
    listCustomRptMain = PstCustomRptMain.list(start, recordToGet, whereClause, orderClause);
    
    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listCustomRptMain.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listCustomRptMain = PstCustomRptMain.list(start, recordToGet, whereClause, orderClause);
    }
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Custom Report</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <link href="<%=approot%>/stylesheets/superTables.css" rel="Stylesheet" type="text/css" /> 
        <style type="text/css">
            
            #mn_utama {
                color: #474747; 
                font-weight: bold;
                padding: 7px 11px; 
                border-left: 2px solid #DE072B; 
                font-size: 12px; 
                background-color: #FFF;
            }
            
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3; color:#0099FF; font-size: 14px; font-weight: bold;}
            
            #btn {
              background-color: #0796DE;
              font-weight: bold;
              border-radius: 3px;
              font-family: Arial;
              color: #F7F7F7;
              font-size: 12px;
              padding: 7px 11px;
            }

            #btn:hover {
                color: #FFF;
                background-color: #0B82BD;
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
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC; vertical-align: middle;}
            .tblStyle tr:hover td {
                color : #474747;
                background-color: #EEE;
                cursor: pointer;
              }
            .title_tbl {
                font-weight: bold;
                background-color: #DDD; 
                color: #575757; 
                padding-top: 9px; 
                padding-bottom: 9px;
            }
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
            
.dropdown {
    color:#575757;
    background-color: #EEE;
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
        <script type="text/javascript">
            function getCmd(){
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.action = "custom_rpt_main.jsp";
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.submit();
            }
            function cmdAdd() {              
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.command.value="<%=Command.ADD%>";               
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.oid_custom.value = "0";
                getCmd();
            }
            function cmdBack() {
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.command.value="<%=Command.BACK%>";               
                getCmd();
            }

            function cmdEdit(oid) {
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.command.value = "<%=Command.EDIT%>";
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.oid_custom.value = oid;
                getCmd();
            }

            function cmdSave() {
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.command.value = "<%=Command.SAVE%>";
                getCmd();
            }
            function cmdAsk(oid){
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.command.value="<%=Command.ASK%>";
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.oid_custom.value = oid;
                getCmd();
            }
            function cmdDelete(oid){
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.command.value = "<%=Command.DELETE%>";
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.oid_custom.value = oid;
                getCmd();
            }
            function cmdManage(oid){
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.oid_custom.value = oid;
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.action = "custom_rpt_config.jsp";
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.submit();
            }
            function cmdGenerate(oid){
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.oid_custom.value = oid;
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.action = "custom_rpt_generate.jsp";
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.submit();
            }
            function cmdListFirst(){
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.command.value="<%=Command.FIRST%>";
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.prev_command.value="<%=Command.FIRST%>";
                getCmd();
            }

            function cmdListPrev(){
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.command.value="<%=Command.PREV%>";
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.prev_command.value="<%=Command.PREV%>";
                getCmd();
            }

            function cmdListNext(){
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.command.value="<%=Command.NEXT%>";
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.prev_command.value="<%=Command.NEXT%>";
                getCmd();
            }

            function cmdListLast(){
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.command.value="<%=Command.LAST%>";
                document.<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>.prev_command.value="<%=Command.LAST%>";
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
        <link rel="stylesheet" href="<%=approot%>/javascripts/datepicker/themes/jquery.ui.all.css">
        <script src="<%=approot%>/javascripts/jquery.js"></script>
        <script src="<%=approot%>/javascripts/datepicker/jquery.ui.core.js"></script>
	<script src="<%=approot%>/javascripts/datepicker/jquery.ui.widget.js"></script>
	<script src="<%=approot%>/javascripts/datepicker/jquery.ui.datepicker.js"></script>
        <script>
	$(function() {
            $( "#datepicker" ).datepicker({ dateFormat: "yy-mm-dd" });
	});
	</script>
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
                            <td width="100%" colspan="3" valign="top" style="padding: 12px"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr> 
                                        <td height="20"> <div id="menu_utama"> <!-- #BeginEditable "contenttitle" -->Custom Report<!-- #EndEditable --> </div> </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            &nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="background-color:#EEE;" valign="top">
                                        
                                            <table style="padding:21px; border:1px solid #00CCFF;" <%=garisContent%> width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                <tr>
                                                    <td valign="top">
                                                        
                                                        
                                                        <form name="<%=FrmCustomRptMain.FRM_NAME_CUSTOM_RPT_MAIN%>" method="POST" action="">
                                                            <input type="hidden" name="command" value="<%=iCommand%>" />
                                                            <input type="hidden" name="oid_custom" value="<%=oidCustom%>" />
                                                            <input type="hidden" name="start" value="<%=start%>" />
                                                            <input type="hidden" name="prev_command" value="<%=prevCommand%>" />
                                                            <%
                                                            if(iCommand == Command.EDIT || iCommand == Command.ADD){
                                                            %>
                                                            <div id="mn_utama">Form Custom Report</div>
                                                            <table>
                                                                <tr>
                                                                    <td valign="top" id="tdForm"><strong>Report Title</strong></td>
                                                                    <td valign="top" id="tdForm">
                                                                        <input type="text" name="<%=FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_TITLE]%>" size="50" value="<%=customRptMain.getRptMainTitle()%>" />
                                                                    </td>
                                                                </tr>
                                                                
                                                                <tr>
                                                                    <td valign="top" id="tdForm"><strong>Report Description</strong></td>
                                                                    <td valign="top" id="tdForm">
                                                                        <input type="text" name="<%=FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_DESC]%>" size="70" value="<%=customRptMain.getRptMainDesc()%>" />
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="top" id="tdForm"><strong>Date Create</strong></td>
                                                                    <td valign="top" id="tdForm">
                                                                        <input type="hidden" name="<%=FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_CREATED_BY]%>" value="<%=emplx.getOID()%>" />
                                                                        <input type="text" name="<%=FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_DATE_CREATE]%>" id="datepicker" value="<%=customRptMain.getRptMainDateCreate()%>" />
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="top" id="tdForm"><strong>Privilege Setup</strong></td>
                                                                    <td valign="top" id="tdForm">
                                                                        <select name="<%=FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_PRIV_LEVEL]%>">                                                                           
                                                                            <option value="0">-select-</option>
                                                                            <%
                                                                            Vector listLevel = PstLevel.list(0, 0, "", "");
                                                                            String levelSelected = "";
                                                                            if(listLevel != null && listLevel.size() > 0){
                                                                                for(int i=0; i<listLevel.size(); i++){
                                                                                    Level level = (Level)listLevel.get(i);
                                                                                    if (customRptMain.getRptMainPrivLevel()==level.getOID()){
                                                                                        levelSelected = " selected=\"selected\" ";
                                                                                    } else {
                                                                                        levelSelected = " ";
                                                                                    }
                                                                                    %>
                                                                                    <option value="<%=level.getOID()%>" <%=levelSelected%>><%=level.getLevel()%></option>
                                                                                    <%
                                                                                }
                                                                            }
                                                                            %>
                                                                        </select>
                                                                        &nbsp;
                                                                        <select name="<%=FrmCustomRptMain.fieldNames[FrmCustomRptMain.FRM_FIELD_RPT_MAIN_PRIV_POS]%>">
                                                                            <option value="0" selected="selected">-select-</option>
                                                                            <%
                                                                            Vector listPos = PstPosition.list(0, 0, "", "");
                                                                            String posSelected = "";
                                                                            if(listPos != null && listPos.size() > 0){
                                                                                for(int i=0; i<listPos.size(); i++){
                                                                                    Position position = (Position)listPos.get(i);
                                                                                    if (customRptMain.getRptMainPrivPos()==position.getOID()){
                                                                                        posSelected = " selected=\"selected\" ";
                                                                                    } else {
                                                                                        posSelected = " ";
                                                                                    }
                                                                                    %>
                                                                                    <option value="<%=position.getOID()%>" <%=posSelected%>><%=position.getPosition()%></option>
                                                                                    <%
                                                                                }
                                                                            }
                                                                            %>
                                                                        </select>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td valign="top" id="tdForm" colspan="2">
                                                                        <a id="btn" href="javascript:cmdSave()">Save New Custom Report Configuration</a>&nbsp;
                                                                        <a id="btn" href="javascript:cmdBack()">Back to List Custom Report Configuration</a>
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
                                                <%
                                                if (iCommand == Command.ASK){
                                                %>
                                                <tr>
                                                    <td>
                                                        <span id="confirm">
                                                            <strong>Are you sure to delete item ?</strong> &nbsp;
                                                            <button id="btn1" onclick="javascript:cmdDelete('<%=oidCustom%>')">Yes</button>
                                                            &nbsp;<button id="btn1" onclick="javascript:cmdBack()">No</button>
                                                        </span>
                                                    </td>
                                                </tr>
                                                <%
                                                }
                                                %>
                                                <tr>
                                                    <td>
                                                        <a id="btn" href="javascript:cmdAdd()">Add New Custom Report Configuration</a>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <%
                                                    if (listCustomRptMain != null && listCustomRptMain.size()>0){
                                                    %>
                                                    <td>
                                                        <div id="mn_utama">List Custom Report</div>
                                                        <div>&nbsp;</div>
                                                        <%=drawList(listCustomRptMain)%>
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
                                                                    cmd = PstCustomRptMain.findLimitCommand(start, recordToGet, vectSize);
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
                                                    <%
                                                    } else {
                                                    %>
                                                    <td>
                                                        <div id="mn_utama">List Custom Report</div>
                                                        record not found
                                                    </td>
                                                    <%    
                                                    }
                                                    %>
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
    <!-- #BeginEditable "script" --> <script language="JavaScript">
                var oBody = document.body;
                var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
                
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
