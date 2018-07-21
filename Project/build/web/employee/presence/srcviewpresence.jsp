<% 
/* 
 * Page Name  		:  srcpresence.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 			: [output ...] 
 *******************************************************************/
%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%//@ page import = "java.sql.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package hris -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_PRESENCE   	); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%!
public String drawList(Vector vDate, Vector vSchedule, Vector vScdTimeIn, Vector vScdTimeOut, Vector vTimeIn, Vector vTimeOut) {
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("90%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("Day In","3%", "2", "0");
	ctrlist.addHeader("Date In","4%", "2", "0");
	ctrlist.addHeader("Schedule","6%", "0", "3");
	ctrlist.addHeader("Symbol","2%", "0", "0");
	ctrlist.addHeader("Time In","2%", "0", "0");
	ctrlist.addHeader("Time Out","2%", "0", "0");
	ctrlist.addHeader("Actual","2%", "0", "3");
	ctrlist.addHeader("Time In","2%", "0", "0");
	ctrlist.addHeader("Duration","2%", "0", "0");	
	ctrlist.addHeader("Time Out","2%", "0", "0");
	ctrlist.addHeader("Difference","4%", "0", "2");
	ctrlist.addHeader("In","2%", "0", "0");
	ctrlist.addHeader("Out","2%", "0", "0");
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	
	for (int i = 0; i < vDate.size(); i++) {
		Vector rowx = new Vector();
	
		Date d = new Date(String.valueOf(vDate.get(i)));
		//System.out.println("d"+d);
		SimpleDateFormat formatter = new SimpleDateFormat ("d MMMM yyyy");
		SimpleDateFormat formatterDay = new SimpleDateFormat ("EEEE");
		String dateString = formatter.format(d);
		String dayString = formatterDay.format(d);
	
		dateString = (dayString.equalsIgnoreCase("Saturday")) ? "<font color=\"darkblue\">" + dateString + "</font>" : dateString;
		dayString = (dayString.equalsIgnoreCase("Saturday")) ? "<font color=\"darkblue\">" + dayString + "</font>" : dayString;
		dateString = (dayString.equalsIgnoreCase("Sunday")) ? "<font color=\"red\">" + dateString + "</font>" : dateString;
		dayString = (dayString.equalsIgnoreCase("Sunday")) ? "<font color=\"red\">" + dayString + "</font>" : dayString;
		
		
		Date timeIn = (Date) vScdTimeIn.get(i);
		Date timeOut = (Date) vScdTimeOut.get(i);
		Date actualTimeIn = (Date) vTimeIn.get(i);
		Date actualTimeOut = (Date)vTimeOut.get(i);
		
		
		/* -------------- day & date period  -------------- */
		rowx.add(dayString); 
		rowx.add(dateString); 
		
		/* -------------- schedule -------------- */
		String scheduleSimbol = String.valueOf(vSchedule.get(i));
		rowx.add(scheduleSimbol); 		
		rowx.add((timeIn != null && scheduleSimbol.length()>0 ) ? Formater.formatTimeLocale(timeIn, "HH:mm"):"");		
		rowx.add((timeOut != null && scheduleSimbol.length()>0 ) ?Formater.formatTimeLocale(timeOut, "HH:mm"):"");
						
		/* -------------- actual -------------- */		
		rowx.add(actualTimeIn != null?Formater.formatTimeLocale(actualTimeIn, "HH:mm"):"");		
		// -------------- calculate duration	
		long iDuration = 0;
		long iDurationHour = 0;
		long iDurationMin = 0;
		String strDurationHour = "";
		String strDurationMin = "";
		if(	actualTimeIn != null && actualTimeOut != null){
			long iDurTimeIn = actualTimeIn.getTime()/1000;
			long iDurTimeOut = actualTimeOut.getTime()/1000;
		
			iDuration = 0;
			if (iDurTimeIn != iDurTimeOut) {
				iDuration = (iDurTimeIn == 0 || iDurTimeOut == 0) ? 0 : iDurTimeOut - iDurTimeIn;
			}
			iDurationHour = (iDuration - (iDuration % 3600)) / 3600;
			iDurationMin = iDuration % 3600 / 60;
		
			strDurationHour = (iDurationHour != 0) ? iDurationHour + "h, " : "";
			strDurationMin = (iDurationMin != 0) ? iDurationMin + "m " : "";
			rowx.add(strDurationHour + strDurationMin);
		}else{
			rowx.add("");	
		}		

		rowx.add(actualTimeOut != null?Formater.formatTimeLocale(actualTimeOut, "HH:mm"):"");
		
		/* -------------- difference beetwen time in and  actual time in -------------- */						
		if(timeIn != null && actualTimeIn != null){
			timeIn.setSeconds(0);																			
			iDuration = timeIn.getTime()/60000 - actualTimeIn.getTime()/60000;
			iDurationHour = (iDuration - (iDuration % 60)) / 60;
			iDurationMin = iDuration % 60;
			strDurationHour = iDurationHour + "h, ";
			strDurationMin = iDurationMin + "m ";				
			rowx.add(strDurationHour + strDurationMin);
		}else{
			rowx.add("");
		}
		
		
		/* -------------- difference beetwen time out and  actual time out -------------- */
		if(timeOut != null && actualTimeOut != null){
			timeOut.setSeconds(0);					
			iDuration = actualTimeOut.getTime()/60000 - timeOut.getTime()/60000 ;
			iDurationHour = (iDuration - (iDuration % 60)) / 60;
			iDurationMin = iDuration % 60;
			strDurationHour = iDurationHour + "h, ";
			strDurationMin = iDurationMin + "m ";
			rowx.add(strDurationHour + strDurationMin);
		}else{
			rowx.add("");
		}
		
		/* -------------- different calculation -------------- */
		/*Date tTimeIn = (strTimeIn == "") ? null : new Date(strTimeIn);
		Date tTimeOut = (strTimeOut == "") ? null : new Date(strTimeOut);
		java.sql.Time tScdTimeIn = (strScdTimeIn == "") ? null : java.sql.Time.valueOf(strScdTimeIn);
		java.sql.Time tScdTimeOut = (strScdTimeOut == "") ? null : java.sql.Time.valueOf(strScdTimeOut);
	
		int iTimeInMin = (tTimeIn == null) ? 0 : tTimeIn.getMinutes();
		int iTimeInHours = (tTimeIn == null) ? 0 : tTimeIn.getHours();
		int iTimeOutMin = (tTimeOut == null) ? 0 : tTimeOut.getMinutes();
		int iTimeOutHours = (tTimeOut == null) ? 0 : tTimeOut.getHours();
		int iScdTimeInMin = (tScdTimeIn == null) ? 0 : tScdTimeIn.getMinutes();
		int iScdTimeInHours = (tScdTimeIn == null) ? 0 : tScdTimeIn.getHours();
		int iScdTimeOutMin = (tScdTimeOut == null) ? 0 : tScdTimeOut.getMinutes();
		int iScdTimeOutHours = (tScdTimeOut == null) ? 0 : tScdTimeOut.getHours();
		
		long iTimeIn = (d.getTime()/60000) + (iTimeInMin * 60) + (iTimeInHours * 3600);
		long iTimeOut = (d.getTime()/60000) + (iTimeOutMin * 60) + (iTimeOutHours * 3600);
		long iScdTimeIn = (d.getTime()/60000) + (iScdTimeInMin * 60) + (iScdTimeInHours * 3600);
		long iScdTimeOut = (d.getTime()/60000) + (iScdTimeOutMin * 60) + (iScdTimeOutHours * 3600);
	
		long iDiffTimeIn = 0;
		long iDiffTimeOut = 0;
		if (iScdTimeIn != iScdTimeOut) {
			iDiffTimeIn = (iTimeIn == 0) ? 0 : iScdTimeIn - iTimeIn;
			iDiffTimeOut = (iTimeOut == 0) ? 0 : iTimeOut - iScdTimeOut;
		}
		long iDiffTimeInHour = (iDiffTimeIn - (iDiffTimeIn % 3600)) / 3600;
		long iDiffTimeInMin = iDiffTimeIn % 3600 / 60;
		long iDiffTimeOutHour = (iDiffTimeOut - (iDiffTimeOut % 3600)) / 3600;
		long iDiffTimeOutMin = iDiffTimeOut % 3600 / 60;
	
		String strDiffTimeInHour = (iDiffTimeInHour != 0) ? iDiffTimeInHour + "h, " : "";
		String strDiffTimeInMin = (iDiffTimeInMin != 0) ? iDiffTimeInMin + "m " : "";
		String strDiffTimeOutHour = (iDiffTimeOutHour != 0) ? iDiffTimeOutHour + "h, " : "";
		String strDiffTimeOutMin = (iDiffTimeOutMin != 0) ? iDiffTimeOutMin + "m " : "";
		rowx.add(strDiffTimeInHour + strDiffTimeInMin);
		rowx.add(strDiffTimeOutHour + strDiffTimeOutMin);*/
	
		lstData.add(rowx);
	}

	return ctrlist.drawList();
}
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
long gotoDept = FRMQueryString.requestLong(request, "hidden_goto_dept");
long gotoEmp = FRMQueryString.requestLong(request, "hidden_goto_emp");
long gotoPeriod = FRMQueryString.requestLong(request, "hidden_goto_period");
String strDepartment = FRMQueryString.requestString(request, "hidden_str_dept");
String strEmployee = FRMQueryString.requestString(request, "hidden_str_emp");
String strPeriod = FRMQueryString.requestString(request, "hidden_str_period");

Date periodStart = new Date();
Date periodEnd = new Date();
Date counterDate = new Date();
Date tmpCounterDate = null;
Date timeIn = null;
Date timeOut = null;
Date paramTimeIn = null;
Date paramTimeOut = null;

Vector vDate = new Vector(1,1);
Vector vPresenceIdIn = new Vector(1,1);
Vector vPresenceIdOut = new Vector(1,1);
Vector vTimeIn = new Vector(1,1);
Vector vTimeOut = new Vector(1,1);
Vector vScdSymbol = new Vector(1,1);
Vector vScdTimeIn = new Vector(1,1);
Vector vScdTimeOut = new Vector(1,1);

long lScheduleId = 0;
long lPresenceIdIn = 0;
long lPresenceIdOut = 0;
String scdSymbol = "";
Date scdTimeIn = new Date();
Date scdTimeOut = new Date();

if ((iCommand==Command.LIST) && (gotoPeriod > 0)) {
	PstPeriod pstperiod = new PstPeriod();
	Period period = pstperiod.fetchExc(gotoPeriod);
	periodStart = period.getStartDate();
	periodEnd = period.getEndDate();
	periodEnd.setDate(periodEnd.getDate()+1);
	counterDate = periodStart;
	while (counterDate.before(periodEnd)) {
		/* --- scheduled day and date --- */		
		vDate.add(counterDate.toLocaleString()); //---> scheduled day&date 
		String whichDt = "D" + String.valueOf(counterDate.getDate());
		try {
			lScheduleId = SessPresence.getPresenceSchedule(gotoEmp, gotoPeriod, whichDt);
			scdSymbol = SessPresence.getPresenceScdSymbol(lScheduleId);
			vScdSymbol.add(scdSymbol); //---> schedule symbol
			//out.println("isi scdSymbol "+scdSymbol);
			scdTimeIn = SessPresence.getPresenceScdTimeInOut(lScheduleId, 0);
			scdTimeOut = SessPresence.getPresenceScdTimeInOut(lScheduleId, 1);

			if ((scdTimeIn != null) && (scdTimeOut != null)) {
				if (scdTimeIn.equals(scdTimeOut)) {
					scdTimeIn = null;
					scdTimeOut = null;
					paramTimeIn = null;
					paramTimeOut = null;
					timeIn = null;
					timeOut = null;
				} else {
					paramTimeIn = new Date(counterDate.getYear(), counterDate.getMonth(), counterDate.getDate(), scdTimeIn.getHours(), scdTimeIn.getMinutes(), scdTimeIn.getSeconds());					
					timeIn = SessPresence.getPresenceActualIn(gotoEmp, paramTimeIn);
					if (((scdTimeIn != null) && (scdTimeOut != null)) && (scdTimeIn.getTime() > scdTimeOut.getTime())) {
						paramTimeOut = new Date(counterDate.getYear(), counterDate.getMonth(), counterDate.getDate() + 1, scdTimeOut.getHours(), scdTimeOut.getMinutes(), scdTimeOut.getSeconds());
					}
					else {
						int day = counterDate.getDate();
						if(scdTimeOut.getHours()< scdTimeIn.getHours()){									
								day = day+1;													
						}
						paramTimeOut = new Date(counterDate.getYear(), counterDate.getMonth(), day, scdTimeOut.getHours(), scdTimeOut.getMinutes(), scdTimeOut.getSeconds());
					}
					timeOut = SessPresence.getPresenceActualOut(gotoEmp, paramTimeOut);
				}
			}
			
			/* --- scheduled time in --- */
			if (paramTimeIn != null) {				
				vScdTimeIn.add(paramTimeIn);
			} else {
				vScdTimeIn.add(null);
			}
			
			/* --- scheduled time out --- */
			if (paramTimeOut != null) {
				vScdTimeOut.add(paramTimeOut);
			} else {
				vScdTimeOut.add(null);
			}
		} catch (Exception e) {
			System.out.println("errrrrrrror "+e);
			vScdSymbol.add("");
			vScdTimeIn.add("");
			vScdTimeOut.add("");
		}

		/* --- actual time out --- */			
		if (timeIn != null) {
			vTimeIn.add(timeIn);
		} else {
			vTimeIn.add(null);
		}
		
		/* --- actual time out --- */
		if (timeOut != null) {
			vTimeOut.add(timeOut);
		} else {
			vTimeOut.add(null);
		}

		counterDate.setDate(counterDate.getDate()+1);
	}
	
	/* ----- add data to vector -----*/
	Vector vectEmployeePresence = new Vector();
	vectEmployeePresence.add(strDepartment);
	vectEmployeePresence.add(strEmployee);
	vectEmployeePresence.add(strPeriod);
	vectEmployeePresence.add(vDate);
	vectEmployeePresence.add(vScdSymbol);
	vectEmployeePresence.add(vScdTimeIn);
	vectEmployeePresence.add(vScdTimeOut);
	vectEmployeePresence.add(vTimeIn);
	vectEmployeePresence.add(vTimeOut);
	
	
	
	if(session.getValue("EMP_PRESENCE")!=null){
		session.removeValue("EMP_PRESENCE");
	}
	session.putValue("EMP_PRESENCE",vectEmployeePresence);
	
}

%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Harisma - View Presence</title>
<script language="JavaScript">
<!--

function deptChange() {
	document.frmsrcpresence.command.value = "<%=Command.GOTO%>";
	document.frmsrcpresence.hidden_goto_dept.value = document.frmsrcpresence.DEPARTMENT_ID.value;
	
	var deptText = "";	
	for(i=0; i<document.frmsrcpresence.DEPARTMENT_ID.length; i++) {
		if(document.frmsrcpresence.DEPARTMENT_ID.options[i].selected){
			deptText = document.frmsrcpresence.DEPARTMENT_ID.options[i].text;
		}
	}	
	document.frmsrcpresence.hidden_str_dept.value = deptText;	
	
	document.frmsrcpresence.hidden_goto_period.value = "0";
	document.frmsrcpresence.hidden_goto_emp.value = "0";
	document.frmsrcpresence.action = "srcviewpresence.jsp";
	document.frmsrcpresence.submit();
}

function empChange() {
	document.frmsrcpresence.command.value = "<%=Command.GOTO%>";
	document.frmsrcpresence.hidden_goto_emp.value = document.frmsrcpresence.EMPLOYEE_ID.value;

	var empText = "";	
	for(i=0; i<document.frmsrcpresence.EMPLOYEE_ID.length; i++) {
		if(document.frmsrcpresence.EMPLOYEE_ID.options[i].selected){
			empText = document.frmsrcpresence.EMPLOYEE_ID.options[i].text;
		}
	}	
	document.frmsrcpresence.hidden_str_emp.value = empText;	

	document.frmsrcpresence.hidden_goto_period.value = "0";
	document.frmsrcpresence.action = "srcviewpresence.jsp";
	document.frmsrcpresence.submit();
}

function periodChange() {
	document.frmsrcpresence.command.value = "<%=Command.LIST%>";
	document.frmsrcpresence.hidden_goto_period.value = document.frmsrcpresence.PERIOD_ID.value;

	var perText = "";	
	for(i=0; i<document.frmsrcpresence.PERIOD_ID.length; i++) {
		if(document.frmsrcpresence.PERIOD_ID.options[i].selected){
			perText = document.frmsrcpresence.PERIOD_ID.options[i].text;
		}
	}	
	document.frmsrcpresence.hidden_str_period.value = perText;	

	document.frmsrcpresence.action = "srcviewpresence.jsp";
	document.frmsrcpresence.submit();
}

function reportPdf(){	 
	var linkPage = "<%=printroot%>.report.staffcontrol.EmpPresencePdf"; 
	window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			
}
//-------------- script control line -------------------
//-->
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
function hideObjectForEmployee(){    
} 
	 
function hideObjectForLockers(){ 
}
	
function hideObjectForCanteen(){
}
	
function hideObjectForClinic(){
}

function hideObjectForMasterdata(){
}

</SCRIPT>
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">    
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" --> 
              Employee &gt; Attendance &gt; Presence &gt; View Presence<!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td class="tablecolor"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                <form name="frmsrcpresence" method="post" action="">
                                  <input type="hidden" name="command" value="<%=iCommand%>">
                                  <input type="hidden" name="hidden_goto_dept" value="<%=gotoDept%>">
                                  <input type="hidden" name="hidden_goto_emp" value="<%=gotoEmp%>">
                                  <input type="hidden" name="hidden_goto_period" value="<%=gotoPeriod%>">
                                  <input type="hidden" name="hidden_str_dept" value="<%=strDepartment%>">
                                  <input type="hidden" name="hidden_str_emp" value="<%=strEmployee%>">
                                  <input type="hidden" name="hidden_str_period" value="<%=strPeriod%>">								  
                                  <input type="hidden" name="hidden_presence_id">
                                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                          <tr>
                                                <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="1%">&nbsp;</td>
                                                <td width="65%"> 
                                                  <table width="" border="0" cellspacing="2" cellpadding="2" bgcolor="">
                                                    <tr> 
                                                      <td width="31%" nowrap> 
                                                        <div align="right">Department 
                                                          : </div>
                                                      </td>
                                                      <td width="69%"> 
                                                        <%
                                                        Vector dept_value = new Vector(1,1);
                                                        Vector dept_key = new Vector(1,1);
                                                        Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");
                                                        dept_key.add("select...");
                                                        dept_value.add("0");
                                                        String selectDept = String.valueOf(gotoDept);
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                        }
                                                    %>
                                                        <%= ControlCombo.draw("DEPARTMENT_ID","formElemen",null, selectDept, dept_value, dept_key, "onchange=\"javascript:deptChange();\"") %> 
                                                      </td>
                                                    </tr>
                                                    <%
                                                        if (gotoDept > 0) {
                                                    %>
                                                    <tr> 
                                                      <td width="31%" nowrap> 
                                                        <div align="right">Full 
                                                          Name : </div>
                                                      </td>
                                                      <td width="69%"> 
                                                        <%
                                                            String whereEmp = " DEPARTMENT_ID = " + gotoDept + " AND RESIGNED = 0 ";
                                                            String orderEmp = " FULL_NAME ";
                                                            Vector emp_value = new Vector(1,1);
                                                            Vector emp_key = new Vector(1,1);
                                                            emp_value.add("0");
                                                            emp_key.add("select...");
                                                            Vector listEmp = PstEmployee.list(0, 0, whereEmp, orderEmp);
                                                            String selectValueEmp = String.valueOf(gotoEmp);
                                                            for (int i = 0; i < listEmp.size(); i++) {
                                                                    Employee emp = (Employee) listEmp.get(i);
                                                                    emp_key.add(emp.getFullName());
                                                                    emp_value.add(String.valueOf(emp.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw("EMPLOYEE_ID","elementForm", null, selectValueEmp, emp_value, emp_key, "onchange=\"javascript:empChange();\"") %> 
                                                      </td>
                                                    </tr>
                                                    <% 
                                                        }
                                                        if (gotoEmp > 0) {
                                                    %>
                                                    <tr> 
                                                      <td width="31%" nowrap> 
                                                        <div align="right">Periode 
                                                          : </div>
                                                        <div align="right"></div>
                                                      </td>
                                                      <td width="69%"> 
                                                        <% 
                                                            Vector period_value = new Vector(1,1);
                                                            Vector period_key = new Vector(1,1);
                                                            period_key.add("0");
                                                            period_value.add("select...");
                                                            String selectValuePeriod = String.valueOf(gotoPeriod);
                                                            Vector listPeriod = new Vector(1,1);
                                                            listPeriod = PstPeriod.list(0, 0, "", " START_DATE DESC ");
                                                            for (int i = 0; i < listPeriod.size(); i++) {
                                                                    Period lsperiod = (Period) listPeriod.get(i);
                                                                    period_value.add(lsperiod.getPeriod());
                                                                    period_key.add(String.valueOf(lsperiod.getOID()));
                                                            }
                                                        %>
                                                        <%=ControlCombo.draw("PERIOD_ID", null, selectValuePeriod, period_key, period_value, "onchange=\"javascript:periodChange();\"")%> 
                                                      </td>
                                                    </tr>
                                                    <% } %>
                                                  </table>
                                                </td>
                                                <td width="34%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="1%">&nbsp;</td>
                                                <td colspan="2"> 
                                                  <% if ((vDate != null) && (vDate.size () > 0)) { 
                                                try{%>
                                                  <%= drawList(vDate, vScdSymbol, vScdTimeIn, vScdTimeOut, vTimeIn, vTimeOut) %>                                                   
                                                  <table width="18%" border="0" cellspacing="1" cellpadding="1">
                                                    <tr>
                                                      <td width="17%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
                                                      <td width="83%"><b><a href="javascript:reportPdf()" class="buttonlink">Print 
                                                        Attendance Record</a></b> 
                                                      </td>
                                                    </tr>
                                                  </table>
                                                  <%	}catch(Exception exc){
                                                                        System.out.println(exc);
                                                                }												  
                                                  }%>
                                                </td>
                                              </tr>
                                            </table>
                                        </td>
                                          </tr>
                                        </table>
                                      </form>
                                    <!-- #EndEditable -->
                            </td>
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
      </table>
		  </td> 
        </tr>
      </table>
    </td> 
  </tr>
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
