<%
            /* 
             * Page Name  		:  empappraisal_edit.jsp
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
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
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

            CtrlEmpAppraisal ctrlEmpAppraisal = new CtrlEmpAppraisal(request);
            long oidEmpAppraisal = FRMQueryString.requestLong(request, "employee_appraisal_oid");
            long oidDepartAppraisor = FRMQueryString.requestLong(request, "depart_appraisor");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidPerformanceEvaluation = FRMQueryString.requestLong(request, "performance_appraisal_oid");
            long oidDevImprovement = FRMQueryString.requestLong(request, "dev_improvement_oid");
            long oidDevImprovementPlan = FRMQueryString.requestLong(request, "hidden_dev_improvement_plan_id");            


//	out.print("oidEmpAppraisal : "+oidEmpAppraisal);
            int iErrCode = FRMMessage.ERR_NONE;
            String errMsg = "";

            String whereClause = "";
            String orderClause = "";
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");

            Vector listAppraisor = new Vector(1, 1);
            if (oidDepartAppraisor == 0) {
                Vector tempDepart = PstDepartment.list(0, 0, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT] + " LIKE '%HUMAN%'", "");
                if (tempDepart.size() < 1) {
                    tempDepart = PstDepartment.list(0, 0, "", "");
                }

                Department dept = (Department) tempDepart.get(0);
                oidDepartAppraisor = dept.getOID();
                listAppraisor = PstEmployee.list(0, 0, PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + oidDepartAppraisor, "");
            } else {
                listAppraisor = PstEmployee.list(0, 0, PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID] + " = " + oidDepartAppraisor, "");
            }
            ControlLine ctrLine = new ControlLine();

            iErrCode = ctrlEmpAppraisal.action(iCommand, oidEmpAppraisal, request);

            errMsg = ctrlEmpAppraisal.getMessage();
            FrmEmpAppraisal frmEmpAppraisal = ctrlEmpAppraisal.getForm();
            EmpAppraisal empAppraisal = ctrlEmpAppraisal.getEmpAppraisal();
            oidEmpAppraisal = empAppraisal.getOID();

            if (iCommand == Command.DELETE) {
%>
<jsp:forward page="empappraisal_list.jsp">
  <jsp:param name="prev_command" value="<%=prevCommand%>" />
  <jsp:param name="start" value="<%=start%>" />
</jsp:forward>
<%
            }

            long oidEmployee = empAppraisal.getEmployeeId();

            Employee employee = new Employee();
            long oidPosition = 0;
            Position position = new Position();
            long oidDepartment = 0;
            Department department = new Department();
            Department depAppraisor = new Department();

            if (empAppraisal.getOID() != 0) {
                try {
                    employee = PstEmployee.fetchExc(oidEmployee);
                    oidPosition = employee.getPositionId();
                    position = PstPosition.fetchExc(oidPosition);
                    oidDepartment = employee.getDepartmentId();
                    department = PstDepartment.fetchExc(oidDepartment);
                    depAppraisor = PstDepartment.fetchExc(oidDepartAppraisor);
                } catch (Exception exc) {
                    employee = new Employee();
                    position = new Position();
                    department = new Department();
                    depAppraisor = new Department();
                }

            }
//out.println(" | employee : " + employee.getFullName());
//out.println(" | oidEmployee "+oidEmployee);
%>
<!-- End of Jsp Block -->
<html>
<head>
<title>HARISMA - Employee Appraisal</title>
<script language="JavaScript">
        
        function cmdEdit(oid){ 
            document.frm_empappraisal.command.value="<%=Command.EDIT%>";
            document.frm_empappraisal.action="empappraisal_edit.jsp";
            document.frm_empappraisal.submit(); 
        } 
        
        function cmdSave(){
            document.frm_empappraisal.command.value="<%=Command.SAVE%>"; 
            document.frm_empappraisal.action="empappraisal_edit.jsp";
            document.frm_empappraisal.submit();
        }
        
        function cmdAsk(oid){
            document.frm_empappraisal.command.value="<%=Command.ASK%>"; 
            document.frm_empappraisal.action="empappraisal_edit.jsp";
            document.frm_empappraisal.submit();
        } 
        
        function cmdConfirmDelete(oid){
            document.frm_empappraisal.command.value="<%=Command.DELETE%>";
            document.frm_empappraisal.action="empappraisal_edit.jsp"; 
            document.frm_empappraisal.submit();
        }  
        
        function cmdBack(){
            document.frm_empappraisal.command.value="<%=Command.LIST%>"; 
            document.frm_empappraisal.action="empappraisal_list.jsp";
            document.frm_empappraisal.submit();
        }
        
        function chgDepart(){
            document.frm_empappraisal.command.value="<%=iCommand%>"; 
            document.frm_empappraisal.action="empappraisal_edit.jsp";
            document.frm_empappraisal.submit();
        }	
        
        function cmdSearchEmp(){
            window.open("empdopsearch.jsp?emp_number=" + document.frm_empappraisal.EMP_NUMBER.value + "&emp_fullname=" + document.frm_empappraisal.EMP_FULLNAME.value + "&emp_department=" + document.frm_empappraisal.EMP_DEPARTMENT.value, null, "height=600,width=800,status=yes,toolbar=no,menubar=no,location=no");
        }
        
        function calAppraisor() 
        {
            appraisor = document.frm_empappraisal.chg_appraisor.value;
            document.frm_empappraisal.<%=frmEmpAppraisal.fieldNames[FrmEmpAppraisal.FRM_FIELD_APPRAISOR_ID]%>.value=appraisor;
         <%
            for (int i = 0; i < listAppraisor.size(); i++) {
                Employee emp = (Employee) listAppraisor.get(i);
                long oid = emp.getOID();
                         %>
                             if(appraisor=="<%=oid%>")
                                 document.frm_empappraisal.appraisor.value="<%=emp.getFullName()%>";
                         <%
            }
         %>
             
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
         
         function MM_swapImage() { //v3.0
             var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
             if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
         }
         
    </script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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
            <%if (empAppraisal.getOID() == 0) {%>
            document.frm_empappraisal.EMP_DEPARTMENT.style.visibility="hidden";
            document.frm_empappraisal.chg_appraisor.style.visibility="hidden";
            <%}%>
        }
        
        function showObjectForMenu(){
            <%if (empAppraisal.getOID() == 0) {%>
            document.frm_empappraisal.EMP_DEPARTMENT.style.visibility="";
            document.frm_empappraisal.chg_appraisor.style.visibility="";
            <%}%>
        }
        
        function MM_findObj(n, d) { //v4.01
            var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
            if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
            for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
            if(!x && d.getElementById) x=d.getElementById(n); return x;
        }
        //-->
    </SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF">
  <tr>
  
  <td width="100%" valign="top" align="left">
  
  <table width="100%" border="0" cellspacing="3" cellpadding="2">
    <tr>
    
    <td width="100%">
    
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
      
      <td>
      
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
        
        <td valign="top">
        
        <table width="100%" border="0" cellspacing="1" cellpadding="1">
          <tr>
          
          <td valign="top">
          
          <form name="frm_empappraisal" method="post" action="">
            <input type="hidden" name="command" value="">
            <input type="hidden" name="start" value="<%=start%>">
            <input type="hidden" name="prev_command" value="<%=prevCommand%>">
            <input type="hidden" name="employee_appraisal_oid" value="<%=oidEmpAppraisal%>">
            <table width="100%" border="0" cellspacing="1" cellpadding="1" height="504">
              <tr>
              
              <td valign="top" height="551">
              
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                
                <td class="tablecolor">
                
                <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                  <tr>
                  
                  <td valign="top">
                  
                  <table width="100%" cellspacing="1" cellpadding="1" class="tabbg" height="100%">
                    <tr>
                      <td width="3%"  valign="top" align="left"  >&nbsp;</td>
                      <td width="94"  ><table width="100%" border="0" cellspacing="2" cellpadding="2">
                          <tr>
                            <td colspan="4">&nbsp;</td>
                          </tr>
                          <tr>
                            <td colspan="4" class="reportTitle">
							<% 
							GroupRank groupRank  = new GroupRank();
							groupRank.setGroupName("");
							groupRank.setDescription("");
							try{ 
							  groupRank = PstGroupRank.fetchExc(empAppraisal.getGroupRankId());
							  } catch(Exception exc){}
							%>
							<div align="center"> <%=groupRank.getGroupName()%> PERFORMANCE ASSESMENT FORM </div></td>
                          </tr>
                          <tr>
                            <td colspan="4" class="reportSubTitle"><div align="center">(<%=groupRank.getDescription()%>)</div></td>
                          </tr>
                          <tr>
                            <td width="18%">&nbsp;</td>
                            <td colspan="3"><input type="hidden" name="<%=FrmEmpAppraisal.fieldNames[FrmEmpAppraisal.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=empAppraisal.getEmployeeId()%>" class="formElemen">                            </td>
                          </tr>
                          <tr>
                            <td width="18%"><div align="right">Name 
                                : </div></td>
                            <td width="31%"><% if (empAppraisal.getOID() != 0) {%>
                              <%= employee.getFullName() %>
                              <% }%>                            </td>
                            <td width="18%" nowrap><div align="right">Employee 
                                Number : </div></td>
                            <td width="38%"><% if (empAppraisal.getOID() != 0) {%>
                              <%= employee.getEmployeeNum() %>
                              <% }%>                            </td>
                          </tr>
                          <tr>
                            <td width="18%"><div align="right">Position 
                                : </div></td>
                            <td width="31%"><% if (empAppraisal.getOID() != 0) {%>
                              <%= position.getPosition() %>
                              <% }%>                            </td>
                            <td width="18%"><div align="right">Department 
                                : </div></td>
                            <td width="38%"><% if (empAppraisal.getOID() != 0) {%>
                              <%= department.getDepartment() %>
                              <% }%>                            </td>
                          </tr>
                          <tr>
                            <td width="18%" nowrap><div align="right">Commencing 
                                Date : </div></td>
                            <td width="31%"><% if (empAppraisal.getOID() != 0) {%>
                              <%= Formater.formatDate(employee.getCommencingDate(), "dd MMMMM yyyy") %>
                              <% }%>                            </td>
                            <td width="18%">&nbsp;</td>
                            <td width="38%"><% if (empAppraisal.getOID() != 0) {%>
                              <% } else {%>
                              <table cellpadding="0" cellspacing="0" border="0">
                                <tr>
                                  <td width="15"><a href="javascript:cmdSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></td>
                                  <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                  <td class="command" nowrap width="99"></td>
                                </tr>
                              </table></td>
                            <% }%>
                          </tr>
                          <tr>
                            <td colspan="4"><hr></td>
                          </tr>
                          <tr>
                            <td nowrap><div align="right">Department 
                                Appraisor :</div></td>
                            <td>&nbsp;<%=depAppraisor.getDepartment()%></td>
                            <td><div align="right">Appraisor :</div></td>
                            <td>&nbsp;
                              <%
            String appraisorName = "";
            long selAppraisor = 0;

            if (oidEmpAppraisal != 0) {%>
                              <%
                                                                                                    Employee emp = new Employee();
                                                                                                    try {
                                                                                                        emp = PstEmployee.fetchExc(empAppraisal.getAppraisorId());
                                                                                                        appraisorName = emp.getFullName();
                                                                                                    } catch (Exception exc) {
                                                                                                        emp = new Employee(); 
                                                                                                    }
                                                                                                %>
                              <%=appraisorName%>
                              <%}%></td>
                          </tr>
                          <tr>
                            <td colspan="4"><hr></td>
                          </tr>
                          <tr>
                            <td width="18%" class="tabbg"  ><div align="right">Date 
                                of Appraisal : </div></td>
                            <td><%=Formater.formatDate(empAppraisal.getDateOfAppraisal(), "dd MMMMM yyyy")%></td>
                            <td width="18%" class="tabbg"  ><div align="right">Total 
                                Score : </div></td>
                            <td>&nbsp;<%=empAppraisal.getTotalScore()%></td>
                          </tr>
                          <tr>
                            <td width="18%" class="tabbg"  ><div align="right">Date 
                                Performance : </div></td>
                            <td><%=Formater.formatDate(empAppraisal.getDatePerformance(), "dd MMMMM yyyy")%> / <%=Formater.formatDate(empAppraisal.getTimePerformance(), "HH:mm")%></td>
                            <td width="18%" class="tabbg"  ><div align="right">Total 
                                Criteria : </div></td>
                            <td>&nbsp;<%=empAppraisal.getTotalCriteria()%></td>
                          </tr>
                          <tr>
                            <td width="18%" class="tabbg"  ><div align="right">Last 
                                Appraisal : </div></td>
                            <td><% if (empAppraisal != null && empAppraisal.getLastAppraisal() != null) {%>
                              <%=Formater.formatDate(empAppraisal.getLastAppraisal(), "dd MMMMM yyyy")%>
                              <%} else {
                out.println("-");
            }%></td>
                            <td width="18%" class="tabbg"  ><div align="right">Score 
                                Average : </div></td>
                            <td>&nbsp;<%=( (empAppraisal.getTotalScore() < 0) ? "0.0" : (empAppraisal.getScoreAverage()+"&nbsp;&nbsp; ( "+(empAppraisal.getScoreAverage()*20)+"% )"))%></td>
                          </tr>
                        </table></td>
                      <td width="3%">&nbsp;</td>
                    </tr>
                    <tr>
                      <td width="3%"  valign="top" align="left"  >&nbsp;</td>
                      <td  valign="top" align="left"><hr>
                      </td>
                      <td width="3%">&nbsp;</td>
                    </tr>
                    <% if (empAppraisal != null && empAppraisal.getLastAppraisal() != null) {%>
                    <%}%>
                    <% if (oidEmpAppraisal != 0) {%>
                    <% }%>
                    <tr>
                    
                    <td colspan="3">
                    
                    &nbsp;
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <% if (oidEmployee != 0) {

                                                                                    %>
                      <%  
            }%>
                      <%
            try {
                Vector listPerformanceEvaluation = PstPerformanceEvaluation.listPerfEvaluation(oidEmpAppraisal, true);
                //out.println("listPerformanceEvaluation "+listPerformanceEvaluation.size());																				
                if (listPerformanceEvaluation.size() > 0) { 
                                                                                     %>
                      <tr align="left" valign="top">
                        <td height="14" valign="middle" colspan="3" class="listtitle">&nbsp;Performance 
                          Assesment </td>
                      </tr>
                      <tr align="left" valign="top">
                        <td valign="middle" colspan="3"><table width="95%" border="0" cellspacing="1" cellpadding="0" class="listgen">
                            <tr>
                              <td width="4%" class="listgentitle">&nbsp;</td>
                              <td width="37%" class="listgentitle"><div align="center">Appraisal 
                                Category</div></td>
                              <td width="14%" class="listgentitle"><div align="center">Score</div></td>
                              <td width="45%" class="listgentitle"><div align="center">Justification</div></td>
                            </tr>
                            <%
                                                                                            int limit = 1000;



                                                                                            limit = listPerformanceEvaluation.size();

                                                                                            try {
                                                                                                for (int i = start; i < limit; i++) {
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
                              <td width="4%" align="right" valign="top" <%if ((perfEvaluation.getOID() == oidPerformanceEvaluation) && (iCommand == Command.EDIT)) {%>class="tabtitlehidden"<%} else {%>class="listgensell"<%}%>>
                                <div align="right"><a href="javascript:cmdEdit('<%=perfEvaluation.getOID()%>')">&nbsp;<%= num + 1%></a>&nbsp;</div></td>
                              <td width="37%" <%if ((perfEvaluation.getOID() == oidPerformanceEvaluation) && (iCommand == Command.EDIT)) {%>class="tabtitlehidden"<%} else {%>class="listgensell"<%}%>><%=categCriteria.getCriteria()%>
                              <%=((categCriteria.getDesc1()!=null && categCriteria.getDesc1().trim().length()>2)? ("<br><br>"+categCriteria.getDesc1()):"")+
                                         ((categCriteria.getDesc2()!=null && categCriteria.getDesc2().trim().length()>2)? ("<br><br>"+categCriteria.getDesc2()):"")+
                                         ((categCriteria.getDesc3()!=null && categCriteria.getDesc3().trim().length()>2)? ("<br><br>"+categCriteria.getDesc3()):"")+
                                         ((categCriteria.getDesc4()!=null && categCriteria.getDesc4().trim().length()>2)? ("<br><br>"+categCriteria.getDesc4()):"")+
                                         ((categCriteria.getDesc5()!=null && categCriteria.getDesc5().trim().length()>2)? ("<br><br>"+categCriteria.getDesc5()):"")+"<br><br>"%></td>
                              <td width="14%" align="center" valign="top" <%if ((perfEvaluation.getOID() == oidPerformanceEvaluation) && (iCommand == Command.EDIT)) {%>class="tabtitlehidden"<%} else {%>class="listgensell"<%}%>><%=evaluate.getCode()%> (<%=evaluate.getMaxPercentage()%> %)</td>
                              <td width="45%" align="left" valign="top" <%if ((perfEvaluation.getOID() == oidPerformanceEvaluation) && (iCommand == Command.EDIT)) {%>class="tabtitlehidden"<%} else {%>class="listgensell"<%}%>><%=perfEvaluation.getJustification()%></td>
                            </tr>
                            <% num++;
                                                                                                }
                                                                                            } catch (Exception exc) {
                                                                                                System.out.println("exc.............." + exc.toString());
                                                                                            }%>
                          </table></td>
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
                        <td height="8" align="left" colspan="3" class="command"><span class="command">
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
                          <%
            whereClause = PstPerformanceEvaluation.fieldNames[PstPerformanceEvaluation.FLD_EMPLOYEE_APPRAISAL] + " = " + oidEmpAppraisal;
            int vectSize = PstPerformanceEvaluation.getCount(whereClause);

                                                                                                %>
                          <%//ctrLine.drawImageListLimit(cmd,vectSize,start,1000)%>
                          </span> </td>
                      </tr>
                      <%if (true) {%>
                      <% if (true) {%>
                      <tr align="left" valign="top">
                        <td height="22" valign="middle" colspan="3">
<table width="95%" border="0" cellspacing="2" cellpadding="1" class="listgen">
  <tr>
    <td height="16" colspan="2" class="listgentitle">Development 
      and Improvement</td>
    </tr>
  <%
            try {
                int limit = 1000;
                Vector listDevImprovement = PstDevImprovement.listDevImprovement(oidEmpAppraisal);
                for (int i = start; i < limit; i++) {
                    Vector vector = (Vector) listDevImprovement.get(i);
                    Vector numbers = (Vector) vector.get(0);
                    GroupCategory groupCategory = (GroupCategory) vector.get(1);
                    DevImprovement devImprov = (DevImprovement) vector.get(2);

                    int num = Integer.parseInt("" + numbers.get(0));

                    if (num == 0) {
                                                                                                %>
  <tr>
    <td colspan="2" class="listgensell"><b color="#0000FF"><%=groupCategory.getGroupName()%></b></td>
  </tr>
  <%	}%>
  <tr>
    <td width="4%" align="right" valign="top" nowrap <%if ((devImprov.getOID() == oidDevImprovement) && (iCommand == Command.EDIT)) {%>class="tabtitlehidden"<%} else {%>class="listgensell"<%}%>>&nbsp;
      <div align="right"><a href="javascript:cmdEdit('<%=devImprov.getOID()%>')"><%= num + 1%></a>&nbsp;</div></td>
    <td width="96%" <%if ((devImprov.getOID() == oidDevImprovement) && (iCommand == Command.EDIT)) {%>class="tabtitlehidden"<%} else {%>class="listgensell"<%}%>><%=devImprov.getImprovement()%></td>
  </tr>
  <%
                }
            } catch (Exception exc) {
            //System.out.println("exc.............."+exc.toString());
            }%>
</table>
						
						
						
						</td>
                      </tr>
                      <tr align="left" valign="top">
                        <td height="22" valign="middle" colspan="3"></td>
                      </tr>
                      <tr align="left" valign="top">
                        <td height="22" valign="middle" colspan="3">	
<table width="95%" border="0" cellspacing="2" cellpadding="1" class="listgen">
<%
            try {
                Vector listDevImprovementPlan = PstDevImprovementPlan.listDevImprovementPlan(oidEmpAppraisal);
                if (listDevImprovementPlan.size() > 0) { 
                                                                                    %>
 
  <tr>
    <td width="4%" class="listgentitle" height="16">&nbsp;</td>
    <td height="16" class="listgentitle" width="8%">Recommend</td>
    <td height="16" class="listgentitle" width="88%">Development 
      and Improvement 
      Plan</td>  
  </tr>
  <% 
                                                                 try {   
                                                                    int limit = 1000;                                                                   

                                                                    for (int i = start; i < limit; i++) {
                                                                        Vector vector = (Vector) listDevImprovementPlan.get(i);
                                                                        Vector numbers = (Vector) vector.get(0);
                                                                        CategoryAppraisal categApp = (CategoryAppraisal) vector.get(1);
                                                                        DevImprovementPlan devImprov = (DevImprovementPlan) vector.get(2);

                                                                        int num = Integer.parseInt("" + numbers.get(0));

                                                                        if (num == 0) {
                                                    %>
  <tr>
    <td colspan="3" class="listgensell">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b color="#0000FF"><%=categApp.getCategory()%></b></td>
  </tr>
  <%	}
                                                        System.out.println("devImprov.getRecommend()" + devImprov.getRecommend());%>
  <tr>
    <td width="4%" align="right" valign="top" nowrap <%if ((devImprov.getOID() == oidDevImprovementPlan) && (iCommand == Command.EDIT)) {%>class="tabtitlehidden"<%} else {%>class="listgensell"<%}%>>&nbsp;
      <div align="right"><a href="javascript:cmdEdit('<%=devImprov.getOID()%>')"><%= num + 1%></a>&nbsp;&nbsp;&nbsp;</div></td>
    <td width="8%" <%if ((devImprov.getOID() == oidDevImprovementPlan) && (iCommand == Command.EDIT)) {%>class="tabtitlehidden"<%} else {%>class="listgensell"<%}%>><%if (devImprov.getRecommend()) {%>
      <%="Yes"%>
      <%} else {%>
      <%="No"%>
      <%}%></td>
    <td width="88%" <%if ((devImprov.getOID() == oidDevImprovementPlan) && (iCommand == Command.EDIT)) {%>class="tabtitlehidden"<%} else {%>class="listgensell"<%}%>><%=devImprov.getImprovPlan()%></td>
  </tr>
  <%
                                                                    }
                                                                } catch (Exception exc) {
                                                                    System.out.println("exc.............." + exc.toString());
                                                                }%>

<%
                                                                                    }
                                                                                    } catch ( Exception exc){
                                                                                    System.out.println("exc Dev & Improvement Plan" + exc.toString());
                                                                                    }
                                                                                    %>
</table>						
						
						
											</td>
                      </tr>
                      <tr align="left" valign="top">
                        <td height="22" valign="middle" colspan="3"><table cellpadding="0" cellspacing="0" border="0">
                            <tr>
                              <td>&nbsp;</td>
                            </tr>
                          </table></td>
                      </tr>
                      <%}
            }%>

                      
                      <tr align="left" valign="top">
                        <td height="22" valign="middle" colspan="3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="3%">&nbsp;</td>
                            <td width="37%">&nbsp;Employee</td>
                            <td width="9%">&nbsp;</td>
                            <td width="48%">&nbsp;Appraisor</td>
                            <td width="3%">&nbsp;</td>
                          </tr>
                          <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;<% if (empAppraisal.getOID() != 0) {%>
                              <%= employee.getFullName() %>
                              <% }%>              </td>
                            <td>&nbsp;</td>
                            <td>&nbsp;<% if (oidEmpAppraisal != 0) {%>
                              <%
                                                                                                    Employee emp = new Employee();
                                                                                                    try {
                                                                                                        emp = PstEmployee.fetchExc(empAppraisal.getAppraisorId());
                                                                                                        appraisorName = emp.getFullName();
                                                                                                    } catch (Exception exc) {
                                                                                                        emp = new Employee(); 
                                                                                                    }
                                                                                                %>
                              <%=appraisorName%>
                              <%}%></td>
                            <td>&nbsp;</td>
                          </tr>
                        </table></td>
                      </tr>
                      <tr align="left" valign="top">
                        <td height="22" valign="middle" colspan="3">&nbsp;</td>
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
          </form>
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
    <td colspan="2" height="20" <%=bgFooterLama%>>&nbsp;</td>
  </tr>
</table>
</body>
</html>
 