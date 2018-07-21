
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

<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode =  AppObjInfo.composeObjCode(AppObjInfo.G1_SYSTEM, AppObjInfo.G2_TIMEKEEPING, AppObjInfo.OBJ_SERVICE_MANAGER); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%
// Check privilege except VIEW, view is already checked on checkuser.jsp as basic access
//boolean privStart=userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_START));
%>

<%!
    //public static ServiceManagerTMA svrmgrTMA = new ServiceManagerTMA();
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
try{
    strTransferMachineAbsensiWithParameter = PstSystemProperty.getValueByName("TRANSFER_ABSENSI_WITH_PARAMETER").equalsIgnoreCase(PstSystemProperty.SYS_NOT_INITIALIZED)?false:Boolean.parseBoolean(PstSystemProperty.getValueByName("TRANSFER_ABSENSI_WITH_PARAMETER")); 
}catch(Exception exc){
    System.out.println("Exc"+exc);
}
TransManager svrmgrMachine =  TransManager.getInstance(true);  /* }*/

//update by satrya 2013-12-19
MesinAbsensiMessage mesinAbsensiMessage = new MesinAbsensiMessage();
mesinAbsensiMessage = svrmgrMachine==null || svrmgrMachine.getMesinAbsensiMessage()==null ? new MesinAbsensiMessage() :svrmgrMachine.getMesinAbsensiMessage(); 
Date startDate = FRMQueryString.requestDateVer3(request, "check_date_start"); 
Date endDate = FRMQueryString.requestDateVer3(request, "check_date_end");
int changeAutomaticManualFinish= FRMQueryString.requestInt(request, "change_automatic_manual");
int statusParam= FRMQueryString.requestInt(request, "input_status");
 if(mesinAbsensiMessage.getStartDate()!=null && mesinAbsensiMessage.getEndDate()!=null && mesinAbsensiMessage.isUsePushStop()){ 
    startDate = mesinAbsensiMessage.getStartDate();
    endDate =  mesinAbsensiMessage.getEndDate();
    changeAutomaticManualFinish= mesinAbsensiMessage.getAutomaticContinueSearch();
    statusParam= mesinAbsensiMessage.getStatusHr();
 }
 
	if (privStart)  
	{	
		String iCommandMachine = request.getParameter("iCommandMachine"); 
		if (iCommandMachine != null) 
		{
			if (iCommandMachine.equalsIgnoreCase("Run")) 
			{                               
				try 
				{
                                    //update by satrya 2013-12-18
                                    if(strTransferMachineAbsensiWithParameter){ 
                                        svrmgrMachine.startTransfer(startDate, endDate, statusParam,changeAutomaticManualFinish); 
                                    }else{
                                          svrmgrMachine.startTransfer();
                                        
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
					svrmgrMachine.stopTransfer();
				}
				catch (Exception e) 
				{
					System.out.println("\t Exception svrmgrMachine.stopWatcherMachine() = " + e);
				}
			}
		}
		
		if (svrmgrMachine.getStatus())
		{
			strStatusSvrmgrMachine = "Run";
			strButtonStatusMachine = "Stop";
		}
		else 
		{
			strStatusSvrmgrMachine = "Stop";
			strButtonStatusMachine = "Run";
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

    function cmdSetStatusMachine(cmd) {
        document.frmsvcmgr.iCommandMachine.value = cmd;
        document.frmsvcmgr.action = "<%=approot%>/system/timekeepingpro/svcmgr.jsp";
        document.frmsvcmgr.submit();
    }

    function cmdSetStatusPresence(cmd) {
        document.frmsvcmgr.iCommandPresence.value = cmd;
        document.frmsvcmgr.action = "<%=approot%>/system/timekeepingpro/svcmgr.jsp";
        document.frmsvcmgr.submit();
    }

</SCRIPT>
<style type="text/css">
input.largerCheckbox  {
	width: 30px;
	padding: 0px;
	margin: 0px;
	}
    
</style>
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
                                <% if (privStart) { %>
                                    <form name="frmsvcmgr" method="post" action="">
                                      <input type="hidden" name="iPortStatus" value="">
                                      <input type="hidden" name="iCommandMachine" value="">
                                      <input type="hidden" name="iCommandPresence" value="">
                                      <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                       <!-- update by satrya 2013-12-18-->
                                       <%if(strTransferMachineAbsensiWithParameter){%>
                                        <tr> 
                                          <td colspan="2"> 
                                            <div align="left">
                                                <fieldset>
                                                  <legend>Parameter:</legend>

                                                       <table>

                                                                <tr>
                                                                        <td>Date</td>
                                                                        <%
                                                                             Date st = new Date();
                                                                            st.setHours(0);
                                                                            st.setMinutes(0);
                                                                            st.setSeconds(0);
                                                                            Date end = new Date();
                                                                            end.setHours(23);
                                                                            end.setMinutes(59);
                                                                            end.setSeconds(59);
                                                                            String ctrTimeStart = ControlDate.drawTime("check_date_start",  startDate != null ? startDate : st, "elemenForm", 24,0, 0); 
                                                                                                 String ctrTimeEnd = ControlDate.drawTime("check_date_end",  endDate != null ?  endDate : end, "elemenForm", 24,0, 0); 
                                                                        %>
                                                                        <td> <%=ControlDate.drawDateWithStyle("check_date_start", startDate != null ? startDate : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"") + ctrTimeStart%>  
                                                                            : <%=ControlDate.drawDateWithStyle("check_date_end", endDate != null ? endDate : new Date(), 2, -5, "formElemen", " onkeydown=\"javascript:fnTrapKD()\"") + ctrTimeEnd%>  
                                                                        </td>
                                                                </tr>
                                                                <tr>
                                                                        <td>Status At Mesin Absence</td>
                                                                        <%
                                                                            Vector value = new Vector();
                                                                            Vector key = new Vector();
                                                                            value.add(""+0);
                                                                            value.add(""+1);
                                                                            value.add(""+2);
                                                                            key.add("New");
                                                                            key.add("Prosess");
                                                                            key.add("All");
                                                                            String cheked = mesinAbsensiMessage.isUsePushStop() ?changeAutomaticManualFinish==0?"": "checked=\"checked\"": changeAutomaticManualFinish==0?"":"checked=\"checked\"";
                                                                        %>
                                                                        <td>: <%=ControlCombo.draw("input_status", null, "" +statusParam , value, key)%>
                                                                            <input type="checkbox"  class="largerCheckbox" name="change_automatic_manual" value="1" <%=cheked%>/>
                                                                            <label>Change to automatic mode after manual mode finished </label>
                                                                        </td>
                                                                </tr>
                                                        </table>
                                                 </fieldset>
                                            </div>
                                          </td>
                                        </tr>
                                        <%}%>
                                        <tr> 
                                          <td colspan="2"> 
                                            <div align="left">
                                              
                                            Status of Service Manager for Machine is <b><%=strStatusSvrmgrMachine%></b>.<br>
                                            <input type="button" name="btnStatusMachine" value="<%=strButtonStatusMachine%>" onClick="javascript:cmdSetStatusMachine('<%=strButtonStatusMachine%>');"> Click this button to <%=strButtonStatusMachine%> the Machine Service Manager.<br><br><br>
                                            </div>
                                          </td>
                                        </tr>
                              <!-- GEDE_20110831_01 { -->   <%
                            if(attViaWeb!=null && attViaWeb.compareTo("1")==0){
                              if(vClass!=null && vClass.size()>0){
                                  for(int i=0; i<vClass.size();i++){
                                      String[] classA = (String[]) vClass.get(i);
                                      svrmgrMachine.addTxtProcessClass("com.dimata.harisma.utility.machine."+classA[0]);                                                                                                                  
                                  }
                              } else {
                                  out.println("ATT_MACHINE_ODBC_CLASS value="+ odbcClasses + " => set to right class ");
                              }
                            } else{
                                out.println("ATTENDANCE_DATA_ACCESS_VIA_WEB value="+ attViaWeb +" => Attendance database  is set to be transfered by other interface");
                            }
                                         

        if ( svrmgrMachine.getTxtProcessClassSize()>0) {
           for(int i =0; i< svrmgrMachine.getTxtProcessClassSize();i++) {
               


                                          %>  
                                          <tr>
                                              <td>
                                          Machine Type: <%=svrmgrMachine.getTxtProcessClass(i)%> <BR>
                                          Total Data:   <%=svrmgrMachine.getTotalRecord(i) %> <BR>
                              Data Transfered:   <%=svrmgrMachine.getProcentTransfer(i) %> 
                                            </td>
                                            <td>&nbsp </td>
                                        </tr>
                                        <tr>
                                            <td>
 <img alt=""  src="../../images/loading.gif" height="8" width="<%=(svrmgrMachine.getTotalRecord(i)==0?"":(""+svrmgrMachine.getProcentTransfer(i)*300/svrmgrMachine.getTotalRecord(i))) %>" > <%=(svrmgrMachine.getTotalRecord(i)==0?"":(""+svrmgrMachine.getProcentTransfer(i)*100/svrmgrMachine.getTotalRecord(i))) %>% 
 <BR>
                                            </td>
                                        </tr>
                                        <tr><td><%=svrmgrMachine.getMessageTransfer(i)%></td></tr>
                                        <%  } }%>                                       
                                         
                                        <tr>
                                              <td>
                                          Analyzing Data Att. Machine <BR>
                                          Total Data:   <%=svrmgrMachine.getTotalRecordAssistant() %> <BR>
                              Data Transfered:   <%=svrmgrMachine.getProcentTransferAssistant() %> 
                                            </td>                                            
                                        </tr>
                                        <tr>
                                            <td>
                                                <img alt=""  src="../../images/loading.gif" height="8" width="<%=(svrmgrMachine.getTotalRecordAssistant()==0?"":(""+svrmgrMachine.getProcentTransferAssistant()*300/svrmgrMachine.getTotalRecordAssistant())) %>" > <%=(svrmgrMachine.getTotalRecordAssistant()==0?"":(""+svrmgrMachine.getProcentTransferAssistant()*100/svrmgrMachine.getTotalRecordAssistant())) %>% 
                                          <BR>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Message : <%=svrmgrMachine.getTransferAssistantMessage() %></td>
                                        </tr>
                                        <!-- update by satrya 2013-07-25 -->
                                        <tr>
                                              <td>
                                          Analyzing Data Status <BR>
                                          Total Days:   <%=svrmgrMachine.getTotalAnalyzeRecordAssistant()%> <BR>
                              Data Transfered Finish Days:   <%=svrmgrMachine.getProcentAnalyzeTransferAssistant() %> 
                                            </td>                                            
                                        </tr>
                                        <tr>
                                            <td>
                                                <img alt=""  src="../../images/loading.gif" height="8" width="<%=(svrmgrMachine.getTotalAnalyzeRecordAssistant()==0?"":(""+svrmgrMachine.getProcentAnalyzeTransferAssistant()*300/svrmgrMachine.getTotalAnalyzeRecordAssistant())) %>" > <%=(svrmgrMachine.getTotalAnalyzeRecordAssistant()==0?"":(""+svrmgrMachine.getProcentAnalyzeTransferAssistant()*100/svrmgrMachine.getTotalAnalyzeRecordAssistant())) %>% 
                                          <BR>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Message : <%=svrmgrMachine.getTransferAnalizeAssistantMessage() %></td>
                                        </tr>
                                        <!-- end -->
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
                          <% 
         %>
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
       <%if(svrmgrMachine.running){%>
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
