
<%@page import="com.dimata.harisma.form.leave.FrmLeaveApplication"%>
<%@page import="com.dimata.harisma.entity.leave.LeaveApplication"%>
<%@page import="com.dimata.harisma.entity.leave.PstLeaveApplication"%>
<%@page import="com.dimata.harisma.entity.leave.I_Leave"%>
<%@page import="com.dimata.harisma.utility.machine.SaverData"%>
<%@page import="com.dimata.harisma.utility.service.presence.PresenceAnalyser"%>
<%
    /* 
    Document   : if_dp_not_balance
    Created on : Dec 13, 2012, 1:16:07 PM
    Author     : Satrya Ramayu
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
    I_Leave leaveConfig = null;

    try {
        leaveConfig = (I_Leave) (Class.forName(PstSystemProperty.getValueByName("LEAVE_CONFIG")).newInstance());
    } catch (Exception e) {
        System.out.println("Exception LEAVE_CONFIG: " + e.getMessage());
    }
%>
<%!    

    private String drawList(Vector vectDpListNotBalance,Vector vectListEmp,I_Leave leaveConfig) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("100%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No", "5%");
        ctrlist.addHeader("NIP", "10%");
        ctrlist.addHeader("Nama", "25%");
        ctrlist.addHeader("Date Off Request", "10%");
        ctrlist.addHeader("Unpaid Date", "10%");
        ctrlist.addHeader("Taken ", "15%");
        ctrlist.addHeader("Taken Date ", "15%");
        ctrlist.addHeader("Taken Finish", "15%");

        ctrlist.setLinkRow(3);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        //DpStockTaken dpStockMan = new DpStockTaken();

        int start = 0;
        
      if(vectListEmp!=null && vectListEmp.size()>0){
        for(int ix = 0; ix < vectListEmp.size(); ix++){
          Employee employee = (Employee) vectListEmp.get(ix); 
         float totalTaken=0;
            vectDpListNotBalance = SessDpStockManagement.ListDpTakenSisa(employee.getOID(), true);    
        if (vectDpListNotBalance != null && vectDpListNotBalance.size() > 0) {
                Vector rowx = new Vector(1, 1);
                rowx.add("");
                rowx.add(""+employee.getEmployeeNum());
                rowx.add(""+employee.getFullName());
                rowx.add("");
                rowx.add("");
                rowx.add("");
                rowx.add("");
                rowx.add("");
                lstData.add(rowx);
                 lstLinkData.add(""); 
            for (int i = 0; i < vectDpListNotBalance.size(); i++) {
                
                boolean cekExpired = false;
                LeaveApplication leaveApplication = new LeaveApplication();
                DpStockTaken dpStockTaken = (DpStockTaken) vectDpListNotBalance.get(i);
         if(dpStockTaken.getEmployeeId()==employee.getOID()){
                try{
                 leaveApplication = PstLeaveApplication.fetchExc(dpStockTaken.getLeaveApplicationId());
                }catch(Exception ex){
                    leaveApplication = new LeaveApplication();
                }
                rowx = new Vector(1, 1);
                
                start = start + 1;
                
                rowx.add(String.valueOf(start));
                rowx.add("");
                rowx.add("");
                String pdaid = "";
                if(dpStockTaken.getPaidDate()!=null){
                    pdaid = Formater.formatDate(dpStockTaken.getPaidDate(), "yyyy-MM-dd");
                }else{
                    pdaid = "-";
                }
                String submissDate ="";
                if(leaveApplication.getSubmissionDate()!=null){
                    submissDate = Formater.formatDate(leaveApplication.getSubmissionDate(), "yyyy-MM-dd");
                }else{
                    submissDate="-";
                } 
                rowx.add("" + submissDate);
                rowx.add("" + pdaid);
                rowx.add("" + Formater.formatWorkDayHoursMinutes(dpStockTaken.getTakenQty(), leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())); 
                rowx.add("" + Formater.formatDate(dpStockTaken.getTakenDate(), "yyyy-MM-dd HH:mm:ss"));
                rowx.add("" + Formater.formatDate(dpStockTaken.getTakenFinnishDate(), "yyyy-MM-dd HH:mm:ss"));///wilbe taken
                
                lstData.add(rowx);

                lstLinkData.add(String.valueOf(leaveApplication.getOID()));
                totalTaken = totalTaken + dpStockTaken.getTakenQty();
                    vectDpListNotBalance.remove(i);
                    i=i-1;
                }//jika tidak sama dengan emp
              /*else if(dpStockTaken.getEmployeeId()!=employee.getOID() && employee.getResigned()!=1){
                    Vector rowxx = new Vector(1, 1);
                    rowxx.add("");
                    rowxx.add("");
                    rowxx.add("Total:");
                    if(totalTaken!=0){
                        rowxx.add(""+ Formater.formatWorkDayHoursMinutes(totalTaken, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave()));
                    }else{
                    rowxx.add("");
                    }
                    rowxx.add("");///wilbe taken
                    rowxx.add("");
                    lstData.add(rowxx);
                    break; 
                    //lstLinkData.add("");
               }*/
            }
             rowx = new Vector(1, 1); 
                    rowx.add("");
                    rowx.add("");
                    rowx.add("");
                    rowx.add("");
                    rowx.add("Total:");
                    if(totalTaken!=0){
                        rowx.add("<p><abbr title=\""+totalTaken+"\">"+Formater.formatWorkDayHoursMinutes(totalTaken, leaveConfig.getHourOneWorkday(), leaveConfig.getFormatLeave())+"</p>");
                    }else{
                    rowx.add("");
                    }
                    rowx.add("");///wilbe taken
                    rowx.add("");
                    lstData.add(rowx);
                     lstLinkData.add("");
            
        }
       }
     }

        return ctrlist.draw();
    }
%>
<!-- Jsp Block -->
<%
 
    int iCommand = FRMQueryString.requestCommand(request);
    String empNumber = FRMQueryString.requestString(request, "hidden_emp_number");
    
    String msgString = "";
    Vector listDp = new Vector();
    Vector listEmp = new Vector();
    if (iCommand == Command.LIST) {

        try {
            CtrlDpStockTaken ctrlDpStockTaken = new CtrlDpStockTaken(request);
            //listDp = SessDpStockManagement.ListDpTakenToDpBalance(empNumber, true);   
            listEmp = PstEmployee.list(0, 0, empNumber!=null && empNumber.length()>0 ? PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM] = empNumber : "", PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID] + " ASC ");
        } catch (Exception ex) {
           listDp = new Vector();
        }
    }
%>
<!-- End of Jsp Block -->
<html>
    <!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - Re-Calculate DP Balance</title>
        <script language="JavaScript">
        function cmdList(){
           
            document.frm_dp_list.command.value="<%=Command.LIST%>";  
            document.frm_dp_list.action="dp_list_not_balance.jsp";
            document.frm_dp_list.submit(); 
        }
        function cmdEdit(oidLeave)
{
     var linkPage = "<%=approot%>/employee/leave/leave_app_edit.jsp?command=<%=(Command.EDIT)%>&<%=FrmLeaveApplication.fieldNames[FrmLeaveApplication.FRM_FLD_LEAVE_APPLICATION_ID]%>="+oidLeave; 
     var newWin = window.open(linkPage,"Leave","height=700,width=950,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes");  			
     newWin.focus();
}
         function cmdBack(){
           
            document.frm_dp_list.command.value="<%=Command.BACK%>";  
            document.frm_dp_list.action="if_dp_not_balance.jsp";
            document.frm_dp_list.submit(); 
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
                                        <td height="20"><font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" --> Employee &gt; Leave &gt; Balance DP<!-- #EndEditable --> </strong></font> </td>
                                    </tr>
                                    <tr>
                                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td  style="background-color:<%=bgColorContent%>; ">
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                            <tr>
                                                                <td valign="top">
                                                                    <table  style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                                        <tr>
                                                                            <td valign="top"><!-- #BeginEditable "content" -->
                                                                                <form name="frm_dp_list" method="post" action="">
                                                                                    <input type="hidden" name="hidden_emp_number" value="<%=empNumber%>">
                                                                                    <input type="hidden" name="command" value="">
                                                                                    <table align="center">
                                                                                        <tr>
                                                                                            <td>
                                                                                                <div align="center"><b><font size="3">List DP Not Difference</font></b> </div>
                                                                                                </td>
                                                                                           
                                                                                       </tr>
                                                                                    </table>
                                                                                    <table  border="0" cellspacing="0" cellpadding="0">
                                                                                        <tr>
                                                                                            <td>&nbsp;</td>
                                                                                            <td>&nbsp;</td>
                                                                                            <td>&nbsp;</td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>&nbsp;</td>
                                                                                            <td>&nbsp;</td>
                                                                                            <td>&nbsp;</td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>Employee Number :</td>
                                                                                            <td></td>
                                                                                            <td align="left"><input name="emp_number" type="text" size="30" maxlength="30"></td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td>&nbsp;</td>
                                                                                            <td>&nbsp;</td>
                                                                                            <td>&nbsp;</td>
                                                                                        </tr>
                                                                                       
                                                                                     <%if(iCommand == Command.GOTO || iCommand == Command.NONE){%>
                                                                                        <tr>
                                                                                            <td  align="left" class="command"><table width="60" border="0" cellspacing="0" cellpadding="0">
                                                                                                    <tr>
                                                                                                        <td ><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                                                        <td ><a href="javascript:cmdList()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Save"></a></td>
                                                                                                        <td ><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                                                        <td ><b><a href="javascript:cmdList()" class="command">List</a></b></td>
                                                                                                    </tr>
                                                                                                </table></td>

                                                                                        </tr>
                                                                                    <%}%>
                                                                                    </table>
                                                                                        <%if(iCommand == Command.LIST){%>
                                                                                         <table border="0" width="100%">
                                                                                              <tr>
                                                                                                    <td height="20" colspan="3"><font color="#FF6600" face="Arial"><strong>  DP ELIGIBLE LIST  </strong></font> </td>
                                                                                                </tr>
                                                                                                <%if( listEmp !=null && listEmp.size()>0){%>
                                                                                                <tr>
                                                                                                    <td colspan="3" valign="top">
                                                                                                        <%
                                                                                                                out.println(drawList(listDp,listEmp,leaveConfig));  
                                                                                                        %>
                                                                                                    </td>
                                                                                                </tr>
                                                                                                <%}else{%>
                                                                                                 <tr>
                                                                                                    <td colspan="3" valign="top">
                                                                                                        No Data
                                                                                                    </td>
                                                                                                </tr>
                                                                                             <%}%>
                                                                                    </table>
                                                                                    <table>
                                                                                        <tr>
                                                                                            <td width="25%" nowrap align="left" class="command">
                                                                                                    <tr>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                                                        <td width="24"><a href="javascript:cmdList()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Save"></a></td>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                                                        <td width="169" nowrap><b><a href="javascript:cmdList()" class="command">List</a></b></td>
                                                                                                        
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                                                        <td width="24"><a href="javascript:cmdBack()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1001','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1001" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Save"></a></td>
                                                                                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                                                                                        <td width="169" nowrap><b><a href="javascript:cmdBack()" class="command">Back to Re-Calculate DP</a></b></td>
                                                                                                    </tr>
                                                                                            </td>
                                                                                           
                                                                                        </tr>
                                                                                    </table>
                                                                                    <%}%>
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
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
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

    <!-- #EndEditable --><!-- #EndTemplate -->
</html>

