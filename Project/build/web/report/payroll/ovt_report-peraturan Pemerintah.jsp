
<%@ page language="java" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.qdep.db.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package java -->
<%@ page import ="java.util.*,
                  com.dimata.harisma.entity.payroll.Ovt_Employee,
                  com.dimata.harisma.entity.payroll.PstOvt_Employee,
				  com.dimata.harisma.entity.payroll.Ovt_Idx,
				  com.dimata.harisma.entity.payroll.PstOvt_Idx,
				  com.dimata.harisma.entity.payroll.Ovt_Type,
				  com.dimata.harisma.entity.payroll.PstOvt_Type,
				  com.dimata.harisma.entity.payroll.PayEmpLevel,
				  com.dimata.harisma.entity.payroll.PstPayEmpLevel,
	              com.dimata.harisma.entity.employee.Employee,
				  com.dimata.harisma.session.employee.SessEmployee,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
                  com.dimata.harisma.entity.employee.PstEmployee"%>
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LATENESS_REPORT, AppObjInfo.OBJ_LATENESS_MONTHLY_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%!
public Vector drawList(Vector objectClass,long oidPeriod,int startDate,int maxDayOfMonth)
	{
		Vector result = new Vector(1,1);
		Vector vectDatePeriod= new Vector(1,1);
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No","2%","2","0");
		ctrlist.addHeader("Name","5%","2","0");
		//ctrlist.addHeader("Date - Time Overtime","5%","0",""+maxDayOfMonth);
		// ambil jenis overtime ( overtime saat hari working day )
		String groupBy = "";
		groupBy = "type."+PstOvt_Type.fieldNames[PstOvt_Type.FLD_OVT_TYPE_CODE];
		Vector vectOvtType1 = PstOvt_Type.getOvertimeType(PstOvt_Type.WORKING_DAY,groupBy);
		Vector vectOvtType = PstOvt_Type.getOvertimeType(PstOvt_Type.WORKING_DAY,"");
		double idxLimit = 0;
		//ambil hoursFrom record terakhir untuk parameter pembanding
		if(vectOvtType1!=null && vectOvtType1.size() > 0){
			for(int l=0;l<vectOvtType1.size();l++){
				Vector temp = (Vector)vectOvtType1.get(l);
				Ovt_Idx ovtIdx = (Ovt_Idx)temp.get(0);
				Ovt_Type ovtType = (Ovt_Type)temp.get(1);
				idxLimit = ovtIdx.getHour_to();
			}
		}
		//System.out.println("idxLimit......................."+idxLimit);
		int dateOvt = startDate ;
		ctrlist.addHeader(""+dateOvt,"2%","0",""+vectOvtType.size());
		vectDatePeriod.add(""+dateOvt);
			//**************** untuk indexnya************************ 
			if(vectOvtType!=null && vectOvtType.size() > 0){
				String strIdx = "";
				for (int o=0;o<vectOvtType.size();o++){
					strIdx = strIdx + "I";
					ctrlist.addHeader(""+strIdx,"1%","0","0");
					
				}
			}
			//******************************************************
		for(int m=0;m<(maxDayOfMonth-1);m++){
			if(dateOvt==maxDayOfMonth){
				dateOvt = 0;
			}
			dateOvt ++;
			ctrlist.addHeader(""+dateOvt,"2%","0",""+vectOvtType.size());
			vectDatePeriod.add(""+dateOvt);
			//**************** untuk indexnya************************ 
			if(vectOvtType!=null && vectOvtType.size() > 0){
				String strIdx = "";
				for (int o=0;o<vectOvtType.size();o++){
					strIdx = strIdx + "I";
					ctrlist.addHeader(""+strIdx,"1%","0","0");
				}
			}
			//******************************************************
		}
		ctrlist.addHeader("Total","2%","0",""+vectOvtType.size());
		if(vectOvtType!=null && vectOvtType.size() > 0){
				String strIdx = "";
				for (int o=0;o<vectOvtType.size();o++){
					strIdx = strIdx + "I";
					ctrlist.addHeader(""+strIdx,"1%","0","0");
				}
		}
		ctrlist.addHeader("Total","2%","2","0");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector vectDataToPdf = new Vector(1,1);
		Vector vectFooter = new Vector(1,1);
		Vector vectTypeOvt = new Vector(1,1);
		String whereClause = "";
		String frmCurrency = "#,###";
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
		double totFinalI=0;
		double totFinalII=0;
		double totFooter = 0;
		vectTypeOvt.add(""+vectOvtType.size());
		for (int i = 0; i <=objectClass.size(); i++) {
			 if(i==objectClass.size()){
			 	Vector rowx = new Vector();
			    Vector rowxPdf = new Vector();
				rowx.add("");
				rowxPdf.add("");
			 	rowx.add("<b>Total</b>");
				rowxPdf.add("Total");
				for(int j=0;j<(vectDatePeriod.size()*vectOvtType.size());j++){
					rowx.add("");
					rowxPdf.add("");
				}
				rowx.add("<b>"+Formater.formatNumber(totFinalI, "#,###.##")+"</b>");
				rowxPdf.add(""+Formater.formatNumber(totFinalI, "#,###.##"));
				rowx.add("<b>"+Formater.formatNumber(totFinalII, "#,###.##")+"</b>");
				rowxPdf.add(""+Formater.formatNumber(totFinalII, "#,###.##"));
				rowx.add("<b>"+Formater.formatNumber(totFooter, "#,###.##")+"</b>");
				rowxPdf.add(""+Formater.formatNumber(totFooter, "#,###.##"));
				lstData.add(rowx);
				vectDataToPdf.add(rowxPdf);
			 }
			 else{
			 Vector temp = (Vector)objectClass.get(i);
			 Employee employee = (Employee)temp.get(0);
			 Ovt_Employee ovtEmp = (Ovt_Employee)temp.get(1);
			 PayEmpLevel payEmpLevel = (PayEmpLevel)temp.get(3);
			 
			 Vector rowx = new Vector();
			 Vector rowxPdf = new Vector();
			 double totI = 0;
			 double totII = 0;
			 rowx.add(String.valueOf(i+1));
			 rowxPdf.add(String.valueOf(i+1));
			 rowx.add(""+employee.getFullName());
			 rowxPdf.add(""+employee.getFullName());
			 // row untuk jumlah jam lemburnya
			 for(int j=0;j<vectDatePeriod.size();j++){
			 	double ovTime = PstOvt_Employee.getOvtDuration(String.valueOf(vectDatePeriod.get(j)),oidPeriod,employee.getEmployeeNum());
				/*
					aturan overtime 
					0.15 - 0.44 = 0.5 jam
					0.45 - 1.00 = 1 jam
				*/
				if(ovTime > 0){
					if(payEmpLevel.getOvtIdxType()==PstPayEmpLevel.NO_INDEX){
						rowx.add("");
						rowxPdf.add("");
						rowx.add(""+ovTime);
						rowxPdf.add(""+ovTime);
						totII = totII + ovTime;
					}else{
						if(ovTime > idxLimit ){
								double ovTimeResidue=0;
								ovTimeResidue = ovTime - idxLimit;
								rowx.add(""+idxLimit);
								rowxPdf.add(""+idxLimit);
								rowx.add(""+ovTimeResidue);
								rowxPdf.add(""+ovTimeResidue);
								totII = totII + ovTimeResidue;
								totI = totI + idxLimit;
						}
						else {
								rowx.add(""+ovTime);
								rowxPdf.add(""+ovTime);
								rowx.add("");
								rowxPdf.add("");
								totI = totI + ovTime;
						}
				  }
				}
				else {
					rowx.add("");
					rowxPdf.add("");
					rowx.add("");
					rowxPdf.add("");
				}
			 }
			 rowx.add(""+Formater.formatNumber(totI, "#,###.##"));
			 rowxPdf.add(""+Formater.formatNumber(totI, "#,###.##"));
			 totFinalI = totFinalI + totI;
			 rowx.add(""+Formater.formatNumber(totII, "#,###.##"));
			 rowxPdf.add(""+Formater.formatNumber(totII, "#,###.##"));
			 totFinalII = totFinalII + totII; 
			 rowx.add(""+Formater.formatNumber((totII+totI), "#,###.##"));
			 rowxPdf.add(""+Formater.formatNumber((totII+totI), "#,###.##"));
			 totFooter = totFooter + (totII+totI);
			lstData.add(rowx);
			vectDataToPdf.add(rowxPdf);
			}
		}
		result.add(ctrlist.drawList());
		result.add(vectDataToPdf);
		result.add(vectTypeOvt);
		return result;
	}
%>
<%!
public Vector drawListNoIndex(Vector objectClass,long oidPeriod,int startDate,int maxDayOfMonth)
	{
		Vector result = new Vector(1,1);
		Vector vectDatePeriod= new Vector(1,1);
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No","2%","0","0");
		ctrlist.addHeader("Name","10%","0","0");
		int dateOvt = startDate ;
		ctrlist.addHeader(""+dateOvt,"2%","0","0");
		vectDatePeriod.add(""+dateOvt);
		for(int m=0;m<(maxDayOfMonth-1);m++){
			if(dateOvt==maxDayOfMonth){
				dateOvt = 0;
			}
			dateOvt ++;
			ctrlist.addHeader(""+dateOvt,"2%","0","0");
			vectDatePeriod.add(""+dateOvt);
		}
		ctrlist.addHeader("Total","2%","0","0");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector vectDataToPdf = new Vector(1,1);
		Vector vectTypeOvt = new Vector(1,1);
		double totFinal = 0;
		for (int i = 0; i <= objectClass.size(); i++) {
			if(i==objectClass.size()){
				Vector rowx = new Vector();
			    Vector rowxPdf = new Vector();
				rowx.add("");
				rowxPdf.add("");
				rowx.add("<b>Total</b>");
				rowxPdf.add("Total");
				for(int j=0;j<vectDatePeriod.size();j++){
					rowx.add("");
					rowxPdf.add("");
				}
				rowx.add(""+totFinal);
				rowxPdf.add(""+totFinal);
				lstData.add(rowx);
				vectDataToPdf.add(rowxPdf);
				vectTypeOvt.add("1");
			}else{
			 Vector temp = (Vector)objectClass.get(i);
			 Employee employee = (Employee)temp.get(0);
			 Ovt_Employee ovtEmp = (Ovt_Employee)temp.get(1);
			 Vector rowx = new Vector();
			 Vector rowxPdf = new Vector();
			 double totI = 0;
			 rowx.add(String.valueOf(i+1));
			 rowxPdf.add(String.valueOf(i+1));
			 rowx.add(""+employee.getFullName());
			 rowxPdf.add(""+employee.getFullName());
			 for(int j=0;j<vectDatePeriod.size();j++){
			 	double ovTime = PstOvt_Employee.getOvtDuration(String.valueOf(vectDatePeriod.get(j)),oidPeriod,employee.getEmployeeNum());
				if(ovTime > 0){
					rowx.add(""+ovTime);
					rowxPdf.add(""+ovTime);
					totI = totI + ovTime;
				}else{
					rowx.add("");
					rowxPdf.add("");
				}
			}
			rowx.add(""+Formater.formatNumber(totI, "#,###.##"));
			rowxPdf.add(""+Formater.formatNumber(totI, "#,###.##"));
			totFinal = totFinal + totI;
			lstData.add(rowx);
			vectDataToPdf.add(rowxPdf);
			vectTypeOvt.add("1");
			}
		}
		result.add(ctrlist.drawList());
		result.add(vectDataToPdf);
		result.add(vectTypeOvt);
		return result;
	}
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment= FRMQueryString.requestLong(request,"department");
long oidPeriod = FRMQueryString.requestLong(request,"periode");
long oidSection = FRMQueryString.requestLong(request,"section");
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int reportPeriod = FRMQueryString.requestInt(request,"reportPeriod");
int setFooter =  FRMQueryString.requestInt(request,"setFooter");
String footer = FRMQueryString.requestString(request, "footer");
String titleName = FRMQueryString.requestString(request, "titleName");
int summRadio= FRMQueryString.requestInt(request, "idx_type");


Vector tempQueryReport = new Vector(1,1);
if(iCommand == Command.LIST ){
		tempQueryReport = SessEmployee.getOvtEmployee(oidDepartment,oidSection,oidPeriod);
}
//fetch data untuk period
Period pr = new Period();
Date date = new Date();
String periodName ="";
		try{
			pr = PstPeriod.fetchExc(oidPeriod);
			date =  pr.getStartDate();
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

// get maximum date of selected month
Calendar newCalendar = Calendar.getInstance();
newCalendar.setTime(date);
int dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); 

int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));  



// process on drawlist
Vector vectResult = new Vector(1,1);
String listData = "";
Vector vectDataToPdf = new Vector (1,1);
Vector vectTypeOvt = new Vector (1,1);
if(summRadio==PstPayEmpLevel.WITH_INDEX){
	vectResult = drawList(tempQueryReport,oidPeriod,startDatePeriod,dateOfMonth);
}else{
		vectResult = drawListNoIndex(tempQueryReport,oidPeriod,startDatePeriod,dateOfMonth);
}
listData = String.valueOf(vectResult.get(0));
vectDataToPdf = (Vector)vectResult.get(1);
vectTypeOvt = (Vector)vectResult.get(2);
// design vector that handle data to store in session
Vector vectPresence = new Vector(1,1);
vectPresence.add(vectDataToPdf);  
vectPresence.add(""+periodName);  
vectPresence.add(""+deptName);  
vectPresence.add(""+sectName);  
vectPresence.add(""+footer); 
vectPresence.add(""+dateOfMonth);  
vectPresence.add(""+startDatePeriod);  
vectPresence.add(""+vectTypeOvt.get(0)); 
vectPresence.add(""+summRadio); 

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
<title>HARISMA - Report off Employee Leave</title>
<script language="JavaScript">
<%if(iCommand==Command.PRINT){%>
    //com.dimata.harisma.report.listRequest	
	window.open("<%=printroot%>.report.listRequest.ListEmpCategPdf");
<%}%>

function cmdView(){
	document.frmreason.command.value="<%=Command.LIST%>";
	document.frmreason.action="ovt_report.jsp";
	document.frmreason.submit();
}

function cmdFooter(){
	document.frmreason.setFooter.value="1";
	document.frmreason.action="ovt_report.jsp";
	document.frmreason.submit();
}



function reportPdf(){
	var linkPage ="<%=printroot%>.report.payroll.ListOvtEmpPdf";
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
<!-- #EndEditable -->
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
  <tr>
    <td width="88%" valign="top" align="left">
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr>
          <td width="100%">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee
                  &gt; List Off Employee Leave<!-- #EndEditable --> </strong></font> </td>
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
									<input type="hidden" name="titleName" value="<%=titleName%>">
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
                                            <%
                                          Vector perValue = new Vector(1,1);
										  Vector perKey = new Vector(1,1);
										 // salkey.add(" ALL DEPARTMET");
										  //deptValue.add("0");
						                  Vector listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");
                                          for(int r=0;r<listPeriod.size();r++){
										  	Period period = (Period)listPeriod.get(r);
											perValue.add(""+period.getOID());
											perKey.add(period.getPeriod());
										  }
										  %> <%=ControlCombo.draw("periode",null,""+oidPeriod,perValue,perKey,"")%></td>
										
                                        </tr>
											<!-- update parameter unutk include summary.
											  		Khusus unutk Intimas
													Updated by Yunny
											  -->
											  <!--		<tr align="left" valign="top"> 
													<td width="1%">&nbsp;</td>
                                                      <td width="17%" height="21" nowrap align="left">Index Type
													   </td>
													    <%
													    //if(summRadio==0){
													   %>
                                                      <td width="83%"> 
												       <input type="radio" name="idx_type" value="0" checked>
                                                        With Index 
                                                        <input type="radio" name="idx_type" value="1">
                                                        Without Index 
                                                        </td>
														<%
														//}else if(summRadio==1){
														%>
														<td width="83%"> 
												       <input type="radio" name="idx_type" value="0">
                                                        With Index 
                                                        <input type="radio" name="idx_type" value="1" checked>
                                                        Without Index 
                                                        </td>
														<%
														//}
														%>
                                                    </tr>			
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" nowrap> <div align="left"><a href="javascript:cmdFooter()">Set Footer</a></div></td>
										  
                                          <td width="83%"> <table border="0" cellspacing="0" cellpadding="0" width="550">
											  <% //if(setFooter==1){ %>
                                                <td  colspan="2"> : <input name="footer" type="text" size="85" value="<%=footer%>">
												</td>
                                                <td width="1" class="command" nowrap> 
                                                </td>
                                              </tr>
											   <%
											  //}
											  %>
                                            </table></td>
                                        </tr>-->
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
									    <tr><td><hr></td></tr>
                                        <tr>
											<td>
											<%
												out.println(listData);
												
											%>
												<% //out.println(vectPresence);%>											
											</td>
										  </tr>
										  <%if(privPrint){%>
										  <tr>
											<td>
											  <table width="23%" border="0" cellspacing="1" cellpadding="1">
                                              <tr>
												  <td width="5%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
												  <td width="95%"><b><a href="javascript:reportPdf()" class="buttonlink">Print
													Overtime Report</a></b>
												  </td>
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
