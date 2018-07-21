<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.canteen.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.canteen.*" %>

<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_PRESENCE   	); %>
<%@ include file = "../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>
<!-- Jsp Block -->

<%
int iCommand = FRMQueryString.requestCommand(request);
SrcCanteenVisitation srcCanteenVisitation = new SrcCanteenVisitation();
FrmSrcCanteenVisitation frmSrcCanteenVisitation = new FrmSrcCanteenVisitation();

if(iCommand==Command.BACK)
{        
	frmSrcCanteenVisitation = new FrmSrcCanteenVisitation(request, srcCanteenVisitation);

	try
	{			
		srcCanteenVisitation = (SrcCanteenVisitation) session.getValue(SessCanteenVisitation.SESS_SRC_CANTEEN_VISITATION);			
		if(srcCanteenVisitation == null)
		{
			srcCanteenVisitation = new SrcCanteenVisitation();
		}	
	}
	catch (Exception e)
	{
		srcCanteenVisitation = new SrcCanteenVisitation();
	}
}	
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Manual Input Canteen Visitation</title>
<script language="JavaScript">
<!--
function cmdAdd(){
	document.frmsrcpresence.command.value="<%=Command.ADD%>";
	document.frmsrcpresence.action="canteenvisitation_edit.jsp";
	document.frmsrcpresence.submit();
}

function cmdSearch(){
	document.frmsrcpresence.command.value="<%=Command.LIST%>";
	document.frmsrcpresence.action="canteenvisitation_list.jsp";
	document.frmsrcpresence.submit();
}

function fnTrapKD(){
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
}

function MM_swapImgRestore() 
{ //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() 
{ //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) 
{ //v4.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() 
{ //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-------------- script control line -------------------
//-->
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
      <!-- #EndEditable --> 
    </td>
  </tr> 
  <tr> 
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../main/mnmain.jsp" %>
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
                  Employee &gt; Attendance &gt; Manual Canteen Visitation<!-- #EndEditable --> 
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
                                    <form name="frmsrcpresence" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
									  <table width="100%" border="0" cellspacing="0" cellpadding="0">
										  <tr>
											<td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td> 
                                                  <table border="0" cellspacing="2" cellpadding="2" width="100%" >
                                                    <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left">Payroll 
                                                          Number</div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <input type="text" name="<%=frmSrcCanteenVisitation.fieldNames[FrmSrcCanteenVisitation.FRM_FIELD_EMPNUMBER] %>"  value="<%= srcCanteenVisitation.getEmpnumber() %>" class="elemenForm" onkeydown="javascript:fnTrapKD()">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left">Full 
                                                          Name</div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <input type="text" name="<%=frmSrcCanteenVisitation.fieldNames[FrmSrcCanteenVisitation.FRM_FIELD_FULLNAME] %>"  value="<%= srcCanteenVisitation.getFullname() %>" class="elemenForm" onkeydown="javascript:fnTrapKD()" size="35">
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <%
                                                            Vector department_value = new Vector(1,1);
                                                            Vector department_key = new Vector(1,1);
                                                            department_value.add("0");
                                                            department_key.add("select ...");                                                          
                                                            Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");                                                        
                                                            String selectValueDepartment = (String) srcCanteenVisitation.getDepartment();
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                    Department dept = (Department) listDept.get(i);
                                                                    department_key.add(dept.getDepartment());
                                                                    department_value.add(String.valueOf(dept.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw(frmSrcCanteenVisitation.fieldNames[FrmSrcCanteenVisitation.FRM_FIELD_DEPARTMENT],"elementForm", null, selectValueDepartment, department_value, department_key, "onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <%
                                                            Vector section_value = new Vector(1,1);
                                                            Vector section_key = new Vector(1,1);
                                                            section_value.add("0");
                                                            section_key.add("select ...");                                                              
                                                            //Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");                                                          
															Vector listSec = PstSection.list(0, 0, "", " SECTION ");                                                          
                                                            String selectValueSection = (String)srcCanteenVisitation.getSection();
                                                            for (int i = 0; i < listSec.size(); i++) {
                                                                    Section sec = (Section) listSec.get(i);
                                                                    section_key.add(sec.getSection());
                                                                    section_value.add(String.valueOf(sec.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw(frmSrcCanteenVisitation.fieldNames[FrmSrcCanteenVisitation.FRM_FIELD_SECTION],"elementForm", null, selectValueSection, section_value, section_key,"onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div>
                                                      </td>
                                                      <td width="2%">:</td>
                                                      <td width="88%"> 
                                                        <%
                                                            Vector position_value = new Vector(1,1);
                                                            Vector position_key = new Vector(1,1);
                                                            position_value.add("0");
                                                            position_key.add("select ...");                                                       
                                                            Vector listPos = PstPosition.list(0, 0, "", " POSITION ");                                                            
                                                            String selectValuePosition = (String)srcCanteenVisitation.getPosition();
                                                            for (int i = 0; i < listPos.size(); i++) {
                                                                    Position pos = (Position) listPos.get(i);
                                                                    position_key.add(pos.getPosition());
                                                                    position_value.add(String.valueOf(pos.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw(frmSrcCanteenVisitation.fieldNames[FrmSrcCanteenVisitation.FRM_FIELD_POSITION],"elementForm", null, selectValuePosition, position_value, position_key,"onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td height="21" valign="top" width="10%">Period</td>
                                                      <td height="21" width="2%">:</td>
                                                      <td height="21" width="88%"> 
                                                        <input type="checkbox" name="<%=frmSrcCanteenVisitation.fieldNames[FrmSrcCanteenVisitation.FRM_FIELD_PERIOD_CHECKED]%>" <%=(srcCanteenVisitation.isPeriodCheck() ? "checked" : "")%> value="1">
                                                        <i><font color="#FF0000">select 
                                                        all period</font></i> 
                                                      </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                      <td height="21" valign="top" width="10%">&nbsp;</td>
                                                      <td height="21" width="2%">&nbsp;</td>
                                                      <td height="21" width="88%"> 
                                                        <table width="100%" border="0">
                                                          <tr> 
                                                            <td width="5%">From</td>
															<%	
															Date selectedDateFrom = srcCanteenVisitation.getDatefrom()!=null ? srcCanteenVisitation.getDatefrom() : new Date();
															%>
                                                            <td width="95%"><%=ControlDate.drawDate(frmSrcCanteenVisitation.fieldNames[FrmSrcCanteenVisitation.FRM_FIELD_DATEFROM], selectedDateFrom, 0, installInterval) %></td>
                                                          </tr>
                                                          <tr> 
                                                            <td width="5%">To</td>
															<%
															Date selectedDateTo = srcCanteenVisitation.getDateto()!=null ? srcCanteenVisitation.getDateto() : new Date();
															%>															
                                                            <td width="95%"><%=ControlDate.drawDate(frmSrcCanteenVisitation.fieldNames[FrmSrcCanteenVisitation.FRM_FIELD_DATETO], selectedDateTo, 0, installInterval) %></td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td nowrap width="10%">  
                                                        <div align="left"></div>
                                                      </td>
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="88%">&nbsp;</td>
                                                    </tr>
                                                    <tr> 
                                                      <td nowrap width="10%"> 
                                                        <div align="left"></div>
                                                      </td>
                                                      <td nowrap width="2%">&nbsp;</td>
                                                      <td nowrap width="88%"> 
                                                        <table border="0" cellpadding="0" cellspacing="0" width="382">
                                                          <tr> 
                                                            <td width="6%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search for Canteen Visitation"></a></td>
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td width="39%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                              for Visitation</a></td>
                                                            <% if(privAdd){%>
                                                            <td width="5%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td width="6%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Canteen Visitation"></a></td>
                                                            <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td width="40%" class="command" nowrap><a href="javascript:cmdAdd()">Add 
                                                              New Visitation</a></td>
                                                            <%}else{%>
                                                            <%}%>
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
      <%@ include file = "../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
