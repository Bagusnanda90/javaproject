
<%
            /* 
             * Page Name  		:  perfevaluation.jsp
             * Created on 		:  [date] [time] AM/PM 
             * 
             * @author  		:  [authorName] 
             * @version  		:  [version] 
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
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_APPRAISAL, AppObjInfo.OBJ_PERFORMANCE_APPRAISAL);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
            /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//            boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//            boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//            boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>
<!-- Jsp Block -->
<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidPerformanceEvaluation = FRMQueryString.requestLong(request, "performance_appraisal_oid");
            long oidEmpAppraisal = FRMQueryString.requestLong(request, "employee_appraisal_oid");
            long oidEmployee = FRMQueryString.requestLong(request, "employee_oid");
            long categoryId = FRMQueryString.requestLong(request, "category");

            Employee employee = new Employee();
            GroupRank groupRank = new GroupRank();
            Level level = new Level();
            try {
                if(oidEmployee!=0){
                employee = PstEmployee.fetchExc(oidEmployee);
                }
                if(employee.getLevelId()!=0){
                    level = PstLevel.fetchExc(employee.getLevelId());
                }
                if(level.getGroupRankId()!=0){ 
                groupRank = PstGroupRank.fetchExc(level.getGroupRankId()); 
                }
            } catch (Exception e) {
                employee = new Employee();
            }
            /*variable declaration*/
            int recordToGet = 10;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            String whereClause = PstPerformanceEvaluation.fieldNames[PstPerformanceEvaluation.FLD_EMPLOYEE_APPRAISAL] + " = " + oidEmpAppraisal;
            String orderClause = "";

            CtrlPerfEvaluation ctrlPerfEvaluation = new CtrlPerfEvaluation(request);
            ControlLine ctrLine = new ControlLine();
            Vector listPerformanceEvaluation = new Vector(1, 1);
            Vector listCateg = PstCategoryAppraisal.getPerfCategory(groupRank.getOID());
            if ((listCateg != null && listCateg.size() > 0) && (categoryId == 0) && (iCommand == Command.NONE || iCommand == Command.ADD)) {
                CategoryAppraisal categApp = (CategoryAppraisal) listCateg.get(0);
                categoryId = categApp.getOID();
            }

            Vector listCriteria = PstPerformanceEvaluation.listCriteria(categoryId, oidEmpAppraisal);
            Vector vectPerformance = new Vector(1, 1);
            if ((iCommand == Command.SAVE) && (listCriteria != null && listCriteria.size() > 0)) {
                for (int pe = 0; pe < listCriteria.size(); pe++) {
                    Vector temp = (Vector) listCriteria.get(pe);
                    CategoryCriteria categCriteria = (CategoryCriteria) temp.get(0);
                    PerformanceEvaluation perfApp = (PerformanceEvaluation) temp.get(1);
                    perfApp.setOID(perfApp.getOID());
                    perfApp.setCategoryCriteriaId(categCriteria.getOID());
                    System.out.println("oidEmpAppraisal " + oidEmpAppraisal);
                    perfApp.setEmployeeAppraisal(oidEmpAppraisal);
                    long evaOID = FRMQueryString.requestLong(request, FrmPerfEvaluation.fieldNames[FrmPerfEvaluation.FRM_FIELD_EVALUATION_ID] + pe);
                    System.out.println("evaOID " + evaOID);
                    perfApp.setEvaluationId(evaOID);
                    String reason = FRMQueryString.requestString(request, FrmPerfEvaluation.fieldNames[FrmPerfEvaluation.FRM_FIELD_JUSTIFICATION] + pe);
                    System.out.println("reason " + reason);
                    perfApp.setJustification(reason);
                    vectPerformance.add(perfApp);
                }
            }
//out.println("vectPerformance "+vectPerformance.size());
/*switch statement */
            iErrCode = ctrlPerfEvaluation.action(iCommand, oidPerformanceEvaluation, oidEmpAppraisal, vectPerformance);
            /* end switch*/
            FrmPerfEvaluation frmPerfEvaluation = ctrlPerfEvaluation.getForm();

            /*count list All PerformanceEvaluation*/
            int vectSize = PstPerformanceEvaluation.getCount(whereClause);

            PerformanceEvaluation performanceEvaluation = ctrlPerfEvaluation.getPerformanceEvaluation();
            if (iCommand == Command.EDIT) {
                CategoryCriteria categCriteria = PstCategoryCriteria.fetchExc(performanceEvaluation.getCategoryCriteriaId());
                categoryId = categCriteria.getCategoryAppraisalId();
            }

            msgString = ctrlPerfEvaluation.getMessage();

            /*switch list PerformanceEvaluation*/
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                start = PstPerformanceEvaluation.findLimitStart(performanceEvaluation.getOID(), recordToGet, whereClause);
            }

            if ((iCommand == Command.FIRST || iCommand == Command.PREV) ||
                    (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlPerfEvaluation.actionList(iCommand, start, vectSize, recordToGet);
            }
            /* end switch list*/

            /* get record to display */
            listPerformanceEvaluation = PstPerformanceEvaluation.listPerfEvaluation(oidEmpAppraisal, true);

            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
            if (listPerformanceEvaluation.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;
                } //go to Command.PREV
                else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listPerformanceEvaluation = PstPerformanceEvaluation.listPerfEvaluation(oidEmpAppraisal, true);
            }

//out.println("listPerformanceEvaluation "+listPerformanceEvaluation.size());
%>
<html><!-- #BeginTemplate "/Templates/maintab.dwt" -->
<head>
    <!-- #BeginEditable "doctitle" --> 
    <title>Performance Evaluation</title>
    <script language="JavaScript">
        <!--
        function cmdAdd(){
            document.frmperfevaluation.performance_appraisal_oid.value="0";
            document.frmperfevaluation.command.value="<%=Command.ADD%>";
            document.frmperfevaluation.prev_command.value="<%=prevCommand%>";
            document.frmperfevaluation.action="perfevaluation.jsp";
            document.frmperfevaluation.submit();
        }
        
        function cmdAsk(oidPerformanceEvaluation){
            document.frmperfevaluation.performance_appraisal_oid.value=oidPerformanceEvaluation;
            document.frmperfevaluation.command.value="<%=Command.ASK%>";
            document.frmperfevaluation.prev_command.value="<%=prevCommand%>";
            document.frmperfevaluation.action="perfevaluation.jsp";
            document.frmperfevaluation.submit();
        }
        
        function cmdConfirmDelete(oidPerformanceEvaluation){
            document.frmperfevaluation.performance_appraisal_oid.value=oidPerformanceEvaluation;
            document.frmperfevaluation.command.value="<%=Command.DELETE%>";
            document.frmperfevaluation.prev_command.value="<%=prevCommand%>";
            document.frmperfevaluation.action="perfevaluation.jsp";
            document.frmperfevaluation.submit();
        }
        function cmdSave(){
            document.frmperfevaluation.command.value="<%=Command.SAVE%>";
            document.frmperfevaluation.prev_command.value="<%=prevCommand%>";
            document.frmperfevaluation.action="perfevaluation.jsp";
            document.frmperfevaluation.submit();
        }
        
        function cmdEdit(oidPerformanceEvaluation){
            document.frmperfevaluation.performance_appraisal_oid.value=oidPerformanceEvaluation;
            document.frmperfevaluation.command.value="0";
            document.frmperfevaluation.command.value="<%=Command.EDIT%>";
            document.frmperfevaluation.prev_command.value="<%=prevCommand%>";
            document.frmperfevaluation.action="perfevaluation.jsp";
            document.frmperfevaluation.submit();
        }
        
        function cmdCancel(oidPerformanceEvaluation){
            document.frmperfevaluation.performance_appraisal_oid.value=oidPerformanceEvaluation;
            document.frmperfevaluation.command.value="<%=Command.EDIT%>";
            document.frmperfevaluation.prev_command.value="<%=prevCommand%>";
            document.frmperfevaluation.action="perfevaluation.jsp";
            document.frmperfevaluation.submit();
        }
        
        function ctgCateg(){
            document.frmperfevaluation.command.value="<%=Command.LIST%>";
            document.frmperfevaluation.prev_command.value="<%=prevCommand%>";
            document.frmperfevaluation.action="perfevaluation.jsp";
            document.frmperfevaluation.submit();
        }
        
        function cmdBack(){
            document.frmperfevaluation.command.value="<%=Command.BACK%>";
            document.frmperfevaluation.action="perfevaluation.jsp";
            document.frmperfevaluation.submit();
        }
        
        function cmdListFirst(){
            document.frmperfevaluation.command.value="<%=Command.FIRST%>";
            document.frmperfevaluation.prev_command.value="<%=Command.FIRST%>";
            document.frmperfevaluation.action="perfevaluation.jsp";
            document.frmperfevaluation.submit();
        }
        
        function cmdListPrev(){
            document.frmperfevaluation.command.value="<%=Command.PREV%>";
            document.frmperfevaluation.prev_command.value="<%=Command.PREV%>";
            document.frmperfevaluation.action="perfevaluation.jsp";
            document.frmperfevaluation.submit();
        }
        
        function cmdListNext(){
            document.frmperfevaluation.command.value="<%=Command.NEXT%>";
            document.frmperfevaluation.prev_command.value="<%=Command.NEXT%>";
            document.frmperfevaluation.action="perfevaluation.jsp";
            document.frmperfevaluation.submit();
        }
        
        function cmdListLast(){
            document.frmperfevaluation.command.value="<%=Command.LAST%>";
            document.frmperfevaluation.prev_command.value="<%=Command.LAST%>";
            document.frmperfevaluation.action="perfevaluation.jsp";
            document.frmperfevaluation.submit();
        }
        
        //-------------- script control line -------------------
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
    <!-- #BeginEditable "headerscript" --> 
    <SCRIPT language=JavaScript>
        <!--
        function hideObjectForEmployee(){ 
            <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmPerfEvaluation.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK) || (iCommand == Command.LIST)) {%>
            document.frmperfevaluation.category.style.visibility="hidden";
            <%}%>   
        } 
        
        function hideObjectForLockers(){ 
        }
        
        function hideObjectForCanteen(){
        }
        
        function hideObjectForClinic(){
        }
        
        function hideObjectForMasterdata(){
            <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmPerfEvaluation.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK) || (iCommand == Command.LIST)) {%>
            document.frmperfevaluation.category.style.visibility="hidden";
            <%}%>
        }
        function showObjectForMenu(){
            <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmPerfEvaluation.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK) || (iCommand == Command.LIST)) {%>
            document.frmperfevaluation.category.style.visibility="";
            <%}%>
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
<tr> 
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
        <!-- #BeginEditable "header" --> 
      <%@ include file = "../../main/header.jsp" %> 
        <!-- #EndEditable --> 
    </td>
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
<tr> 
<td width="88%" valign="top" align="left"> 
    <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
    <tr> 
        <td width="100%"> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
            <tr> 
                <td>
                    <font color="#FF6600" face="Arial"><strong>
                            <!-- #BeginEditable "contenttitle" -->Employee 
                            &gt; Appraisal &gt; Performance Evaluation <!-- #EndEditable --> 
                    </strong></font>
                </td>
            </tr>
            <tr> 
            <td> 
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                    <td valign="top"> 
                    <table width="100%" border="0" cellspacing="1" cellpadding="1">
                        <tr> 
                        <td valign="top"> <!-- #BeginEditable "content" --> 
                            <form name="frmperfevaluation" method ="post" action="">
                            <input type="hidden" name="command" value="<%=iCommand%>">
                            <input type="hidden" name="vectSize" value="<%=vectSize%>">
                            <input type="hidden" name="start" value="<%=start%>">
                            <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                            <input type="hidden" name="employee_appraisal_oid" value="<%=oidEmpAppraisal%>">
                            <input type="hidden" name="employee_oid" value="<%=oidEmployee%>">																
                            <input type="hidden" name="<%=frmPerfEvaluation.fieldNames[FrmPerfEvaluation.FRM_FIELD_CATEGORY_CRITERIA_ID] %>" value="<%=performanceEvaluation.getCategoryCriteriaId()%>">
                            <input type="hidden" name="performance_appraisal_oid" value="<%=oidPerformanceEvaluation%>">
                            <table width="100%" border="0" cellspacing="1" cellpadding="1">
                            <tr> 
                                <td valign="top"> 
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <% if (oidEmpAppraisal != 0) {%>
                                    <tr> 
                                        <td> 
                                            <table  border="0" cellspacing="0" cellpadding="0">
                                                <tr> 
                                                    <td  valign="top" height="20" width="104"> 
                                                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="txtalign">
                                                            <tr> 
                                                                <td   valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                                <td   valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap> 
                                                                    <div align="center" class="tablink"><a href="empappraisal_edit.jsp?employee_appraisal_oid=<%=oidEmpAppraisal%>&command=<%=Command.EDIT%>" class="tablink">Appraisal</a></div>
                                                                </td>
                                                                <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td  valign="top" height="20" width="158"> 
                                                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                            <tr> 
                                                                <td   valign="top" align="left" width="10"><img src="<%=approot%>/images/tab/active_left.jpg" width="12" height="29"></td>
                                                                <td   valign="middle" background="<%=approot%>/images/tab/active_bg.jpg" nowrap > 
                                                                    <div align="center" class="tablink">Performance 
                                                                    Evaluation </div>
                                                                </td>
                                                                <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/active_right.jpg" width="12" height="29"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td  valign="top" height="20"  width="165"> 
                                                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                            <tr> 
                                                                <td   valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                                <td   valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap width="137"> 
                                                                    <div align="center" class="tablink"><a href="devimprovement.jsp?employee_appraisal_oid=<%=oidEmpAppraisal%>&employee_oid=<%=oidEmployee%>" class="tablink">Improvement 
                                                                    Appraisal</a></div>
                                                                </td>
                                                                <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td  valign="top" height="20" width="125"> 
                                                        <table width="97%" border="0" cellpadding="0" cellspacing="0">
                                                            <tr> 
                                                                <td   valign="top" align="left" width="12"><img src="<%=approot%>/images/tab/inactive_left.jpg" width="12" height="29"></td>
                                                                <td   valign="middle" background="<%=approot%>/images/tab/inactive_bg.jpg" nowrap width="110"> 
                                                                    <div align="center" class="tablink"><a href="devimprovementplan.jsp?employee_appraisal_oid=<%=oidEmpAppraisal%>&employee_oid=<%=oidEmployee%>" class="tablink">Improvement 
                                                                    Plan</a></div>
                                                                </td>
                                                                <td width="12"   valign="top" align="right"><img src="<%=approot%>/images/tab/inactive_right.jpg" width="12" height="29"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td width="150"  valign="top" height="20">&nbsp;</td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <%}%>
                                    <tr> 
                                    <td> 
                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                        <tr> 
                                            <td valign="top"  class="tablecolor"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabbg">
                                                <tr align="left" valign="top"> 
                                                <td height="8"  colspan="3"> 
                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr align="left" valign="top"> 
                                                        <td height="8" valign="middle" colspan="3">&nbsp;</td>
                                                    </tr>
                                                    <% if (oidEmployee != 0) {

                                                    %>
                                                    <tr align="left" valign="top"> 
                                                        <td height="8" valign="middle" colspan="3"> 
                                                            <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                                                <tr> 
                                                                    <td width="18%">Employee 
                                                                    Name</td>
                                                                    <td width="82%">: 
                                                                    <%=employee.getFullName()%></td>
                                                                </tr>
                                                                <tr> 
                                                                    <td width="18%">Employee 
                                                                    Number</td>
                                                                    <td width="82%">: 
                                                                    <%=employee.getEmployeeNum()%></td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                        <td height="8" valign="middle" colspan="3">&nbsp;</td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                        <td height="8" valign="middle" colspan="3">&nbsp;</td>
                                                    </tr>
                                                    <%
            }%>
                                                    <%
            try {
                //out.println("listPerformanceEvaluation "+listPerformanceEvaluation.size());																				
                if (listPerformanceEvaluation.size() > 0) {
                                                    %>
                                                    <tr align="left" valign="top"> 
                                                        <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Performance 
                                                            Evaluation List 
                                                        </td>
                                                    </tr>
                                                    <tr align="left" valign="top"> 
                                                        <td valign="middle" colspan="3"> 
                                                        <table width="95%" border="0" cellspacing="1" cellpadding="0" class="listgen">
                                                            <tr> 
                                                                <td width="3%" class="listgentitle">&nbsp;</td>
                                                                <td width="41%" class="listgentitle">Appraisal 
                                                                Category</td>
                                                                <td width="8%" class="listgentitle">Score</td>
                                                                <td width="48%" class="listgentitle">Justification</td>
                                                            </tr>
                                                            <%
                                                                int limit = start + recordToGet;
                                                                if ((start + recordToGet) > listPerformanceEvaluation.size()) {
                                                                    limit = listPerformanceEvaluation.size();
                                                                }

                                                                try {
                                                                    for (int i = start; i <  limit; i++) {
                                                                        Vector vector = (Vector) listPerformanceEvaluation.get(i);
                                                                        PerformanceEvaluation perfEvaluation = (PerformanceEvaluation) vector.get(0);
                                                                        CategoryAppraisal categApp = (CategoryAppraisal) vector.get(1);
                                                                        CategoryCriteria categCriteria = (CategoryCriteria) vector.get(2);
                                                                        Evaluation evaluate = (Evaluation) vector.get(3);
                                                                        Vector numbers = (Vector) vector.get(4);

                                                                        int num = Integer.parseInt("" + numbers.get(0));

                                                                        if (num == 0 || i == start) {

                                                            %>
                                                            <tr> 
                                                                <td colspan="4" class="listgensell">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b color="#0000FF"><%=categApp.getCategory()%></b></td>
                                                            </tr>
                                                            <%	}%>
                                                            <tr> 
                                                        <td width="3%" <%if ((perfEvaluation.getOID() == oidPerformanceEvaluation) && (iCommand == Command.EDIT)) {%>class="tabtitlehidden"<%} else {%>class="listgensell"<%}%>>&nbsp;&nbsp;&nbsp;<a href="javascript:cmdEdit('<%=perfEvaluation.getOID()%>')"><%= num + 1%></a>&nbsp;&nbsp;&nbsp;</td>
                                                            <td width="41%" <%if ((perfEvaluation.getOID() == oidPerformanceEvaluation) && (iCommand == Command.EDIT)) {%>class="tabtitlehidden"<%} else {%>class="listgensell"<%}%>><%=categCriteria.getCriteria()%>
                                                                <br>
                                                                <%
                                                                if( categCriteria.getDesc1()!=null && categCriteria.getDesc1().length()>0) { 
                                                                      out.println("<br>"+categCriteria.getDesc1());                                                                 
                                                                 }
                                                                
                                                                if( categCriteria.getDesc2()!=null && categCriteria.getDesc2().length()>0) { 
                                                                      out.println("<br>"+categCriteria.getDesc2());                                                                 
                                                                 }
                                                                if( categCriteria.getDesc3()!=null && categCriteria.getDesc3().length()>0) { 
                                                                      out.println("<br>"+categCriteria.getDesc3());                                                                 
                                                                 }
                                                                if( categCriteria.getDesc4()!=null && categCriteria.getDesc4().length()>0) { 
                                                                      out.println("<br>"+categCriteria.getDesc4());                                                                 
                                                                 }
                                                                if( categCriteria.getDesc5()!=null && categCriteria.getDesc5().length()>0) { 
                                                                      out.println("<br>"+categCriteria.getDesc5());                                                                 
                                                                 }
                                                                %>
                                                            </td>
                                                            <td width="8%" <%if ((perfEvaluation.getOID() == oidPerformanceEvaluation) && (iCommand == Command.EDIT)) {%>class="tabtitlehidden"<%} else {%>class="listgensell"<%}%>><%=evaluate.getCode()%></td>
                                                            <td width="48%" <%if ((perfEvaluation.getOID() == oidPerformanceEvaluation) && (iCommand == Command.EDIT)) {%>class="tabtitlehidden"<%} else {%>class="listgensell"<%}%>><%=perfEvaluation.getJustification()%></td>
                                                        </tr>
                                                        <% num++;
                                                                    }
                                                                } catch (Exception exc) {
                                                                    System.out.println("exc.............." + exc.toString());
                                                                }%>
                                                    </table>
                                                </td>
                                            </tr>
                                            <%  } else {%>
                                            <tr align="left" valign="top"> 
                                                <td height="14" valign="middle" colspan="3" class="comment">&nbsp;No 
                                                    Performance Evaluation 
                                                available </td>
                                            </tr>
                                            <% }
            } catch (Exception exc) {
            }%>
                                            <tr align="left" valign="top"> 
                                                <td height="8" align="left" colspan="3" class="command"> 
                                                    <span class="command"> 
                                                        <%
            int cmd = 0;
            if ((iCommand == Command.FIRST || iCommand == Command.PREV) ||
                    (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                cmd = iCommand;
            } else {
                if (iCommand == Command.NONE || prevCommand == Command.NONE) {
                    cmd = Command.FIRST;
                } else {
                    cmd = prevCommand;
                }
            }
                                                        %>
                                                        <% ctrLine.setLocationImg(approot + "/images");
            ctrLine.initDefault();
                                                        %>
                                                        <%=ctrLine.drawImageListLimit(cmd, vectSize, start, recordToGet)%> 
                                                </span> </td>
                                            </tr>
                                            <%if (iCommand == Command.NONE || iCommand == Command.SAVE || iCommand == Command.DELETE || iCommand == Command.BACK ||
                    iCommand == Command.FIRST || iCommand == Command.PREV || iCommand == Command.NEXT || iCommand == Command.LAST) {%>
                                            <% if (privAdd) {%>
                                            <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3"> 
                                                    <table cellpadding="0" cellspacing="0" border="0">
                                                        <tr> 
                                                            <td>&nbsp;</td>
                                                        </tr>
                                                        <tr> 
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                            <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a></td>
                                                            <td width="6"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                            <td height="22" valign="middle" colspan="3" width="951"><a href="javascript:cmdAdd()" class="command">Add 
                                                                    New Performance 
                                                                Evaluation</a> 
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <%}
            }%>
                                            <tr align="left" valign="top"> 
                                                <td height="22" valign="middle" colspan="3">&nbsp;</td>
                                            </tr>
                                        </table> 
                                    </td>
                                </tr>
                                <tr align="left" valign="top"> 
                                    <td height="8" valign="middle" colspan="3"> 
                                        <%if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmPerfEvaluation.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK) || (iCommand == Command.LIST)) {%>
                                        <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                            <tr align="left" valign="top"> 
                                                <td height="21" valign="middle" width="15%" align="right"><b>Category 
                                                        Appraisal &nbsp;: 
                                                &nbsp;</b>&nbsp;</td>
                                                <td height="21" colspan="2" class="comment"> 
                                                    <%
    Vector categKey = new Vector(1, 1);
    Vector categValue = new Vector(1, 1);
    for (int i = 0; i < listCateg.size(); i++) {
        CategoryAppraisal categAppraisal = (CategoryAppraisal) listCateg.get(i);
        categKey.add(categAppraisal.getCategory());
        categValue.add("" + categAppraisal.getOID());
    }
                                                    %>
                                                    
                                                    <table>
							<tr>
                                                        <td><%=ControlCombo.draw("category", "formElemen", null, "" + categoryId, categValue, categKey, "onChange=\"javascript:ctgCateg()\"")%></td>
                                                        <td><p title="Please insert data Group Rank to Master Data > Employee > Level > Group Rank">
                                                                <img name="Image300" border="0" src="<%=approot%>/images/Info-icon.png" width="20" height="15" alt="Info"></p></td>
							</tr>
                                                    </table>
													
                                                </td>
                                            </tr>
                                            <tr align="left" valign="top"> 
                                                <td height="21" valign="middle" width="15%">&nbsp;</td>
                                                <td height="21" colspan="2" class="comment">&nbsp;</td>
                                            </tr>
                                            <%
    if ((iCommand == Command.LIST || iCommand == Command.ADD || iCommand == Command.EDIT || iCommand == Command.ASK) &&
            ((listCriteria != null && listCriteria.size() > 0) || (oidPerformanceEvaluation != 0))) {%>

                                            <tr align="left" valign="top"> 
                                                <td height="14" valign="top" colspan="3"> 
                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                        <tr> 
                                                            <td width="5%">&nbsp;</td>
                                                            <td width="95%" valign="top"> 
                                                                <table width="97%" border="0" cellspacing="2" cellpadding="0">
                                                                    <tr> 
                                                                        <td width="4%" valign="top">&nbsp;</td>
                                                                        <td width="26%" valign="top">&nbsp;</td>
                                                                        <td width="29%" valign="top"><b>Score 
                                                                        / Nilai</b></td>
                                                                        <td width="41%" valign="top"><b>Justification 
                                                                        / Alasan</b></td>
                                                                    </tr>
                                                                    <tr> 
                                                                        <td colspan="4" valign="top">&nbsp;</td>
                                                                    </tr>
                                                                    <%
                                                if (iCommand == Command.LIST || iCommand == Command.ADD) {
                                                    for (int r = 0; r < listCriteria.size(); r++) {
                                                        Vector temp = (Vector) listCriteria.get(r);
                                                        CategoryCriteria categCriteria = (CategoryCriteria) temp.get(0);
                                                        PerformanceEvaluation perfEva = (PerformanceEvaluation) temp.get(1);
                                                                    %>
                                                                    <tr> 
                                                                        <td width="4%" valign="top"><%=r + 1%>.</td>
                                                                        <td width="26%" valign="top"><%= categCriteria.getCriteria() %>
 <%     
                                                                if( categCriteria.getDesc1()!=null && categCriteria.getDesc1().length()>0) { 
                                                                      out.println("<br>"+categCriteria.getDesc1());                                                                 
                                                                 }
                                                                if( categCriteria.getDesc2()!=null && categCriteria.getDesc2().length()>0) { 
                                                                      out.println("<br>"+categCriteria.getDesc2());                                                                 
                                                                 }
                                                                if( categCriteria.getDesc3()!=null && categCriteria.getDesc3().length()>0) { 
                                                                      out.println("<br>"+categCriteria.getDesc3());                                                                 
                                                                 }
                                                                if( categCriteria.getDesc4()!=null && categCriteria.getDesc4().length()>0) { 
                                                                      out.println("<br>"+categCriteria.getDesc4());                                                                 
                                                                 }
                                                                if( categCriteria.getDesc5()!=null && categCriteria.getDesc5().length()>0) { 
                                                                      out.println("<br>"+categCriteria.getDesc5());                                                                 
                                                                 }
                                                                %>                                                                            
                                                                        </td>
                                                                        <td width="29%" valign="top"> 
                                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                <%Vector listEvaluation = PstEvaluation.list(0, 0, "", PstEvaluation.fieldNames[PstEvaluation.FLD_CODE]);%>
                                                                                
                                                                                <tr> 
                                                                                    <td> 
                                                                                        <table border=1>
                                                                                            
                                                                                            <tr>
                                                                                                <% for (int i = 0; i < listEvaluation.size(); i++) {
                                                                                Evaluation evaluation = (Evaluation) listEvaluation.get(i);%>
                                                                                                <td>&nbsp;<%=evaluation.getCode()%>&nbsp;</td> 
                                                                                                <%}%>
                                                                                            </tr>
                                                                                            <tr>
                                                                                                <%
                                                                            for (int i = 0; i < listEvaluation.size(); i++) {
                                                                                Evaluation evaluation = (Evaluation) listEvaluation.get(i);
                                                                                    %><td>
                                                                                    <input type="radio" name="<%=frmPerfEvaluation.fieldNames[FrmPerfEvaluation.FRM_FIELD_EVALUATION_ID] + r %>" value="<%=evaluation.getOID()%>" <%if (perfEva.getEvaluationId() == evaluation.getOID()) {%>checked<%}%>>
                                                                                           &nbsp; 
                                                                                           <% }%></td>
                                                                                            </tr>
                                                                                            
                                                                                            <tr>
                                                                                                <% for (int i = 0; i < listEvaluation.size(); i++) {
                                                                                Evaluation evaluation = (Evaluation) listEvaluation.get(i);%>
                                                                                                <td nowrap=true><%=evaluation.getMaxPercentage()%> %</td> 
                                                                                                <%}%>
                                                                                            </tr>
                                                                                        </table>
                                                                                    </td>
                                                                                </tr>
                                                                            </table>
                                                                        </td>
                                                                        <td width="41%" valign="top"> 
                                                                            <textarea name="<%=frmPerfEvaluation.fieldNames[FrmPerfEvaluation.FRM_FIELD_JUSTIFICATION] + r %>" class="elemenForm" cols="35" rows="3"><%= perfEva.getJustification() %></textarea>
                                                                        </td>
                                                                    </tr>
                                                                    <tr> 
                                                                        <td colspan="4" valign="top">&nbsp;</td>
                                                                    </tr>
                                                                    <%}%>
                                                                    <%}%>																	  
                                                                    <% if (iCommand == Command.EDIT || iCommand == Command.ASK ||
                                                        (iCommand == Command.SAVE && frmPerfEvaluation.errorSize() > 0)) {
                                                    CategoryCriteria categCriteria = PstCategoryCriteria.fetchExc(performanceEvaluation.getCategoryCriteriaId());
                                                    Vector listEvaluation = PstEvaluation.list(0, 0, "", PstEvaluation.fieldNames[PstEvaluation.FLD_CODE]);
                                                                    %>
                                                                    <tr> 
                                                                        <td width="4%" valign="top">&nbsp;</td>
                                                                        <td width="26%" valign="top"> 
                                                                            <b><%=categCriteria.getCriteria()%></b> 
 <% 
                                                                 if( categCriteria.getDesc1()!=null && categCriteria.getDesc1().length()>0) { 
                                                                      out.println("<br>"+categCriteria.getDesc1());                                                                 
                                                                 }

                                                                if( categCriteria.getDesc2()!=null && categCriteria.getDesc2().length()>0) { 
                                                                      out.println("<br>"+categCriteria.getDesc2());                                                                 
                                                                 }
                                                                if( categCriteria.getDesc3()!=null && categCriteria.getDesc3().length()>0) { 
                                                                      out.println("<br>"+categCriteria.getDesc3());                                                                 
                                                                 }
                                                                if( categCriteria.getDesc4()!=null && categCriteria.getDesc4().length()>0) { 
                                                                      out.println("<br>"+categCriteria.getDesc4());                                                                 
                                                                 }
                                                                if( categCriteria.getDesc5()!=null && categCriteria.getDesc5().length()>0) { 
                                                                      out.println("<br>"+categCriteria.getDesc5());                                                                 
                                                                 }
                                                                %>                                                                            
                                                                        </td>
                                                                        <td width="29%" valign="top"> 
                                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                <tr> 
                                                                                    <td> 
                                                                                    <%
     for (int i = 0; i < listEvaluation.size(); i++) {
         Evaluation evaluation = (Evaluation) listEvaluation.get(i);
         //System.out.println("perfEva.getEvaluationId() "+performanceEvaluation.getEvaluationId());
         //System.out.println("evaluation.getOID() "+evaluation.getOID());
%>
                                                                                    <input type="radio" name="<%=frmPerfEvaluation.fieldNames[FrmPerfEvaluation.FRM_FIELD_EVALUATION_ID] %>" value="<%=evaluation.getOID()%>" <%if (performanceEvaluation.getEvaluationId() == evaluation.getOID()) {%>checked<%}%>>
                                                                                           &nbsp; 
                                                                                           <% }%>
                                                                                           </td>
                                                                                </tr>
                                                                                <tr> 
                                                                                    <td> 
                                                                                        <table>
                                                                                            <tr>
                                                                                                <% for (int i = 0; i < listEvaluation.size(); i++) {
         Evaluation evaluation = (Evaluation) listEvaluation.get(i);%>
                                                                                                <td><%=evaluation.getCode()%><br><%=evaluation.getMaxPercentage()%>%</td> 
                                                                                                <%}%>
                                                                                            </tr>
                                                                                            <tr> 
                                                                                                
                                                                                                <% for (int i = 0; i < listEvaluation.size(); i++) {
         Evaluation evaluation = (Evaluation) listEvaluation.get(i);%>
                                                                                                <td><input type="radio" name="<%=frmPerfEvaluation.fieldNames[FrmPerfEvaluation.FRM_FIELD_EVALUATION_ID] %>" value="<%=evaluation.getOID()%>" <%if (performanceEvaluation.getEvaluationId() == evaluation.getOID()) {%>checked<%}%>></td>
                                                                                                
                                                                                                <% }%>
                                                                                                
                                                                                            </tr>
                                                                                            
                                                                                        </table>
                                                                                        
                                                                                    </td>
                                                                                </tr>
                                                                            </table>
                                                                        </td>
                                                                        <td width="41%" valign="top"> 
                                                                            <textarea name="<%=frmPerfEvaluation.fieldNames[FrmPerfEvaluation.FRM_FIELD_JUSTIFICATION] %>" class="elemenForm" cols="35" rows="3"><%= performanceEvaluation.getJustification() %></textarea>
                                                                        </td>
                                                                    </tr>
                                                                    <%}%>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <%}%>
                                            <% //out.println("listCriteria"+ listCriteria.size());
    if ((listCriteria == null || listCriteria.size() < 1) && (iCommand != Command.EDIT)) {%>
                                            <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" colspan="3">
                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                        <tr>
                                                            <td width="5%">&nbsp;</td>
                                                            <td width="95%" class="comment">No 
                                                                Category Criteria 
                                                                available 
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" width="15%">&nbsp;</td>
                                                <td height="8" colspan="2">&nbsp;</td>
                                            </tr>
                                            <%}%>
                                            <tr align="left" valign="top"> 
                                                <td height="8" valign="middle" width="15%">&nbsp;</td>
                                                <td height="8" colspan="2">&nbsp; 
                                                </td>
                                            </tr>
                                            <tr align="left" valign="top" > 
                                                <td colspan="3" class="command"> 
                                                    <%
    ctrLine.setLocationImg(approot + "/images");
    ctrLine.initDefault();
    ctrLine.setTableWidth("95%");
    String scomDel = "javascript:cmdAsk('" + oidPerformanceEvaluation + "')";
    String sconDelCom = "javascript:cmdConfirmDelete('" + oidPerformanceEvaluation + "')";
    String scancel = "javascript:cmdEdit('" + oidPerformanceEvaluation + "')";
    ctrLine.setBackCaption("Back to List Performance Evaluation");
    ctrLine.setCommandStyle("buttonlink");
    ctrLine.setSaveCaption("Save Performance Evaluation");
    ctrLine.setDeleteCaption("Delete Performance Evaluation");
    ctrLine.setConfirmDelCaption("Delete Performance Evaluation");

    if (privDelete) {
        ctrLine.setConfirmDelCommand(sconDelCom);
        ctrLine.setDeleteCommand(scomDel);
        ctrLine.setEditCommand(scancel);
    } else {
        ctrLine.setConfirmDelCaption("");
        ctrLine.setDeleteCaption("");
        ctrLine.setEditCaption("");
    }

    if (privAdd == false && privUpdate == false) {
        ctrLine.setSaveCaption("");
    }

    if (privAdd == false) {
        ctrLine.setAddCaption("");
    }
    int cmdEditor = 0;
    if (iCommand == Command.LIST) {
        cmdEditor = Command.ADD;
    } else {
        cmdEditor = iCommand;
    }
                                                    %>
                                                    <%= ctrLine.drawImage(cmdEditor, iErrCode, msgString)%> 
                                                </td>
                                            </tr>
                                            <tr> 
                                                <td width="15%">&nbsp;</td>
                                                <td colspan="2">&nbsp;</td>
                                            </tr>
                                            <tr align="left" valign="top" > 
                                                <td colspan="3"> 
                                                    <div align="left"></div>
                                                </td>
                                            </tr>
                                        </table>
                                        <%}%>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr> 
            <td height="34">&nbsp; </td>
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
<tr> 
    <td colspan="2" height="20" <%=bgFooterLama%>> 	
        <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
    <!-- #EndEditable --> </td>
</tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> 
<!-- #EndTemplate --></html>
