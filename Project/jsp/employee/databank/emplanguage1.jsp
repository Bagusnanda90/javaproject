
<% 
/* 
 * Page Name  		:  emplanguage.jsp
 * Created on 		:  [25-9-2002] [3.20] PM 
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
long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
long oidEmpLanguage = FRMQueryString.requestLong(request, "emp_language_oid");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID] + " = "+oidEmployee;
String orderClause = "";

Vector vctLanguage = new Vector(1,1);
if(iCommand==Command.SAVE){
	for(int i=0; i<3; i++){
		long language = FRMQueryString.requestLong(request, FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_LANGUAGE_ID]+"_"+i);
		int oral = FRMQueryString.requestInt(request, FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_ORAL]+"_"+i);
		int written = FRMQueryString.requestInt(request, FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_WRITTEN]+"_"+i);
		String desc = FRMQueryString.requestString(request, FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_DESCRIPTION]+"_"+i);
		
		EmpLanguage emLang = new EmpLanguage();
		emLang.setLanguageId(language);
		emLang.setOral(oral);
		emLang.setWritten(written);
		emLang.setDescription(desc);
	
		vctLanguage.add(emLang);
		
	}
}

//out.println(vctLanguage);

CtrlEmpLanguage ctrlEmpLanguage = new CtrlEmpLanguage(request);
ControlLine ctrLine = new ControlLine();
Vector listEmpLanguage = new Vector(1,1);

/*switch statement */
ctrlEmpLanguage.action(iCommand , oidEmployee, vctLanguage);
/* end switch*/

whereClause = PstEmpLanguage.fieldNames[PstEmpLanguage.FLD_EMPLOYEE_ID]+"="+oidEmployee;


//if(iCommand!=Command.SAVE){
	vctLanguage = new Vector(1,1);
	vctLanguage = PstEmpLanguage.list( 0, 0, whereClause, "");
	
	//out.println("vctLanguage : "+vctLanguage);
	
//}

%>
<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Language</title>
<script language="JavaScript">
<!--



function cmdAdd(){
	document.frmemplanguage.emp_language_oid.value="0";
	document.frmemplanguage.command.value="<%=Command.ADD%>";
	document.frmemplanguage.prev_command.value="<%=prevCommand%>";
	document.frmemplanguage.action="emplanguage.jsp";
	document.frmemplanguage.submit();
}

function cmdAsk(oidEmpLanguage){
	document.frmemplanguage.emp_language_oid.value=oidEmpLanguage;
	document.frmemplanguage.command.value="<%=Command.ASK%>";
	document.frmemplanguage.prev_command.value="<%=prevCommand%>";
	document.frmemplanguage.action="emplanguage.jsp";
	document.frmemplanguage.submit();
}

function cmdConfirmDelete(oidEmpLanguage){
	document.frmemplanguage.emp_language_oid.value=oidEmpLanguage;
	document.frmemplanguage.command.value="<%=Command.DELETE%>";
	document.frmemplanguage.prev_command.value="<%=prevCommand%>";
	document.frmemplanguage.action="emplanguage.jsp";
	document.frmemplanguage.submit();
}
function cmdSave(){
	document.frmemplanguage.command.value="<%=Command.SAVE%>";
	document.frmemplanguage.prev_command.value="<%=prevCommand%>";
	document.frmemplanguage.action="emplanguage.jsp";
	document.frmemplanguage.submit();
	}

function cmdEdit(oidEmpLanguage){
	document.frmemplanguage.emp_language_oid.value=oidEmpLanguage;
	document.frmemplanguage.command.value="<%=Command.EDIT%>";
	document.frmemplanguage.prev_command.value="<%=prevCommand%>";
	document.frmemplanguage.action="emplanguage.jsp";
	document.frmemplanguage.submit();
	}

function cmdCancel(oidEmpLanguage){
	document.frmemplanguage.emp_language_oid.value=oidEmpLanguage;
	document.frmemplanguage.command.value="<%=Command.EDIT%>";
	document.frmemplanguage.prev_command.value="<%=prevCommand%>";
	document.frmemplanguage.action="emplanguage.jsp";
	document.frmemplanguage.submit();
}

function cmdBack(){
	document.frmemplanguage.command.value="<%=Command.BACK%>";
	document.frmemplanguage.action="emplanguage.jsp";
	document.frmemplanguage.submit();
	}
	
function cmdBackEmp(empOID){
	document.frmemplanguage.employee_oid.value=empOID;
	document.frmemplanguage.command.value="<%=Command.EDIT%>";	
	document.frmemplanguage.action="employee_edit.jsp";
	document.frmemplanguage.submit();
	}

function cmdListFirst(){
	document.frmemplanguage.command.value="<%=Command.FIRST%>";
	document.frmemplanguage.prev_command.value="<%=Command.FIRST%>";
	document.frmemplanguage.action="emplanguage.jsp";
	document.frmemplanguage.submit();
}

function cmdListPrev(){
	document.frmemplanguage.command.value="<%=Command.PREV%>";
	document.frmemplanguage.prev_command.value="<%=Command.PREV%>";
	document.frmemplanguage.action="emplanguage.jsp";
	document.frmemplanguage.submit();
	}

function cmdListNext(){
	document.frmemplanguage.command.value="<%=Command.NEXT%>";
	document.frmemplanguage.prev_command.value="<%=Command.NEXT%>";
	document.frmemplanguage.action="emplanguage.jsp";
	document.frmemplanguage.submit();
}

function cmdListLast(){
	document.frmemplanguage.command.value="<%=Command.LAST%>";
	document.frmemplanguage.prev_command.value="<%=Command.LAST%>";
	document.frmemplanguage.action="emplanguage.jsp";
	document.frmemplanguage.submit();
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
                  &gt; Employee language<!-- #EndEditable --> </strong></font> 
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
                              <form name="frmemplanguage" method ="post" action="">
                                <input type="hidden" name="command" value="<%=iCommand%>">
                                <input type="hidden" name="start" value="<%=start%>">
                                <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                                <input type="hidden" name="emp_language_oid" value="<%=oidEmpLanguage%>">
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
                                          <td width="12%" nowrap bgcolor="#0066CC"> 
                                            <div align="center"  class="tablink"><a href="familymember.jsp?employee_oid=<%=oidEmployee%>" class="tablink">Family 
                                              Member</a></div>
                                          </td>
                                          <td width="9%" nowrap bgcolor="#66CCFF"> 
                                            <div align="center"  class="tablink">Language</div>
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
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabbg">
                                              <tr align="left" valign="top"> 
                                                <td width="10">&nbsp;</td>
                                                <td colspan="2" width="974"> 
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr align="left" valign="top"> 
                                                      <td height="14" valign="middle" colspan="3" class="comment">&nbsp;</td>
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
                                                      <td valign="middle" colspan="3" class="comment"> 
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
                                                    <% }%>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td width="10" valign="middle">&nbsp;</td>
                                                <td valign="middle" colspan="2" width="974"> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr> 
                                                      <td colspan="2" class="listtitle">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td colspan="2" class="listtitle">Employee 
                                                        Language List</td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" colspan="2"> 
                                                        <table width="90%" border="0" cellspacing="1" cellpadding="1" class="listgen">
                                                          <tr class="listgentitle"> 
                                                            <td width="3%"> 
                                                              <div align="center"><b>No</b></div>
                                                            </td>
                                                            <td width="18%"> 
                                                              <div align="center"><b>Language</b></div>
                                                            </td>
                                                            <td width="14%"> 
                                                              <div align="center"><b>Oral</b></div>
                                                            </td>
                                                            <td width="15%"> 
                                                              <div align="center"><b>Written</b></div>
                                                            </td>
                                                            <td width="50%"> 
                                                              <div align="center"><b>Desription</b></div>
                                                            </td>
                                                          </tr>
                                                          <tr class="listgensell"> 
                                                            <td width="3%"> 
                                                              <div align="center">1</div>
                                                            </td>
                                                            <td width="18%"> 
                                                              <div align="center"> 
                                                                <%    
															  
															  EmpLanguage empLanguage = new EmpLanguage();
															  empLanguage.setOral(-1);
															  empLanguage.setWritten(-1);
															  if(vctLanguage!=null && vctLanguage.size()>0){
															  		empLanguage = (EmpLanguage)vctLanguage.get(0);
															  }
															  
															  
																    Vector listLanguage = PstLanguage.listAll();																  
																    Vector language_value = new Vector(1,1);
																	Vector language_key = new Vector(1,1);																	
																    language_value.add("0");
																	language_key.add("Select ...");
																	for(int i=0;i<listLanguage.size();i++){
																		Language language = (Language)listLanguage.get(i);
																		language_value.add(""+language.getOID());
																		language_key.add(""+language.getLanguage());
																	}
																    %>
                                                                <% if((listLanguage != null) && (listLanguage.size()>0)){%>
                                                                <%= ControlCombo.draw(FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_LANGUAGE_ID]+"_0","formElemen",null, ""+empLanguage.getLanguageId(), language_value, language_key) %> 
                                                                <% }else {%>
                                                                <font class="comment">No 
                                                                Language available</font> 
                                                                <% 
															  }%>
                                                              </div>
                                                            </td>
                                                            <td width="14%"> 
                                                              <div align="center"><%= ControlCombo.draw(FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_ORAL]+"_0","formElemen", null, ""+empLanguage.getOral(), PstEmpLanguage.getGradeValue(), PstEmpLanguage.getGradeKey()) %></div>
                                                            </td>
                                                            <td width="15%"> 
                                                              <div align="center"><%= ControlCombo.draw(FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_WRITTEN]+"_0",null, ""+empLanguage.getWritten(), PstEmpLanguage.getGradeValue(), PstEmpLanguage.getGradeKey()) %> </div>
                                                            </td>
                                                            <td width="50%" nowrap> 
                                                              <div align="center"> 
                                                                <input type="text" name="<%=FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_DESCRIPTION]%>_0" class="elemenForm" size="60" value="<%= empLanguage.getDescription() %>">
                                                              </div>
                                                            </td>
                                                          </tr>
                                                          <tr class="listgensell"> 
                                                            <td width="3%"> 
                                                              <div align="center">2</div>
                                                            </td>
                                                            <td width="18%"> 
                                                              <div align="center"> 
                                                                <% 
															  EmpLanguage empLanguage1 = new EmpLanguage();
															  empLanguage1.setOral(-1);
															  empLanguage1.setWritten(-1);
															  
															  if(vctLanguage!=null && vctLanguage.size()>=2){
																	empLanguage1 = (EmpLanguage)vctLanguage.get(1);
															  }
															  if((listLanguage != null) && (listLanguage.size()>0)){%>
                                                                <%= ControlCombo.draw(FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_LANGUAGE_ID]+"_1","formElemen",null, ""+empLanguage1.getLanguageId(), language_value, language_key) %> 
                                                                <% }else {%>
                                                                <font class="comment">No 
                                                                Language available</font> 
                                                                <% }
															  %>
                                                              </div>
                                                            </td>
                                                            <td width="14%"> 
                                                              <div align="center"><%= ControlCombo.draw(FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_ORAL]+"_1","formElemen", null, ""+empLanguage1.getOral(), PstEmpLanguage.getGradeValue(), PstEmpLanguage.getGradeKey()) %></div>
                                                            </td>
                                                            <td width="15%"> 
                                                              <div align="center"><%= ControlCombo.draw(FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_WRITTEN]+"_1",null, ""+empLanguage1.getWritten(), PstEmpLanguage.getGradeValue(), PstEmpLanguage.getGradeKey()) %></div>
                                                            </td>
                                                            <td width="50%" nowrap> 
                                                              <div align="center"> 
                                                                <input type="text" name="<%=FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_DESCRIPTION]%>_1" class="elemenForm" size="60" value="<%= empLanguage1.getDescription() %>">
                                                              </div>
                                                            </td>
                                                          </tr>
                                                          <tr class="listgensell"> 
                                                            <td width="3%"> 
                                                              <div align="center">3</div>
                                                            </td>
                                                            <td width="18%"> 
                                                              <div align="center"> 
                                                                <% 
															  EmpLanguage empLanguage2 = new EmpLanguage();
															  empLanguage2.setOral(-1);
															  empLanguage2.setWritten(-1);
															  
															  if(vctLanguage!=null && vctLanguage.size()>=3){
															  		empLanguage2 = (EmpLanguage)vctLanguage.get(2);
															  }
															  
															  if((listLanguage != null) && (listLanguage.size()>0)){%>
                                                                <%= ControlCombo.draw(FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_LANGUAGE_ID]+"_2","formElemen",null, ""+empLanguage2.getLanguageId(), language_value, language_key) %> 
                                                                <% }else {%>
                                                                <font class="comment">No 
                                                                Language available</font> 
                                                                <% }%>
                                                              </div>
                                                            </td>
                                                            <td width="14%"> 
                                                              <div align="center"><%= ControlCombo.draw(FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_ORAL]+"_2","formElemen", null, ""+empLanguage2.getOral(), PstEmpLanguage.getGradeValue(), PstEmpLanguage.getGradeKey()) %></div>
                                                            </td>
                                                            <td width="15%"> 
                                                              <div align="center"><%= ControlCombo.draw(FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_WRITTEN]+"_2",null, ""+empLanguage2.getWritten(), PstEmpLanguage.getGradeValue(), PstEmpLanguage.getGradeKey()) %></div>
                                                            </td>
                                                            <td width="50%" nowrap> 
                                                              <div align="center"> 
                                                                <input type="text" name="<%=FrmEmpLanguage.fieldNames[FrmEmpLanguage.FRM_FIELD_DESCRIPTION]%>_2" class="elemenForm" size="60" value="<%= empLanguage2.getDescription() %>">
                                                              </div>
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
															ctrLine.setTableWidth("90%");
															String scomDel = "javascript:cmdAsk('"+oidEmpLanguage+"')";
															String sconDelCom = "javascript:cmdConfirmDelete('"+oidEmpLanguage+"')";
															String scancel = "javascript:cmdEdit('"+oidEmpLanguage+"')";
															ctrLine.setBackCaption("");
															ctrLine.setCommandStyle("buttonlink");
															ctrLine.setAddCaption("");
															ctrLine.setSaveCaption("Save Employee Language");
															ctrLine.setDeleteCaption("");
															ctrLine.setConfirmDelCaption("");															
						
						
															if(privAdd == false  && privUpdate == false){
																ctrLine.setSaveCaption("");
															}
						
															iCommand = Command.EDIT;
															
															%>
                                                        <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> </td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="13%">&nbsp;</td>
                                                      <td width="87%">&nbsp;</td>
                                                    </tr>
                                                    <tr align="left" valign="top" > 
                                                      <td colspan="3"> 
                                                        <div align="left"></div>
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
                                    <td>&nbsp; </td>
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
