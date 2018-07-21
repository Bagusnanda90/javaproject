
<% 
/* 
 * Page Name  		:  srcemployeevisit.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [lkarunia] 
 * @version  		:  [version] 
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
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.entity.clinic.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.form.clinic.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.session.clinic.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_CLINIC, AppObjInfo.G2_EMPLOYEE_VISIT, AppObjInfo.OBJ_EMPLOYEE_VISIT); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;

if(isHRDLogin){
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privPrint=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%!

	public String drawList(Vector objectClass ){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("98%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.dataFormat("Visiting <BR>Date","8%","center","center");		
		ctrlist.dataFormat("Name ","18%","center","left");
		ctrlist.dataFormat("Department ","15%","center","left");		
		ctrlist.dataFormat("Payroll Number ","8%","center","center");
		ctrlist.dataFormat("Diagnose","15%","center","left");
		ctrlist.dataFormat("Visited By","18%","center","left");
		ctrlist.dataFormat("Remarks","18%","center","left");
 
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData(); 
		Vector lstLinkData = ctrlist.getLinkData(); 		
		ctrlist.reset();

		for (int i = 0; i < objectClass.size(); i++) {
			Vector temp = (Vector)objectClass.get(i);
			EmployeeVisit employeeVisit = (EmployeeVisit)temp.get(0);
			Employee employee = (Employee)temp.get(1);
			Department department = (Department)temp.get(2);
			Employee visitor =(Employee)temp.get(3);
			Vector rowx = new Vector();

			String str_dt_VisitDate = ""; 
			try{
				Date dt_VisitDate = employeeVisit.getVisitDate();
				if(dt_VisitDate==null){
					dt_VisitDate = new Date();
				}

				str_dt_VisitDate = Formater.formatDate(dt_VisitDate, "dd/MM/yy");
			}catch(Exception e){ str_dt_VisitDate = ""; }

			rowx.add(str_dt_VisitDate);
			rowx.add("<a href=\"javascript:cmdEdit('"+employeeVisit.getOID()+"')\">"+employee.getFullName()+"</a>");
			rowx.add(department.getDepartment());
			rowx.add(employee.getEmployeeNum());
			rowx.add(employeeVisit.getDiagnose());
			rowx.add(visitor.getFullName());
			rowx.add(employeeVisit.getDescription());

			lstData.add(rowx);			
		}

		return ctrlist.drawMe();
	}

%>

<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long empVisitOID = FRMQueryString.requestLong(request, "employee_visit_oid");

int recordToGet = 10;
int vectSize = 0;
ControlLine ctrLine = new ControlLine(); 
CtrlEmployeeVisit ctrlEmployeeVisit = new CtrlEmployeeVisit(request); 
SessEmployeeVisit sessEmployeeVisit = new SessEmployeeVisit();
SrcEmployeeVisit srcEmployeeVisit = new SrcEmployeeVisit();


FrmSrcEmployeeVisit frmSrcEmployeeVisit = new FrmSrcEmployeeVisit(request, srcEmployeeVisit);
frmSrcEmployeeVisit.requestEntityObject(srcEmployeeVisit);


if(iCommand != Command.LIST && iCommand != Command.NONE){
	try{
		srcEmployeeVisit = (SrcEmployeeVisit)session.getValue(SessEmployeeVisit.SESS_SRC_EMPVISIT);
		if(srcEmployeeVisit == null)
			srcEmployeeVisit = new SrcEmployeeVisit();
	}catch(Exception e){		
		srcEmployeeVisit = new SrcEmployeeVisit();
	}
	iCommand = prevCommand; 
}
//if add new employee visit, system will be find start and iCommand where is new data...
vectSize = sessEmployeeVisit.countEmployeeVisit(srcEmployeeVisit);
if(iCommand == Command.ADD){
	start = PstEmployeeVisit.findLimitStart(empVisitOID, recordToGet,vectSize,srcEmployeeVisit);	
	iCommand = PstEmployeeVisit.findLimitCommand(start, recordToGet, vectSize);
}
//if start = vectSize when delete employee visit 
if(start == vectSize){
	if(vectSize > recordToGet)
		start = vectSize - recordToGet;
	else
		start = 0;
}

Vector listEmployeeVisit = new Vector(1,1);
if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||
	(iCommand==Command.LAST)||(iCommand==Command.LIST)){		
		start = ctrlEmployeeVisit.actionList(iCommand, start, vectSize, recordToGet);		
		listEmployeeVisit = sessEmployeeVisit.searchEmployeeVisit(srcEmployeeVisit, start, recordToGet);
}

session.putValue(SessEmployeeVisit.SESS_SRC_EMPVISIT,srcEmployeeVisit);
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Employee Visit</title>
<script language="JavaScript">

function cmdAdd(){
	document.frmsrcemployeevisit.command.value="<%=Command.ADD%>";
	document.frmsrcemployeevisit.prev_command.value="<%=Command.ADD%>";
	document.frmsrcemployeevisit.employee_visit_oid.value="";
	document.frmsrcemployeevisit.action="employeevisit_edit.jsp";
	document.frmsrcemployeevisit.submit();
}

function cmdSearch(){
	document.frmsrcemployeevisit.command.value="<%=Command.LIST%>";
	document.frmsrcemployeevisit.prev_command.value="<%=Command.LIST%>";
	document.frmsrcemployeevisit.action="srcemployeevisit.jsp";
	document.frmsrcemployeevisit.submit();
}

function cmdEdit(oid){
	document.frmsrcemployeevisit.command.value="<%=Command.EDIT%>";
	document.frmsrcemployeevisit.prev_command.value="<%=prevCommand%>";
	document.frmsrcemployeevisit.employee_visit_oid.value=oid;
	document.frmsrcemployeevisit.action="employeevisit_edit.jsp";
	document.frmsrcemployeevisit.submit();
}

function cmdListFirst(){
	document.frm_employeevisit.command.value="<%=Command.FIRST%>";
	document.frm_employeevisit.prev_command.value="<%=Command.FIRST%>";
	document.frm_employeevisit.action="srcemployeevisit.jsp";
	document.frm_employeevisit.submit();
}

function cmdListPrev(){
	document.frm_employeevisit.command.value="<%=Command.PREV%>";
	document.frm_employeevisit.prev_command.value="<%=Command.PREV%>";
	document.frm_employeevisit.action="srcemployeevisit.jsp";
	document.frm_employeevisit.submit();
}

function cmdListNext(){
	document.frm_employeevisit.command.value="<%=Command.NEXT%>";
	document.frm_employeevisit.prev_command.value="<%=Command.NEXT%>";
	document.frm_employeevisit.action="srcemployeevisit.jsp";
	document.frm_employeevisit.submit();
}

function cmdListLast(){
	document.frm_employeevisit.command.value="<%=Command.LAST%>";
	document.frm_employeevisit.prev_command.value="<%=Command.LAST%>";
	document.frm_employeevisit.action="srcemployeevisit.jsp";
	document.frm_employeevisit.submit();
}

function cmdPrint()
{
	
		var empName  = document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_EMPLOYEE_NAME]%>.value;
		var depart   = document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_DEPARTMENT] %>.value;		
		var startYear  = document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_FROM]+"_yr"%>.value;								
		var startMonth = document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_FROM]+"_mn"%>.value;
		var startDate  = document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_FROM]+"_dy"%>.value;
		var dueYear    = document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_TO]+"_yr"%>.value;				
		var dueMonth   = document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_TO]+"_mn"%>.value;				
		var dueDate    = document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_TO]+"_dy"%>.value;	
		var sortBy     = 0;
		var len = document.frmsrcemployeevisit.elements.length;		
		for (var i=0; i < len;i++){
			if(document.frmsrcemployeevisit.elements[i].checked){
				sortBy    = document.frmsrcemployeevisit.elements[i].value;				
			}
		}
		var linkPage   = "empvisit_buffer.jsp?" +
						 "<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_EMPLOYEE_NAME] %>=" + empName + "&" +
						 "<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_DEPARTMENT] %>=" + depart + "&" +						 
						 "<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_FROM]%>_yr=" + startYear + "&" + 
						 "<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_FROM]%>_mn=" + startMonth + "&" + 
						 "<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_FROM]%>_dy=" + startDate + "&" + 						 
						 "<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_TO]%>_yr=" + dueYear + "&" + 
						 "<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_TO]%>_mn=" + dueMonth + "&" + 
						 "<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_TO]%>_dy=" + dueDate + "&" + 
						 "<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_SORT_BY]%>=" + sortBy;  		
		window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			
	
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
	document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_FROM] +"_yr"%>.style.visibility = "hidden";  
	document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_FROM] +"_mn"%>.style.visibility= "hidden";   
	document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_FROM] +"_dy"%>.style.visibility = "hidden";    
	document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_TO] +"_mn"%>.style.visibility = "hidden";    
	document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_DEPARTMENT]%>.style.visibility = "hidden";    
} 
	 
function hideObjectForLockers(){ 
}
	
function hideObjectForCanteen(){
}
	
function hideObjectForClinic(){
}

function hideObjectForMasterdata(){
	document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_TO] +"_yr"%>.style.visibility = "hidden";  
	//document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_TO] +"_mn"%>.style.visibility= "hidden";   
	document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_TO] +"_dy"%>.style.visibility = "hidden";    

}

function showObjectForMenu(){
	document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_FROM] +"_yr"%>.style.visibility = "";  
	document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_FROM] +"_mn"%>.style.visibility= "";   
	document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_FROM] +"_dy"%>.style.visibility = "";    
	document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_TO] +"_yr"%>.style.visibility = "";  
	document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_TO] +"_mn"%>.style.visibility= "";   
	document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_TO] +"_dy"%>.style.visibility = "";    
	document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_DEPARTMENT]%>.style.visibility = "";    
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
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" -->Clinic 
                  &gt; Employee Visit<!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td class="tablecolor"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmsrcemployeevisit" method="post" action="javascript:cmdSearch()">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
									  <input type="hidden" name="prev_command" value="<%=prevCommand%>">									  
									  <input type="hidden" name="employee_visit_oid" value="<%=empVisitOID%>">
                                      <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                        <tr> 
                                          <td height="21" valign="middle" width="10%" align="left">&nbsp;</td>
                                          <td height="21" valign="middle" width="19%" align="left">&nbsp;</td>
                                          <td height="21" width="13%" class="comment" align="left" valign="top">&nbsp;</td>
                                          <td height="21" width="58%" class="comment" align="left" valign="top">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td height="21" width="10%"> 
                                            <div align="right"><b>Search By &nbsp;&nbsp;</b></div>
                                          </td>
                                          <td height="21" width="19%" align="left">Employee 
                                            Name</td>
                                          <td height="21" colspan="2" align="left"> 
                                            <input type="text" name="<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_EMPLOYEE_NAME] %>"  value="<%= srcEmployeeVisit.getEmployeeName() %>" class="elemenForm" size="45">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td height="21" valign="top" width="10%" align="left">&nbsp;</td>
                                          <td height="21" valign="top" width="19%" align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                          <td height="21" colspan="2" align="left" valign="top"> 
                                            <%  Vector department_value = new Vector(1,1);
												Vector department_key = new Vector(1,1);
												Vector listDepartment = PstDepartment.listAll();
												for(int i = 0;i <listDepartment.size();i++){
													Department department = (Department)listDepartment.get(i);
													department_value.add(""+department.getOID());
													department_key.add(department.getDepartment());
												}
											  %>
                                            <%= ControlCombo.draw(frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_DEPARTMENT],"elementForm", "All Department", ""+srcEmployeeVisit.getDepartment(), department_value, department_key) %> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td height="21" valign="top" width="10%" align="left">&nbsp;</td>
                                          <td height="21" valign="top" width="19%" align="left">Visit 
                                            Date </td>
                                          <td height="21" colspan="2" align="left" valign="top">
										 <% Date strVisit = srcEmployeeVisit.getVisitDateFrom(); 
										    if(strVisit == null){
												strVisit = new Date();
												strVisit.setDate(1);
											}
											
											Date toVisit = srcEmployeeVisit.getVisitDateTo(); 
										    if(toVisit == null){
												toVisit = new Date();
												Calendar calendar = new GregorianCalendar();												
												toVisit.setDate(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
											}
										 %>		 
                                            <%=	ControlDate.drawDate(frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_FROM], strVisit, "formElemen",1,-5) %> 
                                            &nbsp;to&nbsp;<%=	ControlDate.drawDate(frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_VISIT_DATE_TO], toVisit,"formElemen", 1,-5) %> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td height="21" align="right" colspan="4"> 
                                            <hr>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td height="21" align="right" width="10%"><b>Sort 
                                            By </b> &nbsp;&nbsp;</td>
                                          <td height="21" colspan="2" align="left"> 
                                            <% 
										  Vector orderKey = FrmSrcEmployeeVisit.getOrderKey();
										  for(int i=0;i<orderKey.size();i++){
										  %>
                                            <input type="radio" name="<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_SORT_BY]%>" value="<%=i%>" <%if(srcEmployeeVisit.getSortBy()==i){%>checked<%}%>>
                                            <%=orderKey.get(i)%> 
                                            <%}%>
                                          </td>
                                          <td height="21" valign="top" width="58%" align="left"> 
                                            <input type="submit" name="Submit" value="Search" onClick="javascript:cmdSearch()">
                                            <input type="reset" name="Submit2" value="Reset">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td height="21" valign="top" colspan="4" align="left">
                                            <hr>
                                          </td>
                                        </tr>
                                      </table>
                                    </form>
                                    <form name="frm_employeevisit" method="post" action="">
									<input type="hidden" name="command" value="<%=iCommand%>">
									<input type="hidden" name="prev_command" value="<%=prevCommand%>">
									<input type="hidden" name="start" value="<%=start%>">
									  <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                        <% if(listEmployeeVisit != null && listEmployeeVisit.size()>0){%>
                                        <tr> 
                                          <td width="3%" class="listtitle">&nbsp;</td>
                                          <td width="63%" class="listtitle">Employee 
                                            Visit List</td>
                                          <td width="34%" class="listtitle">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td colspan="2"><%=drawList(listEmployeeVisit)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td colspan="2"> 
                                            <% ctrLine.setLocationImg(approot+"/images");
												ctrLine.initDefault();
											%>
                                            <%=ctrLine.drawImageListLimit(iCommand==Command.BACK?Command.FIRST:iCommand,vectSize,start,recordToGet)%></td>
                                        </tr>
                                        <tr>
                                          <td width="3%">&nbsp;</td>
                                          <td colspan="2"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr><%if(privAdd){%>
                                                <td width="21%"> 
                                                  <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr> 
                                                      <td height="34">&nbsp;</td>
                                                      <td width="11%" height="34"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New"></a></td>
                                                      <td width="4%" height="34"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td width="81%" class="command" nowrap height="34"><a href="javascript:cmdAdd()">Add 
                                                        New Employee Visit</a></td>
                                                    </tr>
                                                  </table>
                                                </td><%}%><%if(privPrint){%>
                                                <td width="24%"> 
                                                  <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr> 
                                                      <td height="34">&nbsp;</td>
                                                      <td width="11%" height="34"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                      <td width="4%" height="34"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                      <td width="81%" class="command" nowrap height="34"><a href="javascript:cmdPrint()">Print 
                                                        Employee Visit</a></td>
                                                    </tr>
                                                  </table>
                                                </td><%}%>
                                                <td width="55%">&nbsp;</td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <%}else{
											if(iCommand == Command.LIST){%>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td colspan="2" class="comment">No Employee 
                                            Visit match with search data</td>
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td colspan="2" class="comment">&nbsp;</td>
                                        </tr>
                                        <%  }
										}%>
                                        <%if(privAdd && (listEmployeeVisit == null || listEmployeeVisit.size()<1)){%>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="63%">                                             
											  <table width="30%" border="0" cellspacing="0" cellpadding="0">
												<tr> 
												  <td height="34">&nbsp;</td>
												  <td width="11%" height="34"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New"></a></td>
												  <td width="4%" height="34"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
												  <td width="81%" class="command" nowrap height="34"><a href="javascript:cmdAdd()">Add 
													New Employee Visit</a></td>
												</tr>
											  </table>                                               
                                          </td>
                                          <td width="34%">&nbsp;</td>
                                        </tr>
                                        <%}%>
                                        <tr> 
                                          <td height="14" width="3%">&nbsp;</td>
                                          <td height="14" width="63%">&nbsp;</td>
                                          <td height="14" width="34%">&nbsp;</td>
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<script language="JavaScript">
	document.frmsrcemployeevisit.<%=frmSrcEmployeeVisit.fieldNames[FrmSrcEmployeeVisit.FRM_FIELD_EMPLOYEE_NAME] %>.focus();
	
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);

</script>
<!-- #EndEditable -->
<!-- #EndTemplate -->
</html>
<%
}else{
%>    
    <script language="javascript">
              window.location="<%=approot%>/inform.jsp?ic=<%= I_SystemInfo.HAVE_NOPRIV%>";
    </script>             
<%
}
%>

