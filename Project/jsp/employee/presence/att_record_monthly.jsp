<%@page import="com.dimata.harisma.entity.attendance.PstEmpSchedule"%>
<% 
/* 
 * Page Name  		:  srcpresence.jsp
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
<%@ page import = "java.text.*" %>
<%//@ page import = "java.sql.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<%@ page import = "java.util.Date" %>

<!--package hris -->
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<%// int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE , AppObjInfo.G2_ATTENDANCE   , AppObjInfo.OBJ_PRESENCE   	); %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_STAFF_CONTROL_REPORT, AppObjInfo.OBJ_ATTENDANCE_RECORD_REPORT);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//    boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//    boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//    boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
//    boolean privPrint=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<!-- Jsp Block -->
<%!    int DATA_NULL = 0;
int DATA_PRINT = 1;

/**
 * get working duration of actual In and Out presence
 * @param dtActualIn => date actual IN 
 * @param dtActualOut => date actual OUT  
 * @return String of working duration in Hour and Minutes format 
 */
public String getWorkingDuration(Date dtActualIn, Date dtActualOut) 
{
	String result = "";
	if(	dtActualIn!=null && dtActualOut!=null ){
		long iDurTimeIn = dtActualIn.getTime()/1000;
		long iDurTimeOut = dtActualOut.getTime()/1000;	
                
                if(iDurTimeOut<iDurTimeIn)
                     iDurTimeOut=iDurTimeOut+(24*60*60);
                
		long iDuration = 0;
		if (iDurTimeIn != iDurTimeOut) 
		{
			iDuration = (iDurTimeIn == 0 || iDurTimeOut == 0) ? 0 : iDurTimeOut - iDurTimeIn;
		}
		long iDurationHour = (iDuration - (iDuration % 3600)) / 3600;
		long iDurationMin = iDuration % 3600 / 60;
	
		if( !(iDurationHour==0 && iDurationMin==0) )
		{
			String strDurationHour = (iDurationHour != 0) ? iDurationHour + "h" : "";
			String strDurationMin = (iDurationMin != 0) ? iDurationMin + "m" : "";

			if( (strDurationHour!=null && strDurationHour.length()>0) && (strDurationMin!=null && strDurationMin.length()>0) )
			{
				result = strDurationHour + ", " + strDurationMin;
			}
			else
			{
				result = strDurationHour + strDurationMin;
			}
		}	
	}			
	return result;
}

/**
 * get difference of schedule and actual In presence
 * @param dtSchedule => date schedule IN 
 * @param dtActual => date actual IN  
 * @return String of difference in Hour and Minutes format 
 */
public String getDiffIn(Date dtParam, Date dtActual) 
{
	String result = "";
	if(dtParam==null || dtActual==null)
	{
		return result;
	}
		
	// utk mengecek jika waktu di schedule adalah jam 24:00 maka dianggap sebagai jam 00:00 keesokan harinya
	Date dtSchedule = new Date(dtParam.getYear(), dtParam.getMonth(), dtParam.getDate(), dtParam.getHours(), dtParam.getMinutes());
	if(dtSchedule.getHours()==0 && dtSchedule.getMinutes()==0)
	{
		dtSchedule = new Date(dtParam.getYear(), dtParam.getMonth(), dtParam.getDate()+1, 0, 0);
	}
	
	if(dtSchedule!=null && dtActual!=null){
		dtSchedule.setSeconds(0);																			
		long iDuration = dtSchedule.getTime()/60000 - dtActual.getTime()/60000;
		long iDurationHour = (iDuration - (iDuration % 60)) / 60;
		long iDurationMin = iDuration % 60;
		if( !(iDurationHour==0 && iDurationMin==0) )
		{
			String strDurationHour = iDurationHour + "h, ";
			String strDurationMin = iDurationMin + "m";				
			result = strDurationHour + strDurationMin;
                        /*if((iDurationHour<0) || (iDurationMin<0)){
                            result="<font color=\"#CC0000\"><b>"+result+"</b></font>";
                        }*/
		}
	}
	return result;
}

/**
 * get difference of schedule and actual Out presence
 * @param dtSchedule => date schedule OUT 
 * @param dtActual => date actual OUT  
 * @return String of difference Out Hour and Minutes format 
 */
    public String getDiffOut(Date dtParam, Date dtActual) {
	String result = "";
        if (dtParam == null || dtActual == null) {
		return result;
	}

	Date dtSchedule = new Date(dtParam.getYear(), dtParam.getMonth(), dtParam.getDate(), dtParam.getHours(), dtParam.getMinutes());
        if (dtSchedule != null && dtActual != null) {
            dtSchedule.setSeconds(0);
        
                      long  iDuration = dtActual.getTime() / 60000 - dtSchedule.getTime() / 60000;
        
		long iDurationHour = (iDuration - (iDuration % 60)) / 60;
		long iDurationMin = iDuration % 60;
                        if (!(iDurationHour == 0 && iDurationMin == 0)) {
			String strDurationHour = iDurationHour + "h, ";
			String strDurationMin = iDurationMin + "m";				
			result = strDurationHour + strDurationMin;
		}
                    
          
	}
	return result;
}

/**
 * create list object
 * consist of : 
 *  first index  ==> status object (will displayed or not)
 *  second index ==> object string will displayed
 *  third index  ==> object vector of string used in report on PDF format.
 */
public Vector drawList(Vector vectEmpSchedule, int intStartDate, int intEndDate, Date startDatePeriod) 
{
	Vector result = new Vector(1,1);
	if(vectEmpSchedule!=null && vectEmpSchedule.size()>0)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("90%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("Day In","3%", "2", "0");
		ctrlist.addHeader("Date In","4%", "2", "0");
		ctrlist.addHeader("Schedule","6%", "0", "3");
		ctrlist.addHeader("Symbol","2%", "0", "0");
		ctrlist.addHeader("Time In","2%", "0", "0");
		ctrlist.addHeader("Time Out","2%", "0", "0");
		ctrlist.addHeader("Actual","2%", "0", "2");
		ctrlist.addHeader("Time In","2%", "0", "0");
		ctrlist.addHeader("Time Out","2%", "0", "0");
		ctrlist.addHeader("Difference","4%", "0", "3");
		ctrlist.addHeader("In","2%", "0", "0");
		ctrlist.addHeader("Out","2%", "0", "0");
		ctrlist.addHeader("Duration","2%", "0", "0");	
		ctrlist.addHeader("Note","4%", "0", "3");
		ctrlist.addHeader("Reason","2%", "0", "0");
		ctrlist.addHeader("Note","2%", "0", "0");
		ctrlist.addHeader("Status","2%", "0", "0");	
		
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();
			//System.out.println("nilai startDatePeriod"+startDatePeriod);
		// vector of data will used in pdf report
		Vector vectDataToPdf = new Vector(1,1);
		int startDateSet = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));											
		int monthStartDate = Integer.parseInt(String.valueOf(startDatePeriod.getMonth()+1));
		int yearStartDate =  Integer.parseInt(String.valueOf(startDatePeriod.getYear()+1900));
		int dateStartDate =  Integer.parseInt(String.valueOf(startDatePeriod.getDate()));
		GregorianCalendar periodStart = new GregorianCalendar(yearStartDate, monthStartDate-1, dateStartDate);
		int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		
		startDateSet = startDateSet -1;
		int month=startDatePeriod.getMonth();
		
                Hashtable HashReason = new  Hashtable();
                Vector  listReason = PstReason.list(0, 0, "", "REASON");
                for (int r = 0; r < listReason.size(); r++) {
                        Reason reason = (Reason) listReason.get(r);
                        HashReason.put(String.valueOf(reason.getNo()), reason.getKodeReason());
                }  
                
                Hashtable HashStatus = new  Hashtable();
                for (int s = 0; s < PstEmpSchedule.strPresenceStatus.length; s++) {
                        HashStatus.put(String.valueOf(s),PstEmpSchedule.strPresenceStatus[s]);
                }        
                        
		for (int i=0; i< maxDayOfMonth; i++) 
		{
		
			 
			//System.out.println("nilai maxDayOfMonth"+maxDayOfMonth);
			if(startDateSet == maxDayOfMonth){
				startDateSet = 1;
				month=startDatePeriod.getMonth()+1;
			}
			else{
				startDateSet = startDateSet +1;
			}
			System.out.println("vectEmpSchedule...."+vectEmpSchedule.size());
			//System.out.println("startDateSet...."+(startDateSet));
			Vector vectData = (Vector)vectEmpSchedule.get(startDateSet-1);
			//System.out.println("vectData  "+vectData);			
			long empSchedule1st = Long.parseLong(String.valueOf(vectData.get(0)));			
			Date empIn1st = (Date)vectData.get(1);			
			Date empOut1st = (Date)vectData.get(2);
			long empSchedule2nd = Long.parseLong(String.valueOf(vectData.get(3)));								
			Date empIn2nd = (Date)vectData.get(4);			
			Date empOut2nd = (Date)vectData.get(5);
			long reasonLong = Long.parseLong(String.valueOf(vectData.get(6)));	
			String note = (String.valueOf(vectData.get(7)));
			long statusLong = Long.parseLong(String.valueOf(vectData.get(8)));	
			
                        
			String reason = reasonLong == 0 ? "-":String.valueOf(HashReason.get(String.valueOf(reasonLong)));
                        if (reason == null){
                            reason = "-";
                        }
                        String status = String.valueOf(HashStatus.get(String.valueOf(statusLong)));
                        
			Date d = new Date(startDatePeriod.getYear(), month, (startDateSet));
			//Date d = new Date(String.valueOf(vectEmpSchedule.get(i)));
			
			SimpleDateFormat formatter = new SimpleDateFormat ("d MMM yyyy");
			SimpleDateFormat formatterDay = new SimpleDateFormat ("EEEE");
			String dateString = formatter.format(d);
			String dayString = formatterDay.format(d);
		
			String dateStringColor = (dayString.equalsIgnoreCase("Saturday")) ? "<font color=\"darkblue\">" + dateString + "</font>" : dateString;
			String dayStringColor = (dayString.equalsIgnoreCase("Saturday")) ? "<font color=\"darkblue\">" + dayString + "</font>" : dayString;
			dateStringColor = (dayString.equalsIgnoreCase("Sunday")) ? "<font color=\"red\">" + dateStringColor + "</font>" : dateStringColor;
			dayStringColor = (dayString.equalsIgnoreCase("Sunday")) ? "<font color=\"red\">" + dayStringColor + "</font>" : dayStringColor;		

			Vector vctSchldSymbol = PstScheduleSymbol.getScheduleData(empSchedule1st,d);
			//System.out.println("vctSchldSymbol  "+vctSchldSymbol)   ;
					
			String stSchldSymbol = "";
			int intScheduleCategory = 0;
			Date dtSchldIn = null;
			Date dtSchldOut = null;		
			if(vctSchldSymbol != null && vctSchldSymbol.size()>0)
			{
				Vector vectSchldTemp = (Vector)vctSchldSymbol.get(0);
				//System.out.println("vectSchldTemp  "+vectSchldTemp);
				stSchldSymbol = String.valueOf(vectSchldTemp.get(0));
				//System.out.println("vectSchldTemp  "+stSchldSymbol);
				intScheduleCategory = Integer.parseInt(String.valueOf(vectSchldTemp.get(1)));
				dtSchldIn = (Date)vectSchldTemp.get(2);
				dtSchldOut = (Date)vectSchldTemp.get(3);     					
			}
	
	
			// Second schedule
			Vector vctSchldSymbol2nd = PstScheduleSymbol.getScheduleData(empSchedule2nd,d);
			String stSchldSymbol2nd = "";
			int intScheduleCategory2nd = 0;
			Date dtSchldIn2nd = null;
			Date dtSchldOut2nd = null;		
			if(vctSchldSymbol2nd!=null && vctSchldSymbol2nd.size()>0)
			{
				Vector vectSchldTemp2nd = (Vector)vctSchldSymbol2nd.get(0);
				stSchldSymbol2nd = String.valueOf(vectSchldTemp2nd.get(0));
				intScheduleCategory2nd = Integer.parseInt(String.valueOf(vectSchldTemp2nd.get(1)));				
				dtSchldIn2nd = (Date)vectSchldTemp2nd.get(2);
				dtSchldOut2nd = (Date)vectSchldTemp2nd.get(3);					
			}																																
	
			
			/*System.out.println("empIn1st   : " + empIn1st);
			System.out.println("empOut1st  : " + empOut1st);
			System.out.println("dtSchldIn  : " + dtSchldIn);
			System.out.println("dtSchldOut : " + dtSchldOut);*/									
				
				
			Vector rowx = new Vector();
			Vector rowxPdf = new Vector();
			
				
			// ---> SPLIT SHIFT / EOD SCHEDULE		
			//if(intScheduleCategory==PstScheduleCategory.CATEGORY_SPLIT_SHIFT)
			if(dtSchldIn2nd!=null && dtSchldOut2nd!=null)
			{
				// ---> FIRST SCHEDULE													
				// calculate working duration
				String strDurationFirst = getWorkingDuration(empIn1st, empOut1st);
				
				// process generate string time interval for selected schedule
				String strDtSchldIn = Formater.formatTimeLocale(dtSchldIn);
				String strDtSchldOut = Formater.formatTimeLocale(dtSchldOut);
				boolean schedule1stWithoutInterval = false;				
				if( !(intScheduleCategory == PstScheduleCategory.CATEGORY_REGULAR
				     || intScheduleCategory == PstScheduleCategory.CATEGORY_SPLIT_SHIFT
				     || intScheduleCategory == PstScheduleCategory.CATEGORY_NIGHT_WORKER
				     || intScheduleCategory == PstScheduleCategory.CATEGORY_ACCROSS_DAY
				     || intScheduleCategory == PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY
					 || intScheduleCategory == PstScheduleCategory.CATEGORY_NEAR_ACCROSS_DAY))							
				{
					if(strDtSchldIn.compareTo(strDtSchldOut) == 0)
					{
						strDtSchldIn = "-";
						strDtSchldOut = "-";
						schedule1stWithoutInterval = true;						
					}
				}
				
				// calculate diffence between schedule and actual
				String strDiffIn1st = "-";
				String strDiffOut1st = "-";									
				if(!schedule1stWithoutInterval)
				{
					strDiffIn1st = getDiffIn(dtSchldIn, empIn1st);
					strDiffOut1st = getDiffOut(dtSchldOut, empOut1st);									
				}
							
				Vector rowx2nd = new Vector(1,1);
				
				rowx2nd.add(dayStringColor);  			
				rowx2nd.add(dateStringColor);					
				rowx2nd.add(""+stSchldSymbol);
						
				rowx2nd.add(strDtSchldIn);					
				rowx2nd.add(strDtSchldOut);							
				rowx2nd.add((empIn1st != null) ? Formater.formatTimeLocale(empIn1st):"");						
				rowx2nd.add((empOut1st != null) ? Formater.formatTimeLocale(empOut1st):"");															

				rowx2nd.add(strDiffIn1st.indexOf("-")>-1 ? "<font color=\"#CC0000\"><b>"+strDiffIn1st+"</b></font>" :strDiffIn1st );			
				rowx2nd.add(strDiffOut1st.indexOf("-")>-1 ? "<font color=\"#CC0000\"><b>"+strDiffOut1st+"</b></font>" : strDiffOut1st );								
				rowx2nd.add(strDurationFirst);	
                                rowx2nd.add(""+reason);		
                                rowx2nd.add(""+note);		
                                rowx2nd.add(""+status);	
				lstData.add(rowx2nd);																										
				
				Vector rowx2ndPdf = new Vector(1,1);					
				rowx2ndPdf.add(dayString);			
				rowx2ndPdf.add(dateString);					
				rowx2ndPdf.add(""+stSchldSymbol);		
				rowx2ndPdf.add(strDtSchldIn);						
				rowx2ndPdf.add(strDtSchldOut);									
				rowx2ndPdf.add((empIn1st != null) ? Formater.formatTimeLocale(empIn1st):"");									
										
				rowx2ndPdf.add((empOut1st != null) ? Formater.formatTimeLocale(empOut1st):"");															
				rowx2ndPdf.add(strDiffIn1st);			
				rowx2ndPdf.add(strDiffOut1st);	
                                rowx2ndPdf.add(strDurationFirst);
                                rowx2ndPdf.add(""+reason);	
                                rowx2ndPdf.add(""+note);	
                                rowx2ndPdf.add(""+status);		
				vectDataToPdf.add(rowx2ndPdf);
                                

			

				// ---> SECOND SCHEDULE	
				// calculate working duration
				String strDurationSecond = getWorkingDuration(empIn2nd, empOut2nd);
				
				// process generate string time interval for selected schedule
				String strDtSchldIn2nd = Formater.formatTimeLocale(dtSchldIn2nd);
				String strDtSchldOut2nd = Formater.formatTimeLocale(dtSchldOut2nd);
				boolean schedule2ndWithoutInterval = false;
				
				if( !(intScheduleCategory2nd == PstScheduleCategory.CATEGORY_REGULAR
				     || intScheduleCategory2nd == PstScheduleCategory.CATEGORY_SPLIT_SHIFT
				     || intScheduleCategory2nd == PstScheduleCategory.CATEGORY_NIGHT_WORKER
				     || intScheduleCategory2nd == PstScheduleCategory.CATEGORY_ACCROSS_DAY
				     || intScheduleCategory2nd == PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY
					 || intScheduleCategory2nd == PstScheduleCategory.CATEGORY_NEAR_ACCROSS_DAY))							
				{
					if(strDtSchldIn2nd.compareTo(strDtSchldOut2nd) == 0)
					{
						strDtSchldIn2nd = "-";
						strDtSchldOut2nd = "-";
						schedule2ndWithoutInterval = true;						
					}
				}
				
				// calculate diffence between schedule and actual
				String strDiffIn2nd = "-";
				String strDiffOut2nd = "-";									
				if(!schedule2ndWithoutInterval)
				{
					strDiffIn2nd = getDiffIn(dtSchldIn2nd, empIn2nd);
					strDiffOut2nd = getDiffOut(dtSchldOut2nd, empOut2nd);
				}									

				rowx.add("");			
				rowx.add("");					
				rowx.add(stSchldSymbol2nd);		
				rowx.add(strDtSchldIn2nd);						
				rowx.add(strDtSchldOut2nd);									
				rowx.add((empIn2nd != null) ? Formater.formatTimeLocale(empIn2nd):"");											
				rowx.add((empOut2nd != null) ? Formater.formatTimeLocale(empOut2nd):"");															
				rowx.add(strDiffIn2nd.indexOf("-")>-1 ? "<font color=\"#CC0000\"><b>"+strDiffIn2nd+"</b></font>" : strDiffIn2nd );			
				rowx.add(strDiffOut2nd.indexOf("-")>-1 ? "<font color=\"#CC0000\"><b>"+strDiffOut2nd+"</b></font>" : strDiffOut2nd );											
				rowx.add(strDurationSecond);	
                                rowx.add(""+reason);	
                                rowx.add(""+note);
                                rowx.add(""+status);
                                
				rowxPdf.add("");			
				rowxPdf.add("");					
				rowxPdf.add(stSchldSymbol2nd);		
				rowxPdf.add(strDtSchldIn2nd);						
				rowxPdf.add(strDtSchldOut2nd);									
				rowxPdf.add((empIn2nd != null) ? Formater.formatTimeLocale(empIn2nd):"");									
				rowxPdf.add((empOut2nd != null) ? Formater.formatTimeLocale(empOut2nd):"");															
				rowxPdf.add(strDiffIn2nd);			
				rowxPdf.add(strDiffOut2nd);														
				rowxPdf.add(strDurationSecond);														
				rowxPdf.add(""+reason);														
				rowxPdf.add(""+note);														
				rowxPdf.add(""+status);																																							
				}
				
				
				
				// ---> REGULAR SCHEDULE
				else
				{					
					// calculate working duration
					String strDurationFirst = getWorkingDuration(empIn1st, empOut1st);
					
					// process generate string time interval for selected schedule
					String strDtSchldIn = Formater.formatTimeLocale(dtSchldIn);
					String strDtSchldOut = Formater.formatTimeLocale(dtSchldOut);
					boolean schedule1stWithoutInterval = false;
					if(strDtSchldIn.compareTo(strDtSchldOut) == 0)
					{
						strDtSchldIn = "-";
						strDtSchldOut = "-";
						schedule1stWithoutInterval = true;						
					}
					
					// calculate diffence between schedule and actual
					String strDiffIn1st = "-";
					String strDiffOut1st = "-";									
					if(!schedule1stWithoutInterval)
					{
						strDiffIn1st = getDiffIn(dtSchldIn, empIn1st);
						strDiffOut1st = getDiffOut(dtSchldOut, empOut1st);									
					}
													
					rowx.add(dayStringColor);			
					rowx.add(dateStringColor);					
					rowx.add(stSchldSymbol);		
					rowx.add(strDtSchldIn);						
					rowx.add(strDtSchldOut);									
					rowx.add((empIn1st != null) ? Formater.formatTimeLocale(empIn1st):"");						
					rowx.add((empOut1st != null) ? Formater.formatTimeLocale(empOut1st):"");															
                                        rowx.add(strDiffIn1st.indexOf("-")>-1 ? "<font color=\"#CC0000\"><b>"+strDiffIn1st+"</b></font>" :strDiffIn1st );			
                                        rowx.add(strDiffOut1st.indexOf("-")>-1 ? "<font color=\"#CC0000\"><b>"+strDiffOut1st+"</b></font>" : strDiffOut1st );								
					rowx.add(strDurationFirst);	
                                        rowx.add(""+reason);	
                                        rowx.add(""+note);	
                                        rowx.add(""+status);
					
					rowxPdf.add(dayString);			
					rowxPdf.add(dateString);					
					rowxPdf.add(stSchldSymbol);		
					rowxPdf.add(strDtSchldIn);						
					rowxPdf.add(strDtSchldOut);									
					rowxPdf.add((empIn1st != null) ? Formater.formatTimeLocale(empIn1st):"");					
					rowxPdf.add((empOut1st != null) ? Formater.formatTimeLocale(empOut1st):"");															
					rowxPdf.add(strDiffIn1st);			
					rowxPdf.add(strDiffOut1st);									
					rowxPdf.add(strDurationFirst);														
                                        rowxPdf.add(""+reason);														
                                        rowxPdf.add(""+note);														
                                        rowxPdf.add(""+status);							
				}				
			
			lstData.add(rowx);
			vectDataToPdf.add(rowxPdf);								
			/*rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");
			rowx.add("");*/
			
		}
		
		result.add(String.valueOf(DATA_PRINT));							
		result.add(ctrlist.drawList());				
		result.add(vectDataToPdf);							
					
	}
	else
	{
		result.add(String.valueOf(DATA_NULL));					
		result.add("");				
		result.add(new Vector(1,1));																
	}
	
	return result;
}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
long gotoDept = FRMQueryString.requestLong(request, "hidden_goto_dept");
long gotoSect = FRMQueryString.requestLong(request, "hidden_goto_sect");
long gotoEmp = FRMQueryString.requestLong(request, "hidden_goto_emp");
long gotoPeriod = FRMQueryString.requestLong(request, "hidden_goto_period");
String strDepartment = FRMQueryString.requestString(request, "hidden_str_dept");
String strSection = FRMQueryString.requestString(request, "hidden_str_sect");
String strEmployee = FRMQueryString.requestString(request, "hidden_str_emp");
String strPeriod = FRMQueryString.requestString(request, "hidden_str_period");


//System.out.println("hidden_goto_sect"+gotoSect);
// get maximum date of selected period
int dateOfMonth = 0;
Date startDatePeriod = new Date();
if(gotoPeriod!=0)
{
	try
	{
		Period schedulePeriod = new Period();	
		PstPeriod pstPeriod = new PstPeriod();
		schedulePeriod = pstPeriod.fetchExc(gotoPeriod);            
		startDatePeriod = schedulePeriod.getStartDate();
		GregorianCalendar calenderWeek = new GregorianCalendar(schedulePeriod.getStartDate().getYear()+1900, schedulePeriod.getStartDate().getMonth(), 1);                
		dateOfMonth = calenderWeek.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);  
		//System.out.println("dateOfMonth  "+dateOfMonth)                   ;
	}
	catch(Exception e)
	{
		System.out.println(">>> Exc fetch Period object : " + e.toString());
	}        
}

Vector listAttendanceRecordMonthly = new Vector(1,1);
if(iCommand == Command.LIST && gotoEmp != 0)
{
	listAttendanceRecordMonthly = SessEmpSchedule.listEmpPresenceMonthlyDinamis(gotoDept, gotoPeriod, gotoEmp);
	System.out.println("listAttendanceRecordMonthly  "+listAttendanceRecordMonthly.size());
}

// process on drawlist
Vector vectResult = drawList(listAttendanceRecordMonthly, 1, dateOfMonth, startDatePeriod);
int dataStatus = Integer.parseInt(String.valueOf(vectResult.get(0)));
String listData = String.valueOf(vectResult.get(1));
Vector vectDataToPdf = (Vector)vectResult.get(2);

// design vector that handle data to store in session
Vector vectPresence = new Vector(1,1);
vectPresence.add(strDepartment);
vectPresence.add(strEmployee);
vectPresence.add(strPeriod);
vectPresence.add(vectDataToPdf);  

if(session.getValue("ATTENDANCE_RECORD_MONTHLY")!=null){
	session.removeValue("ATTENDANCE_RECORD_MONTHLY");
}
session.putValue("ATTENDANCE_RECORD_MONTHLY",vectPresence);				
%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Report Attendance Record Per Employee</title>
<script language="JavaScript">
<!--
function deptChange() {
	document.frmsrcpresence.command.value = "<%=Command.GOTO%>";
	document.frmsrcpresence.hidden_goto_dept.value = document.frmsrcpresence.DEPARTMENT_ID.value;
	
	var deptText = "";	
	for(i=0; i<document.frmsrcpresence.DEPARTMENT_ID.length; i++) {
		if(document.frmsrcpresence.DEPARTMENT_ID.options[i].selected){
			deptText = document.frmsrcpresence.DEPARTMENT_ID.options[i].text;
		}
	}	
	document.frmsrcpresence.hidden_str_dept.value = deptText;
		
	document.frmsrcpresence.hidden_goto_sect.value = "0";
	document.frmsrcpresence.hidden_goto_period.value = "0";
	document.frmsrcpresence.hidden_goto_emp.value = "0";
	document.frmsrcpresence.action = "att_record_monthly.jsp";
	document.frmsrcpresence.submit();
}

function sectChange() {
	document.frmsrcpresence.command.value = "<%=Command.GOTO%>";
	document.frmsrcpresence.hidden_goto_sect.value = document.frmsrcpresence.SECTION_ID.value;
	
	var sectText = "";	
	for(i=0; i<document.frmsrcpresence.SECTION_ID.length; i++) {
		if(document.frmsrcpresence.SECTION_ID.options[i].selected){
			sectText = document.frmsrcpresence.SECTION_ID.options[i].text;
		}
	}	
	document.frmsrcpresence.hidden_str_sect.value = sectText;	
	
	document.frmsrcpresence.hidden_goto_period.value = "0";
	document.frmsrcpresence.hidden_goto_emp.value = "0";
	document.frmsrcpresence.action = "att_record_monthly.jsp";
	document.frmsrcpresence.submit();
}


function empChange() {
	document.frmsrcpresence.command.value = "<%=Command.LIST%>";
	document.frmsrcpresence.hidden_goto_emp.value = document.frmsrcpresence.EMPLOYEE_ID.value;

	var empText = "";	
	for(i=0; i<document.frmsrcpresence.EMPLOYEE_ID.length; i++) {
		if(document.frmsrcpresence.EMPLOYEE_ID.options[i].selected){
			empText = document.frmsrcpresence.EMPLOYEE_ID.options[i].text;
		}
	}	
	document.frmsrcpresence.hidden_str_emp.value = empText;	
	document.frmsrcpresence.action = "att_record_monthly.jsp";
	document.frmsrcpresence.submit();
}

function periodChange() {
	document.frmsrcpresence.command.value = "<%=Command.LIST%>";
	document.frmsrcpresence.hidden_goto_period.value = document.frmsrcpresence.PERIOD_ID.value;

	var perText = "";	
	for(i=0; i<document.frmsrcpresence.PERIOD_ID.length; i++) {
		if(document.frmsrcpresence.PERIOD_ID.options[i].selected){
			perText = document.frmsrcpresence.PERIOD_ID.options[i].text;
		}
	}	
	document.frmsrcpresence.hidden_str_period.value = perText;	
	document.frmsrcpresence.action = "att_record_monthly.jsp";
	document.frmsrcpresence.submit();
}



function reportPdf(){	 
	var linkPage = "<%=printroot%>.report.staffcontrol.AttRecordPerEmployeePdf"; 
	//window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			
	window.open(linkPage);  				
}
//-------------- script control line -------------------
//-->
</script>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="../../styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
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
      <!-- #EndEditable --> 
    </td>
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
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" --> 
              Employee &gt; Attendance &gt; Presence &gt; View Presence<!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>; "> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                <form name="frmsrcpresence" method="post" action="">
                                  <input type="hidden" name="command" value="<%=iCommand%>">
                                  <input type="hidden" name="hidden_goto_dept" value="<%=gotoDept%>">
								  <input type="hidden" name="hidden_goto_sect" value="<%=gotoSect%>">
                                  <input type="hidden" name="hidden_goto_emp" value="<%=gotoEmp%>">
                                  <input type="hidden" name="hidden_goto_period" value="<%=gotoPeriod%>">
                                  <input type="hidden" name="hidden_str_dept" value="<%=strDepartment%>">
								  <input type="hidden" name="hidden_str_sect" value="<%=strSection%>">
                                  <input type="hidden" name="hidden_str_emp" value="<%=strEmployee%>">
                                  <input type="hidden" name="hidden_str_period" value="<%=strPeriod%>">								  
                                  <input type="hidden" name="hidden_presence_id">
								  
                                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td width="1%">&nbsp;</td>
                                                <td width="99%"> 
                                                  <table border="0" cellspacing="2" cellpadding="2" bgcolor="" width="100%">
                                                    <tr> 
                                                      <td width="10%" nowrap> 
                                                        Department </td>
                                                      <td width="1%">:</td>
                                                      <td width="89%"> 
                                                        <%
                                                        Vector dept_value = new Vector(1,1);
                                                        Vector dept_key = new Vector(1,1);
                                                        Vector listDept = PstDepartment.list(0, 0, "", " DEPARTMENT ");
                                                        dept_key.add("select...");
                                                        dept_value.add("0");
                                                        String selectDept = String.valueOf(gotoDept);
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                        }
                                                    	%>
                                                        <%= ControlCombo.draw("DEPARTMENT_ID","formElemen",null, selectDept, dept_value, dept_key, "onchange=\"javascript:deptChange();\"") %> </td>
                                                    </tr>
													<tr> 
													<%
													if (gotoDept > 0) 
														{ %>
													<!-- Penambahan Section untuk Intimas-->
                                                      <td width="10%" nowrap> 
                                                        Section </td>
                                                      <td width="1%">:</td>
                                                      <td width="89%"> 
                                                        <%
                                                        Vector sec_value = new Vector(1,1);
                                                        Vector sec_key = new Vector(1,1);
							String whereSec = " DEPARTMENT_ID = " + gotoDept;
                                                        
                                                        Vector listSec = PstSection.list(0, 1000, whereSec, " SECTION ");
                                                        sec_key.add("select...");
                                                        sec_value.add("0");
                                                        String selectSec = String.valueOf(gotoSect);
											           for (int i = 0; i < listSec.size(); i++) {
                                                            Section sect= (Section) listSec.get(i);
                                                            sec_key.add(sect.getSection());
                                                            sec_value.add(String.valueOf(sect.getOID()));
                                                        }
                                                    	%>
                                                        <%= ControlCombo.draw("SECTION_ID","formElemen",null, selectSec, sec_value, sec_key, "onchange=\"javascript:sectChange();\"") %> </td>
														<%
														}
														%>
														
                                                    </tr>
													<%
													 if (gotoDept > 0) 
														{
                                                    	%>
                                                    <tr> 
                                                      <td width="10%" nowrap> 
                                                        Periode</td>
                                                      <td width="1%">:</td>
                                                      <td width="89%"> 
                                                        <% 
														Vector period_value = new Vector(1,1);
														Vector period_key = new Vector(1,1);
														period_key.add("0");
														period_value.add("select...");
														String selectValuePeriod = String.valueOf(gotoPeriod);
														Vector listPeriod = new Vector(1,1);
														listPeriod = PstPeriod.list(0, 0, "", " START_DATE DESC ");
														for (int i = 0; i < listPeriod.size(); i++) {
																Period lsperiod = (Period) listPeriod.get(i);
																period_value.add(lsperiod.getPeriod());
																period_key.add(String.valueOf(lsperiod.getOID()));
														}
                                                        %>
                                                        <%=ControlCombo.draw("PERIOD_ID", null, selectValuePeriod, period_key, period_value, "onchange=\"javascript:periodChange();\"")%> </td>
                                                    </tr>
                                                    	<% 
														} 
														%>
                                                    	<%
                                                        if (gotoPeriod > 0) 
														{
                                                    	%>
                                                    <tr> 
                                                      <td width="10%" nowrap> 
                                                        Full Name</td>
                                                      <td width="1%">:</td>
                                                      <td width="89%"> 
                                                        <%
														String whereEmp = "";
														if(gotoSect!=0){
															 whereEmp = " DEPARTMENT_ID = " + gotoDept + " AND SECTION_ID = "+ gotoSect + " AND RESIGNED = 0 ";
														}
														else{
															 whereEmp = " DEPARTMENT_ID = " + gotoDept + " AND RESIGNED = 0 ";
														}
														String orderEmp = " FULL_NAME ";
														Vector emp_value = new Vector(1,1);
														Vector emp_key = new Vector(1,1);
														emp_value.add("0");
														emp_key.add("select...");
														Vector listEmp = PstEmployee.list(0, 0, whereEmp, orderEmp);
														String selectValueEmp = String.valueOf(gotoEmp);
														
														for (int i = 0; i < listEmp.size(); i++) {
																Employee emp = (Employee) listEmp.get(i);
																emp_key.add(emp.getFullName());
																emp_value.add(String.valueOf(emp.getOID()));
														}
                                                        %>
                                                        <%= ControlCombo.draw("EMPLOYEE_ID","elementForm", null, selectValueEmp, emp_value, emp_key, "onchange=\"javascript:empChange();\"") %> </td>
                                                    </tr>                                                    
                                                    	<% 
                                                        }
                                                    	%>
                                                  </table>
                                                </td>
                                              </tr>
                                              <tr> 
                                                <td width="1%">&nbsp;</td>
                                                <td width="99%"> 
												<%
												if(gotoPeriod!=0)
												{
													out.println(listData);																																		
												}
												
												if((privPrint) && (dataStatus==DATA_PRINT))
												{
                                                %>
                                                  <table width="18%" border="0" cellspacing="1" cellpadding="1">
                                                    <tr> 
                                                      <td width="17%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
                                                      <td width="83%"><b><a href="javascript:reportPdf()" class="buttonlink">Print 
                                                        Attendance Record</a></b> 
                                                      </td>
                                                    </tr>
                                                  </table>
                                                <%
                                                }
												%>
                                                </td>
                                              </tr>
                                            </table>
                                        </td>
                                          </tr>
                                        </table>
                                      </form>
                                    <!-- #EndEditable -->
                            </td>
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
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
