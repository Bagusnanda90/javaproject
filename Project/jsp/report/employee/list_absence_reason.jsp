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
                  com.dimata.qdep.db.DBHandler,
                  com.dimata.harisma.entity.attendance.PstPresence,
				  com.dimata.harisma.entity.attendance.PstEmpSchedule,
                  com.dimata.harisma.entity.employee.Employee,
                  com.dimata.harisma.entity.masterdata.ScheduleSymbol,
				  com.dimata.harisma.session.lateness.SessEmployeeLateness,
				  com.dimata.harisma.session.employee.SessEmployee,
				  com.dimata.harisma.session.attendance.SessEmpSchedule,
                  com.dimata.harisma.entity.masterdata.Department,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
                  com.dimata.harisma.session.lateness.LatenessMonthly,
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
public Vector drawList(Vector objectClass, long periodId)
	{

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
		ctrlist.addHeader("Marital status/Religion","5%","2","0");
		ctrlist.addHeader("Emp.Cat","5%","2","0");
		//untuk header absence reason dari tabel master data
		Vector vectReason = new Vector(1,1);
		Vector listReason = PstReason.list(0,0,""," REASON ");
		if(listReason !=null && listReason .size() > 0){
			for(int j=0 ; j< listReason.size();j++){
				Reason reason = (Reason)listReason.get(j);
				ctrlist.addHeader(""+reason.getReason(),"5%","2","0");
				vectReason.add(""+reason.getReason());
				
			}
		}
		ctrlist.addHeader("Jumlah","5%","2","0");
		ctrlist.addHeader("Date","5%","0",""+listReason.size()+5+"");
		if(listReason !=null && listReason .size() > 0){
			for(int j=0 ; j< listReason.size();j++){
				Reason reason = (Reason)listReason.get(j);
				ctrlist.addHeader(""+reason.getReason(),"12%","0","0");
			}
		}
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
			int totReason = 0;
			Vector temp = (Vector)objectClass.get(i);
			Employee employee = (Employee) temp.get(0);
			Marital marital = (Marital)temp.get(1);
			Religion religion = (Religion) temp.get(2);
			//EmpSchedule empSchedule = (EmpSchedule) temp.get(3);
			//Marital marital = (Marital)objectClass.get(i);
			 Vector rowx = new Vector();
			 Vector rowPdf = new Vector();
			 
			index = i;

			rowx.add(String.valueOf(i+1));
			rowPdf.add(String.valueOf(i+1));

			rowx.add(employee.getFullName());
			rowPdf.add(employee.getFullName());
			rowx.add(""+Formater.formatDate(employee.getCommencingDate(),"dd-MMM-yy"));
			rowPdf.add(""+Formater.formatDate(employee.getCommencingDate(),"dd-MMM-yy"));
			rowx.add(marital.getMaritalCode()+"/"+religion.getReligion());
			rowPdf.add(marital.getMaritalCode()+"/"+religion.getReligion());
			//fetch empCategory
			EmpCategory empCategory = new EmpCategory();
			try{
				empCategory = PstEmpCategory.fetchExc(employee.getEmpCategoryId());
			}catch(Exception e){
				System.out.println("err. fetch EmpCategory"+e.toString());
			}
			rowx.add(""+empCategory.getEmpCategory());
			rowPdf.add(""+empCategory.getEmpCategory());
			if(listReason!=null && listReason.size() > 0){
				for(int r=0; r< listReason.size();r++){
					Reason reason = (Reason)listReason.get(r);
					Vector listAbsence = SessEmpSchedule.getAbsenceReason(employee.getOID(),periodId,reason.getNo());
					rowx.add(""+listAbsence.size());
					rowPdf.add(""+listAbsence.size());
					totReason = totReason + listAbsence.size();
				}
			}
			rowx.add(""+totReason);
			rowPdf.add(""+totReason);
			if(listReason!=null && listReason.size() > 0){
				for(int r=0; r< listReason.size();r++){
					Reason reason = (Reason)listReason.get(r);
					Vector listDate = SessEmpSchedule.getAbsenceReason(employee.getOID(),periodId,reason.getNo());
					String nilai ="";
					String nilaiPdf ="";
					//"(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31)";
					if(listDate!= null && listDate.size() > 0){
						for(int s=0;s<listDate.size();s++){
							if(s<listDate.size()-1){
								nilai = nilai +(String)listDate.get(s)+",\n";
								nilaiPdf = nilaiPdf +(String)listDate.get(s)+",";
							}
							else if(s==listDate.size()-1){
								nilai = nilai +(String)listDate.get(s);
								nilaiPdf = nilaiPdf +(String)listDate.get(s);
							}
							//System.out.println("nilai"+nilai);
						}
						rowx.add("["+nilai+"]");
						rowPdf.add("["+nilaiPdf+"]");
						
					}
					else{
						rowx.add("-");
						rowPdf.add("-");
					}
					
					//totReason = totReason + listAbsence.size();
				}
			}
			
			lstData.add(rowx);
			vectDataToPdf.add(rowPdf);
			
			//lstLinkData.add("");
		}

		//return ctrlist.drawMeList();
		result.add(ctrlist.drawList());
		result.add(vectDataToPdf);
		result.add(vectReason);
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
		ctrlist.addHeader("Emp. Category","5%","0","0");
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
			// fetch emp.category karyawan
			EmpCategory empCategory = new EmpCategory();
			try{
				empCategory = PstEmpCategory.fetchExc(employee.getEmpCategoryId());
			}catch(Exception e){
				System.out.println("err fetch EmpCategory..."+e.toString());
			}
			 
			rowx.add(""+empCategory.getEmpCategory());
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

<%!
    public String drawListReason(Vector objectClass) {
        String strSchedule = "<table border=\"0\" cellspacing=\"0\"" + 
            "cellpadding=\"1\" bgcolor=\"#E0EDF0\"><tr>";
        for (int i = 0; i < objectClass.size(); i++) {
            Reason reason = (Reason) objectClass.get(i);
            String description = "";
            //rowx.add(scheduleSymbol.getSymbol() + " = " + str_dt_TimeIn + " - " + str_dt_TimeOut);
                strSchedule += "<td>" + String.valueOf(reason.getReason()) + "</td><td>=</td><td>" + String.valueOf(reason.getDescription())+ "</td><td width=\"8\"></td>";
            //lstData.add(rowx);
            if ((i % 5) == 4) {
                strSchedule += "</tr>";
            }
        }
        strSchedule += "</tr></table>";
        return strSchedule;
    }
%>
<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,"department");
long oidPeriod = FRMQueryString.requestLong(request,"period");
int idx = FRMQueryString.requestInt(request,"week_idx");
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int reportPeriod = FRMQueryString.requestInt(request,"reportPeriod");
long gotoPeriod = FRMQueryString.requestLong(request, "hidden_goto_period");
String stCurrYear = FRMQueryString.requestString(request, "curr_year");
long oidSection = FRMQueryString.requestLong(request,"section");
int setFooter = FRMQueryString.requestInt(request,"setFooter");
String footer = FRMQueryString.requestString(request, "footer");



int iCurrYear = Calendar.getInstance().get(Calendar.YEAR);
if(stCurrYear.trim().length() == 0){
	stCurrYear = iCurrYear+"";
}

//System.out.println("reportPeriod  "+reportPeriod);
Vector tempReason = new Vector(1,1);
if(reportPeriod==PstReason.MONTLY){
	System.out.println("monthly");
}
else if(reportPeriod==PstReason.YEARLY){
	System.out.println("yearly");
}
if((iCommand == Command.LIST) && (reportPeriod==PstReason.MONTLY))
{
		tempReason = SessEmpSchedule.getEmployeeReason(oidDepartment,oidSection,oidPeriod);

}
else if((iCommand == Command.LIST) && (reportPeriod==PstReason.YEARLY)){
		String whereYear = " (YEAR("+PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]+")) = "+stCurrYear+ 
						   " OR (YEAR("+PstPeriod.fieldNames[PstPeriod.FLD_END_DATE]+")) = "+stCurrYear;
		Vector periodYear = PstPeriod.list(0,0,whereYear,"");
		tempReason = SessEmpSchedule.getEmployeeReasonYear(oidDepartment,periodYear,oidSection);
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
	
Section sect = new Section();
String sectName ="";
		try{
			sect = PstSection.fetchExc(oidSection);
			sectName = sect.getSection();
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
Vector reasonToPdf = new Vector (1,1);

if(reportPeriod==PstReason.MONTLY){
	vectResult = drawList(tempReason, oidPeriod);
	listData = String.valueOf(vectResult.get(0));
	vectDataToPdf = (Vector)vectResult.get(1);
	vectReason = (Vector)vectResult.get(2);
}

else if(reportPeriod==PstReason.YEARLY){
	vectResult = drawListYear(tempReason, stCurrYear);
	listData = String.valueOf(vectResult.get(0));
	vectDataToPdf = (Vector)vectResult.get(1);
	vectReason = (Vector)vectResult.get(2);
}

// design vector that handle data to store in session

Vector vectPresence = new Vector(1,1);
if(reportPeriod==PstReason.MONTLY){
	vectPresence.add(""+periodName);
}
else if(reportPeriod==PstReason.YEARLY){
	vectPresence.add(""+stCurrYear);
}

 for (int i = 0; i < listReason.size(); i++) {
            Reason reason = (Reason) listReason.get(i);
			String reasonCode = reason.getReason();
            String description = reason.getDescription();
			reasonToPdf.add(reasonCode + "=" +description+" ");
	}
	
vectPresence.add(""+deptName);
vectPresence.add(vectDataToPdf);  
Vector listReasonAb = PstReason.list(0,0,""," REASON ");
vectPresence.add(""+listReasonAb.size()); 
vectPresence.add(vectReason); 
vectPresence.add(reasonToPdf);  
vectPresence.add(footer); 
vectPresence.add(sectName);  
 

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
<title>HARISMA - Report Reason Absence</title>
<script language="JavaScript">
<%if(iCommand==Command.PRINT){%>
    //com.dimata.harisma.report.listRequest	
	window.open("<%=printroot%>.report.listRequest.ListEmpCategPdf");
<%}%>

function cmdView(){
	document.frmreason.command.value="<%=Command.LIST%>";
	document.frmreason.action="list_absence_reason.jsp";
	document.frmreason.submit();
}

function periodChange(){
	document.frmreason.command.value="<%=Command.ADD%>";
	document.frmreason.action="list_absence_reason.jsp";
	document.frmreason.submit();
}

function reportPdf(){
		document.frmreason.command.value="<%=Command.PRINT%>";
		var linkPage ="<%=printroot%>.report.ListAbsenceReasonPdf";
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
function cmdFooter(){
	document.frmreason.setFooter.value="1";
	document.frmreason.action="list_absence_reason.jsp";
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
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Employee
                  &gt; List Number Of Absence<!-- #EndEditable --> </strong></font> </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td  style="background-color:<%=bgColorContent%>; ">
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
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
									  <table width="86%" border="0" cellspacing="2" cellpadding="2">
									  <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="13%" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                                          <td width="86%"> : 
                                            <%
                                          Vector deptValue = new Vector(1,1);
										  Vector deptKey = new Vector(1,1);
										  /*deptKey.add(" ALL DEPARTMET");
										  deptValue.add("0");*/
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
                                          <td width="13%" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div></td>
                                          <td width="86%"> : 
                                            <%
                                          Vector secValue = new Vector(1,1);
										  Vector secKey = new Vector(1,1);
										  secKey.add(" ALL ");
										  secValue.add("0");
						                  Vector listSec = PstSection.list(0, 0, "", "SECTION");
                                          for(int p=0;p<listSec.size();p++){
										  	Section section = (Section)listSec.get(p);
											secValue.add(""+section.getOID());
											secKey.add(section.getSection());
										  }
										  %> <%=ControlCombo.draw("section",null,""+oidSection,secValue,secKey,"")%></td>
                                        </tr>
										<tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="13%" align="right" nowrap> 
                                            <div align="left">Reprt Period </div></td>
                                          <td width="86%"> : 
                                            <%
                                          //untuk mengambil amount_gaji
											Vector vReasonValue = new Vector();
											Vector vReasonKey = new Vector();
											vReasonKey.add("select");
											vReasonValue.add("0");
											vReasonValue.add(""+PstReason.MONTLY+"");
											vReasonValue.add(""+PstReason.YEARLY+"");
											vReasonKey.add(""+PstReason.periodKey[PstReason.MONTLY]);
											vReasonKey.add(""+PstReason.periodKey[PstReason.YEARLY]);
											String selectPeriod = String.valueOf(gotoPeriod);
											//out.println("gotoPeriod  "+gotoPeriod);
										  %> <%=ControlCombo.draw("reportPeriod",null,""+selectPeriod,vReasonValue,vReasonKey,"onchange=\"javascript:deptChange();\"")%></td>
                                        </tr>
										<%
										if(gotoPeriod==1){
										%>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="13%" align="right" nowrap> 
                                            <div align="left">Period </div></td>
                                          <td width="86%"> : 
                                            <%
                                          Vector prdValue = new Vector(1,1);
										  Vector prdKey = new Vector(1,1);
						                  Vector listPeriod = PstPeriod.list(0, 0, "", "PERIOD");
                                          for(int d=0;d<listPeriod.size();d++){
										  	Period period = (Period)listPeriod.get(d);
											prdValue.add(""+period.getOID());
											prdKey.add(period.getPeriod());
										  }
										  %> <%=ControlCombo.draw("period",null,""+oidPeriod,prdValue,prdKey,"")%></td>
										  <%
										  	}
											else if(gotoPeriod==2){
											%>
												<tr> 
											  <td width="1%">&nbsp;</td>
											  <td width="13%" align="right" nowrap> 
												<div align="left">Year </div></td>
											  <td width="86%"> : 
											  <%
											  	Vector vYear = new Vector();
                                                    for(int i=iCurrYear+installInterval; i<iCurrYear+3; i++){
                                                        vYear.add(i+"");
                                                    }
											  %>
												<%=ControlCombo.draw("curr_year", null, stCurrYear, vYear, vYear, "", "formElemen")%></td>											<%
											}
										  %>
                                        </tr>
										<tr> 
											  <td width="1%">&nbsp;</td>
											  <td width="13%" align="right" nowrap> 
												<div align="left"><a href="javascript:cmdFooter()">Set Footer</a></div></td>
												<% if(setFooter==1){
												%>
											  <td width="86%"> : 
											 <input name="footer" type="text" size="85" value="<%=footer%>">
											 </td>
											 <%
											 }
											 %>
                                        </tr>
                                        <tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="13%" nowrap> <div align="left"></div></td>
                                          <td width="86%"> <table border="0" cellspacing="0" cellpadding="0" width="137">
                                              <tr> 
                                                <td width="16"></td>
                                                <td width="2"></td>
                                                <td width="94" class="command" nowrap> 
                                                </td>
                                              </tr>
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
										  <tr>
										  <tr><td>&nbsp;</td></tr>
											<td>
												<%=drawListReason(listReason)%>											
											</td>
										  </tr>
										  
										  <%if(privPrint){%>
										  <tr>
											<td>
											  <table width="27%" border="0" cellspacing="1" cellpadding="1">
                                              <tr>
											  <% if(reportPeriod==PstReason.MONTLY){
												%>
												  <td width="17%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
												  <td width="83%"><b><a href="javascript:reportPdf()" class="buttonlink">Print
													Absences Reason</a></b>
													<% }
												else if(reportPeriod==PstReason.YEARLY){
												%>
												  <td width="17%"><a href="javascript:reportPdfYear()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
												  <td width="83%"><b><a href="javascript:reportPdfYear()" class="buttonlink">Print
													Absences Reason</a></b>
												<%
												}
												%>
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
											out.println("<div class=\"msginfo\">&nbsp;&nbsp;No absence reason data found ...</div>");											
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
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
