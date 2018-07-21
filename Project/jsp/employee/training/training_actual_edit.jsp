
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<%-- 
    Document   : training_actual_edit
    Created on : Jan 16, 2009, 9:49:52 AM
    Author     : bayuu
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
        ctrlist.setListStyle("tblStyle");
        ctrlist.setTitleStyle("title_tbl");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("title_tbl");
            
        ctrlist.setCellSpacing("0");

        ctrlist.addHeader("No","");
        ctrlist.addHeader("Payroll Number","");
        ctrlist.addHeader("Name","");
        ctrlist.addHeader("Department","");
        ctrlist.addHeader("Commencing Date","");  
        ctrlist.addHeader("Hours","");  
        ctrlist.addHeader("Action","");                          
     
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
                    rowx.add("<input type='text' size='10' name='txtHour" + i + "' value='" + SessTraining.getDurationString(Integer.parseInt(hours[idx])) + "' onchange='javascript:checkValue(" + i + ")'>");
                }
                else {
                    rowx.add("<input type='text' size='10' name='txtHour" + i + "' value='0' onchange='javascript:checkValue(" + i + ")'>");
                }
                rowx.add("<a class=\"btn-small-1\" href=\"javascript:cmdAskDetail('"+attendancePlan.getOID()+"')\">&times;</a>");
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
    /* Update by Hendra | 2016-01-20 */
    long oidDeleteItem = FRMQueryString.requestLong(request, "oid_delete");
    if (oidDeleteItem != 0){
         
        try {
            TrainingAttendancePlan trAttPlan = PstTrainingAttendancePlan.fetchExc(oidDeleteItem);
            String whereTHistory = PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_EMPLOYEE_ID]+"="+trAttPlan.getEmployeeId()+" AND ";
            whereTHistory += PstTrainingHistory.fieldNames[PstTrainingHistory.FLD_TRAINING_ACTIVITY_PLAN_ID]+"="+trAttPlan.getTrainPlanid();
            Vector dataTrainingHistory = PstTrainingHistory.list(0, 0, whereTHistory, "");
            if (dataTrainingHistory != null && dataTrainingHistory.size()>0){
                TrainingHistory dataTHistory = (TrainingHistory)dataTrainingHistory.get(0);
                PstTrainingHistory.deleteExc(dataTHistory.getOID());
            }
            PstTrainingAttendancePlan.deleteExc(oidDeleteItem);
        } catch(Exception e){
            System.out.println("deteleAttendatePlan=>"+e.toString());
        }
    }
    
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
            trainingActivityActual.setTrainingTitle(trainingPlan.getTrainingTitle());
            trainingActivityActual.setTrainingActivityPlanId(trainingPlan.getOID());
            trainingActivityActual.setDate(trainingSchedule.getTrainDate());
            trainingActivityActual.setTrainEndDate(trainingSchedule.getTrainEndDate());
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
            
            function cmdAskDetail(oid){
                document.getElementById("oid_delete").value=oid;
                document.getElementById("confirm-delete-form").style.visibility="visible";
            }
            
            function cmdBatal(){
                document.getElementById("oid_delete").value="0";
                document.getElementById("confirm-delete-form").style.visibility="hidden";
            }
            
            function cmdDelItem(){
                var oid = document.getElementById("oid_delete").value;
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
        <style type="text/css">
            .tblStyle {border-collapse: collapse;font-size: 11px; background-color: #FFF;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            .title_page {color:#0db2e1; font-weight: bold; font-size: 14px; background-color: #EEE; border-left: 1px solid #0099FF; padding: 12px 18px;}
            #menu_utama {
                padding: 9px 14px; 
                border-left: 1px solid #0099FF; 
                font-size: 14px; 
                background-color: #F3F3F3;
                border-bottom: 1px solid #E3E3E3;
            }
            #menu_title {color:#0099FF; font-size: 14px;}
            body {color:#373737;}
            
            #btn {
              background: #3498db;
              border: 1px solid #0066CC;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn:hover {
              background: #3cb0fd;
              border: 1px solid #3498db;
            }
            .title_part {color:#FF7E00; background-color: #F7F7F7; border-left: 1px solid #0099FF; padding: 9px 11px;}
            
            body {background-color: #EEE;}
            .header {
                
            }
            .content-main {
                padding: 21px 32px;
                margin: 0px 23px 59px 23px;
            }
            .content-info {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
            }
            .content-title {
                padding: 21px;
                border-bottom: 1px solid #E5E5E5;
                margin-bottom: 5px;
            }
            #title-large {
                color: #575757;
                font-size: 16px;
                font-weight: bold;
            }
            #title-small {
                color:#797979;
                font-size: 11px;
            }
            .content {
                padding: 21px;
            }
            .box {
                margin: 17px 7px;
                background-color: #FFF;
                color:#575757;
            }
            #box_title {
                padding:21px; 
                font-size: 14px; 
                color: #007fba;
                border-top: 1px solid #28A7D1;
                border-bottom: 1px solid #EEE;
            }
            #box_content {
                padding:21px; 
                font-size: 12px;
                color: #575757;
            }
            .box-info {
                padding:21px 43px; 
                background-color: #F7F7F7;
                border-bottom: 1px solid #CCC;
                -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                 -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
                      box-shadow: 0 1px 4px rgba(0, 0, 0, 0.065);
            }
            #title-info-name {
                padding: 11px 15px;
                font-size: 35px;
                color: #535353;
            }
            #title-info-desc {
                padding: 7px 15px;
                font-size: 21px;
                color: #676767;
            }
            
            #photo {
                padding: 7px; 
                background-color: #FFF; 
                border: 1px solid #DDD;
            }

            .btn {
                background-color: #00a1ec;
                border-radius: 3px;
                font-family: Arial;
                color: #EEE;
                font-size: 12px;
                padding: 7px 11px 7px 11px;
                text-decoration: none;
            }

            .btn:hover {
                color: #FFF;
                background-color: #007fba;
                text-decoration: none;
            }
            
            .btn-small {
                font-family: sans-serif;
                text-decoration: none;
                padding: 5px 7px; 
                background-color: #676767; color: #F5F5F5; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
            }
            .btn-small:hover { background-color: #474747; color: #FFF;}
            
            .btn-small-1 {
                font-family: sans-serif;
                text-decoration: none;
                padding: 3px 5px; 
                background-color: #EEE; color: #575757; 
                font-size: 11px; cursor: pointer;
                border-radius: 3px;
                border: 1px solid #DDD;
            }
            .btn-small-1:hover { background-color: #DDD; color: #474747;}
            
            .tbl-main {border-collapse: collapse; font-size: 11px; background-color: #FFF; margin: 0px;}
            .tbl-main td {padding: 4px 7px; border: 1px solid #DDD; }
            #tbl-title {font-weight: bold; background-color: #F5F5F5; color: #575757;}
            
            .tbl-small {border-collapse: collapse; font-size: 11px; background-color: #FFF;}
            .tbl-small td {padding: 2px 3px; border: 1px solid #DDD; }
            
            #caption {
                font-size: 12px;
                color: #474747;
                font-weight: bold;
                padding: 2px 0px 3px 0px;
            }
            #divinput {
                margin-bottom: 5px;
                padding-bottom: 5px;
            }
            
            #div_item_sch {
                background-color: #EEE;
                color: #575757;
                padding: 5px 7px;
            }
            
            #record_count{
                font-size: 12px;
                font-weight: bold;
                padding-bottom: 9px;
            }
            #box-form {
                background-color: #EEE; 
                border-radius: 5px;
            }
            .form-style {
                font-size: 12px;
                color: #575757;
                border: 1px solid #DDD;
                border-radius: 5px;
            }
            .form-title {
                padding: 11px 21px;
                text-align: left;
                border-bottom: 1px solid #DDD;
                background-color: #FFF;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
                font-weight: bold;
            }
            .form-content {
                text-align: left;
                padding: 21px;
                background-color: #DDD;
            }
            .form-footer {
                border-top: 1px solid #DDD;
                padding: 11px 21px;
                background-color: #FFF;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
            }
            #confirm-delete-form {
                padding: 13px 17px;
                background-color: #FF6666;
                color: #FFF;
                border-radius: 3px;
                font-size: 12px;
                font-weight: bold;
                visibility: hidden;
            }
            #btn-confirm {
                padding: 4px 9px; border-radius: 3px;
                background-color: #CF5353; color: #FFF; 
                font-size: 11px; cursor: pointer;
            }
            .footer-page {
                font-size: 12px;
            }
            
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
        <div id="menu_utama">
            <span id="menu_title">Training <strong style="color:#333;"> / </strong>Training Activity</span>
        </div>
        <div class="content-main">
            <form name="frm_trainingplan" method="post" action="">
                <input type="hidden" name="command" value="<%=iCommand%>">
                <input type="hidden" name="command_custom" value="<%=iCommand%>">
                <input type="hidden" name="start" value="<%=start%>">
                <input type="hidden" name="department_id" value="<%=oidDepartment%>">
                <%--input type="hidden" name="duration" value="<%=durationInMinutes--%>
                <input type="hidden" name="training_id" value="<%=oidTraining%>">
                <input type="hidden" name="training_plan_id" value="<%=oidTrainingPlan%>">
                <input type="hidden" name="training_actual_id" value="<%=oidTrainingActivityActual%>">  
                <input type="hidden" name="training_schedule_id" value="<%=oidTrainingSchedule%>">
                <input type="hidden" name="oid_delete" id="oid_delete" value="">
                <table>
                    <tr>
                        <td><strong>Training Title</strong></td>
                        <td><input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_TRAINING_TITLE]%>" readonly value="<%= trainingActivityActual.getTrainingTitle() %>" class="formElemen" size="90"></td>
                    </tr>
                    <tr>
                        <td><strong>Training Program</strong></td>
                        <td>
                            <input type="hidden" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_TRAINING_ACTIVITY_PLAN_ID]%>" value="<%=trainingActivityActual.getTrainingActivityPlanId()%>">
                            <input type="text" name="title" value="<%= (training.getName().equals("")) ? strTitle : training.getName() %>" size="90" readonly>
                            * <%=frmTrainingActivityActual.getErrorMsg(FrmTrainingActivityActual.FRM_FIELD_TRAINING_ACTIVITY_PLAN_ID)%> <a href="javascript:cmdSearchPlan()">Search for existing plan</a>
                        </td>
                    </tr>
                    <tr>
                        <td><strong>Date</strong></td>
                        <td>
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

                            <%
                            if(trainingActivityActual.getTrainEndDate() == null){
                            %> 
                                <input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_TRAIN_END_DATE]%>" readonly value="">
                            <%    
                            }else{
                            %>
                            <input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_TRAIN_END_DATE]%>" readonly value="<%= (trainingActivityActual.getTrainEndDate() == null) ? Formater.formatDate(new Date(), "MMMM dd, yyyy") : Formater.formatDate(trainingActivityActual.getTrainEndDate(), "MMMM dd, yyyy")%>" class="formElemen">
                            <input type="hidden" name="dateEnd" value="<%=(trainingActivityActual.getTrainEndDate() == null) ? new Date().getDate() : trainingActivityActual.getTrainEndDate().getDate()%>">
                            <input type="hidden" name="monthEnd" value="<%=(trainingActivityActual.getTrainEndDate() == null) ? new Date().getMonth() : trainingActivityActual.getTrainEndDate().getMonth()%>">
                            <input type="hidden" name="yearEnd" value="<%=(trainingActivityActual.getTrainEndDate() == null) ? new Date().getYear() : trainingActivityActual.getTrainEndDate().getYear()%>">
                            <% 
                            }
                            %>
                        </td>
                    </tr>
                    <tr>
                        <td><strong>Time</strong></td>
                        <td>
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
                    <tr>
                        <td><strong>Training Organizer</strong></td>
                        <td>
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
                        <td><strong>Venue</strong></td>
                        <td><input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_VENUE]%>" readonly value="<%=trainingActivityActual.getVenue()%>" class="formElemen" size="30"></td>
                    </tr>
                    <tr>
                        <td><strong>Trainer</strong></td>
                        <td><input type="text" name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_TRAINNER]%>" readonly value="<%= trainingActivityActual.getTrainner()%>" size="57" class="formElement" ></td>
                    </tr>
                    <tr>
                        <td><strong>Remark</strong></td>
                        <td><textarea name="<%=FrmTrainingActivityActual.fieldNames[FrmTrainingActivityActual.FRM_FIELD_REMARK]%>" class="formElemen" cols="50" wrap="virtual" rows="3"><%=trainingActivityActual.getRemark()%></textarea></td>
                    </tr>
                    <tr>
                        <td colspan="2">*) entry required</td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            &nbsp;
                        </td>
                    </tr>
                </table>
                    <div>&nbsp;</div>
                    <div id="confirm-delete-form">
                        Are you sure to delete? <a id="btn-confirm" href="javascript:cmdDelItem()">Yes</a>&nbsp;<a id="btn-confirm" href="javascript:cmdBatal()">No</a>
                    </div>
                    <div>&nbsp;</div>
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
                        <% String hour = String.valueOf(SessTraining.getTrainingDuration(trainingActivityActual.getStartTime(), trainingActivityActual.getEndTime())); %>
                        <table width="100%" border="0">  
                        <tr>
                            <td height="8"  nowrap>
                                <div style="padding: 5px 0px;">
                                <a style="color:#FFF" class="btn" href="javascript:setSelected()">Select All</a>
                                <a style="color:#FFF" class="btn" href="javascript:setUnselected()">Release All</a>
                                <a style="color:#FFF" class="btn" href="javascript:cmdEditAttendance('<%=oidTrainingPlan%>','<%=hour%>')">Edit Attendees</a>
                                </div>
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
                                <div style="padding: 5px 0px;">
                                <a style="color:#FFF" class="btn" href="javascript:setSelected()">Select All</a>
                                <a style="color:#FFF" class="btn" href="javascript:setUnselected()">Release All</a>
                                <a style="color:#FFF" class="btn" href="javascript:cmdEditAttendance('<%=oidTrainingPlan%>','<%=hour%>')">Edit Attendees</a> 
                                </div>
                            </td>
                            <td align="right">
                                &nbsp;                                                                                 
                            </td>
                        </tr>
                        </table>

                    <% }

                        if(iCommand == Command.LIST || iCommand == Command.BACK)
                            iCommand = Command.EDIT;
                    %>
                    <div style="border-top: 1px solid #DDD; margin-top: 21px; padding-top: 21px;"><%= ctrLine.drawImage(iCommand, iErrCode, errMsg)%></div>

                </form>
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
</html>
