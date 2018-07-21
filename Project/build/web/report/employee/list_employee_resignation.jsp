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
<%@ page import ="com.dimata.harisma.session.absenteeism.*"%>
<%@ page import ="com.dimata.harisma.session.employee.*"%>


<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_ABSENTEEISM_REPORT, AppObjInfo.OBJ_ABSENTEEISM_MONTHLY_REPORT); %>
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
public Vector drawList(Vector listAbsenteeismPresence, int dateOfMonth) 
{
	Vector result = new Vector(1,1);
	System.out.println("dateOfMonth  "+dateOfMonth);
	if(listAbsenteeismPresence!=null && listAbsenteeismPresence.size()>0)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No","2%", "2", "0");
		ctrlist.addHeader("Payroll","6%", "2", "0");
		ctrlist.addHeader("Employee","16%", "2", "0");
		ctrlist.addHeader("Department","16%", "2", "0");
		ctrlist.addHeader("Section","16%", "0", "0");				
		ctrlist.addHeader("Remaks","16%", "0", "0");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		Vector lstData = ctrlist.getData();
		ctrlist.reset();
		
		// vector of data will used in pdf report
		Vector vectDataToPdf = new Vector(1,1);									

		int maxAbsenteeismPresence = listAbsenteeismPresence.size();  
		System.out.println("maxAbsenteeismPresence  "+maxAbsenteeismPresence);
		int dataAmount = 0;
											 							
		for(int i=0; i<maxAbsenteeismPresence; i++) 
		{
				/*Vector temp = (Vector)listAbsenteeismPresence.get(i);
				Employee employee = (Employee)temp.get(0);
				Department department = (Department)temp.get(1);
				Section section = (Section)temp.get(2);*/
					dataAmount += 1;				
					Vector rowx = new Vector(1,1);				
					rowx.add(""+dataAmount);
					rowx.add("");  
					rowx.add(""); 
					rowx.add("");  
					rowx.add("");  
					rowx.add("");  
					//rowx.addAll(rowxMonth);
					//rowx.add(String.valueOf(totalAbsenteeism));
					lstData.add(rowx);
					vectDataToPdf.add(rowx);
				
						
		}
	
								
	}
	else
	{
		result.add(String.valueOf(DATA_NULL));					
		result.add("<div class=\"msginfo\">&nbsp;&nbsp;No employee data found ...</div>");				
		result.add(new Vector(1,1));																
	}
	return result;
}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,"department");
long periodId = FRMQueryString.requestLong(request,"period");
int statusValue = FRMQueryString.requestInt(request,"status");
Date selectedDateFrom = FRMQueryString.requestDate(request, "check_date_start");
Date selectedDateTo = FRMQueryString.requestDate(request, "check_date_finish");
int monthStart = selectedDateFrom.getMonth()+1;
int yearStart = selectedDateFrom.getYear()+1900;
int monthFinish = selectedDateTo.getMonth()+1;
int yearFinish = selectedDateTo.getYear()+1900;

//Date endDate2 = Formater.formatTimeLocale(String.valueOf(selectedDateFrom),"yyyy-mm-dd");
String dateStart = ""+yearStart+"-"+monthStart+"-"+selectedDateFrom.getDate();
String dateFisnish = ""+yearFinish+"-"+monthFinish+"-"+selectedDateTo.getDate();

//System.out.println("dateStart  "+dateStart);
Period pr = new Period();
Date date = new Date();
Date endDate = new Date();
String periodName ="";
		try{
			pr = PstPeriod.fetchExc(periodId);
			date =  pr.getStartDate();
			endDate = pr.getEndDate();
			periodName = pr.getPeriod();
		}
		catch(Exception e){
	}

// get maximum date of selected month
Calendar newCalendar = Calendar.getInstance();
newCalendar.setTime(date);
int dateOfMonth = newCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); 

Vector listAbsenteeismPresence = new Vector(1,1);
if(iCommand == Command.LIST)
{
	listAbsenteeismPresence = SessEmployee.searchResignation(dateStart, dateFisnish, statusValue);
	System.out.println("listAbsenteeismPresence   "+listAbsenteeismPresence.size());
}

// process on drawlist
Vector vectResult = drawList(listAbsenteeismPresence, dateOfMonth);
int dataStatus = Integer.parseInt(String.valueOf(vectResult.get(0)));
String listData = String.valueOf(vectResult.get(1));
Vector vectDataToPdf = (Vector)vectResult.get(2);

// design vector that handle data to store in session
Vector vectPresence = new Vector(1,1);
vectPresence.add(""+periodName);
vectPresence.add(""+oidDepartment);
vectPresence.add(vectDataToPdf);  
vectPresence.add(""+dateOfMonth);  
int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));  
vectPresence.add(""+startDatePeriod);     

if(session.getValue("ABSENTEEISM_MONTHLY")!=null){
	session.removeValue("ABSENTEEISM_MONTHLY");
}
session.putValue("ABSENTEEISM_MONTHLY",vectPresence);			
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Absenteeism Report</title>
<script language="JavaScript">
function cmdView(){
	//alert("dateStart"+dateStart);
	document.frpresence.command.value="<%=Command.LIST%>";
	//document.frm_employee.startDate.value = dateStart; 
	document.frpresence.action="employee_list_resignation.jsp";
	document.frpresence.submit();
}

function reportPdf(){	 
	var linkPage = "<%=printroot%>.report.absenteeism.MonthlyAbsenteeismPdf";  
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Attendance 
                  &gt; Monthly Absenteeism<!-- #EndEditable --> </strong></font> 
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
                                    <form name="frpresence" method="post" action="">
									<input type="hidden" name="command" value="<%=iCommand%>">
									  <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left">Status </div>
                                          </td>
                                          <td width="88%"> : 
											<%
                                                    Vector vKey = new Vector();
                                                    Vector vValue = new Vector();

                                                    vKey.add(PstEmployee.STS_COMMING+"");
                                                    vKey.add(PstEmployee.STS_RESIGN+"");
                                
                                                    vValue.add(PstEmployee.stResignation[PstEmployee.STS_COMMING]);
                                                    vValue.add(PstEmployee.stResignation[PstEmployee.STS_RESIGN]);
                                                    //vValue.add(PstPublicHolidays.stHolidaySts[PstPublicHolidays.STS_NON_HINDU]);
                                                %>
													<%=ControlCombo.draw("status",null,""+statusValue,vKey,vValue)%>
                                          			
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left">Period</div>
                                          </td>
                                          <td width="88%">: <%//=ControlDate.drawDateMY("month_year",date==null || iCommand == Command.NONE?new Date():date,"MMM yyyy","formElemen",0,installInterval)%>
										  		<%
										  /*Vector listPeriod = PstPeriod.list(0, 0, "", "PERIOD");
										  Vector periodValue = new Vector(1,1);
										  Vector periodKey = new Vector(1,1);
                                          //deptValue.add("0");
                                          //deptKey.add("ALL");

                                          for(int d=0;d<listPeriod.size();d++){
										  	Period period = (Period)listPeriod.get(d);
											periodValue.add(""+period.getOID());
											periodKey.add(period.getPeriod());
										  }*/
										  %> <%=ControlDate.drawDateWithStyle("check_date_start", selectedDateFrom, 0,-15, "formElemen", "")%> to <%=ControlDate.drawDateWithStyle("check_date_finish", selectedDateTo, 0, -15, "formElemen", "")%>
										  	
										  </td>
                                        </tr>
                                        <tr> 
                                          <td width="9%" nowrap> 
                                            <div align="left"></div>
                                          </td>
                                          <td width="88%"> 
                                            <table border="0" cellspacing="0" cellpadding="0" width="239">
                                              <tr> 
                                                <td width="26"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Absenteeism"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="209" class="command" nowrap><a href="javascript:cmdView()">Search for 
                                                  Employee</a></td>
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
												  
                                                <td width="11%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0" alt="Print Monthly Absenteeism"></a></td>
												  
                                                <td width="89%"><b><a href="javascript:reportPdf()" class="buttonlink">Print 
                                                  Monthly Absenteeism</a></b> 
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" --> 
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
