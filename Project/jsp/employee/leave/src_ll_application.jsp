
<% 
/* 
 * Page Name  		:  srcleave.jsp
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
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.dp.*" %>
<%@ page import = "com.dimata.harisma.session.leave.dp.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_APPLICATION, AppObjInfo.OBJ_DP_APPLICATION); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
privAdd=false;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- Jsp Block -->
<%

int iCommand = FRMQueryString.requestCommand(request);

SrcLeaveApplication objSrcLeaveApplication = new SrcLeaveApplication();
FrmSrcLeaveApplication objFrmSrcLeaveApplication = new FrmSrcLeaveApplication();

if(iCommand==Command.BACK)
{        
	objFrmSrcLeaveApplication = new FrmSrcLeaveApplication(request, objSrcLeaveApplication);
	try
	{				
		objSrcLeaveApplication = (SrcLeaveApplication) session.getValue(SessLeaveApplication.SESS_SRC_DP_APPLICATION);			
		if(objSrcLeaveApplication == null)
		{
			objSrcLeaveApplication = new SrcLeaveApplication();
		}		
	}
	catch (Exception e)
	{
		objSrcLeaveApplication = new SrcLeaveApplication();
	}
}	
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - LL Application</title>
<script language="JavaScript">
<!--
function cmdAdd(){
	document.frmsrcdpapplication.command.value="<%=String.valueOf(Command.ADD)%>";
	document.frmsrcdpapplication.action="ll_application_edit.jsp";
	document.frmsrcdpapplication.submit();
}

function cmdSearch(){
	document.frmsrcdpapplication.command.value="<%=String.valueOf(Command.LIST)%>";
	document.frmsrcdpapplication.action="ll_application_list.jsp";
	document.frmsrcdpapplication.submit();
}

//-------------------------- for Calendar -------------------------
    function getThn(){
        <%
         out.println(ControlDatePopup.writeDateCaller("frmsrcdpapplication",FrmSrcLeaveApplication.fieldNames[FrmSrcLeaveApplication.FRM_FIELD_SUBMISSION_DATE]));
         out.println(ControlDatePopup.writeDateCaller("frmsrcdpapplication",FrmSrcLeaveApplication.fieldNames[FrmSrcLeaveApplication.FRM_FIELD_TAKEN_DATE]));
         %>
    }


    function hideObjectForDate(){
        <%=ControlDatePopup.writeDateHideObj("frmsrcdpapplication", FrmSrcLeaveApplication.fieldNames[FrmSrcLeaveApplication.FRM_FIELD_APPROVAL_STATUS])%>        
        <%=ControlDatePopup.writeDateHideObj("frmsrcdpapplication", FrmSrcLeaveApplication.fieldNames[FrmSrcLeaveApplication.FRM_FIELD_DOC_STATUS])%>        
    }

    function showObjectForDate(){
        <%=ControlDatePopup.writeDateShowObj("frmsrcdpapplication", FrmSrcLeaveApplication.fieldNames[FrmSrcLeaveApplication.FRM_FIELD_APPROVAL_STATUS])%>        
        <%=ControlDatePopup.writeDateShowObj("frmsrcdpapplication", FrmSrcLeaveApplication.fieldNames[FrmSrcLeaveApplication.FRM_FIELD_DOC_STATUS])%>
    } 
//------------------------------------------------------------------

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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSaveOn.jpg')">

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
                  Employee &gt; Leave &gt; LL Application<!-- #EndEditable --> 
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
                                    <form name="frmsrcdpapplication" method="post" action="">
                                      <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                      <table border="0" cellspacing="2" cellpadding="2" width="100%" >
                                        <tr> 
                                          <td width="11%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.PAYROLL) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="87%"> 
                                            <input type="text" name="<%=objFrmSrcLeaveApplication.fieldNames[objFrmSrcLeaveApplication.FRM_FIELD_EMP_NUMBER] %>"  value="<%= objSrcLeaveApplication.getEmpNum() %>" class="elemenForm"  onkeydown="javascript:fnTrapKD()">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="11%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.FULL_NAME) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="87%"> 
                                            <input type="text" name="<%=objFrmSrcLeaveApplication.fieldNames[objFrmSrcLeaveApplication.FRM_FIELD_FULLNAME] %>"  value="<%= objSrcLeaveApplication.getFullName() %>" class="elemenForm" onkeydown="javascript:fnTrapKD()" size="40">
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="11%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="87%"> 
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
											String selectValueDepartment = ""+objSrcLeaveApplication.getDepartmentId();
											for (int i = 0; i < listDept.size(); i++) 
											{
												Department dept = (Department) listDept.get(i);
												department_key.add(dept.getDepartment());
												department_value.add(String.valueOf(dept.getOID()));
											}														
											%>
                                            <%=ControlCombo.draw(objFrmSrcLeaveApplication.fieldNames[objFrmSrcLeaveApplication.FRM_FIELD_DEPARTMENT],"elementForm", null, selectValueDepartment, department_value, department_key," onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                        </tr>
                                        <tr> 
                                          <td width="11%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="87%"> 
                                            <%														
											Vector section_value = new Vector(1,1);
											Vector section_key = new Vector(1,1);
											section_value.add("0");
											section_key.add("select ...");                                                              
											Vector listSec = PstSection.list(0, 0, "", " SECTION ");                                                          
											String selectValueSection = ""+objSrcLeaveApplication.getSectionId();
											for (int i = 0; i < listSec.size(); i++) 
											{
												Section sec = (Section) listSec.get(i);
												section_key.add(sec.getSection());
												section_value.add(String.valueOf(sec.getOID()));
											}															
											%>
                                            <%=ControlCombo.draw(objFrmSrcLeaveApplication.fieldNames[objFrmSrcLeaveApplication.FRM_FIELD_SECTION],"elementForm", null, selectValueSection, section_value, section_key," onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                        </tr>
                                        <tr> 
                                          <td width="11%"> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div>
                                          </td>
                                          <td width="2%">:</td>
                                          <td width="87%"> 
                                            <%														
											Vector position_value = new Vector(1,1);
											Vector position_key = new Vector(1,1);
											position_value.add("0");
											position_key.add("select ...");                                                       
											Vector listPos = PstPosition.list(0, 0, "", " POSITION ");                                                            
											String selectValuePosition = ""+objSrcLeaveApplication.getPositionId();
											for (int i = 0; i < listPos.size(); i++) 
											{
												Position pos = (Position) listPos.get(i);
												position_key.add(pos.getPosition());
												position_value.add(String.valueOf(pos.getOID()));
											}														
											%>
                                            <%=ControlCombo.draw(objFrmSrcLeaveApplication.fieldNames[objFrmSrcLeaveApplication.FRM_FIELD_POSITION],"elementForm", null, selectValuePosition, position_value, position_key," onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                        </tr>
                                        <tr> 
                                          <td width="11%">Submission Date</td>
                                          <td width="2%">:</td>
                                          <td width="87%"> 
                                            <input type="checkbox" name="<%=objFrmSrcLeaveApplication.fieldNames[objFrmSrcLeaveApplication.FRM_FIELD_SUBMISSION]%>" <%=(objSrcLeaveApplication.isSubmission() ? "checked" : "")%> value="1">
                                            <i><font color="#FF0000">Select all 
                                            date</font></i></td>
                                        </tr>
                                        <tr> 
                                          <td width="11%">&nbsp;</td>
                                          <td width="2%">&nbsp;</td>
                                          <td width="87%">&nbsp;
                                       
                                            <%=ControlDatePopup.writeDate(FrmSrcLeaveApplication.fieldNames[FrmSrcLeaveApplication.FRM_FIELD_SUBMISSION_DATE],objSrcLeaveApplication.getSubmissionDate())%>
                                            </td>
                                        </tr>
                                        <tr> 
                                          <td width="11%">Taken Date</td>
                                          <td width="2%">:</td>
                                          <td width="87%"> 
                                            <input type="checkbox" name="<%=objFrmSrcLeaveApplication.fieldNames[objFrmSrcLeaveApplication.FRM_FIELD_TAKEN]%>" <%=(objSrcLeaveApplication.isTaken() ? "checked" : "")%> value="1">
                                            <i><font color="#FF0000">Select all 
                                            date</font></i></td>
                                        </tr>
                                        <tr> 
                                          <td width="11%">&nbsp;</td>
                                          <td width="2%">&nbsp;</td>
                                          <td width="87%">&nbsp;
                                          <% //ControlDate.drawDate(objFrmSrcLeaveApplication.fieldNames[objFrmSrcLeaveApplication.FRM_FIELD_TAKEN_DATE], (objSrcLeaveApplication.getTakenDate()==null ? new Date() : objSrcLeaveApplication.getTakenDate()), 1,-5) %>
                                          <%=ControlDatePopup.writeDate(FrmSrcLeaveApplication.fieldNames[FrmSrcLeaveApplication.FRM_FIELD_TAKEN_DATE],objSrcLeaveApplication.getTakenDate())%>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="11%">Approval Status</td>
                                          <td nowrap width="2%">:</td>
                                          <td nowrap width="87%"> 
                                            <%															
                                            Vector approval_value = new Vector(1,1);
                                            Vector approval_key = new Vector(1,1);

                                            approval_value.add("-1");
                                            approval_key.add("All Approval Status");                                                          

                                            approval_value.add("0");
                                            approval_key.add("Not Approved");                                                          

                                            approval_value.add("1");//+PstDpApplication.FLD_APPROVE_BY_DEPT_HEAD
                                            approval_key.add("Approved");

                                            String selectValueApprovalStatus = ""+objSrcLeaveApplication.getApprovalStatus();                                                         
                                            %>
                                            <%=ControlCombo.draw(objFrmSrcLeaveApplication.fieldNames[objFrmSrcLeaveApplication.FRM_FIELD_APPROVAL_STATUS],"elementForm", null, selectValueApprovalStatus, approval_value, approval_key," onkeydown=\"javascript:fnTrapKD()\"") %></td>
                                        </tr>
                                        <tr> 
                                          <td width="11%">Document Status</td>
                                          <td nowrap width="2%">:</td>
                                          <td nowrap width="87%"> 
                                            <%															
                                            Vector status_value = new Vector(1,1);
                                            Vector status_key = new Vector(1,1);

                                            status_value.add("-1");
                                            status_key.add("All Document Status");                                                          

                                            status_value.add(""+PstDpAppMain.FLD_DOC_STATUS_VALID);
                                            status_key.add(PstDpAppMain.fieldStatusNames[PstDpAppMain.FLD_DOC_STATUS_VALID]);

                                            status_value.add(""+PstDpAppMain.FLD_DOC_STATUS_NOT_VALID);
                                            status_key.add(PstDpAppMain.fieldStatusNames[PstDpAppMain.FLD_DOC_STATUS_NOT_VALID]);
                                            
                                            String selectValueStatus = ""+objSrcLeaveApplication.getStatus();                                                         
                                            %>
                                            <%=ControlCombo.draw(objFrmSrcLeaveApplication.fieldNames[objFrmSrcLeaveApplication.FRM_FIELD_DOC_STATUS],"elementForm", null, selectValueStatus, status_value, status_key," onkeydown=\"javascript:fnTrapKD()\"") %></td>
                                        </tr>
                                        <tr> 
                                          <td width="11%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="87%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="11%">&nbsp;</td>
                                          <td nowrap width="2%">&nbsp;</td>
                                          <td nowrap width="87%"> 
                                            <table border="0" cellpadding="0" cellspacing="0" width="424">
                                              <tr> 
                                                <td width="6%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search for DP Application" ></a></td>
                                                <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="42%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                  for LL Application</a></td>
                                                <%if(privAdd){%>
                                                <td width="6%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New DP Application"></a></td>
                                                <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="42%" class="command" nowrap><a href="javascript:cmdAdd()">Add 
                                                  New LL Application</a></td>
                                                <%}%>
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
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
