<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.harisma.form.search.FrmSrcEmpSchedule"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@ page language = "java" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.dimata.qdep.form.*" %>
<%@ page import="com.dimata.system.entity.system.*" %>

<%@ include file = "../../main/javainit.jsp" %>  
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_ATTENDANCE, AppObjInfo.OBJ_WORKING_SCHEDULE); %>
<%
/* Check privilege except VIEW, view is already checked on checkuser.jsp as basic access*/
boolean privAdd=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
%>

<%
String forward_page = "up_sch_process_mhalf.jsp";
long periodIdSelected = FRMQueryString.requestLong(request,FrmSrcEmpSchedule.fieldNames[FrmSrcEmpSchedule.FRM_FIELD_PERIOD]);
Period period = new Period();
//Period period = new Period();
try{
period = PstPeriod.fetchExc(periodIdSelected);
//period = PstPeriod.fetchExc(periodId);
} catch(Exception exc){
    
}
session.putValue("PERIOD_UPLOAD_EXCEL", period); 



int iCommand = FRMQueryString.requestCommand(request);
if(SystemProperty.SYS_PROP_SCHEDULE_PERIOD==SystemProperty.TYPE_SCHEDULE_PERIOD_A_MONTH_FULL)
{
	forward_page = "up_sch_process_mfull.jsp";  
}

%>
<html>
<!-- #BeginTemplate "/Templates/main.dwt" --> 
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Upload Working Schedule</title>
<script language="JavaScript">
function cmdSave(){
		document.frmUploadSchedule.command.value="<%=String.valueOf(Command.POST)%>";
                document.frmUploadSchedule.action="<%=forward_page%>";
		document.frmUploadSchedule.submit();
       
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
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2">
        <tr> 
          <td width="100%"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->Uploader 
                  &gt; Working Schedule<!-- #EndEditable --> </strong></font> 
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
                              <table  style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                <tr> 
                                  <td valign="top"> <!-- #BeginEditable "content" --> 
                                     <form name="frmUploadSchedule" method="post" action="" enctype="multipart/form-data">
                                        <input type="hidden" name="command" value="<%=iCommand%>">
                                        <!--input type="text" name="FrmPeriodIdSelected" value="<%//=periodIdSelected%>"-->
                                      <table width="60%" border="0" cellspacing="2" cellpadding="2">
                                        <tr> 
                                          <td width="5%">&nbsp;</td>
                                          <td width="9%">&nbsp;</td>
                                          <td width="86%">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td></td>
                                            <td>
                                            <%
                                            /*Vector perValue = new Vector(1,1);
                                            Vector perKey = new Vector(1,1);
					    Vector listPeriod = PstPeriod.list(0, 0, "", PstPeriod.fieldNames[PstPeriod.FLD_PERIOD]);  
                                            for(int r=0;r<listPeriod.size();r++){
                                            Period period = (Period)listPeriod.get(r);
                                            //Period period = (Period)listPeriod.get(r);
                                            perValue.add(""+period.getOID()); 
                                            perKey.add(period.getPeriod());
										  }*/
										  %> <%//=ControlCombo.draw("FrmPeriodIdSelected",null,""+periodIdSelected,perValue,perKey,"")%>
                                                                                   
                                            </td>
                                        </tr>
                                        <tr>
                                          <td width="5%">&nbsp;</td>
                                          <td width="9%" nowrap>Periode: </td>
                                          <td width="80%"> <%=Formater.formatDate(period.getStartDate(), "yyyy-MM-dd") %> s/d <%=Formater.formatDate(period.getEndDate(), "yyyy-MM-dd")%></td>
                                        </tr>
                                        <tr> 
                                          <td width="5%">&nbsp;</td>
                                          <td width="9%" nowrap>Upload File</td>
                                          <td width="86%"> 
                                        <input type="file" name="file" size="40">
                                        <%if(privAdd){%>
                                        <a href="javascript:cmdSave()">Submit</a>
                                        <%}%>
                                          </td>

                                        <tr> 
                                          <td width="5%">&nbsp;</td>
                                          <td width="9%">&nbsp;</td>
                                          <td width="86%">&nbsp;<a href="sample_fomat_schedule_v3_start20.xls">You may downloaded format example here, please RIGHT CLICK AND SAVE TARGET AS : sample-schedule.xls</a></td>
                                        </tr>

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
