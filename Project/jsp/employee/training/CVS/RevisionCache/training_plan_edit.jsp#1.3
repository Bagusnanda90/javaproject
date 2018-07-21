
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<%-- 
    Document   : training_plan_edit
    Created on : Jan 16, 2009, 9:49:52 AM
    Author     : bayu
--%>

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
    public String drawScheduleList(Vector schedule, long oidPlan){        
        
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");

        ctrlist.addHeader("Train Date","20%");
        ctrlist.addHeader("End Date","15%");
        ctrlist.addHeader("Start Time","10%");
        ctrlist.addHeader("End Time","10%");
        ctrlist.addHeader("Total Hour","10%");
        ctrlist.addHeader("Train Venue","35");

        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();

        ctrlist.setLinkRow(0);
        ctrlist.setLinkPrefix("javascript:cmdEditSchedule('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();

        if(schedule != null && schedule.size() > 0) {
            for(int i = 0; i < schedule.size(); i++) {
                Vector rowx = new Vector();
                TrainingSchedule trainingSchedule = (TrainingSchedule)schedule.get(i);
                TrainVenue venue = new TrainVenue();

                try {
                    venue = PstTrainVenue.fetchExc(trainingSchedule.getTrainVenueId());
                }
                catch(Exception e) {
                    venue = new TrainVenue();
                }

                rowx.add(Formater.formatDate(trainingSchedule.getTrainDate(), "MMM d, yyyy"));
                rowx.add(Formater.formatDate(trainingSchedule.getTrainEndDate(), "MMM d, yyyy"));
                rowx.add(Formater.formatDate(trainingSchedule.getStartTime(), "HH:mm"));
                rowx.add(Formater.formatDate(trainingSchedule.getEndTime(), "HH:mm"));
                rowx.add(""+trainingSchedule.getTotalHour());
                rowx.add(venue.getVenueName());

                lstData.add(rowx);
                lstLinkData.add(oidPlan + "','" + trainingSchedule.getOID());
            }            
        }
        
        return ctrlist.draw();        
    }
    
    
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
     
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();

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
        int prevCommand = FRMQueryString.requestInt(request, "prev_command");
	long oidTrainingPlan = FRMQueryString.requestLong(request, "training_plan_id");
        int start = FRMQueryString.requestInt(request, "start");       
     	
        boolean errDisplay = true;
        int totalHours = 0;
        int totalAttendance = 0;               
        
        if(iCommand == Command.EDIT || iCommand == Command.ADD)
            prevCommand = iCommand;
        
	long defaultDeptOid = 0; 
	int iErrCode = FRMMessage.ERR_NONE;
	String errMsg = "";
	
        int recordToGet = 3;
        int vectSize = 0;
        String orderClause = "";
        String whereClause = "";
        
        CtrlTrainingActivityPlan ctrlTrainingActivityPlan = new CtrlTrainingActivityPlan(request);
	ControlLine ctrLine = new ControlLine();

	iErrCode = ctrlTrainingActivityPlan.actionPlan(iCommand, oidTrainingPlan);

	errMsg = ctrlTrainingActivityPlan.getMessage();
	FrmTrainingActivityPlan frmTrainingActivityPlan = ctrlTrainingActivityPlan.getForm();
	TrainingActivityPlan trainingActivityPlan = ctrlTrainingActivityPlan.getTrainingActivityPlan();
	oidTrainingPlan = trainingActivityPlan.getOID();   
        
        /* Added For HardRock */
        Vector listSchedule = new Vector();
        Vector listAttendance = new Vector();
        
        // when department combo box is refreshed
        if (iCommand == Command.GOTO) {
            frmTrainingActivityPlan = new FrmTrainingActivityPlan(request, trainingActivityPlan);
            frmTrainingActivityPlan.requestEntityObject(trainingActivityPlan); 
            errDisplay = false;
        }
        
        // when data is saved or edited
        if (iCommand == Command.SAVE && frmTrainingActivityPlan.errorSize() == 0 || 
            iCommand == Command.EDIT || iCommand == Command.BACK || iCommand == Command.ASK) {
            
            String where = "";
            String order = "";
        
            try {
                where = PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_PLAN_ID] +
                        "=" + oidTrainingPlan;
                
                order = PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_DATE] + "," +
                        PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_START_TIME];
                
                listSchedule = PstTrainingSchedule.list(0, 0, where, order);
            }
            catch(Exception e) {
                listSchedule = new Vector();
            }
            
            
            try {                
                //listAttendance = PstTrainingAttendancePlan.listEmployeeByPlan(oidTrainingPlan);
                whereClause = PstTrainingAttendancePlan.fieldNames[PstTrainingAttendancePlan.FLD_TRAIN_PLAN_ID] + "=" + oidTrainingPlan;
                listAttendance = PstTrainingAttendancePlan.list(0, 0, whereClause, "");
               
            }
            catch(Exception e) {
                listAttendance = new Vector();
            }
            
            if(iCommand == Command.BACK || iCommand == Command.SAVE) {                
                where = PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_PLAN_ID] + "=" + oidTrainingPlan;
                        
                // recount training hour based on schedule
                Vector listTmp = PstTrainingSchedule.list(0, 0, where, "");
                totalHours = 0;
                
                for(int i=0; i<listTmp.size(); i++) {
                    TrainingSchedule tmp = (TrainingSchedule)listTmp.get(i);
                    totalHours += getTrainingDuration(tmp.getStartTime(), tmp.getEndTime());
                }
               
                // recount training attendances              
                totalAttendance = PstTrainingAttendancePlan.getCount(where); 
                
                // update training plan table
                try {
                    TrainingActivityPlan plan = PstTrainingActivityPlan.fetchExc(oidTrainingPlan);
                    plan.setTotHoursPlan(totalHours);
                    plan.setTraineesPlan(totalAttendance);

                    PstTrainingActivityPlan.updateExc(plan);
                }
                catch(Exception e) {
                    System.out.println("EXCEPTION "+e.toString());
                }
            }
            else {
                totalHours = trainingActivityPlan.getTotHoursPlan();
                totalAttendance = trainingActivityPlan.getTraineesPlan();
            }
        
        }   
%>
<!-- End of Jsp Block -->
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Training Activity Plan</title>
<script language="JavaScript">	
    
        function cmdEdit(oid){ 
		document.frm_trainingplan.command.value="<%=Command.ADD%>";
		document.frm_trainingplan.action="training_plan_edit.jsp";
		document.frm_trainingplan.submit(); 
	} 


	function cmdEdit(oid){ 
		document.frm_trainingplan.command.value="<%=Command.EDIT%>";
		document.frm_trainingplan.action="training_plan_edit.jsp";
		document.frm_trainingplan.submit(); 
	} 

	function cmdSave(){
		document.frm_trainingplan.command.value="<%=Command.SAVE%>"; 
		document.frm_trainingplan.action="training_plan_edit.jsp";
		document.frm_trainingplan.submit();
	}

	function cmdAsk(oid){
		document.frm_trainingplan.command.value="<%=Command.ASK%>"; 
		document.frm_trainingplan.action="training_plan_edit.jsp";
		document.frm_trainingplan.submit();
	} 

	function cmdConfirmDelete(oid){
		document.frm_trainingplan.command.value="<%=Command.DELETE%>";
		document.frm_trainingplan.action="training_plan_edit.jsp"; 
		document.frm_trainingplan.submit();
	}  

	function cmdBack(){
		document.frm_trainingplan.command.value="<%=Command.BACK%>"; 
		document.frm_trainingplan.action="training_plan_list.jsp";
		document.frm_trainingplan.submit();
	}
	
	function cmdChangeDept(){
		document.frm_trainingplan.command.value="<%=Command.GOTO%>";
		document.frm_trainingplan.action="training_plan_edit.jsp";
		document.frm_trainingplan.submit();
	}
         
        function cmdChangeProg(){
		document.frm_trainingplan.command.value="<%=Command.GOTO%>";
		document.frm_trainingplan.action="training_plan_edit.jsp";
		document.frm_trainingplan.submit();
	}
         
           
        // ------------- script training plan detail ------------------
        
        function cmdAddSchedule(oidPlan) {               
                window.open("<%=approot%>/employee/training/input_schedule.jsp?plan=" + oidPlan, null, 
                            "height=550,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
        }
        
        function cmdEditSchedule(oidPlan, oidSchedule) {
                var newWin = window.open("<%=approot%>/employee/training/input_schedule.jsp?plan=" + oidPlan + "&schedule=" + oidSchedule, null, 
                            "height=550,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");       
                 newWin.focus();           
        }
        
        function cmdAddTrainer() {               
                var myWindow = window.open("<%=approot%>/employee/training/input_trainer.jsp", null, "height=550,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");
                myWindow.focus();
        }
     
        function cmdEditAttendance(oidPlan, totalHours) {
                <% 
                   whereClause = PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_PLAN_ID] + "=" + oidTrainingPlan; ;
                
                   if(PstTrainingSchedule.getCount(whereClause) == 0) { 
                %>
                    window.alert("Input schedule first!");
                    
                <% } else { %>
                
                    window.open("<%=approot%>/employee/training/input_attendance.jsp?plan=" + oidPlan + "&hours=" + totalHours, null, 
                                "height=550,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");       
                <% } %>
        }
        
        
        //-------------- script navigation -------------------
        
        function cmdListFirst(){
		document.frm_employee.command.value="<%=Command.FIRST%>";
		document.frm_employee.action="training_plan_edit.jsp";
		document.frm_employee.submit();
	}

	function cmdListPrev(){
		document.frm_employee.command.value="<%=Command.PREV%>";
		document.frm_employee.action="training_plan_edit.jsp";
		document.frm_employee.submit();
	}

	function cmdListNext(){
		document.frm_employee.command.value="<%=Command.NEXT%>";
		document.frm_employee.action="training_plan_edit.jsp";
		document.frm_employee.submit();
	}

	function cmdListLast(){
		document.frm_employee.command.value="<%=Command.LAST%>";
		document.frm_employee.action="training_plan_edit.jsp";
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
<link rel="stylesheet" href="../../styles/tab.css" type="text/css">
<!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
<!-- #EndEditable --> 
</head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
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
    <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> 
      <!-- #BeginEditable "menumain" --> 
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
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Training 
                  &gt; Training Plan<!-- #EndEditable --> </strong></font> 
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
                                  <td valign="top"> 
                                  
                                    <!-- #BeginEditable "content" --> 
                                    <form name="frm_trainingplan" method="post" action="">
                                      <input type="hidden" name="command" value="<%=iCommand%>">
                                      <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                      <input type="hidden" name="training_plan_id" value="<%=oidTrainingPlan%>">
                                      <input type="hidden" name="start" value="<%=start%>">                               
                                      <input type="hidden" name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_TOT_HOURS_PLAN]%>" value="<%=trainingActivityPlan.getTotHoursPlan()==0?"":String.valueOf(trainingActivityPlan.getTotHoursPlan())%>">
                                      <input type="hidden" name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_TRAINEES_PLAN]%>" value="<%=trainingActivityPlan.getTraineesPlan()==0?"":String.valueOf(trainingActivityPlan.getTraineesPlan())%>">
                                          
                                      <table width="100%" cellspacing="2" cellpadding="0" >
                                      <tr> 
                                          <td colspan="3">&nbsp; </td>
                                      </tr>
                                      <tr> 
                                          <td width="2%">&nbsp;</td>
                                          <td colspan="2" nowrap><b>TRAINING ACTIVITIES PLAN EDITOR</b></td>
                                      </tr>
                                      <tr>
                                          <td width="2%">&nbsp;</td>
                                          <td class="comment" width="13%" nowrap>&nbsp;</td>
                                          <td class="comment" width="85%">&nbsp;</td>
                                      </tr>
                                      <tr valign="top">
                                          <td width="2%">&nbsp;</td>
                                          <td>
                                          <table>                                           
                                             <tr>
                                                  <td width="2%">&nbsp;</td>
                                                  <td class="comment" width="13%" nowrap>&nbsp;</td>
                                                  <td class="comment" width="85%">*) entry required</td>
                                             </tr>
                                             <tr align="left"> 
                                                  <td width="2%"  valign="top">&nbsp;</td>
                                                  <td width="13%"  valign="top" nowrap><%=dictionaryD.getWord(I_Dictionary.DEPARTMENT) %></td>                                   
                                                  <td  width="85%"  valign="top"> 
                                                    <% 
                                                        String where = "";

                                                        if(trainType == PRIV_DEPT) 
                                                            where = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID] + "=" + departmentOid;

                                                        Vector listDepartment = PstDepartment.list(0, 0, where, PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]);
                                                        Vector deptValue = new Vector(1,1); 
                                                        Vector deptKey = new Vector(1,1); 
                                                        
                                                        if(trainType == PRIV_GENERAL){
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
                                                        									
                                                        String selected_dept = String.valueOf(trainingActivityPlan.getDepartmentId()); 
                                                    %>
                                                    
                                                    <%=ControlCombo.draw(FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_DEPARTMENT_ID], null, selected_dept, deptValue, deptKey, "onChange=\"javascript:cmdChangeDept()\" ", "formElemen")%>
                                                  </td>
                                             </tr>                                          
                                             <tr align="left"> 
                                                  <td width="2%"  valign="top">&nbsp;</td>
                                                  <td width="13%"  valign="top" nowrap>Training Program</td>                                           
                                                  <td  width="85%"  valign="top"> 
                                                    <%  
                                                        if(trainingActivityPlan.getDepartmentId() != 0) 
                                                             defaultDeptOid = trainingActivityPlan.getDepartmentId();
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

                                                        String selected_prog = String.valueOf(trainingActivityPlan.getTrainingId());
                                                    %>
                                                    
                                                    <%= ControlCombo.draw(FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_TRAINING_ID], null, selected_prog, prog_value, prog_key, "onkeydown=\"javascript:fnTrapKD()\" onChange=\"javascript:cmdChangeProg()\"","formElemen") %> 
                                                    * <%=(errDisplay == true) ? frmTrainingActivityPlan.getErrorMsg(FrmTrainingActivityPlan.FRM_FIELD_TRAINING_ID) : ""%>
                                                  </td>
                                            </tr>  
                                            <tr align="left"> 
                                                  <td width="2%"  valign="top">&nbsp;</td>
                                                  <td width="13%"  valign="top" nowrap>Training Description </td>                                           
                                                  <td  width="85%"  valign="top"> 
                                                      <%
                                                            Training trn = new Training();
                                                      
                                                            if(iCommand == Command.GOTO){
                                                                
                                                                try {
                                                                    trn =  PstTraining.fetchExc(trainingActivityPlan.getTrainingId());
                                                                }
                                                                catch(Exception e) {
                                                                    trn = new Training();
                                                                }
                                                            }
                                                      %>
                                                      <textarea readonly cols="30"><%= trn.getDescription()  %></textarea>
                                                  </td>
                                            </tr>
                                            <tr align="left"> 
                                                  <td width="2%"  valign="top">&nbsp;</td>
                                                  <td width="13%"  valign="top" nowrap>Training Organizer</td>                                           
                                                  <td  width="85%"  valign="top"> 
                                                    <%                                                     
                                                        Vector listContact = PstContactList.listAll();
                                                        Vector contactIds = new Vector(1,1);
                                                        Vector contactNames = new Vector(1,1); 

                                                        for (int i = 0; i < listContact.size(); i++) {
                                                             ContactList contact = (ContactList) listContact.get(i);

                                                            contactIds.add(String.valueOf(contact.getOID()));
                                                            contactNames.add(contact.getCompName() + " / " + contact.getPersonName() + " " + contact.getPersonLastname() );                                                           
                                                        }

                                                        String selectedContact = String.valueOf(trainingActivityPlan.getOrganizerID());
                                                    %>
                                                    
                                                    <%= ControlCombo.draw(FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_ORGANIZER_ID], null, selectedContact, contactIds, contactNames, "onkeydown=\"javascript:fnTrapKD()\"","formElemen") %> 
                                                    * <%=(errDisplay == true) ? frmTrainingActivityPlan.getErrorMsg(FrmTrainingActivityPlan.FRM_FIELD_ORGANIZER_ID) : ""%>
                                                  </td>
                                            </tr>  
                                            <tr align="left"> 
                                                  <td width="2%"  valign="top">&nbsp;</td>
                                                  <td width="13%"  valign="top" nowrap>Trainer</td>
                                                  <td  width="85%"  valign="top" nowrap> 
                                                    <input type="text" readonly name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_TRAINER]%>" value="<%=trainingActivityPlan.getTrainer()%>" class="formElemen" size="30">
                                                    * <%=(errDisplay == true) ? frmTrainingActivityPlan.getErrorMsg(FrmTrainingActivityPlan.FRM_FIELD_TRAINER) : ""%>
                                                                                                        
                                                    <a href="javascript:cmdAddTrainer()"> Add Trainer</a>
                                                  </td>
                                            </tr>
                                            <tr align="left"> 
                                                  <td width="2%"  valign="top">&nbsp;</td>
                                                  <td width="13%"  valign="top" nowrap>Number of Programs Plan</td>
                                                  <td  width="85%"  valign="top"> 
                                                    <input type="text" name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_PROGRAMS_PLAN]%>" value="<%=trainingActivityPlan.getProgramsPlan()==0?"":String.valueOf(trainingActivityPlan.getProgramsPlan())%>" class="formElemen" size="10">
                                                    * <%=(errDisplay == true) ? frmTrainingActivityPlan.getErrorMsg(FrmTrainingActivityPlan.FRM_FIELD_PROGRAMS_PLAN) : ""%>
                                                  </td>
                                            </tr>
                                            <%--<tr align="left"> 
                                                  <td width="2%"  valign="top">&nbsp;</td>
                                                  <td width="13%"  valign="top" nowrap>Total Hours Plan</td>
                                                  <td  width="85%"  valign="top"> 
                                                    <input type="text" name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_TOT_HOURS_PLAN]%>" value="<%=trainingActivityPlan.getTotHoursPlan()==0?"":String.valueOf(trainingActivityPlan.getTotHoursPlan())%>" class="formElemen" size="10">
                                                    * <%=(errDisplay == true) ? frmTrainingActivityPlan.getErrorMsg(FrmTrainingActivityPlan.FRM_FIELD_TOT_HOURS_PLAN) : ""%>
                                                  </td>
                                            </tr>
                                            <tr align="left"> 
                                                  <td width="2%"  valign="top">&nbsp;</td>
                                                  <td width="13%"  valign="top" nowrap>Trainees Plan</td>
                                                  <td  width="85%"  valign="top"> 
                                                    <input type="text" name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_TRAINEES_PLAN]%>" value="<%=trainingActivityPlan.getTraineesPlan()==0?"":String.valueOf(trainingActivityPlan.getTraineesPlan())%>" class="formElemen" size="10">
                                                    * <%=(errDisplay == true) ? frmTrainingActivityPlan.getErrorMsg(FrmTrainingActivityPlan.FRM_FIELD_TRAINEES_PLAN) : ""%>
                                                  </td>
                                            </tr>--%>                                        
                                            <tr align="left"> 
                                                  <td width="2%"  valign="top">&nbsp;</td>
                                                  <td width="13%"  valign="top" nowrap>Remark</td>
                                                  <td  width="85%"  valign="top"> 
                                                    <textarea name="<%=FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_REMARK]%>" cols="30" class="formElemen" rows="3" wrap="virtual"><%=trainingActivityPlan.getRemark()%></textarea>
                                                  </td>
                                            </tr>
                                            <tr align="left"> 
                                                  <td width="2%"  valign="top"  >&nbsp;</td>
                                                  <td width="13%"  valign="top" nowrap>&nbsp;</td>
                                                  <td  width="85%"  valign="top">&nbsp;</td>
                                            </tr>                                  
                                          </table>
                                          </td>
                                          <td>
                                              <% if (iCommand == Command.SAVE && frmTrainingActivityPlan.errorSize() == 0 || 
                                                     iCommand == Command.EDIT || iCommand == Command.BACK || iCommand == Command.ASK) { %>
                                                  
                                                  <%-- TRAINING SCHEDULE START --%>
                                                  <table width="100%">
                                                  <tr>
                                                      <td colspan="3"><b>Training Schedule Data</b></td>
                                                  </tr>
                                                  <tr>
                                                      <td colspan="3"><%= drawScheduleList(listSchedule, oidTrainingPlan)%></td>
                                                  </tr>
                                                  <tr>                                                   
                                                      <td width="5%"><a href="javascript:cmdAddSchedule('<%=oidTrainingPlan%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10a','','<%=approot%>/images/BtnNewOn.jpg',1)" id="aSearch"><img name="Image10a" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add Schedule"></a></td>
                                                      <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                      <td width="94%" class="command" nowrap><a href="javascript:cmdAddSchedule('<%=oidTrainingPlan%>')">Add Schedule</a></td> 
                                                  </tr>
                                                  </table>
                                                  <%-- TRAINING SCHEDULE END --%>
                                                  
                                              <% } %>
                                          </td>
                                      </tr>   
                                      <tr align="left">                                                                              
                                          <td colspan="3"> 
                                            <%
                                                ctrLine.setLocationImg(approot+"/images");
                                                ctrLine.initDefault();
                                                ctrLine.setTableWidth("90");
                                                ctrLine.setCommandStyle("buttonlink");
                                                
                                                String scomDel = "javascript:cmdAsk('"+oidTrainingPlan+"')";
                                                String sconDelCom = "javascript:cmdConfirmDelete('"+oidTrainingPlan+"')";
                                                String scancel = "javascript:cmdEdit('"+oidTrainingPlan+"')";
                                                
                                                ctrLine.setAddCaption("");                                                
                                                ctrLine.setBackCaption("Back to Search Plan");
                                                ctrLine.setDeleteCaption("Delete Plan");
                                                ctrLine.setConfirmDelCaption("Yes Delete Plan");
                                                ctrLine.setSaveCaption("Save Plan");                                               
                                               

                                                if (privDelete){
                                                    ctrLine.setConfirmDelCommand(sconDelCom);
                                                    ctrLine.setDeleteCommand(scomDel);
                                                    ctrLine.setEditCommand(scancel);
                                                }
                                                else { 
                                                    ctrLine.setConfirmDelCaption("");
                                                    ctrLine.setDeleteCaption("");
                                                    ctrLine.setEditCaption("");
                                                }

                                                if (privAdd == false  && privUpdate == false){
                                                    ctrLine.setSaveCaption("");
                                                }                                           
                                                                                                
                                                
                                                if ((iCommand == Command.SAVE  && frmTrainingActivityPlan.errorSize() == 0) || (iCommand == Command.DELETE)) {                                       
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
                                                                                                
                                                <% if (iCommand == Command.SAVE && frmTrainingActivityPlan.errorSize() == 0 || 
                                                       iCommand == Command.EDIT || iCommand == Command.BACK || iCommand == Command.ASK) { %>
                                                                
                                                        <%-- TRAINING ATTENDANCE START --%>
                                                        <table width="100%" border="0">
                                                        <tr>
                                                            <td colspan="3">&nbsp;</td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="3"><b>Training Attendances Data</b></td>
                                                        </tr>
                                                        <tr> 
                                                            <td colspan="3"><%= drawAttendanceList(listAttendance)%></td>                                                        
                                                        </tr>                                                       
                                                        <tr>
                                                            <td width="3%"><a href="javascript:cmdEditAttendance('<%=oidTrainingPlan%>','<%=totalHours%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10a','','<%=approot%>/images/BtnNewOn.jpg',1)" id="aSearch"><img name="Image10a" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add Schedule"></a></td>
                                                            <td width="1%"><img src="<%=approot%>/images/spacer.gif" width="1" height="1"></td>
                                                            <td width="96%" class="command" nowrap><a href="javascript:cmdEditAttendance('<%=oidTrainingPlan%>','<%=totalHours%>')">Edit Attendances</a></td>                                                        
                                                        </tr>                                                    
                                                        </table>                                       
                                                        <%-- TRAINING ATTENDANCE END --%>                                                   
                                                  
                                                <% } %>    
                                          </td>
                                        </tr>
                                      </table>
                                    </form>
                                    <!-- #EndEditable --> </td>
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
<!-- #EndEditable --> <!-- #EndTemplate -->
</html>
