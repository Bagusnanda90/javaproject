<%-- 
    Document   : service_bakup_emp_outlet
    Created on : Aug 21, 2014, 4:42:11 PM
    Author     : Dimata 007
--%>
<%@page import="com.dimata.harisma.entity.masterdata.location.Location"%>
<%@page import="com.dimata.harisma.entity.masterdata.location.PstLocation"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.utility.service.bkpdtoutlet.ServiceBakupEmployeeOutletTransfer"%>
<%@page import="java.util.Date"%>
<%@page import="com.dimata.harisma.utility.service.bkpdtoutlet.BakupEmployeeOutletTransferData"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.gui.jsp.ControlDate"%>

<%@ include file = "../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_TIMEKEEPING, AppObjInfo.OBJ_SERVICE_MANAGER); %>
<%@ include file = "../main/checkuser.jsp" %>
<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>

<%

response.setHeader("Expires", "Mon, 06 Jan 1990 00:00:01 GMT");
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "nocache");


ServiceBakupEmployeeOutletTransfer  serviceBakupEmployeeOutletTransfer=  ServiceBakupEmployeeOutletTransfer.getInstance(true);

String strStatusBakup="";
String strButtonStatusBakup="";
Date dtStart = FRMQueryString.requestDateVer3(request, "check_date_start"); 
Date dtFinish = FRMQueryString.requestDateVer3(request, "check_date_end");
String paramSch = FRMQueryString.requestString(request, "inputSymbol");
String codeLocation = FRMQueryString.requestString(request, "codeLocation");
String locationFIle = FRMQueryString.requestString(request, "locationFIle");
boolean cbxOutlet = FRMQueryString.requestInt(request, "chbxEmployeeOutlet")==0?false:true; 
boolean cbxScheduleSymbol = FRMQueryString.requestInt(request, "chbxSchSymbol")==0?false:true;  
boolean cbxKadivMapping = FRMQueryString.requestInt(request, "chbxKdivMapping")==0?false:true;  
	if (true)    
	{	
		String iCommandMachine = request.getParameter("iCommandMachine"); 
		if (iCommandMachine != null) 
		{
			if (iCommandMachine.equalsIgnoreCase("Run")) 
			{                               
				try 
				{
                                    //update by satrya 2013-12-18
                                    if(true){ 
                                        serviceBakupEmployeeOutletTransfer.startService(dtStart, dtFinish, codeLocation,cbxOutlet,false,cbxScheduleSymbol,cbxKadivMapping,paramSch,locationFIle); 
                                    }
				}
				catch (Exception e) 
				{
					System.out.println("\t Exception svrmgrMachine.startTransfer() = " + e);
				}
			}
			else if (iCommandMachine.equalsIgnoreCase("Stop")) 
			{
				try 
				{
					serviceBakupEmployeeOutletTransfer.stopService();
				}
				catch (Exception e) 
				{
					System.out.println("\t Exception svrmgrMachine.stopWatcherMachine() = " + e);
				}
			}
		}
		
		if (serviceBakupEmployeeOutletTransfer.getStatus())
		{
			strStatusBakup = "Run";
			strButtonStatusBakup = "Stop";
		}
		else 
		{
			strStatusBakup = "Stop";
			strButtonStatusBakup = "Run";
		}
	}


%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
<head>
<!-- #BeginEditable "doctitle" --> 
<title>HARISMA - Machine Service</title>
<!-- #EndEditable --> 
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<!-- #BeginEditable "styles" --> 
<link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "stylestab" --> 
<link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
<!-- #EndEditable -->
<!-- #BeginEditable "headerscript" --> 
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

    function cmdSetStatusButtonBakup(cmd) {
        document.frmsvcmgr.iCommandMachine.value = cmd;
        document.frmsvcmgr.action = "<%=approot%>/service/service_bakup_emp_outlet.jsp";
        document.frmsvcmgr.submit();
    }

</SCRIPT>
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
  <tr> 
    <td width="88%" valign="top" align="left"> 
      <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
        <tr> 
          <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td height="20">
		    <font color="#FF6600" face="Arial"><strong>
			  <!-- #BeginEditable "contenttitle" -->System 
                  &gt; Timekeeping &gt; Service Manager<!-- #EndEditable --> 
            </strong></font>
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
                                <% if (true) { %>
                                    <form name="frmsvcmgr" method="post" action="">
                                      <input type="hidden" name="iPortStatus" value="">
                                      <input type="hidden" name="iCommandMachine" value="">
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                       <!-- update by satrya 2013-12-18-->
                                       <tr> 
                                          <td colspan="2"> 
                                            <div align="left">
                                                Location file <input name="locationFIle" type="text" value="" size="100"/>
                                            </div>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2"> 
                                            <div align="left">
                                                <%
                                                    Vector cdLoc_value=new Vector();
                                                    Vector cdLoc_key = new Vector();
                                                    Vector listLocation = PstLocation.list(0, 0, "", PstLocation.fieldNames[PstLocation.FLD_CODE] +" ASC ");
                                                    if(listLocation!=null && listLocation.size()>0){
                                                        for(int idc=0;idc<listLocation.size();idc++){
                                                            Location location = (Location)listLocation.get(idc);
                                                            cdLoc_value.add(""+location.getCode());
                                                            cdLoc_key.add(location.getCode()); 
                                                        }
                                                    }
                                                %>
                                               Code Location <%=ControlCombo.draw("codeLocation", null, "" + codeLocation, cdLoc_value, cdLoc_key)%>
                                            </div>
                                          </td>
                                        </tr>
                                       <tr> 
                                          <td colspan="2"> 
                                            <div align="left">
                                              <input name="chbxEmployeeOutlet" type="checkbox" value="1" /> tbl Employee Outlet
                                              <%=ControlDate.drawDateWithStyle("check_date_start", dtStart != null ? dtStart : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>   to 
                                              <%=ControlDate.drawDateWithStyle("check_date_end", dtFinish != null ? dtFinish : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"")%>   
                                            </div>
                                          </td>
                                        </tr>
                                        
                                        
                                        <tr> 
                                          <td colspan="2"> 
                                            <div align="left">
                                                <input name="chbxSchSymbol" type="checkbox" value="1" /> tbl Sch Symbol
                                                <input type="text" name="inputSymbol" value=""/> 
                                            </div>
                                          </td>
                                        </tr>
                                        <tr> 
                                          <td colspan="2"> 
                                            <div align="left">
                                                <input name="chbxKdivMapping" type="checkbox" value="1" /> Kadiv Mapping
                                            </div>
                                          </td>
                                        </tr>
                                       
                                       
                                        <tr> 
                                          <td colspan="2"> 
                                            <div align="left">
                                              
                                            Status of Service Bakup Sql is <b><%=strStatusBakup%></b>.<br>
                                            <input type="button" name="btnStatusMachine" value="<%=strButtonStatusBakup%>" onClick="javascript:cmdSetStatusButtonBakup('<%=strButtonStatusBakup%>');"> Click this button to <%=strButtonStatusBakup%> Service Bakup Sql.<br><br><br>
                                            </div>
                                          </td>
                                        </tr>
                                       <!-- <tr>
                                            <td>
                                                <img alt=""  src="../../images/loading.gif" height="8" width="<%=(serviceBakupEmployeeOutletTransfer.getProgressSize()==0?"":(""+serviceBakupEmployeeOutletTransfer.getProgressSize()*300/serviceBakupEmployeeOutletTransfer.getProgressSize())) %>" > <%=(serviceBakupEmployeeOutletTransfer.getProgressSize()==0?"":(""+serviceBakupEmployeeOutletTransfer.getProgressSize()*100/serviceBakupEmployeeOutletTransfer.getProgressSize())) %>% 
                                          <BR>
                                            </td>
                                        </tr>-->
                                        <tr>
                                            <td>Message : <%=serviceBakupEmployeeOutletTransfer.getMessageTransferEmployee() %></td>
                                        </tr>
                                        
                                      </table>

                                    </form>
                                
           <%
                                          }
                                   else
                                   {
                                %>
                                <div align="center">You do not have sufficient privilege to access this page.</div>
                                <% }%>

                              <!-- #EndEditable --> 
                            </td>
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
  <%if(false){%>
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
       <%}%>
       <%if(strStatusBakup!=null && strStatusBakup.equalsIgnoreCase("Run")){%>
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
</table>
</body>
<!-- #BeginEditable "script" -->
<!-- #EndEditable -->
<!-- #EndTemplate --></html>

