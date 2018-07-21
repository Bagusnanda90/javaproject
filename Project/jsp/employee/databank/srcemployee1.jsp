
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<% 
/* 
 * Page Name  		:  srcemployee.jsp
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

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
  long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
  long sdmDivisionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_SDM_DIVISION)));
    boolean isHRDLogin = (hrdDepOid == departmentOid || sdmDivisionOid == divisionOid) ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>

<%
//long oidDivision = FRMQueryString.requestLong(request,FrmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMP_DIVISION_ID] );
//long oidCompany = FRMQueryString.requestLong(request,FrmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_COMPANY_ID]);
//long oidDepartment = FRMQueryString.requestLong(request,FrmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_DEPARTMENT]);
int iCommand = FRMQueryString.requestCommand(request);

//update by satrya 2013-08-05
SearchSpecialQuery searchSpecialQuery = new SearchSpecialQuery();
FrmSrcSpecialEmployeeQuery frmSrcSpecialEmployeeQuery = new FrmSrcSpecialEmployeeQuery();//new FrmSrcSpecialEmployeeQuery(request, searchSpecialQuery);
/*xx
SrcEmployee srcEmployee = new SrcEmployee();
FrmSrcEmployee frmSrcEmployee = new FrmSrcEmployee();*/

if(iCommand==Command.GOTO){
    /*frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);
    frmSrcEmployee.requestEntityObject(srcEmployee);*/
    //update by satrya 2013-08-05
    frmSrcSpecialEmployeeQuery = new FrmSrcSpecialEmployeeQuery(request, searchSpecialQuery);
    frmSrcSpecialEmployeeQuery.requestEntityObject(searchSpecialQuery);
}

if(iCommand==Command.BACK)
{
	//frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);
        //update by satrya 2013-08-05
         frmSrcSpecialEmployeeQuery = new FrmSrcSpecialEmployeeQuery(request, searchSpecialQuery); 
	try
	{
		/*srcEmployee = (SrcEmployee)session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
		if(srcEmployee == null) 
		{
			srcEmployee = new SrcEmployee();
		}
		System.out.println("ecccccc "+srcEmployee.getOrderBy());*/
            //update by satrya 2013-08-05
            searchSpecialQuery = (SearchSpecialQuery)session.getValue(SessEmployee.SESS_SRC_EMPLOYEE);
		if(searchSpecialQuery == null) 
		{
			searchSpecialQuery = new SearchSpecialQuery();
		}
		//System.out.println("ecccccc "+searchSpecialQuery.getOrderBy());
	}
	catch (Exception e)
	{
		//srcEmployee = new SrcEmployee();
            ///update by satrya 2013-08-05
            searchSpecialQuery = new SearchSpecialQuery();
	}
}
I_Dictionary dictionaryD = userSession.getUserDictionary();
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Search <%=dictionaryD.getWord(I_Dictionary.EMPLOYEE)%></title>
<script language="JavaScript">

function cmdUpdateDiv(){
	document.frmsrcemployee.command.value="<%=String.valueOf(Command.GOTO)%>";
	document.frmsrcemployee.action="srcemployee.jsp";
	document.frmsrcemployee.submit();
}

function cmdUpdateDep(){
	document.frmsrcemployee.command.value="<%=String.valueOf(Command.GOTO)%>";
	document.frmsrcemployee.action="srcemployee.jsp"; 
	document.frmsrcemployee.submit();
}
function cmdUpdatePos(){
	document.frmsrcemployee.command.value="<%=String.valueOf(Command.GOTO)%>";
	document.frmsrcemployee.action="srcemployee.jsp"; 
	document.frmsrcemployee.submit();
}

    
function cmdAdd(){
		document.frmsrcemployee.command.value="<%=String.valueOf(Command.ADD)%>";
                //document.frmsrcemployee.employee_oid.value=0;
		document.frmsrcemployee.action="employee_edit.jsp";
		document.frmsrcemployee.submit();
}

function cmdSearch(){
		document.frmsrcemployee.command.value="<%=String.valueOf(Command.LIST)%>";
		document.frmsrcemployee.action="employee_list.jsp";
		document.frmsrcemployee.submit();
}

function cmdHeadCount(){
		document.frmsrcemployee.command.value="<%=String.valueOf(Command.LIST)%>";
		document.frmsrcemployee.action="employee_headcount.jsp";
		document.frmsrcemployee.submit();
}

function cmdHistoryPosition(){
		document.frmsrcemployee.command.value="<%=String.valueOf(Command.LIST)%>";
		document.frmsrcemployee.action="structure_history.jsp";
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
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable -->
</head> 

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
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
  <tr > 
      <td width="88%" valign="top" align="left" > 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Data Bank &gt; <%=dictionaryD.getWord(I_Dictionary.EMPLOYEE)%> Search<!-- #EndEditable --> 
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
                        <table  style="border:1px solid <%=garisContent%>" width="100%" cellspacing="1" cellpadding="1" class="tabbg" >
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
                                                        <div align="left">Payroll 
                                                          Number</div></td>
                                                      <td width="83%"> <input type="text" name="<%=frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMPLOYEENUM] %>"  value="<%= searchSpecialQuery.getEmpnumber() %>" class="elemenForm" onkeydown="javascript:fnTrapKD()"> 
                                                      </td>
                                                    </tr>  
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.EMPLOYEE)%>  
                                                          <%=dictionaryD.getWord("NAME")%></div></td>
                                                      <td width="83%"><input type="text" name="<%=frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMP_FULLNAME] %>"  value="<%= searchSpecialQuery.getFullNameEmp() %>" class="elemenForm" size="40" onkeydown="javascript:fnTrapKD()"> 
                                                      </td>
                                                    </tr>
                                                    <script language="javascript">
                                                        document.frmsrcemployee.<%=frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMP_FULLNAME]%>.focus();
                                                    </script>                                                    
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.ADDRESS)%></div></td>
                                                      <td width="83%"> <input type="text" name="<%=frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMP_ADDRESS] %>"  value="<%= searchSpecialQuery.getAddrsEmployee() %>" class="elemenForm" size="50" onkeydown="javascript:fnTrapKD()"> 
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap><%=dictionaryD.getWord("CATEGORY")%></td>
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
                                                        String empCatId[] = searchSpecialQuery.getArrCompany(0);
                                                        long lEmpCatId = empCatId!=null && empCatId[0]!=null && empCatId[0].length()>0 ? Long.parseLong(empCatId[0]):0; 
                                                      %> <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMP_CATEGORY_ID],"formElemen",null, ""+lEmpCatId, cat_value, cat_key, " onkeydown=\"javascript:fnTrapKD()\"") %></td>
                                                    </tr>
                                                    <tr align="left" valign="top">
                                                      <td width="17%" nowrap>
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.COMPANY)%></div></td>
                                                      <td width="83%"> 
                                                           <% 
                                                           long depOid = departmentOfLoginUser.getOID();
                                                           int SetHRD = 0 ;
                                                           try {
                                                             SetHRD = Integer.valueOf(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT")); 
                                                           } catch (Exception e){
                                                               
                                                           }
                                                            %>
                                                          <%
                                                           Vector comp_value = new Vector(1,1);
                                                            Vector comp_key = new Vector(1,1);
                                                            comp_value.add("0");
                                                            comp_key.add("select ...");
                                                            Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                                                            long companyDefault = 0;
                                                            for (int i = 0; i < listComp.size(); i++) {
                                                                    Company comp = (Company) listComp.get(i);
                                                                    comp_key.add(comp.getCompany());
                                                                    comp_value.add(String.valueOf(comp.getOID()));
                                                                    companyDefault = comp.getOID();
                                                            }
                                                             String companyId[] = searchSpecialQuery.getArrCompany(0);
                                                        long lCompanyId = companyId!=null && companyId[0]!=null && companyId[0].length()>0 ? Long.parseLong(companyId[0]):0; 
                                                        /*
                                                        if ((lCompanyId != 0) && (iCommand == Command.GOTO)){
                                                           companyDefault = lCompanyId;                                                            
                                                        } else {
                                                            lCompanyId = companyDefault;
                                                        }*/
                                                      %> <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_COMPANY_ID],"formElemen",null, ""+(lCompanyId) , comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"") %> 
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DIVISION)%></div></td>
                                                      <td width="83%"> 
                                                          <!-- penambahan untuk yang login maka yang dapat dicari adalah employee yang sesuai dengan divisi yang sama -->
                                                         
                                                          <%
                                                            Vector div_value = new Vector(1,1);
                                                            Vector div_key = new Vector(1,1);
                                                            
                                                            String  wheredivId ="" ;
                                                            
                                                            if (depOid==0 || depOid==SetHRD ){
                                                                div_value.add("0");
                                                                div_key.add("select ...");
                                                            } else {
                                                                String whereDepId = " DEPT."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+ "  =  " + depOid;
                                                                long divOid = PstDepartment.getDivisionIdWheredepId( 0, 0, whereDepId, null);
                                                                wheredivId =" " +PstDivision.TBL_HR_DIVISION+ "." +PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + " = " + divOid;                                                          
                                                           }
                                                            Vector listDiv = PstDivision.list(0, 0, wheredivId, " DIVISION ");
                                                            long divisionDefault = 0;
                                                            for (int i = 0; i < listDiv.size(); i++) {
                                                                    Division div = (Division) listDiv.get(i);
                                                                    div_key.add(div.getDivision());
                                                                    div_value.add(String.valueOf(div.getOID()));
                                                                    divisionDefault = div.getOID();
                                                            }
                                                            String divisionId[] = searchSpecialQuery.getArrDivision(0);
                                                            long lDivisionId = divisionId!=null && divisionId[0]!=null && divisionId[0].length()>0 ? Long.parseLong(divisionId[0]):0; 
                                                            /*
                                                            if (lDivisionId != 0){
                                                                divisionDefault = lDivisionId;
                                                            } else {
                                                                lDivisionId = divisionDefault;
                                                            }*/
                                                      %> 
                                                      
                                                      <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMP_DIVISION_ID],"formElemen",null, ""+(lDivisionId), div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"") %> 
                                                     
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%></div></td>
                                                      <td width="83%"> 
                                                         <%
                                                        
                                                          Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);        
                                                            
                                                            
                                                            String strWhere="";
                                                            if (depOid==0 || depOid==SetHRD || divisionOid == sdmDivisionOid ){
                                                               
                                                              strWhere = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+"="+lDivisionId;//oidDivision
                                                            } else {
                                                                if (divisionOid != 0){
                                                                    strWhere = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+"="+lDivisionId;//oidDivision
                                                                } else {
                                                                    strWhere = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]+"="+depOid;//oidDivision
                                                                }
                                                              
                                                            }
                                                            Vector listDept = PstDepartment.list(0, 0, strWhere,PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);                                                        
                                                            dept_value.add("0");
                                                            dept_key.add("select ..."); 
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                    Department dept = (Department) listDept.get(i);
                                                                    dept_key.add(dept.getDepartment());
                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                            }
                                                                String departementId[] = searchSpecialQuery.getArrDepartmentId(0);
                                                                long lDepartment = departementId!=null && departementId[0]!=null && departementId[0].length()>0 ? Long.parseLong(departementId[0]):0;
                                                          %> 
                                                          
                                                        
                                                             <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_DEPARTMENT],"formElemen",null, ""+lDepartment, dept_value, dept_key, "onChange=\"javascript:cmdUpdatePos()\"") %>  
                                                         
                                                          <%
/*
            Vector dept_value = new Vector(1, 1);
            Vector dept_key = new Vector(1, 1);
            //Vector listDept = new Vector(1, 1);
            //DepartmentIDnNameList keyList = new DepartmentIDnNameList();

            if (processDependOnUserDept) {
                if (emplx.getOID() > 0) {
                    if (isHRDLogin || isEdpLogin || isGeneralManager) {
                        Vector listDept= PstDepartment.list(0, 0, "", PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                         div_value.add("0");
                         div_key.add("select ...");
                         for (int i = 0; i < listDept.size(); i++) {
                                Department department = (Department) listDept.get(i);
                                dept_key.add(department.getDepartment());
                                dept_value.add(String.valueOf(department.getOID()));
                        }
                    } else {
                        Position position = null;
                        try {
                            position = PstPosition.fetchExc(emplx.getPositionId());
                        } catch (Exception exc) {
                        }
                        if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                            String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + emplx.getDivisionId() + "";
                             Vector listDept = PstDepartment.listDepartemntByDiv(0, 0, whereDiv, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                            for (int i = 0; i < listDept.size(); i++) {
                                    Department department = (Department) listDept.get(i);
                                    dept_key.add(department.getDepartment());
                                    dept_value.add(String.valueOf(department.getOID()));
                           }
                        } else {

                            String whereClsDep = "(" + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + departmentOid
                                    + ") OR (" + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + " = " + departmentOid + ") ";
                            try {
                                String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                                Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                                int grpIdx = -1;
                                int maxGrp = depGroup == null ? 0 : depGroup.size();
                                int countIdx = 0;
                                int MAX_LOOP = 10;
                                int curr_loop = 0;
                                do { // find group department belonging to curretn user base in departmentOid
                                    curr_loop++;
                                    String[] grp = (String[]) depGroup.get(countIdx);
                                    for (int g = 0; g < grp.length; g++) {
                                        String comp = grp[g];
                                        if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                            grpIdx = countIdx;   // A ha .. found here 
                                        }
                                    }
                                    countIdx++;
                                } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit

                                // compose where clause
                                if (grpIdx >= 0) {
                                    String[] grp = (String[]) depGroup.get(grpIdx);
                                    for (int g = 0; g < grp.length; g++) {
                                        String comp = grp[g];
                                        whereClsDep = whereClsDep + " OR (DEPARTMENT_ID = " + comp + ")";
                                    }
                                }
                            } catch (Exception exc) {
                                    System.out.println(" Parsing Join Dept" + exc);

                            }
                           
                            //listDept = PstDepartment.list(0, 0,whereClsDep, "");
                              Vector listDept = PstDepartment.listDepartment(0, 0, whereClsDep, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]); 
                            for (int i = 0; i < listDept.size(); i++) {
                                    Department department = (Department) listDept.get(i);
                                    dept_key.add(department.getDepartment());
                                    dept_value.add(String.valueOf(department.getOID()));
                           }
                        }
                    }
                } else {
                   
                   // keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                    Vector listDept= PstDepartment.list(0, 0, "", PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                     for (int i = 0; i < listDept.size(); i++) {
                                Department department = (Department) listDept.get(i);
                                dept_key.add(department.getDepartment());
                                dept_value.add(String.valueOf(department.getOID()));
                        }
                   
                }
            } else {
               
               // keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                 Vector listDept= PstDepartment.list(0, 0, "", PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                     for (int i = 0; i < listDept.size(); i++) {
                                Department department = (Department) listDept.get(i);
                                dept_key.add(department.getDepartment());
                                dept_value.add(String.valueOf(department.getOID()));
                        }
               
            }
           
             String selectValueDepartment = "" + oidDepartment;//+objSrcLeaveApp.getDepartmentId();*/

        %>
        <%//=ControlCombo.draw("department", "elementForm", null, selectValueDepartment, dept_value, dept_key, " onkeydown=\"javascript:fnTrapKD()\"")%>
</td>
												
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap><div align="left"><%=dictionaryD.getWord("SECTION")%></div></td>
                                                      <td width="83%"> <% 
                                                            Vector sec_value = new Vector(1,1);
                                                            Vector sec_key = new Vector(1,1);
                                                            //Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
                                                            String strWhereSec  ="";
                                                            if (depOid==0 || depOid==SetHRD || divisionOid == sdmDivisionOid){
                                                                
                                                                //strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+lDepartment;//oidDepartment
                                                            } else {
                                                                String whereInDep = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+"="+lDivisionId;
                                                                String inDept = "";
                                                                Vector listInDep = PstDepartment.list(0, 0, whereInDep, "");
                                                                if (listInDep != null && listInDep.size()>0){
                                                                    for(int i=0; i<listInDep.size(); i++){
                                                                        Department dept = (Department)listInDep.get(i);
                                                                        inDept += dept.getOID() + ",";
                                                                    }
                                                                    inDept += "0";
                                                                    strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+" IN("+inDept+")";
                                                                } else {
                                                                    strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+depOid;
                                                                }
                                                            }
                                                            
                                                            
                                                            Vector listSec = PstSection.list(0, 0, strWhereSec, " SECTION ");
                                                            sec_value.add("0");
                                                            sec_key.add("select ...");
                                                            for (int i = 0; i < listSec.size(); i++) {
                                                                    Section sec = (Section) listSec.get(i);
                                                                    sec_key.add(sec.getSection());
                                                                    sec_value.add(String.valueOf(sec.getOID()));
                                                            }
                                                             String sectionId[] = searchSpecialQuery.getArrSectionId(0);
                                                        long lSectionId = sectionId!=null && sectionId[0]!=null && sectionId[0].length()>0 ? Long.parseLong(sectionId[0]):0;
                                                      %> <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_SECTION],"formElemen",null, "" +lSectionId , sec_value, sec_key, " onkeydown=\"javascript:fnTrapKD()\"") %></td>
													
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
                                                             String positionId[] = searchSpecialQuery.getArrPositionId(0);
                                                        long lPositionId = positionId!=null && positionId[0]!=null && positionId[0].length()>0 ? Long.parseLong(positionId[0]):0;
                                                      %> <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_POSITION],"formElemen",null, "" +lPositionId , pos_value, pos_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    
                                                    <%
                                                    int SetLocation = 1 ;
                                                    try {
                                                     SetLocation = Integer.valueOf(PstSystemProperty.getValueByName("USE_LOCATION_SET")); 
                                                    } catch (Exception e){
                                                     //   out.print("Sysprop : 'USE_LOCATION_SET' is not set ! ");
                                                    }
                                                    if (SetLocation==1) {
                                                    %>
                                                    <tr align="left" valign="top"> 
                                                    <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord("LOCATION")%></div></td>
                                                      <td width="83%"> <% 
                                                            Vector loc_value = new Vector(1,1);
                                                            Vector loc_key = new Vector(1,1);
                                                            loc_value.add("0");
                                                            loc_key.add("select ");                                                            
                                                            Vector listLoc = PstLocation.list(0, 0, "", " NAME ");                                                            
                                                            for (int i = 0; i < listLoc.size(); i++) {
                                                                    Location loc = (Location) listLoc.get(i);
                                                                    loc_key.add(loc.getName());
                                                                    loc_value.add(String.valueOf(loc.getOID()));
                                                            }
                                                             String locationId[] = searchSpecialQuery.getArrLocationId(0);
                                                        long lLocationId = locationId!=null && locationId[0]!=null && locationId[0].length()>0 ? Long.parseLong(locationId[0]):0;
                                                      %> <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_LOCATION],"formElemen",null, "" +lLocationId , loc_value, loc_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    <% } %>
                                                     <% 
                                                     int SetGrade = 1 ;
                                                    try {
                                                     SetLocation = Integer.valueOf(PstSystemProperty.getValueByName("USE_GRADE_SET")); 
                                                    } catch (Exception e){
                                                      //  out.print("Sysprop : 'USE_GRADE_SET' is not set ! ");
                                                    }
                                                    
                                                    if (SetGrade==1) {
                                                    %>
                                                    <tr align="left" valign="top"> 
                                                    <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord("GRADE")%></div></td>
                                                      <td width="83%"> <% 
                                                            Vector gra_value = new Vector(1,1);
                                                            Vector gra_key = new Vector(1,1);
                                                            gra_value.add("0");
                                                            gra_key.add("select ");                                                            
                                                            Vector listGra = PstGradeLevel.list(0, 0, "", " GRADE_CODE ");                                                            
                                                            for (int i = 0; i < listGra.size(); i++) {
                                                                    GradeLevel grade = (GradeLevel) listGra.get(i);
                                                                    gra_key.add(grade.getCodeLevel());
                                                                    gra_value.add(String.valueOf(grade.getOID()));
                                                            }
                                                             String gradeId[] = searchSpecialQuery.getArrGradeId(0);
                                                        long lGradeId = gradeId!=null && gradeId[0]!=null && gradeId[0].length()>0 ? Long.parseLong(gradeId[0]):0;
                                                      %> <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_GRADE],"formElemen",null, "" +lGradeId , gra_value, gra_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                      
                                                    </tr>
                                                    <% } %>
                                                    
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord("MARITAL_STATUS")%></div></td>
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
                                                        String maritalId[] = searchSpecialQuery.getArrMaritalId(0);
                                                        long lMaritalId = maritalId!=null && maritalId[0]!=null && maritalId[0].length()>0 ? Long.parseLong(maritalId[0]):0;
                                                      %> <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_MARITAL],"formElemen",null, ""+lMaritalId, marital_value, marital_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord("RACE")%></div></td>
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
                                                        String raceId[] = searchSpecialQuery.getArrRaceId(0);
                                                        long lRaceId = raceId!=null && raceId[0]!=null && raceId[0].length()>0 ? Long.parseLong(raceId[0]):0;
                                                      %> <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_RACE],"formElemen",null, ""+lRaceId, race_value, race_key) %> </td>
                                                    </tr>                                                    
                                                   <!-- hidden by satrya 2013-08-15 karena sudah ada -selected-
                                                   <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                        <div align="left">Birthday</div></td>
                                                      <td width="83%"> 
                                                        <input type="checkbox" name="<//%=frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMP_BIRTH_DAY_CHEKED]%>" <%=(searchSpecialQuery.isBirthdayChecked() ? "checked" : "")%> value="1">
                                                        <i><font color="#FF0000">Select all 
                                                        months</font></i></td>
                                                      </td> 
                                                    </tr>-->
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" nowrap> 
                                                         <div align="left"><%=dictionaryD.getWord(I_Dictionary.BIRTHDAY)%></div></td>
                                                      <td width="83%">
                                                          <%--=ControlDate.drawDateMY(FrmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_BIRTHDAY],(srcEmployee.getBirthday() == null?new Date():srcEmployee.getBirthday()),"MMMM","formElemen",+4,-8)--%>
                                                          
                                                          <select name="<%=frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMP_BIRTH_DAY_MONTH]%>">
                                                              <option value="<%= ""+0%>" <%= (searchSpecialQuery.getBirthMonth()==(0) ? " selected " : "")  %>>-selected-</option>
                                                          <%
                                                            java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
                                                            cal.set(Calendar.DAY_OF_MONTH, 1);
                                                            for(int i=0; i<12; i++) {
                                                          %>
                                                                <option value="<%= ""+(i+1)%>" <%= (searchSpecialQuery.getBirthMonth()==(i+1) ? " selected " : "")  %>>
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
                                                        <div align="left"><%=dictionaryD.getWord("SALARY_LEVEL")%></div></td>
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
                                                        %> <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMP_SALARY_LEVEL_ID],"formElemen",null, ""+searchSpecialQuery.getSalaryLevel(), level_value, level_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
													<!-- Row ini digunakan jika search by religion edited by Yunny -->
                                                    <tr align="left" valign="top"> 
                                                      <td nowrap><%=dictionaryD.getWord(I_Dictionary.RELIGION)%></td>
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
                                                           String religionId[] = searchSpecialQuery.getArrReligionId(0);
                                                        long lReligionId = religionId!=null && religionId[0]!=null && religionId[0].length()>0 ? Long.parseLong(religionId[0]):0;
                                                      %> <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_RELIGION],"formElemen",null, "" +lReligionId , rel_value, rel_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td nowrap>PAYROLL GROUP</td>
                                                      <td width="83%"> <%
							    Vector payrollG_value = new Vector(1,1);
                                                            Vector payrollG_key = new Vector(1,1);
							    payrollG_value.add("0");
                                                            payrollG_key.add("select ...");
															Vector listpayrollG = PstPayrollGroup.list(0, 0, "", "");
															for (int i = 0; i < listpayrollG.size(); i++) {
																	PayrollGroup payrollG = (PayrollGroup) listpayrollG.get(i);
																	payrollG_key.add(payrollG.getPayrollGroupName());
																	payrollG_value.add(String.valueOf(payrollG.getOID()));
															}
                                                           String payrollGId[] = searchSpecialQuery.getArrReligionId(0);
                                                        long lPayrollGId = payrollGId!=null && payrollGId[0]!=null && payrollGId[0].length()>0 ? Long.parseLong(payrollGId[0]):0;
                                                      %> <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_PAYROLL_GROUP_ID],"formElemen",null, "" +payrollGId , payrollG_value, payrollG_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
													<!-- Row ini digunakan jika search by gender edited by Yunny -->
                                                     <tr align="left" valign="top"> 
                                                      <td nowrap><%=dictionaryD.getWord("GENDER")%></td>
                                                      <td width="18%">
													    <input type="radio" name="<%=frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_SEX]%>" value="0" >
                                                        Male 
                                                        <input type="radio" name="<%=frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_SEX]%>" value="1">
                                                        Female 
														<input type="radio" name="<%=frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_SEX]%>" value="2" checked>
                                                        All 
                                                        </td>
                                                     <%
                                                        String checked="";
                                                        String dflCheked="checked";
                                                        if(searchSpecialQuery.getRadiocommdate()!=0){
                                                            checked="checked";
                                                            dflCheked="";
                                                        }
                                                    %>
                                                  <tr align="left" valign="top"> 
                                                      <td width="17%" height="21" nowrap><%=dictionaryD.getWord("COMMENCING_DATE")%></td>
                                                      <td width="83%">
                                                          <input type="radio" name="<%=FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RADIO_COMMERCING_DATE]%>" value="0" <%=checked.length()>0?checked:dflCheked%>>
                                                        All date</td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="24" nowrap></td>
                                                      <td width="83%">
                                                          <input type="radio" name="<%=FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RADIO_COMMERCING_DATE]%>" value="1" <%=checked%>>
                                                          <%=ControlDate.drawDateWithStyle(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_COMMDATEFROM], searchSpecialQuery.getCommdatefrom(0), 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%> &nbsp;to&nbsp; 
                                                          <%=ControlDate.drawDateWithStyle(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_COMMDATETO], searchSpecialQuery.getCommdateto(0), 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%></td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="21" nowrap><%=dictionaryD.getWord("RESIGNED_STATUS")%></td>
                                                      <td width="83%"> <input type="radio" name="<%=frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_RESIGNED]%>" value="0" checked>
                                                        <%=dictionaryD.getWord("NO")%> 
                                                        <input type="radio" name="<%=frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_RESIGNED]%>" value="1">
                                                        <%=dictionaryD.getWord("YES")%> 
                                                        <input type="radio" name="<%=frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_RESIGNED]%>" value="2">
                                                        <%=dictionaryD.getWord("ALL")%> </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="24" nowrap><%=dictionaryD.getWord("RESIGNED_DATE")%></td>
                                                      <td width="83%"><%=ControlDate.drawDateWithStyle(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMP_START_RESIGN], searchSpecialQuery.getStartResign(), 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%> &nbsp;to&nbsp; 
                                                        <%=ControlDate.drawDateWithStyle(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMP_END_RESIGN], searchSpecialQuery.getEndResign(), 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%></td>
                                                    </tr>
                                                    
                                                    
                                                    
                                                    <%
                                                        String checkedEd="";
                                                        String dflChekedEd="checked";
                                                        if(searchSpecialQuery.getRadioendcontract()!=0){
                                                            checkedEd="checked";
                                                            dflCheked="";
                                                        }
                                                    %>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="21" nowrap><%=dictionaryD.getWord("ENDED_CONTRACT")%></td>
                                                      <td width="83%">
                                                          <input type="radio" name="<%=FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RADIO_END_CONTRACT]%>" value="0" <%=checkedEd.length()>0?checkedEd:dflChekedEd%>>
                                                        <%=dictionaryD.getWord("ALL")%> date</td>
                                                    </tr>
                                                    
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="24" nowrap></td>
                                                      <td width="83%">
                                                          <input type="radio" name="<%=FrmSrcSpecialEmployee.fieldNames[FrmSrcSpecialEmployee.FRM_FIELD_RADIO_END_CONTRACT]%>" value="1" <%=checked%>>
                                                          <%=ControlDate.drawDateWithStyle(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_ENDCONTRACTFROM], searchSpecialQuery.getEndcontractfrom(0), 0, 30,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%> &nbsp;to&nbsp; 
                                                          <%=ControlDate.drawDateWithStyle(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_ENDCONTRACTTO], searchSpecialQuery.getEndcontractto(0), 0, 50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%></td>
                                                    </tr>
                                                    
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="21" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord("SORT_BY")%></div></td>
                                                          <td width="83%"> <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_SORTBY],"formElemen",null, ""+searchSpecialQuery.getSort(0), FrmSrcEmployee.getOrderValue(), FrmSrcEmployee.getOrderKey(), " onkeydown=\"javascript:fnTrapKD()\"") %></td> 
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td width="17%" height="21" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord("LIST_STYLE")%></div></td>
                                                      <td width="83%"> <%= ControlCombo.draw(frmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_STYLIST],"formElemen",null, ""+searchSpecialQuery.getStyle(0), FrmSrcEmployee.getStyleValue(), FrmSrcEmployee.getStyleKey(), "") %></td> 
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
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                            <td width="28%" class="command" nowrap><a href="javascript:cmdSearch()"><%=dictionaryD.getWord("SEARCH")%> 
                                                              <%=dictionaryD.getWord(I_Dictionary.EMPLOYEE)%></a></td>
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="10" height="8"></td>
                                                            <td width="20%"><a href="javascript:cmdSpecialQuery()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Special Query"></a></td>
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                            <td width="26%" class="command" nowrap><a href="javascript:cmdSpecialQuery()">Special 
                                                              Query</a></td>
                                                            <% 
															if(privAdd)
															{
															%>
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="10" height="8"></td>
                                                            <td width="20%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Employee"></a></td>
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                            <td width="26%" class="command" nowrap><a href="javascript:cmdAdd()"><%=dictionaryD.getWord("ADD")%> 
                                                              <%=dictionaryD.getWord("NEW")%> <%=dictionaryD.getWord(I_Dictionary.EMPLOYEE)%></a></td>
                                                            <%
															}
															else
															{
															%>
                                                            <td width="2%">&nbsp;</td>
                                                            <%
															}
															%>
                                                             <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>                                                           
                                                            <td width="20%"><a href="javascript:cmdHeadCount()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Employee Headcount"></a></td>
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                            <td width="28%" class="command" nowrap><a href="javascript:cmdHeadCount()"><%=dictionaryD.getWord("HEADCOUNT_DETAIL")%> </a></td>  <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>  
<td width="20%"><a href="javascript:cmdHistoryPosition()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Position History"></a></td>
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                            <td width="28%" class="command" nowrap><a href="javascript:cmdHistoryPosition()"><%=dictionaryD.getWord("POSITION_HISTORY")%></a></td>                                                             
                                                            
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