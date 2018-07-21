
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
<%@ page import ="com.dimata.harisma.entity.employee.*"%>
<%@ page import ="com.dimata.harisma.session.attendance.*"%>
<%@ page import ="com.dimata.harisma.form.attendance.*"%>
<%@ page import ="com.dimata.harisma.entity.attendance.*"%>
<%@ page import ="com.dimata.harisma.entity.employee.*"%>

<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_PRESENCE_REPORT, AppObjInfo.OBJ_PRESENCE_REPORT); %>
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
public String drawList(Vector listPresenceReport, Date date, int start) 
{
    if(listPresenceReport!=null && listPresenceReport.size()>0)
    {
        
        Calendar newCalendarTemp = Calendar.getInstance();
        newCalendarTemp.setTime(date);
        int dateOfMonth = newCalendarTemp.getActualMaximum(Calendar.DAY_OF_MONTH); 
        
            //int startPeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
            int startPeriod = date.getDate();
            ControlList ctrlist = new ControlList();
            ctrlist.setAreaWidth("100%");
            ctrlist.setListStyle("listgen");
            ctrlist.setTitleStyle("listgentitle");
            ctrlist.setCellStyle("listgensell");
            ctrlist.setHeaderStyle("listgentitle");
            ctrlist.addHeader("No","2%", "2", "0");
            ctrlist.addHeader("Id","6%", "2", "0");
            ctrlist.addHeader("Band Member","16%", "2", "0");
            ctrlist.addHeader("Attendance Status","2%", "0", ""+dateOfMonth);
            ctrlist.addHeader(""+startPeriod,"2%", "0", "0");
            for(int j=0; j<dateOfMonth-1; j++){
                    if(startPeriod == dateOfMonth){
                            startPeriod =1;
                            ctrlist.addHeader(""+startPeriod,"2%", "0", "0");
                    }
                    else{
                            startPeriod =startPeriod+1;
                            ctrlist.addHeader(""+startPeriod,"2%", "0", "0");
                    }

            }
            ctrlist.addHeader("DP Record","8%", "0", "4");
            ctrlist.addHeader("Prev.","2%", "0", "0");		
            ctrlist.addHeader("Taken","2%", "0", "0");		
            ctrlist.addHeader("New","2%", "0", "0");		
            ctrlist.addHeader("Blc.","2%", "0", "0");

            ctrlist.addHeader("AL Record","6%", "0", "3");
            ctrlist.addHeader("Ent.","2%", "0", "0");		
            ctrlist.addHeader("Taken","2%", "0", "0");		
            ctrlist.addHeader("Blc.","2%", "0", "0");

            ctrlist.addHeader("LL Record","6%", "0", "3");
            ctrlist.addHeader("Ent.","2%", "0", "0");		
            ctrlist.addHeader("Taken","2%", "0", "0");		
            ctrlist.addHeader("Blc.","2%", "0", "0");

            ctrlist.setLinkRow(0);
            ctrlist.setLinkSufix("");
            Vector lstData = ctrlist.getData();
            ctrlist.reset();

            // vector of data will used in pdf report
            Vector vectDataToPdf = new Vector(1,1);									

            int maxPresenceData = listPresenceReport.size();  
            int dataAmount = 0;									 							
            for(int i=0; i<maxPresenceData; i++) 
            {
                Vector vDataTemp = new Vector(1,1);
                vDataTemp = (Vector)listPresenceReport.get(i);
                String empId = (String)vDataTemp.get(0);
                String empNum = (String)vDataTemp.get(1);
                String empName = (String)vDataTemp.get(2);
                String periodId = (String)vDataTemp.get(3);
                Vector vScheduleSymbol = (Vector)vDataTemp.get(5);
                DpStockManagement dpStockManagement = (DpStockManagement)vDataTemp.get(6);
                AlStockManagement alStockManagement = (AlStockManagement)vDataTemp.get(7);
                LLStockManagement llStockManagement = (LLStockManagement)vDataTemp.get(8);
                String totalDp = (String)vDataTemp.get(10);
                String totalAl = (String)vDataTemp.get(11);
                String totalLl = (String)vDataTemp.get(12);
                String newDp = (String)vDataTemp.get(13);
                Vector rowx = new Vector(1,1);				
                rowx.add(""+(dataAmount+start));
                dataAmount += 1;				
                rowx.add(empNum);  
                rowx.add(empName);  
                rowx.addAll(vScheduleSymbol);
                float iDpQty = (dpStockManagement!=null?dpStockManagement.getQtyResidue():0);
                int iDpUsedQty = 0;
                int iDpNewQty = 0;
                try{
                    iDpUsedQty = Integer.parseInt(totalDp);
                }catch(Exception ex){iDpUsedQty=0;}
                try{
                    iDpNewQty = Integer.parseInt(newDp);
                }catch(Exception ex){iDpNewQty=0;}
                rowx.add(""+iDpQty);
                rowx.add(""+iDpUsedQty);
                rowx.add(""+iDpNewQty);
                rowx.add(""+(iDpQty+iDpNewQty-iDpUsedQty));
                
                //AL
                //ambil entitled
                Employee empCurr = new Employee();
                try{
                    empCurr = PstEmployee.fetchExc(Long.parseLong(empId));
		}catch(Exception ex){}
                Date commDate = empCurr.getCommencingDate();
		Date now = new Date();
		int yearNow = now.getYear()+1900;
		int yearComm = 0;
		int monthYear = 0;
		try{
                    yearComm = empCurr.getCommencingDate().getYear()+1900;
                    monthYear = empCurr.getCommencingDate().getMonth()+1;
		}catch(Exception ex){
                    yearComm=0;
                    monthYear=0;
                }
                float entitled = 0;
		float residue = 0;
		if((yearNow - yearComm) > 1){
		//	entitled = alStockManagement.getEntitled();
			entitled = alStockManagement.getEntitled();
			residue = entitled - alStockManagement.getQtyUsed();
			//rowx.add("");
		}
		// jika bekerja setahun 
		else if((yearNow - yearComm)==1){
			if(monthYear==12){
				entitled = 0;
				residue = entitled - alStockManagement.getQtyUsed();
			}
			else{
		//		entitled = alStockManagement.getEntitled() - monthYear;
				entitled = alStockManagement.getEntitled() - monthYear;
				residue = entitled - alStockManagement.getQtyUsed();
			}
		}else{
			entitled = 0;
		}
                
                float iAlQty = residue;//(alStockManagement!=null?alStockManagement.getQtyResidue():0);
                int iAlUsedQty = 0;
                try{
                    iAlUsedQty = Integer.parseInt(totalAl);
                }catch(Exception ex){iAlUsedQty=0;}
                rowx.add(""+iAlQty);
                rowx.add(""+iAlUsedQty);
                rowx.add(""+(iAlQty-iAlUsedQty));
                
                float iLlQty = (llStockManagement!=null?llStockManagement.getQtyResidue():0);
                int iLlUsedQty = 0;
                try{
                    iLlUsedQty = Integer.parseInt(totalLl);
                }catch(Exception ex){iLlUsedQty=0;}
                rowx.add(""+iLlQty);
                rowx.add(""+iLlUsedQty);
                rowx.add(""+(iLlQty-iLlUsedQty));
                lstData.add(rowx);
                vectDataToPdf.add(rowx);
            }
            return ctrlist.drawList();
	}
    return "";
}
%>

<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDivision = FRMQueryString.requestLong(request,"division");
long oidDepartment = FRMQueryString.requestLong(request,"department");
//long oidSection = FRMQueryString.requestLong(request,"section");
long periodId = FRMQueryString.requestLong(request,"period");
//Date date = FRMQueryString.requestDate(request,"month_year");
int start = FRMQueryString.requestInt(request, "start");
Period pr = new Period();
Date date = new Date();
String periodName ="";
    try{
            pr = PstPeriod.fetchExc(periodId);
            date =  pr.getStartDate();
            periodName = pr.getPeriod();
    }
    catch(Exception e){}


ControlLine ctrLine = new ControlLine();
CtrlPresence ctrlPresence = new CtrlPresence(request);

final int recordToGet = 20;
int vectSize = 0;
Vector listPresenceReport = new Vector(1,1);
Vector listPresenceReportPdf = new Vector(1,1);

SessPresence sessPresence = new SessPresence();
vectSize = sessPresence.countAttendanceSummaryList(oidDivision, oidDepartment, periodId);
if((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
  ||(iCommand==Command.LAST)||(iCommand==Command.LIST))
{
    start = ctrlPresence.actionList(iCommand, start, vectSize, recordToGet);
}else{
    start = 0;
}
try{
    if(vectSize>0){/*
        Pada Session Presen, buat perhitunga dp terkini, dan semua Al, LL, Dan Al 
 * dari period sebelumnya
        */
        listPresenceReport = SessPresence.listAttendanceData(oidDivision, oidDepartment, periodId, start, recordToGet);
        listPresenceReportPdf = SessPresence.listAttendanceData(oidDivision, oidDepartment, periodId, 0, 0); 
    }
}catch(Exception ex){}


// process on drawlist
//Vector vectResult = drawList(listPresenceReport, dateOfMonth,(start+1));
//int dataStatus = Integer.parseInt(String.valueOf(vectResult.get(0)));
String listData = drawList(listPresenceReport, date,(start+1));
//Vector vectDataToPdf = (Vector)vectResult.get(2);

// design vector that handle data to store in session
Vector vectPresence = new Vector(1,1);
vectPresence.add(""+periodId);
vectPresence.add(""+oidDepartment);
vectPresence.add(listPresenceReportPdf);  
//vectPresence.add(""+dateOfMonth);     
//int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
//vectPresence.add(""+startDatePeriod); 

if(session.getValue("ATTENDANCE_SUMMARY_SHEET")!=null){
	session.removeValue("ATTENDANCE_SUMMARY_SHEET");
}
session.putValue("ATTENDANCE_SUMMARY_SHEET",vectPresence);			
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Attendance Sumary Sheet Report</title>
<script language="JavaScript">

function cmdUpdate(){
	document.frpresence.command.value="<%=String.valueOf(Command.GOTO)%>";
	document.frpresence.action="presence_summary_sheet.jsp"; 
	document.frpresence.submit();
}

function cmdView(){
	document.frpresence.command.value="<%=String.valueOf(Command.LIST)%>";
	document.frpresence.action="presence_summary_sheet.jsp";
	document.frpresence.submit();
}

function cmdListFirst(){
        document.frpresence.command.value="<%=String.valueOf(Command.FIRST)%>";
        document.frpresence.action="presence_summary_sheet.jsp";
        document.frpresence.submit();
}

function cmdListPrev(){
        document.frpresence.command.value="<%=String.valueOf(Command.PREV)%>";
        document.frpresence.action="presence_summary_sheet.jsp";
        document.frpresence.submit();
}

function cmdListNext(){
        document.frpresence.command.value="<%=String.valueOf(Command.NEXT)%>";
        document.frpresence.action="presence_summary_sheet.jsp";
        document.frpresence.submit();
}

function cmdListLast(){
        document.frpresence.command.value="<%=String.valueOf(Command.LAST)%>";
        document.frpresence.action="presence_summary_sheet.jsp";
        document.frpresence.submit();
}

function reportPdf(){	 
	var linkPage = "<%=printroot%>.report.attendance.AttendanceSummarySheet";   
	//window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no"); 
        document.frpresence.target = "ReportPerEmployee";
        document.frpresence.submit();
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->
                Report
                  &gt; Attendance Summary Sheet<!-- #EndEditable --> </strong></font> 
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
                                    <input type="hidden" name="command" value="<%=String.valueOf(iCommand)%>">
                                    <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                      <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="9%" height="34" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DIVISION) %></div>
                                          </td>
                                          <td width="88%"> : 
                                          <%
                                          Vector listDivision = PstDivision.list(0, 0, "", PstDivision.fieldNames[PstDivision.FLD_DIVISION]+" ASC ");										  
                                          Vector divValue = new Vector(1,1);
                                          Vector divKey = new Vector(1,1);
                                          divValue.add("0");
                                          divKey.add(" All... ");
                                          for(int d=0;d<listDivision.size();d++)
                                          {
                                                Division division = (Division)listDivision.get(d);
                                                divValue.add(""+division.getOID());
                                                divKey.add(division.getDivision());
                                          }
                                          %>
                                           <%= ControlCombo.draw("division","formElemen",null, ""+oidDivision, divValue, divKey, "onChange=\"javascript:cmdUpdate()\"") %>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td width="9%" height="34" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                          </td>
                                          <td width="88%"> : 
                                          <%
                                          String whereClauseDept = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID]+"="+oidDivision;
                                          Vector listDepartment = PstDepartment.list(0, 0, whereClauseDept, PstDepartment.TBL_HR_DEPARTMENT+"."+"DEPARTMENT ASC");										  
                                          Vector deptValue = new Vector(1,1);
                                          Vector deptKey = new Vector(1,1);
                                          deptValue.add("0");
                                          deptKey.add(" All... ");
                                          for(int d=0;d<listDepartment.size();d++)
                                          {
                                                Department department = (Department)listDepartment.get(d);
                                                deptValue.add(""+department.getOID());
                                                deptKey.add(department.getDepartment());
                                          }
                                          %> 
                                          <%= ControlCombo.draw("department","formElemen",null, ""+oidDepartment, deptValue, deptKey, "") %>
                                          </td>
                                        </tr>
                               <!--         <tr> 
                                          <td width="9%" height="34" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div>
                                          </td>
                                          <td width="88%"> : 
                                          <% /*
                                          String whereClauseSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+oidDepartment;
                                          Vector listSection = PstSection.list(0, 0, whereClauseSec, " SECTION ASC");										  
                                          Vector sectValue = new Vector(1,1);
                                          Vector sectKey = new Vector(1,1);
                                          sectValue.add("0");
                                          sectKey.add(" All... ");										  
                                          for(int d=0;d<listSection.size();d++)
                                          {
                                                Section section = (Section)listSection.get(d);
                                                sectValue.add(""+section.getOID());
                                                sectKey.add(section.getSection());
                                          }
                                          out.println(ControlCombo.draw("section",null,""+oidSection,sectValue,sectKey));
                                         */ %>
                                          </td>
                                        </tr> -->
                                        <tr> 
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left">Period</div>
                                          </td>
                                          <td width="88%">: <%//=ControlDate.drawDateMY("month_year",date==null || iCommand == Command.NONE?new Date():date,"MMM yyyy","formElemen",0,installInterval)%>
										  		<%
                                          Vector listPeriod = PstPeriod.list(0, 0, "", PstPeriod.fieldNames[PstPeriod.FLD_START_DATE]+" DESC");
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
                                            <table border="0" cellspacing="0" cellpadding="0" width="137">
                                              <tr> 
                                                <td width="16"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="View Attendance Summary"></a></td>
                                                <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="94" class="command" nowrap><a href="javascript:cmdView()">View Attendance Summary</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
									  
                                  <% if (((iCommand==Command.FIRST)||(iCommand==Command.NEXT)||(iCommand==Command.PREV)
      ||(iCommand==Command.LAST)||(iCommand==Command.LIST))) { 
                                      if(listPresenceReport.size() > 0 ){  %>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
<table border='0' cellspacing="2" cellpadding="2">
	<tr>
		<td>
			A = Absent
		</td>
		<td>
			V = Present
		</td>
		<td>
			DP = Day Payment
		</td>
		<td>
			EO = Extra Off
		</td>
		<td>
			Vi = Present In Only
		</td>
	</tr>
	<tr>
		<td>
			AL = Annual Leave
		</td>
		<td>
			X = Day Off
		</td>
		<td>
			LL = Long Leave
		</td>
		<td>
			PL = Paternity Leave
		</td>
		<td>
			Vo = Present Out Only
		</td>
	</tr>
	<tr>
		<td>
			ML = Maternity Leave
		</td>
		<td>
			H = Public Holiday
		</td>
		<td>
			UL = Unpaid Leave
		</td>
		<td>
			BL = Bereavement Leave
		</td>
		<td>
			
		</td>
	</tr>
</table>
                                                </tr>
                                                <tr> 
                                                  <td height="8" width="100%"><%=listData%></td>
                                                </tr>
                                              <tr>
                                                <td><%
						ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                %><%=ctrLine.drawImageListLimit(iCommand,vectSize,start,recordToGet)%>
                                                </td>
                                              </tr>
                                              <%if(privPrint){%>
                                                    <tr>
                                                        <td>
                                                            <table width="27%" border="0" cellspacing="1" cellpadding="1">
                                                                <tr>
                                                                    <td width="11%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0" alt="Print Attendance Summary Sheet"></a></td>
                                                                    <td width="89%"><b><a href="javascript:reportPdf()" class="buttonlink">Print Attendance Summary Sheet</a></b> </td>
                                                                </tr>
                                                            </table>
                                                         </td>
                                                    </tr>
                                                    <%}%>	
                                            </table>
                                            
                                          <%
                                          }else{%>
                                          <br/><hr>
                                          <table width="100%" border="0" cellspacing="1" cellpadding="1">
                                                <tr>
                                                    <td><center><h3><font color=red>NO PRESENCE DATA FOUND!</font></h3></center></td>
                                               </tr>
                                           </table>
                                          <% }
                                      } %>
                                  						  
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