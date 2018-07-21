<%@ page language="java" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>



<!-- package java -->
<%@ page import ="java.util.*,
                  com.dimata.harisma.entity.search.SrcLateness,
                  com.dimata.harisma.entity.attendance.EmpSchedule,
                  com.dimata.harisma.entity.attendance.Presence,
				  com.dimata.harisma.entity.attendance.AlStockTaken,
				  com.dimata.harisma.entity.attendance.AlStockManagement,
                  com.dimata.qdep.db.DBHandler,
                  com.dimata.harisma.entity.attendance.PstPresence,
				  com.dimata.harisma.entity.attendance.PstEmpSchedule,
				  com.dimata.harisma.entity.attendance.PstAlStockTaken,
				  com.dimata.harisma.entity.attendance.PstAlStockManagement,
                  com.dimata.harisma.entity.employee.Employee,
                  com.dimata.harisma.entity.masterdata.ScheduleSymbol,
				  com.dimata.harisma.session.lateness.SessEmployeeLateness,
				  com.dimata.harisma.session.employee.SessEmployee,
				  com.dimata.harisma.session.attendance.SessEmpSchedule,
                  com.dimata.harisma.entity.masterdata.Department,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
                  com.dimata.harisma.session.lateness.LatenessMonthly,
				  com.dimata.harisma.session.attendance.SessLeaveManagement,
                  com.dimata.harisma.entity.masterdata.PstScheduleSymbol"%>
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
public Vector drawList(Vector objectClass,String yearPeriod)
	{
		//System.out.println("objectClass  "+objectClass.size());
		Vector result = new Vector(1,1);
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No.","5%","2","0");
		ctrlist.addHeader("Full Name","15%","2","0");
		ctrlist.addHeader("Comm Date","10%","2","0");
		ctrlist.addHeader("Position","5%","2","0");
		ctrlist.addHeader("Leave Taken","5%","0","12");
		ctrlist.addHeader("JAN","3%","0","0");
		ctrlist.addHeader("FEB","3%","0","0");
		ctrlist.addHeader("MAR","3%","0","0");
		ctrlist.addHeader("APR","3%","0","0");
		ctrlist.addHeader("MAY","3%","0","0");
		ctrlist.addHeader("JUN","3%","0","0");
		ctrlist.addHeader("JUL","3%","0","0");
		ctrlist.addHeader("AUG","3%","0","0");
		ctrlist.addHeader("SEP","3%","0","0");
		ctrlist.addHeader("OCT","3%","0","0");
		ctrlist.addHeader("NOV","3%","0","0");
		ctrlist.addHeader("DEC","3%","0","0");
				
		ctrlist.addHeader("First Month of Leave","7%","2","0");
		ctrlist.addHeader("Entittled","7%","2","0");
		ctrlist.addHeader("Residue","7%","2","0");
		ctrlist.addHeader("Overtime/day","7%","2","0");
		ctrlist.addHeader("Value","7%","2","0");
		
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector vectDataToPdf = new Vector(1,1);
		//Vector lstLinkData = ctrlist.getLinkData();
		//ctrlist.setLinkPrefix("javascript:cmdEdit('");
		//ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
		
		for (int i = 0; i < objectClass.size(); i++) {
			//int totReason = 0;
			Vector temp = (Vector)objectClass.get(i);
			Employee employee = (Employee) temp.get(0);
			Department department = (Department)temp.get(1);
			Position position = (Position) temp.get(2);
			AlStockManagement  alStockManagement = (AlStockManagement) temp.get(3);
			 Vector rowx = new Vector();
			 
			index = i;

			rowx.add(String.valueOf(i+1));

			rowx.add(""+employee.getFullName());
			rowx.add(""+Formater.formatDate(employee.getCommencingDate(),"dd-MMM-yy"));
			rowx.add(position.getPosition());
			
			int totalTaken = 0;
			for(int m = 0 ; m<12;m++){
			String startDate ="";
			String endDate = "";
				if(m==0){
					//java.util.Date presenceDateTime = new java.util.Date(y-1900, M-1, d, h, m);
					 startDate = ""+(Integer.parseInt(yearPeriod)-1)+"-12-26";
					 endDate =  ""+Integer.parseInt(yearPeriod)+"-"+(m+1)+"-25";
				}
				else{
					 startDate = ""+(Integer.parseInt(yearPeriod))+"-"+m+"-26";
					 endDate =  ""+Integer.parseInt(yearPeriod)+"-"+(m+1)+"-25";
				}
				String where = PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_TAKEN_DATE]+" BETWEEN '"+startDate+"'"+
								" AND '"+endDate+"'"+
								" AND "+PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_EMPLOYEE_ID]+ " = "+employee.getOID();

				int  sumLeave = PstAlStockTaken.getSumLeave(where);
				rowx.add(""+sumLeave);
				totalTaken = totalTaken + sumLeave;
			}
			int comYear = employee.getCommencingDate().getYear()+1900;
			int monthYear = employee.getCommencingDate().getMonth()+1;
			int intCurrYear = Integer.parseInt(String.valueOf(yearPeriod));
			String firstMonth="";
			// entitled diset 12
			int entitled = 0;
			int residue = 0;
			int residuePure = 0;
			// jika bekerja sudah lebih dari 1 tahun min 2tahun
		if(intCurrYear > comYear){
			if((intCurrYear - comYear) > 1){
				rowx.add("JANUARY "+intCurrYear);// bulan aal
				rowx.add(""+alStockManagement.getEntitled());//entitled
				residue = alStockManagement.getEntitled() - totalTaken;
				residuePure = residue;
				if(residue < 0){
					residue = residue * -1;
					rowx.add("("+residue+")");// sisa nilainya (-)
				}
				else{
					rowx.add(""+residue);// sisa
				}	
			}
			// jika bekerja setahun 
			else if(intCurrYear - comYear==1){
				// cek bulan kerja dengan commencing date-nya
				int leaveMonthFirst = monthYear+1;
				String dateLeave = ""+intCurrYear+"-"+leaveMonthFirst+"-01";
				Date date = new Date(intCurrYear-1900,monthYear,1);
				if(monthYear==12){
					rowx.add("- ");// bulan awal
					rowx.add(""+entitled);//entitled
					residue = entitled - totalTaken;
					residuePure = residue;
					if(residue < 0){
							residue = residue * -1;
							rowx.add("("+residue+")");// sisa nilainya (-)
					}
					else if(residue==0){
						rowx.add("0");
					}
				}
				else{
					rowx.add(""+Formater.formatDate(date,"MMMM ").toUpperCase() +intCurrYear);
					entitled = alStockManagement.getEntitled() - monthYear;
					rowx.add(""+entitled);//entitled
					residue = entitled - totalTaken;
					residuePure = residue;
					if(residue < 0){
						residue = residue * -1;
						rowx.add("("+residue+")");
					}
					else{
					rowx.add(""+residue);// sisa
					}
				}
					
			}
		}
		// jika belum bekerja pada period yang dipilih / belum dapat cuti
		else{
			rowx.add("- ");// bulan awal
			rowx.add(""+entitled);//entitled
			residue = entitled - totalTaken;
			residuePure = residue;
			if(residue < 0){
					residue = residue * -1;
					rowx.add("("+residue+")");// sisa nilainya (-)
				}
			else if(residue==0){
				rowx.add("0");
			}
		}
		double ovValue = Double.parseDouble(""+PstSystemProperty.getValueByName("OVERTIME_VALUE")); 
		double timeWorksDay = Double.parseDouble(""+PstSystemProperty.getValueByName("TIME_WORKS_DAY")); 
		double timeWorksMonth = Double.parseDouble(""+PstSystemProperty.getValueByName("TIME_WORKS_MONTH")); 
		// untuk karyawan yang sisa cutinya tidak diganti dengan uang
		double totOvValue = totOvValue = ((ovValue/timeWorksMonth)*2*timeWorksDay);
		String frmCurrency = "#,###";
		rowx.add(""+Formater.formatNumber(totOvValue, frmCurrency));
		if(employee.getLeaveStatus()==PstEmployee.NO_CHANGE){
			rowx.add(""+Formater.formatNumber(0, frmCurrency));
		}else{
			rowx.add(""+Formater.formatNumber((totOvValue*residuePure), frmCurrency));
		}
			lstData.add(rowx);
			vectDataToPdf.add(rowx);
			//lstLinkData.add("");
		}

		//return ctrlist.drawMeList();
		result.add(ctrlist.drawList());
		result.add(vectDataToPdf);
		//result.add(vectReason);
		//return ctrlist.drawList();
		return result;
	}
%>

<%!
public Vector drawListYear(Vector objectClass, String stCurrYear)
	{
		Vector result = new Vector(1,1);
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No.","5%","0","0");
		ctrlist.addHeader("Full Name","15%","0","0");
		ctrlist.addHeader("Comm Date","10%","0","0");
		ctrlist.addHeader("Marital status/Religion","5%","0","0");
		//untuk header absence reason dari tabel master data
		Vector vectReason = new Vector(1,1);
		Vector listReason = PstReason.list(0,0,""," REASON ");
		if(listReason !=null && listReason .size() > 0){
			for(int j=0 ; j< listReason.size();j++){
				Reason reason = (Reason)listReason.get(j);
				ctrlist.addHeader(""+reason.getReason(),"5%","0","0");
				vectReason.add(""+reason.getReason());
			}
		}
		ctrlist.addHeader("Jumlah","5%","0","0");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector vectDataToPdf = new Vector(1,1);
		ctrlist.reset();
		int index = -1;
		
		for (int i = 0; i < objectClass.size(); i++) {
			int totReason = 0;
			Vector temp = (Vector)objectClass.get(i);
			Employee employee = (Employee) temp.get(0);
			Marital marital = (Marital)temp.get(1);
			Religion religion = (Religion) temp.get(2);
			 Vector rowx = new Vector();
			 
			index = i;

			rowx.add(String.valueOf(i+1));

			rowx.add(employee.getFullName());

			rowx.add(""+Formater.formatDate(employee.getCommencingDate(),"dd-MMM-yy"));
			rowx.add(marital.getMaritalCode()+"/"+religion.getReligion());
			if(listReason!=null && listReason.size() > 0){
				for(int r=0; r< listReason.size();r++){
					Reason reason = (Reason)listReason.get(r);
					String whereYear = " (YEAR("+PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]+")) = "+stCurrYear+ 
						   				" OR (YEAR("+PstPeriod.fieldNames[PstPeriod.FLD_END_DATE]+")) = "+stCurrYear;
					Vector periodYear = PstPeriod.list(0,0,whereYear,"");
					int totYear = 0;
					if(periodYear!=null && periodYear.size() > 0){
						for(int y =0;y<periodYear.size();y++){
							Period prd = (Period)periodYear.get(y);
							long periodId = prd.getOID();
							Vector listAbsence = SessEmpSchedule.getAbsenceReason(employee.getOID(),periodId,reason.getNo());
							totYear = totYear +listAbsence.size();
						}
					}
					
					rowx.add(""+totYear);
					totReason = totReason + totYear;
				}
			}
			rowx.add(""+totReason);
			lstData.add(rowx);
			vectDataToPdf.add(rowx);
		
		}
		result.add(ctrlist.drawList());
		result.add(vectDataToPdf);
		result.add(vectReason);
		return result;
	}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,"department");
long oidPeriod = FRMQueryString.requestLong(request,"period");
long oidSection = FRMQueryString.requestLong(request,"section");
long oidLevel = FRMQueryString.requestLong(request,"level");
int idx = FRMQueryString.requestInt(request,"week_idx");
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int reportPeriod = FRMQueryString.requestInt(request,"reportPeriod");
long gotoPeriod = FRMQueryString.requestLong(request, "hidden_goto_period");
String stCurrYear = FRMQueryString.requestString(request, "curr_year");
String yearForm = FRMQueryString.requestString(request, "yearForm");
String yearTo = FRMQueryString.requestString(request, "yearTo");
int setFooter =  FRMQueryString.requestInt(request,"setFooter");
String footer = FRMQueryString.requestString(request, "footer");

int iCurrYear = Calendar.getInstance().get(Calendar.YEAR);
if(stCurrYear.trim().length() == 0){
	stCurrYear = iCurrYear+"";
}

int iCurrYearComm = Calendar.getInstance().get(Calendar.YEAR);
if(stCurrYear.trim().length() == 0){
	stCurrYear = iCurrYearComm+"";
}

//System.out.println("reportPeriod  "+reportPeriod);
Vector tempReason = new Vector(1,1);

//System.out.println("yearFrom "+yearForm);

if(iCommand == Command.LIST){
		tempReason = SessLeaveManagement.getEmployeeLeave(oidDepartment,oidSection,oidLevel,stCurrYear,yearForm,yearTo);
}

Period pr = new Period();
String periodName ="";
		try{
			pr = PstPeriod.fetchExc(oidPeriod);
			periodName = pr.getPeriod();
		}
		catch(Exception e){
	}
	
Department dept = new Department();
String deptName ="";
		try{
			dept = PstDepartment.fetchExc(oidDepartment);
			deptName = dept.getDepartment();
		}
		catch(Exception e){
	}
	
Level level = new Level();
String levName ="";
		try{
			level = PstLevel.fetchExc(oidLevel);
			levName = level.getLevel();
		}
		catch(Exception e){
	}

Vector listReason = new Vector(1,1);
listReason = PstReason.listAll();

Vector temp = new Vector();

// process on drawlist
Vector vectResult = new Vector(1,1);
String listData = "";
Vector vectDataToPdf = new Vector (1,1);
Vector vectReason = new Vector (1,1);
vectResult = drawList(tempReason,stCurrYear);
listData = String.valueOf(vectResult.get(0));
vectDataToPdf = (Vector)vectResult.get(1);
/*vectReason = (Vector)vectResult.get(2);*/

// design vector that handle data to store in session
Vector vectPresence = new Vector(1,1);
vectPresence.add(""+stCurrYear);
vectPresence.add(""+deptName);
vectPresence.add(vectDataToPdf);  
Vector listReasonAb = PstReason.list(0,0,""," REASON ");
vectPresence.add(""+yearForm); 
vectPresence.add(""+yearTo);  
vectPresence.add(""+levName);  
vectPresence.add(""+footer);  


/*int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));  
vectPresence.add(""+startDatePeriod); */    

if(session.getValue("ABSENCE_REASON")!=null){
	session.removeValue("ABSENCE_REASON");
}
session.putValue("ABSENCE_REASON",vectPresence);

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
	document.frmreason.action="list_employee_leave.jsp";
	document.frmreason.submit();
}

function cmdFooter(){
	document.frmreason.setFooter.value="1";
	document.frmreason.action="list_employee_leave.jsp";
	document.frmreason.submit();
}

function periodChange(){
	document.frmreason.command.value="<%=Command.ADD%>";
	document.frmreason.action="list_absence_reason.jsp";
	document.frmreason.submit();
}

function reportPdf(){
	var linkPage ="<%=printroot%>.report.ListEmployeeLeavePdf";
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
									<input type="hidden" name="hidden_goto_period" value="<%=gotoPeriod%>">
									<input type="hidden" name="setFooter" value="<%=setFooter%>">
									  <table width="93%" border="0" cellspacing="2" cellpadding="2">
									  <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                                          <td width="83%"> : 
                                            <%
                                          Vector deptValue = new Vector(1,1);
										  Vector deptKey = new Vector(1,1);
										  //deptKey.add(" ALL DEPARTMET");
										  //deptValue.add("0");
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
                                          for(int p=0;p<listSect.size();p++){
										  	Section section = (Section)listSect.get(p);
											sectValue.add(""+section.getOID());
											sectKey.add(section.getSection());
										  }
										  %> <%=ControlCombo.draw("section",null,""+oidSection,sectValue,sectKey,"")%></td>
                                        </tr>
										<tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td nowrap width="16%">Level</td>
                                          <td width="83%"> :
                                            <% 
												Vector lev_value = new Vector(1,1);
												Vector lev_key = new Vector(1,1); 
												lev_value.add("0");
												lev_key.add(" ALL ");
												Vector listLev = PstLevel.list(0, 0, "", " LEVEL_ID, LEVEL ");
												for (int i = 0; i < listLev.size(); i++) 
												{
													Level lev = (Level) listLev.get(i);
													lev_key.add(lev.getLevel());
													lev_value.add(String.valueOf(lev.getOID()));
												}
												%>
												<%=ControlCombo.draw("level",null, ""+oidLevel, lev_value, lev_key, "")%>
	                                          </td>
                                        </tr>
											<tr> 
											  <td width="1%">&nbsp;</td>
											  <td width="16%" align="right" nowrap> 
												<div align="left">Year </div></td>
											  <td width="83%"> : 
											  <%
											  	Vector vYear = new Vector();
                                                    for(int i=iCurrYear+installInterval; i<iCurrYear+3; i++){
                                                        vYear.add(i+"");
                                                   }
											  %>
												<%=ControlCombo.draw("curr_year", null, stCurrYear, vYear, vYear, "", "formElemen")%></td>
                                        </tr>
												<tr> 
											  <td width="1%">&nbsp;</td>
											  <td width="16%" align="right" nowrap> 
												<div align="left"><%=dictionaryD.getWord(I_Dictionary.COMMENCING_DATE) %></div></td>
											  <td width="83%"> : 
											  <%
											  	Vector vComm = new Vector();
												    for(int i=(iCurrYearComm-10)+installInterval; i<iCurrYearComm+2; i++){
                                                        vComm.add(i+"");
                                                   }
											  %>
												<%=ControlCombo.draw("yearForm", null, yearForm, vComm, vComm, "", "formElemen")%> to <%=ControlCombo.draw("yearTo", null, yearTo, vComm, vComm, "", "formElemen") %></td>
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
									  <%if(tempReason != null && tempReason.size() > 0){%>
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
													Leave Report</a></b>
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
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" -->
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
