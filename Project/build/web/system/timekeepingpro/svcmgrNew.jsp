<%-- 
    Document   : timekeeping & analyze
    Created on : Jun 04, 2017, 10:34:12 AM
    Author     : ARYS
--%>

<%@page import="com.dimata.harisma.utility.machine.TransManager"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>
<%@ page language = "java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "javax.comm.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.utility.machine.*" %>
<%@ page import = "com.dimata.util.*" %>
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "java.text.DateFormat" %>
<%@ page import = "java.text.SimpleDateFormat" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_TIMEKEEPING, AppObjInfo.OBJ_SERVICE_MANAGER);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>

<%!    //public static ServiceManagerTMA svrmgrTMA = new ServiceManagerTMA();
    String TKEEPING_TYPE = PstSystemProperty.getValueByName("TIMEKEEPING_TYPE");
    String attViaWeb = PstSystemProperty.getValueByName("ATTENDANCE_DATA_ACCESS_VIA_WEB");
    String odbcClasses = PstSystemProperty.getValueByName("ATT_MACHINE_ODBC_CLASS");
    //String odbcClasses = PstSystemProperty.getValueByName("ATT_MACHINE_ODBC_CLASS");
    Vector vClass = com.dimata.util.StringParser.parseGroup(odbcClasses);

%>

<%
    /* Vector vClassSysProp = com.dimata.util.StringParser.parseGroup(odbcClasses,"_","/");
     Hashtable attMachClass = new Hashtable(); 
     Vector vClass = new Vector();
     if( odbcClasses !=null && odbcClasses.length() > 0)
     {
     String className = "";//com.dimata.harisma.utility.machine."+strGrup[0];
     String user ="";
     String pwd ="";
     String dsnName="";
     String host ="";
     String port =""; 
     String[] strGroup=odbcClasses.split("_"); 
     if(strGroup!=null){
     for(int i=0;i<strGroup.length;i++){
              
     String aGroup = strGroup[i];
     if(aGroup!=null){
     String[] comps = aGroup.split("/"); 
     if(comps!=null){
                         
     try{
     String sComps = comps[0];
     if(comps.length>1){
     for(int isc=0;isc<comps.length;isc++){
     String sCompx = comps[isc];
     if(sCompx!=null && !(sCompx.split("&").length>1) && !(sCompx.split("_").length>1)){ 
     //className = "com.dimata.harisma.utility.machine."+comps[isc];
     DBMachineConfig dbC = new DBMachineConfig();
     dbC.setClassName(comps[isc]);
     dbC.setUser(user);
     dbC.setPwd(pwd);
     dbC.setDsn(dsnName);
     dbC.setHost(host);
     dbC.setPort(port);
     attMachClass.put(className,dbC );
     vClass.add(comps[isc]); 
     }
     }
     }
     String[] strGroupx=sComps.split("&");
     if(strGroupx!=null && strGroupx.length>1){
     Vector vParams = com.dimata.util.StringParser.parseGroup(comps[0],"&","=");
     if(vParams!=null && vParams.size()>0){
     for(int idP=0;idP<vParams.size();idP++){
     String[] strParam = (String[]) vParams.get(idP);
     if(strParam!=null && strParam.length>0 ){
     String paramName  = strParam[0];
     String paramValue = strParam.length >1 ? strParam[1]:""; 
     if(paramName!=null && paramName.equalsIgnoreCase("user")){
     user = paramValue;  
     } else if(paramName!=null && paramName.equalsIgnoreCase("pwd")){
     pwd = paramValue;  
     } else if(paramName!=null && paramName.equalsIgnoreCase("dsnName")){
     dsnName = paramValue;  
     }else if(paramName!=null && paramName.equalsIgnoreCase("host")){
     host = paramValue;  
     }else if(paramName!=null && paramName.equalsIgnoreCase("port")){
     port = paramValue;  
     }
     }                       
     }
     }
                          
     }else{
     className = "com.dimata.harisma.utility.machine."+comps[i];
     vClass.add(comps[i]);
     }
     }catch(Exception exc){
     System.out.println("Exception"+exc);
     }
                      
     }
     }
     }
     DBMachineConfig dbC = new DBMachineConfig();
     dbC.setClassName(className);
     dbC.setUser(user);
     dbC.setPwd(pwd);
     dbC.setDsn(dsnName);
     dbC.setHost(host);
     dbC.setPort(port);
     attMachClass.put(className,dbC );
          
            
     }  
     }*/
    /*if( vClassSysProp !=null && vClassSysProp.size() > 0)
     {
     for(int idx=0;idx<vClassSysProp.size();idx++){        
     String[] strGrup = (String[]) vClassSysProp.get(idx); 
     if(strGrup!=null && strGrup.length>0 ){
     String className = strGrup[0]; //"com.dimata.harisma.utility.machine."+strGrup[0];
     String user ="";
     String pwd ="";
     String dsnName="";
     String host ="";
     String port ="";            
     if(strGrup.length>0){
     for(int x=0;x<strGrup.length;x++){
     Vector vParams = com.dimata.util.StringParser.parseGroup(strGrup[x],"&","="); 
     if(vParams.size()>1){
     if(vParams!=null && vParams.size()>0){
     for(int i=0;i<vParams.size();i++){
     String[] strParam = (String[]) vParams.get(i);
     if(strParam!=null && strParam.length>0 ){
     String paramName  = strParam[0];
     String paramValue = strParam.length >1 ? strParam[1]:"";
     if(paramName!=null && paramName.equalsIgnoreCase("user")){
     user = paramValue;  
     } else if(paramName!=null && paramName.equalsIgnoreCase("pwd")){
     pwd = paramValue;  
     } else if(paramName!=null && paramName.equalsIgnoreCase("dsnName")){
     dsnName = paramValue;  
     }else if(paramName!=null && paramName.equalsIgnoreCase("host")){
     host = paramValue;  
     }else if(paramName!=null && paramName.equalsIgnoreCase("port")){
     port = paramValue;  
     }
     }                       
     }
     }
     }else{
     className = strGrup[1];
     }
     }
     }
     DBMachineConfig dbC = new DBMachineConfig();
     dbC.setClassName(className);
     dbC.setUser(user);
     dbC.setPwd(pwd);
     dbC.setDsn(dsnName);
     dbC.setHost(host);
     dbC.setPort(port);
     attMachClass.put(className,dbC );
     vClass.add(className);
     }
     }
     }*/



    response.setHeader("Expires", "Mon, 06 Jan 1990 00:00:01 GMT");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "nocache");


    String strStatusSvrmgrMachine = "";
    String strButtonStatusMachine = "";
    String strStatusSvrmgrPresence = "";
    String strButtonStatusPresence = "";
    String strStatusPortStatus = "";
    String strButtonPortStatus = "";
    String sTime01 = "";
    String sTime02 = "";    /*GEDE_20110901_01 {*/
//update by satrya 2013-12-18
/*Date startDate =FRMQueryString.requestDateVer3(request, "check_date_start"); 
     Date endDate=FRMQueryString.requestDateVer3(request, "check_date_end");
     int changeAutomaticManualFinish=FRMQueryString.requestInt(request, "change_automatic_manual");
     int statusParam=FRMQueryString.requestInt(request, "input_status");*/
    boolean strTransferMachineAbsensiWithParameter = false;
    try {
        strTransferMachineAbsensiWithParameter = PstSystemProperty.getValueByName("TRANSFER_ABSENSI_WITH_PARAMETER").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED) ? false : Boolean.parseBoolean(PstSystemProperty.getValueByName("TRANSFER_ABSENSI_WITH_PARAMETER"));
    } catch (Exception exc) {
        System.out.println("Exc" + exc);
    }
    TransManager svrmgrMachine = TransManager.getInstance(true);  /* }*/

//update by satrya 2013-12-19
    MesinAbsensiMessage mesinAbsensiMessage = new MesinAbsensiMessage();
    mesinAbsensiMessage = svrmgrMachine == null || svrmgrMachine.getMesinAbsensiMessage() == null ? new MesinAbsensiMessage() : svrmgrMachine.getMesinAbsensiMessage();
    Date startDate = FRMQueryString.requestDateVer3(request, "check_date_start");
    Date endDate = FRMQueryString.requestDateVer3(request, "check_date_end");
    int changeAutomaticManualFinish = FRMQueryString.requestInt(request, "change_automatic_manual");
    int statusParam = FRMQueryString.requestInt(request, "input_status");
    if (mesinAbsensiMessage.getStartDate() != null && mesinAbsensiMessage.getEndDate() != null && mesinAbsensiMessage.isUsePushStop()) {
        startDate = mesinAbsensiMessage.getStartDate();
        endDate = mesinAbsensiMessage.getEndDate();
        changeAutomaticManualFinish = mesinAbsensiMessage.getAutomaticContinueSearch();
        statusParam = mesinAbsensiMessage.getStatusHr();
    }

    if (privStart) {
        String iCommandMachine = request.getParameter("iCommandMachine");
        if (iCommandMachine != null) {
            if (iCommandMachine.equalsIgnoreCase("Run")) {
                try {
                    //update by satrya 2013-12-18
                    if (strTransferMachineAbsensiWithParameter) {
                        svrmgrMachine.startTransfer(startDate, endDate, statusParam, changeAutomaticManualFinish);
                    } else {
                        svrmgrMachine.startTransfer();

                    }
                } catch (Exception e) {
                    System.out.println("\t Exception svrmgrMachine.startTransfer() = " + e);
                }
            } else if (iCommandMachine.equalsIgnoreCase("Stop")) {
                try {
                    svrmgrMachine.stopTransfer();
                } catch (Exception e) {
                    System.out.println("\t Exception svrmgrMachine.stopWatcherMachine() = " + e);
                }
            }
        }

        if (svrmgrMachine.getStatus()) {
            strStatusSvrmgrMachine = "Run";
            strButtonStatusMachine = "Stop";
        } else {
            strStatusSvrmgrMachine = "Stop";
            strButtonStatusMachine = "Run";
        }
    }

//                                                                                                                                          AnalyzeProcess
    int iCommand = FRMQueryString.requestCommand(request);
    String sEmployeeName = FRMQueryString.requestString(request, "EMPLOYEE_NAME");
    long lDivisionOid = FRMQueryString.requestLong(request, "DIVISION_OID");
    long lDepartmentOid = FRMQueryString.requestLong(request, "DEPARTMENT_OID");
    long lSectionOid = FRMQueryString.requestLong(request, "SECTION_OID");
    long periodId = FRMQueryString.requestLong(request, "PERIODE_ID");
//String sEmployeePayroll = FRMQueryString.requestString(request, "EMPLOYEE_PAYROLL");
    String sPayrolNum = FRMQueryString.requestString(request, "PAYROL_NUMBER");
    //Arys
    String strdt = FRMQueryString.requestString(request, "strdate");
    String enddt = FRMQueryString.requestString(request, "enddate");

//    Date selectedDateFrom = FRMQueryString.requestDate(request, "check_date_start");
//    Date selectedDateTo = FRMQueryString.requestDate(request, "check_date_finish"); di disable diganti pakai datepicker

    String sEmpPayrolNum = FRMQueryString.requestString(request, "PAYROL_NUMBER_EMP");
    String sFullName = FRMQueryString.requestString(request, "EMPLOYEE_FULL_NAME");

    String chkstrdt = FRMQueryString.requestString(request, "check_strdate");
    String chkenddt = FRMQueryString.requestString(request, "check_enddate");

//    Date selectDateFrom = FRMQueryString.requestDate(request, "check_date_from");
//    Date selectDateTo = FRMQueryString.requestDate(request, "check_date_to");

    String dateFromStr = strdt + " 10:10";
    String dateToStr = enddt + " 10:10";
    String dtstr = chkstrdt + " 10:10";
    String dtend = chkenddt + " 10:10";

    DateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm");

    Date selectedDateFrom = new Date();
    Date selectedDateTo = new Date();
    Date selectDateFrom = new Date();
    Date selectDateTo = new Date();

    try {
        selectedDateFrom = sdf.parse(dateFromStr);
        selectedDateTo = sdf.parse(dateToStr);
        selectDateFrom = sdf.parse(dtstr);
        selectDateTo = sdf.parse(dtend);

    } catch (Exception ex) {
    }


//priska menambahkan menu category 20150718
    String[] stsEmpCategory = null;
    int sizeCategory = PstEmpCategory.listAll() != null ? PstEmpCategory.listAll().size() : 0;
    stsEmpCategory = new String[sizeCategory];
    String stsEmpCategorySel = "";
    int maxEmpCat = 0;
    for (int j = 0; j < sizeCategory; j++) {
        String name = "EMP_CAT_" + j;
        String val = FRMQueryString.requestString(request, name);
        stsEmpCategory[j] = val;
        if (val != null && val.length() > 0) {
            //stsEmpCategorySel.add(""+val); 
            stsEmpCategorySel = stsEmpCategorySel + val + ",";
        }
        maxEmpCat++;
    }


    String strStatusManualAttd = "";
    ManualAttandance manualAttd = ManualAttandance.getInstance(true);  /* }*/
    Date mDateFrom = new Date();
    Date mDateTo = new Date();
    if (manualAttd.getAssistant().getFromDate() != null) {
        long mlDateFrom = manualAttd.getAssistant().getFromDate().getTime() + (1000 * 60 * 60 * 24);
        mDateFrom = new Date(mlDateFrom);
    }
    if (manualAttd.getAssistant().getToDate() != null) {
        long mlDateTo = manualAttd.getAssistant().getToDate().getTime() - (1000 * 60 * 60 * 24);
        mDateTo = new Date(mlDateTo);
    }
//String strButtonStatusMachine ="";

    //update by satrya 2012-06-30
    String strMessage = "";
    if (privStart) {
        long ldtSelectedFrom = selectedDateFrom.getTime() - (1000 * 60 * 60 * 24);
        long ldtSelectedTo = selectedDateTo.getTime() + (1000 * 60 * 60 * 24);
        Date dtSelectedFrom = new Date(ldtSelectedFrom);
        Date dtSelectedTo = new Date(ldtSelectedTo);
        //Date dtSelectedFrom = new Date(selectedDateFrom.getYear(), selectedDateFrom.getMonth(), selectedDateFrom.getDate());
        //Date dtSelectedTo = new Date(selectedDateTo.getYear(), selectedDateTo.getMonth(), selectedDateTo.getDate());
        Date nowDate = new Date();
        Date dtNow = new Date(nowDate.getYear(), nowDate.getMonth(), nowDate.getDate());
        String commandManualReg = request.getParameter("commandManualReg");
        if (commandManualReg != null) {
            if (commandManualReg.equalsIgnoreCase("Run")) {
                try {
                    if (stsEmpCategorySel != "" && stsEmpCategorySel.length() > 0) {
                        stsEmpCategorySel = stsEmpCategorySel.substring(0, stsEmpCategorySel.length() - 1);
                    }
                    manualAttd.startTransfer(lDepartmentOid, sEmployeeName.trim(), dtSelectedFrom, dtSelectedTo, lSectionOid, sPayrolNum.trim(), stsEmpCategorySel, lDivisionOid);
                    //update by satrya 2012-08-19 penambahan trim
                    //manualAttd.startTransfer(lDepartmentOid,sEmployeeName,dtSelectedFrom,dtSelectedTo,lSectionOid); 


                } catch (Exception e) {
                    System.out.println("\t Exception manualAttd.startAnalisa() = " + e);
                }
            } else if (commandManualReg.equalsIgnoreCase("Stop")) {
                try {
                    manualAttd.stopTransfer();


                } catch (Exception e) {
                    System.out.println("\t Exception svrmgrMachine.stopAnalisa() = " + e);
                }
            }
        }///comand run

        if (manualAttd.getStatus()) {
            //strStatusManualAttd = "Run";
            strStatusManualAttd = "Run";
            strStatusManualAttd = "Stop";

        } else {
            strStatusManualAttd = "Stop";
            strStatusManualAttd = "Run";

        }
        //  if(strStatusManualAttd.equalsIgnoreCase("Run") ){


    } else {
        strMessage = "<div class=\"msginfo\">Selected start date or finish date should less than today ...</div>";
    }






    if (iCommand == Command.NONE) {
        Date dtNow = new Date();
        selectedDateFrom = new Date(dtNow.getYear(), dtNow.getMonth(), (dtNow.getDate() - 1));
        selectedDateTo = new Date(dtNow.getYear(), dtNow.getMonth(), (dtNow.getDate() - 1));
    }


    if (iCommand == Command.SUBMIT) {
    }

    if (iCommand == Command.DELETE) {
        //System.out.println("Reset Data In and Out : periode ID="+periodId+" And empID"+sPayrolNum);
        PstEmpSchedule.resetScheduleDataFromDateToDate(selectDateFrom, selectDateTo, sEmpPayrolNum.trim(), sFullName.trim());
    }
%>

<html>
    <head>
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

            function cmdSetStatusMachine(cmd) {
                document.frmsvcmgr.iCommandMachine.value = cmd;
                document.frmsvcmgr.action = "<%=approot%>/system/timekeepingpro/svcmgrNew.jsp";
                document.frmsvcmgr.submit();
            }

            function cmdSetStatusPresence(cmd) {
                document.frmsvcmgr.iCommandPresence.value = cmd;
                document.frmsvcmgr.action = "<%=approot%>/system/timekeepingpro/svcmgrNew.jsp";
                document.frmsvcmgr.submit();
            }
            function deptChange() 
            {
                //document.frmcheckabsence.command.value = "<//%=Command.GOTO%>";
                document.frmcheckabsence.hidden_goto_div.value = document.frmcheckabsence.DIVISION_OID.value;
                document.frmcheckabsence.hidden_goto_dept.value = document.frmcheckabsence.DEPARTMENT_OID.value;	
                document.frmcheckabsence.action = "svcmgrNew.jsp";
                document.frmcheckabsence.submit();
            }


            function cmdCheck(cmd)
            {  
                
                document.frmcheckabsence.commandManualReg.value = cmd;
                //document.frmcheckabsence.command.value="<%//= Command.SUBMIT %>";
                document.frmcheckabsence.action="svcmgrNew.jsp";    
                document.frmcheckabsence.submit();  
            }

            function cmdResetData()
            {  
                alert("This process will reset Data In an Data Out on Employee Schedule ")        ;
                document.frmcheckabsence.command.value="<%= Command.DELETE%>";	  
                document.frmcheckabsence.action="svcmgrNew.jsp";    
                document.frmcheckabsence.submit();  
            }
        </SCRIPT>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>TIME KEEPING| Dimata Hairisma</title>
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <style>
            #masterdata_icon {
                color: black;
            }
            #masterdata_icon:hover {
                color: #0088cc;
            }
            .modal-lg{
                max-width: 1200px;
                width: 100%;
            }

        </style>
        <%@ include file="../../template/css.jsp" %>
    </head>

    <body class="hold-transition skin-blue sidebar-mini sidebar-collapse">
        <input type="hidden" name="command" id="command" value="<%= Command.NONE%>">
        <input type="hidden" name="approot" id="approot" value="<%= approot%>">
        <div class="wrapper">
            <%@ include file="../../template/header.jsp" %>
            <%@ include file="../../template/sidebar.jsp" %>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        System Time Keeping & Analyze
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="../../home.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">System</li>
                        <li class="active">Time Keeping & Analyze</li>
                    </ol>
                </section>
                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-md-6">
                            <div class='box box-primary'>
                                <div class='box-header'>
                                    <h4 style="background-color:#f7f7f7; font-size: 18px; text-align: center; padding: 7px 10px; margin-top: 0;">
                                        TIME KEEPING
                                    </h4>
                                </div>
                                <div class="box-body">
                                    <!-- #BeginEditable "content" -->
                                    <% if (privStart) {%>
                                    <form name="frmsvcmgr" method="post" action="">
                                        <input type="hidden" name="iPortStatus" value="">
                                        <input type="hidden" name="iCommandMachine" value="">
                                        <input type="hidden" name="iCommandPresence" value="">
                                        <!-- update by satrya 2013-12-18-->
                                        <%if (strTransferMachineAbsensiWithParameter) {%>
                                        <fieldset>
                                            <legend>Parameter:</legend>
                                            <div>Date</div>
                                            <%
                                                Date st = new Date();
                                                st.setHours(0);
                                                st.setMinutes(0);
                                                st.setSeconds(0);
                                                Date end = new Date();
                                                end.setHours(23);
                                                end.setMinutes(59);
                                                end.setSeconds(59);
                                                String ctrTimeStart = ControlDate.drawTime("check_date_start", startDate != null ? startDate : st, "elemenForm", 24, 0, 0);
                                                String ctrTimeEnd = ControlDate.drawTime("check_date_end", endDate != null ? endDate : end, "elemenForm", 24, 0, 0);
                                            %>
                                            <div> <%=ControlDate.drawDateWithStyle("check_date_start", startDate != null ? startDate : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"") + ctrTimeStart%>  
                                                : <%=ControlDate.drawDateWithStyle("check_date_end", endDate != null ? endDate : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"") + ctrTimeEnd%>  
                                            </div>

                                            <div>Status At Mesin Absence</div>
                                            <%
                                                Vector value = new Vector();
                                                Vector key = new Vector();
                                                value.add("" + 0);
                                                value.add("" + 1);
                                                value.add("" + 2);
                                                key.add("New");
                                                key.add("Prosess");
                                                key.add("All");
                                                String cheked = mesinAbsensiMessage.isUsePushStop() ? changeAutomaticManualFinish == 0 ? "" : "checked=\"checked\"" : changeAutomaticManualFinish == 0 ? "" : "checked=\"checked\"";
                                            %>
                                            <div>: <%=ControlCombo.draw("input_status", null, "" + statusParam, value, key)%>
                                                <input type="checkbox"  class="largerCheckbox" name="change_automatic_manual" value="1" <%=cheked%>/>
                                                <label>Change to automatic mode after manual mode finished </label>
                                            </div>
                                        </fieldset>
                                </div>
                                <%}%>


                                <div>
                                    Status of Service Manager for Machine is <b><%=strStatusSvrmgrMachine%></b>.<br><br>
                                    <input id="btn" class="btn btn-success fa btn-md" data-placement="right" data-toggle="tooltip" title="Click this button to <%=strButtonStatusMachine%> the Machine Service Manager." type="button" name="btnStatusMachine" value="<%=strButtonStatusMachine%>" onClick="javascript:cmdSetStatusMachine('<%=strButtonStatusMachine%>');"> <br><br>
                                </div>
                                <%
                                    if (attViaWeb != null && attViaWeb.compareTo("1") == 0) {
                                        if (vClass != null && vClass.size() > 0) {
                                            for (int i = 0; i < vClass.size(); i++) {
                                                String[] classA = (String[]) vClass.get(i);
                                                svrmgrMachine.addTxtProcessClass("com.dimata.harisma.utility.machine." + classA[0]);
                                            }
                                        } else {
                                            out.println("ATT_MACHINE_ODBC_CLASS value=" + odbcClasses + " => set to right class ");
                                        }
                                    } else {
                                        out.println("ATTENDANCE_DATA_ACCESS_VIA_WEB value=" + attViaWeb + " => Attendance database  is set to be transfered by other interface");
                                    }


                                    if (svrmgrMachine.getTxtProcessClassSize() > 0) {
                                        for (int i = 0; i < svrmgrMachine.getTxtProcessClassSize(); i++) {



                                %>  
                                <div>
                                    Machine Type: <%=svrmgrMachine.getTxtProcessClass(i)%> <br>
                                    Total Data:   <%=svrmgrMachine.getTotalRecord(i)%> <br>
                                    Data Transfered:   <%=svrmgrMachine.getProcentTransfer(i)%> 
                                </div>

                                <div>
                                    <img alt=""  src="../../images/loading.gif" height="8" width="<%=(svrmgrMachine.getTotalRecord(i) == 0 ? "" : ("" + svrmgrMachine.getProcentTransfer(i) * 300 / svrmgrMachine.getTotalRecord(i)))%>" > <%=(svrmgrMachine.getTotalRecord(i) == 0 ? "" : ("" + svrmgrMachine.getProcentTransfer(i) * 100 / svrmgrMachine.getTotalRecord(i)))%>% 
                                    <BR>
                                </div>

                                <strong>Massage :</strong> <%=svrmgrMachine.getMessageTransfer(i)%>


                                <%  }
                                    }%>                                       

                                <div>
                                    Analyzing Data Att. Machine <BR>
                                    Total Data:   <%=svrmgrMachine.getTotalRecordAssistant()%> <BR>
                                    Data Transfered:   <%=svrmgrMachine.getProcentTransferAssistant()%> 
                                </div>
                                <div>
                                    <img alt=""  src="../../images/loading.gif" height="8" width="<%=(svrmgrMachine.getTotalRecordAssistant() == 0 ? "" : ("" + svrmgrMachine.getProcentTransferAssistant() * 300 / svrmgrMachine.getTotalRecordAssistant()))%>" > <%=(svrmgrMachine.getTotalRecordAssistant() == 0 ? "" : ("" + svrmgrMachine.getProcentTransferAssistant() * 100 / svrmgrMachine.getTotalRecordAssistant()))%>% 
                                    <BR>
                                </div>

                                <strong>Massage :</strong> <%=svrmgrMachine.getTransferAssistantMessage()%>


                                <div>
                                    Analyzing Data Status <BR>
                                    Total Days:   <%=svrmgrMachine.getTotalAnalyzeRecordAssistant()%> <BR>
                                    Data Transfered Finish Days:   <%=svrmgrMachine.getProcentAnalyzeTransferAssistant()%> 
                                </div>
                                <div>
                                    <img alt=""  src="../../images/loading.gif" height="8" width="<%=(svrmgrMachine.getTotalAnalyzeRecordAssistant() == 0 ? "" : ("" + svrmgrMachine.getProcentAnalyzeTransferAssistant() * 300 / svrmgrMachine.getTotalAnalyzeRecordAssistant()))%>" > <%=(svrmgrMachine.getTotalAnalyzeRecordAssistant() == 0 ? "" : ("" + svrmgrMachine.getProcentAnalyzeTransferAssistant() * 100 / svrmgrMachine.getTotalAnalyzeRecordAssistant()))%>% 
                                    <BR>
                                </div>

                                <strong>Massage :</strong> <%=svrmgrMachine.getTransferAnalizeAssistantMessage()%>

                                </table>
                                </form>
                                <%
                                } else {
                                %>
                                <div align="center">You do not have sufficient privilege to access this page.</div>
                                <% }%>
                            </div>
                        </div>
                    </div>
                    <!-- ./col -->
                    <!-- /.row -->

                    <div class="col-md-6">
                        <!-- Custom Tabs (Pulled to the right) -->
                        <div class="nav-tabs-custom">
                            <ul class="nav nav-tabs pull-right">
                                <li class="active"><a href="#tab_1-1" data-toggle="tab"><strong> ANALYZE </strong></a></li>
                                <li><a href="#tab_2-2" data-toggle="tab"><strong> RESET </strong></a></li>

                                <li class="pull-left header">Analyze Presence <i class="fa fa-clock-o" ></i></li>
                            </ul>
                            <div class="tab-content">


                                <div class="tab-pane active" id="tab_1-1">
                                    <form name="frmcheckabsence" method="post" action="">
                                        <input type="hidden" name="command" value="">
                                        <input type="hidden" name="commandManualReg" value="">
                                        <input type="hidden" name="hidden_goto_div" value="<%=lDivisionOid%>">	
                                        <input type="hidden" name="hidden_goto_dept" value="<%=lDepartmentOid%>">
                                        <table width="100%" border="0" cellpadding="2" cellspacing="2" bgcolor="#00FF00">
                                            <tr> 

                                                <!--                                            <div class="alert alert-info alert-dismissible">
                                                                                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                                                                                            <h4><i class="icon fa fa-info"></i>This process 
                                                                                                is used to :</h4>
                                                                                                &nbsp;&nbsp;&nbsp;- 
                                                                                                Prepare employee's attendance data 
                                                                                                for &quot;Absence&quot; and &quot;Lateness&quot; 
                                                                                                calculation process.<br>
                                                                                                &nbsp;&nbsp;&nbsp;- 
                                                                                                Prepare employee's attendance data 
                                                                                                for &quot;Absence&quot; and &quot;Lateness&quot; 
                                                                                                calculation process.<br>
                                                                                                &nbsp;&nbsp;&nbsp;- 
                                                                                                Analyze employee's absence and update 
                                                                                                their status to be &quot;ABSENCE&quot;.<br>
                                                                                                &nbsp;&nbsp;&nbsp;- 
                                                                                                Analyze employee's lateness and update 
                                                                                                their status to be &quot;LATE&quot;.
                                                                                            <div></div></div>-->

                                            </tr>

                                            <tr> 
                                                <td colspan="3" valign="top">&nbsp;</td>
                                            </tr>
                                            <!-- update by satrya 2012-08-01 -->
                                            <tr>
                                            <div class="form-group">

                                                <div class="form-group">
                                                    <label>Process employee's attendance data :</label>
                                                    <BR><img alt=""  src="<%=approot%>/images/loading.gif" height="10" width="<%=(manualAttd.getTotalRecordAssistant() == 0 ? "" : ("" + manualAttd.getProcentTransferAssistant() * 300 / manualAttd.getTotalRecordAssistant()))%>" > <%=(manualAttd.getTotalRecordAssistant() == 0 ? "" : ("" + manualAttd.getProcentTransferAssistant() * 100 / manualAttd.getTotalRecordAssistant()))%>%
                                                    <BR><span class="description">Message : <%=manualAttd.getTransferAssistantManualMessage()%></span>
                                                </div> 
                                                <div class="form-group">
                                                    <label>Analyze employee's presence status :</label>
                                                    <BR><img alt=""  src="<%=approot%>/images/loading.gif" height="10" width="<%=(manualAttd.getTotalRecordAssistantAbsence() == 0 ? "" : ("" + manualAttd.getProcentTransferAssistantAbsence() * 300 / manualAttd.getTotalRecordAssistantAbsence()))%>" > <%=(manualAttd.getTotalRecordAssistantAbsence() == 0 ? "" : ("" + manualAttd.getProcentTransferAssistantAbsence() * 100 / manualAttd.getTotalRecordAssistantAbsence()))%>%
                                                    <BR><span class="description">Message : <%=manualAttd.getCheckProcessAbsenceMessage()%></span>
                                                </div>
                                                <hr>   
                                                <div class="form-group">
                                                    <label>Payroll Numb :</label>
                                                    <input type="text" name="PAYROL_NUMBER" placeholder="payroll number..."  value="<%=!manualAttd.getStatus() ? sPayrolNum : manualAttd.getAssistant().getPayrolNum()%>" class="form-control" size="40"> 
                                                </div>
                                                </tr>
                                                <tr> 
                                                <div class="form-group">
                                                    <label>Employee :</label>
                                                    <input type="text" name="EMPLOYEE_NAME" placeholder="input name..."  value="<%=!manualAttd.getStatus() ? sEmployeeName : manualAttd.getAssistant().getFullName()%>" class="form-control" size="40"> 
                                                </div>
                                                </td>
                                                </tr>
                                                <tr> 
                                                <div class="form-group">
                                                    <label>Division :</label>
                                                    <%
                                                        Vector div_value = new Vector(1, 1);
                                                        Vector div_key = new Vector(1, 1);
                                                        div_value.add("0");
                                                        div_key.add("All Div ...");
                                                        Vector listDiv = PstDivision.list(0, 0, "", " DIVISION ");
                                                        for (int i = 0; i < listDiv.size(); i++) {
                                                            Division div = (Division) listDiv.get(i);
                                                            div_key.add(div.getDivision());
                                                            div_value.add(String.valueOf(div.getOID()));
                                                        }
                                                    %>
                                                    <%= ControlCombo.draw("DIVISION_OID", "form-control", null, "" + (!manualAttd.getStatus() ? lDivisionOid : manualAttd.getAssistant().getOidDivision()), div_value, div_key, "onchange=\"javascript:deptChange();\"")%> 
                                                </div>


                                                </tr>
                                                <tr> 
                                                <div class="form-group">
                                                    <label>Department :</label>
                                                    <%
                                                        Vector dept_value = new Vector(1, 1);
                                                        Vector dept_key = new Vector(1, 1);
                                                        dept_value.add("0");
                                                        dept_key.add("All Department ...");
                                                        String where = "";
                                                        if (lDivisionOid > 0) {
                                                            where = " hr_department." + PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + " = " + lDivisionOid;
                                                        }
                                                        Vector listDept = PstDepartment.list(0, 0, where, " DEPARTMENT ");
                                                        for (int i = 0; i < listDept.size(); i++) {
                                                            Department dept = (Department) listDept.get(i);
                                                            dept_key.add(dept.getDepartment());
                                                            dept_value.add(String.valueOf(dept.getOID()));
                                                        }
                                                    %>
                                                    <%= ControlCombo.draw("DEPARTMENT_OID", "form-control", null, "" + (!manualAttd.getStatus() ? lDepartmentOid : manualAttd.getAssistant().getOidDepartement()), dept_value, dept_key, "onchange=\"javascript:deptChange();\"")%>
                                                </div>
                                                </tr>
                                                <%
                                                    if (lDepartmentOid != 0) {
                                                %>
                                                <tr> 
                                                <div class="form-group">
                                                    <label>Department :</label>
                                                    <%
                                                        String whereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + " = " + lDepartmentOid;
                                                        String orderSec = PstSection.fieldNames[PstSection.FLD_SECTION];
                                                        Vector sec_value = new Vector(1, 1);
                                                        Vector sec_key = new Vector(1, 1);
                                                        sec_value.add("0");
                                                        sec_key.add("All Section ...");
                                                        Vector listSec = PstSection.list(0, 0, whereSec, orderSec);
                                                        for (int i = 0; i < listSec.size(); i++) {
                                                            Section sec = (Section) listSec.get(i);
                                                            sec_key.add(sec.getSection());
                                                            sec_value.add(String.valueOf(sec.getOID()));
                                                        }
                                                    %>
                                                    <%= ControlCombo.draw("SECTION_OID", "formElemen", null, "" + (manualAttd.getAssistant().getOidsection() != 0 ? manualAttd.getAssistant().getOidsection() : lSectionOid), sec_value, sec_key, "")%>
                                                </div>
                                                </tr>
                                                <%
                                                    }
                                                %>
                                                <tr> 
                                                <div class="form-group">
                                                    <label>Start Date & End Date</label>
                                                    <div class="input-group">
                                                        <input type="text" name="strdate"  id="strdate" class="form-control datepicker" value="07-07-2017">
                                                        <div class="input-group-addon">
                                                            To
                                                        </div>
                                                        <input type="text" name="enddate" id="enddate" class="form-control datepicker" value="07-07-2017">
                                                    </div>
                                                </div>
                                                </tr>
                                                <tr>
                                                <div class="form-group">
                                                    <label>Employee Category</label><br>
                                                    <%
                                                        int numCol = 5;
                                                        boolean createTr = false;
                                                        int numOfColCreated = 0;
                                                        Hashtable hashGetListEmpSel = new Hashtable();
                                                        if (stsEmpCategorySel != null && stsEmpCategorySel.length() > 0) {
                                                            stsEmpCategorySel = stsEmpCategorySel.substring(0, stsEmpCategorySel.length() - 1);
                                                            String whereClause = PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY_ID] + " IN (" + stsEmpCategorySel + ")";
                                                            hashGetListEmpSel = PstEmpCategory.getHashListEmpSchedule(0, 0, whereClause, "");
                                                        }
                                                        Vector vObjek = PstEmpCategory.listAll();
                                                        //String checked="unchecked"; 
                                                        long oidEmpCat = 0;
                                                        for (int tc = 0; (vObjek != null) && (tc < vObjek.size()); tc++) {
                                                            EmpCategory empCategory = (EmpCategory) vObjek.get(tc);
                                                            oidEmpCat = 0;

                                                            if (empCategory != null) {

                                                                if (hashGetListEmpSel != null && hashGetListEmpSel.size() > 0 && hashGetListEmpSel.get(empCategory.getOID()) != null) {
                                                                    EmpCategory empCategoryHas = (EmpCategory) hashGetListEmpSel.get(empCategory.getOID());
                                                                    oidEmpCat = empCategoryHas.getOID();

                                                                }

                                                                if (oidEmpCat != 0) {
                                                    %>
                                                    <input type="checkbox" name="<%="EMP_CAT_" + tc%>"  checked="checked" value="<%=empCategory.getOID()%>"  />
                                                    <%=empCategory.getEmpCategory()%> &nbsp;&nbsp;
                                                    <%} else {%>
                                                    <input type="checkbox" name="<%="EMP_CAT_" + tc%>"  value="<%=empCategory.getOID()%>"  />
                                                    <%=empCategory.getEmpCategory()%> &nbsp;&nbsp;
                                                    <%}%>
                                                    <%

                                                            }
                                                        }

                                                    %>

                                                </div>
                                                </tr>


                                                <tr> 
                                                <div>
                                                    <%if (privStart) {%>
                                                    <input id="btn" name="btnStart" class="btn btn-success fa btn-md" data-placement="right" data-toggle="tooltip" title="Click this button to <%=strStatusManualAttd%> the Attendance Manual " type="button" name="btnStatusMachine" value="<%=strStatusManualAttd%> Analyze" onClick="javascript:cmdCheck('<%=strStatusManualAttd%>');">

                                                    <%}%>
                                                </div>
                                                </tr>
                                                <%
                                                    if (true) {
                                                %>
                                        </table>
                                </div>

                                <div class="tab-pane" id="tab_2-2">
                                    <!--                            <form name="frmcheckabsence" method="post" action="">
                                                                    <input type="hidden" name="command" value="">
                                                                    <input type="hidden" name="commandManualReg" value="">
                                                                    <input type="hidden" name="hidden_goto_div" value="<%=lDivisionOid%>">	
                                                                    <input type="hidden" name="hidden_goto_dept" value="<%=lDepartmentOid%>">-->
                                    <table  width="100%" border="0" cellspacing="1" cellpadding="1" align="center" bgcolor="#FFFF99">
                                        <tr><td></td></tr> 
                                        <!--                                    <tr> 
                                                                                <td colspan="3" valign="top"><b>. : : RESET DATA  IN, OUT AND STATUS : : .</b></td>
                                                                                <td colspan="3" valign="top">&nbsp;</td>
                                                                                <td colspan="3" valign="top">&nbsp;</td>
                                                                            </tr>	
                                                                            <tr> 
                                                                                <td colspan="3" valign="top">This process 
                                                                                    is used to :</td>
                                                                            </tr>
                                                                            <tr> 
                                                                                <td colspan="3" valign="top">&nbsp;&nbsp;&nbsp;- 
                                                                                    Reset Data In and Data Out and Reset 
                                                                                    Status of Selected Employee and Period</td>
                                                                            </tr>
                                                                            <tr> 
                                                                                <td colspan="3" valign="top">&nbsp;&nbsp;&nbsp;- 
                                                                                    After this process you should process 
                                                                                    Re-Update the Data In and Out by selecting 
                                                                                    option below</td>
                                                                            </tr>-->
                                        <tr> 
                                            <td colspan="3" valign="top">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                        <div class="form-group">
                                            <label>Payroll Number :</label>
                                            <input type="text" name="PAYROL_NUMBER_EMP"  value="<%= sEmpPayrolNum%>" class="form-control" size="40"> 
                                        </div>
                                        </td>
                                        </tr>
                                        <tr> 
                                        <div class="form-group">
                                            <label>Employee Name :</label>
                                            <input type="text" name="EMPLOYEE_FULL_NAME"  value="<%=sFullName%>" class="form-control" size="40">
                                        </div>
                                        </tr>
                                        <tr> 
                                        <d<div class="form-group">
                                                <label>Start Date & End Date</label>
                                                <div class="input-group">
                                                    <input type="text" name="check_strdate"  id="strdate" class="form-control datepicker" value="07-07-2017">
                                                    <div class="input-group-addon">
                                                        To
                                                    </div>
                                                    <input type="text" name="check_enddate" id="enddate" class="form-control datepicker" value="07-07-2017">
                                                </div>
                                            </div>
                                            </tr>
                                            <tr> 
                                                <td valign="top"> 
                                                    <%if (privStart) {%>

                                                    <input id="btn" data-placement="right" data-toggle="tooltip" title="" name="btnStatusMachine" value=" Reset " onclick="javascript:cmdResetData()" data-original-title="Click this button to Reset Data In and Data Out and Reset Status" class="btn btn-success fa btn-md" type="button">
                                                    <%}%>
                                                </td>
                                            </tr>

                                            <tr> 
                                                <td colspan="3" valign="top"><%=strMessage%> 
                                                </td>
                                            </tr>
                                            <%
                                                }
                                            %>
                                    </table>
                                    <!--</form>-->
                                </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
            <%@include file="../../template/plugin.jsp" %>
            <%@ include file="../../template/footer.jsp" %>
            <script language="JavaScript">
                $(document).ready(function(){
                    $(function(){
                        $('.datepicker').datepicker({
                            format: 'mm-dd-yyyy',
                            autoclose: true
                        });
                    });
                 
                })
            </script>

            <%if (svrmgrMachine.running) {%>
            <script language="JavaScript">
        
                $("#btn").addClass("btn-danger");
                //enter refresh time in "minutes:seconds" Minutes should range from 0 to inifinity. Seconds should range from 0 to 59
                var limit="0:08"
                if (document.images){
                    var parselimit=limit.split(":")
                    parselimit=parselimit[0]*60+parselimit[1]*1
                }
                function beginrefresh(){
                    if (!document.images)
                        return

                    if (parselimit==1)
                        window.location = window.location.href //agar tidak memunculkan pesan confirmasi
                    else{
                        parselimit-=1
                        //curmin=Math.floor(parselimit/60)
                        //cursec=parselimit%60
                        //if (curmin!=0)
                        //curtime=curmin+" minutes and "+cursec+" seconds left until page refresh!"

                        setTimeout("beginrefresh()",100)
                    }
                }

                window.onload=beginrefresh
                //-->
            </script>
            <%}%>

            <%if (manualAttd.getStatus()) {%>
            <script language="JavaScript">

                //enter refresh time in "minutes:seconds" Minutes should range from 0 to inifinity. Seconds should range from 0 to 59
                var limit="0:08"
                if (document.images){
                    var parselimit=limit.split(":")
                    parselimit=parselimit[0]*60+parselimit[1]*1
                }
                function beginrefresh(){
                    if (!document.images)
                        return

                    if (parselimit==1)
                        window.location = window.location.href //agar tidak memunculkan pesan confirmasi
                    else{
                        parselimit-=1
                        //curmin=Math.floor(parselimit/60)
                        //cursec=parselimit%60
                        //if (curmin!=0)
                        //curtime=curmin+" minutes and "+cursec+" seconds left until page refresh!"

                        setTimeout("beginrefresh()",100)
                    }
                }

                window.onload=beginrefresh
                //-->
            </script>
            <%}%>
        </div><!-- ./wrapper -->
    </body>
</html>
