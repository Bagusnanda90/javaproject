
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<%-- 
    Document   : training_actual_edit
    Created on : Jan 16, 2009, 9:49:52 AM
    Author     : bayu
--%>

<%@ page language = "java" %>

<!-- package java -->
<%@ page import = "java.util.*" %>
<%@ page import = "java.net.*" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
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
    }else if(trainType == PRIV_DEPT) {  
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
    public String drawAttendanceList(Vector attendances, String[] oids, String[] hours, Date start, Date end) {        
        
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");

        ctrlist.addHeader("No","5%");
        ctrlist.addHeader("Payroll Number","15%");
        ctrlist.addHeader("Name","30%");
        ctrlist.addHeader("Department","25%");
        ctrlist.addHeader("Commencing Date","20%");  
        ctrlist.addHeader("Hours","5%");              
     
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();

        ctrlist.setLinkRow(-1);
        ctrlist.setLinkPrefix("javascript:cmdEditAttendance('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        
        int number = 1;
        int duration = SessTraining.getTrainingDuration(start, end);               
        
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

                String strNumber = String.valueOf(number++);                        
                if(i == 0)
                    strNumber += "<input type='hidden' name='count_attds' value='" + attendances.size() + "'>";
                            
                rowx.add(strNumber);
                rowx.add("<input type='checkbox' name='chk_present" + i + "'" + (isSelected(emp.getOID(), oids) ? " checked " : "") + "value='" + emp.getOID() + "' onclick='javascript:checkEnabled(" + i + ")'>" + emp.getEmployeeNum());
                //rowx.add("<input type='hidden' name='hdn_hours' value='" + attendancePlan.getDuration() + "'>" + emp.getFullName());
                rowx.add(emp.getFullName());
                rowx.add(dept.getDepartment());
                rowx.add((emp.getCommencingDate() == null) ? "-" : Formater.formatDate(emp.getCommencingDate(), "MMM dd, yyyy"));               
                
                if(isSelected(emp.getOID(), oids)) {
                    int idx = getIndex(emp.getOID(), oids);
                    rowx.add("<input type='text' name='txtHour" + i + "' value='" + SessTraining.getDurationString(Integer.parseInt(hours[idx])) + "' onchange='javascript:checkValue(" + i + ")'>");
                }
                else {
                    rowx.add("<input type='text' name='txtHour" + i + "' value='0' onchange='javascript:checkValue(" + i + ")'>");
                }
                
                lstData.add(rowx);               
            }           
        }
        
        return ctrlist.draw();        
    }   

    private boolean isSelected(long oid, String[] oids) {      
    
        if(oids != null && oids.length > 0) {
            for(int i=0; i<oids.length; i++) {
                if(Long.parseLong(oids[i]) == oid) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private int getIndex(long oid, String[] oids) {      
    
        if(oids != null && oids.length > 0) {
            for(int i=0; i<oids.length; i++) {
                if(Long.parseLong(oids[i]) == oid) {
                    return i;
                }
            }
        }
        return -1;
    }

%>

<!-- Jsp Block -->
<%
    int iCommand = FRMQueryString.requestCommand(request);
    long oidDepartment = FRMQueryString.requestLong(request, "department_id");
    long oidTraining = FRMQueryString.requestLong(request, "training_id");
    long oidTrainingPlan = FRMQueryString.requestLong(request, "training_plan_id");
    long oidTrainingActivityActual = FRMQueryString.requestLong(request, "training_actual_id");
    long oidTrainingSchedule = FRMQueryString.requestLong(request, "training_schedule_id");
    String strTitle = FRMQueryString.requestString(request, "title");
    int totalAttds = FRMQueryString.requestInt(request, "count_attds");
    //String[] oidAttendance = request.getParameterValues("chk_present");   
    //String[] attdHours = request.getParameterValues("hdn_hours");      
    //int durationInMinutes = FRMQueryString.requestInt(request, "duration");
       
    int iErrCode = FRMMessage.ERR_NONE;
    int start = 0;
    int duration = 0;
    String errMsg = "";
  
    Vector vctEmployeeTraining = new Vector(1, 1);   
    ControlLine ctrLine = new ControlLine();
    
    
    /* ambil daftar peserta berdasarkan plan */
    if(iCommand == Command.SAVE || iCommand == Command.EDIT || iCommand == Command.LIST || iCommand == Command.ASK || iCommand == Command.BACK) {        
        vctEmployeeTraining = PstTrainingAttendancePlan.getAttendanceByPlan(oidTrainingPlan);
    }
    
    /*if(vctEmployeeTraining != null) {
        System.out.println("xxx = " + vctEmployeeTraining.size());
        oidAttendance = new String[vctEmployeeTraining.size()];
        attdHours = new String[vctEmployeeTraining.size()];
        
        for(int i=0; i<vctEmployeeTraining.size(); i++) {
            oidAttendance[i] = request.getParameter("chk_present" + i);
            attdHours[i] = String.valueOf(SessTraining.getTrainingDuration(request.getParameter("txt_hour" + i)));
        }
    }*/
    
    // !------
    //iErrCode = ctrlTrainingActivityActual.action(iCommand, oidTrainingActivityActual, oidDepartment, request);
    
    
    /* ambil id peserta beserta jam, untuk tiap peserta yang ikut */
    String[] attdHours = null;
    String[] oidAttendance = null;
    
    Vector vctAttdHours = new Vector();
    Vector vctOidAttendance = new Vector();
    
    if(totalAttds > 0) {       
        for(int i=0; i<totalAttds; i++) {
            if(!FRMQueryString.requestString(request, "txtHour" + i).equals("0")) {
                vctAttdHours.add( FRMQueryString.requestString(request, "txtHour" + i));
                vctOidAttendance.add( FRMQueryString.requestString(request, "chk_present" + i));
            }
        }        
    }
    
    attdHours = new String[vctAttdHours.size()];
    for(int i=0; i<vctAttdHours.size(); i++)
        attdHours[i] = "" + SessTraining.getTrainingDuration((String)vctAttdHours.get(i));
    
    oidAttendance = new String[vctOidAttendance.size()];
    for(int i=0; i<vctOidAttendance.size(); i++)
        oidAttendance[i] = (String)vctOidAttendance.get(i);
    
    
    CtrlTrainingActivityActual ctrlTrainingActivityActual = new CtrlTrainingActivityActual(request);    
    FrmTrainingActivityActual frmTrainingActivityActual = new FrmTrainingActivityActual();
    TrainingActivityActual trainingActivityActual = new TrainingActivityActual();    
    
    iErrCode = ctrlTrainingActivityActual.action(iCommand, attdHours, oidTraining, oidTrainingSchedule, oidTrainingPlan, oidTrainingActivityActual, oidAttendance, request);
    
    errMsg = ctrlTrainingActivityActual.getMessage();
    frmTrainingActivityActual = ctrlTrainingActivityActual.getForm();
    trainingActivityActual = ctrlTrainingActivityActual.getTrainingActivityActual();
    
    
    if(iCommand == Command.BACK) {
        frmTrainingActivityActual = new FrmTrainingActivityActual(request, trainingActivityActual);
        frmTrainingActivityActual.requestEntityObject(trainingActivityActual);
        
        trainingActivityActual.setOID(oidTrainingActivityActual);
        trainingActivityActual.setScheduleId(oidTrainingSchedule);
        
        int date = FRMQueryString.requestInt(request, "date");
        int month = FRMQueryString.requestInt(request, "month");
        int year = FRMQueryString.requestInt(request, "year");        
        trainingActivityActual.setDate(new Date(year, month, date));
        
        int startHour = FRMQueryString.requestInt(request, "start_hour");
        int startMinute = FRMQueryString.requestInt(request, "start_minute");
        trainingActivityActual.setStartTime(new Date(year, month, date, startHour, startMinute));
        
        int endHour = FRMQueryString.requestInt(request, "end_hour");
        int endMinute = FRMQueryString.requestInt(request, "end_minute");
        trainingActivityActual.setEndTime(new Date(year, month, date, endHour, endMinute));
        
        // recount training attendances              
        int totalAttendance = PstTrainingAttendancePlan.getCount(PstTrainingSchedule.fieldNames[PstTrainingSchedule.FLD_TRAIN_PLAN_ID] + "=" + oidTrainingPlan); 
                
        // update training plan table
        try {
            TrainingActivityPlan plan = PstTrainingActivityPlan.fetchExc(oidTrainingPlan);
            plan.setTraineesPlan(totalAttendance);

            PstTrainingActivityPlan.updateExc(plan);
        }
        catch(Exception e) {}
        
    }
    
    TrainingActivityPlan trainingPlan = new TrainingActivityPlan();
    TrainingSchedule trainingSchedule = new TrainingSchedule();
    TrainVenue trainingVenue = new TrainVenue();
    Training training = new Training();
    
    // after insert new 
    oidTrainingActivityActual = trainingActivityActual.getOID();
    
    // employee data
    //Vector vctEmployeeTraining = new Vector(1, 1);   
    //Vector vctEmployeeActual = new Vector(1, 1);
    
    if(iCommand == Command.SAVE || iCommand == Command.EDIT || iCommand == Command.LIST || iCommand == Command.ASK || iCommand == Command.BACK) {        
        //vctEmployeeTraining = PstTrainingAttendancePlan.getAttendanceByPlan(oidTrainingPlan);
        //vctEmployeeActual = PstTrainingHistory.listEmployeeTrainingByActivity(oidTrainingActivityActual);
        
        if(iCommand == Command.LIST) {            
           
            
            try {
                trainingPlan = PstTrainingActivityPlan.fetchExc(oidTrainingPlan);
            }
            catch(Exception e) {
                trainingPlan = new TrainingActivityPlan();
            }

            try {
                trainingSchedule = PstTrainingSchedule.fetchExc(oidTrainingSchedule);
            }
            catch(Exception e) {
                trainingSchedule = new TrainingSchedule();
            }

            try {
                trainingVenue = PstTrainVenue.fetchExc(trainingSchedule.getTrainVenueId());
            }
            catch(Exception e) {
                trainingVenue = new TrainVenue();
            }

            try {
                training = PstTraining.fetchExc(oidTraining);
            }
            catch(Exception e) {
                training = new Training();
            }               

            // set values        
            trainingActivityActual.setTrainingActivityPlanId(trainingPlan.getOID());
            trainingActivityActual.setDate(trainingSchedule.getTrainDate());
            trainingActivityActual.setStartTime(trainingSchedule.getStartTime());
            trainingActivityActual.setEndTime(trainingSchedule.getEndTime());
            //trainingActivityActual.setAtendees(trainingPlan.getTraineesPlan());
            trainingActivityActual.setAtendees(oidAttendance == null? 0 : oidAttendance.length);
            trainingActivityActual.setVenue(trainingVenue.getVenueName());
            trainingActivityActual.setTrainner(trainingPlan.getTrainer());
            trainingActivityActual.setRemark(trainingPlan.getRemark());
            trainingActivityActual.setTrainingId(oidTraining);
            trainingActivityActual.setScheduleId(oidTrainingSchedule);
            
            //durationInMinutes =  SessTraining.getTrainingDuration(trainingSchedule.getStartTime(), trainingSchedule.getEndTime());
        }
        
        if(iCommand == Command.EDIT) {
            
            try {
                trainingPlan = PstTrainingActivityPlan.fetchExc(oidTrainingPlan);
            }
            catch(Exception e) {
                trainingPlan = new TrainingActivityPlan();
            }
            
            try {
                trainingActivityActual = PstTrainingActivityActual.fetchExc(oidTrainingActivityActual);
            }
            catch(Exception e) {
                trainingActivityActual = new TrainingActivityActual();
            }
            
            try {
                training = PstTraining.fetchExc(oidTraining);
            }
            catch(Exception e) {
                training = new Training();
            }         

            String where = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ACTIVITY_ACTUAL_ID] + "=" + oidTrainingActivityActual;
            Vector listAttd = PstTrainingHistory.list(0, 0, where, "");  
                     
            
            oidAttendance = new String[listAttd.size()];
            attdHours = new String[listAttd.size()];
                       
            for(int i=0; i<listAttd.size(); i++) {
                TrainingHistory hist = (TrainingHistory)listAttd.get(i);
                oidAttendance[i] = String.valueOf(hist.getEmployeeId());
                attdHours[i] = String.valueOf(hist.getDuration());
            }
            
            //durationInMinutes = SessTraining.getTrainingDuration(trainingActivityActual.getStartTime(), trainingActivityActual.getEndTime());                      
        }
        
        duration = SessTraining.getTrainingDuration(trainingActivityActual.getStartTime(), trainingActivityActual.getEndTime());
        
    }
    
%>

<!-- End of Jsp Block -->
<html>    
    <head>
        <title>HARISMA - Training Activity Actual</title>
        <script language="JavaScript">
            
            function cmdCancel(){
                document.frm_trainingplan.command.value="<%=Command.CANCEL%>";
                document.frm_trainingplan.action="training_actual_edit.jsp";
                document.frm_trainingplan.submit();
            } 
            
            function cmdEdit(oid){ 
                document.frm_trainingplan.command.value="<%=Command.EDIT%>";
                document.frm_trainingplan.action="training_actual_edit.jsp";
                document.frm_trainingplan.submit(); 
            } 
            
            function cmdSave(){
                document.frm_trainingplan.command.value="<%=Command.SAVE%>"; 
                document.frm_trainingplan.action="training_actual_edit.jsp";
                document.frm_trainingplan.submit();
            }
            
            function cmdAdd(){
                document.frm_trainingplan.command.value="<%=Command.ADD%>"; 
                document.frm_trainingplan.action="training_actual_edit.jsp";
                document.frm_trainingplan.submit();
            } 
            
            function cmdAsk(oid){
                document.frm_trainingplan.command.value="<%=Command.ASK%>"; 
                document.frm_trainingplan.action="training_actual_edit.jsp";
                document.frm_trainingplan.submit();
            } 
            
            function cmdConfirmDelete(oid){
                document.frm_trainingplan.command.value="<%=Command.DELETE%>";
                document.frm_trainingplan.action="training_actual_edit.jsp"; 
                document.frm_trainingplan.submit();
            }  
            
            function cmdBack(){
                document.frm_trainingplan.command.value="<%=Command.BACK%>"; 
                document.frm_trainingplan.action="training_actual_list.jsp";
                document.frm_trainingplan.submit();
            }
            
            function cmdEditAttendance(oidPlan, totalHours) {      
                window.open("<%=approot%>/employee/training/input_attendance.jsp?plan=" + oidPlan + "&hours=" + totalHours, null, 
                           "height=550,width=600,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes");       
            }
            
            function cmdSearchPlan(){
                window.open("input_plan.jsp", null, "height=600,width=800, status=yes,toolbar=no,menubar=yes,location=no, scrollbars=yes");
            }                     
                     
            function cmdSearch() {                
                oidDepartment = document.frm_trainingplan.oidDepartment.value;
                oidTraining = document.frm_trainingplan.oidTraining.value;
                oidTrainingAktivityActual = document.frm_trainingplan.training_actual_id.value;
                oidTrainingPlan = document.frm_trainingplan.oidTrainingPlan.value;
                dateStart = <%= (trainingActivityActual.getDate() != null) ? trainingActivityActual.getDate().getTime() : new Date().getTime()%>
                
                window.open("search_employee.jsp?oidDepartment=" + oidDepartment + "&oidTraining=" + oidTraining + "&oidTrainingAktivityActual=" + 
                            oidTrainingAktivityActual + "&oidTrainingPlan=" + oidTrainingPlan + "&dateStart=" + dateStart,
                            null, "height=600,width=800, status=yes,toolbar=no,menubar=yes,location=no, scrollbars=yes");

            }
            
            function setChecked(val) {
                dml=document.frm_trainingplan;
                len = dml.elements.length;
                
                var i=0;
                for( i=0 ; i<len ; i++) {						
                    dml.elements[i].checked = val;					
                }
            }
            
            
            function setSelected() {
                <% for(int i=0 ; i<vctEmployeeTraining.size(); i++) { %>	            
                    document.frm_trainingplan.txtHour<%=i%>.value = "<%=SessTraining.getDurationString(duration)%>";   
                    document.frm_trainingplan.chk_present<%=i%>.checked = "true";  
                <% } %>
            }

            function setUnselected() {           
                <% for(int i=0 ; i<vctEmployeeTraining.size(); i++) { %>	            
                    document.frm_trainingplan.txtHour<%=i%>.value = "0";  
                    document.frm_trainingplan.chk_present<%=i%>.checked = "";   
                <% } %>           
            }
        
            function checkEnabled(index) {
                <% for(int i=0 ; i<vctEmployeeTraining.size(); i++) { %> 
                    if(<%=String.valueOf(i)%> == index) {
                        if(document.frm_trainingplan.chk_present<%=String.valueOf(i)%>.checked == "") {
                            document.frm_trainingplan.txtHour<%=String.valueOf(i)%>.value = "0";             
                        }
                        else {
                            document.frm_trainingplan.txtHour<%=String.valueOf(i)%>.value = "<%=SessTraining.getDurationString(duration)%>";
                        }

                    }
                <% } %>           
            }
            
            function checkValue(index){
                <% for(int j=0 ; j<vctEmployeeTraining.size(); j++) { %> 
                    if(<%=String.valueOf(j)%> == index) {
                        if(document.frm_trainingplan.txtHour<%=String.valueOf(j)%>.value == "" || document.frm_trainingplan.txtHour<%=String.valueOf(j)%>.value == "0") {
                            document.frm_trainingplan.txtHour<%=String.valueOf(j)%>.value = "0";             
                            document.frm_trainingplan.chk_present<%=String.valueOf(j)%>.checked = "";         
                        }
                        else {
                            document.frm_trainingplan.chk_present<%=String.valueOf(j)%>.checked = "true";    
                        }

                    }
                <%}%>
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
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
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
        </SCRIPT>
    </head>
    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                <%@ include file = "../../main/header.jsp" %> </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <%@ include file = "../../main/mnmain.jsp" %> </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="10" valign="middle"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                            <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
                            <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
                            <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
                        </tr>
                </table></td>
            </tr>
            <%}%>
            <tr> 
                <td width="88%" valign="top" align="left"> 
                    <table width="100%" border="0" cellspacing="3" cellpadding="2">
                    <tr> 
                        <td width="100%"> 
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr> 
                                <td height="20"> <font color="#FF6600" face="Arial"><strong> Training 
                                &gt; Training Activity </strong></font> </td>
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
                                                    <td valign="top"> <form name="frm_trainingplan" method="post" action="">
                                                        <input type="hidden" name="command" value="<%=iCommand%>">
                                                        <input type="hidden" name="start" value="<%=start%>">
                                                        <input type="hidden" name="department_id" value="<%=oidDepartment%>">
                                                        <%--input type="hidden" name="duration" value="<%=durationInMinutes--%>
                                                        <input type="hidden" name="training_id" value="<%=oidTraining%>">
                                                        <input type="hidden" name="training_plan_id" value="<%=oidTrainingPlan%>">
                                                        <input type="hidden" name="training_actual_id" value="<%=oidTrainingActivityActual%>">  
                                                        <input type="hidden" name="training_schedule_id" value="<%=oidTrainingSchedule%>">
                                                       
                                                        <table width="100%" cellspacing="1" cellpadding="1" >
                                                            <tr> 
                                                                <td colspan="3">&nbsp;</td>
                                                            </tr>
                                                            <tr> 
                                                                <td width="0%">&nbsp;</td>
                                                                <td class="txtheading1" align="center" width="12%" nowrap>&nbsp;</td>
                                                                <td class="comment" width="85%">*) entry required</td>
                                                            </tr>
                                                            <tr> 
                                                                <td width="0%"  valign="top" align="left"  >&nbsp;</td>
                                                                <td width="12%"  valign="top" align="left" nowrap  >Training  Program</td>
                                                                <td  width="85%"  valign="top" align="left"> 
                                                                    <input type="hidden" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_TRAINING_ACTIVITY_PLAN_ID]%>" value="<%=trainingActivityActual.getTrainingActivityPlanId()%>">
                                                                    <input type="text" name="title" value="<%= (training.getName().equals("")) ? strTitle : training.getName() %>" size="50" readonly>
                                                                    * <%=frmTrainingActivityActual.getErrorMsg(FrmTrainingActivityActual.FRM_FIELD_TRAINING_ACTIVITY_PLAN_ID)%> <a href="javascript:cmdSearchPlan()">Search for existing plan</a>
                                                                </td>
                                                            </tr>
                                                            <tr> 
                                                                <td width="0%"  valign="top" align="left"  >&nbsp;</td>
                                                                <td width="12%"  valign="top" align="left" nowrap  >Date</td>
                                                                <td  width="85%"  valign="top" align="left"> 
                                                                    <%
                                                                    if(trainingActivityActual.getDate() == null){
                                                                    %> 
                                                                        <input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_DATE]%>" readonly value="">
                                                                    <%    
                                                                    }else{
                                                                    %>
                                                                    <input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_DATE]%>" readonly value="<%= (trainingActivityActual.getDate() == null) ? Formater.formatDate(new Date(), "MMMM dd, yyyy") : Formater.formatDate(trainingActivityActual.getDate(), "MMMM dd, yyyy")%>" class="formElemen">
                                                                    <input type="hidden" name="date" value="<%=(trainingActivityActual.getDate() == null) ? new Date().getDate() : trainingActivityActual.getDate().getDate()%>">
                                                                    <input type="hidden" name="month" value="<%=(trainingActivityActual.getDate() == null) ? new Date().getMonth() : trainingActivityActual.getDate().getMonth()%>">
                                                                    <input type="hidden" name="year" value="<%=(trainingActivityActual.getDate() == null) ? new Date().getYear() : trainingActivityActual.getDate().getYear()%>">
                                                                    <% 
                                                                    }
                                                                    %>
                                                                </td>
                                                            <tr> 
                                                                <td width="0%"  valign="top" align="left"  >&nbsp;</td>
                                                                <td width="12%"  valign="top" align="left" nowrap>Time</td>
                                                                <td  width="85%"  valign="top" align="left">
                                                                    <%
                                                                    if(trainingActivityActual.getStartTime() == null){
                                                                    %>    
                                                                    <input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_START_TIME]%>" readonly value="" class="formElemen">
                                                                    
                                                                    <%    
                                                                    }else{
                                                                    %> 
                                                                    <%--=ControlDate.drawTime(FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_START_TIME], (trainingActivityActual.getStartTime() == null) ? new Date() : trainingActivityActual.getStartTime(), "formElemen")--%>
                                                                    <input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_START_TIME]%>" readonly value="<%= (trainingActivityActual.getStartTime() == null) ? Formater.formatDate(new Date(), "HH:mm") : Formater.formatDate(trainingActivityActual.getStartTime(), "HH:mm")%>" class="formElemen">
                                                                    <input type="hidden" name="start_hour" value="<%=((trainingActivityActual.getStartTime() != null) ? trainingActivityActual.getStartTime().getHours() : new Date().getHours())%>">
                                                                    <input type="hidden" name="start_minute" value="<%=((trainingActivityActual.getStartTime() != null) ? trainingActivityActual.getStartTime().getMinutes() : new Date().getMinutes())%>">                                                                
                                                                    <%
                                                                    }
    
                                                                    if(trainingActivityActual.getEndTime() == null){
                                                                    %>    
                                                                        <input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_END_TIME]%>" readonly value="" class="formElemen">  
                                                                    <%    
                                                                    }else{    
                                                                    %>
                                                                    &nbsp;&nbsp;To&nbsp;&nbsp;
                                                                    <%--=ControlDate.drawTime(FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_END_TIME], (trainingActivityActual.getEndTime() == null) ? new Date() : trainingActivityActual.getEndTime(), "formElemen")--%>
                                                                    <input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_END_TIME]%>" readonly value="<%= (trainingActivityActual.getEndTime() == null) ? Formater.formatDate(new Date(), "HH:mm") : Formater.formatDate(trainingActivityActual.getEndTime(), "HH:mm")%>" class="formElemen">                                                                  
                                                                    <input type="hidden" name="end_hour" value="<%=((trainingActivityActual.getEndTime() != null) ? trainingActivityActual.getEndTime().getHours() : new Date().getHours())%>">
                                                                    <input type="hidden" name="end_minute" value="<%=((trainingActivityActual.getEndTime() != null) ? trainingActivityActual.getEndTime().getMinutes() : new Date().getMinutes())%>">
                                                                    <%
                                                                    }                                                                   
                                                                    %>
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

                                                        String selectedContact = String.valueOf(trainingActivityActual.getOrganizerID());;

                                                    %>
                                                    
                                                    <%= ControlCombo.draw(FrmTrainingActivityPlan.fieldNames[FrmTrainingActivityPlan.FRM_FIELD_ORGANIZER_ID], null, selectedContact, contactIds, contactNames, "onkeydown=\"javascript:fnTrapKD()\"","formElemen") %> 
                                                    * <%=frmTrainingActivityActual.getErrorMsg(FrmTrainingActivityPlan.FRM_FIELD_ORGANIZER_ID)%>
                                                  </td>
                                            </tr> 
                                            
                                                            
                                                            <tr> 
                                                                <td width="0%"  valign="top" align="left"  >&nbsp;</td>
                                                                <td width="12%"  valign="top" align="left" nowrap  >Venue</td>
                                                                <td  width="85%"  valign="top" align="left"> 
                                                                <input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_VENUE]%>" readonly value="<%=trainingActivityActual.getVenue()%>" class="formElemen" size="30">
                                                                </td>
                                                            </tr>
                                                            <tr> 
                                                                <td  valign="top" align="left"  >&nbsp;</td>
                                                                <td  valign="top" align="left" nowrap  >Trainner</td>
                                                                <td  valign="top" align="left">
                                                                <input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_TRAINNER]%>" readonly value="<%= trainingActivityActual.getTrainner()%>" size="30" class="formElement" >
                                                                </td>
                                                            </tr>
                                                            <tr> 
                                                                <td width="0%"  valign="top" align="left"  >&nbsp;</td>
                                                                <td width="12%"  valign="top" align="left" nowrap  >Remark</td>
                                                                <td  width="85%"  valign="top" align="left"> 
                                                                <textarea name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_REMARK]%>" class="formElemen" cols="30" wrap="virtual" rows="3"><%=trainingActivityActual.getRemark()%></textarea>
                                                                </td>
                                                            </tr>
                                                            <tr> 
                                                                <td width="0%"  valign="top" align="left"  >&nbsp;</td>
                                                                <td width="12%"  valign="top" align="left" nowrap ></td>
                                                                <td  width="85%"  valign="top" align="left">&nbsp;</td>
                                                            </tr>
                                                            <tr> 
                                                                <td valign="top" align="left">&nbsp;</td>   
                                                                <td valign="top" align="left">&nbsp;</td>   
                                                                <td width="85%"></td>
                                                                <td width="3%"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                                            </tr>
                                                            <tr> 
                                                                <td colspan="3"> 
                                                                <%
                                                                    ctrLine.setLocationImg(approot + "/images");
                                                                    ctrLine.initDefault();
                                                                    ctrLine.setTableWidth("85%");
                                                                    ctrLine.setCommandStyle("buttonlink");
                                                                    
                                                                    String scomDel = "javascript:cmdAsk('" + oidTrainingActivityActual + "')";
                                                                    String sconDelCom = "javascript:cmdConfirmDelete('" + oidTrainingActivityActual + "')";
                                                                    String scancel = "javascript:cmdEdit('" + oidTrainingActivityActual + "')";
                                                                    
                                                                    ctrLine.setAddCaption("");
                                                                    ctrLine.setBackCaption("Back to Search Training");
                                                                    ctrLine.setDeleteCaption("Delete Training");
                                                                    ctrLine.setSaveCaption("Save Training");
                                                                    

                                                                    if (privDelete) {
                                                                        ctrLine.setConfirmDelCommand(sconDelCom);
                                                                        ctrLine.setDeleteCommand(scomDel);
                                                                        ctrLine.setEditCommand(scancel);
                                                                    }
                                                                    else {
                                                                        ctrLine.setConfirmDelCaption("");
                                                                        ctrLine.setDeleteCaption("");
                                                                        ctrLine.setEditCaption("");                                                                      
                                                                    }

                                                                    if (privAdd == false && privUpdate == false) {
                                                                        ctrLine.setSaveCaption("");
                                                                    }
                                                                   
                                                                    if (iCommand == Command.SAVE || iCommand == Command.EDIT || iCommand == Command.LIST || iCommand == Command.ASK || iCommand == Command.BACK)  {%>
                                                                
                                                                        <!--masukkan detail employee yang ikut training-->
                                                                        <table width="100%" border="0">  
                                                                        <tr>
                                                                            <td height="8"  nowrap>
                                                                                <a href="javascript:setSelected()">Select All</a> &nbsp;&nbsp;| &nbsp;&nbsp;
                                                                                <a href="javascript:setUnselected()">Release All</a>
                                                                            </td>
                                                                            <td align="right">&nbsp;</td>
                                                                        </tr>
                                                                        <tr> 
                                                                            <td colspan="2" align="left"  valign="top" nowrap  >  
                                                                                <%=drawAttendanceList(vctEmployeeTraining, oidAttendance, attdHours, trainingActivityActual.getStartTime(),
                                                                                                      trainingActivityActual.getEndTime())%>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td height="8"  nowrap>
                                                                                <a href="javascript:setSelected()">Select All</a> &nbsp;&nbsp;| &nbsp;&nbsp;
                                                                                <a href="javascript:setUnselected()">Release All</a>
                                                                            </td>
                                                                            <td align="right">
                                                                                <%String hour = String.valueOf(SessTraining.getTrainingDuration(trainingActivityActual.getStartTime(), trainingActivityActual.getEndTime())); %>
                                                                                <a href="javascript:cmdEditAttendance('<%=oidTrainingPlan%>','<%=hour%>')">Edit Attendees</a>                                                                                   
                                                                            </td>
                                                                        </tr>
                                                                        </table>
                                                                    
                                                                    <% }
                                                                    
                                                                        if(iCommand == Command.LIST || iCommand == Command.BACK)
                                                                            iCommand = Command.EDIT;
                                                                    %>
                                                                    <%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%> 
                                                                </td>
                                                                </tr>
                                                            </table>
                                                        </form>                                                            
                                                        </td>
                                                                </tr>
                                                                </table></td>
                                                            </tr>
                                                    </table></td>
                                                </tr>
                                        </table></td>
                                    </tr>
                            </table></td>
                        </tr>
                </table></td>
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
</html>
