
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
<%@ page import = "com.dimata.harisma.entity.leave.dp.*" %>
<%@ page import = "com.dimata.harisma.session.leave.dp.*" %>
<%@ page import = "com.dimata.harisma.form.leave.ll.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.ll.*" %>
<%@ page import = "com.dimata.harisma.session.leave.ll.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_APPLICATION, AppObjInfo.OBJ_LEAVE_APPLICATION); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//boolean privAdd=false;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));  
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
privPrint = true;
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
	ctrlist.addHeader("Employee","20%");	
	ctrlist.addHeader("Submission Date","12%");		
	ctrlist.addHeader("Taken Date","32%");	
	ctrlist.addHeader("Approval","20%");	
	ctrlist.addHeader("Doc Status","8%");		
	
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
                Vector vDpAppDetail = new Vector(1,1);
		LLAppMain llAppMain = (LLAppMain) temp.get(0);
		Employee employee = (Employee) temp.get(1);
		Employee empApp1 = (Employee) temp.get(2);
		Employee empApp2 = (Employee) temp.get(3);
		Employee empApp3 = (Employee) temp.get(4);
		
		
		Vector rowx = new Vector();		
		rowx.add(employee.getEmployeeNum());		
		rowx.add(employee.getFullName());
                
                String strSubmissionDate = "";
                String strTakenDate = "";
                String strApproval = "";
                
                if(empApp1.getFullName()!=null && llAppMain.getApprovalId()>0){
                    strApproval += empApp1.getFullName();
                }
                if(empApp2.getFullName()!=null && llAppMain.getApproval2Id()>0){
                    if(llAppMain.getApproval2Id()!=llAppMain.getApprovalId()){
                        if(strApproval.length()>0){strApproval+=", <br>";}
                        strApproval += empApp2.getFullName();
                    }
                }
                if(empApp3.getFullName()!=null && llAppMain.getApproval3Id()>0){
                    if(llAppMain.getApprovalId()!=llAppMain.getApproval3Id() && llAppMain.getApproval2Id()!=llAppMain.getApproval3Id()){
                        if(strApproval.length()>0){strApproval+=", <br>";}
                        strApproval += empApp3.getFullName();
                    }
                }
                
                strSubmissionDate = Formater.formatDate(llAppMain.getSubmissionDate(), "dd-MMMM-yyyy");
                strTakenDate = createStrTakenDate(llAppMain.getStartDate(),llAppMain.getEndDate());
                
		rowx.add(strSubmissionDate);		
		rowx.add(strTakenDate);
		rowx.add(strApproval);
		rowx.add(PstDpApplication.fieldStatusNames[llAppMain.getDocumentStatus()]);		
									
		lstData.add(rowx);
                lstLinkData.add(String.valueOf(llAppMain.getOID()));
	}
	return ctrlist.draw();
}

private Date getTommorow(Date date){
   Date tdate = (Date)date.clone();
   tdate.setDate(date.getDate()+1);
   return tdate;
}

private String createStrTakenDate(Date startDate, Date endDate){
    String strTakenDate = "";
    strTakenDate += Formater.formatDate(startDate, "dd-MMM-yyyy")
            +" s/d "+Formater.formatDate(endDate, "dd-MMM-yyyy");
    return strTakenDate;
}

%>

<%
ControlLine ctrLine = new ControlLine();
CtrlLeave ctrlLeave = new CtrlLeave(request);
long oidLLAppMain = FRMQueryString.requestLong(request, "oidLLAppMain");

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
		srcLeaveApplication = (SrcLeaveApplication)session.getValue(SessLeaveApplication.SESS_SRC_DP_APPLICATION); 
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

SessLLAppMain sessLLAppMain = new SessLLAppMain();
//session.putValue(SessLeaveApplication.SESS_SRC_DP_APPLICATION, srcLeaveApplication);
vectSize = sessLLAppMain.countLLAppMain(srcLeaveApplication);

if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST))
{
	start = ctrlLeave.actionList(iCommand, start, vectSize, recordToGet);
}

Vector records = sessLLAppMain.listLLAppMain(start, recordToGet,srcLeaveApplication);
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - LL Application</title>
<script language="JavaScript">
function cmdAdd()
{
	document.frm_dp_application.command.value="<%=String.valueOf(Command.ADD)%>";
	document.frm_dp_application.action="ll_application_edit.jsp";
	document.frm_dp_application.submit();
}

function cmdEdit(oid)
{
	document.frm_dp_application.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frm_dp_application.oidLLAppMain.value = oid;
	document.frm_dp_application.action="ll_application_edit.jsp";
	document.frm_dp_application.submit();
}

function cmdListFirst()
{
	document.frm_dp_application.command.value="<%=String.valueOf(Command.FIRST)%>";
	document.frm_dp_application.action="ll_application_list.jsp";
	document.frm_dp_application.submit();
}

function cmdListPrev()
{
	document.frm_dp_application.command.value="<%=String.valueOf(Command.PREV)%>";
	document.frm_dp_application.action="ll_application_list.jsp";
	document.frm_dp_application.submit();
}

function cmdListNext()  
{
	document.frm_dp_application.command.value="<%=String.valueOf(Command.NEXT)%>";
	document.frm_dp_application.action="ll_application_list.jsp";
	document.frm_dp_application.submit();
}

function cmdListLast()
{
	document.frm_dp_application.command.value="<%=String.valueOf(Command.LAST)%>";
	document.frm_dp_application.action="ll_application_list.jsp";
	document.frm_dp_application.submit();
}

function cmdBack()
{
	document.frm_dp_application.command.value="<%=String.valueOf(Command.BACK)%>";
	document.frm_dp_application.action="src_ll_application.jsp";
	document.frm_dp_application.submit();
}

function fnTrapKD()
{
	switch(event.keyCode) 
	{
		case <%=String.valueOf(LIST_PREV)%>:
			cmdListPrev();
			break;
		case <%=String.valueOf(LIST_NEXT)%>:
			cmdListNext();
			break;
		case <%=String.valueOf(LIST_FIRST)%>:
			cmdListFirst();
			break;
		case <%=String.valueOf(LIST_LAST)%>:
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
                  Employee &gt; Leave Management &gt; LL Application<!-- #EndEditable --> 
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
								<form name="frm_dp_application" method="post" action="">
								  <input type="hidden" name="command" value="">
								  <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
								  <input type="hidden" name="oidLLAppMain" value="<%=String.valueOf(oidLLAppMain)%>">
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
                                                <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
                                                <td width="5"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="229" nowrap> <a href="javascript:cmdBack()" class="command">Back 
                                                  To Search LL Application</a></td>
                                                <%-- if(privAdd){%>
                                                <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New"></a></td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="210" nowrap><b><a href="javascript:cmdAdd()" class="command">Add 
                                                  New LL Application</a></b></td>
                                                <% } --%>
                                              </tr>
                                            </table>
									  </td>
									</tr>
								  </table>
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
