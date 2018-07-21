<%@ page language="java" %>
<!-- package java -->
<%@ page import ="java.util.*,
                  com.dimata.harisma.entity.attendance.EmpSchedule,
                  com.dimata.harisma.entity.attendance.Presence,
                  com.dimata.qdep.db.DBHandler,
                  com.dimata.harisma.entity.attendance.PstPresence,
                  com.dimata.gui.jsp.ControlList,
                  com.dimata.harisma.entity.employee.Employee,
                  com.dimata.harisma.session.early.SessEmployeeEarly,
                  com.dimata.harisma.entity.masterdata.ScheduleSymbol,
                  com.dimata.util.DateCalc,
				  com.dimata.util.CalendarCalc,
                  com.dimata.qdep.form.FRMQueryString,
                  com.dimata.util.Command,
                  com.dimata.gui.jsp.ControlCombo,
                  com.dimata.harisma.entity.masterdata.PstDepartment,
                  com.dimata.harisma.entity.masterdata.Department,
                  com.dimata.gui.jsp.ControlDate,
                  com.dimata.harisma.session.early.EarlyWeekly,
                  com.dimata.harisma.entity.masterdata.PstScheduleSymbol"%>
<!-- package qdep -->
<%@ include file = "../../main/javainit.jsp" %>
<!-- JSP Block -->
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_REPORTS, AppObjInfo.G2_LATENESS_REPORT, AppObjInfo.OBJ_LATENESS_WEEKLY_REPORT); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privPrint 	= userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>

<%!
public String getStrWeek(int idx)
{
	String str = "";
	switch(idx){
		case 1:
			str = "1st Week";
			break;
		case 2:
			str = "2nd Week";
			break;
		case 3:
			str = "3rd Week";
			break;
		case 4:
			str = "4th Week";
			break;
		case 5:
			str = "5th Week";
			break;
		case 6:
			str = "6th Week";
			break;
		case 7:
			str = "7th Week";
			break;
	}
	return str;
}

/** 
 * untuk mencari early Home employee
 * @param timeIn
 * @param timeInSchedule
 * @return
 * @created by Yunny 
 */
public Vector cekEarlyEmployee(Date timeOut, Date timeOutSchedule, ScheduleSymbol scheduleSymbol,Date timeInSchedule,Date timeIn)
{
	Vector vlEarly = new Vector(1,1);
	long scheduleCategoryId=PstScheduleCategory.getOID();
	try
	{
		// diproses jika "timeIn on schedule!=null" dan "time In on presence!=null"
		if(timeOut != null && timeOutSchedule != null)
		{
					
			// waktu timeIn on schedule, date-nya make date-month-year dari timeIn karena memang field schedule hanya
			// berisi data HOUR dan MINUTES 
			Date dtSchedule = new Date();  
			if((scheduleSymbol.getScheduleCategoryId()==scheduleCategoryId) &&  (timeOut.getHours() !=0) ){
				//Date timeIn = scheduleSymbol.getTimeIn();
				if(timeIn.getDate()==timeOut.getDate()){
					dtSchedule.setDate(timeOut.getDate());
					dtSchedule.setMonth(timeOut.getMonth());
					dtSchedule.setYear(timeOut.getYear());
					dtSchedule.setHours(timeOutSchedule.getHours());  
					dtSchedule.setMinutes(timeOutSchedule.getMinutes());
					dtSchedule.setSeconds(0);
				
				}else{
					dtSchedule.setDate(timeOut.getDate());
					dtSchedule.setMonth(timeOut.getMonth());
					dtSchedule.setYear(timeOut.getYear());
					dtSchedule.setHours(timeOutSchedule.getHours());  
					dtSchedule.setMinutes(timeOutSchedule.getMinutes()+ 1440);
					dtSchedule.setSeconds(0);
				}
			}
			else
			{
				dtSchedule.setDate(timeOut.getDate());
				dtSchedule.setMonth(timeOut.getMonth());
				dtSchedule.setYear(timeOut.getYear());
				dtSchedule.setHours(timeOutSchedule.getHours());  
				dtSchedule.setMinutes(timeOutSchedule.getMinutes()- SessEmployeeEarly.TIME_EARLY);
				dtSchedule.setSeconds(0);			
			}
			
			
						
			// waktu time Out on presence
			Date dtPresence = new Date();
			dtPresence.setTime(timeOut.getTime());
			dtPresence.setSeconds(0);			
			
			// ambil selisih antara timeIn schedule dengan timeIn presence (dalam detik)
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
				vlEarly.add(""+(iDurationHour * intMult));
			}
			else
			{
				vlEarly.add("0");
			}

			// memasukkan data hasil proses ke vector durasi menit
			if( (iDurationMin * intMult) < 0 )
			{
				vlEarly.add(""+(iDurationMin * intMult));
			}
			else
			{
				vlEarly.add("0");
			}
		}
		
		// tidak diproses karena salah satu "timeIn on schedule" dan/atau "time In on presence"	adalah null	 
		else
		{
			vlEarly.add("0");
			vlEarly.add("0");
		}
	}
	catch(Exception e)
	{
		System.out.println("Exc on cekLateEmployee : " + e.toString());
	}
	return vlEarly;
}


/**
 * untuk menampilkan/menggambar daftar employee early home
 * @param listLate
 * @param dtSch
 * @param stDate
 * @param endDate  
 * @return
 * created by yunny
 */
public Vector drawList(Vector listEarly, Date dtSch, Date stDate, Date endDate) 
{
    Vector result = new Vector(1,1);    
	ControlList ctrlist = new ControlList();
	
    int max = endDate.getDate() - stDate.getDate();
	
	ctrlist.setAreaWidth("100%");
	ctrlist.setListStyle("listgen");
	ctrlist.setTitleStyle("listgentitle");
	ctrlist.setCellStyle("listgensell");
	ctrlist.setHeaderStyle("listgentitle");
	ctrlist.addHeader("No","2%", "2", "0");
	ctrlist.addHeader("Payroll","6%", "2", "0");
    ctrlist.addHeader("Employee","12%", "2", "0");
	ctrlist.addHeader("Early Home (hour, minutes)","15%", "0", ""+(max+1)+"");     

    for(int k=stDate.getDate(); k<=endDate.getDate() ;k++)
	{
	    ctrlist.addHeader(""+k,"3%", "0", "0");
    }
	ctrlist.addHeader("Total Early Home","3%", "2", "0");

	ctrlist.setLinkRow(0);
	ctrlist.setLinkSufix("");
	Vector lstData = ctrlist.getData();
	Vector lstLinkData = ctrlist.getLinkData();
	ctrlist.setLinkPrefix("javascript:cmdEdit('");
	ctrlist.setLinkSufix("')");
	ctrlist.reset();

	Vector list = new Vector(1,1);

    int num = 0;
	
	// iterasi sebanyak data employee yang lateness
	for (int i = 0; i < listEarly.size(); i++) 
	{
		EarlyWeekly earlyWeekly = (EarlyWeekly)listEarly.get(i);

        String empNum = earlyWeekly.getEmpNum();
        String empName = earlyWeekly.getEmpName();
        Vector empSchedules1st = earlyWeekly.getEmpSchedules1st();
        Vector empActualIn1st = earlyWeekly.getEmpIn1st();
        Vector empActualOut1st = earlyWeekly.getEmpOut1st();
        Vector empSchedules2nd = earlyWeekly.getEmpSchedules2nd();
        Vector empActualIn2nd = earlyWeekly.getEmpIn2nd();
        Vector empActualOut2nd = earlyWeekly.getEmpOut2nd();

        Vector rowx = new Vector(1,1);
		Vector pres = new Vector(1,1);

        Vector vt1St = new Vector(1,1);
        Vector vt2St = new Vector(1,1);
        int totHour1st = 0;
        int totMenit1st = 0;
        int totHour2st = 0;
        int totMenit2st = 0;
        int kk = 0;
		
		// iterasi sebanyak jumlah hari dalam selected week per employee (dalam satu baris data)
        for(int x=stDate.getDate(); x <=endDate.getDate() ;x++)
		{
            // variable untuk menampung data lateness (hour,minutes) dalam report ini   
			String formTime = "";
            String formTime2 = "";
			
			// data timeInPresence 1st dan 2nd
            Date timeIn = (Date)empActualOut1st.get(kk);
            Date actualIn2nd = (Date)empActualIn2nd.get(kk);

			// data timeOutPresence 1st dan 2nd
            Date timeOut = (Date)empActualOut1st.get(kk);
            Date actualOut2nd = (Date)empActualOut2nd.get(kk);
			
			// jika terdiri 2 shift (split shift)
            if( (timeOut!=null && timeOut!=null) && (actualOut2nd!=null && actualOut2nd!=null) )
			{ 
				// proses untuk schedule I		
                long oidSch1St = Long.parseLong((String)empSchedules1st.get(kk));
                ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                try
				{
                    scheduleSymbol = PstScheduleSymbol.fetchExc(oidSch1St);
                }
				catch(Exception e)
				{
					System.out.println("Exc when PstScheduleSymbol.fetchExc(oidSch1St) : " + e.toString());
				}
				
				// mengambil data lateness dengan memanggil method  "cekLateEmployee" 
                Vector vect = cekEarlyEmployee(timeOut, scheduleSymbol.getTimeOut(),scheduleSymbol,scheduleSymbol.getTimeOut(),timeIn); 
                if((Integer.parseInt(""+vect.get(0))!=0) || (Integer.parseInt(""+vect.get(1))!=0))
				{
                    if(Integer.parseInt(""+vect.get(0))!=0)
					{
                        formTime = vect.get(0) + "h";
                    }
					
                    if(Integer.parseInt(""+vect.get(1))!=0)
					{
                        if(formTime.length()>0)
						{
                            formTime = formTime +", "+vect.get(1) + "m";
                        }
						else
						{
                            formTime = vect.get(1) + "m";
                        }
                    }
					
					// simpan total durasi early untuk employee bersangkutan
                    totHour1st = totHour1st + Integer.parseInt(""+vect.get(0));
                    totMenit1st = totMenit1st + Integer.parseInt(""+vect.get(1));
                }
				
				
				// proses untuk schedule II
                long oidSch2nd = Long.parseLong((String)empSchedules2nd.get(kk));				
                try
				{
                    scheduleSymbol = PstScheduleSymbol.fetchExc(oidSch2nd);
                }
				catch(Exception e)
				{
					System.out.println("Exc when PstScheduleSymbol.fetchExc(oidSch2nd) : " + e.toString());				
				}
				
				// mengambil data early home dengan memanggil method  "cekEarlyEmployee" 
                vect = cekEarlyEmployee(actualOut2nd, scheduleSymbol.getTimeOut(),scheduleSymbol,scheduleSymbol.getTimeIn(),timeIn); 
                if((Integer.parseInt(""+vect.get(0))!=0) || (Integer.parseInt(""+vect.get(1))!=0))
				{
                    if(Integer.parseInt(""+vect.get(0))!=0)
					{
                        formTime2 = vect.get(0) + "h";
                    }
					
                    if(Integer.parseInt(""+vect.get(1))!=0)
					{
                        if(formTime2.length()>0)
						{
                            formTime2 = formTime2 +", "+vect.get(1) + "m";
                        }
						else
						{
                            formTime2 = vect.get(1) + "m";
                        }
                    }

					// simpan total durasi late untuk employee bersangkutan
                    totHour2st = totHour2st + Integer.parseInt(""+vect.get(0)); 
                    totMenit2st = totMenit2st + Integer.parseInt(""+vect.get(1)); 
                }
                vt1St.add(formTime);
                vt2St.add(formTime2);				
            }
			
			
			// jika terdiri 1 shift (regular, night shift dll)			
			else if (timeIn!=null && timeOut!=null)
			{ 
                long oidSch1St = Long.parseLong((String)empSchedules1st.get(kk));
                ScheduleSymbol scheduleSymbol = new ScheduleSymbol();
                try
				{
                    scheduleSymbol = PstScheduleSymbol.fetchExc(oidSch1St);
                }
				catch(Exception e)
				{
					System.out.println("Exc when PstScheduleSymbol.fetchExc(oidSch1St) : " + e.toString());								
				}
				
				// mengambil data early home dengan memanggil method  "cekEarlyEmployee" 				
                Vector vect = cekEarlyEmployee(timeOut, scheduleSymbol.getTimeOut(),scheduleSymbol,scheduleSymbol.getTimeOut(),timeIn); 
				System.out.println("vect....."+vect);
                if((Integer.parseInt(""+vect.get(0))!=0) || (Integer.parseInt(""+vect.get(1))!=0))
				{
                    if(Integer.parseInt(""+vect.get(0))!=0)
					{
                        formTime = vect.get(0) + "h";
                    }
					
                    if(Integer.parseInt(""+vect.get(1))!=0)
					{
                        if(formTime.length()>0)
						{
                            formTime = formTime +", "+vect.get(1) + "m";
                        }
						else
						{
                            formTime = vect.get(1) + "m";
                        }
                    }
					
					// simpan total durasi early untuk employee bersangkutan					
                    totHour1st = totHour1st + Integer.parseInt(""+vect.get(0)); 
                    totMenit1st = totMenit1st + Integer.parseInt(""+vect.get(1)); 
					
                }

                vt1St.add(formTime);
                vt2St.add("");
            }
			
			// jika schedule off, absence, DP, AL atau LL						
			else
			{
                vt1St.add("");
                vt2St.add("");			
			}
			
            kk++;
        }


		
        boolean bool = false;
        num++;
        pres.add(""+0);

        rowx.add(""+num);
        pres.add(""+num);

        rowx.add(empNum);
        pres.add(empNum);

        rowx.add(empName);
        pres.add(empName);

        for(int k=0; k<vt1St.size(); k++)
		{
            rowx.add(""+vt1St.get(k));
            pres.add(""+vt1St.get(k));

            if(!vt2St.get(k).toString().equals(""))
                bool = true;
        }

        String strTotal = "";
        if(totMenit1st!=0){
            int jm = totMenit1st/60;
            if(jm!=0)
			{
                totHour1st = totHour1st + jm;
                if((totMenit1st % 60)!=0)
                    totMenit1st = totMenit1st % 60;
            }
        }
		
        if(totHour1st!=0)
		{
            strTotal = totHour1st+"h";
        }
		
        if(totMenit1st!=0)
		{
            if(strTotal.length()>0)
			{
                strTotal = strTotal+ ", "+ totMenit1st+"m";
            }
			else
			{
                strTotal = totMenit1st+"m";
            }
        }
        rowx.add(strTotal);
        pres.add(strTotal);

        lstData.add(rowx);
        list.add(pres);

        if(bool)
		{
            pres = new Vector(1,1);
            rowx = new Vector(1,1);
            pres.add("-1");
			

            pres.add("");
            rowx.add("");
            pres.add("");
            rowx.add("");
            pres.add("");
            rowx.add("");
            pres.add("");
            for(int k=0; k<vt2St.size(); k++)
			{
                rowx.add(""+vt2St.get(k));
                pres.add(""+vt2St.get(k));
            }
			
            strTotal = "";
            if(totMenit2st!=0){
                int jm = totMenit2st/60;
                if(jm!=0)
				{
                    totHour2st = totHour2st + jm;
                    if((totMenit2st % 60)!=0)
                        totMenit2st = totMenit2st % 60;
                }
            }
			
            if(totHour2st!=0)
			{
                strTotal = totHour2st+"h";
            }
			
            if(totMenit2st!=0)
			{
                if(strTotal.length()>0)
				{
                    strTotal = strTotal+ ", "+ totMenit2st+"m";
                }
				else
				{
                    strTotal = totMenit1st+"m";
                }
            }
            rowx.add(strTotal);
            pres.add(strTotal);
            lstData.add(rowx);
            list.add(pres);
			
        }
	System.out.println("list..."+list);
	System.out.println("nilai i"+i);
	System.out.println("nilai vectsize()"+listEarly.size());
	}
	result.add(ctrlist.drawList());
	result.add(list);
	return result;
}
%>


<%
int iCommand = FRMQueryString.requestCommand(request);
long oidDepartment = FRMQueryString.requestLong(request,"department");
long oidSection = FRMQueryString.requestLong(request,"section");
int idx = FRMQueryString.requestInt(request,"week_idx");
Date date = FRMQueryString.requestDate(request,"month_year");

if(iCommand==Command.NONE || date==null)
{
	date = new Date();
}

CalendarCalc objCalendarCalc = new CalendarCalc(iCalendarType);
Date dtStartDate = objCalendarCalc.getStartDateOfTheWeek(date,idx);
Date dtEndDate = objCalendarCalc.getEndDateOfTheWeek(date,idx);

Calendar newCalendar = Calendar.getInstance();
newCalendar.setTime(date);
int intwk = newCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH); 

String drawList = "";
Vector vctWeeklyLateness = new Vector(1,1);
Vector listLateness = new Vector(1,1);
if(iCommand == Command.LIST)
{
	listLateness = SessEmployeeEarly.listEarlyDataWeekly(oidDepartment, iCalendarType, date, idx, oidSection); 

	Vector vPresence = drawList(listLateness, date, dtStartDate, dtEndDate);
	vctWeeklyLateness.add(""+idx);
	vctWeeklyLateness.add(""+oidDepartment);
	vctWeeklyLateness.add(date);
	vctWeeklyLateness.add(dtStartDate);
	vctWeeklyLateness.add(dtEndDate);
	if(vPresence != null && vPresence.size()>0){
		drawList = (String)vPresence.get(0);
		Vector list = (Vector)vPresence.get(1);
		vctWeeklyLateness.add(list);
	}
	vctWeeklyLateness.add(""+oidSection);
}

if(session.getValue("WEEKLY_EARLY_HOME")!=null){
	session.removeValue("WEEKLY_EARLY_HOME");
}
session.putValue("WEEKLY_EARLY_HOME",vctWeeklyLateness);
%>

<!-- End of JSP Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Report Early Home Weekly</title>
<script language="JavaScript">
function cmdView(){
	document.frpresence.command.value="<%=Command.LIST%>";
	document.frpresence.action="early_weekly_report.jsp";
	document.frpresence.submit();
}

function week(){
	document.frpresence.command.value="<%=Command.FIRST%>";
	document.frpresence.action="early_weekly_report.jsp";
	document.frpresence.submit();
}

function reportXls(){
	window.open("<%=approot%>/servlet/com.dimata.harisma.report.WeeklyEarlyXLS");
}


function reportPdf(){
	var linkPage = "<%=printroot%>.report.employee.EarlyWeeklyPdf";
	window.open(linkPage,"reportWeeklyPage","height=600,width=800,status=no,toolbar=no,menubar=no,location=no");
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
	document.frpresence.action="early_weekly_report.jsp"; 
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
                  &gt; Early Home Weekly<!-- #EndEditable --> </strong></font> </td>
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
                                            <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div></td>
                                          <td width="88%"> :
                                            <%
                                          Vector listDepartment = PstDepartment.list(0, 0, "", "DEPARTMENT");
										  Vector deptValue = new Vector(1,1);
										  Vector deptKey = new Vector(1,1);
                                          //deptValue.add("0");
                                          //deptKey.add("ALL");

                                          for(int d=0;d<listDepartment.size();d++){
										  	Department department = (Department)listDepartment.get(d);
											deptValue.add(""+department.getOID());
											deptKey.add(department.getDepartment());
										  }
										  %> <%=ControlCombo.draw("department",null,""+oidDepartment,deptValue,deptKey,"onChange=\"javascript:cmdUpdateDep()\"")%></td>
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
                                            <div align="left">Month</div></td>
                                          <td width="88%">: 
										  <%=ControlDate.drawDateMY("month_year",date==null || iCommand == Command.NONE?new Date():date,"MMM yyyy","formElemen",0,installInterval,"onChange=\"javascript:week()\"")%>
										  </td>
                                        </tr>
                                        <tr>
                                          <td>&nbsp;</td>
                                          <td nowrap>Week</td>
                                          <td>:                                             <%
										  Vector wkValue = new Vector(1,1);
										  Vector wkKey = new Vector(1,1);
                                           for(int d=0;d<intwk;d++){
											wkValue.add(""+(d+1));
											wkKey.add(getStrWeek(d+1));
										  }
										  %> <%=ControlCombo.draw("week_idx",null,""+idx,wkValue,wkKey)%></td>
                                        </tr>
                                        <tr>
                                          <td width="3%">&nbsp;</td>
                                          <td width="9%" nowrap> <div align="left"></div></td>
                                          <td width="88%"> <table border="0" cellspacing="0" cellpadding="0" width="137">
                                              <tr>
                                                <td width="16"><a href="javascript:cmdView()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Lateness"></a></td>
                                                <td width="2"><img src="<%=approot%>/images/spacer.gif" width="4" height="1"></td>
                                                <td width="94" class="command" nowrap><a href="javascript:cmdView()">View
                                                  Early Home</a></td>
                                              </tr>
                                            </table></td>
                                        </tr>
                                      </table>
									  <% if(iCommand == Command.LIST){%>
									  <table width="100%" border="0" cellspacing="2" cellpadding="2">
									  <% if(listLateness != null && listLateness.size()>0){%>
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
											  <table width="33%" border="0" cellspacing="1" cellpadding="1">
                                              <tr>
												  <td width="11%"><a href="javascript:reportPdf()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
												  <td width="50%"><b><a href="javascript:reportPdf()" class="buttonlink">Print
													Early Home</a></b>
												  </td>
												  <td width="12%"><a href="javascript:reportXls()"><img src="../../images/BtnNew.jpg" width="24" height="24" border="0"></a></td>
                                                <td width="27%"><b>
												<%
												long oidHr = Long.parseLong(PstSystemProperty.getValueByName(OID_HRD_DEPARTMENT));											
												if((departmentOid==oidHr)){// && positionType==PstPosition.LEVEL_MANAGER)){%>
													<a href="javascript:reportXls()" class="buttonlink">Export 
													  to Excel</a>
												<%}%>
												
												  </b></td>
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
