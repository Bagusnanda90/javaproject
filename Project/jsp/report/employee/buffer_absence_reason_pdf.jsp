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
<% int  appObjCode = 1; %>
<%@ include file = "../../main/checkuser.jsp" %>


<html>
<head>
<title>Dimata - ProChain POS</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head> 

<body bgcolor="#FFFFFF" text="#000000">
Loading ... 
<script language="JavaScript">
	window.focus();
</script>
</body>
</html>

<!-- JSP Block -->
<%
/**
 * Vector untuk keperluan PDF
 */
/*Vector headerPdf = new Vector(1,1);
Vector bodyPdf = new Vector(1,1);
Vector footerPdf = new Vector(1,1);
Vector listTableHeaderPdf = new Vector(1,1);
Vector pdfContent = new Vector(1,1);

SrcAccPayable srcAccPayable = new SrcAccPayable();
FrmSrcAccPayable frmSrcAccPayable = new FrmSrcAccPayable(request, srcAccPayable);
frmSrcAccPayable.requestEntityObject(srcAccPayable);

try{ 
	srcAccPayable = (SrcAccPayable)session.getValue(SessAccPayable.SESS_ACC_PAYABLE); 
	if (srcAccPayable == null)
		srcAccPayable = new SrcAccPayable();			
}catch(Exception e){ 
	srcAccPayable = new SrcAccPayable();
}

session.putValue(SessAccPayable.SESS_ACC_PAYABLE, srcAccPayable);*/

/** Untuk mendapatkan besarnya daily rate dalam satuan Rp */
/**
 * Yang dibagi dengan variabel dailyRate yaitu: 
 * dr pada function javascript:calculate(), totalPayment, apPayment dan amount
 * Ini berfungsi untuk mengasilkan amount yang sesuai dengan search key yang diminta (Rp atau USD)
 */
/*String whereClause = PstDailyRate.fieldNames[PstDailyRate.FLD_CURRENCY_TYPE_ID]+" = "+srcAccPayable.getCurrencyId();
String orderClause = PstDailyRate.fieldNames[PstDailyRate.FLD_ROSTER_DATE]+" DESC";
Vector listDailyRate = PstDailyRate.list(0, 0, whereClause, orderClause);
DailyRate dr = (DailyRate)listDailyRate.get(0);
double dailyRate = dr	.getSellingRate();

whereClause = PstCurrencyType.fieldNames[PstCurrencyType.FLD_CURRENCY_TYPE_ID]+" = "+srcAccPayable.getCurrencyId();
Vector listCurrencyType = PstCurrencyType.list(0, 0, whereClause, "");
CurrencyType currencyType = (CurrencyType)listCurrencyType.get(0);

Vector records = SessAccPayable.getListAP(srcAccPayable, 0, 0);
Vector listAp = new Vector(1,1);
double amount = 0;
double tax = 0;
double retur = 0;
double apPayment = 0;
double apSaldo = 0;
double totalAmount = 0;
double totalTax = 0;
double totalRetur = 0;
double totalPayment = 0;
double totalSaldo = 0;

//for(int j=0; j<7; j++) { //hanya untuk tes report!
for(int i=0; i<records.size(); i++) {
	Vector temp = (Vector)records.get(i);
	amount = Double.parseDouble((String)temp.get(5));
	tax = Double.parseDouble((String)temp.get(6));
	retur = SessMatReturn.getTotalReturnByReceive(Long.parseLong((String)temp.get(0)));
	apPayment = (SessAccPayable.getTotalAPPayment(srcAccPayable, Long.parseLong((String)temp.get(0))))/dailyRate;
	apSaldo = (amount + tax) - apPayment -retur;
	
	Vector temp2 = new Vector(1,1);
	temp2.add(String.valueOf(i+1));
	temp2.add((String)temp.get(1));
	temp2.add((String)temp.get(2));
	temp2.add((String)temp.get(3));
	temp2.add(Formater.formatDate((Date)temp.get(4), "dd-MM-yyyy"));
	temp2.add(String.valueOf(amount));
	temp2.add(String.valueOf(tax));
	temp2.add(String.valueOf(retur));
	temp2.add(String.valueOf(apPayment));
	temp2.add(String.valueOf(apSaldo));
	
	listAp.add(temp2);
	
	totalAmount += amount;
	totalTax += tax;
	totalRetur += retur;
	totalPayment += apPayment;
	totalSaldo += apSaldo;
}

Vector compTelpFax = (Vector)companyAddress.get(2);
headerPdf.add(0,(String) companyAddress.get(0));
headerPdf.add(1,(String) companyAddress.get(1));
headerPdf.add(2, (String)compTelpFax.get(0)+" "+(String)compTelpFax.get(1));
headerPdf.add(3, textGlobalTitle[SESS_LANGUAGE][0]);
headerPdf.add(4, "");//textGlobalTitle[SESS_LANGUAGE][1]+" : "+(srcAccPayable.getInvoiceDateStatus()==1?(Formater.formatDate(srcAccPayable.getStartDate(), "dd-MM-yyyy")+" - "+Formater.formatDate(srcAccPayable.getEndDate(), "dd-MM-yyyy")):" - "));
headerPdf.add(5, textGlobalTitle[SESS_LANGUAGE][2]+" : "+currencyType.getCode());
headerPdf.add(6, textGlobalTitle[SESS_LANGUAGE][3]+" : "+Formater.formatDate(new Date(), "dd-MM-yyyy"));

listTableHeaderPdf.add("No");
listTableHeaderPdf.add(textListHeader[SESS_LANGUAGE][0]);
listTableHeaderPdf.add(textListHeader[SESS_LANGUAGE][1]);
listTableHeaderPdf.add(textListHeader[SESS_LANGUAGE][2]);
listTableHeaderPdf.add(textListHeader[SESS_LANGUAGE][3]);
listTableHeaderPdf.add(textListHeader[SESS_LANGUAGE][4]);
listTableHeaderPdf.add(textListHeader[SESS_LANGUAGE][5]);
listTableHeaderPdf.add(textListHeader[SESS_LANGUAGE][6]);
listTableHeaderPdf.add(textListHeader[SESS_LANGUAGE][7]);
listTableHeaderPdf.add(textListHeader[SESS_LANGUAGE][8]);

bodyPdf.add(listTableHeaderPdf);
bodyPdf.add(listAp);

footerPdf.add(FRMHandler.userFormatStringDecimal(totalAmount));
footerPdf.add(FRMHandler.userFormatStringDecimal(totalTax));
footerPdf.add(FRMHandler.userFormatStringDecimal(totalRetur));
footerPdf.add(FRMHandler.userFormatStringDecimal(totalPayment));
footerPdf.add(FRMHandler.userFormatStringDecimal(totalSaldo));

pdfContent.add(headerPdf);
pdfContent.add(bodyPdf);
pdfContent.add(footerPdf);

session.putValue("REPORT_AP_PDF", pdfContent);*/

%>
<script language="JavaScript">
document.location="<%=printroot%>.report.ListAbsenceReasonPdf";
</script>


