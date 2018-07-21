 
<%@page import="javax.activation.DataSource"%>
<%@page import="com.dimata.util.net.MailSender"%>
<%@page import="com.dimata.util.net.MailProcess"%>
<%
/*
 * email_logs.jsp
 *
 * Created on April 04, 2002, 11:30 AM
 *
 * @author  ktanjana
 * @version 
 */
%>
<%@ page language="java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.harisma.form.admin.*" %>
<%@ include file = "../main/javainit.jsp" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<% //int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_SERVICE_CENTER, AppObjInfo.OBJ_SERVICE_EMAIL); %>
<%  int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_MANUAL_CHECKING, AppObjInfo.OBJ_MANUAL_CHECKING_ALL); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>
<!-- JSP Block -->
<%!

public String drawListEmailLogs( boolean privViewDetail,String subject, String emailbody, int emailstatus)
{
	String temp = ""; 
	String regdatestr = "";
	
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("tableheader");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("tableheader");
	ctrlist.addHeader("No.","5%");
	ctrlist.addHeader("TO","10%");
        ctrlist.addHeader("CC","5%");
        ctrlist.addHeader("BCC","5%");
	ctrlist.addHeader("Subject","20%");
        if(privViewDetail){
            ctrlist.addHeader("Body","30%");
            ctrlist.addHeader("Att","5%");
        }
        ctrlist.addHeader("status","5%");
        ctrlist.addHeader("Process Msg","20%");
        
	ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");

	Vector lstData = ctrlist.getData();

	Vector lstLinkData 	= ctrlist.getLinkData();

	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();

        Vector objectClass = MailSender.getvMailProcess();
        if (objectClass != null) {
                for (int i = 0; i < objectClass.size(); i++) {
                    MailProcess mail = (MailProcess) objectClass.get(i);

                    Vector rowx = new Vector();
                    rowx.add("" + (i + 1));
                    rowx.add(String.valueOf(mail.getStrRecTo()));
                    rowx.add(String.valueOf(mail.getStrRecCC()));
                    rowx.add(String.valueOf(mail.getStrRecBCC()));
                    rowx.add(String.valueOf(mail.getSubject()));
                    if (privViewDetail) {
                        
                        String bodyMsg = "<div>" + String.valueOf(mail.getTxtMessage()) +"<div>";
                        rowx.add("" + ( (/*(bodyMsg!=null && bodyMsg.length()>120) ? bodyMsg.substring(0, 120) :*/ bodyMsg )+"...<a href='javascript:openEmail(\""+ mail.getOID()+"\")'>more</a>" ) +" ");
                        Vector<DataSource> attchData = mail.getvAttachmentData();
                        String attLink = "";
                        if(attchData!=null && attchData.size()>0){
                            for(int it=0; it < attchData.size() ; it++){
                                DataSource dAtt = (DataSource) attchData.get(it);
                                attLink = attLink + "<a href=\""+ String.valueOf(dAtt)+"\"> Att."+ (it+1) + "</a>  "; 
                            }
                        }
                        rowx.add(""+ attLink);
                    }
                    rowx.add(String.valueOf(MailProcess.strStatus[mail.getStatus()]));
                    rowx.add(String.valueOf(mail.getProcessMessage()));

                    lstData.add(rowx);
                    lstLinkData.add(String.valueOf(mail.getOID()));
                }
            }
	return ctrlist.draw();
}

%>
<%
int listCommand = FRMQueryString.requestCommand(request); 
String subject =  FRMQueryString.requestString(request, "subject");
String emailbody =  FRMQueryString.requestString(request, "emailbody");
int emailstatus =  FRMQueryString.requestInt(request, "emailstatus");
long emailOID =  FRMQueryString.requestLong(request, "emailOID");
/* VARIABLE DECLARATION */
int recordToGet = 100;

%>
<!-- End of JSP Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - System Email Logs</title>
<script language="JavaScript">
<% if (privAdd){%>
function addNew(){
	document.frmEmailLogs.emailOID.value="0";
	document.frmEmailLogs.list_command.value="<%=listCommand%>";
	document.frmEmailLogs.command.value="<%=Command.ADD%>";
	document.frmEmailLogs.action="email_logs.jsp";
	document.frmEmailLogs.submit();
}
<%}%>

<% if (privViewDetail){%>
function openEmail(oid){
	document.frmEmailLogs.emailOID.value="0";
	document.frmEmailLogs.list_command.value="<%=listCommand%>";
	document.frmEmailLogs.command.value="<%=Command.EDIT%>";
	document.frmEmailLogs.target="EDITEMAIL";
        document.frmEmailLogs.action="email_detail.jsp";
	document.frmEmailLogs.submit();
}
<%}%>
 
function cmdEdit(oid){
	document.frmEmailLogs.emailOID.value=oid;
	document.frmEmailLogs.list_command.value="<%=listCommand%>";
	document.frmEmailLogs.command.value="<%=Command.EDIT%>";
	document.frmEmailLogs.action="email_logs.jsp";
	document.frmEmailLogs.submit();
}

function cmdListFirst(){
	document.frmEmailLogs.command.value="<%=Command.FIRST%>";
	document.frmEmailLogs.list_command.value="<%=Command.FIRST%>";
	document.frmEmailLogs.action="email_logs.jsp";
	document.frmEmailLogs.submit();
}
function cmdListPrev(){
	document.frmEmailLogs.command.value="<%=Command.PREV%>";
	document.frmEmailLogs.list_command.value="<%=Command.PREV%>";
	document.frmEmailLogs.action="email_logs.jsp";
	document.frmEmailLogs.submit();
}

function cmdListNext(){
	document.frmEmailLogs.command.value="<%=Command.NEXT%>";
	document.frmEmailLogs.list_command.value="<%=Command.NEXT%>";
	document.frmEmailLogs.action="email_logs.jsp";
	document.frmEmailLogs.submit();
}
function cmdListLast(){
	document.frmEmailLogs.command.value="<%=Command.LAST%>";
	document.frmEmailLogs.list_command.value="<%=Command.LAST%>";
	document.frmEmailLogs.action="email_logs.jsp";
	document.frmEmailLogs.submit();
}
function cmdSearch(){
	document.frmEmailLogs.command.value="<%=Command.SUBMIT%>";
	document.frmEmailLogs.action="email_logs.jsp";
	document.frmEmailLogs.submit();
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
	
	function showObjectForMenu(){        
    }
</SCRIPT>
<script language="JavaScript">
<!--
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
</script>
<link rel="stylesheet" href="../css/default.css" type="text/css">
<!-- #EndEditable -->
</head> 

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
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
			  <!-- #BeginEditable "contenttitle" --> System Service > Email Logs<!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                    <tr> 
                      <td valign="top"> 
                          <form name="frmEmailLogs" method="post" action="">
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                            <tr>
                                <td>
                                    <table width="100%" border="0" cellspacing="1" cellpadding="0">

                                        <td height="21" valign="middle" colspan="4"> 
                                            <div align="left"><b><font color="#000000">Search For User List</font></b></div>
                                        </td>
                                        <tr>
                                            <td>
                                                <table>
                                                    <tr>
                                                        <td>
                                                            <form name="search" method="get" action="">
                                                                <tr align="left" valign="top">
                                                                    <td height="21" valign="top" width="7%">
                                                                        <div align="left">Subject&nbsp;</div>
                                                                    </td>
                                                                    <td height="21" colspan="3">
                                                                        <input onFocus="this.select()" name="subject"  type="text" id="subject" size="25" maxlength="25" value="<%=subject == null || subject.length() == 0 ? "" : subject%>">
                                                                    </td>
                                                                </tr>
                                                                <tr align="left" valign="top">
                                                                    <td height="21" valign="top" width="7%">
                                                                        <div align="left">Email Body&nbsp;</div>
                                                                    </td>
                                                                    <td height="21" colspan="3">
                                                                        <input onFocus="this.select()" name="emailbody"  type="text" id="emailbody" size="25" maxlength="25" value="<%=emailbody == null || emailbody.length() == 0 ? "" : emailbody%>">
                                                                    </td>
                                                                </tr>
                                                <tr align="left" valign="top">
                                                    <td height="21" valign="top" width="7%">
                                                      <div align="left">Status&nbsp;</div>
                                                    </td>
                                                    <td height="21" colspan="3">
                                            <%
                                                ControlCombo cmbox = new ControlCombo();
                                              
                                            %>
                                            <%=cmbox.draw("emailstatus" ,"formElemen", null, Integer.toString(emailstatus), MailProcess.getValStatus(),  MailProcess.getStringStatus())%> 
                                            &nbsp;
                                            <input type="submit" value="search" onclick ="javascript:cmdSearch();" >
                                                    </td>
                                                </tr>
                                                <tr align="left" valign="top"> 
                                                    <td height="8" valign="middle" width="7%">&nbsp;</td>
                                                </tr>
                                                </form>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                              </table>
                                            </td>
                                        </tr>
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" -->                                                                         
                                    
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="emailOID" value="<%=emailOID%>">
                                      <input type="hidden" name="list_command" value="<%=listCommand%>">
                                      <table width="100%" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td colspan="2" class="listtitle">&nbsp;</td>
                                        </tr>
                                        
                                        <tr>
                                            <td>
                                             
                                                <%=drawListEmailLogs(privViewDetail, subject, emailbody, emailstatus)%> 
                                             
                                            </td>
                                        </tr>
                                      </table>
                                   
                                    <!-- #EndEditable -->
                            </td>
                          </tr>
                        </table> </form>
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
<!-- #BeginEditable "script" --><!-- #EndEditable -->
<!-- #EndTemplate --></html>
