

<%@page import="com.dimata.util.lang.I_Language"%>
<%@page import="com.dimata.harisma.entity.payroll.PaySimulationStructure"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPaySimulationStructure"%>
<%@page import="com.dimata.harisma.entity.payroll.PayComponent"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayComponent"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<% 
/* 
 * Page Name  		:  paysimulation.jsp
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
<%@ page import = "com.dimata.harisma.entity.payroll.PstPaySimulation" %>
<%@ page import = "com.dimata.harisma.entity.payroll.PaySimulation" %>
<%@ page import = "com.dimata.harisma.entity.search.SrcPaySimulation" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.system.entity.PstSystemProperty" %>
<%@ page import = "com.dimata.util.blob.*" %>
<%@page import="com.dimata.harisma.form.payroll.CtrlPaySimulation"%>
<%@page import="com.dimata.harisma.form.payroll.FrmPaySimulation"%>
<%@page import="com.dimata.harisma.form.payroll.SrcFrmPaySimulation"%>
<%@ include file = "../../main/javainit.jsp" %>
<% 
    int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL , AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_SIMULATION); 
    
%>
<%@ include file = "../../main/checkuser.jsp" %>

<!-- Jsp Block -->

<!-- Jsp Block -->
<%!
	public String drawList(Vector objectClass,  long simulationId)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("99%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Title","20%");
		ctrlist.addHeader("Objective","30%");
                ctrlist.addHeader("Max. Add.Salary","30%");
                ctrlist.addHeader("Max. Add.Employee","30%");
		ctrlist.addHeader("Status","30%");
                ctrlist.addHeader("Created By", "20%");

		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
	
                
		for (int i = 0; i < objectClass.size(); i++) {
			 PaySimulation paySimulation = (PaySimulation) objectClass.get(i);
			 Vector rowx = new Vector();
			rowx.add(""+paySimulation.getTitle());
			rowx.add(""+paySimulation.getObjectives() );
			rowx.add(""+Formater.formatNumber(paySimulation.getMaxTotalBudget(), "###,###"));			
			rowx.add(""+paySimulation.getMaxAddEmployee() );
                        rowx.add(""+paySimulation.getStatusDoc());
			rowx.add(""+paySimulation.getCreadedById() );
			lstData.add(rowx);
			lstLinkData.add(String.valueOf(paySimulation.getOID()));
		}
		return ctrlist.draw(index);
	}
	
	public String chekOID(long oid, Vector vct){
		if(vct!=null && vct.size()>0){
			for(int i=0; i<vct.size(); i++){
				TrainingDept td = (TrainingDept)vct.get(i);
				if(td.getDepartmentId() == oid){
					return "checked";
				}
			}
		}
		return "";
	}
%>
<%
int iCommand    = FRMQueryString.requestCommand(request);
int prevCommand = FRMQueryString.requestInt(request, "prev_command"); 
SrcPaySimulation srcPaySimulation = (SrcPaySimulation)session.getValue("SESS_PAY_SIMULATION");


long oidPaySimulation = FRMQueryString.requestLong(request, SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]);

if(srcPaySimulation==null) {
    srcPaySimulation = new SrcPaySimulation();
}
 int vectSize=0;
 int start = FRMQueryString.requestInt(request, "start");
 int recordToGet = srcPaySimulation.getRecordToGet();
 
String orderClause = ""+ srcPaySimulation.getSortBy();
String whereClause = ""+ srcPaySimulation.getWhere();


ControlLine ctrLine = new ControlLine();

int iErrCode = 0 ; 


    CtrlPaySimulation ctrPaySimulation = new CtrlPaySimulation(request);
   
    iErrCode = ctrPaySimulation.action(iCommand, oidPaySimulation);
    FrmPaySimulation frmPaySimulation = ctrPaySimulation.getForm();
    PaySimulation paySimulation = ctrPaySimulation.getPaySimulation();
    
  
    String msgString = ctrPaySimulation.getMessage();
    
    if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
        start = PstPaySimulation.findLimitStart(paySimulation.getOID(), recordToGet, "", orderClause);
    }

    if ((iCommand == Command.FIRST || iCommand == Command.PREV)
            || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
        start = ctrPaySimulation.actionList(iCommand, start, vectSize, recordToGet);
    }
     

%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>Harisma - Payroll Simulation</title>
<script language="JavaScript">


function cmdAdd(){
	document.frmpaysimulation.<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]%>.value="0";
	document.frmpaysimulation.command.value="<%=Command.ADD%>";
	document.frmpaysimulation.prev_command.value="<%=prevCommand%>";
	document.frmpaysimulation.action="paysimulation_struct.jsp";
	document.frmpaysimulation.submit();
}

function cmdAsk(oidPaySimulation){
	document.frmpaysimulation.<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]%>.value=oidPaySimulation;
	document.frmpaysimulation.command.value="<%=Command.ASK%>";
	document.frmpaysimulation.prev_command.value="<%=prevCommand%>";
	document.frmpaysimulation.action="paysimulation_struct.jsp";
	document.frmpaysimulation.submit();
}

function cmdConfirmDelete(oidPaySimulation){
	document.frmpaysimulation.<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]%>.value=oidPaySimulation;
	document.frmpaysimulation.command.value="<%=Command.RESET%>";
	document.frmpaysimulation.prev_command.value="<%=prevCommand%>";
	document.frmpaysimulation.action="paysimulation_struct.jsp";
	document.frmpaysimulation.submit();
}

function cmdSave(){
	document.frmpaysimulation.command.value="<%=Command.SUBMIT%>";
	document.frmpaysimulation.prev_command.value="<%=prevCommand%>";
	document.frmpaysimulation.action="paysimulation_struct.jsp";
	document.frmpaysimulation.submit();
        lockScreen(<%=(SESS_LANGUAGE== I_Language.LANGUAGE_DEFAULT ? "'Menghitung dan menyimpan simulasi gaji. Mohon tunggu...'": "'We are  calculate & saving payroll simulation, please wait...'") %>);
}

function cmdGenerate(){
        if(confirm("<%=(SESS_LANGUAGE== I_Language.LANGUAGE_DEFAULT ? "'Membuat simulasi gaji. Akan mengambil data gaji baru, dan simulasi struktur yang ada di reset. Lanjutkan ?'": "'Generating payroll simulation will reset existing structure? Please click Ok to continue.'") %>")){
	document.frmpaysimulation.command.value="<%=Command.LOAD%>";
	document.frmpaysimulation.prev_command.value="<%=prevCommand%>";
	document.frmpaysimulation.action="paysimulation_struct.jsp";
	document.frmpaysimulation.submit();
        lockScreen(<%=(SESS_LANGUAGE== I_Language.LANGUAGE_DEFAULT ? "'Membuat simulasi gaji. Mohon tunggu...'": "'We are  generating payroll simulation, please wait...'") %>);
    }
}

function cmdEdit(oidPaySimulation){
	document.frmpaysimulation.<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]%>.value=oidPaySimulation;
	document.frmpaysimulation.command.value="<%=Command.EDIT%>";
	document.frmpaysimulation.prev_command.value="<%=prevCommand%>";
	document.frmpaysimulation.action="paysimulation_struct.jsp";
	document.frmpaysimulation.submit();
}
	
function cmdCancel(oidPaySimulation){
	document.frmpaysimulation.<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]%>.value=oidPaySimulation;
	document.frmpaysimulation.command.value="<%=Command.EDIT%>";
	document.frmpaysimulation.prev_command.value="<%=prevCommand%>";
	document.frmpaysimulation.action="paysimulation_struct.jsp";
	document.frmpaysimulation.submit();
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
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
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
	
    function showObjectForMenu(){
    }
    
</SCRIPT>

<style type="text/css">
      .LockOff {
         display: none;
         visibility: hidden;
      }

      .LockOn {
         display: block;
         visibility: visible;
         position: absolute;
         z-index: 999;
         top: 0px;
         left: 0px;
         width: 105%;
         height: 105%;
         background-color: #ccc;
         text-align: center;
         padding-top: 20%;
         filter: alpha(opacity=75);
         opacity: 0.75;
         font-size: 250%;
      }
   </style>

   <script type="text/javascript">
      function lockScreen(str)
      {
         var lock = document.getElementById('theLockPane');
         if (lock)
            lock.className = 'LockOn';

         lock.innerHTML = str;
      }
   </script>   
<!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Payroll Simulation Structure<!-- #EndEditable --> 
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
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frmpaysimulation" method ="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start %>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name=<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_PAY_SIMULATION_ID]%> value="<%=oidPaySimulation%>">
                                      <input type="hidden" name=<%=SrcFrmPaySimulation.fieldNames[SrcFrmPaySimulation.FRM_FIELD_CREATED_BY_ID]%> value="<%=(userSession!=null && userSession.getEmployee()!=null? userSession.getEmployee().getOID():0) %>">				  
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr align="left" valign="top"> 
                                          <td height="8" valign="middle" colspan="3"> 
                                            <%if( 1==1 ||(iCommand ==Command.ADD)||iCommand==Command.SAVE ||iCommand==Command.SUBMIT  || iCommand==Command.LOAD || (iErrCode!=FRMMessage.NONE)||(iCommand==Command.EDIT)||(iCommand==Command.ASK)){%>
                                            <table width="97%" border="0" cellspacing="1" cellpadding="0">
                                              <tr align="left" valign="top"> 
                                                <td valign="middle" colspan="4"><b>Payroll Simulation</b></td>
                                              </tr>
                                              <tr align="left" valign="top"> 
                                                <td  valign="middle" >&nbsp;</td>
                                                <td colspan="3" class="comment">*)= 
                                                  required
                                                </td>
                                                
                                              </tr>
                                              <tr valign="top">
                                                <td colspan="4" valign="top">
                                                  <table>
                                                    <tr valign="top"> 
                                                     <td valign="top">
                                                      <table>
                                                        <tr align="left" valign="top"> 
                                                          <td valign="top">Title</td>
                                                          <td  > 
                                                            <input type="text" name="<%=FrmPaySimulation.fieldNames[FrmPaySimulation.FRM_FIELD_TITLE] %>"  value="<%= paySimulation.getTitle() %>" class="formElemen" size="50">
                                                            * <%= frmPaySimulation.getErrorMsg(FrmPaySimulation.FRM_FIELD_TITLE) %> 
                                                          </td>
                                                        </tr>
                                                        <tr align="left" valign="top"> 
                                                        <td  valign="top" >Objectives</td>
                                                        <td > 
                                                          <textarea name="<%=FrmPaySimulation.fieldNames[FrmPaySimulation.FRM_FIELD_OBJECTIVES] %>" class="formElemen" cols="50" rows="3"><%= paySimulation.getObjectives() %></textarea>
                                                        </td>
                                                        
                                                       </tr>
                                                        <tr align="left" valign="top"> 
                                                          <td valign="top" >Max Total Budget</td>
                                                          <td > 
                                                            <input type="text" name="<%=FrmPaySimulation.fieldNames[FrmPaySimulation.FRM_FIELD_MAX_TOTAL_BUDGET] %>"  value="<%= Formater.formatNumber(paySimulation.getMaxTotalBudget(), "######") %>" class="formElemen" size="20">
                                                            * <%= frmPaySimulation.getErrorMsg(FrmPaySimulation.FRM_FIELD_MAX_TOTAL_BUDGET) %> 
                                                          </td>
                                                        </tr>
                                                        <tr align="left" valign="top"> 
                                                         <td  valign="top">Max Total Employee</td>
                                                         <td height="21" > 
                                                           <input type="text" name="<%=FrmPaySimulation.fieldNames[FrmPaySimulation.FRM_FIELD_MAX_ADD_EMPL] %>"  value="<%= paySimulation.getMaxAddEmployee() %>" class="formElemen" size="10">
                                                           * <%= frmPaySimulation.getErrorMsg(FrmPaySimulation.FRM_FIELD_MAX_ADD_EMPL) %> 
                                                         </td>
                                                        </tr>
                                                        <tr align="left" valign="top"> 
                                                         <td  valign="top">Source of Payroll Period</td>
                                                         <td >
                                                              <%
                                                            Vector perValue = new Vector(1, 1);
                                                            Vector perKey = new Vector(1, 1);
                                                            Vector listPeriod = PstPayPeriod.list(0, 0, "", "START_DATE DESC");

                                                            for (int r = 0; r < listPeriod.size(); r++) {
                                                                PayPeriod payPeriod = (PayPeriod) listPeriod.get(r);
                                                                perValue.add("" + payPeriod.getOID());
                                                                perKey.add(payPeriod.getPeriod());
                                                            }
                                                            %> <%= ControlCombo.draw(FrmPaySimulation.fieldNames[FrmPaySimulation.FRM_FIELD_SOURCE_PAY_PERIOD_ID], null, "" + paySimulation.getSourcePayPeriodId() , perValue, perKey, "")%>
                                                            * <%= frmPaySimulation.getErrorMsg(FrmPaySimulation.FRM_FIELD_SOURCE_PAY_PERIOD_ID) %> 
                                                            </td>
                                                          </tr>  
                                                           <tr align="left" valign="top"> 
                                                            <td  valign="top">&nbsp;</td>
                                                            <td > &nbsp;</td>
                                                          </tr>
                                                          <tr align="left" valign="top"> 
                                                            <td  valign="top">&nbsp;</td>
                                                            <td > &nbsp;
                                                           
                                                            </td>
                                                          </tr>  
                                                      </table>
                                                    </td>
                                                    <td>
                                                         <table>
                                                        <tr align="left" valign="top"> 
                                                          <td  valign="top" >Type of employee</td>
                                                          <td  > 
                                                              <%
                                                                Vector empCategories = PstEmpCategory.list(0, 0, "", PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY ]);
                                                                 Vector catValue = new Vector(1, 1);
                                                                  Vector catKey = new Vector(1, 1);


                                                                  for (int r = 0; r < empCategories.size(); r++) {
                                                                      EmpCategory empCat = (EmpCategory) empCategories.get(r);
                                                                      catValue.add("" + empCat.getOID());
                                                                      catKey.add(empCat.getEmpCategory() );
                                                                  }
                                                                 %>

                                                                 <%= ControlCheckBox.draw(FrmPaySimulation.fieldNames[FrmPaySimulation.FRM_FIELD_EMPLOYEE_CATEGORIES], "", paySimulation.getEmployeeCategoryIds(), catValue,catKey,  "", 5)%>


                                                            * <%= frmPaySimulation.getErrorMsg(FrmPaySimulation.FRM_FIELD_EMPLOYEE_CATEGORIES) %> 
                                                          </td>
                                                        </tr>
                                                        <tr align="left" valign="top"> 
                                                         <td  valign="top"  rowspan="=4">Payroll Component</td>
                                                         <td> 
                                                            <%
                                                              Vector payComponents = PstPayComponent.list(0, 0, "", PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE ] +"," + PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_CODE]);
                                                               Vector compValue = new Vector(1, 1);
                                                               Vector compKey = new Vector(1, 1);

                                                                for (int r = 0; r < payComponents.size(); r++) {
                                                                    PayComponent comp = (PayComponent) payComponents.get(r);
                                                                    compValue.add("" + comp.getOID());
                                                                    compKey.add(comp.getCompCode()+" "+comp.getCompName());
                                                                }


                                                               %>
                                                               <%= ControlCombo.drawStringArraySelected(FrmPaySimulation.fieldNames[FrmPaySimulation.FRM_FIELD_PAYROLL_COMPONENTS], null,null, paySimulation.getPayrollComponents() ,  compKey, compValue, null, "multiple=\"multiple\" size=\"9\" ") %>

                                                               <% // ControlCombo.draw(FrmPaySimulation.fieldNames[FrmPaySimulation.FRM_FIELD_PAYROLL_COMPONENTS], null, "" + paySimulation.getSourcePayPeriodId() , compValue, compKey, "multiple=\"multiple\" size=\"10\" ")%>

                                                          * <%= frmPaySimulation.getErrorMsg(FrmPaySimulation.FRM_FIELD_PAYROLL_COMPONENTS) %> 
                                                        </td>
                                                       </tr>   
                                                      </table>
                                                    </td>
                                                 </tr>
                                                </table>
                                       </td>
                                     </tr>
                                             
                                              <tr align="left" valign="top"> 
                                                 <td  colspan="4">
<%
          
 Vector vPaySimStruct = PstPaySimulationStructure.listByPaySimulation(paySimulation);
 if(vPaySimStruct!=null && vPaySimStruct.size()>0){
   %>
   
   <table class="listgen">
        <tr>
          <td class="tableheader">No.</td><td class="tableheader">Company</td>
          <td class="tableheader">Division</td>
          <td class="tableheader">Department</td>
          <td class="tableheader">Section</td>
          <td class="tableheader">Position</td>
          <td class="tableheader">Level Code</td>
          <td class="tableheader">#Employee</td>
          <td class="tableheader">Salary</td>
          <td class="tableheader">Current</td>
          <td class="tableheader">Add.Salary</td>             
          <td class="tableheader">Add.Employee</td> 
          <td class="tableheader">Total Add</td>
          <td class="tableheader">Sub Total</td>
       </tr>
       <%
         PaySimulationStructure paySimStructPrev = new PaySimulationStructure() ;
         double grandCurrentSalary =0.0;
         double grandAddSalary = 0.0;
         int grandAddEmployee =0;
         int totalComp=0, totalDiv=0, totalDep=0, totalSec=0, totalPos=0, totalLevel=0, totalEmployee=0;
         
         double grandTotalSalary =0;
         for(int idx=0; idx < vPaySimStruct.size(); idx++ ){
             PaySimulationStructure paySimStruct= (PaySimulationStructure)vPaySimStruct.get(idx);
             if(paySimStructPrev.getCompanyId()!=paySimStruct.getCompanyId()){
                 totalComp++;
             }
             if(paySimStructPrev.getDivisionId()!=paySimStruct.getDivisionId()){
                 totalDiv++;      
             }
             if(paySimStructPrev.getDepartmentId()!=paySimStruct.getDepartmentId()){
                 totalDep++;      
             }
             if(paySimStructPrev.getSectionId()!=paySimStruct.getSectionId() && paySimStruct.getSectionId()!=0 ){
                 totalSec++;      
             }             

             double totalPrev  = paySimStruct.getSalaryAmount() * paySimStruct.getEmployeeCount();
             double subTotal =(paySimStruct.getSalaryAmount() + paySimStruct.getSalaryAmountAdd()) * ( paySimStruct.getEmployeeCount() + paySimStruct.getNewEmployeeAdd());
             double totalAdd = subTotal- totalPrev; 
             grandCurrentSalary = grandCurrentSalary + totalPrev;
             grandAddSalary = grandAddSalary + totalAdd;
             grandTotalSalary = grandTotalSalary + subTotal;
             totalEmployee = totalEmployee + paySimStruct.getEmployeeCount();
             paySimStructPrev = paySimStruct;
             grandAddEmployee =grandAddEmployee + paySimStruct.getNewEmployeeAdd();
             %>
             <tr>
                <td  class="tablecell"><%=(idx+1) %></td><td  class="tablecell"><%=paySimStruct.getCompany() %></td>
                <td  class="tablecell"><%=paySimStruct.getDivision()%></td>
                <td  class="tablecell"><%=paySimStruct.getDepartment()%></td>
                <td  class="tablecell"><%=paySimStruct.getSection()%></td>
                <td  class="tablecell"><%=paySimStruct.getPosition()%></td>
                <td  class="tablecell"><%=paySimStruct.getLevelCode()%></td>
                <td  class="tablecell"><%=paySimStruct.getEmployeeCount() %></td>
                <td  class="tablecell" align="right" ><%= Formater.formatNumber(paySimStruct.getSalaryAmount(), "###,###") %></td>
                <td  class="tablecell" align="right" ><%= Formater.formatNumber(totalPrev, "###,###")%></td>
                <td  class="tablecell"><input  align="right"  type="text" name="<%=( "ADD_AMOUNT_"+paySimStruct.getOID()) %>"   <%//PstPaySimulationStructure.getKeyMap(paySimStruct)) %> size="8" value="<%=paySimStruct.getSalaryAmountAdd() %>"></td>             
                <td  class="tablecell"><input  align="right"  type="text" name="<%=( "ADD_EMPLOYEE_"+paySimStruct.getOID()) %>"  <% //PstPaySimulationStructure.getKeyMap(paySimStruct)) %> size="8" value="<%=paySimStruct.getNewEmployeeAdd() %>"></td> 
                <td  class="tablecell"  align="right" ><%= Formater.formatNumber(totalAdd, "###,###")%></td>
                <td  class="tablecell"  align="right" ><%= Formater.formatNumber(subTotal, "###,###")%></td>
             </tr>        
             
       <%}%>
        <tr>
                <td  class="tablecell">&nbsp;</td><td  class="tablecell"><%=totalComp %></td>
                <td  class="tablecell"><%=totalDiv%></td>
                <td  class="tablecell"><%=totalDep%></td>
                <td  class="tablecell"><%=totalSec%></td>
                <td  class="tablecell"></td>
                <td  class="tablecell"></td>
                <td  class="tablecell"><%=totalEmployee %></td>
                <td  class="tablecell">&nbsp;</td>
                <td  class="tablecell" align="right" ><%= Formater.formatNumber(grandCurrentSalary, "###,###")%></td>
                <td  class="tablecell">&nbsp;</td>             
                <td  class="tablecell" align="right" ><%= Formater.formatNumber(grandAddEmployee, "###,###")%></td> 
                <td  class="tablecell" align="right" ><%= Formater.formatNumber(grandAddSalary, "###,###")%></td>
                <td  class="tablecell" align="right" ><%= Formater.formatNumber(grandTotalSalary, "###,###")%></td>
             </tr>        
        <tr>
                <td  class="tablecell">&nbsp;</td><td  class="tablecell"></td>
                <td  class="tablecell"></td>
                <td  class="tablecell"></td>
                <td  class="tablecell"></td>
                <td  class="tablecell"></td>
                <td  class="tablecell"></td>
                <td  class="tablecell"></td>
                <td  class="tablecell">&nbsp;</td>
                <td  class="tablecell"></td>
                <td  class="tablecell">Budget</td>             
                <td  class="tablecell" align="right" ><%= Formater.formatNumber(paySimulation.getMaxAddEmployee(), "###,###")%></td> 
                <td  class="tablecell" align="right" ><%= Formater.formatNumber(paySimulation.getMaxTotalBudget() , "###,###")%></td>
                <td  class="tablecell"></td>
             </tr>   
        <tr>
                <td  class="tablecell">&nbsp;</td><td  class="tablecell"></td>
                <td  class="tablecell"></td>
                <td  class="tablecell"></td>
                <td  class="tablecell"></td>
                <td  class="tablecell"></td>
                <td  class="tablecell"></td>
                <td  class="tablecell"></td>
                <td  class="tablecell">&nbsp;</td>
                <td  class="tablecell"></td>
                <td  class="tablecell">Diff.</td>             
                <td  class="tablecell" align="right" ><%= Formater.formatNumber(grandAddEmployee-paySimulation.getMaxAddEmployee(), "###,###")%></td> 
                <td  class="tablecell" align="right" ><%= Formater.formatNumber(grandAddSalary-paySimulation.getMaxTotalBudget() , "###,###")%></td>
                <td  class="tablecell" align="right" ></td>
             </tr>               
             
   </table>
   <%
 }else{
     out.println("<h3>NO Simulation structure is found, please generate !</h3>");
 }
%>                                                                
                                                 </td>
                                              </tr>
                                              <tr align="left" valign="top" > 
                                                <td valign="top"> <img src="../../images/BtnGearOn.jpg" onclick="javascript:cmdGenerate();" > <a href="javascript:cmdGenerate();" valign="top">Generate & Reset !</a> </td>
                                                <td colspan="3" class="command"> 
                                                  <%  iCommand= iCommand != Command.EDIT && iCommand != Command.ASK ? Command.EDIT : iCommand; 
                                                      
									ctrLine.setLocationImg(approot+"/images");
									ctrLine.initDefault();
									ctrLine.setTableWidth("80%");
									String scomDel = "javascript:cmdAsk('"+oidPaySimulation+"')";
									String sconDelCom = "javascript:cmdConfirmDelete('"+oidPaySimulation+"')";
									String scancel = "javascript:cmdEdit('"+oidPaySimulation+"')";
									ctrLine.setBackCaption("Back to List");
									ctrLine.setCommandStyle("buttonlink");
										ctrLine.setDeleteCaption("Delete");
										ctrLine.setSaveCaption("Save All");
										ctrLine.setAddCaption("Add new");
										ctrLine.setDeleteCaption(( vPaySimStruct==null ||vPaySimStruct.size()<1) ? "": "Delete Structure");
                                                                                ctrLine.setBackCaption("");    
									if (privDelete){
										ctrLine.setConfirmDelCommand(sconDelCom);
										ctrLine.setDeleteCommand(scomDel);
										ctrLine.setEditCommand(scancel);
										
									}else{ 
										ctrLine.setConfirmDelCaption("");
										ctrLine.setDeleteCaption("");
										ctrLine.setEditCaption("");
										
									}
									
									if(privAdd == false  && privUpdate == false){
										ctrLine.setSaveCaption("");
									}
									
									if (privAdd == false){
										ctrLine.setAddCaption("");
										
										
									}
									%>
                                                  <%= ctrLine.drawImage(iCommand, iErrCode, msgString)%> 
                                                </td>
                                              </tr>

                                              <tr align="left" valign="top" > 
                                                <td colspan="4"> 
                                                  <div align="left"></div>
                                                </td>
                                              </tr>
                                            </table>
                                             <% } // end if %>
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
<div id="theLockPane" class="LockOff"></div> 
</body>

<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
