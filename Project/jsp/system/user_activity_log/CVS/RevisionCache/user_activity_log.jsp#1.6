<%@page import="com.dimata.harisma.entity.log.I_LogHistory"%>
<%@page import="com.dimata.harisma.entity.log.PstLogSysHistory"%>
<%@page import="com.dimata.harisma.form.log.FrmLogSysHistory"%>
<%@page import="com.dimata.harisma.form.log.CtrlLogSysHistory"%>
<%@page import="com.dimata.harisma.entity.log.LogSysHistory"%>
<%@ page import="java.util.*" %>
<%@ page import="com.dimata.util.*" %>
<%@ page import="com.dimata.gui.jsp.*" %>
<%@ page import="com.dimata.qdep.entity.*" %>
<%@ page import="com.dimata.system.entity.system.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.system.form.system.*" %>
<%@ page import="com.dimata.system.session.system.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<%  int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_SYSTEM_MANAGEMENT, AppObjInfo.OBJ_SYSTEM_PROPERTIES); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%!
%>

<%!
public String drawList(HttpServletRequest request, Vector objectClass, long logId, String approot) {
    ControlList ctrlist = new ControlList();
    ctrlist.setAreaWidth("80%");
    ctrlist.setListStyle("listgen");
    ctrlist.setTitleStyle("listgentitle");
    ctrlist.setCellStyle("listgensell");
    ctrlist.setHeaderStyle("listgentitle");
    ctrlist.addHeader("No ","2%");
    ctrlist.addHeader("Status","10%");
    ctrlist.addHeader("Date","5%");
    ctrlist.addHeader("System","10%");
    ctrlist.addHeader("Doc","10%");
    ctrlist.addHeader("User","20%");
    ctrlist.addHeader("Detail","30%");
    ctrlist.addHeader("Approved","5%");

    ctrlist.setLinkRow(0);
    ctrlist.setLinkSufix("");
    Vector lstData = ctrlist.getData();
    Vector lstLinkData = ctrlist.getLinkData();
    ctrlist.setLinkPrefix("javascript:cmdEdit('");
    ctrlist.setLinkSufix("')");
    ctrlist.reset();
    int index = -1;
    String rootUrl = request.getServerName();
    int port = request.getServerPort();
    
    for (int i = 0; i < objectClass.size(); i++) {
        LogSysHistory logSysHistory = (LogSysHistory) objectClass.get(i);
        Vector rowx = new Vector();
        if (logId == logSysHistory.getOID()) {
            index = i;
        }
        rowx.add(""+(i+1));
        rowx.add(""+I_DocStatus.fieldDocumentStatus[logSysHistory.getStatus()]);
        rowx.add(""+logSysHistory.getLogUpdateDate());
        rowx.add(""+logSysHistory.getLogApplication());
        String uri = logSysHistory.getLogOpenUrl();
        String path = request.getContextPath();
        uri = uri.replaceAll(path, "");
        rowx.add("<a href='"+/*rootUrl+":"+port*/approot+uri+"' target=\"_blank\">"+logSysHistory.getLogDocumentType());
        rowx.add(""+logSysHistory.getLogLoginName());
        rowx.add(""+logSysHistory.getLogDetail());
        if(logSysHistory.getStatus() == I_DocStatus.DOCUMENT_STATUS_CLOSED  && logSysHistory.getApproverId() != 0){
            rowx.add("");
        } else {
            rowx.add("<center><input type=\"checkbox\" name=\"logChk_" + i + "\" value=\"" + logSysHistory.getOID() + "\"></center>");
        }          

        lstData.add(rowx);
        lstLinkData.add(String.valueOf(logSysHistory.getOID()));
    }
    return ctrlist.draw(index);
}
%>

<%   
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidLogActivity = FRMQueryString.requestLong(request, "hidden_logactivity_id");
    long userId = FRMQueryString.requestLong(request, "hidden_user_id");
    String doc = FRMQueryString.requestString(request, "doc");
    long companyId = FRMQueryString.requestLong(request, "companyId");
    String approveList = FRMQueryString.requestString(request, "approved_list");
    
            
    /*variable declaration*/
    int recordToGet = 50;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";

    CtrlLogSysHistory ctrlLogSysHistory = new CtrlLogSysHistory(request);
    ControlLine ctrLine = new ControlLine();
    Vector listLogSysHistory = new Vector(1, 1);
    
    
    /*switch statement */
    iErrCode = ctrlLogSysHistory.action(iCommand, oidLogActivity, approveList, appUserIdSess);
    /* end switch*/
    FrmLogSysHistory frmLogSysHistory = ctrlLogSysHistory.getForm();

    /*count list All Position*/
    int vectSize = PstLogSysHistory.getCount(whereClause);

    LogSysHistory logSysHistory = ctrlLogSysHistory.getLogSysHistory();
    msgString = ctrlLogSysHistory.getMessage();

    /*switch list KPI_List*/
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        //start = PstKPI_List.findLimitStart(kpi_list.getOID(),recordToGet, whereClause);
        oidLogActivity = logSysHistory.getOID();
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrlLogSysHistory.actionList(iCommand, start, vectSize, recordToGet);
    }
    /* end switch list*/

    /* get record to display */
    listLogSysHistory = PstLogSysHistory.listSearch(logSysHistory,doc, userId);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listLogSysHistory.size() < 1 && start > 0) {
        if (vectSize - recordToGet > recordToGet) {
            start = start - recordToGet;   //go to Command.PREV
        } else {
            start = 0;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listLogSysHistory = PstLogSysHistory.listSearch(logSysHistory,doc, userId);
    }
    
    Date startDate = new Date();
    Date endDate = new Date();
    
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>User Activity Log</title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
function hideObjectForEmployee()
{    
} 
 
function hideObjectForLockers()
{ 
}

function hideObjectForCanteen()
{
}

function hideObjectForClinic()
{
}

function hideObjectForMasterdata()
{
}

function showObjectForMenu()
{
}
	
function cmdList() 
{
  document.frmlogactivity.command.value="<%= Command.LIST %>";          
  document.frmlogactivity.action = "user_activity_log.jsp";
  document.frmlogactivity.hidden_user_id.value = 0;
  document.frmlogactivity.doc.value = "";
  document.frmlogactivity.submit();
  
}

function cmdLoad() 
{
  document.frmlogactivity.command.value="<%= Command.LOAD %>";          
  document.frmlogactivity.action = "user_activity_log.jsp";
  document.frmlogactivity.submit();
}

function cmdNew() 
{
  document.frmlogactivity.command.value="<%= Command.ADD %>";          
  document.frmlogactivity.action = "user_activity_log.jsp";
  document.frmlogactivity.submit();
}

function cmdEdit(oid) 
{
  document.frmlogactivity.command.value="<%= Command.EDIT %>";                    
  document.frmlogactivity.oid.value = oid;
  document.frmlogactivity.action = "user_activity_log.jsp";
  document.frmlogactivity.submit();
}

function cmdAssign(oid) 
{
  document.frmlogactivity.command.value="<%= Command.ASSIGN %>";       
  document.frmlogactivity.oid.value= oid;          
  document.frmlogactivity.action = "user_activity_log.jsp";
  document.frmlogactivity.submit();
}

function cmdUpdate(oid) 
{
  document.frmlogactivity.command.value="<%= Command.UPDATE %>";          
  document.frmlogactivity.oid.value = oid;
  document.frmlogactivity.action = "user_activity_log.jsp";
  document.frmlogactivity.submit();
}	
function cmdOpenUserIdSearch(){
    wWindow = window.open("user_activity_log_user_id_search.jsp", null, "height=400,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
    wWindow.focus();
}
function cmdOpenDocSearch(){
    wWindow = window.open("user_activity_log_doc_search.jsp", null, "height=400,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
    wWindow.focus();
}

function cmdApprove(){
    var val='';
<%
for (int i = 0; i < listLogSysHistory.size(); i++) {
%>     
    if(eval("frmlogactivity.logChk_"+<%=i%>+".checked")==true){
        val=val+eval("frmlogactivity.logChk_"+<%=i%>+".value")+",";
    }
<%}%>

    //alert("code delete"+val);

    document.frmlogactivity.command.value="<%=Command.APPROVE%>";

    document.frmlogactivity.approved_list.value=val;

    document.frmlogactivity.action="user_activity_log.jsp";

    document.frmlogactivity.submit();
}

/*
function cmdOpenUserIdSearch(){
    var comm = document.frmlogactivity.command.value;
    newWindow=window.open("user_activity_log_user_id_search.jsp?comm="+comm,"SelectEmployee", "height=400,width=600,status=yes,toolbar=no,menubar=no,location=center,scrollbars=yes");
    newWindow.focus();
    //document.frm_pay_emp_level.submit();
}*/
</SCRIPT>
<!-- #EndEditable --> 
<style type="text/css">
    #listPos {background-color: #FFF; border: 1px solid #CCC; padding: 3px 9px; cursor: pointer; margin: 1px 0px;}  
    #btn {
        padding: 3px; border: 1px solid #CCC; 
        background-color: #EEE; color: #777777; 
        font-size: 11px; cursor: pointer;
    }
    #btn:hover {border: 1px solid #999; background-color: #CCC; color: #FFF;}
    .tblStyle {border-collapse: collapse;font-size: 11px;}
    .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
    .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
    .title_content {
        padding: 9px 14px; 
        border-left: 1px solid #0099FF; 
        font-size: 14px; 
        background-color: #F3F3F3; 
        color:#0099FF;
        font-weight: bold;
    }
    .title_part {
        padding: 9px 14px; 
        border-left: 1px solid #0099FF; 
        font-size: 12px; 
        background-color: #F3F3F3; 
        color:#333; 
        font-weight: bold;
    }
    .part_content {
        border:1px solid #0099FF;
        border-radius: 5px;
        background-color: #F5F5F5;
    }
    .part_name {
        padding: 12px 19px; border-bottom: 1px solid #0099FF;
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
        background-color: #a9d5f2;
        color:#04619e;
        font-weight: bold;
        font-size: 12px;
    }
    #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
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
</style>
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->System 
                  &gt; User Activity Log<!-- #EndEditable --> </strong></font> </td>
              </tr> 
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmlogactivity" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="hidden_logactivity_id" value="<%=oidLogActivity%>">
                                      <input type="hidden" name="hidden_user_id" value="<%=userId%>" />
                                      <input type="hidden" name="doc" value="<%=doc%>" />
                                      <input type="hidden" name="approved_list" value="<%=approveList%>">
                                      <table>
                                          <tr>
                                              <td>System</td>
                                              <td>:</td>
                                              <td><%=I_LogHistory.SYSTEM_NAME[I_LogHistory.SYSTEM_HAIRISMA]%></td>
                                          </tr>
                                          <tr>
                                              <td>Date</td>
                                              <td>:</td>
                                              <td>
                                                  <%=ControlDate.drawDateWithStyle(frmLogSysHistory.fieldNames[FrmLogSysHistory.FRM_FIELD_START_DATE_SEARCH], startDate, 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%> &nbsp;to&nbsp; 
                                                  <%=ControlDate.drawDateWithStyle(frmLogSysHistory.fieldNames[FrmLogSysHistory.FRM_FIELD_END_DATE_SEARCH], endDate, 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>
                                              </td>
                                          </tr>
                                          <tr>
                                              <td>Document/Module</td>
                                              <td>:</td>
                                              <td class="command" nowrap><a id="btn" onclick="javascript:cmdOpenDocSearch()" >Search Doc</a></td>
                                          </tr>
                                          <%
                                          if(!doc.equals("")){
                                          %>
                                          <tr>
                                              <td></td>
                                              <td></td>
                                              <td class="command" nowrap>
                                                  <div id="listPos" onclick="">
                                                    <%=doc%>
                                                </div>
                                              </td>
                                          </tr>
                                          <%}%>
                                          <tr>
                                              <td>User</td>
                                              <td>:</td>
                                              <td class="command" nowrap><a id="btn" onclick="javascript:cmdOpenUserIdSearch()" >Search User</a></td>
                                          </tr>
                                          <%
                                          if(userId!=0){
                                          %>
                                          <tr>
                                              <td></td>
                                              <td></td>
                                              <td class="command" nowrap>
                                                  <div id="listPos" onclick="">
                                                    <%
                                                    long oidUser = userId;
                                                    Vector listAppUser = PstAppUser.listPartObj(0, 0, "USER_ID="+oidUser, "");
                                                    AppUser user = (AppUser) listAppUser.get(0);
                                                    %>
                                                    <%=user.getLoginId()%>
                                                </div>
                                              </td>
                                          </tr>
                                          <%}%>
                                          <tr>
                                              <td>Detail</td>
                                              <td>:</td>
                                              <td>
                                                  <textarea name="<%=frmLogSysHistory.fieldNames[FrmLogSysHistory.FRM_FIELD_LOG_DETAIL]%>"></textarea>
                                              </td>
                                          </tr>
                                          <tr>
                                              <td>Status</td>
                                              <td>:</td>
                                              <td>
                                                  <%
                                                        Vector val_status = new Vector(1,1);
                                                        Vector key_status = new Vector(1,1);
                                                        val_status.add("0") ;
                                                        key_status.add("-- PILIH --");
                                                        val_status.add(""+I_DocStatus.DOCUMENT_STATUS_DRAFT) ;
                                                        key_status.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT] );
                                                        val_status.add(""+I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED);
                                                        key_status.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED] );
                                                        val_status.add(""+I_DocStatus.DOCUMENT_STATUS_CLOSED);
                                                        key_status.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_CLOSED]);
                                                   %>
                                                   <%=ControlCombo.draw(""+frmLogSysHistory.fieldNames[frmLogSysHistory.FRM_FIELD_LOG_STATUS], null,"", val_status, key_status,"", "formElemen")%>
                                              </td>
                                          </tr>
                                          <tr>
                                              <td>Shortly</td>
                                              <td>:</td>
                                              <td>
                                                  <%
                                                        Vector val_short = new Vector(1,1);
                                                        Vector key_short = new Vector(1,1);
                                                        val_short.add("0") ;
                                                        key_short.add("-- PILIH --");
                                                        val_short.add(""+PstLogSysHistory.FLD_LOG_STATUS) ;
                                                        key_short.add(""+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_STATUS] );
                                                        val_short.add(""+PstLogSysHistory.FLD_LOG_UPDATE_DATE);
                                                        key_short.add(""+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_UPDATE_DATE] );
                                                        val_short.add(""+PstLogSysHistory.FLD_LOG_DOCUMENT_TYPE);
                                                        key_short.add(""+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DOCUMENT_TYPE] );
                                                        val_short.add(""+PstLogSysHistory.FLD_LOG_USER_ID);
                                                        key_short.add(""+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_USER_ID] );
                                                        val_short.add(""+PstLogSysHistory.FLD_LOG_DETAIL);
                                                        key_short.add(""+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DETAIL] );
                                                   %>
                                                   <%=ControlCombo.draw(""+frmLogSysHistory.fieldNames[frmLogSysHistory.FRM_FIELD_ORDER_1], null,"", key_short, key_short,"", "formElemen")%>,
                                                   <%=ControlCombo.draw(""+frmLogSysHistory.fieldNames[frmLogSysHistory.FRM_FIELD_ORDER_2], null,"", key_short, key_short,"", "formElemen")%>,
                                                   <%=ControlCombo.draw(""+frmLogSysHistory.fieldNames[frmLogSysHistory.FRM_FIELD_ORDER_3], null,"", key_short, key_short,"", "formElemen")%>
                                              </td>
                                          </tr>
                                          <tr>
                                            <td><a href="javascript:cmdList()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search History"></a></td>
                                            <td><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                            <td class="command" nowrap><a href="javascript:cmdList()">Search History</a></td>
                                          </tr>
                                      </table>
                                    <%
                                    if(iCommand == Command.LIST) {
                                    %>
                                      <table width="100%">
                                          <%
                                                if(listLogSysHistory.size() > 0){
                                            %>
                                          <tr>
                                            <td>
                                                <%=drawList(request, listLogSysHistory, oidLogActivity, approot)%>
                                            </td>
                                          </tr>
                                          <tr>
                                            <td>
                                                <button id="btn1" onclick="javascript:cmdApprove()">Approve</button>
                                            </td>
                                          </tr>
                                          <%} else {%>
                                          <tr>
                                            <td>
                                                <%
                                                    out.println("<div class=\"msginfo\">&nbsp;&nbsp;No log data found ...</div>");  
                                                %>
                                                 
                                            </td>
                                          </tr>
                                          <%}%>
                                      </table>
                                    <%}%>
                                    </form>
                                    <!-- #EndEditable --> </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td>&nbsp; </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
   <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
      <SCRIPT>
// Before you reuse this script you may want to have your head examined
// 
// Copyright 1999 InsideDHTML.com, LLC.  

function doBlink() {
  // Blink, Blink, Blink...
  var blink = document.all.tags("BLINK")
  for (var i=0; i < blink.length; i++)
    blink[i].style.visibility = blink[i].style.visibility == "" ? "hidden" : "" 
}

function startBlink() {
  // Make sure it is IE4
  if (document.all)
    setInterval("doBlink()",1000)
}
window.onload = startBlink;
</SCRIPT>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
