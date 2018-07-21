<%-- 
    Document   : education_search
    Created on : Feb 3, 2015, 8:03:46 PM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.harisma.entity.log.LogSysHistory"%>
<%@page import="com.dimata.harisma.entity.log.PstLogSysHistory"%>
<%@page import="com.dimata.harisma.form.log.FrmLogSysHistory"%>
<%@page import="com.dimata.harisma.form.log.CtrlLogSysHistory"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_POSITION);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%!
	public String drawList(Vector objectClass, long oidLog) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("80%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No ","5%");
        ctrlist.addHeader("User Id","20%");
        ctrlist.addHeader("User Name","20%");

        ctrlist.setLinkRow(1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdChoose('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;
        
        for (int i = 0; i < objectClass.size(); i++) {
            LogSysHistory logSysHistory = (LogSysHistory)objectClass.get(i);
            Vector rowx = new Vector();
            
            rowx.add(""+(i+1));
            
            Vector listAppUser = PstAppUser.listPartObj(0, 0, "USER_ID="+logSysHistory.getLogUserId(), "");
            AppUser user = (AppUser) listAppUser.get(0);
            
            rowx.add(""+user.getLoginId());
            rowx.add(""+user.getFullName());
            
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(logSysHistory.getLogUserId()));
        }
        return ctrlist.draw(index);
    }
%>


<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidLogActivity = FRMQueryString.requestLong(request, "hidden_logactivity_id");
    String comm = request.getParameter("comm");
    
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";

    CtrlLogSysHistory ctrlLogSysHistory = new CtrlLogSysHistory(request);
    ControlLine ctrLine = new ControlLine();
    Vector listLogSysHistory = new Vector(1,1);

    /*switch statement */
    iErrCode = ctrlLogSysHistory.action(iCommand , oidLogActivity, "", 0);
    /* end switch*/
    FrmLogSysHistory frmLogSysHistory = ctrlLogSysHistory.getForm();

    /*count list All Education*/
    int vectSize = PstLogSysHistory.getCount(whereClause);

    LogSysHistory logSysHistory = ctrlLogSysHistory.getLogSysHistory();
    msgString =  ctrlLogSysHistory.getMessage();

    /*switch list Education*/
	/*
    if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)&& (oidEducation == 0))
            start = PstEducation.findLimitStart(education.getOID(),recordToGet, whereClause, orderClause);
	*/		

    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
      (iCommand == Command.NEXT || iCommand == Command.LAST)){
                    start = ctrlLogSysHistory.actionList(iCommand, start, vectSize, recordToGet);
     } 
    /* end switch list*/

    /* get record to display */
    listLogSysHistory = PstLogSysHistory.listDistinchUserId();

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listLogSysHistory.size() < 1 && start > 0)
    {
        if (vectSize - recordToGet > recordToGet)
            start = start - recordToGet;   //go to Command.PREV
        else {
            start = 0 ;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listLogSysHistory = PstLogSysHistory.listDistinchUserId();
    }
%>
<html>
<head>
    <title>User Search</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link rel="stylesheet" href="../../styles/main.css" type="text/css">
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
        }
        #title {
            padding: 5px 7px;
            border-bottom: 1px solid #0099FF;
            background-color: #EEE;
            font-size: 24px;
            color: #333;
        }
        #content {
            background-color: #F7F7F7;
            padding: 5px 7px;
            margin-top: 7px;
        }
    </style>
    <script language="javascript">
        function cmdChoose(kpiId) {
            self.opener.document.frmlogactivity.hidden_user_id.value = kpiId;
            self.opener.document.frmlogactivity.command.value = "<%=comm%>";
            self.opener.document.frmlogactivity.submit();
        }
    </script>
</head>
<body>
    <div id="title">User Search</div>
    <div id="content">
       
        <%if (listLogSysHistory != null && listLogSysHistory.size() > 0) {%>
        <%=drawList(listLogSysHistory, oidLogActivity)%>
        <%}else{%>
        <div>no record</div>
        <%}%>

       
    </div>
</body>
</html>
