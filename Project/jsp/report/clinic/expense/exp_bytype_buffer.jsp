<%@ page language="java" %>
<%@ include file = "../../../main/javainit.jsp" %>

<!--import harisma-->
<%@ page import="com.dimata.harisma.entity.clinic.*" %>
<%@ page import="com.dimata.harisma.session.clinic.*" %>
<%@ page import="com.dimata.harisma.form.clinic.*" %>
<%@ page import="com.dimata.harisma.entity.masterdata.*" %>
<!--import java-->
<%@ page import="java.util.*" %>

<!--import qdep-->
<%@ page import="com.dimata.util.*" %>
<%@ page import="com.dimata.gui.jsp.*" %>
<%@ page import="com.dimata.qdep.form.*" %> 


<html>
<head>
<title>Print Medical Expense</title>
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
int dt = FRMQueryString.requestInt(request, "date_periode_dy");
int month = FRMQueryString.requestInt(request, "date_periode_mn");
int year = FRMQueryString.requestInt(request, "date_periode_yr");
Date dtPeriod = new Date(year-1900,month-1,dt);
System.out.println("start Date : "+dtPeriod); 

int dtend = FRMQueryString.requestInt(request, "date_periode_end_dy");
int monthend = FRMQueryString.requestInt(request, "date_periode_end_mn");
int yearend = FRMQueryString.requestInt(request, "date_periode_end_yr");
Date dtPeriodend = new Date(yearend-1900,monthend-1,dtend);
System.out.println("end date : "+dtPeriodend); 

int person = FRMQueryString.requestInt(request, "persons");

String whereClause = "";
String orderClause = "";

Vector listAll = new Vector(1,1);
Vector listAllContent = new Vector(1,1);

String strMonth = "";
if(dtPeriod != null){
	strMonth = YearMonth.getLongEngMonthName(dtPeriodend.getMonth()+1)+" "+(dtPeriodend.getYear()+1900);
	strMonth = strMonth + "  -  ( "+Formater.formatDate(dtPeriod,"dd MMM yyyy")+" to "+Formater.formatDate(dtPeriodend,"dd MMM yyyy")+" )";
}
listAll.add(strMonth);

orderClause = PstMedicalType.fieldNames[PstMedicalType.FLD_TYPE_CODE];
Vector listMedType = PstMedicalType.list(0,0,"", orderClause);

orderClause = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT];
Vector vctDep = PstDepartment.list(0,0, "", orderClause);

for(int t=0; t< listMedType.size();t++){
	Vector typeExp = new Vector(1,1);
	MedicalType medicalType = (MedicalType)listMedType.get(t);
	typeExp.add(medicalType);
	
	if(vctDep!=null && vctDep.size()>0){
		double totalAll = 0;
		Vector tempDep = new Vector(1,1);
		for(int i=0; i<vctDep.size(); i++){
			Department dep = (Department)vctDep.get(i);
			double amount = SessMedicalRecord.getMedExpForMedTypeAndDepartment(medicalType.getOID(), dep.getOID(), dtPeriod,dtPeriodend);
			totalAll = totalAll + amount;
			if(amount>0){
			
				Vector vc = new Vector(1,1);				
				vc.add(""+((tempDep.size()/3)+1));								
				vc.add(dep.getDepartment());
				vc.add(Formater.formatNumber(amount, "#,###.#"));				
								
				tempDep.add(vc);
			}
		}
		typeExp.add(tempDep);
		typeExp.add(""+Formater.formatNumber(totalAll, "#,###.#"));		
	}
	else{
		typeExp.add(new Vector(1,1));
	}
	
	listAllContent.add(typeExp);
	
	/*Hashtable listData = new Hashtable();
	Vector listAllContentExpense = SessMedicalRecord.getSummaryExpense(dtPeriod, medicalType.getOID());
	if(listAllContentExpense != null && listAllContentExpense.size()>0){
		listData.put(medicalType.getTypeCode().toUpperCase()+ " "+medicalType.getTypeName().toUpperCase()+"\nPERIODE : "+strMonth,listAllContentExpense);
		listAllContent.add(listData);
	}	*/
}

listAll.add(listAllContent);


System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+listAllContent.size());
System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+listAllContent);

if(session.getValue("EXPENSE_TYPE")!=null){
	session.removeValue("EXPENSE_TYPE");
}
session.putValue("EXPENSE_TYPE",listAll);
%>

<script language="JavaScript">
document.location="<%=printroot%>.report.clinic.ExpenseByTypePdf";
</script>


