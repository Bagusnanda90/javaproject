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
<%@ page import = "java.util.Vector,
                   java.util.Date,
                   java.util.GregorianCalendar,
				   com.dimata.util.Command,
                   com.dimata.util.Formater,
                   com.dimata.gui.jsp.ControlLine,
                   com.dimata.gui.jsp.ControlCombo,
                   com.dimata.qdep.form.FRMQueryString,
                   com.dimata.qdep.form.FRMMessage,
                   com.dimata.harisma.form.attendance.CtrlDpStockManagement,
                   com.dimata.harisma.entity.attendance.EmpSchedule,
                   com.dimata.harisma.form.attendance.FrmEmpSchedule,
                   com.dimata.harisma.form.attendance.CtrlEmpSchedule,
                   com.dimata.harisma.entity.attendance.PstEmpSchedule,
                   com.dimata.harisma.entity.leave.PstDpApplication,
                   com.dimata.harisma.entity.leave.PstLeaveApplication,				   				   
                   com.dimata.harisma.session.attendance.SessEmpSchedule,
                   com.dimata.harisma.session.attendance.DPMontly,
                   com.dimata.harisma.session.attendance.AnnualLeaveMontly,
                   com.dimata.harisma.session.attendance.SessLongLeave" %>


<%@ include file = "../../main/javainit.jsp" %>     
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_WORKING_SCHEDULE); %>
<%@ include file = "../../main/checkuser.jsp" %>

<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
//boolean privUpdate=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
//boolean privDelete=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
%>

<!-- Jsp Block -->
<%!
public String drawList(Vector objectClass) {
	String strSchedule = "<table border=\"0\" cellspacing=\"0\"" + "cellpadding=\"1\" bgcolor=\"#E0EDF0\"><tr>";
	for (int i = 0; i < objectClass.size(); i++) {
		ScheduleSymbol scheduleSymbol = (ScheduleSymbol) objectClass.get(i);
		String str_dt_TimeIn = "";
		String str_dt_TimeOut = "";

		try
		{
			Date dt_TimeIn = scheduleSymbol.getTimeIn();
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
// configuration for checking "updating emp schedule"
Date dtCurrentDate = new Date();
boolean checkUpdateScheduleByDate = false; 
int intcheckUpdateScheduleByDate = Integer.parseInt(""+PstSystemProperty.getValueByName("CHECK_UPDATE_SCHEDULE")); 
if(intcheckUpdateScheduleByDate == 1)
{
	checkUpdateScheduleByDate = true;
}

int intCheckLeaveRoster = Integer.parseInt(""+PstSystemProperty.getValueByName("UPDATE_SCHLD_TIME_LEAVE_ROSTER")); 
int intCheckDPRoster = Integer.parseInt(""+PstSystemProperty.getValueByName("UPDATE_SCHLD_TIME_DP_ROSTER")); 
int intCheckOtherRoster = Integer.parseInt(""+PstSystemProperty.getValueByName("UPDATE_SCHLD_TIME_OTHER_ROSTER")); 

long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
boolean isSecretaryLogin = (positionType>=PstPosition.LEVEL_SECRETARY) ? true : false;
boolean isHRDLogin = isSecretaryLogin && (departmentOid==hrdDepartmentOid);


// get data from form request
long oidEmpSchedule = FRMQueryString.requestLong(request, "hidden_emp_schedule_id");
long gotoPeriod = FRMQueryString.requestLong(request, "hidden_goto_period");
long gotoEmployee = FRMQueryString.requestLong(request, "hidden_goto_employee");
int maxToleranDpQuantity = 100;

int iErrCode = FRMMessage.ERR_NONE;
String errMsg = "";
int iCommand = FRMQueryString.requestCommand(request);
int start = FRMQueryString.requestInt(request,"start");

String orderScheduleSymbol = PstScheduleSymbol.fieldNames[PstScheduleSymbol.FLD_SYMBOL];

// menyimpan jumlah hari pada bulan berikutnya yang termasuk dalam schedule ini
int totalDaysInNextMonth = 0;

// flag untuk melakukan pengecekan apakah shedule kedua muncul tergantung pada schedule pertama ???
boolean isCheckFirstSchedule = true;

// get employee schedule previous update
EmpSchedule empSchedulePrevUpdate = new EmpSchedule();
try
{
    empSchedulePrevUpdate = PstEmpSchedule.fetchExc(oidEmpSchedule);
}
catch(Exception e)
{
    empSchedulePrevUpdate = new EmpSchedule();
}

ControlLine ctrLine = new ControlLine();
CtrlEmpSchedule ctrlEmpSchedule = new CtrlEmpSchedule(request);
iErrCode = ctrlEmpSchedule.action(iCommand, oidEmpSchedule);

errMsg = ctrlEmpSchedule.getMessage();
FrmEmpSchedule frmEmpSchedule = ctrlEmpSchedule.getForm();
EmpSchedule empSchedule = ctrlEmpSchedule.getEmpSchedule();

oidEmpSchedule = empSchedule.getOID();
long oidPeriod = empSchedule.getPeriodId();
long oidEmployee = empSchedule.getEmployeeId();


if(iCommand==Command.SAVE)
{
	// --- proses import presence ---
	// proses ini dilakukan jika terlambat insert schedule ke HARISMA
	// atau update schedule untuk masing-masing employee
	// add by edhy
	// update in schedule         
	
	Date dtBefore = new Date();      
	System.out.println(".::JSP - importPresenceTriggerByEmpSchedule before : " + (dtBefore).getTime());                                             
	com.dimata.harisma.entity.attendance.PstPresence.importPresenceTriggerByEmpSchedule(empSchedulePrevUpdate, empSchedule);                                
	System.out.println(".::JSP - importPresenceTriggerByEmpSchedule after  : " + (new Date()).getTime()); 
	

	// --- proses generate DP ---
	// proses DP ini akan generate DP baru jika proses "INSERT" schedule
	// generate dan/atau update DP jika proses "UPDATE" schedule 
	System.out.println(".::JSP - per generateDp before : " + (new Date()).getTime());
    CtrlDpStockManagement ctrlDpStMng = new CtrlDpStockManagement(request);
    ctrlDpStMng.generateDpStock(empSchedulePrevUpdate,empSchedule,oidPeriod,oidEmployee);
	System.out.println(".::JSP - per generateDp after  : " + (new Date()).getTime()); 
	
	System.out.println(".::JSP - per generateDpEod before : " + (new Date()).getTime());	
    ctrlDpStMng.generateDpEod(empSchedulePrevUpdate,empSchedule,oidPeriod,oidEmployee);
	System.out.println(".::JSP - per generateDpEod after : " + (new Date()).getTime());	
	
	System.out.println(".::JSP - per processDP&LeaveApplication before : " + (new Date()).getTime());	
	PstDpApplication objPstDpApplication = new PstDpApplication();
	PstLeaveApplication objPstLeaveApplication = new PstLeaveApplication();  
	if(empSchedulePrevUpdate.getOID() == 0)
	{
		long generateDpResult = objPstDpApplication.generateDpApplication(empSchedule);  
		long generateLeaveResult = objPstLeaveApplication.generateLeaveApplication(empSchedule);     
	}
	else
	{
		long updatedResult = objPstDpApplication.updateDpApplication(empSchedule);  		
		long generateLeaveResult = objPstLeaveApplication.updateLeaveApplication(empSchedule);  		
	}
	System.out.println(".::JSP - per processDP&LeaveApplication after : " + (new Date()).getTime());     	
} 

if(iCommand==Command.DELETE)
{
	// set dp and/or leave application document during this schedule to 'Invalid' status
	int iDpAppDocUpdatedCount = PstDpApplication.setDpApplicationToInvalid(empSchedulePrevUpdate);
	int iLeaveAppDocUpdatedCount = PstLeaveApplication.setLeaveApplicationToInvalid(empSchedulePrevUpdate);
%>
	<jsp:forward page="empschedule_list.jsp">
	<jsp:param name="start" value="<%=start%>" />
	<jsp:param name="hidden_emp_schedule_id" value="<%=empSchedule.getOID()%>" />
	<jsp:param name="iCommand" value="<%=Command.DELETE%>" />        
	<jsp:param name="prevCommand" value="<%=Command.ADD%>" />
	</jsp:forward>
<%
}

if((iCommand==Command.SAVE)&&(frmEmpSchedule.errorSize()<1))
{
	iCommand = Command.EDIT;
}

Date dtPeriodNow = new Date();
Date dtPeriodStart = new Date();
Date dtPeriodEnd = new Date();
long periodId = 0;

Period period = new Period();
Employee employee = new Employee();

if(iCommand == Command.EDIT)
{
	gotoPeriod = oidPeriod;	
}

/**
 * proses DP, AL dan LL untuk data informasi jumlah DP, AL dan LL ke user sebelum memilih schedule itu
 * @created by Gadnyana 
 */
Vector list = PstScheduleSymbol.getScheduleDPALLL();
Vector listDp = DPMontly.getDpStock();
Vector listAl = AnnualLeaveMontly.getAnnualLeaveStock();
Vector listll = SessLongLeave.getLongLeaveStock();

/**
 * Command jika terjadi event onChange pada combobox schedule Period
 * data periode(start dan end) diambil dari object periode berdasar pilihan di combobox schedule period
 */
if (iCommand == Command.GOTO)
{
	if (gotoPeriod != -1)
	{
		period = PstPeriod.fetchExc(gotoPeriod);
		dtPeriodStart = period.getStartDate();
		dtPeriodEnd = period.getEndDate();
		periodId = period.getOID();
		iCommand = Command.ADD;
	}
}
else

	/**
	 * Command jika terjadi event Add Schedule
	 * data periode(start dan end) diambil dari object date dari tanggal sistem komputer
	 */
	if (iCommand == Command.ADD) 
	{
		dtPeriodStart = new Date(dtPeriodNow.getYear(), dtPeriodNow.getMonth()-1, 21);
		dtPeriodEnd = new Date(dtPeriodNow.getYear(), dtPeriodNow.getMonth(), 20);
	}
	else
		/**
		 * Command jika terjadi event Save Schedule
		 * data periode(start dan end) diambil dari object periode berdasar pilihan di combobox schedule period
		 */
		if (iCommand == Command.SAVE) 
		{
			if (oidEmployee != 0) 
			{
				period = PstPeriod.fetchExc(gotoPeriod);
				dtPeriodStart = period.getStartDate();
				dtPeriodEnd = period.getEndDate();
				periodId = period.getOID();
				iCommand = Command.ADD;
				employee = PstEmployee.fetchExc(oidEmployee);
			}
			else 
			{
				period = PstPeriod.fetchExc(oidPeriod);
				dtPeriodStart = period.getStartDate();
				dtPeriodEnd = period.getEndDate();
				periodId = period.getOID();
			}
		}


/**
 * Command jika terjadi event selain event2 di atas
 * data periode(start dan end) diambil dari object periode berdasar pilihan di combobox schedule period
 */
else
{
	period = PstPeriod.fetchExc(oidPeriod);
	dtPeriodStart = period.getStartDate();
	dtPeriodEnd = period.getEndDate();
	employee = PstEmployee.fetchExc(oidEmployee);
}


// pengecekan apakah antara employee yg schedulenya diubah dengan pengubahnya berada dalam department yg sama
//isSecretaryLogin = isSecretaryLogin && (employee.getDepartmentId()==departmentOid ? true : false);


// vector yang menampung daftar simbol-simbol schedule yg ada
Vector listScheduleSymbol = PstScheduleSymbol.list(0,0,"",orderScheduleSymbol);

// cari department_id dari user yang login
AppUser appuser = userSession.getAppUser();
Employee emp = new Employee();
if (appuser.getEmployeeId() > 0) {
	emp = PstEmployee.fetchExc(appuser.getEmployeeId());
}

    // calendar loop/ max day
    int yearSt = dtPeriodStart.getYear() + 1900;
    int monthSt = dtPeriodStart.getMonth();
    int dateSt = dtPeriodStart.getDate();
    GregorianCalendar gc = new GregorianCalendar(yearSt, monthSt, dateSt);
    int maxLoop = gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

%>
<!-- End of Jsp Block -->
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" -->
<title>HARISMA - Working Schedule</title>
<script language="JavaScript">
function cmdEdit(oid){
	document.frm_empschedule.command.value="<%=Command.EDIT%>";
	document.frm_empschedule.hidden_emp_schedule_id.value = oid;
	document.frm_empschedule.action="empschedule_edit.jsp";
	document.frm_empschedule.submit();
}

function cmdCancel(){
	document.frm_empschedule.command.value="<%=Command.CANCEL%>";
	document.frm_empschedule.action="empschedule_edit.jsp";
	document.frm_empschedule.submit();
}

function cmdSave(){
	document.frm_empschedule.command.value="<%=Command.SAVE%>";
	document.frm_empschedule.action="empschedule_edit.jsp";
	document.frm_empschedule.submit();
}

function cmdAsk(oid){
	document.frm_empschedule.command.value="<%=Command.ASK%>";
	document.frm_empschedule.action="empschedule_edit.jsp";
	document.frm_empschedule.submit();
}

function cmdConfirmDelete(oid){
	document.frm_empschedule.command.value="<%=Command.DELETE%>";
	document.frm_empschedule.action="empschedule_edit.jsp";
	document.frm_empschedule.submit();
}

function cmdBack(){
	document.frm_empschedule.command.value="<%=Command.BACK%>";
	document.frm_empschedule.action="empschedule_list.jsp";
	document.frm_empschedule.submit();
}

function periodChange() {
	document.frm_empschedule.command.value = "<%=Command.GOTO%>";
	document.frm_empschedule.hidden_goto_period.value = document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_PERIOD_ID]%>.value;
	document.frm_empschedule.hidden_goto_employee.value = document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID]%>.value;
	document.frm_empschedule.action = "empschedule_edit.jsp";
	document.frm_empschedule.submit();
}

function cmdSearchEmp(){
	emp_number = document.frm_empschedule.EMP_NUMBER.value;
	emp_fullname = document.frm_empschedule.EMP_FULLNAME.value;
	emp_department = document.frm_empschedule.EMP_DEPARTMENT.value;
	emp_period = document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_PERIOD_ID]%>.value;
	window.open("empsearch.jsp?emp_number=" + emp_number + "&emp_fullname=" + emp_fullname + "&emp_department=" + emp_department + "&emp_period=" + emp_period);
}

function cmdClearSearchEmp(){
	document.frm_empschedule.EMP_NUMBER.value = "";
	document.frm_empschedule.EMP_FULLNAME.value = "";
}

function cmdCopyPaste(oid) {
	document.frm_empschedule.hidden_emp_schedule_id.value = oid;
	document.frm_empschedule.command.value = "<%=Command.EDIT%>";
	document.frm_empschedule.hidden_goto_period.value = document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_PERIOD_ID]%>.value;
	document.frm_empschedule.hidden_goto_employee.value = 0;
	document.frm_empschedule.action = "empschedule_edit_copy.jsp";
	document.frm_empschedule.submit();
}

// -- start utk date 
function month(num)
{
	switch(num)
	{
		case 0:
			return 'January';
		case 1:
			return 'February';
		case 2:
			return 'March';
		case 3:
			return 'April';
		case 4:
			return 'May';
		case 5:
			return 'June';
		case 6:
			return 'July';
		case 7:
			return 'August';
		case 8:
			return 'September';
		case 9:
			return 'October';
		case 10:
			return 'November';
		case 11:
			return 'December';
	}
}
// -- end utk date 



/**
 * proses cek dp,al,ll in used
 * Algoritma :
 *   -
 *   -
 * 	 -
 * created by Gadnyana
 */
function inUsed(oid)
{
<%
if(isSecretaryLogin || isHRDLogin)
{
%>
    var qtyUsed = 0;
    <%	
	// awal iterasi sesuai dengan index field2 schedule di PstEmpSchdeule
	int stIterate = 3;	
	Date nowDate = new Date();
	for(int i=0; i<maxLoop; i++)
	{	
		// jika edit, maka pengambilan Dp, AL atau LL pada tanggal yang kurang dari hari ini, "tidak diperhitungkan"
		if( oidEmpSchedule!=0 )  
		{
			if((i+1) >= nowDate.getDate())
			{
	%>
	    var oid_<%=stIterate%> = document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[stIterate]%>.value;
        if(oid_<%=stIterate%>)
		{
            oid_<%=stIterate%> = oid_<%=stIterate%>.substring(0,18);
            if(oid_<%=stIterate%>==oid)
			{
                qtyUsed = parseInt(qtyUsed) + 1;
            }
        }
	<%
			}
		}
		
		// jika add, maka pengambilan DP, AL atau LL dimulai dari tanggal awal sampai akhir periode tersebut
		else
		{
	%>
	    var oid_<%=stIterate%> = document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[stIterate]%>.value;
        if(oid_<%=stIterate%>)
		{
            oid_<%=stIterate%> = oid_<%=stIterate%>.substring(0,18);
            if(oid_<%=stIterate%>==oid)
			{
                qtyUsed = parseInt(qtyUsed) + 1;
            }
        }		
	<%
		}
    	stIterate++;		
    }
	%>
    return parseInt(qtyUsed); 
<%
}
%>	
}


/**
 * proses cek dp
 * Algoritma :
 *   -
 *   -
 * 	 -
 * created by Gadnyana
 */
function cekDayoffPayment(oiddp, objForm, defaultValue)
{
<%
if(isSecretaryLogin || isHRDLogin)
{
%>
	var changeDefaultSchld = false;
	
    var oidemp = document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID]%>.value;
    <%
	if(listDp!=null && listDp.size()>0){
	%>

        var bool = new Boolean();
        bool = false;
        switch(oidemp)
		{
		<%
            for(int k=0;k<listDp.size();k++)
			{
                Vector vt = (Vector)listDp.get(k);
                long oidemp  = Long.parseLong((String)vt.get(0));
                int qtydp = Integer.parseInt((String)vt.get(1));  
		%>
                case '<%=oidemp%>':
                    bool = true;
                    var qty = inUsed(oiddp);
                    if(parseInt(qty) > <%=qtydp%>)
					{					
						var advanceDpResidue = parseInt(qty) - <%=qtydp%>;
						if( parseInt(advanceDpResidue) > <%=maxToleranDpQuantity%> )
						{
							alert("Your DP' stock balance is already 0\nAll of your advance DP have already used\nYou can't take DP anymore");													
							objForm.value = defaultValue;
							changeDefaultSchld = true;
						}
						else
						{
							alert("Your DP' stock balance is already 0\nYou still have " + (<%=maxToleranDpQuantity%> - advanceDpResidue + 1) + " advance DP\nThat will be " + (<%=maxToleranDpQuantity%> - advanceDpResidue) + " if you take one");							
						}						
                    }
					else
					{
						qty = <%=qtydp%> - parseInt(qty);						
						alert("Your DP' stock balance is " + (qty+1) + "\nThat will be " + qty + " if you take one");						
                    }
                break;
            <%
			}
			%>
        }

		if(bool==false)
		{		
            var qty = inUsed(oiddp);			
			var advanceDpResidue = parseInt(qty);
			if( parseInt(advanceDpResidue) > <%=maxToleranDpQuantity%> )
			{
				alert("Your DP' stock balance is already 0\nAll of your advance DP have already used\nYou can't take DP anymore");
				objForm.value = defaultValue;
				changeDefaultSchld = true;
			}
			else
			{
				alert("Your DP' stock balance is already 0\nYou still have " + (<%=maxToleranDpQuantity%> - advanceDpResidue + 1) + " advance DP\nThat will be " + (<%=maxToleranDpQuantity%> - advanceDpResidue) + " if you take one");
			}			
        }
    <%
	}
	else
	{
	%>
		var qty = inUsed(oiddp);			
		var advanceDpResidue = parseInt(qty);
		if( parseInt(advanceDpResidue) > <%=maxToleranDpQuantity%> )
		{
			alert("Your DP' stock balance is already 0\nAll of your advance DP have already used\nYou can't take DP anymore");
			objForm.value = defaultValue;
			changeDefaultSchld = true;
		}
		else
		{
			alert("Your DP' stock balance is already 0\nYou still have " + (<%=maxToleranDpQuantity%> - advanceDpResidue + 1) + " advance DP\nThat will be " + (<%=maxToleranDpQuantity%> - advanceDpResidue) + " if you take one");
		}					
    <%
	}
	%>

	return changeDefaultSchld;
<%
}
%>		
}

/**
 * proses cek al(annual leave)
 * Algoritma :
 *   -
 *   -
 * 	 -
 * created by Gadnyana
 */
function cekAnnualLeave(oidal)
{
<%
if(isSecretaryLogin || isHRDLogin)
{
%>
    var oidemp = document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID]%>.value;
    <%
	if(listAl!=null && listAl.size()>0)
	{
	%>
        var bool = new Boolean();
        bool = false;
        switch(oidemp)
		{
		<%
        for(int k=0;k<listAl.size();k++)
		{
            Vector vt = (Vector)listAl.get(k);
            long oidemp  = Long.parseLong((String)vt.get(0));
            int qty = Integer.parseInt((String)vt.get(1));
		%>
                    case '<%=oidemp%>':
                        bool = true;
                        var qty2 = inUsed(oidal);
                        qty2 = <%=qty%> - parseInt(qty2);
						alert("Your AL' stock balance is " + (qty2+1) + "\nThat will be " + qty2 + " if you take one");
                    break;
            <%
			}
			%>
        }
        if(bool==false)
		{
            var qty2 = inUsed(oidal);
            qty2 = parseInt(0) - parseInt(qty2);
			alert("Your AL' stock balance is " + (qty2+1) + "\nThat will be " + qty2 + " if you take one");			
        }
    <%
	}
	else
	{
	%>
        var qty2 = inUsed(oidal);
        qty2 = parseInt(0) - parseInt(qty2);
		alert("Your AL' stock balance is " + (qty2+1) + "\nThat will be " + qty2 + " if you take one");
    <%
	}
	%>
<%
}
%>	
}

/**
 * proses cek long leave
 * Algoritma :
 *   -
 *   -
 * 	 -
 * created by Gadnyana
 */
function cekLongLeave(oidll)
{
<%
if(isSecretaryLogin || isHRDLogin)
{
%>
    var oidemp = document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID]%>.value;   
    <%
	if(listll!=null && listll.size()>0)
	{
	%>
        var bool = new Boolean();
        bool = false;
        switch(oidemp){
		<%
        for(int k=0;k<listll.size();k++)
		{
            Vector vt = (Vector)listll.get(k);
            long oidemp  = Long.parseLong((String)vt.get(0));
            int qty = Integer.parseInt((String)vt.get(1));
		%>
                    case '<%=oidemp%>':
                        bool = true;
                        var qty2 = inUsed(oidll);
                        qty2 = <%=qty%> - parseInt(qty2);
						alert("Your LL' stock balance is " + (qty2+1) + "\nThat will be " + qty2 + " if you take one");
                    break;
            <%
			}
			%>
        }

        if(bool==false)
		{
            var qty2 = inUsed(oidll);
            qty2 = parseInt(0) - parseInt(qty2);
			alert("Your LL' stock balance is " + (qty2+1) + "\nThat will be " + qty2 + " if you take one");
        }
    <%
	}
	else
	{
	%>
        var qty2 = inUsed(oidll);
        qty2 = parseInt(0) - parseInt(qty2);
		alert("Your LL' stock balance is " + (qty2+1) + "\nThat will be " + qty2 + " if you take one");
    <%
	}
	%>
<%
}
%>
}

/**
 * utk ngatur muncul tidaknya schedule split shift
 * Algoritma :
 *   - Tiap-tiap object form yg dalam hal ini combobox schedule (document.frm_schedule.xxx) akan menjadi parameter
 *   - Nilai dari suatu combo box akan diambil dan di substring untuk mendapatkan category schedulenya (dalam hal ini
 *     masih terbatas dalam pemrosesan split shift)
 * 	 - Jika ternyata schedule yang terpilih adalah split shift maka 'second schedule' akan ditampilkan dan sebaliknya.
 * created by Edhy
 */
function changeSchedule(objForm, defaultValue, formNumber)
{	
<%
if(isSecretaryLogin || isHRDLogin)
{

// schedule kedua tergantung pada schedule I
if(isCheckFirstSchedule)
{
	if(checkUpdateScheduleByDate)
	{
%>
	
	var schld_value = objForm.value;
	var schld_cat = schld_value.substring(18,19);
	var def_schld_cat = defaultValue.substring(18,19);	
	var const_cat = "<%=PstScheduleCategory.CATEGORY_SPLIT_SHIFT%>";  // konstanta split shift yg diambil dari bean		
	var dp_cat = "<%=PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT%>";  // konstanta day off payment yg diambil dari bean					
	var al_cat = "<%=PstScheduleCategory.CATEGORY_ANNUAL_LEAVE%>";  // konstanta annual leave yg diambil dari bean					
	var ll_cat = "<%=PstScheduleCategory.CATEGORY_LONG_LEAVE%>";  // konstanta long leave yg diambil dari bean							
	
	var monthOfStartPeriod = "<%=dtPeriodStart.getMonth()%>";
	var monthOfCurrentDate = "<%=dtCurrentDate.getMonth()%>";
	var dateOfCurrentDate = "<%=dtCurrentDate.getDate()%>";		
	if( monthOfStartPeriod >= monthOfCurrentDate )
	{

		var maxDateUpdated = formNumber;
		if(def_schld_cat == dp_cat)
		{
			maxDateUpdated = maxDateUpdated - "<%=intCheckDPRoster%>";					
		}		

		if(def_schld_cat == al_cat || def_schld_cat == ll_cat)               
		{
			maxDateUpdated = maxDateUpdated - "<%=intCheckLeaveRoster%>";	   
		}

		if( parseInt(maxDateUpdated) < parseInt(dateOfCurrentDate) )
		{		
			if( parseInt(formNumber) < parseInt(dateOfCurrentDate) )			
			{
				alert("Cannot change schedule\nSelected schedule is out of date\nPlease contact HRD to change this schedule ...");			
			}
			else
			{
				var yearOfCurrentDate = "<%=dtCurrentDate.getYear()%>";   		
				var dtLatest = new Date(yearOfCurrentDate, monthOfCurrentDate, maxDateUpdated);
				var sLatestChangingDate = month(dtLatest.getMonth()) + " " +dtLatest.getDate() + ", " +(dtLatest.getYear()+1900);				 
				alert("Cannot change schedule\nLatest changing at " + sLatestChangingDate + "\nPlease contact HRD to change this schedule ...");
			}
			objForm.value = defaultValue;					
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
			
					case '<%=scheduleSymbol.getOID()%>':
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
		
			for(int i=startIterate; i<maxIterate; i++){
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
}



// schedule kedua tidak tergantung pada schedule 
else
{
	if(checkUpdateScheduleByDate)
	{
%>

	var schld_value = objForm.value;
	var schld_cat = schld_value.substring(18,19);		
	var def_schld_cat = defaultValue.substring(18,19);			
	var absence_cat = "<%=PstScheduleCategory.CATEGORY_ABSENCE%>";  // konstanta absence yg diambil dari bean
	var off_cat = "<%=PstScheduleCategory.CATEGORY_OFF%>";  // konstanta off yg diambil dari bean
	var dp_cat = "<%=PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT%>";  // konstanta day off payment yg diambil dari bean
	var al_cat = "<%=PstScheduleCategory.CATEGORY_ANNUAL_LEAVE%>";  // konstanta annual leave yg diambil dari bean				
	var ll_cat = "<%=PstScheduleCategory.CATEGORY_LONG_LEAVE%>";  // konstanta long leave yg diambil dari bean					
	
	var monthOfStartPeriod = "<%=dtPeriodStart.getMonth()%>";
	var monthOfCurrentDate = "<%=dtCurrentDate.getMonth()%>";
	var dateOfCurrentDate = "<%=dtCurrentDate.getDate()%>";		
	if( monthOfStartPeriod >= monthOfCurrentDate )
	{

		var maxDateUpdated = formNumber;
		if(def_schld_cat == dp_cat)
		{
			maxDateUpdated = maxDateUpdated - "<%=intCheckDPRoster%>";					
		}		

		if(def_schld_cat == al_cat || def_schld_cat == ll_cat)               
		{
			maxDateUpdated = maxDateUpdated - "<%=intCheckLeaveRoster%>";	   
		}

		if( parseInt(maxDateUpdated) < parseInt(dateOfCurrentDate) )
		{		
			if( parseInt(formNumber) < parseInt(dateOfCurrentDate) )			
			{
				alert("Cannot change schedule\nSelected schedule is out of date\nPlease contact HRD to change this schedule ...");			
			}
			else
			{
				var yearOfCurrentDate = "<%=dtCurrentDate.getYear()%>";   		
				var dtLatest = new Date(yearOfCurrentDate, monthOfCurrentDate, maxDateUpdated);
				var sLatestChangingDate = month(dtLatest.getMonth()) + " " +dtLatest.getDate() + ", " +(dtLatest.getYear()+1900);				 
				alert("Cannot change schedule\nLatest changing at " + sLatestChangingDate + "\nPlease contact HRD to change this schedule ...");
			}
			objForm.value = defaultValue;					
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
					case '<%=scheduleSymbol.getOID()%>':
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
			int startIterateNotCheck = 3; // awal iterasi
			int countSchldPerMonthNotCheck = 31; // jumlah schedule dalam satu bulan
			int maxIterateNotCheck = startIterateNotCheck + countSchldPerMonthNotCheck;
		
			for(int i=startIterateNotCheck; i<maxIterateNotCheck; i++)
			{
			%>
		
			if( objForm==document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[i]%> )
			{
				if(schld_cat==absence_cat || schld_cat==off_cat || schld_cat==dp_cat || schld_cat==al_cat || schld_cat==ll_cat)
				{
					if( changeFrmByDp )
					{
						if(def_schld_cat==absence_cat || def_schld_cat==off_cat || def_schld_cat==dp_cat || def_schld_cat==al_cat || def_schld_cat==ll_cat)
						{
							document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[i+countSchldPerMonthNotCheck]%>.style.visibility='hidden';
							document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[i+countSchldPerMonthNotCheck]%>.value='0';		
						}
						else
						{
							document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[i+countSchldPerMonthNotCheck]%>.style.visibility='';					
						}
					}
					else
					{
						document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[i+countSchldPerMonthNotCheck]%>.style.visibility='hidden';
						document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[i+countSchldPerMonthNotCheck]%>.value='0';							
					}
				}
				else
				{
					document.frm_empschedule.<%=FrmEmpSchedule.fieldNames[i+countSchldPerMonthNotCheck]%>.style.visibility='';
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
}
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
	if(isSecretaryLogin)
	{
	%>
		var schld_value = objForm.value;
		var schld_cat = schld_value.substring(18,19);		
		var def_schld_cat = defaultValue.substring(18,19);			
		var dp_cat = "<%=PstScheduleCategory.CATEGORY_DAYOFF_PAYMENT%>";  // konstanta day off payment yg diambil dari bean
		var al_cat = "<%=PstScheduleCategory.CATEGORY_ANNUAL_LEAVE%>";  // konstanta annual leave yg diambil dari bean				
		var ll_cat = "<%=PstScheduleCategory.CATEGORY_LONG_LEAVE%>";  // konstanta long leave yg diambil dari bean					

		var monthOfStartPeriod = "<%=dtPeriodStart.getMonth()%>";
		var monthOfCurrentDate = "<%=dtCurrentDate.getMonth()%>";
		var dateOfCurrentDate = "<%=dtCurrentDate.getDate()%>";		
		if( monthOfStartPeriod >= monthOfCurrentDate )
		{
			var maxDateUpdated = formNumber;
			if(def_schld_cat == dp_cat)
			{
				maxDateUpdated = maxDateUpdated - "<%=intCheckDPRoster%>";					
			}		
	
			if(def_schld_cat == al_cat || def_schld_cat == ll_cat)               
			{
				maxDateUpdated = maxDateUpdated - "<%=intCheckLeaveRoster%>";	   
			}
	
			if( parseInt(maxDateUpdated) < parseInt(dateOfCurrentDate) )
			{		
				if( parseInt(formNumber) < parseInt(dateOfCurrentDate) )			
				{
					alert("Cannot change schedule\nSelected schedule is out of date\nPlease contact HRD to change this schedule ...");			
				}
				else
				{
					var yearOfCurrentDate = "<%=dtCurrentDate.getYear()%>";   		
					var dtLatest = new Date(yearOfCurrentDate, monthOfCurrentDate, maxDateUpdated);
					var sLatestChangingDate = month(dtLatest.getMonth()) + " " +dtLatest.getDate() + ", " +(dtLatest.getYear()+1900);				 
					alert("Cannot change schedule\nLatest changing at " + sLatestChangingDate + "\nPlease contact HRD to change this schedule ...");
				}
				objForm.value = defaultValue;					
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


function MM_swapImgRestore() 
{ //v3.0
	var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() 
{ //v3.0
	var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) 
{ //v4.0
	var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
	d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
	if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
	for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
	if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() 
{ //v3.0
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
                                      <input type="hidden" name="start" value="<%=start%>">
                                      <input type="hidden" name="hidden_emp_schedule_id" value="<%=oidEmpSchedule%>">
                                      <input type="hidden" name="hidden_goto_period" value="<%=gotoPeriod%>">
                                      <input type="hidden" name="hidden_goto_employee" value="<%=gotoEmployee%>">
                                      <table width="100%" cellspacing="0" cellpadding="0" >
                                        <tr>
                                          <td colspan="3">
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                                <td>
                                                  <table width="100%" border="0" cellspacing="2" cellpadding="2">

													<%
													if (!((iCommand==Command.EDIT) || (iCommand==Command.ASK)))
													{
													%>
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
                                                        <%
														if ((iCommand==Command.EDIT) || (iCommand==Command.ASK))
														{
														%>
                                                        	<input type="hidden" name="<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_PERIOD_ID]%>" value="<%=oidPeriod%>"><%=period.getPeriod()%>
                                                        <%
														}
														else
														{
															Vector period_value = new Vector(1,1);
															Vector period_key = new Vector(1,1);
															period_key.add("-1");
															period_value.add("select...");
															String selectValuePeriod = String.valueOf(periodId);
															Vector listPeriod = new Vector(1,1);
															listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");
															for (int i = 0; i < listPeriod.size(); i++) {
																Period lsperiod = (Period) listPeriod.get(i);
																period_value.add(lsperiod.getPeriod());
																period_key.add(String.valueOf(lsperiod.getOID()));
															}
														%>
                                                        <%=ControlCombo.draw(FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_PERIOD_ID], null, selectValuePeriod, period_key, period_value, "onchange=\"javascript:periodChange();\"")%><%=frmEmpSchedule.getErrorMsg(FrmEmpSchedule.FRM_FIELD_PERIOD_ID)%>
                                                        <%
														}
                                                        %>
                                                      </td>
                                                    </tr>
                                                    <tr>
                                                      <td colspan="3"> </td>
                                                    </tr>

                                                    <%
													if (!((iCommand==Command.EDIT) || (iCommand==Command.ASK)))
													{
													%>
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
                                                        <%
														if ((iCommand==Command.EDIT) || (iCommand==Command.ASK))
														{
														%>
                                                        	<input type="hidden" name="<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=oidEmployee%>"><%=employee.getFullName()%>
                                                        <%
														}
														else
														{
														%>
                                                        <table cellpadding="1" cellspacing="1" border="0" bgcolor="#E0EDF0" width="453">
                                                          <tr>
                                                            <td valign="top">
                                                              <table cellpadding="1" cellspacing="1" border="0" width="384">
                                                                <tr>
                                                                  <td width="72"></td>
                                                                  <td width="246">
                                                                    <input type="hidden" name="<%=FrmEmpSchedule.fieldNames[FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID]%>" value="<%=gotoEmployee%>" class="formElemen">
                                                                  </td>
                                                                </tr>
                                                                <tr>
                                                                  <td valign="top" width="72">
                                                                    <div align="left"><%=dictionaryD.getWord(I_Dictionary.PAYROLL) %></div>
                                                                  </td>
                                                                  <td width="246">
                                                                    <input type="text" name="EMP_NUMBER"  value="" class="elemenForm" size="10">
                                                                    * <%=frmEmpSchedule.getErrorMsg(FrmEmpSchedule.FRM_FIELD_EMPLOYEE_ID)%>
                                                                  </td>
                                                                </tr>
                                                                <tr>
                                                                  <td valign="top" width="72">
                                                                    <div align="left">Name</div>
                                                                  </td>
                                                                  <td width="246">
                                                                    <input type="text" name="EMP_FULLNAME"  value="" class="elemenForm">
                                                                  </td>
                                                                </tr>
                                                                <tr>
                                                                  <td valign="top" width="72">
                                                                    <div align="left"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                                                                  </td>
                                                                  <td width="246">
                                                                    <%
																	Vector dept_value = new Vector(1,1);
																	Vector dept_key = new Vector(1,1);
																	Vector listDept = new Vector(1,1);
																	if(processDependOnUserDept)
																	{
																		if (emplx.getOID() > 0) 
																		{
																			if(isHRDLogin)
																			{																	
																				dept_value.add("0");
																				dept_key.add("select ...");                                                          
																				listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
																			}
																			else
																			{
																				listDept = PstDepartment.list(0, 0, "DEPARTMENT_ID = " + departmentOid, "");																	
																			}
																		}
																		else 
																		{
																			dept_value.add("0");
																			dept_key.add("select ...");                                                          
																			listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");
																		}
																	}
																	else
																	{
																		dept_value.add("0");
																		dept_key.add("select ...");                                                          																																			
																    	listDept = PstDepartment.list(0, 0, "", "DEPARTMENT");																		
																	} 

																	for (int i = 0; i < listDept.size(); i++) 
																	{
																		Department dept = (Department) listDept.get(i);
																		dept_key.add(dept.getDepartment());
																		dept_value.add(String.valueOf(dept.getOID()));
																	}
                                                                    %>
                                                                    <%= ControlCombo.draw("EMP_DEPARTMENT","formElemen",null, "", dept_value, dept_key) %>
                                                                  </td>
                                                                </tr>
                                                              </table>

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
                                                                  <td width="15"><a href="javascript:cmdClearSearchEmp()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnCancelOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnCancel.jpg" width="24" height="24" alt="Clear Search"></a></td>
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
                                                        <%
														}
														%>
                                                      </td>
                                                    </tr>

                                                    <tr>
                                                      <td colspan="3"> </td>
                                                    </tr>

													<%
													if (!((iCommand==Command.EDIT) || (iCommand==Command.ASK)))
													{
													%>
                                                    <tr>
                                                      <td colspan="3">
                                                        <li>&nbsp;Fill the Schedule
                                                      </td>
                                                    </tr>
													<%
													}
													%>

                                                    <tr>
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="7%" valign="top">
                                                        <div align="left">Schedule</div>
                                                      </td>
                                                      <td width="91%">
                                                        <%
                                                        if ((gotoPeriod == -1) || ((gotoPeriod == 0) && (iCommand == Command.ADD))) {
                                                        %>
                                                        	<div><font color="#000099">No period selected.</font></div>
                                                        <%
                                                        }
                                                        else
														{
                                                        %>
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                          <tr>
                                                            <td>
                                                              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                <tr>
                                                                  <td>
                                                                    <%
                                                                        // Name of each first combobox's schedule
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


                                                                        // Selected value of each first combobox's schedule
                                                                        String dSelect[] = new String[31];
                                                                        dSelect[0] = String.valueOf(empSchedule.getD1());
																		dSelect[1] = String.valueOf(empSchedule.getD2());
                                                                        dSelect[2] = String.valueOf(empSchedule.getD3());
																		dSelect[3] = String.valueOf(empSchedule.getD4());
                                                                        dSelect[4] = String.valueOf(empSchedule.getD5());
																		dSelect[5] = String.valueOf(empSchedule.getD6());
                                                                        dSelect[6] = String.valueOf(empSchedule.getD7());
																		dSelect[7] = String.valueOf(empSchedule.getD8());
                                                                        dSelect[8] = String.valueOf(empSchedule.getD9());
																		dSelect[9] = String.valueOf(empSchedule.getD10());
                                                                        dSelect[10] = String.valueOf(empSchedule.getD11());
																		dSelect[11] = String.valueOf(empSchedule.getD12());
                                                                        dSelect[12] = String.valueOf(empSchedule.getD13());
																		dSelect[13] = String.valueOf(empSchedule.getD14());
                                                                        dSelect[14] = String.valueOf(empSchedule.getD15());
																		dSelect[15] = String.valueOf(empSchedule.getD16());
                                                                        dSelect[16] = String.valueOf(empSchedule.getD17());
																		dSelect[17] = String.valueOf(empSchedule.getD18());
                                                                        dSelect[18] = String.valueOf(empSchedule.getD19());
																		dSelect[19] = String.valueOf(empSchedule.getD20());
                                                                        dSelect[20] = String.valueOf(empSchedule.getD21());
																		dSelect[21] = String.valueOf(empSchedule.getD22());
                                                                        dSelect[22] = String.valueOf(empSchedule.getD23());
																		dSelect[23] = String.valueOf(empSchedule.getD24());
                                                                        dSelect[24] = String.valueOf(empSchedule.getD25());
																		dSelect[25] = String.valueOf(empSchedule.getD26());
                                                                        dSelect[26] = String.valueOf(empSchedule.getD27());
																		dSelect[27] = String.valueOf(empSchedule.getD28());
                                                                        dSelect[28] = String.valueOf(empSchedule.getD29());
																		dSelect[29] = String.valueOf(empSchedule.getD30());
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
																		
                                                                        // Name of days in a week start from SUNDAY
                                                                        String dayname[] = new String[7];
                                                                        dayname[0] = "Sunday";
																		dayname[1] = "Monday";
																		dayname[2] = "Tuesday";
																		dayname[3] = "Wednesday";
                                                                        dayname[4] = "Thursday";
																		dayname[5] = "Friday";
																		dayname[6] = "Saturday";

																		// Name of months in a year start from JANUARY
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

																		// tanggal maksimum pada selected kalender
																		int nDayOfMonthStart = gcStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
																		// pada hari keberapa(mulai dari 0) di dalam week "dateStart" berada
																		int nDayOfWeekStart = gcStart.get(GregorianCalendar.DAY_OF_WEEK) - 1;

																		// manage tanggal akhir periode
                                                                        int yearEnd = dtPeriodEnd.getYear() + 1900;
                                                                        int monthEnd = dtPeriodEnd.getMonth();
                                                                        int dateEnd = dtPeriodEnd.getDate();

																		// mencari total hari yang harus dibuatkan schedulenya
																		int nDayTotal = nDayOfMonthStart;
                                                                        int nWeekInMonthTotal = ((nDayTotal/7) + 1) * 7;

                                                                        int i = 0;
                                                                        int j = 0;
                                                                        int dS = dateStart;
                                                                        int remainder = ( ((nDayTotal+nDayOfWeekStart)/7 + 1)*7 - (nDayTotal+nDayOfWeekStart) ) % 7;


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
																		Vector vectScheduleWithCategory = SessEmpSchedule.getMasterSchedule(vectScheduleFirstRules);
																		
																		Vector vectScheduleSecondRules = new Vector(1,1);
																		vectScheduleSecondRules.add(String.valueOf(PstScheduleCategory.CATEGORY_SPLIT_SHIFT));
																		vectScheduleSecondRules.add(String.valueOf(PstScheduleCategory.CATEGORY_EXTRA_ON_DUTY));																		
																		Vector vectSchldSecondWithCategory = SessEmpSchedule.getMasterSchedule(vectScheduleSecondRules);
                                                                    %>
                                                                  </td>
                                                                </tr>
                                                                <tr>
                                                                  <td>
                                                                    <table border="1" cellpadding="3" cellspacing="0" bgcolor="#B0DDF0">
                                                                      <tr>
                                                                        <td colspan="7" align="center">
                                                                          <b><%=period.getPeriod().toUpperCase()%></b>
                                                                        </td>
                                                                      </tr>

                                                                      <tr>
                                                                        <%
																		// pembuatan header calender
                                                                        for (int d = 0; d < 7; d++)
																		{
																			// Jika kelipatan 7 (sisa pembagian dengan 7 adalah 0) ==> SUNDAY (diberi warna pink)
                                                                            if ((d % 7) == 0)
																			{
                                                                                out.println("<td width=\"80\" align=\"center\" bgcolor=\"#FFEEEE\"><font color=\"red\">" + dayname[d] + "</font></td>");
                                                                            }

																			// Jika sisa pembagian dengan 7 adalah 6 ==> SATURDAY (diberi warna hijau)
                                                                            else if ((d % 7) == 6)
																			{
                                                                                out.println("<td width=\"80\" align=\"center\" bgcolor=\"#EEFFFA\"><font color=\"blue\">" + dayname[d] + "</font></td>");
                                                                            }

																			// Jika sisa pembagian dengan 7 adalah salah satu dari {1,2,3,4,5} ==> {MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY} (diberi warna ungu)
                                                                            else
																			{
                                                                                out.println("<td width=\"80\" align=\"center\" bgcolor=\"#DDDDFF\"><font color=\"black\">" + dayname[d] + "</font></td>");
                                                                            }
                                                                        }
                                                                        %>
                                                                      </tr>

                                                                      <tr>
                                                                        <%
																		// edited by Edhy
																		// ngeset jumlah tanggal dibandingkan dengan kelipatan (minggu) sehingga iterasi menjadi kelipatan 7
																		int iterate = nDayOfMonthStart + nDayOfWeekStart;
																		if((iterate > 28) && (iterate < 35))
																		{
																			iterate = 35;
																		}

																		if(iterate > 35)
																		{
																			iterate = 42;
																		}


																		// START LOOPING SELAMA NILAI iterate
																		for (i = 0; i < iterate; i++)
																		{

																			// START PROSES JIKA NILAI i ADALAH KELIPATAN 7 ==> SUNDAY
                                                                            if ((i % 7) == 0)
																			{
                                                                                out.println("<td bgcolor=\"#FFEEEE\">");
																				if((i > nDayOfWeekStart + dateStart - 1 - 1) && (dS <= nDayOfMonthStart ))
                                                                                //if (i > nDayOfWeekStart + dateStart - 1 - 1)
																				{
                                                                                    out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
                                                                                    out.println("<td valign=\"top\" align=\"left\"><font color=\"red\"><b>" + dS + "</b></font></td>");
                                                                                    out.println("<td align=\"right\">");

																					// first schedule
                                                                                    Vector scd_value = new Vector(1,1);
                                                                                    Vector scd_key = new Vector(1,1);
                                                                                    scd_value.add("0");
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
                                                                        		    out.println(ControlCombo.draw(dField[dS - 1], null, dSelect[dS - 1], scd_value, scd_key, "onChange=\"javascript:changeSchedule(this,'"+(dSelect[dS - 1])+"','"+dS+"')\""));
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

                                                                                    out.println("</td>");
                                                                                    out.println("</tr></table>");
                                                                                    dS++;
                                                                                }
                                                                                else
																				{
                                                                                    out.println("&nbsp;");
                                                                                }
                                                                                out.println("</td>");

																			}
																			// END PROSES JIKA NILAI i ADALAH KELIPATAN 7 ==> SUNDAY



																			// START PROSES JIKA SISA PEMBAGIAN i DENGAN 7 ADALAH 6 ==> SATURDAY
                                                                            else if ((i % 7) == 6)
																			{
                                                                                out.println("<td bgcolor=\"#EEFFFA\">");
                                                                                if ( (i > nDayOfWeekStart + dateStart - 1 - 1) && (dS <= nDayOfMonthStart ) )
																				//if (i > nDayOfWeekStart + dateStart - 1 - 1)
																				{
                                                                                    out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
                                                                                    out.println("<td valign=\"top\" align=\"left\"><font color=\"blue\"><b>" + dS + "</b></font></td>");
                                                                                    out.println("<td align=\"right\">");

																					// first schedule
                                                                                    Vector scd_value = new Vector(1,1);
                                                                                    Vector scd_key = new Vector(1,1);
                                                                                    scd_value.add("0");
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
                                                                        		    out.println(ControlCombo.draw(dField[dS - 1], null, dSelect[dS - 1], scd_value, scd_key, "onChange=\"javascript:changeSchedule(this,'"+(dSelect[dS - 1])+"','"+dS+"')\""));
																					// combo second schedule
																					if(dSelect2nd[dS - 1].equals("0"))
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

                                                                                    out.println("</td>");
                                                                                    out.println("</tr></table>");
                                                                                    dS++;
                                                                                }
                                                                                else
																				{
                                                                                    out.println("&nbsp;");
                                                                                }
                                                                                out.println("</td>");

                                                                            }
																			// END PROSES JIKA SISA PEMBAGIAN i DENGAN 7 ADALAH 6 ==> SATURDAY


																			// START PROSES JIKA SISA PEMBAGIAN i DENGAN 7 ADALAH salah satu dari {1,2,3,4,5} ==> {MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY}
                                                                            else
																			{
                                                                                out.println("<td bgcolor=\"#FFFFFF\">");
																				if((i > nDayOfWeekStart + dateStart - 1 - 1) && (dS <= nDayOfMonthStart ))
                                                                                //if (i > nDayOfWeekStart + dateStart - 1 - 1 )
																				{
                                                                                    out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");
                                                                                    out.println("<td valign=\"top\" align=\"left\"><font color=\"black\"><b>" + dS + "</b></font></td>");
                                                                                    out.println("<td align=\"right\">");

																					// first schedule
                                                                                    Vector scd_value = new Vector(1,1);
                                                                                    Vector scd_key = new Vector(1,1);
                                                                                    scd_value.add("0");
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
																						for (int ls = 0; ls < maxSchedule; ls++) {
																							Vector vectResult = (Vector)vectSchldSecondWithCategory.get(ls);
																							ScheduleSymbol scd = (ScheduleSymbol) vectResult.get(0);
																							ScheduleCategory scdCat = (ScheduleCategory) vectResult.get(1);

																							scd_2nd_key.add(scd.getSymbol());
																							scd_2nd_value.add(String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType()));

																							if(dSelect2nd[dS-1].equals(String.valueOf(scd.getOID()))){
																								dSelect2nd[dS-1] = String.valueOf(scd.getOID()) + String.valueOf(scdCat.getCategoryType());																								
																							}
																						}
																					}


                                                                        			out.println(ControlCombo.draw(dField[dS - 1], null, dSelect[dS - 1], scd_value, scd_key, "onChange=\"javascript:changeSchedule(this,'"+(dSelect[dS - 1])+"','"+dS+"')\""));

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

                                                                                    out.println("</td>");
                                                                                    out.println("</tr></table>");
                                                                                    dS++;
                                                                                }
                                                                                else
																				{
                                                                                    out.println("&nbsp;");
                                                                                }
                                                                                out.println("</td>");
                                                                            }
																			// END PROSES JIKA SISA PEMBAGIAN i DENGAN 7 ADALAH salah satu dari {1,2,3,4,5} ==> {MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY}



                                                                            if ((i + 1) % 7 == 0)
																			{
                                                                                out.println("<tr>");
                                                                            }

                                                                        }
																		// END LOOPING SELAMA NILAI iterate
                                                                        %>
                                                                      </tr>

                                                                      <%
																	  if (iCommand==Command.EDIT)
																	  {
																	  %>
                                                                      <tr>
                                                                        <td colspan="7"><a href="javascript:cmdCopyPaste('<%=oidEmpSchedule%>');">Create</a>
                                                                          new
                                                                          Working
                                                                          Schedule
                                                                          based
                                                                          on this
                                                                          working
                                                                          schedule
                                                                        </td>
                                                                      </tr>
                                                                      <%
																	  }
																	  %>
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
                                                    if ((gotoPeriod == -1) || ((gotoPeriod == 0) && (iCommand == Command.ADD)))
													{
                                                    %>
                                                    <tr>
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="7%" valign="top" align="right">&nbsp;</td>
                                                      <td width="91%">&nbsp;</td>
                                                    </tr>
                                                    <%
													}
													else
													{
													%>
                                                    <tr>
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="7%" valign="top" align="right">&nbsp;</td>
                                                      <td width="91%"> <%=drawList(listScheduleSymbol)%>
                                                      </td>
                                                    </tr>
                                                    <%
													}
													%>
													<%
													if(gotoPeriod > 0)
													{
													%>
                                                    <tr>
                                                      <td width="2%">&nbsp;</td>
                                                      <td width="7%" valign="top" align="right">&nbsp;</td>
                                                      <td width="91%"> 
                                                        <table width="100%" border="0">
                                                          <tr> 
                                                            <td class="errfont">* 
                                                              Insert / update 
                                                              process will take 
                                                              a few minutes <b>only 
                                                              if</b> employee 
                                                              presence data for 
                                                              this schedule already 
                                                              exist ...</td>
                                                          </tr>
                                                          <tr> 
                                                            <td>&nbsp;&nbsp;<span class="errfont">Another 
                                                              process automatically 
                                                              run with, are : 
                                                              </span></td>
                                                          </tr>
                                                          <tr> 
                                                            <td class="errfont">&nbsp;&nbsp;&nbsp;&nbsp;- 
                                                              <b> <i>Import and 
                                                              check presence data</i></b> 
                                                              appropriate to this 
                                                              schedule.</td>
                                                          </tr>
                                                          <tr> 
                                                            <td class="errfont">&nbsp;&nbsp;&nbsp;&nbsp;- 
                                                              <b> <i>Analyze absenteeism</i></b> 
                                                              based on imported 
                                                              presence data.</td>
                                                          </tr>
                                                          <tr> 
                                                            <td class="errfont">&nbsp;&nbsp;&nbsp;&nbsp;- 
                                                              <b> <i>Analyze lateness</i></b> 
                                                              based on imported 
                                                              presence data.</td>
                                                          </tr>
                                                        </table>
                                                      </td>
                                                    </tr>
													<%
													}
													%>
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

														ctrLine.setCommandStyle("buttonlink");														
														ctrLine.setBackCaption("Back to List Schedule");														
														ctrLine.setConfirmDelCaption("Yes Delete Schedule");
														ctrLine.setDeleteCaption("Delete Schedule");
														ctrLine.setSaveCaption("Save Schedule");

														if (privDelete)
														{
															ctrLine.setConfirmDelCommand(sconDelCom);
															ctrLine.setDeleteCommand(scomDel);
															ctrLine.setEditCommand(scancel);
														}
														else
														{
															ctrLine.setConfirmDelCaption("");
															ctrLine.setDeleteCaption("");
															ctrLine.setEditCaption("");
														}

														if(privAdd == false && privUpdate == false)
														{
															ctrLine.setSaveCaption("");
														}

														if (privAdd == false)
														{
															ctrLine.setAddCaption("");
														}
								
														if(!(isSecretaryLogin || isHRDLogin))
														{
															ctrLine.setConfirmDelCaption("");
															ctrLine.setDeleteCaption("");
															ctrLine.setEditCaption("");
															ctrLine.setSaveCaption("");															
															ctrLine.setAddCaption("");																														
														}
																
                                                        if (!((gotoPeriod == -1) || ((gotoPeriod == 0) && (iCommand == Command.ADD))))
														{
                                                        	out.println(ctrLine.drawImage(iCommand, iErrCode, errMsg));
														}
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
