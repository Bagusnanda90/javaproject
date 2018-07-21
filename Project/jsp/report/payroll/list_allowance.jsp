
<%@ page language="java" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<%@ page import = "com.dimata.qdep.db.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>


<!-- package java -->
<%@ page import ="java.util.*,
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
				  com.dimata.harisma.session.attendance.SessEmpSchedule,
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
public Vector drawList(Vector objectClass,long oidPeriod,Vector secSelect,long departmentId)
	{
		Vector result = new Vector(1,1);
		Vector vectCompBenefit= new Vector(1,1);
		Vector vectCompDeduction= new Vector(1,1);
		Vector totalFooter = new Vector(1,1);
		double totalAllowance = 0;
		int totLate=0;
		int totAbsence=0;
		int totalLateAb = 0;
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("80%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No","2%","0","0");
		ctrlist.addHeader("Name","10%","0","0");
		String where = "slip."+PstSalaryLevelDetail.fieldNames[PstSalaryLevelDetail.FLD_TAKE_HOME_PAY]+"="+PstSalaryLevelDetail.NO_TAKE+
					   	" AND PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_TAX_ITEM]+"="+PstPayComponent.TUNJANGAN;
		String order =  "PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE]+","+
						"PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_SORT_IDX];
		String groupBy = "PAY."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_NAME];

		Vector headComp = PstPayComponent.getHeaderComponent(where,order,groupBy);
		if(headComp !=null && headComp.size() >0){
			for(int b=0;b<headComp.size();b++){
				PayComponent payComp = (PayComponent)headComp.get(b);
				ctrlist.addHeader(""+payComp.getCompName().toUpperCase(),"5%","0","0");
				vectCompBenefit.add(payComp.getCompName());
			}
		}
		ctrlist.addHeader("Total","5%","0","0");
		ctrlist.addHeader("Signature","5%","0","0");
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
		for (int i = 0; i <=objectClass.size(); i++) {
			if(i==objectClass.size()){
			//untuk footer
				 Vector rowx = new Vector();
				  Vector rowxPdf = new Vector();
				  rowx.add("<b>Total</b>");
				  rowxPdf.add("Total");
				  rowx.add("&nbsp;");
				  rowxPdf.add("");
				  for(int b=0;b<headComp.size();b++){
				 	 PayComponent payComp = (PayComponent)headComp.get(b);
					 double sumComponent = PstPaySlipComp.getSumComponentAllowance(secSelect,payComp.getCompCode(),oidPeriod,departmentId,2);
					 rowx.add("<b><div align=\"right\">"+Formater.formatNumber(sumComponent, frmCurrency)+"</div></b>");
					 rowxPdf.add(""+Formater.formatNumber(sumComponent, frmCurrency));
					// sumBenefit = sumBenefit + sumComponent;
				 }
				  
				  rowx.add("<b><div align=\"right\">"+Formater.formatNumber(totalAllowance, frmCurrency)+"</div></b>");
				  rowxPdf.add(""+Formater.formatNumber(totalAllowance, frmCurrency));
				  rowx.add("&nbsp;");
				  rowxPdf.add("");
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
				 double total = 0;
				 rowx.add(String.valueOf(i+1));
				 rowxPdf.add(String.valueOf(i+1));
				 rowx.add(""+employee.getFullName());
				 rowxPdf.add(""+employee.getFullName());
				 //ambil nilai dari pay slip id dengan comp_code tertentu (benefit)
				 if(headComp !=null && headComp.size() >0){
					for(int b=0;b<headComp.size();b++){
						PayComponent payComp = (PayComponent)headComp.get(b);
						double compValue = PstPaySlipComp.getReportCompValue(paySlip.getOID(),payComp.getCompCode());
						rowx.add("<div align=\"right\">"+Formater.formatNumber(compValue, frmCurrency)+"</div>");
						rowxPdf.add(""+Formater.formatNumber(compValue, frmCurrency));
						if(payComp.getCompType()==PstPayComponent.TYPE_BENEFIT){
							total = total + compValue;
						}
						else if(payComp.getCompType()==PstPayComponent.TYPE_DEDUCTION){
							total = total - compValue;
						}
						
					}
				}
				//JIka nilai total Late adalah yang no absence dan telat
				Vector vectAbsence = SessEmpSchedule.getEmpWithoutRegAllowance(employee.getOID(),oidPeriod);
				//dipakai jika nilainya termasuk juga yang libur,cuti dan sakit
				//Vector vectAbsence = SessEmpSchedule.getEmpAbsenceAllowance(employee.getOID(),oidPeriod);
				Vector vectLate = SessEmpSchedule.getEmpLateAllowance(employee.getOID(),oidPeriod);
				rowx.add("<div align=\"right\">"+Formater.formatNumber(total, frmCurrency)+"</div>");
				rowxPdf.add(""+Formater.formatNumber(total, frmCurrency));
				totalAllowance = totalAllowance + total;
				rowx.add("&nbsp;");
				rowxPdf.add("");
				totLate = totLate + vectLate.size();
				totAbsence = totAbsence + vectAbsence.size();
				lstData.add(rowx);
				vectDataToPdf.add(rowxPdf);
			}
			totalLateAb = totLate + totAbsence;
		}
		
		result.add(ctrlist.drawList());
		result.add(vectDataToPdf);
		result.add(vectCompBenefit);
		result.add(""+totalAllowance);
		result.add(""+totalLateAb);
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
String frmCurrency = "#,###";
String addStaffAllowance = "Total Staff Allowance";
String strAddWithoutReg = "Presence Without Registration";

Vector vct = new Vector(1,1);

vct = PstSalaryLevel.list(0,0, "", PstSalaryLevel.fieldNames[PstSalaryLevel.FLD_LEVEL_NAME]);
Vector secSelect = new Vector(1,1);

if(vct!=null && vct.size()>0){
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
}


Vector tempQueryReport = new Vector(1,1);
if(iCommand == Command.LIST){
		// 2 adalah nilai konstanta dimana semua karyawan ditampilkan tanpa memperdulikan ditransfer atau tidak
		tempQueryReport = SessEmployee.getEmpSalary(secSelect,oidPeriod,departmentId,2);
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
String departmentName ="";
		try{
			dept = PstDepartment.fetchExc(departmentId);
			departmentName = dept.getDepartment();
		}
		catch(Exception e){
	}
		
//String levelName = PstSalaryLevel.getSalaryName(levelCode);
Vector temp = new Vector();

// process on drawlist
Vector vectResult = new Vector(1,1);
String listData = "";
Vector vectDataToPdf = new Vector (1,1);
Vector vectHeadComp = new Vector (1,1);
String totalAllowance = "";
int withoutReg= 0;
String totLate = "";


vectResult = drawList(tempQueryReport,oidPeriod,secSelect,departmentId);
listData = String.valueOf(vectResult.get(0));
vectDataToPdf = (Vector)vectResult.get(1);
vectHeadComp = (Vector)vectResult.get(2);
totalAllowance = String.valueOf(vectResult.get(3));
totLate = String.valueOf(vectResult.get(4));
System.out.println("vectHeadComp..."+vectHeadComp);

// design vector that handle data to store in session
Vector vectPresence = new Vector(1,1);
vectPresence.add(vectDataToPdf);  
vectPresence.add(""+periodName);  
vectPresence.add(""+summRadio);  
vectPresence.add(""+departmentName);  
vectPresence.add(""+footer);
double intTotalAllowance = Double.parseDouble(totalAllowance);
// Jika ada additional--------------------------------
int addTotalAllowance = PstPayAdditional.getAddValue(addStaffAllowance);
intTotalAllowance = intTotalAllowance + addTotalAllowance;

int addWithoutReg = PstPayAdditional.getAddValue(strAddWithoutReg);
withoutReg = withoutReg + addWithoutReg;

int	totalLate = Integer.parseInt(totLate) * Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("MEAL_ALLOWANCE")));
withoutReg = withoutReg + totalLate;
//sampai sini-----------------------------------------

vectPresence.add(""+intTotalAllowance);  
vectPresence.add(vectHeadComp);  
vectPresence.add(""+withoutReg);  
vectPresence.add(""+(intTotalAllowance+withoutReg));  

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
	document.frmreason.action="list_allowance.jsp";
	document.frmreason.submit();
}

function cmdFooter(){
	document.frmreason.setFooter.value="1";
	document.frmreason.action="list_allowance.jsp";
	document.frmreason.submit();
}

function periodChange(){
	document.frmreason.command.value="<%=Command.ADD%>";
	document.frmreason.action="list_allowance.jsp";
	document.frmreason.submit();
}

function reportPdf(){
	var linkPage ="<%=printroot%>.report.payroll.ListStaffAllowancePdf";
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
                  &gt; Staff's Allowance Report<!-- #EndEditable --> </strong></font> </td>
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
						                  Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                          for(int p=0;p<listDepartment.size();p++){
										  	Department department = (Department)listDepartment.get(p);
											deptValue.add(""+department.getOID());
											deptKey.add(department.getDepartment());
										  }
										  %> 
                                            <%=ControlCombo.draw("department",null,""+departmentId,deptValue,deptKey,"")%></td>
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
										  %> 
                                            <%=ControlCombo.draw("periode",null,""+oidPeriod,perValue,perKey,"")%></td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                          <td nowrap>Salary Level</td>
                                          <td> <%
										  if(vct!=null && vct.size()>0){
										  	for(int i=0; i<vct.size(); i++){
												SalaryLevel salaryLevel = (SalaryLevel)vct.get(i);
												String isSelect = getSelected(salaryLevel, secSelect);
												
												%> 
                                            <input type="checkbox" name="chx_<%=salaryLevel.getLevelCode()%>" value="1" <%=isSelect%>> 
                                            <%=salaryLevel.getLevelName()%> <%}			
										  }
										  %> 
                                          </td>
                                        </tr>
										<!-- update parameter unutk include summary.
											  		Khusus unutk Intimas
													Updated by Yunny
											  -->
                                        <tr> 
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
										  	if(summRadio==1){
											
										  %>
										  <tr>
										  	<td height="48" colspan="3"><table width="29%" border="0">
											  <tr>
												<td width="57%"><strong>Total 
                                                  Staff Allowance</strong></td>
												<td>=</td>
												<td width="43%" align="right"><strong><%=Formater.formatNumber(intTotalAllowance, frmCurrency)%></strong></td>
											  </tr>
											  <tr>
												<td width="57%"><strong>Presence Without Registration</strong></td>
												<td>=</td>
												<td width="43%" align="right"><strong><%=Formater.formatNumber(withoutReg, frmCurrency)%></strong></td>
											  </tr>
											  <tr>
												<td width="57%"><strong>Total</strong></td>
												<td>=</td>
												<td width="43%" align="right"><strong><%=Formater.formatNumber((withoutReg+intTotalAllowance), frmCurrency)%></strong></td>
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
											  <table width="23%" border="0" cellspacing="1" cellpadding="1">
                                              <tr>
												  <td width="5%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
												  <td width="95%"><b><a href="javascript:reportPdf()" class="buttonlink">Print
													Staff's Allowance</a></b>
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
