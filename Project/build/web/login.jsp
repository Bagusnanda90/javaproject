
<%@page import="com.dimata.harisma.entity.log.PstLogSysHistory"%>
<%@page import="com.dimata.harisma.entity.log.LogSysHistory"%>
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.leave.I_Leave"%>
<%@ page language="java" %>
<%@ page import="java.util.*, com.dimata.util.Command" %>
<%@ page import="com.dimata.harisma.session.admin.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.harisma.session.leave.SessLeaveApplication"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="main/javainit.jsp"%>

<%!    
    final static int CMD_NONE = 0;
    final static int CMD_LOGIN = 1;
    final static int MAX_SESSION_IDLE = 100000;
%>

<%
    response.setHeader("Expires", "Mon, 06 Jan 1990 00:00:01 GMT");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "nocache");
%>

<%
I_Leave leaveConfig=null; 
try{
leaveConfig = (I_Leave)(Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance()); 

leaveConfig.setALEntitleBy(I_Leave.AL_ENTITLE_BY_COMMENCING);          
}catch(Exception except){
System.out.println("Exception"+except);
}
    
    //int machineLogin = Integer.parseInt((request.getParameter("machineLogin") == null) ? "0" : request.getParameter("machineLogin"));
    //long machineLoginEmployeeId = Long.parseLong((request.getParameter("machineLoginEmployeeId") == null) ? "0" : request.getParameter("machineLoginEmployeeId"));
    long machineLoginEmployeeId = 0;
    String loginIDX = FRMQueryString.requestString(request, "login_id");
    if(loginIDX.indexOf("DBN")!=-1){
       String machineLoginEmployeeIdString = loginIDX.replace("DBN", "");
       machineLoginEmployeeId = Long.parseLong(machineLoginEmployeeIdString);
    }
    
    int iCommand = Integer.parseInt((request.getParameter("command") == null) ? "0" : request.getParameter("command"));
    String page_name = FRMQueryString.requestString(request, "page_name");
    String page_command = FRMQueryString.requestString(request, "page_command");
    String data_oid = FRMQueryString.requestString(request, "data_oid");
    String employee_oid = FRMQueryString.requestString(request, "employee_oid");
    //if (machineLogin == 1){
    //     session.putValue("machineLogin", String.valueOf(machineLogin));
    //}
    
    String k = request.getRequestURI().toString();
    int dologin = SessUserSession.DO_LOGIN_OK;
    if (iCommand == CMD_LOGIN || (machineLoginEmployeeId != 0) ) {
        
        String loginID = FRMQueryString.requestString(request, "login_id");
        String passwd = FRMQueryString.requestString(request, "pass_wd");
        int appLanguage  = FRMQueryString.requestInt(request,"app_language"); 
        boolean viewMin = FRMQueryString.requestBoolean(request, "viewform");
        boolean WOBirth = FRMQueryString.requestBoolean(request, "viewBirth");
        String remoteIP = request.getRemoteAddr();
        if (machineLoginEmployeeId != 0){
            try{
                String empMachine = String.valueOf(machineLoginEmployeeId);
            long employeeFromMachine = PstEmployee.getEmployeeByBarcode(empMachine);
            AppUser appUserMachineLogin = new AppUser();               
            try { appUserMachineLogin = PstAppUser.getByEmployeeId(String.valueOf(employeeFromMachine));
            
            } catch (Exception e){}
            
           // if (appUserMachineLogin != null || appUserMachineLogin.getEmployeeId() != 0 ){
           //     loginID = appUserMachineLogin.getLoginId();
           //     passwd = appUserMachineLogin.getPassword();    
           // } else {
                Employee employeeMachineLogin = PstEmployee.fetchExc(employeeFromMachine);
                loginID = employeeMachineLogin.getEmployeeNum();
                passwd = employeeMachineLogin.getEmpPin();
           // }
            appLanguage = 1;
            viewMin = false;
            WOBirth = false;
            remoteIP = "0:0:0:0:0:0:0:1";
            } catch (Exception e){}
            
            
        }
        
        
        session.putValue("VIEW_MINIMUM", new Boolean(viewMin));
        session.putValue("VIEW_BIRTHDAY", new Boolean(WOBirth));


        SessUserSession userSess = new SessUserSession(remoteIP);
        dologin = userSess.doLogin(loginID, passwd);
        if (dologin == SessUserSession.DO_LOGIN_OK) {
            //update by satrya 2012-11-1
           session.removeValue(SessLeaveApplication.SESS_SRC_LEAVE_APPLICATION);
           //update by satrya 2013-08-12
           //agar langsung menjalankan penarikan jika ada salah satu karyawan yg login
           //com.dimata.harisma.utility.machine.TransManager.setRunningWithLoginApp(true);
           //end
          //priska 20150607
           session.putValue("APPLICATION_LANGUAGE", String.valueOf(appLanguage));
            String strLang = "";
            if(session.getValue("APPLICATION_LANGUAGE")!=null){
                    strLang = String.valueOf(session.getValue("APPLICATION_LANGUAGE"));
            }
            appLanguage = (strLang!=null && strLang.length()>0) ? Integer.parseInt(strLang) : 0;
            session.putValue("Languange", String.valueOf(appLanguage));
            I_Dictionary dictionary = (I_Dictionary) Class.forName(I_Dictionary.DICTIONARYCLASS[appLanguage]).newInstance();
            userSess.setUserDictionary(dictionary);
           
            session.setMaxInactiveInterval(MAX_SESSION_IDLE);
            userSess.setUserHrdData();
            session.setMaxInactiveInterval(MAX_SESSION_IDLE);
            session.putValue(SessUserSession.HTTP_SESSION_NAME, userSess);
            userSess = (SessUserSession) session.getValue(SessUserSession.HTTP_SESSION_NAME);
            try{
                String val = PstSystemProperty.getValueByName("OVERTIME_ROUND_START");
                String val1 = PstSystemProperty.getValueByName("MIN_OVERTM_DURATION_ASST_MAN");
                if(val!=null && val.length()>0){                   
                   com.dimata.harisma.session.payroll.SessOvertime.setOvertimeRoundStart(Integer.parseInt(val));
                }
                if(val1!=null && val1.length()>0){
                    com.dimata.harisma.session.payroll.SessOvertime.setOvertimeMinimumAsstManager(Double.parseDouble(val1));
                }
            }catch(Exception exc1){                
            }
            try{
                String val = PstSystemProperty.getValueByName("OVERTIME_ROUND_TO");
                if(val!=null && val.length()>0){                   
                   com.dimata.harisma.session.payroll.SessOvertime.setOvertimeRoundTo(Integer.parseInt(val));
                }
            }catch(Exception exc1){                
            }
            
            
            if (page_name != null && page_name.length() > 1) {
                if (page_name.equals("overtime.jsp")) {
%>
<jsp:forward page="payroll/overtimeform/overtime.jsp"> 
    <jsp:param name="command" value="<%=page_command%>" />	                
    <jsp:param name="prev_command" value="<%=page_command%>" />
    <jsp:param name="overtime_oid" value="<%=data_oid%>" />
</jsp:forward>	
<%
} else {
    if (page_name.equals("leave_app_edit.jsp")) {
%>
<jsp:forward page="employee/leave/leave_app_edit.jsp"> 
    <jsp:param name="command" value="<%=page_command%>" />	                
    <jsp:param name="prev_command" value="<%=page_command%>" />
    <jsp:param name="FRM_FLD_LEAVE_APPLICATION_ID" value="<%=data_oid%>" />
    <jsp:param name="oid_employee" value="<%=employee_oid%>" />
</jsp:forward>	
<%
} else {
    if (page_name.equals("home.jsp")) {
%>
<jsp:forward page="home.jsp"> 
    <jsp:param name="command" value="<%=page_command%>" />	                                                        
</jsp:forward>	
<%
} else {
    if (page_name.equals("svcmgr.jsp")) {
%>
<jsp:forward page="system/timekeepingpro/svcmgr.jsp"> 
    <jsp:param name="command" value="<%=page_command%>" />	                
    <jsp:param name="prev_command" value="<%=page_command%>" />                                                        
</jsp:forward>	
<%
                            } else {
                            }



                        }
                    }

                }


            }
            /*
            if(userSess==null)
            {
            System.out.println("userSession after login ----------------->null");
            }		
            else
            {
            System.out.println("userSession after login ----------------->OK");
            }
             */
        } else {

            /* check payroll & pin if ok get Employee ID
            if EmpID!=0 {
            login as username = employee , password : ********
            set employee employee ID on userSession with as currently login  employee
                                    
                                    
            session.setMaxInactiveInterval(MAX_SESSION_IDLE);
            userSess.setEmployeeId(EmpID);
            userSess.setUserHrdData();session.setMaxInactiveInterval(MAX_SESSION_IDLE);
            session.putValue(SessUserSession.HTTP_SESSION_NAME, userSess);
            userSess = (SessUserSession)session.getValue(SessUserSession.HTTP_SESSION_NAME); 
            dologin=SessUserSession.DO_LOGIN_OK;   
            } */
            //if (machineLogin == 1){
            //session.putValue("machineLogin", String.valueOf(machineLogin));
            //}
            Employee objemployee = new Employee();

            String where = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " = '" + loginID + "' AND "
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN] + " = '" + passwd + "'";

            Vector listEmployee = new Vector();

            listEmployee = PstEmployee.list(0, 0, where, null);

            if (listEmployee != null && listEmployee.size() > 0) {

                objemployee = (Employee) listEmployee.get(0);
                dologin = userSess.doLogin("Employee", "dsj2009");

                //priska 20150607
                   session.putValue("APPLICATION_LANGUAGE", String.valueOf(appLanguage));
                    String strLang = "";
                    if(session.getValue("APPLICATION_LANGUAGE")!=null){
                            strLang = String.valueOf(session.getValue("APPLICATION_LANGUAGE"));
                    }
                    appLanguage = (strLang!=null && strLang.length()>0) ? Integer.parseInt(strLang) : 0;
                    session.putValue("Languange", String.valueOf(appLanguage));
                    I_Dictionary dictionary = (I_Dictionary) Class.forName(I_Dictionary.DICTIONARYCLASS[appLanguage]).newInstance();
                    userSess.setUserDictionary(dictionary);
                
                
                session.setMaxInactiveInterval(MAX_SESSION_IDLE);
                userSess.setEmployee(objemployee.getOID());
                userSess.setUserHrdData();
                session.setMaxInactiveInterval(MAX_SESSION_IDLE);
                session.putValue(SessUserSession.HTTP_SESSION_NAME, userSess);
                userSess = (SessUserSession) session.getValue(SessUserSession.HTTP_SESSION_NAME);
                
                /* Update By Hendra Putu | 2016-02-26 | Ngoding di BPD */
                AppUser empUser = userSess.getAppUser();
                try {
                    
                    LogSysHistory logSysHistory = new LogSysHistory();

                    Date nowDate = new Date();
                    logSysHistory.setLogDocumentId(0);
                    logSysHistory.setLogUserId(empUser.getOID());
                    logSysHistory.setApproverId(empUser.getOID());
                    logSysHistory.setApproveDate(nowDate);
                    logSysHistory.setLogLoginName(objemployee.getFullName());
                    logSysHistory.setLogDocumentNumber("");
                    logSysHistory.setLogDocumentType("-"); //entity
                    logSysHistory.setLogUserAction("LOGIN"); // command
                    logSysHistory.setLogOpenUrl("-"); // locate jsp
                    logSysHistory.setLogUpdateDate(nowDate);
                    logSysHistory.setLogApplication("-"); // interface
                    logSysHistory.setLogDetail("-"); // apa yang dirubah
                    logSysHistory.setLogStatus(0);
                    logSysHistory.setLogPrev("-");
                    logSysHistory.setLogCurr("-");
                    logSysHistory.setLogModule("Home");

                    PstLogSysHistory.insertExc(logSysHistory);
                } catch(Exception e) {
                    System.out.println(""+e.toString());
                }
                /*===============================================*/
                
            }

        }
    }

    int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_LOGIN, AppObjInfo.G2_LOGIN, AppObjInfo.OBJ_LOGIN_LOGIN);
%>
<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Dashboard | Dimata Hairisma</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.5 --> 
        <link rel="stylesheet" href="<%= approot%>/assets/bootstrap/css/bootstrap.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="<%= approot%>/assets/font-awesome/4.6.1/css/font-awesome.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="<%= approot%>/assets/ionicons/2.0.1/css/ionicons.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="<%= approot%>/assets/dist/css/AdminLTE.css">
        <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="<%= approot%>/assets/dist/css/skins/skin-blue.css">
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="<%= approot%>/assets/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="<%= approot%>/assets/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <style>
            #masterdata_icon {
                color: black;
            }
            #masterdata_icon:hover {
                color: #0088cc;
            }
        </style>
        <script language="JavaScript">
            window.status="";

            function fnTrapKD()
            {
                //if (event.keyCode == 13) {
                //     document.all.aSearch.focus();
                //     cmdLogin();
                //}
            }

            function cmdLogin()	
            {
                document.frmLogin.action = "login.jsp";
                document.frmLogin.submit();
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
        </head>
    <body class="hold-transition login-page">
        <!-- <%=com.dimata.qdep.db.DBHandler.CONFIG_FILE%> -->
        <div class="login-box">
            <div class="login-logo">
                <a href="http://www.dimata.com"><img src="<%= approot%>/imgcompany/Logo Dimata.png" width="200px" alt="Company Logo" /></a>
            </div>
            <div class="login-box-body">
                <p class="login-box-msg">User Login</p>
                <form name="frmLogin" action="" onsubmit="window.status=''">
                    <input type="hidden" name="command" value="<%=CMD_LOGIN%>">
                    <input type="hidden" name="page_name" value="<%=page_name%>">
                    <input type="hidden" name="page_command" value="<%=page_command%>">
                    <input type="hidden" name="data_oid" value="<%=data_oid%>">
                    <input type="hidden" name="employee_oid" value="<%=employee_oid%>">
                            <%
                                if ((iCommand == CMD_LOGIN) && (dologin == SessUserSession.DO_LOGIN_OK)) {
                                    
                            %>
                            <script language="JavaScript">
                                    //window.location = "home.jsp"
                                    window.location.assign("home.jsp?menu=home.jsp")
                            </script>
                            <%              }
                            %>
                    <div class="form-group has-feedback">
                        <input type="text" class="form-control" name="login_id" id="login_id" placeholder="Login ID">
                        <span class="glyphicon glyphicon-user form-control-feedback"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <input type="password" class="form-control" name="pass_wd" id="pass_wd" placeholder="Password">
                        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                    </div>
                    <div class="form-group">
                        <label>Language</label>
                            <%
                                long DEFAULT_LANGUANGE = 0;
                                try {
                                    DEFAULT_LANGUANGE = Long.parseLong(PstSystemProperty.getValueByName("DEFAULT_LANGUANGE"));
                                } catch (Exception exc) {
                                }
                                String strLang[] = com.dimata.util.lang.I_Language.langName;

                                Vector vectValue = new Vector(1, 1);
                                vectValue.add("" + langForeign);
                                vectValue.add("" + langDefault);
                                //strLang["tes"];
                                Vector vectKey = new Vector(1, 1);
                                vectKey.add(strLang[langForeign]);
                                vectKey.add(strLang[langDefault]);
                                out.println(ControlCombo.draw("app_language", null, "" + DEFAULT_LANGUANGE, vectValue, vectKey, ""));
                            %>
                        </div>
                        <div class="form-group">
                      <div class="checkbox">
                        <label>
                          <input type="checkbox">
                          With Minimum Image
                        </label>
                      </div>
                      <div class="checkbox">
                        <label>
                          <input type="checkbox">
                          Without Birthday On Home Page
                        </label>
                      </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-8">
                        </div>
                        <div class="col-xs-4">
                            <button type="submit" id="b_login" class="btn btn-primary btn-block btn-flat">Login</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
            <div class="text-center">
                <br />
                <p>
                    Copyrights &copy; 2016 <a href="http://www.dimata.com" target="_blank">Dimata IT Solutions</a> <br />
                    Application Build : 2016.05.31 <br />
                    Hotline: +628 123 771 0011 office hour: +62 361 499029 | 482431 <br />
                    All rights reserved.
                </p>
                <br/>
            </div>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <%
                                            if ((iCommand == CMD_LOGIN) && (dologin != SessUserSession.DO_LOGIN_OK)) {
                                        %>
                                        <tr>
                                            <td valign="middle" height="38" align="right" class="text" colspan="2">
                                                <div align="center">
                                                    <font size="+1" color="#FF0000" ><%=SessUserSession.soLoginTxt[dologin]%></font>
                                                </div>
                                            </td>
                                        </tr>
                                        <%}%>
                                    </table>
        <!-- jQuery 2.1.4 -->
        <script src="<%= approot %>/assets/plugins/jQuery/jQuery-2.1.4.min.js"></script>
        <!-- Bootstrap 3.3.5 -->
        <script src="<%= approot %>/assets/bootstrap/js/bootstrap.min.js"></script>
        <script language="JavaScript">
                document.frmLogin.login_id.focus();
        </script>
    </body>
</html>            
        
       