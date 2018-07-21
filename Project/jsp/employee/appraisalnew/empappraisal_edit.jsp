 
<% 
/* 
 * Page Name  		:  empappraisal_edit.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: lkarunia 
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
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.appraisal.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.appraisal.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_APPRAISAL, AppObjInfo.OBJ_PERFORMANCE_APPRAISAL); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%

	CtrlAppraisalMain ctrlAppraisalMain = new CtrlAppraisalMain(request);
	long oidAppraisalMain = FRMQueryString.requestLong(request, "employee_appraisal_oid");
	int prevCommand = FRMQueryString.requestInt(request, "prev_command");
	int iCommand = FRMQueryString.requestCommand(request);
	int start = FRMQueryString.requestInt(request,"start");
        
	
//	out.print("oidAppraisalMain : "+oidAppraisalMain);
	String errMsg = "";
        int iErrCode = FRMMessage.ERR_NONE;
	String whereClause = "";
	String orderClause = "";

	ControlLine ctrLine = new ControlLine();
        
        Employee employee = new Employee();
        Employee assessor = new Employee();
        Department empDep = new Department();
        Department assDep = new Department();
        Position empPos = new Position();
        Position assPos = new Position();
        Level empLevel = new Level();
        Level assLevel = new Level();
        
        AppraisalMain appraisalMain = new AppraisalMain();
        
        try{
            appraisalMain = PstAppraisalMain.fetchExc(oidAppraisalMain);
        }catch(Exception ex){}
        
        Date dateAssPos = new Date();
        Date dateJoinHotel = new Date();
        Date dateAss = new Date();
        Date dateAssLast = new Date();
        Date dateAssNext = new Date();
        
        if(iCommand != Command.ADD){
            dateAssPos = FRMQueryString.requestDate(request, FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_ASS_POSITION]);
            dateJoinHotel = FRMQueryString.requestDate(request, FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_JOINED_HOTEL]);
            dateAss = FRMQueryString.requestDate(request, FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_OF_ASS]);
            dateAssLast = FRMQueryString.requestDate(request, FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_OF_LAST_ASS]);
            dateAssNext = FRMQueryString.requestDate(request, FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_OF_NEXT_ASS]);
        }
        long empOid = FRMQueryString.requestLong(request, FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_EMPLOYEE_ID]);
                
        long assOid = FRMQueryString.requestLong(request, FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_ASSESSOR_ID]);
        try{
            employee = PstEmployee.fetchExc(empOid);
            assessor = PstEmployee.fetchExc(assOid);
        }catch(Exception ex){}
        
        boolean isSuccessSaved = false;
        boolean isReq = false;
        
        if(iCommand==Command.SAVE){
            appraisalMain.setDateAssumedPosition(dateAssPos);
            appraisalMain.setDateJoinedHotel(dateJoinHotel);
            appraisalMain.setDateOfAssessment(dateAss);
            appraisalMain.setDateOfLastAssessment(dateAssLast);
            appraisalMain.setDateOfNextAssessment(dateAssNext);
            if(appraisalMain.getOID()>0){
                try{
                    oidAppraisalMain = PstAppraisalMain.updateExc(appraisalMain);
                    if(oidAppraisalMain>0){
                        errMsg = "appraisal main has been saved";
                        isSuccessSaved = true;
                    }
                }catch(Exception ex){
                    errMsg = "<font color=red>appraisal main failed to saved</font>";
                }
            }else{
                if(employee.getOID()>0 && assessor.getOID()>0){
                    appraisalMain.setEmployeeId(employee.getOID());
                    appraisalMain.setEmpDepartmentId(employee.getDepartmentId());
                    appraisalMain.setEmpPositionId(employee.getPositionId());
                    appraisalMain.setEmpLevelId(employee.getLevelId());
                    appraisalMain.setAssesorId(assessor.getOID());
                    appraisalMain.setAssesorPositionId(assessor.getPositionId());

                    try{
                        oidAppraisalMain = PstAppraisalMain.insertExc(appraisalMain);
                        if(oidAppraisalMain>0){
                            isSuccessSaved = true;
                            errMsg = "appraisal main has been saved";
                        }
                    }catch(Exception ex){
                        errMsg = "<font color=red>appraisal main failed to saved</font>";
                    }
                }else{
                    isReq = true;
                    errMsg = "<font color=red>appraisal main failed to saved, some data required</font>";
                }
            }
            if(isSuccessSaved){
                iCommand = Command.EDIT;
            }else{
                iCommand = Command.ADD;
            }
        }
        
        if(appraisalMain.getOID()>0){
            try{
                employee = PstEmployee.fetchExc(appraisalMain.getEmployeeId());
                assessor = PstEmployee.fetchExc(appraisalMain.getAssesorId());
            }catch(Exception ex){}
        }
        try{

            empDep = PstDepartment.fetchExc(employee.getDepartmentId());
            assDep = PstDepartment.fetchExc(assessor.getDepartmentId());
            empPos = PstPosition.fetchExc(employee.getPositionId());
            assPos = PstPosition.fetchExc(assessor.getPositionId());
            empLevel = PstLevel.fetchExc(employee.getLevelId());
            assLevel = PstLevel.fetchExc(assessor.getLevelId());
        }catch(Exception ex){}
        
        if(iCommand == Command.ASK || iCommand == Command.DELETE){
            ctrlAppraisalMain.action(iCommand, oidAppraisalMain);
            errMsg = ctrlAppraisalMain.getMessage();
            PstAppraisal.deleteAppraisalByMain(oidAppraisalMain);
        }
      //  }
        
        
    
    //out.println(" | employee : " + employee.getFullName());
    //out.println(" | oidEmployee "+oidEmployee);
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Employee Assessment</title>
<script language="JavaScript">

	function cmdEdit(oid){ 
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.command.value="<%=String.valueOf(Command.EDIT)%>";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.action="empappraisal_edit.jsp";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.submit(); 
	} 

	function cmdAdd(){
                document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.employee_appraisal_oid.value="0";
                document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_ASSESSOR_ID]%>.value="";
                document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_ASS_POSITION]%>.value="";
                document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_JOINED_HOTEL]%>.value="";
                document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_OF_ASS]%>.value="";
                document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_OF_LAST_ASS]%>.value="";
                document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_OF_NEXT_ASS]%>.value="";
                document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_EMPLOYEE_ID]%>.value="";
                document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.command.value="<%=String.valueOf(Command.ADD)%>";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.action="empappraisal_edit.jsp";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.submit();
	}
         
	function cmdSave(){
                document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.command.value="<%=String.valueOf(Command.SAVE)%>"; 
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.action="empappraisal_edit.jsp";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.submit();
	}

	function cmdAsk(oid){
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.command.value="<%=String.valueOf(Command.ASK)%>"; 
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.action="empappraisal_edit.jsp";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.submit();
	} 

	function cmdConfirmDelete(oid){
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.command.value="<%=String.valueOf(Command.DELETE)%>";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.action="empappraisal_edit.jsp"; 
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.submit();
	}  

	function cmdBack(){
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.command.value="<%=String.valueOf(Command.LIST)%>"; 
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.action="empappraisal_list.jsp";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.submit();
	}
	
	function chgDepart(){
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.command.value="<%=String.valueOf(iCommand)%>"; 
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.action="empappraisal_edit.jsp";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.submit();
	}	
	
	function cmdSearchEmp(){
            window.open("empdopsearch.jsp?emp_number=" + document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.EMP_NUMBER.value + "&emp_fullname=" + document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.EMP_FULLNAME.value + "&emp_department=" + document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.EMP_DEPARTMENT.value, null, "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	
   

	function cmdPrint(oidAppMain){
          //  alert("CREATE APPRAISAL");
            var url = "<%=reportroot%>/com.dimata.harisma.report.appraisal.AppraisalDetailPdf?appraisalOid="+oidAppMain+"&imagesRoot=<%=imagesroot%>";
            window.open(url);  
	}



        function cmdSearchEmp(){
            window.open("<%=approot%>/employee/search/searchEmp.jsp?formName=<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>&empPathId=<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_EMPLOYEE_ID]%>&firstName=EM_", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
        }
        function cmdSearchAss(){
            window.open("<%=approot%>/employee/search/searchEmp.jsp?formName=<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>&empPathId=<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_ASSESSOR_ID]%>&firstName=ASS_", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
        }

	function cmdClearSearchEmp() {
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_EMPLOYEE_ID]%>.value = "";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.EM_EMP_FULLNAME.value = "";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.EM_EMP_NUMBER.value = "";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.EM_EMP_POSITION.value = "";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.EM_EMP_COMMENCING_DATE.value = "";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.EM_EMP_DEPARTMENT.value = "";
	}
	function cmdClearSearchAss() {
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_ASSESSOR_ID]%>.value = "";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.ASS_EMP_FULLNAME.value = "";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.ASS_EMP_NUMBER.value = "";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.ASS_EMP_POSITION.value = "";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.ASS_EMP_COMMENCING_DATE.value = "";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.ASS_EMP_DEPARTMENT.value = "";
	}

//-------------------------- for Calendar -------------------------
    function getThn(){
        <%
         out.println(ControlDatePopup.writeDateCaller(FrmAppraisalMain.FRM_APPRAISAL_MAIN,FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_ASS_POSITION]));
         out.println(ControlDatePopup.writeDateCaller(FrmAppraisalMain.FRM_APPRAISAL_MAIN,FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_JOINED_HOTEL]));
         out.println(ControlDatePopup.writeDateCaller(FrmAppraisalMain.FRM_APPRAISAL_MAIN,FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_OF_ASS]));
         out.println(ControlDatePopup.writeDateCaller(FrmAppraisalMain.FRM_APPRAISAL_MAIN,FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_OF_LAST_ASS]));
         out.println(ControlDatePopup.writeDateCaller(FrmAppraisalMain.FRM_APPRAISAL_MAIN,FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_OF_NEXT_ASS]));
         %>
    }
    
    function hideObjectForDate(){
        <%
        %>
    }

    function showObjectForDate(){
        <%
        %>
    } 
    function cmdClearDateLast(){
        document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_OF_LAST_ASS]%>.value = "";
    } 
    function cmdClearDateNext(){
        document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_OF_NEXT_ASS]%>.value = "";
    } 


//-------------------------------------------

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
<link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #EndEditable -->
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<!-- #BeginEditable "stylestab" -->  
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
	<%if(appraisalMain.getOID()==0){%>
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.EMP_DEPARTMENT.style.visibility="hidden";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.chg_appraisor.style.visibility="hidden";
	<%}%>
}

function showObjectForMenu(){
	<%if(appraisalMain.getOID()==0){%>
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.EMP_DEPARTMENT.style.visibility="";
		document.<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>.chg_appraisor.style.visibility="";
	<%}%>
}


</SCRIPT>
<!-- #EndEditable -->
</head>  

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
<!-- Untuk Calendar-->
<%=ControlDatePopup.writeTable(approot)%>
<!-- End Calendar-->
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF">
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
    <td bgcolor="#9BC1FF"  ID="MAINMENU" valign="middle" height="15"> <!-- #BeginEditable "menumain" --> 
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
                  <td>
                        <font color="#FF6600" face="Arial"><strong>
                          <!-- #BeginEditable "contenttitle" -->Employee 
  &gt; Employee Assessment <!-- #EndEditable --> 
                        </strong></font>
                  </td>
                </tr>
                <tr> 
                 <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                          <tr> 
                            <td valign="top"> <!-- #BeginEditable "content" --> 
                              <form name="<%=FrmAppraisalMain.FRM_APPRAISAL_MAIN%>" method="post" action="">
                                <input type="hidden" name="command" value="">
                                <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                <input type="hidden" name="prev_command" value="<%=String.valueOf(iCommand)%>">
                                <input type="hidden" name="employee_appraisal_oid" value="<%=String.valueOf(oidAppraisalMain)%>">
                                <table width="100%" border="0" cellspacing="1" cellpadding="1" height="504">
                                  <tr> 
                                    <td valign="top" height="551"> 
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <% if(oidAppraisalMain != 0){%>
                                        <tr> 
                                          <td> 
                                            <table  border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td  valign="top" height="20" width="104"> 
                                                  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="txtalign">
                                                    <tr> 
                                                      <td   valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/active_left.jpg" width="12" height="29"></td>
                                                      <td   valign="middle" background="<%=approot%>/images/tab/active_bg.jpg" nowrap> 
                                                        <div align="center" class="tablink">Employee</div>
                                                      </td>
                                                      <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/active_right.jpg" width="12" height="29"></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                                <td  valign="top" height="20" width="158"> 
                                                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                    <tr> 
                                                      <td   valign="top" align="left" width="10"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                      <td   valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap > 
                                                        <div align="center" class="tablink"><a href="appraisalForm.jsp?employee_appraisal_oid=<%=String.valueOf(oidAppraisalMain)%>" class="tablink">
                                                        Assessment</a></div>
                                                      </td>
                                                      <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <%}%>
                                        <tr> 
                                          <td> 
                                            <table width="100%" border="0" cellpadding="1" cellspacing="1"  style="background-color:<%=bgColorContent%>; ">
                                                <tr><td width="100%">
                                            <!--//////////////////////////////////////////-->
                                            <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellpadding="1" cellspacing="1" class="tablecolor">
                                                <tr><td width="100%">
                                                        <b>Employee :</b>
                                                </td></tr>
                                                <tr>
                                                    <td width="100%">
                                                        <!--Start Data Employee -->
                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <input type="hidden" name="<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=String.valueOf(appraisalMain.getOID()>0?appraisalMain.getEmployeeId():employee.getOID())%>" class="formElemen">
                                                      <input type="hidden" name="<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_EMP_DEPARTMENT_ID]%>" value="<%=String.valueOf(appraisalMain.getOID()>0?appraisalMain.getEmpDepartmentId():employee.getDepartmentId())%>" class="formElemen">
                                                      <input type="hidden" name="<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_EMP_LEVEL_ID]%>" value="<%=String.valueOf(appraisalMain.getOID()>0?appraisalMain.getEmpLevelId():employee.getLevelId())%>" class="formElemen">
                                                      <input type="hidden" name="<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_EMP_POSITION_ID]%>" value="<%=String.valueOf(appraisalMain.getOID()>0?appraisalMain.getEmpPositionId():employee.getPositionId())%>" class="formElemen">
                                                      <td width="12%" nowrap> 
                                                        <div align="left">Name</div>
                                                      </td>
                                                      <td width="29%" nowrap> 
                                                        : 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%= employee.getFullName() %> 
                                                        <% } else {%>
                                                        <input type="text" name="EM_EMP_FULLNAME" size="30"  readonly>
                                                        <%if(isReq){out.println("<font color=red>* required</font>");}%>
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
                                                        <input type="text" name="EM_EMP_NUMBER" readonly>
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
                                                        <%= empPos.getPosition() %> 
                                                        <% } else {%>
                                                        <input type="text" name="EM_EMP_POSITION" readonly size="30">
                                                        <% } %>
                                                      </td>
                                                      <td width="10%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                      </td>
                                                      <td width="49%"> : 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%= empDep.getDepartment() %> 
                                                        <% } else {%>
                                                        <%-- <input type="text" name="EMP_DEPARTMENT"> --%>
                                                        <input type="text" name="EM_EMP_DEPARTMENT" readonly size="40">
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
                                                        <%=(employee.getOID()>0?Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy"):"") %> 
                                                        <% } else {%>
                                                        <input type="text" name="EM_EMP_COMMENCING_DATE" readonly>
                                                        <% } %>
                                                      </td>
                                                      <td width="10%" nowrap> 
                                                        <div align="left"></div>
                                                      </td>
                                                      <td width="49%"> 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <% } else {%>
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
                                                        <!--End Data Employee -->
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td width="100%">
                                                        <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                            <tr>
                                                                <td width="12%" nowrap> 
                                                                    <div align="left">Date Assumed Position</div>
                                                                </td>
                                                                <td width="40%" nowrap> : 
                                                                    <%=ControlDatePopup.writeDate(FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_ASS_POSITION],appraisalMain.getDateAssumedPosition())%>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td width="12%" nowrap> 
                                                                    <div align="left">Date Joined Hotel</div>
                                                                </td>
                                                                <td width="88%" nowrap> : 
                                                                    <%=ControlDatePopup.writeDate(FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_JOINED_HOTEL],appraisalMain.getDateJoinedHotel())%>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td width="100%">
                                                        <hr>
                                                        <b>Assessor :</b>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td width="100%">
                                                        <!--Start Data Assessor-->
                                                        <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <input type="hidden" name="<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_ASSESSOR_ID]%>" value="<%=String.valueOf(appraisalMain.getOID()>0?appraisalMain.getAssesorId():assessor.getOID())%>" class="formElemen">
                                                      <input type="hidden" name="<%=FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_ASS_POSITION_ID]%>" value="<%=String.valueOf(appraisalMain.getOID()>0?appraisalMain.getAssesorPositionId():assessor.getPositionId())%>" class="formElemen">
                                                      <td width="12%" nowrap> 
                                                        <div align="left">Name</div>
                                                      </td>
                                                      <td width="29%" nowrap> 
                                                        : 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%= assessor.getFullName() %> 
                                                        <% } else {%>
                                                        <input type="text" name="ASS_EMP_FULLNAME" size="30"  readonly>
                                                        <%if(isReq){out.println("<font color=red>* required</font>");}%>
                                                        <% } %>
                                                      </td>
                                                      <td width="10%" nowrap> 
                                                        <div align="left">Employee 
                                                          Number</div>
                                                      </td>
                                                      <td width="49%"> : 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%= assessor.getEmployeeNum() %> 
                                                        <% } else {%>
                                                        <input type="text" name="ASS_EMP_NUMBER" readonly>
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
                                                        <%= assPos.getPosition() %> 
                                                        <% } else {%>
                                                        <input type="text" name="ASS_EMP_POSITION" readonly size="30">
                                                        <% } %>
                                                      </td>
                                                      <td width="10%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                      </td>
                                                      <td width="49%"> : 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <%= assDep.getDepartment() %> 
                                                        <% } else {%>
                                                        <%-- <input type="text" name="EMP_DEPARTMENT"> --%>
                                                        <input type="text" name="ASS_EMP_DEPARTMENT" readonly size="40">
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
                                                        <%= assessor.getOID()>0?Formater.formatDate(assessor.getCommencingDate(), "dd MMMM yyyy"):"" %> 
                                                        <% } else {%>
                                                        <input type="text" name="ASS_EMP_COMMENCING_DATE" readonly>
                                                        <% } %>
                                                      </td>
                                                      <td width="10%" nowrap> 
                                                        <div align="left"></div>
                                                      </td>
                                                      <td width="49%"> 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <% } else {%>
                                                        <table cellpadding="0" cellspacing="0" border="0">
                                                          <tr> 
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                            <td width="15"><a href="javascript:cmdSearchAss()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image101" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                            <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td class="command" nowrap width="99"> 
                                                              <div align="left"><a href="javascript:cmdSearchAss()">Search 
                                                                Assessor</a></div>
                                                            </td>
                                                            <td width="10"><img src="<%=approot%>/images/spacer.gif" width="10" height="4"></td>
                                                            <td width="15"><a href="javascript:cmdClearSearchAss()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnCancelOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnCancel.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                            <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td class="command" nowrap width="99"> 
                                                              <div align="left"><a href="javascript:cmdClearSearchAss()">Clear 
                                                                Search </a></div>
                                                            </td>
                                                          </tr>
                                                        </table>
							</td>
                                                      <% } %>
                                                    </tr>
                                                  </table>
                                                        <!--Employee Data Assessor-->
                                                    </td>
                                                </tr>
                                                <tr><td>
                                                    <hr>
                                                </td></tr>
                                                <tr><td>
                                                    <!--Start detail data-->
                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                        <tr>
                                                            <td width="12%" nowrap> 
                                                                <div align="left">Date of Assessment</div>
                                                            </td>
                                                            <td width="88%" nowrap>:
                                                            <%//=Formater.formatDate(appraisalMain.getDateOfAssessment(),"yyyy MM dd")%>
                                                                <%=ControlDatePopup.writeDate(FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_OF_ASS],appraisalMain.getDateOfAssessment())%>
                                                               <!-- <a href="javascript:cmdClearDateLast()">Clear Date</a>-->
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td width="12%" nowrap> 
                                                                <div align="left">Date of Last Assessment</div>
                                                            </td>
                                                            <td width="88%" nowrap>:
                                                                <%=ControlDatePopup.writeDate(FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_OF_LAST_ASS],appraisalMain.getDateOfLastAssessment())%>
                                                                <a href="javascript:cmdClearDateLast()">Clear Date</a>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td width="12%" nowrap> 
                                                                <div align="left">Date of Next Assessment</div>
                                                            </td>
                                                            <td width="88%" nowrap>:
                                                                <%=ControlDatePopup.writeDate(FrmAppraisalMain.fieldNames[FrmAppraisalMain.FRM_FIELD_DATE_OF_NEXT_ASS],appraisalMain.getDateOfNextAssessment())%>
                                                                <a href="javascript:cmdClearDateNext()">Clear Date</a>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <!--End detail data-->
                                                </td></tr>
                                            </table>
                                            <!--//////////////////////////////////////////-->
                                            </td></tr>
                                            <tr>
                                                <td>
                                                    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tabbg">
                                                <tr><td width="100%">
                                                  <%
                                                    ctrLine.setLocationImg(approot+"/images");
                                                    ctrLine.initDefault();
                                                    ctrLine.setTableWidth("80%");
                                                    String scomDel = "javascript:cmdAsk('"+oidAppraisalMain+"')";
                                                    String sconDelCom = "javascript:cmdConfirmDelete('"+oidAppraisalMain+"')";
                                                    String scancel = "javascript:cmdEdit('"+oidAppraisalMain+"')";
                                                    ctrLine.setBackCaption("Back to List");
                                                    ctrLine.setCommandStyle("buttonlink");
                                                    ctrLine.setBackCaption("Back to List Assessment Main");
                                                    ctrLine.setSaveCaption("Save Assessment Main");
                                                    ctrLine.setConfirmDelCaption("Yes Delete Assessment Main");
                                                    ctrLine.setDeleteCaption("Delete Assessment Main");

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

                                                    if(iCommand == Command.ASK)
                                                            ctrLine.setDeleteQuestion(errMsg);
                                                    %>
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%>
                                                  <%//if (privPrint){%>
                                      <table width="100%" border="0">
                                          <tr>
                                              <td width="24"><a href="javascript:cmdPrint('<%=String.valueOf(oidAppraisalMain)%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                              <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                              <td nowrap><a href="javascript:cmdPrint('<%=String.valueOf(oidAppraisalMain)%>')" class="command" style="text-decoration:none">Print Assessment</a></td>
                                          </tr>
                                      </table>
                                      <%//}%>
                                      </td></tr>
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
