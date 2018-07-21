<%-- 
    Document   : overtime_set_manually
    Created on : Dec 3, 2012, 6:00:04 PM
    Author     : SATRYA RAMAYU
--%>
<%@page import="com.dimata.harisma.session.attendance.OutPermision"%>
<%@page import="com.dimata.harisma.form.attendance.FrmPresence"%>
<%@page import="com.dimata.harisma.session.attendance.SessEmpSchedule"%>
<%@page import="com.dimata.harisma.entity.leave.I_Leave"%>
<%@page import="com.dimata.harisma.entity.attendance.PstDpStockManagement"%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.sql.Time" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.qdep.entity.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.overtime.*" %>
<%@ page import = "com.dimata.harisma.form.overtime.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.session.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>

<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_OVERTIME, AppObjInfo.OBJ_PAYROLL_OVERTIME_REPORT);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
            /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- Jsp Block -->
<%!
 long hr_department = 0;
 int finalApprovalMinLevel = PstPosition.LEVEL_DIRECTOR;
 int finalApprovalMaxLevel = PstPosition.LEVEL_GENERAL_MANAGER;
 
 public void init(){
            try{ hr_department = Long.parseLong(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT")); } catch(Exception exc){}                        
}
  
%>

<!-- Jsp Block -->
<%
        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }


float minOvertimeHour = 0.0F;
try{
    String minOv = PstSystemProperty.getValueByName("MIN_OVERTM_DURATION");
    if(minOv!=null && minOv.length()>0){
        minOvertimeHour =  Float.parseFloat(minOv)/60f;
    }
 } catch(Exception exc){
     
 }
        


long oidOvertimeDetail = FRMQueryString.requestLong(request, "FRM_FIELD_OVERTIME_DETAIL_ID");
long oidEmployee = FRMQueryString.requestLong(request, "FRM_FIELD_EMPLOYEE_ID");
long lDateSelected = FRMQueryString.requestLong(request, "lDateFrom");
int iCommand = FRMQueryString.requestCommand(request);
String msgString="";
boolean submitIsOk = false;

int iErrCode = FRMMessage.ERR_NONE;

Employee employee = new Employee();
long oidPosition = 0;
Position position = new Position();
long oidDepartment = 0;
Department department = new Department();
long empScheduleId = 0;
long periodId = 0;
Date selectedDate = new Date();
OvertimeDetail overtimeDetail = new OvertimeDetail(); 
Presence objNewPresence = new Presence(); 

if ((iCommand==Command.EDIT || iCommand==Command.SAVE || iCommand== Command.BACK)) 
///if ((iCommand==Command.EDIT) || (iCommand==Command.ASK) || (iCommand==Command.CONFIRM)) 
{ 
    try{
	employee = PstEmployee.fetchExc(oidEmployee);
	oidPosition = employee.getPositionId();
	position = PstPosition.fetchExc(oidPosition);
	oidDepartment = employee.getDepartmentId();
	department = PstDepartment.fetchExc(oidDepartment);
        // Formater.formatDate(lDateSelected, "yyyy-MM-dd"
        periodId = PstPeriod.getPeriodIdBySelectedDate(new Date(lDateSelected)); 
        empScheduleId = PstEmpSchedule.getEmpScheduleId(periodId, oidEmployee);
        if(oidOvertimeDetail!=0){
        overtimeDetail = PstOvertimeDetail.fetchExc(oidOvertimeDetail);
        }
        selectedDate = new Date(lDateSelected); 
     } catch(Exception exc){
        employee = new Employee();  
     }
}



//Vector vListLeavePermision  = SessEmpSchedule.listLeavePermision(oidEmployee, selectedDate, oidDepartment, periodId);///schedule ada Leave atau break 

if(iCommand==Command.SAVE)
{
      try{
             
             CtrlOvertimeDetail ctrlOvertimeDetail = new CtrlOvertimeDetail(request);
           iErrCode=ctrlOvertimeDetail.actionManual(iCommand, oidOvertimeDetail, minOvertimeHour, request,userIsLogin.toLowerCase());   
            
             msgString = ctrlOvertimeDetail.getMessage();
             if(!(msgString.length()>0)){
               submitIsOk = true;
             }

           }catch(Exception ex){
               System.out.println("Exception"+ex); 
           }          
}


%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Attendance Edit Presence</title>
<script language="JavaScript">
<!--
     function cmdSave(){
            document.frm_overtime.command.value="<%=Command.SAVE%>"; 
            document.frm_overtime.action="overtime_set_manually.jsp";
            document.frm_overtime.submit();
        }
        <%if(submitIsOk) {%>
        self.opener.cmdRel(); 
       
        window.close();//for IE
        //document.frm_dp_list.action= window.close(); //di firefox mau  
       <%}%>
     function cmdExit(){
        window.close();
     } 	
     
//-------------- script control line -------------------

function MM_preloadImages() { //v3.0
		var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
		var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
		if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
	}
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
<!--
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
                    //-->
</SCRIPT>
<style type="text/css">
<!--
.style1 {color: #FFFF00}
-->
</style>
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
<%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
<%@include file="../../styletemplate/template_header_no_menu.jsp" %>
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
                 Attendance &gt; Daily Presence &gt; Attendance Edit Presence<!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                 <td class="tablecolor"  style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                       <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frm_overtime" method="post" action="">
                                        <input type="hidden" name="command" value="<%=iCommand%>">
                                       <input type="hidden" name="<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_OVERTIME_DETAIL_ID]%>" value="<%=String.valueOf(oidOvertimeDetail)%>">
                                      <input type="hidden" name="<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=oidEmployee%>">
                                          <input type="hidden" name="lDateFrom" value="<%=lDateSelected%>">
                  
                        <input type="hidden" name="<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM]+"_yr"%>" value="<%=overtimeDetail.getDateFrom().getYear()+1900%>">
                        <input type="hidden" name="<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM]+"_mn"%>" value="<%=overtimeDetail.getDateFrom().getMonth()+1%>">
                        <input type="hidden" name="<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM]+"_dy"%>" value="<%=overtimeDetail.getDateFrom().getDate()%>">
                        <input type="hidden" name="<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM]+"_hr"%>" value="<%=overtimeDetail.getDateFrom().getHours()%>">
                        <input type="hidden" name="<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_FROM]+"_mi"%>" value="<%=overtimeDetail.getDateFrom().getMinutes()%>">
                        
                        
                        <input type="hidden" name="<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO]+"_yr"%>" value="<%=overtimeDetail.getDateTo().getYear()+1900%>">
                        <input type="hidden" name="<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO]+"_mn"%>" value="<%=overtimeDetail.getDateTo().getMonth()+1%>">
                        <input type="hidden" name="<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO]+"_dy"%>" value="<%=overtimeDetail.getDateTo().getDate()%>">
                        <input type="hidden" name="<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO]+"_hr"%>" value="<%=overtimeDetail.getDateTo().getHours()%>">
                        <input type="hidden" name="<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_DATE_TO]+"_mi"%>" value="<%=overtimeDetail.getDateTo().getMinutes()%>">
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
                                                  <div align="center"><b><font size="3">EDIT MANUAL REAL TIME OVERTIME</font></b></div>                                                </td>
                                              </tr>
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <!--<input type="hidden" name="<//%=FrmPresence.fieldNames[FrmPresence.FRM_FIELD_EMPLOYEE_ID]%>" value="<%//=String.valueOf(presence.getEmployeeId())%>" class="formElemen">-->
                                                      <%//=frmPresence.getErrorMsg(FrmPresence.FRM_FIELD_EMPLOYEE_ID)%> 
                                                      <td width="10%" nowrap> 
                                                        <div align="left">Name</div>                                                      </td>
                                                      <td width="40%" nowrap>: 
                                                        <%= employee.getFullName() %>                                                      </td>
                                                      <td width="13%" nowrap> 
                                                        <div align="left">Employee 
                                                          Number</div>                                                      </td>
                                                      <td width="37%"> : 
                                                        <%= employee.getEmployeeNum() %>                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="10%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div>                                                      </td>
                                                      <td width="40%" nowrap>: 
                                                        <%= position.getPosition() %>                                                      </td>
                                                      <td width="13%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>                                                      </td>
                                                      <td width="37%"> : 
                                                        <%= department.getDepartment() %>                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="10%" nowrap> 
                                                        <div align="left">Presence Date Time</div>                                                      </td>
                                                      <td width="40%"> : 
                                                        <%= Formater.formatTimeLocale(new Date(lDateSelected), "EEEE, dd MMMM yyyy")%>                                                      </td>
                                                      
                                                    </tr>
                                                  </table>                                                </td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <hr>                                                </td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    
                                                    <tr> 
                                                      <td width="9%" valign="top" nowrap="nowrap">Real Time IN</td>
                                                      <td width="5%" valign="top"> 
                                                       :<%=overtimeDetail.getDateFrom() ==null ? "-" :Formater.formatDate(overtimeDetail.getDateFrom(), "HH:mm")%></td>
                                                   
                                                      <td width="88%"> :
                                                        <%
                                                                String whereClause = PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID] + " = " + oidEmployee 
                                                                                     +" AND "+ PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] 
                                                                                     + " BETWEEN " + "\""+Formater.formatDate(new Date((lDateSelected - (1000 * 60 * 60 * 24))), "yyyy-MM-dd 00:00:00")+"\""
                                                                                      + " AND " + "\""+ Formater.formatDate(new Date((lDateSelected + 1000 * 60 * 60 * 24)), "yyyy-MM-dd 23:59:59")+"\"" 
                                                                                      + " AND "+ PstPresence.fieldNames[PstPresence.FLD_STATUS]+"="+Presence.STATUS_IN; 
                                                                       
                                                                String order = PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] +" ASC "; 
                                                                Vector pre_value = new Vector(1,1);
                                                                Vector pre_key = new Vector(1,1);
                                                                //pre_value.add("No data presence date");
                                                                //pre_key.add("select...");
                                                                String cbIn ="";
                                                                //String cbNameIn = frmPresence.fieldNames[FrmOvertimeDetail.FRM_FIELD_PRESENCE_ID]+"_In"+"_"+empScheduleId+"_"+periodId;
                                                                Vector listPresence = PstPresence.list(0, 0, whereClause, order);
                                                                int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(new Date(lDateSelected));
                                                                int idxSch1OnPresenceIn = PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1; 
                                                                Date existPresenceSch1In = PstEmpSchedule.getPresencebyScheduleId(idxSch1OnPresenceIn, empScheduleId);
                                                                String sExistPresenceIn = ""+ Formater.formatDate(existPresenceSch1In, "dd MMMM yyyy HH:mm");
                                                               String selectedIn =""; 
                                                                for (int i = 0; i < listPresence.size(); i++) {
                                                                    try{
                                                                                 Presence objPresence = (Presence) listPresence.get(i);
                                                                                 
                                                                                 String pDate= ""+ Formater.formatDate(objPresence.getPresenceDatetime(), "dd MMMM yyyy HH:mm");
                                                                                  pre_key.add(pDate); 
                                                                                 pre_value.add(""+ objPresence.getPresenceDatetime().getTime());                                                                                  
                                                                                //selectedVal = ""+objPresence.getOID();
                                                                                 
                                                                                if(objPresence.getStatus() == Presence.STATUS_IN && pDate.equals(sExistPresenceIn)){
                                                                                    selectedIn = ""+objPresence.getPresenceDatetime().getTime();
                                                                                    
                                                                                }
                                                                              /*  String names=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REAL_TIME_START]+"_yr ="+(selectedIn.getYear()+1900) +" & "
                                                                                             + FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REAL_TIME_START]+"_mn ="+(objPresence.getPresenceDatetime().getMonth()+1) +" & "   
                                                                                             + FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REAL_TIME_START]+"_dy ="+objPresence.getPresenceDatetime().getDate() +" & " 
                                                                                             + FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REAL_TIME_START]+"_hr ="+objPresence.getPresenceDatetime().getHours() +" & "
                                                                                             + FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REAL_TIME_START]+"_mi ="+objPresence.getPresenceDatetime().getMinutes(); 
                                                                                             
                                                                                */

                                                                               cbIn =  ControlCombo.draw("selectRealDateFrom","elementForm", "-select-", ""+selectedIn, pre_value, pre_key);
                                                      
                                                                    }catch(Exception ex){
                                                                        continue;
                                                                       
                                                                    }
                                                                }
                                                           
                                                            %>
                                                        <%= cbIn%> </td>
                                                    </tr>
                                                    
                                                     <tr> 
                                                      <td width="9%" valign="top" nowrap="nowrap" >Real Time OUT</td>
                                                      <td width="5%" valign="top"> 
                                                       :<%=overtimeDetail.getDateTo() ==null ? "-":Formater.formatDate(overtimeDetail.getDateTo(), "HH:mm")%>                                                      </td>
                                                      <td width="88%"> : 
                                                           <%
                                                              
                                                                whereClause = PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID] + " = " + oidEmployee 
                                                                                     +" AND "+ PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] 
                                                                                     + " BETWEEN " + "\""+Formater.formatDate(new Date((lDateSelected - (1000 * 60 * 60 * 24))), "yyyy-MM-dd 00:00:00")+"\""
                                                                                      + " AND " + "\""+ Formater.formatDate(new Date((lDateSelected + 1000 * 60 * 60 * 24)), "yyyy-MM-dd 23:59:59")+"\"" 
                                                                                      + " AND "+ PstPresence.fieldNames[PstPresence.FLD_STATUS]+"="+Presence.STATUS_OUT; 
                                                                       
                                                                order = PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] +" ASC "; 
                                                                Vector pre_valueOut = new Vector(1,1);
                                                                Vector pre_keyOut = new Vector(1,1);
                                                                
                                                                String cbOut ="";
                                                                //String cbNameIn = frmPresence.fieldNames[FrmOvertimeDetail.FRM_FIELD_PRESENCE_ID]+"_In"+"_"+empScheduleId+"_"+periodId;
                                                                listPresence = PstPresence.list(0, 0, whereClause, order);
                                                                  idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(new Date(lDateSelected));
                                                                int idxSch1OnPresenceOut = PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1; 
                                                                Date existPresenceSch1Out = PstEmpSchedule.getPresencebyScheduleId(idxSch1OnPresenceOut, empScheduleId);
                                                               // listPresence = PstPresence.list(0, 0, whereClause, order);
                                                               //String selectValuePresence ="";
                                                                String sExistPresenceOut ="";
                                                                String selectedOut="";
                                                                sExistPresenceOut = ""+ Formater.formatDate(existPresenceSch1Out, "dd MMMM yyyy HH:mm");
                                                              
                                                                for (int i = 0; i < listPresence.size(); i++) {
                                                                    try{
                                                                                 Presence objPresence = (Presence) listPresence.get(i);
                                                                                 
                                                                                 String pDate= ""+ Formater.formatDate(objPresence.getPresenceDatetime(), "dd MMMM yyyy HH:mm");
                                                                                  pre_keyOut.add(pDate); 
                                                                                 pre_valueOut.add(""+ objPresence.getPresenceDatetime().getTime());                                                                                 
                                                                                //selectedVal = ""+objPresence.getOID();
                                                                                 
                                                                                if(objPresence.getStatus() == Presence.STATUS_OUT && pDate.equals(sExistPresenceOut)){
                                                                                    selectedOut = ""+objPresence.getPresenceDatetime().getTime(); 
                                                                                    
                                                                                }
                                                                                 /*String namesX=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REAL_TIME_END]+"_yr ="+(objPresence.getPresenceDatetime().getYear()+1900) +" & " 
                                                                                             + FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REAL_TIME_END]+"_mn ="+(objPresence.getPresenceDatetime().getMonth()+1) +" & "
                                                                                             + FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REAL_TIME_END]+"_dy ="+objPresence.getPresenceDatetime().getDate() +" & "
                                                                                             + FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REAL_TIME_END]+"_hr ="+objPresence.getPresenceDatetime().getHours() +" & "
                                                                                             + FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_REAL_TIME_END]+"_mi ="+objPresence.getPresenceDatetime().getMinutes(); */
                                                                               cbOut =  ControlCombo.draw("selectRealDateTo","elementForm", "-select-", ""+selectedOut, pre_valueOut, pre_keyOut);
                                                      
                                                                    }catch(Exception ex){
                                                                        continue;
                                                                       
                                                                    }
                                                                }
                                                           
                                                            %>
                                                        <%= cbOut%>
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
                                            <td>&nbsp;</td>
                                            <td>
                                                    <table>
                                                            <tr>
                                                                <!--<td><input type="checkbox" name="<//%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_FLAG_STATUS]%>" value="1" <//%=overtimeDetail.getFlagStatus()!=0?"checked=\"checked\"":"" %>></td>-->
                                                                  <td><input type="checkbox" name="<%=FrmOvertimeDetail.fieldNames[FrmOvertimeDetail.FRM_FIELD_FLAG_STATUS]%>" value="1" checked="checked"></td>
                                                                    <td nowrap="nowrap"><i><font color="#FF0000">! Set manual Real Start And Real End !</font></i></td>


                                                            </tr>
                                                    </table>
                                            </td>
											
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>
                                      
                                        <tr> 
                                         <%if(iCommand == Command.SAVE){%> 
                                          <td width="2%" bgcolor="#FFFF00"></td>
                                          <td width="2%" bgcolor="#FFFF00">
                                            Message : <%= CtrlOvertimeDetail.resultText[CtrlOvertimeDetail.LANGUAGE_FOREIGN][iErrCode]%>  
                                          </td>
                                          <%}else{%>
                                          <td width="2%">&nbsp;</td>
                                          <td width="2%">&nbsp;</td>
                                          <%}%>
                                        </tr>
					<tr>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>
                                           <tr> 
                                                      <td colspan="3"> 
                                                        <table border="0" cellspacing="0" cellpadding="0" width="567">
                                                          <tr> 
							
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                            <td width="26"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3001','','<%=approot%>/images/BtnSaveOn.jpg',1)"><img name="Image3001" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Employee's Absence"></a></td>
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                            <td nowrap width="224"><a href="javascript:cmdSave()" class="command">Save & Process </a></td>
						
                                                            <td nowrap width="26"><a href="javascript:cmdExit()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Exit Manual Presence"></a></td>
                                                            <td nowrap width="11">&nbsp;</td>
                                                            <td nowrap width="272"><a href="javascript:cmdExit()" class="command">Exit 
                                                              </a></td>
                                                          </tr>
                                                        </table>                                                      </td>
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

