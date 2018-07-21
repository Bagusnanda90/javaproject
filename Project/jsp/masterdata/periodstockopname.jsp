
<%@page import="com.dimata.harisma.entity.periodestokopname.PeriodeStokOpname"%>
<%@page import="com.dimata.harisma.entity.periodestokopname.PstPeriodeStokOpname"%>
<%@page import="com.dimata.harisma.form.periodestokopname.FrmPeriodeStokOpname"%>
<%@page import="com.dimata.harisma.form.periodestokopname.CtrlPeriodeStokOpname"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<% 
/* 
 * Page Name  		:  period.jsp
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
<!--package hr -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_LOCATION_OUTLET, AppObjInfo.OBJ_PERIODE_SO); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));

%>
<!-- Jsp Block -->
<%!

	public String drawList(Vector objectClass ,  long periodStockOpname)

	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("90%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No","10%");
		ctrlist.addHeader("Name","50%");
                ctrlist.addHeader("Date From","20%");
                ctrlist.addHeader("Date To","20%");
		

		ctrlist.setLinkRow(1);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
                int no=1; 
                Date startDate = new Date();
                Date endDate = new Date();
                String strStartDate ="";
                String strEndDate="";
		for (int i = 0; i < objectClass.size(); i++) {
			PeriodeStokOpname periodStockOpnamee = (PeriodeStokOpname)objectClass.get(i); 
			 Vector rowx = new Vector(); 
			 if(periodStockOpname == periodStockOpnamee.getOID())
				 index = i;
                         rowx.add(""+no);
                         no++;
			rowx.add(periodStockOpnamee.getNamePeriod());
                        if(periodStockOpnamee.getStartDate()!=null && periodStockOpnamee.getEndDate()!=null){
                            startDate=periodStockOpnamee.getStartDate();
                            endDate=periodStockOpnamee.getEndDate();
                            strStartDate=Formater.formatDate(startDate, "dd MMMM yyyy");
                            strEndDate=Formater.formatDate(endDate, "dd MMMM yyyy");
                        }else{
                            strStartDate=Formater.formatDate(startDate, "dd MMMM yyyy");
                            strEndDate=Formater.formatDate(endDate, "dd MMMM yyyy");
                        }
                        rowx.add(strStartDate);
                        rowx.add(strEndDate);
			

			lstData.add(rowx);
			lstLinkData.add(String.valueOf(periodStockOpnamee.getOID())); 
		}

		//return ctrlist.drawList(index);

		return ctrlist.draw(index);
	}

%>
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidPeriodStockOpname = FRMQueryString.requestLong(request, "hidden_oidperiodStockOpname");
int enableRepalceSchedule = FRMQueryString.requestInt(request, "replace_schedule");

/*variable declaration*/
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;

CtrlPeriodeStokOpname ctrlPeriodeStokOpname = new CtrlPeriodeStokOpname(request); 
ControlLine ctrLine = new ControlLine();
Vector listDtStockPeriod = new Vector(1,1);

/*switch statement */
iErrCode = ctrlPeriodeStokOpname.action(iCommand , oidPeriodStockOpname);
/* end switch*/
FrmPeriodeStokOpname frmPeriodeStokOpname = ctrlPeriodeStokOpname.getForm(); 

/*count list All Period*/
int vectSize = PstPeriodeStokOpname.getCount("");

PeriodeStokOpname periodeStokOpname = ctrlPeriodeStokOpname.getPeriodStokOpname(); 
msgString =  ctrlPeriodeStokOpname.getMessage();

/*switch list Period*/
if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)){
	start = PstPeriodeStokOpname.findLimitStart(periodeStokOpname.getOID(),recordToGet, "",""); 
	oidPeriodStockOpname = periodeStokOpname.getOID();
}

if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlPeriodeStokOpname.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

/* get record to display */
listDtStockPeriod = PstPeriodeStokOpname.list(start,recordToGet, "" , "");  

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listDtStockPeriod.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listDtStockPeriod = PstPeriod.list(start,recordToGet, "" , "");
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Master Data Schedule Periode</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmperiodstockopname.hidden_oidperiodStockOpname.value="0";
	document.frmperiodstockopname.command.value="<%=String.valueOf(Command.ADD)%>";
	document.frmperiodstockopname.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmperiodstockopname.action="periodstockopname.jsp";
	document.frmperiodstockopname.submit();
}

function cmdAsk(oidPeriodStockOpname){
	document.frmperiodstockopname.hidden_oidperiodStockOpname.value=oidPeriodStockOpname;
	document.frmperiodstockopname.command.value="<%=String.valueOf(Command.ASK)%>";
	document.frmperiodstockopname.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmperiodstockopname.action="periodstockopname.jsp";
	document.frmperiodstockopname.submit();
}

function cmdConfirmDelete(oidPeriodStockOpname){
	document.frmperiodstockopname.hidden_oidperiodStockOpname.value=oidPeriodStockOpname;
	document.frmperiodstockopname.command.value="<%=String.valueOf(Command.DELETE)%>";
	document.frmperiodstockopname.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmperiodstockopname.action="periodstockopname.jsp";
	document.frmperiodstockopname.submit();
}
function cmdSave(){
	document.frmperiodstockopname.command.value="<%=String.valueOf(Command.SAVE)%>";
	document.frmperiodstockopname.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmperiodstockopname.action="periodstockopname.jsp";
	document.frmperiodstockopname.submit();
	}

function cmdEdit(oidPeriodStockOpname){
	document.frmperiodstockopname.hidden_oidperiodStockOpname.value=oidPeriodStockOpname;
	document.frmperiodstockopname.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frmperiodstockopname.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmperiodstockopname.action="periodstockopname.jsp";
	document.frmperiodstockopname.submit();
	}

function cmdCancel(oidPeriodStockOpname){
	document.frmperiodstockopname.hidden_oidperiodStockOpname.value=oidPeriodStockOpname;
	document.frmperiodstockopname.command.value="<%=String.valueOf(Command.EDIT)%>";
	document.frmperiodstockopname.prev_command.value="<%=String.valueOf(prevCommand)%>";
	document.frmperiodstockopname.action="periodstockopname.jsp";
	document.frmperiodstockopname.submit();
}




function cmdBack(){
	document.frmperiodstockopname.command.value="<%=String.valueOf(Command.BACK)%>";
	document.frmperiodstockopname.action="periodstockopname.jsp";
	document.frmperiodstockopname.submit();
	}

function cmdListFirst(){
	document.frmperiodstockopname.command.value="<%=String.valueOf(Command.FIRST)%>";
	document.frmperiodstockopname.prev_command.value="<%=String.valueOf(Command.FIRST)%>";
	document.frmperiodstockopname.action="periodstockopname.jsp";
	document.frmperiodstockopname.submit();
}

function cmdListPrev(){
	document.frmperiodstockopname.command.value="<%=String.valueOf(Command.PREV)%>";
	document.frmperiodstockopname.prev_command.value="<%=String.valueOf(Command.PREV)%>";
	document.frmperiodstockopname.action="periodstockopname.jsp";
	document.frmperiodstockopname.submit();
	}

function cmdListNext(){
	document.frmperiodstockopname.command.value="<%=String.valueOf(Command.NEXT)%>";
	document.frmperiodstockopname.prev_command.value="<%=String.valueOf(Command.NEXT)%>";
	document.frmperiodstockopname.action="periodstockopname.jsp";
	document.frmperiodstockopname.submit();
}

function cmdListLast(){
	document.frmperiodstockopname.command.value="<%=String.valueOf(Command.LAST)%>";
	document.frmperiodstockopname.prev_command.value="<%=String.valueOf(Command.LAST)%>";
	document.frmperiodstockopname.action="periodstockopname.jsp";
	document.frmperiodstockopname.submit();
}

function fnTrapKD(){
	//alert(event.keyCode);
	switch(event.keyCode) {
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

//-------------------------- for Calendar -------------------------
    function getThn(){        
        <%
         out.println(ControlDatePopup.writeDateCaller("frmperiodstockopname",FrmPeriodeStokOpname.fieldNames[FrmPeriodeStokOpname.FRM_START_DATE]));
         out.println(ControlDatePopup.writeDateCaller("frmperiodstockopname",FrmPeriodeStokOpname.fieldNames[FrmPeriodeStokOpname.FRM_END_DATE]));
         %>
    }


    function hideObjectForDate(index){
        
    }

    function showObjectForDate(){
    } 
//------------------------------------------------------------------

//-------------- script control line -------------------
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
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #EndEditable -->
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<!-- #BeginEditable "stylestab" --
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

</SCRIPT>
<!-- #EndEditable -->
</head> 

<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<!-- Untuk Calendar-->
<%=ControlDatePopup.writeTable(approot)%>
<!-- End Calendar-->
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
                  Master Data &gt; Period<!-- #EndEditable --> 
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
                                    <form name="frmperiodstockopname" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="vectSize" value="<%=String.valueOf(vectSize)%>">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                      <input type="hidden" name="prev_command" value="<%=String.valueOf(prevCommand)%>">
                                      <input type="hidden" name="hidden_oidperiodStockOpname" value="<%=String.valueOf(oidPeriodStockOpname)%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td valign="middle" colspan="3" class="listtitle">&nbsp;Period 
                                                  List </td>
                                              </tr>
                                              <%
							try{
								if (listDtStockPeriod.size()>0){
							%>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <%= drawList(listDtStockPeriod,oidPeriodStockOpname)%> 
                                                </td>
                                              </tr>
                                              <%  } 
						  }catch(Exception exc){ 
						  }%>
                                              <tr align="left" valign="top"> 
                                                <td height="8" align="left" colspan="3" class="command"> 
                                                  <span class="command"> 
                                                  <% 
								   int cmd = 0;
									   if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
										(iCommand == Command.NEXT || iCommand == Command.LAST))
											cmd =iCommand; 
								   else{
									  if(iCommand == Command.NONE || prevCommand == Command.NONE)
										cmd = Command.FIRST;
									  else{
									  		if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE))
												cmd = PstDepartment.findLimitCommand(start,recordToGet,vectSize);
											else									 
									  			cmd =prevCommand;
									  }  
								   } 
							    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
							   	ctrLine.initDefault();
								 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
                                              </tr>
											  <%//if((iCommand == Command.NONE || iCommand == Command.DELETE || iCommand == Command.BACK || iCommand ==Command.SAVE)&& (frmPeriod.errorSize()<1)){
											   if((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT)&& (frmPeriodeStokOpname.errorSize()<1)){
											   if(privAdd){%>
                                              <tr align="left" valign="top"> 
                                                <td> 
                                                  <table cellpadding="0" cellspacing="0" border="0">
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                      <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td height="22" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add 
                                                        New Periode</a> </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
											  <%}
											  }%>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp; </td>
                                        </tr>
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if((iCommand ==Command.ADD)||(iCommand==Command.SAVE)&&(frmPeriodeStokOpname.errorSize()>0)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="3" class="listtitle"><%=oidPeriodStockOpname == 0?"Add":"Edit"%> 
                                                  Period</td>
                                              </tr>
                                              <tr> 
                                                <td height="100%" width="24%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="100%">
                                                    <tr align="left" valign="top">
                                                      <td valign="top" width="19%">&nbsp;</td>
                                                      <td width="81%" class="comment"> 
                                                        *) entry required </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="19%"> 
                                                        Period </td>
                                                      <td width="81%"> 
                                                        <input type="text" name="<%=frmPeriodeStokOpname.fieldNames[frmPeriodeStokOpname.FRM_NAME_PERIOD] %>"  value="<%= periodeStokOpname.getNamePeriod() %>" class="elemenForm" size="30">
                                                        * <%=frmPeriodeStokOpname.getErrorMsg(frmPeriodeStokOpname.FRM_NAME_PERIOD )%></td> 
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="19%"> 
                                                        Start Date </td>
                                                      <td width="81%"> 
                                                        <% 
														
														 Date startdt = new Date();
                                                         startdt.setDate(21);
														 Date enddt = new Date();
                                                         enddt.setDate(20);
														 enddt.setMonth(enddt.getMonth()+1);
														  
														 if(SystemProperty.SYS_PROP_SCHEDULE_PERIOD == SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL){
														 	startdt.setDate(1);
															
															enddt = new Date();
															enddt.setMonth(enddt.getMonth()+1);	
															enddt.setDate(1);
															enddt.setDate(enddt.getDate()-1);
															
															
														 }
														
														
                                                          
                                                        %>
                                                        <%//=	ControlDate.drawDate(frmPeriod.fieldNames[FrmPeriod.FRM_FIELD_START_DATE], period.getStartDate()==null ? startdt : period.getStartDate(),"formElemen", 1,installInterval-1) %> 
                                                        <%=ControlDatePopup.writeDate(FrmPeriodeStokOpname.fieldNames[FrmPeriodeStokOpname.FRM_START_DATE],periodeStokOpname.getStartDate(),1)%> 
                                                        * <%=frmPeriodeStokOpname.getErrorMsg(frmPeriodeStokOpname.FRM_START_DATE)%></td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td valign="top" width="19%"> 
                                                        End Date</td>
                                                      <td width="81%"> 
                                                        <% 
                                                          
                                                        %>
                                                        <%//=	ControlDate.drawDate(frmPeriod.fieldNames[FrmPeriod.FRM_FIELD_END_DATE], period.getEndDate()==null ? enddt : period.getEndDate(),"formElemen", 1,installInterval-1) %> 
                                                        <%=ControlDatePopup.writeDate(FrmPeriodeStokOpname.fieldNames[FrmPeriodeStokOpname.FRM_END_DATE],periodeStokOpname.getEndDate(),2)%>
                                                        * <%=frmPeriodeStokOpname.getErrorMsg(frmPeriodeStokOpname.FRM_END_DATE )%></td>
                                                    </tr>
                                                </table>                                                </td>
                                              </tr>
                                              <tr align="left" valign="top" > 
                                                <td colspan="3" class="command"> 
                                                  <%
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidPeriodStockOpname+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidPeriodStockOpname+"')";
									String scancel = "javascript:cmdEdit('"+oidPeriodStockOpname+"')"; 
									ctrLine.setBackCaption("Back to List Periode");
									ctrLine.setSaveCaption("Save Periode");
									ctrLine.setConfirmDelCaption("Yes Delete Periode");
									ctrLine.setDeleteCaption("Delete Periode");
									ctrLine.setCommandStyle("buttonlink");

									if (privDelete){
										ctrLine.setConfirmDelCommand(sconDelCom);
										ctrLine.setDeleteCommand(scomDel);
										ctrLine.setEditCommand(scancel);
									}else{ 
										ctrLine.setConfirmDelCaption("");
										ctrLine.setDeleteCaption("");
										ctrLine.setEditCaption("");
									}

									if(privAdd == false  && privUpdate == false){
										ctrLine.setSaveCaption("");
									}

									if (privAdd == false){
										ctrLine.setAddCaption("");
									}
									
									
									if(iCommand == Command.ASK)
										ctrLine.setDeleteQuestion(msgString);
									%>
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%>                                                </td>
                                              </tr>
                                            
                                              
                                            </table>
                                            <%}%>
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
<script language="JavaScript">
	//var oBody = document.body;
	//var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
