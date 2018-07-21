
<%@page import="com.dimata.util.lang.I_Dictionary"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.leave.I_Leave"%>
<%@ page language="java" %>
<%@ page import="java.util.*, com.dimata.util.Command" %>
<%@ page import="com.dimata.harisma.session.admin.*" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.harisma.session.leave.SessLeaveApplication"%>
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
    int iCommand = Integer.parseInt((request.getParameter("command") == null) ? "0" : request.getParameter("command"));
    String page_name = FRMQueryString.requestString(request, "page_name");
    String page_command = FRMQueryString.requestString(request, "page_command");
    String data_oid = FRMQueryString.requestString(request, "data_oid");
    String employee_oid = FRMQueryString.requestString(request, "employee_oid");

    int dologin = SessUserSession.DO_LOGIN_OK;
    if (iCommand == CMD_LOGIN) {
        String loginID = FRMQueryString.requestString(request, "login_id");
        String passwd = FRMQueryString.requestString(request, "pass_wd");
        String remoteIP = request.getRemoteAddr();
        boolean viewMin = FRMQueryString.requestBoolean(request, "viewform");
        boolean WOBirth = FRMQueryString.requestBoolean(request, "viewBirth");
        session.putValue("VIEW_MINIMUM", new Boolean(viewMin));
        session.putValue("VIEW_BIRTHDAY", new Boolean(WOBirth));


        SessUserSession userSess = new SessUserSession(remoteIP);
        dologin = userSess.doLogin(loginID, passwd);
         int appLanguage  = FRMQueryString.requestInt(request,"app_language"); 
        if (dologin == SessUserSession.DO_LOGIN_OK) {
            //update by satrya 2012-11-1
           session.removeValue(SessLeaveApplication.SESS_SRC_LEAVE_APPLICATION);
           //update by satrya 2013-08-12
           //agar langsung menjalankan penarikan jika ada salah satu karyawan yg login
           //com.dimata.harisma.utility.machine.TransManager.setRunningWithLoginApp(true);
           //end
          
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

            Employee objemployee = new Employee();

            String where = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] + " = '" + loginID + "' AND "
                    + PstEmployee.fieldNames[PstEmployee.FLD_EMP_PIN] + " = '" + passwd + "'";

            Vector listEmployee = new Vector();

            listEmployee = PstEmployee.list(0, 0, where, null);

            if (listEmployee != null && listEmployee.size() > 0) {

                objemployee = (Employee) listEmployee.get(0);
                dologin = userSess.doLogin("Employee", "dsj2009");

                session.setMaxInactiveInterval(MAX_SESSION_IDLE);
                userSess.setEmployee(objemployee.getOID());
                userSess.setUserHrdData();
                session.setMaxInactiveInterval(MAX_SESSION_IDLE);
                session.putValue(SessUserSession.HTTP_SESSION_NAME, userSess);
                userSess = (SessUserSession) session.getValue(SessUserSession.HTTP_SESSION_NAME);

            }

        }
    }

    int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_LOGIN, AppObjInfo.G2_LOGIN, AppObjInfo.OBJ_LOGIN_LOGIN);
%>
<html>
    <head>
        <title>Harisma - Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="styles/main.css" type="text/css">
        <link rel="stylesheet" href="styles/tab.css" type="text/css">
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
        <style type="text/css">
            .content {
                border:1px solid #CCC;
                background-color: #EEE;
                border-radius: 5px;
                margin: 0 auto;
                width: 27%;
            }
            .title_form {
                font-size: 18px;
                font-weight: bold;
                color: #575757;
                border-bottom: 1px solid #CCC;
                background-color: #DDD;
                padding: 5px;
                text-align: center;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
            }
            .formlogin {
                font-size: 12px;
                font-weight: bold;
                color: #333;
                padding: 12px 21px;
            }
            .tombol {
                cursor: pointer;
                padding: 5px 7px;
                color:#333;
                font-size: 11px;
                background-color: #DDD;
                border:1px solid #CCC;
                border-radius: 5px;
                font-weight: bold;
            }
            .tombol:hover {
                background-color: #CCC;
                border:1px solid #BBB;
            }
        </style>
    </head>
    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('images/login_images/button_f2.jpg');window.status=''">
        <!-- <%=com.dimata.qdep.db.DBHandler.CONFIG_FILE%> -->
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#FFFFFF">
            <tr>
                <td  bgcolor="#9BC1FF" height="20" ID="MAINMENU" valign="middle">
                    <table width="100%" border="0">
                        <tr>
                            <td  bgcolor="#BBDDFF">
                                <div align="center"><font color="#0000FF">&nbsp;</font></div>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td valign="middle" align="left">
                    <form name="frmLogin" action="" onsubmit="window.status=''">
                        <input type="hidden" name="command" value="<%=CMD_LOGIN%>">
                        <input type="hidden" name="page_name" value="<%=page_name%>">
                        <input type="hidden" name="page_command" value="<%=page_command%>">
                        <input type="hidden" name="data_oid" value="<%=data_oid%>">
                        <input type="hidden" name="employee_oid" value="<%=employee_oid%>">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <%
                                if ((iCommand == CMD_LOGIN) && (dologin == SessUserSession.DO_LOGIN_OK)) {
                            %>
                            <script language="JavaScript">
                                    //window.location = "home.jsp"
                                    window.location.assign("home.jsp?menu=home.jsp")
                            </script>
                            <%              }
                            %>
                            <tr>
                                <td width="100%">
                                    <div align="center"><img src="images/harismaFlatWhite.gif"  width="364" height="177"></div>
                                </td>
                            </tr>
                            <tr>
                                <td width="100%">&nbsp;</td>
                            </tr>
                            <tr>
                                <td width="100%">&nbsp;</td>
                            </tr>
                            <tr>
                                <td valign="top">
                                    <div class="content">
                                        <div class="title_form">User Login</div>
                                        <div class="formlogin">
                                            <table>
                                                <tr>
                                                    <td valign="center" style="padding-right: 7px; color: #575757"><strong>Login Name</strong></td>
                                                    <td valign="center"><input type="text" name="login_id" size="35" /></td>
                                                </tr>
                                                </table>
                                            </td>
                                        </tr>
                                                <tr>
                                                    <td valign="center" style="padding-right: 7px; color: #575757"><strong>Password</strong></td>
                                                    <td valign="center"><input type="password" name="pass_wd" size="35" /></td>
                                                </tr>
                                                <tr>
                                                    <td valign="center" style="padding-right: 7px; color: #575757"><strong>Language</strong></td>
                                                    <td valign="center">
                                                        <%
                                                            String strLang[] = com.dimata.util.lang.I_Language.langName;

                                                            Vector vectValue = new Vector(1,1);
                                                            vectValue.add(""+langForeign);
                                                            vectValue.add(""+langDefault);

                                                            Vector vectKey = new Vector(1,1);
                                                            vectKey.add(strLang[langForeign]);																		
                                                            vectKey.add(strLang[langDefault]);																														
                                                            out.println(ControlCombo.draw("app_language",null,"",vectValue,vectKey,""));						
                                                        %>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">&nbsp;</td>
                                                    <td valign="top">
                                                        <input type="checkbox" name="viewform" value="1" unchecked>
                                                        <i><strong>With Minimum Image</strong></i>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">&nbsp;</td>
                                                    <td valign="top">
                                                        <input type="checkbox" name="viewBirth" value="1" checked>
                                                        <i><strong>Without Birthday On Home Page</strong></i>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2" align="right" valign="top"><button class="tombol">Login</button></td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td width="100%">&nbsp;</td>
                            </tr>
                            
                            <tr>
      <td height="20" valign="top" align="center">&nbsp;<img src="images/dsj-logo-240.gif" height="45"/> </td>
  </tr>
  <tr> 
     
    <td height="30" valign="top" align="center">
        <div style="color: #575757">Copyrights Dimata IT Solutions : Version 4.0 ( Build : 2015.02.01.02 )</div>
       <div style="color: #575757">PT. Dimata Sora Jayate</div>
<div style="color: #575757">
Hotline: +62 361 7869752 / 8739333   office hour: +62 361 499029 | 482431 </div>
<div style="color: #575757">
  Online Chat gtalk : salesdsj@gmail.com

  Contact us : marketing@dimata.com </div>
       <div><a href="www.dimata.com"> www.dimata.com</div>
    </td>
  </tr>
                            <tr>
                                <td height="26" valign="bottom">
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
                                </td>
                            </tr>
                        </table>

                    </form>
                </td>
            </tr>
            <tr>
                <td colspan="2" height="20" >
                    <%@ include file = "footer.jsp" %>
                </td>
            </tr>
        </table>
        <script language="JavaScript">
                document.frmLogin.login_id.focus();
        </script>
    </body>
</html>
