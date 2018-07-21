

<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="com.dimata.harisma.entity.adjusmentworkingday.PstAdjusmentWorkingDay"%>
<%@page import="com.dimata.harisma.entity.adjusmentworkingday.AdjusmentWorkingDay"%>
<%@page import="com.dimata.harisma.form.adjusmentWorkingDay.FrmAdjusmentWorkingDay"%>
<%@page import="com.dimata.harisma.form.adjusmentWorkingDay.CtrlAdjusmentWorkingDay"%>
<%@page import="java.lang.String"%>
<%@page import="com.dimata.harisma.entity.koefisionposition.PstKoefisionPosition"%>
<%@page import="com.dimata.harisma.entity.koefisionposition.KoefisionPosition"%>

<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@ page language="java" %>  

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->

<%@page import="java.util.Vector"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@ page language="java" %>

<%@ page import = "java.util.*" %>
<!-- package wihita -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->

<%@ include file = "../main/javainit.jsp" %> 
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_PAYROLL, AppObjInfo.G2_PAYROLL_SETUP, AppObjInfo.OBJ_PAYROLL_SETUP_COMPONENT);%>
<%@ include file = "../main/checkuser.jsp" %>

<%
            //boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
            //boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
%>
<%

           
            long oidAdjusmentworkingday = FRMQueryString.requestLong(request, "oidAdjusmentworkingday");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            int iCommand = FRMQueryString.requestCommand(request);
            int startAdjusmentWorkingDay = FRMQueryString.requestInt(request, "startAdjusmentWorkingDay");
            String employeeNum = FRMQueryString.requestString(request, "hidden_employeeNum"); 
            String fullName = FRMQueryString.requestString(request, "hidden_fullName"); 
            long oidSubRegency=FRMQueryString.requestLong(request, "hidden_SubRegency");
            long oidLocation=FRMQueryString.requestLong(request, "hidden_location");
            int status = FRMQueryString.requestInt(request, "status"); 
            /*if(iCommand !=Command.LIST){
                employeeNum="";
                fullName="";
                oidSubRegency=0;
                oidLocation=0;
            }*/
            int iErrCode = FRMMessage.ERR_NONE;
            String msgString = "";
            ControlLine ctrLine = new ControlLine();
            //System.out.println("iCommand = " + iCommand);
             CtrlAdjusmentWorkingDay ctrlAdjusmentWorkingDay= new CtrlAdjusmentWorkingDay(request);
            iErrCode = ctrlAdjusmentWorkingDay.action(iCommand, oidAdjusmentworkingday);
            msgString = ctrlAdjusmentWorkingDay.getMessage();
            
            FrmAdjusmentWorkingDay frmAdjusmentWorkingDay = ctrlAdjusmentWorkingDay.getForm();  
            AdjusmentWorkingDay adjusmentWorkingDay = ctrlAdjusmentWorkingDay.getAdjusmentWorkingDay(); 
            oidAdjusmentworkingday = adjusmentWorkingDay.getOID();
            Vector listAdjusmentWorkingDay = new Vector(); 
            PstAdjusmentWorkingDay pstAdjusmentWorkingDay = new PstAdjusmentWorkingDay(); 
            /*variable declaration*/
            int recordToGet = 10; 
           Vector listBenefit = new Vector(1, 1);
           
            String orderClause = " SORT_IDX ";
          
            int vectAdjusmentWorkingDay = pstAdjusmentWorkingDay.getCount("");
              if ((iCommand == Command.FIRST || iCommand == Command.PREV) ||
                    (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                if (startAdjusmentWorkingDay < 0) {
                    startAdjusmentWorkingDay = 0;
                }
              

                startAdjusmentWorkingDay = ctrlAdjusmentWorkingDay.actionList(iCommand, startAdjusmentWorkingDay, vectAdjusmentWorkingDay, recordToGet); 
               
            }
          
            //PayExecutive payExecutive = ctrLanguage.getLanguage();
            msgString = ctrlAdjusmentWorkingDay.getMessage();
             /* get record to display */
            if (startAdjusmentWorkingDay < 0) {
                startAdjusmentWorkingDay = 0;
            }
            
           

            
            listAdjusmentWorkingDay = pstAdjusmentWorkingDay.innerJoinList(startAdjusmentWorkingDay,recordToGet,employeeNum,fullName,oidSubRegency,oidLocation,iCommand);
            
            
            
               /*handle condition if size of record to display = 0 and start > 0 	after delete*/ 
            if (listAdjusmentWorkingDay.size() < 1 && startAdjusmentWorkingDay > 0) {
                if (vectAdjusmentWorkingDay - recordToGet > recordToGet) {
                    startAdjusmentWorkingDay = startAdjusmentWorkingDay - recordToGet; 
                } //go to Command.PREV
                else {
                    startAdjusmentWorkingDay = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                if (startAdjusmentWorkingDay < 0) {
                    startAdjusmentWorkingDay = 0;
                }
                listAdjusmentWorkingDay = pstAdjusmentWorkingDay.list(startAdjusmentWorkingDay, recordToGet, "", "");
            //listDeduction = PstPayComponent.list(startBenefit,recordToGet, whereClauseDeduction , orderClause);
            }
           
            /*handle condition if size of record to display = 0 and start > 0 	after delete*/
          if(iCommand==Command.VIEW){ 
              listAdjusmentWorkingDay = pstAdjusmentWorkingDay.innerJoinList(startAdjusmentWorkingDay,recordToGet,employeeNum,fullName,oidSubRegency,oidLocation,iCommand); 
          }

            


%>

<%!
    public String drawList(int iCommand, FrmAdjusmentWorkingDay frmAdjusmentWorkingDay,Vector listAdjusmentWorkingDay,int status,long oidAdjusmentworkingday,AdjusmentWorkingDay adjusmentWorkingDayy,int limit) {
        System.out.println("Status  " + status);
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No", "2%");
        ctrlist.addHeader("Employee Number", "10%");
        ctrlist.addHeader("Employee Name", "27%");
        //ctrlist.addHeader("","");
        ctrlist.addHeader("Location Name", "20%");
         ctrlist.addHeader("Sistem Work Hours", "10%");
          ctrlist.addHeader("Adjusment Working Day", "10%");
       
       
         
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        Vector rowx = new Vector(1, 1);
        
        ctrlist.reset();
        int index = -1;
        Vector vEmployee = new Vector();
        Vector vLocation = new Vector();
         Vector vOidLoc = new Vector();
         Vector vValueLoc = new Vector();
         Vector vOid = new Vector();
         Vector vValue = new Vector(); 
        vEmployee = PstEmployee.list(0, 0, "", "");
        vLocation = PstLocation.list(0, 0, "", "");
        if(vEmployee!=null && vEmployee.size()>0){
            vOid = new Vector();
            vValue = new Vector();
            vOid.add(""+0);
            vValue.add("select");
            for(int i=0;i < vEmployee.size();i++){
                Employee employee =(Employee)vEmployee.get(i);
                vOid.add(""+employee.getOID());
                vValue.add(employee.getFullName()); 
            }
            
        }
        if(vLocation !=null && vLocation.size()>0){
            vOidLoc = new Vector();
            vValueLoc = new Vector();
            vOidLoc.add(""+0);
            vValueLoc.add("select");
            for(int c=0 ;c<vLocation.size();c++){
                Location location =(Location)vLocation.get(c);
               vOidLoc.add(""+location.getOID());
               vValueLoc.add(location.getName()); 
                
            }
        }
      
        
        int nmber=0;
        for (int i = 0; i < listAdjusmentWorkingDay.size(); i++) {
            AdjusmentWorkingDay adjusmentWorkingDay = (AdjusmentWorkingDay) listAdjusmentWorkingDay.get(i);  
            rowx = new Vector();
            if(i==0){
                nmber=1;
            }else{
                nmber=nmber+1; 
            }
            if (oidAdjusmentworkingday == adjusmentWorkingDay.getOID()) { 
                index = i;
            }
            if (index == i && (iCommand == Command.EDIT || iCommand == Command.ASK)) {
                rowx.add(""+(limit+nmber));
                rowx.add("<input type=\"text\" readonly=\"readonly\" name=\"" + ""+ "\" value=\"" + adjusmentWorkingDay.getEmployeeNum() + "\" size=\"12\" class=\"elemenForm\">");
                rowx.add(""+ControlCombo.draw(frmAdjusmentWorkingDay.fieldNames[frmAdjusmentWorkingDay.FRM_FLD_EMPLOYEE_ID], "formElemen", null, "" + adjusmentWorkingDay.getEmployeeId(), vOid, vValue)); 
                rowx.add(""+ControlCombo.draw(frmAdjusmentWorkingDay.fieldNames[frmAdjusmentWorkingDay.FRM_FLD_LOCATION_ID], "formElemen", null, "" + adjusmentWorkingDay.getLocationId(), vOidLoc, vValueLoc)); 
                rowx.add("<input type=\"text\" name=\"" + frmAdjusmentWorkingDay.fieldNames[frmAdjusmentWorkingDay.FRM_FLD_SISTEM_WORK_HOURS] + "\" value=\"" + adjusmentWorkingDay.getSistemWorkHours() + "\" size=\"12\" class=\"elemenForm\">");
                rowx.add("<input type=\"text\" name=\"" + frmAdjusmentWorkingDay.fieldNames[frmAdjusmentWorkingDay.FRM_FLD_ADJUSMENT_WORKING_DAY] + "\" value=\"" + adjusmentWorkingDay.getAdjusmentWorkingDay() + "\" size=\"12\" class=\"elemenForm\">");
               
                
            } else {
              
                //System.out.println("aku cek");
                 rowx.add(""+(limit+nmber));
                rowx.add("<a href=\"javascript:cmdEdit('" + String.valueOf(adjusmentWorkingDay.getOID()) + "')\">" + adjusmentWorkingDay.getEmployeeNum() + "</a>");  
                rowx.add("" + adjusmentWorkingDay.getFullName());  
                rowx.add("" + adjusmentWorkingDay.getLocationName()); 
                rowx.add("" + adjusmentWorkingDay.getSistemWorkHours()); 
                rowx.add("" + adjusmentWorkingDay.getAdjusmentWorkingDay());  
                
               
                //update by satrya 20130207
               
            }
            lstData.add(rowx);
        }
        rowx = new Vector();
        
        if (status == 1 || status == 0) {
            if (iCommand == Command.ADD || (iCommand == Command.SAVE && frmAdjusmentWorkingDay.errorSize() > 0) || (listAdjusmentWorkingDay.size() < 1)) { 
                 rowx.add(""+(limit+nmber+1));
                 rowx.add("<input type=\"text\" readonly=\"readonly\" name=\"" + "" + "\" value=\"" + adjusmentWorkingDayy.getEmployeeNum() + "\" size=\"12\" class=\"elemenForm\">");
                rowx.add(""+ControlCombo.draw(frmAdjusmentWorkingDay.fieldNames[frmAdjusmentWorkingDay.FRM_FLD_EMPLOYEE_ID], "formElemen", null, ""+adjusmentWorkingDayy.getFullName(), vOid, vValue)); 
                rowx.add(""+ControlCombo.draw(frmAdjusmentWorkingDay.fieldNames[frmAdjusmentWorkingDay.FRM_FLD_LOCATION_ID], "formElemen", null, ""+adjusmentWorkingDayy.getLocationName(), vOidLoc, vValueLoc));  
                rowx.add("<input type=\"text\" name=\"" + frmAdjusmentWorkingDay.fieldNames[frmAdjusmentWorkingDay.FRM_FLD_SISTEM_WORK_HOURS] + "\" value=\"" + adjusmentWorkingDayy.getSistemWorkHours() + "\" size=\"12\" class=\"elemenForm\">"); 
                rowx.add("<input type=\"text\" name=\"" + frmAdjusmentWorkingDay.fieldNames[frmAdjusmentWorkingDay.FRM_FLD_ADJUSMENT_WORKING_DAY] + "\" value=\"" + adjusmentWorkingDayy.getAdjusmentWorkingDay() + "\" size=\"12\" class=\"elemenForm\">");
                
                
                
            }
        }
        lstData.add(rowx);

        return ctrlist.draw();
    }

%>


<!-- JSP Block -->
<!-- End of JSP Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" --> 
    <head>
        <!-- #BeginEditable "doctitle" --> 
        <title>HARISMA - Setup - Salary Component</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="../styles/tab.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "headerscript" --> 
        <SCRIPT language=JavaScript>
            
            function cmdEdit(oidAdjusmentworkingday){
                document.frm_adjusment_working_day.oidAdjusmentworkingday.value=oidAdjusmentworkingday;
                document.frm_adjusment_working_day.status.value="0";
                document.frm_adjusment_working_day.command.value="<%=Command.EDIT%>";
                document.frm_adjusment_working_day.prev_command.value="<%=prevCommand%>";
                document.frm_adjusment_working_day.action="adjusmentworkingday.jsp";
                document.frm_adjusment_working_day.submit();
            }
            
            function cmdAdd(){
                
                document.frm_adjusment_working_day.oidAdjusmentworkingday.value="0";
                document.frm_adjusment_working_day.status.value="1";
                document.frm_adjusment_working_day.command.value="<%=Command.ADD%>";
                document.frm_adjusment_working_day.prev_command.value="<%=prevCommand%>";
                document.frm_adjusment_working_day.action="adjusmentworkingday.jsp";
                document.frm_adjusment_working_day.submit();
            }
            
            function cmdAddD(){
                document.frm_adjusment_working_day.oidAdjusmentworkingday.value="0";
                document.frm_adjusment_working_day.status.value="2";
                document.frm_adjusment_working_day.command.value="<%=Command.ADD%>";
                document.frm_adjusment_working_day.prev_command.value="<%=prevCommand%>";
                document.frm_adjusment_working_day.action="adjusmentworkingday.jsp";
                document.frm_adjusment_working_day.submit();
            }
         
            function cmdSave(){
                document.frm_adjusment_working_day.command.value="<%=Command.SAVE%>";
                document.frm_adjusment_working_day.status.value="3";
                document.frm_adjusment_working_day.prev_command.value="<%=prevCommand%>";
                document.frm_adjusment_working_day.action="adjusmentworkingday.jsp";
                document.frm_adjusment_working_day.submit();
            }
             function cmdSeacrh(){
                 
                document.frm_adjusment_working_day.command.value="<%=Command.VIEW%>";
                document.frm_adjusment_working_day.status.value="3";
                document.frm_adjusment_working_day.prev_command.value="<%=prevCommand%>";
                document.frm_adjusment_working_day.action="adjusmentworkingday.jsp";
                document.frm_adjusment_working_day.submit();
            }
            function cmdView(){
                document.frm_adjusment_working_day.command.value="<%=Command.LIST%>";
                document.frm_adjusment_working_day.prev_command.value="<%=prevCommand%>";
                document.frm_adjusment_working_day.action="adjusmentworkingday.jsp";
                document.frm_adjusment_working_day.submit();
            }
            
            function cmdBack(){
                document.frm_adjusment_working_day.command.value="<%=Command.BACK%>";
                document.frm_adjusment_working_day.action="adjusmentworkingday.jsp";
                document.frm_adjusment_working_day.submit();
            }
            
            function cmdListFirst(){
                document.frm_adjusment_working_day.command.value="<%=Command.FIRST%>";
                document.frm_adjusment_working_day.prev_command.value="<%=Command.FIRST%>";
                document.frm_adjusment_working_day.action="adjusmentworkingday.jsp";
                document.frm_adjusment_working_day.submit();
            }
            
            function cmdListPrev(){
                document.frm_adjusment_working_day.command.value="<%=Command.PREV%>";
                document.frm_adjusment_working_day.prev_command.value="<%=Command.PREV%>";
                document.frm_adjusment_working_day.action="adjusmentworkingday.jsp";
                document.frm_adjusment_working_day.submit();
            }
            function cmdListNext(){
                document.frm_adjusment_working_day.command.value="<%=Command.NEXT%>";
                document.frm_adjusment_working_day.prev_command.value="<%=Command.NEXT%>";
                document.frm_adjusment_working_day.action="adjusmentworkingday.jsp";
                document.frm_adjusment_working_day.submit();
            }
            
            function cmdListLast(){
                document.frm_adjusment_working_day.command.value="<%=Command.LAST%>";
                document.frm_adjusment_working_day.prev_command.value="<%=Command.LAST%>";
                document.frm_adjusment_working_day.action="adjusmentworkingday.jsp";
                document.frm_adjusment_working_day.submit();
            }
            
            function cmdConfirmDelete(oidAdjusmentworkingday){
                var x = confirm(" Are You Sure to Delete?");
                if(x){
                    document.frm_adjusment_working_day.command.value="<%=Command.DELETE%>";
                    document.frm_adjusment_working_day.action="adjusmentworkingday.jsp";
                    document.frm_adjusment_working_day.submit();
                }
            }
            //update by devin 2014-04-08
function cmdUpdateDiv(){
    document.frm_adjusment_working_day.command.value="<%=String.valueOf(Command.GOTO)%>";
    document.frm_adjusment_working_day.action="adjusmentworkingday.jsp";
    document.frm_adjusment_working_day.target = "";
    document.frm_adjusment_working_day.submit();
}
                
            
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
     <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">     
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>  
           <%@include file="../styletemplate/template_header.jsp" %>
            <%}else{%>
            <tr> 
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54"> 
                    <!-- #BeginEditable "header" --> 
      <%@ include file = "../main/header.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <tr> 
                <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" --> 
      <%@ include file = "../main/mnmain.jsp" %>
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
                                        <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Adjusment Working Day Setting <!-- #EndEditable --> </strong></font> </td>
                                    </tr>
                                    <tr> 
                                        <td height="20"> </td>
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
                                                                            <td valign="top"> <!-- #BeginEditable "content" --> 
                                                                                <form name="frm_adjusment_working_day" method="post" action="">
                                                                                    <input type="hidden" name="command" value="">
                                                                                   <input type="hidden" name="startAdjusmentWorkingDay" value="<%=startAdjusmentWorkingDay%>">
                                                                                    <input type="hidden" name="oidAdjusmentworkingday" value="<%=oidAdjusmentworkingday%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="status" value="<%=status%>">
                                                                                    
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                    <tr> <td>
                                                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">   
                                                                                                <tr align="left" valign="top"> 
                                                                                                    <td  align="left" height="14" valign="middle" colspan="2" class="listtitle">&nbsp;Adjusment Working Day Setting </td>
                                                                                                </tr> 
                                                                                            </table>
                                                                                    </td></tr>
                                                                                    
                                                                                     <table width="60%" border="0" cellspacing="2" cellpadding="2">
						<!-- update by satrya 2012-10-03 --><br />
                                        <tr>                                               <tr>
                                            <!-- update by devin 2014-01-28 -->
                                                                                            <td width="6%" nowrap="nowrap"><div align="left">Payrol Num </div></td>
                                                                                             <td width="30%" nowrap="nowrap">:
                                                                                               <input class="masterTooltip" type="text" size="40" name="hidden_employeeNum"  value="<%=  employeeNum %>" title="You can Input Payroll Number more than one, ex-sample : 1111,2222" class="elemenForm" onKeyDown="javascript:fnTrapKD()">
                                                                                             </td>
                                                                                             </tr>
                                                                                             <tr>
                                                                                             <td width="5%" nowrap="nowrap"><div align="left"><%=dictionaryD.getWord(I_Dictionary.FULL_NAME) %></div> </td>
                                                                                             <td width="59%" nowrap="nowrap">:
                                                                                             <input class="masterTooltip" type="text" size="40" name="hidden_fullName"  value="<%=fullName%>" title="You can Input Full Name more than one, ex-sample : saya,kamu" class="elemenForm" onKeyDown="javascript:fnTrapKD()">
                                                                                             </td>
                                                                                            </tr>
                                                                                             <tr>
                                                                                            <td width="6%" nowrap="nowrap"><div align="left">Sub Regency </div></td>
                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                             <%
                                                                                             Vector vOid = new Vector();
                                                                                             Vector vValue =new Vector();
                                                                                             vOid.add(""+0);
                                                                                             vValue.add("select");
                                                                                             Vector listSubRegency=PstKecamatan.list(0, 0, "", "");
                                                                                             if(listSubRegency!=null && listSubRegency.size()>0){
                                                                                                 for(int list=0;list<listSubRegency.size();list++){
                                                                                                     Kecamatan kecamatan =(Kecamatan)listSubRegency.get(list);
                                                                                                     vOid.add(""+kecamatan.getOID());
                                                                                                     vValue.add(kecamatan.getNmKecamatan());
                                                                                                 }
                                                                                             }
                                                                                              %>
                                                                                           <%= ControlCombo.draw("hidden_SubRegency", "formElemen", null, ""+oidSubRegency, vOid, vValue, "onChange=\"javascript:cmdUpdateDiv()\"")%> </td>
                                                                                             
                                                                                              
                                                                                            <td width="6%" nowrap="nowrap"><div align="right">Location </div></td>
                                                                                            <td width="30%" nowrap="nowrap">:
                                                                                             <%
                                                                                             Vector vOidd = new Vector();
                                                                                             Vector vValuee =new Vector();
                                                                                             if(oidSubRegency==0){
                                                                                             vOidd.add(""+0);
                                                                                             vValuee.add("select");
                                                                                             }
                                                                                             
                                                                                             if(oidSubRegency>0){
                                                                                                 String whereClouse="SUB_REGENCY_ID ="+oidSubRegency;  
                                                                                                  Vector listLocation=PstLocation.list(0, 0, whereClouse, "");  
                                                                                             if(listLocation!=null && listLocation.size()>0){
                                                                                                 for(int list=0;list<listLocation.size();list++){
                                                                                                     Location location =(Location)listLocation.get(list);
                                                                                                     vOidd.add(""+location.getOID());
                                                                                                     vValuee.add(location.getName());
                                                                                                 }
                                                                                             }else{
                                                                                                       vOidd.add(""+0);
                                                                                                        vValuee.add("select");  
                                                                                             }
                                                                                             }
                                                                                            
                                                                                              %>
                                                                                           <%= ControlCombo.draw("hidden_location", "formElemen", null, ""+oidLocation, vOidd, vValuee, "")%> </td>
                                                                                             </tr>
                                                                                             <tr align=\"center\">
                                                                                                 <td> <a href="javascript:cmdSeacrh()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data"></a>
                                                                                                         
                                                                                                            <a href="javascript:cmdSeacrh()" class="command">Search </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                                                                             </tr>
                                                                                     </table>
                                                                                    <%
            try {
                // out.println("listBenefit"+listBenefit.size());
                if ((listAdjusmentWorkingDay == null || listAdjusmentWorkingDay.size() < 1) && (iCommand == Command.NONE)) {
                    iCommand = Command.ADD;
                }

                                                                                    %>
                                                                                    <tr> 
                                                                                    <td width="24%" colspan="2">
                                                                                    <%= drawList(iCommand, frmAdjusmentWorkingDay, listAdjusmentWorkingDay, status,oidAdjusmentworkingday,adjusmentWorkingDay,startAdjusmentWorkingDay)%>
                                                                                    <%
                                                                                    if(iErrCode==100){
                                                                                        %>
                                                                                        <div style="background-color:#FF0000; color:#FFFFFF" ><b><center>LIST EMPLOYEE NOT SELECTED</center></b></div>
                                                                                        <%
                                                                                    }else if(iErrCode==101){%>
                                                                                        <div style="background-color:#FF0000; color:#FFFFFF" ><b><center>LIST LOCATION NOT SELECTED</center></b></div>
                                                                                    <%}
                                                                                    %>
                                                                                    <table cellpadding="0" cellspacing="0" border="0">
                                                                                        <%
            } catch (Exception exc) {                                                   
            }
                                                                                        %>
                                                                                        
                                                                                        <tr align="left" valign="top"> 
                                                                                            <td width="519">
                                                                                                
                                                                                                <table width="136%" height="68" border="0">
                                                                                                    
                                                                                                    <tr>
                                                                                                    
                                                                                                      <%
            int cmd = 0;
            if ((iCommand == Command.FIRST || iCommand == Command.PREV) ||
                    (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                cmd = iCommand; 
            } else {
                if (iCommand == Command.NONE || prevCommand == Command.NONE) {
                    cmd = Command.FIRST; 
                } else {
                    cmd = prevCommand; 
                }
            }
                                                                                        %>
                                                                                        <% ctrLine.setLocationImg(approot + "/images");
                                                                                ctrLine.initDefault();
                                                                                        %>
                                                                                        <%=ctrLine.drawImageListLimit(cmd, vectAdjusmentWorkingDay, startAdjusmentWorkingDay, recordToGet)%> 
                                                                                                                                                       <%
            //	out.println("masukq"+Command.ADD) ;
            if ((iCommand != Command.ADD && iCommand != Command.ASK && iCommand != Command.EDIT) && (frmAdjusmentWorkingDay.errorSize() < 1)) {%>
                                                                                                    <tr> 
                                                                                                        <td width="150"><a href="javascript:cmdAdd()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Add new data"></a>
                                                                                                            <a href="javascript:cmdAdd()" class="command">Add 
                                                                                                        New Adjusment</a> </td>
                                                                                                    </tr>
                                                                                                    <%}%>
                                                                                                    
            
                                                                                                    <% if (status == 1 || status == 0) {
                if ((iCommand == Command.ADD) || (iCommand == Command.SAVE) && (frmAdjusmentWorkingDay.errorSize() > 0) || (iCommand == Command.EDIT) || (iCommand == Command.ASK)) {%>
                                                                                                    <tr align="left" valign="top"> 
                                                                                                        <td >
                                                                                                            <a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnSave.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnSave.jpg" width="24" height="24" alt="Save Data"></a>
                                                                                                         
                                                                                                            <a href="javascript:cmdSave()" class="command">Save Adjusment </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                                                            <img name="Image261" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete">
                                                                                                            <a href="javascript:cmdConfirmDelete()" class="command">Delete Adjusment</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                                                            <a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image261','','<%=approot%>/images/BtnBack.jpg',1)"><img name="Image261" border="0" src="<%=approot%>/images/BtnBack.jpg" width="24" height="24" alt="Back to List"></a>
                                                                                                        <a href="javascript:cmdBack()" class="command">Back to Adjusment</a> </td>
                                                                                                    </tr>
                                                                                                    <%}
            }%>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                       
           
                                                                                    </table>
                                                                                    <table width="100%" border="0">
                                                                                        <tr>
                                                    
         
                                                                                        
                                                                                </form>
                                                                            <!-- #EndEditable --> </td>
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
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <!-- #BeginEditable "script" --> <script language="JavaScript">
        var oBody = document.body;
        var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    
    <!-- #EndEditable --> <!-- #EndTemplate -->
</html>
