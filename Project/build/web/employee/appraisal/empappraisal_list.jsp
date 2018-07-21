
<% 
/* 
 * Page Name  		:  empappraisal_list.jsp
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
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_APPRAISAL, AppObjInfo.OBJ_PERFORMANCE_APPRAISAL); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>
<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass ){
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Employee","15%");
		ctrlist.addHeader("Employee Num","5%");
		ctrlist.addHeader("Comm.Date","10%");
		ctrlist.addHeader("Department","10%");
		ctrlist.addHeader("Position","10%");
		ctrlist.addHeader("Level","5%");
		ctrlist.addHeader("Rank","5%");
		ctrlist.addHeader("Appraisor","15%");
		ctrlist.addHeader("Date Appraisal","10%");
		ctrlist.addHeader("Total Score","5%");
		ctrlist.addHeader("Total Criteria","5%");
		ctrlist.addHeader("Score Average","5%");
				
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();

		for (int i = 0; i < objectClass.size(); i++) {
			Vector temp = (Vector)objectClass.get(i);
			
			EmpAppraisal empAppraisal = (EmpAppraisal)temp.get(0);
			Employee employee = (Employee)temp.get(1);
			Employee employee2 = (Employee)temp.get(2);
			Department department = (Department)temp.get(3);
			Position position = (Position)temp.get(4);
			Level level = (Level)temp.get(5);
			GroupRank groupRank = (GroupRank)temp.get(6);			
			
			Vector rowx = new Vector();

			rowx.add(employee.getFullName());
			rowx.add(employee.getEmployeeNum());
						
			if(employee.getCommencingDate()!= null)
				rowx.add(Formater.formatDate(employee.getCommencingDate(),"dd MMM yyyy"));	
			else
				rowx.add("");
				
			rowx.add(department.getDepartment());
			rowx.add(position.getPosition());
			rowx.add(level.getLevel());
			rowx.add(groupRank.getGroupName());
			rowx.add(employee2.getFullName());
			
			if(empAppraisal.getDateOfAppraisal()!=null)
				rowx.add(Formater.formatDate(empAppraisal.getDateOfAppraisal(),"dd MMM yyyy"));
			else
				rowx.add("");
				
			rowx.add(""+empAppraisal.getTotalScore());
			rowx.add(""+empAppraisal.getTotalCriteria());
			rowx.add(Formater.formatNumber(empAppraisal.getScoreAverage(),"#,###.00"));
				
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(empAppraisal.getOID()));
		}

		return ctrlist.draw();
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidEmpAppraisal = FRMQueryString.requestLong(request, "employee_appraisal_oid");


ControlLine ctrLine = new ControlLine();
CtrlEmpAppraisal ctrlEmpAppraisal = new CtrlEmpAppraisal(request);
SrcAppraisal srcAppraisal = new SrcAppraisal();
FrmSrcAppraisal frmSrcAppraisal = new FrmSrcAppraisal(request,srcAppraisal);
int iErrCode = FRMMessage.ERR_NONE;
String msgStr = "";
int recordToGet = 10;
int vectSize = 0;
String whereClause = "";
String orderClause = "";

if(iCommand == Command.LIST){
	frmSrcAppraisal.requestEntityObject(srcAppraisal);
}

if((iCommand==Command.NEXT)||(iCommand==Command.FIRST)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand == Command.BACK)){
	 try{ 
		srcAppraisal = (SrcAppraisal)session.getValue(SessAppraisal.SESS_SRC_APPRAISAL); 
	 }catch(Exception e){ 
		srcAppraisal = new SrcAppraisal();
	 }
 }

SessAppraisal sessAppraisal = new SessAppraisal();
session.putValue(sessAppraisal.SESS_SRC_APPRAISAL, srcAppraisal);
	
//out.println("prevCommand "+prevCommand);
//out.println("oidEmployee "+oidEmployee);
if(iCommand == Command.SAVE && prevCommand == Command.ADD){
	start = PstEmployee.findLimitStart(oidEmpAppraisal,recordToGet, whereClause,orderClause);
	vectSize = PstEmployee.getCount(whereClause);
}else{
	vectSize = SessAppraisal.countAppraisal(srcAppraisal);	
}


long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
System.out.println("hrdDepartmentOid : "+hrdDepartmentOid);

if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||
	(iCommand==Command.LAST)||(iCommand==Command.LIST))
		start = ctrlEmpAppraisal.actionList(iCommand, start, vectSize, recordToGet);

Vector listEmpAppraisal = new Vector(1,1);
if(iCommand == Command.SAVE && prevCommand==Command.ADD){
	listEmpAppraisal = SessAppraisal.searchAppraisal(new SrcAppraisal(), start, recordToGet);
}else{
        
	listEmpAppraisal = SessAppraisal.searchAppraisal(srcAppraisal, start, recordToGet);
}
	

%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Employee Appraisal</title>
<script language="JavaScript">

	function cmdAdd(){
		document.frm_empappraisal.command.value="<%=Command.ADD%>";
		document.frm_empappraisal.action="empappraisal_edit.jsp";
		document.frm_empappraisal.submit();
	}

	function cmdEdit(oid){
		document.frm_empappraisal.command.value="<%=Command.EDIT%>";
		document.frm_empappraisal.employee_appraisal_oid.value=oid;
		document.frm_empappraisal.action="empappraisal_edit.jsp";
		document.frm_empappraisal.submit();
	}

	function cmdListFirst(){
		document.frm_empappraisal.command.value="<%=Command.FIRST%>";
		document.frm_empappraisal.action="empappraisal_list.jsp";
		document.frm_empappraisal.submit();
	}

	function cmdListPrev(){
		document.frm_empappraisal.command.value="<%=Command.PREV%>";
		document.frm_empappraisal.action="empappraisal_list.jsp";
		document.frm_empappraisal.submit();
	}

	function cmdListNext(){
		document.frm_empappraisal.command.value="<%=Command.NEXT%>";
		document.frm_empappraisal.action="empappraisal_list.jsp";
		document.frm_empappraisal.submit();
	}

	function cmdListLast(){
		document.frm_empappraisal.command.value="<%=Command.LAST%>";
		document.frm_empappraisal.action="empappraisal_list.jsp";
		document.frm_empappraisal.submit();
	}

	function cmdBack(){
		document.frm_empappraisal.command.value="<%=Command.BACK%>";
		document.frm_empappraisal.action="srcappraisal.jsp";
		document.frm_empappraisal.submit();
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
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
			  <!-- #BeginEditable "contenttitle" -->Employee 
                  &gt; Employee Appraisal Search Result<!-- #EndEditable --> 
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
                                    <form name="frm_empappraisal" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="employee_appraisal_oid" value="<%=oidEmpAppraisal%>">									
                                      <%if((listEmpAppraisal!=null)&&(listEmpAppraisal.size()>0)){%>
                                      <%=drawList(listEmpAppraisal)%> 
                                      <%}else{%>
                                      <span class="comment"><br>
                                      &nbsp;No Employee Appraisal available</span> 
                                      <%}%>
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td> 
                                            <table width="100%" cellspacing="0" cellpadding="3">
                                              <tr> 
                                                <td> 
                                                  <% ctrLine.setLocationImg(approot+"/images");
													ctrLine.initDefault();
												  %>
                                                  <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="46%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="46%" nowrap align="left" class="command">
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="3%"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
                                                <td width="23%" nowrap> <a href="javascript:cmdBack()" class="command"><b>Back 
                                                  To Search Employee Appraisal</b></a>
                                                </td>
												<% if(privAdd){%>
                                                <td width="3%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New"></a></td>
                                                <td width="71%"><b><a href="javascript:cmdAdd()" class="command">Add 
                                                  New Employee Appraisal</a></b></td>
												  <%}else{%>
												  <td width="74%">&nbsp;</td>
												  <%}%>
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
{script}
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
