 
<%@page import="com.dimata.harisma.entity.overtime.OvertimeSummary"%>
<%@page import="com.dimata.harisma.entity.leave.I_Leave"%>
<%@page import="com.dimata.harisma.entity.overtime.OvertimeDetail"%>
<%@page import="com.dimata.harisma.entity.overtime.PstOvertimeDetail"%>
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
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_PROCESS, AppObjInfo.OBJ_PAYROLL_PROCESS_PRINT);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%!
public void jspInit(){
    try{
    leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
    }catch (Exception e){
    System.out.println("Exception : " + e.getMessage());
    }
    }
%>
<%!
    I_Leave leaveConfig = null;
%>
<%
	int prevCommand = FRMQueryString.requestInt(request, "prev_command");
	int detailSlipYN = FRMQueryString.requestInt(request, "detail_slip");
	int iCommand = FRMQueryString.requestCommand(request);
	long periodId = FRMQueryString.requestLong(request,"periodId");
	int keyPeriod = FRMQueryString.requestInt(request,"paySlipPeriod");
         //update by satrya 2013-01-24
        long payGroupId = FRMQueryString.requestLong(request,"payGroupId");
        
        String frmCurrency = "#,###";
        CurrencyType currencyType = new CurrencyType();
        try{
            Vector currType = PstCurrencyType.list(0, 1, PstCurrencyType.fieldNames[PstCurrencyType.FLD_INCLUDE_INF_PROCESS]+"="+PstCurrencyType.YES_USED, "");
            if(currType!=null && currType.size()>0){
                currencyType =(CurrencyType)currType.get(0);
                frmCurrency = currencyType.getFormatCurrency();
            }
        }catch(Exception exc){
        
        }
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
            //  objPeriod= PstPeriod.fetchExc(periodId);
        } catch(Exception exc){
            System.out.println("Exception"+exc);
        }
        
        long SignatureOnOff =0;
        try {
               SignatureOnOff = Long.parseLong(PstSystemProperty.getValueByName("AKTIF_SIGNATURE"));
        } catch (Exception E) {
        }
%>

<%
            CtrlPaySlipComp ctrlPaySlipComp = new CtrlPaySlipComp(request);
            int start = FRMQueryString.requestInt(request, "start");
            int inclResign = FRMQueryString.requestInt(request, "INCLUDE_RESIGN");
            boolean bIncResign = (inclResign==1);
            long oidDivision = FRMQueryString.requestLong(request, "division");
            long oidDepartment = FRMQueryString.requestLong(request, "department");
            long oidSection = FRMQueryString.requestLong(request, "section");
            long oidPaySlipComp = FRMQueryString.requestLong(request, "section");
            String searchNrFrom = FRMQueryString.requestString(request, "searchNrFrom");
            String searchNrTo = FRMQueryString.requestString(request, "searchNrTo");
            String searchName = FRMQueryString.requestString(request, "searchName");
            int dataStatus = FRMQueryString.requestInt(request, "dataStatus");
            String codeComponenGeneral = FRMQueryString.requestString(request, "compCode");
            String compName = FRMQueryString.requestString(request, "compName");
            int aksiCommand = FRMQueryString.requestInt(request, "aksiCommand");
            long periodeId = FRMQueryString.requestLong(request, "periodId");
            int numKolom = FRMQueryString.requestInt(request, "numKolom");
            int statusSave = FRMQueryString.requestInt(request, "statusSave");
%>
<%
           // System.out.println("iCommand::::" + iCommand);
            int iErrCode = FRMMessage.ERR_NONE;
            String msgString = "";
            String msgStr = "";
            int recordToGet = 1000;
            int vectSize = 0;
            String orderClause = "";
            String whereClause = "";
            ControlLine ctrLine = new ControlLine();

            iErrCode = ctrlPaySlipComp.action(iCommand, oidPaySlipComp);
            FrmPaySlipComp frmPaySlipComp = ctrlPaySlipComp.getForm();
            PaySlipComp paySlipComp = ctrlPaySlipComp.getPaySlipComp();
            msgString = ctrlPaySlipComp.getMessage();

            Vector listEmpPaySlip = SessEmployee.listEmpPaySlip(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeId,-1,  bIncResign,0);
            
            //update by satrya 2013-01-29
           Vector sumOvertimeDailyDurIdxSalary = new Vector();
           Vector sumOvertimeDailyDurIdxDP = new Vector();
           Vector sumOvertimeDailyDurIdxTotal = new Vector();
            if(objPeriod.getStartDate()!=null && objPeriod.getEndDate()!=null){
            sumOvertimeDailyDurIdxSalary = PstOvertimeDetail.listSummaryOT(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, objPeriod.getStartDate(), objPeriod.getEndDate(), bIncResign, OvertimeDetail.PAID_BY_SALARY, -1); 
            sumOvertimeDailyDurIdxDP = PstOvertimeDetail.listSummaryOT(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, objPeriod.getStartDate(), objPeriod.getEndDate(), bIncResign, -1, OvertimeDetail.PAID_BY_DAY_OFF); 
            sumOvertimeDailyDurIdxTotal = PstOvertimeDetail.listSummaryOT(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, objPeriod.getStartDate(), objPeriod.getEndDate(), bIncResign, -1, -1); 
            }
            String s_employee_id = null;
            String s_payslip_id = null;
            String s_level_code = null;
            long oidEmployee = 0;

            if (iCommand == Command.PRINT) { // ngak dipakai ya ??? by Kartika
                // if (1==0 && iCommand == Command.PRINT) { // ngak dipakai ya ??? by Kartika
                if (aksiCommand == 0) {
                    System.out.println("print all");
                    String[] employee_id = null;
                    String[] paySlip_id = null;
                    String[] level_code = null;
                    String hostIpIdx = "";
                    Vector listDfGjPrintBenefit = new Vector(1, 1);
                    Vector listDfGjPrintDeduction = new Vector(1, 1);
                    try {
                        employee_id = request.getParameterValues("employeeId");
                        paySlip_id = request.getParameterValues("paySlipId");
                        level_code = request.getParameterValues("level_code");
                        hostIpIdx = request.getParameter("printeridx");// ip server
                        System.out.println("nilai hostIpIdx  " + hostIpIdx);
                    } catch (Exception e) {
                        System.out.println("Err : " + e.toString());
                    }
                    
                    Vector list = new Vector();
                    for (int i = 0; i < listEmpPaySlip.size(); i++) {
                        listDfGjPrintBenefit = new Vector();
                        try {
                            //oidEmployee = FRMQueryString.requestLong(request, "print"+i+""); // row yang dicheked
                            s_employee_id = String.valueOf(employee_id[i]);
                            s_payslip_id = String.valueOf(paySlip_id[i]);
                            s_level_code = String.valueOf(level_code[i]);
                            //print semua yang ditmapilkan
                            // cari payslip dari slip yang akan dicetak
                            //listDfGjPrintBenefit = PstSalaryLevelDetail.listPaySlip(PstSalaryLevelDetail.YES_TAKE,s_level_code,PstPayComponent.TYPE_BENEFIT,Long.parseLong(paySlip_id[i]),keyPeriod);
                            System.out.println("PaySlipId yang diprint " + Long.parseLong(paySlip_id[i]));
                            listDfGjPrintBenefit = PstSalaryLevelDetail.listPaySlipGlobal(PstSalaryLevelDetail.YES_TAKE, s_level_code, Long.parseLong(paySlip_id[i]), keyPeriod);
                            list.add(listDfGjPrintBenefit);
                          } catch (Exception e) {
                              System.out.println(e);
                        }

                    }
                    //listEmpPaySlip = SessEmployee.listEmpPaySlip(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeId, -1, bIncResign);
                } else {
                    System.out.println("print selected");
                    String[] employee_id = null;
                    String[] paySlip_id = null;
                    String[] level_code = null;
                    String hostIpIdx = "";
                    //Vector listDfGjPrintBenefit = new Vector(1, 1);
                    try {
                        employee_id = request.getParameterValues("employeeId");
                        paySlip_id = request.getParameterValues("paySlipId");
                        level_code = request.getParameterValues("level_code");
                        hostIpIdx = request.getParameter("printeridx");// ip server
                        System.out.println("nilai hostIpIdx  " + hostIpIdx);
                    } catch (Exception e) {
                        System.out.println("Err : " + e.toString());
                    }
                    //update by satrya 2013-01-14
                   // Vector list = new Vector();
                    Vector listEmpId = new Vector();
                   
                     for (int i = 0; i < listEmpPaySlip.size(); i++) {
                        //listDfGjPrintBenefit = new Vector();
                        try {
                            oidEmployee = FRMQueryString.requestLong(request, "print" + i + ""); // row yang dicheked
                            s_employee_id = String.valueOf(employee_id[i]);
                            s_payslip_id = String.valueOf(paySlip_id[i]);
                            s_level_code = String.valueOf(level_code[i]);
                        } catch (Exception e) {
                            System.out.println("Exception"+e);
                        }
                        //System.out.println("nilai statusSave"+statusSave);
                        //update by satrya 2013-01-14
                       if (oidEmployee != 0) {
                                    listEmpId.add(oidEmployee); 
                       } 
                    }
                   if(listEmpId!=null && listEmpId.size()>0){
                    listEmpPaySlip = SessEmployee.listEmpPaySlipByEmployeeId(listEmpId, periodeId, -1, bIncResign);
                   }
                }
                //hide by satrya 2013-01-14
                //update by satrya 2013-01-14
                 ///listEmpPaySlip = SessEmployee.listEmpPaySlip(oidDepartment, oidDivision, oidSection, searchNrFrom, searchNrTo, searchName, periodeId, -1, bIncResign);
            }
%>



<!-- JSP Block -->
<%!
	public String drawList(Vector objectClass,long employeeId,long periodId,long paySlipId,CurrencyType currencyType) 
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
                String mataUang="Rp";
                if(currencyType!=null && currencyType.getCode()!=null && currencyType.getFormatCurrency()!=null){
                    frmCurrency = currencyType.getFormatCurrency();
                    mataUang = currencyType.getCode();
                }
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
							rowx.add(""+Formater.formatNumberVer1(Double.parseDouble(PstSystemProperty.getValueByName("GP_VALUE")), frmCurrency));
						}else if(payComponent.getCompCode().equals(""+PstSystemProperty.getValueByName("COMP_CODE2"))){
							String compName = PstPayComponent.getComponentName(PstSystemProperty.getValueByName("COMP_CODE2"));
							rowx.add(compName);
							rowx.add(""+Formater.formatNumberVer1(Double.parseDouble(PstSystemProperty.getValueByName("TJ.TTP_VALUE")), frmCurrency));
						}
					
				}else */{
					rowx.add(payComponent.getCompName());
					rowx.add(mataUang +" "+Formater.formatNumberVer1(paySlipComp.getCompValue(), frmCurrency));
				}
				
		lstData.add(rowx);
		}
		return ctrlist.draw();
	}
%>
<%!
	public String drawListDeduction(Vector objectClass,long employeeId,long periodId,long paySlipId,String codeTax_count,CurrencyType currencyType)
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
		//String frmCurrency = "#,###";
                String frmCurrency = "#,###";
                String mataUang="Rp";
                if(currencyType!=null && currencyType.getCode()!=null && currencyType.getFormatCurrency()!=null){
                    frmCurrency = currencyType.getFormatCurrency();
                    mataUang = currencyType.getCode();
                }
		for (int i = 0; i < objectClass.size(); i++) {
			Vector temp = (Vector)objectClass.get(i);
			Vector vectToken = new Vector(1,1);
			 PayComponent payComponent = (PayComponent)temp.get(0);
			 SalaryLevelDetail salaryLevelDetail = (SalaryLevelDetail)temp.get(1);
			 PaySlipComp paySlipComp= (PaySlipComp)temp.get(2);
			 // ambil value dari komponent-komponent
			 	rowx = new Vector();
                                
				//rowx.add(payComponent.getCompName());
                              //update by satrya 2013-02-21
                                 if(codeTax_count!=null && codeTax_count.length()>0 && codeTax_count.equalsIgnoreCase(payComponent.getCompCode())){
                                     //jika componennya mengandung pph yg di hitung konsultan maka tidak di tampilkan
                                 }else{
                                        rowx.add(payComponent.getCompName());
                                        rowx.add(mataUang+" "+Formater.formatNumberVer1(paySlipComp.getCompValue(), frmCurrency));//"" + Formater.formatNumberVer1(paySlipComp.getCompValue(), frmCurrency));
                                 }
				//rowx.add(""+Formater.formatNumberVer1(paySlipComp.getCompValue(), frmCurrency));
				
		lstData.add(rowx);
		}
		return ctrlist.draw();
	}
%>
<!-- End of JSP Block -->
<html>
 
<head>
 
<title>HARISMA - Email Pay Slip </title>
 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
  
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
body {
	background-color: #FFFFFF;
}
.style1 {
	color: #000000;
	font-weight: bold;
}
.style2 {color: #000000}
-->
</style></head>
<body text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td > <a href="../../home.jsp">Back To Menu</a> | <a href="pay-printing.jsp"> Payslip Selection</a>
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                          <tr> 
                            <td valign="top"><hr>
                              <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                <tr> 
                                  <td valign="top">                                        										 	
                                      <%
                                      int MODEL_PAYSLIP = PaySlip.MODEL_UP_DOWN;
                                      int payslipOT = 0;
                                      try{
                                        String strSlip=PstSystemProperty.getValueByName("MODEL PAYSLIP");
                                        if(strSlip!=null){
                                            MODEL_PAYSLIP = Integer.parseInt(strSlip);
                                        }
                                        
                                         String spayslipOT=null;
       try{
        spayslipOT = PstSystemProperty.getValueByName("PAYROLL_PAY_SLIP_CONFIG_OVERTIME_LOCATION");
        if(spayslipOT!=null){
              payslipOT = Integer.parseInt(spayslipOT);
        }
       }catch(Exception exc){
           System.out.println("Exception PAYROLL_PAY_SLIP_CONFIG_OVERTIME_LOCATION not be set");
           payslipOT=0;
       }
        I_Leave leaveConfig = null;
        try {
            leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
        } catch (Exception e) {
            System.out.println("Exception LEAVE_CONFIG: " + e.getMessage());
        }
                                      } catch(Exception exc){
                                          
                                      }
                                        //update by satrya 2013-02-21
                                        String codeTax_count="";
                                        try{
                                            codeTax_count=PstSystemProperty.getValueByName("PAY_TAX_CON");
                                      } catch(Exception exc){
                                          codeTax_count="TAX_CON";
                                      }
                                        PayPeriod payPeriod = new PayPeriod();
                                        //  Period period = new Period();
                                        String periodName = "";
                                        Date endDatePeriod = new Date();
                                       // update by satrya 2013-01-29
                                        Date selectedDateFrom =null;
                                        Date selectedDateTo = null;
                                        try{
                                                payPeriod = PstPayPeriod.fetchExc(periodId);
                                                // period = PstPeriod.fetchExc(periodId);
                                                periodName = payPeriod.getPeriod();
                                                endDatePeriod = payPeriod.getEndDate();
                                                //update by satrya 2013-01-29
                                                selectedDateFrom = payPeriod.getStartDate();
                                                selectedDateTo = payPeriod.getEndDate();
                                          }
                                        catch(Exception e){
                                        }
                    PayGeneral payGen = new PayGeneral();
                    Employee employee = new Employee();
                    PaySlip paySlip   = new PaySlip();
                    //update by satrya 2013-01-29
                    //OvertimeDetail overtimeDetail = new OvertimeDetail();
                    for (int ip = 0; ip < listEmpPaySlip.size(); ip++) {
                        Vector vlst = (Vector) listEmpPaySlip.get(ip);
                        Employee objEmployee = (Employee) vlst.get(0);
                        PayEmpLevel objPayEmpLevel = (PayEmpLevel) vlst.get(1);
                        PaySlip objPaySlip = (PaySlip) vlst.get(2);

                        long employeeId = objEmployee.getOID();
                        String salaryLevel =objPayEmpLevel.getLevelCode();
                        long paySlipId = objPaySlip.getOID();
                            // update 2015-01-12 by Hendra McHen 
                            Vector listPaySalary = PstSalaryLevelDetail.listPaySlipNew(PstSalaryLevelDetail.YES_TAKE,salaryLevel,PstPayComponent.TYPE_BENEFIT,paySlipId,keyPeriod, printZeroValue,payGroupId);
                            Vector listPayDeduction = PstSalaryLevelDetail.listPaySlipNew(PstSalaryLevelDetail.YES_TAKE,salaryLevel,PstPayComponent.TYPE_DEDUCTION,paySlipId,keyPeriod, printZeroValue,payGroupId);

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
                    //update by satrya 2013-01-20
                    String bankAcc="";
                    String bankNameX="";
                    //update by satrya 2013-05-06
                    double ovIdAdjustment=0;
                    String privNote="";
                    String otDuration="";
                   // double otIdx=0;
                    double otPaidBySalary=0;
                    double otPaidByDP=0;
                    double otPaidByTotal=0;
                    //System.out.println("empSlip.size()"+empSlip.size());
                    if((empSlip!=null) && empSlip.size() > 0){
                        if(sumOvertimeDailyDurIdxSalary!=null && sumOvertimeDailyDurIdxSalary.size()>0 || sumOvertimeDailyDurIdxDP!=null && sumOvertimeDailyDurIdxDP.size()>0){
                            if(sumOvertimeDailyDurIdxSalary!=null && sumOvertimeDailyDurIdxSalary.size()>0){
                                for(int idxSal=0;idxSal< sumOvertimeDailyDurIdxSalary.size();idxSal++){
                                    OvertimeSummary  overtimeDetailSummary = (OvertimeSummary)sumOvertimeDailyDurIdxSalary.get(idxSal);
                                    if(overtimeDetailSummary.getEmployeeId()==employeeId){
                                    otPaidBySalary = overtimeDetailSummary.getOtPaidSummarySallary();
                                    sumOvertimeDailyDurIdxSalary.remove(idxSal);
                                    idxSal = idxSal-1;
                                    break;
                                    }
                                }
                            }
                         if(sumOvertimeDailyDurIdxDP!=null && sumOvertimeDailyDurIdxDP.size()>0){
                                for(int idxDP=0;idxDP< sumOvertimeDailyDurIdxDP.size();idxDP++){
                                    OvertimeSummary  overtimeDetailSummary = (OvertimeSummary)sumOvertimeDailyDurIdxDP.get(idxDP);
                                    if(overtimeDetailSummary.getEmployeeId()==employeeId){
                                    otPaidByDP = overtimeDetailSummary.getOtPaidSummaryDp();
                                    sumOvertimeDailyDurIdxDP.remove(idxDP);
                                    idxDP = idxDP-1;
                                    break;
                                    }
                                }
                            }
                            if(sumOvertimeDailyDurIdxTotal!=null && sumOvertimeDailyDurIdxTotal.size()>0){
                                for(int idxOtTot=0;idxOtTot< sumOvertimeDailyDurIdxTotal.size();idxOtTot++){
                                    OvertimeSummary  overtimeDetailSummary = (OvertimeSummary)sumOvertimeDailyDurIdxTotal.get(idxOtTot);
                                    if(overtimeDetailSummary.getEmployeeId()==employeeId){
                                    otPaidByTotal = overtimeDetailSummary.getOtPaidSummarySallaryAndDP();
                                    otDuration = Formater.formatWorkDayHoursMinutes((float)overtimeDetailSummary.getOtDuration(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave());
                                    sumOvertimeDailyDurIdxTotal.remove(idxOtTot);
                                    idxOtTot = idxOtTot-1;
                                    break;
                                    }
                                }
                            }
                            employee = (Employee) empSlip.get(0);
                            paySlip= (PaySlip) empSlip.get(1);
                        }else{
                                employee = (Employee) empSlip.get(0);
                                paySlip= (PaySlip) empSlip.get(1);
                        }
                    } else {
                        employee  = new Employee();
                        paySlip = new PaySlip();
                    }
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
                            ovIdAdjustment = objPaySlip!=null ?objPaySlip.getOvIdxAdj():0;
                            privNote=objPaySlip!=null ?objPaySlip.getPrivateNote():"";
                            
                            //note = paySlip.getNote()+"<br> Overtime Duration: "+Formater.formatNumberVer1(overtimeDetail.getTotDuration(), "##.##")+"<br> Overtime Idx: "+Formater.formatNumberVer1(overtimeDetail.getTotIdx(), "##.##");
                            if(payslipOT==0){
                                note = paySlip.getNote();
                            }else{
                                
                                note = paySlip.getNote()+"<br> Overtime Duration: "+otDuration+"<br> Overtime Idx Total: "+otPaidByTotal + " <br> Overtime Idx By Paid Salary: "+otPaidBySalary 
                                        + " <br> Overtime Idx By Paid Day OFF: "+otPaidByDP  + " <br> Overtime Idx Adjusment: "+ovIdAdjustment + (privNote!=null && privNote.length()>0 ? " <br> Overtime Idx Adjusment: "+privNote : "");
                            }
                            //update by satrya 2013-01-20
                            bankAcc=objPayEmpLevel.getBankAccNr();
                            bankNameX=objPaySlip.getBankName();
                          if(employee!=null && ( employee.getCompanyId()!= payGen.getOID()  || payGen.getOID()==0 )&& employee.getCompanyId()!=0){
                                    String whereGen = ""+PstPayGeneral.fieldNames[PstPayGeneral.FLD_GEN_ID]+"="+employee.getCompanyId();
                                    Vector vectCity = PstPayGeneral.list(0,0,whereGen,"");                                                           
                                    if(vectCity!=null && vectCity.size() > 0){
                                            payGen = (PayGeneral) vectCity.get(0);                                                                    
                                    }
                             }

                                                                               
                            
                             switch(MODEL_PAYSLIP){
                                     case PaySlip.MODEL_LEFT_RIGHT:
                                         try{
                          %>                                       
                                      <table width="800" border="0" cellspacing="1" cellpadding="1">
									  <tr><td>&nbsp;</td></tr>
                                        <tr> 
                                          <td class="contenttitle" valign="top" bgcolor="#FFFFFF"	 height="75"> 
                                            <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#FFFFFF">
                                              
                                               
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
                                          <td > 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
                                              <tr> 
                                                <td width="50%">
                                                    <table width="100%" height="23" border="0">
                                                      <tr>
                                                            <td width="100%" valign="top"><%=drawList(listPaySalary,employeeId,periodId,paySlipId,currencyType)%></td>
                                                      </tr>
                                                    </table>												</td>
                                                <td  valign="top" width="50%">
                                                    <table width="100%" border="0">
                                                      <tr>
                                                            <td width="100%" valign="top"><%=drawListDeduction(listPayDeduction,employeeId,periodId,paySlipId,codeTax_count,currencyType)%></td>
                                                      </tr>
                                                    </table>												</td>
                                              </tr>
                                            </table>
                            <table width="100%" border="0">
                                              <tr>
                                              <%
                                                    // cari total benefit
                                                    double totalBenefit = PstSalaryLevelDetail.getSumBenefitDoub(PstSalaryLevelDetail.YES_TAKE,salaryLevel,PstPayComponent.TYPE_BENEFIT,paySlipId,keyPeriod,payGroupId);
                                                    //System.out.println("totalBenefit..............."+totalBenefit);
                                                    double totalDeduction = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE,salaryLevel,PstPayComponent.TYPE_DEDUCTION,paySlipId,keyPeriod,payGroupId);

                                              %>
                                                    <td width="25%" height="23" bgcolor="#FFFFFF" ><span class="style1">TOTAL INCOME</span></td>
                                                    <%
                                                    // kondisi ini digunakan jika ada  kasus yang nilai laporan daftar gajinya berbeda dengan nilai yang tertera di payslip
                                                            if(false && (salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL4")) || salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL5")) || salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL6")) || salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_STAFF_TRAINING")))){
                                                            totalBenefit = Double.parseDouble(PstSystemProperty.getValueByName("GP_VALUE")) + Double.parseDouble(PstSystemProperty.getValueByName("TJ.TTP_VALUE")) ;
                                                            totalDeduction = 0;
                                                    %>
                                                <td width="25%" bgcolor="#FFFFFF"><% out.println(""+Formater.formatNumberVer1(totalBenefit, frmCurrency)); %></td>
                                                    <%
                                                    }else{
                                                    %>
                                                <td width="25%" bgcolor="#FFFFFF"><% out.println(""+Formater.formatNumberVer1(totalBenefit, frmCurrency)); %></td>
                                                    <%
                                                    }
                                                    %>
                                                    <td width="25%" bgcolor="#FFFFFF"><span class="style2"><b>TOTAL DEDUCTION</b></span></td>
                                                <td width="25%" bgcolor="#FFFFFF"><% out.println(""+Formater.formatNumberVer1(totalDeduction, frmCurrency)); %></td>
                                              </tr>
                                              <tr>
                                              <%
                                              double totTakHomePay = totalBenefit - totalDeduction;

                                              %>
                                                    <td width="25%" bgcolor="#FFFFFF"><span class="style2"><b>TOTAL TAKE HOME PAY</b></span></td>
                                                    <td width="25%"  colspan="4" bgcolor="#FFFFFF"><span class="style2"><b>
                                                <% out.println(""+Formater.formatNumberVer1(totTakHomePay, frmCurrency)); %>
                                                </b></span></td>
                                              </tr>
                                              <%if(payslipOT==0){%>
                                               <table width="100%">
                                                    <tr>
                                                        <%String ov= otDuration +" / "+otPaidByTotal+" / "+otPaidBySalary+" / "+otPaidByDP+" / "+ovIdAdjustment+(privNote.length()>0?" / "+privNote:"");%>
                                                        <td width="25%" bgcolor="#FFFFFF"><span class="style2"><b>Ov.Hours / ov.Idx / ov.Paid Salary / ov.Paid DP / ov.Idx adjusment <%=(privNote.length()>0?" / "+"note":"")%></b></span></td>
                                                        <td width="25%" bgcolor="#FFFFFF"><%=ov%></td>
                                                    
                                               <!-- <td colspan="2" scope="col"><%//=ov%></td>-->
                                              </tr>
                                               </table>
                                              <!-- priska 20150808 -->
                                             <table width="100%">
                                                    <tr>
                                                        
                                                        <td width="25%" bgcolor="#FFFFFF"><span class="style2"><b> - </b></span></td>
                                                        
                                              </tr>
                                               </table>
                                             
                                              <%}%>
                                            </table>                                          </td>
                                        </tr>
                                        <tr> 
                                          <td >
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
                                              <tr> 
                                                <td valign="top" colspan="3">Note 
                                                  : <%=note%>
                                                </td>
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
                                            </table></td>
                                        </tr>
                                      </table>
                                      <%  } catch(Exception exc){}
                                         break;
					case PaySlip.MODEL_UP_DOWN:
                                           try{
                                        %>
<table width="800" border="0" cellspacing="1" cellpadding="1">									                                                                                           
                                        <tr> 
                                          <td > 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
                                              <tr> 
                                                <td width="50%" valign="top">
                                                    <table width="100%" height="23" border="0">
                                                      <tr>
                                                        <td valign="top" ><b>
                                                          <div align="center"><%=payGen.getCompanyName() %><br>
                                                            <%=payGen.getCompAddress() %><br>
                                                            <%=payGen.getCity() %> , 
                                                            <%=payGen.getZipCode() %></br>
                                                        Telp. <%=payGen.getTel() %> Fax. <%=payGen.getFax() %>
                                                          </div>
                                                        <hr></td>
                                                      </tr>
                                                      <tr>
                                                        <td valign="top"><table width="100%" cellspacing="1" cellpadding="1">
                                                          <tr>
                                                            <td width="24%">Month</td>
                                                            <td width="3%">:</td>
                                                            <td width="73%"><font color="#006666"><b><%=periodName%>
                                                  <% out.println(detailSlipYN==1?" / "+Formater.formatTimeLocale(endDatePeriod,"dd-MM-yyyy"):"");%>
                                                            </b></font></td>
                                                          </tr>
                                                          <tr>
                                                            <td>Empl. Name </td>
                                                            <td>:</td>
                                                            <td><font color="#006666"><%=employeeName%></font></td>
                                                          </tr>
                                                          <tr>
                                                            <td>Empl. Number </td>
                                                            <td>&nbsp;</td>
                                                            <td><font color="#006666"><%=employeeNum%></font></td>
                                                          </tr>
                                                          <tr>
                                                            <td><% out.println((detailSlipYN==1? "Div. / ":""));%> 
                                                 Department </td>
                                                            <td>:</td>
                                                            <td><% out.println((detailSlipYN==1? division+" / " : ""));%><%=department%></td>
                                                          </tr>
                                                          <tr>
                                                            <td>Comm. date </td>
                                                            <td>:</td>
                                                            <td><font color="#006666"><%=Formater.formatTimeLocale(commDate,"dd-MM-yyyy") %></font></td>
                                                          </tr>
                                                          <tr>
                                                            <td>Bank Account </td>
                                                            <td>:</td>
                                                            <td><%=bankAcc%></td>
                                                          </tr>
                                                          <tr>
                                                            <td>Bank</td>
                                                            <td>:</td>
                                                            <td><%=bankNameX%></td>
                                                          </tr>
                                                          <tr>
                                                            <td>Printed</td>
                                                            <td>:</td>
                                                            <td><%
                                                   Date endDt= objPeriod.getEndDate()==null? new Date() : objPeriod.getEndDate();
                                                    // end date
                                                Calendar gre = Calendar.getInstance();
                                                gre.setTime(endDt);
                                                int day = gre.getActualMaximum ( Calendar.DAY_OF_MONTH);
                                                Date dtEnd = (objPeriod.getPaySlipDate()!=null) ? objPeriod.getPaySlipDate() : (new Date(endDt.getYear(), endDt.getMonth(), day));
                                                
                                                %></td>
                                                          </tr>
                                                        </table></td>
                                                      </tr>
                                                      <tr>
                                                            <td width="100%" valign="top">Note : <%=note%></td>
                                                      </tr>
                                                </table>												</td>
                                                <td  valign="top" width="50%">
                                                    <table width="100%" border="0">
                                                      <tr>
                                                            <td width="100%" valign="top"><%=drawList(listPaySalary,employeeId,periodId,paySlipId,currencyType)%></td>
                                                      </tr>													
                                                      <tr>
                                                            <td width="100%" valign="top"><%=drawListDeduction(listPayDeduction,employeeId,periodId,paySlipId,codeTax_count,currencyType)%></td>
                                                      </tr>
                                                    </table>												</td>
                                              </tr>
                                            </table>
                            <table width="100%" border="0">
                                              <tr>
                                              <%
                                                    // cari total benefit
                                                    double totalBenefit = PstSalaryLevelDetail.getSumBenefitDoub(PstSalaryLevelDetail.YES_TAKE,salaryLevel,PstPayComponent.TYPE_BENEFIT,paySlipId,keyPeriod,payGroupId);
                                                    //System.out.println("totalBenefit..............."+totalBenefit);
                                                    double totalDeduction = PstSalaryLevelDetail.getSumBenefit(PstSalaryLevelDetail.YES_TAKE,salaryLevel,PstPayComponent.TYPE_DEDUCTION,paySlipId,keyPeriod,payGroupId);

                                              %>
                                                    <td width="25%" height="23" bgcolor="#FFFFFF" >&nbsp;</td>
                                                    <%
                                                    // kondisi ini digunakan jika ada  kasus yang nilai laporan daftar gajinya berbeda dengan nilai yang tertera di payslip
                                                            if(false && (salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL4")) || salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL5")) || salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_LEVEL6")) || salaryLevel.equals(""+PstSystemProperty.getValueByName("SALARY_STAFF_TRAINING")))){
                                                            totalBenefit = Double.parseDouble(PstSystemProperty.getValueByName("GP_VALUE")) + Double.parseDouble(PstSystemProperty.getValueByName("TJ.TTP_VALUE")) ;
                                                            totalDeduction = 0;
                                                    %>
                                                    <td width="25%" bgcolor="#FFFFFF">&nbsp;</td>
                                                    <%
                                                    }else{
                                                    %>
                                                    <td width="25%" bgcolor="#FFFFFF">&nbsp;</td>
                                                    <%
                                                    }
                                                    %>
                                                    <td width="25%" bgcolor="#FFFFFF"><span class="style2"><b>TOTAL TAKE HOME PAY</b></span></td>
                                                    <td width="25%" bgcolor="#FFFFFF"><b>
                                                      <% double totTakHomePay = totalBenefit - totalDeduction; 
                                                        out.println(""+Formater.formatNumberVer1(totTakHomePay, frmCurrency)); %> 
                                                </b></td>
                                              </tr>
                                               <%if(payslipOT==0){%>
                                               <table width="100%">
                                                    <tr>
                                                        <%String ov= otDuration +" / "+otPaidByTotal+" / "+otPaidBySalary+" / "+otPaidByDP+" / "+ovIdAdjustment+(privNote.length()>0?" / "+privNote:"");%>
                                                        <td width="25%" bgcolor="#FFFFFF"><span class="style2"><b>Ov.Hours / ov.Idx / ov.Paid Salary / ov.Paid DP / ov.Idx adjusment <%=(privNote.length()>0?" / "+"note":"")%></b></span></td>
                                                        <td width="25%" bgcolor="#FFFFFF"><%=ov%></td>
                                                    
                                               <!-- <td colspan="2" scope="col"><%//=ov%></td>-->
                                              </tr>
                                               </table>
                                              <!-- priska menambahkan 201508011-->
                                              <%
                                             
                                              if (SignatureOnOff>0){
                                              
                                                Date thisDate =  new Date();
                                                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                                                String DateNew = formatDate.format(thisDate);
                                              
                                                Vector empWithLevel = PstEmployee.listEmployeeByLevel(objEmployee);
                                                Vector emplevel = (Vector) empWithLevel.get(0);

                                                Company company = new Company();
                                                try{
                                                    company = PstCompany.fetchExc(objEmployee.getCompanyId());
                                                }catch (Exception e){

                                                }
                                                
                                                Employee employeeAppr = new Employee();
                                                Level level = new Level();
                                                employeeAppr = (Employee) emplevel.get(0);
                                                level = (Level) emplevel.get(1);

                                               
                                              %>
                                              <table width="100%">
                                                    <tr>
                                                        <td width="25%" bgcolor="#FFFFFF"><span class="style2">.</span></td>
                                                        <td width="25%" bgcolor="#FFFFFF"></td>
                                                    </tr>
                                                    <tr>
                                                        <td width="25%" bgcolor="#FFFFFF"><span class="style2"><b></b></span></td>
                                                        <td width="25%" bgcolor="#FFFFFF">_______________, <%=DateNew%></td>
                                                    </tr>
                                                    <tr>
                                                        <td width="25%" bgcolor="#FFFFFF"><span class="style2"><b></b></span></td>
                                                        <td width="25%" bgcolor="#FFFFFF"><%=company.getCompany()%></td>
                                                    </tr>
                                                    <tr>
                                                        <td width="25%" bgcolor="#FFFFFF"><span class="style2"><b></b></span></td>
                                                        <td width="25%" bgcolor="#FFFFFF"><%=level.getLevel()%>,</td>
                                                    </tr>
                                                    <tr>
                                                        <td width="25%" bgcolor="#FFFFFF"><span class="style2">.</span></td>
                                                        <td width="25%" bgcolor="#FFFFFF"></td>
                                                    </tr>
                                                    <tr>
                                                        <td width="25%" bgcolor="#FFFFFF"><span class="style2">.</span></td>
                                                        <td width="25%" bgcolor="#FFFFFF"></td>
                                                    </tr>
                                                    <tr>
                                                        <td width="25%" bgcolor="#FFFFFF"><span class="style2">.</span></td>
                                                        <td width="25%" bgcolor="#FFFFFF"></td>
                                                    </tr>
                                                    <tr>
                                                        <td width="25%" bgcolor="#FFFFFF"><span class="style2"><b></b></span></td>
                                                        <td width="25%" bgcolor="#FFFFFF"><u><%=employee.getFullName()%></u></td>
                                                    </tr>
                                                    <tr>
                                                        <td width="25%" bgcolor="#FFFFFF"><span class="style2"><b></b></span></td>
                                                        <td width="25%" bgcolor="#FFFFFF"><%=employee.getEmployeeNum()%></td>
                                                    </tr>
                                               </table>
                                              <%}%>
                                            </table>                                         
                                        </tr>                                        
                                      </table>                                                                                
                                        <%
                                      }
                                               } catch (Exception exc){                                            
                                        }
                                        break;
                                      
                                      
                                      default :
                                      %>
                                       
                                      <% 
                             
                                    } %>
                                      
                                      <hr>
					<% } %></td>
                                </tr>
                              </table>       </td>
                          </tr>
                        </table>                      </td>
                    </tr>
                    <tr> 
                      <td>&nbsp; </td>
                    </tr>
                  </table>                </td>
              </tr>
            </table>          </td>
        </tr>
      </table>    </td>
  </tr>
</table>
</body>
 
<script language="JavaScript">
	var oBody = document.body;
        oBody
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>

</html>
