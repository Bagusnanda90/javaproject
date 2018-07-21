
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
	              com.dimata.harisma.entity.employee.Employee,
				  com.dimata.harisma.session.employee.SessEmployee,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
				  com.dimata.harisma.entity.attendance.PstEmpSchedule,
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
public Vector drawList(Vector objectClass,long oidPeriod,Date selectedDateFrom,Date selectedDateTo,long departmentId, long sectionId)
	{
		//ambil dayOfMont dari date awal
		Calendar newCalendar = Calendar.getInstance();
		newCalendar.setTime(selectedDateFrom);
		int dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
		int dateStart = selectedDateFrom.getDate();
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
		ctrlist.addHeader("Date","2%","0","7");
		//**************** untuk indexnya************************ 
		ctrlist.addHeader(""+dateStart,"1%","0","0");
		for (int o=0;o<6;o++){
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
		Vector vectTypeOvt = new Vector(1,1);
		String whereClause = "";
		String frmCurrency = "#,###";
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
		int index = -1;
		vectTypeOvt.add("2");
		double sumTotalSalary = 0;
		double sumOvtI= 0;
		double sumOvtII= 0;
		double sumTotTimeOvt = 0 ;
		double nominalI = 0;
		double nominalII=0;
		double sumNominal = 0;
		double sumTakeHome = 0;
		double totMealAlloance= 0;
		long oidPosition = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_KOMPRESSOR_POSITION")));
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
				 for(int f=0;f<6;f++){
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
			 //kondisi unutk karyawan yang dibayar full--jadi dianggap dia masuk terus
			 if(employee.getPositionId()==oidPosition){
			 	statusAbsence = PstEmpSchedule.STATUS_PRESENCE_OK;	
			 }else if(employee.getPositionId()!=oidPosition){
				statusAbsence = PstEmpSchedule.getStatus1(dateStartContent,employee.getOID(),dateContent);
			 }
			 if(statusAbsence==PstEmpSchedule.STATUS_PRESENCE_OK){
				rowx.add("V");
				rowxPdf.add("V");
				totPresence = totPresence +1;
			 }
			 else{
				//jika tidak masuk dicari reasonnya
				int absenceReason1 = PstEmpSchedule.getReason1(dateStartContent,employee.getOID(),dateContent);
				String strSymbol = PstReason.getStrReasonReport(absenceReason1);
				if(strSymbol.equals("X")){
					absence = absence +1;
				}
				else if(strSymbol.equals("V")){
					totPresence = totPresence +1;
				}
				rowx.add(""+strSymbol);
				rowxPdf.add(""+strSymbol);
			 }
			 for(int f=0;f<6;f++){
				 if(dateStartContent==dateOfMonth){
						dateStartContent = 0;
						month = month +1;
						year = selectedDateTo.getYear();
					}
					dateStartContent++;
					dateContent = new java.util.Date(year, month, dateStartContent, 0, 0);
					statusAbsence = PstEmpSchedule.getStatus1(dateStartContent,employee.getOID(),dateContent);
					if(employee.getPositionId()==oidPosition)  {
						statusAbsence = PstEmpSchedule.STATUS_PRESENCE_OK;
					}else if(employee.getPositionId()!=oidPosition){
						statusAbsence = PstEmpSchedule.getStatus1(dateStartContent,employee.getOID(),dateContent);
					}
					if(statusAbsence==PstEmpSchedule.STATUS_PRESENCE_OK){
						rowx.add("V");
						rowxPdf.add("V");
						totPresence = totPresence +1;
					}
					else{
						//jika tidak masuk dicari reasonnya
						int absenceReason = PstEmpSchedule.getReason1(dateStartContent,employee.getOID(),dateContent);
						String strSymbol = PstReason.getStrReasonReport(absenceReason);
						if(strSymbol.equals("X")){
							absence = absence +1;
						}
						else if(strSymbol.equals("V")){
							totPresence = totPresence +1;
						}
						rowx.add(""+strSymbol);
						rowxPdf.add(""+strSymbol);
					}
				 }
			 rowx.add(""+Formater.formatNumber(totPresence,frmCurrency));
			 rowxPdf.add(""+Formater.formatNumber(totPresence,frmCurrency));
			 //ambil uang makan perhari dari system property
			 double mealAllowance = Double.parseDouble(String.valueOf(PstSystemProperty.getValueByName("MEAL_ALLOWANCE")));  
			 rowx.add("<div align=\"right\">"+Formater.formatNumber(mealAllowance*totPresence,frmCurrency)+"</div>");
			 rowxPdf.add(""+Formater.formatNumber(mealAllowance*totPresence,frmCurrency));
			 totMealAlloance = totMealAlloance + (mealAllowance*totPresence);
			 if(absence > 0){
			 	rowx.add("Absence = "+Formater.formatNumber(absence,frmCurrency));
				rowxPdf.add("Absence = "+Formater.formatNumber(absence,frmCurrency));
			 }
			 else{
			 	rowx.add("");
				rowxPdf.add("");
			 }
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
public Vector drawListMontly(Vector objectClass,long oidPeriod,long departmentId, long sectionId,int starDate, int maxDayfMonth,Date periodStartDate, Date periodEndDate)
	{
		
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
		ctrlist.addHeader("Date","2%","0",""+maxDayfMonth);
		//**************** untuk indexnya************************ 
		ctrlist.addHeader(""+starDate,"1%","0","0");
		for (int o=0;o<(maxDayfMonth-1);o++){
				if(starDate==maxDayfMonth){
					starDate = 0;
				}
				starDate++;
				ctrlist.addHeader(""+starDate,"1%","0","0");
		}
		//**************** untuk indexnya************************ 
		ctrlist.addHeader("Presence","1%","2","0");
		ctrlist.addHeader("Meal Allowance","3%","2","0");
		ctrlist.addHeader("Description","3%","2","0");
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
		double sumTakeHome = 0;
		double totMealAlloance= 0;
		long oidPosition = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_KOMPRESSOR_POSITION")));
		for (int i = 0; i <objectClass.size(); i++) {
			Vector rowx = new Vector();
			Vector rowxPdf = new Vector();
			Vector temp = (Vector)objectClass.get(i);
			Employee employee = (Employee)temp.get(0);
			EmpCategory empCateg = (EmpCategory)temp.get(1);
			Religion religion = (Religion)temp.get(2);
			int totPresence = 0;
			int absence = 0;
			int dateStartContent = periodStartDate.getDate();
			int month = periodStartDate.getMonth();
			int year = periodStartDate.getYear();
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
			 Date dateContent = new java.util.Date(year, month, dateStartContent, 0, 0);
			 int statusAbsence = 0;
			 //statusAbsence = PstEmpSchedule.getStatus1(dateStartContent,employee.getOID(),dateContent);
			 //kondisi unutk karyawan yang dibayar full--jadi dianggap dia masuk terus
			 if(employee.getPositionId()==oidPosition)  {
			 	statusAbsence = PstEmpSchedule.STATUS_PRESENCE_OK;	
			 }else{
				statusAbsence = PstEmpSchedule.getStatus1(dateStartContent,employee.getOID(),dateContent);
			 }
			 if(statusAbsence==PstEmpSchedule.STATUS_PRESENCE_OK){
				rowx.add("V");
				rowxPdf.add("V");
				totPresence = totPresence +1;
			 }
			 //jika tidak masuk,maka dicari reasonnya
			 else{
			 	int absenceReason1 = PstEmpSchedule.getReason1(dateStartContent,employee.getOID(),dateContent);
				String strSymbol = PstReason.getStrReasonReport(absenceReason1);
				if(strSymbol.equals("X")){
					absence = absence +1;
				}
				else if(strSymbol.equals("V")){
					totPresence = totPresence +1;
				}
				rowx.add(""+strSymbol);
				rowxPdf.add(""+strSymbol);	
			 }
			for (int o=0;o<(maxDayfMonth-1);o++){
				if(dateStartContent==maxDayfMonth){
						dateStartContent = 0;
						month = month +1;
						year = periodEndDate.getYear();
					}
					dateStartContent++;
					dateContent = new java.util.Date(year, month, dateStartContent, 0, 0);
					statusAbsence = PstEmpSchedule.getStatus1(dateStartContent,employee.getOID(),dateContent);
					if(employee.getPositionId()==oidPosition)  {
						statusAbsence = PstEmpSchedule.STATUS_PRESENCE_OK;
					}else{
						statusAbsence = PstEmpSchedule.getStatus1(dateStartContent,employee.getOID(),dateContent);
					}
					if(statusAbsence==PstEmpSchedule.STATUS_PRESENCE_OK){
						rowx.add("V");
						rowxPdf.add("V");
						totPresence = totPresence +1;
					}
					else{
						//jika tidak masuk dicari reasonnya
						int absenceReason = PstEmpSchedule.getReason1(dateStartContent,employee.getOID(),dateContent);
						String strSymbol = PstReason.getStrReasonReport(absenceReason);
						if(strSymbol.equals("X")){
							absence = absence +1;
						}
						else if(strSymbol.equals("V")){
							totPresence = totPresence +1;
						}
						rowx.add(""+strSymbol);
						rowxPdf.add(""+strSymbol);
					}
		    }
			 rowx.add("<div align=\"right\">"+Formater.formatNumber(totPresence,frmCurrency)+"</div>");
			 rowxPdf.add(""+Formater.formatNumber(totPresence,frmCurrency));
			 //ambil uang makan perhari dari system property
			 double mealAllowance = Double.parseDouble(String.valueOf(PstSystemProperty.getValueByName("MEAL_ALLOWANCE")));  
			 rowx.add("<div align=\"right\">"+Formater.formatNumber(mealAllowance*totPresence,frmCurrency)+"</div>");
			 rowxPdf.add(""+Formater.formatNumber(mealAllowance*totPresence,frmCurrency));
			 totMealAlloance = totMealAlloance + (mealAllowance*totPresence);
			 if(absence > 0){
			 	rowx.add("Absence = "+Formater.formatNumber(absence,frmCurrency));
				rowxPdf.add("Absence = "+Formater.formatNumber(absence,frmCurrency));
			 }
			 else{
			 	rowx.add("");
				rowxPdf.add("");
			 }
			lstData.add(rowx);
			vectDataToPdf.add(rowxPdf);
			}
		
		result.add(ctrlist.drawList());
		result.add(vectDataToPdf);
		result.add(vectTypeOvt);
		return result;
	}
%>
<!-- untuk summary -->
<%!
public Vector drawSummary(Vector objectClass,long oidPeriod,Date selectedDateFrom,Date selectedDateTo,long departmentId, long sectionId)
	{
		//ambil dayOfMont dari date awal
		Calendar newCalendar = Calendar.getInstance();
		newCalendar.setTime(selectedDateFrom);
		int dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
		int dateStart = selectedDateFrom.getDate();
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
		Vector vectTypeOvt = new Vector(1,1);
		String whereClause = "";
		String frmCurrency = "#,###";
		int index = -1;
		vectTypeOvt.add("2");
		int totAbsence = 0;
		long oidPosition = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName("OID_KOMPRESSOR_POSITION")));
		for (int i = 0; i <objectClass.size(); i++) {
			 Vector temp = (Vector)objectClass.get(i);
			 Employee employee = (Employee)temp.get(0);
			 EmpCategory empCateg = (EmpCategory)temp.get(1);
			 Religion religion = (Religion)temp.get(2);
			 Vector rowx = new Vector();
			 Vector rowxPdf = new Vector();
			 int dateStartContent = selectedDateFrom.getDate();
			 int month = selectedDateFrom.getMonth();
			 int year = selectedDateFrom.getYear();
			 double totPresence = 0;
			 double absence = 0;
			 Date dateContent = new java.util.Date(year, month, dateStartContent, 0, 0);
			 int statusAbsence =0;
			 statusAbsence = PstEmpSchedule.getStatus1(dateStartContent,employee.getOID(),dateContent);
			 //kondisi unutk karyawan yang dibayar full--jadi dianggap dia masuk terus
			 if(employee.getPositionId()==oidPosition){
			 	statusAbsence = PstEmpSchedule.STATUS_PRESENCE_OK;	
			 }else if(employee.getPositionId()!=oidPosition){
				statusAbsence = PstEmpSchedule.getStatus1(dateStartContent,employee.getOID(),dateContent);
			 }
			 if(statusAbsence!=PstEmpSchedule.STATUS_PRESENCE_OK){
				//jika tidak masuk dicari reasonnya
				int absenceReason1 = PstEmpSchedule.getReason1(dateStartContent,employee.getOID(),dateContent);
				String strSymbol = PstReason.getStrReasonReport(absenceReason1);
				 if(strSymbol.equals("X") || strSymbol.equals("L") || strSymbol.equals("S") || strSymbol.equals("C") || strSymbol.equals("I")){
					totAbsence = totAbsence +1;
				}
			 }
			 for(int f=0;f<6;f++){
				 if(dateStartContent==dateOfMonth){
						dateStartContent = 0;
						month = month +1;
						year = selectedDateTo.getYear();
					}
					dateStartContent++;
					dateContent = new java.util.Date(year, month, dateStartContent, 0, 0);
					statusAbsence = PstEmpSchedule.getStatus1(dateStartContent,employee.getOID(),dateContent);
					if(employee.getPositionId()==oidPosition)  {
						statusAbsence = PstEmpSchedule.STATUS_PRESENCE_OK;
					}else if(employee.getPositionId()!=oidPosition){
						statusAbsence = PstEmpSchedule.getStatus1(dateStartContent,employee.getOID(),dateContent);
					}
					if(statusAbsence!=PstEmpSchedule.STATUS_PRESENCE_OK){
						//jika tidak masuk dicari reasonnya
						int absenceReason = PstEmpSchedule.getReason1(dateStartContent,employee.getOID(),dateContent);
						String strSymbol = PstReason.getStrReasonReport(absenceReason);
						if(strSymbol.equals("X") || strSymbol.equals("L") || strSymbol.equals("S") || strSymbol.equals("C") || strSymbol.equals("I")){
							totAbsence = totAbsence +1;
						}
					}
				 }
			lstData.add(rowx);
			vectDataToPdf.add(rowxPdf);
			}
		result.add(ctrlist.drawList());
		result.add(vectDataToPdf);
		result.add(vectTypeOvt);
		result.add(""+totAbsence);
		return result;
	}
%>
<!-----------------------------------------------------------------------!>
<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment= FRMQueryString.requestLong(request,"department");
long oidPeriod = FRMQueryString.requestLong(request,"periode");
long oidSection = FRMQueryString.requestLong(request,"section");
int gotoPeriod = FRMQueryString.requestInt(request, "hidden_goto_period");
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int reportPeriod = FRMQueryString.requestInt(request,"reportPeriod");
int setFooter =  FRMQueryString.requestInt(request,"setFooter");
String footer = FRMQueryString.requestString(request, "footer");
String titleName = FRMQueryString.requestString(request, "titleName");
Date selectedDateFrom = FRMQueryString.requestDate(request, "check_date_start");
Date selectedDateTo = FRMQueryString.requestDate(request, "check_date_finish");
int summRadio= FRMQueryString.requestInt(request, "summary");
String frmCurrency = "#,###";


Vector tempQueryReport = new Vector(1,1);
Vector vectSummary = new Vector(1,1);

if(iCommand == Command.LIST){
		System.out.println("masuk list.........");
		tempQueryReport = SessEmployee.getListEmp(oidDepartment,oidSection,gotoPeriod,"meal");
}
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
Vector vectTypeOvt = new Vector (1,1);
if(gotoPeriod==PstPayEmpLevel.WEEKLY){
	vectResult = drawList(tempQueryReport,oidPeriod,selectedDateFrom,selectedDateTo,oidDepartment,oidSection);
}
else if(gotoPeriod==PstPayEmpLevel.MONTLY){
		vectResult = drawListMontly(tempQueryReport,oidPeriod,oidDepartment,oidSection,startDatePeriod,dateOfMonth,date,endDate);
}
listData = String.valueOf(vectResult.get(0));
vectDataToPdf = (Vector)vectResult.get(1);
vectTypeOvt = (Vector)vectResult.get(2);
String totAbsence = "";

/*vectHeadCompD = (Vector)vectResult.get(3);*/
// design vector that handle data to store in session
Vector vectPresence = new Vector(1,1);
vectPresence.add(vectDataToPdf);  
vectPresence.add(""+deptName);  
vectPresence.add(""+sectName);  
vectPresence.add(""+footer); 
if(gotoPeriod==PstPayEmpLevel.WEEKLY){
	vectPresence.add(selectedDateFrom);  
	vectPresence.add(selectedDateTo);  
}else if(gotoPeriod==PstPayEmpLevel.MONTLY){
	vectPresence.add(date);  
	vectPresence.add(endDate);
}
vectPresence.add(""+periodName); 
double totalMealAll = 0;
int weeklyDay = 7;
double totalAbsence = 0;
double totalPayment=0;
Vector vectListSummary = new Vector(1,1);
String listSummary ="";
if(summRadio==1 && gotoPeriod==PstPayEmpLevel.WEEKLY && tempQueryReport.size() > 0){
	vectSummary = SessEmployee.getListEmp(0,0,gotoPeriod,"meal");
	vectListSummary = drawSummary(vectSummary,oidPeriod,selectedDateFrom,selectedDateTo,oidDepartment,oidSection);
	listSummary = String.valueOf(vectListSummary.get(0));
	totAbsence = String.valueOf(vectListSummary.get(3));
	totalMealAll = (vectSummary.size() *(weeklyDay* Double.parseDouble(String.valueOf(PstSystemProperty.getValueByName("MEAL_ALLOWANCE")))));
	totalAbsence = Double.parseDouble(totAbsence) * Double.parseDouble(String.valueOf(PstSystemProperty.getValueByName("MEAL_ALLOWANCE")));
	vectPresence.add(""+totalMealAll);
	vectPresence.add(""+totalAbsence);
	totalPayment = totalMealAll - totalAbsence;
	vectPresence.add(""+totalPayment);
	System.out.println("totAbsence..............."+totAbsence);
}
else{
	vectPresence.add("");
	vectPresence.add("");
	vectPresence.add("");
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
<title>HARISMA - Meal Allowance Report</title>
<script language="JavaScript">
function deptChange() {
	document.frmreason.command.value = "<%=Command.GOTO%>";
	document.frmreason.hidden_goto_period.value = document.frmreason.reportPeriod.value;
	//document.frmsrcpresence.hidden_goto_dept.value = document.frmsrcpresence.DEPARTMENT_ID.value;
	document.frmreason.action = "uang_makan.jsp";
	document.frmreason.submit();
}

<%if(iCommand==Command.PRINT){%>
    //com.dimata.harisma.report.listRequest	
	window.open("<%=printroot%>.report.listRequest.ListEmpCategPdf");
<%}%>

function cmdView(){
	document.frmreason.command.value="<%=Command.LIST%>";
	document.frmreason.action="uang_makan.jsp";
	document.frmreason.submit();
}

function cmdFooter(){
	document.frmreason.setFooter.value="1";
	document.frmreason.action="uang_makan.jsp";
	document.frmreason.submit();
}

function deptChange() {
	document.frmreason.command.value = "<%=Command.GOTO%>";
	document.frmreason.hidden_goto_period.value = document.frmreason.reportPeriod.value;
	//document.frmsrcpresence.hidden_goto_dept.value = document.frmsrcpresence.DEPARTMENT_ID.value;
	document.frmreason.action = "uang_makan.jsp";
	document.frmreason.submit();
}

function reportPdf(){
	var linkPage ="<%=printroot%>.report.payroll.mealAllowancePdf";
	window.open(linkPage); 
}

function reportPdfMontly(){
	var linkPage ="<%=printroot%>.report.payroll.mealAllowanceMontlyPdf";
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Report &gt; Payroll
                  &gt; Meal Allowance<!-- #EndEditable --> </strong></font> </td>
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
                                          <td width="13%" align="right" nowrap> 
                                            <div align="left">Report Period </div></td>
                                          <td width="86%"> : 
                                            <%
											// meall allowance
											Vector mealKey = new Vector();
											Vector mealValue = new Vector();
											
											mealKey.add(PstPayEmpLevel.WEEKLY+"");
											mealKey.add(PstPayEmpLevel.MONTLY+"");
											mealValue.add(PstPayEmpLevel.stAllowance[PstPayEmpLevel.WEEKLY]);
											mealValue.add(PstPayEmpLevel.stAllowance[PstPayEmpLevel.MONTLY]);
											String selectPeriod = String.valueOf(gotoPeriod);
											//out.println("gotoPeriod  "+gotoPeriod);
										  %> <%=ControlCombo.draw("reportPeriod",null,""+selectPeriod,mealKey,mealValue,"onchange=\"javascript:deptChange();\"")%></td>
                                        </tr>
										<%
											if(gotoPeriod==PstPayEmpLevel.WEEKLY){
										%>
										<tr> 
                                          <td width="1%">&nbsp;</td>
                                          <td width="16%" align="right" nowrap> 
                                            <div align="left">Period </div></td>
                                          <td width="83%"> : 
										  <%=ControlDate.drawDate("check_date_start",selectedDateFrom==null || iCommand == Command.NONE?new Date():selectedDateFrom,"formElemen",0,installInterval)%> to <%=ControlDate.drawDate("check_date_finish",selectedDateTo==null || iCommand == Command.NONE?new Date():selectedDateTo,"formElemen",0,installInterval)%>                                    
										  </tr>
										<%
										}else{
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
										  %> <%=ControlCombo.draw("periode",null,""+oidPeriod,prdValue,prdKey,"")%></td>
										  </tr>
										  <%
										   }
										  %>
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
										<table width="66%" border="0">
										<% if(summRadio==1){
											%>
												<tr> 
                                                <td>Total Meal Allowance</td>
                                                <td align="right"><%=Formater.formatNumber(totalMealAll, frmCurrency) %></td>
                                                <td colspan="2">&nbsp;</td>
                                              </tr>
                                              <tr> 
                                                <td>Absence Deduction</td>
                                                <td align="right"><%=Formater.formatNumber(totalAbsence, frmCurrency) %></td>
                                                <td width="5%">&nbsp;</td>
                                                <td width="45%">&nbsp;</td>
                                              </tr>
											  <tr> 
                                                <td><b>Total Payment</b></td>
                                                <td align="right"><b><%=Formater.formatNumber(totalPayment, frmCurrency) %></b></td>
                                                <td width="5%">&nbsp;</td>
                                                <td width="45%">&nbsp;</td>
                                              </tr>
											  <%
											}
											%>
                                              <tr> 
                                                <td><font color="#FF0000"><em>V 
                                                  = </em></font><font color="#FF0000"><em>PRESENCE 
                                                  OK</em></font></td>
                                                <td><font color="#FF0000"><em>C 
                                                  = </em></font><font color="#FF0000"><em>LEAVE</em></font></td>
                                                <td colspan="2"><font color="#FF0000"><em>I 
                                                  = </em></font><font color="#FF0000"><em>SPECIAL 
                                                  DISPENSATION</em></font></td>
                                              </tr>
                                              <tr> 
                                                <td><font color="#FF0000"><em>L 
                                                  =</em></font><font color="#FF0000"><em>DAY 
                                                  OFF</em></font></td>
                                                <td><font color="#FF0000"><em>X 
                                                  =</em> </font><font color="#FF0000"><em>ABSENCE</em></font></td>
                                                <td width="5%">&nbsp;</td>
                                                <td width="45%">&nbsp;</td>
                                              </tr>
                                            </table>
										</td>
										</tr>
                                        <%if(privPrint){%>
                                        <tr> 
                                          <td> <table width="23%" border="0" cellspacing="1" cellpadding="1">
										  	<%
												if(gotoPeriod==PstPayEmpLevel.WEEKLY){
											%>
                                              <tr> 
                                                <td width="5%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
                                                <td width="95%"><b><a href="javascript:reportPdf()" class="buttonlink">Print 
                                                  Meal Allowance Report</a></b> </td>
                                              </tr>
											  <%
											  }else{
											  %>
											  <tr> 
                                                <td width="5%"><a href="javascript:reportPdfMontly()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
                                                <td width="95%"><b><a href="javascript:reportPdfMontly()" class="buttonlink">Print 
                                                  Meal Allowance Report</a></b> </td>
                                              </tr>
											  <%
											  }
											  %>
                                            </table></td>
                                        </tr>
                                        <%}%>
                                        <%}else{%>
                                        <tr> 
                                          <td></td>
                                        </tr>
                                        <tr> 
                                          <td> <%
											out.println("<div class=\"msginfo\">&nbsp;&nbsp;No employee leave  data found ...</div>");											
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
