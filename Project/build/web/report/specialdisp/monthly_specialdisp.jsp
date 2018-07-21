<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.Vector"%>
<%@ page import ="java.util.Calendar"%>
<!-- package qdep -->
<%@ page import ="com.dimata.gui.jsp.*"%>
<%@ page import ="com.dimata.util.*"%>
<%@ page import ="com.dimata.qdep.form.*"%>
<!-- package harisma -->
<%@ page import ="com.dimata.harisma.entity.masterdata.*"%>
<%@ page import ="com.dimata.harisma.entity.attendance.*"%>
<%@ page import ="com.dimata.harisma.entity.employee.*"%>
<%@ page import ="com.dimata.harisma.session.specialdisp.*"%>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_SPECIAL_DISCREPANCIES_REPORT, AppObjInfo.OBJ_SPECIAL_DISCREPANCIES_MONTHLY_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
//boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<%!
int DATA_NULL = 0;
int DATA_PRINT = 1;

/**
 * create list object
 * consist of : 
 *  first index  ==> status object (will displayed or not)
 *  second index ==> object string will displayed
 *  third index  ==> object vector of string used in report on PDF format.
 */
public Vector drawList(Vector listSpecialDispPresence, int dateOfMonth) 
{
	Vector result = new Vector(1,1);
	if(listSpecialDispPresence!=null && listSpecialDispPresence.size()>0)
	{
		int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No","2%", "2", "0");
		ctrlist.addHeader("Payroll","6%", "2", "0");
		ctrlist.addHeader("Employee","16%", "2", "0");
		ctrlist.addHeader("Period","70%", "0", ""+dateOfMonth);
		ctrlist.addHeader(""+startDatePeriod,"2%", "0", "0");				
		for(int j=0; j<dateOfMonth-1; j++){
			if(startDatePeriod==dateOfMonth){
				startDatePeriod =1;
				ctrlist.addHeader(""+startDatePeriod,"2%", "0", "0");
			}
			else{
				startDatePeriod =startDatePeriod +1;
				ctrlist.addHeader(""+startDatePeriod,"2%", "0", "0");
			}
			
		}
		ctrlist.addHeader("Total","3%", "2", "0");
	
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		ctrlist.reset();
		
		// vector of data will used in pdf report
		Vector vectDataToPdf = new Vector(1,1);									

		int maxSpecialDispPresence = listSpecialDispPresence.size();  
		int dataAmount = 0;									 							
		for(int i=0; i<maxSpecialDispPresence; i++) 
		{
			SpecialDispMonthly absenteeismWeekly = (SpecialDispMonthly)listSpecialDispPresence.get(i);
			String empNum = absenteeismWeekly.getEmpNum();
			String empName = absenteeismWeekly.getEmpName();
			Vector empSchedules = absenteeismWeekly.getEmpSchedules();
			Vector absStatus = absenteeismWeekly.getPresenceStatus();
			Vector absReason = absenteeismWeekly.getAbsReason();
			
			// check apakah dalam vector schedule ada schedule tipe not OFf/ABSENCE ???			
			boolean containSchldNotOff = SessSpecialDisp.containSchldNotOff(empSchedules);
			int startPerioDisp = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));									
			startPerioDisp = startPerioDisp-1;
			if(containSchldNotOff)
			{
				int totalSpecialDisp = 0;
				int absenceNull = 0; // menghandle apakah presence dalam month terpilih null atau tidak														
				Vector rowxMonth = new Vector(1,1);				
				for(int isch=0; isch<empSchedules.size(); isch++)
				{
					if(startPerioDisp==dateOfMonth){
						startPerioDisp=1;
					}
					else{
						startPerioDisp = startPerioDisp + 1;
					}
					//String schldSymbol = PstScheduleSymbol.getScheduleSymbol(Long.parseLong(String.valueOf(empSchedules.get(isch))));
					String schldSymbol = "";
					int schldCategory = 0;
					String currAbsence = "";					
					Vector vectSchldSymbol = PstScheduleSymbol.getScheduleData(Long.parseLong(String.valueOf(empSchedules.get(startPerioDisp - 1))));
					if(vectSchldSymbol!=null && vectSchldSymbol.size()>0)
					{
						Vector vectTemp = (Vector)vectSchldSymbol.get(0);
						schldSymbol = String.valueOf(vectTemp.get(0));
						schldCategory = Integer.parseInt(String.valueOf(vectTemp.get(1)));						
					}					

					if(schldSymbol!=null && schldSymbol.length()>0)
					{
						int statusAbsence = Integer.parseInt(String.valueOf(absStatus.get(startPerioDisp-1)));
                                                int reasonAbsence = Integer.parseInt(String.valueOf(absReason.get(startPerioDisp - 1)));
						if(!(schldCategory==PstScheduleCategory.CATEGORY_OFF 
						    || schldCategory==PstScheduleCategory.CATEGORY_ABSENCE							
						    || schldCategory==PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
						    || schldCategory==PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
						    || schldCategory==PstScheduleCategory.CATEGORY_LONG_LEAVE																												
							) &&  
							(statusAbsence==PstEmpSchedule.STATUS_PRESENCE_ABSENCE && reasonAbsence==PstEmpSchedule.REASON_ABSENCE_DISPENSATION))
						{					
							currAbsence="SD"; 					
							absenceNull += 1;							
						}
						else
						{
							currAbsence="";
						}
						rowxMonth.add(currAbsence);											
						
						if(currAbsence!=null && currAbsence.length()>0)
						{
							totalSpecialDisp += 1;
						}						
					
					}					  
					else
					{
						rowxMonth.add("");  
					}
				}												
				
				if(absenceNull>0)
				{
					dataAmount += 1;				
					Vector rowx = new Vector(1,1);				
					rowx.add(""+dataAmount);
					rowx.add(empNum);  
					rowx.add(empName);  
					rowx.addAll(rowxMonth);
					rowx.add(String.valueOf(totalSpecialDisp));
					lstData.add(rowx);
					vectDataToPdf.add(rowx);
				}													

			}			
		}
		
		if(dataAmount>0)
		{
			result.add(String.valueOf(DATA_PRINT));							
			result.add(ctrlist.drawList());				
			result.add(vectDataToPdf);							
		}
		else
		{
			result.add(String.valueOf(DATA_NULL));					
			result.add("<div class=\"msginfo\">&nbsp;&nbsp;No special dispensation data found ...</div>");				
			result.add(new Vector(1,1));																
		}	
								
	}
	else
	{
		result.add(String.valueOf(DATA_NULL));					
		result.add("<div class=\"msginfo\">&nbsp;&nbsp;No special dispensation data found ...</div>");				
		result.add(new Vector(1,1));																
	}
	return result;
}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,"department");
long oidSection = FRMQueryString.requestLong(request,"section");
long periodId = FRMQueryString.requestLong(request,"period");
//Date date = FRMQueryString.requestDate(request,"month_year");
Period pr = new Period();
Date date = new Date();
String periodName ="";
		try{
			pr = PstPeriod.fetchExc(periodId);
			date =  pr.getStartDate();
			periodName = pr.getPeriod();
			
			
		}
		catch(Exception e){
	}



// get maximum date of selected month
Calendar newCalendar = Calendar.getInstance();
newCalendar.setTime(date);
int dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); 

Vector listSpecialDispPresence = new Vector(1,1);
if(iCommand == Command.LIST)
{
	//listSpecialDispPresence = SessSpecialDisp.listSpecialDispDataMonthly(oidDepartment,date);
	// untuk aplikasi yang membutuhkan parameter section. JIka cukup dengan parameter Department pake yang atas
	listSpecialDispPresence = SessSpecialDisp.listSpecialDispDataMonthly(oidDepartment,date,oidSection);

}

// process on drawlist
Vector vectResult = drawList(listSpecialDispPresence, dateOfMonth);
int dataStatus = Integer.parseInt(String.valueOf(vectResult.get(0)));
String listData = String.valueOf(vectResult.get(1));
Vector vectDataToPdf = (Vector)vectResult.get(2);

// design vector that handle data to store in session
Vector vectPresence = new Vector(1,1);
//vectPresence.add(date);
vectPresence.add(""+periodName);
vectPresence.add(""+oidDepartment);
vectPresence.add(vectDataToPdf);  
vectPresence.add(""+dateOfMonth);     

int monthStartDate = Integer.parseInt(String.valueOf(date.getMonth()+1));
int yearStartDate =  Integer.parseInt(String.valueOf(date.getYear()+1900));
int dateStartDate =  Integer.parseInt(String.valueOf(date.getDate()));
GregorianCalendar periodStart = new GregorianCalendar(yearStartDate, monthStartDate-1, dateStartDate);
int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
vectPresence.add(""+startDatePeriod);

if(session.getValue("SPECIAL_DISP_MONTHLY")!=null){
	session.removeValue("SPECIAL_DISP_MONTHLY");
}
session.putValue("SPECIAL_DISP_MONTHLY",vectPresence);			
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Special Dispensation Report</title>
<script language="JavaScript">
function cmdView(){
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.action="monthly_specialdisp.jsp";
	document.frpresence.submit();
}

function reportPdf(){	 
	var linkPage = "<%=printroot%>.report.specialdisp.MonthlySpecialDispPdf";  
	//window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Attendance 
                  &gt; Monthly Special Dispensation<!-- #EndEditable --> </strong></font> 
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
                                  <td valign="top"> <!-- #BeginEditable "content" -->
                                    <form name="frpresence" method="post" action="">
									<input type="hidden" name="command" value="<%=iCommand%>">
									  <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                          </td>
                                          <td width="88%"> : 
                                          <%
                                          Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");										  
										  Vector deptValue = new Vector(1,1);
										  Vector deptKey = new Vector(1,1);
										  deptValue.add("0") ;
										  deptKey.add(" - ALL - ");
										  for(int d=0;d<listDepartment.size();d++)
										  {
										  	Department department = (Department)listDepartment.get(d);
											deptValue.add(""+department.getOID());
											deptKey.add(department.getDepartment());
										  }
										  out.println(ControlCombo.draw("department",null,""+oidDepartment,deptValue,deptKey));
										  %>
                                          </td>
                                        </tr>
										<tr> 
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div>
                                          </td>
                                          <td width="88%"> : 
                                          <%
                                          Vector listSection = PstSection.list(0, 0, "", "SECTION");										  
										  Vector sectValue = new Vector(1,1);
										  Vector sectKey = new Vector(1,1);
										  sectValue.add("0") ;
										  sectKey.add(" - ALL - ");
										  for(int d=0;d<listSection.size();d++)
										  {
										  	Section section = (Section)listSection.get(d);
											sectValue.add(""+section.getOID());
											sectKey.add(section.getSection());
										  }
										  out.println(ControlCombo.draw("section",null,""+oidSection,sectValue,sectKey));
										  %>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left">Period</div>
                                          </td>
                                          <td width="88%">: <%//=ControlDate.drawDateMY("month_year",date==null || iCommand == Command.NONE?new Date():date,"MMM yyyy","formElemen",0,installInterval)%>
										  <%
										  Vector listPeriod = PstPeriod.list(0, 0, "", "PERIOD");
										  Vector periodValue = new Vector(1,1);
										  Vector periodKey = new Vector(1,1);
                                          //deptValue.add("0");
                                          //deptKey.add("ALL");

                                          for(int d=0;d<listPeriod.size();d++){
										  	Period period = (Period)listPeriod.get(d);
											periodValue.add(""+period.getOID());
											periodKey.add(period.getPeriod());
										  }
										  %> <%=ControlCombo.draw("period",null,""+periodId,periodValue,periodKey)%>	
								 		 </td>
                                        </tr>
                                        <tr> 
                                          <td width="9%" nowrap> 
                                            <div align="left"></div>
                                          </td>
                                          <td width="88%"> 
                                            <table border="0" cellspacing="0" cellpadding="0" width="239">
                                              <tr> 
                                                <td width="26"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Special Dispensation"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="209" class="command" nowrap><a href="javascript:cmdView()">View 
                                                  Special Dispensation</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
									  
									  <% if(iCommand == Command.LIST){%>
									  <table width="100%" border="0" cellspacing="2" cellpadding="2">
									    <tr>
											<td><hr></td>
										</tr>
                                        <tr>
											<td>
											<%
											out.println(listData);																						
											%>
											</td>
										</tr>
										<%if(dataStatus==DATA_PRINT && privPrint){%>
										<tr>
											<td>
											  <table width="27%" border="0" cellspacing="1" cellpadding="1">
                                              <tr>
												  
                                                <td width="11%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0" alt="Print Monthly Special Dispensation"></a></td>
												  
                                                <td width="89%"><b><a href="javascript:reportPdf()" class="buttonlink">Print 
                                                  Monthly Special Dispensation</a></b> 
                                                </td>
												</tr>
											  </table>
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
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
</table>
</body>
<!-- #BeginEditable "script" --> 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
