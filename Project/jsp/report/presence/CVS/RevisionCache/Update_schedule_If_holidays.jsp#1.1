<%@page import="com.dimata.harisma.form.masterdata.FrmPeriod"%>
<%@page import="com.dimata.harisma.utility.machine.SaverData"%>
<%@page import="com.dimata.harisma.utility.service.presence.PresenceAnalyser"%>
<%
    /* 
     * Page Name  		:  update_schedule_if_holidays.jsp
     * Created on 		:  [date] [time] AM/PM 
     * 
     * @author  		: Satrya Ramayu 
     */

    /**
     * *****************************************************************
     * Page Description : [project description ... ] Imput Parameters : [input
     * parameter ...] Output : [output ...] 
 ******************************************************************
     */
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
<%@ page import = "com.dimata.harisma.session.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.search.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ include file = "../../main/javainit.jsp" %>
<% //int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_PRESENCE, AppObjInfo.OBJ_MANUAL_PRESENCE); %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_PRESENCE);%>
<%@ include file = "../../main/checkuser.jsp" %>
<%
    /* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
//boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>
<!-- Jsp Block -->
<%
//Date selectedDateFrom = FRMQueryString.requestDate(request, "check_date_start");
    //Date selectedDateTo = FRMQueryString.requestDate(request, "check_date_finish");
    //String empNum = FRMQueryString.requestString(request, "emp_number");
    //String fullName = FRMQueryString.requestString(request, "full_name");
    Vector listPeriod = PstPeriod.list(0, 0, "", "");
    String sOidPeriod = FRMQueryString.requestString(request, "FRM_FIELD_PERIOD_ID");
    int iCommand = FRMQueryString.requestCommand(request);
    long oidPeriod = 0;
    if (sOidPeriod != null && sOidPeriod.length() > 0) {
        try {
            oidPeriod = Long.parseLong(sOidPeriod);
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        }
    }
    String msgString = "";
    Period period = new Period();
    if (oidPeriod != 0) {
        period = PstPeriod.fetchExc(oidPeriod);// get periode and calendar , and start day                                                   
        //Vector listAttendanceRecordDailly = SessEmpSchedule.listEmpPresenceDaily(0, selectedDateFrom, selectedDateTo, 0, empNum, "", "", 0, 0);   
    }
    if (iCommand == Command.SAVE) {
        if (oidPeriod != 0) {
                    try { int totalUpdateOrInsertedSchedule=0;
                        // generate schedule fo ron existing schedule in a periode for all employee having default schedule
                        int empTotal = PstEmployee.getCountEmployeeHaveDefaultSchedule(""); // list count of employee having default schedule
                        if (empTotal > 0) {
                            //Period period = PstPeriod.fetchExc(oidPeriod);// get periode and calendar , and start day                                                 
                            int start=0;
                            //update by satrya 2012-10-09
                            HolidaysTable holidaysTable = PstPublicHolidays.getHolidaysTable(period.getStartDate(), period.getEndDate());
                            long oidPublicHoliday = 0;         
                            try{
                                oidPublicHoliday = Long.parseLong(PstSystemProperty.getValueByName("OID_PUBLIC_HOLIDAY"));
                            }catch(Exception ex){
                                System.out.println("Execption OID_PUBLIC_HOLIDAY: " + ex.toString());
                            }
                            long oidDayOff = 0;         
                            try{
                                oidDayOff = Long.parseLong(PstSystemProperty.getValueByName("OID_DAY_OFF"));
                            }catch(Exception ex){
                                System.out.println("Execption OID_DAY_OFF: " + ex.toString());
                            }
                            do{                                
                                Vector employeeList = PstEmployee.getEmployeeHaveDefaultSchedule(start, 50, "", ""); // loop per 50 employee
                                start = start +50;                                
                                if(employeeList!=null){                                
                                for(int idx=0; idx< employeeList.size();idx++){                                    
                                    Employee employee = (Employee) employeeList.get(idx);
                                    String whereClauseDS = PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_EMPLOYEE_ID]+"="+employee.getOID();
                                    String orderDS= PstDefaultSchedule.fieldNames[PstDefaultSchedule.FLD_DAY_INDEX] ;
                                    Vector dftSchedules = PstDefaultSchedule.list(0, 7, whereClauseDS, orderDS);                                    
                                    if(dftSchedules!=null && dftSchedules.size()>0){
                                        EmpSchedule schedule = PstEmpSchedule.fecth(oidPeriod, employee.getOID());
                                        boolean updated=false;
                                        if(schedule==null){
                                            updated=true;
                                            schedule = new EmpSchedule();
                                            schedule.setEmployeeId(employee.getOID());
                                            schedule.setPeriodId(oidPeriod);                                            
                                        }
                                        GregorianCalendar gcStart = new GregorianCalendar(period.getStartDate().getYear(), period.getStartDate().getMonth(), period.getStartDate().getDate());                                        
                                        int sDayOfWeek = gcStart.get(GregorianCalendar.DAY_OF_WEEK);
                                        int nDayOfMonthStart = gcStart.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
                                        int iDate=period.getStartDate().getDate();                                        
                                        int iDayWeek=period.getStartDate().getDay()+1; // Gregorion callendar Sunday = 1
                                        //update by satrya 2012-10-09
                                        Date selectedDate = (Date)period.getStartDate().clone(); // = new  Date(period.getStartDate().getTime());
                                        int countOfDay=0; // counter pengaman                                        
                                        do{ // loop through the calendar 
                                            if(schedule.getOID()!=0){ // for existing schedule 
                                                
                                                    if(holidaysTable.isHoliday(employee.getReligionId(), selectedDate)){
                                                        schedule.setD(iDate, oidPublicHoliday); //jika ada holiday religion
                                                         updated=true;
                                                    }
                                                
                                            }//end jika oid tidak ada
                                            
                                            iDayWeek = iDayWeek+1;
                                            if(iDayWeek>7){ // if day of week saturday then back to sunday =1
                                                iDayWeek =1; 
                                            }
                                            
                                            countOfDay=countOfDay+1;
                                            iDate = iDate+1; 
                                            selectedDate.setDate(selectedDate.getDate()+1);//otomatis akan melewati bulannya
                                            if(iDate>nDayOfMonthStart){ // jika tanggal di schedule sudah melewati tanggal maximum di bulan itu
                                                iDate=1;
                                            }
                                        } while(iDate!=(period.getEndDate().getDate()+1) && countOfDay < 31 );
                                        if(updated){
                                              // save schedule for an employee
                                            totalUpdateOrInsertedSchedule++;
                                             try{
                                                 if(schedule.getOID()!=0){
                                                    PstEmpSchedule.updateExc(schedule);
                                                 }else{
                                                    PstEmpSchedule.insertExc(schedule);
                                                 }                                                 
                                             }catch(Exception exc){
                                                 System.out.println(exc);
                                             }
                                        }
                                    }
                                }
                               }
                            }while(start < empTotal);
                        }                                          
                        msgString = "Generate schedule : Total Employee have default schedule="+  empTotal + " ; total updated schedule="+totalUpdateOrInsertedSchedule;
                    } catch (Exception exc) {
                        msgString = exc.toString();
                        System.out.println(exc);
                    }
                }
                
    }

%>
<!-- End of Jsp Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Presence</title>
        <script language="JavaScript">
            function cmdSave(){
                document.frm_schedule.command.value="<%=Command.SAVE%>";
                document.frm_schedule.action="Update_schedule_If_holidays.jsp";
                document.frm_schedule.submit();
            }
        </script>
        <!-- #EndEditable -->
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable --><!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable --><!-- #BeginEditable "headerscript" -->
        <SCRIPT language=JavaScript>
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

        </SCRIPT>
        <!-- #EndEditable -->
    </head>
    <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnBackOn.jpg','<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
            <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
            <tr>
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"><!-- #BeginEditable "header" -->
                    <%@ include file = "../../main/header.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <tr>
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"><!-- #BeginEditable "menumain" -->
                    <%@ include file = "../../main/mnmain.jsp" %>
                    <!-- #EndEditable --> </td>
            </tr>
            <tr>
                <td  bgcolor="#9BC1FF" height="10" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
                            <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
                            <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
                        </tr>
                    </table></td>
            </tr>
            <%}%>
            <tr>
                <td width="88%" valign="top" align="left"><table width="100%" border="0" cellspacing="3" cellpadding="2">
                        <tr>
                            <td width="100%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td height="20"><font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> Employee &gt; Attendance &gt; Manual Presence<!-- #EndEditable --> </strong></font> </td>
                                    </tr>
                                    <tr>
                                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td  style="background-color:<%=bgColorContent%>; ">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr>
                                                                <td  style="border:1px solid <%=garisContent%>" valign="top"><table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr>
                                                                            <td valign="top"><!-- #BeginEditable "content" -->
                                                                                <form name="frm_schedule" method="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="oidPeriod" value="<%=sOidPeriod%>"> 
                                                                                    <table border="0" width="100%">
                                                                                        <tr>
                                                                                            <td colspan="2"><div align="center"><b><font size="3">Update Schedule Holidays</font></b></div></td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td width="7%">Periode:</td>
                                                                                            <td width="93%">
                                                                                                <%

                                                                                                    Vector status_value = new Vector(1, 1);
                                                                                                    Vector status_key = new Vector(1, 1);

                                                                                                    for (int i = 0; i < listPeriod.size(); i++) {
                                                                                                        Period newPeriod = (Period) listPeriod.get(i);
                                                                                                        status_value.add("" + newPeriod.getOID());
                                                                                                        status_key.add(newPeriod.getPeriod());
                                                                                                    }
                                                                                                %>
                                                                                                <%=ControlCombo.draw(FrmPeriod.fieldNames[FrmPeriod.FRM_FIELD_PERIOD_ID], "-select-", sOidPeriod, status_value, status_key)%>                                                                                          </td>
                                                                                        </tr>
                                                                                    </table>

                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">

                                                                                        <tr>
                                                                                            <td width="46%" nowrap align="left" class="command"><!-- &nbsp; <a href="javascript:cmdAdd()">Add New</a> | <a href="javascript:cmdBack()">Back to search</a> -->
                                                                                                <table width="60" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                                                        <td width="24"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Save"></a></td>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                                                        <td width="169" nowrap><b><a href="javascript:cmdSave()" class="command">Save</a></b></td>
                                                                                                    </tr>
                                                                                                </table></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </form>
                                                                                <!-- #EndEditable --> </td>
                                                                        </tr>
                                                                    </table></td>
                                                            </tr>
                                                        </table></td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
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
                <td colspan="2" height="20" <%=bgFooterLama%>> <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <!-- #BeginEditable "script" -->
    <script language="JavaScript">
            var oBody = document.body;
            var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <sript language="Javascript" >                                
        <% //if(submitIsOk) {%>
        <!--self.closed();
        window.close();-->

        <%//}%>
    </sript>
    <!-- #EndEditable --><!-- #EndTemplate -->
</html>
