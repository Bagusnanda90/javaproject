
<% 
/* 
 * Page Name  		:  absence_edit.jsp
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
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ABSENCE_MANAGEMENT, AppObjInfo.OBJ_ABSENCE_MANAGEMENT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- Jsp Block -->
<%
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request,"start");
long oidEmpSchedule = FRMQueryString.requestLong(request, "hidden_absence_id");
int intDateEmpSchedule = FRMQueryString.requestInt(request, "hidden_absence_date");
int maxDateOfMonth = FRMQueryString.requestInt(request, "max_date_of_month");
String source=FRMQueryString.requestString(request, "source");

SrcEmpSchedule srcEmpSchedule = new SrcEmpSchedule();
if(session.getValue("ABSENCE_MANAGEMENT")!=null){
    srcEmpSchedule = (SrcEmpSchedule)session.getValue("ABSENCE_MANAGEMENT");
	session.putValue("ABSENCE_MANAGEMENT", srcEmpSchedule); 
}

if(iCommand == Command.SAVE)
{
	Vector vectAbsenceDate = new Vector(1,1);
	Vector vectAbsenceReason = new Vector(1,1);
	Vector vectAbsenceNotes = new Vector(1,1);
	
	String[] strAbsenceDate = request.getParameterValues("absence_date");
	String[] strAbsenceReason = request.getParameterValues("absence_reason");
	String[] strAbsenceNotes = request.getParameterValues("absence_note");		
	
	// generate vect of Absence date
	if(strAbsenceDate!=null && strAbsenceDate.length>0){
		 for(int i=0; i<strAbsenceDate.length; i++){        
			try
			{
				vectAbsenceDate.add(String.valueOf(strAbsenceDate[i]));				
			}
			catch(Exception exc)
			{
				System.out.println("exc strAbsenceDate ...");
			}
		 }
	}
	
	// generate vect of Absence reason
	if(strAbsenceReason!=null && strAbsenceReason.length>0){
		 for(int i=0; i<strAbsenceReason.length; i++){        
			try
			{
				vectAbsenceReason.add(String.valueOf(strAbsenceReason[i]));				
			}
			catch(Exception exc)
			{
				System.out.println("exc strAbsenceReason ...");
			}
		 }
	}

	// generate vect of Absence notes
	if(strAbsenceNotes!=null && strAbsenceNotes.length>0){
		 for(int i=0; i<strAbsenceNotes.length; i++){        
			try
			{
				vectAbsenceNotes.add(String.valueOf(strAbsenceNotes[i]));				
			}
			catch(Exception exc)
			{
				System.out.println("exc strAbsenceNotes ...");
			}
		 }
	}
	
	PstEmpSchedule.updateScheduleByAbsenceManagement(oidEmpSchedule, vectAbsenceDate, vectAbsenceReason, vectAbsenceNotes);	
}

// mencari data employee schedule
EmpSchedule empSchedule = new EmpSchedule();

try
{
	empSchedule = PstEmpSchedule.fetchExc(oidEmpSchedule);
}
catch(Exception e)
{
	System.out.println("Exc when fetch empSchedule : "+e.toString());
}


// mencari data employee
Employee selectedEmployee = new Employee();
try
{
	selectedEmployee = PstEmployee.fetchExc(empSchedule.getEmployeeId());
}
catch(Exception e)
{
	System.out.println("Exc when fetch selectedEmployee : "+e.toString());
}


// mencari data periode
Period selectedPeriod = new Period();

try
{
	selectedPeriod = PstPeriod.fetchExc(empSchedule.getPeriodId());
}
catch(Exception e)
{
	System.out.println("Exc when fetch selectedPeriod : "+e.toString());
}

// utk menghandle data status pegawai
Vector vectStatus = new Vector(1,1);
vectStatus.add(String.valueOf(empSchedule.getStatus1()));
vectStatus.add(String.valueOf(empSchedule.getStatus2()));
vectStatus.add(String.valueOf(empSchedule.getStatus3()));
vectStatus.add(String.valueOf(empSchedule.getStatus4()));
vectStatus.add(String.valueOf(empSchedule.getStatus5()));
vectStatus.add(String.valueOf(empSchedule.getStatus6()));
vectStatus.add(String.valueOf(empSchedule.getStatus7()));
vectStatus.add(String.valueOf(empSchedule.getStatus8()));
vectStatus.add(String.valueOf(empSchedule.getStatus9()));
vectStatus.add(String.valueOf(empSchedule.getStatus10()));
vectStatus.add(String.valueOf(empSchedule.getStatus11()));
vectStatus.add(String.valueOf(empSchedule.getStatus12()));
vectStatus.add(String.valueOf(empSchedule.getStatus13()));
vectStatus.add(String.valueOf(empSchedule.getStatus14()));
vectStatus.add(String.valueOf(empSchedule.getStatus15()));
vectStatus.add(String.valueOf(empSchedule.getStatus16()));
vectStatus.add(String.valueOf(empSchedule.getStatus17()));
vectStatus.add(String.valueOf(empSchedule.getStatus18()));
vectStatus.add(String.valueOf(empSchedule.getStatus19()));
vectStatus.add(String.valueOf(empSchedule.getStatus20()));
vectStatus.add(String.valueOf(empSchedule.getStatus21()));
vectStatus.add(String.valueOf(empSchedule.getStatus22()));
vectStatus.add(String.valueOf(empSchedule.getStatus23()));
vectStatus.add(String.valueOf(empSchedule.getStatus24()));
vectStatus.add(String.valueOf(empSchedule.getStatus25()));
vectStatus.add(String.valueOf(empSchedule.getStatus26()));
vectStatus.add(String.valueOf(empSchedule.getStatus27()));
vectStatus.add(String.valueOf(empSchedule.getStatus28()));
vectStatus.add(String.valueOf(empSchedule.getStatus29()));
vectStatus.add(String.valueOf(empSchedule.getStatus30()));
vectStatus.add(String.valueOf(empSchedule.getStatus31()));

// utk menghandle data reason pegawai
Vector vectReason = new Vector(1,1);
vectReason.add(String.valueOf(empSchedule.getReason1()));
vectReason.add(String.valueOf(empSchedule.getReason2()));
vectReason.add(String.valueOf(empSchedule.getReason3()));
vectReason.add(String.valueOf(empSchedule.getReason4()));
vectReason.add(String.valueOf(empSchedule.getReason5()));
vectReason.add(String.valueOf(empSchedule.getReason6()));
vectReason.add(String.valueOf(empSchedule.getReason7()));
vectReason.add(String.valueOf(empSchedule.getReason8()));
vectReason.add(String.valueOf(empSchedule.getReason9()));
vectReason.add(String.valueOf(empSchedule.getReason10()));
vectReason.add(String.valueOf(empSchedule.getReason11()));
vectReason.add(String.valueOf(empSchedule.getReason12()));
vectReason.add(String.valueOf(empSchedule.getReason13()));
vectReason.add(String.valueOf(empSchedule.getReason14()));
vectReason.add(String.valueOf(empSchedule.getReason15()));
vectReason.add(String.valueOf(empSchedule.getReason16()));
vectReason.add(String.valueOf(empSchedule.getReason17()));
vectReason.add(String.valueOf(empSchedule.getReason18()));
vectReason.add(String.valueOf(empSchedule.getReason19()));
vectReason.add(String.valueOf(empSchedule.getReason20()));
vectReason.add(String.valueOf(empSchedule.getReason21()));
vectReason.add(String.valueOf(empSchedule.getReason22()));
vectReason.add(String.valueOf(empSchedule.getReason23()));
vectReason.add(String.valueOf(empSchedule.getReason24()));
vectReason.add(String.valueOf(empSchedule.getReason25()));
vectReason.add(String.valueOf(empSchedule.getReason26()));
vectReason.add(String.valueOf(empSchedule.getReason27()));
vectReason.add(String.valueOf(empSchedule.getReason28()));
vectReason.add(String.valueOf(empSchedule.getReason29()));
vectReason.add(String.valueOf(empSchedule.getReason30()));
vectReason.add(String.valueOf(empSchedule.getReason31()));

// utk menghandle data notes pegawai
Vector vectNotes = new Vector(1,1);
vectNotes.add(empSchedule.getNote1()!=null ? empSchedule.getNote1() : "");
vectNotes.add(empSchedule.getNote2()!=null ? empSchedule.getNote2() : "");
vectNotes.add(empSchedule.getNote3()!=null ? empSchedule.getNote3() : "");
vectNotes.add(empSchedule.getNote4()!=null ? empSchedule.getNote4() : "");
vectNotes.add(empSchedule.getNote5()!=null ? empSchedule.getNote5() : "");
vectNotes.add(empSchedule.getNote6()!=null ? empSchedule.getNote6() : "");
vectNotes.add(empSchedule.getNote7()!=null ? empSchedule.getNote7() : "");
vectNotes.add(empSchedule.getNote8()!=null ? empSchedule.getNote8() : "");
vectNotes.add(empSchedule.getNote9()!=null ? empSchedule.getNote9() : "");
vectNotes.add(empSchedule.getNote10()!=null ? empSchedule.getNote10() : "");
vectNotes.add(empSchedule.getNote11()!=null ? empSchedule.getNote11() : "");
vectNotes.add(empSchedule.getNote12()!=null ? empSchedule.getNote12() : "");
vectNotes.add(empSchedule.getNote13()!=null ? empSchedule.getNote13() : "");
vectNotes.add(empSchedule.getNote14()!=null ? empSchedule.getNote14() : "");
vectNotes.add(empSchedule.getNote15()!=null ? empSchedule.getNote15() : "");
vectNotes.add(empSchedule.getNote16()!=null ? empSchedule.getNote16() : "");
vectNotes.add(empSchedule.getNote17()!=null ? empSchedule.getNote17() : "");
vectNotes.add(empSchedule.getNote18()!=null ? empSchedule.getNote18() : "");
vectNotes.add(empSchedule.getNote19()!=null ? empSchedule.getNote19() : "");
vectNotes.add(empSchedule.getNote20()!=null ? empSchedule.getNote20() : "");
vectNotes.add(empSchedule.getNote21()!=null ? empSchedule.getNote21() : "");
vectNotes.add(empSchedule.getNote22()!=null ? empSchedule.getNote22() : "");
vectNotes.add(empSchedule.getNote23()!=null ? empSchedule.getNote23() : "");
vectNotes.add(empSchedule.getNote24()!=null ? empSchedule.getNote24() : "");
vectNotes.add(empSchedule.getNote25()!=null ? empSchedule.getNote25() : "");
vectNotes.add(empSchedule.getNote26()!=null ? empSchedule.getNote26() : "");
vectNotes.add(empSchedule.getNote27()!=null ? empSchedule.getNote27() : "");
vectNotes.add(empSchedule.getNote28()!=null ? empSchedule.getNote28() : "");
vectNotes.add(empSchedule.getNote29()!=null ? empSchedule.getNote29() : "");
vectNotes.add(empSchedule.getNote30()!=null ? empSchedule.getNote30() : "");
vectNotes.add(empSchedule.getNote31()!=null ? empSchedule.getNote31() : "");
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Absence Management</title>
<script language="JavaScript">
function cmdSave(){
	document.frm_absence.command.value="<%=Command.SAVE%>"; 
	document.frm_absence.action="absence_edit.jsp";
	document.frm_absence.submit();
}

function cmdBack(){
	document.frm_absence.command.value="<%=Command.BACK%>"; 
	document.frm_absence.action="absence_list.jsp";
	document.frm_absence.submit();
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
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnCancelOn.jpg','<%=approot%>/images/BtnBackOn.jpg')">
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
			  	  <table width="100%">
                    <tr>
                      <td align="left" ><font color="#FF6600" face="Arial"><strong>Attendance 
                        &gt; Absence Management</strong></font></td>
                      <td align="right"><font color="#FF6600" face="Arial"><strong><!--Help--></strong></font></td>
                    </tr></table>
                  <!-- #EndEditable --> 
            </strong></font>
	      </td>
        </tr>
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  style="background-color:<%=bgColorContent%>;"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                    <tr> 
                      <td valign="top"> 
                        <table style="border:1px solid <%=garisContent%>"  width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
								<form name="frm_absence" method="post" action="">                                                                    
                                                                  <input type="hidden" name="source" value="<%=source%>">
								  <input type="hidden" name="command" value="">
								  <input type="hidden" name="start" value="<%=start%>">
								  <input type="hidden" name="hidden_absence_id" value="<%=oidEmpSchedule%>">
								  <input type="hidden" name="hidden_absence_date" value="<%=intDateEmpSchedule%>">  
								  <input type="hidden" name="max_date_of_month" value="<%=maxDateOfMonth%>">									  								  
								  <table width="100%" cellspacing="0" cellpadding="0" >
									<tr> 
									  <td colspan="3"> 
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										  <tr> 
											<td valign="top"> 
											  <table width="100%" border="0" cellspacing="1" cellpadding="1">
												<tr> 
												  <td width="7%" height="18">Employee</td>
												  <td width="1%" height="18">:</td>
												  <td width="92%" height="18"><%=selectedEmployee.getFullName()%></td>
												</tr>
												<tr> 
												  <td width="7%">Period</td>
												  <td width="1%">:</td>
												  <td width="92%"><%=selectedPeriod.getPeriod()%></td>
												</tr>
												<tr> 
												  <td colspan="3"> 
													    <table width="70%" class="listgen" cellspacing="1">
                                                          <tr> 
														<td width="5%" class="listgentitle" rowspan="0" colspan="0" align="center" >Date</td>
														<td width="21%" class="listgentitle" rowspan="0" colspan="0" align="center" >Reason</td>
														<td width="74%" class="listgentitle" rowspan="0" colspan="0" align="center">Notes</td>
													  </tr>
													  <%
													  // semua absence employee akan ditampilkan 
													  if(intDateEmpSchedule == 0)
													  {
													  	  int j = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
														  int awal = j-1;
														  Date startPeriod = selectedPeriod.getStartDate();
														  int monthStartDate = Integer.parseInt(String.valueOf(startPeriod.getMonth()+1));
														  int yearStartDate =  Integer.parseInt(String.valueOf(startPeriod.getYear()+1900));
														  int dateStartDate =  Integer.parseInt(String.valueOf(startPeriod.getDate()));
														  GregorianCalendar periodStart = new GregorianCalendar(yearStartDate, monthStartDate-1, dateStartDate);
														  int maxDayOfMonth = periodStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
														  	
														  for(int i=0; i<maxDayOfMonth; i++)
														  {
														  	if(awal==maxDayOfMonth){
																awal=1;
															}
															else{
																awal=awal+1;
															}
															//out.println("awal"+awal);
														  	int presenceStatus = Integer.parseInt(String.valueOf(vectStatus.get(awal-1)));
														  	if(presenceStatus == PstEmpSchedule.STATUS_PRESENCE_ABSENCE)
															{														  													  
													  %>
													  <tr> 
														<td class="listgensell" width="5%" align="center" >
														<input type="hidden" name="absence_date" class="elemenForm" size="75" value="<%=(awal)%>">
														<%=(awal)%>
														</td>
														<td class="listgensell" width="30%" align="center" > 
                                                            <%
															/*Vector absence_value = new Vector(1,1);
															Vector absence_key = new Vector(1,1);
															String selectedReason = String.valueOf(vectReason.get(awal-1));

															// absence alpha
															absence_value.add(""+PstEmpSchedule.REASON_ABSENCE_ALPHA);
															absence_key.add(PstEmpSchedule.strAbsenceReason[PstEmpSchedule.REASON_ABSENCE_ALPHA]);

															// absence sick
															absence_value.add(""+PstEmpSchedule.REASON_ABSENCE_SICKNESS);
															absence_key.add(PstEmpSchedule.strAbsenceReason[PstEmpSchedule.REASON_ABSENCE_SICKNESS]);

															// absence dispensation
															absence_value.add(""+PstEmpSchedule.REASON_ABSENCE_DISPENSATION);
															absence_key.add(PstEmpSchedule.strAbsenceReason[PstEmpSchedule.REASON_ABSENCE_DISPENSATION]);															

															out.println(ControlCombo.draw("absence_reason", "formElemen", null, selectedReason, absence_value, absence_key, ""));*/
															//untuk reason dinamis yang langsung ngambil dari tabel
															Vector reason_value = new Vector(1,1);
															Vector reason_key = new Vector(1,1);   
															Vector listReason = new Vector(1,1);
															String selectedReason = String.valueOf(vectReason.get(awal-1));
															listReason = PstReason.list(0, 0, "", "REASON");
															for (int r = 0; r < listReason.size(); r++) 
															{
																Reason reason = (Reason) listReason.get(r);
																reason_key.add(reason.getReason());
																reason_value.add(String.valueOf(reason.getNo()));
															}
															%>
															<%= ControlCombo.draw("absence_reason","formElemen",null, ""+selectedReason, reason_value, reason_key, "") %> </td>
															
													
														<td class="listgensell" width="65%" align="center" >                                                                
															  <input type="text" name="absence_note" class="elemenForm" size="75" value="<%=String.valueOf(vectNotes.get(awal-1))%>">
														</td>
													  </tr>
													  <%


                                                                                                                        }
													  	  }
													  }
													  // absence dengan tanggal terpilih saja yang ditampilkan
													  else
													  {
													  %>
													  <tr> 
														<td class="listgensell" width="5%" align="center" >
														<input type="hidden" name="absence_date" class="elemenForm" size="75" value="<%=intDateEmpSchedule%>">														
														<%=intDateEmpSchedule%>
														</td>
														<td class="listgensell" width="30%" align="center" > 
                                                            <%
															Vector absence_value = new Vector(1,1);
															Vector absence_key = new Vector(1,1);
															String selectedReason = String.valueOf(vectReason.get(intDateEmpSchedule-1));															
															//out.println("vectReason  "+vectReason);
															// absence alpha
															/*absence_value.add(""+PstEmpSchedule.REASON_ABSENCE_ALPHA);
															absence_key.add(PstEmpSchedule.strAbsenceReason[PstEmpSchedule.REASON_ABSENCE_ALPHA]);

															// absence sick
															absence_value.add(""+PstEmpSchedule.REASON_ABSENCE_SICKNESS);
															absence_key.add(PstEmpSchedule.strAbsenceReason[PstEmpSchedule.REASON_ABSENCE_SICKNESS]);

															// absence dispensation
															absence_value.add(""+PstEmpSchedule.REASON_ABSENCE_DISPENSATION);
															absence_key.add(PstEmpSchedule.strAbsenceReason[PstEmpSchedule.REASON_ABSENCE_DISPENSATION]);
													
															out.println(ControlCombo.draw("absence_reason", "formElemen", null, selectedReason, absence_value, absence_key, ""));*/
															Vector reason_value = new Vector(1,1);
															Vector reason_key = new Vector(1,1);   
															Vector listReason = new Vector(1,1);
															//String selectedReason = String.valueOf(vectReason.get(awal-1));
															
															listReason = PstReason.list(0, 0, "", "REASON");
															for (int r = 0; r < listReason.size(); r++) 
															{
																Reason reason = (Reason) listReason.get(r);
																reason_key.add(reason.getReason());
																reason_value.add(String.valueOf(reason.getNo()));
															}
															%>
															<%= ControlCombo.draw("absence_reason","formElemen",null, ""+selectedReason, reason_value, reason_key, "") %> </td>
															
														<td class="listgensell" width="65%" align="center" > 
															  <input type="text" name="absence_note" class="elemenForm" size="75" value="<%=String.valueOf(vectNotes.get(intDateEmpSchedule-1))%>">
														</td>
													  </tr>													  
													  <%	  
													  }
													  %>
													</table>
												  </td>
												</tr>
												<tr> 
												  <td colspan="3">&nbsp;</td>
												</tr>
												    <tr> 
                                                      <td colspan="3"> 
                                                        <table border="0" cellspacing="0" cellpadding="0" width="567">
                                                          <tr> 
														  <%if(privUpdate){%>
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                            <td width="26"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3001','','<%=approot%>/images/BtnSaveOn.jpg',1)"><img name="Image3001" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Employee's Absence"></a></td>
                                                            <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                            <td nowrap width="224"><a href="javascript:cmdSave()" class="command">Save 
                                                              Employee's Absence</a></td>
															  <%}%>
                                                            <td nowrap width="26"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image300','','<%=approot%>/images/BtnBackOn.jpg',1)"><img name="Image300" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back To List Employee's Absence"></a></td>
                                                            <td nowrap width="11">&nbsp;</td>
                                                            <td nowrap width="272"><a href="javascript:cmdBack()" class="command">Back 
                                                              To List Employee's 
                                                              Absence</a></td>
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
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
