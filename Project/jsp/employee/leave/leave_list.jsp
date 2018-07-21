
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
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SECRETARY , AppObjInfo.G2_SECRETARY_GENERAL   , AppObjInfo.OBJ_SG_LEAVE_DP   	); %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_LEAVE_MANAGEMENT   	); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//	boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//    boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//    boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//    boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
	privPrint = true;
	//out.println("privStart : "+privStart+", privAdd : "+privAdd+", privUpdate : "+privUpdate+", privDelete : "+privDelete);
	
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
		ctrlist.dataFormat("Employee Name","7%","2","0", "center", "");
		ctrlist.dataFormat("Submit Date","5%","2","0", "center", "");
                ctrlist.dataFormat("Leave","5%","0","2", "center", "");
		ctrlist.dataFormat("From","5%","0","0", "center", "");
		ctrlist.dataFormat("To","5%","0","0", "center", "");
		ctrlist.dataFormat("Dur.","2%","2","0", "center", "center");
		//ctrlist.addHeader("Reason","4%");
		ctrlist.dataFormat("LL","2%","2","0", "center", "center");
		ctrlist.dataFormat("AL","2%","2","0", "center", "center");
		ctrlist.dataFormat("W/O","2%","2","0", "center", "center");
		ctrlist.dataFormat("ML","2%","2","0", "center", "center");
		ctrlist.dataFormat("DO","2%","2","0", "center", "center");
		ctrlist.dataFormat("PH","2%","2","0", "center", "center");
		ctrlist.dataFormat("X","2%","2","0", "center", "center");
		ctrlist.dataFormat("S","2%","2","0", "center", "center");
                ctrlist.dataFormat("Period AL","16%","0","2", "center", "center");
		ctrlist.dataFormat("From","2%","0","0", "center", "center");
		ctrlist.dataFormat("To","2%","0","0", "center", "center");
                ctrlist.dataFormat("AL","3%","0","3", "center", "center");
		ctrlist.dataFormat("En","1%","0","0", "center", "center");
		ctrlist.dataFormat("Tk","1%","0","0", "center", "center");
		ctrlist.dataFormat("Bl","1%","0","0", "center", "center");
		ctrlist.dataFormat("Period LL","16%","0","2", "center", "center");
		ctrlist.dataFormat("From","2%","0","0", "center", "center");
		ctrlist.dataFormat("To","2%","0","0", "center", "center");
                ctrlist.dataFormat("LL","3%","0","3", "center", "center");
		ctrlist.dataFormat("En","1%","0","0", "center", "center");
		ctrlist.dataFormat("Tk","1%","0","0", "center", "center");
		ctrlist.dataFormat("Bl","1%","0","0", "center", "center");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();

		for (int i = 0; i < objectClass.size(); i++) {
                        Vector temp = (Vector) objectClass.get(i);
                        Leave leave = (Leave) temp.get(0);
			Employee employee = (Employee) temp.get(1);
			
			Vector rowx = new Vector();

                        rowx.add(employee.getFullName());

			//rowx.add(String.valueOf(leave.getEmployeeId()));

			String str_dt_SubmitDate = ""; 
			try{
				Date dt_SubmitDate = leave.getSubmitDate();
				if(dt_SubmitDate==null){
					dt_SubmitDate = new Date();
				}

				str_dt_SubmitDate = Formater.formatDate(dt_SubmitDate, "dd-MMM-yyyy");
			}catch(Exception e){ str_dt_SubmitDate = ""; }

			rowx.add(str_dt_SubmitDate);

			String str_dt_LeaveFrom = ""; 
			try{
				Date dt_LeaveFrom = leave.getLeaveFrom();
				if(dt_LeaveFrom==null){
					dt_LeaveFrom = new Date();
				}

				str_dt_LeaveFrom = Formater.formatDate(dt_LeaveFrom, "dd-MMM-yyyy");
			}catch(Exception e){ str_dt_LeaveFrom = ""; }

			rowx.add(str_dt_LeaveFrom);

			String str_dt_LeaveTo = ""; 
			try{
				Date dt_LeaveTo = leave.getLeaveTo();
				if(dt_LeaveTo==null){
					dt_LeaveTo = new Date();
				}

				str_dt_LeaveTo = Formater.formatDate(dt_LeaveTo, "dd-MMM-yyyy");
			}catch(Exception e){ str_dt_LeaveTo = ""; }

			rowx.add(str_dt_LeaveTo);
			rowx.add(String.valueOf(leave.getDuration()));
			//rowx.add(leave.getReason());
			rowx.add(String.valueOf(leave.getLongLeave()));
			rowx.add(String.valueOf(leave.getAnnualLeave()));
			rowx.add(String.valueOf(leave.getLeaveWoPay()));
			rowx.add(String.valueOf(leave.getMaternityLeave()));
			rowx.add(String.valueOf(leave.getDayOff()));
			rowx.add(String.valueOf(leave.getPublicHoliday()));
			rowx.add(String.valueOf(leave.getExtraDayOff()));
			rowx.add(String.valueOf(leave.getSickLeave()));
			rowx.add(String.valueOf(leave.getPeriodAlFrom()));
			rowx.add(String.valueOf(leave.getPeriodAlTo()));
			rowx.add(String.valueOf(leave.getAlEntitlement()));
			rowx.add(String.valueOf(leave.getAlTaken()));
			rowx.add(String.valueOf(leave.getAlBalance()));
			rowx.add(String.valueOf(leave.getPeriodLlFrom()));
			rowx.add(String.valueOf(leave.getPeriodLlTo()));
			rowx.add(String.valueOf(leave.getLlEntitlement()));
			rowx.add(String.valueOf(leave.getLlTaken()));
			rowx.add(String.valueOf(leave.getLlBalance()));
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(leave.getOID()));
		}

		return ctrlist.drawMeList();
	}

%>
<%
    ControlLine ctrLine = new ControlLine();
    CtrlLeave ctrlLeave = new CtrlLeave(request);
    long oidLeave = FRMQueryString.requestLong(request, "hidden_leave_id");

    int iErrCode = FRMMessage.ERR_NONE;
    String msgStr = "";
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int recordToGet = 10;
    int vectSize = 0;
    String whereClause = "";

    //out.println("iCommand : "+iCommand);
    //out.println("<br>start : "+start);
    //out.println("<br>recordToGet : "+recordToGet);

    SrcLeave srcLeave = new SrcLeave();
    FrmSrcLeave frmSrcLeave = new FrmSrcLeave(request, srcLeave);
    frmSrcLeave.requestEntityObject(srcLeave);
    if((iCommand==Command.NEXT)||(iCommand==Command.FIRST)||(iCommand==Command.PREV)||(iCommand==Command.LAST)){
        try{ 
            srcLeave = (SrcLeave)session.getValue(SessLeave.SESS_SRC_LEAVE); 
            if (srcLeave == null) {
                srcLeave = new SrcLeave();
            }
        }catch(Exception e){ 
            srcLeave = new SrcLeave();
        }
    }

    SessLeave sessLeave = new SessLeave();
    session.putValue(SessLeave.SESS_SRC_LEAVE, srcLeave);
    vectSize = sessLeave.getCountSearch(srcLeave);
    //out.println("vectSize : "+vectSize);
    ctrlLeave.action(iCommand , oidLeave);
    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST))
            start = ctrlLeave.actionList(iCommand, start, vectSize, recordToGet);
    Vector records = sessLeave.searchLeave(srcLeave, start, recordToGet);
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Leave Management</title>
<script language="JavaScript">

	function cmdAdd(){
		document.frm_leave.command.value="<%=Command.ADD%>";
		document.frm_leave.action="leave_edit.jsp";
		document.frm_leave.submit();
	}

	function cmdEdit(oid){
		document.frm_leave.command.value="<%=Command.EDIT%>";
                document.frm_leave.hidden_leave_id.value = oid;
		document.frm_leave.action="leave_edit.jsp";
		document.frm_leave.submit();
	}

	function cmdListFirst(){
		document.frm_leave.command.value="<%=Command.FIRST%>";
		document.frm_leave.action="leave_list.jsp";
		document.frm_leave.submit();
	}

	function cmdListPrev(){
		document.frm_leave.command.value="<%=Command.PREV%>";
		document.frm_leave.action="leave_list.jsp";
		document.frm_leave.submit();
	}

	function cmdListNext(){
		document.frm_leave.command.value="<%=Command.NEXT%>";
		document.frm_leave.action="leave_list.jsp";
		document.frm_leave.submit();
	}

	function cmdListLast(){
		document.frm_leave.command.value="<%=Command.LAST%>";
		document.frm_leave.action="leave_list.jsp";
		document.frm_leave.submit();
	}

	function cmdBack(){
		document.frm_leave.command.value="<%=Command.BACK%>";
		document.frm_leave.action="srcleave.jsp";
		document.frm_leave.submit();
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
			  <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Attendance &gt; Leave Management<!-- #EndEditable --> 
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
							  <%if(privStart){%> 
                                    <form name="frm_leave" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_leave_id" value="<%=oidLeave%>">
                                      <table border="0" width="100%">
                                        <tr> 
                                          <td height="8" width="100%" class="listtitle">Leave 
                                            List </td>
                                        </tr>
                                      </table>
                                      <%if((records!=null)&&(records.size()>0)){%>
                                      <%=drawList(records)%> 
                                      <%}
					else{
					%>
                                      <span class="comment"><br>
                                      &nbsp;Records is empty ...</span> 
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
                                          <td width="46" nowrap align="left" class="command">
										  	<%-- &nbsp; <a href="javascript:cmdAdd()">Add New</a> | <a href="javascript:cmdBack()">Back to search</a> --%>
                                            <table width="70%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
												<td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="150" nowrap> <a href="javascript:cmdBack()" class="command">Back 
                                                  To Search Leave</a></td>
												  <% if(privAdd){%>
												<td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New"></a></td>
												<td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="114" nowrap><b><a href="javascript:cmdAdd()" class="command">Add 
                                                  New Leave</a></b></td>
												  <% }
												  if(privPrint){%>
                                                <%--
                                                <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
												<td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="275" nowrap><b><a href="javascript:cmdPrint()" class="command">Print 
                                                  Leave</a></b></td>
                                                  --%>
                                              	<%}%>
											  </tr>
                                            </table>
										  </td>
                                        </tr>
                                      </table>
                                    </form>
									<%}else{%>
									<div align="center">You do not have sufficient privilege to access this page.</div>
									<%}%>
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
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
