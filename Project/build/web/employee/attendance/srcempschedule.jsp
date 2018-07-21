
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%
            /* 
             * Page Name  		:  srcempschedule.jsp
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

<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>

<%@ page import = "com.dimata.harisma.session.attendance.*" %>

<%@ page import = "com.dimata.harisma.entity.employee.*" %>


<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_WORKING_SCHEDULE);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
            /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//            boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//            boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//            boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
AppUser newAppUser = new AppUser();
try{
 newAppUser = PstAppUser.fetch(appUserSess.getOID()); 
}catch (Exception e){}
%>

<!-- Jsp Block -->
<%!
    public String getSectionLink(String sectionId){
        String str = "";
        try{
            Section section = PstSection.fetchExc(Long.valueOf(sectionId));
            str = section.getSection();
            return str;
        } catch(Exception e){
            System.out.println(e);
        }
        return str;
    }
%>
<%
// pengecekan apakah user yang login adalah sekretaris atau tidak

            /* Pengecekan untuk schedule yang berubah */

            String tgl = "2010-11-20 13:30:00";

            Date dateEmp = Formater.formatDate(tgl, "yyyy-MM-dd HH:mm:ss");

            boolean schUpdate = false;

            schUpdate = SessEmpSchedule.getstatusSchedule(emplx.getOID(), PstPosition.UPDATE_SCHEDULE_BEFORE_TIME, dateEmp);

            int countLevel = 0;

            try{
                countLevel = PstLevel.getCount(null);
            }catch(Exception E){
                System.out.println("excption "+E.toString());
            }

            Vector vLevelList = new Vector();
            String orderCnt = PstLevel.fieldNames[PstLevel.FLD_LEVEL]+" ASC ";

            try{
                vLevelList = PstLevel.list(0, 0, "", orderCnt);
            }catch(Exception E){
                System.out.println("excption "+E.toString());
            }

            String[] levelId = null;

            levelId = new String[countLevel];

            int max1 = 0;
            for(int j = 0 ; j < countLevel ; j++){

                Level objLevel = new Level();
                objLevel = (Level)vLevelList.get(j);
                
                String name = "LEVL_"+objLevel.getOID();
                levelId[j] = FRMQueryString.requestString(request,name);
                max1++;
            }


            boolean isSecretaryLogin = (positionType >= PstPosition.LEVEL_SECRETARY) ? true : false;
            long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
            long sdmDivisionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_SDM_DIVISION)));
            boolean isHRDLogin = (hrdDepartmentOid == departmentOid || sdmDivisionOid == divisionOid) ? true : false;
            
            long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
            boolean isEdpLogin = false;
            if (edpSectionOid>0){
                isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
            }
            boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;

            int iCommand = FRMQueryString.requestCommand(request);
            /* Update by Hendra Putu | 20150217 */
            long companyId = FRMQueryString.requestLong(request, "company_id");
            long divisionId = FRMQueryString.requestLong(request, "division_id");
            long departmentId = FRMQueryString.requestLong(request, "department_id");
            
            
            long lDepartmentOid = FRMQueryString.requestLong(request, FrmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_DEPARTMENT]);
            SrcEmpSchedule srcEmpSchedule = new SrcEmpSchedule();
            FrmSrcEmpSchedule frmSrcEmpSchedule = new FrmSrcEmpSchedule();
            frmSrcEmpSchedule = new FrmSrcEmpSchedule(request, srcEmpSchedule);

             String ClientName = "";
            try {
                ClientName = String.valueOf(PstSystemProperty.getValueByName("CLIENT_NAME"));//menambahkan system properties
            } catch (Exception e) {
                System.out.println("Exeception ATTANDACE_ON_NO_SCHEDULE:" + e);
            }
            
            if(iCommand==Command.GOTO){
                frmSrcEmpSchedule = new FrmSrcEmpSchedule(request, srcEmpSchedule);
                frmSrcEmpSchedule.requestEntityObject(srcEmpSchedule);
            }
            
            if (iCommand == Command.BACK) {
                try {
                    if (session.getValue(SessEmpSchedule.SESS_SRC_EMPSCHEDULE) != null) {
                        srcEmpSchedule = (SrcEmpSchedule) session.getValue(SessEmpSchedule.SESS_SRC_EMPSCHEDULE);
                    }
                } catch (Exception e) {
                    srcEmpSchedule = new SrcEmpSchedule();
                }
            }
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Working Schedule</title>
        <script language="JavaScript">
            function compChange(val) 
            {
                document.frmsrcempschedule.command.value = "<%=Command.GOTO%>";
                document.frmsrcempschedule.company_id.value = val;
                document.frmsrcempschedule.division_id.value = "0";
                document.frmsrcempschedule.department_id.value = "0";
                document.frmsrcempschedule.action = "srcempschedule.jsp";
                document.frmsrcempschedule.submit();
            }
            function divisiChange(val) 
            {
                document.frmsrcempschedule.command.value = "<%=Command.GOTO%>";
                document.frmsrcempschedule.division_id.value = val;
                document.frmsrcempschedule.action = "srcempschedule.jsp";
                document.frmsrcempschedule.submit();
            }
            function deptChange(val) 
            {
                document.frmsrcempschedule.command.value = "<%=Command.GOTO%>";	
                document.frmsrcempschedule.department_id.value = val;
                document.frmsrcempschedule.action = "srcempschedule.jsp";
                document.frmsrcempschedule.submit();
            }
            
            function cmdAdd(){
                document.frmsrcempschedule.command.value="<%=Command.ADD%>";
                document.frmsrcempschedule.action="empschedule_edit.jsp";
                document.frmsrcempschedule.submit();
            }
            
          function SetAllCheckBoxesCat(FormName, FieldName, CheckValue)
            {
                    if(!document.forms[FormName])
                            return;
                    var objCheckBoxes = document.forms[FormName].elements[FieldName];
                    if(!objCheckBoxes)
                            return;
                    var countCheckBoxes = objCheckBoxes.length;
                    if(!countCheckBoxes)
                            objCheckBoxes.checked = CheckValue;
                    else
                            // set the check value for all check boxes
                                    for(var i = 0; i < countCheckBoxes; i++)
                                    objCheckBoxes[i].checked = CheckValue;
            }
            
            function SetAllCheckBoxes(FormName, FieldName, CheckValue, nilai)
            {
                <%
                Vector vLevel1 = new Vector();

                try{
                    String orderLev = PstLevel.fieldNames[PstLevel.FLD_LEVEL]+" ASC ";
                    vLevel1 = PstLevel.list(0, 0, "", orderLev);
                }catch(Exception E){
                    System.out.println("Exception "+E.toString());
                }
                 
                for(int iLevel = 0 ; iLevel < vLevel1.size() ; iLevel++){
                    Level level = new Level();
                    level = (Level)vLevel1.get(iLevel);
                    long levId = level.getOID();
                %>
                                  for(var i = 0; i < nilai; i++){
                                  var objCheckBoxes = document.forms[FormName].elements[FieldName+<%=levId%>];
                                      objCheckBoxes.checked = CheckValue;    
                                  }
                 <% } %>                   
            }
            
            function cmdSearch(){ 
                document.frmsrcempschedule.command.value="<%=Command.LIST%>";
                document.frmsrcempschedule.action="empschedule_list.jsp";
                document.frmsrcempschedule.submit();
            }
            
            function cmdImport(){ 
                document.frmsrcempschedule.action="<%=approot%>/system/excel_up/up_work_schedule.jsp";
                document.frmsrcempschedule.submit();
            }
            
            function fnTrapKD(){               
                if (event.keyCode == 13) {
                    document.all.aSearch.focus();
                    cmdSearch();
                }
            }
            
            function cmdRemoveDouble(){
                
                window.open("<%=approot%>/removedouble_sch.jsp");	
                    
            }
                
                //-------------- script control line -------------------
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
                                                    Attendance &gt; Working Schedule Search<!-- #EndEditable --> 
                                            </strong></font>
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td class="tablecolor"  style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr> 
                                                                <td valign="top"> 
                                                                    <table  style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr> 
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" --> 
                                                                                <form name="frmsrcempschedule" method="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="hidden_goto_dept" value="<%=lDepartmentOid%>">									  									  
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr align="left" valign="top"> 
                                                                                            <td valign="middle" colspan="2"> 
                                                                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr> 
                                                                                                        <td width="1%">&nbsp;</td>
                                                                                                        <td width="99%"> 
                                                                                                            <table width="100%" border="0" cellspacing="2" cellpadding="2" >
                                                                                                                <tr> 
                                                                                                                    <td width="2%">&nbsp;</td>
                                                                                                                    <td width="7%">&nbsp;</td>
                                                                                                                    <td width="89%">&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr> 
                                                                                                                    <td width="2%">&nbsp;</td>
                                                                                                                    <td width="7%"> 
                                                                                                                        <div align="left">Payroll Number</div>
                                                                                                                    </td>
                                                                                                                    <td width="89%"> 
                                                                                                                        <input type="text" name="<%=frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_EMPNUMBER] %>"  value="<%= srcEmpSchedule.getEmpNumber() %>" class="elemenForm" onkeydown="javascript:fnTrapKD()" size="40">
                                                                                                                    </td>
                                                                                                                    <script language="javascript">
                                                                                                                        document.frmsrcempschedule.<%=frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_EMPNUMBER]%>.focus();
                                                                                                                    </script>
                                                                                                                </tr>
                                                                                                                <tr> 
                                                                                                                    <td width="2%">&nbsp;</td>
                                                                                                                    <td width="7%"> 
                                                                                                                        <div align="left">Name</div>
                                                                                                                    </td>
                                                                                                                    <td width="89%"> 
                                                                                                                        <input type="text" name="<%=frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_EMPLOYEE] %>"  value="<%= srcEmpSchedule.getEmployee() %>" class="elemenForm" onkeydown="javascript:fnTrapKD()" size="40">
                                                                                                                    </td>
                                                                                                                    <script language="javascript">
                                                                                                                        document.frmsrcempschedule.<%=frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_EMPLOYEE]%>.focus();
                                                                                                                    </script>
                                                                                                                </tr>
                                                                                                                <tr> 
                                                                                                                    <td width="2%">&nbsp;</td>
                                                                                                                    <td width="7%"> 
                                                                                                                        <div align="left">Period</div>
                                                                                                                    </td>
                                                                                                                    <td width="89%"> 
                                                                                                                        <%
                                                                                                                            Vector period_value = new Vector(1, 1);
                                                                                                                            Vector period_key = new Vector(1, 1);
                                                                                                                            String selectValuePeriod = (String) srcEmpSchedule.getPeriod();
                                                                                                                            period_value.add("0");
                                                                                                                            period_key.add("select...");
                                                                                                                            Vector listPeriod = new Vector(1, 1);
                                                                                                                            //listPeriod = PstPeriod.listAll();
                                                                                                                            listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");
                                                                                                                            for (int i = 0; i < listPeriod.size(); i++) {
                                                                                                                                Period period = (Period) listPeriod.get(i);
                                                                                                                                period_key.add(period.getPeriod());
                                                                                                                                period_value.add(String.valueOf(period.getOID()));
                                                                                                                            }
                                                                                                                        %>
                                                                                                                        <%= ControlCombo.draw(frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_PERIOD], null, selectValuePeriod, period_value, period_key, " onkeydown=\"javascript:fnTrapKD()\"") %> 
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <%-- <tr> 
                                                      <td width="37%"> 
                                                        <div align="right">Schedule 
                                                          : </div>
                                                      </td>
                                                      <td width="63%"> 
                                                        <%
                                                 Vector schedule_value = new Vector(1,1);
                                                 Vector schedule_key = new Vector(1,1);
                                                 String selectValue = (String)srcEmpSchedule.getSchedule();
                                                schedule_value.add("0");
                                                schedule_key.add("select ...");
                        Vector listSchedule = new Vector(1,1);
                        listSchedule = PstScheduleSymbol.listAll();
                        for (int i = 0; i < listSchedule.size(); i++) {
                            ScheduleSymbol schedule = (ScheduleSymbol) listSchedule.get(i);
                            schedule_key.add(schedule.getSymbol());
                            schedule_value.add(String.valueOf(schedule.getOID()));
                        }
                                                 %>
                                                        <%= ControlCombo.draw(frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_SCHEDULE], null, selectValue, schedule_value, schedule_key) %> 
                                                      </td>
                                                                                                                </tr> --%>
                                                                                                                <%
                                                                                                                /*
                                                                                                                     * Description : get value Company, Division, Department, and Section
                                                                                                                     * Date : 2015-02-17
                                                                                                                * Author : Hendra Putu
                                                                                                                */
                                                                                                                // List Company
                                                                                                                    Vector comp_value = new Vector(1, 1);
                                                                                                                    Vector comp_key = new Vector(1, 1);
                                                                                                                    comp_value.add("0");
                                                                                                                    comp_key.add("-select-");
                                                                                                                    String comp_selected = "";
                                                                                                                // List Division
                                                                                                                    Vector div_value = new Vector(1, 1);
                                                                                                                    Vector div_key = new Vector(1, 1);
                                                                                                                    String whereDivision = "COMPANY_ID = "+companyId;
                                                                                                                    div_value.add("0");
                                                                                                                    div_key.add("-select-");
                                                                                                                    String div_selected = "";
                                                                                                                // List Department
                                                                                                                    Vector depart_value = new Vector(1,1);
                                                                                                                    Vector depart_key = new Vector(1,1);
                                                                                                                    String whereComp = ""+companyId;
                                                                                                                    String whereDiv = "" +divisionId;
                                                                                                                    depart_value.add("0");
                                                                                                                    depart_key.add("-select-");
                                                                                                                    String depart_selected = "";
                                                                                                                // List Section
                                                                                                                    Vector section_value = new Vector(1,1);
                                                                                                                    Vector section_key = new Vector(1,1);
                                                                                                                    Vector section_v = new Vector();
                                                                                                                    Vector section_k = new Vector();
                                                                                                                    String whereSection = "DEPARTMENT_ID = "+departmentId;
                                                                                                                    section_value.add("0");
                                                                                                                    section_key.add("-select-");
                                                                                                                    section_v.add("0");
                                                                                                                    section_k.add("-select-");
                                                                                                                /* List variabel if not isHRDLogin || isEdpLogin || isGeneralManager */
                                                                                                                    
                                                                                                                    String strComp = "";
                                                                                                                    String strCompId = "0";
                                                                                                                String strDivisi = "";
                                                                                                                String strDivisiId = "0";
                                                                                                                String strDepart = "";
                                                                                                                String strDepartId = "0";
                                                                                                                    String strSection = "";
                                                                                                                    String strSectionId = "0";
                                                                                                                if(isHRDLogin || isEdpLogin || isGeneralManager){
                                                                                                                        
                                                                                                                    Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                                                                                                                    for (int i = 0; i < listComp.size(); i++) {
                                                                                                                        Company comp = (Company) listComp.get(i);
                                                                                                                        if (comp.getOID() == companyId){
                                                                                                                          comp_selected = String.valueOf(companyId);
                                                                                                                        }
                                                                                                                        comp_key.add(comp.getCompany());
                                                                                                                        comp_value.add(String.valueOf(comp.getOID()));
                                                                                                                        }
                                                                                                                    
                                                                                                                    Vector listDiv = PstDivision.list(0, 0, whereDivision, " DIVISION ");
                                                                                                                    if (listDiv != null && listDiv.size() > 0) {
                                                                                                                        for (int i = 0; i < listDiv.size(); i++) {
                                                                                                                            Division division = (Division) listDiv.get(i);
                                                                                                                            if (division.getOID() == divisionId){
                                                                                                                                div_selected = String.valueOf(divisionId);
                                                                                                                    }
                                                                                                                            div_key.add(division.getDivision());
                                                                                                                            div_value.add(String.valueOf(division.getOID()));
                                                                                                                        }
                                                                                                                    }
                                                                                                                            
                                                                                                                    Vector listDepart = PstDepartment.listDepartmentVer1(0, 0, whereComp, whereDiv);
                                                                                                                    if (listDepart != null && listDepart.size() > 0) {
                                                                                                                        for (int i = 0; i < listDepart.size(); i++) {
                                                                                                                            Department depart = (Department) listDepart.get(i);
                                                                                                                            if (depart.getOID()==departmentId){
                                                                                                                                depart_selected = String.valueOf(departmentId);
                                                                                                                        }
                                                                                                                            depart_key.add(depart.getDepartment());
                                                                                                                            depart_value.add(String.valueOf(depart.getOID()));
                                                                                                                        }
                                                                                                                    }
                                                                                                                    
                                                                                                                    Vector listSection = PstSection.list(0, 0, whereSection, "SECTION");
                                                                                                                    if (listSection != null && listSection.size() > 0) {
                                                                                                                        for (int i = 0; i < listSection.size(); i++) {
                                                                                                                            Section section = (Section) listSection.get(i);
                                                                                                                            section_key.add(section.getSection());
                                                                                                                            section_value.add(String.valueOf(section.getOID()));
                                                                                                                            String sectionLink = section.getSectionLinkTo();
                                                                                                                            if ((sectionLink != null) && sectionLink.length()>0){
                            
                                                                                                                                for (String retval : sectionLink.split(",")) {
                                                                                                                                    section_value.add(retval);
                                                                                                                                    section_key.add(getSectionLink(retval));
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
            
                                                                                                                    } else {
                                                                                                                   // for Company and Division
                                                                                                                   if (emplx.getDivisionId() > 0){
                                                                                                                      Division empDiv = PstDivision.fetchExc(emplx.getDivisionId());
                                                                                                                      Company empComp = PstCompany.fetchExc(empDiv.getCompanyId());
                                                                                                                      strComp = empComp.getCompany();
                                                                                                                      strCompId = String.valueOf(empComp.getOID());
                                                                                                                      strDivisi = empDiv.getDivision();
                                                                                                                      strDivisiId = String.valueOf(empDiv.getOID());
                                                                                                                    }
                                                                                                                   // for Department
                                                                                                                   if (emplx.getDepartmentId() > 0){
                                                                                                                       Department empDepart = PstDepartment.fetchExc(emplx.getDepartmentId());
                                                                                                                       strDepart = empDepart.getDepartment();
                                                                                                                       strDepartId = String.valueOf(empDepart.getOID());
                                                                                                                   }
                                                                                                                   // for Section
                                                                                                                   if (emplx.getSectionId() > 0){
                                                                                                                       try {
                                                                                                                           Section empSection = PstSection.fetchExc(emplx.getSectionId());
                                                                                                                            strSection = empSection.getSection();
                                                                                                                            strSectionId = String.valueOf(empSection.getOID());

                                                                                                                            section_v.add(String.valueOf(empSection.getOID()));
                                                                                                                            section_k.add(empSection.getSection());
                                                                                                                            String sectionLink = empSection.getSectionLinkTo();
                                                                                                                             if ((sectionLink != null) && sectionLink.length()>0){

                                                                                                                                 for (String retval : sectionLink.split(",")) {
                                                                                                                                     section_v.add(retval);
                                                                                                                                     section_k.add(getSectionLink(retval));
                                                                                                                                 }
                                                                                                                             }
                                                                                                                       } catch(Exception e){
                                                                                                                           System.out.println("terjadi kesalahan pada Section");
                                                                                                                       }
                                                                                                                       
                                                                                                                   }
                                                                                                                }
                                                                                                                %>
                                                                                                                <tr> 
                                                                                                                    <td width="2%">&nbsp;</td>
                                                                                                                    <td width="7%"> 
                                                                                                                        <div align="left">Company</div>
                                                                                                                    </td>
                                                                                                                    <td width="89%"> 
                                                                                                                        <%
                                                                                                                        if(isHRDLogin || isEdpLogin || isGeneralManager){
                                                                                                                        %>
                                                                                                                        <input type="hidden" name="company_id" value="<%=companyId%>" />
                                                                                                                        <%= ControlCombo.draw(frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_COMPANY], "formElemen", null, comp_selected, comp_value, comp_key, " onChange=\"javascript:compChange(this.value)\"") %>
                                                                                                                        <%
                                                                                                                        } else {
                                                                                                                        %>
                                                                                                                        <input type="hidden" name="company_id" value="<%=strCompId%>" />
                                                                                                                        <input type="hidden" name="<%=frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_COMPANY]%>" value="<%=strCompId%>" />
                                                                                                                        <input type="text" name="company_nm" disabled="disabled" value="<%=strComp%>" size="70" />
                                                                                                                        <%
                                                                                                                        }
                                                                                                                        %>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr> 
                                                                                                                    <td width="2%">&nbsp;</td>
                                                                                                                    <td width="7%"> 
                                                                                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></div>
                                                                                                                    </td>
                                                                                                                    <td width="89%">
                                                                                                                        
                                                                                                                        <%
                                                                                                                        if(isHRDLogin || isEdpLogin || isGeneralManager){
                                                                                                                        %>
                                                                                                                        <input type="hidden" name="division_id" value="<%=divisionId%>" />
                                                                                                                        <%= ControlCombo.draw(frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_DIVISION], "formElemen", null, div_selected, div_value, div_key, " onChange=\"javascript:divisiChange(this.value)\"") %>
                                                                                                                        <%
                                                                                                                        } else {
                                                                                                                        %>
                                                                                                                        <input type="hidden" name="division_id" value="<%=strDivisiId%>" />
                                                                                                                        <input type="hidden" name="<%=frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_DIVISION]%>" value="<%=strDivisiId%>" />
                                                                                                                        <input type="text" name="division_nm" disabled="disabled" value="<%=strDivisi%>" size="70" />
                                                                                                                        <%
                                                                                                                        }
                                                                                                                        %>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr> 
                                                                                                                    <td width="2%">&nbsp;</td>
                                                                                                                    <td width="7%"> 
                                                                                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                                                                                    </td>
                                                                                                                    <td width="89%"> 
                                                                                                                        <%
                                                                                                                        if(isHRDLogin || isEdpLogin || isGeneralManager){
                                                                                                                        %>
                                                                                                                        <input type="hidden" name="department_id" value="<%=departmentId%>" />	
                                                                                                                        <%= ControlCombo.draw(frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_DEPARTMENT], "formElemen", null, depart_selected, depart_value, depart_key, " onChange=\"javascript:deptChange(this.value)\"") %>
                                                                                                                        <%
                                                                                                                        } else {
                                                                                                                        
                                                                                                                                if (emplx.getOID() > 0 && (ClientName.equals("RAMA_RESTO"))) {
                                                                                                                                    if ((!(isHRDLogin || isEdpLogin || isGeneralManager || isDirector)) && (ClientName.equals("RAMA_RESTO")))  {

                                                                                                                                        depart_value = new Vector();
                                                                                                                                        depart_key = new Vector();
                                                                                                                                        String whe = PstDepartment.TBL_HR_DEPARTMENT +"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + " = " +emplx.getDivisionId() + " OR "+ PstDepartment.TBL_HR_DEPARTMENT +"."+PstDepartment.fieldNames[PstDepartment.FLD_JOIN_TO_DEPARTMENT_ID] + " = " +emplx.getDivisionId() ;
                                                                                                                                        Vector listdepart = PstDepartment.list(0, 0, whe, "DEPARTMENT");
                                                                                                                                        for (int ix = 0; ix < listdepart.size(); ix++){
                                                                                                                                            Department departmentX = (Department) listdepart.get(ix);
                                                                                                                                            depart_value.add(""+departmentX.getOID());
                                                                                                                                            depart_key.add(departmentX.getDepartment());
                                                                                                                                        }


                                                                                                                                    }
                                                                                                                                    
                                                                                                                                    String depart_sel = ""+departmentId;
                                                                                                                                    %>
                                                                                                                                    <input type="hidden" name="department_id" value="<%=departmentId%>" />	
                                                                                                                                    <%= ControlCombo.draw(frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_DEPARTMENT], "formElemen", null, depart_sel, depart_value, depart_key, " onChange=\"javascript:deptChange(this.value)\"") %>
                                                                                                                                    <%
                                                                                                                                    
                                                                                                                                } else {
                                                                                                                                    %>
                                                                                                                                    <input type="hidden" name="department_id" value="<%=strDepartId%>" />
                                                                                                                                    <input type="hidden" name="<%=frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_DEPARTMENT]%>" value="<%=strDepartId%>" />
                                                                                                                                    <input type="text" name="department_nm" disabled="disabled" value="<%=strDepart%>" size="70" />
                                                                                                                                    <% 
                                                                                                                                }
                                                                                                                        }
                                                                                                                        %>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                
                                                                                                                <tr> 
                                                                                                                    <td width="2%">&nbsp;</td>
                                                                                                                    <td width="7%"> 
                                                                                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div>
                                                                                                                    </td>
                                                                                                                    <td width="89%"> 
                                                                                                                        <%
                                                                                                                        if(isHRDLogin || isEdpLogin || isGeneralManager){                                                               
                                                                                                                        %> 
                                                                                                                        <%= ControlCombo.draw(frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_SECTION], "formElemen", null, "" + srcEmpSchedule.getSection(), section_value, section_key, "") %>  
                                                                                                                        <%
                                                                                                                        } else {
                                                                                                                            if ((!(isHRDLogin || isEdpLogin || isGeneralManager || isDirector)) && (ClientName.equals("PEPITO")))  {

                                                                                                                                        section_v = new Vector();
                                                                                                                                        section_k = new Vector();
                                                                                                                                            section_v.add("0");
                                                                                                                                            section_k.add("select");
                                                                                                                                        String whe = PstSection.TBL_HR_SECTION +"."+PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " +emplx.getDepartmentId()  ;
                                                                                                                                        Vector listSection = PstSection.list(0, 0, whe, "SECTION");
                                                                                                                                        for (int ix = 0; ix < listSection.size(); ix++){
                                                                                                                                            Section sectionX = (Section) listSection.get(ix);
                                                                                                                                            section_v.add(""+sectionX.getOID());
                                                                                                                                            section_k.add(sectionX.getSection());
                                                                                                                                        }


                                                                                                                                    }
                                                                                                                        %>
                                                                                                                        <%= ControlCombo.draw(frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_SECTION], "formElemen", null, ""+srcEmpSchedule.getSection(), section_v, section_k, "") %> 
                                                                                                                        <%
                                                                                                                        }
                                                                                                                        %>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr> 
                                                                                                                    <td width="2%">&nbsp;</td>
                                                                                                                    <td width="7%"> 
                                                                                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.POSITION) %></div>
                                                                                                                    </td>
                                                                                                                    <td width="89%"> 
                                                                                                                        <%
                                                                                                                            Vector pos_value = new Vector(1, 1);
                                                                                                                            Vector pos_key = new Vector(1, 1);
                                                                                                                            pos_value.add("0");
                                                                                                                            pos_key.add("select ...");
                                                                                                                            Vector listPos = PstPosition.list(0, 0, "", " POSITION ");
                                                                                                                            for (int i = 0; i < listPos.size(); i++) {
                                                                                                                                Position pos = (Position) listPos.get(i);
                                                                                                                                pos_key.add(pos.getPosition());
                                                                                                                                pos_value.add(String.valueOf(pos.getOID()));
                                                                                                                            }
                                                                                                                        %>
                                                                                                                        <%= ControlCombo.draw(frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_POSITION], "formElemen", null, "" + srcEmpSchedule.getPosition(), pos_value, pos_key, " onkeydown=\"javascript:fnTrapKD()\"") %>	
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="2%">&nbsp;</td>
                                                                                                                    <td width="7%">
                                                                                                                        <div align="left">Level</div>
                                                                                                                    </td>
                                                                                                                    <td width="89%">
                                                                                                                        <%
                                                                                                                            
                                                                                                                            Vector vLevel = new Vector();

                                                                                                                            try{

                                                                                                                                String orderLev = PstLevel.fieldNames[PstLevel.FLD_LEVEL]+" ASC ";

                                                                                                                                vLevel = PstLevel.list(0, 0, "", orderLev);

                                                                                                                            }catch(Exception E){
                                                                                                                                System.out.println("Exception "+E.toString());
                                                                                                                            }
                                                                                                                            for(int iLevel = 0 ; iLevel < vLevel.size() ; iLevel++){
                                                                                                                                Level level = new Level();
                                                                                                                                level = (Level)vLevel.get(iLevel);
                                                                                                                                String nameInp = "LEVL_"+level.getOID();
                                                                                                                                if(levelId[iLevel].equals("1")){

                                                                                                                                %>
                                                                                                                                    <input name=<%=nameInp%> type="checkbox" checked value=1 > <%= level.getLevel() %> &nbsp;&nbsp;
                                                                                                                                <%
                                                                                                                                }else{
                                                                                                                                %>
                                                                                                                                    <input name=<%=nameInp%> type="checkbox" value=1 > <%= level.getLevel() %> &nbsp;&nbsp;
                                                                                                                                <%
                                                                                                                                }
                                                                                                                             }                                                                                                                               
                                                                                                                            
                                                                                                                        %>
                                                                                                                        <a href="Javascript:SetAllCheckBoxes('frmsrcempschedule','LEVL_', true,'<%=vLevel.size()%>')">Select All</a> ||
                                                                                                                        <a href="Javascript:SetAllCheckBoxes('frmsrcempschedule','LEVL_', false,'<%=vLevel.size()%>')">Deselect All</a>
                                                                                                                       
                                                                                                                     </td>
                                                                                                                </tr>
                                                                                                                <!-- Update by Hendra Putu | 20150217 -->
                                                                                                                <tr>
                                                                                                                    <td width="2%">&nbsp;</td>
                                                                                                                    <td width="7%">
                                                                                                                        <div align="left"><%=dictionaryD.getWord(I_Dictionary.CATEGORY) %></div>
                                                                                                                    </td>
                                                                                                                    <td width="89%">
                                                                                                                        <%
                                                                                                                        Vector listCategory = new Vector(1,1);
                                                                                                                        listCategory = PstEmpCategory.list(0, 0, "", "");
                                                                                                                        for(int c=0; c<listCategory.size(); c++){
                                                                                                                            EmpCategory empCategory = (EmpCategory)listCategory.get(c);
                                                                                                                            //String nameCat = "CAT_"+empCategory.getOID();
                                                                                                                            %>
                                                                                                                            <input name="emp_category" type="checkbox" value="<%=empCategory.getOID()%>" > <%=empCategory.getEmpCategory()%> &nbsp;&nbsp;
                                                                                                                            <%
                                                                                                                        }
                                                                                                                        %>
                                                                                                                        <a href="Javascript:SetAllCheckBoxesCat('frmsrcempschedule','emp_category', true)">Select All</a> ||
                                                                                                                        <a href="Javascript:SetAllCheckBoxesCat('frmsrcempschedule','emp_category', false)">Deselect All</a>
                                                                                                                       
                                                                                                                    </td>
                                                                                                                </tr>

                                                              
                                                                                                                 <tr>
                                                                                                                    <td width="2%">&nbsp;</td>
                                                                                                                    <td width="7%"><%=dictionaryD.getWord(I_Dictionary.RESIGNED_STATUS)%> :</td>
                                                                                                                    <td width="89%"><input type="radio" name="<%=frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_RESIGNED]%>" value="0" checked>
                                                                                                                        <%=dictionaryD.getWord("NO")%> 
                                                                                                                        <input type="radio" name="<%=frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_RESIGNED]%>" value="1">
                                                                                                                        <%=dictionaryD.getWord("YES")%> 
                                                                                                                        <input type="radio" name="<%=frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_RESIGNED]%>" value="2">
                                                                                                                        <%=dictionaryD.getWord("ALL")%> </td>
                                                                                                                </tr>
                                                                                                                
                                                                                                               <!-- update by satrya 2013-12-06 -->
                                                                                                                <tr>
                                                                                                                    <td width="2%">&nbsp;</td>
                                                                                                                    <td width="7%">Sort By:</td>
                                                                                                                    <td width="89%"><%= ControlCombo.draw(frmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_SORT_BY],"formElemen",null, ""+srcEmpSchedule.getSortBy(), FrmSrcEmpSchedule.getOrderValue(), FrmSrcEmpSchedule.getOrderKey(), " onkeydown=\"javascript:fnTrapKD()\"") %></td>
                                                                                                                </tr>
                                                                                                                <tr>
                                                                                                                    <td width="2%">&nbsp;</td>
                                                                                                                    <td width="7%">&nbsp;</td>
                                                                                                                    <td width="89%">&nbsp;</td>
                                                                                                                </tr>
                                                                                                                <tr> 
                                                                                                                    <td width="2%">&nbsp;</td>
                                                                                                                    <td width="7%"> 
                                                                                                                        <div align="left"></div>
                                                                                                                    </td>
                                                                                                                    <td width="89%"> 
                                                                                                                        <table border="0" cellpadding="0" cellspacing="0" width="749">
                                                                                                                            <tr> 
                                                                                                                                <td width="3%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                                                                                                <td width="3%"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                                                                                                <td width="2%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                                                                                                <td width="20%" class="command" nowrap><a href="javascript:cmdSearch()">Search for Schedule</a></td>
                                                                                                                    <%

                                                                                                                    //out.println("privAdd : "+privAdd);
                                                                                                                    //out.println("isSecretaryLogin : "+isSecretaryLogin);


                                                                                                                    if ((privAdd && isSecretaryLogin) || (privAdd && isHRDLogin)) {%>
                                                                                                                                <td width="8%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                                                                                                <td width="3%"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Schedule"></a></td>
                                                                                                                                <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                                                                                                <td width="26%" class="command" nowrap><a href="javascript:cmdAdd()">Add New Schedule</a></td>
                                                                                                                                <td width="4%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                                                                                                
                                                                                                                                <%
                                                                                                                                /*
                                                                                                                                 * Description : mengambil user name pada AppUser
                                                                                                                                 * Date : 2015-01-20
                                                                                                                                 * Author : Hendra Putu
                                                                                                                                */
                                                                                                                                //SessUserSession userSession = (SessUserSession)session.getValue(SessUserSession.HTTP_SESSION_NAME);
                                                                                                                                //AppUser appUserSess = userSession.getAppUser();/
                                                                                                                                int excelIOStatus = newAppUser.getExcelIO();//
                                                                                                                                if (excelIOStatus == 1){
                                                                                                                                 %>
                                                                                                                                 <td width="3%"><a href="javascript:cmdImport()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New Schedule"></a></td>
                                                                                                                                <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                                                                                                <td width="26%" class="command" nowrap><a href="javascript:cmdImport()">Import from Excel</a></td>
                                                                                                                                <%}%>
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
