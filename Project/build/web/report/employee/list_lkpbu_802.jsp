<%-- 
    Document   : list_lkpbu_802
    Created on : Aug 12, 2015, 11:56:42 AM
    Author     : khirayinnura
--%>
<%@page import="com.dimata.harisma.entity.report.lkpbu.PstLkpbu"%>
<%@page import="com.dimata.harisma.form.employee.FrmEmployee"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
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
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%!
public String drawList(Vector listEmployee) 
{ 
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        
        ctrlist.addHeader("No.","2%", "0", "0");               
        ctrlist.addHeader("NIP","18%", "0", "0");
        ctrlist.addHeader("Nama Perusahaan","25%", "0", "0");
        ctrlist.addHeader("Tanggal Mulai","15%", "0", "0");
        ctrlist.addHeader("Tanggal berakhir","15%", "0", "0");	
        ctrlist.addHeader("Nama jabatan atau posisi","25%", "0", "0");

        ctrlist.setLinkRow(-1);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        Vector rowx = new Vector(1,1);
        for(int i = 0; i < listEmployee.size(); i++) {
            Employee employee = (Employee)listEmployee.get(i);
            int no = i+1;
            rowx = new Vector(1,1);
            rowx.add(""+no);
            rowx.add("0000000000000000"+employee.getEmployeeNum());
            rowx.add(""+employee.getCompanyName());
            rowx.add(Formater.formatDate(employee.getCommencingDate(),"ddMMyyyy"));
            rowx.add(Formater.formatDate(employee.getResignedDate(),"ddMMyyyy"));
            rowx.add(employee.getPositionName()+" "+employee.getDepartmentName());
                        
            lstData.add(rowx);
            lstLinkData.add("0");
        }
     
        return ctrlist.drawList();	
}
%>

<%
    int iCommand = FRMQueryString.requestCommand(request);
    Employee employee = new Employee();
    FrmEmployee frmEmployee = new FrmEmployee(employee);
    String nameSearch = FRMQueryString.requestString(request, frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_FULL_NAME]);
    String payrollSearch = FRMQueryString.requestString(request, frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMPLOYEE_NUM]);
    long oidCompany = FRMQueryString.requestLong(request, "company");
    long oidDivisi = FRMQueryString.requestLong(request, "division");
    long oidDepartment = FRMQueryString.requestLong(request, "department");
    long oidLevel = FRMQueryString.requestLong(request, "level");

    Vector vListResult = new Vector(1, 1);
    
    vListResult = PstEmployee.listEmployeCompany(nameSearch, payrollSearch, oidCompany, oidDivisi, oidDepartment, oidLevel);
    
    session.putValue("listresult", vListResult);
    
    Vector listKadiv= new Vector(1,1);
    //get value kadiv HRD
    String kadivPositionOid = PstSystemProperty.getValueByName("HR_DIR_POS_ID");
    String whereClauseOidPosition = "POSITION_ID='"+kadivPositionOid+"'";

    listKadiv = PstLkpbu.listPosition(whereClauseOidPosition);
    session.putValue("listkadiv", listKadiv);

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HARISMA - LKPBU 802 Report</title>
        <script language="JavaScript">
            <%if (iCommand == Command.PRINT) {%>
                //com.dimata.harisma.report.listRequest	
                window.open("<%=printroot%>.report.listRequest.ListEmpEducationPdf");
            <%}%>

                function cmdAdd(){
                    document.frmemplkpbu802.command.value="<%=Command.ADD%>";
                    document.frmemplkpbu802.action="list_lkpbu_802.jsp";
                    document.frmemplkpbu802.submit();
                }

                function reportPdf(){
                    document.frmemplkpbu802.command.value="<%=Command.PRINT%>";
                    document.frmemplkpbu802.action="list_lkpbu_802.jsp";
                    document.frmemplkpbu802.submit();
                }

                function cmdSearch(){
                    document.frmemplkpbu802.command.value="<%=Command.LIST%>";
                    document.frmemplkpbu802.action="list_lkpbu_802.jsp";
                    document.frmemplkpbu802.submit();
                }

                function cmdSpecialQuery(){
                    document.frmemplkpbu802.action="specialquery.jsp";
                    document.frmemplkpbu802.submit();
                }

                function fnTrapKD(){
                    if (event.keyCode == 13) {
                        document.all.aSearch.focus();
                        cmdSearch();
                    }
                }
                function cmdExportExcel(){
                 
                    var linkPage = "<%=approot%>/report/employee/export_excel/export_excel_list_lkpbu_802.jsp";    
                    var newWin = window.open(linkPage,"attdReportDaily","height=700,width=990,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes"); 			
                     newWin.focus();
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

                    function cmdUpdateLevp(){
                        document.frmemplkpbu802.command.value="<%=Command.ADD%>";
                        document.frmemplkpbu802.action="list_lkpbu_802.jsp"; 
                        document.frmemplkpbu802.submit();
                    }
        </script>
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" --> 
        <!-- #EndEditable -->
    </head>
    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
            <%@include file="../../styletemplate/template_header.jsp" %>
            <%} else {%>
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
                                                Report &gt;Employee &gt; LKPBU Form 802 <!-- #EndEditable --> 
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
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr> 
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" --> 
                                                                                <form name="frmemplkpbu802" method="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
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
                                                                                                        <td width="97%"> <table border="0" cellspacing="2" cellpadding="2" width="74%">
                                                                                                                <tr align="left" valign="top"> 
                                                                                                                    <td width="17%" height="21" nowrap>Employee Name</td>
                                                                                                                    <td width="83%"> 
                                                                                                                        <input type="text" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_FULL_NAME] %>"  value="" class="elemenForm" size="40" onkeydown="javascript:fnTrapKD()">
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top"> 
                                                                                                                    <td width="17%" height="21" nowrap>Payroll Number</td>
                                                                                                                    <td width="83%"> 
                                                                                                                        <input type="text" name="<%=frmEmployee.fieldNames[FrmEmployee.FRM_FIELD_EMPLOYEE_NUM] %>"  value="" class="elemenForm" onkeydown="javascript:fnTrapKD()">
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top"> 
                                                                                                                    <td width="17%" height="21" nowrap>Company</td>
                                                                                                                    <td width="83%"> 
                                                                                                                        <%
                                                                                                                        Vector compValue = new Vector(1, 1);
                                                                                                                        Vector compKey = new Vector(1, 1);
                                                                                                                        compValue.add("0");
                                                                                                                        compKey.add("All Company...");
                                                                                                                        Vector listComp = PstCompany.list(0, 0, "", "COMPANY");
                                                                                                                        for (int c = 0; c < listComp.size(); c++) {
                                                                                                                            Company company = (Company) listComp.get(c);
                                                                                                                            compValue.add("" + company.getOID());
                                                                                                                            compKey.add(company.getCompany());
                                                                                                                        }
                                                                                                                        %> <%=ControlCombo.draw("company", null, "" + oidCompany, compValue, compKey, "")%>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top"> 
                                                                                                                    <td width="17%" height="21" nowrap>Division</td>
                                                                                                                    <td width="83%"> <%
                                                                                                                        Vector divValue = new Vector(1, 1);
                                                                                                                        Vector divtKey = new Vector(1, 1);
                                                                                                                        divValue.add("0");
                                                                                                                        divtKey.add("All Divisi...");
                                                                                                                        Vector listDivisi = PstDivision.list(0, 0, "", "DIVISION");
                                                                                                                        for (int d = 0; d < listDivisi.size(); d++) {
                                                                                                                            Division division = (Division) listDivisi.get(d);
                                                                                                                            divValue.add("" + division.getOID());
                                                                                                                            divtKey.add(division.getDivision());
                                                                                                                        }
                                                                                                                        %> <%=ControlCombo.draw("division", null, "" + oidDivisi, divValue, divtKey, "")%> </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top"> 
                                                                                                                    <td width="17%" height="21" nowrap>Departement</td>
                                                                                                                    <td width="83%"> <%
                                                                                                                        Vector deptValue = new Vector(1, 1);
                                                                                                                        Vector deptKey = new Vector(1, 1);
                                                                                                                        deptValue.add("0");
                                                                                                                        deptKey.add("All Department...");
                                                                                                                        Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                                        for (int e = 0; e < listDept.size(); e++) {
                                                                                                                            Department department = (Department) listDept.get(e);
                                                                                                                            deptValue.add("" + department.getOID());
                                                                                                                            deptKey.add(department.getDepartment());
                                                                                                                        }
                                                                                                                        %> <%=ControlCombo.draw("department", null, "" + oidDepartment, deptValue, deptKey, "")%> </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top"> 
                                                                                                                    <td width="17%" height="21" nowrap>Level</td>
                                                                                                                    <td width="83%"> <%
                                                                                                                        Vector lvlValue = new Vector(1, 1);
                                                                                                                        Vector lvlKey = new Vector(1, 1);
                                                                                                                        lvlValue.add("0");
                                                                                                                        lvlKey.add("All Level...");
                                                                                                                        Vector listLevel = PstLevel.list(0, 0, "", "LEVEL");
                                                                                                                        for (int l = 0; l < listLevel.size(); l++) {
                                                                                                                            Level level = (Level) listLevel.get(l);
                                                                                                                            lvlValue.add("" + level.getOID());
                                                                                                                            lvlKey.add(level.getLevel());
                                                                                                                        }
                                                                                                                        %> <%=ControlCombo.draw("level", null, "" + oidLevel, lvlValue, lvlKey, "")%> </td>
                                                                                                                </tr>

                                                                                                                <tr align="left" valign="top"> 
                                                                                                                    <td width="17%"> <div align="left"></div></td>
                                                                                                                    <td width="83%"> <table width="30%" border="0" cellspacing="0" cellpadding="0">
                                                                                                                            <tr> 
                                                                                                                                <td width="20%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                                                                                                <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                                                                                                <td width="28%" class="command" nowrap><a href="javascript:cmdSearch()">Search 
                                                                                                                                        for Employee</a></td>
                                                                                                                                        <%
                                                                                                                                            if (privAdd) {
                                                                                                                                        %>
                                                                                                                                        <%                                                            } else {
                                                                                                                                        %>
                                                                                                                                <td width="2%">&nbsp;</td>
                                                                                                                                <%                                                                }
                                                                                                                                %>
                                                                                                                            </tr>
                                                                                                                        </table></td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top"> 
                                                                                                                    <td>&nbsp;</td>
                                                                                                                    <td>&nbsp;</td>
                                                                                                                </tr>
                                                                                                            </table></td>
                                                                                                    </tr>
                                                                                                    <tr> 
                                                                                                        <td colspan="2">&nbsp;</td>
                                                                                                    </tr>
                                                                                                    <%if (iCommand == Command.LIST || iCommand == Command.PRINT) {%>
                                                                                                    <tr> 
                                                                                                        <td colspan="2"></td>
                                                                                                    </tr>
                                                                                                    <tr> 
                                                                                                        <td colspan="2"></td>
                                                                                                    </tr>
                                                                                                    <tr> 
                                                                                                        <td height="29" colspan="2">
                                                                                                            <%=drawList(vListResult)%>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <tr> 
                                                                                                        <td height="43" colspan="2"></td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td height="43" colspan="2">
                                                                                                            <table width="27%" border="0" cellspacing="1" cellpadding="1">
                                                                                                                <tr>
                                                                                                                    <td width="17%"><a href="javascript:cmdExportExcel()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
                                                                                                                    <td width="83%"><b><a href="javascript:cmdExportExcel()" class="buttonlink">Export to Excel</a></b>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                            </table>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <%
                                                                                                        }
                                                                                                    %>
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
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom">
                    <!-- untuk footer -->
                    <%@include file="../../footer.jsp" %>
                </td>

            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
                    <%@ include file = "../../main/footer.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
</html>

