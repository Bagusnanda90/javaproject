
<% 
/* 
 * Page Name  		:  empschedule_edit.jsp
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
<%@ page import = "com.dimata.harisma.session.attendance.*" %>


<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>

<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_WORKING_SCHEDULE); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//    boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//    boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//    boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
	
%>
<!-- Jsp Block -->
<%!
    public String drawList(Vector objectClass) {
	String strSchedule = "<table border=\"0\" cellspacing=\"0\"" + "cellpadding=\"1\" bgcolor=\"#E0EDF0\"><tr>";
	
	String in = PstSystemProperty.getValueByName("POSTING_IN_TOLERANCE");
	int inTolerance = Integer.parseInt(in);
	String out = PstSystemProperty.getValueByName("POSTING_OUT_TOLERANCE");
	int outTolerance = Integer.parseInt(out);
	
	for (int i = 0; i < objectClass.size(); i++) {
		ScheduleSymbol scheduleSymbol = (ScheduleSymbol) objectClass.get(i);
		String str_dt_TimeIn = "";
		String str_dt_TimeOut = "";
		
		ScheduleCategory sc = new ScheduleCategory();
		try{
			sc = PstScheduleCategory.fetchExc(scheduleSymbol.getScheduleCategoryId());
		}
		catch(Exception e){
		}

		try
		{
			Date dt_TimeIn = scheduleSymbol.getTimeIn();
			if(sc.getCategoryType()==PstScheduleCategory.CATEGORY_REGULAR 
			|| sc.getCategoryType()==PstScheduleCategory.CATEGORY_SPLIT_SHIFT
			|| sc.getCategoryType()==PstScheduleCategory.CATEGORY_NIGHT_WORKER
			|| sc.getCategoryType()==PstScheduleCategory.CATEGORY_ACCROSS_DAY
			){
				dt_TimeIn.setMinutes(dt_TimeIn.getMinutes()+inTolerance);
			}
			if(dt_TimeIn == null)
				dt_TimeIn = new Date();
			str_dt_TimeIn = Formater.formatTimeLocale(dt_TimeIn);
		}
		catch(Exception e)
		{
			str_dt_TimeIn = "";
		}

		try
		{
			Date dt_TimeOut = scheduleSymbol.getTimeOut();
			if(sc.getCategoryType()==PstScheduleCategory.CATEGORY_REGULAR 
			|| sc.getCategoryType()==PstScheduleCategory.CATEGORY_SPLIT_SHIFT
			|| sc.getCategoryType()==PstScheduleCategory.CATEGORY_NIGHT_WORKER
			|| sc.getCategoryType()==PstScheduleCategory.CATEGORY_ACCROSS_DAY
			){
				dt_TimeOut.setMinutes(dt_TimeOut.getMinutes()-outTolerance);
			}
			if(dt_TimeOut == null)
				dt_TimeOut = new Date();
			str_dt_TimeOut = Formater.formatTimeLocale(dt_TimeOut);
		}
		catch(Exception e)
		{
			str_dt_TimeOut = "";
		}

		if (str_dt_TimeIn.compareTo(str_dt_TimeOut) != 0)
		{
			strSchedule += "<td>" + String.valueOf(scheduleSymbol.getSymbol()) + "</td><td>=</td><td>" + str_dt_TimeIn + "-" + str_dt_TimeOut + "</td><td width=\"8\"></td>";
		}
		else
		{
			strSchedule += "<td>" + String.valueOf(scheduleSymbol.getSymbol()) + "</td><td>=</td><td>" + String.valueOf(scheduleSymbol.getSchedule()) + "</td><td width=\"8\"></td>";
		}

		if ((i % 5) == 4)
		{
			strSchedule += "</tr>";
		}
	}
	strSchedule += "</tr></table>";
	return strSchedule;
}
%>

<%

	long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
	boolean isAuthorizeUserLogin = (positionType>=PstPosition.LEVEL_SECRETARY) ? true : false;
	boolean isHRDLogin = isAuthorizeUserLogin && (departmentOid==hrdDepartmentOid);
	long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
	boolean isEdpLogin = edpSectionOid==sectionOfLoginUser.getOID() ? true : false;
	boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
        
        boolean isCopy = FRMQueryString.requestBoolean(request, "hidden_copy_status");
	
	
	// configuration for checking "updating emp schedule"
	Date dtCurrentDate = new Date();
	boolean checkUpdateScheduleByDate = false; 
	int intcheckUpdateScheduleByDate = Integer.parseInt(""+PstSystemProperty.getValueByName("CHECK_UPDATE_SCHEDULE")); 
	if(intcheckUpdateScheduleByDate == 1 && !isHRDLogin)
	{
		checkUpdateScheduleByDate = true;
	}
	int intCheckLeaveRoster = Integer.parseInt(""+PstSystemProperty.getValueByName("UPDATE_SCHLD_TIME_LEAVE_ROSTER")); 
	int intCheckDPRoster = Integer.parseInt(""+PstSystemProperty.getValueByName("UPDATE_SCHLD_TIME_DP_ROSTER")); 
	int intCheckOtherRoster = Integer.parseInt(""+PstSystemProperty.getValueByName("UPDATE_SCHLD_TIME_OTHER_ROSTER")); 



    CtrlEmpSchedule ctrlEmpSchedule = new CtrlEmpSchedule(request);
    long oidEmpSchedule = FRMQueryString.requestLong(request, "hidden_emp_schedule_id");
    //System.out.println("oidEmpSchedule = " + oidEmpSchedule);

    long gotoPeriod = FRMQueryString.requestLong(request, "hidden_goto_period");
    long gotoEmployee = FRMQueryString.requestLong(request, "hidden_goto_employee");
	int startDatePeriod = Integer.parseInt(String.valueOf(PstSystemProperty.getValueByName("START_DATE_PERIOD")));
	boolean isCheckFirstSchedule = true;

	//out.println("startDatePeriod  "+startDatePeriod);
    int iErrCode = FRMMessage.ERR_NONE;
    String errMsg = "";
    String whereClause = "";
    String orderClause = "";
    int iCommand = FRMQueryString.requestCommand(request);
    int start = FRMQueryString.requestInt(request,"start");

    System.out.println("iCommand : "+iCommand);
    ControlLine ctrLine = new ControlLine();

    iErrCode = ctrlEmpSchedule.action(iCommand , oidEmpSchedule);

    errMsg = ctrlEmpSchedule.getMessage();
    FrmEmpSchedule frmEmpSchedule = ctrlEmpSchedule.getForm();
    EmpSchedule empSchedule = ctrlEmpSchedule.getEmpSchedule();

    oidEmpSchedule = empSchedule.getOID();
    System.out.println("\noidEmpSchedule = " + oidEmpSchedule);

    long oidPeriod = empSchedule.getPeriodId();
    System.out.println("oidPeriod = " + oidPeriod);

    long oidEmployee = empSchedule.getEmployeeId();
    System.out.println("oidEmployee = " + oidEmployee);
    System.out.println("gotoPeriod = " + gotoPeriod);

    //if(((iCommand==Command.SAVE)||(iCommand==Command.DELETE))&&(frmEmpSchedule.errorSize()<1)){
    if(iCommand==Command.DELETE){
    %>
        <jsp:forward page="empschedule_list.jsp"> 
        <jsp:param name="start" value="<%=start%>" />
        <jsp:param name="hidden_emp_schedule_id" value="<%=empSchedule.getOID()%>" />
        <jsp:param name="iCommand" value="<%=Command.SAVE%>" />
        <jsp:param name="prevCommand" value="<%=Command.ADD%>" />
        </jsp:forward>
    <%
    }
    if((iCommand==Command.SAVE)&&(frmEmpSchedule.errorSize()<1)){
    iCommand = Command.EDIT;
    }

    Date dtPeriodNow = new Date();
    Date dtPeriodStart = new Date();
    Date dtPeriodEnd = new Date();
    long periodId = 0;

    Period period = new Period();
    Employee employee = new Employee();
	
	/**
 	* proses DP, AL dan LL untuk data informasi jumlah DP, AL dan LL ke user sebelum memilih schedule itu
 	* @created by Gadnyana 
 	*/
	Vector list = PstScheduleSymbol.getScheduleDPALLL();
	Vector listDp = DPMontly.getDpStock();
	Vector listAl = AnnualLeaveMontly.getAnnualLeaveStock();
	Vector listll = SessLongLeave.getLongLeaveStock();

    if (iCommand == Command.GOTO) {
        if (gotoPeriod != -1) {
            period = PstPeriod.fetchExc(gotoPeriod);
            dtPeriodStart = period.getStartDate();
            dtPeriodEnd = period.getEndDate();
            periodId = period.getOID();
            iCommand = Command.ADD;
        }
    }
    else 
    if (iCommand == Command.ADD) {
		//endDatePeriod = 
        dtPeriodStart = new Date(dtPeriodNow.getYear(), dtPeriodNow.getMonth() - 1, 21);
        dtPeriodEnd = new Date(dtPeriodNow.getYear(), dtPeriodNow.getMonth(), 20);
    }
    else 
    if (iCommand == Command.SAVE) {
        if (oidEmployee != 0) {
            period = PstPeriod.fetchExc(gotoPeriod);
            dtPeriodStart = period.getStartDate();
            dtPeriodEnd = period.getEndDate();
            periodId = period.getOID();
            iCommand = Command.ADD;
            employee = PstEmployee.fetchExc(oidEmployee);
        }
        else {
            period = PstPeriod.fetchExc(oidPeriod);
            dtPeriodStart = period.getStartDate();
            dtPeriodEnd = period.getEndDate();
            periodId = period.getOID();
            //employee = PstEmployee.fetchExc(oidEmployee);
        }
    }
    else 
    if (iCommand == Command.ASSIGN) {
       // if (oidEmployee != 0) {
            period = PstPeriod.fetchExc(gotoPeriod);
            dtPeriodStart = period.getStartDate();
            dtPeriodEnd = period.getEndDate();
            periodId = period.getOID();
          //  iCommand = Command.EDIT;
       //     employee = PstEmployee.fetchExc(oidEmployee);
     //   }
       // else {
      //      period = PstPeriod.fetchExc(oidPeriod);
       //     dtPeriodStart = period.getStartDate();
       //     dtPeriodEnd = period.getEndDate();
       //     periodId = period.getOID();
            //employee = PstEmployee.fetchExc(oidEmployee);
      //  }
    }
    else {
        try{
        period = PstPeriod.fetchExc(oidPeriod);
        dtPeriodStart = period.getStartDate();
        dtPeriodEnd = period.getEndDate();
        //periodId = period.getOID();
        employee = PstEmployee.fetchExc(oidEmployee);
        }catch(Exception ex){}
    }
    //System.out.println(dtPeriodStart);
    //System.out.println(dtPeriodEnd);
    Vector listScheduleSymbol = new Vector(1,1);
    listScheduleSymbol = PstScheduleSymbol.list(0,500,"",PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL]);

    // cari department_id dari user yang login
    AppUser appuser = userSession.getAppUser();
    Employee emp = new Employee();
    System.out.println("\t...employee id = " + appuser.getEmployeeId());
    if (appuser.getEmployeeId() > 0) {
        emp = PstEmployee.fetchExc(appuser.getEmployeeId());
        System.out.println("\t...department id = " + emp.getDepartmentId());
    }
%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Working Schedule</title>
<script language="JavaScript">
    
    function cmdSearchEmp(){
	window.open("<%=approot%>/employee/search/search.jsp?formName=frm_empschedule&empPathId=<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
    }
    
    function cmdClearSearchEmp(){
	document.frm_empschedule.EMP_NUMBER.value = "";
	document.frm_empschedule.EMP_FULLNAME.value = "";
	document.frm_empschedule.EMP_DEPARTMENT.value = "";
    }
    
    function cmdEdit(oid){
        document.frm_empschedule.command.value="<%=String.valueOf(Command.EDIT)%>";
        //document.frm_empschedule.prev_command.value="< %//=String.valueOf(Command.EDIT)% >";
        document.frm_empschedule.hidden_emp_schedule_id.value = oid;
        document.frm_empschedule.action="empschedule_edit.jsp";
        document.frm_empschedule.submit();
    }

    function cmdCancel(){
        document.frm_empschedule.command.value="<%=String.valueOf(Command.CANCEL)%>";
        document.frm_empschedule.action="empschedule_edit.jsp";
        document.frm_empschedule.submit();
    } 

    function cmdSave(){
        document.frm_empschedule.command.value="<%=String.valueOf(Command.SAVE)%>"; 
        document.frm_empschedule.action="empschedule_edit.jsp";
        document.frm_empschedule.submit();
    }

    function cmdAsk(oid){
        document.frm_empschedule.command.value="<%=String.valueOf(Command.ASK)%>"; 
        document.frm_empschedule.action="empschedule_edit.jsp";
        document.frm_empschedule.submit();
    } 

    function cmdConfirmDelete(oid){
        document.frm_empschedule.command.value="<%=String.valueOf(Command.DELETE)%>";
        document.frm_empschedule.action="empschedule_edit.jsp"; 
        document.frm_empschedule.submit();
    }  

    function cmdBack(){
        //document.frm_empschedule.command.value="< %//=String.valueOf(Command.FIRST)% >"; 
        document.frm_empschedule.command.value="<%=String.valueOf(Command.LIST)%>"; 
        document.frm_empschedule.action="empschedule_list.jsp";
        document.frm_empschedule.submit();
    }

    function periodChange() {
        document.frm_empschedule.command.value = "<%=String.valueOf(Command.GOTO)%>";
        document.frm_empschedule.hidden_goto_period.value = document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_PERIOD_ID]%>.value;
        document.frm_empschedule.hidden_goto_employee.value = document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID]%>.value;
        document.frm_empschedule.action = "empschedule_edit.jsp";
        document.frm_empschedule.submit();
    }

    function cmdSearchEmp_old(){
        emp_number = document.frm_empschedule.EMP_NUMBER.value;
        emp_fullname = document.frm_empschedule.EMP_FULLNAME.value;
        emp_department = document.frm_empschedule.EMP_DEPARTMENT.value;
		emp_section = document.frm_empschedule.EMP_SECTION.value;
        emp_period = document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_PERIOD_ID]%>.value;
        //window.open("empsearch.jsp?emp_number=" + emp_number + "&emp_fullname=" + emp_fullname + "&emp_department=" + emp_department + "&emp_period=" + emp_period, null, "height=400,width=640,status=yes,toolbar=no,menubar=no,location=no");
        window.open("empsearch.jsp?emp_number=" + emp_number + "&emp_fullname=" + emp_fullname + "&emp_department=" + emp_department + "&emp_period=" + emp_period + "&emp_section=" + emp_section);
        //window.open("searchemployee.jsp", null, "height=400,width=640,status=yes,toolbar=no,menubar=no,location=no");
        //window.open("searchemployee.jsp", null, "status=no,toolbar=no,menubar=no,location=no");
    }

    function cmdClearSearchEmp_old(){
        document.frm_empschedule.EMP_NUMBER.value = "";
        document.frm_empschedule.EMP_FULLNAME.value = "";
    }

    function cmdCopyPaste(oid) {
  //      document.frm_empschedule.hidden_emp_schedule_id.value = oid;
  //      document.frm_empschedule.command.value = "< =String.valueOf(Command.EDIT)%>";
  //      document.frm_empschedule.hidden_goto_period.value = document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_PERIOD_ID]%>.value;
  //      document.frm_empschedule.hidden_goto_employee.value = 0;//document.frm_empschedule.< %=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID]%>.value;
  //      document.frm_empschedule.action = "empschedule_edit_copy.jsp";
  //      document.frm_empschedule.submit(); 
          document.frm_empschedule.command.value="<%=String.valueOf(Command.SAVE)%>";
          document.frm_empschedule.hidden_copy_status.value = true;
          document.frm_empschedule.action="empschedule_edit.jsp";
          document.frm_empschedule.submit();
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

function changeSchedule(objForm, defaultValue, formNumber)
{	
	<%
	if(isAuthorizeUserLogin)
	{	
		// start isCheckFirstSchedule schedule kedua tergantung pada schedule I
		if(isCheckFirstSchedule)
		{
			// start checkUpdateScheduleByDate ... 
			if(checkUpdateScheduleByDate)
			{
	%>	
			var schld_value = objForm.value;
			var schld_cat = schld_value.substring(18,19);
			var def_schld_cat = defaultValue.substring(18,19);	
			var const_cat = "<%=String.valueOf(PstScheduleCategory.CATEGORY_SPLIT_SHIFT)%>";  // konstanta split shift yg diambil dari bean		
			var dp_cat = "<%=String.valueOf(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT)%>";  // konstanta day off payment yg diambil dari bean					
			var al_cat = "<%=String.valueOf(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE)%>";  // konstanta annual leave yg diambil dari bean					
			var ll_cat = "<%=String.valueOf(PstScheduleCategory.CATEGORY_LONG_LEAVE)%>";  // konstanta long leave yg diambil dari bean							
			
			var monthOfStartPeriod = "<%=String.valueOf(dtPeriodStart.getMonth())%>";
			var monthOfCurrentDate = "<%=String.valueOf(dtCurrentDate.getMonth())%>";
			var dateOfCurrentDate = "<%=String.valueOf(dtCurrentDate.getDate())%>";		
			if( monthOfStartPeriod >= monthOfCurrentDate )
			{		
				var maxDateUpdated = formNumber;
				if(def_schld_cat == dp_cat)
				{
					maxDateUpdated = maxDateUpdated - "<%=String.valueOf(intCheckDPRoster)%>";					
				}		
		
				if(def_schld_cat == al_cat || def_schld_cat == ll_cat)               
				{
					maxDateUpdated = maxDateUpdated - "<%=String.valueOf(intCheckLeaveRoster)%>";	   
				}
		
				if(monthOfStartPeriod == monthOfCurrentDate)
				{
					if( parseInt(maxDateUpdated) < parseInt(dateOfCurrentDate) )
					{		
						if( parseInt(formNumber) < parseInt(dateOfCurrentDate) )			
						{
							alert("Cannot change schedule\nSelected schedule is out of date\nPlease contact HRD to change this schedule ...");			
						}
						else
						{
							var yearOfCurrentDate = "<%=String.valueOf(dtCurrentDate.getYear())%>";   		
							var dtLatest = new Date(yearOfCurrentDate, monthOfCurrentDate, maxDateUpdated);
							var sLatestChangingDate = month(dtLatest.getMonth()) + " " +dtLatest.getDate() + ", " +(dtLatest.getYear()+1900);				 
							alert("Cannot change schedule\nLatest changing at " + sLatestChangingDate + "\nPlease contact HRD to change this schedule ...");
						}
						objForm.value = defaultValue;					
					}
				}
		
				if( (parseInt(monthOfStartPeriod) > parseInt(monthOfCurrentDate)) || (parseInt(maxDateUpdated) >= parseInt(dateOfCurrentDate)) )   	
				{	
			
					/**
					* for cek qty dp,al,ll
					*/	
					var changeFrmByDp = false; 	
					var oid = schld_value.substring(0,18);	
					switch(oid)
					{
					
					<%
					if(list!=null && list.size()>0)
					{
						for(int k=0; k<list.size(); k++)
						{
							Vector vect = (Vector)list.get(k);
							ScheduleSymbol scheduleSymbol = (ScheduleSymbol)vect.get(0);
							ScheduleCategory scheduleCategory = (ScheduleCategory)vect.get(1);
					%>
					
							case '<%=String.valueOf(scheduleSymbol.getOID())%>':
									<%
									if(scheduleCategory.getCategoryType()==PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT)
									{
									%>
										changeFrmByDp = cekDayoffPayment(oid,objForm,defaultValue);
									<%
									}
									else if(scheduleCategory.getCategoryType()==PstScheduleCategory.CATEGORY_ANNUAL_LEAVE)
									{
									%>
										cekAnnualLeave(oid);
									<%
									}
									else if(scheduleCategory.getCategoryType()==PstScheduleCategory.CATEGORY_LONG_LEAVE)
									{
									%>
										cekLongLeave(oid);
									<%
									}
									%>
								break;
					<%
						}
					}
					%>			
						
					}
					
				
					<%
					// Dimulai dari indeks 3 karena dalam urutan nama form di FrmEmpSchedule, schedule mulai dari indeks 3
					int startIterate = 3; // awal iterasi
					int countSchldPerMonth = 31; // jumlah schedule dalam satu bulan
					int maxIterate = startIterate + countSchldPerMonth;
				
					for(int i=startIterate; i<maxIterate; i++)
					{
					%>				
						if( objForm==document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[i]%> )
						{
							alert('in');
							if( schld_cat==const_cat )
							{
								alert('in1');
								document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[i+countSchldPerMonth]%>.style.visibility='';
							}
							else
							{
								document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[i+countSchldPerMonth]%>.style.visibility='hidden';
								document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[i+countSchldPerMonth]%>.value='0';
							}		
						}
			
					<%
					}
					%>			
				}	
			}
			else
			{
				alert("Cannot change schedule\nSelected schedule is out of date\nPlease contact HRD to change this schedule ...");			
				objForm.value = defaultValue;				
			}		
	
	<%
			}
			// end checkUpdateScheduleByDate ...
			// start not checkUpdateScheduleByDate ...
			else
			{
	%>

				var schld_value = objForm.value;
				var schld_cat = schld_value.substring(18,19);
				var def_schld_cat = defaultValue.substring(18,19);	
				var const_cat = "<%=String.valueOf(PstScheduleCategory.CATEGORY_SPLIT_SHIFT)%>";  // konstanta split shift yg diambil dari bean		
				
				/**
				* for cek qty dp,al,ll
				*/	
				var changeFrmByDp = false; 	
				var oid = schld_value.substring(0,18);	
				switch(oid)
				{
				
				<%
				if(list!=null && list.size()>0)
				{
					for(int k=0; k<list.size(); k++)
					{
						Vector vect = (Vector)list.get(k);
						ScheduleSymbol scheduleSymbol = (ScheduleSymbol)vect.get(0);
						ScheduleCategory scheduleCategory = (ScheduleCategory)vect.get(1);
				%>
				
						case '<%=String.valueOf(scheduleSymbol.getOID())%>':
								<%
								if(scheduleCategory.getCategoryType()==PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT)
								{
								%>
									changeFrmByDp = cekDayoffPayment(oid,objForm,defaultValue);
								<%
								}
								else if(scheduleCategory.getCategoryType()==PstScheduleCategory.CATEGORY_ANNUAL_LEAVE)
								{
								%>
									cekAnnualLeave(oid);
								<%
								}
								else if(scheduleCategory.getCategoryType()==PstScheduleCategory.CATEGORY_LONG_LEAVE)
								{
								%>
									cekLongLeave(oid);
								<%
								}
								%>
							break;
				<%
					}
				}
				%>			
							
				}
				
			
				<%
				// Dimulai dari indeks 3 karena dalam urutan nama form di FrmEmpSchedule, schedule mulai dari indeks 3
				int startIterate = 3; // awal iterasi
				int countSchldPerMonth = 31; // jumlah schedule dalam satu bulan
				int maxIterate = startIterate + countSchldPerMonth;
			
				for(int i=startIterate; i<maxIterate; i++)
				{
				%>				
					if( objForm==document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[i]%> )
					{
						if( schld_cat==const_cat )
						{
							document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[i+countSchldPerMonth]%>.style.visibility='';
						}
						else
						{
							document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[i+countSchldPerMonth]%>.style.visibility='hidden';
							document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[i+countSchldPerMonth]%>.value='0';
						}		
					}		
	<%
				}
			}
			// end not checkUpdateScheduleByDate ...			
		}
		// end isCheckFirstSchedule schedule kedua tergantung pada schedule I			
	}
	else
	{
	%>
		alert("Access forbidden\nYou aren't an authorized user\nPlease contact your administrator ...");
		objForm.value = defaultValue;  					
	<%
	}
	%>
}


/**
 * utk ngatur proses pada schedule II, menentukan bisa tidaknya suatu schedule diubah berdasar tanggal ubahnya
 * Algoritma :
 *   - Bandingkan periode schedule dengan bulan saat ini sesuai dengan tanggal server
 *   - Nilai dari suatu combo box akan diambil dan di substring untuk mendapatkan category schedulenya (dalam hal ini
 *     masih terbatas dalam pemrosesan split shift)
 * 	 - Jika ternyata schedule yang terpilih adalah split shift maka 'second schedule' akan ditampilkan dan sebaliknya.
 * created by Edhy
 */
function change2ndSchedule(objForm, defaultValue, formNumber)
{
	<%
	if(isAuthorizeUserLogin)
	{
	%>
		var schld_value = objForm.value;
		var schld_cat = schld_value.substring(18,19);		
		var def_schld_cat = defaultValue.substring(18,19);			
		var dp_cat = "<%=String.valueOf(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT)%>";  // konstanta day off payment yg diambil dari bean
		var al_cat = "<%=String.valueOf(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE)%>";  // konstanta annual leave yg diambil dari bean				
		var ll_cat = "<%=String.valueOf(PstScheduleCategory.CATEGORY_LONG_LEAVE)%>";  // konstanta long leave yg diambil dari bean					

		var monthOfStartPeriod = "<%=String.valueOf(dtPeriodStart.getMonth())%>";
		var monthOfCurrentDate = "<%=String.valueOf(dtCurrentDate.getMonth())%>";
		var dateOfCurrentDate = "<%=String.valueOf(dtCurrentDate.getDate())%>";		
		if( monthOfStartPeriod >= monthOfCurrentDate )
		{
			var maxDateUpdated = formNumber;
			if(def_schld_cat == dp_cat)
			{
				maxDateUpdated = maxDateUpdated - "<%=String.valueOf(intCheckDPRoster)%>";					
			}		
	
			if(def_schld_cat == al_cat || def_schld_cat == ll_cat)               
			{
				maxDateUpdated = maxDateUpdated - "<%=String.valueOf(intCheckLeaveRoster)%>";	   
			}
	
			if(monthOfStartPeriod == monthOfCurrentDate)
			{	
				if( parseInt(maxDateUpdated) < parseInt(dateOfCurrentDate) )
				{		
					if( parseInt(formNumber) < parseInt(dateOfCurrentDate) )			
					{
						alert("Cannot change schedule\nSelected schedule is out of date\nPlease contact HRD to change this schedule ...");			
					}
					else
					{
						var yearOfCurrentDate = "<%=String.valueOf(dtCurrentDate.getYear())%>";   		
						var dtLatest = new Date(yearOfCurrentDate, monthOfCurrentDate, maxDateUpdated);
						var sLatestChangingDate = month(dtLatest.getMonth()) + " " +dtLatest.getDate() + ", " +(dtLatest.getYear()+1900);				 
						alert("Cannot change schedule\nLatest changing at " + sLatestChangingDate + "\nPlease contact HRD to change this schedule ...");
					}
					objForm.value = defaultValue;					
				}
			}
		}
		else
		{
			alert("Cannot change schedule\nSelected schedule is out of date\nPlease contact HRD to change this schedule ...");			
			objForm.value = defaultValue;				
		}	
	<%	
	}
	else if(!isHRDLogin)
	{
	%>
		alert("Cannot change schedule\nYou aren't an authorized user\nPlease contact your administrator ...");
		objForm.value = defaultValue;					
	<%
	}
	%>	
}



</SCRIPT>
<!-- #EndEditable -->
</head> 

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnSearchOn.jpg','<%=approot%>/images/BtnCancelOn.jpg')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
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
                      <td align="left" ><font color="#FF6600" face="Arial"><strong>Attendance &gt; Working Schedule</strong></font></td>
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
                <td class="tablecolor"> 
                  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                    <tr> 
                      <td valign="top"> 
                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                          <tr> 
                            <td valign="top">
		    				  <!-- #BeginEditable "content" --> 
                                    <form name="frm_empschedule" method="post" action="">
                                      <input type="hidden" name="command" value="">
                                      <input type="hidden" name="start" value="<%=String.valueOf(start)%>">
                                      <% if(isCopy){ %>
                                        <input type="hidden" name="hidden_emp_schedule_id" value="0">
                                      <%}else{%>
                                        <input type="hidden" name="hidden_emp_schedule_id" value="<%=String.valueOf(oidEmpSchedule)%>">
                                      <%}%>
                                      <input type="hidden" name="hidden_goto_period" value="<%=String.valueOf(gotoPeriod)%>">
                                      <input type="hidden" name="hidden_goto_employee" value="<%=String.valueOf(gotoEmployee)%>">
                                      <input type="hidden" name="hidden_copy_status">
                                      <table width="100%" cellspacing="0" cellpadding="0" >
                                        <tr> 
                                          <td colspan="3"> 
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr> 
                                                <td> 
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
						<% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                         <% } else {%>
                                                    <tr> 
                                                      <td colspan="3"> Creating 
                                                        Working Schedule step 
                                                        by step: </td>
                                                    </tr>
                                                    <tr> 
                                                      <td colspan="3"> 
                                                        <li>&nbsp;Choose the Period 
                                                          of Working Schedule 
                                                      </td>
                                                    </tr>
                                                        <% 
                                                            } 
                                                        %>
                                                    <tr> 
                                                      <td width="2%"> 
                                                        <div align="left"></div>
                                                      </td>
                                                      <td width="7%"> 
                                                        <div align="left">Period</div>
                                                      </td>
                                                      <td width="91%"> 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <input type="hidden" name="<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_PERIOD_ID]%>" value="<%=String.valueOf(oidPeriod)%>">
                                                        <%=period.getPeriod()%> 
                                                        <% } else {%>
                                                        <% 
                                                            //if (gotoPeriod != -1) {
								Vector period_value = new Vector(1,1); //hidden values that will be deliver on request (oids) 
								Vector period_key = new Vector(1,1); //texts that displayed on combo box
								period_key.add("-1");
								period_value.add("select...");
								//String selectValuePeriod = String.valueOf(empSchedule.getPeriodId()); //selected on combo box
                                                                String selectValuePeriod = String.valueOf(periodId); //selected on combo box
                                                                Vector listPeriod = new Vector(1,1);
                                                                listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");
                                                                for (int i = 0; i < listPeriod.size(); i++) {
                                                                    Period lsperiod = (Period) listPeriod.get(i);
                                                                    period_value.add(lsperiod.getPeriod());
                                                                    period_key.add(String.valueOf(lsperiod.getOID()));
                                                                }
							%>
                                                        <%//=ControlCombo.draw(FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_PERIOD_ID], null, empSchedule.getPeriodId() == 0 ? String.valueOf(oidPeriod) : selectValuePeriod, period_key, period_value, "onchange=\"javascript:periodChange();\"")%>
                                                        <%//=frmEmpSchedule.getErrorMsg(FrmEmpSchedule.FRM_FIELD_PERIOD_ID)%>
                                                        <%=ControlCombo.draw(FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_PERIOD_ID], null, selectValuePeriod, period_key, period_value, "onchange=\"javascript:periodChange();\"")%><%=frmEmpSchedule.getErrorMsg(FrmEmpSchedule.FRM_FIELD_PERIOD_ID)%> 
                                                        <% 
                                                                //}
                                                            } 
                                                        %>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td colspan="3"> </td>
                                                    </tr>
                                                    <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                         <% } else {%>
                                                    <tr> 
                                                      <td colspan="3"> 
                                                        <li>&nbsp;Pick one employee 
                                                          using Search form 
                                                      </td>
                                                    </tr>
                                                        <% 
                                                            } 
                                                        %>
                                                    <tr> 
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="7%" valign="top"> 
                                                        <div align="left">Employee</div>
                                                      </td>
                                                      <td width="91%" valign="top"> 
                                                        <% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                        <% if(isCopy){ %>
                                                            <table cellpadding="1" cellspacing="1" border="0" bgcolor="#E0EDF0" width="453">
                                                          <tr> 
                                                            <td valign="top"> 
                                                              <table cellpadding="1" cellspacing="1" border="0" width="384">
                                                                <tr> 
                                                                  <td width="72"></td>
                                                                  <td width="246"> 
                                                                    <input type="hidden" name="<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=String.valueOf(gotoEmployee)%>" class="formElemen">
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td valign="top" width="72"> 
                                                                    <div align="left"><%=dictionaryD.getWord(I_Dictionary.PAYROLL) %></div>
                                                                  </td>
                                                                  <td width="246"> 
                                                                    <input type="text" name="EMP_NUMBER"  value="" class="elemenForm" size="10"  readonly>
                                                                    * <%=frmEmpSchedule.getErrorMsg(FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID)%> 
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td valign="top" width="72"> 
                                                                    <div align="left">Name</div>
                                                                  </td>
                                                                  <td width="246"> 
                                                                    <input type="text" name="EMP_FULLNAME"  value="" class="elemenForm"  readonly>
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td valign="top" width="72"> 
                                                                    <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                                  </td>
                                                                  <td width="246"> <input type="text" name="EMP_DEPARTMENT"  value="" class="elemenForm"  readonly>  
                                                                </td>
                                                                </tr>
																<tr> 
                                                                  <td valign="top" width="72"> 
                                                                    <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div>
                                                                  </td>
                                                                  <td width="246"> 
                                                                   <input type="hidden" name="EMP_SECTION"  value="" class="elemenForm">  
                                                                </td>
                                                                </tr>
                                                              </table>
                                                              <%-- </td>
                                                      </tr>
                                                      <tr>
                                                        <td>--%>
                                                              <table cellpadding="0" cellspacing="0" border="0">
                                                                <tr> 
                                                                  <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                  <td width="15"><a href="javascript:cmdSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image101" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                                  <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                                  <td class="command" nowrap width="99"> 
                                                                    <div align="left"><a href="javascript:cmdSearchEmp()">Search 
                                                                      Employee</a></div>
                                                                  </td>
                                                                  <td width="15"><img src="<%=approot%>/images/spacer.gif" width="15" height="4"></td>
                                                                  <td width="15"><a href="javascript:cmdClearSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnCancelOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnCancel.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                                  <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                                  <td class="command" nowrap width="99"> 
                                                                    <div align="left"><a href="javascript:cmdClearSearchEmp()">Clear 
                                                                      Search</a></div>
                                                                  </td>
                                                                </tr>
                                                              </table>
                                                            </td>
                                                          </tr>
                                                        </table>
                                                        <%}else{%>
                                                            <input type="hidden" name="<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=String.valueOf(oidEmployee)%>">
                                                        <%=employee.getFullName()%> 
                                                        <%}%>
                                                        <% } else {%>
                                                        <table cellpadding="1" cellspacing="1" border="0" bgcolor="#E0EDF0" width="453">
                                                          <tr> 
                                                            <td valign="top"> 
                                                              <table cellpadding="1" cellspacing="1" border="0" width="384">
                                                                <tr> 
                                                                  <td width="72"></td>
                                                                  <td width="246"> 
                                                                    <input type="hidden" name="<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=String.valueOf(gotoEmployee)%>" class="formElemen">
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td valign="top" width="72"> 
                                                                    <div align="left"><%=dictionaryD.getWord(I_Dictionary.PAYROLL) %></div>
                                                                  </td>
                                                                  <td width="246"> 
                                                                    <input type="text" name="EMP_NUMBER"  value="" class="elemenForm" size="10"  readonly>
                                                                    * <%=frmEmpSchedule.getErrorMsg(FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID)%> 
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td valign="top" width="72"> 
                                                                    <div align="left">Name</div>
                                                                  </td>
                                                                  <td width="246"> 
                                                                    <input type="text" name="EMP_FULLNAME"  value="" class="elemenForm"  readonly>
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td valign="top" width="72"> 
                                                                    <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                                  </td>
                                                                  <td width="246"> 
                                                                    <% /*
                                                                        Vector dept_value = new Vector(1,1);
                                                                        Vector dept_key = new Vector(1,1);
                                                                        dept_value.add("0");
                                                                        dept_key.add("select ...");
                                                                        Vector listDept = new Vector(1,1);
                                                                       /*if (appuser.getEmployeeId() > 0) {
                                                                            listDept = PstDepartment.list(0, 0, "DEPARTMENT_ID = " + emp.getDepartmentId(), "");
                                                                       }
                                                                        else {*/
                                                                     /*       listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
                                                                        //}
                                                                        for (int i = 0; i < listDept.size(); i++) {
                                                                                Department dept = (Department) listDept.get(i);
                                                                                dept_key.add(dept.getDepartment());
                                                                                dept_value.add(String.valueOf(dept.getOID()));
                                                                       }
                                                                        if (appuser.getEmployeeId() == 0) {
                                                                            dept_value.add("0");
                                                                            dept_key.add("...ALL (may take long time)...");
                                                                        }*/
                                                                     %>
                                                                    <%//= ControlCombo.draw("EMP_DEPARTMENT","formElemen",null, "", dept_value, dept_key) %> 
                                                                <input type="text" name="EMP_DEPARTMENT"  value="" class="elemenForm"  readonly>  
                                                                </td>
                                                                </tr>
																<tr> 
                                                                  <td valign="top" width="72"> 
                                                                    <div align="left"><%=dictionaryD.getWord(I_Dictionary.SECTION) %></div>
                                                                  </td>
                                                                  <td width="246"> 
                                                                    <% /*
																		// penambahan section sehubungan dengan permintaan intimas
																		// updated by Yunny
                                                                        Vector sec_value = new Vector(1,1);
                                                                        Vector sec_key = new Vector(1,1);
                                                                        sec_value.add("0");
                                                                        sec_key.add("select ...");
                                                                        Vector listSec = new Vector(1,1);
                                                                       /*if (appuser.getEmployeeId() > 0) {
                                                                            listDept = PstDepartment.list(0, 0, "DEPARTMENT_ID = " + emp.getDepartmentId(), "");
                                                                       }
                                                                        else {*/
                                                                      /*      listSec = PstSection.list(0, 0, "", "SECTION");
                                                                        //}
                                                                        for (int i = 0; i < listSec.size(); i++) {
                                                                                Section sec = (Section) listSec.get(i);
                                                                                sec_key.add(sec.getSection());
                                                                                sec_value.add(String.valueOf(sec.getOID()));
                                                                       }
                                                                        if (appuser.getEmployeeId() == 0) {
                                                                            sec_value.add("0");
                                                                            sec_key.add("...ALL (may take long time)...");
                                                                        }*/
                                                                     %>
                                                                    <%//= ControlCombo.draw("EMP_SECTION","formElemen",null, "", sec_value, sec_key) %> 
                                                                <input type="hidden" name="EMP_SECTION"  value="" class="elemenForm">  
                                                                </td>
                                                                </tr>
                                                              </table>
                                                              <%-- </td>
                                                      </tr>
                                                      <tr>
                                                        <td>--%>
                                                              <table cellpadding="0" cellspacing="0" border="0">
                                                                <tr> 
                                                                  <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                  <td width="15"><a href="javascript:cmdSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnSearchOn.jpg',1)"><img name="Image101" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                                  <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                                  <td class="command" nowrap width="99"> 
                                                                    <div align="left"><a href="javascript:cmdSearchEmp()">Search 
                                                                      Employee</a></div>
                                                                  </td>
                                                                  <td width="15"><img src="<%=approot%>/images/spacer.gif" width="15" height="4"></td>
                                                                  <td width="15"><a href="javascript:cmdClearSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnCancelOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnCancel.jpg" width="24" height="24" alt="Search Schedule"></a></td>
                                                                  <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                                  <td class="command" nowrap width="99"> 
                                                                    <div align="left"><a href="javascript:cmdClearSearchEmp()">Clear 
                                                                      Search</a></div>
                                                                  </td>
                                                                </tr>
                                                              </table>
                                                            </td>
                                                          </tr>
                                                        </table>
                                                        <% } %>
                                                      </td>
                                                    </tr>
                                                    <tr> 
                                                      <td colspan="3"> </td>
                                                    </tr>
													<% if ((iCommand==Command.EDIT) || (iCommand==Command.ASK)) {%>
                                                         <% } else {%>
                                                    <tr> 
                                                      <td colspan="3"> 
                                                        <li>&nbsp;Fill the Schedule
                                                      </td>
                                                    </tr>
                                                         <% } %>
                                                    <tr> 
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="7%" valign="top"> 
                                                        <div align="left">Schedule</div>
                                                      </td>
                                                      <td width="91%"> 
                                                        <%
                                                        System.out.println("\tgotoPeriod = " + gotoPeriod);
                                                        System.out.println("\t(iCommand != Command.ADD) = " + (iCommand != Command.ADD));
                                                        if ((gotoPeriod == -1) || ((gotoPeriod == 0) && (iCommand == Command.ADD))) {
                                                        %>
                                                        <div><font color="#000099">No 
                                                          period selected.</font></div>
                                                        <%
                                                        }
                                                        else {
                                                        %>
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                          <tr> 
                                                            <td> 
                                                              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                <tr> 
                                                                  <td> 
                                                                    <%
                                                                        
                                                                        String dField[] = new String[31];
                                                                        dField[0] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D1];
                                                                        dField[1] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2];
                                                                        dField[2] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D3];
                                                                        dField[3] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D4];
                                                                        dField[4] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D5];
                                                                        dField[5] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D6];
                                                                        dField[6] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D7];
                                                                        dField[7] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D8];
                                                                        dField[8] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D9];
                                                                        dField[9] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D10];
                                                                        dField[10] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D11];
                                                                        dField[11] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D12];
                                                                        dField[12] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D13];
                                                                        dField[13] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D14];
                                                                        dField[14] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D15];
                                                                        dField[15] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D16];
                                                                        dField[16] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D17];
                                                                        dField[17] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D18];
                                                                        dField[18] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D19];
                                                                        dField[19] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D20];
                                                                        dField[20] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D21];
                                                                        dField[21] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D22];
                                                                        dField[22] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D23];
                                                                        dField[23] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D24];
                                                                        dField[24] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D25];
                                                                        dField[25] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D26];
                                                                        dField[26] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D27];
                                                                        dField[27] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D28];
                                                                        dField[28] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D29];
                                                                        dField[29] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D30];
                                                                        dField[30] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D31];
																		
																		
																		// Name of each second combobox's schedule
                                                                        String dField2nd[] = new String[31];
                                                                        dField2nd[0] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND1];
                                                                        dField2nd[1] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND2];
                                                                        dField2nd[2] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND3];
                                                                        dField2nd[3] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND4];
                                                                        dField2nd[4] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND5];
                                                                        dField2nd[5] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND6];
                                                                        dField2nd[6] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND7];
                                                                        dField2nd[7] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND8];
                                                                        dField2nd[8] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND9];
                                                                        dField2nd[9] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND10];
                                                                        dField2nd[10] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND11];
                                                                        dField2nd[11] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND12];
                                                                        dField2nd[12] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND13];
                                                                        dField2nd[13] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND14];
                                                                        dField2nd[14] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND15];
                                                                        dField2nd[15] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND16];
                                                                        dField2nd[16] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND17];
                                                                        dField2nd[17] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND18];
                                                                        dField2nd[18] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND19];
                                                                        dField2nd[19] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND20];
                                                                        dField2nd[20] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND21];
                                                                        dField2nd[21] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND22];
                                                                        dField2nd[22] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND23];
                                                                        dField2nd[23] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND24];
                                                                        dField2nd[24] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND25];
                                                                        dField2nd[25] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND26];
                                                                        dField2nd[26] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND27];
                                                                        dField2nd[27] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND28];
                                                                        dField2nd[28] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND29];
                                                                        dField2nd[29] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND30];
                                                                        dField2nd[30] = FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_D2ND31];

                                                                        String dSelect[] = new String[31];
                                                                        dSelect[0] = String.valueOf(empSchedule.getD1());dSelect[1] = String.valueOf(empSchedule.getD2());
                                                                        dSelect[2] = String.valueOf(empSchedule.getD3());dSelect[3] = String.valueOf(empSchedule.getD4());
                                                                        dSelect[4] = String.valueOf(empSchedule.getD5());dSelect[5] = String.valueOf(empSchedule.getD6()); 
                                                                        dSelect[6] = String.valueOf(empSchedule.getD7());dSelect[7] = String.valueOf(empSchedule.getD8());
                                                                        dSelect[8] = String.valueOf(empSchedule.getD9());dSelect[9] = String.valueOf(empSchedule.getD10());
                                                                        dSelect[10] = String.valueOf(empSchedule.getD11());dSelect[11] = String.valueOf(empSchedule.getD12());
                                                                        dSelect[12] = String.valueOf(empSchedule.getD13());dSelect[13] = String.valueOf(empSchedule.getD14());
                                                                        dSelect[14] = String.valueOf(empSchedule.getD15());dSelect[15] = String.valueOf(empSchedule.getD16());
                                                                        dSelect[16] = String.valueOf(empSchedule.getD17());dSelect[17] = String.valueOf(empSchedule.getD18());
                                                                        dSelect[18] = String.valueOf(empSchedule.getD19());dSelect[19] = String.valueOf(empSchedule.getD20());
                                                                        dSelect[20] = String.valueOf(empSchedule.getD21());dSelect[21] = String.valueOf(empSchedule.getD22());
                                                                        dSelect[22] = String.valueOf(empSchedule.getD23());dSelect[23] = String.valueOf(empSchedule.getD24());
                                                                        dSelect[24] = String.valueOf(empSchedule.getD25());dSelect[25] = String.valueOf(empSchedule.getD26());
                                                                        dSelect[26] = String.valueOf(empSchedule.getD27());dSelect[27] = String.valueOf(empSchedule.getD28());
                                                                        dSelect[28] = String.valueOf(empSchedule.getD29());dSelect[29] = String.valueOf(empSchedule.getD30());
                                                                        dSelect[30] = String.valueOf(empSchedule.getD31());
																		
																		 // Selected value of each second combobox's schedule
                                                                        String dSelect2nd[] = new String[31];
                                                                        dSelect2nd[0] = String.valueOf(empSchedule.getD2nd1());
																		dSelect2nd[1] = String.valueOf(empSchedule.getD2nd2());
                                                                        dSelect2nd[2] = String.valueOf(empSchedule.getD2nd3());
																		dSelect2nd[3] = String.valueOf(empSchedule.getD2nd4());
                                                                        dSelect2nd[4] = String.valueOf(empSchedule.getD2nd5());
																		dSelect2nd[5] = String.valueOf(empSchedule.getD2nd6());
                                                                        dSelect2nd[6] = String.valueOf(empSchedule.getD2nd7());
																		dSelect2nd[7] = String.valueOf(empSchedule.getD2nd8());
                                                                        dSelect2nd[8] = String.valueOf(empSchedule.getD2nd9());
																		dSelect2nd[9] = String.valueOf(empSchedule.getD2nd10());
                                                                        dSelect2nd[10] = String.valueOf(empSchedule.getD2nd11());
																		dSelect2nd[11] = String.valueOf(empSchedule.getD2nd12());
                                                                        dSelect2nd[12] = String.valueOf(empSchedule.getD2nd13());
																		dSelect2nd[13] = String.valueOf(empSchedule.getD2nd14());
                                                                        dSelect2nd[14] = String.valueOf(empSchedule.getD2nd15());
																		dSelect2nd[15] = String.valueOf(empSchedule.getD2nd16());
                                                                        dSelect2nd[16] = String.valueOf(empSchedule.getD2nd17());
																		dSelect2nd[17] = String.valueOf(empSchedule.getD2nd18());
                                                                        dSelect2nd[18] = String.valueOf(empSchedule.getD2nd19());
																		dSelect2nd[19] = String.valueOf(empSchedule.getD2nd20());
                                                                        dSelect2nd[20] = String.valueOf(empSchedule.getD2nd21());
																		dSelect2nd[21] = String.valueOf(empSchedule.getD2nd22());
                                                                        dSelect2nd[22] = String.valueOf(empSchedule.getD2nd23());
																		dSelect2nd[23] = String.valueOf(empSchedule.getD2nd24());
                                                                        dSelect2nd[24] = String.valueOf(empSchedule.getD2nd25());
																		dSelect2nd[25] = String.valueOf(empSchedule.getD2nd26());
                                                                        dSelect2nd[26] = String.valueOf(empSchedule.getD2nd27());
																		dSelect2nd[27] = String.valueOf(empSchedule.getD2nd28());
                                                                        dSelect2nd[28] = String.valueOf(empSchedule.getD2nd29());
																		dSelect2nd[29] = String.valueOf(empSchedule.getD2nd30());
                                                                        dSelect2nd[30] = String.valueOf(empSchedule.getD2nd31());
/*
                                                                        String dSelect[] = new String[31];
                                                                        dSelect[0] = String.valueOf(es.getD1());dSelect[1] = String.valueOf(es.getD2());
                                                                        dSelect[2] = String.valueOf(es.getD3());dSelect[3] = String.valueOf(es.getD4());
                                                                        dSelect[4] = String.valueOf(es.getD5());dSelect[5] = String.valueOf(es.getD6());
                                                                        dSelect[6] = String.valueOf(es.getD7());dSelect[7] = String.valueOf(es.getD8());
                                                                        dSelect[8] = String.valueOf(es.getD9());dSelect[9] = String.valueOf(es.getD10());
                                                                        dSelect[10] = String.valueOf(es.getD11());dSelect[11] = String.valueOf(es.getD12());
                                                                        dSelect[11] = String.valueOf(es.getD10());dSelect[10] = String.valueOf(es.getD11());
                                                                        dSelect[12] = String.valueOf(es.getD13());dSelect[13] = String.valueOf(es.getD14());
                                                                        dSelect[16] = String.valueOf(es.getD17());dSelect[17] = String.valueOf(es.getD18());
                                                                        dSelect[18] = String.valueOf(es.getD19());dSelect[19] = String.valueOf(es.getD20());
                                                                        dSelect[20] = String.valueOf(es.getD21());dSelect[21] = String.valueOf(es.getD22());
                                                                        dSelect[22] = String.valueOf(es.getD23());dSelect[23] = String.valueOf(es.getD24());
                                                                        dSelect[24] = String.valueOf(es.getD25());dSelect[25] = String.valueOf(es.getD26());
                                                                        dSelect[26] = String.valueOf(es.getD27());dSelect[27] = String.valueOf(es.getD28());
                                                                        dSelect[28] = String.valueOf(es.getD29());dSelect[29] = String.valueOf(es.getD30());
                                                                        dSelect[30] = String.valueOf(es.getD31());
*/
                                                                        String dayname[] = new String[7];
                                                                        
dayname[0] = "Sunday";
dayname[1] = "Monday";
dayname[2] = "Tuesday"; 
dayname[3] = "Wednesday";
dayname[4] = "Thursday";
dayname[5] = "Friday"; 
dayname[6] = "Saturday";

                                                                        String mon[] = new String[12];
                                                                        
mon[0] = "January"; 
mon[1] = "February"; 
mon[2] = "March"; 
mon[3] = "April";                                                                        
mon[4] = "May"; 
mon[5] = "June"; 
mon[6] = "July"; 
mon[7] = "August";                                                                        
mon[8] = "September"; 
mon[9] = "October"; 
mon[10] = "November"; 
mon[11] = "December";

                                                                        int yearStart = dtPeriodStart.getYear() + 1900;
                                                                        int monthStart = dtPeriodStart.getMonth();
                                                                        int dateStart = dtPeriodStart.getDate();
                                                                        GregorianCalendar gcStart = new GregorianCalendar(yearStart, monthStart, dateStart);
                                                                        int nDayOfMonthStart = gcStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
                                                                   //     int nDayOfWeekStart = (gcStart.get(GregorianCalendar.DAY_OF_WEEK) -1);
                                                                        /*edit by artha*/ 
                                                                        //Diperbiki untuk membuat start hari sesuai dengan yang sebenarnya.  
                                                                        int nDayOfWeekStart = (gcStart.get(GregorianCalendar.DAY_OF_WEEK) + 6)%8;
                                                                        GregorianCalendar gcLastDateStart = new GregorianCalendar(yearStart, monthStart, nDayOfMonthStart - 1);
                                                                        int nWeekInMonthStart = gcLastDateStart.get(GregorianCalendar.WEEK_OF_MONTH);

                                                                        int yearEnd = dtPeriodEnd.getYear() + 1900;
                                                                        int monthEnd = dtPeriodEnd.getMonth();
                                                                        int dateEnd = dtPeriodEnd.getDate();
                                                                        GregorianCalendar gcEnd = new GregorianCalendar(yearEnd, monthEnd, 1);
                                                                        int nDayOfMonthEnd = gcEnd.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
                                                                        int nDayOfWeekEnd = gcEnd.get(GregorianCalendar.DAY_OF_WEEK);
                                                                        GregorianCalendar gcLastDateEnd = new GregorianCalendar(yearEnd, monthEnd, nDayOfMonthEnd - 1);
                                                                        int nWeekInMonthEnd = gcLastDateEnd.get(GregorianCalendar.WEEK_OF_MONTH);

                                                                        int nDayTotal = nDayOfMonthStart - dateStart + 20 + 1;
									System.out.println("nDayOfWeekStart ::::::"+nDayOfWeekStart);
                                                                        int nWeekInMonthTotal = (nDayTotal / 7 +1) * 7;
                                                                        int i = 0;
                                                                        int j = 0;
                                                                        int dS = dateStart;
                                                                        int remainder = (((nDayTotal + nDayOfWeekStart) / 7 + 1) * 7 - (nDayTotal + nDayOfWeekStart)) % 7;
																		

                                                                                //tambahan
                                                                                // Vector schedule lengkap dengan category schedule-nya
                                                                                Vector vectScheduleFirstRules = new Vector(1,1);
                                                                                vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_REGULAR));
                                                                                vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_SPLIT_SHIFT));
                                                                                vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_NIGHT_WORKER));
                                                                                vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_ABSENCE));
                                                                                vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_OFF));
                                                                                vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_ACCROSS_DAY));
                                                                                vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT));
                                                                                vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE));																																																																																																												
                                                                                vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_LONG_LEAVE));																																																																																																												
                                                                                vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_ANNUAL_LEAVE));
                                                                                vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY));																																																																																																																																																																																				
                                                                                vectScheduleFirstRules.add(String.valueOf(PstScheduleCategory.CATEGORY_NEAR_ACCROSS_DAY));																																																																																																																																																																																				
                                                                                Vector vectScheduleWithCategory = SessEmpSchedule.getMasterSchedule(vectScheduleFirstRules);

                                                                                Vector vectScheduleSecondRules = new Vector(1,1);
                                                                                vectScheduleSecondRules.add(String.valueOf(PstScheduleCategory.CATEGORY_SPLIT_SHIFT));
                                                                                //vectScheduleSecondRules.add(String.valueOf(PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY));																		
                                                                                Vector vectSchldSecondWithCategory = SessEmpSchedule.getMasterSchedule(vectScheduleSecondRules);
                                                                    
                                                                    %>
                                                                    <%--
                                                                    dateStart = <%=dateStart%> - monthStart = <%=monthStart%> (<%=mon[gcStart.get(GregorianCalendar.MONTH)]%>) - yearStart = <%=yearStart%><br>
                                                                    nDayOfMonthStart = <%=nDayOfMonthStart%><br>
                                                                    nDayOfWeekStart = <%=nDayOfWeekStart%><br>
                                                                    nWeekInMonthStart = <%=nWeekInMonthStart%>
                                                                    <hr>
                                                                    dateEnd = <%=dateEnd%> - monthEnd = <%=monthEnd%> (<%=mon[gcEnd.get(GregorianCalendar.MONTH)]%>) - yearEnd = <%=yearEnd%><br>
                                                                    nDayOfMonthEnd = <%=nDayOfMonthEnd%><br>
                                                                    nDayOfWeekEnd = <%=nDayOfWeekEnd%><br>
                                                                    nWeekInMonthEnd = <%=nWeekInMonthEnd%>
                                                                    <hr>
                                                                    nDayTotal = <%=nDayTotal%><br>
                                                                    nWeekInMonthTotal = <%=nWeekInMonthTotal%><br>
                                                                    remainder = <%=remainder%>
                                                                    <hr>
                                                                    --%>
                                                                  </td>
                                                                </tr>
                                                                <tr> 
                                                                  <td> 
                                                                    <table border="1" cellpadding="3" cellspacing="0" bgcolor="#B0DDF0">
                                                                      <tr> 
                                                                        <td colspan="7" align="center"> 
                                                                          <%-- <table border="1" cellpadding="3" cellspacing="0" bgcolor="#CCDDF0">
                                                                            <tr>
                                                                              <td> --%>
                                                                          <b><%=mon[gcStart.get(GregorianCalendar.MONTH)]%> 
                                                                          - <%=mon[gcEnd.get(GregorianCalendar.MONTH)]%> 
                                                                          <%=String.valueOf(yearEnd)%></b> 
                                                                          <%-- </td> 
                                                                            </tr>
                                                                          </table> --%>
                                                                        </td>
                                                                      </tr>
                                                                      <tr> 
                                                                        <%
                                                                        for (int d = 0; d < 7; d++) {
                                                                            if ((d % 7) == 0) { 
                                                                                out.println("<td width=\"70\" bgcolor=\"#FFEEEE\"><font color=\"red\">" + dayname[d] + "</font></td>");
                                                                            }
                                                                            else
                                                                            if ((d % 7) == 6) { 
                                                                                out.println("<td width=\"70\" bgcolor=\"#EEFFFA\"><font color=\"blue\">" + dayname[d] + "</font></td>");
                                                                            }
                                                                            else { 
                                                                                out.println("<td width=\"70\" bgcolor=\"#DDDDFF\"><font color=\"black\">" + dayname[d] + "</font></td>");
                                                                            }
                                                                        }
                                                                        %>
                                                                      </tr>
                                                                      <tr> 
                                                                        <%
                                                                        //for (int i = 0; i < nWeekInMonthTotal; i++) {
																		//out.println("dateStart  "+dateStart);
																		//dateStart = 0;
									int total = nDayOfMonthStart + nDayOfWeekStart + 3;
																		//out.println("dateStart  "+total);
                                                                        for (i = dateStart; i < nDayOfMonthStart + nDayOfWeekStart + 3; i++) {
									    // START PROSES JIKA NILAI i ADALAH KELIPATAN 7 ==> SUNDAY
                                                                            if ((i % 7) == 0 && i>0) {
                                                                                out.println("<td bgcolor=\"#FFEEEE\">");
                                                                                if (i > nDayOfWeekStart + dateStart + 1) {
                                                                                //if ((i > nDayOfWeekStart + dateStart - 1) && (gotoPeriod != -1)) {
                                                                                    out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
                                                                                    out.println("<td width=\"20%\" valign=\"top\" align=\"left\"><font color=\"red\"><b>" + dS + "</b></font></td>");
                                                                                    out.println("<td width=\"80%\" align=\"right\">");
                                                                                    /*Vector scd_value = new Vector(1,1);
                                                                                    Vector scd_key = new Vector(1,1);
                                                                                    scd_value.add("");
                                                                                    scd_key.add("...");
                                                                                    Vector listScd = PstScheduleSymbol.listAll();
                                                                                    for (int ls = 0; ls < listScd.size(); ls++) {
                                                                                        ScheduleSymbol scd = (ScheduleSymbol) listScd.get(ls);
                                                                                        scd_key.add(scd.getSymbol());
                                                                                        scd_value.add(String.valueOf(scd.getOID()));
                                                                                    }*/
																					//first schedule
                                                                                    Vector scd_value = new Vector(1,1);
                                                                                    Vector scd_key = new Vector(1,1);
                                                                                    scd_value.add("");
                                                                                    scd_key.add("...");
																					
																					// second schedule
                                                                                    Vector scd_2nd_value = new Vector(1,1);
                                                                                    Vector scd_2nd_key = new Vector(1,1);
                                                                                    scd_2nd_value.add("0");
                                                                                    scd_2nd_key.add("...");
																					// first schedule
                                                                                    if(vectScheduleWithCategory!=null && vectScheduleWithCategory.size()>0){
                                                                                            int maxSchedule = vectScheduleWithCategory.size();
                                                                                            for (int ls = 0; ls < maxSchedule; ls++) {
                                                                                                    Vector vectResult = (Vector)vectScheduleWithCategory.get(ls);
                                                                                                    ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                                                                    ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                                                                    scd_key.add(scd.getSymbol());
                                                                                                    scd_value.add(String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType()));

                                                                                                    if(dSelect[dS-1].equals(String.valueOf(scd.getOID()))){
                                                                                                            dSelect[dS-1] = String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType());
                                                                                                    }
                                                                                            }
                                                                                    }

                                                                                    // second schedule
                                                                                    if(vectSchldSecondWithCategory!=null && vectSchldSecondWithCategory.size()>0){
                                                                                            int maxSchedule = vectSchldSecondWithCategory.size();
                                                                                            for (int ls2nd = 0; ls2nd < maxSchedule; ls2nd++) {
                                                                                                    Vector vectResult = (Vector)vectSchldSecondWithCategory.get(ls2nd);
                                                                                                    ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                                                                    ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                                                                    scd_2nd_key.add(scd.getSymbol());
                                                                                                    scd_2nd_value.add(String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType()));

                                                                                                    if(dSelect2nd[dS-1].equals(String.valueOf(scd.getOID()))){
                                                                                                            dSelect2nd[dS-1] = String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType());
                                                                                                    }
                                                                                            }
                                                                                    }
                                                                                    // combo first schedule
                                                                                    //out.println(ControlCombo.draw(dField[dS - 1], null, dSelect[dS - 1], scd_value, scd_key, "onChange=\"javascript:changeSchedule(this,'"+(dSelect[dS - 1])+"','"+dS+"')\""));
                                                                                    // tanpa javascript change schedule, kalo yang javascript pake yang atas ya
                                                                                     out.println(ControlCombo.draw(dField[dS - 1], null, dSelect[dS - 1], scd_value, scd_key,""));// "onChange=\"javascript:changeSchedule(this,'"+(dSelect[dS - 1])+"','"+dS+"')\""));


                                                                                    // combo second schedule
                                                                                    if(dSelect2nd[dS-1].equals("0"))
                                                                                    {
                                                                                            if(isCheckFirstSchedule)
                                                                                            {
                                                                                                    out.println("<br>"+ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:hidden\""));
                                                                                            }
                                                                                            else
                                                                                            {
                                                                                                    int cat1stSchld = -1;
                                                                                                    if((dSelect[dS - 1]).length() > 18)
                                                                                                    {
                                                                                                            cat1stSchld = Integer.parseInt((dSelect[dS - 1]).substring(18));
                                                                                                    }	

                                                                                                    if(cat1stSchld==PstScheduleCategory.CATEGORY_ABSENCE
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_OFF
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_LONG_LEAVE)
                                                                                                    {																																																														
                                                                                                            out.println("<br>"+ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:'hidden'\""));																						
                                                                                                    }
                                                                                                    else
                                                                                                    {
                                                                                                            out.println("<br>"+ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'"+(dSelect2nd[dS - 1])+"','"+dS+"')\""));																																													
                                                                                                    }
                                                                                            }
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                            out.println("<br>"+ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'"+(dSelect2nd[dS - 1])+"','"+dS+"')\""));
                                                                                    }

                                                                                %>
                                                                        <%//= ControlCombo.draw(dField[dS - 1], "formElemen", null, dSelect[dS - 1], scd_value, scd_key) %> 
                                                                        <%
																					
                                                                                    out.println("</td>");
                                                                                    out.println("</tr></table>");
                                                                                    dS++;
                                                                                }
                                                                                else {
                                                                                    out.println("&nbsp;");
                                                                                }
                                                                                out.println("</td>");
                                                                            }
                                                                            else
                                                                            if ((i % 7) == 6) {
                                                                                out.println("<td bgcolor=\"#EEFFFA\">");
                                                                                if (i > nDayOfWeekStart + dateStart + 1) {
                                                                                //if ((i > nDayOfWeekStart + dateStart - 1) && (gotoPeriod != -1)) {
                                                                                    out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
                                                                                    out.println("<td width=\"20%\" valign=\"top\" align=\"left\"><font color=\"blue\"><b>" + dS + "</b></font></td>");
                                                                                    out.println("<td width=\"80%\" align=\"right\">");
                                                                                   /* Vector scd_value = new Vector(1,1);
                                                                                    Vector scd_key = new Vector(1,1);
                                                                                    scd_value.add("");
                                                                                    scd_key.add("...");
                                                                                    Vector listScd = PstScheduleSymbol.listAll();
                                                                                    for (int ls = 0; ls < listScd.size(); ls++) {
                                                                                        ScheduleSymbol scd = (ScheduleSymbol) listScd.get(ls);
                                                                                        scd_key.add(scd.getSymbol());
                                                                                        scd_value.add(String.valueOf(scd.getOID()));
                                                                                    }*/
																					//first schedule
                                                                                    Vector scd_value = new Vector(1,1);
                                                                                    Vector scd_key = new Vector(1,1);
                                                                                    scd_value.add("");
                                                                                    scd_key.add("...");
																					
																					// second schedule
                                                                                    Vector scd_2nd_value = new Vector(1,1);
                                                                                    Vector scd_2nd_key = new Vector(1,1);
                                                                                    scd_2nd_value.add("0");
                                                                                    scd_2nd_key.add("...");
																					
                                                                                    // first schedule
                                                                                    if(vectScheduleWithCategory!=null && vectScheduleWithCategory.size()>0){
                                                                                        int maxSchedule = vectScheduleWithCategory.size();
                                                                                        for (int ls = 0; ls < maxSchedule; ls++) {
                                                                                            Vector vectResult = (Vector)vectScheduleWithCategory.get(ls);
                                                                                            ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                                                            ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                                                            scd_key.add(scd.getSymbol());
                                                                                            scd_value.add(String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType()));

                                                                                            if(dSelect[dS-1].equals(String.valueOf(scd.getOID()))){
                                                                                                    dSelect[dS-1] = String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType());
                                                                                            }
                                                                                        }
                                                                                    }

                                                                                    // second schedule
                                                                                    if(vectSchldSecondWithCategory!=null && vectSchldSecondWithCategory.size()>0){
                                                                                            int maxSchedule = vectSchldSecondWithCategory.size();
                                                                                            for (int ls2nd = 0; ls2nd < maxSchedule; ls2nd++) {
                                                                                                    Vector vectResult = (Vector)vectSchldSecondWithCategory.get(ls2nd);
                                                                                                    ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                                                                    ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                                                                    scd_2nd_key.add(scd.getSymbol());
                                                                                                    scd_2nd_value.add(String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType()));

                                                                                                    if(dSelect2nd[dS-1].equals(String.valueOf(scd.getOID()))){
                                                                                                            dSelect2nd[dS-1] = String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType());
                                                                                                    }
                                                                                            }
                                                                                    }
                                                                                    // combo first schedule
                                                                                    //out.println(ControlCombo.draw(dField[dS - 1], null, dSelect[dS - 1], scd_value, scd_key, "onChange=\"javascript:changeSchedule(this,'"+(dSelect[dS - 1])+"','"+dS+"')\""));
                                                                                     out.println(ControlCombo.draw(dField[dS - 1], null, dSelect[dS - 1], scd_value, scd_key,""));// "onChange=\"javascript:changeSchedule(this,'"+(dSelect[dS - 1])+"','"+dS+"')\""));

                                                                                    // combo second schedule
                                                                                    if(dSelect2nd[dS-1].equals("0"))
                                                                                    {
                                                                                            if(isCheckFirstSchedule)
                                                                                            {
                                                                                                    out.println("<br>"+ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:hidden\""));
                                                                                            }
                                                                                            else
                                                                                            {
                                                                                                    int cat1stSchld = -1;
                                                                                                    if((dSelect[dS - 1]).length() > 18)
                                                                                                    {
                                                                                                            cat1stSchld = Integer.parseInt((dSelect[dS - 1]).substring(18));
                                                                                                    }	

                                                                                                    if(cat1stSchld==PstScheduleCategory.CATEGORY_ABSENCE
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_OFF
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_LONG_LEAVE)
                                                                                                    {																																																														
                                                                                                            out.println("<br>"+ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:'hidden'\""));																						
                                                                                                    }
                                                                                                    else
                                                                                                    {
                                                                                                            out.println("<br>"+ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'"+(dSelect2nd[dS - 1])+"','"+dS+"')\""));																																													
                                                                                                    }
                                                                                            }
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                            out.println("<br>"+ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'"+(dSelect2nd[dS - 1])+"','"+dS+"')\""));
                                                                                    }

                                                                                %>
                                                                        <%//= ControlCombo.draw(dField[dS - 1], "formElemen", null, dSelect[dS - 1], scd_value, scd_key) %> 
                                                                        <%
                                                                                    out.println("</td>");
                                                                                    out.println("</tr></table>");
                                                                                    dS++;
                                                                                }
                                                                                else {
                                                                                    out.println("&nbsp;");
                                                                                }
                                                                                out.println("</td>");
                                                                            }
                                                                            else {
                                                                                out.println("<td bgcolor=\"#FFFFFF\">");
                                                                                if (i > nDayOfWeekStart + dateStart + 1) {
                                                                                //if ((i > nDayOfWeekStart + dateStart - 1) && (gotoPeriod != -1)) {
                                                                                    out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
                                                                                    out.println("<td width=\"20%\" valign=\"top\" align=\"left\"><font color=\"black\"><b>" + dS + "</b></font></td>");
                                                                                    out.println("<td width=\"80%\" align=\"right\">");
                                                                                    /*Vector scd_value = new Vector(1,1);
                                                                                    Vector scd_key = new Vector(1,1);
                                                                                    scd_value.add("");
                                                                                    scd_key.add("...");
                                                                                    Vector listScd = PstScheduleSymbol.listAll();
                                                                                    for (int ls = 0; ls < listScd.size(); ls++) {
                                                                                        ScheduleSymbol scd = (ScheduleSymbol) listScd.get(ls);
                                                                                        scd_key.add(scd.getSymbol());
                                                                                        scd_value.add(String.valueOf(scd.getOID()));
                                                                                    }*/
																					//first schedule
                                                                                    Vector scd_value = new Vector(1,1);
                                                                                    Vector scd_key = new Vector(1,1);
                                                                                    scd_value.add("");
                                                                                    scd_key.add("...");
																					
																					// second schedule
                                                                                    Vector scd_2nd_value = new Vector(1,1);
                                                                                    Vector scd_2nd_key = new Vector(1,1);
                                                                                    scd_2nd_value.add("0");
                                                                                    scd_2nd_key.add("...");

                                                                                    // first schedule
                                                                                    if(vectScheduleWithCategory!=null && vectScheduleWithCategory.size()>0){
                                                                                            int maxSchedule = vectScheduleWithCategory.size();
                                                                                            for (int ls = 0; ls < maxSchedule; ls++) {
                                                                                                    Vector vectResult = (Vector)vectScheduleWithCategory.get(ls);
                                                                                                    ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                                                                    ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                                                                    scd_key.add(scd.getSymbol());
                                                                                                    scd_value.add(String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType()));

                                                                                                    if(dSelect[dS-1].equals(String.valueOf(scd.getOID()))){
                                                                                                            dSelect[dS-1] = String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType());
                                                                                                    }
                                                                                            }
                                                                                    }
                                                                                    // second schedule
                                                                                    if(vectSchldSecondWithCategory!=null && vectSchldSecondWithCategory.size()>0){
                                                                                            int maxSchedule = vectSchldSecondWithCategory.size();
                                                                                            for (int ls2nd = 0; ls2nd < maxSchedule; ls2nd++) {
                                                                                                    Vector vectResult = (Vector)vectSchldSecondWithCategory.get(ls2nd);
                                                                                                    ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                                                                    ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                                                                    scd_2nd_key.add(scd.getSymbol());
                                                                                                    scd_2nd_value.add(String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType()));

                                                                                                    if(dSelect2nd[dS-1].equals(String.valueOf(scd.getOID()))){
                                                                                                            dSelect2nd[dS-1] = String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType());
                                                                                                    }
                                                                                            }
                                                                                    }
                                                                                    // combo first schedule
                                                                                    //out.println(ControlCombo.draw(dField[dS - 1], null, dSelect[dS - 1], scd_value, scd_key, "onChange=\"javascript:changeSchedule(this,'"+(dSelect[dS - 1])+"','"+dS+"')\""));
                                                                                      out.println(ControlCombo.draw(dField[dS - 1], null, dSelect[dS - 1], scd_value, scd_key, ""));//"onChange=\"javascript:changeSchedule(this,'"+(dSelect[dS - 1])+"','"+dS+"')\""));

                                                                                    // combo second schedule
                                                                                    if(dSelect2nd[dS-1].equals("0"))
                                                                                    {
                                                                                            if(isCheckFirstSchedule)
                                                                                            {
                                                                                                    out.println("<br>"+ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:hidden\""));
                                                                                            }
                                                                                            else
                                                                                            {
                                                                                                    int cat1stSchld = -1;
                                                                                                    if((dSelect[dS - 1]).length() > 18)
                                                                                                    {
                                                                                                            cat1stSchld = Integer.parseInt((dSelect[dS - 1]).substring(18));
                                                                                                    }	

                                                                                                    if(cat1stSchld==PstScheduleCategory.CATEGORY_ABSENCE
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_OFF
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_LONG_LEAVE)
                                                                                                    {																																																														
                                                                                                            out.println("<br>"+ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:'hidden'\""));																						
                                                                                                    }
                                                                                                    else
                                                                                                    {
                                                                                                            out.println("<br>"+ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'"+(dSelect2nd[dS - 1])+"','"+dS+"')\""));																																													
                                                                                                    }
                                                                                            }
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                            out.println("<br>"+ControlCombo.draw(dField2nd[dS - 1], null, dSelect2nd[dS - 1], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'"+(dSelect2nd[dS - 1])+"','"+dS+"')\""));
                                                                                    }




                                                                                %>
                                                                        <%//= ControlCombo.draw(dField[dS - 1], "formElemen", null, dSelect[dS - 1], scd_value, scd_key) %> 
                                                                        <%
                                                                                    System.out.println("dS : " + dS + " - dField : " + dField[dS - 1]);
                                                                                    out.println("</td>");
                                                                                    out.println("</tr></table>");
                                                                                    dS++;
                                                                                }
                                                                                else {
                                                                                    out.println("&nbsp;");
                                                                                }
                                                                                out.println("</td>");
                                                                            }
                                                                            if ((i + 1) % 7 == 0) {
                                                                                out.println("<tr>");
                                                                            }
                                                                        }
                                                                        int stopI = i;
																		if(startDatePeriod > 1) {
                                                                        for (j = 0; j < dateEnd + remainder; j++, i++) {
                                                                            if ((i % 7) == 0) {
                                                                                out.println("<td valign=\"top\" bgcolor=\"#FFEEEE\">");
                                                                                if ((i > nDayOfWeekStart + dateStart - 1) && (i < dateEnd + stopI)) {
                                                                                    out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
                                                                                    out.println("<td width=\"20%\"  valign=\"top\" align=\"left\"><font color=\"red\"><b>"+(j+1)+"</b></font></td>");
                                                                                    out.println("<td width=\"80%\"align=\"right\">");
																					//first schedule
                                                                                    Vector scd_value = new Vector(1,1);
                                                                                    Vector scd_key = new Vector(1,1);
                                                                                    scd_value.add("");
                                                                                    scd_key.add("...");
																					
																					// second schedule
                                                                                    Vector scd_2nd_value = new Vector(1,1);
                                                                                    Vector scd_2nd_key = new Vector(1,1);
                                                                                    scd_2nd_value.add("0");
                                                                                    scd_2nd_key.add("...");
																					
																					
																					
                                                                                    /*Vector listScd = PstScheduleSymbol.listAll();
                                                                                    for (int ls = 0; ls < listScd.size(); ls++) {
                                                                                        ScheduleSymbol scd = (ScheduleSymbol) listScd.get(ls);
                                                                                        scd_key.add(scd.getSymbol());
                                                                                        scd_value.add(String.valueOf(scd.getOID()));
                                                                                    }*/
																					// first schedule
                                                                                            if(vectScheduleWithCategory!=null && vectScheduleWithCategory.size()>0){
                                                                                                    int maxSchedule = vectScheduleWithCategory.size();
                                                                                                    for (int ls = 0; ls < maxSchedule; ls++) {
                                                                                                            Vector vectResult = (Vector)vectScheduleWithCategory.get(ls);
                                                                                                            ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                                                                            ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                                                                            scd_key.add(scd.getSymbol());
                                                                                                            scd_value.add(String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType()));

                                                                                                            if(dSelect[j].equals(String.valueOf(scd.getOID()))){
                                                                                                                    dSelect[j] = String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType());
                                                                                                            }
                                                                                                    }
                                                                                            }

                                                                                            // second schedule
                                                                                            if(vectSchldSecondWithCategory!=null && vectSchldSecondWithCategory.size()>0){
                                                                                                    int maxSchedule = vectSchldSecondWithCategory.size();
                                                                                                    for (int ls2nd = 0; ls2nd < maxSchedule; ls2nd++) {
                                                                                                            Vector vectResult = (Vector)vectSchldSecondWithCategory.get(ls2nd);
                                                                                                            ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                                                                            ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                                                                            scd_2nd_key.add(scd.getSymbol());
                                                                                                            scd_2nd_value.add(String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType()));

                                                                                                            if(dSelect2nd[j].equals(String.valueOf(scd.getOID()))){
                                                                                                                    dSelect2nd[j] = String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType());
                                                                                                            }
                                                                                                    }
                                                                                            }

                                                                                            // combo first schedule
                                                                        		    //out.println(ControlCombo.draw(dField[j], null, dSelect[j], scd_value, scd_key, "onChange=\"javascript:changeSchedule(this,'"+(dSelect[j])+"','"+dS+"')\""));
                                                                                     out.println(ControlCombo.draw(dField[j], null, dSelect[j], scd_value, scd_key,""));// "onChange=\"javascript:changeSchedule(this,'"+(dSelect[j])+"','"+dS+"')\""));

                                                                                    // combo second schedule
                                                                                    if(dSelect2nd[j].equals("0"))
                                                                                    {
                                                                                            if(isCheckFirstSchedule)
                                                                                            {
                                                                                                    out.println("<br>"+ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:hidden\""));
                                                                                            }
                                                                                            else
                                                                                            {
                                                                                                    int cat1stSchld = -1;
                                                                                                    if((dSelect[j]).length() > 18)
                                                                                                    {
                                                                                                            cat1stSchld = Integer.parseInt((dSelect[j]).substring(18));
                                                                                                    }	

                                                                                                    if(cat1stSchld==PstScheduleCategory.CATEGORY_ABSENCE
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_OFF
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_LONG_LEAVE)
                                                                                                    {																																																														
                                                                                                            out.println("<br>"+ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:'hidden'\""));																						
                                                                                                    }
                                                                                                    else
                                                                                                    {
                                                                                                            out.println("<br>"+ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'"+(dSelect2nd[j])+"','"+dS+"')\""));																																													
                                                                                                    }
                                                                                            }
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                            out.println("<br>"+ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'"+(dSelect2nd[j])+"','"+dS+"')\""));
                                                                                    }


                                                                                %>
                                                                        <%//= ControlCombo.draw(dField[j], "formElemen", null, dSelect[j], scd_value, scd_key) %> 
                                                                        <%
                                                                                    
                                                                                    out.println("</td>");
                                                                                    out.println("</tr></table>");
                                                                                    dS++;
                                                                                }
                                                                                else {
                                                                                    out.println("&nbsp;");
                                                                                }
                                                                                out.println("</td>");
                                                                            }
                                                                            else
                                                                            if ((i % 7) == 6) {
                                                                                System.out.println("Data I : "+i);
                                                                                if ((i > nDayOfWeekStart + dateStart - 1) && (i < dateEnd + stopI)) {
                                                                                out.println("<td valign=\"top\" bgcolor=\"#EEFFFA\">");
                                                                                    out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
                                                                                    out.println("<td width=\"20%\"  valign=\"top\"  align=\"left\"><font color=\"blue\"><b>" + (j+1) + "</b></font></td>");
                                                                                    out.println("<td width=\"80%\" align=\"right\">");
																					//first schedule
                                                                                    Vector scd_value = new Vector(1,1);
                                                                                    Vector scd_key = new Vector(1,1);
                                                                                    scd_value.add("");
                                                                                    scd_key.add("...");
																					
																					// second schedule
                                                                                    Vector scd_2nd_value = new Vector(1,1);
                                                                                    Vector scd_2nd_key = new Vector(1,1);
                                                                                    scd_2nd_value.add("0");
                                                                                    scd_2nd_key.add("...");
                                                                                   /* Vector scd_value = new Vector(1,1);
                                                                                    Vector scd_key = new Vector(1,1);
                                                                                    scd_value.add("");
                                                                                    scd_key.add("...");
                                                                                    Vector listScd = PstScheduleSymbol.listAll();
                                                                                    for (int ls = 0; ls < listScd.size(); ls++) {
                                                                                        ScheduleSymbol scd = (ScheduleSymbol) listScd.get(ls);
                                                                                        scd_key.add(scd.getSymbol());
                                                                                        scd_value.add(String.valueOf(scd.getOID()));
                                                                                    }*/
                                                                                    // first schedule
                                                                                    if(vectScheduleWithCategory!=null && vectScheduleWithCategory.size()>0){
                                                                                            int maxSchedule = vectScheduleWithCategory.size();
                                                                                            for (int ls = 0; ls < maxSchedule; ls++) {
                                                                                                    Vector vectResult = (Vector)vectScheduleWithCategory.get(ls);
                                                                                                    ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                                                                    ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                                                                    scd_key.add(scd.getSymbol());
                                                                                                    scd_value.add(String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType()));

                                                                                                    if(dSelect[j].equals(String.valueOf(scd.getOID()))){
                                                                                                            dSelect[j] = String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType());
                                                                                                    }
                                                                                            }
                                                                                    }

                                                                                    // second schedule
                                                                                    if(vectSchldSecondWithCategory!=null && vectSchldSecondWithCategory.size()>0){
                                                                                            int maxSchedule = vectSchldSecondWithCategory.size();
                                                                                            for (int ls2nd = 0; ls2nd < maxSchedule; ls2nd++) {
                                                                                                    Vector vectResult = (Vector)vectSchldSecondWithCategory.get(ls2nd);
                                                                                                    ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                                                                    ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                                                                    scd_2nd_key.add(scd.getSymbol());
                                                                                                    scd_2nd_value.add(String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType()));

                                                                                                    if(dSelect2nd[j].equals(String.valueOf(scd.getOID()))){
                                                                                                            dSelect2nd[j] = String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType());
                                                                                                    }
                                                                                            }
                                                                                    }
                                                                                    // combo first schedule
                                                                                    //out.println(ControlCombo.draw(dField[j], null, dSelect[j], scd_value, scd_key, "onChange=\"javascript:changeSchedule(this,'"+(dSelect[j])+"','"+dS+"')\""));
                                                                                  out.println(ControlCombo.draw(dField[j], null, dSelect[j], scd_value, scd_key,""));// "onChange=\"javascript:changeSchedule(this,'"+(dSelect[j])+"','"+dS+"')\""));
                                                                                    // combo second schedule
                                                                                    if(dSelect2nd[j].equals("0"))
                                                                                    {
                                                                                            if(isCheckFirstSchedule)
                                                                                            {
                                                                                                    out.println("<br>"+ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:hidden\""));
                                                                                            }
                                                                                            else
                                                                                            {
                                                                                                    int cat1stSchld = -1;
                                                                                                    if((dSelect[j]).length() > 18)
                                                                                                    {
                                                                                                            cat1stSchld = Integer.parseInt((dSelect[j]).substring(18));
                                                                                                    }	

                                                                                                    if(cat1stSchld==PstScheduleCategory.CATEGORY_ABSENCE
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_OFF
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                                                                                       || cat1stSchld==PstScheduleCategory.CATEGORY_LONG_LEAVE)
                                                                                                    {																																																														
                                                                                                            out.println("<br>"+ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:'hidden'\""));																						
                                                                                                    }
                                                                                                    else
                                                                                                    {
                                                                                                            out.println("<br>"+ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'"+(dSelect2nd[j])+"','"+dS+"')\""));																																													
                                                                                                    }
                                                                                            }
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                            out.println("<br>"+ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'"+(dSelect2nd[j])+"','"+dS+"')\""));
                                                                                    }

                                                                                %>
																					
                                                                        		<%//= ControlCombo.draw(dField[j], "formElemen", null, dSelect[j], scd_value, scd_key) %> 
																					<%
																					out.println("</td>");
                                                                                    out.println("</tr></table>");
                                                                                    dS++;
                                                                                }
                                                                                else {
                                                                                    out.println("&nbsp;");
                                                                                }
                                                                                out.println("</td>");
                                                                            }
                                                                            else {
                                                                                out.println("<td valign=\"top\" bgcolor=\"#FFFFFF\">");
                                                                                if ((i > nDayOfWeekStart + dateStart - 1) && (i < dateEnd + stopI)) {
                                                                                    out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
                                                                                    out.println("<td width=\"20%\"  valign=\"top\" align=\"left\"><font color=\"black\"><b>" + (j+1) + "</b></font></td>");
                                                                                    out.println("<td width=\"80%\" align=\"right\">");
                                                                                    /*Vector scd_value = new Vector(1,1);
                                                                                    Vector scd_key = new Vector(1,1);
                                                                                    scd_value.add("");
                                                                                    scd_key.add("...");
                                                                                    Vector listScd = PstScheduleSymbol.listAll();
                                                                                    for (int ls = 0; ls < listScd.size(); ls++) {
                                                                                        ScheduleSymbol scd = (ScheduleSymbol) listScd.get(ls);
                                                                                        scd_key.add(scd.getSymbol());
                                                                                        scd_value.add(String.valueOf(scd.getOID()));
                                                                                    }*/
																					//first schedule
                                                                                    Vector scd_value = new Vector(1,1);
                                                                                    Vector scd_key = new Vector(1,1);
                                                                                    scd_value.add("");
                                                                                    scd_key.add("...");
																					
																					// second schedule
                                                                                    Vector scd_2nd_value = new Vector(1,1);
                                                                                    Vector scd_2nd_key = new Vector(1,1);
                                                                                    scd_2nd_value.add("0");
                                                                                    scd_2nd_key.add("...");
																					
                                                                                        // first schedule
                                                                                        if(vectScheduleWithCategory!=null && vectScheduleWithCategory.size()>0){
                                                                                                int maxSchedule = vectScheduleWithCategory.size();
                                                                                                for (int ls = 0; ls < maxSchedule; ls++) {
                                                                                                        Vector vectResult = (Vector)vectScheduleWithCategory.get(ls);
                                                                                                        ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                                                                        ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                                                                        scd_key.add(scd.getSymbol());
                                                                                                        scd_value.add(String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType()));

                                                                                                        if(dSelect[j].equals(String.valueOf(scd.getOID()))){
                                                                                                                dSelect[j] = String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType());
                                                                                                        }
                                                                                                }
                                                                                        }

                                                                                        // second schedule
                                                                                        if(vectSchldSecondWithCategory!=null && vectSchldSecondWithCategory.size()>0){
                                                                                                int maxSchedule = vectSchldSecondWithCategory.size();
                                                                                                for (int ls2nd = 0; ls2nd < maxSchedule; ls2nd++) {
                                                                                                        Vector vectResult = (Vector)vectSchldSecondWithCategory.get(ls2nd);
                                                                                                        ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
                                                                                                        ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

                                                                                                        scd_2nd_key.add(scd.getSymbol());
                                                                                                        scd_2nd_value.add(String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType()));

                                                                                                        if(dSelect2nd[j].equals(String.valueOf(scd.getOID()))){
                                                                                                                dSelect2nd[j] = String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType());
                                                                                                        }
                                                                                                }
                                                                                        }

                                                                                        // combo first schedule
                                                                        		    //out.println(ControlCombo.draw(dField[j], null, dSelect[j], scd_value, scd_key, "onChange=\"javascript:changeSchedule(this,'"+(dSelect[j])+"','"+dS+"')\""));
																					out.println(ControlCombo.draw(dField[j], null, dSelect[j], scd_value, scd_key,""));// "onChange=\"javascript:changeSchedule(this,'"+(dSelect[j])+"','"+dS+"')\""));
                                                                            // combo second schedule
                                                                            if(dSelect2nd[j].equals("0"))
                                                                            {
                                                                                    if(isCheckFirstSchedule)
                                                                                    {
                                                                                            out.println("<br>"+ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:hidden\""));
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                            int cat1stSchld = -1;
                                                                                            if((dSelect[j]).length() > 18)
                                                                                            {
                                                                                                    cat1stSchld = Integer.parseInt((dSelect[j]).substring(18));
                                                                                            }	

                                                                                            if(cat1stSchld==PstScheduleCategory.CATEGORY_ABSENCE
                                                                                               || cat1stSchld==PstScheduleCategory.CATEGORY_OFF
                                                                                               || cat1stSchld==PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT
                                                                                               || cat1stSchld==PstScheduleCategory.CATEGORY_ANNUAL_LEAVE
                                                                                               || cat1stSchld==PstScheduleCategory.CATEGORY_LONG_LEAVE)
                                                                                            {																																																														
                                                                                                    out.println("<br>"+ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:'hidden'\""));																						
                                                                                            }
                                                                                            else
                                                                                            {
                                                                                                    out.println("<br>"+ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'"+(dSelect2nd[j])+"','"+dS+"')\""));																																													
                                                                                            }
                                                                                    }
                                                                            }
                                                                            else
                                                                            {
                                                                                    out.println("<br>"+ControlCombo.draw(dField2nd[j], null, dSelect2nd[j], scd_2nd_value, scd_2nd_key, "style=\"visibility:''\" onChange=\"javascript:change2ndSchedule(this,'"+(dSelect2nd[j])+"','"+dS+"')\""));
                                                                            }

                                                                                %>
                                                                        <%//= ControlCombo.draw(dField[j], "formElemen", null, dSelect[j], scd_value, scd_key) %> 
                                                                        <%
                                                                                    out.println("</td>");
                                                                                    out.println("</tr></table>");
                                                                                    dS++;
                                                                                }
                                                                                else {
                                                                                    out.println("&nbsp;");
                                                                                }
                                                                                out.println("</td>");
                                                                            }
                                                                            if ((i + 1) % 7 == 0) {
                                                                                out.println("<tr>");
                                                                            }
                                                                        }
																	}	
                                                                        %>
                                                                      </tr>
                                                                      <% if (iCommand==Command.EDIT) {%>
                                                                      <tr> 
                                                                        <td colspan="7"><a href="javascript:cmdCopyPaste(<%=String.valueOf(oidEmpSchedule)%>);">Create</a> 
                                                                          new Working Schedule based on this working schedule 
                                                                        </td>
                                                                      </tr>
                                                                      <% } %>
                                                                    </table>
                                                                  </td>
                                                                </tr>
                                                              </table>
                                                            </td>
                                                          </tr>
                                                        </table>
                                                        <%
                                                                        }
                                                        %>
                                                      </td>
                                                    </tr>
                                                    <%
                                                        if ((gotoPeriod == -1) || ((gotoPeriod == 0) && (iCommand == Command.ADD))) {
                                                        %>
                                                    <tr> 
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="7%" valign="top" align="right">&nbsp;</td>
                                                      <td width="91%"> 
                                                        <%//=drawList(listScheduleSymbol)%>
                                                      </td>
                                                    </tr>
                                                    <% } else { %>
                                                    <tr> 
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="7%" valign="top" align="right"> 
                                                        <!-- <div align="left">Symbol</div> -->
                                                      </td>
                                                      <td width="91%"> <%=drawList(listScheduleSymbol)%> 
                                                      </td>
                                                    </tr>
                                                    <% } %>
                                                    <%-- <tr>
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="7%">&nbsp;</td>
                                                      <td width="91%">&nbsp;</td>
                                                    </tr> --%>
                                                    <tr> 
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="7%">&nbsp;</td>
                                                      <td width="91%"> 
                                                        <%
							ctrLine.setLocationImg(approot+"/images");
							ctrLine.initDefault();
							ctrLine.setTableWidth("100");
							String scomDel = "javascript:cmdAsk('"+oidEmpSchedule+"')";
							String sconDelCom = "javascript:cmdConfirmDelete('"+oidEmpSchedule+"')";
							String scancel = "javascript:cmdEdit('"+oidEmpSchedule+"')";
							ctrLine.setBackCaption("Back to List Schedule");
							ctrLine.setCommandStyle("buttonlink");
							ctrLine.setConfirmDelCaption("Yes Delete Schedule");
							ctrLine.setDeleteCaption("Delete Schedule");
							ctrLine.setSaveCaption("Save Schedule");

							if (privDelete){
								ctrLine.setConfirmDelCommand(sconDelCom);
								ctrLine.setDeleteCommand(scomDel);
								ctrLine.setEditCommand(scancel);
							}else{ 
								ctrLine.setConfirmDelCaption("");
								ctrLine.setDeleteCaption("");
								ctrLine.setEditCaption("");
							}

							if(privAdd == false  && privUpdate == false){
								ctrLine.setSaveCaption("");
							}

							if (privAdd == false){
								ctrLine.setAddCaption("");
							}

                                                        if ((gotoPeriod == -1) || ((gotoPeriod == 0) && (iCommand == Command.ADD))) {
                                                        }
                                                        else {
							%>
                                                        <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%> 
                                                        <% }
                                                        %>
                                                      </td>
                                                    </tr>
                                                  </table>
                                                </td>
                                              </tr>
                                            </table>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="3"> 
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
  <tr> 
    <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
      <!-- #EndEditable --> </td>
  </tr>
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>
