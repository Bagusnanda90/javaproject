<%--
    Document   : src_overtime_report
    Created on : Mar 30, 2012, 11:31:30 AM
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
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_OVERTIME, AppObjInfo.OBJ_PAYROLL_OVERTIME_REPORT);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>



<%
/* for status (maybe)
I_PstDocType i_pstDocType = (I_PstDocType) Class.forName(docTypeClassName).newInstance();
int systemName = I_DocType.SYSTEM_MATERIAL;
//int docType = i_pstDocType.composeDocumentType(systemName,I_DocType.MAT_DOC_TYPE_OPN);
//implement status
I_DocStatus i_status = (I_DocStatus) Class.forName(docStatusClassName).newInstance();
int docType = i_pstDocType.composeDocumentType(systemName,I_DocType.MAT_DOC_TYPE_LMRR);
*/
//test religion

int countReligion = 0;

            try{
                countReligion = PstReligion.getCount(null);
            }catch(Exception E){
                System.out.println("excption "+E.toString());
            }

            Vector vReligionList = new Vector();
            String orderCnt = PstReligion.fieldNames[PstReligion.FLD_RELIGION]+" ASC ";

            try{
                vReligionList = PstReligion.list(0, 0, "", orderCnt);
            }catch(Exception E){
                System.out.println("excption "+E.toString());
            }

            String[] religionId = null;

            religionId = new String[countReligion];

            int max1 = 0;
            for(int j = 0 ; j < countReligion ; j++){

                Religion objReligion = new Religion();
                objReligion = (Religion)vReligionList.get(j);

                String name = "RELIG_"+objReligion.getOID();
                religionId[j] = FRMQueryString.requestString(request,name);
                max1++;
            }

//end test




            long oidCompany = FRMQueryString.requestLong(request, FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_COMPANY_ID]);
            long oidDivision = FRMQueryString.requestLong(request, FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_DIVISION_ID]);
            long oidDepartment = FRMQueryString.requestLong(request, FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_DEPARTMENT_ID]);
            int iCommand = FRMQueryString.requestCommand(request);
            SrcOvertimeReport srcOvertimeReport = new SrcOvertimeReport();
            FrmSrcOvertimeReport frmSrcOvertimeReport = new FrmSrcOvertimeReport();

            if (iCommand == Command.GOTO) {
                frmSrcOvertimeReport = new FrmSrcOvertimeReport(request, srcOvertimeReport);
                frmSrcOvertimeReport.requestEntityObject(srcOvertimeReport);
            } else if(iCommand == Command.BACK){ 
                try{
                    srcOvertimeReport =(SrcOvertimeReport) session.getValue(SessOvertime.SESS_SRC_OVERTIME+"REPORT");
                    if(srcOvertimeReport==null){
                        srcOvertimeReport = new SrcOvertimeReport();
                    }
                  } catch(Exception exc){
                      System.out.println("Exception SrcOvertimeReport"+exc);
                  }
             }
            //update by satrya 2013-08-13
            //jika user melakukan klik refersh broser atau buka kembali browser tanpa memilih koment
              else{
                    srcOvertimeReport = new SrcOvertimeReport();
              }

                       
             session.putValue(SessOvertime.SESS_SRC_OVERTIME, srcOvertimeReport); 
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
        <title>HARISMA - Search Overtime Report</title>
        <script language="JavaScript">

//-------------------------- for Calendar -------------------------
function getThn(){    
    var inname= ds_element.name;    
    if(inname=="<%=FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_REQ_DATE]%>"){
        <%
            out.println(ControlDatePopup.writeDateCaller("frmsrcovertimereport",FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_REQ_DATE]));    
        %>;
} 
if(inname=="<%=FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_REQ_DATE_TO]%>"){
    <%
     out.println(ControlDatePopup.writeDateCaller("frmsrcovertimereport",FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_REQ_DATE_TO]));
    %>
}
}

function getThnStart(){    
    <%
        out.println(ControlDatePopup.writeDateCaller("frmsrcovertimereport",FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_REQ_DATE]));    
    %>
    <%
     out.println(ControlDatePopup.writeDateCaller("frmsrcovertimereport",FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_REQ_DATE_TO]));
    %>
}

function hideObjectForDate(){
   // alert("Hide");
        <%
        /*
            out.println(ControlDatePopup.writeDateHideObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_APPROVAL_STATUS]));
            out.println(ControlDatePopup.writeDateHideObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DOC_STATUS]));
            out.println(ControlDatePopup.writeDateHideObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_APPROVAL_HR_MAN]));
 *      */
        %>
}

function showObjectForDate(){
        //alert("Show");
        <%
        /*
            out.println(ControlDatePopup.writeDateShowObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_APPROVAL_STATUS]));
            out.println(ControlDatePopup.writeDateShowObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_DOC_STATUS]));
            out.println(ControlDatePopup.writeDateHideObj("frmsrcleaveapp", FrmSrcLeaveApp.fieldNames[FrmSrcLeaveApp.FRM_FIELD_APPROVAL_HR_MAN]));
 *      */
        %>
} 
//--------------------------------------------------

            function cmdUpdateDiv(){
                document.frmsrcovertimereport.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcovertimereport.action="src_overtime_report.jsp";
                document.frmsrcovertimereport.submit();
            }
            function cmdUpdateDep(){
                document.frmsrcovertimereport.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcovertimereport.action="src_overtime_report.jsp";
                document.frmsrcovertimereport.submit();
            }


            function cmdAdd(){
                document.frmsrcovertime.command.value="<%=String.valueOf(Command.ADD)%>";
                document.frmsrcovertime.action="overtime.jsp";
                document.frmsrcovertime.submit();
            }

            function cmdSearch(){
                document.frmsrcovertimereport.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmsrcovertimereport.action="overtime_report.jsp";
                document.frmsrcovertimereport.submit();
                /*window.open("overtime_report.jsp",
                "overtimeReport", "height=550,width=1500, status=yes,toolbar=no,menubar=yes,location=no, scrollbars=yes");*/
            }
            
            
            function cmdUpdatePos(){
                document.frmsrcovertimereport.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmsrcovertimereport.action="src_overtime_report.jsp";
                document.frmsrcovertimereport.submit();
            }

            function cmdSearchAndCalc(){
                document.frmsrcovertimereport.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmsrcovertimereport.action="overtime_report.jsp";
                document.frmsrcovertimereport.search_calc.value="<%=String.valueOf(Command.SAVE)%>";
                document.frmsrcovertimereport.submit();
                /*window.open("overtime_report.jsp",
                "overtimeReport", "height=550,width=1500, status=yes,toolbar=no,menubar=yes,location=no, scrollbars=yes");*/
            }



            function cmdHeadCount(){
                document.frmsrcovertime.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmsrcovertime.action="employee_headcount.jsp";
                document.frmsrcovertime.submit();
            }
            

            function cmdSpecialQuery(){
                document.frmsrcovertime.action="specialquery.jsp";
                document.frmsrcovertime.submit();
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
        <link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
        <!-- #EndEditable -->

    </head>

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
<!-- Untuk Calendar-->
<%=ControlDatePopup.writeTable(approot)%>
<!-- End Calendar-->
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
                                                    Overtime &gt; Overtime Search Report<!-- #EndEditable -->
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
                                                                    <table  style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <form name="frmsrcovertimereport" method="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="search_calc" value="0">                                                                                    
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
			<div align="left">Company</div></td>
		<td width="83%"> <%
                                        Vector comp_value = new Vector(1, 1);
                                        Vector comp_key = new Vector(1, 1);
                                        comp_value.add("0");
                                        comp_key.add("--"+dictionaryD.getWord(I_Dictionary.SELECT)+"--");
                                        Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                                        for (int i = 0; i < listComp.size(); i++) {
                                            Company comp = (Company) listComp.get(i);
                                            comp_key.add(comp.getCompany());
                                            comp_value.add(String.valueOf(comp.getOID()));
                                        }
                                %> <%= ControlCombo.draw(FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_COMPANY_ID], "formElemen", null, "" + srcOvertimeReport.getCompanyId(), comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%>
                    </td>
	</tr>
	<tr align="left" valign="top">
		<td width="17%" nowrap>
			<div align="left"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></div></td>
		<td width="83%"> <%
                    Vector div_value = new Vector(1, 1);
                    Vector div_key = new Vector(1, 1);
                    div_value.add("0");
                    div_key.add("ALL ...");
                    String strWhere = PstDivision.fieldNames[PstDivision.FLD_COMPANY_ID] + "=" +  "" + srcOvertimeReport.getCompanyId() ;//oidCompany;
                    Vector listDiv = PstDivision.list(0, 0, strWhere, PstDivision.fieldNames[PstDivision.FLD_DIVISION]);
                    for (int i = 0; i < listDiv.size(); i++) {
                        Division div = (Division) listDiv.get(i);
                        div_key.add(div.getDivision());
                        div_value.add(String.valueOf(div.getOID()));
                    }
         //update by satrya 2013-08-13
         //jika user memilih select kembali
         if(srcOvertimeReport!=null && srcOvertimeReport.getDivisionId()==0){
             srcOvertimeReport.setDepartmentId(0); 
         }
            %> <%= ControlCombo.draw(FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_DIVISION_ID], "formElemen", null, "" + srcOvertimeReport.getDivisionId(), div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"")%>
                     </tr>
	<tr align="left" valign="top">
		<td width="17%" nowrap>
			<div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
		<td width="83%"><%
                    Vector dept_value = new Vector(1, 1);
                    Vector dept_key = new Vector(1, 1);
                    dept_value.add("0");
                    dept_key.add("ALL ...");
                    String strWhereDept = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + "" + srcOvertimeReport.getDivisionId(); //oidDivision;
                    Vector listDept = PstDepartment.list(0, 0, strWhereDept, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                    for (int i = 0; i < listDept.size(); i++) {
                        Department dept = (Department) listDept.get(i);
                        dept_key.add(dept.getDepartment());
                        dept_value.add(String.valueOf(dept.getOID()));
                    }
         //update by satrya 2013-08-13
         //jika user memilih select kembali
         if(srcOvertimeReport!=null && srcOvertimeReport.getDepartmentId()==0){
             srcOvertimeReport.setSectionId(0); 
         }
            %> <%= ControlCombo.draw(FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_DEPARTMENT_ID], "formElemen", null, "" + srcOvertimeReport.getDepartmentId(), dept_value, dept_key, "onChange=\"javascript:cmdUpdatePos()\"")%>                                                                                                                               </td>
        </tr>
        <tr align="left" valign="top">
		<td width="17%" nowrap>
			<div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div></td>
		<td width="83%"> <%
         //update by satrya 2013-08-13
         //jika user memilih select kembali
         if(oidDepartment==0 && srcOvertimeReport!=null && srcOvertimeReport.getDepartmentId()!=0){
             oidDepartment = srcOvertimeReport.getDepartmentId();
         }
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
			%> <%= ControlCombo.draw(FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_SECTION_ID], "formElemen", null, "" + srcOvertimeReport.getSectionId(), sec_value, sec_key, " onkeydown=\"javascript:fnTrapKD()\"")%></td>

	</tr>
	<tr align="left" valign="top">
		<td width="17%" nowrap>
			<div align="left">Employee </div></td>
                <td width="83%"> 
                    Payroll <input type="text" name="<%=FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_PAYROLL]%>" value="<%=srcOvertimeReport.getPayroll() %>">
                    Fullname <input type="text" name="<%=FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_FULLNAME]%>" value="<%=srcOvertimeReport.getFullname() %>">
                </td>
	</tr>
        
	<tr align="left" valign="top">
		<td width="17%" nowrap>
			<div align="left">Overtime Date</div></td>
		<td width="83%"> from <%=ControlDatePopup.writeDate(FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_REQ_DATE],srcOvertimeReport.getRequestDate(),"getThn()") %>
                    to <%=ControlDatePopup.writeDate(FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_REQ_DATE_TO],srcOvertimeReport.getRequestDateTo(),"getThn()")%>
                </td>
	</tr>
	
	<tr align="left" valign="top">
		<td width="17%" nowrap>
			<div align="left">Status Doc.</div></td>
		<td width="83%"> <%
                                     int[] exceptStatus ={I_DocStatus.DOCUMENT_STATUS_CANCELLED};
                                      Vector status_value = OvertimeReport.getStatusIndexString(exceptStatus);
                                      Vector status_key = OvertimeReport.getStatusAttString(exceptStatus);                                      
                                      ControlCheckBox  ctrlChkbox= new ControlCheckBox();
                                    %>
                               <%= ctrlChkbox.draw(FrmSrcOvertimeReport.fieldNames[FrmSrcOvertimeReport.FRM_FIELD_STATUS_DOC],status_value, status_key, srcOvertimeReport.getStatusDoc() ) %>
                                        </td>
               
                        
	</tr>

        <tr align="left" valign="top">
		<td width="17%" nowrap>
			<div align="left">Religion</div></td>
		<td width="83%"> <%
                                Vector vReligion = new Vector();

                                try{

                                    String orderRel = PstReligion.fieldNames[PstReligion.FLD_RELIGION]+" ASC ";

                                    vReligion = PstReligion.list(0, 0, "", orderRel);

                                }catch(Exception E){
                                    System.out.println("Exception "+E.toString());
                                }

                                for(int iReligion = 0 ; iReligion < vReligion.size() ; iReligion++){
                                    Religion religion = new Religion();
                                    religion = (Religion)vReligion.get(iReligion);
                                    String nameInp = "RELIG_"+religion.getOID();

                                    if(religionId[iReligion].equals("1")){

                                    %>
                                        <input name=<%=nameInp%> type="checkbox" checked value=1 > <%=religion.getReligion() %> &nbsp;&nbsp;
                                    <%
                                    }else{
                                    %>
                                        <input name=<%=nameInp%> type="checkbox" value=1 > <%=religion.getReligion() %> &nbsp;&nbsp;
                                    <%
                                    }
                                 }

                            %>
                </td>
	</tr>
	
	<tr align="left" valign="top">
		<td width="17%"> <div align="left"></div></td>
		<td width="83%">
			<table width="50%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td ><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
					<td ><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
					<td  class="command" nowrap="true"><a href="javascript:cmdSearch()">View Detail</a></td>
					<td ><img src="<%=approot%>/images/spacer.gif" width="19" height="8"></td>
					<td ><a href="javascript:cmdSearchAndCalc()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
					<td ><img src="<%=approot%>/images/spacer.gif" width="6" height="1"></td>
					<td  class="command" nowrap="true"><a href="javascript:cmdSearchAndCalc()">View & Calculate Ov.Index</a></td>
					<td ><img src="<%=approot%>/images/spacer.gif" width="19" height="8"></td>					
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
             <script lang="Javascript" >
                            //getThnTo();
                            getThnStart();                            
                    </script>
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

