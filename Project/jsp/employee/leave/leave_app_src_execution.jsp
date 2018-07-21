
<%-- 
    Document   : Leave_App
    Created on : Dec 26, 2009, 9:42:49 AM
    Author     : Tu Roy
--%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.leave.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_APPLICATION, AppObjInfo.OBJ_LEAVE_APPLICATION); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
int TYPE_ALL        = 1;
int TYPE_BY_SEARCH  = 2; 

privAdd=false;

int iCommand = FRMQueryString.requestCommand(request);
SrcLeaveApp objSrcLeaveApp = new SrcLeaveApp();
FrmSrcLeaveApp objFrmSrcLeaveApp = new FrmSrcLeaveApp();

if(iCommand==Command.BACK)
{        
	objFrmSrcLeaveApp= new FrmSrcLeaveApp(request, objSrcLeaveApp);
	try
	{				
		objSrcLeaveApp = (SrcLeaveApp) session.getValue(SessLeaveApplication.SESS_SRC_LEAVE_APPLICATION);			
		if(objSrcLeaveApp == null)
		{
			objSrcLeaveApp = new SrcLeaveApp();
		}		
	}
	catch (Exception e)
	{
		objSrcLeaveApp = new SrcLeaveApp(); 
	}
}

// pengecekan status user yang login utk menentukan status approval mana yang mestinya default buatnya\
boolean isManager = false;
boolean isHrManager = false;
if(positionType == PstPosition.LEVEL_MANAGER)
{
    isManager = true;
	
    long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
	if(departmentOid == hrdDepartmentOid)
	{	
		isHrManager = true;
	}
}
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Leave Application</title>
<script language="JavaScript">
<!--
function cmdAdd(){
	document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.ADD)%>";
	document.frmsrcleaveapp.action="leave_app_edit.jsp";
	document.frmsrcleaveapp.submit();
}

function cmdSearch(){
	document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.LIST)%>";
	document.frmsrcleaveapp.action="leave_app_list.jsp";
	document.frmsrcleaveapp.submit();
}
function cmdSearchExecute(){
	document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.LIST)%>";
        document.frmsrcleaveapp.type.value="BY_SEARCH"; 
	document.frmsrcleaveapp.action="leave_app_execute.jsp";
	document.frmsrcleaveapp.submit();
}

function cmdSearchExecuteAll(){
	document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.LIST)%>";
        document.frmsrcleaveapp.type.value="ALL"; 
	document.frmsrcleaveapp.action="leave_app_execute.jsp";
	document.frmsrcleaveapp.submit();
}

//-------------------------- for Calendar -------------------------
function getThn(){
<%
     out.println(ControlDatePopup.writeDateCaller("frmsrcleaveapp",FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_SUBMISSION_DATE]));
%>
}


function hideObjectForDate(){
        <%
            out.println(ControlDatePopup.writeDateHideObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_APPROVAL_STATUS]));
            out.println(ControlDatePopup.writeDateHideObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DOC_STATUS]));
            out.println(ControlDatePopup.writeDateHideObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_APPROVAL_HR_MAN]));
        %>
}

function showObjectForDate(){
        <%
            out.println(ControlDatePopup.writeDateShowObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_APPROVAL_STATUS]));
            out.println(ControlDatePopup.writeDateShowObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DOC_STATUS]));
            out.println(ControlDatePopup.writeDateHideObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_APPROVAL_HR_MAN]));
        %>
} 


//-------------------------------------------

function fnTrapKD(){
   if (event.keyCode == 13) {
	document.all.aSearch.focus();
	cmdSearch();
   }
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
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #EndEditable -->
<link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
<!-- #BeginEditable "stylestab" -->  
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">

<!-- Untuk Calendar-->
<%=ControlDatePopup.writeTable(approot)%>
<!-- End Calendar-->


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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Employee &gt; Leave Application &gt; Leave Form Execution<!-- #EndEditable --> 
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
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frmsrcleaveapp" method="post" action="">
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <input type="hidden" name="type" value="<%=String.valueOf(iCommand)%>">
                                      <table border="0" cellspacing="2" cellpadding="2" width="100%" >
                                        <tr> 
                                          <td width="13%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.PAYROLL) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="85%"> 
                                            <input type="text" name="<%=objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_EMP_NUMBER] %>"  value="<%= objSrcLeaveApp.getEmpNum() %>" class="elemenForm"  onkeydown="javascript:fnTrapKD()">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.FULL_NAME) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="85%"> 
                                            <input type="text" name="<%=objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_FULLNAME] %>"  value="<%= objSrcLeaveApp.getFullName() %>" class="elemenForm" onkeydown="javascript:fnTrapKD()" size="40">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="85%"> 
                                            <%															
											Vector department_value = new Vector(1,1);
											Vector department_key = new Vector(1,1);
                                                                                        String where;
                                                                                        
                                                                                        long oidHRD = 0;
                                                                                        
                                                                                        try{
                                                                                            oidHRD = Long.parseLong(PstSystemProperty.getValueByName("OID_HRD_DEPARTMENT"));
                                                                                        }catch(Exception E){
                                                                                            System.out.println("[exception] Sys Prop OID_HRD_DEPARTMENT [not set] "+E.toString());
                                                                                        }
                                                                                        
                                                                                        if(departmentOid==oidHRD){
                                                                                            where="";
                                                                                        }else{
                                                                                            where = " DEPARTMENT_ID = "+departmentOid;
                                                                                        }
											
											Vector listDept = PstDepartment.list(0, 0, where, " DEPARTMENT ");                                                        
											String selectValueDepartment = ""+objSrcLeaveApp.getDepartmentId();
											for (int i = 0; i < listDept.size(); i++) 
											{
												Department dept = (Department) listDept.get(i);
												department_key.add(dept.getDepartment());
												department_value.add(String.valueOf(dept.getOID()));
											}														
											%>
                                            <%=ControlCombo.draw(objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_DEPARTMENT],"elementForm", null, selectValueDepartment, department_value, department_key," onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="85%"> 
                                            <%														
											Vector section_value = new Vector(1,1);
											Vector section_key = new Vector(1,1);
											section_value.add("0");
											section_key.add("select ...");                                                              
											Vector listSec = PstSection.list(0, 0, "", " SECTION ");                                                          
											String selectValueSection = ""+objSrcLeaveApp.getSectionId();
											for (int i = 0; i < listSec.size(); i++) 
											{
												Section sec = (Section) listSec.get(i);
												section_key.add(sec.getSection());
												section_value.add(String.valueOf(sec.getOID()));
											}															
											%>
                                            <%=ControlCombo.draw(objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_SECTION],"elementForm", null, selectValueSection, section_value, section_key," onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="85%"> 
                                            <%														
											Vector position_value = new Vector(1,1);
											Vector position_key = new Vector(1,1);
											position_value.add("0");
											position_key.add("select ...");                                                       
											Vector listPos = PstPosition.list(0, 0, "", " POSITION ");                                                            
											String selectValuePosition = ""+objSrcLeaveApp.getPositionId();
											for (int i = 0; i < listPos.size(); i++) 
											{
												Position pos = (Position) listPos.get(i);
												position_key.add(pos.getPosition());
												position_value.add(String.valueOf(pos.getOID()));
											}														
											%>
                                            <%=ControlCombo.draw(objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_POSITION],"elementForm", null, selectValuePosition, position_value, position_key," onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">Date Of Application</td>
                                          <td width="2%">:</td>
                                          <td width="85%"> 
                                            <input type="checkbox" name="<%=objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_SUBMISSION]%>" <%=(objSrcLeaveApp.isSubmission() ? "checked" : "")%> value="1">
                                            <i><font color="#FF0000">Select all 
                                            date</font></i></td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td width="2%">&nbsp;</td>
                                          <td width="85%">&nbsp;                                          
                                          <%=ControlDatePopup.writeDate(FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_SUBMISSION_DATE],objSrcLeaveApp.getSubmissionDate())%>                                              
                                        </td>                                        
                                        </tr>
                                        <%--
                                        <tr>
                                          <td width="11%">Dep Head Approval</td>
                                          <td nowrap width="2%">:</td>
                                          <td nowrap width="87%"> 
                                            <%															
											Vector approval_value = new Vector(1,1);
											Vector approval_key = new Vector(1,1);

                                                                                        approval_value.add("-1");
											approval_key.add("All Status");
                                                                                        
											approval_value.add("0");
											approval_key.add("Not Approved");  
                                                                                        
                                                                                        approval_value.add("1");
											approval_key.add("Approved");
											
											String selectValueApprovalStatus = ""+objSrcLeaveApp.getApprovalStatus();                                                          
											
											%>
                                            <%=ControlCombo.draw(objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_APPROVAL_STATUS],"elementForm", null, selectValueApprovalStatus, approval_value, approval_key," onkeydown=\"javascript:fnTrapKD()\"") %></td>
                                        </tr>                                        
                                        <tr>
                                          <td width="11%">HR Manager Approval</td>
                                          <td nowrap width="2%">:</td>
                                          <td nowrap width="87%"> 
                                            <%															
											Vector approval_HR_value = new Vector(1,1);
											Vector approval_HR_key = new Vector(1,1);

                                                                                        approval_HR_value.add("-1");
											approval_HR_key.add("All Status");
                                                                                        
											approval_HR_value.add("0");
											approval_HR_key.add("Not Approved");  
                                                                                        
                                                                                        approval_HR_value.add("1");
											approval_HR_key.add("Approved");
											
											String selectValueApprovalHRStatus = ""+objSrcLeaveApp.getApprovalHRMan();                                                          
											
											%>
                                            <%=ControlCombo.draw(objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_APPROVAL_HR_MAN],"elementForm", null, selectValueApprovalHRStatus, approval_HR_value, approval_HR_key," onkeydown=\"javascript:fnTrapKD()\"") %></td>
                                        </tr>  
                                        <tr>
                                          <td width="11%">GM</td>
                                          <td nowrap width="2%">:</td>
                                          <td nowrap width="87%"> 
                                            <%															
											Vector approval_GM_value = new Vector(1,1);
											Vector approval_GM_key = new Vector(1,1);

                                                                                        approval_GM_value.add("-1");
											approval_GM_key.add("All Status");
                                                                                        
											approval_GM_value.add("0");
											approval_GM_key.add("Not Approved");  
                                                                                        
                                                                                        approval_GM_value.add("1");
											approval_GM_key.add("Approved");
											
											String selectValueApprovalGMStatus = ""+objSrcLeaveApp.getApprovalGM();                                                          
											
											%>
                                            <%=ControlCombo.draw(objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_APPROVAL_GM],"elementForm", null, selectValueApprovalGMStatus, approval_GM_value, approval_GM_key,"onkeydown=\"javascript:fnTrapKD()\"") %></td>
                                        </tr> 
                                        --%>
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="85%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="13%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="85%"> 
                                            <table border="0" cellpadding="0" cellspacing="0" width="500">
                                              <tr> 
                                                <td width="24px" ><a href="javascript:cmdSearchExecute()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search for Leave Application" ></a></td>
                                                <td width="5px"></td>
                                                <td width="30px" class="command" nowrap><a href="javascript:cmdSearch()">Search</a></td> 
                                                <td width="5px"></td>
                                                <td width="24px" ><a href="javascript:cmdSearchExecute()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search for Leave Application" ></a></td>
                                                <td width="5px"></td>
                                                <td width="30px" class="command" nowrap><a href="javascript:cmdSearchExecuteALL()">Search ALL</a></td> 
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
</html>
