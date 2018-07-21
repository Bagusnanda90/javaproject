<%-- 
    Document   : education_search
    Created on : Feb 3, 2015, 8:03:46 PM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.harisma.entity.masterdata.PositionKPI"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPositionKPI"%>
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
	public String drawList(Vector objectClass, long kpi_listId) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("80%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No ","5%");
        ctrlist.addHeader("Company","20%");
        ctrlist.addHeader("KPI Title","20%");
        ctrlist.addHeader("Description","30%");
        ctrlist.addHeader("Valid from","10%");
        ctrlist.addHeader("Valid to","10%");
        ctrlist.addHeader("Value Type","5%");
        // ctrlist.addHeader("List Group","30%");

        ctrlist.setLinkRow(2);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdChoose('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;

        //kpi_list
                    Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                   
                    Hashtable<String, String> compNames = new Hashtable();
                    for (int c = 0; c < listComp.size(); c++) {
                        Company comp = (Company) listComp.get(c);
                        compNames.put(""+comp.getOID(), comp.getCompany());                       
                    }
        
        for (int i = 0; i < objectClass.size(); i++) {
            KPI_List kpi_list = (KPI_List) objectClass.get(i);
            Vector rowx = new Vector();
            if (kpi_listId == kpi_list.getOID()) {
                index = i;
            }
            rowx.add(""+(i+1));
            rowx.add(""+compNames.get(""+kpi_list.getCompany_id()));
            rowx.add(""+kpi_list.getKpi_title());
            rowx.add(""+kpi_list.getDescription());
            rowx.add(""+kpi_list.getValid_from());
            rowx.add(""+kpi_list.getValid_to());
            rowx.add(""+kpi_list.getValue_type());

            lstData.add(rowx);
            lstLinkData.add(String.valueOf(kpi_list.getOID()));
        }
        return ctrlist.draw(index);
    }

    public String drawData(Vector objectClass) {
        String str = "";
            for (int i = 0; i < objectClass.size(); i++) {
                KPI_List kpiList = (KPI_List)objectClass.get(i);
                
                str += "<div id='divGroup'><a href=\"javascript:cmdChoose('"+kpiList.getOID()+"')\">"+kpiList.getKpi_title()+"</a></div>";
            }
        
        return str;
    }
%>


<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidKpi = FRMQueryString.requestLong(request, "hidden_kpi_id");
    String comm = request.getParameter("comm");
    
    int recordToGet = 10;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";

    CtrlPositionKPI ctrlPositionKPI = new CtrlPositionKPI(request);
    ControlLine ctrLine = new ControlLine();
    Vector listKpi = new Vector(1,1);

    /*switch statement */
    iErrCode = ctrlPositionKPI.action(iCommand , oidKpi);
    /* end switch*/
    FrmPositionKPI frmPositionKPI = ctrlPositionKPI.getForm();

    /*count list All Education*/
    int vectSize = PstPositionKPI.getCount(whereClause);

    PositionKPI positionKPI = ctrlPositionKPI.getPositionKpi();
    msgString =  ctrlPositionKPI.getMessage();

    /*switch list Education*/
	/*
    if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)&& (oidEducation == 0))
            start = PstEducation.findLimitStart(education.getOID(),recordToGet, whereClause, orderClause);
	*/		

    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
      (iCommand == Command.NEXT || iCommand == Command.LAST)){
                    start = ctrlPositionKPI.actionList(iCommand, start, vectSize, recordToGet);
     } 
    /* end switch list*/

    /* get record to display */
    listKpi = PstKPI_List.list(start,recordToGet, whereClause , orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listKpi.size() < 1 && start > 0)
    {
        if (vectSize - recordToGet > recordToGet)
            start = start - recordToGet;   //go to Command.PREV
        else {
            start = 0 ;
            iCommand = Command.FIRST;
            prevCommand = Command.FIRST; //go to Command.FIRST
        }
        listKpi = PstKPI_List.list(start,recordToGet, whereClause , orderClause);
    }
%>
<html>
<head>
    <title>KPI Form</title>
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
        function cmdChoose(kpiId) {
            self.opener.document.frmposition.kpi_id.value = kpiId;
            self.opener.document.frmposition.command.value = "<%=comm%>";                 
            //self.close();
            self.opener.document.frmposition.submit();
        }
    </script>
</head>
<body>
    <div id="title">KPI Search</div>
    <div id="content">
       
        <%if (listKpi != null && listKpi.size() > 0) {%>
        <%=drawList(listKpi, oidKpi)%>
        <%}else{%>
        <div>no record</div>
        <%}%>

       
    </div>
</body>
</html>
