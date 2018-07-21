
<% 
/* 
 * Page Name  		:  presence_edit.jsp
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
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import ="com.dimata.qdep.form.*"%>


<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_PRESENCE, AppObjInfo.OBJ_MANUAL_PRESENCE); %>
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

	public String drawList(Vector objectClass,long periodId, long empId, Date date,long departmentId, long sectionId)
	{
                Vector status_value = new Vector(1,1);
                Vector status_key = new Vector(1,1);
       
                status_key.add("In");
                status_value.add("0");
                status_key.add("Out - Home");
                status_value.add("1");
                                                        
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("50%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Date","15%");
		ctrlist.addHeader("Time","15%");
		ctrlist.addHeader("Status Presence","15%");
		ctrlist.addHeader("Action","15%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
		System.out.println("objectClass  "+objectClass.size());
		for (int i = 0; i < objectClass.size(); i++) {
                    
			Presence presence = (Presence)objectClass.get(i);
			Vector rowx = new Vector();
			//String strDate = ""+(presence.getPresenceDatetime().getYear()+1900)+"-"+(presence.getPresenceDatetime().getMonth()+1)+"-"+presence.getPresenceDatetime().getDate();
			int year = (presence.getPresenceDatetime().getYear()+1900);
			int month = (presence.getPresenceDatetime().getMonth()+1);
			int dateP = presence.getPresenceDatetime().getDate();
			int hours = presence.getPresenceDatetime().getHours();
			int minutes = presence.getPresenceDatetime().getMinutes();
			
			String StrDate = (date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();

			rowx.add("<input type=\"hidden\"name=\"date\"  value=\""+StrDate+"\" size=\"5\" class=\"elemenForm\">"+presence.getPresenceDatetime().getDate()+"-"+(presence.getPresenceDatetime().getMonth()+1)+"-"+(presence.getPresenceDatetime().getYear()+1900));
			rowx.add(""+presence.getPresenceDatetime().getHours()+":"+presence.getPresenceDatetime().getMinutes());
			rowx.add(""+PstEmpSchedule.strPresenceStatusName[presence.getStatus()]);
			rowx.add("<a href=javascript:cmdUpdate('"+year+"','"+(date.getMonth()+1)+"','"+date.getDate()+"','"+hours+"','"+minutes+"','"+presence.getOID()+"','0','"+departmentId+"','"+sectionId+"')><b>In<b><a/>     ,     <a href=javascript:cmdUpdate('"+year+"','"+(date.getMonth()+1)+"','"+date.getDate()+"','"+hours+"','"+minutes+"','"+presence.getOID()+"','1','"+departmentId+"','"+sectionId+"')>Out-Home<a/>");			
			lstData.add(rowx);
			
		}
		return ctrlist.draw(index);
	}

%>
<%

CtrlPresence ctrlPresence = new CtrlPresence(request);
long oidPresence = FRMQueryString.requestLong(request, "hidden_presence_id");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
String empNum= FRMQueryString.requestString(request, "empNum");
String symbol= FRMQueryString.requestString(request, "symbol");
int year= FRMQueryString.requestInt(request, "year");
int month= FRMQueryString.requestInt(request, "month");
int dateP= FRMQueryString.requestInt(request, "date_hidden");
int hours= FRMQueryString.requestInt(request, "hours_hidden");
int minutes= FRMQueryString.requestInt(request, "minutes_hidden");
Date date= FRMQueryString.requestDate(request, "date");
int statusPresence= FRMQueryString.requestInt(request, "statusPresence");
long oid_presence = FRMQueryString.requestLong(request, "oid_presence_hidden");


//for session search
long oidDepartment = FRMQueryString.requestLong(request,"department");
long oidSection= FRMQueryString.requestLong(request,"section");
System.out.println("oidDepartment"+oidDepartment);
System.out.println("oidSection..........."+oidSection);
System.out.println(date);



int iErrCode = FRMMessage.ERR_NONE;
String errMsg = "";
String whereClause = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+"='"+empNum+"'";
String orderClause = "";
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request,"start");

ControlLine ctrLine = new ControlLine();
iErrCode = ctrlPresence.action(iCommand , oidPresence, request);
errMsg = ctrlPresence.getMessage();
FrmPresence frmPresence = ctrlPresence.getForm();
Presence presence = ctrlPresence.getPresence();
oidPresence = presence.getOID();


	Vector listEmp = PstEmployee.list(0,0,whereClause,"");
	String empName="";
	String empNumber="";
	long  positionId=0;
	long departmentId = 0;
	long sectionId = 0;
	Date comm = new Date();
	long empId = 0;
	
	if(iCommand==Command.EDIT ){
            date =new Date(year-1900,month-1,dateP,hours,minutes,0);
	}
        
	long periodId = PstPeriod.getPeriodIdBySelectedDate(date);
	if(listEmp!=null && listEmp.size() > 0){
		for (int i=0;i<listEmp.size();i++){
		Employee emp = (Employee) listEmp.get(i);
		empName = emp.getFullName();
		empNumber = emp.getEmployeeNum();
		positionId = emp.getPositionId();
		departmentId = emp.getDepartmentId();
		comm = emp.getCommencingDate();
		empId=emp.getOID();
		sectionId = emp.getSectionId();
		}
	}

String wherePresence = "";
int scheduleCategory = PstScheduleSymbol.getCategoryType(symbol);
int monthStartDate = Integer.parseInt(String.valueOf(date.getMonth()+1));
int yearStartDate =  Integer.parseInt(String.valueOf(date.getYear()+1900));
int dateStartDate =  Integer.parseInt(String.valueOf(date.getDate()));
GregorianCalendar periodStart = new GregorianCalendar(yearStartDate, monthStartDate-1, dateStartDate);
int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

// jika accross day
if(scheduleCategory==PstScheduleCategory.CATEGORY_ACCROSS_DAY){
	if(dateStartDate==maxDayOfMonth && monthStartDate==12){
		wherePresence = PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID]+"="+empId+
					" AND "+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME]+" BETWEEN '"+
					(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate()+" 00:00:01'"+
					" AND '"+(date.getYear()+1900+1)+"-"+(date.getMonth()-11)+"-1 23:59:59'";

        }else if(dateStartDate==maxDayOfMonth && monthStartDate < 12){
		wherePresence = PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID]+"="+empId+
					" AND "+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME]+" BETWEEN '"+
					(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate()+" 00:00:01'"+
					" AND '"+(date.getYear()+1900)+"-"+(date.getMonth()+2)+"-1 23:59:59'";

	}else{
		wherePresence = PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID]+"="+empId+
					" AND "+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME]+" BETWEEN '"+
					(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate()+" 00:00:01'"+
					" AND '"+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+(date.getDate()+1)+" 23:59:59'";
	}
}else{
	wherePresence = PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID]+"="+empId+
					" AND "+PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME]+" BETWEEN '"+
					(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate()+" 00:00:01'"+
					" AND '"+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+(date.getDate())+" 23:59:59'";
	
}

Vector listPresence = PstPresence.list(0,0,wherePresence,"");

if(iCommand==Command.EDIT){

	System.out.println("oidPresence   "+oid_presence);
	System.out.println("statusPresence   "+statusPresence);
	Date dateEmpSchedule = new Date();
	if(statusPresence==0){
		dateEmpSchedule =new Date(year-1900,month-1,dateP,hours,minutes,0);
	}else if(statusPresence==1){
		if(scheduleCategory==PstScheduleCategory.CATEGORY_ACCROSS_DAY){
			if(dateStartDate==maxDayOfMonth && monthStartDate==12){
				dateEmpSchedule =new Date(((year-1900)+1),month-12,1,hours,minutes,0);
			}else if(dateStartDate==maxDayOfMonth && monthStartDate<12){
				dateEmpSchedule =new Date((year-1900),month,1,hours,minutes,0);
			}else{
				if(hours<24){
					dateEmpSchedule =new Date(year-1900,month-1,dateP,hours,minutes,0);
				}else{
					dateEmpSchedule =new Date(year-1900,month-1,dateP+1,hours,minutes,0);
				}
			}
		}
		else{
			dateEmpSchedule =new Date(year-1900,month-1,dateP,hours,minutes,0);
		}
		
	}
	PstPresence.updatePresenceStatus(oid_presence,statusPresence);
	PstEmpSchedule.updatePresence1(periodId,date,dateEmpSchedule,empId,statusPresence,symbol);

}
 listPresence = PstPresence.list(0,0,wherePresence,"");

if(iCommand==Command.UPDATE){
	
}
 //listPresence = PstPresence.list(0,0,wherePresence,"");

/*long oidEmployee = presence.getEmployeeId();
Employee employee = new Employee();
long oidPosition = 0;
Position position = new Position();
long oidDepartment = 0;
Department department = new Department();
if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) 
{
	employee = PstEmployee.fetchExc(oidEmployee);
	oidPosition = employee.getPositionId();
	position = PstPosition.fetchExc(oidPosition);
	oidDepartment = employee.getDepartmentId();
	department = PstDepartment.fetchExc(oidDepartment);
}*/
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Manual Presence</title>
<script language="JavaScript">
	function cmdLoad(year,month,date,hours,minutes){
		document.frm_presence.year.value=year;
		document.frm_presence.month.value=month;
		document.frm_presence.date_hidden.value=date;
		document.frm_presence.hours_hidden.value=hours;
		document.frm_presence.minutes_hidden.value=minutes;
		document.frm_presence.command.value="<%=Command.EDIT%>";
		document.frm_presence.action="edit_presence.jsp?year=" + year;
		document.frm_presence.submit();
	}
	
	function cmdUpdate(year,month,date,hours,minutes,oid,status,oidDepartement,oidSection){
		//alert ("status "+status);
		document.frm_presence.year.value=year;
		document.frm_presence.month.value=month;
		document.frm_presence.date_hidden.value=date;
		document.frm_presence.hours_hidden.value=hours;
		document.frm_presence.minutes_hidden.value=minutes;
		document.frm_presence.oid_presence_hidden.value=oid;
		document.frm_presence.statusPresence.value=status;
		document.frm_presence.department.value=oidDepartement;
		document.frm_presence.section.value=oidSection;
		document.frm_presence.command.value="<%=Command.EDIT%>";
		document.frm_presence.action="edit_presence.jsp";
		document.frm_presence.submit();
	}
	
	

	function cmdCancel(){
		document.frm_presence.command.value="<%=Command.ADD%>";
		document.frm_presence.action="presence_edit.jsp";
		document.frm_presence.submit();
	} 
	function cmdCancel(){
		document.frm_presence.command.value="<%=Command.CANCEL%>";
		document.frm_presence.action="presence_edit.jsp";
		document.frm_presence.submit();
	} 

	function cmdEdit(oid){ 
		document.frm_presence.command.value="<%=Command.EDIT%>";
		document.frm_presence.action="presence_edit.jsp";
		document.frm_presence.submit(); 
	} 

	function cmdSave(){
		document.frm_presence.command.value="<%=Command.SAVE%>"; 
		document.frm_presence.action="presence_edit.jsp";
		document.frm_presence.submit();
	}
	
	function cmdUpSch(){
		document.frm_presence.command.value="<%=Command.UPDATE%>"; 
		document.frm_presence.action="edit_presence.jsp";
		document.frm_presence.submit();
	}

	function cmdAsk(oid){
		document.frm_presence.command.value="<%=Command.ASK%>"; 
		document.frm_presence.action="presence_edit.jsp";
		document.frm_presence.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_presence.command.value="<%=Command.DELETE%>";
		document.frm_presence.action="presence_edit.jsp"; 
		document.frm_presence.submit();
	}  

	function cmdBack(){
		document.frm_presence.command.value="<%=Command.LIST%>"; 
		document.frm_presence.action="presence_report_daily.jsp?datesrc='<%=Formater.formatDate(date,"yyyy-MM-dd")%>'&department=<%=oidDepartment%>&section=<%=oidSection%>";
		document.frm_presence.submit();
	}

	function cmdSearchEmp(){
            window.open("emppresencesearch.jsp?emp_number=" + document.frm_presence.EMP_NUMBER.value + "&emp_fullname=" + document.frm_presence.EMP_FULLNAME.value + "&emp_department=" + document.frm_presence.EMP_DEPARTMENT.value, null, "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}

	function cmdClearSearchEmp() {
		document.frm_presence.EMP_FULLNAME.value = "";
		document.frm_presence.EMP_NUMBER.value = "";
		document.frm_presence.EMP_POSITION.value = "";
		document.frm_presence.EMP_COMMENCING_DATE.value = "";
	}
//-------------- script control line -------------------
	function MM_swapImgRestore() { //v3.0
		var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
	}

function MM_preloadImages() { //v3.0
		var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
		var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
		if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
	}

function MM_findObj(n, d) { //v4.0
		var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
		d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
		if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
		for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
		if(!x && document.getElementById) x=document.getElementById(n); return x;
	}

function MM_swapImage() { //v3.0
		var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
		if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
	}

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
	<% if((iCommand == Command.ADD) || (iCommand == Command.SAVE && frmPresence.errorSize()>0)){%>
		document.frm_presence.EMP_DEPARTMENT.style.visibility="hidden";
	<% }%>
}
function showObjectForMenu(){
	<% if((iCommand == Command.ADD) || (iCommand == Command.SAVE && frmPresence.errorSize()>0)){%>
		document.frm_presence.EMP_DEPARTMENT.style.visibility="";
	<% }%>
}

</SCRIPT>
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnCancelOn.jpg')">
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
                  Employee &gt; Attendance &gt; Manual Presence<!-- #EndEditable --> 
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
                                    <form name="frm_presence" method="post" action="">
                                    <input type="hidden" name="command" value="">
                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                    <input type="hidden" name="start" value="<%=start%>">
                                    <input type="hidden" name="empNum" value="<%=empNum%>">
                                    <input type="hidden" name="symbol" value="<%=symbol%>">
                                    <input type="hidden" name="date" value="<%=date%>">
                                    <input type="hidden" name="year" value="<%=year%>">
                                    <input type="hidden" name="month" value="<%=month%>">
                                    <input type="hidden" name="date_hidden" value="<%=dateP%>">
                                    <input type="hidden" name="hours_hidden" value="<%=hours%>">
                                    <input type="hidden" name="minutes_hidden" value="<%=minutes%>">
                                    <input type="hidden" name="oid_presence_hidden" value="<%=oid_presence%>">
                                    <input type="hidden" name="statusPresence" value="<%=statusPresence%>">
                                    <input type="hidden" name="department" value="<%=oidDepartment%>">
                                    <input type="hidden" name="section" value="<%=oidSection%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td width="2%">&nbsp;</td>
                                          <td width="94%">&nbsp;</td>
                                          <td width="2%">&nbsp;</td>
										  <%
										  /*	if(iCommand==Command.EDIT){
												date =new Date(year-1900,month-1,dateP,hours,minutes,0);
											}*/
										 		
										   
											int datePresence = date.getDate();
										  
											
											/*if(iCommand==Command.EDIT){
												PstEmpSchedule.updatePresence(periodId,date,empId);
											}*/
											
										  %>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="94%"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                              <tr> 
                                                <td> 
                                                  <div align="center"><b><font size="3">MANUAL 
                                                    STATUS OF PRESENCE</font></b></div>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <input type="hidden" name="<%=FrmPresence.fieldNames[FrmPresence.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=presence.getEmployeeId()%>" class="formElemen">
                                                      <%//=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_EMPLOYEE_ID)%> 
                                                      <td width="12%" nowrap> 
                                                        <div align="left">Name</div>
                                                      </td>
                                                      <td width="29%" nowrap> 
                                                        : 
                                                        <%=empName %> 
                                                      </td>
                                                      <td width="10%" nowrap> 
                                                        <div align="left">Employee 
                                                          Number</div>
                                                      </td>
                                                      <td width="49%"> : 
                                                        <%=empNumber %> 
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="12%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div>
                                                      </td>
                                                      <td width="29%" nowrap> 
                                                        : 
														<%
															Position position = new Position();
															position = PstPosition.fetchExc(positionId);
															String positionName = position.getPosition();
														%>
                                                        <%= positionName %> 
                                                      </td>
                                                      <td width="10%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                      </td>
                                                      <td width="49%"> :
													  <%
															Department department = new Department();
															department = PstDepartment.fetchExc(departmentId);
															String departmentName = department.getDepartment();
														%> 
                                                        <%= departmentName%> 
                                                        
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="12%" nowrap> 
                                                        <div align="left">Commencing 
                                                          Date</div>
                                                      </td>
                                                      <td width="29%" nowrap> 
                                                        : 
                                                        <%= Formater.formatDate(comm, "dd MMMM yyyy") %> 
                                                      </td>
                                                      <td width="10%" nowrap> 
                                                        <div align="left"></div>
                                                      </td>
                                                      <td width="49%"> 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <% } else {%>
                                                        
                                                      </td>
                                                      <% } %>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <hr>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <td width="12%" valign="top" nowrap> 
                                                        <div align="left">Presence 
                                                          Date Time</div>
                                                      </td>
                                                      <td width="88%"> : 
                                                        <%
															
															out.println(drawList(listPresence,periodId,empId,date,departmentId,oidSection));
															
														%>
														  </td>
                                                    </tr> 
                                                    <tr>
													
														<!--<td>
														<a href="javascript:cmdLoad()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save"></a>
														<a href="javascript:cmdLoad()" class="command" style="text-decoration:none">Save</a></td>-->
													</tr>
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                          <td width="2%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="94%">&nbsp;</td>
                                          <td width="2%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="94%"> 
                                            <%
							ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();
							ctrLine.setTableWidth("80");
							String scomDel = "javascript:cmdAsk('"+oidPresence+"')";
							String sconDelCom = "javascript:cmdConfirmDelete('"+oidPresence+"')";
							String scancel = "javascript:cmdEdit('"+oidPresence+"')";
							ctrLine.setBackCaption("Back to List Presence");
							ctrLine.setCommandStyle("buttonlink");
							//ctrLine.setConfirmDelCaption("Yes Delete Presence");
							//ctrLine.setDeleteCaption("Delete Presence");
							//ctrLine.setSaveCaption("Save Presence");

							/*if (privDelete){
								ctrLine.setConfirmDelCommand(sconDelCom);
								ctrLine.setDeleteCommand(scomDel);
								ctrLine.setEditCommand(scancel);
							}else{ 
								ctrLine.setConfirmDelCaption("");
								ctrLine.setDeleteCaption("");
								ctrLine.setEditCaption("");
							}

							if(privAdd == false  && privUpdate == false){
								ctrLine.setSaveCaption("");
							}*/

							if (privAdd == false){
								ctrLine.setAddCaption("");
							}
							%>
                             <%//= ctrLine.drawImage(iCommand, iErrCode, errMsg)%></td>
                              <td width="2%">&nbsp;</td>
                                  </tr>
									<tr>
                                         <td> <table cellpadding="0" cellspacing="0" border="0">
											<tr>
                                             <td>&nbsp;</td>
											 <td>&nbsp;</td>
                                             <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                             <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Back"></a></td>
                                             <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                             <td nowrap><a href="javascript:cmdBack()" class="command" style="text-decoration:none">Back to Presence Daily Report</a></td>
                                             </tr>
                                             </table>
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
  <tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
