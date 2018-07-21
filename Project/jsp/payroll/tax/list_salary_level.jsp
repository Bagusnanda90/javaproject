<%@ page language="java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>

<%
boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
boolean privUpdate=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
boolean privDelete=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- JSP Block -->
<%!
public String drawList(Vector objectClass){
	String result = "";
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("80%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No","10%");
	ctrlist.addHeader("Level Code","30%");
	ctrlist.addHeader("Level Name","30%");
	ctrlist.addHeader("Variabel Component","30%");
	
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	Vector rowx = new Vector(1,1);
	int index = -1;
	
	if(objectClass!=null && objectClass.size()>0){
		for(int i=0; i<objectClass.size(); i++){
			SalaryLevel salaryLevel = (SalaryLevel)objectClass.get(i);
			
			rowx = new Vector();
			rowx.add(String.valueOf(salaryLevel.getSort_Idx()));
			rowx.add(String.valueOf(salaryLevel.getLevelCode()));
			rowx.add(String.valueOf(salaryLevel.getLevelName()));
			rowx.add("<a href=\"javascript:cmdView('"+String.valueOf(salaryLevel.getLevelCode())+"')\">View details</a>");
			
			lstData.add(rowx);
		}
		
		result = ctrlist.draw();
	}else{
			result = "<i>Belum ada data dalam sistem ...</i>";
		}
	return result;
}
%>

<%
// request data from jsp page
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
long oidSalaryLevel = FRMQueryString.requestLong(request, "hidden_oid_salary_level");
String codeSalary = FRMQueryString.requestString(request, "hidden_code_salary");

// variable declaration
int recordToGet = 10;
String msgString = "";
int iErrCode = FRMMessage.NONE;

// instantiate TaxType classes
CtrlSalaryLevel ctrlSalaryLevel = new CtrlSalaryLevel(request);
ControlLine ctrLine = new ControlLine();


// action on object agama defend on command entered
iErrCode = ctrlSalaryLevel.action(iCommand , oidSalaryLevel);
FrmSalaryLevel frmSalaryLevel = ctrlSalaryLevel.getForm();
SalaryLevel salaryLevel = ctrlSalaryLevel.getSalaryLevel();
msgString =  ctrlSalaryLevel.getMessage();

// get records to display
String whereClause = "";
String orderClause = PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_SORT_IDX];

int vectSize = PstSalaryLevel.getCount(whereClause);
if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST){
	start = ctrlSalaryLevel.actionList(iCommand, start, vectSize, recordToGet);
}

Vector listSalaryLevel = PstSalaryLevel.list(start, recordToGet, whereClause , orderClause);
if(listSalaryLevel.size()<1 && start>0){
	 if(vectSize - recordToGet>recordToGet){
		 start = start - recordToGet;
	 }else{
		 start = 0 ;
		 iCommand = Command.FIRST;
		 prevCommand = Command.FIRST;
	 }
	 listSalaryLevel = PstSalaryLevel.list(start, recordToGet, whereClause , orderClause);
}
%>
<%
int idx = FRMQueryString.requestInt(request, "idx");
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
<script language="JavaScript">
function cmdAdd(){
	document.frmSalaryLevel.hidden_id_salary_level.value="0";
	document.frmSalaryLevel.command.value="<%=Command.ADD%>";
	document.frmSalaryLevel.prev_command.value="<%=prevCommand%>";
	document.frmSalaryLevel.action="salary-level.jsp";
	document.frmSalaryLevel.submit();
}

function cmdAsk(oidSalaryLevel){
	document.frmSalaryLevel.hidden_id_salary_level.value=oidSalaryLevel;
	document.frmSalaryLevel.command.value="<%=Command.ASK%>";
	document.frmSalaryLevel.prev_command.value="<%=prevCommand%>";
	document.frmSalaryLevel.action="salary-level.jsp";
	document.frmSalaryLevel.submit();
}

function cmdConfirmDelete(oid){
		var x = confirm(" Are You Sure to Delete?");
		if(x){
			document.frmSalaryLevel.command.value="<%=Command.DELETE%>";
			document.frmSalaryLevel.action="salary-level.jsp";
			document.frmSalaryLevel.submit();
		}
}


function cmdSave(){
	document.frmSalaryLevel.command.value="<%=Command.SAVE%>";
	document.frmSalaryLevel.prev_command.value="<%=prevCommand%>";
	document.frmSalaryLevel.action="salary-level.jsp";
	document.frmSalaryLevel.submit();
	}

function cmdEdit(oidSalaryLevel){
	document.frmSalaryLevel.hidden_id_salary_level.value=oidSalaryLevel;
	document.frmSalaryLevel.command.value="<%=Command.EDIT%>";
	document.frmSalaryLevel.prev_command.value="<%=prevCommand%>";
	document.frmSalaryLevel.action="salary-level.jsp";
	document.frmSalaryLevel.submit();
	}
	
function cmdView(salaryLevel){
    document.frmSalaryLevel.prev_command.value="<%=prevCommand%>";
	document.frmSalaryLevel.action="variabel_component.jsp?hidden_code_salary="+salaryLevel;
	document.frmSalaryLevel.submit();
	}

function cmdCancel(oidSalaryLevel){
	document.frmSalaryLevel.hidden_id_salary_level.value=oidSalaryLevel;
	document.frmSalaryLevel.command.value="<%=Command.EDIT%>";
	document.frmSalaryLevel.prev_command.value="<%=prevCommand%>";
	document.frmSalaryLevel.action="salary-level.jsp";
	document.frmSalaryLevel.submit();
}

function cmdBack(){
	document.frmSalaryLevel.command.value="<%=Command.BACK%>";
	document.frmSalaryLevel.action="home.jsp";
	document.frmSalaryLevel.submit();
	}

function cmdListFirst(){
	document.frmSalaryLevel.command.value="<%=Command.FIRST%>";
	document.frmSalaryLevel.prev_command.value="<%=Command.FIRST%>";
	document.frmSalaryLevel.action="salary-level.jsp";
	document.frmSalaryLevel.submit();
}

function cmdListPrev(){
	document.frmSalaryLevel.command.value="<%=Command.PREV%>";
	document.frmSalaryLevel.prev_command.value="<%=Command.PREV%>";
	document.frmSalaryLevel.action="salary-level.jsp";
	document.frmSalaryLevel.submit();
	}

function cmdListNext(){
	document.frmSalaryLevel.command.value="<%=Command.NEXT%>";
	document.frmSalaryLevel.prev_command.value="<%=Command.NEXT%>";
	document.frmSalaryLevel.action="salary-level.jsp";
	document.frmSalaryLevel.submit();
}

function cmdListLast(){
	document.frmSalaryLevel.command.value="<%=Command.LAST%>";
	document.frmSalaryLevel.prev_command.value="<%=Command.LAST%>";
	document.frmSalaryLevel.action="salary-level.jsp";
	document.frmSalaryLevel.submit();
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
</script>

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
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Salary 
                  Level<!-- #EndEditable --> </strong></font> </td>
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
                                    <form name="frmSalaryLevel" method="post" action="">
									  <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="hidden_oid_salary_level" value="<%=oidSalaryLevel%>">
                                      <table width="963" border="0" cellspacing="0" cellpadding="0" class="listgensell">
                                        <tr> 
                                          <td width="963">&nbsp;</td>
                                        </tr>
										<%
										try{
										%>
                                        <tr> 
                                          <td>
										 <%=drawList(listSalaryLevel)%> 
										  </td>
                                        </tr>
										<% 
										  }catch(Exception exc){ 
										  System.out.println("Err::::::"+exc.toString());
										  }%>
										
										<tr>
										<td>
										<table>
										  </table>
										</td>
										</tr>
                                        
                                      </table>
									  <table width="100%" border="0">
									  <tr> 
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
													  { 
														if((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidSalaryLevel == 0))
															cmd = PstSalaryLevel.findLimitCommand(start,recordToGet,vectSize);
														else
															cmd = prevCommand;
													  } 
												   } 
												%>
												<% ctrLine.setLocationImg(approot+"/images");
												ctrLine.initDefault();
												 %>
                                                  <%=ctrLine.drawImageListLimit(cmd,vectSize,start,recordToGet)%> 
                                                  </span> </td>
											</tr>
											
									  <!--		<tr> 
                                          <td> <input type="submit" name="Submit" value="Regenerate Data Forms"> 
                                          </td>
										  <td width="0"></td>
                                        </tr>-->
										
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
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
