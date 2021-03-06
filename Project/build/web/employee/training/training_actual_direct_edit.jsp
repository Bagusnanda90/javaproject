<%-- 
    Document   : training_actual_direct_edit
    Created on : Jan 16, 2009, 9:49:52 AM
    Author     : bayu
--%>


<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<%@ page language = "java" %>

<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.form.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.session.employee.*" %>

<%@ include file = "../../main/javainit.jsp" %>
<% 
    int appObjCodeGen = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_GENERAL_TRAINING); 
    int appObjCodeDept = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_TRAINING, AppObjInfo.OBJ_DEPARTMENTAL_TRAINING); 
    int appObjCode = 0; 
    
    // check training privilege (0 = none, 1 = general, 2 = departmental)
    int trainType = checkTrainingType(appObjCodeGen, appObjCodeDept, userSession);
    
    if(trainType == PRIV_GENERAL) {    
        appObjCode = appObjCodeGen;
    }
    else if(trainType == PRIV_DEPT) {  
        appObjCode = appObjCodeDept;
    }

    boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
    boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
    boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
    boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%@ include file = "../../main/checktraining.jsp" %>

<%!
    
    public String drawAttendanceList(Vector attendances) {        
        
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");

        ctrlist.addHeader("No","5%");
        ctrlist.addHeader("Payroll Number","15%");
        ctrlist.addHeader("Name","25%");
        ctrlist.addHeader("Department","20%");
        ctrlist.addHeader("Commencing Date","15%");
        ctrlist.addHeader("Hours Planned","15%");
        ctrlist.addHeader("","5%");
     
        Vector lstData = ctrlist.getData();

        ctrlist.setLinkRow(-1);
        ctrlist.setLinkPrefix("javascript:cmdEditAttendance('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        
        int number = 1;
        
        if(attendances != null && attendances.size() > 0) {
                                 
            for(int i = 0; i < attendances.size(); i++) {
                Vector rowx = new Vector();
                TrainingAttendancePlan attendancePlan = (TrainingAttendancePlan)attendances.get(i);
                Employee emp = new Employee();
                Department dept = new Department();
                
                try {
                    emp = PstEmployee.fetchExc(attendancePlan.getEmployeeId());
                }
                catch(Exception e) {
                    emp = new Employee();
                }

                try {
                    dept = PstDepartment.fetchExc(emp.getDepartmentId());
                }
                catch(Exception e) {
                    dept = new Department();
                }
                            
                rowx.add(String.valueOf(number++));
                rowx.add(emp.getEmployeeNum());
                rowx.add(emp.getFullName());
                rowx.add(dept.getDepartment());
                rowx.add((emp.getCommencingDate() == null) ? "-" : Formater.formatDate(emp.getCommencingDate(), "MMM dd, yyyy"));
                rowx.add(String.valueOf(SessTraining.getDurationString(attendancePlan.getDuration())));
                rowx.add("<div class=\"btn-small\" onclick=\"javascript:cmdAskAtt('"+attendancePlan.getOID()+"')\" style=\"color: #FFF\"><center>&times;</center></div>");

                lstData.add(rowx);               
                
            }           
        }
        
        return ctrlist.draw();        
    }
    
    public int getTrainingDuration(Date startTime, Date endTime) 
    {
	int result = 0;
            
	if(startTime!=null && endTime!=null ){
	
            if(startTime.compareTo(endTime) > 0)
                return 0;
                
            int startHour = startTime.getHours();
            int startMinute = startTime.getMinutes();

            int endHour = endTime.getHours();
            int endMinute = endTime.getMinutes();

            int hours = 0;
            int minutes = 0;

            hours = endHour - startHour;
            minutes = endMinute - startMinute;

            if(minutes < 0) {
                minutes = 60 + minutes;
                hours = hours - 1;
            }

            result = hours * 60 + minutes;         
	}
        		
	return result;
    }
            
%>

<!-- Jsp Block -->
<%
	int iCommand = FRMQueryString.requestCommand(request);
        int commandAtt = FRMQueryString.requestInt(request, "commandAtt");
        int prevCommand = FRMQueryString.requestInt(request, "prev_command");
	long oidTrainingPlan = FRMQueryString.requestLong(request, "training_plan_id");
        long oidTrainingActual = FRMQueryString.requestLong(request, "training_actual_id");
        long oidTrainingSchedule = FRMQueryString.requestLong(request, "training_schedule_id");
        long oidTrainType = FRMQueryString.requestLong(request, "training_attplan_id");
        
        String orderClause = "";
        String whereClause = "";
        
        int iErrCode = FRMMessage.ERR_NONE;
        //dedy.20160208
        int iErrCodeAtt = FRMMessage.ERR_NONE;
        //
	String errMsg = "";
        
        boolean errDisplay = true;
        int totalHours = 0;
        int totalAttendance = 0;   
        long defaultDeptOid = 0;
    
        ControlLine ctrLine = new ControlLine();
        String[] attdHours = null;
        String[] attdPersons = null;
     	
        
        if(iCommand == Command.EDIT || iCommand == Command.ADD)
            prevCommand = iCommand;

        
        CtrlTrainingActivityPlan ctrlPlan = new CtrlTrainingActivityPlan(request);
        iErrCode = ctrlPlan.actionPlan(iCommand, oidTrainingPlan);
        errMsg = ctrlPlan.getMessage();
        
        TrainingActivityPlan plan = ctrlPlan.getTrainingActivityPlan();
	FrmTrainingActivityPlan frmPlan = ctrlPlan.getForm();
	oidTrainingPlan = plan.getOID();
        TrainingSchedule schedule = new TrainingSchedule();
        
        if((iCommand == Command.SAVE) && frmPlan.getErrors().size()<=0) {
            
            // process schedule            
            if(iCommand == Command.SAVE) {
                schedule = new TrainingSchedule();
                FrmTrainingSchedule formSchedule = new FrmTrainingSchedule(request, schedule); 
                CtrlTrainingSchedule ctrlSchedule = new CtrlTrainingSchedule(request);

                formSchedule.requestEntityObject(schedule);
                Date timeStart = ControlDate.getTime(FrmTrainingSchedule.fieldNames[FrmTrainingSchedule.FRM_FIELD_START_TIME], request);
                Date timeEnd = ControlDate.getTime(FrmTrainingSchedule.fieldNames[FrmTrainingSchedule.FRM_FIELD_END_TIME], request);

                schedule.setTrainPlanId(oidTrainingPlan);
                schedule.setStartTime(timeStart);
                schedule.setEndTime(timeEnd);

                ctrlSchedule.action(iCommand, oidTrainingSchedule, schedule);
                oidTrainingSchedule = schedule.getOID(); 
            }
            
            // process actual
            TrainingActivityActual actual = new TrainingActivityActual();
            FrmTrainingActivityActual formActual = new FrmTrainingActivityActual(request, actual);
            CtrlTrainingActivityActual ctrlActual = new CtrlTrainingActivityActual(request);
            
            actual.setOID(oidTrainingActual);
            actual.setAtendees(totalAttendance);
            actual.setDate(schedule.getTrainDate());
            actual.setTrainEndDate(schedule.getTrainEndDate());
            actual.setTotalHour(schedule.getTotalHour());
            actual.setOrganizerID(plan.getOrganizerID());
            actual.setEndTime(schedule.getEndTime());
            actual.setRemark(plan.getRemark());
            actual.setScheduleId(oidTrainingSchedule);
            actual.setStartTime(schedule.getStartTime());
            actual.setTrainingActivityPlanId(oidTrainingPlan);
            actual.setTrainingId(plan.getTrainingId());
            actual.setTrainner(plan.getTrainer());
            actual.setTrainingTitle(plan.getTrainingTitle());
            
            try {
                actual.setVenue(PstTrainVenue.fetchExc(schedule.getTrainVenueId()).getVenueName());
            }
            catch(Exception e) {
                actual.setVenue("");
            }                        
            
            ctrlActual.action(iCommand, oidTrainingActual, actual, request);
            oidTrainingActual = actual.getOID();
            
        }
        
        if(iCommand == Command.BACK) {
            // back from popup attendance
           
        }
        
        if(iCommand == Command.GOTO) {
            // refresh combo department
            frmPlan = new FrmTrainingActivityPlan(request, plan);
            frmPlan.requestEntityObject(plan); 
            errDisplay = false;
        }
        
                
        // list schedule dan attendance
        Vector listSchedule = new Vector();
        Vector listAttendance = new Vector();
        //dedy.20160208
        CtrlTrainingAttendacePlan ctrlTrainAttPlan = new CtrlTrainingAttendacePlan(request);
        //
        iErrCodeAtt = ctrlTrainAttPlan.action(commandAtt, oidTrainType);
        
        // when data is saved or edited
        if (iCommand == Command.SAVE && frmPlan.errorSize() == 0 || 
            iCommand == Command.EDIT || iCommand == Command.BACK || iCommand == Command.ASK) {
            
            String where = "";
            String order = "";
        
            try {
                where = PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_PLAN_ID] + "=" + oidTrainingPlan;                               
                listSchedule = PstTrainingSchedule.list(0, 0, where, order);
            }
            catch(Exception e) {
                listSchedule = new Vector();
            }
            
            
            try {         
                whereClause = PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_TRAIN_PLAN_ID] + "=" + oidTrainingPlan;
                listAttendance = PstTrainingAttendancePlan.list(0, 0, whereClause, "");               
            }
            catch(Exception e) {
                listAttendance = new Vector();
            }
            
            if(iCommand == Command.BACK || iCommand == Command.SAVE) {                
                where = PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_PLAN_ID] + "=" + oidTrainingPlan;
                        
                // recount training hour based on schedule                
                schedule = listSchedule.size() > 0 ? (TrainingSchedule)listSchedule.firstElement() : new TrainingSchedule();
                totalHours += getTrainingDuration(schedule.getStartTime(), schedule.getEndTime());
                
                // recount training attendances              
                totalAttendance = listAttendance.size(); 
                
                System.out.println("TOTAL HOURS = " + totalHours + " AND TOTAL ATTENDANCES = " + totalAttendance);
                
                // update training plan table
                try {
                    plan = PstTrainingActivityPlan.fetchExc(oidTrainingPlan);
                    plan.setTotHoursPlan(totalHours);
                    plan.setTraineesPlan(totalAttendance);

                    PstTrainingActivityPlan.updateExc(plan);
                    TrainingActivityActual act;
                    
                    try {
                        act = PstTrainingActivityActual.fetchExc(oidTrainingActual);
                    }
                    catch(Exception e) {
                        act = new TrainingActivityActual();
                    }
                    
                    act.setAtendees(totalAttendance);                    
                    PstTrainingActivityActual.updateExc(act);
                }
                catch(Exception e) {}
            }
            else {
                totalHours = plan.getTotHoursPlan();
                totalAttendance = plan.getTraineesPlan();
            }
        
        }   
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Training Activity Actual</title>
<script language="JavaScript">	
          
	function cmdEdit(oid){ 
		document.frm_trainingplan.command.value="<%=Command.EDIT%>";
		document.frm_trainingplan.action="training_actual_direct_edit.jsp";
		document.frm_trainingplan.submit(); 
	} 

	function cmdSave(){
		document.frm_trainingplan.command.value="<%=Command.SAVE%>"; 
		document.frm_trainingplan.action="training_actual_direct_edit.jsp";
		document.frm_trainingplan.submit();
	}

	function cmdAsk(oid){
		document.frm_trainingplan.command.value="<%=Command.ASK%>"; 
		document.frm_trainingplan.action="training_actual_direct_edit.jsp";
		document.frm_trainingplan.submit();
	} 
        
        function cmdAskAtt(oid){
		document.frm_trainingplan.commandAtt.value="<%=Command.ASK%>"; 
                document.frm_trainingplan.training_attplan_id.value = oid;
		document.frm_trainingplan.action="training_actual_direct_edit.jsp";
		document.frm_trainingplan.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_trainingplan.command.value="<%=Command.DELETE%>";
		document.frm_trainingplan.action="training_actual_direct_edit.jsp"; 
		document.frm_trainingplan.submit();
	} 
        function cmdConfirmDeleteAtt(oid){
		document.frm_trainingplan.commandAtt.value="<%=Command.DELETE%>";
		document.frm_trainingplan.action="training_actual_direct_edit.jsp"; 
		document.frm_trainingplan.submit();
	}

	function cmdBack(){
		document.frm_trainingplan.command.value="<%=Command.BACK%>"; 
		document.frm_trainingplan.action="training_actual_list.jsp";
		document.frm_trainingplan.submit();
	}
	function cmdBackAtt(){
                document.frm_trainingplan.commandAtt.value="<%=Command.BACK%>";
		document.frm_trainingplan.action="training_actual_direct_edit.jsp";
		document.frm_trainingplan.submit();
	}
	function cmdChangeDept(){
		document.frm_trainingplan.command.value="<%=Command.GOTO%>";
		document.frm_trainingplan.action="training_actual_direct_edit.jsp";
		document.frm_trainingplan.submit();
	}
         
        function cmdChangeProg(){
		document.frm_trainingplan.command.value="<%=Command.GOTO%>";
		document.frm_trainingplan.action="training_actual_direct_edit.jsp";
		document.frm_trainingplan.submit();
	}
         
           
        // ------------- script training plan detail ------------------
          
        function cmdAddTrainer() {               
                window.open("<%=approot%>/employee/training/input_trainer_new.jsp", null, 
                            "height=550,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
        }
     
        function cmdEditAttendance(oidPlan, totalHours) {                             
                window.open("<%=approot%>/employee/training/input_attendance.jsp?plan=" + oidPlan + "&hours=" + totalHours, null, 
                            "height=550,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");  
        }
        
        
        //-------------- script navigation -------------------
        
        function cmdListFirst(){
		document.frm_employee.command.value="<%=Command.FIRST%>";
		document.frm_employee.action="training_actual_direct_edit.jsp";
		document.frm_employee.submit();
	}

	function cmdListPrev(){
		document.frm_employee.command.value="<%=Command.PREV%>";
		document.frm_employee.action="training_actual_direct_edit.jsp";
		document.frm_employee.submit();
	}

	function cmdListNext(){
		document.frm_employee.command.value="<%=Command.NEXT%>";
		document.frm_employee.action="training_actual_direct_edit.jsp";
		document.frm_employee.submit();
	}

	function cmdListLast(){
		document.frm_employee.command.value="<%=Command.LAST%>";
		document.frm_employee.action="training_actual_direct_edit.jsp";
		document.frm_employee.submit();
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
<link rel="stylesheet" href="../../styles/mchen-style.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable --> 
<style type="text/css">
    .tblStyle {border-collapse: collapse;font-size: 11px;}
    .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
    .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
    .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
    
    .btn-small {
        font-family: sans-serif;
        text-decoration: none;
        padding: 5px 7px; 
        background-color: #676767; color: #F5F5F5; 
        font-size: 11px; cursor: pointer;
        border-radius: 3px;
    }
    .btn-small:hover { background-color: #474747; color: #FFF;}
</style>
</head>
<body>
    <div class="header">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
        <%@include file="../../styletemplate/template_header.jsp" %>
        <%} else {%>
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
        </table>
    </div>
    <div class="content-main">
        <div class="content-title">
            <strong>Training</strong> <span id="garing">/</span> <strong>Training Actual</strong>
        </div>
        <div class="content">
            <form name="frm_trainingplan" method="post" action="">
                <input type="hidden" name="command" value="<%=iCommand+""%>">
                <input type="hidden" name="commandAtt" value="<%=commandAtt+""%>">
                <input type="hidden" name="prev_command" value="<%=prevCommand+""%>">
                <input type="hidden" name="training_plan_id" value="<%=oidTrainingPlan+""%>"> 
                <input type="hidden" name="training_actual_id" value="<%=oidTrainingActual+""%>">
                <input type="hidden" name="training_schedule_id" value="<%=oidTrainingSchedule+""%>">
                <input type="hidden" name="training_attplan_id" value="<%=oidTrainType+""%>">
                
                <input type="hidden" name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_TOT_HOURS_PLAN]%>" value="<%= totalHours+"" %>">
                <input type="hidden" name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_TRAINEES_PLAN]%>" value="<%= totalAttendance+"" %>">
                <div id="divform">
                    <div style="color: #CF5353; margin-bottom: 7px; font-size: 11px">*) entry required</div>
                    <div id="caption">Training Title</div>
                    <div id="divinput"><input type="text" size="70" name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_TRAINING_TITLE]%>" value="<%= plan.getTrainingTitle() %>" /></div>
                    
                    <div id="caption"><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></div>
                    <div id="divinput">
                        <% 
                            String where = "";

                            if(trainType == PRIV_DEPT) 
                                where = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + "=" + departmentOid;


                            Vector listDepartment = PstDepartment.list(0, 0, where, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                            Vector deptValue = new Vector(1,1); 
                            Vector deptKey = new Vector(1,1); 

                            if(trainType == PRIV_GENERAL) {
                                deptValue.add("0");
                                deptKey.add("-- GENERAL --");
                            }

                            for(int i=0; i<listDepartment.size(); i++){
                                Department department = (Department)listDepartment.get(i);

                                deptValue.add(String.valueOf(department.getOID()));
                                deptKey.add(department.getDepartment());

                                if(i == 0)
                                    defaultDeptOid = department.getOID();
                            }	

                            String selected_dept = String.valueOf(plan.getDepartmentId()); 
                        %>

                        <%=ControlCombo.draw(FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_DEPARTMENT_ID], null, selected_dept, deptValue, deptKey, "onChange=\"javascript:cmdChangeDept()\" ", "formElemen")%>
                    </div>
                    
                    <div id="caption">Training Program</div>
                    <div id="divinput">
                        <%  
                            if(plan.getDepartmentId() != 0) 
                                 defaultDeptOid = plan.getDepartmentId();
                            else
                                 defaultDeptOid = 0;

                            if(trainType == PRIV_DEPT)
                                defaultDeptOid = departmentOid;

                            Vector listTraining = PstTrainingDept.getTrainingByDept(defaultDeptOid);
                            Vector prog_value = new Vector(1,1);
                            Vector prog_key = new Vector(1,1); 

                            for (int i = 0; i < listTraining.size(); i++) {
                                Training trn = (Training) listTraining.get(i);

                                prog_value.add(String.valueOf(trn.getOID()));
                                prog_key.add(trn.getName());               

                            }

                            String selected_prog = String.valueOf(plan.getTrainingId());
                        %>

                        <%= ControlCombo.draw(FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_TRAINING_ID], null, selected_prog, prog_value, prog_key, "onkeydown=\"javascript:fnTrapKD()\" onChange=\"javascript:cmdChangeProg()\"","formElemen") %> 
                            * <%=(errDisplay == true) ? frmPlan.getErrorMsg(FrmTrainingActivityPlan.FRM_FIELD_TRAINING_ID)+"" : ""%>
                    </div>
                    
                    <div id="caption">Training Description</div>
                    <div id="divinput">
                        <%
                            Training trn = new Training();

                            try {
                                if(plan.getTrainingId() != 0)
                                    trn = PstTraining.fetchExc(plan.getTrainingId());

                            }
                            catch(Exception e) {
                                trn = new Training();
                            }

                      %>
                      <textarea readonly cols="30"><%= trn.getDescription()%></textarea>
                    </div>
                    
                    <div id="caption">Training Date</div>
                    <div id="divinput">
                        <%=ControlDate.drawDateWithStyle(FrmTrainingSchedule.fieldNames[FrmTrainingSchedule.FRM_FIELD_TRAIN_DATE], (schedule.getTrainDate() == null) ? new Date() : schedule.getTrainDate(), 1, -5, "formElemen", "")%>
                        To
                        <%=ControlDate.drawDateWithStyle(FrmTrainingSchedule.fieldNames[FrmTrainingSchedule.FRM_FIELD_TRAIN_END_DATE], (schedule.getTrainEndDate() == null) ? new Date() : schedule.getTrainEndDate(), 1, -5, "formElemen", "")%>
                    </div>
                    
                    <div id="caption">Training Time</div>
                    <div id="divinput">
                        <%=ControlDate.drawTime(FrmTrainingSchedule.fieldNames[FrmTrainingSchedule.FRM_FIELD_START_TIME], (schedule.getStartTime() == null) ? new Date() : schedule.getStartTime(), "formElemen")%> To 
                        <%=ControlDate.drawTime(FrmTrainingSchedule.fieldNames[FrmTrainingSchedule.FRM_FIELD_END_TIME], (schedule.getEndTime() == null) ? new Date() : schedule.getEndTime(), "formElemen")%> 
                    </div>
                    
                    <div id="caption">Training Organizer</div>
                    <div id="divinput">
                        <%                                                     
                            Vector listContact = PstContactList.listAll();
                            Vector contactIds = new Vector(1,1);
                            Vector contactNames = new Vector(1,1); 

                            for (int i = 0; i < listContact.size(); i++) {
                                ContactList contact = (ContactList) listContact.get(i);
                                contactIds.add(String.valueOf(contact.getOID()));
                                contactNames.add(contact.getCompName() + " / " + contact.getPersonName() + " " + contact.getPersonLastname() );                                                           
                            }

                            String selectedContact = String.valueOf(plan.getOrganizerID());;

                        %>

                        <%= ControlCombo.draw(FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_ORGANIZER_ID], null, selectedContact, contactIds, contactNames, "onkeydown=\"javascript:fnTrapKD()\"","formElemen") %> 
                        * <%=frmPlan.getErrorMsg(FrmTrainingActivityPlan.FRM_FIELD_ORGANIZER_ID)%>
                    </div>
                    
                    <div id="caption">Venue</div>
                    <div id="divinput">
                        <%
                            Vector venue_keys = new Vector(1, 1);
                            Vector venue_vals = new Vector(1, 1);

                            Vector venueList = PstTrainVenue.listAll();

                            for(int i=0; i<venueList.size(); i++) {
                                TrainVenue ven = (TrainVenue)venueList.get(i);

                                venue_keys.add(ven.getVenueName());
                                venue_vals.add(String.valueOf(ven.getOID()));
                            }
                       %>
                       <%=ControlCombo.draw(FrmTrainingSchedule.fieldNames[FrmTrainingSchedule.FRM_FIELD_TRAIN_VENUE_ID], null, String.valueOf(schedule.getTrainVenueId()), venue_vals, venue_keys)%>
                    </div>
                    
                    <div id="caption">Trainer</div>
                    <div id="divinput">
                        <input type="text" readonly name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_TRAINER]%>" value="<%=plan.getTrainer()%>" class="formElemen" size="30">
                        * <%=(errDisplay == true) ? frmPlan.getErrorMsg(FrmTrainingActivityPlan.FRM_FIELD_TRAINER) : ""%>

                        <a href="javascript:cmdAddTrainer()"> Add Trainer</a>
                    </div>
                    
                    <div id="caption">Number of Programs</div>
                    <div id="divinput">
                        <input type="text" name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_PROGRAMS_PLAN]%>" value="<%=plan.getProgramsPlan()==0?"":String.valueOf(plan.getProgramsPlan())%>" class="formElemen" size="10">
                        * <%=(errDisplay == true) ? frmPlan.getErrorMsg(FrmTrainingActivityPlan.FRM_FIELD_PROGRAMS_PLAN) : ""%>
                    </div>
                    
                    <div id="caption">Remark</div>
                    <div id="divinput">
                        <textarea name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_REMARK]%>" cols="30" class="formElemen" rows="3" wrap="virtual"><%=plan.getRemark()%></textarea>
                    </div>
                </div>
                    
                <table width="100%" cellspacing="2" cellpadding="0" >
                   
                <tr align="left">                                                                              
                    <td colspan="3"> 
                      

                          <% if (iCommand == Command.SAVE && frmPlan.errorSize() == 0 || 
                                 iCommand == Command.EDIT || iCommand == Command.BACK || iCommand == Command.ASK) { %>

                                  <%-- TRAINING ATTENDANCE START --%>
                                  <table width="100%" border="0">
                                  <tr>
                                      <td colspan="3">&nbsp;</td>
                                  </tr>
                                  <tr>
                                      <td colspan="3"><b>Training Attendees Data</b></td>
                                  </tr>
                                  <tr> 
                                      <td colspan="3"><%= drawAttendanceList(listAttendance)%></td>    
                                      <%
                                          // update history

                                          if(listAttendance != null && oidTrainingActual != 0) {

                                              where = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ACTIVITY_ACTUAL_ID] + "=" + oidTrainingActual;
                                              Vector listHist = PstTrainingHistory.list(0, 0, where, "");

                                              for(int j=0; j<listHist.size(); j++) {
                                                  TrainingHistory hist = (TrainingHistory)listHist.get(j);
                                                  long oid = PstTrainingHistory.deleteExc(hist.getOID());
                                              }


                                              attdHours = new String[listAttendance.size()];
                                              attdPersons = new String[listAttendance.size()];

                                              for(int i=0; i<listAttendance.size(); i++) {                                                                                                                                                                                                            
                                                  TrainingAttendancePlan train = (TrainingAttendancePlan)listAttendance.get(i);

                                                  attdHours[i] = train.getDuration()+"";
                                                  attdPersons[i] = train.getEmployeeId()+"";
                                              }
                                            //if (iCommand == Command.SAVE){
                                                PstTrainingHistory.insertTrainHistory(attdPersons, attdHours, plan.getTrainingId(), oidTrainingPlan, oidTrainingSchedule, oidTrainingActual, iCommand);                      
                                            //}
                                          }
                                      %>
                                  </tr>                                                       
                                  <tr>
                                      <td width="3%"><a href="javascript:cmdEditAttendance('<%=oidTrainingPlan%>','<%=totalHours%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10a','','<%=approot%>/images/BtnNewOn.jpg',1)" id="aSearch"><img name="Image10a" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add Schedule"></a></td>
                                      <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                      <td width="96%" class="command" nowrap><a href="javascript:cmdEditAttendance('<%=oidTrainingPlan%>','<%=totalHours%>')">Edit Attendances</a></td>                                                        
                                  </tr>                                                    
                                  </table>                                       
                                  <%-- TRAINING ATTENDANCE END --%>  

                          <% } %>    
                          <%
                            if (commandAtt == Command.ASK){
                            %>
                            <table>
                                <tr>
                                    <td valign="top">
                                        <div id="confirm">
                                            <strong>Are you sure to delete Attendance ?</strong> &nbsp;
                                            <button id="btn-confirm" onclick="javascript:cmdConfirmDeleteAtt('<%=oidTrainType%>')">Yes</button>
                                            &nbsp;<button id="btn-confirm" onclick="javascript:cmdBackAtt()">No</button>
                                        </div>
                                    </td>
                                </tr>
                            </table>

                            <%}%>
                          <%
                          ctrLine.setLocationImg(approot+"/images");
                          ctrLine.initDefault();
                          ctrLine.setTableWidth("90");
                          ctrLine.setCommandStyle("buttonlink");

                          String scomDel = "javascript:cmdAsk('"+oidTrainingPlan+"')";
                          String sconDelCom = "javascript:cmdConfirmDelete('"+oidTrainingPlan+"')";
                          String scancel = "javascript:cmdEdit('"+oidTrainingPlan+"')";

                          ctrLine.setAddCaption("");                                                
                          ctrLine.setBackCaption("Back to Search Actual");
                          ctrLine.setDeleteCaption("Delete Actual");
                          ctrLine.setConfirmDelCaption("Yes Delete Actual");
                          ctrLine.setSaveCaption("Save Actual");                                               


                          if (privDelete){
                              //ctrLine.setConfirmDelCommand(sconDelCom);
                              //ctrLine.setDeleteCommand(scomDel);
                             // ctrLine.setEditCommand(scancel);
                          }
                          else { 
                              //ctrLine.setConfirmDelCaption("");
                              //ctrLine.setDeleteCaption("");
                             // ctrLine.setEditCaption("");
                          }

                          if (privAdd == false  && privUpdate == false){
                              ctrLine.setSaveCaption("");
                          }                                           


                          if ((iCommand == Command.SAVE  && frmPlan.errorSize() == 0) || (iCommand == Command.DELETE)) {                                       
                                  /*ctrLine.setSaveCaption("");
                                  ctrLine.setDeleteCaption("");
                                  ctrLine.setBackCaption("");*/
                          }  

                          if (iCommand == Command.DELETE) {
                              //ctrLine.setAddCaption("Add New");
                          }
                          else {
                              //ctrLine.setAddCaption("");
                          }

                          if (iCommand == Command.GOTO) {                      
                                  iCommand = prevCommand;
                          }

                          if(iCommand == Command.BACK) {
                                  iCommand = Command.EDIT;
                          }

                          %>     

                          <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%> 
                    </td>
                  </tr>
                </table>
              </form>
        </div>
    </div>
    <div class="footer-page">
        <table>
            <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
            <tr>
                <td valign="bottom"><%@include file="../../footer.jsp" %></td>
            </tr>
            <%} else {%>
            <tr> 
                <td colspan="2" height="20" ><%@ include file = "../../main/footer.jsp" %></td>
            </tr>
            <%}%>
        </table>
    </div>
        

</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
