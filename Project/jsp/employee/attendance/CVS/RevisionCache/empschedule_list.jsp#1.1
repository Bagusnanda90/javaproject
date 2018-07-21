 
<% 
/* 
 * Page Name  		:  empschedule_list.jsp
 * Created on 		:  [date] [time] AM/PM 
 * 
 * @author  		: karya 
 * @version  		: 01 
 */

/*******************************************************************
 * Page Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 			: [output ...] 
 *******************************************************************/
%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.form.leave.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.session.leave.dp.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.dp.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.ll.*" %>
<%@ page import = "com.dimata.harisma.entity.leave.ll.LLAppMain" %>
<%@ page import = "com.dimata.harisma.session.leave.ll.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_WORKING_SCHEDULE); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privPrint=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
Date dtPeriodStart = new Date();
%>
  
<!-- Jsp Block -->
<%!
public String drawList(Vector objectClass)
{	
	Date now = new Date();
	//int maxDayOfMonth = 0;
	int monthStartDate = Integer.parseInt(String.valueOf(now.getMonth()));
	int yearStartDate =  Integer.parseInt(String.valueOf(now.getYear()+1900));
	int dateStartDate =  Integer.parseInt(String.valueOf(now.getDate()));
	int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
	System.out.println("START_DATE_PERIOD  "+startDatePeriod);
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("Period","8%");
	ctrlist.addHeader("Employee","11%");
	//untuk period
	for (int i = 0; i < objectClass.size(); i++) 
	{
		Vector temp = (Vector) objectClass.get(i);
		Employee employee = (Employee) temp.get(1);
		Period period = (Period) temp.get(2);
		EmpSchedule empSchedule = (EmpSchedule)temp.get(0);		
		
		long employeeId = employee.getOID();
		Date periodStartDate = period.getStartDate();
		monthStartDate = periodStartDate.getMonth()+1;
		yearStartDate =  periodStartDate.getYear()+1900;
		dateStartDate =  periodStartDate.getDate();
		String strFullName = employee.getFullName();
	}	
	
	if(SystemProperty.SYS_PROP_SCHEDULE_PERIOD != SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL)
	{		
		ctrlist.addHeader("21","2%");
		ctrlist.addHeader("22","2%");
		ctrlist.addHeader("23","2%");
		ctrlist.addHeader("24","2%");
		ctrlist.addHeader("25","2%");
		ctrlist.addHeader("26","2%");
		ctrlist.addHeader("27","2%");
		ctrlist.addHeader("28","2%");
		ctrlist.addHeader("29","2%");
		ctrlist.addHeader("30","2%");
		ctrlist.addHeader("31","2%");
		ctrlist.addHeader("1","2%");
		ctrlist.addHeader("2","2%");
		ctrlist.addHeader("3","2%");
		ctrlist.addHeader("4","2%");
		ctrlist.addHeader("5","2%");
		ctrlist.addHeader("6","2%");
		ctrlist.addHeader("7","2%");
		ctrlist.addHeader("8","2%");
		ctrlist.addHeader("9","2%");
		ctrlist.addHeader("10","2%");
		ctrlist.addHeader("11","2%");
		ctrlist.addHeader("12","2%");
		ctrlist.addHeader("13","2%");
		ctrlist.addHeader("14","2%");
		ctrlist.addHeader("15","2%");
		ctrlist.addHeader("16","2%");
		ctrlist.addHeader("17","2%");
		ctrlist.addHeader("18","2%");
		ctrlist.addHeader("19","2%");				
		ctrlist.addHeader("20","2%");	
	}
	else
	{	
		
		//System.out.println("monthStartDate  "+monthStartDate);
		GregorianCalendar periodStart = new GregorianCalendar(yearStartDate, monthStartDate-1, dateStartDate);
        int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);	
		System.out.println("maxDayOfMonth  "+maxDayOfMonth);
		ctrlist.addHeader(""+startDatePeriod,"2%");
		for(int i = 0 ; i < maxDayOfMonth-1 ; i++) {
				if(startDatePeriod == maxDayOfMonth){
					startDatePeriod = 1;
					ctrlist.addHeader(""+startDatePeriod,"2%");
				}
				else {
					startDatePeriod = startDatePeriod +1;
					ctrlist.addHeader(""+startDatePeriod,"2%");
				}
				
		}
		
		/*ctrlist.addHeader("3","2%");
		ctrlist.addHeader("4","2%");
		ctrlist.addHeader("5","2%");
		ctrlist.addHeader("6","2%");
		ctrlist.addHeader("7","2%");
		ctrlist.addHeader("8","2%");
		ctrlist.addHeader("9","2%");
		ctrlist.addHeader("10","2%");
		ctrlist.addHeader("11","2%");
		ctrlist.addHeader("12","2%");
		ctrlist.addHeader("13","2%");
		ctrlist.addHeader("14","2%");
		ctrlist.addHeader("15","2%");
		ctrlist.addHeader("16","2%");
		ctrlist.addHeader("17","2%");
		ctrlist.addHeader("18","2%");			
		ctrlist.addHeader("19","2%");
		ctrlist.addHeader("20","2%");
		ctrlist.addHeader("21","2%");
		ctrlist.addHeader("22","2%");
		ctrlist.addHeader("23","2%");
		ctrlist.addHeader("24","2%");
		ctrlist.addHeader("25","2%");
		ctrlist.addHeader("26","2%");
		ctrlist.addHeader("27","2%");
		ctrlist.addHeader("28","2%");				
		ctrlist.addHeader("29","2%");
		ctrlist.addHeader("30","2%");
		ctrlist.addHeader("31","2%");*/		
	}

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();

	// store data schedule symbol into Hashtable object
	Hashtable scheduleSymbol = new Hashtable();
	Vector listScd = PstScheduleSymbol.list(0, 0, "", "");
	scheduleSymbol.put("0", "-");
	for (int ls = 0; ls < listScd.size(); ls++) 
	{
		ScheduleSymbol scd = (ScheduleSymbol) listScd.get(ls);
		scheduleSymbol.put(String.valueOf(scd.getOID()), scd.getSymbol());
	}
	
	// store data leave schedule (DP, AL, LL, Maternity Leave, DC, Special Leave and Unpaid Leave) into Hashtable object
	String OID_DAY_OFF_PAYMENT = "OID_DAY_OFF_PAYMENT";
	String OID_AL_LEAVE = "OID_AL_LEAVE";
	String OID_LL_LEAVE = "OID_LL_LEAVE";	
	String OID_MATERNITY_LEAVE = "OID_MATERNITY_LEAVE";
	String OID_SICK_LEAVE = "OID_SICK_LEAVE";
	String OID_SPECIAL_LEAVE = "OID_SPECIAL_LEAVE";
	String OID_UNPAID_LEAVE = "OID_UNPAID_LEAVE";	
	
	String dpOid = String.valueOf(PstSystemProperty.getValueByName(OID_DAY_OFF_PAYMENT));
	String alOid = String.valueOf(PstSystemProperty.getValueByName(OID_AL_LEAVE));
	String llOid = String.valueOf(PstSystemProperty.getValueByName(OID_LL_LEAVE));
	String maternityOid = String.valueOf(PstSystemProperty.getValueByName(OID_MATERNITY_LEAVE));
	String sickOid = String.valueOf(PstSystemProperty.getValueByName(OID_SICK_LEAVE));
	String specialOid = String.valueOf(PstSystemProperty.getValueByName(OID_SPECIAL_LEAVE));
	String unpaidOid = String.valueOf(PstSystemProperty.getValueByName(OID_UNPAID_LEAVE));
	
	Hashtable hashLeaveSchedule = new Hashtable();
	hashLeaveSchedule.put(dpOid,"DP");
	hashLeaveSchedule.put(alOid,"AL");
	hashLeaveSchedule.put(llOid,"LL");
	hashLeaveSchedule.put(maternityOid,"MATERNITY");
	hashLeaveSchedule.put(sickOid,"DC");
	hashLeaveSchedule.put(specialOid,"SPEC");
	hashLeaveSchedule.put(unpaidOid,"UNPAID");							

	for (int i = 0; i < objectClass.size(); i++) 
	{
		Vector temp = (Vector) objectClass.get(i);
		Employee employee = (Employee) temp.get(1);
		Period period = (Period) temp.get(2);
		EmpSchedule empSchedule = (EmpSchedule)temp.get(0);		
		
		long employeeId = employee.getOID();
		Date periodStartDate = period.getStartDate();
		//long periodId = period.getOID();
		String strFullName = employee.getFullName();
		//System.out.println("period.getPeriod()  "+period.getOID());

		Vector rowx = new Vector();		
		rowx.add(period.getPeriod());
		rowx.add(strFullName);
		
		if(SystemProperty.SYS_PROP_SCHEDULE_PERIOD != SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL)
		{
			/*GregorianCalendar periodStart = new GregorianCalendar(yearStartDate, monthStartDate-1, dateStartDate);
        	int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			if(maxDayOfMonth == 28){
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD26())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD27())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD28())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD29())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD30())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD31())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD1())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD2())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD3())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD4())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD5())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD6())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD7())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD8())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD9())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD10())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD11())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD12())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD13())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD14())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD15())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD16())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD17())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD18())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD19())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD20())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD21())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD22())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD23())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD24())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD25())));
			}*/
			/*else if (maxDayOfMonth == 29) {
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD26())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD27())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD28())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD29())));
			/*rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD30())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD31())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD1())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD2())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD3())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD4())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD5())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD6())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD7())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD8())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD9())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD10())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD11())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD12())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD13())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD14())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD15())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD16())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD17())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD18())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD19())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD20())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD21())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD22())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD23())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD24())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD25())));
			}*/
			/*else if (maxDayOfMonth==30){
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD26())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD27())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD28())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD29())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD30())));
			/*rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD31())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD1())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD2())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD3())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD4())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD5())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD6())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD7())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD8())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD9())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD10())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD11())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD12())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD13())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD14())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD15())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD16())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD17())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD18())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD19())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD20())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD21())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD22())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD23())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD24())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD25())));
			}*/
			/*else if(maxDayOfMonth == 31){
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD26())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD27())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD28())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD29())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD30())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD31())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD1())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD2())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD3())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD4())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD5())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD6())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD7())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD8())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD9())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD10())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD11())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD12())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD13())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD14())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD15())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD16())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD17())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD18())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD19())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD20())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD21())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD22())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD23())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD24())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD25())));
			}
			else{
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD26())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD27())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD28())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD29())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD30())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD31())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD1())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD2())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD3())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD4())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD5())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD6())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD7())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD8())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD9())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD10())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD11())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD12())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD13())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD14())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD15())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD16())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD17())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD18())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD19())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD20())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD21())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD22())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD23())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD24())));
			rowx.add(scheduleSymbol.get(String.valueOf(empSchedule.getD25())));
			}*/
		}
		else
		{			
			Date transDate = new Date();				
			long dpApplicationOid = 0;
			long leaveApplicationOid = 0;
			int dpApplicationStatus = -1;
			String strScheduleSymbol ="";
			long idScheduleSymbol1 =0;
			long idScheduleSymbol2 =0;
			// untuk start date pertama kali
			startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));						
			startDatePeriod = startDatePeriod -1;
			GregorianCalendar periodStart = new GregorianCalendar(yearStartDate, monthStartDate-1, dateStartDate);
                        int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			for(int j = 0 ; j < maxDayOfMonth; j++) {
				if(startDatePeriod == maxDayOfMonth){
					startDatePeriod = 1;
					idScheduleSymbol1 = PstEmpSchedule.getSchedule(startDatePeriod,employeeId,periodStartDate);
					idScheduleSymbol2 = PstEmpSchedule.getSchedule2(startDatePeriod,employeeId,periodStartDate);
					strScheduleSymbol = ""+scheduleSymbol.get(""+idScheduleSymbol1) + (idScheduleSymbol2==0 ? "" : "/"+scheduleSymbol.get(""+idScheduleSymbol2));
					if(hashLeaveSchedule.get(""+idScheduleSymbol1) != null)
					{
						transDate = new Date(periodStartDate.getYear(), periodStartDate.getMonth(), periodStartDate.getDate()+1);				
                                                String strLeave = String.valueOf(hashLeaveSchedule.get(""+idScheduleSymbol1));	
						if(strLeave.equalsIgnoreCase("DP"))
						{
							DpApplication obDpApplication = PstDpApplication.getDpApplicationBySchedule(transDate, employeeId);
							dpApplicationOid = obDpApplication.getOID(); 									
							dpApplicationStatus = obDpApplication.getDocStatus();
							//strScheduleSymbol = "<a href=\"javascript:cmdDpApp('"+dpApplicationOid+"','"+employeeId+"','"+(periodStartDate.getYear()+1900)+"','"+(periodStartDate.getMonth()+1)+"','"+(periodStartDate.getDate()+1)+"')\" >"+ ( (dpApplicationOid==0 || (dpApplicationOid!=0 && dpApplicationStatus==PstDpApplication.FLD_DOC_STATUS_INCOMPLATE)) ? "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>" : "<font color=\"#000000\">"+strScheduleSymbol+"</font>") +"</a>";
                                                        strScheduleSymbol = ""+ ( (dpApplicationOid==0 || (dpApplicationOid!=0 && dpApplicationStatus==PstDpApplication.FLD_DOC_STATUS_INCOMPLATE)) ? "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>" : "<font color=\"#000000\">"+strScheduleSymbol+"</font>");																							
						}
						else
						{
							PstLeaveApplication objPstLeaveApplication = new PstLeaveApplication();
							int leaveType = getLeaveType(hashLeaveSchedule, idScheduleSymbol1);
							for(int it=0; it<objPstLeaveApplication.strApplicationType.length; it++)
							{
								leaveApplicationOid = objPstLeaveApplication.getLeaveApplicationIdBySchedule(it, transDate, employeeId);
								if(leaveApplicationOid != 0)
								{
									break;
								}
							}
                                                        //strScheduleSymbol = "<a href=\"javascript:cmdLeaveApp('"+leaveApplicationOid+"','"+employeeId+"','"+leaveType+"')\" >"+ (leaveApplicationOid!=0 ? "<font color=\"#000000\">"+strScheduleSymbol+"</font>" : "<font color=\"#FF0000\"><b>"+strScheduleSymbol+"</b></font>") +"</a>";																												
							strScheduleSymbol = ""+ (leaveApplicationOid!=0 ? "<font color=\"#000000\">"+strScheduleSymbol+"</font>" : "<font color=\"#FF0000\"><b>"+strScheduleSymbol+"</b></font>");																
						}
					}
					rowx.add(strScheduleSymbol);
	
				}
				else {
					startDatePeriod = startDatePeriod +1;
					idScheduleSymbol1 = PstEmpSchedule.getSchedule(startDatePeriod,employeeId,periodStartDate);
					idScheduleSymbol2 = PstEmpSchedule.getSchedule2(startDatePeriod,employeeId,periodStartDate);
					strScheduleSymbol = ""+scheduleSymbol.get(""+idScheduleSymbol1) + (idScheduleSymbol2==0 ? "" : "/"+scheduleSymbol.get(""+idScheduleSymbol2));
					//System.out.println("strScheduleSymbol"+strScheduleSymbol);
					if(hashLeaveSchedule.get(""+idScheduleSymbol1) != null)
					{
						transDate = new Date(periodStartDate.getYear(), periodStartDate.getMonth(), periodStartDate.getDate()+1);				
						String strLeave = String.valueOf(hashLeaveSchedule.get(""+idScheduleSymbol1));	
						if(strLeave.equalsIgnoreCase("DP"))
						{
							DpApplication obDpApplication = PstDpApplication.getDpApplicationBySchedule(transDate, employeeId);
							dpApplicationOid = obDpApplication.getOID(); 									
							dpApplicationStatus = obDpApplication.getDocStatus();
							//strScheduleSymbol = "<a href=\"javascript:cmdDpApp('"+dpApplicationOid+"','"+employeeId+"','"+(periodStartDate.getYear()+1900)+"','"+(periodStartDate.getMonth()+1)+"','"+(periodStartDate.getDate()+1)+"')\" >"+ ( (dpApplicationOid==0 || (dpApplicationOid!=0 && dpApplicationStatus==PstDpApplication.FLD_DOC_STATUS_INCOMPLATE)) ? "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>" : "<font color=\"#000000\">"+strScheduleSymbol+"</font>") +"</a>";	
                                                        strScheduleSymbol = ""+ ( (dpApplicationOid==0 || (dpApplicationOid!=0 && dpApplicationStatus==PstDpApplication.FLD_DOC_STATUS_INCOMPLATE)) ? "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>" : "<font color=\"#000000\">"+strScheduleSymbol+"</font>");																						
						}
						else
						{
							PstLeaveApplication objPstLeaveApplication = new PstLeaveApplication();
							int leaveType = getLeaveType(hashLeaveSchedule, idScheduleSymbol1);
							for(int it=0; it<objPstLeaveApplication.strApplicationType.length; it++)
							{
								leaveApplicationOid = objPstLeaveApplication.getLeaveApplicationIdBySchedule(it, transDate, employeeId);
								if(leaveApplicationOid != 0)
								{
									break;
								}
							}													
							//strScheduleSymbol = "<a href=\"javascript:cmdLeaveApp('"+leaveApplicationOid+"','"+employeeId+"','"+leaveType+"')\" >"+ (leaveApplicationOid!=0 ? "<font color=\"#000000\">"+strScheduleSymbol+"</font>" : "<font color=\"#FF0000\"><b>"+strScheduleSymbol+"</b></font>") +"</a>";																
                                                        strScheduleSymbol = ""+ (leaveApplicationOid!=0 ? "<font color=\"#000000\">"+strScheduleSymbol+"</font>" : "<font color=\"#FF0000\"><b>"+strScheduleSymbol+"</b></font>");																
						}
					}
					rowx.add(strScheduleSymbol);
				}
				}
												
		}
		lstData.add(rowx);
		lstLinkData.add(String.valueOf(empSchedule.getOID()));
	}
	return ctrlist.draw();
}

public String drawListNew(Vector objectClass)
{	
	Date now = new Date();	
	int monthStartDate = Integer.parseInt(String.valueOf(now.getMonth()));
	int yearStartDate =  Integer.parseInt(String.valueOf(now.getYear()+1900));
	int dateStartDate =  Integer.parseInt(String.valueOf(now.getDate()));
	int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
	
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("Period","8%");
	ctrlist.addHeader("Employee","11%");
        
        Hashtable hSysLeaveDP = new Hashtable();
        Hashtable hSysLeaveSP = new Hashtable();
        Hashtable hSysLeaveLL = new Hashtable();
        Hashtable hSysLeaveAL = new Hashtable();
        
        hSysLeaveDP = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT);
        hSysLeaveSP = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE);
        hSysLeaveLL = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_LONG_LEAVE);
        hSysLeaveAL = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE);
        
        Hashtable hLeave = new Hashtable(1,1);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT),hSysLeaveDP);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE),hSysLeaveSP);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_LONG_LEAVE),hSysLeaveLL);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE),hSysLeaveAL);
        
	//untuk period
	for (int i = 0; i < objectClass.size(); i++) 
	{
		Vector temp = (Vector) objectClass.get(i);
		Employee employee = (Employee) temp.get(1);
		Period period = (Period) temp.get(2);
		EmpSchedule empSchedule = (EmpSchedule)temp.get(0);		
		
		//long employeeId = employee.getOID();
		Date periodStartDate = period.getStartDate();
		monthStartDate = periodStartDate.getMonth()+1;
		yearStartDate =  periodStartDate.getYear()+1900;
		dateStartDate =  periodStartDate.getDate();
		//String strFullName = employee.getFullName();
	}	
	
	if(SystemProperty.SYS_PROP_SCHEDULE_PERIOD != SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL)
	{		
		ctrlist.addHeader("21","2%");
		ctrlist.addHeader("22","2%");
		ctrlist.addHeader("23","2%");
		ctrlist.addHeader("24","2%");
		ctrlist.addHeader("25","2%");
		ctrlist.addHeader("26","2%");
		ctrlist.addHeader("27","2%");
		ctrlist.addHeader("28","2%");
		ctrlist.addHeader("29","2%");
		ctrlist.addHeader("30","2%");
		ctrlist.addHeader("31","2%");
		ctrlist.addHeader("1","2%");
		ctrlist.addHeader("2","2%");
		ctrlist.addHeader("3","2%");
		ctrlist.addHeader("4","2%");
		ctrlist.addHeader("5","2%");
		ctrlist.addHeader("6","2%");
		ctrlist.addHeader("7","2%");
		ctrlist.addHeader("8","2%");
		ctrlist.addHeader("9","2%");
		ctrlist.addHeader("10","2%");
		ctrlist.addHeader("11","2%");
		ctrlist.addHeader("12","2%");
		ctrlist.addHeader("13","2%");
		ctrlist.addHeader("14","2%");
		ctrlist.addHeader("15","2%");
		ctrlist.addHeader("16","2%");
		ctrlist.addHeader("17","2%");
		ctrlist.addHeader("18","2%");
		ctrlist.addHeader("19","2%");				
		ctrlist.addHeader("20","2%");	
	}
	else
	{	
		
		//System.out.println("monthStartDate  "+monthStartDate);
		GregorianCalendar periodStart = new GregorianCalendar(yearStartDate, monthStartDate-1, dateStartDate);
                int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);	
		
		ctrlist.addHeader(""+startDatePeriod,"2%");
		for(int i = 0 ; i < maxDayOfMonth-1 ; i++) {
				if(startDatePeriod == maxDayOfMonth){
					startDatePeriod = 1;
					ctrlist.addHeader(""+startDatePeriod,"2%");
				}
				else {
					startDatePeriod = startDatePeriod +1;
					ctrlist.addHeader(""+startDatePeriod,"2%");
				}
				
		}
	}

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();

	// store data schedule symbol into Hashtable object
	Hashtable scheduleSymbol = new Hashtable();
	Vector listScd = PstScheduleSymbol.list(0, 0, "", "");
	scheduleSymbol.put("0", "-");
	for (int ls = 0; ls < listScd.size(); ls++) 
	{
		ScheduleSymbol scd = (ScheduleSymbol) listScd.get(ls);
		scheduleSymbol.put(String.valueOf(scd.getOID()), scd.getSymbol());
	}
	
        startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));						
	startDatePeriod = startDatePeriod -1;
        GregorianCalendar periodStart = new GregorianCalendar(yearStartDate, monthStartDate-1, dateStartDate);
        int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        
	for (int i = 0; i < objectClass.size(); i++) 
	{
		Vector temp = (Vector) objectClass.get(i);
                EmpSchedule empSchedule = (EmpSchedule)temp.get(0);
		Employee employee = (Employee) temp.get(1);
		Period period = (Period) temp.get(2);				
		
		long employeeId = employee.getOID();
		Date periodStartDate = period.getStartDate();
		//long periodId = period.getOID();
		String strFullName = employee.getFullName() + " / "+ employee.getEmployeeNum();
		//System.out.println("period.getPeriod()  "+period.getOID());

		Vector rowx = new Vector();		
		rowx.add(period.getPeriod());
		rowx.add(strFullName);
		
                Vector vDpAppDetail = SessDpAppDetail.listDpApplication(employeeId, period.getStartDate());
                
		if(SystemProperty.SYS_PROP_SCHEDULE_PERIOD != SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL)
		{
		//Not define yet
		}
		else
		{			
			Date transDate = new Date();				
			//long dpApplicationOid = 0;
			long leaveApplicationOid = 0;
			//int dpApplicationStatus = -1;
			String strScheduleSymbol ="";
			long idScheduleSymbol1 =0;
			long idScheduleSymbol2 =0;
			// untuk start date pertama kali
			
			
			for(int j = 0 ; j < maxDayOfMonth; j++) {
                                
				if(startDatePeriod == maxDayOfMonth){
					startDatePeriod = 1;
					idScheduleSymbol1 = PstEmpSchedule.getSchedule(startDatePeriod,employeeId,periodStartDate);
					idScheduleSymbol2 = PstEmpSchedule.getSchedule2(startDatePeriod,employeeId,periodStartDate);
					strScheduleSymbol = ""+scheduleSymbol.get(""+idScheduleSymbol1) + (idScheduleSymbol2==0 ? "" : "/"+scheduleSymbol.get(""+idScheduleSymbol2));
					//Add by Artha
                                        int typeSymbol = getLeaveSchType(hLeave, idScheduleSymbol1);
                                        if(typeSymbol>0)
					{
						transDate = new Date(periodStartDate.getYear(), periodStartDate.getMonth(), startDatePeriod);				
						if(typeSymbol == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT){
							DpAppDetail dpAppDetail = SessDpAppDetail.getDpApp(vDpAppDetail, transDate);
                                                        DpAppMain dpAppMain = new DpAppMain();
                                                        if(dpAppDetail.getOID()>0){
							    try{
								dpAppMain = PstDpAppMain.fetchExc(dpAppDetail.getDpAppMainId());
                                                            }catch(Exception ex){}
                                                        }
                                                                strScheduleSymbol = "<a href=\"javascript:cmdDpAppMain('"+dpAppDetail.getOID()+"','"+employeeId+"','"+transDate.getTime()+"')\" >"+ 
                                                                ((dpAppDetail.getOID()<=0) ? 
                                                                    "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>" : 
                                                                    (dpAppMain.getApprovalId()>0?
                                                                    "<font color=\"#16830d\">"+strScheduleSymbol+"</font>":
                                                                    "<font color=\"#ff6600\">"+strScheduleSymbol+"</font>")) 
                                                                +"</a>";												
						}
                                                
                                                
						if(typeSymbol == PstScheduleCategory.CATEGORY_LONG_LEAVE){
                                                    LLAppMain llAppMain = new LLAppMain();
                                                    llAppMain = SessLLAppMain.getLLAppMain(employeeId, transDate);
                                                    strScheduleSymbol = "<a href=\"javascript:cmdLLAppMain('"+llAppMain.getOID()+"','"+employeeId+"','"+transDate.getTime()+"')\" >"+ 
                                                                ((llAppMain.getOID()<=0) ? 
                                                                    "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>" : 
                                                                    (llAppMain.getApprovalId()>0?
                                                                    "<font color=\"#16830d\">"+strScheduleSymbol+"</font>":
                                                                    "<font color=\"#ff6600\">"+strScheduleSymbol+"</font>")) 
                                                                +"</a>";	
                                                }
                                                
                                                if(typeSymbol == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE
                                                   || typeSymbol == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                                   ){
                                                       
                                                        String where = PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_EMPLOYEE_ID] + 
                                                                       "=" + employeeId + " AND " +
                                                                       PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_TAKEN_DATE] +
                                                                       "='" + Formater.formatDate(transDate, "yyyy-MM-dd") + "'" + " AND " +
                                                                       PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_SYMBOL_ID] +
                                                                       "=" + typeSymbol;
                                                        
                                                        Vector list = PstSpecialLeaveStock.list(0, 0, where, "");
                                                        SpecialLeave special = new SpecialLeave();
                                                        
                                                       
                                                        if(list != null && list.size() >0) {
                                                            SpecialLeaveStock stock = (SpecialLeaveStock)list.firstElement();                                                            
                                                            leaveApplicationOid = stock.getSpecialLeaveId();
                                                            
                                                            try {
                                                               special = PstSpecialLeave.fetchExc(leaveApplicationOid);
                                                            }
                                                            catch(Exception e) {}
                                                        }
                                                        else {
                                                            leaveApplicationOid = 0;
                                                        }
                                                  
                                                	strScheduleSymbol = "<a href=\"javascript:cmdLeaveAppMain('"+period.getOID() + "','"+leaveApplicationOid+"','"+employeeId+"')\" >"+ 
                                                               
                                                                ((leaveApplicationOid <= 0) ? 
                                                                    "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>" : 
                                                                    (special.getApproval3Id() > 0?
                                                                        "<font color=\"#16830d\">"+strScheduleSymbol+"</font>":
                                                                        "<font color=\"#ff6600\">"+strScheduleSymbol+"</font>"
                                                                    ))
                                                               
                                                                
                                                                +"</a>";
                                                                
                                                                //(leaveApplicationOid!=0 ? "<font color=\"#000000\">"+strScheduleSymbol+"</font>" : "<font color=\"#FF0000\"><b>"+strScheduleSymbol+"</b></font>") +"</a>";
                                                }
                                                
						
					}
					rowx.add(strScheduleSymbol);
	
				}
				else {
					startDatePeriod = startDatePeriod +1;
					idScheduleSymbol1 = PstEmpSchedule.getSchedule(startDatePeriod,employeeId,periodStartDate);
					idScheduleSymbol2 = PstEmpSchedule.getSchedule2(startDatePeriod,employeeId,periodStartDate);
					strScheduleSymbol = ""+scheduleSymbol.get(""+idScheduleSymbol1) + (idScheduleSymbol2==0 ? "" : "/"+scheduleSymbol.get(""+idScheduleSymbol2));
					//System.out.println("strScheduleSymbol"+strScheduleSymbol);
					//Add by Artha
                                        int typeSymbol = getLeaveSchType(hLeave, idScheduleSymbol1);
                                        //if(hashLeaveSchedule.get(""+idScheduleSymbol1) != null)
                                        if(typeSymbol>0)
					{
						transDate = new Date(periodStartDate.getYear(), periodStartDate.getMonth(), startDatePeriod);				
						if(typeSymbol == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT){
                                                        DpAppDetail dpAppDetail = new DpAppDetail();
                                                        dpAppDetail = SessDpAppDetail.getDpApp(vDpAppDetail, transDate);
                                                        DpAppMain dpAppMain = new DpAppMain();
                                                        if(dpAppDetail.getOID()>0){
							    try{
								dpAppMain = PstDpAppMain.fetchExc(dpAppDetail.getDpAppMainId());
                                                            }catch(Exception ex){}
                                                        }
							strScheduleSymbol = "<a href=\"javascript:cmdDpAppMain('"+dpAppDetail.getOID()+"','"+employeeId+"','"+transDate.getTime()+"')\" >"+ 
                                                                ((dpAppDetail.getOID()<=0) ? 
                                                                    "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>" : 
                                                                    (dpAppMain.getApprovalId()>0?
                                                                    "<font color=\"#16830d\">"+strScheduleSymbol+"</font>":
                                                                    "<font color=\"#ff6600\">"+strScheduleSymbol+"</font>")) 
                                                                +"</a>";												
						}
                                                
						if(typeSymbol == PstScheduleCategory.CATEGORY_LONG_LEAVE){
                                                    LLAppMain llAppMain = new LLAppMain();
                                                    llAppMain = SessLLAppMain.getLLAppMain(employeeId, transDate);
                                                    strScheduleSymbol = "<a href=\"javascript:cmdLLAppMain('"+llAppMain.getOID()+"','"+employeeId+"','"+transDate.getTime()+"')\" >"+ 
                                                                ((llAppMain.getOID()<=0) ? 
                                                                    "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>" : 
                                                                    (llAppMain.getApprovalId()>0?
                                                                    "<font color=\"#16830d\">"+strScheduleSymbol+"</font>":
                                                                    "<font color=\"#ff6600\">"+strScheduleSymbol+"</font>")) 
                                                                +"</a>";	
                                                }
                                                
                                                if(typeSymbol == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE             
                                                   || typeSymbol == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                                   ){
                                                    
                                                        String where = PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_EMPLOYEE_ID] + 
                                                                       "=" + employeeId + " AND " +
                                                                       PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_TAKEN_DATE] +
                                                                       "='" + Formater.formatDate(transDate, "yyyy-MM-dd") + "'" + " AND " +
                                                                       PstSpecialLeaveStock.fieldNames[PstSpecialLeaveStock.FLD_SYMBOL_ID] +
                                                                       "=" + idScheduleSymbol1;
                                                        
                                                        System.out.println(where);
                                                        
                                                        Vector list = PstSpecialLeaveStock.list(0, 0, where, "");
                                                        SpecialLeave special = new SpecialLeave();
                                                        
                                                       
                                                        if(list != null && list.size() >0) {
                                                            SpecialLeaveStock stock = (SpecialLeaveStock)list.firstElement();                                                            
                                                            leaveApplicationOid = stock.getSpecialLeaveId();
                                                            
                                                            try {
                                                                special = PstSpecialLeave.fetchExc(leaveApplicationOid);
                                                            }
                                                            catch(Exception e) {}
                                                        }
                                                        else {
                                                            leaveApplicationOid = 0;
                                                        }
                                                  
                                                	strScheduleSymbol = "<a href=\"javascript:cmdLeaveAppMain('"+period.getOID() + "','"+leaveApplicationOid+"','"+employeeId+"')\" >"+ 
                                                               
                                                                ((leaveApplicationOid <= 0) ? 
                                                                    "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>" : 
                                                                    (special.getApproval3Id() > 0?
                                                                        "<font color=\"#16830d\">"+strScheduleSymbol+"</font>":
                                                                        "<font color=\"#ff6600\">"+strScheduleSymbol+"</font>"
                                                                    ))
                                                               
                                                                
                                                               +"</a>";
                                                                
                                                                //(leaveApplicationOid!=0 ? "<font color=\"#000000\">"+strScheduleSymbol+"</font>" : "<font color=\"#FF0000\"><b>"+strScheduleSymbol+"</b></font>") +"</a>";
                                                	//strScheduleSymbol = "<a href=\"javascript:cmdLeaveAppMain('"+period.getOID() + "','"+leaveApplicationOid+"','"+employeeId+"')\" >"+ (leaveApplicationOid!=0 ? "<font color=\"#000000\">"+strScheduleSymbol+"</font>" : "<font color=\"#FF0000\"><b>"+strScheduleSymbol+"</b></font>") +"</a>";
                                                }
                                                
					}
					rowx.add(strScheduleSymbol);
				}
				}
												
		}
		lstData.add(rowx);
		lstLinkData.add(String.valueOf(empSchedule.getOID()));
	}
	return ctrlist.draw();
}



public String drawListSchedule(JspWriter outObj, Vector objectClass)
{	
	Date now = new Date();	
	int monthStartDate = Integer.parseInt(String.valueOf(now.getMonth()));
	int yearStartDate =  Integer.parseInt(String.valueOf(now.getYear()+1900));
	int dateStartDate =  Integer.parseInt(String.valueOf(now.getDate()));
	int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
	
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	        
        ctrlist.dataFormat("PERIOD","8%","center","center");
        ctrlist.dataFormat("EMPLOYEE","8%","center","center");
        
        Hashtable hSysLeaveDP = new Hashtable();
        Hashtable hSysLeaveSP = new Hashtable();
        Hashtable hSysLeaveLL = new Hashtable();
        Hashtable hSysLeaveAL = new Hashtable();
        
        hSysLeaveDP = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT);
        hSysLeaveSP = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE);
        hSysLeaveLL = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_LONG_LEAVE);
        hSysLeaveAL = SessEmpSchedule.listScheduleOID(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE);
        
        Hashtable hLeave = new Hashtable(1,1);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT),hSysLeaveDP);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE),hSysLeaveSP);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_LONG_LEAVE),hSysLeaveLL);
	hLeave.put(String.valueOf(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE),hSysLeaveAL);
        
	//untuk period
	if(objectClass!=null && objectClass.size()>0)
	{ int i=0; //for (int i = 0; i < objectClass.size(); i++) 
		Vector temp = (Vector) objectClass.get(i);
		Employee employee = (Employee) temp.get(1);
		Period period = (Period) temp.get(2);		
		Date periodStartDate = period.getStartDate();
		monthStartDate = periodStartDate.getMonth()+1;
		yearStartDate =  periodStartDate.getYear()+1900;
		dateStartDate =  periodStartDate.getDate();
		
	}	
	
	if(SystemProperty.SYS_PROP_SCHEDULE_PERIOD != SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL)
	{		
		ctrlist.addHeader("21","2%");
		ctrlist.addHeader("22","2%");
		ctrlist.addHeader("23","2%");
		ctrlist.addHeader("24","2%");
		ctrlist.addHeader("25","2%");
		ctrlist.addHeader("26","2%");
		ctrlist.addHeader("27","2%");
		ctrlist.addHeader("28","2%");
		ctrlist.addHeader("29","2%");
		ctrlist.addHeader("30","2%");
		ctrlist.addHeader("31","2%");
		ctrlist.addHeader("1","2%");
		ctrlist.addHeader("2","2%");
		ctrlist.addHeader("3","2%");
		ctrlist.addHeader("4","2%");
		ctrlist.addHeader("5","2%");
		ctrlist.addHeader("6","2%");
		ctrlist.addHeader("7","2%");
		ctrlist.addHeader("8","2%");
		ctrlist.addHeader("9","2%");
		ctrlist.addHeader("10","2%");
		ctrlist.addHeader("11","2%");
		ctrlist.addHeader("12","2%");
		ctrlist.addHeader("13","2%");
		ctrlist.addHeader("14","2%");
		ctrlist.addHeader("15","2%");
		ctrlist.addHeader("16","2%");
		ctrlist.addHeader("17","2%");
		ctrlist.addHeader("18","2%");
		ctrlist.addHeader("19","2%");				
		ctrlist.addHeader("20","2%");	
	}else{	
		
		GregorianCalendar periodStart = new GregorianCalendar(yearStartDate, monthStartDate-1, dateStartDate);
                int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);	
		
                ctrlist.dataFormat(""+startDatePeriod,"2%","center","center");
		for(int i = 0 ; i < maxDayOfMonth-1 ; i++) {
				if(startDatePeriod == maxDayOfMonth){
					startDatePeriod = 1;
					
                                        ctrlist.dataFormat(""+startDatePeriod,"2%","center","center");
				}
				else {
					startDatePeriod = startDatePeriod +1;
                                        ctrlist.dataFormat(""+startDatePeriod,"2%","center","center");
					
				}
				
		}
	}
        
        
	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	
	Hashtable scheduleSymbol = new Hashtable();
	Vector listScd = PstScheduleSymbol.list(0, 0, "", "");
	scheduleSymbol.put("0", "-");
	for (int ls = 0; ls < listScd.size(); ls++) 
	{
		ScheduleSymbol scd = (ScheduleSymbol) listScd.get(ls);
		scheduleSymbol.put(String.valueOf(scd.getOID()), scd.getSymbol());
	}
	int index = -1;
        
	startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));// POINT						
	startDatePeriod = startDatePeriod -1;
	GregorianCalendar periodStart = new GregorianCalendar(yearStartDate, monthStartDate-1, dateStartDate); // POINT	
        int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH); // POINT	
        
	for (int i = 0; i < objectClass.size(); i++) 
	{
		Vector temp = (Vector) objectClass.get(i);
                EmpSchedule empSchedule = (EmpSchedule)temp.get(0);
		Employee employee = (Employee) temp.get(1);
		Period period = (Period) temp.get(2);				
		                
                long employeeId = employee.getOID();
		Date periodStartDate = period.getStartDate();
                
                EmpSchedule objEmpSchedule = new EmpSchedule();
                
                try{
                    objEmpSchedule = PstEmpSchedule.listSchedule(employeeId,periodStartDate);
                }catch(Exception e){
                    System.out.println("Exception "+e.toString());
                }
                
		String strFullName = employee.getFullName()+" / "+ employee.getEmployeeNum();

		Vector rowx = new Vector();		
		rowx.add("<input type=\"hidden\" name=\"size\" value=\""+empSchedule.getOID()+ "\">"+"<input type=\"hidden\" name=\"emp_schedule_id_"+i+"\" value=\""+empSchedule.getOID()+ "\">"+period.getPeriod());
		rowx.add(strFullName);
		
		if(SystemProperty.SYS_PROP_SCHEDULE_PERIOD != SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL)
		{
                    //Not define yet
		}
		else
		{			
			String strScheduleSymbol ="";
			long idScheduleSymbol1 =0;
			long idScheduleSymbol2 =0;
                        
			for(int j = 0 ; j < maxDayOfMonth; j++){
                                
				if(startDatePeriod == maxDayOfMonth){
                                    
					startDatePeriod = 1;
                                        
                                        ScheduleD1D2 scheduleD1D2 = new ScheduleD1D2();
                                        
                                        scheduleD1D2 = PstEmpSchedule.getSch(objEmpSchedule,startDatePeriod);
                                        
                                        if(scheduleD1D2 != null){
                                            try{
                                                idScheduleSymbol1 = scheduleD1D2.getD();
                                            }catch(Exception e){
                                                idScheduleSymbol1 = 0;
                                            }
                                        
                                            try{
                                                idScheduleSymbol2 = scheduleD1D2.getD2Nd();
                                            }catch(Exception e){
                                                idScheduleSymbol2 = 0;
                                            }
                                        }
					
					strScheduleSymbol = ""+scheduleSymbol.get(""+idScheduleSymbol1) + (idScheduleSymbol2==0 ? "" : "/"+scheduleSymbol.get(""+idScheduleSymbol2));
					
                                        int typeSymbol = getLeaveSchType(hLeave, idScheduleSymbol1);
                                        
                                        if(typeSymbol>0)
					{
						
						if(typeSymbol == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT){
							
                                                    strScheduleSymbol = "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>";												
						}
                                                
                                                
						if(typeSymbol == PstScheduleCategory.CATEGORY_LONG_LEAVE){                                                    
                                                    strScheduleSymbol = "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>";	
                                                }
                                                
                                                if(typeSymbol == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE || typeSymbol == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE){
                                                       
                                                    strScheduleSymbol = "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>";
                                                                
                                                }
					}
					rowx.add(strScheduleSymbol);
	
				}else {
					
                                        startDatePeriod = startDatePeriod +1;
                                        ScheduleD1D2 scheduleD1D2 = new ScheduleD1D2();
                                        
                                        scheduleD1D2 = PstEmpSchedule.getSch(objEmpSchedule,startDatePeriod);
                                        
                                        if(scheduleD1D2 != null){
                                            try{
                                                idScheduleSymbol1 = scheduleD1D2.getD();
                                            }catch(Exception e){
                                                idScheduleSymbol1 = 0;
                                            }
                                        
                                            try{
                                                idScheduleSymbol2 = scheduleD1D2.getD2Nd();
                                            }catch(Exception e){
                                                idScheduleSymbol2 = 0;
                                            }
                                        }
					strScheduleSymbol = ""+scheduleSymbol.get(""+idScheduleSymbol1) + (idScheduleSymbol2==0 ? "" : "/"+scheduleSymbol.get(""+idScheduleSymbol2));
					
                                        int typeSymbol = getLeaveSchType(hLeave, idScheduleSymbol1);
                                       
                                        if(typeSymbol>0)
					{
                                                			
						if(typeSymbol == PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT){                                                       
                                                        
							strScheduleSymbol = "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>";												
                                                        
						}
                                                
						if(typeSymbol == PstScheduleCategory.CATEGORY_LONG_LEAVE){
                                                    
                                                    strScheduleSymbol = "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>";	
                                                }
                                                
                                                if(typeSymbol == PstScheduleCategory.CATEGORY_SPECIAL_LEAVE || typeSymbol == PstScheduleCategory.CATEGORY_ANNUAL_LEAVE ){
                                                    
                                                	strScheduleSymbol = "<font color=\"#FF0000\">"+strScheduleSymbol+"</font>";
                                                               
                                                }
                                                
					}
					rowx.add(strScheduleSymbol);
				}
				}
												
		}                
		lstData.add(rowx);
		lstLinkData.add(String.valueOf(empSchedule.getOID()));
	}	
        try{
            ctrlist.drawMe(outObj,index);
        }catch(Exception e){
            System.out.println("Exception "+e.toString());
        }
        return "";
}


//Artha ": cek type
public int getLeaveSchType(Hashtable hLeave, long leaveOid){
	int type = -1;
	
	String key = String.valueOf(leaveOid);

        Hashtable hSysLeaveDP = new Hashtable();
        Hashtable hSysLeaveSP = new Hashtable();
        Hashtable hSysLeaveLL = new Hashtable();
        Hashtable hSysLeaveAL = new Hashtable();
        
	hSysLeaveDP = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT));
	hSysLeaveSP = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_SPECIAL_LEAVE));
	hSysLeaveLL = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_LONG_LEAVE));
	hSysLeaveAL = (Hashtable)hLeave.get(String.valueOf(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE));
        
        if(hSysLeaveDP.containsKey(key)){
		return PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT;
	}else if(hSysLeaveSP.containsKey(key)){
		return PstScheduleCategory.CATEGORY_SPECIAL_LEAVE;
	}else if(hSysLeaveLL.containsKey(key)){
		return PstScheduleCategory.CATEGORY_LONG_LEAVE;
	}else if(hSysLeaveAL.containsKey(key)){
		return PstScheduleCategory.CATEGORY_ANNUAL_LEAVE;
	}
        
	return type;
}

public int getLeaveType(Hashtable hashData, long leaveOid)
{
	int result = 0;	
	String strResult = String.valueOf(hashData.get(""+leaveOid));
	
	if(strResult.equalsIgnoreCase("AL"))
	{
		result = PstLeaveApplication.LEAVE_APPLICATION_AL;
	}	

	if(strResult.equalsIgnoreCase("LL"))
	{
		result = PstLeaveApplication.LEAVE_APPLICATION_LL;
	}	

	if(strResult.equalsIgnoreCase("MATERNITY"))
	{
		result = PstLeaveApplication.LEAVE_APPLICATION_MATERNITY;
	}	

	if(strResult.equalsIgnoreCase("DC"))
	{
		result = PstLeaveApplication.LEAVE_APPLICATION_DC;
	}	

	if(strResult.equalsIgnoreCase("SPEC"))
	{
		result = PstLeaveApplication.LEAVE_APPLICATION_SPECIAL;
	}	

	if(strResult.equalsIgnoreCase("UNPAID"))
	{
		result = PstLeaveApplication.LEAVE_APPLICATION_UNPAID;
	}	
	
	return result;
}
%>

<%
// pengecekan apakah user yang login adalah sekretaris atau tidak

int countLevel = 0;

try{
    countLevel = PstLevel.getCount(null);
}catch(Exception E){
    System.out.println("excption "+E.toString());
}

Vector vLevelList = new Vector();
String orderCnt = PstLevel.fieldNames[PstLevel.FLD_LEVEL]+" ASC ";

            try{
                vLevelList = PstLevel.list(0, 0, "", orderCnt);
            }catch(Exception E){
                System.out.println("excption "+E.toString());
            }

            String[] levelId = null;

            levelId = new String[countLevel];

            int max1 = 0;
            for(int j = 0 ; j < countLevel ; j++){

                Level objLevel = new Level();
                objLevel = (Level)vLevelList.get(j);

                String name = "LEVL_"+objLevel.getOID();
                levelId[j] = FRMQueryString.requestString(request,name);
                max1++;
            }

boolean isSecretaryLogin = (positionType>=PstPosition.LEVEL_SECRETARY) ? true : false;

ControlLine ctrLine = new ControlLine();
CtrlEmpSchedule ctrlEmpSchedule = new CtrlEmpSchedule(request);
long oidEmpSchedule = FRMQueryString.requestLong(request, "hidden_emp_schedule_id");

int iErrCode = FRMMessage.ERR_NONE;
String msgStr = "";
int iCommand = FRMQueryString.requestCommand(request);
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int start = FRMQueryString.requestInt(request, "start");
int recordToGet = 15;
int vectSize = 0;
String whereClause = "";

SrcEmpSchedule srcEmpSchedule = new SrcEmpSchedule();
FrmSrcEmpSchedule frmSrcEmpSchedule = new FrmSrcEmpSchedule(request, srcEmpSchedule);
frmSrcEmpSchedule.requestEntityObject(srcEmpSchedule);

// command yang diterima dari editor page karena proses delete
if(iCommand == Command.DELETE)
{
	iCommand = Command.BACK;
}

if((iCommand==Command.NEXT)||(iCommand==Command.FIRST)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand == Command.BACK))
{
	 try
	 {		  
		srcEmpSchedule = (SrcEmpSchedule)session.getValue(SessEmpSchedule.SESS_SRC_EMPSCHEDULE); 
                levelId = (String[])session.getValue("ses_level"); 
	 }
	 catch(Exception e)
	 { 
		srcEmpSchedule = new SrcEmpSchedule();
	 }
}

SessEmpSchedule sessEmpSchedule = new SessEmpSchedule();
session.putValue(SessEmpSchedule.SESS_SRC_EMPSCHEDULE, srcEmpSchedule);
session.putValue("ses_level", levelId);

Vector records = new Vector();
    vectSize = sessEmpSchedule.getCountSearch(srcEmpSchedule, levelId);

if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)||(iCommand==Command.LAST)||(iCommand==Command.LIST)||(iCommand == Command.BACK))
{
    start = ctrlEmpSchedule.actionList(iCommand, start, vectSize, recordToGet);
}
    records = sessEmpSchedule.searchEmpSchedule(srcEmpSchedule, levelId , start, recordToGet);


//Vector records = sessEmpSchedule.searchEmpSchedule(srcEmpSchedule, start, recordToGet);
//Vector records = sessEmpSchedule.searchEmpSchedule(srcEmpSchedule, levelId , start, recordToGet); // diganti | 2015-01-24

%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Working Schedule</title>
<script language="JavaScript">
	function cmdPrint(){
		window.open("<%=approot%>/servlet/EmpScheduleList");
	}

	function cmdPrintXLS(){
		window.open("<%=approot%>/servlet/com.dimata.harisma.report.EmpScheduleListXLS", "Report") //, "status=yes,toolbar=no,menubar=yes,location=no");
                //window.location.href = "< %=approot%>/servlet/com.dimata.harisma.report.EmpScheduleListXLS";
	}

	function cmdAdd(){
		document.frm_empschedule.command.value="<%=String.valueOf(Command.ADD)%>";
		document.frm_empschedule.action="empschedule_edit.jsp";
		document.frm_empschedule.submit();
	}

	function cmdEdit(oid){
		document.frm_empschedule.command.value="<%=String.valueOf(Command.EDIT)%>";
                //document.frm_empschedule.prev_command.value="< % =Command.EDIT%>";
                document.frm_empschedule.hidden_emp_schedule_id.value = oid;
		document.frm_empschedule.action="empschedule_edit.jsp";
		document.frm_empschedule.submit();
	}

	function cmdListFirst(){
		document.frm_empschedule.command.value="<%=String.valueOf(Command.FIRST)%>";
		document.frm_empschedule.action="empschedule_list.jsp";
		document.frm_empschedule.submit();
	}

	function cmdListPrev(){
		document.frm_empschedule.command.value="<%=String.valueOf(Command.PREV)%>";
		document.frm_empschedule.action="empschedule_list.jsp";
		document.frm_empschedule.submit();
	}

	function cmdListNext(){
		document.frm_empschedule.command.value="<%=String.valueOf(Command.NEXT)%>";
		document.frm_empschedule.action="empschedule_list.jsp";
		document.frm_empschedule.submit();
	}

	function cmdListLast(){
		document.frm_empschedule.command.value="<%=String.valueOf(Command.LAST)%>";
		document.frm_empschedule.action="empschedule_list.jsp";
		document.frm_empschedule.submit();
	}

	function cmdBack(){
		document.frm_empschedule.command.value="<%=String.valueOf(Command.BACK)%>";
		document.frm_empschedule.action="srcempschedule.jsp";
		document.frm_empschedule.submit();
	}
	
	function fnTrapKD(){
	//alert(event.keyCode);
		switch(event.keyCode) {
			case <%=String.valueOf(LIST_PREV)%>:
				cmdListPrev();
				break;
			case <%=String.valueOf(LIST_NEXT)%>:
				cmdListNext();
				break;
			case <%=String.valueOf(LIST_FIRST)%>:
				cmdListFirst();
				break;
			case <%=String.valueOf(LIST_LAST)%>:
				cmdListLast();
				break;
			default:
				break;
		}
	}
	

	function cmdDpApp(oidLeave,oidEmployee,takenYr,takenMn,takenDt){
		if(oidLeave != 0)
		{
			document.frm_empschedule.command.value="<%=String.valueOf(Command.EDIT)%>";			
		}
		else
		{
			document.frm_empschedule.command.value="<%=String.valueOf(Command.ADD)%>";		
		}		
		document.frm_empschedule.hidden_dp_application_id.value = oidLeave;
		document.frm_empschedule.<%=FrmDpApplication.fieldNames[FrmDpApplication.FRM_FLD_EMPLOYEE_ID]%>.value = oidEmployee;
		document.frm_empschedule.<%=FrmDpApplication.fieldNames[FrmDpApplication.FRM_FLD_TAKEN_DATE]%>_yr.value = takenYr;
		document.frm_empschedule.<%=FrmDpApplication.fieldNames[FrmDpApplication.FRM_FLD_TAKEN_DATE]%>_mn.value = takenMn;
		document.frm_empschedule.<%=FrmDpApplication.fieldNames[FrmDpApplication.FRM_FLD_TAKEN_DATE]%>_dy.value = takenDt;						
		//document.frm_empschedule.action="<%//=approot%>//employee/leave/dp_application_edit_fr_schld.jsp";
		//document.frm_empschedule.submit();
	}
         
        function cmdDpAppMain(oidDetail,oidEmployee,longDate){
		if(oidDetail != 0)
		{
			document.frm_empschedule.command.value="<%=String.valueOf(Command.EDIT)%>";			
		}
		else
		{
			document.frm_empschedule.command.value="<%=String.valueOf(Command.ADD)%>";		
		}					
		//document.frm_empschedule.action="<%//=approot%>//employee/leave/dp_application_edit_fr_schld.jsp?oidDpAppDetail="+oidDetail+"&oidEmployee="+oidEmployee+"&lTakenDate="+longDate;
		//document.frm_empschedule.submit();
	}

        function cmdDpAppMain(oidDetail,oidEmployee,longDate){
		if(oidDetail != 0)
		{
			document.frm_empschedule.command.value="<%=String.valueOf(Command.EDIT)%>";			
		}
		else
		{
			document.frm_empschedule.command.value="<%=String.valueOf(Command.ADD)%>";		
		}					
		//document.frm_empschedule.action="<%//=approot%>//employee/leave/dp_application_edit_fr_schld.jsp?oidDpAppDetail="+oidDetail+"&oidEmployee="+oidEmployee+"&lTakenDate="+longDate;
		//document.frm_empschedule.submit();
	}
         
        function cmdLLAppMain(oidMain,oidEmployee,longDate){
		if(oidMain != 0)
		{
			document.frm_empschedule.command.value="<%=String.valueOf(Command.EDIT)%>";			
		}
		else
		{
			document.frm_empschedule.command.value="<%=String.valueOf(Command.ADD)%>";		
		}					
		//document.frm_empschedule.action="<%//=approot%>///employee/leave/ll_application_edit_fr_schld.jsp?oidLLAppMain="+oidMain+"&oidEmployee="+oidEmployee+"&lTakenDate="+longDate;
		//document.frm_empschedule.submit();
	}



	function cmdLeaveAppMain(oidPeriod, oidLeave,oidEmployee){
		if(oidLeave != 0)
		{
			document.frm_empschedule.command.value="<%=String.valueOf(Command.EDIT)%>";			
		}
		else
		{
			document.frm_empschedule.command.value="<%=String.valueOf(Command.ADD)%>";  	
		}	 
                //document.frm_empschedule.action="<%//=approot%>//employee/leave/leave_request.jsp?oid_leave="+oidLeave+"&oid_employee="+oidEmployee+"&oid_period="+oidPeriod;
		//document.frm_empschedule.submit();
	}
         
        function cmdLeaveApp(oidLeave,oidEmployee,leavetype){
		if(oidLeave != 0)
		{
			document.frm_empschedule.command.value="<%=String.valueOf(Command.EDIT)%>";			
		}
		else
		{
			document.frm_empschedule.command.value="<%=String.valueOf(Command.ADD)%>";  	
		}		
		document.frm_empschedule.hidden_leave_application_id.value = oidLeave;  
		document.frm_empschedule.<%=FrmDpApplication.fieldNames[FrmDpApplication.FRM_FLD_EMPLOYEE_ID]%>.value = oidEmployee;
		document.frm_empschedule.LEAVE_APP_TYPE.value = leavetype;  
		//document.frm_empschedule.action="<%//=approot%>//employee/leave/leave_application_edit_fr_schld.jsp";
		//document.frm_empschedule.submit();
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

</SCRIPT>
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Attendance &gt; Working Schedule List<!-- #EndEditable --> </strong></font> 
                </td>
              </tr>
              <tr> 
                <td> 
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td  style="background-color:<%=bgColorContent%>; "> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                          <tr> 
                            <td valign="top"> 
                              <table  style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                    <form name="frm_empschedule" method="post" action="">  
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                      <input type="hidden" name="hidden_emp_schedule_id" value="<%=String.valueOf(oidEmpSchedule)%>">
                                      <input type="hidden" name="prev_command" value="<%=String.valueOf(prevCommand)%>">
                                     
									  <!-- utk dp application -->
                                      <input type="hidden" name="hidden_dp_application_id" value="">
                                      <input type="hidden" name="<%=FrmDpApplication.fieldNames[FrmDpApplication.FRM_FLD_TAKEN_DATE]%>_yr" value="">
                                      <input type="hidden" name="<%=FrmDpApplication.fieldNames[FrmDpApplication.FRM_FLD_TAKEN_DATE]%>_mn" value="">
                                      <input type="hidden" name="<%=FrmDpApplication.fieldNames[FrmDpApplication.FRM_FLD_TAKEN_DATE]%>_dy" value="">									  									  									  
									  
                                      <input type="hidden" name="hidden_leave_application_id" value="">									  
									  <input type="hidden" name="<%=FrmDpApplication.fieldNames[FrmDpApplication.FRM_FLD_EMPLOYEE_ID]%>" value="">									  
									  <input type="hidden" name="LEAVE_APP_TYPE" value="">									  									  									  
                                      <table border="0" width="100%">
                                        <tr> 
                                          <td height="8" width="100%" class="listtitle">Working 
                                            Schedule List</td>
                                        </tr>
                                      </table>
                                      <%
                                        //if((listEmpSchedule!=null)&&(listEmpSchedule.size()>0)){
                                        if((records!=null)&&(records.size()>0)){
                                        %>
                                      <%//=drawList(records)%> 
                                      <%//=drawListNew(records)%> 
                                      <%
                                        drawListSchedule(out,records);
                                      %> 
                                      <%}
										else{
										%>
                                      <span class="comment"><br>
                                      &nbsp;Records is empty ...</span> 
                                      <%}%>
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td> 
                                            <table width="100%" cellspacing="0" cellpadding="3">
                                              <tr> 
                                                <td> 
                                                <% 
												ctrLine.setLocationImg(approot+"/images");
												ctrLine.initDefault();
												
												int listCommand = iCommand;
												if(listCommand == Command.BACK)
												{
													listCommand = Command.LIST;
												}
												%>
                                                <%=ctrLine.drawImageListLimit(listCommand,vectSize,start,recordToGet)%></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="46%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="46%" nowrap align="left" class="command"> 
                                            <%-- <a href="javascript:cmdAdd()">Add New</a> | <a href="javascript:cmdBack()">Back to search</a> --%>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap> <a href="javascript:cmdBack()" class="command">Back 
                                                  To Search Schedule </a></td>
                                                <% if(privAdd && isSecretaryLogin){%>
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="24"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add New"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap><a href="javascript:cmdAdd()" class="command">Add 
                                                  New Working Schedule</a></td>
                                                <%}
												if(privPrint){%><%--
                                                <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                <td width="24"><a href="javascript:cmdPrint()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1002','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1002" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap><a href="javascript:cmdPrint()" class="command">Print 
                                                  Schedule</a></td>--%>
                                                <%}%>
												<%
												if(privPrint && isSecretaryLogin && records.size()>0)
												{
												%>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdPrintXLS()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image100','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image100" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Print"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap><a href="javascript:cmdPrintXLS()" class="command">Export to Excel</a></td>
												<%
												}
												%>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
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
<script language="JavaScript">
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>