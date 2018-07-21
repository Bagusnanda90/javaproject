
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationMainRequestOnly"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayGeneral"%>
<%@page import="com.dimata.harisma.entity.masterdata.leaveconfiguration.PstLeaveConfigurationMain"%>
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
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_LEAVE_MANAGEMENT, AppObjInfo.OBJ_LEAVE_APP_SRC);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
    long hrdDepOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    long sdmDivisionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_SDM_DIVISION)));
    boolean isHRDLogin = hrdDepOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;

    int iCommand = FRMQueryString.requestCommand(request);
    
    long oidCompany = FRMQueryString.requestLong(request,FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_COMPANY_ID]);
    long oidDivision = FRMQueryString.requestLong(request,FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DIVISION_ID]);
    long oidDepartment = FRMQueryString.requestLong(request,FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DEPARTMENT]);
    long oidSection = FRMQueryString.requestLong(request,FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_SECTION]);
    
    String whereClause = "";
    String order = "";
    Vector listCompany = new Vector();
    Vector listDivision = new Vector();
    Vector listDepartment = new Vector();
    Vector listSection = new Vector();
    
    SrcLeaveApp objSrcLeaveApp = new SrcLeaveApp();
    FrmSrcLeaveApp objFrmSrcLeaveApp = new FrmSrcLeaveApp();

//SessLeaveClosing.getPeriode(new Date());
//update by satrya 2013-09-19
    if (iCommand == Command.GOTO) {
        objFrmSrcLeaveApp = new FrmSrcLeaveApp(request, objSrcLeaveApp);
        objFrmSrcLeaveApp.requestEntityObject(objSrcLeaveApp);
    }
    if (iCommand == Command.BACK) {
        objFrmSrcLeaveApp = new FrmSrcLeaveApp(request, objSrcLeaveApp);
        try {
            objSrcLeaveApp = (SrcLeaveApp) session.getValue(SessLeaveApplication.SESS_SRC_LEAVE_APPLICATION);
            if (objSrcLeaveApp == null) {
                objSrcLeaveApp = new SrcLeaveApp();
            }
        } catch (Exception e) {
            objSrcLeaveApp = new SrcLeaveApp();
        }
    }

// pengecekan status user yang login utk menentukan status approval mana yang mestinya default buatnya\
    boolean isManager = false;
    boolean isHrManager = false;
    if (positionType == PstPosition.LEVEL_MANAGER) {
        isManager = true;

        long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
        if (departmentOid == hrdDepartmentOid) {
            isHrManager = true;
        }
    }
    //update by satrya 2012-11-1
    /**
     * untuk pengaturan apakah departement itu dpt exsekusi leave
                *
     */
    int isAdminExecuteLeave = 0;//hanya cuti full day jika fullDayLeave = 0
    try {
        isAdminExecuteLeave = Integer.parseInt(PstSystemProperty.getValueByName("LEAVE_EXCECUTE_BY_ADMIN"));
    } catch (Exception ex) {
        System.out.println("Execption LEAVE_EXCECUTE_BY_ADMIN " + ex);
        isAdminExecuteLeave = 0;

    }
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Leave Application</title>
        <script language="JavaScript">
            function cmdChangeComp(oid){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcleaveapp.<%=FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_COMPANY_ID]%>.value=oid;
                document.frmsrcleaveapp.action="leave_app_src.jsp";
                document.frmsrcleaveapp.submit();
            }
            function cmdChangeDivi(oid){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcleaveapp.<%=FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DIVISION_ID]%>.value=oid;
                document.frmsrcleaveapp.action="leave_app_src.jsp";
                document.frmsrcleaveapp.submit();
            }
            function cmdChangeDept(oid){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcleaveapp.<%=FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DEPARTMENT]%>.value=oid;
                document.frmsrcleaveapp.action="leave_app_src.jsp";
                document.frmsrcleaveapp.submit();
            }
            function cmdAdd(){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.ADD)%>";
                document.frmsrcleaveapp.action="leave_app_edit.jsp";
                document.frmsrcleaveapp.submit();
            }

    function cmdUpdateDiv        (){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcleaveapp.action="leave_app_src.jsp";
                document.frmsrcleaveapp.submit();
            }
            function cmdUpdateDep(){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcleaveapp.action="leave_app_src.jsp";
                document.frmsrcleaveapp.submit();
            }
            function cmdUpdatePos(){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcleaveapp.action="leave_app_src.jsp";
                document.frmsrcleaveapp.submit();
            }

            

            function cmdSearch(){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmsrcleaveapp.action="leave_app_list.jsp";
                document.frmsrcleaveapp.submit();
            }
            function cmdSearchExecute(){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmsrcleaveapp.action="leave_app_execute.jsp";
                document.frmsrcleaveapp.submit();
            }
            function cmdSearchStatusToBeApp(){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmsrcleaveapp.action="leave_app_to_be_app.jsp";
                document.frmsrcleaveapp.submit();
            }
            function cmdSearchStatusApprove(){
                document.frmsrcleaveapp.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmsrcleaveapp.action="leave_application_approve.jsp";
                document.frmsrcleaveapp.submit();
            }

            //-------------------------- for Calendar -------------------------
            function getThn(){
            <%
            out.println(ControlDatePopup.writeDateCaller("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_SUBMISSION_DATE]));
            %>
            }


            function hideObjectForDate(){
            <%
                    /*
                     out.println(ControlDatePopup.writeDateHideObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_APPROVAL_STATUS]));
                     out.println(ControlDatePopup.writeDateHideObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DOC_STATUS]));
                     out.println(ControlDatePopup.writeDateHideObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_APPROVAL_HR_MAN]));
                     *      */
            %>
                    }

                    function showObjectForDate(){
            <%
                    /*
                     out.println(ControlDatePopup.writeDateShowObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_APPROVAL_STATUS]));
                     out.println(ControlDatePopup.writeDateShowObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DOC_STATUS]));
                     out.println(ControlDatePopup.writeDateHideObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_APPROVAL_HR_MAN]));
                     *      */
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
                                        <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                                                Employee &gt; Leave Application &gt; Leave Form<!-- #EndEditable --> 
                                            </strong></font> </td>
                                    </tr>
                                    <tr> 
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                                            <tr> 
                                                                <td valign="top">                          
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                                        <tr> 
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" --> 
                                                                                <form name="frmsrcleaveapp" method="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                                                                    <!--update by satrya 2013-11-15-->
                                                                                    <input type="hidden" name="<%=objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_TYPE_FORM]%>" value="<%=PstLeaveApplication.LEAVE_APPLICATION%>">  
                                                                                    <table border="0" cellspacing="2" cellpadding="2" width="100%" >
                                                                                        <tr> 
                                                                                            <td width="13%"> 
                                                                                                <div align="left"><%=dictionaryD.getWord(I_Dictionary.PAYROLL)%></div>
                                                                                            </td>
                                                                                            <td width="2%">:</td>
                                                                                            <td width="85%"> 
                                                                                                <input type="text" name="<%=objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_EMP_NUMBER]%>"  value="<%= objSrcLeaveApp.getEmpNum()%>" class="elemenForm"  onkeydown="javascript:fnTrapKD()">
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="13%"> 
                                                                                                <div align="left"><%=dictionaryD.getWord(I_Dictionary.FULL_NAME)%></div>
                                                                                            </td>
                                                                                            <td width="2%">:</td>
                                                                                            <td width="85%"> 
                                                                                                <input type="text" name="<%=objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_FULLNAME]%>"  value="<%= objSrcLeaveApp.getFullName()%>" class="elemenForm" onKeyDown="javascript:fnTrapKD()" size="40">
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="13%"> 
                                                                                                <div align="left">Company</div>
                                                                                            </td>
                                                                                            <td width="2%">:</td>
                                                                                            <td width="85%">
<%

if (sdmDivisionOid != 0 || hrdDepOid != 0){ /* Jika user login adalah SDM atau HRD */
    listCompany = PstCompany.list(0, 0, whereClause, order);
    if (emplx.getDivisionId() == sdmDivisionOid){
        %>
        <select name="<%=FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_COMPANY_ID]%>" onchange="javascript:cmdChangeComp(this.value)">
            <option value="0">-SELECT-</option>
            <%
            if (listCompany != null && listCompany.size()>0){
                for(int i=0; i<listCompany.size(); i++){
                    Company company = (Company)listCompany.get(i);
                    if (company.getOID()==oidCompany){
                        %>
                        <option selected="selected" value="<%=company.getOID()%>"><%= company.getCompany() %></option>
                        <%
                    } else {
                        %>
                        <option value="<%=company.getOID()%>"><%= company.getCompany() %></option>
                        <%
                    }
                    
                }
            }
            %>
        </select>
        <%
    } else {
        listCompany = PstCompany.list(0, 0, whereClause, order);
        String compName = "";
        long compId = 0;
        if (listCompany != null && listCompany.size()>0){
            Company company = (Company)listCompany.get(0);
            compId = company.getOID();
            compName = company.getCompany();
        }
        %>
        <input type="hidden" name="<%=FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_COMPANY_ID]%>" value="<%=compId%>" />
        <input type="text" disabled="disabled" name="comp-name" value="<%=compName%>" size="70" />
        <%
    }
    
    
} else { /* Jika bukan SDM atau HRD */
    listCompany = PstCompany.list(0, 0, whereClause, order);
    String compName = "";
    long compId = 0;
    if (listCompany != null && listCompany.size()>0){
        Company company = (Company)listCompany.get(0);
        compId = company.getOID();
        compName = company.getCompany();
    }
    %>
    <input type="hidden" name="<%=FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_COMPANY_ID]%>" value="<%=compId%>" />
    <input type="text" disabled="disabled" name="comp-name" value="<%=compName%>" size="70" />
    <%
}

%>                                                                                             
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="13%"> 
                                                                                                <div align="left"><%=dictionaryD.getWord(I_Dictionary.DIVISION)%></div>
                                                                                            </td>
                                                                                            <td width="2%">:</td>
                                                                                            <td width="85%">
<%
if (sdmDivisionOid != 0 || hrdDepOid != 0){ /* Jika user login adalah SDM atau HRD */
    if (emplx.getDivisionId() == sdmDivisionOid){
        whereClause = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID]+"="+oidCompany;
        listDivision = PstDivision.list(0, 0, whereClause, PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
        %>
        <select name="<%=FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DIVISION_ID]%>" onchange="javascript:cmdChangeDivi(this.value)">
            <option value="0">-SELECT-</option>
        <%
        if (listDivision != null && listDivision.size()>0){
            for(int i=0; i<listDivision.size(); i++){
                Division divisi = (Division)listDivision.get(i);
                if (divisi.getOID()==oidDivision){
                    %>
                    <option selected="selected" value="<%=divisi.getOID()%>"><%=divisi.getDivision()%></option>
                    <%
                } else {
                    %>
                    <option value="<%=divisi.getOID()%>"><%=divisi.getDivision()%></option>
                    <%
                }
            }
        }
        %>
        </select>
        <%
    } else {
        String divName = "";
        long divOid = 0;
        try {
            Division divisi = PstDivision.fetchExc(emplx.getDivisionId());
            divOid = divisi.getOID();
            divName = divisi.getDivision();
        } catch(Exception e){
            System.out.println("Galat saat get division =>"+e.toString());
        }
        %>
        <input type="hidden" name="<%=FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DIVISION_ID]%>" value="<%=divOid%>" />
        <input type="text" disabled="disabled" name="divisi-name" value="<%=divName%>" size="70" />
        <%
    }
} else { /* Jika bukan SDM atau HRD */
    String divName = "";
    long divOid = 0;
    try {
        Division divisi = PstDivision.fetchExc(emplx.getDivisionId());
        divOid = divisi.getOID();
        divName = divisi.getDivision();
    } catch(Exception e){
        System.out.println("Galat saat get division =>"+e.toString());
    }
    %>
    <input type="hidden" name="<%=FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DIVISION_ID]%>" value="<%=divOid%>" />
    <input type="text" disabled="disabled" name="divisi-name" value="<%=divName%>" size="70" />
    <%
}
%>                                                                                                
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="13%"> 
                                                                                                <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT)%></div>
                                                                                            </td>
                                                                                            <td width="2%">:</td>
                                                                                            <td width="85%">
<%
        if (sdmDivisionOid != 0 || hrdDepOid != 0){ /* Jika user login adalah SDM atau HRD */
            if (emplx.getDivisionId() == sdmDivisionOid){
                listDepartment = PstDepartment.listDepartmentVer1(0, 0, ""+oidCompany, ""+oidDivision);
            } else {
                listDepartment = PstDepartment.listDepartmentVer1(0, 0, ""+emplx.getCompanyId(), ""+emplx.getDivisionId());
            }
        }
        
        %>
        <select name="<%=FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DEPARTMENT]%>" onchange="javascript:cmdChangeDept(this.value)">
            <option value="0">-SELECT-</option>
        <%
        if (listDepartment != null && listDepartment.size()>0){
            for (int i=0; i<listDepartment.size(); i++){
                Department depart = (Department)listDepartment.get(i);
                if (depart.getOID() == oidDepartment){
                    %>
                    <option selected="selected" value="<%=depart.getOID()%>"><%= depart.getDepartment() %></option>
                    <%
                } else {
                    %>
                    <option value="<%=depart.getOID()%>"><%= depart.getDepartment() %></option>
                    <%
                }
                %>
                <%
            }
        }
        %>
        </select>                                                                                                
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="13%"> 
                                                                                                <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION)%></div>
                                                                                            </td>
                                                                                            <td width="2%">:</td>
                                                                                            <td width="85%">
        <%
        whereClause = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+oidDepartment;
        listSection = PstSection.list(0, 0, whereClause, PstSection.fieldNames[PstSection.FLD_SECTION]);
        %>
        <select name="<%=FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_SECTION]%>">
            <option value="0">-SELECT-</option>
        <%
        if (listSection != null && listSection.size()>0){
            for (int i=0; i<listSection.size(); i++){
                Section section = (Section)listSection.get(i);
                %>
                <option value="<%=section.getOID()%>"><%=section.getSection()%></option>
                <%
            }
        }
        %>
        </select>                                                                                                
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="13%"> 
                                                                                                <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION)%></div>
                                                                                            </td>
                                                                                            <td width="2%">:</td>
                                                                                            <td width="85%"> 
                                                                                                <%
                                                                                                    Vector position_value = new Vector(1, 1);
                                                                                                    Vector position_key = new Vector(1, 1);
                                                                                                    position_value.add("0");
                                                                                                    position_key.add("select ...");
                                                                                                    Vector listPos = PstPosition.list(0, 0, "", " POSITION ");
                                                                                                    String selectValuePosition = "" + objSrcLeaveApp.getPositionId();
                                                                                                    for (int i = 0; i < listPos.size(); i++) {
                                                                                                        Position pos = (Position) listPos.get(i);
                                                                                                        position_key.add(pos.getPosition());
                                                                                                        position_value.add(String.valueOf(pos.getOID()));
                                                                                                    }
                                                                                                %>
                                                                                                <%=ControlCombo.draw(objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_POSITION], "elementForm", null, selectValuePosition, position_value, position_key, " onkeydown=\"javascript:fnTrapKD()\"")%> </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td width="13%">Type Leave</td>
                                                                                            <td width="2%">:</td>
                                                                                            <td>
                                                                                                <%
                                                                                                    Vector vTypeLeave_value = new Vector(1, 1);
                                                                                                    Vector vTypeLeave_key = new Vector(1, 1);
                                                                                                    vTypeLeave_value.add("0");
                                                                                                    vTypeLeave_key.add("Regular");
                                                                                                    vTypeLeave_value.add("" + PstPublicLeaveDetail.TYPE_LEAVE);
                                                                                                    vTypeLeave_key.add("Public Leave");
                                                                                                    String selectTypeValue = "" + objSrcLeaveApp.getPositionId();

                                                                                                %>
                                                                                                <%=ControlCombo.draw(objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_PUBLIC_LEAVE_TYPE], "elementForm", null, selectTypeValue, vTypeLeave_value, vTypeLeave_key, " onkeydown=\"javascript:fnTrapKD()\"")%> 
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td width="13%">Document Status</td>
                                                                                            <td width="2%">:</td>
                                                                                            <td>
                                                                                                <table border="0" cellspacing="0" cellpadding="0" width="60%" >
                                                                                                    <tr>
                                                                                                        <td width="5%">
                                                                                                            <input type="checkbox" name="<%=objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_DOC_STATUS_DRAFT]%>" <%=(objSrcLeaveApp.isDraft() ? "checked" : "")%> value="1">
                                                                                                            <i><font color="#000000">Draft</font></i>
                                                                                                        </td>
                                                                                                        <td width="5%">
                                                                                                            <input type="checkbox" name="<%=objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_DOC_STATUS_TO_BE_APPROVE]%>" <%=(objSrcLeaveApp.isToBeApprove() ? "checked" : "")%> value="1">
                                                                                                            <i><font color="#000000">To Be Approve</font></i>
                                                                                                        </td>
                                                                                                        <td width="5%">
                                                                                                            <input type="checkbox" name="<%=objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_DOC_STATUS_APPROVED]%>" <%=(objSrcLeaveApp.isApproved() ? "checked" : "")%> value="1">
                                                                                                            <i><font color="#000000">Approved</font></i>
                                                                                                        </td>
                                                                                                        <td width="5%">
                                                                                                            <input type="checkbox" name="<%=objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_DOC_STATUS_EXECUTED]%>" <%=(objSrcLeaveApp.isExecuted() ? "checked" : "")%> value="1">
                                                                                                            <i><font color="#000000">Executed</font></i>
                                                                                                        </td>
                                                                                                        <td width="5%">
                                                                                                            <input type="checkbox" name="<%=objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_DOC_STATUS_CANCELED]%>" <%=(objSrcLeaveApp.isCanceled() ? "checked" : "")%> value="1">
                                                                                                            <i><font color="#000000">Canceled</font></i>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="13%">Date Of Application</td>
                                                                                            <td width="2%">:</td>
                                                                                            <td width="85%"> 
                                                                                                <input type="checkbox" name="<%=objFrmSrcLeaveApp.fieldNames[objFrmSrcLeaveApp.FRM_FIELD_SUBMISSION]%>" <%=(objSrcLeaveApp.isSubmission() ? "checked" : "")%> value="1">
                                                                                                <i><font color="#FF0000">Select all date</font></i></td>
                                                                                        </tr>
                                                                                        <tr> 
                                                                                            <td width="13%">&nbsp;</td>
                                                                                            <td width="2%">&nbsp;</td>
                                                                                            <td width="85%">&nbsp;                                          
                                                                                                <%=ControlDatePopup.writeDate(FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_SUBMISSION_DATE], objSrcLeaveApp.getSubmissionDate())%>
                                                                                            </td>                                        
                                                                                        </tr>

                                                                                        <tr> 
                                                                                            <td width="13%">Date Leave</td>
                                                                                            <td width="2%">: &nbsp;</td>
                                                                                            <td width="85%">&nbsp;                                          
                                                                                                <%

                                                                                                    Date st = new Date();
                                                                                                    st.setHours(0);
                                                                                                    st.setMinutes(0);
                                                                                                    st.setSeconds(0);
                                                                                                    Date end = new Date();
                                                                                                    end.setHours(23);
                                                                                                    end.setMinutes(59);
                                                                                                    end.setSeconds(59);
                                                                                                    String ctrTimeStart = ControlDate.drawTime(FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_SELECTED_DATE_FROM_LEAVE], objSrcLeaveApp.getSelectedFrom() != null ? objSrcLeaveApp.getSelectedFrom() : st, "elemenForm", 24, 0, 0);
                                                                                                    String ctrTimeEnd = ControlDate.drawTime(FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_SELECTED_DATE_TO_LEAVE], objSrcLeaveApp.getSelectedTo() != null ? iCommand == Command.NONE ? end : objSrcLeaveApp.getSelectedTo() : end, "elemenForm", 24, 0, 0);
                                                                                                %>
                                                                                                <%=ControlDate.drawDateWithStyle(FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_SELECTED_DATE_FROM_LEAVE], objSrcLeaveApp.getSelectedFrom() != null ? objSrcLeaveApp.getSelectedFrom() : null, 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"") + ctrTimeStart%>  
                                                                                                <%//=ControlDate.drawDateWithStyle("check_date_start", selectedDateFrom != null ? selectedDateFrom : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%> 
                                                                                                to  <%=ControlDate.drawDateWithStyle(FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_SELECTED_DATE_TO_LEAVE], objSrcLeaveApp.getSelectedTo() != null ? objSrcLeaveApp.getSelectedTo() : null, 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"") + ctrTimeEnd%>                                                                                            </td>
                                                                                                <% //out.println("date "+date);
                                                                                                    //long periodId = PstPeriod.getPeriodIdBySelectedDate(date); 

                                                                                                %>
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
                                                                                                        <%if (privView) {%>
                                                                                                        <td width="24px" ><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search for Leave Application" ></a></td>
                                                                                                        <td width="5px"></td>
                                                                                                        <td width="30px" class="command" nowrap><a href="javascript:cmdSearch()">Search</a></td>  
                                                                                                        <td width="30px"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                                                                            <%}
                                                    if (privAdd) {%>
                                                                                                        <td width="30px" ><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOut="MM_swapImgRestore()" ><img name="Image300" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add Leave Application" ></a></td>
                                                                                                        <td width="5px"></td>
                                                                                                        <td class="command" width="180px"><a href="javascript:cmdAdd()">Add Leave Application</a></td>      
                                                                                                        <%}
                                                    if (isHRDLogin || isAdminExecuteLeave != 0) {%>
                                                                                                        <td width="30px" ><a href="javascript:cmdSearchStatusToBeApp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Execution Appliaction"></a></td>
                                                                                                        <td width="5px"></td>
                                                                                                        <td class="command" width="240px"><a href="javascript:cmdSearchStatusToBeApp()">Search To Be App</a></td>      
                                                                                                        <%if (isHRDLogin) {%>
                                                                                                        <td width="30px" ><a href="javascript:cmdSearchStatusApprove()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Execution Appliaction"></a></td>
                                                                                                        <td width="5px"></td>
                                                                                                        <td class="command" width="240px"><a href="javascript:cmdSearchStatusApprove()">Search All Approve HRD </a></td>

                                                                                                        <td width="30px" ><a href="javascript:cmdSearchExecute()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Execution Appliaction"></a></td>
                                                                                                        <td width="5px"></td>
                                                                                                        <td class="command" width="240px"><a href="javascript:cmdSearchExecute()">Search Execution</a></td> 
                                                                                                        <%} else {%>
                                                                                                        <td width="30px" ></td>
                                                                                                        <td width="5px"></td>
                                                                                                        <td class="command" width="240px"></td> 
                                                                                                        <%}%>
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
