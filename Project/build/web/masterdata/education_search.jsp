<%-- 
    Document   : education_search
    Created on : Feb 3, 2015, 8:03:46 PM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.harisma.entity.masterdata.Competency"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstCompetency"%>
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
<%@ include file = "../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_POSITION);%>
<%@ include file = "../main/checkuser.jsp" %>

<%!
	public String drawList(Vector objectClass ,  long educationId)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Education","10%");                
                ctrlist.addHeader("Level", "10%");
		ctrlist.addHeader("Description","30%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdChoose('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;

		for (int i = 0; i < objectClass.size(); i++) {
			Education education = (Education)objectClass.get(i);
			 Vector rowx = new Vector();
			 if(educationId == education.getOID())
				 index = i;

			rowx.add(education.getEducation());
                        rowx.add(""+education.getEducationLevel());
			rowx.add(education.getEducationDesc());

			lstData.add(rowx);
			lstLinkData.add(String.valueOf(education.getOID()));
		}

		return ctrlist.draw(index);
	}

    public String drawData(Vector objectClass) {
        String str = "";
            for (int i = 0; i < objectClass.size(); i++) {
                Education edu = (Education)objectClass.get(i);
                
                str += "<div id='divGroup'><a href=\"javascript:cmdChoose('"+edu.getOID()+"')\">"+edu.getEducation()+"</a></div>";
            }
        
        return str;
    }
%>


<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidEducation = FRMQueryString.requestLong(request, "hidden_education_id");
    String comm = request.getParameter("comm");

    /*variable declaration*/
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = ""+PstEducation.fieldNames[PstEducation.FLD_EDUCATION_LEVEL]+" ASC ";

    CtrlEducation ctrlEducation = new CtrlEducation(request);
    ControlLine ctrLine = new ControlLine();
    Vector listEducation = new Vector(1,1);

    /*switch statement */
    iErrCode = ctrlEducation.action(iCommand , oidEducation);
    /* end switch*/
    FrmEducation frmEducation = ctrlEducation.getForm();

    /*count list All Education*/
    int vectSize = PstEducation.getCount(whereClause);

    Education education = ctrlEducation.getEducation();
    msgString =  ctrlEducation.getMessage();

    /*switch list Education*/
	/*
    if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)&& (oidEducation == 0))
            start = PstEducation.findLimitStart(education.getOID(),recordToGet, whereClause, orderClause);
	*/		

    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
      (iCommand == Command.NEXT || iCommand == Command.LAST)){
                    start = ctrlEducation.actionList(iCommand, start, vectSize, recordToGet);
     } 
    /* end switch list*/

    /* get record to display */
    listEducation = PstEducation.list(start,recordToGet, whereClause , orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listEducation.size() < 1 && start > 0)
    {
        if (vectSize - recordToGet > recordToGet)
            start = start - recordToGet;   //go to Command.PREV
        else {
            start = 0 ;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listEducation = PstEducation.list(start,recordToGet, whereClause , orderClause);
    }
%>
<html>
<head>
    <title>Competency Form</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link rel="stylesheet" href="../styles/main.css" type="text/css">
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
        function cmdChoose(eduId) {
            self.opener.document.frmposition.education_id.value = eduId;
            self.opener.document.frmposition.command.value = "<%=comm%>";                 
            //self.close();
            self.opener.document.frmposition.submit();
        }
    </script>
</head>
<body>
    <div id="title">Employee Search</div>
    <div id="content">
       
        <%if (listEducation != null && listEducation.size() > 0) {%>
        <%=drawList(listEducation, oidEducation)%>
        <%}else{%>
        <div>no record</div>
        <%}%>

       
    </div>
</body>
</html>
