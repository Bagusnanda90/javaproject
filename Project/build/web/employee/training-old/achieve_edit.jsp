
<% 
/* 
 * Page Name  		:  achieve_edit.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
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
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<%--
<% int  appObjCode = 1;// AppObjInfo.composeObjCode(AppObjInfo.--, AppObjInfo.--, AppObjInfo.--); %>
<%//@ include file = "../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
--%>
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_SPECIAL_ACHIEVEMENT); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<!-- Jsp Block -->
<%

//----------

	int prevCommand = FRMQueryString.requestInt(request, "prev_command");	
	int iCommand = FRMQueryString.requestCommand(request);
	int recordToGet = 10;
	long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
	int start = FRMQueryString.requestInt(request, "start");
	Employee employee = new Employee();
	long oidPosition = 0;
	Position position = new Position();
	long oidDepartment = 0;
	Department department = new Department();
	//if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {
		try{
			employee = PstEmployee.fetchExc(oidEmployee);
			oidPosition = employee.getPositionId();
			position = PstPosition.fetchExc(oidPosition);
			oidDepartment = employee.getDepartmentId();
			department = PstDepartment.fetchExc(oidDepartment);
		}catch(Exception exc){
			employee = new Employee();
			position = new Position();
			department = new Department();
		}
	//}

//------------

	if(iCommand==Command.LIST){
		Vector vctHeaderPrint = new Vector(1,1);
		vctHeaderPrint.add(employee);
		vctHeaderPrint.add(position);
		vctHeaderPrint.add(department);
		session.putValue("HEADER_CNT", vctHeaderPrint);
	}
	
	CtrlSpecialAcheivement ctrlSpecialAcheivement = new CtrlSpecialAcheivement(request);
	long oidSpecialAchievement = FRMQueryString.requestLong(request, "achieve_id");

	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	String whereClause = PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_EMPLOYEE_ID]+"="+oidEmployee;
	String orderClause = PstSpecialAchievement.fieldNames[PstSpecialAchievement.FLD_DATE];
	
	//int iCommand = FRMQueryString.requestCommand(request);
	//int start = FRMQueryString.requestInt(request,"start");

	//out.println("oidSpecialAchievement >>>>>>>> "+oidSpecialAchievement); 
	ControlLine ctrLine = new ControlLine();

	iErrCode = ctrlSpecialAcheivement.action(iCommand , oidSpecialAchievement);

	errMsg = ctrlSpecialAcheivement.getMessage();
	FrmSpecialAcheivement frmSpecialAcheivement = ctrlSpecialAcheivement.getForm();
	SpecialAchievement specialAchievement = ctrlSpecialAcheivement.getSpecialAchievement();
	oidSpecialAchievement = specialAchievement.getOID();
	//if(iCommand == Command.SAVE && iErrCode == FRMMessage.NONE){
		//specialAchievement = new SpecialAchievement();	
	//}
	
	/*long oidEmployee = specialAchievement.getEmployeeId();
	//out.println("oidEmployee >>>>>>>> "+oidEmployee); 
        Employee employee = new Employee();
        long oidPosition = 0;
        Position position = new Position();
        long oidDepartment = 0;
        Department department = new Department();
        if ((iCommand==Command.EDIT) || (iCommand==Command.ASK) || (iCommand == Command.SAVE)) {
            try{
                employee = PstEmployee.fetchExc(oidEmployee);
                oidPosition = employee.getPositionId();
                position = PstPosition.fetchExc(oidPosition);
                oidDepartment = employee.getDepartmentId();
                department = PstDepartment.fetchExc(oidDepartment);
            }catch(Exception exc){
                employee = new Employee();
                position = new Position();
                department = new Department();
            }
        }
		
		*/
	/*if((iCommand==Command.DELETE)&&(iErrCode == FRMMessage.NONE)){
	%>
	<jsp:forward page="achieve_list.jsp"> 
	<jsp:param name="start" value="<%=start%>" />
	<jsp:param name="achieve_id" value="<%=specialAchievement.getOID()%>" />
	</jsp:forward>
	<%
	}*/
	
	int vectSize = 0;
	
	vectSize = PstSpecialAchievement.getCount(whereClause);
	
    if(iCommand == Command.SAVE && prevCommand == Command.ADD){
		start = PstSpecialAchievement.findLimitStart(oidSpecialAchievement, recordToGet, whereClause, orderClause);		
    }

    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||
	(iCommand==Command.LAST)||(iCommand==Command.LIST))
            start = ctrlSpecialAcheivement.actionList(iCommand, start, vectSize, recordToGet);
	
	Vector vctAchieve = PstSpecialAchievement.list(start, recordToGet, whereClause, orderClause);	
		
	
	/*out.println("start : "+start);
	out.println("iCommand : "+iCommand);
	out.println("vectSize : "+vectSize);*/
	
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Special Achievement</title>
<script language="JavaScript">

<%if(iCommand==Command.ADD || iCommand==Command.EDIT || iCommand==Command.ASK || iCommand==Command.SAVE || iErrCode!=FRMMessage.NONE){%>
		window.location="#go";
	<%}%>
	
	function cmdBackToEmployee(){
		document.fredit.command.value="<%=Command.BACK%>";
		document.fredit.start.value="0";
		document.fredit.action="achieve_list.jsp";
		document.fredit.submit();
	}

	
	function cmdCancel(){
		document.fredit.command.value="<%=Command.CANCEL%>";
		document.fredit.action="achieve_edit.jsp";
		document.fredit.submit();
	} 
	
	function cmdAdd(){
		document.fredit.command.value="<%=Command.ADD%>";
		document.fredit.prev_command.value="<%=Command.ADD%>";
		document.fredit.action="achieve_edit.jsp";
		document.fredit.submit();
	} 

	function cmdEdit(oid){ 
		document.fredit.achieve_id.value=oid;
		document.fredit.prev_command.value="<%=Command.LIST%>";
		document.fredit.command.value="<%=Command.EDIT%>";
		document.fredit.action="achieve_edit.jsp";
		document.fredit.submit(); 
	} 

	function cmdSave(){		
		document.fredit.command.value="<%=Command.SAVE%>"; 
		document.fredit.action="achieve_edit.jsp";
		document.fredit.submit();
	}

	function cmdAsk(oid){
		document.fredit.achieve_id.value=oid;
		document.fredit.command.value="<%=Command.ASK%>"; 
		document.fredit.action="achieve_edit.jsp";
		document.fredit.submit();
	} 

	function cmdConfirmDelete(oid){
		document.fredit.achieve_id.value=oid;
		document.fredit.command.value="<%=Command.DELETE%>";
		document.fredit.action="achieve_edit.jsp"; 
		document.fredit.submit();
	}  

	function cmdBack(){		
		document.fredit.command.value="<%=Command.BACK%>"; 
		document.fredit.action="achieve_edit.jsp";
		document.fredit.submit();
	}
	
	function cmdSearchEmp(){
            window.open("empdopsearch.jsp?emp_number=" + document.fredit.EMP_NUMBER.value + "&emp_fullname=" + document.fredit.EMP_FULLNAME.value + "&emp_department=" + document.fredit.EMP_DEPARTMENT.value, null, "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	
	
	function cmdListFirst(){
		document.fredit.command.value="<%=Command.FIRST%>";
		document.fredit.action="achieve_edit.jsp";
		document.fredit.submit();
	}

	function cmdListPrev(){
		document.fredit.command.value="<%=Command.PREV%>";
		document.fredit.action="achieve_edit.jsp";
		document.fredit.submit();
	}

	function cmdListNext(){
		document.fredit.command.value="<%=Command.NEXT%>";
		document.fredit.action="achieve_edit.jsp";
		document.fredit.submit();
	}

	function cmdListLast(){
		document.fredit.command.value="<%=Command.LAST%>";
		document.fredit.action="achieve_edit.jsp";
		document.fredit.submit();
	}
	
	function cmdPrint(){
		window.open("achieve_buffer.jsp?employee_oid=<%=oidEmployee%>&approot=<%=approot%>","reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no"); 
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
        //document.fredit.EMP_DEPARTMENT.style.visibility = "hidden";
    } 
	 
    function hideObjectForLockers(){ 
    }
	
    function hideObjectForCanteen(){
    }
	
    function hideObjectForClinic(){
    }

    function hideObjectForMasterdata(){
        //document.fredit.EMP_DEPARTMENT.style.visibility = "hidden";
    }
	
    function showObjectForMenu(){
        //document.fredit.EMP_DEPARTMENT.style.visibility = "";
    }
</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnCancelOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Training 
                  &gt; Special Achievement<!-- #EndEditable --> </strong></font> 
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
                                    <form name="fredit" method="post" action="">
									  <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="command" value="">
									  <input type="hidden" name="prev_command" value="<%=prevCommand%>">
									  <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                                      <input type="hidden" name="achieve_id" value="<%=oidSpecialAchievement%>">
                                      <input type="hidden" name="<%=FrmSpecialAcheivement.fieldNames[FrmSpecialAcheivement.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=oidEmployee%>">
                                      <table width="100%" cellspacing="2" cellpadding="2" >
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td colspan="2"> 
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                              <tr> 
                                                <td colspan="5" nowrap> 
                                                  <div align="center"><b><font size="3">SPECIAL 
                                                    ACHIEVEMENT</font></b> </div>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="20%" nowrap>&nbsp;</td>
                                                <td width="12%" nowrap>&nbsp;</td>
                                                <td width="12%" nowrap>&nbsp;</td>
                                                <td width="19%" nowrap>&nbsp;</td>
                                                <td width="37%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="20%" nowrap>&nbsp;</td>
                                                <td width="12%" nowrap> 
                                                  <div align="left"><i><b>Name</b></i></div>
                                                </td>
                                                <td width="12%" nowrap> : <%= employee.getFullName() %> </td>
                                                <td width="19%" nowrap> 
                                                  <div align="right"><i><b>Employee 
                                                    Number</b></i></div>
                                                </td>
                                                <td width="37%"> : <%= employee.getEmployeeNum() %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="20%" nowrap>&nbsp;</td>
                                                <td width="12%" nowrap> 
                                                  <div align="left"><i><b>Position</b></i></div>
                                                </td>
                                                <td width="12%" nowrap> : <%= position.getPosition() %> </td>
                                                <td width="19%" nowrap> 
                                                  <div align="right"><i><b><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></b></i></div>
                                                </td>
                                                <td width="37%"> : <%= department.getDepartment() %> </td>
                                              </tr>
                                              <tr> 
                                                <td width="20%" nowrap>&nbsp;</td>
                                                <td width="12%" nowrap> 
                                                  <div align="left"><i><b>Commencing 
                                                    Date</b></i></div>
                                                </td>
                                                <td width="12%" nowrap> : <%= Formater.formatDate(employee.getCommencingDate(), "dd MMMM yyyy") %> </td>
                                                <td width="19%" nowrap> 
                                                  <div align="right"></div>
                                                </td>
                                                <td width="37%">&nbsp; </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="1%"  valign="top"  >&nbsp;</td>
                                          <td colspan="2"  valign="top" align="right"  > 
                                            <hr>
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="1%"  valign="top"  >&nbsp;</td>
                                          <td colspan="2" nowrap  > 
                                            <table width="98%" border="0" cellpadding="1" cellspacing="1" class="listgen">
                                              <tr class="listgentitle"> 
                                                <td width="4%"> 
                                                  <div align="center"><b>No</b></div>
                                                </td>
                                                <td width="46%"> 
                                                  <div align="center"><b>Type 
                                                    Of Award</b></div>
                                                </td>
                                                <td width="32%"> 
                                                  <div align="center"><b>Presented 
                                                    By </b></div>
                                                </td>
                                                <td width="18%"> 
                                                  <div align="center"><b>Date</b></div>
                                                </td>
                                              </tr>
                                              <%if(vctAchieve!=null && vctAchieve.size()>0){
											  		for(int i=0; i<vctAchieve.size(); i++){
														SpecialAchievement spAch = (SpecialAchievement)vctAchieve.get(i);											  
											  %>
                                              <tr class="listgensell"> 
                                                <td width="4%"> 
                                                  <div align="center"><%=start + (i+1)%></div>
                                                </td>
                                                <td width="46%"> <a href="javascript:cmdEdit('<%=spAch.getOID()%>')"><%=spAch.getTypeOfAward()%></a></td>
                                                <td width="32%"><%=spAch.getPresentedBy()%></td>
                                                <td width="18%"> 
                                                  <div align="center"><%=Formater.formatDate(((spAch.getDate()==null) ? new Date() : spAch.getDate()), "dd MMMM yyyy")%></div>
                                                </td>
                                              </tr>
                                              <%	}
											  }else{%>
                                              <tr class="listgensell"> 
                                                <td colspan="4">no special achievement 
                                                  available ....</td>
                                              </tr>
                                              <%}%>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="1%"  valign="top"  >&nbsp;</td>
                                          <td colspan="2" align="right" nowrap  > 
                                            <div align="left"> 
                                              <%
										  //ontrolLine ctrLine = new ControlLine();
										  ctrLine.setLocationImg(approot+"/images");
										  ctrLine.initDefault();
										  %>
                                              <%=ctrLine.drawImageListLimit(iCommand, vectSize, start, recordToGet)%> &nbsp;</div>
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="1%"  valign="top"  >&nbsp;</td>
                                          <td colspan="2" nowrap  > 
                                            <%if(iCommand==Command.LIST || iCommand==Command.DELETE || iCommand==Command.FIRST || iCommand==Command.NEXT ||
										  iCommand==Command.PREV || iCommand==Command.LAST || iCommand==Command.BACK){%>
                                            <table cellpadding="0" cellspacing="0" border="0" width="50%">
                                              <tr> 
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image101" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td class="command" nowrap width="189"> 
                                                  <div align="left"><a href="javascript:cmdAdd()">New 
                                                    Special Achievement</a></div>
                                                </td>
                                                <td width="10"><img src="<%=approot%>/images/spacer.gif" width="15" height="4"></td>
                                                <td width="24"><a href="javascript:cmdBackToEmployee()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image102','','<%=approot%>/images/BtnCancelOn.jpg',1)"><img name="Image102" border="0" src="<%=approot%>/images/BtnCancel.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                <td width="20"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td class="command" nowrap width="204"><a href="javascript:cmdBackToEmployee()">Back 
                                                  To Employee List</a></td>
                                                <td class="command" nowrap ><img src="<%=approot%>/images/spacer.gif" width="15" height="4"></td>
                                                <td class="command" nowrap ><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image11','','<%=approot%>/images/list_f2.jpg',1)"><img name="Image11" border="0" src="<%=approot%>/images/list.jpg" width="24" height="24" alt="Print Out"></a></td>
                                                <td class="command" nowrap ><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td class="command" nowrap> 
                                                  <div align="left"><a href="javascript:cmdPrint()">Print 
                                                    Achievement</a></div>
                                                </td>
                                              </tr>
                                            </table>
                                            <%}%>
                                          </td>
                                        </tr>
										<%if(iCommand==Command.ADD || iCommand==Command.EDIT || iCommand==Command.ASK || iCommand==Command.SAVE || iErrCode!=FRMMessage.NONE){%>
                                        <tr align="left"> 
                                          <td width="1%"  valign="top"  >&nbsp;</td>
                                          <td colspan="2" align="right" nowrap  >
                                            <div align="left"><b>Special Achievement 
                                              Editor</b> </div>
                                          </td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="1%"  valign="top"  >&nbsp;</td>
                                          <td width="8%" align="right" nowrap  >&nbsp;</td>
                                          <td  width="91%"  valign="top" class="comment">*) 
                                            entry required</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="1%"  valign="top"  >&nbsp;</td>
                                          <td width="8%" align="right" nowrap  > 
                                            <div align="left">Type of Award</div>
                                          </td>
                                          <td  width="91%"  valign="top"> 
                                            <input type="text" name="<%=FrmSpecialAcheivement.fieldNames[FrmSpecialAcheivement.FRM_FIELD_TYPE_OF_AWARD]%>" value="<%=specialAchievement.getTypeOfAward()%>" class="formElemen" size="50">
                                            * <%=frmSpecialAcheivement.getErrorMsg(FrmSpecialAcheivement.FRM_FIELD_TYPE_OF_AWARD)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="1%"  valign="top"  >&nbsp;</td>
                                          <td width="8%" align="right" nowrap  > 
                                            <div align="left">Presented by</div>
                                          </td>
                                          <td  width="91%"  valign="top"> 
                                            <input type="text" name="<%=FrmSpecialAcheivement.fieldNames[FrmSpecialAcheivement.FRM_FIELD_PRESENTED_BY]%>" value="<%=specialAchievement.getPresentedBy()%>" class="formElemen" size="30">
                                            * <%=frmSpecialAcheivement.getErrorMsg(FrmSpecialAcheivement.FRM_FIELD_PRESENTED_BY)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="1%"  valign="top"  >&nbsp;</td>
                                          <td width="8%" align="right" nowrap  > 
                                            <div align="left">Date</div>
                                          </td>
                                          <td  width="91%"  valign="top"> <%=ControlDate.drawDateWithStyle(FrmSpecialAcheivement.fieldNames[FrmSpecialAcheivement.FRM_FIELD_DATE], (specialAchievement.getDate()==null) ? new Date() : specialAchievement.getDate(), 2, -10, "formElemen", "")%> * <%=frmSpecialAcheivement.getErrorMsg(FrmSpecialAcheivement.FRM_FIELD_DATE)%></td>
                                        </tr>
                                        <tr align="left"> 
                                          <td width="1%"  valign="top"  >&nbsp;</td>
                                          <td width="8%" align="right" nowrap  >&nbsp;</td>
                                          <td  width="91%"  valign="top">&nbsp;</td>
                                        </tr>
                                        <tr align="left"> 
                                          <td colspan="3"> 
                                            <%
                                                ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                ctrLine.setTableWidth("80");
                                                String scomDel = "javascript:cmdAsk('"+oidSpecialAchievement+"')";
                                                String sconDelCom = "javascript:cmdConfirmDelete('"+oidSpecialAchievement+"')";
                                                String scancel = "javascript:cmdEdit('"+oidSpecialAchievement+"')";
                                                ctrLine.setBackCaption("Back to List Special Achievement");
                                                ctrLine.setDeleteCaption("Delete Special Achievement");
                                                ctrLine.setSaveCaption("Save Special Achievement");
                                                ctrLine.setAddCaption("Add Special Achievement");
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
                                            %>
                                            <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%> </td>
                                        </tr>
										
										<%}%>
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
