
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
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SECRETARY , AppObjInfo.G2_SECRETARY_GENERAL   , AppObjInfo.OBJ_SG_LEAVE_DP   	); %>
<% //int  appObjCodeSpec = AppObjInfo.composeObjCode(AppObjInfo.G1_SECRETARY , AppObjInfo.G2_SECRETARY_SPECIFIC   , AppObjInfo.OBJ_SS_LEAVE_DP   	); %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_DAY_OFF_PAYMENT   	); %>
<% int  appObjCodeSpec = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_LEAVE_MANAGEMENT   	); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//	boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
//    boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    //boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
    //boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));

	boolean privStartSpec=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeSpec, AppObjInfo.COMMAND_VIEW));
    boolean privAddSpec=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeSpec, AppObjInfo.COMMAND_ADD));
    //boolean privUpdateSpec=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeSpec, AppObjInfo.COMMAND_UPDATE));
    //boolean privDeleteSpec=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCodeSpec, AppObjInfo.COMMAND_DELETE));
	
	//out.println("privStart : "+privStart+", privAdd : "+privAdd+", privUpdate : "+privUpdate+", privDelete : "+privDelete);
	
%>
<!-- Jsp Block -->
<%
    int iCommand = FRMQueryString.requestCommand(request);

    SrcLeave srcLeave = new SrcLeave();
    FrmSrcLeave frmSrcLeave = new FrmSrcLeave();

    if(iCommand==Command.BACK)
    {        
        frmSrcLeave = new FrmSrcLeave(request, srcLeave);

        try{			
            srcLeave = (SrcLeave) session.getValue(SessLeave.SESS_SRC_LEAVE);			
		if(srcLeave == null)
                    srcLeave = new SrcLeave();
		//System.out.println("ecccccc "+srcEmpSchedule.getOrderBy());
        }catch (Exception e){
			System.out.println("e....."+e.toString());
            srcLeave = new SrcLeave();
        }
    }	

/*
try{
	srcLeave = (SrcLeave)session.getValue(SessLeave.SESS_SRC_LEAVE);
}catch(Exception e){
	srcLeave = new SrcLeave();
}

try{
	session.removeValue(SessLeave.SESS_SRC_LEAVE);
}catch(Exception e){
}
*/
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Leave Management</title>
<script language="JavaScript">
<!--



function cmdAdd(){
	document.frmsrcleave.command.value="<%=Command.ADD%>";
	document.frmsrcleave.action="leave_edit.jsp";
	document.frmsrcleave.submit();
}

function cmdSearch(){
	document.frmsrcleave.command.value="<%=Command.LIST%>";
	document.frmsrcleave.action="leave_list.jsp";
	document.frmsrcleave.submit();
}

function fnTrapKD(){
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
}

//-------------- script control line -------------------
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
<!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
<!--
function hideObjectForEmployee(){ 
	document.frmsrcleave.<%=frmSrcLeave.fieldNames[FrmSrcLeave.FRM_FIELD_DEPARTMENT]%>.style.visibility = "hidden";
    document.frmsrcleave.<%=frmSrcLeave.fieldNames[FrmSrcLeave.FRM_FIELD_SECTION]%>.style.visibility = "hidden";
    document.frmsrcleave.<%=frmSrcLeave.fieldNames[FrmSrcLeave.FRM_FIELD_POSITION]%>.style.visibility = "hidden";
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
    document.frmsrcleave.<%=frmSrcLeave.fieldNames[FrmSrcLeave.FRM_FIELD_DEPARTMENT]%>.style.visibility = "";
    document.frmsrcleave.<%=frmSrcLeave.fieldNames[FrmSrcLeave.FRM_FIELD_SECTION]%>.style.visibility = "";
    document.frmsrcleave.<%=frmSrcLeave.fieldNames[FrmSrcLeave.FRM_FIELD_POSITION]%>.style.visibility = "";
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

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
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
							  <%if(privStart || privStartSpec){%>
                                    <form name="frmsrcleave" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
									  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="63%">&nbsp;</td>
                                                <td width="34%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="63%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" >
                                                    <tr> 
                                                      <td> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.PAYROLL) %></div>
                                                      </td>
                                                      <td> 
                                                        <input type="text" name="<%=frmSrcLeave.fieldNames[FrmSrcLeave.FRM_FIELD_EMP_NUMBER] %>"  value="<%= srcLeave.getEmpNumber() %>" class="elemenForm"  onkeydown="javascript:fnTrapKD()">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td> 
                                                        <div align="left">Full 
                                                          Name</div>
                                                      </td>
                                                      <td> 
                                                        <input type="text" name="<%=frmSrcLeave.fieldNames[FrmSrcLeave.FRM_FIELD_FULLNAME] %>"  value="<%= srcLeave.getFullName() %>" class="elemenForm" onkeydown="javascript:fnTrapKD()">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                      </td>
                                                      <td> 
                                                        <%
						Vector department_value = new Vector(1,1);
						Vector department_key = new Vector(1,1);
                                                            department_value.add("0");
                                                            department_key.add("select ...");                                                          
                                                            Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");                                                        
					 	String selectValueDepartment = (String)srcLeave.getDepartment();
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                    Department dept = (Department) listDept.get(i);
                                                                    department_key.add(dept.getDepartment());
                                                                    department_value.add(String.valueOf(dept.getOID()));
                                                            }
						%>
                                                        <%= ControlCombo.draw(frmSrcLeave.fieldNames[FrmSrcLeave.FRM_FIELD_DEPARTMENT],"elementForm", null, selectValueDepartment, department_value, department_key," onkeydown=\"javascript:fnTrapKD()\"") %> 
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div>
                                                      </td>
                                                      <td> 
                                                        <%
						Vector section_value = new Vector(1,1);
						Vector section_key = new Vector(1,1);
                                                            section_value.add("0");
                                                            section_key.add("select ...");                                                              
                                                            Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");                                                          
					 	String selectValueSection = (String)srcLeave.getSection();
                                                            for (int i = 0; i < listSec.size(); i++) {
                                                                    Section sec = (Section) listSec.get(i);
                                                                    section_key.add(sec.getSection());
                                                                    section_value.add(String.valueOf(sec.getOID()));
                                                            }
						%>
                                                        <%= ControlCombo.draw(frmSrcLeave.fieldNames[FrmSrcLeave.FRM_FIELD_SECTION],"elementForm", null, selectValueSection, section_value, section_key," onkeydown=\"javascript:fnTrapKD()\"") %> 
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div>
                                                      </td>
                                                      <td> 
                                                        <%
						Vector position_value = new Vector(1,1);
						Vector position_key = new Vector(1,1);
                                                            position_value.add("0");
                                                            position_key.add("select ...");                                                       
                                                            Vector listPos = PstPosition.list(0, 0, "", " POSITION ");                                                            
					 	String selectValuePosition = (String)srcLeave.getPosition();
                                                            for (int i = 0; i < listPos.size(); i++) {
                                                                    Position pos = (Position) listPos.get(i);
                                                                    position_key.add(pos.getPosition());
                                                                    position_value.add(String.valueOf(pos.getOID()));
                                                            }
						%>
                                                        <%= ControlCombo.draw(frmSrcLeave.fieldNames[FrmSrcLeave.FRM_FIELD_POSITION],"elementForm", null, selectValuePosition, position_value, position_key," onkeydown=\"javascript:fnTrapKD()\"") %> 
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                      <td>&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td>&nbsp;</td>
                                                      <td nowrap> 
                                                        <table border="0" cellpadding="0" cellspacing="0" width="30">
                                                          <tr> 
                                                            <td width="4%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td width="11%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule" ></a></td>
                                                            <td width="4%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td width="11%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                              for Leave</a></td>
                                                            <% if(privAdd || privAddSpec){%>
                                                            <td width="4%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td width="11%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Schedule"></a></td>
                                                            <td width="4%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td width="11%" class="command" nowrap><a href="javascript:cmdAdd()">Add 
                                                              New Leave</a></td>
                                                            <%}%>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                                <td width="34%">&nbsp;</td>
                                              </tr>
                                              <%-- <tr> 
                                                <td width="1%">&nbsp;</td>
                                                <td width="65%"> 
                                                  <input type="button" name="Submit" value="Search" onClick="javascript:cmdSearch()">
                                                </td>
                                                <td width="34%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="1%">&nbsp;</td>
                                                <td width="65%"> 
                                                  <% if(privAdd){%>
                                                  <a href="javascript:cmdAdd()">Add 
                                                  New</a> 
                                                  <%}%>
                                                </td>
                                                <td width="34%">&nbsp;</td>
                                              </tr> --%>
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
	<%if(privStart){%>
	document.frmsrcleave.<%=frmSrcLeave.fieldNames[FrmSrcLeave.FRM_FIELD_EMP_NUMBER] %>.focus();
	<%}%>
</script>
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
