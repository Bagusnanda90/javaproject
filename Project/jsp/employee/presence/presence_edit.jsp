
<%@page import="com.dimata.harisma.utility.service.presence.PresenceAnalyser"%>
<%@page import="com.dimata.harisma.entity.search.SrcPresence"%>
<%@page import="com.dimata.harisma.session.attendance.SessPresence"%>
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
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_PRESENCE, AppObjInfo.OBJ_MANUAL_PRESENCE); %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_PRESENCE); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//cek tipe browser, browser detection
    String userAgentt = request.getHeader("User-Agentt");
    boolean isMSIEe = (userAgentt!=null && userAgentt.indexOf("MSIE") !=-1);  
%>

<!-- Jsp Block -->
<%
CtrlPresence ctrlPresence = new CtrlPresence(request);
int iCommand = FRMQueryString.requestCommand(request);
long oidPresence = FRMQueryString.requestLong(request, "presence_id");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int start = FRMQueryString.requestInt(request,"start");

int iErrCode = FRMMessage.ERR_NONE;
String errMsg = "";
String whereClause = "";
String orderClause = "";

///oidPresence = presence.getOID();
//update by satrya 2012-08-01
/*if(oidPresence !=0){
    presence.setOID(oidPresence);

}*/        
SrcPresence srcPresence =null;
Employee employee = new Employee();
long oidPosition = 0;
Position position = new Position();
long oidDepartment = 0;
Department department = new Department();
 boolean cekOt = false;
        try 
	{	 
		srcPresence = (SrcPresence)session.getValue(SessPresence.SESS_SRC_PRESENCE); 
		if (srcPresence == null) 
		{
			srcPresence = new SrcPresence();
		}
                //update by satrya 2012-10-25
               /* srcPresence.setEmpnumber(employee.getEmployeeNum()); 
                srcPresence.setFullname(employee.getFullName()); 
                srcPresence.setDatefrom(presence.getPresenceDatetime());
                srcPresence.setDateto(presence.getPresenceDatetime());*/
	}
	catch(Exception e){
		srcPresence = new SrcPresence();
	}
 String tmpSource="";
if(iCommand == Command.ADD){

    long oidEmployee = FRMQueryString.requestLong(request, "FRM_FIELD_EMPLOYEE_ID");
    long lDateSelected = FRMQueryString.requestLong(request, "lDateFrom");
   String   sourceReq = FRMQueryString.requestString(request, "source");
   if(sourceReq!=null && sourceReq.length()>0 && (sourceReq.equalsIgnoreCase("overtime")|| sourceReq.equalsIgnoreCase("attdReportDaily"))){ 
    privAdd = FRMQueryString.requestBoolean(request, "privAdd"); 
    privUpdate = FRMQueryString.requestBoolean(request, "privUpdate");
    // cekOt = true; 
   
    if(oidEmployee !=0 && lDateSelected!=0){
  try{
    employee = PstEmployee.fetchExc(oidEmployee);
    position = PstPosition.fetchExc(employee.getPositionId());
    department = PstDepartment.fetchExc(employee.getDepartmentId());
    // scheduleSymbol = PstEmpSchedule.getDailySchedule(new Date(lDateSelected), oidEmployee);
    
    srcPresence.setEmpId(oidEmployee);
    srcPresence.setEmpnumber(employee.getEmployeeNum());
    srcPresence.setFullname(employee.getFullName());
    srcPresence.setDepartment(""+employee.getDepartmentId()); 
    srcPresence.setSection(""+employee.getSectionId());
    srcPresence.setPosition(""+employee.getPositionId());
    

    srcPresence.setPositionName(position.getPosition()); 
    srcPresence.setsCommarcingDate(Formater.formatDate(employee.getCommencingDate(), "dd MMM yyyy") ); 
    srcPresence.setDepartmentName(department.getDepartment());
    srcPresence.setPresenceId(oidPresence);
    srcPresence.setFlagsts(true);
    srcPresence.setSource(sourceReq);
    Date datefrom = new Date((lDateSelected));
    datefrom.setHours(0);
    datefrom.setMinutes(0);
    datefrom.setSeconds(0);
    Date dateto = new Date((lDateSelected + 1000 * 60 * 60 * 24));
    dateto.setHours(23);
    dateto.setMinutes(59);
    dateto.setSeconds(59);
    srcPresence.setDatefrom(datefrom);
    srcPresence.setDateto(dateto);
    session.putValue(SessPresence.SESS_SRC_PRESENCE, srcPresence);
        }catch(Exception ex){
            System.out.println("Exception att_edit_dutty: " +ex);
        }     
    }
  }//cek jika dia di panggil oleh OT
    //update by satrya 2013-02-01
       else if(srcPresence!=null && (srcPresence.getEmpnumber()!=null && srcPresence.getEmpnumber().length()>0)){
        try{
            employee = PstEmployee.getEmployeeByNum(srcPresence.getEmpnumber());
            oidPosition = employee.getPositionId();
            position = PstPosition.fetchExc(oidPosition);
            oidDepartment = employee.getDepartmentId();
            department = PstDepartment.fetchExc(oidDepartment);
            }catch(Exception ex){
                employee = new Employee();  
            }
        }
}

ControlLine ctrLine = new ControlLine();

    if (iCommand==Command.CONFIRM){ 
        iErrCode = ctrlPresence.action(Command.EDIT , oidPresence, request,1,emplx.getFullName(), appUserIdSess,"");
        //cekOt = false; 
    } else {

        iErrCode = ctrlPresence.action(iCommand , oidPresence, request,1,emplx.getFullName(), appUserIdSess,""); 
    if(iCommand==Command.SAVE){
          cekOt = false; 
    }
 //untuk flag jika user memilih di add di report daily
   if(srcPresence!=null && srcPresence.getSource()!=null && srcPresence.getSource().length()>0){
     try 
	{	 
		srcPresence = (SrcPresence)session.getValue(SessPresence.SESS_SRC_PRESENCE); 
		if (srcPresence == null) 
		{
			srcPresence = new SrcPresence();
		}
	}
	catch(Exception e){
		srcPresence = new SrcPresence();
	}
         if(iCommand==Command.SAVE){
          cekOt = true; 
          tmpSource = srcPresence.getSource();
          srcPresence = new SrcPresence();
          
        }
    }
  
 }
errMsg = ctrlPresence.getMessage(); 
FrmPresence frmPresence = ctrlPresence.getForm();
Presence presence = ctrlPresence.getPresence();
if(!(errMsg!=null && errMsg.length()>0)){
    errMsg = CtrlPresence.resultText[CtrlPresence.LANGUAGE_FOREIGN][iErrCode];
}
if(((iCommand==Command.SAVE))) 
    //if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmPresence.errorSize()<1)) 
{
    //update by satrya 2013-07-03
    
    try{
       
    if(presence.getEmployeeId()!=0){
    	employee = PstEmployee.fetchExc(presence.getEmployeeId());
        //update by satrya 2013-02-01
   srcPresence = new SrcPresence();
   if(employee!=null && employee.getOID()!=0){
    srcPresence.setEmpId(employee.getOID());
    srcPresence.setEmpnumber(employee.getEmployeeNum());
    srcPresence.setFullname(employee.getFullName());
    srcPresence.setDepartment(""+employee.getDepartmentId()); 
    srcPresence.setSection(""+employee.getSectionId());
    srcPresence.setPosition(""+employee.getPositionId());
    
    srcPresence.setPositionName(position.getPosition()); 
    srcPresence.setsCommarcingDate(Formater.formatDate(employee.getCommencingDate(), "dd MMM yyyy") ); 
    
     }
    }else if(srcPresence.getEmpId()!=0){  
        employee = PstEmployee.fetchExc(srcPresence.getEmpId());
        
    }
      
     } catch(Exception exc){
        employee = new Employee();  
     }
     
        session.putValue(SessPresence.SESS_SRC_PRESENCE, srcPresence);
	if(cekOt==false){%>
       
	<jsp:forward page="presence_list.jsp"> 
	<jsp:param name="prev_command" value="<%=prevCommand%>" />
	<jsp:param name="command" value="<%=Command.BACK%>" />		
	<jsp:param name="start" value="<%=start%>" />
	<jsp:param name="presence_id" value="<%=presence.getOID()%>" />
	</jsp:forward>
	<%
      }
}

 long oidEmployeeX = presence.getEmployeeId();
if ((iCommand==Command.EDIT) || (iCommand==Command.ASK) || (iCommand==Command.CONFIRM)) 
    
{ 
    try{
       //update by satrya 2013-02-01
       if(oidEmployeeX!=0){
	employee = PstEmployee.fetchExc(oidEmployeeX);
       }else if(srcPresence!=null && (srcPresence.getEmpnumber()!=null && srcPresence.getEmpnumber().length()>0)){
            employee = PstEmployee.getEmployeeByNum(srcPresence.getEmpnumber());
       }
	oidPosition = employee.getPositionId();
	position = PstPosition.fetchExc(oidPosition);
	oidDepartment = employee.getDepartmentId();
	department = PstDepartment.fetchExc(oidDepartment);
        if(oidPresence!=0){
            presence = PstPresence.fetchExc(oidPresence);
        }
     } catch(Exception exc){
        employee = new Employee();  
     }
}


%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Manual Presence</title>
<script language="JavaScript">

<%if(cekOt) {%> 
                //self.opener.cmdRel(); 
                    <%if(iErrCode==CtrlPresence.RSLT_OK){%>
                        alert('Data Disimpan');
                        <%if(isMSIEe){%>
                         <%if(tmpSource!=null && tmpSource.length()>0 && tmpSource.equalsIgnoreCase("attdReportDaily")){%>
                         self.opener.cmdRefresh();
                          window.close();//for IE
                          <%}else{%>
                                window.close();//for IE
                         <%}%>
                        
                        <%}else{%>
                        <%if(tmpSource!=null && tmpSource.length()>0 && tmpSource.equalsIgnoreCase("attdReportDaily")){%>
                            self.opener.cmdRefresh();
                              window.close();
                           <%}else{%>
                               window.close();
                           <%}%>
                           
                        
                        <%}%>
                    <%}else{%>
                        //
                        <%if(ctrlPresence.getMessage()!=null && ctrlPresence.getMessage().length()>0){%>
                            alert(<%=ctrlPresence.getMessage()%>);
                        <%}else{%>
                             alert(<%=CtrlPresence.resultText[CtrlPresence.LANGUAGE_DEFAULT][iErrCode]%>); 
                        <%}%>
                    <%}%>
                <%}%>
	function cmdCancel(){
		document.frm_presence.command.value="<%=String.valueOf(Command.ADD)%>";
		document.frm_presence.action="presence_edit.jsp";
		document.frm_presence.submit();
	} 
	function cmdCancel(){
		document.frm_presence.command.value="<%=String.valueOf(Command.CANCEL)%>";
		document.frm_presence.action="presence_edit.jsp";
		document.frm_presence.submit();
	} 

	function cmdEdit(oid){ 
		document.frm_presence.command.value="<%=String.valueOf(Command.EDIT)%>";
		document.frm_presence.action="presence_edit.jsp";
		document.frm_presence.submit(); 
	} 
        
	function cmdSave(){
		document.frm_presence.command.value="<%=String.valueOf(Command.SAVE)%>"; 
		document.frm_presence.action="presence_edit.jsp";
		document.frm_presence.submit();
                
	}

	function cmdAsk(oid){
                 document.frm_presence.presence_id.value=oid;
		document.frm_presence.command.value="<%=String.valueOf(Command.ASK)%>"; 
		document.frm_presence.action="presence_edit.jsp";
		document.frm_presence.submit();
	} 

	function cmdConfirmDelete(oid){
                 document.frm_presence.presence_id.value=oid;
		document.frm_presence.command.value="<%=String.valueOf(Command.DELETE)%>";
		document.frm_presence.action="presence_edit.jsp"; 
		document.frm_presence.submit();
	}  

	function cmdBack(){
		document.frm_presence.command.value="<%=String.valueOf(Command.BACK)%>"; 
                ///document.frm_presence.command.value="<%//=String.valueOf(Command.FIRST)%>"; 
		document.frm_presence.action="presence_list.jsp";
		document.frm_presence.submit();
	}

	function cmdSearchEmp_old(){
            window.open("emppresencesearch.jsp?emp_number=" + document.frm_presence.EMP_NUMBER.value + "&emp_fullname=" + document.frm_presence.EMP_FULLNAME.value + "&emp_department=" + document.frm_presence.EMP_DEPARTMENT.value, null, "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
         
        function cmdSearchEmp(){
            window.open("<%=approot%>/employee/search/search_01.jsp?formName=frm_presence&empPathId=<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
        }

	function cmdClearSearchEmp() {
		document.frm_presence.EMP_FULLNAME.value = "";
		document.frm_presence.EMP_NUMBER.value = "";
		document.frm_presence.EMP_POSITION.value = "";
		document.frm_presence.EMP_COMMENCING_DATE.value = "";
		document.frm_presence.EMP_DEPARTMENT.value = "";
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
	<% if((iCommand == Command.ADD) || (iCommand == Command.SAVE && frmPresence.errorSize()>0) || (iCommand == Command.EDIT)){%>
		document.frm_presence.EMP_DEPARTMENT.style.visibility="";
	<% }%>
}

</SCRIPT>
<!-- #EndEditable -->
</head> 

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnCancelOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
    <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%> 
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
 <%}%>
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
                <td  style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frm_presence" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                       <input type="hidden" name="presence_id" value="<%=String.valueOf(oidPresence)%>">
                                      <input type="hidden" name="prev_command" value="<%=String.valueOf(prevCommand)%>">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                      <!-- update by satrya 2012-10-01 -->
                                       <input type="hidden" name="<%=FrmPresence.fieldNames[FrmPresence.FRM_FIELD_SCHEDULE_DATETIME]%>" value="<%=presence.getScheduleDatetime()%>">
                                        
                                       
                                       
                                     
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td width="2%">&nbsp;</td>
                                          <td width="94%">&nbsp;</td>
                                          <td width="2%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td width="94%"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                              <tr> 
                                                <td> 
                                                  <div align="center"><b><font size="3">MANUAL 
                                                    REGISTRATION OF PRESENCE</font></b></div>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                        <!-- update by satrya 2012-12-05 -->
                                                     <!-- <input type="hidden" name="<%//=FrmPresence.fieldNames[FrmPresence.FRM_FIELD_EMPLOYEE_ID]%>" value="<%//=String.valueOf(presence.getEmployeeId())%>" class="formElemen">-->
                                                      <%//=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_EMPLOYEE_ID)%> 
                                                      
                                       <%if(srcPresence.getEmpId()!=0 ||presence.getEmployeeId()!=0){%>
                                        <input type="hidden" name="<%=FrmPresence.fieldNames[FrmPresence.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=srcPresence.getEmpId()!=0 ? srcPresence.getEmpId() : presence.getEmployeeId()%>">
                                       <%}else{%>
                                       <input type="hidden" name="<%=FrmPresence.fieldNames[FrmPresence.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=employee.getOID()%>" size="35">
                                       <%}%>
                                       <input type="hidden" name="<%=FrmPresence.fieldNames[FrmPresence.FRM_FIELD_MANUAL_PRESENCE]%>" value="<%=presence.YES%>">
                                       
                                                      <td width="12%" nowrap> 
                                                        <div align="left">Name</div>
                                                      </td>
                                                      <td width="29%" nowrap> 
                                                        : 
                                                        <!-- update by satrya 2013-02-01-->
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%= employee.getFullName() %> 
                                                        <% } else {%>
                                                            <%if(srcPresence.isFlagsts() && srcPresence.getFullname()!=null && srcPresence.getFullname().length()>0){%>
                                                                <%//=srcPresence.getFullname() update by satrya 2013-02-01%>
                                                                <input type="text" name="EMP_FULLNAME" value="<%= employee!=null && employee.getFullName().length()>0?employee.getFullName(): srcPresence.getFullname() %>" size="30"  disabled> * <%=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_EMPLOYEE_ID)%> 
                                                                <%}else{%>
                                                                <input type="text" name="EMP_FULLNAME" value="<%=employee.getFullName()%>" size="30"  disabled> * <%=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_EMPLOYEE_ID)%> 
                                                                <%}%>
                                                       
                                                        <% } %>
                                                      </td>
                                                      <td width="10%" nowrap> 
                                                        <div align="left">Employee 
                                                          Number</div>
                                                      </td>
                                                      <td width="49%"> : 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%= employee.getEmployeeNum() %> 
                                                        <% } else {%>
                                                            <%if(srcPresence.isFlagsts() && srcPresence.getEmpnumber()!=null && srcPresence.getEmpnumber().length()>0){%>
                                                                <%//= srcPresence.getEmpnumber() update by satrya 2013-02-01%>
                                                                <input type="text" name="EMP_NUMBER" value="<%= employee!=null && employee.getEmployeeNum().length()>0?employee.getEmployeeNum(): srcPresence.getEmpnumber() %>" size="30"  disabled> * <%=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_EMPLOYEE_ID)%> 
                                                                    <%}else{%>
                                                                       <input type="text" name="EMP_NUMBER"  value="<%=employee.getEmployeeNum()%>" disabled size="35">
                                                                    <%}%>
                                                        
                                                        <% } %>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="12%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div>
                                                      </td>
                                                      <td width="29%" nowrap> 
                                                        : 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%= position.getPosition() %> 
                                                        <% } else {%>
                                                            <%if(srcPresence.isFlagsts() &&  srcPresence.getPositionName()!=null && srcPresence.getPositionName().length()>0){%>
                                                                <%//=srcPresence.getPositionName() update by satrya 2013-02-01%>
                                                                <input type="text" name="EMP_POSITION" value="<%= position!=null && position.getPosition().length()>0?position.getPosition(): srcPresence.getPositionName() %>" size="30"  disabled> 
                                                            <%}else{%>
                                                                <input type="text" name="EMP_POSITION" value="<%=position.getPosition()%>" disabled size="35">
                                                            <%}%>
                                                         
                                                        <% } %>
                                                      </td>
                                                      <td width="10%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                      </td>
                                                      <td width="49%"> : 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%= department.getDepartment() %> 
                                                        <% } else {%>
                                                        <%-- <input type="text" name="EMP_DEPARTMENT"> --%>
                                                        <% /*
                                                            Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);        
                                                            //dept_value.add("0");
                                                            //dept_key.add("select ...");
                                                            Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                    Department dept = (Department) listDept.get(i);
                                                                    dept_key.add(dept.getDepartment());
                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                            }
                                                            dept_value.add("0");
                                                            dept_key.add("...ALL (may take long time)..."); */
                                                        %>
                                                        <% //= ControlCombo.draw("EMP_DEPARTMENT","formElemen",null, "", dept_value, dept_key) %> 
                                                        <%if(srcPresence.isFlagsts() && srcPresence.getDepartmentName()!=null && srcPresence.getDepartmentName().length()>0){%>
                                                            <%//=srcPresence.getDepartmentName() update by satrya 2013-02-01%>
                                                            <input type="text" name="EMP_DEPARTMENT" value="<%= department!=null && department.getDepartment().length()>0?department.getDepartment(): srcPresence.getDepartmentName() %>" size="30"  disabled> 
                                                        <%}else{%>
                                                            <input type="text" name="EMP_DEPARTMENT" value="<%=department.getDepartment()%>" disabled size="40">
                                                        <%}%>
                                                          
                                                        
                                                        <% } %>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="12%" nowrap> 
                                                        <div align="left">Commencing 
                                                          Date</div>
                                                      </td>
                                                      <td width="29%" nowrap> 
                                                        : 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%=( employee.getCommencingDate()==null ? "" : Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy") )%> 
                                                        <% } else {%>
                                                        <%if(srcPresence.isFlagsts() && srcPresence.getsCommarcingDate()!=null && srcPresence.getsCommarcingDate().length()>0){%>
                                                            <%//=srcPresence.getsCommarcingDate() update by satrya 2013-02-01%>
                                                            <input type="text" name="EMP_COMMENCING_DATE" value="<%= employee!=null && employee.getCommencingDate()!=null?Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy"): Formater.formatDate(srcPresence.getsCommarcingDate(), "dd MMMM yyyy") %>" size="30"  disabled> 
                                                        <%}else{%>
                                                        <%
                                                            String sDate ="";
                                                            if(employee.getCommencingDate()!=null){
                                                                sDate = Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy");
                                                            }
                                                         %>
                                                             <input type="text" name="EMP_COMMENCING_DATE" value="<%=sDate%>" disabled>
                                                        <%}%>
                                                        
                                                       
                                                        <% } %>
                                                      </td>
                                                      <td width="10%" nowrap> 
                                                        <div align="left"></div>
                                                      </td>
                                                      <td width="49%"> 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK) || ((iCommand==Command.ADD)&& cekOt)) {%>
                                                        
                                                        <% } else { %>
                                                        <table cellpadding="0" cellspacing="0" border="0">
                                                          <tr> 
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                            <td width="15"><a href="javascript:cmdSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image101" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                            <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td class="command" nowrap width="99"> 
                                                              <div align="left"><a href="javascript:cmdSearchEmp()">Search 
                                                                Employee</a></div>
                                                            </td>
                                                            <td width="10"><img src="<%=approot%>/images/spacer.gif" width="10" height="4"></td>
                                                            <td width="15"><a href="javascript:cmdClearSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnCancelOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnCancel.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                            <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td class="command" nowrap width="99"> 
                                                              <div align="left"><a href="javascript:cmdClearSearchEmp()">Clear 
                                                                Search </a></div>
                                                            </td>
                                                          </tr>
                                                        </table>
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
                                                        <%-- <%//=ControlDate.drawDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATE], presence.getPresenceDate() != null ? presence.getPresenceDate() : new Date(),"formElemen", 1, -5)%>
                                                        <%//=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_PRESENCE_DATE)%>
                                                        <input type="text" name="<%=FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME]%>" value="<%=presence.getPresenceDatetime()%>" class="formElemen">
                                                        --%>
                                                        <%if( srcPresence.getDatefrom()!=null){
                                                            if(iCommand==Command.EDIT){%>
                                                                <%=ControlDate.drawDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME], presence.getPresenceDatetime() != null ? presence.getPresenceDatetime() : new Date(),"formElemen", 1, -5)%> 
                                                            <%}else{%>
                                                                <%=ControlDate.drawDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME], srcPresence.getDatefrom(),"formElemen", 1, -5)%> 
                                                            <%}%>
                                                        <%}else{%>
                                                        <%=ControlDate.drawDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME], presence.getPresenceDatetime() != null ? presence.getPresenceDatetime() : new Date(),"formElemen", 1, -5)%> 
                                                       <%}%> 
                                                       <%=ControlDate.drawTime(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_DATETIME], presence.getPresenceDatetime() != null ? presence.getPresenceDatetime() : new Date(),"formElemen", 24, 1, 0)%> 
                                                        * <%=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_PRESENCE_DATETIME)%> 
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="12%" valign="top" nowrap> 
                                                        <div align="left">Status</div>
                                                      </td>
                                                      <td width="88%"> : 
                                                        <%-- <%//=ControlDate.drawDate(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_TIME_IN], presence.getTimeIn(),"formElemen", 1, -5)%>
                                                        <%//=ControlDate.drawTime(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_TIME_IN], presence.getTimeIn(), "elemenForm", 24, 1, 0) %> 
                                                        <%//=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_TIME_IN)%>
                                                        <input type="text" name="<%=FrmPresence.fieldNames[FrmPresence.FRM_FIELD_STATUS]%>" value="<%=presence.getStatus()%>" class="formElemen">
                                                        <%=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_STATUS)%> --%>
                                                        <% 
                                                            Vector status_value = Presence.getStatusIndexString();
                                                            Vector status_key = Presence.getStatusAttString();
                                                            //Vector listDept = PstDepartment.listAll();
                                                            //for (int i = 0; i < listDept.size(); i++) {
                                                                    //Department dept = (Department) listDept.get(i);
                                                                    /*status_key.add("In"); status_value.add("0");
                                                                    status_key.add("Out - Home"); status_value.add("1");
                                                                    status_key.add("Out - On duty"); status_value.add("2");
                                                                    status_key.add("In - Lunch"); status_value.add("3");
                                                                    status_key.add("In - Break"); status_value.add("4");
                                                                    status_key.add("In - Callback"); status_value.add("5");
                                                                */
                                                            //}
                                                        %>
                                                        <%= ControlCombo.draw(frmPresence.fieldNames[FrmPresence.FRM_FIELD_STATUS],"formElemen",null, String.valueOf(presence.getStatus()), status_value, status_key) %> 
                                                         <%//= ControlCombo.draw(FrmPresence.fieldNames[FrmPresence.FRM_FIELD_STATUS],"formElemen",null, String.valueOf(presence.getStatus()), status_value, status_key) %> 
                                                        * <%=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_STATUS)%> 
                                                      </td>
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
                                                       if(cekOt==false){
							ctrLine.setBackCaption("Back to List Presence");
                                                        }else{
                                                            ctrLine.setBackCaption("");
                                                        }
							ctrLine.setCommandStyle("buttonlink");
							ctrLine.setConfirmDelCaption("Yes Delete Presence");
							ctrLine.setDeleteCaption("Delete Presence");
							ctrLine.setSaveCaption("Save Presence");

							if (privDelete){
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
							}

							if (privAdd == false){
								ctrLine.setAddCaption("");
							}
                                            if(iCommand==Command.CONFIRM){ 
							%>
                                              <%= ctrLine.drawImage(Command.EDIT, iErrCode, errMsg)%>
                                               <% } else {%>
                                               <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%>
                                               <%}%>
                                             </td>
                                          <td width="2%">&nbsp;</td>
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
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
