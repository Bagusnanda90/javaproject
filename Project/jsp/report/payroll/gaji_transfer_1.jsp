
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPaySlipGroup"%>
<%@page import="com.dimata.harisma.entity.payroll.PaySlipGroup"%>
<%@ page language="java" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.qdep.db.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.harisma.session.payroll.*" %>

<!-- package java -->
<%@ page import ="java.util.*, com.dimata.harisma.session.payroll.SessPaySlip,
                  com.dimata.harisma.entity.payroll.Query,
                  com.dimata.harisma.entity.payroll.PstQuery,
				  com.dimata.harisma.entity.payroll.SalaryLevel,
				  com.dimata.harisma.entity.payroll.PstSalaryLevel,
				  com.dimata.harisma.entity.payroll.PayEmpLevel,
				  com.dimata.harisma.entity.payroll.PstPayEmpLevel,
				  com.dimata.harisma.entity.payroll.PayComponent,
				  com.dimata.harisma.entity.payroll.PstPayComponent,
				  com.dimata.harisma.entity.payroll.PaySlip,
				  com.dimata.harisma.entity.payroll.PstPaySlip,
				  com.dimata.harisma.entity.payroll.PstSalaryLevelDetail,
				  com.dimata.harisma.entity.payroll.PaySlipComp,
				  com.dimata.harisma.entity.payroll.PstPaySlipComp,
                  com.dimata.harisma.form.payroll.FrmQuery,
				  com.dimata.harisma.form.payroll.CtrlQuery,
				  com.dimata.harisma.entity.payroll.PstPayEmpLevel,
	              com.dimata.harisma.entity.employee.Employee,
				  com.dimata.harisma.session.employee.SessEmployee,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
				  com.dimata.harisma.entity.payroll.PayAdditional,
				  com.dimata.harisma.entity.payroll.PstPayAdditional,
				  com.dimata.harisma.form.payroll.CtrlPayAdditional,
				  com.dimata.harisma.form.payroll.FrmPayAdditional,
                  com.dimata.harisma.entity.employee.PstEmployee"%>
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_PROCESS_PRINT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%!
public Vector drawList(Vector objectClass,long oidPeriod)
	{
		Vector result = new Vector(1,1);
		Vector vectCompBenefit= new Vector(1,1);
		Vector vectCompDeduction= new Vector(1,1);
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("90%");
		ctrlist.setListStyle("tblStyle");
                ctrlist.setTitleStyle("title_tbl");
                ctrlist.setCellStyle("listgensell");
                ctrlist.setHeaderStyle("title_tbl");
                ctrlist.setCellSpacing("0");
		ctrlist.addHeader("No","","0","0");
		ctrlist.addHeader("Name","","0","0");
		ctrlist.addHeader("Department","5%","0","0");
		ctrlist.addHeader("Section","","0","0");
		ctrlist.addHeader("Comm.Date","","0","0");
		ctrlist.addHeader("Bank Acc. Nr.","","0","0");
		ctrlist.addHeader("Total Transfered","","0","0");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector vectDataToPdf = new Vector(1,1);
		Vector vectFooter = new Vector(1,1);
		String whereClause = "";
		String frmCurrency = "#,###";
		//mengambil content dari query
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
		double totalTransfered = 0;
                long oidTraining = 0;
                try{
                oidTraining = Long.parseLong(PstSystemProperty.getValueByName("OID_TRAINING"));
                } catch(Exception exc){
                    
                }
                
		for (int i = 0; i <=objectClass.size(); i++) {
			if(i==objectClass.size()){
			//untuk footer
				 Vector rowx = new Vector();
				  Vector rowxPdf = new Vector();
				  rowx.add("<b>Total</b>");
				  rowxPdf.add("Total");
				  rowx.add("&nbsp;");
				  rowxPdf.add("");
				  rowx.add("&nbsp;");
				  rowxPdf.add("");
				  rowx.add("&nbsp;");
				  rowxPdf.add("");
				  rowx.add("&nbsp;");
				  rowxPdf.add("");
				  rowx.add("&nbsp;");
				  rowxPdf.add("");
				  rowx.add("<b><div align=\"right\">"+Formater.formatNumber(totalTransfered, frmCurrency)+"</div></b>");
				  rowxPdf.add(""+Formater.formatNumber(totalTransfered, frmCurrency));
				lstData.add(rowx);
			 	vectDataToPdf.add(rowxPdf);
			}else{
				 Vector temp = (Vector)objectClass.get(i);
				 Employee employee = (Employee)temp.get(0);
                                 PayEmpLevel payEmpLevel = (PayEmpLevel)temp.get(1);
                                 Religion religion = (Religion)temp.get(2);
                                 Position position = (Position)temp.get(3);
                                 PaySlip paySlip = (PaySlip)temp.get(4);
                                 Department department = (Department)temp.get(5);
                                 Vector rowx = new Vector();
                                 Vector rowxPdf = new Vector();
                                 rowx.add(String.valueOf(i+1));
                                 rowxPdf.add(String.valueOf(i+1));
                                 rowx.add(""+employee.getFullName());
                                 rowxPdf.add(""+employee.getFullName());
                                 rowx.add(""+department.getDepartment());
                                 rowxPdf.add(""+department.getDepartment());
                                 Section section = new Section();
                                 String secName = "";
                                 try{
                                                section = PstSection.fetchExc(employee.getSectionId());
                                                secName = section.getSection();
                                        }
                                        catch(Exception e){
                                  }
                                 rowx.add(""+secName);
                                 rowxPdf.add(""+secName);
                                 rowx.add(""+Formater.formatTimeLocale(employee.getCommencingDate(),"dd-MMM-yyyy"));
                                 rowxPdf.add(""+Formater.formatTimeLocale(employee.getCommencingDate(),"dd-MMM-yyyy"));
                                 rowx.add("<div align=\"right\">"+payEmpLevel.getBankAccNr()+"</div>");
                                 rowxPdf.add(""+payEmpLevel.getBankAccNr());
                                 double totalBenefit = PstSalaryLevelDetail.getSumBenefitDoub(PstSalaryLevelDetail.YES_TAKE,payEmpLevel.getLevelCode(),PstPayComponent.TYPE_BENEFIT,paySlip.getOID(),PstSalaryLevelDetail.PERIODE_MONTHLY,0);
                                 double totalDeduction = PstSalaryLevelDetail.getSumBenefitDoub(PstSalaryLevelDetail.YES_TAKE,payEmpLevel.getLevelCode(),PstPayComponent.TYPE_DEDUCTION,paySlip.getOID(),PstSalaryLevelDetail.PERIODE_MONTHLY,0);
                                 
                                 double empNonTransfered =0;
                                 if(employee.getEmpCategoryId()== oidTraining){
                                     empNonTransfered = PstPaySlipComp.getReportCompValue(paySlip.getOID(), PstSystemProperty.getValueByName("CODE_OVT"));
                                  }
                                 
                                 double takeHomePay = totalBenefit - totalDeduction-empNonTransfered;
                                 rowx.add("<div align=\"right\">"+Formater.formatNumber(takeHomePay, frmCurrency)+"</div>");
                                 rowxPdf.add(""+Formater.formatNumber(takeHomePay, frmCurrency));
                                 rowxPdf.add(""+employee.getEmployeeNum()); 
                                 totalTransfered = totalTransfered + takeHomePay;
                                 lstData.add(rowx);
                                 vectDataToPdf.add(rowxPdf);
                                 
			 }
		}
		result.add(ctrlist.drawList());
		result.add(vectDataToPdf);
		result.add(vectCompBenefit);
		result.add(vectCompDeduction);
                result.add(new Double(totalTransfered));
		return result;
	}
%>
<%!
public String getSelected(SalaryLevel s, Vector secSelect){
	if(secSelect!=null && secSelect.size()>0){
		for(int i=0; i<secSelect.size(); i++){
			SalaryLevel salaryLevel = (SalaryLevel)secSelect.get(i);
			if(salaryLevel.getLevelCode()==s.getLevelCode()){
				return "checked";
			}
		}
	}
	return "";
}
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
//String levelCode = FRMQueryString.requestString(request,"salaryLevel");
long oidPeriod = FRMQueryString.requestLong(request,"periode");
long departmentId = FRMQueryString.requestLong(request,"department");
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int reportPeriod = FRMQueryString.requestInt(request,"reportPeriod");
int setFooter =  FRMQueryString.requestInt(request,"setFooter");
String footer = FRMQueryString.requestString(request, "footer");
String titleName = FRMQueryString.requestString(request, "titleName");
int summRadio= FRMQueryString.requestInt(request, "summary");
String headerBCA = FRMQueryString.requestString(request, "headerBCA");
String empcategory[]= FRMQueryString.requestStringValues(request, "empcategory");
String levelCode = FRMQueryString.requestString(request, "level");
levelCode = levelCode!=null && levelCode.length()>0 ? levelCode: "ALL";
//update by satrya 2013-05-06
String tmplevelCode = "";
if(!levelCode.equalsIgnoreCase("ALL")){
tmplevelCode = tmplevelCode + levelCode;
}else{
tmplevelCode="ALL";
}
Vector vEmpCategory = new Vector();
if(empcategory!=null && empcategory.length>0){
    for (int ix=0;ix<empcategory.length;ix++){
        vEmpCategory.add(empcategory[ix]);
      }
}

int paidby= FRMQueryString.requestInt(request, "paidby");
         //update by satrya 2013-01-24
        long payGroupId = FRMQueryString.requestLong(request,"payGroupId");
double totalTransfered = 0;
String frmCurrency = "#,###";
String totalSummaryTransfered = "Total Transfered";


Vector vct = new Vector(1,1);

vct = PstSalaryLevel.list(0,0, "", PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_LEVEL_NAME]);
Vector secSelect = new Vector(1,1);
//update by satrya 2013-06-05
SalaryLevel salaryLevel = new SalaryLevel();
if(tmplevelCode!=null && tmplevelCode.length()>0 && !tmplevelCode.equalsIgnoreCase("ALL")){
salaryLevel.setLevelCode(tmplevelCode);
secSelect.add(salaryLevel);
}

/*if(vct!=null && vct.size()>0){
	for(int i=0; i<vct.size(); i++){
		SalaryLevel salaryLevel = (SalaryLevel)vct.get(i);
		if(iCommand!=Command.NONE && iCommand!=Command.ADD){
			int ix = FRMQueryString.requestInt(request, "chx_"+salaryLevel.getLevelCode());
			if(ix==1){
				secSelect.add(salaryLevel);
			}
		}
		else{
			secSelect.add(salaryLevel);
		}
	}
}*/ 


Vector tempQueryReport = new Vector(1,1);
Vector tempQueryReport2 = new Vector(1,1);
if(iCommand == Command.LIST){
		tempQueryReport = SessEmployee.getEmpSalary(secSelect,oidPeriod,departmentId,paidby, vEmpCategory);
		//tempQueryReport2 = SessEmployee.getEmpSalary(secSelect,oidPeriod,departmentId,true);
                //tempQueryReport.addAll(tempQueryReport2);
                
}

PayPeriod pr = new PayPeriod();
//Period pr = new Period();
String periodName ="";
		try{
			pr = PstPayPeriod.fetchExc(oidPeriod);
                        //pr = PstPeriod.fetchExc(oidPeriod);
			periodName = pr.getPeriod();
		}
		catch(Exception e){
	}
	
Department dept = new Department();
String departmentName ="";
		try{
			dept = PstDepartment.fetchExc(departmentId);
			departmentName = dept.getDepartment();
		}
		catch(Exception e){
	}
if(summRadio==1){
System.out.println("oidPeriod  "+oidPeriod);
/*
Vector vectTransfered= SessEmployee.getEmpSalary(vct,oidPeriod,0,false);
for (int i = 0; i <vectTransfered.size(); i++) {
	Vector vectTemp = (Vector)vectTransfered.get(i);
	Employee employee = (Employee)vectTemp.get(0);
	PayEmpLevel payEmpLevel = (PayEmpLevel)vectTemp.get(1);
	PaySlip paySlip = (PaySlip)vectTemp.get(4);
	int totalBenefit = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE,payEmpLevel.getLevelCode(),PstPayComponent.TYPE_BENEFIT,paySlip.getOID(),PstSalaryLevelDetail.PERIODE_MONTHLY);
	int totalDeduction = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE,payEmpLevel.getLevelCode(),PstPayComponent.TYPE_DEDUCTION,paySlip.getOID(),PstSalaryLevelDetail.PERIODE_MONTHLY);
	int takeHomePay = totalBenefit - totalDeduction;
	totalTransfered = totalTransfered + takeHomePay;				
}
*/
   
   String totalNonTransfered = "Total Non Transfered";
   //String totalSummaryTransfered = "Total Transfered";   
   //totalTransfered = SessPaySlip.getTotalSalary(oidPeriod, totalSummaryTransfered, "");//totalNonTransfered);
   
    ListSalary listSalTransfer = SessPaySlip.getSalary(false, null, oidPeriod, 0, true, totalSummaryTransfered, paidby, vEmpCategory,payGroupId);
    
    totalTransfered  = listSalTransfer.totalSalary ;// + listSalNonTransfer.totalSalary - nonTransfered;   
   
   //double nonTransfered = SessPaySlip.getSumOvtTraining(oidPeriod, totalNonTransfered);
   //totalTransfered = totalTransfered - nonTransfered;

}
		
//Jika ada additional
	//int addTransfered = PstPayAdditional.getAddValue(totalSummaryTransfered);
	//totalTransfered = totalTransfered + addTransfered;
//---------------------
	
//String levelName = PstSalaryLevel.getSalaryName(levelCode);
Vector temp = new Vector();

// process on drawlist
Vector vectResult = new Vector(1,1);
String listData = "";
Vector vectDataToPdf = new Vector (1,1);
Vector vectHeadCompB = new Vector (1,1);
Vector vectHeadCompD = new Vector (1,1);

vectResult = drawList(tempQueryReport,oidPeriod);
listData = String.valueOf(vectResult.get(0));
vectDataToPdf = (Vector)vectResult.get(1);
vectHeadCompB = (Vector)vectResult.get(2);
vectHeadCompD = (Vector)vectResult.get(3);

// design vector that handle data to store in session
Vector vectPresence = new Vector(1,1);
vectPresence.add(vectDataToPdf);  
vectPresence.add(""+periodName);  
vectPresence.add(""+summRadio);  
vectPresence.add(""+departmentName);  
vectPresence.add(""+footer);
vectPresence.add(""+ ((long)((Double)vectResult.get(4)).doubleValue()));    
vectPresence.add(pr); 
  
if(session.getValue("QUERY_REPORT")!=null){
	session.removeValue("QUERY_REPORT");
        session.removeValue("HEADER_REPORT_BCA");
}
session.putValue("QUERY_REPORT",vectPresence);
session.putValue("HEADER_REPORT_BCA",headerBCA);


%>

<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Report of Salary Transfer</title>
<script language="JavaScript">
<%if(iCommand==Command.PRINT){%>
    //com.dimata.harisma.report.listRequest	
	window.open("<%=printroot%>.report.listRequest.ListEmpCategPdf");
<%}%>
 function openLevel(){
    
            var strUrl ="<%=approot%>/payroll/process/sel_salary-level.jsp?frmname=frmreason";
            var levelWindow = window.open(strUrl);
            levelWindow.focus();
        }

        function clearLevelCode(){
            document.frmreason.level.value="ALL";
        }
        
function cmdView(){
	document.frmreason.command.value="<%=Command.LIST%>";
	document.frmreason.action="gaji_transfer.jsp";
	document.frmreason.submit();
}

function cmdFooter(){
	document.frmreason.setFooter.value="1";
	document.frmreason.action="gaji_transfer.jsp";
	document.frmreason.submit();
}

function periodChange(){
	document.frmreason.command.value="<%=Command.ADD%>";
	document.frmreason.action="list_absence_reason.jsp";
	document.frmreason.submit();
}

function reportPdf(){
	var linkPage ="<%=printroot%>.report.payroll.ListSalaryTransferedPdf";
	window.open(linkPage); 
}

function reportPdfYear(){
	var linkPage ="<%=printroot%>.report.ListAbsenceReasonYearPdf";
	window.open(linkPage); 
}

function deptChange() {
	document.frmreason.command.value = "<%=Command.GOTO%>";
	document.frmreason.hidden_goto_period.value = document.frmreason.reportPeriod.value;

	//document.frmsrcpresence.hidden_goto_dept.value = document.frmsrcpresence.DEPARTMENT_ID.value;
	document.frmreason.action = "list_absence_reason.jsp";
	document.frmreason.submit();
}

function cmdPrintXLS(){
		window.open("<%=approot%>/servlet/com.dimata.harisma.report.payroll.TransferListXLS");
	}
        
function cmdExportDocBCA(){
		window.open("<%=approot%>/servlet/com.dimata.harisma.report.payroll.TransferListToBCA");
	}
        
function cmdExportDocBAG(){
    window.open("<%=approot%>/servlet/com.dimata.harisma.report.payroll.TransferListToBAG");
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
	
	
function cmdUpdateDep(){
	document.frpresence.command.value="<%=Command.ADD%>";
	document.frpresence.action="lateness_monthly_report.jsp"; 
	document.frpresence.submit();
}
	function cmdUpdateLevp(){
	document.frpresence.command.value="<%=Command.ADD%>";
	document.frpresence.action="list_employee_category.jsp"; 
	document.frpresence.submit();
}
</SCRIPT>
<style type="text/css">
    .tblStyle {border-collapse: collapse;font-size: 11px;}
    .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
    .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
</style>
<!-- #EndEditable -->
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg');">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Payroll
                  &gt; List Of Salary Summary<!-- #EndEditable --> </strong></font> </td>
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
                                  <td valign="top"> <!-- #BeginEditable "content" -->
                                    <form name="frmreason" method="post" action="">
									<input type="hidden" name="command" value="">
									<input type="hidden" name="start" value="<%=start%>">
									<input type="hidden" name="prev_command" value="<%=prevCommand%>">
									<input type="hidden" name="setFooter" value="<%=setFooter%>">
									<input type="hidden" name="titleName" value="<%=titleName%>">
									  <table width="93%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                                          <td width="83%"> : 
                                            <%
                                        DepartmentIDnNameList keyList= new DepartmentIDnNameList ();          
                                        Vector dept_value = new Vector(1,1);
                                        Vector dept_key = new Vector(1,1);        
                                       dept_value.add("0");
                                        dept_key.add("select ...");                                                          
                                        keyList = PstDepartment.genDepIDandNameWithCompanyDiv(0, 1000, "", true);
                                        dept_value = keyList.getDepIDs();
                                        dept_key = keyList.getDepNames();     
                                                                                                                                                                                                                                             
										  %> 
                                            <%=ControlCombo.draw("department",null,""+departmentId,dept_value,dept_key,"")%></td>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" align="right" nowrap> 
                                            <div align="left">Period </div></td>
                                          <td width="83%"> : 
                                            <%
                                          Vector perValue = new Vector(1,1);
										  Vector perKey = new Vector(1,1);
										 // salkey.add(" ALL DEPARTMET");
										  //deptValue.add("0");
						                  Vector listPeriod = PstPayPeriod.list(0, 0, "", "START_DATE DESC");
                                                                  // Vector listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");
                                          for(int r=0;r<listPeriod.size();r++){
										  	PayPeriod payPeriod = (PayPeriod)listPeriod.get(r);
                                                                                        //Period period = (Period)listPeriod.get(r);
											perValue.add(""+payPeriod.getOID());
											perKey.add(payPeriod.getPeriod());
										  }
										  %> 
                                            <%=ControlCombo.draw("periode",null,""+oidPeriod,perValue,perKey,"")%></td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td nowrap>Salary Level</td>
                                          <td> <%
										  if( true && vct!=null && vct.size()>0){ // tidak di exekusi
                                                                                      /*if( false && vct!=null && vct.size()>0){ // tidak di exekusi 
										  	for(int i=0; i<vct.size(); i++){
												SalaryLevel salaryLevel = (SalaryLevel)vct.get(i);
												String isSelect = getSelected(salaryLevel, secSelect);*/
                                                                                     
												%> 
                                            <!--<input type="checkbox" name="chx_<%//=salaryLevel.getLevelCode()%>" value="1" <%//=isSelect%>> -->
                                               : <input name="level" type="text"  value="<%=tmplevelCode%>"> <a href="javascript:openLevel();">Select</a>
                                              | <a href="javascript:clearLevelCode()">Clear</a>                                                   
                                            <%//=salaryLevel.getLevelName()%> <%//}  			
										}                                                                  
										  %> 
<%
										  if(vct!=null && vct.size()>0){ 
										  	for(int i=0; i<vct.size(); i++){
												salaryLevel = (SalaryLevel)vct.get(i);
                                                                                                //update by satrya 2013-06-05
                                                                                                //SalaryLevel salaryLevel = (SalaryLevel)vct.get(i);
												//String isSelect = getSelected(salaryLevel, secSelect);
												%> 
                                            <input type="hidden" name="chx_<%=salaryLevel.getLevelCode()%>" value="1" > 
                                             <%}			
										  }                                                                  
										  %>                                                                                   
                                                                                  
                                          </td>
<tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" align="right" nowrap> 
                                            <div align="left">BCA Header</div></td>
                                          <td width="83%"> : 
                                              <input type="text" name="headerBCA" size="128" value="<%=((headerBCA==null || headerBCA.length()<1? "00000000000004000070KAYA0601040016699410MF0050600000000000000.00MMYYYY":headerBCA )) %>"><br> Note: MMYYYY will be replaced with month and year of payroll date setup on period</td>
                                        </tr>                                          
                                          
                                          
                                          
                                          
                                        </tr>
										<!-- update parameter unutk include summary.
											  		Khusus unutk Intimas
													Updated by Yunny
											  -->
                                        <!--tr> 
                                          <td>&nbsp;</td>
                                          <td nowrap>Summary Report</td>
										  <%
											if(summRadio==0){
										  %>
                                          <td><input type="radio" name="summary" value="0" checked>
                                               Without Summary 
                                              <input type="radio" name="summary" value="1">
                                              With Summary  </td>
										<%
											}else if(summRadio==1){
										%>
										<td width="83%"> 
									   <input type="radio" name="summary" value="0">
										Without Summary 
										<input type="radio" name="summary" value="1" checked>
										With Summary 
										</td>
										<%
										}
										%>
                                        </tr-->
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td nowrap>Payment :</td>
                                          <td><%
                                              Vector valPaidBy = new Vector();
                                              valPaidBy.add("0");
                                              valPaidBy.add("1");
                                              valPaidBy.add("2");
                                              Vector keyPaidBy = new Vector();
                                              keyPaidBy.add("With Bank Only");
                                              keyPaidBy.add("Without Bank");
                                              keyPaidBy.add("Both");
                                              %> 
                                              <%=ControlCombo.draw("paidby",null,""+paidby,valPaidBy,keyPaidBy,"")%>
                                          </td>										
                                        </tr>                                        
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td nowrap>Employee category :</td>
                                          <td><% 
                                                            Vector cat_value = new Vector(1,1);
                                                            Vector cat_key = new Vector(1,1);        
							    //cat_value.add("0");
                                                            //cat_key.add("select ...");                                                          
                                                            Vector listCat = PstEmpCategory.list(0, 0, "", " EMP_CATEGORY ");                                                        
                                                            for (int i = 0; i < listCat.size(); i++) {
                                                                    EmpCategory cat = (EmpCategory) listCat.get(i);
                                                                    cat_key.add(cat.getEmpCategory());
                                                                    cat_value.add(String.valueOf(cat.getOID()));
                                                            }
                                                            ControlCheckBox controlCheckBox = new ControlCheckBox();
                                                            controlCheckBox.setWidth(4);
                                                        %> <%= controlCheckBox.draw("empcategory",cat_value, cat_key, empcategory ) %>
                                          </td>										
                                        </tr> 
                                        <!-- update by satrya 2013-01-25 -->
                                         <tr> 
                                          <td>&nbsp;</td>
                                          <td nowrap>Payrol Slip Group :</td>
                                          <td>                                        <%
                                                                                //update by satrya 2013-01-24
        //Pay Group SLip
        
        Vector grkKey = new Vector();
        Vector grValue = new Vector();
        Vector listPaySlipGroup = PstPaySlipGroup.listAll();
        for (int r = 0; r < listPaySlipGroup.size(); r++) {
                    PaySlipGroup paySlipGroup = (PaySlipGroup) listPaySlipGroup.get(r);
                     grkKey.add(String.valueOf(paySlipGroup.getOID())); 
                    grValue.add(paySlipGroup.getGroupName());
        }  
        
                                                                                out.println(ControlCombo.draw("payGroupId",null,""+payGroupId,grkKey,grValue));
									  %>
                                          </td>										
                                        </tr> 
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" nowrap> <div align="left"><a href="javascript:cmdFooter()">Set 
                                              Footer</a></div></td>
                                          <td width="83%"> <table border="0" cellspacing="0" cellpadding="0" width="550">
                                              <% if(setFooter==1){ %>
                                              <td  colspan="2"> : 
                                                <input name="footer" type="text" size="85" value="<%=footer%>"> 
                                              </td>
                                              <td width="1" class="command" nowrap> 
                                              </td>
                                              </tr>
                                              <%
											  }
											  %>
                                            </table></td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td nowrap><div align="right"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a></div></td>
                                          <td> <a href="javascript:cmdView()">Search 
                                            for Employee</a> </td>
                                        </tr>
                                      </table>
									  <%if(iCommand == Command.LIST || iCommand==Command.PRINT){%>
									  <table width="100%" border="0" cellspacing="2" cellpadding="2">
									  <%if(tempQueryReport != null && tempQueryReport.size() > 0){%>
									    <tr><td><hr></td></tr>
                                        <tr>
											<td>
											<%
												out.println(listData);
												
											%>
												<% //out.println(vectPresence);%>											
											</td>
										  </tr>
										  <%
										  	if(false && summRadio==1){
											
										  %>
										  <tr>
										  	<td height="48" colspan="3"><table width="29%" border="0">
											  <tr>
												<td width="57%"><strong>Total Transfered</strong></td>
												<td>=</td>
												<td width="43%" align="right"><strong><%=Formater.formatNumber(totalTransfered, frmCurrency)%></strong></td>
											  </tr>
											</table>
											</td>
										  </tr>
										  <%
										  }
										  %>
										  <%if(privPrint){%>
										  <tr>
											<td>
											  <table width="70%" border="0" cellspacing="1" cellpadding="1">
                                              <tr>
												  <td width="15%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
												  <td width="20%"><b><a href="javascript:reportPdf()" class="buttonlink">Print
													Salary Transfered</a></b>
												  </td>
												  
												   <td width="15%"><a href="javascript:cmdPrintXLS()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
												  <td width="20%"><b><a href="javascript:cmdPrintXLS()" class="buttonlink">Export to Excel</a></b>
												  </td>
                                                                                                  <td width="15%"><a href="javascript:cmdExportDocBCA()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
												  <td width="20%"><b><a href="javascript:cmdExportDocBCA()" class="buttonlink">Export to BCA transfer</a></b></td>
                                                                                                  <td width="15%"><a href="javascript:cmdExportDocBAG()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
												  <td width="20%"><b><a href="javascript:cmdExportDocBAG()" class="buttonlink">Export to BAG transfer</a></b></td>
												</tr>
											  </table>
											 </td>
										  </tr>
										  <%}%>
									    <%}else{%>
									    <tr><td></td></tr>
											<tr>
											<td>
											<%
											out.println("<div class=\"msginfo\">&nbsp;&nbsp;No employee leave  data found ...</div>");											
											%>
											</td>
										  </tr>
										  <%}%>
										</table>
										<%}%>
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
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
