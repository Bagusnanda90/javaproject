
<% 
/* 
 * Page Name  		:  leave_list.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
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
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.session.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_APPLICATION, AppObjInfo.OBJ_LEAVE_APPLICATION); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//boolean privAdd=false;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//boolean privPrint=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<!-- Jsp Block -->
<%!
public String drawList(Vector objectClass )
{
	ControlList ctrlist = new ControlList();
	
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	
	ctrlist.addHeader("Payroll","8%");	
	ctrlist.addHeader("Employee","25%");	
	ctrlist.addHeader("Date Of Application","12%");		
	ctrlist.addHeader("Approval","18%");	
	
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();

	for (int i=0; i<objectClass.size(); i++) 
	{
		Vector temp = (Vector) objectClass.get(i);
		SpecialLeave objLeaveApplication = (SpecialLeave) temp.get(0);
		Employee employee = (Employee) temp.get(1);
                Employee empApp1 = (Employee) temp.get(2);
		Employee empApp2 = (Employee) temp.get(3);
		Employee empApp3 = (Employee) temp.get(4);
		
		String strSubmissionDate = ""; 
		try
		{
			Date dt_SubmitDate = objLeaveApplication.getRequestDate();
			if(dt_SubmitDate==null)
			{
				dt_SubmitDate = new Date();
			}
			strSubmissionDate = Formater.formatDate(dt_SubmitDate, "MMM dd, yyyy");
		}
		catch(Exception e)
		{ 
			strSubmissionDate = ""; 
		}

		String strApproval = "";
                
                if(empApp1.getFullName()!=null && objLeaveApplication.getApprovalId()>0){
                    strApproval += empApp1.getFullName();
                }
                if(empApp2.getFullName()!=null && objLeaveApplication.getApproval2Id()>0){
                    if(strApproval.length()>0){strApproval+=", <br>";}
                    strApproval += empApp2.getFullName();
                }
                if(empApp3.getFullName()!=null && objLeaveApplication.getApproval3Id()>0){
                    if(strApproval.length()>0){strApproval+=", <br>";}
                    strApproval += empApp3.getFullName();
                }
				
                Vector rowx = new Vector();
                rowx.add("<a href=\"javascript:cmdEdit('"+objLeaveApplication.getOID()+"','" + employee.getOID() + "')\">"+employee.getEmployeeNum()+"</a>");		
		rowx.add(employee.getFullName());                
		rowx.add(strSubmissionDate);		
		rowx.add(strApproval);		
									
		lstData.add(rowx);
	}
	return ctrlist.draw();
}
%>

<%
ControlLine ctrLine = new ControlLine();
Control ctrlLeave = new Control();
long oidLeave = FRMQueryString.requestLong(request, "hidden_leave_application_id");

int iErrCode = FRMMessage.ERR_NONE;
String msgStr = "";
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int recordToGet = 10;
int vectSize = 0;
String whereClause = "";

SrcLeaveApplication srcLeaveApplication = new SrcLeaveApplication();
FrmSrcLeaveApplication frmSrcLeaveApplication = new FrmSrcLeaveApplication(request, srcLeaveApplication);
frmSrcLeaveApplication.requestEntityObject(srcLeaveApplication);

if((iCommand==Command.NEXT) || (iCommand==Command.FIRST) || (iCommand==Command.PREV) || (iCommand==Command.LAST))
{
	try
	{ 
		srcLeaveApplication = (SrcLeaveApplication)session.getValue(SessLeaveApplication.SESS_SRC_LEAVE_APPLICATION); 
		if (srcLeaveApplication == null) 
		{
			srcLeaveApplication = new SrcLeaveApplication();
		}
	}
	catch(Exception e)
	{ 
		srcLeaveApplication = new SrcLeaveApplication();
	}
}

SessLeaveApplication sessLeaveApplication = new SessLeaveApplication();
session.putValue(SessLeaveApplication.SESS_SRC_LEAVE_APPLICATION, srcLeaveApplication);
vectSize = sessLeaveApplication.getCountSearchLeaveApplicationHR(srcLeaveApplication);

if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST))
{
	start = ctrlLeave.actionList(iCommand, start, vectSize, recordToGet);
}

Vector records = sessLeaveApplication.searchLeaveApplicationHR(srcLeaveApplication, start, recordToGet);
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Leave Application</title>
<script language="JavaScript">
function cmdAdd()
{
	document.frm_leave_application.command.value="<%=Command.ADD%>";
	document.frm_leave_application.action="leave_request.jsp";
	document.frm_leave_application.submit();
}

function cmdEdit(oidLeave, oidEmployee)
{
	document.frm_leave_application.command.value="<%=Command.EDIT%>";
        document.frm_leave_application.oid_leave.value = oidLeave;
	document.frm_leave_application.oid_employee.value = oidEmployee;
	document.frm_leave_application.action="leave_request.jsp";
	document.frm_leave_application.submit();
}

function cmdListFirst()
{
	document.frm_leave_application.command.value="<%=Command.FIRST%>";
	document.frm_leave_application.action="leave_application_list_hr.jsp";
	document.frm_leave_application.submit();
}

function cmdListPrev()
{
	document.frm_leave_application.command.value="<%=Command.PREV%>";
	document.frm_leave_application.action="leave_application_list_hr.jsp";
	document.frm_leave_application.submit();
}

function cmdListNext()  
{
	document.frm_leave_application.command.value="<%=Command.NEXT%>";
	document.frm_leave_application.action="leave_application_list_hr.jsp";
	document.frm_leave_application.submit();
}

function cmdListLast()
{
	document.frm_leave_application.command.value="<%=Command.LAST%>";
	document.frm_leave_application.action="leave_application_list_hr.jsp";
	document.frm_leave_application.submit();
}

function cmdBack()
{
	document.frm_leave_application.command.value="<%=Command.BACK%>";
	document.frm_leave_application.action="src_leave_application_hr.jsp";
	document.frm_leave_application.submit();
}

function fnTrapKD()
{
	switch(event.keyCode) 
	{
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Leave &gt; Leave Application<!-- #EndEditable --> 
                  </strong></font> </td>
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
							  <%
							  if(privView)
							  {
							  %> 
								<form name="frm_leave_application" method="post" action="">
								  <input type="hidden" name="command" value="">
								  <input type="hidden" name="start" value="<%=start%>">
								  <input type="hidden" name="hidden_leave_application_id" value="<%=oidLeave%>">
								  <%
								  if((records!=null)&&(records.size()>0))
								  {
									  out.println(drawList(records)); 
								  }										  
								  else
								  {
									  out.println("<span class=\"comment\"><br>&nbsp;Records is empty ...</span>");
								  }
								  %>
								  <table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr> 
									  <td> 
										<table width="100%" cellspacing="0" cellpadding="3">
										  <tr> 
											<td> 
											  <% 
											  ctrLine.setLocationImg(approot+"/images");
											  ctrLine.initDefault();
											  out.println(ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet));
											  %>
											</td>
										  </tr>
										</table>
									  </td>
									</tr>
									<tr> 
									  <td width="46%">&nbsp;</td>
									</tr>
									<tr> 
									  <td width="46" nowrap align="left" class="command">
										    <table width="51%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To Search Leave Application"></a></td>
                                                <td width="5"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="229" nowrap> <a href="javascript:cmdBack()" class="command">Back 
                                                  To Search Leave Application</a></td>
                                                <%-- if(privAdd){%>
                                                <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Leave Application"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="210" nowrap><b><a href="javascript:cmdAdd()" class="command">Add 
                                                  New Leave Application</a></b></td>
                                                <% } --%>
                                              </tr>
                                            </table>
									  </td>
									</tr>
								  </table>
                                                                  
                                                                  <input type="hidden" name="oid_leave" value="">                           
                                                                  <input type="hidden" name="oid_employee" value="">     
                                                                  <input type="hidden" name="oid_period" value="">
								</form>
								<%
								}
								else
								{
								%>
								<div align="center">You do not have sufficient privilege to access this page.</div>
								<%
								}
								%>
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
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
