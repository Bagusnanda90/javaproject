 
<% 
/* 
 * Page Name  		:  srcemployee.jsp
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
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = 1;//AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_SALARY, AppObjInfo.OBJ_EMP_SALARY); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    // Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
    boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
    boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    //out.print("privView=" + privView + " | privAdd=" + privAdd);
%>

<%// int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_SALARY, AppObjInfo.OBJ_EMP_SALARY); %>
<%//@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=true;//userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>

<!-- Jsp Block -->
<%
    int iCommand = FRMQueryString.requestCommand(request);
    SrcEmpSalary srcEmpSalary = new SrcEmpSalary();
    FrmSrcEmpSalary frmSrcEmpSalary = new FrmSrcEmpSalary();

    if(iCommand==Command.BACK)
    {
        frmSrcEmpSalary = new FrmSrcEmpSalary(request, srcEmpSalary);
       // frmSrcEmpSalary.requestEntityObject(srcEmpSalary);
        try{
            srcEmpSalary = (SrcEmpSalary)session.getValue(SessEmpSalary.SESS_SRC_SALARY);
            if(srcEmpSalary == null)
                srcEmpSalary = new SrcEmpSalary();
            System.out.println("ecccccc "+srcEmpSalary.getOrderBy());
        }catch (Exception e){
            System.out.println("e....."+e.toString());
            srcEmpSalary = new SrcEmpSalary();
        }
    }
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Salary</title>
<script language="JavaScript">

function cmdAdd(){
	document.frmsrcemployee.command.value="<%=Command.ADD%>";
	document.frmsrcemployee.action="empsalary_edit.jsp";
	document.frmsrcemployee.submit();
}

function cmdSearch(){
	document.frmsrcemployee.command.value="<%=Command.LIST%>";
	document.frmsrcemployee.action="empsalary_list.jsp";
	document.frmsrcemployee.submit();
}

function cmdImport(){ 
	document.frmsrcemployee.action="<%=approot%>/system/excel_up/up_salary.jsp";
	document.frmsrcemployee.submit();
}

function fnTrapKD(){
	//alert(event.keyCode);
   if (event.keyCode == 13) {   		
		document.all.aSearch.focus();
		cmdSearch();
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
<% if (privView) { %>
            document.frmsrcemployee.<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_COMMDATEFROM] + "_dy"%>.style.visibility="hidden";  
            document.frmsrcemployee.<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_COMMDATEFROM] + "_yr"%>.style.visibility="hidden";  
            document.frmsrcemployee.<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_COMMDATETO] + "_dy"%>.style.visibility="hidden";  
            document.frmsrcemployee.<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_COMMDATETO] + "_mn"%>.style.visibility="hidden";  
            document.frmsrcemployee.<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_COMMDATETO] + "_yr"%>.style.visibility="hidden";  	
            document.frmsrcemployee.<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_POSITION]%>.style.visibility="hidden";  
<% } %>
    } 

    function hideObjectForLockers(){ 
    }

    function hideObjectForCanteen(){
    }

    function hideObjectForClinic(){
    }

    function hideObjectForMasterdata(){
<% if (privView) { %>
            document.frmsrcemployee.<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_COMMDATETO] + "_dy"%>.style.visibility="hidden";  
            document.frmsrcemployee.<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_COMMDATETO] + "_yr"%>.style.visibility="hidden";  	
<% } %>
    }

    function showObjectForMenu(){  
<% if (privView) { %>
            document.frmsrcemployee.<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_COMMDATEFROM] + "_dy"%>.style.visibility="";  
            document.frmsrcemployee.<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_COMMDATEFROM] + "_yr"%>.style.visibility="";  
            document.frmsrcemployee.<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_COMMDATETO] + "_dy"%>.style.visibility="";  
            document.frmsrcemployee.<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_COMMDATETO] + "_mn"%>.style.visibility="";  
            document.frmsrcemployee.<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_COMMDATETO] + "_yr"%>.style.visibility="";  	
            document.frmsrcemployee.<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_POSITION]%>.style.visibility="";  
<% } %>
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
</SCRIPT>
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
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
                  Employee &gt; Salary &gt; Employee Salary Search<!-- #EndEditable --> 
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
                                    <% if (privView) { %>
                                    <form name="frmsrcemployee" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="21" valign="middle" colspan="2"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="5%">&nbsp;</td>
                                                <td width="95%"> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                    <tr align="left"> 
                                                      <td width="10%" valign="top" nowrap>&nbsp;</td>
                                                      <td width="90%" valign="top">&nbsp;</td>
                                                    </tr>
                                                    <tr align="left"> 
                                                      <td width="10%" valign="top" nowrap> 
                                                        <div align="left">Name</div>
                                                      </td>
                                                      <td width="90%" valign="top"> 
                                                        <input type="text" name="<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_NAME] %>"  value="<%= srcEmpSalary.getName() %>" class="elemenForm" size="50" onkeydown="javascript:fnTrapKD()">
                                                      </td>
                                                    </tr>
                                                    <tr align="left"> 
                                                      <td width="10%" valign="top" nowrap> 
                                                        <div align="left">Payroll 
                                                          Number</div>
                                                      </td>
                                                      <td width="90%" valign="top"> 
                                                        <input type="text" name="<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_EMPNUMBER] %>"  value="<%= srcEmpSalary.getEmpnumber() %>" class="elemenForm" onkeydown="javascript:fnTrapKD()">
                                                      </td>
                                                    </tr>
                                                    <tr align="left"> 
                                                      <td width="10%" valign="top" nowrap> 
                                                        <div align="left">Commencing 
                                                          Date</div>
                                                      </td>
                                                      <td width="90%" valign="top"><%=ControlDate.drawDateWithStyle(frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_COMMDATEFROM], srcEmpSalary.getCommDateFrom(), 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%> &nbsp;to&nbsp; 
                                                        <%=ControlDate.drawDateWithStyle(frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_COMMDATETO], srcEmpSalary.getCommDateTo(), 0, -50,"formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%> </td>
                                                    </tr>
                                                    <tr align="left"> 
                                                      <td width="10%" valign="top" nowrap><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                      <td width="90%" valign="top"> 
                                                        <% 
                                                            Vector dept_value = new Vector(1,1);
                                                            Vector dept_key = new Vector(1,1);        
															dept_value.add("0");
                                                            dept_key.add("select ...");                                                          
                                                            Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");                                                        
                                                            for (int i = 0; i < listDept.size(); i++) {
                                                                    Department dept = (Department) listDept.get(i);
                                                                    dept_key.add(dept.getDepartment());
                                                                    dept_value.add(String.valueOf(dept.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw(frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_DEPARTMENT],"formElemen",null, ""+srcEmpSalary.getDepartment(), dept_value, dept_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    <tr align="left"> 
                                                      <td width="10%" valign="top" nowrap> 
                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div>
                                                      </td>
                                                      <td width="90%" valign="top"> 
                                                        <% 
                                                            Vector pos_value = new Vector(1,1);
                                                            Vector pos_key = new Vector(1,1); 															                                                  
                                                            Vector listPos = PstPosition.listAll();                                                            
                                                            for (int i = 0; i < listPos.size(); i++) {
                                                                    Position pos = (Position) listPos.get(i);
                                                                    pos_key.add(pos.getPosition());
                                                                    pos_value.add(String.valueOf(pos.getOID()));
                                                            }
                                                        %>
                                                        <%= ControlCombo.draw(frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_POSITION],"formElemen","select ...", "" + srcEmpSalary.getPosition(), pos_value, pos_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    <tr align="left"> 
                                                      <td width="10%" align="right" valign="top" nowrap> 
                                                        <div align="left">Level</div>
                                                      </td>
                                                      <td width="90%" valign="top"> 
                                                        <% Vector level_value = new Vector(1,1);
                                                          Vector level_key = new Vector(1,1);
                                                          Vector listLevel = PstLevel.listAll();
                                                          for(int i = 0;i <listLevel.size();i++){
                                                                Level level = (Level)listLevel.get(i);
                                                                level_value.add(""+level.getOID());
                                                                level_key.add(level.getLevel());
                                                          }														 
                                                        %>
                                                        <%= ControlCombo.draw(frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_LEVEL],"elementForm", "select ...", ""+srcEmpSalary.getLevel(), level_value, level_key, " onkeydown=\"javascript:fnTrapKD()\"") %> </td>
                                                    </tr>
                                                    <tr align="left"> 
                                                      <td width="10%" align="right" nowrap> 
                                                        <div align="left">Current 
                                                          Total</div>
                                                      </td>
                                                      <td width="90%" valign="top"> 
                                                        <input type="text" name="<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_CURRTOTALFROM] %>"  value="<%if((srcEmpSalary.getCurrtotalfrom()-0.0f)>0.0f){%><%= srcEmpSalary.getCurrtotalfrom() %><%}%>" class="elemenForm" size="15" onkeydown="javascript:fnTrapKD()">
                                                        &nbsp;to&nbsp; 
                                                        <input type="text" name="<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_CURRTOTALTO] %>"  value="<%if((srcEmpSalary.getCurrtotalto()-0.0f)>0.0f ){%><%= srcEmpSalary.getCurrtotalto() %><%}%>" class="elemenForm" size="15" onkeydown="javascript:fnTrapKD()">
                                                      </td>
                                                    </tr>
                                                    <tr align="left"> 
                                                      <td width="10%" align="right" nowrap> 
                                                        <div align="left">New 
                                                          Total</div>
                                                      </td>
                                                      <td width="90%" valign="top"> 
                                                        <input type="text" name="<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_NEWTOTALFROM] %>"  value="<%if((srcEmpSalary.getNewtotalfrom()-0.0f)>0.0f ){%><%= srcEmpSalary.getNewtotalfrom() %><%}%>" class="elemenForm" size="15", onkeydown="javascript:fnTrapKD()">
                                                        &nbsp;to&nbsp; 
                                                        <input type="text" name="<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_NEWTOTALTO] %>"  value="<%if((srcEmpSalary.getNewtotalto()-0.0f)>0.0f ){%><%= srcEmpSalary.getNewtotalto() %><%}%>" class="elemenForm" size="15", onkeydown="javascript:fnTrapKD()">
                                                      </td>
                                                    </tr>
                                                    <tr align="left"> 
                                                      <td width="10%" valign="top" nowrap> 
                                                        <div align="left">Sort 
                                                          By</div>
                                                      </td>
                                                      <td width="90%" valign="top"> 
                                                        <%= ControlCombo.draw(frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_ORDER],"formElemen",null, ""+srcEmpSalary.getOrderBy(), FrmSrcEmpSalary.getOrderValue(), FrmSrcEmpSalary.getOrderKey(), " onkeydown=\"javascript:fnTrapKD()\"") %></td>
                                                    </tr>
                                                    <tr align="left"> 
                                                      <td width="10%" valign="top" nowrap> 
                                                        <div align="left"></div>
                                                      </td>
                                                      <td width="90%" valign="top">&nbsp;</td>
                                                    </tr>
                                                    <tr align="left"> 
                                                      <td width="10%" height="30" valign="top" nowrap> 
                                                        <div align="left" valign="top"> 
                                                        </div>
                                                      </td>
                                                      <td valign="top" width="90%"> 
                                                        <table border="0" cellspacing="0" cellpadding="0" width="30">
                                                          <tr> 
                                                            <td width="11%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee Salary"></a></td>
                                                            <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                            <td width="11%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                              for Employee Salary</a></td>
                                                            <% if(privAdd){%>
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="12" height="4"></td>
                                                            <td width="11%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Employee Salary"></a></td>
                                                            <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                            <td width="11%" class="command" nowrap><a href="javascript:cmdAdd()">Add 
                                                              New Employee Salary</a></td>
                                                            <td width="4%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <!--td width="11%"><a href="javascript:cmdImport()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Schedule"></a></td>
                                                            <td width="4%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            <td width="11%" class="command" nowrap><a href="javascript:cmdImport()">Import from Excel</a></td-->
                                                            <%}else{%>
                                                            <td width="76%">&nbsp;</td>
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
                                    <% } 
                                   else
                                   {
                                %>
                                    <div align="center">You do not have sufficient 
                                      privilege to access this page.</div>
                                    <% } %>
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
<% if (privView) { %>
<script language="JavaScript">
	document.frmsrcemployee.<%=frmSrcEmpSalary.fieldNames[FrmSrcEmpSalary.FRM_FIELD_NAME] %>.focus();
</script>
<% } %>
<!-- #EndEditable --> 
<!-- #EndTemplate --></html>
