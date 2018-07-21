<%@page import="com.dimata.harisma.form.CtrlEmpRelvtDocPage"%>
<% 
/* 
 * Page Name  		:  careerpath.jsp
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import="com.dimata.util.blob.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
long oidEmpRelevantDocPages = FRMQueryString.requestLong(request, "relevant_doc_pages_oid");
long oidEmpRelevantDoc = FRMQueryString.requestLong(request, "emp_relevant_doc_id");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_DOC_RELEVANT_ID]+ " = "+oidEmpRelevantDoc;
String orderClause = PstEmpRelvtDocPage.fieldNames[PstEmpRelvtDocPage.FLD_PAGE_TITLE];

CtrlEmpRelvtDocPage ctrlEmpRelvtDocPage = new CtrlEmpRelvtDocPage(request);
ControlLine ctrLine = new ControlLine();
Vector listEmpRelevantDocPages = new Vector(1,1);

/*switch statement */
iErrCode = ctrlEmpRelvtDocPage.action(iCommand , oidEmpRelevantDocPages);
/* end switch*/
FrmEmpRelvtDocPage frmEmpRelvtDocPage = ctrlEmpRelvtDocPage.getForm();

EmpRelvtDocPage empRelvtDocPage = ctrlEmpRelvtDocPage.getEmpRelvtDocPage();
msgString =  ctrlEmpRelvtDocPage.getMessage();

Vector vectPict = new Vector(1,1);
vectPict.add(""+oidEmpRelevantDocPages);
 
Vector vectPictDoc = new Vector(1,1);
vectPictDoc.add(""+oidEmpRelevantDoc);
 
session.putValue("SELECTED_PHOTO_SESSION", vectPict);
session.putValue("OID_DOC", vectPictDoc);
System.out.println("vectPict.........."+vectPict);
%>
<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Relevant Document </title>
<script language="JavaScript">
function cmdUpload(){
	document.frm_upload_pict.command.value="<%=Command.SAVE%>";
	document.frm_upload_pict.prev_command.value="<%=prevCommand%>";
	document.frm_upload_pict.action="upload_relevant_doc_pages.jsp";
	document.frm_upload_pict.submit();
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
      <!-- #EndEditable --> </td>
  </tr>
  <tr> 
    <td bgcolor="#9BC1FF"  ID="MAINMENU" valign="middle" height="15"> <!-- #BeginEditable "menumain" --> 
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
                  &gt; Relevant Document<!-- #EndEditable --> </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                          <tr> 
                            <td valign="top"> <!-- #BeginEditable "content" --> 
                              <form name="frm_upload_pict" method ="post" enctype="multipart/form-data" action="">
                                <input type="hidden" name="command" value="">
								<input type="hidden" name="relevant_doc_pages_oid" value="<%=oidEmpRelevantDocPages%>">
                                                                <input type="hidden" name="emp_relevant_doc_id" value="<%=oidEmpRelevantDoc%>">
								<input type="hidden" name="prev_command" value="<%=prevCommand%>">
								
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                  
                                  <tr> 
                                    <td class="tablecolor"> 
                                      <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                        <tr> 
                                          <td valign="top"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabbg">
                                              <tr align="left" valign="top"> 
                                                <td height="8"  colspan="3"> 
                                                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr align="left" valign="top"> 
                                                      <td height="14" valign="middle" colspan="3" class="listedittitle">&nbsp;</td>
                                                    </tr>
                                                    <% if(oidEmpRelevantDocPages != 0){
                                                            EmpRelvtDocPage objEmpRelvtDocPage = new EmpRelvtDocPage();
                                                            try{
                                                                 objEmpRelvtDocPage = PstEmpRelvtDocPage.fetchExc(oidEmpRelevantDocPages);
                                                            }catch(Exception exc){
                                                                 objEmpRelvtDocPage = new EmpRelvtDocPage();
                                                            }
                                                      %>
                                                    <tr align="left" valign="top"> 
                                                      <td height="14" valign="middle" colspan="3" class="listedittitle"> 
                                                        <table width="100%" border="0" cellspacing="2" cellpadding="1">
                                                          <tr> 
                                                            <td width="17%"><strong>Title </strong>
                                                               </td>
                                                            <td width="2%">:</td>
                                                            <td width="81%"><strong><%=objEmpRelvtDocPage.getPageTitle()%></strong></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="17%"><strong>Description</strong></td>
                                                            <td width="2%">:</td>
                                                            <td width="81%"><strong><%=objEmpRelvtDocPage.getPageDesc()%></strong></td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td height="14" valign="middle" colspan="3" class="listedittitle">&nbsp;</td>
                                                    </tr>
                                                    <%}%>
													<tr><td>
													<table width="100%" border="0">
													 <tr align="left" valign="top"> 
                                                      <td height="22" valign="middle" colspan="3" class="listtitle"> 
                                                        &nbsp;Attach File &nbsp&nbsp NOTE : File name may not consist special character like ' , " , /, `, $, @ !
                                                      </td>
                                                    </tr>
													  <tr>
														
														<td><input type="file" name="pict" size="60" height="100"></td>
													  </tr>
													</table>
													<table cellpadding="0" cellspacing="0" border="0">
                                                          <tr> 
                                                            <td width="4" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                            <td width="24" height="25"><a href="javascript:cmdUpload()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                            <td width="6" height="25"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                            <td height="25" valign="middle" colspan="3" width="951"><a href="javascript:cmdUpload()" class="command">Save Document</a> 
                                                            </td>
                                                          </tr>
                                                        </table>
													</td></tr>
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
