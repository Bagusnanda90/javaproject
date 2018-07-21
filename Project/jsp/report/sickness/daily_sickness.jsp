<%@page import="com.dimata.harisma.form.masterdata.CtrlReason"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.Date"%>
<%@ page import ="java.util.Vector"%>
<!-- package qdep -->
<%@ page import ="com.dimata.gui.jsp.*"%>
<%@ page import ="com.dimata.util.*"%>
<%@ page import ="com.dimata.qdep.form.*"%>
<!-- package harisma -->
<%@ page import ="com.dimata.harisma.entity.masterdata.*"%>
<%@ page import ="com.dimata.harisma.entity.employee.*"%>
<%@ page import ="com.dimata.harisma.session.sickness.*"%>
<%@ page import ="com.dimata.harisma.entity.attendance.*"%>


<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_SICK_DAYS_REPORT, AppObjInfo.OBJ_SICK_DAYS_DAILY_REPORT); %>
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
public Vector drawList(Vector listSicknessPresence,int start,long lSickDC,long lSickNDC,int iSickDc, int iSickNDC)  
{
	Vector result = new Vector(1,1);
	if(listSicknessPresence!=null && listSicknessPresence.size()>0)
	{
		ControlList ctrlist = new ControlList();
		ctrlist.setAreaWidth("100%");
		ctrlist.setListStyle("listgen");
		ctrlist.setTitleStyle("listgentitle");
		ctrlist.setCellStyle("listgensell");
		ctrlist.setHeaderStyle("listgentitle");
		ctrlist.addHeader("No","2%", "2", "0");
                ctrlist.addHeader("Date","8%", "2", "0");
		ctrlist.addHeader("Payroll","8%", "2", "0");
		ctrlist.addHeader("Employee","35%", "2", "0");
		ctrlist.addHeader("Schedule","21%", "0", "3");
		ctrlist.addHeader("Symbol","7%", "0", "0");
		ctrlist.addHeader("Time In","7%", "0", "0");
		ctrlist.addHeader("Time Out","7%", "0", "0");
		ctrlist.addHeader("Actual","6%", "2", "");
		ctrlist.addHeader("Remark","28%", "2", "0");
		ctrlist.setLinkRow(0);
		ctrlist.setLinkSufix("");
		
		Vector lstData = ctrlist.getData();
		Vector lstLinkData = ctrlist.getLinkData();
		ctrlist.setLinkPrefix("javascript:cmdEdit('");
		ctrlist.setLinkSufix("')");
		ctrlist.reset();

		// vector of data will used in pdf report
		Vector vectDataToPdf = new Vector(1,1);				
		
		int maxNightShiftPresence = listSicknessPresence.size();   
		int dataAmount = 0;
                 																							
		for(int i=0; i<maxNightShiftPresence; i++) 
		{
			SicknessDaily absenteeismDaily = (SicknessDaily)listSicknessPresence.get(i);
			Date schldIn = absenteeismDaily.getSchldIn();
			Date schldOut = absenteeismDaily.getSchldOut();			
			//update by satrya 2012-10-26
                        SimpleDateFormat formatter = new SimpleDateFormat("d/MM/yyyy");
                        SimpleDateFormat formatterDay = new SimpleDateFormat("EE");
                        String dayString = formatterDay.format(absenteeismDaily.getSelectedDate());
                        String dateString = formatter.format(absenteeismDaily.getSelectedDate());
                        String dateStringColor = (dayString.equalsIgnoreCase("Sat")) ? "<font color=\"darkblue\">" + dateString + "</font>" : dateString;
                        String dayStringColor = (dayString.equalsIgnoreCase("Sat")) ? "<font color=\"darkblue\">" + dayString + "</font>" : dayString;
                        dateStringColor = (dayString.equalsIgnoreCase("Sun")) ? "<font color=\"red\">" + dateStringColor + "</font>" : dateStringColor;
                        dayStringColor = (dayString.equalsIgnoreCase("Sun")) ? "<font color=\"red\">" + dayStringColor + "</font>" : dayStringColor;
                        start = start + 1;// penambahan no
			Vector rowx = new Vector(1,1);				
			rowx.add(""+(start));
                        rowx.add(dayStringColor+","+dateStringColor);
			rowx.add(absenteeismDaily.getEmpNum().toUpperCase());  
			rowx.add(absenteeismDaily.getEmpName().toUpperCase());  
	
			// schedule
			rowx.add(absenteeismDaily.getSchldSymbol());
			rowx.add(schldIn!=null ? Formater.formatTimeLocale(schldIn, "HH:mm") : "");		
			rowx.add(schldOut!=null ? Formater.formatTimeLocale(schldOut, "HH:mm") : "");
					
			// actual
                        if(lSickDC!=0){
                                if(absenteeismDaily.getReason() == iSickDc){
                                     String strAbsence = "DC";				
                                     rowx.add(strAbsence);    
                                }
                                else if(absenteeismDaily.getReason() == iSickNDC){
                                    String strAbsence = "W/O DC"; 				
                                    rowx.add(strAbsence);      
                                }
                                else{
                                    rowx.add(absenteeismDaily.getSchldSymbol()); 
                                }
                            }
                         else if(lSickNDC!=0){
                                if(absenteeismDaily.getReason() == iSickDc){
                                      String strAbsence = "DC";				
                                       
                                }
                                else if(absenteeismDaily.getReason() == iSickNDC){
                                     String strAbsence = "W/O DC"; 				
                                         
                                }
                             
                    }
                        else {                        
                                if(absenteeismDaily.getReason()==PstEmpSchedule.REASON_ABSENCE_SICKNESS){
                                        String strAbsence = "DC";
                                        rowx.add(strAbsence);
                                }else{
                                        String strAbsence = "CSTD";
                                        rowx.add(strAbsence);
                                }
                        }
			// remark
			String remark = absenteeismDaily.getRemark();
			rowx.add(remark);
			
			lstData.add(rowx);
			vectDataToPdf.add(rowx);			
		}

		result.add(String.valueOf(DATA_PRINT));			
		result.add(ctrlist.drawList());				
		result.add(vectDataToPdf);								
	}
	else
	{
		result.add(String.valueOf(DATA_NULL));			
		result.add("<div class=\"msginfo\">&nbsp;&nbsp;No sickness data found ...</div>");				
		result.add(new Vector(1,1));				
	}	
	return result;		
}
%>


<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,"department");
long oidSection = FRMQueryString.requestLong(request,"section");
//Date date = FRMQueryString.requestDate(request,"date");
int setFooter =  FRMQueryString.requestInt(request,"setFooter");
String footer = FRMQueryString.requestString(request, "footer");
//update by satrya 2012-10-25
SessSickness sessSickness = new SessSickness();
String empNum = FRMQueryString.requestString(request, "emp_number");
String fullName = FRMQueryString.requestString(request, "full_name");
Date selectedDateFrom = FRMQueryString.requestDate(request, "check_date_start");
Date selectedDateTo = FRMQueryString.requestDate(request, "check_date_finish");
int start = FRMQueryString.requestInt(request, "start");
int prevCommand = FRMQueryString.requestInt(request, "prev_command");
  int recordToGet = 10000;
Vector listSicknessPresence = new Vector(1,1);
int vectSize = 0;
/*if(iCommand == Command.LIST)
{
	//listSicknessPresence = SessSickness.listSicknessDataDaily(oidDepartment,date);
	// untuk aplikasi yang membutuhkan parameter section.Jika cukup dengan parameter department pake yang atas
    
	listSicknessPresence = sessSickness.listSicknessDataDaily(oidDepartment,selectedDateFrom,selectedDateTo,oidSection,empNum,fullName);
}*/
int iSickDc = -1;
int iSickNDC=-1;
long oidSickLeave = 0;
long oidSickLeaveWoDC = 0;
 String sISickWDC = PstSystemProperty.getValueByName("SICK_REASON_WITH_DC");
String sISickWoDC = PstSystemProperty.getValueByName("SICK_REASON_NOT_DC"); 

  String sOIDSickLeave = PstSystemProperty.getValueByName("OID_SICK_LEAVE");
  String sOIDSickLeaveWoDC = PstSystemProperty.getValueByName("OID_SICK_LEAVE_WO_DC"); 
                
try {
    if( (sISickWDC!=null) && (sISickWDC.length()>0)) {
        iSickDc = Integer.parseInt(sISickWDC);
    }
     if( (sISickWoDC!=null) && (sISickWoDC.length()>0)) {
        iSickNDC = Integer.parseInt(sISickWoDC);
    }
      if( (sOIDSickLeave!=null) && (sOIDSickLeave.length()>0)) {
                        oidSickLeave = Long.parseLong(sOIDSickLeave);
    }
    if( (sOIDSickLeaveWoDC!=null) && (sOIDSickLeaveWoDC.length()>0)) {
        oidSickLeaveWoDC = Long.parseLong(sOIDSickLeaveWoDC);
    }
}catch(Exception exc){
    System.out.println("===> NOT SICK LEAVE OID DEFINED => USE ABSENCE MANAGEMENT AND SCHEDULE STATUS AS SICKNESS REPORT");
}
String whereClause ="IN("+iSickDc+","+iSickNDC+")";
        vectSize = sessSickness.getCountSessSickness(oidSickLeave,oidSickLeaveWoDC,oidDepartment, selectedDateFrom, selectedDateTo, oidSection, empNum, fullName,whereClause); 	
	if(iCommand==Command.FIRST || iCommand==Command.NEXT || iCommand==Command.PREV || iCommand==Command.LAST || iCommand==Command.LIST || iCommand == Command.SAVE)
	{
		CtrlReason ctrlReason = new CtrlReason(request);	
		start = ctrlReason.actionList(iCommand, start, vectSize, recordToGet);
	}
       listSicknessPresence = sessSickness.listSicknessDataDaily(oidSickLeave,oidSickLeaveWoDC,iSickDc,iSickNDC,oidDepartment,selectedDateFrom,selectedDateTo,oidSection,empNum.trim(),fullName.trim(),start,recordToGet,whereClause);      
	  
		
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Sickness Report</title>
<script language="JavaScript">
function cmdView(){
	document.frpresence.command.value="<%=Command.LIST%>";
        document.frpresence.start.value=0;
	document.frpresence.action="daily_sickness.jsp";
	document.frpresence.submit();
}

function reportPdf(){	 
	var linkPage = "<%=printroot%>.report.sickness.DailySicknessPdf";       
	//window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			
	window.open(linkPage);  				
}

function cmdFooter(){
	document.frpresence.setFooter.value="1";
	document.frpresence.action="daily_sickness.jsp";
	document.frpresence.submit();
}
            function cmdListFirst(){
		document.frpresence.command.value="<%=Command.FIRST%>";
		document.frpresence.action="daily_sickness.jsp";
                document.frpresence.target = "";
		document.frpresence.submit();
	}

	function cmdListPrev(){
		document.frpresence.command.value="<%=Command.PREV%>";
		document.frpresence.action="daily_sickness.jsp";
                document.frpresence.target = "";
		document.frpresence.submit();
	}

	function cmdListNext(){
		document.frpresence.command.value="<%=Command.NEXT%>";
		document.frpresence.action="daily_sickness.jsp";
                document.frpresence.target = "";
		document.frpresence.submit();
	}

	function cmdListLast(){
		document.frpresence.command.value="<%=Command.LAST%>";
		document.frpresence.action="daily_sickness.jsp";
                document.frpresence.target = "";
		document.frpresence.submit();
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
                  &gt; Daily Sickness<!-- #EndEditable --> </strong></font> 
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
			              <input type="hidden" name="setFooter" value="<%=setFooter%>">
                                      <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
									  <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                        <!-- update by satrya 2012-10-03 -->
                                        <tr> 
                                                        <td width="3%" align="right" nowrap>&nbsp; 

                                                        </td>
                                                        <td width="9%"> <div align="left">Payrol Num </div></td>
                                                        <td width="88%">:
                                                            <input type="text" size="20" name="emp_number"  value="<%= sessSickness.getEmpNum() != null ? sessSickness.getEmpNum() : empNum %>" class="elemenForm" onKeyDown="javascript:fnTrapKD()"> </td>
                                                    </tr>
                                                        <tr>
                                                                    <td width="3%" align="right" nowrap>&nbsp; 

                                                                    </td>
                                                                    <td width="9%"> <div align="left"><%=dictionaryD.getWord(I_Dictionary.FULL_NAME) %></div></td>
                                                                    <td width="88%">:
                                                                        <input type="text" size="50" name="full_name"  value="<%= sessSickness.getEmpFullName() != null ? sessSickness.getEmpFullName()  : fullName%>" class="elemenForm" onKeyDown="javascript:fnTrapKD()"> </td>
                                                                </tr>
                                                                
						<!-- end -->
										<tr> 
                                          <td width="3%">&nbsp;</td>
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
                                          <td width="3%">&nbsp;</td>
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
                                        <!--<tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left">Date </div>
                                          </td>
                                          <td width="88%">: </table>/%=ControlDate.drawDate("date",date==null || iCommand == Command.NONE?new Date():date,"formElemen",0,installInterval)%></td>
                                        </tr>-->
										<!-- update by satrya 2012-10-04 -->
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left">Date </div>
                                          </td>
                                        <td width="88%">:  <!--%//=ControlDate.drawDate("date", date == null || iCommand == Command.NONE ? new Date() : date, "formElemen", 0, installInterval)%-->
                                            <!--//update by satrya 2012-7-18-->
                                            <% 
                                                //String selectDateStart = "" + selectedDateFrom; 
                                               //String selectDateFinish 
                                             %>
                                            <%=ControlDate.drawDateWithStyle("check_date_start", selectedDateFrom != null ? selectedDateFrom : new Date(), 0, installInterval, "formElemen", "")%> 
                                            to <%=ControlDate.drawDateWithStyle("check_date_finish", selectedDateTo != null ? selectedDateTo : new Date(), 0, installInterval, "formElemen", "")%>
                                        </td>
                                        </tr>
                                        <!-- end -->
										 <tr> 
                                          <td width="3%" height="48">&nbsp;</td>
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left"><a href="javascript:cmdFooter()">Set Footer</a> </div>
                                          </td>
										   <% if(setFooter==1){ %>
                                          <td width="88%">: <input name="footer" type="text" size="85" value="<%=footer%>">
										  </td>
										 <%
										}
										%>
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" nowrap> 
                                            <div align="left"></div>
                                          </td>
                                          <td width="88%"> 
                                            <table border="0" cellspacing="0" cellpadding="0" width="160">
                                              <tr> 
                                                <td width="26"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Sickness"></a></td>
                                                <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="130" class="command" nowrap><a href="javascript:cmdView()">View 
                                                  Sickness</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
				<% if(iCommand == Command.LIST || iCommand==Command.FIRST || iCommand==Command.NEXT || iCommand==Command.PREV || iCommand==Command.LAST){%>
				 <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                    <tr>
                                       <td><hr></td>
                                    </tr>
                                      <tr>
                                            <td>
                                            <%
 // process data on control drawlist
int dataStatus = 0;
try{
Vector vectResult = drawList(listSicknessPresence,start,oidSickLeave,oidSickLeaveWoDC,iSickDc,iSickNDC);
dataStatus = Integer.parseInt(String.valueOf(vectResult.get(0)));
String listData = String.valueOf(vectResult.get(1));
Vector vectDataToPdf = (Vector)vectResult.get(2);

// design vector that handle data to store in session
Vector vectPresence = new Vector(1,1);
//vectPresence.add(date);
vectPresence.add(selectedDateFrom);
vectPresence.add(selectedDateTo); 
//update by satrya 2012-10-19
vectPresence.add(""+oidDepartment);
vectPresence.add(""+oidSection);
vectPresence.add(vectDataToPdf);  
vectPresence.add(""+footer);
vectPresence.add(empNum);
vectPresence.add(fullName);

vectPresence.add(""+oidSickLeave);
vectPresence.add(""+oidSickLeaveWoDC);
vectPresence.add(""+iSickDc);
vectPresence.add(""+iSickNDC);

if(session.getValue("SICKNESS_DAILY")!=null){   
	session.removeValue("SICKNESS_DAILY");
}
session.putValue("SICKNESS_DAILY",vectPresence);	

                                            out.println(listData);
}catch(Exception ex){
    System.out.println("Exception list dayli sickness"+ ex);
}
                                            %>
                                            </td>
                                    </tr>
                                            <%if(dataStatus==DATA_PRINT && privPrint){%>
                                                   <!-- untuk tombol Next Prev -->
                                              <tr>
                                                <td>
<% 
ControlLine ctrLine = new ControlLine();												
ctrLine.setLocationImg(approot+"/images");

ctrLine.initDefault();		
int listCommand = Command.NEXT;											
out.println(ctrLine.drawImageListLimit(listCommand, vectSize, start, recordToGet));
%>
                                                    </td>
                                                    </tr>
										<tr>
											<td>
											  <table width="18%" border="0" cellspacing="1" cellpadding="1">
												<tr>
												  <td width="17%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0" alt="Print Daily Sickness"></a></td>												  
                                                <td width="83%"><b><a href="javascript:reportPdf()" class="command">Print 
                                                  Daily Sickness</a></b> </td>
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
