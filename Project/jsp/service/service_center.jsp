

<% 
/* 
 * Page Name  		:  back_up.jsp
 * Created on 		:  [date] [time] AM/PM    
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version]    
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
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
<!--package harisma -->
<%@ page import = "com.dimata.harisma.utility.service.presence.*" %>
<%@ page import = "com.dimata.harisma.utility.service.leavedp.*" %>
<%@ page import = "com.dimata.harisma.utility.service.backupdb.*" %>
<%@ page import = "com.dimata.harisma.utility.service.message.*" %>
<%@ page import = "com.dimata.harisma.entity.service.*" %>
<%@ page import = "com.dimata.harisma.form.service.*" %>

<%@ include file = "../main/javainit.jsp" %>
<%  int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_SERVICE_CENTER, AppObjInfo.OBJ_SERVICE_CENTER); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>

<!-- Jsp Block -->
<%
int iCommandAbsence = FRMQueryString.requestInt(request,"command_absence");
int iCommandLateness = FRMQueryString.requestInt(request,"command_lateness");
int iCommandLeave = FRMQueryString.requestInt(request,"command_leave");
int iCommandPresence = FRMQueryString.requestInt(request,"command_presence");
int iCommandBackup = FRMQueryString.requestInt(request,"command_backup");
int iCommandService = FRMQueryString.requestInt(request,"command_service");
int iCommandMessage = FRMQueryString.requestInt(request,"command_message");
int iCommandDPCheck = FRMQueryString.requestInt(request, "command_dpcheck");
long oidServiceAbsence = FRMQueryString.requestLong(request, "hidden_service_absence_id");
long oidServiceLateness = FRMQueryString.requestLong(request, "hidden_service_lateness_id");
long oidServiceLeave = FRMQueryString.requestLong(request, "hidden_service_leave_id");
long oidServicePresence = FRMQueryString.requestLong(request, "hidden_service_presence_id");
long oidServiceBackup = FRMQueryString.requestLong(request, "hidden_service_backup_id"); 
long oidServiceMessage = FRMQueryString.requestLong(request, "hidden_service_message_id"); 
long oidServiceDPCheck = FRMQueryString.requestLong(request, "hidden_service_dpcheck_id");
//update by satrya 2013-02-24
Date selectedDateStart = FRMQueryString.requestDate(request, "check_date_start");
Date toDay = new Date();

Date mDateFrom = new Date();
ServiceAlStock serviceAlStock =  ServiceAlStock.getInstance(true);  
if(serviceAlStock.getStockAl().getStartDate()!=null){ 
mDateFrom = serviceAlStock.getStockAl().getStartDate();
 }
// simpan data dalam array
String strServiceType[] = request.getParameterValues(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_SERVICE_TYPE]);
String strStartTimeHour[] = request.getParameterValues(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_START_TIME]+"_hr");
String strStartTimeMinutes[] = request.getParameterValues(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_START_TIME]+"_mi");
String strInterval[] = request.getParameterValues(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_PERIODE]);

int[] arrCommand = { 
	iCommandPresence,
	iCommandAbsence,	
	iCommandLateness,  	
        iCommandLeave,  	
	iCommandMessage        
};

long[] arrOidService = { 
	oidServicePresence, 
	oidServiceAbsence,	
	oidServiceLateness,  	
	oidServiceLeave,  	
	oidServiceMessage
};

if(strServiceType == null || strServiceType.length == 0) {
        strServiceType = new String[5];    
        strServiceType[0] = String.valueOf(PstServiceConfiguration.SERVICE_TYPE_PRESENCE);
        strServiceType[1] = String.valueOf(PstServiceConfiguration.SERVICE_TYPE_ABSENCE);
        strServiceType[2] = String.valueOf(PstServiceConfiguration.SERVICE_TYPE_LATENESS);        
        strServiceType[3] = String.valueOf(PstServiceConfiguration.SERVICE_TYPE_LEAVE);        
        strServiceType[4] = String.valueOf(PstServiceConfiguration.SERVICE_TYPE_MESSAGE);        
}

Vector vectResult = new Vector(1,1);
int svcCount = arrOidService.length;
CtrlServiceConfiguration ctrlServiceConfiguration = new CtrlServiceConfiguration(request);	
vectResult = ctrlServiceConfiguration.action (svcCount, arrCommand, arrOidService, strServiceType, strStartTimeHour, strStartTimeMinutes, strInterval);

// instantiate semua object service conf utk masing-masing jenis service
ServiceConfiguration serviceConfPresence = new ServiceConfiguration();
ServiceConfiguration serviceConfAbsence = new ServiceConfiguration();
ServiceConfiguration serviceConfLateness = new ServiceConfiguration();
ServiceConfiguration serviceConfLeave = new ServiceConfiguration();
ServiceConfiguration serviceConfMessage = new ServiceConfiguration();

if(vectResult!=null && vectResult.size()>0)
{
	serviceConfPresence = (ServiceConfiguration) vectResult.get(0);
	serviceConfAbsence = (ServiceConfiguration) vectResult.get(1);
	serviceConfLateness = (ServiceConfiguration) vectResult.get(2);	
        serviceConfLeave = (ServiceConfiguration) vectResult.get(3);	
	serviceConfMessage = (ServiceConfiguration) vectResult.get(4);        
	
	oidServicePresence = serviceConfPresence.getOID();
	oidServiceAbsence = serviceConfAbsence.getOID();
	oidServiceLateness = serviceConfLateness.getOID();	
        oidServiceLeave = serviceConfLeave.getOID();	
	oidServiceMessage = serviceConfMessage.getOID();        
}
%>
<!-- End of Jsp Block -->

<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Service Center</title>
<script language="JavaScript">
    
// stop command

function cmdStopLateness()
{
  document.frm_servicecenter.command_lateness.value="<%=String.valueOf(Command.STOP)%>"; 
  document.frm_servicecenter.start.value="0";  
  document.frm_servicecenter.maxLog.value="0";  
  document.frm_servicecenter.submit();  
}

function cmdStopAbsence()
{
  document.frm_servicecenter.command_absence.value="<%=String.valueOf(Command.STOP)%>";  
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";  
  document.frm_servicecenter.submit();  
}

function cmdStopLeave()
{
  document.frm_servicecenter.command_leave.value="<%=String.valueOf(Command.STOP)%>"; 
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";  
  document.frm_servicecenter.submit();  
}

function cmdStopPresence()
{
  document.frm_servicecenter.command_presence.value="<%=String.valueOf(Command.STOP)%>"; 
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";  
  document.frm_servicecenter.submit();  
}

function cmdStopBackup()
{
  document.frm_servicecenter.command_backup.value="<%=String.valueOf(Command.STOP)%>"; 
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";  
  document.frm_servicecenter.submit();  
}

function cmdStopMessage()
{
  document.frm_servicecenter.command_message.value="<%=String.valueOf(Command.STOP) %>"; 
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";  
  document.frm_servicecenter.submit();  
}

function cmdStopDPCheck() 
{
  document.frm_servicecenter.command_dpcheck.value="<%=String.valueOf(Command.STOP) %>"; 
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";  
  document.frm_servicecenter.submit();  
}

  
function cmdStartLateness()
{
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";
  document.frm_servicecenter.command_lateness.value="<%= String.valueOf(Command.START) %>";	  
  document.frm_servicecenter.action="service_center.jsp";    
  document.frm_servicecenter.submit();  
}

function cmdStartAbsence()
{
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";
  document.frm_servicecenter.command_absence.value="<%= String.valueOf(Command.START) %>";	  
  document.frm_servicecenter.action="service_center.jsp";  
  document.frm_servicecenter.submit();
}

function cmdStartLeave()
{
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";
  document.frm_servicecenter.command_leave.value="<%= String.valueOf(Command.START) %>";   
  document.frm_servicecenter.action="service_center.jsp";  
   location.reload(true);
  document.frm_servicecenter.submit();
}

function cmdStartPresence()
{
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";
  document.frm_servicecenter.command_presence.value="<%= String.valueOf(Command.START) %>";   
  document.frm_servicecenter.action="service_center.jsp";  
  document.frm_servicecenter.submit();
}

function cmdStartBackup()
{
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";
  document.frm_servicecenter.command_backup.value="<%= String.valueOf(Command.START) %>";   
  document.frm_servicecenter.action="service_center.jsp";  
  document.frm_servicecenter.submit();
}

function cmdStartMessage()
{
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";
  document.frm_servicecenter.command_message.value="<%= String.valueOf(Command.START) %>";   
  document.frm_servicecenter.action="service_center.jsp";  
  document.frm_servicecenter.submit();
}

function cmdStartDPCheck()
{
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";
  document.frm_servicecenter.command_dpcheck.value="<%= String.valueOf(Command.START) %>";   
  document.frm_servicecenter.action="service_center.jsp";  
  location.reload(true);
  document.frm_servicecenter.submit();
}

// update command
  
function cmdUpdateAbsence(){
  document.frm_servicecenter.command_presence.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_absence.value="<%= String.valueOf(Command.SAVE) %>";
  document.frm_servicecenter.command_lateness.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_leave.value="<%= String.valueOf(Command.NONE) %>";    
  document.frm_servicecenter.command_backup.value="<%= String.valueOf(Command.NONE) %>";  
  document.frm_servicecenter.command_message.value="<%= String.valueOf(Command.NONE) %>";  
  document.frm_servicecenter.command_dpcheck.value="<%= String.valueOf(Command.NONE) %>"; 
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";
  document.frm_servicecenter.submit();
} 

function cmdUpdateLateness(){
  document.frm_servicecenter.command_presence.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_absence.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_lateness.value="<%= String.valueOf(Command.SAVE) %>";
  document.frm_servicecenter.command_leave.value="<%= String.valueOf(Command.NONE) %>";   
  document.frm_servicecenter.command_backup.value="<%= String.valueOf(Command.NONE) %>";     
  document.frm_servicecenter.command_message.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_dpcheck.value="<%= String.valueOf(Command.NONE) %>"; 
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";
  document.frm_servicecenter.submit();
} 

function cmdUpdateLeave(){
  document.frm_servicecenter.command_presence.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_absence.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_lateness.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_leave.value="<%= String.valueOf(Command.SAVE) %>";   
  document.frm_servicecenter.command_backup.value="<%= String.valueOf(Command.NONE) %>";     
  document.frm_servicecenter.command_message.value="<%= String.valueOf(Command.NONE) %>";  
  document.frm_servicecenter.command_dpcheck.value="<%= String.valueOf(Command.NONE) %>"; 
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";
  document.frm_servicecenter.submit();
} 

function cmdUpdatePresence(){
  document.frm_servicecenter.command_presence.value="<%= String.valueOf(Command.SAVE) %>";
  document.frm_servicecenter.command_absence.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_lateness.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_leave.value="<%= String.valueOf(Command.NONE) %>";   
  document.frm_servicecenter.command_backup.value="<%= String.valueOf(Command.NONE) %>";     
  document.frm_servicecenter.command_message.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_dpcheck.value="<%= String.valueOf(Command.NONE) %>"; 
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";
  document.frm_servicecenter.submit();
} 

function cmdUpdateBackup(){
  document.frm_servicecenter.command_presence.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_absence.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_lateness.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_leave.value="<%= String.valueOf(Command.NONE) %>";   
  document.frm_servicecenter.command_backup.value="<%= String.valueOf(Command.SAVE) %>";     
  document.frm_servicecenter.command_message.value="<%= String.valueOf(Command.NONE) %>";  
  document.frm_servicecenter.command_dpcheck.value="<%= String.valueOf(Command.NONE) %>"; 
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";
  document.frm_servicecenter.submit();
} 

function cmdUpdateMessage(){
  document.frm_servicecenter.command_presence.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_absence.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_lateness.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_leave.value="<%= String.valueOf(Command.NONE) %>";   
  document.frm_servicecenter.command_backup.value="<%= String.valueOf(Command.NONE) %>";     
  document.frm_servicecenter.command_message.value="<%= String.valueOf(Command.SAVE) %>";  
  document.frm_servicecenter.command_dpcheck.value="<%= String.valueOf(Command.NONE) %>";  
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";
  document.frm_servicecenter.submit();
} 

function cmdUpdateDPCheck(){
  document.frm_servicecenter.command_presence.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_absence.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_lateness.value="<%= String.valueOf(Command.NONE) %>";
  document.frm_servicecenter.command_leave.value="<%= String.valueOf(Command.NONE) %>";   
  document.frm_servicecenter.command_backup.value="<%= String.valueOf(Command.NONE) %>";     
  document.frm_servicecenter.command_message.value="<%= String.valueOf(Command.NONE) %>";  
  document.frm_servicecenter.command_dpcheck.value="<%= String.valueOf(Command.SAVE) %>";  
  document.frm_servicecenter.start.value="0";
  document.frm_servicecenter.maxLog.value="0";
  document.frm_servicecenter.submit();
} 


// -------------- script control line -------------------
function MM_swapImgRestore() 
{ //v3.0
	var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() 
{ //v3.0
	var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) 
{ //v4.0
	var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
	d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
	if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
	for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
	if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() 
{ //v3.0 
	var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
	if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/ctr_line/back_f2.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> </td>   
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
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Application 
                  Service Center<!-- #EndEditable --> </strong></font> </td>
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
                                    <form name="frm_servicecenter" method="post" action="">
                                        <input type="hidden" name="command" value="">
                                        <input type="hidden" name="command_lateness" value="<%=String.valueOf(iCommandLateness)%>">
                                        <input type="hidden" name="command_absence" value="<%=String.valueOf(iCommandAbsence)%>">
                                        <input type="hidden" name="command_leave" value="<%=String.valueOf(iCommandLeave)%>">
                                        <input type="hidden" name="command_presence" value="<%=String.valueOf(iCommandPresence)%>">
                                        <input type="hidden" name="command_backup" value="<%=String.valueOf(iCommandBackup)%>">										
                                        <input type="hidden" name="command_message" value="<%=String.valueOf(iCommandMessage)%>">	
                                        <input type="hidden" name="command_dpcheck" value="<%=String.valueOf(iCommandDPCheck)%>">					
                                        <input type="hidden" name="hidden_service_absence_id" value="<%=String.valueOf(oidServiceAbsence)%>">
                                        <input type="hidden" name="hidden_service_lateness_id" value="<%=String.valueOf(oidServiceLateness)%>">
                                        <input type="hidden" name="hidden_service_leave_id" value="<%=String.valueOf(oidServiceLeave)%>">																				
                                        <input type="hidden" name="hidden_service_presence_id" value="<%=String.valueOf(oidServicePresence)%>">																														
                                        <input type="hidden" name="hidden_service_backup_id" value="<%=String.valueOf(oidServiceBackup)%>">																																								
                                        <input type="hidden" name="hidden_service_message_id" value="<%=String.valueOf(oidServiceMessage)%>">		
                                        <input type="hidden" name="hidden_service_dpcheck_id" value="<%=String.valueOf(oidServiceDPCheck)%>">					
                                        <input type="hidden" name="log_command" value="0">
                                        <input type="hidden" name="hidden_back_up_conf_id" value="<%//=oidBackUpService%>">										
                                        <input type="hidden" name="start" value="<%//=start%>">
                                        <input type="hidden" name="maxLog" value="<%//=maxLog%>">
                                      <table width="100%" border="0" cellpadding="2" cellspacing="2">
                                        <tr> 
                                          <td width="48%" valign="top"> 
                                                <%if(false){%>
                                              <!-- PRESENCE ANALYZER SERVICE SECTION -->                                          
                                              <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center" bgcolor="#99FFCC">
                                              <tr> 
                                                <td width="65%"><b>. : : PRESENCE ANALYZER SERVICE : : .</b></td>
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">This 
                                                  process is used to prepare employee's 
                                                  attendance data for the next 
                                                  process i.e.: Absence Service,</td>
                                                <td align="left"><b></b></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%"> 
                                                  Lateness Service and Leave Stock 
                                                  Service. </td>
                                                  <%
                                                  Date dtPresence=null;
                                                  try
                                                  {
                                                        dtPresence = serviceConfPresence.getStartTime();  
                                                       // System.out.println("Start Timenya"+dtPresence);
                                                        if(dtPresence==null)
                                                        {
                                                                dtPresence = new Date();
                                                        }
                                                  }
                                                  catch(Exception e)
                                                  {
                                                        dtPresence = new Date();
                                                  }

                                                        PresenceAnalyser servicePresence = new PresenceAnalyser();
                                                        switch (iCommandPresence)
                                                        {
                                                                case  Command.START :
                                                                        try
                                                                        {
                                                                                servicePresence.startService();  //menghidupkan service presence analyser
                                                                        }
                                                                        catch(Exception e)
                                                                        {
                                                                                System.out.println("exc when servicePresence.startService() : " + e.toString());
                                                                        }
                                                                        break;

                                                                case  Command.STOP :
                                                                        try
                                                                        {
                                                                                servicePresence.stopService();  //mematikan service
                                                                        }
                                                                        catch(Exception e)
                                                                        {
                                                                                System.out.println("exc when servicePresence.stopService : " + e.toString());
                                                                        }
                                                                        break;
                                                        }	

                                                        boolean servicePresenceRunning = servicePresence.getStatus();

                                                        String stopStsPresence="";
                                                        String startStsPresence="";
                                                        if(servicePresenceRunning)
                                                        { 					
                                                                startStsPresence="disabled=\"true\"";
                                                                stopStsPresence="";
                                                        } 
                                                        else
                                                        {
                                                                startStsPresence="";
                                                                stopStsPresence="disabled=\"true\"";
                                                        }
                                                        %>
                                                <td align="left"><b><u>Presence 
                                                  Analyzer Service Configuration
                                                  </u></b></td>
                                              </tr>
                                              <tr>
                                                <td align="left" width="65%">To 
                                                  run this service, follow steps 
                                                  below :</td>
                                                <td align="left">-&nbsp;Start 
                                                  Time&nbsp;: 
                                                  <%=ControlDate.drawTime(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_START_TIME], dtPresence, "formElemen")%></td>
												  
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">&nbsp;&nbsp;- 
                                                  Click <font color="#0000FF">start</font> 
                                                  button to start presence analyser 
                                                  service process.</td>
                                                <td align="left">-&nbsp;Interval&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: 
                                                  <input type="text" name="<%=FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_PERIODE]%>" value="<%=serviceConfPresence.getPeriode()%>" class="formElemen" size="10">
                                                  <input type="hidden" name="<%=FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_SERVICE_TYPE]%>" value="<%=PstServiceConfiguration.SERVICE_TYPE_PRESENCE%>">
                                                  <i>(minutes)</i></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">&nbsp;&nbsp;- 
                                                  Click <font color="#0000FF">stop</font> 
                                                  button to stop presence analyser 
                                                  service process.</td>
                                                <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  <%if(privStart){%>
                                                  <input type="button" name="btnSavePresence" value="   Save   " onClick="javascript:cmdUpdatePresence()" class="formElemen" <%=startStsPresence%>>
                                                  <%}%>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">Presence 
                                                  analyser service status is&nbsp;&nbsp; 
                                                  <% 												  													
                                                    if(servicePresenceRunning)
                                                    {
                                                    %>
                                                  <font color="#009900">Running...</font> 
                                                  <%
                                                    }
                                                    else
                                                    {
                                                    %>
                                                  <font color="#FF0000">Stopped</font> 
                                                  <%
                                                    }
                                                    %>
                                                </td>
                                                <td align="left">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="65%"> 
                                                  <%if(privStart){%>
                                                  <input type="button" name="Button4" value="  Start  " onClick="javascript:cmdStartPresence()" class="formElemen" <%=startStsPresence%>>
                                                  <input type="button" name="Submit24" value="  Stop  " onClick="javascript:cmdStopPresence()" class="formElemen" <%=stopStsPresence%>>
                                                  <%}%>
                                                </td>
                                                <td>&nbsp; </td>
                                              </tr>
                                            </table>	
                                          
                                            <br>                                                
                                            <!-- END PRESENCE ANALYZER SERVICE SECTION -->
                                            
                                            <!-- ABSENCE SERVICE SECTION -->                                                
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center" bgcolor="#FFFFFF">
                                              <tr> 
                                                <td width="65%"><b>. : : ABSENCE 
                                                  SERVICE : : .</b></td>
                                                <td width="35%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="65%">This process is 
                                                  used to analyze employee's absence 
                                                  and update their status to be 
                                                  &quot;ABSENCE&quot;</td>
                                                <td width="35%"><b></b></td>
                                              </tr>
                                              <tr>
                                                <td width="65%">To run this service, 
                                                  follow steps below :</td>
                                                <td width="35%"><b><u>Absence 
                                                  Service Configuration</u></b></td>
                                              </tr>
                                              <tr> 
                                                <td width="65%">&nbsp;&nbsp;- 
                                                  Click <font color="#0000FF">start</font> 
                                                  button to start absence service 
                                                  process.</td>
                                                <%
                                                  Date dt=null;
                                                  try
                                                  {
                                                        dt = serviceConfAbsence.getStartTime();  
                                                        if(dt==null)
                                                        {
                                                                dt = new Date();
                                                        }
                                                  }
                                                  catch(Exception e)
                                                  {
                                                        dt = new Date();
                                                  }
                                                  %>
                                                <td width="35%">-&nbsp;Start Time&nbsp;: 
                                                  <%=ControlDate.drawTime(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_START_TIME], dt, "formElemen")%></td>
                                              </tr>
                                              <tr> 
                                                <td width="65%">&nbsp;&nbsp;- 
                                                  Click <font color="#0000FF">stop</font> 
                                                  button to stop absence service 
                                                  process.</td>
                                                <td width="35%">-&nbsp;Interval&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: 
                                                  <input type="text" name="<%=FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_PERIODE]%>" value="<%=serviceConfAbsence.getPeriode()%>" class="formElemen" size="10">
                                                  <input type="hidden" name="<%=FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_SERVICE_TYPE]%>" value="<%=PstServiceConfiguration.SERVICE_TYPE_ABSENCE%>">
                                                  <i>(minutes)</i></td>
                                              </tr>
                                              <tr> 
                                                <td width="65%">Absence service 
                                                  status is&nbsp; 
                                                  <% 
                                                      Date presenceDate = new Date(toDay.getYear(), toDay.getMonth(), toDay.getDate()-1);
                                                      AbsenceAnalyser absenceAnalyser = new AbsenceAnalyser();

                                                      switch (iCommandAbsence)    
                                                      {
                                                            case  Command.START :
                                                                      if(presenceDate.before(new Date()))
                                                                      {
                                                                            System.out.println("\tStart absence service");														  
                                                                            absenceAnalyser.setDate(presenceDate);
                                                                            absenceAnalyser.startService();  
                                                                      }
                                                                      break;

                                                            case  Command.STOP :
                                                                      //System.out.println("\tStop absence service");													
                                                                      Date dtx = new Date();
                                                                      dtx.setDate(dtx.getDate()-1);
                                                                      absenceAnalyser.setDate(dtx);
                                                                      absenceAnalyser.stopService();  
                                                                      break;
                                                      }	

                                                      boolean AbsenceServiceRunning = absenceAnalyser.getStatus();

                                                      if(AbsenceServiceRunning)
                                                      {
                                                      %>
                                                  <font color="#009900">Running 
                                                  ...</font> 
                                                  <%
                                                  }
                                                  else
                                                  {
                                                  %>
                                                  <font color="#FF0000">Stopped</font> 
                                                  <%
                                                  }
                                                  %>
                                                  <% 
                                                    String stopStsAbsence="";
                                                    String startStsAbsence="";
                                                    if(AbsenceServiceRunning)
                                                    { 					
                                                        startStsAbsence="disabled=\"true\"";
                                                        stopStsAbsence="";
                                                    }
                                                    else
                                                    {
                                                            startStsAbsence="";
                                                            stopStsAbsence="disabled=\"true\"";
                                                    }
                                                    %>
                                                </td>
                                                <td width="35%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  <%if(privStart){%>
                                                  <input type="button" name="btnSaveAbsence" value="   Save   " onClick="javascript:cmdUpdateAbsence()" class="formElemen" <%=startStsAbsence%>>
                                                  <%}%>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="65%"> 
                                                  <%if(privStart){%>
                                                  <input type="button" name="Button3" value="  Start  " onClick="javascript:cmdStartAbsence()" class="formElemen" <%=startStsAbsence%>>
                                                  <input type="button" name="Submit23" value="  Stop  " onClick="javascript:cmdStopAbsence()" class="formElemen" <%=stopStsAbsence%>>
                                                  <%}%>
                                                </td>
                                                <td width="35%">&nbsp; </td>
                                              </tr>
                                            </table>											
                                            <br>
                                            <!-- END ABSENCE SERVICE SECTION -->

                                            <!-- LATENESS SERVICE SECTION -->
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center" bgcolor="#F5F9C8">
                                              <tr> 
                                                <td width="65%"> <b>. : : LATENESS 
                                                  SERVICE : : .</b></td>
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="65%">This process is 
                                                  used to analyze employee's lateness 
                                                  and update their status to be 
                                                  &quot;LATE&quot;. </td>
                                                <td><b></b></td>
                                              </tr>
                                              <tr>
                                                <td width="65%">To run this service, 
                                                  follow steps below :</td>
                                                <td><b><u>Lateness Service Configuration</u></b></td>
                                              </tr>
                                              <tr> 
                                                <td width="65%">&nbsp;&nbsp;- 
                                                  Click <font color="#0000FF">start</font> 
                                                  button to start lateness service 
                                                  process. </td>
                                                <%
                                                  Date dtLateness=null;
                                                  try
                                                  {
                                                        dtLateness = serviceConfLateness.getStartTime();  
                                                        if(dtLateness==null)
                                                        {
                                                                dtLateness = new Date();
                                                        }
                                                  }
                                                  catch(Exception e)
                                                  {
                                                        dtLateness = new Date();
                                                  }
                                                  %>
                                                <td>-&nbsp;Start Time&nbsp;: <%=ControlDate.drawTime(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_START_TIME], dtLateness, "formElemen")%></td>
                                              </tr>
                                              <tr> 
                                                <td width="65%">&nbsp;&nbsp;- 
                                                  Click <font color="#0000FF">stop</font> 
                                                  button to stop lateness service 
                                                  process. </td>
                                                <td>-&nbsp;Interval&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: 
                                                  <input type="text" name="<%=FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_PERIODE]%>" value="<%=serviceConfLateness.getPeriode()%>" class="formElemen" size="10">
                                                  <input type="hidden" name="<%=FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_SERVICE_TYPE]%>" value="<%=PstServiceConfiguration.SERVICE_TYPE_LATENESS%>">
                                                  <i>(minutes)</i></td>
                                              </tr>
                                              <tr> 
                                                <td width="65%">Lateness service 
                                                  status is&nbsp;&nbsp; 
                                                  <% 
                                                  Date date = new Date(toDay.getYear(), toDay.getMonth(), toDay.getDate()-1);
                                                  LatenessAnalyser serviceAn = new LatenessAnalyser();
                                                  switch (iCommandLateness)
                                                  {
                                                        case  Command.START :
                                                                if(date.before(new Date()))
                                                                {
                                                                        //System.out.println("\tStart lateness service");														
                                                                        serviceAn.setDate(date);
                                                                        serviceAn.startService();  //menghidupkan service mengubah sts not aktif menjadi aktif
                                                                }														
                                                                break;

                                                        case  Command.STOP :
                                                                //System.out.println("\tStop lateness service");													
                                                                Date dtx = new Date();
                                                                dtx.setDate(dtx.getDate()-1);
                                                                serviceAn.setDate(dtx);
                                                                serviceAn.stopService();  //mematikan service														
                                                                break;
                                                  }	

                                                  boolean latenessServiceRunning = serviceAn.getStatus();

                                                  if(latenessServiceRunning)
                                                  {
                                                  %>
                                                  <font color="#009900">Running...</font> 
                                                  <%
                                                  }
                                                  else
                                                  {
                                                  %>
                                                  <font color="#FF0000">Stopped</font> 
                                                  <%
                                                  }
                                                  %>
                                                  <% 
                                                    String stopStsLateness  = "";
                                                    String startStsLateness = "";
                                                    if(latenessServiceRunning)
                                                    { 					
                                                       startStsLateness ="disabled=\"true\"";
                                                       stopStsLateness ="";
                                                    }
                                                    else
                                                    {
                                                       startStsLateness ="";
                                                       stopStsLateness ="disabled=\"true\"";
                                                    }
                                                %>
                                                </td>
                                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  
                                                  <%if(privStart){%>
                                                    <input type="button" name="btnSaveLateness" value="   Save   " onClick="javascript:cmdUpdateLateness()" class="formElemen" <%=startStsLateness%>>
                                                  <%}%>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="65%"> 
                                                  <%if(privStart){%>
                                                      <input type="button" name="Button2" value="  Start  " onClick="javascript:cmdStartLateness()" class="formElemen" <%=startStsLateness%>>
                                                      <input type="button" name="Submit22" value="  Stop  " onClick="javascript:cmdStopLateness()" class="formElemen" <%=stopStsLateness%>>
                                                  <%}%>
                                                </td>
                                                <td>&nbsp; </td>
                                              </tr>
                                            </table>	
                                            <%}%>
                                            <br>
                                             <!-- END LATENESS SERVICE SECTION -->

                                             <!-- LEAVE STOCk SERVICE SECTION -->
                                             
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center" bgcolor="#EDCA05">
                                              <tr> 
                                                <td width="65%"><b>. : : LEAVE 
                                                  STOCK SERVICE : : .</b></td>
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">This process is used to : ALs Entitle and DPs                                                                                                    
                                                  </td>
                                                <td align="left"><b></b></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">To 
                                                  run this service, follow steps 
                                                  below :</td>
                                                <td align="left"><b><u>Leave Stock 
                                                  Service Configuration</u></b></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">&nbsp;&nbsp;- 
                                                  Click <font color="#0000FF">start</font> 
                                                  button to start leave stock 
                                                  service process.</td>
                                                <% 
                                                 
                                                  Date dtLeave=null;
                                                  try
                                                  {
                                                        dtLeave = serviceConfLeave.getStartTime();  
                                                        if(dtLeave==null)
                                                        {
                                                                dtLeave = new Date();
                                                        }
                                                  }
                                                  catch(Exception e)
                                                  {
                                                        dtLeave = new Date();
                                                  }
                                                 
                                                  %>
                                                  
                                                <td align="left">-&nbsp;Start 
                                                  Time&nbsp;: <%=ControlDate.drawTime(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_START_TIME], dtLeave, "formElemen")%></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">&nbsp;&nbsp;- 
                                                  Click <font color="#0000FF">stop</font> 
                                                  button to stop leave stock service 
                                                  process.</td>
                                                <td align="left">-&nbsp;Interval&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: 
                                                  <input type="text" name="<%=FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_PERIODE]%>" value="<%=serviceConfLeave.getPeriode()%>" class="formElemen" size="10">
                                                  <input type="hidden" name="<%=FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_SERVICE_TYPE]%>" value="<%=PstServiceConfiguration.SERVICE_TYPE_LEAVE%>">
                                                  <i>(minutes)</i></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">Leave 
                                                  service status is&nbsp;&nbsp; 
                                                  <% 
                                                  													
                                                ServiceDpStock serviceDpStock = new ServiceDpStock();
                                                // ServiceAlStock serviceAlStock = new ServiceAlStock();
                                                ServiceLLStock serviceLLStock = new ServiceLLStock();
                                                switch (iCommandLeave)
                                                {
                                                        case  Command.START :
                                                                try
                                                                {
                                                                        serviceDpStock.setEmpLogin(userIsLogin.toLowerCase());
                                                                        serviceDpStock.setDate(selectedDateStart);
                                                                        serviceDpStock.startService();  //menghidupkan service mengubah sts not aktif menjadi aktif
                                                                }
                                                                catch(Exception e)
                                                                {
                                                                        System.out.println("exc when serviceDpStock.startService() : " + e.toString());
                                                                }

                                                                try
                                                                {	
                                                                        serviceAlStock.setDate(selectedDateStart);
                                                                        serviceAlStock.startService(); // insert al tiap employee tiap bulan
                                                                }
                                                                catch(Exception e)
                                                                {
                                                                        System.out.println("exc when serviceAlStock.startService() : " + e.toString());
                                                                }																

                                                                try
                                                                {				
                                                                        serviceLLStock.setDate(selectedDateStart);											
                                                                        serviceLLStock.startService(); // insert ll setiap kelipatan 5 tahun 																
                                                                }
                                                                catch(Exception e)
                                                                {
                                                                        System.out.println("exc when serviceLLStock.startService() : " + e.toString());
                                                                }																
															
                                                                break;

                                                        case  Command.STOP :
                                                                try
                                                                {
                                                                     serviceDpStock.stopService();  //mematikan service
                                                                }
                                                                catch(Exception e)
                                                                {
                                                                        System.out.println("exc when serviceDpStock.stopService : " + e.toString());
                                                                }

                                                                try
                                                                {																																													
                                                                        serviceAlStock.stopService(); // mematikan service
                                                                }
                                                                catch(Exception e)
                                                                {
                                                                        System.out.println("exc when serviceAlStock.stopService() : " + e.toString());
                                                                }																

                                                                try
                                                                {																																													
                                                                       serviceLLStock.stopService(); // mematikan service
                                                                }
                                                                catch(Exception e)
                                                                {
                                                                        System.out.println("exc when serviceLLStock.stopService() : " + e.toString());
                                                                }																

                                                                break;
                                                    }	

                                                    boolean dpStockServiceRunning = serviceDpStock.getStatus();  
                                                    boolean alStockServiceRunning = serviceAlStock.getStatus();
                                                    boolean llStockServiceRunning = serviceLLStock.getStatus();																												

                                                    if(dpStockServiceRunning && alStockServiceRunning && llStockServiceRunning)
                                                    {
                                                    %>
                                                  <font color="#009900">Running...</font> 
                                                  <%
                                                    }
                                                    else
                                                    {
                                                    %>
                                                  <font color="#FF0000">Stopped</font>
                                                  <%
                                                   }
                                                    %>
                                                    <%                                                  
                                                     
                                                    String stopStsLeave="";
                                                    String startStsLeave="";
                                                    if(dpStockServiceRunning && alStockServiceRunning && llStockServiceRunning)
                                                    { 					
                                                            startStsLeave="disabled=\"true\"";
                                                            stopStsLeave="";
                                                    } 
                                                    else
                                                    {
                                                            startStsLeave="";
                                                            stopStsLeave="disabled=\"true\"";
                                                    }

                                                    %>
                                                    
                                                </td>
                                                <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                  <%if(privStart){%>
                                                  <input type="button" name="btnSaveLeave" value="   Save   " onClick="javascript:cmdUpdateLeave()" class="formElemen" <%=startStsLeave%>>
                                                  <%}%>
                                                </td>
                                              </tr>
											 <tr>
											 	<td>Start Date: <%=ControlDate.drawDateWithStyle("check_date_start",mDateFrom, 0, installInterval, "formElemen", "")%></td>
											 
											 </tr>
                                              <tr> 
											  
                                                <td width="65%"> 
                                                  <%if(privStart){%>
                                                      <input type="button" name="Button4" value="  Start  " onClick="javascript:cmdStartLeave()" class="formElemen" <%=startStsLeave%>>
                                                      <input type="button" name="Submit24" value="  Stop  " onClick="javascript:cmdStopLeave()" class="formElemen" <%=stopStsLeave%>>
                                                  <%}%>
                                                </td>
                                                <td>&nbsp; </td>
                                              </tr>
                                            </table>
                                            <br>
                                            <!-- END LEAVE STOCK SERVICE SECTION -->
                                                 
                                            <!-- BACKUP DATABASE SERVICE SECTION -->
                                            <!--
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center" bgcolor="#CCCCFF">
                                              <tr> 
                                                <td width="65%"><b>. : : BACKUP 
                                                  DATABASE SERVICE : : .</b></td>
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">This 
                                                  process will backup database 
                                                  based on service configuration 
                                                  on the right side.</td>
                                                <td align="left"><b></b></td>
                                              </tr>
                                              <tr>
                                                <td align="left" width="65%">To 
                                                  run this service, follow steps 
                                                  below :</td>
                                                <td align="left"><b><u>Backup 
                                                  Database Service Configuration</u></b></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">&nbsp;&nbsp;- 
                                                  Click <font color="#0000FF">start</font> 
                                                  button to start backup database 
                                                  service process.</td>
                                                <%
                                                  /*Date dtBackup=null;
                                                  try
                                                  {
                                                        dtBackup = serviceConfBackup.getStartTime();  
                                                        if(dtBackup==null)
                                                        {
                                                                dtBackup = new Date();
                                                        }
                                                  }
                                                  catch(Exception e)
                                                  {
                                                        dtBackup = new Date();
                                                  }*/
                                                  %>
                                                <td align="left">-&nbsp;Start 
                                                  Time&nbsp;: <%//=ControlDate.drawTime(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_START_TIME], dtBackup, "formElemen")%></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">&nbsp;&nbsp;- 
                                                  Click <font color="#0000FF">stop</font> 
                                                  button to stop backup database 
                                                  service process.</td>
                                                <td align="left">-&nbsp;Interval&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: 
                                                  <input type="text" name="<%//=FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_PERIODE]%>" value="<%//=serviceConfBackup.getPeriode()%>" class="formElemen" size="10">
                                                  <input type="hidden" name="<%//=FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_SERVICE_TYPE]%>" value="<%//=PstServiceConfiguration.SERVICE_TYPE_BACKUPDB%>">
                                                  <i>(minutes)</i></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">Backup 
                                                  database service status is&nbsp;&nbsp; 
                                                  <%
                                                  /*
                                                    String stCommandScript = "d:\\tomcat\\webapps\\harisma_proj\\backup.bat";    																											
                                                    BackupDatabaseProcess objBackupDatabaseProcess = new BackupDatabaseProcess(stCommandScript);
                                                    switch (iCommandBackup)
                                                    {
                                                            case  Command.START :
                                                                    try
                                                                    {
                                                                            objBackupDatabaseProcess.startService();  //menghidupkan service presence analyser
                                                                    }
                                                                    catch(Exception e)
                                                                    {
                                                                            System.out.println("exc when objBackupDatabaseProcess.startService() : " + e.toString());
                                                                    }
                                                                    break;

                                                            case  Command.STOP :
                                                                    try
                                                                    {
                                                                            objBackupDatabaseProcess.stopService();  //mematikan service
                                                                    }
                                                                    catch(Exception e)
                                                                    {
                                                                            System.out.println("exc when objBackupDatabaseProcess.stopService : " + e.toString());
                                                                    }
                                                                    break;
                                                    }	
													
                                                    boolean serviceBackupDatabaseRunning = objBackupDatabaseProcess.getStatus();  													
                                                    if(serviceBackupDatabaseRunning)
                                                    {*/
                                                    %>
                                                  <font color="#009900">Running...</font> 
                                                    <%
                                                    /*}
                                                    else
                                                    {*/
                                                    %>
                                                  <font color="#FF0000">Stopped</font> 
                                                  <%
                                                    //}
                                                    %>
                                                  <% 
                                                  /*
                                                    String stopStsBackup="";
                                                    String startStsBackup="";
                                                    if(serviceBackupDatabaseRunning)
                                                    { 					
                                                            startStsBackup="disabled=\"true\"";
                                                            stopStsBackup="";
                                                    } 
                                                    else
                                                    {
                                                            startStsBackup="";
                                                            stopStsBackup="disabled=\"true\"";
                                                    }*/
                                                    %>
                                                </td>
                                                <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  <%//if(privStart){%>
                                                    <input type="button" name="btnSaveBackup" value="   Save   " onClick="javascript:cmdUpdateBackup()" class="formElemen" <%//=startStsBackup%>>
                                                  <%//}%>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="65%"> 
                                                  <%//if(privStart){%>
                                                      <input type="button" name="Button4" value="  Start  " onClick="javascript:cmdStartBackup()" class="formElemen" <%//=startStsBackup%>>
                                                      <input type="button" name="Submit24" value="  Stop  " onClick="javascript:cmdStopBackup()" class="formElemen" <%//=stopStsBackup%>>
                                                  <%//}%>
                                                </td>
                                                <td>&nbsp; </td>
                                              </tr>
                                            </table>	
                                            <br> -->
                                            <!-- END BACKUP DATABASE SERVICE SECTION -->

                                            <!-- MESSAGE SERVICE SECTION -->
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center" bgcolor="#99FF33">
                                              <tr> 
                                                <td width="65%"><b>. : : MESSAGE SERVICE : : .</b></td>
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">This 
                                                  process will send message to machine 
                                                  based on service configuration 
                                                  on the right side.</td>
                                                <td align="left"><b></b></td>
                                              </tr>
                                              <tr>
                                                <td align="left" width="65%">To 
                                                  run this service, follow steps 
                                                  below :</td>
                                                <td align="left"><b><u>Send Message Service Configuration</u></b></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">&nbsp;&nbsp;- 
                                                  Click <font color="#0000FF">start</font> 
                                                  button to start send message
                                                  service process.</td>
                                                <%
                                                      Date dtMessage=null;
                                                      try
                                                      {
                                                            dtMessage = serviceConfMessage.getStartTime();  
                                                            if(dtMessage==null)
                                                            {
                                                                    dtMessage = new Date();
                                                            }
                                                      }
                                                      catch(Exception e)
                                                      {
                                                            dtMessage = new Date();
                                                      }
                                                      %>
                                                <td align="left">-&nbsp;Start 
                                                  Time&nbsp;: <%=ControlDate.drawTime(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_START_TIME], dtMessage, "formElemen")%></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">&nbsp;&nbsp;- 
                                                  Click <font color="#0000FF">stop</font> 
                                                  button to stop send message
                                                  service process.</td>
                                                <td align="left">-&nbsp;Interval&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: 
                                                  <input type="text" name="<%=String.valueOf(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_PERIODE])%>" value="<%=String.valueOf(serviceConfMessage.getPeriode())%>" class="formElemen" size="10">
                                                  <input type="hidden" name="<%=String.valueOf(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_SERVICE_TYPE])%>" value="<%=String.valueOf(PstServiceConfiguration.SERVICE_TYPE_MESSAGE)%>">
                                                  <i>(minutes)</i></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">Send Message service status is&nbsp;&nbsp; 
                                                  <% 										 																											
                                                    MessageToMachine messageToMachine = new MessageToMachine();
                                                    switch (iCommandMessage)
                                                    {
                                                            case  Command.START :
                                                                    try
                                                                    {
                                                                            messageToMachine.startService();  //menghidupkan service presence analyser
                                                                    }
                                                                    catch(Exception e)
                                                                    {
                                                                            System.out.println("exc when messageToMachine.startService() : " + e.toString());
                                                                    }
                                                                    break;

                                                            case  Command.STOP :
                                                                    try
                                                                    {
                                                                            messageToMachine.stopService();  //mematikan service
                                                                    }
                                                                    catch(Exception e)
                                                                    {
                                                                            System.out.println("exc when messageToMachine.stopService : " + e.toString());
                                                                    }
                                                                    break;
                                                    }	

                                                    boolean serviceSendMessageRunning = messageToMachine.getStatus();  													
                                                    if(serviceSendMessageRunning)
                                                    {
                                                    %>
                                                    <font color="#009900">Running...</font> 
                                                    <%
                                                    }
                                                    else
                                                    {
                                                    %>
                                                    <font color="#FF0000">Stopped</font> 
                                                    <%
                                                    }
                                                    %>
                                                    <% 
                                                    String stopStsMessage="";
                                                    String startStsMessage="";
                                                    if(serviceSendMessageRunning)
                                                    { 					
                                                            startStsMessage="disabled=\"true\"";
                                                            stopStsMessage="";
                                                    } 
                                                    else
                                                    {
                                                            startStsMessage="";
                                                            stopStsMessage="disabled=\"true\"";
                                                    }
                                                    %>
                                                </td>
                                                <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  <%if(privStart){%>
                                                    <input type="button" name="btnSaveBackup" value="   Save   " onClick="javascript:cmdUpdateMessage()" class="formElemen" <%=startStsMessage%>>
                                                  <%}%>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="65%"> 
                                                  <%if(privStart){%>
                                                      <input type="button" name="Button4" value="  Start  " onClick="javascript:cmdStartMessage()" class="formElemen" <%=startStsMessage%>>
                                                      <input type="button" name="Submit24" value="  Stop  " onClick="javascript:cmdStopMessage()" class="formElemen" <%=stopStsMessage%>>
                                                  <%}%>
                                                </td>
                                                <td>&nbsp; </td>
                                              </tr>
                                            </table>	
                                            <br>
                                            <!-- END MESSAGE SERVICE SECTION -->
                                              
                                            <!-- DP SERVICE SECTION --> 
                                            <!--
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" align="center" bgcolor="#F6CCF6">
                                              <tr> 
                                                <td width="65%"><b>. : : DP CHECKING SERVICE : : .</b></td>
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">This 
                                                  process will check for DP got by employee 
                                                  based on service configuration
                                                  on the right side.</td>
                                                <td align="left"><b></b></td>
                                              </tr>
                                              <tr>
                                                <td align="left" width="65%">To 
                                                  run this service, follow steps 
                                                  below :</td>
                                                <td align="left"><b><u>DP Checking Service Configuration</u></b></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">&nbsp;&nbsp;- 
                                                  Click <font color="#0000FF">start</font> 
                                                  button to start DP checking
                                                  service process.</td>
                                                  <%
                                                  /*
                                                      Date dtDPCheck = null;
                                                      try
                                                      {
                                                            dtDPCheck = serviceConfDPCheck.getStartTime();  
                                                            if(dtDPCheck==null)
                                                            {
                                                                    dtDPCheck = new Date();
                                                            }
                                                      }
                                                      catch(Exception e)
                                                      {
                                                            dtDPCheck = new Date();
                                                      }
                                                    */
                                                  %>
                                                <td align="left">-&nbsp;Start 
                                                  Time&nbsp;: <%//=ControlDate.drawTime(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_START_TIME], dtDPCheck, "formElemen")%></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">&nbsp;&nbsp;- 
                                                  Click <font color="#0000FF">stop</font> 
                                                  button to stop DP checking
                                                  service process.</td>
                                                <td align="left">-&nbsp;Interval&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: 
                                                  <input type="text" name="<%//=String.valueOf(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_PERIODE])%>" value="<%//=String.valueOf(serviceConfDPCheck.getPeriode())%>" class="formElemen" size="10">
                                                  <input type="hidden" name="<%//=String.valueOf(FrmServiceConfiguration.fieldNames[FrmServiceConfiguration.FRM_FIELD_SERVICE_TYPE])%>" value="<%//=String.valueOf(PstServiceConfiguration.SERVICE_TYPE_DAY_PAYMENT)%>">
                                                  <i>(minutes)</i></td>
                                              </tr>
                                              <tr> 
                                                <td align="left" width="65%">DP checking service status is&nbsp;&nbsp;                                                   
                                                    <%                                                    
                                                     /* 
                                                     DPCheckerService dpCheck = new DPCheckerService();
                                                        
                                                        switch(iCommandDPCheck) {
                                                            case Command.START :
                                                                try
                                                                {
                                                                        dpCheck.startService();  //menghidupkan service pengecekan DP
                                                                }
                                                                catch(Exception e)
                                                                {
                                                                        System.out.println("exc when dpCheck.startService() : " + e.toString());
                                                                }
                                                                
                                                                break;
                                                                
                                                            case Command.STOP :
                                                                try
                                                                {
                                                                        dpCheck.stopService();  //mematikan service pengecekan DP
                                                                }
                                                                catch(Exception e)
                                                                {
                                                                        System.out.println("exc when dpCheck.stopService() : " + e.toString());
                                                                }
                                                                
                                                                break;
                                                        }
                                                        
                                                        boolean serviceDPCheckRunning = dpCheck.getStatus();
                                                        
                                                        if(serviceDPCheckRunning) 
                                                        {
                                                    */
                                                    %>
                                                        <font color="#009900">Running...</font> 
                                                    <%
                                                       /* }
                                                        else
                                                        {*/
                                                    %>
                                                        <font color="#FF0000">Stopped</font> 
                                                    <%
                                                        //}
                                                    %>    
                                                        
                                                    <%                                                     
                                                    /*
                                                    String stopStsDP="";
                                                    String startStsDP="";
                                                    
                                                    if(serviceDPCheckRunning)
                                                    { 					
                                                            startStsDP="disabled=\"true\"";
                                                            stopStsDP="";
                                                    } 
                                                    else
                                                    {
                                                            startStsDP="";
                                                            stopStsDP="disabled=\"true\"";
                                                    }*/
                                                    %>
                                                </td>
                                                <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                                                  <%//if(privStart){%>
                                                    <input type="button" name="btnSaveDP" value="   Save   " onClick="javascript:cmdUpdateDPCheck()" class="formElemen" <%//=startStsDP%>>
                                                  <%//}%>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="65%"> 
                                                  <%//if(privStart){%>
                                                      <input type="button" name="Button4" value="  Start  " onClick="javascript:cmdStartDPCheck()" class="formElemen" <%//=startStsDP%>>
                                                      <input type="button" name="Submit24" value="  Stop  " onClick="javascript:cmdStopDPCheck()" class="formElemen" <%//=stopStsDP%>>
                                                  <%//}%>
                                                </td>
                                                <td>&nbsp; </td>
                                              </tr>
                                            </table>	-->	
                                            <!-- END DP SERVICE SECTION -->
                                            
                                          </td>
                                        </tr>
                                      </table>
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
  <!--<tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
                 <%//if(serviceAlStock.getStatus()){%>
  <!--      <script language="JavaScript">

//enter refresh time in "minutes:seconds" Minutes should range from 0 to inifinity. Seconds should range from 0 to 59
var limit="0:08"
if (document.images){
var parselimit=limit.split(":")
parselimit=parselimit[0]*60+parselimit[1]*1
}
function beginrefresh(){
if (!document.images)
return

if (parselimit==1)
window.location = window.location.href //agar tidak memunculkan pesan confirmasi
else{
parselimit-=1
//curmin=Math.floor(parselimit/60)
//cursec=parselimit%60
//if (curmin!=0)
//curtime=curmin+" minutes and "+cursec+" seconds left until page refresh!"

setTimeout("beginrefresh()",100)
}
}

window.onload=beginrefresh
//-->
<!--</script>--> 
            
            
<%if(false){%>
   <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
         
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
       <%}%>
       <%if(false){%>
        <script language="JavaScript">

//enter refresh time in "minutes:seconds" Minutes should range from 0 to inifinity. Seconds should range from 0 to 59
var limit="0:08"
if (document.images){
var parselimit=limit.split(":")
parselimit=parselimit[0]*60+parselimit[1]*1
}
function beginrefresh(){
if (!document.images)
return

if (parselimit==1)
window.location = window.location.href //agar tidak memunculkan pesan confirmasi
else{
parselimit-=1
//curmin=Math.floor(parselimit/60)
//cursec=parselimit%60
//if (curmin!=0)
//curtime=curmin+" minutes and "+cursec+" seconds left until page refresh!"

setTimeout("beginrefresh()",100)
}
}

window.onload=beginrefresh
//-->
</script>
    <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
