<%-- 
    Document   : emp_schedule_change_view
    Created on : 30-Dec-2015, 12:08:45
    Author     : GUSWIK
--%>

<%@page import="java.util.Hashtable"%>
<%@page import="com.dimata.harisma.entity.attendance.PstEmpSchedule"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstMappingPosition"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDepartment"%>
<%@page import="com.dimata.harisma.entity.masterdata.Department"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.harisma.entity.masterdata.Position"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstDivision"%>
<%@page import="com.dimata.harisma.entity.masterdata.Division"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page import="com.dimata.qdep.entity.I_DocStatus"%>
<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="com.dimata.system.entity.PstSystemProperty"%>
<%@page import="com.dimata.harisma.entity.leave.I_Leave"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.masterdata.ScheduleSymbol"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstScheduleSymbol"%>
<%@page import="java.util.Date"%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.employee.workschedule.EmpScheduleChange"%>
<%@page import="com.dimata.harisma.form.employee.workschedule.FrmEmpScheduleChange"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.employee.workschedule.CtrlEmpScheduleChange"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.employee.workschedule.PstEmpScheduleChange"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%
            /*
             * Page Name  		:  empScheduleChangeEdit.jsp
             * Created on 		:  [date] [time] AM/PM
             *
             * @author  		: Priska_20150930
             * @version  		: -
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

<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<!--package harisma -->

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G1_EMPLOYEE, AppObjInfo.OBJ_EMPLOYEE_MENU);%>
<%@ include file = "../../main/checkuser.jsp" %>


<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidEmpScheduleChange = FRMQueryString.requestLong(request, "oidEmpScheduleChange");

            I_Leave leaveConfig = null;
            try{
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
            }catch (Exception e){
            System.out.println("Exception : " + e.getMessage());
            }
            
            /*variable declaration*/
            int recordToGet = 50;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = "";
            String orderClause = "";

            CtrlEmpScheduleChange ctrlEmpScheduleChange = new CtrlEmpScheduleChange(request);
            ControlLine ctrLine = new ControlLine();
            Vector listEmpScheduleChange = new Vector(1, 1);

            /*switch statement */
            iErrCode = ctrlEmpScheduleChange.action(iCommand, oidEmpScheduleChange);
            
            /* end switch*/
            FrmEmpScheduleChange frmEmpScheduleChange = ctrlEmpScheduleChange.getForm();

            /*count list All Position*/
            int vectSize = PstEmpScheduleChange.getCount(whereClause);

            EmpScheduleChange empScheduleChange = ctrlEmpScheduleChange.getdEmpScheduleChange();
            msgString = ctrlEmpScheduleChange.getMessage();

            /*switch list EmpScheduleChange*/
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                //start = PstEmpScheduleChange.findLimitStart(empScheduleChange.getOID(),recordToGet, whereClause);
                oidEmpScheduleChange = empScheduleChange.getOID();
            }

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlEmpScheduleChange.actionList(iCommand, start, vectSize, recordToGet);
            }
            /* end switch list*/

            /* get record to display */
            listEmpScheduleChange = PstEmpScheduleChange.list(start, recordToGet, whereClause, orderClause);

            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listEmpScheduleChange.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listEmpScheduleChange = PstEmpScheduleChange.list(start, recordToGet, whereClause, orderClause);
            }

            Hashtable employeeAll = PstEmployee.hashListEmployeeFullname();    

%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Master Data EmpScheduleChange</title>
        
        <style type="text/css">
            #formula {background-color: #FFF;}
            #menu_utama {padding: 9px 14px; border-left: 1px solid #0099FF; font-size: 14px; background-color: #F3F3F3;}
            #menu_title {color:#0099FF; font-size: 14px; font-weight: bold;}
            #menu_teks {color:#CCC;}
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 14px; background-color: #F5F5F5;}
            #btn {
              background: #C7C7C7;
              border: 1px solid #BBBBBB;
              border-radius: 3px;
              font-family: Arial;
              color: #474747;
              font-size: 11px;
              padding: 3px 7px;
              cursor: pointer;
            }

            #btn:hover {
              color: #FFF;
              background: #B3B3B3;
              border: 1px solid #979797;
            }
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
            #tdForm {
                padding: 5px;
            }
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
            #td1 {border: 1px solid #CCC; background-color: #DDD;}
            #td2 {border: 1px solid #CCC;}
            #tdTotal {background-color: #fad9d9;}
            #query {
                padding: 7px 9px; color: #f7f7f7; background-color: #797979; 
                border:1px solid #575757; border-radius: 5px; 
                margin-bottom: 5px; font-size: 12px;
                font-family: Courier New,Courier,Lucida Sans Typewriter,Lucida Typewriter,monospace;
            }
            #info {
                width: 500px;
                padding: 21px; color: #797979; background-color: #F7F7F7;
                border:1px solid #DDD;
                font-size: 12px;
            }
            
            .LockOff {
                display: none;
                visibility: hidden;
            }

            .LockOn {
                display: block;
                visibility: visible;
                position: absolute;
                z-index: 999;
                top: 0px;
                left: 0px;
                width: 105%;
                height: 105%;
                background-color: #ccc;
                text-align: center;
                padding-top: 20%;
                filter: alpha(opacity=75);
                opacity: 0.75;
                font-size: 250%;
            }
        </style>
        
        <script language="JavaScript">


        function cmdSearchEmp(){
        window.open("<%=approot%>/employee/formschedulechange/searchschedulechange.jsp?formName=frmEmpScheduleChange&empPathId=<%=frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_APPLICANT_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");           
        }
        
        function cmdSearchEmpExchange(){
        window.open("<%=approot%>/employee/formschedulechange/searchschedulechange_exc.jsp?formName=frmEmpScheduleChange&empPathId=<%=frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_EXCHANGE_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");           
        }

        function cmdApproval(empId,oidEmpScheduleChange,approvedLevel){
       
	document.frmEmpScheduleChange.action="documentApproval.jsp?oidApprover="+empId+"&oidEmpScheduleChange="+oidEmpScheduleChange+"&approvedLevel="+approvedLevel+"&alamatJsp=emp_schedule_change_edit.jsp"+"&scheduleType=0";
	document.frmEmpScheduleChange.submit(); 
        }
        function cmdApprovalCombo(oidEmpScheduleChange,approvedLevel){
         
        
        var empId = 0;
            if(approvedLevel==3){
            empId = document.frmEmpScheduleChange.<%=FrmEmpScheduleChange.fieldNames[FrmEmpScheduleChange.FRM_FIELD_APPROVAL_LEVEL1_ID]%>.value;
            }
            if(approvedLevel==4){
            empId = document.frmEmpScheduleChange.<%=FrmEmpScheduleChange.fieldNames[FrmEmpScheduleChange.FRM_FIELD_APPROVAL_LEVEL2_ID]%>.value;
            }
       
        document.frmEmpScheduleChange.action="documentApproval.jsp?oidApprover="+empId+"&oidEmpScheduleChange="+oidEmpScheduleChange+"&approvedLevel="+approvedLevel+"&alamatJsp=emp_schedule_change_edit.jsp"+"&scheduleType=0";
          
        document.frmEmpScheduleChange.submit(); 
        }
            function cmdAdd(){
                document.frmEmpScheduleChange.oidEmpScheduleChange.value="0";
                document.frmEmpScheduleChange.command.value="<%=Command.ADD%>";
                document.frmEmpScheduleChange.prev_command.value="<%=prevCommand%>";
                document.frmEmpScheduleChange.action="emp_schedule_change_edit.jsp";
                document.frmEmpScheduleChange.submit();
            }

            function cmdAsk(oidEmpScheduleChange){
                document.frmEmpScheduleChange.oidEmpScheduleChange.value = oidEmpScheduleChange;
                document.frmEmpScheduleChange.command.value="<%=Command.ASK%>";
                document.frmEmpScheduleChange.prev_command.value="<%=prevCommand%>";
                document.frmEmpScheduleChange.action="emp_schedule_change_edit.jsp";
                document.frmEmpScheduleChange.submit();
            }

            function cmdConfirmDelete(oidEmpScheduleChange){
                document.frmEmpScheduleChange.oidEmpScheduleChange.value=oidEmpScheduleChange;
                document.frmEmpScheduleChange.command.value="<%=Command.DELETE%>";
                document.frmEmpScheduleChange.prev_command.value="<%=prevCommand%>";
                document.frmEmpScheduleChange.action="emp_schedule_change.jsp";
                document.frmEmpScheduleChange.submit();
            }
            function cmdSave(){
                document.frmEmpScheduleChange.command.value="<%=Command.SAVE%>";
                document.frmEmpScheduleChange.prev_command.value="<%=prevCommand%>";
                document.frmEmpScheduleChange.action="emp_schedule_change_edit.jsp";
                document.frmEmpScheduleChange.submit();
            }

            function cmdEdit(oidEmpScheduleChange){
                document.frmEmpScheduleChange.oidEmpScheduleChange.value=oidEmpScheduleChange;
                document.frmEmpScheduleChange.command.value="<%=Command.EDIT%>";
                document.frmEmpScheduleChange.prev_command.value="<%=prevCommand%>";
                document.frmEmpScheduleChange.action="emp_schedule_change.jsp";
                document.frmEmpScheduleChange.submit();
            }

            function cmdCancel(oidEmpScheduleChange){
                document.frmEmpScheduleChange.oidEmpScheduleChange.value=oidEmpScheduleChange;
                document.frmEmpScheduleChange.command.value="<%=Command.EDIT%>";
                document.frmEmpScheduleChange.prev_command.value="<%=prevCommand%>";
                document.frmEmpScheduleChange.action="emp_schedule_change_edit.jsp";
                document.frmEmpScheduleChange.submit();
            }

            function cmdBack(){
                document.frmEmpScheduleChange.command.value="<%=Command.BACK%>";
                document.frmEmpScheduleChange.action="emp_schedule_change.jsp";
                document.frmEmpScheduleChange.submit();
            }

            function cmdListFirst(){
                document.frmEmpScheduleChange.command.value="<%=Command.FIRST%>";
                document.frmEmpScheduleChange.prev_command.value="<%=Command.FIRST%>";
                document.frmEmpScheduleChange.action="emp_schedule_change.jsp";
                document.frmEmpScheduleChange.submit();
            }

            function cmdListPrev(){
                document.frmEmpScheduleChange.command.value="<%=Command.PREV%>";
                document.frmEmpScheduleChange.prev_command.value="<%=Command.PREV%>";
                document.frmEmpScheduleChange.action="emp_schedule_change.jsp";
                document.frmEmpScheduleChange.submit();
            }

            function cmdListNext(){
                document.frmEmpScheduleChange.command.value="<%=Command.NEXT%>";
                document.frmEmpScheduleChange.prev_command.value="<%=Command.NEXT%>";
                document.frmEmpScheduleChange.action="emp_schedule_change.jsp";
                document.frmEmpScheduleChange.submit();
            }

            function cmdListLast(){
                document.frmEmpScheduleChange.command.value="<%=Command.LAST%>";
                document.frmEmpScheduleChange.prev_command.value="<%=Command.LAST%>";
                document.frmEmpScheduleChange.action="emp_schedule_change.jsp";
                document.frmEmpScheduleChange.submit();
            }

            function fnTrapKD(){
                //alert(event.keyCode);
                switch(event.keyCode) {
                    case <%=LIST_PREV%>:
                            cmdListPrev();
                        break;
                    case <%=LIST_NEXT%>:
                            cmdListNext();
                        break;
                    case <%=LIST_FIRST%>:
                            cmdListFirst();
                        break;
                    case <%=LIST_LAST%>:
                            cmdListLast();
                        break;
                    default:
                        break;
                    }
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
                    }

        </SCRIPT>
        <!-- #EndEditable -->
    </head>

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
 <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%//@include file="../../styletemplate/template_header.jsp" %>
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
			  DOCUMENT
            </strong></font>
	      </td>
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
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmEmpScheduleChange" method ="post" action="" >
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
				      <input type="hidden" name="oidEmpScheduleChange" value="<%=oidEmpScheduleChange%>">
                                      <input type="hidden" name="<%=frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_TYPE_OF_SCHEDULE]%>" value="<%=PstEmpScheduleChange.TYPE_SCHEDULE_CHANGE%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="center" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                             
                                             
                                              
                                              <%
                                                Hashtable listScheduleSym = PstScheduleSymbol.getHashTblSchedule(0, 0, "", "");
                                           

                                                %>
                                                
                                            <%
                                                Vector val_status = new Vector(1,1);
                                                Vector key_status = new Vector(1,1);
                                                val_status.add(""+I_DocStatus.DOCUMENT_STATUS_DRAFT) ;
                                                key_status.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_DRAFT] );
                                                val_status.add(""+I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED);
                                                key_status.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_TO_BE_APPROVED] );
                                                val_status.add(""+I_DocStatus.DOCUMENT_STATUS_FINAL);
                                                key_status.add(""+I_DocStatus.fieldDocumentStatus[I_DocStatus.DOCUMENT_STATUS_FINAL]);
                                            %>    
                                            
                                           <%
                                           Employee employeeOri = new Employee();
                                           Employee employeeExc = new Employee();
                                           Division divisionOri = new Division();
                                           Division divisionExc = new Division();
                                           Position positionOri = new Position();
                                           Position positionExc = new Position();
                                           Department departmentOri = new Department();
                                           Department departmentExc = new Department();
                                           Location locationOri = new Location();
                                           try {
                                           employeeOri = PstEmployee.fetchExc(empScheduleChange.getApplicantEmployeeId());
                                           } catch (Exception e) {}
                                           try {
                                           employeeExc = PstEmployee.fetchExc(empScheduleChange.getExchangeEmployeeId());
                                           } catch (Exception e) {}
                                           
                                           if (employeeOri != null && empScheduleChange.getApplicantEmployeeId() != 0 ){
                                               try{
                                                divisionOri = PstDivision.fetchExc(employeeOri.getDivisionId());  
                                                positionOri = PstPosition.fetchExc(employeeOri.getPositionId());
                                                departmentOri = PstDepartment.fetchExc(employeeOri.getDepartmentId());
                                                locationOri = PstLocation.fetchExc(employeeOri.getLocationId());
                                               }catch (Exception e){}
                                             }
                                           if (employeeExc != null && empScheduleChange.getExchangeEmployeeId() != 0 ){
                                               try{
                                                 divisionExc = PstDivision.fetchExc(employeeExc.getDivisionId());
                                                 positionExc = PstPosition.fetchExc(employeeExc.getPositionId());
                                                 departmentExc = PstDepartment.fetchExc(employeeExc.getDepartmentId());  
                                                }catch (Exception e){}
                                             
                                           }
                                             
                                           
                                            %>
                                            
                                            
                                           
                                            
                                            
                                            
                                            
                                            <table width="80%" border="0" cellspacing="0" cellpadding="0">                                              
                                              
                                              <tr id="formula" width="50%" align="center" height="850" valign="top"  >
                                                <td  >
                                                    <div>
                                                        
                                                        <table width="80%" height="50%" border="0">
                                                        <tr>
                                                          <td width="35%">  
                                                          <img src="<%=approot%>/imgcompany/logo.png"  width="20%" height="80%">
                                                          </td>
                                                          <td width="35%" colspan="2">
                                                              <table width="100%" border="0">
                                                            <tr>
                                                              <td colspan="100%">&nbsp;</td>
                                                              </tr>
                                                            <tr>
                                                              <td width="20%">Date</td>
                                                              <td width="80%"> : <%=empScheduleChange.getDateOfRequestDatetime()%></td>
                                                              </tr>
                                                            <tr>
                                                              <td width="20%">Outlet</td>
                                                              <td width="80%"> : <%=divisionOri.getDivision() %></td>
                                                              </tr>
                                                            <tr>
                                                              <td height="20%">&nbsp;</td>
                                                              <td width="80%"><p>CHANGE OFF/SHIFT FORM</p>
                                                                <p>(Form Tukar Libur/Jadwal Kerja)</p></td>
                                                              </tr>
                                                          </table>
                                                          </td>
                                                        </tr>
                                                        <tr>
                                                          <td height="30%" colspan="4" style="border:#000; border-bottom:medium" >
                                                              
                                                                  <table  width="80%" style="border-collapse: collapse;" cellpadding="0" cellspacing="1" >
                                                                    <tr>
                                                                      <td  style="font-size: 20px; border:#000; border-bottom:medium" >______________________________________________________________________________________________</td>
                                                                    </tr>
                                                                  </table>
                                                                  <table  width="100%" cellpadding="0" cellspacing="0" border="0">
                                                                    <tr>
                                                                      <td width="20%">&nbsp;</td>
                                                                      <td width="40%" style="text-align: center;">Applicant (Karyawan Ybs) </td>
                                                                      <td width="40%" style="text-align: center;">Exchange With (Yang diajak Menukar) </td>
                                                                    </tr>
                                                                  </table>
                                                              
                                                                  <table class="tblStyle"   width="100%" height="40%" style="border-collapse: collapse;font-size: 11px;" cellpadding="0" cellspacing="0" border="1">
        
                                                                      <tr>
                                                                      <td width="20%" height="23">Department ( Divisi )</td>
                                                                      <td width="40%">: <%=departmentOri.getDepartment()%></td>
                                                                      <td width="40%">: <%=departmentExc.getDepartment()%></td>
                                                                    </tr>
                                                                      <tr>
                                                                        <td height="20%">Change (Tukar)</td>
                                                                        <td>:
                                                                          <% 
                                                                            String checked1 = "";
                                                                            String checked2 = "";
                                                                            if (empScheduleChange.getTypeOfForm() == 1){
                                                                                checked2 = "checked";
                                                                                checked1 = "";
                                                                            } else {
                                                                                checked2 = "";
                                                                                checked1 = "checked";
                                                                            }
                                                                            %>
                                                                            <input name="<%=frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_TYPE_OF_FORM]%>" type="radio" <%=checked1%> value="0" > Off
                                                                            <input name="<%=frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_TYPE_OF_FORM]%>" type="radio" <%=checked2%> value="1" > Shift
                                                                        
                                                                        </td>
                                                                        <td>-</td>
                                                                      </tr>
                                                                      <tr>

                                                                        <td height="15%">Enrollment No. (No. Pendaftaran)</td>
                                                                        <td>: <%=employeeOri.getEmployeeNum()%></td>
                                                                        <td>: <%=employeeExc.getEmployeeNum()%></td>

                                                                      </tr>
                                                                      <tr>

                                                                        <td height="15%">Name(Nama)</td>
                                                                        <td>: <%=employeeOri.getFullName()%></td>
                                                                        <td>: <%=employeeExc.getFullName()%></td>

                                                                      </tr>
                                                                      <tr>
                                                                      <td height="15%">Position (Position)</td>
                                                                      <td>: <%=positionOri.getPosition()%></td>
                                                                      <td>: <%=positionExc.getPosition()%></td>

                                                                    </tr>
                                                                </table>
                                                             

                                                              <table class="tblStyle"   width="100%" height="50%" style="border-collapse: collapse; margin-top: 5px; font-size: 11px; padding: 0;" cellpadding="0" cellspacing="0" border="1">
                                                                  <tr>
                                                                    <td colspan="2" style="text-align:center;">Original (Asli)</td>
                                                                    <td colspan="2" style="text-align:center;">Change(Tukar)</td>
                                                                  </tr>
                                                                  <tr>
                                                                    <td>Tgl</td>
                                                                    <td width="40%">: <%= empScheduleChange.getOriginalDate()%></td>
                                                                    <td width="10%">Tgl</td>
                                                                    <td width="40%">: <%= empScheduleChange.getNewChangeDate()%></td>
                                                                  </tr>
                                                                  <tr>
                                                                    <td>Shift</td>
                                                                    <% long scheduleIdOri = 0 ;
                                                                       long scheduleIdExc = 0 ;
                                                                       if (empScheduleChange != null && empScheduleChange.getNewChangeDate() != null && empScheduleChange.getExchangeEmployeeId() != 0 ) {
                                                                        scheduleIdExc = PstEmpSchedule.getSchedule(empScheduleChange.getNewChangeDate().getDate(), empScheduleChange.getExchangeEmployeeId(), empScheduleChange.getNewChangeDate()); 
                                                                        scheduleIdOri = PstEmpSchedule.getSchedule(empScheduleChange.getOriginalDate().getDate(), empScheduleChange.getApplicantEmployeeId(), empScheduleChange.getOriginalDate()); 
                                                                       } 
                                                                       String symbolOri = "";
                                                                       String symbolExc = ""; 
                                                                       if (scheduleIdOri != 0 && scheduleIdExc != 0 ){
                                                                        symbolOri = PstScheduleSymbol.getScheduleSymbol(scheduleIdOri);
                                                                        symbolExc = PstScheduleSymbol.getScheduleSymbol(scheduleIdExc);
                                                                       }
                                                                       %>
                                                                        
                                                                    <td>: <%=symbolOri%></td>

                                                         
                                                                    <td>Shift</td>
                                                                    <td>: <%=symbolExc%></td>
                                                                    </tr>
                                                                 <!-- <tr>
                                                                    <td>Off</td>
                                                                    <td>--</td>
                                                                    <td>Shift</td>
                                                                    <td>--</td>
                                                                  </tr> -->
                                                                  <tr>
                                                                    <td width="10%">Remark</td>
                                                                    <td colspan="3">: <%=empScheduleChange.getRemark()%></td>
                                                                  </tr>
                                                              </table>
                                                              <p>&nbsp;</p>
                                                              
                                                              
                                                              <% if ( empScheduleChange.getStatusDoc() > 0 ){ %>
                                                              
                                                              <table width="100%" height="40%" style="border-collapse: collapse;font-size: 11px;" cellpadding="0" cellspacing="0" border="0">
                                                                      <tr>
                                                                        <td colspan="2" style="text-align:center;">Applicant</td>
                                                                        <td colspan="2" style="text-align:center;">Approved By</td>
                                                                      </tr>
                                                                      <tr>
                                                                        <td width="25%">&nbsp;</td>
                                                                        <td width="25%">&nbsp;</td>
                                                                        <td width="25%">&nbsp;</td>
                                                                        <td width="25%">&nbsp;</td>
                                                                      </tr>
                                                                      <tr>
                                                                        <td>&nbsp;</td>
                                                                        <td>&nbsp;</td>
                                                                        <td>Department Head</td>
                                                                        <td>Director</td>
                                                                      </tr>
                                                                      <%
                                                                      Vector listLev1 = new Vector();
                                                                      Vector listLev1Key = new Vector(1,1);
                                                                      Vector listLev1Value = new Vector(1,1);
                                                                   //   if (empScheduleChange.getStatusDoc() == 1 ){
                                                                           listLev1Key.add("Select Approver");
                                                                           listLev1Value.add("0");
                                                                           
                                                                           if (employeeOri != null && empScheduleChange.getApplicantEmployeeId() != 0 ){
                                                                              listLev1 = leaveConfig.getApprovalEmployeeTopLink(empScheduleChange.getApplicantEmployeeId(),PstMappingPosition.SCHEDULE_CHANGE_APPROVAL);
                                                                              // listLev1 = PstEmployee.list(0, 15, "","");
                                                                           } 
                                                                          if(listLev1 != null && listLev1.size()>0){
                                                                                for(int i=0; i<listLev1.size(); i++)
                                                                                {
                                                                                    Employee objEmp = (Employee)listLev1.get(i);
                                                                                    listLev1Key.add(objEmp.getFullName());
                                                                                    listLev1Value.add(""+objEmp.getOID());
                                                                                }
                                                                          }
                                                                       //  }
                                                                      %>
                                                                       <%
                                                                        Vector listLev2 = new Vector();
                                                                        Vector listLev2Key = new Vector(1,1);
                                                                        Vector listLev2Value = new Vector(1,1);
                                                                      if (empScheduleChange.getApprovalLevel1Id() != 0){
                                                                           
                                                                           listLev2Key.add("Select Approver");
                                                                           listLev2Value.add("0");
                                                                           if (employeeOri != null && empScheduleChange.getApplicantEmployeeId() != 0 && empScheduleChange.getApprovalLevel1Id() != 0 ){
                                                                             listLev2 = leaveConfig.getApprovalEmployeeTopLink(empScheduleChange.getApprovalLevel1Id(),PstMappingPosition.SCHEDULE_CHANGE_APPROVAL);
                                                                             //listLev2 = PstEmployee.list(0, 15, "","");
                                                                           }
                                                                          if(listLev2 != null && listLev2.size()>0){
                                                                                for(int i=0; i<listLev2.size(); i++)
                                                                                {
                                                                                    Employee objEmp = (Employee)listLev2.get(i);
                                                                                    listLev2Key.add(objEmp.getFullName());
                                                                                    listLev2Value.add(""+objEmp.getOID());
                                                                                }
                                                                          }                                                                          
                                                                         }
                                                                      %>
                                                                      <tr>
                                                                          <td>
                                                                          <% if (empScheduleChange.getApplicantEmployeeId() != 0) { %>
                                                                              <% if (empScheduleChange.getApprovalDateApplicant() == null ){%>
                                                                                <a href="javascript:cmdApproval('<%=empScheduleChange.getApplicantEmployeeId()%>','<%=empScheduleChange.getOID()%>','1')" class="command">Approve</a>
                                                                              <% } else { %>
                                                                                Approved by <%=employeeOri.getFullName() %>
                                                                              <% } %>
                                                                          <% } else { %>
                                                                            belum ada pemohon
                                                                          <% } %>
                                                                          </td>
                                                                        <td>
                                                                          <% if (empScheduleChange.getExchangeEmployeeId() != 0) { %>
                                                                              <% if (empScheduleChange.getApprovalDateExchange() == null ){%>
                                                                                <a href="javascript:cmdApproval('<%=empScheduleChange.getExchangeEmployeeId()%>','<%=empScheduleChange.getOID()%>','2')" class="command">Approve</a>
                                                                              <% } else { %>
                                                                                Approved by <%=employeeExc.getFullName() %>
                                                                              <% } %>
                                                                          <% } else { %>
                                                                            belum ada pemohon
                                                                          <% } %>
                                                                          </td>
                                                                        <%String attributAppLev1  = "class=\"formElemen\" onChange=\"javascript:cmdApprovalCombo('"+empScheduleChange.getOID()+"','3')\""; %>
                                                                        <%String attributAppLev2 = "class=\"formElemen\" onChange=\"javascript:cmdApprovalCombo('"+empScheduleChange.getOID()+"','4')\""; %>
                                                                        <td>
                                                                             <% if (empScheduleChange.getApplicantEmployeeId() != 0 && empScheduleChange.getApprovalDateApplicant() != null && empScheduleChange.getApprovalDateExchange() != null) { %>
                                                                              <% if (empScheduleChange.getApprovalDateLevel1() == null ){%>
                                                                                <%=ControlCombo.draw(frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_APPROVAL_LEVEL1_ID], "formElemen", null, "" + empScheduleChange.getApprovalLevel1Id(), listLev1Value, listLev1Key, attributAppLev1)%>
                                                                              <% } else { %>
                                                                              Approved by <%=employeeAll.get(empScheduleChange.getApprovalLevel1Id()) %> <input type="hidden" name="<%=frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_APPROVAL_LEVEL1_ID]%>" value="<%=empScheduleChange.getApprovalLevel1Id()%>" class="elemenForm">
                                                                              <% } %>
                                                                          <% } else { %>
                                                                            belum ada pemohon
                                                                          <% } %>
                                                                          </td>
                                                                        <td>
                                                                           <% if (empScheduleChange.getApprovalLevel1Id() != 0) { %>
                                                                              <% if (empScheduleChange.getApprovalDateLevel2() == null ){%>
                                                                                <%=ControlCombo.draw(frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_APPROVAL_LEVEL2_ID], "formElemen", null, "" + empScheduleChange.getApprovalLevel2Id(), listLev2Value, listLev2Key, attributAppLev2)%>
                                                                              <% } else { %>
                                                                                Approved by <%=employeeAll.get(empScheduleChange.getApprovalLevel2Id()) %> <input type="hidden" name="<%=frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_APPROVAL_LEVEL2_ID]%>" value="<%=empScheduleChange.getApprovalLevel1Id()%>" class="elemenForm">
                                                                              <% } %>
                                                                          <% } else { %>
                                                                            belum ada pemohon
                                                                          <% } %>
                                                                        </td>
                                                                        
                                                                      </tr>
                                                                      <tr>
                                                                        <td>Applicant</td>
                                                                        <td>Exchange With</td>
                                                                        <td>Date Approved</td>
                                                                        <td>Date Approved</td>
                                                                      </tr>
                                                                      
                                                                      
                                                                      <tr>
                                                                         <td>&nbsp;</td>
                                                                         <td>&nbsp;</td>
                                                                         <td><%=empScheduleChange.getApprovalDateLevel1() == null ? "-" : empScheduleChange.getApprovalDateLevel1() %> <input type="hidden" name="<%=frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_APPROVAL_DATE_LEVEL1]%>" value="<%=empScheduleChange.getApprovalDateLevel1() == null ? "-" : empScheduleChange.getApprovalDateLevel1()%>" class="elemenForm"></td>
                                                                         <td><%=empScheduleChange.getApprovalDateLevel2() == null ? "-" : empScheduleChange.getApprovalDateLevel2() %> <input type="hidden" name="<%=frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_APPROVAL_DATE_LEVEL2]%>" value="<%=empScheduleChange.getApprovalDateLevel2() == null ? "-" : empScheduleChange.getApprovalDateLevel2()%>" class="elemenForm"></td>
                                                                      
                                                                      </tr>
                                                                      <tr>
                                                                        <td colspan="4" style=" border-bottom:dashed">&nbsp;</td>
                                                                      </tr>
                                                                    </table>
                                                                       <% } %>  
                                                                      
                                                                      
                                                                      
                                                                      
                                                                      
                                                                      
                                                           
                                                          </td>
                                                        </tr>
                                                        <tr>
                                                          <td height="50%" colspan="4"><p>&nbsp;</p></td>
                                                        </tr>
                                                      </table>
                                                     </div>
                                           
                                                 
                                                 </td>
                                              </tr>
                                              
                                        
                                            </table>
                                                                       
                                                                       
                                                                       
                                                                      
                                           <table width="80%" border="0" cellspacing="0" cellpadding="0">                                              
                                              
                                              <tr id="formula" width="50%" align="center" height="850" valign="top"  >
                                                <td  >
                                                    <div>
                                                        
                                                        <table width="80%" height="50%" border="0">
                                                        <tr>
                                                          <td width="35%">  
                                                          <img src="<%=approot%>/imgcompany/logo.png"  width="20%" height="80%">
                                                          </td>
                                                          <td width="35%" colspan="2">
                                                              <table width="100%" border="0">
                                                            <tr>
                                                              <td colspan="100%">&nbsp;</td>
                                                              </tr>
                                                            <tr>
                                                              <td width="20%">Date</td>
                                                              <td width="80%"> : <%=empScheduleChange.getDateOfRequestDatetime()%></td>
                                                              </tr>
                                                            <tr>
                                                              <td width="20%">Outlet</td>
                                                              <td width="80%"> : <%=divisionOri.getDivision() %></td>
                                                              </tr>
                                                            <tr>
                                                              <td height="20%">&nbsp;</td>
                                                              <td width="80%"><p>CHANGE OFF/SHIFT FORM</p>
                                                                <p>(Form Tukar Libur/Jadwal Kerja)</p></td>
                                                              </tr>
                                                          </table>
                                                          </td>
                                                        </tr>
                                                        <tr>
                                                          <td height="30%" colspan="4" style="border:#000; border-bottom:medium" >
                                                              
                                                                  <table  width="80%" style="border-collapse: collapse;" cellpadding="0" cellspacing="1" >
                                                                    <tr>
                                                                      <td  style="font-size: 20px; border:#000; border-bottom:medium" >______________________________________________________________________________________________</td>
                                                                    </tr>
                                                                  </table>
                                                                  <table  width="100%" cellpadding="0" cellspacing="0" border="0">
                                                                    <tr>
                                                                      <td width="20%">&nbsp;</td>
                                                                      <td width="40%" style="text-align: center;">Exchange With (Yang diajak Menukar) </td>
                                                                      <td width="40%" style="text-align: center;">Applicant (Karyawan Ybs) </td>
                                                                    </tr>
                                                                  </table>
                                                              
                                                                  <table class="tblStyle"   width="100%" height="40%" style="border-collapse: collapse;font-size: 11px;" cellpadding="0" cellspacing="0" border="1">
        
                                                                      <tr>
                                                                      <td width="20%" height="23">Department ( Divisi )</td>
                                                                      <td width="40%">: <%=departmentExc.getDepartment()%></td>
                                                                      <td width="40%">: <%=departmentOri.getDepartment()%></td>
                                                                    </tr>
                                                                      <tr>
                                                                        <td height="20%">Change (Tukar)</td>
                                                                        <td>:
                                                                          <% 
                                                                            //terbalik
                                                                            if (empScheduleChange.getTypeOfForm() == 1){
                                                                                checked2 = "checked";
                                                                                checked1 = "";
                                                                            } else {
                                                                                checked2 = "";
                                                                                checked1 = "checked";
                                                                            }
                                                                            %>
                                                                            <input name="<%=frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_TYPE_OF_FORM]%>" type="radio" <%=checked1%> value="0" > Off
                                                                            <input name="<%=frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_TYPE_OF_FORM]%>" type="radio" <%=checked2%> value="1" > Shift
                                                                        
                                                                        </td>
                                                                        <td>-</td>
                                                                      </tr>
                                                                      <tr>

                                                                        <td height="15%">Enrollment No. (No. Pendaftaran)</td>
                                                                        <td>: <%=employeeExc.getEmployeeNum()%></td>
                                                                        <td>: <%=employeeOri.getEmployeeNum()%></td>

                                                                      </tr>
                                                                      <tr>

                                                                        <td height="15%">Name(Nama)</td>
                                                                        <td>: <%=employeeExc.getFullName()%></td>
                                                                        <td>: <%=employeeOri.getFullName()%></td>

                                                                      </tr>
                                                                      <tr>
                                                                      <td height="15%">Position (Position)</td>
                                                                      <td>: <%=positionExc.getPosition()%></td>
                                                                      <td>: <%=positionOri.getPosition()%></td>

                                                                    </tr>
                                                                </table>
                                                             

                                                              <table class="tblStyle"   width="100%" height="50%" style="border-collapse: collapse; margin-top: 5px; font-size: 11px; padding: 0;" cellpadding="0" cellspacing="0" border="1">
                                                                  <tr>
                                                                    <td colspan="2" style="text-align:center;">Original (Asli)</td>
                                                                    <td colspan="2" style="text-align:center;">Change(Tukar)</td>
                                                                  </tr>
                                                                  <tr>
                                                                    <td>Tgl</td>
                                                                    <td width="40%">: <%= empScheduleChange.getNewChangeDate()%></td>
                                                                    <td width="10%">Tgl</td>
                                                                    <td width="40%">: <%= empScheduleChange.getOriginalDate()%></td>
                                                                  </tr>
                                                                  <tr>
                                                                    <td>Shift</td>
                                                                    <% ScheduleSymbol scheduleSymbolExc = new ScheduleSymbol();
                                                                       scheduleSymbolExc = (ScheduleSymbol) listScheduleSym.get(empScheduleChange.getNewChangeScheduleId());
                                                                    %>
                                                                    <td>: <%=scheduleSymbolExc.getSymbol()%></td>
                                                                    <td>Shift</td>
                                                                    <% ScheduleSymbol scheduleSymbolOri = new ScheduleSymbol();
                                                                       scheduleSymbolOri = (ScheduleSymbol) listScheduleSym.get(empScheduleChange.getOriginalScheduleId());
                                                                    %>
                                                                    <td>: <%=scheduleSymbolOri.getSymbol()%></td>
                                                                    
                                                                  </tr>
                                                                  <tr>
                                                                    <td width="10%">Remark</td>
                                                                    <td colspan="3">: <%=empScheduleChange.getRemark()%></td>
                                                                  </tr>
                                                              </table>
                                                              <p>&nbsp;</p>
                                                              
                                                              
                                                              <% if ( empScheduleChange.getStatusDoc() > 0 ){ %>
                                                              
                                                              <table width="100%" height="40%" style="border-collapse: collapse;font-size: 11px;" cellpadding="0" cellspacing="0" border="0">
                                                                      <tr>
                                                                        <td colspan="2" style="text-align:center;">Applicant</td>
                                                                        <td colspan="2" style="text-align:center;">Approved By</td>
                                                                      </tr>
                                                                      <tr>
                                                                        <td width="25%">&nbsp;</td>
                                                                        <td width="25%">&nbsp;</td>
                                                                        <td width="25%">&nbsp;</td>
                                                                        <td width="25%">&nbsp;</td>
                                                                      </tr>
                                                                      <tr>
                                                                        <td>&nbsp;</td>
                                                                        <td>&nbsp;</td>
                                                                        <td>Department Head</td>
                                                                        <td>Director</td>
                                                                      </tr>
                                                                      <%
                                                                      Vector listLev1 = new Vector();
                                                                      Vector listLev1Key = new Vector(1,1);
                                                                      Vector listLev1Value = new Vector(1,1);
                                                                   //   if (empScheduleChange.getStatusDoc() == 1 ){
                                                                           listLev1Key.add("Select Approver");
                                                                           listLev1Value.add("0");
                                                                           
                                                                           if (employeeOri != null && empScheduleChange.getApplicantEmployeeId() != 0 ){
                                                                              listLev1 = leaveConfig.getApprovalEmployeeTopLink(empScheduleChange.getApplicantEmployeeId(),PstMappingPosition.SCHEDULE_CHANGE_APPROVAL);
                                                                              // listLev1 = PstEmployee.list(0, 15, "","");
                                                                           } 
                                                                          if(listLev1 != null && listLev1.size()>0){
                                                                                for(int i=0; i<listLev1.size(); i++)
                                                                                {
                                                                                    Employee objEmp = (Employee)listLev1.get(i);
                                                                                    listLev1Key.add(objEmp.getFullName());
                                                                                    listLev1Value.add(""+objEmp.getOID());
                                                                                }
                                                                          }
                                                                       //  }
                                                                      %>
                                                                       <%
                                                                        Vector listLev2 = new Vector();
                                                                        Vector listLev2Key = new Vector(1,1);
                                                                        Vector listLev2Value = new Vector(1,1);
                                                                      if (empScheduleChange.getApprovalLevel1Id() != 0){
                                                                           
                                                                           listLev2Key.add("Select Approver");
                                                                           listLev2Value.add("0");
                                                                           if (employeeOri != null && empScheduleChange.getApplicantEmployeeId() != 0 && empScheduleChange.getApprovalLevel1Id() != 0 ){
                                                                             listLev2 = leaveConfig.getApprovalEmployeeTopLink(empScheduleChange.getApprovalLevel1Id(),PstMappingPosition.SCHEDULE_CHANGE_APPROVAL);
                                                                             //listLev2 = PstEmployee.list(0, 15, "","");
                                                                           }
                                                                          if(listLev2 != null && listLev2.size()>0){
                                                                                for(int i=0; i<listLev2.size(); i++)
                                                                                {
                                                                                    Employee objEmp = (Employee)listLev2.get(i);
                                                                                    listLev2Key.add(objEmp.getFullName());
                                                                                    listLev2Value.add(""+objEmp.getOID());
                                                                                }
                                                                          }                                                                          
                                                                         }
                                                                      %>
                                                                      <tr>
                                                                          <td>
                                                                          <% if (empScheduleChange.getApplicantEmployeeId() != 0) { %>
                                                                              <% if (empScheduleChange.getApprovalDateApplicant() == null ){%>
                                                                                <a href="javascript:cmdApproval('<%=empScheduleChange.getApplicantEmployeeId()%>','<%=empScheduleChange.getOID()%>','1')" class="command">Approve</a>
                                                                              <% } else { %>
                                                                                Approved by <%=employeeOri.getFullName() %>
                                                                              <% } %>
                                                                          <% } else { %>
                                                                            belum ada pemohon
                                                                          <% } %>
                                                                          </td>
                                                                        <td>
                                                                          <% if (empScheduleChange.getExchangeEmployeeId() != 0) { %>
                                                                              <% if (empScheduleChange.getApprovalDateExchange() == null ){%>
                                                                                <a href="javascript:cmdApproval('<%=empScheduleChange.getExchangeEmployeeId()%>','<%=empScheduleChange.getOID()%>','2')" class="command">Approve</a>
                                                                              <% } else { %>
                                                                                Approved by <%=employeeExc.getFullName() %>
                                                                              <% } %>
                                                                          <% } else { %>
                                                                            belum ada pemohon
                                                                          <% } %>
                                                                          </td>
                                                                        <%String attributAppLev1  = "class=\"formElemen\" onChange=\"javascript:cmdApprovalCombo('"+empScheduleChange.getOID()+"','3')\""; %>
                                                                        <%String attributAppLev2 = "class=\"formElemen\" onChange=\"javascript:cmdApprovalCombo('"+empScheduleChange.getOID()+"','4')\""; %>
                                                                        <td>
                                                                             <% if (empScheduleChange.getApplicantEmployeeId() != 0 && empScheduleChange.getApprovalDateApplicant() != null && empScheduleChange.getApprovalDateExchange() != null) { %>
                                                                              <% if (empScheduleChange.getApprovalDateLevel1() == null ){%>
                                                                                <%=ControlCombo.draw(frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_APPROVAL_LEVEL1_ID], "formElemen", null, "" + empScheduleChange.getApprovalLevel1Id(), listLev1Value, listLev1Key, attributAppLev1)%>
                                                                              <% } else { %>
                                                                              Approved by <%=employeeAll.get(empScheduleChange.getApprovalLevel1Id()) %> <input type="hidden" name="<%=frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_APPROVAL_LEVEL1_ID]%>" value="<%=empScheduleChange.getApprovalLevel1Id()%>" class="elemenForm">
                                                                              <% } %>
                                                                          <% } else { %>
                                                                            belum ada pemohon
                                                                          <% } %>
                                                                          </td>
                                                                        <td>
                                                                           <% if (empScheduleChange.getApprovalLevel1Id() != 0) { %>
                                                                              <% if (empScheduleChange.getApprovalDateLevel2() == null ){%>
                                                                                <%=ControlCombo.draw(frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_APPROVAL_LEVEL2_ID], "formElemen", null, "" + empScheduleChange.getApprovalLevel2Id(), listLev2Value, listLev2Key, attributAppLev2)%>
                                                                              <% } else { %>
                                                                                Approved by <%=employeeAll.get(empScheduleChange.getApprovalLevel2Id()) %> <input type="hidden" name="<%=frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_APPROVAL_LEVEL2_ID]%>" value="<%=empScheduleChange.getApprovalLevel1Id()%>" class="elemenForm">
                                                                              <% } %>
                                                                          <% } else { %>
                                                                            belum ada pemohon
                                                                          <% } %>
                                                                        </td>
                                                                        
                                                                      </tr>
                                                                      <tr>
                                                                        <td>Applicant</td>
                                                                        <td>Exchange With</td>
                                                                        <td>Date Approved</td>
                                                                        <td>Date Approved</td>
                                                                      </tr>
                                                                      
                                                                      
                                                                      <tr>
                                                                         <td>&nbsp;</td>
                                                                         <td>&nbsp;</td>
                                                                         <td><%=empScheduleChange.getApprovalDateLevel1() == null ? "-" : empScheduleChange.getApprovalDateLevel1() %> <input type="hidden" name="<%=frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_APPROVAL_DATE_LEVEL1]%>" value="<%=empScheduleChange.getApprovalDateLevel1() == null ? "-" : empScheduleChange.getApprovalDateLevel1()%>" class="elemenForm"></td>
                                                                         <td><%=empScheduleChange.getApprovalDateLevel2() == null ? "-" : empScheduleChange.getApprovalDateLevel2() %> <input type="hidden" name="<%=frmEmpScheduleChange.fieldNames[frmEmpScheduleChange.FRM_FIELD_APPROVAL_DATE_LEVEL2]%>" value="<%=empScheduleChange.getApprovalDateLevel2() == null ? "-" : empScheduleChange.getApprovalDateLevel2()%>" class="elemenForm"></td>
                                                                      
                                                                      </tr>
                                                                      <tr>
                                                                        <td colspan="4" style=" border-bottom:dashed">&nbsp;</td>
                                                                      </tr>
                                                                    </table>
                                                                       <% } %>  
                                                                      
                                                                      
                                                                      
                                                                      
                                                                      
                                                                      
                                                           
                                                          </td>
                                                        </tr>
                                                        <tr>
                                                          <td height="50%" colspan="4"><p>&nbsp;</p></td>
                                                        </tr>
                                                      </table>
                                                     </div>
                                           
                                                 
                                                 </td>
                                              </tr>
                                              
                                        
                                            </table>                    
                                                                  
                                                                      
                                                                      
                                            
                                          </td>										  
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" width="17%">&nbsp;</td>
                                          <td height="8" colspan="2" width="83%">&nbsp; 
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
                                <%@include file="../../../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
    </body>
    <!-- #BeginEditable "script" -->
    <script language="JavaScript">
                //var oBody = document.body;
                //var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>

