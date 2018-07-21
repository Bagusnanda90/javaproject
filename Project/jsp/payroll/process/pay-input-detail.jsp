 
<%@ page language="java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<%@ page import = "java.util.Date" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.payroll.*" %>
<%@ page import = "com.dimata.harisma.form.payroll.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_PROCESS_INPUT);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%
	int prevCommand = FRMQueryString.requestInt(request, "prev_command");
	int detailSlipYN = FRMQueryString.requestInt(request, "detail_slip");
	int iCommand = FRMQueryString.requestCommand(request);
	long employeeId = FRMQueryString.requestLong(request,"employeeId");
	String salaryLevel = FRMQueryString.requestString(request,"salaryLevel");
	long paySlipId = FRMQueryString.requestLong(request,"paySlipId");
	long periodId = FRMQueryString.requestLong(request,"periodId");
	int keyPeriod = FRMQueryString.requestInt(request,"paySlipPeriod");
        //update by satrya 2013-01-24
        long payGroupId = FRMQueryString.requestLong(request,"payGroupId");
	String frmCurrency = "#,###";
        boolean printZeroValue = true;
        String  sprintZeroValue  = PstSystemProperty.getValueByName("PAYROLL_PRINT_ZERO_VALUE");
                if(sprintZeroValue==null || sprintZeroValue.length()==0 || sprintZeroValue.equalsIgnoreCase("YES")
                || sprintZeroValue.equalsIgnoreCase("1")  || sprintZeroValue.equalsIgnoreCase("Not initialized") ){
    printZeroValue=true ;
        } else {
            printZeroValue=false;
        }
        
        PayPeriod objPeriod =new PayPeriod(); 
        // Period objPeriod =new Period();
        try{
            objPeriod= PstPayPeriod.fetchExc(periodId);
        } catch(Exception exc){
            
        }
        
        
%>
<%
	Vector listPaySalary = new Vector(1,1);
	if(iCommand == Command.LIST || iCommand==Command.EDIT || iCommand == Command.SAVE || iCommand == Command.ADD)
		{
			System.out.println("keyPeriod::::::::::::::::::::::::::::::::::"+keyPeriod);
			listPaySalary = PstSalaryLevelDetail.listPaySlip(PstSalaryLevelDetail.YES_TAKE,salaryLevel,PstPayComponent.TYPE_BENEFIT,paySlipId,PstPayComponent.YES_SHOW_PAYSLIP,keyPeriod, printZeroValue,payGroupId);			
			
		}
%>
<%
	Vector listPayDeduction = new Vector(1,1);
	if(iCommand == Command.LIST || iCommand==Command.EDIT || iCommand == Command.SAVE || iCommand == Command.ADD)
		{
			listPayDeduction = PstSalaryLevelDetail.listPaySlip(PstSalaryLevelDetail.YES_TAKE,salaryLevel,PstPayComponent.TYPE_DEDUCTION,paySlipId,PstPayComponent.YES_SHOW_PAYSLIP,keyPeriod, printZeroValue,payGroupId);			
		}
%>
<!-- JSP Block -->
<%!
	public String drawList(Vector objectClass,long employeeId,long periodId,long paySlipId)
	{
		
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("BENEFIT","50%");
		ctrlist.addHeader(" VALUE ","50%");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		String frmCurrency = "#,###";
		//int totIterasi = objectClass.size();
		for (int i = 0; i < objectClass.size(); i++) {
			Vector temp = (Vector)objectClass.get(i);
			Vector vectToken = new Vector(1,1);
			 PayComponent payComponent = (PayComponent)temp.get(0);
			 SalaryLevelDetail salaryLevelDetail = (SalaryLevelDetail)temp.get(1);
			 PaySlipComp paySlipComp= (PaySlipComp)temp.get(2);
			 // ambil value dari komponent-komponent
			 	rowx = new Vector();
				// kondisi ini digunakan jika ada  kasus yang nilai laporan daftar gajinya berbeda dengan nilai yang tertera di payslip
				/*if(salaryLevelDetail.getLevelCode().equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL4")) ||salaryLevelDetail.getLevelCode().equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL5")) || salaryLevelDetail.getLevelCode().equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL6")) || salaryLevelDetail.getLevelCode().equals(""+PstSystemProperty.getValueByName("SALARY_STAFF_TRAINING"))){
						if(payComponent.getCompCode().equals(""+PstSystemProperty.getValueByName("COMP_CODE1"))){
							String compName = PstPayComponent.getComponentName(PstSystemProperty.getValueByName("COMP_CODE1"));
							rowx.add(compName);
							rowx.add(""+Formater.formatNumber(Double.parseDouble(PstSystemProperty.getValueByName("GP_VALUE")), frmCurrency));
						}else if(payComponent.getCompCode().equals(""+PstSystemProperty.getValueByName("COMP_CODE2"))){
							String compName = PstPayComponent.getComponentName(PstSystemProperty.getValueByName("COMP_CODE2"));
							rowx.add(compName);
							rowx.add(""+Formater.formatNumber(Double.parseDouble(PstSystemProperty.getValueByName("TJ.TTP_VALUE")), frmCurrency));
						}
					
				}else */{
					rowx.add(payComponent.getCompName());
					rowx.add(""+Formater.formatNumber(paySlipComp.getCompValue(), frmCurrency));
				}
				
		lstData.add(rowx);
		}
		return ctrlist.draw();
	}
%>
<%!
	public String drawListDeduction(Vector objectClass,long employeeId,long periodId,long paySlipId)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("DEDUCTION","50%");
		ctrlist.addHeader(" VALUE ","50%");
		//ctrlist.addHeader(" ","0%");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		Vector rowx = new Vector(1,1);
		ctrlist.reset();
		int totalBenefit = 0;
		int totalDeduction = 0;
		String frmCurrency = "#,###";
		for (int i = 0; i < objectClass.size(); i++) {
			Vector temp = (Vector)objectClass.get(i);
			Vector vectToken = new Vector(1,1);
			 PayComponent payComponent = (PayComponent)temp.get(0);
			 SalaryLevelDetail salaryLevelDetail = (SalaryLevelDetail)temp.get(1);
			 PaySlipComp paySlipComp= (PaySlipComp)temp.get(2);
			 // ambil value dari komponent-komponent
			 	rowx = new Vector();
				rowx.add(payComponent.getCompName());
				rowx.add(""+Formater.formatNumber(paySlipComp.getCompValue(), frmCurrency));
				
		lstData.add(rowx);
		}
		return ctrlist.draw();
	}
%>
<%
	if(iCommand==Command.SAVE){
			String note = FRMQueryString.requestString(request,"note");
			System.out.println("notex : "+note);
			//pay.setNote(note);
			PstPaySlip.updateNote(employeeId,periodId,note);
		}
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - </title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<SCRIPT language=JavaScript>
function cmdSave(employeeId,salaryLevel,paySlipId,periodId){
	document.frm_prepare_data.action="pay-input-detail.jsp?employeeId=" + employeeId+ "&salaryLevel=" + salaryLevel+"&paySlipId=" + paySlipId+"&periodId=" + periodId;
	document.frm_prepare_data.command.value="<%=Command.SAVE%>";
	document.frm_prepare_data.submit();
}

function fnTrapKD(){
   if (event.keyCode == 13) {
		document.all.aSearch.focus();
		cmdSearch();
   }
}

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
function periodChange(paySlipId,employeeId,salaryLevel,periodId) {
	document.frm_prepare_data.command.value = "<%=Command.LIST%>";
	document.frm_prepare_data.action = "pay-input-detail.jsp?paySlipId=" + paySlipId+ "&employeeId=" + employeeId+"&salaryLevel=" + salaryLevel+"&periodId=" + periodId;
	document.frm_prepare_data.submit();
}
</SCRIPT>
<style type="text/css">
<!--
.style1 {font-size: 16px}
-->
</style>
<!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
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
                  Input Detail<!-- #EndEditable --> </strong></font> </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td class="tablecolor" style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top"> 
                              <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                        <form name="frm_prepare_data" method="post" action="">
										<input type="hidden" name="command" value="">
										<!--<input type="hidden" name="aksiCommand" value="<%//=aksiCommand%>">-->
										<input type="hidden" name="salaryLevel" value="<%=salaryLevel%>">
										<input type="hidden" name="employeeId " value="<%=employeeId%>">
										<input type="hidden" name="periodId " value="<%=periodId%>">
                                     <%if((listPaySalary!=null && listPaySalary.size()>0 ) || ( listPayDeduction!=null && listPayDeduction.size()>0 )){%>
                                      <table width="800" border="0" cellspacing="1" cellpadding="1">
                                     <%}else{%>
                                         <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                     <%}%>
									  <tr><td>Pay Slip Period &nbsp;&nbsp;&nbsp;
									  <%
									  	//value for period
										Vector perKey = new Vector();
										Vector perValue = new Vector();
										perKey.add(PstSalaryLevelDetail.PERIODE_WEEKLY+"");
										perKey.add(PstSalaryLevelDetail.PERIODE_MONTHLY+"");
										perKey.add(PstSalaryLevelDetail.PERIODE_YEAR+"");
										perValue.add(PstSalaryLevelDetail.periodKey[PstSalaryLevelDetail.PERIODE_WEEKLY]);
										perValue.add(PstSalaryLevelDetail.periodKey[PstSalaryLevelDetail.PERIODE_MONTHLY]);
										perValue.add(PstSalaryLevelDetail.periodKey[PstSalaryLevelDetail.PERIODE_YEAR]);
										out.println(ControlCombo.draw("paySlipPeriod",null,""+keyPeriod,perKey,perValue,"onchange=\"javascript:periodChange('"+paySlipId+"','"+employeeId+"','"+salaryLevel+"','"+periodId+"');\""));
                                                                                //update by satrya 2013-01-24
                                                                          %>
                                                                          <br><br>
                                                                          Pay Slip Group &nbsp;&nbsp;&nbsp;
                                                                          <%
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
        
                                                                                out.println(ControlCombo.draw("payGroupId",null,""+payGroupId,grkKey,grValue,"onchange=\"javascript:periodChange('"+paySlipId+"','"+employeeId+"','"+salaryLevel+"','"+periodId+"');\""));
									  %></td></tr>
                                        <%if((listPaySalary!=null && listPaySalary.size()>0 ) || ( listPayDeduction!=null && listPayDeduction.size()>0 )){%>
									  <tr><td>&nbsp;</td></tr>
                                        <tr> 
                                          <td class="contenttitle" valign="top" bgcolor="#006699" height="75"> 
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#FFFFFF">
                                              <tr> 
											  <%
											  	
												PayPeriod payPeriod = new PayPeriod();	
                                                                                                //Period period = new Period();	
											  	String periodName = "";
												Date endDatePeriod = new Date();
												try{
													payPeriod = PstPayPeriod.fetchExc(periodId);
													periodName = payPeriod.getPeriod();
													endDatePeriod = payPeriod.getEndDate();
												  }
												catch(Exception e){
												}
											  %>
                                                <td colspan="2" nowrap align="left"><font color="#006666"><b>PAYROOL 
                                                  SLIP : <%=periodName%> <% out.println(detailSlipYN==1?" / "+Formater.formatTimeLocale(endDatePeriod,"dd-MM-yyyy"):"");%></b></font></td>
                                                <td width="14%" align="right">&nbsp;</td>
												<% //ambil nama kota untuk slip sesuai yang diisi di General tentang nama perusahaan
													Vector vectCity = PstPayGeneral.list(0,0,"","");
													String city = "";
													if(vectCity!=null && vectCity.size() > 0){
														PayGeneral payCity = (PayGeneral) vectCity.get(0);
														city = payCity.getCity();
													}
													
												%>
                                                <td width="35%" align="right"><b><%=city%>, 
                                                <%
                                                   Date endDt= objPeriod.getEndDate()==null?new Date():objPeriod.getEndDate();

// end date
                            Calendar gre = Calendar.getInstance();
                            gre.setTime(endDt);
                            int day = gre.getActualMaximum(Calendar.DAY_OF_MONTH);
                            Date dtEnd = new Date(endDt.getYear(), endDt.getMonth(), day);
                                                   
                                                %>
                                                  <%=Formater.formatTimeLocale(dtEnd,"dd-MM-yyyy")%></b></td>
                                              </tr>
 <%
											  	// get the data for employee who have pay slip
												Vector empSlip = PstPaySlip.getEmpSlip(periodId,paySlipId);
												String employeeName = "";
												String employeeNum = "";
												int status=0;
												int paidStatus =0;
												String division = "";
												String department = "";
												String section = "";
												String position ="";
												double day_present = 0;
												double day_paid_lv = 0;
												double day_absent = 0;
												double day_unpaid_lv = 0;
												double day_late = 0;
												String note = "";
												Date commDate = new Date();
												//System.out.println("empSlip.size()"+empSlip.size());
												if((empSlip!=null) && empSlip.size() > 0){
													Employee employee = (Employee) empSlip.get(0);
													PaySlip paySlip= (PaySlip) empSlip.get(1);
													employeeName = employee.getFullName();
													employeeNum = employee.getEmployeeNum();
													status = paySlip.getStatus();
													paidStatus = paySlip.getPaidStatus();
													division = paySlip.getDivision();
													department = paySlip.getDepartment();
													section = paySlip.getSection();
													position = paySlip.getPosition();
													commDate = paySlip.getCommencDate();
													day_present = paySlip.getDayPresent();
													day_paid_lv = paySlip.getDayPaidLv();
													day_absent = paySlip.getDayAbsent();
													day_unpaid_lv = paySlip.getDayUnpaidLv();
													day_late = paySlip.getDayLate();
													note = paySlip.getNote();
													
												}
											  %>                                              
                                            <% if(detailSlipYN==1) { %>
                                              <tr> 
											 
                                                <td width="9%" nowrap align="right"><font color="#006666"> 
                                                  Status </font></td>
												  <% String statusApp = "";
												  	 String statusPaid ="";
												  	if(status==PstPaySlip.YES_APPROVE){
												  		statusApp = "APPROVED";	
													  }
													  else{
														statusApp = "DRAFT";	
													  }
													if(paidStatus==PstPaySlip.YES_PAID){
													 	statusPaid = "PAID";	
													 }
													 else{
													 	statusPaid = "NOT PAID";	
													 }%>
                                                <td width="27%"><font color="#006666">:  <%=statusApp%> 
                                                  / <%=statusPaid%></font></td>
                                                <td width="14%" align="right"><font color="#006666">Salary 
                                                  level</font></td>
                                                <td width="35%"><font color="#006666"><b><%=salaryLevel%></b></font></td>
                                              </tr>
                                              <%}%>
                                              <tr> 
                                                <td width="9%" nowrap align="right"><font color="#006666">Emp. Num / Name</font></td>
                                                <td width="27%"><font color="#006666">: 
                                                  <%=employeeNum%> / <%=employeeName%></font></td>
                                                <td width="9%" nowrap align="right"><font color="#006666"><% out.println((detailSlipYN==1? "Position / ":""));%> 
                                                  Section</font></td>
                                                <td width="27%"><font color="#006666"> 
                                                  : <% out.println((detailSlipYN==1 ? (position+ " / "):""));%><%=section %> </font></td>
                                              </tr>
                                              <tr> 
                                                <td width="9%" nowrap align="right"><font color="#006666"><% out.println((detailSlipYN==1? "Div. / ":""));%> 
                                                 Department</font></td>
                                                <td width="27%" nowrap><font color="#006666">: 
                                                  <% out.println((detailSlipYN==1? division+" / " : ""));%><%=department%></font></td>
                                                <td width="14%" align="right" nowrap><font color="#006666">Comm. 
                                                  Date </font></td>
                                                <td width="35%"><font color="#006666"> 
                                                  : <%=Formater.formatTimeLocale(commDate,"dd-MM-yyyy") %></font></td>
                                              </tr>
                                            </table>                                          </td>
                                        </tr>
                                        <tr> 
                                          <td bgcolor="#006666"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
                                              <tr> 
                                                <td width="50%">
													<table width="100%" height="23" border="0">
                                                                                                          <tr>
														<td width="100%" valign="top">
                                                                                                                    <%=drawList(listPaySalary,employeeId,periodId,paySlipId)%>                                                                                                                </td>
													  </tr>
													</table>												</td>
                                                                                                <td  valign="top" width="50%">
													<table width="100%" border="0">
													  <tr>
														<td width="100%" valign="top"><%=drawListDeduction(listPayDeduction,employeeId,periodId,paySlipId)%></td>
													  </tr>
													</table>												</td>
                                              </tr>
                                            </table>
											<table width="100%" border="0">
													  <tr>
													  <%
													  	// cari total benefit
														double totalBenefit = PstSalaryLevelDetail.getSumBenefitDoub(PstSalaryLevelDetail.YES_TAKE,salaryLevel,PstPayComponent.TYPE_BENEFIT,paySlipId,keyPeriod,payGroupId);
														System.out.println("totalBenefit..............."+totalBenefit);
														double totalDeduction = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE,salaryLevel,PstPayComponent.TYPE_DEDUCTION,paySlipId,keyPeriod,payGroupId);

													  %>
														<td width="25%" height="23" class="listheader"><b>TOTAL INCOME</b></td>
														<%
														// kondisi ini digunakan jika ada  kasus yang nilai laporan daftar gajinya berbeda dengan nilai yang tertera di payslip
															if(false && (salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL4")) || salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL5")) || salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL6")) || salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_STAFF_TRAINING")))){
															totalBenefit = Double.parseDouble(PstSystemProperty.getValueByName("GP_VALUE")) + Double.parseDouble(PstSystemProperty.getValueByName("TJ.TTP_VALUE")) ;
															totalDeduction = 0;
														%>
														<td width="25%" class="listheader"><% out.println(""+Formater.formatNumber(totalBenefit, frmCurrency)); %></td>
														<%
														}else{
														%>
														<td width="25%" class="listheader"><% out.println(""+Formater.formatNumber(totalBenefit, frmCurrency)); %></td>
														<%
														}
														%>
														<td width="25%" class="listheader"><b>TOTAL DEDUCTION</b></td>
														<td width="25%" class="listheader"><% out.println(""+Formater.formatNumber(totalDeduction, frmCurrency)); %></td>
													  </tr>
													  <tr>
													  <%
													  double totTakHomePay = totalBenefit - totalDeduction;
													  
													  %>
														<td width="25%" class="listheader"><b>TOTAL TAKE HOME PAY</b></td>
														<td width="25%"  colspan="3" class="listheader"><b><% out.println(""+Formater.formatNumber(totTakHomePay, frmCurrency)); %></b></td>
													  </tr>
													</table>                                          </td>
                                        </tr>
                                        <tr> 
                                          <td bgcolor="#006666">
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
                                              <tr> 
                                                <td valign="top" colspan="3">Note 
                                                  : 
                                                  <input type="text" name="note" size="96" value="<%=note%>">                                                </td>
                                              </tr>
                                              <tr> 
                                                <td valign="top" colspan="3"> 
												<% if(detailSlipYN==1 && (iCommand==Command.SAVE || iCommand == Command.LIST)){%>
                                                  Precency summary : Present=<%=day_present%> 
                                                  Paid Leave=<%=day_paid_lv%> Absent=<%=day_absent%> Unpaid 
                                                  Lv=<%=day_unpaid_lv%> Late=<%=day_late%> Total=<%=(day_present+day_paid_lv)-(day_absent+day_unpaid_lv+day_late)%> </td>
												 <% }%> 
                                              </tr>
                                              <tr> 
											  <%
											  	//get the data  bank of employee
												
												String where = PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_EMPLOYEE_ID]+"="+employeeId+
																" AND "+PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_STATUS_DATA]+"="+PstPayEmpLevel.CURRENT;
												Vector vectDataBank = PstPayEmpLevel.list(0,0,where,"");
												long bankId=0;
												String bankAccNr="";
												if((vectDataBank!=null) && (vectDataBank.size() > 0)){
													PayEmpLevel payEmpLevel = (PayEmpLevel) vectDataBank.get(0);
													bankId = payEmpLevel.getBankId();
													bankAccNr = payEmpLevel.getBankAccNr();
													
												}
												String bankName ="";
												String bankBranch="";
												if(bankId == Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_CASH")))){
													bankName =" CASH ";
													%>
														<td valign="top" width="46%"> 
														  <p>
															<%=bankName%><br></p>														</td>
													<%
												}
												else{
													PayBanks bank= new PayBanks();
													try{
														bank = PstPayBanks.fetchExc(bankId);
														bankName = bank.getBankName();
														bankBranch = bank.getBankBranch();
													  }
													catch(Exception e){
													}
													%>
													<td valign="top" width="46%"> 
                                                  <p>Transfer to : <br>
                                                    <%=bankName%> - <%=bankBranch%><br>
                                                    No. : <%=bankAccNr%><br>
                                                    A/n : <%=employeeName%>													</p>                                                </td>
												<%}%>
																				
                                                <td width="35%">Prepared by :<br>
                                                  <br>
                                                  <br>
                                                  <br>
                                                  <%
												  	out.println(""+PstSystemProperty.getValueByName("ACC_NAME"));
												  %><br>
                                                  ( HRD )<br>                                                </td>
                                                <td width="19%" nowrap>Received by : 
                                                  <br>
                                                  <br>
                                                  <br>
                                                  <br>
                                                  <%=employeeName%><br>
                                                  ( Employee )</td>
                                              </tr>
                                            </table>                                          </td>
                                        </tr>
                                        <tr> 
                                          <td>&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td>
                                            <!--<input type="submit" name="Submit" value="Save &amp; Approve ">-->
                                            <a href="javascript:cmdSave('<%=employeeId%>','<%=salaryLevel%>','<%=paySlipId%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data"></a>
											<img src="<%=approot%>/images/spacer.gif" width="6" height="1">
											 <a href="javascript:cmdSave('<%=employeeId%>','<%=salaryLevel%>','<%=paySlipId%>','<%=periodId%>')" class="command">Save</a> &nbsp;&nbsp; &nbsp;&nbsp;
                                           <!-- <input type="submit" name="Submit3" value="&lt; Back">
                                            <input type="submit" name="Submit4" value="NEXT &gt; ">
                                            <input type="submit" name="Submit5" value="&lt; VIEW LIST">-->                                          </td>
                                        </tr>
                                        
                                        <tr> 
                                          <td>&nbsp;</td>
                                        </tr>
                                    <%}else {%>
                                        <tr> 
                                          <td>&nbsp;</td>
                                        </tr>
                                        <tr bgcolor="#FFFF00">
							
                                                        <td><div align="center"><img src="<%=approot%>/images/warning.png" width="17" height="17%"/><span class="style1 style1">  <%=CtrlPayComponent.resultText[CtrlPayComponent.LANGUAGE_FOREIGN][4]%></span></div></td>
                                        </tr>
                                       
                                    
                                      <%}%>
                                      </table>
                                         <!-- end -->
                                     
                                    </form>
                                    <p>&nbsp;</p>
                                    <p>&nbsp;</p>
                                    <p>&nbsp;</p>
                                    <p>&nbsp;</p>
                                    <p>&nbsp;</p>
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
<script language="JavaScript">
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
        window.focus();
</script>
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
