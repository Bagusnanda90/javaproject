 
<%@ page language="java" %>
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
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LEAVE_DP_RECORD_REPORT, AppObjInfo.OBJ_LEAVE_DETAIL_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
    //boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%

long departmentOID = FRMQueryString.requestLong(request, "department_id");
Department department = new Department();
if(departmentOID!=0){
	try{
		department = PstDepartment.fetchExc(departmentOID);
	}catch(Exception e){
	}
}


//--

int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");

/*variable declaration*/
int recordToGet = 17;
String msgString = "";
int iErrCode = FRMMessage.NONE;
String whereClause = "";
String orderClause = "";
int prevCommand = 0;

//get selected and edited


CtrlLeaveStock ctrlLeaveStock = new CtrlLeaveStock(request);
ControlLine ctrLine = new ControlLine();
Vector listLeaveStock = new Vector(1,1);

/*switch statement */

/* end switch*/
FrmLeaveStock frmLeaveStock = ctrlLeaveStock.getForm();

/*count list All LeaveStock*/

int vectSize = SessLeaveStock.getCount(departmentOID, "");

/*switch list LeaveStock*/
if((iCommand == Command.FIRST || iCommand == Command.PREV )||
  (iCommand == Command.NEXT || iCommand == Command.LAST)){
		start = ctrlLeaveStock.actionList(iCommand, start, vectSize, recordToGet);
 } 
/* end switch list*/

vectSize = SessLeaveStock.getCount(departmentOID, "");

LeaveStock leaveStock = ctrlLeaveStock.getLeaveStock();
msgString =  ctrlLeaveStock.getMessage();

/* get record to display */
listLeaveStock = SessLeaveStock.getLeaveStock(departmentOID, "", start, recordToGet);//list(start,recordToGet, whereClause , orderClause);

/*handle condition if size of record to display = 0 and start > 0 	after delete*/
if (listLeaveStock.size() < 1 && start > 0)
{
	 if (vectSize - recordToGet > recordToGet)
			start = start - recordToGet;   //go to Command.PREV
	 else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 //prevCommand = Command.FIRST; //go to Command.FIRST
	 }
	 listLeaveStock = SessLeaveStock.getLeaveStock(departmentOID, "", start, recordToGet);
}


%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - </title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>

function cmdPrint(){
	window.open("<%=printroot%>.report.leavedp.DetailPdf?department_id=<%=departmentOID%>","detail_leavedp",'scrollbars=yes,status=yes,width=850,height=650,resizable=yes'); 
}

function cmdListFirst(){
	document.frmleavestock.command.value="<%=Command.FIRST%>";
	document.frmleavestock.prev_command.value="<%=Command.FIRST%>";
	document.frmleavestock.action="detail_list.jsp";
	document.frmleavestock.submit();
}

function cmdListPrev(){
	document.frmleavestock.command.value="<%=Command.PREV%>";
	document.frmleavestock.prev_command.value="<%=Command.PREV%>";
	document.frmleavestock.action="detail_list.jsp";
	document.frmleavestock.submit();
}

function cmdListNext(){
	document.frmleavestock.command.value="<%=Command.NEXT%>";
	document.frmleavestock.prev_command.value="<%=Command.NEXT%>";
	document.frmleavestock.action="detail_list.jsp";
	document.frmleavestock.submit();
}

function cmdListLast(){
	document.frmleavestock.command.value="<%=Command.LAST%>";
	document.frmleavestock.prev_command.value="<%=Command.LAST%>";
	document.frmleavestock.action="detail_list.jsp";
	document.frmleavestock.submit();
}

function cmdBack(){
	document.frmleavestock.command.value="<%=Command.BACK%>";
	document.frmleavestock.action="detail.jsp";
	document.frmleavestock.submit();
}


    function hideObjectForEmployee(){
        //document.frmsrcemployee.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = 'hidden';
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
        //document.all.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = "";
    }
	
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
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNew.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
     <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %>
      <!-- #EndEditable --> </td>
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Report 
                  &gt; Leave &amp; DP &gt; Detail &gt; List<!-- #EndEditable --> 
                  </strong></font> </td>
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmleavestock" method="post" action="">
                                      <input type="hidden" name="command">
                                      <input type="hidden" name="prev_command">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="department_id" value="<%=departmentOID%>">
                                      <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                        <tr> 
                                          <td> 
                                            <div align="center"><b><%=department.getDepartment()%></b></div>
                                          </td>
                                        </tr>
                                      </table>
                                      <table width="100%" border="0" cellspacing="1" cellpadding="1" class="listgen">
                                        <tr class="listgentitle"> 
                                          <td rowspan="2" class="listgentitle"> 
                                            <div align="center"><b>No</b></div>
                                          </td>
                                          <td rowspan="2" class="listgentitle">Employee</td>
                                          <td rowspan="2" class="listgentitle"> 
                                            <div align="center"><b>Payroll No.</b></div>
                                          </td>
                                          <td colspan="4" class="listgentitle"> 
                                            <div align="center"><b>DP</b></div>
                                          </td>
                                          <td colspan="4" class="listgentitle"> 
                                            <div align="center"><b>Year Leave</b></div>
                                          </td>
                                          <td colspan="4" class="listgentitle"> 
                                            <div align="center"><b>Long Leave</b></div>
                                          </td>
                                        </tr>
                                        <tr class="listgentitle"> 
                                          <td> 
                                            <div align="center"><b>LM</b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b>Add</b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b>Taken</b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b>Blnc</b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b>LM</b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b>Add</b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b>Taken</b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b>Blnc</b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b>LM</b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b>Add</b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b>Taken</b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b>Blnc</b></div>
                                          </td>
                                        </tr>
                                        <%
									  int dpAmount = 0;
									int alAmount = 0;
									int llAmount = 0;
									int addDp = 0;
									int addAl = 0;
									int addLl = 0;
									int takeAl = 0;
									int takeLl	= 0;
									int takeDp = 0; 
									long depOID = 0;
									  
									  
									  int totDpAmount = 0;
									int totAlAmount = 0;
									int totLlAmount = 0;
									
									int totAddDp = 0;
									int totAddAl = 0;
									int totAddLl = 0;
									
									int totTakeAl = 0;
									int totTakeLl	= 0;
									int totTakeDp = 0; 
									
									int blcDp = 0;
									int blcAl = 0;
									int blcLl = 0;
									  
									  if(listLeaveStock!=null && listLeaveStock.size()>0){
									  
											for(int i=0; i<listLeaveStock.size(); i++){
												Vector temp = (Vector)listLeaveStock.get(i);
												Employee emp = (Employee)temp.get(0);															
												LeaveStock lStock = (LeaveStock)temp.get(1);
												
												
												
												dpAmount = lStock.getDpAmount();//SessLeaveStock.getTotalDpByDepartment(depOID);												
												alAmount = lStock.getAlAmount();//SessLeaveStock.getTotalAlByDepartment(depOID);
												llAmount = lStock.getLlAmount();//SessLeaveStock.getTotalLlByDepartment(depOID);
												
												addDp = lStock.getAddDp();//SessLeaveStock.getTotalAddDpByDepartment(depOID);
												addAl = lStock.getAddAl();//SessLeaveStock.getTotalAddAlByDepartment(depOID);
												addLl = lStock.getAddLl();//SessLeaveStock.getTotalAddLlByDepartment(depOID);
												
												takeAl = SessLeave.getTakenAnualLeave(emp.getOID());
												takeLl	= SessLeave.getTakenLongLeave(emp.getOID());
												takeDp = SessDayOfPayment.getEmpDayOff(emp.getOID());
												
												//---------
												
												totDpAmount = totDpAmount + (dpAmount - addDp);
												totAlAmount = totAlAmount + (alAmount - addAl);
												totLlAmount = totLlAmount + (llAmount - addLl);
												
												totAddDp = totAddDp + addDp;
												totAddAl = totAddAl + addAl;
												totAddLl = totAddLl + addLl;
												
												totTakeAl = totTakeAl + takeAl;
												totTakeLl	= totTakeLl + takeLl;
												totTakeDp = totTakeDp + takeDp; 
												
												
									  %>
                                        <tr class="listgensell"> 
                                          <td> 
                                            <div align="center"><%=(start+(i+1))%></div>
                                          </td>
                                          <td><%=emp.getFullName()%></td>
                                          <td> 
                                            <div align="center"><%=emp.getEmployeeNum()%></div>
                                          </td>
                                          <td> 
                                            <div align="center"><%=dpAmount-addDp%></div>
                                          </td>
                                          <td> 
                                            <div align="center"><%=addDp%></div>
                                          </td>
                                          <td> 
                                            <div align="center"><%=takeDp%></div>
                                          </td>
                                          <td> 
                                            <div align="center"><%=dpAmount-takeDp%></div>
                                          </td>
                                          <td> 
                                            <div align="center"><%=alAmount-addAl%></div>
                                          </td>
                                          <td> 
                                            <div align="center"><%=addAl%></div>
                                          </td>
                                          <td> 
                                            <div align="center"><%=takeAl%></div>
                                          </td>
                                          <td> 
                                            <div align="center"><%=alAmount-takeAl%></div>
                                          </td>
                                          <td> 
                                            <div align="center"><%=llAmount-addLl%></div>
                                          </td>
                                          <td> 
                                            <div align="center"><%=addLl%></div>
                                          </td>
                                          <td> 
                                            <div align="center"><%=takeLl%></div>
                                          </td>
                                          <td> 
                                            <div align="center"><%=llAmount-takeLl%></div>
                                          </td>
                                        </tr>
                                        <%}}else{%>
                                        <tr class="listgensell"> 
                                          <td colspan="15"><font color="#FFFFFF"><i>no 
                                            department available ...</i></font></td>
                                        </tr>
                                        <%}%>
                                        <tr class="listgensell"> 
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                        </tr>
                                        <tr class="listgensell"> 
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                        </tr>
                                        <tr class="listgensell"> 
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                        </tr>
                                        <tr class="listgensell"> 
                                          <td colspan="3"> 
                                            <div align="center"><b>TOTAL&nbsp;&nbsp;</b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b><%=totDpAmount%></b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b><%=totAddDp%></b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b><%=totTakeDp%></b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b><%=totDpAmount + totAddDp - totTakeDp%></b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b><%=totAlAmount%></b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b><%=totAddAl%></b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b><%=totTakeAl%></b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b><%=totAlAmount + totAddAl - totTakeAl%></b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b><%=totLlAmount%></b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b><%=totAddLl%></b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b><%=totTakeLl%></b></div>
                                          </td>
                                          <td> 
                                            <div align="center"><b><%=totLlAmount + totAddLl - totTakeLl%></b></div>
                                          </td>
                                        </tr>
                                        <input type="hidden" name="<%=FrmPrevLeave.fieldNames[FrmPrevLeave.FRM_FIELD_DP_LM]%>" value="<%=totDpAmount%>">
                                        <input type="hidden" name="<%=FrmPrevLeave.fieldNames[FrmPrevLeave.FRM_FIELD_DP_ADD]%>" value="<%=totAddDp%>">
                                        <input type="hidden" name="<%=FrmPrevLeave.fieldNames[FrmPrevLeave.FRM_FIELD_DP_TAKEN]%>" value="<%=totTakeDp%>">
                                        <input type="hidden" name="<%=FrmPrevLeave.fieldNames[FrmPrevLeave.FRM_FIELD_DP_BAL]%>" value="<%=totDpAmount + totAddDp - totTakeDp%>">
                                        <input type="hidden" name="<%=FrmPrevLeave.fieldNames[FrmPrevLeave.FRM_FIELD_AL_LM]%>" value="<%=totAlAmount%>">
                                        <input type="hidden" name="<%=FrmPrevLeave.fieldNames[FrmPrevLeave.FRM_FIELD_AL_ADD]%>" value="<%=totAddAl%>">
                                        <input type="hidden" name="<%=FrmPrevLeave.fieldNames[FrmPrevLeave.FRM_FIELD_AL_TAKEN]%>" value="<%=totTakeAl%>">
                                        <input type="hidden" name="<%=FrmPrevLeave.fieldNames[FrmPrevLeave.FRM_FIELD_AL_BAL]%>" value="<%=totAlAmount + totAddAl - totTakeAl%>">
                                        <input type="hidden" name="<%=FrmPrevLeave.fieldNames[FrmPrevLeave.FRM_FIELD_LL_LM]%>" value="<%=totLlAmount%>">
                                        <input type="hidden" name="<%=FrmPrevLeave.fieldNames[FrmPrevLeave.FRM_FIELD_LL_ADD]%>" value="<%=totAddLl%>">
                                        <input type="hidden" name="<%=FrmPrevLeave.fieldNames[FrmPrevLeave.FRM_FIELD_LL_TAKEN]%>" value="<%=totTakeLl%>">
                                        <input type="hidden" name="<%=FrmPrevLeave.fieldNames[FrmPrevLeave.FRM_FIELD_LL_BAL]%>" value="<%=totLlAmount + totAddLl - totTakeLl%>">
                                      </table>
                                      <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                        <tr> 
                                          <td><span class="command"> 
                                            <% 
								   int cmd = 0;
									   if ((iCommand == Command.FIRST || iCommand == Command.PREV )|| 
										(iCommand == Command.NEXT || iCommand == Command.LAST))
											cmd =iCommand; 
								   else{
									  if(iCommand == Command.NONE || prevCommand == Command.NONE)
										cmd = Command.FIRST;
									  else 
									  	cmd =prevCommand; 
								   } 
							    %>
                                            <% ctrLine.setLocationImg(approot+"/images");
							   	ctrLine.initDefault();
								 %>
                                            <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> </span></td>
                                        </tr>
                                        <tr> 
                                          <td> 
                                            <table width="24%" border="0" cellspacing="0" cellpadding="0">
                                              <tr>
											   <% if(privPrint){%> 
                                                <td width="3%" class="command" nowrap><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnNew.jpg',1)"><img name="Image1011" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                <td width="3%" class="command" nowrap><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                <td width="3%" class="command" nowrap><a href="javascript:cmdPrint()">Print 
                                                  Report </a></td>
											   <%}%>
                                                <td width="3%" class="command" nowrap><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                <td width="3%" class="command" nowrap><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnBackOn.jpg',1)" id="aSearch"><img name="Image101" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                <td width="3%" class="command" nowrap><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                <td width="45%" class="command" nowrap><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnBackOn.jpg',1)" id="aSearch">Back 
                                                  To Search Page</a></td>
                                              </tr>
                                            </table>
                                            
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                        </tr>
                                      </table>
                                    </form>
                                    <!-- #EndEditable --> </td>
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
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>

<!-- #BeginEditable "script" --> {script} 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
