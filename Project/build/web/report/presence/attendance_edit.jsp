<%-- 
    Document   : attendance_edit
    Created on : Aug 12, 2012, 3:04:11 PM
    Author     : SATRYA RAMAYU
--%>
<%@page import="org.eclipse.jdt.internal.compiler.ast.AND_AND_Expression"%>
<%@page import="com.dimata.harisma.utility.service.presence.PresenceAnalyser"%>
<%@page import="com.dimata.harisma.session.attendance.OutPermision"%>
<%@page import="com.dimata.harisma.session.attendance.SessEmpSchedule"%>
<%@page import="com.dimata.harisma.session.attendance.AttendanceEdit"%>
<%@page import="com.dimata.harisma.session.attendance.PresenceReportDaily"%>
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
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_PRESENCE   	); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- Jsp Block -->
<%
CtrlPresence ctrlPresence = new CtrlPresence(request);
int iCommand = FRMQueryString.requestCommand(request);
long oidPresence = FRMQueryString.requestLong(request, "presence_id");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int start = FRMQueryString.requestInt(request,"start");
//Date selectedDatePresence = FRMQueryString.requestDate(request, "SELECTED_DATE_PRESENCE");
int iErrCode = FRMMessage.ERR_NONE;
String msg = "";
String whereClause = "";
//String orderClause = "";


//ControlLine ctrLine = new ControlLine();
/*if (iCommand==Command.CONFIRM){ 
    iErrCode = ctrlPresence.action(Command.EDIT , oidPresence, request);
  } else {
    iErrCode = ctrlPresence.action(iCommand , oidPresence, request);
 }*/
//errMsg = ctrlPresence.getMessage();
FrmPresence frmPresence = ctrlPresence.getForm();
//Presence presence = ctrlPresence.getPresence();

long oidEmployee = FRMQueryString.requestLong(request, "employeeId");
long lDateSelected = FRMQueryString.requestLong(request, "requestDateDaily");
//Date selectedDate = new Date(lDateSelected);
Employee employee = new Employee();
long oidPosition = 0;
Position position = new Position();
long oidDepartment = 0;
Department department = new Department();
long empScheduleId = 0;
long periodId = 0;
Date selectedDate = new Date();
ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
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
        scheduleSymbol = PstEmpSchedule.getDailySchedule(new Date(lDateSelected), oidEmployee);
        selectedDate = new Date(lDateSelected);
     } catch(Exception exc){
        employee = new Employee();  
     }
}



Vector vListLeavePermision  = SessEmpSchedule.listLeavePermision(oidEmployee, selectedDate, oidDepartment, periodId);///schedule ada Leave atau break 

if(iCommand==Command.SAVE)
{
      String cbPresenceOidIn =frmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_ID]+"_In"+"_"+empScheduleId+"_"+periodId;
      String cbPresenceOidOut = frmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_ID]+"_Out"+empScheduleId+"_"+periodId;
      String sPresenceOidIn = request.getParameter(cbPresenceOidIn);
      String sPresenceOidOut = request.getParameter(cbPresenceOidOut);
        int statusIn = Presence.STATUS_IN;
        int idxFieldName = PstEmpSchedule.getIdxNameOfTableBySelectedDate(new Date(lDateSelected));
        int idxSch1OnPresenceIn = PstEmpSchedule.OFFSET_INDEX_IN + idxFieldName - 1;
        int idxSch1OnPresenceOut = PstEmpSchedule.OFFSET_INDEX_OUT + idxFieldName - 1;
        int statusOut = Presence.STATUS_OUT;

    if(sPresenceOidIn!=null && sPresenceOidIn.length()>0 && sPresenceOidIn.compareTo("0")!=0){
          long lPresenceOidIn =  Long.parseLong(sPresenceOidIn);
          objNewPresence = PstPresence.fetchExc(lPresenceOidIn);
            PstPresence.updatePresenceStatusInOut(lPresenceOidIn, statusIn);
            PstEmpSchedule.updateScheduleDataByEmpScheduleId(periodId, empScheduleId, idxSch1OnPresenceIn, objNewPresence.getPresenceDatetime()); 
     // }
      //if(sPresenceOidOut!=null && sPresenceOidOut.length()>0 && sPresenceOidOut.compareTo("0")!=0){
     }
     if(sPresenceOidOut!=null && sPresenceOidOut.length()>0 && sPresenceOidOut.compareTo("0")!=0){
          long lPresenceOidOut =  Long.parseLong(sPresenceOidOut);
          objNewPresence = PstPresence.fetchExc(lPresenceOidOut);
            PstPresence.updatePresenceStatusInOut(lPresenceOidOut, statusOut);
            PstEmpSchedule.updateScheduleDataByEmpScheduleId(periodId, empScheduleId, idxSch1OnPresenceOut, objNewPresence.getPresenceDatetime());
     }   
        ///jika user memilih combo -select-
       if(sPresenceOidIn.equals("")){
        PstEmpSchedule.updateScheduleDataByEmpScheduleId(periodId, empScheduleId, idxSch1OnPresenceIn, null);
       }
       if(sPresenceOidOut.equals("")){
        PstEmpSchedule.updateScheduleDataByEmpScheduleId(periodId, empScheduleId, idxSch1OnPresenceOut, null);
       }
      int status = 0;
      long lPresenceOid=0;
      Date selectedDateFrom = null;
      java.util.Date selectedDateTo = null;
    //Sort scheduled permission by Buble Sort
    if(vListLeavePermision!=null && vListLeavePermision.size()>0 && (vListLeavePermision.size()>=4) && (vListLeavePermision.size()%2==0)){
        boolean swapped = true;
        int j = 0;
        OutPermision outPermisionOutTemp = null;
         OutPermision outPermisionInTemp = null;
        try{
        while (swapped) {
              swapped = false;
              j=j+2;
              for (int i = 0; i < vListLeavePermision.size() - j; i=i+2) {      
                   OutPermision outPermision1 = (OutPermision) vListLeavePermision.get(i);
                   OutPermision outPermision2 = (OutPermision) vListLeavePermision.get(i+2);

                    if (outPermision1.getTypeScheduleDateTime().getTime() > outPermision2.getTypeScheduleDateTime().getTime()) {                          
                          outPermisionOutTemp = (OutPermision) vListLeavePermision.get(i);
                          outPermisionInTemp  = (OutPermision) vListLeavePermision.get(i+1);

                          vListLeavePermision.set(i,   (OutPermision) vListLeavePermision.get(i+2));
                          vListLeavePermision.set(i+1, (OutPermision) vListLeavePermision.get(i+3));

                          vListLeavePermision.set(i+2, outPermisionOutTemp);
                          vListLeavePermision.set(i+3, outPermisionInTemp);                                                                       
                          swapped = true;
                    }
              }                
        }
       }catch(Exception exc){
           out.println("Sorting Scheduled Permission failed, used unsorted!"+ exc.toString());
       }
     }
    if(vListLeavePermision !=null && vListLeavePermision.size() > 0){                   
        for (int lvIdx = 0; lvIdx < vListLeavePermision.size(); lvIdx++) {
         try{   
            OutPermision outPermision = (OutPermision) vListLeavePermision.get(lvIdx);

            String cbName = frmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_ID]+"_"+empScheduleId+
                    "_" + outPermision.getInOutType()+"_"+periodId+"_"+lvIdx;
            String sPreValue = request.getParameter(cbName+"_prevalue");///jika sebelumnya sdh di pilih
            
            //long lPresenceOidSelectedPrev = 0;
            String sPresenceOid = request.getParameter(cbName);            
            if(sPresenceOid!=null && sPresenceOid.length()>0 && sPresenceOid.compareTo("0")!=0){
                if(outPermision.getInOutType() == OutPermision.INOUT_TYPE_OUT){
                    //status = ;
                    
                    lPresenceOid = Long.parseLong(sPresenceOid);
                    PstPresence.updatePresenceStatusPersonalOutIn(lPresenceOid, Presence.STATUS_OUT_PERSONAL, outPermision.getTypeScheduleDateTime(),outPermision.getInOutType(),outPermision.getScheduleSymbolId());
                    //lPresenceOidSelectedPrev = Long.parseLong(sPreValue);
                    objNewPresence = PstPresence.fetchExc(lPresenceOid);
                    selectedDateFrom = objNewPresence.getPresenceDatetime();
                   // vListLeavePermision.remove(lvIdx);
                    //lvIdx = lvIdx-1;
                    //lPresenceOid = 0;
                    ///break;
                }
                else if(outPermision.getInOutType() == OutPermision.INOUT_TYPE_IN){
                    //status = Presence.STATUS_IN_PERSONAL;
                    lPresenceOid = Long.parseLong(sPresenceOid);
                    PstPresence.updatePresenceStatusPersonalOutIn(lPresenceOid, Presence.STATUS_IN_PERSONAL, outPermision.getTypeScheduleDateTime(),outPermision.getInOutType(),outPermision.getScheduleSymbolId());
                    //lPresenceOidSelectedPrev = Long.parseLong(sPreValue);
                    objNewPresence = PstPresence.fetchExc(lPresenceOid);
                    selectedDateTo = objNewPresence.getPresenceDatetime();
                    // vListLeavePermision.remove(lvIdx);
                    //lvIdx = lvIdx-1;
                    //lPresenceOid = 0;
                    //break; 
                }
              if(selectedDateFrom !=null && selectedDateTo !=null){
                PstPresence.resetPresenceDataBySelectedDateFromTo(oidEmployee, selectedDateFrom, selectedDateTo);
                selectedDateFrom = null;
                selectedDateTo = null;
              }
            }
            //artinya user memilih -selected-
            /*else{
                 if(outPermision.getInOutType() == OutPermision.INOUT_TYPE_OUT){
                    //status = ;
                    
                    lPresenceOid = Long.parseLong(sPresenceOid);
                    PstPresence.updatePresenceStatusPersonalOutIn(lPresenceOid, Presence.STATUS_OUT_PERSONAL, null,outPermision.getInOutType(),outPermision.getScheduleSymbolId());
                    //lPresenceOidSelectedPrev = Long.parseLong(sPreValue);
                    objNewPresence = PstPresence.fetchExc(lPresenceOid);
                    selectedDateFrom = objNewPresence.getPresenceDatetime();
                   // vListLeavePermision.remove(lvIdx);
                    //lvIdx = lvIdx-1;
                    //lPresenceOid = 0;
                    ///break;
                }
                else if(outPermision.getInOutType() == OutPermision.INOUT_TYPE_IN){
                    //status = Presence.STATUS_IN_PERSONAL;
                    lPresenceOid = Long.parseLong(sPresenceOid);
                    PstPresence.updatePresenceStatusPersonalOutIn(lPresenceOid, Presence.STATUS_IN_PERSONAL, null,outPermision.getInOutType(),outPermision.getScheduleSymbolId());
                    //lPresenceOidSelectedPrev = Long.parseLong(sPreValue);
                    objNewPresence = PstPresence.fetchExc(lPresenceOid);
                    selectedDateTo = objNewPresence.getPresenceDatetime();
                    // vListLeavePermision.remove(lvIdx);
                    //lvIdx = lvIdx-1;
                    //lPresenceOid = 0;
                    //break; 
                }
            }*/
        
         }catch(Exception ex){
             System.out.println("Exception listLeave : " +ex.toString());
         }
       }//end loop
      //update analize status
      PresenceAnalyser.analyzePresencePerEmployee(0,0,selectedDate, department.getOID(),0L, employee.getFullName(), employee.getEmployeeNum(),"");
      //out.println("<span class=\"comment\"><br>&nbsp;Data is Update ...</span>");
      iErrCode = CtrlPresence.RSLT_OK;
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
            document.frm_presence.command.value="<%=Command.SAVE%>"; 
            ///document.frm_presence.command.value="<%//=oidEmployee%>"; 
            document.frm_presence.action="<%=approot%>/report/presence/attendance_edit.jsp";
            document.frm_presence.submit();
        }

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
<style type="text/css">
<!--
.style1 {color: #FFFF00}
-->
</style>
<!-- #EndEditable -->
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
                <td class="tablecolor" style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frm_presence" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                       <input type="hidden" name="presence_id" value="<%=String.valueOf(oidPresence)%>">
                                      <input type="hidden" name="prev_command" value="<%=String.valueOf(prevCommand)%>">
                                       <input type="hidden" name="employeeId" value="<%=oidEmployee%>">
                                          <input type="hidden" name="requestDateDaily" value="<%=lDateSelected%>">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                     
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
                                                  <div align="center"><b><font size="3">EDIT MANUAL ATTENDANCE STATUS</font></b></div>                                                </td>
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
                                                      <td width="10%" nowrap> 
                                                        <div align="left">Schedule Type</div>                                                      </td>
                                                      <td width="40%"> : 
                                                          <%= scheduleSymbol.getSymbol() !=null && scheduleSymbol.getSymbol().length() > 0 ? scheduleSymbol.getSymbol() +" &nbsp; "+"["+scheduleSymbol.getSchedule()+"]" : "-"%>                                                      </td>
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
                                                      <td width="9%" valign="top">IN</td>
                                                      <td width="5%" valign="top"> 
                                                       <%=scheduleSymbol.getTimeIn() ==null ? "-" :Formater.formatDate(scheduleSymbol.getTimeIn(), "HH:mm")%>                                                      </td>
                                                      <td width="88%"> :
                                                        <%
                                                                whereClause = PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID] + " = " + oidEmployee 
                                                                                     +" AND "+ PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] 
                                                                                     + " BETWEEN " + "\""+Formater.formatDate(new Date((lDateSelected - (1000 * 60 * 60 * 24))), "yyyy-MM-dd 00:00:00")+"\""
                                                                                      + " AND " + "\""+ Formater.formatDate(new Date((lDateSelected + 1000 * 60 * 60 * 24)), "yyyy-MM-dd 23:59:59")+"\"" ;
                                                                       
                                                                String order = PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] +" ASC "; 
                                                                Vector pre_value = new Vector(1,1);
                                                                Vector pre_key = new Vector(1,1);
                                                                //pre_value.add("No data presence date");
                                                                //pre_key.add("select...");
                                                                String cbIn ="";
                                                                String cbNameIn = frmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_ID]+"_In"+"_"+empScheduleId+"_"+periodId;
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
                                                                                 pre_value.add(""+ objPresence.getOID());                                                                                 
                                                                                //selectedVal = ""+objPresence.getOID();
                                                                                 
                                                                                if(objPresence.getStatus() == Presence.STATUS_IN && pDate.equals(sExistPresenceIn)){
                                                                                    selectedIn = ""+objPresence.getOID();
                                                                                    
                                                                                }
                                                                                
                                                                               cbIn =  ControlCombo.draw(cbNameIn,"elementForm", "-select-", ""+selectedIn, pre_value, pre_key);
                                                      
                                                                    }catch(Exception ex){
                                                                        continue;
                                                                       
                                                                    }
                                                                }
                                                           
                                                            %>
                                                        <%= cbIn%>                                                      </td>
                                                    </tr>
                                                    <% 
                                                    Vector pre_valuePersonal = new Vector(1,1);
                                                    Vector pre_keyPersonal = new Vector(1,1);
                                                     //pre_value.add("No data presence date");
                                                     //pre_key.add("select...");
                                                    String statusPersonal ="";
                                                     String cbPersonal ="";
                                                     //String cbPersonalIn ="";
                                                      whereClause = PstPresence.fieldNames[PstPresence.FLD_EMPLOYEE_ID] + " = " + oidEmployee 
                                                                                     +" AND ("+ PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] 
                                                                                     + " BETWEEN " + "\""+Formater.formatDate(new Date((lDateSelected - (1000 * 60 * 60 * 24))), "yyyy-MM-dd 00:00:00")+"\""
                                                                                     + " AND " + "\""+ Formater.formatDate(new Date((lDateSelected + 1000 * 60 * 60 * 24)), "yyyy-MM-dd 23:59:59")+"\")" 
                                                                                     + " AND " +  PstPresence.fieldNames[PstPresence.FLD_SCHEDULE_DATETIME]+" !=0 "; 
                                                      order =       PstPresence.fieldNames[PstPresence.FLD_PRESENCE_DATETIME] +" ASC ,"
                                                                                     + PstPresence.fieldNames[PstPresence.FLD_SCHEDULE_DATETIME] +" ASC ,"
                                                                                     + PstPresence.fieldNames[PstPresence.FLD_STATUS] +" ASC ,"
                                                                                     + PstPresence.fieldNames[PstPresence.FLD_SCHEDULE_TYPE] +" ASC "; 
                                                      Vector listPresenceByOrderPresenceScheduleStatus = PstPresence.list(0, 0, whereClause, order);
                                                       for ( int pIdx = 0; pIdx < listPresence.size(); pIdx++) {                                                              
                                                        try{                                                          
                                                            Presence objPresence = (Presence) listPresence.get(pIdx); 
                                                           
                                                            String pDate= ""+ Formater.formatDate(objPresence.getPresenceDatetime(), "dd MMMM yyyy HH:mm");
                                                             pre_keyPersonal.add(pDate); 
                                                             pre_valuePersonal.add(""+ objPresence.getOID());                                                                                                                                            
                                                         }catch(Exception ex){
                                                             //continue;
                                                         
                                                          }
                                                     }
                                                     
                                                     //Sort scheduled permission by Buble Sort
                                                      if(vListLeavePermision!=null && vListLeavePermision.size()>0 && (vListLeavePermision.size()>=4) && (vListLeavePermision.size()%2==0)){
                                                          boolean swapped = true;
                                                          int j = 0;
                                                          OutPermision outPermisionOutTemp = null;
                                                           OutPermision outPermisionInTemp = null; 
                                                          try{
                                                          while (swapped) {
                                                                swapped = false;
                                                                j=j+2;
                                                                for (int i = 0; i < vListLeavePermision.size() - j; i=i+2) {      
                                                                     OutPermision outPermision1 = (OutPermision) vListLeavePermision.get(i);
                                                                     OutPermision outPermision2 = (OutPermision) vListLeavePermision.get(i+2);
                                                                     
                                                                      if (outPermision1.getTypeScheduleDateTime().getTime() > outPermision2.getTypeScheduleDateTime().getTime()) {                          
                                                                            outPermisionOutTemp = (OutPermision) vListLeavePermision.get(i);
                                                                            outPermisionInTemp  = (OutPermision) vListLeavePermision.get(i+1);
                                                                            
                                                                            vListLeavePermision.set(i,   (OutPermision) vListLeavePermision.get(i+2));
                                                                            vListLeavePermision.set(i+1, (OutPermision) vListLeavePermision.get(i+3));
                                                                             
                                                                            vListLeavePermision.set(i+2, outPermisionOutTemp);
                                                                            vListLeavePermision.set(i+3, outPermisionInTemp);                                                                       
                                                                            swapped = true;
                                                                      }
                                                                }                
                                                          }
                                                         }catch(Exception exc){
                                                             out.println("Sorting Scheduled Permission failed, used unsorted!"+ exc.toString());
                                                         }
                                                       }
                                                      
                                                    // Vector vListLeavePermision  = SessEmpSchedule.listLeavePermision(oidEmployee, selectedDate, oidDepartment, periodId);///schedule ada Leave atau break 
                                                    for (int lvIdx = 0; lvIdx < vListLeavePermision.size(); lvIdx++) {
                                                        OutPermision outPermision = (OutPermision) vListLeavePermision.get(lvIdx);
                                                      
                                                        
                                                        if(outPermision.getTypeScheduleDateTime() !=null){
                                                        statusPersonal = outPermision.getScheduleType() + 
                                                                (outPermision.getInOutType()== OutPermision.INOUT_TYPE_IN ?" IN ":" OUT " )
                                                                 +"<td width=\"" +"7%"+"\" valign=\""+"top"+"\">"+Formater.formatDate(outPermision.getTypeScheduleDateTime(), "HH:mm")+"</td>";
                                                       }else{
                                                             statusPersonal = outPermision.getScheduleType() + 
                                                                (outPermision.getInOutType()== OutPermision.INOUT_TYPE_IN ?" IN ":" OUT " )
                                                                 +"<td width=\"" +"7%"+"\" valign=\""+"top"+"\">"+"-"+"</td>";
                                                       }        
                                                              
                                                        String cbName = frmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_ID]+"_"+empScheduleId+
                                                                "_" + outPermision.getInOutType()+"_"+periodId+"_"+lvIdx;
                                                        
                                                        String selected = ""; 
                                                        Date dSelectedDate = new Date(lDateSelected);
                                                        String sSelectedDate = Formater.formatDate(dSelectedDate, "yyyy-MM-dd");
                                                        int inOutTypeIn = OutPermision.INOUT_TYPE_IN;
                                                        int inOutTypeOut = OutPermision.INOUT_TYPE_OUT;
                                                        
                                                      for ( int pIdx = 0; pIdx < listPresenceByOrderPresenceScheduleStatus.size(); pIdx++) { 
                                                         try{
                                                          
                                                            Presence objPresence = (Presence) listPresenceByOrderPresenceScheduleStatus.get(pIdx); 
                                                            if(outPermision.getTypeScheduleDateTime()!=null ){
                                                                String sSchPresenceDate = Formater.formatDate(objPresence.getScheduleDatetime(), "yyyy-MM-dd HH:mm"); 
                                                                String sOutpermissionSch = Formater.formatDate(outPermision.getTypeScheduleDateTime(), "yyyy-MM-dd HH:mm"); 
                                                                if(sSchPresenceDate.equals(sOutpermissionSch) &&
                                                                      ((  objPresence.getStatus()==Presence.STATUS_OUT_PERSONAL  && outPermision.getInOutType()==OutPermision.INOUT_TYPE_OUT ) ||
                                                                         (objPresence.getStatus()==Presence.STATUS_IN_PERSONAL  && outPermision.getInOutType()== OutPermision.INOUT_TYPE_IN) )){
                                                                    selected = ""+objPresence.getOID();                                                                                                                                 
                                                                    break; 
                                                                }
                                                            }else{
                                                             selected = "0"; 
                                                            break;                                                              
                                                             /*String sPresenceDate = Formater.formatDate(objPresence.getPresenceDatetime(), "yyyy-MM-dd"); 
                                                           //if(outPermision.getScheduleSymbolId() == objPresence.getScheduleLeaveId()&&
                                                              if(outPermision.getScheduleSymbolId() !=0 && 
                                                               ( outPermision.getInOutType() == inOutTypeIn && objPresence.getStatus() == Presence.STATUS_IN_PERSONAL )
                                                                && (sPresenceDate.equals(sSelectedDate)) ||
                                                                
                                                               (outPermision.getInOutType() == inOutTypeOut && objPresence.getStatus() == Presence.STATUS_OUT_PERSONAL)
                                                                && (sPresenceDate.equals(sSelectedDate))){
                                                                 //== objPresence.getScheduleLeaveId() 
                                                                    //){
                                                               selected = ""+objPresence.getOID();
                                                                //listPresence.remove(pIdx);                              
                                                                //pIdx=pIdx-1;                                                                  
                                                              break;
                                                              }*/
                                                           }                                                              
                                                         }catch(Exception ex){
                                                             //continue;
                                                         }
                                                     }
                                                        
                                                        cbPersonal =  ControlCombo.draw(cbName,"elementForm", "-select-", selected, pre_valuePersonal, pre_keyPersonal);
                                                     %> 
                                                     <tr> 
                                                      <td width="5%" valign="top">
                                                          <input type="hidden" name="<%=(cbName+"_prevalue")%>" value="<%=selected%>">
                                                       <%= 
                                                             statusPersonal
                                                       %>                                                      </td>
                                                      <td width="88%"> : 
                                                         
                                                        <%= cbPersonal%>                                                      </td>
                                                    </tr>
                                                    <% } 
                                                      
                                                    %> 
                                                  
                                                     <tr> 
                                                      <td width="9%" valign="top" >OUT :</td>
                                                      <td width="5%" valign="top"> 
                                                       <%=scheduleSymbol.getTimeOut() ==null ? "-":Formater.formatDate(scheduleSymbol.getTimeOut(), "HH:mm")%>                                                      </td>
                                                      <td width="88%"> : 
                                                           <%
                                                              
                                                                Vector pre_valueOut = new Vector(1,1);
                                                               Vector pre_keyOut = new Vector(1,1);
                                                                //pre_value.add("No data presence date");
                                                                //pre_key.add("select...");
                                                                String cbOut ="";
                                                                String cbNameOut = frmPresence.fieldNames[FrmPresence.FRM_FIELD_PRESENCE_ID]+"_Out"+empScheduleId+"_"+periodId;
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
                                                                                 pre_valueOut.add(""+ objPresence.getOID());                                                                                 
                                                                                //selectedVal = ""+objPresence.getOID();
                                                                               if(objPresence.getStatus() == Presence.STATUS_OUT && pDate.equals(sExistPresenceOut)){
                                                                                selectedOut = ""+objPresence.getOID();
                                                                                }
                                                                               cbOut =  ControlCombo.draw(cbNameOut,"elementForm", "-select-", ""+selectedOut, pre_valueOut, pre_keyOut);
                                                      
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
                                            <td>&nbsp;</td>
                                        </tr>
                                      
                                        <tr> 
                                         <%if(iCommand == Command.SAVE){%>
                                          <td width="2%" bgcolor="#FFFF00"></td>
                                          <td width="2%" bgcolor="#FFFF00">
                                            Message : <%= CtrlPresence.resultText[CtrlPresence.LANGUAGE_FOREIGN][iErrCode]%> 
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
							<%if(privUpdate){%>
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                            <td width="26"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3001','','<%=approot%>/images/BtnSaveOn.jpg',1)"><img name="Image3001" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Employee's Absence"></a></td>
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                            <td nowrap width="224"><a href="javascript:cmdSave()" class="command">Save 
                                                              Presence</a></td>
							<%}%>
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
