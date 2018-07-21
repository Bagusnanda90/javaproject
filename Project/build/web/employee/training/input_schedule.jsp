<%-- 
    Document   : input_schedule
    Created on : Jan 17, 2009, 1:19:02 PM
    Author     : bayu
--%>

<%@page contentType="text/html"%>

<!-- package java -->
<%@ page import = "java.util.*" %>

<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>

<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>

<!--package harisma -->
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.form.attendance.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.entity.attendance.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
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
    }
    else if(trainType == PRIV_DEPT) {  
        appObjCode = appObjCodeDept;
    }

    boolean privView = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_VIEW));
    boolean privAdd = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_ADD));
    boolean privUpdate = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_UPDATE));
    boolean privDelete = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_DELETE));
    boolean privPrint = userSession.checkPrivilege(AppObjInfo.composeCode(appObjCode, AppObjInfo.COMMAND_PRINT));
%>
<%@ include file = "../../main/checktraining.jsp" %>

<%      
    int iCommand = FRMQueryString.requestCommand(request);
    long oidPlan = FRMQueryString.requestLong(request, "plan");    
    long oidSchedule = FRMQueryString.requestLong(request, "schedule");
    
    
    int iErrCode = FRMMessage.ERR_NONE;
    String errMsg = "";    
    
    TrainingSchedule schedule = new TrainingSchedule();
    FrmTrainingSchedule trainSchedule = new FrmTrainingSchedule(request, schedule);     
    
    CtrlTrainingSchedule ctrlSchedule = new CtrlTrainingSchedule(request);
    SessTraining sessTraining = new SessTraining();
    
    // editing schedule
    if(iCommand == Command.NONE && oidSchedule != 0) {
          schedule = PstTrainingSchedule.fetchExc(oidSchedule); 
    }
 
    if(iCommand == Command.SAVE) {
          trainSchedule.requestEntityObject(schedule);
          schedule.setTrainPlanId(oidPlan);
        
          Date timeStart = ControlDate.getTime(FrmTrainingSchedule.fieldNames[FrmTrainingSchedule.FRM_FIELD_START_TIME], request);
          schedule.setStartTime(timeStart);
          
          Date timeEnd = ControlDate.getTime(FrmTrainingSchedule.fieldNames[FrmTrainingSchedule.FRM_FIELD_END_TIME], request);
          schedule.setEndTime(timeEnd);           
    }
          
%>

<html>
<head><title>Training Schedule</title>
<script language="javascript">
    
        function cmdSave() {
             document.frmschedule.command.value="<%=Command.SAVE%>";
             document.frmschedule.submit();             
        }
        
        function cmdDelete() {
            if(window.confirm("Are you sure to delete this schedule?")) {
                document.frmschedule.command.value="<%=Command.DELETE%>";
                document.frmschedule.submit();
            }
        }
       
    
        <% if(iCommand == Command.SAVE) {                 
                                        
            // save schedule data            
            try {
                iErrCode = ctrlSchedule.action(iCommand, oidSchedule, schedule);                
            }
            catch(Exception e) {}            
          
            %>
                
            self.opener.document.frm_trainingplan.command.value="<%=Command.BACK%>";
            self.opener.document.frm_trainingplan.submit();
            self.close();
            
        <% } %>		
        
        
        <% if(iCommand == Command.DELETE) {
        
            // delete schedule data            
            try {
                iErrCode = ctrlSchedule.action(iCommand, oidSchedule, schedule);
            }
            catch(Exception e) {}            
          
            %>
        
            self.opener.document.frm_trainingplan.command.value="<%=Command.BACK%>";
            self.opener.document.frm_trainingplan.submit();
            self.close();
            
        <% } %>
        
        
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
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
<tr> 
    <td width="88%" valign="top" align="left"> 
    <table width="100%" border="0" cellspacing="3" cellpadding="2"> 
    <tr> 
        <td width="100%">
        <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
            <td height="20"> 
                <font color="#FF6600" face="Arial"><strong>
                    <%= (oidPlan !=0 && oidSchedule != 0) ? "Edit" : "Add" %> Schedule
                </strong></font> 
            </td>
        </tr>
        <tr> 
            <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr> 
                <td class="tablecolor"> 
                <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                <tr> 
                    <td valign="top"> 
                    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                    <tr> 
                        <td valign="top">
                            
                        <!-- #BeginEditable "content" -->    
                        <form name="frmschedule" method="post" action="">                                
                            <input type="hidden" name="command" value="<%=iCommand%>">
                            <input type="hidden" name="plan" value="<%=oidPlan%>">
                            <input type="hidden" name="schedule" value="<%=oidSchedule%>">
                            
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                  <tr> 
                                    <td width="19%">Training Date</td>
                                    <td width="1%">:</td>
                                    <td width="80%"> 
                                        <%=ControlDate.drawDateWithStyle(FrmTrainingSchedule.fieldNames[FrmTrainingSchedule.FRM_FIELD_TRAIN_DATE], (schedule.getTrainDate() == null) ? new Date() : schedule.getTrainDate(), 1, -5, "formElemen", "")%>
                                    </td>
                                  </tr>
                                  <tr> 
                                    <td width="19%">Training End Date</td>
                                    <td width="1%">:</td>
                                    <td width="80%"> 
                                        <%=ControlDate.drawDateWithStyle(FrmTrainingSchedule.fieldNames[FrmTrainingSchedule.FRM_FIELD_TRAIN_END_DATE], (schedule.getTrainEndDate() == null) ? new Date() : schedule.getTrainEndDate(), 1, -5, "formElemen", "")%>
                                    </td>
                                  </tr>
                                  <tr> 
                                    <td width="19%">Training Time</td>
                                    <td width="1%">:</td>
                                    <td width="80%"> 
                                        <%=ControlDate.drawTime(FrmTrainingSchedule.fieldNames[FrmTrainingSchedule.FRM_FIELD_START_TIME], (schedule.getStartTime() == null) ? new Date() : schedule.getStartTime(), "formElemen")%> To 
                                        <%=ControlDate.drawTime(FrmTrainingSchedule.fieldNames[FrmTrainingSchedule.FRM_FIELD_END_TIME], (schedule.getEndTime() == null) ? new Date() : schedule.getEndTime(), "formElemen")%> 
                                    </td>
                                    <tr> 
                                    <td width="19%">Total Hours</td>
                                    <td width="1%">:</td>
                                    <td width="80%"> 
                                        <input type="text" name="<%=FrmTrainingSchedule.fieldNames[FrmTrainingSchedule.FRM_FIELD_TOTAL_HOUR]%>" value="<%=schedule.getTotalHour() %>">
                                    </td>
                                  </tr>
                                  </tr>                                             
                                  <tr> 
                                    <td width="19%">Training Venue</td>
                                    <td width="1%">:</td>
                                    <td width="80%">                                       
                                       <%
                                            Vector venue_keys = new Vector(1, 1);
                                            Vector venue_vals = new Vector(1, 1);
                                            
                                            Vector venueList = PstTrainVenue.listAll();
                                            
                                            for(int i=0; i<venueList.size(); i++) {
                                                TrainVenue ven = (TrainVenue)venueList.get(i);
                                                
                                                venue_keys.add(ven.getVenueName());
                                                venue_vals.add(String.valueOf(ven.getOID()));
                                            }
                                       %>
                                       <%=ControlCombo.draw(FrmTrainingSchedule.fieldNames[FrmTrainingSchedule.FRM_FIELD_TRAIN_VENUE_ID], null, String.valueOf(schedule.getTrainVenueId()), venue_vals, venue_keys)%>  <%= trainSchedule.getErrorMsg(FrmTrainingSchedule.FRM_FIELD_TRAIN_VENUE_ID)%>                                          
                                    </td>
                                  </tr> 
                                  <tr>
                                    <td colspan="3">
                                    <table cellpadding="0" cellspacing="0" border="0">
                                    <tr> 
                                        <td width="4"><img src="<%=approot%>/images/spacer.gif" width="4" height="4"></td>
                                        <td width="15"><a href="javascript:cmdSave()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image101" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" alt="Save Schedule"></a></td>
                                        <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                        <td class="command" nowrap width="99"> 
                                            <div align="left"><a href="javascript:cmdSave()">Save Schedule</a></div>
                                        </td>
                                        
                                        <% if(oidPlan !=0 && oidSchedule != 0) { %>
                                            <td width="15"><img src="<%=approot%>/images/spacer.gif" width="15" height="4"></td>
                                            <td width="15"><a href="javascript:cmdDelete()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnDelOn.jpg',1)"><img name="Image10" border="0" src="<%=approot%>/images/BtnDel.jpg" width="24" height="24" alt="Delete Schedule"></a></td>
                                            <td width="8"><img src="<%=approot%>/images/spacer.gif" width="8" height="8"></td>
                                            <td class="command" nowrap width="99"> 
                                                <div align="left"><a href="javascript:cmdDelete()">Delete Schedule</a></div>
                                            </td>
                                        <% } %>
                                    </tr>
                                    </table>
                                    </td>
                                  </tr>    
                                              
                                </table>
                                </td>
                            </tr> 
                            </table>
                        </form>
                               
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
      </table>
    </td> 
  </tr>
</table>
</body>
</html>