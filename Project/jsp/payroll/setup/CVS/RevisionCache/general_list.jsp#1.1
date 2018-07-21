<% 
/* 
 * Page Name  		:  employee_list.jsp
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
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_GENERAL);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privPrint=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<!-- Jsp Block -->
<%!
public String drawList(Vector objectClass, int st){
	
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("Nr.","2%");
	ctrlist.addHeader("Name","18%");
	ctrlist.addHeader("Address","20%");
	ctrlist.addHeader("City","6%");
	ctrlist.addHeader("Pos Code","7%");
	ctrlist.addHeader("Bussiness Type","7%");
	ctrlist.addHeader("Tel","5%");
	ctrlist.addHeader("Fax","5%");
	ctrlist.addHeader("Leader Name","10%");
	ctrlist.addHeader("Position","10%");
	ctrlist.addHeader("Work Days","5%");
	ctrlist.setLinkRow(1);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	//System.out.println("masuk"+objectClass.size());
	for (int j = 0; j < objectClass.size(); j++) {
		
	}
	for (int i = 0; i < objectClass.size(); i++) {
		//System.out.println("masukvector");
		PayGeneral payGeneral = (PayGeneral)objectClass.get(i);
		
		
		
		Vector rowx = new Vector();
		rowx.add(String.valueOf(st + 1 + i));
		rowx.add(payGeneral.getCompanyName());
		rowx.add(payGeneral.getCompAddress());
		rowx.add(payGeneral.getCity());
		rowx.add(payGeneral.getZipCode());
		rowx.add(payGeneral.getBussinessType());
		//rowx.add(PstEmployee.sexKey[employee.getSex()]);
		rowx.add(payGeneral.getTel());
		rowx.add(payGeneral.getFax());
		rowx.add(payGeneral.getLeaderName());
		rowx.add(payGeneral.getLeaderPosition());
		
		rowx.add(String.valueOf(payGeneral.getWorkDays()));
		//rowx.add(level.getLevel());
		
		lstData.add(rowx);
		lstLinkData.add(String.valueOf(payGeneral.getOID()));
	}
	return ctrlist.draw();
}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidPayGeneral = FRMQueryString.requestLong(request, "pay_general_oid");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = " COMPANY ";

CtrlPayGeneral ctrlPayGeneral = new CtrlPayGeneral(request);
ControlLine ctrLine = new ControlLine();
Vector listPayGeneral = new Vector(1,1);

/*switch statement */
iErrCode = ctrlPayGeneral.action(iCommand , oidPayGeneral);
/* end switch*/
FrmPayGeneral frmPayGeneral = ctrlPayGeneral.getForm();

/*count list All Position*/
int vectSize = PstPayGeneral.getCount(whereClause);

PayGeneral payGeneral = ctrlPayGeneral.getPayGeneral();
msgString =  ctrlPayGeneral.getMessage();
 
/*switch list Division*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	//start = PstDivision.findLimitStart(division.getOID(),recordToGet, whereClause);
	oidPayGeneral = payGeneral.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlPayGeneral.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listPayGeneral = PstPayGeneral.list(start,recordToGet, whereClause , orderClause);
/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listPayGeneral.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listPayGeneral = PstPayGeneral.list(start,recordToGet, whereClause , orderClause);
}


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - General List</title>
<script language="JavaScript">
function cmdEdit(oid){
		document.frm_pay_general.pay_general_oid.value=oid;
		document.frm_pay_general.command.value="<%=Command.EDIT%>";
		document.frm_pay_general.prev_command.value="<%=Command.EDIT%>";
		document.frm_pay_general.action="general.jsp";
		document.frm_pay_general.submit();
	}
function cmdBack(){
	document.frm_pay_general.command.value="<%=Command.BACK%>";
	document.frm_pay_general.action="general.jsp";
	document.frm_pay_general.submit();
	}

function cmdListFirst(){
	document.frm_pay_general.command.value="<%=Command.FIRST%>";
	document.frm_pay_general.prev_command.value="<%=Command.FIRST%>";
	document.frm_pay_general.action="general_list.jsp";
	document.frm_pay_general.submit();
}

function cmdListPrev(){
	document.frm_pay_general.command.value="<%=Command.PREV%>";
	document.frm_pay_general.prev_command.value="<%=Command.PREV%>";
	document.frm_pay_general.action="general_list.jsp";
	document.frm_pay_general.submit();
	}

function cmdListNext(){
	document.frm_pay_general.command.value="<%=Command.NEXT%>";
	document.frm_pay_general.prev_command.value="<%=Command.NEXT%>";
	document.frm_pay_general.action="general_list.jsp";
	document.frm_pay_general.submit();
}

function cmdListLast(){
	document.frm_pay_general.command.value="<%=Command.LAST%>";
	document.frm_pay_general.prev_command.value="<%=Command.LAST%>";
	document.frm_pay_general.action="general_list.jsp";
	document.frm_pay_general.submit();
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

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
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
                  General &gt; General List<!-- #EndEditable --> 
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
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top"> 
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frm_pay_general" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">									  
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="pay_general_oid" value="<%=oidPayGeneral%>">
                                      <table border="0" width="100%">
                                        <tr> 
                                          <td height="8" width="100%" class="listtitle"><span class="listtitle">Company List</span> 
                                          </td>
                                        </tr>
                                        <%if((listPayGeneral!=null)&&(listPayGeneral.size()>0)){%>
                                        <tr> 
                                          <td height="8" width="100%"><%=drawList(listPayGeneral, start)%></td>
                                        </tr>
                                        <%}else{%>
                                        <tr> 
                                          <td height="8" width="100%" class="comment"><span class="comment"><br>
                                            &nbsp;No General Setup  available</span> 
                                          </td>
                                        </tr>
                                        <%}%>
                                      </table>                                                                                                                                                                                              
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td> 
                                            <% ctrLine.setLocationImg(approot+"/images");
												ctrLine.initDefault();						
												%>
                                            <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%> 
                                          </td>
                                        </tr>
                                        <tr>
                                          <td nowrap align="left" class="command">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td nowrap align="left" class="command"> 
                                            <table border="0" cellspacing="0" cellpadding="0" align="left">
                                              <tr> 
                                                
												<% 
												if(privAdd)
												{
												%>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="general.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap><b><a href="general.jsp" class="command">Add 
                                                  New General Setup</a></b></td>
												<%
												}
												%>
												
												
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
<script language="JavaScript">
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
