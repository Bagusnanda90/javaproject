
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
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DB_EMPLOYEE); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privPrint=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));

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
		ctrlist.addHeader("No.","2%");
		ctrlist.addHeader("Payroll","6%");
		ctrlist.addHeader("Name","15%");
		ctrlist.addHeader("Address","18%");
		ctrlist.addHeader("Phone","7%");
		ctrlist.addHeader("Sex","5%");
		ctrlist.addHeader("Religion","5%");
		ctrlist.addHeader("Department","10%");
		ctrlist.addHeader("Position","10%");
		//ctrlist.addHeader("Section","5%");
		//ctrlist.addHeader("Emp Category","10%");
		ctrlist.addHeader("Level","7%");
			
		ctrlist.setLinkRow(1);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();

		for (int i = 0; i < objectClass.size(); i++) {
			Vector temp = (Vector)objectClass.get(i);
			Employee employee = (Employee)temp.get(0);
			Department department = (Department)temp.get(1);
			Position position = (Position)temp.get(2);
			Section section = (Section)temp.get(3);
			EmpCategory empCategory = (EmpCategory)temp.get(4);
			Level level = (Level)temp.get(5);
			Religion religion = (Religion)temp.get(6);
			Marital marital = (Marital)temp.get(7);
			//Locker locker = (Locker)temp.get(8);
			
			Vector rowx = new Vector();
                        rowx.add(String.valueOf(st + 1 + i));
			rowx.add(employee.getEmployeeNum());
			rowx.add(employee.getFullName());
			rowx.add(employee.getAddress());
			rowx.add(employee.getPhone());
			rowx.add(PstEmployee.sexKey[employee.getSex()]);
			rowx.add(religion.getReligion());
			rowx.add(department.getDepartment());
			rowx.add(position.getPosition());
			//rowx.add(section.getSection());
			//rowx.add(empCategory.getEmpCategory());
			rowx.add(level.getLevel());
			
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(employee.getOID()));
		}
		return ctrlist.draw();
	}
%>
<%
    long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
    int iCommand = FRMQueryString.requestCommand(request);
    int prevCommand = FRMQueryString.requestInt(request, "prev_command");
    int start = FRMQueryString.requestInt(request, "start");
    int iErrCode = FRMMessage.ERR_NONE;

    String msgStr = "";
    int recordToGet = 10;
    int vectSize = 0;
    String orderClause = "";
    String whereClause = "RESIGNED = 0";

    ControlLine ctrLine = new ControlLine();
    SrcEmployee srcEmployee = new SrcEmployee();
    CtrlEmployee ctrlEmployee = new CtrlEmployee(request);
    FrmSrcEmployee frmSrcEmployee = new FrmSrcEmployee(request, srcEmployee);

    if(iCommand == Command.LIST){
            frmSrcEmployee.requestEntityObject(srcEmployee);
    }

    if((iCommand==Command.NEXT)||(iCommand==Command.FIRST)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand == Command.BACK)){
	 try{ 
		srcEmployee = (SrcEmployee)session.getValue(SessEmployee.SESS_SRC_EMPLOYEE+"_1"); 
                if (srcEmployee == null) {
                    srcEmployee = new SrcEmployee();
                }
	 }catch(Exception e){
		srcEmployee = new SrcEmployee();
	 }
     }

    SessEmployee sessEmployee = new SessEmployee();
    session.putValue(SessEmployee.SESS_SRC_EMPLOYEE+"_1", srcEmployee);

    //out.println("prevCommand "+prevCommand);
    //out.println("oidEmployee "+oidEmployee);
    if(iCommand == Command.SAVE && prevCommand == Command.ADD){
	start = PstEmployee.findLimitStart(oidEmployee,recordToGet, whereClause,orderClause);
	vectSize = PstEmployee.getCount(whereClause);
    }else{
	vectSize = sessEmployee.countEmployee(srcEmployee);
    }

    if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||
	(iCommand==Command.LAST)||(iCommand==Command.LIST))
            start = ctrlEmployee.actionList(iCommand, start, vectSize, recordToGet);

    //out.println("vectSize "+vectSize);
    //out.println("start "+start);
    //out.println("iCommand "+iCommand);

    Vector listEmployee = new Vector(1,1);
    if(iCommand == Command.SAVE && prevCommand==Command.ADD){
	listEmployee = sessEmployee.searchEmployee(new SrcEmployee(), start, recordToGet);
    }else{
	listEmployee = sessEmployee.searchEmployee(srcEmployee, start, recordToGet);
    }
    //out.println("listEmployee "+listEmployee.size());
    //out.print("start = " + start);
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Employee List</title>
<script language="JavaScript">

	function cmdPrint(){
		window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeListPdf");
	}

	function cmdPrintXLS(){
		window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmployeeListXLS");
	}

	function cmdAdd(){
		document.frm_employee.command.value="<%=Command.ADD%>";
		document.frm_employee.prev_command.value="<%=Command.ADD%>";
		document.frm_employee.action="employee_edit.jsp";
		document.frm_employee.submit();
	}

	function cmdEdit(oid){
		document.frm_employee.employee_oid.value=oid;
		document.frm_employee.command.value="<%=Command.LIST%>";
		document.frm_employee.prev_command.value="<%=Command.LIST%>";
		document.frm_employee.action="training_hist_edit.jsp";
		document.frm_employee.submit();
	}

	function cmdListFirst(){
		document.frm_employee.command.value="<%=Command.FIRST%>";
		document.frm_employee.action="employee_list.jsp";
		document.frm_employee.submit();
	}

	function cmdListPrev(){
		document.frm_employee.command.value="<%=Command.PREV%>";
		document.frm_employee.action="employee_list.jsp";
		document.frm_employee.submit();
	}

	function cmdListNext(){
		document.frm_employee.command.value="<%=Command.NEXT%>";
		document.frm_employee.action="employee_list.jsp";
		document.frm_employee.submit();
	}

	function cmdListLast(){
		document.frm_employee.command.value="<%=Command.LAST%>";
		document.frm_employee.action="employee_list.jsp";
		document.frm_employee.submit();
	}

	function cmdBack(){
		document.frm_employee.command.value="<%=Command.BACK%>";
		document.frm_employee.action="src_training_hist.jsp";
		document.frm_employee.submit();
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg')">
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
                  Employee &gt; Training History &gt; Employee Search Result<!-- #EndEditable --> 
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frm_employee" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                                      <table border="0" width="100%">
                                        <tr> 
                                          <td height="8" width="100%" class="listtitle"><span class="listtitle">Employee 
                                            List</span> </td>
                                        </tr>
                                        <%if((listEmployee!=null)&&(listEmployee.size()>0)){%>
                                        <tr> 
                                          <td height="8" width="100%"><%=drawList(listEmployee, start)%></td>
                                        </tr>
                                        <%}else{%>
                                        <tr> 
                                          <td height="8" width="100%" class="comment"><span class="comment"><br>
                                            &nbsp;No Employee available</span> 
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
                                            <%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%> </td>
                                        </tr>
                                        <tr> 
                                          <td nowrap align="left" class="command">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td nowrap align="left" class="command"> 
                                            <table border="0" cellspacing="0" cellpadding="0" align="left">
                                              <tr> 
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap> <a href="javascript:cmdBack()" class="command">Back 
                                                  To Search Employee</a></td>
                                                <% if(privAdd){%>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <%}%>
                                                <% if(privPrint){%>
                                                <%}%>
                                              </tr>
                                            </table>
                                          </td>
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
