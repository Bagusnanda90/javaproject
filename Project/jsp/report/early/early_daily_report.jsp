<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.*,
                  com.dimata.harisma.entity.search.SrcLateness,
                  com.dimata.harisma.entity.attendance.EmpSchedule,
                  com.dimata.harisma.entity.attendance.Presence,
                  com.dimata.qdep.db.DBHandler,
                  com.dimata.harisma.entity.attendance.PstPresence,
                  com.dimata.gui.jsp.ControlList,
                  com.dimata.harisma.entity.employee.Employee,
                  com.dimata.harisma.session.early.SessEmployeeEarly,
                  com.dimata.util.DateCalc,
                  com.dimata.util.Formater,
                  com.dimata.qdep.form.FRMQueryString,
                  com.dimata.util.Command,
                  com.dimata.harisma.session.early.EarlyDaily,
                  com.dimata.gui.jsp.ControlCombo,
                  com.dimata.harisma.entity.masterdata.*,
                  com.dimata.gui.jsp.ControlDate"%>
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LATENESS_REPORT, AppObjInfo.OBJ_LATENESS_DAILY_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/    
//boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<%!
/** 
 * untuk mencari lateness employee
 * @param timeIn
 * @param timeInSchedule
 * @return
 * @created by gadnyana 
 * edited by Edhy on August 18, 2004 
 */
public String cekLateEmployee(Date timeOut, Date timeOutSchedule,int  catType,Date timeIn)
{
	String formTime = "";
	try
	{
		// diproses jika "timeIn on schedule!=null" dan "time In on presence!=null"
		if(timeOut != null && timeOutSchedule != null)
		{
			String strDurationHour = "";
			String strDurationMin = "";
			// waktu timeIn on schedule, date-nya make date-month-year dari timeIn karena memang field schedule hanya
			// berisi data HOUR dan MINUTES 
			Date dtSchedule = new Date();
			dtSchedule.setDate(timeOut.getDate());
			dtSchedule.setMonth(timeOut.getMonth());
			dtSchedule.setYear(timeOut.getYear());
			if((catType==PstScheduleCategory.CATEGORY_ACCROSS_DAY) && (timeOut.getHours() > timeOutSchedule.getHours()) ){
				dtSchedule.setHours(timeOutSchedule.getHours()+24);
			}else{
				dtSchedule.setHours(timeOutSchedule.getHours());
			}
			
			dtSchedule.setMinutes(timeOutSchedule.getMinutes()- SessEmployeeEarly.TIME_EARLY);
			dtSchedule.setSeconds(0);
			
			// waktu time out on presence
			Date dtPresence = new Date();
			dtPresence.setTime(timeOut.getTime());
			dtPresence.setSeconds(0);			
			
			// ambil selisih antara timeOut schedule dengan timeOut presence (dalam detik)
			long lTimeSchld = dtSchedule.getTime();
			long lTimeActual = dtPresence.getTime();
			
			long iDurationSec = lTimeActual/1000 - lTimeSchld/1000;
			

			// faktor pengali diisi -1 jika selisihnya minus(-) dan sebaliknya
			int intMult = (iDurationSec < -1) ? -1 : 1;
			
			// nilai durasi dalam detik sesuai perhitungan di atas, dikalikan dengan faktor pengali
			iDurationSec = iDurationSec * intMult;			
			
			// hitung durasi dalam menit, dilakukan jika durasi detiknya minimal 60 detik						
			long iDurationMin = 0;			
			if(iDurationSec >= 60)
			{
				iDurationMin = (iDurationSec - (iDurationSec % 60)) / 60;
				iDurationSec = iDurationSec % 60;
			}
			
			// hitung durasi dalam jam, dilakukan jika durasi menitnya minimal 60 menit			
			long iDurationHour = 0;
					
			if(iDurationMin >= 60)
			{
				
				iDurationHour = (iDurationMin - (iDurationMin % 60)) / 60;
				iDurationMin = iDurationMin % 60;
				iDurationSec = iDurationSec % 60;				
			}

			// memasukkan data hasil proses ke vector durasi jam
				
			if( (iDurationHour * intMult) < 0 )
			{
				strDurationHour = (iDurationHour * intMult) + "h, ";
			}

			// memasukkan data hasil proses ke vector durasi menit
			//System.out.println("(iDurationMin * intMult)..."+(iDurationMin * intMult) );
			if( (iDurationMin * intMult) < 0 )
			{
				
				strDurationMin = ""+(iDurationMin * intMult) +"m ";
			}

			formTime = strDurationHour + strDurationMin;
			//formTime = ""+strDurationMin;
		}
	}
	catch(Exception e)
	{
		System.out.println("Exc on cekLateEmployee : " + e.toString());	
	}
	return formTime;
}


/**
 * untuk menampilkan/menggambar daftar employee lateness
 * @param listLate
 * @param dtSch
 * @return
 * created by gadnyana
 */
public Vector drawList(Vector listLate, Date dtSch) 
{
	ControlList ctrlist = new ControlList();
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No","1%", "0", "0");
	ctrlist.addHeader("Payroll","6%", "0", "0");
	ctrlist.addHeader("Employee","10%", "0", "0");
	ctrlist.addHeader("Symbol","4%", "0", "0");
	ctrlist.addHeader("Roster","4%", "0", "0");
	ctrlist.addHeader("Actual","4%", "0", "0");
	ctrlist.addHeader("Early Home","4%", "0", "0");

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();
	Vector result = new Vector(1,1);	
	Vector list = new Vector(1,1);

    int num = 0;
	
	// iterasi sebanyak data employee yang lateness	
	for (int i = 0; i < listLate.size(); i++) 
	{
		EarlyDaily earlyDaily = (EarlyDaily)listLate.get(i);

        Vector rowx = new Vector(1,1);
		Vector pres = new Vector(1,1);
        pres.add(""+earlyDaily.getDepId());

		// jika terdiri 2 shift (split shift)
        if((earlyDaily.getActualOut()!=null) && (earlyDaily.getActualOutII()!=null))
		{ 
			//------------------------------------------
			ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
            try{
                scheduleSymbol = PstScheduleSymbol.fetchExc(earlyDaily.getSchSymbolId());
            }catch(Exception exc){
                System.out.println("ERROR :"+exc.toString());
            }
			System.out.println("scheduleSymbol drawList..."+scheduleSymbol.getScheduleCategoryId());
			//-----------------------------------------
            Date timeOut1st = earlyDaily.getActualOut();
            Date timeOut2st = earlyDaily.getActualOutII();
			Date timeIn = earlyDaily.getActualOut();
	        String formTime = cekLateEmployee(timeOut1st, earlyDaily.getSchldOut(),earlyDaily.getCatType(),timeIn);
            num++;
            rowx.add(""+num);pres.add(""+num);
            rowx.add(earlyDaily.getEmpNum());pres.add(earlyDaily.getEmpNum());
            rowx.add(earlyDaily.getEmpName());pres.add(earlyDaily.getEmpName());
            rowx.add(earlyDaily.getSchldSymbol());pres.add(earlyDaily.getSchldSymbol());
            rowx.add(earlyDaily.getSchldOut() != null?Formater.formatTimeLocale(earlyDaily.getActualOut()):""); pres.add(earlyDaily.getActualOut() != null?Formater.formatTimeLocale(earlyDaily.getActualOut()):"");
            rowx.add(timeOut1st != null?Formater.formatTimeLocale(timeOut1st):""); pres.add(timeOut1st != null?Formater.formatTimeLocale(timeOut1st):"");
            rowx.add(""+formTime); pres.add(formTime);

            lstData.add(rowx);
            list.add(pres);


            
            formTime = cekLateEmployee(timeOut2st, scheduleSymbol.getTimeOut(),earlyDaily.getCatType(),timeIn);

            if(formTime.length()>0)
			{
                rowx = new Vector(1,1);
                pres = new Vector(1,1);
                pres.add("-1");

                rowx.add("");pres.add("");
                rowx.add("");pres.add("");
                rowx.add("");pres.add("");
                rowx.add(scheduleSymbol.getSymbol());pres.add(scheduleSymbol.getSymbol());
                rowx.add(scheduleSymbol.getTimeOut() != null?Formater.formatTimeLocale(scheduleSymbol.getTimeOut()):""); pres.add(scheduleSymbol.getTimeOut() != null?Formater.formatTimeLocale(scheduleSymbol.getTimeOut()):"");
                rowx.add(timeOut2st != null?Formater.formatTimeLocale(timeOut2st):""); pres.add(timeOut2st != null?Formater.formatTimeLocale(timeOut2st):"");
                rowx.add(""+formTime); pres.add(formTime);

                lstData.add(rowx);
                list.add(pres);
            }
        }
		else
		{
			//------------------------------------------
			ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
            try{
                scheduleSymbol = PstScheduleSymbol.fetchExc(earlyDaily.getSchSymbolId());
            }catch(Exception exc){
                System.out.println("ERROR :"+exc.toString());
            }
			System.out.println("scheduleSymbol drawList..."+earlyDaily.getCatType());
			//-----------------------------------------
            Date timeOut1st = earlyDaily.getActualOut();
			Date timeIn = earlyDaily.getActualOut();
            String formTime = cekLateEmployee(timeOut1st, earlyDaily.getSchldOut(),earlyDaily.getCatType(),timeIn);

            num++;
            rowx.add(""+num);pres.add(""+num);
            rowx.add(earlyDaily.getEmpNum());pres.add(earlyDaily.getEmpNum());
            rowx.add(earlyDaily.getEmpName());pres.add(earlyDaily.getEmpName());
            rowx.add(earlyDaily.getSchldSymbol());pres.add(earlyDaily.getSchldSymbol());
            rowx.add(earlyDaily.getSchldOut() != null?Formater.formatTimeLocale(earlyDaily.getSchldOut()):""); pres.add(earlyDaily.getSchldOut() != null?Formater.formatTimeLocale(earlyDaily.getSchldOut()):"");
            rowx.add(timeOut1st != null?Formater.formatTimeLocale(timeOut1st):""); pres.add(timeOut1st != null?Formater.formatTimeLocale(timeOut1st):"");
            rowx.add(formTime); pres.add(formTime);

            lstData.add(rowx);
            list.add(pres);
        }
	}
	
	result.add(ctrlist.drawList());
	result.add(list);	

	return result;
}
%>

<%
try{
	session.removeValue("EARLY_HOME");
}catch(Exception e){}

// get data from form request
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,"department");
long oidSection = FRMQueryString.requestLong(request,"section");
Date date = FRMQueryString.requestDate(request,"date");

Vector listEarlyHome = new Vector(1,1);
if(iCommand == Command.LIST)
{
	listEarlyHome = SessEmployeeEarly.listEarlyDaily(oidDepartment, date, oidSection);
}

Vector vEarlyHome = drawList(listEarlyHome,date);
String drawList = "";
Vector vctDailyearly = new Vector(1,1);
vctDailyearly.add(date);
vctDailyearly.add(""+oidDepartment);
if(vEarlyHome != null && vEarlyHome.size()>0){
	drawList = (String)vEarlyHome.get(0);
	Vector list = (Vector)vEarlyHome.get(1);
	vctDailyearly.add(list);
}
vctDailyearly.add(""+oidSection);

if(session.getValue("EARLY_HOME")!=null){
	session.removeValue("EARLY_HOME");
}
session.putValue("EARLY_HOME",vctDailyearly);
%>
<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Report Early Home Daily</title>
<script language="JavaScript">
function cmdView(){
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.action="early_daily_report.jsp";
	document.frpresence.submit();
}

function reportPdf(){
	var linkPage = "<%=printroot%>.report.employee.DailyEarlyHomePdf";
	window.open(linkPage,"reportPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");  			
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
	document.frpresence.action="early_daily_report.jsp"; 
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Attendance 
                  &gt; Lateness<!-- #EndEditable --> </strong></font> </td>
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
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                          </td>
                                          <td width="88%"> : 
                                            <%
                                          Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");
										  Vector deptValue = new Vector(1,1);
										  Vector deptKey = new Vector(1,1);
                                          deptValue.add("0");
                                          deptKey.add("ALL");

                                          for(int d=0;d<listDepartment.size();d++){
										  	Department department = (Department)listDepartment.get(d);
											deptValue.add(""+department.getOID());
											deptKey.add(department.getDepartment());
										  }
										  %>
                                            <%=ControlCombo.draw("department",null,""+oidDepartment,deptValue,deptKey,"onChange=\"javascript:cmdUpdateDep()\"")%></td>
                                        </tr>
										<!-- Ini Yunny yang ubah-->
										<tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div>
                                          </td>
                                          <td width="88%"> : 
                                            <%
										  Vector listSection = new Vector(1,1);
										  Vector secValue = new Vector(1,1);
										  Vector secKey = new Vector(1,1);
										  secValue.add("0");
                                          secKey.add("ALL");
										  if(oidDepartment==0){
												if(listDepartment!=null && listDepartment.size()>0){
													Department d = (Department)listDepartment.get(0);
													oidDepartment = d.getOID();
												}
											}
											
											String wh = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID]+"="+oidDepartment;
											listSection = PstSection.list(0,0, wh, PstSection.fieldNames[PstSection.FLD_SECTION]);
										
											  for(int s=0;s<listSection.size();s++){
												Section section = (Section)listSection.get(s);
												secValue.add(""+section.getOID());
												secKey.add(section.getSection());
											  }
										  %>
										    <%=ControlCombo.draw("section",null,""+oidSection,secValue,secKey)%></td>

                                        </tr>
										<!-- Yunny ubah sampai disini-->
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" align="right" nowrap> 
                                            <div align="left">Date </div>
                                          </td>
                                          <td width="88%">: <%=ControlDate.drawDate("date",date==null || iCommand == Command.NONE?new Date():date,"formElemen",0,installInterval)%></td>
                                        </tr>
                                        <tr> 
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" nowrap> 
                                            <div align="left"></div>
                                          </td>
                                          <td width="88%"> 
                                            <table border="0" cellspacing="0" cellpadding="0" width="137">
                                              <tr> 
                                                <td width="16"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Lateness"></a></td>
                                                <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="94" class="command" nowrap><a href="javascript:cmdView()">View 
                                                  Early Home</a></td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                      </table>
									  <% if(iCommand == Command.LIST){%>
									  <table width="100%" border="0" cellspacing="2" cellpadding="2">
									  <% if(listEarlyHome != null && listEarlyHome.size()>0){%>
									    <tr><td><hr></td></tr>
                                          <tr>
											<td>
											<%   											
											if(drawList!=null && drawList.length()>0)
											{
												out.println(drawList);
											}
											else										
											{    
												out.println("<div class=\"msginfo\">&nbsp;&nbsp;No early home data found ...</div>");
											}
											%>
											</td>
										  </tr>
										  <%if(privPrint){%>
										  <tr>
											<td>
											  <table width="27%" border="0" cellspacing="1" cellpadding="1">
                                              <tr>
												  <td width="17%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
												  <td width="83%"><b><a href="javascript:reportPdf()" class="buttonlink">Print 
													Early Home</a></b>
												  </td>
												</tr>
											  </table>
											 </td>
										  </tr>
										  <%}%>
									    <%}else{%>
									    <tr><td><hr></td></tr>										
										  <tr>
											<td>
											<%
											out.println("<div class=\"msginfo\">&nbsp;&nbsp;No early home data found ...</div>");											
											%>
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
