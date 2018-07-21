<%@page import="com.dimata.harisma.session.employee.SearchSpecialQuery"%>
<%@page import="com.dimata.harisma.form.search.FrmSrcSpecialEmployeeQuery"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayGeneral"%>
<%@page import="com.dimata.harisma.entity.payroll.PayGeneral"%>
<%
            /*
             * Page Name  		:  careerpath.jsp
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
<%@ page import = "java.text.*" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>

<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
            /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<%
     boolean isSecretaryLogin = (positionType >= PstPosition.LEVEL_SECRETARY) ? true : false;
            long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
            boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
            long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
            boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
            boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>
<!-- Jsp Block -->

<%!    public String drawList(Vector objectClass, long workHistoryNowId) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("Company", "15%");
        ctrlist.addHeader("Division/Department/Section", "10%");
        ctrlist.addHeader("Payroll", "5%");
        ctrlist.addHeader("Employee Name", "10%");
        ctrlist.addHeader("Position", "15%");
        int SetLocation = Integer.valueOf(PstSystemProperty.getValueByName("USE_LOCATION_SET")); 
        if (SetLocation==1) {       ctrlist.addHeader("Location", "10%"); }
        ctrlist.addHeader("Level", "5%");
        ctrlist.addHeader("Emp Catagory", "10%");
        ctrlist.addHeader("Work From", "10%");
        ctrlist.addHeader("Work To", "10%");
        ctrlist.addHeader("Salary", "7%");
        ctrlist.addHeader("Description", "15%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;

        for (int i = 0; i < objectClass.size(); i++) {
            CareerPath careerPath = (CareerPath) objectClass.get(i);
            Vector rowx = new Vector();
            if (workHistoryNowId == careerPath.getOID()) {
                index = i;
            }
            String comapnyName="";
            if(careerPath.getCompanyId()!=0){
                PayGeneral payGeneral =new PayGeneral();
                try{
                    payGeneral =PstPayGeneral.fetchExc(careerPath.getCompanyId());
                }catch(Exception exc){

                }
                comapnyName = payGeneral.getCompanyName();
            }
            rowx.add(comapnyName);
            String divisionName="";
            if(careerPath.getDivisionId()!=0){
                Division division =new Division();
                try{
                    division =PstDivision.fetchExc(careerPath.getDivisionId());
                }catch(Exception exc){

                }
                divisionName = division.getDivision();
            }
            /*if (careerPath.getDivision()!= null)
            {
                rowx.add(divisionName);
            }
            else{
                rowx.add("-");
                }*/
            
            String departmentName="";
            if(careerPath.getDepartmentId()!=0){
                Department department =new Department();
                try{
                    department =PstDepartment.fetchExc(careerPath.getDepartmentId());
                }catch(Exception exc){

                }
                departmentName = department.getDepartment();
            }
            divisionName = divisionName + "/" + departmentName + (careerPath.getSection()==null?"": "/"+careerPath.getSection());
            rowx.add(divisionName);
            rowx.add( careerPath.getEmployee()!=null ? careerPath.getEmployee().getEmployeeNum():"");
            rowx.add( careerPath.getEmployee()!=null ? careerPath.getEmployee().getFullName():"");
            rowx.add(careerPath.getPosition());
            if (SetLocation==1) { rowx.add(careerPath.getLocation()); }
            rowx.add(careerPath.getLevel());
            rowx.add(careerPath.getEmpCategory());

            String str_dt_WorkFrom = "";
            try {
                Date dt_WorkFrom = careerPath.getWorkFrom();
                if (dt_WorkFrom == null) {
                    dt_WorkFrom = new Date();
                }

                str_dt_WorkFrom = Formater.formatDate(dt_WorkFrom, "dd MMMM yyyy");
            } catch (Exception e) {
                str_dt_WorkFrom = "";
            }

            rowx.add(str_dt_WorkFrom);

            String str_dt_WorkTo = "";
            try {
                Date dt_WorkTo = careerPath.getWorkTo();
                if (dt_WorkTo == null) {
                    //dt_WorkTo = new Date();
                    str_dt_WorkTo ="-";
                } else {
                    str_dt_WorkTo = Formater.formatTimeLocale(dt_WorkTo, "dd MMMM yyyy");
               }
            } catch (Exception e) {
                str_dt_WorkTo = "";
            }

            rowx.add(str_dt_WorkTo);
            rowx.add(NumberFormat.getNumberInstance().format(careerPath.getSalary()));

            rowx.add(careerPath.getDescription());

            lstData.add(rowx);
            lstLinkData.add(String.valueOf(careerPath.getOID()));
        }

        return ctrlist.draw(index);

        //return ctrlist.draw();
    }

%>
<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidCareerPath = FRMQueryString.requestLong(request, "career_path_oid");
            long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
           
            long oidHistoryComp = FRMQueryString.requestLong(request, FrmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_COMPANY_ID]);
            long oidHistoryDept = FRMQueryString.requestLong(request,  FrmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_DEPARTMENT]);
            //update by satrya 2013-10-14
            long oidHistoryDiv = FRMQueryString.requestLong(request, FrmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_EMP_DIVISION_ID]);
            long oidHistorySection = FRMQueryString.requestLong(request, FrmSrcSpecialEmployeeQuery.fieldNames[FrmSrcSpecialEmployeeQuery.FRM_FIELD_SECTION]); 

//System.out.println("iCommand............."+iCommand);
/*variable declaration*/
         
            int recordToGet = 1000;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = "";
            if(oidHistoryComp!=0){
                 whereClause= " h."+PstCareerPath.fieldNames[PstCareerPath.FLD_COMPANY_ID] + " = " + oidHistoryComp;
            }
            if(oidHistoryDiv!=0){
                 whereClause= whereClause + (whereClause.length()>0 ? " AND h." :" h." ) + PstCareerPath.fieldNames[PstCareerPath.FLD_DIVISION_ID] + " = " + oidHistoryDiv;
            }
            if(oidHistoryDept!=0){
                 whereClause= whereClause + (whereClause.length()>0 ? " AND h." :" h."  ) + PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT_ID] + " = " + oidHistoryDept;
            }
            if(oidHistorySection!=0){
                 whereClause=  whereClause + (whereClause.length()>0 ? " AND h." :" h."  ) + PstCareerPath.fieldNames[PstCareerPath.FLD_DEPARTMENT_ID] + " = " + oidHistorySection;
            }

                                              
            
            String orderClause =  " p." + PstPosition.fieldNames[PstPosition.FLD_POSITION_LEVEL] + " DESC, h."+ PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM] + " DESC ";

            CtrlCareerPath ctrlCareerPath = new CtrlCareerPath(request);
            ControlLine ctrLine = new ControlLine();
            Vector listCareerPath = new Vector(1, 1);
            Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");
            Vector listCompany = PstCompany.list(0, 0, "", "COMPANY");
            //Vector listSection = new Vector(1, 1);


            /*switch statement */
            iErrCode = ctrlCareerPath.action(iCommand, oidCareerPath, oidEmployee, request);
            /* end switch*/
            FrmCareerPath frmCareerPath = ctrlCareerPath.getForm();

            CareerPath careerPath = ctrlCareerPath.getCareerPath();
            msgString = ctrlCareerPath.getMessage();

            /*switch list CareerPath*/
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidCareerPath == 0)) {
                start = PstCareerPath.findLimitStart(careerPath.getOID(), recordToGet, whereClause, orderClause);
            }

            /*count list All CareerPath*/
            int vectSize = 0;//PstCareerPath.getCount(whereClause);

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlCareerPath.actionList(iCommand, start, vectSize, recordToGet);
            }
            /* end switch list*/

            /* get record to display */
            listCareerPath = PstCareerPath.listHistory(start, recordToGet, whereClause, orderClause);
            vectSize = listCareerPath!=null ? listCareerPath.size() : 0;
            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listCareerPath.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listCareerPath = PstCareerPath.listHistory(start, recordToGet, whereClause, orderClause);
            }

            long oidDepartment = 0;
            if (oidEmployee != 0) {
                Employee employee = new Employee();
                try {
                    employee = PstEmployee.fetchExc(oidEmployee);
                    oidDepartment = employee.getDepartmentId();
                } catch (Exception exc) {
                    employee = new Employee();
                }
            }

            if (iCommand == Command.GOTO) {
                frmCareerPath = new FrmCareerPath(request, careerPath);
                frmCareerPath.requestEntityObject(careerPath);
            }

//listSection = PstSection.list(0,500,PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+ " = "+oidDepartment,"SECTION");

%>
<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title><%=dictionaryD.getWord(I_Dictionary.CAREER_PATH) %></title>
        <script language="JavaScript">
            //update by satrya 2013-10-14
            function cmdUpdateDiv(){
                document.frmcareerpath.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmcareerpath.action="careerpath.jsp";
                document.frmcareerpath.submit();
            }
            function cmdUpdateDep(){
                document.frmcareerpath.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmcareerpath.action="careerpath.jsp";
                document.frmcareerpath.submit();
            }
            function cmdUpdatePos(){
                document.frmcareerpath.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmcareerpath.action="careerpath.jsp";
                document.frmcareerpath.submit();
            }
            function cmdAdd(){
                document.frmcareerpath.career_path_oid.value="0";
                document.frmcareerpath.command.value="<%=Command.ADD%>";
                document.frmcareerpath.prev_command.value="<%=Command.ADD%>";
                document.frmcareerpath.action="careerpath.jsp";
                document.frmcareerpath.submit();
            }

            function cmdAsk(oidCareerPath){
                document.frmcareerpath.career_path_oid.value=oidCareerPath;
                document.frmcareerpath.command.value="<%=Command.ASK%>";
                document.frmcareerpath.prev_command.value="<%=prevCommand%>";
                document.frmcareerpath.action="careerpath.jsp";
                document.frmcareerpath.submit();
            }

            function cmdConfirmDelete(oidCareerPath){
                document.frmcareerpath.career_path_oid.value=oidCareerPath;
                document.frmcareerpath.command.value="<%=Command.DELETE%>";
                document.frmcareerpath.prev_command.value="<%=prevCommand%>";
                document.frmcareerpath.action="careerpath.jsp";
                document.frmcareerpath.submit();
            }
            function cmdSave(){
                document.frmcareerpath.command.value="<%=Command.SAVE%>";
                document.frmcareerpath.prev_command.value="<%=prevCommand%>";
                document.frmcareerpath.action="careerpath.jsp";
                document.frmcareerpath.submit();
            }

            function cmdEdit(oidCareerPath){
                document.frmcareerpath.career_path_oid.value=oidCareerPath;
                document.frmcareerpath.command.value="<%=Command.EDIT%>";
                document.frmcareerpath.prev_command.value="<%=Command.EDIT%>";
                document.frmcareerpath.action="careerpath.jsp";
                document.frmcareerpath.submit();
            }

            function cmdCancel(oidCareerPath){
                document.frmcareerpath.career_path_oid.value=oidCareerPath;
                document.frmcareerpath.command.value="<%=Command.EDIT%>";
                document.frmcareerpath.prev_command.value="<%=prevCommand%>";
                document.frmcareerpath.action="careerpath.jsp";
                document.frmcareerpath.submit();
            }

            function cmdBack(){
                document.frmcareerpath.command.value="<%=Command.BACK%>";
                document.frmcareerpath.action="srcemployee.jsp";
                document.frmcareerpath.submit();
            }

            function cmdUpdateSection(){
                document.frmcareerpath.command.value="<%= Command.GOTO%>";
                document.frmcareerpath.prev_command.value="<%= prevCommand%>";
                document.frmcareerpath.action="srcemployee.jsp";
                document.frmcareerpath.submit();
            }

            function cmdBackEmp(empOID){
                document.frmcareerpath.employee_oid.value=empOID;
                document.frmcareerpath.command.value="<%=Command.EDIT%>";
                document.frmcareerpath.action="employee_edit.jsp";
                document.frmcareerpath.submit();
            }


            function cmdListFirst(){
                document.frmcareerpath.command.value="<%=Command.FIRST%>";
                document.frmcareerpath.prev_command.value="<%=Command.FIRST%>";
                document.frmcareerpath.action="careerpath.jsp";
                document.frmcareerpath.submit();
            }

            function cmdListPrev(){
                document.frmcareerpath.command.value="<%=Command.PREV%>";
                document.frmcareerpath.prev_command.value="<%=Command.PREV%>";
                document.frmcareerpath.action="careerpath.jsp";
                document.frmcareerpath.submit();
            }

            function cmdListNext(){
                document.frmcareerpath.command.value="<%=Command.NEXT%>";
                document.frmcareerpath.prev_command.value="<%=Command.NEXT%>";
                document.frmcareerpath.action="careerpath.jsp";
                document.frmcareerpath.submit();
            }

            function cmdListLast(){
                document.frmcareerpath.command.value="<%=Command.LAST%>";
                document.frmcareerpath.prev_command.value="<%=Command.LAST%>";
                document.frmcareerpath.action="careerpath.jsp";
                document.frmcareerpath.submit();
            }
            function fnTrapKD(){
                //alert(event.keyCode);
                switch(event.keyCode)         {
                    case <%=LIST_PREV%>:
                            cmdListPrev();
                        break        ;
                    case <%=LIST_NEXT%>:
                            cmdListNext();
                        break        ;
                    case <%=LIST_FIRST%>:
                            cmdListFirst();
                        break        ;
                    case <%=LIST_LAST%>:
                            cmdListLast();
                        break;
                    default:
                        break;
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
                <!--
                function hideObjectForEmployee(){
                }
	 
                function hideObjectForLockers(){
                }
	
                function hideObjectForCanteen(){
                }
	
                function hideObjectForClinic(){
                }

                function hideObjectForMasterdata(){
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

    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
       
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF">
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
                <td bgcolor="#9BC1FF"  ID="MAINMENU" valign="middle" height="15"> <!-- #BeginEditable "menumain" -->
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
                                        <td> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Company Position 
                                                    &gt; History<!-- #EndEditable --> </strong></font> </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td valign="top">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                                            <tr>
                                                                <td valign="top"> <!-- #BeginEditable "content" -->
<form name="frmcareerpath" method ="post" action="careerpath.jsp">
    <input type="hidden" name="command" value="<%=iCommand%>">
    <input type="hidden" name="vectSize" value="<%=vectSize%>">
    <input type="hidden" name="start" value="<%=start%>">
    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
    <input type="hidden" name="career_path_oid" value="<%=oidCareerPath%>">
    <input type="hidden" name="department_oid" value="<%=oidDepartment%>">
    <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <% if (oidEmployee != 0) {%>
        <tr>
            <td>
                <br>
                <table width="98%" align="center" border="0" cellspacing="2" cellpadding="2" height="26">
                    <tr>

                        <%-- TAB MENU --%>
                        <td width="11%" nowrap bgcolor="#0066CC">
                            <div align="center" class="tablink">
                                <a href="employee_edit.jsp?employee_oid=<%=oidEmployee%>&prev_command=<%=Command.EDIT%>" class="tablink">Personal Data</a>
                            </div>
                        </td>
                        <td width="11%" nowrap bgcolor="#0066CC">
                            <div align="center"  class="tablink">
                                <a href="familymember.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.FAMILY_MEMBER) %></a>
                            </div>
                        </td>
                        <td width="7%" nowrap bgcolor="#0066CC">
                            <div align="center"  class="tablink">
                                <a href="emplanguage.jsp?employee_oid=<%=oidEmployee%>" class="tablink">Language</a>
                            </div>
                        </td>
                        <td width="8%" nowrap bgcolor="#0066CC">
                            <div align="center"  class="tablink">
                                <a href="empeducation.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.EDUCATION) %></a>
                            </div>
                        </td>
                        <td width="8%" nowrap bgcolor="#0066CC">
                            <div align="center"  class="tablink">
                                <a href="experience.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.EXPERIENCE) %></a>
                            </div>
                        </td>
                        <%-- active tab --%>
                        <td width="9%" nowrap bgcolor="#66CCFF">
                            <div align="center"  class="tablink">
                                <span class="tablink"><%=dictionaryD.getWord(I_Dictionary.CAREER_PATH) %></span>
                            </div>
                        </td>
                        <td width="7%" nowrap bgcolor="#0066CC">
                            <div align="center"  class="tablink">
                                <a href="training.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.TRAINING) %></a>
                            </div>
                        </td>
                        <td width="7%" nowrap bgcolor="#0066CC">
                            <div align="center"  class="tablink">
                                <a href="warning.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.WARNING) %></a>
                            </div>
                        </td>
                        <td width="8%" nowrap bgcolor="#0066CC">
                            <div align="center">
                                <a href="reprimand.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.REPRIMAND) %></a>
                            </div>
                        </td>
                        <td width="6%" nowrap bgcolor="#0066CC">
                            <div align="center"  class="tablink">
                                <a href="award.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.AWARD) %></a>
                            </div>
                        </td>
                        <td width="7%" nowrap bgcolor="#0066CC">
                            <div align="center"  class="tablink">
                                <a href="picture.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.PICTURE) %></a>
                            </div>
                        </td>
                        <td width="11%" nowrap bgcolor="#0066CC">
                            <div align="center">
                                <a href="doc_relevant.jsp?employee_oid=<%=oidEmployee%>" class="tablink"><%=dictionaryD.getWord(I_Dictionary.RELEVANT_DOCS) %></a>
                            </div>
                        </td>
                        <%-- END TAB MENU --%>

                    </tr>
                </table>
            </td>
        </tr>
        <%}%>
        <tr>
            <td class="tablecolor" style="background-color:<%=bgColorContent%>;">
                <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr>
                        <td valign="top">
                            <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="0" cellpadding="0" class="tabbg">
                                <tr align="left" valign="top">
                                    <td height="8"  colspan="3">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr align="left" valign="top">
                                                <td height="14" valign="middle" colspan="3" class="listedittitle">&nbsp;</td>
                                            </tr>
                                            <% {
                                            %>
                                            <tr align="left" valign="top">
                                                <td height="14" valign="middle" colspan="3" class="listedittitle">
                                                    <table width="100%" border="0" cellspacing="2" cellpadding="1">
                                                     
                                                        <% Company company = new Company();
                                                             try {
                                                                 company = PstCompany.fetchExc(oidHistoryComp);
                                                             } catch (Exception exc) {
                                                                 company = new Company(); company.setCompany("-all-");
                                                             }
                                                        %>    
                                                        <tr>
                                                            <td width="17%">Company</td>
                                                            <td width="2%">:</td>
                                                            <td width="81%"><%=company.getCompany()%></td>
                                                        </tr>
                                                        <% Division division = new Division();
                                                             try {
                                                                 division = PstDivision.fetchExc(oidHistoryDiv);
                                                             } catch (Exception exc) {
                                                                 division = new Division(); division.setDivision("-all-");
                                                             }
                                                        %>
                                                        <tr>
                                                            <td width="17%"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></td>
                                                            <td width="2%">:</td>
                                                            <td width="81%"><%=division.getDivision()%></td>
                                                        </tr>                                                        
                                                        <% Department department = new Department();
                                                             try {
                                                                 department = PstDepartment.fetchExc(oidHistoryDept);
                                                             } catch (Exception exc) {
                                                                 department = new Department(); department.setDepartment("-all-");
                                                             }
                                                        %>
                                                        <tr>
                                                            <td width="17%"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>
                                                            <td width="2%">:</td>
                                                            <td width="81%"><%=department.getDepartment()%></td>
                                                        </tr>
                                                        <% Section section = new Section();
                                                             try {
                                                                 section = PstSection.fetchExc(oidHistorySection);
                                                             } catch (Exception exc) {
                                                                 section = new Section(); section.setSection("-all-");
                                                             }
                                                        %>
                                                        <tr>
                                                            <td width="17%"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></td>
                                                            <td width="2%">:</td>
                                                            <td width="81%"><%=section.getSection()%></td>
                                                        </tr>
                                                     
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr align="left" valign="top">
                                                <td height="14" valign="middle" colspan="3" class="listedittitle">&nbsp;</td>
                                            </tr>
                                            <%}%>
                                            <%
                                                        try {
                                                            if (listCareerPath.size() > 0) {
                                            %>
                                            <tr align="left" valign="top">
                                                <td height="22" valign="middle" colspan="3" class="listtitle">
                                                    &nbsp;Career Path List
                                                </td>
                                            </tr>
                                            <tr align="left" valign="top">
                                                <td height="22" valign="middle" colspan="3">
                                                    <%= drawList(listCareerPath, iCommand == Command.SAVE ? careerPath.getOID() : oidCareerPath)%>
                                                </td>
                                            </tr>
                                            <%  } else {%>
                                            <tr align="left" valign="top">
                                                <td height="22" valign="middle" colspan="3" class="comment">
                                                    No Career Path available
                                                </td>
                                            </tr>
                                            <% }
                                                        } catch (Exception exc) {
                                                        }%>
                                            <tr>
                                                <td colspan="2">
                                                    <!-- update by devin 2014-02-05 -->
                                                    <table width="100%">
                                    <tr>
                                   <td align="center">

                                        <%
                                        if(msgString !=null && msgString.length()>0){
                                            %>
                                            <td style="padding-right:100px; background-color:#FF0000; color:#FFFFFF"><%=msgString%></td>
                                        <%
                                        }else{

                                        }

                                        %>

                                    </td>
                                    <td>
                                        <%
                                        if(oidDepartment==0){

                                        }
                                        %>
                                    </td>
                                    </tr>
                                </table>
                                                </td>
                                            </tr>
                                            <tr align="left" valign="top">
                                                <td height="8" align="left" colspan="3" class="listedittitle">
                                                    <span class="command">
                                                        <%
                                                                    int cmd = 0;
                                                                    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                                                                            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                                                                        cmd = iCommand;
                                                                    } else {
                                                                        if (iCommand == Command.NONE || prevCommand == Command.NONE) {
                                                                            cmd = Command.FIRST;
                                                                        } else {
                                                                            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE) && (oidCareerPath == 0)) {
                                                                                cmd = PstCareerPath.findLimitCommand(start, recordToGet, vectSize);
                                                                            } else {
                                                                                cmd = prevCommand;
                                                                            }
                                                                        }
                                                                    }
                                                        %>
                                                        <% ctrLine.setLocationImg(approot + "/images");
                                                                    ctrLine.initDefault();
                                                        %>
                                                        <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%> </span> </td>
                                            </tr>
                                            <%  {%>
                                            <% {%>
                                            <tr align="left" valign="top">
                                                <td>
                                                    <table border="0" cellspacing="0" cellpadding="0" align="left">
                                              <tr> 
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap> <a href="javascript:cmdBack()" class="command">Back 
                                                  To Search Employee</a></td>
												<% 
												if(privAdd)
												{
												%>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap><b><a href="javascript:cmdAdd()" class="command">Add 
                                                  New Employee</a></b></td>
												<%
												}
												%>
												
												
											  </tr>
                                            </table>
                                                </td>
                                            </tr>
                                            <% }
                                                        }%>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp; </td>
                                </tr>
                                <tr align="left" valign="top">
                                    <td height="8" valign="middle" colspan="3">
                                        
                                                                                                        </td>
                                                                                                        <!-- update by devin 2014-02-05 -->
                                                                                                    
                                                                                                         </div
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
                                                                    </form>
                                                                    <!-- #EndEditable --> </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td >&nbsp;</td>
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
    <script language="JavaScript">
                var oBody = document.body;
                var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>
