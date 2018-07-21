
<%@page import="com.dimata.harisma.form.payroll.SrcFrmPaySimulation"%>
<%@page import="com.dimata.harisma.form.payroll.SrcFrmPaySimulation"%>
<% 
/*  
 * Page Name  		:  srcPosition.jsp
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
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% 
    int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL , AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_SIMULATION); 
    
%>
<%@ include file = "../../main/checkuser.jsp" %>

<!-- Jsp Block -->
<%
int iCommand = FRMQueryString.requestCommand(request);
SrcPaySimulation srcPaySimulation = new SrcPaySimulation();

if(iCommand == Command.BACK) {
    srcPaySimulation = (SrcPaySimulation)session.getValue("SESS_PAY_SIMULATION");
}
 
if(iCommand==Command.LIST){
  SrcFrmPaySimulation srcFrm = new SrcFrmPaySimulation(request, srcPaySimulation ); 
  srcFrm.requestEntityObject(srcPaySimulation);
  session.putValue("SESS_PAY_SIMULATION",srcPaySimulation);
  response.sendRedirect("paysimulation.jsp?command="+Command.LIST);
}

%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Payroll Simulation</title>
<script language="JavaScript">
<!--
function cmdSearch(){ 
	document.frmpaysimulation.command.value="<%=Command.LIST%>";
	document.frmpaysimulation.action="srcpaysimulation.jsp";
	document.frmpaysimulation.submit();
}

function fnTrapKD(){
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
}

function setChecked(val) {
	dml=document.frmpaysimulation;
	len = dml.elements.length;
	var i=0;
	for( i=0 ; i<len ; i++) {						
		dml.elements[i].checked = val;					
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
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Payroll Simulation &gt;  Search <!-- #EndEditable --> </strong></font> 
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
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmpaysimulation" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td valign="top" colspan="2"> 
                                            <table width="100%" border="0" cellspacing="2" cellpadding="2" >
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="8%">&nbsp;</td>
                                                <td width="89%">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="8%"> 
                                                  <div align="left">Title</div>
                                                </td>
                                                <td width="89%"> 
                                                    <input type="text" name="<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_TITLE] %>" value="<%=srcPaySimulation.getTitle() %>" class="elemenForm" onKeyDown="javascript:fnTrapKD()" size="40">
                                                </td>
												</tr>
												
												 <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="8%"> 
                                                  <div align="left">Objectives</div>
                                                </td>
                                                <td width="89%"> 
                                                  <input type="text" name="<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_OBJECTIVES] %>" value="<%=srcPaySimulation.getObjectives() %>" class="elemenForm" onKeyDown="javascript:fnTrapKD()" size="40">
                                                </td>
					      </tr>
                                               
                                              
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="8%">Sort By</td>
                                                 <td width="83%"> <%=ControlCombo.draw(SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_SORT_BY],"formElemen",null, ""+srcPaySimulation.getSortBy(),SrcPaySimulation.getSortByValue(SESS_LANGUAGE) ,SrcPaySimulation.getSortByKey(SESS_LANGUAGE), " onkeydown=\"javascript:fnTrapKD()\"") %></td>
                                              </tr>
                                              <tr> 
                                                <td width="3%">&nbsp;</td>
                                                <td width="8%"> 
                                                  <div align="left"></div>
                                                </td>
                                                <td width="89%"> 
                                                  <table border="0" cellpadding="0" cellspacing="0" width="151">
                                                    <tr> 
                                                      <td width="20%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Section"></a></td>
                                                      <td width="80%" class="command" nowrap><a href="javascript:cmdSearch()">Search Payroll Simulation</a> </td>
                                                    </tr>
                                                  </table>
                                                </td>
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
  <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" --> <!-- #EndEditable --> 
<!-- #EndTemplate --></html>
