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
public String drawList(Vector objectClass, int st, boolean privUpdate){
	
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
        ctrlist.setCellSpacing("0");
	ctrlist.addHeader("Nr.","");
	ctrlist.addHeader("Name","");
	ctrlist.addHeader("Address","");
	ctrlist.addHeader("City","");
	ctrlist.addHeader("Pos Code","");
	ctrlist.addHeader("Bussiness Type","");
	ctrlist.addHeader("Tel","");
	ctrlist.addHeader("Fax","");
	ctrlist.addHeader("Leader Name","");
	ctrlist.addHeader("Position","");
	ctrlist.addHeader("Work Days","");
	Vector lstData = ctrlist.getData();
	ctrlist.reset();
	//System.out.println("masuk"+objectClass.size());
	for (int j = 0; j < objectClass.size(); j++) {
		
	}
	for (int i = 0; i < objectClass.size(); i++) {
		//System.out.println("masukvector");
		PayGeneral payGeneral = (PayGeneral)objectClass.get(i);
		String urlEdit = "";
		if (privUpdate == true){
                    urlEdit = "<a href=\"javascript:cmdEdit('"+payGeneral.getOID()+"')\">"+payGeneral.getCompanyName()+"</a>";
                } else {
                    urlEdit = payGeneral.getCompanyName();
                }
		
		Vector rowx = new Vector();
		rowx.add(String.valueOf(st + 1 + i));
		rowx.add(urlEdit);
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
<style type="text/css">
    .tblStyle {border-collapse: collapse;}
    .tblStyle td {padding: 7px 9px; border: 1px solid #CCC; font-size: 12px;}
    .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
    .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
    body {color:#373737;}
    #menu_utama {padding: 9px 14px; border-bottom: 1px solid #CCC; font-size: 14px; background-color: #F3F3F3;}
    #menu_title {color:#0099FF; font-size: 14px;}
    #menu_teks {color:#CCC;}

    #btn {
      background: #3498db;
      border: 1px solid #0066CC;
      border-radius: 3px;
      font-family: Arial;
      color: #ffffff;
      font-size: 12px;
      padding: 3px 9px 3px 9px;
    }

    #btn:hover {
      background: #3cb0fd;
      border: 1px solid #3498db;
    }
    .title_part {
        font-size: 12px;
        color:#0099FF; 
        background-color: #F7F7F7; 
        border-left: 1px solid #0099FF; 
        padding: 7px 9px;
    }

    body {background-color: #EEE;}
    .header {

    }
    .content-main {
        padding: 21px 32px;
        margin: 0px 23px 59px 23px;
    }
    .content-info {
        padding: 21px;
        border-bottom: 1px solid #E5E5E5;
    }
    .content-title {
        padding: 21px;
        border-bottom: 1px solid #E5E5E5;
        margin-bottom: 5px;
    }
    #title-large {
        color: #575757;
        font-size: 16px;
        font-weight: bold;
    }
    #title-small {
        color:#797979;
        font-size: 11px;
    }
    .content {
        padding: 21px;
    }
    .box {
        margin: 17px 7px;
        background-color: #FFF;
        color:#575757;
    }
    #box_title {
        padding:21px; 
        font-size: 14px; 
        color: #007fba;
        border-top: 1px solid #28A7D1;
        border-bottom: 1px solid #EEE;
    }
    #box_content {
        padding:21px; 
        font-size: 12px;
        color: #575757;
    }
    .box-info {
        padding:21px 43px; 
        background-color: #F7F7F7;
        border-bottom: 1px solid #CCC;
        -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
         -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
              box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
    }
    #title-info-name {
        padding: 11px 15px;
        font-size: 35px;
        color: #535353;
    }
    #title-info-desc {
        padding: 7px 15px;
        font-size: 21px;
        color: #676767;
    }

    #photo {
        padding: 7px; 
        background-color: #FFF; 
        border: 1px solid #DDD;
    }

    .btn {
        background-color: #00a1ec;
        border-radius: 3px;
        font-family: Arial;
        color: #EEE;
        font-size: 12px;
        padding: 7px 11px 7px 11px;
        text-decoration: none;
    }

    .btn:hover {
        color: #FFF;
        background-color: #007fba;
        text-decoration: none;
    }

    .btn-small {
        font-family: sans-serif;
        text-decoration: none;
        padding: 5px 7px; 
        background-color: #676767; color: #F5F5F5; 
        font-size: 11px; cursor: pointer;
        border-radius: 3px;
    }
    .btn-small:hover { background-color: #474747; color: #FFF;}

    .btn-small-1 {
        font-family: sans-serif;
        text-decoration: none;
        padding: 5px 7px; 
        background-color: #EEE; color: #575757; 
        font-size: 11px; cursor: pointer;
        border-radius: 3px;
    }
    .btn-small-1:hover { background-color: #DDD; color: #474747;}

    .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
    .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
    #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}

    .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
    .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }

    #caption {
        font-size: 12px;
        color: #474747;
        font-weight: bold;
        padding: 2px 0px 3px 0px;
    }
    #divinput {
        margin-bottom: 5px;
        padding-bottom: 5px;
    }

    #div_item_sch {
        background-color: #EEE;
        color: #575757;
        padding: 5px 7px;
    }

    #record_count{
        font-size: 12px;
        font-weight: bold;
        padding-bottom: 9px;
    }
    #box-form {
        background-color: #EEE; 
        border-radius: 5px;
    }
    .form-style {
        font-size: 12px;
        color: #575757;
        border: 1px solid #DDD;
        border-radius: 5px;
    }
    .form-title {
        padding: 11px 21px;
        text-align: left;
        border-bottom: 1px solid #DDD;
        background-color: #FFF;
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
        font-weight: bold;
    }
    .form-content {
        text-align: left;
        padding: 21px;
        background-color: #DDD;
    }
    .form-footer {
        border-top: 1px solid #DDD;
        padding: 11px 21px;
        background-color: #FFF;
        border-bottom-left-radius: 5px;
        border-bottom-right-radius: 5px;
    }
    #confirm {
        padding: 13px 17px;
        background-color: #FF6666;
        color: #FFF;
        border-radius: 3px;
        font-size: 12px;
        font-weight: bold;
        visibility: hidden;
    }
    #btn-confirm {
        padding: 4px 9px; border-radius: 3px;
        background-color: #CF5353; color: #FFF; 
        font-size: 11px; cursor: pointer;
    }
    .footer-page {
        font-size: 12px;
    }
                
</style>
</head> 

    <body>
        <div class="header">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
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
            </table>
        </div>
            <div id="menu_utama">
            <span id="menu_title">Payroll <strong style="color:#333;"> / </strong>General List</span>
        </div>
        <div class="content-main">
            <form name="frm_pay_general" method="post" action="">
                <input type="hidden" name="command" value="<%=iCommand%>">
                <input type="hidden" name="prev_command" value="<%=prevCommand%>">									  
                <input type="hidden" name="start" value="<%=start%>">
                <input type="hidden" name="pay_general_oid" value="<%=oidPayGeneral%>">

                  <div class="title_part">Company List</div> 
                  <div>&nbsp;</div>
                  <%if((listPayGeneral!=null)&&(listPayGeneral.size()>0)){%>
                  <div><%=drawList(listPayGeneral, start, privUpdate)%></div>
                  <%}else{%>
                  <div>
                      No General Setup  available
                  </div>
                  <%}%>
 
                    <% 
                    ctrLine.setLocationImg(approot+"/images");
                    ctrLine.initDefault();						
                    %>
                    <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%> 
                  <div>&nbsp;</div>
                    <% 
                    if(privAdd)
                    {
                    %>
                    <a href="general.jsp" style="color:#FFF" class="btn">Add New General Setup</a>
                    <%
                    }
                    %>

              </form>
        </div>
        <div class="footer-page">
            <table>
                <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
                <tr>
                    <td valign="bottom"><%@include file="../../footer.jsp" %></td>
                </tr>
                <%} else {%>
                <tr> 
                    <td colspan="2" height="20" ><%@ include file = "../../main/footer.jsp" %></td>
                </tr>
                <%}%>
            </table>
        </div>      
    </body>
</html>
