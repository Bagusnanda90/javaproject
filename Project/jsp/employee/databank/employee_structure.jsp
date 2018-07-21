<%--
    Document   : company_structure
    Created on : Feb 20, 2012, 3:17:51 PM
    Author     : Gede115
--%>

<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>

<%
            long oidCompany = FRMQueryString.requestLong(request, "COMPANY_ID");

%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Search Employee</title>
        <%long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");%>
        <script language="JavaScript">

            function cmdUpdateDiv(){
                document.frmstructure.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmstructure.action="srcEmployee_structure.jsp";
                document.frmstructure.submit();
            }
            function cmdUpdateDep(){
                document.frmstructure.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmstructure.action="srcEmployee_structure.jsp";
                document.frmstructure.submit();
            }
            function cmdUpdatePos(){
                document.frmstructure.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmstructure.action="srcEmployee_structure.jsp";
                document.frmstructure.submit();
            }


            function cmdSearch()        {
                document.frmstructure.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmstructure.action="employee_structure.jsp";
                document.frmstructure.submit();
            }

            function cmdBack(){
		document.frmstructure.command.value="<%=Command.BACK%>";
		document.frmstructure.action="srcEmployee_structure.jsp";
		document.frmstructure.submit();
            }

            function cmdRefresh(){
		document.frmstructure.command.value="<%=Command.BACK%>";
		document.frmstructure.action="employee_structure.jsp";
		document.frmstructure.submit();
            }

           function cmdEdit(oid){
               var newWin= window.open("employee_edit.jsp?employee_oid="+oid+"&command=<%=Command.EDIT%>"+"",
                            "CompanyStructure", "height=550,width=1500, status=yes,toolbar=no,menubar=yes,location=no, scrollbars=yes");
               newWin.focus();             
            }


            function cmdEditX(oid){
		document.frmstructure.employee_oid.value=oid;
		document.frmstructure.command.value="<%=Command.EDIT%>";
		document.frmstructure.prev_command.value="<%=Command.EDIT%>";
		document.frmstructure.action="employee_edit.jsp";
		document.frmstructure.submit();
	}


            function fnTrapKD(){
                if (event.keyCode == 13) {
                    document.all.aSearch.focus();
                    cmdSearch();
                }
            }

            function MM_swapImgRestore() { //v3.0
                var i,x,a=document.MM_sr; for(i=0;a && i < a.length && (x=a[i]) && x.oSrc;i++) x.src=x.oSrc;
            }

            function MM_preloadImages() { //v3.0
                var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i < a.length; i++)
                        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
                }

                function MM_findObj(n, d) { //v4.0
                    var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0 && parent.frames.length) {
                        d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                    if(!(x=d[n]) && d.all) x=d.all[n]; for (i=0;!x && i < d.forms.length;i++) x=d.forms[i][n];
                    for(i=0;!x && d.layers && i < d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
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
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" -->
        <!-- #EndEditable -->
    </head>

    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
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
            <%}%>
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
                                                    Employee &gt; Company Structure<!-- #EndEditable -->
                                                </strong></font>
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
                                                                    <table style="border:1px solid <%=garisContent%>"  width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <form name="frmstructure" method="post" action="">
                                                                                    <input type="hidden" name="command" value="">
                                                                                    <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">
                                                                                    <input type="hidden" name="COMPANY_ID" value="<%=oidCompany%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top">
                                                                                            <td valign="middle" colspan="2">
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                                                                                    <tr>
                                                                                                        <td width="3%">&nbsp;</td>
                                                                                                        <td width="97%">&nbsp;</td>
                                                                                                        <td width="0%">&nbsp;</td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td width="3%">&nbsp;</td>
                                                                                                        <td width="97%">
                                                                                                            <table border="0" cellspacing="2" cellpadding="2">
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <%
                                                                                                                                    //Untuk division
                                                                                                                                    String whereClause = "d." + PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" + oidCompany
                                                                                                                                            + " AND pos." + PstPosition.fieldNames[PstPosition.FLD_HEAD_TITLE] + "=" + PstPosition.HEAD_TITLE_DIVISION;

                                                                                                                                    Vector listDiv = PstDivision.list2(0, 0, whereClause, " DIVISION ");
                                                                                                                        %>
                                                                                                                        <table>
                                                                                                                        <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                                                                                        <td width="24"><a href="javascript:cmdBack()" class="command" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt=""></a></td>
                                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                                                                        <td nowrap><a href="javascript:cmdBack()" class="command">Back To Search Structure</a></td>
                                                                                                                        <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                                                                                        <td width="24"><a href="javascript:cmdRefresh" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1003','','<%=approot%>/images/refresh1.jpg',1)"><img name="Image1003" border="0" src="<%=approot%>/images/refresh2.jpg" width="24" height="24" alt=""></a></td>
                                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                                                                        <td nowrap><a href="javascript:cmdRefresh()" class="command">Refresh</a></td>
                                                                                                                        </table>
                                                                                                                        <table border="1" cellpadding="2" cellspacing="15">
                                                                                                                            <tr>
                                                                                                                                <td nowrap style="background-color: blue"><div><b><font color="#FFFFFF"></font></b></div></td>
                                                                                                                                <td colspan="<%=listDiv.size()%>"><div align="center" style="font-size: 16px; background-color: blue"><b><font color="#FFFFFF"><%=PstCompany.getCompanyName(oidCompany)%></font></b></div></td>
                                                                                                                            </tr>
                                                                                                                            <%//jika ada board of director{
                                                                                                                            String whereClauseEmp = " pos." + PstPosition.fieldNames[PstPosition.FLD_HEAD_TITLE] + "=" + PstPosition.HEAD_TITLE_BOARD
                                                                                                                                    +" AND emp."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN;
                                                                                                                            Vector listEmp = PstEmployee.list2(0, 0, whereClauseEmp, "");
                                                                                                                            if(listEmp.size()>0){
                                                                                                                            %>
                                                                                                                            <tr>
                                                                                                                                <td style="background-color: blue"><div><b><font color="#FFFFFF">Board Of Company</font></b></td>
                                                                                                                                                <td align="center" style="font-weight: bold" colspan="<%=listDiv.size()%>"><table><%for(int d=0;d<listEmp.size();d++){
                                                                                                                                Employee emp = (Employee) listEmp.get(d);
                                                                                                                                                %><td>
                                                                                                                                                    <table border="1" cellpadding="5"><tr>
                                                                                                                                                            
                                                                                                                                                            <td align="center"><img width="90" src="<%=approot%>/imgcache/<%=emp.getEmployeeNum()%>.JPEG" /></td></tr>
                                                                                                                                                        
                                                                                                                                                        <tr>
                                                                                                                              <td align="center"><a href="javascript:cmdEdit('<%=emp.getOID()%>')"><%=emp.getFullName()%></a></td></tr>
                                                                                                                                                        <tr><td align="center"><%=PstPosition.getPositionName(""+emp.getPositionId())%></td></tr>
                                                                                                                                                    </table></td><%}%></table></td>
                                                                                                                            </tr>
                                                                                                                            <% }//}%>
                                                                                                                            <tr>
                                                                                                                                <td style="background-color: blue"><div><b><font color="#FFFFFF">Head Of Company</font></b></td>
                                                                                                                                <%
                                                                                                                                //Untuk Head
                                                                                                                                                whereClauseEmp = " pos." + PstPosition.fieldNames[PstPosition.FLD_HEAD_TITLE] + "=" + PstPosition.HEAD_TITLE_COMPANY
                                                                                                                                                        +" AND emp."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN;

                                                                                                                                                listEmp = PstEmployee.list2(0, 0, whereClauseEmp, "");%>
                                                                                                                                                <td align="center" style="font-weight: bold" colspan="<%=listDiv.size()%>">
                                                                                                                                                    <table>
                                                                                                                                                    <%
                                                                                                                              for(int com=0;com<listEmp.size();com++){
                                                                                                                              Employee emp = (Employee) listEmp.get(com);
                                                                                                                              String posId = String.valueOf(emp.getPositionId());
                                                                                                                               %>
                                                                                                                               <td>
                                                                                                                                                    <table border="1" cellpadding="5"><tr>
                                                                                                                              <td align="center"><img width="90" src="<%=approot%>/imgcache/<%=emp.getEmployeeNum()%>.JPEG" /></td></tr>
                                                                                                                                                        <tr>
                                                                                                                              <td align="center"><a href="javascript:cmdEdit('<%=emp.getOID()%>')"><%=emp.getFullName()%></a></td></tr>
                                                                                                                              <tr><td align="center"><%=PstPosition.getPositionName(posId)%></td></tr></table></td><%}%></table></td>
                                                                                                                            </tr>
         <%
            int divSize = listDiv.size();
            int MAX_DIV = divSize <= 5 ? divSize : 5 ;
            int loopDiv = divSize / MAX_DIV;
            loopDiv = loopDiv ==0 ? 1 :  loopDiv ;
            int startLoop =0;
            for(int iLoop =0 ; iLoop < loopDiv ;iLoop++){
                
            %>
                                <tr>
                                    <td style="background-color: blue"><div><b><font color="#FFFFFF">Head Of Division</font></b></div></td>
                                    <%
                                                for (int i = startLoop ; i < (startLoop + MAX_DIV); i++) {
                                                    Division div = (i<divSize) ? (Division) listDiv.get(i) : new Division();
                                                    //Untuk Employee
                                                    whereClauseEmp = "emp." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID] + "=" + div.getOID()
                                                            + " AND pos." + PstPosition.fieldNames[PstPosition.FLD_HEAD_TITLE] + "=" + PstPosition.HEAD_TITLE_DIVISION
                                                            +" AND emp."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN;

                                                    listEmp = PstEmployee.list2(0, 0, whereClauseEmp, "");

                                    %>

                                    <td valign="top" align="center">
                                        <table border="1" cellpadding="0" align="center" style="line-height: 5">
                                            <tr>
                                                <td nowrap <%if(listEmp.size()>1){%>colspan="<%=listEmp.size()%>"<%}%> align="center" bgcolor="yellow" style="font-weight: bold"><%=div.getDivision()%></td>
                                            </tr>
                                            <tr><%if (listEmp.size() == 0) {%>
                                                <td align="center">-</td>
                                                <%}%>
                                                <%for (int b = 0; b < listEmp.size(); b++) {
                                                  Employee emp = (Employee) listEmp.get(b);%>
                                                <td nowrap align="center"><img width="90" src="<%=approot%>/imgcache/<%=emp.getEmployeeNum()%>.JPEG" /><br>
                                                    <a href="javascript:cmdEdit('<%=emp.getOID()%>')"><%=emp.getFullName()%></a></td><%}%>

                                            </tr>
                                            <tr>
                                                <%if (listEmp.size() == 0) {%>
                                                <td align="center">-</td>
                                                <%}%>
                                                <%for (int b = 0; b < listEmp.size(); b++) {
                                                  Employee emp = (Employee) listEmp.get(b);
                                                  String posId = String.valueOf(emp.getPositionId());
                                                %>
                                                <td align="center"><%=PstPosition.getPositionName(posId)%></td>
                                                <%}%>
                                            </tr>
                                        </table>
                                    </td>
                                    <% }%>

                                </tr>
                                <tr>
                                <td style="background-color: blue"><div><b><font color="#FFFFFF">Head Of Department / Section</font></b></div></td>
                                        <% for (int i = startLoop ; i < (startLoop + MAX_DIV); i++) {
                                                        Division div =  (i<divSize) ? (Division) listDiv.get(i) : new Division();
                                        %>
                                <%
                                     //untuk department
                                     String whereClauseDep = "dep." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + div.getOID()
                                             + " AND pos." + PstPosition.fieldNames[PstPosition.FLD_HEAD_TITLE] + "=" + PstPosition.HEAD_TITLE_DEPARTMENT;

                                     Vector listDep = PstDepartment.list2(0, 0, whereClauseDep, " DEPARTMENT ");
                                %>
                                <td valign="top" align="center">
                                    <%if (listDep.size() == 0){
                                    %><table><tr>---</tr></table><%}%>

                                    <% for (int a = 0; a < listDep.size(); a++) {
                                            Department dep = (Department) listDep.get(a);
                                            //untuk employee dep
                                            whereClauseEmp = "emp." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + "=" + dep.getOID()
                                                    + " AND pos." + PstPosition.fieldNames[PstPosition.FLD_HEAD_TITLE] + "=" + PstPosition.HEAD_TITLE_DEPARTMENT
                                                    +" AND emp."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN;

                                            Vector listEmpDep = PstEmployee.list2(0, 0, whereClauseEmp, "");
                                    %>
                                    <table border="1" cellpadding="2" style="margin: 8px 5px 4px 5px">
                                                                <%if (listEmpDep.size() == 1) {
                                                                    String whereClauseSec = "sec." + PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + dep.getOID()
                                                                     + " AND pos." + PstPosition.fieldNames[PstPosition.FLD_HEAD_TITLE] + "=" + PstPosition.HEAD_TITLE_SECTION;
                                                             Vector listSec = PstSection.list2(0, 0, whereClauseSec, " SECTION ");%>
                                                                <tr border="1">
                                                                    <td colspan="<%=listSec.size()%>" nowrap align="center" bgcolor="yellow" style="font-weight: bold"><%=dep.getDepartment()%></td>
                                                                </tr>
                                                                <tr border="1">
                                                                    <%for (int b = 0; b < listEmpDep.size(); b++) {
                                                                        Employee emp = (Employee) listEmpDep.get(b);%>
                                                                    <td colspan="<%=listSec.size()%>" align="center"> <img width="90" src="<%=approot%>/imgcache/<%=emp.getEmployeeNum()%>.JPEG" /><br>
                                                                        <a href="javascript:cmdEdit('<%=emp.getOID()%>')"><%=emp.getFullName()%></a></td>
                                                                    <%}%>
                                                                </tr>
                                                                <tr border="1">
                                                                    <%for (int b = 0; b < listEmpDep.size(); b++) {
                                                                            Employee emp = (Employee) listEmpDep.get(b);
                                                                            String posId = String.valueOf(emp.getPositionId());
                                                                    %>
                                                                    <td colspan="<%=listSec.size()%>" align="center"><%=PstPosition.getPositionName(posId)%></td>
                                                                    <%}%>
                                                                </tr>
                                                                 <%//untuk section
                                                             if(listSec.size()>0){%>
                                                             <% for (int s = 0; s < listSec.size(); s=listSec.size()) {
                                                                    Section sec = (Section) listSec.get(s);
                                                                    //untuk employee sec
                                                                    whereClauseEmp = "emp." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID] + "=" + sec.getOID()
                                                                            + " AND pos." + PstPosition.fieldNames[PstPosition.FLD_HEAD_TITLE] + "=" + PstPosition.HEAD_TITLE_SECTION
                                                                            +" AND emp."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]+"="+PstEmployee.NO_RESIGN;

                                                                    listEmp = PstEmployee.list2(0, 0, whereClauseEmp, "");
                                                            %>
                                                                <tr border="1">
                                                                    <td colspan="<%=listEmp.size()%>" align="center" bgcolor="blue" style="font-weight: bold">Head Of <%=sec.getSection()%></td>
                                                                </tr>
                                                                <tr border="1">
                                                                    <%for (int b = 0; b < listEmp.size(); b++) {
                                                                            Employee emp = (Employee) listEmp.get(b);
                                                                    %>
                                                                    <td nowrap align="center">
                                                                        <a href="javascript:cmdEdit('<%=emp.getOID()%>')"><%=emp.getFullName()%></a></td>
                                                                    <%}%>
                                                                </tr>
                                                                <tr border="1">
                                                                    <%for (int b = 0; b < listEmp.size(); b++) {
                                                                            Employee emp = (Employee) listEmp.get(b);
                                                                            String posId = String.valueOf(emp.getPositionId());
                                                                    %>
                                                                    <td align="center"><%=PstPosition.getPositionName(posId)%></td>
                                                                    <%}%>
                                                                </tr>
                                                                <%}
                                                             }
                                                                }%>
                                                              
                                                            </table>
                                                            <% }
                                            %>
                                                        </td>
                                                        <% }%>
                                                    </tr>
 <% startLoop =  startLoop + MAX_DIV; }%>
                                                </table></td>
                                                                                                                    <td width="83%"></tr>

                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%"> <div align="left"></div></td>
                                                                                                                    <td width="83%"> <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                                                                                            <tr>
                                                                                                                                <td width="20%"></td>
                                                                                                                                <td width="2%"></td>
                                                                                                                                <td width="28%" class="command" nowrap></td>
                                                                                                                                <td width="2%"></td>

                                                                                                                            </tr>
                                                                                                                        </table></td>
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
    <!-- #BeginEditable "script" -->
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>