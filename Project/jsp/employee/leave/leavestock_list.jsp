<% 
/* 
 * Page Name  		:  leavestock_list.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
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
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SECRETARY , AppObjInfo.G2_SECRETARY_GENERAL   , AppObjInfo.OBJ_SG_LEAVE_DP   	); %>
<% //int  appObjCodeSpec = AppObjInfo.composeObjCode(AppObjInfo.G1_SECRETARY , AppObjInfo.G2_SECRETARY_SPECIFIC   , AppObjInfo.OBJ_SS_LEAVE_OPNAME   	); %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_DAY_OFF_PAYMENT   	); %>
<% int  appObjCodeSpec = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_LEAVE_OPNAME   	); %>

<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//	boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//    boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//    boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//    boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
	
	boolean privViewSpec=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeSpec, AppObjInfo.COMMAND_VIEW));
	//out.println("privStart : "+privStart+", privAdd : "+privAdd+", privUpdate : "+privUpdate+", privDelete : "+privDelete);
	
%>
<!-- Jsp Block -->
<%
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request, "start");
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    long oidLeaveStock = FRMQueryString.requestLong(request, "hidden_leave_stock_id");

    long departmentOID = FRMQueryString.requestLong(request, "department_id");
    String empName = FRMQueryString.requestString(request, "employee_name");

    /*variable declaration*/
    int recordToGet = 1000;
    String msgString = "";
    int iErrCode = FRMMessage.NONE;
    String whereClause = "";
    String orderClause = "";

    //get selected and edited
    Vector result = new Vector(1,1);
    if(iCommand==Command.SAVE){
        Vector selectedLeave =  SessLeaveStock.getLeaveStock(departmentOID, empName, start, recordToGet);
        for(int i=0; i<selectedLeave.size(); i++){
            Vector temp = (Vector)selectedLeave.get(i);
			Employee empl = (Employee)temp.get(0);
            LeaveStock leaveStock = (LeaveStock)temp.get(1);
			
            long stockOID = FRMQueryString.requestLong(request, "stock_oid_"+empl.getOID());			
            int addAl = FRMQueryString.requestInt(request, FrmLeaveStock.fieldNames[FrmLeaveStock.FRM_FIELD_ADD_AL]+"_"+empl.getOID());
            int addLl = FRMQueryString.requestInt(request, FrmLeaveStock.fieldNames[FrmLeaveStock.FRM_FIELD_ADD_LL]+"_"+empl.getOID());
            int addDp = FRMQueryString.requestInt(request, FrmLeaveStock.fieldNames[FrmLeaveStock.FRM_FIELD_ADD_DP]+"_"+empl.getOID());
			
			if(addAl!=0 || addLl!=0 || addDp!=0){
				leaveStock.setEmployeeId(empl.getOID());	
				leaveStock.setOID(stockOID);
				leaveStock.setAddAl(addAl);
				leaveStock.setAddLl(addLl);
				leaveStock.setAddDp(addDp);
				result.add(leaveStock);
			}

        }
    }
	
	//out.println(result);

    CtrlLeaveStock ctrlLeaveStock = new CtrlLeaveStock(request);
    ControlLine ctrLine = new ControlLine();
    Vector listLeaveStock = new Vector(1,1);

    /*switch statement */
    iErrCode = ctrlLeaveStock.action(iCommand , result);
    /* end switch*/
    FrmLeaveStock frmLeaveStock = ctrlLeaveStock.getForm();

    /*count list All LeaveStock*/

    int vectSize = SessLeaveStock.getCount(departmentOID, empName);

    /*switch list LeaveStock*/
    if((iCommand == Command.FIRST || iCommand == Command.PREV )||
      (iCommand == Command.NEXT || iCommand == Command.LAST)){
            start = ctrlLeaveStock.actionList(iCommand, start, vectSize, recordToGet);
     } 
    /* end switch list*/

    vectSize = SessLeaveStock.getCount(departmentOID, empName);

    LeaveStock leaveStock = ctrlLeaveStock.getLeaveStock();
    msgString =  ctrlLeaveStock.getMessage();

    /* get record to display */
    listLeaveStock = SessLeaveStock.getLeaveStock(departmentOID, empName, start, recordToGet);//list(start,recordToGet, whereClause , orderClause);

    /*handle condition if size of record to display = 0 and start > 0 	after delete*/
    if (listLeaveStock.size() < 1 && start > 0)
    {
         if (vectSize - recordToGet > recordToGet)
            start = start - recordToGet;   //go to Command.PREV
         else{
             start = 0 ;
             iCommand = Command.FIRST;
             prevCommand = Command.FIRST; //go to Command.FIRST
         }
         listLeaveStock = SessLeaveStock.getLeaveStock(departmentOID, empName, start, recordToGet);		 
    }
	
	//out.println("listLeaveStock : "+listLeaveStock);
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>harisma--</title>
<script language="JavaScript">

function cmdAdd(){
	document.frmleavestock.hidden_leave_stock_id.value="0";
	document.frmleavestock.command.value="<%=Command.ADD%>";
	document.frmleavestock.prev_command.value="<%=prevCommand%>";
	document.frmleavestock.action="leavestock_list.jsp";
	document.frmleavestock.submit();
}

function cmdAsk(oidLeaveStock){
	document.frmleavestock.hidden_leave_stock_id.value=oidLeaveStock;
	document.frmleavestock.command.value="<%=Command.ASK%>";
	document.frmleavestock.prev_command.value="<%=prevCommand%>";
	document.frmleavestock.action="leavestock_list.jsp";
	document.frmleavestock.submit();
}

function cmdConfirmDelete(oidLeaveStock){
	document.frmleavestock.hidden_leave_stock_id.value=oidLeaveStock;
	document.frmleavestock.command.value="<%=Command.DELETE%>";
	document.frmleavestock.prev_command.value="<%=prevCommand%>";
	document.frmleavestock.action="leavestock_list.jsp";
	document.frmleavestock.submit();
}

function cmdSave(){
	document.frmleavestock.command.value="<%=Command.SAVE%>";
	document.frmleavestock.prev_command.value="<%=prevCommand%>";
	document.frmleavestock.action="leavestock_list.jsp";
	document.frmleavestock.submit();
}

function cmdEdit(oidLeaveStock){
	document.frmleavestock.hidden_leave_stock_id.value=oidLeaveStock;
	document.frmleavestock.command.value="<%=Command.EDIT%>";
	document.frmleavestock.prev_command.value="<%=prevCommand%>";
	document.frmleavestock.action="leavestock_list.jsp";
	document.frmleavestock.submit();
}

function cmdCancel(oidLeaveStock){
	document.frmleavestock.hidden_leave_stock_id.value=oidLeaveStock;
	document.frmleavestock.command.value="<%=Command.EDIT%>";
	document.frmleavestock.prev_command.value="<%=prevCommand%>";
	document.frmleavestock.action="leavestock_list.jsp";
	document.frmleavestock.submit();
}

function cmdBack(){
	document.frmleavestock.command.value="<%=Command.BACK%>";
	document.frmleavestock.action="leavestock.jsp";
	document.frmleavestock.submit();
}

function cmdListFirst(){
	document.frmleavestock.command.value="<%=Command.FIRST%>";
	document.frmleavestock.prev_command.value="<%=Command.FIRST%>";
	document.frmleavestock.action="leavestock_list.jsp";
	document.frmleavestock.submit();
}

function cmdListPrev(){
	document.frmleavestock.command.value="<%=Command.PREV%>";
	document.frmleavestock.prev_command.value="<%=Command.PREV%>";
	document.frmleavestock.action="leavestock_list.jsp";
	document.frmleavestock.submit();
}

function cmdListNext(){
	document.frmleavestock.command.value="<%=Command.NEXT%>";
	document.frmleavestock.prev_command.value="<%=Command.NEXT%>";
	document.frmleavestock.action="leavestock_list.jsp";
	document.frmleavestock.submit();
}

function cmdListLast(){
	document.frmleavestock.command.value="<%=Command.LAST%>";
	document.frmleavestock.prev_command.value="<%=Command.LAST%>";
	document.frmleavestock.action="leavestock_list.jsp";
	document.frmleavestock.submit();
}

//-------------- script form image -------------------

function cmdDelPict(oidLeaveStock){
	document.frmimage.hidden_leave_stock_id.value=oidLeaveStock;
	document.frmimage.command.value="<%=Command.POST%>";
	document.frmimage.action="leavestock_list.jsp";
	document.frmimage.submit();
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

</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
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
        //document.frmsrcemployee.<%//=frmSrcEmployee.fieldNames[FrmSrcEmployee.FRM_FIELD_START_COMMENC] + "_mn"%>.style.visibility = 'hidden';
    }
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Attendance &gt; Leave Opname<!-- #EndEditable --> </strong></font> 
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
								  <%if(privStart || privViewSpec){%>
                                    <form name="frmleavestock" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_leave_stock_id" value="<%=oidLeaveStock%>">
                                      <input type="hidden" name="department_id" value="<%=departmentOID%>">
                                      <input type="hidden" name="employee_name" value="<%=empName%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8"  colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Leave 
                                                  Opname List</td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                  <table width="79%" border="0" cellspacing="1" cellpadding="1" class="listgen">
                                                    <tr class="listgentitle">
                                                      <td rowspan="2" width="4%">No</td>
                                                      <td rowspan="2" width="36%"> 
                                                        <div align="center"><b>Employee</b></div>
                                                      </td>
                                                      <td colspan="3"> 
                                                        <div align="center"><b>New 
                                                          Additional For </b></div>
                                                      </td>
                                                      <td colspan="3"> 
                                                        <div align="center"><b>Total</b></div>
                                                      </td>
                                                    </tr>
                                                    <tr class="listgentitle"> 
                                                      <td width="9%"> 
                                                        <div align="center"><b>AL</b></div>
                                                      </td>
                                                      <td width="8%"> 
                                                        <div align="center"><b>LL</b></div>
                                                      </td>
                                                      <td width="8%"> 
                                                        <div align="center"><b>DP</b></div>
                                                      </td>
                                                      <td width="10%"> 
                                                        <div align="center"><b>AL</b></div>
                                                      </td>
                                                      <td width="9%"> 
                                                        <div align="center"><b>LL</b></div>
                                                      </td>
                                                      <td width="16%"> 
                                                        <div align="center"><b>DP</b></div>
                                                      </td>
                                                    </tr>
                                                    <%if(listLeaveStock!=null && listLeaveStock.size()>0){
                                                        for(int i=0; i<listLeaveStock.size(); i++){
                                                            Vector temp = (Vector)listLeaveStock.get(i);
                                                            Employee emp = (Employee)temp.get(0);															
                                                            LeaveStock lStock = (LeaveStock)temp.get(1);
															//out.println(lStock.getOID());
                                                    %>
                                                    <tr class="listgensell">
                                                      <td width="4%"><%=(i+1)%></td>
                                                      <td width="36%"> <%=emp.getFullName()%> 
													    <input type="hidden" name="emp_id_<%=emp.getOID()%>" value="<%=emp.getOID()%>">
                                                        <input type="hidden" name="stock_oid_<%=emp.getOID()%>" value="<%=lStock.getOID()%>">
                                                      </td>
                                                      <td width="9%"> 
                                                        <div align="center"> 
                                                          <input type="text" name="<%=FrmLeaveStock.fieldNames[FrmLeaveStock.FRM_FIELD_ADD_AL]+"_"+emp.getOID()%>" size="5" class="formElemen">
                                                        </div>
                                                      </td>
                                                      <td width="8%"> 
                                                        <div align="center"> 
                                                          <input type="text" name="<%=FrmLeaveStock.fieldNames[FrmLeaveStock.FRM_FIELD_ADD_LL]+"_"+emp.getOID()%>" size="5"  class="formElemen">
                                                        </div>
                                                      </td>
                                                      <td width="8%"> 
                                                        <div align="center"> 
                                                          <input type="text" name="<%=FrmLeaveStock.fieldNames[FrmLeaveStock.FRM_FIELD_ADD_DP]+"_"+emp.getOID()%>" size="5" class="formElemen">
                                                        </div>
                                                      </td>
                                                      <td width="10%"> 
                                                        <div align="center"><%=lStock.getAlAmount()%></div>
                                                      </td>
                                                      <td width="9%"> 
                                                        <div align="center"><%=lStock.getLlAmount()%></div>
                                                      </td>
                                                      <td width="16%"> 
                                                        <div align="center"><%=lStock.getDpAmount()%></div>
                                                      </td>
                                                    </tr>
                                                    <%}}else{%>
                                                    <tr class="listgensell"> 
                                                      <td colspan="8"><font color="#FF0000"><i>no 
                                                        leave available ....</i></font></td>
                                                    </tr>
                                                    <%}%>
                                                  </table>
                                                </td>
                                              </tr>
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
                                                          else 
                                                            cmd =prevCommand; 
                                                       } 
                                                    %>
                                                  <% ctrLine.setLocationImg(approot+"/images");
                                                    ctrLine.initDefault();
                                                 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> </span> </td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3" class="command"> 
                                                  <%if(listLeaveStock!=null && listLeaveStock.size()>0){%>
                                                  <table width="33%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr> 
													<%if(privStart){%>
                                                      <td width="7%"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSaveOn.jpg',1)" id="aSearch"><img name="Image101" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                      <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                      <td width="40%" class="command" nowrap><a href="javascript:cmdSave()">Save 
                                                        Leave Opname</a></td>
													<%}%>	
                                                      <td width="3%" class="command" nowrap><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnBackOn.jpg',1)" id="aSearch"><img name="Image101" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                      <td width="3%" class="command" nowrap><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                      <td width="45%" class="command" nowrap><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnBackOn.jpg',1)" id="aSearch">Back 
                                                        To Search Page</a></td>
                                                    </tr>
                                                  </table>
                                                  <%}
                                                  else{
                                                  %>
                                                  <table width="16%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr> 
                                                      <td width="3%" class="command" nowrap><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnBackOn.jpg',1)" id="aSearch"><img name="Image101" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                      <td width="3%" class="command" nowrap><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                      <td width="45%" class="command" nowrap><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnBackOn.jpg',1)" id="aSearch">Back 
                                                        To Search Page</a></td>
                                                    </tr>
                                                  </table>
                                                  <%}%>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr align="left" valign="top" > 
                                          <td colspan="3" class="command">&nbsp; </td>
                                        </tr>
                                      </table>
                                    </form>
									<%}else{%>
									<div align="center">You do not have sufficient privilege to access this page.</div>
									<%}%>
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
