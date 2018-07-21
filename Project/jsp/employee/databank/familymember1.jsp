<% 
/* 
 * Page Name  		:  familymember.jsp
 * Created on 		:  [25-9-2002] [9.00] AM
 * 
 * @author  		: lkarunia 
 * @version  		: 01 
 */

/*******************************************************************
 * Page Description 	: this page represent family member of employee 
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- Jsp Block -->

<%

int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidFamilyMember = FRMQueryString.requestLong(request, "family_member_oid");
long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");

//variable declaration
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID]+ " = "+oidEmployee;
String orderClause = "";

CtrlFamilyMember ctrlFamilyMember = new CtrlFamilyMember(request);
ControlLine ctrLine = new ControlLine();
Vector listFamilyMember = new Vector(1,1);


Vector vctMember = new Vector(1,1);
if(iCommand==Command.SAVE){
	for(int i=0; i<4; i++){
		String famName = FRMQueryString.requestString(request, FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_FULL_NAME]+"_"+i);
		String famRelation = FRMQueryString.requestString(request, FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_RELATIONSHIP]+"_"+i);
		boolean famGuaranted = FRMQueryString.requestBoolean(request, FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_GUARANTEED]+"_"+i);
		boolean famIgnore = FRMQueryString.requestBoolean(request, FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_IGNORE_BIRTH]+"_"+i);
		Date famBirth = FRMQueryString.requestDate(request, FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_BIRTH_DATE]+"_"+i);
		String famJob = FRMQueryString.requestString(request, FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_JOB]+"_"+i);
		String famAddress = FRMQueryString.requestString(request, FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_ADDRESS]+"_"+i);
		
		FamilyMember famMember = new FamilyMember();
		famMember.setFullName(famName);
		famMember.setRelationship(famRelation);
		famMember.setGuaranteed(famGuaranted);
		famMember.setBirthDate(famBirth);
		famMember.setIgnoreBirth(famIgnore);		
		famMember.setJob(famJob);
		famMember.setAddress(famAddress);
		
		vctMember.add(famMember);
		
	}
}


//out.println(vctMember);
//for(int i=0; i<4; i++){
//	FamilyMember famMember = (FamilyMember)vctMember.get(i);
//	out.println("i :"+i);
	//out.println(famMember.getFullName());
	//out.println(famMember.getRelationship());
	//out.println(famMember.getGuaranteed());
	//out.println(famMember.getBirthDate());
	//out.println(famMember.getJob());
	//out.println(famMember.getAddress());
//}

//switch statement 
iErrCode = ctrlFamilyMember.action(iCommand , oidEmployee, vctMember);//oidFamilyMember, oidEmployee);
// end switch
FrmFamilyMember FrmFamilyMember = ctrlFamilyMember.getForm();

msgString =  ctrlFamilyMember.getMessage();

whereClause = PstFamilyMember.fieldNames[PstFamilyMember.FLD_EMPLOYEE_ID]+"="+oidEmployee;
orderClause = PstFamilyMember.fieldNames[PstFamilyMember.FLD_RELATIONSHIP]+","+PstFamilyMember.fieldNames[PstFamilyMember.FLD_BIRTH_DATE]+" ASC";

vctMember = PstFamilyMember.list(0, 0, whereClause , orderClause);
//out.println(oidEmployee);
//out.println(vctMember.size());

%>

<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Family Member</title>
<script language="JavaScript">
<!--



function cmdAdd(){
	document.FrmFamilyMember.family_member_oid.value="0";
	document.FrmFamilyMember.command.value="<%=Command.ADD%>";
	document.FrmFamilyMember.prev_command.value="<%=prevCommand%>";
	document.FrmFamilyMember.action="familymember.jsp";
	document.FrmFamilyMember.submit();
}

function cmdAsk(oidFamilyMember){
	document.FrmFamilyMember.family_member_oid.value=oidFamilyMember;
	document.FrmFamilyMember.command.value="<%=Command.ASK%>";
	document.FrmFamilyMember.prev_command.value="<%=prevCommand%>";
	document.FrmFamilyMember.action="familymember.jsp";
	document.FrmFamilyMember.submit();
}

function cmdConfirmDelete(oidFamilyMember){
	document.FrmFamilyMember.family_member_oid.value=oidFamilyMember;
	document.FrmFamilyMember.command.value="<%=Command.DELETE%>";
	document.FrmFamilyMember.prev_command.value="<%=prevCommand%>";
	document.FrmFamilyMember.action="familymember.jsp";
	document.FrmFamilyMember.submit();
}
function cmdSave(){
	document.FrmFamilyMember.command.value="<%=Command.SAVE%>";
	document.FrmFamilyMember.prev_command.value="<%=prevCommand%>";
	document.FrmFamilyMember.action="familymember.jsp";
	document.FrmFamilyMember.submit();
	}

function cmdEdit(oidFamilyMember){
	document.FrmFamilyMember.family_member_oid.value=oidFamilyMember;
	document.FrmFamilyMember.command.value="<%=Command.EDIT%>";
	document.FrmFamilyMember.prev_command.value="<%=prevCommand%>";
	document.FrmFamilyMember.action="familymember.jsp";
	document.FrmFamilyMember.submit();
	}
	
function cmdBackEmp(empOID){
	document.FrmFamilyMember.employee_oid.value=empOID;
	document.FrmFamilyMember.command.value="<%=Command.EDIT%>";	
	document.FrmFamilyMember.action="employee_edit.jsp";
	document.FrmFamilyMember.submit();
	}

function cmdCancel(oidFamilyMember){
	document.FrmFamilyMember.family_member_oid.value=oidFamilyMember;
	document.FrmFamilyMember.command.value="<%=Command.EDIT%>";
	document.FrmFamilyMember.prev_command.value="<%=prevCommand%>";
	document.FrmFamilyMember.action="familymember.jsp";
	document.FrmFamilyMember.submit();
}

function cmdBack(){
	document.FrmFamilyMember.command.value="<%=Command.BACK%>";
	document.FrmFamilyMember.action="familymember.jsp";
	document.FrmFamilyMember.submit();
	}

function cmdListFirst(){
	document.FrmFamilyMember.command.value="<%=Command.FIRST%>";
	document.FrmFamilyMember.prev_command.value="<%=Command.FIRST%>";
	document.FrmFamilyMember.action="familymember.jsp";
	document.FrmFamilyMember.submit();
}

function cmdListPrev(){
	document.FrmFamilyMember.command.value="<%=Command.PREV%>";
	document.FrmFamilyMember.prev_command.value="<%=Command.PREV%>";
	document.FrmFamilyMember.action="familymember.jsp";
	document.FrmFamilyMember.submit();
	}

function cmdListNext(){
	document.FrmFamilyMember.command.value="<%=Command.NEXT%>";
	document.FrmFamilyMember.prev_command.value="<%=Command.NEXT%>";
	document.FrmFamilyMember.action="familymember.jsp";
	document.FrmFamilyMember.submit();
}

function cmdListLast(){
	document.FrmFamilyMember.command.value="<%=Command.LAST%>";
	document.FrmFamilyMember.prev_command.value="<%=Command.LAST%>";
	document.FrmFamilyMember.action="familymember.jsp";
	document.FrmFamilyMember.submit();
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
<!-- #EndEditable -->
</head>  

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF">
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Family Member<!-- #EndEditable --> </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                          <tr> 
                            <td valign="top"> <!-- #BeginEditable "content" --> 
                              <form name="FrmFamilyMember" method="post" action="">
                                <input type="hidden" name="command" value="<%=iCommand%>">
                                <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                <input type="hidden" name="start" value="<%=start%>">
                                <input type="hidden" name="family_member_oid" value="<%=oidFamilyMember%>">
                                <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                  <% if(oidEmployee != 0){%>
                                  <tr> 
                                    <td> <br>
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2" height="26">
                                        <tr> 
                                          <td width="2%" bgcolor="#FFFFFF">&nbsp;</td>
                                          <td width="11%" nowrap bgcolor="#0066CC"> 
                                            <div align="center" class="tablink"><a href="javascript:cmdBackEmp('<%=oidEmployee%>')" class="tablink">Personal 
                                              Data</a></div>
                                          </td>
                                          <td width="12%" nowrap bgcolor="#66CCFF"> 
                                            <div align="center"  class="tablink">Family 
                                              Member</div>
                                          </td>
                                          <td width="9%" nowrap bgcolor="#0066CC"> 
                                            <div align="center"  class="tablink"><a href="emplanguage.jsp?employee_oid=<%=oidEmployee%>" class="tablink">Language</a></div>
                                          </td>
                                          <td width="10%" nowrap bgcolor="#0066CC"> 
                                            <div align="center"  class="tablink"><a href="empeducation.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.EDUCATION) %></a></div>
                                          </td>
                                          <td width="9%" nowrap bgcolor="#0066CC"> 
                                            <div align="center"  class="tablink"><a href="experience.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><span class="tablink"><%=dictionaryD.getWord(I_Dictionary.EXPERIENCE) %></span></a></div>
                                          </td>
                                          <td width="10%" nowrap bgcolor="#0066CC"> 
                                            <div align="center"><font class="tablink" ><span class="tablink"><a href="careerpath.jsp?employee_oid=<%=oidEmployee%>" class="tablink">Career 
                                              Path</a></span></font></div>
                                          </td>
                                          <td width="37%">&nbsp;</td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
                                  <%}%>
                                  <tr> 
                                    <td class="tablecolor"> 
                                      <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                        <tr> 
                                          <td valign="top"> 
                                            <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" class="tabbg">
                                              <tr align="left" valign="top"> 
                                                <td colspan="3"> 
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr align="left" valign="top"> 
                                                      <td height="14" valign="middle" colspan="3" class="listedittitle">&nbsp;</td>
                                                    </tr>
                                                    <% if(oidEmployee != 0){
														  		Employee employee = new Employee();
														  		try{
														  			 employee = PstEmployee.fetchExc(oidEmployee);
																}catch(Exception exc){
																	 employee = new 	Employee();
																}
														  %>
                                                    <tr align="left" valign="top"> 
                                                      <td height="73" valign="middle" colspan="3" class="listedittitle"> 
                                                        <table width="100%" border="0" cellspacing="2" cellpadding="1">
                                                          <tr> 
                                                            <td width="17%">Payroll 
                                                              Number </td>
                                                            <td width="3%">:</td>
                                                            <td width="80%"><%=employee.getEmployeeNum()%></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="17%">Name</td>
                                                            <td width="3%">:</td>
                                                            <td width="80%"><%=employee.getFullName()%></td>
                                                          </tr>
                                                          <% Department department = new Department();
																   try{
																		department = PstDepartment.fetchExc(employee.getDepartmentId());
																   }catch(Exception exc){
																   		department = new Department();
																   }
																%>
                                                          <tr> 
                                                            <td width="17%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                            <td width="3%">:</td>
                                                            <td width="80%"><%=department.getDepartment()%></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="17%">Address</td>
                                                            <td width="3%">:</td>
                                                            <td width="80%"><%=employee.getAddress()%></td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td height="14" valign="middle" colspan="3" class="listedittitle">&nbsp;</td>
                                                    </tr>
                                                    <%}%>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td valign="middle" colspan="3"> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <td width="100%" colspan="2"> 
                                                        <table border="0" cellspacing="2" cellpadding="2" width="100%">
                                                          <tr align="left" valign="top"> 
                                                            <td valign="top" class="listtitle" colspan="2" height="20">Family 
                                                              Member Editor</td>
                                                          </tr>
                                                          <tr align="left" valign="top"> 
                                                            <td valign="top" class="comment" colspan="2"> 
                                                              <table width="100%" border="0" cellspacing="1" cellpadding="1" class="listgen">
                                                                <tr class="listgentitle"> 
                                                                  <td width="2%"> 
                                                                    <div align="center"><b>No</b></div>
                                                                  </td>
                                                                  <td width="24%"> 
                                                                    <div align="center"><b>Full 
                                                                      Name</b></div>
                                                                  </td>
                                                                  <td width="8%"> 
                                                                    <div align="center"><b>Relationship</b></div>
                                                                  </td>
                                                                  <td width="6%"> 
                                                                    <div align="center"><b>Guaranted</b></div>
                                                                  </td>
                                                                  <td width="14%"> 
                                                                    <div align="center"><b>Date 
                                                                      Of Birth 
                                                                      </b></div>
                                                                  </td>
                                                                  <td width="19%"> 
                                                                    <div align="center"><b>Job</b></div>
                                                                  </td>
                                                                  <td width="27%"> 
                                                                    <div align="center"><b>Address</b></div>
                                                                  </td>
                                                                </tr>
                                                                <tr class="listgensell"> 
                                                                  <td width="2%"> 
                                                                    <div align="center">1</div>
                                                                  </td>
                                                                  <td width="24%"> 
                                                                    <%
																  FamilyMember fam1 = new FamilyMember();
																  if(vctMember!=null && vctMember.size()>0){
																  	fam1 = (FamilyMember)vctMember.get(0);
																  }%>
                                                                    <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_FULL_NAME] %>_0"  value="<%= fam1.getFullName() %>" class="elemenForm" size="30">
                                                                  </td>
                                                                  <td width="8%"><%= ControlCombo.draw(FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_RELATIONSHIP]+"_0","formElemen",null, ""+fam1.getRelationship(), PstFamilyMember.getRelation(), PstFamilyMember.getRelation()) %></td>
                                                                  <td width="6%"> 
                                                                    <input type="checkbox" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_GUARANTEED]%>_0" value="1" <%if(fam1.getGuaranteed()){%>checked<%}%>>
                                                                    Yes </td>
                                                                  <td width="14%" nowrap><%=	ControlDate.drawDate(FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_BIRTH_DATE]+"_0", fam1.getBirthDate(),"formElemen", 0,-50) %> 
                                                                    <input type="checkbox" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_IGNORE_BIRTH]%>_0" value="1" <%if(fam1.getIgnoreBirth()){%>checked<%}%>>
                                                                    Ignore </td>
                                                                  <td width="19%"> 
                                                                    <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_JOB] %>_0"  value="<%= fam1.getJob() %>" class="elemenForm" size="30">
                                                                  </td>
                                                                  <td width="27%"> 
                                                                    <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_ADDRESS] %>_0"  value="<%= fam1.getAddress() %>" class="elemenForm" size="30">
                                                                  </td>
                                                                </tr>
                                                                <tr class="listgensell"> 
                                                                  <td width="2%"> 
                                                                    <div align="center">2</div>
                                                                  </td>
                                                                  <td width="24%"> 
                                                                    <%
																  FamilyMember fam2 = new FamilyMember();
																  if(vctMember!=null && vctMember.size()>0 && vctMember.size()>=2){
																  	fam2 = (FamilyMember)vctMember.get(1);
																  }%>
                                                                    <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_FULL_NAME] %>_1"  value="<%= fam2.getFullName() %>" class="elemenForm" size="30">
                                                                  </td>
                                                                  <td width="8%"><%= ControlCombo.draw(FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_RELATIONSHIP]+"_1","formElemen",null, ""+fam2.getRelationship(), PstFamilyMember.getRelation(), PstFamilyMember.getRelation()) %></td>
                                                                  <td width="6%"> 
                                                                    <input type="checkbox" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_GUARANTEED]%>_1" value="1" <%if(fam2.getGuaranteed()){%>checked<%}%>>
                                                                    Yes </td>
                                                                  <td width="14%" nowrap><%=	ControlDate.drawDate(FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_BIRTH_DATE]+"_1", fam2.getBirthDate(),"formElemen", 0,-50) %> 
                                                                    <input type="checkbox" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_IGNORE_BIRTH]%>_1" value="1" <%if(fam2.getIgnoreBirth()){%>checked<%}%>>
                                                                    Ignore </td>
                                                                  <td width="19%"> 
                                                                    <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_JOB] %>_1"  value="<%= fam2.getJob() %>" class="elemenForm" size="30">
                                                                  </td>
                                                                  <td width="27%"> 
                                                                    <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_ADDRESS] %>_1"  value="<%= fam2.getAddress() %>" class="elemenForm" size="30">
                                                                  </td>
                                                                </tr>
                                                                <tr class="listgensell"> 
                                                                  <td width="2%"> 
                                                                    <div align="center">3</div>
                                                                  </td>
                                                                  <td width="24%"> 
                                                                    <%
																  FamilyMember fam3 = new FamilyMember();
																  if(vctMember!=null && vctMember.size()>0 && vctMember.size()>=3){
																  	fam3 = (FamilyMember)vctMember.get(2);
																  }%>
                                                                    <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_FULL_NAME] %>_2"  value="<%= fam3.getFullName() %>" class="elemenForm" size="30">
                                                                  </td>
                                                                  <td width="8%"><%= ControlCombo.draw(FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_RELATIONSHIP]+"_2","formElemen",null, ""+fam3.getRelationship(), PstFamilyMember.getRelation(), PstFamilyMember.getRelation()) %> 
                                                                  </td>
                                                                  <td width="6%"> 
                                                                    <input type="checkbox" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_GUARANTEED]%>_2" value="1" <%if(fam3.getGuaranteed()){%>checked<%}%>>
                                                                    Yes </td>
                                                                  <td width="14%" nowrap><%=	ControlDate.drawDate(FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_BIRTH_DATE]+"_2", fam3.getBirthDate(),"formElemen", 0,-50) %> 
                                                                    <input type="checkbox" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_IGNORE_BIRTH]%>_2" value="1" <%if(fam3.getIgnoreBirth()){%>checked<%}%>>
                                                                    Ignore </td>
                                                                  <td width="19%"> 
                                                                    <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_JOB] %>_2"  value="<%= fam3.getJob() %>" class="elemenForm" size="30">
                                                                  </td>
                                                                  <td width="27%"> 
                                                                    <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_ADDRESS] %>_2"  value="<%= fam3.getAddress() %>" class="elemenForm" size="30">
                                                                  </td>
                                                                </tr>
                                                                <tr class="listgensell"> 
                                                                  <td width="2%"> 
                                                                    <div align="center">4</div>
                                                                  </td>
                                                                  <td width="24%"> 
                                                                    <%
																  FamilyMember fam4 = new FamilyMember();
																  if(vctMember!=null && vctMember.size()>0 && vctMember.size()>=4){
																  	fam4 = (FamilyMember)vctMember.get(3);
																  }%>
                                                                    <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_FULL_NAME] %>_3"  value="<%= fam4.getFullName() %>" class="elemenForm" size="30">
                                                                  </td>
                                                                  <td width="8%"><%= ControlCombo.draw(FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_RELATIONSHIP]+"_3","formElemen",null, ""+fam4.getRelationship(), PstFamilyMember.getRelation(), PstFamilyMember.getRelation()) %></td>
                                                                  <td width="6%"> 
                                                                    <input type="checkbox" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_GUARANTEED]%>_3" value="1" <%if(fam4.getGuaranteed()){%>checked<%}%>>
                                                                    Yes </td>
                                                                  <td width="14%" nowrap><%=	ControlDate.drawDate(FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_BIRTH_DATE]+"_3", fam4.getBirthDate(),"formElemen", 0,-50) %> 
                                                                    <input type="checkbox" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_IGNORE_BIRTH]%>_3" value="1" <%if(fam4.getIgnoreBirth()){%>checked<%}%>>
                                                                    Ignore </td>
                                                                  <td width="19%"> 
                                                                    <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_JOB] %>_3"  value="<%= fam4.getJob() %>" class="elemenForm" size="30">
                                                                  </td>
                                                                  <td width="27%"> 
                                                                    <input type="text" name="<%=FrmFamilyMember.fieldNames[FrmFamilyMember.FRM_FIELD_ADDRESS] %>_3"  value="<%= fam4.getAddress() %>" class="elemenForm" size="30">
                                                                  </td>
                                                                </tr>
                                                              </table>
                                                            </td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top" > 
                                                      <td colspan="2" class="command"> 
                                                        <%
															ctrLine.setLocationImg(approot+"/images");
						
															ctrLine.initDefault();
															ctrLine.setTableWidth("80%");
															String scomDel = "javascript:cmdAsk('"+oidFamilyMember+"')";
															String sconDelCom = "javascript:cmdConfirmDelete('"+oidFamilyMember+"')";
															String scancel = "javascript:cmdEdit('"+oidFamilyMember+"')";
															ctrLine.setBackCaption("");
															ctrLine.setCommandStyle("buttonlink");
															ctrLine.setAddCaption("");
															ctrLine.setSaveCaption("Save Family Member");
															ctrLine.setDeleteCaption("");
															ctrLine.setConfirmDelCaption("");															
						
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
															
															iCommand=Command.EDIT;
															%>
                                                        <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="13%">&nbsp;</td>
                                                      <td width="87%">&nbsp;</td>
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
                              </form>
                              <!-- #EndEditable --> </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td >&nbsp;</td>
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
<script language="JavaScript">
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable --> 
<!-- #EndTemplate --></html>
