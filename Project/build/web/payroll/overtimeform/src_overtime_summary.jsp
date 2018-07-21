<%-- 
    Document   : overtime_summary
    Created on : Aug 7, 2013, 11:49:07 AM
    Author     : Satrya Ramayu Bagus
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
<%@ page import = "com.dimata.qdep.entity.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.overtime.*" %>
<%@ page import = "com.dimata.harisma.session.payroll.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_OVERTIME, AppObjInfo.OBJ_PAYROLL_OVERTIME_SUMMARY);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>
<%
            long oidDivision = FRMQueryString.requestLong(request, FrmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_DIVISION_ID]);
            long oidDepartment = FRMQueryString.requestLong(request, FrmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_DEPARTEMENT_ID]); 
            int iCommand = FRMQueryString.requestCommand(request);
            SrcOvertimeSummary srcOvertimeSummary = new SrcOvertimeSummary();
            FrmSrcOvertimeSummary frmSrcOvertimeSummary = new FrmSrcOvertimeSummary();

            if (iCommand == Command.GOTO) {
                frmSrcOvertimeSummary = new FrmSrcOvertimeSummary(request, srcOvertimeSummary);
                frmSrcOvertimeSummary.requestEntityObject(srcOvertimeSummary);
            }

            if (iCommand == Command.BACK) {
                frmSrcOvertimeSummary = new FrmSrcOvertimeSummary(request, srcOvertimeSummary);
                try {
                    srcOvertimeSummary = (SrcOvertimeSummary) session.getValue(SessOvertimeSummary.SESS_SRC_OVERTIME_SUMMARY);
                    if (srcOvertimeSummary == null) {
                        srcOvertimeSummary = new SrcOvertimeSummary();
                    }
                   // System.out.println("ecccccc " + srcOvertime.getOrderBy());
                } catch (Exception e) {
                    srcOvertimeSummary = new SrcOvertimeSummary();
                }
            }

            boolean isSecretaryLogin = (positionType >= PstPosition.LEVEL_SECRETARY) ? true : false;
            long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
            boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
            long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
            boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
            boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
                                   
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA -Search Summary Overtime</title>
        <script language="JavaScript">

            function cmdUpdateDiv(){
                document.<%=FrmSrcOvertimeSummary.FRM_SRC_OVERTIME_SUMMARY%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.<%=FrmSrcOvertimeSummary.FRM_SRC_OVERTIME_SUMMARY%>.action="src_overtime_summary.jsp";
                document.<%=FrmSrcOvertimeSummary.FRM_SRC_OVERTIME_SUMMARY%>.submit();
            }
            function cmdUpdateDep(){
                document.<%=FrmSrcOvertimeSummary.FRM_SRC_OVERTIME_SUMMARY%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.<%=FrmSrcOvertimeSummary.FRM_SRC_OVERTIME_SUMMARY%>.action="src_overtime_summary.jsp";
                document.<%=FrmSrcOvertimeSummary.FRM_SRC_OVERTIME_SUMMARY%>.submit();
            }
            function cmdUpdatePos(){
                document.<%=FrmSrcOvertimeSummary.FRM_SRC_OVERTIME_SUMMARY%>.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.<%=FrmSrcOvertimeSummary.FRM_SRC_OVERTIME_SUMMARY%>.action="src_overtime_summary.jsp";
                document.<%=FrmSrcOvertimeSummary.FRM_SRC_OVERTIME_SUMMARY%>.submit();
            }


            function cmdSearch(){
                document.<%=FrmSrcOvertimeSummary.FRM_SRC_OVERTIME_SUMMARY%>.command.value="<%=String.valueOf(Command.LIST)%>";
                document.<%=FrmSrcOvertimeSummary.FRM_SRC_OVERTIME_SUMMARY%>.action="overtime_summary.jsp";
                document.<%=FrmSrcOvertimeSummary.FRM_SRC_OVERTIME_SUMMARY%>.submit();
            }
            
            function cmdExportExcel(){
                //var linkPage = "<%//=printroot%>.report.overtimeReport.OvertimeSummaryReportXLS";
                document.<%=FrmSrcOvertimeSummary.FRM_SRC_OVERTIME_SUMMARY%>.action="<%=printroot%>.report.overtimeReport.OvertimeSummaryReportXLS";
                document.<%=FrmSrcOvertimeSummary.FRM_SRC_OVERTIME_SUMMARY%>.submit();
                //document.<//%=FrmSrcOvertimeSummary.FRM_SRC_OVERTIME_SUMMARY%>.target = "";
                //window.open(linkPage,"reportExcelSummary","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");
                
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

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
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
                                                Overtime &gt; Overtime Summary<!-- #EndEditable -->
                                            </strong></font>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td class="tablecolor" style="background-color:<%=bgColorContent%>; ">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr>
                                                                <td valign="top">
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <form name="<%=FrmSrcOvertimeSummary.FRM_SRC_OVERTIME_SUMMARY%>" method="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>"> 
                                                                                    <!-- update by devin 2014-02-12 -->
                                                                                    <input type="hidden" name="<%=FrmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_CEK_USER]%>" value="<%=privPrint%>"> 
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
                                                                                                            <table border="0" cellspacing="2" cellpadding="2" width="72%">
                                                                                                               
                                                                                                                
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left">Payrol Number</div></td>
                                                                                                                    <td width="83%"><input type="text" name="<%=frmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_EMPLOYEE_NUMBER]%>"  value="<%= srcOvertimeSummary.getEmpNumber()%>" class="elemenForm" size="40" onKeyDown="javascript:fnTrapKD()"></td>
                                                                                                                </tr>
                                                                                                                 <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.FULL_NAME) %></div></td>
                                                                                                                    <td width="83%"><input type="text" name="<%=frmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_EMPLOYEE_NAME]%>"  value="<%= srcOvertimeSummary.getFullName()%>" class="elemenForm" size="40" onKeyDown="javascript:fnTrapKD()"></td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left">Company</div></td>
                                                                                                                    <td width="83%"> <%
                                                                                                                        Vector comp_value = new Vector(1, 1);
                                                                                                                        Vector comp_key = new Vector(1, 1);
                                                                                                                        String whereCompany = "";
                                                                                                                        if (!(isHRDLogin || isEdpLogin || isGeneralManager)) {
                                                                                                                            whereCompany = PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + "='" + emplx.getCompanyId() + "'";
                                                                                                                        } else {
                                                                                                                            comp_value.add("0");
                                                                                                                            comp_key.add("select ...");
                                                                                                                        }
                                                                                                                        Vector listComp = PstCompany.list(0, 0, whereCompany, " COMPANY ");
                                                                                                                        for (int i = 0; i < listComp.size(); i++) {
                                                                                                                            Company comp = (Company) listComp.get(i);
                                                                                                                            comp_key.add(comp.getCompany());
                                                                                                                            comp_value.add(String.valueOf(comp.getOID()));
                                                                                                                        }
                                                                                                                        %> <%= ControlCombo.draw(frmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_COMPANY_ID], "formElemen", null, "" + srcOvertimeSummary.getCompanyId(), comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%> </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></div></td>
                                                                                                                    <td width="83%"> <%
                                                                                                                        Vector div_value = new Vector(1, 1);
                                                                                                                        Vector div_key = new Vector(1, 1);
                                                                                                                        String whereDivision = "";
                                                                                                                        if (!(isHRDLogin || isEdpLogin || isGeneralManager)) {
                                                                                                                            whereDivision = PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID] + "='" + emplx.getDivisionId() + "'";
                                                                                                                            oidDivision = emplx.getDivisionId();
                                                                                                                        } else {
                                                                                                                            div_value.add("0");
                                                                                                                            div_key.add("select ...");
                                                                                                                        }
                                                                                                                        if(whereDivision!=null && whereDivision.length()>0 && srcOvertimeSummary.getCompanyId()!=0){
                                                                                                                           whereDivision = " AND "+PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+srcOvertimeSummary.getCompanyId();
                                                                                                                        }else if(srcOvertimeSummary.getCompanyId()!=0){
                                                                                                                            whereDivision = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+srcOvertimeSummary.getCompanyId();
                                                                                                                        }
                                                                                                                        Vector listDiv = PstDivision.list(0, 0, whereDivision, " DIVISION ");
                                                                                                                        for (int i = 0; i < listDiv.size(); i++) {
                                                                                                                            Division div = (Division) listDiv.get(i);
                                                                                                                            div_key.add(div.getDivision());
                                                                                                                            div_value.add(String.valueOf(div.getOID()));
                                                                                                                        }
                                                                                                                        %> <%= ControlCombo.draw(frmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_DIVISION_ID], "formElemen", null, "" + srcOvertimeSummary.getDivisionId(), div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"")%> </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                                                                                                                    <td width="83%"> <%
                                                                                                                        Vector dept_value = new Vector(1, 1);
                                                                                                                        Vector dept_key = new Vector(1, 1);
                                                                                                                       
                                                                                                                        Vector listDept = new Vector();
                                                                                                                         
                                                                                                                        if (processDependOnUserDept) {
                                                                                                                            if (emplx.getOID() > 0) {
                                                                                                                                if (isHRDLogin || isEdpLogin || isGeneralManager) {
                                                                                                                                    String strWhere = PstDepartment.TBL_HR_DEPARTMENT + "." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision;
                                                                                                                                    dept_value.add("0");
                                                                                                                                    dept_key.add("select ...");
                                                                                                                                    listDept = PstDepartment.list(0, 0, strWhere, "DEPARTMENT");

                                                                                                                                } else {
                                                                                                                                    Position position = new Position();
                                                                                                                                    try {
                                                                                                                                        position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                                    } catch (Exception exc) {
                                                                                                                                    }

                                                                                                                                    String whereClsDep = "(((hr_department.DEPARTMENT_ID = " + departmentOid + ") "
                                                                                                                                            + "AND hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision + ") OR "
                                                                                                                                            + "(hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + "=" + departmentOid + "))";

                                                                                                                                    if (position.getOID() != 0 && position.getDisabedAppDivisionScope() == 0) {
                                                                                                                                        whereClsDep = " ( hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision + ") ";
                                                                                                                                    }

                                                                                                                                    Vector SectionList = new Vector();
                                                                                                                                    try {
                                                                                                                                        String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                                                                                                                                        Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                                                                                                                                        String joinDeptSection = PstSystemProperty.getValueByName("JOIN_DEPARTMENT_SECTION");
                                                                                                                                        Vector depSecGroup = com.dimata.util.StringParser.parseGroup(joinDeptSection);

                                                                                                                                        int grpIdx = -1;
                                                                                                                                        int maxGrp = depGroup == null ? 0 : depGroup.size();

                                                                                                                                        int grpSecIdx = -1;
                                                                                                                                        int maxGrpSec = depSecGroup == null ? 0 : depSecGroup.size();

                                                                                                                                        int countIdx = 0;
                                                                                                                                        int MAX_LOOP = 10;
                                                                                                                                        int curr_loop = 0;

                                                                                                                                        int countIdxSec = 0;
                                                                                                                                        int MAX_LOOPSec = 10;
                                                                                                                                        int curr_loopSec = 0;

                                                                                                                                        do { // find group department belonging to curretn user base in departmentOid
                                                                                                                                            curr_loop++;
                                                                                                                                            String[] grp = (String[]) depGroup.get(countIdx);
                                                                                                                                            for (int g = 0; g < grp.length; g++) {
                                                                                                                                                String comp = grp[g];
                                                                                                                                                if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                                                                                                                                    grpIdx = countIdx;   // A ha .. found here                                       
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                            countIdx++;
                                                                                                                                        } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit                            

                                                                                                                                        Vector idxSecGroup = new Vector();

                                                                                                                                        for (int x = 0; x < maxGrpSec; x++) {

                                                                                                                                            String[] grp = (String[]) depSecGroup.get(x);
                                                                                                                                            for (int j = 0; j < 1; j++) {

                                                                                                                                                String comp = grp[j];
                                                                                                                                                if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                                                                                                                                    Counter counter = new Counter();
                                                                                                                                                    counter.setCounter(x);
                                                                                                                                                    idxSecGroup.add(counter);
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }

                                                                                                                                        for (int s = 0; s < idxSecGroup.size(); s++) {

                                                                                                                                            Counter counter = (Counter) idxSecGroup.get(s);

                                                                                                                                            String[] grp = (String[]) depSecGroup.get(counter.getCounter());

                                                                                                                                            Section sec = new Section();
                                                                                                                                            sec.setDepartmentId(Long.parseLong(grp[0]));
                                                                                                                                            sec.setOID(Long.parseLong(grp[2]));
                                                                                                                                            SectionList.add(sec);

                                                                                                                                        }

                                                                                                                                        // compose where clause
                                                                                                                                        if (grpIdx >= 0) {
                                                                                                                                            String[] grp = (String[]) depGroup.get(grpIdx);
                                                                                                                                            for (int g = 0; g < grp.length; g++) {
                                                                                                                                                String comp = grp[g];
                                                                                                                                                whereClsDep = whereClsDep + " OR (j.DEPARTMENT_ID = " + comp + ")";
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                        whereClsDep = " (" + whereClsDep + ") AND hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision;
                                                                                                                                    } catch (Exception exc) {
                                                                                                                                        System.out.println(" Parsing Join Dept" + exc);
                                                                                                                                    }

                                                                                                                                    //dept_value.add("0");
                                                                                                                                    //dept_key.add("select ...");
                                                                                                                                    listDept = PstDepartment.list(0, 0, whereClsDep, "");

                                                                                                                                    for (int idx = 0; idx < SectionList.size(); idx++) {

                                                                                                                                        Section sect = (Section) SectionList.get(idx);

                                                                                                                                        long sectionOid = 0;

                                                                                                                                        for (int z = 0; z < listDept.size(); z++) {

                                                                                                                                            Department dep = new Department();

                                                                                                                                            dep = (Department) listDept.get(z);

                                                                                                                                            if (sect.getDepartmentId() == dep.getOID()) {

                                                                                                                                                sectionOid = sect.getOID();

                                                                                                                                            }
                                                                                                                                        }

                                                                                                                                        if (sectionOid != 0) {

                                                                                                                                            Section lstSection = new Section();
                                                                                                                                            Department lstDepartment = new Department();

                                                                                                                                            try {
                                                                                                                                                lstSection = PstSection.fetchExc(sectionOid);
                                                                                                                                            } catch (Exception e) {
                                                                                                                                                System.out.println("Exception " + e.toString());
                                                                                                                                            }

                                                                                                                                            try {
                                                                                                                                                lstDepartment = PstDepartment.fetchExc(lstSection.getDepartmentId());
                                                                                                                                            } catch (Exception e) {
                                                                                                                                                System.out.println("Exception " + e.toString());
                                                                                                                                            }

                                                                                                                                            listDept.add(lstDepartment);

                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            } else {
                                                                                                                                dept_value.add("0");
                                                                                                                                dept_key.add("select ...");
                                                                                                                                listDept = PstDepartment.list(0, 0, (PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision), "DEPARTMENT");
                                                                                                                            }
                                                                                                                        } else {
                                                                                                                            dept_value.add("0");
                                                                                                                            dept_key.add("select ...");
                                                                                                                            listDept = PstDepartment.list(0, 0, (PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision), "DEPARTMENT");
                                                                                                                        }

                                                                                                                        for (int i = 0; i < listDept.size(); i++) {
                                                                                                                            Department dept = (Department) listDept.get(i);
                                                                                                                            dept_key.add(dept.getDepartment());
                                                                                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                                                                                        }



                                                                                                                        %> <%= ControlCombo.draw(frmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_DEPARTEMENT_ID], "formElemen", null, "" + srcOvertimeSummary.getDepartementId(), dept_value, dept_key, "onChange=\"javascript:cmdUpdatePos()\"")%> </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div></td>
                                                                                                                    <td width="83%"> <%
                                                                                                                        Vector sec_value = new Vector(1, 1);
                                                                                                                        Vector sec_key = new Vector(1, 1);
                                                                                                                        sec_value.add("0");
                                                                                                                        sec_key.add("select ...");
                                                                                                                        //Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
                                                                                                                        String strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + oidDepartment;
                                                                                                                        Vector listSec = PstSection.list(0, 0, strWhereSec, " SECTION ");
                                                                                                                        for (int i = 0; i < listSec.size(); i++) {
                                                                                                                            Section sec = (Section) listSec.get(i);
                                                                                                                            sec_key.add(sec.getSection());
                                                                                                                            sec_value.add(String.valueOf(sec.getOID()));
                                                                                                                        }
                                                                                                                        %> <%= ControlCombo.draw(frmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_SECTION_ID], "formElemen", null, "" + srcOvertimeSummary.getSectionId(), sec_value, sec_key, " onkeydown=\"javascript:fnTrapKD()\"")%></td>

                                                                                                                </tr>
                                                                                                                 <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left">Overtime Date </div></td>
                                                                                                                    <td width="83%"> from <%=ControlDate.drawDateWithStyle(frmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_DATE_OVERTIME_START], srcOvertimeSummary.getStartOvertime()==null?new Date():srcOvertimeSummary.getStartOvertime(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%> 
                                                                                                                        to <%=ControlDate.drawDateWithStyle(frmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_DATE_OVERTIME_END], srcOvertimeSummary.getEndOvertime()==null?new Date():srcOvertimeSummary.getEndOvertime(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left">Religion</div></td>
                                                                                                                    <td width="83%"> <%
                                                                                                                        Vector rel_value = new Vector(1, 1);
                                                                                                                        Vector rel_key = new Vector(1, 1);
                                                                                                                        rel_value.add("0");
                                                                                                                        rel_key.add("select ...");
                                                                                                                        
                                                                                                                        Vector listRel = PstReligion.list(0, 0, "", " RELIGION ");
                                                                                                                        for (int i = 0; i < listRel.size(); i++) {
                                                                                                                            Religion rel = (Religion) listRel.get(i); 
                                                                                                                            rel_key.add(rel.getReligion());
                                                                                                                            rel_value.add(String.valueOf(rel.getOID()));
                                                                                                                        }
                                                                                                                        %> <%= ControlCombo.draw(frmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_RELIGION_ID], "formElemen", null, "" + srcOvertimeSummary.getReligionId(), rel_value, rel_key, " onkeydown=\"javascript:fnTrapKD()\"")%></td>

                                                                                                                </tr>
                                                                                                                 <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left">Resign</div></td>
                                                                                                                    <td width="83%">
                                                                                                                        <input type="radio" name="<%=frmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_RESIGNED]%>" value="<%=FrmSrcOvertimeSummary.RESIGN_NO%>"  checked>No 
                                                                                                                        <input type="radio" name="<%=frmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_RESIGNED]%>" value="<%=FrmSrcOvertimeSummary.RESIGN_YES%>">Yes 
                                                                                                                        <input type="radio" name="<%=frmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_RESIGNED]%>" value="<%=FrmSrcOvertimeSummary.RESIGN_ALL%>">All 
                                                                                                                    </td>

                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" nowrap>
                                                                                                                        <div align="left">Cost Center</div></td>
                                                                                                                    <td width="83%"> <%
                                                                                                                      

                                                                                                                            Vector costDept_value = new Vector(1, 1);
                                                                                                                            Vector costDept_key = new Vector(1, 1);
                                                                                                                            //Vector listDept = new Vector(1, 1);
                                                                                                                            DepartmentIDnNameList keyList = new DepartmentIDnNameList();

                                                                                                                            if (processDependOnUserDept) {
                                                                                                                                if (emplx.getOID() > 0) {
                                                                                                                                    if (isHRDLogin || isEdpLogin || isGeneralManager) {
                                                                                                                                        keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                                                        //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                                                    } else {
                                                                                                                                        Position position = null;
                                                                                                                                        try {
                                                                                                                                            position = PstPosition.fetchExc(emplx.getPositionId());
                                                                                                                                        } catch (Exception exc) {
                                                                                                                                        }
                                                                                                                                        if (position != null & position.getDisabedAppDivisionScope() == 0 & position.getPositionLevel() >= PstPosition.LEVEL_MANAGER) {
                                                                                                                                            String whereDiv = " d." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + emplx.getDivisionId() + "";
                                                                                                                                            keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereDiv, true);
                                                                                                                                        } else {

                                                                                                                                            String whereClsDep = "(" + PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + " = " + departmentOid
                                                                                                                                                    + ") OR (" + PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + " = " + departmentOid + ") ";
                                                                                                                                            try {
                                                                                                                                                String joinDept = PstSystemProperty.getValueByName("JOIN_DEPARMENT");
                                                                                                                                                Vector depGroup = com.dimata.util.StringParser.parseGroup(joinDept);

                                                                                                                                                int grpIdx = -1;
                                                                                                                                                int maxGrp = depGroup == null ? 0 : depGroup.size();
                                                                                                                                                int countIdx = 0;
                                                                                                                                                int MAX_LOOP = 10;
                                                                                                                                                int curr_loop = 0;
                                                                                                                                                do { // find group department belonging to curretn user base in departmentOid
                                                                                                                                                    curr_loop++;
                                                                                                                                                    String[] grp = (String[]) depGroup.get(countIdx);
                                                                                                                                                    for (int g = 0; g < grp.length; g++) {
                                                                                                                                                        String comp = grp[g];
                                                                                                                                                        if (comp.trim().compareToIgnoreCase("" + departmentOid) == 0) {
                                                                                                                                                            grpIdx = countIdx;   // A ha .. found here 
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                    countIdx++;
                                                                                                                                                } while ((grpIdx < 0) && (countIdx < maxGrp) && (curr_loop < MAX_LOOP)); // if found then exit

                                                                                                                                                // compose where clause
                                                                                                                                                if (grpIdx >= 0) {
                                                                                                                                                    String[] grp = (String[]) depGroup.get(grpIdx);
                                                                                                                                                    for (int g = 0; g < grp.length; g++) {
                                                                                                                                                        String comp = grp[g];
                                                                                                                                                        whereClsDep = whereClsDep + " OR (DEPARTMENT_ID = " + comp + ")";
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            } catch (Exception exc) {
                                                                                                                                                    System.out.println(" Parsing Join Dept" + exc);

                                                                                                                                            }
                                                                                                                                            keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, whereClsDep, false);
                                                                                                                                            //listDept = PstDepartment.list(0, 0,whereClsDep, "");
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                } else {
                                                                                                                                    //dept_value.add("0");
                                                                                                                                    //dept_key.add("select ...");
                                                                                                                                    keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                                                    //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                                                }
                                                                                                                            } else {
                                                                                                                                //dept_value.add("0");
                                                                                                                                //dept_key.add("select ...");
                                                                                                                                keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                                                                                                                //listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                                                                            }
                                                                                                                            costDept_value = keyList.getDepIDs();
                                                                                                                            costDept_key = keyList.getDepNames();

                                                                                                                            String selectValueDepartment = "" + oidDepartment;//+objSrcLeaveApp.getDepartmentId();

                                                                                                                        %>
                                                                                                                       <%= ControlCombo.draw(frmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_COST_CENTER], "formElemen", null, "" + srcOvertimeSummary.getCostCenterDptId(), costDept_value, costDept_key, " onkeydown=\"javascript:fnTrapKD()\"")%></td>

                                                                                                                </tr>
                                                                                                                    
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%" height="21" nowrap>
                                                                                                                        <div align="left">Sort By</div></td>
                                                                                                                    <td width="83%"> <%= ControlCombo.draw(frmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_SORT_BY], "formElemen", null, "" + srcOvertimeSummary.getSortBy(), FrmSrcOvertimeSummary.getOrderValue(), FrmSrcOvertimeSummary.getOrderKey(), " onkeydown=\"javascript:fnTrapKD()\"")%></td>
                                                                                                                </tr>
                                                                                                                 <tr align="left" valign="top">
                                                                                                                    <td width="17%" height="21" nowrap>
                                                                                                                        <div align="left">Group By</div></td>
                                                                                                                    <td width="83%"> <%= ControlCombo.draw(frmSrcOvertimeSummary.fieldNames[FrmSrcOvertimeSummary.FRM_FIELD_GROUP_BY], "formElemen", null, "" + srcOvertimeSummary.getGroupBy(), FrmSrcOvertimeSummary.getGroupByValue(), FrmSrcOvertimeSummary.getGroupByKey(), " onkeydown=\"javascript:fnTrapKD()\"")%></td>
                                                                                                                </tr>
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td width="17%"></td>
                                                                                                                    <td width="83%">
                                                                                                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                                            <tr>
                                                                                                                                <td width="6%" ><a href="javascript:cmdExportExcel()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                                                                                                                <td width="92%" nowrap="true"  class="command"><a href="javascript:cmdExportExcel()">Search for Overtime</a></td>

                                                                                                                                <!--<td ><img src="<%//=approot%>/images/spacer.gif" width="10" height="8"></td>
                                                                                                                                <td ><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%//=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%//=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Employee"></a></td>
                                                                                                                                <td ><img src="<%//=approot%>/images/spacer.gif" width="6" height="1"></td>
                                                                                                                                <td  class="command" nowrap="true" ><a href="javascript:cmdAdd()">Add New Overtime</a></td>-->
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
