 
<% 
/* 
 * Page Name  		:  absence_list.jsp
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
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ABSENCE_MANAGEMENT, AppObjInfo.OBJ_ABSENCE_MANAGEMENT); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privPrint=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<!-- Jsp Block -->
<%!
public String drawListAbsence(Vector objectClass, int maxDateOfMonth, int start, Date startPeriod){
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No","3%", "2", "0");		
	ctrlist.addHeader("Payroll","6%","2","0");
	ctrlist.addHeader("Employee","21%","2","0");
	ctrlist.addHeader("Absence's Period","70%","0","31");
	int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));						
	//Date now = new Date();
	int monthStartDate = Integer.parseInt(String.valueOf(startPeriod.getMonth()+1));
	int yearStartDate =  Integer.parseInt(String.valueOf(startPeriod.getYear()+1900));
	int dateStartDate =  Integer.parseInt(String.valueOf(startPeriod.getDate()));
	GregorianCalendar periodStart = new GregorianCalendar(yearStartDate, monthStartDate-1, dateStartDate);
    int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
	//System.out.println("maxDayOfMonth::';:" +maxDayOfMonth);
	 maxDateOfMonth = maxDayOfMonth;
	ctrlist.addHeader(""+(startDatePeriod),"2%","0","0");
	for(int it=0; it<maxDateOfMonth-1; it++)
	{	
		if(startDatePeriod ==maxDayOfMonth){
			startDatePeriod = 1;
			ctrlist.addHeader(""+(startDatePeriod),"2%","0","0");
		} 
		else{
			startDatePeriod = startDatePeriod + 1;
			ctrlist.addHeader(""+(startDatePeriod),"2%","0","0");
		}
			
	}				

	ctrlist.setLinkRow(1);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEditAbsence('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();

	PstEmpSchedule objPstEmpSchedule = new PstEmpSchedule();
	SessEmpSchedule objSessEmpSchedule = new SessEmpSchedule();
	
	for (int i = 0; i < objectClass.size(); i++) {
		Vector temp = (Vector) objectClass.get(i);
		EmpSchedule empSchedule = (EmpSchedule)temp.get(0);
		Employee employee = (Employee) temp.get(1);
		
		Vector rowx = new Vector();
		
		rowx.add(String.valueOf(start+i+1));
		rowx.add(employee.getEmployeeNum());
		rowx.add(employee.getFullName());
		long employeeId = 0;
		EmpSchedule emp = new EmpSchedule();
		try
		{
			emp = PstEmpSchedule.fetchExc(empSchedule.getOID());
			employeeId = emp.getEmployeeId();
			
		}
		catch(Exception e)
		{
			System.out.println("Exc when fetch absence period : "+e.toString());
		}
		System.out.println("employeeId   "+employeeId);
		int status1 = 0;
		int status2 = 0;
		int reason1 = 0;
		startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));						
		startDatePeriod = startDatePeriod -1;
		for(int j = 0 ;j < maxDayOfMonth; j++){
			if(startDatePeriod == maxDayOfMonth){
			  startDatePeriod=1;
			  status1 = PstEmpSchedule.getStatus1(startDatePeriod,employeeId,startPeriod);
			  reason1 = PstEmpSchedule.getReason1(startDatePeriod,employeeId,startPeriod);
			  System.out.println("reason1   "+reason1);
			  int intReasonCode1 = objSessEmpSchedule.getAbsenceReason(status1, reason1); 
			  String strReasonCode1 = intReasonCode1 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode1] : "";				
			  rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','"+startDatePeriod+"')\"><font color=\"#30009D\">" + strReasonCode1 + "</font></a></div>");
			}
			else {
			  startDatePeriod = startDatePeriod +1;
			  status1 = PstEmpSchedule.getStatus1(startDatePeriod,employeeId,startPeriod);
			  reason1 = PstEmpSchedule.getReason1(startDatePeriod,employeeId,startPeriod);
			  System.out.println("reason1   "+reason1);
			  int intReasonCode1 = objSessEmpSchedule.getAbsenceReason(status1,reason1); 
			  String strReasonCode1 = intReasonCode1 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode1] : "";				
			  rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','"+startDatePeriod+"')\"><font color=\"#30009D\">" + strReasonCode1 + "</font></a></div>");
			}
		}
		
		
		/*int intReasonCode1 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus26(), empSchedule.getReason26()); 
		String strReasonCode1 = intReasonCode1 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode1] : "";				
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','1')\"><font color=\"#30009D\">" + strReasonCode1 + "</font></a></div>");
		
		int intReasonCode2 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus2(), empSchedule.getReason2()); 
		String strReasonCode2 = intReasonCode2 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode2] : ""; 						
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','2')\"><font color=\"#30009D\">" + strReasonCode2 + "</font></a></div>");
		
		int intReasonCode3 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus3(), empSchedule.getReason3()); 
		String strReasonCode3 = intReasonCode3 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode3] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','3')\"><font color=\"#30009D\">" + strReasonCode3 + "</font></a></div>");

		int intReasonCode4 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus4(), empSchedule.getReason4()); 
		String strReasonCode4 = intReasonCode4 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode4] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','4')\"><font color=\"#30009D\">" + strReasonCode4 + "</font></a></div>");

		int intReasonCode5 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus5(), empSchedule.getReason5()); 
		String strReasonCode5 = intReasonCode5 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode5] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','5')\"><font color=\"#30009D\">" + strReasonCode5 + "</font></a></div>");

		int intReasonCode6 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus6(), empSchedule.getReason6()); 
		String strReasonCode6 = intReasonCode6 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode6] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','6')\"><font color=\"#30009D\">" + strReasonCode6 + "</font></a></div>");

		int intReasonCode7 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus7(), empSchedule.getReason7()); 
		String strReasonCode7 = intReasonCode7 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode7] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','7')\"><font color=\"#30009D\">" + strReasonCode7 + "</font></a></div>");

		int intReasonCode8 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus8(), empSchedule.getReason8()); 
		String strReasonCode8 = intReasonCode8 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode8] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','8')\"><font color=\"#30009D\">" + strReasonCode8 + "</font></a></div>");

		int intReasonCode9 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus9(), empSchedule.getReason9()); 
		String strReasonCode9 = intReasonCode9 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode9] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','9')\"><font color=\"#30009D\">" + strReasonCode9 + "</font></a></div>");

		int intReasonCode10 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus10(), empSchedule.getReason10()); 
		String strReasonCode10 = intReasonCode10 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode10] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','10')\"><font color=\"#30009D\">" + strReasonCode10 + "</font></a></div>");

		int intReasonCode11 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus11(), empSchedule.getReason11()); 
		String strReasonCode11 = intReasonCode11 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode11] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','11')\"><font color=\"#30009D\">" + strReasonCode11 + "</font></a></div>");

		int intReasonCode12 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus12(), empSchedule.getReason12()); 
		String strReasonCode12 = intReasonCode12 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode12] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','12')\"><font color=\"#30009D\">" + strReasonCode12 + "</font></a></div>");

		int intReasonCode13 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus13(), empSchedule.getReason13()); 
		String strReasonCode13 = intReasonCode13 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode13] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','13')\"><font color=\"#30009D\">" + strReasonCode13 + "</font></a></div>");

		int intReasonCode14 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus14(), empSchedule.getReason14()); 
		String strReasonCode14 = intReasonCode14 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode14] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','14')\"><font color=\"#30009D\">" + strReasonCode14 + "</font></a></div>");

		int intReasonCode15 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus15(), empSchedule.getReason15()); 
		String strReasonCode15 = intReasonCode15 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode15] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','15')\"><font color=\"#30009D\">" + strReasonCode15 + "</font></a></div>");
		
		int intReasonCode16 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus16(), empSchedule.getReason16()); 
		String strReasonCode16 = intReasonCode16 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode16] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','16')\"><font color=\"#30009D\">" + strReasonCode16 + "</font></a></div>");

		int intReasonCode17 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus17(), empSchedule.getReason17()); 
		String strReasonCode17 = intReasonCode17 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode17] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','17')\"><font color=\"#30009D\">" + strReasonCode17 + "</font></a></div>");

		int intReasonCode18 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus18(), empSchedule.getReason18()); 
		String strReasonCode18 = intReasonCode18 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode18] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','18')\"><font color=\"#30009D\">" + strReasonCode18 + "</font></a></div>");
		
		int intReasonCode19 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus19(), empSchedule.getReason19()); 
		String strReasonCode19 = intReasonCode19 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode19] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','19')\"><font color=\"#30009D\">" + strReasonCode19 + "</font></a></div>");
		
		int intReasonCode20 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus20(), empSchedule.getReason20()); 
		String strReasonCode20 = intReasonCode20 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode20] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','20')\"><font color=\"#30009D\">" + strReasonCode20 + "</font></a></div>");

		int intReasonCode21 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus21(), empSchedule.getReason21()); 
		String strReasonCode21 = intReasonCode21 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode21] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','21')\"><font color=\"#30009D\">" + strReasonCode21 + "</font></a></div>");

		int intReasonCode22 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus22(), empSchedule.getReason22()); 
		String strReasonCode22 = intReasonCode22 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode22] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','22')\"><font color=\"#30009D\">" + strReasonCode22 + "</font></a></div>");

		int intReasonCode23 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus23(), empSchedule.getReason23()); 
		String strReasonCode23 = intReasonCode23 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode23] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','23')\"><font color=\"#30009D\">" + strReasonCode23 + "</font></a></div>");

		int intReasonCode24 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus24(), empSchedule.getReason24()); 
		String strReasonCode24 = intReasonCode24 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode24] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','24')\"><font color=\"#30009D\">" + strReasonCode24 + "</font></a></div>");

		int intReasonCode25 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus25(), empSchedule.getReason25()); 
		String strReasonCode25 = intReasonCode25 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode25] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','25')\"><font color=\"#30009D\">" + strReasonCode25 + "</font></a></div>");

		int intReasonCode26 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus26(), empSchedule.getReason26()); 
		String strReasonCode26 = intReasonCode26 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode26] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','26')\"><font color=\"#30009D\">" + strReasonCode26 + "</font></a></div>");

		int intReasonCode27 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus27(), empSchedule.getReason27()); 
		String strReasonCode27 = intReasonCode27 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode27] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','27')\"><font color=\"#30009D\">" + strReasonCode27 + "</font></a></div>");

		int intReasonCode28 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus28(), empSchedule.getReason28()); 
		String strReasonCode28 = intReasonCode28 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode28] : ""; 								
		rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','28')\"><font color=\"#30009D\">" + strReasonCode28 + "</font></a></div>");
	

		if(maxDateOfMonth>=29)
		{		
			int intReasonCode29 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus29(), empSchedule.getReason29()); 
			String strReasonCode29 = intReasonCode29 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode29] : ""; 								
			rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','29')\"><font color=\"#30009D\">" + strReasonCode29 + "</font></a></div>");
		}
	
		if(maxDateOfMonth>=30)
		{					
			int intReasonCode30 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus30(), empSchedule.getReason30()); 
			String strReasonCode30 = intReasonCode30 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode30] : ""; 								
			rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','30')\"><font color=\"#30009D\">" + strReasonCode30 + "</font></a></div>");
		}
		
		if(maxDateOfMonth==31)
		{				
			int intReasonCode31 = objSessEmpSchedule.getAbsenceReason(empSchedule.getStatus31(), empSchedule.getReason31()); 
			String strReasonCode31 = intReasonCode31 > -1 ? objPstEmpSchedule.strAbsenceReasonCode[intReasonCode31] : ""; 								
			rowx.add("<div align=\"center\"><a style=\"text-decoration:none\" href =\"javascript:cmdEditAbsence('"+empSchedule.getOID()+"','31')\"><font color=\"#30009D\">" + strReasonCode31 + "</font></a></div>");																																																														
		}*/	
					
		lstData.add(rowx);			
		lstLinkData.add(String.valueOf(empSchedule.getOID())+"','0");

	}

	return ctrlist.drawList();
}
%>

<%
ControlLine ctrLine = new ControlLine();
CtrlEmpSchedule ctrlEmpSchedule = new CtrlEmpSchedule(request);

int iCommand = FRMQueryString.requestCommand(request);
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
int start = FRMQueryString.requestInt(request, "start");
long oidEmpSchedule = FRMQueryString.requestLong(request, "hidden_absence_id");
int intDateEmpSchedule = FRMQueryString.requestInt(request, "hidden_absence_date");

int iErrCode = FRMMessage.ERR_NONE;
String msgStr = "";
int recordToGet = 15;
int vectSize = 0;
String whereClause = "";

SrcEmpSchedule srcEmpSchedule = new SrcEmpSchedule();
FrmSrcEmpSchedule frmSrcEmpSchedule = new FrmSrcEmpSchedule(request, srcEmpSchedule);
if(iCommand==Command.FIRST || iCommand==Command.PREV || iCommand==Command.NEXT || iCommand==Command.LAST || iCommand==Command.BACK)
{
	 try
	 { 
		srcEmpSchedule = (SrcEmpSchedule)session.getValue("ABSENCE_MANAGEMENT"); 
	 }
	 catch(Exception e)
	 { 
		srcEmpSchedule = new SrcEmpSchedule();
	 }
}
else
{
	frmSrcEmpSchedule.requestEntityObject(srcEmpSchedule);
	session.putValue("ABSENCE_MANAGEMENT", srcEmpSchedule);	
}


SessEmpSchedule sessEmpSchedule = new SessEmpSchedule();
vectSize = sessEmpSchedule.countEmployeeAbsence(srcEmpSchedule);

// get maximum count of date on selected period
Period absencePeriod = new Period();
int maxDateOfMonth = 31;   
if(srcEmpSchedule.getPeriod().compareToIgnoreCase("0") > 0)
{
	try
	{
		absencePeriod = PstPeriod.fetchExc(Long.parseLong(srcEmpSchedule.getPeriod()));
		maxDateOfMonth = absencePeriod.getEndDate().getDate();
		
	}
	catch(Exception e)
	{
		System.out.println("Exc when fetch absence period : "+e.toString());
	}
}
if(iCommand==Command.FIRST || iCommand==Command.NEXT || iCommand==Command.PREV || iCommand==Command.LAST || iCommand==Command.LIST || iCommand==Command.BACK)
{
	start = ctrlEmpSchedule.actionList(iCommand, start, vectSize, recordToGet);
	if(iCommand==Command.BACK)
	{
		iCommand = Command.LIST;
	}
}

//Vector result1 = sessEmpSchedule.listEmployeeAbsence(srcEmpSchedule, start, recordToGet);
Vector result = sessEmpSchedule.listEmployeeAbsence(srcEmpSchedule, start, recordToGet);
%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Absence Management</title>
<script language="JavaScript">
function cmdEditAbsence(oid,absencedate){
	document.frm_absence.command.value="<%=Command.EDIT%>";
	document.frm_absence.hidden_absence_id.value = oid;
	document.frm_absence.max_date_of_month.value = "<%=maxDateOfMonth%>";	
	document.frm_absence.hidden_absence_date.value = absencedate;	
	document.frm_absence.action="absence_edit.jsp";
	document.frm_absence.submit();
}

function cmdListFirst(){
	document.frm_absence.command.value="<%=Command.FIRST%>";
	document.frm_absence.action="absence_list.jsp";
	document.frm_absence.submit();
}

function cmdListPrev(){
	document.frm_absence.command.value="<%=Command.PREV%>";
	document.frm_absence.action="absence_list.jsp";
	document.frm_absence.submit();
}

function cmdListNext(){
	document.frm_absence.command.value="<%=Command.NEXT%>";
	document.frm_absence.action="absence_list.jsp";
	document.frm_absence.submit();
}

function cmdListLast(){
	document.frm_absence.command.value="<%=Command.LAST%>";
	document.frm_absence.action="absence_list.jsp";
	document.frm_absence.submit();
}

function cmdBack(){
	document.frm_absence.command.value="<%=Command.BACK%>";
	document.frm_absence.action="srcabsence.jsp";
	document.frm_absence.submit();
}

function fnTrapKD(){
	switch(event.keyCode) {
		case <%=LIST_PREV%>:
			cmdListPrev();
			break;
		case <%=LIST_NEXT%>:
			cmdListNext();
			break;
		case <%=LIST_FIRST%>:
			cmdListFirst();
			break;
		case <%=LIST_LAST%>:
			cmdListLast();
			break;
		default:
			break;
	}
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
<!-- #EndEditable --> 
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> 
                  Attendance &gt;Absence Management<!-- #EndEditable --> </strong></font> 
                </td>
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
                                    <form name="frm_absence" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start"  value="<%=start%>">
                                      <input type="hidden" name="hidden_absence_id" value="<%=oidEmpSchedule%>">
                                      <input type="hidden" name="hidden_absence_date" value="<%=intDateEmpSchedule%>">  									  
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="max_date_of_month" value="<%=maxDateOfMonth%>">									  
                                      <%
									  if((result!=null)&&(result.size()>0))									  
									  {
									  	//out.println("maxDateOfMonth1  "+absencePeriod.getStartDate());
										Date startPeriod = absencePeriod.getStartDate();
										out.println(drawListAbsence(result,maxDateOfMonth,start,startPeriod));
									  }
									  else
									  {
									  	out.println("<span class=\"comment\"><br>&nbsp;Records is empty ...</span>");
									  }									  
									  %>
                                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                          <td> 
                                            <table width="100%" cellspacing="0" cellpadding="3">
                                              <tr> 
                                                <td> 
                                                  <% 
												  ctrLine.setLocationImg(approot+"/images");
												  ctrLine.initDefault();
												  out.println(ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet));
	  											  %>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
										
										<%
										if(result!=null && result.size()>0)
										{   										
										%>
                                        <tr>
                                          <td width="46%"> 
                                            <table border="0" cellspacing="0"cellpadding="1" bgcolor="#E0EDF0" width="99%" align="center">
                                              <tr> 
                                                <td width="1%"><i><b><font color="#FF0000">AB</font></b></i></td>
                                                <td width="1%"><i><b><font color="#FF0000">=</font></b></i></td>
                                                <td width="13%"><i><b><font color="#FF0000">Absence</font></b></i></td>
                                                <td width="1%"><i><b><font color="#FF0000"></font></b></i></td>
                                                <td width="1%"><i><b><font color="#FF0000">DC</font></b></i></td>
                                                <td width="1%"><i><b><font color="#FF0000">=</font></b></i></td>
                                                <td width="14%"><i><b><font color="#FF0000">Sickness</font></b></i></td>
                                                <td width="1%"><i><b><font color="#FF0000"></font></b></i></td>
                                                <td width="1%"><i><b><font color="#FF0000">SD</font></b></i></td>
                                                <td width="1%"><i><b><font color="#FF0000">=</font></b></i></td>
                                                <td width="65%"><i><b><font color="#FF0000">Special 
                                                  Dispensation</font></b></i></td>
                                              </tr>
                                            </table>
										  </td>
                                        </tr>              
										<%
										}
										%>
										
                                        <tr> 
                                          <td width="46%">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                          <td width="46%" nowrap align="left" class="command"> 
                                            <table border="0" cellspacing="0" cellpadding="0" width="288">
                                              <tr> 
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To Search Employee's Absence"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                <td nowrap> <a href="javascript:cmdBack()" class="command">Back 
                                                  To Search Employee's Absence</a></td>
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --> 
<script language="JavaScript">
	var oBody = document.body;
	var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
</script>
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
