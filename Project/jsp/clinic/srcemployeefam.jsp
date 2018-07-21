<% 
/* 
 * Page Name  		:  srcemployeefam.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: lkarunia 
 * @version  		: 01 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 				: [output ...] 
 *******************************************************************/
%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../main/checkuser.jsp" %>

<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>

<%
//pengecekan apakah user yang login adalah HRD

/*long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;

if(isHRDLogin){*/

long oidDivision = FRMQueryString.requestLong(request,FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DIVISION_ID]);
long oidDepartment = FRMQueryString.requestLong(request,FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT]);
int iCommand = FRMQueryString.requestCommand(request);
SrcEmployee srcEmployee = new SrcEmployee();
FrmSrcEmployee frmSrcEmployee = new FrmSrcEmployee();
if(iCommand==Command.GOTO){
    frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);
    frmSrcEmployee.requestEntityObject(srcEmployee);
}

if(iCommand==Command.BACK)
{
	frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);
	try
	{
		srcEmployee = (SrcEmployee)session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
		if(srcEmployee == null) 
		{
			srcEmployee = new SrcEmployee();
		}
		System.out.println("ecccccc "+srcEmployee.getOrderBy());
	}
	catch (Exception e)
	{
		srcEmployee = new SrcEmployee();
	}
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Medical - Search Employee & Family</title>
<script language="JavaScript">
function cmdUpdateDep(){
	document.frmsrcemployee.command.value="<%=String.valueOf(Command.GOTO)%>";
	document.frmsrcemployee.action="srcemployeefam.jsp"; 
	document.frmsrcemployee.submit();
}
function cmdUpdatePos(){
	document.frmsrcemployee.command.value="<%=String.valueOf(Command.GOTO)%>";
	document.frmsrcemployee.action="srcemployeefam.jsp"; 
	document.frmsrcemployee.submit();
}
    
function cmdAdd(){
		document.frmsrcemployee.command.value="<%=String.valueOf(Command.ADD)%>";
		document.frmsrcemployee.action="employee_edit.jsp";
		document.frmsrcemployee.submit();
}

function cmdSearch(){
		document.frmsrcemployee.command.value="<%=String.valueOf(Command.LIST)%>";
		document.frmsrcemployee.action="employeefam_list.jsp";
		document.frmsrcemployee.submit();
}

function cmdSpecialQuery(){
		document.frmsrcemployee.action="specialquery.jsp";
		document.frmsrcemployee.submit();
}

function fnTrapKD(){
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a && i < a.length && (x=a[i]) && x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i < a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0 && parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n]) && d.all) x=d.all[n]; for (i=0;!x && i < d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x && d.layers && i < d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
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
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable -->
</head> 
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
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
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" --> 
                  Clinic &gt; Medical &gt; Employee & Family Search<!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td class="tablecolor" style="background-color:<%=bgColorContent%>;"  > 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmsrcemployee" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td valign="middle" colspan="2"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="97%">&nbsp;</td>
                                                <td width="0%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="97%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="72%">
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left">Employee 
                                                          Name</div></td>
                                                      <td width="83%"> <input type="text" name="<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_NAME] %>"  value="<%= srcEmployee.getName() %>" class="elemenForm" size="40" onkeydown="javascript:fnTrapKD()"> 
                                                      </td>
                                                    </tr>
                                                    <script language="javascript">
                                                        document.frmsrcemployee.<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_NAME]%>.focus();
                                                    </script>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left">Payroll Number</div></td>
                                                      <td width="83%"> <input type="text" name="<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMPNUMBER] %>"  value="<%= srcEmployee.getEmpnumber() %>" class="elemenForm" onkeydown="javascript:fnTrapKD()"> 
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left">Spouse</div></td>
                                                      <td width="83%"> <input type="text" name="<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SPOUSE] %>"  value="<%= srcEmployee.getSpouse() %>" class="elemenForm" onkeydown="javascript:fnTrapKD()"> 
                                                      </td>
                                                    </tr>                                                    
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left">Address</div></td>
                                                      <td width="83%"> <input type="text" name="<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_ADDRESS] %>"  value="<%= srcEmployee.getAddress() %>" class="elemenForm" size="50" onkeydown="javascript:fnTrapKD()"> 
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap>Category</td>
                                                      <td width="83%"> <% 
                                                            Vector cat_value = new Vector(1,1);
                                                            Vector cat_key = new Vector(1,1);        
															cat_value.add("0");
                                                            cat_key.add("select ...");                                                          
                                                            Vector listCat = PstEmpCategory.list(0, 0, "", " EMP_CATEGORY ");                                                        
                                                            for (int i = 0; i < listCat.size(); i++) {
                                                                    EmpCategory cat = (EmpCategory) listCat.get(i);
                                                                    cat_key.add(cat.getEmpCategory());
                                                                    cat_value.add(String.valueOf(cat.getOID()));
                                                            }
                                                        %> <%= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_EMP_CATEGORY],"formElemen",null, ""+srcEmployee.getEmpCategory(), cat_value, cat_key, " onkeydown=\"javascript:fnTrapKD()\"") %></td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></div></td>
                                                      <td width="83%"> <%
                                                            Vector div_value = new Vector(1,1);
                                                            Vector div_key = new Vector(1,1);
                                                            div_value.add("0");
                                                            div_key.add("select ...");
                                                            Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                                                            for (int i = 0; i < listDiv.size(); i++) {
                                                                    Division div = (Division) listDiv.get(i);
                                                                    div_key.add(div.getDivision());
                                                                    div_value.add(String.valueOf(div.getOID()));
                                                            }
                                                        %> <%= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DIVISION_ID],"formElemen",null, ""+srcEmployee.getDivisionId(), div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"") %> </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                                                      <td width="83%"> <% 
                                                            Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);        
                                                            dept_value.add("0");
                                                            dept_key.add("select ...");
                                                            String strWhere = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+"="+oidDivision;
                                                            Vector listDept = PstDepartment.list(0, 0, strWhere,PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);                                                        
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                    Department dept = (Department) listDept.get(i);
                                                                    dept_key.add(dept.getDepartment());
                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                            }
                                                        %> <%= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_DEPARTMENT],"formElemen",null, ""+srcEmployee.getDepartment(), dept_value, dept_key, "onChange=\"javascript:cmdUpdatePos()\"") %> </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div></td>
                                                      <td width="83%"> <% 
                                                            Vector pos_value = new Vector(1,1);
                                                            Vector pos_key = new Vector(1,1);
                                                            pos_value.add("0");
                                                            pos_key.add("select ...");                                                            
                                                            Vector listPos = PstPosition.list(0, 0, "", " POSITION ");                                                            
                                                            for (int i = 0; i < listPos.size(); i++) {
                                                                    Position pos = (Position) listPos.get(i);
                                                                    pos_key.add(pos.getPosition());
                                                                    pos_value.add(String.valueOf(pos.getOID()));
                                                            }
                                                        %> <%= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_POSITION],"formElemen",null, "" + srcEmployee.getPosition(), pos_value, pos_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
													 <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left">Marital Status</div></td>
                                                      <td width="83%"> <% 
                                                            Vector marital_value = new Vector(1,1);
                                                            Vector marital_key = new Vector(1,1);        
															marital_value.add("0");
                                                            marital_key.add("select ...");                                                          
                                                            Vector listMarital = PstMarital.list(0, 0, "", " MARITAL_STATUS ");                                                        
                                                            for (int i = 0; i < listMarital.size(); i++) {
                                                                    Marital marital = (Marital) listMarital.get(i);
                                                                    marital_key.add(marital.getMaritalStatus());
                                                                    marital_value.add(String.valueOf(marital.getOID()));
                                                            }
                                                        %> <%= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_MARITAL_STATUS],"formElemen",null, ""+srcEmployee.getMaritalStatus(), marital_value, marital_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left">Race</div></td>
                                                      <td width="83%"> <% 
                                                            Vector race_value = new Vector(1,1);
                                                            Vector race_key = new Vector(1,1);        
															race_value.add("0");
                                                            race_key.add("select ...");                                                          
                                                            Vector listRace = PstRace.list(0, 0, "", " RACE_NAME ");                                                        
                                                            for (int i = 0; i < listRace.size(); i++) {
                                                                    Race race = (Race) listRace.get(i);
                                                                    race_key.add(race.getRaceName());
                                                                    race_value.add(String.valueOf(race.getOID()));
                                                            }
                                                        %> <%= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_RACE],"formElemen",null, ""+srcEmployee.getRaceId(), race_value, race_key) %> </td>
                                                    </tr>
                                                    
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left">Birthday</div></td>
                                                      <td width="83%"> 
                                                        <input type="checkbox" name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_BIRTHDAY_CHECK]%>" <%=(srcEmployee.isBirthdayChecked() ? "checked" : "")%> value="1">
                                                        <i><font color="#FF0000">Select all 
                                                        months</font></i></td>
                                                      </td> 
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                         <div align="left">&nbsp;</div></td>
                                                      <td width="83%">
                                                          <%--=ControlDate.drawDateMY(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_BIRTHDAY],(srcEmployee.getBirthday() == null?new Date():srcEmployee.getBirthday()),"MMMM","formElemen",+4,-8)--%>
                                                          <select name="<%=FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_BIRTHMONTH]%>">
                                                          <%
                                                            java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
                                                          
                                                            for(int i=0; i<12; i++) {
                                                          %>
                                                                <option value="<%= ""+(i+1)%>" <%= (srcEmployee.getBirthmonth()==(i+1) ? " selected " : "")  %>>
                                                                <% 
                                                                    cal.set(Calendar.MONTH, i);
                                                                    out.print(Formater.formatDate(cal.getTime(), "MMMM"));
                                                                %>
                                                                </option>
                                                          <%
                                                            }
                                                          %>
                                                         </select>
                                                      </td>
                                                    </tr>
                                                    
						    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left">Salary Level</div></td>
                                                      <td width="83%"> <% 
                                                            Vector level_value = new Vector(1,1);
                                                            Vector level_key = new Vector(1,1);        
															level_value.add("");
                                                            level_key.add("select ...");                                                          
                                                            Vector listLevel = PstSalaryLevel.list(0, 0, "", " LEVEL_CODE ");                                                        
                                                            for (int i = 0; i < listLevel.size(); i++) {
                                                                    SalaryLevel salaryLevel = (SalaryLevel) listLevel.get(i);
                                                                    level_key.add(salaryLevel.getLevelCode());
                                                                    level_value.add(String.valueOf(salaryLevel.getLevelCode()));
                                                            }
                                                        %> <%= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SALARY_LEVEL],"formElemen",null, ""+srcEmployee.getSalaryLevel(), level_value, level_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
													<!-- Row ini digunakan jika search by religion edited by Yunny -->
                                                    <tr align="left" valign="top"> 
                                                      <td nowrap>Religion</td>
                                                      <td width="83%"> <%
													  		Vector rel_value = new Vector(1,1);
                                                            Vector rel_key = new Vector(1,1);
															rel_value.add("0");
                                                            rel_key.add("select ...");
															Vector listRel = PstReligion.list(0, 0, "", " RELIGION ");
															for (int i = 0; i < listRel.size(); i++) {
																	Religion rel = (Religion) listRel.get(i);
																	rel_key.add(rel.getReligion());
																	rel_value.add(String.valueOf(rel.getOID()));
															}
														%> <%= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_RELIGION],"formElemen",null, "" + srcEmployee.getReligion(), rel_value, rel_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div></td>
                                                      <td width="83%"> <% 
                                                            Vector sec_value = new Vector(1,1);
                                                            Vector sec_key = new Vector(1,1);
                                                            sec_value.add("0");
                                                            sec_key.add("select ...");
                                                            //Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
                                                            String strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+oidDepartment;
                                                            Vector listSec = PstSection.list(0, 0, strWhereSec, " SECTION ");
                                                            for (int i = 0; i < listSec.size(); i++) {
                                                                    Section sec = (Section) listSec.get(i);
                                                                    sec_key.add(sec.getSection());
                                                                    sec_value.add(String.valueOf(sec.getOID()));
                                                            }
                                                        %> <%= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SECTION],"formElemen",null, "" + srcEmployee.getSection(), sec_value, sec_key, " onkeydown=\"javascript:fnTrapKD()\"") %></td>
													
                                                    </tr>
													<!-- Row ini digunakan jika search by gender edited by Yunny -->
                                                     <tr align="left" valign="top"> 
                                                      <td nowrap>Gender</td>
                                                      <td width="18%">
													    <input type="radio" name="<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SEX]%>" value="0" >
                                                        Male 
                                                        <input type="radio" name="<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SEX]%>" value="1">
                                                        Female 
														<input type="radio" name="<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_SEX]%>" value="2" checked>
                                                        All 
                                                        </td>
                                                 

                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="24" nowrap>Commencing 
                                                        Date</td>
                                                      <td width="83%"><%=ControlDate.drawDateWithStyle(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC], srcEmployee.getStartCommenc(), 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%> &nbsp;to&nbsp; 
                                                        <%=ControlDate.drawDateWithStyle(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_END_COMMENC], srcEmployee.getEndCommenc(), 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%></td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="21" nowrap>Resigned 
                                                        Status </td>
                                                      <td width="83%"> <input type="radio" name="<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_RESIGNED]%>" value="0" checked>
                                                        No 
                                                        <input type="radio" name="<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_RESIGNED]%>" value="1">
                                                        Yes 
                                                        <input type="radio" name="<%=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_RESIGNED]%>" value="2">
                                                        All </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="21" nowrap> 
                                                        <div align="left">Sort 
                                                          By</div></td>
                                                      <td width="83%"> <%= ControlCombo.draw(frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_ORDER],"formElemen",null, ""+srcEmployee.getOrderBy(), FrmSrcEmployee.getOrderValue(), FrmSrcEmployee.getOrderKey(), " onkeydown=\"javascript:fnTrapKD()\"") %></td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%"> <div align="left"></div></td>
                                                      <td width="83%">&nbsp;</td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%"> <div align="left"></div></td>
                                                      <td width="83%"> <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                          <tr> 
                                                            <td width="20%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="2" height="1"></td>
                                                            <td width="28%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                              for Employee & Family</a></td>
                                                            
                                                          </tr>
                                                        </table></td>
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
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>

<%--
}else{
%>    
    <script language="javascript">
              window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.HAVE_NOPRIV%>";
    </script>             
<%
}
--%>

