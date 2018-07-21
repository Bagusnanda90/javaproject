
<%@ page language="java" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.qdep.db.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<%@ page import ="java.util.Date"%>

<!-- package java -->
<%@ page import ="com.dimata.harisma.entity.payroll.Ovt_Employee,
                  com.dimata.harisma.entity.payroll.PstOvt_Employee,
				  com.dimata.harisma.entity.payroll.Ovt_Idx,
				  com.dimata.harisma.entity.payroll.PstOvt_Idx,
				  com.dimata.harisma.entity.payroll.Ovt_Type,
				  com.dimata.harisma.entity.payroll.PstOvt_Type,
				  com.dimata.harisma.entity.payroll.PaySlip,
				  com.dimata.harisma.entity.payroll.PstPaySlip,
				  com.dimata.harisma.entity.payroll.PstPayEmpLevel,
				  com.dimata.harisma.entity.payroll.SalaryLevel,
				  com.dimata.harisma.entity.payroll.PstSalaryLevel,
	              com.dimata.harisma.entity.employee.Employee,
				  com.dimata.harisma.session.employee.SessEmployee,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
				  com.dimata.harisma.entity.attendance.PstEmpSchedule,
				  com.dimata.harisma.session.attendance.SessEmpSchedule,
				  com.dimata.harisma.entity.payroll.PayAdditional,
				  com.dimata.harisma.entity.payroll.PstPayAdditional,
				  com.dimata.harisma.form.payroll.CtrlPayAdditional,
				  com.dimata.harisma.form.payroll.FrmPayAdditional,
                  com.dimata.harisma.entity.employee.PstEmployee"%>
				  
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LATENESS_REPORT, AppObjInfo.OBJ_LATENESS_MONTHLY_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%!
public String getSelected(SalaryLevel s, Vector salarySelect){
	if(salarySelect!=null && salarySelect.size()>0){
		for(int i=0; i<salarySelect.size(); i++){
			SalaryLevel salaryLevel = (SalaryLevel)salarySelect.get(i);
			if(salaryLevel.getLevelCode()==s.getLevelCode()){
				return "checked";
			}
		}
	}
	return "";
}
%>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%!
public Vector drawList(Vector objectClass,long oidPeriod,Date selectedDateFrom,Date selectedDateTo,long departmentId, long sectionId)
	{
		//ambil dayOfMont dari date awal
		Calendar newCalendar = Calendar.getInstance();
		newCalendar.setTime(selectedDateFrom);
		int dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
		int dateStart = selectedDateFrom.getDate();
		int maxColum  = 0;
		if(selectedDateFrom.getMonth()!=selectedDateTo.getMonth()){
			maxColum = (dateOfMonth-(dateStart-1))+selectedDateTo.getDate();
		}else{
			maxColum =  selectedDateTo.getDate() -  selectedDateFrom.getDate()+1;
		}
		
		Vector result = new Vector(1,1);
		Vector vectDatePeriod= new Vector(1,1);
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No","1%","2","0");
		ctrlist.addHeader("Name","5%","2","0");
		ctrlist.addHeader("Emp.Category","2%","2","0");
		ctrlist.addHeader("Religion","3%","2","0");
		ctrlist.addHeader("Comm.Date","3%","2","0");
		ctrlist.addHeader("Date","2%","0",""+(maxColum));
		//**************** untuk indexnya************************ 
		ctrlist.addHeader(""+dateStart,"1%","0","0");
		for (int o=0;o<(maxColum-1);o++){
				if(dateStart==dateOfMonth){
					dateStart = 0;
				}
				dateStart++;
				ctrlist.addHeader(""+dateStart,"1%","0","0");
		}
		//**************** untuk indexnya************************ 
		ctrlist.addHeader("Presence","2%","2","0");
		ctrlist.addHeader("Meal Allowance","3%","2","0");
		ctrlist.addHeader("Description","3%","2","0");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector vectDataToPdf = new Vector(1,1);
		Vector vectFooter = new Vector(1,1);
		String whereClause = "";
		String frmCurrency = "#,###";
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
		double sumTotalSalary = 0;
		double totMealAlloance= 0;
		int totAbsence = 0;
		double totDeduction = 0;
		int totPresenceGlobal = 0;
		for (int i = 0; i <=objectClass.size(); i++) {
			 if(i==objectClass.size()){
				 Vector rowx = new Vector();
				 Vector rowxPdf = new Vector();
				 int dateStartContent = selectedDateFrom.getDate();
				 rowx.add("");
				 rowxPdf.add("");
				 rowx.add("<b>Total</b>");
				 rowxPdf.add("Total");
				 rowx.add("");
				 rowxPdf.add("");
				 rowx.add("");
				 rowxPdf.add("");
				 rowx.add("");
				 rowxPdf.add("");
				 rowx.add("");
				 rowxPdf.add("");
				 for(int f=0;f<(maxColum-1);f++){
					rowx.add("");
					rowxPdf.add("");
				 }
				 rowx.add("");
				 rowxPdf.add("");
				 rowx.add("<div align=\"right\">"+Formater.formatNumber(totMealAlloance,frmCurrency)+"</div>");
				 rowxPdf.add(""+Formater.formatNumber(totMealAlloance,frmCurrency));
				 rowx.add("");
				 rowxPdf.add("");
				 lstData.add(rowx);
				 vectDataToPdf.add(rowxPdf);
			 }
			 else{
			 Vector temp = (Vector)objectClass.get(i);
			 Employee employee = (Employee)temp.get(0);
			 EmpCategory empCateg = (EmpCategory)temp.get(1);
			 Religion religion = (Religion)temp.get(2);
			 Vector rowx = new Vector();
			 Vector rowxPdf = new Vector();
			 int dateStartContent = selectedDateFrom.getDate();
			 int month = selectedDateFrom.getMonth();
			 int year = selectedDateFrom.getYear();
			 double totI = 0;
			 double totII = 0;
			 double totPresence = 0;
			 double absence = 0;
			 double totNAB = 0;
			 double jmlLate=0;
			 String description="";
			 rowx.add(String.valueOf(i+1));
			 rowxPdf.add(String.valueOf(i+1));
			 rowx.add(""+employee.getFullName());
			 rowxPdf.add(""+employee.getFullName());
			 rowx.add(""+empCateg.getEmpCategory());
			 rowxPdf.add(""+empCateg.getEmpCategory());
			 rowx.add(""+religion.getReligion());
			 rowxPdf.add(""+religion.getReligion());
			 rowx.add(""+Formater.formatDate(employee.getCommencingDate(),"dd-MMM-yyyy"));
			 rowxPdf.add(""+Formater.formatDate(employee.getCommencingDate(),"dd-MMM-yyyy"));
			 //rowx.add(""+dateStartContent);
			 Date dateContent = new java.util.Date(year, month, dateStartContent, 0, 0);
			 int statusAbsence =0;
			 statusAbsence = PstEmpSchedule.getStatus1(dateStartContent,employee.getOID(),dateContent);
			 Date dateIn = SessEmpSchedule.getTimeIn(employee.getOID(),dateContent);
			 Date dateReal = SessEmpSchedule.getTimeOut(dateContent,employee.getOID());
			 //kondisi untuk karyawan yang dibayar full--jadi dianggap dia masuk terus
			 if(statusAbsence==PstEmpSchedule.STATUS_PRESENCE_OK){
			 		long periodPresence = PstPeriod.getPeriodIdBySelectedDate(dateContent);
					rowx.add("V");
					rowxPdf.add("V");
					totPresence = totPresence +1;
			 }
			 else if(statusAbsence==PstEmpSchedule.STATUS_PRESENCE_LATE){
					rowx.add("V");
					rowxPdf.add("V");
					totPresence = totPresence +1;
			 }
			 else if(statusAbsence==PstEmpSchedule.STATUS_PRESENCE_ABSENCE){
				//jika tidak masuk dicari reasonnya
				int absenceReason1 = PstEmpSchedule.getReason1(dateStartContent,employee.getOID(),dateContent);
				String strSymbol = PstReason.getStrReasonReport(absenceReason1,employee.getPositionId(),0);
				if(strSymbol.equals("X")){
					absence = absence +1;
					totPresence = totPresence +1;
					//totAbsence = totAbsence +1;
				}
				else if(strSymbol.equals("V")){
					totPresence = totPresence +1;
				}
				else {
					//absence = absence +1;
					totAbsence = totAbsence +1;
				}
				rowx.add(""+strSymbol);
				rowxPdf.add(""+strSymbol);
			 }else{
			 	rowx.add("V");
				rowxPdf.add("V");
				totPresence = totPresence +1;	
			 }
			 for(int f=0;f<(maxColum-1);f++){
				 if(dateStartContent==dateOfMonth){
					if(month ==11){
						dateStartContent = 0;
						month = 0;
						year = selectedDateTo.getYear();
					}else{
						dateStartContent = 0;
						month = month +1;
						year = selectedDateTo.getYear();
				 	}
				 }
					dateStartContent++;
					dateContent = new java.util.Date(year, month, dateStartContent, 0, 0);
					statusAbsence = PstEmpSchedule.getStatus1(dateStartContent,employee.getOID(),dateContent);
					if(statusAbsence==PstEmpSchedule.STATUS_PRESENCE_OK){
						long periodPresence = PstPeriod.getPeriodIdBySelectedDate(dateContent);
							rowx.add("V");
							rowxPdf.add("V");
							totPresence = totPresence +1;
					}
					else if(statusAbsence==PstEmpSchedule.STATUS_PRESENCE_LATE){
							rowx.add("V");
							rowxPdf.add("V");
							totPresence = totPresence +1;
					}
					else if(statusAbsence==PstEmpSchedule.STATUS_PRESENCE_ABSENCE){
						//jika tidak masuk dicari reasonnya
						int absenceReason = PstEmpSchedule.getReason1(dateStartContent,employee.getOID(),dateContent);
						String strSymbol = PstReason.getStrReasonReport(absenceReason,employee.getPositionId(),0);
						if(strSymbol.equals("X")){
							absence = absence +1;
							totPresence = totPresence +1;
						}
						else if(strSymbol.equals("V")){
							totPresence = totPresence +1;
						}
						else {
							totAbsence = totAbsence +1;
							//absence = absence +1;
						}
						rowx.add(""+strSymbol);
						rowxPdf.add(""+strSymbol);
					}else{
						rowx.add("V");
						rowxPdf.add("V");
						totPresence = totPresence +1;
					}
				 }
			 rowx.add(""+Formater.formatNumber(totPresence,frmCurrency));
			 //rowx.add(""+employee.getEmpCategoryId());
			 rowxPdf.add(""+Formater.formatNumber(totPresence,frmCurrency));
			 //ambil uang makan perhari dari system property
			 double mealAllowance = 0;
			 double potNAB = 0;
			 double potDeduction=0;
			mealAllowance = Double.parseDouble(String.valueOf(PstSystemProperty.getValueByName("DW_VALUE")));  
			rowx.add("<div align=\"right\">"+Formater.formatNumber(mealAllowance*totPresence,frmCurrency)+"</div>");
			rowxPdf.add(""+Formater.formatNumber(mealAllowance*totPresence,frmCurrency));
			totMealAlloance = totMealAlloance + (mealAllowance*totPresence);
			 potDeduction =(mealAllowance * 7) - totMealAlloance;
			 totDeduction = totDeduction + potDeduction;
			rowx.add("");
			rowxPdf.add("");
			lstData.add(rowx);
			vectDataToPdf.add(rowxPdf);
			}
		}
		result.add(ctrlist.drawList());
		result.add(vectDataToPdf);
		result.add(""+totAbsence);
		result.add(""+totMealAlloance);
		//result.add(""+totPotNAB);
		return result;
	}
%>


<!-- untuk summary montly -->
<%!
public Vector drawSummaryMontly(Vector objectClass,long oidPeriod)
	{
		Vector result = new Vector(1,1);
		Vector vectDatePeriod= new Vector(1,1);
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		//**************** untuk indexnya************************ 
		Vector lstData = ctrlist.getData();
		Vector vectDataToPdf = new Vector(1,1);
		Vector vectFooter = new Vector(1,1);
		String whereClause = "";
		String frmCurrency = "#,###";
		int totAbsence = 0;
		//int totNAB = 0;
		for (int i = 0; i < objectClass.size(); i++) {
			 Vector temp = (Vector)objectClass.get(i);
			 Employee employee = (Employee)temp.get(0);
			 EmpCategory empCateg = (EmpCategory)temp.get(1);
			 Religion religion = (Religion)temp.get(2);
			 Vector rowx = new Vector();
			 Vector rowxPdf = new Vector();
			 int statusAbsence =0;
		    //	Vector vectAbsence = SessEmpSchedule.getDWAbsenceAllowance(employee.getOID(),oidPeriod);
			//	totAbsence = totAbsence + vectAbsence.size();
			lstData.add(rowx);
			vectDataToPdf.add(rowxPdf);
			}
		//totAbsence = totAbsence + totLate;
		result.add(ctrlist.drawList());
		result.add(vectDataToPdf);
		result.add(""+totAbsence);
		return result;
	}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment= FRMQueryString.requestLong(request,"department");
long oidSection = FRMQueryString.requestLong(request,"section");
long oidCategory = FRMQueryString.requestLong(request,"category");
int gotoPeriod = FRMQueryString.requestInt(request, "hidden_goto_period");
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int reportPeriod = FRMQueryString.requestInt(request,"reportPeriod");
int setFooter =  FRMQueryString.requestInt(request,"setFooter");
String footer = FRMQueryString.requestString(request, "footer");
Date selectedDateFrom = FRMQueryString.requestDate(request, "check_date_start");
Date selectedDateTo = FRMQueryString.requestDate(request, "check_date_finish");
int summRadio= FRMQueryString.requestInt(request, "summary");
String frmCurrency = "#,###";

Vector tempQueryReport = new Vector(1,1);
Vector vectSummary = new Vector(1,1);


long oidPeriod = PstPeriod.getPeriodIdBySelectedDate(selectedDateFrom);
//fetch data untuk period
Period pr = new Period();
Date date = new Date();
Date endDate = new Date();
String periodName ="";
		try{
			pr = PstPeriod.fetchExc(oidPeriod);
			date =  pr.getStartDate();
			endDate = pr.getEndDate();
			periodName = pr.getPeriod();
		}
		catch(Exception e){
	}

//fetch data untuk department
Department dept = new Department();
String deptName ="";
		try{
			dept = PstDepartment.fetchExc(oidDepartment);
			deptName =  dept.getDepartment();
		}
		catch(Exception e){
	}

//fetch data untuk section
Section sect = new Section();
String sectName ="";
		try{
			sect = PstSection.fetchExc(oidSection);
			sectName =  sect.getSection();
		}
		catch(Exception e){
	}
	
Vector temp = new Vector();
Vector vct = new Vector(1,1);
Vector vctSalary = new Vector(1,1);


vct = PstEmpCategory.list(0,0, "", PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]);
vctSalary = PstSalaryLevel.list(0,0, "", PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_LEVEL_CODE]);

Vector salarySelect = new Vector(1,1);

if(vctSalary!=null && vctSalary.size()>0){
	for(int i=0; i<vctSalary.size(); i++){
		SalaryLevel salaryLevel = (SalaryLevel)vctSalary.get(i);
		if(iCommand!=Command.NONE && iCommand!=Command.ADD){
			int ix = FRMQueryString.requestInt(request, "salary_"+salaryLevel.getLevelCode());
			if(ix==1){
				salarySelect.add(salaryLevel);
			}
		}
		else{
			salarySelect.add(salaryLevel);
		}
	}
}

if(iCommand == Command.LIST){
	tempQueryReport = SessEmployee.getListDW(oidDepartment,oidSection,oidCategory,salarySelect);
}
// get maximum date of selected month
Calendar newCalendar = Calendar.getInstance();
newCalendar.setTime(date);
int dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); 

int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));  

//bulan period awal
int startDate = selectedDateFrom.getDate();  
int startMonth = selectedDateFrom.getMonth()+1;  

// process on drawlist
Vector vectResult = new Vector(1,1);
String listData = "";
Vector vectDataToPdf = new Vector (1,1);
String totAbsence = "";
String totAbsenceTraining="";
String totMealAllowance = "";
String totPresence = "";
String totalDWSalary = "Total Daily Worker Salary";
String totalDWAbsence = "Total Daily Worker Deduction";
String strTotalMealAll = "";

vectResult = drawList(tempQueryReport,oidPeriod,selectedDateFrom,selectedDateTo,oidDepartment,oidSection);
listData = String.valueOf(vectResult.get(0));
vectDataToPdf = (Vector)vectResult.get(1);
strTotalMealAll = String.valueOf(vectResult.get(3));

// design vector that handle data to store in session
Vector vectPresence = new Vector(1,1);
vectPresence.add(vectDataToPdf);  
vectPresence.add(""+deptName);  
vectPresence.add(""+sectName);  
vectPresence.add(""+footer); 
vectPresence.add(selectedDateFrom);  
vectPresence.add(selectedDateTo); 
vectPresence.add(""+periodName); 
vectPresence.add(""+summRadio);
// untuk summary
double totalMealAll = 0;
double totalAbsence = 0;
double totalPayment=0;
Vector vectListSummary = new Vector(1,1);
String listSummary ="";

 if(summRadio==1 && tempQueryReport.size() > 0){
	//vectSummary = SessEmployee.getListDW(0,0,oidCategory,salarySelect);
	//vectListSummary = drawList(vectSummary,oidPeriod);
	//listSummary = String.valueOf(vectListSummary.get(0));
	//totAbsence = String.valueOf(vectListSummary.get(2));
	//totalMealAll = (vectSummary.size() *(dateOfMonth* Double.parseDouble(String.valueOf(PstSystemProperty.getValueByName("DW_VALUE")))));
	//totalAbsence = Double.parseDouble(totAbsence) * Double.parseDouble(String.valueOf(PstSystemProperty.getValueByName("DW_VALUE")));
	totalMealAll = Double.parseDouble(strTotalMealAll);

	//Jika ada additional
	double addDwAllowanceMontly = PstPayAdditional.getAddValue(totalDWSalary);
	totalMealAll = totalMealAll + addDwAllowanceMontly;
	
	double addDwDeduction = PstPayAdditional.getAddValue(totalDWAbsence);
	totalAbsence = totalAbsence + addDwDeduction;
	//------------------------------
	vectPresence.add(""+totalMealAll);
	totalPayment = totalMealAll + totalAbsence;
	vectPresence.add(""+totalAbsence);
	vectPresence.add(""+totalPayment);
	vectPresence.add(salarySelect);
	vectPresence.add("");
	//vectPresence.add(""+totalAbsence);

}
else{
	vectPresence.add("");
	vectPresence.add("");
	vectPresence.add("");
	vectPresence.add("");
	vectPresence.add(salarySelect);
	//vectPresence.add("");

}

if(session.getValue("QUERY_REPORT")!=null){
	session.removeValue("QUERY_REPORT");
}
session.putValue("QUERY_REPORT",vectPresence);
%>

<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Daily Workers Report</title>
<script language="JavaScript">
function deptChange() {
	document.frmreason.command.value = "<%=Command.GOTO%>";
	document.frmreason.hidden_goto_period.value = document.frmreason.reportPeriod.value;
	document.frmreason.action = "dw_report.jsp";
	document.frmreason.submit();
}

<%if(iCommand==Command.PRINT){%>
	window.open("<%=printroot%>.report.listRequest.ListEmpCategPdf");
<%}%>

function cmdView(){
	document.frmreason.command.value="<%=Command.LIST%>";
	document.frmreason.action="dw_report.jsp";
	document.frmreason.submit();
}

function cmdFooter(){
	document.frmreason.setFooter.value="1";
	document.frmreason.action="dw_report.jsp";
	document.frmreason.submit();
}

function deptChange() {
	document.frmreason.command.value = "<%=Command.GOTO%>";
	document.frmreason.hidden_goto_period.value = document.frmreason.reportPeriod.value;
	document.frmreason.action = "dw_report.jsp";
	document.frmreason.submit();
}


function reportPdfMontly(){
	var linkPage ="<%=printroot%>.report.payroll.DWReportPdf";
	window.open(linkPage); 
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
<!-- #EndEditable -->
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr>
    <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
      <!-- #BeginEditable "header" --> <!-- #EndEditable --> </td>
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
  <tr>
    <td width="88%" valign="top" align="left">
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr>
          <td width="100%">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Report &gt; Payroll
                  &gt; DW Report<!-- #EndEditable --> </strong></font> </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class="tablecolor">
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr>
                            <td valign="top">
                              <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr>
                                  <td valign="top"> <!-- #BeginEditable "content" -->
                                    <form name="frmreason" method="post" action="">
									<input type="hidden" name="command" value="">
									<input type="hidden" name="start" value="<%=start%>">
									<input type="hidden" name="prev_command" value="<%=prevCommand%>">
									<input type="hidden" name="setFooter" value="<%=setFooter%>">
									<input type="hidden" name="hidden_goto_period" value="<%=gotoPeriod%>">
									  <table width="93%" border="0" cellspacing="2" cellpadding="2">
									  <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                                          <td width="83%"> : 
                                            <%
                                          Vector deptValue = new Vector(1,1);
										  Vector deptKey = new Vector(1,1);
										  deptKey.add(" ALL DEPARTMET");
										  deptValue.add("0");
						                  Vector listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                          for(int p=0;p<listDept.size();p++){
										  	Department department = (Department)listDept.get(p);
											deptValue.add(""+department.getOID());
											deptKey.add(department.getDepartment());
										  }
										  %> <%=ControlCombo.draw("department",null,""+oidDepartment,deptValue,deptKey,"")%></td>
										
                                        </tr>
										<tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div></td>
                                          <td width="83%"> : 
                                            <%
                                          Vector sectValue = new Vector(1,1);
										  Vector sectKey = new Vector(1,1);
										  sectKey.add(" ALL ");
										  sectValue.add("0");
						                  Vector listSect = PstSection.list(0, 0, "", "SECTION");
                                          for(int s=0;s<listSect.size();s++){
										  	Section section = (Section)listSect.get(s);
											sectValue.add(""+section.getOID());
											sectKey.add(section.getSection());
										  }
										  %> <%=ControlCombo.draw("section",null,""+oidSection,sectValue,sectKey,"")%></td>
										
                                        </tr>
										<tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" align="right" nowrap> 
                                            <div align="left">Period </div></td>
                                          <td width="83%"> : 
										  <%=ControlDate.drawDate("check_date_start",selectedDateFrom==null || iCommand == Command.NONE || iCommand == Command.GOTO?new Date():selectedDateFrom,"formElemen",0,installInterval)%> to <%=ControlDate.drawDate("check_date_finish",selectedDateTo==null || iCommand == Command.NONE || iCommand == Command.GOTO?new Date():selectedDateTo,"formElemen",0,installInterval)%>                                    
										 </td> </tr>
										 <tr>
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" nowrap valign="top">Emp.Category  
                                          </td>
                                          <td width="88%"> :
										    <%
                                          Vector catValue = new Vector(1,1);
										  Vector catKey = new Vector(1,1);
										 // 504404189922212195 adalah id dari category daily worker
									      Vector listCategory = PstEmpCategory.list(0, 0, "EMP_CATEGORY_ID=504404189922212195", "EMP_CATEGORY");
                                          for(int p=0;p<listCategory.size();p++){
										  	EmpCategory empCategory = (EmpCategory)listCategory.get(p);
											catValue.add(""+empCategory.getOID());
											catKey.add(empCategory.getEmpCategory());
										  }
										  %> <%=ControlCombo.draw("category",null,""+oidCategory,catValue,catKey,"")%>
										
                                            
                                          </td>
                                        </tr>
										<tr> 
                                          <td>&nbsp;</td>
                                          <td nowrap>Salary Level</td>
                                          <td> <%
										  if(vctSalary!=null && vctSalary.size()>0){
										  	for(int i=0; i<vctSalary.size(); i++){
												SalaryLevel salaryLevel = (SalaryLevel)vctSalary.get(i);
												String isSelect = getSelected(salaryLevel, salarySelect);
												%> 
                                            <input type="checkbox" name="salary_<%=salaryLevel.getLevelCode()%>" value="1" <%=isSelect%>> 
                                            <%=salaryLevel.getLevelName()%> <%}			
										  }
										  %> 
                                          </td>
                                        </tr>
											<!-- update parameter unutk include summary.
											  		Khusus unutk Intimas
													Updated by Yunny
											  -->
											  		<tr align="left" valign="top"> 
													<td width="1%">&nbsp;</td>
                                                      <td width="17%" height="21" nowrap align="left">Summary Report
													   </td>
													    <%
													    if(summRadio==0){
													   %>
                                                      <td width="83%"> 
												       <input type="radio" name="summary" value="0" checked>
                                                        Without Summary 
                                                        <input type="radio" name="summary" value="1">
                                                        With Summary 
                                                        </td>
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
                                                    </tr>			
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" nowrap> <div align="left"><a href="javascript:cmdFooter()">Set Footer</a></div></td>
										  
                                          <td width="83%"> <table border="0" cellspacing="0" cellpadding="0" width="550">
											  <% if(setFooter==1){ %>
                                                <td  colspan="2"> : <input name="footer" type="text" size="85" value="<%=footer%>">
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
                                          <td>
										  <a href="javascript:cmdView()">Search for Employee</a>
										  </td>
                                        </tr>
                                      </table>
									  <%if(iCommand == Command.LIST || iCommand==Command.PRINT){%>
									  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                        <%if(tempQueryReport != null && tempQueryReport.size() > 0){%>
                                        <tr> 
                                          <td><hr></td>
                                        </tr>
                                        <tr> 
                                          <td> <%
												out.println(listData);
												
											%> 
                                            <% //out.println(vectPresence);%> </td>
                                        </tr>
										<tr><td>
										<table width="76%" border="0">
                                         
                                              <tr> 
                                                <td><font color="#FF0000"><em>V 
                                                  = </em></font><font color="#FF0000"><em>PRESENCE 
                                                  OK</em></font></td>
                                                <td><font color="#FF0000"><em>C 
                                                  = </em></font><font color="#FF0000"><em>LEAVE</em></font></td>
                                                <td><font color="#FF0000"><em>I 
                                                  = </em></font><font color="#FF0000"><em>SPECIAL 
                                                  DISPENSATION</em></font></td>
                                                <td><font color="#FF0000"><em>- = </em> </font><font color="#FF0000"><em>
												NEW EMP</em></font></td>
												 
                                                <td>&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td><font color="#FF0000"><em>L 
                                                  =</em></font><font color="#FF0000"><em>DAY 
                                                  OFF</em></font></td>
                                                <td><font color="#FF0000"><em>A 
                                                  = </em></font><font color="#FF0000"><em>ABSENCE</em></font></td>
                                                <td><font color="#FF0000"><em>HR 
                                                  =</em> </font><font color="#FF0000"><em>PUBLIC 
                                                  HOLIDAY</em></font></td>
                                                <td>&nbsp;</td>
                                              </tr>
                                            </table>
										</td>
										</tr>
                                        <%if(privPrint){%>
                                        <tr> 
                                          <td> <table width="23%" border="0" cellspacing="1" cellpadding="1">
											  <tr> 
                                                <td width="5%"><a href="javascript:reportPdfMontly()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
                                                <td width="95%"><b><a href="javascript:reportPdfMontly()" class="buttonlink">Print 
                                                  DW Report</a></b> </td>
                                              </tr>
                                            </table></td>
                                        </tr>
                                        <%}%>
                                        <%}else{%>
                                        <tr> 
                                          <td></td>
                                        </tr>
                                        <tr> 
                                          <td> <%
											out.println("<div class=\"msginfo\">&nbsp;&nbsp;No employee data found ...</div>");											
											%> 
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
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
  <tr>
    <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" -->
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
